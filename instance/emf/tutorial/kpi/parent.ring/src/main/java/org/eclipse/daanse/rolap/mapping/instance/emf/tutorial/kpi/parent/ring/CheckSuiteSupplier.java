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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.parent.ring;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
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
 * Provides a check suite for the KPI parent ring tutorial.
 * Checks that the catalog, cube, measure, and KPIs with circular parent relationships exist and are accessible.
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

        // Create KPI1 attribute check (value)
        KPIAttributeCheck kpi1ValueCheck = factory.createKPIAttributeCheck();
        kpi1ValueCheck.setAttributeType(KPIAttribute.VALUE);
        kpi1ValueCheck.setExpectedValue("[Measures].[Measure1-Sum]");

        // Create KPI1 check
        KPICheck kpi1Check = factory.createKPICheck();
        kpi1Check.setName("KPI Check for Kpi1");
        kpi1Check.setDescription("Check KPI 'Kpi1' with parent Kpi3 (circular reference)");
        kpi1Check.setKpiName("Kpi1");
        kpi1Check.getKpiAttributeChecks().add(kpi1ValueCheck);

        // Create KPI2 attribute check (value)
        KPIAttributeCheck kpi2ValueCheck = factory.createKPIAttributeCheck();
        kpi2ValueCheck.setAttributeType(KPIAttribute.VALUE);
        kpi2ValueCheck.setExpectedValue("[Measures].[Measure1-Sum]");

        // Create KPI2 check
        KPICheck kpi2Check = factory.createKPICheck();
        kpi2Check.setName("KPI Check for Kpi2");
        kpi2Check.setDescription("Check KPI 'Kpi2' with parent Kpi1");
        kpi2Check.setKpiName("Kpi2");
        kpi2Check.getKpiAttributeChecks().add(kpi2ValueCheck);

        // Create KPI3 attribute check (value)
        KPIAttributeCheck kpi3ValueCheck = factory.createKPIAttributeCheck();
        kpi3ValueCheck.setAttributeType(KPIAttribute.VALUE);
        kpi3ValueCheck.setExpectedValue("[Measures].[Measure1-Sum]");

        // Create KPI3 check
        KPICheck kpi3Check = factory.createKPICheck();
        kpi3Check.setName("KPI Check for Kpi3");
        kpi3Check.setDescription("Check KPI 'Kpi3' with parent Kpi1 (creates circular reference)");
        kpi3Check.setKpiName("Kpi3");
        kpi3Check.getKpiAttributeChecks().add(kpi3ValueCheck);

        // Create cube check with measure and KPI checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube Kpi");
        cubeCheck.setDescription("Check that cube 'Cube Kpi' exists");
        cubeCheck.setCubeName("Cube Kpi");
        cubeCheck.getMeasureChecks().add(measureSumCheck);
        cubeCheck.getKpiChecks().add(kpi1Check);
        cubeCheck.getKpiChecks().add(kpi2Check);
        cubeCheck.getKpiChecks().add(kpi3Check);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1-Sum]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube Kpi] WHERE ([Measures].[Measure1-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnValueTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - KPI Parent Ring");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - KPI Parent Ring");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - KPI Parent Ring' exists with circular KPI parent relationships");
        catalogCheck.setCatalogName("Daanse Tutorial - KPI Parent Ring");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - KPI Parent Ring");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - KPI Parent Ring");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - KPI Parent Ring");
        suite.setDescription("Check suite for the Daanse Tutorial - KPI Parent Ring (circular parent references)");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
