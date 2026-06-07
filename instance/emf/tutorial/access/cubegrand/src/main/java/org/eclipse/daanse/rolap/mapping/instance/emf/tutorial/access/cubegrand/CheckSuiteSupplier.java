/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.cubegrand;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.ConnectionConfig;
import org.eclipse.daanse.olap.check.model.check.CubeAttribute;
import org.eclipse.daanse.olap.check.model.check.CubeAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
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
        catalogCheck.setName("Parcel Catalog Check");
        catalogCheck.setDescription("Demonstrates access control with cube grants and roles");
        catalogCheck.setCatalogName("Daanse Tutorial - Access Cube Grant");
        catalogCheck.setEnabled(true);
        // Add database schema check with detailed column checks
        // Add cube check
        catalogCheck.getCubeChecks().add(createCubeCheck());

        // Add query checks at catalog level
        catalogCheck.getQueryChecks().addAll(java.util.List.of(
            createQueryCheckForRole1()
        ));

        ConnectionConfig role1ConnectionConfig = FACTORY.createConnectionConfig();
        role1ConnectionConfig.setCatalogName("Daanse Tutorial - Access Cube Gran");
        role1ConnectionConfig.getRoles().add("role1");

        OlapConnectionCheck connectionCheckRole1 = FACTORY.createOlapConnectionCheck();
        connectionCheckRole1.setName("Cube Gran Check for role1");
        connectionCheckRole1.setDescription("Connection check for Cube Gran tutorial with role1");
        connectionCheckRole1.setConnectionConfig(role1ConnectionConfig);
        connectionCheckRole1.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = FACTORY.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Access Cube Grand Checks");
        suite.setDescription("Comprehensive checks for Access Cube Grand catalog - logistics and package delivery analysis");
        suite.getConnectionChecks().add(connectionCheckRole1);
        return suite;
    }

    private CubeCheck createCubeCheck() {
        CubeCheck cubeCheck = FACTORY.createCubeCheck();
        cubeCheck.setName("Access Cube Grant Check");
        cubeCheck.setDescription("Verify Access Cube Gran cube structure with all dimensions and measures");
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
        hasAllCheck.setName("Hierarchy1 Type HasAll Check");
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedBoolean(false);
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
        //TODO aggregatorCheck not implemented
        //measureCheck.getMeasureAttributeChecks().add(aggregatorCheck);
        return measureCheck;
    }
    private QueryCheck createQueryCheckForRole1() {
        QueryCheck queryCheck = FACTORY.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data for role1");
        queryCheck.setQuery("SELECT FROM [Cube1] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);

        CellValueCheck queryCheck1CellValueCheck = FACTORY.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure1]");
        queryCheck1CellValueCheck.setExpectedValue("42.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(42);

        queryCheck.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck.setEnabled(true);
        return queryCheck;
    }

}
