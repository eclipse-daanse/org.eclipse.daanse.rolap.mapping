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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpiparent;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_30-07_Cube_KPI_with_Parent";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
        A minimal cube with Kpi with parents - children
        Kpi1 is parent for Kpi2_1, Kpi2_2
        Kpi2_1 is parent for Kpi3

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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        Column valueNumericColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setId("Fact_VALUE_NUMERIC");
        valueNumericColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure-Sum1");
        measure1.setId("Measure-Sum1");
        measure1.setColumn(valueColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("Measure-Sum2");
        measure2.setId("Measure-Sum2");
        measure2.setColumn(valueColumn);

        Measure measure3 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure3.setAggregator(MeasureAggregator.SUM);
        measure3.setName("Measure-Sum3");
        measure3.setId("Measure-Sum3");
        measure3.setColumn(valueColumn);

        Measure measure4 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure4.setAggregator(MeasureAggregator.SUM);
        measure4.setName("Measure-Sum4");
        measure4.setId("Measure-Sum4");
        measure4.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3, measure4));

        Kpi kpi1 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi1.setName("Kpi1");
        kpi1.setId("Kpi1");
        kpi1.setDescription("Kpi1");
        kpi1.setValue("[Measures].[Measure-Sum1]");

        Kpi kpi21 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi21.setName("Kpi21");
        kpi21.setId("Kpi21");
        kpi21.setDescription("Kpi2_1");
        kpi21.setValue("[Measures].[Measure-Sum2]");
        kpi21.setParentKpiID("Kpi1");

        Kpi kpi22 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi22.setName("Kpi22");
        kpi22.setId("Kpi22");
        kpi22.setDescription("Kpi2_2");
        kpi22.setValue("[Measures].[Measure-Sum3]");
        kpi22.setParentKpiID("Kpi1");

        Kpi kpi3 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi3.setName("Kpi3");
        kpi3.setId("Kpi3");
        kpi3.setDescription("Kpi3");
        kpi3.setValue("[Measures].[Measure-Sum4]");
        kpi3.setParentKpiID("Kpi21");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getKpis().addAll(List.of(kpi1, kpi21, kpi22, kpi3));
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cubes_With_KPI_with_parent");
        catalog.setDescription("Minimal Cubes With KPI with parent");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;

    }

}
