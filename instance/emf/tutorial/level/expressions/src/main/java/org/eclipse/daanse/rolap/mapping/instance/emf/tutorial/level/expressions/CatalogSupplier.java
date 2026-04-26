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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.expressions;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.01", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level level2;
    private PhysicalCube cube;
    private TableSource query;
    private SumMeasure measure;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Minimal Cube with levels with SQL expressions as column
            Cube have two levels Level1, Level2 with NameColumn, CaptionColumn, OrdinalColumn as SQL expression
            """;

    private static final String databaseSchemaBody = """
            Schema includes physical table `Fact`.
            Table, named `Fact`, contains 3 columns: `KEY`, `KEY1` and `VALUE`.
            """;

    private static final String queryFactBody = """
            This example uses a TableQuery, as it directly references the table `Fact`.
            """;

    private static final String level1Body = """
            The Level1 uses the column attribute to specify the primary key column. Additionally,
            it defines the nameColumn attribute to specify the column that contains the name of the level as SQL expression.
            """;

    private static final String level2Body = """
            The Level2 uses the primary key column as SQL expression. Additionally,
            it defines the nameColumn attribute to specify the column that contains the name of the level as SQL expression.
            Also it defines the OrdinalColumn attribute to specify the column that contains the ordinal parameter of the level as SQL expression.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists of two levels: Level1 and Level2.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the queryHierarchy Join-query used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measureBody = """
            Measure use fact table column with sum aggregation.
    """;

    private static final String cubeBody = """
            In this example uses cube with levels with SQL expressions as column.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column key1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        key1Column.setName("KEY1");
        key1Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        SqlStatement nameSql = SourceFactory.eINSTANCE.createSqlStatement();
        nameSql.getDialects().addAll(List.of("generic", "h2"));
        nameSql.setSql("\"KEY\" || ' ' || \"KEY1\"");

        ExpressionColumn nameExpressionColumn = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        nameExpressionColumn.setName("nameExpression");
        nameExpressionColumn.getSqls().add(nameSql);

        SqlStatement keySql1 = SourceFactory.eINSTANCE.createSqlStatement();
        keySql1.getDialects().addAll(List.of("generic"));
        keySql1.setSql("KEY");
        SqlStatement keySql2 = SourceFactory.eINSTANCE.createSqlStatement();
        keySql2.getDialects().addAll(List.of("h2"));
        keySql2.setSql("\"KEY1\" || ' ' || \"KEY\"");

        ExpressionColumn keyExpression = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        keyExpression.setName("keyExpression");
        keyExpression.getSqls().addAll(List.of(keySql1, keySql2));

        SqlStatement captionSql1 = SourceFactory.eINSTANCE.createSqlStatement();
        captionSql1.getDialects().addAll(List.of("generic"));
        captionSql1.setSql("KEY");
        SqlStatement captionSql2 = SourceFactory.eINSTANCE.createSqlStatement();
        captionSql2.getDialects().addAll(List.of("h2"));
        captionSql2.setSql("\"KEY1\" || '___' || \"KEY\"");

        ExpressionColumn captionExpression = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        captionExpression.setName("captionExpression");
        captionExpression.getSqls().addAll(List.of(captionSql1, captionSql2));

        SqlStatement ordinalSql1 = SourceFactory.eINSTANCE.createSqlStatement();
        ordinalSql1.getDialects().addAll(List.of("generic", "h2"));
        ordinalSql1.setSql("\"KEY\" || '___' || \"KEY1\"");

        ExpressionColumn ordinalExpression = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        ordinalExpression.setName("ordinalExpression");
        ordinalExpression.getSqls().addAll(List.of(ordinalSql1));

        OrderedColumn orderedColumnExpression = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumnExpression.setColumn(ordinalExpression);

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, key1Column, valueColumn,nameExpressionColumn,keyExpression,captionExpression,ordinalExpression));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setColumn(keyColumn);
        level1.setNameColumn(nameExpressionColumn);

        level2 = LevelFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setColumn(keyExpression);
        level2.setCaptionColumn(captionExpression);
        level2.getOrdinalColumns().add(orderedColumnExpression);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(level1, level2));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level Expressions");
        catalog.setDescription("Level with expression-based definitions");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level Expressions", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryFactBody, 1, 2, 0, query, 2),
                        new DocSection("Level1", level1Body, 1, 3, 0, level1, 0),
                        new DocSection("Level2", level2Body, 1, 4, 0, level2, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 5, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 6, 0, dimension, 0),
                        new DocSection("Measure", measureBody, 1, 7, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 8, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
