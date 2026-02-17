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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TimeDimension;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.3", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    // Static columns - Fact Table (einwohner)
    public static final Column COLUMN_JAHR_EINWOHNER;
    public static final Column COLUMN_STATBEZ_EINWOHNER;
    public static final Column COLUMN_KER_GESCH_EINWOHNER;
    public static final Column COLUMN_AGE_EINWOHNER;
    public static final Column COLUMN_ANZAHL_EINWOHNER;
    public static final Column COLUMN_GEOJSON_EINWOHNER;

    // Static columns - Year Table
    public static final Column COLUMN_YEAR_YEAR;
    public static final Column COLUMN_ORDINAL_YEAR;

    // Static columns - Town Table
    public static final Column COLUMN_ID_TOWN;
    public static final Column COLUMN_NAME_TOWN;
    public static final Column COLUMN_GEOJSON_TOWN;

    // Static columns - Plraum Table
    public static final Column COLUMN_GID_PLRAUM;
    public static final Column COLUMN_PLRAUM_PLRAUM;
    public static final Column COLUMN_UUID_PLRAUM;
    public static final Column COLUMN_GEOJSON_PLRAUM;
    public static final Column COLUMN_TOWNID_PLRAUM;

    // Static columns - Statbez Table
    public static final Column COLUMN_GID_STATBEZ;
    public static final Column COLUMN_PLRAUM_STATBEZ;
    public static final Column COLUMN_STATBEZ_NAME_STATBEZ;
    public static final Column COLUMN_UUID_STATBEZ;
    public static final Column COLUMN_GEOJSON_STATBEZ;

    // Static columns - Gender Table
    public static final Column COLUMN_KEY_GENDER;
    public static final Column COLUMN_NAME_GENDER;

    // Static columns - Age Groups Table
    public static final Column COLUMN_AGE_AGEGROUPS;
    public static final Column COLUMN_H1_AGEGROUPS;
    public static final Column COLUMN_H1_ORDER_AGEGROUPS;
    public static final Column COLUMN_H2_AGEGROUPS;
    public static final Column COLUMN_H2_ORDER_AGEGROUPS;
    public static final Column COLUMN_H7_AGEGROUPS;
    public static final Column COLUMN_H7_ORDER_AGEGROUPS;
    public static final Column COLUMN_H8_AGEGROUPS;
    public static final Column COLUMN_H8_ORDER_AGEGROUPS;
    public static final Column COLUMN_H9_AGEGROUPS;
    public static final Column COLUMN_H9_ORDER_AGEGROUPS;

    // Static tables
    public static final PhysicalTable TABLE_EINWOHNER;
    public static final PhysicalTable TABLE_YEAR;
    public static final PhysicalTable TABLE_TOWN;
    public static final PhysicalTable TABLE_PLRAUM;
    public static final PhysicalTable TABLE_STATBEZ;
    public static final PhysicalTable TABLE_GENDER;
    public static final PhysicalTable TABLE_AGEGROUPS;

    public static final OrderedColumn ORDERED_COLUMN_ORDINAL_YEAR;
    public static final OrderedColumn ORDERED_COLUMN_H1_ORDER_AGEGROUPS;
    public static final OrderedColumn ORDERED_COLUMN_H2_ORDER_AGEGROUPS;
    public static final OrderedColumn ORDERED_COLUMN_H7_ORDER_AGEGROUPS;
    public static final OrderedColumn ORDERED_COLUMN_H8_ORDER_AGEGROUPS;
    public static final OrderedColumn ORDERED_COLUMN_H9_ORDER_AGEGROUPS;

    // Static levels
    public static final Level LEVEL_JAHR;
    public static final Level LEVEL_STADT;
    public static final Level LEVEL_PLANUNGSRAUM;
    public static final Level LEVEL_STATISTISCHER_BEZIRK;
    public static final Level LEVEL_GESCHLECHT;
    public static final Level LEVEL_ALTER_EINZELJAHRGAENGE;
    public static final Level LEVEL_ALTERSGRUPPE_STANDARD;
    public static final Level LEVEL_ALTER_STANDARD;
    public static final Level LEVEL_ALTERSGRUPPE_KINDER;
    public static final Level LEVEL_ALTER_KINDER;
    public static final Level LEVEL_ALTERSGRUPPE_RKI_H7;
    public static final Level LEVEL_ALTER_RKI_H7;
    public static final Level LEVEL_ALTERSGRUPPE_RKI_H8;
    public static final Level LEVEL_ALTER_RKI_H8;
    public static final Level LEVEL_ALTERSGRUPPE_10JAHRE;
    public static final Level LEVEL_ALTER_10JAHRE;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_JAHR;
    public static final ExplicitHierarchy HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK;
    public static final ExplicitHierarchy HIERARCHY_GESCHLECHT;
    public static final ExplicitHierarchy HIERARCHY_ALTER_EINZELJAHRGAENGE;
    public static final ExplicitHierarchy HIERARCHY_ALTERSGRUPPEN_STANDARD;
    public static final ExplicitHierarchy HIERARCHY_ALTERSGRUPPEN_KINDER;
    public static final ExplicitHierarchy HIERARCHY_ALTERSGRUPPEN_RKI_H7;
    public static final ExplicitHierarchy HIERARCHY_ALTERSGRUPPEN_RKI_H8;
    public static final ExplicitHierarchy HIERARCHY_ALTERSGRUPPEN_10JAHRE;

    // Static dimensions
    public static final TimeDimension DIMENSION_JAHR;
    public static final StandardDimension DIMENSION_STATISTISCHER_BEZIRK;
    public static final StandardDimension DIMENSION_GESCHLECHT;
    public static final StandardDimension DIMENSION_ALTER;

    // Static cube
    public static final PhysicalCube CUBE_BEVOELKERUNG;

    // Static table queries
    public static final TableQuery TABLEQUERY_YEAR;
    public static final TableQuery TABLEQUERY_TOWN;
    public static final TableQuery TABLEQUERY_PLRAUM;
    public static final TableQuery TABLEQUERY_STATBEZ;
    public static final TableQuery TABLEQUERY_GENDER;
    public static final TableQuery TABLEQUERY_AGEGROUPS;
    public static final TableQuery TABLEQUERY_FACT;

    // Join queries
    public static final JoinQuery JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_JAHR;
    public static final DimensionConnector CONNECTOR_STATISTISCHER_BEZIRK;
    public static final DimensionConnector CONNECTOR_GESCHLECHT;
    public static final DimensionConnector CONNECTOR_ALTER;

    // Static measures and measure group
    public static final SumMeasure MEASURE_EINWOHNERZAHL;
    public static final MeasureGroup MEASUREGROUP_BEVOELKERUNG;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_POPULATION_JENA;
    public static final Catalog CATALOG_POPULATION_JENA;

    private static final String populationJenaBody = """
            Bevölkerungsstatistik Jena ist eine Beispieldatenbank für demografische Analysen der Stadt Jena.
            Sie enthält Einwohnerdaten mit geografischen, zeitlichen und soziodemografischen Dimensionen
            für detaillierte Bevölkerungsanalysen und Stadtplanung.
            """;

    private static final String bevoelkerungCubeBody = """
            Der Bevölkerungs-Würfel enthält Einwohnerzahlen mit demografischen und geografischen Aufschlüsselungen.
            Er ermöglicht Analysen nach Stadtteilen, Altersgruppen, Geschlecht und Zeitverläufen.
            """;

    private static final String jahrBody = """
            Die Jahr-Dimension ermöglicht zeitliche Analysen der Bevölkerungsentwicklung
            über verschiedene Jahre hinweg für Trendanalysen und Vergleiche.
            """;

    private static final String geografieBody = """
            Die Geografie-Dimension stellt die administrative und statistische Gliederung
            der Stadt Jena dar mit Hierarchie von Stadt über Planungsräume zu statistischen Bezirken.
            """;

    private static final String geschlechtBody = """
            Die Geschlecht-Dimension ermöglicht geschlechtsspezifische demografische Analysen
            mit Kategorien männlich, weiblich und divers.
            """;

    private static final String alterBody = """
            Die Alter-Dimension bietet verschiedene Altersgruppierungen für demografische Analysen,
            einschließlich Einzeljahrgänge und verschiedene Gruppierungssysteme für unterschiedliche Analysezwecke.
            """;

    static {
        // Initialize Einwohner (Fact) columns
        COLUMN_JAHR_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_JAHR_EINWOHNER.setName("JAHR");
        COLUMN_JAHR_EINWOHNER.setId("_column_einwohner_jahr");
        COLUMN_JAHR_EINWOHNER.setType(ColumnType.INTEGER);

        COLUMN_STATBEZ_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATBEZ_EINWOHNER.setName("STATBEZ");
        COLUMN_STATBEZ_EINWOHNER.setId("_column_einwohner_statbez");
        COLUMN_STATBEZ_EINWOHNER.setType(ColumnType.INTEGER);

        COLUMN_KER_GESCH_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_KER_GESCH_EINWOHNER.setName("KER_GESCH");
        COLUMN_KER_GESCH_EINWOHNER.setId("_column_einwohner_ker_gesch");
        COLUMN_KER_GESCH_EINWOHNER.setType(ColumnType.VARCHAR);

        COLUMN_AGE_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_AGE_EINWOHNER.setName("AGE");
        COLUMN_AGE_EINWOHNER.setId("_column_einwohner_age");
        COLUMN_AGE_EINWOHNER.setType(ColumnType.INTEGER);

        COLUMN_ANZAHL_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ANZAHL_EINWOHNER.setName("Anzahl");
        COLUMN_ANZAHL_EINWOHNER.setId("_column_einwohner_anzahl");
        COLUMN_ANZAHL_EINWOHNER.setType(ColumnType.INTEGER);

        COLUMN_GEOJSON_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOJSON_EINWOHNER.setName("GEOJSON");
        COLUMN_GEOJSON_EINWOHNER.setId("_column_einwohner_geojson");
        COLUMN_GEOJSON_EINWOHNER.setType(ColumnType.VARCHAR);

        // Initialize Year Table columns
        COLUMN_YEAR_YEAR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_YEAR_YEAR.setName("year");
        COLUMN_YEAR_YEAR.setId("_column_year_year");
        COLUMN_YEAR_YEAR.setType(ColumnType.INTEGER);

        COLUMN_ORDINAL_YEAR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ORDINAL_YEAR.setName("ordinal");
        COLUMN_ORDINAL_YEAR.setId("_column_year_ordinal");
        COLUMN_ORDINAL_YEAR.setType(ColumnType.INTEGER);

        // Initialize Town Table columns
        COLUMN_ID_TOWN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ID_TOWN.setName("id");
        COLUMN_ID_TOWN.setId("_column_town_id");
        COLUMN_ID_TOWN.setType(ColumnType.INTEGER);

        COLUMN_NAME_TOWN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NAME_TOWN.setName("name");
        COLUMN_NAME_TOWN.setId("_column_town_name");
        COLUMN_NAME_TOWN.setType(ColumnType.VARCHAR);

        COLUMN_GEOJSON_TOWN = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOJSON_TOWN.setName("geojson");
        COLUMN_GEOJSON_TOWN.setId("_column_town_geojson");
        COLUMN_GEOJSON_TOWN.setType(ColumnType.VARCHAR);

        // Initialize Plraum Table columns
        COLUMN_GID_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GID_PLRAUM.setName("gid");
        COLUMN_GID_PLRAUM.setId("_column_plraum_gid");
        COLUMN_GID_PLRAUM.setType(ColumnType.INTEGER);

        COLUMN_PLRAUM_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PLRAUM_PLRAUM.setName("plraum");
        COLUMN_PLRAUM_PLRAUM.setId("_column_plraum_plraum");
        COLUMN_PLRAUM_PLRAUM.setType(ColumnType.VARCHAR);

        COLUMN_UUID_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UUID_PLRAUM.setName("uuid");
        COLUMN_UUID_PLRAUM.setId("_column_plraum_uuid");
        COLUMN_UUID_PLRAUM.setType(ColumnType.VARCHAR);

        COLUMN_GEOJSON_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOJSON_PLRAUM.setName("geojson");
        COLUMN_GEOJSON_PLRAUM.setId("_column_plraum_geojson");
        COLUMN_GEOJSON_PLRAUM.setType(ColumnType.VARCHAR);

        COLUMN_TOWNID_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TOWNID_PLRAUM.setName("townid");
        COLUMN_TOWNID_PLRAUM.setId("_column_plraum_townid");
        COLUMN_TOWNID_PLRAUM.setType(ColumnType.INTEGER);

        // Initialize Statbez Table columns
        COLUMN_GID_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GID_STATBEZ.setName("gid");
        COLUMN_GID_STATBEZ.setId("_column_statbez_gid");
        COLUMN_GID_STATBEZ.setType(ColumnType.INTEGER);

        COLUMN_PLRAUM_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PLRAUM_STATBEZ.setName("plraum");
        COLUMN_PLRAUM_STATBEZ.setId("_column_statbez_plraum");
        COLUMN_PLRAUM_STATBEZ.setType(ColumnType.INTEGER);

        COLUMN_STATBEZ_NAME_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STATBEZ_NAME_STATBEZ.setName("statbez_name");
        COLUMN_STATBEZ_NAME_STATBEZ.setId("_column_statbez_statbez_name");
        COLUMN_STATBEZ_NAME_STATBEZ.setType(ColumnType.VARCHAR);

        COLUMN_UUID_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_UUID_STATBEZ.setName("uuid");
        COLUMN_UUID_STATBEZ.setId("_column_statbez_uuid");
        COLUMN_UUID_STATBEZ.setType(ColumnType.VARCHAR);

        COLUMN_GEOJSON_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_GEOJSON_STATBEZ.setName("geojson");
        COLUMN_GEOJSON_STATBEZ.setId("_column_statbez_geojson");
        COLUMN_GEOJSON_STATBEZ.setType(ColumnType.VARCHAR);

        // Initialize Gender Table columns
        COLUMN_KEY_GENDER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_KEY_GENDER.setName("key");
        COLUMN_KEY_GENDER.setId("_column_gender_key");
        COLUMN_KEY_GENDER.setType(ColumnType.VARCHAR);

        COLUMN_NAME_GENDER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_NAME_GENDER.setName("name");
        COLUMN_NAME_GENDER.setId("_column_gender_name");
        COLUMN_NAME_GENDER.setType(ColumnType.VARCHAR);

        // Initialize Age Groups Table columns
        COLUMN_AGE_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_AGE_AGEGROUPS.setName("Age");
        COLUMN_AGE_AGEGROUPS.setId("_column_agegroups_age");
        COLUMN_AGE_AGEGROUPS.setType(ColumnType.INTEGER);

        COLUMN_H1_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H1_AGEGROUPS.setName("H1");
        COLUMN_H1_AGEGROUPS.setId("_column_agegroups_h1");
        COLUMN_H1_AGEGROUPS.setType(ColumnType.VARCHAR);

        COLUMN_H1_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H1_ORDER_AGEGROUPS.setName("H1_Order");
        COLUMN_H1_ORDER_AGEGROUPS.setId("_column_agegroups_h1_order");
        COLUMN_H1_ORDER_AGEGROUPS.setType(ColumnType.INTEGER);

        COLUMN_H2_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H2_AGEGROUPS.setName("H2");
        COLUMN_H2_AGEGROUPS.setId("_column_agegroups_h2");
        COLUMN_H2_AGEGROUPS.setType(ColumnType.VARCHAR);

        COLUMN_H2_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H2_ORDER_AGEGROUPS.setName("H2_Order");
        COLUMN_H2_ORDER_AGEGROUPS.setId("_column_agegroups_h2_order");
        COLUMN_H2_ORDER_AGEGROUPS.setType(ColumnType.INTEGER);

        COLUMN_H7_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H7_AGEGROUPS.setName("H7");
        COLUMN_H7_AGEGROUPS.setId("_column_agegroups_h7");
        COLUMN_H7_AGEGROUPS.setType(ColumnType.VARCHAR);

        COLUMN_H7_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H7_ORDER_AGEGROUPS.setName("H7_Order");
        COLUMN_H7_ORDER_AGEGROUPS.setId("_column_agegroups_h7_order");
        COLUMN_H7_ORDER_AGEGROUPS.setType(ColumnType.INTEGER);

        COLUMN_H8_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H8_AGEGROUPS.setName("H8");
        COLUMN_H8_AGEGROUPS.setId("_column_agegroups_h8");
        COLUMN_H8_AGEGROUPS.setType(ColumnType.VARCHAR);

        COLUMN_H8_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H8_ORDER_AGEGROUPS.setName("H8_Order");
        COLUMN_H8_ORDER_AGEGROUPS.setId("_column_agegroups_h8_order");
        COLUMN_H8_ORDER_AGEGROUPS.setType(ColumnType.INTEGER);

        COLUMN_H9_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H9_AGEGROUPS.setName("H9");
        COLUMN_H9_AGEGROUPS.setId("_column_agegroups_h9");
        COLUMN_H9_AGEGROUPS.setType(ColumnType.VARCHAR);

        COLUMN_H9_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_H9_ORDER_AGEGROUPS.setName("H9_Order");
        COLUMN_H9_ORDER_AGEGROUPS.setId("_column_agegroups_h9_order");
        COLUMN_H9_ORDER_AGEGROUPS.setType(ColumnType.INTEGER);

        // Initialize tables
        TABLE_EINWOHNER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_EINWOHNER.setName("einwohner");
        TABLE_EINWOHNER.setId("_table_einwohner");
        TABLE_EINWOHNER.getColumns().addAll(List.of(COLUMN_JAHR_EINWOHNER, COLUMN_STATBEZ_EINWOHNER,
                COLUMN_KER_GESCH_EINWOHNER, COLUMN_AGE_EINWOHNER, COLUMN_ANZAHL_EINWOHNER, COLUMN_GEOJSON_EINWOHNER));

        TABLE_YEAR = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_YEAR.setName("year");
        TABLE_YEAR.setId("_table_year");
        TABLE_YEAR.getColumns().addAll(List.of(COLUMN_YEAR_YEAR, COLUMN_ORDINAL_YEAR));

        TABLE_TOWN = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_TOWN.setName("town");
        TABLE_TOWN.setId("_table_town");
        TABLE_TOWN.getColumns().addAll(List.of(COLUMN_ID_TOWN, COLUMN_NAME_TOWN, COLUMN_GEOJSON_TOWN));

        TABLE_PLRAUM = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PLRAUM.setName("plraum");
        TABLE_PLRAUM.setId("_table_plraum");
        TABLE_PLRAUM.getColumns().addAll(List.of(COLUMN_GID_PLRAUM, COLUMN_PLRAUM_PLRAUM,
                COLUMN_UUID_PLRAUM, COLUMN_GEOJSON_PLRAUM, COLUMN_TOWNID_PLRAUM));

        TABLE_STATBEZ = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_STATBEZ.setName("statbez");
        TABLE_STATBEZ.setId("_table_statbez");
        TABLE_STATBEZ.getColumns().addAll(List.of(COLUMN_GID_STATBEZ, COLUMN_PLRAUM_STATBEZ,
                COLUMN_STATBEZ_NAME_STATBEZ, COLUMN_UUID_STATBEZ, COLUMN_GEOJSON_STATBEZ));

        TABLE_GENDER = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_GENDER.setName("gender");
        TABLE_GENDER.setId("_table_gender");
        TABLE_GENDER.getColumns().addAll(List.of(COLUMN_KEY_GENDER, COLUMN_NAME_GENDER));

        TABLE_AGEGROUPS = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_AGEGROUPS.setName("AgeGroups");
        TABLE_AGEGROUPS.setId("_table_agegroups");
        TABLE_AGEGROUPS.getColumns()
                .addAll(List.of(COLUMN_AGE_AGEGROUPS, COLUMN_H1_AGEGROUPS, COLUMN_H1_ORDER_AGEGROUPS,
                        COLUMN_H2_AGEGROUPS, COLUMN_H2_ORDER_AGEGROUPS, COLUMN_H7_AGEGROUPS, COLUMN_H7_ORDER_AGEGROUPS,
                        COLUMN_H8_AGEGROUPS, COLUMN_H8_ORDER_AGEGROUPS, COLUMN_H9_AGEGROUPS,
                        COLUMN_H9_ORDER_AGEGROUPS));

        ORDERED_COLUMN_ORDINAL_YEAR = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_ORDINAL_YEAR.setId("_ordered_column_year_ordinal");
        ORDERED_COLUMN_ORDINAL_YEAR.setColumn(COLUMN_ORDINAL_YEAR);

        ORDERED_COLUMN_H1_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H1_ORDER_AGEGROUPS.setId("_ordered_column_agegroups_h1_order");
        ORDERED_COLUMN_H1_ORDER_AGEGROUPS.setColumn(COLUMN_H1_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H2_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H2_ORDER_AGEGROUPS.setId("_ordered_column_agegroups_h2_order");
        ORDERED_COLUMN_H2_ORDER_AGEGROUPS.setColumn(COLUMN_H2_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H7_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H7_ORDER_AGEGROUPS.setId("_ordered_column_agegroups_h7_order");
        ORDERED_COLUMN_H7_ORDER_AGEGROUPS.setColumn(COLUMN_H7_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H8_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H8_ORDER_AGEGROUPS.setId("_ordered_column_agegroups_h8_order");
        ORDERED_COLUMN_H8_ORDER_AGEGROUPS.setColumn(COLUMN_H8_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H9_ORDER_AGEGROUPS = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H9_ORDER_AGEGROUPS.setId("_ordered_column_agegroups_h9_order");
        ORDERED_COLUMN_H9_ORDER_AGEGROUPS.setColumn(COLUMN_H9_ORDER_AGEGROUPS);

        // Initialize levels
        LEVEL_JAHR = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_JAHR.setName("Jahr");
        LEVEL_JAHR.setColumn(COLUMN_YEAR_YEAR);
        LEVEL_JAHR.setOrdinalColumn(ORDERED_COLUMN_ORDINAL_YEAR);
        LEVEL_JAHR.setType(LevelDefinition.TIME_YEARS);
        LEVEL_JAHR.setId("_level_jahr");

        // Stadt level with GeoJson property
        LEVEL_STADT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STADT.setName("Stadt");
        LEVEL_STADT.setColumn(COLUMN_NAME_TOWN);
        //LEVEL_STADT.setOrdinalColumn(COLUMN_ORDINAL_YEAR);
        LEVEL_STADT.setId("_level_stadt");

        MemberProperty geoJsonPropertyTown = RolapMappingFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyTown.setName("GeoJson");
        geoJsonPropertyTown.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyTown.setColumn(COLUMN_GEOJSON_TOWN);
        geoJsonPropertyTown.setId("_property_town_geojson");
        LEVEL_STADT.getMemberProperties().add(geoJsonPropertyTown);

        // Planungsraum level with properties
        LEVEL_PLANUNGSRAUM = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PLANUNGSRAUM.setName("Planungsraum");
        LEVEL_PLANUNGSRAUM.setColumn(COLUMN_GID_PLRAUM);
        LEVEL_PLANUNGSRAUM.setNameColumn(COLUMN_PLRAUM_PLRAUM);
        LEVEL_PLANUNGSRAUM.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_PLANUNGSRAUM.setId("_level_planungsraum");

        MemberProperty uuidPropertyPlraum = RolapMappingFactory.eINSTANCE.createMemberProperty();
        uuidPropertyPlraum.setName("uuid");
        uuidPropertyPlraum.setColumn(COLUMN_UUID_PLRAUM);
        uuidPropertyPlraum.setId("_property_plraum_uuid");
        LEVEL_PLANUNGSRAUM.getMemberProperties().add(uuidPropertyPlraum);

        MemberProperty geoJsonPropertyPlraum = RolapMappingFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyPlraum.setName("GeoJson");
        geoJsonPropertyPlraum.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyPlraum.setColumn(COLUMN_GEOJSON_PLRAUM);
        geoJsonPropertyPlraum.setId("_property_plraum_geojson");
        LEVEL_PLANUNGSRAUM.getMemberProperties().add(geoJsonPropertyPlraum);

        // Statistischer Bezirk level with properties
        LEVEL_STATISTISCHER_BEZIRK = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STATISTISCHER_BEZIRK.setName("Statistischer Bezirk");
        LEVEL_STATISTISCHER_BEZIRK.setColumn(COLUMN_GID_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.setNameColumn(COLUMN_STATBEZ_NAME_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL_STATISTISCHER_BEZIRK.setId("_level_statistischer_bezirk");

        MemberProperty uuidPropertyStatbez = RolapMappingFactory.eINSTANCE.createMemberProperty();
        uuidPropertyStatbez.setName("uuid");
        uuidPropertyStatbez.setColumn(COLUMN_UUID_STATBEZ);
        uuidPropertyStatbez.setId("_property_statbez_uuid");
        LEVEL_STATISTISCHER_BEZIRK.getMemberProperties().add(uuidPropertyStatbez);

        MemberProperty geoJsonPropertyStatbez = RolapMappingFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyStatbez.setName("GeoJson");
        geoJsonPropertyStatbez.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyStatbez.setColumn(COLUMN_GEOJSON_STATBEZ);
        geoJsonPropertyStatbez.setId("_property_statbez_geojson");
        LEVEL_STATISTISCHER_BEZIRK.getMemberProperties().add(geoJsonPropertyStatbez);

        LEVEL_GESCHLECHT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_GESCHLECHT.setName("Geschlecht");
        LEVEL_GESCHLECHT.setColumn(COLUMN_KEY_GENDER);
        LEVEL_GESCHLECHT.setNameColumn(COLUMN_NAME_GENDER);
        LEVEL_GESCHLECHT.setId("_level_geschlecht");

        // Age levels
        LEVEL_ALTER_EINZELJAHRGAENGE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_EINZELJAHRGAENGE.setName("Alter");
        LEVEL_ALTER_EINZELJAHRGAENGE.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_EINZELJAHRGAENGE.setId("_level_alter_einzeljahrgaenge");

        LEVEL_ALTERSGRUPPE_STANDARD = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_STANDARD.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_STANDARD.setColumn(COLUMN_H1_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_STANDARD.setOrdinalColumn(ORDERED_COLUMN_H1_ORDER_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_STANDARD.setId("_level_altersgruppe_standard");

        LEVEL_ALTER_STANDARD = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_STANDARD.setName("Alter Standard");
        LEVEL_ALTER_STANDARD.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_STANDARD.setId("_level_alter_standard");

        LEVEL_ALTERSGRUPPE_KINDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_KINDER.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_KINDER.setColumn(COLUMN_H2_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_KINDER.setOrdinalColumn(ORDERED_COLUMN_H2_ORDER_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_KINDER.setId("_level_altersgruppe_kinder");

        LEVEL_ALTER_KINDER = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_KINDER.setName("Alter Kinder");
        LEVEL_ALTER_KINDER.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_KINDER.setId("_level_alter_kinder");

        LEVEL_ALTERSGRUPPE_RKI_H7 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_RKI_H7.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_RKI_H7.setColumn(COLUMN_H7_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H7.setOrdinalColumn(ORDERED_COLUMN_H7_ORDER_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H7.setId("_level_altersgruppe_rki_h7");

        LEVEL_ALTER_RKI_H7 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_RKI_H7.setName("Alter H7");
        LEVEL_ALTER_RKI_H7.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_RKI_H7.setId("_level_alter_rki_h7");

        LEVEL_ALTERSGRUPPE_RKI_H8 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_RKI_H8.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_RKI_H8.setColumn(COLUMN_H8_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H8.setOrdinalColumn(ORDERED_COLUMN_H8_ORDER_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H8.setId("_level_altersgruppe_rki_h8");

        LEVEL_ALTER_RKI_H8 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_RKI_H8.setName("Alter H8");
        LEVEL_ALTER_RKI_H8.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_RKI_H8.setId("_level_alter_rki_h8");

        LEVEL_ALTERSGRUPPE_10JAHRE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_10JAHRE.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_10JAHRE.setColumn(COLUMN_H9_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_10JAHRE.setOrdinalColumn(ORDERED_COLUMN_H9_ORDER_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_10JAHRE.setId("_level_altersgruppe_10jahre");

        LEVEL_ALTER_10JAHRE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_10JAHRE.setName("Alter 10");
        LEVEL_ALTER_10JAHRE.setColumn(COLUMN_AGE_AGEGROUPS);
        LEVEL_ALTER_10JAHRE.setId("_level_alter_10jahre");

        // Initialize table queries
        TABLEQUERY_YEAR = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_YEAR.setTable(TABLE_YEAR);
        TABLEQUERY_YEAR.setId("_query_year");

        TABLEQUERY_TOWN = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_TOWN.setTable(TABLE_TOWN);
        TABLEQUERY_TOWN.setId("_query_town");

        TABLEQUERY_PLRAUM = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PLRAUM.setTable(TABLE_PLRAUM);
        TABLEQUERY_PLRAUM.setId("_query_plraum");

        TABLEQUERY_STATBEZ = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_STATBEZ.setTable(TABLE_STATBEZ);
        TABLEQUERY_STATBEZ.setId("_query_statbez");

        TABLEQUERY_GENDER = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_GENDER.setTable(TABLE_GENDER);
        TABLEQUERY_GENDER.setId("_query_gender");

        TABLEQUERY_AGEGROUPS = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_AGEGROUPS.setTable(TABLE_AGEGROUPS);
        TABLEQUERY_AGEGROUPS.setId("_query_agegroups");

        TABLEQUERY_FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_FACT.setId("_query_fact");
        TABLEQUERY_FACT.setTable(TABLE_EINWOHNER);

        // Initialize join query for Stadt-Planungsraum-Statistischer Bezirk hierarchy
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK.setId("_joinquery_stadt_planungsraum_statbezirk");

        // Left side: statbez table
        JoinedQueryElement leftStatbez = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        leftStatbez.setKey(COLUMN_PLRAUM_STATBEZ);
        leftStatbez.setQuery(TABLEQUERY_STATBEZ);
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK.setLeft(leftStatbez);

        // Right side: join of plraum and town tables
        JoinQuery joinPlraumTown = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinPlraumTown.setId("_joinquery_plraum_town");

        JoinedQueryElement leftPlraum = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        leftPlraum.setKey(COLUMN_TOWNID_PLRAUM);
        leftPlraum.setQuery(TABLEQUERY_PLRAUM);
        joinPlraumTown.setLeft(leftPlraum);

        JoinedQueryElement rightTown = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        rightTown.setKey(COLUMN_ID_TOWN);
        rightTown.setQuery(TABLEQUERY_TOWN);
        joinPlraumTown.setRight(rightTown);

        JoinedQueryElement rightPlraumTown = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        rightPlraumTown.setKey(COLUMN_GID_PLRAUM);
        rightPlraumTown.setQuery(joinPlraumTown);
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK.setRight(rightPlraumTown);

        // Initialize hierarchies
        HIERARCHY_JAHR = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_JAHR.setName("Jahr");
        HIERARCHY_JAHR.setId("_hierarchy_jahr");
        HIERARCHY_JAHR.setHasAll(false);
        HIERARCHY_JAHR.setPrimaryKey(COLUMN_YEAR_YEAR);
        HIERARCHY_JAHR.setQuery(TABLEQUERY_YEAR);
        HIERARCHY_JAHR.setDefaultMember("2023");
        HIERARCHY_JAHR.getLevels().add(LEVEL_JAHR);

        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setName("Stadt - Planungsraum - statistischer Bezirk");
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setId("_hierarchy_stadt_planungsraum_statbezirk");
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setHasAll(true);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setAllMemberName("Alle Gebiete");
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setPrimaryKey(COLUMN_GID_STATBEZ);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setQuery(JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.getLevels()
                .addAll(List.of(LEVEL_STADT, LEVEL_PLANUNGSRAUM, LEVEL_STATISTISCHER_BEZIRK));

        HIERARCHY_GESCHLECHT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GESCHLECHT.setName("Geschlecht (m/w/d)");
        HIERARCHY_GESCHLECHT.setId("_hierarchy_geschlecht");
        HIERARCHY_GESCHLECHT.setHasAll(true);
        HIERARCHY_GESCHLECHT.setAllMemberName("Alle Geschlechter");
        HIERARCHY_GESCHLECHT.setPrimaryKey(COLUMN_KEY_GENDER);
        HIERARCHY_GESCHLECHT.setQuery(TABLEQUERY_GENDER);
        HIERARCHY_GESCHLECHT.getLevels().add(LEVEL_GESCHLECHT);

        HIERARCHY_ALTER_EINZELJAHRGAENGE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setName("Alter (Einzeljahrgänge)");
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setId("_hierarchy_alter_einzeljahrgaenge");
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setHasAll(true);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.getLevels().add(LEVEL_ALTER_EINZELJAHRGAENGE);

        HIERARCHY_ALTERSGRUPPEN_STANDARD = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setName("Altersgruppen (Standard)");
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setId("_hierarchy_altersgruppen_standard");
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.getLevels().addAll(List.of(LEVEL_ALTERSGRUPPE_STANDARD, LEVEL_ALTER_STANDARD));

        HIERARCHY_ALTERSGRUPPEN_KINDER = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_KINDER.setName("Altersgruppen (Kinder)");
        HIERARCHY_ALTERSGRUPPEN_KINDER.setId("_hierarchy_altersgruppen_kinder");
        HIERARCHY_ALTERSGRUPPEN_KINDER.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_KINDER.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_KINDER.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_KINDER.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_KINDER.getLevels().addAll(List.of(LEVEL_ALTERSGRUPPE_KINDER, LEVEL_ALTER_KINDER));

        HIERARCHY_ALTERSGRUPPEN_RKI_H7 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setName("Altersgruppen (Systematik RKI H7)");
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setId("_hierarchy_altersgruppen_rki_h7");
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.getLevels().add(LEVEL_ALTER_RKI_H7);

        HIERARCHY_ALTERSGRUPPEN_RKI_H8 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setName("Altersgruppen (Systematik RKI H8)");
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setId("_hierarchy_altersgruppen_rki_h8");
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.getLevels().add(LEVEL_ALTER_RKI_H8);

        HIERARCHY_ALTERSGRUPPEN_10JAHRE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setName("Altersgruppen (10-Jahres-Gruppen)");
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setId("_hierarchy_altersgruppen_10jahre");
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.getLevels().add(LEVEL_ALTER_10JAHRE);

        // Initialize dimensions
        DIMENSION_JAHR = RolapMappingFactory.eINSTANCE.createTimeDimension();
        DIMENSION_JAHR.setName("Jahr");
        DIMENSION_JAHR.setId("_dimension_jahr");
        DIMENSION_JAHR.getHierarchies().add(HIERARCHY_JAHR);

        DIMENSION_STATISTISCHER_BEZIRK = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STATISTISCHER_BEZIRK.setName("statistischer Bezirk");
        DIMENSION_STATISTISCHER_BEZIRK.setId("_dimension_statistischer_bezirk");
        DIMENSION_STATISTISCHER_BEZIRK.getHierarchies().add(HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK);

        DIMENSION_GESCHLECHT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GESCHLECHT.setName("Geschlecht");
        DIMENSION_GESCHLECHT.setId("_dimension_geschlecht");
        DIMENSION_GESCHLECHT.getHierarchies().add(HIERARCHY_GESCHLECHT);

        DIMENSION_ALTER = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_ALTER.setName("Alter");
        DIMENSION_ALTER.setId("_dimension_alter");
        DIMENSION_ALTER.getHierarchies()
                .addAll(List.of(HIERARCHY_ALTER_EINZELJAHRGAENGE, HIERARCHY_ALTERSGRUPPEN_STANDARD,
                        HIERARCHY_ALTERSGRUPPEN_KINDER, HIERARCHY_ALTERSGRUPPEN_RKI_H7, HIERARCHY_ALTERSGRUPPEN_RKI_H8,
                        HIERARCHY_ALTERSGRUPPEN_10JAHRE));

        // Initialize dimension connectors
        CONNECTOR_JAHR = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_JAHR.setDimension(DIMENSION_JAHR);
        CONNECTOR_JAHR.setForeignKey(COLUMN_JAHR_EINWOHNER);
        CONNECTOR_JAHR.setId("_connector_jahr");
        CONNECTOR_JAHR.setOverrideDimensionName("Jahr");

        CONNECTOR_STATISTISCHER_BEZIRK = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STATISTISCHER_BEZIRK.setDimension(DIMENSION_STATISTISCHER_BEZIRK);
        CONNECTOR_STATISTISCHER_BEZIRK.setForeignKey(COLUMN_STATBEZ_EINWOHNER);
        CONNECTOR_STATISTISCHER_BEZIRK.setId("_connector_statistischer_bezirk");
        CONNECTOR_STATISTISCHER_BEZIRK.setOverrideDimensionName("statistischer Bezirk");

        CONNECTOR_GESCHLECHT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_GESCHLECHT.setDimension(DIMENSION_GESCHLECHT);
        CONNECTOR_GESCHLECHT.setForeignKey(COLUMN_KER_GESCH_EINWOHNER);
        CONNECTOR_GESCHLECHT.setId("_connector_geschlecht");
        CONNECTOR_GESCHLECHT.setOverrideDimensionName("Geschlecht");

        CONNECTOR_ALTER = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_ALTER.setDimension(DIMENSION_ALTER);
        CONNECTOR_ALTER.setForeignKey(COLUMN_AGE_EINWOHNER);
        CONNECTOR_ALTER.setId("_connector_alter");
        CONNECTOR_ALTER.setOverrideDimensionName("Alter");

        // Initialize measures
        MEASURE_EINWOHNERZAHL = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_EINWOHNERZAHL.setName("Einwohnerzahl");
        MEASURE_EINWOHNERZAHL.setId("_measure_einwohnerzahl");
        MEASURE_EINWOHNERZAHL.setColumn(COLUMN_ANZAHL_EINWOHNER);
        MEASURE_EINWOHNERZAHL.setFormatString("#,###");

        MEASUREGROUP_BEVOELKERUNG = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_BEVOELKERUNG.getMeasures().add(MEASURE_EINWOHNERZAHL);

        // Initialize cube
        CUBE_BEVOELKERUNG = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_BEVOELKERUNG.setName("Bevölkerung");
        CUBE_BEVOELKERUNG.setId("_cube_bevoelkerung");
        CUBE_BEVOELKERUNG.setQuery(TABLEQUERY_FACT);
        CUBE_BEVOELKERUNG.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_JAHR, CONNECTOR_STATISTISCHER_BEZIRK, CONNECTOR_GESCHLECHT, CONNECTOR_ALTER));
        CUBE_BEVOELKERUNG.getMeasureGroups().add(MEASUREGROUP_BEVOELKERUNG);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_POPULATION_JENA = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA_POPULATION_JENA.setId("_databaseSchema_population_jena");
        //DATABASE_SCHEMA_POPULATION_JENA.setName("population_jena");
        DATABASE_SCHEMA_POPULATION_JENA.getTables().addAll(List.of(TABLE_EINWOHNER, TABLE_YEAR, TABLE_TOWN,
                TABLE_PLRAUM, TABLE_STATBEZ, TABLE_GENDER, TABLE_AGEGROUPS));

        CATALOG_POPULATION_JENA = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_POPULATION_JENA.setName("Bevölkerung");
        CATALOG_POPULATION_JENA.setDescription("Bevölkerungsstatistik Jena - EMF Version");
        CATALOG_POPULATION_JENA.setId("_catalog_population_jena");
        CATALOG_POPULATION_JENA.getDbschemas().add(DATABASE_SCHEMA_POPULATION_JENA);
        CATALOG_POPULATION_JENA.getCubes().add(CUBE_BEVOELKERUNG);

        // Add documentation
        document(CATALOG_POPULATION_JENA, "Bevölkerung Database", populationJenaBody, 1, 0, 0, false, 0);
        document(CUBE_BEVOELKERUNG, "Bevölkerungs Cube", bevoelkerungCubeBody, 1, 1, 0, true, 0);
        document(DIMENSION_JAHR, "Jahr Dimension", jahrBody, 1, 2, 0, true, 0);
        document(DIMENSION_STATISTISCHER_BEZIRK, "Geografie Dimension", geografieBody, 1, 3, 0, true, 0);
        document(DIMENSION_GESCHLECHT, "Geschlecht Dimension", geschlechtBody, 1, 4, 0, true, 0);
        document(DIMENSION_ALTER, "Alter Dimension", alterBody, 1, 5, 0, true, 0);
    }

    @Override
    public Catalog get() {
        return CATALOG_POPULATION_JENA;
    }

}
