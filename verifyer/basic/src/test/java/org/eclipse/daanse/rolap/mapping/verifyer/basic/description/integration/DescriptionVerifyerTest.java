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

import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.Action;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
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

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.ActionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
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
    private Schema databaseSchema;
    private Table table;
    private Column keyColumn;
    private Column valueColumn;
    private TableSource query;

    @BeforeEach
    void setUp() {
        // Create common database objects
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);
    }

    private Catalog createBaseCatalog() {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_test");
        catalog.setName("TestCatalog");
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

    private PhysicalCube createBaseCube() {
        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("TestMeasure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("TestCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        return cube;
    }

    private VirtualCube createBaseVirtualCube() {
        VirtualCube virtualCube = CubeFactory.eINSTANCE.createVirtualCube();
        virtualCube.setName("TestVirtualCube");
        return virtualCube;
    }

    private StandardDimension createBaseDimension() {
        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("TestDimension");
        return dimension;
    }

    private DimensionConnector createDimensionConnector(StandardDimension dimension) {
        DimensionConnector connector = DimensionFactory.eINSTANCE.createDimensionConnector();
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

        NamedSet namedSet = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet.setName("TestNamedSet");
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

        CalculatedMember calculatedMember = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setName("TestCalculatedMember");
        calculatedMember.setFormula("1");
        cube.getCalculatedMembers().add(calculatedMember);

        Action action = ActionFactory.eINSTANCE.createAction();
        action.setName("TestAction");
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

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("TestMeasure");
        measure.setColumn(valueColumn);

        CalculatedMemberProperty calculatedMemberProperty = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        calculatedMemberProperty.setName("TestProperty");
        measure.getCalculatedMemberProperties().add(calculatedMemberProperty);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("TestCube");
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

        CalculatedMember calculatedMember1 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("CalcMember1");
        calculatedMember1.setFormula("1");

        CalculatedMember calculatedMember2 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("CalcMember2");
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

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
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

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("TestLevel");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
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

        MemberProperty property = LevelFactory.eINSTANCE.createMemberProperty();
        property.setName("TestProperty");
        property.setColumn(valueColumn);

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("TestLevel");
        level.setColumn(keyColumn);
        level.getMemberProperties().add(property);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TestHierarchy");
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

        NamedSet namedSet1 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet1.setName("SchemaNamedSet");
        namedSet1.setFormula("{}");

        NamedSet namedSet2 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet2.setName("CubeNamedSet");
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

        DrillThroughAction drillThroughAction = ActionFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction.setName("TestDrillThrough");
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

        Action action = ActionFactory.eINSTANCE.createAction();
        action.setName("TestAction");
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
