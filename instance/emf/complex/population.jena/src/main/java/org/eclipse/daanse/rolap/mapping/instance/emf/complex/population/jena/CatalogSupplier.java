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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ColumnInternalDataType;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.3", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    public static final PhysicalCube CUBE_BEVOELKERUNG;
    public static final StandardDimension DIMENSION_STATISTISCHER_BEZIRK;
    public static final Catalog CATALOG_POPULATION_JENA;
    public static final StandardDimension DIMENSION_GESCHLECHT;
    public static final StandardDimension DIMENSION_ALTER;
    public static final TimeDimension DIMENSION_JAHR;


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
    public static final Table TABLE_EINWOHNER;
    public static final Table TABLE_YEAR;
    public static final Table TABLE_TOWN;
    public static final Table TABLE_PLRAUM;
    public static final Table TABLE_STATBEZ;
    public static final Table TABLE_GENDER;
    public static final Table TABLE_AGEGROUPS;

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
    // field assignment only: DIMENSION_JAHR
    // field assignment only: DIMENSION_STATISTISCHER_BEZIRK
    // field assignment only: DIMENSION_GESCHLECHT
    // field assignment only: DIMENSION_ALTER

    // Static cube
    // field assignment only: CUBE_BEVOELKERUNG

    // Static table queries
    public static final TableSource TABLEQUERY_YEAR;
    public static final TableSource TABLEQUERY_TOWN;
    public static final TableSource TABLEQUERY_PLRAUM;
    public static final TableSource TABLEQUERY_STATBEZ;
    public static final TableSource TABLEQUERY_GENDER;
    public static final TableSource TABLEQUERY_AGEGROUPS;
    public static final TableSource TABLEQUERY_FACT;

    // Join queries
    public static final JoinSource JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_JAHR;
    public static final DimensionConnector CONNECTOR_STATISTISCHER_BEZIRK;
    public static final DimensionConnector CONNECTOR_GESCHLECHT;
    public static final DimensionConnector CONNECTOR_ALTER;

    // Static measures and measure group
    public static final SumMeasure MEASURE_EINWOHNERZAHL;
    public static final MeasureGroup MEASUREGROUP_BEVOELKERUNG;

    // Static database schema and catalog
    public static final Schema DATABASE_SCHEMA_POPULATION_JENA;
    // field assignment only: CATALOG_POPULATION_JENA

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
        COLUMN_JAHR_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_JAHR_EINWOHNER.setName("JAHR");
        COLUMN_JAHR_EINWOHNER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STATBEZ_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STATBEZ_EINWOHNER.setName("STATBEZ");
        COLUMN_STATBEZ_EINWOHNER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_KER_GESCH_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_KER_GESCH_EINWOHNER.setName("KER_GESCH");
        COLUMN_KER_GESCH_EINWOHNER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_AGE_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_AGE_EINWOHNER.setName("AGE");
        COLUMN_AGE_EINWOHNER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_ANZAHL_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ANZAHL_EINWOHNER.setName("Anzahl");
        COLUMN_ANZAHL_EINWOHNER.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_GEOJSON_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GEOJSON_EINWOHNER.setName("GEOJSON");
        COLUMN_GEOJSON_EINWOHNER.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Year Table columns
        COLUMN_YEAR_YEAR = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_YEAR_YEAR.setName("year");
        COLUMN_YEAR_YEAR.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_ORDINAL_YEAR = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ORDINAL_YEAR.setName("ordinal");
        COLUMN_ORDINAL_YEAR.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize Town Table columns
        COLUMN_ID_TOWN = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ID_TOWN.setName("id");
        COLUMN_ID_TOWN.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_NAME_TOWN = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_NAME_TOWN.setName("name");
        COLUMN_NAME_TOWN.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_GEOJSON_TOWN = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GEOJSON_TOWN.setName("geojson");
        COLUMN_GEOJSON_TOWN.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Plraum Table columns
        COLUMN_GID_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GID_PLRAUM.setName("gid");
        COLUMN_GID_PLRAUM.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PLRAUM_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PLRAUM_PLRAUM.setName("plraum");
        COLUMN_PLRAUM_PLRAUM.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_UUID_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UUID_PLRAUM.setName("uuid");
        COLUMN_UUID_PLRAUM.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_GEOJSON_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GEOJSON_PLRAUM.setName("geojson");
        COLUMN_GEOJSON_PLRAUM.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_TOWNID_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TOWNID_PLRAUM.setName("townid");
        COLUMN_TOWNID_PLRAUM.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize Statbez Table columns
        COLUMN_GID_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GID_STATBEZ.setName("gid");
        COLUMN_GID_STATBEZ.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_PLRAUM_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PLRAUM_STATBEZ.setName("plraum");
        COLUMN_PLRAUM_STATBEZ.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_STATBEZ_NAME_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STATBEZ_NAME_STATBEZ.setName("statbez_name");
        COLUMN_STATBEZ_NAME_STATBEZ.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_UUID_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_UUID_STATBEZ.setName("uuid");
        COLUMN_UUID_STATBEZ.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_GEOJSON_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_GEOJSON_STATBEZ.setName("geojson");
        COLUMN_GEOJSON_STATBEZ.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Gender Table columns
        COLUMN_KEY_GENDER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_KEY_GENDER.setName("key");
        COLUMN_KEY_GENDER.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_NAME_GENDER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_NAME_GENDER.setName("name");
        COLUMN_NAME_GENDER.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Age Groups Table columns
        COLUMN_AGE_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_AGE_AGEGROUPS.setName("Age");
        COLUMN_AGE_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_H1_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H1_AGEGROUPS.setName("H1");
        COLUMN_H1_AGEGROUPS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_H1_ORDER_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H1_ORDER_AGEGROUPS.setName("H1_Order");
        COLUMN_H1_ORDER_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_H2_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H2_AGEGROUPS.setName("H2");
        COLUMN_H2_AGEGROUPS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_H2_ORDER_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H2_ORDER_AGEGROUPS.setName("H2_Order");
        COLUMN_H2_ORDER_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_H7_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H7_AGEGROUPS.setName("H7");
        COLUMN_H7_AGEGROUPS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_H7_ORDER_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H7_ORDER_AGEGROUPS.setName("H7_Order");
        COLUMN_H7_ORDER_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_H8_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H8_AGEGROUPS.setName("H8");
        COLUMN_H8_AGEGROUPS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_H8_ORDER_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H8_ORDER_AGEGROUPS.setName("H8_Order");
        COLUMN_H8_ORDER_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_H9_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H9_AGEGROUPS.setName("H9");
        COLUMN_H9_AGEGROUPS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_H9_ORDER_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_H9_ORDER_AGEGROUPS.setName("H9_Order");
        COLUMN_H9_ORDER_AGEGROUPS.setType(SqlSimpleTypes.Sql99.integerType());

        // Initialize tables
        TABLE_EINWOHNER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_EINWOHNER.setName("einwohner");
        TABLE_EINWOHNER.getFeature().addAll(List.of(COLUMN_JAHR_EINWOHNER, COLUMN_STATBEZ_EINWOHNER,
                COLUMN_KER_GESCH_EINWOHNER, COLUMN_AGE_EINWOHNER, COLUMN_ANZAHL_EINWOHNER, COLUMN_GEOJSON_EINWOHNER));

        TABLE_YEAR = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_YEAR.setName("year");
        TABLE_YEAR.getFeature().addAll(List.of(COLUMN_YEAR_YEAR, COLUMN_ORDINAL_YEAR));

        TABLE_TOWN = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_TOWN.setName("town");
        TABLE_TOWN.getFeature().addAll(List.of(COLUMN_ID_TOWN, COLUMN_NAME_TOWN, COLUMN_GEOJSON_TOWN));

        TABLE_PLRAUM = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PLRAUM.setName("plraum");
        TABLE_PLRAUM.getFeature().addAll(List.of(COLUMN_GID_PLRAUM, COLUMN_PLRAUM_PLRAUM,
                COLUMN_UUID_PLRAUM, COLUMN_GEOJSON_PLRAUM, COLUMN_TOWNID_PLRAUM));

        TABLE_STATBEZ = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_STATBEZ.setName("statbez");
        TABLE_STATBEZ.getFeature().addAll(List.of(COLUMN_GID_STATBEZ, COLUMN_PLRAUM_STATBEZ,
                COLUMN_STATBEZ_NAME_STATBEZ, COLUMN_UUID_STATBEZ, COLUMN_GEOJSON_STATBEZ));

        TABLE_GENDER = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_GENDER.setName("gender");
        TABLE_GENDER.getFeature().addAll(List.of(COLUMN_KEY_GENDER, COLUMN_NAME_GENDER));

        TABLE_AGEGROUPS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_AGEGROUPS.setName("AgeGroups");
        TABLE_AGEGROUPS.getFeature()
                .addAll(List.of(COLUMN_AGE_AGEGROUPS, COLUMN_H1_AGEGROUPS, COLUMN_H1_ORDER_AGEGROUPS,
                        COLUMN_H2_AGEGROUPS, COLUMN_H2_ORDER_AGEGROUPS, COLUMN_H7_AGEGROUPS, COLUMN_H7_ORDER_AGEGROUPS,
                        COLUMN_H8_AGEGROUPS, COLUMN_H8_ORDER_AGEGROUPS, COLUMN_H9_AGEGROUPS,
                        COLUMN_H9_ORDER_AGEGROUPS));

        ORDERED_COLUMN_ORDINAL_YEAR = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_ORDINAL_YEAR.setColumn(COLUMN_ORDINAL_YEAR);

        ORDERED_COLUMN_H1_ORDER_AGEGROUPS = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H1_ORDER_AGEGROUPS.setColumn(COLUMN_H1_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H2_ORDER_AGEGROUPS = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H2_ORDER_AGEGROUPS.setColumn(COLUMN_H2_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H7_ORDER_AGEGROUPS = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H7_ORDER_AGEGROUPS.setColumn(COLUMN_H7_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H8_ORDER_AGEGROUPS = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H8_ORDER_AGEGROUPS.setColumn(COLUMN_H8_ORDER_AGEGROUPS);

        ORDERED_COLUMN_H9_ORDER_AGEGROUPS = RelationalFactory.eINSTANCE.createOrderedColumn();
        ORDERED_COLUMN_H9_ORDER_AGEGROUPS.setColumn(COLUMN_H9_ORDER_AGEGROUPS);

        // Initialize levels
        LEVEL_JAHR = LevelFactory.eINSTANCE.createLevel();
        LEVEL_JAHR.setName("Jahr");
        LEVEL_JAHR.setColumn(COLUMN_YEAR_YEAR);
        LEVEL_JAHR.getOrdinalColumns().add(ORDERED_COLUMN_ORDINAL_YEAR);
        LEVEL_JAHR.setType(LevelDefinition.TIME_YEARS);

        // Stadt level with GeoJson property
        LEVEL_STADT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STADT.setName("Stadt");
        LEVEL_STADT.setColumn(COLUMN_NAME_TOWN);
        //LEVEL_STADT.getOrdinalColumns().add(COLUMN_ORDINAL_YEAR);

        MemberProperty geoJsonPropertyTown = LevelFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyTown.setName("GeoJson");
        geoJsonPropertyTown.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyTown.setColumn(COLUMN_GEOJSON_TOWN);
        LEVEL_STADT.getMemberProperties().add(geoJsonPropertyTown);

        // Planungsraum level with properties
        LEVEL_PLANUNGSRAUM = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PLANUNGSRAUM.setName("Planungsraum");
        LEVEL_PLANUNGSRAUM.setColumn(COLUMN_GID_PLRAUM);
        LEVEL_PLANUNGSRAUM.setNameColumn(COLUMN_PLRAUM_PLRAUM);
        LEVEL_PLANUNGSRAUM.setColumnType(ColumnInternalDataType.INTEGER);

        MemberProperty uuidPropertyPlraum = LevelFactory.eINSTANCE.createMemberProperty();
        uuidPropertyPlraum.setName("uuid");
        uuidPropertyPlraum.setColumn(COLUMN_UUID_PLRAUM);
        LEVEL_PLANUNGSRAUM.getMemberProperties().add(uuidPropertyPlraum);

        MemberProperty geoJsonPropertyPlraum = LevelFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyPlraum.setName("GeoJson");
        geoJsonPropertyPlraum.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyPlraum.setColumn(COLUMN_GEOJSON_PLRAUM);
        LEVEL_PLANUNGSRAUM.getMemberProperties().add(geoJsonPropertyPlraum);

        // Statistischer Bezirk level with properties
        LEVEL_STATISTISCHER_BEZIRK = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STATISTISCHER_BEZIRK.setName("Statistischer Bezirk");
        LEVEL_STATISTISCHER_BEZIRK.setColumn(COLUMN_GID_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.setNameColumn(COLUMN_STATBEZ_NAME_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.setColumnType(ColumnInternalDataType.INTEGER);

        MemberProperty uuidPropertyStatbez = LevelFactory.eINSTANCE.createMemberProperty();
        uuidPropertyStatbez.setName("uuid");
        uuidPropertyStatbez.setColumn(COLUMN_UUID_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.getMemberProperties().add(uuidPropertyStatbez);

        MemberProperty geoJsonPropertyStatbez = LevelFactory.eINSTANCE.createMemberProperty();
        geoJsonPropertyStatbez.setName("GeoJson");
        geoJsonPropertyStatbez.setPropertyType(ColumnInternalDataType.STRING);
        geoJsonPropertyStatbez.setColumn(COLUMN_GEOJSON_STATBEZ);
        LEVEL_STATISTISCHER_BEZIRK.getMemberProperties().add(geoJsonPropertyStatbez);

        LEVEL_GESCHLECHT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_GESCHLECHT.setName("Geschlecht");
        LEVEL_GESCHLECHT.setColumn(COLUMN_KEY_GENDER);
        LEVEL_GESCHLECHT.setNameColumn(COLUMN_NAME_GENDER);

        // Age levels
        LEVEL_ALTER_EINZELJAHRGAENGE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_EINZELJAHRGAENGE.setName("Alter");
        LEVEL_ALTER_EINZELJAHRGAENGE.setColumn(COLUMN_AGE_AGEGROUPS);

        LEVEL_ALTERSGRUPPE_STANDARD = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_STANDARD.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_STANDARD.setColumn(COLUMN_H1_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_STANDARD.getOrdinalColumns().add(ORDERED_COLUMN_H1_ORDER_AGEGROUPS);

        LEVEL_ALTER_STANDARD = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_STANDARD.setName("Alter Standard");
        LEVEL_ALTER_STANDARD.setColumn(COLUMN_AGE_AGEGROUPS);

        LEVEL_ALTERSGRUPPE_KINDER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_KINDER.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_KINDER.setColumn(COLUMN_H2_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_KINDER.getOrdinalColumns().add(ORDERED_COLUMN_H2_ORDER_AGEGROUPS);

        LEVEL_ALTER_KINDER = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_KINDER.setName("Alter Kinder");
        LEVEL_ALTER_KINDER.setColumn(COLUMN_AGE_AGEGROUPS);

        LEVEL_ALTERSGRUPPE_RKI_H7 = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_RKI_H7.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_RKI_H7.setColumn(COLUMN_H7_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H7.getOrdinalColumns().add(ORDERED_COLUMN_H7_ORDER_AGEGROUPS);

        LEVEL_ALTER_RKI_H7 = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_RKI_H7.setName("Alter H7");
        LEVEL_ALTER_RKI_H7.setColumn(COLUMN_AGE_AGEGROUPS);

        LEVEL_ALTERSGRUPPE_RKI_H8 = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_RKI_H8.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_RKI_H8.setColumn(COLUMN_H8_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_RKI_H8.getOrdinalColumns().add(ORDERED_COLUMN_H8_ORDER_AGEGROUPS);

        LEVEL_ALTER_RKI_H8 = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_RKI_H8.setName("Alter H8");
        LEVEL_ALTER_RKI_H8.setColumn(COLUMN_AGE_AGEGROUPS);

        LEVEL_ALTERSGRUPPE_10JAHRE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTERSGRUPPE_10JAHRE.setName("Altersgruppe");
        LEVEL_ALTERSGRUPPE_10JAHRE.setColumn(COLUMN_H9_AGEGROUPS);
        LEVEL_ALTERSGRUPPE_10JAHRE.getOrdinalColumns().add(ORDERED_COLUMN_H9_ORDER_AGEGROUPS);

        LEVEL_ALTER_10JAHRE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_ALTER_10JAHRE.setName("Alter 10");
        LEVEL_ALTER_10JAHRE.setColumn(COLUMN_AGE_AGEGROUPS);

        // Initialize table queries
        TABLEQUERY_YEAR = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_YEAR.setTable(TABLE_YEAR);

        TABLEQUERY_TOWN = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_TOWN.setTable(TABLE_TOWN);

        TABLEQUERY_PLRAUM = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_PLRAUM.setTable(TABLE_PLRAUM);

        TABLEQUERY_STATBEZ = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_STATBEZ.setTable(TABLE_STATBEZ);

        TABLEQUERY_GENDER = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_GENDER.setTable(TABLE_GENDER);

        TABLEQUERY_AGEGROUPS = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_AGEGROUPS.setTable(TABLE_AGEGROUPS);

        TABLEQUERY_FACT = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_FACT.setTable(TABLE_EINWOHNER);

        // Initialize join query for Stadt-Planungsraum-Statistischer Bezirk hierarchy
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK = SourceFactory.eINSTANCE.createJoinSource();

        // Left side: statbez table
        JoinedQueryElement leftStatbez = SourceFactory.eINSTANCE.createJoinedQueryElement();
        leftStatbez.setKey(COLUMN_PLRAUM_STATBEZ);
        leftStatbez.setQuery(TABLEQUERY_STATBEZ);
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK.setLeft(leftStatbez);

        // Right side: join of plraum and town tables
        JoinSource joinPlraumTown = SourceFactory.eINSTANCE.createJoinSource();

        JoinedQueryElement leftPlraum = SourceFactory.eINSTANCE.createJoinedQueryElement();
        leftPlraum.setKey(COLUMN_TOWNID_PLRAUM);
        leftPlraum.setQuery(TABLEQUERY_PLRAUM);
        joinPlraumTown.setLeft(leftPlraum);

        JoinedQueryElement rightTown = SourceFactory.eINSTANCE.createJoinedQueryElement();
        rightTown.setKey(COLUMN_ID_TOWN);
        rightTown.setQuery(TABLEQUERY_TOWN);
        joinPlraumTown.setRight(rightTown);

        JoinedQueryElement rightPlraumTown = SourceFactory.eINSTANCE.createJoinedQueryElement();
        rightPlraumTown.setKey(COLUMN_GID_PLRAUM);
        rightPlraumTown.setQuery(joinPlraumTown);
        JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK.setRight(rightPlraumTown);

        // Initialize hierarchies
        HIERARCHY_JAHR = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_JAHR.setName("Jahr");
        HIERARCHY_JAHR.setHasAll(false);
        HIERARCHY_JAHR.setPrimaryKey(COLUMN_YEAR_YEAR);
        HIERARCHY_JAHR.setQuery(TABLEQUERY_YEAR);
        HIERARCHY_JAHR.setDefaultMember("2023");
        HIERARCHY_JAHR.getLevels().add(LEVEL_JAHR);

        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setName("Stadt - Planungsraum - statistischer Bezirk");
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setHasAll(true);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setAllMemberName("Alle Gebiete");
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setPrimaryKey(COLUMN_GID_STATBEZ);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.setQuery(JOINQUERY_STADT_PLANUNGSRAUM_STATBEZIRK);
        HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK.getLevels()
                .addAll(List.of(LEVEL_STADT, LEVEL_PLANUNGSRAUM, LEVEL_STATISTISCHER_BEZIRK));

        HIERARCHY_GESCHLECHT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_GESCHLECHT.setName("Geschlecht (m/w/d)");
        HIERARCHY_GESCHLECHT.setHasAll(true);
        HIERARCHY_GESCHLECHT.setAllMemberName("Alle Geschlechter");
        HIERARCHY_GESCHLECHT.setPrimaryKey(COLUMN_KEY_GENDER);
        HIERARCHY_GESCHLECHT.setQuery(TABLEQUERY_GENDER);
        HIERARCHY_GESCHLECHT.getLevels().add(LEVEL_GESCHLECHT);

        HIERARCHY_ALTER_EINZELJAHRGAENGE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setName("Alter (Einzeljahrgänge)");
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setHasAll(true);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTER_EINZELJAHRGAENGE.getLevels().add(LEVEL_ALTER_EINZELJAHRGAENGE);

        HIERARCHY_ALTERSGRUPPEN_STANDARD = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setName("Altersgruppen (Standard)");
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_STANDARD.getLevels().addAll(List.of(LEVEL_ALTERSGRUPPE_STANDARD, LEVEL_ALTER_STANDARD));

        HIERARCHY_ALTERSGRUPPEN_KINDER = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_KINDER.setName("Altersgruppen (Kinder)");
        HIERARCHY_ALTERSGRUPPEN_KINDER.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_KINDER.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_KINDER.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_KINDER.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_KINDER.getLevels().addAll(List.of(LEVEL_ALTERSGRUPPE_KINDER, LEVEL_ALTER_KINDER));

        HIERARCHY_ALTERSGRUPPEN_RKI_H7 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setName("Altersgruppen (Systematik RKI H7)");
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H7.getLevels().add(LEVEL_ALTER_RKI_H7);

        HIERARCHY_ALTERSGRUPPEN_RKI_H8 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setName("Altersgruppen (Systematik RKI H8)");
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_RKI_H8.getLevels().add(LEVEL_ALTER_RKI_H8);

        HIERARCHY_ALTERSGRUPPEN_10JAHRE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setName("Altersgruppen (10-Jahres-Gruppen)");
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setHasAll(true);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setAllMemberName("Alle Altersgruppen");
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setPrimaryKey(COLUMN_AGE_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.setQuery(TABLEQUERY_AGEGROUPS);
        HIERARCHY_ALTERSGRUPPEN_10JAHRE.getLevels().add(LEVEL_ALTER_10JAHRE);

        // Initialize dimensions
        DIMENSION_JAHR = DimensionFactory.eINSTANCE.createTimeDimension();
        DIMENSION_JAHR.setName("Jahr");
        DIMENSION_JAHR.getHierarchies().add(HIERARCHY_JAHR);

        DIMENSION_STATISTISCHER_BEZIRK = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_STATISTISCHER_BEZIRK.setName("statistischer Bezirk");
        DIMENSION_STATISTISCHER_BEZIRK.getHierarchies().add(HIERARCHY_STADT_PLANUNGSRAUM_STATBEZIRK);

        DIMENSION_GESCHLECHT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_GESCHLECHT.setName("Geschlecht");
        DIMENSION_GESCHLECHT.getHierarchies().add(HIERARCHY_GESCHLECHT);

        DIMENSION_ALTER = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_ALTER.setName("Alter");
        DIMENSION_ALTER.getHierarchies()
                .addAll(List.of(HIERARCHY_ALTER_EINZELJAHRGAENGE, HIERARCHY_ALTERSGRUPPEN_STANDARD,
                        HIERARCHY_ALTERSGRUPPEN_KINDER, HIERARCHY_ALTERSGRUPPEN_RKI_H7, HIERARCHY_ALTERSGRUPPEN_RKI_H8,
                        HIERARCHY_ALTERSGRUPPEN_10JAHRE));

        // Initialize dimension connectors
        CONNECTOR_JAHR = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_JAHR.setDimension(DIMENSION_JAHR);
        CONNECTOR_JAHR.setForeignKey(COLUMN_JAHR_EINWOHNER);
        CONNECTOR_JAHR.setOverrideDimensionName("Jahr");

        CONNECTOR_STATISTISCHER_BEZIRK = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_STATISTISCHER_BEZIRK.setDimension(DIMENSION_STATISTISCHER_BEZIRK);
        CONNECTOR_STATISTISCHER_BEZIRK.setForeignKey(COLUMN_STATBEZ_EINWOHNER);
        CONNECTOR_STATISTISCHER_BEZIRK.setOverrideDimensionName("statistischer Bezirk");

        CONNECTOR_GESCHLECHT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_GESCHLECHT.setDimension(DIMENSION_GESCHLECHT);
        CONNECTOR_GESCHLECHT.setForeignKey(COLUMN_KER_GESCH_EINWOHNER);
        CONNECTOR_GESCHLECHT.setOverrideDimensionName("Geschlecht");

        CONNECTOR_ALTER = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_ALTER.setDimension(DIMENSION_ALTER);
        CONNECTOR_ALTER.setForeignKey(COLUMN_AGE_EINWOHNER);
        CONNECTOR_ALTER.setOverrideDimensionName("Alter");

        // Initialize measures
        MEASURE_EINWOHNERZAHL = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_EINWOHNERZAHL.setName("Einwohnerzahl");
        MEASURE_EINWOHNERZAHL.setColumn(COLUMN_ANZAHL_EINWOHNER);
        MEASURE_EINWOHNERZAHL.setFormatString("#,###");

        MEASUREGROUP_BEVOELKERUNG = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_BEVOELKERUNG.getMeasures().add(MEASURE_EINWOHNERZAHL);

        // Initialize cube
        CUBE_BEVOELKERUNG = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_BEVOELKERUNG.setName("Bevölkerung");
        CUBE_BEVOELKERUNG.setQuery(TABLEQUERY_FACT);
        CUBE_BEVOELKERUNG.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_JAHR, CONNECTOR_STATISTISCHER_BEZIRK, CONNECTOR_GESCHLECHT, CONNECTOR_ALTER));
        CUBE_BEVOELKERUNG.getMeasureGroups().add(MEASUREGROUP_BEVOELKERUNG);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_POPULATION_JENA = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        //DATABASE_SCHEMA_POPULATION_JENA.setName("population_jena");
        DATABASE_SCHEMA_POPULATION_JENA.getOwnedElement().addAll(List.of(TABLE_EINWOHNER, TABLE_YEAR, TABLE_TOWN,
                TABLE_PLRAUM, TABLE_STATBEZ, TABLE_GENDER, TABLE_AGEGROUPS));

        CATALOG_POPULATION_JENA = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG_POPULATION_JENA.setName("Bevölkerung");
        CATALOG_POPULATION_JENA.getDbschemas().add(DATABASE_SCHEMA_POPULATION_JENA);
        CATALOG_POPULATION_JENA.getCubes().add(CUBE_BEVOELKERUNG);

        // Add documentation
    }

    @Override
    public Catalog get() {
        return CATALOG_POPULATION_JENA;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Bevölkerung Database", populationJenaBody, 1, 0, 0, null, 0),
                        new DocSection("Bevölkerungs Cube", bevoelkerungCubeBody, 1, 1, 0, CUBE_BEVOELKERUNG, 0),
                        new DocSection("Jahr Dimension", jahrBody, 1, 2, 0, DIMENSION_JAHR, 0),
                        new DocSection("Geografie Dimension", geografieBody, 1, 3, 0, DIMENSION_STATISTISCHER_BEZIRK, 0),
                        new DocSection("Geschlecht Dimension", geschlechtBody, 1, 4, 0, DIMENSION_GESCHLECHT, 0),
                        new DocSection("Alter Dimension", alterBody, 1, 5, 0, DIMENSION_ALTER, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
