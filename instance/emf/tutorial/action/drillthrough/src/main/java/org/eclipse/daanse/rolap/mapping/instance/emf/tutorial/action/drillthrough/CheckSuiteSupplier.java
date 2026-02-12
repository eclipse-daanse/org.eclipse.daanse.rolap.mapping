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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.action.drillthrough;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the action drillthrough tutorial.
 * Checks that the catalog, cube, measure, and drillthrough actions exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure1");
        measureCheck.setDescription("Check that measure 'Measure1' exists");
        measureCheck.setMeasureName("Measure1");

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1]");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseColumnAttributeCheck columnValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnValueTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create database column checks for H1_L1 table
        DatabaseColumnAttributeCheck columnH1L1KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH1L1Key = factory.createDatabaseColumnCheck();
        columnCheckH1L1Key.setName("Database Column Check KEY (H1_L1)");
        columnCheckH1L1Key.setColumnName("KEY");
        columnCheckH1L1Key.getColumnAttributeChecks().add(columnH1L1KeyTypeCheck);

        DatabaseColumnAttributeCheck columnH1L1NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckH1L1Name = factory.createDatabaseColumnCheck();
        columnCheckH1L1Name.setName("Database Column Check NAME (H1_L1)");
        columnCheckH1L1Name.setColumnName("NAME");
        columnCheckH1L1Name.getColumnAttributeChecks().add(columnH1L1NameTypeCheck);

        DatabaseTableCheck databaseTableCheckH1L1 = factory.createDatabaseTableCheck();
        databaseTableCheckH1L1.setName("Database Table H1_L1 Check");
        databaseTableCheckH1L1.setTableName("H1_L1");
        databaseTableCheckH1L1.getColumnChecks().add(columnCheckH1L1Key);
        databaseTableCheckH1L1.getColumnChecks().add(columnCheckH1L1Name);

        // Create database column checks for H2_L1 table
        DatabaseColumnAttributeCheck columnH2L1KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH2L1KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH2L1KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH2L1Key = factory.createDatabaseColumnCheck();
        columnCheckH2L1Key.setName("Database Column Check KEY (H2_L1)");
        columnCheckH2L1Key.setColumnName("KEY");
        columnCheckH2L1Key.getColumnAttributeChecks().add(columnH2L1KeyTypeCheck);

        DatabaseColumnAttributeCheck columnH2L1NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH2L1NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH2L1NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckH2L1Name = factory.createDatabaseColumnCheck();
        columnCheckH2L1Name.setName("Database Column Check NAME (H2_L1)");
        columnCheckH2L1Name.setColumnName("NAME");
        columnCheckH2L1Name.getColumnAttributeChecks().add(columnH2L1NameTypeCheck);

        DatabaseTableCheck databaseTableCheckH2L1 = factory.createDatabaseTableCheck();
        databaseTableCheckH2L1.setName("Database Table H2_L1 Check");
        databaseTableCheckH2L1.setTableName("H2_L1");
        databaseTableCheckH2L1.getColumnChecks().add(columnCheckH2L1Key);
        databaseTableCheckH2L1.getColumnChecks().add(columnCheckH2L1Name);

        // Create database column checks for HX_L2 table
        DatabaseColumnAttributeCheck columnHXL2KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHXL2KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHXL2KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckHXL2Key = factory.createDatabaseColumnCheck();
        columnCheckHXL2Key.setName("Database Column Check KEY (HX_L2)");
        columnCheckHXL2Key.setColumnName("KEY");
        columnCheckHXL2Key.getColumnAttributeChecks().add(columnHXL2KeyTypeCheck);

        DatabaseColumnAttributeCheck columnHXL2NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHXL2NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHXL2NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckHXL2Name = factory.createDatabaseColumnCheck();
        columnCheckHXL2Name.setName("Database Column Check NAME (HX_L2)");
        columnCheckHXL2Name.setColumnName("NAME");
        columnCheckHXL2Name.getColumnAttributeChecks().add(columnHXL2NameTypeCheck);

        DatabaseColumnAttributeCheck columnH1L1KeyFKTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1KeyFKTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1KeyFKTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH1L1KeyFK = factory.createDatabaseColumnCheck();
        columnCheckH1L1KeyFK.setName("Database Column Check H1L1_KEY (HX_L2)");
        columnCheckH1L1KeyFK.setColumnName("H1L1_KEY");
        columnCheckH1L1KeyFK.getColumnAttributeChecks().add(columnH1L1KeyFKTypeCheck);

        DatabaseColumnAttributeCheck columnH2L1KeyFKTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH2L1KeyFKTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH2L1KeyFKTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH2L1KeyFK = factory.createDatabaseColumnCheck();
        columnCheckH2L1KeyFK.setName("Database Column Check H2L1_KEY (HX_L2)");
        columnCheckH2L1KeyFK.setColumnName("H2L1_KEY");
        columnCheckH2L1KeyFK.getColumnAttributeChecks().add(columnH2L1KeyFKTypeCheck);

        DatabaseTableCheck databaseTableCheckHXL2 = factory.createDatabaseTableCheck();
        databaseTableCheckHXL2.setName("Database Table HX_L2 Check");
        databaseTableCheckHXL2.setTableName("HX_L2");
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckHXL2Key);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckHXL2Name);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckH1L1KeyFK);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckH2L1KeyFK);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Action Drillthrough");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckH1L1);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckH2L1);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckHXL2);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Action Drillthrough");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Action Drillthrough' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Action Drillthrough");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Action Drillthrough");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Action Drillthrough");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Action Drillthrough");
        suite.setDescription("Check suite for the Daanse Tutorial - Action Drillthrough");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
