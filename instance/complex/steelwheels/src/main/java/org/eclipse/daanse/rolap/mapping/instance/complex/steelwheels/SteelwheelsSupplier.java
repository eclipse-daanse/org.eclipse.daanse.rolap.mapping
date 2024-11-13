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
package org.eclipse.daanse.rolap.mapping.instance.complex.steelwheels;

import java.util.List;

import org.eclipse.daanse.rdb.structure.pojo.ColumnImpl;
import org.eclipse.daanse.rdb.structure.pojo.DatabaseSchemaImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl.Builder;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
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

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    //ORDERNUMBER,PRODUCTCODE,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,TOTALPRICE,ORDERDATE,REQUIREDDATE,SHIPPEDDATE,STATUS,COMMENTS,CUSTOMERNUMBER,TIME_ID,QTR_ID,MONTH_ID,YEAR_ID
    //INTEGER,VARCHAR,INTEGER,NUMERIC,INTEGER,NUMERIC,TIMESTAMP,TIMESTAMP,TIMESTAMP,VARCHAR,VARCHAR,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
    public static final ColumnImpl CUSTOMERNUMBER_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName(CUSTOMERNUMBER).withType("INTEGER").build();
    public static final ColumnImpl PRODUCTCODE_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("PRODUCTCODE").withType("VARCHAR").build();
    public static final ColumnImpl TIME_ID_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("TIME_ID").withType("VARCHAR").build();
    public static final ColumnImpl STATUS_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName(STATUS).withType("VARCHAR").build();
    public static final ColumnImpl QUANTITYORDERED_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("QUANTITYORDERED").withType("INTEGER").build();
    public static final ColumnImpl TOTALPRICE_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("TOTALPRICE").withType("NUMERIC").build();
    public static final ColumnImpl ORDERDATE_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("ORDERDATE").withType("TIMESTAMP").build();
    public static final ColumnImpl PRICEEACH_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("PRICEEACH").withType("NUMERIC").build();
    public static final ColumnImpl REQUIREDDATE_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("REQUIREDDATE").withType("TIMESTAMP").build();
    public static final ColumnImpl SHIPPEDDATE_COLUMN_IN_ORDER_FACT = ColumnImpl.builder().withName("SHIPPEDDATE").withType("TIMESTAMP").build();
    public static final PhysicalTableImpl ORDER_FACT_TABLE = ((Builder) PhysicalTableImpl.builder().withName("orderfact")
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
    public static final ColumnImpl CUSTOMERNUMBER_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName(CUSTOMERNUMBER).withType("INTEGER").build();
    public static final ColumnImpl TERRITORY_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("TERRITORY").withType("VARCHAR").build();
    public static final ColumnImpl COUNTRY_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("COUNTRY").withType("VARCHAR").build();
    public static final ColumnImpl STATE_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("STATE").withType("VARCHAR").build();
    public static final ColumnImpl CITY_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("CITY").withType("VARCHAR").build();
    public static final ColumnImpl CUSTOMERNAME_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("CUSTOMERNAME").withType("VARCHAR").build();
    public static final ColumnImpl CONTACTFIRSTNAME_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("CONTACTFIRSTNAME").withType("VARCHAR").build();
    public static final ColumnImpl CONTACTLASTNAME_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("CONTACTLASTNAME").withType("VARCHAR").build();
    public static final ColumnImpl PHONE_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("PHONE").withType("VARCHAR").build();
    public static final ColumnImpl ADDRESSLINE1_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("ADDRESSLINE1").withType("VARCHAR").build();
    public static final ColumnImpl CREDITLIMIT_COLUMN_IN_CUSTOMER_W_TER = ColumnImpl.builder().withName("CREDITLIMIT").withType("NUMERIC").build();
    public static final PhysicalTableImpl CUSTOMER_W_TER_TABLE = ((Builder) PhysicalTableImpl.builder().withName("customer_w_ter")
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
    public static final ColumnImpl PRODUCTCODE_COLUMN_IN_PRODUCTS = ColumnImpl.builder().withName("PRODUCTCODE").withType("VARCHAR").build();
    public static final ColumnImpl PRODUCTLINE_COLUMN_IN_PRODUCTS = ColumnImpl.builder().withName("PRODUCTLINE").withType("VARCHAR").build();
    public static final ColumnImpl PRODUCTVENDOR_COLUMN_IN_PRODUCTS = ColumnImpl.builder().withName("PRODUCTVENDOR").withType("VARCHAR").build();
    public static final ColumnImpl PRODUCTNAME_COLUMN_IN_PRODUCTS = ColumnImpl.builder().withName("PRODUCTNAME").withType("VARCHAR").build();
    public static final ColumnImpl PRODUCTDESCRIPTION_COLUMN_IN_PRODUCTS = ColumnImpl.builder().withName("PRODUCTDESCRIPTION").withType("VARCHAR").build();
    public static final PhysicalTableImpl PRODUCTS_TABLE = ((Builder) PhysicalTableImpl.builder().withName(PRODUCTS)
            .withColumns(List.of(
                    PRODUCTCODE_COLUMN_IN_PRODUCTS,
                    PRODUCTLINE_COLUMN_IN_PRODUCTS,
                    PRODUCTVENDOR_COLUMN_IN_PRODUCTS,
                    PRODUCTNAME_COLUMN_IN_PRODUCTS,
                    PRODUCTDESCRIPTION_COLUMN_IN_PRODUCTS
                ))).build();

    //TIME_ID,MONTH_ID,QTR_ID,YEAR_ID,MONTH_NAME,MONTH_DESC,QTR_NAME,QTR_DESC
    //VARCHAR,INTEGER,INTEGER,INTEGER,VARCHAR,VARCHAR,VARCHAR,VARCHAR
    public static final ColumnImpl TIME_ID_COLUMN_IN_TIME = ColumnImpl.builder().withName("TIME_ID").withType("VARCHAR").build();
    public static final ColumnImpl YEAR_ID_COLUMN_IN_TIME = ColumnImpl.builder().withName("YEAR_ID").withType("INTEGER").build();
    public static final ColumnImpl QTR_NAME_COLUMN_IN_TIME = ColumnImpl.builder().withName("QTR_NAME").withType("VARCHAR").build();
    public static final ColumnImpl QTR_ID_COLUMN_IN_TIME = ColumnImpl.builder().withName("QTR_ID").withType("INTEGER").build();
    public static final ColumnImpl MONTH_NAME_COLUMN_IN_TIME = ColumnImpl.builder().withName("MONTH_NAME").withType("VARCHAR").build();
    public static final ColumnImpl MONTH_ID_COLUMN_IN_TIME = ColumnImpl.builder().withName("MONTH_NAME").withType("INTEGER").build();
    public static final PhysicalTableImpl TIME_TABLE = ((Builder) PhysicalTableImpl.builder().withName("time")
            .withColumns(List.of(
                    TIME_ID_COLUMN_IN_TIME,
                    YEAR_ID_COLUMN_IN_TIME,
                    QTR_NAME_COLUMN_IN_TIME,
                    QTR_ID_COLUMN_IN_TIME,
                    MONTH_NAME_COLUMN_IN_TIME,
                    MONTH_ID_COLUMN_IN_TIME
                ))).build();

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
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl countryLevel = LevelMappingImpl.builder()
            .withName("Country")
            .withColumn(COUNTRY_COLUMN_IN_CUSTOMER_W_TER)
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl stateProvinceLevel = LevelMappingImpl.builder()
            .withName("State Province")
            .withColumn(STATE_COLUMN_IN_CUSTOMER_W_TER)
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl cityLevel = LevelMappingImpl.builder()
            .withName("City")
            .withColumn(CITY_COLUMN_IN_CUSTOMER_W_TER)
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl customerLevel = LevelMappingImpl.builder()
            .withName("Customer")
            .withColumn(CUSTOMERNAME_COLUMN_IN_CUSTOMER_W_TER)
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl lineLevel = LevelMappingImpl.builder()
            .withName("Line")
            .withTable(PRODUCTS_TABLE)
            .withColumn(PRODUCTLINE_COLUMN_IN_PRODUCTS)
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl vendorLevel = LevelMappingImpl.builder()
            .withName("Vendor")
            .withTable(PRODUCTS_TABLE)
            .withColumn(PRODUCTVENDOR_COLUMN_IN_PRODUCTS)
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl productLevel = LevelMappingImpl.builder()
            .withName(PRODUCT)
            .withTable(PRODUCTS_TABLE)
            .withColumn(PRODUCTNAME_COLUMN_IN_PRODUCTS)
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl yearsLevel = LevelMappingImpl.builder()
            .withName("Years")
            .withColumn(YEAR_ID_COLUMN_IN_TIME)
            .withType(DataType.INTEGER)
            .withUniqueMembers(true)
            .withLevelType(LevelType.TIME_YEARS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl quartersLevel = LevelMappingImpl.builder()
            .withName("Quarters")
            .withColumn(QTR_NAME_COLUMN_IN_TIME)
            .withOrdinalColumn(QTR_ID_COLUMN_IN_TIME)
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_QUARTERS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl monthsLevel = LevelMappingImpl.builder()
            .withName("Months")
            .withColumn(MONTH_NAME_COLUMN_IN_TIME)
            .withOrdinalColumn(MONTH_ID_COLUMN_IN_TIME)
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_MONTHS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl typeLevel = LevelMappingImpl.builder()
            .withName("Type")
            .withColumn(STATUS_COLUMN_IN_ORDER_FACT)
            .withType(DataType.STRING)
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
            .withPrimaryKeyTable(PRODUCTS_TABLE)
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
            .withAggregatorType(MeasureAggregatorType.SUM)
            .build();

    public static final MeasureMappingImpl salesMeasure = MeasureMappingImpl.builder()
            .withName("Sales")
            .withColumn(TOTALPRICE_COLUMN_IN_ORDER_FACT)
            .withFormatString("#,###")
            .withAggregatorType(MeasureAggregatorType.SUM)
            .build();

    public static final MeasureGroupMappingImpl steelWheelsSalesMeasureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(quantityMeasure, salesMeasure))
            .build();

    public static final PhysicalCubeMappingImpl steelWheelsSalesCube = PhysicalCubeMappingImpl.builder()
            .withName(CUBE_NAME)
            .withQuery(orderfactTable)
            .withMeasureGroups(List.of(steelWheelsSalesMeasureGroup))
            .withDocumentation(new DocumentationMappingImpl(""))
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

    public static final SchemaMappingImpl schema = SchemaMappingImpl.builder()
            .withName(NAME)
            .withCubes(List.of(steelWheelsSalesCube))
            .build();

    @Override
    public CatalogMapping get() {
        return CatalogMappingImpl.builder()
                .withName(NAME)
                .withDocumentation(documentation)
                .withSchemas(List.of(schema))
                .withDbschemas(List.of(DatabaseSchemaImpl.builder()
                        .withName(NAME)
                        .withTables(List.of(ORDER_FACT_TABLE, CUSTOMER_W_TER_TABLE, PRODUCTS_TABLE,
                                TIME_TABLE))
                        .build()))
                .build();
    }

}
