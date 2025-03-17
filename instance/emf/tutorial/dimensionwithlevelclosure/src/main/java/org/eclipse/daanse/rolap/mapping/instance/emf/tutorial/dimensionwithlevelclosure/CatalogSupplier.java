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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelclosure;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = " tutorial_15-07_Cube_with_share_dimension_with_Level_Closure_parent_child";
    private static final String CUBE = "CubeMultipleHierarchy";
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

        Column closureParentKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureParentKeyColumn.setName("PARENT_KEY");
        closureParentKeyColumn.setId("Closure_PARENT_KEY");
        closureParentKeyColumn.setType(ColumnType.INTEGER);

        Column closureChildKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureChildKeyColumn.setName("CHILD_KEY");
        closureChildKeyColumn.setId("Closure_CHILD_KEY");
        closureChildKeyColumn.setType(ColumnType.INTEGER);

        Column closureDistColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        closureDistColumn.setName("DIST");
        closureDistColumn.setId("Closure_DIST");
        closureDistColumn.setType(ColumnType.INTEGER);

        PhysicalTable closureTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        closureTable.setName("Closure");
        closureTable.setId("Closure");
        closureTable.getColumns().addAll(List.of(closureParentKeyColumn, closureChildKeyColumn, closureDistColumn));
        databaseSchema.getTables().add(closureTable);

        Column hierarchyKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hierarchyKeyColumn.setName("KEY");
        hierarchyKeyColumn.setId("Hierarchy_KEY");
        hierarchyKeyColumn.setType(ColumnType.INTEGER);

        Column hierarchyNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hierarchyNameColumn.setName("KEY");
        hierarchyNameColumn.setId("Hierarchy_NAME");
        hierarchyNameColumn.setType(ColumnType.INTEGER);

        Column hierarchyParentKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hierarchyParentKeyColumn.setName("PARENT_KEY");
        hierarchyParentKeyColumn.setId("Hierarchy_PARENT_KEY");
        hierarchyParentKeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable hierarchyTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hierarchyTable.setName("Hierarchy");
        hierarchyTable.setId("Hierarchy");
        hierarchyTable.getColumns().addAll(List.of(hierarchyKeyColumn, hierarchyNameColumn, hierarchyParentKeyColumn));
        databaseSchema.getTables().add(hierarchyTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery queryClosure = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryClosure.setId("ClosureQuery");
        queryClosure.setTable(closureTable);

        TableQuery queryHierarchy = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHierarchy.setId("HierarchyQuery");
        queryHierarchy.setTable(hierarchyTable);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setId("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        ParentChildLink parentChildLink = RolapMappingFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setTable(queryClosure);
        parentChildLink.setChildColumn(closureChildKeyColumn);
        parentChildLink.setParentColumn(closureParentKeyColumn);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("Level");
        level.setUniqueMembers(true);
        level.setColumn(hierarchyKeyColumn);
        level.setNameColumn(hierarchyNameColumn);
        level.setParentColumn(hierarchyParentKeyColumn);
        level.setParentChildLink(parentChildLink);

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("Hierarchy1");
        hierarchy1.setPrimaryKey(hierarchyKeyColumn);
        hierarchy1.setQuery(queryHierarchy);
        hierarchy1.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("Dimension1");
        dimension.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dimension1");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(dimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Parent_Child_With_Closure");
        catalog.setDescription("Schema of a minimal cube with level parent child with closure");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
