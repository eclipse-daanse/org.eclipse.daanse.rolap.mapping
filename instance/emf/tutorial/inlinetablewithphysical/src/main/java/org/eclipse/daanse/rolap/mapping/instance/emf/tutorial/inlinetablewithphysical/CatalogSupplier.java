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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.inlinetablewithphysical;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
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
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "CubeTwoLevelsInlineAndPhysicalTable";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A minimal cube based on an inline table
            with levels with phisical and inline tables

                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

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

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
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
        catalog.setName("CubeOneMeasureInlineTableLevelPhysicalAndInlineTables");
        catalog.setDescription(
                "Schema of a minimal cube consisting of one measurement and based on an virtual inline fact table and physical table town and country inline table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }
}
