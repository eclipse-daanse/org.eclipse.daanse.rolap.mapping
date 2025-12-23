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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.min;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the minimal virtual cube tutorial.
 * Checks that the catalog with two physical cubes (Cube1, Cube2) and one virtual cube (VirtualCubeMeasureOnly),
 * their measures, and calculated members exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Virtual Cube Minimal";

    // Cube names
    private static final String CUBE1_NAME = "Cube1";
    private static final String CUBE2_NAME = "Cube2";
    private static final String VIRTUAL_CUBE_NAME = "VirtualCubeMeasureOnly";

    // Measure names
    private static final String MEASURE_C1 = "C1-Measure-Sum";
    private static final String MEASURE_C2 = "C2-Measure-Sum";

    // Calculated member name
    private static final String CALC_MEMBER_CALCULATION1 = "Calculation1";

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck measureCheckC1 = createMeasureCheck(MEASURE_C1);
        MeasureCheck measureCheckC2 = createMeasureCheck(MEASURE_C2);

        // Create calculated member check
        MeasureCheck calcMemberCheckCalculation1 = createCalculatedMemberCheck(CALC_MEMBER_CALCULATION1);

        // Create cube check for Cube1
        CubeCheck cubeCheckCube1 = factory.createCubeCheck();
        cubeCheckCube1.setName("CubeCheck-" + CUBE1_NAME);
        cubeCheckCube1.setDescription("Check that cube '" + CUBE1_NAME + "' exists");
        cubeCheckCube1.setCubeName(CUBE1_NAME);
        cubeCheckCube1.getMeasureChecks().add(measureCheckC1);

        // Create cube check for Cube2
        CubeCheck cubeCheckCube2 = factory.createCubeCheck();
        cubeCheckCube2.setName("CubeCheck-" + CUBE2_NAME);
        cubeCheckCube2.setDescription("Check that cube '" + CUBE2_NAME + "' exists");
        cubeCheckCube2.setCubeName(CUBE2_NAME);
        cubeCheckCube2.getMeasureChecks().add(measureCheckC2);

        // Create cube check for Virtual Cube
        CubeCheck cubeCheckVirtualCube = factory.createCubeCheck();
        cubeCheckVirtualCube.setName("CubeCheck-" + VIRTUAL_CUBE_NAME);
        cubeCheckVirtualCube.setDescription("Check that virtual cube '" + VIRTUAL_CUBE_NAME + "' exists");
        cubeCheckVirtualCube.setCubeName(VIRTUAL_CUBE_NAME);
        cubeCheckVirtualCube.getMeasureChecks().add(measureCheckC1);
        cubeCheckVirtualCube.getMeasureChecks().add(measureCheckC2);
        cubeCheckVirtualCube.getMeasureChecks().add(calcMemberCheckCalculation1);

        // Create database table and column checks for C1_Fact
        DatabaseTableCheck tableCheckC1Fact = createTableCheck("C1_Fact",
                createColumnCheck("KEY", "VARCHAR"),
                createColumnCheck("VALUE", "INTEGER")
        );

        // Create database table and column checks for C2_Fact
        DatabaseTableCheck tableCheckC2Fact = createTableCheck("C2_Fact",
                createColumnCheck("KEY", "VARCHAR"),
                createColumnCheck("VALUE", "INTEGER")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for minimal virtual cube tutorial");
        databaseSchemaCheck.getTableChecks().add(tableCheckC1Fact);
        databaseSchemaCheck.getTableChecks().add(tableCheckC2Fact);

        // Create catalog check with all cube checks
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes, measures, and calculated members");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckCube1);
        catalogCheck.getCubeChecks().add(cubeCheckCube2);
        catalogCheck.getCubeChecks().add(cubeCheckVirtualCube);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for minimal virtual cube tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Virtual Cube Minimal Suite");
        suite.setDescription("Check suite for the minimal virtual cube tutorial");
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
     * Creates a CalculatedMemberCheck with the specified name.
     *
     * @param calculatedMemberName the name of the calculated member
     * @return the configured CalculatedMemberCheck
     */
    private MeasureCheck createCalculatedMemberCheck(String calculatedMemberName) {
        MeasureCheck calculatedMemberCheck = factory.createMeasureCheck();
        calculatedMemberCheck.setName("CalculatedMemberCheck-" + calculatedMemberName);
        calculatedMemberCheck.setDescription("Check that calculated member '" + calculatedMemberName + "' exists");
        calculatedMemberCheck.setMeasureName(calculatedMemberName);
        return calculatedMemberCheck;
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
