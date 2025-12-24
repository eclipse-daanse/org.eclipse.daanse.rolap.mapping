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
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
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
    private static final String DIM_SCHULJAHRE = "Schuljahre";
    private static final String DIM_ALTERSGRUPPEN_PERSONAL = "Altersgruppen Personal";
    private static final String DIM_GESCHLECHT = "Geschlecht";
    private static final String DIM_BERUFSGRUPPEN_PERSONAL = "Berufsgruppen Personal";
    private static final String DIM_EINSCHULUNGEN = "Einschulungen";
    private static final String DIM_KLASSENWIEDERHOLUNG = "Klassenwiederholung";
    private static final String DIM_SCHULABSCHLUSS = "Schulabschluss";
    private static final String DIM_MIGRATIONSHINTERGRUND = "Migrationshintergrund";
    private static final String DIM_WOHNLANDKREIS = "Wohnlandkreis";
    private static final String DIM_INKLUSION = "Inklusion";

    private static final String Q1 = """
        SELECT
        foerderung_art.foerderung_art AS Foerderungsart,
        schul_jahr.schul_jahr AS Schuljahr,
        count(fact_schueler.schule_id) AS Schulen_mit_Foerderbedarf,
        sum(fact_schueler.klassen_wdh) AS Betroffene_Klassen
        FROM fact_schueler
        JOIN foerderung_art ON fact_schueler.foerder_art_id = foerderung_art.id
        JOIN sonderpaed_foerderbedarf ON sonderpaed_foerderbedarf.id = foerderung_art.sp_foerderbedarf_id
        JOIN schul_jahr ON fact_schueler.schul_jahr_id = schul_jahr.id
        WHERE schul_jahr.schul_jahr = '2019/2020'
        GROUP BY foerderung_art.foerderung_art, schul_jahr.schul_jahr
        ORDER BY sum(fact_schueler.klassen_wdh) DESC
        """;

    private static final String Q2 = """
        SELECT bundesland.bezeichnung AS Bundesland_Name, sum(fact_schulen.anzahl_schulen) AS Anzahl_Schulen, sum(fact_schulen.anzahl_klassen) AS Anzahl_Klassen
        FROM fact_schulen
            JOIN schule ON fact_schulen.schule_id = schule.id
            JOIN bundesland JOIN wohnort_landkreis ON wohnort_landkreis.bundesland_id = bundesland.id
            GROUP BY bundesland.id, bundesland.bezeichnung
            ORDER BY bundesland.bezeichnung
        """;

    private static final String Q3 = """
        SELECT schul_jahr.schul_jahr AS Schuljahr, alters_gruppe.altersgruppe AS Altersgruppe, geschlecht.bezeichnung AS Geschlecht, personal_art.bezeichnung AS Personalart, sum(fact_personal.anzahl_personen) AS Anzahl_Personen
        FROM fact_personal
            JOIN schul_jahr ON fact_personal.schul_jahr_id = schul_jahr.id
            JOIN alters_gruppe ON fact_personal.alters_gruppe_id = alters_gruppe.id JOIN geschlecht ON fact_personal.geschlecht_id = geschlecht.id
            JOIN personal_art ON fact_personal.personal_art_id = personal_art.id
            WHERE schul_jahr.schul_jahr = '2018/2019'
            GROUP BY schul_jahr.schul_jahr, alters_gruppe.altersgruppe, geschlecht.bezeichnung, personal_art.bezeichnung
            ORDER BY alters_gruppe.altersgruppe, geschlecht.bezeichnung
        """;

    private static final String Q4 = """
        SELECT
        traeger_art.traeger_art AS Traegerart,
        count(fact_schulen.schule_id) AS Anzahl_Schulen,
        sum(fact_schulen.anzahl_klassen) AS Gesamt_Klassen
            FROM fact_schulen
                JOIN schule ON fact_schulen.schule_id = schule.id
                JOIN traeger ON schule.traeger_id = traeger.id
                JOIN traeger_art ON traeger.traeger_art_id = traeger_art.id
                JOIN traeger_kategorie ON traeger_art.traeger_kat_id = traeger_kategorie.id
                JOIN schul_jahr ON fact_schulen.schul_jahr_id = schul_jahr.id
            WHERE schul_jahr.schul_jahr = '2018/2019'
            GROUP BY traeger_art.traeger_art
            ORDER BY count(DISTINCT fact_schulen.schule_id) DESC
        """;

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
        DimensionCheck dimCheckSchuljahre = createDimensionCheck(DIM_SCHULJAHRE,
            createHierarchyCheck("Schuljahre",
                createLevelCheck("Schuljahr")));
        DimensionCheck dimCheckGeschlecht = createDimensionCheck(DIM_GESCHLECHT,
            createHierarchyCheck("Geschlecht",
                createLevelCheck("Geschlecht")));
        DimensionCheck dimCheckAltersgruppenPersonal = createDimensionCheck(DIM_ALTERSGRUPPEN_PERSONAL,
            createHierarchyCheck("Altersgruppen",
                createLevelCheck("Altersgruppe")));
        DimensionCheck dimCheckBerufsgruppenPersonal = createDimensionCheck(DIM_BERUFSGRUPPEN_PERSONAL,
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
        cubeCheckPersonal.getDimensionChecks().add(dimCheckSchulen);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckSchuljahre);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckAltersgruppenPersonal);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckGeschlecht);
        cubeCheckPersonal.getDimensionChecks().add(dimCheckBerufsgruppenPersonal);

        // Create cube 3: Schüler:innen an Jenaer Schulen
        MeasureCheck measureCheckAnzahlSchueler = createMeasureCheck(MEASURE_ANZAHL_SCHUELER);

        CubeCheck cubeCheckSchueler = factory.createCubeCheck();
        cubeCheckSchueler.setName("CubeCheck-" + CUBE_SCHUELER_NAME);
        cubeCheckSchueler.setDescription("Check that cube '" + CUBE_SCHUELER_NAME + "' exists");
        cubeCheckSchueler.setCubeName(CUBE_SCHUELER_NAME);
        cubeCheckSchueler.getMeasureChecks().add(measureCheckAnzahlSchueler);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchulen);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchuljahre);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckGeschlecht);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckEinschulungen);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckKlassenwiederholung);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckSchulabschluss);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckMigrationshintergrund);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckWohnlandkreis);
        cubeCheckSchueler.getDimensionChecks().add(dimCheckInklusion);

        CellValueCheck cellCheck100 = factory.createCellValueCheck();
        cellCheck100.setName("FOERDERUNGSART");
        cellCheck100.setExpectedValue("Gemeinsamer Unterricht");
        cellCheck100.getCoordinates().add(0);
        cellCheck100.getCoordinates().add(0);

        CellValueCheck cellCheck101 = factory.createCellValueCheck();
        cellCheck101.setName("SCHULJAHR");
        cellCheck101.setExpectedValue("2019//2020");
        cellCheck101.getCoordinates().add(0);
        cellCheck101.getCoordinates().add(1);

        CellValueCheck cellCheck102 = factory.createCellValueCheck();
        cellCheck102.setName("SCHULEN_MIT_FOERDERBEDARF");
        cellCheck102.setExpectedValue("13152");
        cellCheck102.getCoordinates().add(0);
        cellCheck102.getCoordinates().add(2);

        CellValueCheck cellCheck103 = factory.createCellValueCheck();
        cellCheck103.setName("BETROFFENE_KLASSEN");
        cellCheck103.setExpectedValue("6576");
        cellCheck103.getCoordinates().add(0);
        cellCheck103.getCoordinates().add(2);

        QueryCheck sqlQueryCheck1 = factory.createQueryCheck();
        sqlQueryCheck1.setName("Sql Query Check1 for " + CATALOG_NAME);
        sqlQueryCheck1.setQuery(Q1);
        sqlQueryCheck1.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck1.getCellChecks().add(cellCheck100);
        sqlQueryCheck1.getCellChecks().add(cellCheck101);
        sqlQueryCheck1.getCellChecks().add(cellCheck102);
        sqlQueryCheck1.getCellChecks().add(cellCheck103);

        CellValueCheck cellCheck200 = factory.createCellValueCheck();
        cellCheck200.setName("BUNDESLAND_NAME");
        cellCheck200.setExpectedValue("Außerhalb Thüringens");
        cellCheck200.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck201 = factory.createCellValueCheck();
        cellCheck201.setName("ANZAHL_SCHULEN");
        cellCheck201.setExpectedValue("189");
        cellCheck201.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck202 = factory.createCellValueCheck();
        cellCheck202.setName("ANZAHL_KLASSEN");
        cellCheck202.setExpectedValue("3710");
        cellCheck202.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck2 = factory.createQueryCheck();
        sqlQueryCheck2.setName("Sql Query Check2 for " + CATALOG_NAME);
        sqlQueryCheck2.setQuery(Q2);
        sqlQueryCheck2.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck2.getCellChecks().addAll(List.of(cellCheck200, cellCheck201, cellCheck202));

        CellValueCheck cellCheck300 = factory.createCellValueCheck();
        cellCheck300.setName("SCHULJAHR");
        cellCheck300.setExpectedValue("2018/2019");
        cellCheck300.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck301 = factory.createCellValueCheck();
        cellCheck301.setName("ALTERSGRUPPE");
        cellCheck301.setExpectedValue("30 bis unter 45 Jahre");
        cellCheck301.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck302 = factory.createCellValueCheck();
        cellCheck302.setName("GESCHLECHT");
        cellCheck302.setExpectedValue("männlich");
        cellCheck302.getCoordinates().addAll(List.of(0, 2));

        CellValueCheck cellCheck303 = factory.createCellValueCheck();
        cellCheck303.setName("PERSONALART");
        cellCheck303.setExpectedValue("Erzieher:in");
        cellCheck303.getCoordinates().addAll(List.of(0, 3));

        CellValueCheck cellCheck304 = factory.createCellValueCheck();
        cellCheck304.setName("ANZAHL_PERSONEN");
        cellCheck304.setExpectedValue("12");
        cellCheck304.getCoordinates().addAll(List.of(0, 4));

        QueryCheck sqlQueryCheck3 = factory.createQueryCheck();
        sqlQueryCheck3.setName("Sql Query Check2 for " + CATALOG_NAME);
        sqlQueryCheck3.setQuery(Q3);
        sqlQueryCheck3.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck3.getCellChecks().addAll(List.of(cellCheck300, cellCheck301, cellCheck302, cellCheck303, cellCheck304));

        CellValueCheck cellCheck400 = factory.createCellValueCheck();
        cellCheck400.setName("TRAEGERART");
        cellCheck400.setExpectedValue("kommunaler Träger");
        cellCheck400.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck cellCheck401 = factory.createCellValueCheck();
        cellCheck401.setName("ANZAHL_SCHULEN");
        cellCheck401.setExpectedValue("27");
        cellCheck401.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck cellCheck402 = factory.createCellValueCheck();
        cellCheck402.setName("GESAMT_KLASSEN");
        cellCheck402.setExpectedValue("602");
        cellCheck402.getCoordinates().addAll(List.of(0, 2));

        QueryCheck sqlQueryCheck4 = factory.createQueryCheck();
        sqlQueryCheck4.setName("Sql Query Check2 for " + CATALOG_NAME);
        sqlQueryCheck4.setQuery(Q4);
        sqlQueryCheck4.setQueryLanguage(QueryLanguage.SQL);
        sqlQueryCheck4.getCellChecks().addAll(List.of(cellCheck400, cellCheck401, cellCheck402));

        // Create database table and column checks
        DatabaseTableCheck tableCheckFactSchueler = createTableCheck("fact_schueler",
            createColumnCheck("schule_id", "INTEGER"),
            createColumnCheck("klassen_wdh", "INTEGER"),
            createColumnCheck("foerder_art_id", "INTEGER"),
            createColumnCheck("schul_jahr_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckFactSchulen = createTableCheck("fact_schulen",
            createColumnCheck("schule_id", "INTEGER"),
            createColumnCheck("anzahl_schulen", "INTEGER"),
            createColumnCheck("anzahl_klassen", "INTEGER"),
            createColumnCheck("schul_jahr_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckFactPersonal = createTableCheck("fact_personal",
            createColumnCheck("schul_jahr_id", "INTEGER"),
            createColumnCheck("alters_gruppe_id", "INTEGER"),
            createColumnCheck("geschlecht_id", "INTEGER"),
            createColumnCheck("personal_art_id", "INTEGER"),
            createColumnCheck("anzahl_personen", "INTEGER")
        );

        DatabaseTableCheck tableCheckSchule = createTableCheck("schule",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("traeger_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckSchulJahr = createTableCheck("schul_jahr",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("schul_jahr", "VARCHAR")
        );

        DatabaseTableCheck tableCheckFoerderungArt = createTableCheck("foerderung_art",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("foerderung_art", "VARCHAR"),
            createColumnCheck("sp_foerderbedarf_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckSonderpaedFoerderbedarf = createTableCheck("sonderpaed_foerderbedarf",
            createColumnCheck("id", "INTEGER")
        );

        DatabaseTableCheck tableCheckBundesland = createTableCheck("bundesland",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("bezeichnung", "VARCHAR")
        );

        DatabaseTableCheck tableCheckWohnortLandkreis = createTableCheck("wohnort_landkreis",
            createColumnCheck("bundesland_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckAltersGruppe = createTableCheck("alters_gruppe",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("altersgruppe", "VARCHAR")
        );

        DatabaseTableCheck tableCheckGeschlecht = createTableCheck("geschlecht",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("bezeichnung", "VARCHAR")
        );

        DatabaseTableCheck tableCheckPersonalArt = createTableCheck("personal_art",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("bezeichnung", "VARCHAR")
        );

        DatabaseTableCheck tableCheckTraeger = createTableCheck("traeger",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("traeger_art_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckTraegerArt = createTableCheck("traeger_art",
            createColumnCheck("id", "INTEGER"),
            createColumnCheck("traeger_art", "VARCHAR"),
            createColumnCheck("traeger_kat_id", "INTEGER")
        );

        DatabaseTableCheck tableCheckTraegerKategorie = createTableCheck("traeger_kategorie",
            createColumnCheck("id", "INTEGER")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for complex school mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckFactSchueler);
        databaseSchemaCheck.getTableChecks().add(tableCheckFactSchulen);
        databaseSchemaCheck.getTableChecks().add(tableCheckFactPersonal);
        databaseSchemaCheck.getTableChecks().add(tableCheckSchule);
        databaseSchemaCheck.getTableChecks().add(tableCheckSchulJahr);
        databaseSchemaCheck.getTableChecks().add(tableCheckFoerderungArt);
        databaseSchemaCheck.getTableChecks().add(tableCheckSonderpaedFoerderbedarf);
        databaseSchemaCheck.getTableChecks().add(tableCheckBundesland);
        databaseSchemaCheck.getTableChecks().add(tableCheckWohnortLandkreis);
        databaseSchemaCheck.getTableChecks().add(tableCheckAltersGruppe);
        databaseSchemaCheck.getTableChecks().add(tableCheckGeschlecht);
        databaseSchemaCheck.getTableChecks().add(tableCheckPersonalArt);
        databaseSchemaCheck.getTableChecks().add(tableCheckTraeger);
        databaseSchemaCheck.getTableChecks().add(tableCheckTraegerArt);
        databaseSchemaCheck.getTableChecks().add(tableCheckTraegerKategorie);

        // Create catalog check with all cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckSchulen);
        catalogCheck.getCubeChecks().add(cubeCheckPersonal);
        catalogCheck.getCubeChecks().add(cubeCheckSchueler);
        catalogCheck.getQueryChecks().add(sqlQueryCheck1);
        catalogCheck.getQueryChecks().add(sqlQueryCheck2);
        catalogCheck.getQueryChecks().add(sqlQueryCheck3);
        catalogCheck.getQueryChecks().add(sqlQueryCheck4);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

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

    /**
     * Creates a DatabaseColumnCheck with the specified name and type.
     *
     * @param columnName the name of the column
     * @param columnType the expected type of the column
     * @return the configured DatabaseColumnCheck
     */
    private DatabaseColumnCheck createColumnCheck(String columnName, String columnType) {
        DatabaseColumnAttributeCheck columnTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTypeCheck.setExpectedValue(columnType);

        DatabaseColumnCheck columnCheck = factory.createDatabaseColumnCheck();
        columnCheck.setName("Database Column Check " + columnName);
        columnCheck.setColumnName(columnName);
        columnCheck.getColumnAttributeChecks().add(columnTypeCheck);

        return columnCheck;
    }

    /**
     * Creates a DatabaseTableCheck with the specified name and column checks.
     *
     * @param tableName    the name of the table
     * @param columnChecks the column checks to add to the table check
     * @return the configured DatabaseTableCheck
     */
    private DatabaseTableCheck createTableCheck(String tableName, DatabaseColumnCheck... columnChecks) {
        DatabaseTableCheck tableCheck = factory.createDatabaseTableCheck();
        tableCheck.setName("Database Table Check " + tableName);
        tableCheck.setTableName(tableName);
        for (DatabaseColumnCheck columnCheck : columnChecks) {
            tableCheck.getColumnChecks().add(columnCheck);
        }
        return tableCheck;
    }
}
