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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.minimal;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
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
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + CatalogSupplier.MEASURE_NAME);
        measureCheck.setDescription("Check that measure '" + CatalogSupplier.MEASURE_NAME + "' exists");
        measureCheck.setMeasureName(CatalogSupplier.MEASURE_NAME);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CatalogSupplier.CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CatalogSupplier.CUBE_NAME + "' exists with its measures");
        cubeCheck.setCubeName(CatalogSupplier.CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("CatalogCheck-" + CatalogSupplier.CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CatalogSupplier.CATALOG_NAME + "' exists with its cubes");
        catalogCheck.setCatalogName(CatalogSupplier.CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("MinimalCubeConnectionCheck");
        connectionCheck.setDescription("Connection check for minimal cube tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("MinimalCubeSuite");
        suite.setDescription("Check suite for the minimal cube mapping tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
