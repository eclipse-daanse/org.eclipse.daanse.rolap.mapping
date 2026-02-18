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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.inlinetable;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.Row;
import org.eclipse.daanse.rolap.mapping.model.RowValue;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "1.03.03", source = Source.EMF, group = "Database")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            The Inline Table is a special Table that is used to hold the data in the mapping. The Inline Table is not a table in the Database, but because it holds the data it can act as a Table. The Server must create at runtime Inline Statements of this Table.
            """;

    private static final String inlineTableBody = """
            The InlineTable is a virtual table. The table and data does not exist in the database and will not be inserted. The inlinetable stores the data in the Row and RowValue elements completely in its definition. The RowValue elements are used to store the values of the columns. The Row element is used to store the values. the RowValue can store the atomic data and the reference to the columns. The InlineTable can have multiple Rows and columns. Inline tables should only be used exceptionally.
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_inlineTable");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        RowValue rowValue1 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue1.setColumn(keyColumn);
        rowValue1.setValue("A");

        RowValue rowValue2 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue2.setColumn(valueColumn);
        rowValue2.setValue("100");

        RowValue rowValue3 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue3.setColumn(keyColumn);
        rowValue3.setValue("B");

        RowValue rowValue4 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue4.setColumn(valueColumn);
        rowValue4.setValue("42");

        Row row = RolapMappingFactory.eINSTANCE.createRow();
        row.getRowValues().addAll(List.of(rowValue1, rowValue2));

        Row row2 = RolapMappingFactory.eINSTANCE.createRow();
        row2.getRowValues().addAll(List.of(rowValue3, rowValue4));

        InlineTable table = RolapMappingFactory.eINSTANCE.createInlineTable();
        table.setName("FACT");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        table.getRows().add(row);
        table.getRows().add(row2);

        databaseSchema.getTables().add(table);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Inline Table");
        catalog.setDescription("Inline table definitions with row data");
        catalog.setId("_catalog_databaseInlineTable");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Database Inline Table", introBody, 1, 0, 0, false, 0);
        document(table, "InlineTable, Row and RowValue", inlineTableBody, 1, 1, 0, true, 2);

        return catalog;

    }

}
