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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.expressivenames;

import java.util.List;

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
 * Provides a check suite for the ExpressiveNames complex mapping example.
 * Checks that the catalog with Cube1 and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "ExpressiveNames";

    // Cube name
    private static final String CUBE_1_NAME = "Cube1";

    // Measure name
    private static final String MEASURE_1 = "Measure1";

    // Dimension names
    private static final String DIMENSION_1 = "Dimension1";
    private static final String DIMENSION_2 = "Dimension2";
    private static final String DIMENSION_3 = "Dimension3";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckDimension1 = createDimensionCheck(DIMENSION_1,
                createHierarchyCheck("D1H1",
                        createLevelCheck("D1H1L1")));
        DimensionCheck dimCheckDimension2 = createDimensionCheck(DIMENSION_2,
                createHierarchyCheck("D2H1",
                        createLevelCheck("D2H1L1")),
                createHierarchyCheck("D2H2",
                        createLevelCheck("D2H2L1"),
                        createLevelCheck("D2H2L2")));
        DimensionCheck dimCheckDimension3 = createDimensionCheck(DIMENSION_3,
                createHierarchyCheck("D3H1",
                        createLevelCheck("D3H1L1")),
                createHierarchyCheck("D3H2",
                        createLevelCheck("D3H2L1"),
                        createLevelCheck("D3H2L2")),
                createHierarchyCheck("D3H3",
                        createLevelCheck("D3H3L1"),
                        createLevelCheck("D3H3L2"),
                        createLevelCheck("D3H3L3")));

        // Create measure check
        MeasureCheck measureCheck1 = createMeasureCheck(MEASURE_1);

        // Create cube check
        CubeCheck cubeCheckCube1 = factory.createCubeCheck();
        cubeCheckCube1.setName("CubeCheck-" + CUBE_1_NAME);
        cubeCheckCube1.setDescription("Check that cube '" + CUBE_1_NAME + "' exists");
        cubeCheckCube1.setCubeName(CUBE_1_NAME);
        cubeCheckCube1.getMeasureChecks().add(measureCheck1);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension1);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension2);
        cubeCheckCube1.getDimensionChecks().add(dimCheckDimension3);

        // Create database table and column checks
        DatabaseTableCheck tableCheckCube1Fact = createTableCheck("Cube1Fact",
                createColumnCheck("D1", "VARCHAR"),
                createColumnCheck("D2", "VARCHAR"),
                createColumnCheck("D3", "VARCHAR"),
                createColumnCheck("M1", "INTEGER")
        );

        DatabaseTableCheck tableCheckD1H1L1Table = createTableCheck("D1H1L1Table",
                createColumnCheck("D1H1L1", "VARCHAR"),
                createColumnCheck("D1H1L1_NAME", "VARCHAR"),
                createColumnCheck("D1H1L1_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD2H1L1Table = createTableCheck("D2H1L1Table",
                createColumnCheck("D2H1L1", "VARCHAR"),
                createColumnCheck("D2H1L1_NAME", "VARCHAR"),
                createColumnCheck("D2H1L1_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD2H2L2Table = createTableCheck("D2H2L2Table",
                createColumnCheck("D2H2L2", "VARCHAR"),
                createColumnCheck("D2H2L1", "VARCHAR"),
                createColumnCheck("D2H2L2_NAME", "VARCHAR"),
                createColumnCheck("D2H2L1_NAME", "VARCHAR"),
                createColumnCheck("D2H2L2_Ordinal", "VARCHAR"),
                createColumnCheck("D2H2L1_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H1L1Table = createTableCheck("D3H1L1Table",
                createColumnCheck("D3H1L1", "VARCHAR"),
                createColumnCheck("D3H1L1_NAME", "VARCHAR"),
                createColumnCheck("D3H1L1_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H2L2Table = createTableCheck("D3H2L2Table",
                createColumnCheck("D3H2L2", "VARCHAR"),
                createColumnCheck("D3H2L2_id", "VARCHAR"),
                createColumnCheck("D3H2L1_id", "VARCHAR"),
                createColumnCheck("D3H2L2_NAME", "VARCHAR"),
                createColumnCheck("D3H2L2_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H2L1Table = createTableCheck("D3H2L1Table",
                createColumnCheck("D3H2L1", "VARCHAR"),
                createColumnCheck("D3H2L1_NAME", "VARCHAR"),
                createColumnCheck("D3H2L1_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H3L3Table = createTableCheck("D3H3L3Table",
                createColumnCheck("D3H3L3", "VARCHAR"),
                createColumnCheck("D3H3L2_id", "VARCHAR"),
                createColumnCheck("D3H3L3_NAME", "VARCHAR"),
                createColumnCheck("D3H3L3_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H3L2Table = createTableCheck("D3H3L2Table",
                createColumnCheck("D3H3L2", "VARCHAR"),
                createColumnCheck("D3H3L1_id", "VARCHAR"),
                createColumnCheck("D3H3L2_NAME", "VARCHAR"),
                createColumnCheck("D3H3L2_Ordinal", "VARCHAR")
        );

        DatabaseTableCheck tableCheckD3H3L1Table = createTableCheck("D3H3L1Table",
                createColumnCheck("D3H3L1", "VARCHAR"),
                createColumnCheck("D3H3L1_NAME", "VARCHAR"),
                createColumnCheck("D3H3L1_Ordinal", "VARCHAR")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for ExpressiveNames mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckCube1Fact);
        databaseSchemaCheck.getTableChecks().add(tableCheckD1H1L1Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD2H1L1Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD2H2L2Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H1L1Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H2L2Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H2L1Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H3L3Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H3L2Table);
        databaseSchemaCheck.getTableChecks().add(tableCheckD3H3L1Table);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckCube1);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for ExpressiveNames mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("ExpressiveNames Example Suite");
        suite.setDescription("Check suite for the ExpressiveNames mapping example");
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
