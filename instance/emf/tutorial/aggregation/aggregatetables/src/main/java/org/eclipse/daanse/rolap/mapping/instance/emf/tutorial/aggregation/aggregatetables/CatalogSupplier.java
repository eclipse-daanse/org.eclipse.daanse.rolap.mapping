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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregation.aggregatetables;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.8.2", source = Source.EMF, group = "Aggregation") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String SALES = "Sales";
    private static final String SALES_FACT_1997 = "SALES_FACT_1997";

    private static final String catalogBody = """
            This tutorial discusses TableQuery with AggregationExclude.
            AggregationExclude defines exclusion rules that prevent specific tables from being used as aggregation tables,
            even if they would otherwise match aggregation patterns or be considered suitable for aggregation optimization.
            AggregationExclude is essential for maintaining aggregation accuracy and system reliability by providing explicit
            control over which tables should be avoided during aggregation table discovery and selection.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on

            SALES_FACT_1997 table which contains two columns: `PRODUCT_ID` and `STORE_COST`.
            PRODUCT table which contains 4 columns: `PRODUCT_CLASS_ID`,`PRODUCT_ID`,`brand_name`,`product_name`
            PRODUCT_CLASS table which contains 3 columns: `PRODUCT_CLASS_ID`, `PRODUCT_ID` and `brand_name`.
            AGG_C_SPECIAL_SALES_FACT_1997 table which contains 3 columns: `PRODUCT_ID`, `STORE_COST_SUM`, `FACT_COUNT`;
            AGG_C_14_SALES_FACT_1997 this is exclude table
            AGG_LC_100_SALES_FACT_1997 this is exclude table
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery, as it directly references the physical table `SALES_FACT_1997`.
            The query element is not visible to users accessing the cube through the XMLA API, such as Daanse Dashboard, Power BI, or Excel.
            this TableQuery have one AggregationTable with reference to 'AGG_C_SPECIAL_SALES_FACT_1997' the specific database table that contains the pre-computed aggregation data.
            this tabele will use for calculate aggregation data for aggregationMeasure [Measures].[Store Cost] for level [Product].[Product Family].[Product Family].
            """;

    private static final String queryProductBody = """
            The TableQuery for the PRODUCT table.
            """;

    private static final String queryProductClassBody = """
            The TableQuery for the PRODUCT_CLASS table.
            """;

    private static final String queryJoinBody = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the PRODUCT the join uses the foreign key.
            - In the PRODUCT_CLASS table, the join uses the primary key.
            """;

    private static final String cubeBody = """
            The cube is the element visible to users in analysis tools. A cube is based on elements such as measures, dimensions, hierarchies, KPIs, and named sets.
            In this case, we only define measures, which are the minimal required elements. The other elements are optional. To link a measure to the cube, we use the `MeasureGroup` element.
            The `MeasureGroup` is useful for organizing multiple measures into logical groups. Measures are used to define the data that should be aggregated.
            In this example, the measure is named Store Cost and references the `STORE_COST` column in the SALES_FACT_1997 table.
            But fact table query has AggregationTable 'AGG_C_SPECIAL_SALES_FACT_1997'. The aggregation calculation will use AGG_C_SPECIAL_SALES_FACT_1997 table instead of SALES_FACT_1997.
            The measure is aggregated using summation.
            """;

    private static final String schemaDocumentationTxt = """
            Aggregate tables are a way to improve performance when the fact table contains
            a huge number of rows: a million or more. An aggregate table is essentially a pre-computed
            summary of the data in the fact table.
                    """;
    private static final String levelBody = """
            The Level Product Family uses the column attribute to specify the primary key column PRODUCT_FAMILE from table PRODUCT_CLASS.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists the level Product Family.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the Join-query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_AggregateTables");

        Column productIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productIdColumn.setName("PRODUCT_ID");
        productIdColumn.setId("_column_sales_fact_1997_product_id");
        productIdColumn.setType(ColumnType.INTEGER);

        Column storeCostColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        storeCostColumn.setName("STORE_COST");
        storeCostColumn.setId("_column_sales_fact_1997_store_cost");
        storeCostColumn.setType(ColumnType.DECIMAL);
        storeCostColumn.setColumnSize(10);
        storeCostColumn.setDecimalDigits(4);

        PhysicalTable salesFact1997 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        salesFact1997.setName(SALES_FACT_1997);
        salesFact1997.setId("_table_sales_fact_1997");
        salesFact1997.getColumns().addAll(List.of(productIdColumn, storeCostColumn));
        databaseSchema.getTables().add(salesFact1997);

        Column productProductClassIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productProductClassIdColumn.setId("_column_product_product_class_id");
        productProductClassIdColumn.setType(ColumnType.INTEGER);

        Column productProductIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productProductIdColumn.setName("PRODUCT_ID");
        productProductIdColumn.setId("_column_product_product_id");
        productProductIdColumn.setType(ColumnType.INTEGER);

        Column productBrandNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productBrandNameColumn.setName("brand_name");
        productBrandNameColumn.setId("_column_product_brandName");
        productBrandNameColumn.setType(ColumnType.VARCHAR);
        productBrandNameColumn.setColumnSize(60);

        Column productProductNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productProductNameColumn.setName("product_name");
        productProductNameColumn.setId("_column_product_productName");
        productProductNameColumn.setType(ColumnType.VARCHAR);
        productProductNameColumn.setColumnSize(60);

        PhysicalTable productTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        productTable.setName("PRODUCT");
        productTable.setId("_table_product");
        productTable.getColumns().addAll(List.of(productProductClassIdColumn, productProductIdColumn,
                productBrandNameColumn, productProductNameColumn));
        databaseSchema.getTables().add(productTable);

        Column productClassProductClassIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productClassProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productClassProductClassIdColumn.setId("_column_product_class_product_class_id");
        productClassProductClassIdColumn.setType(ColumnType.INTEGER);

        Column productClassProductFamileColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        productClassProductFamileColumn.setName("PRODUCT_FAMILE");
        productClassProductFamileColumn.setId("_column_product_class_product_famile");
        productClassProductFamileColumn.setType(ColumnType.VARCHAR);
        productClassProductFamileColumn.setColumnSize(60);

        PhysicalTable productClassTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        productClassTable.setName("PRODUCT_CLASS");
        productClassTable.setId("_table_product_class");
        productClassTable.getColumns()
                .addAll(List.of(productClassProductClassIdColumn, productClassProductFamileColumn));
        databaseSchema.getTables().add(productClassTable);

        Column aggCSpecialSalesFact1997ProductIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        aggCSpecialSalesFact1997ProductIdColumn.setName("PRODUCT_ID");
        aggCSpecialSalesFact1997ProductIdColumn.setId("_column_agg_c_special_sales_fact_1997_product_id");
        aggCSpecialSalesFact1997ProductIdColumn.setType(ColumnType.INTEGER);

        Column aggCSpecialSalesFact1997StoreCostSumColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        aggCSpecialSalesFact1997StoreCostSumColumn.setName("STORE_COST_SUM");
        aggCSpecialSalesFact1997StoreCostSumColumn.setId("_column_agg_c_special_sales_fact_1997_store_cost_sum");
        aggCSpecialSalesFact1997StoreCostSumColumn.setType(ColumnType.DECIMAL);
        aggCSpecialSalesFact1997StoreCostSumColumn.setColumnSize(10);
        aggCSpecialSalesFact1997StoreCostSumColumn.setDecimalDigits(4);

        Column aggCSpecialSalesFact1997FactCountColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        aggCSpecialSalesFact1997FactCountColumn.setName("FACT_COUNT");
        aggCSpecialSalesFact1997FactCountColumn.setId("_column_agg_c_special_sales_fact_1997_fact_count");
        aggCSpecialSalesFact1997FactCountColumn.setType(ColumnType.INTEGER);

        //PRODUCT_ID,STORE_COST_SUM,FACT_COUNT
        //INTEGER,DECIMAL(10.4),INTEGER

        PhysicalTable aggCSpecialSalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggCSpecialSalesFact1997Table.setName("AGG_C_SPECIAL_SALES_FACT_1997");
        aggCSpecialSalesFact1997Table.setId("_table_agg_c_special_sales_fact_1997");
        aggCSpecialSalesFact1997Table.getColumns().addAll(List.of(aggCSpecialSalesFact1997ProductIdColumn,
                aggCSpecialSalesFact1997StoreCostSumColumn, aggCSpecialSalesFact1997FactCountColumn));
        databaseSchema.getTables().add(aggCSpecialSalesFact1997Table);

        PhysicalTable aggC14SalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggC14SalesFact1997Table.setName("AGG_C_14_SALES_FACT_1997");
        aggC14SalesFact1997Table.setId("_table_agg_c_14_sales_fact_1997");
        aggC14SalesFact1997Table.getColumns().addAll(List.of());
        databaseSchema.getTables().add(aggC14SalesFact1997Table);

        PhysicalTable aggLc100SalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggLc100SalesFact1997Table.setName("AGG_LC_100_SALES_FACT_1997");
        aggLc100SalesFact1997Table.setId("_table_agg_lc_100_sales_fact_1997");
        aggLc100SalesFact1997Table.getColumns().addAll(List.of());
        databaseSchema.getTables().add(aggLc100SalesFact1997Table);

        AggregationColumnName aggregationColumnName = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        aggregationColumnName.setColumn(aggCSpecialSalesFact1997FactCountColumn);

        AggregationMeasure aggregationMeasure = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        aggregationMeasure.setName("[Measures].[Store Cost]");
        aggregationMeasure.setColumn(aggCSpecialSalesFact1997StoreCostSumColumn);

        AggregationLevel aggregationLevel = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        aggregationLevel.setName("[Product].[Product Family].[Product Family]");
        aggregationLevel.setColumn(productClassProductFamileColumn);//??

        AggregationExclude aggregationExclude1 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude1.setName("AGG_C_14_SALES_FACT_1997");
        AggregationExclude aggregationExclude2 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude2.setName("AGG_LC_100_SALES_FACT_1997");

        AggregationName aggregationName = RolapMappingFactory.eINSTANCE.createAggregationName();
        aggregationName.setId("_aggregationName_AGG_C_SPECIAL_SALES_FACT_1997");
        aggregationName.setName(aggCSpecialSalesFact1997Table);
        aggregationName.setAggregationFactCount(aggregationColumnName);
        aggregationName.getAggregationMeasures().add(aggregationMeasure);
        aggregationName.getAggregationLevels().add(aggregationLevel);

        TableQuery productQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        productQuery.setId("_query_productQuery");
        productQuery.setTable(productTable);

        TableQuery productClassQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        productClassQuery.setId("_query_productClassQuery");
        productClassQuery.setTable(productClassTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(salesFact1997);
        query.setId("_query_salesFact1997Query");
        query.getAggregationTables().add(aggregationName);
        query.getAggregationExcludes().addAll(List.of(aggregationExclude1, aggregationExclude2));

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(productProductClassIdColumn);
        left.setQuery(productQuery);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(productClassProductClassIdColumn);
        right.setQuery(productClassQuery);

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("_joinQuery_productClassProduct");
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Store Cost");
        measure.setId("_measure_StoreCost");
        measure.setColumn(storeCostColumn);
        measure.setFormatString("#,###.00");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Product Family");
        level.setId("_level_Product_Family_Level");
        level.setColumn(productClassProductFamileColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Product Family");
        hierarchy.setId("_hierarchy_Product_Family_Hierarchy");
        hierarchy.setPrimaryKey(productProductIdColumn);
        hierarchy.setDisplayFolder("Details");
        hierarchy.setQuery(joinQuery);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Product");
        dimension.setId("_dimension_ProductDimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_product");
        dimensionConnector.setOverrideDimensionName("Product");
        dimensionConnector.setForeignKey(productIdColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(SALES);
        cube.setId("_cube_Sales");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalog.setDescription("Aggregate table optimization techniques");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema with aggregate tables");
        catalog.getDocumentations().add(documentation);

        document(catalog, "Cube with aggregate tables", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(productQuery, "Product Query", queryProductBody, 1, 3, 0, true, 2);
        document(productClassQuery, "Product Class Query", queryProductClassBody, 1, 4, 0, true, 2);
        document(joinQuery, "Product Class Query", queryJoinBody, 1, 5, 0, true, 2);
        document(level, "Level - Product Family", levelBody, 1, 6, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 7, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 8, 0, true, 0);
        document(cube, "Cube, MeasureGroup and Measure", cubeBody, 1, 9, 0, true, 2);
        return catalog;
    }

}
