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

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.COMPLEX, number = "99.1.1", source = Source.EMF, group = "Full Examples") // NOSONAR

public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String ALLE_SCHULEN = "Alle Schulen";

    private static final String COL_NAME_EINSCHULUNG2 = "einschulung";

    private static final String COL_NAME_TRAEGER_ART = "traeger_art";

    private static final String WOHNLANDKREIS = "Wohnlandkreis";

    private static final String COL_NAME_FOERDERUNG_ART = "foerderung_art";

    private static final String COL_NAME_BEZEICHNUNG = "bezeichnung";

    private static final String COL_NAME_SCHUL_JAHR = "schul_jahr";

    private static final String COL_NAME_TRAEGER_KATEGORIE = "traeger_kategorie";

    private static final String SCHULEN = "Schulen";

    private static final String COL_NAME_WOHNORT_LANDKREIS = "wohnort_landkreis";

    private static final String EINSCHULUNG = "Einschulung";

    private static final String MIGRATIONSHINTERGRUND = "Migrationshintergrund";

    private static final String COL_NAME_SCHUL_NUMMER = "schul_nummer";

    private static final String COL_NAME_SCHUL_JAHR_ID = "schul_jahr_id";

    private static final String COL_NAME_SCHUL_NAME = "schul_name";

    private static final String COL_NAME_SCHULE_ID = "schule_id";

    private static final String GESAMT = "Gesamt";

    private static final String SCHULJAHR = "Schuljahr";

    private static final String KLASSENWIEDERHOLUNG = "Klassenwiederholung";

    private static final String GESCHLECHT = "Geschlecht";

    private static final String SCHULE2 = "Schule";

    private static final String MIGRATIONS_HINTERGRUND = "migrations_hintergrund";

    private static final String TAB_NAME_SCHULE = "schule";

    private static final String CATALOG_NAME = "Daanse Example - Schulwesen";

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseschema");

        // id,schul_nummer,schul_name,traeger_id,schul_art_id,ganztags_art_id
        // INTEGER,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
        Column columnIdInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSchuleTable.setName("id");
        columnIdInSchuleTable.setId("_col_schule_id");
        columnIdInSchuleTable.setType(ColumnType.INTEGER);

        Column columnSchulNameInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulNameInSchuleTable.setName(COL_NAME_SCHUL_NAME);
        columnSchulNameInSchuleTable.setId("_col_schule_schul_name");
        columnSchulNameInSchuleTable.setType(ColumnType.VARCHAR);

        Column columnSchulNummerInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulNummerInSchuleTable.setName(COL_NAME_SCHUL_NUMMER);
        columnSchulNummerInSchuleTable.setId("_col_schule_schul_nummer");
        columnSchulNummerInSchuleTable.setType(ColumnType.INTEGER);

        Column columnGanztagsArtIdInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnGanztagsArtIdInSchuleTable.setName("ganztags_art_id");
        columnGanztagsArtIdInSchuleTable.setId("_col_schule_ganztags_art_id");
        columnGanztagsArtIdInSchuleTable.setType(ColumnType.INTEGER);

        Column columnTraegerIdInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerIdInSchuleTable.setName("traeger_id");
        columnTraegerIdInSchuleTable.setId("_col_schule_traeger_id");
        columnTraegerIdInSchuleTable.setType(ColumnType.INTEGER);

        Column columnSchulArtIdInSchuleTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulArtIdInSchuleTable.setName("schul_art_id");
        columnSchulArtIdInSchuleTable.setId("_col_schule_schul_art_id");
        columnSchulArtIdInSchuleTable.setType(ColumnType.INTEGER);

        PhysicalTable tableSchule = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSchule.setName(TAB_NAME_SCHULE);
        tableSchule.setId("_tab_schule");
        tableSchule.getColumns()
                .addAll(List.of(columnIdInSchuleTable, columnSchulNameInSchuleTable, columnSchulNummerInSchuleTable,
                        columnGanztagsArtIdInSchuleTable, columnTraegerIdInSchuleTable, columnSchulArtIdInSchuleTable));

        // id,schul_umfang
        // INTEGER,VARCHAR
        Column columnIdInGanztagsArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInGanztagsArt.setName("id");
        columnIdInGanztagsArt.setId("_col_ganztags_art_id");
        columnIdInGanztagsArt.setType(ColumnType.INTEGER);

        Column columnSchulUmfangInGanztagsArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulUmfangInGanztagsArt.setName("schul_umfang");
        columnSchulUmfangInGanztagsArt.setId("_col_ganztags_art_schul_umfang");
        columnSchulUmfangInGanztagsArt.setType(ColumnType.VARCHAR);

        PhysicalTable tableGanztagsArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableGanztagsArt.setName("ganztags_art");
        tableGanztagsArt.setId("_tab_ganztags_art");
        tableGanztagsArt.getColumns().addAll(List.of(columnIdInGanztagsArt, columnSchulUmfangInGanztagsArt));

        // id,traeger_name,traeger_art_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInTraegerTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInTraegerTable.setName("id");
        columnIdInTraegerTable.setId("_col_traeger_id");
        columnIdInTraegerTable.setType(ColumnType.INTEGER);

        Column columnTraegerNameInTraegerTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerNameInTraegerTable.setName("traeger_name");
        columnTraegerNameInTraegerTable.setId("_col_traeger_traeger_name");
        columnTraegerNameInTraegerTable.setType(ColumnType.VARCHAR);

        Column columnTraegerArtIdInTraegerTable = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerArtIdInTraegerTable.setName("traeger_art_id");
        columnTraegerArtIdInTraegerTable.setId("_col_traeger_traeger_art_id");
        columnTraegerArtIdInTraegerTable.setType(ColumnType.INTEGER);

        PhysicalTable tableTraeger = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTraeger.setName("traeger");
        tableTraeger.setId("_tab_traeger");
        tableTraeger.getColumns().addAll(
                List.of(columnIdInTraegerTable, columnTraegerNameInTraegerTable, columnTraegerArtIdInTraegerTable));

        // id,traeger_art,traeger_kat_id
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInTraegerArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInTraegerArt.setName("id");
        columnIdInTraegerArt.setId("_col_traeger_art_id");
        columnIdInTraegerArt.setType(ColumnType.INTEGER);

        Column columnTraegerArtInTraegerArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerArtInTraegerArt.setName(COL_NAME_TRAEGER_ART);
        columnTraegerArtInTraegerArt.setId("_col_traeger_art_traeger_art");
        columnTraegerArtInTraegerArt.setType(ColumnType.VARCHAR);

        Column columnTraegerKatIdInTraegerArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerKatIdInTraegerArt.setName("traeger_kat_id");
        columnTraegerKatIdInTraegerArt.setId("_col_traeger_art_traeger_kat_id");
        columnTraegerKatIdInTraegerArt.setType(ColumnType.VARCHAR);

        PhysicalTable tableTraegerArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTraegerArt.setName(COL_NAME_TRAEGER_ART);
        tableTraegerArt.setId("_tab_traeger_art");
        tableTraegerArt.getColumns()
                .addAll(List.of(columnIdInTraegerArt, columnTraegerArtInTraegerArt, columnTraegerKatIdInTraegerArt));

        // id,traeger_kategorie
        // INTEGER,VARCHAR
        Column columnIdInTraegerKategorie = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInTraegerKategorie.setName("id");
        columnIdInTraegerKategorie.setId("_col_traeger_kategorie_id");
        columnIdInTraegerKategorie.setType(ColumnType.INTEGER);

        Column columnTraegerKategorieInTraegerKategorie = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTraegerKategorieInTraegerKategorie.setName(COL_NAME_TRAEGER_KATEGORIE);
        columnTraegerKategorieInTraegerKategorie.setId("_col_traeger_kategorie_traeger_kategorie");
        columnTraegerKategorieInTraegerKategorie.setType(ColumnType.VARCHAR);

        PhysicalTable tableTraegerKategorie = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTraegerKategorie.setName(COL_NAME_TRAEGER_KATEGORIE);
        tableTraegerKategorie.setId("_tab_traeger_kategorie");
        tableTraegerKategorie.getColumns()
                .addAll(List.of(columnIdInTraegerKategorie, columnTraegerKategorieInTraegerKategorie));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInScheduleArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInScheduleArt.setName("id");
        columnIdInScheduleArt.setId("_col_schul_art_id");
        columnIdInScheduleArt.setType(ColumnType.INTEGER);

        Column columnSchulKategorieInScheduleArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulKategorieInScheduleArt.setName("schul_kategorie_id");
        columnSchulKategorieInScheduleArt.setId("_col_schul_art_schul_kategorie_id");
        columnSchulKategorieInScheduleArt.setType(ColumnType.INTEGER);

        PhysicalTable tableScheduleArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableScheduleArt.setName("schul_art");
        tableScheduleArt.setId("_tab_schul_art");
        tableScheduleArt.getColumns().addAll(List.of(columnIdInScheduleArt, columnSchulKategorieInScheduleArt));

        // "id","schul_jahr","order"
        // ColumnType.INTEGER,ColumnType.VARCHAR,ColumnType.INTEGER
        Column columnIdInSchulJahr = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSchulJahr.setName("id");
        columnIdInSchulJahr.setId("_col_schul_jahr_id");
        columnIdInSchulJahr.setType(ColumnType.INTEGER);

        Column columnSchulJahrInSchulJahr = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulJahrInSchulJahr.setName(COL_NAME_SCHUL_JAHR);
        columnSchulJahrInSchulJahr.setId("_col_schul_jahr_schul_jahr");
        columnSchulJahrInSchulJahr.setType(ColumnType.VARCHAR);

        Column columnOrderInSchulJahr = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnOrderInSchulJahr.setName("order");
        columnOrderInSchulJahr.setId("_col_schul_jahr_order");
        columnOrderInSchulJahr.setType(ColumnType.INTEGER);

        PhysicalTable tableSchulJahr = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSchulJahr.setName(COL_NAME_SCHUL_JAHR);
        tableSchulJahr.setId("_tab_schul_jahr");
        tableSchulJahr.getColumns()
                .addAll(List.of(columnIdInSchulJahr, columnSchulJahrInSchulJahr, columnOrderInSchulJahr));

        // id,altersgruppe
        // INTEGER,VARCHAR
        Column columnIdInAltersGruppe = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInAltersGruppe.setName("id");
        columnIdInAltersGruppe.setId("_col_alters_gruppe_id");
        columnIdInAltersGruppe.setType(ColumnType.INTEGER);

        Column columnAltersgruppeInAltersGruppe = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAltersgruppeInAltersGruppe.setName("altersgruppe");
        columnAltersgruppeInAltersGruppe.setId("_col_alters_gruppe_altersgruppe");
        columnAltersgruppeInAltersGruppe.setType(ColumnType.INTEGER);

        PhysicalTable tableAltersGruppe = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableAltersGruppe.setName("alters_gruppe");
        tableAltersGruppe.setId("_tab_alters_gruppe");
        tableAltersGruppe.getColumns().addAll(List.of(columnIdInAltersGruppe, columnAltersgruppeInAltersGruppe));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInGeschlecht = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInGeschlecht.setName("id");
        columnIdInGeschlecht.setId("_col_geschlecht_id");
        columnIdInGeschlecht.setType(ColumnType.INTEGER);

        Column columnBezeichnungInGeschlecht = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnBezeichnungInGeschlecht.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInGeschlecht.setId("_col_geschlecht_bezeichnung");
        columnBezeichnungInGeschlecht.setType(ColumnType.INTEGER);

        PhysicalTable tableGeschlecht = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableGeschlecht.setName("geschlecht");
        tableGeschlecht.setId("_tab_geschlecht");
        tableGeschlecht.getColumns().addAll(List.of(columnIdInGeschlecht, columnBezeichnungInGeschlecht));

        Column columnIdInEinschulung = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInEinschulung.setName("id");
        columnIdInEinschulung.setId("_col_einschulung_id");
        columnIdInEinschulung.setType(ColumnType.INTEGER);

        Column columnEinschulungInEinschulung = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinschulungInEinschulung.setName(COL_NAME_EINSCHULUNG2);
        columnEinschulungInEinschulung.setId("_col_einschulung_einschulung");
        columnEinschulungInEinschulung.setType(ColumnType.VARCHAR);

        PhysicalTable tableEinschulung = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableEinschulung.setName(COL_NAME_EINSCHULUNG2);
        tableEinschulung.setId("_tab_einschulung");
        tableEinschulung.getColumns().addAll(List.of(columnIdInEinschulung, columnEinschulungInEinschulung));

        // id,klassenwiederholung
        // INTEGER,VARCHAR
        Column columnIdInKlassenWiederholung = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInKlassenWiederholung.setName("id");
        columnIdInKlassenWiederholung.setId("_col_klassen_wiederholung_id");
        columnIdInKlassenWiederholung.setType(ColumnType.INTEGER);
        Column columnKlassenwiedlerholungInKlassenWiederholung = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnKlassenwiedlerholungInKlassenWiederholung.setName("klassenwiederholung");
        columnKlassenwiedlerholungInKlassenWiederholung.setId("_col_klassen_wiederholung_klassenwiederholung");
        columnKlassenwiedlerholungInKlassenWiederholung.setType(ColumnType.INTEGER);

        PhysicalTable tableKlassenWiederholung = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableKlassenWiederholung.setName("klassen_wiederholung");
        tableKlassenWiederholung.setId("_tab_klassen_wiederholung");
        tableKlassenWiederholung.getColumns()
                .addAll(List.of(columnIdInKlassenWiederholung, columnKlassenwiedlerholungInKlassenWiederholung));

        // id,schulabschluss
        // INTEGER,VARCHAR
        Column columnIdInSchulAbschluss = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSchulAbschluss.setName("id");
        columnIdInSchulAbschluss.setId("_col_schul_abschluss_id");
        columnIdInSchulAbschluss.setType(ColumnType.INTEGER);

        Column columnSchulabschlussInSchulAbschluss = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulabschlussInSchulAbschluss.setName("schulabschluss");
        columnSchulabschlussInSchulAbschluss.setId("_col_schul_abschluss_schulabschluss");
        columnSchulabschlussInSchulAbschluss.setType(ColumnType.VARCHAR);

        PhysicalTable tableSchulAbschluss = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSchulAbschluss.setName("schul_abschluss");
        tableSchulAbschluss.setId("_tab_schul_abschluss");
        tableSchulAbschluss.getColumns()
                .addAll(List.of(columnIdInSchulAbschluss, columnSchulabschlussInSchulAbschluss));

        // id,migrations_hintergrund
        // INTEGER,VARCHAR
        Column columnIdInMigrationsHintergrund = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInMigrationsHintergrund.setName("id");
        columnIdInMigrationsHintergrund.setId("_col_migrations_hintergrund_id");
        columnIdInMigrationsHintergrund.setType(ColumnType.INTEGER);

        Column columnMigrationsHintergrundInMigrationsHintergrund = RolapMappingFactory.eINSTANCE
                .createPhysicalColumn();
        columnMigrationsHintergrundInMigrationsHintergrund.setName(MIGRATIONS_HINTERGRUND);
        columnMigrationsHintergrundInMigrationsHintergrund.setId("_col_migrations_hintergrund_migrations_hintergrund");
        columnMigrationsHintergrundInMigrationsHintergrund.setType(ColumnType.VARCHAR);

        PhysicalTable tableMigrationsHintergrund = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableMigrationsHintergrund.setName(MIGRATIONS_HINTERGRUND);
        tableMigrationsHintergrund.setId("_tab_migrations_hintergrund");
        tableMigrationsHintergrund.getColumns()
                .addAll(List.of(columnIdInMigrationsHintergrund, columnMigrationsHintergrundInMigrationsHintergrund));

        // id,kuerzel,bezeichnung,bundesland_id
        // INTEGER,VARCHAR,VARCHAR,INTEGER
        Column columnIdInWohnortLandkreis = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInWohnortLandkreis.setName("id");
        columnIdInWohnortLandkreis.setId("_col_wohnort_landkreis_id");
        columnIdInWohnortLandkreis.setType(ColumnType.INTEGER);

        Column columnBezeichnungInWohnortLandkreis = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnBezeichnungInWohnortLandkreis.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInWohnortLandkreis.setId("_col_wohnort_landkreis_bezeichnung");
        columnBezeichnungInWohnortLandkreis.setType(ColumnType.VARCHAR);

        Column columnBundeslandIdInWohnortLandkreis = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnBundeslandIdInWohnortLandkreis.setName("bundesland_id");
        columnBundeslandIdInWohnortLandkreis.setId("_col_wohnort_landkreis_bundesland_id");
        columnBundeslandIdInWohnortLandkreis.setType(ColumnType.INTEGER);

        PhysicalTable tableWohnortLandkreis = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableWohnortLandkreis.setName(COL_NAME_WOHNORT_LANDKREIS);
        tableWohnortLandkreis.setId("_tab_wohnort_landkreis");
        tableWohnortLandkreis.getColumns().addAll(List.of(columnIdInWohnortLandkreis,
                columnBezeichnungInWohnortLandkreis, columnBundeslandIdInWohnortLandkreis));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInSchulArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSchulArt.setName("id");
        columnIdInSchulArt.setId("_col_schul_art_id");
        columnIdInSchulArt.setType(ColumnType.INTEGER);

        Column columnSchulartNameInSchulArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulartNameInSchulArt.setName("schulart_name");
        columnSchulartNameInSchulArt.setId("_col_schul_art_schulart_name");
        columnSchulartNameInSchulArt.setType(ColumnType.VARCHAR);

        PhysicalTable tableSchulArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSchulArt.setName("schul_art");
        tableSchulArt.setId("_tab_schul_art");
        tableSchulArt.getColumns().addAll(List.of(columnIdInSchulArt, columnSchulartNameInSchulArt));

        // id,schul_kategorie_name
        // INTEGER,VARCHAR
        Column columnIdInSchulKategorie = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSchulKategorie.setName("id");
        columnIdInSchulKategorie.setId("_col_schul_kategorie_id");
        columnIdInSchulKategorie.setType(ColumnType.INTEGER);

        Column columnSchulKategorieNameInSchulKategorie = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulKategorieNameInSchulKategorie.setName("schul_kategorie_name");
        columnSchulKategorieNameInSchulKategorie.setId("_col_schul_kategorie_schul_kategorie_name");
        columnSchulKategorieNameInSchulKategorie.setType(ColumnType.VARCHAR);

        PhysicalTable tableSchulKategorie = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSchulKategorie.setName("schul_kategorie");
        tableSchulKategorie.setId("_tab_schul_kategorie");
        tableSchulKategorie.getColumns()
                .addAll(List.of(columnIdInSchulKategorie, columnSchulKategorieNameInSchulKategorie));

        // id,foerderung_art,sp_foerderbedarf_id
        // INTEGER,VARCHAR,INTEGER,
        Column columnIdInFoerderungArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInFoerderungArt.setName("id");
        columnIdInFoerderungArt.setId("_col_foerderung_art_id");
        columnIdInFoerderungArt.setType(ColumnType.INTEGER);

        Column columnFoerderungArtInFoerderungArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFoerderungArtInFoerderungArt.setName(COL_NAME_FOERDERUNG_ART);
        columnFoerderungArtInFoerderungArt.setId("_col_foerderung_art_foerderung_art");
        columnFoerderungArtInFoerderungArt.setType(ColumnType.VARCHAR);

        Column columnSpFoerderbedarfIdInFoerderungArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSpFoerderbedarfIdInFoerderungArt.setName("sp_foerderbedarf_id");
        columnSpFoerderbedarfIdInFoerderungArt.setId("_col_foerderung_art_sp_foerderbedarf_id");
        columnSpFoerderbedarfIdInFoerderungArt.setType(ColumnType.INTEGER);

        PhysicalTable tableFoerderungArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFoerderungArt.setName(COL_NAME_FOERDERUNG_ART);
        tableFoerderungArt.setId("_tab_foerderung_art");
        tableFoerderungArt.getColumns().addAll(List.of(columnIdInFoerderungArt, columnFoerderungArtInFoerderungArt,
                columnSpFoerderbedarfIdInFoerderungArt));

        // id,bezeichnung,,,,,,,,,,,,,,,,,id,bezeichnung
        // INTEGER,VARCHAR,,,,,,,,,,,,,,,,,INTEGER,VARCHAR
        Column columnIdInPersonalArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInPersonalArt.setName("id");
        columnIdInPersonalArt.setId("_col_personal_art_id");
        columnIdInPersonalArt.setType(ColumnType.INTEGER);

        Column columnBezeichnungInPersonalArt = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnBezeichnungInPersonalArt.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInPersonalArt.setId("_col_personal_art_bezeichnung");
        columnBezeichnungInPersonalArt.setType(ColumnType.VARCHAR);

        PhysicalTable tablePersonalArt = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tablePersonalArt.setName("personal_art");
        tablePersonalArt.setId("_tab_personal_art");
        tablePersonalArt.getColumns().addAll(List.of(columnIdInPersonalArt, columnBezeichnungInPersonalArt));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInBundesland = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInBundesland.setName("id");
        columnIdInBundesland.setId("_col_bundesland_id");
        columnIdInBundesland.setType(ColumnType.INTEGER);

        Column columnBezeichnungInBundesland = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnBezeichnungInBundesland.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInBundesland.setId("_col_bundesland_bezeichnung");
        columnBezeichnungInBundesland.setType(ColumnType.VARCHAR);

        PhysicalTable tableBundesland = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableBundesland.setName("bundesland");
        tableBundesland.setId("_tab_bundesland");
        tableBundesland.getColumns().addAll(List.of(columnIdInBundesland, columnBezeichnungInBundesland));

        Column columnIdInSonderpaedFoerderbedart = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnIdInSonderpaedFoerderbedart.setName("id");
        columnIdInSonderpaedFoerderbedart.setId("_col_sonderpaed_foerderbedarf_id");
        columnIdInSonderpaedFoerderbedart.setType(ColumnType.INTEGER);

        Column columnSonderpaedBedarfInSonderpaedFoerderbedart = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSonderpaedBedarfInSonderpaedFoerderbedart.setName("sonderpaed_bedarf");
        columnSonderpaedBedarfInSonderpaedFoerderbedart.setId("_col_sonderpaed_foerderbedarf_sonderpaed_bedarf");
        columnSonderpaedBedarfInSonderpaedFoerderbedart.setType(ColumnType.VARCHAR);

        PhysicalTable tableSonderpaedFoerderbedart = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableSonderpaedFoerderbedart.setName("sonderpaed_foerderbedarf");
        tableSonderpaedFoerderbedart.setId("_tab_sonderpaed_foerderbedarf");
        tableSonderpaedFoerderbedart.getColumns()
                .addAll(List.of(columnIdInSonderpaedFoerderbedart, columnSonderpaedBedarfInSonderpaedFoerderbedart));

        // schule_id,schul_jahr_id,anzahl_schulen,anzahl_klassen
        // INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactSchulen = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchuleIdInFactSchulen.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactSchulen.setId("_col_fact_schulen_schule_id");
        columnSchuleIdInFactSchulen.setType(ColumnType.INTEGER);

        Column columnSchulJahrIdInFactSchulen = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulJahrIdInFactSchulen.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactSchulen.setId("_col_fact_schulen_schul_jahr_id");
        columnSchulJahrIdInFactSchulen.setType(ColumnType.INTEGER);

        Column columnAnzahlSchulenInFactSchulen = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAnzahlSchulenInFactSchulen.setName("anzahl_schulen");
        columnAnzahlSchulenInFactSchulen.setId("_col_fact_schulen_anzahl_schulen");
        columnAnzahlSchulenInFactSchulen.setType(ColumnType.INTEGER);

        Column columnAnzahlKlassenInFactSchulen = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAnzahlKlassenInFactSchulen.setName("anzahl_klassen");
        columnAnzahlKlassenInFactSchulen.setId("_col_fact_schulen_anzahl_klassen");
        columnAnzahlKlassenInFactSchulen.setType(ColumnType.INTEGER);

        PhysicalTable tableFactSchulen = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFactSchulen.setName("fact_schulen");
        tableFactSchulen.setId("_tab_fact_schulen");
        tableFactSchulen.getColumns().addAll(List.of(columnSchuleIdInFactSchulen, columnSchulJahrIdInFactSchulen,
                columnAnzahlSchulenInFactSchulen, columnAnzahlKlassenInFactSchulen));

        // schule_id,schul_jahr_id,alters_gruppe_id,geschlecht_id,personal_art_id,anzahl_personen
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchuleIdInFactPersonal.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactPersonal.setId("_col_fact_personal_schule_id");
        columnSchuleIdInFactPersonal.setType(ColumnType.INTEGER);

        Column columnSchulJahrIdInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulJahrIdInFactPersonal.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactPersonal.setId("_col_fact_personal_schul_jahr_id");
        columnSchulJahrIdInFactPersonal.setType(ColumnType.INTEGER);

        Column columnAltersGroupIdInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAltersGroupIdInFactPersonal.setName("alters_gruppe_id");
        columnAltersGroupIdInFactPersonal.setId("_col_fact_personal_alters_gruppe_id");
        columnAltersGroupIdInFactPersonal.setType(ColumnType.INTEGER);

        Column columnGeschlechtIdInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnGeschlechtIdInFactPersonal.setName("geschlecht_id");
        columnGeschlechtIdInFactPersonal.setId("_col_fact_personal_geschlecht_id");
        columnGeschlechtIdInFactPersonal.setType(ColumnType.INTEGER);

        Column columnPersonalArtIdInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPersonalArtIdInFactPersonal.setName("personal_art_id");
        columnPersonalArtIdInFactPersonal.setId("_col_fact_personal_personal_art_id");
        columnPersonalArtIdInFactPersonal.setType(ColumnType.INTEGER);

        Column columnAnzahlPersonenInFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAnzahlPersonenInFactPersonal.setName("anzahl_personen");
        columnAnzahlPersonenInFactPersonal.setId("_col_fact_personal_anzahl_personen");
        columnAnzahlPersonenInFactPersonal.setType(ColumnType.INTEGER);

        PhysicalTable tableFactPersonal = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFactPersonal.setName("fact_personal");
        tableFactPersonal.setId("_tab_fact_personal");
        tableFactPersonal.getColumns()
                .addAll(List.of(columnSchuleIdInFactPersonal, columnSchulJahrIdInFactPersonal,
                        columnAltersGroupIdInFactPersonal, columnGeschlechtIdInFactPersonal,
                        columnPersonalArtIdInFactPersonal, columnAnzahlPersonenInFactPersonal));

        // schule_id,schul_jahr_id,geschlecht_id,wohn_lk_id,einschulung_id,schul_abschluss_id,klassen_wdh,migrations_hg_id,foerder_art_id,anzahl_schueler
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchuleIdInFactSchueler.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactSchueler.setId("_col_fact_schueler_schule_id");
        columnSchuleIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnSchulJahrIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulJahrIdInFactSchueler.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactSchueler.setId("_col_fact_schueler_schul_jahr_id");
        columnSchulJahrIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnGeschlechtIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnGeschlechtIdInFactSchueler.setName("geschlecht_id");
        columnGeschlechtIdInFactSchueler.setId("_col_fact_schueler_geschlecht_id");
        columnGeschlechtIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnWohnLkIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnWohnLkIdInFactSchueler.setName("wohn_lk_id");
        columnWohnLkIdInFactSchueler.setId("_col_fact_schueler_wohn_lk_id");
        columnWohnLkIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnEinschulungIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinschulungIdInFactSchueler.setName("einschulung_id");
        columnEinschulungIdInFactSchueler.setId("_col_fact_schueler_einschulung_id");
        columnEinschulungIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnSchulAbschlussIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnSchulAbschlussIdInFactSchueler.setName("schul_abschluss_id");
        columnSchulAbschlussIdInFactSchueler.setId("_col_fact_schueler_schul_abschluss_id");
        columnSchulAbschlussIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnKlassenWdhInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnKlassenWdhInFactSchueler.setName("klassen_wdh");
        columnKlassenWdhInFactSchueler.setId("_col_fact_schueler_klassen_wdh");
        columnKlassenWdhInFactSchueler.setType(ColumnType.INTEGER);

        Column columnMigrationsHgIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMigrationsHgIdInFactSchueler.setName("migrations_hg_id");
        columnMigrationsHgIdInFactSchueler.setId("_col_fact_schueler_migrations_hg_id");
        columnMigrationsHgIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnFoerderArtIdInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFoerderArtIdInFactSchueler.setName("foerder_art_id");
        columnFoerderArtIdInFactSchueler.setId("_col_fact_schueler_foerder_art_id");
        columnFoerderArtIdInFactSchueler.setType(ColumnType.INTEGER);

        Column columnAnzahlSchuelerInFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAnzahlSchuelerInFactSchueler.setName("anzahl_schueler");
        columnAnzahlSchuelerInFactSchueler.setId("_col_fact_schueler_anzahl_schueler");
        columnAnzahlSchuelerInFactSchueler.setType(ColumnType.INTEGER);

        PhysicalTable tableFactSchueler = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFactSchueler.setName("fact_schueler");
        tableFactSchueler.setId("_tab_fact_schueler");
        tableFactSchueler.getColumns()
                .addAll(List.of(columnSchuleIdInFactSchueler, columnSchulJahrIdInFactSchueler,
                        columnGeschlechtIdInFactSchueler, columnWohnLkIdInFactSchueler,
                        columnEinschulungIdInFactSchueler, columnSchulAbschlussIdInFactSchueler,
                        columnKlassenWdhInFactSchueler, columnMigrationsHgIdInFactSchueler,
                        columnFoerderArtIdInFactSchueler, columnAnzahlSchuelerInFactSchueler));

        databaseSchema.getTables().addAll(List.of(tableSchule, tableGanztagsArt, tableTraeger, tableTraegerArt,
                tableTraegerKategorie, tableScheduleArt, tableSchulJahr, tableAltersGruppe, tableGeschlecht,
                tableEinschulung, tableKlassenWiederholung, tableSchulAbschluss, tableMigrationsHintergrund,
                tableWohnortLandkreis, tableSchulArt, tableSchulKategorie, tableFoerderungArt, tablePersonalArt,
                tableBundesland, tableSonderpaedFoerderbedart, tableFactSchulen, tableFactPersonal, tableFactSchueler));

        TableQuery tableQuerySchule = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuerySchule.setId("_query_table_schule");
        tableQuerySchule.setTable(tableSchule);

        TableQuery tableQueryGanztagsArt = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryGanztagsArt.setId("_query_table_ganztags_art");
        tableQueryGanztagsArt.setTable(tableGanztagsArt);

        TableQuery tableQueryTraeger = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryTraeger.setId("_query_traeger_table_query");
        tableQueryTraeger.setTable(tableTraeger);

        TableQuery tableQueryTraegerArt = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryTraegerArt.setId("_query_traeger_art_table_query");
        tableQueryTraegerArt.setTable(tableTraegerArt);

        TableQuery tableQueryTraegerKategorie = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryTraegerKategorie.setId("_query_traeger_kategorie_table_query");
        tableQueryTraegerKategorie.setTable(tableTraegerKategorie);

        TableQuery tableQueryScheduleArt = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryScheduleArt.setId("_query_schedule_art_table_query");
        tableQueryScheduleArt.setTable(tableScheduleArt);

        TableQuery tableQueryScheduleKategorie = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryScheduleKategorie.setId("_query_schedule_kategorie_table_query");
        tableQueryScheduleKategorie.setTable(tableSchulKategorie);

        TableQuery tableQuerySchulJahr = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuerySchulJahr.setId("_query_schul_jaht_table_query");
        tableQuerySchulJahr.setTable(tableSchulJahr);

        TableQuery tableQueryAltersGruppe = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryAltersGruppe.setId("_query_alters_gruppe_table_query");
        tableQueryAltersGruppe.setTable(tableAltersGruppe);

        TableQuery tableQueryGeschlecht = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryGeschlecht.setId("_query_geschlecht_table_query");
        tableQueryGeschlecht.setTable(tableGeschlecht);

        TableQuery tableQueryPersonalArt = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryPersonalArt.setId("_query_personal_art_table_query");
        tableQueryPersonalArt.setTable(tablePersonalArt);

        TableQuery tableQueryEinschulung = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryEinschulung.setId("_query_einschulung_table_query");
        tableQueryEinschulung.setTable(tableEinschulung);

        TableQuery tableQueryKlassenWiederholung = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryKlassenWiederholung.setId("_query_klassen_wiederholung_table_query");
        tableQueryKlassenWiederholung.setTable(tableKlassenWiederholung);

        TableQuery tableQuerySchulAbschluss = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuerySchulAbschluss.setId("_query_schul_abschluss_table_query");
        tableQuerySchulAbschluss.setTable(tableSchulAbschluss);

        TableQuery tableQueryMigrationsHintergrund = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryMigrationsHintergrund.setId("_query_migrations_hintergrund_table_query");
        tableQueryMigrationsHintergrund.setTable(tableMigrationsHintergrund);

        TableQuery tableQueryWohnortLandkreis = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryWohnortLandkreis.setId("_query_wohnort_landkreis_table_query");
        tableQueryWohnortLandkreis.setTable(tableWohnortLandkreis);

        TableQuery tableQueryBundesland = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryBundesland.setId("_query_bundesland_table_query");
        tableQueryBundesland.setTable(tableBundesland);

        TableQuery tableQueryFoerderungArt = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryFoerderungArt.setId("_query_foerderung_art_table_query");
        tableQueryFoerderungArt.setTable(tableFoerderungArt);

        TableQuery tableQuerySonderpaedFoerderbedart = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuerySonderpaedFoerderbedart.setId("_query_sonderpaed_foerderbedart_table_query");
        tableQuerySonderpaedFoerderbedart.setTable(tableSonderpaedFoerderbedart);

        TableQuery tableQueryFactSchulen = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryFactSchulen.setId("_query_fact_schulen_table_query");
        tableQueryFactSchulen.setTable(tableFactSchulen);

        TableQuery tableQueryFactPersonal = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryFactPersonal.setId("_query_fact_personam_table_query");
        tableQueryFactPersonal.setTable(tableFactPersonal);

        TableQuery tableQueryFactSchueler = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQueryFactSchueler.setId("_query_fact_schueler_table_query");
        tableQueryFactSchueler.setTable(tableFactSchueler);

        JoinedQueryElement joinElementSchuleGanztagsartLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleGanztagsartLeft.setKey(columnGanztagsArtIdInSchuleTable);
        joinElementSchuleGanztagsartLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleGanztagsartRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleGanztagsartRight.setKey(columnIdInGanztagsArt);
        joinElementSchuleGanztagsartRight.setQuery(tableQueryGanztagsArt);

        JoinQuery joinSchuleGanztagsart = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinSchuleGanztagsart.setId("_join_schule_ganztagsart");
        joinSchuleGanztagsart.setLeft(joinElementSchuleGanztagsartLeft);
        joinSchuleGanztagsart.setRight(joinElementSchuleGanztagsartRight);

        JoinedQueryElement joinElementTraegerKategorieArtLeft = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementTraegerKategorieArtLeft.setKey(columnTraegerKatIdInTraegerArt);
        joinElementTraegerKategorieArtLeft.setQuery(tableQueryTraegerArt);

        JoinedQueryElement joinElementTraegerKategorieArtRight = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementTraegerKategorieArtRight.setKey(columnIdInTraegerKategorie);
        joinElementTraegerKategorieArtRight.setQuery(tableQueryTraegerKategorie);

        JoinQuery joinTraegerKategorieArt = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinTraegerKategorieArt.setId("_join_traeger_kategorie_art");
        joinTraegerKategorieArt.setLeft(joinElementTraegerKategorieArtLeft);
        joinTraegerKategorieArt.setRight(joinElementTraegerKategorieArtRight);

        JoinedQueryElement joinElementTraegerArtTraegerLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerArtTraegerLeft.setKey(columnTraegerArtIdInTraegerTable);
        joinElementTraegerArtTraegerLeft.setQuery(tableQueryTraeger);

        JoinedQueryElement joinElementTraegerArtTraegerRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerArtTraegerRight.setKey(columnIdInTraegerArt);
        joinElementTraegerArtTraegerRight.setQuery(joinTraegerKategorieArt);

        JoinQuery joinTraegerArtTraeger = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinTraegerArtTraeger.setId("_join_traeger_art_traeger");
        joinTraegerArtTraeger.setLeft(joinElementTraegerArtTraegerLeft);
        joinTraegerArtTraeger.setRight(joinElementTraegerArtTraegerRight);

        JoinedQueryElement joinElementSchuleTraegerHierarchyLeft = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementSchuleTraegerHierarchyLeft.setKey(columnTraegerIdInSchuleTable);
        joinElementSchuleTraegerHierarchyLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleTraegerHierarchyRight = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementSchuleTraegerHierarchyRight.setKey(columnIdInTraegerTable);
        joinElementSchuleTraegerHierarchyRight.setQuery(joinTraegerArtTraeger);

        JoinQuery joinSchuleTraegerHierarchy = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinSchuleTraegerHierarchy.setId("_join_schule_traeger_hierarchy");
        joinSchuleTraegerHierarchy.setLeft(joinElementSchuleTraegerHierarchyLeft);
        joinSchuleTraegerHierarchy.setRight(joinElementSchuleTraegerHierarchyRight);

        JoinedQueryElement joinElementSchulkategorieArtLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchulkategorieArtLeft.setKey(columnSchulKategorieInScheduleArt);
        joinElementSchulkategorieArtLeft.setQuery(tableQueryScheduleArt);

        JoinedQueryElement joinElementSchulkategorieArtRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchulkategorieArtRight.setKey(columnIdInSchulKategorie);
        joinElementSchulkategorieArtRight.setQuery(tableQueryScheduleKategorie);

        JoinQuery joinSchulkategorieArt = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinSchulkategorieArt.setId("_join_schulkategorie_art");
        joinSchulkategorieArt.setLeft(joinElementSchulkategorieArtLeft);
        joinSchulkategorieArt.setRight(joinElementSchulkategorieArtRight);

        JoinedQueryElement joinElementSchuleSchulartHierarchyLeft = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementSchuleSchulartHierarchyLeft.setKey(columnSchulArtIdInSchuleTable);
        joinElementSchuleSchulartHierarchyLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleSchulartHierarchyRight = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementSchuleSchulartHierarchyRight.setKey(columnIdInScheduleArt);
        joinElementSchuleSchulartHierarchyRight.setQuery(joinSchulkategorieArt);

        JoinQuery joinSchuleSchulartHierarchy = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinSchuleSchulartHierarchy.setId("_join_schule_schulart_hierarchy");
        joinSchuleSchulartHierarchy.setLeft(joinElementSchuleSchulartHierarchyLeft);
        joinSchuleSchulartHierarchy.setRight(joinElementSchuleSchulartHierarchyRight);

        JoinedQueryElement joinElementWohnlandkreisBundeslandLeft = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementWohnlandkreisBundeslandLeft.setKey(columnBundeslandIdInWohnortLandkreis);
        joinElementWohnlandkreisBundeslandLeft.setQuery(tableQueryWohnortLandkreis);

        JoinedQueryElement joinElementWohnlandkreisBundeslandRight = RolapMappingFactory.eINSTANCE
                .createJoinedQueryElement();
        joinElementWohnlandkreisBundeslandRight.setKey(columnIdInBundesland);
        joinElementWohnlandkreisBundeslandRight.setQuery(tableQueryBundesland);

        JoinQuery joinWohnlandkreisBundesland = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinWohnlandkreisBundesland.setId("_join_wohnlandkreis_bundesland");
        joinWohnlandkreisBundesland.setLeft(joinElementWohnlandkreisBundeslandLeft);
        joinWohnlandkreisBundesland.setRight(joinElementWohnlandkreisBundeslandRight);

        JoinedQueryElement joinElementFoerderbedarfArtLeft = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFoerderbedarfArtLeft.setKey(columnSpFoerderbedarfIdInFoerderungArt);
        joinElementFoerderbedarfArtLeft.setQuery(tableQueryFoerderungArt);

        JoinedQueryElement joinElementFoerderbedarfArtRight = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFoerderbedarfArtRight.setKey(columnIdInSonderpaedFoerderbedart);
        joinElementFoerderbedarfArtRight.setQuery(tableQuerySonderpaedFoerderbedart);

        JoinQuery joinFoerderbedarfArt = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinFoerderbedarfArt.setId("_join_foerderbedarf_art");
        joinFoerderbedarfArt.setLeft(joinElementFoerderbedarfArtLeft);
        joinFoerderbedarfArt.setRight(joinElementFoerderbedarfArtRight);

        Level levelGanztagsangebot = RolapMappingFactory.eINSTANCE.createLevel();
        levelGanztagsangebot.setName("Art des Ganztagsangebots");
        levelGanztagsangebot.setId("_level_ganztagsangebot");
        levelGanztagsangebot.setColumn(columnIdInGanztagsArt);
        levelGanztagsangebot.setNameColumn(columnSchulUmfangInGanztagsArt);

        Level levelSchule = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchule.setName(SCHULE2);
        levelSchule.setId("_level_schule_basic");
        levelSchule.setColumn(columnIdInSchuleTable);
        levelSchule.setNameColumn(columnSchulNameInSchuleTable);
        levelSchule.setOrdinalColumn(columnSchulNummerInSchuleTable);

        Level levelTraegerKategorie = RolapMappingFactory.eINSTANCE.createLevel();
        levelTraegerKategorie.setName("Schulträger-Kategorie");
        levelTraegerKategorie.setId("_level_traeger_kategorie");
        levelTraegerKategorie.setColumn(columnIdInTraegerKategorie);
        levelTraegerKategorie.setNameColumn(columnTraegerKategorieInTraegerKategorie);

        Level levelTraegerArt = RolapMappingFactory.eINSTANCE.createLevel();
        levelTraegerArt.setName("Schulträger-Art");
        levelTraegerArt.setId("_level_traeger_art");
        levelTraegerArt.setColumn(columnIdInTraegerArt);
        levelTraegerArt.setNameColumn(columnTraegerArtInTraegerArt);

        Level levelTraeger = RolapMappingFactory.eINSTANCE.createLevel();
        levelTraeger.setName("Schulträger");
        levelTraeger.setId("_level_traeger");
        levelTraeger.setColumn(columnIdInTraegerTable);
        levelTraeger.setNameColumn(columnTraegerNameInTraegerTable);

        Level levelSchuleTraegerschaft = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchuleTraegerschaft.setName(SCHULE2);
        levelSchuleTraegerschaft.setId("_level_schule_traegerschaft");
        levelSchuleTraegerschaft.setColumn(columnIdInSchuleTable);
        levelSchuleTraegerschaft.setNameColumn(columnSchulNameInSchuleTable);
        levelSchuleTraegerschaft.setOrdinalColumn(columnSchulNummerInSchuleTable);

        Level levelSchulkategorie = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchulkategorie.setName("Schulkategorie");
        levelSchulkategorie.setId("_level_schulkategorie");
        levelSchulkategorie.setColumn(columnIdInSchulKategorie);
        levelSchulkategorie.setNameColumn(columnSchulKategorieNameInSchulKategorie);

        Level levelSchulart = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchulart.setName("Schulart");
        levelSchulart.setId("_level_schulart");
        levelSchulart.setColumn(columnIdInSchulArt);
        levelSchulart.setNameColumn(columnSchulartNameInSchulArt);

        Level levelSchuleArt = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchuleArt.setName(SCHULE2);
        levelSchuleArt.setId("_level_schule_art");
        levelSchuleArt.setColumn(columnIdInSchuleTable);
        levelSchuleArt.setNameColumn(columnSchulNameInSchuleTable);
        levelSchuleArt.setOrdinalColumn(columnSchulNummerInSchuleTable);

        Level levelSchuljahr = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchuljahr.setName(SCHULJAHR);
        levelSchuljahr.setId("_level_schuljahr");
        levelSchuljahr.setColumn(columnIdInSchulJahr);
        levelSchuljahr.setNameColumn(columnSchulJahrInSchulJahr);
        levelSchuljahr.setOrdinalColumn(columnOrderInSchulJahr);

        Level levelAltersgruppe = RolapMappingFactory.eINSTANCE.createLevel();
        levelAltersgruppe.setName("Altersgruppe");
        levelAltersgruppe.setId("_level_altersgruppe");
        levelAltersgruppe.setColumn(columnIdInAltersGruppe);
        levelAltersgruppe.setNameColumn(columnAltersgruppeInAltersGruppe);

        Level levelGeschlecht = RolapMappingFactory.eINSTANCE.createLevel();
        levelGeschlecht.setName(GESCHLECHT);
        levelGeschlecht.setId("_level_geschlecht");
        levelGeschlecht.setColumn(columnIdInGeschlecht);
        levelGeschlecht.setNameColumn(columnBezeichnungInGeschlecht);

        Level levelBerufsgruppe = RolapMappingFactory.eINSTANCE.createLevel();
        levelBerufsgruppe.setName("Berufsgruppe");
        levelBerufsgruppe.setId("_level_berufsgruppe");
        levelBerufsgruppe.setColumn(columnIdInPersonalArt);
        levelBerufsgruppe.setNameColumn(columnBezeichnungInPersonalArt);

        Level levelEinschulung = RolapMappingFactory.eINSTANCE.createLevel();
        levelEinschulung.setName(EINSCHULUNG);
        levelEinschulung.setId("_level_einschulung");
        levelEinschulung.setColumn(columnIdInEinschulung);
        levelEinschulung.setNameColumn(columnEinschulungInEinschulung);

        Level levelKlassenwiederholung = RolapMappingFactory.eINSTANCE.createLevel();
        levelKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        levelKlassenwiederholung.setId("_level_klassenwiederholung");
        levelKlassenwiederholung.setColumn(columnIdInKlassenWiederholung);
        levelKlassenwiederholung.setNameColumn(columnKlassenwiedlerholungInKlassenWiederholung);

        Level levelSchulabschluss = RolapMappingFactory.eINSTANCE.createLevel();
        levelSchulabschluss.setName("Schulabschlüsse");
        levelSchulabschluss.setId("_level_schulabschluss");
        levelSchulabschluss.setColumn(columnIdInSchulAbschluss);
        levelSchulabschluss.setNameColumn(columnSchulabschlussInSchulAbschluss);

        Level levelMigrationshintergrund = RolapMappingFactory.eINSTANCE.createLevel();
        levelMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        levelMigrationshintergrund.setId("_level_migrationshintergrund");
        levelMigrationshintergrund.setColumn(columnIdInMigrationsHintergrund);
        levelMigrationshintergrund.setNameColumn(columnMigrationsHintergrundInMigrationsHintergrund);

        Level levelBundesland = RolapMappingFactory.eINSTANCE.createLevel();
        levelBundesland.setName("Bundesland");
        levelBundesland.setId("_level_bundesland");
        levelBundesland.setColumn(columnIdInBundesland);
        levelBundesland.setNameColumn(columnBezeichnungInBundesland);

        Level levelWohnlandkreis = RolapMappingFactory.eINSTANCE.createLevel();
        levelWohnlandkreis.setName(WOHNLANDKREIS);
        levelWohnlandkreis.setId("_level_wohnlandkreis");
        levelWohnlandkreis.setColumn(columnIdInWohnortLandkreis);
        levelWohnlandkreis.setNameColumn(columnBezeichnungInWohnortLandkreis);

        Level levelFoerderbedarf = RolapMappingFactory.eINSTANCE.createLevel();
        levelFoerderbedarf.setName("Förderbedarf");
        levelFoerderbedarf.setId("_level_foerderbedarf");
        levelFoerderbedarf.setColumn(columnIdInSonderpaedFoerderbedart);
        levelFoerderbedarf.setNameColumn(columnSonderpaedBedarfInSonderpaedFoerderbedart);

        Level levelFoerderungArt = RolapMappingFactory.eINSTANCE.createLevel();
        levelFoerderungArt.setName("Art der Förderung");
        levelFoerderungArt.setId("_level_foerderung_art");
        levelFoerderungArt.setColumn(columnIdInFoerderungArt);
        levelFoerderungArt.setNameColumn(columnFoerderungArtInFoerderungArt);

        ExplicitHierarchy hierarchySchulenGanztagsangebot = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenGanztagsangebot.setId("_hierarchy_schulen_ganztagsangebot");
        hierarchySchulenGanztagsangebot.setHasAll(true);
        hierarchySchulenGanztagsangebot.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenGanztagsangebot.setName("Schulen nach Ganztagsangebot");
        hierarchySchulenGanztagsangebot.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenGanztagsangebot.setQuery(joinSchuleGanztagsart);
        hierarchySchulenGanztagsangebot.getLevels().addAll(List.of(levelGanztagsangebot, levelSchule));

        ExplicitHierarchy hierarchySchulenTraegerschaft = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenTraegerschaft.setId("_hierarchy_schulen_traegerschaft");
        hierarchySchulenTraegerschaft.setHasAll(true);
        hierarchySchulenTraegerschaft.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenTraegerschaft.setName("Schulen nach Trägerschaft");
        hierarchySchulenTraegerschaft.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenTraegerschaft.setQuery(joinSchuleTraegerHierarchy);
        hierarchySchulenTraegerschaft.getLevels()
                .addAll(List.of(levelTraegerKategorie, levelTraegerArt, levelTraeger, levelSchuleTraegerschaft));

        ExplicitHierarchy hierarchySchulenArt = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenArt.setId("_hierarchy_schulen_art");
        hierarchySchulenArt.setHasAll(true);
        hierarchySchulenArt.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenArt.setName("Schulen nach Art");
        hierarchySchulenArt.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenArt.setQuery(joinSchuleSchulartHierarchy);
        hierarchySchulenArt.getLevels().addAll(List.of(levelSchulkategorie, levelSchulart, levelSchuleArt));

        ExplicitHierarchy hierarchySchuljahre = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchuljahre.setId("_hierarchy_schuljahre");
        hierarchySchuljahre.setHasAll(false);
        hierarchySchuljahre.setName("Schuljahre");
        hierarchySchuljahre.setPrimaryKey(columnIdInSchulJahr);
        hierarchySchuljahre.setQuery(tableQuerySchulJahr);
        hierarchySchuljahre.getLevels().addAll(List.of(levelSchuljahr));

        ExplicitHierarchy hierarchyAltersgruppen = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyAltersgruppen.setId("_hierarchy_altersgruppen");
        hierarchyAltersgruppen.setHasAll(true);
        hierarchyAltersgruppen.setAllMemberName("Alle Altersgruppen");
        hierarchyAltersgruppen.setName("Altersgruppen");
        hierarchyAltersgruppen.setPrimaryKey(columnIdInAltersGruppe);
        hierarchyAltersgruppen.setQuery(tableQueryAltersGruppe);
        hierarchyAltersgruppen.getLevels().addAll(List.of(levelAltersgruppe));

        ExplicitHierarchy hierarchyGeschlecht = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyGeschlecht.setId("_hierarchy_geschlecht");
        hierarchyGeschlecht.setHasAll(true);
        hierarchyGeschlecht.setAllMemberName("Alle Geschlechter");
        hierarchyGeschlecht.setName(GESCHLECHT);
        hierarchyGeschlecht.setPrimaryKey(columnIdInGeschlecht);
        hierarchyGeschlecht.setQuery(tableQueryGeschlecht);
        hierarchyGeschlecht.getLevels().addAll(List.of(levelGeschlecht));

        ExplicitHierarchy hierarchyBerufsgruppen = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyBerufsgruppen.setId("_hierarchy_berufsgruppen");
        hierarchyBerufsgruppen.setHasAll(true);
        hierarchyBerufsgruppen.setAllMemberName("Alle Berufsgruppen");
        hierarchyBerufsgruppen.setName("Berufsgruppen");
        hierarchyBerufsgruppen.setPrimaryKey(columnIdInPersonalArt);
        hierarchyBerufsgruppen.setQuery(tableQueryPersonalArt);
        hierarchyBerufsgruppen.getLevels().addAll(List.of(levelBerufsgruppe));

        ExplicitHierarchy hierarchyEinschulung = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyEinschulung.setId("_hierarchy_einschulung");
        hierarchyEinschulung.setHasAll(true);
        hierarchyEinschulung.setAllMemberName(GESAMT);
        hierarchyEinschulung.setName(EINSCHULUNG);
        hierarchyEinschulung.setPrimaryKey(columnIdInEinschulung);
        hierarchyEinschulung.setQuery(tableQueryEinschulung);
        hierarchyEinschulung.getLevels().addAll(List.of(levelEinschulung));

        ExplicitHierarchy hierarchyKlassenwiederholung = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyKlassenwiederholung.setId("_hierarchy_klassenwiederholung");
        hierarchyKlassenwiederholung.setHasAll(true);
        hierarchyKlassenwiederholung.setAllMemberName(GESAMT);
        hierarchyKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        hierarchyKlassenwiederholung.setPrimaryKey(columnIdInKlassenWiederholung);
        hierarchyKlassenwiederholung.setQuery(tableQueryKlassenWiederholung);
        hierarchyKlassenwiederholung.getLevels().addAll(List.of(levelKlassenwiederholung));

        ExplicitHierarchy hierarchySchulabschluss = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulabschluss.setId("_hierarchy_schulabschluss");
        hierarchySchulabschluss.setHasAll(true);
        hierarchySchulabschluss.setAllMemberName(GESAMT);
        hierarchySchulabschluss.setName("Schulabschlüsse");
        hierarchySchulabschluss.setPrimaryKey(columnIdInSchulAbschluss);
        hierarchySchulabschluss.setQuery(tableQuerySchulAbschluss);
        hierarchySchulabschluss.getLevels().addAll(List.of(levelSchulabschluss));

        ExplicitHierarchy hierarchyMigrationshintergrund = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyMigrationshintergrund.setId("_hierarchy_migrationshintergrund");
        hierarchyMigrationshintergrund.setHasAll(true);
        hierarchyMigrationshintergrund.setAllMemberName(GESAMT);
        hierarchyMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        hierarchyMigrationshintergrund.setPrimaryKey(columnIdInMigrationsHintergrund);
        hierarchyMigrationshintergrund.setQuery(tableQueryMigrationsHintergrund);
        hierarchyMigrationshintergrund.getLevels().addAll(List.of(levelMigrationshintergrund));

        ExplicitHierarchy hierarchyWohnlandkreis = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyWohnlandkreis.setId("_hierarchy_wohnlandkreis");
        hierarchyWohnlandkreis.setHasAll(true);
        hierarchyWohnlandkreis.setAllMemberName("Alle Wohnlandkreise");
        hierarchyWohnlandkreis.setName(WOHNLANDKREIS);
        hierarchyWohnlandkreis.setPrimaryKey(columnIdInWohnortLandkreis);
        hierarchyWohnlandkreis.setQuery(joinWohnlandkreisBundesland);
        hierarchyWohnlandkreis.getLevels().addAll(List.of(levelBundesland, levelWohnlandkreis));

        ExplicitHierarchy hierarchyFoerderung = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyFoerderung.setId("_hierarchy_foerderung");
        hierarchyFoerderung.setHasAll(true);
        hierarchyFoerderung.setAllMemberName(GESAMT);
        hierarchyFoerderung.setName("Sonderpädagogische Förderung");
        hierarchyFoerderung.setPrimaryKey(columnIdInFoerderungArt);
        hierarchyFoerderung.setQuery(joinFoerderbedarfArt);
        hierarchyFoerderung.getLevels().addAll(List.of(levelFoerderbedarf, levelFoerderungArt));

        StandardDimension dimensionSchulen = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionSchulen.setName(SCHULEN);
        dimensionSchulen.setId("_dimension_schulen");
        dimensionSchulen.getHierarchies()
                .addAll(List.of(hierarchySchulenGanztagsangebot, hierarchySchulenTraegerschaft, hierarchySchulenArt));

        StandardDimension dimensionSchuljahre = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionSchuljahre.setName("Schuljahre");
        dimensionSchuljahre.setId("_dimension_schuljahre");
        dimensionSchuljahre.getHierarchies().addAll(List.of(hierarchySchuljahre));

        StandardDimension dimensionAltersgruppenPersonal = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionAltersgruppenPersonal.setName("Altersgruppen Personal");
        dimensionAltersgruppenPersonal.setId("_dimension_altersgruppen_personal");
        dimensionAltersgruppenPersonal.getHierarchies().addAll(List.of(hierarchyAltersgruppen));

        StandardDimension dimensionGeschlecht = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionGeschlecht.setName(GESCHLECHT);
        dimensionGeschlecht.setId("_dimension_geschlecht");
        dimensionGeschlecht.getHierarchies().addAll(List.of(hierarchyGeschlecht));

        StandardDimension dimensionBerufsgruppenPersonal = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionBerufsgruppenPersonal.setName("Berufsgruppen Personal");
        dimensionBerufsgruppenPersonal.setId("_dimension_berufsgruppen_personal");
        dimensionBerufsgruppenPersonal.getHierarchies().addAll(List.of(hierarchyBerufsgruppen));

        StandardDimension dimensionEinschulungen = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionEinschulungen.setName("Einschulungen");
        dimensionEinschulungen.setId("_dimension_einschulungen");
        dimensionEinschulungen.getHierarchies().addAll(List.of(hierarchyEinschulung));

        StandardDimension dimensionKlassenwiederholung = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        dimensionKlassenwiederholung.setId("_dimension_klassenwiederholung");
        dimensionKlassenwiederholung.getHierarchies().addAll(List.of(hierarchyKlassenwiederholung));

        StandardDimension dimensionSchulabschluss = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionSchulabschluss.setName("Schulabschluss");
        dimensionSchulabschluss.setId("_dimension_schulabschluss");
        dimensionSchulabschluss.getHierarchies().addAll(List.of(hierarchySchulabschluss));

        StandardDimension dimensionMigrationshintergrund = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        dimensionMigrationshintergrund.setId("_dimension_migrationshintergrund");
        dimensionMigrationshintergrund.getHierarchies().addAll(List.of(hierarchyMigrationshintergrund));

        StandardDimension dimensionWohnlandkreis = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionWohnlandkreis.setName(WOHNLANDKREIS);
        dimensionWohnlandkreis.setId("_dimension_wohnlandkreis");
        dimensionWohnlandkreis.getHierarchies().addAll(List.of(hierarchyWohnlandkreis));

        StandardDimension dimensionInklusion = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimensionInklusion.setName("Inklusion");
        dimensionInklusion.setId("_dimension_inklusion");
        dimensionInklusion.getHierarchies().addAll(List.of(hierarchyFoerderung));

        SumMeasure measureAnzahlSchulen = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureAnzahlSchulen.setName("Anzahl Schulen");
        measureAnzahlSchulen.setId("_measure_anzahl_schulen");
        measureAnzahlSchulen.setColumn(columnAnzahlSchulenInFactSchulen);

        SumMeasure measureAnzahlKlassen = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureAnzahlKlassen.setName("Anzahl Klassen");
        measureAnzahlKlassen.setId("_measure_anzahl_klassen");
        measureAnzahlKlassen.setColumn(columnAnzahlKlassenInFactSchulen);

        SumMeasure measureAnzahlPersonen = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureAnzahlPersonen.setName("Anzahl Personen");
        measureAnzahlPersonen.setId("_measure_anzahl_personen");
        measureAnzahlPersonen.setColumn(columnAnzahlPersonenInFactPersonal);

        SumMeasure measureAnzahlSchuelerInnen = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureAnzahlSchuelerInnen.setName("Anzahl SchülerInnen");
        measureAnzahlSchuelerInnen.setId("_measure_anzahl_schuler_innen");
        measureAnzahlSchuelerInnen.setColumn(columnAnzahlSchuelerInFactSchueler);

        MeasureGroup measureGroupSchulenInstitutionen = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupSchulenInstitutionen.getMeasures().addAll(List.of(measureAnzahlSchulen, measureAnzahlKlassen));

        MeasureGroup measureGroupPaedagogischesPersonal = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupPaedagogischesPersonal.getMeasures().addAll(List.of(measureAnzahlPersonen));

        MeasureGroup measureGroupSchuelerInnen = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupSchuelerInnen.getMeasures().addAll(List.of(measureAnzahlSchuelerInnen));

        DimensionConnector connectorSchulen1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen1.setId("_dc_schulen");
        connectorSchulen1.setOverrideDimensionName(SCHULEN);
        connectorSchulen1.setDimension(dimensionSchulen);
        connectorSchulen1.setForeignKey(columnSchuleIdInFactSchulen);

        DimensionConnector connectorSchuljahr1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr1.setId("_dc_schuljahr");
        connectorSchuljahr1.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr1.setDimension(dimensionSchuljahre);
        connectorSchuljahr1.setForeignKey(columnSchulJahrIdInFactSchulen);

        PhysicalCube cubeSchulenInstitutionen = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cubeSchulenInstitutionen.setId("_cube_schulen_institutionen");
        cubeSchulenInstitutionen.setName("Schulen in Jena (Institutionen)");
        cubeSchulenInstitutionen.setQuery(tableQueryFactSchulen);
        cubeSchulenInstitutionen.getDimensionConnectors().addAll(List.of(connectorSchulen1, connectorSchuljahr1));
        cubeSchulenInstitutionen.getMeasureGroups().addAll(List.of(measureGroupSchulenInstitutionen));

        DimensionConnector connectorSchulen2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen2.setOverrideDimensionName(SCHULEN);
        connectorSchulen2.setDimension(dimensionSchulen);
        connectorSchulen2.setForeignKey(columnSchuleIdInFactPersonal);

        DimensionConnector connectorSchuljahr2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr2.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr2.setDimension(dimensionSchuljahre);
        connectorSchuljahr2.setForeignKey(columnSchulJahrIdInFactPersonal);

        DimensionConnector connectorAltersgruppe = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorAltersgruppe.setOverrideDimensionName("Altersgruppe");
        connectorAltersgruppe.setDimension(dimensionAltersgruppenPersonal);
        connectorAltersgruppe.setForeignKey(columnAltersGroupIdInFactPersonal);

        DimensionConnector connectorGeschlecht = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorGeschlecht.setOverrideDimensionName(GESCHLECHT);
        connectorGeschlecht.setDimension(dimensionGeschlecht);
        connectorGeschlecht.setForeignKey(columnGeschlechtIdInFactPersonal);

        DimensionConnector connectorBerufsgruppe = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorBerufsgruppe.setOverrideDimensionName("Berufsgruppe");
        connectorBerufsgruppe.setDimension(dimensionBerufsgruppenPersonal);
        connectorBerufsgruppe.setForeignKey(columnPersonalArtIdInFactPersonal);

        PhysicalCube cubePaedagogischesPersonal = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cubePaedagogischesPersonal.setName("Pädagogisches Personal an Jenaer Schulen");
        cubePaedagogischesPersonal.setId("_cube_paedagogisches_personal");
        cubePaedagogischesPersonal.setQuery(tableQueryFactPersonal);
        cubePaedagogischesPersonal.getDimensionConnectors().addAll(List.of(connectorSchulen2, connectorSchuljahr2,
                connectorAltersgruppe, connectorGeschlecht, connectorBerufsgruppe));
        cubePaedagogischesPersonal.getMeasureGroups().addAll(List.of(measureGroupPaedagogischesPersonal));

        DimensionConnector connectorSchulen3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen3.setOverrideDimensionName(SCHULEN);
        connectorSchulen3.setDimension(dimensionSchulen);
        connectorSchulen3.setForeignKey(columnSchuleIdInFactSchueler);

        DimensionConnector connectorSchuljahr3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr3.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr3.setDimension(dimensionSchuljahre);
        connectorSchuljahr3.setForeignKey(columnSchulJahrIdInFactSchueler);

        DimensionConnector connectorGeschlecht3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorGeschlecht3.setOverrideDimensionName(GESCHLECHT);
        connectorGeschlecht3.setDimension(dimensionGeschlecht);
        connectorGeschlecht3.setForeignKey(columnGeschlechtIdInFactSchueler);

        DimensionConnector connectorWohnlandkreis = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorWohnlandkreis.setOverrideDimensionName(WOHNLANDKREIS);
        connectorWohnlandkreis.setDimension(dimensionWohnlandkreis);
        connectorWohnlandkreis.setForeignKey(columnWohnLkIdInFactSchueler);

        DimensionConnector connectorEinschulung = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorEinschulung.setOverrideDimensionName(EINSCHULUNG);
        connectorEinschulung.setDimension(dimensionEinschulungen);
        connectorEinschulung.setForeignKey(columnEinschulungIdInFactSchueler);

        DimensionConnector connectorSchulabschluss = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorSchulabschluss.setOverrideDimensionName("Schulabschluss");
        connectorSchulabschluss.setDimension(dimensionSchulabschluss);
        connectorSchulabschluss.setForeignKey(columnSchulAbschlussIdInFactSchueler);

        DimensionConnector connectorKlassenwiederholung = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorKlassenwiederholung.setOverrideDimensionName(KLASSENWIEDERHOLUNG);
        connectorKlassenwiederholung.setDimension(dimensionKlassenwiederholung);
        connectorKlassenwiederholung.setForeignKey(columnKlassenWdhInFactSchueler);

        DimensionConnector connectorMigrationshintergrund = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        connectorMigrationshintergrund.setOverrideDimensionName(MIGRATIONSHINTERGRUND);
        connectorMigrationshintergrund.setDimension(dimensionMigrationshintergrund);
        connectorMigrationshintergrund.setForeignKey(columnMigrationsHgIdInFactSchueler);

        DimensionConnector connectorSonderpaedagogischeFoerderung = RolapMappingFactory.eINSTANCE
                .createDimensionConnector();
        connectorSonderpaedagogischeFoerderung.setOverrideDimensionName("Sonderpädagogische Förderung");
        connectorSonderpaedagogischeFoerderung.setDimension(dimensionInklusion);
        connectorSonderpaedagogischeFoerderung.setForeignKey(columnFoerderArtIdInFactSchueler);

        PhysicalCube cubeSchuelerInnen = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cubeSchuelerInnen.setId("_cube_schueler_innen");
        cubeSchuelerInnen.setName("Schüler:innen an Jenaer Schulen");
        cubeSchuelerInnen.setQuery(tableQueryFactSchueler);
        cubeSchuelerInnen.getDimensionConnectors()
                .addAll(List.of(connectorSchulen3, connectorSchuljahr3, connectorGeschlecht3, connectorWohnlandkreis,
                        connectorEinschulung, connectorSchulabschluss, connectorKlassenwiederholung,
                        connectorMigrationshintergrund, connectorSonderpaedagogischeFoerderung));
        cubeSchuelerInnen.getMeasureGroups().addAll(List.of(measureGroupSchuelerInnen));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG_NAME);
        catalog.setId("_catalog_schulwesen");
        catalog.getCubes().addAll(List.of(cubeSchulenInstitutionen, cubePaedagogischesPersonal, cubeSchuelerInnen));
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
