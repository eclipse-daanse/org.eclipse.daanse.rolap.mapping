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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.view;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlSelectSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.database.relational.DialectSqlView;
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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.02", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private SqlSelectSource sqlSelectQuery;
    private SumMeasure measure;
    private Level level;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Cube with hierarchy which use SQL query. This example shows combine phisical table as fact and DialectSqlView for hierarchy
            DialectSqlView represents a virtual table defined by SQL query expressions rather than physical database tables.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a two tables and and DialectSqlView that stores all the data.

            - The phisical table is named `Fact` uses for Cube1 and contains two columns: `DIM_KEY` and `VALUE`.
                The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            - The phisical table is named `HT` uses for Hierarchy and contains 3 columns: `KEY`, `VALUE`,`NAME` .
            - DialectSqlView represents a virtual table defined by SQL query expressions rather than physical database tables.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String sqlSelectQueryBody = """
            The bridge between the cube and SqlView `HT_VIEW`.
            """;

    private static final String level1Body = """
            The Level uses the column attribute to specify the primary key `KEY` from SqlView `HT_VIEW`.
            Additionally, it defines the nameColumn `NAME` from SqlView `HT_VIEW` attribute  to specify
            the column that contains the name of the level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists level Level1.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            Query uses SqlView `HT_VIEW` as data sourse.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measure1Body = """
            Measure1 use Fact table VALUE column with sum aggregation in Cube.
            """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data. This example shows combine phisical table as fact and DialectSqlView for hierarchy
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column dimKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumn.setName("NAME");
        nameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column htValueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table htTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        htTable.setName("HT");
        htTable.getFeature().addAll(List.of(keyColumn, nameColumn, htValueColumn));
        databaseSchema.getOwnedElement().add(htTable);

        SqlStatement sqlStatement = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement.getDialects().addAll(List.of("generic", "h2"));
        sqlStatement.setSql("select * from HT");
        DialectSqlView sqlView = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createDialectSqlView();
        sqlView.setName("HT_VIEW");
        sqlView.getDialectStatements().add(sqlStatement);

        Column keyViewColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyViewColumn.setName("KEY");
        keyViewColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameViewColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameViewColumn.setName("NAME");
        nameViewColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column htValueViewColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        htValueViewColumn.setName("VALUE");
        htValueViewColumn.setType(SqlSimpleTypes.Sql99.integerType());

        sqlView.getFeature().addAll(List.of(keyViewColumn, nameViewColumn, htValueViewColumn));

        databaseSchema.getOwnedElement().add(sqlView);

        sqlSelectQuery = SourceFactory.eINSTANCE.createSqlSelectSource();
        sqlSelectQuery.setAlias("HT_VIEW");
        sqlSelectQuery.setSql(sqlView);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Level1");
        level.setColumn(keyViewColumn);
        level.setNameColumn(nameViewColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(keyViewColumn);
        hierarchy.setQuery(sqlSelectQuery);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setForeignKey(dimKeyColumn);
        dimensionConnector.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Hierarchy View");
        catalog.setDescription("Hierarchy with SQL view references");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Hierarchy View", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("HT_VIEW", sqlSelectQueryBody, 1, 3, 0, sqlSelectQuery, 2),
                        new DocSection("Dimension", dimensionBody, 1, 4, 0, dimension, 2),
                        new DocSection("Hierarchy", hierarchyBody, 1, 5, 0, hierarchy, 2),
                        new DocSection("Level1", level1Body, 1, 6, 0, level, 2),
                        new DocSection("Measure1", measure1Body, 1, 7, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 8, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
