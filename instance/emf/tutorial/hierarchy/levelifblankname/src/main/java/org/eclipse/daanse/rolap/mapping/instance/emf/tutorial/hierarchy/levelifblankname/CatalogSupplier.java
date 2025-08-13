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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.levelifblankname;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.3", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "HiddenMembersIfBlankName";
    private static final String CUBE2 = "HiddenMembersMultipleLevels";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A basic OLAP schema with a level with property Level has attribute HideMemberIf.IF_BLANK_NAME
            Catalog has two cubes with one level with HideMemberIf atribut and with multiple levels
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_Fact_DIM_KEY");
        dimKeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable factTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factTable.setName(FACT);
        factTable.setId("_Fact");
        factTable.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(factTable);

        Column factMultipleDimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factMultipleDimKeyColumn.setName("DIM_KEY");
        factMultipleDimKeyColumn.setId("_FactMultiple_DIM_KEY");
        factMultipleDimKeyColumn.setType(ColumnType.INTEGER);

        Column factMultipleValueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factMultipleValueColumn.setName("VALUE");
        factMultipleValueColumn.setId("_Fact_VALUE");
        factMultipleValueColumn.setType(ColumnType.INTEGER);

        PhysicalTable factMultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factMultipleTable.setName("Fact_Multiple");
        factMultipleTable.setId("_Fact_Multiple");
        factMultipleTable.getColumns().addAll(List.of(factMultipleDimKeyColumn, factMultipleValueColumn));
        databaseSchema.getTables().add(factMultipleTable);

        Column level1MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1MultipleKeyColumn.setName("KEY");
        level1MultipleKeyColumn.setId("_Level_1_Multiple_KEY");
        level1MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level1MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1MultipleNameColumn.setName("NAME");
        level1MultipleNameColumn.setId("_Level_1_Multiple_NAME");
        level1MultipleNameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable level1MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1MultipleTable.setName("Level_1_Multiple");
        level1MultipleTable.setId("_Level_1_Multiple");
        level1MultipleTable.getColumns().addAll(List.of(level1MultipleKeyColumn, level1MultipleNameColumn));
        databaseSchema.getTables().add(level1MultipleTable);

        Column level2NullKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullKeyColumn.setName("KEY");
        level2NullKeyColumn.setId("_Level_2_NULL_KEY");
        level2NullKeyColumn.setType(ColumnType.INTEGER);

        Column level2NullNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullNameColumn.setName("NAME");
        level2NullNameColumn.setId("_Level_2_NULL_NAME");
        level2NullNameColumn.setType(ColumnType.VARCHAR);

        Column level2NullL1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2NullL1KeyColumn.setName("L1_KEY");
        level2NullL1KeyColumn.setId("_Level_2_NULL_L1_KEY");
        level2NullL1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level2NullTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2NullTable.setName("Level_2_NULL");
        level2NullTable.setId("_Level_2_NULL");
        level2NullTable.getColumns().addAll(List.of(level2NullKeyColumn, level2NullNameColumn, level2NullL1KeyColumn));
        databaseSchema.getTables().add(level2NullTable);

        Column level1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1KeyColumn.setName("KEY");
        level1KeyColumn.setId("_Level_1_KEY");
        level1KeyColumn.setType(ColumnType.INTEGER);

        Column level1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level1NameColumn.setName("NAME");
        level1NameColumn.setId("_Level_1_NAME");
        level1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable level1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1Table.setName("Level_1");
        level1Table.setId("_Level_1");
        level1Table.getColumns().addAll(List.of(level1KeyColumn, level1NameColumn));
        databaseSchema.getTables().add(level1Table);

        Column level2MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleKeyColumn.setName("KEY");
        level2MultipleKeyColumn.setId("_Level_2_Multiple_KEY");
        level2MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level2MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleNameColumn.setName("NAME");
        level2MultipleNameColumn.setId("_Level_2_Multiple_NAME");
        level2MultipleNameColumn.setType(ColumnType.VARCHAR);

        Column level2MultipleL1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level2MultipleL1KeyColumn.setName("L1_KEY");
        level2MultipleL1KeyColumn.setId("_Level_2_Multiple_L1_KEY");
        level2MultipleL1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level2MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2MultipleTable.setName("Level_2_Multiple");
        level2MultipleTable.setId("_Level_2_Multiple");
        level2MultipleTable.getColumns().addAll(List.of(level2MultipleKeyColumn, level2MultipleNameColumn, level2MultipleL1KeyColumn));
        databaseSchema.getTables().add(level2MultipleTable);


        Column level3MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleKeyColumn.setName("KEY");
        level3MultipleKeyColumn.setId("_Level_3_Multiple_KEY");
        level3MultipleKeyColumn.setType(ColumnType.INTEGER);

        Column level3MultipleNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleNameColumn.setName("NAME");
        level3MultipleNameColumn.setId("_Level_3_Multiple_NAME");
        level3MultipleNameColumn.setType(ColumnType.VARCHAR);

        Column level3MultipleL2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        level3MultipleL2KeyColumn.setName("L2_KEY");
        level3MultipleL2KeyColumn.setId("_Level_3_Multiple_L2_KEY");
        level3MultipleL2KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable level3MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level3MultipleTable.setName("Level_3_Multiple");
        level3MultipleTable.setId("_Level_3_Multiple");
        level3MultipleTable.getColumns().addAll(List.of(level3MultipleKeyColumn, level3MultipleNameColumn, level3MultipleL2KeyColumn));
        databaseSchema.getTables().add(level3MultipleTable);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_queryFact");
        queryFact.setTable(factTable);

        TableQuery queryFactMultiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFactMultiple.setId("_queryFactMultiple");
        queryFactMultiple.setTable(factMultipleTable);

        TableQuery queryLevel2Null = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Null.setId("_queryLevel2Null");
        queryLevel2Null.setTable(level2NullTable);

        TableQuery queryLevel1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1.setId("_queryLevel1");
        queryLevel1.setTable(level1Table);

        TableQuery queryLevel2Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Multiple.setId("_queryLevel2Multiple");
        queryLevel2Multiple.setTable(level2MultipleTable);

        TableQuery queryLevel3Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel3Multiple.setId("_queryLevel3Multiple");
        queryLevel3Multiple.setTable(level3MultipleTable);

        TableQuery queryLevel1Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1Multiple.setId("_queryLevel1Multiple");
        queryLevel1Multiple.setTable(level1MultipleTable);

        JoinedQueryElement queryJoin1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Left.setKey(level2NullL1KeyColumn);
        queryJoin1Left.setQuery(queryLevel2Null);

        JoinedQueryElement queryJoin1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Right.setKey(level1KeyColumn);
        queryJoin1Right.setQuery(queryLevel1);

        JoinQuery queryJoin1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin1.setId("_queryJoin1");
        queryJoin1.setLeft(queryJoin1Left);
        queryJoin1.setRight(queryJoin1Right);

        JoinedQueryElement queryJoin2RightLeftElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2RightLeftElement.setKey(level2MultipleL1KeyColumn);
        queryJoin2RightLeftElement.setQuery(queryLevel2Multiple);

        JoinedQueryElement queryJoin2RightRightElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2RightRightElement.setKey(level1MultipleKeyColumn);
        queryJoin2RightRightElement.setQuery(queryLevel1Multiple);

        JoinQuery queryJoin2Right = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin2Right.setId("_queryJoin2Right");
        queryJoin2Right.setLeft(queryJoin2RightLeftElement);
        queryJoin2Right.setRight(queryJoin2RightRightElement);

        JoinedQueryElement queryJoin2LeftElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2LeftElement.setKey(level3MultipleL2KeyColumn);
        queryJoin2LeftElement.setQuery(queryLevel3Multiple);

        JoinedQueryElement queryJoin2RightElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2RightElement.setKey(level1MultipleKeyColumn);
        queryJoin2RightElement.setQuery(queryJoin2Right);

        JoinQuery queryJoin2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin2.setId("_queryJoin2");
        queryJoin2.setLeft(queryJoin2LeftElement);
        queryJoin2.setRight(queryJoin2RightElement);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_Measure1");
        measure1.setColumn(valueColumn);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2");
        measure2.setId("_Measure2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

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
        hierarchyDimensionMembersHiddenIfBlankName.setId("_Hierarchy1_1");
        hierarchyDimensionMembersHiddenIfBlankName.setPrimaryKey(level2NullKeyColumn);
        hierarchyDimensionMembersHiddenIfBlankName.setQuery(queryJoin1);
        hierarchyDimensionMembersHiddenIfBlankName.getLevels().addAll(List.of(hierarchyDdimensionMembersHiddenIfBlankNameLevel1, hierarchyDdimensionMembersHiddenIfBlankNameLevel2));

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel1 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setName("Level1");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setId("_h2Level1");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setColumn(level1MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setNameColumn(level1MultipleNameColumn);

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel2 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setName("Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setId("_h2Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setColumn(level2MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setNameColumn(level2MultipleNameColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel3 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setName("Level3");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setId("_h2Level3");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setColumn(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setNameColumn(level3MultipleNameColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);

        ExplicitHierarchy hierarchyDimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyDimensionMembersHiddenMultipleLevels.setHasAll(true);
        hierarchyDimensionMembersHiddenMultipleLevels.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenMultipleLevels.setId("_Hierarchy1_2");
        hierarchyDimensionMembersHiddenMultipleLevels.setPrimaryKey(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevels.setQuery(queryJoin2);
        hierarchyDimensionMembersHiddenMultipleLevels.getLevels().addAll(List.of(hierarchyDimensionMembersHiddenMultipleLevelsLevel1,
                hierarchyDimensionMembersHiddenMultipleLevelsLevel2, hierarchyDimensionMembersHiddenMultipleLevelsLevel3));

        StandardDimension dimensionMembersHiddenIfBlankName = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenIfBlankName.setName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.setId("_DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.getHierarchies().add(hierarchyDimensionMembersHiddenIfBlankName);

        StandardDimension dimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenMultipleLevels.setName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.setId("_DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.getHierarchies().add(hierarchyDimensionMembersHiddenMultipleLevels);

        DimensionConnector dimensionMembersHiddenIfBlankNameConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenIfBlankNameConnector.setId("_dc_dimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setOverrideDimensionName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setDimension(dimensionMembersHiddenIfBlankName);
        dimensionMembersHiddenIfBlankNameConnector.setForeignKey(dimKeyColumn);

        DimensionConnector dimensionMembersHiddenMultipleLevelsConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenMultipleLevelsConnector.setId("_dc_dimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setOverrideDimensionName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setDimension(dimensionMembersHiddenMultipleLevels);
        dimensionMembersHiddenMultipleLevelsConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_HiddenMembersIfBlankName");
        cube1.setQuery(queryFact);
        cube1.getMeasureGroups().add(measureGroup1);
        cube1.getDimensionConnectors().add(dimensionMembersHiddenIfBlankNameConnector);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId("_HiddenMembersMultipleLevels");
        cube2.setQuery(queryFact);
        cube2.getMeasureGroups().add(measureGroup2);
        cube2.getDimensionConnectors().add(dimensionMembersHiddenMultipleLevelsConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Hierarchy Hidden Members with IfBlankName");
        catalog.setDescription("Schema of a minimal cube with single Hierarchy Hidden Members with IfBlankName");
        catalog.getCubes().add(cube1);
        catalog.getCubes().add(cube2);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
