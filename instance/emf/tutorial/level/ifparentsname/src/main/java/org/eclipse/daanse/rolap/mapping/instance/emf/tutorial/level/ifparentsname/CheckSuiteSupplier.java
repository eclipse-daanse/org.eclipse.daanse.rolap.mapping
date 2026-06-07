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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.ifparentsname;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
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
 * Provides a check suite for the level if parents name tutorial.
 * Checks that the catalog, cube, dimension, hierarchy, levels with HideMemberIf.IF_PARENTS_NAME attribute, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure1");
        measureCheck.setDescription("Check that measure 'Measure1' exists");
        measureCheck.setMeasureName("Measure1");

        // Create level checks
        LevelCheck level1Check = factory.createLevelCheck();
        level1Check.setName("LevelCheck-Level1");
        level1Check.setDescription("Check that level 'Level1' exists");
        level1Check.setLevelName("Level1");

        LevelCheck level2Check = factory.createLevelCheck();
        level2Check.setName("LevelCheck-Level2");
        level2Check.setDescription("Check that level 'Level2' exists with HideMemberIf.IF_PARENTS_NAME");
        level2Check.setLevelName("Level2");

        // Create hierarchy attribute check
        HierarchyAttributeCheck hasAllCheck = factory.createHierarchyAttributeCheck();
        hasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hasAllCheck.setExpectedValue("true");

        // Create hierarchy check
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-Hierarchy1");
        hierarchyCheck.setDescription("Check that hierarchy 'Hierarchy1' exists with two levels");
        hierarchyCheck.setHierarchyName("Hierarchy1");
        hierarchyCheck.getLevelChecks().add(level1Check);
        hierarchyCheck.getLevelChecks().add(level2Check);
        hierarchyCheck.getHierarchyAttributeChecks().add(hasAllCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck-DimensionMembersHiddenIfBlankName");
        dimensionCheck.setDescription("Check that dimension 'DimensionMembersHiddenIfBlankName' exists with hierarchy");
        dimensionCheck.setDimensionName("DimensionMembersHiddenIfBlankName");
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
        queryCheckCellValueCheck.setName("[Measures].[Measure1]");
        queryCheckCellValueCheck.setExpectedValue("147.0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Level If Parents Name");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Level If Parents Name' exists with HideMemberIf.IF_PARENTS_NAME level");
        catalogCheck.setCatalogName("Daanse Tutorial - Level If Parents Name");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Level If Parents Name");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Level If Parents Name");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Level If Parents Name");
        suite.setDescription("Check suite for the Daanse Tutorial - Level If Parents Name with HideMemberIf.IF_PARENTS_NAME attribute");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
