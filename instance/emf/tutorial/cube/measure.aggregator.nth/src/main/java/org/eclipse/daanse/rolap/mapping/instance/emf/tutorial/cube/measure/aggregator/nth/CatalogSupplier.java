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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NthAggMeasure;
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
            Data cubes have NTH measures.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
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
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        Column idColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        idColumn.setName("ID");
        idColumn.setId("_col_id");
        idColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_value");
        valueColumn.setType(ColumnType.INTEGER);

        Column nameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumn.setName("NAME");
        nameColumn.setId("_col_name");
        nameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_tab");
        table.getColumns().addAll(List.of(idColumn, valueColumn, nameColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        OrderedColumn orderedColumn = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn(valueColumn);

        NthAggMeasure measure1 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure1.setName("NthAgg1");
        measure1.setId("_measure1");
        measure1.setIgnoreNulls(true);
        measure1.setN(1);
        measure1.getOrderByColumns().add(orderedColumn);
        measure1.setColumn(valueColumn);

        NthAggMeasure measure2 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure2.setName("NthAgg2");
        measure2.setId("_measure2");
        measure2.setIgnoreNulls(true);
        measure2.setN(2);
        measure2.getOrderByColumns().add(orderedColumn);
        measure2.setColumn(valueColumn);

        NthAggMeasure measure3 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure3.setName("NthAgg3");
        measure3.setId("_measure3");
        measure3.setIgnoreNulls(true);
        measure3.setN(3);
        measure3.getOrderByColumns().add(orderedColumn);
        measure3.setColumn(valueColumn);

        NthAggMeasure measure4 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure4.setName("NthAgg4");
        measure4.setId("_measure4");
        measure4.setIgnoreNulls(true);
        measure4.setN(4);
        measure4.getOrderByColumns().add(orderedColumn);
        measure4.setColumn(valueColumn);

        NthAggMeasure measure5 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure5.setName("NthAgg5");
        measure5.setId("_measure5");
        measure5.setIgnoreNulls(true);
        measure5.setN(5);
        measure5.getOrderByColumns().add(orderedColumn);
        measure5.setColumn(valueColumn);

        NthAggMeasure measure6 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure6.setName("NthAgg6");
        measure6.setId("_measure6");
        measure6.setIgnoreNulls(true);
        measure6.setN(6);
        measure6.getOrderByColumns().add(orderedColumn);
        measure6.setColumn(valueColumn);

        NthAggMeasure measure7 = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure7.setName("NthAgg7");
        measure7.setId("_measure7");
        measure7.setIgnoreNulls(true);
        measure7.setN(7);
        measure7.getOrderByColumns().add(orderedColumn);
        measure7.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3, measure4, measure5, measure6, measure7));

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Value");
        level.setId("_level");
        level.setColumn(valueColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(false);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy_Hierarchy");
        hierarchy.setPrimaryKey(idColumn);
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
        dimensionConnector.setForeignKey(idColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Measure - NTH value Aggragator");
        catalog.getCubes().add(cube);

        document(catalog, "Multiple NTH Value Aggragator Measures", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and NTH Aggragator Measures", cubeBody, 1, 3, 0, true, 2);
        return catalog;
    }

}
