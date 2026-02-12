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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.expressioncolumn;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the database expression column tutorial.
 * Checks that the catalog and database schema with SQL expression columns exist and are properly configured.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create database column check for "column1" (physical column)
        DatabaseColumnCheck columnCheck1 = factory.createDatabaseColumnCheck();
        columnCheck1.setName("Database Column Check column1");
        columnCheck1.setColumnName("column1");

        // Create database column check for "SqlExpressionColumn" (computed column)
        // Note: SQL expression columns are computed dynamically and may not be visible
        // in the database metadata in the same way as physical columns
        DatabaseColumnCheck columnCheckSqlExpression = factory.createDatabaseColumnCheck();
        columnCheckSqlExpression.setName("Database Column Check SqlExpressionColumn");
        columnCheckSqlExpression.setColumnName("SqlExpressionColumn");

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheck = factory.createDatabaseTableCheck();
        databaseTableCheck.setName("Database Table TableWithExpressionColumn Check");
        databaseTableCheck.setTableName("TableWithExpressionColumn");
        databaseTableCheck.getColumnChecks().add(columnCheck1);
        databaseTableCheck.getColumnChecks().add(columnCheckSqlExpression);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Database Expression Column");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheck);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database Expression Column");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database Expression Column' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database Expression Column");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database Expression Column");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database Expression Column");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database Expression Column");
        suite.setDescription("Check suite for the Daanse Tutorial - Database Expression Column");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
