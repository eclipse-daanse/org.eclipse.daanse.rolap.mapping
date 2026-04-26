/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.intro;

import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
import org.osgi.service.component.annotations.Component;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.01", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String introBody = """
            The foundation of all data analysis is the data to be analyzed.
            In this case, it is stored in a relational database.
            """;

    private static final String catalogBody = """
            The catalog is the primary object in a Daanse server. It serves as a logical grouping to separate different topics in the context of data storage and analysis. The catalog references a single databaseSchema.
            """;

    private static final String schemaBody = """
            A catalog can be structured into databaseSchemas to logically group tables. The same table name can exist in multiple schemas.

            In Daanse, we represent an existing database schema, where the data is stored, as a DatabaseSchema element.
            """;

    private static final String tableBody = """
            A schema contains the tables within a database. There are multiple types of tables, which we will explain in further tutorials.

            The most common type we use is the physical table. The tables defined in a schema do not have to match all tables in the actual database — only those that should be visible for the server. This provides an additional layer of security.
            """;

    private static final String columnBody = """
            A table consists of columns, each with a name and a data type.

            The columns defined in a table do not have to match all columns in the actual database — only those that should be visible for the server. This provides an additional layer of security.
            """;

    private Catalog catalog;
    private Schema schema;
    private Table tableOne;
    private Column columnOne;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }
        schema = RelationalFactory.eINSTANCE.createSchema();
        schema.setName("intro");

        columnOne = RelationalFactory.eINSTANCE.createColumn();
        columnOne.setName("ColumnOne");
        columnOne.setType(SqlSimpleTypes.varcharType(255));

        tableOne = RelationalFactory.eINSTANCE.createTable();
        tableOne.setName("TableOne");
        tableOne.getFeature().add(columnOne);
        schema.getOwnedElement().add(tableOne);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Intro");
        catalog.setDescription("Basic introduction to database schema configuration");
        catalog.setId("_catalog_databaseIntro");
        catalog.getDbschemas().add(schema);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Intro", introBody, 1, 0, 0, null, 0),
                        new DocSection("Catalog", catalogBody, 1, 1, 0, catalog, 0),
                        new DocSection("Schema", schemaBody, 1, 2, 0, schema, 0),
                        new DocSection("Table", tableBody, 1, 3, 0, tableOne, 0),
                        new DocSection("Column", columnBody, 1, 4, 0, columnOne, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
