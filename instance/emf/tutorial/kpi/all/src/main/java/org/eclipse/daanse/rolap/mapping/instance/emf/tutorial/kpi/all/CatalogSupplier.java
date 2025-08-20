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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.all;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.7.2", source = Source.EMF, group = "Kpi") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "CubeKPI";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial is an introduction to the concept of KPIs in data cubes.

            A KPI has four important properties: value, goal, status, and trend. The most crucial (and mandatory) property is value. All four properties are defined as MDX expressions, which allows you to create an expression within the cube, give it a name, and associate a value with it.

            To keep things simple in this example, we will use an existing measure in our expression.
            """;

    private static final String dbBody = """
            A table `Fact` with a Column `VALUE` to have a reference for the Measure.
            """;

    private static final String kpiBody = """
            This KPI is defined
            value             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            goal              expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedGoal]`
            status            expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedStatus]`
            trend             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedTrend]`
            weight            expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            currentTimeMember expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            DisplayFolder     Kpi1Folder1\\Kpi1Folder2 - folder tree of kpi item
            StatusGraphic     'Cylinder' - grafic icone type for status
            TrendGraphic      'Smiley Face' - grafic icone type for trend

            """;

    private static final String cubeBody = """
            This cube holds references to the KPI, and does not use any dimensions.
            Cube have two measures (Measure1-Sum, Measure2-Count) and 4 CalculatedMembers (CalculatedValue, CalculatedGoal, CalculatedStatus, CalculatedTrend)
            """;

    private static final String schemaDocumentationTxt = """
        A minimal cube with Kpi with all kpi properties
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_KpiAll");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        Column valueNumericColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setId("_column_fact_value_numeric");
        valueNumericColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_factQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setId("_measure1-sum");
        measure.setColumn(valueColumn);

        CountMeasure measure1 = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measure1.setName("Measure2-Count");
        measure1.setId("_measure2-count");
        measure1.setColumn(valueColumn);

        CalculatedMember calculatedValue = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedValue.setName("CalculatedValue");
        calculatedValue.setId("_calculatedvalue");
        calculatedValue.setVisible(false);
        calculatedValue.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedGoal = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedGoal.setName("CalculatedGoal");
        calculatedGoal.setId("_calculatedgoal");
        calculatedGoal.setVisible(false);
        calculatedGoal.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedStatus = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedStatus.setName("CalculatedStatus");
        calculatedStatus.setId("_calculatedstatus");
        calculatedStatus.setVisible(false);
        calculatedStatus.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedTrend = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedTrend.setName("CalculatedTrend");
        calculatedTrend.setId("_calculatedtrend");
        calculatedTrend.setVisible(false);
        calculatedTrend.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        measureGroup.getMeasures().add(measure1);
        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setId("_kpi1");
        kpi.setDescription("Kpi with all parameters");
        kpi.setAssociatedMeasureGroupID("Kpi1MeasureGroupID");
        kpi.setValue("[Measures].[CalculatedValue]");
        kpi.setGoal("[Measures].[CalculatedGoal]");
        kpi.setStatus("[Measures].[CalculatedStatus]");
        kpi.setTrend("[Measures].[CalculatedTrend]");
        kpi.setWeight("[Measures].[CalculatedValue]");
        kpi.setCurrentTimeMember("[Measures].[CalculatedValue]");
        kpi.setDisplayFolder("Kpi1Folder1\\Kpi1Folder2");
        kpi.setStatusGraphic("Cylinder");
        kpi.setTrendGraphic("Smiley Face");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cubekpi");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedValue, calculatedGoal, calculatedStatus, calculatedTrend));
        cube.getKpis().add(kpi);
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - KPI All");
        catalog.setDescription("Complete KPI implementation examples");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Kpi with all parameters", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", dbBody, 1, 1, 0, true, 3);
        document(kpi, "Kpi with all parameters", kpiBody, 1, 2, 0, true, 0);

        document(cube, "Cube with KPI with all properties", cubeBody, 1, 4, 0, true, 2);

        return catalog;

    }

}
