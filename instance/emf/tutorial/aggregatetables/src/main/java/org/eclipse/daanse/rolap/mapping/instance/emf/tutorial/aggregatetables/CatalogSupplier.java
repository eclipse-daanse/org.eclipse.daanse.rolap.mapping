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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregatetables;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_28_Cube_with_Aggregate_tables";
    private static final String SALES = "Sales";
    private static final String SALES_FACT_1997 = "SALES_FACT_1997";

    private static final String schemaDocumentationTxt = """
            Aggregate tables are a way to improve Mondrian's performance when the fact table contains
            a huge number of rows: a million or more. An aggregate table is essentially a pre-computed
            summary of the data in the fact table.
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column productIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productIdColumn.setName("PRODUCT_ID");
        productIdColumn.setId("SALES_FACT_1997_PRODUCT_ID");
        productIdColumn.setType("INTEGER");

        Column storeCostColumn = RolapMappingFactory.eINSTANCE.createColumn();
        storeCostColumn.setName("STORE_COST");
        storeCostColumn.setId("SALES_FACT_1997_STORE_COST");
        storeCostColumn.setType("DECIMAL");
        storeCostColumn.setColumnSize(10);
        storeCostColumn.setDecimalDigits(4);

        PhysicalTable salesFact1997 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        salesFact1997.setName(SALES_FACT_1997);
        salesFact1997.setId(SALES_FACT_1997);
        salesFact1997.getColumns().addAll(List.of(productIdColumn, storeCostColumn));
        databaseSchema.getTables().add(salesFact1997);

        Column productProductClassIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productProductClassIdColumn.setId("PRODUCT_PRODUCT_CLASS_ID");
        productProductClassIdColumn.setType("INTEGER");

        Column productProductIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productProductIdColumn.setName("PRODUCT_ID");
        productProductIdColumn.setId("PRODUCT_PRODUCT_ID");
        productProductIdColumn.setType("INTEGER");

        Column productBrandNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productBrandNameColumn.setName("brand_name");
        productBrandNameColumn.setId("PRODUCT_brand_name");
        productBrandNameColumn.setType("VARCHAR");
        productBrandNameColumn.setColumnSize(60);

        Column productProductNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productProductNameColumn.setName("brand_name");
        productProductNameColumn.setId("PRODUCT_brand_name");
        productProductNameColumn.setType("VARCHAR");
        productProductNameColumn.setColumnSize(60);

        PhysicalTable productTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        productTable.setName("PRODUCT");
        productTable.setId("PRODUCT");
        productTable.getColumns().addAll(List.of(productProductClassIdColumn, productProductIdColumn,
                productBrandNameColumn, productProductNameColumn));
        databaseSchema.getTables().add(productTable);

        Column productClassProductClassIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productClassProductClassIdColumn.setName("PRODUCT_CLASS_ID");
        productClassProductClassIdColumn.setId("PRODUCT_CLASS_PRODUCT_CLASS_ID");
        productClassProductClassIdColumn.setType("INTEGER");

        Column productClassProductFamileColumn = RolapMappingFactory.eINSTANCE.createColumn();
        productClassProductFamileColumn.setName("PRODUCT_FAMILE");
        productClassProductFamileColumn.setId("PRODUCT_CLASS_PRODUCT_FAMILE");
        productClassProductFamileColumn.setType("VARCHAR");
        productClassProductFamileColumn.setColumnSize(60);

        PhysicalTable productClassTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        productClassTable.setName("PRODUCT_CLASS");
        productClassTable.setId("PRODUCT_CLASS");
        productClassTable.getColumns()
                .addAll(List.of(productClassProductClassIdColumn, productClassProductFamileColumn));
        databaseSchema.getTables().add(productClassTable);

        Column aggCSpecialSalesFact1997ProductIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997ProductIdColumn.setName("PRODUCT_ID");
        aggCSpecialSalesFact1997ProductIdColumn.setId("aggCSpecialSalesFact1997_PRODUCT_ID");
        aggCSpecialSalesFact1997ProductIdColumn.setType("INTEGER");

        Column aggCSpecialSalesFact1997StoreCostSumColumn = RolapMappingFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997StoreCostSumColumn.setName("STORE_COST_SUM");
        aggCSpecialSalesFact1997StoreCostSumColumn.setId("aggCSpecialSalesFact1997_STORE_COST_SUM");
        aggCSpecialSalesFact1997StoreCostSumColumn.setType("DECIMAL");
        aggCSpecialSalesFact1997StoreCostSumColumn.setColumnSize(10);
        aggCSpecialSalesFact1997StoreCostSumColumn.setDecimalDigits(4);

        Column aggCSpecialSalesFact1997FactCountColumn = RolapMappingFactory.eINSTANCE.createColumn();
        aggCSpecialSalesFact1997FactCountColumn.setName("FACT_COUNT");
        aggCSpecialSalesFact1997FactCountColumn.setId("aggCSpecialSalesFact1997_FACT_COUNT");
        aggCSpecialSalesFact1997FactCountColumn.setType("INTEGER");

        //PRODUCT_ID,STORE_COST_SUM,FACT_COUNT
        //INTEGER,DECIMAL(10.4),INTEGER

        PhysicalTable aggCSpecialSalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggCSpecialSalesFact1997Table.setName("AGG_C_SPECIAL_SALES_FACT_1997");
        aggCSpecialSalesFact1997Table.setId("AGG_C_SPECIAL_SALES_FACT_1997");
        aggCSpecialSalesFact1997Table.getColumns().addAll(List.of(aggCSpecialSalesFact1997ProductIdColumn,
                aggCSpecialSalesFact1997StoreCostSumColumn, aggCSpecialSalesFact1997FactCountColumn));
        databaseSchema.getTables().add(aggCSpecialSalesFact1997Table);

        PhysicalTable aggC14SalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggC14SalesFact1997Table.setName("AGG_C_14_SALES_FACT_1997");
        aggC14SalesFact1997Table.setId("AGG_C_14_SALES_FACT_1997");
        aggC14SalesFact1997Table.getColumns().addAll(List.of());
        databaseSchema.getTables().add(aggC14SalesFact1997Table);

        PhysicalTable aggLc100SalesFact1997Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        aggLc100SalesFact1997Table.setName("AGG_LC_100_SALES_FACT_1997");
        aggLc100SalesFact1997Table.setId("AGG_LC_100_SALES_FACT_1997");
        aggLc100SalesFact1997Table.getColumns().addAll(List.of());
        databaseSchema.getTables().add(aggLc100SalesFact1997Table);

        AggregationColumnName aggregationColumnName = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        aggregationColumnName.setColumn(aggCSpecialSalesFact1997FactCountColumn);

        AggregationMeasure aggregationMeasure = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        aggregationMeasure.setName("[Measures].[Store Cost]");
        aggregationMeasure.setColumn(aggCSpecialSalesFact1997StoreCostSumColumn);

        AggregationLevel aggregationLevel = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        aggregationLevel.setName("[Product].[Product Family]");
        aggregationLevel.setColumn(productClassProductFamileColumn);//??

        AggregationExclude aggregationExclude1 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude1.setName("AGG_C_14_SALES_FACT_1997");
        AggregationExclude aggregationExclude2 = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude2.setName("AGG_LC_100_SALES_FACT_1997");

        AggregationName aggregationName = RolapMappingFactory.eINSTANCE.createAggregationName();
        aggregationName.setName(aggCSpecialSalesFact1997Table);
        aggregationName.setAggregationFactCount(aggregationColumnName);
        aggregationName.getAggregationMeasures().add(aggregationMeasure);
        aggregationName.getAggregationLevels().add(aggregationLevel);

        TableQuery productQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        productQuery.setId("productQuery");
        productQuery.setTable(productTable);

        TableQuery productClassQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        productClassQuery.setId("productClassQuery");
        productClassQuery.setTable(productClassTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(salesFact1997);
        query.setId("salesFact1997Query");
        query.getAggregationTables().add(aggregationName);
        query.getAggregationExcludes().addAll(List.of(aggregationExclude1, aggregationExclude2));

        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setKey(productProductClassIdColumn);
        left.setQuery(productQuery);

        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        right.setKey(productClassProductClassIdColumn);
        right.setQuery(productClassQuery);

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setId("joinQuery");
        joinQuery.setLeft(left);
        joinQuery.setRight(right);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Store Cost");
        measure.setId("Store Cost");
        measure.setColumn(storeCostColumn);
        measure.setFormatString("#,###.00");

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Product Family");
        level.setId("Product_Family_Level");
        level.setTable(productClassTable);
        level.setColumn(productClassProductFamileColumn);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Product Family");
        hierarchy.setId("Product_Family_Hierarchy");
        hierarchy.setPrimaryKey(productProductIdColumn);
        hierarchy.setPrimaryKeyTable(productTable);
        hierarchy.setDisplayFolder("Details");
        hierarchy.setQuery(joinQuery);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Product");
        dimension.setId("ProductDimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Product");
        dimensionConnector.setForeignKey(productIdColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(SALES);
        cube.setId(SALES);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_Aggregate_tables");
        catalog.setDescription("Schema with aggregate tables");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema with aggregate tables");
        catalog.getDocumentations().add(documentation);
        return catalog;
    }

}
