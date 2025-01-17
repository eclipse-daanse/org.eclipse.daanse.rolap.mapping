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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DIMENSIONS;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DIMENSION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.ActionMappingMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughActionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.StandardDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = DescriptionVerifyerTest.COMPONENT_NAME, name = "1", location = "?", properties = {
    @Property(key = "calculatedMemberProperty", value = "INFO"), //
    @Property(key = "action", value = "INFO"), //
    @Property(key = "calculatedMember", value = "INFO"), //
    @Property(key = "cube", value = "INFO"), //
    @Property(key = "drillThroughAction", value = "INFO"), //
    @Property(key = "hierarchy", value = "INFO"), //
    @Property(key = "level", value = "INFO"), //
    @Property(key = "measure", value = "INFO"), //
    @Property(key = "namedSet", value = "INFO"), //
    @Property(key = "parameter", value = "INFO"), //
    @Property(key = "dimension", value = "INFO"), //
    @Property(key = "property", value = "INFO"), //
    @Property(key = "schema", value = "INFO"), //
    @Property(key = "sharedDimension", value = "INFO"), //
    @Property(key = "virtualCube", value = "INFO")})
public class DescriptionVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.rolap.mapping.verifyer.basic.description.DescriptionVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    SchemaMapping schema = mock(SchemaMapping.class);
    PhysicalCubeMapping cube = mock(PhysicalCubeMapping.class);
    VirtualCubeMapping virtualCube = mock(VirtualCubeMapping.class);
    StandardDimensionMapping dimension = mock(StandardDimensionMapping.class);
    CalculatedMemberPropertyMapping calculatedMemberProperty = mock(CalculatedMemberPropertyMapping.class);
    CalculatedMemberMapping calculatedMember = mock(CalculatedMemberMapping.class);
    MeasureGroupMapping measureGroup = mock(MeasureGroupMapping.class);
    MeasureMapping measure = mock(MeasureMapping.class);

    HierarchyMapping hierarchy = mock(HierarchyMapping.class);
    LevelMapping level = mock(LevelMapping.class);
    MemberPropertyMapping property = mock(MemberPropertyMapping.class);
    NamedSetMapping namedSet = mock(NamedSetMapping.class);
    ParameterMapping parameter = mock(ParameterMapping.class);
    DrillThroughActionMapping drillThroughAction = mock(DrillThroughActionMapping.class);
    ActionMappingMapping action = mock(ActionMappingMapping.class);
    DimensionConnectorMapping dimensionConnector = mock(DimensionConnectorMapping.class);

    @Test
    void testSchema() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube, virtualCube));
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(schema.getNamedSets()).thenAnswer(setupDummyListAnswer(namedSet));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(NAMED_SET_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS)
            .contains(NAMED_SET);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testCube() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.getAction()).thenAnswer(setupDummyListAnswer(action));


        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION)
            .contains(ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(CALCULATED_MEMBER)
            .contains(DIMENSIONS)
            .contains(MEASURE)
            .contains(ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testDimension() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube, virtualCube));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(virtualCube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));


        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testMeasure() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testCalculatedMemberProperty() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getMeasureGroups()).thenAnswer(setupDummyListAnswer(measureGroup));
        when(measureGroup.getMeasures()).thenAnswer(setupDummyListAnswer(measure));
        when(measure.getCalculatedMemberProperties()).thenAnswer(setupDummyListAnswer(calculatedMemberProperty));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testCalculatedMember() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube, virtualCube));
        when(cube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.getCalculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION); //2
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testHierarchy() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));


        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DIMENSIONS)
            .contains(HIERARCHY);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testLevel() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(level));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION)
            .contains(LEVEL_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DIMENSIONS)
            .contains(HIERARCHY)
            .contains(LEVEL);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testProperty() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(dimensionConnector.getDimension()).thenReturn(dimension);
        when(cube.getDimensionConnectors()).thenAnswer(setupDummyListAnswer(dimensionConnector));
        when(dimension.getHierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.getLevels()).thenAnswer(setupDummyListAnswer(level));
        when(level.getMemberProperties()).thenAnswer(setupDummyListAnswer(property));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION)
            .contains(LEVEL_MUST_CONTAIN_DESCRIPTION)
            .contains(PROPERTY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DIMENSIONS)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(PROPERTY);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testNamedSet() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.getNamedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(cube.getNamedSets()).thenAnswer(setupDummyListAnswer(namedSet));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(NAMED_SET_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(NAMED_SET);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    // Parameters are now in Catalog
//    void testParameter() {
//
//        when(schema.getParameters()).thenAnswer(setupDummyListAnswer(parameter));
//
//        List<VerificationResult> result = verifyer.verify(schema);
//        assertThat(result).isNotNull()
//            .hasSize(2);
//
//        assertThat(result)
//            .extracting(VerificationResult::description)
//            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
//            .contains(PARAMETER);
//        assertThat(result).extracting(VerificationResult::title)
//            .contains(SCHEMA)
//            .contains(PARAMETER);
//        assertThat(result).extracting(VerificationResult::level)
//            .containsOnly(Level.INFO);
//    }

    @Test
    void testDrillThroughAction() {

        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getAction()).thenAnswer(setupDummyListAnswer(drillThroughAction));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DRILL_THROUGH_ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);
    }

    @Test
    void testAction() {
        when(schema.getCubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.getAction()).thenAnswer(setupDummyListAnswer(action));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(Level.INFO);

    }

    public static <N> Answer<List<N>> setupDummyListAnswer(N... values) {
        final List<N> someList = new ArrayList<>(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<>() {
            @Override
            public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

}
