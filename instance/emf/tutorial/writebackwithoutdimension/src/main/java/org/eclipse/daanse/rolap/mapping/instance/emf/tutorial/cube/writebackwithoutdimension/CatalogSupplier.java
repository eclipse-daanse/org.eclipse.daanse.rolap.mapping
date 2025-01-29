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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.writebackwithoutdimension;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String schemaDocumentationTxt = """
            writeback with fact as table with only measure
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column valColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valColumn.setName("VAL");
        valColumn.setId("Fact_VAL");
        valColumn.setType("INTEGER");

        Column val1Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        val1Column.setName("VAL1");
        val1Column.setId("Fact_VAL1");
        val1Column.setType("INTEGER");

        Column l2Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        l2Column.setName("VALUE");
        l2Column.setId("Fact_VALUE");
        l2Column.setType("VARCHAR");
        l2Column.setColumnSize(100);

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(valColumn, val1Column, l2Column));
        databaseSchema.getTables().add(table);

        Column factwbValColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setId("Factwb_VAL");
        factwbValColumn.setType("INTEGER");

        Column factwbVal1Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setId("Factwb_VAL1");
        factwbVal1Column.setType("INTEGER");

        Column factwbL2Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        factwbL2Column.setName("L2");
        factwbL2Column.setId("factwb_L2");
        factwbL2Column.setType("VARCHAR");
        factwbL2Column.setColumnSize(100);

        Column factwbIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setId("factwb_ID");
        factwbIdColumn.setType("VARCHAR");
        factwbIdColumn.setColumnSize(100);

        Column factwbUserColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setId("factwb_USER");
        factwbUserColumn.setType("VARCHAR");
        factwbUserColumn.setColumnSize(100);

        PhysicalTable factwbTable = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        factwbTable.setName("FACTWB");
        factwbTable.setId("FACTWB");
        factwbTable.getColumns()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getTables().add(factwbTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1");
        measure1.setColumn(valColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        WritebackMeasure writebackMeasure1 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure2");
        writebackMeasure1.setColumn(val1Column);

        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Writeback_without_dimension");
        schema.setDescription("Schema with writeback without dimension");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getSchemas().add(schema);
        catalog.getDbschemas().add(databaseSchema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("Catalog with schema with writeback without dimension");
        catalog.setDocumentation(documentation);
        return catalog;
    }

}
