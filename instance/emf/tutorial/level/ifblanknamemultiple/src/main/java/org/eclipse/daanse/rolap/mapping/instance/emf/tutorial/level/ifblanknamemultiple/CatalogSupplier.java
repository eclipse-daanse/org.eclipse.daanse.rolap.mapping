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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.ifblanknamemultiple;


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

    private JoinSource queryJoinRight;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level level2;
    private PhysicalCube cube;
    private JoinSource queryJoin;
    private Level level3;
    private ExplicitHierarchy hierarchyDimensionMembersHiddenMultipleLevels;
    private TableSource queryLevel3Multiple;
    private TableSource queryLevel1Multiple;
    private SumMeasure measure;
    private StandardDimension dimensionMembersHiddenMultipleLevels;
    private TableSource queryLevel2Multiple;
    private TableSource queryFact;


    private static final String CUBE = "HiddenMembersMultipleLevels";

    private static final String catalogBody = """
            A basic OLAP schema with a level with property Level has attribute HideMemberIf.IF_BLANK_NAME
            Catalog has two cubes with one level with HideMemberIf atribut and with multiple levels.
            Attribute HideMemberIf.IF_BLANK_NAME uses for multiple levels
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a 4 tables that stores all the data.
            - The phisical table is named `Fact_Multiple` uses for Cube1 and contains two columns: `DIM_KEY` and `VALUE`.
            The `DIM_KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            - The phisical table is named `Level_1_Multiple` uses for Level1 and contains 2 columns: `KEY`, `NAME` .
            - The phisical table is named `Level_2_Multiple` uses for Level2 and contains 3 columns: `KEY`, `NAME`, `L1_KEY`.
            - The phisical table is named `Level_3_Multiple` uses for Level2 and contains 3 columns: `KEY`, `NAME`, `L2_KEY`.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact_Multiple`.
            """;

    private static final String queryLevel1Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Level_1_Multiple`.
            """;

    private static final String queryLevel2Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Level_2_Multiple`.
            """;

    private static final String queryLevel3Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Level_3_Multiple`.
            """;

    private static final String queryJoinRightBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Level_2_Multiple), the join uses the foreign key L1_KEY.
            - In the upper-level table (Level_1_Multiple), the join uses the primary key KEY.
            """;

    private static final String queryJoinBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Level_3_Multiple), the join uses the foreign key L2_KEY.
            - In the upper-level queryJoinRight, the join uses the primary key KEY from Level_2_Multiple.
            """;

    private static final String level1Body = """
            The Level uses the column attribute to specify the primary key `KEY` from `Level_1_Multiple`.
            Additionally, it defines the nameColumn `NAME` from `Level_1_Multiple` attribute  to specify
            the column that contains the name of the level.
            """;

    private static final String level2Body = """
            The Level uses the column attribute to specify the primary key `KEY` from `Level_2_Multiple`.
            Additionally, it defines the nameColumn `NAME` from `Level_2_Multiple` attribute  to specify
            the column that contains the name of the level.
            Level has  attribute HideMemberIf.IF_PARENTS_NAME
            Hide members whose name matches their parent member's name.
            Eliminates redundant display where child members have identical names to their parents in the hierarchy.
            """;

    private static final String level3Body = """
            The Level uses the column attribute to specify the primary key `KEY` from `Level_3_Multiple`.
            Additionally, it defines the nameColumn `NAME` from `Level_3_Multiple` attribute  to specify
            the column that contains the name of the level.
            Level has  attribute HideMemberIf.IF_PARENTS_NAME
            Hide members whose name matches their parent member's name.
            Eliminates redundant display where child members have identical names to their parents in the hierarchy.
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

        Column factMultipleDimKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factMultipleDimKeyColumn.setName("DIM_KEY");
        factMultipleDimKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column factMultipleValueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factMultipleValueColumn.setName("VALUE");
        factMultipleValueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table factMultipleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factMultipleTable.setName("Fact_Multiple");
        factMultipleTable.getFeature().addAll(List.of(factMultipleDimKeyColumn, factMultipleValueColumn));
        databaseSchema.getOwnedElement().add(factMultipleTable);

        Column level1MultipleKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level1MultipleKeyColumn.setName("KEY");
        level1MultipleKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column level1MultipleNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level1MultipleNameColumn.setName("NAME");
        level1MultipleNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table level1MultipleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        level1MultipleTable.setName("Level_1_Multiple");
        level1MultipleTable.getFeature().addAll(List.of(level1MultipleKeyColumn, level1MultipleNameColumn));
        databaseSchema.getOwnedElement().add(level1MultipleTable);

        Column level2MultipleKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2MultipleKeyColumn.setName("KEY");
        level2MultipleKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column level2MultipleNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2MultipleNameColumn.setName("NAME");
        level2MultipleNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column level2MultipleL1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level2MultipleL1KeyColumn.setName("L1_KEY");
        level2MultipleL1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table level2MultipleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        level2MultipleTable.setName("Level_2_Multiple");
        level2MultipleTable.getFeature().addAll(List.of(level2MultipleKeyColumn, level2MultipleNameColumn, level2MultipleL1KeyColumn));
        databaseSchema.getOwnedElement().add(level2MultipleTable);

        Column level3MultipleKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level3MultipleKeyColumn.setName("KEY");
        level3MultipleKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column level3MultipleNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level3MultipleNameColumn.setName("NAME");
        level3MultipleNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column level3MultipleL2KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        level3MultipleL2KeyColumn.setName("L2_KEY");
        level3MultipleL2KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table level3MultipleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        level3MultipleTable.setName("Level_3_Multiple");
        level3MultipleTable.getFeature().addAll(List.of(level3MultipleKeyColumn, level3MultipleNameColumn, level3MultipleL2KeyColumn));
        databaseSchema.getOwnedElement().add(level3MultipleTable);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(factMultipleTable);

        queryLevel2Multiple = SourceFactory.eINSTANCE.createTableSource();
        queryLevel2Multiple.setTable(level2MultipleTable);

        queryLevel3Multiple = SourceFactory.eINSTANCE.createTableSource();
        queryLevel3Multiple.setTable(level3MultipleTable);

        queryLevel1Multiple = SourceFactory.eINSTANCE.createTableSource();
        queryLevel1Multiple.setTable(level1MultipleTable);

        JoinedQueryElement queryJoinRightLeftElement = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightLeftElement.setKey(level2MultipleL1KeyColumn);
        queryJoinRightLeftElement.setQuery(queryLevel2Multiple);

        JoinedQueryElement queryJoinRightRightElement = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightRightElement.setKey(level1MultipleKeyColumn);
        queryJoinRightRightElement.setQuery(queryLevel1Multiple);

        queryJoinRight = SourceFactory.eINSTANCE.createJoinSource();
        queryJoinRight.setLeft(queryJoinRightLeftElement);
        queryJoinRight.setRight(queryJoinRightRightElement);

        JoinedQueryElement queryJoinLeftElement = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinLeftElement.setKey(level3MultipleL2KeyColumn);
        queryJoinLeftElement.setQuery(queryLevel3Multiple);

        JoinedQueryElement queryJoinRightElement = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightElement.setKey(level2MultipleKeyColumn);
        queryJoinRightElement.setQuery(queryJoinRight);

        queryJoin = SourceFactory.eINSTANCE.createJoinSource();
        queryJoin.setLeft(queryJoinLeftElement);
        queryJoin.setRight(queryJoinRightElement);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(factMultipleValueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setColumn(level1MultipleKeyColumn);
        level1.setNameColumn(level1MultipleNameColumn);

        level2 = LevelFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setColumn(level2MultipleKeyColumn);
        level2.setNameColumn(level2MultipleNameColumn);
        level2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        level3 = LevelFactory.eINSTANCE.createLevel();
        level3.setName("Level3");
        level3.setColumn(level3MultipleKeyColumn);
        level3.setNameColumn(level3MultipleNameColumn);
        level3.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        hierarchyDimensionMembersHiddenMultipleLevels = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyDimensionMembersHiddenMultipleLevels.setHasAll(true);
        hierarchyDimensionMembersHiddenMultipleLevels.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenMultipleLevels.setPrimaryKey(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevels.setQuery(queryJoin);
        hierarchyDimensionMembersHiddenMultipleLevels.getLevels().addAll(List.of(level1,
                level2, level3));

        dimensionMembersHiddenMultipleLevels = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenMultipleLevels.setName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.getHierarchies().add(hierarchyDimensionMembersHiddenMultipleLevels);

        DimensionConnector dimensionMembersHiddenMultipleLevelsConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenMultipleLevelsConnector.setOverrideDimensionName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setDimension(dimensionMembersHiddenMultipleLevels);
        dimensionMembersHiddenMultipleLevelsConnector.setForeignKey(factMultipleDimKeyColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionMembersHiddenMultipleLevelsConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level If Blank Name Multiple");
        catalog.setDescription("Multiple levels handling blank names");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level If Blank Name Multiple", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Query Level1", queryLevel1Body, 1, 3, 0, queryLevel1Multiple, 2),
                        new DocSection("Query Level2", queryLevel2Body, 1, 3, 0, queryLevel2Multiple, 2),
                        new DocSection("Query Level3", queryLevel3Body, 1, 3, 0, queryLevel3Multiple, 2),
                        new DocSection("Query Join Right", queryJoinRightBody, 1, 4, 0, queryJoinRight, 2),
                        new DocSection("Query Join", queryJoinBody, 1, 4, 0, queryJoin, 2),
                        new DocSection("DimensionMembersHiddenMultipleLevels", dimensionBody, 1, 5, 0, dimensionMembersHiddenMultipleLevels, 2),
                        new DocSection("Hierarchy1", hierarchyBody, 1, 6, 0, hierarchyDimensionMembersHiddenMultipleLevels, 2),
                        new DocSection("Level1", level1Body, 1, 7, 0, level1, 2),
                        new DocSection("Level2", level2Body, 1, 8, 0, level2, 2),
                        new DocSection("Level3", level3Body, 1, 8, 0, level3, 2),
                        new DocSection("Measure1", measure1Body, 1, 9, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
