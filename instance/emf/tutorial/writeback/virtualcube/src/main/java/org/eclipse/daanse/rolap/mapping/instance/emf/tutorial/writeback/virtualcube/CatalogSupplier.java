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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.virtualcube;

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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.06", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CUBE_N = "CN";
    private static final String CUBE_T = "CT";
    private static final String CUBE_V = "V";
    private static final String FACT_N = "FACT_N";
    private static final String FACT_T = "FACT_T";
    private static final String FACTWB_T = "FACTWB_T";
    private static final String CATEGORY = "CATEGORY";
    private static final String REGION = "REGION";

    private Catalog catalog;
    private Schema databaseSchema;
    private PhysicalCube cube1;
    private PhysicalCube cube2;
    private VirtualCube vCube;
    private TableSource factNSource;
    private TableSource factTSource;
    private StandardDimension categoryDimension;
    private StandardDimension regionDimension;
    private SumMeasure amountMeasure;
    private TextAggMeasure commentsMeasure;
    private WritebackTable writebackTable;

    private static final String catalogBody = """
            **Daanse Tutorial — Writeback combined through a Virtual Cube.**

            Two physical cubes sharing the same dimensions, one read-only and
            one writeback, joined through a `VirtualCube`.

            - `CN` (Cube-Numeric) — fact `FACT_N`, one `SumMeasure(Amount)`,
              **no writeback table**. Stays read-only.
            - `CT` (Cube-Text)    — fact `FACT_T`, one `TextAggMeasure(Comments)`,
              **writeback enabled** to `FACTWB_T`. Writes a single row per
              cell update, the `Comments` cell carrying the typed string.
            - `V`  (VirtualCube)  — references both physical cubes and exposes
              `Amount` plus `Comments` together. The same `Category` and
              `Region` dimensions show up across both cubes via per-cube
              `DimensionConnector` instances pointing at the same shared
              `StandardDimension`.

            Why two cubes? `PhysicalCube.writebackTable` is per-cube in the
            ecore. A `VirtualCube` has no writeback field. Mixing a measure
            you want to write and one you don't on the same physical cube
            entangles their fact-table union behind one writeback table —
            cleaner to split into two cubes and combine views above them.
            """;

    private static final String databaseSchemaBody = """
            Five tables in one schema:

            - `CATEGORY(CATEGORY, NAME)` — dimension table.
            - `REGION(REGION, NAME)`     — dimension table.
            - `FACT_N(CATEGORY, REGION, AMOUNT)`  — Cube CN's facts.
            - `FACT_T(CATEGORY, REGION, COMMENT)` — Cube CT's facts.
            - `FACTWB_T(CATEGORY, REGION, COMMENT, ID, USER)` — writeback
              target for Cube CT. `ID` is filled with a UUID by the runtime,
              `USER` with the session user.
            """;

    private static final String dimensionsBody = """
            Two `StandardDimension` instances at catalog scope:

            - `Category` — explicit hierarchy with one level keyed on
              `CATEGORY.CATEGORY`, name from `CATEGORY.NAME`.
            - `Region`   — explicit hierarchy with one level keyed on
              `REGION.REGION`, name from `REGION.NAME`.

            Each physical cube gets its own `DimensionConnector` per
            dimension. All connectors `setDimension(...)` on the same
            shared dimension instance. The `VirtualCube` aggregates all
            four connectors so the same hierarchies surface for both
            cubes' measures.
            """;

    private static final String cube1Body = """
            `CN` is a plain read-only cube:

            - `TableSource` over `FACT_N`.
            - `DimensionConnector`s for `Category` (FK = `FACT_N.CATEGORY`)
              and `Region` (FK = `FACT_N.REGION`).
            - One `MeasureGroup` containing `Amount = SumMeasure(AMOUNT)`.
            - No `writebackTable`. Writebacks targeting `[Measures].[Amount]`
              are a no-op on this cube.
            """;

    private static final String cube2Body = """
            `CT` carries the writeback measure:

            - `TableSource` over `FACT_T`.
            - `DimensionConnector`s for `Category` (FK = `FACT_T.CATEGORY`)
              and `Region` (FK = `FACT_T.REGION`).
            - One `MeasureGroup` containing
              `Comments = TextAggMeasure(COMMENT, separator = " | ")`.
            - `WritebackTable("FACTWB_T")` with two
              `WritebackAttribute`s (one per dimension connector) and one
              `WritebackMeasure` for `Comments` → `FACTWB_T.COMMENT`.
            """;

    private static final String virtualBody = """
            `V` is a `VirtualCube` that references both physical cubes.
            Its `dimensionConnectors` are the four connectors above (two
            from each cube, pointing at the same shared dimensions). Its
            `referencedMeasures` are `Amount` and `Comments`, drawn from
            their respective underlying cubes.

            Clients querying `[V]` see one logical schema combining both
            facts. The default measure is `Amount`.
            """;

    private static final String writebackBody = """
            When the client writes to `([Category].[A], [Region].[N],
            [Measures].[Comments]) = "hello"` through `[V]` (or directly
            through `[CT]`), the runtime resolves the target measure to
            `Comments` on cube `CT`, detects its
            `Datatype.VARCHAR` (because `TextAggMeasure` resolves to
            `ListAggAggregator`), and emits exactly one row into
            `FACTWB_T` with `CATEGORY='A'`, `REGION='N'`,
            `COMMENT='hello'`, the cell-key fields, `ID = UUID()` and
            `USER = sessionUser`.

            No row is written for `[Measures].[Amount]` even via `[V]`,
            because `Amount`'s owning cube `CN` has no writeback table.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column factNCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column factNRegionColumn = createColumn("REGION", SqlSimpleTypes.Sql99.varcharType());
        Column factNAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.Sql99.integerType());
        Table factNTable = createTable(FACT_N,
                List.of(factNCategoryColumn, factNRegionColumn, factNAmountColumn));
        databaseSchema.getOwnedElement().add(factNTable);

        Column factTCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column factTRegionColumn = createColumn("REGION", SqlSimpleTypes.Sql99.varcharType());
        Column factTCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Table factTTable = createTable(FACT_T,
                List.of(factTCategoryColumn, factTRegionColumn, factTCommentColumn));
        databaseSchema.getOwnedElement().add(factTTable);

        Column catCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column catNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Table categoryTable = createTable(CATEGORY, List.of(catCategoryColumn, catNameColumn));
        databaseSchema.getOwnedElement().add(categoryTable);

        Column regRegionColumn = createColumn("REGION", SqlSimpleTypes.Sql99.varcharType());
        Column regNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Table regionTable = createTable(REGION, List.of(regRegionColumn, regNameColumn));
        databaseSchema.getOwnedElement().add(regionTable);

        Column wbCategoryColumn = createColumn("CATEGORY", SqlSimpleTypes.Sql99.varcharType());
        Column wbRegionColumn = createColumn("REGION", SqlSimpleTypes.Sql99.varcharType());
        Column wbCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Table writebackPhysicalTable = createTable(FACTWB_T,
                List.of(wbCategoryColumn, wbRegionColumn, wbCommentColumn, wbIdColumn, wbUserColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        factNSource = SourceFactory.eINSTANCE.createTableSource();
        factNSource.setTable(factNTable);

        factTSource = SourceFactory.eINSTANCE.createTableSource();
        factTSource.setTable(factTTable);

        TableSource categorySource = SourceFactory.eINSTANCE.createTableSource();
        categorySource.setTable(categoryTable);

        TableSource regionSource = SourceFactory.eINSTANCE.createTableSource();
        regionSource.setTable(regionTable);

        Level categoryLevel = LevelFactory.eINSTANCE.createLevel();
        categoryLevel.setName("Category");
        categoryLevel.setColumn(catCategoryColumn);
        categoryLevel.setNameColumn(catNameColumn);
        categoryLevel.setUniqueMembers(true);

        ExplicitHierarchy categoryHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        categoryHierarchy.setName("Category");
        categoryHierarchy.setHasAll(true);
        categoryHierarchy.setAllMemberName("All Categories");
        categoryHierarchy.setPrimaryKey(catCategoryColumn);
        categoryHierarchy.setSource(categorySource);
        categoryHierarchy.getLevels().add(categoryLevel);

        categoryDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        categoryDimension.setName("Category");
        categoryDimension.getHierarchies().add(categoryHierarchy);

        Level regionLevel = LevelFactory.eINSTANCE.createLevel();
        regionLevel.setName("Region");
        regionLevel.setColumn(regRegionColumn);
        regionLevel.setNameColumn(regNameColumn);
        regionLevel.setUniqueMembers(true);

        ExplicitHierarchy regionHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        regionHierarchy.setName("Region");
        regionHierarchy.setHasAll(true);
        regionHierarchy.setAllMemberName("All Regions");
        regionHierarchy.setPrimaryKey(regRegionColumn);
        regionHierarchy.setSource(regionSource);
        regionHierarchy.getLevels().add(regionLevel);

        regionDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        regionDimension.setName("Region");
        regionDimension.getHierarchies().add(regionHierarchy);

        DimensionConnector catConn1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        catConn1.setOverrideDimensionName("Category");
        catConn1.setDimension(categoryDimension);
        catConn1.setForeignKey(factNCategoryColumn);

        DimensionConnector regConn1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        regConn1.setOverrideDimensionName("Region");
        regConn1.setDimension(regionDimension);
        regConn1.setForeignKey(factNRegionColumn);

        DimensionConnector catConn3 = DimensionFactory.eINSTANCE.createDimensionConnector();
        catConn3.setOverrideDimensionName("Category");
        catConn3.setDimension(categoryDimension);
        catConn3.setForeignKey(factNCategoryColumn);

        DimensionConnector regConn3 = DimensionFactory.eINSTANCE.createDimensionConnector();
        regConn3.setOverrideDimensionName("Region");
        regConn3.setDimension(regionDimension);
        regConn3.setForeignKey(factNRegionColumn);
        
        DimensionConnector catConn2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        catConn2.setOverrideDimensionName("Category");
        catConn2.setDimension(categoryDimension);
        catConn2.setForeignKey(factTCategoryColumn);

        DimensionConnector regConn2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        regConn2.setOverrideDimensionName("Region");
        regConn2.setDimension(regionDimension);
        regConn2.setForeignKey(factTRegionColumn);

        amountMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        amountMeasure.setName("Amount");
        amountMeasure.setColumn(factNAmountColumn);
        amountMeasure.setFormatString("#,##0");

        OrderedColumn commentOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        commentOrderedColumn.setColumn(factTCommentColumn);

        commentsMeasure = MeasureFactory.eINSTANCE.createTextAggMeasure();
        commentsMeasure.setName("Comments");
        commentsMeasure.setColumn(factTCommentColumn);
        commentsMeasure.setSeparator(" | ");
        commentsMeasure.getOrderByColumns().add(commentOrderedColumn);

        MeasureGroup measureGroupN = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupN.getMeasures().add(amountMeasure);

        MeasureGroup measureGroupT = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupT.getMeasures().add(commentsMeasure);

        WritebackAttribute wbCategoryAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbCategoryAttribute.setDimensionConnector(catConn2);
        wbCategoryAttribute.setColumn(wbCategoryColumn);

        WritebackAttribute wbRegionAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbRegionAttribute.setDimensionConnector(regConn2);
        wbRegionAttribute.setColumn(wbRegionColumn);

        WritebackMeasure wbCommentsMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbCommentsMeasure.setName("Comments");
        wbCommentsMeasure.setColumn(wbCommentColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(FACTWB_T);
        writebackTable.getWritebackAttribute().addAll(List.of(wbCategoryAttribute, wbRegionAttribute));
        writebackTable.getWritebackMeasure().add(wbCommentsMeasure);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE_N);
        cube1.setSource(factNSource);
        cube1.getDimensionConnectors().addAll(List.of(catConn1, regConn1));
        cube1.getMeasureGroups().add(measureGroupN);
        catConn1.setPhysicalCube(cube1);
        regConn1.setPhysicalCube(cube1);

        cube2 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE_T);
        cube2.setSource(factTSource);
        cube2.getDimensionConnectors().addAll(List.of(catConn2, regConn2));
        cube2.getMeasureGroups().add(measureGroupT);
        cube2.setWritebackTable(writebackTable);
        catConn2.setPhysicalCube(cube2);
        regConn2.setPhysicalCube(cube2);

        vCube = CubeFactory.eINSTANCE.createVirtualCube();
        vCube.setName(CUBE_V);
        vCube.setDefaultMeasure(amountMeasure);
        vCube.getDimensionConnectors().addAll(List.of(catConn3, regConn3));
        vCube.getReferencedMeasures().addAll(List.of(amountMeasure, commentsMeasure));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Virtual Cube");
        catalog.setDescription("Two physical cubes sharing Category + Region dimensions — one read-only"
                + " (SumMeasure 'Amount'), one with text writeback (TextAggMeasure 'Comments') — combined"
                + " through a VirtualCube. Demonstrates that writeback lives on the underlying PhysicalCube"
                + " even when queries flow through the VirtualCube.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Virtual Cube",
                                catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 5),
                        new DocSection("Dimensions (shared)", dimensionsBody, 1, 2, 0, categoryDimension, 0),
                        new DocSection("Cube CN (read-only, numeric)", cube1Body, 1, 3, 0, cube1, 2),
                        new DocSection("Cube CT (writeback, text)", cube2Body, 1, 4, 0, cube2, 2),
                        new DocSection("Virtual Cube V", virtualBody, 1, 5, 0, vCube, 2),
                        new DocSection("Writeback flow (FACTWB_T)", writebackBody, 1, 6, 0, writebackTable, 2)),
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
