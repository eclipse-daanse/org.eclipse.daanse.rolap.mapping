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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.hasall;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.04", source = Source.EMF, group = "Hierarchy") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private StandardDimension dimension;
    private ExplicitHierarchy hierarchyHasAllComplex;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private ExplicitHierarchy hierarchyHasAllSimple;
    private ExplicitHierarchy hierarchyHasAllFalse;
    private Level level;


    private static final String introBody = """
            In a hierarchy, the top level can sometimes be a special case. Typically, levels are created using a Level object, along with a reference to a column and a query on the hierarchy. However, there are situations where no dedicated column or table entry exists for the top level. For example if you want to represent a grand total. In such cases, you can generate a generic top level that serves as a final aggregation for all members of the level below.

            To manage the creation of this top level, the hierarchy provides specific attributes:

            - `hasAll` A Boolean attribute that indicates whether a so-called “HasAll” level is needed. If hasAll is set to true, a top level will be generated.

            - `allLevelName` Specifies the name for the top level itself.

            - `allMemberName` Specifies the name of the member within this top level. If this attribute is not set, the default name is used in the form: `All <HierarchyName>s`.

            By configuring these attributes, you can control whether a top-level aggregation appears, as well as how it is labeled in your hierarchy.

            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on three table: Fact.

            - The Fact table contains measures and a reference to the Level.
            """;

    private static final String queryBody = """
            This Query references the Fact table and will be udes for the Cube and all Hierarchies in same way.
            """;

    private static final String levelBody = """
            The Level object uses the column attribute to specify the column names `KEY` that represents the level and its members.
            This the only Level that exists in this example and will be used in all hierarchies the same way.
            """;

    private static final String hierarchyNoBody = """
            This Hierarchy sets the attribute `hasAll` to false, which means that no top level will be generated. The hierarchy will only contain the levels defined in the Level object.
            """;

    private static final String hierarchyWithSimpleBody = """
            This hierarchy sets the attribute `hasAll` to true, which means that a top level will be generated. The hierarchy will contain the levels defined in the Level object and an additional top level with the default Name for the All-Level and the All-Member.
            """;

    private static final String hierarchyWithComplexBody = """
            tHis hierarchy sets the attribute `hasAll` to true, which means that a top level will be generated. The hierarchy will contain the levels defined in the Level object and an additional top level with the custom Name for the All-Level and the All-Member.
                        """;

    private static final String dimensionBody = """
            The Dimension that containes all the hierarchies.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("theLevel");
        level.setColumn(keyColumn);

        hierarchyHasAllSimple = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllSimple.setHasAll(true);
        hierarchyHasAllSimple.setName("Hierarchy - with HasAll");
        hierarchyHasAllSimple.setPrimaryKey(keyColumn);
        hierarchyHasAllSimple.setQuery(query);
        hierarchyHasAllSimple.getLevels().add(level);

        hierarchyHasAllComplex = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllComplex.setHasAll(true);
        hierarchyHasAllComplex.setAllLevelName("theAllLevelName");
        hierarchyHasAllComplex.setAllMemberName("theAllMemberName");
        hierarchyHasAllComplex.setName("Hierarchy - with HasAll and Names");
        hierarchyHasAllComplex.setPrimaryKey(keyColumn);
        hierarchyHasAllComplex.setQuery(query);
        hierarchyHasAllComplex.getLevels().add(level);

        hierarchyHasAllFalse = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllFalse.setHasAll(false);
        hierarchyHasAllFalse.setName("Hierarchy - Without HasAll");
        hierarchyHasAllFalse.setPrimaryKey(keyColumn);
        hierarchyHasAllFalse.setQuery(query);
        hierarchyHasAllFalse.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchyHasAllSimple);
        dimension.getHierarchies().add(hierarchyHasAllComplex);
        dimension.getHierarchies().add(hierarchyHasAllFalse);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("HasAll Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Hierarchy Has All");
        catalog.setDescription("Hierarchy with all-member configuration");
        catalog.getCubes().add(cube);



        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Hierarchy Has All", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level", levelBody, 1, 6, 0, level, 0),
                        new DocSection("Hierarchy without hasAll Level", hierarchyNoBody, 1, 8, 0, hierarchyHasAllFalse, 0),
                        new DocSection("Hierarchy with hasAll Level and defaut names", hierarchyWithSimpleBody, 1, 8, 0, hierarchyHasAllSimple, 0),
                        new DocSection("Hierarchy with hasAll Level and custom names", hierarchyWithComplexBody, 1, 8, 0, hierarchyHasAllComplex, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cube and DimensionConnector and Measure", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
