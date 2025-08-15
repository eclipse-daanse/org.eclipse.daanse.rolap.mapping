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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.columngrand;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessColumnGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessTableGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.3", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with ColumnGrant.

    roleAll    role: use TableGrant access all; (access all database all tables all columns)
    roleNone   role: use TableGrant access none; (no access to database columns)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: KEY and VALUE. The KEY column is used as the discriminator in the the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String cube1Body = """
            The cube1 is defines by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The roleAll use TableGrant access all; (access all tables columns)
            """;

    private static final String roleNoneBody = """
            The roleNone use ColumnGrant access none; (no access to all tables columns)
            """;

    private static final String schemaDocumentationTxt = """
            Cube with examples of roles with TableGrant
    roleAll    role: use TableGrant access all; (access all database all tables)
    roleNone   role: use TableGrant access none; (no access to database tables)
    roleCustom role: use TableGrant access custom; (access to database table Fact)
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_ColumnGrand");

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

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure1);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube_main");
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroup);

        AccessColumnGrant accessColumnGrantAll1 = RolapMappingFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantAll1.setColumnAccess(ColumnAccess.ALL);
        accessColumnGrantAll1.setColumn(valueColumn);

        AccessColumnGrant accessColumnGrantAll2 = RolapMappingFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantAll2.setColumnAccess(ColumnAccess.ALL);
        accessColumnGrantAll2.setColumn(keyColumn);

        AccessColumnGrant accessColumnGrantNone = RolapMappingFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantNone.setColumnAccess(ColumnAccess.NONE);
        accessColumnGrantNone.setColumn(valueColumn);

        AccessTableGrant accessTableGrantAll = RolapMappingFactory.eINSTANCE.createAccessTableGrant();
        accessTableGrantAll.setTableAccess(TableAccess.CUSTOM);
        accessTableGrantAll.setTable(table);
        accessTableGrantAll.getColumnGrants().add(accessColumnGrantAll1);
        accessTableGrantAll.getColumnGrants().add(accessColumnGrantAll2);

        AccessTableGrant accessTableGrantNone = RolapMappingFactory.eINSTANCE.createAccessTableGrant();
        accessTableGrantNone.setTableAccess(TableAccess.CUSTOM);
        accessTableGrantNone.setTable(table);
        accessTableGrantNone.getColumnGrants().add(accessColumnGrantNone);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantAll = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantAll.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        accessDatabaseSchemaGrantAll.setDatabaseSchema(databaseSchema);
        accessDatabaseSchemaGrantAll.getTableGrants().add(accessTableGrantAll);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantNone = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantNone.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        accessDatabaseSchemaGrantNone.setDatabaseSchema(databaseSchema);
        accessDatabaseSchemaGrantNone.getTableGrants().add(accessTableGrantNone);

        AccessCubeGrant accessCubeGrant1 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant1.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant1.setCube(cube1);

        AccessCubeGrant accessCubeGrant2 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant2.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant2.setCube(cube1);

        AccessCatalogGrant accessCatalogGrantAll = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrantAll);
        accessCatalogGrantAll.getCubeGrants().add(accessCubeGrant1);

        AccessCatalogGrant accessCatalogGrantNone = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantNone.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantNone.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrantNone);
        accessCatalogGrantNone.getCubeGrants().add(accessCubeGrant2);

        AccessRole roleAll = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleAll.setName("roleAll");
        roleAll.setId("_accessRole_all");
        roleAll.getAccessCatalogGrants().add(accessCatalogGrantAll);

        AccessRole roleNone = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleNone.setName("roleNone");
        roleNone.setId("_accessRole_none");
        roleNone.getAccessCatalogGrants().add(accessCatalogGrantNone);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with roles access column");
        catalog.setDescription("Schema with roles access column");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(roleAll);
        catalog.getAccessRoles().add(roleNone);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube with role access column", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(cube1, "Cube1 with access all", cube1Body, 1, 6, 0, true, 2);

        document(roleAll, "roleAll", roleAllBody, 1, 7, 0, true, 2);

        document(roleNone, "roleNone", roleNoneBody, 1, 8, 0, true, 2);

        return catalog;
    }

}
