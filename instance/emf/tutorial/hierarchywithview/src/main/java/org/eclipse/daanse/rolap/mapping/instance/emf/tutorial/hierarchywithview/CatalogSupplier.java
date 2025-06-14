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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchywithview;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlView;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_18_Cube_with_share_dimension_with_view_reference";
    private static final String CUBE_ONE_MEASURE = "CubeOneMeasure";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """

                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("Fact_DIM_KEY");
        dimKeyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("HT_KEY");
        keyColumn.setType(ColumnType.INTEGER);

        Column nameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumn.setName("NAME");
        nameColumn.setId("HT_NAME");
        nameColumn.setType(ColumnType.VARCHAR);

        Column htValueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setId("HT_VALUE");
        htValueColumn.setType(ColumnType.INTEGER);

        PhysicalTable htTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        htTable.setName("HT");
        htTable.setId("HT");
        htTable.getColumns().addAll(List.of(keyColumn, nameColumn, htValueColumn));
        databaseSchema.getTables().add(htTable);

        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.getDialects().addAll(List.of("generic", "h2"));
        sqlStatement.setSql("select * from HT");
        SqlView sqlView = RolapMappingFactory.eINSTANCE.createSqlView();
        sqlView.setName("HT_VIEW");
        sqlView.setId("HT_VIEW");
        sqlView.getSqlStatements().add(sqlStatement);

        Column keyViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyViewColumn.setName("KEY");
        keyViewColumn.setId("View_KEY");
        keyViewColumn.setType(ColumnType.INTEGER);

        Column nameViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameViewColumn.setName("NAME");
        nameViewColumn.setId("View_NAME");
        nameViewColumn.setType(ColumnType.VARCHAR);

        Column htValueViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htValueViewColumn.setName("VALUE");
        htValueViewColumn.setId("View_VALUE");
        htValueViewColumn.setType(ColumnType.INTEGER);

        sqlView.getColumns().addAll(List.of(keyViewColumn, nameViewColumn, htValueViewColumn));

        databaseSchema.getTables().add(sqlView);

        SqlSelectQuery sqlSelectQuery = RolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        sqlSelectQuery.setId("sqlSelectQuery");
        sqlSelectQuery.setAlias("HT_VIEW");
        sqlSelectQuery.setSql(sqlView);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level1");
        level.setId("Level1");
        level.setColumn(keyViewColumn);
        level.setNameColumn(nameViewColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyViewColumn);
        hierarchy.setQuery(sqlSelectQuery);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_ONE_MEASURE);
        cube.setId(CUBE_ONE_MEASURE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_share_dimension_with hierarchy_with_view_reference");
        catalog.setDescription("Schema of a minimal cube with hierarchy with view reference");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }
}
