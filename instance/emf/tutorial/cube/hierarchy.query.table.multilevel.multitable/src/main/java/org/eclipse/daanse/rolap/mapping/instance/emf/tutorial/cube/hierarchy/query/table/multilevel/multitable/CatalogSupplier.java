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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.2.2", source = Source.EMF)//NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            In some cases, a table for a lower-level entity also contains additional information for a higher-level entity. This often happens when no dedicated columns exist for the higher-level entity and the database designer decides that fully applying Third Normal Form (3NF) would involve more work than it seems to be worth, or they wish to optimize lookup speed. Although we strongly recommend using 3NF wherever possible, this tutorial demonstrates how to handle a scenario in which two levels share the same table.

In this example, besides storing the town ID and town NAME, our table also includes information about the COUNTRY in a separate column.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on two tables. Fact and Town. The Fact table contains a measures and a reference to the Town table. The Fact table is linked with its ID column to the Town table by the TOWN_ID column. The Town table has the ID, NAME and COUNTRY.
            """;

    private static final String levelTownBody = """
            The level  of the Town used the `column` attribute to define the primary key column and the `nameColumn` attribute.


            """;

    private static final String levelCountryBody = """
            The level  of the Country used the `column` attribute to define the primary key column on the Country table of the Town table.
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

            To connect the dimension to the cube, a DimensionConnector is used. The dimension has set the attribute `foreignKey` to define the column that contains the foreign key of the dimension in the fact table.
            """;

    private static final String queryLevelBody = """
            The TableQuery for the Level, as it directly references the physical table `Town`.
            """;

    private static final String queryFactBody = """
            The TableQuery for the Level, as it directly references the physical table `Fact`.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column townIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        townIdColumn.setName("TOWN_ID");
        townIdColumn.setId("_col_fact_townId");
        townIdColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab_fact");
        table.getColumns().addAll(List.of(townIdColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column keyColumnTown = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumnTown.setName("ID");
        keyColumnTown.setId("_col_town_id");
        keyColumnTown.setType(ColumnType.INTEGER);

        Column nameColumnTown = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumnTown.setName("NAME");
        nameColumnTown.setId("_col_town_name");
        nameColumnTown.setType(ColumnType.VARCHAR);

        Column keyColumnCountry = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumnCountry.setName("COUNTRY");
        keyColumnCountry.setId("_col_country");
        keyColumnCountry.setType(ColumnType.VARCHAR);

        PhysicalTable tableTown = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTown.setName("Town");
        tableTown.setId("_tab_town");
        tableTown.getColumns().addAll(List.of(keyColumnTown, nameColumnTown, keyColumnCountry));
        databaseSchema.getTables().add(tableTown);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_query_Fact");
        queryFact.setTable(table);

        TableQuery queryHier = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHier.setId("_Query_LevelTown");
        queryHier.setTable(tableTown);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("theMeasure");
        measure.setId("_measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelTown = RolapMappingFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setId("_level_town");
        levelTown.setColumn(keyColumnTown);
        levelTown.setNameColumn(nameColumnTown);

        Level levelCountry = RolapMappingFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setId("_level_country");
        levelCountry.setColumn(keyColumnCountry);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_town");
        hierarchy.setPrimaryKey(keyColumnTown);
        hierarchy.setQuery(queryHier);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.setId("_dim_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(townIdColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Query linked Tables");
        cube.setId("_cube");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Hierarchy - Query based on a Table with 2 Levels");
        catalog.getCubes().add(cube);

        document(catalog, "Hierarchy - Query based on a Table with 2 Levels", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryHier, "Query Level", queryLevelBody, 1, 2, 0, true, 2);
        document(queryFact, "Query Fact", queryFactBody, 1, 3, 0, true, 2);

        document(levelTown, "Level", levelTownBody, 1, 4, 0, true, 0);
        document(levelCountry, "Level", levelCountryBody, 1, 5, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 6, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 7, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
