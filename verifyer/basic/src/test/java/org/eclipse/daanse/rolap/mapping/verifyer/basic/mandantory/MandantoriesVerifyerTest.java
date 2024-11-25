package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ACTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ACTION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_COLUMN_NAME;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_COLUMN_NAME_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_LEVEL_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_FACT_COUNT;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ANNOTATION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ANNOTATION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_DIMENSION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_NAME_MUST_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_USAGE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_USAGE_CUBE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_WITH_NAME_MUST_CONTAIN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DIMENSIONS;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DIMENSION_MUST_BE_SET_FOR_CALCULATED_MEMBER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ATTRIBUTE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ELEMENT_FORMATTER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FACT_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FORMULA;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FORMULA_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HINT;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HINT_TYPE_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.JOIN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.JOIN_LEFT_KEY_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.JOIN_RIGHT_KEY_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE_AGGREGATOR_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NAMED_SET_FORMULA_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NAMED_SET_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NOT_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARAMETER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARAMETER_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARAMETER_TYPE_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PROPERTY_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ROLE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SQL_DIALECT_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.USER_DEFINED_FUNCTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.USER_DEFINED_FUNCTION_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_MEASURES;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE_COLUMN_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_MEASURE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_TABLE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.WRITEBACK_TABLE_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description.DescriptionVerifyerTest.setupDummyListAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggColumnName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggForeignKey;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggMeasureFactCount;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggName;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSQL;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSqlSelectQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTableQueryOptimisationHint;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoinQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingJoinedQueryElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingParameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTableQuery;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingVirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackAttribute;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingWritebackTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class MandantoriesVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.mandantory" +
        ".MandantoriesVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    MappingSchema schema = mock(MappingSchema.class);
    MappingCube cube = mock(MappingCube.class);
    MappingVirtualCube virtualCube = mock(MappingVirtualCube.class);
    MappingPrivateDimension dimension = mock(MappingPrivateDimension.class);
    MappingCalculatedMemberProperty calculatedMemberProperty = mock(MappingCalculatedMemberProperty.class);
    MappingCalculatedMember calculatedMember = mock(MappingCalculatedMember.class);
    MappingMeasure measure = mock(MappingMeasure.class);
    MappingHierarchy hierarchy = mock(MappingHierarchy.class);
    MappingLevel level = mock(MappingLevel.class);
    org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty property =
        mock(org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty.class);
    MappingNamedSet namedSet = mock(MappingNamedSet.class);
    MappingParameter parameter = mock(MappingParameter.class);
    MappingDrillThroughAction drillThroughAction = mock(MappingDrillThroughAction.class);
    MappingAction action = mock(MappingAction.class);
    MappingElementFormatter elementFormatter = mock(MappingElementFormatter.class);
    MappingJoinQuery join = mock(MappingJoinQuery.class);
    MappingTableQuery table = mock(MappingTableQuery.class);
    MappingJoinedQueryElement left = mock(MappingJoinedQueryElement.class);
    MappingJoinedQueryElement right = mock(MappingJoinedQueryElement.class);
    MappingFormula formula = mock(MappingFormula.class);
    MappingUserDefinedFunction userDefinedFunction = mock(MappingUserDefinedFunction.class);
    MappingWritebackTable writebackTable = mock(MappingWritebackTable.class);
    MappingWritebackAttribute writebackAttribute = mock(MappingWritebackAttribute.class);
    MappingWritebackMeasure writebackMeasure = mock(MappingWritebackMeasure.class);
    MappingDrillThroughMeasure drillThroughMeasure = mock(MappingDrillThroughMeasure.class);
    MappingDrillThroughAttribute drillThroughAttribute = mock(MappingDrillThroughAttribute.class);
    MappingAnnotation annotation = mock(MappingAnnotation.class);
    MappingRole role = mock(MappingRole.class);
    MappingCubeUsage cubeUsage = mock(MappingCubeUsage.class);
    MappingSQL sql = mock(MappingSQL.class);
    MappingTableQueryOptimisationHint hint = mock(MappingTableQueryOptimisationHint.class);
    MappingAggTable aggTable = mock(MappingAggName.class);
    MappingAggColumnName aggColumnName = mock(MappingAggColumnName.class);
    MappingAggForeignKey aggForeignKey = mock(MappingAggForeignKey.class);
    MappingAggMeasure aggMeasure = mock(MappingAggMeasure.class);
    MappingAggLevel aggLevel = mock(MappingAggLevel.class);
    MappingAggMeasureFactCount measuresFactCount = mock(MappingAggMeasureFactCount.class);

    MappingLevel l = new LevelTest("name",
        "table",
        "column",
        "nameColumn",
        "ordinalColumn",
        "parentColumn",
        "nullParentValue",
        TypeEnum.STRING,
        "approxRowCount",
        true,
        LevelTypeEnum.REGULAR,
        HideMemberIfEnum.NEVER,
        "formatter",
        "caption",
        "description",
        "captionColumn",
        List.of(),
        null,
        null,
        null,
        null,
        null,
        null,
        List.of(property),
        true,
        InternalTypeEnum.INT,
        null);

    @Test
    void testSchema() {

        when(schema.userDefinedFunctions()).thenAnswer(setupDummyListAnswer(userDefinedFunction));
        when(schema.parameters()).thenAnswer(setupDummyListAnswer(parameter));
        when(schema.roles()).thenAnswer(setupDummyListAnswer(role));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(USER_DEFINED_FUNCTION_NAME_MUST_BE_SET)
            .contains(String.format(EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, NOT_SET))
            .contains(PARAMETER_NAME_MUST_BE_SET)
            .contains(PARAMETER_TYPE_MUST_BE_SET)
            .contains(ROLE_NAME_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(USER_DEFINED_FUNCTION)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

    @Test
    void testCubeAndVirtualCubeAndCalculatedMemberAndAction() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCubes()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(cube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(cube.namedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(virtualCube.namedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(virtualCube.cubeUsages()).thenAnswer(setupDummyListAnswer(cubeUsage));
        when(cube.actions()).thenAnswer(setupDummyListAnswer(action));
        when(cube.writebackTable()).thenReturn(Optional.of(writebackTable));
        when(writebackTable.columns()).thenAnswer(setupDummyListAnswer(writebackAttribute, writebackMeasure));
        when(cube.drillThroughActions()).thenAnswer(setupDummyListAnswer(drillThroughAction));
        when(drillThroughAction.drillThroughElements()).thenAnswer(setupDummyListAnswer(drillThroughMeasure,
            drillThroughAttribute));
        when(drillThroughAction.annotations()).thenAnswer(setupDummyListAnswer(annotation));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(28);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(CUBE_NAME_MUST_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, NOT_SET))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN, NOT_SET, DIMENSIONS))
            .contains(VIRTUAL_CUBE_NAME_MUST_BE_SET)
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(CALCULATED_MEMBER_NAME_MUST_BE_SET)
            .contains(String.format(DIMENSION_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET))
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
            .contains(DIMENSIONS)
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
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

    @Test
    void testCubeAndVirtualCubeAndCalculatedMemberAndFormula() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCubes()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(cube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(calculatedMember.formulaElement()).thenReturn(formula);

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(14);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(CUBE_NAME_MUST_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, NOT_SET))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN, NOT_SET, DIMENSIONS))
            .contains(VIRTUAL_CUBE_NAME_MUST_BE_SET)
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(CALCULATED_MEMBER_NAME_MUST_BE_SET)
            .contains(String.format(DIMENSION_MUST_BE_SET_FOR_CALCULATED_MEMBER, NOT_SET))
            .contains(FORMULA_MUST_BE_SET);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS)
            .contains(MEASURE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER)
            .contains(FORMULA);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

    @Test
    void testMeasure() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.name()).thenReturn("cubeName");
        when(measure.calculatedMemberProperties()).thenAnswer(setupDummyListAnswer(calculatedMemberProperty));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(7);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN, "cubeName", DIMENSIONS))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_AGGREGATOR_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(DIMENSIONS)
            .contains(MEASURE)
            .contains(CALCULATED_MEMBER_PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

    @Test
    void testHierarchyWithJoin() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.name()).thenReturn("cubeName");
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(dimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(level));
        when(hierarchy.relation()).thenReturn(join);
        when(hierarchy.primaryKeyTable()).thenReturn("primaryKeyTable");
        when(join.left()).thenReturn(left);
        when(join.right()).thenReturn(right);
        when(left.getQuery()).thenReturn(table);
        when(right.getQuery()).thenReturn(table);
        when(table.getName()).thenReturn("tableName");
        when(table.getSql()).thenReturn(sql);
        when(table.getHints()).thenAnswer(setupDummyListAnswer(hint));
        when(table.getAggTables()).thenAnswer(setupDummyListAnswer(aggTable));
        when(level.memberFormatter()).thenReturn(elementFormatter);
        when(aggTable.aggIgnoreColumns()).thenAnswer(setupDummyListAnswer(aggColumnName));
        when(aggTable.aggForeignKeys()).thenAnswer(setupDummyListAnswer(aggForeignKey));
        when(aggTable.aggMeasures()).thenAnswer(setupDummyListAnswer(aggMeasure));
        when(aggTable.aggLevels()).thenAnswer(setupDummyListAnswer(aggLevel));
        when(aggTable.measuresFactCounts()).thenAnswer(setupDummyListAnswer(measuresFactCount));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(40);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(CUBE_DIMENSION_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_AGGREGATOR_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN, NOT_SET, NOT_SET))
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
            .contains(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR)
            .contains(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.WARNING);
    }

    @Test
    void testHierarchyWithoutJoin() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.name()).thenReturn("cubeName");
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(dimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(level));
        when(hierarchy.relation()).thenReturn(join);
        when(level.memberFormatter()).thenReturn(elementFormatter);
        when(level.properties()).thenAnswer(setupDummyListAnswer(property));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(15);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_AGGREGATOR_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(String.format(PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN, NOT_SET))
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
            .contains(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR,
            		org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.WARNING);
    }

    @Test
    void testCheckColumn_With_Table() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.name()).thenReturn("cubeName");
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(dimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(l));
        when(hierarchy.relation()).thenReturn(table);
        when(table.getName()).thenReturn("tableName");
        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(20);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_AGGREGATOR_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(PROPERTY_COLUMN_MUST_BE_SET)
            .contains(TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(LEVEL)
            .contains(TABLE)
            .contains(PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .contains(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR,
            		org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.WARNING);
    }

    @Test
    void testCheckColumn_With_Join() {
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.name()).thenReturn("cubeName");
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(dimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(l));
        when(hierarchy.relation()).thenReturn(join);
        when(join.left()).thenReturn(left);
        when(join.right()).thenReturn(right);
        when(left.getQuery()).thenReturn(table);
        when(right.getQuery()).thenReturn(table);
        when(table.getName()).thenReturn("tableName");
        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(36);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_NAME_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_AGGREGATOR_MUST_BE_SET, "cubeName"))
            .contains(String.format(MEASURE_COLUMN_MUST_BE_SET, "cubeName"))
            .contains(String.format(PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN, NOT_SET))
            .contains(PROPERTY_COLUMN_MUST_BE_SET)
            .contains(TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN);

        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .contains(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR, org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.WARNING);
    }

    public record LevelTest(String name,
                            String table,
                            String column,
                            String nameColumn,
                            String ordinalColumn,
                            String parentColumn,
                            String nullParentValue,
                            TypeEnum type,
                            String approxRowCount,
                            Boolean uniqueMembers,
                            LevelTypeEnum levelType,
                            HideMemberIfEnum hideMemberIf,
                            String formatter,
                            String caption,
                            String description,
                            String captionColumn,
                            List<MappingAnnotation> annotations,
                            MappingExpressionView keyExpression,
                            MappingExpressionView nameExpression,
                            MappingExpressionView captionExpression,
                            MappingExpressionView ordinalExpression,
                            MappingExpressionView parentExpression,
                            MappingClosure closure,
                            List<MappingProperty> properties,
                            Boolean visible,
                            InternalTypeEnum internalType,
                            MappingElementFormatter memberFormatter
    )
        implements MappingLevel {

    }
}
