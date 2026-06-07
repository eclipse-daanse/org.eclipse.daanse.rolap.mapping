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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.property.geo;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the member property geo tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, level with geographic member properties, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-TotalValue");
        measureCheck.setDescription("Check that measure 'TotalValue' exists");
        measureCheck.setMeasureName("TotalValue");

        // Create level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-Location");
        levelCheck.setDescription("Check that level 'Location' exists with geographic member properties");
        levelCheck.setLevelName("Location");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-LocationHierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'LocationHierarchy' exists with location level");
        hierarchyCheck.setHierarchyName("LocationHierarchy");
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Location");
        dimensionCheck.setDescription("Check that dimension 'Location' exists with hierarchy");
        dimensionCheck.setDimensionName("Location");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Geographic Analysis");
        cubeCheck.setDescription("Check that cube 'Geographic Analysis' exists");
        cubeCheck.setCubeName("Geographic Analysis");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[TotalValue]");
        queryCheckCellValueCheck.setExpectedValue("2309.0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns TotalValue data");
        queryCheck.setQuery("SELECT FROM [Geographic Analysis] WHERE ([Measures].[TotalValue])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Member Properties with Geographic Data");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Member Properties with Geographic Data' exists with geographic member properties");
        catalogCheck.setCatalogName("Daanse Tutorial - Member Properties with Geographic Data");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Member Properties with Geographic Data");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Member Properties with Geographic Data");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Member Properties with Geographic Data");
        suite.setDescription("Check suite for the Daanse Tutorial - Member Properties with Geographic Data");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
