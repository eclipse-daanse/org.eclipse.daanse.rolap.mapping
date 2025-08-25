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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.intro;

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

@MappingInstance(kind = Kind.TUTORIAL, number = "1.1", source = Source.EMF, group = "Database")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            The foundation of all data analysis is the data to be analyzed.
            In this case, it is stored in a relational database.
            """;

    private static final String catalogBody = """
            The catalog is the primary object in a Daanse server. It serves as a logical grouping to separate different topics in the context of data storage and analysis. The catalog references a single databaseSchema.
            """;

    private static final String schemaBody = """
            A catalog can be structured into databaseSchemas to logically group tables. The same table name can exist in multiple schemas.

            In Daanse, we represent the database schema, where the data is stored, as a DatabaseSchema element.
            """;

    private static final String tableBody = """
            A schema contains the tables within a database. There are multiple types of tables, which we will explain in further tutorials.

            The most common type we use is the physical table. The tables defined in a schema do not have to match all tables in the actual database — only those that should be visible for the server. This provides an additional layer of security.
            """;

    private static final String columnBody = """
            A table consists of columns, each with a name and a data type.

            The columns defined in a table do not have to match all columns in the actual database — only those that should be visible for the server. This provides an additional layer of security.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_intro");

        Column column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        column.setName("ColumnOne");
        column.setId("_column_tableOne_columnOne");
        column.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("TableOne");
        table.setId("_table_tableOne");
        table.getColumns().addAll(List.of(column));
        databaseSchema.getTables().add(table);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Intro");
        catalog.setDescription("Basic introduction to database schema configuration");
        catalog.setId("_catalog_databaseIntro");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Database Intro", introBody, 1, 0, 0, false, 0);
        document(catalog, "Catalog", catalogBody, 1, 1, 0, true, 0);
        document(databaseSchema, "Schema", schemaBody, 1, 2, 0, true, 0);
        document(table, "Table", tableBody, 1, 3, 0, true, 0);
        document(column, "Column", columnBody, 1, 4, 0, true, 0);

        return catalog;

    }

}
