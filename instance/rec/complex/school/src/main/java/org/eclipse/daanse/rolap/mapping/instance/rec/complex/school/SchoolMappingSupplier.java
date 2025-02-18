/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.rec.complex.school;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl.Builder;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "4")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class SchoolMappingSupplier implements CatalogMappingSupplier {

    private static final String ALLE_SCHULEN = "Alle Schulen";

    private static final String EINSCHULUNG2 = "einschulung";

    private static final String TRAEGER_ART = "traeger_art";

    private static final String WOHNLANDKREIS = "Wohnlandkreis";

    private static final String FOERDERUNG_ART = "foerderung_art";

    private static final String BEZEICHNUNG = "bezeichnung";

    private static final String SCHUL_JAHR = "schul_jahr";

    private static final String TRAEGER_KATEGORIE = "traeger_kategorie";

    private static final String SCHULEN = "Schulen";

    private static final String WOHNORT_LANDKREIS = "wohnort_landkreis";

    private static final String EINSCHULUNG = "Einschulung";

    private static final String MIGRATIONSHINTERGRUND = "Migrationshintergrund";

    private static final String SCHUL_NUMMER = "schul_nummer";

    private static final String SCHUL_JAHR_ID = "schul_jahr_id";

    private static final String SCHUL_NAME = "schul_name";

    private static final String SCHULE_ID = "schule_id";

    private static final String GESAMT = "Gesamt";

    private static final String SCHULJAHR = "Schuljahr";

    private static final String KLASSENWIEDERHOLUNG = "Klassenwiederholung";

    private static final String GESCHLECHT = "Geschlecht";

    private static final String SCHULE2 = "Schule";

    private static final String MIGRATIONS_HINTERGRUND = "migrations_hintergrund";

    private static final String SCHULE = "schule";

    private static final String CATALOG_NAME = "Schulwesen";

    //id,schul_nummer,schul_name,traeger_id,schul_art_id,ganztags_art_id
    //INTEGER,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
    public static final ColumnMappingImpl ID_COLUMN_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_NAME_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName(SCHUL_NAME).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl SCHUL_NUMMER_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName(SCHUL_NUMMER).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl GANZTAGS_ART_ID_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName("ganztags_art_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl TRAEGER_ART_ID_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName("traeger_art_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_ART_ID_IN_SCHULE_TABLE = ColumnMappingImpl.builder().withName("schul_art_id").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl SCHULE_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(SCHULE)
            .withColumns(List.of(ID_COLUMN_IN_SCHULE_TABLE, SCHUL_NAME_IN_SCHULE_TABLE, SCHUL_NUMMER_IN_SCHULE_TABLE,
                    GANZTAGS_ART_ID_IN_SCHULE_TABLE, TRAEGER_ART_ID_IN_SCHULE_TABLE, SCHUL_ART_ID_IN_SCHULE_TABLE))).build();

    //id,schul_umfang
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_GANZTAGS_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_UMFANG_IN_GANZTAGS_ART = ColumnMappingImpl.builder().withName("schul_umfang").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl GANZTAGS_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("ganztags_art")
            .withColumns(List.of(ID_COLUMN_IN_GANZTAGS_ART, SCHUL_UMFANG_IN_GANZTAGS_ART))).build();

    //id,traeger_name,traeger_art_id
    //INTEGER,VARCHAR,INTEGER
    public static final ColumnMappingImpl ID_COLUMN_IN_TRAEGER_TABLE = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE = ColumnMappingImpl.builder().withName("traeger_name").withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl TRAEGER_ID_COLUMN_IN_TRAEGER_TABLE = ColumnMappingImpl.builder().withName("traeger_id").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl TRAEGER_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("traeger")
            .withColumns(List.of(ID_COLUMN_IN_TRAEGER_TABLE, TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE, TRAEGER_ID_COLUMN_IN_TRAEGER_TABLE))).build();

    //id,traeger_art,traeger_kat_id
    //INTEGER,VARCHAR,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_TRAEGER_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl TRAEGER_ART_COLUMN_IN_TRAEGER_ART = ColumnMappingImpl.builder().withName(TRAEGER_ART).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART = ColumnMappingImpl.builder().withName("traeger_kat_id").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl TRAEGER_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(TRAEGER_ART)
            .withColumns(List.of(ID_COLUMN_IN_TRAEGER_ART, TRAEGER_ART_COLUMN_IN_TRAEGER_ART, TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART))).build();

    //id,traeger_kategorie
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_TRAEGER_KATEGORIE = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE = ColumnMappingImpl.builder().withName(TRAEGER_KATEGORIE).withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl TRAEGER_KATEGORIE_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(TRAEGER_KATEGORIE)
            .withColumns(List.of(ID_COLUMN_IN_TRAEGER_KATEGORIE, TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE))).build();

    //id,schulart_name,schul_kategorie_id
    //INTEGER,VARCHAR,INTEGER
    public static final ColumnMappingImpl ID_IN_SCHEDULE_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_KATEGORIE_IN_SCHEDULE_ART = ColumnMappingImpl.builder().withName("schul_kategorie_id").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl SCHEDULE_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("schule_art")
            .withColumns(List.of(ID_IN_SCHEDULE_ART, SCHUL_KATEGORIE_IN_SCHEDULE_ART))).build();

    //"id","schul_jahr","order"
    //ColumnType.INTEGER,ColumnType.VARCHAR,ColumnType.INTEGER
    public static final ColumnMappingImpl ID_COLUMN_IN_SCHUL_JAHR = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR = ColumnMappingImpl.builder().withName(SCHUL_JAHR).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl ORDER_COLUMN_IN_SCHUL_JAHR = ColumnMappingImpl.builder().withName("order").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl SCHUL_JAHR_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(SCHUL_JAHR)
            .withColumns(List.of(ID_COLUMN_IN_SCHUL_JAHR, SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR, ORDER_COLUMN_IN_SCHUL_JAHR))).build();

    //id,altersgruppe
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_ALTERS_GRUPPE = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE = ColumnMappingImpl.builder().withName("altersgruppe").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl ALTERS_GRUPPE_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("alters_gruppe")
            .withColumns(List.of(ID_COLUMN_IN_ALTERS_GRUPPE, ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE))).build();

    //id,kuerzel,bezeichnung
    //INTEGER,VARCHAR,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_GESCHLECHT = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl BEZEICHNUNG_COLUMN_IN_GESCHLECHT = ColumnMappingImpl.builder().withName(BEZEICHNUNG).withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl GESCHLECHT_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("geschlecht")
            .withColumns(List.of(ID_COLUMN_IN_GESCHLECHT, BEZEICHNUNG_COLUMN_IN_GESCHLECHT))).build();

    public static final ColumnMappingImpl ID_COLUMN_IN_EINSCHULUNG = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl EINSCHULUNG_COLUMN_IN_EINSCHULUNG = ColumnMappingImpl.builder().withName(EINSCHULUNG2).withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl EINSCHULUNG_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(EINSCHULUNG2)
            .withColumns(List.of(ID_COLUMN_IN_EINSCHULUNG, EINSCHULUNG_COLUMN_IN_EINSCHULUNG))).build();

    //id,klassenwiederholung
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG = ColumnMappingImpl.builder().withName("klassenwiederholung").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl KLASSEN_WIEDERHOLUNG_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("klassen_wiederholung")
            .withColumns(List.of(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG, KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG))).build();

    //id,schulabschluss
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_SCHUL_ABSCHLUSS = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS = ColumnMappingImpl.builder().withName("schulabschluss").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl SCHUL_ABSCHLUSS_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("schul_abschluss")
            .withColumns(List.of(ID_COLUMN_IN_SCHUL_ABSCHLUSS, SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS))).build();

    //id,migrations_hintergrund
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_MIGRATIONS_HINTERGRUND = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND = ColumnMappingImpl.builder().withName(MIGRATIONS_HINTERGRUND).withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl MIGRATIONS_HINTERGRUND_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(MIGRATIONS_HINTERGRUND)
            .withColumns(List.of(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND, MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND))).build();

    //id,kuerzel,bezeichnung,bundesland_id
    //INTEGER,VARCHAR,VARCHAR,INTEGER
    public static final ColumnMappingImpl ID_COLUMN_IN_WOHNORT_LANDKREIS = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS = ColumnMappingImpl.builder().withName(BEZEICHNUNG).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS = ColumnMappingImpl.builder().withName("bundesland_id").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl WOHNORT_LANDKREIS_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(WOHNORT_LANDKREIS)
            .withColumns(List.of(ID_COLUMN_IN_WOHNORT_LANDKREIS, BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS, BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS))).build();

    //id,schulart_name,schul_kategorie_id
    //INTEGER,VARCHAR,INTEGER
    public static final ColumnMappingImpl ID_COLUMN_IN_SCHUL_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHULART_NAME_COLUMN_IN_SCHUL_ART = ColumnMappingImpl.builder().withName("schulart_name").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl SCHUL_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("schul_art")
            .withColumns(List.of(ID_COLUMN_IN_SCHUL_ART, SCHULART_NAME_COLUMN_IN_SCHUL_ART))).build();

    //id,schul_kategorie_name
    //INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_SCHUL_KATEGORIE = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE = ColumnMappingImpl.builder().withName("schul_kategorie_name").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl SCHUL_KATEGORIE_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("schul_kategorie")
            .withColumns(List.of(ID_COLUMN_IN_SCHUL_KATEGORIE, SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE))).build();

    //id,foerderung_art,sp_foerderbedarf_id
    //INTEGER,VARCHAR,INTEGER,
    public static final ColumnMappingImpl ID_COLUMN_IN_FOERDERUNG_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART = ColumnMappingImpl.builder().withName(FOERDERUNG_ART).withType(ColumnType.VARCHAR).build();
    public static final ColumnMappingImpl SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART = ColumnMappingImpl.builder().withName("sp_foerderbedarf_id").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl FOERDERUNG_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName(FOERDERUNG_ART)
            .withColumns(List.of(ID_COLUMN_IN_FOERDERUNG_ART, FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART, SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART))).build();

    //id,bezeichnung,,,,,,,,,,,,,,,,,id,bezeichnung
    //INTEGER,VARCHAR,,,,,,,,,,,,,,,,,INTEGER,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_PERSONAL_ART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl BEZEICHNUNG_COLUMN_IN_PERSONAL_ART = ColumnMappingImpl.builder().withName(BEZEICHNUNG).withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl PERSONAL_ART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("personal_art")
            .withColumns(List.of(ID_COLUMN_IN_PERSONAL_ART, BEZEICHNUNG_COLUMN_IN_PERSONAL_ART))).build();

    //id,kuerzel,bezeichnung
    //INTEGER,VARCHAR,VARCHAR
    public static final ColumnMappingImpl ID_COLUMN_IN_BUNDESLAND = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl BEZEICHNUNG_COLUMN_IN_BUNDESLAND = ColumnMappingImpl.builder().withName(BEZEICHNUNG).withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl BUNDESLAND_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("bundesland")
            .withColumns(List.of(ID_COLUMN_IN_BUNDESLAND, BEZEICHNUNG_COLUMN_IN_BUNDESLAND))).build();

    public static final ColumnMappingImpl ID_COLUMN_IN_SONDERPAED_FOERDERBEDART = ColumnMappingImpl.builder().withName("id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART = ColumnMappingImpl.builder().withName("sonderpaed_bedarf").withType(ColumnType.VARCHAR).build();
    public static final PhysicalTableMappingImpl SONDERPAED_FOERDERBEDART_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("sonderpaed_foerderbedarf")
            .withColumns(List.of(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART, SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART))).build();

    //schule_id,schul_jahr_id,anzahl_schulen,anzahl_klassen
    //INTEGER,INTEGER,INTEGER,INTEGER
    public static final ColumnMappingImpl SCHULE_ID_COLUMN_IN_FACT_SCHULEN = ColumnMappingImpl.builder().withName(SCHULE_ID).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN = ColumnMappingImpl.builder().withName(SCHUL_JAHR_ID).withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl FACT_SCHULEN_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("fact_schulen")
            .withColumns(List.of(SCHULE_ID_COLUMN_IN_FACT_SCHULEN, SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN))).build();

    //schule_id,schul_jahr_id,alters_gruppe_id,geschlecht_id,personal_art_id,anzahl_personen
    //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
    public static final ColumnMappingImpl SCHULE_ID_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName(SCHULE_ID).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName(SCHUL_JAHR_ID).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName("alters_gruppe_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName("geschlecht_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName("personal_art_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL = ColumnMappingImpl.builder().withName("anzahl_personen").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl FACT_PERSONAM_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("fact_personal")
            .withColumns(List.of(
                SCHULE_ID_COLUMN_IN_FACT_PERSONAL,
                SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL,
                ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL,
                GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL,
                PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL,
                ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL
            ))).build();

    //schule_id,schul_jahr_id,geschlecht_id,wohn_lk_id,einschulung_id,schul_abschluss_id,klassen_wdh,migrations_hg_id,foerder_art_id,anzahl_schueler
    //INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
    public static final ColumnMappingImpl SCHULE_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName(SCHULE_ID).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName(SCHUL_JAHR_ID).withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("geschlecht_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("wohn_lk_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("einschulung_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("schul_abschluss_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("klassen_wdh").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("migrations_hg_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("foerder_art_id").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("anzahl_schueler").withType(ColumnType.INTEGER).build();
    public static final ColumnMappingImpl ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHUELER = ColumnMappingImpl.builder().withName("anzahl_schulen").withType(ColumnType.INTEGER).build();
    public static final PhysicalTableMappingImpl FACT_SCHUELER_TABLE = ((Builder) PhysicalTableMappingImpl.builder().withName("fact_schueler")
            .withColumns(List.of(
                SCHULE_ID_COLUMN_IN_FACT_SCHUELER,
                SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER,
                GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER,
                WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER,
                EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER,
                SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER,
                KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER,
                MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER,
                FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER,
                ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER,
                ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHUELER
                ))).build();

    private static final TableQueryMappingImpl SCHEDULE_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(SCHULE_TABLE).build();
    private static final TableQueryMappingImpl GANZTAGS_ART_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        GANZTAGS_ART_TABLE).build();
    private static final TableQueryMappingImpl TRAEGER_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(TRAEGER_TABLE).build();
    private static final TableQueryMappingImpl TRAEGER_ART_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(TRAEGER_ART_TABLE).build();
    private static final TableQueryMappingImpl TRAEGER_KATEGORIE_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(TRAEGER_KATEGORIE_TABLE).build();
    private static final TableQueryMappingImpl SCHEDULE_ART_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        SCHEDULE_ART_TABLE).build();
    private static final TableQueryMappingImpl SCHEDULE_KATEGORIE_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        SCHUL_KATEGORIE_TABLE).build();
    private static final TableQueryMappingImpl SCHUL_JAHT_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(SCHUL_JAHR_TABLE).build();
    private static final TableQueryMappingImpl ALTERS_GRUPPE_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        ALTERS_GRUPPE_TABLE).build();
    private static final TableQueryMappingImpl GESCHLECHT_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        GESCHLECHT_TABLE).build();
    private static final TableQueryMappingImpl PERSONAL_ART_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        PERSONAL_ART_TABLE).build();
    private static final TableQueryMappingImpl EINSCHULUNG_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(EINSCHULUNG_TABLE).build();
    private static final TableQueryMappingImpl KLASSEN_WIEDERHOLUNG_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        KLASSEN_WIEDERHOLUNG_TABLE).build();
    private static final TableQueryMappingImpl SCHUL_ABSCHLUSS_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        SCHUL_ABSCHLUSS_TABLE).build();
    private static final TableQueryMappingImpl MIGRATIONS_HINTERGRUND_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(MIGRATIONS_HINTERGRUND_TABLE).build();
    private static final TableQueryMappingImpl WOHNORT_LANDKREIS_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(WOHNORT_LANDKREIS_TABLE).build();
    private static final TableQueryMappingImpl BUNDESLAND_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        BUNDESLAND_TABLE).build();
    private static final TableQueryMappingImpl FOERDERUNG_ART_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(FOERDERUNG_ART_TABLE).build();
    private static final TableQueryMappingImpl SONDERPAED_FOERDERBEDART_TABLE_QUERY =
        TableQueryMappingImpl.builder().withTable(SONDERPAED_FOERDERBEDART_TABLE).build();
    private static final TableQueryMappingImpl FACT_SCHULEN_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        FACT_SCHULEN_TABLE).build();
    private static final TableQueryMappingImpl FACT_PERSONAM_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        FACT_PERSONAM_TABLE).build();
    private static final TableQueryMappingImpl FACT_SCHUELER_TABLE_QUERY = TableQueryMappingImpl.builder().withTable(
        FACT_SCHUELER_TABLE).build();

    private static final JoinQueryMappingImpl JOIN1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(GANZTAGS_ART_ID_IN_SCHULE_TABLE)
            .withQuery(SCHEDULE_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_GANZTAGS_ART)
            .withQuery(GANZTAGS_ART_TABLE_QUERY)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2_1_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART)
            .withQuery(TRAEGER_ART_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_TRAEGER_KATEGORIE)
            .withQuery(TRAEGER_KATEGORIE_TABLE_QUERY)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(TRAEGER_ID_COLUMN_IN_TRAEGER_TABLE)
            .withQuery(TRAEGER_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_TRAEGER_ART)
            .withQuery(JOIN2_1_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(TRAEGER_ART_ID_IN_SCHULE_TABLE)
            .withQuery(SCHEDULE_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_TRAEGER_TABLE)
            .withQuery(JOIN2_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN3_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(SCHUL_KATEGORIE_IN_SCHEDULE_ART)
            .withQuery(SCHEDULE_ART_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_SCHUL_KATEGORIE)
            .withQuery(SCHEDULE_KATEGORIE_TABLE_QUERY)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN3 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(SCHUL_ART_ID_IN_SCHULE_TABLE)
            .withQuery(SCHEDULE_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_IN_SCHEDULE_ART)
            .withQuery(JOIN3_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN4 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS)
            .withQuery(WOHNORT_LANDKREIS_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_BUNDESLAND)
            .withQuery(BUNDESLAND_TABLE_QUERY)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN5 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey(SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART)
            .withQuery(FOERDERUNG_ART_TABLE_QUERY)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART)
            .withQuery(SONDERPAED_FOERDERBEDART_TABLE_QUERY)
            .build())
        .build();

    private static final LevelMappingImpl LEVEL1 = LevelMappingImpl
        .builder()
        .withName("Art des Ganztagsangebots")
        .withColumn(ID_COLUMN_IN_GANZTAGS_ART)
        .withNameColumn(SCHUL_UMFANG_IN_GANZTAGS_ART)
        .withTable(GANZTAGS_ART_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL2 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn(ID_COLUMN_IN_SCHULE_TABLE)
        .withNameColumn(SCHUL_NAME_IN_SCHULE_TABLE)
        .withOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE)
        .withTable(SCHULE_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL3 = LevelMappingImpl
        .builder()
        .withName("Schulträger-Kategorie")
        .withColumn(ID_COLUMN_IN_TRAEGER_KATEGORIE)
        .withNameColumn(TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE)
        .withTable(TRAEGER_KATEGORIE_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL4 = LevelMappingImpl
        .builder()
        .withName("Schulträger-Art")
        .withColumn(ID_COLUMN_IN_TRAEGER_ART)
        .withNameColumn(TRAEGER_ART_COLUMN_IN_TRAEGER_ART)
        .withTable(TRAEGER_ART_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL5 = LevelMappingImpl
        .builder()
        .withName("Schulträger")
        .withColumn(ID_COLUMN_IN_TRAEGER_TABLE)
        .withNameColumn(TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE)
        .withTable(TRAEGER_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL6 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn(ID_COLUMN_IN_SCHULE_TABLE)
        .withNameColumn(SCHUL_NAME_IN_SCHULE_TABLE)
        .withOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE)
        .withTable(SCHULE_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL7 = LevelMappingImpl
        .builder()
        .withName("Schulkategorie")
        .withColumn(ID_COLUMN_IN_SCHUL_KATEGORIE)
        .withNameColumn(SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE)
        .withTable(SCHUL_KATEGORIE_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL8 = LevelMappingImpl
        .builder()
        .withName("Schulart")
        .withColumn(ID_COLUMN_IN_SCHUL_ART)
        .withNameColumn(SCHULART_NAME_COLUMN_IN_SCHUL_ART)
        .withTable(SCHUL_ART_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL9 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn(ID_COLUMN_IN_SCHULE_TABLE)
        .withNameColumn(SCHUL_NAME_IN_SCHULE_TABLE)
        .withOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE)
        .withTable(SCHULE_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL10 = LevelMappingImpl
        .builder()
        .withName(SCHULJAHR)
        .withColumn(ID_COLUMN_IN_SCHUL_JAHR)
        .withNameColumn(SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR)
        .withOrdinalColumn(ORDER_COLUMN_IN_SCHUL_JAHR)
        .build();

    private static final LevelMappingImpl LEVEL11 = LevelMappingImpl
        .builder()
        .withName("Altersgruppe")
        .withColumn(ID_COLUMN_IN_ALTERS_GRUPPE)
        .withNameColumn(ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE)
        .build();

    private static final LevelMappingImpl LEVEL12 = LevelMappingImpl
        .builder()
        .withName(GESCHLECHT)
        .withColumn(ID_COLUMN_IN_GESCHLECHT)
        .withNameColumn(BEZEICHNUNG_COLUMN_IN_GESCHLECHT)
        .build();

    private static final LevelMappingImpl LEVEL13 = LevelMappingImpl
        .builder()
        .withName("Berufsgruppe")
        .withColumn(ID_COLUMN_IN_PERSONAL_ART)
        .withNameColumn(BEZEICHNUNG_COLUMN_IN_PERSONAL_ART)
        .build();

    private static final LevelMappingImpl LEVEL14 = LevelMappingImpl
        .builder()
        .withName(EINSCHULUNG)
        .withColumn(ID_COLUMN_IN_EINSCHULUNG)
        .withNameColumn(EINSCHULUNG_COLUMN_IN_EINSCHULUNG)
        .build();

    private static final LevelMappingImpl LEVEL15 = LevelMappingImpl
        .builder()
        .withName(KLASSENWIEDERHOLUNG)
        .withColumn(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG)
        .withNameColumn(KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG)
        .build();

    private static final LevelMappingImpl LEVEL16 = LevelMappingImpl
        .builder()
        .withName("Schulabschlüsse")
        .withColumn(ID_COLUMN_IN_SCHUL_ABSCHLUSS)
        .withNameColumn(SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS)
        .build();

    private static final LevelMappingImpl LEVEL17 = LevelMappingImpl
        .builder()
        .withName(MIGRATIONSHINTERGRUND)
        .withColumn(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND)
        .withNameColumn(MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND)
        .build();

    private static final LevelMappingImpl LEVEL18 = LevelMappingImpl
        .builder()
        .withName("Bundesland")
        .withColumn(ID_COLUMN_IN_BUNDESLAND)
        .withNameColumn(BEZEICHNUNG_COLUMN_IN_BUNDESLAND)
        .withTable(BUNDESLAND_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL19 = LevelMappingImpl
        .builder()
        .withName(WOHNLANDKREIS)
        .withColumn(ID_COLUMN_IN_WOHNORT_LANDKREIS)
        .withNameColumn(BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS)
        .withTable(WOHNORT_LANDKREIS_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL20 = LevelMappingImpl
        .builder()
        .withName("Förderbedarf")
        .withColumn(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART)
        .withNameColumn(SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART)
        .withTable(SONDERPAED_FOERDERBEDART_TABLE)
        .build();

    private static final LevelMappingImpl LEVEL21 = LevelMappingImpl
        .builder()
        .withName("Art der Förderung")
        .withColumn(ID_COLUMN_IN_FOERDERUNG_ART)
        .withNameColumn(FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART)
        .withTable(FOERDERUNG_ART_TABLE)
        .build();

    private static final HierarchyMappingImpl HIERARCHY1 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Ganztagsangebot")
        .withPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE)
        .withPrimaryKeyTable(SCHULE_TABLE)
        .withQuery(JOIN1)
        .withLevels(List.of(LEVEL1, LEVEL2))
        .build();

    private static final HierarchyMappingImpl HIERARCHY2 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Trägerschaft")
        .withPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE)
        .withPrimaryKeyTable(SCHULE_TABLE)
        .withQuery(JOIN2)
        .withLevels(List.of(LEVEL3, LEVEL4, LEVEL5, LEVEL6))
        .build();

    private static final HierarchyMappingImpl HIERARCHY3 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Art")
        .withPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE)
        .withPrimaryKeyTable(SCHULE_TABLE)
        .withQuery(JOIN3)
        .withLevels(List.of(LEVEL7, LEVEL8, LEVEL9))
        .build();

    private static final HierarchyMappingImpl HIERARCHY4 = HierarchyMappingImpl
        .builder()
        .withHasAll(false)
        .withName("Schuljahre")
        .withPrimaryKey(ID_COLUMN_IN_SCHUL_JAHR)
        .withPrimaryKeyTable(SCHUL_JAHR_TABLE)
        .withQuery(SCHUL_JAHT_TABLE_QUERY)
        .withLevels(List.of(LEVEL10))
        .build();

    private static final HierarchyMappingImpl HIERARCHY5 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Altersgruppen")
        .withName("Altersgruppen")
        .withPrimaryKey(ID_COLUMN_IN_ALTERS_GRUPPE)
        .withPrimaryKeyTable(ALTERS_GRUPPE_TABLE)
        .withQuery(ALTERS_GRUPPE_TABLE_QUERY)
        .withLevels(List.of(LEVEL11))
        .build();

    private static final HierarchyMappingImpl HIERARCHY6 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Geschlechter")
        .withName(GESCHLECHT)
        .withPrimaryKey(ID_COLUMN_IN_GESCHLECHT)
        .withPrimaryKeyTable(GESCHLECHT_TABLE)
        .withQuery(GESCHLECHT_TABLE_QUERY)
        .withLevels(List.of(LEVEL12))
        .build();

    private static final HierarchyMappingImpl HIERARCHY7 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Berufsgruppen")
        .withName("Berufsgruppen")
        .withPrimaryKey(ID_COLUMN_IN_PERSONAL_ART)
        .withPrimaryKeyTable(PERSONAL_ART_TABLE)
        .withQuery(PERSONAL_ART_TABLE_QUERY)
        .withLevels(List.of(LEVEL13))
        .build();

    private static final HierarchyMappingImpl HIERARCHY8 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(EINSCHULUNG)
        .withPrimaryKey(ID_COLUMN_IN_EINSCHULUNG)
        .withPrimaryKeyTable(EINSCHULUNG_TABLE)
        .withQuery(EINSCHULUNG_TABLE_QUERY)
        .withLevels(List.of(LEVEL14))
        .build();

    private static final HierarchyMappingImpl HIERARCHY9 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(KLASSENWIEDERHOLUNG)
        .withPrimaryKey(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG)
        .withPrimaryKeyTable(KLASSEN_WIEDERHOLUNG_TABLE)
        .withQuery(KLASSEN_WIEDERHOLUNG_TABLE_QUERY)
        .withLevels(List.of(LEVEL15))
        .build();

    private static final HierarchyMappingImpl HIERARCHY10 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName("Schulabschlüsse")
        .withPrimaryKey(ID_COLUMN_IN_SCHUL_ABSCHLUSS)
        .withPrimaryKeyTable(SCHUL_ABSCHLUSS_TABLE)
        .withQuery(SCHUL_ABSCHLUSS_TABLE_QUERY)
        .withLevels(List.of(LEVEL16))
        .build();

    private static final HierarchyMappingImpl HIERARCHY11 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(MIGRATIONSHINTERGRUND)
        .withPrimaryKey(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND)
        .withPrimaryKeyTable(MIGRATIONS_HINTERGRUND_TABLE)
        .withQuery(MIGRATIONS_HINTERGRUND_TABLE_QUERY)
        .withLevels(List.of(LEVEL17))
        .build();

    private static final HierarchyMappingImpl HIERARCHY12 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Wohnlandkreise")
        .withName(WOHNLANDKREIS)
        .withPrimaryKey(ID_COLUMN_IN_WOHNORT_LANDKREIS)
        .withPrimaryKeyTable(WOHNORT_LANDKREIS_TABLE)
        .withQuery(JOIN4)
        .withLevels(List.of(LEVEL18, LEVEL19))
        .build();

    private static final HierarchyMappingImpl HIERARCHY13 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName("Sonderpädagogische Förderung")
        .withPrimaryKey(ID_COLUMN_IN_FOERDERUNG_ART)
        .withPrimaryKeyTable(FOERDERUNG_ART_TABLE)
        .withQuery(JOIN5)
        .withLevels(List.of(LEVEL20, LEVEL21))
        .build();

    private static final StandardDimensionMappingImpl SCHULEN_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName(SCHULEN)
        .withHierarchies(List.of(HIERARCHY1, HIERARCHY2, HIERARCHY3))
        .build();

    private static final StandardDimensionMappingImpl SCHULJAHRE_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Schuljahre")
        .withHierarchies(List.of(HIERARCHY4))
        .build();

    private static final StandardDimensionMappingImpl ALTERSGRUPPEN_PERSONAL_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Altersgruppen Personal")
        .withHierarchies(List.of(HIERARCHY5))
        .build();

    private static final StandardDimensionMappingImpl GESCHLECHT_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName(GESCHLECHT)
        .withHierarchies(List.of(HIERARCHY6))
        .build();

    private static final StandardDimensionMappingImpl Berufsgruppen_Personal_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Berufsgruppen Personal")
        .withHierarchies(List.of(HIERARCHY7))
        .build();

    private static final StandardDimensionMappingImpl Einschulungen_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Einschulungen")
        .withHierarchies(List.of(HIERARCHY8))
        .build();

    private static final StandardDimensionMappingImpl Klassenwiederholung_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName(KLASSENWIEDERHOLUNG)
        .withHierarchies(List.of(HIERARCHY9))
        .build();

    private static final StandardDimensionMappingImpl Schulabschluss_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Schulabschluss")
        .withHierarchies(List.of(HIERARCHY10))
        .build();

    private static final StandardDimensionMappingImpl Migrationshintergrund_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName(MIGRATIONSHINTERGRUND)
        .withHierarchies(List.of(HIERARCHY11))
        .build();

    private static final StandardDimensionMappingImpl Wohnlandkreis_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName(WOHNLANDKREIS)
        .withHierarchies(List.of(HIERARCHY12))
        .build();

    private static final StandardDimensionMappingImpl Inklusion_DIMENSION = StandardDimensionMappingImpl
        .builder()
        .withName("Inklusion")
        .withHierarchies(List.of(HIERARCHY13))
        .build();

    private static final MeasureMappingImpl measure1 = MeasureMappingImpl.builder()
        .withName("Anzahl Schulen")
        .withColumn(ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHUELER)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure2 = MeasureMappingImpl.builder()
        .withName("Anzahl Klassen")
        .withColumn(KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure3 = MeasureMappingImpl.builder()
        .withName("Anzahl Personen")
        .withColumn(ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure4 = MeasureMappingImpl.builder()
        .withName("Anzahl Schüler:innen")
        .withColumn(ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER)
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureGroupMappingImpl CUBE1_MEASURE_GROUP = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            measure1,
            measure2
        ))
        .build();

    private static final MeasureGroupMappingImpl CUBE2_MEASURE_GROUP = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            measure3
        ))
        .build();

    private static final MeasureGroupMappingImpl CUBE3_MEASURE_GROUP = MeasureGroupMappingImpl.builder()
        .withMeasures(List.of(
            measure4
        ))
        .build();

    private static final PhysicalCubeMappingImpl CUBE1 = PhysicalCubeMappingImpl
        .builder()
        .withName("Schulen in Jena (Institutionen)")
        .withQuery(FACT_SCHULEN_TABLE_QUERY)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID_COLUMN_IN_FACT_SCHULEN).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN).build()
        ))
        .withMeasureGroups(List.of(CUBE1_MEASURE_GROUP))
        .build();

    private static final PhysicalCubeMappingImpl CUBE2 = PhysicalCubeMappingImpl
        .builder()
        .withName("Pädagogisches Personal an Jenaer Schulen")
        .withQuery(FACT_PERSONAM_TABLE_QUERY)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID_COLUMN_IN_FACT_PERSONAL).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Altersgruppe").withDimension(ALTERSGRUPPEN_PERSONAL_DIMENSION).withForeignKey(ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GESCHLECHT).withDimension(GESCHLECHT_DIMENSION).withForeignKey(GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Berufsgruppe").withDimension(Berufsgruppen_Personal_DIMENSION).withForeignKey(PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL).build()
        ))
        .withMeasureGroups(List.of(CUBE2_MEASURE_GROUP))
        .build();

    private static final PhysicalCubeMappingImpl CUBE3 = PhysicalCubeMappingImpl
        .builder()
        .withName("Schüler:innen an Jenaer Schulen")
        .withQuery(FACT_SCHUELER_TABLE_QUERY)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GESCHLECHT).withDimension(GESCHLECHT_DIMENSION).withForeignKey(GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(WOHNLANDKREIS).withDimension(Wohnlandkreis_DIMENSION).withForeignKey(WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(EINSCHULUNG).withDimension(Einschulungen_DIMENSION).withForeignKey(EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Schulabschluss").withDimension(Schulabschluss_DIMENSION).withForeignKey(SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(KLASSENWIEDERHOLUNG).withDimension(Klassenwiederholung_DIMENSION).withForeignKey(KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(MIGRATIONSHINTERGRUND).withDimension(Migrationshintergrund_DIMENSION).withForeignKey(MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Sonderpädagogische Förderung").withDimension(Inklusion_DIMENSION).withForeignKey(FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER).build()

        ))
        .withMeasureGroups(List.of(CUBE3_MEASURE_GROUP))
        .build();

    private static final CatalogMappingImpl
        CATALOG = CatalogMappingImpl.builder()
        .withName(CATALOG_NAME)
        .withCubes(List.of(CUBE1, CUBE2, CUBE3))
        .withDbSchemas(List.of(DatabaseSchemaMappingImpl.builder()
                .withName(CATALOG_NAME)
                .withTables(List.of(SCHULE_TABLE, GANZTAGS_ART_TABLE, TRAEGER_TABLE, TRAEGER_ART_TABLE,
                        TRAEGER_KATEGORIE_TABLE, SCHEDULE_ART_TABLE, SCHUL_JAHR_TABLE,
                        ALTERS_GRUPPE_TABLE, GESCHLECHT_TABLE, EINSCHULUNG_TABLE,
                        KLASSEN_WIEDERHOLUNG_TABLE, SCHUL_ABSCHLUSS_TABLE, MIGRATIONS_HINTERGRUND_TABLE,
                        WOHNORT_LANDKREIS_TABLE, SCHUL_ART_TABLE, SCHUL_KATEGORIE_TABLE, FOERDERUNG_ART_TABLE,
                        PERSONAL_ART_TABLE, BUNDESLAND_TABLE, SONDERPAED_FOERDERBEDART_TABLE, FACT_SCHULEN_TABLE,
                        FACT_PERSONAM_TABLE, FACT_SCHUELER_TABLE))
                .build()))

        .build();

    @Override
    public CatalogMapping get() {
        return CATALOG;
    }

}
