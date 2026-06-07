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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.nullparent;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
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
 * Provides a check suite for the parent-child hierarchy with null parent value tutorial.
 * Checks that the catalog, cube, measure, and parent-child hierarchy with nullParentValue exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Parent Child Null Parent";
    private static final String CUBE_NAME = "Cube";
    private static final String MEASURE_NAME = "Value";
    private static final String DIMENSION_NAME = "Dimension";
    private static final String HIERARCHY_NAME = "Hierarchy";
    private static final String LEVEL_NAME = "Name";
    private static final String ALL_MEMBER_NAME = "All";

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
        levelCheck.setName("LevelCheck for " + LEVEL_NAME + "1");
        levelCheck.setLevelName(LEVEL_NAME + "1");
        levelCheck.getLevelAttributeChecks().add(levelUniqueCheck);

        // Create hierarchy attribute checks
        HierarchyAttributeCheck hierarchyHasAllCheck = factory.createHierarchyAttributeCheck();
        hierarchyHasAllCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyHasAllCheck.setExpectedBoolean(true);

        HierarchyAttributeCheck hierarchyAllMemberNameCheck = factory.createHierarchyAttributeCheck();
        hierarchyAllMemberNameCheck.setAttributeType(HierarchyAttribute.ALL_MEMBER_NAME);
        hierarchyAllMemberNameCheck.setExpectedValue(ALL_MEMBER_NAME);

        // Create hierarchy check for parent-child hierarchy
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for " + HIERARCHY_NAME);
        hierarchyCheck.setHierarchyName(HIERARCHY_NAME);
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyHasAllCheck);
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAllMemberNameCheck);
        hierarchyCheck.getLevelChecks().add(levelCheck);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + DIMENSION_NAME);
        dimensionCheck.setDimensionName(DIMENSION_NAME);
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists with parent-child hierarchy with null parent value");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with parent-child hierarchy with null parent value");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for parent-child hierarchy with null parent value tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Parent Child Null Parent Suite");
        suite.setDescription("Check suite for the parent-child hierarchy with null parent value mapping tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
