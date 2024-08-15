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
package org.eclipse.daanse.rolap.mapping.instance.complex.school;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
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

    private static final String SCHEMA_NAME = "Schulwesen";

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    private static final TableQueryMappingImpl SCHEDULE_TABLE =
        TableQueryMappingImpl.builder().withName(SCHULE).build();
    private static final TableQueryMappingImpl GANZTAGS_ART_TABLE = TableQueryMappingImpl.builder().withName(
        "ganztags_art").build();
    private static final TableQueryMappingImpl TRAEGER_TABLE =
        TableQueryMappingImpl.builder().withName("traeger").build();
    private static final TableQueryMappingImpl TRAEGER_ART_TABLE =
        TableQueryMappingImpl.builder().withName(TRAEGER_ART).build();
    private static final TableQueryMappingImpl TRAEGER_KATEGORIE_TABLE =
        TableQueryMappingImpl.builder().withName(TRAEGER_KATEGORIE).build();
    private static final TableQueryMappingImpl SCHEDULE_ART_TABLE = TableQueryMappingImpl.builder().withName(
        "schule_art").build();
    private static final TableQueryMappingImpl SCHEDULE_KATEGORIE_TABLE = TableQueryMappingImpl.builder().withName(
        "schul_kategorie").build();
    private static final TableQueryMappingImpl SCHUL_JAHT_TABLE =
        TableQueryMappingImpl.builder().withName(SCHUL_JAHR).build();
    private static final TableQueryMappingImpl ALTERS_GRUPPE_TABLE = TableQueryMappingImpl.builder().withName(
        "alters_gruppe").build();
    private static final TableQueryMappingImpl GESCHLECHT_TABLE = TableQueryMappingImpl.builder().withName(
        "geschlecht").build();
    private static final TableQueryMappingImpl PERSONAL_ART_TABLE = TableQueryMappingImpl.builder().withName(
        "personal_art").build();
    private static final TableQueryMappingImpl EINSCHULUNG_TABLE =
        TableQueryMappingImpl.builder().withName(EINSCHULUNG2).build();
    private static final TableQueryMappingImpl KLASSEN_WIEDERHOLUNG_TABLE = TableQueryMappingImpl.builder().withName(
        "klassen_wiederholung").build();
    private static final TableQueryMappingImpl SCHUL_ABSCHLUSS_TABLE = TableQueryMappingImpl.builder().withName(
        "schul_abschluss").build();
    private static final TableQueryMappingImpl MIGRATIONS_HINTERGRUND_TABLE =
        TableQueryMappingImpl.builder().withName(MIGRATIONS_HINTERGRUND).build();
    private static final TableQueryMappingImpl WOHNORT_LANDKREIS_TABLE =
        TableQueryMappingImpl.builder().withName(WOHNORT_LANDKREIS).build();
    private static final TableQueryMappingImpl BUNDESLAND_TABLE = TableQueryMappingImpl.builder().withName(
        "bundesland").build();
    private static final TableQueryMappingImpl FOERDERUNG_ART_TABLE =
        TableQueryMappingImpl.builder().withName(FOERDERUNG_ART).build();
    private static final TableQueryMappingImpl SONDERPAED_FOERDERBEDART_TABLE =
        TableQueryMappingImpl.builder().withName("sonderpaed_foerderbedarf").build();
    private static final TableQueryMappingImpl FACT_SCHULEN_TABLE = TableQueryMappingImpl.builder().withName(
        "fact_schulen").build();
    private static final TableQueryMappingImpl FACT_PERSONAM_TABLE = TableQueryMappingImpl.builder().withName(
        "fact_personal").build();
    private static final TableQueryMappingImpl FACT_SCHUELER_TABLE = TableQueryMappingImpl.builder().withName(
        "fact_schueler").build();

    private static final JoinQueryMappingImpl JOIN1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("ganztags_art_id")
            .withQuery(SCHEDULE_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(GANZTAGS_ART_TABLE)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2_1_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("traeger_kat_id")
            .withQuery(TRAEGER_ART_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(TRAEGER_KATEGORIE_TABLE)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("traeger_id")
            .withQuery(TRAEGER_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(JOIN2_1_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN2 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("traeger_art_id")
            .withQuery(SCHEDULE_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(JOIN2_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN3_1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("schul_kategorie_id")
            .withQuery(SCHEDULE_ART_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(SCHEDULE_KATEGORIE_TABLE)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN3 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("schul_art_id")
            .withQuery(SCHEDULE_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(JOIN3_1)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN4 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("bundesland_id")
            .withQuery(WOHNORT_LANDKREIS_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(BUNDESLAND_TABLE)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN5 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("sp_foerderbedarf_id")
            .withQuery(FOERDERUNG_ART_TABLE)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(SONDERPAED_FOERDERBEDART_TABLE)
            .build())
        .build();

    private static final LevelMappingImpl LEVEL1 = LevelMappingImpl
        .builder()
        .withName("Art des Ganztagsangebots")
        .withColumn("id")
        .withNameColumn("schul_umfang")
        .withTable("ganztags_art")
        .build();

    private static final LevelMappingImpl LEVEL2 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn("id")
        .withNameColumn(SCHUL_NAME)
        .withOrdinalColumn(SCHUL_NUMMER)
        .withTable(SCHULE)
        .build();

    private static final LevelMappingImpl LEVEL3 = LevelMappingImpl
        .builder()
        .withName("Schulträger-Kategorie")
        .withColumn("id")
        .withNameColumn(TRAEGER_KATEGORIE)
        .withTable(TRAEGER_KATEGORIE)
        .build();

    private static final LevelMappingImpl LEVEL4 = LevelMappingImpl
        .builder()
        .withName("Schulträger-Art")
        .withColumn("id")
        .withNameColumn(TRAEGER_ART)
        .withTable(TRAEGER_ART)
        .build();

    private static final LevelMappingImpl LEVEL5 = LevelMappingImpl
        .builder()
        .withName("Schulträger")
        .withColumn("id")
        .withNameColumn("traeger_name")
        .withTable("traeger")
        .build();

    private static final LevelMappingImpl LEVEL6 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn("id")
        .withNameColumn(SCHUL_NAME)
        .withOrdinalColumn(SCHUL_NUMMER)
        .withTable(SCHULE)
        .build();

    private static final LevelMappingImpl LEVEL7 = LevelMappingImpl
        .builder()
        .withName("Schulkategorie")
        .withColumn("id")
        .withNameColumn("schul_kategorie_name")
        .withTable("schul_kategorie")
        .build();

    private static final LevelMappingImpl LEVEL8 = LevelMappingImpl
        .builder()
        .withName("Schulart")
        .withColumn("id")
        .withNameColumn("schulart_name")
        .withTable("schul_art")
        .build();

    private static final LevelMappingImpl LEVEL9 = LevelMappingImpl
        .builder()
        .withName(SCHULE2)
        .withColumn("id")
        .withNameColumn(SCHUL_NAME)
        .withOrdinalColumn(SCHUL_NUMMER)
        .withTable(SCHULE)
        .build();

    private static final LevelMappingImpl LEVEL10 = LevelMappingImpl
        .builder()
        .withName(SCHULJAHR)
        .withColumn("id")
        .withNameColumn(SCHUL_JAHR)
        .withOrdinalColumn("order")
        .build();

    private static final LevelMappingImpl LEVEL11 = LevelMappingImpl
        .builder()
        .withName("Altersgruppe")
        .withColumn("id")
        .withNameColumn("altersgruppe")
        .build();

    private static final LevelMappingImpl LEVEL12 = LevelMappingImpl
        .builder()
        .withName(GESCHLECHT)
        .withColumn("id")
        .withNameColumn(BEZEICHNUNG)
        .build();

    private static final LevelMappingImpl LEVEL13 = LevelMappingImpl
        .builder()
        .withName("Berufsgruppe")
        .withColumn("id")
        .withNameColumn(BEZEICHNUNG)
        .build();

    private static final LevelMappingImpl LEVEL14 = LevelMappingImpl
        .builder()
        .withName(EINSCHULUNG)
        .withColumn("id")
        .withNameColumn(EINSCHULUNG2)
        .build();

    private static final LevelMappingImpl LEVEL15 = LevelMappingImpl
        .builder()
        .withName(KLASSENWIEDERHOLUNG)
        .withColumn("id")
        .withNameColumn("klassenwiederholung")
        .build();

    private static final LevelMappingImpl LEVEL16 = LevelMappingImpl
        .builder()
        .withName("Schulabschlüsse")
        .withColumn("id")
        .withNameColumn("schulabschluss")
        .build();

    private static final LevelMappingImpl LEVEL17 = LevelMappingImpl
        .builder()
        .withName(MIGRATIONSHINTERGRUND)
        .withColumn("id")
        .withNameColumn(MIGRATIONS_HINTERGRUND)
        .build();

    private static final LevelMappingImpl LEVEL18 = LevelMappingImpl
        .builder()
        .withName("Bundesland")
        .withColumn("id")
        .withNameColumn(BEZEICHNUNG)
        .withTable("bundesland")
        .build();

    private static final LevelMappingImpl LEVEL19 = LevelMappingImpl
        .builder()
        .withName(WOHNLANDKREIS)
        .withColumn("id")
        .withNameColumn(BEZEICHNUNG)
        .withTable(WOHNORT_LANDKREIS)
        .build();

    private static final LevelMappingImpl LEVEL20 = LevelMappingImpl
        .builder()
        .withName("Förderbedarf")
        .withColumn("id")
        .withNameColumn("sonderpaed_bedarf")
        .withTable("sonderpaed_foerderbedarf")
        .build();

    private static final LevelMappingImpl LEVEL21 = LevelMappingImpl
        .builder()
        .withName("Art der Förderung")
        .withColumn("id")
        .withNameColumn(FOERDERUNG_ART)
        .withTable(FOERDERUNG_ART)
        .build();

    private static final HierarchyMappingImpl HIERARCHY1 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Ganztagsangebot")
        .withPrimaryKey("id")
        .withPrimaryKeyTable(SCHULE)
        .withQuery(JOIN1)
        .withLevels(List.of(LEVEL1, LEVEL2))
        .build();

    private static final HierarchyMappingImpl HIERARCHY2 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Trägerschaft")
        .withPrimaryKey("id")
        .withPrimaryKeyTable(SCHULE)
        .withQuery(JOIN2)
        .withLevels(List.of(LEVEL3, LEVEL4, LEVEL5, LEVEL6))
        .build();

    private static final HierarchyMappingImpl HIERARCHY3 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(ALLE_SCHULEN)
        .withName("Schulen nach Art")
        .withPrimaryKey("id")
        .withPrimaryKeyTable(SCHULE)
        .withQuery(JOIN3)
        .withLevels(List.of(LEVEL7, LEVEL8, LEVEL9))
        .build();

    private static final HierarchyMappingImpl HIERARCHY4 = HierarchyMappingImpl
        .builder()
        .withHasAll(false)
        .withName("Schuljahre")
        .withPrimaryKey("id")
        .withPrimaryKeyTable(SCHUL_JAHR)
        .withQuery(SCHUL_JAHT_TABLE)
        .withLevels(List.of(LEVEL10))
        .build();

    private static final HierarchyMappingImpl HIERARCHY5 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Altersgruppen")
        .withName("Altersgruppen")
        .withPrimaryKey("id")
        .withPrimaryKeyTable("alters_gruppe")
        .withQuery(ALTERS_GRUPPE_TABLE)
        .withLevels(List.of(LEVEL11))
        .build();

    private static final HierarchyMappingImpl HIERARCHY6 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Geschlechter")
        .withName(GESCHLECHT)
        .withPrimaryKey("id")
        .withPrimaryKeyTable("geschlecht")
        .withQuery(GESCHLECHT_TABLE)
        .withLevels(List.of(LEVEL12))
        .build();

    private static final HierarchyMappingImpl HIERARCHY7 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Berufsgruppen")
        .withName("Berufsgruppen")
        .withPrimaryKey("id")
        .withPrimaryKeyTable("personal_art")
        .withQuery(PERSONAL_ART_TABLE)
        .withLevels(List.of(LEVEL13))
        .build();

    private static final HierarchyMappingImpl HIERARCHY8 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(EINSCHULUNG)
        .withPrimaryKey("id")
        .withPrimaryKeyTable(EINSCHULUNG2)
        .withQuery(EINSCHULUNG_TABLE)
        .withLevels(List.of(LEVEL14))
        .build();

    private static final HierarchyMappingImpl HIERARCHY9 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(KLASSENWIEDERHOLUNG)
        .withPrimaryKey("id")
        .withPrimaryKeyTable("klassen_wiederholung")
        .withQuery(KLASSEN_WIEDERHOLUNG_TABLE)
        .withLevels(List.of(LEVEL15))
        .build();

    private static final HierarchyMappingImpl HIERARCHY10 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName("Schulabschlüsse")
        .withPrimaryKey("id")
        .withPrimaryKeyTable("schul_abschluss")
        .withQuery(SCHUL_ABSCHLUSS_TABLE)
        .withLevels(List.of(LEVEL16))
        .build();

    private static final HierarchyMappingImpl HIERARCHY11 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName(MIGRATIONSHINTERGRUND)
        .withPrimaryKey("id")
        .withPrimaryKeyTable(MIGRATIONS_HINTERGRUND)
        .withQuery(MIGRATIONS_HINTERGRUND_TABLE)
        .withLevels(List.of(LEVEL17))
        .build();

    private static final HierarchyMappingImpl HIERARCHY12 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName("Alle Wohnlandkreise")
        .withName(WOHNLANDKREIS)
        .withPrimaryKey("id")
        .withPrimaryKeyTable(WOHNORT_LANDKREIS)
        .withQuery(JOIN4)
        .withLevels(List.of(LEVEL18, LEVEL19))
        .build();

    private static final HierarchyMappingImpl HIERARCHY13 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withAllMemberName(GESAMT)
        .withName("Sonderpädagogische Förderung")
        .withPrimaryKey("id")
        .withPrimaryKeyTable(FOERDERUNG_ART)
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
        .withColumn("anzahl_schulen")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure2 = MeasureMappingImpl.builder()
        .withName("Anzahl Klassen")
        .withColumn("Anzahl Klassen")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure3 = MeasureMappingImpl.builder()
        .withName("Anzahl Personen")
        .withColumn("anzahl_personen")
        .withAggregatorType(MeasureAggregatorType.SUM)
        .build();

    private static final MeasureMappingImpl measure4 = MeasureMappingImpl.builder()
        .withName("Anzahl Schüler:innen")
        .withColumn("anzahl_schueler")
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
        .withQuery(FACT_SCHULEN_TABLE)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID).build()
        ))
        .withMeasureGroups(List.of(CUBE1_MEASURE_GROUP))
        .build();

    private static final PhysicalCubeMappingImpl CUBE2 = PhysicalCubeMappingImpl
        .builder()
        .withName("Pädagogisches Personal an Jenaer Schulen")
        .withQuery(FACT_PERSONAM_TABLE)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Altersgruppe").withDimension(ALTERSGRUPPEN_PERSONAL_DIMENSION).withForeignKey("alters_gruppe_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GESCHLECHT).withDimension(GESCHLECHT_DIMENSION).withForeignKey("geschlecht_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Berufsgruppe").withDimension(Berufsgruppen_Personal_DIMENSION).withForeignKey("personal_art_id").build()
        ))
        .withMeasureGroups(List.of(CUBE2_MEASURE_GROUP))
        .build();

    private static final PhysicalCubeMappingImpl CUBE3 = PhysicalCubeMappingImpl
        .builder()
        .withName("Schüler:innen an Jenaer Schulen")
        .withQuery(FACT_SCHUELER_TABLE)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULEN).withDimension(SCHULEN_DIMENSION).withForeignKey(SCHULE_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(SCHULJAHR).withDimension(SCHULJAHRE_DIMENSION).withForeignKey(SCHUL_JAHR_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GESCHLECHT).withDimension(GESCHLECHT_DIMENSION).withForeignKey("geschlecht_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(WOHNLANDKREIS).withDimension(Wohnlandkreis_DIMENSION).withForeignKey("wohn_lk_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(EINSCHULUNG).withDimension(Einschulungen_DIMENSION).withForeignKey("einschulung_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Schulabschluss").withDimension(Schulabschluss_DIMENSION).withForeignKey("schul_abschluss_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(KLASSENWIEDERHOLUNG).withDimension(Klassenwiederholung_DIMENSION).withForeignKey("klassen_wdh").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(MIGRATIONSHINTERGRUND).withDimension(Migrationshintergrund_DIMENSION).withForeignKey("migrations_hg_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Sonderpädagogische Förderung").withDimension(Inklusion_DIMENSION).withForeignKey("foerder_art_id").build()
        ))
        .withMeasureGroups(List.of(CUBE3_MEASURE_GROUP))
        .build();

    private static final SchemaMappingImpl
        SCHEMA = SchemaMappingImpl.builder()
        .withName(SCHEMA_NAME)
        .withCubes(List.of(CUBE1, CUBE2, CUBE3))
        .build();

    @Override
    public CatalogMapping get() {
        return CatalogMappingImpl.builder()
            .withName(SCHEMA_NAME)
            .withDocumentation(documentation)
            .withSchemas(List.of(SCHEMA))
            .build();
    }

}
