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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.Row;
import org.eclipse.daanse.rolap.mapping.model.RowValue;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.1", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_inlinetable");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_column_fact_dim_key");
        dimKeyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column htKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htKeyColumn.setName("KEY");
        htKeyColumn.setId("_ht_key");
        htKeyColumn.setType(ColumnType.VARCHAR);

        Column htValueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setId("_ht_value");
        htValueColumn.setType(ColumnType.NUMERIC);

        Column htNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htNameColumn.setName("NAME");
        htNameColumn.setId("_ht_name");
        htNameColumn.setType(ColumnType.VARCHAR);

        RowValue r1v1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v1.setColumn(htKeyColumn);
        r1v1.setValue("1");

        RowValue r1v2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v2.setColumn(htValueColumn);
        r1v2.setValue("100.5");

        RowValue r1v3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r1v3.setColumn(htNameColumn);
        r1v3.setValue("name1");

        Row r1 = RolapMappingFactory.eINSTANCE.createRow();
        r1.getRowValues().addAll(List.of(r1v1, r1v2, r1v3));

        RowValue r2v1 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v1.setColumn(htKeyColumn);
        r2v1.setValue("2");

        RowValue r2v2 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v2.setColumn(htValueColumn);
        r2v2.setValue("100.2");

        RowValue r2v3 = RolapMappingFactory.eINSTANCE.createRowValue();
        r2v3.setColumn(htNameColumn);
        r2v3.setValue("name2");

        Row r2 = RolapMappingFactory.eINSTANCE.createRow();
        r2.getRowValues().addAll(List.of(r2v1, r2v2, r2v3));
        InlineTable inlineTable = RolapMappingFactory.eINSTANCE.createInlineTable();
        inlineTable.setName("HT");
        inlineTable.getColumns().addAll(List.of(htKeyColumn, htValueColumn, htNameColumn));
        inlineTable.getRows().addAll(List.of(r1, r2));
        databaseSchema.getTables().add(inlineTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        InlineTableQuery inlineTableQuery = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        inlineTableQuery.setId("_inlineTableQuery");
        inlineTableQuery.setTable(inlineTable);
        inlineTableQuery.setAlias("HT");

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("_measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_level1");
        level1.setColumn(htKeyColumn);
        level1.setNameColumn(htNameColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("_hierarchy1");
        hierarchy.setPrimaryKey(htKeyColumn);
        hierarchy.setQuery(inlineTableQuery);
        hierarchy.getLevels().addAll(List.of(level1));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("_dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dimension1");
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(dimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Inline Table");
        catalog.setDescription("Hierarchy with inline table data");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Inline Table", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(inlineTableQuery, "InlineTableQuery", inlineTableQueryBody, 1, 3, 0, true, 2);
        document(dimension, "Dimension1", dimensionBody, 1, 4, 0, true, 2);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 5, 0, true, 2);
        document(level1, "Level1", level1Body, 1, 6, 0, true, 2);
        document(measure, "Measure1", measure1Body, 1, 7, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 8, 0, true, 2);

        return catalog;
    }
}
