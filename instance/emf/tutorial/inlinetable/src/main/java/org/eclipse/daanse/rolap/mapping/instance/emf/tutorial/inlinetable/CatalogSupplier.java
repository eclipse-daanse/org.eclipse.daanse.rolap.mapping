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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.inlinetable;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.InlineTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Row;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RowValue;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-03_Cube_InlineTable";
    private static final String CUBE_ONE_MEASURE = "CubeOneMeasure";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A minimal cube based on an inline table

            An inline table is a virtual table which is completely defined within the schema file instead of using an existing database table. Inline tables should only be used exceptionally.
            The <InlineTable> element consists of 2 main components:
            - the column definition (<ColumnDefs>) and
            - the table data held in the table rows (<Rows>).
            Within the <ColumnDefs> tag, every table column is represented by a <ColumnDef> element which defines the column name (name attribute) and  datatype (type attribute).
            Each row containing the data must be defined separately by a <Row> element within the <Rows> tag of the inline table.
            For each table cell within a row, a separate <Value> element must be created which contains the column name in the name attribute and the value as the element's content.

            In this example schema, an inline table named "Fact" is used as the fact table of the cube. Therefore, the <InlineTable> element is used instead of the <Table> element.
            It consists of the string column "KEY" and the numeric column "VALUE" (<ColumnDef> within <ColumnDefs>).
            The table has one row which holds the text "A" in the "KEY" column and the value 100.5 in the "VALUE" column (<Row> & <Value> within <Rows>)
            As the inline table is the only dataset the cube refers to, no external database is needed.
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        RowValue rowValue1 = RelationalDatabaseFactory.eINSTANCE.createRowValue();
        rowValue1.setColumn(keyColumn);
        rowValue1.setValue("A");

        RowValue rowValue2 = RelationalDatabaseFactory.eINSTANCE.createRowValue();
        rowValue1.setColumn(valueColumn);
        rowValue1.setValue("100.5");

        Row row = RelationalDatabaseFactory.eINSTANCE.createRow();
        row.getRowValues().addAll(List.of(rowValue1, rowValue2));

        InlineTable table = RelationalDatabaseFactory.eINSTANCE.createInlineTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        table.getRows().add(row);

        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_ONE_MEASURE);
        cube.setId(CUBE_ONE_MEASURE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("CubeOneMeasureInlineTable");
        schema.setDescription(
                "Schema of a minimal cube consisting of one measurement and based on an virtual inline table");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG);
        catalog.getSchemas().add(schema);
        catalog.getDbschemas().add(databaseSchema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema with a minimal cube");
        catalog.setDocumentation(documentation);
        return catalog;
    }
}
