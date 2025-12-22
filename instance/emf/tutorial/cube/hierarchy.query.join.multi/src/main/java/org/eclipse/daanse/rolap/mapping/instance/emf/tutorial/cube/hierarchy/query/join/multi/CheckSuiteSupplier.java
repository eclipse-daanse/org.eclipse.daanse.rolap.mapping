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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.join.multi;

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

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);

        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        // Create Level check
        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("levelCheck for Town");
        levelTownCheck.setLevelName("Town");

        LevelCheck levelCountryCheck = factory.createLevelCheck();
        levelCountryCheck.setName("levelCheck for County");
        levelCountryCheck.setLevelName("County");

        HierarchyAttributeCheck hierarchyTownHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyTownHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyTownHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getHierarchyAttributeChecks().add(hierarchyTownHasAllAttributeCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);
        hierarchyTownCheck.getLevelChecks().add(levelCountryCheck);


        // Create dimension check
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube Query linked Tables");
        cubeCheck.setDescription("Check that cube 'Cube Query linked Tables' exists");
        cubeCheck.setCubeName("Cube Query linked Tables");
        cubeCheck.getMeasureChecks().add(theMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[theMeasure]");
        queryCheck1CellValueCheck.setExpectedValue("378");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check theMeasure");
        queryCheck1.setDescription("Verify MDX query returns Measure data for theMeasure");
        queryCheck1.setQuery("SELECT FROM [Cube Query linked Tables] WHERE ([Measures].[theMeasure])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        DatabaseColumnAttributeCheck columnAttributeCheckFactTownId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckFactTownId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckFactTownId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactTownId = factory.createDatabaseColumnCheck();
        columnCheckFactTownId.setName("Database Column Check TOWN_ID");
        columnCheckFactTownId.setColumnName("TOWN_ID");
        columnCheckFactTownId.getColumnAttributeChecks().add(columnAttributeCheckFactTownId);

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
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactTownId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);

        DatabaseColumnAttributeCheck columnAttributeCheckTownId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownId = factory.createDatabaseColumnCheck();
        columnCheckTownId.setName("Database Column Check Town ID");
        columnCheckTownId.setColumnName("ID");
        columnCheckTownId.getColumnAttributeChecks().add(columnAttributeCheckTownId);

        DatabaseColumnAttributeCheck columnAttributeCheckTownName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownName = factory.createDatabaseColumnCheck();
        columnCheckTownName.setName("Database Column Check Town Name");
        columnCheckTownName.setColumnName("NAME");
        columnCheckTownName.getColumnAttributeChecks().add(columnAttributeCheckTownName);

        DatabaseColumnAttributeCheck columnAttributeCheckTownCountryId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownCountryId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownCountryId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownCountryId = factory.createDatabaseColumnCheck();
        columnCheckTownCountryId.setName("Database Column Check Town Country Id");
        columnCheckTownCountryId.setColumnName("COUNTRY_ID");
        columnCheckTownCountryId.getColumnAttributeChecks().add(columnAttributeCheckTownCountryId);

        // Create Database Table Town Check
        DatabaseTableCheck databaseTableTownCheck = factory.createDatabaseTableCheck();
        databaseTableTownCheck.setName("Database Table Town Check");
        databaseTableTownCheck.setTableName("Town");
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownId);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownName);
        databaseTableTownCheck.getColumnChecks().add(columnCheckTownCountryId);

        DatabaseColumnAttributeCheck columnAttributeCheckCountryId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckCountryId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckCountryId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckCountryId = factory.createDatabaseColumnCheck();
        columnCheckCountryId.setName("Database Column Check Country ID");
        columnCheckCountryId.setColumnName("ID");
        columnCheckCountryId.getColumnAttributeChecks().add(columnAttributeCheckCountryId);

        DatabaseColumnAttributeCheck columnAttributeCheckCountryName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckCountryName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckCountryName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckCountryName = factory.createDatabaseColumnCheck();
        columnCheckCountryName.setName("Database Column Check Country Name");
        columnCheckCountryName.setColumnName("NAME");
        columnCheckCountryName.getColumnAttributeChecks().add(columnAttributeCheckCountryName);

        DatabaseColumnAttributeCheck columnAttributeCheckCountryContinentId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckCountryContinentId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckCountryContinentId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckCountryContinentId = factory.createDatabaseColumnCheck();
        columnCheckCountryContinentId.setName("Database Column Check Continent ID");
        columnCheckCountryContinentId.setColumnName("CONTINENT_ID");
        columnCheckCountryContinentId.getColumnAttributeChecks().add(columnAttributeCheckCountryContinentId);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCountryCheck = factory.createDatabaseTableCheck();
        databaseTableCountryCheck.setName("Database Table Country Check");
        databaseTableCountryCheck.setTableName("Country");
        databaseTableCountryCheck.getColumnChecks().add(columnCheckCountryId);
        databaseTableCountryCheck.getColumnChecks().add(columnCheckCountryName);
        databaseTableCountryCheck.getColumnChecks().add(columnCheckCountryContinentId);

        DatabaseColumnAttributeCheck columnAttributeCheckContinentId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckContinentId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckContinentId.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckContinentId = factory.createDatabaseColumnCheck();
        columnCheckContinentId.setName("Database Column Check Country ID");
        columnCheckContinentId.setColumnName("ID");
        columnCheckContinentId.getColumnAttributeChecks().add(columnAttributeCheckContinentId);

        DatabaseColumnAttributeCheck columnAttributeCheckContinentName = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckContinentName.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckContinentName.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckContinentName = factory.createDatabaseColumnCheck();
        columnCheckContinentName.setName("Database Column Check Continent Name");
        columnCheckContinentName.setColumnName("NAME");
        columnCheckContinentName.getColumnAttributeChecks().add(columnAttributeCheckCountryName);

        // Create Database Table Check
        DatabaseTableCheck databaseTableContinentCheck = factory.createDatabaseTableCheck();
        databaseTableContinentCheck.setName("Database Table Continent Check");
        databaseTableContinentCheck.setTableName("Continent");
        databaseTableContinentCheck.getColumnChecks().add(columnCheckContinentId);
        databaseTableContinentCheck.getColumnChecks().add(columnCheckContinentName);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Hierarchy Query Join Multi");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCountryCheck);
        databaseSchemaCheck.getTableChecks().add(databaseTableTownCheck);
        databaseSchemaCheck.getTableChecks().add(databaseTableContinentCheck);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Hierarchy Query Join Base");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Hierarchy Query Join Multi' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Hierarchy Query Join Multi");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Hierarchy Query Join Multi");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Hierarchy Query Join Multi");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Hierarchy Query Join Multi");
        suite.setDescription("Check suite for the Daanse Tutorial - Hierarchy Query Join Multi");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
