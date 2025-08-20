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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.multiple;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.2", source = Source.EMF, group = "Measure")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Data cubes can have multiple measures to provide different data related to the cube's topic. This is particularly useful when aggregating different data columns within the same cube.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named Fact, contains four columns: KEY, VALUE1, VALUE2, and VALUE3. The KEY column acts as a discriminator, while the VALUE1, VALUE2, and VALUE3 columns contain the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table Fact.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined:
            - The first measure references the `VALUE1` column.
            - The second measure references the `VALUE2` column.
            - The third measure references the `VALUE3` column.
            All measures use sum aggregation.
            """;

    private static final String defaultMeasureBody = """
            Specifying `defaultMeasure` in the `Cube` element allows users to explicitly set a base measure as the default. If `defaultMeasure` is not specified, the first measure in the list is automatically considered the default measure.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_measureMultiple");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column value1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value1Column.setName("VALUE1");
        value1Column.setId("_column_fact_value1");
        value1Column.setType(ColumnType.INTEGER);

        Column value2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value2Column.setName("VALUE2");
        value2Column.setId("_column_fact_value2");
        value2Column.setType(ColumnType.INTEGER);

        Column value3Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value3Column.setName("VALUE3");
        value3Column.setId("_column_fact_value3");
        value3Column.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, value1Column, value2Column, value3Column));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value1");
        measure1.setId("_measure_sumOfValue1");
        measure1.setColumn(value1Column);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Sum of Value2");
        measure2.setId("_measure_sumOfValue2");
        measure2.setColumn(value2Column);

        SumMeasure measure3 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure3.setName("Sum of Value3");
        measure3.setId("_measure_sumOfValue3");
        measure3.setColumn(value3Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MultipleMeasuresCube");
        cube.setId("_cube_multipleMeasuresCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setDefaultMeasure(measure3);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureMultipleMeasures");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Cube Measure Multiple");
        catalog.setDescription("Multiple measures in cubes");
        catalog.getCubes().add(cube);

        document(catalog, "Multiple Measures", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Measures", cubeBody, 1, 3, 0, true, 2);
        document(cube, "DefaultMeasure", defaultMeasureBody, 1, 4, 0, false, 0);
        return catalog;

    }

}
