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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.namedset;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.NamedSetAttribute;
import org.eclipse.daanse.olap.check.model.check.NamedSetAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.NamedSetCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the namedset tutorial mapping.
 * Checks that the catalog, cube, measure, and named sets exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Namedset All";
    private static final String CUBE_NAME = "Cube";
    private static final String MEASURE_NAME = "Measure1";

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + MEASURE_NAME);
        measureCheck.setDescription("Check that measure '" + MEASURE_NAME + "' exists");
        measureCheck.setMeasureName(MEASURE_NAME);

        // Create named set checks
        NamedSetCheck namedSetCheck1 = createNamedSetCheck(
            "NsWithFolderDimension1",
            "Check NamedSet with folder using Dimension1",
            "TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])",
            "Folder1"
        );

        NamedSetCheck namedSetCheck2 = createNamedSetCheck(
            "NsWithoutFolderDimension1",
            "Check NamedSet without folder using Dimension1",
            "TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])",
            null
        );

        NamedSetCheck namedSetCheck3 = createNamedSetCheck(
            "NSInCubeWithFolder",
            "Check NamedSet with folder in Cube level",
            "{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}",
            "Folder2"
        );

        NamedSetCheck namedSetCheck4 = createNamedSetCheck(
            "NSInCubeWithoutFolder",
            "Check NamedSet without folder in Cube level",
            "{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}",
            null
        );

        // Create cube check with measure and named set checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists with its measures and named sets");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getNamedSetChecks().add(namedSetCheck1);
        cubeCheck.getNamedSetChecks().add(namedSetCheck2);
        cubeCheck.getNamedSetChecks().add(namedSetCheck3);
        cubeCheck.getNamedSetChecks().add(namedSetCheck4);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("CatalogCheck-" + CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with its cubes");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("NamedsetConnectionCheck");
        connectionCheck.setDescription("Connection check for namedset tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("NamedsetSuite");
        suite.setDescription("Check suite for the namedset mapping tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates a NamedSetCheck with the specified properties.
     *
     * @param name the name of the named set
     * @param description the description of the check
     * @param expression the expected MDX expression/formula
     * @param displayFolder the expected display folder (can be null)
     * @return the configured NamedSetCheck
     */
    private NamedSetCheck createNamedSetCheck(String name, String description, String expression, String displayFolder) {
        NamedSetCheck namedSetCheck = factory.createNamedSetCheck();
        namedSetCheck.setName("NamedSetCheck-" + name);
        namedSetCheck.setDescription(description);
        namedSetCheck.setNamedSetName(name);

        // Check expression/formula
        NamedSetAttributeCheck expressionCheck = factory.createNamedSetAttributeCheck();
        expressionCheck.setAttributeType(NamedSetAttribute.EXPRESSION);
        expressionCheck.setExpectedValue(expression);
        namedSetCheck.getNamedSetAttributeChecks().add(expressionCheck);

        // Check display folder if specified
        if (displayFolder != null) {
            NamedSetAttributeCheck displayFolderCheck = factory.createNamedSetAttributeCheck();
            displayFolderCheck.setAttributeType(NamedSetAttribute.DISPLAY_FOLDER);
            displayFolderCheck.setExpectedValue(displayFolder);
            namedSetCheck.getNamedSetAttributeChecks().add(displayFolderCheck);
        }

        return namedSetCheck;
    }
}
