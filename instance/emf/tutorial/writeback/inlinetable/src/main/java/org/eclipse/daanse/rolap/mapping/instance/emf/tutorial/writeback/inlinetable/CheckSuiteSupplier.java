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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.inlinetable;

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
 * Provides a check suite for the writeback inline table tutorial.
 * Checks that the catalog with cube C, its measures (Measure1, Measure2),
 * dimension D1 with hierarchy and levels, and writeback functionality exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Writeback Inline Table";

    // Cube name
    private static final String CUBE_NAME = "C";

    // Measure names
    private static final String MEASURE1 = "Measure1";
    private static final String MEASURE2 = "Measure2";

    // Dimension name
    private static final String DIMENSION_D1 = "D1";

    // Hierarchy name
    private static final String HIERARCHY_NAME = "HierarchyWithHasAll";

    // Level names
    private static final String LEVEL_L1 = "L1";
    private static final String LEVEL_L2 = "L2";

    @Override
    public OlapCheckSuite get() {
        // Create level checks
        LevelCheck levelCheckL1 = createLevelCheck(LEVEL_L1);
        LevelCheck levelCheckL2 = createLevelCheck(LEVEL_L2);

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for " + HIERARCHY_NAME);
        hierarchyCheck.setHierarchyName(HIERARCHY_NAME);
        hierarchyCheck.getLevelChecks().add(levelCheckL1);
        hierarchyCheck.getLevelChecks().add(levelCheckL2);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + DIMENSION_D1);
        dimensionCheck.setDimensionName(DIMENSION_D1);
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create measure checks
        MeasureCheck measureCheck1 = createMeasureCheck(MEASURE1);
        MeasureCheck measureCheck2 = createMeasureCheck(MEASURE2);

        // Create cube check for C
        CubeCheck cubeCheckC = factory.createCubeCheck();
        cubeCheckC.setName("CubeCheck-" + CUBE_NAME);
        cubeCheckC.setDescription("Check that cube '" + CUBE_NAME + "' exists");
        cubeCheckC.setCubeName(CUBE_NAME);
        cubeCheckC.getMeasureChecks().add(measureCheck1);
        cubeCheckC.getMeasureChecks().add(measureCheck2);
        cubeCheckC.getDimensionChecks().add(dimensionCheck);

        // Create database table and column checks for FACT inline table
        DatabaseTableCheck tableCheckFact = createTableCheck("FACT",
                createColumnCheck("VAL", "INTEGER"),
                createColumnCheck("VAL1", "INTEGER"),
                createColumnCheck("L2", "VARCHAR")
        );

        // Create database table and column checks for L1 table
        DatabaseTableCheck tableCheckL1 = createTableCheck("L1",
                createColumnCheck("L1", "VARCHAR"),
                createColumnCheck("L2", "VARCHAR")
        );

        // Create database table and column checks for L2 table
        DatabaseTableCheck tableCheckL2 = createTableCheck("L2",
                createColumnCheck("L2", "VARCHAR")
        );

        // Create database table and column checks for FACTWB table
        DatabaseTableCheck tableCheckFactWB = createTableCheck("FACTWB",
                createColumnCheck("VAL", "INTEGER"),
                createColumnCheck("VAL1", "INTEGER"),
                createColumnCheck("L2", "VARCHAR"),
                createColumnCheck("ID", "VARCHAR"),
                createColumnCheck("USER", "VARCHAR")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for writeback inline table tutorial");
        databaseSchemaCheck.getTableChecks().add(tableCheckFact);
        databaseSchemaCheck.getTableChecks().add(tableCheckL1);
        databaseSchemaCheck.getTableChecks().add(tableCheckL2);
        databaseSchemaCheck.getTableChecks().add(tableCheckFactWB);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with cube, measures, dimension, and writeback functionality");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckC);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for writeback inline table tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Writeback Inline Table Suite");
        suite.setDescription("Check suite for the writeback inline table tutorial");
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
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck for " + levelName);
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
