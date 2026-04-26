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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.Kpi;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.7", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    public static final Catalog CATALOG;
    public static final PhysicalCube CUBE;
    public static final Schema DATABASE_SCHEMA;
    public static final TableSource TABLEQUERY_BIKE;


    public static final String CATALOG_NAME = "CSDLBI 1.1";
    public static final String CATALOG_DESCRIPTION = "SalesChannel";
    public static final String CUBE_NAME = "SalesChannel";
    public static final String MEASURE_NAME1 = "Sum of TotalProductCost";
    public static final String MEASURE_NAME2 = "Sum of SalesAmount";

    // field assignment only: DATABASE_SCHEMA

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
    public static final Table TABLE_BIKE;
    public static final Table TABLE_BIKE_SALES;
    public static final Table TABLE_BIKE_SUBCATEGORY;
    public static final Table TABLE_CALENDAR_QUARTER;
    public static final Table TABLE_COUNTRY;
    public static final Table TABLE_CURRENCY;
    public static final Table TABLE_SALES_CHANNEL;

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

    // field assignment only: TABLEQUERY_BIKE
    public static final TableSource TABLEQUERY_BIKE_SALES;
    public static final TableSource TABLEQUERY_BIKE_SUBCATEGORY;
    public static final TableSource TABLEQUERY_CALENDAR_QUARTER;
    public static final TableSource TABLEQUERY_COUNTRY;
    public static final TableSource TABLEQUERY_CURRENCY;
    public static final TableSource TABLEQUERY_SALES_CHANNEL;
    public static final JoinedQueryElement JOIN_SUBCATEGORY_LEFT;
    public static final JoinedQueryElement JOIN_SUBCATEGORY_RIGHT;
    public static final JoinSource JOIN_SUBCATEGORY;

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

    // field assignment only: CUBE

    public static final SumMeasure MEASURE_TOTAL_PRODUCT_COST;
    public static final SumMeasure MEASURE_SALES_AMOUNT;

    // field assignment only: CATALOG

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
        COLUMN_ROW_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_BIKE_SALES.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES.setName("SalesOrderNumber");
        COLUMN_SALES_ORDER_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES.setName("SalesOrderLineNumber");
        COLUMN_SALES_ORDER_LINE_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_REVISION_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_REVISION_NUMBER_BIKE_SALES.setName("RevisionNumber");
        COLUMN_REVISION_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_KEY_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_KEY_BIKE_SALES.setName("ProductKey");
        COLUMN_PRODUCT_KEY_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_COUNTRY_CODE_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_CODE_BIKE_SALES.setName("CountryCode");
        COLUMN_COUNTRY_CODE_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CURENCY_KEY_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CURENCY_KEY_BIKE_SALES.setName("CurrencyKey");
        COLUMN_CURENCY_KEY_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CALENDAR_QUARTER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CALENDAR_QUARTER_BIKE_SALES.setName("CalendarQuarter");
        COLUMN_CALENDAR_QUARTER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES.setName("SalesChannelCode");
        COLUMN_SALES_CHANNEL_CODE_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ORDER_QUANTITY_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ORDER_QUANTITY_BIKE_SALES.setName("OrderQuantity");
        COLUMN_ORDER_QUANTITY_BIKE_SALES.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_UNIT_PRICE_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_PRICE_BIKE_SALES.setName("UnitPrice");
        COLUMN_UNIT_PRICE_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_EXTENDED_AMOUNT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setName("ExtendedAmount");
        COLUMN_EXTENDED_AMOUNT_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES.setName("UnitPriceDiscountPct");
        COLUMN_UNIT_PRICE_DISCOUNT_PCT_BIKE_SALES.setType(SqlSimpleTypes.Sql99.doublePrecisionType());

        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES.setName("DiscountAmount");
        COLUMN_DISCOUNT_AMOUNT_BIKE_SALES.setType(SqlSimpleTypes.Sql99.doublePrecisionType());

        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setName("ProductStandardCost");
        COLUMN_PRODUCT_STANDART_COST_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setName("TotalProductCost");
        COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_SALES_AMOUNT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_AMOUNT_BIKE_SALES.setName("SalesAmount");
        COLUMN_SALES_AMOUNT_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_TAX_AMT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TAX_AMT_BIKE_SALES.setName("TaxAmt");
        COLUMN_TAX_AMT_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FREIGHT_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FREIGHT_BIKE_SALES.setName("Freight");
        COLUMN_FREIGHT_BIKE_SALES.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES.setName("CarrierTrackingNumber");
        COLUMN_CARRIER_TRACKING_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES.setName("CustomerPONumber");
        COLUMN_CUSTOMER_PO_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES.setName("CustomerAccountNumber");
        COLUMN_CUSTOMER_ACCOUNT_NUMBER_BIKE_SALES.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_BIKE.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_KEY_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_KEY_BIKE.setName("ProductKey");
        COLUMN_PRODUCT_KEY_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE.setName("ProductAlternateKey");
        COLUMN_PRODUCT_ALTERNATE_KEY_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_NAME_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_NAME_BIKE.setName("ProductName");
        COLUMN_PRODUCT_NAME_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STARDART_COST_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STARDART_COST_BIKE.setName("StandardCost");
        COLUMN_STARDART_COST_BIKE.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_FINISHED_GOODS_FLAG_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_FINISHED_GOODS_FLAG_BIKE.setName("FinishedGoodsFlag");
        COLUMN_FINISHED_GOODS_FLAG_BIKE.setType(SqlSimpleTypes.Sql99.booleanType());

        COLUMN_COLOR_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COLOR_BIKE.setName("Color");
        COLUMN_COLOR_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_LIST_PRICE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_LIST_PRICE_BIKE.setName("ListPrice");
        COLUMN_LIST_PRICE_BIKE.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_SIZE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SIZE_BIKE.setName("Size");
        COLUMN_SIZE_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SIZE_RANGE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SIZE_RANGE_BIKE.setName("SizeRange");
        COLUMN_SIZE_RANGE_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_WIGHT_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WIGHT_BIKE.setName("Weight");
        COLUMN_WIGHT_BIKE.setType(SqlSimpleTypes.Sql99.doublePrecisionType());

        COLUMN_DEALER_PRICE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEALER_PRICE_BIKE.setName("DealerPrice");
        COLUMN_DEALER_PRICE_BIKE.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_CLASS_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CLASS_BIKE.setName("Class");
        COLUMN_CLASS_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STYLE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STYLE_BIKE.setName("Style");
        COLUMN_STYLE_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_MODEL_NAME_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_MODEL_NAME_BIKE.setName("ModelName");
        COLUMN_MODEL_NAME_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_DESCRIPTION_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DESCRIPTION_BIKE.setName("Description");
        COLUMN_DESCRIPTION_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE.setName("WeightUnitMeasureCode");
        COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE.setName("SizeUnitMeasureCode");
        COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_SAFETY_STOCK_LEVEL_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SAFETY_STOCK_LEVEL_BIKE.setName("SafetyStockLevel");
        COLUMN_SAFETY_STOCK_LEVEL_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_REORDER_POINT_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_REORDER_POINT_BIKE.setName("ReorderPoint");
        COLUMN_REORDER_POINT_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DAYS_TO_MANUFACTURE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DAYS_TO_MANUFACTURE_BIKE.setName("DaysToManufacture");
        COLUMN_DAYS_TO_MANUFACTURE_BIKE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_LINE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_LINE_BIKE.setName("ProductLine");
        COLUMN_PRODUCT_LINE_BIKE.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY.setName("RowNumber");
        COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY.setName("ProductSubcategoryKey");
        COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY.setName("Subcategory");
        COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_CALENDAR_QUARTER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_CALENDAR_QUARTER.setName("RowNumber");
        COLUMN_ROW_NUMBER_CALENDAR_QUARTER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER.setName("CalendarQuarter2");
        COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_COUNTRY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_COUNTRY.setName("RowNumber");
        COLUMN_ROW_NUMBER_COUNTRY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_COUNTRY_CODE_COUNTRY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_CODE_COUNTRY.setName("CountryCode");
        COLUMN_COUNTRY_CODE_COUNTRY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_COUNTRY_NAME_COUNTRY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_NAME_COUNTRY.setName("CountryName");
        COLUMN_COUNTRY_NAME_COUNTRY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_CURRENCY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_CURRENCY.setName("RowNumber");
        COLUMN_ROW_NUMBER_CURRENCY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CURRENCY_KEY_CURRENCY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CURRENCY_KEY_CURRENCY.setName("CurrencyKey");
        COLUMN_CURRENCY_KEY_CURRENCY.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY.setName("CurrencyAlternateKey");
        COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CURRENCY_NAME_CURRENCY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CURRENCY_NAME_CURRENCY.setName("CurrencyName");
        COLUMN_CURRENCY_NAME_CURRENCY.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_ROW_NUMBER_SALES_CHANNEL = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ROW_NUMBER_SALES_CHANNEL.setName("RowNumber");
        COLUMN_ROW_NUMBER_SALES_CHANNEL.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL.setName("SalesChannelCode");
        COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL.setName("SalesChannelName");
        COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL.setType(SqlSimpleTypes.Sql99.varcharType());

        TABLE_BIKE_SALES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_BIKE_SALES.setName("BikeSales");
        TABLE_BIKE_SALES.getFeature().addAll(List.of(
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

        TABLE_BIKE_SUBCATEGORY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_BIKE_SUBCATEGORY.setName("BikeSubcategory");
        TABLE_BIKE_SUBCATEGORY.getFeature().addAll(List.of(
                COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY,
                COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY,
                COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY));

        TABLE_CALENDAR_QUARTER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_CALENDAR_QUARTER.setName("CalendarQuarter");
        TABLE_CALENDAR_QUARTER.getFeature().addAll(List.of(
                COLUMN_ROW_NUMBER_CALENDAR_QUARTER,
                COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER));

        TABLE_COUNTRY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_COUNTRY.setName("Country");
        TABLE_COUNTRY.getFeature().addAll(List.of(
                COLUMN_ROW_NUMBER_COUNTRY,
                COLUMN_COUNTRY_CODE_COUNTRY,
                COLUMN_COUNTRY_NAME_COUNTRY));

        TABLE_BIKE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_BIKE.setName("Bike");
        TABLE_BIKE.getFeature().addAll(List.of(
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


        TABLE_CURRENCY = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_CURRENCY.setName("Currency");
        TABLE_CURRENCY.getFeature().addAll(List.of(
                COLUMN_ROW_NUMBER_CURRENCY,
                COLUMN_CURRENCY_KEY_CURRENCY,
                COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY,
                COLUMN_CURRENCY_NAME_CURRENCY));

        TABLE_SALES_CHANNEL = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_SALES_CHANNEL.setName("SalesChannel");
        TABLE_SALES_CHANNEL.getFeature().addAll(List.of(
                COLUMN_ROW_NUMBER_SALES_CHANNEL,
                COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL,
                COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL));

        LEVEL_BIKE_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_ROW_NUMBER.setName("RowNumber");
        LEVEL_BIKE_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_BIKE);
        LEVEL_BIKE_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_BIKE_PRODUCT_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_KEY.setName("ProductKey");
        LEVEL_BIKE_PRODUCT_KEY.setColumn(COLUMN_PRODUCT_KEY_BIKE);

        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY.setName("ProductAlternateKey");
        LEVEL_BIKE_PRODUCT_ALTERNATE_KEY.setColumn(COLUMN_PRODUCT_ALTERNATE_KEY_BIKE);

        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_BIKE_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE);

        LEVEL_BIKE_PRODUCT_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_NAME.setName("ProductName");
        LEVEL_BIKE_PRODUCT_NAME.setColumn(COLUMN_PRODUCT_NAME_BIKE);

        LEVEL_BIKE_STARDART_COST = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_STARDART_COST.setName("StandardCost");
        LEVEL_BIKE_STARDART_COST.setColumn(COLUMN_STARDART_COST_BIKE);

        LEVEL_BIKE_FINISHED_GOODS_FLAG = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_FINISHED_GOODS_FLAG.setName("FinishedGoodsFlag");
        LEVEL_BIKE_FINISHED_GOODS_FLAG.setColumn(COLUMN_FINISHED_GOODS_FLAG_BIKE);

        LEVEL_BIKE_COLOR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_COLOR.setName("Color");
        LEVEL_BIKE_COLOR.setColumn(COLUMN_COLOR_BIKE);

        LEVEL_BIKE_LIST_PRICE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_LIST_PRICE.setName("ListPrice");
        LEVEL_BIKE_LIST_PRICE.setColumn(COLUMN_LIST_PRICE_BIKE);

        LEVEL_BIKE_SIZE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE.setName("Size");
        LEVEL_BIKE_SIZE.setColumn(COLUMN_SIZE_BIKE);

        LEVEL_BIKE_SIZE_RANGE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE_RANGE.setName("SizeRange");
        LEVEL_BIKE_SIZE_RANGE.setColumn(COLUMN_SIZE_RANGE_BIKE);

        LEVEL_BIKE_WIGHT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_WIGHT.setName("Weight");
        LEVEL_BIKE_WIGHT.setColumn(COLUMN_WIGHT_BIKE);

        LEVEL_BIKE_DEALER_PRICE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DEALER_PRICE.setName("DealerPrice");
        LEVEL_BIKE_DEALER_PRICE.setColumn(COLUMN_DEALER_PRICE_BIKE);

        LEVEL_BIKE_CLASS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_CLASS.setName("Class");
        LEVEL_BIKE_CLASS.setColumn(COLUMN_CLASS_BIKE);

        LEVEL_BIKE_STYLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_STYLE.setName("Style");
        LEVEL_BIKE_STYLE.setColumn(COLUMN_STYLE_BIKE);

        LEVEL_BIKE_MODEL_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_MODEL_NAME.setName("ModelName");
        LEVEL_BIKE_MODEL_NAME.setColumn(COLUMN_MODEL_NAME_BIKE);

        LEVEL_BIKE_DESCRIPTION = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DESCRIPTION.setName("Description");
        LEVEL_BIKE_DESCRIPTION.setColumn(COLUMN_DESCRIPTION_BIKE);

        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE.setName("WeightUnitMeasureCode");
        LEVEL_BIKE_WEIGHT_UNIT_MEASURE_CODE.setColumn(COLUMN_WEIGHT_UNIT_MEASURE_CODE_BIKE);

        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE.setName("SizeUnitMeasureCode");
        LEVEL_BIKE_SIZE_UNIT_MEASURE_CODE.setColumn(COLUMN_SIZE_UNIT_MEASURE_CODE_BIKE);

        LEVEL_BIKE_SAFETY_STOCK_LEVEL = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SAFETY_STOCK_LEVEL.setName("SafetyStockLevel");
        LEVEL_BIKE_SAFETY_STOCK_LEVEL.setColumn(COLUMN_SAFETY_STOCK_LEVEL_BIKE);

        LEVEL_BIKE_REORDER_POINT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_REORDER_POINT.setName("ReorderPoint");
        LEVEL_BIKE_REORDER_POINT.setColumn(COLUMN_REORDER_POINT_BIKE);

        LEVEL_BIKE_DAYS_TO_MANUFACTURE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_DAYS_TO_MANUFACTURE.setName("DaysToManufacture");
        LEVEL_BIKE_DAYS_TO_MANUFACTURE.setColumn(COLUMN_DAYS_TO_MANUFACTURE_BIKE);

        LEVEL_BIKE_PRODUCT_LINE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_PRODUCT_LINE.setName("ProductLine");
        LEVEL_BIKE_PRODUCT_LINE.setColumn(COLUMN_PRODUCT_LINE_BIKE);

        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setName("RowNumber");
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_BIKE_SUBCATEGORY);
        LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setName("ProductSubcategoryKey");
        LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY.setColumn(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY);

        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY.setName("Subcategory");
        LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY.setColumn(COLUMN_SUBCATEGORY_BIKE_SUBCATEGORY);

        LEVEL_CALENDAR_QUARTER_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setName("RowNumber");
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_CALENDAR_QUARTER);
        LEVEL_CALENDAR_QUARTER_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2 = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2.setName("CalendarQuarter2");
        LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2.setColumn(COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER);

        LEVEL_COUNTRY_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_ROW_NUMBER.setName("RowNumber");
        LEVEL_COUNTRY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_COUNTRY);
        LEVEL_COUNTRY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_COUNTRY_COUNTRY_CODE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_COUNTRY_CODE.setName("CountryCode");
        LEVEL_COUNTRY_COUNTRY_CODE.setColumn(COLUMN_COUNTRY_CODE_COUNTRY);

        LEVEL_COUNTRY_COUNTRY_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY_COUNTRY_NAME.setName("CountryName");
        LEVEL_COUNTRY_COUNTRY_NAME.setColumn(COLUMN_COUNTRY_NAME_COUNTRY);
        LEVEL_COUNTRY_COUNTRY_NAME.setUniqueMembers(true);

        LEVEL_CURRENCY_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_ROW_NUMBER.setName("RowNumber");
        LEVEL_CURRENCY_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_CURRENCY);
        LEVEL_CURRENCY_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_CURRENCY_CURRENCY_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_KEY.setName("CurrencyKey");
        LEVEL_CURRENCY_CURRENCY_KEY.setColumn(COLUMN_CURRENCY_KEY_CURRENCY);

        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY.setName("CurrencyAlternateKey");
        LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY.setColumn(COLUMN_CURRENCY_ALTERNATE_KEY_CURRENCY);

        LEVEL_CURRENCY_CURRENCY_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CURRENCY_CURRENCY_NAME.setName("CurrencyName");
        LEVEL_CURRENCY_CURRENCY_NAME.setColumn(COLUMN_CURRENCY_NAME_CURRENCY);


        LEVEL_SALES_CHANNEL_ROW_NUMBER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setName("RowNumber");
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setColumn(COLUMN_ROW_NUMBER_SALES_CHANNEL);
        LEVEL_SALES_CHANNEL_ROW_NUMBER.setUniqueMembers(true);

        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE.setName("SalesChannelCode");
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE.setColumn(COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL);

        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME = LevelFactory.eINSTANCE.createLevel();
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME.setName("SalesChannelName");
        LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME.setColumn(COLUMN_SALES_CHANNEL_NAME_SALES_CHANNEL);

        // Initialize database schema
        DATABASE_SCHEMA = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        DATABASE_SCHEMA.getOwnedElement()
                .addAll(List.of(TABLE_BIKE, TABLE_BIKE_SALES, TABLE_BIKE_SUBCATEGORY, TABLE_CALENDAR_QUARTER, TABLE_COUNTRY, TABLE_CURRENCY, TABLE_SALES_CHANNEL));

        TABLEQUERY_BIKE = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_BIKE.setTable(TABLE_BIKE);

        TABLEQUERY_BIKE_SALES = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_BIKE_SALES.setTable(TABLE_BIKE_SALES);

        TABLEQUERY_BIKE_SUBCATEGORY = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_BIKE_SUBCATEGORY.setTable(TABLE_BIKE_SUBCATEGORY);

        JOIN_SUBCATEGORY_LEFT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_LEFT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE);
        JOIN_SUBCATEGORY_LEFT.setQuery(TABLEQUERY_BIKE);

        JOIN_SUBCATEGORY_RIGHT = SourceFactory.eINSTANCE.createJoinedQueryElement();
        JOIN_SUBCATEGORY_RIGHT.setKey(COLUMN_PRODUCT_SUBCATEGORY_KEY_BIKE_SUBCATEGORY);
        JOIN_SUBCATEGORY_RIGHT.setQuery(TABLEQUERY_BIKE_SUBCATEGORY);

        JOIN_SUBCATEGORY = SourceFactory.eINSTANCE.createJoinSource();
        JOIN_SUBCATEGORY.setLeft(JOIN_SUBCATEGORY_LEFT);
        JOIN_SUBCATEGORY.setRight(JOIN_SUBCATEGORY_RIGHT);


        TABLEQUERY_CALENDAR_QUARTER = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_CALENDAR_QUARTER.setTable(TABLE_CALENDAR_QUARTER);

        TABLEQUERY_COUNTRY = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_COUNTRY.setTable(TABLE_COUNTRY);

        TABLEQUERY_CURRENCY = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_CURRENCY.setTable(TABLE_CURRENCY);

        TABLEQUERY_SALES_CHANNEL = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_SALES_CHANNEL.setTable(TABLE_SALES_CHANNEL);

        HIERARCHY_BIKE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_BIKE.setName("Product_Hierarchy");
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

        HIERARCHY_BIKE_SUBCATEGORY = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_BIKE_SUBCATEGORY.setHasAll(true);
        HIERARCHY_BIKE_SUBCATEGORY.setPrimaryKey(COLUMN_PRODUCT_KEY_BIKE);
        HIERARCHY_BIKE_SUBCATEGORY.setQuery(JOIN_SUBCATEGORY);
        HIERARCHY_BIKE_SUBCATEGORY.getLevels().addAll(List.of(
                LEVEL_BIKE_SUBCATEGORY_ROW_NUMBER,
                LEVEL_BIKE_SUBCATEGORY_PRODUCT_SUBCATEGORY_KEY,
                LEVEL_BIKE_SUBCATEGORY_SUBCATEGORY));

        HIERARCHY_CALENDAR_QUARTER = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CALENDAR_QUARTER.setHasAll(true);
        HIERARCHY_CALENDAR_QUARTER.setPrimaryKey(COLUMN_CALENDAR_QUARTER2_CALENDAR_QUARTER);
        HIERARCHY_CALENDAR_QUARTER.setQuery(TABLEQUERY_CALENDAR_QUARTER);
        HIERARCHY_CALENDAR_QUARTER.getLevels().addAll(List.of(
                LEVEL_CALENDAR_QUARTER_ROW_NUMBER,
                LEVEL_CALENDAR_QUARTER_CALENDAR_QUARTER2));

        HIERARCHY_COUNTRY = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_COUNTRY.setHasAll(true);
        HIERARCHY_COUNTRY.setPrimaryKey(COLUMN_COUNTRY_CODE_COUNTRY);
        HIERARCHY_COUNTRY.setQuery(TABLEQUERY_COUNTRY);
        HIERARCHY_COUNTRY.getLevels().addAll(List.of(
                LEVEL_COUNTRY_ROW_NUMBER,
                LEVEL_COUNTRY_COUNTRY_CODE,
                LEVEL_COUNTRY_COUNTRY_NAME));

        HIERARCHY_CURRENCY = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CURRENCY.setHasAll(true);
        HIERARCHY_CURRENCY.setPrimaryKey(COLUMN_CURRENCY_KEY_CURRENCY);
        HIERARCHY_CURRENCY.setQuery(TABLEQUERY_CURRENCY);
        HIERARCHY_CURRENCY.getLevels().addAll(List.of(
                LEVEL_CURRENCY_ROW_NUMBER,
                LEVEL_CURRENCY_CURRENCY_KEY,
                LEVEL_CURRENCY_CURRENCY_ALTERNATE_KEY,
                LEVEL_CURRENCY_CURRENCY_NAME));

        HIERARCHY_SALES_CHANNEL = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_SALES_CHANNEL.setHasAll(true);
        HIERARCHY_SALES_CHANNEL.setPrimaryKey(COLUMN_SALES_CHANNEL_CODE_SALES_CHANNEL);
        HIERARCHY_SALES_CHANNEL.setQuery(TABLEQUERY_SALES_CHANNEL);
        HIERARCHY_SALES_CHANNEL.getLevels().addAll(List.of(
                LEVEL_SALES_CHANNEL_ROW_NUMBER,
                LEVEL_SALES_CHANNEL_SALES_CHANNEL_CODE,
                LEVEL_SALES_CHANNEL_SALES_CHANNEL_NAME));

        DIMENSION_BIKE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_BIKE.setName("Bike");
        DIMENSION_BIKE.getHierarchies().add(HIERARCHY_BIKE);

        DIMENSION_BIKE_SUBCATEGORY = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_BIKE_SUBCATEGORY.setName("BikeSubcategory");
        DIMENSION_BIKE_SUBCATEGORY.getHierarchies().add(HIERARCHY_BIKE_SUBCATEGORY);

        DIMENSION_CALENDAR_QUARTER = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CALENDAR_QUARTER.setName("CalendarQuarter");
        DIMENSION_CALENDAR_QUARTER.getHierarchies().add(HIERARCHY_CALENDAR_QUARTER);

        DIMENSION_COUNTRY = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_COUNTRY.setName("Country");
        DIMENSION_COUNTRY.getHierarchies().add(HIERARCHY_COUNTRY);

        DIMENSION_CURRENCY = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CURRENCY.setName("Currency");
        DIMENSION_CURRENCY.getHierarchies().add(HIERARCHY_CURRENCY);

        DIMENSION_SALES_CHANNEL = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SALES_CHANNEL.setName("SalesChannel");
        DIMENSION_SALES_CHANNEL.getHierarchies().add(HIERARCHY_SALES_CHANNEL);

        CONNECTOR_BIKE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_BIKE.setDimension(DIMENSION_BIKE);
        CONNECTOR_BIKE.setForeignKey(COLUMN_PRODUCT_KEY_BIKE_SALES);
        CONNECTOR_BIKE.setOverrideDimensionName("Bike");

        CONNECTOR_BIKE_SUBCATEGORY = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_BIKE_SUBCATEGORY.setDimension(DIMENSION_BIKE_SUBCATEGORY);
        CONNECTOR_BIKE_SUBCATEGORY.setForeignKey(COLUMN_PRODUCT_KEY_BIKE_SALES);
        CONNECTOR_BIKE_SUBCATEGORY.setOverrideDimensionName("BikeSubcategory");

        CONNECTOR_CALENDAR_QUARTER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CALENDAR_QUARTER.setDimension(DIMENSION_CALENDAR_QUARTER);
        CONNECTOR_CALENDAR_QUARTER.setForeignKey(COLUMN_CALENDAR_QUARTER_BIKE_SALES);
        CONNECTOR_CALENDAR_QUARTER.setOverrideDimensionName("CalendarQuarter");

        CONNECTOR_COUNTRY = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_COUNTRY.setDimension(DIMENSION_COUNTRY);
        CONNECTOR_COUNTRY.setForeignKey(COLUMN_COUNTRY_CODE_BIKE_SALES);
        CONNECTOR_COUNTRY.setOverrideDimensionName("Country");

        CONNECTOR_CURRENCY = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CURRENCY.setDimension(DIMENSION_CURRENCY);
        CONNECTOR_CURRENCY.setForeignKey(COLUMN_CURENCY_KEY_BIKE_SALES);
        CONNECTOR_CURRENCY.setOverrideDimensionName("Currency");

        CONNECTOR_SALES_CHANNEL = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SALES_CHANNEL.setDimension(DIMENSION_SALES_CHANNEL);
        CONNECTOR_SALES_CHANNEL.setForeignKey(COLUMN_SALES_CHANNEL_CODE_BIKE_SALES);
        CONNECTOR_SALES_CHANNEL.setOverrideDimensionName("SalesChannel");

        MEASURE_TOTAL_PRODUCT_COST = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_TOTAL_PRODUCT_COST.setName(MEASURE_NAME1);
        MEASURE_TOTAL_PRODUCT_COST.setColumn(COLUMN_TOTAL_PRODUCT_COST_BIKE_SALES);

        MEASURE_SALES_AMOUNT = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_SALES_AMOUNT.setName(MEASURE_NAME2);
        MEASURE_SALES_AMOUNT.setColumn(COLUMN_SALES_AMOUNT_BIKE_SALES);

        MEASURE_GROUP = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASURE_GROUP.getMeasures().addAll(List.of(MEASURE_TOTAL_PRODUCT_COST, MEASURE_SALES_AMOUNT));
        MEASURE_GROUP.setName("BikeSales");

        KPI = CubeFactory.eINSTANCE.createKpi();
        KPI.setName("Three Circles Colored");
        KPI.setGoal("[Measures].[Sum of TotalProductCost]");
        KPI.setStatus("[Measures].[Sum of TotalProductCost]");

        CUBE = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE.setName(CUBE_NAME);
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

        CATALOG = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG.setMeasuresDimensionName("BikeSales");
        CATALOG.getDbschemas().add(DATABASE_SCHEMA);
        CATALOG.setName(CATALOG_NAME);
        CATALOG.getCubes().add(CUBE);

    }

    @Override
    public Catalog get() {
        return CATALOG;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection(CATALOG_NAME, introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, DATABASE_SCHEMA, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, TABLEQUERY_BIKE, 2),
                        new DocSection("Cube CSDLBI 1.1", cubeBody, 1, 3, 0, CUBE, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
