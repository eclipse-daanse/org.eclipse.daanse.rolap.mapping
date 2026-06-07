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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.unvisiblereferencecubes;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the virtual cube with invisible reference cubes tutorial.
 * Checks that the catalog with two invisible physical cubes (Cube1, Cube2) and one visible virtual cube (Cube1Cube2),
 * their measures, and dimensions exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Virtual Cube Unvisible Reference Cubes";

    // Cube names
    private static final String CUBE1_NAME = "Cube1";
    private static final String CUBE2_NAME = "Cube2";
    private static final String VIRTUAL_CUBE_NAME = "Cube1Cube2";

    // Measure names
    private static final String MEASURE_CUBE1 = "MeasureCube1";
    private static final String MEASURE_CUBE2 = "MeasureCube2";

    // Dimension names
    private static final String DIMENSION_CUBE1 = "Cube1Dimension1";
    private static final String DIMENSION_CUBE2 = "Cube2Dimension1";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckCube1Dimension1 = createDimensionCheck(DIMENSION_CUBE1);
        DimensionCheck dimCheckCube2Dimension1 = createDimensionCheck(DIMENSION_CUBE2);

        // Create measure checks
        MeasureCheck measureCheckCube1 = createMeasureCheck(MEASURE_CUBE1);
        MeasureCheck measureCheckCube2 = createMeasureCheck(MEASURE_CUBE2);

        // Create cube check for Cube1 (invisible)
        CubeCheck cubeCheckCube1 = factory.createCubeCheck();
        cubeCheckCube1.setName("CubeCheck-" + CUBE1_NAME);
        cubeCheckCube1.setDescription("Check that cube '" + CUBE1_NAME + "' exists (invisible)");
        cubeCheckCube1.setCubeName(CUBE1_NAME);
        cubeCheckCube1.getMeasureChecks().add(measureCheckCube1);
        cubeCheckCube1.getDimensionChecks().add(dimCheckCube1Dimension1);

        // Create cube check for Cube2 (invisible)
        CubeCheck cubeCheckCube2 = factory.createCubeCheck();
        cubeCheckCube2.setName("CubeCheck-" + CUBE2_NAME);
        cubeCheckCube2.setDescription("Check that cube '" + CUBE2_NAME + "' exists (invisible)");
        cubeCheckCube2.setCubeName(CUBE2_NAME);
        cubeCheckCube2.getMeasureChecks().add(measureCheckCube2);
        cubeCheckCube2.getDimensionChecks().add(dimCheckCube2Dimension1);

        // Create cube check for Virtual Cube
        CubeCheck cubeCheckVirtualCube = factory.createCubeCheck();
        cubeCheckVirtualCube.setName("CubeCheck-" + VIRTUAL_CUBE_NAME);
        cubeCheckVirtualCube.setDescription("Check that virtual cube '" + VIRTUAL_CUBE_NAME + "' exists");
        cubeCheckVirtualCube.setCubeName(VIRTUAL_CUBE_NAME);
        cubeCheckVirtualCube.getMeasureChecks().add(measureCheckCube1);
        cubeCheckVirtualCube.getMeasureChecks().add(measureCheckCube2);
        cubeCheckVirtualCube.getDimensionChecks().add(dimCheckCube1Dimension1);
        cubeCheckVirtualCube.getDimensionChecks().add(dimCheckCube2Dimension1);


        // Create catalog check with all cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes, measures, and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckCube1);
        catalogCheck.getCubeChecks().add(cubeCheckCube2);
        catalogCheck.getCubeChecks().add(cubeCheckVirtualCube);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for virtual cube with invisible reference cubes tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Virtual Cube Unvisible Reference Cubes Suite");
        suite.setDescription("Check suite for the virtual cube with invisible reference cubes tutorial");
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
     * @return the configured DimensionCheck
     */
    private DimensionCheck createDimensionCheck(String dimensionName) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        return dimensionCheck;
    }


}
