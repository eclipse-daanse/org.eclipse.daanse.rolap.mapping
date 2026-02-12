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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.csdl.bikeshop;

import java.util.List;

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
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the CSDL BikeShop mapping example.
 * Checks that the catalog with one cube (SalesChannel),
 * its measures, dimensions, database schema, and SQL queries are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "CSDLBI 1.1";

    // Cube name
    private static final String CUBE_NAME = "SalesChannel";

    // Measure names
    private static final String MEASURE_TOTAL_PRODUCT_COST = "Sum of TotalProductCost";
    private static final String MEASURE_SALES_AMOUNT = "Sum of SalesAmount";

    // Dimension names
    private static final String DIM_BIKE = "Bike";
    private static final String DIM_BIKE_SUBCATEGORY = "BikeSubcategory";
    private static final String DIM_CALENDAR_QUARTER = "CalendarQuarter";
    private static final String DIM_COUNTRY = "Country";
    private static final String DIM_CURRENCY = "Currency";
    private static final String DIM_SALES_CHANNEL = "SalesChannel";

    private static final String Q1 = """
        SELECT
            c.CountryName,
            count(*) AS NumberOfSales,
            sum(bs.OrderQuantity) AS TotalQuantity
        FROM BikeSales bs
            JOIN Country c ON bs.CountryCode = c.CountryCode
        GROUP BY c.CountryName
        ORDER BY count(*) DESC
        """;

    private static final String Q2 = """
        SELECT
            sc.SalesChannelName,
            count(*) AS NumberOfSales,
            sum(bs.OrderQuantity) AS TotalQuantity
        FROM BikeSales bs
            JOIN SalesChannel sc ON bs.SalesChannelCode = sc.SalesChannelCode
        GROUP BY sc.SalesChannelName
        ORDER BY count(*) DESC
        """;

    private static final String Q3 = """
        SELECT
            bs.CalendarQuarter,
            count(*) AS NumberOfSales,
            sum(bs.OrderQuantity) AS TotalQuantity
        FROM BikeSales bs
        GROUP BY bs.CalendarQuarter
        ORDER BY bs.CalendarQuarter
        """;

    private static final String Q4 = """
        SELECT
            bsc.Subcategory,
            count(*) AS NumberOfSales,
            sum(bs.OrderQuantity) AS TotalQuantity
        FROM BikeSales bs
            JOIN Bike b ON bs.ProductKey = b.ProductKey
            JOIN BikeSubcategory bsc ON b.ProductSubcategoryKey = bsc.ProductSubcategoryKey
        GROUP BY bsc.Subcategory
        ORDER BY sum(bs.OrderQuantity) DESC
        """;

    private static final String Q5 = """
    SELECT FROM [SalesChannel] WHERE ([Measures].[Sum of TotalProductCost])
        """;

    private static final String Q6 = """
    SELECT FROM [SalesChannel] WHERE ([Measures].[Sum of SalesAmount])
        """;

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckBike = createDimensionCheck(DIM_BIKE,
            createHierarchyCheck("Product_Hierarchy",
                createLevelCheck("RowNumber"),
                createLevelCheck("ProductKey"),
                createLevelCheck("ProductAlternateKey"),
                createLevelCheck("ProductSubcategoryKey"),
                createLevelCheck("ProductName"),
                createLevelCheck("StandardCost"),
                createLevelCheck("FinishedGoodsFlag"),
                createLevelCheck("Color"),
                createLevelCheck("ListPrice"),
                createLevelCheck("Size"),
                createLevelCheck("SizeRange"),
                createLevelCheck("Weight"),
                createLevelCheck("DealerPrice"),
                createLevelCheck("Class"),
                createLevelCheck("Style"),
                createLevelCheck("ModelName"),
                createLevelCheck("Description"),
                createLevelCheck("WeightUnitMeasureCode"),
                createLevelCheck("SizeUnitMeasureCode"),
                createLevelCheck("SafetyStockLevel"),
                createLevelCheck("ReorderPoint"),
                createLevelCheck("DaysToManufacture"),
                createLevelCheck("ProductLine")));
        DimensionCheck dimCheckBikeSubcategory = createDimensionCheck(DIM_BIKE_SUBCATEGORY,
            createHierarchyCheck("BikeSubcategory",
                createLevelCheck("ProductSubcategoryKey"),
                createLevelCheck("Subcategory")));
        DimensionCheck dimCheckCalendarQuarter = createDimensionCheck(DIM_CALENDAR_QUARTER,
            createHierarchyCheck("CalendarQuarter",
                createLevelCheck("RowNumber"),
                createLevelCheck("CalendarQuarter2")));
        DimensionCheck dimCheckCountry = createDimensionCheck(DIM_COUNTRY,
            createHierarchyCheck("Country",
                createLevelCheck("RowNumber"),
                createLevelCheck("CountryCode"),
                createLevelCheck("CountryName")));
        DimensionCheck dimCheckCurrency = createDimensionCheck(DIM_CURRENCY,
            createHierarchyCheck("Currency",
                createLevelCheck("RowNumber"),
                createLevelCheck("CurrencyKey"),
                createLevelCheck("CurrencyAlternateKey"),
                createLevelCheck("CurrencyName")));
        DimensionCheck dimCheckSalesChannel = createDimensionCheck(DIM_SALES_CHANNEL,
            createHierarchyCheck("SalesChannel",
                createLevelCheck("RowNumber"),
                createLevelCheck("SalesChannelCode"),
                createLevelCheck("SalesChannelName")));

        // Create cube: SalesChannel
        MeasureCheck measureCheckTotalProductCost = createMeasureCheck(MEASURE_TOTAL_PRODUCT_COST);
        MeasureCheck measureCheckSalesAmount = createMeasureCheck(MEASURE_SALES_AMOUNT);

        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheckTotalProductCost);
        cubeCheck.getMeasureChecks().add(measureCheckSalesAmount);
        cubeCheck.getDimensionChecks().add(dimCheckBike);
        cubeCheck.getDimensionChecks().add(dimCheckBikeSubcategory);
        cubeCheck.getDimensionChecks().add(dimCheckCalendarQuarter);
        cubeCheck.getDimensionChecks().add(dimCheckCountry);
        cubeCheck.getDimensionChecks().add(dimCheckCurrency);
        cubeCheck.getDimensionChecks().add(dimCheckSalesChannel);

        // Q1: Sales by country
        CellValueCheck cellCheck100 = factory.createCellValueCheck();
        cellCheck100.setName("COUNTRY_NAME");
        cellCheck100.setExpectedValue("United States");
        cellCheck100.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck101 = factory.createCellValueCheck();
        cellCheck101.setName("NUMBER_OF_SALES");
        cellCheck101.setExpectedValue("8");
        cellCheck101.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck102 = factory.createCellValueCheck();
        cellCheck102.setName("TOTAL_QUANTITY");
        cellCheck102.setExpectedValue("13");
        cellCheck102.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck1 = factory.createQueryCheck();
        sqlQueryCheck1.setName("Sql Query Check1 for " + CATALOG_NAME);
        sqlQueryCheck1.setQuery(Q1);
        sqlQueryCheck1.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck1.getCellChecks().addAll(List.of(cellCheck100, cellCheck101, cellCheck102));

        // Q2: Sales by sales channel
        CellValueCheck cellCheck200 = factory.createCellValueCheck();
        cellCheck200.setName("SALES_CHANNEL_NAME");
        cellCheck200.setExpectedValue("Online Store");
        cellCheck200.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck201 = factory.createCellValueCheck();
        cellCheck201.setName("NUMBER_OF_SALES");
        cellCheck201.setExpectedValue("4");
        cellCheck201.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck202 = factory.createCellValueCheck();
        cellCheck202.setName("TOTAL_QUANTITY");
        cellCheck202.setExpectedValue("6");
        cellCheck202.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck2 = factory.createQueryCheck();
        sqlQueryCheck2.setName("Sql Query Check2 for " + CATALOG_NAME);
        sqlQueryCheck2.setQuery(Q2);
        sqlQueryCheck2.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck2.getCellChecks().addAll(List.of(cellCheck200, cellCheck201, cellCheck202));

        // Q3: Sales by calendar quarter
        CellValueCheck cellCheck300 = factory.createCellValueCheck();
        cellCheck300.setName("CALENDAR_QUARTER");
        cellCheck300.setExpectedValue("2024-Q1");
        cellCheck300.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck301 = factory.createCellValueCheck();
        cellCheck301.setName("NUMBER_OF_SALES");
        cellCheck301.setExpectedValue("10");
        cellCheck301.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck302 = factory.createCellValueCheck();
        cellCheck302.setName("TOTAL_QUANTITY");
        cellCheck302.setExpectedValue("31");
        cellCheck302.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck3 = factory.createQueryCheck();
        sqlQueryCheck3.setName("Sql Query Check3 for " + CATALOG_NAME);
        sqlQueryCheck3.setQuery(Q3);
        sqlQueryCheck3.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck3.getCellChecks().addAll(List.of(cellCheck300, cellCheck301, cellCheck302));

        // Q4: Sales by bike subcategory
        CellValueCheck cellCheck400 = factory.createCellValueCheck();
        cellCheck400.setName("SUBCATEGORY");
        cellCheck400.setExpectedValue("Bike Bags & Packs");
        cellCheck400.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck401 = factory.createCellValueCheck();
        cellCheck401.setName("NUMBER_OF_SALES");
        cellCheck401.setExpectedValue("1");
        cellCheck401.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck402 = factory.createCellValueCheck();
        cellCheck402.setName("TOTAL_QUANTITY");
        cellCheck402.setExpectedValue("25");
        cellCheck402.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck4 = factory.createQueryCheck();
        sqlQueryCheck4.setName("Sql Query Check4 for " + CATALOG_NAME);
        sqlQueryCheck4.setQuery(Q4);
        sqlQueryCheck4.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck4.getCellChecks().addAll(List.of(cellCheck400, cellCheck401, cellCheck402));

        CellValueCheck cellCheck500 = factory.createCellValueCheck();
        cellCheck500.setName("[Measures].[Sum of TotalProductCost]");
        cellCheck500.setExpectedValue("108561.7");
        cellCheck500.getCoordinates().addAll(List.of(0, 0));

        QueryCheck sqlQueryCheck5 = factory.createQueryCheck();
        sqlQueryCheck5.setName("Sql Query Check5 for " + CATALOG_NAME);
        sqlQueryCheck5.setQuery(Q5);
        sqlQueryCheck5.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck5.getCellChecks().addAll(List.of(cellCheck500));

        CellValueCheck cellCheck600 = factory.createCellValueCheck();
        cellCheck600.setName("[Measures].[Sum of SalesAmount]");
        cellCheck600.setExpectedValue("108561.7");
        cellCheck600.getCoordinates().addAll(List.of(0, 0));

        QueryCheck sqlQueryCheck6 = factory.createQueryCheck();
        sqlQueryCheck6.setName("Sql Query Check5 for " + CATALOG_NAME);
        sqlQueryCheck6.setQuery(Q6);
        sqlQueryCheck6.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck6.getCellChecks().addAll(List.of(cellCheck600));

        // Create database table and column checks
        DatabaseTableCheck tableCheckBikeSales = createTableCheck("BikeSales",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("SalesOrderNumber", "VARCHAR"),
            createColumnCheck("SalesOrderLineNumber", "INTEGER"),
            createColumnCheck("RevisionNumber", "INTEGER"),
            createColumnCheck("ProductKey", "INTEGER"),
            createColumnCheck("CountryCode", "VARCHAR"),
            createColumnCheck("CurrencyKey", "INTEGER"),
            createColumnCheck("CalendarQuarter", "VARCHAR"),
            createColumnCheck("SalesChannelCode", "VARCHAR"),
            createColumnCheck("OrderQuantity", "INTEGER"),
            createColumnCheck("UnitPrice", "DECIMAL"),
            createColumnCheck("ExtendedAmount", "DECIMAL"),
            createColumnCheck("UnitPriceDiscountPct", "DOUBLE"),
            createColumnCheck("DiscountAmount", "DOUBLE"),
            createColumnCheck("ProductStandardCost", "DECIMAL"),
            createColumnCheck("TotalProductCost", "DECIMAL"),
            createColumnCheck("SalesAmount", "DECIMAL"),
            createColumnCheck("TaxAmt", "DECIMAL"),
            createColumnCheck("Freight", "DECIMAL"),
            createColumnCheck("CarrierTrackingNumber", "VARCHAR"),
            createColumnCheck("CustomerPONumber", "VARCHAR"),
            createColumnCheck("CustomerAccountNumber", "VARCHAR")
        );

        DatabaseTableCheck tableCheckBike = createTableCheck("Bike",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductKey", "INTEGER"),
            createColumnCheck("ProductAlternateKey", "VARCHAR"),
            createColumnCheck("ProductSubcategoryKey", "INTEGER"),
            createColumnCheck("ProductName", "VARCHAR"),
            createColumnCheck("StandardCost", "DECIMAL"),
            createColumnCheck("FinishedGoodsFlag", "BOOLEAN"),
            createColumnCheck("Color", "VARCHAR"),
            createColumnCheck("ListPrice", "DECIMAL"),
            createColumnCheck("Size", "VARCHAR"),
            createColumnCheck("SizeRange", "VARCHAR"),
            createColumnCheck("Weight", "DOUBLE"),
            createColumnCheck("DealerPrice", "DECIMAL"),
            createColumnCheck("Class", "VARCHAR"),
            createColumnCheck("Style", "VARCHAR"),
            createColumnCheck("ModelName", "VARCHAR"),
            createColumnCheck("Description", "VARCHAR"),
            createColumnCheck("WeightUnitMeasureCode", "VARCHAR"),
            createColumnCheck("SizeUnitMeasureCode", "VARCHAR"),
            createColumnCheck("SafetyStockLevel", "INTEGER"),
            createColumnCheck("ReorderPoint", "INTEGER"),
            createColumnCheck("DaysToManufacture", "INTEGER"),
            createColumnCheck("ProductLine", "VARCHAR")
        );

        DatabaseTableCheck tableCheckBikeSubcategory = createTableCheck("BikeSubcategory",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductSubcategoryKey", "INTEGER"),
            createColumnCheck("Subcategory", "VARCHAR")
        );

        DatabaseTableCheck tableCheckCalendarQuarter = createTableCheck("CalendarQuarter",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("CalendarQuarter2", "VARCHAR")
        );

        DatabaseTableCheck tableCheckCountry = createTableCheck("Country",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("CountryCode", "INTEGER"),
            createColumnCheck("CountryName", "VARCHAR")
        );

        DatabaseTableCheck tableCheckCurrency = createTableCheck("Currency",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("CurrencyKey", "INTEGER"),
            createColumnCheck("CurrencyAlternateKey", "VARCHAR"),
            createColumnCheck("CurrencyName", "VARCHAR")
        );

        DatabaseTableCheck tableCheckSalesChannel = createTableCheck("SalesChannel",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("SalesChannelCode", "VARCHAR"),
            createColumnCheck("SalesChannelName", "VARCHAR")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for CSDL BikeShop mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckBikeSales);
        databaseSchemaCheck.getTableChecks().add(tableCheckBike);
        databaseSchemaCheck.getTableChecks().add(tableCheckBikeSubcategory);
        databaseSchemaCheck.getTableChecks().add(tableCheckCalendarQuarter);
        databaseSchemaCheck.getTableChecks().add(tableCheckCountry);
        databaseSchemaCheck.getTableChecks().add(tableCheckCurrency);
        databaseSchemaCheck.getTableChecks().add(tableCheckSalesChannel);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with cube and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(sqlQueryCheck1);
        catalogCheck.getQueryChecks().add(sqlQueryCheck2);
        catalogCheck.getQueryChecks().add(sqlQueryCheck3);
        catalogCheck.getQueryChecks().add(sqlQueryCheck4);
        catalogCheck.getQueryChecks().add(sqlQueryCheck5);
        catalogCheck.getQueryChecks().add(sqlQueryCheck6);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for CSDL BikeShop mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("CSDL BikeShop Example Suite");
        suite.setDescription("Check suite for the CSDL BikeShop mapping example");
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
     * @param dimensionName   the name of the dimension
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
     * @param levelChecks   the level checks to add to the hierarchy check
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
     * @param tableName    the name of the table
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
