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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.inlinetable;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the measure inline table tutorial.
 * Checks that the catalog, cube, and measure using inline table exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure-Sum");
        measureCheck.setDescription("Check that measure 'Measure-Sum' exists using inline table");
        measureCheck.setMeasureName("Measure-Sum");

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists with inline table");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure-Sum]");
        queryCheckCellValueCheck.setExpectedValue("100.5");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure-Sum data from inline table");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create catalog check with cube check
        // Note: No database schema check needed as this uses InlineTable with embedded data
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Inline Table");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Inline Table' exists with inline table data");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Inline Table");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Inline Table");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Inline Table");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Inline Table");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Inline Table with embedded data");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
