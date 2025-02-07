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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.accessallnonecustom;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Enviroment;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RollupPolicy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SchemaAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_25_with_role_with_access_all_none_custom";
    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
    Cube with examples of roles with access (all, none, custom)
    role4 use SchemaGrant, CubeGrant, HierarchyGrant, MemberGrant
    Rollup policy: (Full. Partial. Hidden.)
    Full. The total for that member includes all children. This is the default policy if you don't specify the rollupPolicy attribute.
    Partial. The total for that member includes only accessible children.
    Hidden. If any of the children are inaccessible, the total is hidden.
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query1.setTable(table);

        TableQuery query2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query2.setTable(table);

        TableQuery queryh = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryh.setTable(table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1");
        measure1.setColumn(valueColumn);


        MeasureGroup measureGroupC1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        MeasureGroup measureGroupC2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("Level2");
        level2.setColumn(keyColumn);
        level2.setColumnType(ColumnDataType.STRING);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(queryh);
        hierarchy.getLevels().add(level2);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnectorCube11 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube12 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube12.setOverrideDimensionName("Dimension2");
        dimensionConnectorCube12.setDimension(dimension1);
        dimensionConnectorCube12.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube2.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube2.setDimension(dimension1);
        dimensionConnectorCube2.setForeignKey(keyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId(CUBE1);
        cube1.setQuery(query1);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId(CUBE2);
        cube2.setQuery(query2);
        cube2.getMeasureGroups().add(measureGroupC2);
        cube2.getDimensionConnectors().add(dimensionConnectorCube2);

        AccessCubeGrant cube2GrantRole1 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube2GrantRole1.setCube(cube2);
        cube2GrantRole1.setCubeAccess(CubeAccess.NONE);

        AccessSchemaGrant accessSchemaGrantRole1 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole1.setSchemaAccess(SchemaAccess.ALL);
        accessSchemaGrantRole1.getCubeGrants().addAll(List.of(cube2GrantRole1));

        AccessRole role1 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role1.setName("role1");
        role1.getAccessSchemaGrants().add(accessSchemaGrantRole1);


        AccessCubeGrant cube1GrantRole11 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1GrantRole11.setCube(cube1);
        cube1GrantRole11.setCubeAccess(CubeAccess.ALL);

        AccessSchemaGrant accessSchemaGrantRole11 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole11.setSchemaAccess(SchemaAccess.NONE);
        accessSchemaGrantRole11.getCubeGrants().addAll(List.of(cube1GrantRole11));

        AccessRole role11 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role11.setName("role11");
        role11.getAccessSchemaGrants().add(accessSchemaGrantRole11);

        AccessCubeGrant cube2GrantRole12 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube2GrantRole12.setCube(cube2);
        cube2GrantRole12.setCubeAccess(CubeAccess.ALL);

        AccessSchemaGrant accessSchemaGrantRole12 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole12.setSchemaAccess(SchemaAccess.NONE);
        accessSchemaGrantRole12.getCubeGrants().addAll(List.of(cube2GrantRole12));

        AccessRole role12 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role12.setName("role12");
        role12.getAccessSchemaGrants().add(accessSchemaGrantRole12);

        AccessSchemaGrant accessSchemaGrantRole2 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole2.setSchemaAccess(SchemaAccess.ALL);

        AccessRole role2 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role2.setName("role2");
        role2.getAccessSchemaGrants().add(accessSchemaGrantRole2);

        AccessSchemaGrant accessSchemaGrantRole3 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole3.setSchemaAccess(SchemaAccess.NONE);

        AccessRole role3 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role3.setName("role3");
        role3.getAccessSchemaGrants().add(accessSchemaGrantRole2);

        AccessMemberGrant mg1Role4 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg1Role4.setMemberAccess(MemberAccess.ALL);
        mg1Role4.setMember("[Dimension1].[A]");

        AccessMemberGrant mg2Role4 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg2Role4.setMemberAccess(MemberAccess.NONE);
        mg2Role4.setMember("[Dimension1].[B]");

        AccessHierarchyGrant hierarchyGrantRole4 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrantRole4.setHierarchy(hierarchy);
        hierarchyGrantRole4.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrantRole4.setTopLevel(level2);
        hierarchyGrantRole4.getMemberGrants().addAll(List.of(mg1Role4, mg2Role4));

        AccessCubeGrant cube1GrantRole4 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1GrantRole4.setCube(cube1);
        cube1GrantRole4.setCubeAccess(CubeAccess.ALL);
        cube1GrantRole4.getHierarchyGrants().add(hierarchyGrantRole4);

        AccessSchemaGrant accessSchemaGrantRole4 = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRole4.setSchemaAccess(SchemaAccess.NONE);
        accessSchemaGrantRole4.getCubeGrants().add(cube1GrantRole4);

        AccessRole role4 = RolapMappingFactory.eINSTANCE.createAccessRole();
        role4.setName("role3");
        role4.getAccessSchemaGrants().add(accessSchemaGrantRole4);

        AccessMemberGrant mg1RoleManager1 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg1RoleManager1.setMemberAccess(MemberAccess.ALL);
        mg1RoleManager1.setMember("[Dimension1].[A]");

        AccessMemberGrant mg2RoleManager1 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg2RoleManager1.setMemberAccess(MemberAccess.NONE);
        mg2RoleManager1.setMember("[Dimension1].[B]");

        AccessHierarchyGrant hierarchyGrantRoleManager1 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrantRoleManager1.setHierarchy(hierarchy);
        hierarchyGrantRoleManager1.setRollupPolicy(RollupPolicy.PARTIAL);
        hierarchyGrantRoleManager1.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrantRoleManager1.setTopLevel(level2);
        hierarchyGrantRoleManager1.getMemberGrants().addAll(List.of(mg1RoleManager1, mg2RoleManager1));

        AccessMemberGrant mg1RoleManager2 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg1RoleManager2.setMemberAccess(MemberAccess.ALL);
        mg1RoleManager2.setMember("[Dimension1].[A]");

        AccessMemberGrant mg2RoleManager2 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        mg2RoleManager2.setMemberAccess(MemberAccess.NONE);
        mg2RoleManager2.setMember("[Dimension1].[B]");

        AccessHierarchyGrant hierarchyGrantRoleManager2 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrantRoleManager2.setHierarchy(hierarchy);
        hierarchyGrantRoleManager2.setRollupPolicy(RollupPolicy.FULL);
        hierarchyGrantRoleManager2.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrantRoleManager2.setTopLevel(level2);
        hierarchyGrantRoleManager2.setBottomLevel(level2);
        hierarchyGrantRoleManager2.getMemberGrants().addAll(List.of(mg1RoleManager2, mg2RoleManager2));

        AccessCubeGrant cube1GrantRoleManager = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1GrantRoleManager.setCube(cube1);
        cube1GrantRoleManager.setCubeAccess(CubeAccess.ALL);
        cube1GrantRoleManager.getHierarchyGrants().addAll(List.of(hierarchyGrantRoleManager1, hierarchyGrantRoleManager2));

        AccessSchemaGrant accessSchemaGrantRoleManager = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrantRoleManager.setSchemaAccess(SchemaAccess.NONE);
        accessSchemaGrantRoleManager.getCubeGrants().add(cube1GrantRoleManager);

        AccessRole roleManager = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleManager.setName("manager");
        roleManager.getAccessSchemaGrants().add(accessSchemaGrantRoleManager);

        AccessRole roleU = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleU.setName("role_u");
        roleU.getReferencedAccessRoles().add(role11);
        roleU.getReferencedAccessRoles().add(role12);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_role_access_all_none_custom");
        catalog.setDescription("Schema with role access all, none, custom");
        catalog.getCubes().add(cube1);
        catalog.getCubes().add(cube2);
        catalog.getAccessRoles().addAll(List.of(role1, role11, role12, role2, role3, role4, roleManager, roleU));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
