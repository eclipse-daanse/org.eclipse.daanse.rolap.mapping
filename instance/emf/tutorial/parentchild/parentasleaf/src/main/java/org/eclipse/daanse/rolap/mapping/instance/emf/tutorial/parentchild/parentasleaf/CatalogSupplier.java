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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.parentasleaf;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.17.4", source = Source.EMF, group = "Parent Child") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String PARENT = "Parent";

    private static final String catalogBody = """
            Catalog with Minimal Cube with Parent Child Hierarchy
            Parent Child Hierarchy is self-referencing hierarchy where members can have parent-child relationships within the same table,
            creating variable-depth structures.
            Hierarchy has parentAsLeafEnable true value
            This is a boolean flag that allows intermediate parent members to also appear as leaf members,
            enabling scenarios where the same entity functions both as a container and as a data point.
            When true, parents can have their own measures and participate in aggregations at multiple levels.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Parent table with 3 columns: NAME, PARENT and VALUE.
            The NAME column is used as the discriminator in the Hierarchy definitions.

            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Parent table to use in the measures.
            """;

    private static final String levelBody = """
            This Example uses 'Name' level bases on the NAME column as key and name column NAME of table Parent.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 defined parentColumn to NAME column of Parent table.
            ParentColumn containing the parent reference for each member, establishing the self-referencing relationship.
            This column typically contains the primary key value of the parent member, or the nullParentValue for root members. The column enables the recursive traversal that defines the hierarchy structure.
            Also Hierarchy1 defined the level 'Name'.
            Level is Single level definition that applies to all members in this parent-child hierarchy.
            Unlike explicit hierarchies with multiple levels, parent-child hierarchies use one level
            definition that describes the properties and behavior of all members regardless of their position in the tree structure.
            Also Hierarchy1 Hierarchy has parentAsLeafEnable true value
            When true, parents can have their own measures and participate in aggregations at multiple levels.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            The cube with with Parent Child Hierarchy.
            """;

    private static final String catalogDocumentationTxt = """
            Minimal Cube with Parent Child Hierarchy with null parent value.
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        Column nameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumn.setName("NAME");
        nameColumn.setId("_parent_name");
        nameColumn.setType(ColumnType.VARCHAR);

        Column parentColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        parentColumn.setName("PARENT");
        parentColumn.setId("_parent_parent");
        parentColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_parent_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(PARENT);
        table.setId("_table_parent");
        table.getColumns().addAll(List.of(nameColumn, parentColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Value");
        measure.setId("_measure_value");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Name");
        level.setId("_level_name");
        level.setUniqueMembers(true);
        level.setColumn(nameColumn);
        level.setNameColumn(nameColumn);

        ParentChildHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy");
        hierarchy.setAllMemberName("All");
        hierarchy.setPrimaryKey(nameColumn);
        hierarchy.setQuery(query);
        hierarchy.setParentColumn(parentColumn);
        hierarchy.setNullParentValue("all");
        hierarchy.setParentAsLeafEnable(true);
        hierarchy.setParentAsLeafNameFormat("parent %s");
        hierarchy.setLevel(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchy);


        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setId("_dc_dimension");
        dimensionConnector.setForeignKey(nameColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal Cube with Parent Child Hierarchy with parentAsLeafEnable.");
        catalog.setDescription("Minimal Cube with Parent Child Hierarchy with parentAsLeafEnable.");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Catalog with Minimal Cube with Parent Child Hierarchy with parentAsLeafEnable", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Fact Query", queryBody, 1, 2, 0, true, 2);
        document(level, "Level", levelBody, 1, 3, 0, true, 0);

        document(hierarchy, "Hierarchy1", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Diml1", dimensionBody, 1, 5, 0, true, 0);

        document(cube, "Cube", cubeBody, 1, 6, 0, true, 2);

        return catalog;
    }

}
