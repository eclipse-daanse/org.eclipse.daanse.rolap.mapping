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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.intro;

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
 * Provides a check suite for the database intro tutorial.
 * Checks that the catalog and basic database schema structure exist and are properly configured.
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

        // Create Database Table Check for "TableOne"
        DatabaseTableCheck databaseTableCheck = factory.createDatabaseTableCheck();
        databaseTableCheck.setName("Database Table TableOne Check");
        databaseTableCheck.setTableName("TableOne");
        databaseTableCheck.getColumnChecks().add(columnCheckOne);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Database Intro");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheck);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database Intro");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database Intro' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database Intro");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database Intro");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database Intro");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database Intro");
        suite.setDescription("Check suite for the Daanse Tutorial - Database Intro");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
