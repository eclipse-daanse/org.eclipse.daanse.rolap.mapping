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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.identifier;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.6.1", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            In some cases, all data are stored in one table, the fact as well as multiple levels. This Tutorial shows how to handle this case with diferent casses of data.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on only one tables with diferent casses of data
            space, space first, space last, separate line, long with over 100 12345678....   , äüö, some russian latter, some french letters, @€
            """;

    private static final String level1Body = """
            The level of the level1 used the `column` attribute to define the column that holds the name, wich is also the key Column.
            """;

    private static final String level2Body = """
            The level  of the level2 used the `column` attribute to define the column that holds the name, wich is also the key Column.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains both defined levels. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used.
            """;

    private static final String queryFactBody = """
            The TableQuery for the Levels and the Measure.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column columnKey1 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnKey1.setName("KEY1");
        columnKey1.setId("_col_fact_key1");
        columnKey1.setType(ColumnType.VARCHAR);

        Column columnValue = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnValue.setName("VALUE");
        columnValue.setId("_col_fact_value");
        columnValue.setType(ColumnType.INTEGER);

        Column columnKey2 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnKey2.setName("KEY2");
        columnKey2.setId("_col_fact_key2");
        columnKey2.setType(ColumnType.VARCHAR);

        PhysicalTable tableFact = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFact.setName("Fact");
        tableFact.setId("_tab_fact");
        tableFact.getColumns().addAll(List.of(columnKey1,columnKey2, columnValue));
        databaseSchema.getTables().add(tableFact);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(tableFact);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setId("_measure");
        measure.setColumn(columnValue);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("level1");
        level1.setId("_level1");
        level1.setColumn(columnKey1);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("level2");
        level2.setId("_level2");
        level2.setColumn(columnKey2);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy");
        hierarchy.setPrimaryKey(columnKey1);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level1);
        hierarchy.getLevels().add(level2);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dim_dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_dimension");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnKey1);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Catalog with cube with different member identifiers");
        catalog.getCubes().add(cube);

        document(catalog, "Catalog with cube with different member identifiers", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query Fact", queryFactBody, 1, 3, 0, true, 2);

        document(level1, "Level1", level1Body, 1, 4, 0, true, 0);
        document(level2, "Level2", level2Body, 1, 5, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 6, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 7, 0, true, 0);

        document(cube, "Cube with different member identifiers", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
