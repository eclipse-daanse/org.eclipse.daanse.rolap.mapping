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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.inlinetable;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.2", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Data cube with InlineTable.
            InlineTable represents a table with data embedded directly in the schema definition rather than referencing external database tables.
            InlineTable allows small lookup tables, dimension data, or test data to be included directly in the OLAP schema,
            eliminating the need for separate database tables for static reference data.
            """;

    private static final String databaseSchemaBody = """
            DatabaseSchema includes InlineTable with data embedded directly in the schema definition.
            InlineTable, named `Fact`, contains two columns: `KEY` and `VALUE`.
            """;

    private static final String queryBody = """
            This example uses a InlineTableQuery, as it directly references the InlineTable table `Fact`.
            """;

    private static final String measureBody = """
            Measure use InlineTable column with sum aggregation.
    """;

    private static final String cubeBody = """
            In this example uses cube with InlineTable as data.
            """;

    private static final String catalogDocumentationTxt = """
            A minimal cube based on an inline table
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        RowValue rowValue1 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue1.setColumn(keyColumn);
        rowValue1.setValue("A");

        RowValue rowValue2 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue2.setColumn(valueColumn);
        rowValue2.setValue("100.5");

        Row row = RolapMappingFactory.eINSTANCE.createRow();
        row.getRowValues().addAll(List.of(rowValue1, rowValue2));

        InlineTable table = RolapMappingFactory.eINSTANCE.createInlineTable();
        table.setName(FACT);
        table.setId("_Fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        table.getRows().add(row);

        databaseSchema.getTables().add(table);

        InlineTableQuery query = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        query.setId("FactQuery");
        query.setAlias(FACT);
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setId("_Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with one measure with inline table");
        catalog.setDescription(
                "Schema of a minimal cube consisting of one measurement and based on an virtual inline table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Minimal Cube With Inline Table", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(measure, "Measure-Sum", measureBody, 1, 3, 0, true, 2);
        document(cube, "Cube with Inline Table", cubeBody, 1, 4, 0, true, 2);
        return catalog;
    }
}
