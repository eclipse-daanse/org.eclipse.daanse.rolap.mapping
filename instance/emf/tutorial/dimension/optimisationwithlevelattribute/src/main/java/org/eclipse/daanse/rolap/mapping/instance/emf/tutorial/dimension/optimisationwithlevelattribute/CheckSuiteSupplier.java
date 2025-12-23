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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.optimisationwithlevelattribute;

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
 * Provides a check suite for the dimension optimisation with level attribute tutorial.
 * Checks that the catalog, cube, and dimension with level attribute optimization exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure");
        measureCheck.setDescription("Check that measure 'Measure' exists");
        measureCheck.setMeasureName("Measure");

        // Create level checks
        LevelCheck levelH1Level1Check = factory.createLevelCheck();
        levelH1Level1Check.setName("LevelCheck for H1_Level1");
        levelH1Level1Check.setLevelName("H1_Level1");

        LevelCheck levelH1Level2Check = factory.createLevelCheck();
        levelH1Level2Check.setName("LevelCheck for H1_Level2");
        levelH1Level2Check.setLevelName("H1_Level2");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check for Hierarchy1
        HierarchyCheck hierarchyCheck1 = factory.createHierarchyCheck();
        hierarchyCheck1.setName("HierarchyCheck for Hierarchy1 (Dim1)");
        hierarchyCheck1.setHierarchyName("Hierarchy1");
        hierarchyCheck1.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck1.getLevelChecks().add(levelH1Level1Check);
        hierarchyCheck1.getLevelChecks().add(levelH1Level2Check);

        // Create dimension check for Dim1
        DimensionCheck dimensionCheck1 = factory.createDimensionCheck();
        dimensionCheck1.setName("DimensionCheck for Dim1");
        dimensionCheck1.setDimensionName("Dim1");
        dimensionCheck1.getHierarchyChecks().add(hierarchyCheck1);

        // Create level checks for Dim2 (reusing the same level definitions)
        LevelCheck levelH1Level1Check2 = factory.createLevelCheck();
        levelH1Level1Check2.setName("LevelCheck for H1_Level1 (Dim2)");
        levelH1Level1Check2.setLevelName("H1_Level1");

        LevelCheck levelH1Level2Check2 = factory.createLevelCheck();
        levelH1Level2Check2.setName("LevelCheck for H1_Level2 (Dim2)");
        levelH1Level2Check2.setLevelName("H1_Level2");

        // Create hierarchy attribute check for hasAll (Dim2)
        HierarchyAttributeCheck hierarchyAttributeCheck2 = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck2.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck2.setExpectedBoolean(true);

        // Create hierarchy check for Hierarchy1 (for Dim2)
        HierarchyCheck hierarchyCheck2 = factory.createHierarchyCheck();
        hierarchyCheck2.setName("HierarchyCheck for Hierarchy1 (Dim2)");
        hierarchyCheck2.setHierarchyName("Hierarchy1");
        hierarchyCheck2.getHierarchyAttributeChecks().add(hierarchyAttributeCheck2);
        hierarchyCheck2.getLevelChecks().add(levelH1Level1Check2);
        hierarchyCheck2.getLevelChecks().add(levelH1Level2Check2);

        // Create dimension check for Dim2
        DimensionCheck dimensionCheck2 = factory.createDimensionCheck();
        dimensionCheck2.setName("DimensionCheck for Dim2");
        dimensionCheck2.setDimensionName("Dim2");
        dimensionCheck2.getHierarchyChecks().add(hierarchyCheck2);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck1);
        cubeCheck.getDimensionChecks().add(dimensionCheck2);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure]");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnDimKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDimKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDimKeyTypeCheck.setExpectedValue("INTEGER");

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

        // Create database column checks for H1_L1 table
        DatabaseColumnAttributeCheck columnH1L1KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH1L1Key = factory.createDatabaseColumnCheck();
        columnCheckH1L1Key.setName("Database Column Check KEY (H1_L1)");
        columnCheckH1L1Key.setColumnName("KEY");
        columnCheckH1L1Key.getColumnAttributeChecks().add(columnH1L1KeyTypeCheck);

        DatabaseColumnAttributeCheck columnH1L1NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckH1L1Name = factory.createDatabaseColumnCheck();
        columnCheckH1L1Name.setName("Database Column Check NAME (H1_L1)");
        columnCheckH1L1Name.setColumnName("NAME");
        columnCheckH1L1Name.getColumnAttributeChecks().add(columnH1L1NameTypeCheck);

        DatabaseTableCheck databaseTableCheckH1L1 = factory.createDatabaseTableCheck();
        databaseTableCheckH1L1.setName("Database Table H1_L1 Check");
        databaseTableCheckH1L1.setTableName("H1_L1");
        databaseTableCheckH1L1.getColumnChecks().add(columnCheckH1L1Key);
        databaseTableCheckH1L1.getColumnChecks().add(columnCheckH1L1Name);

        // Create database column checks for HX_L2 table
        DatabaseColumnAttributeCheck columnHXL2KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHXL2KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHXL2KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckHXL2Key = factory.createDatabaseColumnCheck();
        columnCheckHXL2Key.setName("Database Column Check KEY (HX_L2)");
        columnCheckHXL2Key.setColumnName("KEY");
        columnCheckHXL2Key.getColumnAttributeChecks().add(columnHXL2KeyTypeCheck);

        DatabaseColumnAttributeCheck columnHXL2NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnHXL2NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnHXL2NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckHXL2Name = factory.createDatabaseColumnCheck();
        columnCheckHXL2Name.setName("Database Column Check NAME (HX_L2)");
        columnCheckHXL2Name.setColumnName("NAME");
        columnCheckHXL2Name.getColumnAttributeChecks().add(columnHXL2NameTypeCheck);

        DatabaseColumnAttributeCheck columnH1L1KeyFKTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH1L1KeyFKTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH1L1KeyFKTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH1L1KeyFK = factory.createDatabaseColumnCheck();
        columnCheckH1L1KeyFK.setName("Database Column Check H1L1_KEY (HX_L2)");
        columnCheckH1L1KeyFK.setColumnName("H1L1_KEY");
        columnCheckH1L1KeyFK.getColumnAttributeChecks().add(columnH1L1KeyFKTypeCheck);

        DatabaseColumnAttributeCheck columnH2L1KeyFKTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnH2L1KeyFKTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnH2L1KeyFKTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckH2L1KeyFK = factory.createDatabaseColumnCheck();
        columnCheckH2L1KeyFK.setName("Database Column Check H2L1_KEY (HX_L2)");
        columnCheckH2L1KeyFK.setColumnName("H2L1_KEY");
        columnCheckH2L1KeyFK.getColumnAttributeChecks().add(columnH2L1KeyFKTypeCheck);

        DatabaseTableCheck databaseTableCheckHXL2 = factory.createDatabaseTableCheck();
        databaseTableCheckHXL2.setName("Database Table HX_L2 Check");
        databaseTableCheckHXL2.setTableName("HX_L2");
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckHXL2Key);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckHXL2Name);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckH1L1KeyFK);
        databaseTableCheckHXL2.getColumnChecks().add(columnCheckH2L1KeyFK);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Dimension Optimisation With Level Attribute");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckH1L1);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckHXL2);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Dimension Optimisation With Level Attribute' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Dimension Optimisation With Level Attribute");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Dimension Optimisation With Level Attribute");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        suite.setDescription("Check suite for the Daanse Tutorial - Dimension Optimisation With Level Attribute");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
