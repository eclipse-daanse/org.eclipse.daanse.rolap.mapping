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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.textagg;

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
 * Provides a check suite for the text aggregator cube mapping.
 * Checks that the catalog, cube, and text aggregation measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck sumOfValueMeasureCheck = factory.createMeasureCheck();
        sumOfValueMeasureCheck.setName("MeasureCheck-Sum of Value");
        sumOfValueMeasureCheck.setDescription("Check that measure 'Sum of Value' exists");
        sumOfValueMeasureCheck.setMeasureName("Sum of Value");

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);

        sumOfValueMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        MeasureCheck commentMeasureCheck = factory.createMeasureCheck();
        commentMeasureCheck.setName("MeasureCheck-Comment");
        commentMeasureCheck.setDescription("Check that measure 'Comment' exists");
        commentMeasureCheck.setMeasureName("Comment");

        // Create level checks for Town dimension
        LevelCheck levelContinentCheck = factory.createLevelCheck();
        levelContinentCheck.setName("LevelCheck for Continent");
        levelContinentCheck.setLevelName("Continent");

        LevelCheck levelCountryCheck = factory.createLevelCheck();
        levelCountryCheck.setName("LevelCheck for Country");
        levelCountryCheck.setLevelName("Country");

        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("LevelCheck for Town");
        levelTownCheck.setLevelName("Town");

        // Create hierarchy check for Town dimension
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getLevelChecks().add(levelContinentCheck);
        hierarchyTownCheck.getLevelChecks().add(levelCountryCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);

        // Create dimension check for Town
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);

        // Create level checks for Time dimension
        LevelCheck levelYearCheck = factory.createLevelCheck();
        levelYearCheck.setName("LevelCheck for Year");
        levelYearCheck.setLevelName("Year");

        LevelCheck levelMonthCheck = factory.createLevelCheck();
        levelMonthCheck.setName("LevelCheck for Month");
        levelMonthCheck.setLevelName("Month");

        // Create hierarchy check for Time dimension
        HierarchyCheck hierarchyTimeCheck = factory.createHierarchyCheck();
        hierarchyTimeCheck.setName("HierarchyCheck for TimeHierarchy");
        hierarchyTimeCheck.setHierarchyName("TimeHierarchy");
        hierarchyTimeCheck.getLevelChecks().add(levelYearCheck);
        hierarchyTimeCheck.getLevelChecks().add(levelMonthCheck);

        // Create dimension check for Time
        DimensionCheck dimensionTimeCheck = factory.createDimensionCheck();
        dimensionTimeCheck.setName("DimensionCheck for Time");
        dimensionTimeCheck.setDimensionName("Time");
        dimensionTimeCheck.getHierarchyChecks().add(hierarchyTimeCheck);

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresTextAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresTextAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresTextAggregatorsCube");
        cubeCheck.getMeasureChecks().add(sumOfValueMeasureCheck);
        cubeCheck.getMeasureChecks().add(commentMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);
        cubeCheck.getDimensionChecks().add(dimensionTimeCheck);

        // Create query checks for each measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Sum of Value]");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Sum of Value");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Sum of Value");
        queryCheck1.setQuery("SELECT FROM [MeasuresTextAggregatorsCube] WHERE ([Measures].[Sum of Value])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Comment]");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Comment");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Comment");
        queryCheck2.setQuery("SELECT FROM [MeasuresTextAggregatorsCube] WHERE ([Measures].[Comment])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

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

        DatabaseColumnAttributeCheck columnAttributeCheckCountry = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckCountry.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckCountry.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckCountry = factory.createDatabaseColumnCheck();
        columnCheckCountry.setName("Database Column Check COUNTRY");
        columnCheckCountry.setColumnName("COUNTRY");
        columnCheckCountry.getColumnAttributeChecks().add(columnAttributeCheckCountry);

        DatabaseColumnAttributeCheck columnAttributeCheckContinent = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckContinent.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckContinent.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckContinent = factory.createDatabaseColumnCheck();
        columnCheckContinent.setName("Database Column Check CONTINENT");
        columnCheckContinent.setColumnName("CONTINENT");
        columnCheckContinent.getColumnAttributeChecks().add(columnAttributeCheckContinent);

        DatabaseColumnAttributeCheck columnAttributeCheckYear = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckYear.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckYear.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckYear = factory.createDatabaseColumnCheck();
        columnCheckYear.setName("Database Column Check YEAR");
        columnCheckYear.setColumnName("YEAR");
        columnCheckYear.getColumnAttributeChecks().add(columnAttributeCheckYear);

        DatabaseColumnAttributeCheck columnAttributeCheckMonth = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckMonth.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckMonth.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckMonth = factory.createDatabaseColumnCheck();
        columnCheckMonth.setName("Database Column Check MONTH");
        columnCheckMonth.setColumnName("MONTH");
        columnCheckMonth.getColumnAttributeChecks().add(columnAttributeCheckMonth);

        DatabaseColumnAttributeCheck columnAttributeCheckMonthName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckMonthName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckMonthName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMonthName = factory.createDatabaseColumnCheck();
        columnCheckMonthName.setName("Database Column Check MONTH_NAME");
        columnCheckMonthName.setColumnName("MONTH_NAME");
        columnCheckMonthName.getColumnAttributeChecks().add(columnAttributeCheckMonthName);

        DatabaseColumnAttributeCheck columnAttributeCheckUser = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckUser.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckUser.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckUser = factory.createDatabaseColumnCheck();
        columnCheckUser.setName("Database Column Check USER");
        columnCheckUser.setColumnName("USER");
        columnCheckUser.getColumnAttributeChecks().add(columnAttributeCheckUser);

        DatabaseColumnAttributeCheck columnAttributeCheckComment = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckComment.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckComment.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckComment = factory.createDatabaseColumnCheck();
        columnCheckComment.setName("Database Column Check COMMENT");
        columnCheckComment.setColumnName("COMMENT");
        columnCheckComment.getColumnAttributeChecks().add(columnAttributeCheckComment);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckCountry);
        databaseTableCheckFact.getColumnChecks().add(columnCheckContinent);
        databaseTableCheckFact.getColumnChecks().add(columnCheckYear);
        databaseTableCheckFact.getColumnChecks().add(columnCheckMonth);
        databaseTableCheckFact.getColumnChecks().add(columnCheckMonthName);
        databaseTableCheckFact.getColumnChecks().add(columnCheckUser);
        databaseTableCheckFact.getColumnChecks().add(columnCheckComment);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Aggregator Text Agg");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Text Agg' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Text Agg");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Text Agg");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Text Agg");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
