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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationName;
import org.eclipse.daanse.rolap.mapping.model.Annotation;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.access.common.CatalogAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.CountMeasure;
import org.eclipse.daanse.rolap.mapping.model.access.olap.CubeAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.access.olap.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.access.olap.MemberAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.access.common.CommonFactory;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseFactory;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapFactory;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.4", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    public static final StandardDimension DIMENSION_STORE_TYPE;
    public static final TableSource QUERY_CUSTOMER;
    public static final Level LEVEL_EMPLOYEE_POSITION;
    public static final SumMeasure MEASURE_GROCERY_SQFT;
    public static final Level LEVEL_CITY_TABLE_COLUMN_STORE_CITY;
    public static final Level LEVEL_CUSTOMER_COUNTRY;
    public static final SumMeasure MEASURE_WAREHOUSE_STORE_INVOICE;
    public static final SumMeasure MEASURE_UNITS_ORDERED;
    public static final SumMeasure MEASURE_STORE_SALES_RAGGED;
    public static final Level LEVEL_CUSTOMER_EDUCATION;
    public static final StandardDimension DIMENSION_DEPARTMENT;
    public static final StandardDimension DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE;
    public static final StandardDimension DIMENSION_STORE;
    public static final TableSource QUERY_PRODUCT_CLASS;
    public static final SumMeasure MEASURE_STORE_SALES;
    public static final SumMeasure MEASURE_STORE_COST_WITH_PROPERTY;
    public static final Level LEVEL_WAREHOUSE_COUNTRY;
    public static final Level LEVEL_PRODUCT_CATEGORY;
    public static final TableSource QUERY_EMPLOYEE_CLOSURE;
    public static final SumMeasure MEASURE_WAREHOUSE_SUPPLY_TIME;
    public static final ExplicitHierarchy HIERARCHY_PROMOTIONS;
    public static final StandardDimension DIMENSION_MARITAL_STATUS;
    public static final ExplicitHierarchy HIERARCHY_POSITION;
    public static final Level LEVEL_STORE_SQFT;
    public static final CountMeasure MEASURE_NUMBER_OF_EMPLOYEES;
    public static final StandardDimension DIMENSION_STORE_SIZE_IN_SQFT;
    public static final Level LEVEL_STORE_STATE;
    public static final Level LEVEL_POSITION_TITLE;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT;
    public static final Level LEVEL_PROMOTION_MEDIA;
    public static final Level LEVEL_STORE_NAME;
    public static final Level LEVEL_DAY;
    public static final ExplicitHierarchy HIERARCHY_MARITAL_STATUS;
    public static final StandardDimension DIMENSION_YEARLY_INCOME;
    public static final VirtualCube CUBE_VIRTIAL_WAREHOUSE_AND_SALES;
    public static final SumMeasure MEASURE_PROMOTION_SALES;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY;
    public static final StandardDimension DIMENSION_PROMOTIONS;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_EDUCATION;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE;
    public static final Level LEVEL_EMPLOYEE_ID;
    public static final TimeDimension DIMENSION_HR_TIME;
    public static final ExplicitHierarchy HIERARCHY_YEARLY_INCOME;
    public static final Level LEVEL_CUSTOMER_NAME;
    public static final Level LEVEL_EMPLOYEE_DEPARTMENT;
    public static final SumMeasure MEASURE_STORE_COST;
    public static final Level LEVEL_PRODUCT_BRAND;
    public static final Level LEVEL_CUSTOMER_MARITAL_STATUS;
    public static final Level LEVEL_MARITAL_STATUS;
    public static final TableSource QUERY_STORE;
    public static final Level LEVEL_COUNTRY_WITH_NEVER;
    public static final Level LEVEL_CUSTOMER_STATE;
    public static final Level LEVEL_WAREHOUSE_CITY;
    public static final Level LEVEL_EDUCATION;
    public static final SumMeasure MEASURE_WAREHOUSE_COST;
    public static final StandardDimension DIMENSION_PRODUCT;
    public static final TableSource QUERY_SALARY;
    public static final PhysicalCube CUBE_WAREHOUSE;
    public static final StandardDimension DIMENSION_CUSTOMERS;
    public static final StandardDimension DIMENSION_PROMOTION_MEDIA;
    public static final PhysicalCube CUBE_HR;
    public static final Level LEVEL_PAY_TYPE;
    public static final AccessRole ROLE_NO_HR_CUBE;
    public static final ExplicitHierarchy HIERARCHY_EDUCATION_LEVEL;
    public static final Catalog CATALOG_FOODMART;
    public static final SumMeasure MEASURE_ORG_SALARY;
    public static final TableSource QUERY_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_PAY_TYPE;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITHOUT_QUERY;
    public static final Level LEVEL_CUSTOMER_GENDER;
    public static final Level LEVEL_PRODUCT_NAME;
    public static final Level LEVEL_DEPARTMENT_DESCRIPTION;
    public static final SumMeasure MEASURE_UNIT_SALES_MEMBER_ORDINAL;
    public static final Level LEVEL_YEAR;
    public static final StandardDimension DIMENSION_PAY_TYPE;
    public static final Level LEVEL_STORE_HAS_COFFEE_BAR;
    public static final TableSource QUERY_INVENTORY_FACT;
    public static final Level LEVEL_STORE_CYTY_IF_BLANK_NAME;
    public static final Level LEVEL_EMPLOYEE_FULL_NAME;
    public static final PhysicalCube CUBE_STORE;
    public static final SumMeasure MEASURE_WAREHOUSE_SALES;
    public static final TableSource QUERY_PROMOTION;
    public static final SumMeasure MEASURE_UNIT_SALES;
    public static final Level LEVEL_STORE_TYPE;
    public static final StandardDimension DIMENSION_STORE_SALES_RAGGED;
    public static final ExplicitHierarchy HIERARCHY_DEPARTMENT;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE;
    public static final Level LEVEL_STATE;
    public static final ExplicitHierarchy HIERARCHY_HR_POSITION;
    public static final StandardDimension DIMENSION_POSITION;
    public static final Level LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY;
    public static final StandardDimension DIMENSION_GENDER;
    public static final Level LEVEL_STORE_CYTY_IF_PARENTS_NAME;
    public static final TableSource QUERY_DEPARTMENT;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT_RAGGED;
    public static final Level LEVEL_WAREHOUSE_STATE;
    public static final TableSource QUERY_EMPLOYEE;
    public static final CountMeasure MEASURE_SALES_COUNT_WITH_PROPERTY;
    public static final ExplicitHierarchy HIERARCHY_STORE_SIZE_IN_SQFT;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_MARITAL;
    public static final Level LEVEL_PRODUCT_FAMILY;
    public static final Level LEVEL_STORE_CITY;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE;
    public static final SumMeasure MEASURE_STORE_COST_RAGGED;
    public static final AccessRole ROLE_CALIFORNIA_MANAGER;
    public static final Level LEVEL_STORE_COUNTRY;
    public static final Level LEVEL_WEEK;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT_WITH_PROPERTY;
    public static final StandardDimension DIMENSION_HR_POSITION;
    public static final SumMeasure MEASURE_STORE_SALES_WITH_PROPERTY;
    public static final Level LEVEL_MONTH;
    public static final SumMeasure MEASURE_WAREHOUSE_PROFIT;
    public static final TableSource QUERY_POSITION;
    public static final ExplicitHierarchy HIERARCHY_PROMOTION_MEDIA;
    public static final Level LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR;
    public static final Level LEVEL_WAREHOUSE_NAME;
    public static final SumMeasure MEASURE_UNITS_SHIPPED;
    public static final Level LEVEL_PROMOTION_NAME;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE_WITHOUT_TABLE;
    public static final PhysicalCube CUBE_SALES;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITH_QUERY_STORE;
    public static final TimeDimension DIMENSION_TIME;
    public static final Schema DATABASE_SCHEMA_FOODMART;
    public static final Level LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER;
    public static final TableSource QUERY_SALES_FACT;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GENDER;
    public static final StandardDimension DIMENSION_WAREHOUSE;
    public static final Level LEVEL_STORE_COUNTRY_WITH_NEVER;
    public static final StandardDimension DIMENSION_EMPLOYEE;
    public static final TableSource QUERY_WAREHOUSE;
    public static final CountMeasure MEASURE_COUNT;
    public static final Level LEVEL_HR_POSITION_TITLE;
    public static final ExplicitHierarchy HIERARCHY_STORE_HAS_COFFEE_BAR;
    public static final Level LEVEL_GENDER;
    public static final TableSource QUERY_STORE_RAGGED;
    public static final SumMeasure MEASURE_UNIT_SALES_RAGGED;
    public static final Level LEVEL_CITY_TABLE_COLUMN_CITY;
    public static final SumMeasure MEASURE_STORE_SQFT;
    public static final Level LEVEL_STORE_TYPE_WITHOUT_TABLE;
    public static final PhysicalCube CUBE_SALES_2;
    public static final AccessRole ROLE_ADMINISTRATOR;
    public static final Level LEVEL_EMPLOYEE_MANAGEMENT_ROLE;
    public static final StandardDimension DIMENSION_STORE_HAS_COFFEE_BAR;
    public static final StandardDimension DIMENSION_GEOGRAPHY;
    public static final ParentChildHierarchy HIERARCHY_EMPLOYEE;
    public static final Level LEVEL_CUSTOMER_CITY;
    public static final Level LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE;
    public static final CountMeasure MEASURE_SALES_COUNT_RAGGED;
    public static final ExplicitHierarchy HIERARCHY_GENDER;
    public static final CountMeasure MEASURE_SALES_COUNT;
    public static final Level LEVEL_YEARLY_INCOME;
    public static final Level LEVEL_MANAGEMENT_ROLE;
    public static final StandardDimension DIMENSION_EDUCATION_LEVEL;
    public static final PhysicalCube CUBE_SALES_RAGGED;
    public static final Level LEVEL_QUARTER;
    public static final TableSource QUERY_TIME_BY_DAY;


    // Static columns - Sales Fact Table
    public static final Column COLUMN_TIME_ID_SALESFACT;
    public static final Column COLUMN_STORE_ID_SALESFACT;
    public static final Column COLUMN_CUSTOMER_ID_SALESFACT;
    public static final Column COLUMN_PROMOTION_ID_SALESFACT;
    public static final Column COLUMN_PRODUCT_ID_SALESFACT;
    public static final Column COLUMN_UNIT_SALES_SALESFACT;
    public static final Column COLUMN_STORE_SALES_SALESFACT;
    public static final Column COLUMN_STORE_COST_SALESFACT;

    public static final Column COLUMN_PRODUCT_ID_SALESFACT1998;
    public static final Column COLUMN_TIME_ID_SALESFACT1998;
    public static final Column COLUMN_CUSTOMER_ID_SALESFACT1998;
    public static final Column COLUMN_PROMOTION_ID_SALESFACT1998;
    public static final Column COLUMN_STORE_ID_SALESFACT1998;
    public static final Column COLUMN_STORE_SALES_SALESFACT1998;
    public static final Column COLUMN_STORE_COST_SALESFACT1998;
    public static final Column COLUMN_UNIT_SALES_SALESFACT1998;

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
    public static final Column COLUMN_LNAME_CUSTOMER;
    public static final Column COLUMN_FNAME_CUSTOMER;
    public static final Column COLUMN_ACCOUNT_NUM_CUSTOMER;
    public static final Column COLUMN_CUSTOMER_REGION_ID_CUSTOMER;
    public static final Column COLUMN_NUM_CARS_OWNED_CUSTOMER;
    public static final Column COLUMN_TOTAL_CHILDREN_CUSTOMER;
    public static final Column COLUMN_ADDRESS2_CUSTOMER;

    // Static columns - Product Table
    //product_class_id,product_id,brand_name,product_name,SKU,SRP,gross_weight,net_weight,recyclable_package,low_fat,units_per_case,cases_per_pallet,shelf_width,shelf_height,shelf_depth
    public static final Column COLUMN_PRODUCT_CLASS_ID_PRODUCT;
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
    public static final Column COLUMN_STORES_ID_WAREHOUSE;

    // Inventory fact table columns
    //product_id,time_id,warehouse_id,store_id,units_ordered,units_shipped,warehouse_sales,warehouse_cost,supply_time,store_invoice
    public static final Column COLUMN_PRODUCT_ID_INVENTORY_FACT;
    public static final Column COLUMN_TIME_ID_INVENTORY_FACT;
    public static final Column COLUMN_WAREHOUSE_ID_INVENTORY_FACT;
    public static final Column COLUMN_STORE_ID_INVENTORY_FACT;
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
    public static final Column COLUMN_SALARY_EMPLOYEE;
    public static final Column COLUMN_EDUCATION_LEVEL_EMPLOYEE;

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
    //pay_date,employee_id,department_id,currency_id,salary_paid,overtime_paid,vacation_accrued,vacation_used
    //TIMESTAMP,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),REAL,REAL
    public static final Column COLUMN_EMPLOYEE_ID_SALARY;
    public static final Column COLUMN_DEPARTMENT_ID_SALARY;
    public static final Column COLUMN_CURRENCY_ID_SALARY;
    public static final Column COLUMN_PAY_DATE_SALARY;
    public static final Column COLUMN_SALARY_PAID_SALARY;
    public static final Column COLUMN_OVERTIME_PAID_SALARY;
    public static final Column COLUMN_VACATION_ACCRUED_SALARY;
    public static final Column COLUMN_VACATION_USED_SALARY;

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
    public static final Column COLUMN_STREET_ADDRESS_STORE;

    public static final Column COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997;

    public static final Column COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997;

    public static final Column COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997;

    public static final Column COLUMN_PRODUCT_ID_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_TIME_ID_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_ID_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_SUM_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_SUM_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_SUM_AGG_PL_01_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_PL_01_SALES_FACT_1997;

    public static final Column COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997;

    public static final Column COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997;

    public static final Column COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997;
    public static final Column COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997;

    public static final Column COLUMN_TIME_ID_TIME_BY_DAY;
    public static final Column COLUMN_THE_DATE_TIME_BY_DAY;
    public static final Column COLUMN_THE_DAY_TIME_BY_DAY;
    public static final Column COLUMN_THE_MONTH_TIME_BY_DAY;
    public static final Column COLUMN_THE_YEAR_TIME_BY_DAY;
    public static final Column COLUMN_DAY_OF_MONTH_TIME_BY_DAY;
    public static final Column COLUMN_WEEK_OF_YEAR_TIME_BY_DAY;
    public static final Column COLUMN_MONTH_OF_YEAR_TIME_BY_DAY;
    public static final Column COLUMN_QUARTER_TIME_BY_DAY;
    public static final Column COLUMN_FISCAL_PERIOD_TIME_BY_DAY;

    // Store Ragged table columns
    public static final Column COLUMN_STORE_ID_STORE_RAGGED;
    public static final Column COLUMN_STORE_TYPE_STORE_RAGGED;
    public static final Column COLUMN_STORE_NAME_STORE_RAGGED;
    public static final Column COLUMN_STREET_ADDRESS_STORE_RAGGED;
    public static final Column COLUMN_STORE_STATE_STORE_RAGGED;
    public static final Column COLUMN_STORE_COUNTRY_STORE_RAGGED;
    public static final Column COLUMN_STORE_MANAGER_STORE_RAGGED;
    public static final Column COLUMN_STORE_CITY_STORE_RAGGED;
    public static final Column COLUMN_STORE_SQFT_STORE_RAGGED;
    public static final Column COLUMN_GROCERY_SQFT_STORE_RAGGED;
    public static final Column COLUMN_FROZEN_SQFT_STORE_RAGGED;
    public static final Column COLUMN_MEAT_SQFT_STORE_RAGGED;
    public static final Column COLUMN_COFFEE_BAR_STORE_RAGGED;
    public static final Column COLUMN_REGION_ID_STORE_RAGGED;

    public static final SqlStatement MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1;
    public static final SqlStatement MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2;
    public static final SqlStatement MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3;
    public static final SqlStatement MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4;
    public static final ExpressionColumn MEASURE_PROMOTION_SALES_COL;
    public static final SqlStatement MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1;
    public static final SqlStatement MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2;
    public static final ExpressionColumn MEASURE_WAREHOUSE_PROFIT_COL;

    public static final SqlStatement NAME_COL_SQL_STATEMENT1;
    public static final SqlStatement NAME_COL_SQL_STATEMENT2;
    public static final SqlStatement NAME_COL_SQL_STATEMENT3;
    public static final SqlStatement NAME_COL_SQL_STATEMENT4;
    public static final SqlStatement NAME_COL_SQL_STATEMENT5;
    public static final SqlStatement NAME_COL_SQL_STATEMENT6;
    public static final SqlStatement NAME_COL_SQL_STATEMENT7;
    public static final ExpressionColumn SQL_EXPRESSION_COLUMN_NAME;

    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT1;
    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT2;
    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT3;
    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT4;
    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT5;
    public static final SqlStatement NAME_ORDER_COL_SQL_STATEMENT6;
    public static final ExpressionColumn SQL_EXPRESSION_COLUMN_NAME_ORDER;

    // Static tables
    public static final Table TABLE_SALES_FACT;
    public static final Table TABLE_SALES_FACT1998;
    public static final Table TABLE_TIME;
    public static final Table TABLE_STORE;
    public static final Table TABLE_CUSTOMER;
    public static final Table TABLE_PRODUCT;
    public static final Table TABLE_WAREHOUSE;
    public static final Table TABLE_INVENTORY_FACT;
    public static final Table TABLE_PROMOTION;
    public static final Table TABLE_EMPLOYEE;
    public static final Table TABLE_DEPARTMENT;
    public static final Table TABLE_POSITION;
    public static final Table TABLE_SALARY;
    public static final Table TABLE_EMPLOYEE_CLOSURE;
    public static final Table TABLE_PRODUCT_CLASS;
    public static final Table TABLE_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final Table TABLE_AGG_C_10_SALES_FACT_1997;
    public static final Table TABLE_AGG_L_05_SALES_FACT;
    public static final Table TABLE_AGG_L_03_SALES_FACT;
    public static final Table TABLE_AGG_G_MS_PCAT_SALES_FACT;
    public static final Table TABLE_AGG_C_14_SALES_FACT;
    public static final Table TABLE_AGG_PL_01_SALES_FACT;
    public static final Table TABLE_TIME_BY_DAY;
    public static final Table TABLE_STORE_RAGGED;

    public static final OrderedColumn ORDERED_COLUMN_POSITION_ID_EMPLOYEE;
    public static final OrderedColumn ORDERED_SQL_EXPRESSION_COLUMN_NAME_ORDER;

    // Static AggregationName
    public static final AggregationName AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997;

    // Static AggregationExclude
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997;

    // Static table queries
    //public static final TableSource QUERY_TIME;
    // field assignment only: QUERY_STORE
    // field assignment only: QUERY_CUSTOMER
    // field assignment only: QUERY_PRODUCT
    // field assignment only: QUERY_SALES_FACT
    // field assignment only: QUERY_WAREHOUSE
    // field assignment only: QUERY_INVENTORY_FACT
    // field assignment only: QUERY_PROMOTION
    // field assignment only: QUERY_EMPLOYEE
    // field assignment only: QUERY_DEPARTMENT
    // field assignment only: QUERY_POSITION
    // field assignment only: QUERY_SALARY
    // field assignment only: QUERY_EMPLOYEE_CLOSURE
    // field assignment only: QUERY_PRODUCT_CLASS
    // field assignment only: QUERY_TIME_BY_DAY
    // field assignment only: QUERY_STORE_RAGGED

    public static final JoinedQueryElement JOIN_PRODUCT_PRODUCT_CLASS_LEFT;
    public static final JoinedQueryElement JOIN_PRODUCT_PRODUCT_CLASS_RIGHT;
    public static final JoinSource JOIN_PRODUCT_PRODUCT_CLASS;

    public static final JoinedQueryElement JOIN_EMPLOYEE_POSITION_LEFT;
    public static final JoinedQueryElement JOIN_EMPLOYEE_POSITION_RIGHT;
    public static final JoinSource JOIN_EMPLOYEE_POSITION;

    public static final JoinedQueryElement JOIN_EMPLOYEE_STORE_LEFT;
    public static final JoinedQueryElement JOIN_EMPLOYEE_STORE_RIGHT;
    public static final JoinSource JOIN_EMPLOYEE_STORE;

    // Static levels
    // field assignment only: LEVEL_YEAR
    // field assignment only: LEVEL_QUARTER
    // field assignment only: LEVEL_MONTH
    // field assignment only: LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR
    // field assignment only: LEVEL_WEEK
    // field assignment only: LEVEL_DAY
    // field assignment only: LEVEL_STORE_COUNTRY
    // field assignment only: LEVEL_STORE_STATE
    // field assignment only: LEVEL_STORE_CITY
    // field assignment only: LEVEL_STORE_NAME
    // field assignment only: LEVEL_STORE_HAS_COFFEE_BAR
    // field assignment only: LEVEL_CUSTOMER_COUNTRY
    // field assignment only: LEVEL_CUSTOMER_STATE
    // field assignment only: LEVEL_CUSTOMER_CITY
    // field assignment only: LEVEL_CUSTOMER_NAME
    // field assignment only: LEVEL_CUSTOMER_GENDER
    // field assignment only: LEVEL_PRODUCT_FAMILY
    public static final Level LEVEL_PRODUCT_DEPARTMENT;
    // field assignment only: LEVEL_PRODUCT_CATEGORY
    // field assignment only: LEVEL_PRODUCT_SUBCATEGORY
    // field assignment only: LEVEL_PRODUCT_BRAND
    // field assignment only: LEVEL_PRODUCT_NAME
    // field assignment only: LEVEL_PROMOTION_NAME
    // field assignment only: LEVEL_STORE_SQFT
    // field assignment only: LEVEL_STORE_TYPE_WITHOUT_TABLE
    // field assignment only: LEVEL_EDUCATION
    // field assignment only: LEVEL_GENDER
    // field assignment only: LEVEL_MARITAL_STATUS
    // field assignment only: LEVEL_YEARLY_INCOME
    // field assignment only: LEVEL_PAY_TYPE
    // field assignment only: LEVEL_STORE_TYPE
    // field assignment only: LEVEL_STORE_COUNTRY_WITH_NEVER
    // field assignment only: LEVEL_STORE_CYTY_IF_PARENTS_NAME
    // field assignment only: LEVEL_STORE_CYTY_IF_BLANK_NAME
    // field assignment only: LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER

    // field assignment only: LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY
    // field assignment only: LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE
    // field assignment only: LEVEL_CITY_TABLE_COLUMN_CITY
    public static final Level LEVEL_NAME;

    // Warehouse dimension levels
    // field assignment only: LEVEL_WAREHOUSE_COUNTRY
    // field assignment only: LEVEL_WAREHOUSE_STATE
    // field assignment only: LEVEL_WAREHOUSE_CITY
    // field assignment only: LEVEL_WAREHOUSE_NAME

    // Promotion dimension levels
    // field assignment only: LEVEL_PROMOTION_MEDIA

    // Employee dimension levels
    // field assignment only: LEVEL_EMPLOYEE_MANAGEMENT_ROLE
    // field assignment only: LEVEL_EMPLOYEE_POSITION
    // field assignment only: LEVEL_EMPLOYEE_DEPARTMENT
    // field assignment only: LEVEL_EMPLOYEE_FULL_NAME

    // Department dimension levels
    // field assignment only: LEVEL_DEPARTMENT_DESCRIPTION

    // Position dimension levels
    // field assignment only: LEVEL_POSITION_TITLE
    // field assignment only: LEVEL_MANAGEMENT_ROLE
    // field assignment only: LEVEL_HR_POSITION_TITLE

    // Enhanced Customer levels
    // field assignment only: LEVEL_CUSTOMER_EDUCATION
    // field assignment only: LEVEL_CUSTOMER_MARITAL_STATUS

    // field assignment only: LEVEL_EMPLOYEE_ID

    // field assignment only: LEVEL_COUNTRY_WITH_NEVER
    // field assignment only: LEVEL_STATE
    // field assignment only: LEVEL_CITY_TABLE_COLUMN_STORE_CITY

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_TIME;
    public static final ExplicitHierarchy HIERARCHY_HR_TIME;
    public static final ExplicitHierarchy HIERARCHY_TIME2;
    public static final ExplicitHierarchy HIERARCHY_STORE;
    public static final ExplicitHierarchy HIERARCHY_STORE_SALES_RAGGED;
    public static final ExplicitHierarchy HIERARCHY_HR_STORE;
    // field assignment only: HIERARCHY_PAY_TYPE
    // field assignment only: HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE
    public static final ExplicitHierarchy HIERARCHY_CUSTOMER;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GEO;
    // field assignment only: HIERARCHY_CUSTOMERS_GENDER
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;
    // field assignment only: HIERARCHY_STORE_SIZE_IN_SQFT
    // field assignment only: HIERARCHY_PROMOTIONS
    // field assignment only: HIERARCHY_STORE_TYPE
    // field assignment only: HIERARCHY_STORE_TYPE_WITHOUT_TABLE
    // field assignment only: HIERARCHY_EDUCATION_LEVEL
    // field assignment only: HIERARCHY_GENDER
    // field assignment only: HIERARCHY_MARITAL_STATUS
    // field assignment only: HIERARCHY_YEARLY_INCOME
    // field assignment only: HIERARCHY_STORE_HAS_COFFEE_BAR
    public static final ExplicitHierarchy HIERARCHY_GEOGRAPHY;

    // Missing hierarchies
    public static final ExplicitHierarchy HIERARCHY_WAREHOUSE;
    // field assignment only: HIERARCHY_PROMOTION_MEDIA
    // field assignment only: HIERARCHY_EMPLOYEE
    // field assignment only: HIERARCHY_DEPARTMENT
    // field assignment only: HIERARCHY_POSITION
    // field assignment only: HIERARCHY_HR_POSITION
    // field assignment only: HIERARCHY_CUSTOMERS_EDUCATION
    // field assignment only: HIERARCHY_CUSTOMERS_MARITAL

    // Static dimensions
    // field assignment only: DIMENSION_TIME
    // field assignment only: DIMENSION_HR_TIME
    // field assignment only: DIMENSION_STORE
    // field assignment only: DIMENSION_STORE_SALES_RAGGED
    // field assignment only: DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE
    // field assignment only: DIMENSION_PAY_TYPE
    // field assignment only: DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE
    // field assignment only: DIMENSION_CUSTOMERS
    // field assignment only: DIMENSION_PRODUCT
    // field assignment only: DIMENSION_STORE_SIZE_IN_SQFT
    // field assignment only: DIMENSION_PROMOTIONS
    // field assignment only: DIMENSION_STORE_TYPE_WITH_QUERY_STORE
    // field assignment only: DIMENSION_STORE_TYPE_WITHOUT_QUERY
    // field assignment only: DIMENSION_EDUCATION_LEVEL
    // field assignment only: DIMENSION_GENDER
    // field assignment only: DIMENSION_MARITAL_STATUS
    // field assignment only: DIMENSION_YEARLY_INCOME
    // field assignment only: DIMENSION_STORE_HAS_COFFEE_BAR
    // field assignment only: DIMENSION_GEOGRAPHY
    // field assignment only: DIMENSION_STORE_TYPE


    // Missing dimensions
    // field assignment only: DIMENSION_WAREHOUSE
    // field assignment only: DIMENSION_PROMOTION_MEDIA
    // field assignment only: DIMENSION_EMPLOYEE
    // field assignment only: DIMENSION_DEPARTMENT
    // field assignment only: DIMENSION_POSITION
    // field assignment only: DIMENSION_HR_POSITION

    // Static measures
    // field assignment only: MEASURE_UNIT_SALES
    // field assignment only: MEASURE_STORE_SALES
    // field assignment only: MEASURE_STORE_COST
    // field assignment only: MEASURE_SALES_COUNT
    // field assignment only: MEASURE_CUSTOMER_COUNT
    // field assignment only: MEASURE_PROMOTION_SALES

    // Warehouse measures
    // field assignment only: MEASURE_WAREHOUSE_SALES
    // field assignment only: MEASURE_WAREHOUSE_STORE_INVOICE
    // field assignment only: MEASURE_WAREHOUSE_SUPPLY_TIME
    // field assignment only: MEASURE_WAREHOUSE_PROFIT
    // field assignment only: MEASURE_WAREHOUSE_COST
    // field assignment only: MEASURE_UNITS_SHIPPED
    // field assignment only: MEASURE_UNITS_ORDERED

    // Store measures
    // field assignment only: MEASURE_STORE_SQFT
    // field assignment only: MEASURE_GROCERY_SQFT

    // HR measures
    // field assignment only: MEASURE_ORG_SALARY
    // field assignment only: MEASURE_COUNT
    // field assignment only: MEASURE_NUMBER_OF_EMPLOYEES

    // field assignment only: MEASURE_UNIT_SALES_RAGGED
    // field assignment only: MEASURE_STORE_COST_RAGGED
    // field assignment only: MEASURE_STORE_SALES_RAGGED
    // field assignment only: MEASURE_SALES_COUNT_RAGGED
    // field assignment only: MEASURE_CUSTOMER_COUNT_RAGGED

    // field assignment only: MEASURE_SALES_COUNT_WITH_PROPERTY
    // field assignment only: MEASURE_UNIT_SALES_MEMBER_ORDINAL
    // field assignment only: MEASURE_STORE_SALES_WITH_PROPERTY
    // field assignment only: MEASURE_STORE_COST_WITH_PROPERTY
    // field assignment only: MEASURE_CUSTOMER_COUNT_WITH_PROPERTY

    // Static measure groups
    public static final MeasureGroup MEASUREGROUP_SALES;
    public static final MeasureGroup MEASUREGROUP_WAREHOUSE;
    public static final MeasureGroup MEASUREGROUP_STORE;
    public static final MeasureGroup MEASUREGROUP_HR;
    public static final MeasureGroup MEASUREGROUP_RAGGED;
    public static final MeasureGroup MEASUREGROUP_SALES2;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_STORE;
    public static final DimensionConnector CONNECTOR_STORE_SIZE_IN_SOFT;
    public static final DimensionConnector CONNECTOR_STORE_TYPE;
    public static final DimensionConnector CONNECTOR_TIME;
    public static final DimensionConnector CONNECTOR_PRODUCT;
    public static final DimensionConnector CONNECTOR_PROMOTION_MEDIA;
    public static final DimensionConnector CONNECTOR_PROMOTIONS;
    public static final DimensionConnector CONNECTOR_CUSTOMER;
    public static final DimensionConnector CONNECTOR_EDUCATION_LEVEL;
    public static final DimensionConnector CONNECTOR_GENDER;
    public static final DimensionConnector CONNECTOR_MARITAL_STATUS;
    public static final DimensionConnector CONNECTOR_YEARLY_INCOME;


    // Additional connectors for warehouse cube
    public static final DimensionConnector CONNECTOR_WAREHOUSE_TIME;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_STORE;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_STORE_TYPE;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_PRODUCT;
    public static final DimensionConnector CONNECTOR_WAREHOUSE_WAREHOUSE;

    // Additional connectors for HR cube
    public static final DimensionConnector CONNECTOR_HR_TIME;
    public static final DimensionConnector CONNECTOR_HR_STORE;
    public static final DimensionConnector CONNECTOR_HR_PAY_TYPE;
    public static final DimensionConnector CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE;
    public static final DimensionConnector CONNECTOR_HR_EMPLOYEE;
    public static final DimensionConnector CONNECTOR_HR_POSITION;
    public static final DimensionConnector CONNECTOR_HR_DEPARTMENT;

    // Additional connectors for Store cube
    public static final DimensionConnector CONNECTOR_STORE_STORE_TYPE;
    public static final DimensionConnector CONNECTOR_STORE_STORE;
    public static final DimensionConnector CONNECTOR_STORE_HAS_COFFEE_BAR;

    // Additional connectors for Regged store
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_STORE;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_GEOGRAPHY;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_STORE_SIZE;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_STORE_TYPE;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_TIME;
    public static final DimensionConnector CONNECTOR_SALES_2_TIME;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_PRODUCT;
    public static final DimensionConnector CONNECTOR_SALES_2_PRODUCT;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_PROMOTIONS;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_CUSTOMERS;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_GENDER;
    public static final DimensionConnector CONNECTOR_SALES_2_GENDER;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_MARITAL_STATUS;
    public static final DimensionConnector CONNECTOR_SALES_RAGGED_YEARLY_INCOME;


    // Static calculated members
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_LAST_PERIOD;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_GROWTH;
    public static final CalculatedMember CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE;
    public static final CalculatedMember CALCULATED_MEMBER_EMPLOYEE_SALARY;
    public static final CalculatedMember CALCULATED_MEMBER_AVG_SALARY;
    public static final CalculatedMember PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_WITH_ORDER;
    public static final CalculatedMember CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2;

    // Static access grants
    public static final AccessDatabaseSchemaGrant GRANT_DATABASE_SCHEMA_ADMIN;
    public static final AccessDatabaseSchemaGrant GRANT_DATABASE_SCHEMA_HR;
    public static final AccessDatabaseSchemaGrant GRANT_DATABASE_SCHEMA_MANAGER;

    public static final AccessCatalogGrant GRANT_ADMIN_CATALOG;
    public static final AccessCatalogGrant GRANT_NO_HR_CATALOG;
    public static final AccessCatalogGrant GRANT_CALIFORNIA_MANAGER_CATALOG;
    public static final AccessCubeGrant GRANT_NO_HR_CUBE;
    public static final AccessCubeGrant GRANT_CALIFORNIA_MANAGER_SALES_CUBE;

    public static final AccessHierarchyGrant GRANT_HIERARCHY_STORE;
    public static final AccessHierarchyGrant GRANT_HIERARCHY_CUSTOMERS;
    public static final AccessHierarchyGrant GRANT_HIERARCHY_GENDER;

    public static final AccessMemberGrant MEMBER_GRANT_STORE1;
    public static final AccessMemberGrant MEMBER_GRANT_STORE2;
    public static final AccessMemberGrant MEMBER_GRANT_CUSTOMERS1;
    public static final AccessMemberGrant MEMBER_GRANT_CUSTOMERS2;

    // Static access roles
    // field assignment only: ROLE_CALIFORNIA_MANAGER
    // field assignment only: ROLE_NO_HR_CUBE
    // field assignment only: ROLE_ADMINISTRATOR

    // Static cubes
    // field assignment only: CUBE_SALES
    // field assignment only: CUBE_WAREHOUSE
    // field assignment only: CUBE_STORE
    // field assignment only: CUBE_HR
    // field assignment only: CUBE_SALES_RAGGED
    // field assignment only: CUBE_SALES_2
    // field assignment only: CUBE_VIRTIAL_WAREHOUSE_AND_SALES

    // Static database schema and catalog
    // field assignment only: DATABASE_SCHEMA_FOODMART
    // field assignment only: CATALOG_FOODMART

    // Static annotation
    public static final Annotation ANNOTATION_SALES1;
    public static final Annotation ANNOTATION_SALES2;
    public static final Annotation ANNOTATION_SALES3;
    public static final Annotation ANNOTATION_SALES4;
    public static final Annotation ANNOTATION_SALES5;

    public static final AggregationColumnName AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997;

    public static final AggregationForeignKey AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationForeignKey AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationForeignKey AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationForeignKey AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997;

    public static final AggregationMeasure AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationMeasure AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationMeasure AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997;

    public static final AggregationLevel AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationLevel AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationLevel AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997;

    public static final MemberProperty LEVEL_NAME_PROP1;
    public static final MemberProperty LEVEL_NAME_PROP2;
    public static final MemberProperty LEVEL_NAME_PROP3;
    public static final MemberProperty LEVEL_NAME_PROP4;

    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7;
    public static final MemberProperty LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8;

    public static final MemberProperty LEVEL_STORE_PROP1;
    public static final MemberProperty LEVEL_STORE_PROP2;
    public static final MemberProperty LEVEL_STORE_PROP3;
    public static final MemberProperty LEVEL_STORE_PROP4;
    public static final MemberProperty LEVEL_STORE_PROP5;
    public static final MemberProperty LEVEL_STORE_PROP6;
    public static final MemberProperty LEVEL_STORE_PROP7;
    public static final MemberProperty LEVEL_STORE_PROP8;

    public static final MemberProperty LEVEL_EMPLOYEE_PROP1;
    public static final MemberProperty LEVEL_EMPLOYEE_PROP2;
    public static final MemberProperty LEVEL_EMPLOYEE_PROP3;
    public static final MemberProperty LEVEL_EMPLOYEE_PROP4;
    public static final MemberProperty LEVEL_EMPLOYEE_PROP5;
    public static final MemberProperty LEVEL_EMPLOYEE_PROP6;

    public static final CalculatedMemberProperty MEASURE_SALES_COUNT_PROP;
    public static final CalculatedMemberProperty MEASURE_UNIT_SALES_PROP;
    public static final CalculatedMemberProperty MEASURE_STORE_SALES_PROP;
    public static final CalculatedMemberProperty MEASURE_STORE_COST_PROP;
    public static final CalculatedMemberProperty MEASURE_CUSTOMER_COUNT_PROP;

    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROP0;
    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROP1;
    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROP2;
    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROP3;
    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROFIT_GROWTH_PROP0;

    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP0;
    public static final CalculatedMemberProperty CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP1;

    public static final ParentChildLink HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK;

    public static final CalculatedMemberProperty PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE;

    public static final NamedSet NAMED_SET_TOP_SELLERS;

    private static final String foodMartBody = """
            The FoodMart database is a classic example of a data warehouse schema used for demonstrating OLAP and business intelligence concepts.
            It contains sales data for a fictional food retail chain with multiple stores, products, customers, and time periods.
            """;
    private static final String databaseSchemaBody = """
            FoodMart is a sample database representing a classic sales company.
            It contains order data with product information, customer details, and time-based sales transactions
            for analyzing business performance across different markets and product lines.
            """;

    private static final String querySalesBody = """
            SalesFactQuery it directly references the physical table `sales_fact_1997`.
            """;

    private static final String queryIventoryFactBody = """
            InventoryFactQuery it directly references the physical table `inventory_fact_1997`.
            """;

    private static final String queryStoreBody = """
            StoreQuery it directly references the physical table `store`.
            """;
    private static final String queryCustomerBody = """
            CustomerQuery it directly references the physical table `customer`.
            """;
    private static final String queryProductBody = """
            ProductQuery it directly references the physical table `product`.
            """;
    private static final String queryWarehouseBody = """
            WarehouseQuery it directly references the physical table `warehouse`.
            """;
    private static final String queryPromotionBody = """
            PromotionQuery it directly references the physical table `promotion`.
            """;
    private static final String queryEmployeeBody = """
            EmployeeQuery it directly references the physical table `employee`.
            """;
    private static final String queryDepartamentBody = """
            DepartmentQuery it directly references the physical table `department`.
            """;
    private static final String queryPositionBody = """
            PositionQuery it directly references the physical table `position`.
            """;
    private static final String querySalaryBody = """
            SalaryQuery it directly references the physical table `salary`.
            """;
    private static final String queryEmployeeClosureBody = """
            EmployeeClosureQuery it directly references the physical table `employee_closure`.
            """;
    private static final String queryProductClassBody = """
            ProductClassQuery it directly references the physical table `product_class`.
            """;
    private static final String queryTimeByDayBody = """
            TimeByDayQuery it directly references the physical table `time_by_day`.
            """;
    private static final String queryStoreRaggedBody = """
            StoreRaggedQuery it directly references the physical table `store_ragged`.
            """;

    private static final String salesCubeBody = """
            The Sales cube is the sales_fact_1997 table containing detailed sales transactions.
            It includes measures for Unit Sales, Store Sales and Store Cost, Sales Count, Promotion Sales along with dimensions for Stores,
            Store Size, Store Type, Time, Products, Promotion Media, Promotions, Customers, Education Level, Gender, Marital Status, Yearly Income.
            """;

    private static final String warehouseCubeBody = """
    The Warehouse cube is the inventory_fact_1997 table containing detailed warehouse sales transactions.
    It includes measures for Store Invoice, Supply Time, Warehouse Cost, Warehouse Sales, Units Shipped, Units Ordered and Warehouse Profit,
    along with dimensions for Store, Store Size in SQFT, Store Type, Time, Product and Warehouse.
    """;

    private static final String storeCubeBody = """
            The Store cube is the store table containing detailed store information.
            It includes measures for Store Sqft and Grocery Sqft, along with dimensions for Store Type, Store and Has coffee bar.
            """;

    private static final String hrCubeBody = """
            The HR cube is the salary table containing detailed employees information.
            It includes measures for Org Salary, Count and Number of Employees, along with dimensions for Time, Store, Pay Type, Store Type,
            Position, Departament and Employees.
            """;

    private static final String salesRaggedCubeBody = """
            The Sales Ragged cube is the sales_fact_1997 table containing detailed sales transactions.
            It includes measures for Unit Sales, Store Cost, Store Sales, Store Count, CustomerStore Count along with dimensions for Store, Geography, Store Size,
            Store Type, and Time, Product, Promotion Media, Promotions, Customers, Education Level, Gender, Status, Yearly Income.
            """;

    private static final String sales2CubeBody = """
            The Sales cube is the sales_fact_1997 table containing detailed sales transactions.
            It includes measures for Sales Count, Unit Sales, Store Sales, Store Cost, and Customer Count, along with dimensions for Time, Products, and Gender.
            """;

    private static final String warehouseSalesCubeBody = """
            The Warehouse and Sales virual cube detailed sales transactions. This Cube unions Warehouse and Sales cubes.
            It includes measures for Sales Count, Store Cost, Store Sales, Unit Sales, Store Invoice,
            Supply Time, Units Ordered, Units Shipped, Warehouse Cost, Warehouse Profit, Warehouse Sales with dimensions for Customers, Education Level,
            Gender, Material Status, Products, Promotion Media, Promotions, Stores, Time, Yearly Income, Warehouse.
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

    private static final String payTypeBody = """
            The Pay Type dimension show pay type into a hierarchy of pay type.
            """;

    private static final String storeTypeBody = """
            The Store Type dimension show store type into a hierarchy of store type.
            """;

    private static final String storeSizeBody = """
            The Store Size dimension show store size into a hierarchy of store size.
            """;

    private static final String promotionsBody = """
            The Promotions dimension show promotions into a promotions hierarchy.
            """;

    private static final String educationLevelBody = """
            The Education Level show education level of customer.
            """;

    private static final String genderBody = """
            The Gender dimension  show gender type of customer.
            """;

    private static final String maritalStatusBody = """
            The Marital Status dimension show marital status of customer.
            """;

    private static final String yearlyIncomeBody = """
            The YearlyIncome dimension show yearly income of customer.
            """;

    private static final String hasCoffeeBarBody = """
            The HasCoffeeBar dimension show coffee bar on store.
            """;

    private static final String geographyBody = """
            The Geography dimension show geography on store (city, state, country).
            """;

    private static final String warehouseBody = """
            The Warehouse dimension show information of warehouse (city, state, country, name).
            """;

    private static final String promotionMediaBody = """
            The Promotion Media dimension show promotion media type into a promotion media hierarchy.
            """;

    private static final String employeeBody = """
            The Employee dimension show employees structure into a employee hierarchy.
            """;

    private static final String departmentBody = """
            The Department dimension show department name into a department hierarchy.
            """;

    private static final String positionBody = """
            The Position dimension show employees role and position into a position hierarchy.
            """;
    private static final String roleAdministratorBody = """
            The `Administrator` use CatalogGrant access all;
            """;
    private static final String roleNoHRCubeBody = """
            The `No HR Cube` use CatalogGrant access all except Cube HR. CubeGrant has HR cube access none;
            """;
    private static final String roleCaliforniaManagerBody = """
            The `California manager` use CatalogGrant access none. CatalogGrant has Sales cube access All
            with HierarchyGrant Store and Customers access custom with member grants of caligornia
            with HierarchyGrant Gender access none
            """;

    static {
        // Initialize columns
        //product_id,time_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales
        //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4)

        COLUMN_TIME_ID_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_SALESFACT.setName("time_id");
        COLUMN_TIME_ID_SALESFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_SALESFACT.setName("store_id");
        COLUMN_STORE_ID_SALESFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_SALESFACT.setName("customer_id");
        COLUMN_CUSTOMER_ID_SALESFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_ID_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_SALESFACT.setName("promotion_id");
        COLUMN_PROMOTION_ID_SALESFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_ID_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_SALESFACT.setName("product_id");
        COLUMN_PRODUCT_ID_SALESFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_UNIT_SALES_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_SALESFACT.setName("unit_sales");
        COLUMN_UNIT_SALES_SALESFACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_SALES_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_SALESFACT.setName("store_sales");
        COLUMN_STORE_SALES_SALESFACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_SALESFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_SALESFACT.setName("store_cost");
        COLUMN_STORE_COST_SALESFACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_PRODUCT_ID_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_SALESFACT1998.setName("product_id");
        COLUMN_PRODUCT_ID_SALESFACT1998.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TIME_ID_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_SALESFACT1998.setName("time_id");
        COLUMN_TIME_ID_SALESFACT1998.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_SALESFACT1998.setName("customer_id");
        COLUMN_CUSTOMER_ID_SALESFACT1998.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_ID_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_SALESFACT1998.setName("promotion_id");
        COLUMN_PROMOTION_ID_SALESFACT1998.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_SALESFACT1998.setName("store_id");
        COLUMN_STORE_ID_SALESFACT1998.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_SALES_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_SALESFACT1998.setName("store_sales");
        COLUMN_STORE_SALES_SALESFACT1998.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_SALESFACT1998.setName("store_cost");
        COLUMN_STORE_COST_SALESFACT1998.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_SALESFACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_SALESFACT1998.setName("unit_sales");
        COLUMN_UNIT_SALES_SALESFACT1998.setType(SqlSimpleTypes.decimalType(18, 4));

        // Time table columns
        COLUMN_TIME_ID_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_TIME.setName("time_id");
        COLUMN_TIME_ID_TIME.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_THE_DATE_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_DATE_TIME.setName("the_date");
        COLUMN_THE_DATE_TIME.setType(SqlSimpleTypes.Sql99.dateType());

        COLUMN_THE_YEAR_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_YEAR_TIME.setName("the_year");
        COLUMN_THE_YEAR_TIME.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_QUARTER_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUARTER_TIME.setName("quarter");
        COLUMN_QUARTER_TIME.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_MONTH_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_MONTH_TIME.setName("the_month");
        COLUMN_THE_MONTH_TIME.setType(SqlSimpleTypes.Sql99.varcharType());

        // Store table columns
        COLUMN_STORE_ID_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_STORE.setName("store_id");
        COLUMN_STORE_ID_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_NAME_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_NAME_STORE.setName("store_name");
        COLUMN_STORE_NAME_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_COUNTRY_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COUNTRY_STORE.setName("store_country");
        COLUMN_STORE_COUNTRY_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_STATE_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_STATE_STORE.setName("store_state");
        COLUMN_STORE_STATE_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_CITY_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_CITY_STORE.setName("store_city");
        COLUMN_STORE_CITY_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        // Customer table columns
        COLUMN_CUSTOMER_ID_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_CUSTOMER.setName("customer_id");
        COLUMN_CUSTOMER_ID_CUSTOMER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FULLNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FULLNAME_CUSTOMER.setName("fullname");
        COLUMN_FULLNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_GENDER_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GENDER_CUSTOMER.setName("gender");
        COLUMN_GENDER_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_COUNTRY_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_CUSTOMER.setName("country");
        COLUMN_COUNTRY_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STATE_PROVINCE_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STATE_PROVINCE_CUSTOMER.setName("state_province");
        COLUMN_STATE_PROVINCE_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CITY_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CITY_CUSTOMER.setName("city");
        COLUMN_CITY_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_LNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_LNAME_CUSTOMER.setName("lname");
        COLUMN_LNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_FNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FNAME_CUSTOMER.setName("fname");
        COLUMN_FNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ACCOUNT_NUM_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ACCOUNT_NUM_CUSTOMER.setName("account_num");
        COLUMN_ACCOUNT_NUM_CUSTOMER.setType(SqlSimpleTypes.bigintType());

        COLUMN_CUSTOMER_REGION_ID_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_REGION_ID_CUSTOMER.setName("customer_region_id");
        COLUMN_CUSTOMER_REGION_ID_CUSTOMER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_NUM_CARS_OWNED_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_NUM_CARS_OWNED_CUSTOMER.setName("num_cars_owned");
        COLUMN_NUM_CARS_OWNED_CUSTOMER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TOTAL_CHILDREN_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setName("total_children");
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_ADDRESS2_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ADDRESS2_CUSTOMER.setName("address2");
        COLUMN_ADDRESS2_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        // Product table columns
        COLUMN_PRODUCT_CLASS_ID_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_CLASS_ID_PRODUCT.setName("product_class_id");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_ID_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_PRODUCT.setName("product_id");
        COLUMN_PRODUCT_ID_PRODUCT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_NAME_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_NAME_PRODUCT.setName("product_name");
        COLUMN_PRODUCT_NAME_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_FAMILY_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_FAMILY_PRODUCT.setName("product_family");
        COLUMN_PRODUCT_FAMILY_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_DEPARTMENT_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_CATEGORY_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_CATEGORY_PRODUCT.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT.setName("product_subcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_BRAND_NAME_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_BRAND_NAME_PRODUCT.setName("brand_name");
        COLUMN_BRAND_NAME_PRODUCT.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize warehouse columns
        COLUMN_WAREHOUSE_ID_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_ID_WAREHOUSE.setName("warehouse_id");
        COLUMN_WAREHOUSE_ID_WAREHOUSE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_WAREHOUSE_NAME_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_NAME_WAREHOUSE.setName("warehouse_name");
        COLUMN_WAREHOUSE_NAME_WAREHOUSE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_WAREHOUSE_CITY_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_CITY_WAREHOUSE.setName("warehouse_city");
        COLUMN_WAREHOUSE_CITY_WAREHOUSE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE.setName("warehouse_state_province");
        COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE.setName("warehouse_country");
        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORES_ID_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORES_ID_WAREHOUSE.setName("stores_id");
        COLUMN_STORES_ID_WAREHOUSE.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize inventory fact columns
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setName("warehouse_id");
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_INVENTORY_FACT.setName("store_id");
        COLUMN_STORE_ID_INVENTORY_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_ID_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_INVENTORY_FACT.setName("product_id");
        COLUMN_PRODUCT_ID_INVENTORY_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TIME_ID_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_INVENTORY_FACT.setName("time_id");
        COLUMN_TIME_ID_INVENTORY_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_INVOICE_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_INVOICE_INVENTORY_FACT.setName("store_invoice");
        COLUMN_STORE_INVOICE_INVENTORY_FACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_SUPPLY_TIME_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SUPPLY_TIME_INVENTORY_FACT.setName("supply_time");
        COLUMN_SUPPLY_TIME_INVENTORY_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_WAREHOUSE_COST_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_COST_INVENTORY_FACT.setName("warehouse_cost");
        COLUMN_WAREHOUSE_COST_INVENTORY_FACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT.setName("warehouse_sales");
        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_UNITS_SHIPPED_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNITS_SHIPPED_INVENTORY_FACT.setName("units_shipped");
        COLUMN_UNITS_SHIPPED_INVENTORY_FACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_UNITS_ORDERED_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNITS_ORDERED_INVENTORY_FACT.setName("units_ordered");
        COLUMN_UNITS_ORDERED_INVENTORY_FACT.setType(SqlSimpleTypes.numericType(18, 4));

        // Initialize promotion columns
        COLUMN_PROMOTION_ID_PROMOTION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_PROMOTION.setName("promotion_id");
        COLUMN_PROMOTION_ID_PROMOTION.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_NAME_PROMOTION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_NAME_PROMOTION.setName("promotion_name");
        COLUMN_PROMOTION_NAME_PROMOTION.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MEDIA_TYPE_PROMOTION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MEDIA_TYPE_PROMOTION.setName("media_type");
        COLUMN_MEDIA_TYPE_PROMOTION.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize employee columns
        COLUMN_EMPLOYEE_ID_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EMPLOYEE_ID_EMPLOYEE.setName("employee_id");
        COLUMN_EMPLOYEE_ID_EMPLOYEE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FIRST_NAME_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FIRST_NAME_EMPLOYEE.setName("first_name");
        COLUMN_FIRST_NAME_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_LAST_NAME_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_LAST_NAME_EMPLOYEE.setName("last_name");
        COLUMN_LAST_NAME_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_FULL_NAME_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FULL_NAME_EMPLOYEE.setName("full_name");
        COLUMN_FULL_NAME_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MANAGEMENT_ROLE_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MANAGEMENT_ROLE_EMPLOYEE.setName("management_role");
        COLUMN_MANAGEMENT_ROLE_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_POSITION_ID_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSITION_ID_EMPLOYEE.setName("position_id");
        COLUMN_POSITION_ID_EMPLOYEE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_POSITION_TITLE_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSITION_TITLE_EMPLOYEE.setName("position_title");
        COLUMN_POSITION_TITLE_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_ID_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_EMPLOYEE.setName("store_id");
        COLUMN_STORE_ID_EMPLOYEE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_SUPERVISOR_ID_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SUPERVISOR_ID_EMPLOYEE.setName("supervisor_id");
        COLUMN_SUPERVISOR_ID_EMPLOYEE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MARITAL_STATUS_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MARITAL_STATUS_EMPLOYEE.setName("marital_status");
        COLUMN_MARITAL_STATUS_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_GENDER_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GENDER_EMPLOYEE.setName("gender");
        COLUMN_GENDER_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SALARY_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALARY_EMPLOYEE.setName("salary");
        COLUMN_SALARY_EMPLOYEE.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_EDUCATION_LEVEL_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EDUCATION_LEVEL_EMPLOYEE.setName("education_level");
        COLUMN_EDUCATION_LEVEL_EMPLOYEE.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize department columns
        COLUMN_DEPARTMENT_ID_DEPARTMENT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEPARTMENT_ID_DEPARTMENT.setName("department_id");
        COLUMN_DEPARTMENT_ID_DEPARTMENT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT.setName("department_description");
        COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize position columns
        COLUMN_POSITION_ID_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSITION_ID_POSITION.setName("position_id");
        COLUMN_POSITION_ID_POSITION.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_POSITION_TITLE_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSITION_TITLE_POSITION.setName("position_title");
        COLUMN_POSITION_TITLE_POSITION.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PAY_TYPE_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PAY_TYPE_POSITION.setName("pay_type");
        COLUMN_PAY_TYPE_POSITION.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MIN_SCALE_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MIN_SCALE_POSITION.setName("min_scale");
        COLUMN_MIN_SCALE_POSITION.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_MAX_SCALE_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MAX_SCALE_POSITION.setName("max_scale");
        COLUMN_MAX_SCALE_POSITION.setType(SqlSimpleTypes.numericType(18, 4));

        // Initialize salary columns
        COLUMN_EMPLOYEE_ID_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EMPLOYEE_ID_SALARY.setName("employee_id");
        COLUMN_EMPLOYEE_ID_SALARY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DEPARTMENT_ID_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEPARTMENT_ID_SALARY.setName("department_id");
        COLUMN_DEPARTMENT_ID_SALARY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CURRENCY_ID_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CURRENCY_ID_SALARY.setName("currency_id");
        COLUMN_CURRENCY_ID_SALARY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PAY_DATE_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PAY_DATE_SALARY.setName("pay_date");
        COLUMN_PAY_DATE_SALARY.setType(SqlSimpleTypes.Sql99.dateType());

        COLUMN_SALARY_PAID_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALARY_PAID_SALARY.setName("salary_paid");
        COLUMN_SALARY_PAID_SALARY.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_OVERTIME_PAID_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_OVERTIME_PAID_SALARY.setName("overtime_paid");
        COLUMN_OVERTIME_PAID_SALARY.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_VACATION_ACCRUED_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_VACATION_ACCRUED_SALARY.setName("vacation_accrued");
        COLUMN_VACATION_ACCRUED_SALARY.setType(SqlSimpleTypes.Sql99.realType());

        COLUMN_VACATION_USED_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_VACATION_USED_SALARY.setName("vacation_used");
        COLUMN_VACATION_USED_SALARY.setType(SqlSimpleTypes.Sql99.realType());

        // Initialize employee closure columns
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE.setName("supervisor_id");
        COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE.setName("employee_id");
        COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DISTANCE_EMPLOYEE_CLOSURE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DISTANCE_EMPLOYEE_CLOSURE.setName("distance");
        COLUMN_DISTANCE_EMPLOYEE_CLOSURE.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize product class columns
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS.setName("product_class_id");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS.setName("product_subcategory");
        COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS.setName("product_family");
        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize enhanced customer columns
        COLUMN_MARITAL_STATUS_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MARITAL_STATUS_CUSTOMER.setName("marital_status");
        COLUMN_MARITAL_STATUS_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_EDUCATION_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EDUCATION_CUSTOMER.setName("education");
        COLUMN_EDUCATION_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_YEARLY_INCOME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_YEARLY_INCOME_CUSTOMER.setName("yearly_income");
        COLUMN_YEARLY_INCOME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MEMBER_CARD_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MEMBER_CARD_CUSTOMER.setName("member_card");
        COLUMN_MEMBER_CARD_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_OCCUPATION_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_OCCUPATION_CUSTOMER.setName("occupation");
        COLUMN_OCCUPATION_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_HOUSEOWNER_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_HOUSEOWNER_CUSTOMER.setName("houseowner");
        COLUMN_HOUSEOWNER_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER.setName("num_children_at_home");
        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize enhanced store columns
        COLUMN_STORE_TYPE_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_TYPE_STORE.setName("store_type");
        COLUMN_STORE_TYPE_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_REGION_ID_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_REGION_ID_STORE.setName("region_id");
        COLUMN_REGION_ID_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_STREET_ADDRESS_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_STREET_ADDRESS_STORE.setName("store_street_address");
        COLUMN_STORE_STREET_ADDRESS_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_MANAGER_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_MANAGER_STORE.setName("store_manager");
        COLUMN_STORE_MANAGER_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_SQFT_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SQFT_STORE.setName("store_sqft");
        COLUMN_STORE_SQFT_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_GROCERY_SQFT_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GROCERY_SQFT_STORE.setName("grocery_sqft");
        COLUMN_GROCERY_SQFT_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FROZEN_SQFT_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FROZEN_SQFT_STORE.setName("frozen_sqft");
        COLUMN_FROZEN_SQFT_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MEAT_SQFT_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MEAT_SQFT_STORE.setName("meat_sqft");
        COLUMN_MEAT_SQFT_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_COFFEE_BAR_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COFFEE_BAR_STORE.setName("coffee_bar");
        COLUMN_COFFEE_BAR_STORE.setType(SqlSimpleTypes.Sql99.booleanType());

        COLUMN_STORE_POSTAL_CODE_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_POSTAL_CODE_STORE.setName("store_postal_code");
        COLUMN_STORE_POSTAL_CODE_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_NUMBER_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_NUMBER_STORE.setName("store_number");
        COLUMN_STORE_NUMBER_STORE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STREET_ADDRESS_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STREET_ADDRESS_STORE.setName("store_street_address");
        COLUMN_STREET_ADDRESS_STORE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_month");
        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_quarter");
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_year");
        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_sales_sum");
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_cost_sum");
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("unit_sales_sum");
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        //unit_sales,customer_count,fact_count
        //DECIMAL(10.4),INTEGER,INTEGER
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997.setName("customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        //store_sales,store_cost,unit_sales,fact_count
        //DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_ID_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_AGG_PL_01_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TIME_ID_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_AGG_PL_01_SALES_FACT_1997.setName("time_id");
        COLUMN_TIME_ID_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_AGG_PL_01_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_SALES_SUM_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_SUM_AGG_PL_01_SALES_FACT_1997.setName("store_sales_sum");
        COLUMN_STORE_SALES_SUM_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_SUM_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_SUM_AGG_PL_01_SALES_FACT_1997.setName("store_cost_sum");
        COLUMN_STORE_COST_SUM_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_SUM_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_SUM_AGG_PL_01_SALES_FACT_1997.setName("unit_sales_sum");
        COLUMN_UNIT_SALES_SUM_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FACT_COUNT_AGG_PL_01_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_PL_01_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_PL_01_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997.setName("time_id");
        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        //fact_count
        //INTEGER
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setName("gender");
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setName("marital_status");
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_family");
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        //store_sales,store_cost,unit_sales,fact_count
        //DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997.setType(SqlSimpleTypes.Sql99.integerType());

        //quarter,fiscal_period
        //VARCHAR(30),VARCHAR(30)
        COLUMN_TIME_ID_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_TIME_BY_DAY.setName("time_id");
        COLUMN_TIME_ID_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_THE_DATE_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_DATE_TIME_BY_DAY.setName("the_date");
        COLUMN_THE_DATE_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.timestampType());

        COLUMN_THE_DAY_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_DAY_TIME_BY_DAY.setName("the_day");
        COLUMN_THE_DAY_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_MONTH_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_MONTH_TIME_BY_DAY.setName("the_month");
        COLUMN_THE_MONTH_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_THE_YEAR_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_THE_YEAR_TIME_BY_DAY.setName("the_year");
        COLUMN_THE_YEAR_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_DAY_OF_MONTH_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DAY_OF_MONTH_TIME_BY_DAY.setName("day_of_month");
        COLUMN_DAY_OF_MONTH_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY.setName("week_of_year");
        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY.setName("month_of_year");
        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_QUARTER_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUARTER_TIME_BY_DAY.setName("quarter");
        COLUMN_QUARTER_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_FISCAL_PERIOD_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setName("fiscal_period");
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setType(SqlSimpleTypes.Sql99.varcharType());

        //region_id,store_name,store_number,store_street_address,store_city,store_state,store_postal_code,store_country,store_manager,store_phone,store_fax,first_opened_date,last_remodel_date,store_sqft,grocery_sqft,frozen_sqft,meat_sqft,coffee_bar,video_store,salad_bar,prepared_food,florist
        //INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),TIMESTAMP,TIMESTAMP,INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,SMALLINT,SMALLINT,SMALLINT,SMALLINT

        COLUMN_STORE_ID_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_ID_STORE_RAGGED.setName("store_id");
        COLUMN_STORE_ID_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STORE_TYPE_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_TYPE_STORE_RAGGED.setName("store_type");
        COLUMN_STORE_TYPE_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_NAME_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_NAME_STORE_RAGGED.setName("store_name");
        COLUMN_STORE_NAME_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STREET_ADDRESS_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setName("store_street_address");
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_STATE_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_STATE_STORE_RAGGED.setName("store_state");
        COLUMN_STORE_STATE_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_COUNTRY_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setName("store_country");
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_MANAGER_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_MANAGER_STORE_RAGGED.setName("store_manager");
        COLUMN_STORE_MANAGER_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_CITY_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_CITY_STORE_RAGGED.setName("store_city");
        COLUMN_STORE_CITY_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STORE_SQFT_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STORE_SQFT_STORE_RAGGED.setName("store_sqft");
        COLUMN_STORE_SQFT_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_GROCERY_SQFT_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GROCERY_SQFT_STORE_RAGGED.setName("grocery_sqft");
        COLUMN_GROCERY_SQFT_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_FROZEN_SQFT_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FROZEN_SQFT_STORE_RAGGED.setName("frozen_sqft");
        COLUMN_FROZEN_SQFT_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MEAT_SQFT_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MEAT_SQFT_STORE_RAGGED.setName("meat_sqft");
        COLUMN_MEAT_SQFT_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_COFFEE_BAR_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COFFEE_BAR_STORE_RAGGED.setName("coffee_bar");
        COLUMN_COFFEE_BAR_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.smallintType());

        COLUMN_REGION_ID_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_REGION_ID_STORE_RAGGED.setName("region_id");
        COLUMN_REGION_ID_STORE_RAGGED.setType(SqlSimpleTypes.Sql99.integerType());

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1.getDialects().add("access");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1.setSql("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\".\"store_sales\")");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2.getDialects().addAll(List.of(
                "oracle", "h2", "hsqldb", "postgres", "neoview", "derby", "luciddb", "db2", "nuodb", "snowflake"));
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2.setSql("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3.getDialects().add("infobright");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3.setSql("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4.getDialects().add("generic");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4.setSql("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)");

        MEASURE_PROMOTION_SALES_COL = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        MEASURE_PROMOTION_SALES_COL.getSqls().addAll(List.of(MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1, MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2,
                MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3, MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4));
        MEASURE_PROMOTION_SALES_COL.setType(SqlSimpleTypes.decimalType(18, 4));

        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1.getDialects().addAll(List.of("mysql", "mariadb", "infobright"));
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1.setSql("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`");

        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2 = SourceFactory.eINSTANCE.createSqlStatement();
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2.getDialects().add("generic");
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2.setSql("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;" +
                "warehouse_cost&quot;");

        MEASURE_WAREHOUSE_PROFIT_COL = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        MEASURE_WAREHOUSE_PROFIT_COL.getSqls().addAll(List.of(MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1, MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2));
        MEASURE_WAREHOUSE_PROFIT_COL.setType(SqlSimpleTypes.decimalType(18, 4));

        // Initialize tables
        //product_id,time_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales
        //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4)
        TABLE_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_SALES_FACT.setName("sales_fact_1997");
        TABLE_SALES_FACT.getFeature()
                .addAll(List.of(COLUMN_PRODUCT_ID_SALESFACT, COLUMN_TIME_ID_SALESFACT, COLUMN_CUSTOMER_ID_SALESFACT,
                        COLUMN_PROMOTION_ID_SALESFACT, COLUMN_STORE_ID_SALESFACT, COLUMN_STORE_SALES_SALESFACT,
                        COLUMN_STORE_COST_SALESFACT, COLUMN_UNIT_SALES_SALESFACT));

        //product_id,time_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales
        //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4)
        TABLE_SALES_FACT1998 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_SALES_FACT1998.setName("sales_fact_1998");
        TABLE_SALES_FACT1998.getFeature()
                .addAll(List.of(COLUMN_PRODUCT_ID_SALESFACT1998, COLUMN_TIME_ID_SALESFACT1998, COLUMN_CUSTOMER_ID_SALESFACT1998,
                        COLUMN_PROMOTION_ID_SALESFACT1998,  COLUMN_STORE_ID_SALESFACT1998, COLUMN_STORE_SALES_SALESFACT1998,
                        COLUMN_STORE_COST_SALESFACT1998, COLUMN_UNIT_SALES_SALESFACT1998));

        TABLE_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_TIME.setName("time_by_day");
        TABLE_TIME.getFeature().addAll(List.of(COLUMN_TIME_ID_TIME, COLUMN_THE_DATE_TIME, COLUMN_THE_YEAR_TIME,
                COLUMN_QUARTER_TIME, COLUMN_THE_MONTH_TIME));

        TABLE_STORE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_STORE.setName("store");
        TABLE_STORE.getFeature().addAll(List.of(COLUMN_STORE_ID_STORE, COLUMN_STORE_NAME_STORE,
                COLUMN_STORE_COUNTRY_STORE, COLUMN_STORE_STATE_STORE, COLUMN_STORE_CITY_STORE, COLUMN_STORE_TYPE_STORE,
                COLUMN_REGION_ID_STORE, COLUMN_STORE_STREET_ADDRESS_STORE, COLUMN_STORE_MANAGER_STORE,
                COLUMN_STORE_SQFT_STORE, COLUMN_GROCERY_SQFT_STORE, COLUMN_FROZEN_SQFT_STORE, COLUMN_MEAT_SQFT_STORE,
                COLUMN_COFFEE_BAR_STORE, COLUMN_STORE_POSTAL_CODE_STORE, COLUMN_STORE_NUMBER_STORE, COLUMN_STREET_ADDRESS_STORE));

        TABLE_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_CUSTOMER.setName("customer");
        TABLE_CUSTOMER.getFeature()
                .addAll(List.of(COLUMN_CUSTOMER_ID_CUSTOMER, COLUMN_FULLNAME_CUSTOMER, COLUMN_GENDER_CUSTOMER,
                        COLUMN_COUNTRY_CUSTOMER, COLUMN_STATE_PROVINCE_CUSTOMER, COLUMN_CITY_CUSTOMER,
                        COLUMN_MARITAL_STATUS_CUSTOMER, COLUMN_EDUCATION_CUSTOMER, COLUMN_YEARLY_INCOME_CUSTOMER,
                        COLUMN_MEMBER_CARD_CUSTOMER, COLUMN_OCCUPATION_CUSTOMER, COLUMN_HOUSEOWNER_CUSTOMER,
                        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER, COLUMN_LNAME_CUSTOMER, COLUMN_FNAME_CUSTOMER,
                        COLUMN_ACCOUNT_NUM_CUSTOMER, COLUMN_CUSTOMER_REGION_ID_CUSTOMER, COLUMN_NUM_CARS_OWNED_CUSTOMER,
                        COLUMN_TOTAL_CHILDREN_CUSTOMER, COLUMN_ADDRESS2_CUSTOMER));

        TABLE_PRODUCT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PRODUCT.setName("product");
        TABLE_PRODUCT.getFeature()
                .addAll(List.of(COLUMN_PRODUCT_ID_PRODUCT, COLUMN_PRODUCT_NAME_PRODUCT, COLUMN_BRAND_NAME_PRODUCT,
                        COLUMN_PRODUCT_FAMILY_PRODUCT, COLUMN_PRODUCT_DEPARTMENT_PRODUCT,
                        COLUMN_PRODUCT_CATEGORY_PRODUCT, COLUMN_PRODUCT_SUBCATEGORY_PRODUCT));

        // Initialize new business tables
        TABLE_WAREHOUSE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_WAREHOUSE.setName("warehouse");
        TABLE_WAREHOUSE.getFeature()
                .addAll(List.of(COLUMN_WAREHOUSE_ID_WAREHOUSE, COLUMN_WAREHOUSE_NAME_WAREHOUSE,
                        COLUMN_WAREHOUSE_CITY_WAREHOUSE, COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE,
                        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE, COLUMN_STORES_ID_WAREHOUSE));

        TABLE_INVENTORY_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_INVENTORY_FACT.setName("inventory_fact_1997");
        TABLE_INVENTORY_FACT.getFeature()
                .addAll(List.of(COLUMN_WAREHOUSE_ID_INVENTORY_FACT, COLUMN_STORE_ID_INVENTORY_FACT, COLUMN_PRODUCT_ID_INVENTORY_FACT,
                        COLUMN_TIME_ID_INVENTORY_FACT, COLUMN_STORE_INVOICE_INVENTORY_FACT,
                        COLUMN_SUPPLY_TIME_INVENTORY_FACT, COLUMN_WAREHOUSE_COST_INVENTORY_FACT,
                        COLUMN_WAREHOUSE_SALES_INVENTORY_FACT, COLUMN_UNITS_SHIPPED_INVENTORY_FACT,
                        COLUMN_UNITS_ORDERED_INVENTORY_FACT));

        TABLE_PROMOTION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PROMOTION.setName("promotion");
        TABLE_PROMOTION.getFeature().addAll(
                List.of(COLUMN_PROMOTION_ID_PROMOTION, COLUMN_PROMOTION_NAME_PROMOTION, COLUMN_MEDIA_TYPE_PROMOTION));

        TABLE_EMPLOYEE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_EMPLOYEE.setName("employee");
        TABLE_EMPLOYEE.getFeature()
                .addAll(List.of(COLUMN_EMPLOYEE_ID_EMPLOYEE, COLUMN_FIRST_NAME_EMPLOYEE, COLUMN_LAST_NAME_EMPLOYEE,
                        COLUMN_FULL_NAME_EMPLOYEE, COLUMN_MANAGEMENT_ROLE_EMPLOYEE, COLUMN_POSITION_ID_EMPLOYEE,
                        COLUMN_POSITION_TITLE_EMPLOYEE, COLUMN_STORE_ID_EMPLOYEE, COLUMN_SUPERVISOR_ID_EMPLOYEE,
                        COLUMN_MARITAL_STATUS_EMPLOYEE, COLUMN_GENDER_EMPLOYEE, COLUMN_SALARY_EMPLOYEE, COLUMN_EDUCATION_LEVEL_EMPLOYEE));

        TABLE_DEPARTMENT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_DEPARTMENT.setName("department");
        TABLE_DEPARTMENT.getFeature()
                .addAll(List.of(COLUMN_DEPARTMENT_ID_DEPARTMENT, COLUMN_DEPARTMENT_DESCRIPTION_DEPARTMENT));

        TABLE_POSITION = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_POSITION.setName("position");
        TABLE_POSITION.getFeature().addAll(List.of(COLUMN_POSITION_ID_POSITION, COLUMN_POSITION_TITLE_POSITION,
                COLUMN_PAY_TYPE_POSITION, COLUMN_MIN_SCALE_POSITION, COLUMN_MAX_SCALE_POSITION));

        TABLE_SALARY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_SALARY.setName("salary");
        TABLE_SALARY.getFeature()
                .addAll(List.of(COLUMN_EMPLOYEE_ID_SALARY, COLUMN_DEPARTMENT_ID_SALARY, COLUMN_CURRENCY_ID_SALARY, COLUMN_PAY_DATE_SALARY,
                        COLUMN_SALARY_PAID_SALARY, COLUMN_OVERTIME_PAID_SALARY, COLUMN_VACATION_ACCRUED_SALARY, COLUMN_VACATION_USED_SALARY));

        TABLE_EMPLOYEE_CLOSURE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_EMPLOYEE_CLOSURE.setName("employee_closure");
        TABLE_EMPLOYEE_CLOSURE.getFeature().addAll(List.of(COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE,
                COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE, COLUMN_DISTANCE_EMPLOYEE_CLOSURE));

        TABLE_PRODUCT_CLASS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PRODUCT_CLASS.setName("product_class");
        TABLE_PRODUCT_CLASS.getFeature()
                .addAll(List.of(COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS, COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS,
                        COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS, COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS,
                        COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS));
        TABLE_AGG_C_SPECIAL_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_C_SPECIAL_SALES_FACT_1997.setName("agg_c_special_sales_fact_1997");
        TABLE_AGG_C_SPECIAL_SALES_FACT_1997.getFeature()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997));

        //month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,customer_count,fact_count
        //SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
        TABLE_AGG_C_10_SALES_FACT_1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_C_10_SALES_FACT_1997.setName("agg_c_10_sales_fact_1997");
        TABLE_AGG_C_10_SALES_FACT_1997.getFeature()
        .addAll(List.of(COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997,
                COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997, COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997, COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997,
                COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997, COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997));

        //product_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales,fact_count
        //INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_L_05_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_L_05_SALES_FACT.setName("agg_l_05_sales_fact_1997");
        TABLE_AGG_L_05_SALES_FACT.getFeature()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997,
                COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997, COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997, COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997, COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997
                ));

        //time_id,customer_id,store_sales,store_cost,unit_sales,fact_count
        //INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_L_03_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_L_03_SALES_FACT.setName("agg_l_03_sales_fact_1997");
        TABLE_AGG_L_03_SALES_FACT.getFeature()
        .addAll(List.of(COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997, COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997,
                COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997, COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997
                ));

        //product_id,time_id,customer_id,store_sales_sum,store_cost_sum,unit_sales_sum,fact_count
        //INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_PL_01_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_PL_01_SALES_FACT.setName("agg_pl_01_sales_fact_1997");
        TABLE_AGG_PL_01_SALES_FACT.getFeature()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_PL_01_SALES_FACT_1997, COLUMN_TIME_ID_AGG_PL_01_SALES_FACT_1997,
                COLUMN_CUSTOMER_ID_AGG_PL_01_SALES_FACT_1997, COLUMN_STORE_SALES_SUM_AGG_PL_01_SALES_FACT_1997,
                COLUMN_STORE_COST_SUM_AGG_PL_01_SALES_FACT_1997, COLUMN_UNIT_SALES_SUM_AGG_PL_01_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_PL_01_SALES_FACT_1997
                ));


        //gender,marital_status,product_family,product_department,product_category,month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,customer_count,fact_count
        //VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
        TABLE_AGG_G_MS_PCAT_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_G_MS_PCAT_SALES_FACT.setName("agg_g_ms_pcat_sales_fact_1997");
        TABLE_AGG_G_MS_PCAT_SALES_FACT.getFeature()
        .addAll(List.of(COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997, COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997
                ));

        //product_id,customer_id,store_id,promotion_id,month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,fact_count
        //INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_C_14_SALES_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGG_C_14_SALES_FACT.setName("agg_c_14_sales_fact_1997");
        TABLE_AGG_C_14_SALES_FACT.getFeature()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997,
                COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997, COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997,
                COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997, COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997,
                COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997, COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997,
                COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997, COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997
                ));

        TABLE_TIME_BY_DAY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_TIME_BY_DAY.setName("time_by_day");
        TABLE_TIME_BY_DAY.getFeature()
        .addAll(List.of(COLUMN_TIME_ID_TIME_BY_DAY,
                COLUMN_THE_DATE_TIME_BY_DAY,
                COLUMN_THE_DAY_TIME_BY_DAY,
                COLUMN_THE_MONTH_TIME_BY_DAY,
                COLUMN_THE_YEAR_TIME_BY_DAY,
                COLUMN_DAY_OF_MONTH_TIME_BY_DAY,
                COLUMN_WEEK_OF_YEAR_TIME_BY_DAY,
                COLUMN_MONTH_OF_YEAR_TIME_BY_DAY,
                COLUMN_QUARTER_TIME_BY_DAY,
                COLUMN_FISCAL_PERIOD_TIME_BY_DAY));

        TABLE_STORE_RAGGED = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_STORE_RAGGED.setName("store_ragged");
        TABLE_STORE_RAGGED.getFeature()
        .addAll(List.of(
                COLUMN_STORE_ID_STORE_RAGGED,
                COLUMN_STORE_TYPE_STORE_RAGGED,
                COLUMN_STORE_NAME_STORE_RAGGED,
                COLUMN_STREET_ADDRESS_STORE_RAGGED,
                COLUMN_STORE_STATE_STORE_RAGGED,
                COLUMN_STORE_COUNTRY_STORE_RAGGED,
                COLUMN_STORE_MANAGER_STORE_RAGGED,
                COLUMN_STORE_CITY_STORE_RAGGED,
                COLUMN_STORE_SQFT_STORE_RAGGED,
                COLUMN_GROCERY_SQFT_STORE_RAGGED,
                COLUMN_FROZEN_SQFT_STORE_RAGGED,
                COLUMN_MEAT_SQFT_STORE_RAGGED,
                COLUMN_COFFEE_BAR_STORE_RAGGED,
                COLUMN_REGION_ID_STORE_RAGGED
                ));

        ORDERED_COLUMN_POSITION_ID_EMPLOYEE = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_POSITION_ID_EMPLOYEE.setColumn(COLUMN_POSITION_ID_EMPLOYEE);

        AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationColumnName();
        AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_PRODUCT_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_PROMOTION_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_STORE_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Unit Sales]");
        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Store Cost]");
        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Store Sales]");
        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Year]");
        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997);;

        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Quarter]");
        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997);;

        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Month]");
        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997);;

        // Initialize AggregationName
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationName();
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.setName(TABLE_AGG_C_SPECIAL_SALES_FACT_1997);
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationFactCount(AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997);
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.getAggregationForeignKeys().addAll(List.of(
                AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997));
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.getAggregationMeasures().addAll(List.of(
                AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997
                ));
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.getAggregationLevels().addAll(List.of(
                AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997
                ));

        // Initialize AggregationExcludes
        AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997.setName("agg_c_special_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997.setName("agg_lc_100_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997.setName("agg_lc_10_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997 = AggregationFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997.setName("agg_pc_10_sales_fact_1997");

        // Initialize table queries
        QUERY_STORE = SourceFactory.eINSTANCE.createTableSource();
        QUERY_STORE.setTable(TABLE_STORE);

        QUERY_CUSTOMER = SourceFactory.eINSTANCE.createTableSource();
        QUERY_CUSTOMER.setTable(TABLE_CUSTOMER);

        QUERY_PRODUCT = SourceFactory.eINSTANCE.createTableSource();
        QUERY_PRODUCT.setTable(TABLE_PRODUCT);

        QUERY_SALES_FACT = SourceFactory.eINSTANCE.createTableSource();
        QUERY_SALES_FACT.setTable(TABLE_SALES_FACT);
        QUERY_SALES_FACT.getAggregationExcludes().addAll(List.of(AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997,
                AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997,
                AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997));
        QUERY_SALES_FACT.getAggregationTables().add(AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997);

        // Initialize new table queries
        QUERY_WAREHOUSE = SourceFactory.eINSTANCE.createTableSource();
        QUERY_WAREHOUSE.setTable(TABLE_WAREHOUSE);

        QUERY_INVENTORY_FACT = SourceFactory.eINSTANCE.createTableSource();
        QUERY_INVENTORY_FACT.setTable(TABLE_INVENTORY_FACT);

        QUERY_PROMOTION = SourceFactory.eINSTANCE.createTableSource();
        QUERY_PROMOTION.setTable(TABLE_PROMOTION);

        QUERY_EMPLOYEE = SourceFactory.eINSTANCE.createTableSource();
        QUERY_EMPLOYEE.setTable(TABLE_EMPLOYEE);

        QUERY_DEPARTMENT = SourceFactory.eINSTANCE.createTableSource();
        QUERY_DEPARTMENT.setTable(TABLE_DEPARTMENT);

        QUERY_POSITION = SourceFactory.eINSTANCE.createTableSource();
        QUERY_POSITION.setTable(TABLE_POSITION);

        QUERY_SALARY = SourceFactory.eINSTANCE.createTableSource();
        QUERY_SALARY.setTable(TABLE_SALARY);

        QUERY_EMPLOYEE_CLOSURE = SourceFactory.eINSTANCE.createTableSource();
        QUERY_EMPLOYEE_CLOSURE.setTable(TABLE_EMPLOYEE_CLOSURE);

        QUERY_PRODUCT_CLASS = SourceFactory.eINSTANCE.createTableSource();
        QUERY_PRODUCT_CLASS.setTable(TABLE_PRODUCT_CLASS);

        QUERY_TIME_BY_DAY = SourceFactory.eINSTANCE.createTableSource();
        QUERY_TIME_BY_DAY.setTable(TABLE_TIME_BY_DAY);

        QUERY_STORE_RAGGED = SourceFactory.eINSTANCE.createTableSource();
        QUERY_STORE_RAGGED.setTable(TABLE_STORE_RAGGED);

        JOIN_PRODUCT_PRODUCT_CLASS_LEFT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_PRODUCT_CLASS_LEFT.setKey(COLUMN_PRODUCT_CLASS_ID_PRODUCT);
        JOIN_PRODUCT_PRODUCT_CLASS_LEFT.setQuery(QUERY_PRODUCT);

        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT.setKey(COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS);
        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT.setQuery(QUERY_PRODUCT_CLASS);

        JOIN_PRODUCT_PRODUCT_CLASS = SourceFactory.eINSTANCE.createJoinSource();
        JOIN_PRODUCT_PRODUCT_CLASS.setLeft(JOIN_PRODUCT_PRODUCT_CLASS_LEFT);
        JOIN_PRODUCT_PRODUCT_CLASS.setRight(JOIN_PRODUCT_PRODUCT_CLASS_RIGHT);

        JOIN_EMPLOYEE_POSITION_LEFT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_POSITION_LEFT.setKey(COLUMN_POSITION_ID_EMPLOYEE);
        JOIN_EMPLOYEE_POSITION_LEFT.setQuery(QUERY_EMPLOYEE);

        JOIN_EMPLOYEE_POSITION_RIGHT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_POSITION_RIGHT.setKey(COLUMN_POSITION_ID_POSITION);
        JOIN_EMPLOYEE_POSITION_RIGHT.setQuery(QUERY_POSITION);

        JOIN_EMPLOYEE_POSITION = SourceFactory.eINSTANCE.createJoinSource();
        JOIN_EMPLOYEE_POSITION.setLeft(JOIN_EMPLOYEE_POSITION_LEFT);
        JOIN_EMPLOYEE_POSITION.setRight(JOIN_EMPLOYEE_POSITION_RIGHT);

        JOIN_EMPLOYEE_STORE_LEFT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_STORE_LEFT.setKey(COLUMN_STORE_ID_EMPLOYEE);
        JOIN_EMPLOYEE_STORE_LEFT.setQuery(QUERY_EMPLOYEE);

        JOIN_EMPLOYEE_STORE_RIGHT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_STORE_RIGHT.setKey(COLUMN_STORE_ID_STORE);
        JOIN_EMPLOYEE_STORE_RIGHT.setQuery(QUERY_STORE);

        JOIN_EMPLOYEE_STORE = SourceFactory.eINSTANCE.createJoinSource();
        JOIN_EMPLOYEE_STORE.setLeft(JOIN_EMPLOYEE_STORE_LEFT);
        JOIN_EMPLOYEE_STORE.setRight(JOIN_EMPLOYEE_STORE_RIGHT);

        // Initialize levels
        LEVEL_YEAR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_YEAR.setName("Year");
        LEVEL_YEAR.setUniqueMembers(true);
        LEVEL_YEAR.setColumn(COLUMN_THE_YEAR_TIME_BY_DAY);
        LEVEL_YEAR.setType(LevelDefinition.TIME_YEARS);
        LEVEL_YEAR.setColumnType(ColumnInternalDataType.NUMERIC);

        LEVEL_QUARTER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_QUARTER.setName("Quarter");
        LEVEL_QUARTER.setUniqueMembers(false);
        LEVEL_QUARTER.setColumn(COLUMN_QUARTER_TIME_BY_DAY);
        LEVEL_QUARTER.setType(LevelDefinition.TIME_QUARTERS);

        LEVEL_MONTH = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MONTH.setName("Month");
        LEVEL_MONTH.setColumn(COLUMN_MONTH_OF_YEAR_TIME_BY_DAY);
        LEVEL_MONTH.setUniqueMembers(false);
        LEVEL_MONTH.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_MONTH.setType(LevelDefinition.TIME_MONTHS);

        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setName("Month");
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setColumn(COLUMN_MONTH_OF_YEAR_TIME_BY_DAY);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setNameColumn(COLUMN_THE_MONTH_TIME_BY_DAY);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setUniqueMembers(false);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setType(LevelDefinition.TIME_MONTHS);

        LEVEL_WEEK = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WEEK.setName("Week");
        LEVEL_WEEK.setColumn(COLUMN_WEEK_OF_YEAR_TIME_BY_DAY);
        LEVEL_WEEK.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_WEEK.setType(LevelDefinition.TIME_WEEKS);

        LEVEL_DAY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_DAY.setName("Day");
        LEVEL_DAY.setColumn(COLUMN_DAY_OF_MONTH_TIME_BY_DAY);
        LEVEL_DAY.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_DAY.setUniqueMembers(false);
        LEVEL_DAY.setType(LevelDefinition.TIME_DAYS);

        LEVEL_STORE_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_COUNTRY.setName("Store Country");
        LEVEL_STORE_COUNTRY.setColumn(COLUMN_STORE_COUNTRY_STORE);
        LEVEL_STORE_COUNTRY.setUniqueMembers(true);

        LEVEL_STORE_STATE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_STATE.setName("Store State");
        LEVEL_STORE_STATE.setColumn(COLUMN_STORE_STATE_STORE);
        LEVEL_STORE_STATE.setUniqueMembers(true);

        LEVEL_STORE_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CITY.setName("Store City");
        LEVEL_STORE_CITY.setColumn(COLUMN_STORE_CITY_STORE);
        LEVEL_STORE_CITY.setUniqueMembers(false);

        LEVEL_STORE_PROP1 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP1.setName("Store Type");
        LEVEL_STORE_PROP1.setColumn(COLUMN_STORE_TYPE_STORE);

        LEVEL_STORE_PROP2 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP2.setName("Store Manager");
        LEVEL_STORE_PROP2.setColumn(COLUMN_STORE_MANAGER_STORE);

        LEVEL_STORE_PROP3 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP3.setName("Store Sqft");
        LEVEL_STORE_PROP3.setColumn(COLUMN_STORE_SQFT_STORE);
        LEVEL_STORE_PROP3.setPropertyType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_PROP4 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP4.setName("Grocery Sqft");
        LEVEL_STORE_PROP4.setColumn(COLUMN_GROCERY_SQFT_STORE);
        LEVEL_STORE_PROP4.setPropertyType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_PROP5 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP5.setName("Frozen Sqft");
        LEVEL_STORE_PROP5.setColumn(COLUMN_FROZEN_SQFT_STORE);
        LEVEL_STORE_PROP5.setPropertyType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_PROP6 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP6.setName("Meat Sqft");
        LEVEL_STORE_PROP6.setColumn(COLUMN_MEAT_SQFT_STORE);
        LEVEL_STORE_PROP6.setPropertyType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_PROP7 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP7.setName("Has coffee bar");
        LEVEL_STORE_PROP7.setColumn(COLUMN_COFFEE_BAR_STORE);
        LEVEL_STORE_PROP7.setPropertyType(ColumnInternalDataType.BOOLEAN);

        LEVEL_STORE_PROP8 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP8.setName("Street address");
        LEVEL_STORE_PROP8.setColumn(COLUMN_STORE_STREET_ADDRESS_STORE);
        LEVEL_STORE_PROP8.setPropertyType(ColumnInternalDataType.STRING);

        LEVEL_STORE_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NAME.setName("Store Name");
        LEVEL_STORE_NAME.setUniqueMembers(true);
        LEVEL_STORE_NAME.setColumn(COLUMN_STORE_NAME_STORE);
        LEVEL_STORE_NAME.getMemberProperties().addAll(List.of(LEVEL_STORE_PROP1, LEVEL_STORE_PROP2, LEVEL_STORE_PROP3,
                LEVEL_STORE_PROP4, LEVEL_STORE_PROP5, LEVEL_STORE_PROP6, LEVEL_STORE_PROP7, LEVEL_STORE_PROP8
                ));

        LEVEL_STORE_HAS_COFFEE_BAR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_HAS_COFFEE_BAR.setName("Has coffee bar");
        LEVEL_STORE_HAS_COFFEE_BAR.setColumn(COLUMN_COFFEE_BAR_STORE);
        LEVEL_STORE_HAS_COFFEE_BAR.setUniqueMembers(true);
        LEVEL_STORE_HAS_COFFEE_BAR.setColumnType(ColumnInternalDataType.BOOLEAN);

        LEVEL_CUSTOMER_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_COUNTRY.setName("Country");
        LEVEL_CUSTOMER_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);

        LEVEL_CUSTOMER_STATE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_STATE.setName("State Province");
        LEVEL_CUSTOMER_STATE.setColumn(COLUMN_STATE_PROVINCE_CUSTOMER);

        LEVEL_CUSTOMER_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_CITY.setName("City");
        LEVEL_CUSTOMER_CITY.setColumn(COLUMN_CITY_CUSTOMER);

        LEVEL_CUSTOMER_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_NAME.setName("Name");
        LEVEL_CUSTOMER_NAME.setColumn(COLUMN_FULLNAME_CUSTOMER);

        LEVEL_CUSTOMER_GENDER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_GENDER.setName("Gender");
        LEVEL_CUSTOMER_GENDER.setColumn(COLUMN_GENDER_CUSTOMER);

        LEVEL_PRODUCT_FAMILY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_FAMILY.setName("Product Family");
        LEVEL_PRODUCT_FAMILY.setColumn(COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS);

        LEVEL_PRODUCT_DEPARTMENT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_DEPARTMENT.setName("Product Department");
        LEVEL_PRODUCT_DEPARTMENT.setColumn(COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS);
        LEVEL_PRODUCT_DEPARTMENT.setUniqueMembers(false);

        LEVEL_PRODUCT_CATEGORY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY.setName("Product Category");
        LEVEL_PRODUCT_CATEGORY.setColumn(COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS);
        LEVEL_PRODUCT_CATEGORY.setUniqueMembers(false);

        LEVEL_PRODUCT_SUBCATEGORY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY.setName("Product Subcategory");
        LEVEL_PRODUCT_SUBCATEGORY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS);
        LEVEL_PRODUCT_SUBCATEGORY.setUniqueMembers(false);

        LEVEL_PRODUCT_BRAND = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_BRAND.setName("Brand Name");
        LEVEL_PRODUCT_BRAND.setColumn(COLUMN_BRAND_NAME_PRODUCT);

        LEVEL_PRODUCT_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_NAME.setName("Product Name");
        LEVEL_PRODUCT_NAME.setColumn(COLUMN_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_NAME.setUniqueMembers(true);

        LEVEL_PROMOTION_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PROMOTION_NAME.setName("Promotion Name");
        LEVEL_PROMOTION_NAME.setColumn(COLUMN_PROMOTION_NAME_PROMOTION);
        LEVEL_PROMOTION_NAME.setUniqueMembers(true);

        LEVEL_STORE_SQFT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_SQFT.setName("Store Sqft");
        LEVEL_STORE_SQFT.setColumn(COLUMN_STORE_SQFT_STORE);
        LEVEL_STORE_SQFT.setUniqueMembers(true);
        LEVEL_STORE_SQFT.setColumnType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_TYPE_WITHOUT_TABLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setName("Store Type");
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setColumn(COLUMN_STORE_TYPE_STORE);
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setUniqueMembers(true);

        LEVEL_EDUCATION = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EDUCATION.setName("Education Level");
        LEVEL_EDUCATION.setColumn(COLUMN_EDUCATION_CUSTOMER);
        LEVEL_EDUCATION.setUniqueMembers(true);

        LEVEL_GENDER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_GENDER.setName("Gender");
        LEVEL_GENDER.setColumn(COLUMN_GENDER_CUSTOMER);
        LEVEL_GENDER.setUniqueMembers(true);

        LEVEL_MARITAL_STATUS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MARITAL_STATUS.setName("Marital Status");
        LEVEL_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);
        LEVEL_MARITAL_STATUS.setUniqueMembers(true);
        LEVEL_MARITAL_STATUS.setApproxRowCount("111");

        LEVEL_YEARLY_INCOME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_YEARLY_INCOME.setName("Yearly Income");
        LEVEL_YEARLY_INCOME.setColumn(COLUMN_YEARLY_INCOME_CUSTOMER);
        LEVEL_YEARLY_INCOME.setUniqueMembers(true);

        LEVEL_PAY_TYPE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PAY_TYPE.setName("Pay Type");
        LEVEL_PAY_TYPE.setColumn(COLUMN_PAY_TYPE_POSITION);
        LEVEL_PAY_TYPE.setUniqueMembers(true);

        LEVEL_STORE_TYPE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_TYPE.setName("Store Type");
        LEVEL_STORE_TYPE.setColumn(COLUMN_STORE_TYPE_STORE);
        LEVEL_STORE_TYPE.setUniqueMembers(true);

        LEVEL_STORE_COUNTRY_WITH_NEVER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_COUNTRY_WITH_NEVER.setName("Store Country");
        LEVEL_STORE_COUNTRY_WITH_NEVER.setColumn(COLUMN_STORE_COUNTRY_STORE_RAGGED);
        LEVEL_STORE_COUNTRY_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_STORE_CYTY_IF_PARENTS_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setName("Store State");
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setColumn(COLUMN_STORE_STATE_STORE_RAGGED);
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setHideMemberIf(HideMemberIf.IF_PARENTS_NAME);
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setUniqueMembers(true);

        LEVEL_STORE_CYTY_IF_BLANK_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setName("Store City");
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setColumn(COLUMN_STORE_CITY_STORE_RAGGED);
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setUniqueMembers(false);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1.setName("Store Type");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1.setColumn(COLUMN_STORE_TYPE_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2.setName("Store Manager");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2.setColumn(COLUMN_STORE_MANAGER_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3.setName("Store Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3.setColumn(COLUMN_STORE_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4.setName("Grocery Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4.setColumn(COLUMN_GROCERY_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5.setName("Frozen Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5.setColumn(COLUMN_FROZEN_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6.setName("Meat Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6.setColumn(COLUMN_MEAT_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7.setName("Has coffee bar");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7.setColumn(COLUMN_COFFEE_BAR_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8.setName("Street address");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8.setColumn(COLUMN_STREET_ADDRESS_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setName("Store Name");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setColumn(COLUMN_STORE_NAME_STORE_RAGGED);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setUniqueMembers(true);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.getMemberProperties().addAll(List.of(
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8));

        LEVEL_COUNTRY_WITH_NEVER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_WITH_NEVER.setName("Country");
        LEVEL_COUNTRY_WITH_NEVER.setColumn(COLUMN_STORE_COUNTRY_STORE_RAGGED);
        LEVEL_COUNTRY_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);
        LEVEL_COUNTRY_WITH_NEVER.setUniqueMembers(true);

        LEVEL_STATE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STATE.setName("State");
        LEVEL_STATE.setColumn(COLUMN_STORE_STATE_STORE_RAGGED);
        LEVEL_STATE.setHideMemberIf(HideMemberIf.IF_PARENTS_NAME);
        LEVEL_STATE.setUniqueMembers(true);

        LEVEL_CITY_TABLE_COLUMN_STORE_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setName("City");
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setColumn(COLUMN_STORE_CITY_STORE_RAGGED);
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setUniqueMembers(false);

        // Initialize new levels
        LEVEL_WAREHOUSE_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_COUNTRY.setName("Country");
        LEVEL_WAREHOUSE_COUNTRY.setColumn(COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE);

        LEVEL_WAREHOUSE_STATE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_STATE.setName("State Province");
        LEVEL_WAREHOUSE_STATE.setColumn(COLUMN_WAREHOUSE_STATE_PROVINCE_WAREHOUSE);

        LEVEL_WAREHOUSE_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_CITY.setName("City");
        LEVEL_WAREHOUSE_CITY.setColumn(COLUMN_WAREHOUSE_CITY_WAREHOUSE);

        LEVEL_WAREHOUSE_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WAREHOUSE_NAME.setName("Warehouse Name");
        LEVEL_WAREHOUSE_NAME.setColumn(COLUMN_WAREHOUSE_NAME_WAREHOUSE);
        LEVEL_WAREHOUSE_NAME.setUniqueMembers(true);

        LEVEL_PROMOTION_MEDIA = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PROMOTION_MEDIA.setName("Media Type");
        LEVEL_PROMOTION_MEDIA.setColumn(COLUMN_MEDIA_TYPE_PROMOTION);
        LEVEL_PROMOTION_MEDIA.setUniqueMembers(true);

        LEVEL_EMPLOYEE_MANAGEMENT_ROLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_MANAGEMENT_ROLE.setName("Management Role");
        LEVEL_EMPLOYEE_MANAGEMENT_ROLE.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);

        LEVEL_EMPLOYEE_POSITION = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_POSITION.setName("Position Title");
        LEVEL_EMPLOYEE_POSITION.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);

        LEVEL_EMPLOYEE_DEPARTMENT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_DEPARTMENT.setName("Position ID");
        LEVEL_EMPLOYEE_DEPARTMENT.setColumn(COLUMN_POSITION_ID_EMPLOYEE);

        LEVEL_EMPLOYEE_FULL_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_FULL_NAME.setName("Employee Name");
        LEVEL_EMPLOYEE_FULL_NAME.setColumn(COLUMN_FULL_NAME_EMPLOYEE);

        LEVEL_DEPARTMENT_DESCRIPTION = LevelFactory.eINSTANCE.createLevel();
        LEVEL_DEPARTMENT_DESCRIPTION.setName("Department Description");
        LEVEL_DEPARTMENT_DESCRIPTION.setColumn(COLUMN_DEPARTMENT_ID_DEPARTMENT);
        LEVEL_DEPARTMENT_DESCRIPTION.setColumnType(ColumnInternalDataType.NUMERIC);

        LEVEL_POSITION_TITLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_POSITION_TITLE.setName("Position Title");
        LEVEL_POSITION_TITLE.setColumn(COLUMN_POSITION_TITLE_POSITION);

        LEVEL_HR_POSITION_TITLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_HR_POSITION_TITLE.setName("Position Title");
        LEVEL_HR_POSITION_TITLE.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);
        LEVEL_HR_POSITION_TITLE.getOrdinalColumns().add(ORDERED_COLUMN_POSITION_ID_EMPLOYEE);

        LEVEL_MANAGEMENT_ROLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MANAGEMENT_ROLE.setName("Management Role");
        LEVEL_MANAGEMENT_ROLE.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);

        LEVEL_CUSTOMER_EDUCATION = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_EDUCATION.setName("Education Level");
        LEVEL_CUSTOMER_EDUCATION.setColumn(COLUMN_EDUCATION_CUSTOMER);

        LEVEL_CUSTOMER_MARITAL_STATUS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_MARITAL_STATUS.setName("Marital Status");
        LEVEL_CUSTOMER_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);

        LEVEL_EMPLOYEE_PROP1 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP1.setName("Marital Status");
        LEVEL_EMPLOYEE_PROP1.setColumn(COLUMN_MARITAL_STATUS_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP2 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP2.setName("Position Title");
        LEVEL_EMPLOYEE_PROP2.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP3 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP3.setName("Gender");
        LEVEL_EMPLOYEE_PROP3.setColumn(COLUMN_GENDER_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP4 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP4.setName("Salary");
        LEVEL_EMPLOYEE_PROP4.setColumn(COLUMN_SALARY_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP5 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP5.setName("Education Level");
        LEVEL_EMPLOYEE_PROP5.setColumn(COLUMN_EDUCATION_LEVEL_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP6 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP6.setName("Management Role");
        LEVEL_EMPLOYEE_PROP6.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);

        LEVEL_EMPLOYEE_ID = LevelFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_ID.setName("Employee Id");
        LEVEL_EMPLOYEE_ID.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_EMPLOYEE_ID.setColumn(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        LEVEL_EMPLOYEE_ID.setNameColumn(COLUMN_FULL_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_ID.setUniqueMembers(true);
        LEVEL_EMPLOYEE_ID.getMemberProperties().addAll(List.of(LEVEL_EMPLOYEE_PROP1, LEVEL_EMPLOYEE_PROP2, LEVEL_EMPLOYEE_PROP3,
                LEVEL_EMPLOYEE_PROP4, LEVEL_EMPLOYEE_PROP5, LEVEL_EMPLOYEE_PROP6));

        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setName("Country");
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setUniqueMembers(true);


        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setName("State Province");
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setColumn(COLUMN_STATE_PROVINCE_CUSTOMER);
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setUniqueMembers(true);

        LEVEL_CITY_TABLE_COLUMN_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CITY_TABLE_COLUMN_CITY.setName("City");
        LEVEL_CITY_TABLE_COLUMN_CITY.setColumn(COLUMN_CITY_CUSTOMER);
        LEVEL_CITY_TABLE_COLUMN_CITY.setUniqueMembers(false);

        LEVEL_NAME_PROP1 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP1.setName("Gender");
        LEVEL_NAME_PROP1.setColumn(COLUMN_GENDER_CUSTOMER);

        LEVEL_NAME_PROP2 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP2.setName("Marital Status");
        LEVEL_NAME_PROP2.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);

        LEVEL_NAME_PROP3 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP3.setName("Education");
        LEVEL_NAME_PROP3.setColumn(COLUMN_EDUCATION_CUSTOMER);

        LEVEL_NAME_PROP4 = LevelFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP4.setName("Yearly Income");
        LEVEL_NAME_PROP4.setColumn(COLUMN_YEARLY_INCOME_CUSTOMER);

        NAME_COL_SQL_STATEMENT1 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT1.getDialects().addAll(List.of("oracle", "h2", "hsqldb", "postgres", "luciddb", "teradata"));
        NAME_COL_SQL_STATEMENT1.setSql("\"fname\" || ' ' || \"lname\"");

        NAME_COL_SQL_STATEMENT2 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT2.getDialects().addAll(List.of("hive"));
        NAME_COL_SQL_STATEMENT2.setSql("`customer`.`fullname`");

        NAME_COL_SQL_STATEMENT3 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT3.getDialects().addAll(List.of("access", "mssql"));
        NAME_COL_SQL_STATEMENT3.setSql("fname + ' ' + lname");

        NAME_COL_SQL_STATEMENT4 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT4.getDialects().addAll(List.of("mysql", "mariadb"));
        NAME_COL_SQL_STATEMENT4.setSql("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)");

        NAME_COL_SQL_STATEMENT5 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT5.getDialects().addAll(List.of("derby", "neoview", "snowflake"));
        NAME_COL_SQL_STATEMENT5.setSql("\"customer\".\"fullname\"");

        NAME_COL_SQL_STATEMENT6 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT6.getDialects().addAll(List.of("db2"));
        NAME_COL_SQL_STATEMENT6.setSql("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")");

        NAME_COL_SQL_STATEMENT7 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_COL_SQL_STATEMENT7.getDialects().addAll(List.of("generic"));
        NAME_COL_SQL_STATEMENT7.setSql("fullname");

        SQL_EXPRESSION_COLUMN_NAME = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        SQL_EXPRESSION_COLUMN_NAME.getSqls().addAll(List.of(NAME_COL_SQL_STATEMENT1, NAME_COL_SQL_STATEMENT2, NAME_COL_SQL_STATEMENT3, NAME_COL_SQL_STATEMENT4,
                NAME_COL_SQL_STATEMENT5, NAME_COL_SQL_STATEMENT6, NAME_COL_SQL_STATEMENT7));
        SQL_EXPRESSION_COLUMN_NAME.setType(SqlSimpleTypes.Sql99.varcharType());

        NAME_ORDER_COL_SQL_STATEMENT1 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT1.getDialects().addAll(List.of("oracle", "h2", "hsqldb", "postgres", "luciddb"));
        NAME_ORDER_COL_SQL_STATEMENT1.setSql("\"fname\" || ' ' || \"lname\"");

        NAME_ORDER_COL_SQL_STATEMENT2 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT2.getDialects().addAll(List.of("access", "mssql"));
        NAME_ORDER_COL_SQL_STATEMENT2.setSql("fname + ' ' + lname");

        NAME_ORDER_COL_SQL_STATEMENT3 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT3.getDialects().addAll(List.of("mysql", "mariadb"));
        NAME_ORDER_COL_SQL_STATEMENT3.setSql("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)");

        NAME_ORDER_COL_SQL_STATEMENT4 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT4.getDialects().addAll(List.of("derby", "neoview", "snowflake"));
        NAME_ORDER_COL_SQL_STATEMENT4.setSql("\"customer\".\"fullname\"");

        NAME_ORDER_COL_SQL_STATEMENT5 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT5.getDialects().addAll(List.of("db2"));
        NAME_ORDER_COL_SQL_STATEMENT5.setSql("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")");

        NAME_ORDER_COL_SQL_STATEMENT6 = SourceFactory.eINSTANCE.createSqlStatement();
        NAME_ORDER_COL_SQL_STATEMENT6.getDialects().addAll(List.of("generic"));
        NAME_ORDER_COL_SQL_STATEMENT6.setSql("fullname");

        SQL_EXPRESSION_COLUMN_NAME_ORDER = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        SQL_EXPRESSION_COLUMN_NAME_ORDER.getSqls().addAll(List.of(NAME_ORDER_COL_SQL_STATEMENT1, NAME_ORDER_COL_SQL_STATEMENT2, NAME_ORDER_COL_SQL_STATEMENT3, NAME_ORDER_COL_SQL_STATEMENT4));
        SQL_EXPRESSION_COLUMN_NAME_ORDER.setType(SqlSimpleTypes.Sql99.varcharType());

        ORDERED_SQL_EXPRESSION_COLUMN_NAME_ORDER = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_SQL_EXPRESSION_COLUMN_NAME_ORDER.setColumn(SQL_EXPRESSION_COLUMN_NAME_ORDER);

        LEVEL_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_NAME.setName("Name");
        LEVEL_NAME.setColumn(COLUMN_CUSTOMER_ID_CUSTOMER);
        LEVEL_NAME.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_NAME.setUniqueMembers(true);
        LEVEL_NAME.setNameColumn(SQL_EXPRESSION_COLUMN_NAME);
        LEVEL_NAME.getOrdinalColumns().add(ORDERED_SQL_EXPRESSION_COLUMN_NAME_ORDER);
        LEVEL_NAME.getMemberProperties().addAll(List.of(LEVEL_NAME_PROP1, LEVEL_NAME_PROP2, LEVEL_NAME_PROP3, LEVEL_NAME_PROP4));

        // Initialize hierarchies
        HIERARCHY_TIME = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME.setHasAll(false);
        HIERARCHY_TIME.setPrimaryKey(COLUMN_TIME_ID_TIME_BY_DAY);
        HIERARCHY_TIME.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_TIME.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH));

        HIERARCHY_HR_TIME = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_TIME.setHasAll(false);
        HIERARCHY_HR_TIME.setPrimaryKey(COLUMN_THE_DATE_TIME_BY_DAY);
        HIERARCHY_HR_TIME.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_HR_TIME.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR));

        HIERARCHY_TIME2 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME2.setHasAll(true);
        HIERARCHY_TIME2.setPrimaryKey(COLUMN_TIME_ID_TIME_BY_DAY);
        HIERARCHY_TIME2.setName("Weekly");
        HIERARCHY_TIME2.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_TIME2.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_WEEK, LEVEL_DAY));


        HIERARCHY_STORE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_STORE.setName("Store Hierarchy");
        HIERARCHY_STORE.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE.setHasAll(true);
        //HIERARCHY_STORE.setAllMemberName("All Stores");
        HIERARCHY_STORE.setQuery(QUERY_STORE);
        HIERARCHY_STORE.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE, LEVEL_STORE_CITY, LEVEL_STORE_NAME));

        HIERARCHY_STORE_SALES_RAGGED = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_SALES_RAGGED.setPrimaryKey(COLUMN_STORE_ID_STORE_RAGGED);
        HIERARCHY_STORE_SALES_RAGGED.setHasAll(true);
        HIERARCHY_STORE_SALES_RAGGED.setQuery(QUERY_STORE_RAGGED);
        HIERARCHY_STORE_SALES_RAGGED.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY_WITH_NEVER, LEVEL_STORE_CYTY_IF_PARENTS_NAME,
                        LEVEL_STORE_CYTY_IF_BLANK_NAME, LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER));

        HIERARCHY_HR_STORE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_STORE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_HR_STORE.setHasAll(true);
        HIERARCHY_HR_STORE.setQuery(JOIN_EMPLOYEE_STORE);
        HIERARCHY_HR_STORE.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE,
                        LEVEL_STORE_CITY, LEVEL_STORE_NAME));

        HIERARCHY_PAY_TYPE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PAY_TYPE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_PAY_TYPE.setQuery(JOIN_EMPLOYEE_POSITION);
        HIERARCHY_PAY_TYPE.setHasAll(true);
        HIERARCHY_PAY_TYPE.getLevels().addAll(List.of(LEVEL_PAY_TYPE));

        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setQuery(JOIN_EMPLOYEE_STORE);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setHasAll(true);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.getLevels().addAll(List.of(LEVEL_STORE_TYPE));

        HIERARCHY_STORE_HAS_COFFEE_BAR = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_HAS_COFFEE_BAR.setHasAll(true);
        HIERARCHY_STORE_HAS_COFFEE_BAR.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_HAS_COFFEE_BAR.getLevels().addAll(List.of(LEVEL_STORE_HAS_COFFEE_BAR));

        HIERARCHY_CUSTOMER = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMER.setHasAll(true);
        HIERARCHY_CUSTOMER.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMER.getLevels()
                .addAll(List.of(LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE, LEVEL_CITY_TABLE_COLUMN_CITY, LEVEL_NAME));

        HIERARCHY_CUSTOMERS_GEO = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GEO.setName("Geography");
        HIERARCHY_CUSTOMERS_GEO.setHasAll(true);
        HIERARCHY_CUSTOMERS_GEO.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMERS_GEO.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);//
        HIERARCHY_CUSTOMERS_GEO.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GEO.getLevels().addAll(
                List.of(LEVEL_CUSTOMER_COUNTRY, LEVEL_CUSTOMER_STATE, LEVEL_CUSTOMER_CITY, LEVEL_CUSTOMER_NAME));

        HIERARCHY_CUSTOMERS_GENDER = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GENDER.setName("Gender");
        HIERARCHY_CUSTOMERS_GENDER.setHasAll(true);
        HIERARCHY_CUSTOMERS_GENDER.setAllMemberName("All Gender");
        HIERARCHY_CUSTOMERS_GENDER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_GENDER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GENDER.getLevels().add(LEVEL_CUSTOMER_GENDER);

        HIERARCHY_PRODUCT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PRODUCT.setName("Product Hierarchy");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setPrimaryKey(COLUMN_PRODUCT_ID_PRODUCT);
        //HIERARCHY_PRODUCT.setAllMemberName("All Products");
        HIERARCHY_PRODUCT.setQuery(JOIN_PRODUCT_PRODUCT_CLASS);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(LEVEL_PRODUCT_FAMILY, LEVEL_PRODUCT_DEPARTMENT,
                LEVEL_PRODUCT_CATEGORY, LEVEL_PRODUCT_SUBCATEGORY, LEVEL_PRODUCT_BRAND, LEVEL_PRODUCT_NAME));

        HIERARCHY_STORE_SIZE_IN_SQFT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_SIZE_IN_SQFT.setHasAll(true);
        HIERARCHY_STORE_SIZE_IN_SQFT.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_SIZE_IN_SQFT.setQuery(QUERY_STORE);
        HIERARCHY_STORE_SIZE_IN_SQFT.getLevels().add(LEVEL_STORE_SQFT);

        HIERARCHY_PROMOTIONS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PROMOTIONS.setHasAll(true);
        HIERARCHY_PROMOTIONS.setAllMemberName("All Promotions");
        HIERARCHY_PROMOTIONS.setPrimaryKey(COLUMN_PROMOTION_ID_PROMOTION);
        HIERARCHY_PROMOTIONS.setDefaultMember("All Promotions");
        HIERARCHY_PROMOTIONS.setQuery(QUERY_PROMOTION);
        HIERARCHY_PROMOTIONS.getLevels().add(LEVEL_PROMOTION_NAME);

        HIERARCHY_STORE_TYPE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE.setHasAll(true);
        HIERARCHY_STORE_TYPE.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_TYPE.setQuery(QUERY_STORE);
        HIERARCHY_STORE_TYPE.getLevels().add(LEVEL_STORE_TYPE_WITHOUT_TABLE);

        HIERARCHY_STORE_TYPE_WITHOUT_TABLE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.setPrimaryKey(COLUMN_STORE_ID_STORE); //remove that
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.setHasAll(true);
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.getLevels().add(LEVEL_STORE_TYPE_WITHOUT_TABLE);

        HIERARCHY_EDUCATION_LEVEL = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_EDUCATION_LEVEL.setHasAll(true);
        HIERARCHY_EDUCATION_LEVEL.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_EDUCATION_LEVEL.setQuery(QUERY_CUSTOMER);
        HIERARCHY_EDUCATION_LEVEL.getLevels().add(LEVEL_EDUCATION);

        HIERARCHY_GENDER = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GENDER.setHasAll(true);
        HIERARCHY_GENDER.setAllMemberName("All Gender");
        HIERARCHY_GENDER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_GENDER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_GENDER.getLevels().add(LEVEL_GENDER);

        HIERARCHY_MARITAL_STATUS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_MARITAL_STATUS.setHasAll(true);
        HIERARCHY_MARITAL_STATUS.setAllMemberName("All Marital Status");
        HIERARCHY_MARITAL_STATUS.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_MARITAL_STATUS.setQuery(QUERY_CUSTOMER);
        HIERARCHY_MARITAL_STATUS.getLevels().add(LEVEL_MARITAL_STATUS);

        HIERARCHY_YEARLY_INCOME = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_YEARLY_INCOME.setHasAll(true);
        HIERARCHY_YEARLY_INCOME.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_YEARLY_INCOME.setQuery(QUERY_CUSTOMER);
        HIERARCHY_YEARLY_INCOME.getLevels().add(LEVEL_YEARLY_INCOME);

        // Initialize new hierarchies
        HIERARCHY_WAREHOUSE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_WAREHOUSE.setName("Warehouse");
        HIERARCHY_WAREHOUSE.setHasAll(true);
        HIERARCHY_WAREHOUSE.setAllMemberName("All Warehouses");
        HIERARCHY_WAREHOUSE.setPrimaryKey(COLUMN_WAREHOUSE_ID_WAREHOUSE);
        HIERARCHY_WAREHOUSE.setQuery(QUERY_WAREHOUSE);
        HIERARCHY_WAREHOUSE.getLevels().addAll(
                List.of(LEVEL_WAREHOUSE_COUNTRY, LEVEL_WAREHOUSE_STATE, LEVEL_WAREHOUSE_CITY, LEVEL_WAREHOUSE_NAME));

        HIERARCHY_PROMOTION_MEDIA = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PROMOTION_MEDIA.setName("Promotion Media");
        HIERARCHY_PROMOTION_MEDIA.setHasAll(true);
        HIERARCHY_PROMOTION_MEDIA.setPrimaryKey(COLUMN_PROMOTION_ID_PROMOTION);
        HIERARCHY_PROMOTION_MEDIA.setAllMemberName("All Media");
        HIERARCHY_PROMOTION_MEDIA.setQuery(QUERY_PROMOTION);
        HIERARCHY_PROMOTION_MEDIA.getLevels().addAll(List.of(LEVEL_PROMOTION_MEDIA));

        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK = HierarchyFactory.eINSTANCE.createParentChildLink();
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setParentColumn(COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE);
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setChildColumn(COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE);
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setTable(QUERY_EMPLOYEE_CLOSURE);

        HIERARCHY_EMPLOYEE = HierarchyFactory.eINSTANCE.createParentChildHierarchy();
        //HIERARCHY_EMPLOYEE.setName("Employee");
        HIERARCHY_EMPLOYEE.setHasAll(true);
        HIERARCHY_EMPLOYEE.setAllMemberName("All Employees");
        HIERARCHY_EMPLOYEE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setQuery(QUERY_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setParentColumn(COLUMN_SUPERVISOR_ID_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setNullParentValue("0");
        HIERARCHY_EMPLOYEE.setParentChildLink(HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK);
        HIERARCHY_EMPLOYEE.setLevel(LEVEL_EMPLOYEE_ID);

        HIERARCHY_DEPARTMENT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEPARTMENT.setName("Department");
        HIERARCHY_DEPARTMENT.setHasAll(true);
        HIERARCHY_DEPARTMENT.setPrimaryKey(COLUMN_DEPARTMENT_ID_DEPARTMENT);
        //HIERARCHY_DEPARTMENT.setAllMemberName("All Departments");
        HIERARCHY_DEPARTMENT.setQuery(QUERY_DEPARTMENT);
        HIERARCHY_DEPARTMENT.getLevels().addAll(List.of(LEVEL_DEPARTMENT_DESCRIPTION));

        HIERARCHY_POSITION = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_POSITION.setName("Position");
        HIERARCHY_POSITION.setHasAll(true);
        HIERARCHY_POSITION.setAllMemberName("All Positions");
        HIERARCHY_POSITION.setPrimaryKey(COLUMN_POSITION_ID_POSITION);
        HIERARCHY_POSITION.setQuery(QUERY_POSITION);
        HIERARCHY_POSITION.getLevels().addAll(List.of(LEVEL_POSITION_TITLE));

        HIERARCHY_HR_POSITION = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_POSITION.setName("Position");
        HIERARCHY_HR_POSITION.setHasAll(true);
        HIERARCHY_HR_POSITION.setAllMemberName("All Position");
        HIERARCHY_HR_POSITION.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_HR_POSITION.setQuery(QUERY_EMPLOYEE);
        HIERARCHY_HR_POSITION.getLevels().addAll(List.of(LEVEL_MANAGEMENT_ROLE, LEVEL_HR_POSITION_TITLE));

        HIERARCHY_CUSTOMERS_EDUCATION = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_EDUCATION.setName("Customers Education");
        HIERARCHY_CUSTOMERS_EDUCATION.setHasAll(true);
        HIERARCHY_CUSTOMERS_EDUCATION.setAllMemberName("All Education Levels");
        HIERARCHY_CUSTOMERS_EDUCATION.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_EDUCATION.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_EDUCATION.getLevels().addAll(List.of(LEVEL_CUSTOMER_EDUCATION));

        HIERARCHY_CUSTOMERS_MARITAL = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_MARITAL.setName("Customers Marital Status");
        HIERARCHY_CUSTOMERS_MARITAL.setHasAll(true);
        HIERARCHY_CUSTOMERS_MARITAL.setAllMemberName("All Marital Statuses");
        HIERARCHY_CUSTOMERS_MARITAL.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_MARITAL.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_MARITAL.getLevels().addAll(List.of(LEVEL_CUSTOMER_MARITAL_STATUS));

        HIERARCHY_GEOGRAPHY = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GEOGRAPHY.setHasAll(true);
        HIERARCHY_GEOGRAPHY.setPrimaryKey(COLUMN_STORE_ID_STORE_RAGGED);
        HIERARCHY_GEOGRAPHY.setQuery(QUERY_STORE_RAGGED);
        HIERARCHY_GEOGRAPHY.getLevels().addAll(List.of(LEVEL_COUNTRY_WITH_NEVER, LEVEL_STATE, LEVEL_CITY_TABLE_COLUMN_STORE_CITY));

        // Initialize dimensions
        DIMENSION_TIME = DimensionFactory.eINSTANCE.createTimeDimension();
        DIMENSION_TIME.setName("Time");
        DIMENSION_TIME.getHierarchies().addAll(List.of(HIERARCHY_TIME, HIERARCHY_TIME2));

        DIMENSION_HR_TIME = DimensionFactory.eINSTANCE.createTimeDimension();
        DIMENSION_HR_TIME.setName("Time");
        DIMENSION_HR_TIME.getHierarchies().addAll(List.of(HIERARCHY_HR_TIME));

        DIMENSION_STORE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE.setName("Store");
        DIMENSION_STORE.getHierarchies().add(HIERARCHY_STORE);

        DIMENSION_STORE_SALES_RAGGED = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_SALES_RAGGED.setName("Store");
        DIMENSION_STORE_SALES_RAGGED.getHierarchies().add(HIERARCHY_STORE_SALES_RAGGED);

        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE.setName("Store");
        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE.getHierarchies().add(HIERARCHY_HR_STORE);

        DIMENSION_PAY_TYPE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PAY_TYPE.setName("Pay Type");
        DIMENSION_PAY_TYPE.getHierarchies().add(HIERARCHY_PAY_TYPE);

        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE.setName("Store Type");
        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE.getHierarchies().add(HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE);

        DIMENSION_STORE_HAS_COFFEE_BAR = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_HAS_COFFEE_BAR.setName("Has coffee bar");
        DIMENSION_STORE_HAS_COFFEE_BAR.getHierarchies().add(HIERARCHY_STORE_HAS_COFFEE_BAR);

        DIMENSION_CUSTOMERS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMERS.setName("Customers");
        DIMENSION_CUSTOMERS.getHierarchies().addAll(List.of(HIERARCHY_CUSTOMER));

        DIMENSION_PRODUCT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PRODUCT.setName("Product");
        DIMENSION_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        DIMENSION_STORE_SIZE_IN_SQFT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_SIZE_IN_SQFT.setName("Store Size in SQFT");
        DIMENSION_STORE_SIZE_IN_SQFT.getHierarchies().add(HIERARCHY_STORE_SIZE_IN_SQFT);

        DIMENSION_PROMOTIONS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PROMOTIONS.setName("Promotions");
        DIMENSION_PROMOTIONS.getHierarchies().add(HIERARCHY_PROMOTIONS);

        DIMENSION_STORE_TYPE_WITH_QUERY_STORE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITH_QUERY_STORE.setName("Store Type");
        DIMENSION_STORE_TYPE_WITH_QUERY_STORE.getHierarchies().add(HIERARCHY_STORE_TYPE);

        DIMENSION_STORE_TYPE_WITHOUT_QUERY = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITHOUT_QUERY.setName("Store Type");
        DIMENSION_STORE_TYPE_WITHOUT_QUERY.getHierarchies().add(HIERARCHY_STORE_TYPE_WITHOUT_TABLE);

        DIMENSION_EDUCATION_LEVEL = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_EDUCATION_LEVEL.setName("Education Level");
        DIMENSION_EDUCATION_LEVEL.getHierarchies().add(HIERARCHY_EDUCATION_LEVEL);

        DIMENSION_GENDER = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GENDER.setName("Gender");
        DIMENSION_GENDER.getHierarchies().add(HIERARCHY_GENDER);

        DIMENSION_MARITAL_STATUS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_MARITAL_STATUS.setName("Marital Status");
        DIMENSION_MARITAL_STATUS.getHierarchies().add(HIERARCHY_MARITAL_STATUS);

        DIMENSION_YEARLY_INCOME = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_YEARLY_INCOME.setName("Yearly Income");
        DIMENSION_YEARLY_INCOME.getHierarchies().add(HIERARCHY_YEARLY_INCOME);

        // Initialize new dimensions
        DIMENSION_WAREHOUSE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_WAREHOUSE.setName("Warehouse");
        DIMENSION_WAREHOUSE.getHierarchies().add(HIERARCHY_WAREHOUSE);

        DIMENSION_PROMOTION_MEDIA = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PROMOTION_MEDIA.setName("Promotion Media");
        DIMENSION_PROMOTION_MEDIA.getHierarchies().add(HIERARCHY_PROMOTION_MEDIA);

        DIMENSION_EMPLOYEE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_EMPLOYEE.setName("Employee");
        DIMENSION_EMPLOYEE.getHierarchies().add(HIERARCHY_EMPLOYEE);

        DIMENSION_DEPARTMENT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEPARTMENT.setName("Department");
        DIMENSION_DEPARTMENT.getHierarchies().add(HIERARCHY_DEPARTMENT);

        DIMENSION_POSITION = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_POSITION.setName("Position");
        DIMENSION_POSITION.getHierarchies().add(HIERARCHY_POSITION);

        DIMENSION_HR_POSITION = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_HR_POSITION.setName("Position");
        DIMENSION_HR_POSITION.getHierarchies().add(HIERARCHY_HR_POSITION);

        DIMENSION_STORE_TYPE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE.setName("Store Type");
        DIMENSION_STORE_TYPE.getHierarchies().add(HIERARCHY_STORE_TYPE);

        DIMENSION_GEOGRAPHY = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GEOGRAPHY.setName("Geography");
        DIMENSION_GEOGRAPHY.getHierarchies().add(HIERARCHY_GEOGRAPHY);

        // Initialize measures
        MEASURE_UNIT_SALES = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES.setName("Unit Sales");
        MEASURE_UNIT_SALES.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES.setFormatString("Standard");

        MEASURE_STORE_SALES = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES.setName("Store Sales");
        MEASURE_STORE_SALES.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES.setFormatString("#,###.00");

        MEASURE_STORE_COST = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST.setName("Store Cost");
        MEASURE_STORE_COST.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST.setFormatString("#,###.00");

        MEASURE_SALES_COUNT = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT.setName("Sales Count");
        MEASURE_SALES_COUNT.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT.setFormatString("#,###");

        MEASURE_CUSTOMER_COUNT = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT.setDistinct(true);

        MEASURE_PROMOTION_SALES = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_PROMOTION_SALES.setName("Promotion Sales");
        MEASURE_PROMOTION_SALES.setColumn(MEASURE_PROMOTION_SALES_COL);
        MEASURE_PROMOTION_SALES.setFormatString("#,###.00");

        // Initialize warehouse measures
        MEASURE_WAREHOUSE_SALES = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_SALES.setName("Warehouse Sales");
        MEASURE_WAREHOUSE_SALES.setColumn(COLUMN_WAREHOUSE_SALES_INVENTORY_FACT);

        MEASURE_WAREHOUSE_STORE_INVOICE = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_STORE_INVOICE.setName("Store Invoice");
        MEASURE_WAREHOUSE_STORE_INVOICE.setColumn(COLUMN_STORE_INVOICE_INVENTORY_FACT);

        MEASURE_WAREHOUSE_SUPPLY_TIME = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_SUPPLY_TIME.setName("Supply Time");
        MEASURE_WAREHOUSE_SUPPLY_TIME.setColumn(COLUMN_SUPPLY_TIME_INVENTORY_FACT);

        MEASURE_WAREHOUSE_PROFIT = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_PROFIT.setName("Warehouse Profit");
        MEASURE_WAREHOUSE_PROFIT.setColumn(MEASURE_WAREHOUSE_PROFIT_COL);

        MEASURE_WAREHOUSE_COST = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_COST.setName("Warehouse Cost");
        MEASURE_WAREHOUSE_COST.setColumn(COLUMN_WAREHOUSE_COST_INVENTORY_FACT);
        //MEASURE_WAREHOUSE_COST.setFormatString("$#,##0.00");

        MEASURE_UNITS_SHIPPED = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_SHIPPED.setName("Units Shipped");
        MEASURE_UNITS_SHIPPED.setColumn(COLUMN_UNITS_SHIPPED_INVENTORY_FACT);
        MEASURE_UNITS_SHIPPED.setFormatString("#.0");

        MEASURE_UNITS_ORDERED = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_ORDERED.setName("Units Ordered");
        MEASURE_UNITS_ORDERED.setColumn(COLUMN_UNITS_ORDERED_INVENTORY_FACT);
        MEASURE_UNITS_ORDERED.setFormatString("#.0");

        // Initialize store measures
        MEASURE_STORE_SQFT = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SQFT.setName("Store Sqft");
        MEASURE_STORE_SQFT.setColumn(COLUMN_STORE_SQFT_STORE);
        MEASURE_STORE_SQFT.setFormatString("#,###");

        MEASURE_GROCERY_SQFT = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_GROCERY_SQFT.setName("Grocery Sqft");
        MEASURE_GROCERY_SQFT.setColumn(COLUMN_GROCERY_SQFT_STORE);
        MEASURE_GROCERY_SQFT.setFormatString("#,###");

        // Initialize HR measures
        MEASURE_ORG_SALARY = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_ORG_SALARY.setName("Org Salary");
        MEASURE_ORG_SALARY.setColumn(COLUMN_SALARY_PAID_SALARY);
        MEASURE_ORG_SALARY.setFormatString("Currency");

        MEASURE_COUNT = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_COUNT.setName("Count");
        MEASURE_COUNT.setColumn(COLUMN_EMPLOYEE_ID_SALARY);
        MEASURE_COUNT.setFormatString("#,#");

        MEASURE_NUMBER_OF_EMPLOYEES = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_NUMBER_OF_EMPLOYEES.setName("Number of Employees");
        MEASURE_NUMBER_OF_EMPLOYEES.setColumn(COLUMN_EMPLOYEE_ID_SALARY);
        MEASURE_NUMBER_OF_EMPLOYEES.setFormatString("#,#");
        MEASURE_NUMBER_OF_EMPLOYEES.setDistinct(true);

        MEASURE_UNIT_SALES_RAGGED = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES_RAGGED.setName("Unit Sales");
        MEASURE_UNIT_SALES_RAGGED.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES_RAGGED.setFormatString("Standard");

        MEASURE_STORE_COST_RAGGED = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST_RAGGED.setName("Store Cost");
        MEASURE_STORE_COST_RAGGED.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST_RAGGED.setFormatString("#,###");

        MEASURE_STORE_SALES_RAGGED = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES_RAGGED.setName("Store Sales");
        MEASURE_STORE_SALES_RAGGED.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES_RAGGED.setFormatString("#,###");

        MEASURE_SALES_COUNT_RAGGED = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT_RAGGED.setName("Sales Count");
        MEASURE_SALES_COUNT_RAGGED.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT_RAGGED.setFormatString("#,###");

        MEASURE_CUSTOMER_COUNT_RAGGED = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT_RAGGED.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT_RAGGED.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT_RAGGED.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT_RAGGED.setDistinct(true);

        MEASURE_SALES_COUNT_PROP = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_SALES_COUNT_PROP.setName("MEMBER_ORDINAL");
        MEASURE_SALES_COUNT_PROP.setValue("1");

        MEASURE_SALES_COUNT_WITH_PROPERTY = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT_WITH_PROPERTY.setName("Sales Count");
        MEASURE_SALES_COUNT_WITH_PROPERTY.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_SALES_COUNT_PROP);

        MEASURE_UNIT_SALES_PROP = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_UNIT_SALES_PROP.setName("MEMBER_ORDINAL");
        MEASURE_UNIT_SALES_PROP.setValue("2");

        MEASURE_UNIT_SALES_MEMBER_ORDINAL = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.setName("Unit Sales");
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.getCalculatedMemberProperties().add(MEASURE_UNIT_SALES_PROP);

        MEASURE_STORE_SALES_PROP = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_STORE_SALES_PROP.setName("MEMBER_ORDINAL");
        MEASURE_STORE_SALES_PROP.setValue("3");

        MEASURE_STORE_SALES_WITH_PROPERTY = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES_WITH_PROPERTY.setName("Store Sales");
        MEASURE_STORE_SALES_WITH_PROPERTY.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_STORE_SALES_PROP);

        MEASURE_STORE_COST_PROP = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_STORE_COST_PROP.setName("MEMBER_ORDINAL");
        MEASURE_STORE_COST_PROP.setValue("6");

        MEASURE_STORE_COST_WITH_PROPERTY = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST_WITH_PROPERTY.setName("Store Cost");
        MEASURE_STORE_COST_WITH_PROPERTY.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST_WITH_PROPERTY.setFormatString("#,###.00");
        MEASURE_STORE_COST_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_STORE_COST_PROP);

        MEASURE_CUSTOMER_COUNT_PROP = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_CUSTOMER_COUNT_PROP.setName("MEMBER_ORDINAL");
        MEASURE_CUSTOMER_COUNT_PROP.setValue("7");

        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_CUSTOMER_COUNT_PROP);

        // Initialize measure groups
        MEASUREGROUP_SALES = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_SALES.getMeasures()
                .addAll(List.of(MEASURE_UNIT_SALES, MEASURE_STORE_COST, MEASURE_STORE_SALES, MEASURE_SALES_COUNT, MEASURE_CUSTOMER_COUNT, MEASURE_PROMOTION_SALES));

        MEASUREGROUP_WAREHOUSE = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_WAREHOUSE.getMeasures().addAll(
                List.of(MEASURE_WAREHOUSE_STORE_INVOICE, MEASURE_WAREHOUSE_SUPPLY_TIME, MEASURE_WAREHOUSE_COST, MEASURE_WAREHOUSE_SALES, MEASURE_UNITS_SHIPPED,
                        MEASURE_UNITS_ORDERED, MEASURE_WAREHOUSE_PROFIT));

        MEASUREGROUP_STORE = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_STORE.getMeasures().addAll(List.of(MEASURE_STORE_SQFT, MEASURE_GROCERY_SQFT));

        MEASUREGROUP_HR = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_HR.getMeasures().addAll(List.of(MEASURE_ORG_SALARY, MEASURE_COUNT, MEASURE_NUMBER_OF_EMPLOYEES));

        MEASUREGROUP_RAGGED = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_RAGGED.getMeasures().addAll(List.of(MEASURE_UNIT_SALES_RAGGED,
        MEASURE_STORE_COST_RAGGED,
        MEASURE_STORE_SALES_RAGGED,
        MEASURE_SALES_COUNT_RAGGED,
        MEASURE_CUSTOMER_COUNT_RAGGED));

        MEASUREGROUP_SALES2 = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_SALES2.getMeasures()
                .addAll(List.of(MEASURE_SALES_COUNT_WITH_PROPERTY, MEASURE_UNIT_SALES_MEMBER_ORDINAL,
                        MEASURE_STORE_SALES_WITH_PROPERTY, MEASURE_STORE_COST_WITH_PROPERTY,
                        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY));

        // Initialize dimension connectors
        CONNECTOR_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_TIME.setOverrideDimensionName("Time");
        CONNECTOR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);

        CONNECTOR_STORE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE.setOverrideDimensionName("Store");
        CONNECTOR_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_STORE.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_STORE_SIZE_IN_SOFT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_SIZE_IN_SOFT.setOverrideDimensionName("Store Size in SQFT");
        CONNECTOR_STORE_SIZE_IN_SOFT.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_STORE_SIZE_IN_SOFT.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_STORE_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE);
        CONNECTOR_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_PROMOTION_MEDIA = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PROMOTION_MEDIA.setOverrideDimensionName("Promotion Media");
        CONNECTOR_PROMOTION_MEDIA.setDimension(DIMENSION_PROMOTION_MEDIA);
        CONNECTOR_PROMOTION_MEDIA.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);

        CONNECTOR_PROMOTIONS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PROMOTIONS.setOverrideDimensionName("Promotions");
        CONNECTOR_PROMOTIONS.setDimension(DIMENSION_PROMOTIONS);
        CONNECTOR_PROMOTIONS.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);

        CONNECTOR_EDUCATION_LEVEL = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_EDUCATION_LEVEL.setOverrideDimensionName("Education Level");
        CONNECTOR_EDUCATION_LEVEL.setDimension(DIMENSION_EDUCATION_LEVEL);
        CONNECTOR_EDUCATION_LEVEL.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_GENDER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_MARITAL_STATUS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_MARITAL_STATUS.setOverrideDimensionName("Marital Status");
        CONNECTOR_MARITAL_STATUS.setDimension(DIMENSION_MARITAL_STATUS);
        CONNECTOR_MARITAL_STATUS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_YEARLY_INCOME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_YEARLY_INCOME.setOverrideDimensionName("Yearly Income");
        CONNECTOR_YEARLY_INCOME.setDimension(DIMENSION_YEARLY_INCOME);
        CONNECTOR_YEARLY_INCOME.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_CUSTOMER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMER.setOverrideDimensionName("Customers");
        CONNECTOR_CUSTOMER.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_CUSTOMER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_PRODUCT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);

        // Initialize warehouse cube connectors
        CONNECTOR_WAREHOUSE_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_TIME.setOverrideDimensionName("Time");
        CONNECTOR_WAREHOUSE_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_WAREHOUSE_TIME.setForeignKey(COLUMN_TIME_ID_INVENTORY_FACT);

        CONNECTOR_WAREHOUSE_STORE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE.setOverrideDimensionName("Store");
        CONNECTOR_WAREHOUSE_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_WAREHOUSE_STORE.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);

        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setOverrideDimensionName("Store Size in SQFT");
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);

        CONNECTOR_WAREHOUSE_STORE_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_WAREHOUSE_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE);
        CONNECTOR_WAREHOUSE_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);

        CONNECTOR_WAREHOUSE_PRODUCT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_WAREHOUSE_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_WAREHOUSE_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_INVENTORY_FACT);

        CONNECTOR_WAREHOUSE_WAREHOUSE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_WAREHOUSE.setOverrideDimensionName("Warehouse");
        CONNECTOR_WAREHOUSE_WAREHOUSE.setDimension(DIMENSION_WAREHOUSE);
        CONNECTOR_WAREHOUSE_WAREHOUSE.setForeignKey(COLUMN_WAREHOUSE_ID_INVENTORY_FACT);

        // Initialize HR cube connectors
        CONNECTOR_HR_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_TIME.setOverrideDimensionName("Time");
        CONNECTOR_HR_TIME.setDimension(DIMENSION_HR_TIME);
        CONNECTOR_HR_TIME.setForeignKey(COLUMN_PAY_DATE_SALARY);

        CONNECTOR_HR_STORE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_STORE.setOverrideDimensionName("Store");
        CONNECTOR_HR_STORE.setDimension(DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE);
        CONNECTOR_HR_STORE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);

        CONNECTOR_HR_PAY_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_PAY_TYPE.setOverrideDimensionName("Pay Type");
        CONNECTOR_HR_PAY_TYPE.setDimension(DIMENSION_PAY_TYPE);
        CONNECTOR_HR_PAY_TYPE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);

        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setOverrideDimensionName("Store Type");
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE);
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);

        CONNECTOR_HR_EMPLOYEE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_EMPLOYEE.setOverrideDimensionName("Employees");
        CONNECTOR_HR_EMPLOYEE.setDimension(DIMENSION_EMPLOYEE);
        CONNECTOR_HR_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);

        CONNECTOR_HR_POSITION = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_POSITION.setOverrideDimensionName("Position");
        CONNECTOR_HR_POSITION.setDimension(DIMENSION_HR_POSITION);
        CONNECTOR_HR_POSITION.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);

        CONNECTOR_HR_DEPARTMENT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_DEPARTMENT.setOverrideDimensionName("Department");
        CONNECTOR_HR_DEPARTMENT.setDimension(DIMENSION_DEPARTMENT);
        CONNECTOR_HR_DEPARTMENT.setForeignKey(COLUMN_DEPARTMENT_ID_SALARY);

        // Initialize Store cube connectors
        CONNECTOR_STORE_STORE_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_STORE_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITHOUT_QUERY);

        CONNECTOR_STORE_STORE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_STORE.setOverrideDimensionName("Store");
        CONNECTOR_STORE_STORE.setDimension(DIMENSION_STORE);

        CONNECTOR_STORE_HAS_COFFEE_BAR = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_HAS_COFFEE_BAR.setOverrideDimensionName("Has coffee bar");
        CONNECTOR_STORE_HAS_COFFEE_BAR.setDimension(DIMENSION_STORE_HAS_COFFEE_BAR);
        // This would need a coffee bar dimension, but for now using store

        CONNECTOR_SALES_RAGGED_STORE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE.setOverrideDimensionName("Store");
        CONNECTOR_SALES_RAGGED_STORE.setDimension(DIMENSION_STORE_SALES_RAGGED);
        CONNECTOR_SALES_RAGGED_STORE.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_GEOGRAPHY = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setOverrideDimensionName("Geography");
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setDimension(DIMENSION_GEOGRAPHY);
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_STORE_SIZE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setOverrideDimensionName("Store Size in SqFt");
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_STORE_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE);
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_TIME.setOverrideDimensionName("Time");
        CONNECTOR_SALES_RAGGED_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_SALES_RAGGED_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);

        CONNECTOR_SALES_2_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_TIME.setOverrideDimensionName("Time");
        CONNECTOR_SALES_2_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_SALES_2_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_PRODUCT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_SALES_RAGGED_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_SALES_RAGGED_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);

        CONNECTOR_SALES_2_PRODUCT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_SALES_2_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_SALES_2_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setOverrideDimensionName("Promotion Media");
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setDimension(DIMENSION_PROMOTION_MEDIA);
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_PROMOTIONS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setOverrideDimensionName("Promotions");
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setDimension(DIMENSION_PROMOTIONS);
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_CUSTOMERS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setOverrideDimensionName("Customers");
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setOverrideDimensionName("Education Level");
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setDimension(DIMENSION_EDUCATION_LEVEL);
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_GENDER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_SALES_RAGGED_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_SALES_RAGGED_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_2_GENDER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_SALES_2_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_SALES_2_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_MARITAL_STATUS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setOverrideDimensionName("Marital Status");
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setDimension(DIMENSION_MARITAL_STATUS);
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CONNECTOR_SALES_RAGGED_YEARLY_INCOME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setOverrideDimensionName("Yearly Income");
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setDimension(DIMENSION_YEARLY_INCOME);
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);

        CALCULATED_MEMBER_PROP0 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP0.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROP0.setValue("$#,##0.00");

        // Initialize calculated members
        CALCULATED_MEMBER_PROFIT = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT.setName("Profit");
        CALCULATED_MEMBER_PROFIT.setFormula("[Measures].[Store Sales] - [Measures].[Store Cost]");
        CALCULATED_MEMBER_PROFIT.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROP0));

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP0 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP0.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP0.setValue("$#,##0.00");

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP1 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP1.setName("MEMBER_ORDINAL");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP1.setValue("18");

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.setName("Profit last Period");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD
                .setFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER), Measures.[Profit])");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.setFormatString("$#,##0.00");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP0,
                CALCULATED_MEMBER_PROFIT_LAST_PERIOD_PROP1));

        CALCULATED_MEMBER_PROFIT_GROWTH_PROP0 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROFIT_GROWTH_PROP0.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROFIT_GROWTH_PROP0.setValue("0.0%");

        CALCULATED_MEMBER_PROFIT_GROWTH = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_GROWTH.setName("Profit Growth");
        CALCULATED_MEMBER_PROFIT_GROWTH.setFormula(
                "([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]");
        CALCULATED_MEMBER_PROFIT_GROWTH.getCalculatedMemberProperties().add(CALCULATED_MEMBER_PROFIT_GROWTH_PROP0);
        CALCULATED_MEMBER_PROFIT_GROWTH.setVisible(true);

        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setName("FORMAT_STRING");
        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setValue("$#,##0.00");

        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setName("Average Warehouse Sale");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE
                .setFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.getCalculatedMemberProperties().add(PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE);

        CALCULATED_MEMBER_EMPLOYEE_SALARY = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setName("Employee Salary");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormatString("Currency");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])");

        CALCULATED_MEMBER_AVG_SALARY = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVG_SALARY.setName("Avg Salary");
        CALCULATED_MEMBER_AVG_SALARY.setFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]");
        CALCULATED_MEMBER_AVG_SALARY.setFormatString("Currency");

        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER = LevelFactory.eINSTANCE.createCalculatedMember();
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setName("Profit Per Unit Shipped");
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setFormula("[Measures].[Profit] / [Measures].[Units Shipped]");
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setFormatString("Currency");

        CALCULATED_MEMBER_PROP1 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP1.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROP1.setValue("$#,##0.00");

        CALCULATED_MEMBER_PROP2 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP2.setName("MEMBER_ORDINAL");
        CALCULATED_MEMBER_PROP2.setValue("4");

        CALCULATED_MEMBER_PROP3 = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP3.setName("MEMBER_ORDINAL");
        CALCULATED_MEMBER_PROP3.setValue("5");

        CALCULATED_MEMBER_PROFIT_WITH_ORDER = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.setName("Profit");
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.setFormula("[Measures].[Store Sales] - [Measures].[Store Cost]");
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROP1, CALCULATED_MEMBER_PROP2));

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2 = LevelFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.setName("Profit last Period");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.setFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.setVisible(false);
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROP3));

        ANNOTATION_SALES1 = RolapMappingFactory.eINSTANCE.createAnnotation();
        ANNOTATION_SALES1.setName("caption.de_DE");
        ANNOTATION_SALES1.setValue("Verkaufen");

        ANNOTATION_SALES2 = RolapMappingFactory.eINSTANCE.createAnnotation();
        ANNOTATION_SALES2.setName("caption.fr_FR");
        ANNOTATION_SALES2.setValue("Ventes");

        ANNOTATION_SALES3 = RolapMappingFactory.eINSTANCE.createAnnotation();
        ANNOTATION_SALES3.setName("description.fr_FR");
        ANNOTATION_SALES3.setValue("Cube des ventes");

        ANNOTATION_SALES4 = RolapMappingFactory.eINSTANCE.createAnnotation();
        ANNOTATION_SALES4.setName("description.de");
        ANNOTATION_SALES4.setValue("Cube Verkaufen");

        ANNOTATION_SALES5 = RolapMappingFactory.eINSTANCE.createAnnotation();
        ANNOTATION_SALES5.setName("description.de_AT");
        ANNOTATION_SALES5.setValue("Cube den Verkaufen");

        NAMED_SET_TOP_SELLERS = DimensionFactory.eINSTANCE.createNamedSet();
        NAMED_SET_TOP_SELLERS.setName("Top Sellers");
        NAMED_SET_TOP_SELLERS.setFormula("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])");

        // Initialize cubes
        CUBE_SALES = CubeFactory.eINSTANCE.createPhysicalCube();
        //CUBE_SALES.setDefaultMeasure(MEASURE_UNIT_SALES);
        CUBE_SALES.setName("Sales");
        CUBE_SALES.setQuery(QUERY_SALES_FACT);
        CUBE_SALES.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_STORE, CONNECTOR_STORE_SIZE_IN_SOFT, CONNECTOR_STORE_TYPE, CONNECTOR_TIME,
                        CONNECTOR_PRODUCT, CONNECTOR_PROMOTION_MEDIA, CONNECTOR_PROMOTIONS, CONNECTOR_CUSTOMER,
                        CONNECTOR_EDUCATION_LEVEL, CONNECTOR_GENDER, CONNECTOR_MARITAL_STATUS, CONNECTOR_YEARLY_INCOME ));
        CUBE_SALES.getMeasureGroups().add(MEASUREGROUP_SALES);
        CUBE_SALES.getAnnotations().addAll(List.of(ANNOTATION_SALES1, ANNOTATION_SALES2, ANNOTATION_SALES3, ANNOTATION_SALES4, ANNOTATION_SALES5));
        CUBE_SALES.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_LAST_PERIOD,
                CALCULATED_MEMBER_PROFIT_GROWTH));

        CUBE_WAREHOUSE = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_WAREHOUSE.setName("Warehouse");
        CUBE_WAREHOUSE.setQuery(QUERY_INVENTORY_FACT);
        CUBE_WAREHOUSE.getDimensionConnectors().addAll(List.of(CONNECTOR_WAREHOUSE_STORE, CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT,
                CONNECTOR_WAREHOUSE_STORE_TYPE, CONNECTOR_WAREHOUSE_TIME,
                CONNECTOR_WAREHOUSE_PRODUCT, CONNECTOR_WAREHOUSE_WAREHOUSE));
        CUBE_WAREHOUSE.getMeasureGroups().add(MEASUREGROUP_WAREHOUSE);
        CUBE_WAREHOUSE.getCalculatedMembers().add(CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE);
        CUBE_WAREHOUSE.getNamedSets().add(NAMED_SET_TOP_SELLERS);


        CUBE_STORE = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_STORE.setName("Store");
        CUBE_STORE.setQuery(QUERY_STORE);
        CUBE_STORE.getDimensionConnectors().addAll(List.of(CONNECTOR_STORE_STORE_TYPE, CONNECTOR_STORE_STORE, CONNECTOR_STORE_HAS_COFFEE_BAR));
        CUBE_STORE.getMeasureGroups().add(MEASUREGROUP_STORE);

        CUBE_HR = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_HR.setName("HR");
        CUBE_HR.setQuery(QUERY_SALARY);
        CUBE_HR.getDimensionConnectors().addAll(List.of(CONNECTOR_HR_TIME, CONNECTOR_HR_STORE, CONNECTOR_HR_PAY_TYPE, CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE,
                CONNECTOR_HR_POSITION, CONNECTOR_HR_DEPARTMENT, CONNECTOR_HR_EMPLOYEE));
        CUBE_HR.getMeasureGroups().add(MEASUREGROUP_HR);
        CUBE_HR.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_EMPLOYEE_SALARY, CALCULATED_MEMBER_AVG_SALARY));

        CUBE_SALES_RAGGED = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES_RAGGED.setName("Sales Ragged");
        CUBE_SALES_RAGGED.setQuery(QUERY_SALES_FACT);
        CUBE_SALES_RAGGED.getMeasureGroups().add(MEASUREGROUP_RAGGED);
        CUBE_SALES_RAGGED.getDimensionConnectors().addAll(List.of(
            CONNECTOR_SALES_RAGGED_STORE,
            CONNECTOR_SALES_RAGGED_GEOGRAPHY,
            CONNECTOR_SALES_RAGGED_STORE_SIZE,
            CONNECTOR_SALES_RAGGED_STORE_TYPE,
            CONNECTOR_SALES_RAGGED_TIME,
            CONNECTOR_SALES_RAGGED_PRODUCT,
            CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA,
            CONNECTOR_SALES_RAGGED_PROMOTIONS,
            CONNECTOR_SALES_RAGGED_CUSTOMERS,
            CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL,
            CONNECTOR_SALES_RAGGED_GENDER,
            CONNECTOR_SALES_RAGGED_MARITAL_STATUS,
            CONNECTOR_SALES_RAGGED_YEARLY_INCOME
        ));

        CUBE_SALES_2 = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES_2.setName("Sales 2");
        CUBE_SALES_2.setQuery(QUERY_SALES_FACT);
        CUBE_SALES_2.getMeasureGroups().add(MEASUREGROUP_SALES2);
        CUBE_SALES_2.getDimensionConnectors().addAll(List.of(
            CONNECTOR_SALES_2_TIME,
            CONNECTOR_SALES_2_PRODUCT,
            CONNECTOR_SALES_2_GENDER
        ));
        CUBE_SALES_2.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_PROFIT_WITH_ORDER, CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2));
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES = CubeFactory.eINSTANCE.createVirtualCube();
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.setName("Warehouse and Sales");
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.getDimensionConnectors().addAll(List.of(
                CONNECTOR_CUSTOMER,
                CONNECTOR_EDUCATION_LEVEL,
                CONNECTOR_GENDER,
                CONNECTOR_MARITAL_STATUS,
                CONNECTOR_PRODUCT,
                CONNECTOR_PROMOTION_MEDIA,
                CONNECTOR_PROMOTIONS,
                CONNECTOR_STORE,
                CONNECTOR_TIME,
                CONNECTOR_YEARLY_INCOME,
                CONNECTOR_WAREHOUSE_WAREHOUSE
        ));
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.getReferencedMeasures().addAll(List.of(
                MEASURE_SALES_COUNT, MEASURE_STORE_COST, MEASURE_STORE_SALES, MEASURE_UNIT_SALES,
                MEASURE_WAREHOUSE_STORE_INVOICE, MEASURE_WAREHOUSE_SUPPLY_TIME, MEASURE_UNITS_ORDERED,
                MEASURE_UNITS_SHIPPED, MEASURE_WAREHOUSE_COST, MEASURE_WAREHOUSE_PROFIT, MEASURE_WAREHOUSE_SALES));
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.getReferencedCalculatedMembers().addAll(List.of(
                CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_GROWTH, CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE));
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.getCalculatedMembers().add(PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER);


        // Initialize access grants
        GRANT_DATABASE_SCHEMA_ADMIN = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_ADMIN.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_DATABASE_SCHEMA_HR = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_HR.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_DATABASE_SCHEMA_MANAGER = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_MANAGER.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_ADMIN_CATALOG = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_ADMIN_CATALOG.setCatalogAccess(CatalogAccess.ALL);
        GRANT_ADMIN_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_ADMIN);

        GRANT_NO_HR_CATALOG = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_NO_HR_CATALOG.setCatalogAccess(CatalogAccess.ALL);
        GRANT_NO_HR_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_HR);

        GRANT_NO_HR_CUBE = OlapFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_NO_HR_CUBE.setCube(CUBE_HR);
        GRANT_NO_HR_CUBE.setCubeAccess(CubeAccess.NONE);

        GRANT_NO_HR_CATALOG.getCubeGrants().add(GRANT_NO_HR_CUBE);

        GRANT_CALIFORNIA_MANAGER_CATALOG = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_CALIFORNIA_MANAGER_CATALOG.setCatalogAccess(CatalogAccess.NONE);
        GRANT_CALIFORNIA_MANAGER_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_MANAGER);

        MEMBER_GRANT_STORE1 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_STORE1.setMember("[Store].[USA].[CA]");
        MEMBER_GRANT_STORE1.setMemberAccess(MemberAccess.ALL);

        MEMBER_GRANT_STORE2 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_STORE2.setMember("[Store].[USA].[CA].[Los Angeles]");
        MEMBER_GRANT_STORE2.setMemberAccess(MemberAccess.NONE);

        GRANT_HIERARCHY_STORE = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_STORE.setHierarchy(HIERARCHY_STORE);
        GRANT_HIERARCHY_STORE.setHierarchyAccess(HierarchyAccess.CUSTOM);
        GRANT_HIERARCHY_STORE.setTopLevel(LEVEL_STORE_COUNTRY);
        GRANT_HIERARCHY_STORE.getMemberGrants().addAll(List.of(MEMBER_GRANT_STORE1, MEMBER_GRANT_STORE2));

        MEMBER_GRANT_CUSTOMERS1 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_CUSTOMERS1.setMember("[Customers].[USA].[CA]");
        MEMBER_GRANT_CUSTOMERS1.setMemberAccess(MemberAccess.ALL);

        MEMBER_GRANT_CUSTOMERS2 = OlapFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_CUSTOMERS2.setMember("[Customers].[USA].[CA].[Los Angeles]");
        MEMBER_GRANT_CUSTOMERS2.setMemberAccess(MemberAccess.NONE);

        GRANT_HIERARCHY_CUSTOMERS = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_CUSTOMERS.setHierarchy(HIERARCHY_CUSTOMER);
        GRANT_HIERARCHY_CUSTOMERS.setHierarchyAccess(HierarchyAccess.CUSTOM);
        GRANT_HIERARCHY_CUSTOMERS.setTopLevel(LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE);
        GRANT_HIERARCHY_CUSTOMERS.setBottomLevel(LEVEL_CITY_TABLE_COLUMN_CITY);
        GRANT_HIERARCHY_CUSTOMERS.getMemberGrants().addAll(List.of(MEMBER_GRANT_CUSTOMERS1, MEMBER_GRANT_CUSTOMERS2));

        GRANT_HIERARCHY_GENDER = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_GENDER.setHierarchy(HIERARCHY_GENDER);
        GRANT_HIERARCHY_GENDER.setHierarchyAccess(HierarchyAccess.NONE);

        GRANT_CALIFORNIA_MANAGER_SALES_CUBE = OlapFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCube(CUBE_SALES);
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCubeAccess(CubeAccess.ALL);
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.getHierarchyGrants().addAll(List.of(
                GRANT_HIERARCHY_STORE,
                GRANT_HIERARCHY_CUSTOMERS,
                GRANT_HIERARCHY_GENDER));

        GRANT_CALIFORNIA_MANAGER_CATALOG.getCubeGrants().add(GRANT_CALIFORNIA_MANAGER_SALES_CUBE);

        // Initialize access roles
        ROLE_ADMINISTRATOR = CommonFactory.eINSTANCE.createAccessRole();
        ROLE_ADMINISTRATOR.setName("Administrator");
        ROLE_ADMINISTRATOR.getAccessCatalogGrants().add(GRANT_ADMIN_CATALOG);

        ROLE_NO_HR_CUBE = CommonFactory.eINSTANCE.createAccessRole();
        ROLE_NO_HR_CUBE.setName("No HR Cube");
        ROLE_NO_HR_CUBE.getAccessCatalogGrants().add(GRANT_NO_HR_CATALOG);

        ROLE_CALIFORNIA_MANAGER = CommonFactory.eINSTANCE.createAccessRole();
        ROLE_CALIFORNIA_MANAGER.setName("California manager");
        ROLE_CALIFORNIA_MANAGER.getAccessCatalogGrants().add(GRANT_CALIFORNIA_MANAGER_CATALOG);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_FOODMART = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        //DATABASE_SCHEMA_FOODMART.setName("foodmart");
        DATABASE_SCHEMA_FOODMART.getOwnedElement()
                .addAll(List.of(TABLE_SALES_FACT, TABLE_TIME, TABLE_STORE, TABLE_CUSTOMER, TABLE_PRODUCT,
                        TABLE_WAREHOUSE, TABLE_INVENTORY_FACT, TABLE_PROMOTION, TABLE_EMPLOYEE, TABLE_DEPARTMENT,
                        TABLE_POSITION, TABLE_SALARY, TABLE_EMPLOYEE_CLOSURE, TABLE_PRODUCT_CLASS, TABLE_AGG_C_SPECIAL_SALES_FACT_1997,
                        TABLE_AGG_L_05_SALES_FACT, TABLE_AGG_L_03_SALES_FACT, TABLE_AGG_PL_01_SALES_FACT, TABLE_AGG_G_MS_PCAT_SALES_FACT,
                        TABLE_AGG_C_14_SALES_FACT, TABLE_AGG_C_10_SALES_FACT_1997,
                        TABLE_TIME_BY_DAY, TABLE_STORE_RAGGED ));

        GRANT_DATABASE_SCHEMA_ADMIN.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);
        GRANT_DATABASE_SCHEMA_HR.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);
        GRANT_DATABASE_SCHEMA_MANAGER.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);

        CATALOG_FOODMART = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG_FOODMART.setName("FoodMart");
        CATALOG_FOODMART.getDbschemas().add(DATABASE_SCHEMA_FOODMART);
        CATALOG_FOODMART.getCubes().addAll(List.of(CUBE_SALES, CUBE_WAREHOUSE, CUBE_STORE, CUBE_HR, CUBE_SALES_RAGGED, CUBE_SALES_2, CUBE_VIRTIAL_WAREHOUSE_AND_SALES));
        CATALOG_FOODMART.getAccessRoles().addAll(List.of(ROLE_CALIFORNIA_MANAGER, ROLE_NO_HR_CUBE, ROLE_ADMINISTRATOR));

        // Add documentation






    }

    @Override
    public Catalog get() {
        return CATALOG_FOODMART;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("FoodMart Database", foodMartBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, DATABASE_SCHEMA_FOODMART, 0),
                        new DocSection("Sales Fact Query", querySalesBody, 1, 2, 0, QUERY_SALES_FACT, 0),
                        new DocSection("Inventory Fact Query", queryIventoryFactBody, 1, 3, 0, QUERY_INVENTORY_FACT, 0),
                        new DocSection("Store Query", queryStoreBody, 1, 4, 0, QUERY_STORE, 0),
                        new DocSection("Customer Query", queryCustomerBody, 1, 5, 0, QUERY_CUSTOMER, 0),
                        new DocSection("Product Query", queryProductBody, 1, 6, 0, QUERY_PRODUCT, 0),
                        new DocSection("Warehouse Query", queryWarehouseBody, 1, 7, 0, QUERY_WAREHOUSE, 0),
                        new DocSection("Promotion Query", queryPromotionBody, 1, 8, 0, QUERY_PROMOTION, 0),
                        new DocSection("Employee Query", queryEmployeeBody, 1, 9, 0, QUERY_EMPLOYEE, 0),
                        new DocSection("Departament Query", queryDepartamentBody, 1, 10, 0, QUERY_DEPARTMENT, 0),
                        new DocSection("Position Query", queryPositionBody, 1, 11, 0, QUERY_POSITION, 0),
                        new DocSection("Salary Query", querySalaryBody, 1, 12, 0, QUERY_SALARY, 0),
                        new DocSection("Employee Closure Query", queryEmployeeClosureBody, 1, 13, 0, QUERY_EMPLOYEE_CLOSURE, 0),
                        new DocSection("Product Class Query", queryProductClassBody, 1, 14, 0, QUERY_PRODUCT_CLASS, 0),
                        new DocSection("Time By Day Query", queryTimeByDayBody, 1, 15, 0, QUERY_TIME_BY_DAY, 0),
                        new DocSection("Store Ragged Query", queryStoreRaggedBody, 1, 16, 0, QUERY_STORE_RAGGED, 0),
                        new DocSection("Sales Cube", salesCubeBody, 1, 17, 0, CUBE_SALES, 0),
                        new DocSection("Warehouse Cube", warehouseCubeBody, 1, 18, 0, CUBE_WAREHOUSE, 0),
                        new DocSection("Store Cube", storeCubeBody, 1, 19, 0, CUBE_STORE, 0),
                        new DocSection("HR Cube", hrCubeBody, 1, 20, 0, CUBE_HR, 0),
                        new DocSection("Sales Ragged Cube", salesRaggedCubeBody, 1, 21, 0, CUBE_SALES_RAGGED, 0),
                        new DocSection("Sales2 Cube", sales2CubeBody, 1, 22, 0, CUBE_SALES_2, 0),
                        new DocSection("Warehouse and Sales Cube", warehouseSalesCubeBody, 1, 22, 0, CUBE_VIRTIAL_WAREHOUSE_AND_SALES, 0),
                        new DocSection("Time Dimension", timeBody, 1, 23, 0, DIMENSION_TIME, 0),
                        new DocSection("Time Dimension", timeBody, 1, 24, 0, DIMENSION_HR_TIME, 0),
                        new DocSection("Store Dimension", storeBody, 1, 25, 0, DIMENSION_STORE, 0),
                        new DocSection("Store Dimension", storeBody, 1, 26, 0, DIMENSION_STORE_SALES_RAGGED, 0),
                        new DocSection("Store Dimension", storeBody, 1, 27, 0, DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE, 0),
                        new DocSection("Pay Type Dimension", payTypeBody, 1, 28, 0, DIMENSION_PAY_TYPE, 0),
                        new DocSection("Store Type Dimension", storeTypeBody, 1, 29, 0, DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE, 0),
                        new DocSection("Store Type Dimension", storeTypeBody, 1, 30, 0, DIMENSION_STORE_TYPE_WITH_QUERY_STORE, 0),
                        new DocSection("Store Type Dimension", storeTypeBody, 1, 31, 0, DIMENSION_STORE_TYPE_WITHOUT_QUERY, 0),
                        new DocSection("Store Type Dimension", storeTypeBody, 1, 32, 0, DIMENSION_STORE_TYPE, 0),
                        new DocSection("Customers Dimension", customersBody, 1, 33, 0, DIMENSION_CUSTOMERS, 0),
                        new DocSection("Product Dimension", productBody, 1, 34, 0, DIMENSION_PRODUCT, 0),
                        new DocSection("Store Size Dimension", storeSizeBody, 1, 35, 0, DIMENSION_STORE_SIZE_IN_SQFT, 0),
                        new DocSection("Promotions Dimension", promotionsBody, 1, 36, 0, DIMENSION_PROMOTIONS, 0),
                        new DocSection("Education Level Dimension", educationLevelBody, 1, 37, 0, DIMENSION_EDUCATION_LEVEL, 0),
                        new DocSection("Gender Dimension", genderBody, 1, 38, 0, DIMENSION_GENDER, 0),
                        new DocSection("Marital Status Dimension", maritalStatusBody, 1, 39, 0, DIMENSION_MARITAL_STATUS, 0),
                        new DocSection("Yearly Income Dimension", yearlyIncomeBody, 1, 40, 0, DIMENSION_YEARLY_INCOME, 0),
                        new DocSection("Has coffee bar Dimension", hasCoffeeBarBody, 1, 41, 0, DIMENSION_STORE_HAS_COFFEE_BAR, 0),
                        new DocSection("Geography Dimension", geographyBody, 1, 42, 0, DIMENSION_GEOGRAPHY, 0),
                        new DocSection("Warehouse Dimension", warehouseBody, 1, 43, 0, DIMENSION_WAREHOUSE, 0),
                        new DocSection("Promotion Media Dimension", promotionMediaBody, 1, 44, 0, DIMENSION_PROMOTION_MEDIA, 0),
                        new DocSection("Employee Dimension", employeeBody, 1, 45, 0, DIMENSION_EMPLOYEE, 0),
                        new DocSection("Department Dimension", departmentBody, 1, 46, 0, DIMENSION_DEPARTMENT, 0),
                        new DocSection("Position Dimension", positionBody, 1, 47, 0, DIMENSION_POSITION, 0),
                        new DocSection("Position Dimension", positionBody, 1, 48, 0, DIMENSION_HR_POSITION, 0),
                        new DocSection("Pay Type Hierarchy", "The Pay Type provides pay type analysis for employee.", 1, 55, 0, HIERARCHY_PAY_TYPE, 0),
                        new DocSection("Store Type Hierarchy", "The Store Type provides store type analysis for store.", 1, 56, 0, HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE, 0),
                        new DocSection("Gender Hierarchy", "The Gender provides customer analysis by gender identity.", 1, 59, 0, HIERARCHY_CUSTOMERS_GENDER, 0),
                        new DocSection("Store Size Hierarchy", "The Store Size provides store analysis by size.", 1, 61, 0, HIERARCHY_STORE_SIZE_IN_SQFT, 0),
                        new DocSection("Promotions Hierarchy", "The Promotions provides sales analysis by promotions.", 1, 62, 0, HIERARCHY_PROMOTIONS, 0),
                        new DocSection("Store Type Hierarchy", "The Store Type provides store analysis by type.", 1, 63, 0, HIERARCHY_STORE_TYPE, 0),
                        new DocSection("Store Type Hierarchy", "The Store Type provides store analysis by type.", 1, 64, 0, HIERARCHY_STORE_TYPE_WITHOUT_TABLE, 0),
                        new DocSection("Education Level", "The Education Level provides customer analysis by education level.", 1, 65, 0, HIERARCHY_EDUCATION_LEVEL, 0),
                        new DocSection("Gender Hierarchy", "The Gender provides customer analysis by gender identity.", 1, 65, 0, HIERARCHY_GENDER, 0),
                        new DocSection("Marital Status Hierarchy", "The Marital Status provides customer analysis by marital status.", 1, 66, 0, HIERARCHY_MARITAL_STATUS, 0),
                        new DocSection("Marital Status Hierarchy", "The Marital Status provides customer analysis by marital status.", 1, 67, 0, HIERARCHY_MARITAL_STATUS, 0),
                        new DocSection("Yerly Income Hierarchy", "The Yerly Income provides customer analysis by yerly income.", 1, 68, 0, HIERARCHY_YEARLY_INCOME, 0),
                        new DocSection("Has Coffe Bar Hierarchy", "The Has Coffe Bar provides store analysis by availability сoffe bar.", 1, 69, 0, HIERARCHY_STORE_HAS_COFFEE_BAR, 0),
                        new DocSection("Promotion Media Hierarchy", "The Promotion Media provides sales analysis by promotion media type.", 1, 74, 1, HIERARCHY_PROMOTION_MEDIA, 0),
                        new DocSection("Employee Hierarchy", "The Employee provides organisation structure analysis by employees.", 1, 75, 0, HIERARCHY_EMPLOYEE, 0),
                        new DocSection("Department Hierarchy", "The Department provides sales analysis by departoment of employees.", 1, 76, 0, HIERARCHY_DEPARTMENT, 0),
                        new DocSection("Position Hierarchy", "The Position provides employee analysis by role and position.", 1, 77, 0, HIERARCHY_POSITION, 0),
                        new DocSection("Position Hierarchy", "The Position provides employee analysis by role and position.", 1, 78, 0, HIERARCHY_HR_POSITION, 0),
                        new DocSection("Customers Education Hierarchy", "The Customers Education provides customer analysis by education level.", 1, 79, 0, HIERARCHY_CUSTOMERS_EDUCATION, 0),
                        new DocSection("Customers Marital Status", "The Customers Marital Status provides customer analysis by marital status.", 1, 80, 0, HIERARCHY_CUSTOMERS_MARITAL, 0),
                        new DocSection("Year Level", "Year level represents year for sales analysis.", 1, 81, 0, LEVEL_YEAR, 0),
                        new DocSection("Quarter Level", "Quarter level represents quarter for sales analysis.", 1, 82, 0, LEVEL_QUARTER, 0),
                        new DocSection("Month Level", "Month level represents month for sales analysis.", 1, 83, 0, LEVEL_MONTH, 0),
                        new DocSection("Month Level", "Month level represents month for sales analysis.", 1, 84, 0, LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR, 0),
                        new DocSection("Week Level", "Week level represents week for sales analysis.", 1, 85, 0, LEVEL_WEEK, 0),
                        new DocSection("Day Level", "Day level represents date for sales analysis.", 1, 86, 0, LEVEL_DAY, 0),
                        new DocSection("Store Country Level", "Store Country level represents country for store.", 1, 87, 0, LEVEL_STORE_COUNTRY, 0),
                        new DocSection("Store State Level", "Store State level represents state for store.", 1, 88, 0, LEVEL_STORE_STATE, 0),
                        new DocSection("Store State Level", "Store City level represents city for store.", 1, 89, 0, LEVEL_STORE_CITY, 0),
                        new DocSection("Store State Level", "Store Name level represents name of store.", 1, 90, 0, LEVEL_STORE_NAME, 0),
                        new DocSection("Has coffee bar Level", "Has coffee bar level represents tag that store has coffe bar.", 1, 91, 0, LEVEL_STORE_HAS_COFFEE_BAR, 0),
                        new DocSection("Customer Country Level", "Customer Country level represents country for customer.", 1, 92, 0, LEVEL_CUSTOMER_COUNTRY, 0),
                        new DocSection("Customer State Level", "Customer State level represents state for customer.", 1, 93, 0, LEVEL_CUSTOMER_STATE, 0),
                        new DocSection("Customer City Level", "Customer City level represents city for customer.", 1, 94, 0, LEVEL_CUSTOMER_CITY, 0),
                        new DocSection("Customer Name Level", "Customer Name level represents name for customer.", 1, 95, 0, LEVEL_CUSTOMER_NAME, 0),
                        new DocSection("Customer Gender Level", "Customer Gender level represents gender identification for customer.", 1, 96, 0, LEVEL_CUSTOMER_GENDER, 0),
                        new DocSection("Product Family Level", "Product Family level represents family of product.", 1, 97, 0, LEVEL_PRODUCT_FAMILY, 0),
                        new DocSection("Product Departament Level", "Product Departament level represents departament of product.", 1, 98, 0, LEVEL_PRODUCT_FAMILY, 0),
                        new DocSection("Product Category Level", "Product Category level represents category of product.", 1, 99, 0, LEVEL_PRODUCT_CATEGORY, 0),
                        new DocSection("Product Category Level", "Product Subcategory level represents subcategory of product.", 1, 100, 0, LEVEL_PRODUCT_SUBCATEGORY, 0),
                        new DocSection("Product Brand Level", "Product Brand level represents brand of product.", 1, 101, 0, LEVEL_PRODUCT_BRAND, 0),
                        new DocSection("Product Name Level", "Product Name level represents name of product.", 1, 102, 0, LEVEL_PRODUCT_NAME, 0),
                        new DocSection("Promotions Level", "Promotions level represents promotions of sales.", 1, 103, 0, LEVEL_PROMOTION_NAME, 0),
                        new DocSection("Store Sqft", "Store Sqft level represents sqft of store.", 1, 104, 0, LEVEL_STORE_SQFT, 0),
                        new DocSection("Store Type", "Store Type level represents type of store.", 1, 105, 0, LEVEL_STORE_TYPE_WITHOUT_TABLE, 0),
                        new DocSection("Education Level", "Education Level level represents education level of customer.", 1, 106, 0, LEVEL_EDUCATION, 0),
                        new DocSection("Gender", "Gender Level level represents gender identification of customer.", 1, 107, 0, LEVEL_GENDER, 0),
                        new DocSection("Marital Status", "Marital Status level represents marital status of customer.", 1, 108, 0, LEVEL_MARITAL_STATUS, 0),
                        new DocSection("Yearly Income", "Yearly Income level represents yearly income of customer.", 1, 109, 0, LEVEL_YEARLY_INCOME, 0),
                        new DocSection("Pay Type", "Pay Type level represents pay type of sales.", 1, 110, 0, LEVEL_PAY_TYPE, 0),
                        new DocSection("Store Type", "Store Type level represents type of store.", 1, 111, 0, LEVEL_STORE_TYPE, 0),
                        new DocSection("Store Country", "Store Country level represents country of store.", 1, 112, 0, LEVEL_STORE_COUNTRY_WITH_NEVER, 0),
                        new DocSection("Store City", "Store City level represents city of store.", 1, 113, 0, LEVEL_STORE_CYTY_IF_PARENTS_NAME, 0),
                        new DocSection("Store City", "Store City level represents city of store. with property hide member if blank name", 1, 114, 0, LEVEL_STORE_CYTY_IF_BLANK_NAME, 0),
                        new DocSection("Store Name", "Store Name level represents name of store. with property hide member if never", 1, 115, 0, LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER, 0),
                        new DocSection("Country", "Country level represents name of customer.", 1, 116, 0, LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, 0),
                        new DocSection("State Province", "State Province level represents state province of customer.", 1, 117, 0, LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE, 0),
                        new DocSection("City", "City level represents city of customer.", 1, 118, 0, LEVEL_CITY_TABLE_COLUMN_CITY, 0),
                        new DocSection("Country", "Country level represents name of warehouse.", 1, 120, 0, LEVEL_WAREHOUSE_COUNTRY, 0),
                        new DocSection("State", "State level represents state of warehouse.", 1, 121, 0, LEVEL_WAREHOUSE_STATE, 0),
                        new DocSection("City", "City level represents city of warehouse.", 1, 122, 0, LEVEL_WAREHOUSE_CITY, 0),
                        new DocSection("Warehouse Name", "Warehouse Name level represents name of warehouse.", 1, 123, 0, LEVEL_WAREHOUSE_NAME, 0),
                        new DocSection("Media Type", "Media Type level represents promotions media type of sales.", 1, 124, 0, LEVEL_PROMOTION_MEDIA, 0),
                        new DocSection("Management Role", "Management Role level represents role of employee.", 1, 125, 0, LEVEL_EMPLOYEE_MANAGEMENT_ROLE, 0),
                        new DocSection("Position Title", "Position Title level represents position title of employee.", 1, 126, 0, LEVEL_EMPLOYEE_POSITION, 0),
                        new DocSection("Position ID", "Position ID level represents position ID of employee.", 1, 127, 0, LEVEL_EMPLOYEE_DEPARTMENT, 0),
                        new DocSection("Employee Name", "Employee Name level represents full name of employee.", 1, 128, 0, LEVEL_EMPLOYEE_FULL_NAME, 0),
                        new DocSection("Employee Name", "Employee Name level represents full name of employee.", 1, 129, 0, LEVEL_EMPLOYEE_FULL_NAME, 0),
                        new DocSection("Department Description", "Department Description level represents department description of sales.", 1, 130, 0, LEVEL_DEPARTMENT_DESCRIPTION, 0),
                        new DocSection("Position Title", "Position Title level represents position title of employee.", 1, 131, 0, LEVEL_POSITION_TITLE, 0),
                        new DocSection("Management Role", "Management Role level represents management role of employee.", 1, 132, 0, LEVEL_MANAGEMENT_ROLE, 0),
                        new DocSection("Position Title", "Position Title level represents position title of employee.", 1, 133, 0, LEVEL_HR_POSITION_TITLE, 0),
                        new DocSection("Education Level", "Education Level level represents education level of customer.", 1, 134, 0, LEVEL_CUSTOMER_EDUCATION, 0),
                        new DocSection("Marital Status", "Marital Status level represents marital status of customer.", 1, 135, 0, LEVEL_CUSTOMER_MARITAL_STATUS, 0),
                        new DocSection("Employee Id", "Employee Id level represents Id of employee.", 1, 136, 0, LEVEL_EMPLOYEE_ID, 0),
                        new DocSection("Country", "Country level represents country of regged store.", 1, 137, 0, LEVEL_COUNTRY_WITH_NEVER, 0),
                        new DocSection("State", "State level represents state of regged store.", 1, 138, 0, LEVEL_STATE, 0),
                        new DocSection("City", "City level represents city of regged store.", 1, 139, 0, LEVEL_CITY_TABLE_COLUMN_STORE_CITY, 0),
                        new DocSection("Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation.", 1, 140, 0, MEASURE_UNIT_SALES, 0),
                        new DocSection("Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation.", 1, 141, 0, MEASURE_STORE_SALES, 0),
                        new DocSection("Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation.", 1, 142, 0, MEASURE_STORE_COST, 0),
                        new DocSection("Store Count", "Measure Store Count use sales_fact_1997 table product_id column with count aggregation.", 1, 143, 0, MEASURE_SALES_COUNT, 0),
                        new DocSection("Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation.", 1, 144, 0, MEASURE_CUSTOMER_COUNT, 0),
                        new DocSection("Promotion Sales", "Measure Promotion Sales use (case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end) expression with sum aggregation.", 1, 145, 0, MEASURE_PROMOTION_SALES, 0),
                        new DocSection("Warehouse Sales", "Measure Warehouse Sales use inventory_fact_1997 table warehouse_sales column with sum aggregation.", 1, 146, 0, MEASURE_WAREHOUSE_SALES, 0),
                        new DocSection("Store Invoice", "Measure Store Invoice use inventory_fact_1997 table store_invoice column with sum aggregation.", 1, 147, 0, MEASURE_WAREHOUSE_STORE_INVOICE, 0),
                        new DocSection("Supply Time", "Measure Supply Time use inventory_fact_1997 table supply_time column with sum aggregation.", 1, 148, 0, MEASURE_WAREHOUSE_SUPPLY_TIME, 0),
                        new DocSection("Warehouse Profit", "Measure Warehouse Profit use warehouse_sales - warehouse_cost expression with sum aggregation.", 1, 149, 0, MEASURE_WAREHOUSE_PROFIT, 0),
                        new DocSection("Warehouse Cost", "Measure Warehouse Cost use inventory_fact_1997 table warehouse_cost column with sum aggregation.", 1, 150, 0, MEASURE_WAREHOUSE_COST, 0),
                        new DocSection("Units Shipped", "Measure Units Shipped use inventory_fact_1997 table units_shipped column with sum aggregation.", 1, 151, 0, MEASURE_UNITS_SHIPPED, 0),
                        new DocSection("Units Ordered", "Measure Units Ordered use inventory_fact_1997 table units_ordered column with sum aggregation.", 1, 152, 0, MEASURE_UNITS_ORDERED, 0),
                        new DocSection("Store Sqft", "Measure Store Sqft use store table store_sqft column with sum aggregation.", 1, 153, 0, MEASURE_STORE_SQFT, 0),
                        new DocSection("Grocery Sqft", "Measure Grocery Sqft use store table grocery_sqft column with sum aggregation.", 1, 154, 0, MEASURE_GROCERY_SQFT, 0),
                        new DocSection("Org Salary", "Measure Warehouse Cost use salary table salary_paid column with sum aggregation.", 1, 155, 0, MEASURE_ORG_SALARY, 0),
                        new DocSection("Count", "Measure Count use salary table employee_id column with count aggregation.", 1, 156, 0, MEASURE_COUNT, 0),
                        new DocSection("Count", "Measure Count use salary table employee_id column with count aggregation.", 1, 157, 0, MEASURE_NUMBER_OF_EMPLOYEES, 0),
                        new DocSection("Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation.", 1, 158, 0, MEASURE_UNIT_SALES_RAGGED, 0),
                        new DocSection("Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation.", 1, 159, 0, MEASURE_STORE_COST_RAGGED, 0),
                        new DocSection("Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation.", 1, 160, 0, MEASURE_STORE_SALES_RAGGED, 0),
                        new DocSection("Sales Count", "Measure Sales Count use sales_fact_1997 table product_id column with count aggregation.", 1, 161, 0, MEASURE_SALES_COUNT_RAGGED, 0),
                        new DocSection("Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation.", 1, 162, 0, MEASURE_CUSTOMER_COUNT_RAGGED, 0),
                        new DocSection("Sales Count", "Measure Sales Count use sales_fact_1997 table product_id column with count aggregation. with MEMBER_ORDINAL property", 1, 163, 0, MEASURE_SALES_COUNT_WITH_PROPERTY, 0),
                        new DocSection("Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation. with MEMBER_ORDINAL property", 1, 164, 0, MEASURE_UNIT_SALES_MEMBER_ORDINAL, 0),
                        new DocSection("Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation. with MEMBER_ORDINAL property", 1, 165, 0, MEASURE_STORE_SALES_WITH_PROPERTY, 0),
                        new DocSection("Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation. with MEMBER_ORDINAL property", 1, 165, 0, MEASURE_STORE_COST_WITH_PROPERTY, 0),
                        new DocSection("Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation. with MEMBER_ORDINAL property", 1, 166, 0, MEASURE_CUSTOMER_COUNT_WITH_PROPERTY, 0),
                        new DocSection("Administrator Role", roleAdministratorBody, 1, 167, 0, ROLE_ADMINISTRATOR, 0),
                        new DocSection("California manager Role", roleCaliforniaManagerBody, 1, 168, 0, ROLE_CALIFORNIA_MANAGER, 0),
                        new DocSection("No HR Cube Role", roleNoHRCubeBody, 1, 169, 0, ROLE_NO_HR_CUBE, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
