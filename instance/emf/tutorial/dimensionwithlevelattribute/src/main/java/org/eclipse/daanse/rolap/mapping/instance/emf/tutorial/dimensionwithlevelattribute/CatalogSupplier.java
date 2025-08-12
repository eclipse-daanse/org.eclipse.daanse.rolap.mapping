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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelattribute;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_Cube_with_CubeDimension_with_level_attribute";
    private static final String CUBE = "CubeMultipleHierarchy";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
    A basic OLAP schema with DimensionUsage with level attribute
    Level attribute in DimensionUsage uses for optimize sql inner join
    Level attribute is name of the level to join to
    If not specified joins to the lowest level of the dimension
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("Fact_DIM_KEY");
        dimKeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column h1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setId("H1_L1_KEY");
        h1L1KeyColumn.setType(ColumnType.INTEGER);

        Column h1L1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setId("H1_L1_NAME");
        h1L1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable h1L1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h1L1Table.setName("H1_L1");
        h1L1Table.setId("H1_L1");
        h1L1Table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h1L1Table);

        Column hxL2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setId("HX_L2_KEY");
        hxL2KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setId("HX_L2_NAME");
        hxL2NameColumn.setType(ColumnType.VARCHAR);

        Column hxL2H1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setId("HX_L2_H1L1_KEY");
        hxL2H1L1KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2H2L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setId("HX_L2_H2L1_KEY");
        hxL2H2L1KeyColumn.setType(ColumnType.INTEGER);

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

        TableQuery queryHxL2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHxL2.setId("HxL2Query");
        queryHxL2.setTable(hxL2Table);

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(hxL2H1L1KeyColumn);
        left.setQuery(queryHxL2);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(h1L1KeyColumn);
        right.setQuery(queryH1L1);

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("joinQuery");
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level h1Level1 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level1.setName("H1_Level1");
        h1Level1.setId("H1_Level1");
        h1Level1.setColumn(h1L1KeyColumn);
        h1Level1.setNameColumn(h1L1NameColumn);

        Level h1Level2 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level2.setName("H1_Level2");
        h1Level2.setId("H1_Level2");
        h1Level2.setColumn(hxL2KeyColumn);
        h1Level2.setNameColumn(hxL2NameColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setQuery(joinQuery);
        hierarchy1.getLevels().add(h1Level1);
        hierarchy1.getLevels().add(h1Level2);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml1");
        dimension.setId("Diml1");
        dimension.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_dim1");
        dimensionConnector1.setOverrideDimensionName("Dim1");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setLevel(h1Level2);
        dimensionConnector1.setForeignKey(dimKeyColumn);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setId("_dc_dim2");
        dimensionConnector2.setOverrideDimensionName("Dim2");
        dimensionConnector2.setDimension(dimension);
        dimensionConnector2.setLevel(h1Level1);
        dimensionConnector2.setForeignKey(dimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_level_attribute");
        catalog.setDescription("Schema of a minimal cube with level attribute");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
