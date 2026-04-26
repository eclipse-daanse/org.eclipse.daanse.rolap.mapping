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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.17.04", source = Source.EMF, group = "Parent Child") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ParentChildHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private Level level;


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
            The Database Schema contains the `Parent` table with 3 columns: `NAME`, `PARENT` and `VALUE`.
            The `NAME` column is used as the discriminator in the Hierarchy definitions.

            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the `Parent` table to use in the measures.
            """;

    private static final String levelBody = """
            This Example uses 'Name' level based on the `NAME` column as key and name column `NAME` of table `Parent`.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 defined parentColumn to `NAME` column of `Parent` table.
            `ParentColumn` containing the parent reference for each member, establishing the self-referencing relationship.
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
        table.setName(PARENT);
        table.getFeature().addAll(List.of(nameColumn, parentColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Value");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Name");
        level.setUniqueMembers(true);
        level.setColumn(nameColumn);
        level.setNameColumn(nameColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setAllMemberName("All");
        hierarchy.setPrimaryKey(nameColumn);
        hierarchy.setQuery(query);
        hierarchy.setParentColumn(parentColumn);
        hierarchy.setNullParentValue("all");
        hierarchy.setParentAsLeafEnable(true);
        hierarchy.setParentAsLeafNameFormat("parent %s");
        hierarchy.setLevel(level);

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
        catalog.setName("Daanse Tutorial - Parent Child Parent As Leaf");
        catalog.setDescription("Parent-child hierarchy with parent as leaf enabled");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Parent Child Parent As Leaf", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Fact Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level", levelBody, 1, 3, 0, level, 0),
                        new DocSection("Hierarchy1", hierarchyBody, 1, 4, 0, hierarchy, 0),
                        new DocSection("Diml1", dimensionBody, 1, 5, 0, dimension, 0),
                        new DocSection("Cube", cubeBody, 1, 6, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
