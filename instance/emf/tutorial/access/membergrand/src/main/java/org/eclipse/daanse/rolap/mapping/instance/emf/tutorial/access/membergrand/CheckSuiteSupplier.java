/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.membergrand;
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
        //ConnectionConfig connectionConfig = FACTORY.createConnectionConfig();
        //connectionConfig.setCatalogName("Daanse Tutorial - Access Catalog Gran");
        //connectionConfig.getRoles().add("role1");
        //model.setConnectionConfig(connectionConfig);
        // Create catalog check
        CatalogCheck catalogCheck = FACTORY.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Access Member Grant Catalog Check");
        catalogCheck.setDescription("Demonstrates access control with member grants and roles");
        catalogCheck.setCatalogName("Daanse Tutorial - Access Member Grant");
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
        role1ConnectionConfig.setCatalogName("Daanse Tutorial - Access Member Gran");
        role1ConnectionConfig.getRoles().add("role1");

        OlapConnectionCheck connectionCheckRole1 = FACTORY.createOlapConnectionCheck();
        connectionCheckRole1.setName("Member Gran Check");
        connectionCheckRole1.setDescription("Connection check for Member Gran tutorial with role1");
        connectionCheckRole1.setConnectionConfig(role1ConnectionConfig);
        connectionCheckRole1.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Member Grant Catalog Checks");
        suite.setDescription("Comprehensive checks for Daanse Tutorial - Access Member Grant catalog - logistics and package delivery analysis");
        suite.getConnectionChecks().add(connectionCheckRole1);
        return suite;
    }

    private DatabaseSchemaCheck createDatabaseSchemaCheck() {
        DatabaseSchemaCheck schemaCheck = FACTORY.createDatabaseSchemaCheck();
        schemaCheck.setName("Daanse Tutorial - Access Member Grant Database Schema Check");
        schemaCheck.setDescription("Verify database tables and columns exist for Daanse Tutorial - Access Member Grant");
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
        cubeCheck.setName("Daanse Tutorial - Access Member Grant Cube Check");
        cubeCheck.setDescription("Verify Daanse Tutorial - Access Member Grant cube structure with all dimensions and measures");
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

        HierarchyAttributeCheck hasAllCheck = FACTORY.createHierarchyAttributeCheck();
        hasAllCheck.setName("Hierarchy HasAll Check");
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedBoolean(true);
        hierarchyCheck.getHierarchyAttributeChecks().add(hasAllCheck);

        LevelCheck levelCheck = FACTORY.createLevelCheck();
        levelCheck.setName("Level1 Level Check");
        levelCheck.setLevelName("Level1");
        levelCheck.setDescription("Verify level Level1 exists");
        levelCheck.setEnabled(true);

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
