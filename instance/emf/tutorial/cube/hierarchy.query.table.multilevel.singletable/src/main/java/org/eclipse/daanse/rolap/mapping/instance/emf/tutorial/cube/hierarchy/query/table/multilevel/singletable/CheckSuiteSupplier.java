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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.table.multilevel.singletable;

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
 * Provides a check suite for the minimal cube mapping.
 * Checks that the catalog, cube, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck theMeasureCheck = factory.createMeasureCheck();
        theMeasureCheck.setName("MeasureCheck-theMeasure");
        theMeasureCheck.setDescription("Check that measure 'theMeasure' exists");
        theMeasureCheck.setMeasureName("theMeasure");

        //TODO fix aggregates check executer
        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setAttributeType(MeasureAttribute.AGGREGATOR);
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setAttributeType(MeasureAttribute.NAME);
        measureSumAttributeCheck2.setExpectedValue("theMeasure");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        measureSumAttributeCheck3.setExpectedValue("[Measures].[theMeasure]");

        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck2);
        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck3);

        // Create Level check
        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("levelCheck for Town");
        levelTownCheck.setLevelName("Town");

        LevelCheck levelCountryCheck = factory.createLevelCheck();
        levelCountryCheck.setName("levelCheck for Country");
        levelCountryCheck.setLevelName("Country");

        HierarchyAttributeCheck hierarchyTownHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyTownHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyTownHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getHierarchyAttributeChecks().add(hierarchyTownHasAllAttributeCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);
        hierarchyTownCheck.getLevelChecks().add(levelCountryCheck);

        // Create dimension check
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube Query linked Tables");
        cubeCheck.setDescription("Check that cube 'Cube Query linked Tables' exists");
        cubeCheck.setCubeName("Cube Query linked Tables");
        cubeCheck.getMeasureChecks().add(theMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[theMeasure]");
        queryCheck1CellValueCheck.setExpectedValue("378.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(378);
        queryCheck1CellValueCheck.getCoordinates().add(0);
        queryCheck1CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check theMeasure");
        queryCheck1.setDescription("Verify MDX query returns Measure data for theMeasure");
        queryCheck1.setQuery("SELECT [Measures].[theMeasure] ON COLUMNS FROM [Cube Query linked Tables]");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Hierarchy Query Table Multilevel Singletable' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        suite.setDescription("Check suite for the Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
