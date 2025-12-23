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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.all;

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
import org.eclipse.daanse.olap.check.model.check.KPIAttribute;
import org.eclipse.daanse.olap.check.model.check.KPIAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.KPICheck;
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
 * Provides a check suite for the unique key level name hierarchy tutorial.
 * Checks that the catalog, cube, measure, and hierarchy with unique key level name optimization exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureSumCheck = factory.createMeasureCheck();
        measureSumCheck.setName("MeasureCheck-Measure1-Sum");
        measureSumCheck.setDescription("Check that measure 'Measure1-Sum' exists");
        measureSumCheck.setMeasureName("Measure1-Sum");

        MeasureCheck measureCountCheck = factory.createMeasureCheck();
        measureCountCheck.setName("MeasureCheck-Measure2-Count");
        measureCountCheck.setDescription("Check that measure 'Measure2-Count' exists");
        measureCountCheck.setMeasureName("Measure2-Count");

        KPIAttributeCheck kpiAttributeValueCheck = factory.createKPIAttributeCheck();
        kpiAttributeValueCheck.setAttributeType(KPIAttribute.VALUE);
        kpiAttributeValueCheck.setExpectedValue("[Measures].[CalculatedValue]");

        KPIAttributeCheck kpiAttributeGoalCheck = factory.createKPIAttributeCheck();
        kpiAttributeGoalCheck.setAttributeType(KPIAttribute.GOAL);
        kpiAttributeGoalCheck.setExpectedValue("[Measures].[CalculatedGoal]");

        KPIAttributeCheck kpiAttributeStatusCheck = factory.createKPIAttributeCheck();
        kpiAttributeStatusCheck.setAttributeType(KPIAttribute.STATUS);
        kpiAttributeStatusCheck.setExpectedValue("[Measures].[CalculatedStatus]");

        KPIAttributeCheck kpiAttributeTrendCheck = factory.createKPIAttributeCheck();
        kpiAttributeTrendCheck.setAttributeType(KPIAttribute.TREND);
        kpiAttributeTrendCheck.setExpectedValue("[Measures].[CalculatedTrend]");

        KPIAttributeCheck kpiAttributeWeightCheck = factory.createKPIAttributeCheck();
        kpiAttributeWeightCheck.setAttributeType(KPIAttribute.WEIGHT);
        kpiAttributeWeightCheck.setExpectedValue("[Measures].[CalculatedValue]");

        KPIAttributeCheck kpiAttributeCurrentTimeMemberCheck = factory.createKPIAttributeCheck();
        kpiAttributeCurrentTimeMemberCheck.setAttributeType(KPIAttribute.CURRENT_TIME_MEMBER);
        kpiAttributeCurrentTimeMemberCheck.setExpectedValue("[Measures].[CalculatedValue]");

        KPIAttributeCheck kpiAttributeDisplayFolderCheck = factory.createKPIAttributeCheck();
        kpiAttributeDisplayFolderCheck.setAttributeType(KPIAttribute.DISPLAY_FOLDER);
        kpiAttributeDisplayFolderCheck.setExpectedValue("Kpi1Folder1\\Kpi1Folder2");

        KPIAttributeCheck kpiAttributeStatusGraphicCheck = factory.createKPIAttributeCheck();
        kpiAttributeStatusGraphicCheck.setAttributeType(KPIAttribute.STATUS_GRAPHIC);
        kpiAttributeStatusGraphicCheck.setExpectedValue("Cylinder");

        KPIAttributeCheck kpiAttributeTrendGraphicCheck = factory.createKPIAttributeCheck();
        kpiAttributeTrendGraphicCheck.setAttributeType(KPIAttribute.TREND_GRAPHIC);
        kpiAttributeTrendGraphicCheck.setExpectedValue("Smiley Face");

        // Create level checks
        KPICheck kpiCheck = factory.createKPICheck();
        kpiCheck.setName("kpi Check for Kpi1");
        kpiCheck.setKpiName("Kpi1");
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeValueCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeGoalCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeStatusCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeTrendCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeWeightCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeCurrentTimeMemberCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeDisplayFolderCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiAttributeStatusGraphicCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-CubeKPI");
        cubeCheck.setDescription("Check that cube 'CubeKPI' exists");
        cubeCheck.setCubeName("CubeKPI");
        cubeCheck.getMeasureChecks().add(measureSumCheck);
        cubeCheck.getMeasureChecks().add(measureCountCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1-Sum]");
        queryCheckCellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [CubeKPI] WHERE ([Measures].[Measure1-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        CellValueCheck queryCheckCellValueCheck2 = factory.createCellValueCheck();
        queryCheckCellValueCheck2.setName("[Measures].[Measure2-Count]");
        queryCheckCellValueCheck2.setExpectedValue("2");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check");
        queryCheck2.setDescription("Verify MDX query returns Measure data");
        queryCheck2.setQuery("SELECT FROM [CubeKPI] WHERE ([Measures].[Measure2-Count])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheckCellValueCheck2);
        queryCheck2.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnDimKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDimKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDimKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckDimKey = factory.createDatabaseColumnCheck();
        columnCheckDimKey.setName("Database Column Check DIM_KEY");
        columnCheckDimKey.setColumnName("DIM_KEY");
        columnCheckDimKey.getColumnAttributeChecks().add(columnDimKeyTypeCheck);

        DatabaseColumnAttributeCheck columnValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnValueTypeCheck);

        DatabaseColumnAttributeCheck columnValueNumerucTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueNumerucTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueNumerucTypeCheck.setExpectedValue("NUMERIC");

        DatabaseColumnCheck columnCheckValueNumeric = factory.createDatabaseColumnCheck();
        columnCheckValueNumeric.setName("Database Column Check VALUE");
        columnCheckValueNumeric.setColumnName("VALUE_NUMERIC");
        columnCheckValueNumeric.getColumnAttributeChecks().add(columnValueNumerucTypeCheck);


        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckDimKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValueNumeric);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - KPI All");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - KPI All");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - KPI All' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - KPI All");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - KPI All");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - KPI All");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - KPI All");
        suite.setDescription("Check suite for the Daanse Tutorial - KPI All");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
