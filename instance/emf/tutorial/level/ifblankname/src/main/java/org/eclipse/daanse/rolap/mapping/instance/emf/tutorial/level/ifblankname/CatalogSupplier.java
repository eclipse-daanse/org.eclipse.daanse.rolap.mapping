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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.ifblankname;


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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.HideMemberIf;
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
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.04", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube1;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level hierarchyDdimensionMembersHiddenIfBlankNameLevel1;
    private JoinSource queryJoin;
    private StandardDimension dimensionMembersHiddenIfBlankName;
    private SumMeasure measure1;
    private TableSource queryLevel1;
    private ExplicitHierarchy hierarchyDimensionMembersHiddenIfBlankName;
    private TableSource queryLevel2Null;
    private TableSource queryFact;
    private Level hierarchyDdimensionMembersHiddenIfBlankNameLevel2;


    private static final String CUBE1 = "HiddenMembersIfBlankName";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            A basic OLAP schema with a level with property Level has attribute HideMemberIf.IF_BLANK_NAME
            Catalog has two cubes with one level with HideMemberIf atribut and with multiple levels
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a 3 tables that stores all the data.
            - The phisical table is named `Fact` uses for Cube1 and contains two columns: `DIM_KEY` and `VALUE`.
            The `DIM_KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            - The phisical table is named `Level_1` uses for Level1 and contains 2 columns: `KEY`, `NAME` .
            - The phisical table is named `Level_2_NULL` uses for Level2 and contains 3 columns: `KEY`, `NAME`, `L1_KEY`.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String queryLevel1Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Level_1`.
            """;

    private static final String queryLevel2Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Level_2_NULL`.
            """;

    private static final String queryJoinBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Level_2_NULL), the join uses the foreign key L1_KEY.
            - In the upper-level table (Level_1), the join uses the primary key KEY.

            """;

    private static final String level1Body = """
            The Level uses the column attribute to specify the primary key `KEY` from `Level_1`.
            Additionally, it defines the nameColumn `NAME` from `Level_1` attribute  to specify
            the column that contains the name of the level.
            """;

    private static final String level2Body = """
            The Level uses the column attribute to specify the primary key `KEY` from `Level_2_NULL`.
            Additionally, it defines the nameColumn `NAME` from `Level_2_NULL` attribute  to specify
            the column that contains the name of the level.
            Level has  attribute HideMemberIf.IF_BLANK_NAME
            Hide members that have blank or null names. Useful for filtering out incomplete data
            where member names are missing from the source system.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists two levels Level1 and Level2.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measure1Body = """
            Measure1 use Fact table VALUE column with sum aggregation in Cube.
    """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column dimKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table factTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factTable.setName(FACT);
        factTable.getFeature().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column level2NullKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2NullKeyColumn.setName("KEY");
        level2NullKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column level2NullNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2NullNameColumn.setName("NAME");
        level2NullNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column level2NullL1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2NullL1KeyColumn.setName("L1_KEY");
        level2NullL1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table level2NullTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        level2NullTable.setName("Level_2_NULL");
        level2NullTable.getFeature().addAll(List.of(level2NullKeyColumn, level2NullNameColumn, level2NullL1KeyColumn));
        databaseSchema.getOwnedElement().add(level2NullTable);

        Column level1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level1KeyColumn.setName("KEY");
        level1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column level1NameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level1NameColumn.setName("NAME");
        level1NameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table level1Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        level1Table.setName("Level_1");
        level1Table.getFeature().addAll(List.of(level1KeyColumn, level1NameColumn));
        databaseSchema.getOwnedElement().add(level1Table);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(factTable);

        queryLevel2Null = SourceFactory.eINSTANCE.createTableSource();
        queryLevel2Null.setTable(level2NullTable);

        queryLevel1 = SourceFactory.eINSTANCE.createTableSource();
        queryLevel1.setTable(level1Table);

        JoinedQueryElement queryJoin1Left = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Left.setKey(level2NullL1KeyColumn);
        queryJoin1Left.setQuery(queryLevel2Null);

        JoinedQueryElement queryJoin1Right = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Right.setKey(level1KeyColumn);
        queryJoin1Right.setQuery(queryLevel1);

        queryJoin = SourceFactory.eINSTANCE.createJoinSource();
        queryJoin.setLeft(queryJoin1Left);
        queryJoin.setRight(queryJoin1Right);

        measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        hierarchyDdimensionMembersHiddenIfBlankNameLevel1 = LevelFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setName("Level1");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setColumn(level1KeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setNameColumn(level1NameColumn);

        hierarchyDdimensionMembersHiddenIfBlankNameLevel2 = LevelFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setName("Level2");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setColumn(level2NullKeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setNameColumn(level2NullNameColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        hierarchyDimensionMembersHiddenIfBlankName = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyDimensionMembersHiddenIfBlankName.setHasAll(true);
        hierarchyDimensionMembersHiddenIfBlankName.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenIfBlankName.setPrimaryKey(level2NullKeyColumn);
        hierarchyDimensionMembersHiddenIfBlankName.setQuery(queryJoin);
        hierarchyDimensionMembersHiddenIfBlankName.getLevels().addAll(List.of(hierarchyDdimensionMembersHiddenIfBlankNameLevel1, hierarchyDdimensionMembersHiddenIfBlankNameLevel2));

        dimensionMembersHiddenIfBlankName = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenIfBlankName.setName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.getHierarchies().add(hierarchyDimensionMembersHiddenIfBlankName);

        DimensionConnector dimensionMembersHiddenIfBlankNameConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenIfBlankNameConnector.setOverrideDimensionName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setDimension(dimensionMembersHiddenIfBlankName);
        dimensionMembersHiddenIfBlankNameConnector.setForeignKey(dimKeyColumn);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(queryFact);
        cube1.getMeasureGroups().add(measureGroup1);
        cube1.getDimensionConnectors().add(dimensionMembersHiddenIfBlankNameConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level If Blank Name");
        catalog.setDescription("Level handling blank names");
        catalog.getCubes().add(cube1);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level If Blank Name", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Query Level1", queryLevel1Body, 1, 3, 0, queryLevel1, 2),
                        new DocSection("Query Level2", queryLevel2Body, 1, 3, 0, queryLevel2Null, 2),
                        new DocSection("Query Join", queryJoinBody, 1, 4, 0, queryJoin, 2),
                        new DocSection("DimensionMembersHiddenIfBlankName", dimensionBody, 1, 5, 0, dimensionMembersHiddenIfBlankName, 2),
                        new DocSection("Hierarchy1", hierarchyBody, 1, 6, 0, hierarchyDimensionMembersHiddenIfBlankName, 2),
                        new DocSection("Level1", level1Body, 1, 7, 0, hierarchyDdimensionMembersHiddenIfBlankNameLevel1, 2),
                        new DocSection("Level2", level2Body, 1, 8, 0, hierarchyDdimensionMembersHiddenIfBlankNameLevel2, 2),
                        new DocSection("Measure1", measure1Body, 1, 9, 0, measure1, 2),
                        new DocSection("Cube", cubeBody, 1, 10, 0, cube1, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
