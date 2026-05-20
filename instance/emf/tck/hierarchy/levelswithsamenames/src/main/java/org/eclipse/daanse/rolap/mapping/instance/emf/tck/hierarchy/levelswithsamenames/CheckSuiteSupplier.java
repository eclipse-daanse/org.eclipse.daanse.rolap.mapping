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
package org.eclipse.daanse.rolap.mapping.instance.emf.tck.hierarchy.levelswithsamenames;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
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
import org.eclipse.daanse.olap.check.model.check.MeasureAttribute;
import org.eclipse.daanse.olap.check.model.check.MeasureAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the minimal cube mapping.
 * Checks that the catalog, cube, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck theMeasureCheck = factory.createMeasureCheck();
        theMeasureCheck.setName("MeasureCheck-theMeasure");
        theMeasureCheck.setDescription("Check that measure 'theMeasure' exists");
        theMeasureCheck.setMeasureName("theMeasure");

        //TODO fix aggregates check executer
        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setAttributeType(MeasureAttribute.AGGREGATOR);
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setAttributeType(MeasureAttribute.NAME);
        measureSumAttributeCheck2.setExpectedValue("theMeasure");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        measureSumAttributeCheck3.setExpectedValue("[Measures].[theMeasure]");

        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck2);
        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck3);

        // Create Level check
        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("levelCheck for Town");
        levelTownCheck.setLevelName("Town");

        LevelCheck levelObjectCheck = factory.createLevelCheck();
        levelObjectCheck.setName("levelCheck for LevelObject");
        levelObjectCheck.setLevelName("Objects");

        LevelCheck levelIdCheck = factory.createLevelCheck();
        levelIdCheck.setName("levelCheck for LevelId");
        levelIdCheck.setLevelName("Id");

        HierarchyAttributeCheck hierarchyTownHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyTownHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyTownHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getHierarchyAttributeChecks().add(hierarchyTownHasAllAttributeCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);

        HierarchyAttributeCheck hierarchyObjectsHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyObjectsHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyObjectsHasAllAttributeCheck.setExpectedBoolean(true);

        HierarchyCheck objectsHierarchyCheck = factory.createHierarchyCheck();
        objectsHierarchyCheck.setName("HierarchyCheck for ObjectsHierarchy");
        objectsHierarchyCheck.setHierarchyName("ObjectsHierarchy");
        objectsHierarchyCheck.getHierarchyAttributeChecks().add(hierarchyObjectsHasAllAttributeCheck);
        objectsHierarchyCheck.getLevelChecks().add(levelObjectCheck);
        objectsHierarchyCheck.getLevelChecks().add(levelIdCheck);

        HierarchyAttributeCheck hierarchyYearHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyYearHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyYearHasAllAttributeCheck.setExpectedBoolean(false);

        LevelCheck levelYearCheck = factory.createLevelCheck();
        levelYearCheck.setName("levelCheck for LevelYear");
        levelYearCheck.setLevelName("Year");

        HierarchyCheck hierarchyYearCheck = factory.createHierarchyCheck();
        hierarchyYearCheck.setName("HierarchyCheck for ObjectsHierarchy");
        hierarchyYearCheck.setHierarchyName("YearHierarchy");
        hierarchyYearCheck.getHierarchyAttributeChecks().add(hierarchyYearHasAllAttributeCheck);
        hierarchyYearCheck.getLevelChecks().add(levelYearCheck);

        // Create dimension check
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);
        dimensionTownCheck.getHierarchyChecks().add(objectsHierarchyCheck);

        DimensionCheck dimensionYearCheck = factory.createDimensionCheck();
        dimensionYearCheck.setName("DimensionCheck for Town");
        dimensionYearCheck.setDimensionName("Year");
        dimensionYearCheck.getHierarchyChecks().add(hierarchyYearCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube with levels with same names of values");
        cubeCheck.setDescription("Check that cube 'Cube with levels with same names of values");
        cubeCheck.setCubeName("Cube with levels with same names of values");
        cubeCheck.getMeasureChecks().add(theMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);
        cubeCheck.getDimensionChecks().add(dimensionYearCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[theMeasure]");
        queryCheck1CellValueCheck.setExpectedValue("836.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(836.0);

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check theMeasure");
        queryCheck1.setDescription("Verify MDX query returns Measure data for theMeasure");
        queryCheck1.setQuery("SELECT FROM [Cube with levels with same names of values] WHERE ([Measures].[theMeasure])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(0);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        DatabaseColumnAttributeCheck columnAttributeCheckFactYear = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactYear.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactYear.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactTownYear = factory.createDatabaseColumnCheck();
        columnCheckFactTownYear.setName("Database Column Check YEAR");
        columnCheckFactTownYear.setColumnName("YEAR");
        columnCheckFactTownYear.getColumnAttributeChecks().add(columnAttributeCheckFactYear);

        DatabaseColumnAttributeCheck columnAttributeCheckFactId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactTownId = factory.createDatabaseColumnCheck();
        columnCheckFactTownId.setName("Database Column Check ID");
        columnCheckFactTownId.setColumnName("ID");
        columnCheckFactTownId.getColumnAttributeChecks().add(columnAttributeCheckFactId);

        DatabaseColumnAttributeCheck columnAttributeCheckFactValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactValue = factory.createDatabaseColumnCheck();
        columnCheckFactValue.setName("Database Column Check Value");
        columnCheckFactValue.setColumnName("VALUE");
        columnCheckFactValue.getColumnAttributeChecks().add(columnAttributeCheckFactValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactTownYear);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactTownId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);

        DatabaseColumnAttributeCheck columnAttributeCheckTownId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownId = factory.createDatabaseColumnCheck();
        columnCheckTownId.setName("Database Column Check Town ID");
        columnCheckTownId.setColumnName("ID");
        columnCheckTownId.getColumnAttributeChecks().add(columnAttributeCheckTownId);

        DatabaseColumnAttributeCheck columnAttributeCheckTownTownId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownTownId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownTownId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownTownId = factory.createDatabaseColumnCheck();
        columnCheckTownTownId.setName("Database Column Check Town TOWNID");
        columnCheckTownTownId.setColumnName("TOWNID");
        columnCheckTownTownId.getColumnAttributeChecks().add(columnAttributeCheckTownTownId);

        DatabaseColumnAttributeCheck columnAttributeCheckTownName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownName = factory.createDatabaseColumnCheck();
        columnCheckTownName.setName("Database Column Check Town Name");
        columnCheckTownName.setColumnName("NAME");
        columnCheckTownName.getColumnAttributeChecks().add(columnAttributeCheckTownName);

        DatabaseColumnAttributeCheck columnAttributeCheckTownObjectId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownObjectId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownObjectId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownObjectId = factory.createDatabaseColumnCheck();
        columnCheckTownObjectId.setName("Database Column Check Town OBJECTID");
        columnCheckTownObjectId.setColumnName("OBJECTID");
        columnCheckTownObjectId.getColumnAttributeChecks().add(columnAttributeCheckTownObjectId);

        DatabaseColumnAttributeCheck columnAttributeCheckTownObject = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownObject.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownObject.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownObject = factory.createDatabaseColumnCheck();
        columnCheckTownObject.setName("Database Column Check OBJECT");
        columnCheckTownObject.setColumnName("OBJECT");
        columnCheckTownObject.getColumnAttributeChecks().add(columnAttributeCheckTownObject);

        // Create Database Table Town Check
        DatabaseTableCheck databaseTableTownCheck = factory.createDatabaseTableCheck();
        databaseTableTownCheck.setName("Database Table Town Check");
        databaseTableTownCheck.setTableName("Town");
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownId);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownTownId);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownName);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownObjectId);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownObject);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Hierarchy Query Join Multi");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableTownCheck);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.setDescription("Check that catalog 'Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.setCatalogName("Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tck - Hierarchy with levels with same names of values");
        connectionCheck.setDescription("Connection check for Daanse Tck - Hierarchy with levels with same names of values");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tck - Hierarchy with levels with same names of values");
        suite.setDescription("Check suite for the Daanse Tck - Hierarchy with levels with same names of values");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
