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
package org.eclipse.daanse.rolap.mapping.instance.rec.complex.expressivenames;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl.Builder;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "4")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class ExpressivenamesMappingSupplier implements CatalogMappingSupplier {

    private static final String D3H3L3_TABLE_NAME = "D3H3L3Table";

    private static final String D3H2L2_TABLE_NAME = "D3H2L2Table";

    private static final String CATALOG_NAME = "ExpressiveNames";

    private static final String CUBE_1_NAME = "Cube1";

    public static final String D_1_H_1_L_1 = "D1H1L1";

    public static final String D_2_H_1_L_1 = "D2H1L1";
    public static final String D_2_H_2_L_2 = "D2H2L2";

    public static final String D_3_H_1_L_1 = "D3H1L1";
    public static final String D_3_H_2_L_1 = "D3H2L1";
    public static final String D_3_H_3_L_1 = "D3H3L1";
    public static final String D_3_H_3_L_2 = "D3H3L2";
    public static final String D_3_H_3_L_3 = "D3H3L3";
    public static final String D_3_H_2_L_2 = "D3H2L2";

    public static final String DIMENSION_1 = "Dimension1";
    public static final String DIMENSION_2 = "Dimension2";
    public static final String DIMENSION_3 = "Dimension3";

    public static final ColumnMappingImpl D1_COLUMN = ColumnMappingImpl.builder().withName("D1").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2_COLUMN = ColumnMappingImpl.builder().withName("D2").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3_COLUMN = ColumnMappingImpl.builder().withName("D3").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl M1_COLUMN = ColumnMappingImpl.builder().withName("M1").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl CUBE_1_TABLE_FACT = ((Builder) PhysicalTableMappingImpl.builder().withName("Cube1Fact")
            .withColumns(List.of(D1_COLUMN, D2_COLUMN, D3_COLUMN, M1_COLUMN))).build();

    public static final ColumnMappingImpl D1H1L1_COLUMN = ColumnMappingImpl.builder().withName(D_1_H_1_L_1).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D1H1L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D1H1L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D1H1L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D1H1L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D1H1L1_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D1H1L1Table")
            .withColumns(List.of(D1H1L1_COLUMN, D1H1L1_NAME_COLUMN, D1H1L1_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D2H1L1_COLUMN = ColumnMappingImpl.builder().withName(D_2_H_1_L_1).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H1L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D2H1L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H1L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D2H1L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D2H1L1_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D2H1L1Table")
            .withColumns(List.of(D2H1L1_COLUMN, D2H1L1_NAME_COLUMN, D2H1L1_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D2H2L2_COLUMN = ColumnMappingImpl.builder().withName(D_2_H_2_L_2).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H2L1_COLUMN = ColumnMappingImpl.builder().withName("D2H2L1").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H2L2_NAME_COLUMN = ColumnMappingImpl.builder().withName("D2H2L2_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H2L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D2H2L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H2L2_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D2H2L2_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D2H2L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D2H2L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D2H2L2_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D2H2L2Table")
            .withColumns(List.of(D2H2L2_COLUMN, D2H2L2_NAME_COLUMN, D2H2L1_NAME_COLUMN, D2H2L2_ORDINAL_COLUMN, D2H2L1_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D3H1L1_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_1_L_1).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H1L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H1L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H1L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H1L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H1L1_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D3H1L1Table")
            .withColumns(List.of(D3H1L1_COLUMN, D3H1L1_NAME_COLUMN, D3H1L1_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D3H2L2_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_2_L_2).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L2_ID_COLUMN = ColumnMappingImpl.builder().withName("D3H2L2_id").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L1_ID_COLUMN = ColumnMappingImpl.builder().withName("D3H2L1_id").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L2_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H2L2_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L2_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H2L2_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H2L2_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(D3H2L2_TABLE_NAME)
            .withColumns(List.of(D3H2L2_COLUMN, D3H2L2_ID_COLUMN, D3H2L1_ID_COLUMN, D3H2L2_NAME_COLUMN, D3H2L2_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D3H2L1_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_2_L_1).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H2L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H2L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H2L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H2L1_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D3H2L1Table")
            .withColumns(List.of(D3H2L1_COLUMN, D3H2L1_NAME_COLUMN, D3H2L1_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D3H3L3_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_3_L_3).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L2_ID_COLUMN = ColumnMappingImpl.builder().withName("D3H3L2_id").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L3_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H3L3_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L3_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H3L3_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H3L3_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(D3H3L3_TABLE_NAME)
            .withColumns(List.of(D3H3L3_COLUMN, D3H3L2_ID_COLUMN, D3H3L3_NAME_COLUMN, D3H3L3_ORDINAL_COLUMN))).build();

    public static final ColumnMappingImpl D3H3L2_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_3_L_2).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L1_ID_COLUMN = ColumnMappingImpl.builder().withName("D3H3L1_id").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L2_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H3L2_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L2_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H3L2_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H3L2_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D3H3L2Table")
            .withColumns(List.of(D3H3L2_COLUMN, D3H3L1_ID_COLUMN, D3H3L2_NAME_COLUMN, D3H3L2_ORDINAL_COLUMN))).build();

    //D3H3L1,D3H3L1_NAME,D3H3L1_Ordinal
    public static final ColumnMappingImpl D3H3L1_COLUMN = ColumnMappingImpl.builder().withName(D_3_H_3_L_1).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L1_NAME_COLUMN = ColumnMappingImpl.builder().withName("D3H3L1_NAME").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl D3H3L1_ORDINAL_COLUMN = ColumnMappingImpl.builder().withName("D3H3L1_Ordinal").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl D3H3L1_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("D3H3L1Table")
            .withColumns(List.of(D3H3L1_COLUMN, D3H3L1_NAME_COLUMN, D3H3L1_ORDINAL_COLUMN))).build();

    private static final TableQueryMappingImpl CUBE_1_TABLE_FACT_QUERY = TableQueryMappingImpl.builder().withTable(
        CUBE_1_TABLE_FACT).build();
    private static final TableQueryMappingImpl TABLE1 = TableQueryMappingImpl.builder().withTable(D1H1L1_TABLE).build();
    private static final TableQueryMappingImpl TABLE2 = TableQueryMappingImpl.builder().withTable(D2H1L1_TABLE).build();
    private static final TableQueryMappingImpl TABLE3 = TableQueryMappingImpl.builder().withTable(D2H2L2_TABLE).build();
    private static final TableQueryMappingImpl TABLE4 = TableQueryMappingImpl.builder().withTable(D3H1L1_TABLE).build();
    private static final TableQueryMappingImpl TABLE5_1 =
        TableQueryMappingImpl.builder().withTable(D3H2L2_TABLE).build();
    private static final TableQueryMappingImpl TABLE5_2 =
        TableQueryMappingImpl.builder().withTable(D3H2L1_TABLE).build();
    private static final TableQueryMappingImpl TABLE6 = TableQueryMappingImpl.builder().withTable(D3H3L3_TABLE).build();
    private static final TableQueryMappingImpl TABLE7 = TableQueryMappingImpl.builder().withTable(D3H3L2_TABLE).build();
    private static final TableQueryMappingImpl TABLE8 = TableQueryMappingImpl.builder().withTable(D3H3L1_TABLE).build();

    private static final JoinQueryMappingImpl JOIN1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H3L2_ID_COLUMN)
            .withQuery(TABLE7)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H3L1_COLUMN)
            .withQuery(TABLE8)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H3L2_ID_COLUMN)
            .withQuery(TABLE6)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H3L2_COLUMN)
            .withQuery(JOIN1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN0 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H2L1_ID_COLUMN)
            .withQuery(TABLE5_1)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(D3H2L1_COLUMN)
            .withQuery(TABLE5_2)
            .build())
        .build();

    private static final LevelMappingImpl LEVEL1 = LevelMappingImpl
        .builder()
        .withName(D_1_H_1_L_1)
        .withColumn(D1H1L1_COLUMN)
        .withOrdinalColumn(D1H1L1_ORDINAL_COLUMN)
        .withNameColumn(D1H1L1_NAME_COLUMN)
        .withDescription("Level 1 Dimension 1 Hierarchy1")
        .build();

    private static final LevelMappingImpl LEVEL21 = LevelMappingImpl
        .builder()
        .withName(D_2_H_1_L_1)
        .withColumn(D2H1L1_COLUMN)
        .withNameColumn(D2H1L1_NAME_COLUMN)
        .withOrdinalColumn(D2H1L1_ORDINAL_COLUMN)
        .withDescription("Level 1 Hierarchy 1 Dimension 2")
        .withType(DataType.INTEGER)
        .withTable(D2H1L1_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL221 = LevelMappingImpl
        .builder()
        .withName("D2H2L1")
        .withColumn(D2H2L1_COLUMN)
        .withNameColumn(D2H2L1_NAME_COLUMN)
        .withOrdinalColumn(D2H2L1_ORDINAL_COLUMN)
        .withDescription("Level 2 Hierarchy 2 Dimension 2")
        .withType(DataType.INTEGER)
        .build();

    private static final LevelMappingImpl LEVEL222 = LevelMappingImpl
        .builder()
        .withName(D_2_H_2_L_2)
        .withColumn(D2H2L2_COLUMN)
        .withNameColumn(D2H2L2_NAME_COLUMN)
        .withOrdinalColumn(D2H2L2_ORDINAL_COLUMN)
        .withDescription("Level 2 Dimension 3")
        .withType(DataType.INTEGER)
        .build();

    private static final LevelMappingImpl LEVEL31 = LevelMappingImpl
        .builder()
        .withName(D_3_H_1_L_1)
        .withColumn(D3H1L1_COLUMN)
        .withNameColumn(D3H1L1_NAME_COLUMN)
        .withOrdinalColumn(D3H1L1_ORDINAL_COLUMN)
        .withDescription("Level 1 Hierarchy1 Dimension 3")
        .build();

    private static final LevelMappingImpl LEVEL321 = LevelMappingImpl
        .builder()
        .withName(D_3_H_2_L_1)
        .withColumn(D3H2L1_COLUMN)
        .withNameColumn(D3H2L1_NAME_COLUMN)
        .withOrdinalColumn(D3H2L1_ORDINAL_COLUMN)
        .withType(DataType.INTEGER)
        .withTable(D3H2L1_TABLE)
        .withDescription("Level 1 Hierarchy2 Dimension 3")
        .build();

    private static final LevelMappingImpl LEVEL322 = LevelMappingImpl
        .builder()
        .withName(D_3_H_2_L_2)
        .withColumn(D3H2L2_COLUMN)
        .withNameColumn(D3H2L2_NAME_COLUMN)
        .withOrdinalColumn(D3H2L2_ORDINAL_COLUMN)
        .withType(DataType.INTEGER)
        .withTable(D3H2L2_TABLE)
        .withDescription("Level 2 Hierarchy2 Dimension 3")
        .build();

    private static final LevelMappingImpl LEVEL331 = LevelMappingImpl
        .builder()
        .withName(D_3_H_3_L_1)
        .withColumn(D3H3L1_COLUMN)
        .withNameColumn(D3H3L1_NAME_COLUMN)
        .withOrdinalColumn(D3H3L1_ORDINAL_COLUMN)
        .withType(DataType.INTEGER)
        .withTable(D3H3L1_TABLE)
        .withDescription("Level 1 Hierarchy3 Dimension 3")
        .build();

    private static final LevelMappingImpl LEVEL332 = LevelMappingImpl
        .builder()
        .withName(D_3_H_3_L_2)
        .withColumn(D3H3L2_COLUMN)
        .withNameColumn(D3H3L2_NAME_COLUMN)
        .withOrdinalColumn(D3H3L2_ORDINAL_COLUMN)
        .withType(DataType.INTEGER)
        .withTable(D3H3L2_TABLE)
        .withDescription("Level 2 Hierarchy3 Dimension 3")
        .build();

    private static final LevelMappingImpl LEVEL333 = LevelMappingImpl
        .builder()
        .withName(D_3_H_3_L_3)
        .withColumn(D3H3L3_COLUMN)
        .withNameColumn(D3H3L3_NAME_COLUMN)
        .withOrdinalColumn(D3H3L3_ORDINAL_COLUMN)
        .withType(DataType.INTEGER)
        .withTable(D3H3L3_TABLE)
        .withDescription("Level 3 Hierarchy3 Dimension 3")
        .build();

    private static final HierarchyMappingImpl HIERARCHY1 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D1H1")
        .withPrimaryKey(D1H1L1_COLUMN)
        .withDescription("Hierarchy 1 Dimension 1")
        .withQuery(TABLE1)
        .withLevels(List.of(LEVEL1))
        .build();

    private static final HierarchyMappingImpl HIERARCHY21 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D2H1")
        .withPrimaryKey(D2H1L1_COLUMN)
        .withDescription("Hierarchy 1 Dimension 2")
        .withQuery(TABLE2)
        .withLevels(List.of(LEVEL21))
        .build();

    private static final HierarchyMappingImpl HIERARCHY22 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D2H2")
        .withDescription("Hierarchy 2 Dimension 2")
        .withPrimaryKey(D2H2L2_COLUMN)
        .withDescription("Hierarchy 2 Dimension 2")
        .withQuery(TABLE3)
        .withLevels(List.of(LEVEL221, LEVEL222))
        .build();

    private static final HierarchyMappingImpl HIERARCHY31 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D3H1")
        .withPrimaryKey(D3H1L1_COLUMN)
        .withDescription("Hierarchy 1 Dimension 3")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL31))
        .build();

    private static final HierarchyMappingImpl HIERARCHY32 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D3H2")
        .withPrimaryKey(D3H2L2_COLUMN)
        .withPrimaryKeyTable(D3H2L2_TABLE)
        .withDescription("Hierarchy 2 Dimension 3")
        .withQuery(JOIN0)
        .withLevels(List.of(LEVEL321, LEVEL322))
        .build();

    private static final HierarchyMappingImpl HIERARCHY33 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("D3H3")
        .withPrimaryKey(D3H3L3_COLUMN)
        .withPrimaryKeyTable(D3H3L3_TABLE)
        .withDescription("Hierarchy 1 Dimension 3")
        .withQuery(JOIN)
        .withLevels(List.of(LEVEL331, LEVEL332, LEVEL333))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_SCHEMA1 = StandardDimensionMappingImpl
        .builder()
        .withName(DIMENSION_1)
        //.foreignKey(D_1_H_1_L_1)
        .withDescription("Hierarchy 1 Dimension 1")
        .withHierarchies(List.of(HIERARCHY1))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_SCHEMA2 = StandardDimensionMappingImpl
        .builder()
        .withName(DIMENSION_2)
        //.foreignKey("D2")
        .withHierarchies(List.of(HIERARCHY21, HIERARCHY22))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION_SCHEMA3 = StandardDimensionMappingImpl
        .builder()
        .withName(DIMENSION_3)
        //.foreignKey("D3")
        .withHierarchies(List.of(HIERARCHY31, HIERARCHY32, HIERARCHY33))
        .build();

    private static final DimensionConnectorMappingImpl DIMENSION_USAGE_1 = DimensionConnectorMappingImpl
        .builder()
        .withOverrideDimensionName(DIMENSION_1)
        .withDimension(DIMENSION_SCHEMA1)
        .withForeignKey(D1_COLUMN)
        .build();

    private static final DimensionConnectorMappingImpl DIMENSION_USAGE_2 = DimensionConnectorMappingImpl
        .builder()
        .withOverrideDimensionName(DIMENSION_2)
        .withDimension(DIMENSION_SCHEMA2)
        .withForeignKey(D2_COLUMN)
        .build();

    private static final DimensionConnectorMappingImpl DIMENSION_USAGE_3 = DimensionConnectorMappingImpl
        .builder()
        .withOverrideDimensionName(DIMENSION_3)
        .withDimension(DIMENSION_SCHEMA3)
        .withForeignKey(D3_COLUMN)
        .build();

    private static final MeasureMappingImpl MEASURE_1_1 = MeasureMappingImpl
        .builder()
        .withName("Measure1")
        .withColumn(M1_COLUMN)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .withFormatString("Standard")
        .build();

    private static final MeasureGroupMappingImpl MEASURE_GROUP = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(MEASURE_1_1))
        .build();

    private static final PhysicalCubeMappingImpl CUBE = PhysicalCubeMappingImpl
        .builder()
        .withName(CUBE_1_NAME)
        .withDescription("Test Cube")
        .withQuery(CUBE_1_TABLE_FACT_QUERY)
        .withDimensionConnectors(List.of(
            DIMENSION_USAGE_1,
            DIMENSION_USAGE_2,
            DIMENSION_USAGE_3))
        .withMeasureGroups(List.of(MEASURE_GROUP))
        .build();

    private static final CatalogMappingImpl
        CATALOG = CatalogMappingImpl.builder()
        .withName(CATALOG_NAME)
        .withCubes(List.of(CUBE))
        .withDbSchemas(List.of(DatabaseSchemaMappingImpl.builder()
                .withName(CATALOG_NAME)
                .withTables(List.of(CUBE_1_TABLE_FACT, D1H1L1_TABLE, D2H1L1_TABLE,
                        D2H2L2_TABLE, D3H1L1_TABLE, D3H2L2_TABLE, D3H2L1_TABLE,
                        D3H3L3_TABLE, D3H3L2_TABLE, D3H3L1_TABLE))
                .build()))
        .build();

    @Override
    public CatalogMapping get() {
        return CATALOG;
    }

}
