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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.property.geo;

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
 * Provides a check suite for the member property geo tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, level with geographic member properties, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-TotalValue");
        measureCheck.setDescription("Check that measure 'TotalValue' exists");
        measureCheck.setMeasureName("TotalValue");

        // Create level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-Location");
        levelCheck.setDescription("Check that level 'Location' exists with geographic member properties");
        levelCheck.setLevelName("Location");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-LocationHierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'LocationHierarchy' exists with location level");
        hierarchyCheck.setHierarchyName("LocationHierarchy");
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Location");
        dimensionCheck.setDescription("Check that dimension 'Location' exists with hierarchy");
        dimensionCheck.setDimensionName("Location");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Geographic Analysis");
        cubeCheck.setDescription("Check that cube 'Geographic Analysis' exists");
        cubeCheck.setCubeName("Geographic Analysis");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[TotalValue]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns TotalValue data");
        queryCheck.setQuery("SELECT FROM [Geographic Analysis] WHERE ([Measures].[TotalValue])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnFactMemberIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFactMemberIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFactMemberIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckFactMemberId = factory.createDatabaseColumnCheck();
        columnCheckFactMemberId.setName("Database Column Check MEMBER_ID");
        columnCheckFactMemberId.setColumnName("MEMBER_ID");
        columnCheckFactMemberId.getColumnAttributeChecks().add(columnFactMemberIdTypeCheck);

        DatabaseColumnAttributeCheck columnFactValueTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnFactValueTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnFactValueTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckFactValue = factory.createDatabaseColumnCheck();
        columnCheckFactValue.setName("Database Column Check VALUE");
        columnCheckFactValue.setColumnName("VALUE");
        columnCheckFactValue.getColumnAttributeChecks().add(columnFactValueTypeCheck);

        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactMemberId);
        databaseTableCheckFact.getColumnChecks().add(columnCheckFactValue);

        // Create database column checks for Member table
        DatabaseColumnAttributeCheck columnMemberIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckMemberId = factory.createDatabaseColumnCheck();
        columnCheckMemberId.setName("Database Column Check ID");
        columnCheckMemberId.setColumnName("ID");
        columnCheckMemberId.getColumnAttributeChecks().add(columnMemberIdTypeCheck);

        DatabaseColumnAttributeCheck columnMemberNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMemberName = factory.createDatabaseColumnCheck();
        columnCheckMemberName.setName("Database Column Check NAME");
        columnCheckMemberName.setColumnName("NAME");
        columnCheckMemberName.getColumnAttributeChecks().add(columnMemberNameTypeCheck);

        DatabaseColumnAttributeCheck columnMemberLocationTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberLocationTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberLocationTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMemberLocation = factory.createDatabaseColumnCheck();
        columnCheckMemberLocation.setName("Database Column Check LOCATION");
        columnCheckMemberLocation.setColumnName("LOCATION");
        columnCheckMemberLocation.getColumnAttributeChecks().add(columnMemberLocationTypeCheck);

        DatabaseColumnAttributeCheck columnMemberLatitudeTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberLatitudeTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberLatitudeTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckMemberLatitude = factory.createDatabaseColumnCheck();
        columnCheckMemberLatitude.setName("Database Column Check LATITUDE");
        columnCheckMemberLatitude.setColumnName("LATITUDE");
        columnCheckMemberLatitude.getColumnAttributeChecks().add(columnMemberLatitudeTypeCheck);

        DatabaseColumnAttributeCheck columnMemberLongitudeTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberLongitudeTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberLongitudeTypeCheck.setExpectedValue("DECIMAL");

        DatabaseColumnCheck columnCheckMemberLongitude = factory.createDatabaseColumnCheck();
        columnCheckMemberLongitude.setName("Database Column Check LONGITUDE");
        columnCheckMemberLongitude.setColumnName("LONGITUDE");
        columnCheckMemberLongitude.getColumnAttributeChecks().add(columnMemberLongitudeTypeCheck);

        DatabaseColumnAttributeCheck columnMemberDescriptionTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMemberDescriptionTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMemberDescriptionTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMemberDescription = factory.createDatabaseColumnCheck();
        columnCheckMemberDescription.setName("Database Column Check DESCRIPTION");
        columnCheckMemberDescription.setColumnName("DESCRIPTION");
        columnCheckMemberDescription.getColumnAttributeChecks().add(columnMemberDescriptionTypeCheck);

        DatabaseTableCheck databaseTableCheckMember = factory.createDatabaseTableCheck();
        databaseTableCheckMember.setName("Database Table Member Check");
        databaseTableCheckMember.setTableName("Member");
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberId);
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberName);
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberLocation);
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberLatitude);
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberLongitude);
        databaseTableCheckMember.getColumnChecks().add(columnCheckMemberDescription);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Member Properties with Geographic Data");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckMember);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Member Properties with Geographic Data");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Member Properties with Geographic Data' exists with geographic member properties");
        catalogCheck.setCatalogName("Daanse Tutorial - Member Properties with Geographic Data");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Member Properties with Geographic Data");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Member Properties with Geographic Data");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Member Properties with Geographic Data");
        suite.setDescription("Check suite for the Daanse Tutorial - Member Properties with Geographic Data");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
