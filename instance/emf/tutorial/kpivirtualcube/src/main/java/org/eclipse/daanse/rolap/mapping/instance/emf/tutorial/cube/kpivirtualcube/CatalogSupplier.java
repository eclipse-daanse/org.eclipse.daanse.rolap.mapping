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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.kpivirtualcube;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with virtual cube which have reference to Cube1, Cube2 and with KPI

                A KPI has four important properties which are value, goal, status and trend.
                Let's explain this by means of Profit Margin with the below calculation.

            Value: is the actual value of the KPI. This will be a numeric value. For example, this can be the Profit Margin.
                This might not be included in the fact table hence we may have to calculate or derive this column.

            Goal: every organization has a goal for this value. For example, the organization may look at the
                goal of achieving a five percent Profit Margin. Also, sometimes they may have different values for
                different business areas. For example, depending on the product category or sales territory,
                the sales margin goal will differ.

            Status: depending on the KPI value and the KPI goal, the KPI status can be defined.
                For an example, we can say that if the KPI value is greater than the goal it is great if it is not greater
                than the goal, but still greater than zero it is good and if less than zero or running at a loss it is bad.
                This Great, Good or Bad can be displayed to the user by means of a graphical representation such as an arrow,
                traffic lights or a gauge.

            Trend: trend is an optional parameter when defining a KPI, but still an important feature in a KPI.
                For example, you may have a great profit margin, but comparing with last year, it could be less.
                On the other hand, you might have a bad profit margin, but compared to last year it is improving.

            Weight:  The unique name of the member in the measures dimension for the KPI weight.

            StatusGraphic: The default graphical representation of the KPI status.
                (Traffic Light, Road Signs, Gauge - Ascending, Gauge - Descending, Thermometer, Cylinder, Smiley Face)

            TrendGraphic: The default graphical representation of the KPI trend.
                (Standard Arrow, Status Arrow - Ascending, Status Arrow - Descending, Smiley Face)

            DisplayFolder:  The display folder.
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

        Column valueNumericColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE_NUMERIC");
        valueColumn.setId("Fact_VALUE_NUMERIC");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("MeasureCube1");
        measure1.setId("MeasureCube1");
        measure1.setColumn(valueColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("MeasureCube2");
        measure2.setId("MeasureCube2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setColumn(keyColumn);
        level.setColumnType(ColumnDataType.STRING);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("HierarchyWithoutHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector1.setDimension(dimension);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector2.setDimension(dimension);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId(CUBE1);
        cube1.setQuery(query);
        cube1.getDimensionConnectors().add(dimensionConnector1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId(CUBE2);
        cube2.setQuery(query);
        cube2.getDimensionConnectors().add(dimensionConnector1);
        cube2.getMeasureGroups().add(measureGroup2);

        dimensionConnector1.setPhysicalCube(cube1);
        dimensionConnector2.setPhysicalCube(cube2);
        measureGroup1.setPhysicalCube(cube1);
        measureGroup2.setPhysicalCube(cube2);

        CalculatedMember cm1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm1.setName("CalculatedValue");
        cm1.setId("CalculatedValue");
        cm1.setHierarchy(hierarchy);
        cm1.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        CalculatedMember cm2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm2.setName("CalculatedTrend");
        cm2.setId("CalculatedTrend");
        cm2.setHierarchy(hierarchy);
        cm2.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setId("Kpi1");
        kpi.setDescription("Kpi with all parameters");
        kpi.setValue("[Measures].[CalculatedValue]");
        kpi.setTrend("[Measures].[CalculatedTrend]");
        kpi.setDisplayFolder("Kpi1Folder1\\Kpi1Folder3");
        kpi.setAssociatedMeasureGroupID("Kpi2MeasureGroupID");

        VirtualCube vCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        vCube.setName("Cube1Cube2Kpi");
        vCube.setId("Cube1Cube2Kpi");
        vCube.setDefaultMeasure(measure1);
        vCube.getDimensionConnectors().addAll(List.of(dimensionConnector1, dimensionConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().addAll(List.of(cm1, cm2));
        vCube.getKpis().add(kpi);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Cube_with_virtual_cube_with_kpi");
        schema.setDescription("Cube with virtual cube with kpi");
        schema.getCubes().addAll(List.of(cube1, cube2, vCube));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getSchemas().add(schema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema with a minimal cubes With KPI with minimal properties");
        catalog.setDocumentation(documentation);
        return catalog;

    }

}
