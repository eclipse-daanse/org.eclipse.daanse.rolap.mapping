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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.link;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelAttribute;
import org.eclipse.daanse.olap.check.model.check.LevelAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the parent-child hierarchy with link tutorial.
 * Checks that the catalog, cube, measure, parent-child hierarchy, and closure table exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Parent Child Link";
    private static final String CUBE_NAME = "Cube";
    private static final String MEASURE_NAME = "Value";
    private static final String DIMENSION_NAME = "Dimension";
    private static final String HIERARCHY_NAME = "Hierarchy";
    private static final String LEVEL_NAME = "Name";

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + MEASURE_NAME);
        measureCheck.setDescription("Check that measure '" + MEASURE_NAME + "' exists");
        measureCheck.setMeasureName(MEASURE_NAME);

        // Create level check with unique members attribute
        LevelAttributeCheck levelUniqueCheck = factory.createLevelAttributeCheck();
        levelUniqueCheck.setAttributeType(LevelAttribute.IS_UNIQUE);
        levelUniqueCheck.setExpectedBoolean(true);

        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck for " + LEVEL_NAME);
        levelCheck.setLevelName(LEVEL_NAME);
        levelCheck.getLevelAttributeChecks().add(levelUniqueCheck);

        // Create hierarchy check for parent-child hierarchy
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for " + HIERARCHY_NAME);
        hierarchyCheck.setHierarchyName(HIERARCHY_NAME);
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + DIMENSION_NAME);
        dimensionCheck.setDimensionName(DIMENSION_NAME);
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists with parent-child hierarchy");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnNameTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckName = factory.createDatabaseColumnCheck();
        columnCheckName.setName("Database Column Check NAME");
        columnCheckName.setColumnName("NAME");
        columnCheckName.getColumnAttributeChecks().add(columnNameTypeCheck);

        DatabaseColumnAttributeCheck columnParentTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnParentTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnParentTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckParent = factory.createDatabaseColumnCheck();
        columnCheckParent.setName("Database Column Check PARENT");
        columnCheckParent.setColumnName("PARENT");
        columnCheckParent.getColumnAttributeChecks().add(columnParentTypeCheck);

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
        databaseTableCheckFact.getColumnChecks().add(columnCheckName);
        databaseTableCheckFact.getColumnChecks().add(columnCheckParent);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create database column checks for Closure table
        DatabaseColumnAttributeCheck columnClosureNameTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnClosureNameTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnClosureNameTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckClosureName = factory.createDatabaseColumnCheck();
        columnCheckClosureName.setName("Database Column Check Closure NAME");
        columnCheckClosureName.setColumnName("NAME");
        columnCheckClosureName.getColumnAttributeChecks().add(columnClosureNameTypeCheck);

        DatabaseColumnAttributeCheck columnClosureParentTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnClosureParentTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnClosureParentTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckClosureParent = factory.createDatabaseColumnCheck();
        columnCheckClosureParent.setName("Database Column Check Closure PARENT");
        columnCheckClosureParent.setColumnName("PARENT");
        columnCheckClosureParent.getColumnAttributeChecks().add(columnClosureParentTypeCheck);

        DatabaseColumnAttributeCheck columnDistanceTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnDistanceTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnDistanceTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckDistance = factory.createDatabaseColumnCheck();
        columnCheckDistance.setName("Database Column Check DISTANCE");
        columnCheckDistance.setColumnName("DISTANCE");
        columnCheckDistance.getColumnAttributeChecks().add(columnDistanceTypeCheck);

        DatabaseTableCheck databaseTableCheckClosure = factory.createDatabaseTableCheck();
        databaseTableCheckClosure.setName("Database Table Closure Check");
        databaseTableCheckClosure.setTableName("Closure");
        databaseTableCheckClosure.getColumnChecks().add(columnCheckClosureName);
        databaseTableCheckClosure.getColumnChecks().add(columnCheckClosureParent);
        databaseTableCheckClosure.getColumnChecks().add(columnCheckDistance);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckClosure);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with parent-child hierarchy");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for parent-child hierarchy with link tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Parent Child Link Suite");
        suite.setDescription("Check suite for the parent-child hierarchy with link mapping tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
