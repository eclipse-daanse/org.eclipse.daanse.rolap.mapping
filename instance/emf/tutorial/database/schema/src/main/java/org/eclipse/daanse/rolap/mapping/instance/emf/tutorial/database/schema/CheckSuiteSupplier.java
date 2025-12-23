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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.schema;

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
 * Provides a check suite for the database schema tutorial.
 * Checks that the catalog with multiple database schemas exists and is properly configured.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create database column check for "theColumn" in default schema
        DatabaseColumnAttributeCheck columnDefaultTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDefaultTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDefaultTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckDefault = factory.createDatabaseColumnCheck();
        columnCheckDefault.setName("Database Column Check theColumn (Default Schema)");
        columnCheckDefault.setColumnName("theColumn");
        columnCheckDefault.getColumnAttributeChecks().add(columnDefaultTypeCheck);

        // Create Database Table Check for "theTable" in default schema
        DatabaseTableCheck databaseTableCheckDefault = factory.createDatabaseTableCheck();
        databaseTableCheckDefault.setName("Database Table theTable Check (Default Schema)");
        databaseTableCheckDefault.setTableName("theTable");
        databaseTableCheckDefault.getColumnChecks().add(columnCheckDefault);

        // Create Database Schema Check for default schema (without name)
        DatabaseSchemaCheck databaseSchemaCheckDefault = factory.createDatabaseSchemaCheck();
        databaseSchemaCheckDefault.setName("Database Schema Check (Default)");
        databaseSchemaCheckDefault.setDescription("Database Schema Check for default schema without name");
        databaseSchemaCheckDefault.getTableChecks().add(databaseTableCheckDefault);

        // Create database column check for "theColumn" in "foo" schema
        DatabaseColumnAttributeCheck columnFooTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFooTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFooTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckFoo = factory.createDatabaseColumnCheck();
        columnCheckFoo.setName("Database Column Check theColumn (Schema foo)");
        columnCheckFoo.setColumnName("theColumn");
        columnCheckFoo.getColumnAttributeChecks().add(columnFooTypeCheck);

        // Create Database Table Check for "theTable" in "foo" schema
        DatabaseTableCheck databaseTableCheckFoo = factory.createDatabaseTableCheck();
        databaseTableCheckFoo.setName("Database Table theTable Check (Schema foo)");
        databaseTableCheckFoo.setTableName("theTable");
        databaseTableCheckFoo.getColumnChecks().add(columnCheckFoo);

        // Create Database Schema Check for "foo" schema (with name)
        DatabaseSchemaCheck databaseSchemaCheckFoo = factory.createDatabaseSchemaCheck();
        databaseSchemaCheckFoo.setName("Database Schema Check (foo)");
        databaseSchemaCheckFoo.setDescription("Database Schema Check for named schema 'foo'");
        databaseSchemaCheckFoo.getTableChecks().add(databaseTableCheckFoo);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database Schema");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database Schema' exists with multiple database schemas");
        catalogCheck.setCatalogName("Daanse Tutorial - Database Schema");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheckDefault);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheckFoo);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database Schema");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database Schema");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database Schema");
        suite.setDescription("Check suite for the Daanse Tutorial - Database Schema");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
