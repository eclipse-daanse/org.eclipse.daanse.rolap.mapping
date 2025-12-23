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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.expression;

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
 * Provides a check suite for the measure expression tutorial.
 * Checks that the catalog, cube, measures with SQL expression columns, and database schema exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck measure1Check = factory.createMeasureCheck();
        measure1Check.setName("MeasureCheck-Measure1-Sum");
        measure1Check.setDescription("Check that measure 'Measure1-Sum' exists with SQL expression");
        measure1Check.setMeasureName("Measure1-Sum");

        MeasureCheck measure2Check = factory.createMeasureCheck();
        measure2Check.setName("MeasureCheck-Measure2-Sum");
        measure2Check.setDescription("Check that measure 'Measure2-Sum' exists with SQL expression");
        measure2Check.setMeasureName("Measure2-Sum");

        // Create cube check with measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube With MeasureExpression");
        cubeCheck.setDescription("Check that cube 'Cube With MeasureExpression' exists");
        cubeCheck.setCubeName("Cube With MeasureExpression");
        cubeCheck.getMeasureChecks().add(measure1Check);
        cubeCheck.getMeasureChecks().add(measure2Check);

        // Create query check for Measure1-Sum
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure1-Sum]");
        queryCheck1CellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure1 Query Check");
        queryCheck1.setDescription("Verify MDX query returns Measure1-Sum data");
        queryCheck1.setQuery("SELECT FROM [Cube With MeasureExpression] WHERE ([Measures].[Measure1-Sum])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        // Create query check for Measure2-Sum
        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Measure2-Sum]");
        queryCheck2CellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure2 Query Check");
        queryCheck2.setDescription("Verify MDX query returns Measure2-Sum data");
        queryCheck2.setQuery("SELECT FROM [Cube With MeasureExpression] WHERE ([Measures].[Measure2-Sum])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        // Create database column checks for FACT table
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

        DatabaseColumnAttributeCheck columnValueNumericTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueNumericTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueNumericTypeCheck.setExpectedValue("NUMERIC");

        DatabaseColumnCheck columnCheckValueNumeric = factory.createDatabaseColumnCheck();
        columnCheckValueNumeric.setName("Database Column Check VALUE_NUMERIC");
        columnCheckValueNumeric.setColumnName("VALUE_NUMERIC");
        columnCheckValueNumeric.getColumnAttributeChecks().add(columnValueNumericTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table FACT Check");
        databaseTableCheckFact.setTableName("FACT");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValueNumeric);

        // Create database column checks for MEASURE_TABLE table
        DatabaseColumnAttributeCheck columnIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckId = factory.createDatabaseColumnCheck();
        columnCheckId.setName("Database Column Check ID");
        columnCheckId.setColumnName("ID");
        columnCheckId.getColumnAttributeChecks().add(columnIdTypeCheck);

        DatabaseColumnAttributeCheck columnMeasureTableValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMeasureTableValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMeasureTableValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckMeasureTableValue = factory.createDatabaseColumnCheck();
        columnCheckMeasureTableValue.setName("Database Column Check VALUE");
        columnCheckMeasureTableValue.setColumnName("VALUE");
        columnCheckMeasureTableValue.getColumnAttributeChecks().add(columnMeasureTableValueTypeCheck);

        DatabaseColumnAttributeCheck columnFlagTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFlagTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFlagTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFlag = factory.createDatabaseColumnCheck();
        columnCheckFlag.setName("Database Column Check FLAG");
        columnCheckFlag.setColumnName("FLAG");
        columnCheckFlag.getColumnAttributeChecks().add(columnFlagTypeCheck);

        DatabaseTableCheck databaseTableCheckMeasureTable = factory.createDatabaseTableCheck();
        databaseTableCheckMeasureTable.setName("Database Table MEASURE_TABLE Check");
        databaseTableCheckMeasureTable.setTableName("MEASURE_TABLE");
        databaseTableCheckMeasureTable.getColumnChecks().add(columnCheckId);
        databaseTableCheckMeasureTable.getColumnChecks().add(columnCheckMeasureTableValue);
        databaseTableCheckMeasureTable.getColumnChecks().add(columnCheckFlag);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Expression");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckMeasureTable);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Expression");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Expression' exists with SQL expression-based measures");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Expression");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Expression");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Expression");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Expression");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Expression with SQL expression columns");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
