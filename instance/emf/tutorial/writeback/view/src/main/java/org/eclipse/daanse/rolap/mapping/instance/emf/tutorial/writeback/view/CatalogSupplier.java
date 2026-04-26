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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.view;


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
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
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
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackTable;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.05.03", source = Source.EMF, group = "Writeback") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private JoinSource joinQuery;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private SqlSelectSource query;
    private Level l2Level;
    private Level l1Level;
    private TableSource l1Query;
    private TableSource l2Query;


    private static final String CUBE = "C";
    private static final String FACT = "FACT";

    private static final String catalogBody = """
    This tutorial discusses writeback with fact as sql view.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the
            - SqlView `FACT` with 3 columns `VAL`, `VAL1`, `L2`. The `L2` column is used as the discriminator in the Level and Hierarchy definitions
            - `L1` table with two columns: `L1` and `L2`.
            - `L2` table with one column: `L2`.
            - `FACTWB` table with 4 columns: `VAL`, `VAL1`, `ID`, `USER`.
            """;

    private static final String queryBody = """
            The SqlSelectSource with `FACT` alias that selects all columns from the Fact DialectSqlView to use in the cube for the measures. DialectSqlView have description as sql query
            """;

    private static final String query1Body = """
            The l1Query is a simple TableSource that selects all columns from the `L1` table to use in the cube for the `L1` level.
            """;

    private static final String query2Body = """
            The l2Query is a simple TableSource that selects all columns from the `L2` table to use in the cube for the `L2` level.
            """;

    private static final String joinBody = """
            The joinQuery is a simple JoinedQuery that unites `l1TableQuery` and `l2TableQuery` by L2 column.
            """;

    private static final String level1Body = """
            This Example uses one simple `L1` level based on the `L1` column. `L2` column to use for connection to level `L2`
            """;
    private static final String level2Body = """
            This Example uses one simple `L2` level based on the `L2` column. `L2` column to use for connection to level `L1`
            """;

    private static final String hierarchyBody = """
            The Hierarchy is defined with the hasAll property set to true and the two levels.
            """;

    private static final String dimensionBody = """
            The dimension is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            Cube C is defined by DimensionConnector D1 and a MeasureGroup containing two measures using `SUM` aggregation.
            The cube also contains a `FACTWB` WritebackTable configuration with a WritebackAttribute mapped to the `VAL` column from the fact table, along with two WritebackMeasures: WbMeasure1 and WbMeasure2.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column valColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valColumn.setName("VAL");
        valColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column val1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        val1Column.setName("VAL1");
        val1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column l2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        l2Column.setName("L2");
        l2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        SqlStatement sqlStatement = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement.getDialects().addAll(List.of("generic", "h1"));
        sqlStatement.setSql("select * from FACT");
        DialectSqlView sqlView = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createDialectSqlView();
        sqlView.setName(FACT);
        sqlView.getFeature().addAll(List.of(valColumn, val1Column, l2Column));
        sqlView.getDialectStatements().add(sqlStatement);
        databaseSchema.getOwnedElement().add(sqlView);

        Column l1L1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        l1L1Column.setName("L1");
        l1L1Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column l1L2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        l1L2Column.setName("L2");
        l1L2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Table l1Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        l1Table.setName("L1");
        l1Table.getFeature().addAll(List.of(l1L1Column, l1L2Column));
        databaseSchema.getOwnedElement().add(l1Table);

        Column l2L2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        l2L2Column.setName("L2");
        l2L2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Table l2Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        l2Table.setName("L2");
        l2Table.getFeature().addAll(List.of(l2L2Column));
        databaseSchema.getOwnedElement().add(l2Table);

        Column factwbValColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column factwbVal1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column factwbL2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbL2Column.setName("L2");
        factwbL2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column factwbIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column factwbUserColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table factwbTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factwbTable.setName("FACTWB");
        factwbTable.getFeature()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getOwnedElement().add(factwbTable);

        query = SourceFactory.eINSTANCE.createSqlSelectSource();
        query.setSql(sqlView);
        query.setAlias(FACT);

        l1Query = SourceFactory.eINSTANCE.createTableSource();
        l1Query.setTable(l1Table);

        l2Query = SourceFactory.eINSTANCE.createTableSource();
        l2Query.setTable(l2Table);

        JoinedQueryElement joinLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinLeft.setKey(l1L2Column);
        joinLeft.setQuery(l1Query);
        JoinedQueryElement joinRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinRight.setKey(l2L2Column);
        joinRight.setQuery(l2Query);

        joinQuery = SourceFactory.eINSTANCE.createJoinSource();
        joinQuery.setLeft(joinLeft);
        joinQuery.setRight(joinRight);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setColumn(valColumn);

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        l1Level = LevelFactory.eINSTANCE.createLevel();
        l1Level.setName("L1");
        l1Level.setColumn(l1L1Column);

        l2Level = LevelFactory.eINSTANCE.createLevel();
        l2Level.setName("L2");
        l2Level.setColumn(l2L2Column);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(l1L2Column);
        hierarchy.setQuery(joinQuery);
        hierarchy.getLevels().addAll(List.of(l1Level, l2Level));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("D1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(l1L2Column);

        WritebackAttribute writebackAttribute = WritebackFactory.eINSTANCE.createWritebackAttribute();
        writebackAttribute.setDimensionConnector(dimensionConnector);
        writebackAttribute.setColumn(l2Column);

        WritebackMeasure writebackMeasure1 = WritebackFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = WritebackFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("Measure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackAttribute().add(writebackAttribute);
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.setWritebackTable(writebackTable);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Writeback View");
        catalog.setDescription("View-based writeback functionality");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);




            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Writeback View", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("FactQuery", queryBody, 1, 2, 0, query, 2),
                        new DocSection("l1TableQuery", query1Body, 1, 3, 0, l1Query, 2),
                        new DocSection("l21TableQuery", query2Body, 1, 4, 0, l2Query, 2),
                        new DocSection("joinQuery", joinBody, 1, 5, 0, joinQuery, 2),
                        new DocSection("L1", level1Body, 1, 6, 0, l1Level, 0),
                        new DocSection("L2", level2Body, 1, 7, 0, l2Level, 0),
                        new DocSection("HierarchyWithHasAll", hierarchyBody, 1, 8, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cubec C", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
