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
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessSchemaGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationExcludeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.NamedSetMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ParentChildLinkMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RolapContextMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.VirtualCubeMappingImpl;
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
    private final static TableQueryMappingImpl employeeTable = TableQueryMappingImpl.builder().withName(
        "employee").build();
    private final static TableQueryMappingImpl positionTable = TableQueryMappingImpl.builder().withName(
        "position").build();
    private final static TableQueryMappingImpl salaryTable = TableQueryMappingImpl.builder().withName(
        "salary").build();
    private final static TableQueryMappingImpl employeeClosureTable = TableQueryMappingImpl.builder().withName(
        "employee_closure").build();
    private final static TableQueryMappingImpl storeRaggedTable = TableQueryMappingImpl.builder().withName(
        "store_ragged").build();

    private final static JoinQueryMappingImpl join1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
            .withQuery(productTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey("product_class_id")
            .withQuery(productClassTable).build()).build();
    private final static JoinQueryMappingImpl join2 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey("store_id")
            .withQuery(employeeTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey("store_id")
            .withQuery(storeTable).build()).build();
    private final static JoinQueryMappingImpl join3 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder().withKey("position_id")
            .withQuery(employeeTable).build())
        .withRight(JoinedQueryElementMappingImpl.builder().withKey("position_id")
            .withQuery(positionTable).build()).build();
    private final static TableQueryMappingImpl warehouseTable =
        TableQueryMappingImpl.builder().withName("warehouse").build();
    private final static TableQueryMappingImpl promotionTable =
        TableQueryMappingImpl.builder().withName("promotion").build();
    private final static TableQueryMappingImpl customerTable =
        TableQueryMappingImpl.builder().withName("customer").build();
    private final static TableQueryMappingImpl inventoryFact1997Table =
        TableQueryMappingImpl.builder().withName("inventory_fact_1997").build();

    private final static LevelMappingImpl storeCountryLevel = LevelMappingImpl.builder()
        .withName("Store Country")
        .withColumn("store_country")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl storeCountry2Level = LevelMappingImpl.builder()
        .withName("Store Country")
        .withColumn("store_country")
        .withUniqueMembers(true)
        .withHideMemberIf("Never")
        .build();

    private final static LevelMappingImpl storeCountry1Level = LevelMappingImpl.builder()
        .withName("Store Country")
        .withColumn("store_country")
        .withTable("store")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl storeStateLevel = LevelMappingImpl.builder()
        .withName("Store State")
        .withColumn("store_state")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl storeState2Level = LevelMappingImpl.builder()
        .withName("Store State")
        .withColumn("store_state")
        .withUniqueMembers(true)
        .withHideMemberIf("IfParentsName")
        .build();

    private final static LevelMappingImpl storeState1Level = LevelMappingImpl.builder()
        .withName("Store State")
        .withTable("store")
        .withColumn("store_state")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl storeCityLevel = LevelMappingImpl.builder()
        .withName("Store City")
        .withColumn("store_city")
        .withUniqueMembers(false)
        .build();

    private final static LevelMappingImpl storeCity2Level = LevelMappingImpl.builder()
        .withName("Store City")
        .withColumn("store_city")
        .withUniqueMembers(false)
        .withHideMemberIf("IfBlankName")
        .build();

    private final static LevelMappingImpl storeCity1Level = LevelMappingImpl.builder()
        .withName("Store City")
        .withTable("store")
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

    private final static LevelMappingImpl storeName2Level = LevelMappingImpl.builder()
        .withName("Store Name")
        .withColumn("store_name")
        .withUniqueMembers(true)
        .withHideMemberIf("Never")
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

    private final static LevelMappingImpl storeName1Level = LevelMappingImpl.builder()
        .withName("Store Name")
        .withTable("store")
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

    private final static LevelMappingImpl month1Level = LevelMappingImpl.builder()
        .withName("Month")
        .withColumn("month_of_year")
        .withNameColumn("the_month")
        .withUniqueMembers(false)
        .withType("Numeric")
        .withLevelType("TimeMonths")
        .build();

    private final static LevelMappingImpl storeTypeLevel = LevelMappingImpl.builder()
        .withName("Store Type")
        .withColumn("store_type")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl storeType1Level = LevelMappingImpl.builder()
        .withName("Store Type")
        .withTable("store")
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

    private final static LevelMappingImpl country2Level = LevelMappingImpl.builder()
        .withName("Country")
        .withColumn("store_country")
        .withUniqueMembers(true)
        .withHideMemberIf("Never")
        .build();

    private final static LevelMappingImpl stateLevel = LevelMappingImpl.builder()
        .withName("State")
        .withColumn("store_state")
        .withUniqueMembers(true)
        .withHideMemberIf("IfParentsName")
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

    private final static LevelMappingImpl city2Level = LevelMappingImpl.builder()
        .withName("City")
        .withColumn("store_city")
        .withUniqueMembers(false)
        .withHideMemberIf("IfBlankName")
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

    private final static LevelMappingImpl educationLevel = LevelMappingImpl.builder()
        .withName("Education Level")
        .withColumn("education")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl genderLevel = LevelMappingImpl.builder()
        .withName("Gender")
        .withColumn("gender")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl maritalStatusLevel = LevelMappingImpl.builder()
        .withName("Marital Status")
        .withColumn("marital_status")
        .withUniqueMembers(true)
        .withApproxRowCount("111")
        .build();

    private final static LevelMappingImpl yearlyIncomeLevel = LevelMappingImpl.builder()
        .withName("Yearly Income")
        .withColumn("yearly_income")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl hasCoffeeBarLevel = LevelMappingImpl.builder()
        .withName("Has coffee bar")
        .withColumn("coffee_bar")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl payTypeLevel = LevelMappingImpl.builder()
        .withName("Pay Type")
        .withColumn("pay_type")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl managementRoleLevel = LevelMappingImpl.builder()
        .withName("Management Rol")
        .withColumn("management_role")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl positionTitleLevel = LevelMappingImpl.builder()
        .withName("Position Title")
        .withColumn("position_title")
        .withOrdinalColumn("position_id")
        .withUniqueMembers(false)
        .build();

    private final static LevelMappingImpl departmentDescriptionLevel = LevelMappingImpl.builder()
        .withName("Department Description")
        .withType("Numeric")
        .withColumn("department_id")
        .withUniqueMembers(true)
        .build();

    private final static LevelMappingImpl employeeIdLevel = LevelMappingImpl.builder()
        .withName("Employee Id")
        .withType("Numeric")
        .withColumn("employee_id")
        .withParentColumn("supervisor_id")
        .withNullParentValue("0")
        .withParentChildLink(
            ParentChildLinkMappingImpl.builder()
                .withParentColumn("supervisor_id")
                .withChildColumn("employee_id")
                .withTable(employeeClosureTable)
                .build())
        .withUniqueMembers(true)
        .withMemberProperties(List.of(
            MemberPropertyMappingImpl.builder().withName("Marital Status").withColumn("marital_status").build(),
            MemberPropertyMappingImpl.builder().withName("Position Title").withColumn("position_title").build(),
            MemberPropertyMappingImpl.builder().withName("Gender").withColumn("gender").build(),
            MemberPropertyMappingImpl.builder().withName("Salary").withColumn("salary").build(),
            MemberPropertyMappingImpl.builder().withName("Education Level").withColumn("education_level").build(),
            MemberPropertyMappingImpl.builder().withName("Management Role").withColumn("management_role").build()
        ))
        .build();

    private final static CalculatedMemberMappingImpl profitCalculatedMember = CalculatedMemberMappingImpl.builder()
        .withName("Profit")
        .withFormula("[Measures].[Store Sales] - [Measures].[Store Cost]")
        .withCalculatedMemberProperties(List.of(
            CalculatedMemberPropertyMappingImpl.builder()
                .withName("FORMAT_STRING")
                .withValue("$#,##0.00")
                .build(),
            CalculatedMemberPropertyMappingImpl.builder()
                .withName("MEMBER_ORDINAL")
                .withValue("4")
                .build()
        ))
        .build();

    private final static CalculatedMemberMappingImpl profitLastPeriodCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit last Period")
            .withFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName("FORMAT_STRING")
                    .withValue("$#,##0.00")
                    .build(),
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName("MEMBER_ORDINAL")
                    .withValue("18")
                    .build()
            ))
            .build();

    private final static CalculatedMemberMappingImpl profitLastPeriod1CalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit last Period")
            .withFormula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .withVisible(false)
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName("MEMBER_ORDINAL")
                    .withValue("5")
                    .build()
            ))
            .build();

    private final static CalculatedMemberMappingImpl profitGrowthCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            //.withName("Profit Growth")
            .withName("Gewinn-Wachstum")
            .withFormula("([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]")
            .withVisible(true)
            //.withCaption("Gewinn-Wachstum")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName("FORMAT_STRING")
                    .withValue("0.0%")
                    .build()
            ))
            .build();

    private final static CalculatedMemberMappingImpl averageWarehouseSaleCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Average Warehouse Sale")
            .withFormula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]")
            .withCalculatedMemberProperties(List.of(
                CalculatedMemberPropertyMappingImpl.builder()
                    .withName("FORMAT_STRING")
                    .withValue("$#,##0.00")
                    .build()
            ))
            .build();

    private final static CalculatedMemberMappingImpl employeeSalaryCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Employee Salary")
            .withFormatString("Currency")
            .withFormula("([Employees].currentmember.datamember, [Measures].[Org Salary])")
            .build();

    private final static CalculatedMemberMappingImpl avgSalaryCalculatedMember = CalculatedMemberMappingImpl.builder()
        .withName("Avg Salary")
        .withFormatString("Currency")
        .withFormula("[Measures].[Org Salary]/[Measures].[Number of Employees]")
        .build();

    private final static CalculatedMemberMappingImpl profitPerUnitShippedCalculatedMember =
        CalculatedMemberMappingImpl.builder()
            .withName("Profit Per Unit Shipped")
            .withFormatString("Currency")
            .withFormula("[Measures].[Profit] / [Measures].[Units Shipped]")
            .build();

    private final static NamedSetMappingImpl topSellersNamedSet = NamedSetMappingImpl.builder()
        .withName("Top Sellers")
        .withFormula("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])")
        .build();

    private final static HierarchyMappingImpl storeHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withPrimaryKey("store_sqft")
        .withQuery(storeTable)
        .withLevels(List.of(storeCountryLevel, storeStateLevel, storeCityLevel, storeNameLevel))
        .build();

    private final static StandardDimensionMappingImpl storeDimension = StandardDimensionMappingImpl.builder()
        .withName("Store")
        .withHierarchies(List.of(storeHierarchy))
        .build();

    private final static StandardDimensionMappingImpl store2Dimension = StandardDimensionMappingImpl.builder()
        .withName("Store")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("store_id")
            .withQuery(storeRaggedTable)
            .withLevels(List.of(storeCountry2Level, storeState2Level, storeCity2Level, storeName2Level))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl store1Dimension = StandardDimensionMappingImpl.builder()
        .withName("Store")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("employee_id")
            .withQuery(join2)
            .withLevels(List.of(storeCountry1Level, storeState1Level, storeCity1Level, storeName1Level))
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

    private final static StandardDimensionMappingImpl storeType1Dimension = StandardDimensionMappingImpl.builder()
        .withName("Store Type")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(storeTypeLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl storeType2Dimension = StandardDimensionMappingImpl.builder()
        .withName("Store Type")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKeyTable("employee")
            .withPrimaryKey("employee_id")
            .withQuery(join2)
            .withLevels(List.of(storeType1Level))
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

    private final static TimeDimensionMappingImpl time1Dimension = TimeDimensionMappingImpl.builder()
        .withName("Time")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(false)
            .withPrimaryKey("the_date")
            .withQuery(timeByDayTable)
            .withLevels(List.of(yearLevel, quarterLevel, month1Level))
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

    private final static TableQueryMappingImpl salesFact19972Table = TableQueryMappingImpl.builder()
        .withName("sales_fact_1997").build();

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

    private final static TableQueryMappingImpl salesFact19971Table = TableQueryMappingImpl.builder()
        .withName("sales_fact_1997")
        .withAggregationExcludes(List.of(
            AggregationExcludeMappingImpl.builder().withName("agg_pc_10_sales_fact_1997").build(),
            AggregationExcludeMappingImpl.builder().withName("agg_lc_10_sales_fact_1997").build()
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

    private final static StandardDimensionMappingImpl promotionMedia1Dimension = StandardDimensionMappingImpl.builder()
        .withName("Promotion Media")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("promotion_id")
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

    private final static StandardDimensionMappingImpl promotions1Dimension = StandardDimensionMappingImpl.builder()
        .withName("Promotions")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Promotions")
            .withPrimaryKey("promotion_id")
            .withQuery(promotionTable)
            .withLevels(List.of(promotionNameLevel))
            .build()))
        .build();

    private final static HierarchyMappingImpl customersHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName("All Customers")
        .withPrimaryKey("customer_id")
        .withQuery(customerTable)
        .withLevels(List.of(country1Level, stateProvince1Level, city1Level, nameLevel))
        .build();

    private final static StandardDimensionMappingImpl customersDimension = StandardDimensionMappingImpl.builder()
        .withName("Customers")
        .withHierarchies(List.of())
        .build();

    private final static StandardDimensionMappingImpl educationLevelDimension = StandardDimensionMappingImpl.builder()
        .withName("Education Level")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("customer_id")
            .withQuery(customerTable)
            .withLevels(List.of(educationLevel))
            .build()))
        .build();

    private final static HierarchyMappingImpl genderHierarchy = HierarchyMappingImpl.builder()
        .withHasAll(true)
        .withAllMemberName("All Gender")
        .withPrimaryKey("customer_id")
        .withQuery(customerTable)
        .withLevels(List.of(genderLevel))
        .build();

    private final static StandardDimensionMappingImpl genderDimension = StandardDimensionMappingImpl.builder()
        .withName("Gender")
        .withHierarchies(List.of(genderHierarchy))
        .build();

    private final static StandardDimensionMappingImpl maritalStatusDimension = StandardDimensionMappingImpl.builder()
        .withName("Marital Status")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Marital Status")
            .withPrimaryKey("customer_id")
            .withQuery(customerTable)
            .withLevels(List.of(maritalStatusLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl yearlyIncomeDimension = StandardDimensionMappingImpl.builder()
        .withName("Yearly Income")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Marital Status")
            .withPrimaryKey("customer_id")
            .withQuery(customerTable)
            .withLevels(List.of(yearlyIncomeLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl yearlyIncome1Dimension = StandardDimensionMappingImpl.builder()
        .withName("Yearly Income")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("customer_id")
            .withQuery(customerTable)
            .withLevels(List.of(yearlyIncomeLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl hasCoffeeBarDimension = StandardDimensionMappingImpl.builder()
        .withName("Has coffee bar")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withLevels(List.of(hasCoffeeBarLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl payTypeDimension = StandardDimensionMappingImpl.builder()
        .withName("Pay Type")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("employee_id")
            .withPrimaryKeyTable("employee")
            .withQuery(join3)
            .withLevels(List.of(payTypeLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl positionDimension = StandardDimensionMappingImpl.builder()
        .withName("Position")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("employee_id")
            .withPrimaryKeyTable("employee")
            .withQuery(employeeTable)
            .withLevels(List.of(managementRoleLevel, positionTitleLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl departmentDimension = StandardDimensionMappingImpl.builder()
        .withName("Department")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("department_id")
            .withQuery(employeeTable)
            .withLevels(List.of(departmentDescriptionLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl employeesDimension = StandardDimensionMappingImpl.builder()
        .withName("Employees")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withAllMemberName("All Employees")
            .withPrimaryKey("employee_id")
            .withQuery(employeeTable)
            .withLevels(List.of(employeeIdLevel))
            .build()))
        .build();

    private final static StandardDimensionMappingImpl geographyDimension = StandardDimensionMappingImpl.builder()
        .withName("Geography")
        .withHierarchies(List.of(HierarchyMappingImpl.builder()
            .withHasAll(true)
            .withPrimaryKey("store_id")
            .withQuery(storeRaggedTable)
            .withLevels(List.of(country2Level, stateLevel, city2Level))
            .build()))
        .build();

    private final static MeasureMappingImpl unitSalesMeasure = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl unitSales1Measure = MeasureMappingImpl.builder()
        .withName("Unit Sales")
        .withColumn("unit_sales")
        .withFormatString("Standard")
        .withType("sum")
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName("MEMBER_ORDINAL").withValue("2").build()
        ))
        .build();

    private final static MeasureMappingImpl storeCostMeasure = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString("#,###.00")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl storeCost1Measure = MeasureMappingImpl.builder()
        .withName("Store Cost")
        .withColumn("store_cost")
        .withFormatString("#,###.00")
        .withType("sum")
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName("MEMBER_ORDINAL").withValue("6").build()
        ))
        .build();

    private final static MeasureMappingImpl storeSalesMeasure = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString("#,###.00")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl storeSales1Measure = MeasureMappingImpl.builder()
        .withName("Store Sales")
        .withColumn("store_sales")
        .withFormatString("#,###.00")
        .withType("sum")
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName("MEMBER_ORDINAL").withValue("3").build()
        ))
        .build();

    private final static MeasureMappingImpl salesCountMeasure = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn("product_id")
        .withFormatString("#,###")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl salesCount1Measure = MeasureMappingImpl.builder()
        .withName("Sales Count")
        .withColumn("product_id")
        .withFormatString("#,###")
        .withType("count")
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName("MEMBER_ORDINAL").withValue("1").build()
        ))
        .build();

    private final static MeasureMappingImpl customerCountMeasure = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn("customer_id")
        .withFormatString("#,###")
        .withType("distinct-count")
        .build();

    private final static MeasureMappingImpl customerCount1Measure = MeasureMappingImpl.builder()
        .withName("Customer Count")
        .withColumn("customer_id")
        .withFormatString("#,###")
        .withType("distinct-count")
        .withCalculatedMemberProperty(List.of(
            CalculatedMemberPropertyMappingImpl.builder().withName("MEMBER_ORDINAL").withValue("7").build()
        ))
        .build();

    private final static MeasureMappingImpl promotionSalesMeasure = MeasureMappingImpl.builder()
        .withName("Promotion Sales")
        .withFormatString("#,###")
        .withType("sum")
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

    private final static MeasureMappingImpl storeInvoiceMeasure = MeasureMappingImpl.builder()
        .withName("Store Invoice")
        .withColumn("store_invoice")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl supplyTimeMeasure = MeasureMappingImpl.builder()
        .withName("Supply Time")
        .withColumn("supply_time")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl warehouseCostMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Cost")
        .withColumn("warehouse_cost")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl warehouseSalesMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Sales")
        .withColumn("warehouse_sales")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl unitsShippedMeasure = MeasureMappingImpl.builder()
        .withName("Units Shipped")
        .withColumn("units_shipped")
        .withFormatString("#.0")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl unitsOrderedMeasure = MeasureMappingImpl.builder()
        .withName("Units Ordered")
        .withColumn("units_ordered")
        .withFormatString("#.0")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl warehouseProfitMeasure = MeasureMappingImpl.builder()
        .withName("Warehouse Profit")
        .withType("sum")
        .withMeasureExpression(
            SQLExpressionMappingImpl.builder()
                .withSqls(List.of(
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            "mysql",
                            "mariadb",
                            "infobright"
                        ))
                        .withStatement("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                        .build(),
                    SQLMappingImpl.builder()
                        .withDialects(List.of(
                            "generic"
                        ))
                        .withStatement("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;" +
                            "warehouse_cost&quot;")
                        .build()
                ))
                .build()
        )
        .build();

    private final static MeasureMappingImpl storeSqftMeasure = MeasureMappingImpl.builder()
        .withName("Store Sqft")
        .withColumn("store_sqft")
        .withFormatString("#,###")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl grocerySqftMeasure = MeasureMappingImpl.builder()
        .withName("Grocery Sqft")
        .withColumn("grocery_sqft")
        .withFormatString("#,###")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl orgSalaryMeasure = MeasureMappingImpl.builder()
        .withName("Org Salary")
        .withColumn("salary_paid")
        .withFormatString("Currency")
        .withType("sum")
        .build();

    private final static MeasureMappingImpl countMeasure = MeasureMappingImpl.builder()
        .withName("Count")
        .withColumn("employee_id")
        .withFormatString("#,#")
        .withType("count")
        .build();

    private final static MeasureMappingImpl numberOfEmployeesMeasure = MeasureMappingImpl.builder()
        .withName("Number of Employees")
        .withColumn("employee_id")
        .withFormatString("#,#")
        .withType("distinct-count")
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

    private final static MeasureGroupMappingImpl vSalesMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            salesCountMeasure,
            storeCostMeasure,
            storeSalesMeasure,
            unitSalesMeasure
        ))
        .build();

    private final static MeasureGroupMappingImpl warehouseMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            storeInvoiceMeasure,
            supplyTimeMeasure,
            warehouseCostMeasure,
            warehouseSalesMeasure,
            unitsShippedMeasure,
            unitsOrderedMeasure,
            warehouseProfitMeasure
        ))
        .build();

    private final static MeasureGroupMappingImpl vWarehouseMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            storeInvoiceMeasure,
            supplyTimeMeasure,
            unitsOrderedMeasure,
            unitsShippedMeasure,
            warehouseCostMeasure,
            warehouseProfitMeasure,
            warehouseSalesMeasure
        ))
        .build();

    private final static MeasureGroupMappingImpl storeMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(storeSqftMeasure, grocerySqftMeasure))
        .build();

    private final static MeasureGroupMappingImpl hrMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(orgSalaryMeasure, countMeasure, numberOfEmployeesMeasure))
        .build();

    private final static MeasureGroupMappingImpl salesRaggedMeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(unitSalesMeasure, storeCostMeasure, storeSalesMeasure, salesCountMeasure,
            customerCountMeasure))
        .build();

    private final static MeasureGroupMappingImpl sales2MeasureGroup = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(salesCount1Measure, unitSales1Measure, storeSales1Measure, storeCost1Measure,
            customerCount1Measure))
        .build();

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
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Promotions").withDimension(promotionsDimension).withForeignKey("promotion_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Customers").withDimension(customersDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Education Level").withDimension(educationLevelDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Gender").withDimension(genderDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Marital Status").withDimension(maritalStatusDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Yearly Income").withDimension(yearlyIncomeDimension).withForeignKey("customer_id").build()
        ))
        .withCalculatedMembers(List.of(profitCalculatedMember, profitLastPeriodCalculatedMember,
            profitGrowthCalculatedMember))
        .build();

    private final static PhysicalCubeMappingImpl warehouseCube = PhysicalCubeMappingImpl.builder()
        .withName("Warehouse")
        .withQuery(inventoryFact1997Table)
        .withMeasureGroups(List.of(storeMeasureGroup))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store").withDimension(storeDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Size in SQFT").withDimension(storeSizeInSQFTDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Type").withDimension(storeTypeDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Time").withDimension(timeDimension).withForeignKey("time_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Product").withDimension(productDimension).withForeignKey("product_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Warehouse").withDimension(warehouseDimension).withForeignKey("warehouse_id").build()
        ))
        .withMeasureGroups(List.of(warehouseMeasureGroup))
        .withCalculatedMembers(List.of(averageWarehouseSaleCalculatedMember))
        .withNamedSets(List.of(
            topSellersNamedSet
        ))
        .build();

    private final static PhysicalCubeMappingImpl storeCube = PhysicalCubeMappingImpl.builder()
        .withName("Store")
        .withQuery(storeTable)
        .withMeasureGroups(List.of(storeMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Type").withDimension(storeType1Dimension).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store").withDimension(storeDimension).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Has coffee bar").withDimension(hasCoffeeBarDimension).build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    private final static PhysicalCubeMappingImpl hrCube = PhysicalCubeMappingImpl.builder()
        .withName("HR")
        .withQuery(salaryTable)
        .withMeasureGroups(List.of(hrMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Time").withDimension(time1Dimension).withForeignKey("pay_date").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store").withDimension(store1Dimension).withForeignKey("employee_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Pay Type").withDimension(payTypeDimension).withForeignKey("employee_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Type").withDimension(storeType2Dimension).withForeignKey("employee_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Position").withDimension(positionDimension).withForeignKey("employee_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Department").withDimension(departmentDimension).withForeignKey("department_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Employees").withDimension(employeesDimension).withForeignKey("employee_id").build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(employeeSalaryCalculatedMember, avgSalaryCalculatedMember))
        .build();

    private final static PhysicalCubeMappingImpl salesRaggedCube = PhysicalCubeMappingImpl.builder()
        .withName("Sales Ragged")
        .withQuery(salesFact19971Table)
        .withMeasureGroups(List.of(salesRaggedMeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store").withDimension(store2Dimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Geography").withDimension(geographyDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Size in SQFT").withDimension(storeSizeInSQFTDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Store Type").withDimension(storeTypeDimension).withForeignKey("store_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Time").withDimension(timeDimension).withForeignKey("time_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Product").withDimension(productDimension).withForeignKey("product_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Promotion Media").withDimension(promotionMedia1Dimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Promotions").withDimension(promotions1Dimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Customers").withDimension(customersDimension).withForeignKey("department_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Education Level").withDimension(educationLevelDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Gender").withDimension(genderDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Marital Status").withDimension(maritalStatusDimension).withForeignKey("customer_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Yearly Income").withDimension(yearlyIncome1Dimension).withForeignKey("customer_id").build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .build();

    private final static PhysicalCubeMappingImpl sales2Cube = PhysicalCubeMappingImpl.builder()
        .withName("Sales 2")
        .withQuery(salesFact19972Table)
        .withMeasureGroups(List.of(sales2MeasureGroup))
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Time").withDimension(time1Dimension).withForeignKey("time_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Product").withDimension(productDimension).withForeignKey("product_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Gender").withDimension(genderDimension).withForeignKey("customer_id").build()
        ))
        .withDocumentation(new DocumentationMappingImpl(""))
        .withCalculatedMembers(List.of(profitCalculatedMember, profitLastPeriod1CalculatedMember))
        .build();

    private final static VirtualCubeMappingImpl warehouseAndSalesVirtualCube = VirtualCubeMappingImpl.builder()
        .withName("Warehouse and Sales")
        .withDefaultMeasure(storeSalesMeasure)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder()
                .withDimension(customersDimension)
                .withOverrideDimensionName("Customers")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(educationLevelDimension)
                .withOverrideDimensionName("Education Level")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(genderDimension)
                .withOverrideDimensionName("Gender")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(maritalStatusDimension)
                .withOverrideDimensionName("Marital Status")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(productDimension)
                .withOverrideDimensionName("Product")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(promotionMediaDimension)
                .withOverrideDimensionName("Promotion Media")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(promotionsDimension)
                .withOverrideDimensionName("Promotions")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(storeDimension)
                .withOverrideDimensionName("Store")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(timeDimension)
                .withOverrideDimensionName("Time")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(yearlyIncomeDimension)
                .withOverrideDimensionName("Yearly Income")
                .build(),
            DimensionConnectorMappingImpl.builder()
                .withDimension(warehouseDimension)
                .withOverrideDimensionName("Warehouse")
                .build()
        ))
        .withMeasureGroups(List.of(
            vSalesMeasureGroup,
            vWarehouseMeasureGroup
        ))
        .withCalculatedMembers(List.of(
            profitCalculatedMember,
            profitGrowthCalculatedMember,
            averageWarehouseSaleCalculatedMember,
            profitPerUnitShippedCalculatedMember
        ))
        .build();

    private final static AccessRoleMappingImpl californiaManagerRole = AccessRoleMappingImpl.builder()
        .withName("California manager")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess("none")
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(salesCube)
                        .withAccess("all")
                        .withHierarchyGrants(List.of(
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(storeHierarchy)
                                .withAccess("custom")
                                .withTopLevel(storeCountryLevel)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA]")
                                        .withAccess("all")
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Store].[USA].[CA].[Los Angeles]")
                                        .withAccess("none")
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(customersHierarchy)
                                .withAccess("custom")
                                .withTopLevel(stateProvinceLevel)
                                .withBottomLevel(cityLevel)
                                .withMemberGrants(List.of(
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA]")
                                        .withAccess("all")
                                        .build(),
                                    AccessMemberGrantMappingImpl.builder()
                                        .withMember("[Customers].[USA].[CA].[Los Angeles]")
                                        .withAccess("none")
                                        .build()
                                ))
                                .build(),
                            AccessHierarchyGrantMappingImpl.builder()
                                .withHierarchy(genderHierarchy)
                                .withAccess("none")
                                .build()
                        ))
                        .build()
                ))
                .build()
        ))
        .build();

    private final static AccessRoleMappingImpl noHRCubeRole = AccessRoleMappingImpl.builder()
        .withName("No HR Cube")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess("all")
                .withCubeGrant(List.of(
                    AccessCubeGrantMappingImpl.builder()
                        .withCube(hrCube)
                        .withAccess("none")
                        .build()
                ))
                .build()
        ))
        .build();

    private final static AccessRoleMappingImpl administratorRole = AccessRoleMappingImpl.builder()
        .withName("Administrator")
        .withAccessSchemaGrants(List.of(
            AccessSchemaGrantMappingImpl.builder()
                .withAccess("all")
                .build()
        ))
        .build();

    private final static SchemaMappingImpl schema = SchemaMappingImpl.builder()
        .withName(name)
        .withCubes(List.of(salesCube, warehouseCube, storeCube, hrCube, salesRaggedCube, warehouseAndSalesVirtualCube))
        .withAccessRoles(List.of(
            californiaManagerRole,
            noHRCubeRole,
            administratorRole
        ))
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
