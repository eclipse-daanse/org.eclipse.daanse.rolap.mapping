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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.column;

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
 * Provides a check suite for the database column tutorial.
 * Checks that the catalog and database schema with various column types exist and are properly configured.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create database column checks for "ColumnWithDescription"
        DatabaseColumnAttributeCheck columnWithDescriptionTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnWithDescriptionTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnWithDescriptionTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckWithDescription = factory.createDatabaseColumnCheck();
        columnCheckWithDescription.setName("Database Column Check ColumnWithDescription");
        columnCheckWithDescription.setColumnName("ColumnWithDescription");
        columnCheckWithDescription.getColumnAttributeChecks().add(columnWithDescriptionTypeCheck);

        // Create database column checks for "ColumnVarchar"
        DatabaseColumnAttributeCheck columnVarcharTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnVarcharTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnVarcharTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckVarchar = factory.createDatabaseColumnCheck();
        columnCheckVarchar.setName("Database Column Check ColumnVarchar");
        columnCheckVarchar.setColumnName("ColumnVarchar");
        columnCheckVarchar.getColumnAttributeChecks().add(columnVarcharTypeCheck);

        // Create database column checks for "ColumnDecimal"
        DatabaseColumnAttributeCheck columnDecimalTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDecimalTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDecimalTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckDecimal = factory.createDatabaseColumnCheck();
        columnCheckDecimal.setName("Database Column Check ColumnDecimal");
        columnCheckDecimal.setColumnName("ColumnDecimal");
        columnCheckDecimal.getColumnAttributeChecks().add(columnDecimalTypeCheck);

        // Create database column checks for "ColumnNumeric"
        DatabaseColumnAttributeCheck columnNumericTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnNumericTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnNumericTypeCheck.setExpectedValue("NUMERIC");

        DatabaseColumnCheck columnCheckNumeric = factory.createDatabaseColumnCheck();
        columnCheckNumeric.setName("Database Column Check ColumnNumeric");
        columnCheckNumeric.setColumnName("ColumnNumeric");
        columnCheckNumeric.getColumnAttributeChecks().add(columnNumericTypeCheck);

        // Create database column checks for "ColumnFloat"
        DatabaseColumnAttributeCheck columnFloatTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFloatTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFloatTypeCheck.setExpectedValue("FLOAT");

        DatabaseColumnCheck columnCheckFloat = factory.createDatabaseColumnCheck();
        columnCheckFloat.setName("Database Column Check ColumnFloat");
        columnCheckFloat.setColumnName("ColumnFloat");
        columnCheckFloat.getColumnAttributeChecks().add(columnFloatTypeCheck);

        // Create database column checks for "ColumnReal"
        DatabaseColumnAttributeCheck columnRealTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnRealTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnRealTypeCheck.setExpectedValue("REAL");

        DatabaseColumnCheck columnCheckReal = factory.createDatabaseColumnCheck();
        columnCheckReal.setName("Database Column Check ColumnReal");
        columnCheckReal.setColumnName("ColumnReal");
        columnCheckReal.getColumnAttributeChecks().add(columnRealTypeCheck);

        // Create database column checks for "ColumnDouble"
        DatabaseColumnAttributeCheck columnDoubleTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDoubleTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDoubleTypeCheck.setExpectedValue("DOUBLE");

        DatabaseColumnCheck columnCheckDouble = factory.createDatabaseColumnCheck();
        columnCheckDouble.setName("Database Column Check ColumnDouble");
        columnCheckDouble.setColumnName("ColumnDouble");
        columnCheckDouble.getColumnAttributeChecks().add(columnDoubleTypeCheck);

        // Create database column checks for "ColumnInteger"
        DatabaseColumnAttributeCheck columnIntegerTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnIntegerTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnIntegerTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckInteger = factory.createDatabaseColumnCheck();
        columnCheckInteger.setName("Database Column Check ColumnInteger");
        columnCheckInteger.setColumnName("ColumnInteger");
        columnCheckInteger.getColumnAttributeChecks().add(columnIntegerTypeCheck);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheck = factory.createDatabaseTableCheck();
        databaseTableCheck.setName("Database Table TableWithColumnTypes Check");
        databaseTableCheck.setTableName("TableWithColumnTypes");
        databaseTableCheck.getColumnChecks().add(columnCheckWithDescription);
        databaseTableCheck.getColumnChecks().add(columnCheckVarchar);
        databaseTableCheck.getColumnChecks().add(columnCheckDecimal);
        databaseTableCheck.getColumnChecks().add(columnCheckNumeric);
        databaseTableCheck.getColumnChecks().add(columnCheckFloat);
        databaseTableCheck.getColumnChecks().add(columnCheckReal);
        databaseTableCheck.getColumnChecks().add(columnCheckDouble);
        databaseTableCheck.getColumnChecks().add(columnCheckInteger);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Database Column");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheck);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database Column");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database Column' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database Column");
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database Column");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database Column");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database Column");
        suite.setDescription("Check suite for the Daanse Tutorial - Database Column");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
