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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.ifparentsname;

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
 * Provides a check suite for the level if parents name tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, levels with HideMemberIf.IF_PARENTS_NAME attribute, and measure exist and are accessible.
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
        level1Check.setName("LevelCheck-Level1");
        level1Check.setDescription("Check that level 'Level1' exists");
        level1Check.setLevelName("Level1");

        LevelCheck level2Check = factory.createLevelCheck();
        level2Check.setName("LevelCheck-Level2");
        level2Check.setDescription("Check that level 'Level2' exists with HideMemberIf.IF_PARENTS_NAME");
        level2Check.setLevelName("Level2");

        // Create hierarchy attribute check
        HierarchyAttributeCheck hasAllCheck = factory.createHierarchyAttributeCheck();
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedValue("true");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-Hierarchy1");
        hierarchyCheck.setDescription("Check that hierarchy 'Hierarchy1' exists with two levels");
        hierarchyCheck.setHierarchyName("Hierarchy1");
        hierarchyCheck.getLevelChecks().add(level1Check);
        hierarchyCheck.getLevelChecks().add(level2Check);
        hierarchyCheck.getHierarchyAttributeChecks().add(hasAllCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-DimensionMembersHiddenIfBlankName");
        dimensionCheck.setDescription("Check that dimension 'DimensionMembersHiddenIfBlankName' exists with hierarchy");
        dimensionCheck.setDimensionName("DimensionMembersHiddenIfBlankName");
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
        queryCheckCellValueCheck.setExpectedValue("0");

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

        // Create database column checks for Level_1 table
        DatabaseColumnAttributeCheck columnLevel1KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLevel1KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLevel1KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckLevel1Key = factory.createDatabaseColumnCheck();
        columnCheckLevel1Key.setName("Database Column Check KEY");
        columnCheckLevel1Key.setColumnName("KEY");
        columnCheckLevel1Key.getColumnAttributeChecks().add(columnLevel1KeyTypeCheck);

        DatabaseColumnAttributeCheck columnLevel1NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLevel1NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLevel1NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckLevel1Name = factory.createDatabaseColumnCheck();
        columnCheckLevel1Name.setName("Database Column Check NAME");
        columnCheckLevel1Name.setColumnName("NAME");
        columnCheckLevel1Name.getColumnAttributeChecks().add(columnLevel1NameTypeCheck);

        DatabaseTableCheck databaseTableCheckLevel1 = factory.createDatabaseTableCheck();
        databaseTableCheckLevel1.setName("Database Table Level_1 Check");
        databaseTableCheckLevel1.setTableName("Level_1");
        databaseTableCheckLevel1.getColumnChecks().add(columnCheckLevel1Key);
        databaseTableCheckLevel1.getColumnChecks().add(columnCheckLevel1Name);

        // Create database column checks for Level_2 table
        DatabaseColumnAttributeCheck columnLevel2KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLevel2KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLevel2KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckLevel2Key = factory.createDatabaseColumnCheck();
        columnCheckLevel2Key.setName("Database Column Check KEY");
        columnCheckLevel2Key.setColumnName("KEY");
        columnCheckLevel2Key.getColumnAttributeChecks().add(columnLevel2KeyTypeCheck);

        DatabaseColumnAttributeCheck columnLevel2NameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLevel2NameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLevel2NameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckLevel2Name = factory.createDatabaseColumnCheck();
        columnCheckLevel2Name.setName("Database Column Check NAME");
        columnCheckLevel2Name.setColumnName("NAME");
        columnCheckLevel2Name.getColumnAttributeChecks().add(columnLevel2NameTypeCheck);

        DatabaseColumnAttributeCheck columnLevel2L1KeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLevel2L1KeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLevel2L1KeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckLevel2L1Key = factory.createDatabaseColumnCheck();
        columnCheckLevel2L1Key.setName("Database Column Check L1_KEY");
        columnCheckLevel2L1Key.setColumnName("L1_KEY");
        columnCheckLevel2L1Key.getColumnAttributeChecks().add(columnLevel2L1KeyTypeCheck);

        DatabaseTableCheck databaseTableCheckLevel2 = factory.createDatabaseTableCheck();
        databaseTableCheckLevel2.setName("Database Table Level_2 Check");
        databaseTableCheckLevel2.setTableName("Level_2");
        databaseTableCheckLevel2.getColumnChecks().add(columnCheckLevel2Key);
        databaseTableCheckLevel2.getColumnChecks().add(columnCheckLevel2Name);
        databaseTableCheckLevel2.getColumnChecks().add(columnCheckLevel2L1Key);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Level If Parents Name");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckLevel1);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckLevel2);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Level If Parents Name");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Level If Parents Name' exists with HideMemberIf.IF_PARENTS_NAME level");
        catalogCheck.setCatalogName("Daanse Tutorial - Level If Parents Name");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Level If Parents Name");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Level If Parents Name");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Level If Parents Name");
        suite.setDescription("Check suite for the Daanse Tutorial - Level If Parents Name with HideMemberIf.IF_PARENTS_NAME attribute");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
