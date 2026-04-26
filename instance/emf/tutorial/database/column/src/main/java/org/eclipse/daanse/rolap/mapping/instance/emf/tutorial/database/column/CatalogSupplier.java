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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.02.01", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Column columnVarchar;
    private Catalog catalog;
    private Column columnDecimal;
    private Column columnDouble;
    private Column columnInteger;
    private Column columnNumeric;
    private Column columnFloat;
    private Column columnCommon;
    private Column columnReal;


    private static final String introBody = """
            The data of a table are seperated into columns. Each column that should be used, must be explicitly defined. Columns that are not relevant for the analysis can be left out.
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
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        columnCommon = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnCommon.setName("ColumnWithDescription");
        columnCommon.setType(SqlSimpleTypes.Sql99.varcharType());

        columnVarchar = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnVarchar.setName("ColumnVarchar");
        columnVarchar.setType(SqlSimpleTypes.Sql99.varcharType());

        columnDecimal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnDecimal.setName("ColumnDecimal");
        columnDecimal.setType(SqlSimpleTypes.decimalType(18, 4));

        columnNumeric = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnNumeric.setName("ColumnNumeric");
        columnNumeric.setType(SqlSimpleTypes.numericType(18, 4));

        columnFloat = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnFloat.setName("ColumnFloat");
        columnFloat.setType(SqlSimpleTypes.Sql99.floatType());

        columnReal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnReal.setName("ColumnReal");
        columnReal.setType(SqlSimpleTypes.Sql99.realType());

        columnDouble = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnDouble.setName("ColumnDouble");
        columnDouble.setType(SqlSimpleTypes.Sql99.doublePrecisionType());

        columnInteger = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnInteger.setName("ColumnInteger");
        columnInteger.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("TableWithColumnTypes");
        table.getFeature().addAll(List.of(columnCommon, columnVarchar, columnDecimal, columnNumeric, columnFloat,
                columnReal, columnDouble, columnInteger));
        databaseSchema.getOwnedElement().add(table);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Column");
        catalog.setDescription("Database column types and configuration");
        catalog.setId("_catalog_databaseColumnTypes");
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Column", introBody, 1, 0, 0, null, 0),
                        new DocSection("Common column fields", colDescr, 1, 1, 0, columnCommon, 0),
                        new DocSection("Types of Columns", colTypes, 1, 2, 0, null, 0),
                        new DocSection("Varchar", colVarChar, 1, 2, 1, columnVarchar, 0),
                        new DocSection("Decimal", colDecimal, 1, 2, 2, columnDecimal, 0),
                        new DocSection("Numeric", colNumeric, 1, 2, 3, columnNumeric, 0),
                        new DocSection("Float", colFloar, 1, 2, 4, columnFloat, 0),
                        new DocSection("Real", colReal, 1, 2, 5, columnReal, 0),
                        new DocSection("Double", colDouble, 1, 2, 6, columnDouble, 0),
                        new DocSection("Integer", colInteger, 1, 2, 6, columnInteger, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
