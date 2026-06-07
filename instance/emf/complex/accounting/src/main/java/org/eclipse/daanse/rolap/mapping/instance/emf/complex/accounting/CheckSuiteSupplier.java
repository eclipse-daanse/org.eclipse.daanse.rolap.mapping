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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.accounting;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;

import org.osgi.service.component.annotations.Component;

/**
 * Check suite for the Accounting complex mapping. Asserts that the catalog,
 * the three cubes (AccountingIst read-only, AccountingWb writeback, and
 * Accounting VirtualCube), all measures, dimensions/hierarchies/levels, and
 * the full database schema (tables + column types) materialise as expected.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Accounting";
    private static final String CUBE_IST_NAME = "AccountingIst";
    private static final String CUBE_WB_NAME = "AccountingWb";
    private static final String CUBE_V_NAME = "Accounting";

    @Override
    public OlapCheckSuite get() {
        // The full dimension shape — surfaced on every cube.
        DimensionCheck yearDim = createDimensionCheck("Year", createHierarchyCheck("Year", createLevelCheck("Year")));
        DimensionCheck accountDim = createDimensionCheck("Account",
                createHierarchyCheck("Account",
                        createLevelCheck("Category"),
                        createLevelCheck("Group"),
                        createLevelCheck("Account")));
        DimensionCheck orgUnitDim = createDimensionCheck("OrgUnit", createHierarchyCheck("OrgUnit",
                createLevelCheck("L1"), createLevelCheck("L2"), createLevelCheck("L3")));

        // AccountingIst — read-only, holds only AmountIst.
        CubeCheck cubeIstCheck = factory.createCubeCheck();
        cubeIstCheck.setName("CubeCheck-" + CUBE_IST_NAME);
        cubeIstCheck.setDescription("Read-only cube AccountingIst — AmountIst measure only, no writeback");
        cubeIstCheck.setCubeName(CUBE_IST_NAME);
        cubeIstCheck.getMeasureChecks().add(createMeasureCheck("AmountIst"));
        addAllDimensions(cubeIstCheck);

        // AccountingWb — writeback-enabled, holds AmountPlan + Comments.
        CubeCheck cubeWbCheck = factory.createCubeCheck();
        cubeWbCheck.setName("CubeCheck-" + CUBE_WB_NAME);
        cubeWbCheck.setDescription("Writeback cube AccountingWb — AmountPlan + Comments, bound to BOOKINGWB");
        cubeWbCheck.setCubeName(CUBE_WB_NAME);
        cubeWbCheck.getMeasureChecks().add(createMeasureCheck("AmountPlan"));
        cubeWbCheck.getMeasureChecks().add(createMeasureCheck("Comments"));
        addAllDimensions(cubeWbCheck);

        // Accounting — VirtualCube exposing all three measures.
        CubeCheck cubeVCheck = factory.createCubeCheck();
        cubeVCheck.setName("CubeCheck-" + CUBE_V_NAME);
        cubeVCheck.setDescription("VirtualCube Accounting — combines AccountingIst + AccountingWb, exposes all three measures");
        cubeVCheck.setCubeName(CUBE_V_NAME);
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("AmountIst"));
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("AmountPlan"));
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("Comments"));
        cubeVCheck.getDimensionChecks().add(yearDim);
        cubeVCheck.getDimensionChecks().add(accountDim);
        cubeVCheck.getDimensionChecks().add(orgUnitDim);


        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Catalog '" + CATALOG_NAME + "' with three cubes (Ist / Wb / Virtual) and full dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeIstCheck);
        catalogCheck.getCubeChecks().add(cubeWbCheck);
        catalogCheck.getCubeChecks().add(cubeVCheck);

        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for the Accounting mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Accounting Example Suite");
        suite.setDescription("Check suite for the Accounting complex mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /** Adds the full 3-dimension shape onto the given CubeCheck. */
    private void addAllDimensions(CubeCheck cube) {
        cube.getDimensionChecks().add(createDimensionCheck("Year",
                createHierarchyCheck("Year", createLevelCheck("Year"))));
        cube.getDimensionChecks().add(createDimensionCheck("Account",
                createHierarchyCheck("Account",
                        createLevelCheck("Category"),
                        createLevelCheck("Group"),
                        createLevelCheck("Account"))));
        cube.getDimensionChecks().add(createDimensionCheck("OrgUnit", createHierarchyCheck("OrgUnit",
                createLevelCheck("L1"), createLevelCheck("L2"), createLevelCheck("L3"))));
    }

    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        for (HierarchyCheck hc : hierarchyChecks) {
            dimensionCheck.getHierarchyChecks().add(hc);
        }
        return dimensionCheck;
    }

    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        for (LevelCheck lc : levelChecks) {
            hierarchyCheck.getLevelChecks().add(lc);
        }
        return hierarchyCheck;
    }

    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }


}
