/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.sqlview;

import org.eclipse.daanse.olap.check.model.check.AxisCheck;
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
 * Provides a check suite for the database SQL view tutorial.
 * Checks that the catalog and database schema with SQL view exist and are properly configured.
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

        // MDX QueryCheck — verify SUM of VALUE column is 63 (42 + 21).
        CellValueCheck sumCell = factory.createCellValueCheck();
        sumCell.setName("CellValue-MeasureSum-Total");
        sumCell.setExpectedNumericValue(63.0);
        sumCell.setTolerance(0.001);
        sumCell.getCoordinates().add(0);

        // AxisCheck — verify columns axis has 1 position (only the one measure)
        // whose first member is [Measures].[Measure-Sum].
        AxisCheck columnsAxis = factory.createAxisCheck();
        columnsAxis.setName("Axis-Columns");
        columnsAxis.setAxisIndex(0);
        columnsAxis.setExpectedPositionCount(1);
        columnsAxis.setExpectedFirstMemberUniqueName("[Measures].[" + CatalogSupplier.MEASURE_NAME + "]");

        QueryCheck mdxSum = factory.createQueryCheck();
        mdxSum.setName("MDX-MinimalCube-Sum-Total");
        mdxSum.setDescription("SUM(VALUE) across both rows should be 63 (42 + 21)");
        mdxSum.setQuery("SELECT [Measures].[" + CatalogSupplier.MEASURE_NAME
                + "] ON COLUMNS FROM [" + CatalogSupplier.CUBE_NAME + "]");
        mdxSum.setQueryLanguage(QueryLanguage.MDX);
        mdxSum.getCellChecks().add(sumCell);
        mdxSum.getAxisChecks().add(columnsAxis);

        // Create catalog check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Database SQL View Cube");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Database SQL View' exists with database schema");
        catalogCheck.setCatalogName("Daanse Tutorial - Database SQL View Cube");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(mdxSum);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Database SQL View Cube");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Database SQL View Cube");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Database SQL View Cube");
        suite.setDescription("Check suite for the Daanse Tutorial - Database SQL View Cube");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
