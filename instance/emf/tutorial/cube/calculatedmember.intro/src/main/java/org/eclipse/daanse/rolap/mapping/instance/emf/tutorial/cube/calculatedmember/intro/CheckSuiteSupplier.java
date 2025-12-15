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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.calculatedmember.intro;

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
        MeasureCheck measureSumCheck = factory.createMeasureCheck();
        measureSumCheck.setName("MeasureCheck-Measure-Sum");
        measureSumCheck.setDescription("Check that measure 'Measure-Sum' exists");
        measureSumCheck.setMeasureName("Measure1-Sum");

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);

        measureSumCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        MeasureCheck measureCountCheck = factory.createMeasureCheck();
        measureCountCheck.setName("MeasureCheck-Measure-Count");
        measureCountCheck.setDescription("Check that measure 'Measure-Count' exists");
        measureCountCheck.setMeasureName("Measure2-Count");

        MeasureAttributeCheck measureCountAttributeCheck = factory.createMeasureAttributeCheck();
        measureCountAttributeCheck.setExpectedAggregator(AggregatorType.COUNT);

        measureCountCheck.getMeasureAttributeChecks().add(measureCountAttributeCheck);

        MeasureCheck measureCheckCalculatedMember1 = factory.createMeasureCheck();
        measureCheckCalculatedMember1.setName("MemberCheck-Calculated Member 1");
        measureCheckCalculatedMember1.setMeasureName("Calculated Member 1");
        measureCheckCalculatedMember1.setMeasureUniqueName("[Measures].[Calculated Member 1]");


        MeasureCheck measureCheckCalculatedMember2 = factory.createMeasureCheck();
        measureCheckCalculatedMember2.setName("MemberCheck-Calculated Member 2");
        measureCheckCalculatedMember2.setMeasureName("Calculated Member 2");
        measureCheckCalculatedMember2.setMeasureUniqueName("[Measures].[Calculated Member 2]");

        // Create Level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("levelCheck for theLevel");
        levelCheck.setLevelName("theLevel");

        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for theHierarchy");
        hierarchyCheck.setHierarchyName("theHierarchy");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for theDimension");
        dimensionCheck.setDimensionName("theDimension");
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube CalculatedMember with different colors");
        cubeCheck.setDescription("Check that cube 'Cube CalculatedMember with different colors' exists with its measures");
        cubeCheck.setCubeName("Cube CalculatedMember with different colors");
        cubeCheck.getMeasureChecks().add(measureSumCheck);
        cubeCheck.getMeasureChecks().add(measureCountCheck);
        cubeCheck.getMeasureChecks().add(measureCheckCalculatedMember1);
        cubeCheck.getMeasureChecks().add(measureCheckCalculatedMember2);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure-Sum]");
        queryCheck1CellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Measure-Sum");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Measure-Sum");
        queryCheck1.setQuery("SELECT FROM [Cube CalculatedMember] WHERE ([Measures].[Measure1-Sum])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Measure-Count]");
        queryCheck2CellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Measure-Count");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Measure-Count");
        queryCheck2.setQuery("SELECT FROM [Cube CalculatedMember] WHERE ([Measures].[Measure2-Count])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[Calculated Member 1]");
        queryCheck3CellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check Calculated Member 1");
        queryCheck3.setDescription("Verify MDX query returns Measure data for Calculated Member 1");
        queryCheck3.setQuery("SELECT FROM [Cube CalculatedMember] WHERE ([Measures].[Calculated Member 1])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        CellValueCheck queryCheck4CellValueCheck = factory.createCellValueCheck();
        queryCheck4CellValueCheck.setName("[Measures].[Calculated Member 2]");
        queryCheck4CellValueCheck.setExpectedValue("63");

        QueryCheck queryCheck4 = factory.createQueryCheck();
        queryCheck4.setName("Measure Query Check Calculated Member 2");
        queryCheck4.setDescription("Verify MDX query returns Measure data for Calculated Member 2");
        queryCheck4.setQuery("SELECT FROM [Cube CalculatedMember] WHERE ([Measures].[Calculated Member 2])");
        queryCheck4.setQueryLanguage(QueryLanguage.MDX);
        queryCheck4.setExpectedColumnCount(1);
        queryCheck4.getCellChecks().add(queryCheck4CellValueCheck);
        queryCheck4.setEnabled(true);

        DatabaseColumnAttributeCheck columnAttributeCheckKey = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckKey.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckKey.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check Key");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnAttributeCheckKey);

        DatabaseColumnAttributeCheck columnAttributeCheckValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check Value");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnAttributeCheckValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Cube Calculated Member Color catalog check");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Calculated Member Intro catalog check");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Calculated Member Intro' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Calculated Member Intro");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getQueryChecks().add(queryCheck4);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Calculated Member Intro");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Calculated Member Intro tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Calculated Member Intro");
        suite.setDescription("Check suite for the Daanse Tutorial - Calculated Member Intro");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
