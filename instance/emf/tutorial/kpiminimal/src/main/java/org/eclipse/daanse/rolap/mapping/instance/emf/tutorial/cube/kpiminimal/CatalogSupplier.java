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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.kpiminimal;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "CubeKPI";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
                A minimal cube with Kpi with minimal kpi properties

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

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure1-Sum");
        measure.setId("Measure1-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setId("Kpi1");
        kpi.setDescription("Kpi with value");
        kpi.setValue("[Measures].[Measure1-Sum]");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getKpis().add(kpi);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cubes_With_KPI_minimal_Properties");
        schema.setDescription("Minimal Cubes With KPI with minimal properties");
        schema.getCubes().add(cube);
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
