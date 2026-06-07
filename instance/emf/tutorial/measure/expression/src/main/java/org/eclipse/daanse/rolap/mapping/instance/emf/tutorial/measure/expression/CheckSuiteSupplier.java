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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.expression;

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
 * Provides a check suite for the measure expression tutorial.
 * Checks that the catalog, cube, measures with SQL expression columns, and database schema exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck measure1Check = factory.createMeasureCheck();
        measure1Check.setName("MeasureCheck-Measure1-Sum");
        measure1Check.setDescription("Check that measure 'Measure1-Sum' exists with SQL expression");
        measure1Check.setMeasureName("Measure1-Sum");

        MeasureCheck measure2Check = factory.createMeasureCheck();
        measure2Check.setName("MeasureCheck-Measure2-Sum");
        measure2Check.setDescription("Check that measure 'Measure2-Sum' exists with SQL expression");
        measure2Check.setMeasureName("Measure2-Sum");

        // Create cube check with measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube With MeasureExpression");
        cubeCheck.setDescription("Check that cube 'Cube With MeasureExpression' exists");
        cubeCheck.setCubeName("Cube With MeasureExpression");
        cubeCheck.getMeasureChecks().add(measure1Check);
        cubeCheck.getMeasureChecks().add(measure2Check);

        // Create query check for Measure1-Sum
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure1-Sum]");
        queryCheck1CellValueCheck.setExpectedValue("126.0");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure1 Query Check");
        queryCheck1.setDescription("Verify MDX query returns Measure1-Sum data");
        queryCheck1.setQuery("SELECT FROM [Cube With MeasureExpression] WHERE ([Measures].[Measure1-Sum])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(0);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        // Create query check for Measure2-Sum
        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Measure2-Sum]");
        queryCheck2CellValueCheck.setExpectedValue("71.0");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure2 Query Check");
        queryCheck2.setDescription("Verify MDX query returns Measure2-Sum data");
        queryCheck2.setQuery("SELECT FROM [Cube With MeasureExpression] WHERE ([Measures].[Measure2-Sum])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(0);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Expression");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Expression' exists with SQL expression-based measures");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Expression");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Expression");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Expression");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Expression");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Expression with SQL expression columns");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
