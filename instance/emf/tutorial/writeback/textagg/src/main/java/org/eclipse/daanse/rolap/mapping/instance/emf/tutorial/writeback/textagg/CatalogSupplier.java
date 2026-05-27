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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.textagg;

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

import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackTable;

import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.TextAggMeasure;

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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.05", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";
    private static final String FACTWB = "FACTWB";
    private static final String CATEGORY = "CATEGORY";

    private Catalog catalog;
    private Schema databaseSchema;
    private PhysicalCube cube;
    private TableSource factSource;
    private TableSource categorySource;
    private StandardDimension dimension;
    private ExplicitHierarchy hierarchy;
    private Level level;
    private SumMeasure amountMeasure;
    private TextAggMeasure commentsMeasure;
    private WritebackTable writebackTable;

    private static final String catalogBody = """
            **Daanse Tutorial — Writeback with text aggregation.**

            This is the minimal example demonstrating *two-kind* writeback in one
            cube:

            - a numeric `Amount` measure backed by `SumMeasure` over an `AMOUNT`
              column, with the usual allocation-policy semantics on write-back;
            - a text `Comments` measure backed by `TextAggMeasure` over a
              `COMMENT` column. On read it concatenates every matching cell's
              `COMMENT` value using the configured separator; on **write** the
              daanse runtime appends a single row to the writeback table with
              the typed string for the cell's exact coordinates.

            The writeback table `FACTWB` carries one `WritebackAttribute` for the
            dimension and two `WritebackMeasure`s — one numeric, one text. The
            runtime detects the text target from its aggregator type
            (`ListAggAggregator` ⇒ `Datatype.VARCHAR`) and short-circuits the
            allocation flow for that path, so text values land verbatim instead
            of being split numerically across descendants.
            """;

    private static final String databaseSchemaBody = """
            The database schema contains two tables:

            - **`FACT`** — `CATEGORY`, `AMOUNT`, `COMMENT`. One row per posting.
            - **`CATEGORY`** — `CATEGORY`, `NAME`. The single dimension table.
            - **`FACTWB`** — `CATEGORY`, `AMOUNT`, `COMMENT`, `ID`, `USER`. The
              writeback target. `ID` is filled by the engine with a UUID,
              `USER` with the session user.
            """;

    private static final String dimensionBody = """
            Single dimension `Category` with one explicit hierarchy and one level
            keyed on `CATEGORY`. The fact joins on `FACT.CATEGORY = CATEGORY.CATEGORY`.
            """;

    private static final String measuresBody = """
            Two stored measures share the same fact source:

            - `Amount` — `SumMeasure(AMOUNT)`. Numeric, currency-formatted.
            - `Comments` — `TextAggMeasure(COMMENT, separator = " | ")`. The
              read-side aggregator concatenates all matching comments at every
              level the slice rolls up to.
            """;

    private static final String writebackBody = """
            The cube wires both measures into the same `FACTWB` writeback table:

            ```
            WritebackTable("FACTWB")                                     [database.writeback]
              ├── WritebackAttribute(Category connector) → CATEGORY     [database.writeback]
              ├── WritebackMeasure("Amount")             → AMOUNT       [olap.cube.measure]
              └── WritebackMeasure("Comments")           → COMMENT      [olap.cube.measure]
            ```

            **Where these classes live in the ecore.** `WritebackMeasure` is
            no longer a sibling of the writeback *infrastructure*
            (`WritebackTable`, `WritebackAttribute`). It now sits in the
            `olap/cube/measure/` ecore package next to `SumMeasure`,
            `TextAggMeasure` and the other base measures, because a writeback
            measure is conceptually a measure — it names a cube measure by
            its logical name and just happens to be paired with a database
            column for persistence. Concretely:

            - Java package:
              `org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.WritebackMeasure`
              (was `…database.writeback.WritebackMeasure`).
            - Factory call:
              `MeasureFactory.eINSTANCE.createWritebackMeasure()`
              (was `WritebackFactory.eINSTANCE.createWritebackMeasure()`).
            - XMI namespace prefix: `rolapmeas:WritebackMeasure`
              (was `rolapdwriteback:WritebackMeasure`).

            **Runtime behaviour.** When the client calls
            `Cell.setValue([Measures].[Amount], 42.0, ...)` the runtime
            allocates the numeric value across the cell's leaves (per
            `AllocationPolicy`) and emits one or more rows with `AMOUNT`
            populated. When the client calls
            `Cell.setValue([Measures].[Comments], "on track", ...)` the
            runtime recognises the `TextAggMeasure` target by its aggregator
            (`ListAggAggregator` ⇒ `Datatype.VARCHAR`), **bypasses
            allocation**, and emits exactly one row keyed by the cell's
            coordinates with `COMMENT` set and `AMOUNT` left `NULL`. The
            engine appends `ID = UUID()` and `USER = sessionUser`.

            Re-running an MDX query for either measure folds the new
            writeback row in via the normal aggregation path — `SUM` for
            `Amount`, `TextAgg` (string concat) for `Comments`.
            """;

    private static final String cubeBody = """
            One physical cube `C` over the `FACT` table with one
            `DimensionConnector` and one `MeasureGroup` holding both measures,
            referencing the `FACTWB` writeback table.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column factCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column factAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.Sql99.integerType());
        Column factCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Table factTable = createTable(FACT, List.of(factCategoryColumn, factAmountColumn, factCommentColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column catCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column catNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Table categoryTable = createTable(CATEGORY, List.of(catCategoryColumn, catNameColumn));
        databaseSchema.getOwnedElement().add(categoryTable);

        Column wbCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column wbAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.Sql99.integerType());
        Column wbCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Table writebackPhysicalTable = createTable(FACTWB,
                List.of(wbCategoryColumn, wbAmountColumn, wbCommentColumn, wbIdColumn, wbUserColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        factSource = SourceFactory.eINSTANCE.createTableSource();
        factSource.setTable(factTable);

        categorySource = SourceFactory.eINSTANCE.createTableSource();
        categorySource.setTable(categoryTable);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Category");
        level.setColumn(catCategoryColumn);
        level.setNameColumn(catNameColumn);
        level.setUniqueMembers(true);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Category");
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("All Categories");
        hierarchy.setPrimaryKey(catCategoryColumn);
        hierarchy.setSource(categorySource);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Category");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Category");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(factCategoryColumn);

        amountMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        amountMeasure.setName("Amount");
        amountMeasure.setColumn(factAmountColumn);
        amountMeasure.setFormatString("#,##0");

        OrderedColumn commentOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        commentOrderedColumn.setColumn(factCommentColumn);

        commentsMeasure = MeasureFactory.eINSTANCE.createTextAggMeasure();
        commentsMeasure.setName("Comments");
        commentsMeasure.setColumn(factCommentColumn);
        commentsMeasure.setSeparator(" | ");
        commentsMeasure.getOrderByColumns().add(commentOrderedColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(amountMeasure, commentsMeasure));

        WritebackAttribute wbCategoryAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbCategoryAttribute.setDimensionConnector(dimensionConnector);
        wbCategoryAttribute.setColumn(wbCategoryColumn);

        WritebackMeasure wbAmountMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbAmountMeasure.setName("Amount");
        wbAmountMeasure.setColumn(wbAmountColumn);

        WritebackMeasure wbCommentsMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbCommentsMeasure.setName("Comments");
        wbCommentsMeasure.setColumn(wbCommentColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(FACTWB);
        writebackTable.getWritebackAttribute().add(wbCategoryAttribute);
        writebackTable.getWritebackMeasure().addAll(List.of(wbAmountMeasure, wbCommentsMeasure));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setSource(factSource);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Text Aggregation");
        catalog.setDescription("Minimal writeback example: numeric SumMeasure + text TextAggMeasure"
                + " in the same writeback table.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Text Aggregation",
                                catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", "Plain TableSource over the FACT table.",
                                1, 2, 0, factSource, 2),
                        new DocSection("Category dimension", dimensionBody, 1, 3, 0, dimension, 0),
                        new DocSection("Measures (numeric + text)", measuresBody, 1, 4, 0, null, 0),
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
