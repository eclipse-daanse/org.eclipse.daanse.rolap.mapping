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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.measure.inlinetablewithphysical;

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
 * Provides a check suite for the measure inline table with physical tutorial.
 * Checks that the catalog, cube, measure, dimension, hierarchy, levels combining inline table and physical table exist and are accessible.
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

        // Create level checks
        LevelCheck levelCountryCheck = factory.createLevelCheck();
        levelCountryCheck.setName("LevelCheck-Country");
        levelCountryCheck.setDescription("Check that level 'Country' exists using inline table");
        levelCountryCheck.setLevelName("Country");

        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("LevelCheck-Town");
        levelTownCheck.setDescription("Check that level 'Town' exists using physical table");
        levelTownCheck.setLevelName("Town");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-Hierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'Hierarchy' exists with country and town levels");
        hierarchyCheck.setHierarchyName("Hierarchy");
        hierarchyCheck.getLevelChecks().add(levelCountryCheck);
        hierarchyCheck.getLevelChecks().add(levelTownCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Dimension");
        dimensionCheck.setDescription("Check that dimension 'Dimension' exists with hierarchy");
        dimensionCheck.setDimensionName("Dimension");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-CubeTwoLevelsInlineAndPhysicalTable");
        cubeCheck.setDescription("Check that cube 'CubeTwoLevelsInlineAndPhysicalTable' exists");
        cubeCheck.setCubeName("CubeTwoLevelsInlineAndPhysicalTable");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure-Sum]");
        queryCheckCellValueCheck.setExpectedValue("601.5");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure-Sum data from inline table (sum of 100.5 + 200.5 + 300.5)");
        queryCheck.setQuery("SELECT FROM [CubeTwoLevelsInlineAndPhysicalTable] WHERE ([Measures].[Measure-Sum])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Inline Table With Physical");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Inline Table With Physical' exists with combined inline and physical tables");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Inline Table With Physical");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Inline Table With Physical");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Inline Table With Physical");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Inline Table With Physical");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Inline Table With Physical combining inline and physical tables");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
