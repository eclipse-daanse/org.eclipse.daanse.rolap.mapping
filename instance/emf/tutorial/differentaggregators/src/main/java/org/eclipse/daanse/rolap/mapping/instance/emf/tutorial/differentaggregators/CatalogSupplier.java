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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.differentaggregators;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-04-01_Cube_Measure_MeasuresWithDifferentAggregators ";
    private static final String CUBE_NAME = "CubeDifferentAggregators";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A minimal cube with one measurement column and different aggregators

            Measurement values in a data cube can be aggregated differently, depending on the chosen aggregation function in the aggregator attribute of the <Measure> tag. Available aggregators are:
            - sum: summation of numeric values
            - count: number of values
            - min: minimal value
            - max: maximal value
            - avg: average of numeric values
            - distinct-count: number of different values (without duplicate values)

            In this example cube every measure traces back to the "VALUE" column of the "Fact" database table, but uses another aggregator.
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measureSum = RolapMappingFactory.eINSTANCE.createMeasure();
        measureSum.setAggregator(MeasureAggregator.SUM);
        measureSum.setName("Measure-Sum");
        measureSum.setId("Measure-Sum");
        measureSum.setColumn(valueColumn);

        Measure measureMin = RolapMappingFactory.eINSTANCE.createMeasure();
        measureMin.setAggregator(MeasureAggregator.MIN);
        measureMin.setName("Measure-Min");
        measureMin.setId("Measure-Min");
        measureMin.setColumn(valueColumn);

        Measure measureMax = RolapMappingFactory.eINSTANCE.createMeasure();
        measureMax.setAggregator(MeasureAggregator.MAX);
        measureMax.setName("Measure-Max");
        measureMax.setId("Measure-Max");
        measureMax.setColumn(valueColumn);

        Measure measureAvg = RolapMappingFactory.eINSTANCE.createMeasure();
        measureAvg.setAggregator(MeasureAggregator.AVG);
        measureAvg.setName("Measure-Avg");
        measureAvg.setId("Measure-Avg");
        measureAvg.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureMin, measureMax, measureAvg));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setId(CUBE_NAME);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("01-03_Minimal_Cube_With_One_Measure_And_Different_Aggregators");
        catalog.setDescription("Schema of a minimal cube with one measurement column and different aggregators");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;

    }

}
