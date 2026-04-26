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
import org.eclipse.daanse.rolap.mapping.model.database.relational.ExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.02.02", source = Source.EMF, group = "Database")

@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private ExpressionColumn columnSqlExp;


    private static final String introBody = """
            A table’s data consists not only of physical columns that store values but also of another type: the SqlExpressionColumn. This type of column is created dynamically using an SQL expression. The SQL expression is a string executed by the database system to generate the column on demand. Unlike physical columns, the SqlExpressionColumn is not stored in the database but is computed in real time whenever it is requested.
            """;

    private static final String sqlExpColDescr = """
            Since the SqlExpressionColumn is not physically stored in the database, it does not contain any persistent data. Instead, it is dynamically generated based on an SQL expression, which depends on the underlying database management system. Multiple SQL statements can be assigned to a SqlExpressionColumn to support different database systems. This is necessary because databases use different function names and apply varying conventions for quoting column names.

            The dialects attribute of an SqlStatement specifies the target database system. This allows the server to select the appropriate SQL statement for the corresponding database. Additionally, multiple dialects can be defined for a single SQL statement, ensuring compatibility across various database systems.
            """;

    @Override
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        column.setName("column1");

        SqlStatement sqlStatement1 = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement1.setSql("SUBSTRING(column1,1,3)");
        sqlStatement1.getDialects().add("generic");
        sqlStatement1.getDialects().add("mysql");

        SqlStatement sqlStatement2 = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement2.setSql("SUBSTR(column1,1,3)");
        sqlStatement2.getDialects().add("oracle");

        SqlStatement sqlStatement3 = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement3.setSql("substring(column1, 1, 3)");
        sqlStatement3.getDialects().add("h2");

        columnSqlExp = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        columnSqlExp.setName("SqlExpressionColumn");
        columnSqlExp.getSqls().add(sqlStatement1);
        columnSqlExp.getSqls().add(sqlStatement2);
        columnSqlExp.getSqls().add(sqlStatement3);

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("TableWithExpressionColumn");
        table.getFeature().addAll(List.of(column, columnSqlExp));
        databaseSchema.getOwnedElement().add(table);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Expression Column");
        catalog.setDescription("SQL expression columns and computed fields");
        catalog.setId("_catalog_databaseSqlExpressionColumn");
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Expression Column", introBody, 1, 0, 0, null, 0),
                        new DocSection("The Column", sqlExpColDescr, 1, 1, 0, columnSqlExp, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
