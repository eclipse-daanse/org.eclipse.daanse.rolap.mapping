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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.cataloggrand;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.CubeAccess;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.4", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with CatalogGrant.

    - `roleAll` role                   : use CatalogGrant access `all`; (access `cube1`)
    - `roleNone` role                  : use CatalogGrant access `none`; (no access `cube1`)
    - `roleAllDimWithCubeGrand` role   : use CatalogGrant access `all_dimensions`; CubeGrant `cube1` access all; `cube2` access none (access `cube1`)
    - `roleAllDimWithoutCubeGrand` role: use CatalogGrant access `all_dimensions` without cube grant (As result is 'no access'. For access need CubeGrant with `custom` or `all`)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the `Fact` table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String levelBody = """
            This Example uses one simple Level1 bases on the `KEY` column.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level1.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy1.
            """;

    private static final String cube1Body = """
            The cube1 is defines by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The roleAll use CatalogGrant access `all`; (access `cube1`)
            """;

    private static final String roleNoneBody = """
            The `roleNone` use CatalogGrant access `none`; (no access `cube1`)
            """;

    private static final String roleAllDimWithCubeGrandBody = """
            The `roleAllDimWithCubeGrand` use CatalogGrant access `all_dimensions`; CubeGrant access `all`; (access `cube1`)
            """;

    private static final String roleAllDimWithoutCubeGrandBody = """
            The `roleAllDimWithoutCubeGrand` use CatalogGrant access `all_dimensions` without CubeGrant; (no access `cube1`)
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_CatalogGrand");

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
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_measure_sum");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroupC1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_level_key");
        level1.setColumn(keyColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(false);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("_hierarchy_main");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(level1);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("_dimension_main");
        dimension1.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnectorCube11 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setId("_dimensionConnector_main");
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube12 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube12.setId("_dimensionConnector_secondary");
        dimensionConnectorCube12.setOverrideDimensionName("Dimension2");
        dimensionConnectorCube12.setDimension(dimension1);
        dimensionConnectorCube12.setForeignKey(keyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube_main");
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessCubeGrant accessCubeGrant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant.setCube(cube1);

        AccessCatalogGrant accessCatalogGrantAll = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrantAll.getCubeGrants().add(accessCubeGrant);

        AccessCatalogGrant accessCatalogGrantNone = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantNone.setCatalogAccess(CatalogAccess.NONE);
        accessCatalogGrantNone.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        AccessCatalogGrant accessCatalogGrantAllDimWithCubeGrand = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAllDimWithCubeGrand.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        accessCatalogGrantAllDimWithCubeGrand.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrantAllDimWithCubeGrand.getCubeGrants().add(accessCubeGrant);

        AccessCatalogGrant accessCatalogGrantAllDimWithoutCubeGrand = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAllDimWithoutCubeGrand.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        accessCatalogGrantAllDimWithoutCubeGrand.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        AccessRole roleAll = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleAll.setName("roleAll");
        roleAll.setId("_accessRole_all");
        roleAll.getAccessCatalogGrants().add(accessCatalogGrantAll);

        AccessRole roleNone = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleNone.setName("roleNone");
        roleNone.setId("_accessRole_none");
        roleNone.getAccessCatalogGrants().add(accessCatalogGrantNone);

        AccessRole roleAllDimWithCubeGrand = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleAllDimWithCubeGrand.setName("roleAllDimWithCubeGrand");
        roleAllDimWithCubeGrand.setId("_accessRole_allDimWithCubeGrand");
        roleAllDimWithCubeGrand.getAccessCatalogGrants().add(accessCatalogGrantAllDimWithCubeGrand);

        AccessRole roleAllDimWithoutCubeGrand = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleAllDimWithoutCubeGrand.setName("roleAllDimWithoutCubeGrand");
        roleAllDimWithoutCubeGrand.setId("_accessRole_allDimWithoutCubeGrand");
        roleAllDimWithoutCubeGrand.getAccessCatalogGrants().add(accessCatalogGrantAllDimWithoutCubeGrand);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Catalog Grant");
        catalog.setDescription("Demonstrates access control with catalog grants and roles");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(roleAll);
        catalog.getAccessRoles().add(roleNone);
        catalog.getAccessRoles().add(roleAllDimWithCubeGrand);
        catalog.getAccessRoles().add(roleAllDimWithoutCubeGrand);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Access Catalog Grant", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level1, "Level1", levelBody, 1, 3, 0, true, 0);

        document(hierarchy1, "Hierarchy1 without hasAll Level1", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension1, "Dimension1", dimension1Body, 1, 5, 0, true, 0);

        document(cube1, "Cube1 with access all", cube1Body, 1, 6, 0, true, 2);

        document(roleAll, "roleAll", roleAllBody, 1, 7, 0, true, 2);

        document(roleNone, "roleNone", roleNoneBody, 1, 8, 0, true, 2);

        document(roleAllDimWithCubeGrand, "roleAllDimWithCubeGrand", roleAllDimWithCubeGrandBody, 1, 9, 0, true, 2);

        document(roleAllDimWithoutCubeGrand, "roleAllDimWithoutCubeGrand", roleAllDimWithoutCubeGrandBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
