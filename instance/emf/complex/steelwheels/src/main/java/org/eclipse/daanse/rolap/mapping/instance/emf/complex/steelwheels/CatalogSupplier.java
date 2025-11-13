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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.steelwheels;

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
import org.eclipse.daanse.rolap.mapping.model.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TimeDimension;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.5", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    // Static columns - Order Fact Table
    public static final Column COLUMN_CUSTOMERNUMBER_ORDERFACT;
    public static final Column COLUMN_PRODUCTCODE_ORDERFACT;
    public static final Column COLUMN_TIME_ID_ORDERFACT;
    public static final Column COLUMN_QUANTITYORDERED_ORDERFACT;
    public static final Column COLUMN_TOTALPRICE_ORDERFACT;
    public static final Column COLUMN_STATUS_ORDERFACT;
    public static final Column COLUMN_ORDERDATE_ORDERFACT;
    public static final Column COLUMN_PRICEEACH_ORDERFACT;
    public static final Column COLUMN_REQUIREDDATE_ORDERFACT;
    public static final Column COLUMN_SHIPPEDDATE_ORDERFACT;

    // Static columns - Customer Table
    public static final Column COLUMN_CUSTOMERNUMBER_CUSTOMER;
    public static final Column COLUMN_CUSTOMERNAME_CUSTOMER;
    public static final Column COLUMN_TERRITORY_CUSTOMER;
    public static final Column COLUMN_COUNTRY_CUSTOMER;
    public static final Column COLUMN_STATE_CUSTOMER;
    public static final Column COLUMN_CITY_CUSTOMER;
    public static final Column COLUMN_CONTACTFIRSTNAME_CUSTOMER;
    public static final Column COLUMN_CONTACTLASTNAME_CUSTOMER;
    public static final Column COLUMN_PHONE_CUSTOMER;
    public static final Column COLUMN_ADDRESSLINE1_CUSTOMER;
    public static final Column COLUMN_CREDITLIMIT_CUSTOMER;

    // Static columns - Products Table
    public static final Column COLUMN_PRODUCTCODE_PRODUCTS;
    public static final Column COLUMN_PRODUCTNAME_PRODUCTS;
    public static final Column COLUMN_PRODUCTLINE_PRODUCTS;
    public static final Column COLUMN_PRODUCTVENDOR_PRODUCTS;
    public static final Column COLUMN_PRODUCTDESCRIPTION_PRODUCTS;

    // Static columns - Time Table
    public static final Column COLUMN_TIME_ID_TIME;
    public static final Column COLUMN_YEAR_ID_TIME;
    public static final Column COLUMN_QTR_NAME_TIME;
    public static final Column COLUMN_QTR_ID_TIME;
    public static final Column COLUMN_MONTH_NAME_TIME;
    public static final Column COLUMN_MONTH_ID_TIME;

    // Static tables
    public static final PhysicalTable TABLE_ORDERFACT;
    public static final PhysicalTable TABLE_CUSTOMER;
    public static final PhysicalTable TABLE_PRODUCTS;
    public static final PhysicalTable TABLE_TIME;

    // Static levels
    public static final Level LEVEL_MARKETS_TERRITORY;
    public static final Level LEVEL_MARKETS_COUNTRY;
    public static final Level LEVEL_MARKETS_STATE;
    public static final Level LEVEL_MARKETS_CITY;
    public static final Level LEVEL_CUSTOMERS_CUSTOMER;
    public static final Level LEVEL_PRODUCT_LINE;
    public static final Level LEVEL_PRODUCT_VENDOR;
    public static final Level LEVEL_PRODUCT_PRODUCT;
    public static final Level LEVEL_TIME_YEARS;
    public static final Level LEVEL_TIME_QUARTERS;
    public static final Level LEVEL_TIME_MONTHS;
    public static final Level LEVEL_ORDERSTATUS_TYPE;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_MARKETS;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS;
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_TIME;
    public static final ExplicitHierarchy HIERARCHY_ORDERSTATUS;

    // Static dimensions
    public static final StandardDimension DIMENSION_MARKETS;
    public static final StandardDimension DIMENSION_CUSTOMERS;
    public static final StandardDimension DIMENSION_PRODUCT;
    public static final TimeDimension DIMENSION_TIME;
    public static final StandardDimension DIMENSION_ORDERSTATUS;

    // Static cube
    public static final PhysicalCube CUBE_STEELWHEELSSALES;

    // Static table queries
    public static final TableQuery TABLEQUERY_CUSTOMER;
    public static final TableQuery TABLEQUERY_PRODUCTS;
    public static final TableQuery TABLEQUERY_TIME;
    public static final TableQuery TABLEQUERY_ORDERSTATUS;
    public static final TableQuery TABLEQUERY_FACT;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_MARKETS;
    public static final DimensionConnector CONNECTOR_CUSTOMERS;
    public static final DimensionConnector CONNECTOR_PRODUCT;
    public static final DimensionConnector CONNECTOR_TIME;
    public static final DimensionConnector CONNECTOR_ORDERSTATUS;

    // Static measures and measure group
    public static final SumMeasure MEASURE_QUANTITY;
    public static final SumMeasure MEASURE_SALES;
    public static final MeasureGroup MEASUREGROUP_STEELWHEELSSALES;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_STEELWHEELS;
    public static final Catalog CATALOG_STEELWHEELS;

    private static final String steelWheelsBody = """
            SteelWheels is a sample database representing a classic car and motorcycle sales company.
            It contains order data with product information, customer details, and time-based sales transactions
            for analyzing business performance across different markets and product lines.
            """;

    private static final String queryCustomerBody = """
            The Query selects all columns from the customer_w_ter table.
            """;

    private static final String queryProductBody = """
            The Query selects all columns from the products table.
            """;

    private static final String queryTimeBody = """
            The Query selects all columns from the time table.
            """;

    private static final String queryStatusBody = """
            The Query selects all columns from the orderfact table.
            """;

    private static final String queryBody = """
            The Query selects all columns from the orderfact table for cube measures and dimension connections.
            """;

    private static final String salesCubeBody = """
            The SteelWheels Sales cube contains order-level transactions with quantity and sales amount measures.
            It provides analysis capabilities across customer markets, product categories, and time periods.
            """;

    private static final String marketsBody = """
            The Markets dimension represents customer territories and geographic regions
            where SteelWheels operates, enabling regional sales analysis.
            """;

    private static final String customersBody = """
            The Customers dimension contains individual customer information
            for detailed customer-level sales analysis and segmentation.
            """;

    private static final String productBody = """
            The Product dimension organizes the classic car and motorcycle inventory
            into product lines, vendors, and individual product details for sales analysis.
            """;

    private static final String timeBody = """
            The Time dimension provides temporal analysis capabilities with yearly
            and quarterly breakdowns for trend analysis and seasonal comparisons.
            """;

    static {
        // Initialize Order Fact columns
        COLUMN_CUSTOMERNUMBER_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMERNUMBER_ORDERFACT.setName("CUSTOMERNUMBER");
        COLUMN_CUSTOMERNUMBER_ORDERFACT.setId("_column_orderfact_customernumber");
        COLUMN_CUSTOMERNUMBER_ORDERFACT.setType(ColumnType.INTEGER);

        COLUMN_PRODUCTCODE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTCODE_ORDERFACT.setName("PRODUCTCODE");
        COLUMN_PRODUCTCODE_ORDERFACT.setId("_column_orderfact_productcode");
        COLUMN_PRODUCTCODE_ORDERFACT.setType(ColumnType.VARCHAR);

        COLUMN_TIME_ID_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_ORDERFACT.setName("TIME_ID");
        COLUMN_TIME_ID_ORDERFACT.setId("_column_orderfact_timeid");
        COLUMN_TIME_ID_ORDERFACT.setType(ColumnType.VARCHAR);

        COLUMN_QUANTITYORDERED_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QUANTITYORDERED_ORDERFACT.setName("QUANTITYORDERED");
        COLUMN_QUANTITYORDERED_ORDERFACT.setId("_column_orderfact_quantityordered");
        COLUMN_QUANTITYORDERED_ORDERFACT.setType(ColumnType.INTEGER);

        COLUMN_TOTALPRICE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTALPRICE_ORDERFACT.setName("TOTALPRICE");
        COLUMN_TOTALPRICE_ORDERFACT.setId("_column_orderfact_totalprice");
        COLUMN_TOTALPRICE_ORDERFACT.setType(ColumnType.NUMERIC);

        COLUMN_STATUS_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATUS_ORDERFACT.setName("STATUS");
        COLUMN_STATUS_ORDERFACT.setId("_column_orderfact_status");
        COLUMN_STATUS_ORDERFACT.setType(ColumnType.VARCHAR);

        COLUMN_ORDERDATE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ORDERDATE_ORDERFACT.setName("ORDERDATE");
        COLUMN_ORDERDATE_ORDERFACT.setId("_column_orderfact_orderdate");
        COLUMN_ORDERDATE_ORDERFACT.setType(ColumnType.TIMESTAMP);

        COLUMN_PRICEEACH_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRICEEACH_ORDERFACT.setName("PRICEEACH");
        COLUMN_PRICEEACH_ORDERFACT.setId("_column_orderfact_priceeach");
        COLUMN_PRICEEACH_ORDERFACT.setType(ColumnType.NUMERIC);

        COLUMN_REQUIREDDATE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REQUIREDDATE_ORDERFACT.setName("REQUIREDDATE");
        COLUMN_REQUIREDDATE_ORDERFACT.setId("_column_orderfact_requireddate");
        COLUMN_REQUIREDDATE_ORDERFACT.setType(ColumnType.TIMESTAMP);

        COLUMN_SHIPPEDDATE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SHIPPEDDATE_ORDERFACT.setName("SHIPPEDDATE");
        COLUMN_SHIPPEDDATE_ORDERFACT.setId("_column_orderfact_shippeddate");
        COLUMN_SHIPPEDDATE_ORDERFACT.setType(ColumnType.TIMESTAMP);

        // Initialize Customer Table columns
        COLUMN_CUSTOMERNUMBER_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMERNUMBER_CUSTOMER.setName("CUSTOMERNUMBER");
        COLUMN_CUSTOMERNUMBER_CUSTOMER.setId("_column_customer_customernumber");
        COLUMN_CUSTOMERNUMBER_CUSTOMER.setType(ColumnType.INTEGER);

        COLUMN_CUSTOMERNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMERNAME_CUSTOMER.setName("CUSTOMERNAME");
        COLUMN_CUSTOMERNAME_CUSTOMER.setId("_column_customer_customername");
        COLUMN_CUSTOMERNAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_TERRITORY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TERRITORY_CUSTOMER.setName("TERRITORY");
        COLUMN_TERRITORY_CUSTOMER.setId("_column_customer_territory");
        COLUMN_TERRITORY_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_COUNTRY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_CUSTOMER.setName("COUNTRY");
        COLUMN_COUNTRY_CUSTOMER.setId("_column_customer_country");
        COLUMN_COUNTRY_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_STATE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATE_CUSTOMER.setName("STATE");
        COLUMN_STATE_CUSTOMER.setId("_column_customer_state");
        COLUMN_STATE_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_CITY_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CITY_CUSTOMER.setName("CITY");
        COLUMN_CITY_CUSTOMER.setId("_column_customer_city");
        COLUMN_CITY_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_CONTACTFIRSTNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CONTACTFIRSTNAME_CUSTOMER.setName("CONTACTFIRSTNAME");
        COLUMN_CONTACTFIRSTNAME_CUSTOMER.setId("_column_customer_contactfirstname");
        COLUMN_CONTACTFIRSTNAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_CONTACTLASTNAME_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CONTACTLASTNAME_CUSTOMER.setName("CONTACTLASTNAME");
        COLUMN_CONTACTLASTNAME_CUSTOMER.setId("_column_customer_contactlastname");
        COLUMN_CONTACTLASTNAME_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_PHONE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PHONE_CUSTOMER.setName("PHONE");
        COLUMN_PHONE_CUSTOMER.setId("_column_customer_phone");
        COLUMN_PHONE_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_ADDRESSLINE1_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ADDRESSLINE1_CUSTOMER.setName("ADDRESSLINE1");
        COLUMN_ADDRESSLINE1_CUSTOMER.setId("_column_customer_addressline1");
        COLUMN_ADDRESSLINE1_CUSTOMER.setType(ColumnType.VARCHAR);

        COLUMN_CREDITLIMIT_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CREDITLIMIT_CUSTOMER.setName("CREDITLIMIT");
        COLUMN_CREDITLIMIT_CUSTOMER.setId("_column_customer_creditlimit");
        COLUMN_CREDITLIMIT_CUSTOMER.setType(ColumnType.NUMERIC);

        // Initialize Products Table columns
        COLUMN_PRODUCTCODE_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTCODE_PRODUCTS.setName("PRODUCTCODE");
        COLUMN_PRODUCTCODE_PRODUCTS.setId("_column_products_productcode");
        COLUMN_PRODUCTCODE_PRODUCTS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCTNAME_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTNAME_PRODUCTS.setName("PRODUCTNAME");
        COLUMN_PRODUCTNAME_PRODUCTS.setId("_column_products_productname");
        COLUMN_PRODUCTNAME_PRODUCTS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCTLINE_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTLINE_PRODUCTS.setName("PRODUCTLINE");
        COLUMN_PRODUCTLINE_PRODUCTS.setId("_column_products_productline");
        COLUMN_PRODUCTLINE_PRODUCTS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCTVENDOR_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTVENDOR_PRODUCTS.setName("PRODUCTVENDOR");
        COLUMN_PRODUCTVENDOR_PRODUCTS.setId("_column_products_productvendor");
        COLUMN_PRODUCTVENDOR_PRODUCTS.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCTDESCRIPTION_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCTDESCRIPTION_PRODUCTS.setName("PRODUCTDESCRIPTION");
        COLUMN_PRODUCTDESCRIPTION_PRODUCTS.setId("_column_products_productdescription");
        COLUMN_PRODUCTDESCRIPTION_PRODUCTS.setType(ColumnType.VARCHAR);

        // Initialize Time Table columns
        COLUMN_TIME_ID_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TIME_ID_TIME.setName("TIME_ID");
        COLUMN_TIME_ID_TIME.setId("_column_time_timeid");
        COLUMN_TIME_ID_TIME.setType(ColumnType.VARCHAR);

        COLUMN_YEAR_ID_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_YEAR_ID_TIME.setName("YEAR_ID");
        COLUMN_YEAR_ID_TIME.setId("_column_time_yearid");
        COLUMN_YEAR_ID_TIME.setType(ColumnType.INTEGER);

        COLUMN_QTR_NAME_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QTR_NAME_TIME.setName("QTR_NAME");
        COLUMN_QTR_NAME_TIME.setId("_column_time_qtrname");
        COLUMN_QTR_NAME_TIME.setType(ColumnType.VARCHAR);

        COLUMN_QTR_ID_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_QTR_ID_TIME.setName("QTR_ID");
        COLUMN_QTR_ID_TIME.setId("_column_time_qtrid");
        COLUMN_QTR_ID_TIME.setType(ColumnType.INTEGER);

        COLUMN_MONTH_NAME_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_NAME_TIME.setName("MONTH_NAME");
        COLUMN_MONTH_NAME_TIME.setId("_column_time_monthname");
        COLUMN_MONTH_NAME_TIME.setType(ColumnType.VARCHAR);

        COLUMN_MONTH_ID_TIME = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MONTH_ID_TIME.setName("MONTH_ID");
        COLUMN_MONTH_ID_TIME.setId("_column_time_monthid");
        COLUMN_MONTH_ID_TIME.setType(ColumnType.INTEGER);

        // Initialize tables
        TABLE_ORDERFACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_ORDERFACT.setName("orderfact");
        TABLE_ORDERFACT.setId("_table_orderfact");
        TABLE_ORDERFACT.getColumns()
                .addAll(List.of(COLUMN_CUSTOMERNUMBER_ORDERFACT, COLUMN_PRODUCTCODE_ORDERFACT, COLUMN_TIME_ID_ORDERFACT,
                        COLUMN_QUANTITYORDERED_ORDERFACT, COLUMN_TOTALPRICE_ORDERFACT, COLUMN_STATUS_ORDERFACT,
                        COLUMN_ORDERDATE_ORDERFACT, COLUMN_PRICEEACH_ORDERFACT, COLUMN_REQUIREDDATE_ORDERFACT,
                        COLUMN_SHIPPEDDATE_ORDERFACT));

        TABLE_CUSTOMER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CUSTOMER.setName("customer_w_ter");
        TABLE_CUSTOMER.setId("_table_customerWTer");
        TABLE_CUSTOMER.getColumns()
                .addAll(List.of(COLUMN_CUSTOMERNUMBER_CUSTOMER, COLUMN_CUSTOMERNAME_CUSTOMER, COLUMN_TERRITORY_CUSTOMER,
                        COLUMN_COUNTRY_CUSTOMER, COLUMN_STATE_CUSTOMER, COLUMN_CITY_CUSTOMER,
                        COLUMN_CONTACTFIRSTNAME_CUSTOMER, COLUMN_CONTACTLASTNAME_CUSTOMER, COLUMN_PHONE_CUSTOMER,
                        COLUMN_ADDRESSLINE1_CUSTOMER, COLUMN_CREDITLIMIT_CUSTOMER));

        TABLE_PRODUCTS = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PRODUCTS.setName("products");
        TABLE_PRODUCTS.setId("_table_products");
        TABLE_PRODUCTS.getColumns().addAll(List.of(COLUMN_PRODUCTCODE_PRODUCTS, COLUMN_PRODUCTNAME_PRODUCTS,
                COLUMN_PRODUCTLINE_PRODUCTS, COLUMN_PRODUCTVENDOR_PRODUCTS, COLUMN_PRODUCTDESCRIPTION_PRODUCTS));

        TABLE_TIME = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_TIME.setName("time");
        TABLE_TIME.setId("_table_time");
        TABLE_TIME.getColumns().addAll(List.of(COLUMN_TIME_ID_TIME, COLUMN_YEAR_ID_TIME, COLUMN_QTR_NAME_TIME,
                COLUMN_QTR_ID_TIME, COLUMN_MONTH_NAME_TIME, COLUMN_MONTH_ID_TIME));

        // Initialize levels
        LEVEL_MARKETS_TERRITORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_TERRITORY.setName("Territory");
        LEVEL_MARKETS_TERRITORY.setColumn(COLUMN_TERRITORY_CUSTOMER);
        LEVEL_MARKETS_TERRITORY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_TERRITORY.setUniqueMembers(true);
        LEVEL_MARKETS_TERRITORY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_TERRITORY.setId("_level_markets_territory");
        LEVEL_MARKETS_TERRITORY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_COUNTRY.setName("Country");
        LEVEL_MARKETS_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);
        LEVEL_MARKETS_COUNTRY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_COUNTRY.setUniqueMembers(false);
        LEVEL_MARKETS_COUNTRY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_COUNTRY.setId("_level_markets_country");
        LEVEL_MARKETS_COUNTRY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_STATE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_STATE.setName("State Province");
        LEVEL_MARKETS_STATE.setColumn(COLUMN_STATE_CUSTOMER);
        LEVEL_MARKETS_STATE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_STATE.setUniqueMembers(true);
        LEVEL_MARKETS_STATE.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_STATE.setId("_level_markets_state");
        LEVEL_MARKETS_STATE.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_CITY.setName("City");
        LEVEL_MARKETS_CITY.setColumn(COLUMN_CITY_CUSTOMER);
        LEVEL_MARKETS_CITY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_CITY.setUniqueMembers(true);
        LEVEL_MARKETS_CITY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_CITY.setId("_level_markets_city");
        LEVEL_MARKETS_CITY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_CUSTOMERS_CUSTOMER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMERS_CUSTOMER.setName("Customer");
        LEVEL_CUSTOMERS_CUSTOMER.setColumn(COLUMN_CUSTOMERNAME_CUSTOMER);
        LEVEL_CUSTOMERS_CUSTOMER.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMERS_CUSTOMER.setUniqueMembers(true);
        LEVEL_CUSTOMERS_CUSTOMER.setType(LevelDefinition.REGULAR);
        LEVEL_CUSTOMERS_CUSTOMER.setId("_level_customers_customer");
        LEVEL_CUSTOMERS_CUSTOMER.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_LINE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_LINE.setName("Line");
        LEVEL_PRODUCT_LINE.setColumn(COLUMN_PRODUCTLINE_PRODUCTS);
        LEVEL_PRODUCT_LINE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_LINE.setUniqueMembers(true);
        LEVEL_PRODUCT_LINE.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_LINE.setId("_level_product_line");
        LEVEL_PRODUCT_LINE.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_VENDOR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_VENDOR.setName("Vendor");
        LEVEL_PRODUCT_VENDOR.setColumn(COLUMN_PRODUCTVENDOR_PRODUCTS);
        LEVEL_PRODUCT_VENDOR.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_VENDOR.setUniqueMembers(true);
        LEVEL_PRODUCT_VENDOR.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_VENDOR.setId("_level_product_vendor");
        LEVEL_PRODUCT_VENDOR.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_PRODUCT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT.setName("Product");
        LEVEL_PRODUCT_PRODUCT.setColumn(COLUMN_PRODUCTNAME_PRODUCTS);
        LEVEL_PRODUCT_PRODUCT.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_PRODUCT.setUniqueMembers(true);
        LEVEL_PRODUCT_PRODUCT.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_PRODUCT.setId("_level_product_product");
        LEVEL_PRODUCT_PRODUCT.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_YEARS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_YEARS.setName("Years");
        LEVEL_TIME_YEARS.setColumn(COLUMN_YEAR_ID_TIME);
        LEVEL_TIME_YEARS.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_TIME_YEARS.setUniqueMembers(true);
        LEVEL_TIME_YEARS.setType(LevelDefinition.TIME_YEARS);
        LEVEL_TIME_YEARS.setId("_level_time_years");
        LEVEL_TIME_YEARS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_QUARTERS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_QUARTERS.setName("Quarters");
        LEVEL_TIME_QUARTERS.setColumn(COLUMN_QTR_NAME_TIME);
        LEVEL_TIME_QUARTERS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_TIME_QUARTERS.setUniqueMembers(false);
        LEVEL_TIME_QUARTERS.setType(LevelDefinition.TIME_QUARTERS);
        LEVEL_TIME_QUARTERS.setId("_level_time_quarters");
        LEVEL_TIME_QUARTERS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_MONTHS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_TIME_MONTHS.setName("Months");
        LEVEL_TIME_MONTHS.setColumn(COLUMN_MONTH_NAME_TIME);
        LEVEL_TIME_MONTHS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_TIME_MONTHS.setUniqueMembers(false);
        LEVEL_TIME_MONTHS.setType(LevelDefinition.TIME_MONTHS);
        LEVEL_TIME_MONTHS.setId("_level_time_months");
        LEVEL_TIME_MONTHS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_ORDERSTATUS_TYPE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ORDERSTATUS_TYPE.setName("Type");
        LEVEL_ORDERSTATUS_TYPE.setColumn(COLUMN_STATUS_ORDERFACT);
        LEVEL_ORDERSTATUS_TYPE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_ORDERSTATUS_TYPE.setUniqueMembers(true);
        LEVEL_ORDERSTATUS_TYPE.setType(LevelDefinition.REGULAR);
        LEVEL_ORDERSTATUS_TYPE.setId("_level_orderstatus_type");
        LEVEL_ORDERSTATUS_TYPE.setHideMemberIf(HideMemberIf.NEVER);

        // Initialize table queries
        TABLEQUERY_CUSTOMER = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_CUSTOMER.setId("_query_customer");
        TABLEQUERY_CUSTOMER.setTable(TABLE_CUSTOMER);

        TABLEQUERY_PRODUCTS = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PRODUCTS.setId("_query_products");
        TABLEQUERY_PRODUCTS.setTable(TABLE_PRODUCTS);

        TABLEQUERY_TIME = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_TIME.setId("_query_time");
        TABLEQUERY_TIME.setTable(TABLE_TIME);

        TABLEQUERY_ORDERSTATUS = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_ORDERSTATUS.setId("_query_orderstatus");
        TABLEQUERY_ORDERSTATUS.setTable(TABLE_ORDERFACT);

        TABLEQUERY_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_FACT.setId("_query_fact");
        TABLEQUERY_FACT.setTable(TABLE_ORDERFACT);

        // Initialize hierarchies
        HIERARCHY_MARKETS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_MARKETS.setName("Markets Hierarchy");
        HIERARCHY_MARKETS.setId("_hierarchy_markets");
        HIERARCHY_MARKETS.setHasAll(true);
        HIERARCHY_MARKETS.setPrimaryKey(COLUMN_CUSTOMERNUMBER_CUSTOMER);
        HIERARCHY_MARKETS.setAllMemberName("All Markets");
        HIERARCHY_MARKETS.setQuery(TABLEQUERY_CUSTOMER);
        HIERARCHY_MARKETS.getLevels().addAll(
                List.of(LEVEL_MARKETS_TERRITORY, LEVEL_MARKETS_COUNTRY, LEVEL_MARKETS_STATE, LEVEL_MARKETS_CITY));

        HIERARCHY_CUSTOMERS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_CUSTOMERS.setName("Customers Hierarchy");
        HIERARCHY_CUSTOMERS.setId("_hierarchy_customers");
        HIERARCHY_CUSTOMERS.setHasAll(true);
        HIERARCHY_CUSTOMERS.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMERS.setPrimaryKey(COLUMN_CUSTOMERNUMBER_CUSTOMER);
        HIERARCHY_CUSTOMERS.setQuery(TABLEQUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS.getLevels().add(LEVEL_CUSTOMERS_CUSTOMER);

        HIERARCHY_PRODUCT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PRODUCT.setName("Product Hierarchy");
        HIERARCHY_PRODUCT.setId("_hierarchy_product");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setAllMemberName("All Products");
        HIERARCHY_PRODUCT.setPrimaryKey(COLUMN_PRODUCTCODE_PRODUCTS);
        HIERARCHY_PRODUCT.setQuery(TABLEQUERY_PRODUCTS);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(LEVEL_PRODUCT_LINE, LEVEL_PRODUCT_VENDOR, LEVEL_PRODUCT_PRODUCT));

        HIERARCHY_TIME = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_TIME.setName("Time Hierarchy");
        HIERARCHY_TIME.setId("_hierarchy_time");
        HIERARCHY_TIME.setHasAll(true);
        HIERARCHY_TIME.setAllMemberName("All Years");
        HIERARCHY_TIME.setPrimaryKey(COLUMN_TIME_ID_TIME);
        HIERARCHY_TIME.setQuery(TABLEQUERY_TIME);
        HIERARCHY_TIME.getLevels().addAll(List.of(LEVEL_TIME_YEARS, LEVEL_TIME_QUARTERS, LEVEL_TIME_MONTHS));

        HIERARCHY_ORDERSTATUS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_ORDERSTATUS.setName("Order Status Hierarchy");
        HIERARCHY_ORDERSTATUS.setId("_hierarchy_orderstatus");
        HIERARCHY_ORDERSTATUS.setHasAll(true);
        HIERARCHY_ORDERSTATUS.setAllMemberName("All Status Types");
        HIERARCHY_ORDERSTATUS.setPrimaryKey(COLUMN_STATUS_ORDERFACT);
        //HIERARCHY_ORDERSTATUS.setQuery(TABLEQUERY_ORDERSTATUS);
        HIERARCHY_ORDERSTATUS.getLevels().add(LEVEL_ORDERSTATUS_TYPE);

        // Initialize dimensions
        DIMENSION_MARKETS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_MARKETS.setName("Markets");
        DIMENSION_MARKETS.setId("_dimension_markets");
        DIMENSION_MARKETS.getHierarchies().add(HIERARCHY_MARKETS);

        DIMENSION_CUSTOMERS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMERS.setName("Customers");
        DIMENSION_CUSTOMERS.setId("_dimension_customers");
        DIMENSION_CUSTOMERS.getHierarchies().add(HIERARCHY_CUSTOMERS);

        DIMENSION_PRODUCT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PRODUCT.setName("Product");
        DIMENSION_PRODUCT.setId("_dimension_product");
        DIMENSION_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        DIMENSION_TIME = RolapMappingFactory.eINSTANCE.createTimeDimension();
        DIMENSION_TIME.setName("Time");
        DIMENSION_TIME.setId("_dimension_time");
        DIMENSION_TIME.getHierarchies().add(HIERARCHY_TIME);

        DIMENSION_ORDERSTATUS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_ORDERSTATUS.setName("Order Status");
        DIMENSION_ORDERSTATUS.setId("_dimension_orderstatus");
        DIMENSION_ORDERSTATUS.getHierarchies().add(HIERARCHY_ORDERSTATUS);

        // Initialize dimension connectors
        CONNECTOR_MARKETS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_MARKETS.setDimension(DIMENSION_MARKETS);
        CONNECTOR_MARKETS.setForeignKey(COLUMN_CUSTOMERNUMBER_ORDERFACT);
        CONNECTOR_MARKETS.setId("_connector_markets");
        CONNECTOR_MARKETS.setOverrideDimensionName("Markets");

        CONNECTOR_CUSTOMERS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMERS.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_CUSTOMERS.setForeignKey(COLUMN_CUSTOMERNUMBER_ORDERFACT);
        CONNECTOR_CUSTOMERS.setId("_connector_customers");
        CONNECTOR_CUSTOMERS.setOverrideDimensionName("Customers");

        CONNECTOR_PRODUCT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_PRODUCT.setForeignKey(COLUMN_PRODUCTCODE_ORDERFACT);
        CONNECTOR_PRODUCT.setId("_connector_product");
        CONNECTOR_PRODUCT.setOverrideDimensionName("Product");

        CONNECTOR_TIME = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_TIME.setForeignKey(COLUMN_TIME_ID_ORDERFACT);
        CONNECTOR_TIME.setId("_connector_time");
        CONNECTOR_TIME.setOverrideDimensionName("Time");

        CONNECTOR_ORDERSTATUS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_ORDERSTATUS.setDimension(DIMENSION_ORDERSTATUS);
        CONNECTOR_ORDERSTATUS.setForeignKey(COLUMN_STATUS_ORDERFACT);
        CONNECTOR_ORDERSTATUS.setId("_connector_orderstatus");
        CONNECTOR_ORDERSTATUS.setOverrideDimensionName("Order Status");

        // Initialize measures
        MEASURE_QUANTITY = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_QUANTITY.setName("Quantity");
        MEASURE_QUANTITY.setId("_measure_quantity");
        MEASURE_QUANTITY.setColumn(COLUMN_QUANTITYORDERED_ORDERFACT);
        MEASURE_QUANTITY.setFormatString("#,###");

        MEASURE_SALES = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_SALES.setName("Sales");
        MEASURE_SALES.setId("_measure_sales");
        MEASURE_SALES.setColumn(COLUMN_TOTALPRICE_ORDERFACT);
        MEASURE_SALES.setFormatString("#,###");

        MEASUREGROUP_STEELWHEELSSALES = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_STEELWHEELSSALES.getMeasures().addAll(List.of(MEASURE_QUANTITY, MEASURE_SALES));

        // Initialize cube
        CUBE_STEELWHEELSSALES = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_STEELWHEELSSALES.setName("SteelWheelsSales");
        CUBE_STEELWHEELSSALES.setId("_cube_steelwheelssales");
        CUBE_STEELWHEELSSALES.setQuery(TABLEQUERY_FACT);
        CUBE_STEELWHEELSSALES.getDimensionConnectors().addAll(List.of(CONNECTOR_MARKETS, CONNECTOR_CUSTOMERS,
                CONNECTOR_PRODUCT, CONNECTOR_TIME, CONNECTOR_ORDERSTATUS));
        CUBE_STEELWHEELSSALES.getMeasureGroups().add(MEASUREGROUP_STEELWHEELSSALES);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_STEELWHEELS = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA_STEELWHEELS.setId("_databaseSchema_steelwheels");
        //DATABASE_SCHEMA_STEELWHEELS.setName("steelwheels");
        DATABASE_SCHEMA_STEELWHEELS.getTables()
                .addAll(List.of(TABLE_ORDERFACT, TABLE_CUSTOMER, TABLE_PRODUCTS, TABLE_TIME));

        CATALOG_STEELWHEELS = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_STEELWHEELS.setName("SteelWheels");
        CATALOG_STEELWHEELS.setDescription("SteelWheels Sample Database - EMF Version");
        CATALOG_STEELWHEELS.setId("_catalog_steelwheels");
        CATALOG_STEELWHEELS.getDbschemas().add(DATABASE_SCHEMA_STEELWHEELS);
        CATALOG_STEELWHEELS.getCubes().add(CUBE_STEELWHEELSSALES);

        // Add documentation
        document(CATALOG_STEELWHEELS, "SteelWheels Database", steelWheelsBody, 1, 0, 1, false, 0);

        document(TABLEQUERY_CUSTOMER, "Customer Query", queryCustomerBody, 1, 1, 1, true, 0);
        document(TABLEQUERY_PRODUCTS, "Product Query", queryProductBody, 1, 2, 1, true, 0);
        document(TABLEQUERY_TIME, "Time Query", queryTimeBody, 1, 3, 1, true, 0);
        document(TABLEQUERY_ORDERSTATUS, "Status Query", queryStatusBody, 1, 4, 1, true, 0);
        document(TABLEQUERY_FACT, "Fact Query", queryBody, 1, 5, 1, true, 0);

        document(DIMENSION_MARKETS, "Markets Dimension", marketsBody, 1, 6, 1, true, 0);
        document(DIMENSION_CUSTOMERS, "Customers Dimension", customersBody, 1, 7, 1, true, 0);
        document(DIMENSION_PRODUCT, "Product Dimension", productBody, 1, 8, 1, true, 0);
        document(DIMENSION_TIME, "Time Dimension", timeBody, 1, 9, 1, true, 0);
        document(DIMENSION_ORDERSTATUS, "Order Status Dimension",
                "The Order Status dimension represents the current state of orders for tracking fulfillment progress.",
                1, 10, 0, true, 0);

        document(HIERARCHY_MARKETS, "Markets Hierarchy",
                "The Markets hierarchy organizes geographic territories with hasAll enabled and primary key on customer number.", 1, 11, 1, true, 0);
        document(HIERARCHY_CUSTOMERS, "Customers Hierarchy",
                "The Customers hierarchy provides customer-level analysis with hasAll enabled.", 1, 12, 1, true, 0);
        document(HIERARCHY_PRODUCT, "Product Hierarchy",
                "The Product hierarchy organizes products by line, vendor, and individual product with hasAll enabled.", 1, 13, 1, true, 0);
        document(HIERARCHY_TIME, "Time Hierarchy",
                "The Time hierarchy provides temporal analysis with years, quarters, and months with hasAll enabled.", 1, 14, 1, true, 0);
        document(HIERARCHY_ORDERSTATUS, "Order Status Hierarchy",
                "The Time hierarchy provides temporal analysis with product status with hasAll enabled.", 1, 15, 1, true, 0);

        document(LEVEL_MARKETS_TERRITORY, "Territory Level",
                "Territory level represents geographic territories for market analysis.", 1, 16, 1, true, 0);
        document(LEVEL_MARKETS_COUNTRY, "Country Level",
                "Country level represents country for market analysis.", 1, 17, 1, true, 0);
        document(LEVEL_MARKETS_STATE, "State Level",
                "State level represents state for market analysis.", 1, 18, 1, true, 0);
        document(LEVEL_MARKETS_CITY, "State City",
                "City level represents city for market analysis.", 1, 19, 1, true, 0);
        document(LEVEL_CUSTOMERS_CUSTOMER, "Customer Level",
                "Customer level provides individual customer details for analysis.", 1, 20, 1, true, 0);
        document(LEVEL_PRODUCT_LINE, "Product Line Level",
                "Product line level categorizes products into major product categories.", 1, 21, 1, true, 0);
        document(LEVEL_PRODUCT_VENDOR, "Vendor Level",
                "Vendor level into major product categories.", 1, 22, 1, true, 0);
        document(LEVEL_PRODUCT_PRODUCT, "Product Level",
                "Product name into major product categories.", 1, 23, 1, true, 0);
        document(LEVEL_TIME_YEARS, "Years Level",
                "Years level provides temporal analysis by year periods.", 1, 24, 1, true, 0);
        document(LEVEL_TIME_QUARTERS, "Quarters Level",
                "Quarters level provides temporal analysis by quarter periods.", 1, 25, 1, true, 0);
        document(LEVEL_TIME_MONTHS, "Months Level",
                "Months level provides temporal analysis by month periods.", 1, 26, 1, true, 0);
        document(LEVEL_ORDERSTATUS_TYPE, "Status Level",
                "Products status products into major product categories.", 1, 27, 1, true, 0);

        document(MEASURE_QUANTITY, "Measure Quantity", "Measure Quantity use orderfact table QUANTITYORDERED column with sum aggregation in Cube.", 1, 28, 1, true, 0);

        document(MEASURE_SALES, "Measure Sales", "Measure Sales is sum of product sales.", 1, 29, 1, true, 0);

        document(CUBE_STEELWHEELSSALES, "Sales Cube", salesCubeBody, 1, 30, 1, true, 0);
    }

    @Override
    public Catalog get() {
        return CATALOG_STEELWHEELS;
    }

}
