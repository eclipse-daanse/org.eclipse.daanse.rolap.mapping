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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.optimisationwithlevelattribute;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.09.02", source = Source.EMF, group = "Dimension") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private StandardDimension dimension;
    private JoinSource joinQuery;
    private Schema databaseSchema;
    private Catalog catalog;
    private ExplicitHierarchy hierarchy1;
    private PhysicalCube cube;
    private TableSource query;
    private Level h1Level2;
    private Level h1Level1;
    private TableSource queryH1L1;
    private TableSource queryHxL2;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            A basic OLAP schema with two Dimension Connectors with level attribute
            Level attribute in DimensionConnector uses for optimize sql inner join
            Level attribute is name of the level to join to
            If not specified joins to the lowest level of the dimension
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: DIM_KEY and VALUE.
            The DATE_KEY column is used as the discriminator in the Hierarchy definitions.

            - `H1_L1` table with two columns: `KEY` and `NAME`
            - `HX_L2` table with 3 columns: `KEY`, `NAME`, `H1L1_KEY`, `H2L1_KEY`
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the measures.
            """;

    private static final String queryH1L1Body = """
            The Query is a simple TableSource that selects all columns from the H1_L1 table to use in the hierarchy join.
            """;

    private static final String queryHxL2Body = """
            The Query is a simple TableSource that selects all columns from the Hx_L2 table to use in the hierarchy join.
            """;

    private static final String joinQueryBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (`Hx_L2`), the join uses the foreign key `H1L1_KEY`.
            - In the upper-level table (`H1_L1`), the join uses the primary key `KEY`.

            """;

    private static final String h1Level1Body = """
            This Example uses `H1_Level1` level based on the `KEY` column and name column `NAME` of table `H1_L1`.
            """;

    private static final String h1Level2Body = """
            This Example uses `H1_Level2` level based on the `KEY` column and name column `NAME` of table `HX_L2`.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the two levels `H1_Level1` and `H1_Level2`.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.

            """;

    private static final String cubeBody = """
            The cube with two DimensionConnectors.
            One of them with level attribute to level H1_Level1
            Second of them with level attribute to level H1_Level2
            Level attribute in DimensionConnector uses for optimize sql inner join
            Level attribute is name of the level to join to
            If not specified joins to the lowest level of the dimension
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column dimKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column h1L1KeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column h1L1NameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table h1L1Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        h1L1Table.setName("H1_L1");
        h1L1Table.getFeature().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getOwnedElement().add(h1L1Table);

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

        Table hxL2Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        hxL2Table.setName("HX_L2");
        hxL2Table.getFeature().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getOwnedElement().add(hxL2Table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        queryH1L1 = SourceFactory.eINSTANCE.createTableSource();
        queryH1L1.setTable(h1L1Table);

        queryHxL2 = SourceFactory.eINSTANCE.createTableSource();
        queryHxL2.setTable(hxL2Table);

        JoinedQueryElement left = SourceFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(hxL2H1L1KeyColumn);
        left.setQuery(queryHxL2);

        JoinedQueryElement right = SourceFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(h1L1KeyColumn);
        right.setQuery(queryH1L1);

        joinQuery = SourceFactory.eINSTANCE.createJoinSource();
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        h1Level1 = LevelFactory.eINSTANCE.createLevel();
        h1Level1.setName("H1_Level1");
        h1Level1.setColumn(h1L1KeyColumn);
        h1Level1.setNameColumn(h1L1NameColumn);

        h1Level2 = LevelFactory.eINSTANCE.createLevel();
        h1Level2.setName("H1_Level2");
        h1Level2.setColumn(hxL2KeyColumn);
        h1Level2.setNameColumn(hxL2NameColumn);

        hierarchy1 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setQuery(joinQuery);
        hierarchy1.getLevels().add(h1Level1);
        hierarchy1.getLevels().add(h1Level2);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml1");
        dimension.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dim1");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setLevel(h1Level2);
        dimensionConnector1.setForeignKey(dimKeyColumn);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Dim2");
        dimensionConnector2.setDimension(dimension);
        dimensionConnector2.setLevel(h1Level1);
        dimensionConnector2.setForeignKey(dimKeyColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalog.setDescription("Dimension optimization with level attributes");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Dimension Optimisation With Level Attribute", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("H1L1Query", queryH1L1Body, 1, 2, 0, queryH1L1, 2),
                        new DocSection("HxL2Query", queryHxL2Body, 1, 2, 0, queryHxL2, 2),
                        new DocSection("JoinQuery", joinQueryBody, 1, 2, 0, joinQuery, 2),
                        new DocSection("H1_Level1", h1Level1Body, 1, 3, 0, h1Level1, 0),
                        new DocSection("H1_Level2", h1Level2Body, 1, 4, 0, h1Level2, 0),
                        new DocSection("Hierarchy1", hierarchyBody, 1, 8, 0, hierarchy1, 0),
                        new DocSection("Diml1", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cube", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
