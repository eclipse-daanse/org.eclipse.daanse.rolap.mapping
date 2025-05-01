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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpiall;

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
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_30-01_Cube_KPI_All_Properties";
    private static final String CUBE = "CubeKPI";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
        A minimal cube with Kpi with all kpi properties


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

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setId("Measure1-Sum");
        measure.setColumn(valueColumn);

        CountMeasure measure1 = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measure1.setName("Measure2-Count");
        measure1.setId("Measure2-Count");
        measure1.setColumn(valueColumn);

        CalculatedMember calculatedValue = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedValue.setName("CalculatedValue");
        calculatedValue.setId("CalculatedValue");
        calculatedValue.setVisible(false);
        calculatedValue.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedGoal = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedGoal.setName("CalculatedGoal");
        calculatedGoal.setId("CalculatedGoal");
        calculatedGoal.setVisible(false);
        calculatedGoal.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedStatus = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedStatus.setName("CalculatedStatus");
        calculatedStatus.setId("CalculatedStatus");
        calculatedStatus.setVisible(false);
        calculatedStatus.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedTrend = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedTrend.setName("CalculatedTrend");
        calculatedTrend.setId("CalculatedTrend");
        calculatedTrend.setVisible(false);
        calculatedTrend.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        measureGroup.getMeasures().add(measure1);
        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setId("Kpi1");
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
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedValue, calculatedGoal, calculatedStatus, calculatedTrend));
        cube.getKpis().add(kpi);
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cubes_With_KPI_all_Properties");
        catalog.setDescription("Minimal Cubes With KPI with all properties");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;

    }

}
