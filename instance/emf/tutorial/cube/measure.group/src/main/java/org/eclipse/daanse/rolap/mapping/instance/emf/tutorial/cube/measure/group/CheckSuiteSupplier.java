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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.group;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
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
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the measure group cube mapping.
 * Checks that the catalog, cube, and measures organized in groups exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks for "Group Alphabetic" measure group
        MeasureCheck measureACheck = factory.createMeasureCheck();
        measureACheck.setName("MeasureCheck-Measure A");
        measureACheck.setDescription("Check that measure 'Measure A' exists");
        measureACheck.setMeasureName("Measure A");

        MeasureCheck measureBCheck = factory.createMeasureCheck();
        measureBCheck.setName("MeasureCheck-Measure B");
        measureBCheck.setDescription("Check that measure 'Measure B' exists");
        measureBCheck.setMeasureName("Measure B");

        // Create measure check for "Group Other" measure group
        MeasureCheck measure1Check = factory.createMeasureCheck();
        measure1Check.setName("MeasureCheck-Measure 1");
        measure1Check.setDescription("Check that measure 'Measure 1' exists");
        measure1Check.setMeasureName("Measure 1");

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasureGroupCube");
        cubeCheck.setDescription("Check that cube 'MeasureGroupCube' exists");
        cubeCheck.setCubeName("MeasureGroupCube");
        cubeCheck.getMeasureChecks().add(measureACheck);
        cubeCheck.getMeasureChecks().add(measureBCheck);
        cubeCheck.getMeasureChecks().add(measure1Check);

        // Create query checks for each measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure A]");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Measure A");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Measure A");
        queryCheck1.setQuery("SELECT FROM [MeasureGroupCube] WHERE ([Measures].[Measure A])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Measure B]");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Measure B");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Measure B");
        queryCheck2.setQuery("SELECT FROM [MeasureGroupCube] WHERE ([Measures].[Measure B])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[Measure 1]");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check Measure 1");
        queryCheck3.setDescription("Verify MDX query returns Measure data for Measure 1");
        queryCheck3.setQuery("SELECT FROM [MeasureGroupCube] WHERE ([Measures].[Measure 1])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        // Create database column checks for the Fact table
        DatabaseColumnAttributeCheck columnAttributeCheckKey = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckKey.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckKey.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnAttributeCheckKey);

        DatabaseColumnAttributeCheck columnAttributeCheckValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnAttributeCheckValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Group");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Group");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Group' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Group");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Group");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Group");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Group");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Group");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
