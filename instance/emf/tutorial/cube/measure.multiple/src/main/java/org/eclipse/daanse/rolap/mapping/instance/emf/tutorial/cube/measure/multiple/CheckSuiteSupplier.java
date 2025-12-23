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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.multiple;

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
 * Provides a check suite for the measure multiple tutorial.
 * Checks that the catalog, cube, and multiple measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks for three measures
        MeasureCheck measureCheck1 = factory.createMeasureCheck();
        measureCheck1.setName("MeasureCheck-Sum of Value1");
        measureCheck1.setDescription("Check that measure 'Sum of Value1' exists");
        measureCheck1.setMeasureName("Sum of Value1");

        MeasureCheck measureCheck2 = factory.createMeasureCheck();
        measureCheck2.setName("MeasureCheck-Sum of Value2");
        measureCheck2.setDescription("Check that measure 'Sum of Value2' exists");
        measureCheck2.setMeasureName("Sum of Value2");

        MeasureCheck measureCheck3 = factory.createMeasureCheck();
        measureCheck3.setName("MeasureCheck-Sum of Value3");
        measureCheck3.setDescription("Check that measure 'Sum of Value3' exists");
        measureCheck3.setMeasureName("Sum of Value3");

        // Create cube check with measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MultipleMeasuresCube");
        cubeCheck.setDescription("Check that cube 'MultipleMeasuresCube' exists");
        cubeCheck.setCubeName("MultipleMeasuresCube");
        cubeCheck.getMeasureChecks().add(measureCheck1);
        cubeCheck.getMeasureChecks().add(measureCheck2);
        cubeCheck.getMeasureChecks().add(measureCheck3);

        // Create query checks for each measure
        CellValueCheck queryCheckCellValueCheck1 = factory.createCellValueCheck();
        queryCheckCellValueCheck1.setName("[Measures].[Sum of Value1]");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Sum of Value1 Query Check");
        queryCheck1.setDescription("Verify MDX query returns Sum of Value1 data");
        queryCheck1.setQuery("SELECT FROM [MultipleMeasuresCube] WHERE ([Measures].[Sum of Value1])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheckCellValueCheck1);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheckCellValueCheck2 = factory.createCellValueCheck();
        queryCheckCellValueCheck2.setName("[Measures].[Sum of Value2]");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Sum of Value2 Query Check");
        queryCheck2.setDescription("Verify MDX query returns Sum of Value2 data");
        queryCheck2.setQuery("SELECT FROM [MultipleMeasuresCube] WHERE ([Measures].[Sum of Value2])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheckCellValueCheck2);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheckCellValueCheck3 = factory.createCellValueCheck();
        queryCheckCellValueCheck3.setName("[Measures].[Sum of Value3]");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Sum of Value3 Query Check");
        queryCheck3.setDescription("Verify MDX query returns Sum of Value3 data");
        queryCheck3.setQuery("SELECT FROM [MultipleMeasuresCube] WHERE ([Measures].[Sum of Value3])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheckCellValueCheck3);
        queryCheck3.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseColumnAttributeCheck columnValue1TypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValue1TypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValue1TypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue1 = factory.createDatabaseColumnCheck();
        columnCheckValue1.setName("Database Column Check VALUE1");
        columnCheckValue1.setColumnName("VALUE1");
        columnCheckValue1.getColumnAttributeChecks().add(columnValue1TypeCheck);

        DatabaseColumnAttributeCheck columnValue2TypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValue2TypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValue2TypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue2 = factory.createDatabaseColumnCheck();
        columnCheckValue2.setName("Database Column Check VALUE2");
        columnCheckValue2.setColumnName("VALUE2");
        columnCheckValue2.getColumnAttributeChecks().add(columnValue2TypeCheck);

        DatabaseColumnAttributeCheck columnValue3TypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValue3TypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValue3TypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue3 = factory.createDatabaseColumnCheck();
        columnCheckValue3.setName("Database Column Check VALUE3");
        columnCheckValue3.setColumnName("VALUE3");
        columnCheckValue3.getColumnAttributeChecks().add(columnValue3TypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue1);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue2);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue3);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Multiple");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Multiple");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Multiple' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Multiple");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Multiple");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Multiple");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Multiple");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Multiple");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
