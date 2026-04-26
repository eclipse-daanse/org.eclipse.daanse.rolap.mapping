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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.database.inlinetable;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.database.relational.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Row;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RowSet;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.DataSlot;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.InstanceFactory;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "1.03.03", source = Source.EMF, group = "Database")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;


    private static final String introBody = """
            An InlineTable is a named ColumnSet whose data is carried inline in the catalog XMI rather than stored in the database. It is not a physical Table and not a database View: nothing is created in the DB. The server materialises the rows at query time as literal VALUES, derived from the inline data. Use it for small lookup tables or test fixtures.
            """;

    private static final String inlineTableBody = """
            An InlineTable extends cwm::ColumnSet. Its columns are declared via the inherited `feature` reference — each entry is a cwm::Column with a name and type. The data lives under the new `extent` containment, which holds a cwm::RowSet.

            The RowSet's `ownedElement` list contains cwm::Row instances. Each Row carries a `slot` containment (inherited from cwm::Object) of cwm::DataSlot entries. A DataSlot binds one Column (via its `feature` reference) to a literal string value (`dataValue`). All CWM classes here are standard; nothing is Daanse-custom below the InlineTable level.
            """;

    @Override
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        DataSlot rowValue1 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue1.setFeature(keyColumn);
        rowValue1.setDataValue("A");

        DataSlot rowValue2 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue2.setFeature(valueColumn);
        rowValue2.setDataValue("100");

        DataSlot rowValue3 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue3.setFeature(keyColumn);
        rowValue3.setDataValue("B");

        DataSlot rowValue4 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue4.setFeature(valueColumn);
        rowValue4.setDataValue("42");

        Row row = RelationalFactory.eINSTANCE.createRow();
        row.getSlot().addAll(List.of(rowValue1, rowValue2));

        Row row2 = RelationalFactory.eINSTANCE.createRow();
        row2.getSlot().addAll(List.of(rowValue3, rowValue4));

        InlineTable table = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createInlineTable();
        table.setExtent(RelationalFactory.eINSTANCE.createRowSet());
        table.setName("FACT");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        table.getExtent().getOwnedElement().add(row);
        table.getExtent().getOwnedElement().add(row2);

        databaseSchema.getOwnedElement().add(table);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database Inline Table");
        catalog.setDescription("Inline table definitions with row data");
        catalog.setId("_catalog_databaseInlineTable");
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database Inline Table", introBody, 1, 0, 0, null, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
