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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchywithinnertable;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_17_Cube_with_share_dimension_with_inner_table_reference";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with a level with reference with inner table
            Share dimension Dimension1 is defined in a <Dimension> element inside <Schema> element.
            Hierarchy is defined in a <Hierarchy> element inside <Dimension> element.
            Hierarchy have reference to inner table element
            Cube dimension Dimension1 have link on share dimension Dimension1 as source.


                Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
                This example schema contains one cube named "Cube1".

                A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
                In this case the database table representing the fact table is named "Fact" in the database, which is adressed in the name attribute within the <Table> tag.

                Each measurement of the cube is defined in a separate <Measure> element.
                The measurement in this example cube is named "Measure" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
                Level is defined in <Level> element.
                Property is defined in <Property> element inside <Level> element. Property we can see in cell tooltip in excel
                                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("Fact_DIM_KEY");
        dimKeyColumn.setType("VARCHAR");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column htKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        htKeyColumn.setName("KEY");
        htKeyColumn.setId("HT_KEY");
        htKeyColumn.setType("VARCHAR");

        Column htValueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setId("HT_VALUE");
        htValueColumn.setType("NUMERIC");

        Column htNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        htNameColumn.setName("NAME");
        htNameColumn.setId("HT_NAME");
        htNameColumn.setType("VARCHAR");

        RowValue r1v1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v1.setColumn(htKeyColumn);
        r1v1.setValue("1");

        RowValue r1v2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v2.setColumn(htValueColumn);
        r1v2.setValue("100.5");

        RowValue r1v3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v3.setColumn(htNameColumn);
        r1v3.setValue("name1");

        Row r1 = RolapMappingFactory.eINSTANCE.createRow();
        r1.getRowValues().addAll(List.of(r1v1, r1v2, r1v3));

        RowValue r2v1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v1.setColumn(htKeyColumn);
        r2v1.setValue("2");

        RowValue r2v2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v2.setColumn(htValueColumn);
        r2v2.setValue("100.2");

        RowValue r2v3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v3.setColumn(htNameColumn);
        r2v3.setValue("name2");

        Row r2 = RolapMappingFactory.eINSTANCE.createRow();
        r2.getRowValues().addAll(List.of(r2v1, r2v2, r2v3));
        InlineTable inlineTable = RolapMappingFactory.eINSTANCE.createInlineTable();
        inlineTable.setName("HT");
        inlineTable.getColumns().addAll(List.of(htKeyColumn, htValueColumn, htNameColumn));
        inlineTable.getRows().addAll(List.of(r1, r2));
        databaseSchema.getTables().add(inlineTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        InlineTableQuery inlineTableQuery = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        inlineTableQuery.setId("inlineTableQuery");
        inlineTableQuery.setTable(inlineTable);
        inlineTableQuery.setAlias("HT");

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("Level1");
        level1.setColumn(htKeyColumn);
        level1.setNameColumn(htNameColumn);
        level1.setColumnType(ColumnDataType.INTEGER);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(htKeyColumn);
        hierarchy.setQuery(inlineTableQuery);
        hierarchy.setPrimaryKeyTable(inlineTable);
        hierarchy.getLevels().addAll(List.of(level1));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_dimension_with hierarchy_with_inner_table");
        catalog.setDescription("Schema with hierarchy with table reference with inner table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("Catalog with schema with hierarchy with table reference with inner table");
        catalog.setDocumentation(documentation);
        return catalog;
    }
}
