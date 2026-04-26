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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.table.multilevel.multitable;


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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.02.02", source = Source.EMF, group = "Hierarchy")//NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private Level levelCountry;
    private TableSource queryHier;
    private Level levelTown;
    private TableSource queryFact;


    private static final String introBody = """
        In some cases, a table for a lower-level entity also contains additional information for a higher-level entity.
    This often happens when no dedicated columns exist for the higher-level entity and the database designer decides
    that fully applying Third Normal Form (3NF) would involve more work than it seems to be worth, or they wish to
    optimize lookup speed. Although we strongly recommend using 3NF wherever possible, this tutorial demonstrates
    how to handle a scenario in which two levels share the same table.

    In this example, besides storing the town `ID` and town `NAME`, our table also includes information about the
    `COUNTRY` in a separate column.
    """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on two tables. `Fact` and `Town`. The `Fact` table contains a
            measures and a reference to the `Town` table. The `Fact` table is linked with its `ID` column to the `Town`
            table by the `TOWN_ID` column. The Town table has the `ID`, `NAME` and `COUNTRY`.
            """;

    private static final String levelTownBody = """
            The level  of the `Town` used the `column` attribute to define the primary key column and the `nameColumn`
            attribute.

            """;

    private static final String levelCountryBody = """
            The level  of the `Country` used the `column` attribute to define the primary key column on the `Country`
            table of the `Town` table.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains both defined levels. The `primaryKey` attribute defines the column that contains
            the primary key of the hierarchy. The `query` attribute references to the query that will be used to
            retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used. The dimension has set the attribute
            `foreignKey` to define the column that contains the foreign key of the dimension in the fact table.
            """;

    private static final String queryLevelBody = """
            The TableSource for the Level, as it directly references the physical table `Town`.
            """;

    private static final String queryFactBody = """
            The TableSource for the Level, as it directly references the physical table `Fact`.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column townIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        townIdColumn.setName("TOWN_ID");
        townIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(townIdColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column keyColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumnTown.setName("ID");
        keyColumnTown.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumnTown.setName("NAME");
        nameColumnTown.setType(SqlSimpleTypes.Sql99.varcharType());

        Column keyColumnCountry = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumnCountry.setName("COUNTRY");
        keyColumnCountry.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTown.setName("Town");
        tableTown.getFeature().addAll(List.of(keyColumnTown, nameColumnTown, keyColumnCountry));
        databaseSchema.getOwnedElement().add(tableTown);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(table);

        queryHier = SourceFactory.eINSTANCE.createTableSource();
        queryHier.setTable(tableTown);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(keyColumnTown);
        levelTown.setNameColumn(nameColumnTown);

        levelCountry = LevelFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setColumn(keyColumnCountry);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setPrimaryKey(keyColumnTown);
        hierarchy.setQuery(queryHier);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(townIdColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Query linked Tables");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Multitable");
        catalog.setDescription("Multi-level hierarchy across multiple tables");
        catalog.getCubes().add(cube);



            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Hierarchy Query Table Multilevel Multitable", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Level", queryLevelBody, 1, 2, 0, queryHier, 2),
                        new DocSection("Query Fact", queryFactBody, 1, 3, 0, queryFact, 2),
                        new DocSection("Level", levelTownBody, 1, 4, 0, levelTown, 0),
                        new DocSection("Level", levelCountryBody, 1, 5, 0, levelCountry, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 6, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 7, 0, dimension, 0),
                        new DocSection("Cube and DimensionConnector and Measure", cubeBody, 1, 7, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
