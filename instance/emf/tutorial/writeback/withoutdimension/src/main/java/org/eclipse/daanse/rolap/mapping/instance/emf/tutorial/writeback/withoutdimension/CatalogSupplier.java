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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.withoutdimension;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.5.4", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String catalogBody = """
    This tutorial discusses writeback with fact as InlineTable.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the
            FACT table with 3 columns VAL, VAL1, VALUE.
            FACTWB table with 5 columns: VAL, VAL1, VALUE, ID, USER.
            """;

    private static final String queryBody = """
            The FactQuery is a simple InlineTableQuery that selects all columns from the Fact inline table to use in the cube for the measures. InlineTableQuery have description and data in catalog
            """;

    private static final String cubeBody = """
            Cube C is defined a FACTWB WritebackTable configuration with two WritebackMeasures: WbMeasure1 and WbMeasure2.
            """;

    private static final String schemaDocumentationTxt = """
            writeback with fact as table with only measure
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_writeback_withoutdimension");

        Column valColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valColumn.setName("VAL");
        valColumn.setId("_column_fact_val");
        valColumn.setType(ColumnType.INTEGER);

        Column val1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        val1Column.setName("VAL1");
        val1Column.setId("_column_fact_val1");
        val1Column.setType(ColumnType.INTEGER);

        Column l2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        l2Column.setName("VALUE");
        l2Column.setId("_column_fact_value");
        l2Column.setType(ColumnType.VARCHAR);
        l2Column.setColumnSize(100);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(valColumn, val1Column, l2Column));
        databaseSchema.getTables().add(table);

        Column factwbValColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setId("_column_factwb_val");
        factwbValColumn.setType(ColumnType.INTEGER);

        Column factwbVal1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setId("_column_factwb_val1");
        factwbVal1Column.setType(ColumnType.INTEGER);

        Column factwbL2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbL2Column.setName("VALUE");
        factwbL2Column.setId("_column_factwb_value");
        factwbL2Column.setType(ColumnType.VARCHAR);
        factwbL2Column.setColumnSize(100);

        Column factwbIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setId("_column_factwb_id");
        factwbIdColumn.setType(ColumnType.VARCHAR);
        factwbIdColumn.setColumnSize(100);

        Column factwbUserColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setId("_column_factwb_user");
        factwbUserColumn.setType(ColumnType.VARCHAR);
        factwbUserColumn.setColumnSize(100);

        PhysicalTable factwbTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factwbTable.setName("FACTWB");
        factwbTable.setId("_table_factwb");
        factwbTable.getColumns()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getTables().add(factwbTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_tableQuery_FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_sumMeasure_Measure1");
        measure1.setColumn(valColumn);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2");
        measure2.setId("_sumMeasure_Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        WritebackMeasure writebackMeasure1 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("WbMeasure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("WbMeasure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_physicalCube_C");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with Writeback without dimension");
        catalog.setDescription("Schema with writeback without dimension");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube with writeback without dimension", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "FactQuery", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cubec C ", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
