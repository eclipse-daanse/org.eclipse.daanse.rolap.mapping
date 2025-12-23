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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.function.logic;

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
 * Provides a check suite for the function logic tutorial.
 * Checks that the catalog, cube, measures, and calculated members with logic functions exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure checks
        MeasureCheck measureCheckSum = factory.createMeasureCheck();
        measureCheckSum.setName("MeasureCheck-Measure-Sum");
        measureCheckSum.setDescription("Check that measure 'Measure-Sum' exists");
        measureCheckSum.setMeasureName("Measure-Sum");

        MeasureCheck measureCheckCount = factory.createMeasureCheck();
        measureCheckCount.setName("MeasureCheck-Measure-Count");
        measureCheckCount.setDescription("Check that measure 'Measure-Count' exists");
        measureCheckCount.setMeasureName("Measure-Count");

        // Create cube check with measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube logic functions");
        cubeCheck.setDescription("Check that cube 'Cube logic functions' exists");
        cubeCheck.setCubeName("Cube logic functions");
        cubeCheck.getMeasureChecks().add(measureCheckSum);
        cubeCheck.getMeasureChecks().add(measureCheckCount);

        // Create query check for Measure-Sum
        CellValueCheck queryCheckCellValueCheckSum = factory.createCellValueCheck();
        queryCheckCellValueCheckSum.setName("[Measures].[Measure-Sum]");

        QueryCheck queryCheckSum = factory.createQueryCheck();
        queryCheckSum.setName("Measure-Sum Query Check");
        queryCheckSum.setDescription("Verify MDX query returns Measure-Sum data");
        queryCheckSum.setQuery("SELECT FROM [Cube logic functions] WHERE ([Measures].[Measure-Sum])");
        queryCheckSum.setQueryLanguage(QueryLanguage.MDX);
        queryCheckSum.setExpectedColumnCount(1);
        queryCheckSum.getCellChecks().add(queryCheckCellValueCheckSum);
        queryCheckSum.setEnabled(true);

        // Create query check for Measure-Count
        CellValueCheck queryCheckCellValueCheckCount = factory.createCellValueCheck();
        queryCheckCellValueCheckCount.setName("[Measures].[Measure-Count]");

        QueryCheck queryCheckCount = factory.createQueryCheck();
        queryCheckCount.setName("Measure-Count Query Check");
        queryCheckCount.setDescription("Verify MDX query returns Measure-Count data");
        queryCheckCount.setQuery("SELECT FROM [Cube logic functions] WHERE ([Measures].[Measure-Count])");
        queryCheckCount.setQueryLanguage(QueryLanguage.MDX);
        queryCheckCount.setExpectedColumnCount(1);
        queryCheckCount.getCellChecks().add(queryCheckCellValueCheckCount);
        queryCheckCount.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseColumnAttributeCheck columnValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnValueTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Function Logic");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Function Logic");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Function Logic' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Function Logic");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheckSum);
        catalogCheck.getQueryChecks().add(queryCheckCount);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Function Logic");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Function Logic");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Function Logic");
        suite.setDescription("Check suite for the Daanse Tutorial - Function Logic");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
