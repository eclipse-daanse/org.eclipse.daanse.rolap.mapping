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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.table.multilevel.singletable;

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
        levelCountryCheck.setName("levelCheck for Country");
        levelCountryCheck.setLevelName("Country");

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

        DatabaseColumnAttributeCheck columnAttributeCheckTownCountryId = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckTownCountryId.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckTownCountryId.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownCountry = factory.createDatabaseColumnCheck();
        columnCheckTownCountry.setName("Database Column Check Town Country");
        columnCheckTownCountry.setColumnName("COUNTRY");
        columnCheckTownCountry.getColumnAttributeChecks().add(columnAttributeCheckTownCountryId);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactTownId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);
        databaseTableCheckFact.getColumnChecks().add(columnCheckTownCountry);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Hierarchy Query Table Multilevel Singletable' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        suite.setDescription("Check suite for the Daanse Tutorial - Hierarchy Query Table Multilevel Singletable");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
