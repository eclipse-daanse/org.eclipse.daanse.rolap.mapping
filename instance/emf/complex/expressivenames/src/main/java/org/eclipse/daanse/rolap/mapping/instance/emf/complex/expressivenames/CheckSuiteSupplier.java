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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.expressivenames;

import java.util.List;

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
 * Provides a check suite for the ExpressiveNames complex mapping example.
 * Checks that the catalog with Cube1 and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "ExpressiveNames";

    // Cube name
    private static final String CUBE_1_NAME = "Cube1";

    // Measure name
    private static final String MEASURE_1 = "Measure1";

    // Dimension names
    private static final String DIMENSION_1 = "Dimension1";
    private static final String DIMENSION_2 = "Dimension2";
    private static final String DIMENSION_3 = "Dimension3";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckDimension1 = createDimensionCheck(DIMENSION_1,
                createHierarchyCheck("D1H1",
                        createLevelCheck("D1H1L1")));
        DimensionCheck dimCheckDimension2 = createDimensionCheck(DIMENSION_2,
                createHierarchyCheck("D2H1",
                        createLevelCheck("D2H1L1")),
                createHierarchyCheck("D2H2",
                        createLevelCheck("D2H2L1"),
                        createLevelCheck("D2H2L2")));
        DimensionCheck dimCheckDimension3 = createDimensionCheck(DIMENSION_3,
                createHierarchyCheck("D3H1",
                        createLevelCheck("D3H1L1")),
                createHierarchyCheck("D3H2",
                        createLevelCheck("D3H2L1"),
                        createLevelCheck("D3H2L2")),
                createHierarchyCheck("D3H3",
                        createLevelCheck("D3H3L1"),
                        createLevelCheck("D3H3L2"),
                        createLevelCheck("D3H3L3")));

        // Create measure check
        MeasureCheck measureCheck1 = createMeasureCheck(MEASURE_1);

        // Create cube check
        CubeCheck cubeCheckCube1 = factory.createCubeCheck();
        cubeCheckCube1.setName("CubeCheck-" + CUBE_1_NAME);
        cubeCheckCube1.setDescription("Check that cube '" + CUBE_1_NAME + "' exists");
        cubeCheckCube1.setCubeName(CUBE_1_NAME);
        cubeCheckCube1.getMeasureChecks().add(measureCheck1);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension1);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension2);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension3);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckCube1);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for ExpressiveNames mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("ExpressiveNames Example Suite");
        suite.setDescription("Check suite for the ExpressiveNames mapping example");
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
     * Creates a DimensionCheck with the specified name.
     *
     * @param dimensionName the name of the dimension
     * @param hierarchyChecks the hierarchy checks to add to the dimension check
     * @return the configured DimensionCheck
     */
    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        if (hierarchyChecks != null) {
            for (HierarchyCheck hierarchyCheck : hierarchyChecks) {
                dimensionCheck.getHierarchyChecks().add(hierarchyCheck);
            }
        }
        return dimensionCheck;
    }

    /**
     * Creates a HierarchyCheck with the specified name and level checks.
     *
     * @param hierarchyName the name of the hierarchy
     * @param levelChecks the level checks to add to the hierarchy check
     * @return the configured HierarchyCheck
     */
    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        if (levelChecks != null) {
            for (LevelCheck levelCheck : levelChecks) {
                hierarchyCheck.getLevelChecks().add(levelCheck);
            }
        }
        return hierarchyCheck;
    }

    /**
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }


}
