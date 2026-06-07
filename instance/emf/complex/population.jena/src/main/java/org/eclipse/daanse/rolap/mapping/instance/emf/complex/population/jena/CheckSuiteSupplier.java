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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.population.jena;

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
 * Provides a check suite for the Bevölkerung (Population) Jena complex mapping example.
 * Checks that the catalog with Bevölkerung cube and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Bevölkerung";

    // Cube name
    private static final String CUBE_BEVOELKERUNG = "Bevölkerung";

    // Measure name
    private static final String MEASURE_EINWOHNERZAHL = "Einwohnerzahl";

    // Dimension names
    private static final String DIM_JAHR = "Jahr";
    private static final String DIM_STATISTISCHER_BEZIRK = "statistischer Bezirk";
    private static final String DIM_GESCHLECHT = "Geschlecht";
    private static final String DIM_ALTER = "Alter";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckJahr = createDimensionCheck(DIM_JAHR,
                createHierarchyCheck("Jahr",
                        createLevelCheck("Jahr")));
        DimensionCheck dimCheckStatistischerBezirk = createDimensionCheck(DIM_STATISTISCHER_BEZIRK,
                createHierarchyCheck("Stadt - Planungsraum - statistischer Bezirk",
                        createLevelCheck("Stadt"),
                        createLevelCheck("Planungsraum"),
                        createLevelCheck("Statistischer Bezirk")));
        DimensionCheck dimCheckGeschlecht = createDimensionCheck(DIM_GESCHLECHT,
                createHierarchyCheck("Geschlecht (m/w/d)",
                        createLevelCheck("Geschlecht")));
        DimensionCheck dimCheckAlter = createDimensionCheck(DIM_ALTER,
                createHierarchyCheck("Alter (Einzeljahrgänge)",
                        createLevelCheck("Alter")),
                createHierarchyCheck("Altersgruppen (Standard)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter Standard")),
                createHierarchyCheck("Altersgruppen (Kinder)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter Kinder")),
                createHierarchyCheck("Altersgruppen (Systematik RKI H7)",
                        createLevelCheck("Alter H7")),
                createHierarchyCheck("Altersgruppen (Systematik RKI H8)",
                        createLevelCheck("Alter H8")),
                createHierarchyCheck("Altersgruppen (10-Jahres-Gruppen)",
                        createLevelCheck("Alter 10")));

        // Create measure check
        MeasureCheck measureCheckEinwohnerzahl = createMeasureCheck(MEASURE_EINWOHNERZAHL);

        // Create cube check for Bevölkerung
        CubeCheck cubeCheckBevoelkerung = factory.createCubeCheck();
        cubeCheckBevoelkerung.setName("CubeCheck-" + CUBE_BEVOELKERUNG);
        cubeCheckBevoelkerung.setDescription("Check that cube '" + CUBE_BEVOELKERUNG + "' exists");
        cubeCheckBevoelkerung.setCubeName(CUBE_BEVOELKERUNG);
        cubeCheckBevoelkerung.getMeasureChecks().add(measureCheckEinwohnerzahl);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckJahr);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckStatistischerBezirk);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckGeschlecht);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckAlter);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckBevoelkerung);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for Bevölkerung Jena mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Bevölkerung Jena Example Suite");
        suite.setDescription("Check suite for the Bevölkerung Jena (Population) mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates a MeasureCheck with the specified name.
     *
     * @param measureName the name of the measure
     * @return the configured MeasureCheck
     */
    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    /**
     * Creates a DimensionCheck with the specified name.
     *
     * @param dimensionName the name of the dimension
     * @param hierarchyChecks the hierarchy checks to add to the dimension check
     * @return the configured DimensionCheck
     */
    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        if (hierarchyChecks != null) {
            for (HierarchyCheck hierarchyCheck : hierarchyChecks) {
                dimensionCheck.getHierarchyChecks().add(hierarchyCheck);
            }
        }
        return dimensionCheck;
    }

    /**
     * Creates a HierarchyCheck with the specified name and level checks.
     *
     * @param hierarchyName the name of the hierarchy
     * @param levelChecks the level checks to add to the hierarchy check
     * @return the configured HierarchyCheck
     */
    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        if (levelChecks != null) {
            for (LevelCheck levelCheck : levelChecks) {
                hierarchyCheck.getLevelChecks().add(levelCheck);
            }
        }
        return hierarchyCheck;
    }

    /**
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }


}
