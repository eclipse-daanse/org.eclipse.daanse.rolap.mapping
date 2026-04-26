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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.TextAggMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.02.08", source = Source.EMF, group = "Measure")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private OrderedColumn orderedColumn;
    private Schema databaseSchema;


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
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnCountry = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnCountry.setName("COUNTRY");
        columnCountry.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnContinent = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnContinent.setName("CONTINENT");
        columnContinent.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnYear = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnYear.setName("YEAR");
        columnYear.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnMonth = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMonth.setName("MONTH");
        columnMonth.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnMonthName = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMonthName.setName("MONTH_NAME");
        columnMonthName.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnUser = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnUser.setName("USER");
        columnUser.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnComment = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnComment.setName("COMMENT");
        columnComment.setType(SqlSimpleTypes.Sql99.varcharType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn, columnCountry, columnContinent, columnYear, columnMonth, columnMonthName, columnUser, columnComment));
        databaseSchema.getOwnedElement().add(table);


        SqlStatement sqlStatement = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("CONCAT(\"Fact\".\"USER\", ' : ',  \"Fact\".\"COMMENT\")");
        sqlStatement.getDialects().add("generic");
        sqlStatement.getDialects().add("h2");

        ExpressionColumn c = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createExpressionColumn();
        c.setName("sql_expression");
        c.getSqls().add(sqlStatement);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value");
        measure1.setColumn(valueColumn);

        orderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(columnComment);

        TextAggMeasure measure2 = MeasureFactory.eINSTANCE.createTextAggMeasure();
        measure2.setName("Comment");
        measure2.setSeparator(", ");
        measure2.setColumn(c);
        measure2.getOrderByColumns().add(orderedColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        Level levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(keyColumn);

        Level levelCountry = LevelFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setColumn(columnCountry);

        Level levelContinent = LevelFactory.eINSTANCE.createLevel();
        levelContinent.setName("Continent");
        levelContinent.setColumn(columnContinent);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(levelContinent);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnCountry);

        Level levelYear = LevelFactory.eINSTANCE.createLevel();
        levelYear.setType(LevelDefinition.TIME_YEARS);
        levelYear.setName("Year");
        levelYear.setColumn(columnYear);

        Level levelMonth = LevelFactory.eINSTANCE.createLevel();
        levelMonth.setType(LevelDefinition.TIME_MONTHS);
        levelMonth.setName("Month");
        levelMonth.setColumn(columnMonth);
        levelMonth.setNameColumn(columnMonthName);

        ExplicitHierarchy hierarchy1 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setName("TimeHierarchy");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(levelYear);
        hierarchy1.getLevels().add(levelMonth);

        TimeDimension dimensionTime = DimensionFactory.eINSTANCE.createTimeDimension();
        dimensionTime.setName("Time");
        dimensionTime.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setDimension(dimensionTime);
        dimensionConnector2.setForeignKey(columnYear);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresTextAggregatorsCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalog.setDescription("Text aggregation functions");
        catalog.getCubes().add(cube);


            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Aggregator Text Agg", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Ordered Column", orderedColumnBody, 1, 2, 0, orderedColumn, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
