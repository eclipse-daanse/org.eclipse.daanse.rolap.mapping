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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.timedimension;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
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
 * Provides a check suite for the time dimension tutorial.
 * Checks that the catalog, cube, and time dimension with temporal levels exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {

        //TODO fix aggregates check executer
        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setAttributeType(MeasureAttribute.AGGREGATOR);
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setAttributeType(MeasureAttribute.NAME);
        measureSumAttributeCheck2.setExpectedValue("Measure-Sum");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        measureSumAttributeCheck3.setExpectedValue("[Measures].[Measure-Sum]");

        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure-Sum");
        measureCheck.setDescription("Check that measure 'Measure-Sum' exists");
        measureCheck.setMeasureName("Measure-Sum");
        measureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck2);
        measureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck3);

        // Create level checks for Time dimension
        LevelCheck levelYearsCheck = factory.createLevelCheck();
        levelYearsCheck.setName("LevelCheck for Years");
        levelYearsCheck.setLevelName("Years");

        LevelCheck levelQuartersCheck = factory.createLevelCheck();
        levelQuartersCheck.setName("LevelCheck for Quarters");
        levelQuartersCheck.setLevelName("Quarters");

        LevelCheck levelMonthsCheck = factory.createLevelCheck();
        levelMonthsCheck.setName("LevelCheck for Months");
        levelMonthsCheck.setLevelName("Months");

        LevelCheck levelWeekCheck = factory.createLevelCheck();
        levelWeekCheck.setName("LevelCheck for Week");
        levelWeekCheck.setLevelName("Week");

        LevelCheck levelDayCheck = factory.createLevelCheck();
        levelDayCheck.setName("LevelCheck for Day");
        levelDayCheck.setLevelName("Day");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        HierarchyAttributeCheck hierarchyAllMemberNameCheck = factory.createHierarchyAttributeCheck();
        hierarchyAllMemberNameCheck.setAttributeType(HierarchyAttribute.ALL_MEMBER_NAME);
        hierarchyAllMemberNameCheck.setExpectedValue("All Years");

        // Create hierarchy check for Time dimension
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Time");
        hierarchyCheck.setHierarchyName("Time");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAllMemberNameCheck);
        hierarchyCheck.getLevelChecks().add(levelYearsCheck);
        hierarchyCheck.getLevelChecks().add(levelQuartersCheck);
        hierarchyCheck.getLevelChecks().add(levelMonthsCheck);
        hierarchyCheck.getLevelChecks().add(levelWeekCheck);
        hierarchyCheck.getLevelChecks().add(levelDayCheck);

        // Create dimension check for Time
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Time");
        dimensionCheck.setDimensionName("Time");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-CubeTimeDimension");
        cubeCheck.setDescription("Check that cube 'CubeTimeDimension' exists");
        cubeCheck.setCubeName("CubeTimeDimension");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure-Sum]");
        queryCheckCellValueCheck.setExpectedValue("94.0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [CubeTimeDimension] WHERE ([Measures].[Measure-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Dimension Time Dimension");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Dimension Time Dimension' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Dimension Time Dimension");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Dimension Time Dimension");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Dimension Time Dimension");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Dimension Time Dimension");
        suite.setDescription("Check suite for the Daanse Tutorial - Dimension Time Dimension");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
