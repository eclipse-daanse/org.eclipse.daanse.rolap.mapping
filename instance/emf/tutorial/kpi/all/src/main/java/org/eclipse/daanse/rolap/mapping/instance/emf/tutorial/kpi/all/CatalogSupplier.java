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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.CountMeasure;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.Kpi;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.07.02", source = Source.EMF, group = "Kpi") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private PhysicalCube cube;
    private Kpi kpi;
    private Schema databaseSchema;


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

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueNumericColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getOwnedElement().add(table);

        TableSource query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setColumn(valueColumn);

        CountMeasure measure1 = MeasureFactory.eINSTANCE.createCountMeasure();
        measure1.setName("Measure2-Count");
        measure1.setColumn(valueColumn);

        CalculatedMember calculatedValue = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedValue.setName("CalculatedValue");
        calculatedValue.setVisible(false);
        calculatedValue.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedGoal = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedGoal.setName("CalculatedGoal");
        calculatedGoal.setVisible(false);
        calculatedGoal.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedStatus = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedStatus.setName("CalculatedStatus");
        calculatedStatus.setVisible(false);
        calculatedStatus.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        CalculatedMember calculatedTrend = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedTrend.setName("CalculatedTrend");
        calculatedTrend.setVisible(false);
        calculatedTrend.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        measureGroup.getMeasures().add(measure1);
        kpi = CubeFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
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

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedValue, calculatedGoal, calculatedStatus, calculatedTrend));
        cube.getKpis().add(kpi);
        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - KPI All");
        catalog.setDescription("Complete KPI implementation examples");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - KPI All", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", dbBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Kpi with all parameters", kpiBody, 1, 2, 0, kpi, 0),
                        new DocSection("Cube with KPI with all properties", cubeBody, 1, 4, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
