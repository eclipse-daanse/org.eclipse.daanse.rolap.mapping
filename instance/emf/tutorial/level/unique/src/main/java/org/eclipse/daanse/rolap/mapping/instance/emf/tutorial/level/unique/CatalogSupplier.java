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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.unique;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.5", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "NotUniqueLevelsMembers";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            A basic OLAP schema with a two levels : level building and level rooms.
            Catalog has cube with two levels with not unique data in room level
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a one table that stores all the data.
            - The phisical table is named `Fact` uses for Cube and contains two columns: `KEY`, `BUILDING`, `ROOM` and `VALUE`.
            The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String levelBuildingBody = """
            The Level uses the column attribute to specify the primary key `BUILDING` from `Fact`.
            `BUILDING` column have unique values
            """;

    private static final String levelRoomBody = """
            The Level uses the column attribute to specify the primary key `ROOM` from `Fact`.
            `ROOM` column have not unique values
            """;

    private static final String hierarchyBody = """
            This hierarchy consists two levels Building and Room.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measureBody = """
            Measure use Fact table VALUE column with sum aggregation in Cube.
    """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data.
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_unique");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.INTEGER);

        Column buildingColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        buildingColumn.setName("BUILDING");
        buildingColumn.setId("_column_fact_building");
        buildingColumn.setType(ColumnType.INTEGER);

        Column roomColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        roomColumn.setName("ROOM");
        roomColumn.setId("_column_fact_room");
        roomColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable factTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factTable.setName(FACT);
        factTable.setId("_table_fact");
        factTable.getColumns().addAll(List.of(keyColumn, buildingColumn, roomColumn, valueColumn));
        databaseSchema.getTables().add(factTable);


        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_queryFact");
        queryFact.setTable(factTable);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure);

        Level buildingLevel = RolapMappingFactory.eINSTANCE.createLevel();
        buildingLevel.setName("Building");
        buildingLevel.setId("_level_building");
        buildingLevel.setColumn(buildingColumn);
        buildingLevel.setNameColumn(buildingColumn);
        buildingLevel.setUniqueMembers(true);

        Level roomLevel = RolapMappingFactory.eINSTANCE.createLevel();
        roomLevel.setName("Room");
        roomLevel.setId("_level_room");
        roomLevel.setColumn(roomColumn);
        roomLevel.setNameColumn(roomColumn);
        roomLevel.setUniqueMembers(false);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(queryFact);
        hierarchy.getLevels().addAll(List.of(buildingLevel, roomLevel));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dimension");
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(keyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup1);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level with not unique members");
        catalog.setDescription("Level handling blank names");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Level with not unique members", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryFact, "Query Fact", queryBody, 1, 2, 0, true, 2);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 2);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 6, 0, true, 2);
        document(buildingLevel, "BuildingLevel", levelBuildingBody, 1, 7, 0, true, 2);
        document(roomLevel, "RoomLevel", levelRoomBody, 1, 8, 0, true, 2);
        document(measure, "Measure", measureBody, 1, 9, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
