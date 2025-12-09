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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.description.integration;

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
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.Action;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Property(key = "calculatedMemberProperty", value = "INFO"),
    @Property(key = "action", value = "INFO"),
    @Property(key = "calculatedMember", value = "INFO"),
    @Property(key = "cube", value = "INFO"),
    @Property(key = "drillThroughAction", value = "INFO"),
    @Property(key = "hierarchy", value = "INFO"),
    @Property(key = "level", value = "INFO"),
    @Property(key = "measure", value = "INFO"),
    @Property(key = "namedSet", value = "INFO"),
    @Property(key = "parameter", value = "INFO"),
    @Property(key = "dimension", value = "INFO"),
    @Property(key = "property", value = "INFO"),
    @Property(key = "schema", value = "INFO"),
    @Property(key = "sharedDimension", value = "INFO"),
    @Property(key = "virtualCube", value = "INFO")})
public class DescriptionVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.rolap.mapping.verifyer.basic.description.DescriptionVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    // Shared EMF objects
    private DatabaseSchema databaseSchema;
    private PhysicalTable table;
    private Column keyColumn;
    private Column valueColumn;
    private TableQuery query;

    @BeforeEach
    void setUp() {
        // Create common database objects
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

        query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);
    }

    private Catalog createBaseCatalog() {
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_test");
        catalog.setName("TestCatalog");
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

    private PhysicalCube createBaseCube() {
        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("TestMeasure");
        measure.setId("_measure_test");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("TestCube");
        cube.setId("_cube_test");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        return cube;
    }

    private VirtualCube createBaseVirtualCube() {
        VirtualCube virtualCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        virtualCube.setName("TestVirtualCube");
        virtualCube.setId("_virtualCube_test");
        return virtualCube;
    }

    private StandardDimension createBaseDimension() {
        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("TestDimension");
        dimension.setId("_dimension_test");
        return dimension;
    }

    private DimensionConnector createDimensionConnector(StandardDimension dimension) {
        DimensionConnector connector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connector.setId("_dimensionConnector_test");
        connector.setDimension(dimension);
        return connector;
    }

    @Test
    @Disabled
    void testSchema() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        VirtualCube virtualCube = createBaseVirtualCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        NamedSet namedSet = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet.setName("TestNamedSet");
        namedSet.setId("_namedSet_test");
        namedSet.setFormula("{}");

        Parameter parameter = RolapMappingFactory.eINSTANCE.createParameter();
        parameter.setName("TestParameter");

        schema.getCubes().addAll(List.of(cube, virtualCube));
        schema.getNamedSets().add(namedSet);
        schema.getParameters().add(parameter);

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(NAMED_SET_MUST_CONTAIN_DESCRIPTION)
            .contains(PARAMETER_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS)
            .contains(NAMED_SET)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testCube() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setName("TestCalculatedMember");
        calculatedMember.setId("_calculatedMember_test");
        calculatedMember.setFormula("1");
        cube.getCalculatedMembers().add(calculatedMember);

        Action action = RolapMappingFactory.eINSTANCE.createAction();
        action.setName("TestAction");
        action.setId("_action_test");
        cube.getAction().add(action);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testDimension() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        VirtualCube virtualCube = createBaseVirtualCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        DimensionConnector dimensionConnector2 = createDimensionConnector(dimension);
        dimensionConnector2.setId("_dimensionConnector_test2");

        cube.getDimensionConnectors().add(dimensionConnector);
        virtualCube.getDimensionConnectors().add(dimensionConnector2);

        schema.getCubes().addAll(List.of(cube, virtualCube));

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testMeasure() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testCalculatedMemberProperty() {
        Catalog schema = createBaseCatalog();

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("TestMeasure");
        measure.setId("_measure_test");
        measure.setColumn(valueColumn);

        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        calculatedMemberProperty.setName("TestProperty");
        calculatedMemberProperty.setId("_calculatedMemberProperty_test");
        measure.getCalculatedMemberProperties().add(calculatedMemberProperty);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("TestCube");
        cube.setId("_cube_test");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testCalculatedMember() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        VirtualCube virtualCube = createBaseVirtualCube();

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("CalcMember1");
        calculatedMember1.setId("_calculatedMember_1");
        calculatedMember1.setFormula("1");

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("CalcMember2");
        calculatedMember2.setId("_calculatedMember_2");
        calculatedMember2.setFormula("2");

        cube.getCalculatedMembers().add(calculatedMember1);
        virtualCube.getCalculatedMembers().add(calculatedMember2);

        schema.getCubes().addAll(List.of(cube, virtualCube));

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testHierarchy() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
        hierarchy.setId("_hierarchy_test");
        hierarchy.setQuery(query);
        hierarchy.setPrimaryKey(keyColumn);
        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testLevel() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("TestLevel");
        level.setId("_level_test");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
        hierarchy.setId("_hierarchy_test");
        hierarchy.setQuery(query);
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.getLevels().add(level);
        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testProperty() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();
        StandardDimension dimension = createBaseDimension();
        DimensionConnector dimensionConnector = createDimensionConnector(dimension);
        cube.getDimensionConnectors().add(dimensionConnector);

        MemberProperty property = RolapMappingFactory.eINSTANCE.createMemberProperty();
        property.setName("TestProperty");
        property.setId("_property_test");
        property.setColumn(valueColumn);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("TestLevel");
        level.setId("_level_test");
        level.setColumn(keyColumn);
        level.getMemberProperties().add(property);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
        hierarchy.setId("_hierarchy_test");
        hierarchy.setQuery(query);
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.getLevels().add(level);
        dimension.getHierarchies().add(hierarchy);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testNamedSet() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();

        NamedSet namedSet1 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet1.setName("SchemaNamedSet");
        namedSet1.setId("_namedSet_schema");
        namedSet1.setFormula("{}");

        NamedSet namedSet2 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet2.setName("CubeNamedSet");
        namedSet2.setId("_namedSet_cube");
        namedSet2.setFormula("{}");

        schema.getNamedSets().add(namedSet1);
        cube.getNamedSets().add(namedSet2);
        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    void testParameter() {
        Catalog schema = createBaseCatalog();

        Parameter parameter = RolapMappingFactory.eINSTANCE.createParameter();
        parameter.setName("TestParameter");
        schema.getParameters().add(parameter);

        List<VerificationResult> result = verifyer.verify(schema);
        assertThat(result).isNotNull()
            .hasSize(2);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testDrillThroughAction() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();

        DrillThroughAction drillThroughAction = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction.setName("TestDrillThrough");
        drillThroughAction.setId("_drillThrough_test");
        cube.getAction().add(drillThroughAction);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }

    @Test
    @Disabled
    void testAction() {
        Catalog schema = createBaseCatalog();
        PhysicalCube cube = createBaseCube();

        Action action = RolapMappingFactory.eINSTANCE.createAction();
        action.setName("TestAction");
        action.setId("_action_test");
        cube.getAction().add(action);

        schema.getCubes().add(cube);

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
            .containsOnly(org.eclipse.daanse.rolap.mapping.verifyer.api.Level.INFO);
    }
}
