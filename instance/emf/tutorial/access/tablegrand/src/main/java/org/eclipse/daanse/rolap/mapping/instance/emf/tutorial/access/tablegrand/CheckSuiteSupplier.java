/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.tablegrand;
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
        catalogCheckRoleAll.setName("Daanse Tutorial - Access Table Grant Catalog Check for RoleAll");
        catalogCheckRoleAll.setDescription("Demonstrates access control with table grants and roles");
        catalogCheckRoleAll.setCatalogName("Daanse Tutorial - Access Table Grant");
        catalogCheckRoleAll.setEnabled(true);
        // Add database schema check with detailed column checks
        // Add cube check
        catalogCheckRoleAll.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog level
        catalogCheckRoleAll.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRoleAll()
        ));

        CatalogCheck catalogCheckRoleNone = FACTORY.createCatalogCheck();
        catalogCheckRoleNone.setName("Daanse Tutorial - Access Table Grant Catalog Check for RoleNone");
        catalogCheckRoleNone.setDescription("Demonstrates access control with table grants and roles");
        catalogCheckRoleNone.setCatalogName("Daanse Tutorial - Access Table Grant");
        catalogCheckRoleNone.setEnabled(true);
        // Add database schema check with detailed column checks
        // Add cube check
        catalogCheckRoleNone.getCubeChecks().add(createCubeCheck());

        ConnectionConfig roleAllConnectionConfig = FACTORY.createConnectionConfig();
        roleAllConnectionConfig.setCatalogName("Daanse Tutorial - Access Table Gran for roleAll");
        roleAllConnectionConfig.getRoles().add("roleAll");

        ConnectionConfig roleNoneConnectionConfig = FACTORY.createConnectionConfig();
        roleNoneConnectionConfig.setCatalogName("Daanse Tutorial - Access Table Gran for roleNone");
        roleNoneConnectionConfig.getRoles().add("roleNone");

        OlapConnectionCheck connectionCheckRoleAll = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleAll.setName("Connection Check Table Gran with roleAll");
        connectionCheckRoleAll.setDescription("Connection check for Catalog Gran tutorial with roleAll");
        connectionCheckRoleAll.setConnectionConfig(roleAllConnectionConfig);
        connectionCheckRoleAll.getCatalogChecks().add(catalogCheckRoleAll);

        OlapConnectionCheck connectionCheckRoleNone = FACTORY.createOlapConnectionCheck();
        connectionCheckRoleNone.setName("Connection Check Table Gran with roleNone");
        connectionCheckRoleNone.setDescription("Connection check for Table Gran tutorial with roleNone");
        connectionCheckRoleNone.setConnectionConfig(roleNoneConnectionConfig);
        connectionCheckRoleNone.getCatalogChecks().add(catalogCheckRoleNone);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Table Grant Catalog Checks");
        suite.setDescription("Comprehensive checks for Daanse Tutorial - Access Table Grant catalog - logistics and package delivery analysis");
        suite.getConnectionChecks().addAll(List.of(connectionCheckRoleAll, connectionCheckRoleNone));
        return suite;
    }

    private CubeCheck createCubeCheck() {
        CubeCheck cubeCheck = FACTORY.createCubeCheck();
        cubeCheck.setName("Daanse Tutorial - Access Table Grant Cube Check");
        cubeCheck.setDescription("Verify Daanse Tutorial - Access Table Grant cube structure with all dimensions and measures");
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
        //TODO
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

        CellValueCheck queryCheckCellValueCheck0 = FACTORY.createCellValueCheck();
        queryCheckCellValueCheck0.setName("[Measures].[Measure1]");
        queryCheckCellValueCheck0.setExpectedValue("84.0");
        queryCheck.getCellChecks().add(queryCheckCellValueCheck0);

        queryCheck.setEnabled(true);
        return queryCheck;
    }

}
