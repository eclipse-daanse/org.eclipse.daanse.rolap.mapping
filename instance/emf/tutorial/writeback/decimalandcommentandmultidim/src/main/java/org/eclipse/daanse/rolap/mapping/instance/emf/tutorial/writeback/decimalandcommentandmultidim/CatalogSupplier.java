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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.decimalandcommentandmultidim;

import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SqlSimpleTypes;

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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.08", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";
    private static final String FACTWB = "FACTWB";
    private static final String PRODUCT = "PRODUCT";
    private static final String REGION = "REGION";

    private Catalog catalog;
    private Schema databaseSchema;
    private PhysicalCube cube;
    private TableSource factSource;
    private TableSource productSource;
    private TableSource regionSource;
    private StandardDimension productDimension;
    private StandardDimension regionDimension;
    private ExplicitHierarchy productHierarchy;
    private ExplicitHierarchy regionHierarchy;
    private Level productLevel;
    private Level countryLevel;
    private Level cityLevel;
    private SumMeasure amountMeasure;
    private TextAggMeasure commentsMeasure;
    private WritebackTable writebackTable;

    private static final String catalogBody = """
            **Daanse Tutorial — Writeback with two dimensions, one of them
            multi-level.**

            Extends the single-dimension `tutorial.writeback.decimal`
            example with a second dimension (`Region`) that has **two**
            levels (`Country → City`). The point: writeback works the
            same way regardless of the slicer's depth — the runtime
            always writes the leaf-key value of every connected
            dimension into the writeback table, no matter which level
            the user typed at in the UI.

            The cube exposes:
            - **`Amount`** — `SumMeasure` over a `DECIMAL(18,2)` column.
            - **`Comments`** — `TextAggMeasure` over a `VARCHAR` column.

            Two `DimensionConnector`s (Product, Region) both feed
            `WritebackAttribute`s pointing at the matching writeback
            columns. The `Region` connector's foreign key is the
            leaf-level `CITY` column on the fact — exactly the column
            the writeback row will carry.
            """;

    private static final String databaseSchemaBody = """
            Four tables:

            - **`FACT`** — `PRODUCT` (VARCHAR), `CITY` (VARCHAR),
              `AMOUNT` (DECIMAL), `COMMENT` (VARCHAR).
            - **`PRODUCT`** — `KEY` (VARCHAR), `NAME` (VARCHAR).
            - **`REGION`** — `COUNTRY_KEY` (VARCHAR), `COUNTRY_NAME`
              (VARCHAR), `CITY_KEY` (VARCHAR), `CITY_NAME` (VARCHAR).
              Single denormalised table that lists every city with its
              owning country.
            - **`FACTWB`** — `PRODUCT` (VARCHAR), `CITY` (VARCHAR),
              `AMOUNT` (DECIMAL), `COMMENT` (VARCHAR), `ID` (VARCHAR,
              UUID written by the engine), `USER` (VARCHAR, session
              user).
            """;

    private static final String productDimensionBody = """
            Same single-level shape as the `tutorial.writeback.decimal`
            tutorial — `Product` dim keyed on `PRODUCT.KEY` with the
            displayed name from `PRODUCT.NAME`.
            """;

    private static final String regionDimensionBody = """
            `Region` is a two-level `ExplicitHierarchy` on a single
            denormalised `REGION` table (no snowflake join):

            - **`Country`** (L1) — keyed on `REGION.COUNTRY_KEY`,
              `uniqueMembers = true`. Each country is unique.
            - **`City`** (L2, leaf) — keyed on `REGION.CITY_KEY`,
              `uniqueMembers = false`. Different countries can share a
              city *name*, but the (country, city) pair is unique.

            The fact joins on the leaf: `FACT.CITY = REGION.CITY_KEY`.
            Aggregations across a country roll up its cities through the
            standard SQL `GROUP BY` path.
            """;

    private static final String writebackBody = """
            ```
            WritebackTable("FACTWB")                     [database.writeback]
              ├── WritebackAttribute(Product) → PRODUCT  [database.writeback]
              ├── WritebackAttribute(Region)  → CITY     [database.writeback]
              ├── WritebackMeasure("Amount")  → AMOUNT   [olap.cube.measure]
              └── WritebackMeasure("Comments") → COMMENT [olap.cube.measure]
            ```

            **Two-dimensional cell coordinates.** Every cell in this
            cube has a (Product, City) coordinate. A
            `Cell.setValue([Measures].[Amount], 99.95, ...)` issued at
            an intermediate `Country` member is allocated across the
            country's cities (each city gets `99.95 / N`); a write at a
            specific city goes directly to that one leaf.

            **Text writeback semantics.** The `Comments` measure follows
            the text short-path: a single row at the cell's exact
            (Product, City-or-Country) coordinate; intermediate-level
            writes record the country's `COUNTRY_KEY` as the city
            attribute (since `Region`'s writeback attribute targets the
            `CITY` column). At read time the `ListAggAggregator`
            re-aggregates everything through SQL.

            **Both writeable measures share the same writeback table.**
            Numeric and text writes interleave freely; each call
            produces its own row(s) tagged with a fresh UUID `ID` and
            the session `USER`.
            """;

    private static final String cubeBody = """
            One physical cube `C` over `FACT` with two
            `DimensionConnector`s (Product, Region), one `MeasureGroup`
            holding the decimal `Amount` plus the text `Comments`
            measure, and the `FACTWB` writeback table.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column factProductColumn = createColumn("PRODUCT", SqlSimpleTypes.Sql99.varcharType());
        Column factCityColumn = createColumn("CITY", SqlSimpleTypes.Sql99.varcharType());
        Column factAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.decimalType(18, 2));
        Column factCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Table factTable = createTable(FACT,
                List.of(factProductColumn, factCityColumn, factAmountColumn, factCommentColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column productKeyColumn = createColumn("KEY", SqlSimpleTypes.Sql99.varcharType());
        Column productNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Table productTable = createTable(PRODUCT, List.of(productKeyColumn, productNameColumn));
        databaseSchema.getOwnedElement().add(productTable);

        Column countryKeyColumn = createColumn("COUNTRY_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column countryNameColumn = createColumn("COUNTRY_NAME", SqlSimpleTypes.Sql99.varcharType());
        Column cityKeyColumn = createColumn("CITY_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column cityNameColumn = createColumn("CITY_NAME", SqlSimpleTypes.Sql99.varcharType());
        Table regionTable = createTable(REGION,
                List.of(countryKeyColumn, countryNameColumn, cityKeyColumn, cityNameColumn));
        databaseSchema.getOwnedElement().add(regionTable);

        Column wbProductColumn = createColumn("PRODUCT", SqlSimpleTypes.Sql99.varcharType());
        Column wbCityColumn = createColumn("CITY", SqlSimpleTypes.Sql99.varcharType());
        Column wbAmountColumn = createColumn("AMOUNT", SqlSimpleTypes.decimalType(18, 2));
        Column wbCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());
        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Table writebackPhysicalTable = createTable(FACTWB,
                List.of(wbProductColumn, wbCityColumn, wbAmountColumn, wbCommentColumn, wbIdColumn, wbUserColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        factSource = SourceFactory.eINSTANCE.createTableSource();
        factSource.setTable(factTable);

        productSource = SourceFactory.eINSTANCE.createTableSource();
        productSource.setTable(productTable);

        regionSource = SourceFactory.eINSTANCE.createTableSource();
        regionSource.setTable(regionTable);

        productLevel = LevelFactory.eINSTANCE.createLevel();
        productLevel.setName("Product");
        productLevel.setColumn(productKeyColumn);
        productLevel.setNameColumn(productNameColumn);
        productLevel.setUniqueMembers(true);

        productHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        productHierarchy.setName("Product");
        productHierarchy.setHasAll(true);
        productHierarchy.setAllMemberName("All Products");
        productHierarchy.setPrimaryKey(productKeyColumn);
        productHierarchy.setSource(productSource);
        productHierarchy.getLevels().add(productLevel);

        productDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        productDimension.setName("Product");
        productDimension.getHierarchies().add(productHierarchy);

        countryLevel = LevelFactory.eINSTANCE.createLevel();
        countryLevel.setName("Country");
        countryLevel.setColumn(countryKeyColumn);
        countryLevel.setNameColumn(countryNameColumn);
        countryLevel.setUniqueMembers(true);

        cityLevel = LevelFactory.eINSTANCE.createLevel();
        cityLevel.setName("City");
        cityLevel.setColumn(cityKeyColumn);
        cityLevel.setNameColumn(cityNameColumn);
        cityLevel.setUniqueMembers(false);

        regionHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        regionHierarchy.setName("Region");
        regionHierarchy.setHasAll(true);
        regionHierarchy.setAllMemberName("All Regions");
        regionHierarchy.setPrimaryKey(cityKeyColumn);
        regionHierarchy.setSource(regionSource);
        regionHierarchy.getLevels().addAll(List.of(countryLevel, cityLevel));

        regionDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        regionDimension.setName("Region");
        regionDimension.getHierarchies().add(regionHierarchy);

        DimensionConnector productConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        productConnector.setOverrideDimensionName("Product");
        productConnector.setDimension(productDimension);
        productConnector.setForeignKey(factProductColumn);

        DimensionConnector regionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        regionConnector.setOverrideDimensionName("Region");
        regionConnector.setDimension(regionDimension);
        regionConnector.setForeignKey(factCityColumn);

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
        wbProductAttribute.setDimensionConnector(productConnector);
        wbProductAttribute.setColumn(wbProductColumn);

        WritebackAttribute wbRegionAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbRegionAttribute.setDimensionConnector(regionConnector);
        wbRegionAttribute.setColumn(wbCityColumn);

        WritebackMeasure wbAmountMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbAmountMeasure.setName("Amount");
        wbAmountMeasure.setColumn(wbAmountColumn);

        WritebackMeasure wbCommentsMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbCommentsMeasure.setName("Comments");
        wbCommentsMeasure.setColumn(wbCommentColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(FACTWB);
        writebackTable.getWritebackAttribute().addAll(List.of(wbProductAttribute, wbRegionAttribute));
        writebackTable.getWritebackMeasure().addAll(List.of(wbAmountMeasure, wbCommentsMeasure));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setSource(factSource);
        cube.getDimensionConnectors().addAll(List.of(productConnector, regionConnector));
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Multi-Dimension");
        catalog.setDescription("Writeback example with two dimensions; the Region dimension has two levels.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Multi-Dimension",
                                catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", "Plain TableSource over FACT.",
                                1, 2, 0, factSource, 2),
                        new DocSection("Product dimension (1 level)", productDimensionBody,
                                1, 3, 0, productDimension, 0),
                        new DocSection("Region dimension (2 levels)", regionDimensionBody,
                                1, 4, 0, regionDimension, 0),
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
