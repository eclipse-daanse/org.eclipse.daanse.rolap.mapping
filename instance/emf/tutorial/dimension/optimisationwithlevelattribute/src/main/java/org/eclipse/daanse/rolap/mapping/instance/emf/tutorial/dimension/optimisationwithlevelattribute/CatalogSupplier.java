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
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.09.02", source = Source.EMF, group = "Dimension") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

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
            The Query is a simple TableQuery that selects all columns from the Fact table to use in the measures.
            """;

    private static final String queryH1L1Body = """
            The Query is a simple TableQuery that selects all columns from the H1_L1 table to use in the hierarchy join.
            """;

    private static final String queryHxL2Body = """
            The Query is a simple TableQuery that selects all columns from the Hx_L2 table to use in the hierarchy join.
            """;

    private static final String joinQueryBody = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_optimisationwithlevelattribute");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_column_fact_dim_key");
        dimKeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column h1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setId("_h1_l1_key");
        h1L1KeyColumn.setType(ColumnType.INTEGER);

        Column h1L1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setId("_h1_l1_name");
        h1L1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable h1L1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h1L1Table.setName("H1_L1");
        h1L1Table.setId("_h1_l1");
        h1L1Table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h1L1Table);

        Column hxL2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setId("_hx_l2_key");
        hxL2KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setId("_hx_l2_name");
        hxL2NameColumn.setType(ColumnType.VARCHAR);

        Column hxL2H1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setId("_hx_l2_h1l1_key");
        hxL2H1L1KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2H2L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setId("_hx_l2_h2l1_key");
        hxL2H2L1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable hxL2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hxL2Table.setName("HX_L2");
        hxL2Table.setId("_hx_l2");
        hxL2Table.getColumns().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getTables().add(hxL2Table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_table_factQuery");
        query.setTable(table);

        TableQuery queryH1L1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryH1L1.setId("_h1l1query");
        queryH1L1.setTable(h1L1Table);

        TableQuery queryHxL2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHxL2.setId("_hxl2query");
        queryHxL2.setTable(hxL2Table);

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(hxL2H1L1KeyColumn);
        left.setQuery(queryHxL2);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(h1L1KeyColumn);
        right.setQuery(queryH1L1);

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("_joinQuery");
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure_Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level h1Level1 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level1.setName("H1_Level1");
        h1Level1.setId("_h1_level1");
        h1Level1.setColumn(h1L1KeyColumn);
        h1Level1.setNameColumn(h1L1NameColumn);

        Level h1Level2 = RolapMappingFactory.eINSTANCE.createLevel();
        h1Level2.setName("H1_Level2");
        h1Level2.setId("_h1_level2");
        h1Level2.setColumn(hxL2KeyColumn);
        h1Level2.setNameColumn(hxL2NameColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("_hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setQuery(joinQuery);
        hierarchy1.getLevels().add(h1Level1);
        hierarchy1.getLevels().add(h1Level2);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Diml1");
        dimension.setId("_diml1");
        dimension.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_dim1");
        dimensionConnector1.setOverrideDimensionName("Dim1");
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setLevel(h1Level2);
        dimensionConnector1.setForeignKey(dimKeyColumn);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setId("_dc_dim2");
        dimensionConnector2.setOverrideDimensionName("Dim2");
        dimensionConnector2.setDimension(dimension);
        dimensionConnector2.setLevel(h1Level1);
        dimensionConnector2.setForeignKey(dimKeyColumn);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalog.setDescription("Dimension optimization with level attributes");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Dimension Optimisation With Level Attribute", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(queryH1L1, "H1L1Query", queryH1L1Body, 1, 2, 0, true, 2);
        document(queryHxL2, "HxL2Query", queryHxL2Body, 1, 2, 0, true, 2);
        document(joinQuery, "JoinQuery", joinQueryBody, 1, 2, 0, true, 2);
        document(h1Level1, "H1_Level1", h1Level1Body, 1, 3, 0, true, 0);
        document(h1Level2, "H1_Level2", h1Level2Body, 1, 4, 0, true, 0);

        document(hierarchy1, "Hierarchy1", hierarchyBody, 1, 8, 0, true, 0);
        document(dimension, "Diml1", dimensionBody, 1, 9, 0, true, 0);

        document(cube, "Cube", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
