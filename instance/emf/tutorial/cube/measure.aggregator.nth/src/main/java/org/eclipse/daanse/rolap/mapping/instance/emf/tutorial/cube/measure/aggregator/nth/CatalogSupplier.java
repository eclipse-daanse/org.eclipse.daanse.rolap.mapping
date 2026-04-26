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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.nth;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.NthAggMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.02.07", source = Source.EMF, group = "Measure")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private OrderedColumn orderedColumn;
    private Schema databaseSchema;


    private static final String introBody = """
            Data cubes have NTH measures.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String orderedColumnBody = """
            Represents a column with specific ordering information used in queries and result sets.
            OrderedColumn is typically used in OLAP contexts where explicit column ordering is required for query processing or result presentation.
            OrderedColumn uses ascending by default.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following aggregation functions:
            - NTH1 – NTH1 .
            - NTH2 – NTH2 .
            - NTH3 – NTH3 .
            - NTH4 – NTH4 .
            - NTH5 – NTH5 .
            - NTH6 – NTH6 .
            - NTH7 – NTH7 .
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column idColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        idColumn.setName("ID");
        idColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumn.setName("NAME");
        nameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(idColumn, valueColumn, nameColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        orderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(valueColumn);

        NthAggMeasure measure1 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure1.setName("NthAgg1");
        measure1.setIgnoreNulls(true);
        measure1.setN(1);
        measure1.getOrderByColumns().add(orderedColumn);
        measure1.setColumn(valueColumn);

        NthAggMeasure measure2 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure2.setName("NthAgg2");
        measure2.setIgnoreNulls(true);
        measure2.setN(2);
        measure2.getOrderByColumns().add(orderedColumn);
        measure2.setColumn(valueColumn);

        NthAggMeasure measure3 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure3.setName("NthAgg3");
        measure3.setIgnoreNulls(true);
        measure3.setN(3);
        measure3.getOrderByColumns().add(orderedColumn);
        measure3.setColumn(valueColumn);

        NthAggMeasure measure4 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure4.setName("NthAgg4");
        measure4.setIgnoreNulls(true);
        measure4.setN(4);
        measure4.getOrderByColumns().add(orderedColumn);
        measure4.setColumn(valueColumn);

        NthAggMeasure measure5 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure5.setName("NthAgg5");
        measure5.setIgnoreNulls(true);
        measure5.setN(5);
        measure5.getOrderByColumns().add(orderedColumn);
        measure5.setColumn(valueColumn);

        NthAggMeasure measure6 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure6.setName("NthAgg6");
        measure6.setIgnoreNulls(true);
        measure6.setN(6);
        measure6.getOrderByColumns().add(orderedColumn);
        measure6.setColumn(valueColumn);

        NthAggMeasure measure7 = MeasureFactory.eINSTANCE.createNthAggMeasure();
        measure7.setName("NthAgg7");
        measure7.setIgnoreNulls(true);
        measure7.setN(7);
        measure7.getOrderByColumns().add(orderedColumn);
        measure7.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3, measure4, measure5, measure6, measure7));

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Value");
        level.setColumn(valueColumn);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(idColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dim");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(idColumn);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Aggregator Nth");
        catalog.setDescription("Nth value aggregation functions");
        catalog.getCubes().add(cube);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Aggregator Nth", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Ordered Column", orderedColumnBody, 1, 2, 0, orderedColumn, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
