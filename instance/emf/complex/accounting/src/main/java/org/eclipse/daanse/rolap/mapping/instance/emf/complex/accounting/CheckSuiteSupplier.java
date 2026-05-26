/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.accounting;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;

import org.osgi.service.component.annotations.Component;

/**
 * Check suite for the Accounting complex mapping. Asserts that the catalog, its
 * single cube, all measures, dimensions/hierarchies/levels, and the full
 * database schema (tables + column types) materialise as expected.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Accounting";
    private static final String CUBE_NAME = "Accounting";

    @Override
    public OlapCheckSuite get() {
        DimensionCheck yearDim = createDimensionCheck("Year", createHierarchyCheck("Year", createLevelCheck("Year")));
        DimensionCheck planStageDim = createDimensionCheck("PlanStage",
                createHierarchyCheck("PlanStage", createLevelCheck("PlanStage")));
        DimensionCheck accountDim = createDimensionCheck("Account",
                createHierarchyCheck("Account",
                        createLevelCheck("Category"),
                        createLevelCheck("Group"),
                        createLevelCheck("Account")));
        DimensionCheck orgUnitDim = createDimensionCheck("OrgUnit", createHierarchyCheck("OrgUnit",
                createLevelCheck("L1"), createLevelCheck("L2"), createLevelCheck("L3")));
        DimensionCheck budgetDim = createDimensionCheck("Budget",
                createHierarchyCheck("Budget", createLevelCheck("Budget")));

        MeasureCheck amountIst = createMeasureCheck("AmountIst");
        MeasureCheck amountPlan = createMeasureCheck("AmountPlan");
        MeasureCheck comments = createMeasureCheck("Comments");

        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists with all dimensions and measures");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(amountIst);
        cubeCheck.getMeasureChecks().add(amountPlan);
        cubeCheck.getMeasureChecks().add(comments);
        cubeCheck.getDimensionChecks().add(yearDim);
        cubeCheck.getDimensionChecks().add(planStageDim);
        cubeCheck.getDimensionChecks().add(accountDim);
        cubeCheck.getDimensionChecks().add(orgUnitDim);
        cubeCheck.getDimensionChecks().add(budgetDim);

        DatabaseTableCheck bookingTable = createTableCheck("BOOKING", createColumnCheck("BOOKING_ID", "INTEGER"),
                createColumnCheck("YEAR_KEY", "INTEGER"), createColumnCheck("PLAN_STAGE_KEY", "VARCHAR"),
                createColumnCheck("ACCOUNT_KEY", "VARCHAR"), createColumnCheck("ORG_UNIT_KEY", "VARCHAR"),
                createColumnCheck("BUDGET_KEY", "VARCHAR"), createColumnCheck("AMOUNT_IST", "INTEGER"),
                createColumnCheck("AMOUNT_PLAN", "INTEGER"), createColumnCheck("COMMENT", "VARCHAR"));

        DatabaseTableCheck bookingWbTable = createTableCheck("BOOKINGWB", createColumnCheck("ID", "VARCHAR"),
                createColumnCheck("USER", "VARCHAR"), createColumnCheck("YEAR_KEY", "INTEGER"),
                createColumnCheck("PLAN_STAGE_KEY", "VARCHAR"), createColumnCheck("ACCOUNT_KEY", "VARCHAR"),
                createColumnCheck("ORG_UNIT_KEY", "VARCHAR"), createColumnCheck("BUDGET_KEY", "VARCHAR"),
                createColumnCheck("AMOUNT_PLAN", "INTEGER"), createColumnCheck("COMMENT", "VARCHAR"));

        DatabaseTableCheck accountTable = createTableCheck("ACCOUNT",
                createColumnCheck("L1_KEY", "VARCHAR"),
                createColumnCheck("L1_NAME", "VARCHAR"),
                createColumnCheck("L2_KEY", "VARCHAR"),
                createColumnCheck("L2_NAME", "VARCHAR"),
                createColumnCheck("L3_KEY", "VARCHAR"),
                createColumnCheck("L3_NAME", "VARCHAR"));

        DatabaseTableCheck yearTable = createTableCheck("YEAR", createColumnCheck("YEAR_KEY", "INTEGER"),
                createColumnCheck("YEAR_NAME", "VARCHAR"));

        DatabaseTableCheck planStageTable = createTableCheck("PLANSTAGE", createColumnCheck("KEY", "VARCHAR"),
                createColumnCheck("NAME", "VARCHAR"), createColumnCheck("YEAR_KEY", "INTEGER"),
                createColumnCheck("ORDINAL", "INTEGER"));

        DatabaseTableCheck orgUnitTable = createTableCheck("ORGUNIT", createColumnCheck("L1_KEY", "VARCHAR"),
                createColumnCheck("L1_NAME", "VARCHAR"), createColumnCheck("L2_KEY", "VARCHAR"),
                createColumnCheck("L2_NAME", "VARCHAR"), createColumnCheck("L3_KEY", "VARCHAR"),
                createColumnCheck("L3_NAME", "VARCHAR"));

        DatabaseTableCheck budgetTable = createTableCheck("BUDGET", createColumnCheck("KEY", "VARCHAR"),
                createColumnCheck("NAME", "VARCHAR"));

        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for Accounting mapping");
        databaseSchemaCheck.getTableChecks().add(bookingTable);
        databaseSchemaCheck.getTableChecks().add(bookingWbTable);
        databaseSchemaCheck.getTableChecks().add(accountTable);
        databaseSchemaCheck.getTableChecks().add(yearTable);
        databaseSchemaCheck.getTableChecks().add(planStageTable);
        databaseSchemaCheck.getTableChecks().add(orgUnitTable);
        databaseSchemaCheck.getTableChecks().add(budgetTable);

        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with cube and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for the Accounting mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Accounting Example Suite");
        suite.setDescription("Check suite for the Accounting complex mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        for (HierarchyCheck hc : hierarchyChecks) {
            dimensionCheck.getHierarchyChecks().add(hc);
        }
        return dimensionCheck;
    }

    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        for (LevelCheck lc : levelChecks) {
            hierarchyCheck.getLevelChecks().add(lc);
        }
        return hierarchyCheck;
    }

    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }

    private DatabaseColumnCheck createColumnCheck(String columnName, String columnType) {
        DatabaseColumnAttributeCheck columnTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTypeCheck.setExpectedValue(columnType);

        DatabaseColumnCheck columnCheck = factory.createDatabaseColumnCheck();
        columnCheck.setName("Database Column Check " + columnName);
        columnCheck.setColumnName(columnName);
        columnCheck.getColumnAttributeChecks().add(columnTypeCheck);
        return columnCheck;
    }

    private DatabaseTableCheck createTableCheck(String tableName, DatabaseColumnCheck... columnChecks) {
        DatabaseTableCheck tableCheck = factory.createDatabaseTableCheck();
        tableCheck.setName("Database Table Check " + tableName);
        tableCheck.setTableName(tableName);
        for (DatabaseColumnCheck cc : columnChecks) {
            tableCheck.getColumnChecks().add(cc);
        }
        return tableCheck;
    }
}
