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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.kpi.parent.ring;

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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.7.3", source = Source.EMF, group = "Kpi") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial is an introduction to the concept of KPIs in data cubes with parent KPI.


            Kpi1 is parent for Kpi2. Kpi2 is parent for Kpi3. Kpi3 is again parent for Kpi1.

            Be carefull to not do that. Excel not able to find parent and show empty KPI tree
            """;

    private static final String dbBody = """
            A table `Fact` with a Column `VALUE` to have a reference for the Measure.
            """;

    private static final String kpi1Body = """
            This KPI is defined solely by its value expression, which in this example references the following measure: `[Measures].[Measure1-Sum]`
            This KPI is additionally using a Kpi3 as parent. We have cyrcle link here"
            """;

    private static final String kpi2Body = """
            This KPI is additionally using a Kpi1 as parent.
            """;

    private static final String kpi3Body = """
            This KPI is additionally using a Kpi2 as parent. And this KPI is parent for Kpi1.
            """;

    private static final String cubeBody = """
            This cube holds references to the KPI, and does not use any dimensions.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_KpiParentRing");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_factQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setId("_measure_Measure1Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        Kpi kpi3 = RolapMappingFactory.eINSTANCE.createKpi();

        Kpi kpi1 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi1.setName("Kpi1");
        kpi1.setId("_kpi_Kpi1");
        kpi1.setValue("[Measures].[Measure1-Sum]");
        kpi1.setParentKpi(kpi3);

        Kpi kpi2 = RolapMappingFactory.eINSTANCE.createKpi();
        kpi2.setName("Kpi2");
        kpi2.setId("_kpi_Kpi2");
        kpi2.setValue("[Measures].[Measure1-Sum]");
        kpi2.setParentKpi(kpi1);

        kpi3.setName("Kpi3");
        kpi3.setId("_kpi_Kpi3");
        kpi3.setValue("[Measures].[Measure1-Sum]");
        kpi3.setParentKpi(kpi1);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Kpi");
        cube.setId("_cube_CubeKpi");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getKpis().add(kpi1);
        cube.getKpis().add(kpi2);
        cube.getKpis().add(kpi3);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Kpi - parent ring");
        catalog.getCubes().add(cube);

        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Kpi - Introduction", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", dbBody, 1, 1, 0, true, 3);
        document(kpi1, "KPI - value only", kpi1Body, 1, 2, 0, true, 0);
        document(kpi2, "KPI - DisplayFolder", kpi2Body, 1, 3, 0, true, 0);
        document(kpi3, "KPI - Parent", kpi3Body, 1, 4, 0, true, 0);

        document(cube, "Cube and Measure and KPI parent ring", cubeBody, 1, 5, 0, true, 2);

        return catalog;

    }

}
