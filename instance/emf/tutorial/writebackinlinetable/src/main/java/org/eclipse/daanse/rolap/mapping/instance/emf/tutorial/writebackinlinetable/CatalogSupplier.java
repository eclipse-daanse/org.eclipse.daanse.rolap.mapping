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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writebackinlinetable;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {
    private static final String CATALOG = "tutorial_for_writeback_with_fact_InlineTable";
    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String schemaDocumentationTxt = """
            writeback with fact as InlineTable
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column valColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valColumn.setName("VAL");
        valColumn.setId("Fact_VAL");
        valColumn.setType(ColumnType.INTEGER);

        Column val1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        val1Column.setName("VAL1");
        val1Column.setId("Fact_VAL1");
        val1Column.setType(ColumnType.INTEGER);

        Column l2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        l2Column.setName("L2");
        l2Column.setId("Fact_L2");
        l2Column.setType(ColumnType.VARCHAR);
        l2Column.setColumnSize(100);

        RowValue r1V1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1V1.setColumn(valColumn);
        r1V1.setValue("42");
        RowValue r1V2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1V2.setColumn(val1Column);
        r1V2.setValue("21");
        RowValue r1V3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1V3.setColumn(l2Column);
        r1V3.setValue("Level11");
        Row r1 = RolapMappingFactory.eINSTANCE.createRow();
        r1.getRowValues().addAll(List.of(r1V1, r1V2, r1V3));

        Row r2 = RolapMappingFactory.eINSTANCE.createRow();
        RowValue r2V1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2V1.setColumn(valColumn);
        r2V1.setValue("62");
        RowValue r2V2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2V2.setColumn(val1Column);
        r2V2.setValue("31");
        RowValue r2V3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2V3.setColumn(l2Column);
        r2V3.setValue("Level22");
        r2.getRowValues().addAll(List.of(r2V1, r2V2, r2V3));

        Row r3 = RolapMappingFactory.eINSTANCE.createRow();
        RowValue r3V1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r3V1.setColumn(valColumn);
        r3V1.setValue("20");
        RowValue r3V2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r3V2.setColumn(val1Column);
        r3V2.setValue("10");
        RowValue r3V3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r3V3.setColumn(l2Column);
        r3V3.setValue("Level33");
        r3.getRowValues().addAll(List.of(r3V1, r3V2, r3V3));

        Row r4 = RolapMappingFactory.eINSTANCE.createRow();
        RowValue r4V1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r4V1.setColumn(valColumn);
        r4V1.setValue("40");
        RowValue r4V2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r4V2.setColumn(val1Column);
        r4V2.setValue("20");
        RowValue r4V3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r4V3.setColumn(l2Column);
        r4V3.setValue("Level44");
        r4.getRowValues().addAll(List.of(r4V1, r4V2, r4V3));

        Row r5 = RolapMappingFactory.eINSTANCE.createRow();
        RowValue r5V1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r5V1.setColumn(valColumn);
        r5V1.setValue("60");
        RowValue r5V2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r5V2.setColumn(val1Column);
        r5V2.setValue("30");
        RowValue r5V3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r5V3.setColumn(l2Column);
        r5V3.setValue("Level55");
        r5.getRowValues().addAll(List.of(r5V1, r5V2, r5V3));

        InlineTable table = RolapMappingFactory.eINSTANCE.createInlineTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(valColumn, val1Column, l2Column));
        table.getRows().addAll(List.of(r1, r2, r3, r4, r5));
        databaseSchema.getTables().add(table);

        Column l1L1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        l1L1Column.setName("L1");
        l1L1Column.setId("L1_L1");
        l1L1Column.setType(ColumnType.VARCHAR);
        l1L1Column.setColumnSize(100);

        Column l1L2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        l1L2Column.setName("L2");
        l1L2Column.setId("L1_L2");
        l1L2Column.setType(ColumnType.VARCHAR);
        l1L2Column.setColumnSize(100);

        PhysicalTable l1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        l1Table.setName("L1");
        l1Table.setId("L1");
        l1Table.getColumns().addAll(List.of(l1L1Column, l1L2Column));
        databaseSchema.getTables().add(l1Table);

        Column l2L2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        l2L2Column.setName("L2");
        l2L2Column.setId("L2_L2");
        l2L2Column.setType(ColumnType.VARCHAR);
        l2L2Column.setColumnSize(100);

        PhysicalTable l2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        l2Table.setName("L2");
        l2Table.setId("L2");
        l2Table.getColumns().addAll(List.of(l2L2Column));
        databaseSchema.getTables().add(l2Table);

        Column factwbValColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setId("Factwb_VAL");
        factwbValColumn.setType(ColumnType.INTEGER);

        Column factwbVal1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setId("Factwb_VAL1");
        factwbVal1Column.setType(ColumnType.INTEGER);

        Column factwbL2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbL2Column.setName("L2");
        factwbL2Column.setId("factwb_L2");
        factwbL2Column.setType(ColumnType.VARCHAR);
        factwbL2Column.setColumnSize(100);

        Column factwbIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setId("factwb_ID");
        factwbIdColumn.setType(ColumnType.VARCHAR);
        factwbIdColumn.setColumnSize(100);

        Column factwbUserColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setId("factwb_USER");
        factwbUserColumn.setType(ColumnType.VARCHAR);
        factwbUserColumn.setColumnSize(100);

        PhysicalTable factwbTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factwbTable.setName("FACTWB");
        factwbTable.setId("FACTWB");
        factwbTable.getColumns()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getTables().add(factwbTable);

        InlineTableQuery query = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        query.setId("FactQuery");
        query.setTable(table);
        query.setAlias(FACT);

        TableQuery l1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        l1Query.setId("l1TableQuery");
        l1Query.setTable(l1Table);

        TableQuery l2Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        l2Query.setId("l2TableQuery");
        l2Query.setTable(l2Table);

        JoinedQueryElement joinLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinLeft.setKey(l1L2Column);
        joinLeft.setQuery(l1Query);
        JoinedQueryElement joinRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinRight.setKey(l2L2Column);
        joinRight.setQuery(l2Query);

        JoinQuery join = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join.setId("join");
        join.setLeft(joinLeft);
        join.setRight(joinRight);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valColumn);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2");
        measure2.setId("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));
        Level l1Level = RolapMappingFactory.eINSTANCE.createLevel();
        l1Level.setName("L1");
        l1Level.setId("L1Level");
        l1Level.setColumn(l1L1Column);

        Level l2Level = RolapMappingFactory.eINSTANCE.createLevel();
        l2Level.setName("L2");
        l2Level.setId("L2Level");
        l2Level.setColumn(l2L2Column);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(l1L2Column);
        hierarchy.setQuery(join);
        hierarchy.getLevels().addAll(List.of(l1Level, l2Level));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("D1");
        dimensionConnector.setId("D1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(l2Column);

        WritebackAttribute writebackAttribute = RolapMappingFactory.eINSTANCE.createWritebackAttribute();
        writebackAttribute.setDimensionConnector(dimensionConnector);
        writebackAttribute.setColumn(l2Column);

        WritebackMeasure writebackMeasure1 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("Measure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackAttribute().add(writebackAttribute);
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.setWritebackTable(writebackTable);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("tutorial_for_writeback_with_fact_InlineTable");
        catalog.setDescription("Schema with writeback with fact InlineTable");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
