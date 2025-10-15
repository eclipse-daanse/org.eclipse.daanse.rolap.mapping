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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Annotation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.4", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

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
    public static final SQLExpressionColumn MEASURE_PROMOTION_SALES_COL;
    public static final SqlStatement MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1;
    public static final SqlStatement MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2;
    public static final SQLExpressionColumn MEASURE_WAREHOUSE_PROFIT_COL;

    // Static tables
    public static final PhysicalTable TABLE_SALES_FACT;
    public static final PhysicalTable TABLE_SALES_FACT1998;
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
    public static final PhysicalTable TABLE_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final PhysicalTable TABLE_AGG_C_10_SALES_FACT_1997;
    public static final PhysicalTable TABLE_AGG_L_05_SALES_FACT;
    public static final PhysicalTable TABLE_AGG_L_03_SALES_FACT;
    public static final PhysicalTable TABLE_AGG_G_MS_PCAT_SALES_FACT;
    public static final PhysicalTable TABLE_AGG_C_14_SALES_FACT;
    public static final PhysicalTable TABLE_TIME_BY_DAY;
    public static final PhysicalTable TABLE_STORE_RAGGED;


    // Static AggregationName
    public static final AggregationName AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997;

    // Static AggregationExclude
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997;
    public static final AggregationExclude AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997;

    // Static table queries
    //public static final TableQuery QUERY_TIME;
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
    public static final TableQuery QUERY_TIME_BY_DAY;
    public static final TableQuery QUERY_STORE_RAGGED;

    public static final JoinedQueryElement JOIN_PRODUCT_PRODUCT_CLASS_LEFT;
    public static final JoinedQueryElement JOIN_PRODUCT_PRODUCT_CLASS_RIGHT;
    public static final JoinQuery JOIN_PRODUCT_PRODUCT_CLASS;

    public static final JoinedQueryElement JOIN_EMPLOYEE_POSITION_LEFT;
    public static final JoinedQueryElement JOIN_EMPLOYEE_POSITION_RIGHT;
    public static final JoinQuery JOIN_EMPLOYEE_POSITION;

    public static final JoinedQueryElement JOIN_EMPLOYEE_STORE_LEFT;
    public static final JoinedQueryElement JOIN_EMPLOYEE_STORE_RIGHT;
    public static final JoinQuery JOIN_EMPLOYEE_STORE;

    // Static levels
    public static final Level LEVEL_YEAR;
    public static final Level LEVEL_QUARTER;
    public static final Level LEVEL_MONTH;
    public static final Level LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR;
    public static final Level LEVEL_WEEK;
    public static final Level LEVEL_DAY;
    public static final Level LEVEL_STORE_COUNTRY;
    public static final Level LEVEL_STORE_STATE;
    public static final Level LEVEL_STORE_CITY;
    public static final Level LEVEL_STORE_NAME;
    public static final Level LEVEL_STORE_HAS_COFFEE_BAR;
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
    public static final Level LEVEL_PROMOTION_NAME;
    public static final Level LEVEL_STORE_SQFT;
    public static final Level LEVEL_STORE_TYPE_WITHOUT_TABLE;
    public static final Level LEVEL_EDUCATION;
    public static final Level LEVEL_GENDER;
    public static final Level LEVEL_MARITAL_STATUS;
    public static final Level LEVEL_YEARLY_INCOME;
    public static final Level LEVEL_PAY_TYPE;
    public static final Level LEVEL_STORE_TYPE;
    public static final Level LEVEL_STORE_COUNTRY_WITH_NEVER;
    public static final Level LEVEL_STORE_CYTY_IF_PARENTS_NAME;
    public static final Level LEVEL_STORE_CYTY_IF_BLANK_NAME;
    public static final Level LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER;

    public static final Level LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY;
    public static final Level LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE;
    public static final Level LEVEL_CITY_TABLE_COLUMN_CITY;
    public static final Level LEVEL_NAME;

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
    public static final Level LEVEL_MANAGEMENT_ROLE;
    public static final Level LEVEL_HR_POSITION_TITLE;

    // Enhanced Customer levels
    public static final Level LEVEL_CUSTOMER_EDUCATION;
    public static final Level LEVEL_CUSTOMER_MARITAL_STATUS;

    public static final Level LEVEL_EMPLOYEE_ID;

    public static final Level LEVEL_COUNTRY_WITH_NEVER;
    public static final Level LEVEL_STATE;
    public static final Level LEVEL_CITY_TABLE_COLUMN_STORE_CITY;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_TIME;
    public static final ExplicitHierarchy HIERARCHY_HR_TIME;
    public static final ExplicitHierarchy HIERARCHY_TIME2;
    public static final ExplicitHierarchy HIERARCHY_STORE;
    public static final ExplicitHierarchy HIERARCHY_STORE_SALES_RAGGED;
    public static final ExplicitHierarchy HIERARCHY_HR_STORE;
    public static final ExplicitHierarchy HIERARCHY_PAY_TYPE;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMER;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GEO;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_GENDER;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_STORE_SIZE_IN_SQFT;
    public static final ExplicitHierarchy HIERARCHY_PROMOTIONS;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE;
    public static final ExplicitHierarchy HIERARCHY_STORE_TYPE_WITHOUT_TABLE;
    public static final ExplicitHierarchy HIERARCHY_EDUCATION_LEVEL;
    public static final ExplicitHierarchy HIERARCHY_GENDER;
    public static final ExplicitHierarchy HIERARCHY_MARITAL_STATUS;
    public static final ExplicitHierarchy HIERARCHY_YEARLY_INCOME;
    public static final ExplicitHierarchy HIERARCHY_STORE_HAS_COFFEE_BAR;
    public static final ExplicitHierarchy HIERARCHY_GEOGRAPHY;

    // Missing hierarchies
    public static final ExplicitHierarchy HIERARCHY_WAREHOUSE;
    public static final ExplicitHierarchy HIERARCHY_PROMOTION_MEDIA;
    public static final ParentChildHierarchy HIERARCHY_EMPLOYEE;
    public static final ExplicitHierarchy HIERARCHY_DEPARTMENT;
    public static final ExplicitHierarchy HIERARCHY_POSITION;
    public static final ExplicitHierarchy HIERARCHY_HR_POSITION;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_EDUCATION;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS_MARITAL;

    // Static dimensions
    public static final TimeDimension DIMENSION_TIME;
    public static final TimeDimension DIMENSION_HR_TIME;
    public static final StandardDimension DIMENSION_STORE;
    public static final StandardDimension DIMENSION_STORE_SALES_RAGGED;
    public static final StandardDimension DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE;
    public static final StandardDimension DIMENSION_PAY_TYPE;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE;
    public static final StandardDimension DIMENSION_CUSTOMERS;
    public static final StandardDimension DIMENSION_PRODUCT;
    public static final StandardDimension DIMENSION_STORE_SIZE_IN_SQFT;
    public static final StandardDimension DIMENSION_PROMOTIONS;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITH_QUERY_STORE;
    public static final StandardDimension DIMENSION_STORE_TYPE_WITHOUT_QUERY;
    public static final StandardDimension DIMENSION_EDUCATION_LEVEL;
    public static final StandardDimension DIMENSION_GENDER;
    public static final StandardDimension DIMENSION_MARITAL_STATUS;
    public static final StandardDimension DIMENSION_YEARLY_INCOME;
    public static final StandardDimension DIMENSION_STORE_HAS_COFFEE_BAR;
    public static final StandardDimension DIMENSION_GEOGRAPHY;
    public static final StandardDimension DIMENSION_STORE_TYPE;


    // Missing dimensions
    public static final StandardDimension DIMENSION_WAREHOUSE;
    public static final StandardDimension DIMENSION_PROMOTION_MEDIA;
    public static final StandardDimension DIMENSION_EMPLOYEE;
    public static final StandardDimension DIMENSION_DEPARTMENT;
    public static final StandardDimension DIMENSION_POSITION;
    public static final StandardDimension DIMENSION_HR_POSITION;

    // Static measures
    public static final SumMeasure MEASURE_UNIT_SALES;
    public static final SumMeasure MEASURE_STORE_SALES;
    public static final SumMeasure MEASURE_STORE_COST;
    public static final CountMeasure MEASURE_SALES_COUNT;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT;
    public static final SumMeasure MEASURE_PROMOTION_SALES;

    // Warehouse measures
    public static final SumMeasure MEASURE_WAREHOUSE_SALES;
    public static final SumMeasure MEASURE_WAREHOUSE_STORE_INVOICE;
    public static final SumMeasure MEASURE_WAREHOUSE_SUPPLY_TIME;
    public static final SumMeasure MEASURE_WAREHOUSE_PROFIT;
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

    public static final SumMeasure MEASURE_UNIT_SALES_RAGGED;
    public static final SumMeasure MEASURE_STORE_COST_RAGGED;
    public static final SumMeasure MEASURE_STORE_SALES_RAGGED;
    public static final CountMeasure MEASURE_SALES_COUNT_RAGGED;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT_RAGGED;

    public static final CountMeasure MEASURE_SALES_COUNT_WITH_PROPERTY;
    public static final SumMeasure MEASURE_UNIT_SALES_MEMBER_ORDINAL;
    public static final SumMeasure MEASURE_STORE_SALES_WITH_PROPERTY;
    public static final SumMeasure MEASURE_STORE_COST_WITH_PROPERTY;
    public static final CountMeasure MEASURE_CUSTOMER_COUNT_WITH_PROPERTY;

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
    public static final AccessRole ROLE_CALIFORNIA_MANAGER;
    public static final AccessRole ROLE_NO_HR_CUBE;
    public static final AccessRole ROLE_ADMINISTRATOR;

    // Static cubes
    public static final PhysicalCube CUBE_SALES;
    public static final PhysicalCube CUBE_WAREHOUSE;
    public static final PhysicalCube CUBE_STORE;
    public static final PhysicalCube CUBE_HR;
    public static final PhysicalCube CUBE_SALES_RAGGED;
    public static final PhysicalCube CUBE_SALES_2;
    public static final VirtualCube CUBE_VIRTIAL_WAREHOUSE_AND_SALES;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_FOODMART;
    public static final Catalog CATALOG_FOODMART;

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

        COLUMN_PROMOTION_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_SALESFACT.setName("promotion_id");
        COLUMN_PROMOTION_ID_SALESFACT.setId("_column_salesFact_promotionId");
        COLUMN_PROMOTION_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ID_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_SALESFACT.setName("product_id");
        COLUMN_PRODUCT_ID_SALESFACT.setId("_column_salesFact_productId");
        COLUMN_PRODUCT_ID_SALESFACT.setType(ColumnType.INTEGER);

        COLUMN_UNIT_SALES_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_SALESFACT.setName("unit_sales");
        COLUMN_UNIT_SALES_SALESFACT.setId("_column_salesFact_unitSales");
        COLUMN_UNIT_SALES_SALESFACT.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_SALESFACT.setColumnSize(10);
        COLUMN_UNIT_SALES_SALESFACT.setDecimalDigits(4);

        COLUMN_STORE_SALES_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_SALESFACT.setName("store_sales");
        COLUMN_STORE_SALES_SALESFACT.setId("_column_salesFact_storeSales");
        COLUMN_STORE_SALES_SALESFACT.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_SALESFACT.setColumnSize(10);
        COLUMN_STORE_SALES_SALESFACT.setDecimalDigits(4);

        COLUMN_STORE_COST_SALESFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_SALESFACT.setName("store_cost");
        COLUMN_STORE_COST_SALESFACT.setId("_column_salesFact_storeCost");
        COLUMN_STORE_COST_SALESFACT.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_SALESFACT.setColumnSize(10);
        COLUMN_STORE_COST_SALESFACT.setDecimalDigits(4);

        COLUMN_PRODUCT_ID_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_SALESFACT1998.setName("product_id");
        COLUMN_PRODUCT_ID_SALESFACT1998.setId("_column_salesFact1998_productId");
        COLUMN_PRODUCT_ID_SALESFACT1998.setType(ColumnType.INTEGER);

        COLUMN_TIME_ID_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_SALESFACT1998.setName("time_id");
        COLUMN_TIME_ID_SALESFACT1998.setId("_column_salesFact1998_timeId");
        COLUMN_TIME_ID_SALESFACT1998.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ID_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_SALESFACT1998.setName("customer_id");
        COLUMN_CUSTOMER_ID_SALESFACT1998.setId("_column_salesFact1998_customerId");
        COLUMN_CUSTOMER_ID_SALESFACT1998.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_ID_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_SALESFACT1998.setName("promotion_id");
        COLUMN_PROMOTION_ID_SALESFACT1998.setId("_column_salesFact1998_promotionId");
        COLUMN_PROMOTION_ID_SALESFACT1998.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_SALESFACT1998.setName("store_id");
        COLUMN_STORE_ID_SALESFACT1998.setId("_column_salesFact1998_storeId");
        COLUMN_STORE_ID_SALESFACT1998.setType(ColumnType.INTEGER);

        COLUMN_STORE_SALES_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_SALESFACT1998.setName("store_sales");
        COLUMN_STORE_SALES_SALESFACT1998.setId("_column_salesFact1998_storeSales");
        COLUMN_STORE_SALES_SALESFACT1998.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_SALESFACT1998.setColumnSize(10);
        COLUMN_STORE_SALES_SALESFACT1998.setDecimalDigits(4);

        COLUMN_STORE_COST_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_SALESFACT1998.setName("store_cost");
        COLUMN_STORE_COST_SALESFACT1998.setId("_column_salesFact1998_storeCost");
        COLUMN_STORE_COST_SALESFACT1998.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_SALESFACT1998.setColumnSize(10);
        COLUMN_STORE_COST_SALESFACT1998.setDecimalDigits(4);

        COLUMN_UNIT_SALES_SALESFACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_SALESFACT1998.setName("unit_sales");
        COLUMN_UNIT_SALES_SALESFACT1998.setId("_column_salesFact1998_unitSales");
        COLUMN_UNIT_SALES_SALESFACT1998.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_SALESFACT1998.setColumnSize(10);
        COLUMN_UNIT_SALES_SALESFACT1998.setDecimalDigits(4);

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

        COLUMN_LNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LNAME_CUSTOMER.setName("lname");
        COLUMN_LNAME_CUSTOMER.setId("_column_customer_lname");
        COLUMN_LNAME_CUSTOMER.setType(ColumnType.VARCHAR);
        COLUMN_LNAME_CUSTOMER.setColumnSize(30);

        COLUMN_FNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FNAME_CUSTOMER.setName("fname");
        COLUMN_FNAME_CUSTOMER.setId("_column_customer_fname");
        COLUMN_FNAME_CUSTOMER.setType(ColumnType.VARCHAR);
        COLUMN_FNAME_CUSTOMER.setColumnSize(30);

        COLUMN_ACCOUNT_NUM_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ACCOUNT_NUM_CUSTOMER.setName("account_num");
        COLUMN_ACCOUNT_NUM_CUSTOMER.setId("_column_customer_account_num");
        COLUMN_ACCOUNT_NUM_CUSTOMER.setType(ColumnType.BIGINT);

        COLUMN_CUSTOMER_REGION_ID_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_REGION_ID_CUSTOMER.setName("customer_region_id");
        COLUMN_CUSTOMER_REGION_ID_CUSTOMER.setId("_column_customer_customer_region_id");
        COLUMN_CUSTOMER_REGION_ID_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_NUM_CARS_OWNED_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NUM_CARS_OWNED_CUSTOMER.setName("num_cars_owned");
        COLUMN_NUM_CARS_OWNED_CUSTOMER.setId("_column_customer_num_cars_owned");
        COLUMN_NUM_CARS_OWNED_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_TOTAL_CHILDREN_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setName("total_children");
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setId("_column_customer_total_children");
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setType(ColumnType.SMALLINT);

        COLUMN_ADDRESS2_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ADDRESS2_CUSTOMER.setName("address2");
        COLUMN_ADDRESS2_CUSTOMER.setId("_column_customer_address2");
        COLUMN_ADDRESS2_CUSTOMER.setType(ColumnType.VARCHAR);
        COLUMN_ADDRESS2_CUSTOMER.setColumnSize(30);

        // Product table columns
        COLUMN_PRODUCT_CLASS_ID_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CLASS_ID_PRODUCT.setName("product_class_id");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT.setId("_column_product_productClassId");
        COLUMN_PRODUCT_CLASS_ID_PRODUCT.setType(ColumnType.INTEGER);

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

        COLUMN_STORES_ID_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORES_ID_WAREHOUSE.setName("stores_id");
        COLUMN_STORES_ID_WAREHOUSE.setId("_column_warehouse_stores_id");
        COLUMN_STORES_ID_WAREHOUSE.setType(ColumnType.INTEGER);

        // Initialize inventory fact columns
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setName("warehouse_id");
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setId("_column_inventoryFact_warehouseId");
        COLUMN_WAREHOUSE_ID_INVENTORY_FACT.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_INVENTORY_FACT.setName("store_id");
        COLUMN_STORE_ID_INVENTORY_FACT.setId("_column_inventoryFact_storeId");
        COLUMN_STORE_ID_INVENTORY_FACT.setType(ColumnType.INTEGER);

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

        COLUMN_SALARY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALARY_EMPLOYEE.setName("salary");
        COLUMN_SALARY_EMPLOYEE.setId("_column_employee_salary");
        COLUMN_SALARY_EMPLOYEE.setType(ColumnType.DECIMAL);
        COLUMN_SALARY_EMPLOYEE.setColumnSize(10);
        COLUMN_SALARY_EMPLOYEE.setDecimalDigits(4);

        COLUMN_EDUCATION_LEVEL_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EDUCATION_LEVEL_EMPLOYEE.setName("education_level");
        COLUMN_EDUCATION_LEVEL_EMPLOYEE.setId("_column_employee_education_level");
        COLUMN_EDUCATION_LEVEL_EMPLOYEE.setType(ColumnType.VARCHAR);

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

        COLUMN_DEPARTMENT_ID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEPARTMENT_ID_SALARY.setName("department_id");
        COLUMN_DEPARTMENT_ID_SALARY.setId("_column_salary_departmentId");
        COLUMN_DEPARTMENT_ID_SALARY.setType(ColumnType.INTEGER);

        COLUMN_CURRENCY_ID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURRENCY_ID_SALARY.setName("currency_id");
        COLUMN_CURRENCY_ID_SALARY.setId("_column_salary_currencyId");
        COLUMN_CURRENCY_ID_SALARY.setType(ColumnType.INTEGER);

        COLUMN_PAY_DATE_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PAY_DATE_SALARY.setName("pay_date");
        COLUMN_PAY_DATE_SALARY.setId("_column_salary_payDate");
        COLUMN_PAY_DATE_SALARY.setType(ColumnType.DATE);

        COLUMN_SALARY_PAID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALARY_PAID_SALARY.setName("salary_paid");
        COLUMN_SALARY_PAID_SALARY.setId("_column_salary_salaryPaid");
        COLUMN_SALARY_PAID_SALARY.setType(ColumnType.DECIMAL);
        COLUMN_SALARY_PAID_SALARY.setColumnSize(10);
        COLUMN_SALARY_PAID_SALARY.setDecimalDigits(4);

        COLUMN_OVERTIME_PAID_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_OVERTIME_PAID_SALARY.setName("overtime_paid");
        COLUMN_OVERTIME_PAID_SALARY.setId("_column_salary_overtimePaid");
        COLUMN_OVERTIME_PAID_SALARY.setType(ColumnType.NUMERIC);
        COLUMN_OVERTIME_PAID_SALARY.setColumnSize(10);
        COLUMN_OVERTIME_PAID_SALARY.setDecimalDigits(4);

        COLUMN_VACATION_ACCRUED_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_VACATION_ACCRUED_SALARY.setName("vacation_accrued");
        COLUMN_VACATION_ACCRUED_SALARY.setId("_column_salary_vacationAccrued");
        COLUMN_VACATION_ACCRUED_SALARY.setType(ColumnType.REAL);

        COLUMN_VACATION_USED_SALARY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_VACATION_USED_SALARY.setName("vacation_used");
        COLUMN_VACATION_USED_SALARY.setId("_column_salary_vacationUsed");
        COLUMN_VACATION_USED_SALARY.setType(ColumnType.REAL);

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

        COLUMN_STREET_ADDRESS_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STREET_ADDRESS_STORE.setName("store_street_address");
        COLUMN_STREET_ADDRESS_STORE.setId("_column_store_store_street_address");
        COLUMN_STREET_ADDRESS_STORE.setType(ColumnType.VARCHAR);
        COLUMN_STREET_ADDRESS_STORE.setColumnSize(30);

        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_product_id");
        COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_store_id");
        COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_month");
        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_time_month");
        COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_quarter");
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_time_quarter");
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setColumnSize(30);

        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setName("time_year");
        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_time_year");
        COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_sales_sum");
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_store_sales_sum");
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("store_cost_sum");
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_store_cost_sum");
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setName("unit_sales_sum");
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_unit_sales_sum");
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setColumnSize(10);
        COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setId("_column_agg_c_special_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setType(ColumnType.INTEGER);

        //unit_sales,customer_count,fact_count
        //DECIMAL(10.4),INTEGER,INTEGER
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_quarter");
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997.setColumnSize(30);

        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_the_year");
        COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_store_sales");
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_store_cost");
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_unit_sales");
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setColumnSize(10);
        COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997.setName("customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997.setId("_column_agg_c_10_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997.setType(ColumnType.INTEGER);

        //store_sales,store_cost,unit_sales,fact_count
        //DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_product_id");
        COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_promotion_id");
        COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_store_id");
        COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_store_sales");
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_store_cost");
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_unit_sales");
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setColumnSize(10);
        COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997.setId("_column_agg_l_05_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997.setName("time_id");
        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_time_id");
        COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_customer_id");
        COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_store_sales");
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_store_cost");
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_unit_sales");
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setColumnSize(10);
        COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997.setId("_column_agg_l_03_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997.setType(ColumnType.INTEGER);

        //store_cost,unit_sales,customer_count,fact_count
        //DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setName("gender");
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_gender");
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_GENDER_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setName("marital_status");
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_marital_status");
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_MARITAL_STATUS_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_family");
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_product_family");
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_PRODUCT_FAMILY_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_department");
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_product_department");
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_PRODUCT_DEPARTMENT_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setName("product_category");
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_product_category");
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_PRODUCT_CATEGORY_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_month_of_year");
        COLUMN_MONTH_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_quarter");
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_QUARTER_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(30);

        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_the_year");
        COLUMN_THE_YEAR_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_store_sales");
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_store_cost");
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(10);
        COLUMN_STORE_COST_AGG_G_MS_PCAT_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_unit_sales");
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setColumnSize(10);
        COLUMN_UNIT_SALES_AGG_G_MS_PCAT_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_customer_count");
        COLUMN_CUSTOMER_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setId("_column_agg_g_ms_pcat_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_G_MS_PCAT_SALES_FACT_1997.setType(ColumnType.INTEGER);

        //store_sales,store_cost,unit_sales,fact_count
        //DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997.setName("product_id");
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_product_id");
        COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997.setName("customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_customer_id");
        COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997.setName("store_id");
        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_store_id");
        COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997.setName("promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_promotion_id");
        COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997.setType(ColumnType.INTEGER);

        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997.setName("month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_month_of_year");
        COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setName("quarter");
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_quarter");
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setType(ColumnType.VARCHAR);
        COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997.setColumnSize(30);

        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997.setName("the_year");
        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_the_year");
        COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997.setType(ColumnType.SMALLINT);

        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setName("store_sales");
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_store_sales");
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setColumnSize(30);
        COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setName("store_cost");
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_store_cost");
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setColumnSize(30);
        COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setName("unit_sales");
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_unit_sales");
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setColumnSize(30);
        COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997.setDecimalDigits(4);

        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997.setName("fact_count");
        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997.setId("_column_agg_c_14_sales_fact_1997_fact_count");
        COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997.setType(ColumnType.INTEGER);

        //quarter,fiscal_period
        //VARCHAR(30),VARCHAR(30)
        COLUMN_TIME_ID_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_TIME_BY_DAY.setName("time_id");
        COLUMN_TIME_ID_TIME_BY_DAY.setId("_column_time_by_day_time_id");
        COLUMN_TIME_ID_TIME_BY_DAY.setType(ColumnType.INTEGER);

        COLUMN_THE_DATE_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_DATE_TIME_BY_DAY.setName("the_date");
        COLUMN_THE_DATE_TIME_BY_DAY.setId("_column_time_by_day_the_date");
        COLUMN_THE_DATE_TIME_BY_DAY.setType(ColumnType.TIMESTAMP);

        COLUMN_THE_DAY_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_DAY_TIME_BY_DAY.setName("the_day");
        COLUMN_THE_DAY_TIME_BY_DAY.setId("_column_time_by_day_the_day");
        COLUMN_THE_DAY_TIME_BY_DAY.setType(ColumnType.VARCHAR);
        COLUMN_THE_DAY_TIME_BY_DAY.setColumnSize(30);

        COLUMN_THE_MONTH_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_MONTH_TIME_BY_DAY.setName("the_month");
        COLUMN_THE_MONTH_TIME_BY_DAY.setId("_column_time_by_day_the_month");
        COLUMN_THE_MONTH_TIME_BY_DAY.setType(ColumnType.VARCHAR);
        COLUMN_THE_MONTH_TIME_BY_DAY.setColumnSize(30);

        COLUMN_THE_YEAR_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THE_YEAR_TIME_BY_DAY.setName("the_year");
        COLUMN_THE_YEAR_TIME_BY_DAY.setId("_column_time_by_day_the_year");
        COLUMN_THE_YEAR_TIME_BY_DAY.setType(ColumnType.SMALLINT);

        COLUMN_DAY_OF_MONTH_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAY_OF_MONTH_TIME_BY_DAY.setName("day_of_month");
        COLUMN_DAY_OF_MONTH_TIME_BY_DAY.setId("_column_time_by_day_day_of_month");
        COLUMN_DAY_OF_MONTH_TIME_BY_DAY.setType(ColumnType.SMALLINT);

        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY.setName("week_of_year");
        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY.setId("_column_time_by_day_week_of_year");
        COLUMN_WEEK_OF_YEAR_TIME_BY_DAY.setType(ColumnType.INTEGER);

        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY.setName("month_of_year");
        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY.setId("_column_time_by_day_month_of_year");
        COLUMN_MONTH_OF_YEAR_TIME_BY_DAY.setType(ColumnType.SMALLINT);

        COLUMN_QUARTER_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUARTER_TIME_BY_DAY.setName("quarter");
        COLUMN_QUARTER_TIME_BY_DAY.setId("_column_time_by_day_quarter");
        COLUMN_QUARTER_TIME_BY_DAY.setType(ColumnType.VARCHAR);
        COLUMN_QUARTER_TIME_BY_DAY.setColumnSize(30);

        COLUMN_FISCAL_PERIOD_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setName("fiscal_period");
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setId("_column_time_by_day_fiscal_period");
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setType(ColumnType.VARCHAR);
        COLUMN_FISCAL_PERIOD_TIME_BY_DAY.setColumnSize(30);

        //region_id,store_name,store_number,store_street_address,store_city,store_state,store_postal_code,store_country,store_manager,store_phone,store_fax,first_opened_date,last_remodel_date,store_sqft,grocery_sqft,frozen_sqft,meat_sqft,coffee_bar,video_store,salad_bar,prepared_food,florist
        //INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),TIMESTAMP,TIMESTAMP,INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,SMALLINT,SMALLINT,SMALLINT,SMALLINT

        COLUMN_STORE_ID_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_ID_STORE_RAGGED.setName("store_id");
        COLUMN_STORE_ID_STORE_RAGGED.setId("_column_store_ragged_store_id");
        COLUMN_STORE_ID_STORE_RAGGED.setType(ColumnType.INTEGER);

        COLUMN_STORE_TYPE_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_TYPE_STORE_RAGGED.setName("store_type");
        COLUMN_STORE_TYPE_STORE_RAGGED.setId("_column_store_ragged_store_type");
        COLUMN_STORE_TYPE_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_TYPE_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_NAME_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_NAME_STORE_RAGGED.setName("store_name");
        COLUMN_STORE_NAME_STORE_RAGGED.setId("_column_store_ragged_store_name");
        COLUMN_STORE_NAME_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_NAME_STORE_RAGGED.setColumnSize(30);

        COLUMN_STREET_ADDRESS_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setName("store_street_address");
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setId("_column_store_ragged_store_street_address");
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STREET_ADDRESS_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_STATE_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_STATE_STORE_RAGGED.setName("store_state");
        COLUMN_STORE_STATE_STORE_RAGGED.setId("_column_store_ragged_store_state");
        COLUMN_STORE_STATE_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_STATE_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_COUNTRY_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setName("store_country");
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setId("_column_store_ragged_store_country");
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_COUNTRY_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_MANAGER_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_MANAGER_STORE_RAGGED.setName("store_manager");
        COLUMN_STORE_MANAGER_STORE_RAGGED.setId("_column_store_ragged_store_manager");
        COLUMN_STORE_MANAGER_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_MANAGER_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_CITY_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_CITY_STORE_RAGGED.setName("store_city");
        COLUMN_STORE_CITY_STORE_RAGGED.setId("_column_store_ragged_store_city");
        COLUMN_STORE_CITY_STORE_RAGGED.setType(ColumnType.VARCHAR);
        COLUMN_STORE_CITY_STORE_RAGGED.setColumnSize(30);

        COLUMN_STORE_SQFT_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_SQFT_STORE_RAGGED.setName("store_sqft");
        COLUMN_STORE_SQFT_STORE_RAGGED.setId("_column_store_ragged_store_sqft");
        COLUMN_STORE_SQFT_STORE_RAGGED.setType(ColumnType.INTEGER);

        COLUMN_GROCERY_SQFT_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GROCERY_SQFT_STORE_RAGGED.setName("grocery_sqft");
        COLUMN_GROCERY_SQFT_STORE_RAGGED.setId("_column_store_ragged_grocery_sqft");
        COLUMN_GROCERY_SQFT_STORE_RAGGED.setType(ColumnType.INTEGER);

        COLUMN_FROZEN_SQFT_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FROZEN_SQFT_STORE_RAGGED.setName("frozen_sqft");
        COLUMN_FROZEN_SQFT_STORE_RAGGED.setId("_column_store_ragged_frozen_sqft");
        COLUMN_FROZEN_SQFT_STORE_RAGGED.setType(ColumnType.INTEGER);

        COLUMN_MEAT_SQFT_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MEAT_SQFT_STORE_RAGGED.setName("meat_sqft");
        COLUMN_MEAT_SQFT_STORE_RAGGED.setId("_column_store_ragged_meat_sqft");
        COLUMN_MEAT_SQFT_STORE_RAGGED.setType(ColumnType.INTEGER);

        COLUMN_COFFEE_BAR_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COFFEE_BAR_STORE_RAGGED.setName("coffee_bar");
        COLUMN_COFFEE_BAR_STORE_RAGGED.setId("_column_store_ragged_coffee_bar");
        COLUMN_COFFEE_BAR_STORE_RAGGED.setType(ColumnType.SMALLINT);

        COLUMN_REGION_ID_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REGION_ID_STORE_RAGGED.setName("region_id");
        COLUMN_REGION_ID_STORE_RAGGED.setId("_column_store_ragged_coffee_bar");
        COLUMN_REGION_ID_STORE_RAGGED.setType(ColumnType.INTEGER);

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1.getDialects().add("access");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1.setSql("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\".\"store_sales\")");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2.getDialects().addAll(List.of(
                "oracle", "h2", "hsqldb", "postgres", "neoview", "derby", "luciddb", "db2", "nuodb", "snowflake"));
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2.setSql("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3.getDialects().add("infobright");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3.setSql("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)");

        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4.getDialects().add("generic");
        MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4.setSql("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)");

        MEASURE_PROMOTION_SALES_COL = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        MEASURE_PROMOTION_SALES_COL.getSqls().addAll(List.of(MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT1, MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT2,
                MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT3, MEASURE_PROMOTION_SALES_COL_SQL_STATEMENT4));
        MEASURE_PROMOTION_SALES_COL.setType(ColumnType.DECIMAL);

        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1.getDialects().addAll(List.of("mysql", "mariadb", "infobright"));
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1.setSql("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`");

        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2.getDialects().add("generic");
        MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2.setSql("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;" +
                "warehouse_cost&quot;");

        MEASURE_WAREHOUSE_PROFIT_COL = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        MEASURE_WAREHOUSE_PROFIT_COL.getSqls().addAll(List.of(MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT1, MEASURE_WAREHOUSE_PROFIT_COL_SQL_STATEMENT2));
        MEASURE_WAREHOUSE_PROFIT_COL.setType(ColumnType.DECIMAL);

        // Initialize tables
        TABLE_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_SALES_FACT.setName("sales_fact_1997");
        TABLE_SALES_FACT.setId("_table_salesFact1997");
        TABLE_SALES_FACT.getColumns()
                .addAll(List.of(COLUMN_TIME_ID_SALESFACT, COLUMN_STORE_ID_SALESFACT, COLUMN_CUSTOMER_ID_SALESFACT,
                        COLUMN_PROMOTION_ID_SALESFACT, COLUMN_PRODUCT_ID_SALESFACT, COLUMN_UNIT_SALES_SALESFACT, COLUMN_STORE_SALES_SALESFACT,
                        COLUMN_STORE_COST_SALESFACT));

        //product_id,time_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales
        //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4)
        TABLE_SALES_FACT1998 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_SALES_FACT1998.setName("sales_fact_1998");
        TABLE_SALES_FACT1998.setId("_table_salesFact1998");
        TABLE_SALES_FACT1998.getColumns()
                .addAll(List.of(COLUMN_PRODUCT_ID_SALESFACT1998, COLUMN_TIME_ID_SALESFACT1998, COLUMN_CUSTOMER_ID_SALESFACT1998,
                        COLUMN_PROMOTION_ID_SALESFACT1998,  COLUMN_STORE_ID_SALESFACT1998, COLUMN_STORE_SALES_SALESFACT1998,
                        COLUMN_STORE_COST_SALESFACT1998, COLUMN_UNIT_SALES_SALESFACT1998));

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
                COLUMN_COFFEE_BAR_STORE, COLUMN_STORE_POSTAL_CODE_STORE, COLUMN_STORE_NUMBER_STORE, COLUMN_STREET_ADDRESS_STORE));

        TABLE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CUSTOMER.setName("customer");
        TABLE_CUSTOMER.setId("_table_customer");
        TABLE_CUSTOMER.getColumns()
                .addAll(List.of(COLUMN_CUSTOMER_ID_CUSTOMER, COLUMN_FULLNAME_CUSTOMER, COLUMN_GENDER_CUSTOMER,
                        COLUMN_COUNTRY_CUSTOMER, COLUMN_STATE_PROVINCE_CUSTOMER, COLUMN_CITY_CUSTOMER,
                        COLUMN_MARITAL_STATUS_CUSTOMER, COLUMN_EDUCATION_CUSTOMER, COLUMN_YEARLY_INCOME_CUSTOMER,
                        COLUMN_MEMBER_CARD_CUSTOMER, COLUMN_OCCUPATION_CUSTOMER, COLUMN_HOUSEOWNER_CUSTOMER,
                        COLUMN_NUM_CHILDREN_AT_HOME_CUSTOMER, COLUMN_LNAME_CUSTOMER, COLUMN_FNAME_CUSTOMER,
                        COLUMN_ACCOUNT_NUM_CUSTOMER, COLUMN_CUSTOMER_REGION_ID_CUSTOMER, COLUMN_NUM_CARS_OWNED_CUSTOMER,
                        COLUMN_TOTAL_CHILDREN_CUSTOMER, COLUMN_ADDRESS2_CUSTOMER));

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
                        COLUMN_WAREHOUSE_COUNTRY_WAREHOUSE, COLUMN_STORES_ID_WAREHOUSE));

        TABLE_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_INVENTORY_FACT.setName("inventory_fact_1997");
        TABLE_INVENTORY_FACT.setId("_table_inventoryFact1997");
        TABLE_INVENTORY_FACT.getColumns()
                .addAll(List.of(COLUMN_WAREHOUSE_ID_INVENTORY_FACT, COLUMN_STORE_ID_INVENTORY_FACT, COLUMN_PRODUCT_ID_INVENTORY_FACT,
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
                        COLUMN_MARITAL_STATUS_EMPLOYEE, COLUMN_GENDER_EMPLOYEE, COLUMN_SALARY_EMPLOYEE, COLUMN_EDUCATION_LEVEL_EMPLOYEE));

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
                .addAll(List.of(COLUMN_EMPLOYEE_ID_SALARY, COLUMN_DEPARTMENT_ID_SALARY, COLUMN_CURRENCY_ID_SALARY, COLUMN_PAY_DATE_SALARY,
                        COLUMN_SALARY_PAID_SALARY, COLUMN_OVERTIME_PAID_SALARY, COLUMN_VACATION_ACCRUED_SALARY, COLUMN_VACATION_USED_SALARY));

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

        TABLE_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_C_SPECIAL_SALES_FACT_1997.setName("agg_c_special_sales_fact_1997");
        TABLE_AGG_C_SPECIAL_SALES_FACT_1997.getColumns()
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
        TABLE_AGG_C_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_C_10_SALES_FACT_1997.setName("agg_c_10_sales_fact_1997");
        TABLE_AGG_C_10_SALES_FACT_1997.getColumns()
        .addAll(List.of(COLUMN_MONTH_YEAR_AGG_C_10_SALES_FACT_1997,
                COLUMN_QUARTER_AGG_C_10_SALES_FACT_1997, COLUMN_THE_YEAR_AGG_C_10_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_C_10_SALES_FACT_1997, COLUMN_STORE_COST_AGG_C_10_SALES_FACT_1997,
                COLUMN_UNIT_SALES_AGG_C_10_SALES_FACT_1997, COLUMN_CUSTOMER_COUNT_AGG_C_10_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_C_10_SALES_FACT_1997));

        //product_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales,fact_count
        //INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_L_05_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_L_05_SALES_FACT.setName("agg_l_05_sales_fact_1997");
        TABLE_AGG_L_05_SALES_FACT.getColumns()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_L_05_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_L_05_SALES_FACT_1997,
                COLUMN_PROMOTION_ID_AGG_L_05_SALES_FACT_1997, COLUMN_STORE_ID_AGG_L_05_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_L_05_SALES_FACT_1997, COLUMN_STORE_COST_AGG_L_05_SALES_FACT_1997, COLUMN_UNIT_SALES_AGG_L_05_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_L_05_SALES_FACT_1997
                ));

        //time_id,customer_id,store_sales,store_cost,unit_sales,fact_count
        //INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
        TABLE_AGG_L_03_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_L_03_SALES_FACT.setName("agg_l_03_sales_fact_1997");
        TABLE_AGG_L_03_SALES_FACT.getColumns()
        .addAll(List.of(COLUMN_TIME_ID_AGG_L_03_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_L_03_SALES_FACT_1997,
                COLUMN_STORE_SALES_AGG_L_03_SALES_FACT_1997, COLUMN_STORE_COST_AGG_L_03_SALES_FACT_1997,
                COLUMN_UNIT_SALES_AGG_L_03_SALES_FACT_1997, COLUMN_FACT_COUNT_AGG_L_03_SALES_FACT_1997
                ));

        //gender,marital_status,product_family,product_department,product_category,month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,customer_count,fact_count
        //VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
        TABLE_AGG_G_MS_PCAT_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_G_MS_PCAT_SALES_FACT.setName("agg_g_ms_pcat_sales_fact_1997");
        TABLE_AGG_G_MS_PCAT_SALES_FACT.getColumns()
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
        TABLE_AGG_C_14_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGG_C_14_SALES_FACT.setName("agg_c_14_sales_fact_1997");
        TABLE_AGG_C_14_SALES_FACT.getColumns()
        .addAll(List.of(COLUMN_PRODUCT_ID_AGG_C_14_SALES_FACT_1997, COLUMN_CUSTOMER_ID_AGG_C_14_SALES_FACT_1997,
                COLUMN_STORE_ID_AGG_C_14_SALES_FACT_1997, COLUMN_PROMOTION_ID_AGG_C_14_SALES_FACT_1997,
                COLUMN_MONTH_YEAR_AGG_C_14_SALES_FACT_1997, COLUMN_QUARTER_AGG_C_14_SALES_FACT_1997,
                COLUMN_THE_YEAR_AGG_C_14_SALES_FACT_1997, COLUMN_STORE_SALES_AGG_C_14_SALES_FACT_1997,
                COLUMN_STORE_COST_AGG_C_14_SALES_FACT_1997, COLUMN_UNIT_SALES_AGG_C_14_SALES_FACT_1997,
                COLUMN_FACT_COUNT_AGG_C_14_SALES_FACT_1997
                ));

        TABLE_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_TIME_BY_DAY.setName("time_by_day");
        TABLE_TIME_BY_DAY.getColumns()
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

        TABLE_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_STORE_RAGGED.setName("store_ragged");
        TABLE_STORE_RAGGED.getColumns()
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

        AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        AGGREGATION_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_FACT_COUNT_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_PRODUCT_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_PRODUCT_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_CUSTOMER_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_PROMOTION_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_PROMOTION_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setFactColumn(COLUMN_STORE_ID_SALESFACT);
        AGGREGATION_FOREIGN_KEY_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997.setAggregationColumn(COLUMN_STORE_ID_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Unit Sales]");
        AGGREGATION_MEASURE_UNION_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_UNIT_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Store Cost]");
        AGGREGATION_MEASURE_STORE_COST_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_STORE_COST_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Measures].[Store Sales]");
        AGGREGATION_MEASURE_STORE_SALES_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_STORE_SALES_SUM_AGG_C_SPECIAL_SALES_FACT_1997);

        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Year]");
        AGGREGATION_LEVEL_YEAR_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_YEAR_AGG_C_SPECIAL_SALES_FACT_1997);;

        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Quarter]");
        AGGREGATION_LEVEL_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_QUARTER_AGG_C_SPECIAL_SALES_FACT_1997);;

        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setName("[Time].[Month]");
        AGGREGATION_LEVEL_MONTH_AGG_C_SPECIAL_SALES_FACT_1997.setColumn(COLUMN_TIME_MONTH_AGG_C_SPECIAL_SALES_FACT_1997);;

        // Initialize AggregationName
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationName();
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.setName(TABLE_AGG_C_SPECIAL_SALES_FACT_1997);
        AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997.setId("_aggregation_name_agg_c_special_sales_fact_1997");
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
        AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997.setName("agg_c_special_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997.setName("agg_lc_100_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997.setName("agg_lc_10_sales_fact_1997");

        AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997.setName("agg_pc_10_sales_fact_1997");

        // Initialize table queries
        QUERY_STORE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_STORE.setTable(TABLE_STORE);
        QUERY_STORE.setId("_query_store");

        QUERY_CUSTOMER = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_CUSTOMER.setTable(TABLE_CUSTOMER);
        QUERY_CUSTOMER.setId("_query_customer");

        QUERY_PRODUCT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PRODUCT.setTable(TABLE_PRODUCT);
        QUERY_PRODUCT.setId("_query_product");

        QUERY_SALES_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_SALES_FACT.setTable(TABLE_SALES_FACT);
        QUERY_SALES_FACT.getAggregationExcludes().addAll(List.of(AGGREGATION_EXCLUDE_AGG_C_SPECIAL_SALES_FACT_1997,
                AGGREGATION_EXCLUDE_AGG_LC_100_SALES_FACT_1997, AGGREGATION_EXCLUDE_AGG_LC_10_SALES_FACT_1997,
                AGGREGATION_EXCLUDE_AGG_PC_10_SALES_FACT_1997));
        QUERY_SALES_FACT.getAggregationTables().add(AGGREGATION_NAME_AGG_C_SPECIAL_SALES_FACT_1997);
        QUERY_SALES_FACT.setId("_query_sales_fact");

        // Initialize new table queries
        QUERY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_WAREHOUSE.setTable(TABLE_WAREHOUSE);
        QUERY_WAREHOUSE.setId("_query_warehouse");

        QUERY_INVENTORY_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_INVENTORY_FACT.setTable(TABLE_INVENTORY_FACT);
        QUERY_INVENTORY_FACT.setId("_query_inventory_fact");

        QUERY_PROMOTION = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PROMOTION.setTable(TABLE_PROMOTION);
        QUERY_PROMOTION.setId("_query_promotion");

        QUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_EMPLOYEE.setTable(TABLE_EMPLOYEE);
        QUERY_EMPLOYEE.setId("_query_exployee");

        QUERY_DEPARTMENT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_DEPARTMENT.setTable(TABLE_DEPARTMENT);
        QUERY_DEPARTMENT.setId("_query_departament");

        QUERY_POSITION = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_POSITION.setTable(TABLE_POSITION);
        QUERY_POSITION.setId("_query_position");

        QUERY_SALARY = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_SALARY.setTable(TABLE_SALARY);
        QUERY_SALARY.setId("_query_salary");

        QUERY_EMPLOYEE_CLOSURE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_EMPLOYEE_CLOSURE.setTable(TABLE_EMPLOYEE_CLOSURE);
        QUERY_EMPLOYEE_CLOSURE.setId("_query_employee_closure");

        QUERY_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_PRODUCT_CLASS.setTable(TABLE_PRODUCT_CLASS);
        QUERY_PRODUCT_CLASS.setId("_query_product_class");

        QUERY_TIME_BY_DAY = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_TIME_BY_DAY.setTable(TABLE_TIME_BY_DAY);
        QUERY_TIME_BY_DAY.setId("_query_time_by_day");

        QUERY_STORE_RAGGED = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_STORE_RAGGED.setTable(TABLE_STORE_RAGGED);
        QUERY_STORE_RAGGED.setId("_query_store_ragged");

        JOIN_PRODUCT_PRODUCT_CLASS_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_PRODUCT_CLASS_LEFT.setKey(COLUMN_PRODUCT_CLASS_ID_PRODUCT);
        JOIN_PRODUCT_PRODUCT_CLASS_LEFT.setQuery(QUERY_PRODUCT);

        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT.setKey(COLUMN_PRODUCT_CLASS_ID_PRODUCT_CLASS);
        JOIN_PRODUCT_PRODUCT_CLASS_RIGHT.setQuery(QUERY_PRODUCT_CLASS);

        JOIN_PRODUCT_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_PRODUCT_PRODUCT_CLASS.setId("_join_query_product_product_class");
        JOIN_PRODUCT_PRODUCT_CLASS.setLeft(JOIN_PRODUCT_PRODUCT_CLASS_LEFT);
        JOIN_PRODUCT_PRODUCT_CLASS.setRight(JOIN_PRODUCT_PRODUCT_CLASS_RIGHT);

        JOIN_EMPLOYEE_POSITION_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_POSITION_LEFT.setKey(COLUMN_POSITION_ID_EMPLOYEE);
        JOIN_EMPLOYEE_POSITION_LEFT.setQuery(QUERY_EMPLOYEE);

        JOIN_EMPLOYEE_POSITION_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_POSITION_RIGHT.setKey(COLUMN_POSITION_ID_POSITION);
        JOIN_EMPLOYEE_POSITION_RIGHT.setQuery(QUERY_POSITION);

        JOIN_EMPLOYEE_POSITION = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_EMPLOYEE_POSITION.setId("_join_query_employee_position");
        JOIN_EMPLOYEE_POSITION.setLeft(JOIN_EMPLOYEE_POSITION_LEFT);
        JOIN_EMPLOYEE_POSITION.setRight(JOIN_EMPLOYEE_POSITION_RIGHT);

        JOIN_EMPLOYEE_STORE_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_STORE_LEFT.setKey(COLUMN_STORE_ID_EMPLOYEE);
        JOIN_EMPLOYEE_STORE_LEFT.setQuery(QUERY_EMPLOYEE);

        JOIN_EMPLOYEE_STORE_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_EMPLOYEE_STORE_RIGHT.setKey(COLUMN_STORE_ID_STORE);
        JOIN_EMPLOYEE_STORE_RIGHT.setQuery(QUERY_STORE);

        JOIN_EMPLOYEE_STORE = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_EMPLOYEE_STORE.setId("_join_query_employee_store");
        JOIN_EMPLOYEE_STORE.setLeft(JOIN_EMPLOYEE_STORE_LEFT);
        JOIN_EMPLOYEE_STORE.setRight(JOIN_EMPLOYEE_STORE_RIGHT);

        // Initialize levels
        LEVEL_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_YEAR.setName("Year");
        LEVEL_YEAR.setUniqueMembers(true);
        LEVEL_YEAR.setColumn(COLUMN_THE_YEAR_TIME_BY_DAY);
        LEVEL_YEAR.setType(LevelDefinition.TIME_YEARS);
        LEVEL_YEAR.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_YEAR.setId("_level_time_year");

        LEVEL_QUARTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_QUARTER.setName("Quarter");
        LEVEL_QUARTER.setColumn(COLUMN_QUARTER_TIME_BY_DAY);
        LEVEL_QUARTER.setType(LevelDefinition.TIME_QUARTERS);
        LEVEL_QUARTER.setId("_level_time_quarter");

        LEVEL_MONTH = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MONTH.setName("Month");
        LEVEL_MONTH.setColumn(COLUMN_MONTH_OF_YEAR_TIME_BY_DAY);
        LEVEL_MONTH.setId("_level_time_month");
        LEVEL_MONTH.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_MONTH.setType(LevelDefinition.TIME_MONTHS);

        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setName("Month");
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setColumn(COLUMN_MONTH_OF_YEAR_TIME_BY_DAY);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setNameColumn(COLUMN_THE_MONTH_TIME_BY_DAY);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setId("_level_time_month_hr");
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setUniqueMembers(false);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR.setType(LevelDefinition.TIME_MONTHS);

        LEVEL_WEEK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WEEK.setName("Week");
        LEVEL_WEEK.setColumn(COLUMN_WEEK_OF_YEAR_TIME_BY_DAY);
        LEVEL_WEEK.setId("_level_time_week");
        LEVEL_WEEK.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_WEEK.setType(LevelDefinition.TIME_WEEKS);

        LEVEL_DAY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_DAY.setName("Day");
        LEVEL_DAY.setColumn(COLUMN_DAY_OF_MONTH_TIME_BY_DAY);
        LEVEL_DAY.setId("_level_time_day");
        LEVEL_DAY.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_DAY.setUniqueMembers(false);
        LEVEL_DAY.setType(LevelDefinition.TIME_DAYS);

        LEVEL_STORE_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_COUNTRY.setName("Store Country");
        LEVEL_STORE_COUNTRY.setColumn(COLUMN_STORE_COUNTRY_STORE);
        LEVEL_STORE_COUNTRY.setUniqueMembers(true);
        LEVEL_STORE_COUNTRY.setId("_level_store_country");

        LEVEL_STORE_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_STATE.setName("Store State");
        LEVEL_STORE_STATE.setColumn(COLUMN_STORE_STATE_STORE);
        LEVEL_STORE_STATE.setId("_level_store_state");

        LEVEL_STORE_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CITY.setName("Store City");
        LEVEL_STORE_CITY.setColumn(COLUMN_STORE_CITY_STORE);
        LEVEL_STORE_CITY.setId("_level_store_city");
        LEVEL_STORE_CITY.setUniqueMembers(false);

        LEVEL_STORE_PROP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP1.setName("Store Type");
        LEVEL_STORE_PROP1.setColumn(COLUMN_STORE_TYPE_STORE);

        LEVEL_STORE_PROP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP2.setName("Store Manager");
        LEVEL_STORE_PROP2.setColumn(COLUMN_STORE_MANAGER_STORE);

        LEVEL_STORE_PROP3 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP3.setName("Store Sqft");
        LEVEL_STORE_PROP3.setColumn(COLUMN_STORE_SQFT_STORE);

        LEVEL_STORE_PROP4 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP4.setName("Grocery Sqft");
        LEVEL_STORE_PROP4.setColumn(COLUMN_GROCERY_SQFT_STORE);

        LEVEL_STORE_PROP5 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP5.setName("Frozen Sqft");
        LEVEL_STORE_PROP5.setColumn(COLUMN_FROZEN_SQFT_STORE);

        LEVEL_STORE_PROP6 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP6.setName("Meat Sqft");
        LEVEL_STORE_PROP6.setColumn(COLUMN_MEAT_SQFT_STORE);
        LEVEL_STORE_PROP6.setPropertyType(ColumnInternalDataType.NUMERIC);

        LEVEL_STORE_PROP7 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP7.setName("Has coffee bar");
        LEVEL_STORE_PROP7.setColumn(COLUMN_COFFEE_BAR_STORE);
        LEVEL_STORE_PROP7.setPropertyType(ColumnInternalDataType.BOOLEAN);

        LEVEL_STORE_PROP8 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_PROP8.setName("Street address");
        LEVEL_STORE_PROP8.setColumn(COLUMN_STORE_STREET_ADDRESS_STORE);
        LEVEL_STORE_PROP8.setPropertyType(ColumnInternalDataType.BOOLEAN);

        LEVEL_STORE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NAME.setName("Store Name");
        LEVEL_STORE_NAME.setColumn(COLUMN_STORE_NAME_STORE);
        LEVEL_STORE_NAME.setId("_level_store_name");
        LEVEL_STORE_NAME.getMemberProperties().addAll(List.of(LEVEL_STORE_PROP1, LEVEL_STORE_PROP2, LEVEL_STORE_PROP3,
                LEVEL_STORE_PROP4, LEVEL_STORE_PROP5, LEVEL_STORE_PROP6, LEVEL_STORE_PROP7, LEVEL_STORE_PROP8
                ));

        LEVEL_STORE_HAS_COFFEE_BAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_HAS_COFFEE_BAR.setName("Has coffee bar");
        LEVEL_STORE_HAS_COFFEE_BAR.setColumn(COLUMN_COFFEE_BAR_STORE);
        LEVEL_STORE_HAS_COFFEE_BAR.setUniqueMembers(true);
        LEVEL_STORE_HAS_COFFEE_BAR.setColumnType(ColumnInternalDataType.BOOLEAN);
        LEVEL_STORE_HAS_COFFEE_BAR.setId("_level_store_has_coffe_bar");

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
        LEVEL_PRODUCT_FAMILY.setColumn(COLUMN_PRODUCT_FAMILY_PRODUCT_CLASS);
        LEVEL_PRODUCT_FAMILY.setId("_level_product_family");

        LEVEL_PRODUCT_DEPARTMENT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_DEPARTMENT.setName("Product Department");
        LEVEL_PRODUCT_DEPARTMENT.setColumn(COLUMN_PRODUCT_DEPARTMENT_PRODUCT_CLASS);
        LEVEL_PRODUCT_DEPARTMENT.setId("_level_product_department");
        LEVEL_PRODUCT_DEPARTMENT.setUniqueMembers(false);

        LEVEL_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY.setName("Product Category");
        LEVEL_PRODUCT_CATEGORY.setColumn(COLUMN_PRODUCT_CATEGORY_PRODUCT_CLASS);
        LEVEL_PRODUCT_CATEGORY.setUniqueMembers(false);
        LEVEL_PRODUCT_CATEGORY.setId("_level_product_category");

        LEVEL_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY.setName("Product Subcategory");
        LEVEL_PRODUCT_SUBCATEGORY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_PRODUCT_CLASS);
        LEVEL_PRODUCT_SUBCATEGORY.setUniqueMembers(false);
        LEVEL_PRODUCT_SUBCATEGORY.setId("_level_product_subcategory");

        LEVEL_PRODUCT_BRAND = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_BRAND.setName("Brand Name");
        LEVEL_PRODUCT_BRAND.setColumn(COLUMN_BRAND_NAME_PRODUCT);
        LEVEL_PRODUCT_BRAND.setId("_level_product_brand");

        LEVEL_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_NAME.setName("Product Name");
        LEVEL_PRODUCT_NAME.setColumn(COLUMN_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_NAME.setId("_level_product_name");
        LEVEL_PRODUCT_NAME.setUniqueMembers(true);

        LEVEL_PROMOTION_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PROMOTION_NAME.setName("Promotion Name");
        LEVEL_PROMOTION_NAME.setColumn(COLUMN_PROMOTION_NAME_PROMOTION);
        LEVEL_PROMOTION_NAME.setUniqueMembers(true);
        LEVEL_PROMOTION_NAME.setId("_level_promotion_name");

        LEVEL_STORE_SQFT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_SQFT.setName("Store Sqft");
        LEVEL_STORE_SQFT.setColumn(COLUMN_STORE_SQFT_STORE);
        LEVEL_STORE_SQFT.setUniqueMembers(true);
        LEVEL_STORE_SQFT.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_STORE_SQFT.setId("_level_store_sqft");

        LEVEL_STORE_TYPE_WITHOUT_TABLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setName("Store Type");
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setColumn(COLUMN_STORE_TYPE_STORE);
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setUniqueMembers(true);
        LEVEL_STORE_TYPE_WITHOUT_TABLE.setId("_level_store_type_without_table");

        LEVEL_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EDUCATION.setName("Education Level");
        LEVEL_EDUCATION.setColumn(COLUMN_EDUCATION_CUSTOMER);
        LEVEL_EDUCATION.setUniqueMembers(true);
        LEVEL_EDUCATION.setId("_level_education_level");

        LEVEL_GENDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GENDER.setName("Gender");
        LEVEL_GENDER.setColumn(COLUMN_GENDER_CUSTOMER);
        LEVEL_GENDER.setUniqueMembers(true);
        LEVEL_GENDER.setId("_level_gender");

        LEVEL_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MARITAL_STATUS.setName("Marital Status");
        LEVEL_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);
        LEVEL_MARITAL_STATUS.setUniqueMembers(true);
        LEVEL_MARITAL_STATUS.setApproxRowCount("111");
        LEVEL_MARITAL_STATUS.setId("_level_marital_status");

        LEVEL_YEARLY_INCOME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_YEARLY_INCOME.setName("Yearly Income");
        LEVEL_YEARLY_INCOME.setColumn(COLUMN_YEARLY_INCOME_CUSTOMER);
        LEVEL_YEARLY_INCOME.setUniqueMembers(true);
        LEVEL_YEARLY_INCOME.setId("_level_yearly_income");

        LEVEL_PAY_TYPE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PAY_TYPE.setName("Pay Type");
        LEVEL_PAY_TYPE.setColumn(COLUMN_PAY_TYPE_POSITION);
        LEVEL_PAY_TYPE.setUniqueMembers(true);
        LEVEL_PAY_TYPE.setId("_level_pay_type");

        LEVEL_STORE_TYPE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_TYPE.setName("Store Type");
        LEVEL_STORE_TYPE.setColumn(COLUMN_STORE_TYPE_STORE);
        LEVEL_STORE_TYPE.setUniqueMembers(true);
        LEVEL_STORE_TYPE.setId("_level_store_type");

        LEVEL_STORE_COUNTRY_WITH_NEVER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_COUNTRY_WITH_NEVER.setName("Store Country");
        LEVEL_STORE_COUNTRY_WITH_NEVER.setColumn(COLUMN_STORE_COUNTRY_STORE_RAGGED);
        LEVEL_STORE_COUNTRY_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);
        LEVEL_STORE_COUNTRY_WITH_NEVER.setId("_level_regged_store_country");

        LEVEL_STORE_CYTY_IF_PARENTS_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setName("Store State");
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setColumn(COLUMN_STORE_STATE_STORE_RAGGED);
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setHideMemberIf(HideMemberIf.IF_PARENTS_NAME);
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setUniqueMembers(true);
        LEVEL_STORE_CYTY_IF_PARENTS_NAME.setId("_level_regged_store_state");

        LEVEL_STORE_CYTY_IF_BLANK_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setName("Store City");
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setColumn(COLUMN_STORE_CITY_STORE_RAGGED);
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setUniqueMembers(false);
        LEVEL_STORE_CYTY_IF_BLANK_NAME.setId("_level_regged_store_city");

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1.setName("Store Type");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1.setColumn(COLUMN_STORE_TYPE_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2.setName("Store Manager");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2.setColumn(COLUMN_STORE_MANAGER_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3.setName("Store Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3.setColumn(COLUMN_STORE_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4.setName("Grocery Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4.setColumn(COLUMN_GROCERY_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5.setName("Frozen Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5.setColumn(COLUMN_FROZEN_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6.setName("Meat Sqft");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6.setColumn(COLUMN_MEAT_SQFT_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7.setName("Has coffee bar");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7.setColumn(COLUMN_COFFEE_BAR_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8.setName("Street address");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8.setColumn(COLUMN_STREET_ADDRESS_STORE_RAGGED);

        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setName("Store Name");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setColumn(COLUMN_STORE_NAME_STORE_RAGGED);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setUniqueMembers(true);
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.setId("_level_regged_store_name");
        LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER.getMemberProperties().addAll(List.of(
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP1,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP2,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP3,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP4,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP5,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP6,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP7,
                LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER_PROP8));

        LEVEL_COUNTRY_WITH_NEVER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_WITH_NEVER.setName("Country");
        LEVEL_COUNTRY_WITH_NEVER.setColumn(COLUMN_STORE_COUNTRY_STORE_RAGGED);
        LEVEL_COUNTRY_WITH_NEVER.setHideMemberIf(HideMemberIf.NEVER);
        LEVEL_COUNTRY_WITH_NEVER.setUniqueMembers(true);
        LEVEL_COUNTRY_WITH_NEVER.setId("_level_regged_store_country");

        LEVEL_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STATE.setName("State");
        LEVEL_STATE.setColumn(COLUMN_STORE_STATE_STORE_RAGGED);
        LEVEL_STATE.setHideMemberIf(HideMemberIf.IF_PARENTS_NAME);
        LEVEL_STATE.setUniqueMembers(true);
        LEVEL_STATE.setId("_level_regged_store_state");

        LEVEL_CITY_TABLE_COLUMN_STORE_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setName("City");
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setColumn(COLUMN_STORE_CITY_STORE_RAGGED);
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setHideMemberIf(HideMemberIf.IF_BLANK_NAME);
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setUniqueMembers(false);
        LEVEL_CITY_TABLE_COLUMN_STORE_CITY.setId("_level_regged_store_city");

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
        LEVEL_WAREHOUSE_NAME.setUniqueMembers(true);
        LEVEL_WAREHOUSE_NAME.setId("_level_warehouse_name");

        LEVEL_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PROMOTION_MEDIA.setName("Media Type");
        LEVEL_PROMOTION_MEDIA.setColumn(COLUMN_MEDIA_TYPE_PROMOTION);
        LEVEL_PROMOTION_MEDIA.setUniqueMembers(true);
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
        LEVEL_DEPARTMENT_DESCRIPTION.setName("Department Description");
        LEVEL_DEPARTMENT_DESCRIPTION.setColumn(COLUMN_DEPARTMENT_ID_DEPARTMENT);
        LEVEL_DEPARTMENT_DESCRIPTION.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_DEPARTMENT_DESCRIPTION.setId("_level_department_description");

        LEVEL_POSITION_TITLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_POSITION_TITLE.setName("Position Title");
        LEVEL_POSITION_TITLE.setColumn(COLUMN_POSITION_TITLE_POSITION);
        LEVEL_POSITION_TITLE.setId("_level_position_title");

        LEVEL_HR_POSITION_TITLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_HR_POSITION_TITLE.setName("Position Title");
        LEVEL_HR_POSITION_TITLE.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);
        LEVEL_HR_POSITION_TITLE.setOrdinalColumn(COLUMN_POSITION_ID_EMPLOYEE);
        LEVEL_HR_POSITION_TITLE.setId("_level_hr_position_title");

        LEVEL_MANAGEMENT_ROLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MANAGEMENT_ROLE.setName("Management Role");
        LEVEL_MANAGEMENT_ROLE.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);
        LEVEL_MANAGEMENT_ROLE.setId("_level_hr_management_role");

        LEVEL_CUSTOMER_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_EDUCATION.setName("Education Level");
        LEVEL_CUSTOMER_EDUCATION.setColumn(COLUMN_EDUCATION_CUSTOMER);
        LEVEL_CUSTOMER_EDUCATION.setId("_level_customer_education");

        LEVEL_CUSTOMER_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_MARITAL_STATUS.setName("Marital Status");
        LEVEL_CUSTOMER_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);
        LEVEL_CUSTOMER_MARITAL_STATUS.setId("_level_customer_marital_status");

        LEVEL_EMPLOYEE_PROP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP1.setName("Marital Status");
        LEVEL_EMPLOYEE_PROP1.setColumn(COLUMN_MARITAL_STATUS_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP2.setName("Position Title");
        LEVEL_EMPLOYEE_PROP2.setColumn(COLUMN_POSITION_TITLE_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP3 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP3.setName("Gender");
        LEVEL_EMPLOYEE_PROP3.setColumn(COLUMN_GENDER_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP4 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP4.setName("Salary");
        LEVEL_EMPLOYEE_PROP4.setColumn(COLUMN_SALARY_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP5 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP5.setName("Education Level");
        LEVEL_EMPLOYEE_PROP5.setColumn(COLUMN_EDUCATION_LEVEL_EMPLOYEE);

        LEVEL_EMPLOYEE_PROP6 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_EMPLOYEE_PROP6.setName("Management Role");
        LEVEL_EMPLOYEE_PROP6.setColumn(COLUMN_MANAGEMENT_ROLE_EMPLOYEE);

        LEVEL_EMPLOYEE_ID = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_ID.setName("Employee Id");
        LEVEL_EMPLOYEE_ID.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_EMPLOYEE_ID.setColumn(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        LEVEL_EMPLOYEE_ID.setNameColumn(COLUMN_FULL_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_ID.setId("_level_employe_id");
        LEVEL_EMPLOYEE_ID.setUniqueMembers(true);
        LEVEL_EMPLOYEE_ID.getMemberProperties().addAll(List.of(LEVEL_EMPLOYEE_PROP1, LEVEL_EMPLOYEE_PROP2, LEVEL_EMPLOYEE_PROP3,
                LEVEL_EMPLOYEE_PROP4, LEVEL_EMPLOYEE_PROP5, LEVEL_EMPLOYEE_PROP6));

        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setName("Country");
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setId("_level_country");
        LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY.setUniqueMembers(true);


        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setName("State Province");
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setColumn(COLUMN_STATE_PROVINCE_CUSTOMER);
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setId("_level_state_province");
        LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE.setUniqueMembers(true);

        LEVEL_CITY_TABLE_COLUMN_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CITY_TABLE_COLUMN_CITY.setName("City");
        LEVEL_CITY_TABLE_COLUMN_CITY.setColumn(COLUMN_CITY_CUSTOMER);
        LEVEL_CITY_TABLE_COLUMN_CITY.setId("_level_city");
        LEVEL_CITY_TABLE_COLUMN_CITY.setUniqueMembers(false);

        LEVEL_NAME_PROP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP1.setName("Gender");
        LEVEL_NAME_PROP1.setColumn(COLUMN_GENDER_CUSTOMER);

        LEVEL_NAME_PROP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP2.setName("Marital Status");
        LEVEL_NAME_PROP2.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);

        LEVEL_NAME_PROP3 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP3.setName("Education");
        LEVEL_NAME_PROP3.setColumn(COLUMN_EDUCATION_CUSTOMER);

        LEVEL_NAME_PROP4 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        LEVEL_NAME_PROP4.setName("Yearly Income");
        LEVEL_NAME_PROP4.setColumn(COLUMN_YEARLY_INCOME_CUSTOMER);

        LEVEL_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_NAME.setName("Name");
        LEVEL_NAME.setColumn(COLUMN_CUSTOMER_ID_CUSTOMER);
        LEVEL_NAME.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_NAME.setUniqueMembers(true);
        LEVEL_NAME.setNameColumn(COLUMN_FULLNAME_CUSTOMER); //TODO
        LEVEL_NAME.getMemberProperties().addAll(List.of(LEVEL_NAME_PROP1, LEVEL_NAME_PROP2, LEVEL_NAME_PROP3, LEVEL_NAME_PROP4));

        /*
                .withNameColumn(SQLExpressionMappingColumnImpl.builder()
                    .withSqls(List.of(
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_ORACLE,
                                DIALECT_H2,
                                DIALECT_HSQLDB,
                                DIALECT_POSTGRES,
                                DIALECT_LUCIDDB,
                                DIALECT_TERADATA
                            ))
                            .withSql("\"fname\" || ' ' || \"lname\"")
                            .build(),
                            SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_HIVE
                            ))
                            .withSql("`customer`.`fullname`")
                            .build(),
                            SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_ACCESS,
                                DIALECT_MSSQL
                            ))
                            .withSql("fname + ' ' + lname")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_MYSQL,
                                DIALECT_MARIADB
                            ))
                            .withSql("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_DERBY,
                                DIALECT_NEOVIEW,
                                DIALECT_SNOWFLAKE
                            ))
                            .withSql("\"customer\".\"fullname\"")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_DB2
                            ))
                            .withSql("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_GENERIC
                            ))
                            .withSql("fullname")
                            .build()
                    ))
                    .withDataType(ColumnDataType.VARCHAR)
                    .build())
                .withOrdinalColumn(SQLExpressionMappingColumnImpl.builder()
                    .withSqls(List.of(
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_ORACLE,
                                DIALECT_H2,
                                DIALECT_HSQLDB,
                                DIALECT_POSTGRES,
                                DIALECT_LUCIDDB
                            ))
                            .withSql("\"fname\" || ' ' || \"lname\"")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_ACCESS,
                                DIALECT_MSSQL
                            ))
                            .withSql("fname + ' ' + lname")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_MYSQL,
                                DIALECT_MARIADB
                            ))
                            .withSql("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_NEOVIEW,
                                DIALECT_DERBY,
                                DIALECT_SNOWFLAKE
                            ))
                            .withSql("\"customer\".\"fullname\"")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_DB2
                            ))
                            .withSql("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                            .build(),
                        SqlStatementMappingImpl.builder()
                            .withDialects(List.of(
                                DIALECT_GENERIC
                            ))
                            .withSql("fullname")
                            .build()
                    ))
                    .withDataType(ColumnDataType.VARCHAR)
                    .build())
                .withMemberProperties(List.of(
                    MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_GENDER).withColumn(GENDER_COLUMN_IN_CUSTOMER).build(),
                    MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_MARITAL_STATUS).withColumn(MARITAL_STATUS_COLUMN_IN_CUSTOMER).build(),
                    MemberPropertyMappingImpl.builder().withName("Education").withColumn(EDUCATION_COLUMN_IN_CUSTOMER).build(),
                    MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_YEARLY_INCOME).withColumn(YEARLY_INCOME_COLUMN_IN_CUSTOMER).build()
                ))
                .build();
            */


        // Initialize hierarchies
        HIERARCHY_TIME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME.setId("_hierarchy_time");
        HIERARCHY_TIME.setHasAll(false);
        HIERARCHY_TIME.setPrimaryKey(COLUMN_TIME_ID_TIME_BY_DAY);
        HIERARCHY_TIME.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_TIME.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH));

        HIERARCHY_HR_TIME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_TIME.setId("_hierarchy_hr_time");
        HIERARCHY_HR_TIME.setHasAll(false);
        HIERARCHY_HR_TIME.setPrimaryKey(COLUMN_THE_DATE_TIME_BY_DAY);
        HIERARCHY_HR_TIME.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_HR_TIME.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR));

        HIERARCHY_TIME2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME2.setId("_hierarchy_time1");
        HIERARCHY_TIME2.setHasAll(true);
        HIERARCHY_TIME2.setPrimaryKey(COLUMN_TIME_ID_TIME_BY_DAY);
        HIERARCHY_TIME2.setName("Weekly");
        HIERARCHY_TIME2.setQuery(QUERY_TIME_BY_DAY);
        HIERARCHY_TIME2.getLevels().addAll(List.of(LEVEL_YEAR, LEVEL_WEEK, LEVEL_DAY));


        HIERARCHY_STORE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_STORE.setName("Store Hierarchy");
        HIERARCHY_STORE.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE.setId("_hierarchy_store");
        HIERARCHY_STORE.setHasAll(true);
        //HIERARCHY_STORE.setAllMemberName("All Stores");
        HIERARCHY_STORE.setQuery(QUERY_STORE);
        HIERARCHY_STORE.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE, LEVEL_STORE_CITY, LEVEL_STORE_NAME));

        HIERARCHY_STORE_SALES_RAGGED = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_SALES_RAGGED.setPrimaryKey(COLUMN_STORE_ID_STORE_RAGGED);
        HIERARCHY_STORE_SALES_RAGGED.setId("_hierarchy_store_sales_ragged");
        HIERARCHY_STORE_SALES_RAGGED.setHasAll(true);
        HIERARCHY_STORE_SALES_RAGGED.setQuery(QUERY_STORE_RAGGED);
        HIERARCHY_STORE_SALES_RAGGED.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY_WITH_NEVER, LEVEL_STORE_CYTY_IF_PARENTS_NAME,
                        LEVEL_STORE_CYTY_IF_BLANK_NAME, LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER));

        HIERARCHY_HR_STORE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_STORE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_HR_STORE.setId("_hierarchy_hr_store");
        HIERARCHY_HR_STORE.setHasAll(true);
        HIERARCHY_HR_STORE.setQuery(JOIN_EMPLOYEE_STORE);
        HIERARCHY_HR_STORE.getLevels()
                .addAll(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE,
                        LEVEL_STORE_CITY, LEVEL_STORE_NAME));

        HIERARCHY_PAY_TYPE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PAY_TYPE.setId("_hierarchy_store_pay_type");
        HIERARCHY_PAY_TYPE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_PAY_TYPE.setQuery(JOIN_EMPLOYEE_POSITION);
        HIERARCHY_PAY_TYPE.setHasAll(true);
        HIERARCHY_PAY_TYPE.getLevels().addAll(List.of(LEVEL_PAY_TYPE));

        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setId("_hierarchy_store_type_query_employee");
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setQuery(JOIN_EMPLOYEE_STORE);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.setHasAll(true);
        HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE.getLevels().addAll(List.of(LEVEL_STORE_TYPE));

        HIERARCHY_STORE_HAS_COFFEE_BAR = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_HAS_COFFEE_BAR.setId("_hierarchy_store_has_coffe_bar");
        HIERARCHY_STORE_HAS_COFFEE_BAR.setHasAll(true);
        HIERARCHY_STORE_HAS_COFFEE_BAR.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_HAS_COFFEE_BAR.getLevels().addAll(List.of(LEVEL_STORE_HAS_COFFEE_BAR));

        HIERARCHY_CUSTOMER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMER.setHasAll(true);
        HIERARCHY_CUSTOMER.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMER.setId("_hierarchy_customer");
        HIERARCHY_CUSTOMER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMER.getLevels()
                .addAll(List.of(LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE, LEVEL_CITY_TABLE_COLUMN_CITY, LEVEL_NAME));

        HIERARCHY_CUSTOMERS_GEO = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GEO.setName("Geography");
        HIERARCHY_CUSTOMERS_GEO.setId("_hierarchy_customer_geography");
        HIERARCHY_CUSTOMERS_GEO.setHasAll(true);
        HIERARCHY_CUSTOMERS_GEO.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMERS_GEO.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);//
        HIERARCHY_CUSTOMERS_GEO.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GEO.getLevels().addAll(
                List.of(LEVEL_CUSTOMER_COUNTRY, LEVEL_CUSTOMER_STATE, LEVEL_CUSTOMER_CITY, LEVEL_CUSTOMER_NAME));

        HIERARCHY_CUSTOMERS_GENDER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_GENDER.setName("Gender");
        HIERARCHY_CUSTOMERS_GENDER.setId("_hierarchy_customer_gender");
        HIERARCHY_CUSTOMERS_GENDER.setHasAll(true);
        HIERARCHY_CUSTOMERS_GENDER.setAllMemberName("All Gender");
        HIERARCHY_CUSTOMERS_GENDER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_GENDER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_GENDER.getLevels().add(LEVEL_CUSTOMER_GENDER);

        HIERARCHY_PRODUCT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PRODUCT.setName("Product Hierarchy");
        HIERARCHY_PRODUCT.setId("_hierarchy_product");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setPrimaryKey(COLUMN_PRODUCT_ID_PRODUCT);
        //HIERARCHY_PRODUCT.setAllMemberName("All Products");
        HIERARCHY_PRODUCT.setQuery(JOIN_PRODUCT_PRODUCT_CLASS);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(LEVEL_PRODUCT_FAMILY, LEVEL_PRODUCT_DEPARTMENT,
                LEVEL_PRODUCT_CATEGORY, LEVEL_PRODUCT_SUBCATEGORY, LEVEL_PRODUCT_BRAND, LEVEL_PRODUCT_NAME));

        HIERARCHY_STORE_SIZE_IN_SQFT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_SIZE_IN_SQFT.setId("_hierarchy_store_size_in_sqft");
        HIERARCHY_STORE_SIZE_IN_SQFT.setHasAll(true);
        HIERARCHY_STORE_SIZE_IN_SQFT.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_SIZE_IN_SQFT.setQuery(QUERY_STORE);
        HIERARCHY_STORE_SIZE_IN_SQFT.getLevels().add(LEVEL_STORE_SQFT);

        HIERARCHY_PROMOTIONS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PROMOTIONS.setHasAll(true);
        HIERARCHY_PROMOTIONS.setId("_hierarchy_promotions");
        HIERARCHY_PROMOTIONS.setAllMemberName("All Promotions");
        HIERARCHY_PROMOTIONS.setPrimaryKey(COLUMN_PROMOTION_ID_PROMOTION);
        HIERARCHY_PROMOTIONS.setDefaultMember("All Promotions");
        HIERARCHY_PROMOTIONS.setQuery(QUERY_PROMOTION);
        HIERARCHY_PROMOTIONS.getLevels().add(LEVEL_PROMOTION_NAME);

        HIERARCHY_STORE_TYPE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE.setHasAll(true);
        HIERARCHY_STORE_TYPE.setId("_hierarchy_store_type");
        HIERARCHY_STORE_TYPE.setPrimaryKey(COLUMN_STORE_ID_STORE);
        HIERARCHY_STORE_TYPE.setQuery(QUERY_STORE);
        HIERARCHY_STORE_TYPE.getLevels().add(LEVEL_STORE_TYPE_WITHOUT_TABLE);

        HIERARCHY_STORE_TYPE_WITHOUT_TABLE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.setPrimaryKey(COLUMN_STORE_ID_STORE); //remove that
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.setHasAll(true);
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.setId("_hierarchy_store_type_without_table");
        HIERARCHY_STORE_TYPE_WITHOUT_TABLE.getLevels().add(LEVEL_STORE_TYPE_WITHOUT_TABLE);

        HIERARCHY_EDUCATION_LEVEL = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_EDUCATION_LEVEL.setHasAll(true);
        HIERARCHY_EDUCATION_LEVEL.setId("_hierarchy_education_level");
        HIERARCHY_EDUCATION_LEVEL.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_EDUCATION_LEVEL.setQuery(QUERY_CUSTOMER);
        HIERARCHY_EDUCATION_LEVEL.getLevels().add(LEVEL_EDUCATION);

        HIERARCHY_GENDER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GENDER.setHasAll(true);
        HIERARCHY_GENDER.setId("_hierarchy_gender");
        HIERARCHY_GENDER.setAllMemberName("All Gender");
        HIERARCHY_GENDER.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_GENDER.setQuery(QUERY_CUSTOMER);
        HIERARCHY_GENDER.getLevels().add(LEVEL_GENDER);

        HIERARCHY_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_MARITAL_STATUS.setHasAll(true);
        HIERARCHY_MARITAL_STATUS.setId("_hierarchy_marital_status");
        HIERARCHY_MARITAL_STATUS.setAllMemberName("All Marital Status");
        HIERARCHY_MARITAL_STATUS.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_MARITAL_STATUS.setQuery(QUERY_CUSTOMER);
        HIERARCHY_MARITAL_STATUS.getLevels().add(LEVEL_MARITAL_STATUS);

        HIERARCHY_YEARLY_INCOME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_YEARLY_INCOME.setHasAll(true);
        HIERARCHY_YEARLY_INCOME.setId("_hierarchy_yerly_income");
        HIERARCHY_YEARLY_INCOME.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_YEARLY_INCOME.setQuery(QUERY_CUSTOMER);
        HIERARCHY_YEARLY_INCOME.getLevels().add(LEVEL_YEARLY_INCOME);

        // Initialize new hierarchies
        HIERARCHY_WAREHOUSE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_WAREHOUSE.setName("Warehouse");
        HIERARCHY_WAREHOUSE.setId("_hierarchy_warehouse");
        HIERARCHY_WAREHOUSE.setHasAll(true);
        HIERARCHY_WAREHOUSE.setAllMemberName("All Warehouses");
        HIERARCHY_WAREHOUSE.setPrimaryKey(COLUMN_WAREHOUSE_ID_WAREHOUSE);
        HIERARCHY_WAREHOUSE.setQuery(QUERY_WAREHOUSE);
        HIERARCHY_WAREHOUSE.getLevels().addAll(
                List.of(LEVEL_WAREHOUSE_COUNTRY, LEVEL_WAREHOUSE_STATE, LEVEL_WAREHOUSE_CITY, LEVEL_WAREHOUSE_NAME));

        HIERARCHY_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PROMOTION_MEDIA.setName("Promotion Media");
        HIERARCHY_PROMOTION_MEDIA.setId("_hierarchy_promotion_media");
        HIERARCHY_PROMOTION_MEDIA.setHasAll(true);
        HIERARCHY_PROMOTION_MEDIA.setPrimaryKey(COLUMN_PROMOTION_ID_PROMOTION);
        HIERARCHY_PROMOTION_MEDIA.setAllMemberName("All Media");
        HIERARCHY_PROMOTION_MEDIA.setQuery(QUERY_PROMOTION);
        HIERARCHY_PROMOTION_MEDIA.getLevels().addAll(List.of(LEVEL_PROMOTION_MEDIA));

        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK = RolapMappingFactory.eINSTANCE.createParentChildLink();
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setParentColumn(COLUMN_SUPERVISOR_ID_EMPLOYEE_CLOSURE);
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setChildColumn(COLUMN_EMPLOYEE_ID_EMPLOYEE_CLOSURE);
        HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK.setTable(QUERY_EMPLOYEE_CLOSURE);

        HIERARCHY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        //HIERARCHY_EMPLOYEE.setName("Employee");
        HIERARCHY_EMPLOYEE.setId("_hierarchy_employee");
        HIERARCHY_EMPLOYEE.setHasAll(true);
        HIERARCHY_EMPLOYEE.setAllMemberName("All Employees");
        HIERARCHY_EMPLOYEE.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setQuery(QUERY_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setParentColumn(COLUMN_SUPERVISOR_ID_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setNullParentValue("0");
        HIERARCHY_EMPLOYEE.setParentChildLink(HIERARCHY_EMPLOYEE_PARENT_CHILD_LINK);
        HIERARCHY_EMPLOYEE.setLevel(LEVEL_EMPLOYEE_ID);

        HIERARCHY_DEPARTMENT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEPARTMENT.setName("Department");
        HIERARCHY_DEPARTMENT.setId("_hierarchy_department");
        HIERARCHY_DEPARTMENT.setHasAll(true);
        HIERARCHY_DEPARTMENT.setPrimaryKey(COLUMN_DEPARTMENT_ID_DEPARTMENT);
        //HIERARCHY_DEPARTMENT.setAllMemberName("All Departments");
        HIERARCHY_DEPARTMENT.setQuery(QUERY_DEPARTMENT);
        HIERARCHY_DEPARTMENT.getLevels().addAll(List.of(LEVEL_DEPARTMENT_DESCRIPTION));

        HIERARCHY_POSITION = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_POSITION.setName("Position");
        HIERARCHY_POSITION.setId("_hierarchy_position");
        HIERARCHY_POSITION.setHasAll(true);
        HIERARCHY_POSITION.setAllMemberName("All Positions");
        HIERARCHY_POSITION.setPrimaryKey(COLUMN_POSITION_ID_POSITION);
        HIERARCHY_POSITION.setQuery(QUERY_POSITION);
        HIERARCHY_POSITION.getLevels().addAll(List.of(LEVEL_POSITION_TITLE));

        HIERARCHY_HR_POSITION = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HR_POSITION.setName("Position");
        HIERARCHY_HR_POSITION.setId("_hierarchy_hr_position");
        HIERARCHY_HR_POSITION.setHasAll(true);
        HIERARCHY_HR_POSITION.setAllMemberName("All Positions");
        HIERARCHY_HR_POSITION.setPrimaryKey(COLUMN_EMPLOYEE_ID_EMPLOYEE);
        HIERARCHY_HR_POSITION.setQuery(QUERY_EMPLOYEE);
        HIERARCHY_HR_POSITION.getLevels().addAll(List.of(LEVEL_MANAGEMENT_ROLE, LEVEL_HR_POSITION_TITLE));

        HIERARCHY_CUSTOMERS_EDUCATION = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_EDUCATION.setName("Customers Education");
        HIERARCHY_CUSTOMERS_EDUCATION.setId("_hierarchy_customers_education");
        HIERARCHY_CUSTOMERS_EDUCATION.setHasAll(true);
        HIERARCHY_CUSTOMERS_EDUCATION.setAllMemberName("All Education Levels");
        HIERARCHY_CUSTOMERS_EDUCATION.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_EDUCATION.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_EDUCATION.getLevels().addAll(List.of(LEVEL_CUSTOMER_EDUCATION));

        HIERARCHY_CUSTOMERS_MARITAL = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMERS_MARITAL.setName("Customers Marital Status");
        HIERARCHY_CUSTOMERS_MARITAL.setId("_hierarchy_customers_marital");
        HIERARCHY_CUSTOMERS_MARITAL.setHasAll(true);
        HIERARCHY_CUSTOMERS_MARITAL.setAllMemberName("All Marital Statuses");
        HIERARCHY_CUSTOMERS_MARITAL.setPrimaryKey(COLUMN_CUSTOMER_ID_CUSTOMER);
        HIERARCHY_CUSTOMERS_MARITAL.setQuery(QUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS_MARITAL.getLevels().addAll(List.of(LEVEL_CUSTOMER_MARITAL_STATUS));

        HIERARCHY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GEOGRAPHY.setHasAll(true);
        HIERARCHY_GEOGRAPHY.setPrimaryKey(COLUMN_STORE_ID_STORE_RAGGED);
        HIERARCHY_GEOGRAPHY.setQuery(QUERY_STORE_RAGGED);
        HIERARCHY_GEOGRAPHY.getLevels().addAll(List.of(LEVEL_COUNTRY_WITH_NEVER, LEVEL_STATE, LEVEL_CITY_TABLE_COLUMN_STORE_CITY));

        // Initialize dimensions
        DIMENSION_TIME = RolapMappingFactory.eINSTANCE.createTimeDimension();
        DIMENSION_TIME.setName("Time");
        DIMENSION_TIME.setId("_dimension_time");
        DIMENSION_TIME.getHierarchies().addAll(List.of(HIERARCHY_TIME, HIERARCHY_TIME2));

        DIMENSION_HR_TIME = RolapMappingFactory.eINSTANCE.createTimeDimension();
        DIMENSION_HR_TIME.setName("Time");
        DIMENSION_HR_TIME.setId("_dimension_hr_time");
        DIMENSION_HR_TIME.getHierarchies().addAll(List.of(HIERARCHY_HR_TIME));

        DIMENSION_STORE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE.setName("Store");
        DIMENSION_STORE.setId("_dimension_store");
        DIMENSION_STORE.getHierarchies().add(HIERARCHY_STORE);

        DIMENSION_STORE_SALES_RAGGED = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_SALES_RAGGED.setName("Store");
        DIMENSION_STORE_SALES_RAGGED.setId("_dimension_store_sales_ragged");
        DIMENSION_STORE_SALES_RAGGED.getHierarchies().add(HIERARCHY_STORE_SALES_RAGGED);

        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE.setName("Store");
        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE.setId("_dimension_hr_store");
        DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE.getHierarchies().add(HIERARCHY_HR_STORE);

        DIMENSION_PAY_TYPE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PAY_TYPE.setName("Pay Type");
        DIMENSION_PAY_TYPE.setId("_dimension_pay_type");
        DIMENSION_PAY_TYPE.getHierarchies().add(HIERARCHY_PAY_TYPE);

        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE.setName("Store Type");
        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE.setId("_dimension_store_type_query_employee");
        DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE.getHierarchies().add(HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE);

        DIMENSION_STORE_HAS_COFFEE_BAR = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_HAS_COFFEE_BAR.setName("Has coffee bar");
        DIMENSION_STORE_HAS_COFFEE_BAR.setId("_dimension_store_has_coffee_bar");
        DIMENSION_STORE_HAS_COFFEE_BAR.getHierarchies().add(HIERARCHY_STORE_HAS_COFFEE_BAR);

        DIMENSION_CUSTOMERS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMERS.setName("Customers");
        DIMENSION_CUSTOMERS.setId("_dimension_customers");
        DIMENSION_CUSTOMERS.getHierarchies().addAll(List.of(HIERARCHY_CUSTOMER));

        DIMENSION_PRODUCT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PRODUCT.setName("Product");
        DIMENSION_PRODUCT.setId("_dimension_product");
        DIMENSION_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        DIMENSION_STORE_SIZE_IN_SQFT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_SIZE_IN_SQFT.setName("Store Size in SQFT");
        DIMENSION_STORE_SIZE_IN_SQFT.setId("_dimension_store_size_in_sqft");
        DIMENSION_STORE_SIZE_IN_SQFT.getHierarchies().add(HIERARCHY_STORE_SIZE_IN_SQFT);

        DIMENSION_PROMOTIONS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PROMOTIONS.setName("Promotions");
        DIMENSION_PROMOTIONS.setId("_dimension_promotions");
        DIMENSION_PROMOTIONS.getHierarchies().add(HIERARCHY_PROMOTIONS);

        DIMENSION_STORE_TYPE_WITH_QUERY_STORE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITH_QUERY_STORE.setName("Store Type");
        DIMENSION_STORE_TYPE_WITH_QUERY_STORE.setId("_dimension_store_type");
        DIMENSION_STORE_TYPE_WITH_QUERY_STORE.getHierarchies().add(HIERARCHY_STORE_TYPE);

        DIMENSION_STORE_TYPE_WITHOUT_QUERY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE_WITHOUT_QUERY.setName("Store Type");
        DIMENSION_STORE_TYPE_WITHOUT_QUERY.setId("_dimension_store_type_without_query");
        DIMENSION_STORE_TYPE_WITHOUT_QUERY.getHierarchies().add(HIERARCHY_STORE_TYPE_WITHOUT_TABLE);

        DIMENSION_EDUCATION_LEVEL = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_EDUCATION_LEVEL.setName("Education Level");
        DIMENSION_EDUCATION_LEVEL.setId("_dimension_education_level");
        DIMENSION_EDUCATION_LEVEL.getHierarchies().add(HIERARCHY_EDUCATION_LEVEL);

        DIMENSION_GENDER = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GENDER.setName("Gender");
        DIMENSION_GENDER.setId("_dimension_gender");
        DIMENSION_GENDER.getHierarchies().add(HIERARCHY_GENDER);

        DIMENSION_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_MARITAL_STATUS.setName("Marital Status");
        DIMENSION_MARITAL_STATUS.setId("_dimension_marital_status");
        DIMENSION_MARITAL_STATUS.getHierarchies().add(HIERARCHY_MARITAL_STATUS);

        DIMENSION_YEARLY_INCOME = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_YEARLY_INCOME.setName("Yearly Income");
        DIMENSION_YEARLY_INCOME.setId("_dimension_yearly_income");
        DIMENSION_YEARLY_INCOME.getHierarchies().add(HIERARCHY_YEARLY_INCOME);

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

        DIMENSION_HR_POSITION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_HR_POSITION.setName("Position");
        DIMENSION_HR_POSITION.setId("_dimension_hr_position");
        DIMENSION_HR_POSITION.getHierarchies().add(HIERARCHY_HR_POSITION);

        DIMENSION_STORE_TYPE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STORE_TYPE.setName("Store Type");
        DIMENSION_STORE_TYPE.getHierarchies().add(HIERARCHY_STORE_TYPE);

        DIMENSION_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GEOGRAPHY.setName("Geography");
        DIMENSION_GEOGRAPHY.getHierarchies().add(HIERARCHY_GEOGRAPHY);

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
        MEASURE_STORE_SALES.setFormatString("#,###.00");

        MEASURE_STORE_COST = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST.setName("Store Cost");
        MEASURE_STORE_COST.setId("_measure_storeCost");
        MEASURE_STORE_COST.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST.setFormatString("#,###.00");

        MEASURE_SALES_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT.setName("Sales Count");
        MEASURE_SALES_COUNT.setId("_measure_salesCount");
        MEASURE_SALES_COUNT.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT.setFormatString("#,###");

        MEASURE_CUSTOMER_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT.setId("_measure_customerCount");
        MEASURE_CUSTOMER_COUNT.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT.setDistinct(true);

        MEASURE_PROMOTION_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_PROMOTION_SALES.setName("Promotion Sales");
        MEASURE_PROMOTION_SALES.setId("_measure_customerCount");
        MEASURE_PROMOTION_SALES.setColumn(MEASURE_PROMOTION_SALES_COL);
        MEASURE_PROMOTION_SALES.setFormatString("#,###.00");

        // Initialize warehouse measures
        MEASURE_WAREHOUSE_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_SALES.setName("Warehouse Sales");
        MEASURE_WAREHOUSE_SALES.setId("_measure_warehouseSales");
        MEASURE_WAREHOUSE_SALES.setColumn(COLUMN_WAREHOUSE_SALES_INVENTORY_FACT);

        MEASURE_WAREHOUSE_STORE_INVOICE = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_STORE_INVOICE.setName("Store Invoice");
        MEASURE_WAREHOUSE_STORE_INVOICE.setId("_measure_warehouseStoreInvoice");
        MEASURE_WAREHOUSE_STORE_INVOICE.setColumn(COLUMN_STORE_INVOICE_INVENTORY_FACT);

        MEASURE_WAREHOUSE_SUPPLY_TIME = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_SUPPLY_TIME.setName("Supply Time");
        MEASURE_WAREHOUSE_SUPPLY_TIME.setId("_measure_warehouseSupplyTime");
        MEASURE_WAREHOUSE_SUPPLY_TIME.setColumn(COLUMN_SUPPLY_TIME_INVENTORY_FACT);

        MEASURE_WAREHOUSE_PROFIT = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_PROFIT.setName("Warehouse Profit");
        MEASURE_WAREHOUSE_PROFIT.setId("_measure_warehouseWarehouseProfit");
        MEASURE_WAREHOUSE_PROFIT.setColumn(MEASURE_WAREHOUSE_PROFIT_COL);

        MEASURE_WAREHOUSE_COST = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_WAREHOUSE_COST.setName("Warehouse Cost");
        MEASURE_WAREHOUSE_COST.setId("_measure_warehouseCost");
        MEASURE_WAREHOUSE_COST.setColumn(COLUMN_WAREHOUSE_COST_INVENTORY_FACT);
        MEASURE_WAREHOUSE_COST.setFormatString("$#,##0.00");

        MEASURE_UNITS_SHIPPED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_SHIPPED.setName("Units Shipped");
        MEASURE_UNITS_SHIPPED.setId("_measure_unitsShipped");
        MEASURE_UNITS_SHIPPED.setColumn(COLUMN_UNITS_SHIPPED_INVENTORY_FACT);
        MEASURE_UNITS_SHIPPED.setFormatString("#.0");

        MEASURE_UNITS_ORDERED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNITS_ORDERED.setName("Units Ordered");
        MEASURE_UNITS_ORDERED.setId("_measure_unitsOrdered");
        MEASURE_UNITS_ORDERED.setColumn(COLUMN_UNITS_ORDERED_INVENTORY_FACT);
        MEASURE_UNITS_ORDERED.setFormatString("#.0");

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
        MEASURE_ORG_SALARY.setFormatString("Currency");

        MEASURE_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_COUNT.setName("Count");
        MEASURE_COUNT.setId("_measure_count");
        MEASURE_COUNT.setColumn(COLUMN_EMPLOYEE_ID_SALARY);
        MEASURE_COUNT.setFormatString("#,#");

        MEASURE_NUMBER_OF_EMPLOYEES = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_NUMBER_OF_EMPLOYEES.setName("Number of Employees");
        MEASURE_NUMBER_OF_EMPLOYEES.setId("_measure_numberOfEmployees");
        MEASURE_NUMBER_OF_EMPLOYEES.setColumn(COLUMN_EMPLOYEE_ID_SALARY);
        MEASURE_NUMBER_OF_EMPLOYEES.setFormatString("#,#");

        MEASURE_UNIT_SALES_RAGGED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES_RAGGED.setName("Unit Sales");
        MEASURE_UNIT_SALES_RAGGED.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES_RAGGED.setFormatString("Standard");

        MEASURE_STORE_COST_RAGGED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST_RAGGED.setName("Store Cost");
        MEASURE_STORE_COST_RAGGED.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST_RAGGED.setFormatString("#,###");

        MEASURE_STORE_SALES_RAGGED = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES_RAGGED.setName("Store Sales");
        MEASURE_STORE_SALES_RAGGED.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES_RAGGED.setFormatString("#,###");

        MEASURE_SALES_COUNT_RAGGED = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT_RAGGED.setName("Sales Count");
        MEASURE_SALES_COUNT_RAGGED.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT_RAGGED.setFormatString("#,###");

        MEASURE_CUSTOMER_COUNT_RAGGED = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT_RAGGED.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT_RAGGED.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT_RAGGED.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT_RAGGED.setDistinct(true);

        MEASURE_SALES_COUNT_PROP = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_SALES_COUNT_PROP.setName("MEMBER_ORDINAL");
        MEASURE_SALES_COUNT_PROP.setValue("1");

        MEASURE_SALES_COUNT_WITH_PROPERTY = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_SALES_COUNT_WITH_PROPERTY.setName("Sales Count");
        MEASURE_SALES_COUNT_WITH_PROPERTY.setColumn(COLUMN_PRODUCT_ID_SALESFACT);
        MEASURE_SALES_COUNT_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_SALES_COUNT_PROP);

        MEASURE_UNIT_SALES_PROP = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_UNIT_SALES_PROP.setName("MEMBER_ORDINAL");
        MEASURE_UNIT_SALES_PROP.setValue("2");

        MEASURE_UNIT_SALES_MEMBER_ORDINAL = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.setName("Unit Sales");
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.setColumn(COLUMN_UNIT_SALES_SALESFACT);
        MEASURE_UNIT_SALES_MEMBER_ORDINAL.getCalculatedMemberProperties().add(MEASURE_UNIT_SALES_PROP);

        MEASURE_STORE_SALES_PROP = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_STORE_SALES_PROP.setName("MEMBER_ORDINAL");
        MEASURE_STORE_SALES_PROP.setValue("3");

        MEASURE_STORE_SALES_WITH_PROPERTY = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_SALES_WITH_PROPERTY.setName("Store Sales");
        MEASURE_STORE_SALES_WITH_PROPERTY.setColumn(COLUMN_STORE_SALES_SALESFACT);
        MEASURE_STORE_SALES_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_STORE_SALES_PROP);

        MEASURE_STORE_COST_PROP = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_STORE_COST_PROP.setName("MEMBER_ORDINAL");
        MEASURE_STORE_COST_PROP.setValue("6");

        MEASURE_STORE_COST_WITH_PROPERTY = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_STORE_COST_WITH_PROPERTY.setName("Store Cost");
        MEASURE_STORE_COST_WITH_PROPERTY.setColumn(COLUMN_STORE_COST_SALESFACT);
        MEASURE_STORE_COST_WITH_PROPERTY.setFormatString("#,###.00");
        MEASURE_STORE_COST_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_STORE_COST_PROP);

        MEASURE_CUSTOMER_COUNT_PROP = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        MEASURE_CUSTOMER_COUNT_PROP.setName("MEMBER_ORDINAL");
        MEASURE_CUSTOMER_COUNT_PROP.setValue("7");

        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setName("Customer Count");
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setColumn(COLUMN_CUSTOMER_ID_SALESFACT);
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.setFormatString("#,###");
        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY.getCalculatedMemberProperties().add(MEASURE_CUSTOMER_COUNT_PROP);

        // Initialize measure groups
        MEASUREGROUP_SALES = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_SALES.getMeasures()
                .addAll(List.of(MEASURE_UNIT_SALES, MEASURE_STORE_COST, MEASURE_STORE_SALES, MEASURE_SALES_COUNT, MEASURE_CUSTOMER_COUNT, MEASURE_PROMOTION_SALES));

        MEASUREGROUP_WAREHOUSE = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_WAREHOUSE.getMeasures().addAll(
                List.of(MEASURE_WAREHOUSE_STORE_INVOICE, MEASURE_WAREHOUSE_SUPPLY_TIME, MEASURE_WAREHOUSE_COST, MEASURE_WAREHOUSE_SALES, MEASURE_UNITS_SHIPPED,
                        MEASURE_UNITS_ORDERED, MEASURE_WAREHOUSE_PROFIT));

        MEASUREGROUP_STORE = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_STORE.getMeasures().addAll(List.of(MEASURE_STORE_SQFT, MEASURE_GROCERY_SQFT));

        MEASUREGROUP_HR = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_HR.getMeasures().addAll(List.of(MEASURE_ORG_SALARY, MEASURE_COUNT, MEASURE_NUMBER_OF_EMPLOYEES));

        MEASUREGROUP_RAGGED = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_RAGGED.getMeasures().addAll(List.of(MEASURE_UNIT_SALES_RAGGED,
        MEASURE_STORE_COST_RAGGED,
        MEASURE_STORE_SALES_RAGGED,
        MEASURE_SALES_COUNT_RAGGED,
        MEASURE_CUSTOMER_COUNT_RAGGED));

        MEASUREGROUP_SALES2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_SALES2.getMeasures()
                .addAll(List.of(MEASURE_SALES_COUNT_WITH_PROPERTY, MEASURE_UNIT_SALES_MEMBER_ORDINAL,
                        MEASURE_STORE_SALES_WITH_PROPERTY, MEASURE_STORE_COST_WITH_PROPERTY,
                        MEASURE_CUSTOMER_COUNT_WITH_PROPERTY));

        // Initialize dimension connectors
        CONNECTOR_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_TIME.setOverrideDimensionName("Time");
        CONNECTOR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);
        CONNECTOR_TIME.setId("_connector_time");

        CONNECTOR_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE.setOverrideDimensionName("Store");
        CONNECTOR_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_STORE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_STORE.setId("_connector_store");

        CONNECTOR_STORE_SIZE_IN_SOFT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_SIZE_IN_SOFT.setOverrideDimensionName("Store Size in SQFT");
        CONNECTOR_STORE_SIZE_IN_SOFT.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_STORE_SIZE_IN_SOFT.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_STORE_SIZE_IN_SOFT.setId("_connector_store_size_in_soft");

        CONNECTOR_STORE_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE);
        CONNECTOR_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_STORE_TYPE.setId("_connector_store_type");

        CONNECTOR_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PROMOTION_MEDIA.setOverrideDimensionName("Promotion Media");
        CONNECTOR_PROMOTION_MEDIA.setDimension(DIMENSION_PROMOTION_MEDIA);
        CONNECTOR_PROMOTION_MEDIA.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);
        CONNECTOR_PROMOTION_MEDIA.setId("_connector_promotion_media");

        CONNECTOR_PROMOTIONS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PROMOTIONS.setOverrideDimensionName("Promotions");
        CONNECTOR_PROMOTIONS.setDimension(DIMENSION_PROMOTIONS);
        CONNECTOR_PROMOTIONS.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);
        CONNECTOR_PROMOTIONS.setId("_connector_promotions");

        CONNECTOR_EDUCATION_LEVEL = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_EDUCATION_LEVEL.setOverrideDimensionName("Education Level");
        CONNECTOR_EDUCATION_LEVEL.setDimension(DIMENSION_EDUCATION_LEVEL);
        CONNECTOR_EDUCATION_LEVEL.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_EDUCATION_LEVEL.setId("_connector_education_level");

        CONNECTOR_GENDER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_GENDER.setId("_connector_gender");

        CONNECTOR_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_MARITAL_STATUS.setOverrideDimensionName("Marital Status");
        CONNECTOR_MARITAL_STATUS.setDimension(DIMENSION_MARITAL_STATUS);
        CONNECTOR_MARITAL_STATUS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_MARITAL_STATUS.setId("_connector_marital_status");

        CONNECTOR_YEARLY_INCOME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_YEARLY_INCOME.setOverrideDimensionName("Yearly Income");
        CONNECTOR_YEARLY_INCOME.setDimension(DIMENSION_YEARLY_INCOME);
        CONNECTOR_YEARLY_INCOME.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_YEARLY_INCOME.setId("_connector_yearly_income");

        CONNECTOR_CUSTOMER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMER.setOverrideDimensionName("Customers");
        CONNECTOR_CUSTOMER.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_CUSTOMER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_CUSTOMER.setId("_connector_customer");

        CONNECTOR_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);
        CONNECTOR_PRODUCT.setId("_connector_product");

        // Initialize warehouse cube connectors
        CONNECTOR_WAREHOUSE_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_TIME.setOverrideDimensionName("Time");
        CONNECTOR_WAREHOUSE_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_WAREHOUSE_TIME.setForeignKey(COLUMN_TIME_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_TIME.setId("_connector_warehouse_time");

        CONNECTOR_WAREHOUSE_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE.setOverrideDimensionName("Store");
        CONNECTOR_WAREHOUSE_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_WAREHOUSE_STORE.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_STORE.setId("_connector_warehouse_store");

        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setOverrideDimensionName("Store Size in SQFT");
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT.setId("_connector_warehouse_store_size_in_sqft");

        CONNECTOR_WAREHOUSE_STORE_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_WAREHOUSE_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE);
        CONNECTOR_WAREHOUSE_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_STORE_TYPE.setId("_connector_warehouse_store_size_in_sqft");

        CONNECTOR_WAREHOUSE_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_WAREHOUSE_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_WAREHOUSE_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_PRODUCT.setId("_connector_warehouse_product");

        CONNECTOR_WAREHOUSE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WAREHOUSE_WAREHOUSE.setOverrideDimensionName("Warehouse");
        CONNECTOR_WAREHOUSE_WAREHOUSE.setDimension(DIMENSION_WAREHOUSE);
        CONNECTOR_WAREHOUSE_WAREHOUSE.setForeignKey(COLUMN_WAREHOUSE_ID_INVENTORY_FACT);
        CONNECTOR_WAREHOUSE_WAREHOUSE.setId("_connector_warehouse_warehouse");

        // Initialize HR cube connectors
        CONNECTOR_HR_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_TIME.setOverrideDimensionName("Time");
        CONNECTOR_HR_TIME.setDimension(DIMENSION_HR_TIME);
        CONNECTOR_HR_TIME.setForeignKey(COLUMN_PAY_DATE_SALARY);
        CONNECTOR_HR_TIME.setId("_connector_hr_time");

        CONNECTOR_HR_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_STORE.setOverrideDimensionName("Store");
        CONNECTOR_HR_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_HR_STORE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_STORE.setId("_connector_hr_store");

        CONNECTOR_HR_PAY_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_PAY_TYPE.setOverrideDimensionName("Pay Type");
        CONNECTOR_HR_PAY_TYPE.setDimension(DIMENSION_PAY_TYPE);
        CONNECTOR_HR_PAY_TYPE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_PAY_TYPE.setId("_connector_hr_pay_type");

        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setOverrideDimensionName("Store Type");
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setDimension(DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE);
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE.setId("_connector_hr_store_type");

        CONNECTOR_HR_EMPLOYEE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_EMPLOYEE.setOverrideDimensionName("Employees");
        CONNECTOR_HR_EMPLOYEE.setDimension(DIMENSION_EMPLOYEE);
        CONNECTOR_HR_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_EMPLOYEE.setId("_connector_hr_employee");

        CONNECTOR_HR_POSITION = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_POSITION.setOverrideDimensionName("Position");
        CONNECTOR_HR_POSITION.setDimension(DIMENSION_POSITION);
        CONNECTOR_HR_POSITION.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_POSITION.setId("_connector_hr_position");

        CONNECTOR_HR_DEPARTMENT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HR_DEPARTMENT.setOverrideDimensionName("Department");
        CONNECTOR_HR_DEPARTMENT.setDimension(DIMENSION_DEPARTMENT);
        CONNECTOR_HR_DEPARTMENT.setForeignKey(COLUMN_EMPLOYEE_ID_SALARY);
        CONNECTOR_HR_DEPARTMENT.setId("_connector_hr_department");

        // Initialize Store cube connectors
        CONNECTOR_STORE_STORE_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_STORE_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE_WITHOUT_QUERY);
        CONNECTOR_STORE_STORE_TYPE.setId("_connector_store_store_type");

        CONNECTOR_STORE_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_STORE.setOverrideDimensionName("Store");
        CONNECTOR_STORE_STORE.setDimension(DIMENSION_STORE);
        CONNECTOR_STORE_STORE.setId("_connector_store_store");

        CONNECTOR_STORE_HAS_COFFEE_BAR = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STORE_HAS_COFFEE_BAR.setOverrideDimensionName("Has coffee bar");
        CONNECTOR_STORE_HAS_COFFEE_BAR.setDimension(DIMENSION_STORE_HAS_COFFEE_BAR);
        // This would need a coffee bar dimension, but for now using store
        CONNECTOR_STORE_HAS_COFFEE_BAR.setId("_connector_store_has_coffee_bar");

        CONNECTOR_SALES_RAGGED_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE.setOverrideDimensionName("Store");
        CONNECTOR_SALES_RAGGED_STORE.setDimension(DIMENSION_STORE_SALES_RAGGED);
        CONNECTOR_SALES_RAGGED_STORE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_STORE.setId("_connector_ragged_store_store");

        CONNECTOR_SALES_RAGGED_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setOverrideDimensionName("Geography");
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setDimension(DIMENSION_GEOGRAPHY);
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_GEOGRAPHY.setId("_connector_ragged_store_geography");

        CONNECTOR_SALES_RAGGED_STORE_SIZE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setOverrideDimensionName("Store Size in SqFt");
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setDimension(DIMENSION_STORE_SIZE_IN_SQFT);
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_STORE_SIZE.setId("_connector_ragged_store_store_size_in_sqft");

        CONNECTOR_SALES_RAGGED_STORE_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setOverrideDimensionName("Store Type");
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setDimension(DIMENSION_STORE_TYPE);
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setForeignKey(COLUMN_STORE_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_STORE_TYPE.setId("_connector_ragged_store_store_type");

        CONNECTOR_SALES_RAGGED_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_TIME.setOverrideDimensionName("Time");
        CONNECTOR_SALES_RAGGED_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_SALES_RAGGED_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_TIME.setId("_connector_ragged_store_time");

        CONNECTOR_SALES_2_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_TIME.setOverrideDimensionName("Time");
        CONNECTOR_SALES_2_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_SALES_2_TIME.setForeignKey(COLUMN_TIME_ID_SALESFACT);
        CONNECTOR_SALES_2_TIME.setId("_connector_sales2_time");

        CONNECTOR_SALES_RAGGED_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_SALES_RAGGED_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_SALES_RAGGED_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_PRODUCT.setId("_connector_ragged_product");

        CONNECTOR_SALES_2_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_PRODUCT.setOverrideDimensionName("Product");
        CONNECTOR_SALES_2_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_SALES_2_PRODUCT.setForeignKey(COLUMN_PRODUCT_ID_SALESFACT);
        CONNECTOR_SALES_2_PRODUCT.setId("_connector_sales2_product");

        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setOverrideDimensionName("Promotion Media");
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setDimension(DIMENSION_PROMOTION_MEDIA);
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_PROMOTION_MEDIA.setId("_connector_ragged_promotion_media");

        CONNECTOR_SALES_RAGGED_PROMOTIONS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setOverrideDimensionName("Promotions");
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setDimension(DIMENSION_PROMOTIONS);
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setForeignKey(COLUMN_PROMOTION_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_PROMOTIONS.setId("_connector_ragged_promotions");

        CONNECTOR_SALES_RAGGED_CUSTOMERS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setOverrideDimensionName("Customers");
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_CUSTOMERS.setId("_connector_ragged_customers");

        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setOverrideDimensionName("Education Level");
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setDimension(DIMENSION_EDUCATION_LEVEL);
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_EDUCATION_LEVEL.setId("_connector_ragged_education_level");

        CONNECTOR_SALES_RAGGED_GENDER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_SALES_RAGGED_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_SALES_RAGGED_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_GENDER.setId("_connector_ragged_gender");

        CONNECTOR_SALES_2_GENDER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_2_GENDER.setOverrideDimensionName("Gender");
        CONNECTOR_SALES_2_GENDER.setDimension(DIMENSION_GENDER);
        CONNECTOR_SALES_2_GENDER.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_2_GENDER.setId("_connector_sales2_gender");

        CONNECTOR_SALES_RAGGED_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setOverrideDimensionName("Marital Status");
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setDimension(DIMENSION_MARITAL_STATUS);
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_MARITAL_STATUS.setId("_connector_ragged_marital_status");

        CONNECTOR_SALES_RAGGED_YEARLY_INCOME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setOverrideDimensionName("Yearly Income");
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setDimension(DIMENSION_YEARLY_INCOME);
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setForeignKey(COLUMN_CUSTOMER_ID_SALESFACT);
        CONNECTOR_SALES_RAGGED_YEARLY_INCOME.setId("_connector_ragged_yearly_income");

        CALCULATED_MEMBER_PROP0 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP0.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROP0.setValue("$#,##0.00");

        // Initialize calculated members
        CALCULATED_MEMBER_PROFIT = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT.setName("Profit");
        CALCULATED_MEMBER_PROFIT.setId("_calculated_member_profit");
        CALCULATED_MEMBER_PROFIT.setFormula("[Measures].[Store Sales] - [Measures].[Store Cost]");
        CALCULATED_MEMBER_PROFIT.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROP0));

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

        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setName("FORMAT_STRING");
        PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setValue("$#,##0.00");

        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setName("Average Warehouse Sale");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.setId("_calculated_member_average_warehouse_sale");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE
                .setFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]");
        CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE.getCalculatedMemberProperties().add(PROPERTY_CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE);

        CALCULATED_MEMBER_EMPLOYEE_SALARY = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setName("Employee Salary");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setId("_calculated_member_employee_salary");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormatString("Currency");
        CALCULATED_MEMBER_EMPLOYEE_SALARY.setFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])");

        CALCULATED_MEMBER_AVG_SALARY = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_AVG_SALARY.setName("Avg Salary");
        CALCULATED_MEMBER_AVG_SALARY.setId("_calculated_member_avg_salary");
        CALCULATED_MEMBER_AVG_SALARY.setFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]");
        CALCULATED_MEMBER_AVG_SALARY.setFormatString("Currency");

        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setName("Profit Per Unit Shipped");
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setId("_calculated_member_profit_per_unit_shipped");
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setFormula("[Measures].[Profit] / [Measures].[Units Shipped]");
        PROFIT_PER_UNIT_SHIPPED_CALCULATED_MEMBER.setFormatString("Currency");

        CALCULATED_MEMBER_PROP1 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP1.setName("FORMAT_STRING");
        CALCULATED_MEMBER_PROP1.setValue("$#,##0.00");

        CALCULATED_MEMBER_PROP2 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP2.setName("MEMBER_ORDINAL");
        CALCULATED_MEMBER_PROP2.setValue("4");

        CALCULATED_MEMBER_PROP3 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        CALCULATED_MEMBER_PROP3.setName("MEMBER_ORDINAL");
        CALCULATED_MEMBER_PROP3.setValue("5");

        CALCULATED_MEMBER_PROFIT_WITH_ORDER = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.setName("Profit");
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.setId("_calculated_member_profit_with_order");
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.setFormula("[Measures].[Store Sales] - [Measures].[Store Cost]");
        CALCULATED_MEMBER_PROFIT_WITH_ORDER.getCalculatedMemberProperties().addAll(List.of(CALCULATED_MEMBER_PROP1, CALCULATED_MEMBER_PROP2));

        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.setName("Profit last Period");
        CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2.setId("_calculated_member_profit_last_period_sales2");
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

        NAMED_SET_TOP_SELLERS = RolapMappingFactory.eINSTANCE.createNamedSet();
        NAMED_SET_TOP_SELLERS.setName("Top Sellers");
        NAMED_SET_TOP_SELLERS.setId("namedSet_TopSellers");
        NAMED_SET_TOP_SELLERS.setFormula("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])");

        // Initialize cubes
        CUBE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES.setDefaultMeasure(MEASURE_UNIT_SALES);
        CUBE_SALES.setName("Sales");
        CUBE_SALES.setId("_cube_sales");
        CUBE_SALES.setQuery(QUERY_SALES_FACT);
        CUBE_SALES.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_STORE, CONNECTOR_STORE_SIZE_IN_SOFT, CONNECTOR_STORE_TYPE, CONNECTOR_TIME,
                        CONNECTOR_PRODUCT, CONNECTOR_PROMOTION_MEDIA, CONNECTOR_PROMOTIONS, CONNECTOR_CUSTOMER,
                        CONNECTOR_EDUCATION_LEVEL, CONNECTOR_GENDER, CONNECTOR_MARITAL_STATUS, CONNECTOR_YEARLY_INCOME ));
        CUBE_SALES.getMeasureGroups().add(MEASUREGROUP_SALES);
        //CUBE_SALES.setDefaultMeasure(MEASURE_UNIT_SALES);
        CUBE_SALES.getAnnotations().addAll(List.of(ANNOTATION_SALES1, ANNOTATION_SALES2, ANNOTATION_SALES3, ANNOTATION_SALES4, ANNOTATION_SALES5));
        CUBE_SALES.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_LAST_PERIOD,
                CALCULATED_MEMBER_PROFIT_GROWTH));

        CUBE_WAREHOUSE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_WAREHOUSE.setName("Warehouse");
        CUBE_WAREHOUSE.setId("_cube_warehouse");
        CUBE_WAREHOUSE.setQuery(QUERY_INVENTORY_FACT);
        CUBE_WAREHOUSE.getDimensionConnectors().addAll(List.of(CONNECTOR_WAREHOUSE_STORE, CONNECTOR_WAREHOUSE_STORE_SIZE_IN_SQFT,
                CONNECTOR_WAREHOUSE_STORE_TYPE, CONNECTOR_WAREHOUSE_TIME,
                CONNECTOR_WAREHOUSE_PRODUCT, CONNECTOR_WAREHOUSE_WAREHOUSE));
        CUBE_WAREHOUSE.getMeasureGroups().add(MEASUREGROUP_WAREHOUSE);
        CUBE_WAREHOUSE.getCalculatedMembers().add(CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE);
        CUBE_WAREHOUSE.getNamedSets().add(NAMED_SET_TOP_SELLERS);


        CUBE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_STORE.setName("Store");
        CUBE_STORE.setId("_cube_store");
        CUBE_STORE.setQuery(QUERY_STORE);
        CUBE_STORE.getDimensionConnectors().addAll(List.of(CONNECTOR_STORE_STORE_TYPE, CONNECTOR_STORE_STORE, CONNECTOR_STORE_HAS_COFFEE_BAR));
        CUBE_STORE.getMeasureGroups().add(MEASUREGROUP_STORE);

        CUBE_HR = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_HR.setName("HR");
        CUBE_HR.setId("_cube_hr");
        CUBE_HR.setQuery(QUERY_SALARY);
        CUBE_HR.getDimensionConnectors().addAll(List.of(CONNECTOR_HR_TIME, CONNECTOR_HR_STORE, CONNECTOR_HR_PAY_TYPE, CONNECTOR_HR_STORE_TYPE_WITH_QUERY_EMPLOYEE,
                CONNECTOR_HR_POSITION, CONNECTOR_HR_DEPARTMENT, CONNECTOR_HR_EMPLOYEE));
        CUBE_HR.getMeasureGroups().add(MEASUREGROUP_HR);
        CUBE_HR.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_EMPLOYEE_SALARY, CALCULATED_MEMBER_AVG_SALARY));

        CUBE_SALES_RAGGED = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES_RAGGED.setName("Sales Ragged");
        CUBE_SALES_RAGGED.setId("_cube_sales_ragged");
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

        CUBE_SALES_2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_SALES_2.setName("Sales 2");
        CUBE_SALES_2.setId("_cube_sales_2");
        CUBE_SALES_2.setQuery(QUERY_SALES_FACT);
        CUBE_SALES_2.getMeasureGroups().add(MEASUREGROUP_SALES2);
        CUBE_SALES_2.getDimensionConnectors().addAll(List.of(
            CONNECTOR_SALES_2_TIME,
            CONNECTOR_SALES_2_PRODUCT,
            CONNECTOR_SALES_2_GENDER
        ));
        CUBE_SALES_2.getCalculatedMembers().addAll(List.of(CALCULATED_MEMBER_PROFIT_WITH_ORDER, CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2));
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES = RolapMappingFactory.eINSTANCE.createVirtualCube();
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.setName("Warehouse and Sales");
        CUBE_VIRTIAL_WAREHOUSE_AND_SALES.setId("_cube_warehouse_sales");
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
        GRANT_DATABASE_SCHEMA_ADMIN = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_ADMIN.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_DATABASE_SCHEMA_HR = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_HR.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_DATABASE_SCHEMA_MANAGER = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        GRANT_DATABASE_SCHEMA_MANAGER.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);

        GRANT_ADMIN_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_ADMIN_CATALOG.setCatalogAccess(CatalogAccess.ALL);
        GRANT_ADMIN_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_ADMIN);

        GRANT_NO_HR_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_NO_HR_CATALOG.setCatalogAccess(CatalogAccess.ALL);
        GRANT_NO_HR_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_HR);

        GRANT_NO_HR_CUBE = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_NO_HR_CUBE.setCube(CUBE_HR);
        GRANT_NO_HR_CUBE.setCubeAccess(CubeAccess.NONE);

        GRANT_NO_HR_CATALOG.getCubeGrants().add(GRANT_NO_HR_CUBE);

        GRANT_CALIFORNIA_MANAGER_CATALOG = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        GRANT_CALIFORNIA_MANAGER_CATALOG.setCatalogAccess(CatalogAccess.NONE);
        GRANT_CALIFORNIA_MANAGER_CATALOG.getDatabaseSchemaGrants().add(GRANT_DATABASE_SCHEMA_MANAGER);

        MEMBER_GRANT_STORE1 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_STORE1.setMember("[Store].[USA].[CA]");
        MEMBER_GRANT_STORE1.setMemberAccess(MemberAccess.ALL);

        MEMBER_GRANT_STORE2 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_STORE2.setMember("[Store].[USA].[CA].[Los Angeles]");
        MEMBER_GRANT_STORE2.setMemberAccess(MemberAccess.NONE);

        GRANT_HIERARCHY_STORE = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_STORE.setHierarchy(HIERARCHY_STORE);
        GRANT_HIERARCHY_STORE.setHierarchyAccess(HierarchyAccess.CUSTOM);
        GRANT_HIERARCHY_STORE.setTopLevel(LEVEL_STORE_COUNTRY);
        GRANT_HIERARCHY_STORE.getMemberGrants().addAll(List.of(MEMBER_GRANT_STORE1, MEMBER_GRANT_STORE2));

        MEMBER_GRANT_CUSTOMERS1 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_CUSTOMERS1.setMember("[Customers].[USA].[CA]");
        MEMBER_GRANT_CUSTOMERS1.setMemberAccess(MemberAccess.ALL);

        MEMBER_GRANT_CUSTOMERS2 = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        MEMBER_GRANT_CUSTOMERS2.setMember("[Customers].[USA].[CA].[Los Angeles]");
        MEMBER_GRANT_CUSTOMERS2.setMemberAccess(MemberAccess.NONE);

        GRANT_HIERARCHY_CUSTOMERS = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_CUSTOMERS.setHierarchy(HIERARCHY_CUSTOMER);
        GRANT_HIERARCHY_CUSTOMERS.setHierarchyAccess(HierarchyAccess.CUSTOM);
        GRANT_HIERARCHY_CUSTOMERS.setTopLevel(LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE);
        GRANT_HIERARCHY_CUSTOMERS.setBottomLevel(LEVEL_CITY_TABLE_COLUMN_CITY);
        GRANT_HIERARCHY_CUSTOMERS.getMemberGrants().addAll(List.of(MEMBER_GRANT_CUSTOMERS1, MEMBER_GRANT_CUSTOMERS2));

        GRANT_HIERARCHY_GENDER = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        GRANT_HIERARCHY_GENDER.setHierarchy(HIERARCHY_GENDER);
        GRANT_HIERARCHY_GENDER.setHierarchyAccess(HierarchyAccess.NONE);

        GRANT_CALIFORNIA_MANAGER_SALES_CUBE = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCube(CUBE_SALES);
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.setCubeAccess(CubeAccess.ALL);
        GRANT_CALIFORNIA_MANAGER_SALES_CUBE.getHierarchyGrants().addAll(List.of(
                GRANT_HIERARCHY_STORE,
                GRANT_HIERARCHY_CUSTOMERS,
                GRANT_HIERARCHY_GENDER));

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
        //DATABASE_SCHEMA_FOODMART.setName("foodmart");
        DATABASE_SCHEMA_FOODMART.getTables()
                .addAll(List.of(TABLE_SALES_FACT, TABLE_TIME, TABLE_STORE, TABLE_CUSTOMER, TABLE_PRODUCT,
                        TABLE_WAREHOUSE, TABLE_INVENTORY_FACT, TABLE_PROMOTION, TABLE_EMPLOYEE, TABLE_DEPARTMENT,
                        TABLE_POSITION, TABLE_SALARY, TABLE_EMPLOYEE_CLOSURE, TABLE_PRODUCT_CLASS, TABLE_AGG_C_SPECIAL_SALES_FACT_1997,
                        TABLE_AGG_L_05_SALES_FACT, TABLE_AGG_L_03_SALES_FACT,
                        TABLE_TIME_BY_DAY, TABLE_STORE_RAGGED ));

        GRANT_DATABASE_SCHEMA_ADMIN.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);
        GRANT_DATABASE_SCHEMA_HR.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);
        GRANT_DATABASE_SCHEMA_MANAGER.setDatabaseSchema(DATABASE_SCHEMA_FOODMART);

        CATALOG_FOODMART = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_FOODMART.setName("FoodMart");
        CATALOG_FOODMART.setDescription("FoodMart Sample Database - EMF Version");
        CATALOG_FOODMART.setId("_catalog_foodmart");
        CATALOG_FOODMART.getDbschemas().add(DATABASE_SCHEMA_FOODMART);
        CATALOG_FOODMART.getCubes().addAll(List.of(CUBE_SALES, CUBE_WAREHOUSE, CUBE_STORE, CUBE_HR, CUBE_SALES_RAGGED, CUBE_SALES_2, CUBE_VIRTIAL_WAREHOUSE_AND_SALES));
        CATALOG_FOODMART.getAccessRoles().addAll(List.of(ROLE_CALIFORNIA_MANAGER, ROLE_NO_HR_CUBE, ROLE_ADMINISTRATOR));

        // Add documentation
        document(CATALOG_FOODMART, "FoodMart Database", foodMartBody, 1, 0, 0, false, 0);
        document(DATABASE_SCHEMA_FOODMART, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 0);
        document(QUERY_SALES_FACT, "Sales Fact Query", querySalesBody, 1, 2, 0, true, 0);
        document(QUERY_INVENTORY_FACT, "Inventory Fact Query", queryIventoryFactBody, 1, 3, 0, true, 0);
        document(QUERY_STORE, "Store Query", queryStoreBody, 1, 4, 0, true, 0);
        document(QUERY_CUSTOMER, "Customer Query", queryCustomerBody, 1, 5, 0, true, 0);
        document(QUERY_PRODUCT, "Product Query", queryProductBody, 1, 6, 0, true, 0);
        document(QUERY_WAREHOUSE, "Warehouse Query", queryWarehouseBody, 1, 7, 0, true, 0);
        document(QUERY_PROMOTION, "Promotion Query", queryPromotionBody, 1, 8, 0, true, 0);
        document(QUERY_EMPLOYEE, "Employee Query", queryEmployeeBody, 1, 9, 0, true, 0);
        document(QUERY_DEPARTMENT, "Departament Query", queryDepartamentBody, 1, 10, 0, true, 0);
        document(QUERY_POSITION, "Position Query", queryPositionBody, 1, 11, 0, true, 0);
        document(QUERY_SALARY, "Salary Query", querySalaryBody, 1, 12, 0, true, 0);
        document(QUERY_EMPLOYEE_CLOSURE, "Employee Closure Query", queryEmployeeClosureBody, 1, 13, 0, true, 0);
        document(QUERY_PRODUCT_CLASS, "Product Class Query", queryProductClassBody, 1, 14, 0, true, 0);
        document(QUERY_TIME_BY_DAY, "Time By Day Query", queryTimeByDayBody, 1, 15, 0, true, 0);
        document(QUERY_STORE_RAGGED, "Store Ragged Query", queryStoreRaggedBody, 1, 16, 0, true, 0);

        document(CUBE_SALES, "Sales Cube", salesCubeBody, 1, 17, 0, true, 0);
        document(CUBE_WAREHOUSE, "Warehouse Cube", warehouseCubeBody, 1, 18, 0, true, 0);
        document(CUBE_STORE, "Store Cube", storeCubeBody, 1, 19, 0, true, 0);
        document(CUBE_HR, "HR Cube", hrCubeBody, 1, 20, 0, true, 0);
        document(CUBE_SALES_RAGGED, "Sales Ragged Cube", salesRaggedCubeBody, 1, 21, 0, true, 0);
        document(CUBE_SALES_2, "Sales2 Cube", sales2CubeBody, 1, 22, 0, true, 0);
        document(CUBE_VIRTIAL_WAREHOUSE_AND_SALES, "Warehouse and Sales Cube", warehouseSalesCubeBody, 1, 22, 0, true, 0);

        document(DIMENSION_TIME, "Time Dimension", timeBody, 1, 23, 0, true, 0);
        document(DIMENSION_HR_TIME, "Time Dimension", timeBody, 1, 24, 0, true, 0);
        document(DIMENSION_STORE, "Store Dimension", storeBody, 1, 25, 0, true, 0);
        document(DIMENSION_STORE_SALES_RAGGED, "Store Dimension", storeBody, 1, 26, 0, true, 0);
        document(DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE, "Store Dimension", storeBody, 1, 27, 0, true, 0);
        document(DIMENSION_PAY_TYPE, "Pay Type Dimension", payTypeBody, 1, 28, 0, true, 0);
        document(DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE, "Store Type Dimension", storeTypeBody, 1, 29, 0, true, 0);
        document(DIMENSION_STORE_TYPE_WITH_QUERY_STORE, "Store Type Dimension", storeTypeBody, 1, 30, 0, true, 0);
        document(DIMENSION_STORE_TYPE_WITHOUT_QUERY, "Store Type Dimension", storeTypeBody, 1, 31, 0, true, 0);
        document(DIMENSION_STORE_TYPE, "Store Type Dimension", storeTypeBody, 1, 32, 0, true, 0);
        document(DIMENSION_CUSTOMERS, "Customers Dimension", customersBody, 1, 33, 0, true, 0);
        document(DIMENSION_PRODUCT, "Product Dimension", productBody, 1, 34, 0, true, 0);
        document(DIMENSION_STORE_SIZE_IN_SQFT, "Store Size Dimension", storeSizeBody, 1, 35, 0, true, 0);
        document(DIMENSION_PROMOTIONS, "Promotions Dimension", promotionsBody, 1, 36, 0, true, 0);
        document(DIMENSION_EDUCATION_LEVEL, "Education Level Dimension", educationLevelBody, 1, 37, 0, true, 0);
        document(DIMENSION_GENDER, "Gender Dimension", genderBody, 1, 38, 0, true, 0);
        document(DIMENSION_MARITAL_STATUS, "Marital Status Dimension", maritalStatusBody, 1, 39, 0, true, 0);
        document(DIMENSION_YEARLY_INCOME, "Yearly Income Dimension", yearlyIncomeBody, 1, 40, 0, true, 0);
        document(DIMENSION_STORE_HAS_COFFEE_BAR, "Has coffee bar Dimension", hasCoffeeBarBody, 1, 41, 0, true, 0);
        document(DIMENSION_GEOGRAPHY, "Geography Dimension", geographyBody, 1, 42, 0, true, 0);
        document(DIMENSION_WAREHOUSE, "Warehouse Dimension", warehouseBody, 1, 43, 0, true, 0);
        document(DIMENSION_PROMOTION_MEDIA, "Promotion Media Dimension", promotionMediaBody, 1, 44, 0, true, 0);
        document(DIMENSION_EMPLOYEE, "Employee Dimension", employeeBody, 1, 45, 0, true, 0);
        document(DIMENSION_DEPARTMENT, "Department Dimension", departmentBody, 1, 46, 0, true, 0);
        document(DIMENSION_POSITION, "Position Dimension", positionBody, 1, 47, 0, true, 0);
        document(DIMENSION_HR_POSITION, "Position Dimension", positionBody, 1, 48, 0, true, 0);

        document(HIERARCHY_TIME, "Time Hierarchy",
                "The Time hierarchy provides temporal analysis with years, quarters, and months with hasAll enabled.", 1, 49, 0, true, 0);
        document(HIERARCHY_HR_TIME, "Time Hierarchy",
                "The Time hierarchy provides temporal analysis with years, quarters, and months with hasAll enabled.", 1, 50, 0, true, 0);
        document(HIERARCHY_TIME2, "Time Hierarchy",
                "The Time hierarchy provides temporal analysis with years, quarters, and months with hasAll enabled.", 1, 51, 0, true, 0);
        document(HIERARCHY_STORE, "Store Hierarchy",
                "The Store hierarchy provides store analysis with country, state, city and name of story with hasAll enabled.", 1, 52, 0, true, 0);
        document(HIERARCHY_STORE_SALES_RAGGED, "Store Hierarchy",
                "The Store hierarchy provides store analysis with country, state, city and name of story with hasAll enabled.", 1, 53, 0, true, 0);
        document(HIERARCHY_HR_STORE, "Store Hierarchy",
                "The Store hierarchy provides store analysis with country, state, city and name of story with hasAll enabled.", 1, 54, 0, true, 0);
        document(HIERARCHY_PAY_TYPE, "Pay Type Hierarchy",
                "The Pay Type provides pay type analysis for employee.", 1, 55, 0, true, 0);
        document(HIERARCHY_STORE_TYPE_WITH_QUERY_EMPLOYEE, "Store Type Hierarchy",
                "The Store Type provides store type analysis for store.", 1, 56, 0, true, 0);
        document(HIERARCHY_CUSTOMER, "Customer Hierarchy",
                "The Customer provides customer analysis by country, state, cyty, name of customer.", 1, 57, 0, true, 0);
        document(HIERARCHY_CUSTOMERS_GEO, "Geography Hierarchy",
                "The Geography provides customer analysis by country, state, cyty, name of customer.", 1, 58, 0, true, 0);
        document(HIERARCHY_CUSTOMERS_GENDER, "Gender Hierarchy",
                "The Gender provides customer analysis by gender identity.", 1, 59, 0, true, 0);
        document(HIERARCHY_PRODUCT, "Product Hierarchy",
                "The Product provides product analysis by product family, product departament, category, subcategory, brand, product name.", 1, 60, 0, true, 0);
        document(HIERARCHY_STORE_SIZE_IN_SQFT, "Store Size Hierarchy",
                "The Store Size provides store analysis by size.", 1, 61, 0, true, 0);
        document(HIERARCHY_PROMOTIONS, "Promotions Hierarchy",
                "The Promotions provides sales analysis by promotions.", 1, 62, 0, true, 0);
        document(HIERARCHY_STORE_TYPE, "Store Type Hierarchy",
                "The Store Type provides store analysis by type.", 1, 63, 0, true, 0);
        document(HIERARCHY_STORE_TYPE_WITHOUT_TABLE, "Store Type Hierarchy",
                "The Store Type provides store analysis by type.", 1, 64, 0, true, 0);
        document(HIERARCHY_EDUCATION_LEVEL, "Education Level",
                "The Education Level provides customer analysis by education level.", 1, 65, 0, true, 0);
        document(HIERARCHY_GENDER, "Gender Hierarchy",
                "The Gender provides customer analysis by gender identity.", 1, 65, 0, true, 0);
        document(HIERARCHY_MARITAL_STATUS, "Marital Status Hierarchy",
                "The Marital Status provides customer analysis by marital status.", 1, 66, 0, true, 0);
        document(HIERARCHY_MARITAL_STATUS, "Marital Status Hierarchy",
                "The Marital Status provides customer analysis by marital status.", 1, 67, 0, true, 0);
        document(HIERARCHY_YEARLY_INCOME, "Yerly Income Hierarchy",
                "The Yerly Income provides customer analysis by yerly income.", 1, 68, 0, true, 0);
        document(HIERARCHY_STORE_HAS_COFFEE_BAR, "Has Coffe Bar Hierarchy",
                "The Has Coffe Bar provides store analysis by availability сoffe bar.", 1, 69, 0, true, 0);
        document(HIERARCHY_GEOGRAPHY, "Has Coffe Bar Hierarchy",
                "The Has Coffe Bar provides store analysis by geography country, state, city.", 1, 70, 0, true, 0);
        document(HIERARCHY_GEOGRAPHY, "Has Coffe Bar Hierarchy",
                "The Has Coffe Bar provides store analysis by geography country, state, city.", 1, 71, 0, true, 0);
        document(HIERARCHY_GEOGRAPHY, "Has Coffe Bar Hierarchy",
                "The Has Coffe Bar provides store analysis by geography country, state, city.", 1, 72, 0, true, 0);
        document(HIERARCHY_WAREHOUSE, "Warehouse Hierarchy",
                "The Warehouse provides warehouse analysis by geography country, state, city and name of warehouse.", 1, 73, 0, true, 0);
        document(HIERARCHY_PROMOTION_MEDIA, "Promotion Media Hierarchy",
                "The Promotion Media provides sales analysis by promotion media type.", 1, 74, 1, true, 0);
        document(HIERARCHY_EMPLOYEE, "Employee Hierarchy",
                "The Employee provides organisation structure analysis by employees.", 1, 75, 0, true, 0);
        document(HIERARCHY_DEPARTMENT, "Department Hierarchy",
                "The Department provides sales analysis by departoment of employees.", 1, 76, 0, true, 0);
        document(HIERARCHY_POSITION, "Position Hierarchy",
                "The Position provides employee analysis by role and position.", 1, 77, 0, true, 0);
        document(HIERARCHY_HR_POSITION, "Position Hierarchy",
                "The Position provides employee analysis by role and position.", 1, 78, 0, true, 0);
        document(HIERARCHY_CUSTOMERS_EDUCATION, "Customers Education Hierarchy",
                "The Customers Education provides customer analysis by education level.", 1, 79, 0, true, 0);
        document(HIERARCHY_CUSTOMERS_MARITAL, "Customers Marital Status",
                "The Customers Marital Status provides customer analysis by marital status.", 1, 80, 0, true, 0);

        document(LEVEL_YEAR, "Year Level",
                "Year level represents year for sales analysis.", 1, 81, 0, true, 0);
        document(LEVEL_QUARTER, "Quarter Level",
                "Quarter level represents quarter for sales analysis.", 1, 82, 0, true, 0);
        document(LEVEL_MONTH, "Month Level",
                "Month level represents month for sales analysis.", 1, 83, 0, true, 0);
        document(LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR, "Month Level",
                "Month level represents month for sales analysis.", 1, 84, 0, true, 0);
        document(LEVEL_WEEK, "Week Level",
                "Week level represents week for sales analysis.", 1, 85, 0, true, 0);
        document(LEVEL_DAY, "Day Level",
                "Day level represents date for sales analysis.", 1, 86, 0, true, 0);
        document(LEVEL_STORE_COUNTRY, "Store Country Level",
                "Store Country level represents country for store.", 1, 87, 0, true, 0);
        document(LEVEL_STORE_STATE, "Store State Level",
                "Store State level represents state for store.", 1, 88, 0, true, 0);
        document(LEVEL_STORE_CITY, "Store State Level",
                "Store City level represents city for store.", 1, 89, 0, true, 0);
        document(LEVEL_STORE_NAME, "Store State Level",
                "Store Name level represents name of store.", 1, 90, 0, true, 0);
        document(LEVEL_STORE_HAS_COFFEE_BAR, "Has coffee bar Level",
                "Has coffee bar level represents tag that store has coffe bar.", 1, 91, 0, true, 0);
        document(LEVEL_CUSTOMER_COUNTRY, "Customer Country Level",
                "Customer Country level represents country for customer.", 1, 92, 0, true, 0);
        document(LEVEL_CUSTOMER_STATE, "Customer State Level",
                "Customer State level represents state for customer.", 1, 93, 0, true, 0);
        document(LEVEL_CUSTOMER_CITY, "Customer City Level",
                "Customer City level represents city for customer.", 1, 94, 0, true, 0);
        document(LEVEL_CUSTOMER_NAME, "Customer Name Level",
                "Customer Name level represents name for customer.", 1, 95, 0, true, 0);
        document(LEVEL_CUSTOMER_GENDER, "Customer Gender Level",
                "Customer Gender level represents gender identification for customer.", 1, 96, 0, true, 0);
        document(LEVEL_PRODUCT_FAMILY, "Product Family Level",
                "Product Family level represents family of product.", 1, 97, 0, true, 0);
        document(LEVEL_PRODUCT_FAMILY, "Product Departament Level",
                "Product Departament level represents departament of product.", 1, 98, 0, true, 0);
        document(LEVEL_PRODUCT_CATEGORY, "Product Category Level",
                "Product Category level represents category of product.", 1, 99, 0, true, 0);
        document(LEVEL_PRODUCT_SUBCATEGORY, "Product Category Level",
                "Product Subcategory level represents subcategory of product.", 1, 100, 0, true, 0);
        document(LEVEL_PRODUCT_BRAND, "Product Brand Level",
                "Product Brand level represents brand of product.", 1, 101, 0, true, 0);
        document(LEVEL_PRODUCT_NAME, "Product Name Level",
                "Product Name level represents name of product.", 1, 102, 0, true, 0);
        document(LEVEL_PROMOTION_NAME, "Promotions Level",
                "Promotions level represents promotions of sales.", 1, 103, 0, true, 0);
        document(LEVEL_STORE_SQFT, "Store Sqft",
                "Store Sqft level represents sqft of store.", 1, 104, 0, true, 0);
        document(LEVEL_STORE_TYPE_WITHOUT_TABLE, "Store Type",
                "Store Type level represents type of store.", 1, 105, 0, true, 0);
        document(LEVEL_EDUCATION, "Education Level",
                "Education Level level represents education level of customer.", 1, 106, 0, true, 0);
        document(LEVEL_GENDER, "Gender",
                "Gender Level level represents gender identification of customer.", 1, 107, 0, true, 0);
        document(LEVEL_MARITAL_STATUS, "Marital Status",
                "Marital Status level represents marital status of customer.", 1, 108, 0, true, 0);
        document(LEVEL_YEARLY_INCOME, "Yearly Income",
                "Yearly Income level represents yearly income of customer.", 1, 109, 0, true, 0);
        document(LEVEL_PAY_TYPE, "Pay Type",
                "Pay Type level represents pay type of sales.", 1, 110, 0, true, 0);
        document(LEVEL_STORE_TYPE, "Store Type",
                "Store Type level represents type of store.", 1, 111, 0, true, 0);
        document(LEVEL_STORE_COUNTRY_WITH_NEVER, "Store Country",
                "Store Country level represents country of store.", 1, 112, 0, true, 0);
        document(LEVEL_STORE_CYTY_IF_PARENTS_NAME, "Store City",
                "Store City level represents city of store.", 1, 113, 0, true, 0);
        document(LEVEL_STORE_CYTY_IF_BLANK_NAME, "Store City",
                "Store City level represents city of store. with property hide member if blank name", 1, 114, 0, true, 0);
        document(LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER, "Store Name",
                "Store Name level represents name of store. with property hide member if never", 1, 115, 0, true, 0);
        document(LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, "Country",
                "Country level represents name of customer.", 1, 116, 0, true, 0);
        document(LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE, "State Province",
                "State Province level represents state province of customer.", 1, 117, 0, true, 0);
        document(LEVEL_CITY_TABLE_COLUMN_CITY, "City",
                "City level represents city of customer.", 1, 118, 0, true, 0);
        document(LEVEL_NAME, "Name",
                "Name level represents name of customer with properties (gender, marital status, education, yearly income) .", 1, 119, 0, true, 0);
        document(LEVEL_WAREHOUSE_COUNTRY, "Country",
                "Country level represents name of warehouse.", 1, 120, 0, true, 0);
        document(LEVEL_WAREHOUSE_STATE, "State",
                "State level represents state of warehouse.", 1, 121, 0, true, 0);
        document(LEVEL_WAREHOUSE_CITY, "City",
                "City level represents city of warehouse.", 1, 122, 0, true, 0);
        document(LEVEL_WAREHOUSE_NAME, "Warehouse Name",
                "Warehouse Name level represents name of warehouse.", 1, 123, 0, true, 0);
        document(LEVEL_PROMOTION_MEDIA, "Media Type",
                "Media Type level represents promotions media type of sales.", 1, 124, 0, true, 0);
        document(LEVEL_EMPLOYEE_MANAGEMENT_ROLE, "Management Role",
                "Management Role level represents role of employee.", 1, 125, 0, true, 0);
        document(LEVEL_EMPLOYEE_POSITION, "Position Title",
                "Position Title level represents position title of employee.", 1, 126, 0, true, 0);
        document(LEVEL_EMPLOYEE_DEPARTMENT, "Position ID",
                "Position ID level represents position ID of employee.", 1, 127, 0, true, 0);
        document(LEVEL_EMPLOYEE_FULL_NAME, "Employee Name",
                "Employee Name level represents full name of employee.", 1, 128, 0, true, 0);
        document(LEVEL_EMPLOYEE_FULL_NAME, "Employee Name",
                "Employee Name level represents full name of employee.", 1, 129, 0, true, 0);
        document(LEVEL_DEPARTMENT_DESCRIPTION, "Department Description",
                "Department Description level represents department description of sales.", 1, 130, 0, true, 0);
        document(LEVEL_POSITION_TITLE, "Position Title",
                "Position Title level represents position title of employee.", 1, 131, 0, true, 0);
        document(LEVEL_MANAGEMENT_ROLE, "Management Role",
                "Management Role level represents management role of employee.", 1, 132, 0, true, 0);
        document(LEVEL_HR_POSITION_TITLE, "Position Title",
                "Position Title level represents position title of employee.", 1, 133, 0, true, 0);
        document(LEVEL_CUSTOMER_EDUCATION, "Education Level",
                "Education Level level represents education level of customer.", 1, 134, 0, true, 0);
        document(LEVEL_CUSTOMER_MARITAL_STATUS, "Marital Status",
                "Marital Status level represents marital status of customer.", 1, 135, 0, true, 0);
        document(LEVEL_EMPLOYEE_ID, "Employee Id",
                "Employee Id level represents Id of employee.", 1, 136, 0, true, 0);
        document(LEVEL_COUNTRY_WITH_NEVER, "Country",
                "Country level represents country of regged store.", 1, 137, 0, true, 0);
        document(LEVEL_STATE, "State",
                "State level represents state of regged store.", 1, 138, 0, true, 0);
        document(LEVEL_CITY_TABLE_COLUMN_STORE_CITY, "City",
                "City level represents city of regged store.", 1, 139, 0, true, 0);

        document(MEASURE_UNIT_SALES, "Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation.", 1, 140, 0, true, 0);
        document(MEASURE_STORE_SALES, "Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation.", 1, 141, 0, true, 0);
        document(MEASURE_STORE_COST, "Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation.", 1, 142, 0, true, 0);
        document(MEASURE_SALES_COUNT, "Store Count", "Measure Store Count use sales_fact_1997 table product_id column with count aggregation.", 1, 143, 0, true, 0);
        document(MEASURE_CUSTOMER_COUNT, "Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation.", 1, 144, 0, true, 0);
        document(MEASURE_PROMOTION_SALES, "Promotion Sales", "Measure Promotion Sales use (case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end) expression with sum aggregation.", 1, 145, 0, true, 0);

        document(MEASURE_WAREHOUSE_SALES, "Warehouse Sales", "Measure Warehouse Sales use inventory_fact_1997 table warehouse_sales column with sum aggregation.", 1, 146, 0, true, 0);
        document(MEASURE_WAREHOUSE_STORE_INVOICE, "Store Invoice", "Measure Store Invoice use inventory_fact_1997 table store_invoice column with sum aggregation.", 1, 147, 0, true, 0);
        document(MEASURE_WAREHOUSE_SUPPLY_TIME, "Supply Time", "Measure Supply Time use inventory_fact_1997 table supply_time column with sum aggregation.", 1, 148, 0, true, 0);
        document(MEASURE_WAREHOUSE_PROFIT, "Warehouse Profit", "Measure Warehouse Profit use warehouse_sales - warehouse_cost expression with sum aggregation.", 1, 149, 0, true, 0);
        document(MEASURE_WAREHOUSE_COST, "Warehouse Cost", "Measure Warehouse Cost use inventory_fact_1997 table warehouse_cost column with sum aggregation.", 1, 150, 0, true, 0);
        document(MEASURE_UNITS_SHIPPED, "Units Shipped", "Measure Units Shipped use inventory_fact_1997 table units_shipped column with sum aggregation.", 1, 151, 0, true, 0);
        document(MEASURE_UNITS_ORDERED, "Units Ordered", "Measure Units Ordered use inventory_fact_1997 table units_ordered column with sum aggregation.", 1, 152, 0, true, 0);
        document(MEASURE_STORE_SQFT, "Store Sqft", "Measure Store Sqft use store table store_sqft column with sum aggregation.", 1, 153, 0, true, 0);
        document(MEASURE_GROCERY_SQFT, "Grocery Sqft", "Measure Grocery Sqft use store table grocery_sqft column with sum aggregation.", 1, 154, 0, true, 0);
        document(MEASURE_ORG_SALARY, "Org Salary", "Measure Warehouse Cost use salary table salary_paid column with sum aggregation.", 1, 155, 0, true, 0);
        document(MEASURE_COUNT, "Count", "Measure Count use salary table employee_id column with count aggregation.", 1, 156, 0, true, 0);
        document(MEASURE_NUMBER_OF_EMPLOYEES, "Count", "Measure Count use salary table employee_id column with count aggregation.", 1, 157, 0, true, 0);
        document(MEASURE_UNIT_SALES_RAGGED, "Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation.", 1, 158, 0, true, 0);
        document(MEASURE_STORE_COST_RAGGED, "Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation.", 1, 159, 0, true, 0);
        document(MEASURE_STORE_SALES_RAGGED, "Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation.", 1, 160, 0, true, 0);
        document(MEASURE_SALES_COUNT_RAGGED, "Sales Count", "Measure Sales Count use sales_fact_1997 table product_id column with count aggregation.", 1, 161, 0, true, 0);
        document(MEASURE_CUSTOMER_COUNT_RAGGED, "Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation.", 1, 162, 0, true, 0);

        document(MEASURE_SALES_COUNT_WITH_PROPERTY, "Sales Count", "Measure Sales Count use sales_fact_1997 table product_id column with count aggregation. with MEMBER_ORDINAL property", 1, 163, 0, true, 0);
        document(MEASURE_UNIT_SALES_MEMBER_ORDINAL, "Unit Sales", "Measure Unit Sales use sales_fact_1997 table unit_sales column with sum aggregation. with MEMBER_ORDINAL property", 1, 164, 0, true, 0);
        document(MEASURE_STORE_SALES_WITH_PROPERTY, "Store Sales", "Measure Store Sales use sales_fact_1997 table store_sales column with sum aggregation. with MEMBER_ORDINAL property", 1, 165, 0, true, 0);
        document(MEASURE_STORE_COST_WITH_PROPERTY, "Store Cost", "Measure Store Cost use sales_fact_1997 table store_cost column with sum aggregation. with MEMBER_ORDINAL property", 1, 165, 0, true, 0);
        document(MEASURE_CUSTOMER_COUNT_WITH_PROPERTY, "Customer Count", "Measure Customer Count use sales_fact_1997 table customer_id column with count aggregation. with MEMBER_ORDINAL property", 1, 166, 0, true, 0);

        document(ROLE_ADMINISTRATOR, "Administrator Role", roleAdministratorBody, 1, 167, 0, true, 0);
        document(ROLE_CALIFORNIA_MANAGER, "California manager Role", roleCaliforniaManagerBody, 1, 168, 0, true, 0);
        document(ROLE_NO_HR_CUBE, "No HR Cube Role", roleNoHRCubeBody, 1, 169, 0, true, 0);

    }

    @Override
    public CatalogMapping get() {
        return CATALOG_FOODMART;
    }
}
