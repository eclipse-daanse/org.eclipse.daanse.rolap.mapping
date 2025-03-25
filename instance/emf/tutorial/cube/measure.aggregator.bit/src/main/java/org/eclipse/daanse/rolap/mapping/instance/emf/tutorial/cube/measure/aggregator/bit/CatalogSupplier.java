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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.bit;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.BitAggMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.BitAggType;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.6", source = Source.EMF)
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
            - BIT AGG AND – bit aggregation 'and'.
            - BIT AGG OR  – bit aggregation 'or'.
            - BIT AGG XOR  – bit aggregation 'xor'.
            - BIT AGG NAND – bit aggregation 'nand'.
            - BIT AGG NOR  – bit aggregation 'nor'.
            - BIT AGG NXOR  – bit aggregation 'nxor'.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        BitAggMeasure measure1 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure1.setName("BitAgg AND");
        measure1.setId("_measure1");
        measure1.setAggType(BitAggType.AND);
        measure1.setColumn(valueColumn);

        BitAggMeasure measure2 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure2.setName("BitAgg OR");
        measure2.setId("_measure2");
        measure2.setAggType(BitAggType.OR);
        measure2.setColumn(valueColumn);

        BitAggMeasure measure3 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure3.setName("BitAgg XOR");
        measure3.setId("_measure3");
        measure3.setAggType(BitAggType.XOR);
        measure3.setColumn(valueColumn);

        BitAggMeasure measure4 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure4.setName("BitAgg NAND");
        measure4.setId("_measure4");
        measure4.setAggType(BitAggType.AND);
        measure4.setNot(true);
        measure4.setColumn(valueColumn);

        BitAggMeasure measure5 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure5.setName("BitAgg NOR");
        measure5.setId("_measure2");
        measure5.setAggType(BitAggType.OR);
        measure5.setNot(true);
        measure5.setColumn(valueColumn);

        BitAggMeasure measure6 = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure6.setName("BitAgg NXOR");
        measure6.setId("_measure3");
        measure6.setAggType(BitAggType.XOR);
        measure6.setNot(true);
        measure6.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure1, measure2, measure3, measure4, measure5, measure6));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Cube - Measures and Aggregators");
        catalog.getCubes().add(cube);

        document(catalog, "Multiple Measures and Aggragators", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Measure", cubeBody, 1, 3, 0, true, 2);
        return catalog;

    }

}
