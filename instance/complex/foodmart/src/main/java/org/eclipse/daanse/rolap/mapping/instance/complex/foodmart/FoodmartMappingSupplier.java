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
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCubeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchyEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMemberGrantEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessSchemaEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.PropertyTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.TypeEnum;
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
@Component(service =  CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class FoodmartMappingSupplier implements CatalogMappingSupplier {

    private static final String MEMBER_ORDINAL = "MEMBER_ORDINAL";

    private static final String MARITAL_STATUS2 = "marital_status";

    private static final String GENDER2 = "gender";

    private static final String MEAT_SQFT2 = "meat_sqft";

    private static final String FROZEN_SQFT2 = "frozen_sqft";

    private static final String FORMAT_STRING3 = "$#,##0.00";

    private static final String FORMAT_STRING2 = "FORMAT_STRING";

    private static final String DISTINCT_COUNT = "distinct-count";

    private static final String COUNT = "count";

    private static final String SUM = "sum";

    private static final String FORMAT_STRING1 = "#,###.00";

    private static final String GENERIC = "generic";

    private static final String TERADATA = "teradata";

    private static final String MARIADB = "mariadb";

    private static final String MYSQL = "mysql";

    private static final String MSSQL = "mssql";

    private static final String ACCESS = "access";

    private static final String HIVE = "hive";

    private static final String FORMAT_STRING = "#,###";

    private static final String SNOWFLAKE = "snowflake";

    private static final String NUODB = "nuodb";

    private static final String DB2 = "db2";

    private static final String LUCIDDB = "luciddb";

    private static final String DERBY = "derby";

    private static final String NEOVIEW = "neoview";

    private static final String POSTGRES = "postgres";

    private static final String HSQLDB = "hsqldb";

    private static final String CURRENCY = "Currency";

    private static final String STORE_STREET_ADDRESS = "store_street_address";

    private static final String COFFEE_BAR = "coffee_bar";

    private static final String GROCERY_SQFT2 = "grocery_sqft";

    private static final String STORE_MANAGER2 = "store_manager";

    private static final String H2 = "h2";

    private static final String ORACLE = "oracle";

    private static final String NAME2 = "Name";

    private static final String CITY2 = "city";

    private static final String STATE_PROVINCE2 = "state_province";

    private static final String COUNTRY2 = "country";

    private static final String PROMOTION_NAME2 = "promotion_name";

    private static final String PROMOTION_NAME = "Promotion Name";

    private static final String MEDIA_TYPE2 = "media_type";

    private static final String MEDIA_TYPE = "Media Type";

    private static final String WAREHOUSE_NAME2 = "warehouse_name";

    private static final String WAREHOUSE_NAME = "Warehouse Name";

    private static final String WAREHOUSE_CITY = "warehouse_city";

    private static final String CITY = "City";

    private static final String WAREHOUSE_STATE_PROVINCE = "warehouse_state_province";

    private static final String STATE_PROVINCE = "State Province";

    private static final String STATE = "State";

    private static final String WAREHOUSE_COUNTRY = "warehouse_country";

    private static final String COUNTRY = "Country";

    private static final String PRODUCT_NAME2 = "product_name";

    private static final String BRAND_NAME2 = "brand_name";

    private static final String PRODUCT_SUBCATEGORY2 = "product_subcategory";

    private static final String PRODUCT_CATEGORY2 = "product_category";

    private static final String EMPLOYEE_ID = "employee_id";

    private static final String ALL_EMPLOYEES = "All Employees";

    private static final String GEOGRAPHY = "Geography";

    private static final String DEPARTMENT_ID = "department_id";

    private static final String EMPLOYEES = "Employees";

    private static final String DEPARTMENT = "Department";

    private static final String POSITION2 = "Position";

    private static final String PAY_TYPE = "Pay Type";

    private static final String YEARLY_INCOME = "Yearly Income";

    private static final String ALL_MARITAL_STATUS = "All Marital Status";

    private static final String MARITAL_STATUS = "Marital Status";

    private static final String GENDER = "Gender";

    private static final String ALL_GENDER = "All Gender";

    private static final String EDUCATION_LEVEL = "Education Level";

    private static final String CUSTOMERS = "Customers";

    private static final String ALL_CUSTOMERS = "All Customers";

    private static final String ALL_PROMOTIONS = "All Promotions";

    private static final String PROMOTIONS = "Promotions";

    private static final String ALL_MEDIA = "All Media";

    private static final String PROMOTION_MEDIA = "Promotion Media";

    private static final String PROMOTION_ID = "promotion_id";

    private static final String CUSTOMER_ID = "customer_id";

    private static final String BAR = "bar";

    private static final String FOO = "foo";

    private static final String FACT_COUNT = "FACT_COUNT";

    private static final String WAREHOUSE_ID = "warehouse_id";

    private static final String SALES_FACT_1997 = "sales_fact_1997";

    private static final String WAREHOUSE = "Warehouse";

    private static final String PRODUCT_NAME = "Product Name";

    private static final String BRAND_NAME = "Brand Name";

    private static final String PRODUCT_SUBCATEGORY = "Product Subcategory";

    private static final String PRODUCT_CATEGORY = "Product Category";

    private static final String DETAILS = "Details";

    private static final String PRODUCT_ID = "product_id";

    private static final String PRODUCT2 = "Product";

    private static final String THE_DATE = "the_date";

    private static final String TIME_ID = "time_id";

    private static final String TIME = "Time";

    private static final String STORE_SIZE_IN_SQFT = "Store Size in SQFT";

    private static final String STORE2 = "Store";

    private static final String PRODUCT_DEPARTMENT2 = "product_department";

    private static final String PRODUCT_DEPARTMENT = "Product Department";

    private static final String PRODUCT_FAMILY2 = "product_family";

    private static final String PRODUCT_FAMILY = "Product Family";

    private static final String STORE_TYPE2 = "store_type";

    private static final String THE_MONTH = "the_month";

    private static final String MONTH_OF_YEAR = "month_of_year";

    private static final String MONTH = "Month";

    private static final String QUARTER2 = "quarter";

    private static final String QUARTER = "Quarter";

    private static final String THE_YEAR = "the_year";

    private static final String YEAR = "Year";

    private static final String STORE_SQFT2 = "store_sqft";

    private static final String STREET_ADDRESS = "Street address";

    private static final String HAS_COFFEE_BAR = "Has coffee bar";

    private static final String MEAT_SQFT = "Meat Sqft";

    private static final String FROZEN_SQFT = "Frozen Sqft";

    private static final String GROCERY_SQFT = "Grocery Sqft";

    private static final String STORE_SQFT = "Store Sqft";

    private static final String STORE_MANAGER = "Store Manager";

    private static final String STORE_TYPE = "Store Type";

    private static final String STORE_NAME2 = "store_name";

    private static final String STORE_NAME = "Store Name";

    private static final String STORE_STATE2 = "store_state";

    private static final String STORE_STATE = "Store State";

    private static final String STORE_COUNTRY2 = "store_country";

    private static final String STORE_COUNTRY = "Store Country";

    private static final String STORE_RAGGED = "store_ragged";

    private static final String EMPLOYEE_CLOSURE = "employee_closure";

    private static final String SALARY = "salary";

    private static final String STORE_CITY2 = "store_city";

    private static final String STORE_CITY = "Store City";

    private static final String POSITION = "position";

    private static final String POSITION_ID = "position_id";

    private static final String POSITION_ID2 = POSITION_ID;

    private static final String STORE_ID = "store_id";

    private static final String EMPLOYEE = "employee";

    private static final String PRODUCT_CLASS = "product_class";

    private static final String PRODUCT_CLASS_ID = "product_class_id";

    private static final String PRODUCT = "product";

    private static final String STORE = "store";

    private static final String NAME = "FoodMart";

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    private static final TableQueryMappingImpl storeTable = TableQueryMappingImpl.builder().withName(STORE).build();
    private static final TableQueryMappingImpl timeByDayTable = TableQueryMappingImpl.builder().withName("time_by_day"
    ).build();
    private static final TableQueryMappingImpl productTable =
        TableQueryMappingImpl.builder().withName(PRODUCT).build();
    private static final TableQueryMappingImpl productClassTable = TableQueryMappingImpl.builder().withName(
        PRODUCT_CLASS).build();
    private static final TableQueryMappingImpl employeeTable = TableQueryMappingImpl.builder().withName(
        EMPLOYEE).build();
    private static final TableQueryMappingImpl positionTable = TableQueryMappingImpl.builder().withName(
        POSITION).build();
    private static final TableQueryMappingImpl salaryTable = TableQueryMappingImpl.builder().withName(
        SALARY).build();
    private static final TableQueryMappingImpl employeeClosureTable = TableQueryMappingImpl.builder().withName(
        EMPLOYEE_CLOSURE).build();
    private static final TableQueryMappingImpl storeRaggedTable = TableQueryMappingImpl.builder().withName(
        STORE_RAGGED).build();

    private static final JoinQueryMappingImpl join1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID)
            .withQuery(productTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(PRODUCT_CLASS_ID)
            .withQuery(productClassTable).build()).build();
    private static final JoinQueryMappingImpl join2 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(STORE_ID)
            .withQuery(employeeTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(STORE_ID)
            .withQuery(storeTable).build()).build();
    private static final JoinQueryMappingImpl join3 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey(POSITION_ID2)
            .withQuery(employeeTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey(POSITION_ID2)
            .withQuery(positionTable).build()).build();
    private static final TableQueryMappingImpl warehouseTable =
        TableQueryMappingImpl.builder().withName("warehouse").build();
    private static final TableQueryMappingImpl promotionTable =
        TableQueryMappingImpl.builder().withName("promotion").build();
    private static final TableQueryMappingImpl customerTable =
        TableQueryMappingImpl.builder().withName("customer").build();
    private static final TableQueryMappingImpl inventoryFact1997Table =
        TableQueryMappingImpl.builder().withName("inventory_fact_1997").build();

    private static final LevelMappingImpl storeCountryLevel = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl storeCountry2Level = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY2)
        .withUniqueMembers(true)
        .withHideMemberIf(HideMemberIfEnum.NEVER)
        .build();

    private static final LevelMappingImpl storeCountry1Level = LevelMappingImpl.builder()
        .withName(STORE_COUNTRY)
        .withColumn(STORE_COUNTRY2)
        .withTable(STORE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl storeStateLevel = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(STORE_STATE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl storeState2Level = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withColumn(STORE_STATE2)
        .withUniqueMembers(true)
        .withHideMemberIf(HideMemberIfEnum.IF_PARENTS_NAME)
        .build();

    private static final LevelMappingImpl storeState1Level = LevelMappingImpl.builder()
        .withName(STORE_STATE)
        .withTable(STORE)
        .withColumn(STORE_STATE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl storeCityLevel = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(STORE_CITY2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl storeCity2Level = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withColumn(STORE_CITY2)
        .withUniqueMembers(false)
        .withHideMemberIf(HideMemberIfEnum.IF_BLANK_NAME)
        .build();

    private static final LevelMappingImpl storeCity1Level = LevelMappingImpl.builder()
        .withName(STORE_CITY)
        .withTable(STORE)
        .withColumn(STORE_CITY2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl storeNameLevel = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(STORE_NAME2)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(STORE_TYPE).withColumn(STORE_TYPE2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT2).withType(PropertyTypeEnum.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(HAS_COFFEE_BAR).withColumn(COFFEE_BAR).withType(PropertyTypeEnum.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withType(
                PropertyTypeEnum.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl storeName2Level = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withColumn(STORE_NAME2)
        .withUniqueMembers(true)
        .withHideMemberIf(HideMemberIfEnum.NEVER)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(STORE_TYPE).withColumn(STORE_TYPE2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT2).withType(PropertyTypeEnum.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(HAS_COFFEE_BAR).withColumn(COFFEE_BAR).withType(PropertyTypeEnum.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withType(
                PropertyTypeEnum.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl storeName1Level = LevelMappingImpl.builder()
        .withName(STORE_NAME)
        .withTable(STORE)
        .withColumn(STORE_NAME2)
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(STORE_TYPE).withColumn(STORE_TYPE2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_MANAGER).withColumn(STORE_MANAGER2).build(),
            MemberPropertyMappingImpl.builder().withName(STORE_SQFT).withColumn(STORE_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(GROCERY_SQFT).withColumn(GROCERY_SQFT2).withType(PropertyTypeEnum.NUMERIC
            ).build(),
            MemberPropertyMappingImpl.builder().withName(FROZEN_SQFT).withColumn(FROZEN_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(MEAT_SQFT).withColumn(MEAT_SQFT2).withType(PropertyTypeEnum.NUMERIC).build(),
            MemberPropertyMappingImpl.builder().withName(HAS_COFFEE_BAR).withColumn(COFFEE_BAR).withType(PropertyTypeEnum.BOOLEAN
            ).build(),
            MemberPropertyMappingImpl.builder().withName(STREET_ADDRESS).withColumn(STORE_STREET_ADDRESS).withType(
                PropertyTypeEnum.STRING).build()
        ))
        .build();

    private static final LevelMappingImpl storeSqftLevel = LevelMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(STORE_SQFT2)
        .withUniqueMembers(true)
        .withType(TypeEnum.NUMERIC)
        .build();

    private static final LevelMappingImpl yearLevel = LevelMappingImpl.builder()
        .withName(YEAR)
        .withColumn(THE_YEAR)
        .withUniqueMembers(true)
        .withType(TypeEnum.NUMERIC)
        .withLevelType(LevelTypeEnum.TIME_YEARS)
        .build();

    private static final LevelMappingImpl quarterLevel = LevelMappingImpl.builder()
        .withName(QUARTER)
        .withColumn(QUARTER2)
        .withUniqueMembers(false)
        .withLevelType(LevelTypeEnum.TIME_QUARTERS)
        .build();

    private static final LevelMappingImpl monthLevel = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(MONTH_OF_YEAR)
        .withUniqueMembers(false)
        .withType(TypeEnum.NUMERIC)
        .withLevelType(LevelTypeEnum.TIME_MONTHS)
        .build();

    private static final LevelMappingImpl month1Level = LevelMappingImpl.builder()
        .withName(MONTH)
        .withColumn(MONTH_OF_YEAR)
        .withNameColumn(THE_MONTH)
        .withUniqueMembers(false)
        .withType(TypeEnum.NUMERIC)
        .withLevelType(LevelTypeEnum.TIME_MONTHS)
        .build();

    private static final LevelMappingImpl storeTypeLevel = LevelMappingImpl.builder()
        .withName(STORE_TYPE)
        .withColumn(STORE_TYPE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl storeType1Level = LevelMappingImpl.builder()
        .withName(STORE_TYPE)
        .withTable(STORE)
        .withColumn(STORE_TYPE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl productFamilyLevel = LevelMappingImpl.builder()
        .withName(PRODUCT_FAMILY)
        .withTable(PRODUCT_CLASS)
        .withColumn(PRODUCT_FAMILY2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl productDepartmentLevel = LevelMappingImpl.builder()
        .withName(PRODUCT_DEPARTMENT)
        .withTable(PRODUCT_CLASS)
        .withColumn(PRODUCT_DEPARTMENT2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl productCategoryLevel = LevelMappingImpl.builder()
        .withName(PRODUCT_CATEGORY)
        .withTable(PRODUCT_CLASS)
        .withColumn(PRODUCT_CATEGORY2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl productSubcategoryLevel = LevelMappingImpl.builder()
        .withName(PRODUCT_SUBCATEGORY)
        .withTable(PRODUCT_CLASS)
        .withColumn(PRODUCT_SUBCATEGORY2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl brandNameLevel = LevelMappingImpl.builder()
        .withName(BRAND_NAME)
        .withTable(PRODUCT)
        .withColumn(BRAND_NAME2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl productNameLevel = LevelMappingImpl.builder()
        .withName(PRODUCT_NAME)
        .withTable(PRODUCT)
        .withColumn(PRODUCT_NAME2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl countryLevel = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(WAREHOUSE_COUNTRY)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl country2Level = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(STORE_COUNTRY2)
        .withUniqueMembers(true)
        .withHideMemberIf(HideMemberIfEnum.NEVER)
        .build();

    private static final LevelMappingImpl stateLevel = LevelMappingImpl.builder()
        .withName(STATE)
        .withColumn(STORE_STATE2)
        .withUniqueMembers(true)
        .withHideMemberIf(HideMemberIfEnum.IF_PARENTS_NAME)
        .build();

    private static final LevelMappingImpl stateProvinceLevel = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(WAREHOUSE_STATE_PROVINCE)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl cityLevel = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(WAREHOUSE_CITY)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl city2Level = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(STORE_CITY2)
        .withUniqueMembers(false)
        .withHideMemberIf(HideMemberIfEnum.IF_BLANK_NAME)
        .build();

    private static final LevelMappingImpl warehouseNameLevel = LevelMappingImpl.builder()
        .withName(WAREHOUSE_NAME)
        .withColumn(WAREHOUSE_NAME2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl mediaTypeLevel = LevelMappingImpl.builder()
        .withName(MEDIA_TYPE)
        .withColumn(MEDIA_TYPE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl promotionNameLevel = LevelMappingImpl.builder()
        .withName(PROMOTION_NAME)
        .withColumn(PROMOTION_NAME2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl country1Level = LevelMappingImpl.builder()
        .withName(COUNTRY)
        .withColumn(COUNTRY2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl stateProvince1Level = LevelMappingImpl.builder()
        .withName(STATE_PROVINCE)
        .withColumn(STATE_PROVINCE2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl city1Level = LevelMappingImpl.builder()
        .withName(CITY)
        .withColumn(CITY2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl nameLevel = LevelMappingImpl.builder()
        .withName(NAME2)
        .withColumn(CUSTOMER_ID)
        .withType(TypeEnum.NUMERIC)
        .withUniqueMembers(true)
        .withNameExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        ORACLE,
                        H2,
                        HSQLDB,
                        ORACLE,
                        POSTGRES,
                        LUCIDDB,
                        TERADATA
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        HIVE
                    ))
                    .withStatement("`customer`.`fullname`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        ACCESS,
                        MSSQL
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        MYSQL,
                        MARIADB
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DERBY,
                        NEOVIEW,
                        SNOWFLAKE
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DB2
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        GENERIC
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withOrdinalExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        ORACLE,
                        H2,
                        HSQLDB,
                        POSTGRES,
                        LUCIDDB
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        ACCESS,
                        MSSQL
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        MYSQL,
                        MARIADB
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        NEOVIEW,
                        DERBY,
                        SNOWFLAKE
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        DB2
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        GENERIC
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(GENDER).withColumn(GENDER2).build(),
            MemberPropertyMappingImpl.builder().withName(MARITAL_STATUS).withColumn(MARITAL_STATUS2).build(),
            MemberPropertyMappingImpl.builder().withName("Education").withColumn("education").build(),
            MemberPropertyMappingImpl.builder().withName(YEARLY_INCOME).withColumn("yearly_income").build()
        ))
        .build();

    private static final LevelMappingImpl educationLevel = LevelMappingImpl.builder()
        .withName(EDUCATION_LEVEL)
        .withColumn("education")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl genderLevel = LevelMappingImpl.builder()
        .withName(GENDER)
        .withColumn(GENDER2)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl maritalStatusLevel = LevelMappingImpl.builder()
        .withName(MARITAL_STATUS)
        .withColumn(MARITAL_STATUS2)
        .withUniqueMembers(true)
        .withApproxRowCount("111")
        .build();

    private static final LevelMappingImpl yearlyIncomeLevel = LevelMappingImpl.builder()
        .withName(YEARLY_INCOME)
        .withColumn("yearly_income")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl hasCoffeeBarLevel = LevelMappingImpl.builder()
        .withName(HAS_COFFEE_BAR)
        .withColumn(COFFEE_BAR)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl payTypeLevel = LevelMappingImpl.builder()
        .withName(PAY_TYPE)
        .withColumn("pay_type")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl managementRoleLevel = LevelMappingImpl.builder()
        .withName("Management Rol")
        .withColumn("management_role")
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl positionTitleLevel = LevelMappingImpl.builder()
        .withName("Position Title")
        .withColumn("position_title")
        .withOrdinalColumn(POSITION_ID2)
        .withUniqueMembers(false)
        .build();

    private static final LevelMappingImpl departmentDescriptionLevel = LevelMappingImpl.builder()
        .withName("Department Description")
        .withType(TypeEnum.NUMERIC)
        .withColumn(DEPARTMENT_ID)
        .withUniqueMembers(true)
        .build();

    private static final LevelMappingImpl employeeIdLevel = LevelMappingImpl.builder()
        .withName("Employee Id")
        .withType(TypeEnum.NUMERIC)
        .withColumn(EMPLOYEE_ID)
        .withParentColumn("supervisor_id")
        .withNullParentValue("0")
        .withParentChildLink(
            ParentChildLinkMappingImpl.builder()
                .withParentColumn("supervisor_id")
                .withChildColumn(EMPLOYEE_ID)
                .withTable(employeeClosureTable)
                .build())
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName(MARITAL_STATUS).withColumn(MARITAL_STATUS2).build(),
            MemberPropertyMappingImpl.builder().withName("Position Title").withColumn("position_title").build(),
            MemberPropertyMappingImpl.builder().withName(GENDER).withColumn(GENDER2).build(),
            MemberPropertyMappingImpl.builder().withName("Salary").withColumn(SALARY).build(),
            MemberPropertyMappingImpl.builder().withName(EDUCATION_LEVEL).withColumn("education_level").build(),
            MemberPropertyMappingImpl.builder().withName("Management Role").withColumn("management_role").build()
        ))
        .build();

    private static final CalculatedMemberMappingImpl profitCalculatedMember = CalculatedMemberMappingImpl.builder()
        .withName("Profit")
        .withFormula("[Measures].[Store Sales] - [Measures].[Store Cost]")
        .withCalculatedMemberProperties(List.of(
            CalculatedMemberPropertyMappingImpl.builder()
                .withName(FORMAT_STRING2)
                .withValue(FORMAT_STRING3)
                .build(),
            CalculatedMemberPropertyMappingImpl.builder()
                .withName(MEMBER_ORDINAL)
                .withValue("4")
                .build()
        ))
        .build();

    private static final CalculatedMemberMappingImpl profitLastPeriodCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit last Period")
            .withFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING2)
                    .withValue(FORMAT_STRING3)
                    .build(),
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(MEMBER_ORDINAL)
                    .withValue("18")
                    .build()
            ))
            .build();

    private static final CalculatedMemberMappingImpl profitLastPeriod1CalculatedMember =
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

    private static final CalculatedMemberMappingImpl profitGrowthCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            //.withName("Profit Growth")
            .withName("Gewinn-Wachstum")
            .withFormula("([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]")
            .withVisible(true)
            //.withCaption("Gewinn-Wachstum")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING2)
                    .withValue("0.0%")
                    .build()
            ))
            .build();

    private static final CalculatedMemberMappingImpl averageWarehouseSaleCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Average Warehouse Sale")
            .withFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName(FORMAT_STRING2)
                    .withValue(FORMAT_STRING3)
                    .build()
            ))
            .build();

    private static final CalculatedMemberMappingImpl employeeSalaryCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Employee Salary")
            .withFormatString(CURRENCY)
            .withFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])")
            .build();

    private static final CalculatedMemberMappingImpl avgSalaryCalculatedMember = CalculatedMemberMappingImpl.builder()
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
        .withPrimaryKey(STORE_SQFT2)
        .withQuery(storeTable)
        .withLevels(List.of(storeCountryLevel, storeStateLevel, storeCityLevel, storeNameLevel))
        .build();

    private static final StandardDimensionMappingImpl storeDimension = StandardDimensionMappingImpl.builder()
        .withName(STORE2)
        .withHierarchies(List.of(storeHierarchy))
        .build();

    private static final StandardDimensionMappingImpl store2Dimension = StandardDimensionMappingImpl.builder()
        .withName(STORE2)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID)
            .withQuery(storeRaggedTable)
            .withLevels(List.of(storeCountry2Level, storeState2Level, storeCity2Level, storeName2Level))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl store1Dimension = StandardDimensionMappingImpl.builder()
        .withName(STORE2)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(EMPLOYEE_ID)
            .withQuery(join2)
            .withLevels(List.of(storeCountry1Level, storeState1Level, storeCity1Level, storeName1Level))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl storeSizeInSQFTDimension = StandardDimensionMappingImpl.builder()
        .withName(STORE_SIZE_IN_SQFT)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID)
            .withQuery(storeTable)
            .withLevels(List.of(storeSqftLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl storeTypeDimension = StandardDimensionMappingImpl.builder()
        .withName(STORE_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID)
            .withQuery(storeTable)
            .withLevels(List.of(storeTypeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl storeType1Dimension = StandardDimensionMappingImpl.builder()
        .withName(STORE_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(storeTypeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl storeType2Dimension = StandardDimensionMappingImpl.builder()
        .withName(STORE_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKeyTable(EMPLOYEE)
            .withPrimaryKey(EMPLOYEE_ID)
            .withQuery(join2)
            .withLevels(List.of(storeType1Level))
            .build()))
        .build();

    private static final TimeDimensionMappingImpl timeDimension = TimeDimensionMappingImpl.builder()
        .withName(TIME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey(TIME_ID)
            .withQuery(timeByDayTable)
            .withLevels(List.of(yearLevel, quarterLevel, monthLevel))
            .build()))
        .build();

    private static final TimeDimensionMappingImpl time1Dimension = TimeDimensionMappingImpl.builder()
        .withName(TIME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey(THE_DATE)
            .withQuery(timeByDayTable)
            .withLevels(List.of(yearLevel, quarterLevel, month1Level))
            .build()))
        .build();

    private static final TimeDimensionMappingImpl productDimension = TimeDimensionMappingImpl.builder()
        .withName(PRODUCT2)
        .withHierarchies(List.of(
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey(PRODUCT_ID)
                .withQuery(join1)
                .withLevels(List.of(productFamilyLevel, productDepartmentLevel, productCategoryLevel,
                    productSubcategoryLevel, brandNameLevel, productNameLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_FAMILY)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(productFamilyLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_DEPARTMENT)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(productDepartmentLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_CATEGORY)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(productCategoryLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_SUBCATEGORY)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(productSubcategoryLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(BRAND_NAME)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(brandNameLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName(PRODUCT_NAME)
                .withPrimaryKey(PRODUCT_ID)
                .withPrimaryKeyTable(PRODUCT)
                .withDisplayFolder(DETAILS)
                .withQuery(join1)
                .withLevels(List.of(productNameLevel))
                .build()
        ))
        .build();

    private static final TimeDimensionMappingImpl warehouseDimension = TimeDimensionMappingImpl.builder()
        .withName(WAREHOUSE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(WAREHOUSE_ID)
            .withQuery(warehouseTable)
            .withLevels(List.of(countryLevel, stateProvinceLevel, cityLevel, warehouseNameLevel))
            .build()))
        .build();

    private static final TableQueryMappingImpl salesFact19972Table = TableQueryMappingImpl.builder()
        .withName(SALES_FACT_1997).build();

    private static final TableQueryMappingImpl salesFact1997Table = TableQueryMappingImpl.builder()
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
                    AggregationForeignKeyMappingImpl.builder().withFactColumn(PRODUCT_ID).withAggregationColumn(
                        "PRODUCT_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn(CUSTOMER_ID).withAggregationColumn(
                        "CUSTOMER_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn(PROMOTION_ID).withAggregationColumn(
                        "PROMOTION_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn(STORE_ID).withAggregationColumn(
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

    private static final TableQueryMappingImpl salesFact19971Table = TableQueryMappingImpl.builder()
        .withName(SALES_FACT_1997)
        .withAggregationExcludes(List.of(
            AggregationExcludeMappingImpl.builder().withName("agg_pc_10_sales_fact_1997").build(),
            AggregationExcludeMappingImpl.builder().withName("agg_lc_10_sales_fact_1997").build()
        ))
        .build();

    private static final StandardDimensionMappingImpl promotionMediaDimension = StandardDimensionMappingImpl.builder()
        .withName(PROMOTION_MEDIA)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MEDIA)
            .withPrimaryKey(PROMOTION_ID)
            .withDefaultMember(ALL_MEDIA)
            .withQuery(promotionTable)
            .withLevels(List.of(mediaTypeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl promotionMedia1Dimension = StandardDimensionMappingImpl.builder()
        .withName(PROMOTION_MEDIA)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(PROMOTION_ID)
            .withQuery(promotionTable)
            .withLevels(List.of(mediaTypeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl promotionsDimension = StandardDimensionMappingImpl.builder()
        .withName(PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MEDIA)
            .withPrimaryKey(PROMOTION_ID)
            .withDefaultMember("[All Promotions]")
            .withQuery(promotionTable)
            .withLevels(List.of(promotionNameLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl promotions1Dimension = StandardDimensionMappingImpl.builder()
        .withName(PROMOTIONS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_PROMOTIONS)
            .withPrimaryKey(PROMOTION_ID)
            .withQuery(promotionTable)
            .withLevels(List.of(promotionNameLevel))
            .build()))
        .build();

    private static final HierarchyMappingImpl customersHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_CUSTOMERS)
        .withPrimaryKey(CUSTOMER_ID)
        .withQuery(customerTable)
        .withLevels(List.of(country1Level, stateProvince1Level, city1Level, nameLevel))
        .build();

    private static final StandardDimensionMappingImpl customersDimension = StandardDimensionMappingImpl.builder()
        .withName(CUSTOMERS)
        .withHierarchies(List.of())
        .build();

    private static final StandardDimensionMappingImpl educationLevelDimension = StandardDimensionMappingImpl.builder()
        .withName(EDUCATION_LEVEL)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(CUSTOMER_ID)
            .withQuery(customerTable)
            .withLevels(List.of(educationLevel))
            .build()))
        .build();

    private static final HierarchyMappingImpl genderHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName(ALL_GENDER)
        .withPrimaryKey(CUSTOMER_ID)
        .withQuery(customerTable)
        .withLevels(List.of(genderLevel))
        .build();

    private static final StandardDimensionMappingImpl genderDimension = StandardDimensionMappingImpl.builder()
        .withName(GENDER)
        .withHierarchies(List.of(genderHierarchy))
        .build();

    private static final StandardDimensionMappingImpl maritalStatusDimension = StandardDimensionMappingImpl.builder()
        .withName(MARITAL_STATUS)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MARITAL_STATUS)
            .withPrimaryKey(CUSTOMER_ID)
            .withQuery(customerTable)
            .withLevels(List.of(maritalStatusLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl yearlyIncomeDimension = StandardDimensionMappingImpl.builder()
        .withName(YEARLY_INCOME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_MARITAL_STATUS)
            .withPrimaryKey(CUSTOMER_ID)
            .withQuery(customerTable)
            .withLevels(List.of(yearlyIncomeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl yearlyIncome1Dimension = StandardDimensionMappingImpl.builder()
        .withName(YEARLY_INCOME)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(CUSTOMER_ID)
            .withQuery(customerTable)
            .withLevels(List.of(yearlyIncomeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl hasCoffeeBarDimension = StandardDimensionMappingImpl.builder()
        .withName(HAS_COFFEE_BAR)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(hasCoffeeBarLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl payTypeDimension = StandardDimensionMappingImpl.builder()
        .withName(PAY_TYPE)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(EMPLOYEE_ID)
            .withPrimaryKeyTable(EMPLOYEE)
            .withQuery(join3)
            .withLevels(List.of(payTypeLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl positionDimension = StandardDimensionMappingImpl.builder()
        .withName(POSITION2)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(EMPLOYEE_ID)
            .withPrimaryKeyTable(EMPLOYEE)
            .withQuery(employeeTable)
            .withLevels(List.of(managementRoleLevel, positionTitleLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl departmentDimension = StandardDimensionMappingImpl.builder()
        .withName(DEPARTMENT)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(DEPARTMENT_ID)
            .withQuery(employeeTable)
            .withLevels(List.of(departmentDescriptionLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl employeesDimension = StandardDimensionMappingImpl.builder()
        .withName(EMPLOYEES)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName(ALL_EMPLOYEES)
            .withPrimaryKey(EMPLOYEE_ID)
            .withQuery(employeeTable)
            .withLevels(List.of(employeeIdLevel))
            .build()))
        .build();

    private static final StandardDimensionMappingImpl geographyDimension = StandardDimensionMappingImpl.builder()
        .withName(GEOGRAPHY)
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey(STORE_ID)
            .withQuery(storeRaggedTable)
            .withLevels(List.of(country2Level, stateLevel, city2Level))
            .build()))
        .build();

    private static final MeasureMappingImpl unitSalesMeasure = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl unitSales1Measure = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withType(SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("2").build()
        ))
        .build();

    private static final MeasureMappingImpl storeCostMeasure = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString(FORMAT_STRING1)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl storeCost1Measure = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString(FORMAT_STRING1)
        .withType(SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("6").build()
        ))
        .build();

    private static final MeasureMappingImpl storeSalesMeasure = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString(FORMAT_STRING1)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl storeSales1Measure = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString(FORMAT_STRING1)
        .withType(SUM)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("3").build()
        ))
        .build();

    private static final MeasureMappingImpl salesCountMeasure = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn(PRODUCT_ID)
        .withFormatString(FORMAT_STRING)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl salesCount1Measure = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn(PRODUCT_ID)
        .withFormatString(FORMAT_STRING)
        .withType(COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("1").build()
        ))
        .build();

    private static final MeasureMappingImpl customerCountMeasure = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn(CUSTOMER_ID)
        .withFormatString(FORMAT_STRING)
        .withType(DISTINCT_COUNT)
        .build();

    private static final MeasureMappingImpl customerCount1Measure = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn(CUSTOMER_ID)
        .withFormatString(FORMAT_STRING)
        .withType(DISTINCT_COUNT)
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName(MEMBER_ORDINAL).withValue("7").build()
        ))
        .build();

    private static final MeasureMappingImpl promotionSalesMeasure = MeasureMappingImpl.builder()
        .withName("Promotion Sales")
        .withFormatString(FORMAT_STRING)
        .withType(SUM)
        .withMeasureExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(ACCESS))
                    .withStatement("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\"" +
                        ".\"store_sales\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        ORACLE,
                        H2,
                        HSQLDB,
                        POSTGRES,
                        NEOVIEW,
                        DERBY,
                        LUCIDDB,
                        DB2,
                        NUODB,
                        SNOWFLAKE
                    ))
                    .withStatement("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else " +
                        "\"sales_fact_1997\".\"store_sales\" end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of("infobright"))
                    .withStatement("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`" +
                        ".`store_sales` end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(ACCESS))
                    .withStatement("`sales_fact_1997`.`store_sales`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(GENERIC))
                    .withStatement("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997" +
                        ".store_sales end)")
                    .build()
            ))
            .build())
        .build();

    private static final MeasureMappingImpl storeInvoiceMeasure = MeasureMappingImpl.builder()
        .withName("Store Invoice")
        .withColumn("store_invoice")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl supplyTimeMeasure = MeasureMappingImpl.builder()
        .withName("Supply Time")
        .withColumn("supply_time")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl warehouseCostMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Cost")
        .withColumn("warehouse_cost")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl warehouseSalesMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Sales")
        .withColumn("warehouse_sales")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl unitsShippedMeasure = MeasureMappingImpl.builder()
        .withName("Units Shipped")
        .withColumn("units_shipped")
        .withFormatString("#.0")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl unitsOrderedMeasure = MeasureMappingImpl.builder()
        .withName("Units Ordered")
        .withColumn("units_ordered")
        .withFormatString("#.0")
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl warehouseProfitMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Profit")
        .withType(SUM)
        .withMeasureExpression(
            SQLExpressionMappingImpl.builder()
                .withSqls(List.of(
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            MYSQL,
                            MARIADB,
                            "infobright"
                        ))
                        .withStatement("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                        .build(),
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            GENERIC
                        ))
                        .withStatement("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;" +
                            "warehouse_cost&quot;")
                        .build()
                ))
                .build()
        )
        .build();

    private static final MeasureMappingImpl storeSqftMeasure = MeasureMappingImpl.builder()
        .withName(STORE_SQFT)
        .withColumn(STORE_SQFT2)
        .withFormatString(FORMAT_STRING)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl grocerySqftMeasure = MeasureMappingImpl.builder()
        .withName(GROCERY_SQFT)
        .withColumn(GROCERY_SQFT2)
        .withFormatString(FORMAT_STRING)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl orgSalaryMeasure = MeasureMappingImpl.builder()
        .withName("Org Salary")
        .withColumn("salary_paid")
        .withFormatString(CURRENCY)
        .withType(SUM)
        .build();

    private static final MeasureMappingImpl countMeasure = MeasureMappingImpl.builder()
        .withName("Count")
        .withColumn(EMPLOYEE_ID)
        .withFormatString("#,#")
        .withType(COUNT)
        .build();

    private static final MeasureMappingImpl numberOfEmployeesMeasure = MeasureMappingImpl.builder()
        .withName("Number of Employees")
        .withColumn(EMPLOYEE_ID)
        .withFormatString("#,#")
        .withType(DISTINCT_COUNT)
        .build();

    private static final MeasureGroupMappingImpl salesMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            unitSalesMeasure,
            storeCostMeasure,
            storeSalesMeasure,
            salesCountMeasure,
            customerCountMeasure,
            promotionSalesMeasure
        ))
        .build();

    private static final MeasureGroupMappingImpl vSalesMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            salesCountMeasure,
            storeCostMeasure,
            storeSalesMeasure,
            unitSalesMeasure
        ))
        .build();

    private static final MeasureGroupMappingImpl warehouseMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            storeInvoiceMeasure,
            supplyTimeMeasure,
            warehouseCostMeasure,
            warehouseSalesMeasure,
            unitsShippedMeasure,
            unitsOrderedMeasure,
            warehouseProfitMeasure
        ))
        .build();

    private static final MeasureGroupMappingImpl vWarehouseMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            storeInvoiceMeasure,
            supplyTimeMeasure,
            unitsOrderedMeasure,
            unitsShippedMeasure,
            warehouseCostMeasure,
            warehouseProfitMeasure,
            warehouseSalesMeasure
        ))
        .build();

    private static final MeasureGroupMappingImpl storeMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(storeSqftMeasure, grocerySqftMeasure))
        .build();

    private static final MeasureGroupMappingImpl hrMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(orgSalaryMeasure, countMeasure, numberOfEmployeesMeasure))
        .build();

    private static final MeasureGroupMappingImpl salesRaggedMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(unitSalesMeasure, storeCostMeasure, storeSalesMeasure, salesCountMeasure,
            customerCountMeasure))
        .build();

    private static final MeasureGroupMappingImpl sales2MeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(salesCount1Measure, unitSales1Measure, storeSales1Measure, storeCost1Measure,
            customerCount1Measure))
        .build();

    private static final PhysicalCubeMappingImpl salesCube = PhysicalCubeMappingImpl.builder()
        .withName("Sales")
        .withQuery(salesFact1997Table)
        .withMeasureGroups(List.of(salesMeasureGroup))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withAnnotations(List.of(
            AnnotationMappingImpl.builder().withName("caption.de_DE").withValue("Verkaufen").build(),
            AnnotationMappingImpl.builder().withName("caption.fr_FR").withValue("Ventes").build(),
            AnnotationMappingImpl.builder().withName("description.fr_FR").withValue("Cube des ventes").build(),
            AnnotationMappingImpl.builder().withName("description.de").withValue("Cube Verkaufen").build(),
            AnnotationMappingImpl.builder().withName("description.de_AT").withValue("Cube den Verkaufen").build()
        ))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE2).withDimension(storeDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_SIZE_IN_SQFT).withDimension(storeSizeInSQFTDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_TYPE).withDimension(storeTypeDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(TIME).withDimension(timeDimension).withForeignKey(TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PRODUCT2).withDimension(productDimension).withForeignKey(PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PROMOTION_MEDIA).withDimension(promotionMediaDimension).withForeignKey(PROMOTION_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PROMOTIONS).withDimension(promotionsDimension).withForeignKey(PROMOTION_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(CUSTOMERS).withDimension(customersDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(EDUCATION_LEVEL).withDimension(educationLevelDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GENDER).withDimension(genderDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(MARITAL_STATUS).withDimension(maritalStatusDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(YEARLY_INCOME).withDimension(yearlyIncomeDimension).withForeignKey(CUSTOMER_ID).build()
        ))
        .withCalculatedMembers(List.of(profitCalculatedMember, profitLastPeriodCalculatedMember,
            profitGrowthCalculatedMember))
        .build();

    private static final PhysicalCubeMappingImpl warehouseCube = PhysicalCubeMappingImpl.builder()
        .withName(WAREHOUSE)
        .withQuery(inventoryFact1997Table)
        .withMeasureGroups(List.of(storeMeasureGroup))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE2).withDimension(storeDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_SIZE_IN_SQFT).withDimension(storeSizeInSQFTDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_TYPE).withDimension(storeTypeDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(TIME).withDimension(timeDimension).withForeignKey(TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PRODUCT2).withDimension(productDimension).withForeignKey(PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(WAREHOUSE).withDimension(warehouseDimension).withForeignKey(WAREHOUSE_ID).build()
        ))
        .withMeasureGroups(List.of(warehouseMeasureGroup))
        .withCalculatedMembers(List.of(averageWarehouseSaleCalculatedMember))
        .withNamedSets(List.of(
            topSellersNamedSet
        ))
        .build();

    private static final PhysicalCubeMappingImpl storeCube = PhysicalCubeMappingImpl.builder()
        .withName(STORE2)
        .withQuery(storeTable)
        .withMeasureGroups(List.of(storeMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_TYPE).withDimension(storeType1Dimension).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE2).withDimension(storeDimension).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(HAS_COFFEE_BAR).withDimension(hasCoffeeBarDimension).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    private static final PhysicalCubeMappingImpl hrCube = PhysicalCubeMappingImpl.builder()
        .withName("HR")
        .withQuery(salaryTable)
        .withMeasureGroups(List.of(hrMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(TIME).withDimension(time1Dimension).withForeignKey("pay_date").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE2).withDimension(store1Dimension).withForeignKey(EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PAY_TYPE).withDimension(payTypeDimension).withForeignKey(EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_TYPE).withDimension(storeType2Dimension).withForeignKey(EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(POSITION2).withDimension(positionDimension).withForeignKey(EMPLOYEE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(DEPARTMENT).withDimension(departmentDimension).withForeignKey(DEPARTMENT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(EMPLOYEES).withDimension(employeesDimension).withForeignKey(EMPLOYEE_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(employeeSalaryCalculatedMember, avgSalaryCalculatedMember))
        .build();

    private static final PhysicalCubeMappingImpl salesRaggedCube = PhysicalCubeMappingImpl.builder()
        .withName("Sales Ragged")
        .withQuery(salesFact19971Table)
        .withMeasureGroups(List.of(salesRaggedMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE2).withDimension(store2Dimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GEOGRAPHY).withDimension(geographyDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_SIZE_IN_SQFT).withDimension(storeSizeInSQFTDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(STORE_TYPE).withDimension(storeTypeDimension).withForeignKey(STORE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(TIME).withDimension(timeDimension).withForeignKey(TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PRODUCT2).withDimension(productDimension).withForeignKey(PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PROMOTION_MEDIA).withDimension(promotionMedia1Dimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PROMOTIONS).withDimension(promotions1Dimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(CUSTOMERS).withDimension(customersDimension).withForeignKey(DEPARTMENT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(EDUCATION_LEVEL).withDimension(educationLevelDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GENDER).withDimension(genderDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(MARITAL_STATUS).withDimension(maritalStatusDimension).withForeignKey(CUSTOMER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(YEARLY_INCOME).withDimension(yearlyIncome1Dimension).withForeignKey(CUSTOMER_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    private static final PhysicalCubeMappingImpl sales2Cube = PhysicalCubeMappingImpl.builder()
        .withName("Sales 2")
        .withQuery(salesFact19972Table)
        .withMeasureGroups(List.of(sales2MeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(TIME).withDimension(time1Dimension).withForeignKey(TIME_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(PRODUCT2).withDimension(productDimension).withForeignKey(PRODUCT_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GENDER).withDimension(genderDimension).withForeignKey(CUSTOMER_ID).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(profitCalculatedMember, profitLastPeriod1CalculatedMember))
        .build();

    private static final VirtualCubeMappingImpl warehouseAndSalesVirtualCube = VirtualCubeMappingImpl.builder()
        .withName("Warehouse and Sales")
        .withDefaultMeasure(storeSalesMeasure)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder()
                .withDimension(customersDimension)
                .withOverrideDimensionName(CUSTOMERS)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(educationLevelDimension)
                .withOverrideDimensionName(EDUCATION_LEVEL)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(genderDimension)
                .withOverrideDimensionName(GENDER)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(maritalStatusDimension)
                .withOverrideDimensionName(MARITAL_STATUS)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(productDimension)
                .withOverrideDimensionName(PRODUCT2)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(promotionMediaDimension)
                .withOverrideDimensionName(PROMOTION_MEDIA)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(promotionsDimension)
                .withOverrideDimensionName(PROMOTIONS)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(storeDimension)
                .withOverrideDimensionName(STORE2)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(timeDimension)
                .withOverrideDimensionName(TIME)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(yearlyIncomeDimension)
                .withOverrideDimensionName(YEARLY_INCOME)
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(warehouseDimension)
                .withOverrideDimensionName(WAREHOUSE)
                .build()
        ))
        .withMeasureGroups(List.of(
            vSalesMeasureGroup,
            vWarehouseMeasureGroup
        ))
        .withCalculatedMembers(List.of(
            profitCalculatedMember,
            profitGrowthCalculatedMember,
            averageWarehouseSaleCalculatedMember,
            profitPerUnitShippedCalculatedMember
        ))
        .build();

    private static final AccessRoleMappingImpl californiaManagerRole = AccessRoleMappingImpl.builder()
        .withName("California manager")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchemaEnum.NONE)
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(salesCube)
                        .withAccess(AccessCubeEnum.ALL)
                        .withHierarchyGrants(List.of(
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(storeHierarchy)
                                .withAccess(AccessHierarchyEnum.CUSTOM)
                                .withTopLevel(storeCountryLevel)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA]")
                                        .withAccess(AccessMemberGrantEnum.ALL)
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA].[Los Angeles]")
                                        .withAccess(AccessMemberGrantEnum.NONE)
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(customersHierarchy)
                                .withAccess(AccessHierarchyEnum.CUSTOM)
                                .withTopLevel(stateProvinceLevel)
                                .withBottomLevel(cityLevel)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA]")
                                        .withAccess(AccessMemberGrantEnum.ALL)
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA].[Los Angeles]")
                                        .withAccess(AccessMemberGrantEnum.NONE)
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(genderHierarchy)
                                .withAccess(AccessHierarchyEnum.NONE)
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
                .withAccess(AccessSchemaEnum.ALL)
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(hrCube)
                        .withAccess(AccessCubeEnum.NONE)
                        .build()
                ))
                .build()
        ))
        .build();

    private static final AccessRoleMappingImpl administratorRole = AccessRoleMappingImpl.builder()
        .withName("Administrator")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess(AccessSchemaEnum.ALL)
                .build()
        ))
        .build();

    private static final SchemaMappingImpl schema = SchemaMappingImpl.builder()
        .withName(NAME)
        .withCubes(List.of(salesCube, warehouseCube, storeCube, hrCube, salesRaggedCube, sales2Cube,
            warehouseAndSalesVirtualCube))
        .withAccessRoles(List.of(
            californiaManagerRole,
            noHRCubeRole,
            administratorRole
        ))
        .build();

    @Override
    public CatalogMapping get() {
        return CatalogMappingImpl.builder()
            .withName(NAME)
            .withDocumentation(documentation)
            .withSchemas(List.of(schema))
            .build();
    }

}
