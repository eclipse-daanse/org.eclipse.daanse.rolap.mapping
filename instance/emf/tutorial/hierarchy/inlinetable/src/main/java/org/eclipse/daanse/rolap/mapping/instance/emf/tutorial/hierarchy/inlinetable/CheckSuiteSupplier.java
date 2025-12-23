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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.inlinetable;

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
 * Provides a check suite for the hierarchy inline table tutorial.
 * Checks that the catalog, cube, measure, and hierarchy with inline table exist and are accessible.
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

        // Create level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck for Level1");
        levelCheck.setLevelName("Level1");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Hierarchy1");
        hierarchyCheck.setHierarchyName("Hierarchy1");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Dimension1");
        dimensionCheck.setDimensionName("Dimension1");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1]");

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

        // Create database column checks for HT inline table
        DatabaseColumnAttributeCheck columnHTKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHTKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHTKeyTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckHTKey = factory.createDatabaseColumnCheck();
        columnCheckHTKey.setName("Database Column Check KEY (HT)");
        columnCheckHTKey.setColumnName("KEY");
        columnCheckHTKey.getColumnAttributeChecks().add(columnHTKeyTypeCheck);

        DatabaseColumnAttributeCheck columnHTValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHTValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHTValueTypeCheck.setExpectedValue("NUMERIC");

        DatabaseColumnCheck columnCheckHTValue = factory.createDatabaseColumnCheck();
        columnCheckHTValue.setName("Database Column Check VALUE (HT)");
        columnCheckHTValue.setColumnName("VALUE");
        columnCheckHTValue.getColumnAttributeChecks().add(columnHTValueTypeCheck);

        DatabaseColumnAttributeCheck columnHTNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHTNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHTNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckHTName = factory.createDatabaseColumnCheck();
        columnCheckHTName.setName("Database Column Check NAME (HT)");
        columnCheckHTName.setColumnName("NAME");
        columnCheckHTName.getColumnAttributeChecks().add(columnHTNameTypeCheck);

        DatabaseTableCheck databaseTableCheckHT = factory.createDatabaseTableCheck();
        databaseTableCheckHT.setName("Database Table HT Check");
        databaseTableCheckHT.setTableName("HT");
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTKey);
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTValue);
        databaseTableCheckHT.getColumnChecks().add(columnCheckHTName);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Inline Table");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckHT);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Inline Table");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Inline Table' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Inline Table");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Inline Table");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Inline Table");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Inline Table");
        suite.setDescription("Check suite for the Daanse Tutorial - Inline Table");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
