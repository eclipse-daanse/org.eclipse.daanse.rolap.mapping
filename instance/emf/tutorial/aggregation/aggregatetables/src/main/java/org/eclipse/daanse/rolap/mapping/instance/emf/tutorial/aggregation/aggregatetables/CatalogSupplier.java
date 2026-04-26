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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationName;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.AggregationFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.08.02", source = Source.EMF, group = "Aggregation") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private TableSource productClassQuery;
    private TableSource productQuery;
    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private JoinSource joinQuery;
    private Schema databaseSchema;
    private Catalog catalog;
    private TableSource query;
    private Level level;


    private static final String SALES = "Sales";
    private static final String SALES_FACT_1997 = "SALES_FACT_1997";

    private static final String catalogBody = """
            This tutorial discusses TableSource with AggregationExclude.
            AggregationExclude defines exclusion rules that prevent specific tables from being used as aggregation tables,
            even if they would otherwise match aggregation patterns or be considered suitable for aggregation optimization.
            AggregationExclude is essential for maintaining aggregation accuracy and system reliability by providing explicit
            control over which tables should be avoided during aggregation table discovery and selection.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on

            - `SALES_FACT_1997 `table which contains two columns: `PRODUCT_ID` and `STORE_COST`.<br />
            - `PRODUCT` table which contains 4 columns: `PRODUCT_CLASS_ID`,`PRODUCT_ID`,`brand_name`,`product_name`<br />
            - `PRODUCT_CLASS` table which contains 3 columns: `PRODUCT_CLASS_ID`, `PRODUCT_ID` and `brand_name`.<br />
            - `AGG_C_SPECIAL_SALES_FACT_1997` table which contains 3 columns: `PRODUCT_ID`, `STORE_COST_SUM`, `FACT_COUNT`;<br />
            - `AGG_C_14_SALES_FACT_1997` this is exclude table<br />
            - `AGG_LC_100_SALES_FACT_1997` this is exclude table<br />
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery, as it directly references the physical table `SALES_FACT_1997`.
            The query element is not visible to users accessing the cube through the XMLA API, such as Daanse Dashboard, Power BI, or Excel.
            this TableSource have one AggregationTable with reference to 'AGG_C_SPECIAL_SALES_FACT_1997' the specific database table that contains the pre-computed aggregation data.
            this tabele will use for calculate aggregation data for aggregationMeasure [Measures].[Store Cost] for level [Product].[Product Family].[Product Family].
            """;

    private static final String queryProductBody = """
            The TableSource for the PRODUCT table.
            """;

    private static final String queryProductClassBody = """
            The TableSource for the PRODUCT_CLASS table.
            """;

    private static final String queryJoinBody = """
            The JoinSource specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

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
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column productIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productIdColumn.setName("PRODUCT_ID");
        productIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column storeCostColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        storeCostColumn.setName("STORE_COST");
        storeCostColumn.setType(SqlSimpleTypes.decimalType(18, 4));

        Table salesFact1997 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        salesFact1997.setName(SALES_FACT_1997);
        salesFact1997.getFeature().addAll(List.of(productIdColumn, storeCostColumn));
        databaseSchema.getOwnedElement().add(salesFact1997);

        Column productProductClassIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productProductClassIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column productProductIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productProductIdColumn.setName("PRODUCT_ID");
        productProductIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column productBrandNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productBrandNameColumn.setName("brand_name");
        productBrandNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column productProductNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productProductNameColumn.setName("product_name");
        productProductNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table productTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        productTable.setName("PRODUCT");
        productTable.getFeature().addAll(List.of(productProductClassIdColumn, productProductIdColumn,
                productBrandNameColumn, productProductNameColumn));
        databaseSchema.getOwnedElement().add(productTable);

        Column productClassProductClassIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productClassProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productClassProductClassIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column productClassProductFamileColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        productClassProductFamileColumn.setName("PRODUCT_FAMILE");
        productClassProductFamileColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table productClassTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        productClassTable.setName("PRODUCT_CLASS");
        productClassTable.getFeature()
                .addAll(List.of(productClassProductClassIdColumn, productClassProductFamileColumn));
        databaseSchema.getOwnedElement().add(productClassTable);

        Column aggCSpecialSalesFact1997ProductIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997ProductIdColumn.setName("PRODUCT_ID");
        aggCSpecialSalesFact1997ProductIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column aggCSpecialSalesFact1997StoreCostSumColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997StoreCostSumColumn.setName("STORE_COST_SUM");
        aggCSpecialSalesFact1997StoreCostSumColumn.setType(SqlSimpleTypes.decimalType(18, 4));

        Column aggCSpecialSalesFact1997FactCountColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997FactCountColumn.setName("FACT_COUNT");
        aggCSpecialSalesFact1997FactCountColumn.setType(SqlSimpleTypes.Sql99.integerType());

        //PRODUCT_ID,STORE_COST_SUM,FACT_COUNT
        //INTEGER,DECIMAL(10.4),INTEGER

        Table aggCSpecialSalesFact1997Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        aggCSpecialSalesFact1997Table.setName("AGG_C_SPECIAL_SALES_FACT_1997");
        aggCSpecialSalesFact1997Table.getFeature().addAll(List.of(aggCSpecialSalesFact1997ProductIdColumn,
                aggCSpecialSalesFact1997StoreCostSumColumn, aggCSpecialSalesFact1997FactCountColumn));
        databaseSchema.getOwnedElement().add(aggCSpecialSalesFact1997Table);

        Table aggC14SalesFact1997Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        aggC14SalesFact1997Table.setName("AGG_C_14_SALES_FACT_1997");
        aggC14SalesFact1997Table.getFeature().addAll(List.of());
        databaseSchema.getOwnedElement().add(aggC14SalesFact1997Table);

        Table aggLc100SalesFact1997Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        aggLc100SalesFact1997Table.setName("AGG_LC_100_SALES_FACT_1997");
        aggLc100SalesFact1997Table.getFeature().addAll(List.of());
        databaseSchema.getOwnedElement().add(aggLc100SalesFact1997Table);

        AggregationColumnName aggregationColumnName = AggregationFactory.eINSTANCE.createAggregationColumnName();
        aggregationColumnName.setColumn(aggCSpecialSalesFact1997FactCountColumn);

        AggregationMeasure aggregationMeasure = AggregationFactory.eINSTANCE.createAggregationMeasure();
        aggregationMeasure.setName("[Measures].[Store Cost]");
        aggregationMeasure.setColumn(aggCSpecialSalesFact1997StoreCostSumColumn);

        AggregationLevel aggregationLevel = AggregationFactory.eINSTANCE.createAggregationLevel();
        aggregationLevel.setName("[Product].[Product Family].[Product Family]");
        aggregationLevel.setColumn(productClassProductFamileColumn);//??

        AggregationExclude aggregationExclude1 = AggregationFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude1.setName("AGG_C_14_SALES_FACT_1997");
        AggregationExclude aggregationExclude2 = AggregationFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude2.setName("AGG_LC_100_SALES_FACT_1997");

        AggregationName aggregationName = AggregationFactory.eINSTANCE.createAggregationName();
        aggregationName.setName(aggCSpecialSalesFact1997Table);
        aggregationName.setAggregationFactCount(aggregationColumnName);
        aggregationName.getAggregationMeasures().add(aggregationMeasure);
        aggregationName.getAggregationLevels().add(aggregationLevel);

        productQuery = SourceFactory.eINSTANCE.createTableSource();
        productQuery.setTable(productTable);

        productClassQuery = SourceFactory.eINSTANCE.createTableSource();
        productClassQuery.setTable(productClassTable);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(salesFact1997);
        query.getAggregationTables().add(aggregationName);
        query.getAggregationExcludes().addAll(List.of(aggregationExclude1, aggregationExclude2));

        JoinedQueryElement left = SourceFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(productProductClassIdColumn);
        left.setQuery(productQuery);

        JoinedQueryElement right = SourceFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(productClassProductClassIdColumn);
        right.setQuery(productClassQuery);

        joinQuery = SourceFactory.eINSTANCE.createJoinSource();
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Store Cost");
        measure.setColumn(storeCostColumn);
        measure.setFormatString("#,###.00");

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Product Family");
        level.setColumn(productClassProductFamileColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Product Family");
        hierarchy.setPrimaryKey(productProductIdColumn);
        hierarchy.setDisplayFolder("Details");
        hierarchy.setQuery(joinQuery);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Product");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Product");
        dimensionConnector.setForeignKey(productIdColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(SALES);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalog.setDescription("Aggregate table optimization techniques");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);

        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Aggregation Aggregate Tables", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Product Query", queryProductBody, 1, 3, 0, productQuery, 2),
                        new DocSection("Product Class Query", queryProductClassBody, 1, 4, 0, productClassQuery, 2),
                        new DocSection("Product Class Query", queryJoinBody, 1, 5, 0, joinQuery, 2),
                        new DocSection("Level - Product Family", levelBody, 1, 6, 0, level, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 7, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 8, 0, dimension, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
