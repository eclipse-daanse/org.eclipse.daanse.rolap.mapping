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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.csdl.bikeaccessories;

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
 * Provides a check suite for the CSDL BikeAccessories mapping example.
 * Checks that the catalog with one cube (DescriptionRolePlayingDimensionsDB),
 * its measure, dimensions, database schema, and SQL queries are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "CSDLBI 1.0";

    // Cube name
    private static final String CUBE_NAME = "DescriptionRolePlayingDimensionsDB";

    // Measure name
    private static final String MEASURE_FACT_INTERNET_SALES = "FactInternetSales";

    // Dimension names
    private static final String DIM_CUSTOMER = "DimCustomer";
    private static final String DIM_EMPLOYEE = "DimEmployee";
    private static final String DIM_GEOGRAPHY = "DimGeography";
    private static final String DIM_PRODUCT = "DimProduct";
    private static final String DIM_PRODUCT_CATEGORY = "DimProductCategory";
    private static final String DIM_PRODUCT_SUBCATEGORY = "DimProductSubcategory";
    private static final String DIM_STORE = "DimStore";
    private static final String DIM_TIME = "DimTime";

    private static final String Q1 = """
        SELECT
            pc.EnglishProductCategoryName AS CategoryName,
            count(*) AS NumberOfSales,
            sum(f.OrderQuantity) AS TotalQuantity
        FROM Fact f
            JOIN Product p ON f.ProductKey = p.ProductKey
            JOIN ProductSubcategory ps ON p.ProductSubcategoryKey = ps.ProductSubcategoryKey
            JOIN ProductCategory pc ON ps.ProductCategoryKey = pc.ProductCategoryKey
        GROUP BY pc.EnglishProductCategoryName
        ORDER BY count(*) DESC
        """;

    private static final String Q2 = """
        SELECT
            g.EnglishCountryRegionName AS Country,
            count(*) AS NumberOfSales,
            sum(f.OrderQuantity) AS TotalQuantity
        FROM Fact f
            JOIN Customer c ON f.CustomerKey = c.CustomerKey
            JOIN Geography g ON c.GeographyKey = g.GeographyKey
        GROUP BY g.EnglishCountryRegionName
        ORDER BY count(*) DESC
        """;

    private static final String Q3 = """
        SELECT
            t.CalendarYear AS Year,
            count(*) AS NumberOfSales,
            sum(f.OrderQuantity) AS TotalQuantity
        FROM Fact f
            JOIN Time t ON f.OrderDateKey = t.TimeKey
        GROUP BY t.CalendarYear
        ORDER BY t.CalendarYear
        """;

    private static final String Q4 = """
        SELECT
            s.StoreName,
            count(*) AS NumberOfSales,
            sum(f.OrderQuantity) AS TotalQuantity
        FROM Fact f
            JOIN Store s ON f.StoreKey = s.StoreKey
        GROUP BY s.StoreName
        ORDER BY count(*) DESC
        """;

    private static final String Q5 = """
    SELECT  FROM [DescriptionRolePlayingDimensionsDB] WHERE ([Measures].[FactInternetSales])
            """;

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckCustomer = createDimensionCheck(DIM_CUSTOMER,
            createHierarchyCheck("DimCustomer",
                createLevelCheck("RowNumber"),
                createLevelCheck("CustomerKey"),
                createLevelCheck("CustomerAlternateKey"),
                createLevelCheck("FirstName"),
                createLevelCheck("MiddleName"),
                createLevelCheck("LastName"),
                createLevelCheck("NameStyle"),
                createLevelCheck("BirthDate"),
                createLevelCheck("MaritalStatus"),
                createLevelCheck("Suffix"),
                createLevelCheck("Gender"),
                createLevelCheck("EmailAddress"),
                createLevelCheck("YearlyIncome"),
                createLevelCheck("TotalChildren"),
                createLevelCheck("NumberChildrenAtHome"),
                createLevelCheck("EnglishEducation"),
                createLevelCheck("SpanishEducation"),
                createLevelCheck("FrenchEducation"),
                createLevelCheck("EnglishOccupation"),
                createLevelCheck("SpanishOccupation"),
                createLevelCheck("FrenchOccupation"),
                createLevelCheck("HouseOwnerFlag"),
                createLevelCheck("NumberCarsOwned"),
                createLevelCheck("AddressLine1"),
                createLevelCheck("AddressLine2"),
                createLevelCheck("Phone"),
                createLevelCheck("DateFirstPurchase"),
                createLevelCheck("CommuteDistance")));
        DimensionCheck dimCheckEmployee = createDimensionCheck(DIM_EMPLOYEE,
            createHierarchyCheck("DimEmployee",
                createLevelCheck("RowNumber"),
                createLevelCheck("EmployeeKey"),
                createLevelCheck("ParentEmployeeKey"),
                createLevelCheck("EmployeeNationalIDAlternateKey"),
                createLevelCheck("ParentEmployeeNationalIDAlternateKey"),
                createLevelCheck("SalesTerritoryKey"),
                createLevelCheck("FirstName"),
                createLevelCheck("LastName"),
                createLevelCheck("MiddleName"),
                createLevelCheck("NameStyle"),
                createLevelCheck("Title"),
                createLevelCheck("HireDate"),
                createLevelCheck("BirthDate"),
                createLevelCheck("LoginID"),
                createLevelCheck("EmailAddress"),
                createLevelCheck("Phone"),
                createLevelCheck("MaritalStatus"),
                createLevelCheck("EmergencyContactName"),
                createLevelCheck("EmergencyContactPhone"),
                createLevelCheck("SalariedFlag"),
                createLevelCheck("Gender"),
                createLevelCheck("PayFrequency"),
                createLevelCheck("BaseRate"),
                createLevelCheck("VacationHours"),
                createLevelCheck("SickLeaveHours"),
                createLevelCheck("CurrentFlag"),
                createLevelCheck("SalesPersonFlag"),
                createLevelCheck("DepartmentName"),
                createLevelCheck("StartDate"),
                createLevelCheck("EndDate"),
                createLevelCheck("Status")));
        DimensionCheck dimCheckGeography = createDimensionCheck(DIM_GEOGRAPHY,
            createHierarchyCheck("DimGeography",
                createLevelCheck("RowNumber"),
                createLevelCheck("GeographyKey"),
                createLevelCheck("City"),
                createLevelCheck("StateProvinceCode"),
                createLevelCheck("StateProvinceName"),
                createLevelCheck("CountryRegionCode"),
                createLevelCheck("EnglishCountryRegionName"),
                createLevelCheck("SpanishCountryRegionName"),
                createLevelCheck("FrenchCountryRegionName"),
                createLevelCheck("PostalCode"),
                createLevelCheck("SalesTerritoryKey")));
        DimensionCheck dimCheckProduct = createDimensionCheck(DIM_PRODUCT,
            createHierarchyCheck("DimProduct",
                createLevelCheck("RowNumber"),
                createLevelCheck("ProductKey"),
                createLevelCheck("ProductAlternateKey"),
                createLevelCheck("ProductSubcategoryKey"),
                createLevelCheck("WeightUnitMeasureCode"),
                createLevelCheck("SizeUnitMeasureCode"),
                createLevelCheck("EnglishProductName"),
                createLevelCheck("SpanishProductName"),
                createLevelCheck("FrenchProductName"),
                createLevelCheck("StandardCost"),
                createLevelCheck("FinishedGoodsFlag"),
                createLevelCheck("Color"),
                createLevelCheck("SafetyStockLevel"),
                createLevelCheck("ReorderPoint"),
                createLevelCheck("ListPrice"),
                createLevelCheck("Size"),
                createLevelCheck("SizeRange"),
                createLevelCheck("Weight"),
                createLevelCheck("DaysToManufacture"),
                createLevelCheck("ProductLine"),
                createLevelCheck("DealerPrice"),
                createLevelCheck("Class"),
                createLevelCheck("Style"),
                createLevelCheck("ModelName"),
                createLevelCheck("EnglishDescription"),
                createLevelCheck("FrenchDescription"),
                createLevelCheck("ChineseDescription"),
                createLevelCheck("ArabicDescription"),
                createLevelCheck("HebrewDescription"),
                createLevelCheck("ThaiDescription"),
                createLevelCheck("StartDate"),
                createLevelCheck("EndDate"),
                createLevelCheck("Status"),
                createLevelCheck("Subcategory")));
        DimensionCheck dimCheckProductCategory = createDimensionCheck(DIM_PRODUCT_CATEGORY,
            createHierarchyCheck("DimProductCategory",
                createLevelCheck("RowNumber"),
                createLevelCheck("ProductCategoryKey"),
                createLevelCheck("ProductCategoryAlternateKey"),
                createLevelCheck("EnglishProductCategoryName"),
                createLevelCheck("SpanishProductCategoryName"),
                createLevelCheck("FrenchProductCategoryName")));
        DimensionCheck dimCheckProductSubcategory = createDimensionCheck(DIM_PRODUCT_SUBCATEGORY,
            createHierarchyCheck("DimProductSubcategory",
                createLevelCheck("RowNumber"),
                createLevelCheck("ProductSubcategoryKey"),
                createLevelCheck("ProductSubcategoryAlternateKey"),
                createLevelCheck("EnglishProductSubcategoryName"),
                createLevelCheck("SpanishProductSubcategoryName"),
                createLevelCheck("FrenchProductSubcategoryName"),
                createLevelCheck("ProductCategoryKey")));
        DimensionCheck dimCheckStore = createDimensionCheck(DIM_STORE,
            createHierarchyCheck("DimStore",
                createLevelCheck("RowNumber"),
                createLevelCheck("StoreKey"),
                createLevelCheck("Geography_Key"),
                createLevelCheck("StoreName"),
                createLevelCheck("Number_of_Employees"),
                createLevelCheck("Sales")));
        DimensionCheck dimCheckTime = createDimensionCheck(DIM_TIME,
            createHierarchyCheck("DimTime",
                createLevelCheck("RowNumber"),
                createLevelCheck("TimeKey"),
                createLevelCheck("FullDateAlternateKey"),
                createLevelCheck("DayNumberOfWeek"),
                createLevelCheck("EnglishDayNameOfWeek"),
                createLevelCheck("SpanishDayNameOfWeek"),
                createLevelCheck("FrenchDayNameOfWeek"),
                createLevelCheck("DayNumberOfMonth"),
                createLevelCheck("DayNumberOfYear"),
                createLevelCheck("WeekNumberOfYear"),
                createLevelCheck("EnglishMonthName"),
                createLevelCheck("SpanishMonthName"),
                createLevelCheck("FrenchMonthName"),
                createLevelCheck("MonthNumberOfYear"),
                createLevelCheck("CalendarQuarter"),
                createLevelCheck("CalendarYear"),
                createLevelCheck("CalendarSemester"),
                createLevelCheck("FiscalQuarter"),
                createLevelCheck("FiscalYear"),
                createLevelCheck("FiscalSemester")));

        // Create cube: DescriptionRolePlayingDimensionsDB
        MeasureCheck measureCheck = createMeasureCheck(MEASURE_FACT_INTERNET_SALES);

        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimCheckCustomer);
        cubeCheck.getDimensionChecks().add(dimCheckEmployee);
        cubeCheck.getDimensionChecks().add(dimCheckGeography);
        cubeCheck.getDimensionChecks().add(dimCheckProduct);
        cubeCheck.getDimensionChecks().add(dimCheckProductCategory);
        cubeCheck.getDimensionChecks().add(dimCheckProductSubcategory);
        cubeCheck.getDimensionChecks().add(dimCheckStore);
        cubeCheck.getDimensionChecks().add(dimCheckTime);

        // Q1: Sales by product category
        CellValueCheck cellCheck100 = factory.createCellValueCheck();
        cellCheck100.setName("CATEGORY_NAME");
        cellCheck100.setExpectedValue("Bikes");
        cellCheck100.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck101 = factory.createCellValueCheck();
        cellCheck101.setName("NUMBER_OF_SALES");
        cellCheck101.setExpectedValue("46");
        cellCheck101.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck102 = factory.createCellValueCheck();
        cellCheck102.setName("TOTAL_QUANTITY");
        cellCheck102.setExpectedValue("78");
        cellCheck102.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck1 = factory.createQueryCheck();
        sqlQueryCheck1.setName("Sql Query Check1 for " + CATALOG_NAME);
        sqlQueryCheck1.setQuery(Q1);
        sqlQueryCheck1.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck1.getCellChecks().addAll(List.of(cellCheck100, cellCheck101, cellCheck102));

        // Q2: Sales by country
        CellValueCheck cellCheck200 = factory.createCellValueCheck();
        cellCheck200.setName("COUNTRY");
        cellCheck200.setExpectedValue("United States");
        cellCheck200.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck201 = factory.createCellValueCheck();
        cellCheck201.setName("NUMBER_OF_SALES");
        cellCheck201.setExpectedValue("61");
        cellCheck201.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck202 = factory.createCellValueCheck();
        cellCheck202.setName("TOTAL_QUANTITY");
        cellCheck202.setExpectedValue("348");
        cellCheck202.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck2 = factory.createQueryCheck();
        sqlQueryCheck2.setName("Sql Query Check2 for " + CATALOG_NAME);
        sqlQueryCheck2.setQuery(Q2);
        sqlQueryCheck2.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck2.getCellChecks().addAll(List.of(cellCheck200, cellCheck201, cellCheck202));

        // Q3: Sales by calendar year
        CellValueCheck cellCheck300 = factory.createCellValueCheck();
        cellCheck300.setName("YEAR");
        cellCheck300.setExpectedValue("2026");
        cellCheck300.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck301 = factory.createCellValueCheck();
        cellCheck301.setName("NUMBER_OF_SALES");
        cellCheck301.setExpectedValue("74");
        cellCheck301.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck302 = factory.createCellValueCheck();
        cellCheck302.setName("TOTAL_QUANTITY");
        cellCheck302.setExpectedValue("470");
        cellCheck302.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck3 = factory.createQueryCheck();
        sqlQueryCheck3.setName("Sql Query Check3 for " + CATALOG_NAME);
        sqlQueryCheck3.setQuery(Q3);
        sqlQueryCheck3.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck3.getCellChecks().addAll(List.of(cellCheck300, cellCheck301, cellCheck302));

        // Q4: Sales by store
        CellValueCheck cellCheck400 = factory.createCellValueCheck();
        cellCheck400.setName("STORE_NAME");
        cellCheck400.setExpectedValue("Seattle Downtown Bikes");
        cellCheck400.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck401 = factory.createCellValueCheck();
        cellCheck401.setName("NUMBER_OF_SALES");
        cellCheck401.setExpectedValue("21");
        cellCheck401.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck402 = factory.createCellValueCheck();
        cellCheck402.setName("TOTAL_QUANTITY");
        cellCheck402.setExpectedValue("119");
        cellCheck402.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck4 = factory.createQueryCheck();
        sqlQueryCheck4.setName("Sql Query Check4 for " + CATALOG_NAME);
        sqlQueryCheck4.setQuery(Q4);
        sqlQueryCheck4.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck4.getCellChecks().addAll(List.of(cellCheck400, cellCheck401, cellCheck402));

        CellValueCheck cellCheck500 = factory.createCellValueCheck();
        cellCheck500.setName("[Measures].[FactInternetSales]");
        cellCheck500.setExpectedValue("108561.7");
        cellCheck500.getCoordinates().addAll(List.of(0, 0));

        QueryCheck sqlQueryCheck5 = factory.createQueryCheck();
        sqlQueryCheck5.setName("Sql Query Check5 for " + CATALOG_NAME);
        sqlQueryCheck5.setQuery(Q5);
        sqlQueryCheck5.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck5.getCellChecks().addAll(List.of(cellCheck500));

        // Create database table and column checks
        DatabaseTableCheck tableCheckFact = createTableCheck("Fact",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductKey", "INTEGER"),
            createColumnCheck("OrderDateKey", "INTEGER"),
            createColumnCheck("DueDateKey", "INTEGER"),
            createColumnCheck("ShipDateKey", "INTEGER"),
            createColumnCheck("CustomerKey", "INTEGER"),
            createColumnCheck("PromotionKey", "INTEGER"),
            createColumnCheck("CurrencyKey", "INTEGER"),
            createColumnCheck("SalesTerritoryKey", "INTEGER"),
            createColumnCheck("SalesOrderNumber", "INTEGER"),
            createColumnCheck("SalesOrderLineNumber", "VARCHAR"),
            createColumnCheck("RevisionNumber", "INTEGER"),
            createColumnCheck("OrderQuantity", "INTEGER"),
            createColumnCheck("UnitPrice", "INTEGER"),
            createColumnCheck("ExtendedAmount", "DECIMAL"),
            createColumnCheck("UnitPriceDiscountPct", "DECIMAL"),
            createColumnCheck("DiscountAmount", "DOUBLE"),
            createColumnCheck("ProductStandardCost", "DOUBLE"),
            createColumnCheck("TotalProductCost", "DECIMAL"),
            createColumnCheck("SalesAmount", "DECIMAL"),
            createColumnCheck("TaxAmt", "DECIMAL"),
            createColumnCheck("Freight", "DECIMAL"),
            createColumnCheck("CarrierTrackingNumber", "VARCHAR"),
            createColumnCheck("CustomerPONumber", "VARCHAR"),
            createColumnCheck("EmployeeKey", "INTEGER"),
            createColumnCheck("BillingCustomerKey", "INTEGER"),
            createColumnCheck("StoreKey", "INTEGER"),
            createColumnCheck("TotalSales", "INTEGER")
        );

        DatabaseTableCheck tableCheckCustomer = createTableCheck("Customer",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("CustomerKey", "INTEGER"),
            createColumnCheck("GeographyKey", "INTEGER"),
            createColumnCheck("CustomerAlternateKey", "VARCHAR"),
            createColumnCheck("FirstName", "VARCHAR"),
            createColumnCheck("MiddleName", "VARCHAR"),
            createColumnCheck("LastName", "VARCHAR"),
            createColumnCheck("NameStyle", "BOOLEAN"),
            createColumnCheck("BirthDate", "DATE"),
            createColumnCheck("MaritalStatus", "VARCHAR"),
            createColumnCheck("Suffix", "VARCHAR"),
            createColumnCheck("Gender", "VARCHAR"),
            createColumnCheck("EmailAddress", "VARCHAR"),
            createColumnCheck("YearlyIncome", "DECIMAL"),
            createColumnCheck("TotalChildren", "INTEGER"),
            createColumnCheck("NumberChildrenAtHome", "INTEGER"),
            createColumnCheck("EnglishEducation", "VARCHAR"),
            createColumnCheck("SpanishEducation", "VARCHAR"),
            createColumnCheck("FrenchEducation", "VARCHAR"),
            createColumnCheck("EnglishOccupation", "VARCHAR"),
            createColumnCheck("SpanishOccupation", "VARCHAR"),
            createColumnCheck("FrenchOccupation", "VARCHAR"),
            createColumnCheck("HouseOwnerFlag", "VARCHAR"),
            createColumnCheck("NumberCarsOwned", "VARCHAR"),
            createColumnCheck("AddressLine1", "VARCHAR"),
            createColumnCheck("AddressLine2", "VARCHAR"),
            createColumnCheck("Phone", "VARCHAR"),
            createColumnCheck("DateFirstPurchase", "DATE"),
            createColumnCheck("CommuteDistance", "VARCHAR")
        );

        DatabaseTableCheck tableCheckEmployee = createTableCheck("Employee",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("EmployeeKey", "INTEGER"),
            createColumnCheck("ParentEmployeeKey", "INTEGER"),
            createColumnCheck("EmployeeNationalIDAlternateKey", "VARCHAR"),
            createColumnCheck("ParentEmployeeNationalIDAlternateKey", "VARCHAR"),
            createColumnCheck("SalesTerritoryKey", "INTEGER"),
            createColumnCheck("FirstName", "VARCHAR"),
            createColumnCheck("LastName", "VARCHAR"),
            createColumnCheck("MiddleName", "VARCHAR"),
            createColumnCheck("NameStyle", "BOOLEAN"),
            createColumnCheck("Title", "VARCHAR"),
            createColumnCheck("HireDate", "DATE"),
            createColumnCheck("BirthDate", "DATE"),
            createColumnCheck("LoginID", "VARCHAR"),
            createColumnCheck("EmailAddress", "VARCHAR"),
            createColumnCheck("Phone", "VARCHAR"),
            createColumnCheck("MaritalStatus", "VARCHAR"),
            createColumnCheck("EmergencyContactName", "VARCHAR"),
            createColumnCheck("EmergencyContactPhone", "VARCHAR"),
            createColumnCheck("SalariedFlag", "BOOLEAN"),
            createColumnCheck("Gender", "VARCHAR"),
            createColumnCheck("PayFrequency", "INTEGER"),
            createColumnCheck("BaseRate", "DECIMAL"),
            createColumnCheck("VacationHours", "INTEGER"),
            createColumnCheck("SickLeaveHours", "INTEGER"),
            createColumnCheck("CurrentFlag", "BOOLEAN"),
            createColumnCheck("SalesPersonFlag", "BOOLEAN"),
            createColumnCheck("DepartmentName", "VARCHAR"),
            createColumnCheck("StartDate", "DATE"),
            createColumnCheck("EndDate", "DATE"),
            createColumnCheck("Status", "VARCHAR")
        );

        DatabaseTableCheck tableCheckGeography = createTableCheck("Geography",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("GeographyKey", "INTEGER"),
            createColumnCheck("City", "VARCHAR"),
            createColumnCheck("StateProvinceCode", "VARCHAR"),
            createColumnCheck("StateProvinceName", "VARCHAR"),
            createColumnCheck("CountryRegionCode", "VARCHAR"),
            createColumnCheck("EnglishCountryRegionName", "VARCHAR"),
            createColumnCheck("SpanishCountryRegionName", "VARCHAR"),
            createColumnCheck("FrenchCountryRegionName", "VARCHAR"),
            createColumnCheck("PostalCode", "VARCHAR"),
            createColumnCheck("SalesTerritoryKey", "INTEGER")
        );

        DatabaseTableCheck tableCheckProduct = createTableCheck("Product",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductKey", "INTEGER"),
            createColumnCheck("ProductAlternateKey", "VARCHAR"),
            createColumnCheck("ProductSubcategoryKey", "INTEGER"),
            createColumnCheck("WeightUnitMeasureCode", "VARCHAR"),
            createColumnCheck("SizeUnitMeasureCode", "VARCHAR"),
            createColumnCheck("EnglishProductName", "VARCHAR"),
            createColumnCheck("SpanishProductName", "VARCHAR"),
            createColumnCheck("FrenchProductName", "VARCHAR"),
            createColumnCheck("StandardCost", "DECIMAL"),
            createColumnCheck("FinishedGoodsFlag", "BOOLEAN"),
            createColumnCheck("Color", "VARCHAR"),
            createColumnCheck("SafetyStockLevel", "INTEGER"),
            createColumnCheck("ReorderPoint", "INTEGER"),
            createColumnCheck("ListPrice", "DECIMAL"),
            createColumnCheck("Size", "VARCHAR"),
            createColumnCheck("SizeRange", "VARCHAR"),
            createColumnCheck("Weight", "DOUBLE"),
            createColumnCheck("DaysToManufacture", "INTEGER"),
            createColumnCheck("ProductLine", "VARCHAR"),
            createColumnCheck("DealerPrice", "DECIMAL"),
            createColumnCheck("Class", "VARCHAR"),
            createColumnCheck("Style", "VARCHAR"),
            createColumnCheck("ModelName", "VARCHAR"),
            createColumnCheck("EnglishDescription", "VARCHAR"),
            createColumnCheck("FrenchDescription", "VARCHAR"),
            createColumnCheck("ChineseDescription", "VARCHAR"),
            createColumnCheck("ArabicDescription", "VARCHAR"),
            createColumnCheck("HebrewDescription", "VARCHAR"),
            createColumnCheck("ThaiDescription", "VARCHAR"),
            createColumnCheck("StartDate", "DATE"),
            createColumnCheck("EndDate", "DATE"),
            createColumnCheck("Status", "VARCHAR"),
            createColumnCheck("Subcategory", "VARCHAR")
        );

        DatabaseTableCheck tableCheckProductCategory = createTableCheck("ProductCategory",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductCategoryKey", "INTEGER"),
            createColumnCheck("ProductCategoryAlternateKey", "INTEGER"),
            createColumnCheck("EnglishProductCategoryName", "VARCHAR"),
            createColumnCheck("SpanishProductCategoryName", "VARCHAR"),
            createColumnCheck("FrenchProductCategoryName", "VARCHAR")
        );

        DatabaseTableCheck tableCheckProductSubcategory = createTableCheck("ProductSubcategory",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("ProductSubcategoryKey", "INTEGER"),
            createColumnCheck("ProductSubcategoryAlternateKey", "INTEGER"),
            createColumnCheck("EnglishProductSubcategoryName", "VARCHAR"),
            createColumnCheck("SpanishProductSubcategoryName", "VARCHAR"),
            createColumnCheck("FrenchProductSubcategoryName", "VARCHAR"),
            createColumnCheck("ProductCategoryKey", "INTEGER")
        );

        DatabaseTableCheck tableCheckStore = createTableCheck("Store",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("StoreKey", "INTEGER"),
            createColumnCheck("Geography_Key", "INTEGER"),
            createColumnCheck("StoreName", "VARCHAR"),
            createColumnCheck("Number_of_Employees", "INTEGER"),
            createColumnCheck("Sales", "DECIMAL")
        );

        DatabaseTableCheck tableCheckTime = createTableCheck("Time",
            createColumnCheck("RowNumber", "INTEGER"),
            createColumnCheck("TimeKey", "INTEGER"),
            createColumnCheck("FullDateAlternateKey", "DATE"),
            createColumnCheck("DayNumberOfWeek", "INTEGER"),
            createColumnCheck("EnglishDayNameOfWeek", "VARCHAR"),
            createColumnCheck("SpanishDayNameOfWeek", "VARCHAR"),
            createColumnCheck("FrenchDayNameOfWeek", "VARCHAR"),
            createColumnCheck("DayNumberOfMonth", "INTEGER"),
            createColumnCheck("DayNumberOfYear", "INTEGER"),
            createColumnCheck("WeekNumberOfYear", "INTEGER"),
            createColumnCheck("EnglishMonthName", "VARCHAR"),
            createColumnCheck("SpanishMonthName", "VARCHAR"),
            createColumnCheck("FrenchMonthName", "VARCHAR"),
            createColumnCheck("MonthNumberOfYear", "INTEGER"),
            createColumnCheck("CalendarQuarter", "INTEGER"),
            createColumnCheck("CalendarYear", "VARCHAR"),
            createColumnCheck("CalendarSemester", "INTEGER"),
            createColumnCheck("FiscalQuarter", "INTEGER"),
            createColumnCheck("FiscalYear", "VARCHAR"),
            createColumnCheck("FiscalSemester", "INTEGER")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for CSDL BikeAccessories mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckFact);
        databaseSchemaCheck.getTableChecks().add(tableCheckCustomer);
        databaseSchemaCheck.getTableChecks().add(tableCheckEmployee);
        databaseSchemaCheck.getTableChecks().add(tableCheckGeography);
        databaseSchemaCheck.getTableChecks().add(tableCheckProduct);
        databaseSchemaCheck.getTableChecks().add(tableCheckProductCategory);
        databaseSchemaCheck.getTableChecks().add(tableCheckProductSubcategory);
        databaseSchemaCheck.getTableChecks().add(tableCheckStore);
        databaseSchemaCheck.getTableChecks().add(tableCheckTime);

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
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for CSDL BikeAccessories mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("CSDL BikeAccessories Example Suite");
        suite.setDescription("Check suite for the CSDL BikeAccessories mapping example");
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
