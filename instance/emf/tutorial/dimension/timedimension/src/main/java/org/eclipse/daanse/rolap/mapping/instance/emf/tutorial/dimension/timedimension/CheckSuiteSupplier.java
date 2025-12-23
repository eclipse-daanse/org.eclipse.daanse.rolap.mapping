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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.timedimension;

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
 * Provides a check suite for the time dimension tutorial.
 * Checks that the catalog, cube, and time dimension with temporal levels exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {

        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure-Sum");
        measureCheck.setDescription("Check that measure 'Measure-Sum' exists");
        measureCheck.setMeasureName("Measure-Sum");
        measureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck1);

        // Create level checks for Time dimension
        LevelCheck levelYearsCheck = factory.createLevelCheck();
        levelYearsCheck.setName("LevelCheck for Years");
        levelYearsCheck.setLevelName("Years");

        LevelCheck levelQuartersCheck = factory.createLevelCheck();
        levelQuartersCheck.setName("LevelCheck for Quarters");
        levelQuartersCheck.setLevelName("Quarters");

        LevelCheck levelMonthsCheck = factory.createLevelCheck();
        levelMonthsCheck.setName("LevelCheck for Months");
        levelMonthsCheck.setLevelName("Months");

        LevelCheck levelWeekCheck = factory.createLevelCheck();
        levelWeekCheck.setName("LevelCheck for Week");
        levelWeekCheck.setLevelName("Week");

        LevelCheck levelDayCheck = factory.createLevelCheck();
        levelDayCheck.setName("LevelCheck for Day");
        levelDayCheck.setLevelName("Day");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        HierarchyAttributeCheck hierarchyAllMemberNameCheck = factory.createHierarchyAttributeCheck();
        hierarchyAllMemberNameCheck.setAttributeType(HierarchyAttribute.ALL_MEMBER_NAME);
        hierarchyAllMemberNameCheck.setExpectedValue("All Years");

        // Create hierarchy check for Time dimension
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Time");
        hierarchyCheck.setHierarchyName("Time");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAllMemberNameCheck);
        hierarchyCheck.getLevelChecks().add(levelYearsCheck);
        hierarchyCheck.getLevelChecks().add(levelQuartersCheck);
        hierarchyCheck.getLevelChecks().add(levelMonthsCheck);
        hierarchyCheck.getLevelChecks().add(levelWeekCheck);
        hierarchyCheck.getLevelChecks().add(levelDayCheck);

        // Create dimension check for Time
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Time");
        dimensionCheck.setDimensionName("Time");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-CubeTimeDimension");
        cubeCheck.setDescription("Check that cube 'CubeTimeDimension' exists");
        cubeCheck.setCubeName("CubeTimeDimension");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure-Sum]");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [CubeTimeDimension] WHERE ([Measures].[Measure-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnDateKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDateKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDateKeyTypeCheck.setExpectedValue("TIMESTAMP");

        DatabaseColumnCheck columnCheckDateKey = factory.createDatabaseColumnCheck();
        columnCheckDateKey.setName("Database Column Check DATE_KEY");
        columnCheckDateKey.setColumnName("DATE_KEY");
        columnCheckDateKey.getColumnAttributeChecks().add(columnDateKeyTypeCheck);

        DatabaseColumnAttributeCheck columnValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnValueTypeCheck);

        DatabaseColumnAttributeCheck columnYearIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnYearIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnYearIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckYearId = factory.createDatabaseColumnCheck();
        columnCheckYearId.setName("Database Column Check YEAR_ID");
        columnCheckYearId.setColumnName("YEAR_ID");
        columnCheckYearId.getColumnAttributeChecks().add(columnYearIdTypeCheck);

        DatabaseColumnAttributeCheck columnQtrIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnQtrIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnQtrIdTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckQtrId = factory.createDatabaseColumnCheck();
        columnCheckQtrId.setName("Database Column Check QTR_ID");
        columnCheckQtrId.setColumnName("QTR_ID");
        columnCheckQtrId.getColumnAttributeChecks().add(columnQtrIdTypeCheck);

        DatabaseColumnAttributeCheck columnQtrNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnQtrNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnQtrNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckQtrName = factory.createDatabaseColumnCheck();
        columnCheckQtrName.setName("Database Column Check QTR_NAME");
        columnCheckQtrName.setColumnName("QTR_NAME");
        columnCheckQtrName.getColumnAttributeChecks().add(columnQtrNameTypeCheck);

        DatabaseColumnAttributeCheck columnMonthIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMonthIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMonthIdTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMonthId = factory.createDatabaseColumnCheck();
        columnCheckMonthId.setName("Database Column Check MONTH_ID");
        columnCheckMonthId.setColumnName("MONTH_ID");
        columnCheckMonthId.getColumnAttributeChecks().add(columnMonthIdTypeCheck);

        DatabaseColumnAttributeCheck columnMonthNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMonthNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMonthNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMonthName = factory.createDatabaseColumnCheck();
        columnCheckMonthName.setName("Database Column Check MONTH_NAME");
        columnCheckMonthName.setColumnName("MONTH_NAME");
        columnCheckMonthName.getColumnAttributeChecks().add(columnMonthNameTypeCheck);

        DatabaseColumnAttributeCheck columnWeekInMonthTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnWeekInMonthTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnWeekInMonthTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckWeekInMonth = factory.createDatabaseColumnCheck();
        columnCheckWeekInMonth.setName("Database Column Check WEEK_IN_MONTH");
        columnCheckWeekInMonth.setColumnName("WEEK_IN_MONTH");
        columnCheckWeekInMonth.getColumnAttributeChecks().add(columnWeekInMonthTypeCheck);

        DatabaseColumnAttributeCheck columnDayInMonthTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDayInMonthTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDayInMonthTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckDayInMonth = factory.createDatabaseColumnCheck();
        columnCheckDayInMonth.setName("Database Column Check DAY_IN_MONTH");
        columnCheckDayInMonth.setColumnName("DAY_IN_MONTH");
        columnCheckDayInMonth.getColumnAttributeChecks().add(columnDayInMonthTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckDateKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckYearId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckQtrId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckQtrName);
        databaseTableCheckFact.getColumnChecks().add(columnCheckMonthId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckMonthName);
        databaseTableCheckFact.getColumnChecks().add(columnCheckWeekInMonth);
        databaseTableCheckFact.getColumnChecks().add(columnCheckDayInMonth);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Dimension Time Dimension");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Dimension Time Dimension");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Dimension Time Dimension' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Dimension Time Dimension");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Dimension Time Dimension");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Dimension Time Dimension");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Dimension Time Dimension");
        suite.setDescription("Check suite for the Daanse Tutorial - Dimension Time Dimension");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
