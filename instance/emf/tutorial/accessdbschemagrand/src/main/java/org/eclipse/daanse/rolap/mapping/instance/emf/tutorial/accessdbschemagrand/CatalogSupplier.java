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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.accessdbschemagrand;

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
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";
    private static final String FACT1 = "Fact1";

    private static final String schemaDocumentationTxt = """
    Cube with examples of roles with database schema grant
    role_database_schema_access_all - access all tables (Fact, Fact1) and all columns
    role_database_schema_access_custom - access tables (Fact, Fact1) and all columns from Fact and KEY column from Fact1. no access for VALUE column from Fact1
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

        Column keyColumn1 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn1.setName("KEY");
        keyColumn1.setId("Fact1_KEY");
        keyColumn1.setType(ColumnType.VARCHAR);

        Column valueColumn1 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn1.setName("VALUE");
        valueColumn1.setId("Fact1_VALUE");
        valueColumn1.setType(ColumnType.INTEGER);

        PhysicalTable table1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table1.setName(FACT1);
        table1.setId(FACT1);
        table1.getColumns().addAll(List.of(keyColumn1, valueColumn1));
        databaseSchema.getTables().add(table1);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure1);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        AccessCubeGrant accessCubeGrant1 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant1.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant1.setCube(cube);

        AccessCubeGrant accessCubeGrant2 = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant2.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant2.setCube(cube);

        AccessDatabaseSchemaGrant databaseSchemaGrantAll = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        databaseSchemaGrantAll.setDatabaseSchema(databaseSchema);
        // all access for all table and columns
        databaseSchemaGrantAll.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        AccessTableGrant tableGrant1 = RolapMappingFactory.eINSTANCE.createAccessTableGrant();
        // all access for all columns of Fact table
        tableGrant1.setTableAccess(TableAccess.ALL);
        tableGrant1.setTable(table);

        AccessColumnGrant columnGrant1 = RolapMappingFactory.eINSTANCE.createAccessColumnGrant();
        columnGrant1.setColumn(keyColumn1);
        // all access for column KEY of Fact1 table
        columnGrant1.setColumnAccess(ColumnAccess.ALL);

        AccessColumnGrant columnGrant2 = RolapMappingFactory.eINSTANCE.createAccessColumnGrant();
        columnGrant2.setColumn(valueColumn1);
        // no access for column VALUE of Fact1 table
        columnGrant2.setColumnAccess(ColumnAccess.NONE);

        AccessTableGrant tableGrant2 = RolapMappingFactory.eINSTANCE.createAccessTableGrant();
        tableGrant2.setTableAccess(TableAccess.CUSTOM);
        tableGrant2.setTable(table1);
        tableGrant2.getColumnGrants().addAll(List.of(columnGrant1, columnGrant2));

        AccessDatabaseSchemaGrant databaseSchemaGrantCustom = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        databaseSchemaGrantCustom.setDatabaseSchema(databaseSchema);
        databaseSchemaGrantCustom.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        databaseSchemaGrantCustom.getTableGrants().addAll(List.of(tableGrant1, tableGrant2));

        AccessCatalogGrant accessCatalogGrant1 = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant1.setCatalogAccess(CatalogAccess.CUSTOM);
        accessCatalogGrant1.getDatabaseSchemaGrants().add(databaseSchemaGrantAll);
        accessCatalogGrant1.getCubeGrants().add(accessCubeGrant1);

        AccessCatalogGrant accessCatalogGrant2 = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant2.setCatalogAccess(CatalogAccess.CUSTOM);
        accessCatalogGrant2.getDatabaseSchemaGrants().add(databaseSchemaGrantCustom);
        accessCatalogGrant2.getCubeGrants().add(accessCubeGrant2);

        //all access for table Fact all columns and table Fact1 all columns
        AccessRole roleDatabaseSchemaAccessAll = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleDatabaseSchemaAccessAll.setName("role_database_schema_access_all");
        roleDatabaseSchemaAccessAll.setId("role_database_schema_access_all");
        roleDatabaseSchemaAccessAll.getAccessCatalogGrants().add(accessCatalogGrant1);

        //all access for table Fact and access for column Key for table Fact1
        AccessRole roleDatabaseSchemaAccessCustom = RolapMappingFactory.eINSTANCE.createAccessRole();
        roleDatabaseSchemaAccessCustom.setName("role_database_schema_access_custom");
        roleDatabaseSchemaAccessCustom.setId("role_database_schema_access_custom");
        roleDatabaseSchemaAccessCustom.getAccessCatalogGrants().add(accessCatalogGrant2);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_access_database_schema");
        catalog.setDescription("Schema with access database schema");
        catalog.getCubes().add(cube);
        catalog.getAccessRoles().addAll(List.of(roleDatabaseSchemaAccessAll, roleDatabaseSchemaAccessCustom));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
