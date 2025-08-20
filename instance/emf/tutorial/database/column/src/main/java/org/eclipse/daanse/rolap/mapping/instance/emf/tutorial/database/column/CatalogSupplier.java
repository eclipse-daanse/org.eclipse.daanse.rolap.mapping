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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.column;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "1.2.1", source = Source.EMF, group = "Database")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            The data of a table are seperated into columns. Each column that should be used, must explicitly defines. Columns that are not relevant for the analysis can be left out.
            """;

    private static final String colDescr = """
            A Column has the fields `id,` `name`, `description` and `nullable`. Id is used to reference the column. Name is the name of the Column in the Database-table. Description is a human-readable textual description of the column. Nullable is an boolean flag, that indicates if the column can contain null values.
            """;

    private static final String colDecimal = """
            DECIMAL type represents Fixed-point number with exact precision (p) and scale (s), used for precise calculations (e.g., financial data).

            relevant attributes are:

            The DecimalDigits field represents the number of digits to the right of the decimal point for numeric columns in a database. It is typically used for DECIMAL, NUMERIC, FLOAT, REAL, and DOUBLE data types.

            The NumPrecRadix field represents the numeric precision radix (or base) used for numeric data types. It indicates whether the precision (COLUMN_SIZE) of a column is based on base 10 (decimal) or base 2 (binary).
            """;

    private static final String colVarChar = """
            The ColumnSize field in represents the maximum width or precision of a column, depending on its data type. It provides essential information about how much data a column can store
            """;

    private static final String colNumeric = """
            Numeric are the same as DECIMAL, but some databases treat it as stricter in enforcing precision.""";

    private static final String colFloar = """
            Float Approximate floating-point number, implementation-dependent precision, can introduce rounding errors""";

    private static final String colReal = """
            REAL are Single-precision (32-bit) floating-point number, less precise than DOUBLE.""";

    private static final String colDouble = """
            DOUBLE (or DOUBLE PRECISION) – Double-precision (64-bit) floating-point number, more accurate than REAL.""";

    private static final String colInteger = """
            INTEGER are the whole number type, typically 32-bit, used for exact, non-decimal values.""";

    private static String colTypes = """
            In database tables, columns can have various data types to store data efficiently. These types come with additional attributes that provide more detailed descriptions and constraints.""";

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_columnTypes");

        Column columnCommon = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnCommon.setName("ColumnWithDescription");
        columnCommon.setId("_column_tableWithColumnTypes_columnWithDescription");
        columnCommon.setNullable(true);
        columnCommon.setDescription("Non nullable Column with description");
        columnCommon.setType(ColumnType.VARCHAR);

        Column columnVarchar = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnVarchar.setName("ColumnVarchar");
        columnVarchar.setId("_column_tableWithColumnTypes_columnVarchar");
        columnVarchar.setColumnSize(255);
        columnVarchar.setType(ColumnType.VARCHAR);

        Column columnDecimal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnDecimal.setName("ColumnDecimal");
        columnDecimal.setId("_column_tableWithColumnTypes_columnDecimal");
        columnDecimal.setDecimalDigits(2);
        columnDecimal.setNumPrecRadix(3);
        columnDecimal.setType(ColumnType.DECIMAL);

        Column columnNumeric = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnNumeric.setName("ColumnNumeric");
        columnNumeric.setId("_column_tableWithColumnTypes_columnNumeric");
        columnNumeric.setType(ColumnType.NUMERIC);

        Column columnFloat = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFloat.setName("ColumnFloat");
        columnFloat.setId("_column_tableWithColumnTypes_columnFloat");
        columnFloat.setType(ColumnType.FLOAT);

        Column columnReal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnReal.setName("ColumnReal");
        columnReal.setId("_column_tableWithColumnTypes_columnReal");
        columnReal.setType(ColumnType.REAL);

        Column columnDouble = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnDouble.setName("ColumnDouble");
        columnDouble.setId("_column_tableWithColumnTypes_columnDouble");
        columnDouble.setType(ColumnType.DOUBLE);

        Column columnInteger = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnInteger.setName("ColumnInteger");
        columnInteger.setId("_column_tableWithColumnTypes_columnInteger");
        columnInteger.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("TableWithColumnTypes");
        table.setId("_table_tableWithColumnTypes");
        table.getColumns().addAll(List.of(columnCommon, columnVarchar, columnDecimal, columnNumeric, columnFloat,
                columnReal, columnDouble, columnInteger));
        databaseSchema.getTables().add(table);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Column");
        catalog.setDescription("Database column types and configuration");
        catalog.setId("_catalog_databaseColumnTypes");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Introduction into Columns", introBody, 1, 0, 0, false, 0);
        document(columnCommon, "Common column fields", colDescr, 1, 1, 0, true, 0);
        document(catalog, "Types of Columns", colTypes, 1, 2, 0, false, 0);

        document(columnVarchar, "Varchar", colVarChar, 1, 2, 1, true, 0);
        document(columnDecimal, "Decimal", colDecimal, 1, 2, 2, true, 0);
        document(columnNumeric, "Numeric", colNumeric, 1, 2, 3, true, 0);
        document(columnFloat, "Float", colFloar, 1, 2, 4, true, 0);
        document(columnReal, "Real", colReal, 1, 2, 5, true, 0);
        document(columnDouble, "Double", colDouble, 1, 2, 6, true, 0);
        document(columnInteger, "Integer", colInteger, 1, 2, 6, true, 0);

        return catalog;

    }

}
