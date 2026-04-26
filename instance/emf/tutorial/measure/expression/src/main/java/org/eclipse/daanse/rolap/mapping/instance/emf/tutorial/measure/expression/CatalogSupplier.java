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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.expression;


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
import org.eclipse.daanse.rolap.mapping.model.database.relational.ExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.01", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private ExpressionColumn measureExpression2;
    private TableSource query;
    private ExpressionColumn measureExpression1;
    private SumMeasure measure1;


    private static final String CUBE_NAME = "Cube With MeasureExpression";
    private static final String FACT = "FACT";
    private static final String MEASURE_TABLE = "MEASURE_TABLE";

    private static final String catalogBody = """
            Data cube with measure Expression.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a two tables that stores all the data.
            - The table, named `Fact`, contains two columns: `KEY` and `VALUE`.
            - The table, named `MEASURE_TABLE`, contains 3 columns: `ID`, `VALUE` and 'FLAG'.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String measureExpression1Body = """
            Specialized formatter for controlling the presentation of cell values in analytical grids.
            Cell formatter use reference to class formatter mondrian.rolap.format.CellFormatterImpl implemented CellFormatter interface
    """;

    private static final String measureExpression2Body = """
            Specialized formatter for controlling the presentation of cell values in analytical grids.
            Cell formatter use reference to class formatter mondrian.rolap.format.CellFormatterImpl implemented CellFormatter interface
    """;

    private static final String measure1Body = """
            Measure with ExpressionColumn as column. measure use SQL expression to MEASURE_TABL table.
    """;

    private static final String measure2Body = """
            Measure with ExpressionColumn as column. measure use SQL expression to FACT table.
    """;

    private static final String cubeBody = """
            In this example, measure with SQLExpressionColumn. Measures use SQL expression as column.
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

        Column valueNumericColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setType(SqlSimpleTypes.numericType(18, 4));

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getOwnedElement().add(table);

        Column idColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        idColumn.setName("ID");
        idColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column value1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value1Column.setName("VALUE");
        value1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column flagColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        flagColumn.setName("FLAG");
        flagColumn.setType(SqlSimpleTypes.Sql99.integerType());

        SqlStatement sql1 = SourceFactory.eINSTANCE.createSqlStatement();
        sql1.getDialects().addAll(List.of("generic", "h2"));
        sql1.setSql(
                "(select sum(\"MEASURE_TABLE\".\"VALUE\") from \"MEASURE_TABLE\" where \"MEASURE_TABLE\".\"FLAG\" = 1)");

        SqlStatement sql2 = SourceFactory.eINSTANCE.createSqlStatement();
        sql2.getDialects().addAll(List.of("generic", "h2"));
        sql2.setSql("(CASE WHEN \"FACT\".\"VALUE\" > 21 THEN 50 ELSE \"FACT\".\"VALUE\" END)");

        measureExpression1 = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        measureExpression1.setName("measureExpression1");
        measureExpression1.getSqls().addAll(List.of(sql1));

        measureExpression2 = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        measureExpression2.setName("measureExpression2");
        measureExpression2.getSqls().addAll(List.of(sql2));

        Table table1 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table1.setName(MEASURE_TABLE);
        table1.getFeature().addAll(List.of(idColumn, value1Column, flagColumn, measureExpression1, measureExpression2));
        databaseSchema.getOwnedElement().add(table1);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1-Sum");
        measure1.setColumn(measureExpression1);

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2-Sum");
        measure2.setColumn(measureExpression2);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Measure Expression");
        catalog.setDescription("Measure with expression-based calculations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Expression", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("MeasureExpression1", measureExpression1Body, 1, 3, 0, measureExpression1, 2),
                        new DocSection("MeasureExpression2", measureExpression2Body, 1, 4, 0, measureExpression2, 2),
                        new DocSection("Measure1", measure1Body, 1, 5, 0, measure1, 2),
                        new DocSection("Measure1", measure2Body, 1, 6, 0, measure1, 2),
                        new DocSection("Cube with Measures CellFormatter", cubeBody, 1, 7, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
