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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.join;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.2", source = Source.EMF)
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Very often, the data of a cube is not stored in a single table, but in multiple tables. In this case, it must be defined one query for the Facts to store the values that be aggregated for the measures and one for the Levels. This example shows how this must be defined.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on two tables. Fact and Town. The Fact table contains a measures and a reference to the Town table. The Fact table is linked with its ID column to the Town table by the TOWN_ID column.            .
            """;

    private static final String levelBody = """
            The level used the `column` attribute to define the primary key column. It also defines the `nameColumn` attribute to define the column that contains the name of the level. The `nameColumn` attribute is optional, if it is not defined, the server will use the column defined in the `column` attribute as name column.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains only one level. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimenaion has only one hierarchy.
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

        Column keyTownColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyTownColumn.setName("ID");
        keyTownColumn.setId("_col_town_id");
        keyTownColumn.setType(ColumnType.INTEGER);

        Column nameTownColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameTownColumn.setName("NAME");
        nameTownColumn.setId("_col_town_name");
        nameTownColumn.setType(ColumnType.VARCHAR);

        Column idTownCountryIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        idTownCountryIdColumn.setName("COUNTRY_ID");
        idTownCountryIdColumn.setId("_col_town_countryid");
        idTownCountryIdColumn.setType(ColumnType.INTEGER);

        PhysicalTable tableTown = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTown.setName("Town");
        tableTown.setId("_tab_town");
        tableTown.getColumns().addAll(List.of(keyTownColumn, nameTownColumn, idTownCountryIdColumn));
        databaseSchema.getTables().add(tableTown);

        Column keyCountryColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyCountryColumn.setName("ID");
        keyCountryColumn.setId("_col_country_id");
        keyCountryColumn.setType(ColumnType.INTEGER);

        Column nameCountryColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameCountryColumn.setName("NAME");
        nameCountryColumn.setId("_col_country_name");
        nameCountryColumn.setType(ColumnType.VARCHAR);

        PhysicalTable tableCountry = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableCountry.setName("Country");
        tableCountry.setId("_tab_country");
        tableCountry.getColumns().addAll(List.of(keyCountryColumn, nameCountryColumn));
        databaseSchema.getTables().add(tableCountry);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_query_Fact");
        queryFact.setTable(table);

        TableQuery queryLevelTown = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevelTown.setId("_query_LevelTown");
        queryLevelTown.setTable(tableCountry);

        TableQuery queryLevelCountry = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevelCountry.setId("_query_LevelCountry");
        queryLevelCountry.setTable(tableCountry);

        JoinedQueryElement joinQueryElementTown = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementTown.setQuery(queryLevelTown);
        joinQueryElementTown.setKey(keyTownColumn);

        JoinedQueryElement joinQueryElementCountry = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementCountry.setQuery(queryLevelCountry);
        joinQueryElementCountry.setKey(idTownCountryIdColumn);

        JoinQuery queryJoinTownToCountry = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoinTownToCountry.setId("_query_LevelTownToCountry");
        queryJoinTownToCountry.setLeft(joinQueryElementTown);
        queryJoinTownToCountry.setRight(joinQueryElementCountry);

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
        levelTown.setColumn(keyTownColumn);
        levelTown.setNameColumn(nameTownColumn);

        Level levelCounty = RolapMappingFactory.eINSTANCE.createLevel();
        levelCounty.setName("County");
        levelCounty.setId("_level_country");
        levelCounty.setColumn(keyCountryColumn);
        levelCounty.setNameColumn(nameCountryColumn);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_town");
        hierarchy.setPrimaryKey(keyTownColumn);
        hierarchy.setQuery(queryJoinTownToCountry);
        hierarchy.getLevels().add(levelTown);
        hierarchy.getLevels().add(levelCounty);

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
        catalog.setName("Hierarchy - Query based on Join");
        catalog.getCubes().add(cube);

        document(catalog, "Hierarchy - Query based on a Join", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryLevelTown, "Query - Level Town", queryLevelBody, 1, 2, 0, true, 2);
        document(queryLevelCountry, "Query - Level Country", queryLevelBody, 1, 2, 0, true, 2);
        document(queryJoinTownToCountry, "Query - Join Town to Country", queryLevelBody, 1, 2, 0, true, 2);

        document(queryFact, "Query Fact", queryFactBody, 1, 3, 0, true, 2);

        document(levelTown, "Level - Town", levelBody, 1, 4, 0, true, 0);
        document(levelCounty, "Level - Country", levelBody, 1, 4, 0, true, 0);

        document(hierarchy, "Hierarchy", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
