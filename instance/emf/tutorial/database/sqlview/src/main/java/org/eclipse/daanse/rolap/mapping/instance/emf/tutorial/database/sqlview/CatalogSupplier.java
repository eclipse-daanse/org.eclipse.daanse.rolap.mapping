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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.sqlview;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlView;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            The SqlView Table is a special Table that is used to reference columns of an SQL Query. The differenxe to the ViewTable is that the SqlView Table is not a view in the Database, but it holds the SQLStatement inside the mapping.
            """;

    private static final String sqlviewBody = """
            The SqlView must contain a SqlStatement that is used to get the data from the Database. The SqlStatement is a simple SQL Query. The SqlView can have multiple SqlStatements for different Dialects. The SqlStatement can alsobe used for multiple dialects. The SqlView must also have the columns defined in the SQL Query.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column column = RolapMappingFactory.eINSTANCE.createColumn();
        column.setName("ColumnOne");
        column.setId("_col");
        column.setType(ColumnDataType.VARCHAR);

        SqlView sqlview = RolapMappingFactory.eINSTANCE.createSqlView();
        sqlview.setName("sqlview");
        sqlview.setId("_tab");
        sqlview.getColumns().addAll(List.of(column));
        databaseSchema.getTables().add(sqlview);

        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("select t.c as ColumnOne from table t");
        sqlStatement.getDialects().add("h2");
        sqlview.getSqlStatements().add(sqlStatement);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("CatalogOne");
        catalog.setId("_cat");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Special Table -  SqlView", introBody, 1, 0, 0, false, 0);
        document(sqlview, "SqlView and SqlStatement", sqlviewBody, 1, 1, 0, true, 2);

        return catalog;

    }

}
