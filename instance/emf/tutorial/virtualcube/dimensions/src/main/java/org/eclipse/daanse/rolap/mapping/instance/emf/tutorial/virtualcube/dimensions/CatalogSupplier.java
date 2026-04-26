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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.dimensions;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.15.02", source = Source.EMF, group = "VirtualCube") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private SumMeasure measure2;
    private PhysicalCube cube1;
    private Schema databaseSchema;
    private Catalog catalog;
    private VirtualCube vCube;
    private TableSource query;
    private SumMeasure measure1;


    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Cube with virtual cube with dimension references from other cubes
            A virtual cube that combines measures and dimensions from multiple physical cubes into a unified analytical view.
            Virtual cubes enable cross-cube analysis by creating a logical integration layer over existing physical cubes,
            allowing users to analyze related metrics from different business processes in a single query
            Catalog have two physical cubes Cube1 and Cube2 and virtual cube Cube1Cube2.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a table that stores all the data.
            The table is named `Fact` uses for Cube1 and contains two columns: `KEY` and `VALUE`.
            The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;


    private static final String measure1Body = """
            Measure use `C1_Fact` table `VALUE` column with sum aggregation in Cube1.
    """;

    private static final String measure2Body = """
            Measure use `Fact` table `VALUE` column with sum aggregation in Cube2.
    """;

    private static final String cube1Body = """
            In this example uses cube with fact table `Fact` as data.
            """;

    private static final String cube2Body = """
            In this example uses cube with fact table Fact as data.
            """;

    private static final String vCubeBody = """
            Virtual cube uses mesures from Cube1 and Cube2. Virtual cube has references for them.
            Also virtual cube has references to dimensions from Cube1 and Cube2
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("MeasureCube1");
        measure1.setColumn(valueColumn);

        measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("MeasureCube2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("HierarchyWithoutHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector1.setForeignKey(keyColumn);
        dimensionConnector1.setDimension(dimension);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Cube2Dimension1");
        dimensionConnector2.setForeignKey(keyColumn);
        dimensionConnector2.setDimension(dimension);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query);
        cube1.getDimensionConnectors().add(dimensionConnector1);
        cube1.getMeasureGroups().add(measureGroup1);
        dimensionConnector1.setPhysicalCube(cube1);

        PhysicalCube cube2 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setQuery(query);
        cube2.getDimensionConnectors().add(dimensionConnector2);
        cube2.getMeasureGroups().add(measureGroup2);
        dimensionConnector2.setPhysicalCube(cube2);

        vCube = CubeFactory.eINSTANCE.createVirtualCube();
        vCube.setName("Cube1Cube2");
        vCube.setDefaultMeasure(measure1);
        vCube.getDimensionConnectors().addAll(List.of(dimensionConnector1, dimensionConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Virtual Cube Dimensions");
        catalog.setDescription("Dimension configuration in virtual cubes");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        catalog.getDbschemas().add(databaseSchema);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Virtual Cube Dimensions", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("MeasureCube1", measure1Body, 1, 4, 0, measure1, 2),
                        new DocSection("MeasureCube2", measure2Body, 1, 5, 0, measure2, 2),
                        new DocSection("Cube1", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("Cube1", cube2Body, 1, 7, 0, cube1, 2),
                        new DocSection("VirtualCubeMeasureOnly", vCubeBody, 1, 7, 0, vCube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
