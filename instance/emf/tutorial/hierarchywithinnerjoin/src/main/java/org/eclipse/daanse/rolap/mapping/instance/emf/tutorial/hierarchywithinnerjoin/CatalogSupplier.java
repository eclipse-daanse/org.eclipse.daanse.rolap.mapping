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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchywithinnerjoin;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
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

    private static final String CATALOG = "tutorial_15_Cube_with_share_dimension_with hierarchy_with_table reference_with_inner_join";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with a multy level with properties with reference with inner join
            Share dimension Dimension1 is defined in a <Dimension> element inside <Schema> element.
            Hierarchy is defined in a <Hierarchy> element inside <Dimension> element.
            Hierarchy have reference to several tables by help <Join> element
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
        dimKeyColumn.setType(ColumnDataType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column l1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        l1KeyColumn.setName("KEY");
        l1KeyColumn.setId("Level_1_KEY");
        l1KeyColumn.setType(ColumnDataType.INTEGER);

        Column l1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        l1NameColumn.setName("NAME");
        l1NameColumn.setId("Level_1_NAME");
        l1NameColumn.setType(ColumnDataType.VARCHAR);

        PhysicalTable level1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level1Table.setName("Level_1");
        level1Table.setId("Level_1");
        level1Table.getColumns().addAll(List.of(l1KeyColumn, l1NameColumn));
        databaseSchema.getTables().add(level1Table);

        Column l2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        l2KeyColumn.setName("KEY");
        l2KeyColumn.setId("Level_2_KEY");
        l2KeyColumn.setType(ColumnDataType.INTEGER);

        Column l2NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        l2NameColumn.setName("NAME");
        l2NameColumn.setId("Level_2_NAME");
        l2NameColumn.setType(ColumnDataType.VARCHAR);

        Column l2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        l2L1KeyColumn.setName("L1_KEY");
        l2L1KeyColumn.setId("Level_2_L1_KEY");
        l2L1KeyColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable level2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        level2Table.setName("Level_2");
        level2Table.setId("Level_2");
        level2Table.getColumns().addAll(List.of(l2KeyColumn, l2NameColumn, l2L1KeyColumn));
        databaseSchema.getTables().add(level2Table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery level1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        level1Query.setId("level1Query");
        level1Query.setTable(level1Table);

        TableQuery level2Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        level2Query.setId("level2Query");
        level2Query.setTable(level2Table);

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(l2L1KeyColumn);
        left.setQuery(level2Query);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(l1KeyColumn);
        right.setQuery(level1Query);

        JoinQuery join = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join.setId("join");
        join.setLeft(left);
        join.setRight(right);

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
        level1.setColumn(l1KeyColumn);
        level1.setNameColumn(l1NameColumn);
        level1.setTable(level1Table);
        level1.setColumnType(ColumnInternalDataType.INTEGER);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("Level2");
        level2.setColumn(l2KeyColumn);
        level2.setNameColumn(l2NameColumn);
        level2.setTable(level2Table);
        level2.setColumnType(ColumnInternalDataType.INTEGER);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(l2KeyColumn);
        hierarchy.setQuery(join);
        hierarchy.setPrimaryKeyTable(level2Table);
        hierarchy.getLevels().addAll(List.of(level1, level2));

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
        catalog.setName("Cube_with_hierarchy_with_table_reference_with_inner_join");
        catalog.setDescription("Schema with hierarchy with table reference with inner join");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }
}
