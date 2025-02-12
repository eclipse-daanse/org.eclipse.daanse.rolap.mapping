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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.levelifblankname;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
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
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_31-01_Level_hideMemberIf_IfBlankName";
    private static final String CUBE1 = "HiddenMembersIfBlankName";
    private static final String CUBE2 = "HiddenMembersIfBlankName";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A basic OLAP schema with a level with property

            Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
            This example schema contains one cube named "Cube".

            A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
            In this case the database table representing the fact table is named "Fact" in the database, which is addressed in the name attribute within the <Table> tag.

            Each measurement of the cube is defined in a separate <Measure> element.
            The measurement in this example cube is named "Measure" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
            Level is defined in <Level> element.
            Property is defined in <Property> element inside <Level> element. Property we can see in cell tooltip in excel
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("Fact_DIM_KEY");
        dimKeyColumn.setType("INTEGER");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable factTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factTable.setName(FACT);
        factTable.setId(FACT);
        factTable.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(factTable);

        Column factMultipleDimKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        factMultipleDimKeyColumn.setName("DIM_KEY");
        factMultipleDimKeyColumn.setId("FactMultiple_DIM_KEY");
        factMultipleDimKeyColumn.setType("INTEGER");

        Column factMultipleValueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        factMultipleValueColumn.setName("VALUE");
        factMultipleValueColumn.setId("Fact_VALUE");
        factMultipleValueColumn.setType("INTEGER");

        PhysicalTable factMultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factMultipleTable.setName("Fact_Multiple");
        factMultipleTable.setId("Fact_Multiple");
        factMultipleTable.getColumns().addAll(List.of(factMultipleDimKeyColumn, factMultipleValueColumn));
        databaseSchema.getTables().add(factMultipleTable);

        Column level1MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level1MultipleKeyColumn.setName("KEY");
        level1MultipleKeyColumn.setId("Level_1_Multiple_KEY");
        level1MultipleKeyColumn.setType("INTEGER");

        Column level1MultipleNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level1MultipleNameColumn.setName("NAME");
        level1MultipleNameColumn.setId("Level_1_Multiple_NAME");
        level1MultipleNameColumn.setType("VARCHAR");

        PhysicalTable level1MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1MultipleTable.setName("Level_1_Multiple");
        level1MultipleTable.setId("Level_1_Multiple");
        level1MultipleTable.getColumns().addAll(List.of(level1MultipleKeyColumn, level1MultipleNameColumn));
        databaseSchema.getTables().add(level1MultipleTable);

        Column level2NullKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2NullKeyColumn.setName("KEY");
        level2NullKeyColumn.setId("Level_2_NULL_KEY");
        level2NullKeyColumn.setType("INTEGER");

        Column level2NullNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2NullNameColumn.setName("NAME");
        level2NullNameColumn.setId("Level_2_NULL_NAME");
        level2NullNameColumn.setType("VARCHAR");

        Column level2NullL1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2NullL1KeyColumn.setName("L1_KEY");
        level2NullL1KeyColumn.setId("Level_2_NULL_L1_KEY");
        level2NullL1KeyColumn.setType("INTEGER");

        PhysicalTable level2NullTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2NullTable.setName("Level_2_NULL");
        level2NullTable.setId("Level_2_NULL");
        level2NullTable.getColumns().addAll(List.of(level2NullKeyColumn, level2NullNameColumn, level2NullL1KeyColumn));
        databaseSchema.getTables().add(level2NullTable);

        Column level1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level1KeyColumn.setName("KEY");
        level1KeyColumn.setId("Level_1_KEY");
        level1KeyColumn.setType("INTEGER");

        Column level1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level1NameColumn.setName("NAME");
        level1NameColumn.setId("Level_1_NAME");
        level1NameColumn.setType("VARCHAR");

        PhysicalTable level1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1Table.setName("Level_1");
        level1Table.setId("Level_1");
        level1Table.getColumns().addAll(List.of(level1KeyColumn, level1NameColumn));
        databaseSchema.getTables().add(level1Table);

        Column level2MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2MultipleKeyColumn.setName("KEY");
        level2MultipleKeyColumn.setId("Level_2_Multiple_KEY");
        level2MultipleKeyColumn.setType("INTEGER");

        Column level2MultipleNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2MultipleNameColumn.setName("NAME");
        level2MultipleNameColumn.setId("Level_2_Multiple_NAME");
        level2MultipleNameColumn.setType("VARCHAR");

        Column level2MultipleL1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2MultipleL1KeyColumn.setName("L1_KEY");
        level2MultipleL1KeyColumn.setId("Level_2_Multiple_L1_KEY");
        level2MultipleL1KeyColumn.setType("INTEGER");

        PhysicalTable level2MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2MultipleTable.setName("Level_2_Multiple");
        level2MultipleTable.setId("Level_2_Multiple");
        level2MultipleTable.getColumns().addAll(List.of(level2MultipleKeyColumn, level2MultipleNameColumn, level2MultipleL1KeyColumn));
        databaseSchema.getTables().add(level2MultipleTable);


        Column level3MultipleKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level3MultipleKeyColumn.setName("KEY");
        level3MultipleKeyColumn.setId("Level_3_Multiple_KEY");
        level3MultipleKeyColumn.setType("INTEGER");

        Column level3MultipleNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level3MultipleNameColumn.setName("NAME");
        level3MultipleNameColumn.setId("Level_3_Multiple_NAME");
        level3MultipleNameColumn.setType("VARCHAR");

        Column level3MultipleL2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level3MultipleL2KeyColumn.setName("L2_KEY");
        level3MultipleL2KeyColumn.setId("Level_3_Multiple_L2_KEY");
        level3MultipleL2KeyColumn.setType("INTEGER");

        PhysicalTable level3MultipleTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level3MultipleTable.setName("Level_3_Multiple");
        level3MultipleTable.setId("Level_3_Multiple");
        level3MultipleTable.getColumns().addAll(List.of(level3MultipleKeyColumn, level3MultipleNameColumn, level3MultipleL2KeyColumn));
        databaseSchema.getTables().add(level3MultipleTable);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("queryFact");
        queryFact.setTable(factTable);

        TableQuery queryFactMultiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFactMultiple.setId("queryFactMultiple");
        queryFactMultiple.setTable(factMultipleTable);

        TableQuery queryLevel2Null = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Null.setId("queryLevel2Null");
        queryLevel2Null.setTable(level2NullTable);

        TableQuery queryLevel1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1.setId("queryLevel1");
        queryLevel1.setTable(level1Table);

        TableQuery queryLevel2Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2Multiple.setId("queryLevel2Multiple");
        queryLevel2Multiple.setTable(level2MultipleTable);

        TableQuery queryLevel3Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel3Multiple.setId("queryLevel3Multiple");
        queryLevel3Multiple.setTable(level3MultipleTable);

        TableQuery queryLevel1Multiple = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1Multiple.setId("queryLevel1Multiple");
        queryLevel1Multiple.setTable(level1MultipleTable);

        JoinedQueryElement queryJoin1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Left.setKey(level2NullL1KeyColumn);
        queryJoin1Left.setQuery(queryLevel2Null);

        JoinedQueryElement queryJoin1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Right.setKey(level1KeyColumn);
        queryJoin1Right.setQuery(queryLevel1);

        JoinQuery queryJoin1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin1.setId("queryJoin1");
        queryJoin1.setLeft(queryJoin1Left);
        queryJoin1.setRight(queryJoin1Right);

        JoinedQueryElement queryJoin2Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2Left.setKey(level2MultipleL1KeyColumn);
        queryJoin2Left.setQuery(queryLevel2Multiple);

        JoinedQueryElement queryJoin2Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin2Right.setKey(level1MultipleKeyColumn);
        queryJoin2Right.setQuery(queryLevel1Multiple);

        JoinQuery queryJoin2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin2.setId("queryJoin2");
        queryJoin2.setLeft(queryJoin2Left);
        queryJoin2.setRight(queryJoin2Right);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valueColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("Measure1");
        measure2.setId("Measure1");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        Level hierarchyDdimensionMembersHiddenIfBlankNameLevel1 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setName("Level1");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setId("h1Level1");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setColumn(level1KeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setNameColumn(level1NameColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel1.setTable(level1Table);

        Level hierarchyDdimensionMembersHiddenIfBlankNameLevel2 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setName("Level2");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setId("h1Level2");
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setColumn(level2NullKeyColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setNameColumn(level2NullNameColumn);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        hierarchyDdimensionMembersHiddenIfBlankNameLevel2.setTable(level2NullTable);

        Hierarchy hierarchyDimensionMembersHiddenIfBlankName = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchyDimensionMembersHiddenIfBlankName.setHasAll(true);
        hierarchyDimensionMembersHiddenIfBlankName.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenIfBlankName.setId("Hierarchy1_1");
        hierarchyDimensionMembersHiddenIfBlankName.setPrimaryKey(level2NullKeyColumn);
        hierarchyDimensionMembersHiddenIfBlankName.setPrimaryKeyTable(level2NullTable);
        hierarchyDimensionMembersHiddenIfBlankName.setQuery(queryJoin1);
        hierarchyDimensionMembersHiddenIfBlankName.getLevels().addAll(List.of(hierarchyDdimensionMembersHiddenIfBlankNameLevel1, hierarchyDdimensionMembersHiddenIfBlankNameLevel2));

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel1 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setName("Level1");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setId("h2Level1");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setColumn(level1MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setNameColumn(level1MultipleNameColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel1.setTable(level1MultipleTable);

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel2 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setName("Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setId("h2Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setColumn(level2MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setNameColumn(level2MultipleNameColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel2.setTable(level2MultipleTable);

        Level hierarchyDimensionMembersHiddenMultipleLevelsLevel3 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setName("Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setId("h2Level2");
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setColumn(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setNameColumn(level3MultipleNameColumn);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        hierarchyDimensionMembersHiddenMultipleLevelsLevel3.setTable(level3MultipleTable);

        Hierarchy hierarchyDimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchyDimensionMembersHiddenMultipleLevels.setHasAll(true);
        hierarchyDimensionMembersHiddenMultipleLevels.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenMultipleLevels.setId("Hierarchy1_2");
        hierarchyDimensionMembersHiddenMultipleLevels.setPrimaryKey(level3MultipleKeyColumn);
        hierarchyDimensionMembersHiddenMultipleLevels.setPrimaryKeyTable(level3MultipleTable);
        hierarchyDimensionMembersHiddenMultipleLevels.setQuery(queryJoin2);
        hierarchyDimensionMembersHiddenMultipleLevels.getLevels().addAll(List.of(hierarchyDimensionMembersHiddenMultipleLevelsLevel1,
                hierarchyDimensionMembersHiddenMultipleLevelsLevel2, hierarchyDimensionMembersHiddenMultipleLevelsLevel3));

        StandardDimension dimensionMembersHiddenIfBlankName = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenIfBlankName.setName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.setId("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankName.getHierarchies().add(hierarchyDimensionMembersHiddenIfBlankName);

        StandardDimension dimensionMembersHiddenMultipleLevels = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenMultipleLevels.setName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.setId("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevels.getHierarchies().add(hierarchyDimensionMembersHiddenMultipleLevels);

        DimensionConnector dimensionMembersHiddenIfBlankNameConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenIfBlankNameConnector.setOverrideDimensionName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfBlankNameConnector.setDimension(dimensionMembersHiddenIfBlankName);
        dimensionMembersHiddenIfBlankNameConnector.setForeignKey(dimKeyColumn);

        DimensionConnector dimensionMembersHiddenMultipleLevelsConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenMultipleLevelsConnector.setOverrideDimensionName("DimensionMembersHiddenMultipleLevels");
        dimensionMembersHiddenMultipleLevelsConnector.setDimension(dimensionMembersHiddenMultipleLevels);
        dimensionMembersHiddenMultipleLevelsConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId(CUBE1);
        cube1.setQuery(queryFact);
        cube1.getMeasureGroups().add(measureGroup1);
        cube1.getDimensionConnectors().add(dimensionMembersHiddenIfBlankNameConnector);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId(CUBE2);
        cube2.setQuery(queryFact);
        cube2.getMeasureGroups().add(measureGroup2);
        cube2.getDimensionConnectors().add(dimensionMembersHiddenMultipleLevelsConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Single_Hierarchy_Hidden_Members_with_IfBlankName");
        catalog.setDescription("Schema of a minimal cube with single Hierarchy Hidden Members with IfBlankName");
        catalog.getCubes().add(cube1);
        catalog.getCubes().add(cube2);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
