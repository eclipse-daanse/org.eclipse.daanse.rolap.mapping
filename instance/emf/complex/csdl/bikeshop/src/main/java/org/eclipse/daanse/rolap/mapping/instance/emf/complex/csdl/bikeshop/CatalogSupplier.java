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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.csdl.bikeshop;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Kpi;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.7", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    public static final String CATALOG_NAME = "CSDLBI 1.1";
    public static final String CATALOG_DESCRIPTION = "SalesChannel";
    public static final String CUBE_NAME = "SalesChannel";
    public static final String MEASURE_NAME1 = "Sum of TotalProductCost";
    public static final String MEASURE_NAME2 = "Sum of SalesAmount";

    public static final DatabaseSchema DATABASE_SCHEMA;

    public static final Column COLUMN_ROW_NUMBER_BIKE_SALES;
    public static final Column COLUMN_SALES_ORDER_NUMBER_BIKE_SALES;
    public static final Column COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES;
    public static final Column COLUMN_REVISION_NUMBER_BIKE_SALES;
    public static final Column COLUMN_PRODUCT_KEY_BIKE_SALES;
    public static final Column COLUMN_COUNTRY_CODE_BIKE_SALES;
    public static final Column COLUMN_CURENCY_KEY_BIKE_SALES;
    public static final Column COLUMN_CALENDAR_QUARTER_BIKE_SALES;
    public static final Column COLUMN_SALES_CHANNEL_CODE_BIKE_SALES;
    public static final Column COLUMN_ORDER_QUANTITY_BIKE_SALES;
    public static final Column COLUMN_UNIT_PRICE_BIKE_SALES;
    public static final Column COLUMN_EXTENDED_AMOUNT_BIKE_SALES;
    public static final Column COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES;
    public static final Column COLUMN_DISCOUNT_AMOUNT_BIKE_SALES;
    public static final Column COLUMN_PRODUCT_STANDART_COST_BIKE_SALES;
    public static final Column COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES;
    public static final Column COLUMN_SALES_AMOUNT_BIKE_SALES;
    public static final Column COLUMN_TAX_AMT_BIKE_SALES;
    public static final Column COLUMN_FREIGHT_BIKE_SALES;
    public static final Column COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES;
    public static final Column COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES;
    public static final Column COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES;

    public static final Column COLUMN_ROW_NUMBER_BIKE;
    public static final Column COLUMN_PRODUCT_KEY_BIKE;
    public static final Column COLUMN_PRODUCT_ALTERNATE_KEY_BIKE;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE;
    public static final Column COLUMN_PRODUCT_NAME_BIKE;
    public static final Column COLUMN_STARDART_COST_BIKE;
    public static final Column COLUMN_FINISHED_GOODS_FLAG_BIKE;
    public static final Column COLUMN_COLOR_BIKE;
    public static final Column COLUMN_LIST_PRICE_BIKE;
    public static final Column COLUMN_SIZE_BIKE;
    public static final Column COLUMN_SIZE_RANGE_BIKE;
    public static final Column COLUMN_WIGHT_BIKE;
    public static final Column COLUMN_DEALER_PRICE_BIKE;
    public static final Column COLUMN_CLASS_BIKE;
    public static final Column COLUMN_STYLE_BIKE;
    public static final Column COLUMN_MODEL_NAME_BIKE;
    public static final Column COLUMN_DESCRIPTION_BIKE;
    public static final Column COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE;
    public static final Column COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE;
    public static final Column COLUMN_SAFETY_STOCK_LEVEL_BIKE;
    public static final Column COLUMN_REORDER_POINT_BIKE;
    public static final Column COLUMN_DAYS_TO_MANUFACTURE_BIKE;
    public static final Column COLUMN_PRODUCT_LINE_BIKE;

    public static final Column COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY;
    public static final Column COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY;
    public static final Column COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY;

    public static final Column COLUMN_ROW_NUMBER_CALENDAR_QUARTER;
    public static final Column COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER;

    public static final Column COLUMN_ROW_NUMBER_COUNTRY;
    public static final Column COLUMN_COUNTRY_CODE_COUNTRY;
    public static final Column COLUMN_COUNTRY_NAME_COUNTRY;

    public static final Column COLUMN_ROW_NUMBER_CURRENCY;
    public static final Column COLUMN_CURRENCY_KEY_CURRENCY;
    public static final Column COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY;
    public static final Column COLUMN_CURRENCY_NAME_CURRENCY;

    public static final Column COLUMN_ROW_NUMBER_SALES_CHANNEL;
    public static final Column COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL;
    public static final Column COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL;

    // Static tables
    public static final PhysicalTable TABLE_BIKE;
    public static final PhysicalTable TABLE_BIKE_SALES;
    public static final PhysicalTable TABLE_BIKE_SUBCATEGORY;
    public static final PhysicalTable TABLE_CALENDAR_QUARTER;
    public static final PhysicalTable TABLE_COUNTRY;
    public static final PhysicalTable TABLE_CURRENCY;
    public static final PhysicalTable TABLE_SALES_CHANNEL;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_BIKE;
    public static final DimensionConnector CONNECTOR_BIKE_SUBCATEGORY;
    public static final DimensionConnector CONNECTOR_CALENDAR_QUARTER;
    public static final DimensionConnector CONNECTOR_COUNTRY;
    public static final DimensionConnector CONNECTOR_CURRENCY;
    public static final DimensionConnector CONNECTOR_SALES_CHANNEL;

    public static final ExplicitHierarchy HIERARCHY_BIKE;
    public static final ExplicitHierarchy HIERARCHY_BIKE_SUBCATEGORY;
    public static final ExplicitHierarchy HIERARCHY_CALENDAR_QUARTER;
    public static final ExplicitHierarchy HIERARCHY_COUNTRY;
    public static final ExplicitHierarchy HIERARCHY_CURRENCY;
    public static final ExplicitHierarchy HIERARCHY_SALES_CHANNEL;

    public static final StandardDimension DIMENSION_BIKE;
    public static final StandardDimension DIMENSION_BIKE_SUBCATEGORY;
    public static final StandardDimension DIMENSION_CALENDAR_QUARTER;
    public static final StandardDimension DIMENSION_COUNTRY;
    public static final StandardDimension DIMENSION_CURRENCY;
    public static final StandardDimension DIMENSION_SALES_CHANNEL;

    public static final MeasureGroup MEASURE_GROUP;

    public static final Kpi KPI;

    public static final TableQuery TABLEQUERY_BIKE;
    public static final TableQuery TABLEQUERY_BIKE_SALES;
    public static final TableQuery TABLEQUERY_BIKE_SUBCATEGORY;
    public static final TableQuery TABLEQUERY_CALENDAR_QUARTER;
    public static final TableQuery TABLEQUERY_COUNTRY;
    public static final TableQuery TABLEQUERY_CURRENCY;
    public static final TableQuery TABLEQUERY_SALES_CHANNEL;
    public static final JoinedQueryElement JOIN_SUBCATEGORY_LEFT;
    public static final JoinedQueryElement JOIN_SUBCATEGORY_RIGHT;
    public static final JoinQuery JOIN_SUBCATEGORY;

    public static final Level LEVEL_BIKE_ROW_NUMBER;
    public static final Level LEVEL_BIKE_PRODUCT_KEY;
    public static final Level LEVEL_BIKE_PRODUCT_ALTERNATE_KEY;
    public static final Level LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY;
    public static final Level LEVEL_BIKE_PRODUCT_NAME;
    public static final Level LEVEL_BIKE_STARDART_COST;
    public static final Level LEVEL_BIKE_FINISHED_GOODS_FLAG;
    public static final Level LEVEL_BIKE_COLOR;
    public static final Level LEVEL_BIKE_LIST_PRICE;
    public static final Level LEVEL_BIKE_SIZE;
    public static final Level LEVEL_BIKE_SIZE_RANGE;
    public static final Level LEVEL_BIKE_WIGHT;
    public static final Level LEVEL_BIKE_DEALER_PRICE;
    public static final Level LEVEL_BIKE_CLASS;
    public static final Level LEVEL_BIKE_STYLE;
    public static final Level LEVEL_BIKE_MODEL_NAME;
    public static final Level LEVEL_BIKE_DESCRIPTION;
    public static final Level LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE;
    public static final Level LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE;
    public static final Level LEVEL_BIKE_SAFETY_STOCK_LEVEL;
    public static final Level LEVEL_BIKE_REORDER_POINT;
    public static final Level LEVEL_BIKE_DAYS_TO_MANUFACTURE;
    public static final Level LEVEL_BIKE_PRODUCT_LINE;

    public static final Level LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER;
    public static final Level LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY;
    public static final Level LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY;

    public static final Level LEVEL_CALENDAR_QUARTER_ROW_NUMBER;
    public static final Level LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2;

    public static final Level LEVEL_COUNTRY_ROW_NUMBER;
    public static final Level LEVEL_COUNTRY_COUNTRY_CODE;
    public static final Level LEVEL_COUNTRY_COUNTRY_NAME;

    public static final Level LEVEL_CURRENCY_ROW_NUMBER;
    public static final Level LEVEL_CURRENCY_CURRENCY_KEY;
    public static final Level LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY;
    public static final Level LEVEL_CURRENCY_CURRENCY_NAME;

    public static final Level LEVEL_SALES_CHANNEL_ROW_NUMBER;
    public static final Level LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE;
    public static final Level LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME;

    public static final PhysicalCube CUBE;

    public static final SumMeasure MEASURE_TOTAL_PRODUCT_COST;
    public static final SumMeasure MEASURE_SALES_AMOUNT;

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
        COLUMN_ROW_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_BIKE_SALES.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE_SALES.setId("_column_bike_sales_row_number");
        COLUMN_ROW_NUMBER_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES.setName("SalesOrderNumber");
        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES.setId("_column_bike_sales_sales_order_number");
        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES.setName("SalesOrderLineNumber");
        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES.setId("_column_bike_sales_sales_order_line_number");
        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_REVISION_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REVISION_NUMBER_BIKE_SALES.setName("RevisionNumber");
        COLUMN_REVISION_NUMBER_BIKE_SALES.setId("_column_bike_sales_revision_number");
        COLUMN_REVISION_NUMBER_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_KEY_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_KEY_BIKE_SALES.setName("ProductKey");
        COLUMN_PRODUCT_KEY_BIKE_SALES.setId("_column_bike_sales_product_key");
        COLUMN_PRODUCT_KEY_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_COUNTRY_CODE_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_CODE_BIKE_SALES.setName("CountryCode");
        COLUMN_COUNTRY_CODE_BIKE_SALES.setId("_column_bike_sales_country_code");
        COLUMN_COUNTRY_CODE_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_CURENCY_KEY_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURENCY_KEY_BIKE_SALES.setName("CurrencyKey");
        COLUMN_CURENCY_KEY_BIKE_SALES.setId("_column_bike_sales_currency_key");
        COLUMN_CURENCY_KEY_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_CALENDAR_QUARTER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CALENDAR_QUARTER_BIKE_SALES.setName("CalendarQuarter");
        COLUMN_CALENDAR_QUARTER_BIKE_SALES.setId("_column_bike_sales_calendar_quarter");
        COLUMN_CALENDAR_QUARTER_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES.setName("SalesChannelCode");
        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES.setId("_column_bike_sales_sales_channel_code");
        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_ORDER_QUANTITY_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ORDER_QUANTITY_BIKE_SALES.setName("OrderQuantity");
        COLUMN_ORDER_QUANTITY_BIKE_SALES.setId("_column_bike_sales_sales_order_quantity");
        COLUMN_ORDER_QUANTITY_BIKE_SALES.setType(ColumnType.INTEGER);

        COLUMN_UNIT_PRICE_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_PRICE_BIKE_SALES.setName("UnitPrice");
        COLUMN_UNIT_PRICE_BIKE_SALES.setId("_column_bike_sales_unit_price");
        COLUMN_UNIT_PRICE_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_UNIT_PRICE_BIKE_SALES.setCharOctetLength(19);
        COLUMN_UNIT_PRICE_BIKE_SALES.setDecimalDigits(4);

        COLUMN_EXTENDED_AMOUNT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setName("ExtendedAmount");
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setId("_column_bike_sales_extended_amount");
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setCharOctetLength(19);
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setDecimalDigits(4);

        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES.setName("UnitPriceDiscountPct");
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES.setId("_column_bike_sales_unit_price_discount_pct");
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES.setType(ColumnType.DOUBLE);

        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES.setName("DiscountAmount");
        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES.setId("_column_bike_sales_discount_amount");
        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES.setType(ColumnType.DOUBLE);

        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setName("ProductStandardCost");
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setId("_column_bike_sales_product_standard_cost");
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setCharOctetLength(19);
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setDecimalDigits(4);

        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setName("TotalProductCost");
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setId("_column_bike_sales_total_product_cost");
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setCharOctetLength(19);
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setDecimalDigits(4);

        COLUMN_SALES_AMOUNT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_AMOUNT_BIKE_SALES.setName("SalesAmount");
        COLUMN_SALES_AMOUNT_BIKE_SALES.setId("_column_bike_sales_sales_amount");
        COLUMN_SALES_AMOUNT_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_SALES_AMOUNT_BIKE_SALES.setCharOctetLength(19);
        COLUMN_SALES_AMOUNT_BIKE_SALES.setDecimalDigits(4);

        COLUMN_TAX_AMT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TAX_AMT_BIKE_SALES.setName("TaxAmt");
        COLUMN_TAX_AMT_BIKE_SALES.setId("_column_bike_sales_tax_amt");
        COLUMN_TAX_AMT_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_TAX_AMT_BIKE_SALES.setCharOctetLength(19);
        COLUMN_TAX_AMT_BIKE_SALES.setDecimalDigits(4);

        COLUMN_FREIGHT_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FREIGHT_BIKE_SALES.setName("Freight");
        COLUMN_FREIGHT_BIKE_SALES.setId("_column_bike_sales_freight");
        COLUMN_FREIGHT_BIKE_SALES.setType(ColumnType.DECIMAL);
        COLUMN_FREIGHT_BIKE_SALES.setCharOctetLength(19);
        COLUMN_FREIGHT_BIKE_SALES.setDecimalDigits(4);

        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES.setName("CarrierTrackingNumber");
        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES.setId("_column_bike_sales_carrier_tracking_number");
        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES.setName("CustomerPONumber");
        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES.setId("_column_bike_sales_customer_po_number");
        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES.setName("CustomerAccountNumber");
        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES.setId("_column_bike_sales_customer_account_number");
        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_BIKE.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE.setId("_column_bike_row_number");
        COLUMN_ROW_NUMBER_BIKE.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_KEY_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_KEY_BIKE.setName("ProductKey");
        COLUMN_PRODUCT_KEY_BIKE.setId("_column_bike_product_key");
        COLUMN_PRODUCT_KEY_BIKE.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE.setName("ProductAlternateKey");
        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE.setId("_column_bike_product_alternate_key");
        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE.setId("_column_bike_product_subcategory_key");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_NAME_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_NAME_BIKE.setName("ProductName");
        COLUMN_PRODUCT_NAME_BIKE.setId("_column_bike_product_name");
        COLUMN_PRODUCT_NAME_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_STARDART_COST_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STARDART_COST_BIKE.setName("StandardCost");
        COLUMN_STARDART_COST_BIKE.setId("_column_bike_standard_cost");
        COLUMN_STARDART_COST_BIKE.setType(ColumnType.DECIMAL);
        COLUMN_STARDART_COST_BIKE.setColumnSize(19);
        COLUMN_STARDART_COST_BIKE.setDecimalDigits(4);

        COLUMN_FINISHED_GOODS_FLAG_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_FINISHED_GOODS_FLAG_BIKE.setName("FinishedGoodsFlag");
        COLUMN_FINISHED_GOODS_FLAG_BIKE.setId("_column_bike_finished_goods_flag");
        COLUMN_FINISHED_GOODS_FLAG_BIKE.setType(ColumnType.BOOLEAN);

        COLUMN_COLOR_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COLOR_BIKE.setName("Color");
        COLUMN_COLOR_BIKE.setId("_column_bike_color");
        COLUMN_COLOR_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_LIST_PRICE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_LIST_PRICE_BIKE.setName("ListPrice");
        COLUMN_LIST_PRICE_BIKE.setId("_column_bike_list_price");
        COLUMN_LIST_PRICE_BIKE.setType(ColumnType.DECIMAL);
        COLUMN_LIST_PRICE_BIKE.setColumnSize(19);
        COLUMN_LIST_PRICE_BIKE.setDecimalDigits(4);

        COLUMN_SIZE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_BIKE.setName("Size");
        COLUMN_SIZE_BIKE.setId("_column_bike_size");
        COLUMN_SIZE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_SIZE_RANGE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_RANGE_BIKE.setName("SizeRange");
        COLUMN_SIZE_RANGE_BIKE.setId("_column_bike_size_range");
        COLUMN_SIZE_RANGE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_WIGHT_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WIGHT_BIKE.setName("Weight");
        COLUMN_WIGHT_BIKE.setId("_column_bike_weight");
        COLUMN_WIGHT_BIKE.setType(ColumnType.DOUBLE);

        COLUMN_DEALER_PRICE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEALER_PRICE_BIKE.setName("DealerPrice");
        COLUMN_DEALER_PRICE_BIKE.setId("_column_bike_dealer_price");
        COLUMN_DEALER_PRICE_BIKE.setType(ColumnType.DECIMAL);
        COLUMN_DEALER_PRICE_BIKE.setColumnSize(19);
        COLUMN_DEALER_PRICE_BIKE.setDecimalDigits(4);

        COLUMN_CLASS_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CLASS_BIKE.setName("Class");
        COLUMN_CLASS_BIKE.setId("_column_bike_class");
        COLUMN_CLASS_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_STYLE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STYLE_BIKE.setName("Style");
        COLUMN_STYLE_BIKE.setId("_column_bike_style");
        COLUMN_STYLE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_MODEL_NAME_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_MODEL_NAME_BIKE.setName("ModelName");
        COLUMN_MODEL_NAME_BIKE.setId("_column_bike_model_name");
        COLUMN_MODEL_NAME_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_DESCRIPTION_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DESCRIPTION_BIKE.setName("Description");
        COLUMN_DESCRIPTION_BIKE.setId("_column_bike_description");
        COLUMN_DESCRIPTION_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE.setName("WeightUnitMeasureCode");
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE.setId("_column_bike_weight_unit_measure_code");
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE.setName("WeightUnitMeasureCode");
        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE.setId("_column_bike_size_unit_measure_code");
        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_SAFETY_STOCK_LEVEL_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SAFETY_STOCK_LEVEL_BIKE.setName("SafetyStockLevel");
        COLUMN_SAFETY_STOCK_LEVEL_BIKE.setId("_column_bike_safety_stock_level");
        COLUMN_SAFETY_STOCK_LEVEL_BIKE.setType(ColumnType.INTEGER);

        COLUMN_REORDER_POINT_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_REORDER_POINT_BIKE.setName("ReorderPoint");
        COLUMN_REORDER_POINT_BIKE.setId("_column_bike_reorder_point");
        COLUMN_REORDER_POINT_BIKE.setType(ColumnType.INTEGER);

        COLUMN_DAYS_TO_MANUFACTURE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DAYS_TO_MANUFACTURE_BIKE.setName("DaysToManufacture");
        COLUMN_DAYS_TO_MANUFACTURE_BIKE.setId("_column_bike_days_to_manufacture");
        COLUMN_DAYS_TO_MANUFACTURE_BIKE.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_LINE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_LINE_BIKE.setName("ProductLine");
        COLUMN_PRODUCT_LINE_BIKE.setId("_column_bike_productLine");
        COLUMN_PRODUCT_LINE_BIKE.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY.setId("_column_bike_subcategory_row_number");
        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY.setType(ColumnType.INTEGER);

        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY.setId("_column_bike_subcategory_product_subcategory_key");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY.setType(ColumnType.INTEGER);

        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY.setName("Subcategory");
        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY.setId("_column_bike_subcategory_subcategory");
        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_CALENDAR_QUARTER.setName("RowNumber");
        COLUMN_ROW_NUMBER_CALENDAR_QUARTER.setId("_column_calendar_quarter_row_number");
        COLUMN_ROW_NUMBER_CALENDAR_QUARTER.setType(ColumnType.INTEGER);

        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER.setName("CalendarQuarter2");
        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER.setId("_column_calendar_quarter_calendar_quarter2");
        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_COUNTRY.setName("RowNumber");
        COLUMN_ROW_NUMBER_COUNTRY.setId("_column_country_row_number");
        COLUMN_ROW_NUMBER_COUNTRY.setType(ColumnType.INTEGER);

        COLUMN_COUNTRY_CODE_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_CODE_COUNTRY.setName("CountryCode");
        COLUMN_COUNTRY_CODE_COUNTRY.setId("_column_country_country_code");
        COLUMN_COUNTRY_CODE_COUNTRY.setType(ColumnType.INTEGER);

        COLUMN_COUNTRY_NAME_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_NAME_COUNTRY.setName("CountryName");
        COLUMN_COUNTRY_NAME_COUNTRY.setId("_column_country_country_name");
        COLUMN_COUNTRY_NAME_COUNTRY.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_CURRENCY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_CURRENCY.setName("RowNumber");
        COLUMN_ROW_NUMBER_CURRENCY.setId("_column_currency_row_number");
        COLUMN_ROW_NUMBER_CURRENCY.setType(ColumnType.INTEGER);

        COLUMN_CURRENCY_KEY_CURRENCY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURRENCY_KEY_CURRENCY.setName("CurrencyKey");
        COLUMN_CURRENCY_KEY_CURRENCY.setId("_column_currency_currency_key");
        COLUMN_CURRENCY_KEY_CURRENCY.setType(ColumnType.INTEGER);

        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY.setName("CurrencyAlternateKey");
        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY.setId("_column_currency_currency_alternate_key");
        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY.setType(ColumnType.VARCHAR);

        COLUMN_CURRENCY_NAME_CURRENCY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CURRENCY_NAME_CURRENCY.setName("CurrencyName");
        COLUMN_CURRENCY_NAME_CURRENCY.setId("_column_currency_currency_name");
        COLUMN_CURRENCY_NAME_CURRENCY.setType(ColumnType.VARCHAR);

        COLUMN_ROW_NUMBER_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ROW_NUMBER_SALES_CHANNEL.setName("RowNumber");
        COLUMN_ROW_NUMBER_SALES_CHANNEL.setId("_column_sales_channel_row_number");
        COLUMN_ROW_NUMBER_SALES_CHANNEL.setType(ColumnType.INTEGER);

        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL.setName("SalesChannelCode");
        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL.setId("_column_sales_channel_sales_channel_code");
        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL.setType(ColumnType.VARCHAR);

        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL.setName("SalesChannelName");
        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL.setId("_column_sales_channel_sales_channel_name");
        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL.setType(ColumnType.VARCHAR);

        TABLE_BIKE_SALES = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_BIKE_SALES.setName("BikeSales");
        TABLE_BIKE_SALES.setId("_table_bike_sales");
        TABLE_BIKE_SALES.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_BIKE_SALES,
                COLUMN_SALES_ORDER_NUMBER_BIKE_SALES,
                COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES,
                COLUMN_REVISION_NUMBER_BIKE_SALES,
                COLUMN_PRODUCT_KEY_BIKE_SALES,
                COLUMN_COUNTRY_CODE_BIKE_SALES,
                COLUMN_CURENCY_KEY_BIKE_SALES,
                COLUMN_CALENDAR_QUARTER_BIKE_SALES,
                COLUMN_SALES_CHANNEL_CODE_BIKE_SALES,
                COLUMN_ORDER_QUANTITY_BIKE_SALES,
                COLUMN_UNIT_PRICE_BIKE_SALES,
                COLUMN_EXTENDED_AMOUNT_BIKE_SALES,
                COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES,
                COLUMN_DISCOUNT_AMOUNT_BIKE_SALES,
                COLUMN_PRODUCT_STANDART_COST_BIKE_SALES,
                COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES,
                COLUMN_SALES_AMOUNT_BIKE_SALES,
                COLUMN_TAX_AMT_BIKE_SALES,
                COLUMN_FREIGHT_BIKE_SALES,
                COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES,
                COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES,
                COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES));

        TABLE_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_BIKE_SUBCATEGORY.setName("BikeSubcategory");
        TABLE_BIKE_SUBCATEGORY.setId("_table_bike_subcategory");
        TABLE_BIKE_SUBCATEGORY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY,
                COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY,
                COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY));

        TABLE_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CALENDAR_QUARTER.setName("CalendarQuarter");
        TABLE_CALENDAR_QUARTER.setId("_table_calendar_quarter");
        TABLE_CALENDAR_QUARTER.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_CALENDAR_QUARTER,
                COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER));

        TABLE_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_COUNTRY.setName("Country");
        TABLE_COUNTRY.setId("_table_country");
        TABLE_COUNTRY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_COUNTRY,
                COLUMN_COUNTRY_CODE_COUNTRY,
                COLUMN_COUNTRY_NAME_COUNTRY));

        TABLE_BIKE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_BIKE.setName("Bike");
        TABLE_BIKE.setId("_table_bike");
        TABLE_BIKE.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_BIKE,
                COLUMN_PRODUCT_KEY_BIKE,
                COLUMN_PRODUCT_ALTERNATE_KEY_BIKE,
                COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE,
                COLUMN_PRODUCT_NAME_BIKE,
                COLUMN_STARDART_COST_BIKE,
                COLUMN_FINISHED_GOODS_FLAG_BIKE,
                COLUMN_COLOR_BIKE,
                COLUMN_LIST_PRICE_BIKE,
                COLUMN_SIZE_BIKE,
                COLUMN_SIZE_RANGE_BIKE,
                COLUMN_WIGHT_BIKE,
                COLUMN_DEALER_PRICE_BIKE,
                COLUMN_CLASS_BIKE,
                COLUMN_STYLE_BIKE,
                COLUMN_MODEL_NAME_BIKE,
                COLUMN_DESCRIPTION_BIKE,
                COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE,
                COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE,
                COLUMN_SAFETY_STOCK_LEVEL_BIKE,
                COLUMN_REORDER_POINT_BIKE,
                COLUMN_DAYS_TO_MANUFACTURE_BIKE,
                COLUMN_PRODUCT_LINE_BIKE));


        TABLE_CURRENCY = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CURRENCY.setName("Currency");
        TABLE_CURRENCY.setId("_table_currency");
        TABLE_CURRENCY.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_CURRENCY,
                COLUMN_CURRENCY_KEY_CURRENCY,
                COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY,
                COLUMN_CURRENCY_NAME_CURRENCY));

        TABLE_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_SALES_CHANNEL.setName("SalesChannel");
        TABLE_SALES_CHANNEL.setId("_table_sales_channel");
        TABLE_SALES_CHANNEL.getColumns().addAll(List.of(
                COLUMN_ROW_NUMBER_SALES_CHANNEL,
                COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL,
                COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL));

        LEVEL_BIKE_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_ROW_NUMBER.setName("RowNumber");
        LEVEL_BIKE_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_BIKE);
        LEVEL_BIKE_ROW_NUMBER.setId("_level_bike_row_number");
        LEVEL_BIKE_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_BIKE_PRODUCT_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_KEY.setName("ProductKey");
        LEVEL_BIKE_PRODUCT_KEY.setColumn(COLUMN_PRODUCT_KEY_BIKE);
        LEVEL_BIKE_PRODUCT_KEY.setId("_level_bike_product_key");

        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY.setName("ProductAlternateKey");
        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY.setColumn(COLUMN_PRODUCT_ALTERNATE_KEY_BIKE);
        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY.setId("_level_bike_product_alternate_key");

        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE);
        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY.setId("_level_bike_product_subcategory_key");

        LEVEL_BIKE_PRODUCT_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_NAME.setName("ProductName");
        LEVEL_BIKE_PRODUCT_NAME.setColumn(COLUMN_PRODUCT_NAME_BIKE);
        LEVEL_BIKE_PRODUCT_NAME.setId("_level_bike_product_name");

        LEVEL_BIKE_STARDART_COST = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_STARDART_COST.setName("StandardCost");
        LEVEL_BIKE_STARDART_COST.setColumn(COLUMN_STARDART_COST_BIKE);
        LEVEL_BIKE_STARDART_COST.setId("_level_bike_standard_cost");

        LEVEL_BIKE_FINISHED_GOODS_FLAG = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_FINISHED_GOODS_FLAG.setName("FinishedGoodsFlag");
        LEVEL_BIKE_FINISHED_GOODS_FLAG.setColumn(COLUMN_FINISHED_GOODS_FLAG_BIKE);
        LEVEL_BIKE_FINISHED_GOODS_FLAG.setId("_level_bike_finished_goods_flag");

        LEVEL_BIKE_COLOR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_COLOR.setName("Color");
        LEVEL_BIKE_COLOR.setColumn(COLUMN_COLOR_BIKE);
        LEVEL_BIKE_COLOR.setId("_level_bike_color");

        LEVEL_BIKE_LIST_PRICE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_LIST_PRICE.setName("ListPrice");
        LEVEL_BIKE_LIST_PRICE.setColumn(COLUMN_LIST_PRICE_BIKE);
        LEVEL_BIKE_LIST_PRICE.setId("_level_bike_list_price");

        LEVEL_BIKE_SIZE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE.setName("Size");
        LEVEL_BIKE_SIZE.setColumn(COLUMN_SIZE_BIKE);
        LEVEL_BIKE_SIZE.setId("_level_bike_size");

        LEVEL_BIKE_SIZE_RANGE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE_RANGE.setName("SizeRange");
        LEVEL_BIKE_SIZE_RANGE.setColumn(COLUMN_SIZE_RANGE_BIKE);
        LEVEL_BIKE_SIZE_RANGE.setId("_level_bike_size_range");

        LEVEL_BIKE_WIGHT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_WIGHT.setName("Weight");
        LEVEL_BIKE_WIGHT.setColumn(COLUMN_WIGHT_BIKE);
        LEVEL_BIKE_WIGHT.setId("_level_bike_weight");

        LEVEL_BIKE_DEALER_PRICE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DEALER_PRICE.setName("DealerPrice");
        LEVEL_BIKE_DEALER_PRICE.setColumn(COLUMN_DEALER_PRICE_BIKE);
        LEVEL_BIKE_DEALER_PRICE.setId("_level_bike_dealer_price");

        LEVEL_BIKE_CLASS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_CLASS.setName("Class");
        LEVEL_BIKE_CLASS.setColumn(COLUMN_CLASS_BIKE);
        LEVEL_BIKE_CLASS.setId("_level_bike_class");

        LEVEL_BIKE_STYLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_STYLE.setName("Style");
        LEVEL_BIKE_STYLE.setColumn(COLUMN_STYLE_BIKE);
        LEVEL_BIKE_STYLE.setId("_level_bike_Style");

        LEVEL_BIKE_MODEL_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_MODEL_NAME.setName("ModelName");
        LEVEL_BIKE_MODEL_NAME.setColumn(COLUMN_MODEL_NAME_BIKE);
        LEVEL_BIKE_MODEL_NAME.setId("_level_bike_model_name");

        LEVEL_BIKE_DESCRIPTION = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DESCRIPTION.setName("Description");
        LEVEL_BIKE_DESCRIPTION.setColumn(COLUMN_DESCRIPTION_BIKE);
        LEVEL_BIKE_DESCRIPTION.setId("_level_bike_description");

        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE.setName("WeightUnitMeasureCode");
        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE.setColumn(COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE);
        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE.setId("_level_bike_weight_unit_measure_code");

        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE.setName("SizeUnitMeasureCode");
        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE.setColumn(COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE);
        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE.setId("_level_bike_size_unit_measure_code");

        LEVEL_BIKE_SAFETY_STOCK_LEVEL = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SAFETY_STOCK_LEVEL.setName("SafetyStockLevel");
        LEVEL_BIKE_SAFETY_STOCK_LEVEL.setColumn(COLUMN_SAFETY_STOCK_LEVEL_BIKE);
        LEVEL_BIKE_SAFETY_STOCK_LEVEL.setId("_level_bike_safety_stock_level");

        LEVEL_BIKE_REORDER_POINT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_REORDER_POINT.setName("ReorderPoint");
        LEVEL_BIKE_REORDER_POINT.setColumn(COLUMN_REORDER_POINT_BIKE);
        LEVEL_BIKE_REORDER_POINT.setId("_level_bike_reorder_point");

        LEVEL_BIKE_DAYS_TO_MANUFACTURE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DAYS_TO_MANUFACTURE.setName("DaysToManufacture");
        LEVEL_BIKE_DAYS_TO_MANUFACTURE.setColumn(COLUMN_DAYS_TO_MANUFACTURE_BIKE);
        LEVEL_BIKE_DAYS_TO_MANUFACTURE.setId("_level_bike_days_to_manufacture");

        LEVEL_BIKE_PRODUCT_LINE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_LINE.setName("ProductLine");
        LEVEL_BIKE_PRODUCT_LINE.setColumn(COLUMN_PRODUCT_LINE_BIKE);
        LEVEL_BIKE_PRODUCT_LINE.setId("_level_bike_product_line");

        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setName("RowNumber");
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY);
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setId("_level_bike_subcategory_row_number");
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY);
        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setId("_level_bike_subcategory_product_subcategory_key");

        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY.setName("Subcategory");
        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY.setColumn(COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY);
        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY.setId("_level_bike_subcategory_subcategory");

        LEVEL_CALENDAR_QUARTER_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setName("RowNumber");
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_CALENDAR_QUARTER);
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setId("_level_calendar_quarter_row_number");
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2.setName("CalendarQuarter2");
        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2.setColumn(COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER);
        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2.setId("_level_calendar_quarter_calendar_quarter2");

        LEVEL_COUNTRY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_ROW_NUMBER.setName("RowNumber");
        LEVEL_COUNTRY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_COUNTRY);
        LEVEL_COUNTRY_ROW_NUMBER.setId("_level_country_row_number");
        LEVEL_COUNTRY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_COUNTRY_COUNTRY_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_COUNTRY_CODE.setName("CountryCode");
        LEVEL_COUNTRY_COUNTRY_CODE.setColumn(COLUMN_COUNTRY_CODE_COUNTRY);
        LEVEL_COUNTRY_COUNTRY_CODE.setId("_level_country_country_code");

        LEVEL_COUNTRY_COUNTRY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_COUNTRY_NAME.setName("CountryName");
        LEVEL_COUNTRY_COUNTRY_NAME.setColumn(COLUMN_COUNTRY_NAME_COUNTRY);
        LEVEL_COUNTRY_COUNTRY_NAME.setId("_level_country_country_name");
        LEVEL_COUNTRY_COUNTRY_NAME.setUniqueMembers(true);

        LEVEL_CURRENCY_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_ROW_NUMBER.setName("RowNumber");
        LEVEL_CURRENCY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_CURRENCY);
        LEVEL_CURRENCY_ROW_NUMBER.setId("_level_currency_row_number");
        LEVEL_CURRENCY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_CURRENCY_CURRENCY_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_KEY.setName("CurrencyKey");
        LEVEL_CURRENCY_CURRENCY_KEY.setColumn(COLUMN_CURRENCY_KEY_CURRENCY);
        LEVEL_CURRENCY_CURRENCY_KEY.setId("_level_currency_currency_key");

        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY.setName("CurrencyAlternateKey");
        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY.setColumn(COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY);
        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY.setId("_level_currency_currency_alternate_key");

        LEVEL_CURRENCY_CURRENCY_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_NAME.setName("CurrencyName");
        LEVEL_CURRENCY_CURRENCY_NAME.setColumn(COLUMN_CURRENCY_NAME_CURRENCY);
        LEVEL_CURRENCY_CURRENCY_NAME.setId("_level_currency_currency_name");


        LEVEL_SALES_CHANNEL_ROW_NUMBER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setName("RowNumber");
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_SALES_CHANNEL);
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setId("_level_sales_channel_row_number");
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE.setName("SalesChannelCode");
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE.setColumn(COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL);
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE.setId("_level_sales_channel_sales_channel_code");

        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME.setName("SalesChannelName");
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME.setColumn(COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL);
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME.setId("_level_sales_channel_sales_channel_name");

        // Initialize database schema
        DATABASE_SCHEMA = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA.setId("_databaseSchema");
        DATABASE_SCHEMA.getTables()
                .addAll(List.of(TABLE_BIKE, TABLE_BIKE_SALES, TABLE_BIKE_SUBCATEGORY, TABLE_CALENDAR_QUARTER, TABLE_COUNTRY, TABLE_CURRENCY, TABLE_SALES_CHANNEL));

        TABLEQUERY_BIKE = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_BIKE.setId("_query_bike");
        TABLEQUERY_BIKE.setTable(TABLE_BIKE);

        TABLEQUERY_BIKE_SALES = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_BIKE_SALES.setId("_query_bike_sales");
        TABLEQUERY_BIKE_SALES.setTable(TABLE_BIKE_SALES);

        TABLEQUERY_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_BIKE_SUBCATEGORY.setId("_query_bike_sebcategory");
        TABLEQUERY_BIKE_SUBCATEGORY.setTable(TABLE_BIKE_SUBCATEGORY);

        JOIN_SUBCATEGORY_LEFT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_LEFT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE);
        JOIN_SUBCATEGORY_LEFT.setQuery(TABLEQUERY_BIKE);

        JOIN_SUBCATEGORY_RIGHT = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_RIGHT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY);
        JOIN_SUBCATEGORY_RIGHT.setQuery(TABLEQUERY_BIKE_SUBCATEGORY);

        JOIN_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_SUBCATEGORY.setId("_join_query_subcategory");
        JOIN_SUBCATEGORY.setLeft(JOIN_SUBCATEGORY_LEFT);
        JOIN_SUBCATEGORY.setRight(JOIN_SUBCATEGORY_RIGHT);


        TABLEQUERY_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_CALENDAR_QUARTER.setId("_query_calendar_quarter");
        TABLEQUERY_CALENDAR_QUARTER.setTable(TABLE_CALENDAR_QUARTER);

        TABLEQUERY_COUNTRY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_COUNTRY.setId("_query_country");
        TABLEQUERY_COUNTRY.setTable(TABLE_COUNTRY);

        TABLEQUERY_CURRENCY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_CURRENCY.setId("_query_currency");
        TABLEQUERY_CURRENCY.setTable(TABLE_CURRENCY);

        TABLEQUERY_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_SALES_CHANNEL.setId("_query_sales_channel");
        TABLEQUERY_SALES_CHANNEL.setTable(TABLE_SALES_CHANNEL);

        HIERARCHY_BIKE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_BIKE.setName("Product_Hierarchy");
        HIERARCHY_BIKE.setId("_hierarchy_bike");
        HIERARCHY_BIKE.setHasAll(true);
        HIERARCHY_BIKE.setPrimaryKey(COLUMN_PRODUCT_KEY_BIKE);
        HIERARCHY_BIKE.setQuery(TABLEQUERY_BIKE);
        HIERARCHY_BIKE.getLevels().addAll(List.of(
                LEVEL_BIKE_ROW_NUMBER,
                LEVEL_BIKE_PRODUCT_KEY,
                LEVEL_BIKE_PRODUCT_ALTERNATE_KEY,
                LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY,
                LEVEL_BIKE_PRODUCT_NAME,
                LEVEL_BIKE_STARDART_COST,
                LEVEL_BIKE_FINISHED_GOODS_FLAG,
                LEVEL_BIKE_COLOR,
                LEVEL_BIKE_LIST_PRICE,
                LEVEL_BIKE_SIZE,
                LEVEL_BIKE_SIZE_RANGE,
                LEVEL_BIKE_WIGHT,
                LEVEL_BIKE_DEALER_PRICE,
                LEVEL_BIKE_CLASS,
                LEVEL_BIKE_STYLE,
                LEVEL_BIKE_MODEL_NAME,
                LEVEL_BIKE_DESCRIPTION,
                LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE,
                LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE,
                LEVEL_BIKE_SAFETY_STOCK_LEVEL,
                LEVEL_BIKE_REORDER_POINT,
                LEVEL_BIKE_DAYS_TO_MANUFACTURE,
                LEVEL_BIKE_PRODUCT_LINE));

        HIERARCHY_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_BIKE_SUBCATEGORY.setId("_hierarchy_bike_subcategory");
        HIERARCHY_BIKE_SUBCATEGORY.setHasAll(true);
        HIERARCHY_BIKE_SUBCATEGORY.setPrimaryKey(COLUMN_PRODUCT_KEY_BIKE);
        HIERARCHY_BIKE_SUBCATEGORY.setQuery(JOIN_SUBCATEGORY);
        HIERARCHY_BIKE_SUBCATEGORY.getLevels().addAll(List.of(
                LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER,
                LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY,
                LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY));

        HIERARCHY_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CALENDAR_QUARTER.setId("_hierarchy_calendar_quarter");
        HIERARCHY_CALENDAR_QUARTER.setHasAll(true);
        HIERARCHY_CALENDAR_QUARTER.setPrimaryKey(COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER);
        HIERARCHY_CALENDAR_QUARTER.setQuery(TABLEQUERY_CALENDAR_QUARTER);
        HIERARCHY_CALENDAR_QUARTER.getLevels().addAll(List.of(
                LEVEL_CALENDAR_QUARTER_ROW_NUMBER,
                LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2));

        HIERARCHY_COUNTRY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_COUNTRY.setId("_hierarchy_country");
        HIERARCHY_COUNTRY.setHasAll(true);
        HIERARCHY_COUNTRY.setPrimaryKey(COLUMN_COUNTRY_CODE_COUNTRY);
        HIERARCHY_COUNTRY.setQuery(TABLEQUERY_COUNTRY);
        HIERARCHY_COUNTRY.getLevels().addAll(List.of(
                LEVEL_COUNTRY_ROW_NUMBER,
                LEVEL_COUNTRY_COUNTRY_CODE,
                LEVEL_COUNTRY_COUNTRY_NAME));

        HIERARCHY_CURRENCY = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CURRENCY.setId("_hierarchy_currencyy");
        HIERARCHY_CURRENCY.setHasAll(true);
        HIERARCHY_CURRENCY.setPrimaryKey(COLUMN_CURRENCY_KEY_CURRENCY);
        HIERARCHY_CURRENCY.setQuery(TABLEQUERY_CURRENCY);
        HIERARCHY_CURRENCY.getLevels().addAll(List.of(
                LEVEL_CURRENCY_ROW_NUMBER,
                LEVEL_CURRENCY_CURRENCY_KEY,
                LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY,
                LEVEL_CURRENCY_CURRENCY_NAME));

        HIERARCHY_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_SALES_CHANNEL.setId("_hierarchy_sales_channel");
        HIERARCHY_SALES_CHANNEL.setHasAll(true);
        HIERARCHY_SALES_CHANNEL.setPrimaryKey(COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL);
        HIERARCHY_SALES_CHANNEL.setQuery(TABLEQUERY_SALES_CHANNEL);
        HIERARCHY_SALES_CHANNEL.getLevels().addAll(List.of(
                LEVEL_SALES_CHANNEL_ROW_NUMBER,
                LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE,
                LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME));

        DIMENSION_BIKE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_BIKE.setName("Bike");
        DIMENSION_BIKE.setId("_dimension_bike");
        DIMENSION_BIKE.getHierarchies().add(HIERARCHY_BIKE);

        DIMENSION_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_BIKE_SUBCATEGORY.setName("BikeSubcategory");
        DIMENSION_BIKE_SUBCATEGORY.setId("_dimension_bike_subcategory");
        DIMENSION_BIKE_SUBCATEGORY.getHierarchies().add(HIERARCHY_BIKE_SUBCATEGORY);

        DIMENSION_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CALENDAR_QUARTER.setName("CalendarQuarter");
        DIMENSION_CALENDAR_QUARTER.setId("_dimension_calendar_quarter");
        DIMENSION_CALENDAR_QUARTER.getHierarchies().add(HIERARCHY_CALENDAR_QUARTER);

        DIMENSION_COUNTRY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_COUNTRY.setName("Country");
        DIMENSION_COUNTRY.setId("_dimension_country");
        DIMENSION_COUNTRY.getHierarchies().add(HIERARCHY_COUNTRY);

        DIMENSION_CURRENCY = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CURRENCY.setName("Currency");
        DIMENSION_CURRENCY.setId("_dimension_currency");
        DIMENSION_CURRENCY.getHierarchies().add(HIERARCHY_CURRENCY);

        DIMENSION_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SALES_CHANNEL.setName("SalesChannel");
        DIMENSION_SALES_CHANNEL.setId("_dimension_sales_channel");
        DIMENSION_SALES_CHANNEL.getHierarchies().add(HIERARCHY_SALES_CHANNEL);

        CONNECTOR_BIKE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_BIKE.setDimension(DIMENSION_BIKE);
        CONNECTOR_BIKE.setForeignKey(COLUMN_PRODUCT_KEY_BIKE_SALES);
        CONNECTOR_BIKE.setId("_connector_bike");
        CONNECTOR_BIKE.setOverrideDimensionName("Bike");

        CONNECTOR_BIKE_SUBCATEGORY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_BIKE_SUBCATEGORY.setDimension(DIMENSION_BIKE_SUBCATEGORY);
        CONNECTOR_BIKE_SUBCATEGORY.setForeignKey(COLUMN_PRODUCT_KEY_BIKE_SALES);
        CONNECTOR_BIKE_SUBCATEGORY.setId("_connector_bike_subcategory");
        CONNECTOR_BIKE_SUBCATEGORY.setOverrideDimensionName("BikeSubcategory");

        CONNECTOR_CALENDAR_QUARTER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CALENDAR_QUARTER.setDimension(DIMENSION_CALENDAR_QUARTER);
        CONNECTOR_CALENDAR_QUARTER.setForeignKey(COLUMN_CALENDAR_QUARTER_BIKE_SALES);
        CONNECTOR_CALENDAR_QUARTER.setId("_connector_calendar_quarter");
        CONNECTOR_CALENDAR_QUARTER.setOverrideDimensionName("CalendarQuarter");

        CONNECTOR_COUNTRY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_COUNTRY.setDimension(DIMENSION_COUNTRY);
        CONNECTOR_COUNTRY.setForeignKey(COLUMN_COUNTRY_CODE_BIKE_SALES);
        CONNECTOR_COUNTRY.setId("_connector_country");
        CONNECTOR_COUNTRY.setOverrideDimensionName("Country");

        CONNECTOR_CURRENCY = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CURRENCY.setDimension(DIMENSION_CURRENCY);
        CONNECTOR_CURRENCY.setForeignKey(COLUMN_CURENCY_KEY_BIKE_SALES);
        CONNECTOR_CURRENCY.setId("_connector_currency");
        CONNECTOR_CURRENCY.setOverrideDimensionName("Currency");

        CONNECTOR_SALES_CHANNEL = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_CHANNEL.setDimension(DIMENSION_SALES_CHANNEL);
        CONNECTOR_SALES_CHANNEL.setForeignKey(COLUMN_SALES_CHANNEL_CODE_BIKE_SALES);
        CONNECTOR_SALES_CHANNEL.setId("_connector_sales_channel");
        CONNECTOR_SALES_CHANNEL.setOverrideDimensionName("SalesChannel");

        MEASURE_TOTAL_PRODUCT_COST = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_TOTAL_PRODUCT_COST.setName(MEASURE_NAME1);
        MEASURE_TOTAL_PRODUCT_COST.setId("_measure_sum_of_total_product_cost");
        MEASURE_TOTAL_PRODUCT_COST.setColumn(COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES);

        MEASURE_SALES_AMOUNT = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_SALES_AMOUNT.setName(MEASURE_NAME2);
        MEASURE_SALES_AMOUNT.setId("_measure_sum_of_sales_amount");
        MEASURE_SALES_AMOUNT.setColumn(COLUMN_SALES_AMOUNT_BIKE_SALES);

        MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASURE_GROUP.getMeasures().addAll(List.of(MEASURE_TOTAL_PRODUCT_COST, MEASURE_SALES_AMOUNT));
        MEASURE_GROUP.setName("BikeSales");

        KPI = RolapMappingFactory.eINSTANCE.createKpi();
        KPI.setName("Three Circles Colored");
        KPI.setDescription("KPI Description");
        KPI.setGoal("[Measures].[Sum of TotalProductCost]");
        KPI.setStatus("[Measures].[Sum of TotalProductCost]");

        CUBE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE.setName(CUBE_NAME);
        CUBE.setId("_cube_DescriptionRolePlayingDimensionsDB");
        CUBE.getDimensionConnectors().addAll(List.of(
                CONNECTOR_BIKE,
                CONNECTOR_BIKE_SUBCATEGORY,
                CONNECTOR_CALENDAR_QUARTER,
                CONNECTOR_COUNTRY,
                CONNECTOR_CURRENCY,
                CONNECTOR_SALES_CHANNEL));
        CUBE.setQuery(TABLEQUERY_BIKE_SALES);
        CUBE.getMeasureGroups().add(MEASURE_GROUP);
        CUBE.getKpis().add(KPI);

        CATALOG = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG.setMeasuresDimensionName("BikeSales");
        CATALOG.getDbschemas().add(DATABASE_SCHEMA);
        CATALOG.setId("_catalog_csdl1");
        CATALOG.setName(CATALOG_NAME);
        CATALOG.setDescription(CATALOG_DESCRIPTION);
        CATALOG.getCubes().add(CUBE);

        document(CATALOG, CATALOG_NAME, introBody, 1, 0, 0, false, 0);
        document(DATABASE_SCHEMA, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(TABLEQUERY_BIKE, "Query", queryBody, 1, 2, 0, true, 2);
        document(CUBE, "Cube CSDLBI 1.1", cubeBody, 1, 3, 0, true, 2);
    }

    @Override
    public Catalog get() {
        return CATALOG;
    }

}
