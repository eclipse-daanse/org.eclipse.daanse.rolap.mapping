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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.base;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the minimal cube mapping.
 * Checks that the catalog, cube, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck sumMeasureCheck = factory.createMeasureCheck();
        sumMeasureCheck.setName("MeasureCheck-Sum of Value");
        sumMeasureCheck.setDescription("Check that measure 'Sum of Value' exists");
        sumMeasureCheck.setMeasureName("Sum of Value");

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);

        sumMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        MeasureCheck maxMeasureCheck = factory.createMeasureCheck();
        maxMeasureCheck.setName("MeasureCheck-Max of Value");
        maxMeasureCheck.setDescription("Check that measure 'Max of Value' exists");
        maxMeasureCheck.setMeasureName("Max of Value");

        MeasureAttributeCheck measureMaxAttributeCheck = factory.createMeasureAttributeCheck();
        measureMaxAttributeCheck.setExpectedAggregator(AggregatorType.MAX);

        maxMeasureCheck.getMeasureAttributeChecks().add(measureMaxAttributeCheck);

        MeasureCheck minMeasureCheck = factory.createMeasureCheck();
        minMeasureCheck.setName("MeasureCheck-Min of Value");
        minMeasureCheck.setDescription("Check that measure 'Min of Value' exists");
        minMeasureCheck.setMeasureName("Min of Value");

        MeasureAttributeCheck measureMinAttributeCheck = factory.createMeasureAttributeCheck();
        measureMinAttributeCheck.setExpectedAggregator(AggregatorType.MIN);

        minMeasureCheck.getMeasureAttributeChecks().add(measureMinAttributeCheck);

        MeasureCheck avgMeasureCheck = factory.createMeasureCheck();
        minMeasureCheck.setName("MeasureCheck-Avg of Value");
        minMeasureCheck.setDescription("Check that measure 'Avg of Value' exists");
        minMeasureCheck.setMeasureName("Avg of Value");

        MeasureAttributeCheck measureAvgAttributeCheck = factory.createMeasureAttributeCheck();
        measureAvgAttributeCheck.setExpectedAggregator(AggregatorType.AVG);

        avgMeasureCheck.getMeasureAttributeChecks().add(measureAvgAttributeCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresAggregatorsCube");
        cubeCheck.getMeasureChecks().add(sumMeasureCheck);
        cubeCheck.getMeasureChecks().add(maxMeasureCheck);
        cubeCheck.getMeasureChecks().add(minMeasureCheck);
        cubeCheck.getMeasureChecks().add(avgMeasureCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Sum of Value]");
        queryCheck1CellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Sum of Value");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Sum of Value");
        queryCheck1.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Sum of Value])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Max of Value]");
        queryCheck2CellValueCheck.setExpectedValue("42");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Max of Value");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Max of Value");
        queryCheck2.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Max of Value])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[Min of Value]");
        queryCheck3CellValueCheck.setExpectedValue("21");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check Min of Value");
        queryCheck3.setDescription("Verify MDX query returns Measure data for Min of Value");
        queryCheck3.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Min of Value])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        CellValueCheck queryCheck4CellValueCheck = factory.createCellValueCheck();
        queryCheck4CellValueCheck.setName("[Measures].[Avg of Value]");
        queryCheck4CellValueCheck.setExpectedValue("31.5");

        QueryCheck queryCheck4 = factory.createQueryCheck();
        queryCheck4.setName("Measure Query Check Avg of Value");
        queryCheck4.setDescription("Verify MDX query returns Measure data for Avg of Value");
        queryCheck4.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Avg of Value])");
        queryCheck4.setQueryLanguage(QueryLanguage.MDX);
        queryCheck4.setExpectedColumnCount(1);
        queryCheck4.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck4.setEnabled(true);

        DatabaseColumnAttributeCheck columnAttributeCheckFactKey = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactKey.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactKey.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactKey = factory.createDatabaseColumnCheck();
        columnCheckFactKey.setName("Database Column Check KEY");
        columnCheckFactKey.setColumnName("KEY");
        columnCheckFactKey.getColumnAttributeChecks().add(columnAttributeCheckFactKey);

        DatabaseColumnAttributeCheck columnAttributeCheckFactValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactValue = factory.createDatabaseColumnCheck();
        columnCheckFactValue.setName("Database Column Check Value");
        columnCheckFactValue.setColumnName("VALUE");
        columnCheckFactValue.getColumnAttributeChecks().add(columnAttributeCheckFactValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Aggregator Base");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Base");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Base' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Base");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getQueryChecks().add(queryCheck4);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Base");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Base");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Base");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Base");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
