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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measureexpression;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpression;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-04-07_Cube_Measure_MeasureExpression";
    private static final String CUBE_NAME = "CubeOneNumericMeasureDifferentFormatStrings";
    private static final String FACT = "FACT";
    private static final String MEASURE_TABLE = "MEASURE_TABLE";

    private static final String schemaDocumentationTxt = """
            A mininmal cube with a simple measure with MeasureExpression.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        Column valueNumericColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueNumericColumn.setName("VALUE_NUMERIC");
        valueNumericColumn.setId("Fact_VALUE_NUMERIC");
        valueNumericColumn.setType(ColumnType.NUMERIC);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn, valueNumericColumn));
        databaseSchema.getTables().add(table);

        Column idColumn = RolapMappingFactory.eINSTANCE.createColumn();
        idColumn.setName("ID");
        idColumn.setId("MEASURE_TABLE_ID");
        idColumn.setType(ColumnType.INTEGER);

        Column value1Column = RolapMappingFactory.eINSTANCE.createColumn();
        value1Column.setName("VALUE");
        value1Column.setId("MEASURE_TABLE_VALUE");
        value1Column.setType(ColumnType.INTEGER);

        Column flagColumn = RolapMappingFactory.eINSTANCE.createColumn();
        flagColumn.setName("FLAG");
        flagColumn.setId("MEASURE_TABLE_FLAG");
        flagColumn.setType(ColumnType.INTEGER);

        PhysicalTable table1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table1.setName(MEASURE_TABLE);
        table1.setId(MEASURE_TABLE);
        table1.getColumns().addAll(List.of(idColumn, value1Column, flagColumn));
        databaseSchema.getTables().add(table1);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        SqlStatement sql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sql1.getDialects().addAll(List.of("generic", "h2"));
        sql1.setSql(
                "(select sum(\"MEASURE_TABLE\".\"VALUE\") from \"MEASURE_TABLE\" where \"MEASURE_TABLE\".\"FLAG\" = 1)");

        SqlStatement sql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sql2.getDialects().addAll(List.of("generic", "h2"));
        sql2.setSql("(CASE WHEN \"FACT\".\"VALUE\" > 21 THEN 50 ELSE \"FACT\".\"VALUE\" END)");

        SQLExpression measureExpression1 = RolapMappingFactory.eINSTANCE.createSQLExpression();
        measureExpression1.getSqls().addAll(List.of(sql1));

        SQLExpression measureExpression2 = RolapMappingFactory.eINSTANCE.createSQLExpression();
        measureExpression2.getSqls().addAll(List.of(sql2));

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1-Sum");
        measure1.setId("Measure1-Sum");
        measure1.setMeasureExpression(measureExpression1);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("Measure2-Sum");
        measure2.setId("Measure2-Sum");
        measure2.setMeasureExpression(measureExpression2);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setId(CUBE_NAME);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cubes_With_MeasureExpression");
        catalog.setDescription("Minimal Cube With MeasureExpression");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;

    }

}
