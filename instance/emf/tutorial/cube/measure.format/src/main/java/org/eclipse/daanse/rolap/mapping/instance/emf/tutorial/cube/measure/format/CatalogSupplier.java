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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.format;

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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.4", source = Source.EMF, group = "Measure")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Depending on the measure, it may be necessary to format its values appropriately.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following Formats functions:
            - `#,##0.00` – value with two decimal places and includes a comma as the thousands separator.
            - `#,##0` – value without decimal places and includes a comma as the thousands separator.
            - `#,##0.` – value with one decimal place and includes a comma as the thousands separator.
            """;

    private static final String formatsBody = """
            General Number Formats
            - `#,##0` – Displays numbers with thousand separators (e.g., `1,234`).
            - `#,##0.00` – Displays numbers with two decimal places and thousand separators (e.g., `1,234.56`).
            - `0` – Displays whole numbers without decimal places (e.g., `1234`).
            - `0.00` – Displays numbers with exactly two decimal places (e.g., `1234.56`).

            Currency Formats
            - `$#,##0.00` – Displays numbers as currency with two decimal places (e.g., `$1,234.56`).
            - `€#,##0.00` – Displays numbers as Euro currency with two decimal places (e.g., `€1,234.56`).
            - `¤#,##0.00` – Uses the system's default currency symbol (e.g., `£1,234.56` or `¥1,234.56`).

            Percentage Formats
            - `0%` – Displays whole-number percentages (e.g., `75%` instead of `0.75`).
            - `0.00%` – Displays percentages with two decimal places (e.g., `75.50%`).
            - `#,##0.00%` – Displays percentages with thousand separators and two decimal places (e.g., `1,234.50%`).

            Scientific Notation
            - `0.00E+00` – Displays numbers in scientific notation (e.g., `1.23E+03` for `1230`).
            - `##0.###E+0` – Displays scientific notation without unnecessary zeros (e.g., `1.23E3`).

            Date & Time Formats
            - `yyyy-MM-dd` – Formats as a standard date (e.g., `2025-03-14`).
            - `MM/dd/yyyy` – Formats as a U.S. date (e.g., `03/14/2025`).
            - `dd.MM.yyyy` – Formats as a European date (e.g., `14.03.2025`).
            - `hh:mm:ss` – Displays time in hours, minutes, and seconds (e.g., `14:30:15`).
            - `yyyy-MM-dd hh:mm:ss` – Displays full date and time (e.g., `2025-03-14 14:30:15`).

            Custom Text & Symbol Formats
            - `"Value: " #,##0` – Adds custom text before a number (e.g., `Value: 1,234`).
            - `#,##0 "units"` – Adds a text suffix (e.g., `1,234 units`).
            - `[Red]#,##0;[Blue]-#,##0` – Colors positive numbers red and negative numbers blue.
            """;
    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_measureFormat");

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
        measure1.setName("Format #,##0.00");
        measure1.setId("_measure_formatTwoDecimals");
        measure1.setColumn(valueColumn);
        measure1.setFormatString("#,##0.00");

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Format #,##0");
        measure2.setId("_measure_formatNoDecimals");
        measure2.setColumn(valueColumn);
        measure2.setFormatString("#,##0");

        SumMeasure measure3 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure3.setName("Format #,##0.");
        measure3.setId("_measure_formatOneDecimal");
        measure3.setColumn(valueColumn);
        measure3.setFormatString("#,##0.");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresFormatCube");
        cube.setId("_cube_measuresFormatCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureFormats");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Measure - Formats");
        catalog.getCubes().add(cube);

        document(catalog, "Measures Formats", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Formats on Measures", cubeBody, 1, 3, 0, true, 2);
        document(cube, "Further Formats", formatsBody, 1, 4, 0, false, 0);

        return catalog;

    }

}
