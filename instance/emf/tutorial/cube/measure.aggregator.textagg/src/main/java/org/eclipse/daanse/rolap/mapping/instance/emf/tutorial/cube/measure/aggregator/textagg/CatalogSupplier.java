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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TextAggMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.100", source = Source.EMF)
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Data cubes can also have multiple measures when different aggregations are required for a column.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following aggregation functions:
            - SUM – Calculates the sum of the values.
            - TextAgg – Text aggregation
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col");
        valueColumn.setType(ColumnType.INTEGER);

        Column columnCountry = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnCountry.setName("COUNTRY");
        columnCountry.setId("_col_fact_country");
        columnCountry.setType(ColumnType.VARCHAR);

        Column columnContinent = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnContinent.setName("CONTINENT");
        columnContinent.setId("_col_fact_cntinent");
        columnContinent.setType(ColumnType.VARCHAR);

        Column columnYear = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnYear.setName("YEAR");
        columnYear.setId("_col_fact_year");
        columnYear.setType(ColumnType.INTEGER);

        Column columnMonth = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMonth.setName("MONTH");
        columnMonth.setId("_col_fact_month");
        columnMonth.setType(ColumnType.INTEGER);

        Column columnMonthName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMonthName.setName("MONTH_NAME");
        columnMonthName.setId("_col_fact_month_name");
        columnMonthName.setType(ColumnType.VARCHAR);

        Column columnUser = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnUser.setName("USER");
        columnUser.setId("_col_fact_user");
        columnUser.setType(ColumnType.VARCHAR);

        Column columnComment = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnComment.setName("COMMENT");
        columnComment.setId("_col_fact_comment");
        columnComment.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, columnCountry, columnContinent, columnYear, columnMonth, columnMonthName, columnUser, columnComment));
        databaseSchema.getTables().add(table);


        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("CONCAT(\"Fact\".\"USER\", ' : ',  \"Fact\".\"COMMENT\")");
        sqlStatement.getDialects().add("generic");
        sqlStatement.getDialects().add("h2");

        SQLExpressionColumn c = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        c.setName("sql_expression");
        c.setId("sql_expression");
        c.getSqls().add(sqlStatement);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value");
        measure1.setId("_measure1");
        measure1.setColumn(valueColumn);

        OrderedColumn orderedColumn = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(columnComment);
        orderedColumn.setId("orderedColumn");

        TextAggMeasure measure2 = RolapMappingFactory.eINSTANCE.createTextAggMeasure();
        measure2.setName("Comment");
        measure2.setId("_measure6");
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

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_town");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(levelContinent);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.setId("_dim_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
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

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setName("TimeHierarchy");
        hierarchy1.setId("_hierarchy_time");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(levelYear);
        hierarchy1.getLevels().add(levelMonth);

        TimeDimension dimensionTime = RolapMappingFactory.eINSTANCE.createTimeDimension();
        dimensionTime.setName("Time");
        dimensionTime.setId("_dim_time");
        dimensionTime.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setDimension(dimensionTime);
        dimensionConnector2.setForeignKey(columnYear);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresTextAggregatorsCube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Cube - Measures and Text Aggregators with comments");
        catalog.getCubes().add(cube);

        document(catalog, "Multiple Measures and Aggragators", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Measure", cubeBody, 1, 3, 0, true, 2);
        return catalog;

    }

}
