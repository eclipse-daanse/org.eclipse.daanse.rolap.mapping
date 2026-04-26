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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.inlinetablewithphysical;


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
import org.eclipse.daanse.rolap.mapping.model.database.relational.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.database.source.InlineTableSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Row;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RowSet;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.DataSlot;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.InstanceFactory;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.03", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private Level levelCountry;
    private TableSource queryTown;
    private InlineTableSource queryCountry;
    private SumMeasure measure;
    private Level levelTown;
    private InlineTableSource queryFact;
    private JoinSource queryHierarchy;


    private static final String CUBE = "CubeTwoLevelsInlineAndPhysicalTable";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Data cube combines Physical and InlineTable.
            InlineTable represents a table with data embedded directly in the schema definition rather than referencing external database tables.
            InlineTable allows small lookup tables, dimension data, or test data to be included directly in the OLAP schema,
            eliminating the need for separate database tables for static reference data.
            """;

    private static final String databaseSchemaBody = """
            Schema includes InlineTable with data embedded directly in the schema definition and Physical table from database.
            - Physical table `TOWN` contains 3 columns: `KEY`, `KEY_COUNTRY` and `NAME`.
            - InlineTable, named `COUNTRY`, contains two columns: `KEY` and `NAME`.
            - InlineTable, named `Fact`, contains two columns: `KEY` and `VALUE`.
            """;

    private static final String queryFactBody = """
            This example uses a InlineTableQuery, as it directly references the InlineTable table `Fact`.
            """;

    private static final String queryTownBody = """
            This example uses a TableQuery, as it directly references the Physical table `TOWN`.
            """;

    private static final String queryCountryBody = """
            This example uses a InlineTableQuery, as it directly references the InlineTable table `COUNTRY`.
            """;

    private static final String joinQueryBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (TOWN - Physical table), the join uses the foreign key.
            - In the upper-level table (COUNTRY - InlineTable), the join uses the primary key.
            This JoinSource combins combines Physical and InlineTable
            """;

    private static final String levelTownBody = """
            The Level uses the column attribute to specify the primary key column. Additionally, it defines the nameColumn attribute to specify the column that contains the name of the level.
            """;

    private static final String levelCountryBody = """
            The Country level follows the same pattern as the Town level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists of two levels: Town and Country.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the queryHierarchy Join-query used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measureBody = """
            Measure use InlineTable column with sum aggregation.
    """;

    private static final String cubeBody = """
            In this example uses cube with InlineTable as data.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column townKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        townKeyColumn.setName("KEY");
        townKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column townCountryKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        townCountryKeyColumn.setName("KEY_COUNTRY");
        townCountryKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column townNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        townNameColumn.setName("NAME");
        townNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table townTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        townTable.setName("TOWN");
        townTable.getFeature().addAll(List.of(townKeyColumn, townCountryKeyColumn, townNameColumn));
        databaseSchema.getOwnedElement().add(townTable);

        Column countryKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        countryKeyColumn.setName("KEY");
        countryKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column countryNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        countryNameColumn.setName("NAME");
        countryNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        DataSlot rowCountryValue11 = InstanceFactory.eINSTANCE.createDataSlot();
        rowCountryValue11.setFeature(countryKeyColumn);
        rowCountryValue11.setDataValue("1");

        DataSlot rowCountryValue21 = InstanceFactory.eINSTANCE.createDataSlot();
        rowCountryValue21.setFeature(countryNameColumn);
        rowCountryValue21.setDataValue("Germany");

        Row rowCountry1 = RelationalFactory.eINSTANCE.createRow();
        rowCountry1.getSlot().addAll(List.of(rowCountryValue11, rowCountryValue21));

        DataSlot rowCountryValue12 = InstanceFactory.eINSTANCE.createDataSlot();
        rowCountryValue12.setFeature(countryKeyColumn);
        rowCountryValue12.setDataValue("2");

        DataSlot rowCountryValue22 = InstanceFactory.eINSTANCE.createDataSlot();
        rowCountryValue22.setFeature(countryNameColumn);
        rowCountryValue22.setDataValue("France");

        Row rowCountry2 = RelationalFactory.eINSTANCE.createRow();
        rowCountry2.getSlot().addAll(List.of(rowCountryValue12, rowCountryValue22));

        InlineTable countryTable = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createInlineTable();
        countryTable.setExtent(RelationalFactory.eINSTANCE.createRowSet());
        countryTable.setName("COUNTRY");
        countryTable.getFeature().addAll(List.of(countryKeyColumn, countryNameColumn));
        countryTable.getExtent().getOwnedElement().add(rowCountry1);
        countryTable.getExtent().getOwnedElement().add(rowCountry2);

        databaseSchema.getOwnedElement().add(countryTable);

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.doublePrecisionType());

        DataSlot rowValue11 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue11.setFeature(keyColumn);
        rowValue11.setDataValue("1");

        DataSlot rowValue21 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue21.setFeature(valueColumn);
        rowValue21.setDataValue("100.5");

        Row row1 = RelationalFactory.eINSTANCE.createRow();
        row1.getSlot().addAll(List.of(rowValue11, rowValue21));

        DataSlot rowValue12 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue12.setFeature(keyColumn);
        rowValue12.setDataValue("2");

        DataSlot rowValue22 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue22.setFeature(valueColumn);
        rowValue22.setDataValue("200.5");

        Row row2 = RelationalFactory.eINSTANCE.createRow();
        row2.getSlot().addAll(List.of(rowValue12, rowValue22));

        DataSlot rowValue13 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue13.setFeature(keyColumn);
        rowValue13.setDataValue("3");

        DataSlot rowValue23 = InstanceFactory.eINSTANCE.createDataSlot();
        rowValue23.setFeature(valueColumn);
        rowValue23.setDataValue("300.5");

        Row row3 = RelationalFactory.eINSTANCE.createRow();
        row3.getSlot().addAll(List.of(rowValue13, rowValue23));

        InlineTable table = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createInlineTable();
        table.setExtent(RelationalFactory.eINSTANCE.createRowSet());
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        table.getExtent().getOwnedElement().add(row1);
        table.getExtent().getOwnedElement().add(row2);
        table.getExtent().getOwnedElement().add(row3);

        databaseSchema.getOwnedElement().add(table);

        queryFact = SourceFactory.eINSTANCE.createInlineTableSource();
        queryFact.setAlias(FACT);
        queryFact.setTable(table);

        queryTown = SourceFactory.eINSTANCE.createTableSource();
        queryTown.setTable(townTable);

        queryCountry = SourceFactory.eINSTANCE.createInlineTableSource();
        queryCountry.setAlias("COUNTRY");
        queryCountry.setTable(countryTable);

        JoinedQueryElement joinQueryLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryLeft.setQuery(queryTown);
        joinQueryLeft.setKey(townCountryKeyColumn);

        JoinedQueryElement joinQueryRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryRight.setQuery(queryCountry);
        joinQueryRight.setKey(countryKeyColumn);

        queryHierarchy = SourceFactory.eINSTANCE.createJoinSource();
        queryHierarchy.setLeft(joinQueryLeft);
        queryHierarchy.setRight(joinQueryRight);

        levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(townKeyColumn);
        levelTown.setNameColumn(townNameColumn);

        levelCountry = LevelFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setColumn(countryKeyColumn);
        levelCountry.setNameColumn(countryNameColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(townKeyColumn);
        hierarchy.setQuery(queryHierarchy);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(keyColumn);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(queryFact);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Measure Inline Table With Physical");
        catalog.setDescription("Measure with inline table and physical table");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Inline Table With Physical", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryFactBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Query Town", queryTownBody, 1, 3, 0, queryTown, 2),
                        new DocSection("Query Country", queryCountryBody, 1, 4, 0, queryCountry, 2),
                        new DocSection("Join Query", joinQueryBody, 1, 5, 0, queryHierarchy, 2),
                        new DocSection("Level - Town", levelTownBody, 1, 8, 0, levelTown, 0),
                        new DocSection("Level - Country", levelCountryBody, 1, 9, 0, levelCountry, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 10, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 11, 0, dimension, 0),
                        new DocSection("Measure-Sum", measureBody, 1, 12, 0, measure, 2),
                        new DocSection("Cube with Phisical and Inline Tables", cubeBody, 1, 13, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
