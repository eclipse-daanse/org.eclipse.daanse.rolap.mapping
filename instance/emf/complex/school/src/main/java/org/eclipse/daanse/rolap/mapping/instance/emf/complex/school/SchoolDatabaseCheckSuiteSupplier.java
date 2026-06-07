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
 *   SmartCity Jena, Stefan Bischof - initial
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.school;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCellCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseQueryCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

/**
 * Phase-2 replacement for the database-shape checks that used to be
 * embedded in {@link CheckSuiteSupplier}'s OLAP {@code CatalogCheck} via
 * {@code getDatabaseSchemaChecks()}.
 *
 * <p>Also hosts the 4 raw-SQL query checks that previously lived as
 * {@code QueryCheck} with {@code QueryLanguage.SQL} on the OLAP
 * {@code CatalogCheck}. The OLAP path tried to parse them via the MDX
 * parser and failed. The CWM-side {@link DatabaseQueryCheck} runs them
 * directly via JDBC, which is the right path for raw SQL.
 */
public class SchoolDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

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
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for Daanse Example - Schulwesen",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("fact_schueler",
                                col("schule_id", "INTEGER"),
                                col("klassen_wdh", "INTEGER"),
                                col("foerder_art_id", "INTEGER"),
                                col("schul_jahr_id", "INTEGER")),
                        table("fact_schulen",
                                col("schule_id", "INTEGER"),
                                col("anzahl_schulen", "INTEGER"),
                                col("anzahl_klassen", "INTEGER"),
                                col("schul_jahr_id", "INTEGER")),
                        table("fact_personal",
                                col("schul_jahr_id", "INTEGER"),
                                col("alters_gruppe_id", "INTEGER"),
                                col("geschlecht_id", "INTEGER"),
                                col("personal_art_id", "INTEGER"),
                                col("anzahl_personen", "INTEGER")),
                        table("schule",
                                col("id", "INTEGER"),
                                col("traeger_id", "INTEGER")),
                        table("schul_jahr",
                                col("id", "INTEGER"),
                                col("schul_jahr", "VARCHAR")),
                        table("foerderung_art",
                                col("id", "INTEGER"),
                                col("foerderung_art", "VARCHAR"),
                                col("sp_foerderbedarf_id", "INTEGER")),
                        table("sonderpaed_foerderbedarf",
                                col("id", "INTEGER")),
                        table("bundesland",
                                col("id", "INTEGER"),
                                col("bezeichnung", "VARCHAR")),
                        table("wohnort_landkreis",
                                col("bundesland_id", "INTEGER")),
                        table("alters_gruppe",
                                col("id", "INTEGER"),
                                col("altersgruppe", "VARCHAR")),
                        table("geschlecht",
                                col("id", "INTEGER"),
                                col("bezeichnung", "VARCHAR")),
                        table("personal_art",
                                col("id", "INTEGER"),
                                col("bezeichnung", "VARCHAR")),
                        table("traeger",
                                col("id", "INTEGER"),
                                col("traeger_art_id", "INTEGER")),
                        table("traeger_art",
                                col("id", "INTEGER"),
                                col("traeger_art", "VARCHAR"),
                                col("traeger_kat_id", "VARCHAR")),
                        table("traeger_kategorie",
                                col("id", "INTEGER"))))),
                List.of(
                        new DatabaseQueryCheck("Sql Query Check1 for Daanse Example - Schulwesen", Q1, List.of(
                                new DatabaseCellCheck("FOERDERUNGSART", 0, 0, "Gemeinsamer Unterricht"),
                                new DatabaseCellCheck("SCHULJAHR", 0, 1, "2019/2020"),
                                new DatabaseCellCheck("SCHULEN_MIT_FOERDERBEDARF", 0, 2, "13152"),
                                new DatabaseCellCheck("BETROFFENE_KLASSEN", 0, 3, "6576"))),
                        new DatabaseQueryCheck("Sql Query Check2 for Daanse Example - Schulwesen", Q2, List.of(
                                new DatabaseCellCheck("BUNDESLAND_NAME", 0, 0, "Außerhalb Thüringens"),
                                new DatabaseCellCheck("ANZAHL_SCHULEN", 0, 1, "189"),
                                new DatabaseCellCheck("ANZAHL_KLASSEN", 0, 2, "3710"))),
                        new DatabaseQueryCheck("Sql Query Check3 for Daanse Example - Schulwesen", Q3, List.of(
                                new DatabaseCellCheck("SCHULJAHR", 0, 0, "2018/2019"),
                                new DatabaseCellCheck("ALTERSGRUPPE", 0, 1, "30 bis unter 45 Jahre"),
                                new DatabaseCellCheck("GESCHLECHT", 0, 2, "männlich"),
                                new DatabaseCellCheck("PERSONALART", 0, 3, "Erzieher:in"),
                                new DatabaseCellCheck("ANZAHL_PERSONEN", 0, 4, "12"))),
                        new DatabaseQueryCheck("Sql Query Check4 for Daanse Example - Schulwesen", Q4, List.of(
                                new DatabaseCellCheck("TRAEGERART", 0, 0, "kommunaler Träger"),
                                new DatabaseCellCheck("ANZAHL_SCHULEN", 0, 1, "27"),
                                new DatabaseCellCheck("GESAMT_KLASSEN", 0, 2, "602")))));
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
