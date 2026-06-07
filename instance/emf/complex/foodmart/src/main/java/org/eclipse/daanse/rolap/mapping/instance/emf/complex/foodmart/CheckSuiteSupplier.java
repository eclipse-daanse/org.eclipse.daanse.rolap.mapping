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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.foodmart;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
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
 * Provides a check suite for the FoodMart complex mapping example.
 * Checks that the catalog with Sales, Warehouse, Store, HR, Sales Ragged, Sales 2, and Warehouse and Sales cubes exist with their associated dimensions and measures.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "FoodMart";

    // Cube names
    private static final String CUBE_SALES = "Sales";
    private static final String CUBE_WAREHOUSE = "Warehouse";
    private static final String CUBE_STORE = "Store";
    private static final String CUBE_HR = "HR";
    private static final String CUBE_SALES_RAGGED = "Sales Ragged";
    private static final String CUBE_SALES_2 = "Sales 2";
    private static final String CUBE_WAREHOUSE_AND_SALES = "Warehouse and Sales";

    // Sales cube measure names
    private static final String MEASURE_UNIT_SALES = "Unit Sales";
    private static final String MEASURE_STORE_SALES = "Store Sales";
    private static final String MEASURE_STORE_COST = "Store Cost";
    private static final String MEASURE_SALES_COUNT = "Sales Count";
    private static final String MEASURE_CUSTOMER_COUNT = "Customer Count";
    private static final String MEASURE_PROMOTION_SALES = "Promotion Sales";

    // Warehouse cube measure names
    private static final String MEASURE_WAREHOUSE_SALES = "Warehouse Sales";
    private static final String MEASURE_WAREHOUSE_COST = "Warehouse Cost";
    private static final String MEASURE_WAREHOUSE_PROFIT = "Warehouse Profit";
    private static final String MEASURE_UNITS_SHIPPED = "Units Shipped";
    private static final String MEASURE_UNITS_ORDERED = "Units Ordered";
    private static final String MEASURE_STORE_INVOICE = "Store Invoice";
    private static final String MEASURE_SUPPLY_TIME = "Supply Time";

    // Store cube measure names
    private static final String MEASURE_STORE_SQFT = "Store Sqft";
    private static final String MEASURE_GROCERY_SQFT = "Grocery Sqft";

    // HR cube measure names
    private static final String MEASURE_ORG_SALARY = "Org Salary";
    private static final String MEASURE_COUNT = "Count";
    private static final String MEASURE_NUMBER_OF_EMPLOYEES = "Number of Employees";

    // Common dimension names
    private static final String DIM_TIME = "Time";
    private static final String DIM_STORE = "Store";
    private static final String HIERARCHY_STORE = "Store";
    private static final String DIM_PRODUCT = "Product";
    private static final String DIM_CUSTOMERS = "Customers";
    private static final String DIM_PROMOTIONS = "Promotions";
    private static final String DIM_PROMOTION_MEDIA = "Promotion Media";
    private static final String DIM_STORE_SIZE_IN_SQFT = "Store Size in SQFT";
    private static final String DIM_STORE_SIZE = "Store Size in SqFt";
    private static final String DIM_STORE_TYPE = "Store Type";
    private static final String DIM_WAREHOUSE = "Warehouse";
    private static final String DIM_EDUCATION_LEVEL = "Education Level";
    private static final String DIM_GENDER = "Gender";
    private static final String DIM_MARITAL_STATUS = "Marital Status";
    private static final String DIM_YEARLY_INCOME = "Yearly Income";
    private static final String DIM_GEOGRAPHY = "Geography";
    private static final String DIM_POSITION = "Position";
    private static final String DIM_DEPARTMENT = "Department";
    private static final String DIM_EMPLOYEES = "Employees";
    private static final String DIM_HAS_COFFEE_BAR = "Has coffee bar";
    private static final String DIM_PAY_TYPE = "Pay Type";

    @Override
    public OlapCheckSuite get() {
        // Create Sales cube check
        CubeCheck cubeCheckSales = createSalesCubeCheck();

        // Create Warehouse cube check
        CubeCheck cubeCheckWarehouse = createWarehouseCubeCheck();

        // Create Store cube check
        CubeCheck cubeCheckStore = createStoreCubeCheck();

        // Create HR cube check
        CubeCheck cubeCheckHR = createHRCubeCheck();

        // Create Sales Ragged cube check
        CubeCheck cubeCheckSalesRagged = createSalesRaggedCubeCheck();

        // Create Sales 2 cube check
        CubeCheck cubeCheckSales2 = createSales2CubeCheck();

        // Create Warehouse and Sales cube check
        CubeCheck cubeCheckWarehouseAndSales = createWarehouseAndSalesCubeCheck();


        // Create catalog check with cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckSales);
        catalogCheck.getCubeChecks().add(cubeCheckWarehouse);
        catalogCheck.getCubeChecks().add(cubeCheckStore);
        catalogCheck.getCubeChecks().add(cubeCheckHR);
        catalogCheck.getCubeChecks().add(cubeCheckSalesRagged);
        catalogCheck.getCubeChecks().add(cubeCheckSales2);
        catalogCheck.getCubeChecks().add(cubeCheckWarehouseAndSales);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for FoodMart mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("FoodMart Example Suite");
        suite.setDescription("Check suite for the FoodMart mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates the Sales cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Sales
     */
    private CubeCheck createSalesCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_SALES);
        cubeCheck.setDescription("Check that cube '" + CUBE_SALES + "' exists");
        cubeCheck.setCubeName(CUBE_SALES);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNIT_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_SALES_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_CUSTOMER_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_PROMOTION_SALES));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME,
            createHierarchyCheck("Time",
                createLevelCheck("Year"),
                createLevelCheck("Quarter"),
                createLevelCheck("Month")),
            createHierarchyCheck("Weekly",
                createLevelCheck("Year"),
                createLevelCheck("Week"),
                createLevelCheck("Day"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE,
            createHierarchyCheck(HIERARCHY_STORE,
                createLevelCheck("Store Country"),
                createLevelCheck("Store State"),
                createLevelCheck("Store City"),
                createLevelCheck("Store Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PRODUCT,
            createHierarchyCheck("Product",
                createLevelCheck("Product Family"),
                createLevelCheck("Product Department"),
                createLevelCheck("Product Category"),
                createLevelCheck("Product Subcategory"),
                createLevelCheck("Brand Name"),
                createLevelCheck("Product Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_CUSTOMERS,
            createHierarchyCheck("Customers",
                createLevelCheck("Country"),
                createLevelCheck("State Province"),
                createLevelCheck("City"),
                createLevelCheck("Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTIONS,
            createHierarchyCheck("Promotions",
                createLevelCheck("Promotion Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTION_MEDIA,
            createHierarchyCheck("Promotion Media",
                createLevelCheck("Media Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_SIZE_IN_SQFT,
            createHierarchyCheck("Store Size in SQFT",
                createLevelCheck("Store Sqft"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_TYPE,
            createHierarchyCheck("Store Type",
                createLevelCheck("Store Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_EDUCATION_LEVEL,
            createHierarchyCheck("Education Level",
                createLevelCheck("Education Level"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_GENDER,
            createHierarchyCheck("Gender",
                createLevelCheck("Gender"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_MARITAL_STATUS,
            createHierarchyCheck("Marital Status",
                createLevelCheck("Marital Status"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_YEARLY_INCOME,
            createHierarchyCheck("Yearly Income",
                createLevelCheck("Yearly Income"))));

        return cubeCheck;
    }

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

    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }

    /**
     * Creates the Warehouse cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Warehouse
     */
    private CubeCheck createWarehouseCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_WAREHOUSE);
        cubeCheck.setDescription("Check that cube '" + CUBE_WAREHOUSE + "' exists");
        cubeCheck.setCubeName(CUBE_WAREHOUSE);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_PROFIT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNITS_SHIPPED));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNITS_ORDERED));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME,
            createHierarchyCheck("Time",
                createLevelCheck("Year"),
                createLevelCheck("Quarter"),
                createLevelCheck("Month")),
            createHierarchyCheck("Weekly",
                createLevelCheck("Year"),
                createLevelCheck("Week"),
                createLevelCheck("Day"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE,
            createHierarchyCheck(HIERARCHY_STORE,
                createLevelCheck("Store Country"),
                createLevelCheck("Store State"),
                createLevelCheck("Store City"),
                createLevelCheck("Store Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_SIZE_IN_SQFT,
            createHierarchyCheck("Store Size in SQFT",
                createLevelCheck("Store Sqft"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_TYPE,
            createHierarchyCheck("Store Type",
                createLevelCheck("Store Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PRODUCT,
            createHierarchyCheck("Product",
                createLevelCheck("Product Family"),
                createLevelCheck("Product Department"),
                createLevelCheck("Product Category"),
                createLevelCheck("Product Subcategory"),
                createLevelCheck("Brand Name"),
                createLevelCheck("Product Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_WAREHOUSE,
            createHierarchyCheck("Warehouse",
                createLevelCheck("Country"),
                createLevelCheck("State Province"),
                createLevelCheck("City"),
                createLevelCheck("Warehouse Name"))));

        return cubeCheck;
    }

    /**
     * Creates the Store cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Store
     */
    private CubeCheck createStoreCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_STORE);
        cubeCheck.setDescription("Check that cube '" + CUBE_STORE + "' exists");
        cubeCheck.setCubeName(CUBE_STORE);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_SQFT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_GROCERY_SQFT));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE,
            createHierarchyCheck(HIERARCHY_STORE,
                createLevelCheck("Store Country"),
                createLevelCheck("Store State"),
                createLevelCheck("Store City"),
                createLevelCheck("Store Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_TYPE,
            createHierarchyCheck("Store Type",
                createLevelCheck("Store Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_HAS_COFFEE_BAR,
            createHierarchyCheck("Has coffee bar",
                createLevelCheck("Has coffee bar"))));

        return cubeCheck;
    }

    /**
     * Creates the HR cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for HR
     */
    private CubeCheck createHRCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_HR);
        cubeCheck.setDescription("Check that cube '" + CUBE_HR + "' exists");
        cubeCheck.setCubeName(CUBE_HR);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_ORG_SALARY));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_NUMBER_OF_EMPLOYEES));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME,
            createHierarchyCheck("Time",
                createLevelCheck("Year"),
                createLevelCheck("Quarter"),
                createLevelCheck("Month"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE,
            createHierarchyCheck(HIERARCHY_STORE,
                createLevelCheck("Store Country"),
                createLevelCheck("Store State"),
                createLevelCheck("Store City"),
                createLevelCheck("Store Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PAY_TYPE,
            createHierarchyCheck("Pay Type",
                createLevelCheck("Pay Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_TYPE,
            createHierarchyCheck("Store Type",
                createLevelCheck("Store Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_POSITION,
            createHierarchyCheck("Position",
                createLevelCheck("Management Role"),
                createLevelCheck("Position Title"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_DEPARTMENT,
            createHierarchyCheck("Department",
                createLevelCheck("Department Description"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_EMPLOYEES,
            createHierarchyCheck("Employees",
                createLevelCheck("Employee Id1"))));

        return cubeCheck;
    }

    /**
     * Creates the Sales Ragged cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Sales Ragged
     */
    private CubeCheck createSalesRaggedCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_SALES_RAGGED);
        cubeCheck.setDescription("Check that cube '" + CUBE_SALES_RAGGED + "' exists");
        cubeCheck.setCubeName(CUBE_SALES_RAGGED);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNIT_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_SALES_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_CUSTOMER_COUNT));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE,
            createHierarchyCheck("Store",
                createLevelCheck("Store Country"),
                createLevelCheck("Store State"),
                createLevelCheck("Store City"),
                createLevelCheck("Store Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_GEOGRAPHY,
            createHierarchyCheck("Geography",
                createLevelCheck("Country"),
                createLevelCheck("State"),
                createLevelCheck("City"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_SIZE,
            createHierarchyCheck("Store Size in SQFT",
                createLevelCheck("Store Sqft"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE_TYPE,
            createHierarchyCheck("Store Type",
                createLevelCheck("Store Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME,
            createHierarchyCheck("Time",
                createLevelCheck("Year"),
                createLevelCheck("Quarter"),
                createLevelCheck("Month")),
            createHierarchyCheck("Weekly",
                createLevelCheck("Year"),
                createLevelCheck("Week"),
                createLevelCheck("Day"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PRODUCT,
            createHierarchyCheck("Product",
                createLevelCheck("Product Family"),
                createLevelCheck("Product Department"),
                createLevelCheck("Product Category"),
                createLevelCheck("Product Subcategory"),
                createLevelCheck("Brand Name"),
                createLevelCheck("Product Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTION_MEDIA,
            createHierarchyCheck("Promotion Media",
                createLevelCheck("Media Type"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTIONS,
            createHierarchyCheck("Promotions",
                createLevelCheck("Promotion Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_CUSTOMERS,
            createHierarchyCheck("Customers",
                createLevelCheck("Country"),
                createLevelCheck("State Province"),
                createLevelCheck("City"),
                createLevelCheck("Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_EDUCATION_LEVEL,
            createHierarchyCheck("Education Level",
                createLevelCheck("Education Level"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_GENDER,
            createHierarchyCheck("Gender",
                createLevelCheck("Gender"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_MARITAL_STATUS,
            createHierarchyCheck("Marital Status",
                createLevelCheck("Marital Status"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_YEARLY_INCOME,
            createHierarchyCheck("Yearly Income",
                createLevelCheck("Yearly Income"))));

        return cubeCheck;
    }

    /**
     * Creates the Sales 2 cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Sales 2
     */
    private CubeCheck createSales2CubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_SALES_2);
        cubeCheck.setDescription("Check that cube '" + CUBE_SALES_2 + "' exists");
        cubeCheck.setCubeName(CUBE_SALES_2);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_SALES_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNIT_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_CUSTOMER_COUNT));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME,
            createHierarchyCheck("Time",
                createLevelCheck("Year"),
                createLevelCheck("Quarter"),
                createLevelCheck("Month")),
            createHierarchyCheck("Weekly",
                createLevelCheck("Year"),
                createLevelCheck("Week"),
                createLevelCheck("Day"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PRODUCT,
            createHierarchyCheck("Product",
                createLevelCheck("Product Family"),
                createLevelCheck("Product Department"),
                createLevelCheck("Product Category"),
                createLevelCheck("Product Subcategory"),
                createLevelCheck("Brand Name"),
                createLevelCheck("Product Name"))));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_GENDER,
            createHierarchyCheck("Gender",
                createLevelCheck("Gender"))));

        return cubeCheck;
    }

    /**
     * Creates the Warehouse and Sales virtual cube check with its dimensions and measures.
     *
     * @return the configured CubeCheck for Warehouse and Sales
     */
    private CubeCheck createWarehouseAndSalesCubeCheck() {
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_WAREHOUSE_AND_SALES);
        cubeCheck.setDescription("Check that cube '" + CUBE_WAREHOUSE_AND_SALES + "' exists");
        cubeCheck.setCubeName(CUBE_WAREHOUSE_AND_SALES);

        // Add measures
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_SALES_COUNT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNIT_SALES));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_STORE_INVOICE));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_SUPPLY_TIME));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNITS_ORDERED));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_UNITS_SHIPPED));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_COST));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_PROFIT));
        cubeCheck.getMeasureChecks().add(createMeasureCheck(MEASURE_WAREHOUSE_SALES));

        // Add dimensions
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_CUSTOMERS));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_EDUCATION_LEVEL));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_GENDER));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_MARITAL_STATUS));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PRODUCT));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTION_MEDIA));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_PROMOTIONS));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_STORE));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_TIME));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_YEARLY_INCOME));
        cubeCheck.getDimensionChecks().add(createDimensionCheck(DIM_WAREHOUSE));

        return cubeCheck;
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


}
