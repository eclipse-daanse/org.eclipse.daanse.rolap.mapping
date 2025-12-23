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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.identifier;

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
 * Provides a check suite for the member identifier tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, levels with various member identifier formats, and measure exist and are accessible.
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

        // Create level checks
        LevelCheck level1Check = factory.createLevelCheck();
        level1Check.setName("LevelCheck-level1");
        level1Check.setDescription("Check that level 'level1' exists");
        level1Check.setLevelName("level1");

        LevelCheck level2Check = factory.createLevelCheck();
        level2Check.setName("LevelCheck-level2");
        level2Check.setDescription("Check that level 'level2' exists");
        level2Check.setLevelName("level2");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-Hierarchy");
        hierarchyCheck.setDescription("Check that hierarchy 'Hierarchy' exists with two levels");
        hierarchyCheck.setHierarchyName("Hierarchy");
        hierarchyCheck.getLevelChecks().add(level1Check);
        hierarchyCheck.getLevelChecks().add(level2Check);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-Dimension");
        dimensionCheck.setDescription("Check that dimension 'Dimension' exists with hierarchy");
        dimensionCheck.setDimensionName("Dimension");
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
        queryCheckCellValueCheck.setName("[Measures].[theMeasure]");
        queryCheckCellValueCheck.setExpectedValue("0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns theMeasure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[theMeasure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for Fact table
        DatabaseColumnAttributeCheck columnKey1TypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKey1TypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKey1TypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey1 = factory.createDatabaseColumnCheck();
        columnCheckKey1.setName("Database Column Check KEY1");
        columnCheckKey1.setColumnName("KEY1");
        columnCheckKey1.getColumnAttributeChecks().add(columnKey1TypeCheck);

        DatabaseColumnAttributeCheck columnKey2TypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnKey2TypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnKey2TypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey2 = factory.createDatabaseColumnCheck();
        columnCheckKey2.setName("Database Column Check KEY2");
        columnCheckKey2.setColumnName("KEY2");
        columnCheckKey2.getColumnAttributeChecks().add(columnKey2TypeCheck);

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
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey1);
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey2);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Member Identifier");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Member Identifier");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Member Identifier' exists with member identifier configurations");
        catalogCheck.setCatalogName("Daanse Tutorial - Member Identifier");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Member Identifier");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Member Identifier");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Member Identifier");
        suite.setDescription("Check suite for the Daanse Tutorial - Member Identifier with various identifier formats");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
