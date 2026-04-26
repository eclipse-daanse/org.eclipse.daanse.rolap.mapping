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
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.03.01", source = Source.EMF, group = "Hierarchy")//NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource queryLevelTown;
    private TableSource queryLevelCountry;
    private Level levelCounty;
    private JoinSource queryJoinTownToCountry;
    private Level levelTown;
    private TableSource queryFact;


    private static final String introBody = """
            If the database structure follows the Third Normal Form (3NF), hierarchies in a cube are not stored in a single table but are distributed across multiple tables.

            For example, consider a Geographical hierarchy with the levels Town and Country. If each entity is stored in a separate table, with a primary-foreign key relationship linking them, a query must be defined that incorporates both tables and specifies how the levels are joined.

            The following example demonstrates how to define such a query.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on three tables: `Fact`, `Town`, and `Country`.

            - The `Fact` table contains measures and a reference to the `Town` table.
            - The `Fact` table is linked to the `Town` table through the TOWN_ID column, which corresponds to the `ID` column in the `Town` table.
            - The `Town` table includes a column that references the primary key of the `Country` table.
            - The Country table consists of two columns: ID (primary key) and Name.

            This structure ensures that the hierarchy is properly normalized, following the Third Normal Form (3NF).
            """;

    private static final String levelTownBody = """
            The `Town` level uses the column attribute to specify the primary key column. Additionally, it defines the nameColumn attribute to specify the column that contains the name of the level.
            """;

    private static final String levelCountryBody = """
            The `Country` level follows the same pattern as the `Town` level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists of two levels: `Town` and `Country`.
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
            The TableSource for the Town level directly references the physical Town table.
            """;

    private static final String queryLevelCountryBody = """
            The TableSource for the Country level directly references the physical Country table.
            """;

    private static final String queryJoinBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Town), the join uses the foreign key.
            - In the upper-level table (Country), the join uses the primary key.
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

        Column keyTownColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyTownColumn.setName("ID");
        keyTownColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameTownColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameTownColumn.setName("NAME");
        nameTownColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column idTownCountryIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        idTownCountryIdColumn.setName("COUNTRY_ID");
        idTownCountryIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTown.setName("Town");
        tableTown.getFeature().addAll(List.of(keyTownColumn, nameTownColumn, idTownCountryIdColumn));
        databaseSchema.getOwnedElement().add(tableTown);

        Column keyCountryColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyCountryColumn.setName("ID");
        keyCountryColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameCountryColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameCountryColumn.setName("NAME");
        nameCountryColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableCountry = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableCountry.setName("Country");
        tableCountry.getFeature().addAll(List.of(keyCountryColumn, nameCountryColumn));
        databaseSchema.getOwnedElement().add(tableCountry);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(table);

        queryLevelTown = SourceFactory.eINSTANCE.createTableSource();
        queryLevelTown.setTable(tableTown);

        queryLevelCountry = SourceFactory.eINSTANCE.createTableSource();
        queryLevelCountry.setTable(tableCountry);

        JoinedQueryElement joinQueryElementTown = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementTown.setQuery(queryLevelTown);
        joinQueryElementTown.setKey(idTownCountryIdColumn);

        JoinedQueryElement joinQueryElementCountry = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryElementCountry.setQuery(queryLevelCountry);
        joinQueryElementCountry.setKey(keyCountryColumn);

        queryJoinTownToCountry = SourceFactory.eINSTANCE.createJoinSource();
        queryJoinTownToCountry.setLeft(joinQueryElementTown);
        queryJoinTownToCountry.setRight(joinQueryElementCountry);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(keyTownColumn);
        levelTown.setNameColumn(nameTownColumn);

        levelCounty = LevelFactory.eINSTANCE.createLevel();
        levelCounty.setName("County");
        levelCounty.setColumn(keyCountryColumn);
        levelCounty.setNameColumn(nameCountryColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setPrimaryKey(keyTownColumn);
        hierarchy.setQuery(queryJoinTownToCountry);
        hierarchy.getLevels().add(levelTown);
        hierarchy.getLevels().add(levelCounty);

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
        catalog.setName("Daanse Tutorial - Hierarchy Query Join Base");
        catalog.setDescription("Basic hierarchy with joined tables");
        catalog.getCubes().add(cube);




            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Hierarchy Query Join Base", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query - Level Town", queryLevelTownBody, 1, 2, 0, queryLevelTown, 2),
                        new DocSection("Query - Level Country", queryLevelCountryBody, 1, 3, 0, queryLevelCountry, 2),
                        new DocSection("Query - Join Town to Country", queryJoinBody, 1, 4, 0, queryJoinTownToCountry, 2),
                        new DocSection("Query Fact", queryFactBody, 1, 5, 0, queryFact, 2),
                        new DocSection("Level - Town", levelTownBody, 1, 6, 0, levelTown, 0),
                        new DocSection("Level - Country", levelCountryBody, 1, 7, 0, levelCounty, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 8, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cube and DimensionConnector and Measure", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
