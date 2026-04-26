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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
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
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.05", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level roomLevel;
    private PhysicalCube cube;
    private Level buildingLevel;
    private SumMeasure measure;
    private TableSource queryFact;


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
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column buildingColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        buildingColumn.setName("BUILDING");
        buildingColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column roomColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        roomColumn.setName("ROOM");
        roomColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table factTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factTable.setName(FACT);
        factTable.getFeature().addAll(List.of(keyColumn, buildingColumn, roomColumn, valueColumn));
        databaseSchema.getOwnedElement().add(factTable);


        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(factTable);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure);

        buildingLevel = LevelFactory.eINSTANCE.createLevel();
        buildingLevel.setName("Building");
        buildingLevel.setColumn(buildingColumn);
        buildingLevel.setNameColumn(buildingColumn);
        buildingLevel.setUniqueMembers(true);

        roomLevel = LevelFactory.eINSTANCE.createLevel();
        roomLevel.setName("Room");
        roomLevel.setColumn(roomColumn);
        roomLevel.setNameColumn(roomColumn);
        roomLevel.setUniqueMembers(false);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(queryFact);
        hierarchy.getLevels().addAll(List.of(buildingLevel, roomLevel));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(keyColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup1);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level with not unique members");
        catalog.setDescription("Level handling blank names");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level with not unique members", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Dimension", dimensionBody, 1, 5, 0, dimension, 2),
                        new DocSection("Hierarchy", hierarchyBody, 1, 6, 0, hierarchy, 2),
                        new DocSection("BuildingLevel", levelBuildingBody, 1, 7, 0, buildingLevel, 2),
                        new DocSection("RoomLevel", levelRoomBody, 1, 8, 0, roomLevel, 2),
                        new DocSection("Measure", measureBody, 1, 9, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
