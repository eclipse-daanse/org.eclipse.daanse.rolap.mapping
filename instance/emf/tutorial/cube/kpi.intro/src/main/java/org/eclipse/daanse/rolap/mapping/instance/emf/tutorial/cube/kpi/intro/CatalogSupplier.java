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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.kpi.intro;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.7.1", source = Source.EMF) // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial is an introduction to the concept of KPIs in data cubes.

            A KPI has four important properties: value, goal, status, and trend. The most crucial (and mandatory) property is value. All four properties are defined as MDX expressions, which allows you to create an expression within the cube, give it a name, and associate a value with it.

            To keep things simple in this example, we will use an existing measure in our expression.
            """;

    private static final String dbBody = """
            A table `Fact` with a Column `VALUE` to have a reference for the Measure.
            """;

    private static final String kpi1Body = """
            This KPI is defined solely by its value expression, which in this example references the following measure: `[Measures].[Measure1-Sum]`"
            """;

    private static final String kpi2Body = """
            This KPI is additionally using a ParentKpiID.
            """;

    private static final String kpi3Body = """
            In addition to its value, this KPI has a display folder defined, which includes a folder hierarchy with folder and subfolder.
            """;

    private static final String cubeBody = """
            This cube holds references to the KPI, and does not use any dimensions.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab");
        table.getColumns().addAll(List.of(valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setId("Measure1-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Kpi kpi1 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi1.setName("Kpi1");
        kpi1.setId("_kpi_1");
        kpi1.setValue("[Measures].[Measure1-Sum]");

        Kpi kpi2 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi2.setName("Kpi2");
        kpi2.setId("_kpi_2");
        kpi2.setValue("[Measures].[Measure1-Sum]");
        kpi2.setParentKpi(kpi1);

        Kpi kpi3 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi3.setName("Kpi3");
        kpi3.setId("_kpi_4");
        kpi3.setValue("[Measures].[Measure1-Sum]");
        kpi3.setDisplayFolder("theDisplayFolder\\otherDisplayFolder");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Kpi");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getKpis().add(kpi1);
        cube.getKpis().add(kpi2);
        cube.getKpis().add(kpi3);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube - KPI Introduction");
        catalog.getCubes().add(cube);

        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube - KPI Introduction", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", dbBody, 1, 1, 0, true, 3);
        document(kpi1, "KPI - value only", kpi1Body, 1, 2, 0, true, 0);
        document(kpi2, "KPI - DisplayFolder", kpi2Body, 1, 3, 0, true, 0);
        document(kpi3, "KPI - Parent", kpi3Body, 1, 4, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 5, 0, true, 2);

        return catalog;

    }

}
