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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregation.aggregatetables;

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
 * Provides a check suite for the aggregation aggregate tables tutorial.
 * Checks that the catalog, cube, measure, and aggregation tables configuration exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Store Cost");
        measureCheck.setDescription("Check that measure 'Store Cost' exists");
        measureCheck.setMeasureName("Store Cost");

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Sales");
        cubeCheck.setDescription("Check that cube 'Sales' exists");
        cubeCheck.setCubeName("Sales");
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Store Cost]");
        queryCheckCellValueCheck.setExpectedValue("1001.0");
        queryCheckCellValueCheck.setExpectedNumericValue(1001);


        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Store Cost Query Check");
        queryCheck.setDescription("Verify MDX query returns Store Cost data");
        queryCheck.setQuery("SELECT FROM [Sales] WHERE ([Measures].[Store Cost])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Aggregation Aggregate Tables' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Aggregation Aggregate Tables");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Aggregation Aggregate Tables");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Aggregation Aggregate Tables");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Aggregation Aggregate Tables");
        suite.setDescription("Check suite for the Daanse Tutorial - Aggregation Aggregate Tables");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
