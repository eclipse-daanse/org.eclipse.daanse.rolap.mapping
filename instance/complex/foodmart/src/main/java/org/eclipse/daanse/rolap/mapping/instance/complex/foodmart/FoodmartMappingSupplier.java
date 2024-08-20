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

    private static final String EMPLOYEE = "employee";

    private static final String TABLE_PRODUCT_CLASS = "product_class";

    private static final String PRODUCT_CLASS_ID = "product_class_id";

    private static final String TABLE_PRODUCT = "product";

    private static final String TABLE_STORE = "store";

    private static final String SCHEMA_NAME = "FoodMart";

    private static final String CATALOG_NAME = SCHEMA_NAME;

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    private static final TableQueryMappingImpl QUERY_TABLE_STORE =
        TableQueryMappingImpl.builder().withName(TABLE_STORE).build();
    private static final TableQueryMappingImpl QUERY_TABLE_TIME_BY_DAY = TableQueryMappingImpl.builder().withName(
        "time_by_day"
    ).build();
    private static final TableQueryMappingImpl QUERY_TABLE_PRODUCT =
        TableQueryMappingImpl.builder().withName(TABLE_PRODUCT).build();
    private static final TableQueryMappingImpl QUERY_TABLE_PRODUCT_CLASS = TableQueryMappingImpl.builder().withName(
        TABLE_PRODUCT_CLASS).build();
    private static final TableQueryMappingImpl QUERY_TABLE_EMPLOYEE = TableQueryMappingImpl.builder().withName(
        EMPLOYEE).build();
    private static final TableQueryMappingImpl QUERY_TABLE_POSITION = TableQueryMappingImpl.builder().withName(
        TABLE_NAME_POSITION).build();
    private static final TableQueryMappingImpl QUERY_TABLE_SALARY = TableQueryMappingImpl.builder().withName(
        SALARY).build();
    private static final TableQueryMappingImpl QUERY_TABLE_EMPLOYEE_CLOSURE = TableQueryMappingImpl.builder().withName(
        EMPLOYEE_CLOSURE).build();
    private static final TableQueryMappingImpl QUERY_TABLE_STORE_RAGGED = TableQueryMappingImpl.builder().withName(
        STORE_RAGGED).build();

    private static final JoinQueryMappingImpl JOIN_PRODUCT_PRODUCT_CLASS = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID)
            .withQuery(QUERY_TABLE_PRODUCT).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID)
            .withQuery(QUERY_TABLE_PRODUCT_CLASS).build()).build();
    private static final JoinQueryMappingImpl JOIN_EMPLOYEE_STORE = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(TABLE_COLUMN_STORE_ID)
            .withQuery(QUERY_TABLE_EMPLOYEE).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(TABLE_COLUMN_STORE_ID)
            .withQuery(QUERY_TABLE_STORE).build()).build();
    private static final JoinQueryMappingImpl JOIN_EMPLOYEE_POSITION = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(TABLE_COLUMN_POSITION_ID)
            .withQuery(QUERY_TABLE_EMPLOYEE).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(TABLE_COLUMN_POSITION_ID)
            .withQuery(QUERY_TABLE_POSITION).build()).build();
    private static final TableQueryMappingImpl warehouseTable =
        TableQueryMappingImpl.builder().withName("warehouse").build();
    private static final TableQueryMappingImpl QUERY_TABLE_PROMOTION =
        TableQueryMappingImpl.builder().withName("promotion").build();
    private static final TableQueryMappingImpl QUERY_TABLE_CUSTOMER =
        TableQueryMappingImpl.builder().withName("customer").build();
    private static final TableQueryMappingImpl QUERY_TABLE_inventoryFact1997 =
        TableQueryMappingImpl.builder().withName("inventory_fact_1997").build();

    private static final LevelMappingImpl LEVEL_STORE_COUNTRY = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(TABLE_COLUMN_STORE_COUNTRY)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_COUNTRY_WITH_NEVER = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(TABLE_COLUMN_STORE_COUNTRY)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_COUNTRY_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(TABLE_COLUMN_STORE_COUNTRY)
        .withTable(TABLE_STORE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY_UNIQUE_MEMBERS_TRUE = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(TABLE_COLUMN_STORE_STATE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY_IF_PARENTS_NAME = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(TABLE_COLUMN_STORE_STATE)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.IF_PARENTS_NAME)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withTable(TABLE_STORE)
        .withColumn(TABLE_COLUMN_STORE_STATE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(TABLE_COLUMN_STORE_CITY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY_IF_BLANK_NAME = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(TABLE_COLUMN_STORE_CITY)
        .withUniqueMembers(false)
        .withHideMemberIfType(HideMemberIfType.IF_BLANK_NAME)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_CYTY_WITH_TABLE_COLUMN_STORE_CITY = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withTable(TABLE_STORE)
        .withColumn(TABLE_COLUMN_STORE_CITY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_NAME_WITHOUT_TABLE = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(TABLE_COLUMN_STORE_NAME)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(TABLE_COLUMN_STORE_TYPE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(TABLE_COLUMN_STORE_MANAGER).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(TABLE_COLUMN_STORE_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(TABLE_COLUMN_GROCERY_SQFT).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(TABLE_COLUMN_FROZEN_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(TABLE_COLUMN_MEAT_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(TABLE_COLUMN_COFFEE_BAR).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withDataType(
                DataType.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(TABLE_COLUMN_STORE_NAME)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(TABLE_COLUMN_STORE_TYPE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(TABLE_COLUMN_STORE_MANAGER).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(TABLE_COLUMN_STORE_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(TABLE_COLUMN_GROCERY_SQFT).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(TABLE_COLUMN_FROZEN_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(TABLE_COLUMN_MEAT_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(TABLE_COLUMN_COFFEE_BAR).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withDataType(
                DataType.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl LEVEL_STORE_NAME_WITH_TABLE = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withTable(TABLE_STORE)
        .withColumn(TABLE_COLUMN_STORE_NAME)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_STORE_TYPE).withColumn(TABLE_COLUMN_STORE_TYPE).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(TABLE_COLUMN_STORE_MANAGER).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(TABLE_COLUMN_STORE_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(TABLE_COLUMN_GROCERY_SQFT).withDataType(DataType.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(TABLE_COLUMN_FROZEN_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(TABLE_COLUMN_MEAT_SQFT).withDataType(DataType.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_HAS_COFFEE_BAR).withColumn(TABLE_COLUMN_COFFEE_BAR).withDataType(DataType.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withDataType(
                DataType.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl LEVEL_STORE_SQFT = LevelMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(TABLE_COLUMN_STORE_SQFT)
        .withUniqueMembers(true)
        .withType(DataType.NUMERIC)
        .build();

    private static final LevelMappingImpl LEVEL_YEAR = LevelMappingImpl.builder()
        .withName(YEAR)
        .withColumn(TABLE_COLUMN_THE_YEAR)
        .withUniqueMembers(true)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_YEARS)
        .build();

    private static final LevelMappingImpl LEVEL_QUARTER = LevelMappingImpl.builder()
        .withName(QUARTER)
        .withColumn(TABLE_COLUMN_QUARTER)
        .withUniqueMembers(false)
        .withLevelType(LevelType.TIME_QUARTERS)
        .build();

    private static final LevelMappingImpl LEVEL_MONTH = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(TABLE_COLUMN_MONTH_OF_YEAR)
        .withUniqueMembers(false)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_MONTHS)
        .build();

    private static final LevelMappingImpl LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(TABLE_COLUMN_MONTH_OF_YEAR)
        .withNameColumn(TABLE_COLUMN_THE_MONTH)
        .withUniqueMembers(false)
        .withType(DataType.NUMERIC)
        .withLevelType(LevelType.TIME_MONTHS)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_TYPE_WITHOUT_TABLE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_STORE_TYPE)
        .withColumn(TABLE_COLUMN_STORE_TYPE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STORE_TYPE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_STORE_TYPE)
        .withTable(TABLE_STORE)
        .withColumn(TABLE_COLUMN_STORE_TYPE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_productFamily = LevelMappingImpl.builder()
        .withName(PRODUCT_FAMILY)
        .withTable(TABLE_PRODUCT_CLASS)
        .withColumn(TABLE_COLUMN_PRODUCT_FAMILY)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_productDepartment = LevelMappingImpl.builder()
        .withName(PRODUCT_DEPARTMENT)
        .withTable(TABLE_PRODUCT_CLASS)
        .withColumn(TABLE_COLUMN_PRODUCT_DEPARTMENT)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_productCategory = LevelMappingImpl.builder()
        .withName(PRODUCT_CATEGORY)
        .withTable(TABLE_PRODUCT_CLASS)
        .withColumn(TABLE_COLUMN_PRODUCT_CATEGORY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_productSubcategory = LevelMappingImpl.builder()
        .withName(PRODUCT_SUBCATEGORY)
        .withTable(TABLE_PRODUCT_CLASS)
        .withColumn(TABLE_COLUMN_PRODUCT_SUBCATEGORY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_brandName = LevelMappingImpl.builder()
        .withName(BRAND_NAME)
        .withTable(TABLE_PRODUCT)
        .withColumn(TABLE_COLUMN_BRAND_NAME)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_PRODUCT_NAME = LevelMappingImpl.builder()
        .withName(PRODUCT_NAME)
        .withTable(TABLE_PRODUCT)
        .withColumn(TABLE_COLUMN_PRODUCT_NAME)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_COUNTRY = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(TABLE_COLUMN_WAREHOUSE_COUNTRY)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_COUNTRY_WITH_NEVER = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(TABLE_COLUMN_STORE_COUNTRY)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.NEVER)
        .build();

    private static final LevelMappingImpl LEVEL_state = LevelMappingImpl.builder()
        .withName(STATE)
        .withColumn(TABLE_COLUMN_STORE_STATE)
        .withUniqueMembers(true)
        .withHideMemberIfType(HideMemberIfType.IF_PARENTS_NAME)
        .build();

    private static final LevelMappingImpl LEVEL_stateProvince = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(TABLE_COLUMN_WAREHOUSE_STATE_PROVINCE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_WAREHOUSE_CIT = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(TABLE_COLUMN_WAREHOUSE_CITY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_STORE_CITY = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(TABLE_COLUMN_STORE_CITY)
        .withUniqueMembers(false)
        .withHideMemberIfType(HideMemberIfType.IF_BLANK_NAME)
        .build();

    private static final LevelMappingImpl LEVEL_WAREHOUSE_NAME = LevelMappingImpl.builder()
        .withName(WAREHOUSE_NAME)
        .withColumn(TABLE_COLUMN_WAREHOUSE_NAME)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_MEDIA_TYPE = LevelMappingImpl.builder()
        .withName(MEDIA_TYPE)
        .withColumn(TABLE_COLUMN_MEDIA_TYPE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_PROMOTION_NAME = LevelMappingImpl.builder()
        .withName(PROMOTION_NAME)
        .withColumn(TABLE_COLUMN_PROMOTION_NAME)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(TABLE_COLUMN_COUNTRY)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(TABLE_COLUMN_STATE_PROVINCE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_CITY_TABLE_COLUMN_CITY = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(TABLE_COLUMN_CITY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_NAME = LevelMappingImpl.builder()
        .withName(NAME)
        .withColumn(TABLE_COLUMN_CUSTOMER_ID)
        .withType(DataType.NUMERIC)
        .withUniqueMembers(true)
        .withNameExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DIALECT_ORACLE,
                        DIALECT_H2,
                        DIALECT_HSQLDB,
                        DIALECT_ORACLE,
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
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_GENDER).withColumn(TABLE_COLUMN_GENDER).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_MARITAL_STATUS).withColumn(TABLE_COLUMN_MARITAL_STATUS).build(),
            MemberPropertyMappingImpl.builder().withName("Education").withColumn("education").build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_YEARLY_INCOME).withColumn("yearly_income").build()
        ))
        .build();

    private static final LevelMappingImpl LEVEL_EDUCATION = LevelMappingImpl.builder()
        .withName(NAME_LEVEL_EDUCATION_LEVEL)
        .withColumn("education")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_GENDER = LevelMappingImpl.builder()
        .withName(NAME_LEVEL_GENDER)
        .withColumn(TABLE_COLUMN_GENDER)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_MARITAL_STATUS = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_MARITAL_STATUS)
        .withColumn(TABLE_COLUMN_MARITAL_STATUS)
        .withUniqueMembers(true)
        .withApproxRowCount("111")
        .build();

    private static final LevelMappingImpl LEVEL_YEARLY_INCOME = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_YEARLY_INCOME)
        .withColumn("yearly_income")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_HAS_COFFEE_BAR = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_HAS_COFFEE_BAR)
        .withColumn(TABLE_COLUMN_COFFEE_BAR)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_PAY_TYPE = LevelMappingImpl.builder()
        .withName(NAME_DIMENSION_PAY_TYPE)
        .withColumn("pay_type")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_MANAGEMENT_ROLE = LevelMappingImpl.builder()
        .withName("Management Rol")
        .withColumn("management_role")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_POSITION_TITLE = LevelMappingImpl.builder()
        .withName("Position Title")
        .withColumn("position_title")
        .withOrdinalColumn(TABLE_COLUMN_POSITION_ID)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl LEVEL_DEPARTAMENT_DESCRIPTION = LevelMappingImpl.builder()
        .withName("Department Description")
        .withType(DataType.NUMERIC)
        .withColumn(TABLE_COLUMN_DEPARTMENT_ID)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl LEVEL_EMPLOYEE_ID = LevelMappingImpl.builder()
        .withName("Employee Id")
        .withType(DataType.NUMERIC)
        .withColumn(TABLE_COLUMN_EMPLOYEE_ID)
        .withParentColumn("supervisor_id")
        .withNullParentValue("0")
        .withParentChildLink(
            ParentChildLinkMappingImpl.builder()
                .withParentColumn("supervisor_id")
                .withChildColumn(TABLE_COLUMN_EMPLOYEE_ID)
                .withTable(QUERY_TABLE_EMPLOYEE_CLOSURE)
                .build())
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_MARITAL_STATUS).withColumn(TABLE_COLUMN_MARITAL_STATUS).build(),
            MemberPropertyMappingImpl.builder().withName("Position Title").withColumn("position_title").build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_GENDER).withColumn(TABLE_COLUMN_GENDER).build(),
            MemberPropertyMappingImpl.builder().withName("Salary").withColumn(SALARY).build(),
            MemberPropertyMappingImpl.builder().withName(NAME_DIMENSION_EDUCATION_LEVEL).withColumn("education_level").build(),
            MemberPropertyMappingImpl.builder().withName("Management Role").withColumn("management_role").build()
        ))
        .build();

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT = CalculatedMemberMappingImpl.builder()
        .withName("Profit")
        .withFormula("[Measures].[Store Sales] - [Measures].[Store Cost]")
        .withCalculatedMemberProperties(List.of(
            CalculatedMemberPropertyMappingImpl.builder()
                .withName(FORMAT_STRING)
                .withValue(FORMAT_STRING_CURRENCY)
                .build()
        ))
        .build();

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_WITH_ORDER =
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

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_LAST_PERIOD =
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

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2 =
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

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_PROFIT_GROWINN =
        CalculatedMemberMappingImpl.builder()
            //.withName("Profit Growth")
            .withName("Gewinn-Wachstum")
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

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE =
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

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_EMPLOEE_SALARY =
        CalculatedMemberMappingImpl.builder()
            .withName("Employee Salary")
            .withFormatString(CURRENCY)
            .withFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])")
            .build();

    private static final CalculatedMemberMappingImpl CALCULATED_MEMBER_AVG_SALARY =
        CalculatedMemberMappingImpl.builder()
            .withName("Avg Salary")
            .withFormatString(CURRENCY)
            .withFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]")
            .build();

    private static final CalculatedMemberMappingImpl profitPerUnitShippedCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit Per Unit Shipped")
            .withFormatString(CURRENCY)
            .withFormula("[Measures].[Profit] / [Measures].[Units Shipped]")
            .build();

    private static final NamedSetMappingImpl topSellersNamedSet = NamedSetMappingImpl.builder()
        .withName("Top Sellers")
        .withFormula("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])")
        .build();

    private static final HierarchyMappingImpl storeHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey(TABLE_COLUMN_STORE_SQFT)
        .withQuery(QUERY_TABLE_STORE)
        .withLevels(List.of(LEVEL_STORE_COUNTRY, LEVEL_STORE_CYTY_UNIQUE_MEMBERS_TRUE, LEVEL_STORE_CYTY,
            LEVEL_STORE_NAME_WITHOUT_TABLE))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(storeHierarchy))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_STORE_RAGGED =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(TABLE_COLUMN_STORE_ID)
                .withQuery(QUERY_TABLE_STORE_RAGGED)
                .withLevels(List.of(LEVEL_STORE_COUNTRY_WITH_NEVER, LEVEL_STORE_CYTY_IF_PARENTS_NAME,
                    LEVEL_STORE_CYTY_IF_BLANK_NAME, LEVEL_STORE_NAME_WITHOUT_TABLE_WITH_NEVER))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(TABLE_COLUMN_EMPLOYEE_ID)
                .withQuery(JOIN_EMPLOYEE_STORE)
                .withLevels(List.of(LEVEL_STORE_COUNTRY_WITH_TABLE, LEVEL_STORE_CYTY_WITH_TABLE,
                    LEVEL_STORE_CYTY_WITH_TABLE_COLUMN_STORE_CITY, LEVEL_STORE_NAME_WITH_TABLE))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_SIZE_IN_SQFT =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_SIZE_IN_SQFT)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(TABLE_COLUMN_STORE_ID)
                .withQuery(QUERY_TABLE_STORE)
                .withLevels(List.of(LEVEL_STORE_SQFT))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITH_QUERY_STORE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(TABLE_COLUMN_STORE_ID)
                .withQuery(QUERY_TABLE_STORE)
                .withLevels(List.of(LEVEL_STORE_TYPE_WITHOUT_TABLE))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITHOUT_QUERY =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withLevels(List.of(LEVEL_STORE_TYPE_WITHOUT_TABLE))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_STORE_TYPE)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKeyTable(EMPLOYEE)
                .withPrimaryKey(TABLE_COLUMN_EMPLOYEE_ID)
                .withQuery(JOIN_EMPLOYEE_STORE)
                .withLevels(List.of(LEVEL_STORE_TYPE))
                .build()))
            .build();

    private static final TimeDimensionMappingImpl DIMENSION_TIME = TimeDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_TIME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey(TABLE_COLUMN_TIME_ID)
            .withQuery(QUERY_TABLE_TIME_BY_DAY)
            .withLevels(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH))
            .build()))
        .build();

    private static final TimeDimensionMappingImpl DIMENSION_TIME_HR = TimeDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_TIME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey(TABLE_COLUMN_THE_DATE)
            .withQuery(QUERY_TABLE_TIME_BY_DAY)
            .withLevels(List.of(LEVEL_YEAR, LEVEL_QUARTER, LEVEL_MONTH_WITH_NAME_COLUMN_IN_CUBE_HR))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_PRODUCT = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PRODUCT)
        .withHierarchies(List.of(
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_productFamily, LEVEL_productDepartment, LEVEL_productCategory,
                    LEVEL_productSubcategory, LEVEL_brandName, LEVEL_PRODUCT_NAME))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_FAMILY)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_productFamily))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_DEPARTMENT)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_productDepartment))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_CATEGORY)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_productCategory))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_SUBCATEGORY)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_productSubcategory))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(BRAND_NAME)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_brandName))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_NAME)
                .withPrimaryKey(TABLE_COLUMN_PRODUCT_ID)
                .withPrimaryKeyTable(TABLE_PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(JOIN_PRODUCT_PRODUCT_CLASS)
                .withLevels(List.of(LEVEL_PRODUCT_NAME))
                .build()
        ))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_WAREHOUSE = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_WAREHOUSE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_WAREHOUSE_ID)
            .withQuery(warehouseTable)
            .withLevels(List.of(LEVEL_COUNTRY, LEVEL_stateProvince, LEVEL_CITY_TABLE_COLUMN_WAREHOUSE_CIT,
                LEVEL_WAREHOUSE_NAME))
            .build()))
        .build();

    private static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997 = TableQueryMappingImpl.builder()
        .withName(SALES_FACT_1997).build();

    private static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED =
        TableQueryMappingImpl.builder()
            .withName(SALES_FACT_1997)
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

    private static final TableQueryMappingImpl TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED_FOR_CUBE_SALES_RAGGED =
        TableQueryMappingImpl.builder()
            .withName(SALES_FACT_1997)
            .withAggregationExcludes(List.of(
                AggregationExcludeMappingImpl.builder().withName("agg_pc_10_sales_fact_1997").build(),
                AggregationExcludeMappingImpl.builder().withName("agg_lc_10_sales_fact_1997").build()
            ))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_PROMOTION_MEDIA = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTION_MEDIA)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MEDIA)
            .withPrimaryKey(TABLE_COLUMN_PROMOTION_ID)
            .withDefaultMember(ALL_MEDIA)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_MEDIA_TYPE))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl promotionMedia1Dimension = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTION_MEDIA)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_PROMOTION_ID)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_MEDIA_TYPE))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_PROMOTIONS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_PROMOTIONS)
            .withPrimaryKey(TABLE_COLUMN_PROMOTION_ID)
            .withDefaultMember(ALL_PROMOTIONS)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_PROMOTION_NAME))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl promotions1Dimension = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_PROMOTIONS)
            .withPrimaryKey(TABLE_COLUMN_PROMOTION_ID)
            .withQuery(QUERY_TABLE_PROMOTION)
            .withLevels(List.of(LEVEL_PROMOTION_NAME))
            .build()))
        .build();

    private static final HierarchyMappingImpl customersHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_CUSTOMERS)
        .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_COUNTRY_TABLE_COLUMN_COUNTRY, LEVEL_STATE_PROVINCE_TABLE_COLUMN_STATE_PROVINCE,
            LEVEL_CITY_TABLE_COLUMN_CITY, LEVEL_NAME))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_CUSTOMERS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_CUSTOMERS)
        .withHierarchies(List.of())
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_EDUCATION_LEVEL = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_EDUCATION_LEVEL)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
            .withQuery(QUERY_TABLE_CUSTOMER)
            .withLevels(List.of(LEVEL_EDUCATION))
            .build()))
        .build();

    private static final HierarchyMappingImpl genderHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_GENDER)
        .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
        .withQuery(QUERY_TABLE_CUSTOMER)
        .withLevels(List.of(LEVEL_GENDER))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_GENDER = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_GENDER)
        .withHierarchies(List.of(genderHierarchy))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_MARITAL_STATUS = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_MARITAL_STATUS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MARITAL_STATUS)
            .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
            .withQuery(QUERY_TABLE_CUSTOMER)
            .withLevels(List.of(LEVEL_MARITAL_STATUS))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_YEARLY_INCOME_WITH_ALL_MEMBER_NAME =
        StandardDimensionMappingImpl.builder()
            .withName(NAME_DIMENSION_YEARLY_INCOME)
            .withHierarchies(List.of(HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withAllMemberName(ALL_MARITAL_STATUS)
                .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
                .withQuery(QUERY_TABLE_CUSTOMER)
                .withLevels(List.of(LEVEL_YEARLY_INCOME))
                .build()))
            .build();

    private static final StandardDimensionMappingImpl DIMENSION_YEARLY_INCOME = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_YEARLY_INCOME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_CUSTOMER_ID)
            .withQuery(QUERY_TABLE_CUSTOMER)
            .withLevels(List.of(LEVEL_YEARLY_INCOME))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_HAS_COFFEE_BAR = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_HAS_COFFEE_BAR)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(LEVEL_HAS_COFFEE_BAR))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_PAY_TYPE = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_PAY_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_EMPLOYEE_ID)
            .withPrimaryKeyTable(EMPLOYEE)
            .withQuery(JOIN_EMPLOYEE_POSITION)
            .withLevels(List.of(LEVEL_PAY_TYPE))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_POSITION = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_POSITION)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_EMPLOYEE_ID)
            .withPrimaryKeyTable(EMPLOYEE)
            .withQuery(QUERY_TABLE_EMPLOYEE)
            .withLevels(List.of(LEVEL_MANAGEMENT_ROLE, LEVEL_POSITION_TITLE))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_DEPARTMENT = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_DEPARTMENT)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_DEPARTMENT_ID)
            .withQuery(QUERY_TABLE_EMPLOYEE)
            .withLevels(List.of(LEVEL_DEPARTAMENT_DESCRIPTION))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_EMPLOYEES = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_EMPLOYEES)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_EMPLOYEES)
            .withPrimaryKey(TABLE_COLUMN_EMPLOYEE_ID)
            .withQuery(QUERY_TABLE_EMPLOYEE)
            .withLevels(List.of(LEVEL_EMPLOYEE_ID))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_GEOGRAPHY = StandardDimensionMappingImpl.builder()
        .withName(NAME_DIMENSION_GEOGRAPHY)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(TABLE_COLUMN_STORE_ID)
            .withQuery(QUERY_TABLE_STORE_RAGGED)
            .withLevels(List.of(LEVEL_COUNTRY_WITH_NEVER, LEVEL_state, LEVEL_CITY_TABLE_COLUMN_STORE_CITY))
            .build()))
        .build();

    private static final MeasureMappingImpl MEASURE_UNIT_SALES = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_UNIT_SALES_MEMBER_ORDINAL = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("2").build()
        ))
        .build();

    private static final MeasureMappingImpl MEASURE_STORE_COST = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_STORE_COST_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("6").build()
        ))
        .build();

    private static final MeasureMappingImpl MEASURE_STORE_SALES = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_STORE_SALES_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString(FORMAT_STRING_WITH_COMMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("3").build()
        ))
        .build();

    private static final MeasureMappingImpl MEASURE_SALES_COUNT = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn(TABLE_COLUMN_PRODUCT_ID)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .build();

    private static final MeasureMappingImpl MEASURE_SALES_COUNT_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn(TABLE_COLUMN_PRODUCT_ID)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("1").build()
        ))
        .build();

    private static final MeasureMappingImpl MEASURE_CUSTOMER_COUNT = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn(TABLE_COLUMN_CUSTOMER_ID)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .build();

    private static final MeasureMappingImpl MEASURE_CUSTOMER_COUNT_WITH_PROPERTY = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn(TABLE_COLUMN_CUSTOMER_ID)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("7").build()
        ))
        .build();

    private static final MeasureMappingImpl MEASURE_PROMOTION_SALES = MeasureMappingImpl.builder()
        .withName("Promotion Sales")
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
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

    private static final MeasureMappingImpl MEASURE_STORE_INVOICE = MeasureMappingImpl.builder()
        .withName("Store Invoice")
        .withColumn("store_invoice")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_SUPPLY_TIME = MeasureMappingImpl.builder()
        .withName("Supply Time")
        .withColumn("supply_time")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_WAREHOUSE_COST = MeasureMappingImpl.builder()
        .withName("Warehouse Cost")
        .withColumn("warehouse_cost")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_WAREHOUSE_SALES = MeasureMappingImpl.builder()
        .withName("Warehouse Sales")
        .withColumn("warehouse_sales")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_UNITS_SHIPPED = MeasureMappingImpl.builder()
        .withName("Units Shipped")
        .withColumn("units_shipped")
        .withFormatString("#.0")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_UNITS_ORDERED = MeasureMappingImpl.builder()
        .withName("Units Ordered")
        .withColumn("units_ordered")
        .withFormatString("#.0")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_WAREHOUSE_PROFIT = MeasureMappingImpl.builder()
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

    private static final MeasureMappingImpl MEASURE_STORE_SQFT = MeasureMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(TABLE_COLUMN_STORE_SQFT)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_GROCERY_SQFT = MeasureMappingImpl.builder()
        .withName(GROCERY_SQFT)
        .withColumn(TABLE_COLUMN_GROCERY_SQFT)
        .withFormatString(FORMAT_STRING_WITHOUT_COMMA)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_ORG_SALARY = MeasureMappingImpl.builder()
        .withName("Org Salary")
        .withColumn("salary_paid")
        .withFormatString(CURRENCY)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl MEASURE_COUNT = MeasureMappingImpl.builder()
        .withName("Count")
        .withColumn(TABLE_COLUMN_EMPLOYEE_ID)
        .withFormatString("#,#")
        .withAggregatorType(MeasureAggregatorType.COUNT)
        .build();

    private static final MeasureMappingImpl MEASURE_NUMBER_OF_EMPLOYEES = MeasureMappingImpl.builder()
        .withName("Number of Employees")
        .withColumn(TABLE_COLUMN_EMPLOYEE_ID)
        .withFormatString("#,#")
        .withAggregatorType(MeasureAggregatorType.DICTINCT_COUNT)
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            MEASURE_UNIT_SALES,
            MEASURE_STORE_COST,
            MEASURE_STORE_SALES,
            MEASURE_SALES_COUNT,
            MEASURE_CUSTOMER_COUNT,
            MEASURE_PROMOTION_SALES
        ))
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_VIRTUAL_CUBE_SALES =
        MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(
                MEASURE_SALES_COUNT,
                MEASURE_STORE_COST,
                MEASURE_STORE_SALES,
                MEASURE_UNIT_SALES
            ))
            .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_WAREHOUSE = MeasureGroupMappingImpl.builder()
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

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_VIRTUAL_CUBE_WAREHOUSE =
        MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(
                MEASURE_STORE_INVOICE,
                MEASURE_SUPPLY_TIME,
                MEASURE_UNITS_ORDERED,
                MEASURE_UNITS_SHIPPED,
                MEASURE_WAREHOUSE_COST,
                MEASURE_WAREHOUSE_PROFIT,
                MEASURE_WAREHOUSE_SALES
            ))
            .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_STORE = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_STORE_SQFT, MEASURE_GROCERY_SQFT))
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_HR = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_ORG_SALARY, MEASURE_COUNT, MEASURE_NUMBER_OF_EMPLOYEES))
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES_RAGGED = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_UNIT_SALES, MEASURE_STORE_COST, MEASURE_STORE_SALES, MEASURE_SALES_COUNT,
            MEASURE_CUSTOMER_COUNT))
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP_FOR_CUBE_SALES2 = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_SALES_COUNT_WITH_PROPERTY, MEASURE_UNIT_SALES_MEMBER_ORDINAL,
            MEASURE_STORE_SALES_WITH_PROPERTY, MEASURE_STORE_COST_WITH_PROPERTY,
            MEASURE_CUSTOMER_COUNT_WITH_PROPERTY))
        .build();

    private static final PhysicalCubeMappingImpl CUBE_SALES = PhysicalCubeMappingImpl.builder()
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
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TABLE_COLUMN_TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(TABLE_COLUMN_PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTION_MEDIA).withDimension(DIMENSION_PROMOTION_MEDIA).withForeignKey(TABLE_COLUMN_PROMOTION_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTIONS).withDimension(DIMENSION_PROMOTIONS).withForeignKey(TABLE_COLUMN_PROMOTION_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_CUSTOMERS).withDimension(DIMENSION_CUSTOMERS).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EDUCATION_LEVEL).withDimension(DIMENSION_EDUCATION_LEVEL).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_MARITAL_STATUS).withDimension(DIMENSION_MARITAL_STATUS).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME).withDimension(DIMENSION_YEARLY_INCOME_WITH_ALL_MEMBER_NAME).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build()
        ))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_PROFIT, CALCULATED_MEMBER_PROFIT_LAST_PERIOD,
            CALCULATED_MEMBER_PROFIT_GROWINN))
        .build();

    private static final PhysicalCubeMappingImpl CUBE_WAREHOUSE = PhysicalCubeMappingImpl.builder()
        .withName(NAME_CUBE_WAREHOUSE)
        .withQuery(QUERY_TABLE_inventoryFact1997)
        .withDocumentation(new DocumentationMappingImpl(""))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TABLE_COLUMN_TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(TABLE_COLUMN_PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_WAREHOUSE).withDimension(DIMENSION_WAREHOUSE).withForeignKey(TABLE_COLUMN_WAREHOUSE_ID).build()
        ))
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_WAREHOUSE))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE))
        .withNamedSets(List.of(
            topSellersNamedSet
        ))
        .build();

    private static final PhysicalCubeMappingImpl CUBE_STORE = PhysicalCubeMappingImpl.builder()
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

    private static final PhysicalCubeMappingImpl CUBE_HR = PhysicalCubeMappingImpl.builder()
        .withName("HR")
        .withQuery(QUERY_TABLE_SALARY)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_HR))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME_HR).withForeignKey("pay_date").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_JOIN_EMPLOYEE_STORE).withForeignKey(TABLE_COLUMN_EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PAY_TYPE).withDimension(DIMENSION_PAY_TYPE).withForeignKey(TABLE_COLUMN_EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_EMPLOYEE).withForeignKey(TABLE_COLUMN_EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_POSITION).withDimension(DIMENSION_POSITION).withForeignKey(TABLE_COLUMN_EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_DEPARTMENT).withDimension(DIMENSION_DEPARTMENT).withForeignKey(TABLE_COLUMN_DEPARTMENT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EMPLOYEES).withDimension(DIMENSION_EMPLOYEES).withForeignKey(TABLE_COLUMN_EMPLOYEE_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_EMPLOEE_SALARY, CALCULATED_MEMBER_AVG_SALARY))
        .build();

    private static final PhysicalCubeMappingImpl CUBE_SALES_RAGGED = PhysicalCubeMappingImpl.builder()
        .withName("Sales Ragged")
        .withQuery(TABLE_QUERY_FACT_SALES_1997_WITH_AGG_EXCLUSED_FOR_CUBE_SALES_RAGGED)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_SALES_RAGGED))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE).withDimension(DIMENSION_STORE_WITH_QUERY_STORE_RAGGED).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GEOGRAPHY).withDimension(DIMENSION_GEOGRAPHY).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_SIZE_IN_SQFT).withDimension(DIMENSION_STORE_SIZE_IN_SQFT).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_STORE_TYPE).withDimension(DIMENSION_STORE_TYPE_WITH_QUERY_STORE).withForeignKey(TABLE_COLUMN_STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TABLE_COLUMN_TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(TABLE_COLUMN_PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTION_MEDIA).withDimension(promotionMedia1Dimension).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PROMOTIONS).withDimension(promotions1Dimension).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_CUSTOMERS).withDimension(DIMENSION_CUSTOMERS).withForeignKey(TABLE_COLUMN_DEPARTMENT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_EDUCATION_LEVEL).withDimension(DIMENSION_EDUCATION_LEVEL).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_MARITAL_STATUS).withDimension(DIMENSION_MARITAL_STATUS).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME).withDimension(DIMENSION_YEARLY_INCOME).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    private static final PhysicalCubeMappingImpl CUBE_SALES_2 = PhysicalCubeMappingImpl.builder()
        .withName("Sales 2")
        .withQuery(TABLE_QUERY_FACT_SALES_1997)
        .withMeasureGroups(List.of(MEASURE_GROUP_FOR_CUBE_SALES2))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_TIME).withDimension(DIMENSION_TIME).withForeignKey(TABLE_COLUMN_TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_PRODUCT).withDimension(DIMENSION_PRODUCT).withForeignKey(TABLE_COLUMN_PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(NAME_DIMENSION_GENDER).withDimension(DIMENSION_GENDER).withForeignKey(TABLE_COLUMN_CUSTOMER_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(CALCULATED_MEMBER_PROFIT_WITH_ORDER,
            CALCULATED_MEMBER_PROFIT_LAST_PERIOD_FOR_CUBE_SALES2))
        .build();

    private static final VirtualCubeMappingImpl CUBE_VIRTIAL_WAREHOUSE_AND_SALES = VirtualCubeMappingImpl.builder()
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
                .withPhysicalCube(CUBE_SALES)
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
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_TIME)
                .withOverrideDimensionName(NAME_DIMENSION_TIME)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_YEARLY_INCOME_WITH_ALL_MEMBER_NAME)
                .withOverrideDimensionName(NAME_DIMENSION_YEARLY_INCOME)
                .withPhysicalCube(CUBE_SALES)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(DIMENSION_WAREHOUSE)
                .withOverrideDimensionName(NAME_DIMENSION_WAREHOUSE)
                .withPhysicalCube(CUBE_WAREHOUSE)
                .build()
        ))
        .withMeasureGroups(List.of(
            MEASURE_GROUP_FOR_VIRTUAL_CUBE_SALES,
            MEASURE_GROUP_FOR_VIRTUAL_CUBE_WAREHOUSE
        ))
        .withCalculatedMembers(List.of(
            CALCULATED_MEMBER_PROFIT,
            CALCULATED_MEMBER_PROFIT_GROWINN,
            CALCULATED_MEMBER_AVERAGE_WAREHOUSE_SALE,
            profitPerUnitShippedCalculatedMember
        ))
        .build();

    private static final AccessRoleMappingImpl californiaManagerRole = AccessRoleMappingImpl.builder()
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

    private static final AccessRoleMappingImpl noHRCubeRole = AccessRoleMappingImpl.builder()
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

    private static final AccessRoleMappingImpl administratorRole = AccessRoleMappingImpl.builder()
        .withName("Administrator")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchema.ALL)
                .build()
        ))
        .build();

    private static final SchemaMappingImpl SCHEMA = SchemaMappingImpl.builder()
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
            .build();
    }

}
