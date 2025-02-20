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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writebackview;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlView;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_for_writeback_with_fact_view";
    private static final String CUBE = "C";
    private static final String FACT1 = "FACT1";

    private static final String schemaDocumentationTxt = """
            writeback with fact as view
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column valColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valColumn.setName("VAL");
        valColumn.setId("Fact_VAL");
        valColumn.setType(ColumnDataType.INTEGER);

        Column val1Column = RolapMappingFactory.eINSTANCE.createColumn();
        val1Column.setName("VAL1");
        val1Column.setId("Fact_VAL1");
        val1Column.setType(ColumnDataType.INTEGER);

        Column l2Column = RolapMappingFactory.eINSTANCE.createColumn();
        l2Column.setName("L2");
        l2Column.setId("Fact_VALUE");
        l2Column.setType(ColumnDataType.VARCHAR);
        l2Column.setColumnSize(100);

        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.getDialects().addAll(List.of("generic", "h1"));
        sqlStatement.setSql("select * from FACT");
        SqlView sqlView = RolapMappingFactory.eINSTANCE.createSqlView();
        sqlView.setName(FACT1);
        sqlView.setId(FACT1);
        sqlView.getColumns().addAll(List.of(valColumn, val1Column, l2Column));
        sqlView.getSqlStatements().add(sqlStatement);
        databaseSchema.getTables().add(sqlView);

        Column l1L1Column = RolapMappingFactory.eINSTANCE.createColumn();
        l1L1Column.setName("L1");
        l1L1Column.setId("L1_L1");
        l1L1Column.setType(ColumnDataType.VARCHAR);
        l1L1Column.setColumnSize(100);

        Column l1L2Column = RolapMappingFactory.eINSTANCE.createColumn();
        l1L2Column.setName("L2");
        l1L2Column.setId("L1_L2");
        l1L2Column.setType(ColumnDataType.VARCHAR);
        l1L2Column.setColumnSize(100);

        PhysicalTable l1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        l1Table.setName("L1");
        l1Table.setId("L1");
        l1Table.getColumns().addAll(List.of(l1L1Column, l1L2Column));
        databaseSchema.getTables().add(l1Table);

        Column l2L2Column = RolapMappingFactory.eINSTANCE.createColumn();
        l2L2Column.setName("L2");
        l2L2Column.setId("L2_L2");
        l2L2Column.setType(ColumnDataType.VARCHAR);
        l2L2Column.setColumnSize(100);

        PhysicalTable l2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        l2Table.setName("L2");
        l2Table.setId("L2");
        l2Table.getColumns().addAll(List.of(l2L2Column));
        databaseSchema.getTables().add(l2Table);

        Column factwbValColumn = RolapMappingFactory.eINSTANCE.createColumn();
        factwbValColumn.setName("VAL");
        factwbValColumn.setId("Factwb_VAL");
        factwbValColumn.setType(ColumnDataType.INTEGER);

        Column factwbVal1Column = RolapMappingFactory.eINSTANCE.createColumn();
        factwbVal1Column.setName("VAL1");
        factwbVal1Column.setId("Factwb_VAL1");
        factwbVal1Column.setType(ColumnDataType.INTEGER);

        Column factwbL2Column = RolapMappingFactory.eINSTANCE.createColumn();
        factwbL2Column.setName("L2");
        factwbL2Column.setId("factwb_L2");
        factwbL2Column.setType(ColumnDataType.VARCHAR);
        factwbL2Column.setColumnSize(100);

        Column factwbIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        factwbIdColumn.setName("ID");
        factwbIdColumn.setId("factwb_ID");
        factwbIdColumn.setType(ColumnDataType.VARCHAR);
        factwbIdColumn.setColumnSize(100);

        Column factwbUserColumn = RolapMappingFactory.eINSTANCE.createColumn();
        factwbUserColumn.setName("USER");
        factwbUserColumn.setId("factwb_USER");
        factwbUserColumn.setType(ColumnDataType.VARCHAR);
        factwbUserColumn.setColumnSize(100);

        PhysicalTable factwbTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        factwbTable.setName("FACTWB");
        factwbTable.setId("FACTWB");
        factwbTable.getColumns()
                .addAll(List.of(factwbValColumn, factwbVal1Column, factwbL2Column, factwbIdColumn, factwbUserColumn));
        databaseSchema.getTables().add(factwbTable);

        SqlSelectQuery query = RolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        query.setId("query");
        query.setSql(sqlView);
        query.setAlias(FACT1);

        TableQuery l1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        l1Query.setId("l1Query");
        l1Query.setTable(l1Table);

        TableQuery l2Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        l2Query.setId("l2Query");
        l2Query.setTable(l2Table);

        JoinedQueryElement joinLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinLeft.setKey(l1L2Column);
        joinLeft.setQuery(l1Query);
        JoinedQueryElement joinRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinRight.setKey(l2L2Column);
        joinRight.setQuery(l2Query);

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("joinQuery");
        joinQuery.setLeft(joinLeft);
        joinQuery.setRight(joinRight);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("Measure1");
        measure1.setId("Measure1");
        measure1.setColumn(valColumn);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("Measure2");
        measure2.setId("Measure2");
        measure2.setColumn(val1Column);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2));

        Level l1Level = RolapMappingFactory.eINSTANCE.createLevel();
        l1Level.setName("L1");
        l1Level.setId("L1Level");
        l1Level.setColumn(l1L1Column);
        l1Level.setTable(l1Table);

        Level l2Level = RolapMappingFactory.eINSTANCE.createLevel();
        l2Level.setName("L2");
        l2Level.setId("L2Level");
        l2Level.setColumn(l2L2Column);
        l2Level.setTable(l2Table);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(l1L2Column);
        hierarchy.setPrimaryKeyTable(l1Table);
        hierarchy.setQuery(joinQuery);
        hierarchy.getLevels().addAll(List.of(l1Level, l2Level));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("D1");
        dimensionConnector.setId("D1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(l1L2Column);

        WritebackAttribute writebackAttribute = RolapMappingFactory.eINSTANCE.createWritebackAttribute();
        writebackAttribute.setDimensionConnector(dimensionConnector);
        writebackAttribute.setColumn(l2Column);

        WritebackMeasure writebackMeasure1 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure1.setName("Measure1");
        writebackMeasure1.setColumn(valColumn);

        WritebackMeasure writebackMeasure2 = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure2.setName("Measure2");
        writebackMeasure2.setColumn(val1Column);

        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName("FACTWB");
        writebackTable.getWritebackAttribute().add(writebackAttribute);
        writebackTable.getWritebackMeasure().addAll(List.of(writebackMeasure1, writebackMeasure2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.setWritebackTable(writebackTable);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("tutorial_for_writeback_with_fact_view");
        catalog.setDescription("Schema with writeback with fact as sql view");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
