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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.formatter.cell;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.11.1", source = Source.EMF, group = "Formatter") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE_NAME = "CubeOneNumericMeasureDifferentDataTypes";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Data cube with measures CellFormatter.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`.
            The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String cellFormatterBody = """
            Specialized formatter for controlling the presentation of cell values in analytical grids.
            Cell formatter use reference to class formatter mondrian.rolap.format.CellFormatterImpl implemented CellFormatter interface
    """;

    private static final String measureBody = """
            Measure with CellFormatter. measure reference the `VALUE` column and use the following SUM aggregation function.
    """;

    private static final String cubeBody = """
            In this example, measure with CellFormatter. measure reference the `VALUE` column and use the following SUM aggregation function.
            """;

    private static final String schemaDocumentationTxt = """
            A minimal cube with CellFormatter with class name
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_cell");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_" + FACT.toLowerCase());
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        CellFormatter cellFormatter = RolapMappingFactory.eINSTANCE.createCellFormatter();
        cellFormatter.setId("_cellFormatter");
        cellFormatter.setRef("mondrian.rolap.format.CellFormatterImpl");

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_measure_Measure1");
        measure1.setColumn(valueColumn);
        measure1.setFormatString("Standard");
        measure1.setCellFormatter(cellFormatter);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setId("_cube_cellFormatter");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Formatter Cell");
        catalog.setDescription("Cell formatter configurations");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        document(catalog, "Minimal Cube With Measures CellFormatter", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cellFormatter, "CellFormatter", cellFormatterBody, 1, 3, 0, true, 2);
        document(measure1, "Measure1", measureBody, 1, 4, 0, true, 2);
        document(cube, "Cube with Measures CellFormatter", cubeBody, 1, 5, 0, true, 2);

        return catalog;
    }

}
