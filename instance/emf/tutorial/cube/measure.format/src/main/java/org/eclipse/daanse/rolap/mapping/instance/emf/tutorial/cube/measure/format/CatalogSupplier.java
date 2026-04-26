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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
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
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.02.04", source = Source.EMF, group = "Measure")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private PhysicalCube cube;
    private Schema databaseSchema;


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

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Format #,##0.00");
        measure1.setColumn(valueColumn);
        measure1.setFormatString("#,##0.00");

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Format #,##0");
        measure2.setColumn(valueColumn);
        measure2.setFormatString("#,##0");

        SumMeasure measure3 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure3.setName("Format #,##0.");
        measure3.setColumn(valueColumn);
        measure3.setFormatString("#,##0.");

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresFormatCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureFormats");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Format");
        catalog.setDescription("Measure formatting options");
        catalog.getCubes().add(cube);


            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Format", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Formats on Measures", cubeBody, 1, 3, 0, cube, 2),
                        new DocSection("Further Formats", formatsBody, 1, 4, 0, null, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
