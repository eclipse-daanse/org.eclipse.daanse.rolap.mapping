/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.pojo.tutorials;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableColumnDefinitionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowCellMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowMappingMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SumMeasureMappingImpl;

public final class MinimalCubeWithInlineTables extends SchemaMappingImpl {

    private final static String description = "Schema of a minimal cube consisting of one measurement and based on an" +
        " virtual inline table";
    private final static String documentationValue = """
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
    private final static String name = "Minimal_Cube_With_Inline_Tables";
    private final static DocumentationMappingImpl documentation = new DocumentationMappingImpl(documentationValue);
    private final static PhysicalCubeMappingImpl cube = new PhysicalCubeMappingImpl();
    private final static InlineTableColumnDefinitionMappingImpl columnDef1 =
        new InlineTableColumnDefinitionMappingImpl();
    private final static InlineTableColumnDefinitionMappingImpl columnDef2 =
        new InlineTableColumnDefinitionMappingImpl();
    private final static InlineTableRowMappingMappingImpl row = new InlineTableRowMappingMappingImpl();
    private final static InlineTableQueryMappingImpl table = new InlineTableQueryMappingImpl();
    private final static MeasureGroupMappingImpl measureGroup = new MeasureGroupMappingImpl();
    private final static SumMeasureMappingImpl measure = new SumMeasureMappingImpl();

    static {
        columnDef1.setName("KEY");
        columnDef1.setType("String");
        columnDef2.setName("VALUE");
        columnDef2.setType("Numeric");
        row.setCells(List.of(new InlineTableRowCellMappingImpl("A", "KEY"),
            new InlineTableRowCellMappingImpl("100.5", "VALUE")));
        table.setAlias("Fact");
        table.setColumnDefinitions(List.of(columnDef1, columnDef2));
        table.setRows(List.of(row));
        measure.setName("Measure-Sum");
        measure.setColumn("VALUE");
        measureGroup.setMeasures(List.of(measure));
        cube.setName("CubeOneMeasureInlineTable");
        cube.setQuery(table);
        cube.setMeasureGroups(List.of(measureGroup));
    }

    @Override
    public DocumentationMapping getDocumentation() {
        return documentation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<? extends CubeMapping> getCubes() {
        return List.of(cube);
    }

}
