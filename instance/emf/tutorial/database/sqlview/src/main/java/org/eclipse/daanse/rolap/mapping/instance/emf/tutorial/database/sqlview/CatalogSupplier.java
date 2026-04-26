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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.database.relational.DialectSqlView;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.03.02", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private DialectSqlView sqlview;


    private static final String introBody = """
            The `SqlView` Table is a special Table that is used to reference columns of an SQL Query. The differenxe to the View is that the `SqlView` Table is not a view in the Database, but it holds the SQLStatement inside the mapping.
            """;

    private static final String sqlviewBody = """
            The DialectSqlView must contain a SqlStatement that is used to get the data from the Database. The SqlStatement is a simple SQL Query. The DialectSqlView can have multiple SqlStatements for different Dialects. The SqlStatement can alsobe used for multiple dialects. The DialectSqlView must also have the columns defined in the SQL Query.
            """;

    @Override
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        column.setName("ColumnOne");
        column.setType(SqlSimpleTypes.Sql99.varcharType());

        sqlview = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createDialectSqlView();
        sqlview.setName("sqlview");
        sqlview.getFeature().addAll(List.of(column));
        databaseSchema.getOwnedElement().add(sqlview);

        SqlStatement sqlStatement = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("select t.c as ColumnOne from table t");
        sqlStatement.getDialects().add("h2");
        sqlview.getDialectStatements().add(sqlStatement);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database SQL View");
        catalog.setDescription("SQL view definitions and usage");
        catalog.setId("_catalog_databaseSqlView");
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database SQL View", introBody, 1, 0, 0, null, 0),
                        new DocSection("SqlView and SqlStatement", sqlviewBody, 1, 1, 0, sqlview, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
