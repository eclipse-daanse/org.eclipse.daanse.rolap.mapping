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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.steelwheels;

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
 * Provides a check suite for the SteelWheels complex mapping example.
 * Checks that the catalog with SteelWheelsSales cube and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "SteelWheels";

    // Cube name
    private static final String CUBE_STEELWHEELSSALES = "SteelWheelsSales";

    // Measure names
    private static final String MEASURE_QUANTITY = "Quantity";
    private static final String MEASURE_SALES = "Sales";

    // Dimension names
    private static final String DIM_MARKETS = "Markets";
    private static final String DIM_CUSTOMERS = "Customers";
    private static final String DIM_PRODUCT = "Product";
    private static final String DIM_TIME = "Time";
    private static final String DIM_ORDERSTATUS = "Order Status";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckMarkets = createDimensionCheck(DIM_MARKETS,
                createHierarchyCheck("Markets",
                        createLevelCheck("Territory"),
                        createLevelCheck("Country"),
                        createLevelCheck("State Province"),
                        createLevelCheck("City")));
        DimensionCheck dimCheckCustomers = createDimensionCheck(DIM_CUSTOMERS,
                createHierarchyCheck("Customers",
                        createLevelCheck("Customer")));
        DimensionCheck dimCheckProduct = createDimensionCheck(DIM_PRODUCT,
                createHierarchyCheck("Product",
                        createLevelCheck("Line"),
                        createLevelCheck("Vendor"),
                        createLevelCheck("Product")));
        DimensionCheck dimCheckTime = createDimensionCheck(DIM_TIME,
                createHierarchyCheck("Time",
                        createLevelCheck("Years"),
                        createLevelCheck("Quarters"),
                        createLevelCheck("Months")));
        DimensionCheck dimCheckOrderStatus = createDimensionCheck(DIM_ORDERSTATUS,
                createHierarchyCheck("Order Status",
                        createLevelCheck("Type")));

        // Create measure checks
        MeasureCheck measureCheckQuantity = createMeasureCheck(MEASURE_QUANTITY);
        MeasureCheck measureCheckSales = createMeasureCheck(MEASURE_SALES);

        // Create cube check for SteelWheelsSales
        CubeCheck cubeCheckSteelWheelsSales = factory.createCubeCheck();
        cubeCheckSteelWheelsSales.setName("CubeCheck-" + CUBE_STEELWHEELSSALES);
        cubeCheckSteelWheelsSales.setDescription("Check that cube '" + CUBE_STEELWHEELSSALES + "' exists");
        cubeCheckSteelWheelsSales.setCubeName(CUBE_STEELWHEELSSALES);
        cubeCheckSteelWheelsSales.getMeasureChecks().add(measureCheckQuantity);
        cubeCheckSteelWheelsSales.getMeasureChecks().add(measureCheckSales);
        cubeCheckSteelWheelsSales.getDimensionChecks().add(dimCheckMarkets);
        cubeCheckSteelWheelsSales.getDimensionChecks().add(dimCheckCustomers);
        cubeCheckSteelWheelsSales.getDimensionChecks().add(dimCheckProduct);
        cubeCheckSteelWheelsSales.getDimensionChecks().add(dimCheckTime);
        cubeCheckSteelWheelsSales.getDimensionChecks().add(dimCheckOrderStatus);

        // Create database table and column checks
        DatabaseTableCheck tableCheckOrderFact = createTableCheck("orderfact",
                createColumnCheck("CUSTOMERNUMBER", "INTEGER"),
                createColumnCheck("PRODUCTCODE", "VARCHAR"),
                createColumnCheck("TIME_ID", "VARCHAR"),
                createColumnCheck("QUANTITYORDERED", "INTEGER"),
                createColumnCheck("TOTALPRICE", "NUMERIC"),
                createColumnCheck("STATUS", "VARCHAR"),
                createColumnCheck("ORDERDATE", "TIMESTAMP"),
                createColumnCheck("PRICEEACH", "NUMERIC"),
                createColumnCheck("REQUIREDDATE", "TIMESTAMP"),
                createColumnCheck("SHIPPEDDATE", "TIMESTAMP")
        );

        DatabaseTableCheck tableCheckCustomer = createTableCheck("customer_w_ter",
                createColumnCheck("CUSTOMERNUMBER", "INTEGER"),
                createColumnCheck("CUSTOMERNAME", "VARCHAR"),
                createColumnCheck("TERRITORY", "VARCHAR"),
                createColumnCheck("COUNTRY", "VARCHAR"),
                createColumnCheck("STATE", "VARCHAR"),
                createColumnCheck("CITY", "VARCHAR"),
                createColumnCheck("CONTACTFIRSTNAME", "VARCHAR"),
                createColumnCheck("CONTACTLASTNAME", "VARCHAR"),
                createColumnCheck("PHONE", "VARCHAR"),
                createColumnCheck("ADDRESSLINE1", "VARCHAR"),
                createColumnCheck("CREDITLIMIT", "NUMERIC")
        );

        DatabaseTableCheck tableCheckProducts = createTableCheck("products",
                createColumnCheck("PRODUCTCODE", "VARCHAR"),
                createColumnCheck("PRODUCTNAME", "VARCHAR"),
                createColumnCheck("PRODUCTLINE", "VARCHAR"),
                createColumnCheck("PRODUCTVENDOR", "VARCHAR"),
                createColumnCheck("PRODUCTDESCRIPTION", "VARCHAR")
        );

        DatabaseTableCheck tableCheckTime = createTableCheck("time",
                createColumnCheck("TIME_ID", "VARCHAR"),
                createColumnCheck("YEAR_ID", "INTEGER"),
                createColumnCheck("QTR_NAME", "VARCHAR"),
                createColumnCheck("QTR_ID", "INTEGER"),
                createColumnCheck("MONTH_NAME", "VARCHAR"),
                createColumnCheck("MONTH_ID", "INTEGER")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for SteelWheels mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckOrderFact);
        databaseSchemaCheck.getTableChecks().add(tableCheckCustomer);
        databaseSchemaCheck.getTableChecks().add(tableCheckProducts);
        databaseSchemaCheck.getTableChecks().add(tableCheckTime);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckSteelWheelsSales);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for SteelWheels mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("SteelWheels Example Suite");
        suite.setDescription("Check suite for the SteelWheels mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates a MeasureCheck with the specified name.
     *
     * @param measureName the name of the measure
     * @return the configured MeasureCheck
     */
    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    /**
     * Creates a DimensionCheck with the specified name.
     *
     * @param dimensionName the name of the dimension
     * @param hierarchyChecks the hierarchy checks to add to the dimension check
     * @return the configured DimensionCheck
     */
    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        if (hierarchyChecks != null) {
            for (HierarchyCheck hierarchyCheck : hierarchyChecks) {
                dimensionCheck.getHierarchyChecks().add(hierarchyCheck);
            }
        }
        return dimensionCheck;
    }

    /**
     * Creates a HierarchyCheck with the specified name and level checks.
     *
     * @param hierarchyName the name of the hierarchy
     * @param levelChecks the level checks to add to the hierarchy check
     * @return the configured HierarchyCheck
     */
    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        if (levelChecks != null) {
            for (LevelCheck levelCheck : levelChecks) {
                hierarchyCheck.getLevelChecks().add(levelCheck);
            }
        }
        return hierarchyCheck;
    }

    /**
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }

    /**
     * Creates a DatabaseColumnCheck with the specified name and type.
     *
     * @param columnName the name of the column
     * @param columnType the expected type of the column
     * @return the configured DatabaseColumnCheck
     */
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

    /**
     * Creates a DatabaseTableCheck with the specified name and column checks.
     *
     * @param tableName the name of the table
     * @param columnChecks the column checks to add to the table check
     * @return the configured DatabaseTableCheck
     */
    private DatabaseTableCheck createTableCheck(String tableName, DatabaseColumnCheck... columnChecks) {
        DatabaseTableCheck tableCheck = factory.createDatabaseTableCheck();
        tableCheck.setName("Database Table Check " + tableName);
        tableCheck.setTableName(tableName);
        for (DatabaseColumnCheck columnCheck : columnChecks) {
            tableCheck.getColumnChecks().add(columnCheck);
        }
        return tableCheck;
    }
}
