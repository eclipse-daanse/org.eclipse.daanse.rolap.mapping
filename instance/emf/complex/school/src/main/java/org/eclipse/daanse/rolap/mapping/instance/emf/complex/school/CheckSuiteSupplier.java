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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.school;

import java.util.List;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
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
 * Provides a check suite for the complex school mapping example.
 * Checks that the catalog with three cubes (Schulen, Personal, SchülerInnen),
 * their measures, and dimensions exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Example - Schulwesen";

    // Cube names
    private static final String CUBE_SCHULEN_NAME = "Schulen in Jena (Institutionen)";
    private static final String CUBE_PERSONAL_NAME = "Pädagogisches Personal an Jenaer Schulen";
    private static final String CUBE_SCHUELER_NAME = "Schüler:innen an Jenaer Schulen";

    // Measure names
    private static final String MEASURE_ANZAHL_SCHULEN = "Anzahl Schulen";
    private static final String MEASURE_ANZAHL_KLASSEN = "Anzahl Klassen";
    private static final String MEASURE_ANZAHL_PERSONEN = "Anzahl Personen";
    private static final String MEASURE_ANZAHL_SCHUELER = "Anzahl SchülerInnen";

    // Dimension names
    private static final String DIM_SCHULEN = "Schulen";
    private static final String DIM_SCHULJAHR = "Schuljahr";
    private static final String DIM_ALTERSGRUPPE = "Altersgruppe";
    private static final String DIM_GESCHLECHT = "Geschlecht";
    private static final String DIM_BERUFSGRUPPE = "Berufsgruppe";
    private static final String DIM_EINSCHULUNGEN = "Einschulung";
    private static final String DIM_KLASSENWIEDERHOLUNG = "Klassenwiederholung";
    private static final String DIM_SCHULABSCHLUSS = "Schulabschluss";
    private static final String DIM_MIGRATIONSHINTERGRUND = "Migrationshintergrund";
    private static final String DIM_WOHNLANDKREIS = "Wohnlandkreis";
    private static final String DIM_INKLUSION = "Sonderpädagogische Förderung";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks for shared dimensions
        DimensionCheck dimCheckSchulen = createDimensionCheck(DIM_SCHULEN,
            createHierarchyCheck("Schulen nach Ganztagsangebot",
                createLevelCheck("Art des Ganztagsangebots"),
                createLevelCheck("Schule")),
            createHierarchyCheck("Schulen nach Trägerschaft",
                createLevelCheck("Schulträger-Kategorie"),
                createLevelCheck("Schulträger-Art"),
                createLevelCheck("Schulträger"),
                createLevelCheck("Schule")),
            createHierarchyCheck("Schulen nach Art",
                createLevelCheck("Schulkategorie"),
                createLevelCheck("Schulart"),
                createLevelCheck("Schule")));
        DimensionCheck dimCheckSchulen2 = createDimensionCheck(DIM_SCHULEN,
                createHierarchyCheck("Schulen nach Ganztagsangebot",
                    createLevelCheck("Art des Ganztagsangebots"),
                    createLevelCheck("Schule")),
                createHierarchyCheck("Schulen nach Trägerschaft",
                    createLevelCheck("Schulträger-Kategorie"),
                    createLevelCheck("Schulträger-Art"),
                    createLevelCheck("Schulträger"),
                    createLevelCheck("Schule")),
                createHierarchyCheck("Schulen nach Art",
                    createLevelCheck("Schulkategorie"),
                    createLevelCheck("Schulart"),
                    createLevelCheck("Schule")));
        DimensionCheck dimCheckSchulen3 = createDimensionCheck(DIM_SCHULEN,
                createHierarchyCheck("Schulen nach Ganztagsangebot",
                    createLevelCheck("Art des Ganztagsangebots"),
                    createLevelCheck("Schule")),
                createHierarchyCheck("Schulen nach Trägerschaft",
                    createLevelCheck("Schulträger-Kategorie"),
                    createLevelCheck("Schulträger-Art"),
                    createLevelCheck("Schulträger"),
                    createLevelCheck("Schule")),
                createHierarchyCheck("Schulen nach Art",
                    createLevelCheck("Schulkategorie"),
                    createLevelCheck("Schulart"),
                    createLevelCheck("Schule")));
        DimensionCheck dimCheckSchuljahre = createDimensionCheck(DIM_SCHULJAHR,
            createHierarchyCheck("Schuljahre",
                createLevelCheck("Schuljahr")));
        DimensionCheck dimCheckSchuljahre2 = createDimensionCheck(DIM_SCHULJAHR,
                createHierarchyCheck("Schuljahre",
                    createLevelCheck("Schuljahr")));
        DimensionCheck dimCheckSchuljahre3 = createDimensionCheck(DIM_SCHULJAHR,
                createHierarchyCheck("Schuljahre",
                    createLevelCheck("Schuljahr")));
        DimensionCheck dimCheckAltersgruppe = createDimensionCheck(DIM_ALTERSGRUPPE,
                createHierarchyCheck("Altersgruppen",
                        createLevelCheck("Altersgruppe")));
        DimensionCheck dimCheckGeschlecht = createDimensionCheck(DIM_GESCHLECHT,
            createHierarchyCheck("Geschlecht",
                createLevelCheck("Geschlecht")));
        DimensionCheck dimCheckGeschlecht3 = createDimensionCheck(DIM_GESCHLECHT,
                createHierarchyCheck("Geschlecht",
                    createLevelCheck("Geschlecht")));
        DimensionCheck dimCheckBerufsgruppenPersonal = createDimensionCheck(DIM_BERUFSGRUPPE,
            createHierarchyCheck("Berufsgruppen",
                createLevelCheck("Berufsgruppe")));
        DimensionCheck dimCheckEinschulungen = createDimensionCheck(DIM_EINSCHULUNGEN,
            createHierarchyCheck("Einschulung",
                createLevelCheck("Einschulung")));
        DimensionCheck dimCheckKlassenwiederholung = createDimensionCheck(DIM_KLASSENWIEDERHOLUNG,
            createHierarchyCheck("Klassenwiederholung",
                createLevelCheck("Klassenwiederholung")));
        DimensionCheck dimCheckSchulabschluss = createDimensionCheck(DIM_SCHULABSCHLUSS,
            createHierarchyCheck("Schulabschlüsse",
                createLevelCheck("Schulabschlüsse")));
        DimensionCheck dimCheckMigrationshintergrund = createDimensionCheck(DIM_MIGRATIONSHINTERGRUND,
            createHierarchyCheck("Migrationshintergrund",
                createLevelCheck("Migrationshintergrund")));
        DimensionCheck dimCheckWohnlandkreis = createDimensionCheck(DIM_WOHNLANDKREIS,
            createHierarchyCheck("Wohnlandkreis",
                createLevelCheck("Bundesland"),
                createLevelCheck("Wohnlandkreis")));
        DimensionCheck dimCheckInklusion = createDimensionCheck(DIM_INKLUSION,
            createHierarchyCheck("Sonderpädagogische Förderung",
                createLevelCheck("Förderbedarf"),
                createLevelCheck("Art der Förderung")));

        // Create cube 1: Schulen in Jena (Institutionen)
        MeasureCheck measureCheckAnzahlSchulen = createMeasureCheck(MEASURE_ANZAHL_SCHULEN);
        MeasureCheck measureCheckAnzahlKlassen = createMeasureCheck(MEASURE_ANZAHL_KLASSEN);

        CubeCheck cubeCheckSchulen = factory.createCubeCheck();
        cubeCheckSchulen.setName("CubeCheck-" + CUBE_SCHULEN_NAME);
        cubeCheckSchulen.setDescription("Check that cube '" + CUBE_SCHULEN_NAME + "' exists");
        cubeCheckSchulen.setCubeName(CUBE_SCHULEN_NAME);
        cubeCheckSchulen.getMeasureChecks().add(measureCheckAnzahlSchulen);
        cubeCheckSchulen.getMeasureChecks().add(measureCheckAnzahlKlassen);
        cubeCheckSchulen.getDimensionChecks().add(dimCheckSchulen);
        cubeCheckSchulen.getDimensionChecks().add(dimCheckSchuljahre);

        // Create cube 2: Pädagogisches Personal an Jenaer Schulen
        MeasureCheck measureCheckAnzahlPersonen = createMeasureCheck(MEASURE_ANZAHL_PERSONEN);

        CubeCheck cubeCheckPersonal = factory.createCubeCheck();
        cubeCheckPersonal.setName("CubeCheck-" + CUBE_PERSONAL_NAME);
        cubeCheckPersonal.setDescription("Check that cube '" + CUBE_PERSONAL_NAME + "' exists");
        cubeCheckPersonal.setCubeName(CUBE_PERSONAL_NAME);
        cubeCheckPersonal.getMeasureChecks().add(measureCheckAnzahlPersonen);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckSchulen2);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckSchuljahre2);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckAltersgruppe);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckGeschlecht);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckBerufsgruppenPersonal);

        // Create cube 3: Schüler:innen an Jenaer Schulen
        MeasureCheck measureCheckAnzahlSchueler = createMeasureCheck(MEASURE_ANZAHL_SCHUELER);

        CubeCheck cubeCheckSchueler = factory.createCubeCheck();
        cubeCheckSchueler.setName("CubeCheck-" + CUBE_SCHUELER_NAME);
        cubeCheckSchueler.setDescription("Check that cube '" + CUBE_SCHUELER_NAME + "' exists");
        cubeCheckSchueler.setCubeName(CUBE_SCHUELER_NAME);
        cubeCheckSchueler.getMeasureChecks().add(measureCheckAnzahlSchueler);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchulen3);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchuljahre3);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckGeschlecht3);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckWohnlandkreis);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckEinschulungen);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchulabschluss);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckKlassenwiederholung);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckMigrationshintergrund);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckInklusion);

        // MDX QueryChecks — smoke-test that simple measure queries against each
        // cube parse, execute, and return at least one column. Mirrors the
        // shape of legacy.xmla query tests so we can migrate them incrementally.
        QueryCheck mdxSchulen = factory.createQueryCheck();
        mdxSchulen.setName("MDX-Schulen-Members");
        mdxSchulen.setDescription("Schulen cube responds to a Measures.Members query");
        mdxSchulen.setQuery("SELECT [Measures].Members ON COLUMNS FROM [" + CUBE_SCHULEN_NAME + "]");
        mdxSchulen.setQueryLanguage(QueryLanguage.MDX);

        QueryCheck mdxPersonal = factory.createQueryCheck();
        mdxPersonal.setName("MDX-Personal-Members");
        mdxPersonal.setDescription("Personal cube responds to a Measures.Members query");
        mdxPersonal.setQuery("SELECT [Measures].Members ON COLUMNS FROM [" + CUBE_PERSONAL_NAME + "]");
        mdxPersonal.setQueryLanguage(QueryLanguage.MDX);

        QueryCheck mdxSchueler = factory.createQueryCheck();
        mdxSchueler.setName("MDX-Schueler-Members");
        mdxSchueler.setDescription("Schüler cube responds to a Measures.Members query");
        mdxSchueler.setQuery("SELECT [Measures].Members ON COLUMNS FROM [" + CUBE_SCHUELER_NAME + "]");
        mdxSchueler.setQueryLanguage(QueryLanguage.MDX);

        // Create catalog check with all cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckSchulen);
        catalogCheck.getCubeChecks().add(cubeCheckPersonal);
        catalogCheck.getCubeChecks().add(cubeCheckSchueler);
        catalogCheck.getQueryChecks().add(mdxSchulen);
        catalogCheck.getQueryChecks().add(mdxPersonal);
        catalogCheck.getQueryChecks().add(mdxSchueler);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for complex school mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("School Example Suite");
        suite.setDescription("Check suite for the complex school mapping example");
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
     * @param dimensionName   the name of the dimension
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
     * @param levelChecks   the level checks to add to the hierarchy check
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
