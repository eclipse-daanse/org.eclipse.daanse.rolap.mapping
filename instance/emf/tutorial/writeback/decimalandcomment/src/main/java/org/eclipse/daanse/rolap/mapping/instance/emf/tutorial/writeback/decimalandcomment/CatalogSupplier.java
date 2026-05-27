/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.decimalandcomment;

import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;

import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;

import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackFactory;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackTable;

import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.TextAggMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.WritebackMeasure;

import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;

import org.osgi.service.component.annotations.Component;

@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.07", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";
    private static final String FACTWB = "FACTWB";
    private static final String PRODUCT = "PRODUCT";

    private Catalog catalog;
    private Schema databaseSchema;
    private PhysicalCube cube;
    private TableSource factSource;
    private TableSource productSource;
    private StandardDimension dimension;
    private ExplicitHierarchy hierarchy;
    private Level level;
    private SumMeasure amountMeasure;
    private TextAggMeasure commentsMeasure;
    private WritebackTable writebackTable;

    private static final String catalogBody = """
            **Daanse Tutorial — Writeback with a DECIMAL amount and a text comment.**

            The minimal writeback example that uses a fixed-precision
            **decimal** numeric type (not integer) for the writeable amount
            column, paired with a free-text comment column. One single-level
            dimension (`Product`) supplies the slicing key.

            This is the simplest realistic shape for a planning/forecasting
            cube where users type currency amounts (which require decimal
            precision) along with explanatory notes.

            The cube exposes:
            - **`Amount`** — `SumMeasure` over a `DECIMAL(18,2)` `AMOUNT`
              column. Currency-formatted.
            - **`Comments`** — `TextAggMeasure` over a `VARCHAR` `COMMENT`
              column with separator `" | "`.

            Both measures are writeable via the `FACTWB` writeback table.
            The runtime detects the text bind type for the `Comments`
            measure from its `ListAggAggregator`; the numeric `Amount`
            measure follows the standard allocation path.
            """;

    private static final String databaseSchemaBody = """
            Three tables:

            - **`FACT`** — `PRODUCT` (VARCHAR), `AMOUNT` (DECIMAL),
              `COMMENT` (VARCHAR). One row per posting.
            - **`PRODUCT`** — `KEY` (VARCHAR), `NAME` (VARCHAR). The single
              dimension table; the fact joins on
              `FACT.PRODUCT = PRODUCT.KEY`.
            - **`FACTWB`** — same dimensional + measure columns as `FACT`,
              plus `ID` (VARCHAR, UUID written by the engine) and `USER`
              (VARCHAR, session user).
            """;

    private static final String dimensionBody = """
            Single dimension `Product` with one `ExplicitHierarchy` and a
            single level. The level keys on `PRODUCT.KEY` and shows
            `PRODUCT.NAME` as the displayed member name.
            """;

    private static final String measuresBody = """
            - **`Amount`** — `SumMeasure(FACT.AMOUNT)`. Note that the
              underlying column is declared `DECIMAL(18, 2)` rather than
              `INTEGER`, so the writeback binding preserves fractional
              precision (e.g. tax amounts, currency).
            - **`Comments`** — `TextAggMeasure(FACT.COMMENT, separator = " | ")`.
              On read the aggregator concatenates every matching comment;
              on write the runtime takes the text short-path and inserts a
              single row with the typed string.
            """;

    private static final String writebackBody = """
            ```
            WritebackTable("FACTWB")                     [database.writeback]
              ├── WritebackAttribute(Product) → PRODUCT  [database.writeback]
              ├── WritebackMeasure("Amount")  → AMOUNT   [olap.cube.measure]
              └── WritebackMeasure("Comments") → COMMENT [olap.cube.measure]
            ```

            *Numeric write.* `Cell.setValue([Measures].[Amount], 19.95, EQUAL_ALLOCATION, ...)`
            → the runtime spreads `19.95` across the cell's leaves per the
            allocation policy and emits the resulting rows with
            `AMOUNT` populated as a decimal.

            *Text write.* `Cell.setValue([Measures].[Comments], "promo Q3", ...)`
            → the runtime recognises the `TextAggMeasure` target, bypasses
            allocation, and emits exactly one row with `COMMENT = "promo Q3"`
            and `AMOUNT = NULL`. Every writeback row gets `ID = UUID()`
            and `USER = sessionUser`.
            """;

    private static final String cubeBody = """
            One physical cube `C` over `FACT` with one `DimensionConnector`
            on `Product`, one `MeasureGroup` holding `Amount` and
            `Comments`, and the `FACTWB` writeback table.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column factProductColumn = createColumn("PRODUCT", SqlSimpleTypes.Sql99.varcharType());
        Column factAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.decimalType(18, 2));
        Column factCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Table factTable = createTable(FACT, List.of(factProductColumn, factAmountColumn, factCommentColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column productKeyColumn = createColumn("KEY", SqlSimpleTypes.Sql99.varcharType());
        Column productNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Table productTable = createTable(PRODUCT, List.of(productKeyColumn, productNameColumn));
        databaseSchema.getOwnedElement().add(productTable);

        Column wbProductColumn = createColumn("PRODUCT", SqlSimpleTypes.Sql99.varcharType());
        Column wbAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.decimalType(18, 2));
        Column wbCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Table writebackPhysicalTable = createTable(FACTWB,
                List.of(wbProductColumn, wbAmountColumn, wbCommentColumn, wbIdColumn, wbUserColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        factSource = SourceFactory.eINSTANCE.createTableSource();
        factSource.setTable(factTable);

        productSource = SourceFactory.eINSTANCE.createTableSource();
        productSource.setTable(productTable);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Product");
        level.setColumn(productKeyColumn);
        level.setNameColumn(productNameColumn);
        level.setUniqueMembers(true);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Product");
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("All Products");
        hierarchy.setPrimaryKey(productKeyColumn);
        hierarchy.setSource(productSource);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Product");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Product");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(factProductColumn);

        amountMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        amountMeasure.setName("Amount");
        amountMeasure.setColumn(factAmountColumn);
        amountMeasure.setFormatString("#,##0.00");

        commentsMeasure = MeasureFactory.eINSTANCE.createTextAggMeasure();
        commentsMeasure.setName("Comments");
        commentsMeasure.setColumn(factCommentColumn);
        commentsMeasure.setSeparator(" | ");

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(amountMeasure, commentsMeasure));

        WritebackAttribute wbProductAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbProductAttribute.setDimensionConnector(dimensionConnector);
        wbProductAttribute.setColumn(wbProductColumn);

        WritebackMeasure wbAmountMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbAmountMeasure.setName("Amount");
        wbAmountMeasure.setColumn(wbAmountColumn);

        WritebackMeasure wbCommentsMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbCommentsMeasure.setName("Comments");
        wbCommentsMeasure.setColumn(wbCommentColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(FACTWB);
        writebackTable.getWritebackAttribute().add(wbProductAttribute);
        writebackTable.getWritebackMeasure().addAll(List.of(wbAmountMeasure, wbCommentsMeasure));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setSource(factSource);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Decimal + Comment");
        catalog.setDescription("Minimal writeback example with a DECIMAL amount and a text comment.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Decimal + Comment",
                                catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", "Plain TableSource over FACT.",
                                1, 2, 0, factSource, 2),
                        new DocSection("Product dimension", dimensionBody, 1, 3, 0, dimension, 0),
                        new DocSection("Measures (decimal Amount + text Comments)", measuresBody, 1, 4, 0, null, 0),
                        new DocSection("Writeback (FACTWB)", writebackBody, 1, 5, 0, writebackTable, 2),
                        new DocSection("Cube C", cubeBody, 1, 6, 0, cube, 2)),
                List.of(new CatalogRef("catalog", () -> c)));
    }

    private static Column createColumn(String name,
            org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType type) {
        Column c = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        c.setName(name);
        c.setType(type);
        return c;
    }

    private static Table createTable(String name, List<Column> columns) {
        Table t = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        t.setName(name);
        t.getFeature().addAll(columns);
        return t;
    }
}
