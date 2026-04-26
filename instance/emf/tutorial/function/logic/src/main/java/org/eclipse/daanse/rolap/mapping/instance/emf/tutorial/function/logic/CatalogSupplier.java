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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.function.logic;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.06", source = Source.EMF, group = "Cube") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube;
    private Catalog catalog;
    private Schema databaseSchema;
    private TableSource query;
    private CalculatedMember calculatedMember1;


    private static final String introBody = """
            This tutorial discusses Calculated Members with logic functions.

            """;
    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with three columns: `KEY` and `VALUE` and `VALUE_NUMERIC`.
            """;
    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;
    private static final String cm1Body = """
            This calculated member with `IIF` function.
            """;
    private static final String cubeBody = """
            The cube defines the calculated members with logic functions.
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

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measureSum = MeasureFactory.eINSTANCE.createSumMeasure();
        measureSum.setName("Measure-Sum");
        measureSum.setColumn(valueColumn);

        CountMeasure measureCount = MeasureFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure-Count");
        measureCount.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

        calculatedMember1 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("IIF([Measures].[Measure-Sum]>100,[Measures].[Measure-Sum],Null)");
        calculatedMember1.setFormula("IIF([Measures].[Measure-Sum]>100,[Measures].[Measure-Sum],Null)");

        CalculatedMember calculatedMember2 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("IIF([Measures].[Measure-Sum]>10,[Measures].[Measure-Sum],Null)");
        calculatedMember2.setFormula("IIF([Measures].[Measure-Sum]>10,[Measures].[Measure-Sum],Null)");

        CalculatedMember calculatedMember3 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember3.setName("IIF([Measures].[Measure-Sum]>10,5,10)");
        calculatedMember3.setFormula("IIF([Measures].[Measure-Sum]>10,5,10)");

        CalculatedMember calculatedMember4 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember4.setName("case when [Measures].[Measure-Sum]>10 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");
        calculatedMember4.setFormula("case when [Measures].[Measure-Sum]>10 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");

        CalculatedMember calculatedMember5 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember5.setName("case when [Measures].[Measure-Sum]>100 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");
        calculatedMember5.setFormula("case when [Measures].[Measure-Sum]>100 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");

        CalculatedMember calculatedMember6 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember6.setName("[Measures].[Measure-Sum]>10 and [Measures].[Measure-Count]>0");
        calculatedMember6.setFormula("[Measures].[Measure-Sum]>10 and [Measures].[Measure-Count]>0");

        CalculatedMember calculatedMember7 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember7.setName("[Measures].[Measure-Sum]>10 or [Measures].[Measure-Count]>0");
        calculatedMember7.setFormula("[Measures].[Measure-Sum]>10 or [Measures].[Measure-Count]>0");

        CalculatedMember calculatedMember8 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember8.setName("IsEmpty([Measures].[Measure-Sum])");
        calculatedMember8.setFormula("IsEmpty([Measures].[Measure-Sum])");

        CalculatedMember calculatedMember9 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember9.setName("NOT IsEmpty([Measures].[Measure-Sum])");
        calculatedMember9.setFormula("NOT IsEmpty([Measures].[Measure-Sum])");

        CalculatedMember calculatedMember10 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember10.setName("[Measures].[Measure-Sum] IS NULL");
        calculatedMember10.setFormula("[Measures].[Measure-Sum] IS NULL");

        CalculatedMember calculatedMember11 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember11.setName("[Measures].[Measure-Sum]=10");
        calculatedMember11.setFormula("[Measures].[Measure-Sum]=10");

        CalculatedMember calculatedMember12 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember12.setName("[Measures].[Measure-Sum]<>10");
        calculatedMember12.setFormula("[Measures].[Measure-Sum]<>10");

        CalculatedMember calculatedMember13 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember13.setName("[Measures].[Measure-Sum]=10 XOR [Measures].[Measure-Sum]>10");
        calculatedMember13.setFormula("[Measures].[Measure-Sum]=10 XOR [Measures].[Measure-Sum]>10");

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube logic functions");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember1, calculatedMember2, calculatedMember3, calculatedMember4,
                calculatedMember5, calculatedMember6, calculatedMember7, calculatedMember8, calculatedMember9, calculatedMember10,
                calculatedMember11, calculatedMember12, calculatedMember13 ));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Function Logic");
        catalog.setDescription("Logic function implementations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Function Logic", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Calculated Member with IIF function", cm1Body, 1, 3, 0, calculatedMember1, 0),
                        new DocSection("Cube - logic functions", cubeBody, 1, 8, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
