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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.4", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

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
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_ifblankname");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_column_fact_dim_key");
        dimKeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable factTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factTable.setName(FACT);
        factTable.setId("_table_fact");
        factTable.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(factTable);

        Column level2NullKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullKeyColumn.setName("KEY");
        level2NullKeyColumn.setId("_level_2_null_key");
        level2NullKeyColumn.setType(ColumnType.INTEGER);

        Column level2NullNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullNameColumn.setName("NAME");
        level2NullNameColumn.setId("_level_2_null_name");
        level2NullNameColumn.setType(ColumnType.VARCHAR);

        Column level2NullL1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullL1KeyColumn.setName("L1_KEY");
        level2NullL1KeyColumn.setId("_level_2_null_l1_key");
        level2NullL1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level2NullTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2NullTable.setName("Level_2_NULL");
        level2NullTable.setId("_level_2_null");
        level2NullTable.getColumns().addAll(List.of(level2NullKeyColumn, level2NullNameColumn, level2NullL1KeyColumn));
        databaseSchema.getTables().add(level2NullTable);

        Column level1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1KeyColumn.setName("KEY");
        level1KeyColumn.setId("_level_1_key");
        level1KeyColumn.setType(ColumnType.INTEGER);

        Column level1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1NameColumn.setName("NAME");
        level1NameColumn.setId("_level_1_name");
        level1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable level1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1Table.setName("Level_1");
        level1Table.setId("_level_1");
        level1Table.getColumns().addAll(List.of(level1KeyColumn, level1NameColumn));
        databaseSchema.getTables().add(level1Table);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_queryFact");
        queryFact.setTable(factTable);

        TableQuery queryLevel2Null = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Null.setId("_queryLevel2Null");
        queryLevel2Null.setTable(level2NullTable);

        TableQuery queryLevel1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1.setId("_queryLevel1");
        queryLevel1.setTable(level1Table);

        JoinedQueryElement queryJoin1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Left.setKey(level2NullL1KeyColumn);
        queryJoin1Left.setQuery(queryLevel2Null);

        JoinedQueryElement queryJoin1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Right.setKey(level1KeyColumn);
        queryJoin1Right.setQuery(queryLevel1);

        JoinQuery queryJoin = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin.setId("_queryJoin");
        queryJoin.setLeft(queryJoin1Left);
        queryJoin.setRight(queryJoin1Right);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        Level hierarchyDdimensionMembersHiddenIfBlankNameLevel1 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setName("Level1");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setId("_h1Level1");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setColumn(level1KeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setNameColumn(level1NameColumn);

        Level hierarchyDdimensionMembersHiddenIfBlankNameLevel2 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setName("Level2");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setId("_h1Level2");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setColumn(level2NullKeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setNameColumn(level2NullNameColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        ExplicitHierarchy hierarchyDimensionMembersHiddenIfBlankName = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyDimensionMembersHiddenIfBlankName.setHasAll(true);
        hierarchyDimensionMembersHiddenIfBlankName.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenIfBlankName.setId("_hierarchy1_1");
        hierarchyDimensionMembersHiddenIfBlankName.setPrimaryKey(level2NullKeyColumn);
        hierarchyDimensionMembersHiddenIfBlankName.setQuery(queryJoin);
        hierarchyDimensionMembersHiddenIfBlankName.getLevels().addAll(List.of(hierarchyDdimensionMembersHiddenIfBlankNameLevel1, hierarchyDdimensionMembersHiddenIfBlankNameLevel2));

        StandardDimension dimensionMembersHiddenIfBlankName = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenIfBlankName.setName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.setId("_dimensionmembershiddenifblankname");
        dimensionMembersHiddenIfBlankName.getHierarchies().add(hierarchyDimensionMembersHiddenIfBlankName);

        DimensionConnector dimensionMembersHiddenIfBlankNameConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenIfBlankNameConnector.setId("_dc_dimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setOverrideDimensionName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setDimension(dimensionMembersHiddenIfBlankName);
        dimensionMembersHiddenIfBlankNameConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_hiddenmembersifblankname");
        cube1.setQuery(queryFact);
        cube1.getMeasureGroups().add(measureGroup1);
        cube1.getDimensionConnectors().add(dimensionMembersHiddenIfBlankNameConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level If Blank Name");
        catalog.setDescription("Level handling blank names");
        catalog.getCubes().add(cube1);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Level If Blank Name", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryFact, "Query Fact", queryBody, 1, 2, 0, true, 2);
        document(queryLevel1, "Query Level1", queryLevel1Body, 1, 3, 0, true, 2);
        document(queryLevel2Null, "Query Level2", queryLevel2Body, 1, 3, 0, true, 2);
        document(queryJoin, "Query Join", queryJoinBody, 1, 4, 0, true, 2);
        document(dimensionMembersHiddenIfBlankName, "DimensionMembersHiddenIfBlankName", dimensionBody, 1, 5, 0, true, 2);
        document(hierarchyDimensionMembersHiddenIfBlankName, "Hierarchy1", hierarchyBody, 1, 6, 0, true, 2);
        document(hierarchyDdimensionMembersHiddenIfBlankNameLevel1, "Level1", level1Body, 1, 7, 0, true, 2);
        document(hierarchyDdimensionMembersHiddenIfBlankNameLevel2, "Level2", level2Body, 1, 8, 0, true, 2);
        document(measure1, "Measure1", measure1Body, 1, 9, 0, true, 2);
        document(cube1, "Cube", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
