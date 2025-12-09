/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.hierarchygrand;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
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
        CatalogCheck catalogCheck = FACTORY.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Access Hierarchy Grant Catalog Check");
        catalogCheck.setDescription("Demonstrates access control with hierarchy grants and roles");
        catalogCheck.setCatalogName("Daanse Tutorial - Access Hierarchy Grant");
        catalogCheck.setEnabled(true);
        // Add database schema check with detailed column checks
        catalogCheck.getDatabaseSchemaChecks().add(createDatabaseSchemaCheck());
        // Add cube check
        catalogCheck.getCubeChecks().add(createCubeCheck());
        // Add query checks at catalog level
        catalogCheck.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRole1()
        ));

        ConnectionConfig role1ConnectionConfig = FACTORY.createConnectionConfig();
        role1ConnectionConfig.setCatalogName("Daanse Tutorial - Access Hierarchy Gran");
        role1ConnectionConfig.getRoles().add("role1");

        OlapConnectionCheck connectionCheckRole1 = FACTORY.createOlapConnectionCheck();
        connectionCheckRole1.setName("Hierarchy Gran Check");
        connectionCheckRole1.setDescription("Connection check for Hierarchy Gran tutorial with role1");
        connectionCheckRole1.setConnectionConfig(role1ConnectionConfig);
        connectionCheckRole1.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Hierarchy Grant Catalog Checks");
        suite.setDescription("Comprehensive checks for Daanse Tutorial - Access Hierarchy Grant catalog - logistics and package delivery analysis");
        suite.getConnectionChecks().add(connectionCheckRole1);
        return suite;
    }

    private DatabaseSchemaCheck createDatabaseSchemaCheck() {
        DatabaseSchemaCheck schemaCheck = FACTORY.createDatabaseSchemaCheck();
        schemaCheck.setName("Daanse Tutorial - Access Hierarchy Grant Database Schema Check");
        schemaCheck.setDescription("Verify database tables and columns exist for Daanse Tutorial - Access Hierarchy Grant");
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
        cubeCheck.setName("Daanse Tutorial - Access Hierarchy Grant Cube Check");
        cubeCheck.setDescription("Verify Daanse Tutorial - Access Hierarchy Grant cube structure with all dimensions and measures");
        cubeCheck.setCubeName("Cube1");
        cubeCheck.setEnabled(true);
        // Add cube attribute checks
        CubeAttributeCheck visibleCheck = FACTORY.createCubeAttributeCheck();
        visibleCheck.setName("Cube Visibility Check");
        visibleCheck.setAttributeType(CubeAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);

        cubeCheck.getCubeAttributeChecks().add(visibleCheck);
        // Add dimension checks
        cubeCheck.getDimensionChecks().add(createDimension1Check());
        // Add measure checks
        cubeCheck.getMeasureChecks().add(createMeasureCheck("Measure1", "sum"));
        return cubeCheck;
    }

    private DimensionCheck createDimension1Check() {
        DimensionCheck dimCheck = FACTORY.createDimensionCheck();
        dimCheck.setName("Dimension1 Dimension Check");
        dimCheck.setDescription("Dimension1 Dimension Check");
        dimCheck.setDimensionName("Dimension1");
        dimCheck.setEnabled(true);

        DimensionAttributeCheck visibleCheck = FACTORY.createDimensionAttributeCheck();
        visibleCheck.setName("Dimension1 Visible Check");
        visibleCheck.setAttributeType(DimensionAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);
        dimCheck.getDimensionAttributeChecks().add(visibleCheck);

        HierarchyCheck hierarchy1Check = FACTORY.createHierarchyCheck();
        hierarchy1Check.setName("Hierarchy1 Hierarchy Check");
        hierarchy1Check.setHierarchyName("Hierarchy1");
        hierarchy1Check.setEnabled(true);

        HierarchyAttributeCheck hierarchy1HasAllCheck = FACTORY.createHierarchyAttributeCheck();
        hierarchy1HasAllCheck.setName("Hierarchy1 HasAll Check");
        hierarchy1HasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchy1HasAllCheck.setExpectedBoolean(false);
        hierarchy1Check.getHierarchyAttributeChecks().add(hierarchy1HasAllCheck);

        LevelCheck level1Check = FACTORY.createLevelCheck();
        level1Check.setName("Level1 Level Check");
        level1Check.setLevelName("Level1");
        level1Check.setDescription("Verify level Level1 exists");
        level1Check.setEnabled(true);

        hierarchy1Check.getLevelChecks().add(level1Check);

        HierarchyCheck hierarchy2Check = FACTORY.createHierarchyCheck();
        hierarchy1Check.setName("Hierarchy2 Hierarchy Check");
        hierarchy1Check.setHierarchyName("Hierarchy2");
        hierarchy1Check.setEnabled(true);

        HierarchyAttributeCheck hierarchy2HasAllCheck = FACTORY.createHierarchyAttributeCheck();
        hierarchy2HasAllCheck.setName("Hierarchy2 HasAll Check");
        hierarchy2HasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchy2HasAllCheck.setExpectedBoolean(false);
        hierarchy2Check.getHierarchyAttributeChecks().add(hierarchy1HasAllCheck);

        LevelCheck level2Check = FACTORY.createLevelCheck();
        level2Check.setName("Level2 Level Check");
        level2Check.setLevelName("Level2");
        level2Check.setDescription("Verify level Level2 exists");
        level2Check.setEnabled(true);

        hierarchy1Check.getLevelChecks().add(level2Check);

        dimCheck.getHierarchyChecks().add(hierarchy1Check);
        dimCheck.getHierarchyChecks().add(hierarchy2Check);

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
        MeasureAttributeCheck aggregatorCheck = FACTORY.createMeasureAttributeCheck();
        aggregatorCheck.setName(measureName + " Aggregator Check");
        aggregatorCheck.setAttributeType(MeasureAttribute.AGGREGATOR);
        aggregatorCheck.setExpectedValue(expectedAggregator);
        aggregatorCheck.setMatchMode(MatchMode.EQUALS);
        aggregatorCheck.setCaseSensitive(false);
        measureCheck.getMeasureAttributeChecks().add(aggregatorCheck);
        return measureCheck;
    }
    private QueryCheck createQueryCheckForRole1() {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT NON EMPTY Hierarchize(AddCalculatedMembers({DrilldownLevel({[Dimension1].[Hierarchy1].[All Hierarchy1s]})})) DIMENSION PROPERTIES PARENT_UNIQUE_NAME ON COLUMNS  FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.setEnabled(true);
        return queryCheck;
    }

}
