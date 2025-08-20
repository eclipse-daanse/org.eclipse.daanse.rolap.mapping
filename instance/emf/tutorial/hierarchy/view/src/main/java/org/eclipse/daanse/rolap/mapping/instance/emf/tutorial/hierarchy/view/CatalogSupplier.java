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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.view;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlView;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.2", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Cube with hierarchy which use SQL query. This example shows combine phisical table as fact and SqlView for hierarchy
            SqlView represents a virtual table defined by SQL query expressions rather than physical database tables.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a two tables and and SqlView that stores all the data.
            The phisical table is named `Fact` uses for Cube1 and contains two columns: `DIM_KEY` and `VALUE`.
            The KEY column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            The phisical table is named `HT` uses for Hierarchy and contains 3 columns: `KEY`, `VALUE`,`NAME` .
            SqlView represents a virtual table defined by SQL query expressions rather than physical database tables.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String sqlSelectQueryBody = """
            The bridge between the cube and SqlView HT_VIEW.
            """;

    private static final String level1Body = """
            The Level uses the column attribute to specify the primary key `KEY` from SqlView `HT_VIEW`.
            Additionally, it defines the nameColumn `NAME` from SqlView `HT_VIEW` attribute  to specify
            the column that contains the name of the level.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists level Level1.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            Query uses SqlView `HT_VIEW` as data sourse.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measure1Body = """
            Measure1 use Fact table VALUE column with sum aggregation in Cube.
            """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data. This example shows combine phisical table as fact and SqlView for hierarchy
            """;

    private static final String catalogDocumentationTxt = """
            Catalog of a minimal cube with hierarchy with SQL view reference.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_view");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_column_fact_dimKey");
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

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_ht_key");
        keyColumn.setType(ColumnType.INTEGER);

        Column nameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameColumn.setName("NAME");
        nameColumn.setId("_column_ht_name");
        nameColumn.setType(ColumnType.VARCHAR);

        Column htValueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htValueColumn.setName("VALUE");
        htValueColumn.setId("_column_ht_value");
        htValueColumn.setType(ColumnType.INTEGER);

        PhysicalTable htTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        htTable.setName("HT");
        htTable.setId("_table_ht");
        htTable.getColumns().addAll(List.of(keyColumn, nameColumn, htValueColumn));
        databaseSchema.getTables().add(htTable);

        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE.createSqlStatement();
        sqlStatement.getDialects().addAll(List.of("generic", "h2"));
        sqlStatement.setSql("select * from HT");
        SqlView sqlView = RolapMappingFactory.eINSTANCE.createSqlView();
        sqlView.setName("HT_VIEW");
        sqlView.setId("_sqlView_htView");
        sqlView.getSqlStatements().add(sqlStatement);

        Column keyViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyViewColumn.setName("KEY");
        keyViewColumn.setId("_column_view_key");
        keyViewColumn.setType(ColumnType.INTEGER);

        Column nameViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        nameViewColumn.setName("NAME");
        nameViewColumn.setId("_column_view_name");
        nameViewColumn.setType(ColumnType.VARCHAR);

        Column htValueViewColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        htValueViewColumn.setName("VALUE");
        htValueViewColumn.setId("_column_view_value");
        htValueViewColumn.setType(ColumnType.INTEGER);

        sqlView.getColumns().addAll(List.of(keyViewColumn, nameViewColumn, htValueViewColumn));

        databaseSchema.getTables().add(sqlView);

        SqlSelectQuery sqlSelectQuery = RolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        sqlSelectQuery.setId("_query_htViewSelect");
        sqlSelectQuery.setAlias("HT_VIEW");
        sqlSelectQuery.setSql(sqlView);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("_measure_measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level1");
        level.setId("_level_level1");
        level.setColumn(keyViewColumn);
        level.setNameColumn(nameViewColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_hierarchy_hierarchy");
        hierarchy.setPrimaryKey(keyViewColumn);
        hierarchy.setQuery(sqlSelectQuery);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension_dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_dimension");
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube_cube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Hierarchy View");
        catalog.setDescription("Hierarchy with SQL view references");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Catalog of a minimal cube with hierarchy with SQL view reference", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(sqlSelectQuery, "HT_VIEW", sqlSelectQueryBody, 1, 3, 0, true, 2);
        document(dimension, "Dimension", dimensionBody, 1, 4, 0, true, 2);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 5, 0, true, 2);
        document(level, "Level1", level1Body, 1, 6, 0, true, 2);
        document(measure, "Measure1", measure1Body, 1, 7, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 8, 0, true, 2);

        return catalog;
    }
}
