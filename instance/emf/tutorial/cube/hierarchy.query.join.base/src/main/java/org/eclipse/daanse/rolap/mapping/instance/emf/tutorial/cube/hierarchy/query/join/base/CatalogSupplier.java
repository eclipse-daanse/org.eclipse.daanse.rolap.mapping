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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.join.base;

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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.3.1", source = Source.EMF)//NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            If the database structure follows the Third Normal Form (3NF), hierarchies in a cube are not stored in a single table but are distributed across multiple tables.

            For example, consider a Geographical hierarchy with the levels Town and Country. If each entity is stored in a separate table, with a primary-foreign key relationship linking them, a query must be defined that incorporates both tables and specifies how the levels are joined.

            The following example demonstrates how to define such a query.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on three tables: Fact, Town, and Country.

            - The Fact table contains measures and a reference to the Town table.
            - The Fact table is linked to the Town table through the TOWN_ID column, which corresponds to the ID column in the Town table.
            - The Town table includes a column that references the primary key of the Country table.
            - The Country table consists of two columns: ID (primary key) and Name.

            This structure ensures that the hierarchy is properly normalized, following the Third Normal Form (3NF).
            """;

    private static final String levelTownBody = """
            The Level uses the column attribute to specify the primary key column. Additionally, it defines the nameColumn attribute to specify the column that contains the name of the level.
            """;

    private static final String levelCountryBody = """
            The Country level follows the same pattern as the Town level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists of two levels: Town and Country.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used. The dimension has set the attribute `foreignKey` to define the column that contains the foreign key of the dimension in the fact table.
            """;

    private static final String queryLevelTownBody = """
            The TableQuery for the Town level directly references the physical Town table.
            """;

    private static final String queryLevelCountryBody = """
            The TableQuery for the Country level directly references the physical Country table.
            """;

    private static final String queryJoinBody = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Town), the join uses the foreign key.
            - In the upper-level table (Country), the join uses the primary key.
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
        queryLevelTown.setTable(tableTown);

        TableQuery queryLevelCountry = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevelCountry.setId("_query_LevelCountry");
        queryLevelCountry.setTable(tableCountry);

        JoinedQueryElement joinQueryElementTown = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementTown.setQuery(queryLevelTown);
        joinQueryElementTown.setKey(keyTownColumn);

        JoinedQueryElement joinQueryElementCountry = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementCountry.setQuery(queryLevelCountry);
        joinQueryElementCountry.setKey(keyCountryColumn);

        JoinQuery queryJoinTownToCountry = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoinTownToCountry.setId("_query_LevelTownToCountry");
        queryJoinTownToCountry.setLeft(joinQueryElementTown);
        queryJoinTownToCountry.setRight(joinQueryElementCountry);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
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
        document(queryLevelTown, "Query - Level Town", queryLevelTownBody, 1, 2, 0, true, 2);
        document(queryLevelCountry, "Query - Level Country", queryLevelCountryBody, 1, 3, 0, true, 2);
        document(queryJoinTownToCountry, "Query - Join Town to Country", queryJoinBody, 1, 4, 0, true, 2);

        document(queryFact, "Query Fact", queryFactBody, 1, 5, 0, true, 2);

        document(levelTown, "Level - Town", levelTownBody, 1, 6, 0, true, 0);
        document(levelCounty, "Level - Country", levelCountryBody, 1, 7, 0, true, 0);

        document(hierarchy, "Hierarchy", hierarchyBody, 1, 8, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 9, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
