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
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
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
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        // id,schul_nummer,schul_name,traeger_id,schul_art_id,ganztags_art_id
        // INTEGER,INTEGER,VARCHAR,INTEGER,INTEGER,INTEGER
        Column columnIdInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSchuleTable.setName("id");
        columnIdInSchuleTable.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulNameInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulNameInSchuleTable.setName(COL_NAME_SCHUL_NAME);
        columnSchulNameInSchuleTable.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnSchulNummerInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulNummerInSchuleTable.setName(COL_NAME_SCHUL_NUMMER);
        columnSchulNummerInSchuleTable.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnGanztagsArtIdInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnGanztagsArtIdInSchuleTable.setName("ganztags_art_id");
        columnGanztagsArtIdInSchuleTable.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnTraegerIdInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerIdInSchuleTable.setName("traeger_id");
        columnTraegerIdInSchuleTable.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulArtIdInSchuleTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulArtIdInSchuleTable.setName("schul_art_id");
        columnSchulArtIdInSchuleTable.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableSchule = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSchule.setName(TAB_NAME_SCHULE);
        tableSchule.getFeature()
                .addAll(List.of(columnIdInSchuleTable, columnSchulNameInSchuleTable, columnSchulNummerInSchuleTable,
                        columnGanztagsArtIdInSchuleTable, columnTraegerIdInSchuleTable, columnSchulArtIdInSchuleTable));

        // id,schul_umfang
        // INTEGER,VARCHAR
        Column columnIdInGanztagsArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInGanztagsArt.setName("id");
        columnIdInGanztagsArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulUmfangInGanztagsArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulUmfangInGanztagsArt.setName("schul_umfang");
        columnSchulUmfangInGanztagsArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableGanztagsArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableGanztagsArt.setName("ganztags_art");
        tableGanztagsArt.getFeature().addAll(List.of(columnIdInGanztagsArt, columnSchulUmfangInGanztagsArt));

        // id,traeger_name,traeger_art_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInTraegerTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInTraegerTable.setName("id");
        columnIdInTraegerTable.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnTraegerNameInTraegerTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerNameInTraegerTable.setName("traeger_name");
        columnTraegerNameInTraegerTable.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnTraegerArtIdInTraegerTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerArtIdInTraegerTable.setName("traeger_art_id");
        columnTraegerArtIdInTraegerTable.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableTraeger = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTraeger.setName("traeger");
        tableTraeger.getFeature().addAll(
                List.of(columnIdInTraegerTable, columnTraegerNameInTraegerTable, columnTraegerArtIdInTraegerTable));

        // id,traeger_art,traeger_kat_id
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInTraegerArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInTraegerArt.setName("id");
        columnIdInTraegerArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnTraegerArtInTraegerArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerArtInTraegerArt.setName(COL_NAME_TRAEGER_ART);
        columnTraegerArtInTraegerArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnTraegerKatIdInTraegerArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerKatIdInTraegerArt.setName("traeger_kat_id");
        columnTraegerKatIdInTraegerArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableTraegerArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTraegerArt.setName(COL_NAME_TRAEGER_ART);
        tableTraegerArt.getFeature()
                .addAll(List.of(columnIdInTraegerArt, columnTraegerArtInTraegerArt, columnTraegerKatIdInTraegerArt));

        // id,traeger_kategorie
        // INTEGER,VARCHAR
        Column columnIdInTraegerKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInTraegerKategorie.setName("id");
        columnIdInTraegerKategorie.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnTraegerKategorieInTraegerKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnTraegerKategorieInTraegerKategorie.setName(COL_NAME_TRAEGER_KATEGORIE);
        columnTraegerKategorieInTraegerKategorie.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableTraegerKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTraegerKategorie.setName(COL_NAME_TRAEGER_KATEGORIE);
        tableTraegerKategorie.getFeature()
                .addAll(List.of(columnIdInTraegerKategorie, columnTraegerKategorieInTraegerKategorie));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInScheduleArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInScheduleArt.setName("id");
        columnIdInScheduleArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulKategorieInScheduleArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulKategorieInScheduleArt.setName("schul_kategorie_id");
        columnSchulKategorieInScheduleArt.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableScheduleArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableScheduleArt.setName("schul_art");
        tableScheduleArt.getFeature().addAll(List.of(columnIdInScheduleArt, columnSchulKategorieInScheduleArt));

        // "id","schul_jahr","order"
        // SqlSimpleTypes.Sql99.integerType(),SqlSimpleTypes.Sql99.varcharType(),SqlSimpleTypes.Sql99.integerType()
        Column columnIdInSchulJahr = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSchulJahr.setName("id");
        columnIdInSchulJahr.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulJahrInSchulJahr = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulJahrInSchulJahr.setName(COL_NAME_SCHUL_JAHR);
        columnSchulJahrInSchulJahr.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnOrderInSchulJahr = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnOrderInSchulJahr.setName("order");
        columnOrderInSchulJahr.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableSchulJahr = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSchulJahr.setName(COL_NAME_SCHUL_JAHR);
        tableSchulJahr.getFeature()
                .addAll(List.of(columnIdInSchulJahr, columnSchulJahrInSchulJahr, columnOrderInSchulJahr));

        // id,altersgruppe
        // INTEGER,VARCHAR
        Column columnIdInAltersGruppe = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInAltersGruppe.setName("id");
        columnIdInAltersGruppe.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAltersgruppeInAltersGruppe = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAltersgruppeInAltersGruppe.setName("altersgruppe");
        columnAltersgruppeInAltersGruppe.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableAltersGruppe = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableAltersGruppe.setName("alters_gruppe");
        tableAltersGruppe.getFeature().addAll(List.of(columnIdInAltersGruppe, columnAltersgruppeInAltersGruppe));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInGeschlecht = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInGeschlecht.setName("id");
        columnIdInGeschlecht.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnBezeichnungInGeschlecht = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnBezeichnungInGeschlecht.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInGeschlecht.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableGeschlecht = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableGeschlecht.setName("geschlecht");
        tableGeschlecht.getFeature().addAll(List.of(columnIdInGeschlecht, columnBezeichnungInGeschlecht));

        Column columnIdInEinschulung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInEinschulung.setName("id");
        columnIdInEinschulung.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnEinschulungInEinschulung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnEinschulungInEinschulung.setName(COL_NAME_EINSCHULUNG2);
        columnEinschulungInEinschulung.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableEinschulung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableEinschulung.setName(COL_NAME_EINSCHULUNG2);
        tableEinschulung.getFeature().addAll(List.of(columnIdInEinschulung, columnEinschulungInEinschulung));

        // id,klassenwiederholung
        // INTEGER,VARCHAR
        Column columnIdInKlassenWiederholung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInKlassenWiederholung.setName("id");
        columnIdInKlassenWiederholung.setType(SqlSimpleTypes.Sql99.integerType());
        Column columnKlassenwiedlerholungInKlassenWiederholung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKlassenwiedlerholungInKlassenWiederholung.setName("klassenwiederholung");
        columnKlassenwiedlerholungInKlassenWiederholung.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableKlassenWiederholung = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableKlassenWiederholung.setName("klassen_wiederholung");
        tableKlassenWiederholung.getFeature()
                .addAll(List.of(columnIdInKlassenWiederholung, columnKlassenwiedlerholungInKlassenWiederholung));

        // id,schulabschluss
        // INTEGER,VARCHAR
        Column columnIdInSchulAbschluss = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSchulAbschluss.setName("id");
        columnIdInSchulAbschluss.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulabschlussInSchulAbschluss = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulabschlussInSchulAbschluss.setName("schulabschluss");
        columnSchulabschlussInSchulAbschluss.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableSchulAbschluss = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSchulAbschluss.setName("schul_abschluss");
        tableSchulAbschluss.getFeature()
                .addAll(List.of(columnIdInSchulAbschluss, columnSchulabschlussInSchulAbschluss));

        // id,migrations_hintergrund
        // INTEGER,VARCHAR
        Column columnIdInMigrationsHintergrund = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInMigrationsHintergrund.setName("id");
        columnIdInMigrationsHintergrund.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnMigrationsHintergrundInMigrationsHintergrund = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMigrationsHintergrundInMigrationsHintergrund.setName(MIGRATIONS_HINTERGRUND);
        columnMigrationsHintergrundInMigrationsHintergrund.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableMigrationsHintergrund = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableMigrationsHintergrund.setName(MIGRATIONS_HINTERGRUND);
        tableMigrationsHintergrund.getFeature()
                .addAll(List.of(columnIdInMigrationsHintergrund, columnMigrationsHintergrundInMigrationsHintergrund));

        // id,kuerzel,bezeichnung,bundesland_id
        // INTEGER,VARCHAR,VARCHAR,INTEGER
        Column columnIdInWohnortLandkreis = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInWohnortLandkreis.setName("id");
        columnIdInWohnortLandkreis.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnBezeichnungInWohnortLandkreis = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnBezeichnungInWohnortLandkreis.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInWohnortLandkreis.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnBundeslandIdInWohnortLandkreis = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnBundeslandIdInWohnortLandkreis.setName("bundesland_id");
        columnBundeslandIdInWohnortLandkreis.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableWohnortLandkreis = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableWohnortLandkreis.setName(COL_NAME_WOHNORT_LANDKREIS);
        tableWohnortLandkreis.getFeature().addAll(List.of(columnIdInWohnortLandkreis,
                columnBezeichnungInWohnortLandkreis, columnBundeslandIdInWohnortLandkreis));

        // id,schulart_name,schul_kategorie_id
        // INTEGER,VARCHAR,INTEGER
        Column columnIdInSchulArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSchulArt.setName("id");
        columnIdInSchulArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulartNameInSchulArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulartNameInSchulArt.setName("schulart_name");
        columnSchulartNameInSchulArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableSchulArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSchulArt.setName("schul_art");
        tableSchulArt.getFeature().addAll(List.of(columnIdInSchulArt, columnSchulartNameInSchulArt));

        // id,schul_kategorie_name
        // INTEGER,VARCHAR
        Column columnIdInSchulKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSchulKategorie.setName("id");
        columnIdInSchulKategorie.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulKategorieNameInSchulKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulKategorieNameInSchulKategorie.setName("schul_kategorie_name");
        columnSchulKategorieNameInSchulKategorie.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableSchulKategorie = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSchulKategorie.setName("schul_kategorie");
        tableSchulKategorie.getFeature()
                .addAll(List.of(columnIdInSchulKategorie, columnSchulKategorieNameInSchulKategorie));

        // id,foerderung_art,sp_foerderbedarf_id
        // INTEGER,VARCHAR,INTEGER,
        Column columnIdInFoerderungArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInFoerderungArt.setName("id");
        columnIdInFoerderungArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnFoerderungArtInFoerderungArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnFoerderungArtInFoerderungArt.setName(COL_NAME_FOERDERUNG_ART);
        columnFoerderungArtInFoerderungArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnSpFoerderbedarfIdInFoerderungArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSpFoerderbedarfIdInFoerderungArt.setName("sp_foerderbedarf_id");
        columnSpFoerderbedarfIdInFoerderungArt.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableFoerderungArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFoerderungArt.setName(COL_NAME_FOERDERUNG_ART);
        tableFoerderungArt.getFeature().addAll(List.of(columnIdInFoerderungArt, columnFoerderungArtInFoerderungArt,
                columnSpFoerderbedarfIdInFoerderungArt));

        // id,bezeichnung,,,,,,,,,,,,,,,,,id,bezeichnung
        // INTEGER,VARCHAR,,,,,,,,,,,,,,,,,INTEGER,VARCHAR
        Column columnIdInPersonalArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInPersonalArt.setName("id");
        columnIdInPersonalArt.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnBezeichnungInPersonalArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnBezeichnungInPersonalArt.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInPersonalArt.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tablePersonalArt = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tablePersonalArt.setName("personal_art");
        tablePersonalArt.getFeature().addAll(List.of(columnIdInPersonalArt, columnBezeichnungInPersonalArt));

        // id,kuerzel,bezeichnung
        // INTEGER,VARCHAR,VARCHAR
        Column columnIdInBundesland = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInBundesland.setName("id");
        columnIdInBundesland.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnBezeichnungInBundesland = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnBezeichnungInBundesland.setName(COL_NAME_BEZEICHNUNG);
        columnBezeichnungInBundesland.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableBundesland = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableBundesland.setName("bundesland");
        tableBundesland.getFeature().addAll(List.of(columnIdInBundesland, columnBezeichnungInBundesland));

        Column columnIdInSonderpaedFoerderbedart = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnIdInSonderpaedFoerderbedart.setName("id");
        columnIdInSonderpaedFoerderbedart.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSonderpaedBedarfInSonderpaedFoerderbedart = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSonderpaedBedarfInSonderpaedFoerderbedart.setName("sonderpaed_bedarf");
        columnSonderpaedBedarfInSonderpaedFoerderbedart.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableSonderpaedFoerderbedart = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableSonderpaedFoerderbedart.setName("sonderpaed_foerderbedarf");
        tableSonderpaedFoerderbedart.getFeature()
                .addAll(List.of(columnIdInSonderpaedFoerderbedart, columnSonderpaedBedarfInSonderpaedFoerderbedart));

        // schule_id,schul_jahr_id,anzahl_schulen,anzahl_klassen
        // INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactSchulen = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchuleIdInFactSchulen.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactSchulen.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulJahrIdInFactSchulen = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulJahrIdInFactSchulen.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactSchulen.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAnzahlSchulenInFactSchulen = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAnzahlSchulenInFactSchulen.setName("anzahl_schulen");
        columnAnzahlSchulenInFactSchulen.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAnzahlKlassenInFactSchulen = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAnzahlKlassenInFactSchulen.setName("anzahl_klassen");
        columnAnzahlKlassenInFactSchulen.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableFactSchulen = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFactSchulen.setName("fact_schulen");
        tableFactSchulen.getFeature().addAll(List.of(columnSchuleIdInFactSchulen, columnSchulJahrIdInFactSchulen,
                columnAnzahlSchulenInFactSchulen, columnAnzahlKlassenInFactSchulen));

        // schule_id,schul_jahr_id,alters_gruppe_id,geschlecht_id,personal_art_id,anzahl_personen
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchuleIdInFactPersonal.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulJahrIdInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulJahrIdInFactPersonal.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAltersGroupIdInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAltersGroupIdInFactPersonal.setName("alters_gruppe_id");
        columnAltersGroupIdInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnGeschlechtIdInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnGeschlechtIdInFactPersonal.setName("geschlecht_id");
        columnGeschlechtIdInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnPersonalArtIdInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnPersonalArtIdInFactPersonal.setName("personal_art_id");
        columnPersonalArtIdInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAnzahlPersonenInFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAnzahlPersonenInFactPersonal.setName("anzahl_personen");
        columnAnzahlPersonenInFactPersonal.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableFactPersonal = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFactPersonal.setName("fact_personal");
        tableFactPersonal.getFeature()
                .addAll(List.of(columnSchuleIdInFactPersonal, columnSchulJahrIdInFactPersonal,
                        columnAltersGroupIdInFactPersonal, columnGeschlechtIdInFactPersonal,
                        columnPersonalArtIdInFactPersonal, columnAnzahlPersonenInFactPersonal));

        // schule_id,schul_jahr_id,geschlecht_id,wohn_lk_id,einschulung_id,schul_abschluss_id,klassen_wdh,migrations_hg_id,foerder_art_id,anzahl_schueler
        // INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,INTEGER
        Column columnSchuleIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchuleIdInFactSchueler.setName(COL_NAME_SCHULE_ID);
        columnSchuleIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulJahrIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulJahrIdInFactSchueler.setName(COL_NAME_SCHUL_JAHR_ID);
        columnSchulJahrIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnGeschlechtIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnGeschlechtIdInFactSchueler.setName("geschlecht_id");
        columnGeschlechtIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnWohnLkIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnWohnLkIdInFactSchueler.setName("wohn_lk_id");
        columnWohnLkIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnEinschulungIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnEinschulungIdInFactSchueler.setName("einschulung_id");
        columnEinschulungIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnSchulAbschlussIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnSchulAbschlussIdInFactSchueler.setName("schul_abschluss_id");
        columnSchulAbschlussIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnKlassenWdhInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKlassenWdhInFactSchueler.setName("klassen_wdh");
        columnKlassenWdhInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnMigrationsHgIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMigrationsHgIdInFactSchueler.setName("migrations_hg_id");
        columnMigrationsHgIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnFoerderArtIdInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnFoerderArtIdInFactSchueler.setName("foerder_art_id");
        columnFoerderArtIdInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnAnzahlSchuelerInFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnAnzahlSchuelerInFactSchueler.setName("anzahl_schueler");
        columnAnzahlSchuelerInFactSchueler.setType(SqlSimpleTypes.Sql99.integerType());

        Table tableFactSchueler = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFactSchueler.setName("fact_schueler");
        tableFactSchueler.getFeature()
                .addAll(List.of(columnSchuleIdInFactSchueler, columnSchulJahrIdInFactSchueler,
                        columnGeschlechtIdInFactSchueler, columnWohnLkIdInFactSchueler,
                        columnEinschulungIdInFactSchueler, columnSchulAbschlussIdInFactSchueler,
                        columnKlassenWdhInFactSchueler, columnMigrationsHgIdInFactSchueler,
                        columnFoerderArtIdInFactSchueler, columnAnzahlSchuelerInFactSchueler));

        databaseSchema.getOwnedElement().addAll(List.of(tableSchule, tableGanztagsArt, tableTraeger, tableTraegerArt,
                tableTraegerKategorie, tableScheduleArt, tableSchulJahr, tableAltersGruppe, tableGeschlecht,
                tableEinschulung, tableKlassenWiederholung, tableSchulAbschluss, tableMigrationsHintergrund,
                tableWohnortLandkreis, tableSchulArt, tableSchulKategorie, tableFoerderungArt, tablePersonalArt,
                tableBundesland, tableSonderpaedFoerderbedart, tableFactSchulen, tableFactPersonal, tableFactSchueler));

        TableSource tableQuerySchule = SourceFactory.eINSTANCE.createTableSource();
        tableQuerySchule.setTable(tableSchule);

        TableSource tableQueryGanztagsArt = SourceFactory.eINSTANCE.createTableSource();
        tableQueryGanztagsArt.setTable(tableGanztagsArt);

        TableSource tableQueryTraeger = SourceFactory.eINSTANCE.createTableSource();
        tableQueryTraeger.setTable(tableTraeger);

        TableSource tableQueryTraegerArt = SourceFactory.eINSTANCE.createTableSource();
        tableQueryTraegerArt.setTable(tableTraegerArt);

        TableSource tableQueryTraegerKategorie = SourceFactory.eINSTANCE.createTableSource();
        tableQueryTraegerKategorie.setTable(tableTraegerKategorie);

        TableSource tableQueryScheduleArt = SourceFactory.eINSTANCE.createTableSource();
        tableQueryScheduleArt.setTable(tableScheduleArt);

        TableSource tableQueryScheduleKategorie = SourceFactory.eINSTANCE.createTableSource();
        tableQueryScheduleKategorie.setTable(tableSchulKategorie);

        TableSource tableQuerySchulJahr = SourceFactory.eINSTANCE.createTableSource();
        tableQuerySchulJahr.setTable(tableSchulJahr);

        TableSource tableQueryAltersGruppe = SourceFactory.eINSTANCE.createTableSource();
        tableQueryAltersGruppe.setTable(tableAltersGruppe);

        TableSource tableQueryGeschlecht = SourceFactory.eINSTANCE.createTableSource();
        tableQueryGeschlecht.setTable(tableGeschlecht);

        TableSource tableQueryPersonalArt = SourceFactory.eINSTANCE.createTableSource();
        tableQueryPersonalArt.setTable(tablePersonalArt);

        TableSource tableQueryEinschulung = SourceFactory.eINSTANCE.createTableSource();
        tableQueryEinschulung.setTable(tableEinschulung);

        TableSource tableQueryKlassenWiederholung = SourceFactory.eINSTANCE.createTableSource();
        tableQueryKlassenWiederholung.setTable(tableKlassenWiederholung);

        TableSource tableQuerySchulAbschluss = SourceFactory.eINSTANCE.createTableSource();
        tableQuerySchulAbschluss.setTable(tableSchulAbschluss);

        TableSource tableQueryMigrationsHintergrund = SourceFactory.eINSTANCE.createTableSource();
        tableQueryMigrationsHintergrund.setTable(tableMigrationsHintergrund);

        TableSource tableQueryWohnortLandkreis = SourceFactory.eINSTANCE.createTableSource();
        tableQueryWohnortLandkreis.setTable(tableWohnortLandkreis);

        TableSource tableQueryBundesland = SourceFactory.eINSTANCE.createTableSource();
        tableQueryBundesland.setTable(tableBundesland);

        TableSource tableQueryFoerderungArt = SourceFactory.eINSTANCE.createTableSource();
        tableQueryFoerderungArt.setTable(tableFoerderungArt);

        TableSource tableQuerySonderpaedFoerderbedart = SourceFactory.eINSTANCE.createTableSource();
        tableQuerySonderpaedFoerderbedart.setTable(tableSonderpaedFoerderbedart);

        TableSource tableQueryFactSchulen = SourceFactory.eINSTANCE.createTableSource();
        tableQueryFactSchulen.setTable(tableFactSchulen);

        TableSource tableQueryFactPersonal = SourceFactory.eINSTANCE.createTableSource();
        tableQueryFactPersonal.setTable(tableFactPersonal);

        TableSource tableQueryFactSchueler = SourceFactory.eINSTANCE.createTableSource();
        tableQueryFactSchueler.setTable(tableFactSchueler);

        JoinedQueryElement joinElementSchuleGanztagsartLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleGanztagsartLeft.setKey(columnGanztagsArtIdInSchuleTable);
        joinElementSchuleGanztagsartLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleGanztagsartRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleGanztagsartRight.setKey(columnIdInGanztagsArt);
        joinElementSchuleGanztagsartRight.setQuery(tableQueryGanztagsArt);

        JoinSource joinSchuleGanztagsart = SourceFactory.eINSTANCE.createJoinSource();
        joinSchuleGanztagsart.setLeft(joinElementSchuleGanztagsartLeft);
        joinSchuleGanztagsart.setRight(joinElementSchuleGanztagsartRight);

        JoinedQueryElement joinElementTraegerKategorieArtLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerKategorieArtLeft.setKey(columnTraegerKatIdInTraegerArt);
        joinElementTraegerKategorieArtLeft.setQuery(tableQueryTraegerArt);

        JoinedQueryElement joinElementTraegerKategorieArtRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerKategorieArtRight.setKey(columnIdInTraegerKategorie);
        joinElementTraegerKategorieArtRight.setQuery(tableQueryTraegerKategorie);

        JoinSource joinTraegerKategorieArt = SourceFactory.eINSTANCE.createJoinSource();
        joinTraegerKategorieArt.setLeft(joinElementTraegerKategorieArtLeft);
        joinTraegerKategorieArt.setRight(joinElementTraegerKategorieArtRight);

        JoinedQueryElement joinElementTraegerArtTraegerLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerArtTraegerLeft.setKey(columnTraegerArtIdInTraegerTable);
        joinElementTraegerArtTraegerLeft.setQuery(tableQueryTraeger);

        JoinedQueryElement joinElementTraegerArtTraegerRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementTraegerArtTraegerRight.setKey(columnIdInTraegerArt);
        joinElementTraegerArtTraegerRight.setQuery(joinTraegerKategorieArt);

        JoinSource joinTraegerArtTraeger = SourceFactory.eINSTANCE.createJoinSource();
        joinTraegerArtTraeger.setLeft(joinElementTraegerArtTraegerLeft);
        joinTraegerArtTraeger.setRight(joinElementTraegerArtTraegerRight);

        JoinedQueryElement joinElementSchuleTraegerHierarchyLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleTraegerHierarchyLeft.setKey(columnTraegerIdInSchuleTable);
        joinElementSchuleTraegerHierarchyLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleTraegerHierarchyRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleTraegerHierarchyRight.setKey(columnIdInTraegerTable);
        joinElementSchuleTraegerHierarchyRight.setQuery(joinTraegerArtTraeger);

        JoinSource joinSchuleTraegerHierarchy = SourceFactory.eINSTANCE.createJoinSource();
        joinSchuleTraegerHierarchy.setLeft(joinElementSchuleTraegerHierarchyLeft);
        joinSchuleTraegerHierarchy.setRight(joinElementSchuleTraegerHierarchyRight);

        JoinedQueryElement joinElementSchulkategorieArtLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchulkategorieArtLeft.setKey(columnSchulKategorieInScheduleArt);
        joinElementSchulkategorieArtLeft.setQuery(tableQueryScheduleArt);

        JoinedQueryElement joinElementSchulkategorieArtRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchulkategorieArtRight.setKey(columnIdInSchulKategorie);
        joinElementSchulkategorieArtRight.setQuery(tableQueryScheduleKategorie);

        JoinSource joinSchulkategorieArt = SourceFactory.eINSTANCE.createJoinSource();
        joinSchulkategorieArt.setLeft(joinElementSchulkategorieArtLeft);
        joinSchulkategorieArt.setRight(joinElementSchulkategorieArtRight);

        JoinedQueryElement joinElementSchuleSchulartHierarchyLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleSchulartHierarchyLeft.setKey(columnSchulArtIdInSchuleTable);
        joinElementSchuleSchulartHierarchyLeft.setQuery(tableQuerySchule);

        JoinedQueryElement joinElementSchuleSchulartHierarchyRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementSchuleSchulartHierarchyRight.setKey(columnIdInScheduleArt);
        joinElementSchuleSchulartHierarchyRight.setQuery(joinSchulkategorieArt);

        JoinSource joinSchuleSchulartHierarchy = SourceFactory.eINSTANCE.createJoinSource();
        joinSchuleSchulartHierarchy.setLeft(joinElementSchuleSchulartHierarchyLeft);
        joinSchuleSchulartHierarchy.setRight(joinElementSchuleSchulartHierarchyRight);

        JoinedQueryElement joinElementWohnlandkreisBundeslandLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementWohnlandkreisBundeslandLeft.setKey(columnBundeslandIdInWohnortLandkreis);
        joinElementWohnlandkreisBundeslandLeft.setQuery(tableQueryWohnortLandkreis);

        JoinedQueryElement joinElementWohnlandkreisBundeslandRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementWohnlandkreisBundeslandRight.setKey(columnIdInBundesland);
        joinElementWohnlandkreisBundeslandRight.setQuery(tableQueryBundesland);

        JoinSource joinWohnlandkreisBundesland = SourceFactory.eINSTANCE.createJoinSource();
        joinWohnlandkreisBundesland.setLeft(joinElementWohnlandkreisBundeslandLeft);
        joinWohnlandkreisBundesland.setRight(joinElementWohnlandkreisBundeslandRight);

        JoinedQueryElement joinElementFoerderbedarfArtLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFoerderbedarfArtLeft.setKey(columnSpFoerderbedarfIdInFoerderungArt);
        joinElementFoerderbedarfArtLeft.setQuery(tableQueryFoerderungArt);

        JoinedQueryElement joinElementFoerderbedarfArtRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFoerderbedarfArtRight.setKey(columnIdInSonderpaedFoerderbedart);
        joinElementFoerderbedarfArtRight.setQuery(tableQuerySonderpaedFoerderbedart);

        OrderedColumn orderedColumnSchulNummerInSchuleTable1 = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumnSchulNummerInSchuleTable1.setColumn(columnSchulNummerInSchuleTable);

        OrderedColumn orderedColumnSchulNummerInSchuleTable2 = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumnSchulNummerInSchuleTable2.setColumn(columnSchulNummerInSchuleTable);

        OrderedColumn orderedColumnSchulNummerInSchuleTable3 = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumnSchulNummerInSchuleTable3.setColumn(columnSchulNummerInSchuleTable);

        OrderedColumn orderedColumnOrderInSchulJahr = RelationalFactory.eINSTANCE.createOrderedColumn();
        orderedColumnOrderInSchulJahr.setColumn(columnOrderInSchulJahr);

        JoinSource joinFoerderbedarfArt = SourceFactory.eINSTANCE.createJoinSource();
        joinFoerderbedarfArt.setLeft(joinElementFoerderbedarfArtLeft);
        joinFoerderbedarfArt.setRight(joinElementFoerderbedarfArtRight);

        Level levelGanztagsangebot = LevelFactory.eINSTANCE.createLevel();
        levelGanztagsangebot.setName("Art des Ganztagsangebots");
        levelGanztagsangebot.setColumn(columnIdInGanztagsArt);
        levelGanztagsangebot.setNameColumn(columnSchulUmfangInGanztagsArt);

        Level levelSchule = LevelFactory.eINSTANCE.createLevel();
        levelSchule.setName(SCHULE2);
        levelSchule.setColumn(columnIdInSchuleTable);
        levelSchule.setNameColumn(columnSchulNameInSchuleTable);
        levelSchule.getOrdinalColumns().add(orderedColumnSchulNummerInSchuleTable1);

        Level levelTraegerKategorie = LevelFactory.eINSTANCE.createLevel();
        levelTraegerKategorie.setName("Schulträger-Kategorie");
        levelTraegerKategorie.setColumn(columnIdInTraegerKategorie);
        levelTraegerKategorie.setNameColumn(columnTraegerKategorieInTraegerKategorie);

        Level levelTraegerArt = LevelFactory.eINSTANCE.createLevel();
        levelTraegerArt.setName("Schulträger-Art");
        levelTraegerArt.setColumn(columnIdInTraegerArt);
        levelTraegerArt.setNameColumn(columnTraegerArtInTraegerArt);

        Level levelTraeger = LevelFactory.eINSTANCE.createLevel();
        levelTraeger.setName("Schulträger");
        levelTraeger.setColumn(columnIdInTraegerTable);
        levelTraeger.setNameColumn(columnTraegerNameInTraegerTable);

        Level levelSchuleTraegerschaft = LevelFactory.eINSTANCE.createLevel();
        levelSchuleTraegerschaft.setName(SCHULE2);
        levelSchuleTraegerschaft.setColumn(columnIdInSchuleTable);
        levelSchuleTraegerschaft.setNameColumn(columnSchulNameInSchuleTable);
        levelSchuleTraegerschaft.getOrdinalColumns().add(orderedColumnSchulNummerInSchuleTable2);

        Level levelSchulkategorie = LevelFactory.eINSTANCE.createLevel();
        levelSchulkategorie.setName("Schulkategorie");
        levelSchulkategorie.setColumn(columnIdInSchulKategorie);
        levelSchulkategorie.setNameColumn(columnSchulKategorieNameInSchulKategorie);

        Level levelSchulart = LevelFactory.eINSTANCE.createLevel();
        levelSchulart.setName("Schulart");
        levelSchulart.setColumn(columnIdInSchulArt);
        levelSchulart.setNameColumn(columnSchulartNameInSchulArt);

        Level levelSchuleArt = LevelFactory.eINSTANCE.createLevel();
        levelSchuleArt.setName(SCHULE2);
        levelSchuleArt.setColumn(columnIdInSchuleTable);
        levelSchuleArt.setNameColumn(columnSchulNameInSchuleTable);
        levelSchuleArt.getOrdinalColumns().add(orderedColumnSchulNummerInSchuleTable3);

        Level levelSchuljahr = LevelFactory.eINSTANCE.createLevel();
        levelSchuljahr.setName(SCHULJAHR);
        levelSchuljahr.setColumn(columnIdInSchulJahr);
        levelSchuljahr.setNameColumn(columnSchulJahrInSchulJahr);
        levelSchuljahr.getOrdinalColumns().add(orderedColumnOrderInSchulJahr);

        Level levelAltersgruppe = LevelFactory.eINSTANCE.createLevel();
        levelAltersgruppe.setName("Altersgruppe");
        levelAltersgruppe.setColumn(columnIdInAltersGruppe);
        levelAltersgruppe.setNameColumn(columnAltersgruppeInAltersGruppe);

        Level levelGeschlecht = LevelFactory.eINSTANCE.createLevel();
        levelGeschlecht.setName(GESCHLECHT);
        levelGeschlecht.setColumn(columnIdInGeschlecht);
        levelGeschlecht.setNameColumn(columnBezeichnungInGeschlecht);

        Level levelBerufsgruppe = LevelFactory.eINSTANCE.createLevel();
        levelBerufsgruppe.setName("Berufsgruppe");
        levelBerufsgruppe.setColumn(columnIdInPersonalArt);
        levelBerufsgruppe.setNameColumn(columnBezeichnungInPersonalArt);

        Level levelEinschulung = LevelFactory.eINSTANCE.createLevel();
        levelEinschulung.setName(EINSCHULUNG);
        levelEinschulung.setColumn(columnIdInEinschulung);
        levelEinschulung.setNameColumn(columnEinschulungInEinschulung);

        Level levelKlassenwiederholung = LevelFactory.eINSTANCE.createLevel();
        levelKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        levelKlassenwiederholung.setColumn(columnIdInKlassenWiederholung);
        levelKlassenwiederholung.setNameColumn(columnKlassenwiedlerholungInKlassenWiederholung);

        Level levelSchulabschluss = LevelFactory.eINSTANCE.createLevel();
        levelSchulabschluss.setName("Schulabschlüsse");
        levelSchulabschluss.setColumn(columnIdInSchulAbschluss);
        levelSchulabschluss.setNameColumn(columnSchulabschlussInSchulAbschluss);

        Level levelMigrationshintergrund = LevelFactory.eINSTANCE.createLevel();
        levelMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        levelMigrationshintergrund.setColumn(columnIdInMigrationsHintergrund);
        levelMigrationshintergrund.setNameColumn(columnMigrationsHintergrundInMigrationsHintergrund);

        Level levelBundesland = LevelFactory.eINSTANCE.createLevel();
        levelBundesland.setName("Bundesland");
        levelBundesland.setColumn(columnIdInBundesland);
        levelBundesland.setNameColumn(columnBezeichnungInBundesland);

        Level levelWohnlandkreis = LevelFactory.eINSTANCE.createLevel();
        levelWohnlandkreis.setName(WOHNLANDKREIS);
        levelWohnlandkreis.setColumn(columnIdInWohnortLandkreis);
        levelWohnlandkreis.setNameColumn(columnBezeichnungInWohnortLandkreis);

        Level levelFoerderbedarf = LevelFactory.eINSTANCE.createLevel();
        levelFoerderbedarf.setName("Förderbedarf");
        levelFoerderbedarf.setColumn(columnIdInSonderpaedFoerderbedart);
        levelFoerderbedarf.setNameColumn(columnSonderpaedBedarfInSonderpaedFoerderbedart);

        Level levelFoerderungArt = LevelFactory.eINSTANCE.createLevel();
        levelFoerderungArt.setName("Art der Förderung");
        levelFoerderungArt.setColumn(columnIdInFoerderungArt);
        levelFoerderungArt.setNameColumn(columnFoerderungArtInFoerderungArt);

        ExplicitHierarchy hierarchySchulenGanztagsangebot = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenGanztagsangebot.setHasAll(true);
        hierarchySchulenGanztagsangebot.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenGanztagsangebot.setName("Schulen nach Ganztagsangebot");
        hierarchySchulenGanztagsangebot.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenGanztagsangebot.setQuery(joinSchuleGanztagsart);
        hierarchySchulenGanztagsangebot.getLevels().addAll(List.of(levelGanztagsangebot, levelSchule));

        ExplicitHierarchy hierarchySchulenTraegerschaft = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenTraegerschaft.setHasAll(true);
        hierarchySchulenTraegerschaft.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenTraegerschaft.setName("Schulen nach Trägerschaft");
        hierarchySchulenTraegerschaft.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenTraegerschaft.setQuery(joinSchuleTraegerHierarchy);
        hierarchySchulenTraegerschaft.getLevels()
                .addAll(List.of(levelTraegerKategorie, levelTraegerArt, levelTraeger, levelSchuleTraegerschaft));

        ExplicitHierarchy hierarchySchulenArt = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulenArt.setHasAll(true);
        hierarchySchulenArt.setAllMemberName(ALLE_SCHULEN);
        hierarchySchulenArt.setName("Schulen nach Art");
        hierarchySchulenArt.setPrimaryKey(columnIdInSchuleTable);
        hierarchySchulenArt.setQuery(joinSchuleSchulartHierarchy);
        hierarchySchulenArt.getLevels().addAll(List.of(levelSchulkategorie, levelSchulart, levelSchuleArt));

        ExplicitHierarchy hierarchySchuljahre = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchuljahre.setHasAll(false);
        hierarchySchuljahre.setName("Schuljahre");
        hierarchySchuljahre.setPrimaryKey(columnIdInSchulJahr);
        hierarchySchuljahre.setQuery(tableQuerySchulJahr);
        hierarchySchuljahre.getLevels().addAll(List.of(levelSchuljahr));

        ExplicitHierarchy hierarchyAltersgruppen = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyAltersgruppen.setHasAll(true);
        hierarchyAltersgruppen.setAllMemberName("Alle Altersgruppen");
        hierarchyAltersgruppen.setName("Altersgruppen");
        hierarchyAltersgruppen.setPrimaryKey(columnIdInAltersGruppe);
        hierarchyAltersgruppen.setQuery(tableQueryAltersGruppe);
        hierarchyAltersgruppen.getLevels().addAll(List.of(levelAltersgruppe));

        ExplicitHierarchy hierarchyGeschlecht = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyGeschlecht.setHasAll(true);
        hierarchyGeschlecht.setAllMemberName("Alle Geschlechter");
        hierarchyGeschlecht.setName(GESCHLECHT);
        hierarchyGeschlecht.setPrimaryKey(columnIdInGeschlecht);
        hierarchyGeschlecht.setQuery(tableQueryGeschlecht);
        hierarchyGeschlecht.getLevels().addAll(List.of(levelGeschlecht));

        ExplicitHierarchy hierarchyBerufsgruppen = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyBerufsgruppen.setHasAll(true);
        hierarchyBerufsgruppen.setAllMemberName("Alle Berufsgruppen");
        hierarchyBerufsgruppen.setName("Berufsgruppen");
        hierarchyBerufsgruppen.setPrimaryKey(columnIdInPersonalArt);
        hierarchyBerufsgruppen.setQuery(tableQueryPersonalArt);
        hierarchyBerufsgruppen.getLevels().addAll(List.of(levelBerufsgruppe));

        ExplicitHierarchy hierarchyEinschulung = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyEinschulung.setHasAll(true);
        hierarchyEinschulung.setAllMemberName(GESAMT);
        hierarchyEinschulung.setName(EINSCHULUNG);
        hierarchyEinschulung.setPrimaryKey(columnIdInEinschulung);
        hierarchyEinschulung.setQuery(tableQueryEinschulung);
        hierarchyEinschulung.getLevels().addAll(List.of(levelEinschulung));

        ExplicitHierarchy hierarchyKlassenwiederholung = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyKlassenwiederholung.setHasAll(true);
        hierarchyKlassenwiederholung.setAllMemberName(GESAMT);
        hierarchyKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        hierarchyKlassenwiederholung.setPrimaryKey(columnIdInKlassenWiederholung);
        hierarchyKlassenwiederholung.setQuery(tableQueryKlassenWiederholung);
        hierarchyKlassenwiederholung.getLevels().addAll(List.of(levelKlassenwiederholung));

        ExplicitHierarchy hierarchySchulabschluss = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchySchulabschluss.setHasAll(true);
        hierarchySchulabschluss.setAllMemberName(GESAMT);
        hierarchySchulabschluss.setName("Schulabschlüsse");
        hierarchySchulabschluss.setPrimaryKey(columnIdInSchulAbschluss);
        hierarchySchulabschluss.setQuery(tableQuerySchulAbschluss);
        hierarchySchulabschluss.getLevels().addAll(List.of(levelSchulabschluss));

        ExplicitHierarchy hierarchyMigrationshintergrund = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyMigrationshintergrund.setHasAll(true);
        hierarchyMigrationshintergrund.setAllMemberName(GESAMT);
        hierarchyMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        hierarchyMigrationshintergrund.setPrimaryKey(columnIdInMigrationsHintergrund);
        hierarchyMigrationshintergrund.setQuery(tableQueryMigrationsHintergrund);
        hierarchyMigrationshintergrund.getLevels().addAll(List.of(levelMigrationshintergrund));

        ExplicitHierarchy hierarchyWohnlandkreis = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyWohnlandkreis.setHasAll(true);
        hierarchyWohnlandkreis.setAllMemberName("Alle Wohnlandkreise");
        hierarchyWohnlandkreis.setName(WOHNLANDKREIS);
        hierarchyWohnlandkreis.setPrimaryKey(columnIdInWohnortLandkreis);
        hierarchyWohnlandkreis.setQuery(joinWohnlandkreisBundesland);
        hierarchyWohnlandkreis.getLevels().addAll(List.of(levelBundesland, levelWohnlandkreis));

        ExplicitHierarchy hierarchyFoerderung = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyFoerderung.setHasAll(true);
        hierarchyFoerderung.setAllMemberName(GESAMT);
        hierarchyFoerderung.setName("Sonderpädagogische Förderung");
        hierarchyFoerderung.setPrimaryKey(columnIdInFoerderungArt);
        hierarchyFoerderung.setQuery(joinFoerderbedarfArt);
        hierarchyFoerderung.getLevels().addAll(List.of(levelFoerderbedarf, levelFoerderungArt));

        StandardDimension dimensionSchulen = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionSchulen.setName(SCHULEN);
        dimensionSchulen.getHierarchies()
                .addAll(List.of(hierarchySchulenGanztagsangebot, hierarchySchulenTraegerschaft, hierarchySchulenArt));

        StandardDimension dimensionSchuljahre = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionSchuljahre.setName("Schuljahre");
        dimensionSchuljahre.getHierarchies().addAll(List.of(hierarchySchuljahre));

        StandardDimension dimensionAltersgruppenPersonal = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionAltersgruppenPersonal.setName("Altersgruppen Personal");
        dimensionAltersgruppenPersonal.getHierarchies().addAll(List.of(hierarchyAltersgruppen));

        StandardDimension dimensionGeschlecht = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionGeschlecht.setName(GESCHLECHT);
        dimensionGeschlecht.getHierarchies().addAll(List.of(hierarchyGeschlecht));

        StandardDimension dimensionBerufsgruppenPersonal = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionBerufsgruppenPersonal.setName("Berufsgruppen Personal");
        dimensionBerufsgruppenPersonal.getHierarchies().addAll(List.of(hierarchyBerufsgruppen));

        StandardDimension dimensionEinschulungen = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionEinschulungen.setName("Einschulungen");
        dimensionEinschulungen.getHierarchies().addAll(List.of(hierarchyEinschulung));

        StandardDimension dimensionKlassenwiederholung = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionKlassenwiederholung.setName(KLASSENWIEDERHOLUNG);
        dimensionKlassenwiederholung.getHierarchies().addAll(List.of(hierarchyKlassenwiederholung));

        StandardDimension dimensionSchulabschluss = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionSchulabschluss.setName("Schulabschluss");
        dimensionSchulabschluss.getHierarchies().addAll(List.of(hierarchySchulabschluss));

        StandardDimension dimensionMigrationshintergrund = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionMigrationshintergrund.setName(MIGRATIONSHINTERGRUND);
        dimensionMigrationshintergrund.getHierarchies().addAll(List.of(hierarchyMigrationshintergrund));

        StandardDimension dimensionWohnlandkreis = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionWohnlandkreis.setName(WOHNLANDKREIS);
        dimensionWohnlandkreis.getHierarchies().addAll(List.of(hierarchyWohnlandkreis));

        StandardDimension dimensionInklusion = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionInklusion.setName("Inklusion");
        dimensionInklusion.getHierarchies().addAll(List.of(hierarchyFoerderung));

        SumMeasure measureAnzahlSchulen = MeasureFactory.eINSTANCE.createSumMeasure();
        measureAnzahlSchulen.setName("Anzahl Schulen");
        measureAnzahlSchulen.setColumn(columnAnzahlSchulenInFactSchulen);

        SumMeasure measureAnzahlKlassen = MeasureFactory.eINSTANCE.createSumMeasure();
        measureAnzahlKlassen.setName("Anzahl Klassen");
        measureAnzahlKlassen.setColumn(columnAnzahlKlassenInFactSchulen);

        SumMeasure measureAnzahlPersonen = MeasureFactory.eINSTANCE.createSumMeasure();
        measureAnzahlPersonen.setName("Anzahl Personen");
        measureAnzahlPersonen.setColumn(columnAnzahlPersonenInFactPersonal);

        SumMeasure measureAnzahlSchuelerInnen = MeasureFactory.eINSTANCE.createSumMeasure();
        measureAnzahlSchuelerInnen.setName("Anzahl SchülerInnen");
        measureAnzahlSchuelerInnen.setColumn(columnAnzahlSchuelerInFactSchueler);

        MeasureGroup measureGroupSchulenInstitutionen = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupSchulenInstitutionen.getMeasures().addAll(List.of(measureAnzahlSchulen, measureAnzahlKlassen));

        MeasureGroup measureGroupPaedagogischesPersonal = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupPaedagogischesPersonal.getMeasures().addAll(List.of(measureAnzahlPersonen));

        MeasureGroup measureGroupSchuelerInnen = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupSchuelerInnen.getMeasures().addAll(List.of(measureAnzahlSchuelerInnen));

        DimensionConnector connectorSchulen1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen1.setOverrideDimensionName(SCHULEN);
        connectorSchulen1.setDimension(dimensionSchulen);
        connectorSchulen1.setForeignKey(columnSchuleIdInFactSchulen);

        DimensionConnector connectorSchuljahr1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr1.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr1.setDimension(dimensionSchuljahre);
        connectorSchuljahr1.setForeignKey(columnSchulJahrIdInFactSchulen);

        PhysicalCube cubeSchulenInstitutionen = CubeFactory.eINSTANCE.createPhysicalCube();
        cubeSchulenInstitutionen.setName("Schulen in Jena (Institutionen)");
        cubeSchulenInstitutionen.setQuery(tableQueryFactSchulen);
        cubeSchulenInstitutionen.getDimensionConnectors().addAll(List.of(connectorSchulen1, connectorSchuljahr1));
        cubeSchulenInstitutionen.getMeasureGroups().addAll(List.of(measureGroupSchulenInstitutionen));

        DimensionConnector connectorSchulen2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen2.setOverrideDimensionName(SCHULEN);
        connectorSchulen2.setDimension(dimensionSchulen);
        connectorSchulen2.setForeignKey(columnSchuleIdInFactPersonal);

        DimensionConnector connectorSchuljahr2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr2.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr2.setDimension(dimensionSchuljahre);
        connectorSchuljahr2.setForeignKey(columnSchulJahrIdInFactPersonal);

        DimensionConnector connectorAltersgruppe = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorAltersgruppe.setOverrideDimensionName("Altersgruppe");
        connectorAltersgruppe.setDimension(dimensionAltersgruppenPersonal);
        connectorAltersgruppe.setForeignKey(columnAltersGroupIdInFactPersonal);

        DimensionConnector connectorGeschlecht = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorGeschlecht.setOverrideDimensionName(GESCHLECHT);
        connectorGeschlecht.setDimension(dimensionGeschlecht);
        connectorGeschlecht.setForeignKey(columnGeschlechtIdInFactPersonal);

        DimensionConnector connectorBerufsgruppe = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorBerufsgruppe.setOverrideDimensionName("Berufsgruppe");
        connectorBerufsgruppe.setDimension(dimensionBerufsgruppenPersonal);
        connectorBerufsgruppe.setForeignKey(columnPersonalArtIdInFactPersonal);

        PhysicalCube cubePaedagogischesPersonal = CubeFactory.eINSTANCE.createPhysicalCube();
        cubePaedagogischesPersonal.setName("Pädagogisches Personal an Jenaer Schulen");
        cubePaedagogischesPersonal.setQuery(tableQueryFactPersonal);
        cubePaedagogischesPersonal.getDimensionConnectors().addAll(List.of(connectorSchulen2, connectorSchuljahr2,
                connectorAltersgruppe, connectorGeschlecht, connectorBerufsgruppe));
        cubePaedagogischesPersonal.getMeasureGroups().addAll(List.of(measureGroupPaedagogischesPersonal));

        DimensionConnector connectorSchulen3 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchulen3.setOverrideDimensionName(SCHULEN);
        connectorSchulen3.setDimension(dimensionSchulen);
        connectorSchulen3.setForeignKey(columnSchuleIdInFactSchueler);

        DimensionConnector connectorSchuljahr3 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchuljahr3.setOverrideDimensionName(SCHULJAHR);
        connectorSchuljahr3.setDimension(dimensionSchuljahre);
        connectorSchuljahr3.setForeignKey(columnSchulJahrIdInFactSchueler);

        DimensionConnector connectorGeschlecht3 = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorGeschlecht3.setOverrideDimensionName(GESCHLECHT);
        connectorGeschlecht3.setDimension(dimensionGeschlecht);
        connectorGeschlecht3.setForeignKey(columnGeschlechtIdInFactSchueler);

        DimensionConnector connectorWohnlandkreis = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorWohnlandkreis.setOverrideDimensionName(WOHNLANDKREIS);
        connectorWohnlandkreis.setDimension(dimensionWohnlandkreis);
        connectorWohnlandkreis.setForeignKey(columnWohnLkIdInFactSchueler);

        DimensionConnector connectorEinschulung = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorEinschulung.setOverrideDimensionName(EINSCHULUNG);
        connectorEinschulung.setDimension(dimensionEinschulungen);
        connectorEinschulung.setForeignKey(columnEinschulungIdInFactSchueler);

        DimensionConnector connectorSchulabschluss = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSchulabschluss.setOverrideDimensionName("Schulabschluss");
        connectorSchulabschluss.setDimension(dimensionSchulabschluss);
        connectorSchulabschluss.setForeignKey(columnSchulAbschlussIdInFactSchueler);

        DimensionConnector connectorKlassenwiederholung = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorKlassenwiederholung.setOverrideDimensionName(KLASSENWIEDERHOLUNG);
        connectorKlassenwiederholung.setDimension(dimensionKlassenwiederholung);
        connectorKlassenwiederholung.setForeignKey(columnKlassenWdhInFactSchueler);

        DimensionConnector connectorMigrationshintergrund = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorMigrationshintergrund.setOverrideDimensionName(MIGRATIONSHINTERGRUND);
        connectorMigrationshintergrund.setDimension(dimensionMigrationshintergrund);
        connectorMigrationshintergrund.setForeignKey(columnMigrationsHgIdInFactSchueler);

        DimensionConnector connectorSonderpaedagogischeFoerderung = DimensionFactory.eINSTANCE.createDimensionConnector();
        connectorSonderpaedagogischeFoerderung.setOverrideDimensionName("Sonderpädagogische Förderung");
        connectorSonderpaedagogischeFoerderung.setDimension(dimensionInklusion);
        connectorSonderpaedagogischeFoerderung.setForeignKey(columnFoerderArtIdInFactSchueler);

        PhysicalCube cubeSchuelerInnen = CubeFactory.eINSTANCE.createPhysicalCube();
        cubeSchuelerInnen.setName("Schüler:innen an Jenaer Schulen");
        cubeSchuelerInnen.setQuery(tableQueryFactSchueler);
        cubeSchuelerInnen.getDimensionConnectors()
                .addAll(List.of(connectorSchulen3, connectorSchuljahr3, connectorGeschlecht3, connectorWohnlandkreis,
                        connectorEinschulung, connectorSchulabschluss, connectorKlassenwiederholung,
                        connectorMigrationshintergrund, connectorSonderpaedagogischeFoerderung));
        cubeSchuelerInnen.getMeasureGroups().addAll(List.of(measureGroupSchuelerInnen));

        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG_NAME);
        catalog.setId("_catalog_schulwesen");
        catalog.getCubes().addAll(List.of(cubeSchulenInstitutionen, cubePaedagogischesPersonal, cubeSchuelerInnen));
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
