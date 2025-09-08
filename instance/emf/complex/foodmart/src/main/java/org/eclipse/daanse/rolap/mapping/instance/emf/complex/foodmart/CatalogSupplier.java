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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "3")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    // Static columns - Sales Fact Table
    public static final Column COLUMN_TIME_ID_SALESFACT;
    public static final Column COLUMN_STORE_ID_SALESFACT;
    public static final Column COLUMN_CUSTOMER_ID_SALESFACT;
    public static final Column COLUMN_PRODUCT_ID_SALESFACT;
    public static final Column COLUMN_UNIT_SALES_SALESFACT;
    public static final Column COLUMN_STORE_SALES_SALESFACT;
    public static final Column COLUMN_STORE_COST_SALESFACT;

    // Static columns - Time Table
    public static final Column COLUMN_TIME_ID_TIME;
    public static final Column COLUMN_THE_DATE_TIME;
    public static final Column COLUMN_THE_YEAR_TIME;
    public static final Column COLUMN_QUARTER_TIME;
    public static final Column COLUMN_THE_MONTH_TIME;

    // Static columns - Store Table
    public static final Column COLUMN_STORE_ID_STORE;
    public static final Column COLUMN_STORE_NAME_STORE;
    public static final Column COLUMN_STORE_COUNTRY_STORE;
    public static final Column COLUMN_STORE_STATE_STORE;
    public static final Column COLUMN_STORE_CITY_STORE;

    // Static columns - Customer Table
    public static final Column COLUMN_CUSTOMER_ID_CUSTOMER;
    public static final Column COLUMN_FULLNAME_CUSTOMER;
    public static final Column COLUMN_GENDER_CUSTOMER;
    public static final Column COLUMN_COUNTRY_CUSTOMER;
    public static final Column COLUMN_STATE_PROVINCE_CUSTOMER;
    public static final Column COLUMN_CITY_CUSTOMER;

    // Static columns - Product Table
    public static final Column COLUMN_PRODUCT_ID_PRODUCT;
    public static final Column COLUMN_PRODUCT_NAME_PRODUCT;
    public static final Column COLUMN_PRODUCT_FAMILY_PRODUCT;
    public static final Column COLUMN_PRODUCT_DEPARTMENT_PRODUCT;
    public static final Column COLUMN_PRODUCT_CATEGORY_PRODUCT;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_PRODUCT;
    public static final Column COLUMN_BRAND_NAME_PRODUCT;

    // Warehouse table columns
    public static final Column COLUMN_WAREHOUSE_ID_WAREHOUSE;
    public static final Column COLUMN_WAREHOUSE_NAME_WAREHOUSE;
    public static final Column COLUMN_WAREHOUSE_CITY_WAREHOUSE;
    public static final Column COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE;
    public static final Column COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE;

    // Inventory fact table columns
    public static final Column COLUMN_WAREHOUSE_ID_INVENTORY_FACT;
    public static final Column COLUMN_PRODUCT_ID_INVENTORY_FACT;
    public static final Column COLUMN_TIME_ID_INVENTORY_FACT;
    public static final Column COLUMN_STORE_INVOICE_INVENTORY_FACT;
    public static final Column COLUMN_SUPPLY_TIME_INVENTORY_FACT;
    public static final Column COLUMN_WAREHOUSE_COST_INVENTORY_FACT;
    public static final Column COLUMN_WAREHOUSE_SALES_INVENTORY_FACT;
    public static final Column COLUMN_UNITS_SHIPPED_INVENTORY_FACT;
    public static final Column COLUMN_UNITS_ORDERED_INVENTORY_FACT;

    // Promotion table columns
    public static final Column COLUMN_PROMOTION_ID_PROMOTION;
    public static final Column COLUMN_PROMOTION_NAME_PROMOTION;
    public static final Column COLUMN_MEDIA_TYPE_PROMOTION;

    // Employee table columns
    public static final Column COLUMN_EMPLOYEE_ID_EMPLOYEE;
    public static final Column COLUMN_FIRST_NAME_EMPLOYEE;
    public static final Column COLUMN_LAST_NAME_EMPLOYEE;
    public static final Column COLUMN_FULL_NAME_EMPLOYEE;
    public static final Column COLUMN_MANAGEMENT_ROLE_EMPLOYEE;
    public static final Column COLUMN_POSITION_ID_EMPLOYEE;
    public static final Column COLUMN_POSITION_TITLE_EMPLOYEE;
    public static final Column COLUMN_STORE_ID_EMPLOYEE;
    public static final Column COLUMN_SUPERVISOR_ID_EMPLOYEE;
    public static final Column COLUMN_MARITAL_STATUS_EMPLOYEE;
    public static final Column COLUMN_GENDER_EMPLOYEE;

    // Department table columns
    public static final Column COLUMN_DEPARTMENT_ID_DEPARTMENT;
    public static final Column COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT;

    // Position table columns
    public static final Column COLUMN_POSITION_ID_POSITION;
    public static final Column COLUMN_POSITION_TITLE_POSITION;
    public static final Column COLUMN_PAY_TYPE_POSITION;
    public static final Column COLUMN_MIN_SCALE_POSITION;
    public static final Column COLUMN_MAX_SCALE_POSITION;

    // Salary table columns
    public static final Column COLUMN_EMPLOYEE_ID_SALARY;
    public static final Column COLUMN_PAY_DATE_SALARY;
    public static final Column COLUMN_SALARY_PAID_SALARY;

    // Employee closure table columns
    public static final Column COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE;
    public static final Column COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE;
    public static final Column COLUMN_DISTANCE_EMPLOYEE_CLOSURE;

    // Product class table columns
    public static final Column COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS;
    public static final Column COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS;
    public static final Column COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS;
    public static final Column COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS;

    // Enhanced customer columns
    public static final Column COLUMN_MARITAL_STATUS_CUSTOMER;
    public static final Column COLUMN_EDUCATION_CUSTOMER;
    public static final Column COLUMN_YEARLY_INCOME_CUSTOMER;
    public static final Column COLUMN_MEMBER_CARD_CUSTOMER;
    public static final Column COLUMN_OCCUPATION_CUSTOMER;
    public static final Column COLUMN_HOUSEOWNER_CUSTOMER;
    public static final Column COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER;

    // Enhanced store columns
    public static final Column COLUMN_STORE_TYPE_STORE;
    public static final Column COLUMN_REGION_ID_STORE;
    public static final Column COLUMN_STORE_STREET_ADDRESS_STORE;
    public static final Column COLUMN_STORE_MANAGER_STORE;
    public static final Column COLUMN_STORE_SQFT_STORE;
    public static final Column COLUMN_GROCERY_SQFT_STORE;
    public static final Column COLUMN_FROZEN_SQFT_STORE;
    public static final Column COLUMN_MEAT_SQFT_STORE;
    public static final Column COLUMN_COFFEE_BAR_STORE;
    public static final Column COLUMN_STORE_POSTAL_CODE_STORE;
    public static final Column COLUMN_STORE_NUMBER_STORE;

    // Static tables
    public static final PhysicalTable TABLE_SALES_FACT;
    public static final PhysicalTable TABLE_TIME;
    public static final PhysicalTable TABLE_STORE;
    public static final PhysicalTable TABLE_CUSTOMER;
    public static final PhysicalTable TABLE_PRODUCT;
    public static final PhysicalTable TABLE_WAREHOUSE;
    public static final PhysicalTable TABLE_INVENTORY_FACT;
    public static final PhysicalTable TABLE_PROMOTION;
    public static final PhysicalTable TABLE_EMPLOYEE;
    public static final PhysicalTable TABLE_DEPARTMENT;
    public static final PhysicalTable TABLE_POSITION;
    public static final PhysicalTable TABLE_SALARY;
    public static final PhysicalTable TABLE_EMPLOYEE_CLOSURE;
    public static final PhysicalTable TABLE_PRODUCT_CLASS;

    // Static table queries
    public static final TableQuery QUERY_TIME;
    public static final TableQuery QUERY_STORE;
    public static final TableQuery QUERY_CUSTOMER;
    public static final TableQuery QUERY_PRODUCT;
    public static final TableQuery QUERY_SALES_FACT;
    public static final TableQuery QUERY_WAREHOUSE;
    public static final TableQuery QUERY_INVENTORY_FACT;
    public static final TableQuery QUERY_PROMOTION;
    public static final TableQuery QUERY_EMPLOYEE;
    public static final TableQuery QUERY_DEPARTMENT;
    public static final TableQuery QUERY_POSITION;
    public static final TableQuery QUERY_SALARY;
    public static final TableQuery QUERY_EMPLOYEE_CLOSURE;
    public static final TableQuery QUERY_PRODUCT_CLASS;

    // Static levels
    public static final Level LEVEL_YEAR;
    public static final Level LEVEL_QUARTER;
    public static final Level LEVEL_MONTH;
    public static final Level LEVEL_STORE_COUNTRY;
    public static final Level LEVEL_STORE_STATE;
    public static final Level LEVEL_STORE_CITY;
    public static final Level LEVEL_STORE_NAME;
    public static final Level LEVEL_CUSTOMER_COUNTRY;
    public static final Level LEVEL_CUSTOMER_STATE;
    public static final Level LEVEL_CUSTOMER_CITY;
    public static final Level LEVEL_CUSTOMER_NAME;
    public static final Level LEVEL_CUSTOMER_GENDER;
    public static final Level LEVEL_PRODUCT_FAMILY;
    public static final Level LEVEL_PRODUCT_DEPARTMENT;
    public static final Level LEVEL_PRODUCT_CATEGORY;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY;
    public static final Level LEVEL_PRODUCT_BRAND;
    public static final Level LEVEL_PRODUCT_NAME;

    // Warehouse dimension levels
    public static final Level LEVEL_WAREHOUSE_COUNTRY;
    public static final Level LEVEL_WAREHOUSE_STATE;
    public static final Level LEVEL_WAREHOUSE_CITY;
    public static final Level LEVEL_WAREHOUSE_NAME;

    // Promotion dimension levels
    public static final Level LEVEL_PROMOTION_MEDIA;

    // Employee dimension levels
    public static final Level LEVEL_EMPLOYEE_MANAGEMENT_ROLE;
    public static final Level LEVEL_EMPLOYEE_POSITION;
    public static final Level LEVEL_EMPLOYEE_DEPARTMENT;
    public static final Level LEVEL_EMPLOYEE_FULL_NAME;

    // Department dimension levels
    public static final Level LEVEL_DEPARTMENT_DESCRIPTION;

    // Position dimension levels
    public static final Level LEVEL_POSITION_TITLE;

    // Enhanced Customer levels
    public static final Level LEVEL_CUSTOMER_EDUCATION;
    public static final Level LEVEL_CUSTOMER_MARITAL_STATUS;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_TIME;
    public static final ExplicitHierarchy HIERARCHY_STORE;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GEO;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GENDER;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;

    // Missing hierarchies
    public static final ExplicitHierarchy HIERARCHY_WAREHOUSE;
    public static final ExplicitHierarchy HIERARCHY_PROMOTION_MEDIA;
    public static final ExplicitHierarchy HIERARCHY_EMPLOYEE;
    public static final ExplicitHierarchy HIERARCHY_DEPARTMENT;
    public static final ExplicitHierarchy HIERARCHY_POSITION;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_EDUCATION;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_MARITAL;

    // Static dimensions
    public static final StandardDimension DIMENSION_TIME;
    public static final StandardDimension DIMENSION_STORE;
    public static final StandardDimension DIMENSION_CUSTOMERS;
    public static final StandardDimension DIMENSION_PRODUCT;

    // Missing dimensions
    public static final StandardDimension DIMENSION_WAREHOUSE;
    public static final StandardDimension DIMENSION_PROMOTION_MEDIA;
    public static final StandardDimension DIMENSION_EMPLOYEE;
    public static final StandardDimension DIMENSION_DEPARTMENT;
    public static final StandardDimension DIMENSION_POSITION;

    // Static measures
    public static final SumMeasure MEASURE_UNIT_SALES;
    public static final SumMeasure MEASURE_STORE_SALES;
    public static final SumMeasure MEASURE_STORE_COST;
    public static final CountMeasure MEASURE_SALES_COUNT;

    // Warehouse measures
    public static final SumMeasure MEASURE_WAREHOUSE_SALES;
    public static final SumMeasure MEASURE_WAREHOUSE_COST;
    public static final SumMeasure MEASURE_UNITS_SHIPPED;
    public static final SumMeasure MEASURE_UNITS_ORDERED;

    // Store measures
    public static final SumMeasure MEASURE_STORE_SQFT;
    public static final SumMeasure MEASURE_GROCERY_SQFT;

    // HR measures
    public static final SumMeasure MEASURE_ORG_SALARY;
    public static final CountMeasure MEASURE_COUNT;
    public static final CountMeasure MEASURE_NUMBER_OF_EMPLOYEES;

    // Static measure groups
    public static final MeasureGroup MEASUREGROUP_SALES;
    public static final MeasureGroup MEASUREGROUP_WAREHOUSE;
    public static final MeasureGroup MEASUREGROUP_STORE;
    public static final MeasureGroup MEASUREGROUP_HR;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_TIME;
    public static final DimensionConnector CONNECTOR_STORE;
    public static final DimensionConnector CONNECTOR_CUSTOMER;
    public static final DimensionConnector CONNECTOR_PRODUCT;

    // Additional connectors for warehouse cube
    public static final DimensionConnector CONNECTOR_WAREHOUSE_TIME;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_STORE;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_PRODUCT;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_WAREHOUSE;

    // Additional connectors for HR cube
    public static final DimensionConnector CONNECTOR_HR_TIME;
    public static final DimensionConnector CONNECTOR_HR_STORE;
    public static final DimensionConnector CONNECTOR_HR_EMPLOYEE;
    public static final DimensionConnector CONNECTOR_HR_POSITION;
    public static final DimensionConnector CONNECTOR_HR_DEPARTMENT;

    // Additional connectors for Store cube
    public static final DimensionConnector CONNECTOR_STORE_STORE;
    public static final DimensionConnector CONNECTOR_STORE_HAS_COFFEE_BAR;

    // Static calculated members
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_LAST_PERIOD;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_GROWTH;
    public static final CalculatedMember CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE;
    public static final CalculatedMember CALCULATED_MEMBER_EMPLOYEE_SALARY;
    public static final CalculatedMember CALCULATED_MEMBER_AVG_SALARY;

    // Static access grants
    public static final AccessCatalogGrant GRANT_ADMIN_CATALOG;
    public static final AccessCatalogGrant GRANT_NO_HR_CATALOG;
    public static final AccessCatalogGrant GRANT_CALIFORNIA_MANAGER_CATALOG;
    public static final AccessCubeGrant GRANT_NO_HR_CUBE;
    public static final AccessCubeGrant GRANT_CALIFORNIA_MANAGER_SALES_CUBE;

    // Static access roles
    public static final AccessRole ROLE_CALIFORNIA_MANAGER;
    public static final AccessRole ROLE_NO_HR_CUBE;
    public static final AccessRole ROLE_ADMINISTRATOR;

    // Static cubes
    public static final PhysicalCube CUBE_SALES;
    public static final PhysicalCube CUBE_WAREHOUSE;
    public static final PhysicalCube CUBE_STORE;
    public static final PhysicalCube CUBE_HR;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_FOODMART;
    public static final Catalog CATALOG_FOODMART;

    private static final String foodMartBody = """
            The FoodMart database is a classic example of a data warehouse schema used for demonstrating OLAP and business intelligence concepts.
            It contains sales data for a fictional food retail chain with multiple stores, products, customers, and time periods.
            """;

    private static final String salesCubeBody = """
            The Sales cube is the primary fact table containing detailed sales transactions.
            It includes measures for unit sales, store sales, and store cost, along with dimensions for time, customers, products, and stores.
            """;

    private static final String timeBody = """
            The Time dimension provides various levels of temporal analysis including year, quarter, month, and day.
            This allows for trend analysis and time-based comparisons of sales performance.
            """;

    private static final String storeBody = """
            The Store dimension contains information about retail locations including geographic hierarchy
            (country, state, city) and store-specific attributes like store type and size.
            """;

    private static final String customersBody = """
            The Customer dimension includes demographic information about customers such as
            gender, marital status, education level, and geographic location for customer analysis.
            """;

    private static final String productBody = """
            The Product dimension organizes products into a hierarchy including product family,
            department, category, subcategory, brand, and individual product details.
            """;

    static {
        // Initialize columns
        COLUMN_TIME_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_SALESFACT.setName("time_id");
        COLUMN_TIME_ID_SALESFACT.setId("_column_salesFact_timeId");
        COLUMN_TIME_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_SALESFACT.setName("store_id");
        COLUMN_STORE_ID_SALESFACT.setId("_column_salesFact_storeId");
        COLUMN_STORE_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_SALESFACT.setName("customer_id");
        COLUMN_CUSTOMER_ID_SALESFACT.setId("_column_salesFact_customerId");
        COLUMN_CUSTOMER_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_SALESFACT.setName("product_id");
        COLUMN_PRODUCT_ID_SALESFACT.setId("_column_salesFact_productId");
        COLUMN_PRODUCT_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_UNIT_SALES_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_SALESFACT.setName("unit_sales");
        COLUMN_UNIT_SALES_SALESFACT.setId("_column_salesFact_unitSales");
        COLUMN_UNIT_SALES_SALESFACT.setType(ColumnType.NUMERIC);

        COLUMN_STORE_SALES_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_SALESFACT.setName("store_sales");
        COLUMN_STORE_SALES_SALESFACT.setId("_column_salesFact_storeSales");
        COLUMN_STORE_SALES_SALESFACT.setType(ColumnType.NUMERIC);

        COLUMN_STORE_COST_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_SALESFACT.setName("store_cost");
        COLUMN_STORE_COST_SALESFACT.setId("_column_salesFact_storeCost");
        COLUMN_STORE_COST_SALESFACT.setType(ColumnType.NUMERIC);

        // Time table columns
        COLUMN_TIME_ID_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_TIME.setName("time_id");
        COLUMN_TIME_ID_TIME.setId("_column_time_timeId");
        COLUMN_TIME_ID_TIME.setType(ColumnType.INTEGER);

        COLUMN_THE_DATE_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_DATE_TIME.setName("the_date");
        COLUMN_THE_DATE_TIME.setId("_column_time_theDate");
        COLUMN_THE_DATE_TIME.setType(ColumnType.DATE);

        COLUMN_THE_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_YEAR_TIME.setName("the_year");
        COLUMN_THE_YEAR_TIME.setId("_column_time_theYear");
        COLUMN_THE_YEAR_TIME.setType(ColumnType.INTEGER);

        COLUMN_QUARTER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUARTER_TIME.setName("quarter");
        COLUMN_QUARTER_TIME.setId("_column_time_quarter");
        COLUMN_QUARTER_TIME.setType(ColumnType.VARCHAR);

        COLUMN_THE_MONTH_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_MONTH_TIME.setName("the_month");
        COLUMN_THE_MONTH_TIME.setId("_column_time_theMonth");
        COLUMN_THE_MONTH_TIME.setType(ColumnType.VARCHAR);

        // Store table columns
        COLUMN_STORE_ID_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_STORE.setName("store_id");
        COLUMN_STORE_ID_STORE.setId("_column_store_storeId");
        COLUMN_STORE_ID_STORE.setType(ColumnType.INTEGER);

        COLUMN_STORE_NAME_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_NAME_STORE.setName("store_name");
        COLUMN_STORE_NAME_STORE.setId("_column_store_storeName");
        COLUMN_STORE_NAME_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_COUNTRY_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COUNTRY_STORE.setName("store_country");
        COLUMN_STORE_COUNTRY_STORE.setId("_column_store_storeCountry");
        COLUMN_STORE_COUNTRY_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_STATE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_STATE_STORE.setName("store_state");
        COLUMN_STORE_STATE_STORE.setId("_column_store_storeState");
        COLUMN_STORE_STATE_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_CITY_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_CITY_STORE.setName("store_city");
        COLUMN_STORE_CITY_STORE.setId("_column_store_storeCity");
        COLUMN_STORE_CITY_STORE.setType(ColumnType.VARCHAR);

        // Customer table columns
        COLUMN_CUSTOMER_ID_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_CUSTOMER.setName("customer_id");
        COLUMN_CUSTOMER_ID_CUSTOMER.setId("_column_customer_customerId");
        COLUMN_CUSTOMER_ID_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_FULLNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FULLNAME_CUSTOMER.setName("fullname");
        COLUMN_FULLNAME_CUSTOMER.setId("_column_customer_fullName");
        COLUMN_FULLNAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_GENDER_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GENDER_CUSTOMER.setName("gender");
        COLUMN_GENDER_CUSTOMER.setId("_column_customer_gender");
        COLUMN_GENDER_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_COUNTRY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_CUSTOMER.setName("country");
        COLUMN_COUNTRY_CUSTOMER.setId("_column_customer_country");
        COLUMN_COUNTRY_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_STATE_PROVINCE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATE_PROVINCE_CUSTOMER.setName("state_province");
        COLUMN_STATE_PROVINCE_CUSTOMER.setId("_column_customer_stateProvince");
        COLUMN_STATE_PROVINCE_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_CITY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CITY_CUSTOMER.setName("city");
        COLUMN_CITY_CUSTOMER.setId("_column_customer_city");
        COLUMN_CITY_CUSTOMER.setType(ColumnType.VARCHAR);

        // Product table columns
        COLUMN_PRODUCT_ID_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_PRODUCT.setName("product_id");
        COLUMN_PRODUCT_ID_PRODUCT.setId("_column_product_productId");
        COLUMN_PRODUCT_ID_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_NAME_PRODUCT.setName("product_name");
        COLUMN_PRODUCT_NAME_PRODUCT.setId("_column_product_productName");
        COLUMN_PRODUCT_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_FAMILY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_FAMILY_PRODUCT.setName("product_family");
        COLUMN_PRODUCT_FAMILY_PRODUCT.setId("_column_product_productFamily");
        COLUMN_PRODUCT_FAMILY_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_DEPARTMENT_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT.setId("_column_product_productDepartment");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_CATEGORY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CATEGORY_PRODUCT.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_PRODUCT.setId("_column_product_productCategory");
        COLUMN_PRODUCT_CATEGORY_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT.setName("product_subcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT.setId("_column_product_productSubcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_BRAND_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_BRAND_NAME_PRODUCT.setName("brand_name");
        COLUMN_BRAND_NAME_PRODUCT.setId("_column_product_brandName");
        COLUMN_BRAND_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        // Initialize warehouse columns
        COLUMN_WAREHOUSE_ID_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_ID_WAREHOUSE.setName("warehouse_id");
        COLUMN_WAREHOUSE_ID_WAREHOUSE.setId("_column_warehouse_warehouseId");
        COLUMN_WAREHOUSE_ID_WAREHOUSE.setType(ColumnType.INTEGER);

        COLUMN_WAREHOUSE_NAME_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_NAME_WAREHOUSE.setName("warehouse_name");
        COLUMN_WAREHOUSE_NAME_WAREHOUSE.setId("_column_warehouse_warehouseName");
        COLUMN_WAREHOUSE_NAME_WAREHOUSE.setType(ColumnType.VARCHAR);

        COLUMN_WAREHOUSE_CITY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_CITY_WAREHOUSE.setName("warehouse_city");
        COLUMN_WAREHOUSE_CITY_WAREHOUSE.setId("_column_warehouse_warehouseCity");
        COLUMN_WAREHOUSE_CITY_WAREHOUSE.setType(ColumnType.VARCHAR);

        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE.setName("warehouse_state_province");
        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE.setId("_column_warehouse_warehouseStateProvince");
        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE.setType(ColumnType.VARCHAR);

        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE.setName("warehouse_country");
        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE.setId("_column_warehouse_warehouseCountry");
        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE.setType(ColumnType.VARCHAR);

        // Initialize inventory fact columns
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setName("warehouse_id");
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setId("_column_inventoryFact_warehouseId");
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ID_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_INVENTORY_FACT.setName("product_id");
        COLUMN_PRODUCT_ID_INVENTORY_FACT.setId("_column_inventoryFact_productId");
        COLUMN_PRODUCT_ID_INVENTORY_FACT.setType(ColumnType.INTEGER);

        COLUMN_TIME_ID_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_INVENTORY_FACT.setName("time_id");
        COLUMN_TIME_ID_INVENTORY_FACT.setId("_column_inventoryFact_timeId");
        COLUMN_TIME_ID_INVENTORY_FACT.setType(ColumnType.INTEGER);

        COLUMN_STORE_INVOICE_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_INVOICE_INVENTORY_FACT.setName("store_invoice");
        COLUMN_STORE_INVOICE_INVENTORY_FACT.setId("_column_inventoryFact_storeInvoice");
        COLUMN_STORE_INVOICE_INVENTORY_FACT.setType(ColumnType.NUMERIC);

        COLUMN_SUPPLY_TIME_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUPPLY_TIME_INVENTORY_FACT.setName("supply_time");
        COLUMN_SUPPLY_TIME_INVENTORY_FACT.setId("_column_inventoryFact_supplyTime");
        COLUMN_SUPPLY_TIME_INVENTORY_FACT.setType(ColumnType.INTEGER);

        COLUMN_WAREHOUSE_COST_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_COST_INVENTORY_FACT.setName("warehouse_cost");
        COLUMN_WAREHOUSE_COST_INVENTORY_FACT.setId("_column_inventoryFact_warehouseCost");
        COLUMN_WAREHOUSE_COST_INVENTORY_FACT.setType(ColumnType.NUMERIC);

        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT.setName("warehouse_sales");
        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT.setId("_column_inventoryFact_warehouseSales");
        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT.setType(ColumnType.NUMERIC);

        COLUMN_UNITS_SHIPPED_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNITS_SHIPPED_INVENTORY_FACT.setName("units_shipped");
        COLUMN_UNITS_SHIPPED_INVENTORY_FACT.setId("_column_inventoryFact_unitsShipped");
        COLUMN_UNITS_SHIPPED_INVENTORY_FACT.setType(ColumnType.NUMERIC);

        COLUMN_UNITS_ORDERED_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNITS_ORDERED_INVENTORY_FACT.setName("units_ordered");
        COLUMN_UNITS_ORDERED_INVENTORY_FACT.setId("_column_inventoryFact_unitsOrdered");
        COLUMN_UNITS_ORDERED_INVENTORY_FACT.setType(ColumnType.NUMERIC);

        // Initialize promotion columns
        COLUMN_PROMOTION_ID_PROMOTION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_PROMOTION.setName("promotion_id");
        COLUMN_PROMOTION_ID_PROMOTION.setId("_column_promotion_promotionId");
        COLUMN_PROMOTION_ID_PROMOTION.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_NAME_PROMOTION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_NAME_PROMOTION.setName("promotion_name");
        COLUMN_PROMOTION_NAME_PROMOTION.setId("_column_promotion_promotionName");
        COLUMN_PROMOTION_NAME_PROMOTION.setType(ColumnType.VARCHAR);

        COLUMN_MEDIA_TYPE_PROMOTION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MEDIA_TYPE_PROMOTION.setName("media_type");
        COLUMN_MEDIA_TYPE_PROMOTION.setId("_column_promotion_mediaType");
        COLUMN_MEDIA_TYPE_PROMOTION.setType(ColumnType.VARCHAR);

        // Initialize employee columns
        COLUMN_EMPLOYEE_ID_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_ID_EMPLOYEE.setName("employee_id");
        COLUMN_EMPLOYEE_ID_EMPLOYEE.setId("_column_employee_employeeId");
        COLUMN_EMPLOYEE_ID_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_FIRST_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FIRST_NAME_EMPLOYEE.setName("first_name");
        COLUMN_FIRST_NAME_EMPLOYEE.setId("_column_employee_firstName");
        COLUMN_FIRST_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_LAST_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LAST_NAME_EMPLOYEE.setName("last_name");
        COLUMN_LAST_NAME_EMPLOYEE.setId("_column_employee_lastName");
        COLUMN_LAST_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_FULL_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FULL_NAME_EMPLOYEE.setName("full_name");
        COLUMN_FULL_NAME_EMPLOYEE.setId("_column_employee_fullName");
        COLUMN_FULL_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_MANAGEMENT_ROLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MANAGEMENT_ROLE_EMPLOYEE.setName("management_role");
        COLUMN_MANAGEMENT_ROLE_EMPLOYEE.setId("_column_employee_managementRole");
        COLUMN_MANAGEMENT_ROLE_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_POSITION_ID_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSITION_ID_EMPLOYEE.setName("position_id");
        COLUMN_POSITION_ID_EMPLOYEE.setId("_column_employee_positionId");
        COLUMN_POSITION_ID_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_POSITION_TITLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSITION_TITLE_EMPLOYEE.setName("position_title");
        COLUMN_POSITION_TITLE_EMPLOYEE.setId("_column_employee_positionTitle");
        COLUMN_POSITION_TITLE_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_ID_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_EMPLOYEE.setName("store_id");
        COLUMN_STORE_ID_EMPLOYEE.setId("_column_employee_storeId");
        COLUMN_STORE_ID_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_SUPERVISOR_ID_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUPERVISOR_ID_EMPLOYEE.setName("supervisor_id");
        COLUMN_SUPERVISOR_ID_EMPLOYEE.setId("_column_employee_supervisorId");
        COLUMN_SUPERVISOR_ID_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_MARITAL_STATUS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MARITAL_STATUS_EMPLOYEE.setName("marital_status");
        COLUMN_MARITAL_STATUS_EMPLOYEE.setId("_column_employee_maritalStatus");
        COLUMN_MARITAL_STATUS_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_GENDER_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GENDER_EMPLOYEE.setName("gender");
        COLUMN_GENDER_EMPLOYEE.setId("_column_employee_gender");
        COLUMN_GENDER_EMPLOYEE.setType(ColumnType.VARCHAR);

        // Initialize department columns
        COLUMN_DEPARTMENT_ID_DEPARTMENT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEPARTMENT_ID_DEPARTMENT.setName("department_id");
        COLUMN_DEPARTMENT_ID_DEPARTMENT.setId("_column_department_departmentId");
        COLUMN_DEPARTMENT_ID_DEPARTMENT.setType(ColumnType.INTEGER);

        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT.setName("department_description");
        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT.setId("_column_department_departmentDescription");
        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT.setType(ColumnType.VARCHAR);

        // Initialize position columns
        COLUMN_POSITION_ID_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSITION_ID_POSITION.setName("position_id");
        COLUMN_POSITION_ID_POSITION.setId("_column_position_positionId");
        COLUMN_POSITION_ID_POSITION.setType(ColumnType.INTEGER);

        COLUMN_POSITION_TITLE_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSITION_TITLE_POSITION.setName("position_title");
        COLUMN_POSITION_TITLE_POSITION.setId("_column_position_positionTitle");
        COLUMN_POSITION_TITLE_POSITION.setType(ColumnType.VARCHAR);

        COLUMN_PAY_TYPE_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PAY_TYPE_POSITION.setName("pay_type");
        COLUMN_PAY_TYPE_POSITION.setId("_column_position_payType");
        COLUMN_PAY_TYPE_POSITION.setType(ColumnType.VARCHAR);

        COLUMN_MIN_SCALE_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MIN_SCALE_POSITION.setName("min_scale");
        COLUMN_MIN_SCALE_POSITION.setId("_column_position_minScale");
        COLUMN_MIN_SCALE_POSITION.setType(ColumnType.NUMERIC);

        COLUMN_MAX_SCALE_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MAX_SCALE_POSITION.setName("max_scale");
        COLUMN_MAX_SCALE_POSITION.setId("_column_position_maxScale");
        COLUMN_MAX_SCALE_POSITION.setType(ColumnType.NUMERIC);

        // Initialize salary columns
        COLUMN_EMPLOYEE_ID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_ID_SALARY.setName("employee_id");
        COLUMN_EMPLOYEE_ID_SALARY.setId("_column_salary_employeeId");
        COLUMN_EMPLOYEE_ID_SALARY.setType(ColumnType.INTEGER);

        COLUMN_PAY_DATE_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PAY_DATE_SALARY.setName("pay_date");
        COLUMN_PAY_DATE_SALARY.setId("_column_salary_payDate");
        COLUMN_PAY_DATE_SALARY.setType(ColumnType.DATE);

        COLUMN_SALARY_PAID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALARY_PAID_SALARY.setName("salary_paid");
        COLUMN_SALARY_PAID_SALARY.setId("_column_salary_salaryPaid");
        COLUMN_SALARY_PAID_SALARY.setType(ColumnType.NUMERIC);

        // Initialize employee closure columns
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE.setName("supervisor_id");
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE.setId("_column_employeeClosure_supervisorId");
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE.setType(ColumnType.INTEGER);

        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE.setName("employee_id");
        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE.setId("_column_employeeClosure_employeeId");
        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE.setType(ColumnType.INTEGER);

        COLUMN_DISTANCE_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DISTANCE_EMPLOYEE_CLOSURE.setName("distance");
        COLUMN_DISTANCE_EMPLOYEE_CLOSURE.setId("_column_employeeClosure_distance");
        COLUMN_DISTANCE_EMPLOYEE_CLOSURE.setType(ColumnType.INTEGER);

        // Initialize product class columns
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS.setName("product_class_id");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS.setId("_column_productClass_productClassId");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS.setName("product_subcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS.setId("_column_productClass_productSubcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS.setId("_column_productClass_productCategory");
        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS.setId("_column_productClass_productDepartment");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS.setName("product_family");
        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS.setId("_column_productClass_productFamily");
        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS.setType(ColumnType.VARCHAR);

        // Initialize enhanced customer columns
        COLUMN_MARITAL_STATUS_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MARITAL_STATUS_CUSTOMER.setName("marital_status");
        COLUMN_MARITAL_STATUS_CUSTOMER.setId("_column_customer_maritalStatus");
        COLUMN_MARITAL_STATUS_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_EDUCATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EDUCATION_CUSTOMER.setName("education");
        COLUMN_EDUCATION_CUSTOMER.setId("_column_customer_education");
        COLUMN_EDUCATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_YEARLY_INCOME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_YEARLY_INCOME_CUSTOMER.setName("yearly_income");
        COLUMN_YEARLY_INCOME_CUSTOMER.setId("_column_customer_yearlyIncome");
        COLUMN_YEARLY_INCOME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_MEMBER_CARD_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MEMBER_CARD_CUSTOMER.setName("member_card");
        COLUMN_MEMBER_CARD_CUSTOMER.setId("_column_customer_memberCard");
        COLUMN_MEMBER_CARD_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_OCCUPATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_OCCUPATION_CUSTOMER.setName("occupation");
        COLUMN_OCCUPATION_CUSTOMER.setId("_column_customer_occupation");
        COLUMN_OCCUPATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_HOUSEOWNER_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_HOUSEOWNER_CUSTOMER.setName("houseowner");
        COLUMN_HOUSEOWNER_CUSTOMER.setId("_column_customer_houseowner");
        COLUMN_HOUSEOWNER_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER.setName("num_children_at_home");
        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER.setId("_column_customer_numChildrenAtHome");
        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER.setType(ColumnType.INTEGER);

        // Initialize enhanced store columns
        COLUMN_STORE_TYPE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_TYPE_STORE.setName("store_type");
        COLUMN_STORE_TYPE_STORE.setId("_column_store_storeType");
        COLUMN_STORE_TYPE_STORE.setType(ColumnType.VARCHAR);

        COLUMN_REGION_ID_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REGION_ID_STORE.setName("region_id");
        COLUMN_REGION_ID_STORE.setId("_column_store_regionId");
        COLUMN_REGION_ID_STORE.setType(ColumnType.INTEGER);

        COLUMN_STORE_STREET_ADDRESS_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_STREET_ADDRESS_STORE.setName("store_street_address");
        COLUMN_STORE_STREET_ADDRESS_STORE.setId("_column_store_storeStreetAddress");
        COLUMN_STORE_STREET_ADDRESS_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_MANAGER_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_MANAGER_STORE.setName("store_manager");
        COLUMN_STORE_MANAGER_STORE.setId("_column_store_storeManager");
        COLUMN_STORE_MANAGER_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_SQFT_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SQFT_STORE.setName("store_sqft");
        COLUMN_STORE_SQFT_STORE.setId("_column_store_storeSqft");
        COLUMN_STORE_SQFT_STORE.setType(ColumnType.INTEGER);

        COLUMN_GROCERY_SQFT_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GROCERY_SQFT_STORE.setName("grocery_sqft");
        COLUMN_GROCERY_SQFT_STORE.setId("_column_store_grocerySqft");
        COLUMN_GROCERY_SQFT_STORE.setType(ColumnType.INTEGER);

        COLUMN_FROZEN_SQFT_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FROZEN_SQFT_STORE.setName("frozen_sqft");
        COLUMN_FROZEN_SQFT_STORE.setId("_column_store_frozenSqft");
        COLUMN_FROZEN_SQFT_STORE.setType(ColumnType.INTEGER);

        COLUMN_MEAT_SQFT_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MEAT_SQFT_STORE.setName("meat_sqft");
        COLUMN_MEAT_SQFT_STORE.setId("_column_store_meatSqft");
        COLUMN_MEAT_SQFT_STORE.setType(ColumnType.INTEGER);

        COLUMN_COFFEE_BAR_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COFFEE_BAR_STORE.setName("coffee_bar");
        COLUMN_COFFEE_BAR_STORE.setId("_column_store_coffeeBar");
        COLUMN_COFFEE_BAR_STORE.setType(ColumnType.BOOLEAN);

        COLUMN_STORE_POSTAL_CODE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_POSTAL_CODE_STORE.setName("store_postal_code");
        COLUMN_STORE_POSTAL_CODE_STORE.setId("_column_store_storePostalCode");
        COLUMN_STORE_POSTAL_CODE_STORE.setType(ColumnType.VARCHAR);

        COLUMN_STORE_NUMBER_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_NUMBER_STORE.setName("store_number");
        COLUMN_STORE_NUMBER_STORE.setId("_column_store_storeNumber");
        COLUMN_STORE_NUMBER_STORE.setType(ColumnType.INTEGER);

        // Initialize tables
        TABLE_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_SALES_FACT.setName("sales_fact_1997");
        TABLE_SALES_FACT.setId("_table_salesFact1997");
        TABLE_SALES_FACT.getColumns()
                .addAll(List.of(COLUMN_TIME_ID_SALESFACT, COLUMN_STORE_ID_SALESFACT, COLUMN_CUSTOMER_ID_SALESFACT,
                        COLUMN_PRODUCT_ID_SALESFACT, COLUMN_UNIT_SALES_SALESFACT, COLUMN_STORE_SALES_SALESFACT,
                        COLUMN_STORE_COST_SALESFACT));

        TABLE_TIME = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_TIME.setName("time_by_day");
        TABLE_TIME.setId("_table_timeByDay");
        TABLE_TIME.getColumns().addAll(List.of(COLUMN_TIME_ID_TIME, COLUMN_THE_DATE_TIME, COLUMN_THE_YEAR_TIME,
                COLUMN_QUARTER_TIME, COLUMN_THE_MONTH_TIME));

        TABLE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_STORE.setName("store");
        TABLE_STORE.setId("_table_store");
        TABLE_STORE.getColumns().addAll(List.of(COLUMN_STORE_ID_STORE, COLUMN_STORE_NAME_STORE,
                COLUMN_STORE_COUNTRY_STORE, COLUMN_STORE_STATE_STORE, COLUMN_STORE_CITY_STORE, COLUMN_STORE_TYPE_STORE,
                COLUMN_REGION_ID_STORE, COLUMN_STORE_STREET_ADDRESS_STORE, COLUMN_STORE_MANAGER_STORE,
                COLUMN_STORE_SQFT_STORE, COLUMN_GROCERY_SQFT_STORE, COLUMN_FROZEN_SQFT_STORE, COLUMN_MEAT_SQFT_STORE,
                COLUMN_COFFEE_BAR_STORE, COLUMN_STORE_POSTAL_CODE_STORE, COLUMN_STORE_NUMBER_STORE));

        TABLE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CUSTOMER.setName("customer");
        TABLE_CUSTOMER.setId("_table_customer");
        TABLE_CUSTOMER.getColumns()
                .addAll(List.of(COLUMN_CUSTOMER_ID_CUSTOMER, COLUMN_FULLNAME_CUSTOMER, COLUMN_GENDER_CUSTOMER,
                        COLUMN_COUNTRY_CUSTOMER, COLUMN_STATE_PROVINCE_CUSTOMER, COLUMN_CITY_CUSTOMER,
                        COLUMN_MARITAL_STATUS_CUSTOMER, COLUMN_EDUCATION_CUSTOMER, COLUMN_YEARLY_INCOME_CUSTOMER,
                        COLUMN_MEMBER_CARD_CUSTOMER, COLUMN_OCCUPATION_CUSTOMER, COLUMN_HOUSEOWNER_CUSTOMER,
                        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER));

        TABLE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCT.setName("product");
        TABLE_PRODUCT.setId("_table_product");
        TABLE_PRODUCT.getColumns()
                .addAll(List.of(COLUMN_PRODUCT_ID_PRODUCT, COLUMN_PRODUCT_NAME_PRODUCT, COLUMN_BRAND_NAME_PRODUCT,
                        COLUMN_PRODUCT_FAMILY_PRODUCT, COLUMN_PRODUCT_DEPARTMENT_PRODUCT,
                        COLUMN_PRODUCT_CATEGORY_PRODUCT, COLUMN_PRODUCT_SUBCATEGORY_PRODUCT));

        // Initialize new business tables
        TABLE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_WAREHOUSE.setName("warehouse");
        TABLE_WAREHOUSE.setId("_table_warehouse");
        TABLE_WAREHOUSE.getColumns()
                .addAll(List.of(COLUMN_WAREHOUSE_ID_WAREHOUSE, COLUMN_WAREHOUSE_NAME_WAREHOUSE,
                        COLUMN_WAREHOUSE_CITY_WAREHOUSE, COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE,
                        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE));

        TABLE_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_INVENTORY_FACT.setName("inventory_fact_1997");
        TABLE_INVENTORY_FACT.setId("_table_inventoryFact1997");
        TABLE_INVENTORY_FACT.getColumns()
                .addAll(List.of(COLUMN_WAREHOUSE_ID_INVENTORY_FACT, COLUMN_PRODUCT_ID_INVENTORY_FACT,
                        COLUMN_TIME_ID_INVENTORY_FACT, COLUMN_STORE_INVOICE_INVENTORY_FACT,
                        COLUMN_SUPPLY_TIME_INVENTORY_FACT, COLUMN_WAREHOUSE_COST_INVENTORY_FACT,
                        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT, COLUMN_UNITS_SHIPPED_INVENTORY_FACT,
                        COLUMN_UNITS_ORDERED_INVENTORY_FACT));

        TABLE_PROMOTION = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PROMOTION.setName("promotion");
        TABLE_PROMOTION.setId("_table_promotion");
        TABLE_PROMOTION.getColumns().addAll(
                List.of(COLUMN_PROMOTION_ID_PROMOTION, COLUMN_PROMOTION_NAME_PROMOTION, COLUMN_MEDIA_TYPE_PROMOTION));

        TABLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_EMPLOYEE.setName("employee");
        TABLE_EMPLOYEE.setId("_table_employee");
        TABLE_EMPLOYEE.getColumns()
                .addAll(List.of(COLUMN_EMPLOYEE_ID_EMPLOYEE, COLUMN_FIRST_NAME_EMPLOYEE, COLUMN_LAST_NAME_EMPLOYEE,
                        COLUMN_FULL_NAME_EMPLOYEE, COLUMN_MANAGEMENT_ROLE_EMPLOYEE, COLUMN_POSITION_ID_EMPLOYEE,
                        COLUMN_POSITION_TITLE_EMPLOYEE, COLUMN_STORE_ID_EMPLOYEE, COLUMN_SUPERVISOR_ID_EMPLOYEE,
                        COLUMN_MARITAL_STATUS_EMPLOYEE, COLUMN_GENDER_EMPLOYEE));

        TABLE_DEPARTMENT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_DEPARTMENT.setName("department");
        TABLE_DEPARTMENT.setId("_table_department");
        TABLE_DEPARTMENT.getColumns()
                .addAll(List.of(COLUMN_DEPARTMENT_ID_DEPARTMENT, COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT));

        TABLE_POSITION = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_POSITION.setName("position");
        TABLE_POSITION.setId("_table_position");
        TABLE_POSITION.getColumns().addAll(List.of(COLUMN_POSITION_ID_POSITION, COLUMN_POSITION_TITLE_POSITION,
                COLUMN_PAY_TYPE_POSITION, COLUMN_MIN_SCALE_POSITION, COLUMN_MAX_SCALE_POSITION));

        TABLE_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_SALARY.setName("salary");
        TABLE_SALARY.setId("_table_salary");
        TABLE_SALARY.getColumns()
                .addAll(List.of(COLUMN_EMPLOYEE_ID_SALARY, COLUMN_PAY_DATE_SALARY, COLUMN_SALARY_PAID_SALARY));

        TABLE_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_EMPLOYEE_CLOSURE.setName("employee_closure");
        TABLE_EMPLOYEE_CLOSURE.setId("_table_employeeClosure");
        TABLE_EMPLOYEE_CLOSURE.getColumns().addAll(List.of(COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE,
                COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE, COLUMN_DISTANCE_EMPLOYEE_CLOSURE));

        TABLE_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCT_CLASS.setName("product_class");
        TABLE_PRODUCT_CLASS.setId("_table_productClass");
        TABLE_PRODUCT_CLASS.getColumns()
                .addAll(List.of(COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS, COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS,
                        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS, COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS,
                        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS));

        // Initialize table queries
        QUERY_TIME = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_TIME.setTable(TABLE_TIME);

        QUERY_STORE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_STORE.setTable(TABLE_STORE);

        QUERY_CUSTOMER = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_CUSTOMER.setTable(TABLE_CUSTOMER);

        QUERY_PRODUCT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PRODUCT.setTable(TABLE_PRODUCT);

        QUERY_SALES_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_SALES_FACT.setTable(TABLE_SALES_FACT);

        // Initialize new table queries
        QUERY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_WAREHOUSE.setTable(TABLE_WAREHOUSE);

        QUERY_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_INVENTORY_FACT.setTable(TABLE_INVENTORY_FACT);

        QUERY_PROMOTION = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PROMOTION.setTable(TABLE_PROMOTION);

        QUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_EMPLOYEE.setTable(TABLE_EMPLOYEE);

        QUERY_DEPARTMENT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_DEPARTMENT.setTable(TABLE_DEPARTMENT);

        QUERY_POSITION = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_POSITION.setTable(TABLE_POSITION);

        QUERY_SALARY = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_SALARY.setTable(TABLE_SALARY);

        QUERY_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_EMPLOYEE_CLOSURE.setTable(TABLE_EMPLOYEE_CLOSURE);

        QUERY_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PRODUCT_CLASS.setTable(TABLE_PRODUCT_CLASS);

        // Initialize levels
        LEVEL_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_YEAR.setName("Year");
        LEVEL_YEAR.setColumn(COLUMN_THE_YEAR_TIME);
        LEVEL_YEAR.setId("_level_time_year");

        LEVEL_QUARTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_QUARTER.setName("Quarter");
        LEVEL_QUARTER.setColumn(COLUMN_QUARTER_TIME);
        LEVEL_QUARTER.setId("_level_time_quarter");

        LEVEL_MONTH = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MONTH.setName("Month");
        LEVEL_MONTH.setColumn(COLUMN_THE_MONTH_TIME);
        LEVEL_MONTH.setId("_level_time_month");

        LEVEL_STORE_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_COUNTRY.setName("Store Country");
        LEVEL_STORE_COUNTRY.setColumn(COLUMN_STORE_COUNTRY_STORE);
        LEVEL_STORE_COUNTRY.setId("_level_store_country");

        LEVEL_STORE_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_STATE.setName("Store State");
        LEVEL_STORE_STATE.setColumn(COLUMN_STORE_STATE_STORE);
        LEVEL_STORE_STATE.setId("_level_store_state");

        LEVEL_STORE_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CITY.setName("Store City");
        LEVEL_STORE_CITY.setColumn(COLUMN_STORE_CITY_STORE);
        LEVEL_STORE_CITY.setId("_level_store_city");

        LEVEL_STORE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NAME.setName("Store Name");
        LEVEL_STORE_NAME.setColumn(COLUMN_STORE_NAME_STORE);
        LEVEL_STORE_NAME.setId("_level_store_name");

        LEVEL_CUSTOMER_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_COUNTRY.setName("Country");
        LEVEL_CUSTOMER_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);
        LEVEL_CUSTOMER_COUNTRY.setId("_level_customer_country");

        LEVEL_CUSTOMER_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_STATE.setName("State Province");
        LEVEL_CUSTOMER_STATE.setColumn(COLUMN_STATE_PROVINCE_CUSTOMER);
        LEVEL_CUSTOMER_STATE.setId("_level_customer_stateProvince");

        LEVEL_CUSTOMER_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_CITY.setName("City");
        LEVEL_CUSTOMER_CITY.setColumn(COLUMN_CITY_CUSTOMER);
        LEVEL_CUSTOMER_CITY.setId("_level_customer_city");

        LEVEL_CUSTOMER_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_NAME.setName("Name");
        LEVEL_CUSTOMER_NAME.setColumn(COLUMN_FULLNAME_CUSTOMER);
        LEVEL_CUSTOMER_NAME.setId("_level_customer_name");

        LEVEL_CUSTOMER_GENDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_GENDER.setName("Gender");
        LEVEL_CUSTOMER_GENDER.setColumn(COLUMN_GENDER_CUSTOMER);
        LEVEL_CUSTOMER_GENDER.setId("_level_customer_gender");

        LEVEL_PRODUCT_FAMILY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_FAMILY.setName("Product Family");
        LEVEL_PRODUCT_FAMILY.setColumn(COLUMN_PRODUCT_FAMILY_PRODUCT);
        LEVEL_PRODUCT_FAMILY.setId("_level_product_family");

        LEVEL_PRODUCT_DEPARTMENT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_DEPARTMENT.setName("Product Department");
        LEVEL_PRODUCT_DEPARTMENT.setColumn(COLUMN_PRODUCT_DEPARTMENT_PRODUCT);
        LEVEL_PRODUCT_DEPARTMENT.setId("_level_product_department");

        LEVEL_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY.setName("Product Category");
        LEVEL_PRODUCT_CATEGORY.setColumn(COLUMN_PRODUCT_CATEGORY_PRODUCT);
        LEVEL_PRODUCT_CATEGORY.setId("_level_product_category");

        LEVEL_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY.setName("Product Subcategory");
        LEVEL_PRODUCT_SUBCATEGORY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_PRODUCT);
        LEVEL_PRODUCT_SUBCATEGORY.setId("_level_product_subcategory");

        LEVEL_PRODUCT_BRAND = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_BRAND.setName("Brand Name");
        LEVEL_PRODUCT_BRAND.setColumn(COLUMN_BRAND_NAME_PRODUCT);
        LEVEL_PRODUCT_BRAND.setId("_level_product_brand");

        LEVEL_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_NAME.setName("Product Name");
        LEVEL_PRODUCT_NAME.setColumn(COLUMN_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_NAME.setId("_level_product_name");

        // Initialize new levels
        LEVEL_WAREHOUSE_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_COUNTRY.setName("Country");
        LEVEL_WAREHOUSE_COUNTRY.setColumn(COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE);
        LEVEL_WAREHOUSE_COUNTRY.setId("_level_warehouse_country");

        LEVEL_WAREHOUSE_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_STATE.setName("State Province");
        LEVEL_WAREHOUSE_STATE.setColumn(COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE);
        LEVEL_WAREHOUSE_STATE.setId("_level_warehouse_state");

        LEVEL_WAREHOUSE_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_CITY.setName("City");
        LEVEL_WAREHOUSE_CITY.setColumn(COLUMN_WAREHOUSE_CITY_WAREHOUSE);
        LEVEL_WAREHOUSE_CITY.setId("_level_warehouse_city");

        LEVEL_WAREHOUSE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_NAME.setName("Warehouse Name");
        LEVEL_WAREHOUSE_NAME.setColumn(COLUMN_WAREHOUSE_NAME_WAREHOUSE);
        LEVEL_WAREHOUSE_NAME.setId("_level_warehouse_name");

        LEVEL_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PROMOTION_MEDIA.setName("Media Type");
        LEVEL_PROMOTION_MEDIA.setColumn(COLUMN_MEDIA_TYPE_PROMOTION);
        LEVEL_PROMOTION_MEDIA.setId("_level_promotion_media");

        LEVEL_EMPLOYEE_MANAGEMENT_ROLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_MANAGEMENT_ROLE.setName("Management Role");
        LEVEL_EMPLOYEE_MANAGEMENT_ROLE.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);
        LEVEL_EMPLOYEE_MANAGEMENT_ROLE.setId("_level_employee_management_role");

        LEVEL_EMPLOYEE_POSITION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_POSITION.setName("Position Title");
        LEVEL_EMPLOYEE_POSITION.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);
        LEVEL_EMPLOYEE_POSITION.setId("_level_employee_position");

        LEVEL_EMPLOYEE_DEPARTMENT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_DEPARTMENT.setName("Position ID");
        LEVEL_EMPLOYEE_DEPARTMENT.setColumn(COLUMN_POSITION_ID_EMPLOYEE);
        LEVEL_EMPLOYEE_DEPARTMENT.setId("_level_employee_department");

        LEVEL_EMPLOYEE_FULL_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_FULL_NAME.setName("Employee Name");
        LEVEL_EMPLOYEE_FULL_NAME.setColumn(COLUMN_FULL_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_FULL_NAME.setId("_level_employee_full_name");

        LEVEL_DEPARTMENT_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_DEPARTMENT_DESCRIPTION.setName("Department");
        LEVEL_DEPARTMENT_DESCRIPTION.setColumn(COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT);
        LEVEL_DEPARTMENT_DESCRIPTION.setId("_level_department_description");

        LEVEL_POSITION_TITLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_POSITION_TITLE.setName("Position Title");
        LEVEL_POSITION_TITLE.setColumn(COLUMN_POSITION_TITLE_POSITION);
        LEVEL_POSITION_TITLE.setId("_level_position_title");

        LEVEL_CUSTOMER_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_EDUCATION.setName("Education Level");
        LEVEL_CUSTOMER_EDUCATION.setColumn(COLUMN_EDUCATION_CUSTOMER);
        LEVEL_CUSTOMER_EDUCATION.setId("_level_customer_education");

        LEVEL_CUSTOMER_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_MARITAL_STATUS.setName("Marital Status");
        LEVEL_CUSTOMER_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);
        LEVEL_CUSTOMER_MARITAL_STATUS.setId("_level_customer_marital_status");

        // Initialize hierarchies
        HIERARCHY_TIME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME.setName("Time Hierarchy");
        HIERARCHY_TIME.setId("_hierarchy_time");
        HIERARCHY_TIME.setHasAll(true);
        HIERARCHY_TIME.setAllMemberName("All Years");
        HIERARCHY_TIME.setQuery(QUERY_TIME);
        HIERARCHY_TIME.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH));

        HIERARCHY_STORE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE.setName("Store Hierarchy");
        HIERARCHY_STORE.setId("_hierarchy_store");
        HIERARCHY_STORE.setHasAll(true);
        HIERARCHY_STORE.setAllMemberName("All Stores");
        HIERARCHY_STORE.setQuery(QUERY_STORE);
        HIERARCHY_STORE.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE, LEVEL_STORE_CITY, LEVEL_STORE_NAME));

        HIERARCHY_CUSTOMERS_GEO = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GEO.setName("Geography");
        HIERARCHY_CUSTOMERS_GEO.setId("_hierarchy_customer_geography");
        HIERARCHY_CUSTOMERS_GEO.setHasAll(true);
        HIERARCHY_CUSTOMERS_GEO.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMERS_GEO.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GEO.getLevels().addAll(
                List.of(LEVEL_CUSTOMER_COUNTRY, LEVEL_CUSTOMER_STATE, LEVEL_CUSTOMER_CITY, LEVEL_CUSTOMER_NAME));

        HIERARCHY_CUSTOMERS_GENDER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GENDER.setName("Gender");
        HIERARCHY_CUSTOMERS_GENDER.setId("_hierarchy_customer_gender");
        HIERARCHY_CUSTOMERS_GENDER.setHasAll(true);
        HIERARCHY_CUSTOMERS_GENDER.setAllMemberName("All Gender");
        HIERARCHY_CUSTOMERS_GENDER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GENDER.getLevels().add(LEVEL_CUSTOMER_GENDER);

        HIERARCHY_PRODUCT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PRODUCT.setName("Product Hierarchy");
        HIERARCHY_PRODUCT.setId("_hierarchy_product");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setAllMemberName("All Products");
        HIERARCHY_PRODUCT.setQuery(QUERY_PRODUCT);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(LEVEL_PRODUCT_FAMILY, LEVEL_PRODUCT_DEPARTMENT,
                LEVEL_PRODUCT_CATEGORY, LEVEL_PRODUCT_SUBCATEGORY, LEVEL_PRODUCT_BRAND, LEVEL_PRODUCT_NAME));

        // Initialize new hierarchies
        HIERARCHY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_WAREHOUSE.setName("Warehouse");
        HIERARCHY_WAREHOUSE.setId("_hierarchy_warehouse");
        HIERARCHY_WAREHOUSE.setHasAll(true);
        HIERARCHY_WAREHOUSE.setAllMemberName("All Warehouses");
        HIERARCHY_WAREHOUSE.setQuery(QUERY_WAREHOUSE);
        HIERARCHY_WAREHOUSE.getLevels().addAll(
                List.of(LEVEL_WAREHOUSE_COUNTRY, LEVEL_WAREHOUSE_STATE, LEVEL_WAREHOUSE_CITY, LEVEL_WAREHOUSE_NAME));

        HIERARCHY_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PROMOTION_MEDIA.setName("Promotion Media");
        HIERARCHY_PROMOTION_MEDIA.setId("_hierarchy_promotion_media");
        HIERARCHY_PROMOTION_MEDIA.setHasAll(true);
        HIERARCHY_PROMOTION_MEDIA.setAllMemberName("All Media");
        HIERARCHY_PROMOTION_MEDIA.setQuery(QUERY_PROMOTION);
        HIERARCHY_PROMOTION_MEDIA.getLevels().addAll(List.of(LEVEL_PROMOTION_MEDIA));

        HIERARCHY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_EMPLOYEE.setName("Employee");
        HIERARCHY_EMPLOYEE.setId("_hierarchy_employee");
        HIERARCHY_EMPLOYEE.setHasAll(true);
        HIERARCHY_EMPLOYEE.setAllMemberName("All Employees");
        HIERARCHY_EMPLOYEE.setQuery(QUERY_EMPLOYEE);
        HIERARCHY_EMPLOYEE.getLevels().addAll(List.of(LEVEL_EMPLOYEE_MANAGEMENT_ROLE, LEVEL_EMPLOYEE_POSITION,
                LEVEL_EMPLOYEE_DEPARTMENT, LEVEL_EMPLOYEE_FULL_NAME));

        HIERARCHY_DEPARTMENT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEPARTMENT.setName("Department");
        HIERARCHY_DEPARTMENT.setId("_hierarchy_department");
        HIERARCHY_DEPARTMENT.setHasAll(true);
        HIERARCHY_DEPARTMENT.setAllMemberName("All Departments");
        HIERARCHY_DEPARTMENT.setQuery(QUERY_DEPARTMENT);
        HIERARCHY_DEPARTMENT.getLevels().addAll(List.of(LEVEL_DEPARTMENT_DESCRIPTION));

        HIERARCHY_POSITION = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_POSITION.setName("Position");
        HIERARCHY_POSITION.setId("_hierarchy_position");
        HIERARCHY_POSITION.setHasAll(true);
        HIERARCHY_POSITION.setAllMemberName("All Positions");
        HIERARCHY_POSITION.setQuery(QUERY_POSITION);
        HIERARCHY_POSITION.getLevels().addAll(List.of(LEVEL_POSITION_TITLE));

        HIERARCHY_CUSTOMERS_EDUCATION = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_EDUCATION.setName("Customers Education");
        HIERARCHY_CUSTOMERS_EDUCATION.setId("_hierarchy_customers_education");
        HIERARCHY_CUSTOMERS_EDUCATION.setHasAll(true);
        HIERARCHY_CUSTOMERS_EDUCATION.setAllMemberName("All Education Levels");
        HIERARCHY_CUSTOMERS_EDUCATION.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_EDUCATION.getLevels().addAll(List.of(LEVEL_CUSTOMER_EDUCATION));

        HIERARCHY_CUSTOMERS_MARITAL = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_MARITAL.setName("Customers Marital Status");
        HIERARCHY_CUSTOMERS_MARITAL.setId("_hierarchy_customers_marital");
        HIERARCHY_CUSTOMERS_MARITAL.setHasAll(true);
        HIERARCHY_CUSTOMERS_MARITAL.setAllMemberName("All Marital Statuses");
        HIERARCHY_CUSTOMERS_MARITAL.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_MARITAL.getLevels().addAll(List.of(LEVEL_CUSTOMER_MARITAL_STATUS));

        // Initialize dimensions
        DIMENSION_TIME = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_TIME.setName("Time");
        DIMENSION_TIME.setId("_dimension_time");
        DIMENSION_TIME.getHierarchies().add(HIERARCHY_TIME);

        DIMENSION_STORE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE.setName("Store");
        DIMENSION_STORE.setId("_dimension_store");
        DIMENSION_STORE.getHierarchies().add(HIERARCHY_STORE);

        DIMENSION_CUSTOMERS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMERS.setName("Customers");
        DIMENSION_CUSTOMERS.setId("_dimension_customers");
        DIMENSION_CUSTOMERS.getHierarchies().addAll(List.of(HIERARCHY_CUSTOMERS_GEO, HIERARCHY_CUSTOMERS_GENDER,
                HIERARCHY_CUSTOMERS_EDUCATION, HIERARCHY_CUSTOMERS_MARITAL));

        DIMENSION_PRODUCT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PRODUCT.setName("Product");
        DIMENSION_PRODUCT.setId("_dimension_product");
        DIMENSION_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        // Initialize new dimensions
        DIMENSION_WAREHOUSE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_WAREHOUSE.setName("Warehouse");
        DIMENSION_WAREHOUSE.setId("_dimension_warehouse");
        DIMENSION_WAREHOUSE.getHierarchies().add(HIERARCHY_WAREHOUSE);

        DIMENSION_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PROMOTION_MEDIA.setName("Promotion Media");
        DIMENSION_PROMOTION_MEDIA.setId("_dimension_promotion_media");
        DIMENSION_PROMOTION_MEDIA.getHierarchies().add(HIERARCHY_PROMOTION_MEDIA);

        DIMENSION_EMPLOYEE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_EMPLOYEE.setName("Employee");
        DIMENSION_EMPLOYEE.setId("_dimension_employee");
        DIMENSION_EMPLOYEE.getHierarchies().add(HIERARCHY_EMPLOYEE);

        DIMENSION_DEPARTMENT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEPARTMENT.setName("Department");
        DIMENSION_DEPARTMENT.setId("_dimension_department");
        DIMENSION_DEPARTMENT.getHierarchies().add(HIERARCHY_DEPARTMENT);

        DIMENSION_POSITION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_POSITION.setName("Position");
        DIMENSION_POSITION.setId("_dimension_position");
        DIMENSION_POSITION.getHierarchies().add(HIERARCHY_POSITION);

        // Initialize measures
        MEASURE_UNIT_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES.setName("Unit Sales");
        MEASURE_UNIT_SALES.setId("_measure_unitSales");
        MEASURE_UNIT_SALES.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES.setFormatString("#,###");

        MEASURE_STORE_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES.setName("Store Sales");
        MEASURE_STORE_SALES.setId("_measure_storeSales");
        MEASURE_STORE_SALES.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES.setFormatString("$#,##0.00");

        MEASURE_STORE_COST = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST.setName("Store Cost");
        MEASURE_STORE_COST.setId("_measure_storeCost");
        MEASURE_STORE_COST.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST.setFormatString("$#,##0.00");

        MEASURE_SALES_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT.setName("Sales Count");
        MEASURE_SALES_COUNT.setId("_measure_salesCount");
        MEASURE_SALES_COUNT.setFormatString("#,###");

        // Initialize warehouse measures
        MEASURE_WAREHOUSE_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_SALES.setName("Warehouse Sales");
        MEASURE_WAREHOUSE_SALES.setId("_measure_warehouseSales");
        MEASURE_WAREHOUSE_SALES.setColumn(COLUMN_WAREHOUSE_SALES_INVENTORY_FACT);
        MEASURE_WAREHOUSE_SALES.setFormatString("$#,##0.00");

        MEASURE_WAREHOUSE_COST = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_COST.setName("Warehouse Cost");
        MEASURE_WAREHOUSE_COST.setId("_measure_warehouseCost");
        MEASURE_WAREHOUSE_COST.setColumn(COLUMN_WAREHOUSE_COST_INVENTORY_FACT);
        MEASURE_WAREHOUSE_COST.setFormatString("$#,##0.00");

        MEASURE_UNITS_SHIPPED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_SHIPPED.setName("Units Shipped");
        MEASURE_UNITS_SHIPPED.setId("_measure_unitsShipped");
        MEASURE_UNITS_SHIPPED.setColumn(COLUMN_UNITS_SHIPPED_INVENTORY_FACT);
        MEASURE_UNITS_SHIPPED.setFormatString("#,###");

        MEASURE_UNITS_ORDERED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_ORDERED.setName("Units Ordered");
        MEASURE_UNITS_ORDERED.setId("_measure_unitsOrdered");
        MEASURE_UNITS_ORDERED.setColumn(COLUMN_UNITS_ORDERED_INVENTORY_FACT);
        MEASURE_UNITS_ORDERED.setFormatString("#,###");

        // Initialize store measures
        MEASURE_STORE_SQFT = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SQFT.setName("Store Sqft");
        MEASURE_STORE_SQFT.setId("_measure_storeSqft");
        MEASURE_STORE_SQFT.setColumn(COLUMN_STORE_SQFT_STORE);
        MEASURE_STORE_SQFT.setFormatString("#,###");

        MEASURE_GROCERY_SQFT = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_GROCERY_SQFT.setName("Grocery Sqft");
        MEASURE_GROCERY_SQFT.setId("_measure_grocerySqft");
        MEASURE_GROCERY_SQFT.setColumn(COLUMN_GROCERY_SQFT_STORE);
        MEASURE_GROCERY_SQFT.setFormatString("#,###");

        // Initialize HR measures
        MEASURE_ORG_SALARY = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_ORG_SALARY.setName("Org Salary");
        MEASURE_ORG_SALARY.setId("_measure_orgSalary");
        MEASURE_ORG_SALARY.setColumn(COLUMN_SALARY_PAID_SALARY);
        MEASURE_ORG_SALARY.setFormatString("$#,##0.00");

        MEASURE_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_COUNT.setName("Count");
        MEASURE_COUNT.setId("_measure_count");
        MEASURE_COUNT.setFormatString("#,###");

        MEASURE_NUMBER_OF_EMPLOYEES = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_NUMBER_OF_EMPLOYEES.setName("Number of Employees");
        MEASURE_NUMBER_OF_EMPLOYEES.setId("_measure_numberOfEmployees");
        MEASURE_NUMBER_OF_EMPLOYEES.setFormatString("#,###");

        // Initialize measure groups
        MEASUREGROUP_SALES = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_SALES.getMeasures()
                .addAll(List.of(MEASURE_UNIT_SALES, MEASURE_STORE_SALES, MEASURE_STORE_COST, MEASURE_SALES_COUNT));

        MEASUREGROUP_WAREHOUSE = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_WAREHOUSE.getMeasures().addAll(
                List.of(MEASURE_WAREHOUSE_SALES, MEASURE_WAREHOUSE_COST, MEASURE_UNITS_SHIPPED, MEASURE_UNITS_ORDERED));

        MEASUREGROUP_STORE = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_STORE.getMeasures().addAll(List.of(MEASURE_STORE_SQFT, MEASURE_GROCERY_SQFT));

        MEASUREGROUP_HR = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_HR.getMeasures().addAll(List.of(MEASURE_ORG_SALARY, MEASURE_COUNT, MEASURE_NUMBER_OF_EMPLOYEES));

        // Initialize dimension connectors
        CONNECTOR_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);
        CONNECTOR_TIME.setId("_connector_time");

        CONNECTOR_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_STORE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_STORE.setId("_connector_store");

        CONNECTOR_CUSTOMER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMER.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_CUSTOMER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_CUSTOMER.setId("_connector_customer");

        CONNECTOR_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);
        CONNECTOR_PRODUCT.setId("_connector_product");

        // Initialize warehouse cube connectors
        CONNECTOR_WAREHOUSE_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_WAREHOUSE_TIME.setForeignKey(COLUMN_TIME_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_TIME.setId("_connector_warehouse_time");

        CONNECTOR_WAREHOUSE_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_WAREHOUSE_STORE.setForeignKey(COLUMN_STORE_INVOICE_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_STORE.setId("_connector_warehouse_store");

        CONNECTOR_WAREHOUSE_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_WAREHOUSE_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_PRODUCT.setId("_connector_warehouse_product");

        CONNECTOR_WAREHOUSE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_WAREHOUSE.setDimension(DIMENSION_WAREHOUSE);
        CONNECTOR_WAREHOUSE_WAREHOUSE.setForeignKey(COLUMN_WAREHOUSE_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_WAREHOUSE.setId("_connector_warehouse_warehouse");

        // Initialize HR cube connectors
        CONNECTOR_HR_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_HR_TIME.setForeignKey(COLUMN_PAY_DATE_SALARY);
        CONNECTOR_HR_TIME.setId("_connector_hr_time");

        CONNECTOR_HR_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_HR_STORE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_STORE.setId("_connector_hr_store");

        CONNECTOR_HR_EMPLOYEE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_EMPLOYEE.setDimension(DIMENSION_EMPLOYEE);
        CONNECTOR_HR_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_EMPLOYEE.setId("_connector_hr_employee");

        CONNECTOR_HR_POSITION = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_POSITION.setDimension(DIMENSION_POSITION);
        CONNECTOR_HR_POSITION.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_POSITION.setId("_connector_hr_position");

        CONNECTOR_HR_DEPARTMENT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_DEPARTMENT.setDimension(DIMENSION_DEPARTMENT);
        CONNECTOR_HR_DEPARTMENT.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_DEPARTMENT.setId("_connector_hr_department");

        // Initialize Store cube connectors
        CONNECTOR_STORE_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_STORE_STORE.setId("_connector_store_store");

        CONNECTOR_STORE_HAS_COFFEE_BAR = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_HAS_COFFEE_BAR.setDimension(DIMENSION_STORE); // This would need a coffee bar dimension, but for
                                                                      // now using store
        CONNECTOR_STORE_HAS_COFFEE_BAR.setId("_connector_store_has_coffee_bar");

        // Initialize calculated members
        CALCULATED_MEMBER_PROFIT = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT.setName("Profit");
        CALCULATED_MEMBER_PROFIT.setId("_calculated_member_profit");
        CALCULATED_MEMBER_PROFIT.setFormula("[Measures].[Store Sales] - [Measures].[Store Cost]");
        CALCULATED_MEMBER_PROFIT.setFormatString("$#,##0.00");

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.setName("Profit last Period");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.setId("_calculated_member_profit_last_period");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD
                .setFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER), Measures.[Profit])");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.setFormatString("$#,##0.00");

        CALCULATED_MEMBER_PROFIT_GROWTH = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_GROWTH.setName("Profit Growth");
        CALCULATED_MEMBER_PROFIT_GROWTH.setId("_calculated_member_profit_growth");
        CALCULATED_MEMBER_PROFIT_GROWTH.setFormula(
                "([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]");
        CALCULATED_MEMBER_PROFIT_GROWTH.setFormatString("0.0%");
        CALCULATED_MEMBER_PROFIT_GROWTH.setVisible(true);

        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setName("Average Warehouse Sale");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setId("_calculated_member_average_warehouse_sale");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE
                .setFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setFormatString("$#,##0.00");

        CALCULATED_MEMBER_EMPLOYEE_SALARY = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setName("Employee Salary");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setId("_calculated_member_employee_salary");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormatString("$#,##0.00");

        CALCULATED_MEMBER_AVG_SALARY = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVG_SALARY.setName("Avg Salary");
        CALCULATED_MEMBER_AVG_SALARY.setId("_calculated_member_avg_salary");
        CALCULATED_MEMBER_AVG_SALARY.setFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]");
        CALCULATED_MEMBER_AVG_SALARY.setFormatString("$#,##0.00");

        // Initialize cubes
        CUBE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES.setName("Sales");
        CUBE_SALES.setId("_cube_sales");
        CUBE_SALES.setQuery(QUERY_SALES_FACT);
        CUBE_SALES.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_TIME, CONNECTOR_STORE, CONNECTOR_CUSTOMER, CONNECTOR_PRODUCT));
        CUBE_SALES.getMeasureGroups().add(MEASUREGROUP_SALES);
        CUBE_SALES.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_LAST_PERIOD,
                CALCULATED_MEMBER_PROFIT_GROWTH));

        CUBE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_WAREHOUSE.setName("Warehouse");
        CUBE_WAREHOUSE.setId("_cube_warehouse");
        CUBE_WAREHOUSE.setQuery(QUERY_INVENTORY_FACT);
        CUBE_WAREHOUSE.getDimensionConnectors().addAll(List.of(CONNECTOR_WAREHOUSE_TIME, CONNECTOR_WAREHOUSE_STORE,
                CONNECTOR_WAREHOUSE_PRODUCT, CONNECTOR_WAREHOUSE_WAREHOUSE));
        CUBE_WAREHOUSE.getMeasureGroups().add(MEASUREGROUP_WAREHOUSE);
        CUBE_WAREHOUSE.getCalculatedMembers().add(CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE);

        CUBE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_STORE.setName("Store");
        CUBE_STORE.setId("_cube_store");
        CUBE_STORE.setQuery(QUERY_STORE);
        CUBE_STORE.getDimensionConnectors().addAll(List.of(CONNECTOR_STORE_STORE, CONNECTOR_STORE_HAS_COFFEE_BAR));
        CUBE_STORE.getMeasureGroups().add(MEASUREGROUP_STORE);

        CUBE_HR = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_HR.setName("HR");
        CUBE_HR.setId("_cube_hr");
        CUBE_HR.setQuery(QUERY_SALARY);
        CUBE_HR.getDimensionConnectors().addAll(List.of(CONNECTOR_HR_TIME, CONNECTOR_HR_STORE, CONNECTOR_HR_EMPLOYEE,
                CONNECTOR_HR_POSITION, CONNECTOR_HR_DEPARTMENT));
        CUBE_HR.getMeasureGroups().add(MEASUREGROUP_HR);
        CUBE_HR.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_EMPLOYEE_SALARY, CALCULATED_MEMBER_AVG_SALARY));

        // Initialize access grants
        GRANT_ADMIN_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_ADMIN_CATALOG.setCatalogAccess(CatalogAccess.ALL);

        GRANT_NO_HR_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_NO_HR_CATALOG.setCatalogAccess(CatalogAccess.ALL);

        GRANT_NO_HR_CUBE = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_NO_HR_CUBE.setCube(CUBE_HR);
        GRANT_NO_HR_CUBE.setCubeAccess(CubeAccess.NONE);

        GRANT_NO_HR_CATALOG.getCubeGrants().add(GRANT_NO_HR_CUBE);

        GRANT_CALIFORNIA_MANAGER_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_CALIFORNIA_MANAGER_CATALOG.setCatalogAccess(CatalogAccess.NONE);

        GRANT_CALIFORNIA_MANAGER_SALES_CUBE = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCube(CUBE_SALES);
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCubeAccess(CubeAccess.ALL);

        GRANT_CALIFORNIA_MANAGER_CATALOG.getCubeGrants().add(GRANT_CALIFORNIA_MANAGER_SALES_CUBE);

        // Initialize access roles
        ROLE_ADMINISTRATOR = RolapMappingFactory.eINSTANCE.createAccessRole();
        ROLE_ADMINISTRATOR.setName("Administrator");
        ROLE_ADMINISTRATOR.setId("_role_administrator");
        ROLE_ADMINISTRATOR.getAccessCatalogGrants().add(GRANT_ADMIN_CATALOG);

        ROLE_NO_HR_CUBE = RolapMappingFactory.eINSTANCE.createAccessRole();
        ROLE_NO_HR_CUBE.setName("No HR Cube");
        ROLE_NO_HR_CUBE.setId("_role_no_hr_cube");
        ROLE_NO_HR_CUBE.getAccessCatalogGrants().add(GRANT_NO_HR_CATALOG);

        ROLE_CALIFORNIA_MANAGER = RolapMappingFactory.eINSTANCE.createAccessRole();
        ROLE_CALIFORNIA_MANAGER.setName("California manager");
        ROLE_CALIFORNIA_MANAGER.setId("_role_california_manager");
        ROLE_CALIFORNIA_MANAGER.getAccessCatalogGrants().add(GRANT_CALIFORNIA_MANAGER_CATALOG);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_FOODMART = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA_FOODMART.setId("_databaseSchema_foodmart");
        DATABASE_SCHEMA_FOODMART.setName("foodmart");
        DATABASE_SCHEMA_FOODMART.getTables()
                .addAll(List.of(TABLE_SALES_FACT, TABLE_TIME, TABLE_STORE, TABLE_CUSTOMER, TABLE_PRODUCT,
                        TABLE_WAREHOUSE, TABLE_INVENTORY_FACT, TABLE_PROMOTION, TABLE_EMPLOYEE, TABLE_DEPARTMENT,
                        TABLE_POSITION, TABLE_SALARY, TABLE_EMPLOYEE_CLOSURE, TABLE_PRODUCT_CLASS));

        CATALOG_FOODMART = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_FOODMART.setName("FoodMart");
        CATALOG_FOODMART.setDescription("FoodMart Sample Database - EMF Version");
        CATALOG_FOODMART.setId("_catalog_foodmart");
        CATALOG_FOODMART.getDbschemas().add(DATABASE_SCHEMA_FOODMART);
        CATALOG_FOODMART.getCubes().addAll(List.of(CUBE_SALES, CUBE_WAREHOUSE, CUBE_STORE, CUBE_HR));
        CATALOG_FOODMART.getAccessRoles().addAll(List.of(ROLE_CALIFORNIA_MANAGER, ROLE_NO_HR_CUBE, ROLE_ADMINISTRATOR));

        // Add documentation
        document(CATALOG_FOODMART, "FoodMart Database", foodMartBody, 1, 0, 0, false, 0);
        document(CUBE_SALES, "Sales Cube", salesCubeBody, 1, 1, 0, true, 0);
        document(DIMENSION_TIME, "Time Dimension", timeBody, 1, 2, 0, true, 0);
        document(DIMENSION_STORE, "Store Dimension", storeBody, 1, 3, 0, true, 0);
        document(DIMENSION_CUSTOMERS, "Customers Dimension", customersBody, 1, 4, 0, true, 0);
        document(DIMENSION_PRODUCT, "Product Dimension", productBody, 1, 5, 0, true, 0);
    }

    @Override
    public CatalogMapping get() {
        return CATALOG_FOODMART;
    }
}