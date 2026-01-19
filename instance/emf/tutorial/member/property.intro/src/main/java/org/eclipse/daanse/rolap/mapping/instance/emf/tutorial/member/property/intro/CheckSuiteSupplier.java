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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.property.intro;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
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
 * Provides a check suite for the member property intro tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, level with member property, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-theMeasure");
        measureCheck.setDescription("Check that measure 'theMeasure' exists");
        measureCheck.setMeasureName("theMeasure");

        // Create level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-Town");
        levelCheck.setDescription("Check that level 'Town' exists with member property 'Capital'");
        levelCheck.setLevelName("Town");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-TownHierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'TownHierarchy' exists with town level");
        hierarchyCheck.setHierarchyName("TownHierarchy");
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Town");
        dimensionCheck.setDescription("Check that dimension 'Town' exists with hierarchy");
        dimensionCheck.setDimensionName("Town");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube Query linked Tables");
        cubeCheck.setDescription("Check that cube 'Cube Query linked Tables' exists");
        cubeCheck.setCubeName("Cube Query linked Tables");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[theMeasure]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns theMeasure data");
        queryCheck.setQuery("SELECT FROM [Cube Query linked Tables] WHERE ([Measures].[theMeasure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnFactTownIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFactTownIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFactTownIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactTownId = factory.createDatabaseColumnCheck();
        columnCheckFactTownId.setName("Database Column Check TOWN_ID");
        columnCheckFactTownId.setColumnName("TOWN_ID");
        columnCheckFactTownId.getColumnAttributeChecks().add(columnFactTownIdTypeCheck);

        DatabaseColumnAttributeCheck columnFactValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFactValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFactValueTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactValue = factory.createDatabaseColumnCheck();
        columnCheckFactValue.setName("Database Column Check VALUE");
        columnCheckFactValue.setColumnName("VALUE");
        columnCheckFactValue.getColumnAttributeChecks().add(columnFactValueTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactTownId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);

        // Create database column checks for Town table
        DatabaseColumnAttributeCheck columnTownIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTownIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTownIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTownId = factory.createDatabaseColumnCheck();
        columnCheckTownId.setName("Database Column Check ID");
        columnCheckTownId.setColumnName("ID");
        columnCheckTownId.getColumnAttributeChecks().add(columnTownIdTypeCheck);

        DatabaseColumnAttributeCheck columnTownNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTownNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTownNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownName = factory.createDatabaseColumnCheck();
        columnCheckTownName.setName("Database Column Check NAME");
        columnCheckTownName.setColumnName("NAME");
        columnCheckTownName.getColumnAttributeChecks().add(columnTownNameTypeCheck);

        DatabaseColumnAttributeCheck columnTownCapitalTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTownCapitalTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTownCapitalTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckTownCapital = factory.createDatabaseColumnCheck();
        columnCheckTownCapital.setName("Database Column Check CAPITAL");
        columnCheckTownCapital.setColumnName("CAPITAL");
        columnCheckTownCapital.getColumnAttributeChecks().add(columnTownCapitalTypeCheck);

        DatabaseTableCheck databaseTableCheckTown = factory.createDatabaseTableCheck();
        databaseTableCheckTown.setName("Database Table Town Check");
        databaseTableCheckTown.setTableName("Town");
        databaseTableCheckTown.getColumnChecks().add(columnCheckTownId);
        databaseTableCheckTown.getColumnChecks().add(columnCheckTownName);
        databaseTableCheckTown.getColumnChecks().add(columnCheckTownCapital);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Level Member Property Intro");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckTown);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Level Member Property Intro");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Level Member Property Intro' exists with member properties");
        catalogCheck.setCatalogName("Daanse Tutorial - Level Member Property Intro");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Level Member Property Intro");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Level Member Property Intro");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Level Member Property Intro");
        suite.setDescription("Check suite for the Daanse Tutorial - Level Member Property Intro with Capital member property");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
