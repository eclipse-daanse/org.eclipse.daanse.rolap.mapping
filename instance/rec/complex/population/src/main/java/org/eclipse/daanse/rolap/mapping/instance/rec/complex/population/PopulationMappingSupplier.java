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
package org.eclipse.daanse.rolap.mapping.instance.rec.complex.population;

import java.util.List;

import org.eclipse.daanse.rdb.structure.pojo.ColumnImpl;
import org.eclipse.daanse.rdb.structure.pojo.DatabaseSchemaImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl.Builder;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "4")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class PopulationMappingSupplier implements CatalogMappingSupplier {

    private static final String STATE = "state";
    private static final String GENDER = "Gender";
    private static final String GENDER_ID = "gender_id";
    private static final String AGE_GROUP = "AgeGroup";
    private static final String GEOGRAPHICAL = "Geographical";

    private static final String POPULATION = "Population";

    public static final ColumnImpl YEAR_COLUMN_IN_POPULATION = ColumnImpl.builder().withName("year").withType("INTEGER").build();
    public static final ColumnImpl STATE_ID_COLUMN_IN_POPULATION = ColumnImpl.builder().withName("state_id").withType("INTEGER").build();
    public static final ColumnImpl GENDER_ID_COLUMN_IN_POPULATION = ColumnImpl.builder().withName(GENDER_ID).withType("INTEGER").build();
    public static final ColumnImpl AGE_COLUMN_IN_POPULATION = ColumnImpl.builder().withName("age").withType("INTEGER").build();
    public static final PhysicalTableImpl POPULATION_TABLE = ((Builder) PhysicalTableImpl.builder().withName("population")).build();

    public static final ColumnImpl YEAR_IN_YEAR = ColumnImpl.builder().withName("year").withType("INTEGER").build();
    public static final ColumnImpl ORDINAL_IN_YEAR = ColumnImpl.builder().withName("ordinal").withType("INTEGER").build();
    public static final PhysicalTableImpl YEAR_TABLE = ((Builder) PhysicalTableImpl.builder().withName("year")
            .withColumns(List.of(YEAR_IN_YEAR, ORDINAL_IN_YEAR))).build();

    public static final ColumnImpl CONTINENT_ID_COLUMN_IN_COUNTRY = ColumnImpl.builder().withName("continent_id").withType("INTEGER").build();
    public static final ColumnImpl ID_COLUMN_IN_COUNTRY = ColumnImpl.builder().withName("id").withType("INTEGER").build();
    public static final ColumnImpl NAME_COLUMN_IN_COUNTRY = ColumnImpl.builder().withName("name").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl COUNTRY_TABLE = ((Builder) PhysicalTableImpl.builder().withName("country")
            .withColumns(List.of(ID_COLUMN_IN_COUNTRY, NAME_COLUMN_IN_COUNTRY))).build();

    public static final ColumnImpl ID_COLUMN_IN_CONTENT = ColumnImpl.builder().withName("id").withType("INTEGER").build();
    public static final ColumnImpl NAME_COLUMN_IN_CONTENT = ColumnImpl.builder().withName("name").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl CONTENT_TABLE = ((Builder) PhysicalTableImpl.builder().withName("continent")
            .withColumns(List.of(ID_COLUMN_IN_CONTENT, NAME_COLUMN_IN_CONTENT))).build();

    public static final ColumnImpl ID_COLUMN_IN_STATE = ColumnImpl.builder().withName("id").withType("INTEGER").build();
    public static final ColumnImpl CONTRY_ID_COLUMN_IN_STATE = ColumnImpl.builder().withName("contry_id").withType("INTEGER").build();
    public static final ColumnImpl NAME_COLUMN_IN_STATE = ColumnImpl.builder().withName("name").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl STATE_TABLE = ((Builder) PhysicalTableImpl.builder().withName(STATE)
            .withColumns(List.of(ID_COLUMN_IN_STATE, NAME_COLUMN_IN_STATE))).build();

    public static final ColumnImpl GENDER_ID_COLUMN_IN_GENDER = ColumnImpl.builder().withName(GENDER_ID).withType("INTEGER").build();
    public static final ColumnImpl NAME_COLUMN_IN_GENDER = ColumnImpl.builder().withName("name").withType("VARCHAR").withColumnSize(30).build();
    public static final PhysicalTableImpl GENDER_TABLE = ((Builder) PhysicalTableImpl.builder().withName("gender")
            .withColumns(List.of(GENDER_ID_COLUMN_IN_GENDER, NAME_COLUMN_IN_GENDER))).build();

    public static final ColumnImpl AGE_IN_AGE_GROUPS = ColumnImpl.builder().withName("age").withType("INTEGER").build();
    public static final ColumnImpl H1_IN_AGE_GROUPS = ColumnImpl.builder().withName("H1").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl H1_ORDER_IN_AGE_GROUPS = ColumnImpl.builder().withName("H1_Order").withType("INTEGER").build();
    public static final ColumnImpl H2_IN_AGE_GROUPS = ColumnImpl.builder().withName("H2").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl H2_ORDER_IN_AGE_GROUPS = ColumnImpl.builder().withName("H2_Order").withType("INTEGER").build();
    public static final ColumnImpl H9_IN_AGE_GROUPS = ColumnImpl.builder().withName("H9").withType("VARCHAR").withColumnSize(30).build();
    public static final ColumnImpl H9_ORDER_IN_AGE_GROUPS = ColumnImpl.builder().withName("H9_Order").withType("INTEGER").build();
    public static final PhysicalTableImpl AGE_GROUPS_TABLE = ((Builder) PhysicalTableImpl.builder().withName("ageGroups")
            .withColumns(List.of(AGE_IN_AGE_GROUPS, H1_IN_AGE_GROUPS, H1_ORDER_IN_AGE_GROUPS, H2_IN_AGE_GROUPS, H2_ORDER_IN_AGE_GROUPS,
                    H9_IN_AGE_GROUPS, H9_ORDER_IN_AGE_GROUPS))).build();

    private static final TableQueryMappingImpl TABLE_FACT =
        TableQueryMappingImpl.builder().withTable(POPULATION_TABLE).build();
    private static final TableQueryMappingImpl TABLE1 = TableQueryMappingImpl.builder().withTable(YEAR_TABLE).build();
    private static final TableQueryMappingImpl TABLE22 = TableQueryMappingImpl.builder().withTable(COUNTRY_TABLE).build();
    private static final TableQueryMappingImpl TABLE23 = TableQueryMappingImpl.builder().withTable(CONTENT_TABLE).build();
    private static final TableQueryMappingImpl TABLE21 = TableQueryMappingImpl.builder().withTable(STATE_TABLE).build();
    private static final TableQueryMappingImpl TABLE3 = TableQueryMappingImpl.builder().withTable(GENDER_TABLE).build();
    private static final TableQueryMappingImpl TABLE4 = TableQueryMappingImpl.builder().withTable(AGE_GROUPS_TABLE).build();

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    private static final JoinQueryMappingImpl JOIN21 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(CONTINENT_ID_COLUMN_IN_COUNTRY)
            .withQuery(TABLE22)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_CONTENT)
            .withQuery(TABLE23)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(CONTRY_ID_COLUMN_IN_STATE)
            .withQuery(TABLE21)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_COUNTRY)
            .withQuery(JOIN21)
            .build())
        .build();

    private static final LevelMappingImpl LEVEL1 = LevelMappingImpl
        .builder()
        .withName("Year")
        .withColumn(YEAR_IN_YEAR)
        .withOrdinalColumn(ORDINAL_IN_YEAR)
        .withDescription("Year")
        .build();

    private static final LevelMappingImpl LEVEL21 = LevelMappingImpl
        .builder()
        .withName("Continent")
        .withColumn(ID_COLUMN_IN_CONTENT)
        .withNameColumn(NAME_COLUMN_IN_CONTENT)
        .withType(DataType.INTEGER)
        .withTable(CONTENT_TABLE)
        .withDescription("Continent")
        .build();

    private static final LevelMappingImpl LEVEL22 = LevelMappingImpl
        .builder()
        .withName("Country")
        .withColumn(ID_COLUMN_IN_COUNTRY)
        .withNameColumn(NAME_COLUMN_IN_COUNTRY)
        .withType(DataType.INTEGER)
        .withTable(COUNTRY_TABLE)
        .withDescription("Country")
        .build();

    private static final LevelMappingImpl LEVEL23 = LevelMappingImpl
        .builder()
        .withName("State")
        .withColumn(ID_COLUMN_IN_STATE)
        .withNameColumn(NAME_COLUMN_IN_STATE)
        .withType(DataType.INTEGER)
        .withTable(STATE_TABLE)
        .withDescription("State")
        .build();

    private static final LevelMappingImpl LEVEL3 = LevelMappingImpl
        .builder()
        .withName(GENDER)
        .withColumn(GENDER_ID_COLUMN_IN_GENDER)
        .withNameColumn(NAME_COLUMN_IN_GENDER)
        .withType(DataType.INTEGER)
        .withDescription(GENDER)
        .build();

    private static final LevelMappingImpl LEVEL41 = LevelMappingImpl
        .builder()
        .withName("Age")
        .withColumn(AGE_IN_AGE_GROUPS)
        .withDescription("Age")
        .build();

    private static final LevelMappingImpl LEVEL42 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn(H1_IN_AGE_GROUPS)
        .withOrdinalColumn(H1_ORDER_IN_AGE_GROUPS)
        .withDescription("Age Group H1")
        .build();

    private static final LevelMappingImpl LEVEL43 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn(H2_IN_AGE_GROUPS)
        .withOrdinalColumn(H2_ORDER_IN_AGE_GROUPS)
        .withDescription("Age Group H2")
        .build();

    private static final LevelMappingImpl LEVEL44 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn(H9_IN_AGE_GROUPS)
        .withOrdinalColumn(H9_ORDER_IN_AGE_GROUPS)
        .withDescription("Age Group H9")
        .build();

    private static final HierarchyMappingImpl HIERARCHY1 = HierarchyMappingImpl
        .builder()
        .withHasAll(false)
        .withName("Year")
        .withPrimaryKey(YEAR_IN_YEAR)
        .withDescription("Year")
        .withQuery(TABLE1)
        .withLevels(List.of(LEVEL1))
        .build();

    private static final HierarchyMappingImpl HIERARCHY2 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName(GEOGRAPHICAL)
        .withPrimaryKey(ID_COLUMN_IN_STATE)
        .withPrimaryKeyTable(STATE_TABLE)
        .withDescription(GEOGRAPHICAL)
        .withQuery(JOIN1)
        .withLevels(List.of(LEVEL21, LEVEL22, LEVEL23))
        .build();

    private static final HierarchyMappingImpl HIERARCHY3 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Gender (m/f/d)")
        .withPrimaryKey(GENDER_ID_COLUMN_IN_GENDER)
        .withDescription(GENDER)
        .withQuery(TABLE3)
        .withLevels(List.of(LEVEL3))
        .build();

    private static final HierarchyMappingImpl HIERARCHY41 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age (single vintages)")
        .withPrimaryKey(AGE_IN_AGE_GROUPS)
        .withDescription("Age (single vintages)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY42 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (Standard)")
        .withPrimaryKey(AGE_IN_AGE_GROUPS)
        .withDescription("Age group (Standard)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL42, LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY43 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (children)")
        .withPrimaryKey(AGE_IN_AGE_GROUPS)
        .withDescription("Age group (children)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL43, LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY44 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (10-year groups)")
        .withPrimaryKey(AGE_IN_AGE_GROUPS)
        .withDescription("Age group (10-year groups)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL44, LEVEL41))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION1 = StandardDimensionMappingImpl
        .builder()
        .withName("Year")
        .withHierarchies(List.of(HIERARCHY1))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION2 = StandardDimensionMappingImpl
        .builder()
        .withName(GEOGRAPHICAL)
        .withHierarchies(List.of(HIERARCHY2))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION3 = StandardDimensionMappingImpl
        .builder()
        .withName(GENDER)
        .withHierarchies(List.of(HIERARCHY3))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION4 = StandardDimensionMappingImpl
        .builder()
        .withName("Age")
        .withHierarchies(List.of(HIERARCHY41, HIERARCHY42, HIERARCHY43, HIERARCHY44))
        .build();

    private static final PhysicalCubeMappingImpl CUBE = PhysicalCubeMappingImpl
        .builder()
        .withName(POPULATION)
        .withDescription("Population Cube")
        .withQuery(TABLE_FACT)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Year").withDimension(DIMENSION1).withForeignKey(YEAR_COLUMN_IN_POPULATION).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GEOGRAPHICAL).withDimension(DIMENSION2).withForeignKey(STATE_ID_COLUMN_IN_POPULATION).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GENDER).withDimension(DIMENSION3).withForeignKey(GENDER_ID_COLUMN_IN_POPULATION).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Age").withDimension(DIMENSION4).withForeignKey(AGE_COLUMN_IN_POPULATION).build()))
        .build();

    private static final CatalogMappingImpl
        CATALOG = CatalogMappingImpl.builder()
        .withName(POPULATION)
        .withCubes(List.of(CUBE))
        .withDbSchemas(List.of(DatabaseSchemaImpl.builder()
                .withName(POPULATION)
                .withTables(List.of(POPULATION_TABLE, YEAR_TABLE, COUNTRY_TABLE,
                        CONTENT_TABLE, STATE_TABLE, GENDER_TABLE, AGE_GROUPS_TABLE))
                .build()))
        .build();

    @Override
    public CatalogMapping get() {
        return CATALOG;
    }

}
