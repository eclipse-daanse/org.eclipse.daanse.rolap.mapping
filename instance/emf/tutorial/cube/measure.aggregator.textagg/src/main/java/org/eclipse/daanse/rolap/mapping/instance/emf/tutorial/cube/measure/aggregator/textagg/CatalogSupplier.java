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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.textagg;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TextAggMeasure;
import org.eclipse.daanse.rolap.mapping.model.TimeDimension;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.8", source = Source.EMF, group = "Measure")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Data cubes have multiple measures when different aggregations are required for a column.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String orderedColumnBody = """
            Represents a column with specific ordering information used in queries and result sets.
            OrderedColumn is typically used in OLAP contexts where explicit column ordering is required for query processing or result presentation.
            OrderedColumn uses ascending by default.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following aggregation functions:
            - SUM – Calculates the sum of the values.
            - TextAgg – Text aggregation
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_main");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        Column columnCountry = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnCountry.setName("COUNTRY");
        columnCountry.setId("_column_fact_country");
        columnCountry.setType(ColumnType.VARCHAR);

        Column columnContinent = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnContinent.setName("CONTINENT");
        columnContinent.setId("_column_fact_continent");
        columnContinent.setType(ColumnType.VARCHAR);

        Column columnYear = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnYear.setName("YEAR");
        columnYear.setId("_column_fact_year");
        columnYear.setType(ColumnType.INTEGER);

        Column columnMonth = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMonth.setName("MONTH");
        columnMonth.setId("_column_fact_month");
        columnMonth.setType(ColumnType.INTEGER);

        Column columnMonthName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMonthName.setName("MONTH_NAME");
        columnMonthName.setId("_column_fact_monthName");
        columnMonthName.setType(ColumnType.VARCHAR);

        Column columnUser = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnUser.setName("USER");
        columnUser.setId("_column_fact_user");
        columnUser.setType(ColumnType.VARCHAR);

        Column columnComment = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnComment.setName("COMMENT");
        columnComment.setId("_column_fact_comment");
        columnComment.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, columnCountry, columnContinent, columnYear, columnMonth, columnMonthName, columnUser, columnComment));
        databaseSchema.getTables().add(table);


        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("CONCAT(\"Fact\".\"USER\", ' : ',  \"Fact\".\"COMMENT\")");
        sqlStatement.getDialects().add("generic");
        sqlStatement.getDialects().add("h2");

        SQLExpressionColumn c = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        c.setName("sql_expression");
        c.setId("_sqlExpressionColumn_userComment");
        c.getSqls().add(sqlStatement);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value");
        measure1.setId("_measure_sumValue");
        measure1.setColumn(valueColumn);

        OrderedColumn orderedColumn = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setId("_ordered_column_col_fact_value");
        orderedColumn.setColumn(columnComment);

        TextAggMeasure measure2 = RolapMappingFactory.eINSTANCE.createTextAggMeasure();
        measure2.setName("Comment");
        measure2.setId("_measure_comment");
        measure2.setSeparator(", ");
        measure2.setColumn(c);
        measure2.getOrderByColumns().add(orderedColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        Level levelTown = RolapMappingFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setId("_level_town");
        levelTown.setColumn(keyColumn);

        Level levelCountry = RolapMappingFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setId("_level_country");
        levelCountry.setColumn(columnCountry);

        Level levelContinent = RolapMappingFactory.eINSTANCE.createLevel();
        levelContinent.setName("Continent");
        levelContinent.setId("_level_continent");
        levelContinent.setColumn(columnContinent);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_townHierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(levelContinent);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.setId("_dimension_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dimensionConnector_town");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnCountry);

        Level levelYear = RolapMappingFactory.eINSTANCE.createLevel();
        levelYear.setType(LevelDefinition.TIME_YEARS);
        levelYear.setName("Year");
        levelYear.setId("_level_year");
        levelYear.setColumn(columnYear);

        Level levelMonth = RolapMappingFactory.eINSTANCE.createLevel();
        levelMonth.setType(LevelDefinition.TIME_MONTHS);
        levelMonth.setName("Month");
        levelMonth.setId("_level_month");
        levelMonth.setColumn(columnMonth);
        levelMonth.setNameColumn(columnMonthName);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setName("TimeHierarchy");
        hierarchy1.setId("_hierarchy_timeHierarchy");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(levelYear);
        hierarchy1.getLevels().add(levelMonth);

        TimeDimension dimensionTime = RolapMappingFactory.eINSTANCE.createTimeDimension();
        dimensionTime.setName("Time");
        dimensionTime.setId("_dimension_time");
        dimensionTime.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setId("_dimensionConnector_time");
        dimensionConnector2.setDimension(dimensionTime);
        dimensionConnector2.setForeignKey(columnYear);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresTextAggregatorsCube");
        cube.setId("_cube_measuresTextAggregators");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalog.setDescription("Text aggregation functions");
        catalog.getCubes().add(cube);

        document(catalog, "Daanse Tutorial - Measure Aggregator Text Agg", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(orderedColumn, "Ordered Column", orderedColumnBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Measure with Text Aggregator", cubeBody, 1, 3, 0, true, 2);
        return catalog;

    }

}
