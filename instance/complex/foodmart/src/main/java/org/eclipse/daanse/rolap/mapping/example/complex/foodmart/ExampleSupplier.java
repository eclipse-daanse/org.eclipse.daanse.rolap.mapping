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
package org.eclipse.daanse.rolap.mapping.example.complex.foodmart;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.RolapContextMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CountMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RolapContextMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SumMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "3")
@Component
public class ExampleSupplier implements RolapContextMappingSupplier {

    private final static String name = "FoodMart";

    private final static String documentation_text = "";

    private final static DocumentationMappingImpl documentation = new DocumentationMappingImpl(documentation_text);

    private final static TableQueryMappingImpl storeTable = TableQueryMappingImpl.builder().withName("store").build();
    private final static TableQueryMappingImpl timeByDayTable = TableQueryMappingImpl.builder().withName("time_by_day").build();
    private final static TableQueryMappingImpl productTable = TableQueryMappingImpl.builder().withName("product").build();
    private final static TableQueryMappingImpl productClassTable = TableQueryMappingImpl.builder().withName("product_class").build();
    private final static JoinQueryMappingImpl join1 = JoinQueryMappingImpl.builder()
    		.withLeft(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
                .withQuery(productTable).build())
    		.withRight(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
                .withQuery(productClassTable).build()).build();

    private final static LevelMappingImpl storeCountryLevel = LevelMappingImpl.builder()
    		.withName("Store Country")
    		.withColumn("store_country")
    		.withUniqueMembers(true)
    		.build();

    private final static LevelMappingImpl storeStateLevel = LevelMappingImpl.builder()
    		.withName("Store State")
    		.withColumn("store_state")
    		.withUniqueMembers(true)
    		.build();

    private final static LevelMappingImpl storeCityLevel = LevelMappingImpl.builder()
    		.withName("Store City")
    		.withColumn("store_city")
    		.withUniqueMembers(false)
    		.build();

    private final static LevelMappingImpl storeNameLevel = LevelMappingImpl.builder()
    		.withName("Store Name")
    		.withColumn("store_name")
    		.withUniqueMembers(true)
    		.withMemberProperties(List.of(
    				MemberPropertyMappingImpl.builder().withName("Store Type").withColumn("store_type").build(),
    				MemberPropertyMappingImpl.builder().withName("Store Manager").withColumn("store_manager").build(),
    				MemberPropertyMappingImpl.builder().withName("Store Sqft").withColumn("store_sqft").withType("Numeric").build(),
    				MemberPropertyMappingImpl.builder().withName("Grocery Sqft").withColumn("grocery_sqft").withType("Numeric").build(),
    				MemberPropertyMappingImpl.builder().withName("Frozen Sqft").withColumn("frozen_sqft").withType("Numeric").build(),
    				MemberPropertyMappingImpl.builder().withName("Meat Sqft").withColumn("meat_sqft").withType("Numeric").build(),
    				MemberPropertyMappingImpl.builder().withName("Has coffee bar").withColumn("coffee_bar").withType("Boolean").build(),
    				MemberPropertyMappingImpl.builder().withName("Street address").withColumn("store_street_address").withType("String").build()
    				))
    		.build();

    private final static LevelMappingImpl storeSqftLevel = LevelMappingImpl.builder()
    		.withName("Store Sqft")
    		.withColumn("store_sqft")
    		.withUniqueMembers(true)
    		.withType("Numeric")
    		.build();

    private final static LevelMappingImpl yearLevel = LevelMappingImpl.builder()
    		.withName("Year")
    		.withColumn("the_year")
    		.withUniqueMembers(true)
    		.withType("Numeric")
    		.withLevelType("TimeYears")
    		.build();

    private final static LevelMappingImpl quarterLevel = LevelMappingImpl.builder()
    		.withName("Quarter")
    		.withColumn("quarter")
    		.withUniqueMembers(false)
    		.withLevelType("TimeQuarters")
    		.build();

    private final static LevelMappingImpl monthLevel = LevelMappingImpl.builder()
    		.withName("Month")
    		.withColumn("month_of_year")
    		.withUniqueMembers(false)
    		.withType("Numeric")
    		.withLevelType("TimeMonths")
    		.build();

    private final static LevelMappingImpl storeTypeLevel = LevelMappingImpl.builder()
    		.withName("Store Type")
    		.withColumn("store_type")
    		.withUniqueMembers(true)
    		.build();

    private final static LevelMappingImpl productFamilyLevel = LevelMappingImpl.builder()
    		.withName("Product Family")
    		.withTable("product_class")
    		.withColumn("product_family")
    		.withUniqueMembers(true)
    		.build();

    private final static LevelMappingImpl productDepartmentLevel = LevelMappingImpl.builder()
    		.withName("Product Department")
    		.withTable("product_class")
    		.withColumn("product_department")
    		.withUniqueMembers(false)
    		.build();

    private final static LevelMappingImpl productCategoryLevel = LevelMappingImpl.builder()
    		.withName("Product Category")
    		.withTable("product_class")
    		.withColumn("product_category")
    		.withUniqueMembers(false)
    		.build();

    private final static LevelMappingImpl productSubcategoryLevel = LevelMappingImpl.builder()
    		.withName("Product Subcategory")
    		.withTable("product_class")
    		.withColumn("product_subcategory")
    		.withUniqueMembers(false)
    		.build();

    private final static LevelMappingImpl brandNameLevel = LevelMappingImpl.builder()
    		.withName("Brand Name")
    		.withTable("product")
    		.withColumn("brand_name")
    		.withUniqueMembers(false)
    		.build();

    private final static LevelMappingImpl productNameLevel = LevelMappingImpl.builder()
    		.withName("Product Name")
    		.withTable("product")
    		.withColumn("product_name")
    		.withUniqueMembers(true)
    		.build();

    private final static StandardDimensionMappingImpl storeDimension = StandardDimensionMappingImpl.builder()
    		.withName("Store")
    		.withHierarchies(List.of(HierarchyMappingImpl.builder()
    				.withHasAll(true)
    				.withPrimaryKey("store_sqft")
    				.withQuery(storeTable)
    				.withLevels(List.of(storeCountryLevel,storeStateLevel,storeCityLevel,storeNameLevel))
    				.build()))
    		.build();

    private final static StandardDimensionMappingImpl storeSizeInSQFTDimension = StandardDimensionMappingImpl.builder()
    		.withName("Store Size in SQFT")
    		.withHierarchies(List.of(HierarchyMappingImpl.builder()
    				.withHasAll(true)
    				.withPrimaryKey("store_id")
    				.withQuery(storeTable)
    				.withLevels(List.of(storeSqftLevel))
    				.build()))
    		.build();

    private final static StandardDimensionMappingImpl storeTypeDimension = StandardDimensionMappingImpl.builder()
    		.withName("Store Type")
    		.withHierarchies(List.of(HierarchyMappingImpl.builder()
    				.withHasAll(true)
    				.withPrimaryKey("store_id")
    				.withQuery(storeTable)
    				.withLevels(List.of(storeTypeLevel))
    				.build()))
    		.build();

    private final static TimeDimensionMappingImpl timeDimension = TimeDimensionMappingImpl.builder()
    		.withName("Time")
    		.withHierarchies(List.of(HierarchyMappingImpl.builder()
    				.withHasAll(false)
    				.withPrimaryKey("time_id")
    				.withQuery(timeByDayTable)
    				.withLevels(List.of(yearLevel,quarterLevel,monthLevel))
    				.build()))
    		.build();

    private final static TimeDimensionMappingImpl productDimension = TimeDimensionMappingImpl.builder()
    		.withName("Product")
    		.withHierarchies(List.of(
    				HierarchyMappingImpl.builder()
    				.withHasAll(true)
    				.withPrimaryKey("product_id")
    				.withQuery(join1)
    				.withLevels(List.of(productFamilyLevel,productDepartmentLevel,productCategoryLevel,productSubcategoryLevel,brandNameLevel,productNameLevel))
    				.build(),
    				HierarchyMappingImpl.builder()
    				.withHasAll(true)
    				.withName("Product Family")
    				.withPrimaryKey("product_id")
    				.withPrimaryKeyTable("product")
    				.withDisplayFolder("Details")
    				.withQuery(join1)
    				.withLevels(List.of(productFamilyLevel))
    				.build()
    			    ))
    		.build();

    /*


    <Hierarchy hasAll="true" name="Product Department" primaryKey="product_id" primaryKeyTable="product" displayFolder="Details">
      <Join>
        <Left key="product_class_id">
          <Table name="product"/>
        </Left>
        <Right key="product_class_id">
          <Table name="product_class"/>
        </Right>
      </Join>
      <Level name="Product Department" table="product_class" column="product_department"
      uniqueMembers="false"/>
    </Hierarchy>
    <Hierarchy hasAll="true" name="Product Category" primaryKey="product_id" primaryKeyTable="product" displayFolder="Details">
      <Join>
        <Left key="product_class_id">
          <Table name="product"/>
        </Left>
        <Right key="product_class_id">
          <Table name="product_class"/>
        </Right>
      </Join>
      <Level name="Product Category" table="product_class" column="product_category"
      uniqueMembers="false"/>
    </Hierarchy>
    <Hierarchy hasAll="true" name="Product Subcategory" primaryKey="product_id" primaryKeyTable="product" displayFolder="Details">
      <Join>
        <Left key="product_class_id">
          <Table name="product"/>
        </Left>
        <Right key="product_class_id">
          <Table name="product_class"/>
        </Right>
      </Join>
      <Level name="Product Subcategory" table="product_class" column="product_subcategory"
      uniqueMembers="false"/>
    </Hierarchy>
    <Hierarchy hasAll="true" name="Brand Name" primaryKey="product_id" primaryKeyTable="product" displayFolder="Details">
      <Join>
        <Left key="product_class_id">
          <Table name="product"/>
        </Left>
        <Right key="product_class_id">
          <Table name="product_class"/>
        </Right>
      </Join>
      <Level name="Brand Name" table="product" column="brand_name" uniqueMembers="false"/>
    </Hierarchy>
    <Hierarchy hasAll="true" name="Product Name" primaryKey="product_id" primaryKeyTable="product" displayFolder="Details">
      <Join>
        <Left key="product_class_id">
          <Table name="product"/>
        </Left>
        <Right key="product_class_id">
          <Table name="product_class"/>
        </Right>
      </Join>
      <Level name="Product Name" table="product" column="product_name"
      uniqueMembers="true"/>
    </Hierarchy>

  </Dimension>


*/


    private final static TableQueryMappingImpl salesFact1997Table = TableQueryMappingImpl.builder().withName("sales_fact_1997").build();

    private final static SumMeasureMappingImpl measure = SumMeasureMappingImpl.builder()
            .withName("Measure-Sum")
            .withColumn("VALUE")
            .build();

    private final static SumMeasureMappingImpl unitSalesMeasure = SumMeasureMappingImpl.builder()
            .withName("Unit Sales")
            .withColumn("unit_sales")
            .withFormatString("Standard")
            .build();

    private final static SumMeasureMappingImpl storeCostMeasure = SumMeasureMappingImpl.builder()
            .withName("Store Cost")
            .withColumn("store_cost")
            .withFormatString("#,###.00")
            .build();

    private final static SumMeasureMappingImpl storeSalesMeasure = SumMeasureMappingImpl.builder()
            .withName("Store Sales")
            .withColumn("store_sales")
            .withFormatString("#,###.00")
            .build();

    private final static SumMeasureMappingImpl salesCountMeasure = SumMeasureMappingImpl.builder()
            .withName("Sales Count")
            .withColumn("product_id")
            .withFormatString("#,###")
            .build();

    private final static CountMeasureMappingImpl customerCountMeasure = CountMeasureMappingImpl.builder()
            .withName("Customer Count")
            .withColumn("customer_id")
            .withFormatString("#,###")
            .build(); //TODO "distinct-count"

    private final static SumMeasureMappingImpl promotionSalesMeasure = SumMeasureMappingImpl.builder()
            .withName("Promotion Sales")
            .withFormatString("#,###")
            .withMeasureExpression(SQLExpressionMappingImpl.builder()
            		.withSqls(List.of(
            				SQLMappingImpl.builder()
            				.withDialects(List.of("access"))
            				.withStatement("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\".\"store_sales\")")
            				.build(),
            				SQLMappingImpl.builder()
            				.withDialects(List.of(
            				        "oracle",
            				        "h2",
            				        "hsqldb",
            				        "postgres",
            				        "neoview",
            				        "derby",
            				        "luciddb",
            				        "db2",
            				        "nuodb",
            				        "snowflake"
            						))
            				.withStatement("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\".\"store_sales\" end)")
            				.build(),
            				SQLMappingImpl.builder()
            				.withDialects(List.of("infobright"))
            				.withStatement("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)")
            				.build(),
            				SQLMappingImpl.builder()
            				.withDialects(List.of("access"))
            				.withStatement("`sales_fact_1997`.`store_sales`")
            				.build(),
            				SQLMappingImpl.builder()
            				.withDialects(List.of("generic"))
            				.withStatement("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)")
            				.build()
            				))
            		.build())
            .build();

    private final static MeasureGroupMappingImpl salesMeasureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(
            		unitSalesMeasure,
            		storeCostMeasure,
            		storeSalesMeasure,
            		salesCountMeasure,
            		customerCountMeasure,
            		promotionSalesMeasure
            		))
            .build();

    private final static MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(measure))
            .build();



    private final static PhysicalCubeMappingImpl salesCube = PhysicalCubeMappingImpl.builder()
            .withName("Sales")
            .withQuery(salesFact1997Table)
            .withMeasureGroups(List.of(salesMeasureGroup))
            .withDocumentation(new DocumentationMappingImpl(""))
            .build();

    private final static PhysicalCubeMappingImpl storeCube = PhysicalCubeMappingImpl.builder()
            .withName("Store")
            .withQuery(storeTable)
            .withMeasureGroups(List.of(measureGroup))
            .withDocumentation(new DocumentationMappingImpl(""))
            .build();

    private final static SchemaMappingImpl schema = SchemaMappingImpl.builder()
            .withName(name)
            .withCubes(List.of(salesCube, storeCube))
            .build();

    private final static CatalogMappingImpl catalog = CatalogMappingImpl.builder()
            .withName(name)
            .withSchemas(List.of(schema))
            .build();

    @Override
    public RolapContextMapping get() {
        return RolapContextMappingImpl.builder()
                .withName(name)
                .withDocumentation(documentation)
                .withCatalogs(List.of(catalog))
                .build();
    }

}
