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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.levelifparentsname;

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

    private static final String CATALOG = "tutorial_31-02_Level_hideMemberIf_IfParentsName";
    private static final String CUBE = "HiddenMembersIfParentName";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
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

        Column level2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2KeyColumn.setName("KEY");
        level2KeyColumn.setId("Level_2_KEY");
        level2KeyColumn.setType("INTEGER");

        Column level2NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2NameColumn.setName("NAME");
        level2NameColumn.setId("Level_2_NAME");
        level2NameColumn.setType("VARCHAR");

        Column level2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        level2L1KeyColumn.setName("L1_KEY");
        level2L1KeyColumn.setId("Level_2_L1_KEY");
        level2L1KeyColumn.setType("INTEGER");

        PhysicalTable level2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2Table.setName("Level_2");
        level2Table.setId("Level_2");
        level2Table.getColumns().addAll(List.of(level2KeyColumn, level2NameColumn, level2L1KeyColumn));
        databaseSchema.getTables().add(level2Table);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("queryFact");
        queryFact.setTable(factTable);

        TableQuery queryLevel1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel1.setId("queryLevel1");
        queryLevel1.setTable(level1Table);

        TableQuery queryLevel2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryLevel2.setId("queryLevel2");
        queryLevel2.setTable(level2Table);

        JoinedQueryElement queryJoin1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Left.setKey(level2L1KeyColumn);
        queryJoin1Left.setQuery(queryLevel2);

        JoinedQueryElement queryJoin1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        queryJoin1Right.setKey(level1KeyColumn);
        queryJoin1Right.setQuery(queryLevel1);

        JoinQuery queryJoin1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin1.setId("queryJoin1");
        queryJoin1.setLeft(queryJoin1Left);
        queryJoin1.setRight(queryJoin1Right);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        Level hierarchyDimensionMembersHiddenIfParentsNameLevel1 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenIfParentsNameLevel1.setName("Level1");
        hierarchyDimensionMembersHiddenIfParentsNameLevel1.setId("h1Level1");
        hierarchyDimensionMembersHiddenIfParentsNameLevel1.setColumn(level1KeyColumn);
        hierarchyDimensionMembersHiddenIfParentsNameLevel1.setNameColumn(level1NameColumn);
        hierarchyDimensionMembersHiddenIfParentsNameLevel1.setTable(level1Table);

        Level hierarchyDimensionMembersHiddenIfParentsNameLevel2 = RolapMappingFactory.eINSTANCE.createLevel();
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setName("Level2");
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setId("h1Level2");
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setColumn(level2KeyColumn);
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setNameColumn(level2NameColumn);
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setHideMemberIf(HideMemberIf.IF_PARENTS_NAME);
        hierarchyDimensionMembersHiddenIfParentsNameLevel2.setTable(level2Table);

        Hierarchy hierarchyDimensionMembersHiddenIfParentsName = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchyDimensionMembersHiddenIfParentsName.setHasAll(true);
        hierarchyDimensionMembersHiddenIfParentsName.setName("Hierarchy1");
        hierarchyDimensionMembersHiddenIfParentsName.setId("Hierarchy1");
        hierarchyDimensionMembersHiddenIfParentsName.setPrimaryKey(level2KeyColumn);
        hierarchyDimensionMembersHiddenIfParentsName.setPrimaryKeyTable(level2Table);
        hierarchyDimensionMembersHiddenIfParentsName.setQuery(queryJoin1);
        hierarchyDimensionMembersHiddenIfParentsName.getLevels().addAll(List.of(hierarchyDimensionMembersHiddenIfParentsNameLevel1, hierarchyDimensionMembersHiddenIfParentsNameLevel2));

        StandardDimension dimensionMembersHiddenIfParentsName = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMembersHiddenIfParentsName.setName("DimensionMembersHiddenIfParentsName");
        dimensionMembersHiddenIfParentsName.setId("DimensionMembersHiddenIfParentsName");
        dimensionMembersHiddenIfParentsName.getHierarchies().add(hierarchyDimensionMembersHiddenIfParentsName);

        DimensionConnector dimensionMembersHiddenIfParentsNameConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionMembersHiddenIfParentsNameConnector.setOverrideDimensionName("DimensionMembersHiddenIfBlankName");
        dimensionMembersHiddenIfParentsNameConnector.setDimension(dimensionMembersHiddenIfParentsName);
        dimensionMembersHiddenIfParentsNameConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE);
        cube1.setId(CUBE);
        cube1.setQuery(queryFact);
        cube1.getMeasureGroups().add(measureGroup1);
        cube1.getDimensionConnectors().add(dimensionMembersHiddenIfParentsNameConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Single_Hierarchy_Hidden_Members_with_IfParentsName");
        catalog.setDescription("Schema of a minimal cube with single Hierarchy Hidden Members with IfParentsName");
        catalog.getCubes().add(cube1);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
