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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.inlinetable;


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
import org.eclipse.daanse.rolap.mapping.model.database.relational.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.database.source.InlineTableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Row;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RowSet;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.DataSlot;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.instance.InstanceFactory;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.01", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private InlineTableSource inlineTableQuery;
    private TableSource query;
    private SumMeasure measure;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Cube with hierarchy which use inner table. This example shows combine phisical table as fact and Inline table for hierarchy
            Inline table represents a table with data embedded directly in the schema
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a two tables that stores all the data.

            - The phisical table is named `Fact` uses for Cube1 and contains two columns: `DIM_KEY` and `VALUE`. The `DIM_KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            - The InlineTable is named `HT` uses for Hierarchy and contains 3 columns: `KEY`, `VALUE`,`NAME` .

            InlineTable represents a table with data embedded directly in the schema.
            InlineTable uses for hierarchy.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String inlineTableQueryBody = """
            The bridge between the cube and InlineTable `HT`.
            """;

    private static final String level1Body = """
            The Level uses the column attribute to specify the primary key `KEY` from Inline table `HT`.
            Additionally, it defines the nameColumn `NAME` from Inline table `HT` attribute  to specify
            the column that contains the name of the level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists level Level1.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            Query uses Inline table as data sourse.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measure1Body = """
            Measure1 use Fact table VALUE column with sum aggregation in Cube.
            """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data. This example shows combine phisical table as fact and Inline table for hierarchy
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column dimKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column htKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        htKeyColumn.setName("KEY");
        htKeyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column htValueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setType(SqlSimpleTypes.numericType(18, 4));

        Column htNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        htNameColumn.setName("NAME");
        htNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        DataSlot r1v1 = InstanceFactory.eINSTANCE.createDataSlot();
        r1v1.setFeature(htKeyColumn);
        r1v1.setDataValue("1");

        DataSlot r1v2 = InstanceFactory.eINSTANCE.createDataSlot();
        r1v2.setFeature(htValueColumn);
        r1v2.setDataValue("100.5");

        DataSlot r1v3 = InstanceFactory.eINSTANCE.createDataSlot();
        r1v3.setFeature(htNameColumn);
        r1v3.setDataValue("name1");

        Row r1 = RelationalFactory.eINSTANCE.createRow();
        r1.getSlot().addAll(List.of(r1v1, r1v2, r1v3));

        DataSlot r2v1 = InstanceFactory.eINSTANCE.createDataSlot();
        r2v1.setFeature(htKeyColumn);
        r2v1.setDataValue("2");

        DataSlot r2v2 = InstanceFactory.eINSTANCE.createDataSlot();
        r2v2.setFeature(htValueColumn);
        r2v2.setDataValue("100.2");

        DataSlot r2v3 = InstanceFactory.eINSTANCE.createDataSlot();
        r2v3.setFeature(htNameColumn);
        r2v3.setDataValue("name2");

        Row r2 = RelationalFactory.eINSTANCE.createRow();
        r2.getSlot().addAll(List.of(r2v1, r2v2, r2v3));
        InlineTable inlineTable = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createInlineTable();
        inlineTable.setExtent(RelationalFactory.eINSTANCE.createRowSet());
        inlineTable.setName("HT");
        inlineTable.getFeature().addAll(List.of(htKeyColumn, htValueColumn, htNameColumn));
        inlineTable.getExtent().getOwnedElement().addAll(List.of(r1, r2));
        databaseSchema.getOwnedElement().add(inlineTable);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        inlineTableQuery = SourceFactory.eINSTANCE.createInlineTableSource();
        inlineTableQuery.setTable(inlineTable);
        inlineTableQuery.setAlias("HT");

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setColumn(htKeyColumn);
        level1.setNameColumn(htNameColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setPrimaryKey(htKeyColumn);
        hierarchy.setQuery(inlineTableQuery);
        hierarchy.getLevels().addAll(List.of(level1));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(dimKeyColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Inline Table");
        catalog.setDescription("Hierarchy with inline table data");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Inline Table", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("InlineTableQuery", inlineTableQueryBody, 1, 3, 0, inlineTableQuery, 2),
                        new DocSection("Dimension1", dimensionBody, 1, 4, 0, dimension, 2),
                        new DocSection("Hierarchy", hierarchyBody, 1, 5, 0, hierarchy, 2),
                        new DocSection("Level1", level1Body, 1, 6, 0, level1, 2),
                        new DocSection("Measure1", measure1Body, 1, 7, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 8, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
