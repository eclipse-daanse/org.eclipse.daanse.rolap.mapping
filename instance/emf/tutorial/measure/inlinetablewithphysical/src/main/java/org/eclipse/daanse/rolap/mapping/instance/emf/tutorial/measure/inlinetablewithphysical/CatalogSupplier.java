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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.12.3", source = Source.EMF, group = "Measure") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "CubeTwoLevelsInlineAndPhysicalTable";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Data cube combines Physical and InlineTable.
            InlineTable represents a table with data embedded directly in the schema definition rather than referencing external database tables.
            InlineTable allows small lookup tables, dimension data, or test data to be included directly in the OLAP schema,
            eliminating the need for separate database tables for static reference data.
            """;

    private static final String databaseSchemaBody = """
            DatabaseSchema includes InlineTable with data embedded directly in the schema definition and Physical table from database.
            Physical table TOWN contains 3 columns: `KEY`, `KEY_COUNTRY` and `NAME`.
            InlineTable, named `COUNTRY`, contains two columns: `KEY` and `NAME`.
            InlineTable, named `Fact`, contains two columns: `KEY` and `VALUE`.
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
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (TOWN - Physical table), the join uses the foreign key.
            - In the upper-level table (COUNTRY - InlineTable), the join uses the primary key.
            This JoinQuery combins combines Physical and InlineTable
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

    private static final String catalogDocumentationTxt = """
            A minimal cube based on an inline table and phisical table
            with levels with phisical and inline tables

                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_inlinetablewithphysical");

        Column townKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        townKeyColumn.setName("KEY");
        townKeyColumn.setId("_col_town_key");
        townKeyColumn.setType(ColumnType.INTEGER);

        Column townCountryKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        townCountryKeyColumn.setName("KEY_COUNTRY");
        townCountryKeyColumn.setId("_col_town_country_key");
        townCountryKeyColumn.setType(ColumnType.INTEGER);

        Column townNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        townNameColumn.setName("NAME");
        townNameColumn.setId("_col_level_name");
        townNameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable townTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        townTable.setName("TOWN");
        townTable.setId("_tab_TOWN_Physical");
        townTable.getColumns().addAll(List.of(townKeyColumn, townCountryKeyColumn, townNameColumn));
        databaseSchema.getTables().add(townTable);

        Column countryKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        countryKeyColumn.setName("KEY");
        countryKeyColumn.setId("_col_country_KEY");
        countryKeyColumn.setType(ColumnType.INTEGER);

        Column countryNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        countryNameColumn.setName("NAME");
        countryNameColumn.setId("_col_country_NAME");
        countryNameColumn.setType(ColumnType.VARCHAR);

        RowValue rowCountryValue11 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowCountryValue11.setColumn(countryKeyColumn);
        rowCountryValue11.setValue("1");

        RowValue rowCountryValue21 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowCountryValue21.setColumn(countryNameColumn);
        rowCountryValue21.setValue("Germany");

        Row rowCountry1 = RolapMappingFactory.eINSTANCE.createRow();
        rowCountry1.getRowValues().addAll(List.of(rowCountryValue11, rowCountryValue21));

        RowValue rowCountryValue12 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowCountryValue12.setColumn(countryKeyColumn);
        rowCountryValue12.setValue("2");

        RowValue rowCountryValue22 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowCountryValue22.setColumn(countryNameColumn);
        rowCountryValue22.setValue("France");

        Row rowCountry2 = RolapMappingFactory.eINSTANCE.createRow();
        rowCountry2.getRowValues().addAll(List.of(rowCountryValue12, rowCountryValue22));

        InlineTable countryTable = RolapMappingFactory.eINSTANCE.createInlineTable();
        countryTable.setName("COUNTRY");
        countryTable.setId("_tab_COUNTRY");
        countryTable.getColumns().addAll(List.of(countryKeyColumn, countryNameColumn));
        countryTable.getRows().add(rowCountry1);
        countryTable.getRows().add(rowCountry2);

        databaseSchema.getTables().add(countryTable);

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_Fact_KEY");
        keyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_Fact_VALUE");
        valueColumn.setType(ColumnType.DOUBLE);

        RowValue rowValue11 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue11.setColumn(keyColumn);
        rowValue11.setValue("1");

        RowValue rowValue21 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue21.setColumn(valueColumn);
        rowValue21.setValue("100.5");

        Row row1 = RolapMappingFactory.eINSTANCE.createRow();
        row1.getRowValues().addAll(List.of(rowValue11, rowValue21));

        RowValue rowValue12 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue12.setColumn(keyColumn);
        rowValue12.setValue("2");

        RowValue rowValue22 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue22.setColumn(valueColumn);
        rowValue22.setValue("200.5");

        Row row2 = RolapMappingFactory.eINSTANCE.createRow();
        row2.getRowValues().addAll(List.of(rowValue12, rowValue22));

        RowValue rowValue13 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue13.setColumn(keyColumn);
        rowValue13.setValue("3");

        RowValue rowValue23 = RolapMappingFactory.eINSTANCE.createRowValue();
        rowValue23.setColumn(valueColumn);
        rowValue23.setValue("300.5");

        Row row3 = RolapMappingFactory.eINSTANCE.createRow();
        row3.getRowValues().addAll(List.of(rowValue13, rowValue23));

        InlineTable table = RolapMappingFactory.eINSTANCE.createInlineTable();
        table.setName(FACT);
        table.setId("_tab_FACT");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        table.getRows().add(row1);
        table.getRows().add(row2);
        table.getRows().add(row3);

        databaseSchema.getTables().add(table);

        InlineTableQuery queryFact = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        queryFact.setId("_query_fact");
        queryFact.setAlias(FACT);
        queryFact.setTable(table);

        TableQuery queryTown = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryTown.setId("_query_town");
        queryTown.setTable(townTable);

        InlineTableQuery queryCountry = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        queryCountry.setId("_query_country");
        queryCountry.setAlias("COUNTRY");
        queryCountry.setTable(countryTable);

        JoinedQueryElement joinQueryLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryLeft.setQuery(queryTown);
        joinQueryLeft.setKey(townCountryKeyColumn);

        JoinedQueryElement joinQueryRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQueryRight.setQuery(queryCountry);
        joinQueryRight.setKey(countryKeyColumn);

        JoinQuery queryHierarchy = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryHierarchy.setId("_query_hierarchy");
        queryHierarchy.setLeft(joinQueryLeft);
        queryHierarchy.setRight(joinQueryRight);

        Level levelTown = RolapMappingFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setId("_level_town");
        levelTown.setColumn(townKeyColumn);
        levelTown.setNameColumn(townNameColumn);

        Level levelCountry = RolapMappingFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setId("_level_country");
        levelCountry.setColumn(countryKeyColumn);
        levelCountry.setNameColumn(countryNameColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy");
        hierarchy.setPrimaryKey(townKeyColumn);
        hierarchy.setQuery(queryHierarchy);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dim_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_town");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(keyColumn);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setId("_measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(queryFact);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with One Measure with Inline Table and with Levels with physical and inline tables");
        catalog.setDescription(
                "Schema of a minimal cube consisting of one measurement and based on an virtual inline fact table and physical table town and country inline table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Minimal Cube With Phisical and Inline Tables", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryFact, "Query Fact", queryFactBody, 1, 2, 0, true, 2);
        document(queryTown, "Query Town", queryTownBody, 1, 3, 0, true, 2);
        document(queryCountry, "Query Country", queryCountryBody, 1, 4, 0, true, 2);
        document(queryHierarchy, "Join Query", joinQueryBody, 1, 5, 0, true, 2);
        document(levelTown, "Level - Town", levelTownBody, 1, 8, 0, true, 0);
        document(levelCountry, "Level - Country", levelCountryBody, 1, 9, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 10, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 11, 0, true, 0);

        document(measure, "Measure-Sum", measureBody, 1, 12, 0, true, 2);
        document(cube, "Cube with Phisical and Inline Tables", cubeBody, 1, 13, 0, true, 2);

        return catalog;
    }
}
