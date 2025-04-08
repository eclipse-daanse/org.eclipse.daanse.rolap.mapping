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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelparentcolumn;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_15-06_Cube_with_share_dimension_with_Level_parentColumn_parent_child_";
    private static final String CUBE = "CubeParentChildOneTopMember";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
    The parentColumn attribute is the name of the column which
    links a member to its parent member; in this case,
    it is the foreign key column which points to an employee's
    supervisor. The ParentExpression child element of Level
    is equivalent to the parentColumn attribute, but allows you to
    define an arbitrary SQL expression, just like the Expression
    element. The parentColumn attribute (or ParentExpression element)
    is the only indication to Mondrian that a hierarchy has a
    parent-child structure.
    The nullParentValue attribute is the value which indicates
    that a member has no parent. The default is
    nullParentValue="null", but since many database don't index
    null values, schema designers sometimes use values as the
    empty string, 0, and -1 instead.
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

        Column memberKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberKeyColumn.setName("KEY");
        memberKeyColumn.setId("Hier_One_Top_Member_KEY");
        memberKeyColumn.setType(ColumnType.INTEGER);

        Column memberNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberNameColumn.setName("NAME");
        memberNameColumn.setId("Hier_One_Top_Member_NAME");
        memberNameColumn.setType(ColumnType.VARCHAR);

        Column memberParentKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberParentKeyColumn.setName("PARENT_KEY");
        memberParentKeyColumn.setId("Hier_One_Top_Member_PARENT_KEY");
        memberParentKeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable table1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table1.setName("Hier_One_Top_Member");
        table1.setId("Hier_One_Top_Member");
        table1.getColumns().addAll(List.of(memberKeyColumn, memberNameColumn, memberParentKeyColumn));
        databaseSchema.getTables().add(table1);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query1.setId("Hier_One_Top_MemberQuery");
        query1.setTable(table1);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregatorType("sum");
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("Level");
        level.setUniqueMembers(true);
        level.setColumn(memberKeyColumn);
        level.setNameColumn(memberNameColumn);
        level.setParentColumn(memberParentKeyColumn);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("Hierarchy1");
        hierarchy.setPrimaryKey(memberKeyColumn);
        hierarchy.setQuery(query1);
        hierarchy.getLevels().add(level);

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
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Parent_Child_Hierarchy");
        catalog.setDescription("Schema of a minimal cube with parent child hierarchy");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
