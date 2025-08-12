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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.1", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE_NAME = "Cube With MeasureExpression";
    private static final String FACT = "FACT";
    private static final String MEASURE_TABLE = "MEASURE_TABLE";

    private static final String catalogBody = """
            Data cube with measure Expression.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data.
            The table, named `Fact`, contains two columns: `KEY` and `VALUE`.
            The table, named `MEASURE_TABLE`, contains 3 columns: `ID`, `VALUE` and 'FLAG'.
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
            Measure with SQLExpressionColumn as column. measure use SQL expression to MEASURE_TABL table.
    """;

    private static final String measure2Body = """
            Measure with SQLExpressionColumn as column. measure use SQL expression to FACT table.
    """;

    private static final String cubeBody = """
            In this example, measure with SQLExpressionColumn. Measures use SQL expression as column.
            """;

    private static final String catalogDocumentationTxt = """
            A mininmal cube with a simple measure with SQLExpressionColumn.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        Column valueNumericColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setId("_Fact_VALUE_NUMERIC");
        valueNumericColumn.setType(ColumnType.NUMERIC);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_FACT");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        Column idColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        idColumn.setName("ID");
        idColumn.setId("_MEASURE_TABLE_ID");
        idColumn.setType(ColumnType.INTEGER);

        Column value1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value1Column.setName("VALUE");
        value1Column.setId("_MEASURE_TABLE_VALUE");
        value1Column.setType(ColumnType.INTEGER);

        Column flagColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        flagColumn.setName("FLAG");
        flagColumn.setId("_MEASURE_TABLE_FLAG");
        flagColumn.setType(ColumnType.INTEGER);

        SqlStatement sql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sql1.getDialects().addAll(List.of("generic", "h2"));
        sql1.setSql(
                "(select sum(\"MEASURE_TABLE\".\"VALUE\") from \"MEASURE_TABLE\" where \"MEASURE_TABLE\".\"FLAG\" = 1)");

        SqlStatement sql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sql2.getDialects().addAll(List.of("generic", "h2"));
        sql2.setSql("(CASE WHEN \"FACT\".\"VALUE\" > 21 THEN 50 ELSE \"FACT\".\"VALUE\" END)");

        SQLExpressionColumn measureExpression1 = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        measureExpression1.setName("measureExpression1");
        measureExpression1.setId("_measureExpression1");
        measureExpression1.getSqls().addAll(List.of(sql1));

        SQLExpressionColumn measureExpression2 = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        measureExpression2.setName("measureExpression2");
        measureExpression2.setId("_measureExpression2");
        measureExpression2.getSqls().addAll(List.of(sql2));

        PhysicalTable table1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table1.setName(MEASURE_TABLE);
        table1.setId("_MEASURE_TABLE");
        table1.getColumns().addAll(List.of(idColumn, value1Column, flagColumn, measureExpression1, measureExpression2));
        databaseSchema.getTables().add(table1);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1-Sum");
        measure1.setId("_Measure1-Sum");
        measure1.setColumn(measureExpression1);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2-Sum");
        measure2.setId("_Measure2-Sum");
        measure2.setColumn(measureExpression2);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal Cube With SQLExpressionColumn");
        catalog.setDescription("Minimal Cube With SQLExpressionColumn");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Minimal Cube With SQLExpressionColumn", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(measureExpression1, "MeasureExpression1", measureExpression1Body, 1, 3, 0, true, 2);
        document(measureExpression2, "MeasureExpression2", measureExpression2Body, 1, 4, 0, true, 2);
        document(measure1, "Measure1", measure1Body, 1, 5, 0, true, 2);
        document(measure1, "Measure1", measure2Body, 1, 6, 0, true, 2);
        document(cube, "Cube with Measures CellFormatter", cubeBody, 1, 7, 0, true, 2);

        return catalog;

    }

}
