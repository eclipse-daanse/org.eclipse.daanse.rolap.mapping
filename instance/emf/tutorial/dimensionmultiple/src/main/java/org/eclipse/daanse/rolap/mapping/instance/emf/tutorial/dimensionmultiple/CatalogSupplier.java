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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionmultiple;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_15-01_Cube_with_share_dimension_multiple";
    private static final String CUBE = "CubeTwoDimensions";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            Cube with multiple dimensions
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dim1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dim1KeyColumn.setName("DIM1_KEY");
        dim1KeyColumn.setId("Fact_DIM1_KEY");
        dim1KeyColumn.setType(ColumnType.INTEGER);

        Column dim2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dim2KeyColumn.setName("DIM2_KEY");
        dim2KeyColumn.setId("Fact_DIM2_KEY");
        dim2KeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dim1KeyColumn, dim2KeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column keyDim1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyDim1Column.setName("KEY");
        keyDim1Column.setId("Dim1_KEY");
        keyDim1Column.setType(ColumnType.INTEGER);

        Column nameDim1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameDim1Column.setName("NAME");
        nameDim1Column.setId("Dim1_NAME");
        nameDim1Column.setType(ColumnType.VARCHAR);

        PhysicalTable tableDim1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableDim1.setName("DIM_1");
        tableDim1.setId("DIM_1");
        tableDim1.getColumns().addAll(List.of(keyDim1Column, nameDim1Column));
        databaseSchema.getTables().add(tableDim1);

        Column keyDim2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyDim2Column.setName("KEY");
        keyDim2Column.setId("Dim2_KEY");
        keyDim2Column.setType(ColumnType.INTEGER);

        Column nameDim2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameDim2Column.setName("NAME");
        nameDim2Column.setId("Dim2_NAME");
        nameDim2Column.setType(ColumnType.VARCHAR);

        PhysicalTable tableDim2 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableDim2.setName("DIM_2");
        tableDim2.setId("DIM_2");
        tableDim2.getColumns().addAll(List.of(keyDim2Column, nameDim2Column));
        databaseSchema.getTables().add(tableDim2);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery queryDim1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryDim1.setId("Dim1Query");
        queryDim1.setTable(tableDim1);

        TableQuery queryDim2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryDim2.setId("Dim2Query");
        queryDim2.setTable(tableDim2);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setId("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("D1H1L1");
        level1.setId("D1H1L1");
        level1.setColumn(keyDim1Column);
        level1.setNameColumn(nameDim1Column);

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("D1H1");
        hierarchy1.setId("D1H1");
        hierarchy1.setPrimaryKey(keyDim1Column);
        hierarchy1.setQuery(queryDim1);
        hierarchy1.getLevels().add(level1);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("D1");
        dimension1.setId("D1");
        dimension1.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dimension1");
        dimensionConnector1.setDimension(dimension1);
        dimensionConnector1.setForeignKey(dim1KeyColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("D2H1L1");
        level2.setId("D2H1L1");
        level2.setColumn(keyDim2Column);
        level2.setNameColumn(nameDim2Column);

        Hierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy2.setHasAll(true);
        hierarchy2.setName("D21H1");
        hierarchy2.setId("D2H1");
        hierarchy2.setPrimaryKey(keyDim1Column);
        hierarchy2.setQuery(queryDim1);
        hierarchy2.getLevels().add(level1);

        StandardDimension dimension2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("D2");
        dimension2.setId("D2");
        dimension2.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Dimension2");
        dimensionConnector2.setDimension(dimension2);
        dimensionConnector2.setForeignKey(dim2KeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Two_Dimensions");
        catalog.setDescription("Schema of a minimal cube with two dimensions");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
