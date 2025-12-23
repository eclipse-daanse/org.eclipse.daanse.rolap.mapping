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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.percentile;

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
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the percentile aggregator cube mapping.
 * Checks that the catalog, cube, and percentile measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks for all percentile measures
        MeasureCheck percentileDisc025MeasureCheck = factory.createMeasureCheck();
        percentileDisc025MeasureCheck.setName("MeasureCheck-Percentile disc 0.25");
        percentileDisc025MeasureCheck.setDescription("Check that measure 'Percentile disc 0.25' exists");
        percentileDisc025MeasureCheck.setMeasureName("Percentile disc 0.25");

        MeasureCheck percentileCont025MeasureCheck = factory.createMeasureCheck();
        percentileCont025MeasureCheck.setName("MeasureCheck-Percentile cont 0.25");
        percentileCont025MeasureCheck.setDescription("Check that measure 'Percentile cont 0.25' exists");
        percentileCont025MeasureCheck.setMeasureName("Percentile cont 0.25");

        MeasureCheck percentileDisc042MeasureCheck = factory.createMeasureCheck();
        percentileDisc042MeasureCheck.setName("MeasureCheck-Percentile disc 0.42");
        percentileDisc042MeasureCheck.setDescription("Check that measure 'Percentile disc 0.42' exists");
        percentileDisc042MeasureCheck.setMeasureName("Percentile disc 0.42");

        MeasureCheck percentileCont042MeasureCheck = factory.createMeasureCheck();
        percentileCont042MeasureCheck.setName("MeasureCheck-Percentile cont 0.42");
        percentileCont042MeasureCheck.setDescription("Check that measure 'Percentile cont 0.42' exists");
        percentileCont042MeasureCheck.setMeasureName("Percentile cont 0.42");

        MeasureCheck percentileDisc05MeasureCheck = factory.createMeasureCheck();
        percentileDisc05MeasureCheck.setName("MeasureCheck-Percentile disc 0.5");
        percentileDisc05MeasureCheck.setDescription("Check that measure 'Percentile disc 0.5' exists");
        percentileDisc05MeasureCheck.setMeasureName("Percentile disc 0.5");

        MeasureCheck percentileCont05MeasureCheck = factory.createMeasureCheck();
        percentileCont05MeasureCheck.setName("MeasureCheck-Percentile cont 0.5");
        percentileCont05MeasureCheck.setDescription("Check that measure 'Percentile cont 0.5' exists");
        percentileCont05MeasureCheck.setMeasureName("Percentile cont 0.5");

        MeasureCheck percentileDisc075MeasureCheck = factory.createMeasureCheck();
        percentileDisc075MeasureCheck.setName("MeasureCheck-Percentile disc 0.75");
        percentileDisc075MeasureCheck.setDescription("Check that measure 'Percentile disc 0.75' exists");
        percentileDisc075MeasureCheck.setMeasureName("Percentile disc 0.75");

        MeasureCheck percentileCont075MeasureCheck = factory.createMeasureCheck();
        percentileCont075MeasureCheck.setName("MeasureCheck-Percentile cont 0.75");
        percentileCont075MeasureCheck.setDescription("Check that measure 'Percentile cont 0.75' exists");
        percentileCont075MeasureCheck.setMeasureName("Percentile cont 0.75");

        MeasureCheck percentileDisc100MeasureCheck = factory.createMeasureCheck();
        percentileDisc100MeasureCheck.setName("MeasureCheck-Percentile disc 1.00");
        percentileDisc100MeasureCheck.setDescription("Check that measure 'Percentile disc 1.00' exists");
        percentileDisc100MeasureCheck.setMeasureName("Percentile disc 1.00");

        MeasureCheck percentileCont100MeasureCheck = factory.createMeasureCheck();
        percentileCont100MeasureCheck.setName("MeasureCheck-Percentile cont 1.00");
        percentileCont100MeasureCheck.setDescription("Check that measure 'Percentile cont 1.00' exists");
        percentileCont100MeasureCheck.setMeasureName("Percentile cont 1.00");

        // Create level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck for Level");
        levelCheck.setLevelName("Level");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Hierarchy");
        hierarchyCheck.setHierarchyName("Hierarchy");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Dim");
        dimensionCheck.setDimensionName("Dim");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresAggregatorsCube");
        cubeCheck.getMeasureChecks().add(percentileDisc025MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileCont025MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileDisc042MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileCont042MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileDisc05MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileCont05MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileDisc075MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileCont075MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileDisc100MeasureCheck);
        cubeCheck.getMeasureChecks().add(percentileCont100MeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query checks for each percentile measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Percentile disc 0.25]");
        queryCheck1CellValueCheck.setExpectedValue("");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Percentile disc 0.25");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Percentile disc 0.25");
        queryCheck1.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile disc 0.25])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Percentile cont 0.25]");
        queryCheck2CellValueCheck.setExpectedValue("");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Percentile cont 0.25");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Percentile cont 0.25");
        queryCheck2.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile cont 0.25])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[Percentile disc 0.42]");
        queryCheck3CellValueCheck.setExpectedValue("");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check Percentile disc 0.42");
        queryCheck3.setDescription("Verify MDX query returns Measure data for Percentile disc 0.42");
        queryCheck3.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile disc 0.42])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        CellValueCheck queryCheck4CellValueCheck = factory.createCellValueCheck();
        queryCheck4CellValueCheck.setName("[Measures].[Percentile cont 0.42]");

        QueryCheck queryCheck4 = factory.createQueryCheck();
        queryCheck4.setName("Measure Query Check Percentile cont 0.42");
        queryCheck4.setDescription("Verify MDX query returns Measure data for Percentile cont 0.42");
        queryCheck4.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile cont 0.42])");
        queryCheck4.setQueryLanguage(QueryLanguage.MDX);
        queryCheck4.setExpectedColumnCount(1);
        queryCheck4.getCellChecks().add(queryCheck4CellValueCheck);
        queryCheck4.setEnabled(true);

        CellValueCheck queryCheck5CellValueCheck = factory.createCellValueCheck();
        queryCheck5CellValueCheck.setName("[Measures].[Percentile disc 0.5]");

        QueryCheck queryCheck5 = factory.createQueryCheck();
        queryCheck5.setName("Measure Query Check Percentile disc 0.5");
        queryCheck5.setDescription("Verify MDX query returns Measure data for Percentile disc 0.5");
        queryCheck5.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile disc 0.5])");
        queryCheck5.setQueryLanguage(QueryLanguage.MDX);
        queryCheck5.setExpectedColumnCount(1);
        queryCheck5.getCellChecks().add(queryCheck5CellValueCheck);
        queryCheck5.setEnabled(true);

        CellValueCheck queryCheck6CellValueCheck = factory.createCellValueCheck();
        queryCheck6CellValueCheck.setName("[Measures].[Percentile cont 0.5]");

        QueryCheck queryCheck6 = factory.createQueryCheck();
        queryCheck6.setName("Measure Query Check Percentile cont 0.5");
        queryCheck6.setDescription("Verify MDX query returns Measure data for Percentile cont 0.5");
        queryCheck6.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile cont 0.5])");
        queryCheck6.setQueryLanguage(QueryLanguage.MDX);
        queryCheck6.setExpectedColumnCount(1);
        queryCheck6.getCellChecks().add(queryCheck6CellValueCheck);
        queryCheck6.setEnabled(true);

        CellValueCheck queryCheck7CellValueCheck = factory.createCellValueCheck();
        queryCheck7CellValueCheck.setName("[Measures].[Percentile disc 0.75]");

        QueryCheck queryCheck7 = factory.createQueryCheck();
        queryCheck7.setName("Measure Query Check Percentile disc 0.75");
        queryCheck7.setDescription("Verify MDX query returns Measure data for Percentile disc 0.75");
        queryCheck7.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile disc 0.75])");
        queryCheck7.setQueryLanguage(QueryLanguage.MDX);
        queryCheck7.setExpectedColumnCount(1);
        queryCheck7.getCellChecks().add(queryCheck7CellValueCheck);
        queryCheck7.setEnabled(true);

        CellValueCheck queryCheck8CellValueCheck = factory.createCellValueCheck();
        queryCheck8CellValueCheck.setName("[Measures].[Percentile cont 0.75]");

        QueryCheck queryCheck8 = factory.createQueryCheck();
        queryCheck8.setName("Measure Query Check Percentile cont 0.75");
        queryCheck8.setDescription("Verify MDX query returns Measure data for Percentile cont 0.75");
        queryCheck8.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile cont 0.75])");
        queryCheck8.setQueryLanguage(QueryLanguage.MDX);
        queryCheck8.setExpectedColumnCount(1);
        queryCheck8.getCellChecks().add(queryCheck8CellValueCheck);
        queryCheck8.setEnabled(true);

        CellValueCheck queryCheck9CellValueCheck = factory.createCellValueCheck();
        queryCheck9CellValueCheck.setName("[Measures].[Percentile disc 1.00]");

        QueryCheck queryCheck9 = factory.createQueryCheck();
        queryCheck9.setName("Measure Query Check Percentile disc 1.00");
        queryCheck9.setDescription("Verify MDX query returns Measure data for Percentile disc 1.00");
        queryCheck9.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile disc 1.00])");
        queryCheck9.setQueryLanguage(QueryLanguage.MDX);
        queryCheck9.setExpectedColumnCount(1);
        queryCheck9.getCellChecks().add(queryCheck9CellValueCheck);
        queryCheck9.setEnabled(true);

        CellValueCheck queryCheck10CellValueCheck = factory.createCellValueCheck();
        queryCheck10CellValueCheck.setName("[Measures].[Percentile cont 1.00]");

        QueryCheck queryCheck10 = factory.createQueryCheck();
        queryCheck10.setName("Measure Query Check Percentile cont 1.00");
        queryCheck10.setDescription("Verify MDX query returns Measure data for Percentile cont 1.00");
        queryCheck10.setQuery("SELECT FROM [MeasuresAggregatorsCube] WHERE ([Measures].[Percentile cont 1.00])");
        queryCheck10.setQueryLanguage(QueryLanguage.MDX);
        queryCheck10.setExpectedColumnCount(1);
        queryCheck10.getCellChecks().add(queryCheck10CellValueCheck);
        queryCheck10.setEnabled(true);

        // Create database column checks for the Fact table
        DatabaseColumnAttributeCheck columnAttributeCheckKey = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckKey.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckKey.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnAttributeCheckKey);

        DatabaseColumnAttributeCheck columnAttributeCheckValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnAttributeCheckValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Aggregator Percentile");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Percentile");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Percentile' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Percentile");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getQueryChecks().add(queryCheck4);
        catalogCheck.getQueryChecks().add(queryCheck5);
        catalogCheck.getQueryChecks().add(queryCheck6);
        catalogCheck.getQueryChecks().add(queryCheck7);
        catalogCheck.getQueryChecks().add(queryCheck8);
        catalogCheck.getQueryChecks().add(queryCheck9);
        catalogCheck.getQueryChecks().add(queryCheck10);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Percentile");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Percentile");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Percentile");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Percentile");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
