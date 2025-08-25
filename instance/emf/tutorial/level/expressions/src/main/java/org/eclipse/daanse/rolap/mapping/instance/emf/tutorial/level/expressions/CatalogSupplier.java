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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.1", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Minimal Cube with levels with SQL expressions as column
            Cube have two levels Level1, Level2 with NameColumn, CaptionColumn, OrdinalColumn as SQL expression
            """;

    private static final String databaseSchemaBody = """
            DatabaseSchema includes physical table `Fact`.
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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_LevelExpressions");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column key1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        key1Column.setName("KEY1");
        key1Column.setId("_column_fact_key1");
        key1Column.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        SqlStatement nameSql = RolapMappingFactory.eINSTANCE.createSqlStatement();
        nameSql.getDialects().addAll(List.of("generic", "h2"));
        nameSql.setSql("\"KEY\" || ' ' || \"KEY1\"");

        SQLExpressionColumn nameExpressionColumn = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        nameExpressionColumn.setName("nameExpression");
        nameExpressionColumn.setId("_nameExpression");
        nameExpressionColumn.getSqls().add(nameSql);

        SqlStatement keySql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        keySql1.getDialects().addAll(List.of("generic"));
        keySql1.setSql("KEY");
        SqlStatement keySql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        keySql2.getDialects().addAll(List.of("h2"));
        keySql2.setSql("\"KEY1\" || ' ' || \"KEY\"");

        SQLExpressionColumn keyExpression = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        keyExpression.setName("keyExpression");
        keyExpression.setId("_keyExpression");
        keyExpression.getSqls().addAll(List.of(keySql1, keySql2));

        SqlStatement captionSql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        captionSql1.getDialects().addAll(List.of("generic"));
        captionSql1.setSql("KEY");
        SqlStatement captionSql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        captionSql2.getDialects().addAll(List.of("h2"));
        captionSql2.setSql("\"KEY1\" || '___' || \"KEY\"");

        SQLExpressionColumn captionExpression = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        captionExpression.setName("captionExpression");
        captionExpression.setId("_captionExpression");
        captionExpression.getSqls().addAll(List.of(captionSql1, captionSql2));

        SqlStatement ordinalSql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        ordinalSql1.getDialects().addAll(List.of("generic", "h2"));
        ordinalSql1.setSql("\"KEY\" || '___' || \"KEY1\"");

        SQLExpressionColumn ordinalExpression = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        ordinalExpression.setName("ordinalExpression");
        ordinalExpression.setId("_ordinalExpression");
        ordinalExpression.getSqls().addAll(List.of(ordinalSql1));

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, key1Column, valueColumn,nameExpressionColumn,keyExpression,captionExpression,ordinalExpression));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_table_factQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_level1");
        level1.setColumn(keyColumn);
        level1.setNameColumn(nameExpressionColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("_level2");
        level2.setColumn(keyExpression);
        level2.setCaptionColumn(captionExpression);
        level2.setOrdinalColumn(ordinalExpression);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("_hierarchywithhasall");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(level1, level2));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dimension");
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level Expressions");
        catalog.setDescription("Level with expression-based definitions");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Level Expressions", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query Fact", queryFactBody, 1, 2, 0, true, 2);
        document(level1, "Level1", level1Body, 1, 3, 0, true, 0);
        document(level2, "Level2", level2Body, 1, 4, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 5, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 6, 0, true, 0);

        document(measure, "Measure", measureBody, 1, 7, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 8, 0, true, 2);

        return catalog;
    }

}
