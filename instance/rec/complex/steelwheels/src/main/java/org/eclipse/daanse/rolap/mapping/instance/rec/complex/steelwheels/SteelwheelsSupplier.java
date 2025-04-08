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
package org.eclipse.daanse.rolap.mapping.instance.rec.complex.steelwheels;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl.Builder;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "4")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class SteelwheelsSupplier implements CatalogMappingSupplier {

    private static final String STATUS = "STATUS";

    private static final String PRODUCT = "Product";

    private static final String CUSTOMERNUMBER = "CUSTOMERNUMBER";

    private static final String PRODUCTS = "products";

    private static final String NAME = "SteelWheels";

    private static final String CUBE_NAME = "SteelWheelsSales";

    //ORDERNUMBER,PRODUCTCODE,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,TOTALPRICE,ORDERDATE,REQUIREDDATE,SHIPPEDDATE,STATUS,COMMENTS,CUSTOMERNUMBER,TIME_ID,QTR_ID,MONTH_ID,YEAR_ID
    //INTEGER,VARCHAR,INTEGER,NUMERIC,INTEGER,NUMERIC,TIMESTAMP,TIMESTAMP,TIMESTAMP,VARCHAR,VARCHAR,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
    public static final PhysicalColumnMappingImpl CUSTOMERNUMBER_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName(CUSTOMERNUMBER).withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalColumnMappingImpl PRODUCTCODE_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("PRODUCTCODE").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl TIME_ID_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("TIME_ID").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl STATUS_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName(STATUS).withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl QUANTITYORDERED_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("QUANTITYORDERED").withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalColumnMappingImpl TOTALPRICE_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("TOTALPRICE").withDataType(ColumnDataType.NUMERIC).build();
    public static final PhysicalColumnMappingImpl ORDERDATE_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("ORDERDATE").withDataType(ColumnDataType.TIMESTAMP).build();
    public static final PhysicalColumnMappingImpl PRICEEACH_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("PRICEEACH").withDataType(ColumnDataType.NUMERIC).build();
    public static final PhysicalColumnMappingImpl REQUIREDDATE_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("REQUIREDDATE").withDataType(ColumnDataType.TIMESTAMP).build();
    public static final PhysicalColumnMappingImpl SHIPPEDDATE_COLUMN_IN_ORDER_FACT = PhysicalColumnMappingImpl.builder().withName("SHIPPEDDATE").withDataType(ColumnDataType.TIMESTAMP).build();
    public static final PhysicalTableMappingImpl ORDER_FACT_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("orderfact")
            .withColumns(List.of(
                    CUSTOMERNUMBER_COLUMN_IN_ORDER_FACT,
                    PRODUCTCODE_COLUMN_IN_ORDER_FACT,
                    TIME_ID_COLUMN_IN_ORDER_FACT,
                    STATUS_COLUMN_IN_ORDER_FACT,
                    QUANTITYORDERED_COLUMN_IN_ORDER_FACT,
                    TOTALPRICE_COLUMN_IN_ORDER_FACT,
                    ORDERDATE_COLUMN_IN_ORDER_FACT,
                    PRICEEACH_COLUMN_IN_ORDER_FACT,
                    REQUIREDDATE_COLUMN_IN_ORDER_FACT,
                    SHIPPEDDATE_COLUMN_IN_ORDER_FACT
                ))).build();

    //CUSTOMERNUMBER,CUSTOMERNAME,CONTACTLASTNAME,CONTACTFIRSTNAME,PHONE,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,POSTALCODE,COUNTRY,EMPLOYEENUMBER,CREDITLIMIT,TERRITORY
    //INTEGER,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,INTEGER,NUMERIC,VARCHAR
    public static final PhysicalColumnMappingImpl CUSTOMERNUMBER_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName(CUSTOMERNUMBER).withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalColumnMappingImpl TERRITORY_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("TERRITORY").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl COUNTRY_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("COUNTRY").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl STATE_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("STATE").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl CITY_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("CITY").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl CUSTOMERNAME_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("CUSTOMERNAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl CONTACTFIRSTNAME_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("CONTACTFIRSTNAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl CONTACTLASTNAME_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("CONTACTLASTNAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl PHONE_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("PHONE").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl ADDRESSLINE1_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("ADDRESSLINE1").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl CREDITLIMIT_COLUMN_IN_CUSTOMER_W_TER = PhysicalColumnMappingImpl.builder().withName("CREDITLIMIT").withDataType(ColumnDataType.NUMERIC).build();
    public static final PhysicalTableMappingImpl CUSTOMER_W_TER_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("customer_w_ter")
            .withColumns(List.of(
                    CUSTOMERNUMBER_COLUMN_IN_CUSTOMER_W_TER,
                    TERRITORY_COLUMN_IN_CUSTOMER_W_TER,
                    COUNTRY_COLUMN_IN_CUSTOMER_W_TER,
                    STATE_COLUMN_IN_CUSTOMER_W_TER,
                    CITY_COLUMN_IN_CUSTOMER_W_TER,
                    CUSTOMERNAME_COLUMN_IN_CUSTOMER_W_TER,
                    CONTACTFIRSTNAME_COLUMN_IN_CUSTOMER_W_TER,
                    CONTACTLASTNAME_COLUMN_IN_CUSTOMER_W_TER,
                    PHONE_COLUMN_IN_CUSTOMER_W_TER,
                    ADDRESSLINE1_COLUMN_IN_CUSTOMER_W_TER,
                    CREDITLIMIT_COLUMN_IN_CUSTOMER_W_TER
                ))).build();

    //PRODUCTCODE,PRODUCTNAME,PRODUCTLINE,PRODUCTSCALE,PRODUCTVENDOR,PRODUCTDESCRIPTION,QUANTITYINSTOCK,BUYPRICE,MSRP
    //VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR(900),INTEGER,NUMERIC,NUMERIC
    public static final PhysicalColumnMappingImpl PRODUCTCODE_COLUMN_IN_PRODUCTS = PhysicalColumnMappingImpl.builder().withName("PRODUCTCODE").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl PRODUCTLINE_COLUMN_IN_PRODUCTS = PhysicalColumnMappingImpl.builder().withName("PRODUCTLINE").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl PRODUCTVENDOR_COLUMN_IN_PRODUCTS = PhysicalColumnMappingImpl.builder().withName("PRODUCTVENDOR").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl PRODUCTNAME_COLUMN_IN_PRODUCTS = PhysicalColumnMappingImpl.builder().withName("PRODUCTNAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl PRODUCTDESCRIPTION_COLUMN_IN_PRODUCTS = PhysicalColumnMappingImpl.builder().withName("PRODUCTDESCRIPTION").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalTableMappingImpl PRODUCTS_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(PRODUCTS)
            .withColumns(List.of(
                    PRODUCTCODE_COLUMN_IN_PRODUCTS,
                    PRODUCTLINE_COLUMN_IN_PRODUCTS,
                    PRODUCTVENDOR_COLUMN_IN_PRODUCTS,
                    PRODUCTNAME_COLUMN_IN_PRODUCTS,
                    PRODUCTDESCRIPTION_COLUMN_IN_PRODUCTS
                ))).build();

    //TIME_ID,MONTH_ID,QTR_ID,YEAR_ID,MONTH_NAME,MONTH_DESC,QTR_NAME,QTR_DESC
    //VARCHAR,INTEGER,INTEGER,INTEGER,VARCHAR,VARCHAR,VARCHAR,VARCHAR
    public static final PhysicalColumnMappingImpl TIME_ID_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("TIME_ID").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl YEAR_ID_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("YEAR_ID").withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalColumnMappingImpl QTR_NAME_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("QTR_NAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl QTR_ID_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("QTR_ID").withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalColumnMappingImpl MONTH_NAME_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("MONTH_NAME").withDataType(ColumnDataType.VARCHAR).build();
    public static final PhysicalColumnMappingImpl MONTH_ID_COLUMN_IN_TIME = PhysicalColumnMappingImpl.builder().withName("MONTH_NAME").withDataType(ColumnDataType.INTEGER).build();
    public static final PhysicalTableMappingImpl TIME_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("time")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_TIME,
                    YEAR_ID_COLUMN_IN_TIME,
                    QTR_NAME_COLUMN_IN_TIME,
                    QTR_ID_COLUMN_IN_TIME,
                    MONTH_NAME_COLUMN_IN_TIME,
                    MONTH_ID_COLUMN_IN_TIME
                ))).build();

    public static final DatabaseSchemaMappingImpl DATABASE_SCHEMA = DatabaseSchemaMappingImpl.builder()
    .withName(NAME)
    .withTables(List.of(ORDER_FACT_TABLE, CUSTOMER_W_TER_TABLE, PRODUCTS_TABLE,
            TIME_TABLE))
    .build();

    public static final TableQueryMappingImpl orderfactTable = TableQueryMappingImpl.builder()
            .withTable(ORDER_FACT_TABLE)
            .build();
    public static final TableQueryMappingImpl customerWTerTable = TableQueryMappingImpl.builder()
            .withTable(CUSTOMER_W_TER_TABLE)
            .build();
    public static final TableQueryMappingImpl productsTable = TableQueryMappingImpl.builder()
            .withTable(PRODUCTS_TABLE)
            .build();
    public static final TableQueryMappingImpl timeTable = TableQueryMappingImpl.builder().withTable(TIME_TABLE).build();

    public static final LevelMappingImpl territoryLevel = LevelMappingImpl.builder()
            .withName("Territory")
            .withColumn(TERRITORY_COLUMN_IN_CUSTOMER_W_TER)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl countryLevel = LevelMappingImpl.builder()
            .withName("Country")
            .withColumn(COUNTRY_COLUMN_IN_CUSTOMER_W_TER)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl stateProvinceLevel = LevelMappingImpl.builder()
            .withName("State Province")
            .withColumn(STATE_COLUMN_IN_CUSTOMER_W_TER)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl cityLevel = LevelMappingImpl.builder()
            .withName("City")
            .withColumn(CITY_COLUMN_IN_CUSTOMER_W_TER)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl customerLevel = LevelMappingImpl.builder()
            .withName("Customer")
            .withColumn(CUSTOMERNAME_COLUMN_IN_CUSTOMER_W_TER)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl lineLevel = LevelMappingImpl.builder()
            .withName("Line")
            .withColumn(PRODUCTLINE_COLUMN_IN_PRODUCTS)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl vendorLevel = LevelMappingImpl.builder()
            .withName("Vendor")
            .withColumn(PRODUCTVENDOR_COLUMN_IN_PRODUCTS)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl productLevel = LevelMappingImpl.builder()
            .withName(PRODUCT)
            .withColumn(PRODUCTNAME_COLUMN_IN_PRODUCTS)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl yearsLevel = LevelMappingImpl.builder()
            .withName("Years")
            .withColumn(YEAR_ID_COLUMN_IN_TIME)
            .withType(InternalDataType.INTEGER)
            .withUniqueMembers(true)
            .withLevelType(LevelType.TIME_YEARS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl quartersLevel = LevelMappingImpl.builder()
            .withName("Quarters")
            .withColumn(QTR_NAME_COLUMN_IN_TIME)
            .withOrdinalColumn(QTR_ID_COLUMN_IN_TIME)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_QUARTERS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl monthsLevel = LevelMappingImpl.builder()
            .withName("Months")
            .withColumn(MONTH_NAME_COLUMN_IN_TIME)
            .withOrdinalColumn(MONTH_ID_COLUMN_IN_TIME)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_MONTHS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl typeLevel = LevelMappingImpl.builder()
            .withName("Type")
            .withColumn(STATUS_COLUMN_IN_ORDER_FACT)
            .withType(InternalDataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final HierarchyMappingImpl marketsHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Markets")
            .withPrimaryKey(CUSTOMERNUMBER_COLUMN_IN_CUSTOMER_W_TER)
            .withQuery(customerWTerTable)
            .withLevels(List.of(territoryLevel, countryLevel, stateProvinceLevel, cityLevel))
            .build();

    public static final StandardDimensionMappingImpl marketsDimension = StandardDimensionMappingImpl.builder()
            .withName("Markets")
            .withHierarchies(List.of(marketsHierarchy))
            .build();

    public static final HierarchyMappingImpl customersHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Customers")
            .withPrimaryKey(CUSTOMERNUMBER_COLUMN_IN_CUSTOMER_W_TER)
            .withQuery(customerWTerTable)
            .withLevels(List.of(customerLevel))
            .build();

    public static final StandardDimensionMappingImpl customersDimension = StandardDimensionMappingImpl.builder()
            .withName("Customers")
            .withHierarchies(List.of(customersHierarchy))
            .build();

    public static final HierarchyMappingImpl productHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Products")
            .withPrimaryKey(PRODUCTCODE_COLUMN_IN_PRODUCTS)
            .withQuery(productsTable)
            .withLevels(List.of(lineLevel, vendorLevel, productLevel))
            .build();

    public static final StandardDimensionMappingImpl productDimension = StandardDimensionMappingImpl.builder()
            .withName(PRODUCT)
            .withHierarchies(List.of(productHierarchy))
            .build();

    public static final HierarchyMappingImpl timeHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Years")
            .withPrimaryKey(TIME_ID_COLUMN_IN_TIME)
            .withQuery(timeTable)
            .withLevels(List.of(yearsLevel, quartersLevel, monthsLevel))
            .build();

    public static final TimeDimensionMappingImpl timeDimension = TimeDimensionMappingImpl.builder()
            .withName("Time")
            .withHierarchies(List.of(timeHierarchy))
            .build();

    public static final HierarchyMappingImpl orderStatusHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Status Types")
            .withPrimaryKey(STATUS_COLUMN_IN_ORDER_FACT)
            .withLevels(List.of(typeLevel))
            .build();

    public static final StandardDimensionMappingImpl orderStatusDimension = StandardDimensionMappingImpl.builder()
            .withName("Order Status")
            .withHierarchies(List.of(orderStatusHierarchy))
            .build();

    public static final MeasureMappingImpl quantityMeasure = MeasureMappingImpl.builder()
            .withName("Quantity")
            .withColumn(QUANTITYORDERED_COLUMN_IN_ORDER_FACT)
            .withFormatString("#,###")
            .withAggregatorType("sum")
            .build();

    public static final MeasureMappingImpl salesMeasure = MeasureMappingImpl.builder()
            .withName("Sales")
            .withColumn(TOTALPRICE_COLUMN_IN_ORDER_FACT)
            .withFormatString("#,###")
            .withAggregatorType("sum")
            .build();

    public static final MeasureGroupMappingImpl steelWheelsSalesMeasureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(quantityMeasure, salesMeasure))
            .build();

    public static final PhysicalCubeMappingImpl steelWheelsSalesCube = PhysicalCubeMappingImpl.builder()
            .withName(CUBE_NAME)
            .withQuery(orderfactTable)
            .withMeasureGroups(List.of(steelWheelsSalesMeasureGroup))
            .withDimensionConnectors(List.of(
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Markets")
                            .withDimension(marketsDimension)
                            .withForeignKey(CUSTOMERNUMBER_COLUMN_IN_ORDER_FACT)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Customers")
                            .withDimension(customersDimension)
                            .withForeignKey(CUSTOMERNUMBER_COLUMN_IN_ORDER_FACT)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName(PRODUCT)
                            .withDimension(productDimension)
                            .withForeignKey(PRODUCTCODE_COLUMN_IN_ORDER_FACT)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Time")
                            .withDimension(timeDimension)
                            .withForeignKey(TIME_ID_COLUMN_IN_ORDER_FACT)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Order Status")
                            .withDimension(orderStatusDimension)
                            .withForeignKey(STATUS_COLUMN_IN_ORDER_FACT)
                            .build()))
            .build();

    public static final CatalogMappingImpl catalog = CatalogMappingImpl.builder()
            .withName(NAME)
            .withCubes(List.of(steelWheelsSalesCube))
            .withDbSchemas(List.of())
            .build();

    @Override
    public CatalogMapping get() {
        return catalog;
    }

}
