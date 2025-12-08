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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.model.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.model.AggregationName;
import org.eclipse.daanse.rolap.mapping.model.Annotation;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.CubeConnector;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.model.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.WritebackTable;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages;
import org.junit.jupiter.api.BeforeEach;
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

    // Shared EMF objects
    private DatabaseSchema databaseSchema;
    private PhysicalTable table;
    private Column keyColumn;
    private Column valueColumn;
    private TableQuery tableQuery;

    @BeforeEach
    void setUp() {
        databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_test");

        keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_key");
        keyColumn.setType(ColumnType.VARCHAR);

        valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_value");
        valueColumn.setType(ColumnType.INTEGER);

        table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        tableQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuery.setId("_query_fact");
        tableQuery.setTable(table);
    }

    private Catalog createBaseCatalog() {
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_test");
        // No name set - to test validation
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

    @Test
    @Disabled
    void testSchema() {
        Catalog schema = createBaseCatalog();

        Parameter parameter = RolapMappingFactory.eINSTANCE.createParameter();
        // No name or type set - to test validation
        schema.getParameters().add(parameter);

        AccessRole role = RolapMappingFactory.eINSTANCE.createAccessRole();
        role.setId("_role_test");
        // No name set - to test validation
        schema.getAccessRoles().add(role);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR);
    }

    @Test
    @Disabled
    void testCubeAndVirtualCubeAndCalculatedMemberAndAction() {
        Catalog schema = createBaseCatalog();

        // Create PhysicalCube without name, without fact
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        // No name or query set

        // Create VirtualCube without name, dimensions, measures
        VirtualCube virtualCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        virtualCube.setId("_virtualCube_test");
        // No name set

        // Create CalculatedMember without name, hierarchy, formula
        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setId("_calculatedMember_test");
        // No name, hierarchy, or formula set

        cube.getCalculatedMembers().add(calculatedMember);
        virtualCube.getCalculatedMembers().add(calculatedMember);

        // Create NamedSets without names/formulas
        NamedSet namedSet = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet.setId("_namedSet_test");
        // No name or formula set

        cube.getNamedSets().add(namedSet);
        virtualCube.getNamedSets().add(namedSet);

        // Create CubeConnector without cube reference
        CubeConnector cubeUsage = RolapMappingFactory.eINSTANCE.createCubeConnector();
        // No cube set
        virtualCube.getCubeUsages().add(cubeUsage);

        // Create DrillThroughAction without name
        DrillThroughAction drillThroughAction = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction.setId("_drillThrough_test");
        // No name set

        // Create DrillThroughAttribute without name
        DrillThroughAttribute drillThroughAttribute = RolapMappingFactory.eINSTANCE.createDrillThroughAttribute();
        // No name set
        drillThroughAction.getDrillThroughAttribute().add(drillThroughAttribute);

        // Create BaseMeasure for drill through measure without name
        SumMeasure drillThroughMeasure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        drillThroughMeasure.setId("_drillThroughMeasure_test");
        // No name set
        drillThroughAction.getDrillThroughMeasure().add(drillThroughMeasure);

        // Create Annotation without name
        Annotation annotation = RolapMappingFactory.eINSTANCE.createAnnotation();
        // No name set
        drillThroughAction.getAnnotations().add(annotation);

        cube.getAction().add(drillThroughAction);

        // Create WritebackTable without name
        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        // No name set

        WritebackAttribute writebackAttribute = RolapMappingFactory.eINSTANCE.createWritebackAttribute();
        // No dimension or column set
        writebackTable.getWritebackAttribute().add(writebackAttribute);

        WritebackMeasure writebackMeasure = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        // No name or column set
        writebackTable.getWritebackMeasure().add(writebackMeasure);

        cube.setWritebackTable(writebackTable);

        schema.getCubes().addAll(List.of(cube, virtualCube));

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR);
    }

    @Test
    @Disabled
    void testCubeAndVirtualCubeAndCalculatedMemberAndFormula() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");

        VirtualCube virtualCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        virtualCube.setId("_virtualCube_test");

        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setId("_calculatedMember_test");

        cube.getCalculatedMembers().add(calculatedMember);
        virtualCube.getCalculatedMembers().add(calculatedMember);

        schema.getCubes().addAll(List.of(cube, virtualCube));

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
            .contains(CALCULATED_MEMBER);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR);
    }

    @Test
    @Disabled
    void testMeasure() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        cube.setName("cubeName");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setId("_measure_test");
        // No name or column set

        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        calculatedMemberProperty.setId("_calcMemberProp_test");
        // No name set
        measure.getCalculatedMemberProperties().add(calculatedMemberProperty);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR);
    }

    @Test
    @Disabled
    void testHierarchyWithJoin() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        cube.setName("cubeName");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setId("_measure_test");
        // No name or column set

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setId("_dimension_test");

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_test");
        dimensionConnector.setOverrideDimensionName("DimensionName");
        dimensionConnector.setDimension(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setId("_level_test");
        // No name or column set

        MemberFormatter elementFormatter = RolapMappingFactory.eINSTANCE.createMemberFormatter();
        // No className or script set
        level.setMemberFormatter(elementFormatter);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setId("_hierarchy_test");
        hierarchy.getLevels().add(level);

        // Create JoinQuery
        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("_joinQuery_test");

        // Create TableQueries for join
        PhysicalTable hierarchyTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hierarchyTable.setName("hierarchyTable");
        hierarchyTable.setId("_table_hierarchy");

        TableQuery leftTableQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        leftTableQuery.setId("_query_left");
        leftTableQuery.setTable(table);

        TableQuery rightTableQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        rightTableQuery.setId("_query_right");
        rightTableQuery.setTable(table);

        // Add SqlWhereExpression without dialect
        SqlStatement sql = RolapMappingFactory.eINSTANCE.createSqlStatement();
        // No dialect set
        leftTableQuery.setSqlWhereExpression(sql);

        // Add hint without type
        TableQueryOptimizationHint hint = RolapMappingFactory.eINSTANCE.createTableQueryOptimizationHint();
        // No type set
        leftTableQuery.getOptimizationHints().add(hint);

        // Add aggregation table without aggFactCount
        AggregationName aggTable = RolapMappingFactory.eINSTANCE.createAggregationName();
        aggTable.setId("_aggTable_test");
        // No aggFactCount set

        AggregationColumnName aggColumnName = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        // No column set
        aggTable.getAggregationIgnoreColumns().add(aggColumnName);

        AggregationForeignKey aggForeignKey = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        // No fact or agg column set
        aggTable.getAggregationForeignKeys().add(aggForeignKey);

        AggregationMeasure aggMeasure = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        // No name or column set
        aggTable.getAggregationMeasures().add(aggMeasure);

        AggregationLevel aggLevel = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        // No name or column set
        aggTable.getAggregationLevels().add(aggLevel);

        AggregationMeasureFactCount measuresFactCount = RolapMappingFactory.eINSTANCE.createAggregationMeasureFactCount();
        // No factColumn set
        aggTable.getAggregationMeasureFactCounts().add(measuresFactCount);

        leftTableQuery.getAggregationTables().add(aggTable);

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        // No key set
        left.setQuery(leftTableQuery);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        // No key set
        right.setQuery(rightTableQuery);

        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        hierarchy.setQuery(joinQuery);
        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .contains(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR)
            .contains(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING);
    }

    @Test
    @Disabled
    void testHierarchyWithoutJoin() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        cube.setName("cubeName");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setId("_measure_test");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setId("_dimension_test");

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_test");
        dimensionConnector.setOverrideDimensionName("DimensionName");
        dimensionConnector.setDimension(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setId("_level_test");

        MemberFormatter elementFormatter = RolapMappingFactory.eINSTANCE.createMemberFormatter();
        level.setMemberFormatter(elementFormatter);

        MemberProperty property = RolapMappingFactory.eINSTANCE.createMemberProperty();
        property.setId("_property_test");
        // No column set
        level.getMemberProperties().add(property);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setId("_hierarchy_test");
        hierarchy.getLevels().add(level);

        // Create JoinQuery for hierarchy (with null left/right - triggers join validation)
        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("_joinQuery_test");
        hierarchy.setQuery(joinQuery);

        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .contains(PROPERTY);

        assertThat(result).extracting(VerificationResult::level)
            .contains(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR,
                org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING);
    }

    @Test
    @Disabled
    void testCheckColumn_With_Table() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        cube.setName("cubeName");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setId("_measure_test");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setId("_dimension_test");

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_test");
        dimensionConnector.setOverrideDimensionName("DimensionName");
        dimensionConnector.setDimension(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setId("_hierarchy_test");
        hierarchy.setQuery(tableQuery);

        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .contains(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR,
                org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING);
    }

    @Test
    @Disabled
    void testCheckColumn_With_Join() {
        Catalog schema = createBaseCatalog();

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setId("_cube_test");
        cube.setName("cubeName");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setId("_measure_test");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setId("_dimension_test");

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_test");
        dimensionConnector.setOverrideDimensionName("DimensionName");
        dimensionConnector.setDimension(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setId("_hierarchy_test");

        // Create JoinQuery
        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("_joinQuery_test");

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setQuery(tableQuery);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setQuery(tableQuery);

        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        hierarchy.setQuery(joinQuery);
        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .contains(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR, org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING);
    }
}
