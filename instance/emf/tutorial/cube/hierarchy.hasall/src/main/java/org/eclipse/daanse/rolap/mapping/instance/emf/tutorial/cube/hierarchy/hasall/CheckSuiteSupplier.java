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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.hasall;

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

        MeasureAttributeCheck measureSumAttributeCheck = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck.setExpectedAggregator(AggregatorType.SUM);

        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck);

        // Create Level check
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("levelCheck for theLevel");
        levelCheck.setLevelName("theLevel");

        HierarchyAttributeCheck hierarchyHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyHasAllCheck = factory.createHierarchyCheck();
        hierarchyHasAllCheck.setName("HierarchyCheck for Hierarchy - with HasAll");
        hierarchyHasAllCheck.setHierarchyName("Hierarchy - with HasAll");
        hierarchyHasAllCheck.getHierarchyAttributeChecks().add(hierarchyHasAllAttributeCheck);
        hierarchyHasAllCheck.getLevelChecks().add(levelCheck);

        HierarchyAttributeCheck hierarchyHasAllAndNamesAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyHasAllAndNamesAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyHasAllAndNamesAttributeCheck.setExpectedBoolean(true);

        HierarchyAttributeCheck hierarchyHasAllAndNamesAttributeCheck1 = factory.createHierarchyAttributeCheck();
        hierarchyHasAllAndNamesAttributeCheck1.setAttributeType(HierarchyAttribute.ALL_MEMBER_NAME);
        hierarchyHasAllAndNamesAttributeCheck1.setExpectedValue("theAllMemberName");

        // Create hierarchy check
        HierarchyCheck hierarchyHasAllAndNamesCheck = factory.createHierarchyCheck();
        hierarchyHasAllAndNamesCheck.setName("HierarchyCheck for Hierarchy - with HasAll and Names");
        hierarchyHasAllAndNamesCheck.setHierarchyName("Hierarchy - with HasAll and Names");
        hierarchyHasAllAndNamesCheck.getHierarchyAttributeChecks().add(hierarchyHasAllAndNamesAttributeCheck);
        hierarchyHasAllAndNamesCheck.getHierarchyAttributeChecks().add(hierarchyHasAllAndNamesAttributeCheck1);
        hierarchyHasAllAndNamesCheck.getLevelChecks().add(levelCheck);

        HierarchyAttributeCheck hierarchyWithoutHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyWithoutHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyWithoutHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyWithoutHasAllCheck = factory.createHierarchyCheck();
        hierarchyWithoutHasAllCheck.setName("HierarchyCheck for Hierarchy - Without HasAll");
        hierarchyWithoutHasAllCheck.setHierarchyName("Hierarchy - Without HasAll");
        hierarchyWithoutHasAllCheck.getHierarchyAttributeChecks().add(hierarchyWithoutHasAllAttributeCheck);
        hierarchyWithoutHasAllCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Dimension1");
        dimensionCheck.setDimensionName("Dimension1");
        dimensionCheck.getHierarchyChecks().add(hierarchyHasAllCheck);
        dimensionCheck.getHierarchyChecks().add(hierarchyHasAllAndNamesCheck);
        dimensionCheck.getHierarchyChecks().add(hierarchyWithoutHasAllCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-HasAll Cube");
        cubeCheck.setDescription("Check that cube 'HasAll Cube' exists hierarchy with has all true");
        cubeCheck.setCubeName("HasAll Cube");
        cubeCheck.getMeasureChecks().add(theMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure-Sum]");
        queryCheck1CellValueCheck.setExpectedValue("378");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Measure-Sum");
        queryCheck1.setDescription("Verify MDX query returns Measure data for theMeasure");
        queryCheck1.setQuery("SELECT FROM [HasAll Cube] WHERE ([Measures].[theMeasure])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

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
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Dimension Intro catalog check");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Dimension Intro catalog check");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Dimension Intro' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Cube Calculated Member Property");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Hierarchy Has All");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Hierarchy Has All tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Hierarchy Has All");
        suite.setDescription("Check suite for the Daanse Tutorial - Hierarchy Has All");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
