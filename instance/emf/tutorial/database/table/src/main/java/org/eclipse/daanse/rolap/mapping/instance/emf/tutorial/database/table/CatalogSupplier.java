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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.table;


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
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.View;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.03.01", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private Table table;
    private View table2;
    private Table table3;


    private static final String introBody = """
            There are several Tables Types. The most common are `PhysicalTable`, `ViewTable`, and `SystemTable`. All of this, and thre upcoming table typed can be used to build cubes on.
            """;

    private static final String physicaltableBody = """
            Physical Tables are the most common Tables.  They are used to store data in the Database.
            """;

    private static final String viewtableBody = """
            View Tables ate Database views. Virtual tables that are the result of a query inside the underlaying Database. They are used to simplify complex queries and to hide the complexity.
            """;

    private static final String systenmtableBody = """
            Sytsem Tables are are used and managed by the underlaying Database Management System. They are used to store metadata and other information about the Database itsselfe. Please be carefill when using System Tables, this may cause secutiry issues.
            """;

    @Override
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        column.setName("ColumnOne");
        column.setType(SqlSimpleTypes.Sql99.varcharType());

        table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("TableOne");
        table.getFeature().addAll(List.of(column));
        databaseSchema.getOwnedElement().add(table);

        Column column2 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        column2.setName("ColumnOne");
        column2.setType(SqlSimpleTypes.Sql99.varcharType());

        table2 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createView();
        table2.setName("ViewOne");
        table2.getFeature().addAll(List.of(column2));
        databaseSchema.getOwnedElement().add(table2);

        Column column3 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        column3.setName("ColumnOne");
        column3.setType(SqlSimpleTypes.Sql99.varcharType());

        table3 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table3.setName("TableOne");
        table3.getFeature().addAll(List.of(column3));
        databaseSchema.getOwnedElement().add(table3);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Table");
        catalog.setDescription("Physical table definitions and types");
        catalog.setId("_catalog_databaseTable");
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Table", introBody, 1, 0, 0, null, 0),
                        new DocSection("Physical Table", physicaltableBody, 1, 1, 0, table, 0),
                        new DocSection("View Table", viewtableBody, 1, 2, 0, table2, 0),
                        new DocSection("System Table", systenmtableBody, 1, 3, 0, table3, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
