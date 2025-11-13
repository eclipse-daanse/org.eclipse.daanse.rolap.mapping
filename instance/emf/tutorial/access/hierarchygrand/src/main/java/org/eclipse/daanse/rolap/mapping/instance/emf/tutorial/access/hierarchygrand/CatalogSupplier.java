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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.hierarchygrand;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.CubeAccess;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.DimensionAccess;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.HierarchyAccess;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.7", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses role with HierarchyGrant

    - `role1` role:   use HierarchyGrant hierarchy1 access `all` hierarchy2 access `none`;
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the `Fact` table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String level2Body = """
            This Example uses one simple Level2 bases on the KEY column.
            """;

    private static final String hierarchy1Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level2.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy1.
            """;

    private static final String cube1Body = """
            The cube1 is defines by the DimensionConnector1 and the MeasureGroup with measure with aggregation sum.
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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_HierarchyGrand");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_factQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_measure_Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroupC1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_level_Level1");
        level1.setColumn(keyColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("_level_Level2");
        level2.setColumn(keyColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("_hierarchy_Hierarchy1");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(level1);

        ExplicitHierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setId("_hierarchy_Hierarchy2");
        hierarchy2.setPrimaryKey(keyColumn);
        hierarchy2.setQuery(query);
        hierarchy2.getLevels().add(level2);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("_dimension_Dimension1");
        dimension1.getHierarchies().add(hierarchy1);
        dimension1.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnectorCube11 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setId("_dimensionConnector_dimension1");
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube_Cube1");
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11));

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessDimensionGrant dimensionGrant = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        dimensionGrant.setDimensionAccess(DimensionAccess.CUSTOM);
        dimensionGrant.setDimension(dimension1);

        AccessHierarchyGrant hierarchyGrant0 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant0.setHierarchyAccess(HierarchyAccess.ALL);

        AccessHierarchyGrant hierarchyGrant1 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant1.setHierarchy(hierarchy1);
        hierarchyGrant1.setHierarchyAccess(HierarchyAccess.ALL);
        hierarchyGrant1.setTopLevel(level1);
        hierarchyGrant1.setBottomLevel(level1);

        AccessHierarchyGrant hierarchyGrant2 = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant2.setHierarchy(hierarchy2);
        hierarchyGrant2.setHierarchyAccess(HierarchyAccess.NONE);
        hierarchyGrant2.setTopLevel(level2);
        hierarchyGrant2.setBottomLevel(level2);

        AccessCubeGrant cube1Grant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1Grant.setCube(cube1);
        cube1Grant.getDimensionGrants().add(dimensionGrant);
        cube1Grant.getHierarchyGrants().addAll(List.of(hierarchyGrant0, hierarchyGrant1, hierarchyGrant2));
        cube1Grant.setCubeAccess(CubeAccess.CUSTOM);

        AccessCatalogGrant accessCatalogGrant = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant.setCatalogAccess(CatalogAccess.CUSTOM);
        accessCatalogGrant.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrant.getCubeGrants().addAll(List.of(cube1Grant));

        AccessRole role = RolapMappingFactory.eINSTANCE.createAccessRole();
        role.setName("role1");
        role.setId("_accessRole_role1");
        role.getAccessCatalogGrants().add(accessCatalogGrant);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Hierarchy Grant");
        catalog.setDescription("Access control with hierarchy-level grants");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(role);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Access Hierarchy Grant", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level1, "Level2", level2Body, 1, 3, 0, true, 0);

        document(hierarchy1, "Hierarchy1 without hasAll Level1", hierarchy1Body, 1, 4, 0, true, 0);
        document(dimension1, "Dimension1", dimension1Body, 1, 5, 0, true, 0);

        document(cube1, "Cube1 with access all", cube1Body, 1, 6, 0, true, 2);

        document(role, "Role1", role1Body, 1, 8, 0, true, 2);

        return catalog;
    }

}
