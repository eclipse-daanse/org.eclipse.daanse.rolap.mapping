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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.base;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MaxMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MinMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.1", source = Source.EMF, group = "Measure")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Data cubes can also have multiple measures when different aggregations are required for a column.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following aggregation functions:
            - SUM – Calculates the sum of the values.
            - MIN – Retrieves the minimum value.
            - MAX – Retrieves the maximum value.
            - AVG – Computes the average of the values.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_measureAggregatorBase");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value");
        measure1.setId("_measure_sumOfValue");
        measure1.setColumn(valueColumn);

        MaxMeasure measure2 = RolapMappingFactory.eINSTANCE.createMaxMeasure();
        measure2.setName("Max of Value");
        measure2.setId("_measure_maxOfValue");
        measure2.setColumn(valueColumn);

        MinMeasure measure3 = RolapMappingFactory.eINSTANCE.createMinMeasure();
        measure3.setName("Min of Value");
        measure3.setId("_measure_minOfValue");
        measure3.setColumn(valueColumn);

        AvgMeasure measure4 = RolapMappingFactory.eINSTANCE.createAvgMeasure();
        measure4.setName("Avg of Value");
        measure4.setId("_measure_avgOfValue");
        measure4.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3, measure4));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setId("_cube_measuresAggregatorsCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureBasicAggregators");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Cube Measure Aggregator Base");
        catalog.setDescription("Basic measure aggregation functions");
        catalog.getCubes().add(cube);

        document(catalog, "Daanse Tutorial - Cube Measure Aggregator Base", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Measure", cubeBody, 1, 3, 0, true, 2);
        return catalog;

    }

}
