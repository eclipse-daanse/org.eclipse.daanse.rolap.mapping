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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.17.02", source = Source.EMF, group = "Parent Child") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ParentChildHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private TableSource closureQuery;
    private Level level;


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
            The Database Schema contains the `Parent` table with 3 columns: `NAME`, `PARENT` and `VALUE`.
            The `NAME` column is used as the discriminator in the Hierarchy definitions.
            The Database Schema also contains the Closure  table with 3 columns: `NAME`, `PARENT` and `DISTANCE`.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Parent table to use in the measures.
            """;

    private static final String closureQueryBody = """
            The ClosureQuery is a simple TableSource that selects all columns from the `Parent` table to use in the parent child link.
            """;

    private static final String levelBody = """
            This Example uses 'Name' level based on the `NAME` column as key and name column `NAME` of table `Parent`.
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
            The cube with Parent Child Hierarchy.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column nameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumn.setName("NAME");
        nameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column parentColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        parentColumn.setName("PARENT");
        parentColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(nameColumn, parentColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column closureNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        closureNameColumn.setName("NAME");
        closureNameColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column closureParentColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        closureParentColumn.setName("PARENT");
        closureParentColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column closureDistanceColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        closureDistanceColumn.setName("DISTANCE");
        closureDistanceColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table closureTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        closureTable.setName("Closure");
        closureTable.getFeature().addAll(List.of(closureNameColumn,
                closureParentColumn, closureDistanceColumn));
        databaseSchema.getOwnedElement().add(closureTable);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        closureQuery = SourceFactory.eINSTANCE.createTableSource();
        closureQuery.setTable(closureTable);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Value");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);


        ParentChildLink parentChildLink = HierarchyFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setParentColumn(closureParentColumn);
        parentChildLink.setChildColumn(closureNameColumn);
        parentChildLink.setTable(closureQuery);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Name");
        level.setUniqueMembers(true);
        level.setColumn(nameColumn);
        level.setNameColumn(nameColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(nameColumn);
        hierarchy.setQuery(query);
        hierarchy.setLevel(level);
        hierarchy.setParentColumn(parentColumn);
        hierarchy.setNullParentValue("0");
        hierarchy.setParentChildLink(parentChildLink);


        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);


        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setForeignKey(nameColumn);
        dimensionConnector.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Parent Child Link");
        catalog.setDescription("Parent-child hierarchy with links");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Parent Child Link", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Closure Query", closureQueryBody, 1, 3, 0, closureQuery, 2),
                        new DocSection("Level", levelBody, 1, 4, 0, level, 0),
                        new DocSection("Hierarchy1", hierarchyBody, 1, 5, 0, hierarchy, 0),
                        new DocSection("Diml1", dimensionBody, 1, 6, 0, dimension, 0),
                        new DocSection("Cube", cubeBody, 1, 7, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
