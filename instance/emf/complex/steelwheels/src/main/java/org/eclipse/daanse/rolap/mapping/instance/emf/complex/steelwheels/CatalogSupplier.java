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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ColumnInternalDataType;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.5", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    public static final TableSource TABLEQUERY_ORDERSTATUS;
    public static final Level LEVEL_PRODUCT_LINE;
    public static final SumMeasure MEASURE_QUANTITY;
    public static final TableSource TABLEQUERY_PRODUCTS;
    public static final ExplicitHierarchy HIERARCHY_MARKETS;
    public static final Catalog CATALOG_STEELWHEELS;
    public static final SumMeasure MEASURE_SALES;
    public static final TableSource TABLEQUERY_TIME;
    public static final Level LEVEL_MARKETS_STATE;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMERS;
    public static final Level LEVEL_MARKETS_COUNTRY;
    public static final StandardDimension DIMENSION_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_ORDERSTATUS;
    public static final TableSource TABLEQUERY_FACT;
    public static final StandardDimension DIMENSION_CUSTOMERS;
    public static final PhysicalCube CUBE_STEELWHEELSSALES;
    public static final Level LEVEL_TIME_MONTHS;
    public static final Level LEVEL_TIME_QUARTERS;
    public static final Level LEVEL_PRODUCT_VENDOR;
    public static final StandardDimension DIMENSION_MARKETS;
    public static final Level LEVEL_PRODUCT_PRODUCT;
    public static final StandardDimension DIMENSION_ORDERSTATUS;
    public static final TableSource TABLEQUERY_CUSTOMER;
    public static final Level LEVEL_MARKETS_CITY;
    public static final Level LEVEL_MARKETS_TERRITORY;
    public static final Level LEVEL_CUSTOMERS_CUSTOMER;
    public static final TimeDimension DIMENSION_TIME;
    public static final Level LEVEL_ORDERSTATUS_TYPE;
    public static final Level LEVEL_TIME_YEARS;


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
    public static final Table TABLE_ORDERFACT;
    public static final Table TABLE_CUSTOMER;
    public static final Table TABLE_PRODUCTS;
    public static final Table TABLE_TIME;

    // Static levels
    // field assignment only: LEVEL_MARKETS_TERRITORY
    // field assignment only: LEVEL_MARKETS_COUNTRY
    // field assignment only: LEVEL_MARKETS_STATE
    // field assignment only: LEVEL_MARKETS_CITY
    // field assignment only: LEVEL_CUSTOMERS_CUSTOMER
    // field assignment only: LEVEL_PRODUCT_LINE
    // field assignment only: LEVEL_PRODUCT_VENDOR
    // field assignment only: LEVEL_PRODUCT_PRODUCT
    // field assignment only: LEVEL_TIME_YEARS
    // field assignment only: LEVEL_TIME_QUARTERS
    // field assignment only: LEVEL_TIME_MONTHS
    // field assignment only: LEVEL_ORDERSTATUS_TYPE

    // Static hierarchies
    // field assignment only: HIERARCHY_MARKETS
    // field assignment only: HIERARCHY_CUSTOMERS
    public static final ExplicitHierarchy HIERARCHY_PRODUCT;
    public static final ExplicitHierarchy HIERARCHY_TIME;
    // field assignment only: HIERARCHY_ORDERSTATUS

    // Static dimensions
    // field assignment only: DIMENSION_MARKETS
    // field assignment only: DIMENSION_CUSTOMERS
    // field assignment only: DIMENSION_PRODUCT
    // field assignment only: DIMENSION_TIME
    // field assignment only: DIMENSION_ORDERSTATUS

    // Static cube
    // field assignment only: CUBE_STEELWHEELSSALES

    // Static table queries
    // field assignment only: TABLEQUERY_CUSTOMER
    // field assignment only: TABLEQUERY_PRODUCTS
    // field assignment only: TABLEQUERY_TIME
    // field assignment only: TABLEQUERY_ORDERSTATUS
    // field assignment only: TABLEQUERY_FACT

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_MARKETS;
    public static final DimensionConnector CONNECTOR_CUSTOMERS;
    public static final DimensionConnector CONNECTOR_PRODUCT;
    public static final DimensionConnector CONNECTOR_TIME;
    public static final DimensionConnector CONNECTOR_ORDERSTATUS;

    // Static measures and measure group
    // field assignment only: MEASURE_QUANTITY
    // field assignment only: MEASURE_SALES
    public static final MeasureGroup MEASUREGROUP_STEELWHEELSSALES;

    // Static database schema and catalog
    public static final Schema DATABASE_SCHEMA_STEELWHEELS;
    // field assignment only: CATALOG_STEELWHEELS

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
        COLUMN_CUSTOMERNUMBER_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMERNUMBER_ORDERFACT.setName("CUSTOMERNUMBER");
        COLUMN_CUSTOMERNUMBER_ORDERFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCTCODE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTCODE_ORDERFACT.setName("PRODUCTCODE");
        COLUMN_PRODUCTCODE_ORDERFACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_TIME_ID_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_ORDERFACT.setName("TIME_ID");
        COLUMN_TIME_ID_ORDERFACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_QUANTITYORDERED_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QUANTITYORDERED_ORDERFACT.setName("QUANTITYORDERED");
        COLUMN_QUANTITYORDERED_ORDERFACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TOTALPRICE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TOTALPRICE_ORDERFACT.setName("TOTALPRICE");
        COLUMN_TOTALPRICE_ORDERFACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_STATUS_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STATUS_ORDERFACT.setName("STATUS");
        COLUMN_STATUS_ORDERFACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ORDERDATE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ORDERDATE_ORDERFACT.setName("ORDERDATE");
        COLUMN_ORDERDATE_ORDERFACT.setType(SqlSimpleTypes.Sql99.timestampType());

        COLUMN_PRICEEACH_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRICEEACH_ORDERFACT.setName("PRICEEACH");
        COLUMN_PRICEEACH_ORDERFACT.setType(SqlSimpleTypes.numericType(18, 4));

        COLUMN_REQUIREDDATE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_REQUIREDDATE_ORDERFACT.setName("REQUIREDDATE");
        COLUMN_REQUIREDDATE_ORDERFACT.setType(SqlSimpleTypes.Sql99.timestampType());

        COLUMN_SHIPPEDDATE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SHIPPEDDATE_ORDERFACT.setName("SHIPPEDDATE");
        COLUMN_SHIPPEDDATE_ORDERFACT.setType(SqlSimpleTypes.Sql99.timestampType());

        // Initialize Customer Table columns
        COLUMN_CUSTOMERNUMBER_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMERNUMBER_CUSTOMER.setName("CUSTOMERNUMBER");
        COLUMN_CUSTOMERNUMBER_CUSTOMER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CUSTOMERNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMERNAME_CUSTOMER.setName("CUSTOMERNAME");
        COLUMN_CUSTOMERNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_TERRITORY_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TERRITORY_CUSTOMER.setName("TERRITORY");
        COLUMN_TERRITORY_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_COUNTRY_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_CUSTOMER.setName("COUNTRY");
        COLUMN_COUNTRY_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STATE_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STATE_CUSTOMER.setName("STATE");
        COLUMN_STATE_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CITY_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CITY_CUSTOMER.setName("CITY");
        COLUMN_CITY_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CONTACTFIRSTNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CONTACTFIRSTNAME_CUSTOMER.setName("CONTACTFIRSTNAME");
        COLUMN_CONTACTFIRSTNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CONTACTLASTNAME_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CONTACTLASTNAME_CUSTOMER.setName("CONTACTLASTNAME");
        COLUMN_CONTACTLASTNAME_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PHONE_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PHONE_CUSTOMER.setName("PHONE");
        COLUMN_PHONE_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ADDRESSLINE1_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ADDRESSLINE1_CUSTOMER.setName("ADDRESSLINE1");
        COLUMN_ADDRESSLINE1_CUSTOMER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CREDITLIMIT_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CREDITLIMIT_CUSTOMER.setName("CREDITLIMIT");
        COLUMN_CREDITLIMIT_CUSTOMER.setType(SqlSimpleTypes.numericType(18, 4));

        // Initialize Products Table columns
        COLUMN_PRODUCTCODE_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTCODE_PRODUCTS.setName("PRODUCTCODE");
        COLUMN_PRODUCTCODE_PRODUCTS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCTNAME_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTNAME_PRODUCTS.setName("PRODUCTNAME");
        COLUMN_PRODUCTNAME_PRODUCTS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCTLINE_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTLINE_PRODUCTS.setName("PRODUCTLINE");
        COLUMN_PRODUCTLINE_PRODUCTS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCTVENDOR_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTVENDOR_PRODUCTS.setName("PRODUCTVENDOR");
        COLUMN_PRODUCTVENDOR_PRODUCTS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCTDESCRIPTION_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCTDESCRIPTION_PRODUCTS.setName("PRODUCTDESCRIPTION");
        COLUMN_PRODUCTDESCRIPTION_PRODUCTS.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Time Table columns
        COLUMN_TIME_ID_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TIME_ID_TIME.setName("TIME_ID");
        COLUMN_TIME_ID_TIME.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_YEAR_ID_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_YEAR_ID_TIME.setName("YEAR_ID");
        COLUMN_YEAR_ID_TIME.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_QTR_NAME_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QTR_NAME_TIME.setName("QTR_NAME");
        COLUMN_QTR_NAME_TIME.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_QTR_ID_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_QTR_ID_TIME.setName("QTR_ID");
        COLUMN_QTR_ID_TIME.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_MONTH_NAME_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_NAME_TIME.setName("MONTH_NAME");
        COLUMN_MONTH_NAME_TIME.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MONTH_ID_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MONTH_ID_TIME.setName("MONTH_ID");
        COLUMN_MONTH_ID_TIME.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize tables
        TABLE_ORDERFACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_ORDERFACT.setName("orderfact");
        TABLE_ORDERFACT.getFeature()
                .addAll(List.of(COLUMN_CUSTOMERNUMBER_ORDERFACT, COLUMN_PRODUCTCODE_ORDERFACT, COLUMN_TIME_ID_ORDERFACT,
                        COLUMN_QUANTITYORDERED_ORDERFACT, COLUMN_TOTALPRICE_ORDERFACT, COLUMN_STATUS_ORDERFACT,
                        COLUMN_ORDERDATE_ORDERFACT, COLUMN_PRICEEACH_ORDERFACT, COLUMN_REQUIREDDATE_ORDERFACT,
                        COLUMN_SHIPPEDDATE_ORDERFACT));

        TABLE_CUSTOMER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_CUSTOMER.setName("customer_w_ter");
        TABLE_CUSTOMER.getFeature()
                .addAll(List.of(COLUMN_CUSTOMERNUMBER_CUSTOMER, COLUMN_CUSTOMERNAME_CUSTOMER, COLUMN_TERRITORY_CUSTOMER,
                        COLUMN_COUNTRY_CUSTOMER, COLUMN_STATE_CUSTOMER, COLUMN_CITY_CUSTOMER,
                        COLUMN_CONTACTFIRSTNAME_CUSTOMER, COLUMN_CONTACTLASTNAME_CUSTOMER, COLUMN_PHONE_CUSTOMER,
                        COLUMN_ADDRESSLINE1_CUSTOMER, COLUMN_CREDITLIMIT_CUSTOMER));

        TABLE_PRODUCTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PRODUCTS.setName("products");
        TABLE_PRODUCTS.getFeature().addAll(List.of(COLUMN_PRODUCTCODE_PRODUCTS, COLUMN_PRODUCTNAME_PRODUCTS,
                COLUMN_PRODUCTLINE_PRODUCTS, COLUMN_PRODUCTVENDOR_PRODUCTS, COLUMN_PRODUCTDESCRIPTION_PRODUCTS));

        TABLE_TIME = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_TIME.setName("time");
        TABLE_TIME.getFeature().addAll(List.of(COLUMN_TIME_ID_TIME, COLUMN_YEAR_ID_TIME, COLUMN_QTR_NAME_TIME,
                COLUMN_QTR_ID_TIME, COLUMN_MONTH_NAME_TIME, COLUMN_MONTH_ID_TIME));

        // Initialize levels
        LEVEL_MARKETS_TERRITORY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_TERRITORY.setName("Territory");
        LEVEL_MARKETS_TERRITORY.setColumn(COLUMN_TERRITORY_CUSTOMER);
        LEVEL_MARKETS_TERRITORY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_TERRITORY.setUniqueMembers(true);
        LEVEL_MARKETS_TERRITORY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_TERRITORY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_COUNTRY.setName("Country");
        LEVEL_MARKETS_COUNTRY.setColumn(COLUMN_COUNTRY_CUSTOMER);
        LEVEL_MARKETS_COUNTRY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_COUNTRY.setUniqueMembers(false);
        LEVEL_MARKETS_COUNTRY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_COUNTRY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_STATE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_STATE.setName("State Province");
        LEVEL_MARKETS_STATE.setColumn(COLUMN_STATE_CUSTOMER);
        LEVEL_MARKETS_STATE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_STATE.setUniqueMembers(true);
        LEVEL_MARKETS_STATE.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_STATE.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_MARKETS_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_MARKETS_CITY.setName("City");
        LEVEL_MARKETS_CITY.setColumn(COLUMN_CITY_CUSTOMER);
        LEVEL_MARKETS_CITY.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_MARKETS_CITY.setUniqueMembers(true);
        LEVEL_MARKETS_CITY.setType(LevelDefinition.REGULAR);
        LEVEL_MARKETS_CITY.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_CUSTOMERS_CUSTOMER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMERS_CUSTOMER.setName("Customer");
        LEVEL_CUSTOMERS_CUSTOMER.setColumn(COLUMN_CUSTOMERNAME_CUSTOMER);
        LEVEL_CUSTOMERS_CUSTOMER.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_CUSTOMERS_CUSTOMER.setUniqueMembers(true);
        LEVEL_CUSTOMERS_CUSTOMER.setType(LevelDefinition.REGULAR);
        LEVEL_CUSTOMERS_CUSTOMER.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_LINE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_LINE.setName("Line");
        LEVEL_PRODUCT_LINE.setColumn(COLUMN_PRODUCTLINE_PRODUCTS);
        LEVEL_PRODUCT_LINE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_LINE.setUniqueMembers(true);
        LEVEL_PRODUCT_LINE.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_LINE.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_VENDOR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_VENDOR.setName("Vendor");
        LEVEL_PRODUCT_VENDOR.setColumn(COLUMN_PRODUCTVENDOR_PRODUCTS);
        LEVEL_PRODUCT_VENDOR.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_VENDOR.setUniqueMembers(true);
        LEVEL_PRODUCT_VENDOR.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_VENDOR.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_PRODUCT_PRODUCT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PRODUCT_PRODUCT.setName("Product");
        LEVEL_PRODUCT_PRODUCT.setColumn(COLUMN_PRODUCTNAME_PRODUCTS);
        LEVEL_PRODUCT_PRODUCT.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_PRODUCT_PRODUCT.setUniqueMembers(true);
        LEVEL_PRODUCT_PRODUCT.setType(LevelDefinition.REGULAR);
        LEVEL_PRODUCT_PRODUCT.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_YEARS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_TIME_YEARS.setName("Years");
        LEVEL_TIME_YEARS.setColumn(COLUMN_YEAR_ID_TIME);
        LEVEL_TIME_YEARS.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_TIME_YEARS.setUniqueMembers(true);
        LEVEL_TIME_YEARS.setType(LevelDefinition.TIME_YEARS);
        LEVEL_TIME_YEARS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_QUARTERS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_TIME_QUARTERS.setName("Quarters");
        LEVEL_TIME_QUARTERS.setColumn(COLUMN_QTR_NAME_TIME);
        LEVEL_TIME_QUARTERS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_TIME_QUARTERS.setUniqueMembers(false);
        LEVEL_TIME_QUARTERS.setType(LevelDefinition.TIME_QUARTERS);
        LEVEL_TIME_QUARTERS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_TIME_MONTHS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_TIME_MONTHS.setName("Months");
        LEVEL_TIME_MONTHS.setColumn(COLUMN_MONTH_NAME_TIME);
        LEVEL_TIME_MONTHS.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_TIME_MONTHS.setUniqueMembers(false);
        LEVEL_TIME_MONTHS.setType(LevelDefinition.TIME_MONTHS);
        LEVEL_TIME_MONTHS.setHideMemberIf(HideMemberIf.NEVER);

        LEVEL_ORDERSTATUS_TYPE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ORDERSTATUS_TYPE.setName("Type");
        LEVEL_ORDERSTATUS_TYPE.setColumn(COLUMN_STATUS_ORDERFACT);
        LEVEL_ORDERSTATUS_TYPE.setColumnType(ColumnInternalDataType.STRING);
        LEVEL_ORDERSTATUS_TYPE.setUniqueMembers(true);
        LEVEL_ORDERSTATUS_TYPE.setType(LevelDefinition.REGULAR);
        LEVEL_ORDERSTATUS_TYPE.setHideMemberIf(HideMemberIf.NEVER);

        // Initialize table queries
        TABLEQUERY_CUSTOMER = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_CUSTOMER.setTable(TABLE_CUSTOMER);

        TABLEQUERY_PRODUCTS = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_PRODUCTS.setTable(TABLE_PRODUCTS);

        TABLEQUERY_TIME = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_TIME.setTable(TABLE_TIME);

        TABLEQUERY_ORDERSTATUS = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_ORDERSTATUS.setTable(TABLE_ORDERFACT);

        TABLEQUERY_FACT = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_FACT.setTable(TABLE_ORDERFACT);

        // Initialize hierarchies
        HIERARCHY_MARKETS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_MARKETS.setName("Markets Hierarchy");
        HIERARCHY_MARKETS.setHasAll(true);
        HIERARCHY_MARKETS.setPrimaryKey(COLUMN_CUSTOMERNUMBER_CUSTOMER);
        HIERARCHY_MARKETS.setAllMemberName("All Markets");
        HIERARCHY_MARKETS.setQuery(TABLEQUERY_CUSTOMER);
        HIERARCHY_MARKETS.getLevels().addAll(
                List.of(LEVEL_MARKETS_TERRITORY, LEVEL_MARKETS_COUNTRY, LEVEL_MARKETS_STATE, LEVEL_MARKETS_CITY));

        HIERARCHY_CUSTOMERS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_CUSTOMERS.setName("Customers Hierarchy");
        HIERARCHY_CUSTOMERS.setHasAll(true);
        HIERARCHY_CUSTOMERS.setAllMemberName("All Customers");
        HIERARCHY_CUSTOMERS.setPrimaryKey(COLUMN_CUSTOMERNUMBER_CUSTOMER);
        HIERARCHY_CUSTOMERS.setQuery(TABLEQUERY_CUSTOMER);
        HIERARCHY_CUSTOMERS.getLevels().add(LEVEL_CUSTOMERS_CUSTOMER);

        HIERARCHY_PRODUCT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_PRODUCT.setName("Product Hierarchy");
        HIERARCHY_PRODUCT.setHasAll(true);
        HIERARCHY_PRODUCT.setAllMemberName("All Products");
        HIERARCHY_PRODUCT.setPrimaryKey(COLUMN_PRODUCTCODE_PRODUCTS);
        HIERARCHY_PRODUCT.setQuery(TABLEQUERY_PRODUCTS);
        HIERARCHY_PRODUCT.getLevels().addAll(List.of(LEVEL_PRODUCT_LINE, LEVEL_PRODUCT_VENDOR, LEVEL_PRODUCT_PRODUCT));

        HIERARCHY_TIME = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_TIME.setName("Time Hierarchy");
        HIERARCHY_TIME.setHasAll(true);
        HIERARCHY_TIME.setAllMemberName("All Years");
        HIERARCHY_TIME.setPrimaryKey(COLUMN_TIME_ID_TIME);
        HIERARCHY_TIME.setQuery(TABLEQUERY_TIME);
        HIERARCHY_TIME.getLevels().addAll(List.of(LEVEL_TIME_YEARS, LEVEL_TIME_QUARTERS, LEVEL_TIME_MONTHS));

        HIERARCHY_ORDERSTATUS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        //HIERARCHY_ORDERSTATUS.setName("Order Status Hierarchy");
        HIERARCHY_ORDERSTATUS.setHasAll(true);
        HIERARCHY_ORDERSTATUS.setAllMemberName("All Status Types");
        HIERARCHY_ORDERSTATUS.setPrimaryKey(COLUMN_STATUS_ORDERFACT);
        //HIERARCHY_ORDERSTATUS.setQuery(TABLEQUERY_ORDERSTATUS);
        HIERARCHY_ORDERSTATUS.getLevels().add(LEVEL_ORDERSTATUS_TYPE);

        // Initialize dimensions
        DIMENSION_MARKETS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_MARKETS.setName("Markets");
        DIMENSION_MARKETS.getHierarchies().add(HIERARCHY_MARKETS);

        DIMENSION_CUSTOMERS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMERS.setName("Customers");
        DIMENSION_CUSTOMERS.getHierarchies().add(HIERARCHY_CUSTOMERS);

        DIMENSION_PRODUCT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PRODUCT.setName("Product");
        DIMENSION_PRODUCT.getHierarchies().add(HIERARCHY_PRODUCT);

        DIMENSION_TIME = DimensionFactory.eINSTANCE.createTimeDimension();
        DIMENSION_TIME.setName("Time");
        DIMENSION_TIME.getHierarchies().add(HIERARCHY_TIME);

        DIMENSION_ORDERSTATUS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_ORDERSTATUS.setName("Order Status");
        DIMENSION_ORDERSTATUS.getHierarchies().add(HIERARCHY_ORDERSTATUS);

        // Initialize dimension connectors
        CONNECTOR_MARKETS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_MARKETS.setDimension(DIMENSION_MARKETS);
        CONNECTOR_MARKETS.setForeignKey(COLUMN_CUSTOMERNUMBER_ORDERFACT);
        CONNECTOR_MARKETS.setOverrideDimensionName("Markets");

        CONNECTOR_CUSTOMERS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMERS.setDimension(DIMENSION_CUSTOMERS);
        CONNECTOR_CUSTOMERS.setForeignKey(COLUMN_CUSTOMERNUMBER_ORDERFACT);
        CONNECTOR_CUSTOMERS.setOverrideDimensionName("Customers");

        CONNECTOR_PRODUCT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PRODUCT.setDimension(DIMENSION_PRODUCT);
        CONNECTOR_PRODUCT.setForeignKey(COLUMN_PRODUCTCODE_ORDERFACT);
        CONNECTOR_PRODUCT.setOverrideDimensionName("Product");

        CONNECTOR_TIME = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_TIME.setDimension(DIMENSION_TIME);
        CONNECTOR_TIME.setForeignKey(COLUMN_TIME_ID_ORDERFACT);
        CONNECTOR_TIME.setOverrideDimensionName("Time");

        CONNECTOR_ORDERSTATUS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_ORDERSTATUS.setDimension(DIMENSION_ORDERSTATUS);
        CONNECTOR_ORDERSTATUS.setForeignKey(COLUMN_STATUS_ORDERFACT);
        CONNECTOR_ORDERSTATUS.setOverrideDimensionName("Order Status");

        // Initialize measures
        MEASURE_QUANTITY = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_QUANTITY.setName("Quantity");
        MEASURE_QUANTITY.setColumn(COLUMN_QUANTITYORDERED_ORDERFACT);
        MEASURE_QUANTITY.setFormatString("#,###");

        MEASURE_SALES = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_SALES.setName("Sales");
        MEASURE_SALES.setColumn(COLUMN_TOTALPRICE_ORDERFACT);
        MEASURE_SALES.setFormatString("#,###");

        MEASUREGROUP_STEELWHEELSSALES = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_STEELWHEELSSALES.getMeasures().addAll(List.of(MEASURE_QUANTITY, MEASURE_SALES));

        // Initialize cube
        CUBE_STEELWHEELSSALES = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_STEELWHEELSSALES.setName("SteelWheelsSales");
        CUBE_STEELWHEELSSALES.setQuery(TABLEQUERY_FACT);
        CUBE_STEELWHEELSSALES.getDimensionConnectors().addAll(List.of(CONNECTOR_MARKETS, CONNECTOR_CUSTOMERS,
                CONNECTOR_PRODUCT, CONNECTOR_TIME, CONNECTOR_ORDERSTATUS));
        CUBE_STEELWHEELSSALES.getMeasureGroups().add(MEASUREGROUP_STEELWHEELSSALES);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_STEELWHEELS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        //DATABASE_SCHEMA_STEELWHEELS.setName("steelwheels");
        DATABASE_SCHEMA_STEELWHEELS.getOwnedElement()
                .addAll(List.of(TABLE_ORDERFACT, TABLE_CUSTOMER, TABLE_PRODUCTS, TABLE_TIME));

        CATALOG_STEELWHEELS = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG_STEELWHEELS.setName("SteelWheels");
        CATALOG_STEELWHEELS.getDbschemas().add(DATABASE_SCHEMA_STEELWHEELS);
        CATALOG_STEELWHEELS.getCubes().add(CUBE_STEELWHEELSSALES);

        // Add documentation





    }


    @Override
    public Catalog get() {
        return CATALOG_STEELWHEELS;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("SteelWheels Database", steelWheelsBody, 1, 0, 1, null, 0),
                        new DocSection("Customer Query", queryCustomerBody, 1, 1, 1, TABLEQUERY_CUSTOMER, 0),
                        new DocSection("Product Query", queryProductBody, 1, 2, 1, TABLEQUERY_PRODUCTS, 0),
                        new DocSection("Time Query", queryTimeBody, 1, 3, 1, TABLEQUERY_TIME, 0),
                        new DocSection("Status Query", queryStatusBody, 1, 4, 1, TABLEQUERY_ORDERSTATUS, 0),
                        new DocSection("Fact Query", queryBody, 1, 5, 1, TABLEQUERY_FACT, 0),
                        new DocSection("Markets Dimension", marketsBody, 1, 6, 1, DIMENSION_MARKETS, 0),
                        new DocSection("Customers Dimension", customersBody, 1, 7, 1, DIMENSION_CUSTOMERS, 0),
                        new DocSection("Product Dimension", productBody, 1, 8, 1, DIMENSION_PRODUCT, 0),
                        new DocSection("Time Dimension", timeBody, 1, 9, 1, DIMENSION_TIME, 0),
                        new DocSection("Order Status Dimension", "The Order Status dimension represents the current state of orders for tracking fulfillment progress.", 1, 10, 0, DIMENSION_ORDERSTATUS, 0),
                        new DocSection("Markets Hierarchy", "The Markets hierarchy organizes geographic territories with hasAll enabled and primary key on customer number.", 1, 11, 1, HIERARCHY_MARKETS, 0),
                        new DocSection("Customers Hierarchy", "The Customers hierarchy provides customer-level analysis with hasAll enabled.", 1, 12, 1, HIERARCHY_CUSTOMERS, 0),
                        new DocSection("Order Status Hierarchy", "The Time hierarchy provides temporal analysis with product status with hasAll enabled.", 1, 15, 1, HIERARCHY_ORDERSTATUS, 0),
                        new DocSection("Territory Level", "Territory level represents geographic territories for market analysis.", 1, 16, 1, LEVEL_MARKETS_TERRITORY, 0),
                        new DocSection("Country Level", "Country level represents country for market analysis.", 1, 17, 1, LEVEL_MARKETS_COUNTRY, 0),
                        new DocSection("State Level", "State level represents state for market analysis.", 1, 18, 1, LEVEL_MARKETS_STATE, 0),
                        new DocSection("State City", "City level represents city for market analysis.", 1, 19, 1, LEVEL_MARKETS_CITY, 0),
                        new DocSection("Customer Level", "Customer level provides individual customer details for analysis.", 1, 20, 1, LEVEL_CUSTOMERS_CUSTOMER, 0),
                        new DocSection("Product Line Level", "Product line level categorizes products into major product categories.", 1, 21, 1, LEVEL_PRODUCT_LINE, 0),
                        new DocSection("Vendor Level", "Vendor level into major product categories.", 1, 22, 1, LEVEL_PRODUCT_VENDOR, 0),
                        new DocSection("Product Level", "Product name into major product categories.", 1, 23, 1, LEVEL_PRODUCT_PRODUCT, 0),
                        new DocSection("Years Level", "Years level provides temporal analysis by year periods.", 1, 24, 1, LEVEL_TIME_YEARS, 0),
                        new DocSection("Quarters Level", "Quarters level provides temporal analysis by quarter periods.", 1, 25, 1, LEVEL_TIME_QUARTERS, 0),
                        new DocSection("Months Level", "Months level provides temporal analysis by month periods.", 1, 26, 1, LEVEL_TIME_MONTHS, 0),
                        new DocSection("Status Level", "Products status products into major product categories.", 1, 27, 1, LEVEL_ORDERSTATUS_TYPE, 0),
                        new DocSection("Measure Quantity", "Measure Quantity use orderfact table QUANTITYORDERED column with sum aggregation in Cube.", 1, 28, 1, MEASURE_QUANTITY, 0),
                        new DocSection("Measure Sales", "Measure Sales is sum of product sales.", 1, 29, 1, MEASURE_SALES, 0),
                        new DocSection("Sales Cube", salesCubeBody, 1, 30, 1, CUBE_STEELWHEELSSALES, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
