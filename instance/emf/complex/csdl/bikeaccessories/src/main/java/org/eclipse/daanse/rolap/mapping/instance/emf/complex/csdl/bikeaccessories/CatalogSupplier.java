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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.6", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    public static final String CATALOG_NAME = "CSDLBI 1.0";
    public static final String CATALOG_DESCRIPTION = "DescriptionRolePlayingDimensionsDB";
    public static final String CUBE_NAME = "DescriptionRolePlayingDimensionsDB";
    public static final String MEASURE_NAME = "FactInternetSales";

    public static final DatabaseSchema DATABASE_SCHEMA;

    public static final Column COLUMN_ROW_NUMBER_FACT;
    public static final Column COLUMN_PRODUCT_KEY_FACT;
    public static final Column COLUMN_ORDER_DATE_KEY_FACT;
    public static final Column COLUMN_DUE_DATE_KEY_FACT;
    public static final Column COLUMN_SHIP_DATE_KEY_FACT;
    public static final Column COLUMN_CUSTOMER_KEY_FACT;
    public static final Column COLUMN_PROMOTION_KEY_FACT;
    public static final Column COLUMN_CURENCY_KEY_FACT;
    public static final Column COLUMN_SALES_TERITORY_KEY_FACT;
    public static final Column COLUMN_SALES_ORDER_NUMBER_FACT;
    public static final Column COLUMN_SALES_ORDER_LINE_NUMBER_FACT;
    public static final Column COLUMN_REVISION_NUMBER_FACT;
    public static final Column COLUMN_ORDER_QUANTITY_FACT;
    public static final Column COLUMN_UNIT_PRICE_FACT;
    public static final Column COLUMN_EXTENDED_AMOUNT_FACT;
    public static final Column COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT;
    public static final Column COLUMN_DISCOUNT_AMOUNT_FACT;
    public static final Column COLUMN_PRODUCT_STANDART_COST_FACT;
    public static final Column COLUMN_TOTAL_PRODUCT_COST_FACT;
    public static final Column COLUMN_SALES_AMOUNT_FACT;
    public static final Column COLUMN_TAX_AMT_FACT;
    public static final Column COLUMN_FREIGHT_FACT;
    public static final Column COLUMN_CARRIER_TRACKING_NUMBER_FACT;
    public static final Column COLUMN_CUSTOMER_PO_NUMBER_FACT;
    public static final Column COLUMN_EMPLOYEE_KEY_FACT;
    public static final Column COLUMN_BILLING_CUSTOMER_KEY_FACT;
    public static final Column COLUMN_STORE_KEY_FACT;
    public static final Column COLUMN_TOTAL_SALES_FACT;

    public static final Column COLUMN_ROW_NUMBER_CUSTOMER;
    public static final Column COLUMN_CUSTOMER_KEY_CUSTOMER;
    public static final Column COLUMN_GEOGRAPHY_KEY_CUSTOMER;
    public static final Column COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER;
    public static final Column COLUMN_FIRST_NAME_CUSTOMER;
    public static final Column COLUMN_MIDDLE_NAME_CUSTOMER;
    public static final Column COLUMN_LAST_NAME_CUSTOMER;
    public static final Column COLUMN_NAME_STYLE_CUSTOMER;
    public static final Column COLUMN_BIRTH_DATE_CUSTOMER;
    public static final Column COLUMN_MARITAL_STATUS_CUSTOMER;
    public static final Column COLUMN_SUFFIX_CUSTOMER;
    public static final Column COLUMN_GENDER_CUSTOMER;
    public static final Column COLUMN_EMAIL_ADDRESS_CUSTOMER;
    public static final Column COLUMN_YARLY_INCOME_CUSTOMER;
    public static final Column COLUMN_TOTAL_CHILDREN_CUSTOMER;
    public static final Column COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER;
    public static final Column COLUMN_ENGLISH_EDUCATION_CUSTOMER;
    public static final Column COLUMN_SPANISH_EDUCATION_CUSTOMER;
    public static final Column COLUMN_FRENCH_EDUCATION_CUSTOMER;
    public static final Column COLUMN_ENGLISH_OCCUPATION_CUSTOMER;
    public static final Column COLUMN_SPANISH_OCCUPATION_CUSTOMER;
    public static final Column COLUMN_FRENCH_OCCUPATION_CUSTOMER;
    public static final Column COLUMN_HOUSE_OWNER_FLAG_CUSTOMER;
    public static final Column COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER;
    public static final Column COLUMN_ADDRESS_LINE1_CUSTOMER;
    public static final Column COLUMN_ADDRESS_LINE2_CUSTOMER;
    public static final Column COLUMN_PHONE_CUSTOMER;
    public static final Column COLUMN_DATE_FIRST_PURCHASE_CUSTOMER;
    public static final Column COLUMN_COMMUTE_DISTANCE_CUSTOMER;

    public static final Column COLUMN_ROW_NUMBER_EMPLOYEE;
    public static final Column COLUMN_EMPLOYEE_KEY_EMPLOYEE;
    public static final Column COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE;
    public static final Column COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE;
    public static final Column COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE;
    public static final Column COLUMN_SALES_TERRITORY_KEY_EMPLOYEE;
    public static final Column COLUMN_FIRST_NAME_EMPLOYEE;
    public static final Column COLUMN_LAST_NAME_EMPLOYEE;
    public static final Column COLUMN_MIDDLE_NAME_EMPLOYEE;
    public static final Column COLUMN_NAME_STYLE_EMPLOYEE;
    public static final Column COLUMN_TITLE_EMPLOYEE;
    public static final Column COLUMN_HIRE_DATE_EMPLOYEE;
    public static final Column COLUMN_BIRTH_DATE_EMPLOYEE;
    public static final Column COLUMN_LOGIN_ID_EMPLOYEE;
    public static final Column COLUMN_EMAIL_ADDRESS_EMPLOYEE;
    public static final Column COLUMN_PHONE_EMPLOYEE;
    public static final Column COLUMN_MARITAL_STATUS_EMPLOYEE;
    public static final Column COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE;
    public static final Column COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE;
    public static final Column COLUMN_SALARIED_FLAG_EMPLOYEE;
    public static final Column COLUMN_GENDER_EMPLOYEE;
    public static final Column COLUMN_PAY_FREQUENCY_EMPLOYEE;
    public static final Column COLUMN_BASE_RATE_EMPLOYEE;
    public static final Column COLUMN_VACATION_HOURS_EMPLOYEE;
    public static final Column COLUMN_SICK_LEAVE_HOURS_EMPLOYEE;
    public static final Column COLUMN_CURRENT_FLAG_EMPLOYEE;
    public static final Column COLUMN_SALES_PERSONE_FLAG_EMPLOYEE;
    public static final Column COLUMN_DEPARTAMENT_NAME_EMPLOYEE;
    public static final Column COLUMN_START_DATE_EMPLOYEE;
    public static final Column COLUMN_END_DATE_EMPLOYEE;
    public static final Column COLUMN_STATUS_EMPLOYEE;

    public static final Column COLUMN_ROW_NUMBER_GEOGRAPHY;
    public static final Column COLUMN_GEOGRAPHY_KEY_GEOGRAPHY;
    public static final Column COLUMN_CITY_GEOGRAPHY;
    public static final Column COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY;
    public static final Column COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY;
    public static final Column COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY;
    public static final Column COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY;
    public static final Column COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY;
    public static final Column COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY;
    public static final Column COLUMN_POSTAL_CODE_GEOGRAPHY;
    public static final Column COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY;

    public static final Column COLUMN_ROW_NUMBER_PRODUCT;
    public static final Column COLUMN_PRODUCT_KEY_PRODUCT;
    public static final Column COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT;
    public static final Column COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT;
    public static final Column COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT;
    public static final Column COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT;
    public static final Column COLUMN_SPANISH_PRODUCT_NAME_PRODUCT;
    public static final Column COLUMN_FRENCH_PRODUCT_NAME_PRODUCT;
    public static final Column COLUMN_STANDART_COST_PRODUCT;
    public static final Column COLUMN_FINISH_GOODS_FLAG_PRODUCT;
    public static final Column COLUMN_COLOR_PRODUCT;
    public static final Column COLUMN_SAFETY_STOCK_LEVEL_PRODUCT;
    public static final Column COLUMN_REORDER_POINT_PRODUCT;
    public static final Column COLUMN_LIST_PRICE_PRODUCT;
    public static final Column COLUMN_SIZE_PRODUCT;
    public static final Column COLUMN_SIZE_RANGE_PRODUCT;
    public static final Column COLUMN_WEIGHT_PRODUCT;
    public static final Column COLUMN_DAYS_TO_MANUFACTURE_PRODUCT;
    public static final Column COLUMN_PRODUCT_LINE_PRODUCT;
    public static final Column COLUMN_DEALER_PRICE_PRODUCT;
    public static final Column COLUMN_CLASS_PRODUCT;
    public static final Column COLUMN_STYLE_PRODUCT;
    public static final Column COLUMN_MODEL_NAME_PRODUCT;
    public static final Column COLUMN_ENGLISH_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_FRENCH_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_CHINESE_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_ARABIC_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_HEBREW_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_THAI_DESCRIPTION_PRODUCT;
    public static final Column COLUMN_START_DATE_PRODUCT;
    public static final Column COLUMN_END_DATE_PRODUCT;
    public static final Column COLUMN_STATUS_PRODUCT;
    public static final Column COLUMN_SUBCATEGORY_PRODUCT;

    public static final Column COLUMN_ROW_NUMBER_PRODUCT_CATEGORY;
    public static final Column COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY;
    public static final Column COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY;
    public static final Column COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY;
    public static final Column COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY;
    public static final Column COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY;

    public static final Column COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY;
    public static final Column COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY;

    public static final Column COLUMN_ROW_NUMBER_STORE;
    public static final Column COLUMN_STORE_KEY_STORE;
    public static final Column COLUMN_GEOGRAPHY_KEY_STORE;
    public static final Column COLUMN_STORE_NAME_STORE;
    public static final Column COLUMN_NUMBER_OF_EMPLOYEES_STORE;
    public static final Column COLUMN_SALES_STORE;

    public static final Column COLUMN_ROW_NUMBER_TIME;
    public static final Column COLUMN_TIME_KEY_TIME;
    public static final Column COLUMN_FULL_DATE_ALTERNATE_KEY_TIME;
    public static final Column COLUMN_DAY_NUMBER_OF_WEEK_TIME;
    public static final Column COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME;
    public static final Column COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME;
    public static final Column COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME;
    public static final Column COLUMN_DAY_NUMBER_OF_MONTH_TIME;
    public static final Column COLUMN_DAY_NUMBER_OF_YEAR_TIME;
    public static final Column COLUMN_WEEK_NUMBER_OF_YEAR_TIME;
    public static final Column COLUMN_ENGLISH_MONTH_NAME_TIME;
    public static final Column COLUMN_SPANISH_MONTH_NAME_TIME;
    public static final Column COLUMN_FRENCH_MONTH_NAME_TIME;
    public static final Column COLUMN_MONTH_NUMBER_OF_YEAR_TIME;
    public static final Column COLUMN_CALENDAR_QUARTER_TIME;
    public static final Column COLUMN_CALENDAR_YEAR_TIME;
    public static final Column COLUMN_CALENDAR_SEMESTER_TIME;
    public static final Column COLUMN_FISCAL_QUARTER_TIME;
    public static final Column COLUMN_FISCAL_YEAR_TIME;
    public static final Column COLUMN_FISCAL_SEMESTER_TIME;

    // Static tables
    public static final PhysicalTable TABLE_FACT;
    public static final PhysicalTable TABLE_CUSTOMER;
    public static final PhysicalTable TABLE_EMPLOYEE;
    public static final PhysicalTable TABLE_GEOGRAPHY;
    public static final PhysicalTable TABLE_PRODUCT;
    public static final PhysicalTable TABLE_PRODUCT_CATEGORY;
    public static final PhysicalTable TABLE_PRODUCT_SUBCATEGORY;
    public static final PhysicalTable TABLE_STORE;
    public static final PhysicalTable TABLE_TIME;

    public static final JoinedQueryElement JOIN_PRODUCT_CATEGORY_LEFT;
    public static final JoinedQueryElement JOIN_PRODUCT_CATEGORY_RIGHT;
    public static final JoinQuery JOIN_PRODUCT_CATEGORY;

    public static final JoinedQueryElement JOIN_SUBCATEGORY_CATEGORY_LEFT;
    public static final JoinedQueryElement JOIN_SUBCATEGORY_CATEGORY_RIGHT;
    public static final JoinQuery JOIN_SUBCATEGORY_CATEGORY;

    public static final JoinedQueryElement JOIN_PRODUCT_SUBCATEGORY_LEFT;
    public static final JoinedQueryElement JOIN_PRODUCT_SUBCATEGORY_RIGHT;
    public static final JoinQuery JOIN_PRODUCT_SUBCATEGORY;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_DIM_CUSTOMER;
    public static final DimensionConnector CONNECTOR_DIM_EMPLOYEE;
    public static final DimensionConnector CONNECTOR_DIM_GEOGRAPHY;
    public static final DimensionConnector CONNECTOR_DIM_PRODUCT;
    public static final DimensionConnector CONNECTOR_DIM_PRODUCT_CATEGORY;
    public static final DimensionConnector CONNECTOR_DIM_PRODUCT_SUB_CATEGORY;
    public static final DimensionConnector CONNECTOR_DIM_STORE;
    public static final DimensionConnector CONNECTOR_DIM_TIME;

    public static final ExplicitHierarchy HIERARCHY_CUSTOMER;
    public static final ExplicitHierarchy HIERARCHY_EMPLOYEE;
    public static final ExplicitHierarchy HIERARCHY_GEOGRAPHY;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT_CATEGORY;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT_SUBCATEGORY;
    public static final ExplicitHierarchy HIERARCHY_STORE;
    public static final ExplicitHierarchy HIERARCHY_TIME;

    public static final StandardDimension DIMENSION_DIM_CUSTOMER;
    public static final StandardDimension DIMENSION_DIM_EMPLOYEE;
    public static final StandardDimension DIMENSION_DIM_GEOGRAPHY;
    public static final StandardDimension DIMENSION_DIM_PRODUCT;
    public static final StandardDimension DIMENSION_DIM_PRODUCT_CATEGORY;
    public static final StandardDimension DIMENSION_DIM_PRODUCT_SUB_CATEGORY;
    public static final StandardDimension DIMENSION_DIM_STORE;
    public static final StandardDimension DIMENSION_DIM_TIME;

    public static final MeasureGroup MEASURE_GROUP;

    public static final TableQuery TABLEQUERY_FACT;
    public static final TableQuery TABLEQUERY_CUSTOMER;
    public static final TableQuery TABLEQUERY_EMPLOYEE;
    public static final TableQuery TABLEQUERY_GEOGRAPHY;
    public static final TableQuery TABLEQUERY_PRODUCT;
    public static final TableQuery TABLEQUERY_PRODUCT_CATEGORY;
    public static final TableQuery TABLEQUERY_PRODUCT_SUBCATEGORY;
    public static final TableQuery TABLEQUERY_STORE;
    public static final TableQuery TABLEQUERY_TIME;
    public static final JoinedQueryElement JOIN_GEOGRAPHY_LEFT;
    public static final JoinedQueryElement JOIN_GEOGRAPHY_RIGHT;
    public static final JoinQuery JOIN_GEOGRAPHY;


    public static final Level LEVEL_CUSTOMER_ROW_NUMBER;
    public static final Level LEVEL_CUSTOMER_CUSTOMER_KEY;
    public static final Level LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY;
    public static final Level LEVEL_CUSTOMER_FIRST_NAME;
    public static final Level LEVEL_CUSTOMER_MIDDLE_NAME;
    public static final Level LEVEL_CUSTOMER_LAST_NAME;
    public static final Level LEVEL_CUSTOMER_NAME_STYLE;
    public static final Level LEVEL_CUSTOMER_BIRTH_DATE;
    public static final Level LEVEL_CUSTOMER_MARITAL_STATUS;
    public static final Level LEVEL_CUSTOMER_SUFFIX;
    public static final Level LEVEL_CUSTOMER_GENDER;
    public static final Level LEVEL_CUSTOMER_EMAIL_ADDRESS;
    public static final Level LEVEL_CUSTOMER_YARLY_INCOME;
    public static final Level LEVEL_CUSTOMER_TOTAL_CHILDREN;
    public static final Level LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME;
    public static final Level LEVEL_CUSTOMER_ENGLISH_EDUCATION;
    public static final Level LEVEL_CUSTOMER_SPANISH_EDUCATION;
    public static final Level LEVEL_CUSTOMER_FRENCH_EDUCATION;
    public static final Level LEVEL_CUSTOMER_ENGLISH_OCCUPATION;
    public static final Level LEVEL_CUSTOMER_SPANISH_OCCUPATION;
    public static final Level LEVEL_CUSTOMER_FRENCH_OCCUPATION;
    public static final Level LEVEL_CUSTOMER_HOUSE_OWNER_FLAG;
    public static final Level LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG;
    public static final Level LEVEL_CUSTOMER_ADDRESS_LINE1;
    public static final Level LEVEL_CUSTOMER_ADDRESS_LINE2;
    public static final Level LEVEL_CUSTOMER_PHONE;
    public static final Level LEVEL_CUSTOMER_DATE_FIRST_PURCHASE;
    public static final Level LEVEL_CUSTOMER_COMMUTE_DISTANCE;

    public static final Level LEVEL_EMPLOYEE_ROW_NUMBER;
    public static final Level LEVEL_EMPLOYEE_EMPLOYEE_KEY;
    public static final Level LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY;
    public static final Level LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY;
    public static final Level LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY;
    public static final Level LEVEL_EMPLOYEE_SALES_TERRITORY_KEY;
    public static final Level LEVEL_EMPLOYEE_FIRST_NAME;
    public static final Level LEVEL_EMPLOYEE_LAST_NAME;
    public static final Level LEVEL_EMPLOYEE_MIDDLE_NAME;
    public static final Level LEVEL_EMPLOYEE_NAME_STYLE;
    public static final Level LEVEL_EMPLOYEE_TITLE;
    public static final Level LEVEL_EMPLOYEE_HIRE_DATE;
    public static final Level LEVEL_EMPLOYEE_BIRTH_DATE;
    public static final Level LEVEL_EMPLOYEE_LOGIN_ID;
    public static final Level LEVEL_EMPLOYEE_EMAIL_ADDRESS;
    public static final Level LEVEL_EMPLOYEE_PHONE;
    public static final Level LEVEL_EMPLOYEE_MARITAL_STATUS;
    public static final Level LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME;
    public static final Level LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE;
    public static final Level LEVEL_EMPLOYEE_SALARIED_FLAG;
    public static final Level LEVEL_EMPLOYEE_GENDER;
    public static final Level LEVEL_EMPLOYEE_PAY_FREQUENCY;
    public static final Level LEVEL_EMPLOYEE_BASE_RATE;
    public static final Level LEVEL_EMPLOYEE_VACATION_HOURS;
    public static final Level LEVEL_EMPLOYEE_SICK_LEAVE_HOURS;
    public static final Level LEVEL_EMPLOYEE_CURRENT_FLAG;
    public static final Level LEVEL_EMPLOYEE_SALES_PERSONE_FLAG;
    public static final Level LEVEL_EMPLOYEE_DEPARTAMENT_NAME;
    public static final Level LEVEL_EMPLOYEE_START_DATE;
    public static final Level LEVEL_EMPLOYEE_END_DATE;
    public static final Level LEVEL_EMPLOYEE_STATUS;

    public static final Level LEVEL_GEOGRAPHY_ROW_NUMBER;
    public static final Level LEVEL_GEOGRAPHY_GEOGRAPHY_KEY;
    public static final Level LEVEL_GEOGRAPHY_CITY;
    public static final Level LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE;
    public static final Level LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME;
    public static final Level LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE;
    public static final Level LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME;
    public static final Level LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME;
    public static final Level LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME;
    public static final Level LEVEL_GEOGRAPHY_POSTAL_CODE;
    public static final Level LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY;

    public static final Level LEVEL_STORE_ROW_NUMBER;
    public static final Level LEVEL_STORE_STORE_KEY;
    public static final Level LEVEL_STORE_GEOGRAPHY_KEY;
    public static final Level LEVEL_STORE_STORE_NAME;
    public static final Level LEVEL_STORE_NUMBER_OF_EMPLOYEES;
    public static final Level LEVEL_STORE_SALES;

    public static final Level LEVEL_TIME_ROW_NUMBER;
    public static final Level LEVEL_TIME_TIME_KEY;
    public static final Level LEVEL_TIME_FULL_DATE_ALTERNATE_KEY;
    public static final Level LEVEL_TIME_DAY_NUMBER_OF_WEEK;
    public static final Level LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK;
    public static final Level LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK;
    public static final Level LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK;
    public static final Level LEVEL_TIME_DAY_NUMBER_OF_MONTH;
    public static final Level LEVEL_TIME_DAY_NUMBER_OF_YEAR;
    public static final Level LEVEL_TIME_WEEK_NUMBER_OF_YEAR;
    public static final Level LEVEL_TIME_ENGLISH_MONTH_NAME;
    public static final Level LEVEL_TIME_SPANISH_MONTH_NAME;
    public static final Level LEVEL_TIME_FRENCH_MONTH_NAME;
    public static final Level LEVEL_TIME_MONTH_NUMBER_OF_YEAR;
    public static final Level LEVEL_TIME_CALENDAR_QUARTER;
    public static final Level LEVEL_TIME_CALENDAR_YEAR;
    public static final Level LEVEL_TIME_CALENDAR_SEMESTER;
    public static final Level LEVEL_TIME_FISCAL_QUARTER;
    public static final Level LEVEL_TIME_FISCAL_YEAR;
    public static final Level LEVEL_TIME_FISCAL_SEMESTER;

    public static final Level LEVEL_PRODUCT_ROW_NUMBER;
    public static final Level LEVEL_PRODUCT_PRODUCT_KEY;
    public static final Level LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY;
    public static final Level LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY;
    public static final Level LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE;
    public static final Level LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE;
    public static final Level LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME;
    public static final Level LEVEL_PRODUCT_SPANISH_PRODUCT_NAME;
    public static final Level LEVEL_PRODUCT_FRENCH_PRODUCT_NAME;
    public static final Level LEVEL_PRODUCT_STANDART_COST;
    public static final Level LEVEL_PRODUCT_FINISH_GOODS_FLAG;
    public static final Level LEVEL_PRODUCT_COLOR;
    public static final Level LEVEL_PRODUCT_SAFETY_STOCK_LEVEL;
    public static final Level LEVEL_PRODUCT_REORDER_POINT;
    public static final Level LEVEL_PRODUCT_LIST_PRICE;
    public static final Level LEVEL_PRODUCT_SIZE;
    public static final Level LEVEL_PRODUCT_SIZE_RANGE;
    public static final Level LEVEL_PRODUCT_WEIGHT;
    public static final Level LEVEL_PRODUCT_DAYS_TO_MANUFACTURE;
    public static final Level LEVEL_PRODUCT_PRODUCT_LINE;
    public static final Level LEVEL_PRODUCT_DEALER_PRICE;
    public static final Level LEVEL_PRODUCT_CLASS;
    public static final Level LEVEL_PRODUCT_STYLE;
    public static final Level LEVEL_PRODUCT_MODEL_NAME;
    public static final Level LEVEL_PRODUCT_ENGLISH_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_FRENCH_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_CHINESE_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_ARABIC_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_HEBREW_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_THAI_DESCRIPTION;
    public static final Level LEVEL_PRODUCT_START_DATE;
    public static final Level LEVEL_PRODUCT_END_DATE;
    public static final Level LEVEL_PRODUCT_STATUS;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY;

    public static final Level LEVEL_PRODUCT_CATEGORY_ROW_NUMBER;
    public static final Level LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY;
    public static final Level LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY;
    public static final Level LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME;
    public static final Level LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME;
    public static final Level LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME;

    public static final Level LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME;
    public static final Level LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY;


    public static final PhysicalCube CUBE;

    public static final SumMeasure MEASURE_FACT_INTERNET_SALES;

    public static final Catalog CATALOG;

    private static final String introBody = """
            Data cubes are the most important objects in OLAP. Cubes provide access to data related to a specific topic, which corresponds to the cube's name. Within the catalog, each data cube must have a unique name.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table is named `Fact` and contains two columns: `KEY` and `VALUE`. The KEY column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery, as it directly references the physical table `Fact`. The query element is not visible to users accessing the cube through the XMLA API, such as Daanse Dashboard, Power BI, or Excel.
            """;

    private static final String cubeBody = """
            The Cube CSDLBI 1.0.
            """;
    static {
        COLUMN_ROW_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_FACT.setName("RowNumber");
        COLUMN_ROW_NUMBER_FACT.setId("_column_fact_row_number");
        COLUMN_ROW_NUMBER_FACT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_KEY_FACT.setName("ProductKey");
        COLUMN_PRODUCT_KEY_FACT.setId("_column_fact_Product_key");
        COLUMN_PRODUCT_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_ORDER_DATE_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ORDER_DATE_KEY_FACT.setName("OrderDateKey");
        COLUMN_ORDER_DATE_KEY_FACT.setId("_column_fact_order_date_key");
        COLUMN_ORDER_DATE_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_DUE_DATE_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DUE_DATE_KEY_FACT.setName("DueDateKey");
        COLUMN_DUE_DATE_KEY_FACT.setId("_column_fact_due_date_key");
        COLUMN_DUE_DATE_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_SHIP_DATE_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SHIP_DATE_KEY_FACT.setName("ShipDateKey");
        COLUMN_SHIP_DATE_KEY_FACT.setId("_column_fact_ship_date_key");
        COLUMN_SHIP_DATE_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_KEY_FACT.setName("CustomerKey");
        COLUMN_CUSTOMER_KEY_FACT.setId("_column_fact_customer_key");
        COLUMN_CUSTOMER_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_PROMOTION_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PROMOTION_KEY_FACT.setName("PromotionKey");
        COLUMN_PROMOTION_KEY_FACT.setId("_column_fact_promotion_key");
        COLUMN_PROMOTION_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_CURENCY_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURENCY_KEY_FACT.setName("CurrencyKey");
        COLUMN_CURENCY_KEY_FACT.setId("_column_fact_currency_key");
        COLUMN_CURENCY_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_SALES_TERITORY_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_TERITORY_KEY_FACT.setName("SalesTerritoryKey");
        COLUMN_SALES_TERITORY_KEY_FACT.setId("_column_fact_sales_territory_key");
        COLUMN_SALES_TERITORY_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_SALES_ORDER_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_ORDER_NUMBER_FACT.setName("SalesOrderNumber");
        COLUMN_SALES_ORDER_NUMBER_FACT.setId("_column_fact_sales_order_number");
        COLUMN_SALES_ORDER_NUMBER_FACT.setType(ColumnType.INTEGER);

        COLUMN_SALES_ORDER_LINE_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_ORDER_LINE_NUMBER_FACT.setName("SalesOrderLineNumber");
        COLUMN_SALES_ORDER_LINE_NUMBER_FACT.setId("_column_fact_sales_order_line_number");
        COLUMN_SALES_ORDER_LINE_NUMBER_FACT.setType(ColumnType.VARCHAR);

        COLUMN_REVISION_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REVISION_NUMBER_FACT.setName("RevisionNumber");
        COLUMN_REVISION_NUMBER_FACT.setId("_column_fact_revision_number");
        COLUMN_REVISION_NUMBER_FACT.setType(ColumnType.INTEGER);

        COLUMN_ORDER_QUANTITY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ORDER_QUANTITY_FACT.setName("OrderQuantity");
        COLUMN_ORDER_QUANTITY_FACT.setId("_column_fact_order_ouantity");
        COLUMN_ORDER_QUANTITY_FACT.setType(ColumnType.INTEGER);

        COLUMN_UNIT_PRICE_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_PRICE_FACT.setName("UnitPrice");
        COLUMN_UNIT_PRICE_FACT.setId("_column_fact_unit_price");
        COLUMN_UNIT_PRICE_FACT.setType(ColumnType.INTEGER);

        COLUMN_EXTENDED_AMOUNT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EXTENDED_AMOUNT_FACT.setName("ExtendedAmount");
        COLUMN_EXTENDED_AMOUNT_FACT.setId("_column_fact_extended_amount");
        COLUMN_EXTENDED_AMOUNT_FACT.setType(ColumnType.DECIMAL);
        COLUMN_EXTENDED_AMOUNT_FACT.setCharOctetLength(19);
        COLUMN_EXTENDED_AMOUNT_FACT.setDecimalDigits(4);

        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT.setName("UnitPriceDiscountPct");
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT.setId("_column_fact_UnitPriceDiscountPct");
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT.setCharOctetLength(19);
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT.setDecimalDigits(4);

        COLUMN_DISCOUNT_AMOUNT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DISCOUNT_AMOUNT_FACT.setName("DiscountAmount");
        COLUMN_DISCOUNT_AMOUNT_FACT.setId("_column_fact_DiscountAmount");
        COLUMN_DISCOUNT_AMOUNT_FACT.setType(ColumnType.DOUBLE);

        COLUMN_PRODUCT_STANDART_COST_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_STANDART_COST_FACT.setName("ProductStandardCost");
        COLUMN_PRODUCT_STANDART_COST_FACT.setId("_column_fact_product_standard_cost");
        COLUMN_PRODUCT_STANDART_COST_FACT.setType(ColumnType.DOUBLE);

        COLUMN_TOTAL_PRODUCT_COST_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTAL_PRODUCT_COST_FACT.setName("TotalProductCost");
        COLUMN_TOTAL_PRODUCT_COST_FACT.setId("_column_fact_total_product_cost");
        COLUMN_TOTAL_PRODUCT_COST_FACT.setType(ColumnType.DECIMAL);
        COLUMN_TOTAL_PRODUCT_COST_FACT.setCharOctetLength(19);
        COLUMN_TOTAL_PRODUCT_COST_FACT.setDecimalDigits(4);

        COLUMN_SALES_AMOUNT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_AMOUNT_FACT.setName("SalesAmount");
        COLUMN_SALES_AMOUNT_FACT.setId("_column_fact_sales_amount");
        COLUMN_SALES_AMOUNT_FACT.setType(ColumnType.DECIMAL);
        COLUMN_SALES_AMOUNT_FACT.setCharOctetLength(19);
        COLUMN_SALES_AMOUNT_FACT.setDecimalDigits(4);

        COLUMN_TAX_AMT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TAX_AMT_FACT.setName("TaxAmt");
        COLUMN_TAX_AMT_FACT.setId("_column_fact_tax_amt");
        COLUMN_TAX_AMT_FACT.setType(ColumnType.DECIMAL);
        COLUMN_TAX_AMT_FACT.setCharOctetLength(19);
        COLUMN_TAX_AMT_FACT.setDecimalDigits(4);

        COLUMN_FREIGHT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FREIGHT_FACT.setName("Freight");
        COLUMN_FREIGHT_FACT.setId("_column_fact_freight");
        COLUMN_FREIGHT_FACT.setType(ColumnType.DECIMAL);
        COLUMN_FREIGHT_FACT.setCharOctetLength(19);
        COLUMN_FREIGHT_FACT.setDecimalDigits(4);

        COLUMN_CARRIER_TRACKING_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CARRIER_TRACKING_NUMBER_FACT.setName("CarrierTrackingNumber");
        COLUMN_CARRIER_TRACKING_NUMBER_FACT.setId("_column_fact_carrier_tracking_number");
        COLUMN_CARRIER_TRACKING_NUMBER_FACT.setType(ColumnType.VARCHAR);

        COLUMN_CUSTOMER_PO_NUMBER_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_PO_NUMBER_FACT.setName("CustomerPONumber");
        COLUMN_CUSTOMER_PO_NUMBER_FACT.setId("_column_fact_customer_po_number");
        COLUMN_CUSTOMER_PO_NUMBER_FACT.setType(ColumnType.VARCHAR);

        COLUMN_EMPLOYEE_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_KEY_FACT.setName("EmployeeKey");
        COLUMN_EMPLOYEE_KEY_FACT.setId("_column_fact_employee_key");
        COLUMN_EMPLOYEE_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_BILLING_CUSTOMER_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_BILLING_CUSTOMER_KEY_FACT.setName("BillingCustomerKey");
        COLUMN_BILLING_CUSTOMER_KEY_FACT.setId("_column_fact_billing_customer_key");
        COLUMN_BILLING_CUSTOMER_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_STORE_KEY_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_KEY_FACT.setName("StoreKey");
        COLUMN_STORE_KEY_FACT.setId("_column_fact_store_key");
        COLUMN_STORE_KEY_FACT.setType(ColumnType.INTEGER);

        COLUMN_TOTAL_SALES_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTAL_SALES_FACT.setName("TotalSales");
        COLUMN_TOTAL_SALES_FACT.setId("_column_fact_total_sales");
        COLUMN_TOTAL_SALES_FACT.setType(ColumnType.INTEGER);

        COLUMN_ROW_NUMBER_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_CUSTOMER.setName("RowNumber");
        COLUMN_ROW_NUMBER_CUSTOMER.setId("_column_customer_row_number");
        COLUMN_ROW_NUMBER_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_KEY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_KEY_CUSTOMER.setName("CustomerKey");
        COLUMN_CUSTOMER_KEY_CUSTOMER.setId("_column_customer_customer_Key");
        COLUMN_CUSTOMER_KEY_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_GEOGRAPHY_KEY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOGRAPHY_KEY_CUSTOMER.setName("GeographyKey");
        COLUMN_GEOGRAPHY_KEY_CUSTOMER.setId("_column_customer_geography_key");
        COLUMN_GEOGRAPHY_KEY_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER.setName("CustomerAlternateKey");
        COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER.setId("_column_customer_customer_alternate_key");
        COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_FIRST_NAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FIRST_NAME_CUSTOMER.setName("FirstName");
        COLUMN_FIRST_NAME_CUSTOMER.setId("_column_customer_first_name");
        COLUMN_FIRST_NAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_MIDDLE_NAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MIDDLE_NAME_CUSTOMER.setName("MiddleName");
        COLUMN_MIDDLE_NAME_CUSTOMER.setId("_column_customer_middle_name");
        COLUMN_MIDDLE_NAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_LAST_NAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LAST_NAME_CUSTOMER.setName("LastName");
        COLUMN_LAST_NAME_CUSTOMER.setId("_column_customer_last_name");
        COLUMN_LAST_NAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_NAME_STYLE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NAME_STYLE_CUSTOMER.setName("NameStyle");
        COLUMN_NAME_STYLE_CUSTOMER.setId("_column_customer_name_style");
        COLUMN_NAME_STYLE_CUSTOMER.setType(ColumnType.BOOLEAN);

        COLUMN_BIRTH_DATE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_BIRTH_DATE_CUSTOMER.setName("BirthDate");
        COLUMN_BIRTH_DATE_CUSTOMER.setId("_column_customer_birth_date");
        COLUMN_BIRTH_DATE_CUSTOMER.setType(ColumnType.DATE);

        COLUMN_MARITAL_STATUS_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MARITAL_STATUS_CUSTOMER.setName("MaritalStatus");
        COLUMN_MARITAL_STATUS_CUSTOMER.setId("_column_customer_marital_status");
        COLUMN_MARITAL_STATUS_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_SUFFIX_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUFFIX_CUSTOMER.setName("Suffix");
        COLUMN_SUFFIX_CUSTOMER.setId("_column_customer_suffix");
        COLUMN_SUFFIX_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_GENDER_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GENDER_CUSTOMER.setName("Gender");
        COLUMN_GENDER_CUSTOMER.setId("_column_customer_gender");
        COLUMN_GENDER_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_EMAIL_ADDRESS_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMAIL_ADDRESS_CUSTOMER.setName("EmailAddress");
        COLUMN_EMAIL_ADDRESS_CUSTOMER.setId("_column_customer_email_address");
        COLUMN_EMAIL_ADDRESS_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_YARLY_INCOME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_YARLY_INCOME_CUSTOMER.setName("YearlyIncome");
        COLUMN_YARLY_INCOME_CUSTOMER.setId("_column_customer_yearly_income");
        COLUMN_YARLY_INCOME_CUSTOMER.setType(ColumnType.DECIMAL);
        COLUMN_YARLY_INCOME_CUSTOMER.setColumnSize(10);
        COLUMN_YARLY_INCOME_CUSTOMER.setDecimalDigits(2);

        COLUMN_TOTAL_CHILDREN_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setName("TotalChildren");
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setId("_column_customer_total_children");
        COLUMN_TOTAL_CHILDREN_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER.setName("NumberChildrenAtHome");
        COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER.setId("_column_customer_number_children_at_home");
        COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_ENGLISH_EDUCATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_EDUCATION_CUSTOMER.setName("EnglishEducation");
        COLUMN_ENGLISH_EDUCATION_CUSTOMER.setId("_column_customer_english_education");
        COLUMN_ENGLISH_EDUCATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_EDUCATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_EDUCATION_CUSTOMER.setName("SpanishEducation");
        COLUMN_SPANISH_EDUCATION_CUSTOMER.setId("_column_customer_spanish_education");
        COLUMN_SPANISH_EDUCATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_EDUCATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_EDUCATION_CUSTOMER.setName("FrenchEducation");
        COLUMN_FRENCH_EDUCATION_CUSTOMER.setId("_column_customer_french_education");
        COLUMN_FRENCH_EDUCATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_ENGLISH_OCCUPATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_OCCUPATION_CUSTOMER.setName("EnglishOccupation");
        COLUMN_ENGLISH_OCCUPATION_CUSTOMER.setId("_column_customer_english_occupation");
        COLUMN_ENGLISH_OCCUPATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_OCCUPATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_OCCUPATION_CUSTOMER.setName("SpanishOccupation");
        COLUMN_SPANISH_OCCUPATION_CUSTOMER.setId("_column_customer_spanish_occupation");
        COLUMN_SPANISH_OCCUPATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_OCCUPATION_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_OCCUPATION_CUSTOMER.setName("FrenchOccupation");
        COLUMN_FRENCH_OCCUPATION_CUSTOMER.setId("_column_customer_french_occupation");
        COLUMN_FRENCH_OCCUPATION_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_HOUSE_OWNER_FLAG_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_HOUSE_OWNER_FLAG_CUSTOMER.setName("HouseOwnerFlag");
        COLUMN_HOUSE_OWNER_FLAG_CUSTOMER.setId("_column_customer_house_owner_flag");
        COLUMN_HOUSE_OWNER_FLAG_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER.setName("NumberCarsOwned");
        COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER.setId("_column_customer_number_cars_owned");
        COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_ADDRESS_LINE1_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ADDRESS_LINE1_CUSTOMER.setName("AddressLine1");
        COLUMN_ADDRESS_LINE1_CUSTOMER.setId("_column_customer_address_line1");
        COLUMN_ADDRESS_LINE1_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_ADDRESS_LINE2_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ADDRESS_LINE2_CUSTOMER.setName("AddressLine2");
        COLUMN_ADDRESS_LINE2_CUSTOMER.setId("_column_customer_address_line2");
        COLUMN_ADDRESS_LINE2_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_PHONE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PHONE_CUSTOMER.setName("Phone");
        COLUMN_PHONE_CUSTOMER.setId("_column_customer_phone");
        COLUMN_PHONE_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_DATE_FIRST_PURCHASE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DATE_FIRST_PURCHASE_CUSTOMER.setName("DateFirstPurchase");
        COLUMN_DATE_FIRST_PURCHASE_CUSTOMER.setId("_column_customer_date_first_purchase");
        COLUMN_DATE_FIRST_PURCHASE_CUSTOMER.setType(ColumnType.DATE);

        COLUMN_COMMUTE_DISTANCE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COMMUTE_DISTANCE_CUSTOMER.setName("CommuteDistance");
        COLUMN_COMMUTE_DISTANCE_CUSTOMER.setId("_column_customer_commute_distance");
        COLUMN_COMMUTE_DISTANCE_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_EMPLOYEE.setName("RowNumber");
        COLUMN_ROW_NUMBER_EMPLOYEE.setId("_column_employee_row_number");
        COLUMN_ROW_NUMBER_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_EMPLOYEE_KEY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_KEY_EMPLOYEE.setName("EmployeeKey");
        COLUMN_EMPLOYEE_KEY_EMPLOYEE.setId("_column_employee_employee_key");
        COLUMN_EMPLOYEE_KEY_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE.setName("ParentEmployeeKey");
        COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE.setId("_column_employee_parent_employee_key");
        COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setName("EmployeeNationalIDAlternateKey");
        COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setId("_column_employee_employee_national_id_alternate_key");
        COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setName("ParentEmployeeNationalIDAlternateKey");
        COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setId("_column_employee_parent_employee_national_id_alternate_key");
        COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_SALES_TERRITORY_KEY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_TERRITORY_KEY_EMPLOYEE.setName("SalesTerritoryKey");
        COLUMN_SALES_TERRITORY_KEY_EMPLOYEE.setId("_column_employee_sales_territory_key");
        COLUMN_SALES_TERRITORY_KEY_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_FIRST_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FIRST_NAME_EMPLOYEE.setName("FirstName");
        COLUMN_FIRST_NAME_EMPLOYEE.setId("_column_employee_first_name");
        COLUMN_FIRST_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_LAST_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LAST_NAME_EMPLOYEE.setName("LastName");
        COLUMN_LAST_NAME_EMPLOYEE.setId("_column_employee_last_name");
        COLUMN_LAST_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_MIDDLE_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MIDDLE_NAME_EMPLOYEE.setName("MiddleName");
        COLUMN_MIDDLE_NAME_EMPLOYEE.setId("_column_employee_middle_name");
        COLUMN_MIDDLE_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_NAME_STYLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NAME_STYLE_EMPLOYEE.setName("NameStyle");
        COLUMN_NAME_STYLE_EMPLOYEE.setId("_column_employee_name_style");
        COLUMN_NAME_STYLE_EMPLOYEE.setType(ColumnType.BOOLEAN);

        COLUMN_TITLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TITLE_EMPLOYEE.setName("Title");
        COLUMN_TITLE_EMPLOYEE.setId("_column_employee_title");
        COLUMN_TITLE_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_HIRE_DATE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_HIRE_DATE_EMPLOYEE.setName("HireDate");
        COLUMN_HIRE_DATE_EMPLOYEE.setId("_column_employee_hire_date");
        COLUMN_HIRE_DATE_EMPLOYEE.setType(ColumnType.DATE);

        COLUMN_BIRTH_DATE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_BIRTH_DATE_EMPLOYEE.setName("BirthDate");
        COLUMN_BIRTH_DATE_EMPLOYEE.setId("_column_employee_birth_date");
        COLUMN_BIRTH_DATE_EMPLOYEE.setType(ColumnType.DATE);

        COLUMN_LOGIN_ID_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LOGIN_ID_EMPLOYEE.setName("LoginID");
        COLUMN_LOGIN_ID_EMPLOYEE.setId("_column_employee_login_id");
        COLUMN_LOGIN_ID_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_EMAIL_ADDRESS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMAIL_ADDRESS_EMPLOYEE.setName("EmailAddress");
        COLUMN_EMAIL_ADDRESS_EMPLOYEE.setId("_column_employee_email_address");
        COLUMN_EMAIL_ADDRESS_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_PHONE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PHONE_EMPLOYEE.setName("Phone");
        COLUMN_PHONE_EMPLOYEE.setId("_column_employee_phone");
        COLUMN_PHONE_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_MARITAL_STATUS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MARITAL_STATUS_EMPLOYEE.setName("MaritalStatus");
        COLUMN_MARITAL_STATUS_EMPLOYEE.setId("_column_employee_marital_status");
        COLUMN_MARITAL_STATUS_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE.setName("EmergencyContactName");
        COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE.setId("_column_employee_emergency_contact_name");
        COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE.setName("EmergencyContactPhone");
        COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE.setId("_column_employee_emergency_contact_phone");
        COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_SALARIED_FLAG_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALARIED_FLAG_EMPLOYEE.setName("SalariedFlag");
        COLUMN_SALARIED_FLAG_EMPLOYEE.setId("_column_employee_salaried_flag");
        COLUMN_SALARIED_FLAG_EMPLOYEE.setType(ColumnType.BOOLEAN);

        COLUMN_GENDER_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GENDER_EMPLOYEE.setName("Gender");
        COLUMN_GENDER_EMPLOYEE.setId("_column_employee_gender");
        COLUMN_GENDER_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_PAY_FREQUENCY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PAY_FREQUENCY_EMPLOYEE.setName("PayFrequency");
        COLUMN_PAY_FREQUENCY_EMPLOYEE.setId("_column_employee_pay_frequency");
        COLUMN_PAY_FREQUENCY_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_BASE_RATE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_BASE_RATE_EMPLOYEE.setName("BaseRate");
        COLUMN_BASE_RATE_EMPLOYEE.setId("_column_employee_base_rate");
        COLUMN_BASE_RATE_EMPLOYEE.setType(ColumnType.DECIMAL);
        COLUMN_BASE_RATE_EMPLOYEE.setColumnSize(19);
        COLUMN_BASE_RATE_EMPLOYEE.setDecimalDigits(2);

        COLUMN_VACATION_HOURS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_VACATION_HOURS_EMPLOYEE.setName("VacationHours");
        COLUMN_VACATION_HOURS_EMPLOYEE.setId("_column_employee_vacation_hours");
        COLUMN_VACATION_HOURS_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_SICK_LEAVE_HOURS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SICK_LEAVE_HOURS_EMPLOYEE.setName("SickLeaveHours");
        COLUMN_SICK_LEAVE_HOURS_EMPLOYEE.setId("_column_employee_sick_leave_hours");
        COLUMN_SICK_LEAVE_HOURS_EMPLOYEE.setType(ColumnType.INTEGER);

        COLUMN_CURRENT_FLAG_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURRENT_FLAG_EMPLOYEE.setName("CurrentFlag");
        COLUMN_CURRENT_FLAG_EMPLOYEE.setId("_column_employee_current_flag");
        COLUMN_CURRENT_FLAG_EMPLOYEE.setType(ColumnType.BOOLEAN);

        COLUMN_SALES_PERSONE_FLAG_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_PERSONE_FLAG_EMPLOYEE.setName("SalesPersonFlag");
        COLUMN_SALES_PERSONE_FLAG_EMPLOYEE.setId("_column_employee_sales_person_flag");
        COLUMN_SALES_PERSONE_FLAG_EMPLOYEE.setType(ColumnType.BOOLEAN);

        COLUMN_DEPARTAMENT_NAME_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEPARTAMENT_NAME_EMPLOYEE.setName("DepartmentName");
        COLUMN_DEPARTAMENT_NAME_EMPLOYEE.setId("_column_employee_department_name");
        COLUMN_DEPARTAMENT_NAME_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_START_DATE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_START_DATE_EMPLOYEE.setName("StartDate");
        COLUMN_START_DATE_EMPLOYEE.setId("_column_employee_start_date");
        COLUMN_START_DATE_EMPLOYEE.setType(ColumnType.DATE);

        COLUMN_END_DATE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_END_DATE_EMPLOYEE.setName("EndDate");
        COLUMN_END_DATE_EMPLOYEE.setId("_column_employee_end_date");
        COLUMN_END_DATE_EMPLOYEE.setType(ColumnType.DATE);

        COLUMN_STATUS_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATUS_EMPLOYEE.setName("Status");
        COLUMN_STATUS_EMPLOYEE.setId("_column_employee_status");
        COLUMN_STATUS_EMPLOYEE.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_GEOGRAPHY.setName("RowNumber");
        COLUMN_ROW_NUMBER_GEOGRAPHY.setId("_column_geography_row_number");
        COLUMN_ROW_NUMBER_GEOGRAPHY.setType(ColumnType.INTEGER);

        COLUMN_GEOGRAPHY_KEY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOGRAPHY_KEY_GEOGRAPHY.setName("GeographyKey");
        COLUMN_GEOGRAPHY_KEY_GEOGRAPHY.setId("_column_geography_geography_key");
        COLUMN_GEOGRAPHY_KEY_GEOGRAPHY.setType(ColumnType.INTEGER);

        COLUMN_CITY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CITY_GEOGRAPHY.setName("City");
        COLUMN_CITY_GEOGRAPHY.setId("_column_geography_city");
        COLUMN_CITY_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY.setName("StateProvinceCode");
        COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY.setId("_column_geography_state_province_code");
        COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY.setName("StateProvinceName");
        COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY.setId("_column_geography_state_province_name");
        COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY.setName("CountryRegionCode");
        COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY.setId("_column_geography_country_region_code");
        COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY.setName("EnglishCountryRegionName");
        COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY.setId("_column_geography_english_country_region_name");
        COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY.setName("SpanishCountryRegionName");
        COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY.setId("_column_geography_spanish_country_region_name");
        COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY.setName("FrenchCountryRegionName");
        COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY.setId("_column_geography_french_country_region_name");
        COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_POSTAL_CODE_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSTAL_CODE_GEOGRAPHY.setName("PostalCode");
        COLUMN_POSTAL_CODE_GEOGRAPHY.setId("_column_geography_postal_code");
        COLUMN_POSTAL_CODE_GEOGRAPHY.setType(ColumnType.VARCHAR);

        COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY.setName("SalesTerritoryKey");
        COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY.setId("_column_geography_sales_territory_key");
        COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY.setType(ColumnType.INTEGER);

        COLUMN_ROW_NUMBER_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_PRODUCT.setName("RowNumber");
        COLUMN_ROW_NUMBER_PRODUCT.setId("_column_product_row_number");
        COLUMN_ROW_NUMBER_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_KEY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_KEY_PRODUCT.setName("ProductKey");
        COLUMN_PRODUCT_KEY_PRODUCT.setId("_column_product_product_Key");
        COLUMN_PRODUCT_KEY_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT.setName("ProductAlternateKey");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT.setId("_column_product_product_alternate_key");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT.setId("_column_product_product_subcategory_key");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT.setName("WeightUnitMeasureCode");
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT.setId("_column_product_weight_unit_measure_code");
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT.setName("SizeUnitMeasureCode");
        COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT.setId("_column_product_size_unit_measure_code");
        COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT.setName("EnglishProductName");
        COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT.setId("_column_product_english_product_name");
        COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_PRODUCT_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_PRODUCT_NAME_PRODUCT.setName("SpanishProductName");
        COLUMN_SPANISH_PRODUCT_NAME_PRODUCT.setId("_column_product_spanish_product_name");
        COLUMN_SPANISH_PRODUCT_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_PRODUCT_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_PRODUCT_NAME_PRODUCT.setName("FrenchProductName");
        COLUMN_FRENCH_PRODUCT_NAME_PRODUCT.setId("_column_product_french_product_name");
        COLUMN_FRENCH_PRODUCT_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_STANDART_COST_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STANDART_COST_PRODUCT.setName("StandardCost");
        COLUMN_STANDART_COST_PRODUCT.setId("_column_product_standard_cost");
        COLUMN_STANDART_COST_PRODUCT.setType(ColumnType.DECIMAL);
        COLUMN_STANDART_COST_PRODUCT.setCharOctetLength(19);
        COLUMN_STANDART_COST_PRODUCT.setDecimalDigits(4);

        COLUMN_FINISH_GOODS_FLAG_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FINISH_GOODS_FLAG_PRODUCT.setName("FinishedGoodsFlag");
        COLUMN_FINISH_GOODS_FLAG_PRODUCT.setId("_column_product_finished_goods_flag");
        COLUMN_FINISH_GOODS_FLAG_PRODUCT.setType(ColumnType.BOOLEAN);

        COLUMN_COLOR_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COLOR_PRODUCT.setName("Color");
        COLUMN_COLOR_PRODUCT.setId("_column_product_color");
        COLUMN_COLOR_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_SAFETY_STOCK_LEVEL_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SAFETY_STOCK_LEVEL_PRODUCT.setName("SafetyStockLevel");
        COLUMN_SAFETY_STOCK_LEVEL_PRODUCT.setId("_column_product_safety_stock_level");
        COLUMN_SAFETY_STOCK_LEVEL_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_REORDER_POINT_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REORDER_POINT_PRODUCT.setName("ReorderPoint");
        COLUMN_REORDER_POINT_PRODUCT.setId("_column_product_reorder_point");
        COLUMN_REORDER_POINT_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_LIST_PRICE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LIST_PRICE_PRODUCT.setName("ListPrice");
        COLUMN_LIST_PRICE_PRODUCT.setId("_column_product_list_price");
        COLUMN_LIST_PRICE_PRODUCT.setType(ColumnType.DECIMAL);
        COLUMN_LIST_PRICE_PRODUCT.setCharOctetLength(19);
        COLUMN_LIST_PRICE_PRODUCT.setDecimalDigits(4);

        COLUMN_SIZE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_PRODUCT.setName("Size");
        COLUMN_SIZE_PRODUCT.setId("_column_product_size");
        COLUMN_SIZE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_SIZE_RANGE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_RANGE_PRODUCT.setName("SizeRange");
        COLUMN_SIZE_RANGE_PRODUCT.setId("_column_product_size_range");
        COLUMN_SIZE_RANGE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_WEIGHT_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEIGHT_PRODUCT.setName("Weight");
        COLUMN_WEIGHT_PRODUCT.setId("_column_product_weight");
        COLUMN_WEIGHT_PRODUCT.setType(ColumnType.DOUBLE);

        COLUMN_DAYS_TO_MANUFACTURE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAYS_TO_MANUFACTURE_PRODUCT.setName("DaysToManufacture");
        COLUMN_DAYS_TO_MANUFACTURE_PRODUCT.setId("_column_productd_days_to_manufacture");
        COLUMN_DAYS_TO_MANUFACTURE_PRODUCT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_LINE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_LINE_PRODUCT.setName("ProductLine");
        COLUMN_PRODUCT_LINE_PRODUCT.setId("_column_product_product_line");
        COLUMN_PRODUCT_LINE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_DEALER_PRICE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEALER_PRICE_PRODUCT.setName("DealerPrice");
        COLUMN_DEALER_PRICE_PRODUCT.setId("_column_product_dealer_price");
        COLUMN_DEALER_PRICE_PRODUCT.setType(ColumnType.DECIMAL);
        COLUMN_DEALER_PRICE_PRODUCT.setCharOctetLength(19);
        COLUMN_DEALER_PRICE_PRODUCT.setDecimalDigits(4);

        COLUMN_CLASS_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CLASS_PRODUCT.setName("Class");
        COLUMN_CLASS_PRODUCT.setId("_column_product_class");
        COLUMN_CLASS_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_STYLE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STYLE_PRODUCT.setName("Style");
        COLUMN_STYLE_PRODUCT.setId("_column_product_style");
        COLUMN_STYLE_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_MODEL_NAME_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MODEL_NAME_PRODUCT.setName("ModelName");
        COLUMN_MODEL_NAME_PRODUCT.setId("_column_product_model_name");
        COLUMN_MODEL_NAME_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_ENGLISH_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_DESCRIPTION_PRODUCT.setName("EnglishDescription");
        COLUMN_ENGLISH_DESCRIPTION_PRODUCT.setId("_column_product_english_description");
        COLUMN_ENGLISH_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_DESCRIPTION_PRODUCT.setName("FrenchDescription");
        COLUMN_FRENCH_DESCRIPTION_PRODUCT.setId("_column_product_french_description");
        COLUMN_FRENCH_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_CHINESE_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CHINESE_DESCRIPTION_PRODUCT.setName("ChineseDescription");
        COLUMN_CHINESE_DESCRIPTION_PRODUCT.setId("_column_product_chinese_description");
        COLUMN_CHINESE_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_ARABIC_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ARABIC_DESCRIPTION_PRODUCT.setName("ArabicDescription");
        COLUMN_ARABIC_DESCRIPTION_PRODUCT.setId("_column_product_arabic_description");
        COLUMN_ARABIC_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_HEBREW_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_HEBREW_DESCRIPTION_PRODUCT.setName("HebrewDescription");
        COLUMN_HEBREW_DESCRIPTION_PRODUCT.setId("_column_product_hebrew_description");
        COLUMN_HEBREW_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_THAI_DESCRIPTION_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_THAI_DESCRIPTION_PRODUCT.setName("ThaiDescription");
        COLUMN_THAI_DESCRIPTION_PRODUCT.setId("_column_product_thai_description");
        COLUMN_THAI_DESCRIPTION_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_START_DATE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_START_DATE_PRODUCT.setName("StartDate");
        COLUMN_START_DATE_PRODUCT.setId("_column_product_start_date");
        COLUMN_START_DATE_PRODUCT.setType(ColumnType.DATE);

        COLUMN_END_DATE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_END_DATE_PRODUCT.setName("EndDate");
        COLUMN_END_DATE_PRODUCT.setId("_column_product_end_date");
        COLUMN_END_DATE_PRODUCT.setType(ColumnType.DATE);

        COLUMN_STATUS_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATUS_PRODUCT.setName("Status");
        COLUMN_STATUS_PRODUCT.setId("_column_product_status");
        COLUMN_STATUS_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_SUBCATEGORY_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUBCATEGORY_PRODUCT.setName("Subcategory");
        COLUMN_SUBCATEGORY_PRODUCT.setId("_column_product_subcategory");
        COLUMN_SUBCATEGORY_PRODUCT.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_PRODUCT_CATEGORY.setName("RowNumber");
        COLUMN_ROW_NUMBER_PRODUCT_CATEGORY.setId("_column_product_category_row_number");
        COLUMN_ROW_NUMBER_PRODUCT_CATEGORY.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY.setName("ProductCategoryKey");
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY.setId("_column_product_category_product_category_key");
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY.setName("ProductCategoryAlternateKey");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY.setId("_column_product_category_product_category_alternate_key");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY.setType(ColumnType.INTEGER);

        COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setName("EnglishProductCategoryName");
        COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setId("_column_product_category_english_product_category_name");
        COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setName("SpanishProductCategoryName");
        COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setId("_column_product_category_spanish_product_category_name");
        COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setName("FrenchProductCategoryName");
        COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setId("_column_product_category_french_product_category_name");
        COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY.setName("RowNumber");
        COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_row_number");
        COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_product_subcategory_key");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY.setName("ProductSubcategoryAlternateKey");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_product_subcategory_alternate_key");
        COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY.setType(ColumnType.INTEGER);

        COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setName("EnglishProductSubcategoryName");
        COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_english_product_subcategory_name");
        COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setName("SpanishProductSubcategoryName");
        COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_spanish_product_subcategory_name");
        COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setName("FrenchProductSubcategoryName");
        COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_french_product_subcategory_name");
        COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY.setName("ProductCategoryKey");
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY.setId("_column_product_subcategory_product_category_key");
        COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY.setType(ColumnType.INTEGER);

        //"RowNumber","StoreKey","Geography_Key","StoreName","Number_of_Employees","Sales"
        //INTEGER,INTEGER,INTEGER,VARCHAR,INTEGER,DECIMAL(19.4)
        COLUMN_ROW_NUMBER_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_STORE.setName("RowNumber");
        COLUMN_ROW_NUMBER_STORE.setId("_column_store_row_number");
        COLUMN_ROW_NUMBER_STORE.setType(ColumnType.INTEGER);

        COLUMN_STORE_KEY_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_KEY_STORE.setName("StoreKey");
        COLUMN_STORE_KEY_STORE.setId("_column_store_store_key");
        COLUMN_STORE_KEY_STORE.setType(ColumnType.INTEGER);

        COLUMN_GEOGRAPHY_KEY_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOGRAPHY_KEY_STORE.setName("Geography_Key");
        COLUMN_GEOGRAPHY_KEY_STORE.setId("_column_store_geography_key");
        COLUMN_GEOGRAPHY_KEY_STORE.setType(ColumnType.INTEGER);

        COLUMN_STORE_NAME_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STORE_NAME_STORE.setName("StoreName");
        COLUMN_STORE_NAME_STORE.setId("_column_store_store_name");
        COLUMN_STORE_NAME_STORE.setType(ColumnType.VARCHAR);

        COLUMN_NUMBER_OF_EMPLOYEES_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NUMBER_OF_EMPLOYEES_STORE.setName("Number_of_Employees");
        COLUMN_NUMBER_OF_EMPLOYEES_STORE.setId("_column_store_number_of_employees");
        COLUMN_NUMBER_OF_EMPLOYEES_STORE.setType(ColumnType.INTEGER);

        COLUMN_SALES_STORE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_STORE.setName("Sales");
        COLUMN_SALES_STORE.setId("_column_store_sales");
        COLUMN_SALES_STORE.setType(ColumnType.DECIMAL);
        COLUMN_SALES_STORE.setColumnSize(19);
        COLUMN_SALES_STORE.setDecimalDigits(4);

        COLUMN_ROW_NUMBER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_TIME.setName("RowNumber");
        COLUMN_ROW_NUMBER_TIME.setId("_column_time_row_number");
        COLUMN_ROW_NUMBER_TIME.setType(ColumnType.INTEGER);

        COLUMN_TIME_KEY_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_KEY_TIME.setName("TimeKey");
        COLUMN_TIME_KEY_TIME.setId("_column_time_time_key");
        COLUMN_TIME_KEY_TIME.setType(ColumnType.INTEGER);

        COLUMN_FULL_DATE_ALTERNATE_KEY_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FULL_DATE_ALTERNATE_KEY_TIME.setName("FullDateAlternateKey");
        COLUMN_FULL_DATE_ALTERNATE_KEY_TIME.setId("_column_time_full_date_alternate_key");
        COLUMN_FULL_DATE_ALTERNATE_KEY_TIME.setType(ColumnType.DATE);

        COLUMN_DAY_NUMBER_OF_WEEK_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAY_NUMBER_OF_WEEK_TIME.setName("DayNumberOfWeek");
        COLUMN_DAY_NUMBER_OF_WEEK_TIME.setId("_column_time_day_number_of_week");
        COLUMN_DAY_NUMBER_OF_WEEK_TIME.setType(ColumnType.INTEGER);

        COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME.setName("EnglishDayNameOfWeek");
        COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME.setId("_column_time_english_day_name_of_week");
        COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME.setName("SpanishDayNameOfWeek");
        COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME.setId("_column_time_spanish_day_name_of_week");
        COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME.setName("FrenchDayNameOfWeek");
        COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME.setId("_column_time_french_day_name_of_week");
        COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME.setType(ColumnType.VARCHAR);

        COLUMN_DAY_NUMBER_OF_MONTH_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAY_NUMBER_OF_MONTH_TIME.setName("DayNumberOfMonth");
        COLUMN_DAY_NUMBER_OF_MONTH_TIME.setId("_column_time_day_number_of_month");
        COLUMN_DAY_NUMBER_OF_MONTH_TIME.setType(ColumnType.INTEGER);

        COLUMN_DAY_NUMBER_OF_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAY_NUMBER_OF_YEAR_TIME.setName("DayNumberOfYear");
        COLUMN_DAY_NUMBER_OF_YEAR_TIME.setId("_column_time_day_number_of_year");
        COLUMN_DAY_NUMBER_OF_YEAR_TIME.setType(ColumnType.INTEGER);

        COLUMN_WEEK_NUMBER_OF_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEEK_NUMBER_OF_YEAR_TIME.setName("WeekNumberOfYear");
        COLUMN_WEEK_NUMBER_OF_YEAR_TIME.setId("_column_time_week_number_of_year");
        COLUMN_WEEK_NUMBER_OF_YEAR_TIME.setType(ColumnType.INTEGER);

        COLUMN_ENGLISH_MONTH_NAME_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ENGLISH_MONTH_NAME_TIME.setName("EnglishMonthName");
        COLUMN_ENGLISH_MONTH_NAME_TIME.setId("_column_time_english_month_name");
        COLUMN_ENGLISH_MONTH_NAME_TIME.setType(ColumnType.VARCHAR);

        COLUMN_SPANISH_MONTH_NAME_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SPANISH_MONTH_NAME_TIME.setName("SpanishMonthName");
        COLUMN_SPANISH_MONTH_NAME_TIME.setId("_column_time_spanish_month_name");
        COLUMN_SPANISH_MONTH_NAME_TIME.setType(ColumnType.VARCHAR);

        COLUMN_FRENCH_MONTH_NAME_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FRENCH_MONTH_NAME_TIME.setName("FrenchMonthName");
        COLUMN_FRENCH_MONTH_NAME_TIME.setId("_column_time_french+month_name");
        COLUMN_FRENCH_MONTH_NAME_TIME.setType(ColumnType.VARCHAR);

        COLUMN_MONTH_NUMBER_OF_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_NUMBER_OF_YEAR_TIME.setName("MonthNumberOfYear");
        COLUMN_MONTH_NUMBER_OF_YEAR_TIME.setId("_column_time_month_number_of_year");
        COLUMN_MONTH_NUMBER_OF_YEAR_TIME.setType(ColumnType.INTEGER);

        COLUMN_CALENDAR_QUARTER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CALENDAR_QUARTER_TIME.setName("CalendarQuarter");
        COLUMN_CALENDAR_QUARTER_TIME.setId("_column_time_calendar_quarter");
        COLUMN_CALENDAR_QUARTER_TIME.setType(ColumnType.INTEGER);

        COLUMN_CALENDAR_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CALENDAR_YEAR_TIME.setName("CalendarYear");
        COLUMN_CALENDAR_YEAR_TIME.setId("_column_time_calendar_year");
        COLUMN_CALENDAR_YEAR_TIME.setType(ColumnType.VARCHAR);

        COLUMN_CALENDAR_SEMESTER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CALENDAR_SEMESTER_TIME.setName("CalendarSemester");
        COLUMN_CALENDAR_SEMESTER_TIME.setId("_column_time_calendar_semester");
        COLUMN_CALENDAR_SEMESTER_TIME.setType(ColumnType.INTEGER);

        COLUMN_FISCAL_QUARTER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FISCAL_QUARTER_TIME.setName("FiscalQuarter");
        COLUMN_FISCAL_QUARTER_TIME.setId("_column_time_fiscal_quarter");
        COLUMN_FISCAL_QUARTER_TIME.setType(ColumnType.INTEGER);

        COLUMN_FISCAL_YEAR_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FISCAL_YEAR_TIME.setName("FiscalYear");
        COLUMN_FISCAL_YEAR_TIME.setId("_column_time_fiscal_year");
        COLUMN_FISCAL_YEAR_TIME.setType(ColumnType.VARCHAR);

        COLUMN_FISCAL_SEMESTER_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FISCAL_SEMESTER_TIME.setName("FiscalSemester");
        COLUMN_FISCAL_SEMESTER_TIME.setId("_column_time_fiscal_semester");
        COLUMN_FISCAL_SEMESTER_TIME.setType(ColumnType.INTEGER);

        TABLE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CUSTOMER.setName("Customer");
        TABLE_CUSTOMER.setId("_table_customer");
        TABLE_CUSTOMER.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_CUSTOMER,
                COLUMN_CUSTOMER_KEY_CUSTOMER,
                COLUMN_GEOGRAPHY_KEY_CUSTOMER,
                COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER,
                COLUMN_FIRST_NAME_CUSTOMER,
                COLUMN_MIDDLE_NAME_CUSTOMER,
                COLUMN_LAST_NAME_CUSTOMER,
                COLUMN_NAME_STYLE_CUSTOMER,
                COLUMN_BIRTH_DATE_CUSTOMER,
                COLUMN_MARITAL_STATUS_CUSTOMER,
                COLUMN_SUFFIX_CUSTOMER,
                COLUMN_GENDER_CUSTOMER,
                COLUMN_EMAIL_ADDRESS_CUSTOMER,
                COLUMN_YARLY_INCOME_CUSTOMER,
                COLUMN_TOTAL_CHILDREN_CUSTOMER,
                COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER,
                COLUMN_ENGLISH_EDUCATION_CUSTOMER,
                COLUMN_SPANISH_EDUCATION_CUSTOMER,
                COLUMN_FRENCH_EDUCATION_CUSTOMER,
                COLUMN_ENGLISH_OCCUPATION_CUSTOMER,
                COLUMN_SPANISH_OCCUPATION_CUSTOMER,
                COLUMN_FRENCH_OCCUPATION_CUSTOMER,
                COLUMN_HOUSE_OWNER_FLAG_CUSTOMER,
                COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER,
                COLUMN_ADDRESS_LINE1_CUSTOMER,
                COLUMN_ADDRESS_LINE2_CUSTOMER,
                COLUMN_PHONE_CUSTOMER,
                COLUMN_DATE_FIRST_PURCHASE_CUSTOMER,
                COLUMN_COMMUTE_DISTANCE_CUSTOMER));

        TABLE_EMPLOYEE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_EMPLOYEE.setName("Employee");
        TABLE_EMPLOYEE.setId("_table_employee");
        TABLE_EMPLOYEE.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_EMPLOYEE,
                COLUMN_EMPLOYEE_KEY_EMPLOYEE,
                COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE,
                COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE,
                COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE,
                COLUMN_SALES_TERRITORY_KEY_EMPLOYEE,
                COLUMN_FIRST_NAME_EMPLOYEE,
                COLUMN_LAST_NAME_EMPLOYEE,
                COLUMN_MIDDLE_NAME_EMPLOYEE,
                COLUMN_NAME_STYLE_EMPLOYEE,
                COLUMN_TITLE_EMPLOYEE,
                COLUMN_HIRE_DATE_EMPLOYEE,
                COLUMN_BIRTH_DATE_EMPLOYEE,
                COLUMN_LOGIN_ID_EMPLOYEE,
                COLUMN_EMAIL_ADDRESS_EMPLOYEE,
                COLUMN_PHONE_EMPLOYEE,
                COLUMN_MARITAL_STATUS_EMPLOYEE,
                COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE,
                COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE,
                COLUMN_SALARIED_FLAG_EMPLOYEE,
                COLUMN_GENDER_EMPLOYEE,
                COLUMN_PAY_FREQUENCY_EMPLOYEE,
                COLUMN_BASE_RATE_EMPLOYEE,
                COLUMN_VACATION_HOURS_EMPLOYEE,
                COLUMN_SICK_LEAVE_HOURS_EMPLOYEE,
                COLUMN_CURRENT_FLAG_EMPLOYEE,
                COLUMN_SALES_PERSONE_FLAG_EMPLOYEE,
                COLUMN_DEPARTAMENT_NAME_EMPLOYEE,
                COLUMN_START_DATE_EMPLOYEE,
                COLUMN_END_DATE_EMPLOYEE,
                COLUMN_STATUS_EMPLOYEE));

        TABLE_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_GEOGRAPHY.setName("Geography");
        TABLE_GEOGRAPHY.setId("_table_geography");
        TABLE_GEOGRAPHY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_GEOGRAPHY,
                COLUMN_GEOGRAPHY_KEY_GEOGRAPHY,
                COLUMN_CITY_GEOGRAPHY,
                COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY,
                COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY,
                COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY,
                COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY,
                COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY,
                COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY,
                COLUMN_POSTAL_CODE_GEOGRAPHY,
                COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY));

        TABLE_PRODUCT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCT.setName("Product");
        TABLE_PRODUCT.setId("_table_product");
        TABLE_PRODUCT.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_PRODUCT,
                COLUMN_PRODUCT_KEY_PRODUCT,
                COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT,
                COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT,
                COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT,
                COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT,
                COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT,
                COLUMN_SPANISH_PRODUCT_NAME_PRODUCT,
                COLUMN_FRENCH_PRODUCT_NAME_PRODUCT,
                COLUMN_STANDART_COST_PRODUCT,
                COLUMN_FINISH_GOODS_FLAG_PRODUCT,
                COLUMN_COLOR_PRODUCT,
                COLUMN_SAFETY_STOCK_LEVEL_PRODUCT,
                COLUMN_REORDER_POINT_PRODUCT,
                COLUMN_LIST_PRICE_PRODUCT,
                COLUMN_SIZE_PRODUCT,
                COLUMN_SIZE_RANGE_PRODUCT,
                COLUMN_WEIGHT_PRODUCT,
                COLUMN_DAYS_TO_MANUFACTURE_PRODUCT,
                COLUMN_PRODUCT_LINE_PRODUCT,
                COLUMN_DEALER_PRICE_PRODUCT,
                COLUMN_CLASS_PRODUCT,
                COLUMN_STYLE_PRODUCT,
                COLUMN_MODEL_NAME_PRODUCT,
                COLUMN_ENGLISH_DESCRIPTION_PRODUCT,
                COLUMN_FRENCH_DESCRIPTION_PRODUCT,
                COLUMN_CHINESE_DESCRIPTION_PRODUCT,
                COLUMN_ARABIC_DESCRIPTION_PRODUCT,
                COLUMN_HEBREW_DESCRIPTION_PRODUCT,
                COLUMN_THAI_DESCRIPTION_PRODUCT,
                COLUMN_START_DATE_PRODUCT,
                COLUMN_END_DATE_PRODUCT,
                COLUMN_STATUS_PRODUCT,
                COLUMN_SUBCATEGORY_PRODUCT));

        TABLE_FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_FACT.setName("Fact");
        TABLE_FACT.setId("_table_fact");
        TABLE_FACT.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_FACT,
                COLUMN_PRODUCT_KEY_FACT,
                COLUMN_ORDER_DATE_KEY_FACT,
                COLUMN_DUE_DATE_KEY_FACT,
                COLUMN_SHIP_DATE_KEY_FACT,
                COLUMN_CUSTOMER_KEY_FACT,
                COLUMN_PROMOTION_KEY_FACT,
                COLUMN_CURENCY_KEY_FACT,
                COLUMN_SALES_TERITORY_KEY_FACT,
                COLUMN_SALES_ORDER_NUMBER_FACT,
                COLUMN_SALES_ORDER_LINE_NUMBER_FACT,
                COLUMN_REVISION_NUMBER_FACT,
                COLUMN_ORDER_QUANTITY_FACT,
                COLUMN_UNIT_PRICE_FACT,
                COLUMN_EXTENDED_AMOUNT_FACT,
                COLUMN_UNIT_PRICE_DISCOUNT_PCT_FACT,
                COLUMN_DISCOUNT_AMOUNT_FACT,
                COLUMN_PRODUCT_STANDART_COST_FACT,
                COLUMN_TOTAL_PRODUCT_COST_FACT,
                COLUMN_SALES_AMOUNT_FACT,
                COLUMN_TAX_AMT_FACT,
                COLUMN_FREIGHT_FACT,
                COLUMN_CARRIER_TRACKING_NUMBER_FACT,
                COLUMN_CUSTOMER_PO_NUMBER_FACT,
                COLUMN_EMPLOYEE_KEY_FACT,
                COLUMN_BILLING_CUSTOMER_KEY_FACT,
                COLUMN_STORE_KEY_FACT,
                COLUMN_TOTAL_SALES_FACT));

        TABLE_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCT_CATEGORY.setName("ProductCategory");
        TABLE_PRODUCT_CATEGORY.setId("_table_product_catagory");
        TABLE_PRODUCT_CATEGORY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_PRODUCT_CATEGORY,
                COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY,
                COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY,
                COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY,
                COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY,
                COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY));

        TABLE_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCT_SUBCATEGORY.setName("ProductSubcategory");
        TABLE_PRODUCT_SUBCATEGORY.setId("_table_product_subcatagory");
        TABLE_PRODUCT_SUBCATEGORY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY,
                COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY,
                COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY,
                COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY,
                COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY,
                COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY,
                COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY));

        TABLE_STORE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_STORE.setName("Store");
        TABLE_STORE.setId("_table_store");
        TABLE_STORE.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_STORE,
                COLUMN_STORE_KEY_STORE,
                COLUMN_GEOGRAPHY_KEY_STORE,
                COLUMN_STORE_NAME_STORE,
                COLUMN_NUMBER_OF_EMPLOYEES_STORE,
                COLUMN_SALES_STORE));

        TABLE_TIME = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_TIME.setName("Time");
        TABLE_TIME.setId("_table_time");
        TABLE_TIME.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_TIME,
                COLUMN_TIME_KEY_TIME,
                COLUMN_FULL_DATE_ALTERNATE_KEY_TIME,
                COLUMN_DAY_NUMBER_OF_WEEK_TIME,
                COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME,
                COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME,
                COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME,
                COLUMN_DAY_NUMBER_OF_MONTH_TIME,
                COLUMN_DAY_NUMBER_OF_YEAR_TIME,
                COLUMN_WEEK_NUMBER_OF_YEAR_TIME,
                COLUMN_ENGLISH_MONTH_NAME_TIME,
                COLUMN_SPANISH_MONTH_NAME_TIME,
                COLUMN_FRENCH_MONTH_NAME_TIME,
                COLUMN_MONTH_NUMBER_OF_YEAR_TIME,
                COLUMN_CALENDAR_QUARTER_TIME,
                COLUMN_CALENDAR_YEAR_TIME,
                COLUMN_CALENDAR_SEMESTER_TIME,
                COLUMN_FISCAL_QUARTER_TIME,
                COLUMN_FISCAL_YEAR_TIME,
                COLUMN_FISCAL_SEMESTER_TIME));

        LEVEL_CUSTOMER_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_ROW_NUMBER.setName("RowNumber");
        LEVEL_CUSTOMER_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_CUSTOMER);
        LEVEL_CUSTOMER_ROW_NUMBER.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_CUSTOMER_ROW_NUMBER.setId("_level_customer_row_number");
        LEVEL_CUSTOMER_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_CUSTOMER_CUSTOMER_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_CUSTOMER_KEY.setName("CustomerKey");
        LEVEL_CUSTOMER_CUSTOMER_KEY.setColumn(COLUMN_CUSTOMER_KEY_CUSTOMER);
        LEVEL_CUSTOMER_CUSTOMER_KEY.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_CUSTOMER_CUSTOMER_KEY.setId("_level_customer_customer_key");

        LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY.setName("CustomerAlternateKey");
        LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY.setColumn(COLUMN_CUSTOMER_ALTERNATE_KEY_CUSTOMER);
        LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY.setId("_level_customer_customer_alternate_key");

        LEVEL_CUSTOMER_FIRST_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_FIRST_NAME.setName("FirstName");
        LEVEL_CUSTOMER_FIRST_NAME.setColumn(COLUMN_FIRST_NAME_CUSTOMER);
        LEVEL_CUSTOMER_FIRST_NAME.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_FIRST_NAME.setId("_level_customer_first_name");

        LEVEL_CUSTOMER_MIDDLE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_MIDDLE_NAME.setName("MiddleName");
        LEVEL_CUSTOMER_MIDDLE_NAME.setColumn(COLUMN_MIDDLE_NAME_CUSTOMER);
        LEVEL_CUSTOMER_MIDDLE_NAME.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_MIDDLE_NAME.setId("_level_customer_middle_name");

        LEVEL_CUSTOMER_LAST_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_LAST_NAME.setName("LastName");
        LEVEL_CUSTOMER_LAST_NAME.setColumn(COLUMN_LAST_NAME_CUSTOMER);
        LEVEL_CUSTOMER_LAST_NAME.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_LAST_NAME.setId("_level_customer_last_name");

        LEVEL_CUSTOMER_NAME_STYLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_NAME_STYLE.setName("NameStyle");
        LEVEL_CUSTOMER_NAME_STYLE.setColumn(COLUMN_NAME_STYLE_CUSTOMER);
        LEVEL_CUSTOMER_NAME_STYLE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_NAME_STYLE.setId("_level_customer_name_style");

        LEVEL_CUSTOMER_BIRTH_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_BIRTH_DATE.setName("BirthDate");
        LEVEL_CUSTOMER_BIRTH_DATE.setColumn(COLUMN_BIRTH_DATE_CUSTOMER);
        LEVEL_CUSTOMER_BIRTH_DATE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_BIRTH_DATE.setId("_level_customer_birth_date");

        LEVEL_CUSTOMER_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_MARITAL_STATUS.setName("MaritalStatus");
        LEVEL_CUSTOMER_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_CUSTOMER);
        LEVEL_CUSTOMER_MARITAL_STATUS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_MARITAL_STATUS.setId("_level_customer_marital_status");

        LEVEL_CUSTOMER_SUFFIX = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_SUFFIX.setName("Suffix");
        LEVEL_CUSTOMER_SUFFIX.setColumn(COLUMN_SUFFIX_CUSTOMER);
        LEVEL_CUSTOMER_SUFFIX.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_SUFFIX.setId("_level_customer_suffix");

        LEVEL_CUSTOMER_GENDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_GENDER.setName("Gender");
        LEVEL_CUSTOMER_GENDER.setColumn(COLUMN_GENDER_CUSTOMER);
        LEVEL_CUSTOMER_GENDER.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_GENDER.setId("_level_customer_gender");

        LEVEL_CUSTOMER_EMAIL_ADDRESS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_EMAIL_ADDRESS.setName("EmailAddress");
        LEVEL_CUSTOMER_EMAIL_ADDRESS.setColumn(COLUMN_EMAIL_ADDRESS_CUSTOMER);
        LEVEL_CUSTOMER_EMAIL_ADDRESS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_EMAIL_ADDRESS.setId("_level_customer_email_address");

        LEVEL_CUSTOMER_YARLY_INCOME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_YARLY_INCOME.setName("YearlyIncome");
        LEVEL_CUSTOMER_YARLY_INCOME.setColumn(COLUMN_YARLY_INCOME_CUSTOMER);
        LEVEL_CUSTOMER_YARLY_INCOME.setColumnType(ColumnInternalDataType.NUMERIC);
        LEVEL_CUSTOMER_YARLY_INCOME.setId("_level_customer_yearly_income");

        LEVEL_CUSTOMER_TOTAL_CHILDREN = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_TOTAL_CHILDREN.setName("TotalChildren");
        LEVEL_CUSTOMER_TOTAL_CHILDREN.setColumn(COLUMN_TOTAL_CHILDREN_CUSTOMER);
        LEVEL_CUSTOMER_TOTAL_CHILDREN.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_CUSTOMER_TOTAL_CHILDREN.setId("_level_customer_total_children");

        LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME.setName("NumberChildrenAtHome");
        LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME.setColumn(COLUMN_NUMBER_CHILDREN_AT_HOME_CUSTOMER);
        LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME.setId("_level_customer_number_children_at_home");

        LEVEL_CUSTOMER_ENGLISH_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_ENGLISH_EDUCATION.setName("EnglishEducation");
        LEVEL_CUSTOMER_ENGLISH_EDUCATION.setColumn(COLUMN_ENGLISH_EDUCATION_CUSTOMER);
        LEVEL_CUSTOMER_ENGLISH_EDUCATION.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_ENGLISH_EDUCATION.setId("_level_customer_english_education");

        LEVEL_CUSTOMER_SPANISH_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_SPANISH_EDUCATION.setName("SpanishEducation");
        LEVEL_CUSTOMER_SPANISH_EDUCATION.setColumn(COLUMN_SPANISH_EDUCATION_CUSTOMER);
        LEVEL_CUSTOMER_SPANISH_EDUCATION.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_SPANISH_EDUCATION.setId("_level_customer_spanish_education");

        LEVEL_CUSTOMER_FRENCH_EDUCATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_FRENCH_EDUCATION.setName("FrenchEducation");
        LEVEL_CUSTOMER_FRENCH_EDUCATION.setColumn(COLUMN_FRENCH_EDUCATION_CUSTOMER);
        LEVEL_CUSTOMER_FRENCH_EDUCATION.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMER_FRENCH_EDUCATION.setId("_level_customer_french_education");

        LEVEL_CUSTOMER_ENGLISH_OCCUPATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_ENGLISH_OCCUPATION.setName("EnglishOccupation");
        LEVEL_CUSTOMER_ENGLISH_OCCUPATION.setColumn(COLUMN_ENGLISH_OCCUPATION_CUSTOMER);
        LEVEL_CUSTOMER_ENGLISH_OCCUPATION.setId("_level_customer_english_occupation");

        LEVEL_CUSTOMER_SPANISH_OCCUPATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_SPANISH_OCCUPATION.setName("SpanishOccupation");
        LEVEL_CUSTOMER_SPANISH_OCCUPATION.setColumn(COLUMN_SPANISH_OCCUPATION_CUSTOMER);
        LEVEL_CUSTOMER_SPANISH_OCCUPATION.setId("_level_customer_spanish_occupation");

        LEVEL_CUSTOMER_FRENCH_OCCUPATION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_FRENCH_OCCUPATION.setName("FrenchOccupation");
        LEVEL_CUSTOMER_FRENCH_OCCUPATION.setColumn(COLUMN_FRENCH_OCCUPATION_CUSTOMER);
        LEVEL_CUSTOMER_FRENCH_OCCUPATION.setId("_level_customer_french_occupation");

        LEVEL_CUSTOMER_HOUSE_OWNER_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_HOUSE_OWNER_FLAG.setName("HouseOwnerFlag");
        LEVEL_CUSTOMER_HOUSE_OWNER_FLAG.setColumn(COLUMN_HOUSE_OWNER_FLAG_CUSTOMER);
        LEVEL_CUSTOMER_HOUSE_OWNER_FLAG.setId("_level_customer_house_owner_flag");

        LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG.setName("NumberCarsOwned");
        LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG.setColumn(COLUMN_NUMBER_CARS_OWNED_FLAG_CUSTOMER);
        LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG.setId("_level_customer_number_cars_owned");

        LEVEL_CUSTOMER_ADDRESS_LINE1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_ADDRESS_LINE1.setName("AddressLine1");
        LEVEL_CUSTOMER_ADDRESS_LINE1.setColumn(COLUMN_ADDRESS_LINE1_CUSTOMER);
        LEVEL_CUSTOMER_ADDRESS_LINE1.setId("_level_customer_address_line1");

        LEVEL_CUSTOMER_ADDRESS_LINE2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_ADDRESS_LINE2.setName("AddressLine2");
        LEVEL_CUSTOMER_ADDRESS_LINE2.setColumn(COLUMN_ADDRESS_LINE2_CUSTOMER);
        LEVEL_CUSTOMER_ADDRESS_LINE2.setId("_level_customer_address_line2");

        LEVEL_CUSTOMER_PHONE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_PHONE.setName("Phone");
        LEVEL_CUSTOMER_PHONE.setColumn(COLUMN_PHONE_CUSTOMER);
        LEVEL_CUSTOMER_PHONE.setId("_level_customer_phone");

        LEVEL_CUSTOMER_DATE_FIRST_PURCHASE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_DATE_FIRST_PURCHASE.setName("DateFirstPurchase");
        LEVEL_CUSTOMER_DATE_FIRST_PURCHASE.setColumn(COLUMN_DATE_FIRST_PURCHASE_CUSTOMER);
        LEVEL_CUSTOMER_DATE_FIRST_PURCHASE.setId("_level_customer_date_first_purchase");

        LEVEL_CUSTOMER_COMMUTE_DISTANCE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMER_COMMUTE_DISTANCE.setName("CommuteDistance");
        LEVEL_CUSTOMER_COMMUTE_DISTANCE.setColumn(COLUMN_COMMUTE_DISTANCE_CUSTOMER);
        LEVEL_CUSTOMER_COMMUTE_DISTANCE.setId("_level_customer_commute_distance");

        LEVEL_EMPLOYEE_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_ROW_NUMBER.setName("RowNumber");
        LEVEL_EMPLOYEE_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_EMPLOYEE);
        LEVEL_EMPLOYEE_ROW_NUMBER.setId("_level_employee_row_number");

        LEVEL_EMPLOYEE_EMPLOYEE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_EMPLOYEE_KEY.setName("EmployeeKey");
        LEVEL_EMPLOYEE_EMPLOYEE_KEY.setColumn(COLUMN_EMPLOYEE_KEY_EMPLOYEE);
        LEVEL_EMPLOYEE_EMPLOYEE_KEY.setId("_level_employee_employee_key");

        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY.setName("ParentEmployeeKey");
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY.setColumn(COLUMN_PARENT_EMPLOYEE_KEY_EMPLOYEE);
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY.setId("_level_employee_parent_employee_key");

        LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setName("EmployeeNationalIDAlternateKey");
        LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setColumn(COLUMN_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE);
        LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setId("_level_employee_employee_mational_id_alternate_key");

        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setName("ParentEmployeeNationalIDAlternateKey");
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setColumn(COLUMN_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY_EMPLOYEE);
        LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY.setId("_level_employee_parent_employee_national_id_alternate_key");

        LEVEL_EMPLOYEE_SALES_TERRITORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_SALES_TERRITORY_KEY.setName("SalesTerritoryKey");
        LEVEL_EMPLOYEE_SALES_TERRITORY_KEY.setColumn(COLUMN_SALES_TERRITORY_KEY_EMPLOYEE);
        LEVEL_EMPLOYEE_SALES_TERRITORY_KEY.setId("_level_employee_sales_territory_key");

        LEVEL_EMPLOYEE_FIRST_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_FIRST_NAME.setName("FirstName");
        LEVEL_EMPLOYEE_FIRST_NAME.setColumn(COLUMN_FIRST_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_FIRST_NAME.setId("_level_employee_first_name");

        LEVEL_EMPLOYEE_LAST_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_LAST_NAME.setName("LastName");
        LEVEL_EMPLOYEE_LAST_NAME.setColumn(COLUMN_LAST_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_LAST_NAME.setId("_level_employee_last_name");

        LEVEL_EMPLOYEE_MIDDLE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_MIDDLE_NAME.setName("MiddleName");
        LEVEL_EMPLOYEE_MIDDLE_NAME.setColumn(COLUMN_MIDDLE_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_MIDDLE_NAME.setId("_level_employee_middle_name");

        LEVEL_EMPLOYEE_NAME_STYLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_NAME_STYLE.setName("NameStyle");
        LEVEL_EMPLOYEE_NAME_STYLE.setColumn(COLUMN_NAME_STYLE_EMPLOYEE);
        LEVEL_EMPLOYEE_NAME_STYLE.setId("_level_employee_name_style");

        LEVEL_EMPLOYEE_TITLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_TITLE.setName("Title");
        LEVEL_EMPLOYEE_TITLE.setColumn(COLUMN_TITLE_EMPLOYEE);
        LEVEL_EMPLOYEE_TITLE.setId("_level_employee_title");

        LEVEL_EMPLOYEE_HIRE_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_HIRE_DATE.setName("HireDate");
        LEVEL_EMPLOYEE_HIRE_DATE.setColumn(COLUMN_HIRE_DATE_EMPLOYEE);
        LEVEL_EMPLOYEE_HIRE_DATE.setId("_level_employee_hire_date");

        LEVEL_EMPLOYEE_BIRTH_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_BIRTH_DATE.setName("BirthDate");
        LEVEL_EMPLOYEE_BIRTH_DATE.setColumn(COLUMN_BIRTH_DATE_EMPLOYEE);
        LEVEL_EMPLOYEE_BIRTH_DATE.setId("_level_employee_birth_date");

        LEVEL_EMPLOYEE_LOGIN_ID = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_LOGIN_ID.setName("LoginID");
        LEVEL_EMPLOYEE_LOGIN_ID.setColumn(COLUMN_LOGIN_ID_EMPLOYEE);
        LEVEL_EMPLOYEE_LOGIN_ID.setId("_level_employee_login_id");

        LEVEL_EMPLOYEE_EMAIL_ADDRESS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_EMAIL_ADDRESS.setName("EmailAddress");
        LEVEL_EMPLOYEE_EMAIL_ADDRESS.setColumn(COLUMN_EMAIL_ADDRESS_EMPLOYEE);
        LEVEL_EMPLOYEE_EMAIL_ADDRESS.setId("_level_employee_email_address");

        LEVEL_EMPLOYEE_PHONE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_PHONE.setName("Phone");
        LEVEL_EMPLOYEE_PHONE.setColumn(COLUMN_PHONE_EMPLOYEE);
        LEVEL_EMPLOYEE_PHONE.setId("_level_employee_phone");

        LEVEL_EMPLOYEE_MARITAL_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_MARITAL_STATUS.setName("MaritalStatus");
        LEVEL_EMPLOYEE_MARITAL_STATUS.setColumn(COLUMN_MARITAL_STATUS_EMPLOYEE);
        LEVEL_EMPLOYEE_MARITAL_STATUS.setId("_level_employee_marital_status");

        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME.setName("EmergencyContactName");
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME.setColumn(COLUMN_EMERGENCY_CONTACT_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME.setId("_level_employee_emergency_contact_name");

        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE.setName("EmergencyContactPhone");
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE.setColumn(COLUMN_EMERGENCY_CONTACT_PHONE_EMPLOYEE);
        LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE.setId("_level_employee_emergency_contact_phone");

        LEVEL_EMPLOYEE_SALARIED_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_SALARIED_FLAG.setName("SalariedFlag");
        LEVEL_EMPLOYEE_SALARIED_FLAG.setColumn(COLUMN_SALARIED_FLAG_EMPLOYEE);
        LEVEL_EMPLOYEE_SALARIED_FLAG.setId("_level_employee_salaried_flag");

        LEVEL_EMPLOYEE_GENDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_GENDER.setName("Gender");
        LEVEL_EMPLOYEE_GENDER.setColumn(COLUMN_GENDER_EMPLOYEE);
        LEVEL_EMPLOYEE_GENDER.setId("_level_employee_gender");

        LEVEL_EMPLOYEE_PAY_FREQUENCY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_PAY_FREQUENCY.setName("PayFrequency");
        LEVEL_EMPLOYEE_PAY_FREQUENCY.setColumn(COLUMN_PAY_FREQUENCY_EMPLOYEE);
        LEVEL_EMPLOYEE_PAY_FREQUENCY.setId("_level_employee_pay_frequency");

        LEVEL_EMPLOYEE_BASE_RATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_BASE_RATE.setName("BaseRate");
        LEVEL_EMPLOYEE_BASE_RATE.setColumn(COLUMN_BASE_RATE_EMPLOYEE);
        LEVEL_EMPLOYEE_BASE_RATE.setId("_level_employee_base_rate");

        LEVEL_EMPLOYEE_VACATION_HOURS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_VACATION_HOURS.setName("VacationHours");
        LEVEL_EMPLOYEE_VACATION_HOURS.setColumn(COLUMN_VACATION_HOURS_EMPLOYEE);
        LEVEL_EMPLOYEE_VACATION_HOURS.setId("_level_employee_vacation_hours");

        LEVEL_EMPLOYEE_SICK_LEAVE_HOURS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_SICK_LEAVE_HOURS.setName("SickLeaveHours");
        LEVEL_EMPLOYEE_SICK_LEAVE_HOURS.setColumn(COLUMN_SICK_LEAVE_HOURS_EMPLOYEE);
        LEVEL_EMPLOYEE_SICK_LEAVE_HOURS.setId("_level_employee_sick_leave_hours");

        LEVEL_EMPLOYEE_CURRENT_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_CURRENT_FLAG.setName("CurrentFlag");
        LEVEL_EMPLOYEE_CURRENT_FLAG.setColumn(COLUMN_CURRENT_FLAG_EMPLOYEE);
        LEVEL_EMPLOYEE_CURRENT_FLAG.setId("_level_employee_current_flag");

        LEVEL_EMPLOYEE_SALES_PERSONE_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_SALES_PERSONE_FLAG.setName("SalesPersonFlag");
        LEVEL_EMPLOYEE_SALES_PERSONE_FLAG.setColumn(COLUMN_SALES_PERSONE_FLAG_EMPLOYEE);
        LEVEL_EMPLOYEE_SALES_PERSONE_FLAG.setId("_level_employee_sales_person_flag");

        LEVEL_EMPLOYEE_DEPARTAMENT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_DEPARTAMENT_NAME.setName("DepartmentName");
        LEVEL_EMPLOYEE_DEPARTAMENT_NAME.setColumn(COLUMN_DEPARTAMENT_NAME_EMPLOYEE);
        LEVEL_EMPLOYEE_DEPARTAMENT_NAME.setId("_level_employee_department_name");

        LEVEL_EMPLOYEE_START_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_START_DATE.setName("StartDate");
        LEVEL_EMPLOYEE_START_DATE.setColumn(COLUMN_START_DATE_EMPLOYEE);
        LEVEL_EMPLOYEE_START_DATE.setId("_level_employee_start_date");

        LEVEL_EMPLOYEE_END_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_END_DATE.setName("EndDate");
        LEVEL_EMPLOYEE_END_DATE.setColumn(COLUMN_END_DATE_EMPLOYEE);
        LEVEL_EMPLOYEE_END_DATE.setId("_level_employee_end_date");

        LEVEL_EMPLOYEE_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_EMPLOYEE_STATUS.setName("Status");
        LEVEL_EMPLOYEE_STATUS.setColumn(COLUMN_STATUS_EMPLOYEE);
        LEVEL_EMPLOYEE_STATUS.setId("_level_employee_status");

        LEVEL_GEOGRAPHY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_ROW_NUMBER.setName("RowNumber");
        LEVEL_GEOGRAPHY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_GEOGRAPHY);
        LEVEL_GEOGRAPHY_ROW_NUMBER.setId("_level_geography_row_number");

        LEVEL_GEOGRAPHY_GEOGRAPHY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_GEOGRAPHY_KEY.setName("GeographyKey");
        LEVEL_GEOGRAPHY_GEOGRAPHY_KEY.setColumn(COLUMN_GEOGRAPHY_KEY_GEOGRAPHY);
        LEVEL_GEOGRAPHY_GEOGRAPHY_KEY.setId("_level_geography_geography_key");

        LEVEL_GEOGRAPHY_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_CITY.setName("City");
        LEVEL_GEOGRAPHY_CITY.setColumn(COLUMN_CITY_GEOGRAPHY);
        LEVEL_GEOGRAPHY_CITY.setId("_level_geography_city");

        LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE.setName("StateProvinceCode");
        LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE.setColumn(COLUMN_STATE_PROVINCE_CODE_GEOGRAPHY);
        LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE.setId("_level_geography_state_province_code");

        LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME.setName("StateProvinceName");
        LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME.setColumn(COLUMN_STATE_PROVINCE_NAME_GEOGRAPHY);
        LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME.setId("_level_geography_state_province_name");

        LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE.setName("CountryRegionCode");
        LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE.setColumn(COLUMN_COUNTRY_REGION_CODE_GEOGRAPHY);
        LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE.setId("_level_geography_country_region_code");

        LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME.setName("EnglishCountryRegionName");
        LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME.setColumn(COLUMN_ENGLISH_COUNTRY_REGION_NAME_GEOGRAPHY);
        LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME.setId("_level_geography_english_country_region_name");

        LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME.setName("SpanishCountryRegionName");
        LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME.setColumn(COLUMN_SPANISH_COUNTRY_REGION_NAME_GEOGRAPHY);
        LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME.setId("_level_geography_spanish_country_region_name");

        LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME.setName("FrenchCountryRegionName");
        LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME.setColumn(COLUMN_FRENCH_COUNTRY_REGION_NAME_GEOGRAPHY);
        LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME.setId("_level_geography_french_country_region_name");

        LEVEL_GEOGRAPHY_POSTAL_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_POSTAL_CODE.setName("PostalCode");
        LEVEL_GEOGRAPHY_POSTAL_CODE.setColumn(COLUMN_POSTAL_CODE_GEOGRAPHY);
        LEVEL_GEOGRAPHY_POSTAL_CODE.setId("_level_geography_postal_code");

        LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY.setName("SalesTerritoryKey");
        LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY.setColumn(COLUMN_SALES_TERRITORY_KEY_GEOGRAPHY);
        LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY.setId("_level_geography_sales_territory_key");

        LEVEL_STORE_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_ROW_NUMBER.setName("RowNumber");
        LEVEL_STORE_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_STORE);
        LEVEL_STORE_ROW_NUMBER.setId("_level_store_row_number");

        //"Geography_Key","StoreName","Number_of_Employees","Sales"
        LEVEL_STORE_STORE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_STORE_KEY.setName("StoreKey");
        LEVEL_STORE_STORE_KEY.setColumn(COLUMN_STORE_KEY_STORE);
        LEVEL_STORE_STORE_KEY.setId("_level_store_store_key");

        LEVEL_STORE_GEOGRAPHY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_GEOGRAPHY_KEY.setName("Geography_Key");
        LEVEL_STORE_GEOGRAPHY_KEY.setColumn(COLUMN_GEOGRAPHY_KEY_STORE);
        LEVEL_STORE_GEOGRAPHY_KEY.setId("_level_store_geography_key");

        LEVEL_STORE_STORE_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_STORE_NAME.setName("StoreName");
        LEVEL_STORE_STORE_NAME.setColumn(COLUMN_STORE_NAME_STORE);
        LEVEL_STORE_STORE_NAME.setId("_level_store_store_name");

        LEVEL_STORE_NUMBER_OF_EMPLOYEES = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_NUMBER_OF_EMPLOYEES.setName("Number_of_Employees");
        LEVEL_STORE_NUMBER_OF_EMPLOYEES.setColumn(COLUMN_NUMBER_OF_EMPLOYEES_STORE);
        LEVEL_STORE_NUMBER_OF_EMPLOYEES.setId("_level_store_number_of_employees");

        LEVEL_STORE_SALES = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STORE_SALES.setName("Sales");
        LEVEL_STORE_SALES.setColumn(COLUMN_SALES_STORE);
        LEVEL_STORE_SALES.setId("_level_store_sales");

        LEVEL_TIME_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_ROW_NUMBER.setName("RowNumber");
        LEVEL_TIME_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_TIME);
        LEVEL_TIME_ROW_NUMBER.setId("_level_time_row_number");

        LEVEL_TIME_TIME_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_TIME_KEY.setName("TimeKey");
        LEVEL_TIME_TIME_KEY.setColumn(COLUMN_TIME_KEY_TIME);
        LEVEL_TIME_TIME_KEY.setId("_level_time_time_key");

        LEVEL_TIME_FULL_DATE_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FULL_DATE_ALTERNATE_KEY.setName("FullDateAlternateKey");
        LEVEL_TIME_FULL_DATE_ALTERNATE_KEY.setColumn(COLUMN_FULL_DATE_ALTERNATE_KEY_TIME);
        LEVEL_TIME_FULL_DATE_ALTERNATE_KEY.setId("_level_time_full_date_alternate_key");

        LEVEL_TIME_DAY_NUMBER_OF_WEEK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_DAY_NUMBER_OF_WEEK.setName("DayNumberOfWeek");
        LEVEL_TIME_DAY_NUMBER_OF_WEEK.setColumn(COLUMN_DAY_NUMBER_OF_WEEK_TIME);
        LEVEL_TIME_DAY_NUMBER_OF_WEEK.setId("_level_time_day_number_of_week");

        LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK.setName("EnglishDayNameOfWeek");
        LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK.setColumn(COLUMN_ENGLISH_DAY_NAME_OF_WEEK_TIME);
        LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK.setId("_level_time_english_day_name_of_week");

        LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK.setName("SpanishDayNameOfWeek");
        LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK.setColumn(COLUMN_SPANISH_DAY_NAME_OF_WEEK_TIME);
        LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK.setId("_level_time_spanish_day_name_of_week");

        LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK.setName("FrenchDayNameOfWeek");
        LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK.setColumn(COLUMN_FRENCH_DAY_NAME_OF_WEEK_TIME);
        LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK.setId("_level_time_french_day_name_of_week");

        LEVEL_TIME_DAY_NUMBER_OF_MONTH = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_DAY_NUMBER_OF_MONTH.setName("DayNumberOfMonth");
        LEVEL_TIME_DAY_NUMBER_OF_MONTH.setColumn(COLUMN_DAY_NUMBER_OF_MONTH_TIME);
        LEVEL_TIME_DAY_NUMBER_OF_MONTH.setId("_level_time_day_number_of_month");

        LEVEL_TIME_DAY_NUMBER_OF_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_DAY_NUMBER_OF_YEAR.setName("DayNumberOfYear");
        LEVEL_TIME_DAY_NUMBER_OF_YEAR.setColumn(COLUMN_DAY_NUMBER_OF_YEAR_TIME);
        LEVEL_TIME_DAY_NUMBER_OF_YEAR.setId("_level_time_day_number_of_year");

        LEVEL_TIME_WEEK_NUMBER_OF_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_WEEK_NUMBER_OF_YEAR.setName("WeekNumberOfYear");
        LEVEL_TIME_WEEK_NUMBER_OF_YEAR.setColumn(COLUMN_WEEK_NUMBER_OF_YEAR_TIME);
        LEVEL_TIME_WEEK_NUMBER_OF_YEAR.setId("_level_time_week_number_of_year");

        LEVEL_TIME_ENGLISH_MONTH_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_ENGLISH_MONTH_NAME.setName("EnglishMonthName");
        LEVEL_TIME_ENGLISH_MONTH_NAME.setColumn(COLUMN_ENGLISH_MONTH_NAME_TIME);
        LEVEL_TIME_ENGLISH_MONTH_NAME.setId("_level_time_english_month_name");

        LEVEL_TIME_SPANISH_MONTH_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_SPANISH_MONTH_NAME.setName("SpanishMonthName");
        LEVEL_TIME_SPANISH_MONTH_NAME.setColumn(COLUMN_SPANISH_MONTH_NAME_TIME);
        LEVEL_TIME_SPANISH_MONTH_NAME.setId("_level_time_spanish_month_name");

        LEVEL_TIME_FRENCH_MONTH_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FRENCH_MONTH_NAME.setName("FrenchMonthName");
        LEVEL_TIME_FRENCH_MONTH_NAME.setColumn(COLUMN_FRENCH_MONTH_NAME_TIME);
        LEVEL_TIME_FRENCH_MONTH_NAME.setId("_level_time_french_month_name");

        LEVEL_TIME_MONTH_NUMBER_OF_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_MONTH_NUMBER_OF_YEAR.setName("MonthNumberOfYear");
        LEVEL_TIME_MONTH_NUMBER_OF_YEAR.setColumn(COLUMN_MONTH_NUMBER_OF_YEAR_TIME);
        LEVEL_TIME_MONTH_NUMBER_OF_YEAR.setId("_level_time_month_number_of_year");

        LEVEL_TIME_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_CALENDAR_QUARTER.setName("CalendarQuarter");
        LEVEL_TIME_CALENDAR_QUARTER.setColumn(COLUMN_CALENDAR_QUARTER_TIME);
        LEVEL_TIME_CALENDAR_QUARTER.setId("_level_time_calendar_quarter");

        LEVEL_TIME_CALENDAR_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_CALENDAR_YEAR.setName("CalendarYear");
        LEVEL_TIME_CALENDAR_YEAR.setColumn(COLUMN_CALENDAR_YEAR_TIME);
        LEVEL_TIME_CALENDAR_YEAR.setId("_level_time_calendar_year");

        LEVEL_TIME_CALENDAR_SEMESTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_CALENDAR_SEMESTER.setName("CalendarSemester");
        LEVEL_TIME_CALENDAR_SEMESTER.setColumn(COLUMN_CALENDAR_SEMESTER_TIME);
        LEVEL_TIME_CALENDAR_SEMESTER.setId("_level_time_calendar_semester");

        LEVEL_TIME_FISCAL_QUARTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FISCAL_QUARTER.setName("FiscalQuarter");
        LEVEL_TIME_FISCAL_QUARTER.setColumn(COLUMN_FISCAL_QUARTER_TIME);
        LEVEL_TIME_FISCAL_QUARTER.setId("_level_time_fiscal_quarter");

        LEVEL_TIME_FISCAL_YEAR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FISCAL_YEAR.setName("FiscalYear");
        LEVEL_TIME_FISCAL_YEAR.setColumn(COLUMN_FISCAL_YEAR_TIME);
        LEVEL_TIME_FISCAL_YEAR.setId("_level_time_fiscal_year");

        LEVEL_TIME_FISCAL_SEMESTER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_FISCAL_SEMESTER.setName("FiscalSemester");
        LEVEL_TIME_FISCAL_SEMESTER.setColumn(COLUMN_FISCAL_SEMESTER_TIME);
        LEVEL_TIME_FISCAL_SEMESTER.setId("_level_time_fiscal_semester");

        LEVEL_PRODUCT_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_ROW_NUMBER.setName("RowNumber");
        LEVEL_PRODUCT_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_PRODUCT);
        LEVEL_PRODUCT_ROW_NUMBER.setId("_level_product_row_number");

        LEVEL_PRODUCT_PRODUCT_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT_KEY.setName("ProductKey");
        LEVEL_PRODUCT_PRODUCT_KEY.setColumn(COLUMN_PRODUCT_KEY_PRODUCT);
        LEVEL_PRODUCT_PRODUCT_KEY.setId("_level_product_product_key");

        LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY.setName("ProductAlternateKey");
        LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY.setColumn(COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT);
        LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY.setId("_level_product_product_alternate_key");

        LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT);
        LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY.setId("_level_product_product_subcategory_key");

        LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE.setName("WeightUnitMeasureCode");
        LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE.setColumn(COLUMN_WEIGHT_UNIT_MEASURE_CODE_PRODUCT);
        LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE.setId("_level_product_weight_unit_measure_code");

        LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE.setName("SizeUnitMeasureCode");
        LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE.setColumn(COLUMN_SIZE_UNIT_MEASURE_CODE_PRODUCT);
        LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE.setId("_level_product_size_unit_measure_code");

        LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME.setName("EnglishProductName");
        LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME.setColumn(COLUMN_ENGLISH_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME.setId("_level_product_english_product_name");

        LEVEL_PRODUCT_SPANISH_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SPANISH_PRODUCT_NAME.setName("SpanishProductName");
        LEVEL_PRODUCT_SPANISH_PRODUCT_NAME.setColumn(COLUMN_SPANISH_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_SPANISH_PRODUCT_NAME.setId("_level_product_spanish_product_name");

        LEVEL_PRODUCT_FRENCH_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_FRENCH_PRODUCT_NAME.setName("FrenchProductName");
        LEVEL_PRODUCT_FRENCH_PRODUCT_NAME.setColumn(COLUMN_FRENCH_PRODUCT_NAME_PRODUCT);
        LEVEL_PRODUCT_FRENCH_PRODUCT_NAME.setId("_level_product_french_product_name");

        LEVEL_PRODUCT_STANDART_COST = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_STANDART_COST.setName("StandardCost");
        LEVEL_PRODUCT_STANDART_COST.setColumn(COLUMN_STANDART_COST_PRODUCT);
        LEVEL_PRODUCT_STANDART_COST.setId("_level_product_standard_cost");

        LEVEL_PRODUCT_FINISH_GOODS_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_FINISH_GOODS_FLAG.setName("FinishedGoodsFlag");
        LEVEL_PRODUCT_FINISH_GOODS_FLAG.setColumn(COLUMN_FINISH_GOODS_FLAG_PRODUCT);
        LEVEL_PRODUCT_FINISH_GOODS_FLAG.setId("_level_product_finished_goods_flag");

        LEVEL_PRODUCT_COLOR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_COLOR.setName("Color");
        LEVEL_PRODUCT_COLOR.setColumn(COLUMN_COLOR_PRODUCT);
        LEVEL_PRODUCT_COLOR.setId("_level_product_color");

        LEVEL_PRODUCT_SAFETY_STOCK_LEVEL = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SAFETY_STOCK_LEVEL.setName("SafetyStockLevel");
        LEVEL_PRODUCT_SAFETY_STOCK_LEVEL.setColumn(COLUMN_SAFETY_STOCK_LEVEL_PRODUCT);
        LEVEL_PRODUCT_SAFETY_STOCK_LEVEL.setId("_level_product_safety_stock_level");

        LEVEL_PRODUCT_REORDER_POINT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_REORDER_POINT.setName("ReorderPoint");
        LEVEL_PRODUCT_REORDER_POINT.setColumn(COLUMN_REORDER_POINT_PRODUCT);
        LEVEL_PRODUCT_REORDER_POINT.setId("_level_product_reorder_point");

        LEVEL_PRODUCT_LIST_PRICE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_LIST_PRICE.setName("ListPrice");
        LEVEL_PRODUCT_LIST_PRICE.setColumn(COLUMN_LIST_PRICE_PRODUCT);
        LEVEL_PRODUCT_LIST_PRICE.setId("_level_product_list_price");

        LEVEL_PRODUCT_SIZE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SIZE.setName("Size");
        LEVEL_PRODUCT_SIZE.setColumn(COLUMN_SIZE_PRODUCT);
        LEVEL_PRODUCT_SIZE.setId("_level_product_size");

        LEVEL_PRODUCT_SIZE_RANGE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SIZE_RANGE.setName("SizeRange");
        LEVEL_PRODUCT_SIZE_RANGE.setColumn(COLUMN_SIZE_RANGE_PRODUCT);
        LEVEL_PRODUCT_SIZE_RANGE.setId("_level_product_size_range");

        LEVEL_PRODUCT_WEIGHT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_WEIGHT.setName("Weight");
        LEVEL_PRODUCT_WEIGHT.setColumn(COLUMN_WEIGHT_PRODUCT);
        LEVEL_PRODUCT_WEIGHT.setId("_level_product_weight");

        LEVEL_PRODUCT_DAYS_TO_MANUFACTURE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_DAYS_TO_MANUFACTURE.setName("DaysToManufacture");
        LEVEL_PRODUCT_DAYS_TO_MANUFACTURE.setColumn(COLUMN_DAYS_TO_MANUFACTURE_PRODUCT);
        LEVEL_PRODUCT_DAYS_TO_MANUFACTURE.setId("_level_product_days_to_manufacture");

        LEVEL_PRODUCT_PRODUCT_LINE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT_LINE.setName("ProductLine");
        LEVEL_PRODUCT_PRODUCT_LINE.setColumn(COLUMN_PRODUCT_LINE_PRODUCT);
        LEVEL_PRODUCT_PRODUCT_LINE.setId("_level_product_product_line");

        LEVEL_PRODUCT_DEALER_PRICE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_DEALER_PRICE.setName("DealerPrice");
        LEVEL_PRODUCT_DEALER_PRICE.setColumn(COLUMN_DEALER_PRICE_PRODUCT);
        LEVEL_PRODUCT_DEALER_PRICE.setId("_level_product_dealer_price");

        LEVEL_PRODUCT_CLASS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CLASS.setName("Class");
        LEVEL_PRODUCT_CLASS.setColumn(COLUMN_CLASS_PRODUCT);
        LEVEL_PRODUCT_CLASS.setId("_level_product_class");

        LEVEL_PRODUCT_STYLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_STYLE.setName("Style");
        LEVEL_PRODUCT_STYLE.setColumn(COLUMN_STYLE_PRODUCT);
        LEVEL_PRODUCT_STYLE.setId("_level_product_style");

        LEVEL_PRODUCT_MODEL_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_MODEL_NAME.setName("ModelName");
        LEVEL_PRODUCT_MODEL_NAME.setColumn(COLUMN_MODEL_NAME_PRODUCT);
        LEVEL_PRODUCT_MODEL_NAME.setId("_level_product_model_name");

        LEVEL_PRODUCT_ENGLISH_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_ENGLISH_DESCRIPTION.setName("EnglishDescription");
        LEVEL_PRODUCT_ENGLISH_DESCRIPTION.setColumn(COLUMN_ENGLISH_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_ENGLISH_DESCRIPTION.setId("_level_product_english_description");

        LEVEL_PRODUCT_FRENCH_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_FRENCH_DESCRIPTION.setName("FrenchDescription");
        LEVEL_PRODUCT_FRENCH_DESCRIPTION.setColumn(COLUMN_FRENCH_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_FRENCH_DESCRIPTION.setId("_level_product_french_description");

        LEVEL_PRODUCT_CHINESE_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CHINESE_DESCRIPTION.setName("ChineseDescription");
        LEVEL_PRODUCT_CHINESE_DESCRIPTION.setColumn(COLUMN_CHINESE_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_CHINESE_DESCRIPTION.setId("_level_product_chinese_description");

        LEVEL_PRODUCT_ARABIC_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_ARABIC_DESCRIPTION.setName("ArabicDescription");
        LEVEL_PRODUCT_ARABIC_DESCRIPTION.setColumn(COLUMN_ARABIC_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_ARABIC_DESCRIPTION.setId("_level_product_arabic_description");

        LEVEL_PRODUCT_HEBREW_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_HEBREW_DESCRIPTION.setName("HebrewDescription");
        LEVEL_PRODUCT_HEBREW_DESCRIPTION.setColumn(COLUMN_HEBREW_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_HEBREW_DESCRIPTION.setId("_level_product_hebrew_description");

        LEVEL_PRODUCT_THAI_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_THAI_DESCRIPTION.setName("ThaiDescription");
        LEVEL_PRODUCT_THAI_DESCRIPTION.setColumn(COLUMN_THAI_DESCRIPTION_PRODUCT);
        LEVEL_PRODUCT_THAI_DESCRIPTION.setId("_level_product_thai_description");

        LEVEL_PRODUCT_START_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_START_DATE.setName("StartDate");
        LEVEL_PRODUCT_START_DATE.setColumn(COLUMN_START_DATE_PRODUCT);
        LEVEL_PRODUCT_START_DATE.setId("_level_product_start_date");

        LEVEL_PRODUCT_END_DATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_END_DATE.setName("EndDate");
        LEVEL_PRODUCT_END_DATE.setColumn(COLUMN_END_DATE_PRODUCT);
        LEVEL_PRODUCT_END_DATE.setId("_level_product_end_date");

        LEVEL_PRODUCT_STATUS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_STATUS.setName("Status");
        LEVEL_PRODUCT_STATUS.setColumn(COLUMN_STATUS_PRODUCT);
        LEVEL_PRODUCT_STATUS.setId("_level_product_status");

        LEVEL_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY.setName("Subcategory");
        LEVEL_PRODUCT_SUBCATEGORY.setColumn(COLUMN_SUBCATEGORY_PRODUCT);
        LEVEL_PRODUCT_SUBCATEGORY.setId("_level_product_subcategory"); //TODO need remove

        LEVEL_PRODUCT_CATEGORY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_ROW_NUMBER.setName("RowNumber");
        LEVEL_PRODUCT_CATEGORY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_ROW_NUMBER.setId("_level_product_category_row_number");

        LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY.setName("ProductCategoryKey");
        LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY.setColumn(COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY.setId("_level_product_category_product_category_key");

        LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY.setName("ProductCategoryAlternateKey");
        LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY.setColumn(COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY.setId("_level_product_category_product_category_alternate_key");

        LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME.setName("EnglishProductCategoryName");
        LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME.setColumn(COLUMN_ENGLISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME.setId("_level_product_category_english_product_category_name");

        LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME.setName("SpanishProductCategoryName");
        LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME.setColumn(COLUMN_SPANISH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME.setId("_level_product_category_spanish_product_category_name");

        LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME.setName("FrenchProductCategoryName");
        LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME.setColumn(COLUMN_FRENCH_PRODUCT_CATEGORY_NAME_PRODUCT_CATEGORY);
        LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME.setId("_level_product_category_french_product_category_name");

        LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER.setName("RowNumber");
        LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER.setId("_level_product_subcategory_row_number");

        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setId("_level_product_subcategory_product_subcategory_key");

        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY.setName("ProductSubcategoryAlternateKey");
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY.setColumn(COLUMN_PRODUCT_ALTERNATE_KEY_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY.setId("_level_product_subcategory_product_subcategory_alternate_key");

        LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME.setName("EnglishProductSubcategoryName");
        LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME.setColumn(COLUMN_ENGLISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME.setId("_level_product_subcategory_english_product_subcategory_name");

        LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME.setName("SpanishProductSubcategoryName");
        LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME.setColumn(COLUMN_SPANISH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME.setId("_level_product_subcategory_spanish_product_subcategory_name");

        LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME.setName("FrenchProductSubcategoryName");
        LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME.setColumn(COLUMN_FRENCH_PRODUCT_SUBCATEGORY_NAME_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME.setId("_level_product_subcategory_french_product_subcategory_name");

        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY.setName("ProductCategoryKey");
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY.setColumn(COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY);
        LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY.setId("_level_product_subcategory_product_category_key");

        // Initialize database schema
        DATABASE_SCHEMA = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA.setId("_databaseSchema");
        DATABASE_SCHEMA.getTables()
                .addAll(List.of(
                        TABLE_FACT,
                        TABLE_CUSTOMER,
                        TABLE_EMPLOYEE,
                        TABLE_GEOGRAPHY,
                        TABLE_PRODUCT,
                        TABLE_PRODUCT_CATEGORY,
                        TABLE_PRODUCT_SUBCATEGORY,
                        TABLE_STORE,
                        TABLE_TIME));

        TABLEQUERY_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_FACT.setId("_query_fact");
        TABLEQUERY_FACT.setTable(TABLE_FACT);

        TABLEQUERY_CUSTOMER = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_CUSTOMER.setId("_query_customer");
        TABLEQUERY_CUSTOMER.setTable(TABLE_CUSTOMER);

        TABLEQUERY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_EMPLOYEE.setId("_query_employee");
        TABLEQUERY_EMPLOYEE.setTable(TABLE_EMPLOYEE);

        TABLEQUERY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_GEOGRAPHY.setId("_query_geography");
        TABLEQUERY_GEOGRAPHY.setTable(TABLE_GEOGRAPHY);

        TABLEQUERY_PRODUCT = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PRODUCT.setId("_query_product");
        TABLEQUERY_PRODUCT.setTable(TABLE_PRODUCT);

        TABLEQUERY_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PRODUCT_CATEGORY.setId("_query_product_category");
        TABLEQUERY_PRODUCT_CATEGORY.setTable(TABLE_PRODUCT_CATEGORY);

        TABLEQUERY_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PRODUCT_SUBCATEGORY.setId("_query_product_subcategory");
        TABLEQUERY_PRODUCT_SUBCATEGORY.setTable(TABLE_PRODUCT_SUBCATEGORY);

        TABLEQUERY_STORE = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_STORE.setId("_query_store");
        TABLEQUERY_STORE.setTable(TABLE_STORE);

        TABLEQUERY_TIME = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_TIME.setId("_query_time");
        TABLEQUERY_TIME.setTable(TABLE_TIME);

        JOIN_GEOGRAPHY_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_GEOGRAPHY_LEFT.setKey(COLUMN_GEOGRAPHY_KEY_CUSTOMER);
        JOIN_GEOGRAPHY_LEFT.setQuery(TABLEQUERY_CUSTOMER);

        JOIN_GEOGRAPHY_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_GEOGRAPHY_RIGHT.setKey(COLUMN_GEOGRAPHY_KEY_GEOGRAPHY);
        JOIN_GEOGRAPHY_RIGHT.setQuery(TABLEQUERY_GEOGRAPHY);

        JOIN_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_GEOGRAPHY.setId("_join_query_geography");
        JOIN_GEOGRAPHY.setLeft(JOIN_GEOGRAPHY_LEFT);
        JOIN_GEOGRAPHY.setRight(JOIN_GEOGRAPHY_RIGHT);

        JOIN_SUBCATEGORY_CATEGORY_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_CATEGORY_LEFT.setKey(COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_SUBCATEGORY);
        JOIN_SUBCATEGORY_CATEGORY_LEFT.setQuery(TABLEQUERY_PRODUCT_SUBCATEGORY);

        JOIN_SUBCATEGORY_CATEGORY_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_CATEGORY_RIGHT.setKey(COLUMN_PRODUCT_CATEGORY_KEY_PRODUCT_CATEGORY);
        JOIN_SUBCATEGORY_CATEGORY_RIGHT.setQuery(TABLEQUERY_PRODUCT_CATEGORY);

        JOIN_SUBCATEGORY_CATEGORY = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_SUBCATEGORY_CATEGORY.setId("_join_query_subcategory_category");
        JOIN_SUBCATEGORY_CATEGORY.setLeft(JOIN_SUBCATEGORY_CATEGORY_LEFT);
        JOIN_SUBCATEGORY_CATEGORY.setRight(JOIN_SUBCATEGORY_CATEGORY_RIGHT);

        JOIN_PRODUCT_CATEGORY_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_CATEGORY_LEFT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT);
        JOIN_PRODUCT_CATEGORY_LEFT.setQuery(TABLEQUERY_PRODUCT);

        JOIN_PRODUCT_CATEGORY_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_CATEGORY_RIGHT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY);
        JOIN_PRODUCT_CATEGORY_RIGHT.setQuery(JOIN_SUBCATEGORY_CATEGORY);

        JOIN_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_PRODUCT_CATEGORY.setId("_join_query_product_category");
        JOIN_PRODUCT_CATEGORY.setLeft(JOIN_PRODUCT_CATEGORY_LEFT);
        JOIN_PRODUCT_CATEGORY.setRight(JOIN_PRODUCT_CATEGORY_RIGHT);

        JOIN_PRODUCT_SUBCATEGORY_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_SUBCATEGORY_LEFT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT);
        JOIN_PRODUCT_SUBCATEGORY_LEFT.setQuery(TABLEQUERY_PRODUCT);

        JOIN_PRODUCT_SUBCATEGORY_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_PRODUCT_SUBCATEGORY_RIGHT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_PRODUCT_SUBCATEGORY);
        JOIN_PRODUCT_SUBCATEGORY_RIGHT.setQuery(TABLEQUERY_PRODUCT_SUBCATEGORY);

        JOIN_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_PRODUCT_SUBCATEGORY.setId("_join_query_product_subcategory");
        JOIN_PRODUCT_SUBCATEGORY.setLeft(JOIN_PRODUCT_SUBCATEGORY_LEFT);
        JOIN_PRODUCT_SUBCATEGORY.setRight(JOIN_PRODUCT_SUBCATEGORY_RIGHT);

        HIERARCHY_CUSTOMER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMER.setId("_hierarchy_customer");
        HIERARCHY_CUSTOMER.setHasAll(true);
        HIERARCHY_CUSTOMER.setPrimaryKey(COLUMN_CUSTOMER_KEY_CUSTOMER);
        HIERARCHY_CUSTOMER.setQuery(TABLEQUERY_CUSTOMER);
        HIERARCHY_CUSTOMER.getLevels().addAll(List.of(
                LEVEL_CUSTOMER_ROW_NUMBER,
                LEVEL_CUSTOMER_CUSTOMER_KEY,
                LEVEL_CUSTOMER_CUSTOMER_ALTERNATE_KEY,
                LEVEL_CUSTOMER_FIRST_NAME,
                LEVEL_CUSTOMER_MIDDLE_NAME,
                LEVEL_CUSTOMER_LAST_NAME,
                LEVEL_CUSTOMER_NAME_STYLE,
                LEVEL_CUSTOMER_BIRTH_DATE,
                LEVEL_CUSTOMER_MARITAL_STATUS,
                LEVEL_CUSTOMER_SUFFIX,
                LEVEL_CUSTOMER_GENDER,
                LEVEL_CUSTOMER_EMAIL_ADDRESS,
                LEVEL_CUSTOMER_YARLY_INCOME,
                LEVEL_CUSTOMER_TOTAL_CHILDREN,
                LEVEL_CUSTOMER_NUMBER_CHILDREN_AT_HOME,
                LEVEL_CUSTOMER_ENGLISH_EDUCATION,
                LEVEL_CUSTOMER_SPANISH_EDUCATION,
                LEVEL_CUSTOMER_FRENCH_EDUCATION,
                LEVEL_CUSTOMER_ENGLISH_OCCUPATION,
                LEVEL_CUSTOMER_SPANISH_OCCUPATION,
                LEVEL_CUSTOMER_FRENCH_OCCUPATION,
                LEVEL_CUSTOMER_HOUSE_OWNER_FLAG,
                LEVEL_CUSTOMER_NUMBER_CARS_OWNED_FLAG,
                LEVEL_CUSTOMER_ADDRESS_LINE1,
                LEVEL_CUSTOMER_ADDRESS_LINE2,
                LEVEL_CUSTOMER_PHONE,
                LEVEL_CUSTOMER_DATE_FIRST_PURCHASE,
                LEVEL_CUSTOMER_COMMUTE_DISTANCE));

        HIERARCHY_EMPLOYEE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_EMPLOYEE.setId("_hierarchy_employee");
        HIERARCHY_EMPLOYEE.setHasAll(true);
        HIERARCHY_EMPLOYEE.setPrimaryKey(COLUMN_EMPLOYEE_KEY_EMPLOYEE);
        HIERARCHY_EMPLOYEE.setQuery(TABLEQUERY_EMPLOYEE);
        HIERARCHY_EMPLOYEE.getLevels().addAll(List.of(
                LEVEL_EMPLOYEE_ROW_NUMBER,
                LEVEL_EMPLOYEE_EMPLOYEE_KEY,
                LEVEL_EMPLOYEE_PARENT_EMPLOYEE_KEY,
                LEVEL_EMPLOYEE_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY,
                LEVEL_EMPLOYEE_PARENT_EMPLOYEE_NATIONAL_ID_ALTERNATE_KEY,
                LEVEL_EMPLOYEE_SALES_TERRITORY_KEY,
                LEVEL_EMPLOYEE_FIRST_NAME,
                LEVEL_EMPLOYEE_LAST_NAME,
                LEVEL_EMPLOYEE_MIDDLE_NAME,
                LEVEL_EMPLOYEE_NAME_STYLE,
                LEVEL_EMPLOYEE_TITLE,
                LEVEL_EMPLOYEE_HIRE_DATE,
                LEVEL_EMPLOYEE_BIRTH_DATE,
                LEVEL_EMPLOYEE_LOGIN_ID,
                LEVEL_EMPLOYEE_EMAIL_ADDRESS,
                LEVEL_EMPLOYEE_PHONE,
                LEVEL_EMPLOYEE_MARITAL_STATUS,
                LEVEL_EMPLOYEE_EMERGENCY_CONTACT_NAME,
                LEVEL_EMPLOYEE_EMERGENCY_CONTACT_PHONE,
                LEVEL_EMPLOYEE_SALARIED_FLAG,
                LEVEL_EMPLOYEE_GENDER,
                LEVEL_EMPLOYEE_PAY_FREQUENCY,
                LEVEL_EMPLOYEE_BASE_RATE,
                LEVEL_EMPLOYEE_VACATION_HOURS,
                LEVEL_EMPLOYEE_SICK_LEAVE_HOURS,
                LEVEL_EMPLOYEE_CURRENT_FLAG,
                LEVEL_EMPLOYEE_SALES_PERSONE_FLAG,
                LEVEL_EMPLOYEE_DEPARTAMENT_NAME,
                LEVEL_EMPLOYEE_START_DATE,
                LEVEL_EMPLOYEE_END_DATE,
                LEVEL_EMPLOYEE_STATUS));

        HIERARCHY_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GEOGRAPHY.setId("_hierarchy_geography");
        HIERARCHY_GEOGRAPHY.setHasAll(true);
        HIERARCHY_GEOGRAPHY.setPrimaryKey(COLUMN_GEOGRAPHY_KEY_GEOGRAPHY);
        HIERARCHY_GEOGRAPHY.setQuery(JOIN_GEOGRAPHY);
        HIERARCHY_GEOGRAPHY.getLevels().addAll(List.of(
                LEVEL_GEOGRAPHY_ROW_NUMBER,
                LEVEL_GEOGRAPHY_GEOGRAPHY_KEY,
                LEVEL_GEOGRAPHY_CITY,
                LEVEL_GEOGRAPHY_STATE_PROVINCE_CODE,
                LEVEL_GEOGRAPHY_STATE_PROVINCE_NAME,
                LEVEL_GEOGRAPHY_COUNTRY_REGION_CODE,
                LEVEL_GEOGRAPHY_ENGLISH_COUNTRY_REGION_NAME,
                LEVEL_GEOGRAPHY_SPANISH_COUNTRY_REGION_NAME,
                LEVEL_GEOGRAPHY_FRENCH_COUNTRY_REGION_NAME,
                LEVEL_GEOGRAPHY_POSTAL_CODE,
                LEVEL_GEOGRAPHY_SALES_TERRITORY_KEY));

        HIERARCHY_PRODUCT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PRODUCT.setId("_hierarchy_poroduct");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setPrimaryKey(COLUMN_PRODUCT_KEY_PRODUCT);
        HIERARCHY_PRODUCT.setQuery(TABLEQUERY_PRODUCT);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(
                LEVEL_PRODUCT_ROW_NUMBER,
                LEVEL_PRODUCT_PRODUCT_KEY,
                LEVEL_PRODUCT_PRODUCT_ALTERNATE_KEY,
                LEVEL_PRODUCT_PRODUCT_SUBCATEGORY_KEY,
                LEVEL_PRODUCT_WEIGHT_UNIT_MEASURE_CODE,
                LEVEL_PRODUCT_SIZE_UNIT_MEASURE_CODE,
                LEVEL_PRODUCT_ENGLISH_PRODUCT_NAME,
                LEVEL_PRODUCT_SPANISH_PRODUCT_NAME,
                LEVEL_PRODUCT_FRENCH_PRODUCT_NAME,
                LEVEL_PRODUCT_STANDART_COST,
                LEVEL_PRODUCT_FINISH_GOODS_FLAG,
                LEVEL_PRODUCT_COLOR,
                LEVEL_PRODUCT_SAFETY_STOCK_LEVEL,
                LEVEL_PRODUCT_REORDER_POINT,
                LEVEL_PRODUCT_LIST_PRICE,
                LEVEL_PRODUCT_SIZE,
                LEVEL_PRODUCT_SIZE_RANGE,
                LEVEL_PRODUCT_WEIGHT,
                LEVEL_PRODUCT_DAYS_TO_MANUFACTURE,
                LEVEL_PRODUCT_PRODUCT_LINE,
                LEVEL_PRODUCT_DEALER_PRICE,
                LEVEL_PRODUCT_CLASS,
                LEVEL_PRODUCT_STYLE,
                LEVEL_PRODUCT_MODEL_NAME,
                LEVEL_PRODUCT_ENGLISH_DESCRIPTION,
                LEVEL_PRODUCT_FRENCH_DESCRIPTION,
                LEVEL_PRODUCT_CHINESE_DESCRIPTION,
                LEVEL_PRODUCT_ARABIC_DESCRIPTION,
                LEVEL_PRODUCT_HEBREW_DESCRIPTION,
                LEVEL_PRODUCT_THAI_DESCRIPTION,
                LEVEL_PRODUCT_START_DATE,
                LEVEL_PRODUCT_END_DATE,
                LEVEL_PRODUCT_STATUS,
                LEVEL_PRODUCT_SUBCATEGORY));

        HIERARCHY_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PRODUCT_CATEGORY.setId("_hierarchy_poroduct_category");
        HIERARCHY_PRODUCT_CATEGORY.setHasAll(true);
        HIERARCHY_PRODUCT_CATEGORY.setPrimaryKey(COLUMN_PRODUCT_KEY_PRODUCT);
        HIERARCHY_PRODUCT_CATEGORY.setQuery(JOIN_PRODUCT_CATEGORY);
        HIERARCHY_PRODUCT_CATEGORY.getLevels().addAll(List.of(
                LEVEL_PRODUCT_CATEGORY_ROW_NUMBER,
                LEVEL_PRODUCT_CATEGORY_PRODUCT_CATEGORY_KEY,
                LEVEL_PRODUCT_CATEGORY_PRODUCT_ALTERNATE_KEY,
                LEVEL_PRODUCT_CATEGORY_ENGLISH_PRODUCT_CATEGORY_NAME,
                LEVEL_PRODUCT_CATEGORY_SPANISH_PRODUCT_CATEGORY_NAME,
                LEVEL_PRODUCT_CATEGORY_FINISH_PRODUCT_CATEGORY_NAME));

        HIERARCHY_PRODUCT_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PRODUCT_SUBCATEGORY.setId("_hierarchy_poroduct_subcategory");
        HIERARCHY_PRODUCT_SUBCATEGORY.setHasAll(true);
        HIERARCHY_PRODUCT_SUBCATEGORY.setPrimaryKey(COLUMN_PRODUCT_KEY_PRODUCT);
        HIERARCHY_PRODUCT_SUBCATEGORY.setQuery(JOIN_PRODUCT_SUBCATEGORY);
        HIERARCHY_PRODUCT_SUBCATEGORY.getLevels().addAll(List.of(
                LEVEL_PRODUCT_SUBCATEGORY_ROW_NUMBER,
                LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY,
                LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_ALTERNATE_KEY,
                LEVEL_PRODUCT_SUBCATEGORY_ENGLISH_PRODUCT_SUBCATEGORY_NAME,
                LEVEL_PRODUCT_SUBCATEGORY_SPANISH_PRODUCT_SUBCATEGORY_NAME,
                LEVEL_PRODUCT_SUBCATEGORY_FINISH_PRODUCT_SUBCATEGORY_NAME,
                LEVEL_PRODUCT_SUBCATEGORY_PRODUCT_CATEGORY_KEY));

        HIERARCHY_STORE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STORE.setId("_hierarchy_store");
        HIERARCHY_STORE.setHasAll(true);
        HIERARCHY_STORE.setPrimaryKey(COLUMN_STORE_KEY_STORE);
        HIERARCHY_STORE.setQuery(TABLEQUERY_STORE);
        HIERARCHY_STORE.getLevels().addAll(List.of(
                LEVEL_STORE_ROW_NUMBER,
                LEVEL_STORE_STORE_KEY,
                LEVEL_STORE_GEOGRAPHY_KEY,
                LEVEL_STORE_STORE_NAME,
                LEVEL_STORE_NUMBER_OF_EMPLOYEES,
                LEVEL_STORE_SALES));

        HIERARCHY_TIME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_TIME.setId("_hierarchy_time");
        HIERARCHY_TIME.setHasAll(true);
        HIERARCHY_TIME.setPrimaryKey(COLUMN_TIME_KEY_TIME);
        HIERARCHY_TIME.setQuery(TABLEQUERY_TIME);
        HIERARCHY_TIME.getLevels().addAll(List.of(
                LEVEL_TIME_ROW_NUMBER,
                LEVEL_TIME_TIME_KEY,
                LEVEL_TIME_FULL_DATE_ALTERNATE_KEY,
                LEVEL_TIME_DAY_NUMBER_OF_WEEK,
                LEVEL_TIME_ENGLISH_DAY_NAME_OF_WEEK,
                LEVEL_TIME_SPANISH_DAY_NAME_OF_WEEK,
                LEVEL_TIME_FRENCH_DAY_NAME_OF_WEEK,
                LEVEL_TIME_DAY_NUMBER_OF_MONTH,
                LEVEL_TIME_DAY_NUMBER_OF_YEAR,
                LEVEL_TIME_WEEK_NUMBER_OF_YEAR,
                LEVEL_TIME_ENGLISH_MONTH_NAME,
                LEVEL_TIME_SPANISH_MONTH_NAME,
                LEVEL_TIME_FRENCH_MONTH_NAME,
                LEVEL_TIME_MONTH_NUMBER_OF_YEAR,
                LEVEL_TIME_CALENDAR_QUARTER,
                LEVEL_TIME_CALENDAR_YEAR,
                LEVEL_TIME_CALENDAR_SEMESTER,
                LEVEL_TIME_FISCAL_QUARTER,
                LEVEL_TIME_FISCAL_YEAR,
                LEVEL_TIME_FISCAL_SEMESTER));

        DIMENSION_DIM_CUSTOMER = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_CUSTOMER.setName("DimCustomer");
        DIMENSION_DIM_CUSTOMER.setId("_dimension_customer");
        DIMENSION_DIM_CUSTOMER.getHierarchies().add(HIERARCHY_CUSTOMER);

        DIMENSION_DIM_EMPLOYEE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_EMPLOYEE.setName("DimEmployee");
        DIMENSION_DIM_EMPLOYEE.setId("_dimension_employee");
        DIMENSION_DIM_EMPLOYEE.getHierarchies().add(HIERARCHY_EMPLOYEE);

        DIMENSION_DIM_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_GEOGRAPHY.setName("DimGeography");
        DIMENSION_DIM_GEOGRAPHY.setId("_dimension_geography");
        DIMENSION_DIM_GEOGRAPHY.getHierarchies().add(HIERARCHY_GEOGRAPHY);

        DIMENSION_DIM_PRODUCT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_PRODUCT.setName("DimProduct");
        DIMENSION_DIM_PRODUCT.setId("_dimension_product");
        DIMENSION_DIM_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        DIMENSION_DIM_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_PRODUCT_CATEGORY.setName("DimProductCategory");
        DIMENSION_DIM_PRODUCT_CATEGORY.setId("_dimension_product_category");
        DIMENSION_DIM_PRODUCT_CATEGORY.getHierarchies().add(HIERARCHY_PRODUCT_CATEGORY);

        DIMENSION_DIM_PRODUCT_SUB_CATEGORY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_PRODUCT_SUB_CATEGORY.setName("DimProductSubcategory");
        DIMENSION_DIM_PRODUCT_SUB_CATEGORY.setId("_dimension_product_subcategory");
        DIMENSION_DIM_PRODUCT_SUB_CATEGORY.getHierarchies().add(HIERARCHY_PRODUCT_SUBCATEGORY);

        DIMENSION_DIM_STORE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_STORE.setName("DimStore");
        DIMENSION_DIM_STORE.setId("_dimension_store");
        DIMENSION_DIM_STORE.getHierarchies().add(HIERARCHY_STORE);

        DIMENSION_DIM_TIME = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DIM_TIME.setName("DimTime");
        DIMENSION_DIM_TIME.setId("_dimension_time");
        DIMENSION_DIM_TIME.getHierarchies().add(HIERARCHY_TIME);

        CONNECTOR_DIM_CUSTOMER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_CUSTOMER.setDimension(DIMENSION_DIM_CUSTOMER);
        CONNECTOR_DIM_CUSTOMER.setForeignKey(COLUMN_CUSTOMER_KEY_FACT);
        CONNECTOR_DIM_CUSTOMER.setId("_connector_DimCustomer");
        CONNECTOR_DIM_CUSTOMER.setOverrideDimensionName("DimCustomer");

        CONNECTOR_DIM_EMPLOYEE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_EMPLOYEE.setDimension(DIMENSION_DIM_EMPLOYEE);
        CONNECTOR_DIM_EMPLOYEE.setForeignKey(COLUMN_EMPLOYEE_KEY_FACT);
        CONNECTOR_DIM_EMPLOYEE.setId("_connector_DimEmployee");
        CONNECTOR_DIM_EMPLOYEE.setOverrideDimensionName("DimEmployee");

        CONNECTOR_DIM_GEOGRAPHY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_GEOGRAPHY.setDimension(DIMENSION_DIM_GEOGRAPHY);
        CONNECTOR_DIM_GEOGRAPHY.setForeignKey(COLUMN_CUSTOMER_KEY_FACT);
        CONNECTOR_DIM_GEOGRAPHY.setId("_connector_DimGeography");
        CONNECTOR_DIM_GEOGRAPHY.setOverrideDimensionName("DimGeography");

        CONNECTOR_DIM_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_PRODUCT.setDimension(DIMENSION_DIM_PRODUCT);
        CONNECTOR_DIM_PRODUCT.setForeignKey(COLUMN_PRODUCT_KEY_FACT);
        CONNECTOR_DIM_PRODUCT.setId("_connector_DimProduct");
        CONNECTOR_DIM_PRODUCT.setOverrideDimensionName("DimProduct");

        CONNECTOR_DIM_PRODUCT_CATEGORY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_PRODUCT_CATEGORY.setDimension(DIMENSION_DIM_PRODUCT_CATEGORY);
        CONNECTOR_DIM_PRODUCT_CATEGORY.setForeignKey(COLUMN_PRODUCT_KEY_FACT);
        CONNECTOR_DIM_PRODUCT_CATEGORY.setId("_connector_DimProductCategory");
        CONNECTOR_DIM_PRODUCT_CATEGORY.setOverrideDimensionName("DimProductCategory");

        CONNECTOR_DIM_PRODUCT_SUB_CATEGORY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_PRODUCT_SUB_CATEGORY.setDimension(DIMENSION_DIM_PRODUCT_SUB_CATEGORY);
        CONNECTOR_DIM_PRODUCT_SUB_CATEGORY.setForeignKey(COLUMN_PRODUCT_KEY_FACT);
        CONNECTOR_DIM_PRODUCT_SUB_CATEGORY.setId("_connector_DimProductSubCategory");
        CONNECTOR_DIM_PRODUCT_SUB_CATEGORY.setOverrideDimensionName("DimProductSubCategory");

        CONNECTOR_DIM_STORE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_STORE.setDimension(DIMENSION_DIM_STORE);
        CONNECTOR_DIM_STORE.setForeignKey(COLUMN_STORE_KEY_FACT);
        CONNECTOR_DIM_STORE.setId("_connector_DimStore");
        CONNECTOR_DIM_STORE.setOverrideDimensionName("DimStore");

        CONNECTOR_DIM_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIM_TIME.setDimension(DIMENSION_DIM_TIME);
        CONNECTOR_DIM_TIME.setForeignKey(COLUMN_ORDER_DATE_KEY_FACT); //TODO add "DueDateKey","ShipDateKey"
        CONNECTOR_DIM_TIME.setId("_connector_DimTime");
        CONNECTOR_DIM_TIME.setOverrideDimensionName("DimTime");

        MEASURE_FACT_INTERNET_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_FACT_INTERNET_SALES.setName(MEASURE_NAME);
        MEASURE_FACT_INTERNET_SALES.setId("_measure_fact_internet_sales");
        MEASURE_FACT_INTERNET_SALES.setColumn(COLUMN_SALES_AMOUNT_FACT);

        MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASURE_GROUP.getMeasures().addAll(List.of(MEASURE_FACT_INTERNET_SALES));

        CUBE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE.setName(CUBE_NAME);
        CUBE.setId("_cube_DescriptionRolePlayingDimensionsDB");
        CUBE.getDimensionConnectors().addAll(List.of(
                CONNECTOR_DIM_CUSTOMER,
                CONNECTOR_DIM_EMPLOYEE,
                CONNECTOR_DIM_GEOGRAPHY,
                CONNECTOR_DIM_PRODUCT,
                CONNECTOR_DIM_PRODUCT_CATEGORY,
                CONNECTOR_DIM_PRODUCT_SUB_CATEGORY,
                CONNECTOR_DIM_STORE,
                CONNECTOR_DIM_TIME));
        CUBE.setQuery(TABLEQUERY_FACT);
        CUBE.getMeasureGroups().add(MEASURE_GROUP);

        CATALOG = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG.getDbschemas().add(DATABASE_SCHEMA);
        CATALOG.setId("_catalog_csdl1");
        CATALOG.setName(CATALOG_NAME);
        CATALOG.setDescription(CATALOG_DESCRIPTION);
        CATALOG.getCubes().add(CUBE);

        document(CATALOG, CATALOG_NAME, introBody, 1, 0, 0, false, 0);
        document(DATABASE_SCHEMA, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(TABLEQUERY_FACT, "Query", queryBody, 1, 2, 0, true, 2);
        document(CUBE, "Cube CSDLBI 1.0", cubeBody, 1, 3, 0, true, 2);
    }

    @Override
    public Catalog get() {
        return CATALOG;
    }

}
