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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.accesshierarchygrand;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_25_3_with_role_with_access_all_dimension_cube_grand_custom";
    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
    Cube with examples of roles with SchemaGrant all_dimensions
    Cube1 - access to "A"
    Cube2 - no access
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valueColumn);


        MeasureGroup measureGroupC1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        MeasureGroup measureGroupC2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("Level2");
        level2.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
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
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId(CUBE2);
        cube2.setQuery(query);
        cube2.getMeasureGroups().add(measureGroupC2);
        cube2.getDimensionConnectors().add(dimensionConnectorCube2);

        AccessDimensionGrant dimensionGrant = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        dimensionGrant.setDimensionAccess(DimensionAccess.ALL);
        dimensionGrant.setDimension(dimension1);

        AccessHierarchyGrant hierarchyGrant = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant.setHierarchy(hierarchy);
        hierarchyGrant.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrant.setTopLevel(level2);
        hierarchyGrant.setBottomLevel(level2);

        AccessMemberGrant memberGrant1 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant1.setMemberAccess(MemberAccess.ALL);
        memberGrant1.setMember("[Dimension1].[A]");

        AccessMemberGrant memberGrant2 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant2.setMemberAccess(MemberAccess.NONE);
        memberGrant2.setMember("[Dimension1].[B]");

        AccessMemberGrant memberGrant3 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant3.setMemberAccess(MemberAccess.NONE);
        memberGrant3.setMember("[Dimension1].[C]");

        hierarchyGrant.getMemberGrants().addAll(List.of(memberGrant1, memberGrant2, memberGrant3));

        AccessCubeGrant cube1Grant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1Grant.setCube(cube1);
        cube1Grant.getDimensionGrants().add(dimensionGrant);
        cube1Grant.getHierarchyGrants().addAll(List.of(hierarchyGrant));
        cube1Grant.setCubeAccess(CubeAccess.ALL);

        AccessCatalogGrant accessSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessSchemaGrant.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        accessSchemaGrant.getCubeGrants().addAll(List.of(cube1Grant));

        AccessRole role = RolapMappingFactory.eINSTANCE.createAccessRole();
        role.setName("role1");
        role.setId("role1");
        role.getAccessCatalogGrants().add(accessSchemaGrant);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_access_all_dimension_cube1_access_to_A_only");
        catalog.setDescription("Schema with access all dimension with cube1 access to A only");
        catalog.getCubes().add(cube1);
        catalog.getCubes().add(cube2);
        catalog.getAccessRoles().add(role);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
