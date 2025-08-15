/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.unvisiblereferencecubes;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.15.4", source = Source.EMF, group = "VirtualCube") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Cube with virtual cube with dimension references from other cubes
            A virtual cube that combines measures and dimensions from multiple physical cubes into a unified analytical view.
            Virtual cubes enable cross-cube analysis by creating a logical integration layer over existing physical cubes,
            allowing users to analyze related metrics from different business processes in a single query
            Catalog have two physical cubes Cube1 and Cube2 and virtual cube Cube1Cube2.
            But Cube1 and Cube2 are un visible in client side.
            Virtual cube only visible in excel and other clients
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a table that stores all the data.
            The table is named `Fact` uses for Cube1 and contains two columns: `KEY` and `VALUE`.
            The KEY column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;


    private static final String measure1Body = """
            Measure use C1_Fact table VALUE column with sum aggregation in Cube1.
    """;

    private static final String measure2Body = """
            Measure use Fact table VALUE column with sum aggregation in Cube2.
    """;

    private static final String cube1Body = """
            In this example uses cube with fact table Fact as data.
            Cube1 is un visible in client side.
            """;

    private static final String cube2Body = """
            In this example uses cube with fact table Fact as data.
            Cube2 is un visible in client side.
            """;

    private static final String vCubeBody = """
            Virtual cube uses mesures from Cube1 and Cube2. Virtual cube has references for them.
            Also virtual cube has references to dimensions from Cube1 and Cube2
            Cube1 and Cube2 are un visible in client side.
            """;

    private static final String catalogDocumentationTxt = """
        A basic OLAP schema with virtual cube which have reference to Cube1, Cube2 with visible = false
        Virtual cube only visible in excel
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_unvisiblereferencecubes");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable c1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        c1Table.setName(FACT);
        c1Table.setId("_table_" + FACT.toLowerCase());
        c1Table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(c1Table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(c1Table);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setId("_level_Level2");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("_hierarchy_HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("_dimension_Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_cube1Dimension1");
        dimensionConnector1.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector1.setDimension(dimension1);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setId("_dc_cube2Dimension1");
        dimensionConnector2.setOverrideDimensionName("Cube2Dimension1");
        dimensionConnector2.setDimension(dimension1);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("MeasureCube1");
        measure1.setId("_measure_MeasureCube1");
        measure1.setColumn(valueColumn);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("MeasureCube2");
        measure2.setId("_measure_MeasureCube2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube_" + CUBE1.toLowerCase());
        cube1.setQuery(query);
        cube1.setVisible(false);
        cube1.getDimensionConnectors().add(dimensionConnector1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId("_cube_" + CUBE2.toLowerCase());
        cube2.setQuery(query);
        cube2.setVisible(false);
        cube1.getDimensionConnectors().add(dimensionConnector2);
        cube2.getMeasureGroups().add(measureGroup2);

        measureGroup1.setPhysicalCube(cube1);
        measureGroup2.setPhysicalCube(cube2);
        dimensionConnector1.setPhysicalCube(cube1);
        dimensionConnector2.setPhysicalCube(cube2);

        VirtualCube vCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        vCube.setName("Cube1Cube2");
        vCube.setId("_virtualCube_Cube1Cube2");
        vCube.setDefaultMeasure(measure1);
        vCube.getDimensionConnectors().add(dimensionConnector1);
        vCube.getDimensionConnectors().add(dimensionConnector2);
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));


        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with virtual cube with un visible reference cubes");
        catalog.setDescription("Catalog of a minimal virtual cube with unvisible reference cubes");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Schema with virtual cube with dimension references from other cubes with un visible reference cubes ", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(measure1, "MeasureCube1", measure1Body, 1, 4, 0, true, 2);
        document(measure2, "MeasureCube2", measure2Body, 1, 5, 0, true, 2);
        document(cube1, "Cube1", cube1Body, 1, 6, 0, true, 2);
        document(cube1, "Cube1", cube2Body, 1, 7, 0, true, 2);
        document(vCube, "VirtualCubeMeasureOnly", vCubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
