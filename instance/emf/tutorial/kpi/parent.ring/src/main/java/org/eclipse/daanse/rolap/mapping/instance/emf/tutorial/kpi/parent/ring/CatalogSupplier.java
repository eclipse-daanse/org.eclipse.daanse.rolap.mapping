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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
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
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.07.03", source = Source.EMF, group = "Kpi") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Kpi kpi2;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private Kpi kpi1;
    private Kpi kpi3;


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
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(valueColumn));
        databaseSchema.getOwnedElement().add(table);

        TableSource query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);
        kpi3 = CubeFactory.eINSTANCE.createKpi();

        kpi1 = CubeFactory.eINSTANCE.createKpi();
        kpi1.setName("Kpi1");
        kpi1.setValue("[Measures].[Measure1-Sum]");
        kpi1.setParentKpi(kpi3);

        kpi2 = CubeFactory.eINSTANCE.createKpi();
        kpi2.setName("Kpi2");
        kpi2.setValue("[Measures].[Measure1-Sum]");
        kpi2.setParentKpi(kpi1);

        kpi3.setName("Kpi3");
        kpi3.setValue("[Measures].[Measure1-Sum]");
        kpi3.setParentKpi(kpi1);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Kpi");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getKpis().add(kpi1);
        cube.getKpis().add(kpi2);
        cube.getKpis().add(kpi3);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - KPI Parent Ring");
        catalog.setDescription("KPI parent-child ring relationships");
        catalog.getCubes().add(cube);

        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - KPI Parent Ring", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", dbBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("KPI - value only", kpi1Body, 1, 2, 0, kpi1, 0),
                        new DocSection("KPI - DisplayFolder", kpi2Body, 1, 3, 0, kpi2, 0),
                        new DocSection("KPI - Parent", kpi3Body, 1, 4, 0, kpi3, 0),
                        new DocSection("Cube and Measure and KPI parent ring", cubeBody, 1, 5, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
