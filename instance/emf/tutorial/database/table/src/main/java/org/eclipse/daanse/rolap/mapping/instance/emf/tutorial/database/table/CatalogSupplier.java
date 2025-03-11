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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SystemTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ViewTable;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
                        ther are several Tables Types. The most common are PhysicalTable, ViewTable, and SystemTable. All of this, and thre upcoming table typed can be used to build cubes on.
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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        column.setName("ColumnOne");
        column.setId("_col");
        column.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("TableOne");
        table.setId("_tab");
        table.getColumns().addAll(List.of(column));
        databaseSchema.getTables().add(table);

        Column column2 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        column2.setName("ColumnOne");
        column2.setId("_col");
        column2.setType(ColumnType.VARCHAR);

        ViewTable table2 = RolapMappingFactory.eINSTANCE.createViewTable();
        table2.setName("ViewOne");
        table2.setId("_tab");
        table2.getColumns().addAll(List.of(column2));
        databaseSchema.getTables().add(table2);

        Column column3 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        column3.setName("ColumnOne");
        column3.setId("_col");
        column3.setType(ColumnType.VARCHAR);

        SystemTable table3 = RolapMappingFactory.eINSTANCE.createSystemTable();
        table3.setName("TableOne");
        table3.setId("_tab");
        table3.getColumns().addAll(List.of(column3));
        databaseSchema.getTables().add(table3);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Database - Table");
        catalog.setId("_cat");
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Tables and TableTypes", introBody, 1, 0, 0, false, 0);
        document(table, "Physical Table", physicaltableBody, 1, 1, 0, true, 0);
        document(table2, "View Table", viewtableBody, 1, 2, 0, true, 0);
        document(table3, "System Table", systenmtableBody, 1, 3, 0, true, 0);

        return catalog;

    }

}
