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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.membergrand;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.access.common.CatalogAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.access.olap.CubeAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.DimensionAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.access.olap.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.access.olap.MemberAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.RollupPolicy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.access.common.CommonFactory;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseFactory;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapFactory;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.04.08", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private PhysicalCube cube1;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private AccessRole role;
    private StandardDimension dimension1;
    private TableSource query;


    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses role with MemberGrant

    role1 role: with member grants <br />
    - [Dimension1].[A] -all;
    - [Dimension1].[B] -none;
    - [Dimension1].[C] -none;
    (Cube1 - access to 'A' only)

    Rollup policy: (Full. Partial. Hidden.)<br />
    - Full. The total for that member includes all children. This is the default policy if you don't specify the rollupPolicy attribute.
    - Partial. The total for that member includes only accessible children.
    - Hidden. If any of the children are inaccessible, the total is hidden.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the `Fact` table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String level1Body = """
            This Example uses one simple Level1 based on the `KEY` column.
            """;

    private static final String hierarchy1Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level2.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy1.
            """;

    private static final String cube1Body = """
            The cube1 is defined by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String role1Body = """
            The `role1` use CatalogGrant access `all`; CubeGrant cube1 access `all`; dimensionGrant dimension1 access `all`;
            hierarchyGrant hierarchy1 access custom with member grants<br />
            [Dimension1].[A] -all,<br />
            [Dimension1].[B] -none,<br />
            [Dimension1].[C] -none;<br />
            (Cube1 - access to "A" Cube2 - no access)
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroupC1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setColumn(keyColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Hierarchy1");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level1);

        dimension1 = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnectorCube11 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube12 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube12.setOverrideDimensionName("Dimension2");
        dimensionConnectorCube12.setDimension(dimension1);
        dimensionConnectorCube12.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube2.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube2.setDimension(dimension1);
        dimensionConnectorCube2.setForeignKey(keyColumn);
        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessDimensionGrant dimensionGrant = OlapFactory.eINSTANCE.createAccessDimensionGrant();
        dimensionGrant.setDimensionAccess(DimensionAccess.ALL);
        dimensionGrant.setDimension(dimension1);

        AccessMemberGrant memberGrant1 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant1.setMemberAccess(MemberAccess.ALL);
        memberGrant1.setMember("[Dimension1].[Hierarchy1].[A]");

        AccessMemberGrant memberGrant2 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant2.setMemberAccess(MemberAccess.NONE);
        memberGrant2.setMember("[Dimension1].[Hierarchy1].[B]");

        AccessMemberGrant memberGrant3 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant3.setMemberAccess(MemberAccess.NONE);
        memberGrant3.setMember("[Dimension1].[Hierarchy1].[C]");

        AccessHierarchyGrant hierarchyGrant0 = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant0.setHierarchyAccess(HierarchyAccess.ALL);

        AccessHierarchyGrant hierarchyGrant = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant.setHierarchy(hierarchy);
        hierarchyGrant.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrant.setTopLevel(level1);
        hierarchyGrant.setBottomLevel(level1);
        hierarchyGrant.setRollupPolicy(RollupPolicy.FULL);
        hierarchyGrant.getMemberGrants().addAll(List.of(memberGrant1, memberGrant2, memberGrant3));

        AccessCubeGrant cube1Grant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        cube1Grant.setCube(cube1);
        cube1Grant.getDimensionGrants().add(dimensionGrant);
        cube1Grant.getHierarchyGrants().addAll(List.of(hierarchyGrant0, hierarchyGrant));
        cube1Grant.setCubeAccess(CubeAccess.CUSTOM);

        AccessCatalogGrant accessCatalogGrant = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrant.getCubeGrants().addAll(List.of(cube1Grant));
        accessCatalogGrant.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        role = CommonFactory.eINSTANCE.createAccessRole();
        role.setName("role1");
        role.getAccessCatalogGrants().add(accessCatalogGrant);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Member Grant");
        catalog.setDescription("Access control with member-level grants");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(role);
        catalog.getDbschemas().add(databaseSchema);




        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Access Member Grant", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level1", level1Body, 1, 3, 0, level1, 0),
                        new DocSection("Hierarchy1 without hasAll Level1", hierarchy1Body, 1, 4, 0, hierarchy, 0),
                        new DocSection("Dimension1", dimension1Body, 1, 5, 0, dimension1, 0),
                        new DocSection("Cube1 with access all", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("Role1", role1Body, 1, 8, 0, role, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
