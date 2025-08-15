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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PercentType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PercentileMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.2.7", source = Source.EMF, group = "Measure")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            Data cubes have percentile measures.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        OrderedColumn orderedColumn = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(valueColumn);

        PercentileMeasure measure1 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure1.setName("Percentile disc 0.25");
        measure1.setId("_measure1");
        measure1.setPercentType(PercentType.DISC);
        measure1.setPercentile(0.25);
        measure1.setColumn(orderedColumn);

        PercentileMeasure measure2 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure2.setName("Percentile cont 0.25");
        measure2.setId("_measure2");
        measure2.setPercentType(PercentType.CONT);
        measure2.setPercentile(0.25);
        measure2.setColumn(orderedColumn);

        PercentileMeasure measure3 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure3.setName("Percentile disc 0.42");
        measure3.setId("_measure3");
        measure3.setPercentType(PercentType.DISC);
        measure3.setPercentile(0.42);
        measure3.setColumn(orderedColumn);

        PercentileMeasure measure4 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure4.setName("Percentile cont 0.42");
        measure4.setId("_measure4");
        measure4.setPercentType(PercentType.CONT);
        measure4.setPercentile(0.42);
        measure4.setColumn(orderedColumn);

        PercentileMeasure measure5 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure5.setName("Percentile disc 0.5");
        measure5.setId("_measure5");
        measure5.setPercentType(PercentType.DISC);
        measure5.setPercentile(0.5);
        measure5.setColumn(orderedColumn);

        PercentileMeasure measure6 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure6.setName("Percentile cont 0.5");
        measure6.setId("_measure6");
        measure6.setPercentType(PercentType.CONT);
        measure6.setPercentile(0.5);
        measure6.setColumn(orderedColumn);

        PercentileMeasure measure7 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure7.setName("Percentile disc 0.75");
        measure7.setId("_measure7");
        measure7.setPercentType(PercentType.DISC);
        measure7.setPercentile(0.75);
        measure7.setColumn(orderedColumn);

        PercentileMeasure measure8 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure8.setName("Percentile cont 0.75");
        measure8.setId("_measure8");
        measure8.setPercentType(PercentType.CONT);
        measure8.setPercentile(0.75);
        measure8.setColumn(orderedColumn);

        PercentileMeasure measure9 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure9.setName("Percentile disc 1.00");
        measure9.setId("_measure9");
        measure9.setPercentType(PercentType.DISC);
        measure9.setPercentile(1.00);
        measure9.setColumn(orderedColumn);

        PercentileMeasure measure10 = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure10.setName("Percentile cont 1.00");
        measure10.setId("_measure10");
        measure10.setPercentType(PercentType.CONT);
        measure10.setPercentile(1.00);
        measure10.setColumn(orderedColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure1, measure2, measure3, measure4, measure5, measure6, measure7, measure8, measure9, measure10));

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("_level");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy_Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml");
        dimension.setId("_diml");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dim");
        dimensionConnector.setOverrideDimensionName("Dim");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(keyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Measure - Percentile Aggragator");
        catalog.getCubes().add(cube);

        document(catalog, "Multiple Percentile Aggragator Measures", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Multiple Percentile Aggragator Measures", cubeBody, 1, 3, 0, true, 2);
        return catalog;
    }

}
