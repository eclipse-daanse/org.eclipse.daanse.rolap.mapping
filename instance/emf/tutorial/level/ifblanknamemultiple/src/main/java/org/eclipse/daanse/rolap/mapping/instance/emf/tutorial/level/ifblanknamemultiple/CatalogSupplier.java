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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.04", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

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
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (Level_2_Multiple), the join uses the foreign key L1_KEY.
            - In the upper-level table (Level_1_Multiple), the join uses the primary key KEY.
            """;

    private static final String queryJoinBody = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_ifblanknamemultiple");

        Column factMultipleDimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factMultipleDimKeyColumn.setName("DIM_KEY");
        factMultipleDimKeyColumn.setId("_column_factmultiple_dim_key");
        factMultipleDimKeyColumn.setType(ColumnType.INTEGER);

        Column factMultipleValueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factMultipleValueColumn.setName("VALUE");
        factMultipleValueColumn.setId("_column_fact_value");
        factMultipleValueColumn.setType(ColumnType.INTEGER);

        PhysicalTable factMultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factMultipleTable.setName("Fact_Multiple");
        factMultipleTable.setId("_table_fact_Multiple");
        factMultipleTable.getColumns().addAll(List.of(factMultipleDimKeyColumn, factMultipleValueColumn));
        databaseSchema.getTables().add(factMultipleTable);

        Column level1MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1MultipleKeyColumn.setName("KEY");
        level1MultipleKeyColumn.setId("_level_1_multiple_key");
        level1MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level1MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1MultipleNameColumn.setName("NAME");
        level1MultipleNameColumn.setId("_level_1_multiple_name");
        level1MultipleNameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable level1MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1MultipleTable.setName("Level_1_Multiple");
        level1MultipleTable.setId("_level_1_multiple");
        level1MultipleTable.getColumns().addAll(List.of(level1MultipleKeyColumn, level1MultipleNameColumn));
        databaseSchema.getTables().add(level1MultipleTable);

        Column level2MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleKeyColumn.setName("KEY");
        level2MultipleKeyColumn.setId("_level_2_multiple_key");
        level2MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level2MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleNameColumn.setName("NAME");
        level2MultipleNameColumn.setId("_level_2_multiple_name");
        level2MultipleNameColumn.setType(ColumnType.VARCHAR);

        Column level2MultipleL1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleL1KeyColumn.setName("L1_KEY");
        level2MultipleL1KeyColumn.setId("_level_2_multiple_l1_key");
        level2MultipleL1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level2MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2MultipleTable.setName("Level_2_Multiple");
        level2MultipleTable.setId("_level_2_multiple");
        level2MultipleTable.getColumns().addAll(List.of(level2MultipleKeyColumn, level2MultipleNameColumn, level2MultipleL1KeyColumn));
        databaseSchema.getTables().add(level2MultipleTable);

        Column level3MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleKeyColumn.setName("KEY");
        level3MultipleKeyColumn.setId("_level_3_multiple_key");
        level3MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level3MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleNameColumn.setName("NAME");
        level3MultipleNameColumn.setId("_level_3_multiple_name");
        level3MultipleNameColumn.setType(ColumnType.VARCHAR);

        Column level3MultipleL2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleL2KeyColumn.setName("L2_KEY");
        level3MultipleL2KeyColumn.setId("_level_3_multiple_l2_key");
        level3MultipleL2KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level3MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level3MultipleTable.setName("Level_3_Multiple");
        level3MultipleTable.setId("_level_3_multiple");
        level3MultipleTable.getColumns().addAll(List.of(level3MultipleKeyColumn, level3MultipleNameColumn, level3MultipleL2KeyColumn));
        databaseSchema.getTables().add(level3MultipleTable);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_queryFact");
        queryFact.setTable(factMultipleTable);

        TableQuery queryLevel2Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Multiple.setId("_queryLevel2Multiple");
        queryLevel2Multiple.setTable(level2MultipleTable);

        TableQuery queryLevel3Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel3Multiple.setId("_queryLevel3Multiple");
        queryLevel3Multiple.setTable(level3MultipleTable);

        TableQuery queryLevel1Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1Multiple.setId("_queryLevel1Multiple");
        queryLevel1Multiple.setTable(level1MultipleTable);

        JoinedQueryElement queryJoinRightLeftElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightLeftElement.setKey(level2MultipleL1KeyColumn);
        queryJoinRightLeftElement.setQuery(queryLevel2Multiple);

        JoinedQueryElement queryJoinRightRightElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightRightElement.setKey(level1MultipleKeyColumn);
        queryJoinRightRightElement.setQuery(queryLevel1Multiple);

        JoinQuery queryJoinRight = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoinRight.setId("_queryJoinRight");
        queryJoinRight.setLeft(queryJoinRightLeftElement);
        queryJoinRight.setRight(queryJoinRightRightElement);

        JoinedQueryElement queryJoinLeftElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinLeftElement.setKey(level3MultipleL2KeyColumn);
        queryJoinLeftElement.setQuery(queryLevel3Multiple);

        JoinedQueryElement queryJoinRightElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoinRightElement.setKey(level2MultipleKeyColumn);
        queryJoinRightElement.setQuery(queryJoinRight);

        JoinQuery queryJoin = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin.setId("_queryJoin2");
        queryJoin.setLeft(queryJoinLeftElement);
        queryJoin.setRight(queryJoinRightElement);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure");
        measure.setColumn(factMultipleValueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_h2Level1");
        level1.setColumn(level1MultipleKeyColumn);
        level1.setNameColumn(level1MultipleNameColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("_h2Level2");
        level2.setColumn(level2MultipleKeyColumn);
        level2.setNameColumn(level2MultipleNameColumn);
        level2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        Level level3 = RolapMappingFactory.eINSTANCE.createLevel();
        level3.setName("Level3");
        level3.setId("_h2Level3");
        level3.setColumn(level3MultipleKeyColumn);
        level3.setNameColumn(level3MultipleNameColumn);
        level3.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        ExplicitHierarchy hierarchyDimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyDimensionMembersHiddenMultipleLevels.setHasAll(true);
        hierarchyDimensionMembersHiddenMultipleLevels.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenMultipleLevels.setId("_hierarchy1_2");
        hierarchyDimensionMembersHiddenMultipleLevels.setPrimaryKey(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevels.setQuery(queryJoin);
        hierarchyDimensionMembersHiddenMultipleLevels.getLevels().addAll(List.of(level1,
                level2, level3));

        StandardDimension dimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenMultipleLevels.setName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.setId("_dimensionmembershiddenmultiplelevels");
        dimensionMembersHiddenMultipleLevels.getHierarchies().add(hierarchyDimensionMembersHiddenMultipleLevels);

        DimensionConnector dimensionMembersHiddenMultipleLevelsConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenMultipleLevelsConnector.setId("_dc_dimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setOverrideDimensionName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setDimension(dimensionMembersHiddenMultipleLevels);
        dimensionMembersHiddenMultipleLevelsConnector.setForeignKey(factMultipleDimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_hiddenmembersmultiplelevels");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionMembersHiddenMultipleLevelsConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level If Blank Name Multiple");
        catalog.setDescription("Multiple levels handling blank names");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Level If Blank Name Multiple", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryFact, "Query Fact", queryBody, 1, 2, 0, true, 2);
        document(queryLevel1Multiple, "Query Level1", queryLevel1Body, 1, 3, 0, true, 2);
        document(queryLevel2Multiple, "Query Level2", queryLevel2Body, 1, 3, 0, true, 2);
        document(queryLevel3Multiple, "Query Level3", queryLevel3Body, 1, 3, 0, true, 2);
        document(queryJoinRight, "Query Join Right", queryJoinRightBody, 1, 4, 0, true, 2);
        document(queryJoin, "Query Join", queryJoinBody, 1, 4, 0, true, 2);
        document(dimensionMembersHiddenMultipleLevels, "DimensionMembersHiddenMultipleLevels", dimensionBody, 1, 5, 0, true, 2);
        document(hierarchyDimensionMembersHiddenMultipleLevels, "Hierarchy1", hierarchyBody, 1, 6, 0, true, 2);
        document(level1, "Level1", level1Body, 1, 7, 0, true, 2);
        document(level2, "Level2", level2Body, 1, 8, 0, true, 2);
        document(level3, "Level3", level3Body, 1, 8, 0, true, 2);
        document(measure, "Measure1", measure1Body, 1, 9, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
