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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.expressioncolumn;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "1.2.2", source = Source.EMF, group = "Database")

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            A table’s data consists not only of physical columns that store values but also of another type: the SqlExpressionColumn. This type of column is created dynamically using an SQL expression. The SQL expression is a string executed by the database system to generate the column on demand. Unlike physical columns, the SqlExpressionColumn is not stored in the database but is computed in real time whenever it is requested.
            """;

    private static final String sqlExpColDescr = """
            Since the SqlExpressionColumn is not physically stored in the database, it does not contain any persistent data. Instead, it is dynamically generated based on an SQL expression, which depends on the underlying database management system. Multiple SQL statements can be assigned to a SqlExpressionColumn to support different database systems. This is necessary because databases use different function names and apply varying conventions for quoting column names.

            The dialects attribute of an SqlStatement specifies the target database system. This allows the server to select the appropriate SQL statement for the corresponding database. Additionally, multiple dialects can be defined for a single SQL statement, ensuring compatibility across various database systems.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_expressionColumn");

        Column column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        column.setName("column1");
        column.setId("_column_tableWithExpressionColumn_column1");

        SqlStatement sqlStatement1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement1.setSql("column1 + column1");
        sqlStatement1.getDialects().add("generic");
        sqlStatement1.getDialects().add("mysql");

        SqlStatement sqlStatement2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement2.setSql("column1 + column1 + column1");
        sqlStatement2.getDialects().add("h2");

        SQLExpressionColumn columnSqlExp = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        columnSqlExp.setName("SqlExpressionColumn");
        columnSqlExp.getSqls().add(sqlStatement1);
        columnSqlExp.getSqls().add(sqlStatement2);
        columnSqlExp.setId("_column_tableWithExpressionColumn_sqlExpressionColumn");

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("TableWithExpressionColumn");
        table.setId("_table_tableWithExpressionColumn");
        table.getColumns().addAll(List.of(column, columnSqlExp));
        databaseSchema.getTables().add(table);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Expression Column");
        catalog.setDescription("SQL expression columns and computed fields");
        catalog.setId("_catalog_databaseSqlExpressionColumn");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Introduction into SqlExpressionColumn", introBody, 1, 0, 0, false, 0);
        document(columnSqlExp, "The Column", sqlExpColDescr, 1, 1, 0, true, 0);

        return catalog;

    }

}
