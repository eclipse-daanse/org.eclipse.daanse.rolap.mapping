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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.virtualcube;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.KPIAttribute;
import org.eclipse.daanse.olap.check.model.check.KPIAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.KPICheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the KPI virtual cube tutorial.
 * Checks that the catalog, physical cubes, virtual cube, measures, calculated members, and KPIs exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks for Cube1
        MeasureCheck measureCube1Check = factory.createMeasureCheck();
        measureCube1Check.setName("MeasureCheck-MeasureCube1");
        measureCube1Check.setDescription("Check that measure 'MeasureCube1' exists");
        measureCube1Check.setMeasureName("MeasureCube1");

        // Create dimension check for Cube1
        DimensionCheck dimensionCube1Check = factory.createDimensionCheck();
        dimensionCube1Check.setName("DimensionCheck-Cube1Dimension1");
        dimensionCube1Check.setDescription("Check that dimension 'Cube1Dimension1' exists");
        dimensionCube1Check.setDimensionName("Cube1Dimension1");

        // Create Cube1 check
        CubeCheck cube1Check = factory.createCubeCheck();
        cube1Check.setName("CubeCheck-Cube1");
        cube1Check.setDescription("Check that cube 'Cube1' exists");
        cube1Check.setCubeName("Cube1");
        cube1Check.getMeasureChecks().add(measureCube1Check);
        cube1Check.getDimensionChecks().add(dimensionCube1Check);

        // Create measure checks for Cube2
        MeasureCheck measureCube2Check = factory.createMeasureCheck();
        measureCube2Check.setName("MeasureCheck-MeasureCube2");
        measureCube2Check.setDescription("Check that measure 'MeasureCube2' exists");
        measureCube2Check.setMeasureName("MeasureCube2");

        // Create dimension check for Cube2
        DimensionCheck dimensionCube2Check = factory.createDimensionCheck();
        dimensionCube2Check.setName("DimensionCheck-Cube2Dimension1");
        dimensionCube2Check.setDescription("Check that dimension 'Cube2Dimension1' exists");
        dimensionCube2Check.setDimensionName("Cube2Dimension1");

        // Create Cube2 check
        CubeCheck cube2Check = factory.createCubeCheck();
        cube2Check.setName("CubeCheck-Cube2");
        cube2Check.setDescription("Check that cube 'Cube2' exists");
        cube2Check.setCubeName("Cube2");
        cube2Check.getMeasureChecks().add(measureCube2Check);
        cube2Check.getDimensionChecks().add(dimensionCube2Check);

        // Create measure checks for Virtual Cube (includes measures from both physical cubes)
        MeasureCheck vCubeMeasure1Check = factory.createMeasureCheck();
        vCubeMeasure1Check.setName("MeasureCheck-MeasureCube1");
        vCubeMeasure1Check.setDescription("Check that measure 'MeasureCube1' exists in virtual cube");
        vCubeMeasure1Check.setMeasureName("MeasureCube1");

        MeasureCheck vCubeMeasure2Check = factory.createMeasureCheck();
        vCubeMeasure2Check.setName("MeasureCheck-MeasureCube2");
        vCubeMeasure2Check.setDescription("Check that measure 'MeasureCube2' exists in virtual cube");
        vCubeMeasure2Check.setMeasureName("MeasureCube2");

        // Create calculated member checks
        MeasureCheck calculatedValueCheck = factory.createMeasureCheck();
        calculatedValueCheck.setName("MeasureCheck-CalculatedValue");
        calculatedValueCheck.setDescription("Check that calculated member 'CalculatedValue' exists");
        calculatedValueCheck.setMeasureName("CalculatedValue");

        MeasureCheck calculatedTrendCheck = factory.createMeasureCheck();
        calculatedTrendCheck.setName("MeasureCheck-CalculatedTrend");
        calculatedTrendCheck.setDescription("Check that calculated member 'CalculatedTrend' exists");
        calculatedTrendCheck.setMeasureName("CalculatedTrend");

        // Create KPI attribute checks
        KPIAttributeCheck kpiValueCheck = factory.createKPIAttributeCheck();
        kpiValueCheck.setAttributeType(KPIAttribute.VALUE);
        kpiValueCheck.setExpectedValue("[Measures].[CalculatedValue]");

        KPIAttributeCheck kpiTrendCheck = factory.createKPIAttributeCheck();
        kpiTrendCheck.setAttributeType(KPIAttribute.TREND);
        kpiTrendCheck.setExpectedValue("[Measures].[CalculatedTrend]");

        KPIAttributeCheck kpiDisplayFolderCheck = factory.createKPIAttributeCheck();
        kpiDisplayFolderCheck.setAttributeType(KPIAttribute.DISPLAY_FOLDER);
        kpiDisplayFolderCheck.setExpectedValue("Kpi1Folder1\\Kpi1Folder3");

        // Create KPI check
        KPICheck kpiCheck = factory.createKPICheck();
        kpiCheck.setName("KPI Check for Kpi1");
        kpiCheck.setDescription("Check KPI 'Kpi1' in virtual cube with calculated members");
        kpiCheck.setKpiName("Kpi1");
        kpiCheck.getKpiAttributeChecks().add(kpiValueCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiTrendCheck);
        kpiCheck.getKpiAttributeChecks().add(kpiDisplayFolderCheck);

        // Create dimension checks for Virtual Cube
        DimensionCheck vCubeDimension1Check = factory.createDimensionCheck();
        vCubeDimension1Check.setName("DimensionCheck-Cube1Dimension1");
        vCubeDimension1Check.setDescription("Check that dimension 'Cube1Dimension1' exists in virtual cube");
        vCubeDimension1Check.setDimensionName("Cube1Dimension1");

        DimensionCheck vCubeDimension2Check = factory.createDimensionCheck();
        vCubeDimension2Check.setName("DimensionCheck-Cube2Dimension1");
        vCubeDimension2Check.setDescription("Check that dimension 'Cube2Dimension1' exists in virtual cube");
        vCubeDimension2Check.setDimensionName("Cube2Dimension1");

        // Create Virtual Cube check
        CubeCheck vCubeCheck = factory.createCubeCheck();
        vCubeCheck.setName("CubeCheck-Cube1Cube2Kpi");
        vCubeCheck.setDescription("Check that virtual cube 'Cube1Cube2Kpi' exists with KPI");
        vCubeCheck.setCubeName("Cube1Cube2Kpi");
        vCubeCheck.getMeasureChecks().add(vCubeMeasure1Check);
        vCubeCheck.getMeasureChecks().add(vCubeMeasure2Check);
        vCubeCheck.getMeasureChecks().add(calculatedValueCheck);
        vCubeCheck.getMeasureChecks().add(calculatedTrendCheck);
        vCubeCheck.getKpiChecks().add(kpiCheck);
        vCubeCheck.getDimensionChecks().add(vCubeDimension1Check);
        vCubeCheck.getDimensionChecks().add(vCubeDimension2Check);

        // Create query check for virtual cube
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[CalculatedValue]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Virtual Cube Query Check");
        queryCheck.setDescription("Verify MDX query returns calculated member data");
        queryCheck.setQuery("SELECT FROM [Cube1Cube2Kpi] WHERE ([Measures].[CalculatedValue])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
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
        columnValueNumericTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValueNumeric = factory.createDatabaseColumnCheck();
        columnCheckValueNumeric.setName("Database Column Check VALUE_NUMERIC");
        columnCheckValueNumeric.setColumnName("VALUE_NUMERIC");
        columnCheckValueNumeric.getColumnAttributeChecks().add(columnValueNumericTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValueNumeric);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - KPI Virtual Cube");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with all cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - KPI Virtual Cube");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - KPI Virtual Cube' exists with physical and virtual cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - KPI Virtual Cube");
        catalogCheck.getCubeChecks().add(cube1Check);
        catalogCheck.getCubeChecks().add(cube2Check);
        catalogCheck.getCubeChecks().add(vCubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - KPI Virtual Cube");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - KPI Virtual Cube");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - KPI Virtual Cube");
        suite.setDescription("Check suite for the Daanse Tutorial - KPI Virtual Cube with calculated members");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
