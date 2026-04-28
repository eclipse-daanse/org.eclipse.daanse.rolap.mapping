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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.cataloggrand;
import java.util.List;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.ConnectionConfig;
import org.eclipse.daanse.olap.check.model.check.CubeAttribute;
import org.eclipse.daanse.olap.check.model.check.CubeAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionAttribute;
import org.eclipse.daanse.olap.check.model.check.DimensionAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MatchMode;
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

@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {
    private static final OlapCheckFactory FACTORY = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create catalog check
        CatalogCheck catalogCheckRoleAll = FACTORY.createCatalogCheck();
        catalogCheckRoleAll.setName("Catalog Check Role All");
        catalogCheckRoleAll.setDescription("Demonstrates access control with catalog grants and roles");
        catalogCheckRoleAll.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        catalogCheckRoleAll.setEnabled(true);
        // Add database schema check with detailed column checks
        catalogCheckRoleAll.getDatabaseSchemaChecks().add(createDatabaseSchemaCheck());
        // Add cube check
        catalogCheckRoleAll.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog level
        catalogCheckRoleAll.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleAll()
        ));

        CatalogCheck catalogCheckRoleAllDimWithCubeGrand = FACTORY.createCatalogCheck();
        catalogCheckRoleAllDimWithCubeGrand.setName("Catalog Check Role All Dim With CubeGrand");
        catalogCheckRoleAllDimWithCubeGrand.setDescription("Demonstrates access control with catalog grants and roles");
        catalogCheckRoleAllDimWithCubeGrand.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        catalogCheckRoleAllDimWithCubeGrand.setEnabled(true);
        // Add database schema check with detailed column checks
        catalogCheckRoleAllDimWithCubeGrand.getDatabaseSchemaChecks().add(createDatabaseSchemaCheck());
        // Add cube check
        catalogCheckRoleAllDimWithCubeGrand.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog level
        catalogCheckRoleAllDimWithCubeGrand.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleAllDimWithCubeGrand()
        ));

        CatalogCheck catalogCheckRoleNone = FACTORY.createCatalogCheck();
        catalogCheckRoleNone.setName("Catalog Check Role None");
        catalogCheckRoleNone.setDescription("Demonstrates access control with catalog grants and roles");
        catalogCheckRoleNone.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        catalogCheckRoleNone.setEnabled(true);

        ConnectionConfig roleAllConnectionConfig = FACTORY.createConnectionConfig();
        roleAllConnectionConfig.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        roleAllConnectionConfig.getRoles().add("roleAll");

        ConnectionConfig roleAllDimWithCubeGrandConnectionConfig = FACTORY.createConnectionConfig();
        roleAllDimWithCubeGrandConnectionConfig.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        roleAllDimWithCubeGrandConnectionConfig.getRoles().add("roleAllDimWithCubeGrand");

        ConnectionConfig roleNoneConnectionConfig = FACTORY.createConnectionConfig();
        roleNoneConnectionConfig.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        roleNoneConnectionConfig.getRoles().add("roleNone");

        OlapConnectionCheck connectionCheckRoleAll = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleAll.setName("Connection Check Catalog Gran with roleAll");
        connectionCheckRoleAll.setDescription("Connection check for Catalog Gran tutorial with roleAll");
        connectionCheckRoleAll.setConnectionConfig(roleAllConnectionConfig);
        connectionCheckRoleAll.getCatalogChecks().add(catalogCheckRoleAll);

        OlapConnectionCheck connectionCheckRoleAllDimWithCubeGrand = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleAllDimWithCubeGrand.setName("Connection Check Catalog Gran with RoleAllDimWithCubeGrand");
        connectionCheckRoleAllDimWithCubeGrand.setDescription("Connection check for Catalog Gran tutorial with roleAllDimWithCubeGrand");
        connectionCheckRoleAllDimWithCubeGrand.setConnectionConfig(roleAllDimWithCubeGrandConnectionConfig);
        connectionCheckRoleAllDimWithCubeGrand.getCatalogChecks().add(catalogCheckRoleAllDimWithCubeGrand);

        OlapConnectionCheck connectionCheckRoleNone = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleNone.setName("Catalog Gran Check");
        connectionCheckRoleNone.setDescription("Connection check for Catalog Gran tutorial with roleNone");
        connectionCheckRoleNone.setConnectionConfig(roleNoneConnectionConfig);
        connectionCheckRoleNone.getCatalogChecks().add(catalogCheckRoleNone);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Access Catalog Gran Suite");
        suite.setDescription("Check suite for Access Catalog Gran catalog");
        suite.getConnectionChecks().addAll(List.of(connectionCheckRoleAll, connectionCheckRoleAllDimWithCubeGrand, connectionCheckRoleNone));
        return suite;
    }

    private DatabaseSchemaCheck createDatabaseSchemaCheck() {
        DatabaseSchemaCheck schemaCheck = FACTORY.createDatabaseSchemaCheck();
        schemaCheck.setName("Daanse Tutorial - Access Catalog Grand Database Schema Check");
        schemaCheck.setDescription("Verify database tables and columns exist for Daanse Tutorial - Access Catalog Grand");
        schemaCheck.setEnabled(true);
        // Check parcels fact table with columns
        DatabaseTableCheck factTableCheck = FACTORY.createDatabaseTableCheck();
        factTableCheck.setTableName("Fact");
        factTableCheck.setEnabled(true);
        // Add column checks for Fact table
        factTableCheck.getColumnChecks().add(createColumnCheck("KEY", "VARCHAR"));
        factTableCheck.getColumnChecks().add(createColumnCheck("VALUE", "INTEGER"));
        schemaCheck.getTableChecks().add(factTableCheck);
        return schemaCheck;
    }
    private DatabaseColumnCheck createColumnCheck(String columnName, String type) {
        DatabaseColumnCheck columnCheck = FACTORY.createDatabaseColumnCheck();
        columnCheck.setName(columnName + " Column Check");
        columnCheck.setColumnName(columnName);
        DatabaseColumnAttributeCheck typeCheck = FACTORY.createDatabaseColumnAttributeCheck();
        typeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        typeCheck.setExpectedValue(type);
        columnCheck.getColumnAttributeChecks().add(typeCheck);
        columnCheck.setEnabled(true);
        return columnCheck;
    }
    private CubeCheck createCubeCheck() {
        CubeCheck cubeCheck = FACTORY.createCubeCheck();
        cubeCheck.setName("Access Catalog Grant Cube Check");
        cubeCheck.setDescription("Verify Access Catalog Gran cube structure with all dimensions and measures");
        cubeCheck.setCubeName("Cube1");
        cubeCheck.setEnabled(true);
        // Add cube attribute checks
        CubeAttributeCheck visibleCheck = FACTORY.createCubeAttributeCheck();
        visibleCheck.setName("Cube Visibility Check");
        visibleCheck.setAttributeType(CubeAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);

        cubeCheck.getCubeAttributeChecks().add(visibleCheck);
        // Add dimension checks
        cubeCheck.getDimensionChecks().add(createDimensionCheck("Dimension1", null));
        // Add measure checks
        cubeCheck.getMeasureChecks().add(createMeasureCheck("Measure1", "sum"));
        return cubeCheck;
    }
    private DimensionCheck createDimensionCheck(String dimensionName, String description) {
        DimensionCheck dimCheck = FACTORY.createDimensionCheck();
        dimCheck.setName(dimensionName + " Dimension Check");
        dimCheck.setDescription(description);
        dimCheck.setDimensionName(dimensionName);
        dimCheck.setEnabled(true);

        DimensionAttributeCheck visibleCheck = FACTORY.createDimensionAttributeCheck();
        visibleCheck.setName(dimensionName + " Visible Check");
        visibleCheck.setAttributeType(DimensionAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);
        dimCheck.getDimensionAttributeChecks().add(visibleCheck);

        HierarchyCheck hierarchyCheck = FACTORY.createHierarchyCheck();
        hierarchyCheck.setName("Hierarchy1 Hierarchy Check");
        hierarchyCheck.setEnabled(true);
        hierarchyCheck.setHierarchyName("Hierarchy1");

        HierarchyAttributeCheck hasAllCheck = FACTORY.createHierarchyAttributeCheck();
        hasAllCheck.setName("Hierarchy1 HasAll Check");
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedBoolean(false);
        hierarchyCheck.getHierarchyAttributeChecks().add(hasAllCheck);

        LevelCheck levelCheck = FACTORY.createLevelCheck();
        levelCheck.setName("Level1 Level Check");
        levelCheck.setLevelName("Level1");
        levelCheck.setDescription("Verify level Level1 exists");
        levelCheck.setEnabled(true);

        //LevelAttributeCheck levelAttributeCheck = FACTORY.createLevelAttributeCheck();
        //TODO add column type
        //levelCheck.getLevelAttributeChecks().add(levelAttributeCheck);

        hierarchyCheck.getLevelChecks().add(levelCheck);
        dimCheck.getHierarchyChecks().add(hierarchyCheck);

        return dimCheck;
    }

    private MeasureCheck createMeasureCheck(String measureName, String expectedAggregator) {
        MeasureCheck measureCheck = FACTORY.createMeasureCheck();
        measureCheck.setName(measureName + " Measure Check");
        measureCheck.setMeasureName(measureName);
        measureCheck.setEnabled(true);
        MeasureAttributeCheck visibleCheck = FACTORY.createMeasureAttributeCheck();
        visibleCheck.setName(measureName + " Visible Check");
        visibleCheck.setAttributeType(MeasureAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);
        measureCheck.getMeasureAttributeChecks().add(visibleCheck);

        MeasureAttributeCheck uniqueNameCheck = FACTORY.createMeasureAttributeCheck();
        uniqueNameCheck.setName(measureName + " Unique Name");
        uniqueNameCheck.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        uniqueNameCheck.setExpectedValue("[Measures].[" + measureName + "]");
        measureCheck.getMeasureAttributeChecks().add(uniqueNameCheck);

        MeasureAttributeCheck aggregatorCheck = FACTORY.createMeasureAttributeCheck();
        aggregatorCheck.setName(measureName + " Aggregator Check");
        aggregatorCheck.setAttributeType(MeasureAttribute.AGGREGATOR);
        aggregatorCheck.setExpectedValue(expectedAggregator);
        aggregatorCheck.setMatchMode(MatchMode.EQUALS);
        aggregatorCheck.setCaseSensitive(false);
        //TODO fix aggregates check executer
        //measureCheck.getMeasureAttributeChecks().add(aggregatorCheck);
        return measureCheck;
    }
    private QueryCheck createQueryCheckForRoleAll() {
        QueryCheck queryCheck = createQueryCheck("RoleAll");
        CellValueCheck queryCheckCellValueCheck = FACTORY.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1] RoleAll");
        queryCheckCellValueCheck.setExpectedValue("42.0");
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        return queryCheck;
    }

    private QueryCheck createQueryCheckForRoleAllDimWithCubeGrand() {
        QueryCheck queryCheck = createQueryCheck("RoleAllDimWithCubeGrand");
        CellValueCheck queryCheckCellValueCheck = FACTORY.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1] RoleAllDimWithCubeGrand");
        queryCheckCellValueCheck.setExpectedValue("42.0");
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        return queryCheck;
    }

    private QueryCheck createQueryCheck(String name) {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check for " + name);
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.setEnabled(true);
        return queryCheck;
    }

}
