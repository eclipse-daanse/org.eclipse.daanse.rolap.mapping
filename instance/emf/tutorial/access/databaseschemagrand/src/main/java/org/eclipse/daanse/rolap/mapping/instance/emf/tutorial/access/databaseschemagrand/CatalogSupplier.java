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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.databaseschemagrand;

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
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.1", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with DatabaseSchemaGrant.

    - `roleAll` role : use DatabaseSchemaGrant access `all`; (access all database)
    - `roleNone` role: use CatalogGrant access `none`; (no access to database)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String cube1Body = """
            The cube1 is defined by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The `roleAll` use DatabaseSchemaGrant access `all`; (access all database)
            """;

    private static final String roleNoneBody = """
            The `roleNone` use DatabaseSchemaGrant access `none`; (no access to database)
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_DatabaseSchemaGrand");

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

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure1);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube_Cube1");
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroup);

        AccessCubeGrant accessCubeGrant1 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant1.setCube(cube1);
        accessCubeGrant1.setCubeAccess(CubeAccess.ALL);

        AccessCubeGrant accessCubeGrant2 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant2.setCube(cube1);
        accessCubeGrant2.setCubeAccess(CubeAccess.ALL);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantNone = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantNone.setDatabaseSchemaAccess(DatabaseSchemaAccess.NONE);
        accessDatabaseSchemaGrantNone.setDatabaseSchema(databaseSchema);

        AccessCatalogGrant accessCatalogGrantAll = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrantAll.getCubeGrants().add(accessCubeGrant1);

        AccessCatalogGrant accessCatalogGrantNone = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantNone.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantNone.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrantNone);
        accessCatalogGrantNone.getCubeGrants().add(accessCubeGrant2);

        AccessRole roleAll = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleAll.setName("roleAll");
        roleAll.setId("_accessRole_roleAll");
        roleAll.getAccessCatalogGrants().add(accessCatalogGrantAll);

        AccessRole roleNone = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleNone.setName("roleNone");
        roleNone.setId("_accessRole_roleNone");
        roleNone.getAccessCatalogGrants().add(accessCatalogGrantNone);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Database Schema Grant");
        catalog.setDescription("Access control with database schema grants");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(roleAll);
        catalog.getAccessRoles().add(roleNone);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Access Database Schema Grant", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(cube1, "Cube1 with access all", cube1Body, 1, 6, 0, true, 2);

        document(roleAll, "roleAll", roleAllBody, 1, 7, 0, true, 2);

        document(roleNone, "roleNone", roleNoneBody, 1, 8, 0, true, 2);

        return catalog;
    }

}
