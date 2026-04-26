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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.ordinalcolumns;


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
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.SortingDirection;
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
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.06", source = Source.EMF, group = "Level") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private OrderedColumn townOrderedColumn;
    private PhysicalCube cube;
    private OrderedColumn countryOrderedColumn;
    private TableSource query;
    private Level levelCountry;
    private Level levelTown;


    private static final String introBody = """
            This Tutorial shows how to use Ordinal columns in levels. This Tutorial shows `Country` with sorting ascending and `Town` with sorting descending
            Ordinal columns parameter can to use with multiple columns. in this case, sorting occurs by several columns
            OrderedColumn is typically used in OLAP contexts where explicit column ordering is required for query processing or result presentation.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on one table. The `Fact` table contains a columns the name of the `Town` and the `Country`.
            """;

    private static final String levelTownBody = """
            The level of the `Town` used the `column` attribute to define the column that holds the name, which is also the key Column.
            """;

    private static final String levelCountryBody = """
            The level  of the `Country` used the `column` attribute to define the column that holds the name, which is also the key Column.
            """;

    private static final String orderedColumnCountryBody = """
            OrderedColumn for level country. OrderedColumn use ascending sorting by country name
            """;

    private static final String orderedColumnTownBody = """
            OrderedColumn for level town. OrderedColumn use ascending sorting by town name
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains both defined levels. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used.
            """;

    private static final String queryFactBody = """
            The TableSource for the Levels and the Measure.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column columnKey = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKey.setName("KEY");
        columnKey.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnValue = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnValue.setName("VALUE");
        columnValue.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnCountry = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnCountry.setName("COUNTRY");
        columnCountry.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableFact = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFact.setName("Fact");
        tableFact.getFeature().addAll(List.of(columnKey,columnCountry, columnValue));
        databaseSchema.getOwnedElement().add(tableFact);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(tableFact);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(columnValue);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        countryOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        countryOrderedColumn.setColumn(columnCountry);
        countryOrderedColumn.setDirection(SortingDirection.ASC);

        townOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        townOrderedColumn.setColumn(columnKey);
        townOrderedColumn.setDirection(SortingDirection.DESC);

        levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(columnKey);
        levelTown.getOrdinalColumns().add(townOrderedColumn);

        levelCountry = LevelFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setColumn(columnCountry);
        levelCountry.getOrdinalColumns().add(countryOrderedColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("CountryHierarchy");
        hierarchy.setPrimaryKey(columnKey);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Country");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnCountry);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube with ordinal columns");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Levels with ordinal columns");
        catalog.setDescription("Levels with ordinal columns");
        catalog.getCubes().add(cube);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Levels with ordinal columns", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryFactBody, 1, 3, 0, query, 2),
                        new DocSection("Level town", levelTownBody, 1, 4, 0, levelTown, 0),
                        new DocSection("Level country", levelCountryBody, 1, 5, 0, levelCountry, 0),
                        new DocSection("Ordered Column Country", orderedColumnCountryBody, 1, 6, 0, countryOrderedColumn, 0),
                        new DocSection("Ordered Column Town", orderedColumnTownBody, 1, 7, 0, townOrderedColumn, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 8, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cube and DimensionConnector and Measure", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
