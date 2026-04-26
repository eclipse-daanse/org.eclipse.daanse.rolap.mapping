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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.virtualcube;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.Kpi;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.07.04", source = Source.EMF, group = "Kpi") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube1;
    private Schema databaseSchema;
    private Catalog catalog;
    private VirtualCube vCube;
    private PhysicalCube cube2;
    private Kpi kpi;


    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
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
            - value             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            - trend             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedTrend]`
            - weight            expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            - currentTimeMember expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            - DisplayFolder     Kpi1Folder1\\Kpi1Folder3 - folder tree of kpi item
            """;

    private static final String cube1Body = """
            This cube holds references to measure1 and DimensionConnector Cube1Dimension1
            """;

    private static final String cube2Body = """
            This cube holds references to measure2 and DimensionConnector Cube2Dimension1
            """;

    private static final String vCubeBody = """
            This cube holds references to the dimensions and mesures from Cube1 and Cube2.
            Cube have two CalculatedMembers (CalculatedValue, CalculatedTrend).
            CalculatedMembers uses measures from Cube1 and Cube2.
            Cube have KPI which use CalculatedMembers for parameters Value, Trend.
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

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("MeasureCube1");
        measure1.setColumn(valueColumn);

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("MeasureCube2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("HierarchyWithoutHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector1.setDimension(dimension);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Cube2Dimension1");
        dimensionConnector2.setDimension(dimension);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query);
        cube1.getDimensionConnectors().add(dimensionConnector1);
        cube1.getMeasureGroups().add(measureGroup1);

        cube2 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setQuery(query);
        cube2.getDimensionConnectors().add(dimensionConnector2);
        cube2.getMeasureGroups().add(measureGroup2);

        dimensionConnector1.setPhysicalCube(cube1);
        dimensionConnector2.setPhysicalCube(cube2);
        measureGroup1.setPhysicalCube(cube1);
        measureGroup2.setPhysicalCube(cube2);

        CalculatedMember cm1 = LevelFactory.eINSTANCE.createCalculatedMember();
        cm1.setName("CalculatedValue");
        cm1.setHierarchy(hierarchy);
        cm1.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        CalculatedMember cm2 = LevelFactory.eINSTANCE.createCalculatedMember();
        cm2.setName("CalculatedTrend");
        cm2.setHierarchy(hierarchy);
        cm2.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        kpi = CubeFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setValue("[Measures].[CalculatedValue]");
        kpi.setTrend("[Measures].[CalculatedTrend]");
        kpi.setDisplayFolder("Kpi1Folder1\\Kpi1Folder3");
        kpi.setAssociatedMeasureGroupID("Kpi2MeasureGroupID");

        vCube = CubeFactory.eINSTANCE.createVirtualCube();
        vCube.setName("Cube1Cube2Kpi");
        vCube.setDefaultMeasure(measure1);
        vCube.getDimensionConnectors().addAll(List.of(dimensionConnector1, dimensionConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().addAll(List.of(cm1, cm2));
        vCube.getKpis().add(kpi);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - KPI Virtual Cube");
        catalog.setDescription("KPI implementation in virtual cubes");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - KPI Virtual Cube", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", dbBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Kpi with parameters", kpiBody, 1, 2, 0, kpi, 0),
                        new DocSection("Cube1", cube1Body, 1, 3, 0, cube1, 2),
                        new DocSection("Cube2", cube2Body, 1, 4, 0, cube2, 2),
                        new DocSection("vCube", vCubeBody, 1, 5, 0, vCube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
