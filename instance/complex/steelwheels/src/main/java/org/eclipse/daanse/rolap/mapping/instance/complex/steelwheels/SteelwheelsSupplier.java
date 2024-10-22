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

    public static final PhysicalTableImpl ORDER_FACT_TABLE = ((Builder) PhysicalTableImpl.builder().withName("orderfact")).build();
    public static final PhysicalTableImpl CUSTOMER_W_TER_TABLE = ((Builder) PhysicalTableImpl.builder().withName("customer_w_ter")).build();
    public static final PhysicalTableImpl PRODUCTS_TABLE = ((Builder) PhysicalTableImpl.builder().withName(PRODUCTS)).build();
    public static final PhysicalTableImpl TIME_TABLE = ((Builder) PhysicalTableImpl.builder().withName("time")).build();

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
            .withColumn("TERRITORY")
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl countryLevel = LevelMappingImpl.builder()
            .withName("Country")
            .withColumn("COUNTRY")
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl stateProvinceLevel = LevelMappingImpl.builder()
            .withName("State Province")
            .withColumn("STATE")
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl cityLevel = LevelMappingImpl.builder()
            .withName("City")
            .withColumn("CITY")
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl customerLevel = LevelMappingImpl.builder()
            .withName("Customer")
            .withColumn("CUSTOMERNAME")
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl lineLevel = LevelMappingImpl.builder()
            .withName("Line")
            .withTable(PRODUCTS)
            .withColumn("PRODUCTLINE")
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl vendorLevel = LevelMappingImpl.builder()
            .withName("Vendor")
            .withTable(PRODUCTS)
            .withColumn("PRODUCTVENDOR")
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl productLevel = LevelMappingImpl.builder()
            .withName(PRODUCT)
            .withTable(PRODUCTS)
            .withColumn("PRODUCTNAME")
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl yearsLevel = LevelMappingImpl.builder()
            .withName("Years")
            .withColumn("YEAR_ID")
            .withType(DataType.INTEGER)
            .withUniqueMembers(true)
            .withLevelType(LevelType.TIME_YEARS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl quartersLevel = LevelMappingImpl.builder()
            .withName("Quarters")
            .withColumn("QTR_NAME")
            .withOrdinalColumn("QTR_ID")
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_QUARTERS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl monthsLevel = LevelMappingImpl.builder()
            .withName("Months")
            .withColumn("MONTH_NAME")
            .withOrdinalColumn("MONTH_ID")
            .withType(DataType.STRING)
            .withUniqueMembers(false)
            .withLevelType(LevelType.TIME_MONTHS)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final LevelMappingImpl typeLevel = LevelMappingImpl.builder()
            .withName("Type")
            .withColumn(STATUS)
            .withType(DataType.STRING)
            .withUniqueMembers(true)
            .withLevelType(LevelType.REGULAR)
            .withHideMemberIfType(HideMemberIfType.NEVER)
            .build();

    public static final HierarchyMappingImpl marketsHierarchy = HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Markets")
            .withPrimaryKey(CUSTOMERNUMBER)
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
            .withPrimaryKey(CUSTOMERNUMBER)
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
            .withPrimaryKey("PRODUCTCODE")
            .withPrimaryKeyTable(PRODUCTS)
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
            .withPrimaryKey("TIME_ID")
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
            .withPrimaryKey(STATUS)
            .withLevels(List.of(typeLevel))
            .build();

    public static final StandardDimensionMappingImpl orderStatusDimension = StandardDimensionMappingImpl.builder()
            .withName("Order Status")
            .withHierarchies(List.of(orderStatusHierarchy))
            .build();

    public static final MeasureMappingImpl quantityMeasure = MeasureMappingImpl.builder()
            .withName("Quantity")
            .withColumn("QUANTITYORDERED")
            .withFormatString("#,###")
            .withAggregatorType(MeasureAggregatorType.SUM)
            .build();

    public static final MeasureMappingImpl salesMeasure = MeasureMappingImpl.builder()
            .withName("Sales")
            .withColumn("TOTALPRICE")
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
                            .withForeignKey(CUSTOMERNUMBER)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Customers")
                            .withDimension(customersDimension)
                            .withForeignKey(CUSTOMERNUMBER)
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName(PRODUCT)
                            .withDimension(productDimension)
                            .withForeignKey("PRODUCTCODE")
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Time")
                            .withDimension(timeDimension)
                            .withForeignKey("TIME_ID")
                            .build(),
                    DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Order Status")
                            .withDimension(orderStatusDimension)
                            .withForeignKey(STATUS)
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
                .build();
    }

}
