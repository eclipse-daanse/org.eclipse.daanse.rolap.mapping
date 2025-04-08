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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writebackwithoutdimension;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_for_writeback_without_dimension";
    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String schemaDocumentationTxt = """
            writeback with fact as table with only measure
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
        l2Column.setName("VALUE");
        l2Column.setId("Fact_VALUE");
        l2Column.setType(ColumnType.VARCHAR);
        l2Column.setColumnSize(100);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(valColumn, val1Column, l2Column));
        databaseSchema.getTables().add(table);

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

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregatorType("sum");
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregatorType("sum");
        measure2.setName("Measure2");
        measure2.setId("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        WritebackMeasure writebackMeasure1 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("Measure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Writeback_without_dimension");
        catalog.setDescription("Schema with writeback without dimension");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
