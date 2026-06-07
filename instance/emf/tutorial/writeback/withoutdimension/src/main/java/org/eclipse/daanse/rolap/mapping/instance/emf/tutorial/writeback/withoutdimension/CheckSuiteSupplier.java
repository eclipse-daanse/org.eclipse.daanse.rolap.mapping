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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.withoutdimension;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the writeback without dimension tutorial.
 * Checks that the catalog with cube C, its measures (Measure1, Measure2),
 * and writeback functionality exist and are accessible.
 * This tutorial demonstrates writeback without dimension constraints.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Writeback Without Dimension";

    // Cube name
    private static final String CUBE_NAME = "C";

    // Measure names
    private static final String MEASURE1 = "Measure1";
    private static final String MEASURE2 = "Measure2";

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck measureCheck1 = createMeasureCheck(MEASURE1);
        MeasureCheck measureCheck2 = createMeasureCheck(MEASURE2);

        // Create cube check for C (without dimensions)
        CubeCheck cubeCheckC = factory.createCubeCheck();
        cubeCheckC.setName("CubeCheck-" + CUBE_NAME);
        cubeCheckC.setDescription("Check that cube '" + CUBE_NAME + "' exists without dimensions");
        cubeCheckC.setCubeName(CUBE_NAME);
        cubeCheckC.getMeasureChecks().add(measureCheck1);
        cubeCheckC.getMeasureChecks().add(measureCheck2);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with cube, measures, and writeback functionality");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckC);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for writeback without dimension tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Writeback Without Dimension Suite");
        suite.setDescription("Check suite for the writeback without dimension tutorial");
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


}
