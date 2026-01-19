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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.nth;

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
 * Provides a check suite for the nth aggregator cube mapping.
 * Checks that the catalog, cube, and NthAgg measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks for NthAgg1 through NthAgg7
        MeasureCheck nthAgg1MeasureCheck = factory.createMeasureCheck();
        nthAgg1MeasureCheck.setName("MeasureCheck-NthAgg1");
        nthAgg1MeasureCheck.setDescription("Check that measure 'NthAgg1' exists");
        nthAgg1MeasureCheck.setMeasureName("NthAgg1");

        MeasureCheck nthAgg2MeasureCheck = factory.createMeasureCheck();
        nthAgg2MeasureCheck.setName("MeasureCheck-NthAgg2");
        nthAgg2MeasureCheck.setDescription("Check that measure 'NthAgg2' exists");
        nthAgg2MeasureCheck.setMeasureName("NthAgg2");

        MeasureCheck nthAgg3MeasureCheck = factory.createMeasureCheck();
        nthAgg3MeasureCheck.setName("MeasureCheck-NthAgg3");
        nthAgg3MeasureCheck.setDescription("Check that measure 'NthAgg3' exists");
        nthAgg3MeasureCheck.setMeasureName("NthAgg3");

        MeasureCheck nthAgg4MeasureCheck = factory.createMeasureCheck();
        nthAgg4MeasureCheck.setName("MeasureCheck-NthAgg4");
        nthAgg4MeasureCheck.setDescription("Check that measure 'NthAgg4' exists");
        nthAgg4MeasureCheck.setMeasureName("NthAgg4");

        MeasureCheck nthAgg5MeasureCheck = factory.createMeasureCheck();
        nthAgg5MeasureCheck.setName("MeasureCheck-NthAgg5");
        nthAgg5MeasureCheck.setDescription("Check that measure 'NthAgg5' exists");
        nthAgg5MeasureCheck.setMeasureName("NthAgg5");

        MeasureCheck nthAgg6MeasureCheck = factory.createMeasureCheck();
        nthAgg6MeasureCheck.setName("MeasureCheck-NthAgg6");
        nthAgg6MeasureCheck.setDescription("Check that measure 'NthAgg6' exists");
        nthAgg6MeasureCheck.setMeasureName("NthAgg6");

        MeasureCheck nthAgg7MeasureCheck = factory.createMeasureCheck();
        nthAgg7MeasureCheck.setName("MeasureCheck-NthAgg7");
        nthAgg7MeasureCheck.setDescription("Check that measure 'NthAgg7' exists");
        nthAgg7MeasureCheck.setMeasureName("NthAgg7");

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresAggregatorsCube");
        cubeCheck.getMeasureChecks().add(nthAgg1MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg2MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg3MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg4MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg5MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg6MeasureCheck);
        cubeCheck.getMeasureChecks().add(nthAgg7MeasureCheck);

        // Create query checks for each NthAgg measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[NthAgg1]");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check NthAgg1");
        queryCheck1.setDescription("Verify MDX query returns Measure data for NthAgg1");
        queryCheck1.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg1])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[NthAgg2]");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check NthAgg2");
        queryCheck2.setDescription("Verify MDX query returns Measure data for NthAgg2");
        queryCheck2.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg2])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[NthAgg3]");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check NthAgg3");
        queryCheck3.setDescription("Verify MDX query returns Measure data for NthAgg3");
        queryCheck3.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg3])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        CellValueCheck queryCheck4CellValueCheck = factory.createCellValueCheck();
        queryCheck4CellValueCheck.setName("[Measures].[NthAgg4]");

        QueryCheck queryCheck4 = factory.createQueryCheck();
        queryCheck4.setName("Measure Query Check NthAgg4");
        queryCheck4.setDescription("Verify MDX query returns Measure data for NthAgg4");
        queryCheck4.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg4])");
        queryCheck4.setQueryLanguage(QueryLanguage.MDX);
        queryCheck4.setExpectedColumnCount(1);
        queryCheck4.getCellChecks().add(queryCheck4CellValueCheck);
        queryCheck4.setEnabled(true);

        CellValueCheck queryCheck5CellValueCheck = factory.createCellValueCheck();
        queryCheck5CellValueCheck.setName("[Measures].[NthAgg5]");

        QueryCheck queryCheck5 = factory.createQueryCheck();
        queryCheck5.setName("Measure Query Check NthAgg5");
        queryCheck5.setDescription("Verify MDX query returns Measure data for NthAgg5");
        queryCheck5.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg5])");
        queryCheck5.setQueryLanguage(QueryLanguage.MDX);
        queryCheck5.setExpectedColumnCount(1);
        queryCheck5.getCellChecks().add(queryCheck5CellValueCheck);
        queryCheck5.setEnabled(true);

        CellValueCheck queryCheck6CellValueCheck = factory.createCellValueCheck();
        queryCheck6CellValueCheck.setName("[Measures].[NthAgg6]");

        QueryCheck queryCheck6 = factory.createQueryCheck();
        queryCheck6.setName("Measure Query Check NthAgg6");
        queryCheck6.setDescription("Verify MDX query returns Measure data for NthAgg6");
        queryCheck6.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg6])");
        queryCheck6.setQueryLanguage(QueryLanguage.MDX);
        queryCheck6.setExpectedColumnCount(1);
        queryCheck6.getCellChecks().add(queryCheck6CellValueCheck);
        queryCheck6.setEnabled(true);

        CellValueCheck queryCheck7CellValueCheck = factory.createCellValueCheck();
        queryCheck7CellValueCheck.setName("[Measures].[NthAgg7]");

        QueryCheck queryCheck7 = factory.createQueryCheck();
        queryCheck7.setName("Measure Query Check NthAgg7");
        queryCheck7.setDescription("Verify MDX query returns Measure data for NthAgg7");
        queryCheck7.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[NthAgg7])");
        queryCheck7.setQueryLanguage(QueryLanguage.MDX);
        queryCheck7.setExpectedColumnCount(1);
        queryCheck7.getCellChecks().add(queryCheck7CellValueCheck);
        queryCheck7.setEnabled(true);

        // Create database column checks for the Fact table
        DatabaseColumnAttributeCheck columnAttributeCheckId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckId = factory.createDatabaseColumnCheck();
        columnCheckId.setName("Database Column Check ID");
        columnCheckId.setColumnName("ID");
        columnCheckId.getColumnAttributeChecks().add(columnAttributeCheckId);

        DatabaseColumnAttributeCheck columnAttributeCheckValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnAttributeCheckValue);

        DatabaseColumnAttributeCheck columnAttributeCheckName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckName = factory.createDatabaseColumnCheck();
        columnCheckName.setName("Database Column Check NAME");
        columnCheckName.setColumnName("NAME");
        columnCheckName.getColumnAttributeChecks().add(columnAttributeCheckName);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckName);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Aggregator Nth");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Nth");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Nth' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Nth");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getQueryChecks().add(queryCheck4);
        catalogCheck.getQueryChecks().add(queryCheck5);
        catalogCheck.getQueryChecks().add(queryCheck6);
        catalogCheck.getQueryChecks().add(queryCheck7);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Nth");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Nth");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Nth");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Nth");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
