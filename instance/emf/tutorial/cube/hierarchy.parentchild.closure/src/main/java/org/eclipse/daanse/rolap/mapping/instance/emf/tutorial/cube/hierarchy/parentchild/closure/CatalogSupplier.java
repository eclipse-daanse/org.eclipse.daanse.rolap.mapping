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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.parentchild.closure;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """

                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column nameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumn.setName("NAME");
        nameColumn.setId("_fact_name");
        nameColumn.setType(ColumnType.VARCHAR);

        Column parentColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        parentColumn.setName("PARENT");
        parentColumn.setId("_fact_parent");
        parentColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(nameColumn, parentColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column closureNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureNameColumn.setName("NAME");
        closureNameColumn.setId("_closure_name");
        closureNameColumn.setType(ColumnType.INTEGER);

        Column closureParentColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureParentColumn.setName("PARENT");
        closureParentColumn.setId("_closure_parent");
        closureParentColumn.setType(ColumnType.VARCHAR);

        Column closureDistanceColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureDistanceColumn.setName("DISTANCE");
        closureDistanceColumn.setId("_closure_distance");
        closureDistanceColumn.setType(ColumnType.INTEGER);

        PhysicalTable closureTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        closureTable.setName("Closure");
        closureTable.setId("_table_closure");
        closureTable.getColumns().addAll(List.of(closureNameColumn,
                closureParentColumn, closureDistanceColumn));
        databaseSchema.getTables().add(closureTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery closureQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        closureQuery.setId("_closureQuery");
        closureQuery.setTable(closureTable);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Value");
        measure.setId("_measure_value");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);


        ParentChildLink parentChildLink = RolapMappingFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setParentColumn(closureParentColumn);
        parentChildLink.setChildColumn(closureNameColumn);
        parentChildLink.setTable(closureQuery);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Name");
        level.setId("_level_name");
        level.setUniqueMembers(true);
        level.setColumn(nameColumn);
        level.setNameColumn(nameColumn);

        ParentChildHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setId("hierarchy");
        hierarchy.setPrimaryKey(nameColumn);
        hierarchy.setQuery(query);
        hierarchy.setLevel(level);
        hierarchy.setParentColumn(parentColumn);
        hierarchy.setNullParentValue("0");
        hierarchy.setParentChildLink(parentChildLink);


        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchy);


        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setForeignKey(nameColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_level_with_parent_child_with_closure");
        catalog.setDescription("Schema of a minimal cube with cube dimension level with closure table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
