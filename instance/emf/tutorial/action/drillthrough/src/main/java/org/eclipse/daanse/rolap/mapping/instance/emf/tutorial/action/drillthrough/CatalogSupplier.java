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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.action.drillthrough;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.ActionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.10.01", source = Source.EMF, group = "Actions") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private StandardDimension dimension;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private JoinSource join2;
    private Level level2;
    private ExplicitHierarchy hierarchy2;
    private Level level3;
    private TableSource query;
    private TableSource h2L1Query;
    private PhysicalCube cube;
    private DrillThroughAction drillThroughAction2;
    private TableSource hxL2Query;
    private JoinSource join1;
    private TableSource h1L1Query;
    private Level level4;
    private ExplicitHierarchy hierarchy1;
    private DrillThroughAction drillThroughAction1;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial discusses DrillThroughAction.
            DrillThroughAction feature enables users to seamlessly transition from analytical summaries
            to detailed operational data without losing analytical context or requiring technical
            knowledge of underlying data structures.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`.<br />
            - `H1_L1` table with two columns: `KEY` and `NAME`.<br />
            - `H2_L1` table with two columns: `KEY` and `NAME`.<br />
            - `HX_L2` table with 4 columns: `KEY`, `NAME`, `H1L1_KEY`, `H2L1_KEY`.<br />
            The `KEY` column of `Fact` table is used as the discriminator in the dimension.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the `Fact` table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String h1L1QueryBody = """
            The Query is a simple TableSource that selects all columns from the `H1_L1` table.
            """;

    private static final String h2L1QueryBody = """
            The Query is a simple TableSource that selects all columns from the `H2_L1` table.
            """;

    private static final String hxL2QueryBody = """
            The Query is a simple TableSource that selects all columns from the `HX_L2` table.
            """;

    private static final String join1Body = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (`HX_L2`), the join uses the foreign key `H1L1_KEY`.<br />
            - In the upper-level table (`H1_L1`), the join uses the primary key `KEY`.<br />
            """;

    private static final String join2Body = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (`HX_L2`), the join uses the foreign key `H2L1_KEY`.<br />
            - In the upper-level table (`H2_L1`), the join uses the primary key `KEY`.<br />
            """;

    private static final String level1Body = """
            This Example uses H1_Level1 level based on the `KEY` column as key and `NAME` column as name from `H1_L1` table.
            """;

    private static final String level2Body = """
            This Example uses H1_Level2 level based on the KEY column as key and NAME column as name from HX_L2 table.
            """;

    private static final String level3Body = """
            This Example uses H2_Level1 level based on the KEY column as key and NAME column as name from H2_L1 table.
            """;

    private static final String level4Body = """
            This Example uses H2_Level2 level based on the KEY column as key and NAME column as name from HX_L2 table.
            """;

    private static final String hierarchy1Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the two levels H1_Level1, H1_Level2.
            """;

    private static final String hierarchy2Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the two levels H1_Level1, H1_Level2.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the 2 hierarchies.
            """;

    private static final String drillthroughH1L1Body = """
            Specialized action that enables users to drill through from aggregated analytical views to the underlying
            detailed transaction data that contributed to specific measure values, providing powerful capabilities
            for data exploration, validation, and detailed investigation of analytical results.
            Collection of DrillThroughAttribute objects that specify which dimensional attributes and descriptive columns
            should be included in drill-through result sets

            DrillThroughAttributes have reference to H1_Level1 level from Hierarchy1; H1_L1 table KEY and NAME column
            """;

    private static final String drillthroughH2L1Body = """
            DrillThroughAttributes have reference to H2_Level1 level from Hierarchy2; H2_L1 table KEY and NAME column
            """;

    private static final String cubeBody = """
            The cube with DrillThroughAction
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
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        // "KEY","NAME"
        // INTEGER,VARCHAR

        Column h1L1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column h1L1NameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table h1L1table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        h1L1table.setName("H1_L1");
        h1L1table.getFeature().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getOwnedElement().add(h1L1table);

        Column h2L1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h2L1KeyColumn.setName("KEY");
        h2L1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column h2L1NameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h2L1NameColumn.setName("NAME");
        h2L1NameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table h2L1table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        h2L1table.setName("H2_L1");
        h2L1table.getFeature().addAll(List.of(h2L1KeyColumn, h2L1NameColumn));
        databaseSchema.getOwnedElement().add(h2L1table);

        Column hxL2KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column hxL2NameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column hxL2H1L1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column hxL2H2L1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table hxL2table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        hxL2table.setName("HX_L2");
        hxL2table.getFeature().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getOwnedElement().add(hxL2table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        hxL2Query = SourceFactory.eINSTANCE.createTableSource();
        hxL2Query.setTable(hxL2table);


        h1L1Query = SourceFactory.eINSTANCE.createTableSource();
        h1L1Query.setTable(h1L1table);

        h2L1Query = SourceFactory.eINSTANCE.createTableSource();
        h2L1Query.setTable(h2L1table);

        JoinedQueryElement join1Left = SourceFactory.eINSTANCE.createJoinedQueryElement();
        join1Left.setKey(hxL2H1L1KeyColumn);
        join1Left.setQuery(hxL2Query);

        JoinedQueryElement join1Right = SourceFactory.eINSTANCE.createJoinedQueryElement();
        join1Right.setKey(h1L1KeyColumn);
        join1Right.setQuery(h1L1Query);

        join1 = SourceFactory.eINSTANCE.createJoinSource();
        join1.setLeft(join1Left);
        join1.setRight(join1Right);

        JoinedQueryElement join2Left = SourceFactory.eINSTANCE.createJoinedQueryElement();
        join2Left.setKey(hxL2H2L1KeyColumn);
        join2Left.setQuery(hxL2Query);

        JoinedQueryElement join2Right = SourceFactory.eINSTANCE.createJoinedQueryElement();
        join2Right.setKey(h2L1KeyColumn);
        join2Right.setQuery(h2L1Query);

        join2 = SourceFactory.eINSTANCE.createJoinSource();
        join2.setLeft(join2Left);
        join2.setRight(join2Right);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("H1_Level1");
        level1.setColumn(h1L1KeyColumn);
        level1.setNameColumn(h1L1NameColumn);

        level2 = LevelFactory.eINSTANCE.createLevel();
        level2.setName("H1_Level2");
        level2.setColumn(hxL2KeyColumn);
        level2.setNameColumn(hxL2NameColumn);

        hierarchy1 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setQuery(join1);
        hierarchy1.getLevels().addAll(List.of(level1, level2));

        level3 = LevelFactory.eINSTANCE.createLevel();
        level3.setName("H2_Level1");
        level3.setColumn(h2L1KeyColumn);
        level3.setNameColumn(h2L1NameColumn);

        level4 = LevelFactory.eINSTANCE.createLevel();
        level4.setName("H2_Level2");
        level4.setColumn(hxL2KeyColumn);
        level4.setNameColumn(hxL2NameColumn);

        hierarchy2 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy2.setHasAll(true);
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setPrimaryKey(hxL2KeyColumn);
        hierarchy2.setQuery(join2);
        hierarchy2.getLevels().addAll(List.of(level3, level4));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchy1);
        dimension.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setForeignKey(keyColumn);
        dimensionConnector.setDimension(dimension);

        DrillThroughAttribute drillThroughAttribute1 = ActionFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute1.setDimension(dimension);
        drillThroughAttribute1.setHierarchy(hierarchy1);
        drillThroughAttribute1.setLevel(level1); // H1_Level1
        // drillThroughAttribute1.setName("H1_Level1");

        DrillThroughAttribute drillThroughAttribute2 = ActionFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute2.setDimension(dimension);
        drillThroughAttribute2.setHierarchy(hierarchy2);
        drillThroughAttribute2.setLevel(level3); // H2_Level1
        // drillThroughAttribute2.setName("H2_Level1");

        drillThroughAction1 = ActionFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction1.setName("DrillthroughH1L1");
        drillThroughAction1.setDefault(true);
        drillThroughAction1.getDrillThroughAttribute().add(drillThroughAttribute1);
        drillThroughAction1.getDrillThroughMeasure().add(measure);

        drillThroughAction2 = ActionFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction2.setName("DrillthroughH2L1");
        drillThroughAction2.getDrillThroughAttribute().add(drillThroughAttribute2);
        drillThroughAction2.getDrillThroughMeasure().add(measure);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getAction().addAll(List.of(drillThroughAction1, drillThroughAction2));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Action Drillthrough");
        catalog.setDescription("Drill-through action configuration");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);





        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Action Drillthrough", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("HxL2Query", hxL2QueryBody, 1, 3, 0, hxL2Query, 2),
                        new DocSection("H1L1Query", h1L1QueryBody, 1, 2, 0, h1L1Query, 2),
                        new DocSection("H2L1Query", h2L1QueryBody, 1, 2, 0, h2L1Query, 2),
                        new DocSection("join1", join1Body, 1, 2, 0, join1, 2),
                        new DocSection("join2", join2Body, 1, 2, 0, join2, 2),
                        new DocSection("Level1", level1Body, 1, 3, 0, level1, 0),
                        new DocSection("Level2", level2Body, 1, 3, 0, level2, 0),
                        new DocSection("Level3", level3Body, 1, 3, 0, level3, 0),
                        new DocSection("Level4", level4Body, 1, 3, 0, level4, 0),
                        new DocSection("Hierarchy1", hierarchy1Body, 1, 8, 0, hierarchy1, 0),
                        new DocSection("Hierarchy2", hierarchy2Body, 1, 8, 0, hierarchy2, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("DrillthroughH1L1", drillthroughH1L1Body, 1, 9, 0, drillThroughAction1, 0),
                        new DocSection("DrillthroughH2L1", drillthroughH2L1Body, 1, 9, 0, drillThroughAction2, 0),
                        new DocSection("Cube with Time_Dimension", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
