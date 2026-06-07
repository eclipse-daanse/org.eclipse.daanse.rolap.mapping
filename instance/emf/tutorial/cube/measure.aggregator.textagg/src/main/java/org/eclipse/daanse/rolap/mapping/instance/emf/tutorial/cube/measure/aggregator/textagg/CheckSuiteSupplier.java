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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.textagg;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureAttribute;
import org.eclipse.daanse.olap.check.model.check.MeasureAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the text aggregator cube mapping.
 * Checks that the catalog, cube, and text aggregation measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck sumOfValueMeasureCheck = factory.createMeasureCheck();
        sumOfValueMeasureCheck.setName("MeasureCheck-Sum of Value");
        sumOfValueMeasureCheck.setDescription("Check that measure 'Sum of Value' exists");
        sumOfValueMeasureCheck.setMeasureName("Sum of Value");

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);
        measureSumAttributeCheck.setAttributeType(MeasureAttribute.AGGREGATOR);
        //TODO
        //sumOfValueMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        MeasureCheck commentMeasureCheck = factory.createMeasureCheck();
        commentMeasureCheck.setName("MeasureCheck-Comment");
        commentMeasureCheck.setDescription("Check that measure 'Comment' exists");
        commentMeasureCheck.setMeasureName("Comment");

        // Create level checks for Town dimension
        LevelCheck levelContinentCheck = factory.createLevelCheck();
        levelContinentCheck.setName("LevelCheck for Continent");
        levelContinentCheck.setLevelName("Continent");

        LevelCheck levelCountryCheck = factory.createLevelCheck();
        levelCountryCheck.setName("LevelCheck for Country");
        levelCountryCheck.setLevelName("Country");

        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("LevelCheck for Town");
        levelTownCheck.setLevelName("Town");

        // Create hierarchy check for Town dimension
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getLevelChecks().add(levelContinentCheck);
        hierarchyTownCheck.getLevelChecks().add(levelCountryCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);

        // Create dimension check for Town
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);

        // Create level checks for Time dimension
        LevelCheck levelYearCheck = factory.createLevelCheck();
        levelYearCheck.setName("LevelCheck for Year");
        levelYearCheck.setLevelName("Year");

        LevelCheck levelMonthCheck = factory.createLevelCheck();
        levelMonthCheck.setName("LevelCheck for Month");
        levelMonthCheck.setLevelName("Month");

        // Create hierarchy check for Time dimension
        HierarchyCheck hierarchyTimeCheck = factory.createHierarchyCheck();
        hierarchyTimeCheck.setName("HierarchyCheck for TimeHierarchy");
        hierarchyTimeCheck.setHierarchyName("TimeHierarchy");
        hierarchyTimeCheck.getLevelChecks().add(levelYearCheck);
        hierarchyTimeCheck.getLevelChecks().add(levelMonthCheck);

        // Create dimension check for Time
        DimensionCheck dimensionTimeCheck = factory.createDimensionCheck();
        dimensionTimeCheck.setName("DimensionCheck for Time");
        dimensionTimeCheck.setDimensionName("Time");
        dimensionTimeCheck.getHierarchyChecks().add(hierarchyTimeCheck);

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresTextAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresTextAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresTextAggregatorsCube");
        cubeCheck.getMeasureChecks().add(sumOfValueMeasureCheck);
        cubeCheck.getMeasureChecks().add(commentMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);
        cubeCheck.getDimensionChecks().add(dimensionTimeCheck);

        // Create query checks for each measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Sum of Value]");
        queryCheck1CellValueCheck.setExpectedValue("1432.0");
        queryCheck1CellValueCheck.getCoordinates().add(0);
        queryCheck1CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Sum of Value");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Sum of Value");
        queryCheck1.setQuery("SELECT [Measures].[Sum of Value] ON COLUMNS FROM [MeasuresTextAggregatorsCube]");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Comment]");
        queryCheck2CellValueCheck.setExpectedValue("user1 : comment1, user2 : comment2, user3 : comment3, user4 : comment4, user5 : comment5");
        queryCheck2CellValueCheck.setCheckFormattedValue(true);
        queryCheck2CellValueCheck.getCoordinates().add(0);

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Comment");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Comment");
        queryCheck2.setQuery("SELECT [Measures].[Comment] ON COLUMNS FROM [MeasuresTextAggregatorsCube]");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Text Agg' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Text Agg");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Text Agg");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Text Agg");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Text Agg");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Text Agg");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
