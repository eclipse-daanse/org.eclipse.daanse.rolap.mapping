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
    private static final String DIM_PRODUCT_SUBCATEGORY = "DimProductSubCategory";
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
            t.CalendarYear AS Y,
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
            createHierarchyCheck("DimProductSubCategory",
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
        cellCheck102.setExpectedValue("78.0000");
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
        cellCheck202.setExpectedValue("348.0000");
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
        cellCheck302.setExpectedValue("470.0000");
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
        cellCheck402.setExpectedValue("119.0000");
        cellCheck402.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck4 = factory.createQueryCheck();
        sqlQueryCheck4.setName("Sql Query Check4 for " + CATALOG_NAME);
        sqlQueryCheck4.setQuery(Q4);
        sqlQueryCheck4.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck4.getCellChecks().addAll(List.of(cellCheck400, cellCheck401, cellCheck402));

        CellValueCheck cellCheck500 = factory.createCellValueCheck();
        cellCheck500.setName("[Measures].[FactInternetSales]");
        cellCheck500.setExpectedValue("108561.7");

        QueryCheck sqlQueryCheck5 = factory.createQueryCheck();
        sqlQueryCheck5.setName("Sql Query Check5 for " + CATALOG_NAME);
        sqlQueryCheck5.setQuery(Q5);
        sqlQueryCheck5.setQueryLanguage(QueryLanguage.MDX);
        sqlQueryCheck5.getCellChecks().addAll(List.of(cellCheck500));


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


}
