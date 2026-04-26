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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.inlinetable;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.database.relational.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.database.source.InlineTableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Row;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RowSet;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.DataSlot;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.InstanceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.02", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube;
    private Catalog catalog;
    private Schema databaseSchema;
    private InlineTableSource query;
    private SumMeasure measure;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Data cube with InlineTable.
            InlineTable represents a table with data embedded directly in the schema definition rather than referencing external database tables.
            InlineTable allows small lookup tables, dimension data, or test data to be included directly in the OLAP schema,
            eliminating the need for separate database tables for static reference data.
            """;

    private static final String databaseSchemaBody = """
            Schema includes InlineTable with data embedded directly in the schema definition.
            InlineTable, named `Fact`, contains two columns: `KEY` and `VALUE`.
            """;

    private static final String queryBody = """
            This example uses a InlineTableQuery, as it directly references the InlineTable table `Fact`.
            """;

    private static final String measureBody = """
            Measure use InlineTable column with sum aggregation.
    """;

    private static final String cubeBody = """
            In this example uses cube with InlineTable as data.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

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
        rowValue2.setDataValue("100.5");

        Row row = RelationalFactory.eINSTANCE.createRow();
        row.getSlot().addAll(List.of(rowValue1, rowValue2));

        InlineTable table = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createInlineTable();
        table.setExtent(RelationalFactory.eINSTANCE.createRowSet());
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        table.getExtent().getOwnedElement().add(row);

        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createInlineTableSource();
        query.setAlias(FACT);
        query.setTable(table);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Measure Inline Table");
        catalog.setDescription("Measure with inline table data");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Inline Table", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Measure-Sum", measureBody, 1, 3, 0, measure, 2),
                        new DocSection("Cube with Inline Table", cubeBody, 1, 4, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
