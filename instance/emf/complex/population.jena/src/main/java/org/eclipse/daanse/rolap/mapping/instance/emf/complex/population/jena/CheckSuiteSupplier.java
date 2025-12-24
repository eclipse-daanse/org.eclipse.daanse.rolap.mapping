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

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
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
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the Bevölkerung (Population) Jena complex mapping example.
 * Checks that the catalog with Bevölkerung cube and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Bevölkerung";

    // Cube name
    private static final String CUBE_BEVOELKERUNG = "Bevölkerung";

    // Measure name
    private static final String MEASURE_EINWOHNERZAHL = "Einwohnerzahl";

    // Dimension names
    private static final String DIM_JAHR = "Jahr";
    private static final String DIM_STATISTISCHER_BEZIRK = "statistischer Bezirk";
    private static final String DIM_GESCHLECHT = "Geschlecht";
    private static final String DIM_ALTER = "Alter";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckJahr = createDimensionCheck(DIM_JAHR,
                createHierarchyCheck("Jahr",
                        createLevelCheck("Jahr")));
        DimensionCheck dimCheckStatistischerBezirk = createDimensionCheck(DIM_STATISTISCHER_BEZIRK,
                createHierarchyCheck("Stadt - Planungsraum - statistischer Bezirk",
                        createLevelCheck("Stadt"),
                        createLevelCheck("Planungsraum"),
                        createLevelCheck("Statistischer Bezirk")));
        DimensionCheck dimCheckGeschlecht = createDimensionCheck(DIM_GESCHLECHT,
                createHierarchyCheck("Geschlecht",
                        createLevelCheck("Geschlecht")));
        DimensionCheck dimCheckAlter = createDimensionCheck(DIM_ALTER,
                createHierarchyCheck("Alter (Einzeljahrgänge)",
                        createLevelCheck("Alter")),
                createHierarchyCheck("Altersgruppen (Standard)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter Standard")),
                createHierarchyCheck("Altersgruppen (Kinder)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter Kinder")),
                createHierarchyCheck("Altersgruppen (Systematik RKI H7)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter H7")),
                createHierarchyCheck("Altersgruppen (Systematik RKI H8)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter H8")),
                createHierarchyCheck("Altersgruppen (10-Jahres-Gruppen)",
                        createLevelCheck("Altersgruppe"),
                        createLevelCheck("Alter 10")));

        // Create measure check
        MeasureCheck measureCheckEinwohnerzahl = createMeasureCheck(MEASURE_EINWOHNERZAHL);

        // Create cube check for Bevölkerung
        CubeCheck cubeCheckBevoelkerung = factory.createCubeCheck();
        cubeCheckBevoelkerung.setName("CubeCheck-" + CUBE_BEVOELKERUNG);
        cubeCheckBevoelkerung.setDescription("Check that cube '" + CUBE_BEVOELKERUNG + "' exists");
        cubeCheckBevoelkerung.setCubeName(CUBE_BEVOELKERUNG);
        cubeCheckBevoelkerung.getMeasureChecks().add(measureCheckEinwohnerzahl);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckJahr);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckStatistischerBezirk);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckGeschlecht);
        cubeCheckBevoelkerung.getDimensionChecks().add(dimCheckAlter);

        // Create database table and column checks
        DatabaseTableCheck tableCheckEinwohner = createTableCheck("einwohner",
                createColumnCheck("JAHR", "INTEGER"),
                createColumnCheck("STATBEZ", "INTEGER"),
                createColumnCheck("KER_GESCH", "VARCHAR"),
                createColumnCheck("AGE", "INTEGER"),
                createColumnCheck("Anzahl", "INTEGER"),
                createColumnCheck("GEOJSON", "VARCHAR")
        );

        DatabaseTableCheck tableCheckYear = createTableCheck("year",
                createColumnCheck("year", "INTEGER"),
                createColumnCheck("ordinal", "INTEGER")
        );

        DatabaseTableCheck tableCheckTown = createTableCheck("town",
                createColumnCheck("id", "INTEGER"),
                createColumnCheck("name", "VARCHAR"),
                createColumnCheck("geojson", "VARCHAR")
        );

        DatabaseTableCheck tableCheckPlraum = createTableCheck("plraum",
                createColumnCheck("gid", "INTEGER"),
                createColumnCheck("plraum", "VARCHAR"),
                createColumnCheck("uuid", "VARCHAR"),
                createColumnCheck("geojson", "VARCHAR"),
                createColumnCheck("townid", "INTEGER")
        );

        DatabaseTableCheck tableCheckStatbez = createTableCheck("statbez",
                createColumnCheck("gid", "INTEGER"),
                createColumnCheck("plraum", "INTEGER"),
                createColumnCheck("statbez_name", "VARCHAR"),
                createColumnCheck("uuid", "VARCHAR"),
                createColumnCheck("geojson", "VARCHAR")
        );

        DatabaseTableCheck tableCheckGender = createTableCheck("gender",
                createColumnCheck("key", "VARCHAR"),
                createColumnCheck("name", "VARCHAR")
        );

        DatabaseTableCheck tableCheckAgeGroups = createTableCheck("AgeGroups",
                createColumnCheck("Age", "INTEGER"),
                createColumnCheck("H1", "VARCHAR"),
                createColumnCheck("H1_Order", "INTEGER"),
                createColumnCheck("H2", "VARCHAR"),
                createColumnCheck("H2_Order", "INTEGER"),
                createColumnCheck("H7", "VARCHAR"),
                createColumnCheck("H7_Order", "INTEGER"),
                createColumnCheck("H8", "VARCHAR"),
                createColumnCheck("H8_Order", "INTEGER"),
                createColumnCheck("H9", "VARCHAR"),
                createColumnCheck("H9_Order", "INTEGER")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for Bevölkerung Jena mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckEinwohner);
        databaseSchemaCheck.getTableChecks().add(tableCheckYear);
        databaseSchemaCheck.getTableChecks().add(tableCheckTown);
        databaseSchemaCheck.getTableChecks().add(tableCheckPlraum);
        databaseSchemaCheck.getTableChecks().add(tableCheckStatbez);
        databaseSchemaCheck.getTableChecks().add(tableCheckGender);
        databaseSchemaCheck.getTableChecks().add(tableCheckAgeGroups);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckBevoelkerung);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for Bevölkerung Jena mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Bevölkerung Jena Example Suite");
        suite.setDescription("Check suite for the Bevölkerung Jena (Population) mapping example");
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
     * @param dimensionName the name of the dimension
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
     * @param levelChecks the level checks to add to the hierarchy check
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
     * @param tableName the name of the table
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
