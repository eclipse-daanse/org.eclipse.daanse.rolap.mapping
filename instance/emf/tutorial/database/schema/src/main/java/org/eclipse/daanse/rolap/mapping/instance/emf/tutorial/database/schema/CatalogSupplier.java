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
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema1");
        databaseSchema.setName("foo");
        databaseSchema.setDescription("theDescription");

        Column columnOther = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnOther.setName("theColumn");
        columnOther.setId("_colt1c1");
        columnOther.setType(ColumnType.VARCHAR);

        PhysicalTable tableOther = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableOther.setName("theTable");
        tableOther.setId("_tab1");
        tableOther.getColumns().addAll(List.of(columnOther));
        databaseSchema.getTables().add(tableOther);

        DatabaseSchema databaseSchemaDefault = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchemaDefault.setId("_dbschema2");

        Column columnDefault = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnDefault.setName("theColumn");
        columnDefault.setId("_colt2c1");
        columnDefault.setType(ColumnType.VARCHAR);

        PhysicalTable tableDefault = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableDefault.setName("theTable");
        tableDefault.setId("_tab2");
        tableDefault.getColumns().addAll(List.of(columnDefault));
        databaseSchemaDefault.getTables().add(tableDefault);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Database - Schema");
        catalog.setId("_cat");
        catalog.getDbschemas().add(databaseSchemaDefault);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Introduction into DataBase Schemas", introBody, 1, 0, 0, false, 0);
        document(databaseSchemaDefault, "Schema without name", schemaDefaultBody, 1, 1, 0, true, 0);
        document(databaseSchema, "Schema and attrebutes", schemaOtherBody, 1, 2, 0, true, 0);
        document(catalog, null, catalogBody, 1, 3, 1, true, 0);

        return catalog;

    }

}
