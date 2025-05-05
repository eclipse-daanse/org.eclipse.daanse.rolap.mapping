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

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.COMPLEX, number = "99.1.1", source = Source.EMF, group = "Full Examples") // NOSONAR

public class CatalogSupplier implements CatalogMappingSupplier {

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

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        // id,schul_nummer,schul_name,traeger_id,schul_art_id,ganztags_art_id
        // INTEGER,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
        Column ID_COLUMN_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SCHULE_TABLE.setName("id");
        ID_COLUMN_IN_SCHULE_TABLE.setId("_col_schule_id");
        ID_COLUMN_IN_SCHULE_TABLE.setType(ColumnType.INTEGER);

        Column SCHUL_NAME_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_NAME_IN_SCHULE_TABLE.setName(SCHUL_NAME);
        SCHUL_NAME_IN_SCHULE_TABLE.setId("_col_schule_schul_name");
        SCHUL_NAME_IN_SCHULE_TABLE.setType(ColumnType.VARCHAR);

        Column SCHUL_NUMMER_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_NUMMER_IN_SCHULE_TABLE.setName(SCHUL_NUMMER);
        SCHUL_NUMMER_IN_SCHULE_TABLE.setId("_col_schule_schul_nummer");
        SCHUL_NUMMER_IN_SCHULE_TABLE.setType(ColumnType.INTEGER);

        Column GANZTAGS_ART_ID_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        GANZTAGS_ART_ID_IN_SCHULE_TABLE.setName("ganztags_art_id");
        GANZTAGS_ART_ID_IN_SCHULE_TABLE.setId("_col_schule_ganztags_art_id");
        GANZTAGS_ART_ID_IN_SCHULE_TABLE.setType(ColumnType.INTEGER);

        Column TRAEGER_ID_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_ID_IN_SCHULE_TABLE.setName("traeger_id");
        TRAEGER_ID_IN_SCHULE_TABLE.setId("_col_schule_traeger_id");
        TRAEGER_ID_IN_SCHULE_TABLE.setType(ColumnType.INTEGER);

        Column SCHUL_ART_ID_IN_SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_ART_ID_IN_SCHULE_TABLE.setName("schul_art_id");
        SCHUL_ART_ID_IN_SCHULE_TABLE.setId("_col_schule_schul_art_id");
        SCHUL_ART_ID_IN_SCHULE_TABLE.setType(ColumnType.INTEGER);

        PhysicalTable SCHULE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHULE_TABLE.setName(SCHULE);
        SCHULE_TABLE.setId("_tab_schule");
        SCHULE_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_SCHULE_TABLE, SCHUL_NAME_IN_SCHULE_TABLE, SCHUL_NUMMER_IN_SCHULE_TABLE,
                        GANZTAGS_ART_ID_IN_SCHULE_TABLE, TRAEGER_ID_IN_SCHULE_TABLE, SCHUL_ART_ID_IN_SCHULE_TABLE));

        // id,schul_umfang
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_GANZTAGS_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_GANZTAGS_ART.setName("id");
        ID_COLUMN_IN_GANZTAGS_ART.setId("_col_ganztags_art_id");
        ID_COLUMN_IN_GANZTAGS_ART.setType(ColumnType.INTEGER);

        Column SCHUL_UMFANG_IN_GANZTAGS_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_UMFANG_IN_GANZTAGS_ART.setName("schul_umfang");
        SCHUL_UMFANG_IN_GANZTAGS_ART.setId("_col_ganztags_art_schul_umfang");
        SCHUL_UMFANG_IN_GANZTAGS_ART.setType(ColumnType.VARCHAR);

        PhysicalTable GANZTAGS_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        GANZTAGS_ART_TABLE.setName("ganztags_art");
        GANZTAGS_ART_TABLE.setId("_tab_ganztags_art");
        GANZTAGS_ART_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_GANZTAGS_ART, SCHUL_UMFANG_IN_GANZTAGS_ART));

        // id,traeger_name,traeger_art_id
        // INTEGER,VARCHAR,INTEGER
        Column ID_COLUMN_IN_TRAEGER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_TRAEGER_TABLE.setName("id");
        ID_COLUMN_IN_TRAEGER_TABLE.setId("_col_traeger_id");
        ID_COLUMN_IN_TRAEGER_TABLE.setType(ColumnType.INTEGER);

        Column TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE.setName("traeger_name");
        TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE.setId("_col_traeger_traeger_name");
        TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE.setType(ColumnType.VARCHAR);

        Column TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE.setName("traeger_art_id");
        TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE.setId("_col_traeger_traeger_art_id");
        TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE.setType(ColumnType.INTEGER);

        PhysicalTable TRAEGER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TRAEGER_TABLE.setName("traeger");
        TRAEGER_TABLE.setId("_tab_traeger");
        TRAEGER_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_TRAEGER_TABLE, TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE,
                TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE));

        // id,traeger_art,traeger_kat_id
        // INTEGER,VARCHAR,VARCHAR
        Column ID_COLUMN_IN_TRAEGER_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_TRAEGER_ART.setName("id");
        ID_COLUMN_IN_TRAEGER_ART.setId("_col_traeger_art_id");
        ID_COLUMN_IN_TRAEGER_ART.setType(ColumnType.INTEGER);

        Column TRAEGER_ART_COLUMN_IN_TRAEGER_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_ART_COLUMN_IN_TRAEGER_ART.setName(TRAEGER_ART);
        TRAEGER_ART_COLUMN_IN_TRAEGER_ART.setId("_col_traeger_art_traeger_art");
        TRAEGER_ART_COLUMN_IN_TRAEGER_ART.setType(ColumnType.VARCHAR);

        Column TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART.setName("traeger_kat_id");
        TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART.setId("_col_traeger_art_traeger_kat_id");
        TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART.setType(ColumnType.VARCHAR);

        PhysicalTable TRAEGER_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TRAEGER_ART_TABLE.setName(TRAEGER_ART);
        TRAEGER_ART_TABLE.setId("_tab_traeger_art");
        TRAEGER_ART_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_TRAEGER_ART, TRAEGER_ART_COLUMN_IN_TRAEGER_ART,
                TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART));

        // id,traeger_kategorie
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_TRAEGER_KATEGORIE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_TRAEGER_KATEGORIE.setName("id");
        ID_COLUMN_IN_TRAEGER_KATEGORIE.setId("_col_traeger_kategorie_id");
        ID_COLUMN_IN_TRAEGER_KATEGORIE.setType(ColumnType.INTEGER);

        Column TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE.setName(TRAEGER_KATEGORIE);
        TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE.setId("_col_traeger_kategorie_traeger_kategorie");
        TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE.setType(ColumnType.VARCHAR);

        PhysicalTable TRAEGER_KATEGORIE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TRAEGER_KATEGORIE_TABLE.setName(TRAEGER_KATEGORIE);
        TRAEGER_KATEGORIE_TABLE.setId("_tab_traeger_kategorie");
        TRAEGER_KATEGORIE_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_TRAEGER_KATEGORIE, TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column ID_IN_SCHEDULE_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_IN_SCHEDULE_ART.setName("id");
        ID_IN_SCHEDULE_ART.setId("_col_schul_art_id");
        ID_IN_SCHEDULE_ART.setType(ColumnType.INTEGER);

        Column SCHUL_KATEGORIE_IN_SCHEDULE_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_KATEGORIE_IN_SCHEDULE_ART.setName("schul_kategorie_id");
        SCHUL_KATEGORIE_IN_SCHEDULE_ART.setId("_col_schul_art_schul_kategorie_id");
        SCHUL_KATEGORIE_IN_SCHEDULE_ART.setType(ColumnType.INTEGER);

        PhysicalTable SCHEDULE_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHEDULE_ART_TABLE.setName("schul_art");
        SCHEDULE_ART_TABLE.setId("_tab_schul_art");
        SCHEDULE_ART_TABLE.getColumns().addAll(List.of(ID_IN_SCHEDULE_ART, SCHUL_KATEGORIE_IN_SCHEDULE_ART));

        // "id","schul_jahr","order"
        // ColumnType.INTEGER,ColumnType.VARCHAR,ColumnType.INTEGER
        Column ID_COLUMN_IN_SCHUL_JAHR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SCHUL_JAHR.setName("id");
        ID_COLUMN_IN_SCHUL_JAHR.setId("_col_schul_jahr_id");
        ID_COLUMN_IN_SCHUL_JAHR.setType(ColumnType.INTEGER);

        Column SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR.setName(SCHUL_JAHR);
        SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR.setId("_col_schul_jahr_schul_jahr");
        SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR.setType(ColumnType.VARCHAR);

        Column ORDER_COLUMN_IN_SCHUL_JAHR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ORDER_COLUMN_IN_SCHUL_JAHR.setName("order");
        ORDER_COLUMN_IN_SCHUL_JAHR.setId("_col_schul_jahr_order");
        ORDER_COLUMN_IN_SCHUL_JAHR.setType(ColumnType.INTEGER);

        PhysicalTable SCHUL_JAHR_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHUL_JAHR_TABLE.setName(SCHUL_JAHR);
        SCHUL_JAHR_TABLE.setId("_tab_schul_jahr");
        SCHUL_JAHR_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_SCHUL_JAHR, SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR, ORDER_COLUMN_IN_SCHUL_JAHR));

        // id,altersgruppe
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_ALTERS_GRUPPE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_ALTERS_GRUPPE.setName("id");
        ID_COLUMN_IN_ALTERS_GRUPPE.setId("_col_alters_gruppe_id");
        ID_COLUMN_IN_ALTERS_GRUPPE.setType(ColumnType.INTEGER);

        Column ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE.setName("altersgruppe");
        ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE.setId("_col_alters_gruppe_altersgruppe");
        ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE.setType(ColumnType.INTEGER);

        PhysicalTable ALTERS_GRUPPE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        ALTERS_GRUPPE_TABLE.setName("alters_gruppe");
        ALTERS_GRUPPE_TABLE.setId("_col_alters_gruppe");
        ALTERS_GRUPPE_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_ALTERS_GRUPPE, ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column ID_COLUMN_IN_GESCHLECHT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_GESCHLECHT.setName("id");
        ID_COLUMN_IN_GESCHLECHT.setId("_col_geschlecht_id");
        ID_COLUMN_IN_GESCHLECHT.setType(ColumnType.INTEGER);

        Column BEZEICHNUNG_COLUMN_IN_GESCHLECHT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        BEZEICHNUNG_COLUMN_IN_GESCHLECHT.setName(BEZEICHNUNG);
        BEZEICHNUNG_COLUMN_IN_GESCHLECHT.setId("_col_geschlecht_bezeichnung");
        BEZEICHNUNG_COLUMN_IN_GESCHLECHT.setType(ColumnType.INTEGER);

        PhysicalTable GESCHLECHT_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        GESCHLECHT_TABLE.setName("geschlecht");
        GESCHLECHT_TABLE.setId("_tab_geschlecht");
        GESCHLECHT_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_GESCHLECHT, BEZEICHNUNG_COLUMN_IN_GESCHLECHT));

        Column ID_COLUMN_IN_EINSCHULUNG = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_EINSCHULUNG.setName("id");
        ID_COLUMN_IN_EINSCHULUNG.setId("_col_einschulung_id");
        ID_COLUMN_IN_EINSCHULUNG.setType(ColumnType.INTEGER);

        Column EINSCHULUNG_COLUMN_IN_EINSCHULUNG = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        EINSCHULUNG_COLUMN_IN_EINSCHULUNG.setName(EINSCHULUNG2);
        EINSCHULUNG_COLUMN_IN_EINSCHULUNG.setId("_col_einschulung_einschulung");
        EINSCHULUNG_COLUMN_IN_EINSCHULUNG.setType(ColumnType.VARCHAR);

        PhysicalTable EINSCHULUNG_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        EINSCHULUNG_TABLE.setName(EINSCHULUNG2);
        EINSCHULUNG_TABLE.setId("_tab_einschulung");
        EINSCHULUNG_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_EINSCHULUNG, EINSCHULUNG_COLUMN_IN_EINSCHULUNG));

        // id,klassenwiederholung
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setName("id");
        ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setId("_col_klassen_wiederholung_id");
        ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setType(ColumnType.INTEGER);
        Column KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG = RolapMappingFactory.eINSTANCE
                .createPhysicalColumn();
        KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setName("klassenwiederholung");
        KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setId("_col_klassen_wiederholung_klassenwiederholung");
        KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG.setType(ColumnType.INTEGER);

        PhysicalTable KLASSEN_WIEDERHOLUNG_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        KLASSEN_WIEDERHOLUNG_TABLE.setName("klassen_wiederholung");
        KLASSEN_WIEDERHOLUNG_TABLE.setId("_tab_klassen_wiederholung");
        KLASSEN_WIEDERHOLUNG_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG, KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG));

        // id,schulabschluss
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_SCHUL_ABSCHLUSS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SCHUL_ABSCHLUSS.setName("id");
        ID_COLUMN_IN_SCHUL_ABSCHLUSS.setId("_col_schul_abschluss_id");
        ID_COLUMN_IN_SCHUL_ABSCHLUSS.setType(ColumnType.INTEGER);

        Column SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS.setName("schulabschluss");
        SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS.setId("_col_schul_abschluss_schulabschluss");
        SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS.setType(ColumnType.VARCHAR);

        PhysicalTable SCHUL_ABSCHLUSS_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHUL_ABSCHLUSS_TABLE.setName("schul_abschluss");
        SCHUL_ABSCHLUSS_TABLE.setId("_tab_schul_abschluss");
        SCHUL_ABSCHLUSS_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_SCHUL_ABSCHLUSS, SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS));

        // id,migrations_hintergrund
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_MIGRATIONS_HINTERGRUND = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_MIGRATIONS_HINTERGRUND.setName("id");
        ID_COLUMN_IN_MIGRATIONS_HINTERGRUND.setId("_col_migrations_hintergrund_id");
        ID_COLUMN_IN_MIGRATIONS_HINTERGRUND.setType(ColumnType.INTEGER);

        Column MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND = RolapMappingFactory.eINSTANCE
                .createPhysicalColumn();
        MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND.setName(MIGRATIONS_HINTERGRUND);
        MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND.setId("_col_migrations_hintergrund_migrations_hintergrund");
        MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND.setType(ColumnType.VARCHAR);

        PhysicalTable MIGRATIONS_HINTERGRUND_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        MIGRATIONS_HINTERGRUND_TABLE.setName(MIGRATIONS_HINTERGRUND);
        MIGRATIONS_HINTERGRUND_TABLE.setId("_tab_migrations_hintergrund");
        MIGRATIONS_HINTERGRUND_TABLE.getColumns().addAll(
                List.of(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND, MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND));

        // id,kuerzel,bezeichnung,bundesland_id
        // INTEGER,VARCHAR,VARCHAR,INTEGER
        Column ID_COLUMN_IN_WOHNORT_LANDKREIS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_WOHNORT_LANDKREIS.setName("id");
        ID_COLUMN_IN_WOHNORT_LANDKREIS.setId("_col_wohnort_landkreis_id");
        ID_COLUMN_IN_WOHNORT_LANDKREIS.setType(ColumnType.INTEGER);

        Column BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS.setName(BEZEICHNUNG);
        BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS.setId("_col_wohnort_landkreis_bezeichnung");
        BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS.setType(ColumnType.VARCHAR);

        Column BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS.setName("bundesland_id");
        BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS.setId("_col_wohnort_landkreis_bundesland_id");
        BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS.setType(ColumnType.INTEGER);

        PhysicalTable WOHNORT_LANDKREIS_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        WOHNORT_LANDKREIS_TABLE.setName(WOHNORT_LANDKREIS);
        WOHNORT_LANDKREIS_TABLE.setId("_tab_wohnort_landkreis");
        WOHNORT_LANDKREIS_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_WOHNORT_LANDKREIS,
                BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS, BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column ID_COLUMN_IN_SCHUL_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SCHUL_ART.setName("id");
        ID_COLUMN_IN_SCHUL_ART.setId("_col_schul_art_id");
        ID_COLUMN_IN_SCHUL_ART.setType(ColumnType.INTEGER);

        Column SCHULART_NAME_COLUMN_IN_SCHUL_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHULART_NAME_COLUMN_IN_SCHUL_ART.setName("schulart_name");
        SCHULART_NAME_COLUMN_IN_SCHUL_ART.setId("_col_schul_art_schulart_name");
        SCHULART_NAME_COLUMN_IN_SCHUL_ART.setType(ColumnType.VARCHAR);

        PhysicalTable SCHUL_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHUL_ART_TABLE.setName("schul_art");
        SCHUL_ART_TABLE.setId("_tab_schul_art");
        SCHUL_ART_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_SCHUL_ART, SCHULART_NAME_COLUMN_IN_SCHUL_ART));

        // id,schul_kategorie_name
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_SCHUL_KATEGORIE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SCHUL_KATEGORIE.setName("id");
        ID_COLUMN_IN_SCHUL_KATEGORIE.setId("_col_schul_kategorie_id");
        ID_COLUMN_IN_SCHUL_KATEGORIE.setType(ColumnType.INTEGER);

        Column SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE.setName("schul_kategorie_name");
        SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE.setId("_col_schul_kategorie_schul_kategorie_name");
        SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE.setType(ColumnType.VARCHAR);

        PhysicalTable SCHUL_KATEGORIE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SCHUL_KATEGORIE_TABLE.setName("schul_kategorie");
        SCHUL_KATEGORIE_TABLE.setId("_tab_schul_kategorie");
        SCHUL_KATEGORIE_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_SCHUL_KATEGORIE, SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE));

        // id,foerderung_art,sp_foerderbedarf_id
        // INTEGER,VARCHAR,INTEGER,
        Column ID_COLUMN_IN_FOERDERUNG_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_FOERDERUNG_ART.setName("id");
        ID_COLUMN_IN_FOERDERUNG_ART.setId("_col_foerderung_art_id");
        ID_COLUMN_IN_FOERDERUNG_ART.setType(ColumnType.INTEGER);

        Column FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART.setName(FOERDERUNG_ART);
        FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART.setId("_col_foerderung_art_foerderung_art");
        FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART.setType(ColumnType.VARCHAR);

        Column SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART.setName("sp_foerderbedarf_id");
        SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART.setId("_col_foerderung_art_sp_foerderbedarf_id");
        SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART.setType(ColumnType.INTEGER);

        PhysicalTable FOERDERUNG_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        FOERDERUNG_ART_TABLE.setName(FOERDERUNG_ART);
        FOERDERUNG_ART_TABLE.setId("_tab_foerderung_art");
        FOERDERUNG_ART_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_FOERDERUNG_ART,
                FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART, SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART));

        // id,bezeichnung,,,,,,,,,,,,,,,,,id,bezeichnung
        // INTEGER,VARCHAR,,,,,,,,,,,,,,,,,INTEGER,VARCHAR
        Column ID_COLUMN_IN_PERSONAL_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_PERSONAL_ART.setName("id");
        ID_COLUMN_IN_PERSONAL_ART.setId("_col_personal_art_id");
        ID_COLUMN_IN_PERSONAL_ART.setType(ColumnType.INTEGER);

        Column BEZEICHNUNG_COLUMN_IN_PERSONAL_ART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        BEZEICHNUNG_COLUMN_IN_PERSONAL_ART.setName(BEZEICHNUNG);
        BEZEICHNUNG_COLUMN_IN_PERSONAL_ART.setId("_col_personal_art_bezeichnung");
        BEZEICHNUNG_COLUMN_IN_PERSONAL_ART.setType(ColumnType.VARCHAR);

        PhysicalTable PERSONAL_ART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        PERSONAL_ART_TABLE.setName("personal_art");
        PERSONAL_ART_TABLE.setId("_tab_personal_art");
        PERSONAL_ART_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_PERSONAL_ART, BEZEICHNUNG_COLUMN_IN_PERSONAL_ART));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column ID_COLUMN_IN_BUNDESLAND = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_BUNDESLAND.setName("id");
        ID_COLUMN_IN_BUNDESLAND.setId("_col_bundesland_id");
        ID_COLUMN_IN_BUNDESLAND.setType(ColumnType.INTEGER);

        Column BEZEICHNUNG_COLUMN_IN_BUNDESLAND = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        BEZEICHNUNG_COLUMN_IN_BUNDESLAND.setName(BEZEICHNUNG);
        BEZEICHNUNG_COLUMN_IN_BUNDESLAND.setId("_col_bundesland_bezeichnung");
        BEZEICHNUNG_COLUMN_IN_BUNDESLAND.setType(ColumnType.VARCHAR);

        PhysicalTable BUNDESLAND_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        BUNDESLAND_TABLE.setName("bundesland");
        BUNDESLAND_TABLE.setId("_tab_bundesland");
        BUNDESLAND_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_BUNDESLAND, BEZEICHNUNG_COLUMN_IN_BUNDESLAND));

        Column ID_COLUMN_IN_SONDERPAED_FOERDERBEDART = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_SONDERPAED_FOERDERBEDART.setName("id");
        ID_COLUMN_IN_SONDERPAED_FOERDERBEDART.setId("_col_sonderpaed_foerderbedarf_id");
        ID_COLUMN_IN_SONDERPAED_FOERDERBEDART.setType(ColumnType.INTEGER);

        Column SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART = RolapMappingFactory.eINSTANCE
                .createPhysicalColumn();
        SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART.setName("sonderpaed_bedarf");
        SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART.setId("_col_sonderpaed_foerderbedarf_sonderpaed_bedarf");
        SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART.setType(ColumnType.VARCHAR);

        PhysicalTable SONDERPAED_FOERDERBEDART_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        SONDERPAED_FOERDERBEDART_TABLE.setName("sonderpaed_foerderbedarf");
        SONDERPAED_FOERDERBEDART_TABLE.setId("_tab_sonderpaed_foerderbedarf");
        SONDERPAED_FOERDERBEDART_TABLE.getColumns().addAll(
                List.of(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART, SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART));

        // schule_id,schul_jahr_id,anzahl_schulen,anzahl_klassen
        // INTEGER,INTEGER,INTEGER,INTEGER
        Column SCHULE_ID_COLUMN_IN_FACT_SCHULEN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHULE_ID_COLUMN_IN_FACT_SCHULEN.setName(SCHULE_ID);
        SCHULE_ID_COLUMN_IN_FACT_SCHULEN.setId("_col_fact_schulen_schule_id");
        SCHULE_ID_COLUMN_IN_FACT_SCHULEN.setType(ColumnType.INTEGER);

        Column SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN.setName(SCHUL_JAHR_ID);
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN.setId("_col_fact_schulen_schul_jahr_id");
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN.setType(ColumnType.INTEGER);

        Column ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN.setName("anzahl_schulen");
        ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN.setId("_col_fact_schulen_anzahl_schulen");
        ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN.setType(ColumnType.INTEGER);

        Column ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN.setName("anzahl_klassen");
        ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN.setId("_col_fact_schulen_anzahl_klassen");
        ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN.setType(ColumnType.INTEGER);

        PhysicalTable FACT_SCHULEN_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        FACT_SCHULEN_TABLE.setName("fact_schulen");
        FACT_SCHULEN_TABLE.setId("_tab_fact_schulen");
        FACT_SCHULEN_TABLE.getColumns()
                .addAll(List.of(SCHULE_ID_COLUMN_IN_FACT_SCHULEN, SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN, ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN, ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN));

        // schule_id,schul_jahr_id,alters_gruppe_id,geschlecht_id,personal_art_id,anzahl_personen
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column SCHULE_ID_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHULE_ID_COLUMN_IN_FACT_PERSONAL.setName(SCHULE_ID);
        SCHULE_ID_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_schule_id");
        SCHULE_ID_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        Column SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL.setName(SCHUL_JAHR_ID);
        SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_schul_jahr_id");
        SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        Column ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL.setName("alters_gruppe_id");
        ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_alters_gruppe_id");
        ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        Column GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL.setName("geschlecht_id");
        GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_geschlecht_id");
        GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        Column PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL.setName("personal_art_id");
        PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_personal_art_id");
        PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        Column ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL.setName("anzahl_personen");
        ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL.setId("_col_fact_personal_anzahl_personen");
        ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL.setType(ColumnType.INTEGER);

        PhysicalTable FACT_PERSONAM_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        FACT_PERSONAM_TABLE.setName("fact_personal");
        FACT_PERSONAM_TABLE.setId("_tab_fact_personal");
        FACT_PERSONAM_TABLE.getColumns()
                .addAll(List.of(SCHULE_ID_COLUMN_IN_FACT_PERSONAL, SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL,
                        ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL, GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL,
                        PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL, ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL));

        // schule_id,schul_jahr_id,geschlecht_id,wohn_lk_id,einschulung_id,schul_abschluss_id,klassen_wdh,migrations_hg_id,foerder_art_id,anzahl_schueler
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column SCHULE_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHULE_ID_COLUMN_IN_FACT_SCHUELER.setName(SCHULE_ID);
        SCHULE_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_schule_id");
        SCHULE_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER.setName(SCHUL_JAHR_ID);
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_schul_jahr_id");
        SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER.setName("geschlecht_id");
        GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_geschlecht_id");
        GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER.setName("wohn_lk_id");
        WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_wohn_lk_id");
        WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER.setName("einschulung_id");
        EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_einschulung_id");
        EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER.setName("schul_abschluss_id");
        SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_schul_abschluss_id");
        SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER.setName("klassen_wdh");
        KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_klassen_wdh");
        KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER.setName("migrations_hg_id");
        MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_migrations_hg_id");
        MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER.setName("foerder_art_id");
        FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_foerder_art_id");
        FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        Column ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER.setName("anzahl_schueler");
        ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER.setId("_col_fact_schueler_anzahl_schueler");
        ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER.setType(ColumnType.INTEGER);

        PhysicalTable FACT_SCHUELER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        FACT_SCHUELER_TABLE.setName("fact_schueler");
        FACT_SCHUELER_TABLE.setId("_tab_fact_schueler");
        FACT_SCHUELER_TABLE.getColumns()
                .addAll(List.of(SCHULE_ID_COLUMN_IN_FACT_SCHUELER, SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER,
                        GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER, WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER,
                        EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER, SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER,
                        KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER, MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER,
                        FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER, ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER));

        databaseSchema.getTables()
                .addAll(List.of(SCHULE_TABLE, GANZTAGS_ART_TABLE, TRAEGER_TABLE, TRAEGER_ART_TABLE,
                        TRAEGER_KATEGORIE_TABLE, SCHEDULE_ART_TABLE, SCHUL_JAHR_TABLE, ALTERS_GRUPPE_TABLE,
                        GESCHLECHT_TABLE, EINSCHULUNG_TABLE, KLASSEN_WIEDERHOLUNG_TABLE, SCHUL_ABSCHLUSS_TABLE,
                        MIGRATIONS_HINTERGRUND_TABLE, WOHNORT_LANDKREIS_TABLE, SCHUL_ART_TABLE, SCHUL_KATEGORIE_TABLE,
                        FOERDERUNG_ART_TABLE, PERSONAL_ART_TABLE, BUNDESLAND_TABLE, SONDERPAED_FOERDERBEDART_TABLE,
                        FACT_SCHULEN_TABLE, FACT_PERSONAM_TABLE, FACT_SCHUELER_TABLE));

        TableQuery SCHEDULE_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SCHEDULE_TABLE_QUERY.setId("_SCHEDULE_TABLE_QUERY");
        SCHEDULE_TABLE_QUERY.setTable(SCHULE_TABLE);

        TableQuery GANZTAGS_ART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        GANZTAGS_ART_TABLE_QUERY.setId("_query_GANZTAGS_ART_TABLE_QUERY");
        GANZTAGS_ART_TABLE_QUERY.setTable(GANZTAGS_ART_TABLE);

        TableQuery TRAEGER_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TRAEGER_TABLE_QUERY.setId("_query_TRAEGER_TABLE_QUERY");
        TRAEGER_TABLE_QUERY.setTable(TRAEGER_TABLE);

        TableQuery TRAEGER_ART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TRAEGER_ART_TABLE_QUERY.setId("_query_TRAEGER_ART_TABLE_QUERY");
        TRAEGER_ART_TABLE_QUERY.setTable(TRAEGER_ART_TABLE);

        TableQuery TRAEGER_KATEGORIE_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TRAEGER_KATEGORIE_TABLE_QUERY.setId("_query_TRAEGER_KATEGORIE_TABLE_QUERY");
        TRAEGER_KATEGORIE_TABLE_QUERY.setTable(TRAEGER_KATEGORIE_TABLE);

        TableQuery SCHEDULE_ART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SCHEDULE_ART_TABLE_QUERY.setId("_query_SCHEDULE_ART_TABLE_QUERY");
        SCHEDULE_ART_TABLE_QUERY.setTable(SCHEDULE_ART_TABLE);

        TableQuery SCHEDULE_KATEGORIE_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SCHEDULE_KATEGORIE_TABLE_QUERY.setId("_query_SCHEDULE_KATEGORIE_TABLE_QUERY");
        SCHEDULE_KATEGORIE_TABLE_QUERY.setTable(SCHUL_KATEGORIE_TABLE);

        TableQuery SCHUL_JAHT_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SCHUL_JAHT_TABLE_QUERY.setId("_query_SCHUL_JAHT_TABLE_QUERY");
        SCHUL_JAHT_TABLE_QUERY.setTable(SCHUL_JAHR_TABLE);

        TableQuery ALTERS_GRUPPE_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        ALTERS_GRUPPE_TABLE_QUERY.setId("_query_ALTERS_GRUPPE_TABLE_QUERY");
        ALTERS_GRUPPE_TABLE_QUERY.setTable(ALTERS_GRUPPE_TABLE);

        TableQuery GESCHLECHT_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        GESCHLECHT_TABLE_QUERY.setId("_query_GESCHLECHT_TABLE_QUERY");
        GESCHLECHT_TABLE_QUERY.setTable(GESCHLECHT_TABLE);

        TableQuery PERSONAL_ART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        PERSONAL_ART_TABLE_QUERY.setId("_query_PERSONAL_ART_TABLE_QUERY");
        PERSONAL_ART_TABLE_QUERY.setTable(PERSONAL_ART_TABLE);

        TableQuery EINSCHULUNG_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        EINSCHULUNG_TABLE_QUERY.setId("_query_EINSCHULUNG_TABLE_QUERY");
        EINSCHULUNG_TABLE_QUERY.setTable(EINSCHULUNG_TABLE);

        TableQuery KLASSEN_WIEDERHOLUNG_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        KLASSEN_WIEDERHOLUNG_TABLE_QUERY.setId("_query_KLASSEN_WIEDERHOLUNG_TABLE_QUERY");
        KLASSEN_WIEDERHOLUNG_TABLE_QUERY.setTable(KLASSEN_WIEDERHOLUNG_TABLE);

        TableQuery SCHUL_ABSCHLUSS_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SCHUL_ABSCHLUSS_TABLE_QUERY.setId("_query_SCHUL_ABSCHLUSS_TABLE_QUERY");
        SCHUL_ABSCHLUSS_TABLE_QUERY.setTable(SCHUL_ABSCHLUSS_TABLE);

        TableQuery MIGRATIONS_HINTERGRUND_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        MIGRATIONS_HINTERGRUND_TABLE_QUERY.setId("_query_MIGRATIONS_HINTERGRUND_TABLE_QUERY");
        MIGRATIONS_HINTERGRUND_TABLE_QUERY.setTable(MIGRATIONS_HINTERGRUND_TABLE);

        TableQuery WOHNORT_LANDKREIS_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        WOHNORT_LANDKREIS_TABLE_QUERY.setId("_query_WOHNORT_LANDKREIS_TABLE_QUERY");
        WOHNORT_LANDKREIS_TABLE_QUERY.setTable(WOHNORT_LANDKREIS_TABLE);

        TableQuery BUNDESLAND_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        BUNDESLAND_TABLE_QUERY.setId("_query_BUNDESLAND_TABLE_QUERY");
        BUNDESLAND_TABLE_QUERY.setTable(BUNDESLAND_TABLE);

        TableQuery FOERDERUNG_ART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        FOERDERUNG_ART_TABLE_QUERY.setId("_query_FOERDERUNG_ART_TABLE_QUERY");
        FOERDERUNG_ART_TABLE_QUERY.setTable(FOERDERUNG_ART_TABLE);

        TableQuery SONDERPAED_FOERDERBEDART_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        SONDERPAED_FOERDERBEDART_TABLE_QUERY.setId("_query_SONDERPAED_FOERDERBEDART_TABLE_QUERY");
        SONDERPAED_FOERDERBEDART_TABLE_QUERY.setTable(SONDERPAED_FOERDERBEDART_TABLE);

        TableQuery FACT_SCHULEN_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        FACT_SCHULEN_TABLE_QUERY.setId("_query_FACT_SCHULEN_TABLE_QUERY");
        FACT_SCHULEN_TABLE_QUERY.setTable(FACT_SCHULEN_TABLE);

        TableQuery FACT_PERSONAL_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        FACT_PERSONAL_TABLE_QUERY.setId("_query_FACT_PERSONAM_TABLE_QUERY");
        FACT_PERSONAL_TABLE_QUERY.setTable(FACT_PERSONAM_TABLE);

        TableQuery FACT_SCHUELER_TABLE_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        FACT_SCHUELER_TABLE_QUERY.setId("_query_FACT_SCHUELER_TABLE_QUERY");
        FACT_SCHUELER_TABLE_QUERY.setTable(FACT_SCHUELER_TABLE);

        JoinedQueryElement JOIN1L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN1L.setKey(GANZTAGS_ART_ID_IN_SCHULE_TABLE);
        JOIN1L.setQuery(SCHEDULE_TABLE_QUERY);

        JoinedQueryElement JOIN1R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN1R.setKey(ID_COLUMN_IN_GANZTAGS_ART);
        JOIN1R.setQuery(GANZTAGS_ART_TABLE_QUERY);

        JoinQuery JOIN1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN1.setId("_join_JOIN1");
        JOIN1.setLeft(JOIN1L);
        JOIN1.setRight(JOIN1R);

        JoinedQueryElement JOIN2_1_1L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2_1_1L.setKey(TRAEGER_KAT_ID_COLUMN_IN_TRAEGER_ART);
        JOIN2_1_1L.setQuery(TRAEGER_ART_TABLE_QUERY);

        JoinedQueryElement JOIN2_1_1R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2_1_1R.setKey(ID_COLUMN_IN_TRAEGER_KATEGORIE);
        JOIN2_1_1R.setQuery(TRAEGER_KATEGORIE_TABLE_QUERY);

        JoinQuery JOIN2_1_1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN2_1_1.setId("_join_JOIN2_1_1");
        JOIN2_1_1.setLeft(JOIN2_1_1L);
        JOIN2_1_1.setRight(JOIN2_1_1R);

        JoinedQueryElement JOIN2_1L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2_1L.setKey(TRAEGER_ART_ID_COLUMN_IN_TRAEGER_TABLE);
        JOIN2_1L.setQuery(TRAEGER_TABLE_QUERY);

        JoinedQueryElement JOIN2_1R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2_1R.setKey(ID_COLUMN_IN_TRAEGER_ART);
        JOIN2_1R.setQuery(JOIN2_1_1);

        JoinQuery JOIN2_1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN2_1.setId("_join_JOIN2_1");
        JOIN2_1.setLeft(JOIN2_1L);
        JOIN2_1.setRight(JOIN2_1R);

        JoinedQueryElement JOIN2L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2L.setKey(TRAEGER_ID_IN_SCHULE_TABLE);
        JOIN2L.setQuery(SCHEDULE_TABLE_QUERY);

        JoinedQueryElement JOIN2R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN2R.setKey(ID_COLUMN_IN_TRAEGER_TABLE);
        JOIN2R.setQuery(JOIN2_1);

        JoinQuery JOIN2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN2.setId("_join_JOIN2");
        JOIN2.setLeft(JOIN2L);
        JOIN2.setRight(JOIN2R);

        JoinedQueryElement JOIN3_1L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN3_1L.setKey(SCHUL_KATEGORIE_IN_SCHEDULE_ART);
        JOIN3_1L.setQuery(SCHEDULE_ART_TABLE_QUERY);

        JoinedQueryElement JOIN3_1R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN3_1R.setKey(ID_COLUMN_IN_SCHUL_KATEGORIE);
        JOIN3_1R.setQuery(SCHEDULE_KATEGORIE_TABLE_QUERY);

        JoinQuery JOIN3_1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN3_1.setId("_join_JOIN3_1");
        JOIN3_1.setLeft(JOIN3_1L);
        JOIN3_1.setRight(JOIN3_1R);

        JoinedQueryElement JOIN3_L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN3_L.setKey(SCHUL_ART_ID_IN_SCHULE_TABLE);
        JOIN3_L.setQuery(SCHEDULE_TABLE_QUERY);

        JoinedQueryElement JOIN3_R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN3_R.setKey(ID_IN_SCHEDULE_ART);
        JOIN3_R.setQuery(JOIN3_1);

        JoinQuery JOIN3 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN3.setId("_join_JOIN3");
        JOIN3.setLeft(JOIN3_L);
        JOIN3.setRight(JOIN3_R);

        JoinedQueryElement JOIN4_L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN4_L.setKey(BUNDESLAND_ID_COLUMN_IN_WOHNORT_LANDKREIS);
        JOIN4_L.setQuery(WOHNORT_LANDKREIS_TABLE_QUERY);

        JoinedQueryElement JOIN4_R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN4_R.setKey(ID_COLUMN_IN_BUNDESLAND);
        JOIN4_R.setQuery(BUNDESLAND_TABLE_QUERY);

        JoinQuery JOIN4 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN4.setId("_join_JOIN4");
        JOIN4.setLeft(JOIN4_L);
        JOIN4.setRight(JOIN4_R);

        JoinedQueryElement JOIN5L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN5L.setKey(SP_FOERDERBEDARF_ID_COLUMN_IN_FOERDERUNG_ART);
        JOIN5L.setQuery(FOERDERUNG_ART_TABLE_QUERY);

        JoinedQueryElement JOIN5R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN5R.setKey(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART);
        JOIN5R.setQuery(SONDERPAED_FOERDERBEDART_TABLE_QUERY);

        JoinQuery JOIN5 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN5.setId("_join_JOIN5");
        JOIN5.setLeft(JOIN5L);
        JOIN5.setRight(JOIN5R);

        Level LEVEL1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL1.setName("Art des Ganztagsangebots");
        LEVEL1.setId("_level_LEVEL1");
        LEVEL1.setColumn(ID_COLUMN_IN_GANZTAGS_ART);
        LEVEL1.setNameColumn(SCHUL_UMFANG_IN_GANZTAGS_ART);

        Level LEVEL2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL2.setName(SCHULE2);
        LEVEL2.setId("_level_LEVEL2");
        LEVEL2.setColumn(ID_COLUMN_IN_SCHULE_TABLE);
        LEVEL2.setNameColumn(SCHUL_NAME_IN_SCHULE_TABLE);
        LEVEL2.setOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE);

        Level LEVEL3 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL3.setName("Schulträger-Kategorie");
        LEVEL3.setId("_level_LEVEL3");
        LEVEL3.setColumn(ID_COLUMN_IN_TRAEGER_KATEGORIE);
        LEVEL3.setNameColumn(TRAEGER_KATEGORIE_COLUMN_IN_TRAEGER_KATEGORIE);

        Level LEVEL4 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL4.setName("Schulträger-Art");
        LEVEL4.setId("_level_LEVEL4");
        LEVEL4.setColumn(ID_COLUMN_IN_TRAEGER_ART);
        LEVEL4.setNameColumn(TRAEGER_ART_COLUMN_IN_TRAEGER_ART);

        Level LEVEL5 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL5.setName("Schulträger");
        LEVEL5.setId("_level_LEVEL5");
        LEVEL5.setColumn(ID_COLUMN_IN_TRAEGER_TABLE);
        LEVEL5.setNameColumn(TRAEGER_NAME_COLUMN_IN_TRAEGER_TABLE);

        Level LEVEL6 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL6.setName(SCHULE2);
        LEVEL6.setId("_level_LEVEL6");
        LEVEL6.setColumn(ID_COLUMN_IN_SCHULE_TABLE);
        LEVEL6.setNameColumn(SCHUL_NAME_IN_SCHULE_TABLE);
        LEVEL6.setOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE);

        Level LEVEL7 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL7.setName("Schulkategorie");
        LEVEL7.setId("_level_LEVEL7");
        LEVEL7.setColumn(ID_COLUMN_IN_SCHUL_KATEGORIE);
        LEVEL7.setNameColumn(SCHUL_KATEGORIE_NAME_COLUMN_IN_SCHUL_KATEGORIE);

        Level LEVEL8 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL8.setName("Schulart");
        LEVEL8.setId("_level_LEVEL8");
        LEVEL8.setColumn(ID_COLUMN_IN_SCHUL_ART);
        LEVEL8.setNameColumn(SCHULART_NAME_COLUMN_IN_SCHUL_ART);

        Level LEVEL9 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL9.setName(SCHULE2);
        LEVEL9.setId("_level_LEVEL9");
        LEVEL9.setColumn(ID_COLUMN_IN_SCHULE_TABLE);
        LEVEL9.setNameColumn(SCHUL_NAME_IN_SCHULE_TABLE);
        LEVEL9.setOrdinalColumn(SCHUL_NUMMER_IN_SCHULE_TABLE);

        Level LEVEL10 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL10.setName(SCHULJAHR);
        LEVEL10.setId("_level_LEVEL10");
        LEVEL10.setColumn(ID_COLUMN_IN_SCHUL_JAHR);
        LEVEL10.setNameColumn(SCHUL_JAHR_COLUMN_IN_SCHUL_JAHR);
        LEVEL10.setOrdinalColumn(ORDER_COLUMN_IN_SCHUL_JAHR);

        Level LEVEL11 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL11.setName("Altersgruppe");
        LEVEL11.setId("_level_LEVEL11");
        LEVEL11.setColumn(ID_COLUMN_IN_ALTERS_GRUPPE);
        LEVEL11.setNameColumn(ALTERSGRUPPE_COLUMN_IN_ALTERS_GRUPPE);

        Level LEVEL12 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL12.setName(GESCHLECHT);
        LEVEL12.setId("_level_LEVEL12");
        LEVEL12.setColumn(ID_COLUMN_IN_GESCHLECHT);
        LEVEL12.setNameColumn(BEZEICHNUNG_COLUMN_IN_GESCHLECHT);

        Level LEVEL13 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL13.setName("Berufsgruppe");
        LEVEL13.setId("_level_LEVEL13");
        LEVEL13.setColumn(ID_COLUMN_IN_PERSONAL_ART);
        LEVEL13.setNameColumn(BEZEICHNUNG_COLUMN_IN_PERSONAL_ART);

        Level LEVEL14 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL14.setName(EINSCHULUNG);
        LEVEL14.setId("_level_LEVEL14");
        LEVEL14.setColumn(ID_COLUMN_IN_EINSCHULUNG);
        LEVEL14.setNameColumn(EINSCHULUNG_COLUMN_IN_EINSCHULUNG);

        Level LEVEL15 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL15.setName(KLASSENWIEDERHOLUNG);
        LEVEL15.setId("_level_LEVEL15");
        LEVEL15.setColumn(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG);
        LEVEL15.setNameColumn(KLASSENWIEDERHOLUNG_COLUMN_IN_KLASSEN_WIEDERHOLUNG);

        Level LEVEL16 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL16.setName("Schulabschlüsse");
        LEVEL16.setId("_level_LEVEL16");
        LEVEL16.setColumn(ID_COLUMN_IN_SCHUL_ABSCHLUSS);
        LEVEL16.setNameColumn(SCHULABSCHLUSS_COLUMN_IN_SCHUL_ABSCHLUSS);

        Level LEVEL17 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL17.setName(MIGRATIONSHINTERGRUND);
        LEVEL17.setId("_level_LEVEL17");
        LEVEL17.setColumn(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND);
        LEVEL17.setNameColumn(MIGRATIONS_HINTERGRUND_COLUMN_IN_MIGRATIONS_HINTERGRUND);

        Level LEVEL18 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL18.setName("Bundesland");
        LEVEL18.setId("_level_LEVEL18");
        LEVEL18.setColumn(ID_COLUMN_IN_BUNDESLAND);
        LEVEL18.setNameColumn(BEZEICHNUNG_COLUMN_IN_BUNDESLAND);

        Level LEVEL19 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL19.setName(WOHNLANDKREIS);
        LEVEL19.setId("_level_LEVEL19");
        LEVEL19.setColumn(ID_COLUMN_IN_WOHNORT_LANDKREIS);
        LEVEL19.setNameColumn(BEZEICHNUNG_COLUMN_IN_WOHNORT_LANDKREIS);

        Level LEVEL20 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL20.setName("Förderbedarf");
        LEVEL20.setId("_level_LEVEL20");
        LEVEL20.setColumn(ID_COLUMN_IN_SONDERPAED_FOERDERBEDART);
        LEVEL20.setNameColumn(SONDERPAED_BEDARF_COLUMN_IN_SONDERPAED_FOERDERBEDART);

        Level LEVEL21 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL21.setName("Art der Förderung");
        LEVEL21.setId("_level_LEVEL21");
        LEVEL21.setColumn(ID_COLUMN_IN_FOERDERUNG_ART);
        LEVEL21.setNameColumn(FOERDERUNG_ART_COLUMN_IN_FOERDERUNG_ART);

        Hierarchy HIERARCHY1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY1.setId("_hierarchy_HIERARCHY1");
        HIERARCHY1.setHasAll(true);
        HIERARCHY1.setAllMemberName(ALLE_SCHULEN);
        HIERARCHY1.setName("Schulen nach Ganztagsangebot");
        HIERARCHY1.setPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE);
        HIERARCHY1.setQuery(JOIN1);
        HIERARCHY1.getLevels().addAll(List.of(LEVEL1, LEVEL2));

        Hierarchy HIERARCHY2 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY2.setId("_hierarchy_HIERARCHY2");
        HIERARCHY2.setHasAll(true);
        HIERARCHY2.setAllMemberName(ALLE_SCHULEN);
        HIERARCHY2.setName("Schulen nach Trägerschaft");
        HIERARCHY2.setPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE);
        HIERARCHY2.setQuery(JOIN2);
        HIERARCHY2.getLevels().addAll(List.of(LEVEL3, LEVEL4, LEVEL5, LEVEL6));

        Hierarchy HIERARCHY3 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY3.setId("_hierarchy_HIERARCHY3");
        HIERARCHY3.setHasAll(true);
        HIERARCHY3.setAllMemberName(ALLE_SCHULEN);
        HIERARCHY3.setName("Schulen nach Art");
        HIERARCHY3.setPrimaryKey(ID_COLUMN_IN_SCHULE_TABLE);
        HIERARCHY3.setQuery(JOIN3);
        HIERARCHY3.getLevels().addAll(List.of(LEVEL7, LEVEL8, LEVEL9));

        Hierarchy HIERARCHY4 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY4.setId("_hierarchy_HIERARCHY4");
        HIERARCHY4.setHasAll(false);
        HIERARCHY4.setName("Schuljahre");
        HIERARCHY4.setPrimaryKey(ID_COLUMN_IN_SCHUL_JAHR);
        HIERARCHY4.setQuery(SCHUL_JAHT_TABLE_QUERY);
        HIERARCHY4.getLevels().addAll(List.of(LEVEL10));

        Hierarchy HIERARCHY5 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY5.setId("_hierarchy_HIERARCHY5");
        HIERARCHY5.setHasAll(true);
        HIERARCHY5.setAllMemberName("Alle Altersgruppen");
        HIERARCHY5.setName("Altersgruppen");
        HIERARCHY5.setPrimaryKey(ID_COLUMN_IN_ALTERS_GRUPPE);
        HIERARCHY5.setQuery(ALTERS_GRUPPE_TABLE_QUERY);
        HIERARCHY5.getLevels().addAll(List.of(LEVEL11));

        Hierarchy HIERARCHY6 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY6.setId("_hierarchy_HIERARCHY6");
        HIERARCHY6.setHasAll(true);
        HIERARCHY6.setAllMemberName("Alle Geschlechter");
        HIERARCHY6.setName(GESCHLECHT);
        HIERARCHY6.setPrimaryKey(ID_COLUMN_IN_GESCHLECHT);
        HIERARCHY6.setQuery(GESCHLECHT_TABLE_QUERY);
        HIERARCHY6.getLevels().addAll(List.of(LEVEL12));

        Hierarchy HIERARCHY7 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY7.setId("_hierarchy_HIERARCHY7");
        HIERARCHY7.setHasAll(true);
        HIERARCHY7.setAllMemberName("Alle Berufsgruppen");
        HIERARCHY7.setName("Berufsgruppen");
        HIERARCHY7.setPrimaryKey(ID_COLUMN_IN_PERSONAL_ART);
        HIERARCHY7.setQuery(PERSONAL_ART_TABLE_QUERY);
        HIERARCHY7.getLevels().addAll(List.of(LEVEL13));

        Hierarchy HIERARCHY8 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY8.setId("_hierarchy_HIERARCHY8");
        HIERARCHY8.setHasAll(true);
        HIERARCHY8.setAllMemberName(GESAMT);
        HIERARCHY8.setName(EINSCHULUNG);
        HIERARCHY8.setPrimaryKey(ID_COLUMN_IN_EINSCHULUNG);
        HIERARCHY8.setQuery(EINSCHULUNG_TABLE_QUERY);
        HIERARCHY8.getLevels().addAll(List.of(LEVEL14));

        Hierarchy HIERARCHY9 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY9.setId("_hierarchy_HIERARCHY9");
        HIERARCHY9.setHasAll(true);
        HIERARCHY9.setAllMemberName(GESAMT);
        HIERARCHY9.setName(KLASSENWIEDERHOLUNG);
        HIERARCHY9.setPrimaryKey(ID_COLUMN_IN_KLASSEN_WIEDERHOLUNG);
        HIERARCHY9.setQuery(KLASSEN_WIEDERHOLUNG_TABLE_QUERY);
        HIERARCHY9.getLevels().addAll(List.of(LEVEL15));

        Hierarchy HIERARCHY10 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY10.setId("_hierarchy_HIERARCHY10");
        HIERARCHY10.setHasAll(true);
        HIERARCHY10.setAllMemberName(GESAMT);
        HIERARCHY10.setName("Schulabschlüsse");
        HIERARCHY10.setPrimaryKey(ID_COLUMN_IN_SCHUL_ABSCHLUSS);
        HIERARCHY10.setQuery(SCHUL_ABSCHLUSS_TABLE_QUERY);
        HIERARCHY10.getLevels().addAll(List.of(LEVEL16));

        Hierarchy HIERARCHY11 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY11.setId("_hierarchy_HIERARCHY11");
        HIERARCHY11.setHasAll(true);
        HIERARCHY11.setAllMemberName(GESAMT);
        HIERARCHY11.setName(MIGRATIONSHINTERGRUND);
        HIERARCHY11.setPrimaryKey(ID_COLUMN_IN_MIGRATIONS_HINTERGRUND);
        HIERARCHY11.setQuery(MIGRATIONS_HINTERGRUND_TABLE_QUERY);
        HIERARCHY11.getLevels().addAll(List.of(LEVEL17));

        Hierarchy HIERARCHY12 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY12.setId("_hierarchy_HIERARCHY12");
        HIERARCHY12.setHasAll(true);
        HIERARCHY12.setAllMemberName("Alle Wohnlandkreise");
        HIERARCHY12.setName(WOHNLANDKREIS);
        HIERARCHY12.setPrimaryKey(ID_COLUMN_IN_WOHNORT_LANDKREIS);
        HIERARCHY12.setQuery(JOIN4);
        HIERARCHY12.getLevels().addAll(List.of(LEVEL18, LEVEL19));

        Hierarchy HIERARCHY13 = RolapMappingFactory.eINSTANCE.createHierarchy();
        HIERARCHY12.setId("_hierarchy_HIERARCHY12");
        HIERARCHY13.setHasAll(true);
        HIERARCHY13.setAllMemberName(GESAMT);
        HIERARCHY13.setName("Sonderpädagogische Förderung");
        HIERARCHY13.setPrimaryKey(ID_COLUMN_IN_FOERDERUNG_ART);
        HIERARCHY13.setQuery(JOIN5);
        HIERARCHY13.getLevels().addAll(List.of(LEVEL20, LEVEL21));

        StandardDimension SCHULEN_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        SCHULEN_DIMENSION.setName(SCHULEN);
        SCHULEN_DIMENSION.setId("_dimension_Schulen");
        SCHULEN_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY1, HIERARCHY2, HIERARCHY3));

        StandardDimension SCHULJAHRE_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        SCHULJAHRE_DIMENSION.setName("Schuljahre");
        SCHULJAHRE_DIMENSION.setId("_dimension_Schuljahre");
        SCHULJAHRE_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY4));

        StandardDimension ALTERSGRUPPEN_PERSONAL_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        ALTERSGRUPPEN_PERSONAL_DIMENSION.setName("Altersgruppen Personal");
        ALTERSGRUPPEN_PERSONAL_DIMENSION.setId("_dimension_Altersgruppen_Personal");
        ALTERSGRUPPEN_PERSONAL_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY5));

        StandardDimension GESCHLECHT_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        GESCHLECHT_DIMENSION.setName(GESCHLECHT);
        GESCHLECHT_DIMENSION.setId("_dimension_Geschlecht");
        GESCHLECHT_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY6));

        StandardDimension Berufsgruppen_Personal_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Berufsgruppen_Personal_DIMENSION.setName("Berufsgruppen Personal");
        Berufsgruppen_Personal_DIMENSION.setId("_dimension_Berufsgruppen_Personal");
        Berufsgruppen_Personal_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY7));

        StandardDimension Einschulungen_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Einschulungen_DIMENSION.setName("Einschulungen");
        Einschulungen_DIMENSION.setId("_dimension_Einschulungen");
        Einschulungen_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY8));

        StandardDimension Klassenwiederholung_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Klassenwiederholung_DIMENSION.setName(KLASSENWIEDERHOLUNG);
        Klassenwiederholung_DIMENSION.setId("_dimension_Klassenwiederholung");
        Klassenwiederholung_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY9));

        StandardDimension Schulabschluss_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Schulabschluss_DIMENSION.setName("Schulabschluss");
        Schulabschluss_DIMENSION.setId("_dimension_Schulabschluss");
        Schulabschluss_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY10));

        StandardDimension Migrationshintergrund_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Migrationshintergrund_DIMENSION.setName(MIGRATIONSHINTERGRUND);
        Migrationshintergrund_DIMENSION.setId("_dimension_Migrationshintergrund");
        Migrationshintergrund_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY11));

        StandardDimension Wohnlandkreis_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Wohnlandkreis_DIMENSION.setName(WOHNLANDKREIS);
        Wohnlandkreis_DIMENSION.setId("_dimension_Wohnlandkreis");
        Wohnlandkreis_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY12));

        StandardDimension Inklusion_DIMENSION = RolapMappingFactory.eINSTANCE.createStandardDimension();
        Inklusion_DIMENSION.setName("Inklusion");
        Inklusion_DIMENSION.setId("_dimension_Inklusion");
        Inklusion_DIMENSION.getHierarchies().addAll(List.of(HIERARCHY13));

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Anzahl Schulen");
        measure1.setId("_measure_Anzahl_Schulen");
        measure1.setColumn(ANZAHL_SCHULEN_COLUMN_IN_FACT_SCHULEN);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Anzahl Klassen");
        measure2.setId("_measure_Anzahl_Klassen");
        measure2.setColumn(ANZAHL_KLASSEN_COLUMN_IN_FACT_SCHULEN);

        SumMeasure measure3 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure3.setName("Anzahl Personen");
        measure3.setId("_measure_Anzahl_Personen");
        measure3.setColumn(ANZAHL_PERSONEN_COLUMN_IN_FACT_PERSONAL);

        SumMeasure measure4 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure4.setName("Anzahl Schüler:innen");
        measure4.setId("_measure_Anzahl_Schuler_innen");
        measure4.setColumn(ANZAHL_SCHUELER_COLUMN_IN_FACT_SCHUELER);

        MeasureGroup CUBE1_MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        CUBE1_MEASURE_GROUP.getMeasures().addAll(List.of(measure1, measure2));

        MeasureGroup CUBE2_MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        CUBE2_MEASURE_GROUP.getMeasures().addAll(List.of(measure3));

        MeasureGroup CUBE3_MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        CUBE3_MEASURE_GROUP.getMeasures().addAll(List.of(measure4));

        DimensionConnector SCHULEN_DC1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULEN_DC1.setOverrideDimensionName(SCHULEN);
        SCHULEN_DC1.setDimension(SCHULEN_DIMENSION);
        SCHULEN_DC1.setForeignKey(SCHULE_ID_COLUMN_IN_FACT_SCHULEN);

        DimensionConnector SCHULJAHR_DC1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULJAHR_DC1.setOverrideDimensionName(SCHULJAHR);
        SCHULJAHR_DC1.setDimension(SCHULJAHRE_DIMENSION);
        SCHULJAHR_DC1.setForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHULEN);

        PhysicalCube CUBE1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE1.setId("_cube_CUBE1");
        CUBE1.setName("Schulen in Jena (Institutionen)");
        CUBE1.setQuery(FACT_SCHULEN_TABLE_QUERY);
        CUBE1.getDimensionConnectors().addAll(List.of(SCHULEN_DC1, SCHULJAHR_DC1));
        CUBE1.getMeasureGroups().addAll(List.of(CUBE1_MEASURE_GROUP));

        DimensionConnector SCHULEN_DC2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULEN_DC2.setOverrideDimensionName(SCHULEN);
        SCHULEN_DC2.setDimension(SCHULEN_DIMENSION);
        SCHULEN_DC2.setForeignKey(SCHULE_ID_COLUMN_IN_FACT_PERSONAL);

        DimensionConnector SCHULJAHR_DC2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULJAHR_DC2.setOverrideDimensionName(SCHULJAHR);
        SCHULJAHR_DC2.setDimension(SCHULJAHRE_DIMENSION);
        SCHULJAHR_DC2.setForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_PERSONAL);

        DimensionConnector Altersgruppe_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        Altersgruppe_DC.setOverrideDimensionName("Altersgruppe");
        Altersgruppe_DC.setDimension(ALTERSGRUPPEN_PERSONAL_DIMENSION);
        Altersgruppe_DC.setForeignKey(ALTERS_GROUP_ID_COLUMN_IN_FACT_PERSONAL);

        DimensionConnector GESCHLECHT_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        GESCHLECHT_DC.setOverrideDimensionName(GESCHLECHT);
        GESCHLECHT_DC.setDimension(GESCHLECHT_DIMENSION);
        GESCHLECHT_DC.setForeignKey(GESCHLECHT_ID_COLUMN_IN_FACT_PERSONAL);

        DimensionConnector Berufsgruppe_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        Berufsgruppe_DC.setOverrideDimensionName("Berufsgruppe");
        Berufsgruppe_DC.setDimension(Berufsgruppen_Personal_DIMENSION);
        Berufsgruppe_DC.setForeignKey(PERSONAL_ART_ID_COLUMN_IN_FACT_PERSONAL);

        PhysicalCube CUBE2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE2.setName("Pädagogisches Personal an Jenaer Schulen");
        CUBE2.setId("_cube_CUBE2");
        CUBE2.setQuery(FACT_PERSONAL_TABLE_QUERY);
        CUBE2.getDimensionConnectors()
                .addAll(List.of(SCHULEN_DC2, SCHULJAHR_DC2, Altersgruppe_DC, GESCHLECHT_DC, Berufsgruppe_DC));
        CUBE2.getMeasureGroups().addAll(List.of(CUBE2_MEASURE_GROUP));

        DimensionConnector SCHULEN_DC3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULEN_DC3.setOverrideDimensionName(SCHULEN);
        SCHULEN_DC3.setDimension(SCHULEN_DIMENSION);
        SCHULEN_DC3.setForeignKey(SCHULE_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector SCHULJAHR_DC3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        SCHULJAHR_DC3.setOverrideDimensionName(SCHULJAHR);
        SCHULJAHR_DC3.setDimension(SCHULJAHRE_DIMENSION);
        SCHULJAHR_DC3.setForeignKey(SCHUL_JAHR_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector GESCHLECHT_DC3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        GESCHLECHT_DC3.setOverrideDimensionName(GESCHLECHT);
        GESCHLECHT_DC3.setDimension(GESCHLECHT_DIMENSION);
        GESCHLECHT_DC3.setForeignKey(GESCHLECHT_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector WOHNLANDKREIS_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        WOHNLANDKREIS_DC.setOverrideDimensionName(WOHNLANDKREIS);
        WOHNLANDKREIS_DC.setDimension(Wohnlandkreis_DIMENSION);
        WOHNLANDKREIS_DC.setForeignKey(WOHN_LK_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector EINSCHULUNG_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        EINSCHULUNG_DC.setOverrideDimensionName(EINSCHULUNG);
        EINSCHULUNG_DC.setDimension(Einschulungen_DIMENSION);
        EINSCHULUNG_DC.setForeignKey(EINSCHULUNG_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector Schulabschluss_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        Schulabschluss_DC.setOverrideDimensionName("Schulabschluss");
        Schulabschluss_DC.setDimension(Schulabschluss_DIMENSION);
        Schulabschluss_DC.setForeignKey(SCHUL_ABSCHLUSS_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector KLASSENWIEDERHOLUNG_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        KLASSENWIEDERHOLUNG_DC.setOverrideDimensionName(KLASSENWIEDERHOLUNG);
        KLASSENWIEDERHOLUNG_DC.setDimension(Klassenwiederholung_DIMENSION);
        KLASSENWIEDERHOLUNG_DC.setForeignKey(KLASSEN_WDH_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector MIGRATIONSHINTERGRUND_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        MIGRATIONSHINTERGRUND_DC.setOverrideDimensionName(MIGRATIONSHINTERGRUND);
        MIGRATIONSHINTERGRUND_DC.setDimension(Migrationshintergrund_DIMENSION);
        MIGRATIONSHINTERGRUND_DC.setForeignKey(MIGRATIONS_HG_ID_COLUMN_IN_FACT_SCHUELER);

        DimensionConnector Sonderpadagogische_Forderung_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        Sonderpadagogische_Forderung_DC.setOverrideDimensionName("Sonderpädagogische Förderung");
        Sonderpadagogische_Forderung_DC.setDimension(Inklusion_DIMENSION);
        Sonderpadagogische_Forderung_DC.setForeignKey(FOERDER_ART_ID_COLUMN_IN_FACT_SCHUELER);

        PhysicalCube CUBE3 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE3.setId("_cube_CUBE3");
        CUBE3.setName("Schüler:innen an Jenaer Schulen");
        CUBE3.setQuery(FACT_SCHUELER_TABLE_QUERY);
        CUBE3.getDimensionConnectors()
                .addAll(List.of(SCHULEN_DC3, SCHULJAHR_DC3, GESCHLECHT_DC3, WOHNLANDKREIS_DC, EINSCHULUNG_DC,
                        Schulabschluss_DC, KLASSENWIEDERHOLUNG_DC, MIGRATIONSHINTERGRUND_DC,
                        Sonderpadagogische_Forderung_DC));
        CUBE3.getMeasureGroups().addAll(List.of(CUBE3_MEASURE_GROUP));

        Catalog CATALOG = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG.setName(CATALOG_NAME);
        CATALOG.setId("_catalog_Schulwesen");
        CATALOG.getCubes().addAll(List.of(CUBE1, CUBE2, CUBE3));
        CATALOG.getDbschemas().add(databaseSchema);

        return CATALOG;
    }

}
