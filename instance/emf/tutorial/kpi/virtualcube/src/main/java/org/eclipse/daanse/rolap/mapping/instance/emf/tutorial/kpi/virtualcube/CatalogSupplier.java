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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.7.4", source = Source.EMF, group = "Kpi") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

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
            value             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            trend             expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedTrend]`
            weight            expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            currentTimeMember expression, which in this example references the following CalculatedMember: `[Measures].[CalculatedValue]`
            DisplayFolder     Kpi1Folder1\\Kpi1Folder3 - folder tree of kpi item
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


    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with virtual cube which have reference to Cube1, Cube2 and with KPI

                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        Column valueNumericColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setId("_Fact_VALUE_NUMERIC");
        valueNumericColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_Fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("MeasureCube1");
        measure1.setId("_MeasureCube1");
        measure1.setColumn(valueColumn);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("MeasureCube2");
        measure2.setId("_MeasureCube2");
        measure2.setColumn(valueColumn);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setId("_Level2");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("HierarchyWithoutHasAll");
        hierarchy.setId("_HierarchyWithoutHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("_Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_cube1Dimension1");
        dimensionConnector1.setOverrideDimensionName("Cube1Dimension1");
        dimensionConnector1.setDimension(dimension);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setId("_dc_cube2Dimension1");
        dimensionConnector2.setOverrideDimensionName("Cube2Dimension1");
        dimensionConnector2.setDimension(dimension);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_Cube1");
        cube1.setQuery(query);
        cube1.getDimensionConnectors().add(dimensionConnector1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId("_Cube2");
        cube2.setQuery(query);
        cube2.getDimensionConnectors().add(dimensionConnector2);
        cube2.getMeasureGroups().add(measureGroup2);

        dimensionConnector1.setPhysicalCube(cube1);
        dimensionConnector2.setPhysicalCube(cube2);
        measureGroup1.setPhysicalCube(cube1);
        measureGroup2.setPhysicalCube(cube2);

        CalculatedMember cm1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm1.setName("CalculatedValue");
        cm1.setId("_CalculatedValue");
        cm1.setHierarchy(hierarchy);
        cm1.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        CalculatedMember cm2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm2.setName("CalculatedTrend");
        cm2.setId("_CalculatedTrend");
        cm2.setHierarchy(hierarchy);
        cm2.setFormula("[Measures].[MeasureCube1] + [Measures].[MeasureCube2]");

        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.setName("Kpi1");
        kpi.setId("_Kpi1");
        kpi.setDescription("Kpi with all parameters");
        kpi.setValue("[Measures].[CalculatedValue]");
        kpi.setTrend("[Measures].[CalculatedTrend]");
        kpi.setDisplayFolder("Kpi1Folder1\\Kpi1Folder3");
        kpi.setAssociatedMeasureGroupID("Kpi2MeasureGroupID");

        VirtualCube vCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        vCube.setName("Cube1Cube2Kpi");
        vCube.setId("_Cube1Cube2Kpi");
        vCube.setDefaultMeasure(measure1);
        vCube.getDimensionConnectors().addAll(List.of(dimensionConnector1, dimensionConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().addAll(List.of(cm1, cm2));
        vCube.getKpis().add(kpi);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with virtual cube with kpi");
        catalog.setDescription("Cube with virtual cube with kpi");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube with virtual cube with kpi", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", dbBody, 1, 1, 0, true, 3);
        document(kpi, "Kpi with parameters", kpiBody, 1, 2, 0, true, 0);

        document(cube1, "Cube1", cube1Body, 1, 3, 0, true, 2);
        document(cube2, "Cube2", cube2Body, 1, 4, 0, true, 2);
        document(vCube, "vCube", vCubeBody, 1, 5, 0, true, 2);

        return catalog;

    }

}
