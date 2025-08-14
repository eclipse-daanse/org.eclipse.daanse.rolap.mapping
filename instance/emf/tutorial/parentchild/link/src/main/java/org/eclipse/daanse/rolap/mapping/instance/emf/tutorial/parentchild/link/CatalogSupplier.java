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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.link;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.17.2", source = Source.EMF, group = "Parent Child") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";
    private static final String catalogBody = """
            Catalog with Minimal Cube with Parent Child Hierarchy
            Parent Child Hierarchy is self-referencing hierarchy where members can have parent-child relationships within the same table,
            creating variable-depth structures.
            Hierarchy has nullParentValue attribute.
            nullParentValue attribute is string value in the parent column that indicates root-level members (those without parents).
            Common values include '0', 'NULL', or empty string. This value identifies which members serve as the top-level roots of the hierarchy tree structure.
            Our example uses 'All'
            Also hierarchy has ParentChildLink attribute.
            ParentChildLink is table configuration for performance optimization of parent-child queries.
            The closure table pre-computes all ancestor-descendant relationships, enabling efficient recursive queries and aggregations.
            When specified, provides significant performance benefits for deep hierarchies with complex drill-down operations.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Parent table with 3 columns: NAME, PARENT and VALUE.
            The NAME column is used as the discriminator in the Hierarchy definitions.
            The Database Schema also contains the Closure  table with 3 columns: NAME, PARENT and DISTANCE.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Parent table to use in the measures.
            """;

    private static final String closureQueryBody = """
            The ClosureQuery is a simple TableQuery that selects all columns from the Parent table to use in the parent child link.
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
            Also Hierarchy1 defined the nullParentValue attribute with '0'.
            Elements items with PARENT = '0' are root elements in the tree
            Also Hierarchy1 defined the ParentChildLink with reference to Closure table.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            The cube with with Parent Child Hierarchy.
            """;

    private static final String catalogDocumentationTxt = """

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
        query.setId("_FactQuery");
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
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy");
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
        dimensionConnector.setId("_dc_dimension");
        dimensionConnector.setForeignKey(nameColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal Cube with ParentChildHierarchy with ParentChildLink");
        catalog.setDescription("Schema of a minimal cube with ParentChildHierarchy with ParentChildLink");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Catalog with Cube with Parent Child Hierarchy with ParentChildLink", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Fact Query", queryBody, 1, 2, 0, true, 2);
        document(closureQuery, "Closure Query", closureQueryBody, 1, 3, 0, true, 2);
        document(level, "Level", levelBody, 1, 4, 0, true, 0);
        document(hierarchy, "Hierarchy1", hierarchyBody, 1, 5, 0, true, 0);
        document(dimension, "Diml1", dimensionBody, 1, 6, 0, true, 0);

        document(cube, "Cube", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
