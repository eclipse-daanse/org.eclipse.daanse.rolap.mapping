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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.parentchild;

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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.WritebackMeasure;

import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;

import org.osgi.service.component.annotations.Component;

@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.06", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CUBE = "C";
    private static final String FACT = "FACT";
    private static final String NODE = "NODE";
    private static final String FACTWB = "FACTWB";

    private Catalog catalog;
    private Schema databaseSchema;
    private PhysicalCube cube;
    private TableSource factSource;
    private TableSource nodeSource;
    private StandardDimension dimension;
    private ParentChildHierarchy hierarchy;
    private Level level;
    private SumMeasure valueMeasure;
    private WritebackTable writebackTable;

    private static final String catalogBody = """
            **Daanse Tutorial — Writeback with a parent-child hierarchy.**

            The minimal example showing how writeback works against a
            `ParentChildHierarchy`. One self-referencing dimension table
            `NODE` (KEY, NAME, PARENT_KEY) defines an arbitrary-depth tree,
            the fact table `FACT` posts values against any node, and a
            `FACTWB` writeback table receives user-entered adjustments.

            When the user writes a value at an *intermediate* node (e.g. a
            parent in the tree), the runtime allocates the value across the
            descendant leaves per the chosen `AllocationPolicy` — exactly
            the same behaviour as for a multi-level explicit hierarchy. The
            interesting part is that for a parent-child hierarchy the set
            of leaves is computed by walking the self-referencing
            relationship at cube-init time, not from a fixed level depth.
            """;

    private static final String databaseSchemaBody = """
            Three tables:

            - **`FACT`** — `NODE`, `VALUE`. One row per fact posting, with
              `NODE` referencing the leaf node and `VALUE` the numeric
              amount.
            - **`NODE`** — `KEY`, `NAME`, `PARENT_KEY`. The self-referencing
              dimension table. Root rows have an empty `PARENT_KEY`
              (matching the configured `nullParentValue = ""`). Members at
              any depth share the same level definition.
            - **`FACTWB`** — `NODE`, `VALUE`, `ID`, `USER`. The writeback
              target. `ID` is filled by the engine with a UUID, `USER` with
              the session user.
            """;

    private static final String dimensionBody = """
            One dimension `Tree` with a single-level `ParentChildHierarchy`.
            The level keys on the `KEY` column; the `PARENT_KEY` column
            wires the self-reference, and `nullParentValue = ""` marks the
            roots. The fact joins on `FACT.NODE = NODE.KEY` — at every
            level of the tree, the same column carries the join key.
            """;

    private static final String hierarchyBody = """
            ```
            ParentChildHierarchy
              ├── name = "Tree"
              ├── hasAll = true
              ├── allMemberName = "All Nodes"
              ├── primaryKey  = NODE.KEY
              ├── parentColumn = NODE.PARENT_KEY
              ├── nullParentValue = ""
              ├── source = NodeTableSource
              └── level("Node") with column = NODE.KEY, nameColumn = NODE.NAME
            ```

            `setUniqueMembers(true)` on the level is appropriate here
            because every parent-child member is uniquely identified by
            its `KEY` value, regardless of depth.
            """;

    private static final String writebackBody = """
            The cube wires the `Value` measure into a single-measure
            writeback table:

            ```
            WritebackTable("FACTWB")                     [database.writeback]
              ├── WritebackAttribute(Tree connector) → NODE   [database.writeback]
              └── WritebackMeasure("Value")          → VALUE  [olap.cube.measure]
            ```

            *Behaviour at write time.* A `Cell.setValue([Measures].[Value], 100, EQUAL_ALLOCATION, ...)`
            at any node — root, intermediate or leaf — drives the engine to:

            1. Walk the self-referencing `NODE.PARENT_KEY` chain to compute
               the leaves of the addressed node.
            2. Spread the `100` value across those leaves per the chosen
               `AllocationPolicy` — `EQUAL_ALLOCATION` gives every leaf
               `100 / N`, `EQUAL_INCREMENT` adds `(100 - oldValue) / N` to
               each.
            3. Emit one row per leaf into `FACTWB` with the leaf's `KEY` as
               `NODE`, the allocated portion as `VALUE`, a fresh `UUID` as
               `ID`, and the session user as `USER`.

            *Behaviour at read time.* A subsequent MDX query for any node
            sums fact rows plus writeback rows under that subtree via the
            standard SQL aggregation path — no special handling needed,
            because the writeback table has the same shape as the fact and
            both are aggregated by the cube's `SumMeasure`.

            *Where the measure lives.* `WritebackMeasure` is now in the
            `olap.cube.measure` ecore package (alongside `SumMeasure`,
            `TextAggMeasure`, …) — it is conceptually a measure that
            happens to be paired with a database column for persistence.
            Only the writeback *infrastructure* (`WritebackTable`,
            `WritebackAttribute`) remains in `database.writeback`. In Java
            the factory call is `MeasureFactory.eINSTANCE.createWritebackMeasure()`.
            """;

    private static final String cubeBody = """
            One physical cube `C` over `FACT` with one `DimensionConnector`
            (Tree → `FACT.NODE`), one `MeasureGroup` holding the `Value`
            sum measure, and the `FACTWB` writeback table.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column factNodeColumn = createColumn("NODE", SqlSimpleTypes.Sql99.varcharType());
        Column factValueColumn = createColumn("VALUE", SqlSimpleTypes.Sql99.integerType());
        Table factTable = createTable(FACT, List.of(factNodeColumn, factValueColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column nodeKeyColumn = createColumn("KEY", SqlSimpleTypes.Sql99.varcharType());
        Column nodeNameColumn = createColumn("NAME", SqlSimpleTypes.Sql99.varcharType());
        Column nodeParentKeyColumn = createColumn("PARENT_KEY", SqlSimpleTypes.Sql99.varcharType());
        Table nodeTable = createTable(NODE, List.of(nodeKeyColumn, nodeNameColumn, nodeParentKeyColumn));
        databaseSchema.getOwnedElement().add(nodeTable);

        Column wbNodeColumn = createColumn("NODE", SqlSimpleTypes.Sql99.varcharType());
        Column wbValueColumn = createColumn("VALUE", SqlSimpleTypes.Sql99.integerType());
        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Table writebackPhysicalTable = createTable(FACTWB,
                List.of(wbNodeColumn, wbValueColumn, wbIdColumn, wbUserColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        factSource = SourceFactory.eINSTANCE.createTableSource();
        factSource.setTable(factTable);

        nodeSource = SourceFactory.eINSTANCE.createTableSource();
        nodeSource.setTable(nodeTable);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Node");
        level.setUniqueMembers(true);
        level.setColumn(nodeKeyColumn);
        level.setNameColumn(nodeNameColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setName("Tree");
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("All Nodes");
        hierarchy.setPrimaryKey(nodeKeyColumn);
        hierarchy.setSource(nodeSource);
        hierarchy.setParentColumn(nodeParentKeyColumn);
        hierarchy.setNullParentValue("");
        hierarchy.setLevel(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Tree");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Tree");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(factNodeColumn);

        valueMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        valueMeasure.setName("Value");
        valueMeasure.setColumn(factValueColumn);
        valueMeasure.setFormatString("#,##0");

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(valueMeasure);

        WritebackAttribute wbTreeAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        wbTreeAttribute.setDimensionConnector(dimensionConnector);
        wbTreeAttribute.setColumn(wbNodeColumn);

        WritebackMeasure wbValueMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbValueMeasure.setName("Value");
        wbValueMeasure.setColumn(wbValueColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(FACTWB);
        writebackTable.getWritebackAttribute().add(wbTreeAttribute);
        writebackTable.getWritebackMeasure().add(wbValueMeasure);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setSource(factSource);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback Parent-Child");
        catalog.setDescription("Minimal writeback example against a ParentChildHierarchy.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback Parent-Child",
                                catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", "Plain TableSource over FACT.",
                                1, 2, 0, factSource, 2),
                        new DocSection("Tree dimension", dimensionBody, 1, 3, 0, dimension, 0),
                        new DocSection("ParentChildHierarchy", hierarchyBody, 1, 4, 0, hierarchy, 0),
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
