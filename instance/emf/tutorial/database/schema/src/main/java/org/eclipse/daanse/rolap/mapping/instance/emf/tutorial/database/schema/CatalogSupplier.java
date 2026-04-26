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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.schema;


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
@MappingInstance(kind = Kind.TUTORIAL, number = "1.03", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private Schema databaseSchemaDefault;
    private Schema databaseSchema;


    private static final String introBody = """
            The purpose of database schemas is to organize tables into logical groups.

            In the Daanse server, schemas can be referenced in the catalog. None or only a subset of the existing database schemas may be referenced. This reference is only required if the Daanse server needs to expose the schemas and their tables via an API, such as XMLA.
            """;

    private static final String schemaDefaultBody = """
            The name of a database schema is optional and can be left empty. In such cases, the server will query the default schema of the underlying database.
            """;

    private static final String schemaOtherBody = """
            It is more secure to explicitly define the schema using the `name` attribute. To provide a clearer description of the schema's contents, you can use the `description` attribute.
            """;

    private static final String catalogBody = "Schema can be refernced in the catalog. You can see this by checking the `dbSchema attribute` in the catalog.";

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        databaseSchema.setName("foo");

        Column columnOther = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnOther.setName("theColumn");
        columnOther.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableOther = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableOther.setName("theTable");
        tableOther.getFeature().addAll(List.of(columnOther));
        databaseSchema.getOwnedElement().add(tableOther);

        databaseSchemaDefault = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column columnDefault = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnDefault.setName("theColumn");
        columnDefault.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableDefault = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableDefault.setName("theTable");
        tableDefault.getFeature().addAll(List.of(columnDefault));
        databaseSchemaDefault.getOwnedElement().add(tableDefault);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Schema");
        catalog.setDescription("Database schema configuration and organization");
        catalog.setId("_catalog_databaseSchema");
        catalog.getDbschemas().add(databaseSchemaDefault);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Schema", introBody, 1, 0, 0, null, 0),
                        new DocSection("Schema without name", schemaDefaultBody, 1, 1, 0, databaseSchemaDefault, 0),
                        new DocSection("Schema and attrebutes", schemaOtherBody, 1, 2, 0, databaseSchema, 0),
                        new DocSection(null, catalogBody, 1, 3, 1, catalog, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
