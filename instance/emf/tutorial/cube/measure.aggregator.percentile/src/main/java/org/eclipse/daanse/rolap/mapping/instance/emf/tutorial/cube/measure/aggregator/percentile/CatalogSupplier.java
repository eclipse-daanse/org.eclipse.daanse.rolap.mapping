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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.percentile;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.PercentType;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.PercentileMeasure;
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
            Data cubes have percentile measures.
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
            - Percentile disc 0.25 – Percentile 'disc' 0.25.
            - Percentile cont 0.25 – Percentile 'cont' 0.25.
            - Percentile disc 0.42 – Percentile 'disc' 0.42.
            - Percentile cont 0.42 – Percentile 'cont' 0.42.
            - Percentile disc 0.5 – Percentile 'disc' 0.5.
            - Percentile cont 0.5 – Percentile 'cont' 0.5.
            - Percentile disc 0.75 – Percentile 'disc' 0.75.
            - Percentile cont 0.75 – Percentile 'cont' 0.75.
            - Percentile disc 1.0 – Percentile 'disc' 1.00.
            - Percentile cont 1.0 – Percentile 'cont' 1.00.
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

        orderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(valueColumn);

        PercentileMeasure measure1 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure1.setName("Percentile disc 0.25");
        measure1.setPercentType(PercentType.DISC);
        measure1.setPercentile(0.25);
        measure1.setColumn(orderedColumn);

        PercentileMeasure measure2 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure2.setName("Percentile cont 0.25");
        measure2.setPercentType(PercentType.CONT);
        measure2.setPercentile(0.25);
        measure2.setColumn(orderedColumn);

        PercentileMeasure measure3 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure3.setName("Percentile disc 0.42");
        measure3.setPercentType(PercentType.DISC);
        measure3.setPercentile(0.42);
        measure3.setColumn(orderedColumn);

        PercentileMeasure measure4 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure4.setName("Percentile cont 0.42");
        measure4.setPercentType(PercentType.CONT);
        measure4.setPercentile(0.42);
        measure4.setColumn(orderedColumn);

        PercentileMeasure measure5 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure5.setName("Percentile disc 0.5");
        measure5.setPercentType(PercentType.DISC);
        measure5.setPercentile(0.5);
        measure5.setColumn(orderedColumn);

        PercentileMeasure measure6 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure6.setName("Percentile cont 0.5");
        measure6.setPercentType(PercentType.CONT);
        measure6.setPercentile(0.5);
        measure6.setColumn(orderedColumn);

        PercentileMeasure measure7 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure7.setName("Percentile disc 0.75");
        measure7.setPercentType(PercentType.DISC);
        measure7.setPercentile(0.75);
        measure7.setColumn(orderedColumn);

        PercentileMeasure measure8 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure8.setName("Percentile cont 0.75");
        measure8.setPercentType(PercentType.CONT);
        measure8.setPercentile(0.75);
        measure8.setColumn(orderedColumn);

        PercentileMeasure measure9 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure9.setName("Percentile disc 1.00");
        measure9.setPercentType(PercentType.DISC);
        measure9.setPercentile(1.00);
        measure9.setColumn(orderedColumn);

        PercentileMeasure measure10 = MeasureFactory.eINSTANCE.createPercentileMeasure();
        measure10.setName("Percentile cont 1.00");
        measure10.setPercentType(PercentType.CONT);
        measure10.setPercentile(1.00);
        measure10.setColumn(orderedColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure1, measure2, measure3, measure4, measure5, measure6, measure7, measure8, measure9, measure10));

        Level level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dim");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(keyColumn);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Aggregator Percentile");
        catalog.setDescription("Percentile aggregation functions");
        catalog.getCubes().add(cube);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Aggregator Percentile", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Ordered Column", orderedColumnBody, 1, 2, 0, orderedColumn, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
