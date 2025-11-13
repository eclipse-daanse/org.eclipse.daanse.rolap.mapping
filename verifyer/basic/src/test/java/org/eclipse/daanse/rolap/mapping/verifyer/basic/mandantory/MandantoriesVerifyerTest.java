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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_COLUMN_NAME;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_COLUMN_NAME_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_FACT_COUNT;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ANNOTATION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ANNOTATION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_NAME_MUST_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_USAGE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_USAGE_CUBE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_WITH_NAME_MUST_CONTAIN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ATTRIBUTE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ELEMENT_FORMATTER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.FACT_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY_MUST_BE_SET_FOR_CALCULATED_MEMBER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HINT;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HINT_TYPE_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.JOIN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.JOIN_LEFT_KEY_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.JOIN_RIGHT_KEY_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET_FORMULA_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NOT_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER_TYPE_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ROLE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SQL_DIALECT_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_MEASURES;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.WRITEBACK_TABLE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.description.DescriptionVerifyerTest.setupDummyListAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.model.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.model.AggregationTable;
import org.eclipse.daanse.rolap.mapping.model.Annotation;
import org.eclipse.daanse.rolap.mapping.model.BaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.CubeConnector;
import org.eclipse.daanse.rolap.mapping.model.Dimension;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.Documentation;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.Table;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.model.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.WritebackTable;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class MandantoriesVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory.MandantoriesVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    Catalog schema = mock(Catalog.class);
    PhysicalCube cube = mock(PhysicalCube.class);
    VirtualCube virtualCube = mock(VirtualCube.class);
    DimensionConnector dimensionConnector = mock(DimensionConnector.class);
    Dimension dimension = mock(Dimension.class);
    CalculatedMemberProperty calculatedMemberProperty = mock(CalculatedMemberProperty.class);
    CalculatedMember calculatedMember = mock(CalculatedMember.class);
    MeasureGroup measureGroup = mock(MeasureGroup.class);
    SumMeasure measure = mock(SumMeasure.class);
    ExplicitHierarchy hierarchy = mock(ExplicitHierarchy.class);
    org.eclipse.daanse.rolap.mapping.model.Level level = mock(org.eclipse.daanse.rolap.mapping.model.Level.class);
    MemberProperty property = mock(MemberProperty.class);
    NamedSet namedSet = mock(NamedSet.class);
    Parameter parameter = mock(Parameter.class);
    DrillThroughAction drillThroughAction = mock(DrillThroughAction.class);
    MemberFormatter elementFormatter = mock(MemberFormatter.class);
    JoinQuery joinQuery = mock(JoinQuery.class);
    TableQuery tableQuery = mock(TableQuery.class);
    JoinedQueryElement left = mock(JoinedQueryElement.class);
    JoinedQueryElement right = mock(JoinedQueryElement.class);
    WritebackTable writebackTable = mock(WritebackTable.class);
    WritebackAttribute writebackAttribute = mock(WritebackAttribute.class);
    WritebackMeasure writebackMeasure = mock(WritebackMeasure.class);
    BaseMeasure drillThroughMeasure = mock(BaseMeasure.class);
    DrillThroughAttribute drillThroughAttribute = mock(DrillThroughAttribute.class);
    Annotation annotation = mock(Annotation.class);
    AccessRole role = mock(AccessRole.class);
    CubeConnector cubeUsage = mock(CubeConnector.class);
    SqlStatement sql = mock(SqlStatement.class);
    TableQueryOptimizationHint hint = mock(TableQueryOptimizationHint.class);
    AggregationTable aggTable = mock(AggregationTable.class);
    AggregationColumnName aggColumnName = mock(AggregationColumnName.class);
    AggregationForeignKey aggForeignKey = mock(AggregationForeignKey.class);
    AggregationMeasure aggMeasure = mock(AggregationMeasure.class);
    AggregationLevel aggLevel = mock(AggregationLevel.class);
    AggregationMeasureFactCount measuresFactCount = mock(AggregationMeasureFactCount.class);
    Table table = mock(Table.class);
    Table levelTable = mock(Table.class);
    Column column = mock(Column.class);
    Column nameColumn = mock(Column.class);
    Column ordinalColumn = mock(Column.class);
    Column parentColumn = mock(Column.class);
    Column captionColumn = mock(Column.class);

//    Level l = new LevelTest(
//        "id",
//        "name",
//        levelTable,
//        column,
//        nameColumn,
//        ordinalColumn,
//        parentColumn,
//        "nullParentValue",
//        InternalDataType.STRING,
//        "approxRowCount",
//        true,
//        LevelType.REGULAR,
//        HideMemberIfType.NEVER,
//        null,
//        "description",
//        captionColumn,
//        List.of(),
//        null,
//        null,
//        null,
//        null,
//        null,
//        null,
//        List.of(property),
//        true,
//        null, false, null);

    @Test
    @Disabled
    void testSchema() {


        when(schema.getParameters()).thenAnswer(setupDummyListAnswer(parameter));
        when(schema.getAccessRoles()).thenAnswer(setupDummyListAnswer(role));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(PARAMETER_NAME_MUST_BE_SET)
            .contains(PARAMETER_TYPE_MUST_BE_SET)
            .contains(ROLE_NAME_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.ERROR);
    }

    @Test
    @Disabled
    void testCubeAndVirtualCubeAndCalculatedMemberAndAction() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube, virtualCube));
        when(cube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(cube.getNamedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(virtualCube.getNamedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(virtualCube.getCubeUsages()).thenAnswer(setupDummyListAnswer(cubeUsage));
        when(cube.getAction()).thenAnswer(setupDummyListAnswer(drillThroughAction));
        when(drillThroughAction.getDrillThroughAttribute()).thenAnswer(setupDummyListAnswer(drillThroughAttribute));
        when(drillThroughAction.getDrillThroughMeasure()).thenAnswer(setupDummyListAnswer(drillThroughMeasure));
        when(cube.getWritebackTable()).thenReturn(writebackTable);
        when(writebackTable.getWritebackAttribute()).thenAnswer(setupDummyListAnswer(writebackAttribute));
        when(writebackTable.getWritebackMeasure()).thenAnswer(setupDummyListAnswer(writebackMeasure));
        when(drillThroughAction.getAnnotations()).thenAnswer(setupDummyListAnswer(annotation));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(28);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(CUBE_NAME_MUST_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, NOT_SET))
            .contains(VIRTUAL_CUBE_NAME_MUST_BE_SET)
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(CALCULATED_MEMBER_NAME_MUST_BE_SET)
            .contains(String.format(HIERARCHY_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET))
            .contains(String.format(FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET))
            .contains(NAMED_SET_NAME_MUST_BE_SET)
            .contains(NAMED_SET_FORMULA_MUST_BE_SET)
            .contains(ACTION_NAME_MUST_BE_SET)
            .contains(WRITEBACK_TABLE_NAME_MUST_BE_SET)
            .contains(WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET)
            .contains(WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET)
            .contains(WRITEBACK_MEASURE_NAME_MUST_BE_SET)
            .contains(WRITEBACK_MEASURE_COLUMN_MUST_BE_SET)
            .contains(DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET)
            .contains(DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET)
            .contains(ANNOTATION_NAME_MUST_BE_SET)
            .contains(CUBE_USAGE_CUBE_NAME_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(MEASURE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER)
            .contains(ACTION)
            .contains(WRITEBACK_TABLE)
            .contains(WRITEBACK_ATTRIBUTE)
            .contains(WRITEBACK_MEASURE)
            .contains(DRILL_THROUGH_ATTRIBUTE)
            .contains(DRILL_THROUGH_MEASURE)
            .contains(ANNOTATION)
            .contains(CUBE_USAGE);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.ERROR);
    }

    @Test
    @Disabled
    void testCubeAndVirtualCubeAndCalculatedMemberAndFormula() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube, virtualCube));
        when(cube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(14);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(CUBE_NAME_MUST_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, NOT_SET))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN, NOT_SET, MEASURE))
            .contains(VIRTUAL_CUBE_NAME_MUST_BE_SET)
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(CALCULATED_MEMBER_NAME_MUST_BE_SET)
            .contains(String.format(HIERARCHY_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET))
            .contains(String.format(FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET));

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(MEASURE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.ERROR);
    }

    @Test
    @Disabled
    void testMeasure() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getName()).thenReturn("cubeName");
        when(measure.getCalculatedMemberProperties()).thenAnswer(setupDummyListAnswer(calculatedMemberProperty));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(MEASURE)
            .contains(CALCULATED_MEMBER_PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.ERROR);
    }

    @Test
    @Disabled
    void testHierarchyWithJoin() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getName()).thenReturn("cubeName");
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(dimensionConnector.getOverrideDimensionName()).thenReturn("DimensionName");
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(level));
        when(hierarchy.getQuery()).thenReturn(joinQuery);
        Table hierarchyTable = mock(Table.class);
        when(hierarchyTable.getName()).thenReturn("hierarchyTable");
        when(joinQuery.getLeft()).thenReturn(left);
        when(joinQuery.getRight()).thenReturn(right);
        when(left.getQuery()).thenReturn(tableQuery);
        when(right.getQuery()).thenReturn(tableQuery);
        when(tableQuery.getTable()).thenReturn(table);
        when(table.getName()).thenReturn("tableName");
        when(levelTable.getName()).thenReturn("table");
        when(tableQuery.getSqlWhereExpression()).thenReturn(sql);
        when(tableQuery.getOptimizationHints()).thenAnswer(setupDummyListAnswer(hint));
        when(tableQuery.getAggregationTables()).thenAnswer(setupDummyListAnswer(aggTable));
        when(level.getMemberFormatter()).thenReturn(elementFormatter);
        when(aggTable.getAggregationIgnoreColumns()).thenAnswer(setupDummyListAnswer(aggColumnName));
        when(aggTable.getAggregationForeignKeys()).thenAnswer(setupDummyListAnswer(aggForeignKey));
        when(aggTable.getAggregationMeasures()).thenAnswer(setupDummyListAnswer(aggMeasure));
        when(aggTable.getAggregationLevels()).thenAnswer(setupDummyListAnswer(aggLevel));
        when(aggTable.getAggregationMeasureFactCounts()).thenAnswer(setupDummyListAnswer(measuresFactCount));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(35);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(String.format(LEVEL_NAME_MUST_BE_SET, NOT_SET))
            .contains(String.format(LEVEL_COLUMN_MUST_BE_SET, NOT_SET))
            .contains(JOIN_LEFT_KEY_MUST_BE_SET)
            .contains(JOIN_RIGHT_KEY_MUST_BE_SET)
            .contains(FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED)
            .contains(SQL_DIALECT_MUST_BE_SET)
            .contains(HINT_TYPE_MUST_BE_SET)
            .contains(AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET)
            .contains(AGG_COLUMN_NAME_COLUMN_MUST_BE_SET)
            .contains(AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET)
            .contains(AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET)
            .contains(AGG_MEASURE_COLUMN_MUST_BE_SET)
            .contains(AGG_MEASURE_NAME_MUST_BE_SET)
            .contains(AGG_LEVEL_NAME_MUST_BE_SET)
            .contains(AGG_LEVEL_COLUMN_MUST_BE_SET)
            .contains(AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(JOIN)
            .contains(ELEMENT_FORMATTER)
            .contains(SchemaWalkerMessages.SQL)
            .contains(HINT)
            .contains(AGG_TABLE)
            .contains(AGG_COLUMN_NAME)
            .contains(AGG_FOREIGN_KEY)
            .contains(AGG_MEASURE)
            .contains(AGG_LEVEL)
            .contains(AGG_MEASURE_FACT_COUNT);

        assertThat(result).extracting(VerificationResult::level)
            .contains(Level.ERROR)
            .contains(Level.WARNING);
    }

    @Test
    @Disabled
    void testHierarchyWithoutJoin() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getName()).thenReturn("cubeName");
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(dimensionConnector.getOverrideDimensionName()).thenReturn("DimensionName");
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(level));
        when(hierarchy.getQuery()).thenReturn(joinQuery);
        when(level.getMemberFormatter()).thenReturn(elementFormatter);
        when(level.getMemberProperties()).thenAnswer(setupDummyListAnswer(property));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(13);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(String.format(LEVEL_NAME_MUST_BE_SET, NOT_SET))
            .contains(FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED)
            .contains(PROPERTY_COLUMN_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(ELEMENT_FORMATTER)
            .contains(PROPERTY)
        ;

        assertThat(result).extracting(VerificationResult::level)
            .contains(Level.ERROR,
                Level.WARNING);
    }

    @Test
    @Disabled
    void testCheckColumn_With_Table() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getName()).thenReturn("cubeName");
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(dimensionConnector.getOverrideDimensionName()).thenReturn("DimensionName");
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
//        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(l));
        when(hierarchy.getQuery()).thenReturn(tableQuery);
        when(table.getName()).thenReturn("tableName");
        when(tableQuery.getTable()).thenReturn(table);
        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(8);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(PROPERTY_COLUMN_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(TABLE)
            .contains(PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .contains(Level.ERROR,
                Level.WARNING);
    }

    @Test
    @Disabled
    void testCheckColumn_With_Join() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getName()).thenReturn("cubeName");
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(dimensionConnector.getOverrideDimensionName()).thenReturn("DimensionName");
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
//        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(l));
        when(hierarchy.getQuery()).thenReturn(joinQuery);
        when(joinQuery.getLeft()).thenReturn(left);
        when(joinQuery.getRight()).thenReturn(right);
        when(left.getQuery()).thenReturn(tableQuery);
        when(right.getQuery()).thenReturn(tableQuery);
        when(table.getName()).thenReturn("tableName");
        when(levelTable.getName()).thenReturn("table");
        when(tableQuery.getTable()).thenReturn(table);
        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(31);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(PROPERTY_COLUMN_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .contains(Level.ERROR, Level.WARNING);
    }



}
