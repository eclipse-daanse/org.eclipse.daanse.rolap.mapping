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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregation.aggregatetables;

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
 * Provides a check suite for the aggregation aggregate tables tutorial.
 * Checks that the catalog, cube, measure, and aggregation tables configuration exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Store Cost");
        measureCheck.setDescription("Check that measure 'Store Cost' exists");
        measureCheck.setMeasureName("Store Cost");

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Sales");
        cubeCheck.setDescription("Check that cube 'Sales' exists");
        cubeCheck.setCubeName("Sales");
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Store Cost]");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Store Cost Query Check");
        queryCheck.setDescription("Verify MDX query returns Store Cost data");
        queryCheck.setQuery("SELECT FROM [Sales] WHERE ([Measures].[Store Cost])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for SALES_FACT_1997 table
        DatabaseColumnAttributeCheck columnProductIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckProductId = factory.createDatabaseColumnCheck();
        columnCheckProductId.setName("Database Column Check PRODUCT_ID");
        columnCheckProductId.setColumnName("PRODUCT_ID");
        columnCheckProductId.getColumnAttributeChecks().add(columnProductIdTypeCheck);

        DatabaseColumnAttributeCheck columnStoreCostTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnStoreCostTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnStoreCostTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckStoreCost = factory.createDatabaseColumnCheck();
        columnCheckStoreCost.setName("Database Column Check STORE_COST");
        columnCheckStoreCost.setColumnName("STORE_COST");
        columnCheckStoreCost.getColumnAttributeChecks().add(columnStoreCostTypeCheck);

        DatabaseTableCheck databaseTableCheckSalesFact1997 = factory.createDatabaseTableCheck();
        databaseTableCheckSalesFact1997.setName("Database Table SALES_FACT_1997 Check");
        databaseTableCheckSalesFact1997.setTableName("SALES_FACT_1997");
        databaseTableCheckSalesFact1997.getColumnChecks().add(columnCheckProductId);
        databaseTableCheckSalesFact1997.getColumnChecks().add(columnCheckStoreCost);

        // Create database column checks for PRODUCT table
        DatabaseColumnAttributeCheck columnProductClassIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductClassIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductClassIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckProductClassId = factory.createDatabaseColumnCheck();
        columnCheckProductClassId.setName("Database Column Check PRODUCT_CLASS_ID");
        columnCheckProductClassId.setColumnName("PRODUCT_CLASS_ID");
        columnCheckProductClassId.getColumnAttributeChecks().add(columnProductClassIdTypeCheck);

        DatabaseColumnAttributeCheck columnProductProductIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductProductIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductProductIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckProductProductId = factory.createDatabaseColumnCheck();
        columnCheckProductProductId.setName("Database Column Check PRODUCT_ID (PRODUCT)");
        columnCheckProductProductId.setColumnName("PRODUCT_ID");
        columnCheckProductProductId.getColumnAttributeChecks().add(columnProductProductIdTypeCheck);

        DatabaseColumnAttributeCheck columnBrandNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnBrandNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnBrandNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckBrandName = factory.createDatabaseColumnCheck();
        columnCheckBrandName.setName("Database Column Check brand_name");
        columnCheckBrandName.setColumnName("brand_name");
        columnCheckBrandName.getColumnAttributeChecks().add(columnBrandNameTypeCheck);

        DatabaseColumnAttributeCheck columnProductNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckProductName = factory.createDatabaseColumnCheck();
        columnCheckProductName.setName("Database Column Check product_name");
        columnCheckProductName.setColumnName("product_name");
        columnCheckProductName.getColumnAttributeChecks().add(columnProductNameTypeCheck);

        DatabaseTableCheck databaseTableCheckProduct = factory.createDatabaseTableCheck();
        databaseTableCheckProduct.setName("Database Table PRODUCT Check");
        databaseTableCheckProduct.setTableName("PRODUCT");
        databaseTableCheckProduct.getColumnChecks().add(columnCheckProductClassId);
        databaseTableCheckProduct.getColumnChecks().add(columnCheckProductProductId);
        databaseTableCheckProduct.getColumnChecks().add(columnCheckBrandName);
        databaseTableCheckProduct.getColumnChecks().add(columnCheckProductName);

        // Create database column checks for PRODUCT_CLASS table
        DatabaseColumnAttributeCheck columnProductClassProductClassIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductClassProductClassIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductClassProductClassIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckProductClassProductClassId = factory.createDatabaseColumnCheck();
        columnCheckProductClassProductClassId.setName("Database Column Check PRODUCT_CLASS_ID (PRODUCT_CLASS)");
        columnCheckProductClassProductClassId.setColumnName("PRODUCT_CLASS_ID");
        columnCheckProductClassProductClassId.getColumnAttributeChecks().add(columnProductClassProductClassIdTypeCheck);

        DatabaseColumnAttributeCheck columnProductFamileTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnProductFamileTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnProductFamileTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckProductFamile = factory.createDatabaseColumnCheck();
        columnCheckProductFamile.setName("Database Column Check PRODUCT_FAMILE");
        columnCheckProductFamile.setColumnName("PRODUCT_FAMILE");
        columnCheckProductFamile.getColumnAttributeChecks().add(columnProductFamileTypeCheck);

        DatabaseTableCheck databaseTableCheckProductClass = factory.createDatabaseTableCheck();
        databaseTableCheckProductClass.setName("Database Table PRODUCT_CLASS Check");
        databaseTableCheckProductClass.setTableName("PRODUCT_CLASS");
        databaseTableCheckProductClass.getColumnChecks().add(columnCheckProductClassProductClassId);
        databaseTableCheckProductClass.getColumnChecks().add(columnCheckProductFamile);

        // Create database column checks for AGG_C_SPECIAL_SALES_FACT_1997 table
        DatabaseColumnAttributeCheck columnAggProductIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnAggProductIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAggProductIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckAggProductId = factory.createDatabaseColumnCheck();
        columnCheckAggProductId.setName("Database Column Check PRODUCT_ID (AGG_C_SPECIAL_SALES_FACT_1997)");
        columnCheckAggProductId.setColumnName("PRODUCT_ID");
        columnCheckAggProductId.getColumnAttributeChecks().add(columnAggProductIdTypeCheck);

        DatabaseColumnAttributeCheck columnStoreCostSumTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnStoreCostSumTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnStoreCostSumTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckStoreCostSum = factory.createDatabaseColumnCheck();
        columnCheckStoreCostSum.setName("Database Column Check STORE_COST_SUM");
        columnCheckStoreCostSum.setColumnName("STORE_COST_SUM");
        columnCheckStoreCostSum.getColumnAttributeChecks().add(columnStoreCostSumTypeCheck);

        DatabaseColumnAttributeCheck columnFactCountTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFactCountTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFactCountTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactCount = factory.createDatabaseColumnCheck();
        columnCheckFactCount.setName("Database Column Check FACT_COUNT");
        columnCheckFactCount.setColumnName("FACT_COUNT");
        columnCheckFactCount.getColumnAttributeChecks().add(columnFactCountTypeCheck);

        DatabaseTableCheck databaseTableCheckAggCSpecial = factory.createDatabaseTableCheck();
        databaseTableCheckAggCSpecial.setName("Database Table AGG_C_SPECIAL_SALES_FACT_1997 Check");
        databaseTableCheckAggCSpecial.setTableName("AGG_C_SPECIAL_SALES_FACT_1997");
        databaseTableCheckAggCSpecial.getColumnChecks().add(columnCheckAggProductId);
        databaseTableCheckAggCSpecial.getColumnChecks().add(columnCheckStoreCostSum);
        databaseTableCheckAggCSpecial.getColumnChecks().add(columnCheckFactCount);

        // Create table checks for excluded aggregation tables (without column checks)
        DatabaseTableCheck databaseTableCheckAggC14 = factory.createDatabaseTableCheck();
        databaseTableCheckAggC14.setName("Database Table AGG_C_14_SALES_FACT_1997 Check");
        databaseTableCheckAggC14.setTableName("AGG_C_14_SALES_FACT_1997");

        DatabaseTableCheck databaseTableCheckAggLC100 = factory.createDatabaseTableCheck();
        databaseTableCheckAggLC100.setName("Database Table AGG_LC_100_SALES_FACT_1997 Check");
        databaseTableCheckAggLC100.setTableName("AGG_LC_100_SALES_FACT_1997");

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Aggregation Aggregate Tables");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckSalesFact1997);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckProduct);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckProductClass);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckAggCSpecial);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckAggC14);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckAggLC100);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Aggregation Aggregate Tables' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Aggregation Aggregate Tables");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Aggregation Aggregate Tables");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        suite.setDescription("Check suite for the Daanse Tutorial - Aggregation Aggregate Tables");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
