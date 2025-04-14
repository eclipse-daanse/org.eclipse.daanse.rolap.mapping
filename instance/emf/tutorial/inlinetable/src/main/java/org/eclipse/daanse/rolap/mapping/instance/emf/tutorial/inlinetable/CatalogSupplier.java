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

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        RowValue rowValue1 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue1.setColumn(keyColumn);
        rowValue1.setValue("A");

        RowValue rowValue2 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue2.setColumn(valueColumn);
        rowValue2.setValue("100.5");

        Row row = RolapMappingFactory.eINSTANCE.createRow();
        row.getRowValues().addAll(List.of(rowValue1, rowValue2));

        InlineTable table = RolapMappingFactory.eINSTANCE.createInlineTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        table.getRows().add(row);

        databaseSchema.getTables().add(table);

        InlineTableQuery query = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        query.setId("FactQuery");
        query.setAlias(FACT);
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setId("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_ONE_MEASURE);
        cube.setId(CUBE_ONE_MEASURE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("CubeOneMeasureInlineTable");
        catalog.setDescription(
                "Schema of a minimal cube consisting of one measurement and based on an virtual inline table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }
}
