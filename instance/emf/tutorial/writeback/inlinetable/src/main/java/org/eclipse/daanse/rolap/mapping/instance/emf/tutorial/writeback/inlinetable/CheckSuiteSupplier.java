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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.inlinetable;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the writeback inline table tutorial.
 * Checks that the catalog with cube C, its measures (Measure1, Measure2),
 * dimension D1 with hierarchy and levels, and writeback functionality exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Writeback Inline Table";

    // Cube name
    private static final String CUBE_NAME = "C";

    // Measure names
    private static final String MEASURE1 = "Measure1";
    private static final String MEASURE2 = "Measure2";

    // Dimension name
    private static final String DIMENSION_D1 = "D1";

    // Hierarchy name
    private static final String HIERARCHY_NAME = "HierarchyWithHasAll";

    // Level names
    private static final String LEVEL_L1 = "L1";
    private static final String LEVEL_L2 = "L2";

    @Override
    public OlapCheckSuite get() {
        // Create level checks
        LevelCheck levelCheckL1 = createLevelCheck(LEVEL_L1);
        LevelCheck levelCheckL2 = createLevelCheck(LEVEL_L2);

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for " + HIERARCHY_NAME);
        hierarchyCheck.setHierarchyName(HIERARCHY_NAME);
        hierarchyCheck.getLevelChecks().add(levelCheckL1);
        hierarchyCheck.getLevelChecks().add(levelCheckL2);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + DIMENSION_D1);
        dimensionCheck.setDimensionName(DIMENSION_D1);
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create measure checks
        MeasureCheck measureCheck1 = createMeasureCheck(MEASURE1);
        MeasureCheck measureCheck2 = createMeasureCheck(MEASURE2);

        // Create cube check for C
        CubeCheck cubeCheckC = factory.createCubeCheck();
        cubeCheckC.setName("CubeCheck-" + CUBE_NAME);
        cubeCheckC.setDescription("Check that cube '" + CUBE_NAME + "' exists");
        cubeCheckC.setCubeName(CUBE_NAME);
        cubeCheckC.getMeasureChecks().add(measureCheck1);
        cubeCheckC.getMeasureChecks().add(measureCheck2);
        cubeCheckC.getDimensionChecks().add(dimensionCheck);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with cube, measures, dimension, and writeback functionality");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckC);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for writeback inline table tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Writeback Inline Table Suite");
        suite.setDescription("Check suite for the writeback inline table tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates a MeasureCheck with the specified name.
     *
     * @param measureName the name of the measure
     * @return the configured MeasureCheck
     */
    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    /**
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck for " + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }


}
