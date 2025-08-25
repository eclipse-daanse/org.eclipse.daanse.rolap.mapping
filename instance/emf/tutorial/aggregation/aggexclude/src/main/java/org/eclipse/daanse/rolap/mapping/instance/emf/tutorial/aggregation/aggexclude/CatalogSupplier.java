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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregation.aggexclude;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.8.1", source = Source.EMF, group = "Aggregation") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial discusses TableQuery with AggregationExclude.
            AggregationExclude defines exclusion rules that prevent specific tables from being used as aggregation tables,
            even if they would otherwise match aggregation patterns or be considered suitable for aggregation optimization.
            AggregationExclude is essential for maintaining aggregation accuracy and system reliability by providing explicit
            control over which tables should be avoided during aggregation table discovery and selection.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table is named `Fact` and contains two columns: `KEY` and `VALUE`.
            The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery, as it directly references the physical table `Fact`.
            The query element is not visible to users accessing the cube through the XMLA API, such as Daanse Dashboard, Power BI, or Excel.
            """;

    private static final String cubeBody = """
            The cube is the element visible to users in analysis tools. A cube is based on elements such as measures, dimensions, hierarchies, KPIs, and named sets.
            In this case, we only define measures, which are the minimal required elements. The other elements are optional. To link a measure to the cube, we use the `MeasureGroup` element.
            The `MeasureGroup` is useful for organizing multiple measures into logical groups. Measures are used to define the data that should be aggregated.
            In this example, the measure is named Measure-Sum and references the `VALUE` column in the Fact table. The measure is aggregated using summation.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_AggExclude");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        //"KEY","VALUE_count"
        //VARCHAR,INTEGER

        Column aggKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        aggKeyColumn.setName("KEY");
        aggKeyColumn.setId("_column_agg_01_fact_key");
        aggKeyColumn.setType(ColumnType.VARCHAR);

        Column aggValueCountColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        aggValueCountColumn.setName("KEY");
        aggValueCountColumn.setId("_column_agg_01_Fact_VALUE_count");
        aggValueCountColumn.setType(ColumnType.VARCHAR);

        PhysicalTable aggTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggTable.setName("agg_01_Fact");
        aggTable.setId("_table_agg_01_Fact");
        aggTable.getColumns().addAll(List.of(aggKeyColumn, aggValueCountColumn));
        databaseSchema.getTables().add(aggTable);

        AggregationExclude aggregationExclude = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude.setId("_aggregationExclude_agg_01_Fact");
        aggregationExclude.setName("agg_01_Fact");

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_factQuery");
        query.setTable(table);
        query.getAggregationExcludes().add(aggregationExclude);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure_Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube_Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Aggregation Agg Exclude");
        catalog.setDescription("Aggregate exclusion patterns");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Aggregation Agg Exclude", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(cube, "Cube, MeasureGroup and Measure", cubeBody, 1, 3, 0, true, 2);

        return catalog;

    }

}
