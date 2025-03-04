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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithmultiplehierarchies;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_15-02_Cube_with_share_dimension_with_Hierarchy_multiple";
    private static final String CUBE = "CubeMultipleHierarchy";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            Cube with multiple hierarchy
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dim1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dim1KeyColumn.setName("DIM1_KEY");
        dim1KeyColumn.setId("Fact_DIM1_KEY");
        dim1KeyColumn.setType(ColumnDataType.INTEGER);

        Column dim2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dim2KeyColumn.setName("DIM2_KEY");
        dim2KeyColumn.setId("Fact_DIM2_KEY");
        dim2KeyColumn.setType(ColumnDataType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dim1KeyColumn, dim2KeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column h1L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setId("H1_L1_KEY");
        h1L1KeyColumn.setType(ColumnDataType.INTEGER);

        Column h1L1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setId("H1_L1_NAME");
        h1L1NameColumn.setType(ColumnDataType.VARCHAR);

        PhysicalTable h1L1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h1L1Table.setName("H1_L1");
        h1L1Table.setId("H1_L1");
        h1L1Table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h1L1Table);

        Column h2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h2L1KeyColumn.setName("KEY");
        h2L1KeyColumn.setId("H2_L1_KEY");
        h2L1KeyColumn.setType(ColumnDataType.INTEGER);

        Column h2L1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h2L1NameColumn.setName("NAME");
        h2L1NameColumn.setId("H2_L1_NAME");
        h2L1NameColumn.setType(ColumnDataType.VARCHAR);

        PhysicalTable h2L1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h2L1Table.setName("H2_L1");
        h2L1Table.setId("H2_L1");
        h2L1Table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h2L1Table);

        Column hxL2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setId("HX_L2_KEY");
        hxL2KeyColumn.setType(ColumnDataType.INTEGER);

        Column hxL2NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setId("HX_L2_NAME");
        hxL2NameColumn.setType(ColumnDataType.VARCHAR);

        Column hxL2H1L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setId("HX_L2_H1L1_KEY");
        hxL2H1L1KeyColumn.setType(ColumnDataType.INTEGER);

        Column hxL2H2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setId("HX_L2_H2L1_KEY");
        hxL2H2L1KeyColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable hxL2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hxL2Table.setName("HX_L2");
        hxL2Table.setId("HX_L2");
        hxL2Table.getColumns().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getTables().add(hxL2Table);


        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery queryH1L1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryH1L1.setId("H1L1Query");
        queryH1L1.setTable(h1L1Table);

        TableQuery queryH2L1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryH2L1.setId("H2L1Query");
        queryH2L1.setTable(h2L1Table);

        TableQuery queryHxL2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHxL2.setId("HxL2Query");
        queryHxL2.setTable(hxL2Table);

        JoinedQueryElement left1 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left1.setKey(hxL2KeyColumn);
        left1.setQuery(queryHxL2);

        JoinedQueryElement right1 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right1.setKey(h1L1KeyColumn);
        right1.setQuery(queryH1L1);

        JoinQuery joinQuery1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery1.setId("joinQuery1");
        joinQuery1.setLeft(left1);
        joinQuery1.setRight(right1);

        //<Join leftKey="H2L1_KEY" rightKey="KEY">
        //<Table name="HX_L2"/>
        //<Table name="H2_L1"/>
        //</Join>

        JoinedQueryElement left2 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left2.setKey(hxL2H2L1KeyColumn);
        left2.setQuery(queryHxL2);

        JoinedQueryElement right2 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right2.setKey(h2L1KeyColumn);
        right2.setQuery(queryH2L1);

        JoinQuery joinQuery2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery2.setId("joinQuery2");
        joinQuery2.setLeft(left2);
        joinQuery2.setRight(right2);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);


        Level h1Level1 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level1.setName("H1_Level1");
        h1Level1.setId("H1_Level1");
        h1Level1.setColumn(h1L1KeyColumn);
        h1Level1.setNameColumn(h1L1NameColumn);
        h1Level1.setTable(h1L1Table);


        Level h1Level2 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level2.setName("H1_Level2");
        h1Level2.setId("H1_Level2");
        h1Level2.setColumn(hxL2KeyColumn);
        h1Level2.setNameColumn(hxL2NameColumn);
        h1Level2.setTable(hxL2Table);

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setPrimaryKeyTable(hxL2Table);
        hierarchy1.setQuery(joinQuery1);
        hierarchy1.getLevels().add(h1Level1);
        hierarchy1.getLevels().add(h1Level2);

        Level h2Level1 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level2.setName("H2_Level1");
        h1Level2.setId("H2_Level1");
        h1Level2.setColumn(h2L1KeyColumn);
        h1Level2.setNameColumn(h2L1NameColumn);
        h1Level2.setTable(h2L1Table);


        Level h2Level2 = RolapMappingFactory.eINSTANCE.createLevel();
        h2Level2.setName("H1_Level2");
        h2Level2.setId("H1_Level2");
        h2Level2.setColumn(hxL2KeyColumn);
        h2Level2.setNameColumn(hxL2NameColumn);
        h2Level2.setTable(hxL2Table);

        Hierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy2.setHasAll(true);
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setId("Hierarchy2");
        hierarchy2.setPrimaryKey(hxL2KeyColumn);
        hierarchy2.setPrimaryKeyTable(hxL2Table);
        hierarchy2.setQuery(joinQuery2);
        hierarchy2.getLevels().add(h2Level1);
        hierarchy2.getLevels().add(h2Level2);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("Dimension1");
        dimension1.getHierarchies().add(hierarchy1);
        dimension1.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setDimension(dimension1);
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_multiple_hierarchy");
        catalog.setDescription("Schema of a minimal cube with multiple hierarchies");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
