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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchyhasalltruefalse;

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

    private static final String CATALOG = "tutorial_15-04_Cube_with_share_dimension_with_Hierarchy_hasAll_true_false";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
    By default, every hierarchy contains a top level called '(All)',
    which contains a single member called '(All {hierarchyName})'.
    This member is parent of all other members of the hierarchy, and thus represents a grand total.
    It is also the default member of the hierarchy; that is, the member which is used for
    calculating cell values when the hierarchy is not included on an axis or in the slicer.
    The allMemberName and allLevelName attributes override the default names of the all level
    and all member.

    If the Hierarchy element has hasAll="false", the 'all' level is suppressed.
    The default member of that dimension will now be the first member of the first level;
    for example, in a Time hierarchy, it will be the first year in the hierarchy.
    Changing the default member can be confusing, so you should generally use hasAll="true".
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("Query");
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setId("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("Level");
        level.setColumn(keyColumn);

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("HierarchyWithHasAll");
        hierarchy1.setId("HierarchyWithHasAll");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(level);

        Hierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy2.setHasAll(false);
        hierarchy2.setName("HierarchyWithoutHasAll");
        hierarchy2.setId("HierarchyWithoutHasAll");
        hierarchy2.setPrimaryKey(keyColumn);
        hierarchy2.setQuery(query);
        hierarchy2.getLevels().add(level);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("Dimension1");
        dimension1.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dimension1");
        dimensionConnector1.setDimension(dimension1);

        StandardDimension dimension2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("Dimension2");
        dimension2.setId("Dimension2");
        dimension2.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Dimension2");
        dimensionConnector2.setDimension(dimension2);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().addAll(List.of(dimensionConnector1, dimensionConnector2));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_with_Hierarchy_hasAll_and_without_hasAll");
        catalog.setDescription("Schema of a minimal cube with Hierarchy hasAll and without hasAll");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
