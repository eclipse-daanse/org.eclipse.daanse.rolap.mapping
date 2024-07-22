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
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationExcludeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CountMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
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
    private final static TableQueryMappingImpl timeByDayTable = TableQueryMappingImpl.builder().withName("time_by_day"
    ).build();
    private final static TableQueryMappingImpl productTable =
        TableQueryMappingImpl.builder().withName("product").build();
    private final static TableQueryMappingImpl productClassTable = TableQueryMappingImpl.builder().withName(
        "product_class").build();
    private final static JoinQueryMappingImpl join1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
            .withQuery(productTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
            .withQuery(productClassTable).build()).build();
    private final static TableQueryMappingImpl warehouseTable =
        TableQueryMappingImpl.builder().withName("warehouse").build();
    private final static TableQueryMappingImpl promotionTable =
        TableQueryMappingImpl.builder().withName("promotion").build();
    private final static TableQueryMappingImpl customerTable =
        TableQueryMappingImpl.builder().withName("customer").build();

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
            MemberPropertyMappingImpl.builder().withName("Grocery Sqft").withColumn("grocery_sqft").withType("Numeric"
            ).build(),
            MemberPropertyMappingImpl.builder().withName("Frozen Sqft").withColumn("frozen_sqft").withType("Numeric").build(),
            MemberPropertyMappingImpl.builder().withName("Meat Sqft").withColumn("meat_sqft").withType("Numeric").build(),
            MemberPropertyMappingImpl.builder().withName("Has coffee bar").withColumn("coffee_bar").withType("Boolean"
            ).build(),
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

    private final static LevelMappingImpl countryLevel = LevelMappingImpl.builder()
        .withName("Country")
        .withColumn("warehouse_country")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl stateProvinceLevel = LevelMappingImpl.builder()
        .withName("State Province")
        .withColumn("warehouse_state_province")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl cityLevel = LevelMappingImpl.builder()
        .withName("City")
        .withColumn("warehouse_city")
        .withUniqueMembers(false)
        .build();

    private final static LevelMappingImpl warehouseNameLevel = LevelMappingImpl.builder()
        .withName("Warehouse Name")
        .withColumn("warehouse_name")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl mediaTypeLevel = LevelMappingImpl.builder()
        .withName("Media Type")
        .withColumn("media_type")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl promotionNameLevel = LevelMappingImpl.builder()
        .withName("Promotion Name")
        .withColumn("promotion_name")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl country1Level = LevelMappingImpl.builder()
        .withName("Country")
        .withColumn("country")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl stateProvince1Level = LevelMappingImpl.builder()
        .withName("State Province")
        .withColumn("state_province")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl city1Level = LevelMappingImpl.builder()
        .withName("City")
        .withColumn("city")
        .withUniqueMembers(false)
        .build();

    private final static LevelMappingImpl nameLevel = LevelMappingImpl.builder()
        .withName("Name")
        .withColumn("customer_id")
        .withType("Numeric")
        .withUniqueMembers(true)
        .withNameExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "oracle",
                        "h2",
                        "hsqldb",
                        "oracle",
                        "postgres",
                        "luciddb",
                        "teradata"
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "hive"
                    ))
                    .withStatement("`customer`.`fullname`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "access",
                        "mssql"
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "mysql",
                        "mariadb"
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "derby",
                        "neoview",
                        "snowflake"
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "db2"
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "generic"
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withOrdinalExpression(SQLExpressionMappingImpl.builder()
            .withSqls(List.of(
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "oracle",
                        "h2",
                        "hsqldb",
                        "postgres",
                        "luciddb"
                    ))
                    .withStatement("\"fname\" || ' ' || \"lname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "access",
                        "mssql"
                    ))
                    .withStatement("fname + ' ' + lname")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "mysql",
                        "mariadb"
                    ))
                    .withStatement("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "neoview",
                        "derby",
                        "snowflake"
                    ))
                    .withStatement("\"customer\".\"fullname\"")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "db2"
                    ))
                    .withStatement("CONCAT(CONCAT(\"customer\".\"fname\", ' '), \"customer\".\"lname\")")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of(
                        "generic"
                    ))
                    .withStatement("fullname")
                    .build()
            ))
            .build())
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName("Gender").withColumn("gender").build(),
            MemberPropertyMappingImpl.builder().withName("Marital Status").withColumn("marital_status").build(),
            MemberPropertyMappingImpl.builder().withName("Education").withColumn("education").build(),
            MemberPropertyMappingImpl.builder().withName("Yearly Income").withColumn("yearly_income").build()
        ))
        .build();

    /*


     */

    private final static StandardDimensionMappingImpl storeDimension = StandardDimensionMappingImpl.builder()
        .withName("Store")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("store_sqft")
            .withQuery(storeTable)
            .withLevels(List.of(storeCountryLevel, storeStateLevel, storeCityLevel, storeNameLevel))
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
            .withLevels(List.of(yearLevel, quarterLevel, monthLevel))
            .build()))
        .build();

    private final static TimeDimensionMappingImpl productDimension = TimeDimensionMappingImpl.builder()
        .withName("Product")
        .withHierarchies(List.of(
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withPrimaryKey("product_id")
                .withQuery(join1)
                .withLevels(List.of(productFamilyLevel, productDepartmentLevel, productCategoryLevel,
                    productSubcategoryLevel, brandNameLevel, productNameLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Product Family")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(productFamilyLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Product Department")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(productDepartmentLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Product Category")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(productCategoryLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Product Subcategory")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(productSubcategoryLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Brand Name")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(brandNameLevel))
                .build(),
            HierarchyMappingImpl.builder()
                .withHasAll(true)
                .withName("Product Name")
                .withPrimaryKey("product_id")
                .withPrimaryKeyTable("product")
                .withDisplayFolder("Details")
                .withQuery(join1)
                .withLevels(List.of(productNameLevel))
                .build()
        ))
        .build();

    private final static TimeDimensionMappingImpl warehouseDimension = TimeDimensionMappingImpl.builder()
        .withName("Warehouse")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("warehouse_id")
            .withQuery(warehouseTable)
            .withLevels(List.of(countryLevel, stateProvinceLevel, cityLevel, warehouseNameLevel))
            .build()))
        .build();

    private final static TableQueryMappingImpl salesFact1997Table = TableQueryMappingImpl.builder()
        .withName("sales_fact_1997")
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
                    AggregationColumnNameMappingImpl.builder().withColumn("FACT_COUNT").build()
                )
                .withAggregationIgnoreColumns(List.of(
                    AggregationColumnNameMappingImpl.builder().withColumn("foo").build(),
                    AggregationColumnNameMappingImpl.builder().withColumn("bar").build()
                ))
                .withAggregationForeignKeys(List.of(
                    AggregationForeignKeyMappingImpl.builder().withFactColumn("product_id").withAggregationColumn(
                        "PRODUCT_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn("customer_id").withAggregationColumn(
                        "CUSTOMER_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn("promotion_id").withAggregationColumn(
                        "PROMOTION_ID").build(),
                    AggregationForeignKeyMappingImpl.builder().withFactColumn("store_id").withAggregationColumn(
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

    private final static StandardDimensionMappingImpl promotionMediaDimension = StandardDimensionMappingImpl.builder()
        .withName("Promotion Media")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Media")
            .withPrimaryKey("promotion_id")
            .withDefaultMember("All Media")
            .withQuery(promotionTable)
            .withLevels(List.of(mediaTypeLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl promotionsDimension = StandardDimensionMappingImpl.builder()
        .withName("Promotions")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Media")
            .withPrimaryKey("promotion_id")
            .withDefaultMember("[All Promotions]")
            .withQuery(promotionTable)
            .withLevels(List.of(promotionNameLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl customersDimension = StandardDimensionMappingImpl.builder()
        .withName("Customers")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Customers")
            .withPrimaryKey("customer_id")
            .withQuery(customerTable)
            .withLevels(List.of(country1Level, stateProvince1Level, city1Level, nameLevel))
            .build()))
        .build();
    /*

  <Dimension name="Customers" foreignKey="customer_id">
    <Hierarchy hasAll="true" allMemberName="All Customers" primaryKey="customer_id">
      <Table name="customer"/>
      <Level name="Country" column="country" uniqueMembers="true"/>
      <Level name="State Province" column="state_province" uniqueMembers="true"/>
      <Level name="City" column="city" uniqueMembers="false"/>
      <Level name="Name" column="customer_id" type="Numeric" uniqueMembers="true">
        <NameExpression>
          <SqlSelectQuery>
            <SQL>
              <Dialect>oracle</Dialect>
              <Dialect>h2</Dialect>
              <Dialect>hsqldb</Dialect>
              <Dialect>oracle</Dialect>
              <Dialect>postgres</Dialect>
              <Dialect>luciddb</Dialect>
              <Dialect>teradata</Dialect>
              <SQLStatement>
              "fname" || ' ' || "lname"
              </SQLStatement>
            </SQL>
            <SQL>
              <Dialect>hive</Dialect>
              <SQLStatement>
`customer`.`fullname`
              </SQLStatement>
            </SQL>
          <SQL>
            <Dialect>access</Dialect>
            <Dialect>mssql</Dialect>
            <SQLStatement>
fname + ' ' + lname
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>mysql</Dialect>
            <Dialect>mariadb</Dialect>
            <SQLStatement>
CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>derby</Dialect>
            <Dialect>neoview</Dialect>
            <Dialect>snowflake</Dialect>
            <SQLStatement>
"customer"."fullname"
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>db2</Dialect>
            <SQLStatement>
CONCAT(CONCAT("customer"."fname", ' '), "customer"."lname")
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>generic</Dialect>
            <SQLStatement>
fullname
            </SQLStatement>
          </SQL>
          </SqlSelectQuery>
        </NameExpression>
        <OrdinalExpression>
          <SqlSelectQuery>
          <SQL>
            <Dialect>oracle</Dialect>
            <Dialect>h2</Dialect>
            <Dialect>hsqldb</Dialect>
            <Dialect>postgres</Dialect>
            <Dialect>luciddb</Dialect>
            <SQLStatement>
"fname" || ' ' || "lname"
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>access</Dialect>
            <Dialect>mssql</Dialect>
fname + ' ' + lname
          </SQL>
          <SQL>
            <Dialect>mysql</Dialect>
            <Dialect>mariadb</Dialect>
            <SQLStatement>
CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>neoview</Dialect>
            <Dialect>derby</Dialect>
            <Dialect>snowflake</Dialect>
            <SQLStatement>
"customer"."fullname"
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>db2</Dialect>
            <SQLStatement>
CONCAT(CONCAT("customer"."fname", ' '), "customer"."lname")
            </SQLStatement>
          </SQL>
          <SQL>
            <Dialect>generic</Dialect>
            <SQLStatement>
fullname
            </SQLStatement>
          </SQL>
          </SqlSelectQuery>
        </OrdinalExpression>
        <Property name="Gender" column="gender"/>
        <Property name="Marital Status" column="marital_status"/>
        <Property name="Education" column="education"/>
        <Property name="Yearly Income" column="yearly_income"/>
      </Level>
    </Hierarchy>
  </Dimension>


     */
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
                    .withStatement("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\"" +
                        ".\"store_sales\")")
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
                    .withStatement("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else " +
                        "\"sales_fact_1997\".\"store_sales\" end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of("infobright"))
                    .withStatement("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`" +
                        ".`store_sales` end)")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of("access"))
                    .withStatement("`sales_fact_1997`.`store_sales`")
                    .build(),
                SQLMappingImpl.builder()
                    .withDialects(List.of("generic"))
                    .withStatement("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997" +
                        ".store_sales end)")
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

    private final static MeasureGroupMappingImpl storeMeasureGroup = MeasureGroupMappingImpl.builder().build();

    private final static PhysicalCubeMappingImpl salesCube = PhysicalCubeMappingImpl.builder()
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
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store").withDimension(storeDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Size in SQFT").withDimension(storeSizeInSQFTDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Type").withDimension(storeTypeDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Time").withDimension(timeDimension).withForeignKey("time_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Product").withDimension(productDimension).withForeignKey("product_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Promotion Media").withDimension(promotionMediaDimension).withForeignKey("promotion_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Promotions").withDimension(promotionsDimension).withForeignKey("promotion_id").build()
        ))
        .build();
    /*

     */

    private final static PhysicalCubeMappingImpl storeCube = PhysicalCubeMappingImpl.builder()
        .withName("Store")
        .withQuery(storeTable)
        .withMeasureGroups(List.of(storeMeasureGroup))
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
