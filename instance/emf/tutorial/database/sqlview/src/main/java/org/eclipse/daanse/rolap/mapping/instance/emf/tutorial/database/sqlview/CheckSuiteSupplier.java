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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.sqlview;

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
 * Provides a check suite for the database SQL view tutorial.
 * Checks that the catalog and database schema with SQL view exist and are properly configured.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create database column check for "ColumnOne"
        DatabaseColumnAttributeCheck columnOneTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnOneTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnOneTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckOne = factory.createDatabaseColumnCheck();
        columnCheckOne.setName("Database Column Check ColumnOne");
        columnCheckOne.setColumnName("ColumnOne");
        columnCheckOne.getColumnAttributeChecks().add(columnOneTypeCheck);

        // Create Database Table Check for SQL view "sqlview"
        // Note: SqlView is a virtual table based on SQL statements, not a physical table
        DatabaseTableCheck databaseTableCheck = factory.createDatabaseTableCheck();
        databaseTableCheck.setName("Database SQL View sqlview Check");
        databaseTableCheck.setTableName("sqlview");
        databaseTableCheck.getColumnChecks().add(columnCheckOne);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Database SQL View");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheck);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database SQL View");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database SQL View' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database SQL View");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database SQL View");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database SQL View");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database SQL View");
        suite.setDescription("Check suite for the Daanse Tutorial - Database SQL View");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
