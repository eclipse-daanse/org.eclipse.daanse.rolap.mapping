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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.columngrand;
 
import java.util.List;

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

@Component(service = CheckSuiteSupplier.class)
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
        catalogCheck.setName("Daanse Tutorial - Access Column Grant Check");
        catalogCheck.setDescription("Demonstrates access control with column grant and roles");
        catalogCheck.setCatalogName("Daanse Tutorial - Access Column Grant");
        catalogCheck.setEnabled(true);
        // Add database schema check with detailed column checks
        catalogCheck.getDatabaseSchemaChecks().add(createDatabaseSchemaCheck());
        // Add cube check
        catalogCheck.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog level
        catalogCheck.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleAll()
            //createQueryCheckForRoleNone()
        ));

        ConnectionConfig roleAllConnectionConfig = FACTORY.createConnectionConfig();
        roleAllConnectionConfig.setCatalogName("Daanse Tutorial - Access Column Gran");
        roleAllConnectionConfig.getRoles().add("roleAll");

        ConnectionConfig roleNoneConnectionConfig = FACTORY.createConnectionConfig();
        roleNoneConnectionConfig.setCatalogName("Daanse Tutorial - Access Column Gran");
        roleNoneConnectionConfig.getRoles().add("roleNone");

        OlapConnectionCheck connectionCheckRoleAll = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleAll.setName("Connection Check Column Gran with roleAll");
        connectionCheckRoleAll.setDescription("Connection check for Catalog Gran tutorial with roleAll");
        connectionCheckRoleAll.setConnectionConfig(roleAllConnectionConfig);
        connectionCheckRoleAll.getCatalogChecks().add(catalogCheck);

        OlapConnectionCheck connectionCheckRoleNone = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleNone.setName("Column Gran Check");
        connectionCheckRoleNone.setDescription("Connection check for Column Gran tutorial with roleNone");
        connectionCheckRoleNone.setConnectionConfig(roleNoneConnectionConfig);
        connectionCheckRoleNone.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Column Grant Checks");
        suite.setDescription("Comprehensive checks for Daanse Tutorial - Access Column Grant catalog");
        suite.getConnectionChecks().addAll(List.of(connectionCheckRoleAll, connectionCheckRoleNone));
        return suite;
    }

    private DatabaseSchemaCheck createDatabaseSchemaCheck() {
        DatabaseSchemaCheck schemaCheck = FACTORY.createDatabaseSchemaCheck();
        schemaCheck.setName("Daanse Tutorial - Access Column Grand Database Schema Check");
        schemaCheck.setDescription("Verify database tables and columns exist for Daanse Tutorial - Access Column Grand");
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
        cubeCheck.setName("Access Catalog Gran Cube Check");
        cubeCheck.setDescription("Verify Access Catalog Gran cube structure with all dimensions and measures");
        cubeCheck.setCubeName("Cube1");
        cubeCheck.setEnabled(true);
        // Add cube attribute checks
        CubeAttributeCheck visibleCheck = FACTORY.createCubeAttributeCheck();
        visibleCheck.setName("Cube Visibility Check");
        visibleCheck.setAttributeType(CubeAttribute.VISIBLE);
        visibleCheck.setExpectedBoolean(true);
        
        cubeCheck.getCubeAttributeChecks().add(visibleCheck);
        // Add measure checks
        cubeCheck.getMeasureChecks().add(createMeasureCheck("Measure1", "sum"));
        return cubeCheck;
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
    private QueryCheck createQueryCheckForRoleAll() {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.setEnabled(true);
        return queryCheck;
    }

}
