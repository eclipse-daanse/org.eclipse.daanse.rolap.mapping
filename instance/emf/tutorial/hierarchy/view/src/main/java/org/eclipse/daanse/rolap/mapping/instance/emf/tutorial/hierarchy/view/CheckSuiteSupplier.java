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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.view;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the unique key level name hierarchy tutorial.
 * Checks that the catalog, cube, measure, and hierarchy with unique key level name optimization exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure1");
        measureCheck.setDescription("Check that measure 'Measure1' exists");
        measureCheck.setMeasureName("Measure1");

        // Create level checks
        LevelCheck level1Check = factory.createLevelCheck();
        level1Check.setName("LevelCheck for Level1");
        level1Check.setLevelName("Level1");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check for Automotive dimension
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Hierarchy");
        hierarchyCheck.setHierarchyName("Hierarchy");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(level1Check);

        // Create dimension check for Automotive
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Dimension");
        dimensionCheck.setDimensionName("Dimension");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1]");
        queryCheckCellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnDimKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDimKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDimKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckDimKey = factory.createDatabaseColumnCheck();
        columnCheckDimKey.setName("Database Column Check DIM_KEY");
        columnCheckDimKey.setColumnName("DIM_KEY");
        columnCheckDimKey.getColumnAttributeChecks().add(columnDimKeyTypeCheck);

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
        databaseTableCheckFact.getColumnChecks().add(columnCheckDimKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        DatabaseColumnAttributeCheck columnKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckHTKey = factory.createDatabaseColumnCheck();
        columnCheckHTKey.setName("Database Column Check KEY");
        columnCheckHTKey.setColumnName("KEY");
        columnCheckHTKey.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseColumnAttributeCheck columnNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckHTName = factory.createDatabaseColumnCheck();
        columnCheckHTName.setName("Database Column Check NAME");
        columnCheckHTName.setColumnName("NAME");
        columnCheckHTName.getColumnAttributeChecks().add(columnNameTypeCheck);

        DatabaseColumnAttributeCheck columnHTValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHTValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHTValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckHTValue = factory.createDatabaseColumnCheck();
        columnCheckHTValue.setName("Database Column Check VALUE");
        columnCheckHTValue.setColumnName("VALUE");
        columnCheckHTValue.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseTableCheck databaseTableCheckHT = factory.createDatabaseTableCheck();
        databaseTableCheckHT.setName("Database Table HT Check");
        databaseTableCheckHT.setTableName("HT");
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTKey);
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTName);
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Hierarchy View");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckHT);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Hierarchy View");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Hierarchy View' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Hierarchy View");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Hierarchy View");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Hierarchy View");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Hierarchy View");
        suite.setDescription("Check suite for the Daanse Tutorial - Hierarchy View");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
