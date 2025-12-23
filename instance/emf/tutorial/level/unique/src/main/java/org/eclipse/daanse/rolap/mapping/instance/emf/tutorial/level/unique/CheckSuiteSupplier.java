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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.unique;

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
 * Provides a check suite for the level unique tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, levels with unique and non-unique members, and measure exist and are accessible.
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
        LevelCheck buildingLevelCheck = factory.createLevelCheck();
        buildingLevelCheck.setName("LevelCheck-Building");
        buildingLevelCheck.setDescription("Check that level 'Building' exists with unique members");
        buildingLevelCheck.setLevelName("Building");

        LevelCheck roomLevelCheck = factory.createLevelCheck();
        roomLevelCheck.setName("LevelCheck-Room");
        roomLevelCheck.setDescription("Check that level 'Room' exists with non-unique members");
        roomLevelCheck.setLevelName("Room");

        // Create hierarchy attribute check
        HierarchyAttributeCheck hasAllCheck = factory.createHierarchyAttributeCheck();
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedValue("true");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-Hierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'Hierarchy' exists with two levels");
        hierarchyCheck.setHierarchyName("Hierarchy");
        hierarchyCheck.getLevelChecks().add(buildingLevelCheck);
        hierarchyCheck.getLevelChecks().add(roomLevelCheck);
        hierarchyCheck.getHierarchyAttributeChecks().add(hasAllCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Dimension");
        dimensionCheck.setDescription("Check that dimension 'Dimension' exists with hierarchy");
        dimensionCheck.setDimensionName("Dimension");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-NotUniqueLevelsMembers");
        cubeCheck.setDescription("Check that cube 'NotUniqueLevelsMembers' exists");
        cubeCheck.setCubeName("NotUniqueLevelsMembers");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [NotUniqueLevelsMembers] WHERE ([Measures].[Measure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnKeyTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKeyTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKeyTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnKeyTypeCheck);

        DatabaseColumnAttributeCheck columnBuildingTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnBuildingTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnBuildingTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckBuilding = factory.createDatabaseColumnCheck();
        columnCheckBuilding.setName("Database Column Check BUILDING");
        columnCheckBuilding.setColumnName("BUILDING");
        columnCheckBuilding.getColumnAttributeChecks().add(columnBuildingTypeCheck);

        DatabaseColumnAttributeCheck columnRoomTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnRoomTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnRoomTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckRoom = factory.createDatabaseColumnCheck();
        columnCheckRoom.setName("Database Column Check ROOM");
        columnCheckRoom.setColumnName("ROOM");
        columnCheckRoom.getColumnAttributeChecks().add(columnRoomTypeCheck);

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
        databaseTableCheckFact.getColumnChecks().add(columnCheckBuilding);
        databaseTableCheckFact.getColumnChecks().add(columnCheckRoom);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Level with not unique members");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Level with not unique members");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Level with not unique members' exists with unique and non-unique levels");
        catalogCheck.setCatalogName("Daanse Tutorial - Level with not unique members");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Level with not unique members");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Level with not unique members");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Level with not unique members");
        suite.setDescription("Check suite for the Daanse Tutorial - Level with unique and non-unique members");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
