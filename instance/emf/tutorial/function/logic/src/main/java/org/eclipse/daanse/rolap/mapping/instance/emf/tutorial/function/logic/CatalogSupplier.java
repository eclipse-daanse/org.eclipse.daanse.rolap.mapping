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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.CountMeasure;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.06", source = Source.EMF, group = "Cube") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial discusses Calculated Members with logic functions.

            """;
    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with three columns: `KEY` and `VALUE` and `VALUE_NUMERIC`.
            """;
    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;
    private static final String cm1Body = """
            This calculated member with `IIF` function.
            """;
    private static final String cubeBody = """
            The cube defines the calculated members with logic functions.
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseschema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        SumMeasure measureSum = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureSum.setName("Measure-Sum");
        measureSum.setId("_measure_MeasureSum");
        measureSum.setColumn(valueColumn);

        CountMeasure measureCount = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure-Count");
        measureCount.setId("_measure_MeasureCount");
        measureCount.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("IIF([Measures].[Measure-Sum]>100,[Measures].[Measure-Sum],Null)");
        calculatedMember1.setId("_cm1");
        calculatedMember1.setFormula("IIF([Measures].[Measure-Sum]>100,[Measures].[Measure-Sum],Null)");

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("IIF([Measures].[Measure-Sum]>10,[Measures].[Measure-Sum],Null)");
        calculatedMember2.setId("_cm2");
        calculatedMember2.setFormula("IIF([Measures].[Measure-Sum]>10,[Measures].[Measure-Sum],Null)");

        CalculatedMember calculatedMember3 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember3.setName("IIF([Measures].[Measure-Sum]>10,5,10)");
        calculatedMember3.setId("_cm3");
        calculatedMember3.setFormula("IIF([Measures].[Measure-Sum]>10,5,10)");

        CalculatedMember calculatedMember4 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember4.setName("case when [Measures].[Measure-Sum]>10 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");
        calculatedMember4.setId("_cm4");
        calculatedMember4.setFormula("case when [Measures].[Measure-Sum]>10 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");

        CalculatedMember calculatedMember5 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember5.setName("case when [Measures].[Measure-Sum]>100 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");
        calculatedMember5.setId("_cm5");
        calculatedMember5.setFormula("case when [Measures].[Measure-Sum]>100 then [Measures].[Measure-Sum] else [Measures].[Measure-Count] end");

        CalculatedMember calculatedMember6 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember6.setName("[Measures].[Measure-Sum]>10 and [Measures].[Measure-Count]>0");
        calculatedMember6.setId("_cm6");
        calculatedMember6.setFormula("[Measures].[Measure-Sum]>10 and [Measures].[Measure-Count]>0");

        CalculatedMember calculatedMember7 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember7.setName("[Measures].[Measure-Sum]>10 or [Measures].[Measure-Count]>0");
        calculatedMember7.setId("_cm7");
        calculatedMember7.setFormula("[Measures].[Measure-Sum]>10 or [Measures].[Measure-Count]>0");

        CalculatedMember calculatedMember8 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember8.setName("IsEmpty([Measures].[Measure-Sum])");
        calculatedMember8.setId("_cm8");
        calculatedMember8.setFormula("IsEmpty([Measures].[Measure-Sum])");

        CalculatedMember calculatedMember9 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember9.setName("NOT IsEmpty([Measures].[Measure-Sum])");
        calculatedMember9.setId("_cm9");
        calculatedMember9.setFormula("NOT IsEmpty([Measures].[Measure-Sum])");

        CalculatedMember calculatedMember10 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember10.setName("[Measures].[Measure-Sum] IS NULL");
        calculatedMember10.setId("_cm10");
        calculatedMember10.setFormula("[Measures].[Measure-Sum] IS NULL");

        CalculatedMember calculatedMember11 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember11.setName("[Measures].[Measure-Sum]=10");
        calculatedMember11.setId("_cm11");
        calculatedMember11.setFormula("[Measures].[Measure-Sum]=10");

        CalculatedMember calculatedMember12 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember12.setName("[Measures].[Measure-Sum]<>10");
        calculatedMember12.setId("_cm12");
        calculatedMember12.setFormula("[Measures].[Measure-Sum]<>10");

        CalculatedMember calculatedMember13 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember13.setName("[Measures].[Measure-Sum]=10 XOR [Measures].[Measure-Sum]>10");
        calculatedMember13.setId("_cm13");
        calculatedMember13.setFormula("[Measures].[Measure-Sum]=10 XOR [Measures].[Measure-Sum]>10");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube logic functions");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember1, calculatedMember2, calculatedMember3, calculatedMember4,
                calculatedMember5, calculatedMember6, calculatedMember7, calculatedMember8, calculatedMember9, calculatedMember10,
                calculatedMember11, calculatedMember12, calculatedMember13 ));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Function Logic");
        catalog.setDescription("Logic function implementations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Function Logic", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(calculatedMember1, "Calculated Member with IIF function", cm1Body, 1, 3, 0, true, 0);

        document(cube, "Cube - logic functions", cubeBody, 1, 8, 0, true, 2);

        return catalog;

    }

}
