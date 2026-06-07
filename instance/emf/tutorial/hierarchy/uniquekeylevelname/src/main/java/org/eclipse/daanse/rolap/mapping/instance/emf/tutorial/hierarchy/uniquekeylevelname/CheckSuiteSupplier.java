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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.uniquekeylevelname;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
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
 * Provides a check suite for the unique key level name hierarchy tutorial.
 * Checks that the catalog, cube, measure, and hierarchy with unique key level name optimization exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure");
        measureCheck.setDescription("Check that measure 'Measure' exists");
        measureCheck.setMeasureName("Measure");

        // Create level checks for Automotive dimension
        LevelCheck levelMakeCheck = factory.createLevelCheck();
        levelMakeCheck.setName("LevelCheck for Make");
        levelMakeCheck.setLevelName("Make");

        LevelCheck levelModelCheck = factory.createLevelCheck();
        levelModelCheck.setName("LevelCheck for Model");
        levelModelCheck.setLevelName("Model");

        LevelCheck levelManufacturingPlantCheck = factory.createLevelCheck();
        levelManufacturingPlantCheck.setName("LevelCheck for ManufacturingPlant");
        levelManufacturingPlantCheck.setLevelName("ManufacturingPlant");

        LevelCheck levelVehicleCheck = factory.createLevelCheck();
        levelVehicleCheck.setName("LevelCheck for Vehicle Identification Number");
        levelVehicleCheck.setLevelName("Vehicle Identification Number");

        LevelCheck levelLicensePlateCheck = factory.createLevelCheck();
        levelLicensePlateCheck.setName("LevelCheck for LicensePlateNum");
        levelLicensePlateCheck.setLevelName("LicensePlateNum");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check for Automotive dimension
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Automotive");
        hierarchyCheck.setHierarchyName("Automotive");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(levelMakeCheck);
        hierarchyCheck.getLevelChecks().add(levelModelCheck);
        hierarchyCheck.getLevelChecks().add(levelManufacturingPlantCheck);
        hierarchyCheck.getLevelChecks().add(levelVehicleCheck);
        hierarchyCheck.getLevelChecks().add(levelLicensePlateCheck);

        // Create dimension check for Automotive
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Automotive");
        dimensionCheck.setDimensionName("Automotive");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure]");
        queryCheckCellValueCheck.setExpectedValue("225000.0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Unique Key Level Name");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Unique Key Level Name' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Unique Key Level Name");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Unique Key Level Name");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Unique Key Level Name");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Unique Key Level Name");
        suite.setDescription("Check suite for the Daanse Tutorial - Unique Key Level Name");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
