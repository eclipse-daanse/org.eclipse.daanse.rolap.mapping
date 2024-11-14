/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.complex.foodmart;

import java.util.List;

import org.eclipse.daanse.rdb.structure.pojo.ColumnImpl;
import org.eclipse.daanse.rdb.structure.pojo.DatabaseSchemaImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl.Builder;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchy;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessSchema;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessSchemaGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationExcludeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.NamedSetMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ParentChildLinkMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.VirtualCubeMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "3")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class FoodmartMappingSupplier implements CatalogMappingSupplier {

    private static final String SALES_COUNT = "Sales Count";

    private static final String FORMAT_STANDARD = "Standard";

    private static final String CUSTOMER_COUNT = "Customer Count";

    private static final String TABLE_COLUMN_STORE_SALES = "store_sales";

    private static final String STORE_SALES = "Store Sales";

    private static final String TABLE_COLUMN_UNIT_SALES = "unit_sales";

    private static final String UNIT_SALES = "Unit Sales";

    private static final String TABLE_COLUMN_STORE_COST = "store_cost";

    private static final String STORE_COST = "Store Cost";

    private static final String MEMBER_ORDINAL = "MEMBER_ORDINAL";

    private static final String TABLE_COLUMN_MARITAL_STATUS = "marital_status";

    private static final String TABLE_COLUMN_GENDER = "gender";

    private static final String TABLE_COLUMN_MEAT_SQFT = "meat_sqft";

    private static final String TABLE_COLUMN_FROZEN_SQFT = "frozen_sqft";

    private static final String FORMAT_STRING_CURRENCY = "$#,##0.00";

    private static final String FORMAT_STRING = "FORMAT_STRING";

    private static final String FORMAT_STRING_WITH_COMMMA = "#,###.00";

    private static final String DIALECT_GENERIC = "generic";

    private static final String DIALECT_TERADATA = "teradata";

    private static final String DIALECT_MARIADB = "mariadb";

    private static final String DIALECT_INFOBRIGHT = "infobright";

    private static final String DIALECT_MYSQL = "mysql";

    private static final String DIALECT_MSSQL = "mssql";

    private static final String DIALECT_ACCESS = "access";

    private static final String DIALECT_HIVE = "hive";

    private static final String FORMAT_STRING_WITHOUT_COMMA = "#,###";

    private static final String DIALECT_SNOWFLAKE = "snowflake";

    private static final String NUODB = "nuodb";

    private static final String DIALECT_DB2 = "db2";

    private static final String DIALECT_LUCIDDB = "luciddb";

    private static final String DIALECT_DERBY = "derby";

    private static final String DIALECT_NEOVIEW = "neoview";

    private static final String DIALECT_POSTGRES = "postgres";

    private static final String DIALECT_HSQLDB = "hsqldb";

    private static final String CURRENCY = "Currency";

    private static final String STORE_STREET_ADDRESS = "store_street_address";

    private static final String TABLE_COLUMN_COFFEE_BAR = "coffee_bar";

    private static final String TABLE_COLUMN_GROCERY_SQFT = "grocery_sqft";

    private static final String TABLE_COLUMN_STORE_MANAGER = "store_manager";

    private static final String DIALECT_H2 = "h2";

    private static final String DIALECT_ORACLE = "oracle";

    private static final String NAME = "Name";

    private static final String TABLE_COLUMN_CITY = "city";

    private static final String TABLE_COLUMN_STATE_PROVINCE = "state_province";

    private static final String TABLE_COLUMN_COUNTRY = "country";

    private static final String TABLE_COLUMN_PROMOTION_NAME = "promotion_name";

    private static final String PROMOTION_NAME = "Promotion Name";

    private static final String TABLE_COLUMN_MEDIA_TYPE = "media_type";

    private static final String MEDIA_TYPE = "Media Type";

    private static final String TABLE_COLUMN_WAREHOUSE_NAME = "warehouse_name";

    private static final String WAREHOUSE_NAME = "Warehouse Name";

    private static final String TABLE_COLUMN_WAREHOUSE_CITY = "warehouse_city";

    private static final String CITY = "City";

    private static final String TABLE_COLUMN_WAREHOUSE_STATE_PROVINCE = "warehouse_state_province";

    private static final String STATE_PROVINCE = "State Province";

    private static final String STATE = "State";

    private static final String TABLE_COLUMN_WAREHOUSE_COUNTRY = "warehouse_country";

    private static final String COUNTRY = "Country";

    private static final String TABLE_COLUMN_PRODUCT_NAME = "product_name";

    private static final String TABLE_COLUMN_BRAND_NAME = "brand_name";

    private static final String TABLE_COLUMN_PRODUCT_SUBCATEGORY = "product_subcategory";

    private static final String TABLE_COLUMN_PRODUCT_CATEGORY = "product_category";

    private static final String TABLE_COLUMN_EMPLOYEE_ID = "employee_id";

    private static final String ALL_EMPLOYEES = "All Employees";

    private static final String NAME_DIMENSION_GEOGRAPHY = "Geography";

    private static final String TABLE_COLUMN_DEPARTMENT_ID = "department_id";

    private static final String NAME_DIMENSION_EMPLOYEES = "Employees";

    private static final String NAME_DIMENSION_DEPARTMENT = "Department";

    private static final String NAME_DIMENSION_POSITION = "Position";

    private static final String NAME_DIMENSION_PAY_TYPE = "Pay Type";

    private static final String NAME_DIMENSION_YEARLY_INCOME = "Yearly Income";

    private static final String ALL_MARITAL_STATUS = "All Marital Status";

    private static final String NAME_DIMENSION_MARITAL_STATUS = "Marital Status";

    private static final String NAME_DIMENSION_GENDER = "Gender";

    private static final String NAME_LEVEL_GENDER = "Gender";

    private static final String ALL_GENDER = "All Gender";

    private static final String NAME_DIMENSION_EDUCATION_LEVEL = "Education Level";

    private static final String NAME_LEVEL_EDUCATION_LEVEL = "Education Level";

    private static final String NAME_DIMENSION_CUSTOMERS = "Customers";

    private static final String ALL_CUSTOMERS = "All Customers";

    private static final String ALL_PROMOTIONS = "All Promotions";

    private static final String NAME_DIMENSION_PROMOTIONS = "Promotions";

    private static final String ALL_MEDIA = "All Media";

    private static final String NAME_DIMENSION_PROMOTION_MEDIA = "Promotion Media";

    private static final String TABLE_COLUMN_PROMOTION_ID = "promotion_id";

    private static final String TABLE_COLUMN_CUSTOMER_ID = "customer_id";

    private static final String BAR = "bar";

    private static final String FOO = "foo";

    private static final String FACT_COUNT = "FACT_COUNT";

    private static final String TABLE_COLUMN_WAREHOUSE_ID = "warehouse_id";

    private static final String SALES_FACT_1997 = "sales_fact_1997";

    private static final String NAME_DIMENSION_WAREHOUSE = "Warehouse";

    private static final String NAME_CUBE_WAREHOUSE = "Warehouse";

    private static final String PRODUCT_NAME = "Product Name";

    private static final String BRAND_NAME = "Brand Name";

    private static final String PRODUCT_SUBCATEGORY = "Product Subcategory";

    private static final String PRODUCT_CATEGORY = "Product Category";

    private static final String DETAILS = "Details";

    private static final String TABLE_COLUMN_PRODUCT_ID = "product_id";

    private static final String NAME_DIMENSION_PRODUCT = "Product";

    private static final String TABLE_COLUMN_THE_DATE = "the_date";

    private static final String TABLE_COLUMN_TIME_ID = "time_id";

    private static final String NAME_DIMENSION_TIME = "Time";

    private static final String NAME_DIMENSION_STORE_SIZE_IN_SQFT = "Store Size in SQFT";

    private static final String NAME_DIMENSION_STORE = "Store";

    private static final String NAME_CUBE_STORE = "Store";

    private static final String TABLE_COLUMN_PRODUCT_DEPARTMENT = "product_department";

    private static final String PRODUCT_DEPARTMENT = "Product Department";

    private static final String TABLE_COLUMN_PRODUCT_FAMILY = "product_family";

    private static final String PRODUCT_FAMILY = "Product Family";

    private static final String TABLE_COLUMN_STORE_TYPE = "store_type";

    private static final String TABLE_COLUMN_THE_MONTH = "the_month";

    private static final String TABLE_COLUMN_MONTH_OF_YEAR = "month_of_year";

    private static final String MONTH = "Month";

    private static final String TABLE_COLUMN_QUARTER = "quarter";

    private static final String QUARTER = "Quarter";

    private static final String TABLE_COLUMN_THE_YEAR = "the_year";

    private static final String TABLE_COLUMN_WEEK_OF_YEAR = "week_of_year";

    private static final String TABLE_COLUMN_DAY_OF_MONTH = "day_of_month";

    private static final String YEAR = "Year";

    private static final String TABLE_COLUMN_STORE_SQFT = "store_sqft";

    private static final String STREET_ADDRESS = "Street address";

    private static final String NAME_DIMENSION_HAS_COFFEE_BAR = "Has coffee bar";

    private static final String MEAT_SQFT = "Meat Sqft";

    private static final String FROZEN_SQFT = "Frozen Sqft";

    private static final String GROCERY_SQFT = "Grocery Sqft";

    private static final String STORE_SQFT = "Store Sqft";

    private static final String STORE_MANAGER = "Store Manager";

    private static final String NAME_DIMENSION_STORE_TYPE = "Store Type";

    private static final String TABLE_COLUMN_STORE_NAME = "store_name";

    private static final String STORE_NAME = "Store Name";

    private static final String TABLE_COLUMN_STORE_STATE = "store_state";

    private static final String STORE_STATE = "Store State";

    private static final String TABLE_COLUMN_STORE_COUNTRY = "store_country";

    private static final String STORE_COUNTRY = "Store Country";

    private static final String STORE_RAGGED = "store_ragged";

    private static final String EMPLOYEE_CLOSURE = "employee_closure";

    private static final String SALARY = "salary";

    private static final String TABLE_COLUMN_STORE_CITY = "store_city";

    private static final String STORE_CITY = "Store City";

    private static final String TABLE_NAME_POSITION = "position";

    private static final String TABLE_COLUMN_POSITION_ID = "position_id";

    private static final String TABLE_COLUMN_STORE_ID = "store_id";

    private static final String TABLE_COLUMN_STORES_ID = "stores_id";

    private static final String EMPLOYEE = "employee";

    private static final String TABLE_PRODUCT_CLASS = "product_class";

    private static final String PRODUCT_CLASS_ID = "product_class_id";

    private static final String TABLE_PRODUCT = "product";

    private static final String TABLE_STORE = "store";

    private static final String SCHEMA_NAME = "FoodMart";

    private static final String CATALOG_NAME = SCHEMA_NAME;

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    //month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,customer_count,fact_count
    //SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
    public static final ColumnImpl MONTH_OF_YEAR_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName("month_of_year").withType("SMALLINT").build();
    public static final ColumnImpl QUARTER_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName("quarter").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl THE_YEAR_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName("the_year").withType("SMALLINT").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl CUSTOMER_COUNT_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName("customer_count").withType("INTEGER").build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_C_10_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_C_10_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_c_10_sales_fact_1997")
            .withColumns(List.of(
                    MONTH_OF_YEAR_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    QUARTER_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    THE_YEAR_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    CUSTOMER_COUNT_COLUMN_IN_AGG_C_10_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_C_10_SALES_FACT_1997
            ))).build();

    //product_id,customer_id,store_id,promotion_id,month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl PROMOTION_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_ID).withType("SMALLINT").build();
    public static final ColumnImpl MONTH_OF_YEAR_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("month_of_year").withType("SMALLINT").build();
    public static final ColumnImpl QUARTER_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("quarter").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl THE_YEAR_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("the_year").withType("SMALLINT").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_C_14_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_c_14_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    STORE_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    PROMOTION_ID_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    MONTH_OF_YEAR_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    QUARTER_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    THE_YEAR_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_C_14_SALES_FACT_1997
            ))).build();

    //product_id,promotion_id,customer_id,store_id,time_month,time_quarter,time_year,store_sales_sum,store_cost_sum,unit_sales_sum,fact_count
    //INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl PROMOTION_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_ID).withType("SMALLINT").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl TIME_MONTH_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName("time_month").withType("SMALLINT").build();
    public static final ColumnImpl TIME_QUARTER_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName("time_quarter").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl TIME_YEAR_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName("time_year").withType("SMALLINT").build();
    public static final ColumnImpl STORE_SALES_SUM_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997 = ColumnImpl.builder().withName("store_sales_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_SUM_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("store_cost_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_SUM_COLUMN_IN_AGG_C_14_SALES_FACT_1997 = ColumnImpl.builder().withName("unit_sales_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_C_SALES_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_C_SPECIAL_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_c_special_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    PROMOTION_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    STORE_ID_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    TIME_MONTH_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    TIME_QUARTER_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    TIME_YEAR_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    STORE_SALES_SUM_COLUMN_IN_AGG_C_SPECIAL_SALES_FACT_1997,
                    STORE_COST_SUM_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    UNIT_SALES_SUM_COLUMN_IN_AGG_C_14_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_C_SALES_SALES_FACT_1997
            ))).build();

    //gender,marital_status,product_family,product_department,product_category,month_of_year,quarter,the_year,store_sales,store_cost,unit_sales,customer_count,fact_count
    //VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),SMALLINT,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
    public static final ColumnImpl GENDER_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_GENDER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl MARITAL_STATUS_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_MARITAL_STATUS).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_FAMILY_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_FAMILY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_DEPARTMENT_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_DEPARTMENT).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_CATEGORY_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_CATEGORY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl MONTH_OF_YEAR_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName("month_of_year").withType("SMALLINT").build();
    public static final ColumnImpl QUARTER_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName("quarter").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl THE_YEAR_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName("the_year").withType("SMALLINT").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_G_MS_PCAT_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_g_ms_pcat_sales_fact_1997")
            .withColumns(List.of(
                    GENDER_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    MARITAL_STATUS_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    PRODUCT_FAMILY_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    PRODUCT_DEPARTMENT_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    PRODUCT_CATEGORY_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    MONTH_OF_YEAR_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    QUARTER_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    THE_YEAR_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_G_MS_PCAT_SALES_FACT_1997
            ))).build();

    //time_id,customer_id,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl TIME_ID_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName("time_id").withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_L_03_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_L_03_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_l_03_sales_fact_1997")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_AGG_L_03_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_L_03_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_L_03_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_L_03_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_L_03_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_L_03_SALES_FACT_1997
            ))).build();

    //time_id,store_sales,store_cost,unit_sales,customer_count,fact_count
    //INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER,INTEGER
    public static final ColumnImpl TIME_ID_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName("time_id").withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl CUSTOMER_COUNT_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName("customer_count").withType("INTEGER").build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_L_04_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_L_04_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_l_04_sales_fact_1997")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_AGG_L_04_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_L_04_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_L_04_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_L_04_SALES_FACT_1997,
                    CUSTOMER_COUNT_COLUMN_IN_AGG_L_04_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_L_04_SALES_FACT_1997
            ))).build();

    //product_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl PROMOTION_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_L_05_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_L_05_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_l_05_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    PROMOTION_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    STORE_ID_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_L_05_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_L_05_SALES_FACT_1997
            ))).build();

    //time_id,city,state_province,country,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl TIME_ID_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName("time_id").withType("INTEGER").build();
    public static final ColumnImpl CITY_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CITY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STATE_PROVINCE_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STATE_PROVINCE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl COUNTRY_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_COUNTRY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_LC_06_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_LC_06_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_lc_06_sales_fact_1997")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    CITY_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    STATE_PROVINCE_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    COUNTRY_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_LC_06_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_LC_06_SALES_FACT_1997
            ))).build();

    //product_id,customer_id,quarter,the_year,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,INTEGER,VARCHAR(30),SMALLINT,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl QUARTER_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName("quarter").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl THE_YEAR_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName("the_year").withType("SMALLINT").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_LC_100_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_LC_100_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_lc_100_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    QUARTER_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    THE_YEAR_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_LC_100_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_LC_100_SALES_FACT_1997
            ))).build();

    //product_id,time_id,customer_id,store_sales,store_cost,unit_sales,fact_count
    //INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl TIME_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("time_id").withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_LL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_LL_01_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_ll_01_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    TIME_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_AGG_LL_01_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_LL_01_SALES_FACT_1997
            ))).build();

    //product_id,time_id,customer_id,store_sales_sum,store_cost_sum,unit_sales_sum,fact_count
    //INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4),INTEGER
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl TIME_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("time_id").withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("store_sales_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("store_cost_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("unit_sales_sum").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl FACT_COUNT_COLUMN_IN_AGG_PL_01_SALES_FACT_1997 = ColumnImpl.builder().withName("fact_count").withType("INTEGER").build();
    public static final PhysicalTableImpl AGG_PL_01_SALES_FACT_1997 = ((Builder) PhysicalTableImpl.builder().withName("agg_pl_01_sales_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    TIME_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    STORE_SALES_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    STORE_COST_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    UNIT_SALES_SUM_COLUMN_IN_AGG_PL_01_SALES_FACT_1997,
                    FACT_COUNT_COLUMN_IN_AGG_PL_01_SALES_FACT_1997
            ))).build();

    //store_id,store_type,region_id,store_name,store_number,store_street_address,store_city,store_state,store_postal_code,store_country,store_manager,store_phone,store_fax,first_opened_date,last_remodel_date,store_sqft,grocery_sqft,frozen_sqft,meat_sqft,coffee_bar,video_store,salad_bar,prepared_food,florist
    //INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),TIMESTAMP,TIMESTAMP,INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,SMALLINT,SMALLINT,SMALLINT,SMALLINT
    public static final ColumnImpl STORE_ID_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl REGION_ID_COLUMN_IN_STORE = ColumnImpl.builder().withName("region_id").withType("INTEGER").build();
    public static final ColumnImpl STORE_TYPE_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_TYPE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STREET_ADDRESS_COLUMN_IN_STORE = ColumnImpl.builder().withName(STORE_STREET_ADDRESS).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_NAME_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_NAME).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_COUNTRY_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COUNTRY).withType("INTEGER").build();
    public static final ColumnImpl STORE_MANAGER_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_MANAGER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_CITY_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_CITY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_STATE_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_STATE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_SQFT_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SQFT).withType("INTEGER").build();
    public static final ColumnImpl GROCERY_SQFT_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_GROCERY_SQFT).withType("INTEGER").build();
    public static final ColumnImpl FROZEN_SQFT_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_FROZEN_SQFT).withType("INTEGER").build();
    public static final ColumnImpl MEAT_SQFT_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_MEAT_SQFT).withType("INTEGER").build();
    public static final ColumnImpl COFFEE_BAR_COLUMN_IN_STORE = ColumnImpl.builder().withName(TABLE_COLUMN_COFFEE_BAR).withType("SMALLINT").build();
    public static final ColumnImpl STORE_POSTAL_CODE_COLUMN_IN_STORE = ColumnImpl.builder().withName("store_postal_code").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_NUMBER_COLUMN_IN_STORE = ColumnImpl.builder().withName("store_number").withType("INTEGER").build();
    public static final PhysicalTableImpl STORE_TABLE = ((Builder) PhysicalTableImpl.builder().withName(TABLE_STORE)
            .withColumns(List.of(
                STORE_ID_COLUMN_IN_STORE,
                STORE_TYPE_COLUMN_IN_STORE,
                STREET_ADDRESS_COLUMN_IN_STORE,
                STORE_NAME_COLUMN_IN_STORE,
                STORE_COUNTRY_COLUMN_IN_STORE,
                STORE_MANAGER_COLUMN_IN_STORE,
                STORE_CITY_COLUMN_IN_STORE,
                STORE_STATE_COLUMN_IN_STORE,
                STORE_SQFT_COLUMN_IN_STORE,
                GROCERY_SQFT_COLUMN_IN_STORE,
                FROZEN_SQFT_COLUMN_IN_STORE,
                MEAT_SQFT_COLUMN_IN_STORE,
                COFFEE_BAR_COLUMN_IN_STORE,
                STORE_POSTAL_CODE_COLUMN_IN_STORE,
                STORE_NUMBER_COLUMN_IN_STORE
            ))).build();

    //time_id,the_date,the_day,the_month,the_year,day_of_month,week_of_year,month_of_year,quarter,fiscal_period
    //INTEGER,TIMESTAMP,VARCHAR(30),VARCHAR(30),SMALLINT,SMALLINT,INTEGER,SMALLINT,VARCHAR(30),VARCHAR(30)
    public static final ColumnImpl TIME_ID_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_TIME_ID).withType("INTEGER").build();
    public static final ColumnImpl THE_DATE_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_THE_DATE).withType("TIMESTAMP").build();
    public static final ColumnImpl THE_MONTH_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_THE_MONTH).withType("SMALLINT").build();
    public static final ColumnImpl THE_YEAR_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_THE_YEAR).withType("SMALLINT").build();
    public static final ColumnImpl DAY_OF_MONTH_COLUMN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_DAY_OF_MONTH).withType("SMALLINT").build();
    public static final ColumnImpl WEEK_OF_YEAR_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_WEEK_OF_YEAR).withType("INTEGER").build();
    public static final ColumnImpl MONTH_OF_YEAR_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_MONTH_OF_YEAR).withType("SMALLINT").build();
    public static final ColumnImpl QUARTER_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName(TABLE_COLUMN_QUARTER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl FISCAL_PERIOD_COLUMN_IN_TIME_BY_DAY = ColumnImpl.builder().withName("fiscal_period").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl TIME_BY_DAY_TABLE = ((Builder) PhysicalTableImpl.builder().withName("time_by_day")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_TIME_BY_DAY,
                    THE_DATE_COLUMN_IN_TIME_BY_DAY,
                    THE_MONTH_COLUMN_IN_TIME_BY_DAY,
                    THE_YEAR_COLUMN_IN_TIME_BY_DAY,
                    DAY_OF_MONTH_COLUMN_TIME_BY_DAY,
                    WEEK_OF_YEAR_COLUMN_IN_TIME_BY_DAY,
                    MONTH_OF_YEAR_COLUMN_IN_TIME_BY_DAY,
                    QUARTER_COLUMN_IN_TIME_BY_DAY,
                    FISCAL_PERIOD_COLUMN_IN_TIME_BY_DAY
                    ))).build();

    //product_class_id,product_id,brand_name,product_name,SKU,SRP,gross_weight,net_weight,recyclable_package,low_fat,units_per_case,cases_per_pallet,shelf_width,shelf_height,shelf_depth
    //INTEGER,INTEGER,VARCHAR(60),VARCHAR(60),BIGINT,DECIMAL(10.4),REAL,REAL,SMALLINT,SMALLINT,SMALLINT,SMALLINT,REAL,REAL,REAL
    public static final ColumnImpl PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT = ColumnImpl.builder().withName(PRODUCT_CLASS_ID).withType("INTEGER").build();
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_PRODUCT = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl BRAND_NAME_COLUMN_IN_PRODUCT = ColumnImpl.builder().withName(TABLE_COLUMN_BRAND_NAME).withType("VARCHAR").withColumnSize(60).build();
    public static final ColumnImpl PRODUCT_NAME_COLUMN_IN_PRODUCT = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_NAME).withType("VARCHAR").withColumnSize(60).build();
    public static final PhysicalTableImpl PRODUCT_TABLE =  ((Builder) PhysicalTableImpl.builder().withName(TABLE_PRODUCT)
            .withColumns(List.of(
                    PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT,
                    PRODUCT_ID_COLUMN_IN_PRODUCT,
                    BRAND_NAME_COLUMN_IN_PRODUCT,
                    PRODUCT_NAME_COLUMN_IN_PRODUCT
            ))).build();


    //product_class_id,product_subcategory,product_category,product_department,product_family
    //INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30)
    public static final ColumnImpl PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT_CLASS = ColumnImpl.builder().withName(PRODUCT_CLASS_ID).withType("INTEGER").build();
    public static final ColumnImpl PRODUCT_SUBCATEGORY_COLUMN_IN_PRODUCT_CLASS = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_SUBCATEGORY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_CATEGORY_COLUMN_IN_PRODUCT_CLASS = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_CATEGORY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_DEPARTMENT_COLUMN_IN_PRODUCT_CLASS = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_DEPARTMENT).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl PRODUCT_FAMILY_COLUMN_IN_PRODUCT_CLASS = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_FAMILY).withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl PRODUCT_CLASS_TABLE = ((Builder) PhysicalTableImpl.builder().withName(TABLE_PRODUCT_CLASS)
            .withColumns(List.of(
                    PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT_CLASS,
                    PRODUCT_SUBCATEGORY_COLUMN_IN_PRODUCT_CLASS,
                    PRODUCT_CATEGORY_COLUMN_IN_PRODUCT_CLASS,
                    PRODUCT_DEPARTMENT_COLUMN_IN_PRODUCT_CLASS,
                    PRODUCT_FAMILY_COLUMN_IN_PRODUCT_CLASS
            ))).build();

    //employee_id,full_name,first_name,last_name,position_id,position_title,store_id,department_id,birth_date,hire_date,end_date,salary,supervisor_id,education_level,marital_status,gender,management_role
    //INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),INTEGER,VARCHAR(30),INTEGER,INTEGER,DATE,TIMESTAMP,TIMESTAMP,DECIMAL(10.4),INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30)
    public static final ColumnImpl EMPLOYEE_ID_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName(TABLE_COLUMN_EMPLOYEE_ID).withType("INTEGER").build();
    public static final ColumnImpl FIRST_NAME_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("first_name").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl LAST_NAME_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("last_name").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl MANAGEMENT_ROLE_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("management_role").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl POSITION_ID_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName(TABLE_COLUMN_POSITION_ID).withType("INTEGER").build();
    public static final ColumnImpl POSITION_TITLE_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("position_title").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("store_id").withType("INTEGER").build();
    public static final ColumnImpl SUPERVISOR_ID_COLUMN_IN_EMPLOYEE  = ColumnImpl.builder().withName("supervisor_id").withType("INTEGER").build();
    public static final ColumnImpl FULL_NAME_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("full_name").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl MARITAL_STATUS_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName(TABLE_COLUMN_MARITAL_STATUS).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl GENDER_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName(TABLE_COLUMN_GENDER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl SALARY_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName(SALARY).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl EDUCATION_LEVEL_COLUMN_IN_EMPLOYEE = ColumnImpl.builder().withName("education_level").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl EMPLOYEE_TABLE = ((Builder) PhysicalTableImpl.builder().withName(EMPLOYEE)
            .withColumns(List.of(
                    EMPLOYEE_ID_COLUMN_IN_EMPLOYEE,
                    FIRST_NAME_COLUMN_IN_EMPLOYEE,
                    LAST_NAME_COLUMN_IN_EMPLOYEE,
                    MANAGEMENT_ROLE_COLUMN_IN_EMPLOYEE,
                    POSITION_ID_COLUMN_IN_EMPLOYEE,
                    POSITION_TITLE_COLUMN_IN_EMPLOYEE,
                    SUPERVISOR_ID_COLUMN_IN_EMPLOYEE,
                    FULL_NAME_COLUMN_IN_EMPLOYEE,
                    MARITAL_STATUS_COLUMN_IN_EMPLOYEE,
                    GENDER_COLUMN_IN_EMPLOYEE,
                    SALARY_COLUMN_IN_EMPLOYEE,
                    EDUCATION_LEVEL_COLUMN_IN_EMPLOYEE
            ))).build();

    //department_id,department_description
    //INTEGER,VARCHAR(30)
    public static final ColumnImpl DEPARTMENT_ID_COLUMN_IN_DEPARTMENT = ColumnImpl.builder().withName(TABLE_COLUMN_DEPARTMENT_ID).withType("INTEGER").build();
    public static final PhysicalTableImpl DEPARTAMENT_TABLE = ((Builder) PhysicalTableImpl.builder().withName("department")
            .withColumns(List.of(DEPARTMENT_ID_COLUMN_IN_DEPARTMENT))).build();

    //position_id,position_title,pay_type,min_scale,max_scale,management_role
    //INTEGER,VARCHAR(30),VARCHAR(30),DECIMAL(10.4),DECIMAL(10.4),VARCHAR(30)
    public static final ColumnImpl POSITION_ID_COLUMN_IN_POSITION = ColumnImpl.builder().withName(TABLE_COLUMN_POSITION_ID).withType("INTEGER").build();
    public static final ColumnImpl PAY_TYPE_COLUMN_IN_POSITION = ColumnImpl.builder().withName("pay_type").withType("INTEGER").build();
    public static final PhysicalTableImpl POSITION_TABLE = ((Builder) PhysicalTableImpl.builder().withName(TABLE_NAME_POSITION)
            .withColumns(List.of(POSITION_ID_COLUMN_IN_POSITION, PAY_TYPE_COLUMN_IN_POSITION))).build();

    //pay_date,employee_id,department_id,currency_id,salary_paid,overtime_paid,vacation_accrued,vacation_used
    //TIMESTAMP,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),REAL,REAL
    public static final ColumnImpl PAY_DATE_COLUMN_IN_SALARY = ColumnImpl.builder().withName("pay_date").withType("TIMESTAMP").build();
    public static final ColumnImpl EMPLOYEE_ID_COLUMN_IN_SALARY = ColumnImpl.builder().withName(TABLE_COLUMN_EMPLOYEE_ID).withType("INTEGER").build();
    public static final ColumnImpl DEPARTMENT_ID_COLUMN_IN_SALARY = ColumnImpl.builder().withName(TABLE_COLUMN_DEPARTMENT_ID).withType("INTEGER").build();
    public static final ColumnImpl SALARY_PAID_COLUMN_IN_SALARY = ColumnImpl.builder().withName("salary_paid").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final PhysicalTableImpl SALARY_TABLE = ((Builder) PhysicalTableImpl.builder().withName(SALARY)
            .withColumns(List.of(
                    PAY_DATE_COLUMN_IN_SALARY,
                    EMPLOYEE_ID_COLUMN_IN_SALARY,
                    DEPARTMENT_ID_COLUMN_IN_SALARY,
                    SALARY_PAID_COLUMN_IN_SALARY
            ))).build();

    public static final ColumnImpl EMPLOYEE_ID_COLUMN_IN_EMPLOYEE_CLOSURE = ColumnImpl.builder().withName(TABLE_COLUMN_EMPLOYEE_ID).withType("INTEGER").build();
    public static final ColumnImpl SUPERVISOR_ID_COLUMN_IN_EMPLOYEE_CLOSURE = ColumnImpl.builder().withName("supervisor_id").withType("INTEGER").build();
    public static final PhysicalTableImpl EMPLOYEE_CLOSURE_TABLE = ((Builder) PhysicalTableImpl.builder().withName(EMPLOYEE_CLOSURE)
            .withColumns(List.of(
                    EMPLOYEE_ID_COLUMN_IN_EMPLOYEE_CLOSURE,
                    SUPERVISOR_ID_COLUMN_IN_EMPLOYEE_CLOSURE
            ))).build();

    //store_id,store_type,region_id,store_name,store_number,store_street_address,store_city,store_state,store_postal_code,store_country,store_manager,store_phone,store_fax,first_opened_date,last_remodel_date,store_sqft,grocery_sqft,frozen_sqft,meat_sqft,coffee_bar,video_store,salad_bar,prepared_food,florist
    //INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),INTEGER,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),TIMESTAMP,TIMESTAMP,INTEGER,INTEGER,INTEGER,INTEGER,SMALLINT,SMALLINT,SMALLINT,SMALLINT,SMALLINT
    public static final ColumnImpl STORE_ID_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_TYPE_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_TYPE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_NAME_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_NAME).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STREET_ADDRESS_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(STORE_STREET_ADDRESS).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_STATE_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_STATE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_COUNTRY_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COUNTRY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_MANAGER_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_MANAGER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_CITY_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_CITY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STORE_SQFT_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SQFT).withType("INTEGER").build();
    public static final ColumnImpl GROCERY_SQFT_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_GROCERY_SQFT).withType("INTEGER").build();
    public static final ColumnImpl FROZEN_SQFT_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_FROZEN_SQFT).withType("INTEGER").build();
    public static final ColumnImpl MEAT_SQFT_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_MEAT_SQFT).withType("INTEGER").build();
    public static final ColumnImpl COFFEE_BAR_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName(TABLE_COLUMN_COFFEE_BAR).withType("SMALLINT").build();
    public static final ColumnImpl REGION_ID_COLUMN_IN_STORE_RAGGED = ColumnImpl.builder().withName("region_id").withType("INTEGER").build();
    public static final PhysicalTableImpl STORE_RAGGED_TABLE = ((Builder) PhysicalTableImpl.builder().withName(STORE_RAGGED)
            .withColumns(List.of(
                    STORE_ID_COLUMN_IN_STORE_RAGGED,
                    STORE_TYPE_COLUMN_IN_STORE_RAGGED,
                    STORE_NAME_COLUMN_IN_STORE_RAGGED,
                    STREET_ADDRESS_COLUMN_IN_STORE_RAGGED,
                    STORE_STATE_COLUMN_IN_STORE_RAGGED,
                    STORE_COUNTRY_COLUMN_IN_STORE_RAGGED,
                    STORE_MANAGER_COLUMN_IN_STORE_RAGGED,
                    STORE_CITY_COLUMN_IN_STORE_RAGGED,
                    STORE_SQFT_COLUMN_IN_STORE_RAGGED,
                    GROCERY_SQFT_COLUMN_IN_STORE_RAGGED,
                    FROZEN_SQFT_COLUMN_IN_STORE_RAGGED,
                    MEAT_SQFT_COLUMN_IN_STORE_RAGGED,
                    COFFEE_BAR_COLUMN_IN_STORE_RAGGED,
                    REGION_ID_COLUMN_IN_STORE_RAGGED
            ))).build();

    //warehouse_id,warehouse_class_id,stores_id,warehouse_name,wa_address1,wa_address2,wa_address3,wa_address4,warehouse_city,warehouse_state_province,warehouse_postal_code,warehouse_country,warehouse_owner_name,warehouse_phone,warehouse_fax
    //INTEGER,INTEGER,INTEGER,VARCHAR(60),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30)
    public static final ColumnImpl WAREHOUSE_ID_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_STORES_ID).withType("INTEGER").build();
    public static final ColumnImpl WAREHOUSE_COUNTRY_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_COUNTRY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WAREHOUSE_STATE_PROVINCE_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_STATE_PROVINCE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WAREHOUSE_CITY_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_CITY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WAREHOUSE_NAME_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_NAME).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WAREHOUSE_FAX_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName("warehouse_fax").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WA_ADDRESS1_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName("wa_address1").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WA_ADDRESS2_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName("wa_address2").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl WA_ADDRESS3_COLUMN_IN_WAREHOUSE = ColumnImpl.builder().withName("wa_address3").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl WAREHOUSE_TABLE = ((Builder) PhysicalTableImpl.builder().withName("warehouse")
            .withColumns(List.of(
                    WAREHOUSE_ID_COLUMN_IN_WAREHOUSE,
                    STORE_ID_COLUMN_IN_WAREHOUSE,
                    WAREHOUSE_COUNTRY_COLUMN_IN_WAREHOUSE,
                    WAREHOUSE_STATE_PROVINCE_COLUMN_IN_WAREHOUSE,
                    WAREHOUSE_CITY_COLUMN_IN_WAREHOUSE,
                    WAREHOUSE_NAME_COLUMN_IN_WAREHOUSE,
                    WAREHOUSE_FAX_COLUMN_IN_WAREHOUSE,
                    WA_ADDRESS1_COLUMN_IN_WAREHOUSE,
                    WA_ADDRESS2_COLUMN_IN_WAREHOUSE,
                    WA_ADDRESS3_COLUMN_IN_WAREHOUSE
            ))).build();

    //promotion_id,promotion_district_id,promotion_name,media_type,cost,start_date,end_date
    //INTEGER,INTEGER,VARCHAR(30),VARCHAR(30),DECIMAL(10.4),TIMESTAMP,TIMESTAMP
    public static final ColumnImpl PROMOTION_ID_COLUMN_IN_PROMOTION = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_ID).withType("INTEGER").build();
    public static final ColumnImpl PROMOTION_NAME_COLUMN_IN_PROMOTION = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_NAME).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl MEDIA_TYPE_COLUMN_IN_PROMOTION = ColumnImpl.builder().withName(TABLE_COLUMN_MEDIA_TYPE).withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl PROMOTION_TABLE = ((Builder) PhysicalTableImpl.builder().withName("promotion")
            .withColumns(List.of(
                    PROMOTION_ID_COLUMN_IN_PROMOTION,
                    PROMOTION_NAME_COLUMN_IN_PROMOTION,
                    MEDIA_TYPE_COLUMN_IN_PROMOTION
            ))).build();

    //customer_id,account_num,lname,fname,mi,address1,address2,address3,address4,city,state_province,postal_code,country,customer_region_id,phone1,phone2,birthdate,marital_status,yearly_income,gender,total_children,num_children_at_home,education,date_accnt_opened,member_card,occupation,houseowner,num_cars_owned,fullname
    //INTEGER,BIGINT,VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),VARCHAR(30),INTEGER,VARCHAR(30),VARCHAR(30),DATE,VARCHAR(30),VARCHAR(30),VARCHAR(30),SMALLINT,SMALLINT,VARCHAR(30),DATE,VARCHAR(30),VARCHAR(30),VARCHAR(30),INTEGER,VARCHAR(60)
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl CITY_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_CITY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl STATE_PROVINCE_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_STATE_PROVINCE).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl COUNTRY_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_COUNTRY).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl CUSTOMER_REGION_ID_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("customer_region_id").withType("INTEGER").build();
    public static final ColumnImpl MARITAL_STATUS_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_MARITAL_STATUS).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl YEARLY_INCOME_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("yearly_income").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl GENDER_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName(TABLE_COLUMN_GENDER).withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl EDUCATION_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("education").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl NUM_CARS_OWNED_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("num_cars_owned").withType("INTEGER").build();
    public static final ColumnImpl TOTAL_CHILDREN_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("total_children").withType("SMALLINT").build();
    public static final ColumnImpl FULL_NAME_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("fullname").withType("VARCHAR").withColumnSize(60).build();
    public static final ColumnImpl ACCOUNT_NUM_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("account_num").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl LNAME_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("lname").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl FNAME_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("fname").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl ADDRESS2_COLUMN_IN_CUSTOMER = ColumnImpl.builder().withName("address2").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl CUSTOMER_TABLE = ((Builder) PhysicalTableImpl.builder().withName("customer")
            .withColumns(List.of(
                    CUSTOMER_ID_COLUMN_IN_CUSTOMER,
                    CITY_COLUMN_IN_CUSTOMER,
                    STATE_PROVINCE_COLUMN_IN_CUSTOMER,
                    COUNTRY_COLUMN_IN_CUSTOMER,
                    MARITAL_STATUS_COLUMN_IN_CUSTOMER,
                    YEARLY_INCOME_COLUMN_IN_CUSTOMER,
                    GENDER_COLUMN_IN_CUSTOMER,
                    EDUCATION_COLUMN_IN_CUSTOMER,
                    FULL_NAME_COLUMN_IN_CUSTOMER,
                    ACCOUNT_NUM_COLUMN_IN_CUSTOMER,
                    LNAME_COLUMN_IN_CUSTOMER,
                    FNAME_COLUMN_IN_CUSTOMER,
                    ADDRESS2_COLUMN_IN_CUSTOMER
            ))).build();

    //product_id,time_id,warehouse_id,store_id,units_ordered,units_shipped,warehouse_sales,warehouse_cost,supply_time,store_invoice
    //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),SMALLINT,DECIMAL(10.4)
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl TIME_ID_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_TIME_ID).withType("INTEGER").build();
    public static final ColumnImpl WAREHOUSE_ID_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_WAREHOUSE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_INVOICE_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("store_invoice").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl SUPPLY_TIME_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("supply_time").withType("SMALLINT").build();
    public static final ColumnImpl WAREHOUSE_COST_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("warehouse_cost").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl WAREHOUSE_SALES_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("warehouse_sales").withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNITS_SHIPPED_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("units_shipped").withType("INTEGER").build();
    public static final ColumnImpl UNITS_ORDERED_COLUMN_IN_INVENTORY_FACKT_1997 = ColumnImpl.builder().withName("units_ordered").withType("INTEGER").build();
    public static final PhysicalTableImpl INVENTORY_FACKT_1997_TABLE = ((Builder) PhysicalTableImpl.builder().withName("inventory_fact_1997")
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_INVENTORY_FACKT_1997,
                    TIME_ID_COLUMN_IN_INVENTORY_FACKT_1997,
                    WAREHOUSE_ID_COLUMN_IN_INVENTORY_FACKT_1997,
                    STORE_ID_COLUMN_IN_INVENTORY_FACKT_1997,
                    STORE_INVOICE_COLUMN_IN_INVENTORY_FACKT_1997,
                    SUPPLY_TIME_COLUMN_IN_INVENTORY_FACKT_1997,
                    WAREHOUSE_COST_COLUMN_IN_INVENTORY_FACKT_1997,
                    WAREHOUSE_SALES_COLUMN_IN_INVENTORY_FACKT_1997,
                    UNITS_SHIPPED_COLUMN_IN_INVENTORY_FACKT_1997,
                    UNITS_ORDERED_COLUMN_IN_INVENTORY_FACKT_1997
            ))).build();


    //product_id,time_id,customer_id,promotion_id,store_id,store_sales,store_cost,unit_sales
    //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,DECIMAL(10.4),DECIMAL(10.4),DECIMAL(10.4)
    public static final ColumnImpl PRODUCT_ID_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PRODUCT_ID).withType("INTEGER").build();
    public static final ColumnImpl TIME_ID_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_TIME_ID).withType("INTEGER").build();
    public static final ColumnImpl CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_CUSTOMER_ID).withType("INTEGER").build();
    public static final ColumnImpl PROMOTION_ID_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_PROMOTION_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_ID_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_ID).withType("INTEGER").build();
    public static final ColumnImpl STORE_SALES_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl STORE_COST_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_STORE_COST).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final ColumnImpl UNIT_SALES_COLUMN_IN_SALES_FACT_1997 = ColumnImpl.builder().withName(TABLE_COLUMN_UNIT_SALES).withType("DECIMAL").withColumnSize(10).withDecimalDigits(4).build();
    public static final PhysicalTableImpl SALES_FACT_1997_TABLE = ((Builder) PhysicalTableImpl.builder().withName(SALES_FACT_1997)
            .withColumns(List.of(
                    PRODUCT_ID_COLUMN_IN_SALES_FACT_1997,
                    TIME_ID_COLUMN_IN_SALES_FACT_1997,
                    CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997,
                    PROMOTION_ID_COLUMN_IN_SALES_FACT_1997,
                    STORE_ID_COLUMN_IN_SALES_FACT_1997,
                    STORE_SALES_COLUMN_IN_SALES_FACT_1997,
                    STORE_COST_COLUMN_IN_SALES_FACT_1997,
                    UNIT_SALES_COLUMN_IN_SALES_FACT_1997
                    ))).build();

    public static final DatabaseSchemaImpl DATABASE_SCHEMA = DatabaseSchemaImpl.builder()
            .withName(SCHEMA_NAME)
            .withTables(List.of(AGG_C_10_SALES_FACT_1997, AGG_C_14_SALES_FACT_1997,
                            AGG_C_SPECIAL_SALES_FACT_1997, AGG_G_MS_PCAT_SALES_FACT_1997,
                            AGG_L_03_SALES_FACT_1997, AGG_L_04_SALES_FACT_1997,
                            AGG_L_05_SALES_FACT_1997, AGG_LC_06_SALES_FACT_1997,
                            AGG_LC_100_SALES_FACT_1997, AGG_LL_01_SALES_FACT_1997,
                            AGG_PL_01_SALES_FACT_1997,STORE_TABLE, TIME_BY_DAY_TABLE, PRODUCT_TABLE, PRODUCT_CLASS_TABLE,
                            EMPLOYEE_TABLE, DEPARTAMENT_TABLE, POSITION_TABLE, SALARY_TABLE,
                            EMPLOYEE_CLOSURE_TABLE, STORE_RAGGED_TABLE, WAREHOUSE_TABLE, PROMOTION_TABLE,
                            CUSTOMER_TABLE, INVENTORY_FACKT_1997_TABLE, SALES_FACT_1997_TABLE))
            .build();

    public static final TableQueryMappingImpl QUERY_TABLE_STORE =
        TableQueryMappingImpl.builder().withTable(STORE_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_TIME_BY_DAY = TableQueryMappingImpl.builder().withTable(TIME_BY_DAY_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_PRODUCT =
        TableQueryMappingImpl.builder().withTable(PRODUCT_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_PRODUCT_CLASS = TableQueryMappingImpl.builder().withTable(
        PRODUCT_CLASS_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_EMPLOYEE = TableQueryMappingImpl.builder().withTable(
        EMPLOYEE_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_DEPARTMENT = TableQueryMappingImpl.builder().withTable(
        DEPARTAMENT_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_POSITION = TableQueryMappingImpl.builder().withTable(
        POSITION_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_SALARY = TableQueryMappingImpl.builder().withTable(
        SALARY_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_EMPLOYEE_CLOSURE = TableQueryMappingImpl.builder().withTable(
        EMPLOYEE_CLOSURE_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_STORE_RAGGED = TableQueryMappingImpl.builder().withTable(
        STORE_RAGGED_TABLE).build();

    public static final JoinQueryMappingImpl JOIN_PRODUCT_PRODUCT_CLASS = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT)
            .withQuery(QUERY_TABLE_PRODUCT).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID_COLUMN_IN_PRODUCT_CLASS)
            .withQuery(QUERY_TABLE_PRODUCT_CLASS).build()).build();
    public static final JoinQueryMappingImpl JOIN_EMPLOYEE_STORE = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(STORE_ID_COLUMN_IN_EMPLOYEE)
            .withQuery(QUERY_TABLE_EMPLOYEE).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(STORE_ID_COLUMN_IN_STORE)
            .withQuery(QUERY_TABLE_STORE).build()).build();
    public static final JoinQueryMappingImpl JOIN_EMPLOYEE_POSITION = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(POSITION_ID_COLUMN_IN_EMPLOYEE)
            .withQuery(QUERY_TABLE_EMPLOYEE).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(POSITION_ID_COLUMN_IN_POSITION)
            .withQuery(QUERY_TABLE_POSITION).build()).build();
    public static final TableQueryMappingImpl warehouseTable =
        TableQueryMappingImpl.builder().withTable(WAREHOUSE_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_PROMOTION =
        TableQueryMappingImpl.builder().withTable(PROMOTION_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_CUSTOMER =
        TableQueryMappingImpl.builder().withTable(CUSTOMER_TABLE).build();
    public static final TableQueryMappingImpl QUERY_TABLE_inventoryFact1997 =
        TableQueryMappingImpl.builder().withTable(INVENTORY_FACKT_1997_TABLE).build();

    public static final LevelMappingImpl LEVEL_STORE_COUNTRY = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_COUNTRY_WITH_NEVER = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_COUNTRY_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY_COLUMN_IN_STORE)
        .withTable(STORE_TABLE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_STATE_UNIQUE_MEMBERS_TRUE = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(STORE_STATE_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_CYTY_IF_PARENTS_NAME = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(STORE_STATE_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.IF_PARENTS_NAME)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_CYTY_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withTable(STORE_TABLE)
        .withColumn(STORE_STATE_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_CYTY = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(STORE_CITY_COLUMN_IN_STORE)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_CYTY_IF_BLANK_NAME = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(STORE_CITY_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(false)
        .withHideMemberIfType(HideMemberIfType.IF_BLANK_NAME)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_CYTY_WITH_TABLE_COLUMN_STORE_CITY = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withTable(STORE_TABLE)
        .withColumn(STORE_CITY_COLUMN_IN_STORE)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_NAME_WITHOUT_TABLE = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(STORE_NAME_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(STORE_TYPE_COLUMN_IN_STORE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER_COLUMN_IN_STORE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(COFFEE_BAR_COLUMN_IN_STORE).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STREET_ADDRESS_COLUMN_IN_STORE).withDataType(
                DataType.STRING).build()
        ))
        .build();

    public static final LevelMappingImpl LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(STORE_NAME_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(STORE_TYPE_COLUMN_IN_STORE_RAGGED).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER_COLUMN_IN_STORE_RAGGED).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT_COLUMN_IN_STORE_RAGGED).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT_COLUMN_IN_STORE_RAGGED).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT_COLUMN_IN_STORE_RAGGED).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT_COLUMN_IN_STORE_RAGGED).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(COFFEE_BAR_COLUMN_IN_STORE_RAGGED).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STREET_ADDRESS_COLUMN_IN_STORE_RAGGED).withDataType(
                DataType.STRING).build()
        ))
        .build();

    public static final LevelMappingImpl LEVEL_STORE_NAME_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withTable(STORE_TABLE)
        .withColumn(STORE_NAME_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(STORE_TYPE_COLUMN_IN_STORE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER_COLUMN_IN_STORE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT_COLUMN_IN_STORE).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(COFFEE_BAR_COLUMN_IN_STORE).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STREET_ADDRESS_COLUMN_IN_STORE).withDataType(
                DataType.STRING).build()
        ))
        .build();

    public static final LevelMappingImpl LEVEL_STORE_SQFT = LevelMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(STORE_SQFT_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .withType(DataType.NUMERIC)
        .build();

    public static final LevelMappingImpl LEVEL_YEAR = LevelMappingImpl.builder()
        .withName(YEAR)
        .withColumn(THE_YEAR_COLUMN_IN_TIME_BY_DAY)
        .withUniqueMembers(true)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_YEARS)
        .build();

    public static final LevelMappingImpl LEVEL_WEEK = LevelMappingImpl.builder()
        .withName("Week")
        .withColumn(WEEK_OF_YEAR_COLUMN_IN_TIME_BY_DAY)
        .withType(DataType.NUMERIC)
        .withUniqueMembers(false)
        .withLevelType(LevelType.TIME_WEEKS)
        .build();

    public static final LevelMappingImpl LEVEL_DAY = LevelMappingImpl.builder()
        .withName("Day")
        .withColumn(DAY_OF_MONTH_COLUMN_TIME_BY_DAY)
        .withType(DataType.NUMERIC)
        .withUniqueMembers(false)
        .withLevelType(LevelType.TIME_DAYS)
        .build();

    public static final LevelMappingImpl LEVEL_QUARTER = LevelMappingImpl.builder()
        .withName(QUARTER)
        .withColumn(QUARTER_COLUMN_IN_TIME_BY_DAY)
        .withUniqueMembers(false)
        .withLevelType(LevelType.TIME_QUARTERS)
        .build();

    public static final LevelMappingImpl LEVEL_MONTH = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(MONTH_OF_YEAR_COLUMN_IN_TIME_BY_DAY)
        .withUniqueMembers(false)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_MONTHS)
        .build();

    public static final LevelMappingImpl LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(MONTH_OF_YEAR_COLUMN_IN_TIME_BY_DAY)
        .withNameColumn(THE_MONTH_COLUMN_IN_TIME_BY_DAY)
        .withUniqueMembers(false)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_MONTHS)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_TYPE_WITHOUT_TABLE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_STORE_TYPE)
        .withColumn(STORE_TYPE_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STORE_TYPE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_STORE_TYPE)
        .withTable(STORE_TABLE)
        .withColumn(STORE_TYPE_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_productFamily = LevelMappingImpl.builder()
        .withName(PRODUCT_FAMILY)
        .withTable(PRODUCT_CLASS_TABLE)
        .withColumn(PRODUCT_FAMILY_COLUMN_IN_PRODUCT_CLASS)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_productDepartment = LevelMappingImpl.builder()
        .withName(PRODUCT_DEPARTMENT)
        .withTable(PRODUCT_CLASS_TABLE)
        .withColumn(PRODUCT_DEPARTMENT_COLUMN_IN_PRODUCT_CLASS)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_productCategory = LevelMappingImpl.builder()
        .withName(PRODUCT_CATEGORY)
        .withTable(PRODUCT_CLASS_TABLE)
        .withColumn(PRODUCT_CATEGORY_COLUMN_IN_PRODUCT_CLASS)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_productSubcategory = LevelMappingImpl.builder()
        .withName(PRODUCT_SUBCATEGORY)
        .withTable(PRODUCT_CLASS_TABLE)
        .withColumn(PRODUCT_SUBCATEGORY_COLUMN_IN_PRODUCT_CLASS)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_brandName = LevelMappingImpl.builder()
        .withName(BRAND_NAME)
        .withTable(PRODUCT_TABLE)
        .withColumn(BRAND_NAME_COLUMN_IN_PRODUCT)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_PRODUCT_NAME = LevelMappingImpl.builder()
        .withName(PRODUCT_NAME)
        .withTable(PRODUCT_TABLE)
        .withColumn(PRODUCT_NAME_COLUMN_IN_PRODUCT)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_COUNTRY = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(WAREHOUSE_COUNTRY_COLUMN_IN_WAREHOUSE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_COUNTRY_WITH_NEVER = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(STORE_COUNTRY_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .build();

    public static final LevelMappingImpl LEVEL_state = LevelMappingImpl.builder()
        .withName(STATE)
        .withColumn(STORE_STATE_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.IF_PARENTS_NAME)
        .build();

    public static final LevelMappingImpl LEVEL_stateProvince = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(WAREHOUSE_STATE_PROVINCE_COLUMN_IN_WAREHOUSE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_WAREHOUSE_CIT = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(WAREHOUSE_CITY_COLUMN_IN_WAREHOUSE)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_STORE_CITY = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(STORE_CITY_COLUMN_IN_STORE_RAGGED)
        .withUniqueMembers(false)
        .withHideMemberIfType(HideMemberIfType.IF_BLANK_NAME)
        .build();

    public static final LevelMappingImpl LEVEL_WAREHOUSE_NAME = LevelMappingImpl.builder()
        .withName(WAREHOUSE_NAME)
        .withColumn(WAREHOUSE_NAME_COLUMN_IN_WAREHOUSE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_MEDIA_TYPE = LevelMappingImpl.builder()
        .withName(MEDIA_TYPE)
        .withColumn(MEDIA_TYPE_COLUMN_IN_PROMOTION)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_PROMOTION_NAME = LevelMappingImpl.builder()
        .withName(PROMOTION_NAME)
        .withColumn(PROMOTION_NAME_COLUMN_IN_PROMOTION)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(COUNTRY_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(STATE_PROVINCE_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_CITY = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(CITY_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_NAME = LevelMappingImpl.builder()
        .withName(NAME)
        .withColumn(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
        .withType(DataType.NUMERIC)
        .withUniqueMembers(true)
        .withNameExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ORACLE,
                        DIALECT_H2,
                        DIALECT_HSQLDB,
                        DIALECT_POSTGRES,
                        DIALECT_LUCIDDB,
                        DIALECT_TERADATA
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_HIVE
                    ))
                    .withStatement("`customer`.`fullname`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ACCESS,
                        DIALECT_MSSQL
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_MYSQL,
                        DIALECT_MARIADB
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_DERBY,
                        DIALECT_NEOVIEW,
                        DIALECT_SNOWFLAKE
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_DB2
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_GENERIC
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withOrdinalExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ORACLE,
                        DIALECT_H2,
                        DIALECT_HSQLDB,
                        DIALECT_POSTGRES,
                        DIALECT_LUCIDDB
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ACCESS,
                        DIALECT_MSSQL
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_MYSQL,
                        DIALECT_MARIADB
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_NEOVIEW,
                        DIALECT_DERBY,
                        DIALECT_SNOWFLAKE
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_DB2
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_GENERIC
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_GENDER).withColumn(GENDER_COLUMN_IN_CUSTOMER).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_MARITAL_STATUS).withColumn(MARITAL_STATUS_COLUMN_IN_CUSTOMER).build(),
            MemberPropertyMappingImpl.builder().withName("Education").withColumn(EDUCATION_COLUMN_IN_CUSTOMER).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_YEARLY_INCOME).withColumn(YEARLY_INCOME_COLUMN_IN_CUSTOMER).build()
        ))
        .build();

    public static final LevelMappingImpl LEVEL_EDUCATION = LevelMappingImpl.builder()
        .withName(NAME_LEVEL_EDUCATION_LEVEL)
        .withColumn(EDUCATION_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_GENDER = LevelMappingImpl.builder()
        .withName(NAME_LEVEL_GENDER)
        .withColumn(GENDER_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_MARITAL_STATUS = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_MARITAL_STATUS)
        .withColumn(MARITAL_STATUS_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .withApproxRowCount("111")
        .build();

    public static final LevelMappingImpl LEVEL_YEARLY_INCOME = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_YEARLY_INCOME)
        .withColumn(YEARLY_INCOME_COLUMN_IN_CUSTOMER)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_HAS_COFFEE_BAR = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_HAS_COFFEE_BAR)
        .withColumn(COFFEE_BAR_COLUMN_IN_STORE)
        .withUniqueMembers(true)
        .withType(DataType.BOOLEAN)
        .build();

    public static final LevelMappingImpl LEVEL_PAY_TYPE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_PAY_TYPE)
        .withColumn(PAY_TYPE_COLUMN_IN_POSITION)
        .withTable(POSITION_TABLE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_MANAGEMENT_ROLE = LevelMappingImpl.builder()
        .withName("Management Role")
        .withColumn(MANAGEMENT_ROLE_COLUMN_IN_EMPLOYEE)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_POSITION_TITLE = LevelMappingImpl.builder()
        .withName("Position Title")
        .withColumn(POSITION_TITLE_COLUMN_IN_EMPLOYEE)
        .withOrdinalColumn(POSITION_ID_COLUMN_IN_EMPLOYEE)
        .withUniqueMembers(false)
        .build();

    public static final LevelMappingImpl LEVEL_DEPARTAMENT_DESCRIPTION = LevelMappingImpl.builder()
        .withName("Department Description")
        .withType(DataType.NUMERIC)
        .withColumn(DEPARTMENT_ID_COLUMN_IN_DEPARTMENT)
        .withUniqueMembers(true)
        .build();

    public static final LevelMappingImpl LEVEL_EMPLOYEE_ID = LevelMappingImpl.builder()
        .withName("Employee Id")
        .withType(DataType.NUMERIC)
        .withColumn(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
        .withParentColumn(SUPERVISOR_ID_COLUMN_IN_EMPLOYEE)
        .withNameColumn(FULL_NAME_COLUMN_IN_EMPLOYEE)
        .withNullParentValue("0")
        .withParentChildLink(
            ParentChildLinkMappingImpl.builder()
                .withParentColumn(SUPERVISOR_ID_COLUMN_IN_EMPLOYEE_CLOSURE)
                .withChildColumn(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE_CLOSURE)
                .withTable(QUERY_TABLE_EMPLOYEE_CLOSURE)
                .build())
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_MARITAL_STATUS).withColumn(MARITAL_STATUS_COLUMN_IN_EMPLOYEE).build(),
            MemberPropertyMappingImpl.builder().withName("Position Title").withColumn(POSITION_TITLE_COLUMN_IN_EMPLOYEE).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_GENDER).withColumn(GENDER_COLUMN_IN_EMPLOYEE).build(),
            MemberPropertyMappingImpl.builder().withName("Salary").withColumn(SALARY_COLUMN_IN_EMPLOYEE).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_EDUCATION_LEVEL).withColumn(EDUCATION_LEVEL_COLUMN_IN_EMPLOYEE).build(),
            MemberPropertyMappingImpl.builder().withName("Management Role").withColumn(MANAGEMENT_ROLE_COLUMN_IN_EMPLOYEE).build()
        ))
        .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT = CalculatedMemberMappingImpl.builder()
        .withName("Profit")
        .withFormula("[Measures].[Store Sales] - [Measures].[Store Cost]")
        .withCalculatedMemberProperties(List.of(
            CalculatedMemberPropertyMappingImpl.builder()
                .withName(FORMAT_STRING)
                .withValue(FORMAT_STRING_CURRENCY)
                .build()
        ))
        .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_WITH_ORDER =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit")
            .withFormula("[Measures].[Store Sales] - [Measures].[Store Cost]")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING)
                    .withValue(FORMAT_STRING_CURRENCY)
                    .build(),
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(MEMBER_ORDINAL)
                    .withValue("4")
                    .build()
            ))
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_LAST_PERIOD =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit last Period")
            .withFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING)
                    .withValue(FORMAT_STRING_CURRENCY)
                    .build(),
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(MEMBER_ORDINAL)
                    .withValue("18")
                    .build()
            ))
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2 =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit last Period")
            .withFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .withVisible(false)
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(MEMBER_ORDINAL)
                    .withValue("5")
                    .build()
            ))
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_GROWTH =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit Growth")
//            .withName("Gewinn-Wachstum")
            .withFormula("([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]")
            .withVisible(true)
            //.withCaption("Gewinn-Wachstum")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING)
                    .withValue("0.0%")
                    .build()
            ))
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE =
        CalculatedMemberMappingImpl.builder()
            .withName("Average Warehouse Sale")
            .withFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING)
                    .withValue(FORMAT_STRING_CURRENCY)
                    .build()
            ))
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_EMPLOEE_SALARY =
        CalculatedMemberMappingImpl.builder()
            .withName("Employee Salary")
            .withFormatString(CURRENCY)
            .withFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])")
            .build();

    public static final CalculatedMemberMappingImpl CALCULATED_MEMBER_AVG_SALARY =
        CalculatedMemberMappingImpl.builder()
            .withName("Avg Salary")
            .withFormatString(CURRENCY)
            .withFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]")
            .build();

    public static final CalculatedMemberMappingImpl profitPerUnitShippedCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit Per Unit Shipped")
            .withFormatString(CURRENCY)
            .withFormula("[Measures].[Profit] / [Measures].[Units Shipped]")
            .build();

    public static final NamedSetMappingImpl topSellersNamedSet = NamedSetMappingImpl.builder()
        .withName("Top Sellers")
        .withFormula("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])")
        .build();

    public static final HierarchyMappingImpl storeHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(STORE_ID_COLUMN_IN_STORE)
        .withQuery(QUERY_TABLE_STORE)
        .withLevels(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_STATE_UNIQUE_MEMBERS_TRUE, LEVEL_STORE_CYTY,
            LEVEL_STORE_NAME_WITHOUT_TABLE))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(storeHierarchy))
            .build();

    public static final HierarchyMappingImpl STORE_HIERARCHY_STORE_RAGGED_CUBE = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID_COLUMN_IN_STORE_RAGGED)
            .withQuery(QUERY_TABLE_STORE_RAGGED)
            .withLevels(List.of(LEVEL_STORE_COUNTRY_WITH_NEVER, LEVEL_STORE_CYTY_IF_PARENTS_NAME,
                LEVEL_STORE_CYTY_IF_BLANK_NAME, LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER))
            .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_STORE_RAGGED =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(STORE_HIERARCHY_STORE_RAGGED_CUBE))
            .build();

    public static final HierarchyMappingImpl STORE_HIERARCHY_FOR_HR_CUBE = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
            .withPrimaryKeyTable(EMPLOYEE_TABLE)
            .withQuery(JOIN_EMPLOYEE_STORE)
            .withLevels(List.of(LEVEL_STORE_COUNTRY_WITH_TABLE, LEVEL_STORE_CYTY_WITH_TABLE,
                LEVEL_STORE_CYTY_WITH_TABLE_COLUMN_STORE_CITY, LEVEL_STORE_NAME_WITH_TABLE))
            .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(STORE_HIERARCHY_FOR_HR_CUBE))
            .build();

    public static final HierarchyMappingImpl HIERARCHY_STORE_SIZE_IN_SQFT = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(STORE_ID_COLUMN_IN_STORE)
        .withQuery(QUERY_TABLE_STORE)
        .withLevels(List.of(LEVEL_STORE_SQFT))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_SIZE_IN_SQFT =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_SIZE_IN_SQFT)
            .withHierarchies(List.of(HIERARCHY_STORE_SIZE_IN_SQFT))
            .build();

    public static final HierarchyMappingImpl HIERARCHY_STORE_TYPE = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(STORE_ID_COLUMN_IN_STORE)
        .withQuery(QUERY_TABLE_STORE)
        .withLevels(List.of(LEVEL_STORE_TYPE_WITHOUT_TABLE))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITH_QUERY_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HIERARCHY_STORE_TYPE))
            .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITHOUT_QUERY =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withLevels(List.of(LEVEL_STORE_TYPE_WITHOUT_TABLE))
                .build()))
            .build();

    public static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKeyTable(EMPLOYEE_TABLE)
                .withPrimaryKey(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
                .withQuery(JOIN_EMPLOYEE_STORE)
                .withLevels(List.of(LEVEL_STORE_TYPE))
                .build()))
            .build();

    public static final HierarchyMappingImpl HIERARCHY_TIME1 = HierarchyMappingImpl.builder()
        .withHasAll(false)
        .withPrimaryKey(TIME_ID_COLUMN_IN_TIME_BY_DAY)
        .withQuery(QUERY_TABLE_TIME_BY_DAY)
        .withLevels(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH))
        .build();

    public static final HierarchyMappingImpl HIERARCHY_TIME2 = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(TIME_ID_COLUMN_IN_TIME_BY_DAY)
        .withName("Weekly")
        .withQuery(QUERY_TABLE_TIME_BY_DAY)
        .withLevels(List.of(LEVEL_YEAR, LEVEL_WEEK, LEVEL_DAY))
        .build();

    public static final TimeDimensionMappingImpl DIMENSION_TIME = TimeDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_TIME)
        .withHierarchies(List.of(HIERARCHY_TIME1, HIERARCHY_TIME2))
        .build();

    public static final TimeDimensionMappingImpl DIMENSION_TIME_HR = TimeDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_TIME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey(THE_DATE_COLUMN_IN_TIME_BY_DAY)
            .withQuery(QUERY_TABLE_TIME_BY_DAY)
            .withLevels(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR))
            .build()))
        .build();

    public static final HierarchyMappingImpl HIERARCHY_PRODUCT = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(PRODUCT_ID_COLUMN_IN_PRODUCT)
        .withPrimaryKeyTable(PRODUCT_TABLE)
        .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
        .withLevels(List.of(LEVEL_productFamily, LEVEL_productDepartment, LEVEL_productCategory,
            LEVEL_productSubcategory, LEVEL_brandName, LEVEL_PRODUCT_NAME))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_PRODUCT = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PRODUCT)
        .withHierarchies(List.of(
            HIERARCHY_PRODUCT
        ))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_WAREHOUSE = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_WAREHOUSE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(WAREHOUSE_ID_COLUMN_IN_WAREHOUSE)
            .withQuery(warehouseTable)
            .withLevels(List.of(LEVEL_COUNTRY, LEVEL_stateProvince, LEVEL_CITY_TABLE_COLUMN_WAREHOUSE_CIT,
                LEVEL_WAREHOUSE_NAME))
            .build()))
        .build();

    public static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997 = TableQueryMappingImpl.builder()
        .withTable(SALES_FACT_1997_TABLE).build();

    public static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED =
        TableQueryMappingImpl.builder()
            .withTable(SALES_FACT_1997_TABLE)
            .withAggregationExcludes(List.of(
                AggregationExcludeMappingImpl.builder().withName("agg_c_special_sales_fact_1997").build(),
                AggregationExcludeMappingImpl.builder().withName("agg_lc_100_sales_fact_1997").build(),
                AggregationExcludeMappingImpl.builder().withName("agg_lc_10_sales_fact_1997").build(),
                AggregationExcludeMappingImpl.builder().withName("agg_pc_10_sales_fact_1997").build()
            ))
            .withAggregationTables(List.of(
                AggregationNameMappingImpl.builder()
                    .withName("agg_c_special_sales_fact_1997")
                    .withAggregationFactCount(
                        AggregationColumnNameMappingImpl.builder().withColumn(FACT_COUNT).build()
                    )
                    .withAggregationIgnoreColumns(List.of(
                        AggregationColumnNameMappingImpl.builder().withColumn(FOO).build(),
                        AggregationColumnNameMappingImpl.builder().withColumn(BAR).build()
                    ))
                    .withAggregationForeignKeys(List.of(
                        AggregationForeignKeyMappingImpl.builder().withFactColumn(TABLE_COLUMN_PRODUCT_ID).withAggregationColumn(
                            "PRODUCT_ID").build(),
                        AggregationForeignKeyMappingImpl.builder().withFactColumn(TABLE_COLUMN_CUSTOMER_ID).withAggregationColumn(
                            "CUSTOMER_ID").build(),
                        AggregationForeignKeyMappingImpl.builder().withFactColumn(TABLE_COLUMN_PROMOTION_ID).withAggregationColumn(
                            "PROMOTION_ID").build(),
                        AggregationForeignKeyMappingImpl.builder().withFactColumn(TABLE_COLUMN_STORE_ID).withAggregationColumn(
                            "STORE_ID").build()
                    ))
                    .withAggregationMeasures(List.of(
                        AggregationMeasureMappingImpl.builder().withName("[Measures].[Unit Sales]").withColumn(
                            "UNIT_SALES_SUM").build(),
                        AggregationMeasureMappingImpl.builder().withName("[Measures].[Store Cost]").withColumn(
                            "STORE_COST_SUM").build(),
                        AggregationMeasureMappingImpl.builder().withName("[Measures].[Store Sales]").withColumn(
                            "STORE_SALES_SUM").build()
                    ))
                    .withAggregationLevels(List.of(
                        AggregationLevelMappingImpl.builder().withName("[Time].[Year]").withColumn("TIME_YEAR").build(),
                        AggregationLevelMappingImpl.builder().withName("[Time].[Quarter]").withColumn("TIME_QUARTER").build(),
                        AggregationLevelMappingImpl.builder().withName("[Time].[Month]").withColumn("TIME_MONTH").build()
                    ))
                    .build()
            ))
            .build();

    public static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED_FOR_CUBE_SALES_RAGGED =
        TableQueryMappingImpl.builder()
            .withTable(SALES_FACT_1997_TABLE)
            .withAggregationExcludes(List.of(
                AggregationExcludeMappingImpl.builder().withName("agg_pc_10_sales_fact_1997").build(),
                AggregationExcludeMappingImpl.builder().withName("agg_lc_10_sales_fact_1997").build()
            ))
            .build();

    public static final HierarchyMappingImpl HIERARCHY_PROMOTION_MEDIA = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_MEDIA)
        .withPrimaryKey(PROMOTION_ID_COLUMN_IN_PROMOTION)
        .withDefaultMember(ALL_MEDIA)
        .withQuery(QUERY_TABLE_PROMOTION)
        .withLevels(List.of(LEVEL_MEDIA_TYPE))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_PROMOTION_MEDIA = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTION_MEDIA)
        .withHierarchies(List.of(HIERARCHY_PROMOTION_MEDIA))
        .build();

    public static final StandardDimensionMappingImpl promotionMedia1Dimension = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTION_MEDIA)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Media")
            .withPrimaryKey(PROMOTION_ID_COLUMN_IN_PROMOTION)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_MEDIA_TYPE))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_PROMOTIONS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_PROMOTIONS)
            .withPrimaryKey(PROMOTION_ID_COLUMN_IN_PROMOTION)
            .withDefaultMember(ALL_PROMOTIONS)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_PROMOTION_NAME))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl promotions1Dimension = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_PROMOTIONS)
            .withPrimaryKey(PROMOTION_ID_COLUMN_IN_PROMOTION)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_PROMOTION_NAME))
            .build()))
        .build();

    public static final HierarchyMappingImpl customersHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_CUSTOMERS)
        .withPrimaryKey(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE,
            LEVEL_CITY_TABLE_COLUMN_CITY, LEVEL_NAME))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_CUSTOMERS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_CUSTOMERS)
        .withHierarchies(List.of(customersHierarchy))
        .build();

    public static final HierarchyMappingImpl HIERARCHY_EDUCATION_LEVEL = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_EDUCATION))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_EDUCATION_LEVEL = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_EDUCATION_LEVEL)
        .withHierarchies(List.of(HIERARCHY_EDUCATION_LEVEL))
        .build();

    public static final HierarchyMappingImpl genderHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_GENDER)
        .withPrimaryKey(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_GENDER))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_GENDER = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_GENDER)
        .withHierarchies(List.of(genderHierarchy))
        .build();

    public static final HierarchyMappingImpl HIERARCHY_MARITAL_STATUS = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_MARITAL_STATUS)
        .withPrimaryKey(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_MARITAL_STATUS))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_MARITAL_STATUS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_MARITAL_STATUS)
        .withHierarchies(List.of(HIERARCHY_MARITAL_STATUS))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_YEARLY_INCOME = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_YEARLY_INCOME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(CUSTOMER_ID_COLUMN_IN_CUSTOMER)
            .withQuery(QUERY_TABLE_CUSTOMER)
            .withLevels(List.of(LEVEL_YEARLY_INCOME))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_HAS_COFFEE_BAR = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_HAS_COFFEE_BAR)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(LEVEL_HAS_COFFEE_BAR))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_PAY_TYPE = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PAY_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
            .withPrimaryKeyTable(EMPLOYEE_TABLE)
            .withQuery(JOIN_EMPLOYEE_POSITION)
            .withLevels(List.of(LEVEL_PAY_TYPE))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_POSITION = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_POSITION)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Position")
            .withPrimaryKey(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
            .withQuery(QUERY_TABLE_EMPLOYEE)
            .withLevels(List.of(LEVEL_MANAGEMENT_ROLE, LEVEL_POSITION_TITLE))
            .build()))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_DEPARTMENT = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_DEPARTMENT)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(DEPARTMENT_ID_COLUMN_IN_DEPARTMENT)
            .withQuery(QUERY_TABLE_DEPARTMENT)
            .withLevels(List.of(LEVEL_DEPARTAMENT_DESCRIPTION))
            .build()))
        .build();

    public static final HierarchyMappingImpl HIERARCHY_EMPLOYEES = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_EMPLOYEES)
        .withPrimaryKey(EMPLOYEE_ID_COLUMN_IN_EMPLOYEE)
        .withQuery(QUERY_TABLE_EMPLOYEE)
        .withLevels(List.of(LEVEL_EMPLOYEE_ID))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_EMPLOYEES = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_EMPLOYEES)
        .withHierarchies(List.of(HIERARCHY_EMPLOYEES))
        .build();

    public static final StandardDimensionMappingImpl DIMENSION_GEOGRAPHY = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_GEOGRAPHY)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID_COLUMN_IN_STORE_RAGGED)
            .withQuery(QUERY_TABLE_STORE_RAGGED)
            .withLevels(List.of(LEVEL_COUNTRY_WITH_NEVER, LEVEL_state, LEVEL_CITY_TABLE_COLUMN_STORE_CITY))
            .build()))
        .build();

    public static final MeasureMappingImpl MEASURE_UNIT_SALES = MeasureMappingImpl.builder()
        .withName(UNIT_SALES)
        .withColumn(UNIT_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STANDARD)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_UNIT_SALES_RAGGED = MeasureMappingImpl.builder()
        .withName(UNIT_SALES)
        .withColumn(UNIT_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STANDARD)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_UNIT_SALES_MEMBER_ORDINAL = MeasureMappingImpl.builder()
        .withName(UNIT_SALES)
        .withColumn(UNIT_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STANDARD)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("2").build()
        ))
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_COST = MeasureMappingImpl.builder()
        .withName(STORE_COST)
        .withColumn(STORE_COST_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_COST_RAGGED = MeasureMappingImpl.builder()
        .withName(STORE_COST)
        .withColumn(STORE_COST_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_COST_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName(STORE_COST)
        .withColumn(STORE_COST_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("6").build()
        ))
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_SALES = MeasureMappingImpl.builder()
        .withName(STORE_SALES)
        .withColumn(STORE_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_SALES_RAGGED = MeasureMappingImpl.builder()
        .withName(STORE_SALES)
        .withColumn(STORE_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_SALES_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName(STORE_SALES)
        .withColumn(STORE_SALES_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("3").build()
        ))
        .build();

    public static final MeasureMappingImpl MEASURE_SALES_COUNT = MeasureMappingImpl.builder()
        .withName(SALES_COUNT)
        .withColumn(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .build();

    public static final MeasureMappingImpl MEASURE_SALES_COUNT_RAGGED = MeasureMappingImpl.builder()
        .withName(SALES_COUNT)
        .withColumn(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .build();

    public static final MeasureMappingImpl MEASURE_SALES_COUNT_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName(SALES_COUNT)
        .withColumn(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("1").build()
        ))
        .build();

    public static final MeasureMappingImpl MEASURE_CUSTOMER_COUNT = MeasureMappingImpl.builder()
        .withName(CUSTOMER_COUNT)
        .withColumn(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .build();

    public static final MeasureMappingImpl MEASURE_CUSTOMER_COUNT_RAGGED = MeasureMappingImpl.builder()
        .withName(CUSTOMER_COUNT)
        .withColumn(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .build();

    public static final MeasureMappingImpl MEASURE_CUSTOMER_COUNT_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName(CUSTOMER_COUNT)
        .withColumn(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("7").build()
        ))
        .build();

    public static final MeasureMappingImpl MEASURE_PROMOTION_SALES = MeasureMappingImpl.builder()
        .withName("Promotion Sales")
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withMeasureExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(DIALECT_ACCESS))
                    .withStatement("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\"" +
                        ".\"store_sales\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ORACLE,
                        DIALECT_H2,
                        DIALECT_HSQLDB,
                        DIALECT_POSTGRES,
                        DIALECT_NEOVIEW,
                        DIALECT_DERBY,
                        DIALECT_LUCIDDB,
                        DIALECT_DB2,
                        NUODB,
                        DIALECT_SNOWFLAKE
                    ))
                    .withStatement("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else " +
                        "\"sales_fact_1997\".\"store_sales\" end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(DIALECT_INFOBRIGHT))
                    .withStatement("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`" +
                        ".`store_sales` end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(DIALECT_ACCESS))
                    .withStatement("`sales_fact_1997`.`store_sales`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(DIALECT_GENERIC))
                    .withStatement("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997" +
                        ".store_sales end)")
                    .build()
            ))
            .build())
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_INVOICE = MeasureMappingImpl.builder()
        .withName("Store Invoice")
        .withColumn(STORE_INVOICE_COLUMN_IN_INVENTORY_FACKT_1997)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_SUPPLY_TIME = MeasureMappingImpl.builder()
        .withName("Supply Time")
        .withColumn(SUPPLY_TIME_COLUMN_IN_INVENTORY_FACKT_1997)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_WAREHOUSE_COST = MeasureMappingImpl.builder()
        .withName("Warehouse Cost")
        .withColumn(WAREHOUSE_COST_COLUMN_IN_INVENTORY_FACKT_1997)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_WAREHOUSE_SALES = MeasureMappingImpl.builder()
        .withName("Warehouse Sales")
        .withColumn(WAREHOUSE_SALES_COLUMN_IN_INVENTORY_FACKT_1997)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_UNITS_SHIPPED = MeasureMappingImpl.builder()
        .withName("Units Shipped")
        .withColumn(UNITS_SHIPPED_COLUMN_IN_INVENTORY_FACKT_1997)
        .withFormatString("#.0")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_UNITS_ORDERED = MeasureMappingImpl.builder()
        .withName("Units Ordered")
        .withColumn(UNITS_ORDERED_COLUMN_IN_INVENTORY_FACKT_1997)
        .withFormatString("#.0")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_WAREHOUSE_PROFIT = MeasureMappingImpl.builder()
        .withName("Warehouse Profit")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withMeasureExpression(
            SQLExpressionMappingImpl.builder()
                .withSqls(List.of(
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            DIALECT_MYSQL,
                            DIALECT_MARIADB,
                            DIALECT_INFOBRIGHT
                        ))
                        .withStatement("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                        .build(),
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            DIALECT_GENERIC
                        ))
                        .withStatement("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;" +
                            "warehouse_cost&quot;")
                        .build()
                ))
                .build()
        )
        .build();

    public static final MeasureMappingImpl MEASURE_STORE_SQFT = MeasureMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(STORE_SQFT_COLUMN_IN_STORE)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_GROCERY_SQFT = MeasureMappingImpl.builder()
        .withName(GROCERY_SQFT)
        .withColumn(GROCERY_SQFT_COLUMN_IN_STORE)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_ORG_SALARY = MeasureMappingImpl.builder()
        .withName("Org Salary")
        .withColumn(SALARY_PAID_COLUMN_IN_SALARY)
        .withFormatString(CURRENCY)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    public static final MeasureMappingImpl MEASURE_COUNT = MeasureMappingImpl.builder()
        .withName("Count")
        .withColumn(EMPLOYEE_ID_COLUMN_IN_SALARY)
        .withFormatString("#,#")
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .build();

    public static final MeasureMappingImpl MEASURE_NUMBER_OF_EMPLOYEES = MeasureMappingImpl.builder()
        .withName("Number of Employees")
        .withColumn(EMPLOYEE_ID_COLUMN_IN_SALARY)
        .withFormatString("#,#")
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            MEASURE_UNIT_SALES,
            MEASURE_STORE_COST,
            MEASURE_STORE_SALES,
            MEASURE_SALES_COUNT,
            MEASURE_CUSTOMER_COUNT,
            MEASURE_PROMOTION_SALES
        ))
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_WAREHOUSE = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            MEASURE_STORE_INVOICE,
            MEASURE_SUPPLY_TIME,
            MEASURE_WAREHOUSE_COST,
            MEASURE_WAREHOUSE_SALES,
            MEASURE_UNITS_SHIPPED,
            MEASURE_UNITS_ORDERED,
            MEASURE_WAREHOUSE_PROFIT
        ))
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_STORE = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_STORE_SQFT, MEASURE_GROCERY_SQFT))
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_HR = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_ORG_SALARY, MEASURE_COUNT, MEASURE_NUMBER_OF_EMPLOYEES))
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES_RAGGED = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_UNIT_SALES_RAGGED, MEASURE_STORE_COST_RAGGED, MEASURE_STORE_SALES_RAGGED,
            MEASURE_SALES_COUNT_RAGGED,
            MEASURE_CUSTOMER_COUNT_RAGGED))
        .build();

    public static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES2 = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_SALES_COUNT_WITH_PROPERTY, MEASURE_UNIT_SALES_MEMBER_ORDINAL,
            MEASURE_STORE_SALES_WITH_PROPERTY, MEASURE_STORE_COST_WITH_PROPERTY,
            MEASURE_CUSTOMER_COUNT_WITH_PROPERTY))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_SALES = PhysicalCubeMappingImpl.builder()
        .withName("Sales")
        .withQuery(TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_SALES))
        .withDefaultMeasure(MEASURE_UNIT_SALES)
        .withDocumentation(new DocumentationMappingImpl(""))
        .withAnnotations(List.of(
            AnnotationMappingImpl.builder().withName("caption.de_DE").withValue("Verkaufen").build(),
            AnnotationMappingImpl.builder().withName("caption.fr_FR").withValue("Ventes").build(),
            AnnotationMappingImpl.builder().withName("description.fr_FR").withValue("Cube des ventes").build(),
            AnnotationMappingImpl.builder().withName("description.de").withValue("Cube Verkaufen").build(),
            AnnotationMappingImpl.builder().withName("description.de_AT").withValue("Cube den Verkaufen").build()
        ))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE)
            .withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT)
            .withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE)
            .withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME)
            .withForeignKey(TIME_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT)
            .withForeignKey(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTION_MEDIA).withDimension(DIMENSION_PROMOTION_MEDIA)
            .withForeignKey(PROMOTION_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTIONS).withDimension(DIMENSION_PROMOTIONS)
            .withForeignKey(PROMOTION_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_CUSTOMERS).withDimension(DIMENSION_CUSTOMERS)
            .withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EDUCATION_LEVEL).withDimension(DIMENSION_EDUCATION_LEVEL)
            .withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER)
            .withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_MARITAL_STATUS).withDimension(DIMENSION_MARITAL_STATUS)
            .withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME).withDimension(DIMENSION_YEARLY_INCOME)
            .withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build()
        ))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_LAST_PERIOD,
            CALCULATED_MEMBER_PROFIT_GROWTH))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_WAREHOUSE = PhysicalCubeMappingImpl.builder()
        .withName(NAME_CUBE_WAREHOUSE)
        .withQuery(QUERY_TABLE_inventoryFact1997)
        .withDocumentation(new DocumentationMappingImpl(""))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE).withForeignKey(STORE_ID_COLUMN_IN_INVENTORY_FACKT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT).withForeignKey(STORE_ID_COLUMN_IN_INVENTORY_FACKT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE).withForeignKey(STORE_ID_COLUMN_IN_INVENTORY_FACKT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TIME_ID_COLUMN_IN_INVENTORY_FACKT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(PRODUCT_ID_COLUMN_IN_INVENTORY_FACKT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_WAREHOUSE).withDimension(DIMENSION_WAREHOUSE).withForeignKey(WAREHOUSE_ID_COLUMN_IN_INVENTORY_FACKT_1997).build()
        ))
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_WAREHOUSE))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE))
        .withNamedSets(List.of(
            topSellersNamedSet
        ))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_STORE = PhysicalCubeMappingImpl.builder()
        .withName(NAME_CUBE_STORE)
        .withQuery(QUERY_TABLE_STORE)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_STORE))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITHOUT_QUERY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_HAS_COFFEE_BAR).withDimension(DIMENSION_HAS_COFFEE_BAR).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_HR = PhysicalCubeMappingImpl.builder()
        .withName("HR")
        .withQuery(QUERY_TABLE_SALARY)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_HR))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME_HR).withForeignKey(PAY_DATE_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE).withForeignKey(EMPLOYEE_ID_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PAY_TYPE).withDimension(DIMENSION_PAY_TYPE).withForeignKey(EMPLOYEE_ID_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE).withForeignKey(EMPLOYEE_ID_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_POSITION).withDimension(DIMENSION_POSITION).withForeignKey(EMPLOYEE_ID_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_DEPARTMENT).withDimension(DIMENSION_DEPARTMENT).withForeignKey(DEPARTMENT_ID_COLUMN_IN_SALARY).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EMPLOYEES).withDimension(DIMENSION_EMPLOYEES).withForeignKey(EMPLOYEE_ID_COLUMN_IN_SALARY).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_EMPLOEE_SALARY, CALCULATED_MEMBER_AVG_SALARY))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_SALES_RAGGED = PhysicalCubeMappingImpl.builder()
        .withName("Sales Ragged")
        .withQuery(TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED_FOR_CUBE_SALES_RAGGED)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_SALES_RAGGED))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE_RAGGED).withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GEOGRAPHY).withDimension(DIMENSION_GEOGRAPHY).withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT).withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE).withForeignKey(STORE_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TIME_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTION_MEDIA).withDimension(promotionMedia1Dimension).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTIONS).withDimension(promotions1Dimension).withForeignKey(PROMOTION_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_CUSTOMERS).withDimension(DIMENSION_CUSTOMERS).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EDUCATION_LEVEL).withDimension(DIMENSION_EDUCATION_LEVEL).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),

            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_MARITAL_STATUS).withDimension(DIMENSION_MARITAL_STATUS).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME).withDimension(DIMENSION_YEARLY_INCOME).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    public static final PhysicalCubeMappingImpl CUBE_SALES_2 = PhysicalCubeMappingImpl.builder()
        .withName("Sales 2")
        .withQuery(TABLE_QUERY_FACT_SALES_1997)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_SALES2))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TIME_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(PRODUCT_ID_COLUMN_IN_SALES_FACT_1997).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER).withForeignKey(CUSTOMER_ID_COLUMN_IN_SALES_FACT_1997).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_PROFIT_WITH_ORDER,
            CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2))
        .build();

    public static final VirtualCubeMappingImpl CUBE_VIRTIAL_WAREHOUSE_AND_SALES = VirtualCubeMappingImpl.builder()
        .withName("Warehouse and Sales")
        .withDefaultMeasure(MEASURE_STORE_SALES)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_CUSTOMERS)
                .withOverrideDimensionName(NAME_DIMENSION_CUSTOMERS)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_EDUCATION_LEVEL)
                .withOverrideDimensionName(NAME_DIMENSION_EDUCATION_LEVEL)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_GENDER)
                .withOverrideDimensionName(NAME_DIMENSION_GENDER)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_MARITAL_STATUS)
                .withOverrideDimensionName(NAME_DIMENSION_MARITAL_STATUS)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_PRODUCT)
                .withOverrideDimensionName(NAME_DIMENSION_PRODUCT)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_PROMOTION_MEDIA)
                .withOverrideDimensionName(NAME_DIMENSION_PROMOTION_MEDIA)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_PROMOTIONS)
                .withOverrideDimensionName(NAME_DIMENSION_PROMOTIONS)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_STORE_WITH_QUERY_STORE)
                .withOverrideDimensionName(NAME_DIMENSION_STORE)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_TIME)
                .withOverrideDimensionName(NAME_DIMENSION_TIME)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_YEARLY_INCOME)
                .withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_WAREHOUSE)
                .withOverrideDimensionName(NAME_DIMENSION_WAREHOUSE)
                .withPhysicalCube(CUBE_WAREHOUSE)
                .build()
        ))
        .withReferencedMeasures(List.of(
            MEASURE_SALES_COUNT,
            MEASURE_STORE_COST,
            MEASURE_STORE_SALES,
            MEASURE_UNIT_SALES,
            MEASURE_STORE_INVOICE,
            MEASURE_SUPPLY_TIME,
            MEASURE_UNITS_ORDERED,
            MEASURE_UNITS_SHIPPED,
            MEASURE_WAREHOUSE_COST,
            MEASURE_WAREHOUSE_PROFIT,
            MEASURE_WAREHOUSE_SALES
        ))
        .withReferencedCalculatedMembers(List.of(
            CALCULATED_MEMBER_PROFIT,
            CALCULATED_MEMBER_PROFIT_GROWTH,
            CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE
        ))
        .withCalculatedMembers(List.of(
            profitPerUnitShippedCalculatedMember
        ))
        .build();

    public static final AccessRoleMappingImpl californiaManagerRole = AccessRoleMappingImpl.builder()
        .withName("California manager")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchema.NONE)
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(CUBE_SALES)
                        .withAccess(AccessCube.ALL)
                        .withHierarchyGrants(List.of(
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(storeHierarchy)
                                .withAccess(AccessHierarchy.CUSTOM)
                                .withTopLevel(LEVEL_STORE_COUNTRY)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA]")
                                        .withAccess(AccessMember.ALL)
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA].[Los Angeles]")
                                        .withAccess(AccessMember.NONE)
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(customersHierarchy)
                                .withAccess(AccessHierarchy.CUSTOM)
                                .withTopLevel(LEVEL_stateProvince)
                                .withBottomLevel(LEVEL_CITY_TABLE_COLUMN_WAREHOUSE_CIT)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA]")
                                        .withAccess(AccessMember.ALL)
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA].[Los Angeles]")
                                        .withAccess(AccessMember.NONE)
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(genderHierarchy)
                                .withAccess(AccessHierarchy.NONE)
                                .build()
                        ))
                        .build()
                ))
                .build()
        ))
        .build();

    public static final AccessRoleMappingImpl noHRCubeRole = AccessRoleMappingImpl.builder()
        .withName("No HR Cube")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchema.ALL)
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(CUBE_HR)
                        .withAccess(AccessCube.NONE)
                        .build()
                ))
                .build()
        ))
        .build();

    public static final AccessRoleMappingImpl administratorRole = AccessRoleMappingImpl.builder()
        .withName("Administrator")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchema.ALL)
                .build()
        ))
        .build();

    public static final SchemaMappingImpl SCHEMA = SchemaMappingImpl.builder()
        .withName(SCHEMA_NAME)
        .withCubes(List.of(CUBE_SALES, CUBE_WAREHOUSE, CUBE_STORE, CUBE_HR, CUBE_SALES_RAGGED, CUBE_SALES_2,
            CUBE_VIRTIAL_WAREHOUSE_AND_SALES))
        .withAccessRoles(List.of(
            californiaManagerRole,
            noHRCubeRole,
            administratorRole
        ))
        .build();

    @Override
    public CatalogMapping get() {
        return CatalogMappingImpl.builder()
            .withName(CATALOG_NAME)
            .withDocumentation(documentation)
            .withSchemas(List.of(SCHEMA))
            .withDbschemas(List.of(DATABASE_SCHEMA))
            .build();
    }

}
