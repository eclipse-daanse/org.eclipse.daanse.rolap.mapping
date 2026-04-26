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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackTable;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.04", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private PhysicalCube cube;
    private Schema databaseSchema;


    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String catalogBody = """
    This tutorial discusses writeback with fact as InlineTable.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the
            - `FACT` table with 3 columns `VAL`, `VAL1`, `VALUE`.
            - `FACTWB` table with 5 columns: `VAL`, `VAL1`, `VALUE`, `ID`, `USER`.
            """;

    private static final String queryBody = """
            The FactQuery is a simple InlineTableSource that selects all columns from the Fact inline table to use in the cube for the measures. InlineTableSource have description and data in catalog
            """;

    private static final String cubeBody = """
            Cube `C` is defined a `FACTWB` WritebackTable configuration with two WritebackMeasures: WbMeasure1 and WbMeasure2.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column valColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valColumn.setName("VAL");
        valColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column val1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        val1Column.setName("VAL1");
        val1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column l2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        l2Column.setName("VALUE");
        l2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(valColumn, val1Column, l2Column));
        databaseSchema.getOwnedElement().add(table);

        Column factwbValColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column factwbVal1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column factwbL2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbL2Column.setName("VALUE");
        factwbL2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column factwbIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column factwbUserColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table factwbTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factwbTable.setName("FACTWB");
        factwbTable.getFeature()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getOwnedElement().add(factwbTable);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setColumn(valColumn);

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        WritebackMeasure writebackMeasure1 = WritebackFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = WritebackFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("Measure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Without Dimension");
        catalog.setDescription("Writeback without dimension constraints");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Without Dimension", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("FactQuery", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Cubec C", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
