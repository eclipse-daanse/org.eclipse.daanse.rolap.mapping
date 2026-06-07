/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.databaseschemagrand;
import java.util.List;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.ConnectionConfig;
import org.eclipse.daanse.olap.check.model.check.CubeAttribute;
import org.eclipse.daanse.olap.check.model.check.CubeAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
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
        catalogCheckRoleAll.setName("Catalog Check");
        catalogCheckRoleAll.setDescription("Demonstrates access control with database grants and roles");
        catalogCheckRoleAll.setCatalogName("Daanse Tutorial - Access Database Schema Grant");
        catalogCheckRoleAll.setEnabled(true);
        // Add database schema check with detailed column checks
        // Add cube check
        catalogCheckRoleAll.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog
        catalogCheckRoleAll.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleAll()
        ));

        CatalogCheck catalogCheckRoleNone = FACTORY.createCatalogCheck();
        catalogCheckRoleNone.setName("Catalog Check");
        catalogCheckRoleNone.setDescription("Demonstrates access control with database grants and roles");
        catalogCheckRoleNone.setCatalogName("Daanse Tutorial - Access Database Schema Grant");
        catalogCheckRoleNone.setEnabled(true);
        // Add database schema check with detailed column checks
        //TODO add check that database absent
        // Add cube check
        catalogCheckRoleNone.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog
        catalogCheckRoleNone.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleNone()
        ));

        ConnectionConfig roleAllConnectionConfig = FACTORY.createConnectionConfig();
        roleAllConnectionConfig.setCatalogName("Daanse Tutorial - Access Database Schema Gran");
        roleAllConnectionConfig.getRoles().add("roleAll");

        ConnectionConfig roleNoneConnectionConfig = FACTORY.createConnectionConfig();
        roleNoneConnectionConfig.setCatalogName("Daanse Tutorial - Access Database Schema Gran");
        roleNoneConnectionConfig.getRoles().add("roleNone");

        OlapConnectionCheck connectionCheckRoleAll = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleAll.setName("Connection Check Database Schema Gran with roleAll");
        connectionCheckRoleAll.setDescription("Connection check for Database Schema Gran tutorial with roleAll");
        connectionCheckRoleAll.setConnectionConfig(roleAllConnectionConfig);
        connectionCheckRoleAll.getCatalogChecks().add(catalogCheckRoleAll);

        OlapConnectionCheck connectionCheckRoleNone = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleNone.setName("Connection Check Database Schema Gran Check with roleNone");
        connectionCheckRoleNone.setDescription("Connection check for Database Schema Gran tutorial with roleNone");
        connectionCheckRoleNone.setConnectionConfig(roleNoneConnectionConfig);
        connectionCheckRoleNone.getCatalogChecks().add(catalogCheckRoleNone);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Database Schema Grant Checks");
        suite.setDescription("Comprehensive checks for Daanse Tutorial - Access Database Schema Grant catalog - logistics and package delivery analysis");
        suite.getConnectionChecks().addAll(List.of(connectionCheckRoleAll, connectionCheckRoleNone));
        return suite;
    }

    private CubeCheck createCubeCheck() {
        CubeCheck cubeCheck = FACTORY.createCubeCheck();
        cubeCheck.setName("Access Database Schema Grant Cube Check");
        cubeCheck.setDescription("Verify Access Database Schema Grant cube structure with all dimensions and measures");
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

        MeasureAttributeCheck uniqueNameCheck = FACTORY.createMeasureAttributeCheck();
        uniqueNameCheck.setName(measureName + " Unique Name Check");
        uniqueNameCheck.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        uniqueNameCheck.setExpectedValue("[Measures].[" + measureName + "]");
        measureCheck.getMeasureAttributeChecks().add(uniqueNameCheck);

        MeasureAttributeCheck nameCheck = FACTORY.createMeasureAttributeCheck();
        nameCheck.setName(measureName + " Name Check");
        nameCheck.setAttributeType(MeasureAttribute.NAME);
        nameCheck.setExpectedValue(measureName);
        measureCheck.getMeasureAttributeChecks().add(nameCheck);

        //TODO
        //MeasureAttributeCheck aggregatorCheck = FACTORY.createMeasureAttributeCheck();
        //aggregatorCheck.setName(measureName + " Aggregator Check");
        //aggregatorCheck.setAttributeType(MeasureAttribute.AGGREGATOR);
        //aggregatorCheck.setExpectedValue(expectedAggregator);
        //aggregatorCheck.setMatchMode(MatchMode.EQUALS);
        //aggregatorCheck.setCaseSensitive(false);
        //measureCheck.getMeasureAttributeChecks().add(aggregatorCheck);
        return measureCheck;
    }
    private QueryCheck createQueryCheckForRoleAll() {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.setEnabled(true);

        CellValueCheck queryCheck1CellValueCheck = FACTORY.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure1]");
        queryCheck1CellValueCheck.setExpectedValue("84.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(84);
        queryCheck.getCellChecks().add(queryCheck1CellValueCheck);
        return queryCheck;
    }

    private QueryCheck createQueryCheckForRoleNone() {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.setEnabled(true);

        CellValueCheck queryCheck1CellValueCheck = FACTORY.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure1]");
        queryCheck1CellValueCheck.setExpectedValue("84.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(84);
        queryCheck.getCellChecks().add(queryCheck1CellValueCheck);

        return queryCheck;
    }
}
