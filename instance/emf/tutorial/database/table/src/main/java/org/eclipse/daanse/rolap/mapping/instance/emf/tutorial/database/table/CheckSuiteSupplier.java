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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.table;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the database table tutorial.
 * Checks that the catalog and database schema with different table types exist and are properly configured.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create database column check for PhysicalTable "TableOne"
        DatabaseColumnAttributeCheck columnPhysicalTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPhysicalTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPhysicalTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckPhysical = factory.createDatabaseColumnCheck();
        columnCheckPhysical.setName("Database Column Check ColumnOne (PhysicalTable)");
        columnCheckPhysical.setColumnName("ColumnOne");
        columnCheckPhysical.getColumnAttributeChecks().add(columnPhysicalTypeCheck);

        // Create Database Table Check for PhysicalTable "TableOne"
        DatabaseTableCheck physicalTableCheck = factory.createDatabaseTableCheck();
        physicalTableCheck.setName("Database Physical Table TableOne Check");
        physicalTableCheck.setTableName("TableOne");
        physicalTableCheck.getColumnChecks().add(columnCheckPhysical);

        // Create database column check for ViewTable "ViewOne"
        DatabaseColumnAttributeCheck columnViewTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnViewTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnViewTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckView = factory.createDatabaseColumnCheck();
        columnCheckView.setName("Database Column Check ColumnOne (ViewTable)");
        columnCheckView.setColumnName("ColumnOne");
        columnCheckView.getColumnAttributeChecks().add(columnViewTypeCheck);

        // Create Database Table Check for ViewTable "ViewOne"
        DatabaseTableCheck viewTableCheck = factory.createDatabaseTableCheck();
        viewTableCheck.setName("Database View Table ViewOne Check");
        viewTableCheck.setTableName("ViewOne");
        viewTableCheck.getColumnChecks().add(columnCheckView);

        // Create database column check for SystemTable "TableOne"
        DatabaseColumnAttributeCheck columnSystemTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnSystemTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnSystemTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckSystem = factory.createDatabaseColumnCheck();
        columnCheckSystem.setName("Database Column Check ColumnOne (SystemTable)");
        columnCheckSystem.setColumnName("ColumnOne");
        columnCheckSystem.getColumnAttributeChecks().add(columnSystemTypeCheck);

        // Create Database Table Check for SystemTable "TableOne"
        DatabaseTableCheck systemTableCheck = factory.createDatabaseTableCheck();
        systemTableCheck.setName("Database System Table TableOne Check");
        systemTableCheck.setTableName("TableOne");
        systemTableCheck.getColumnChecks().add(columnCheckSystem);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Database Table");
        databaseSchemaCheck.getTableChecks().add(physicalTableCheck);
        databaseSchemaCheck.getTableChecks().add(viewTableCheck);
        databaseSchemaCheck.getTableChecks().add(systemTableCheck);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database Table");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database Table' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database Table");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database Table");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database Table");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database Table");
        suite.setDescription("Check suite for the Daanse Tutorial - Database Table");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
