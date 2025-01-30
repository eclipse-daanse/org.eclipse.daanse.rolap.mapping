/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.calculatedmember;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-04-05_1_Cube_Measure_CalculatedMember";
    private static final String CUBE_NAME = "CubeTwoMeasuresPlusCalculatedMeasures";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A mininmal cube with a simple measure-based calculated member.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("Level");
        level.setColumn(keyColumn);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("D1");
        dimension.setId("D1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("D1");
        dimensionConnector.setForeignKey(keyColumn);
        dimensionConnector.setDimension(dimension);

        Measure measureSum = RolapMappingFactory.eINSTANCE.createMeasure();
        measureSum.setAggregator(MeasureAggregator.SUM);
        measureSum.setName("Measure1-Sum");
        measureSum.setColumn(valueColumn);

        Measure measureCount = RolapMappingFactory.eINSTANCE.createMeasure();
        measureCount.setAggregator(MeasureAggregator.COUNT);
        measureCount.setName("Measure2-Count");
        measureCount.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE
                .createCalculatedMemberProperty();
        calculatedMemberProperty.setId("P1");
        calculatedMemberProperty.setName("FORMAT_STRING");
        calculatedMemberProperty.setValue("0.0%");

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("CM1");
        calculatedMember1.setId("CM1");
        calculatedMember1.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
        calculatedMember1.getCalculatedMemberProperties().add(calculatedMemberProperty);

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("CM2");
        calculatedMember2.setId("CM2");
        calculatedMember2.setHierarchy(hierarchy);
        calculatedMember2.setParent("[Dimension1.Hierarchy1].[All Dimension1.Hierarchy1s]");
        calculatedMember2.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
        calculatedMember2.getCalculatedMemberProperties().add(calculatedMemberProperty);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setId(CUBE_NAME);
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember1, calculatedMember2));

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cubes_With_Two_Calculated_Members");
        schema.setDescription("Schema of a minimal cube with two calculated members");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG);
        catalog.getSchemas().add(schema);
        catalog.getDbschemas().add(databaseSchema);

        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("Catalog with schema of a minimal cube with CellFormatter with class name");
        catalog.setDocumentation(documentation);
        return catalog;

    }

}
