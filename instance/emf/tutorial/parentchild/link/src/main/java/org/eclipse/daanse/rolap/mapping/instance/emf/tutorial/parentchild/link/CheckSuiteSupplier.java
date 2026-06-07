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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.link;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
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
 * Provides a check suite for the parent-child hierarchy with link tutorial.
 * Checks that the catalog, cube, measure, parent-child hierarchy, and closure table exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Parent Child Link";
    private static final String CUBE_NAME = "Cube";
    private static final String MEASURE_NAME = "Value";
    private static final String DIMENSION_NAME = "Dimension";
    private static final String HIERARCHY_NAME = "Hierarchy";
    private static final String LEVEL_NAME = "Name";

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + MEASURE_NAME);
        measureCheck.setDescription("Check that measure '" + MEASURE_NAME + "' exists");
        measureCheck.setMeasureName(MEASURE_NAME);

        // Create level check with unique members attribute
        LevelAttributeCheck levelUniqueCheck1 = factory.createLevelAttributeCheck();
        levelUniqueCheck1.setAttributeType(LevelAttribute.IS_UNIQUE);
        levelUniqueCheck1.setExpectedBoolean(true);

        LevelCheck levelCheck1 = factory.createLevelCheck();
        levelCheck1.setName("LevelCheck for " + LEVEL_NAME + "1");
        levelCheck1.setLevelName(LEVEL_NAME + "1");
        levelCheck1.getLevelAttributeChecks().add(levelUniqueCheck1);

        LevelAttributeCheck levelUniqueCheck2 = factory.createLevelAttributeCheck();
        levelUniqueCheck2.setAttributeType(LevelAttribute.IS_UNIQUE);
        levelUniqueCheck2.setExpectedBoolean(true);

        LevelCheck levelCheck2 = factory.createLevelCheck();
        levelCheck2.setName("LevelCheck for " + LEVEL_NAME + "2");
        levelCheck2.setLevelName(LEVEL_NAME + "2");
        levelCheck2.getLevelAttributeChecks().add(levelUniqueCheck2);

        LevelAttributeCheck levelUniqueCheck3 = factory.createLevelAttributeCheck();
        levelUniqueCheck3.setAttributeType(LevelAttribute.IS_UNIQUE);
        levelUniqueCheck3.setExpectedBoolean(true);

        LevelCheck levelCheck3 = factory.createLevelCheck();
        levelCheck3.setName("LevelCheck for " + LEVEL_NAME + "3");
        levelCheck3.setLevelName(LEVEL_NAME + "3");
        levelCheck3.getLevelAttributeChecks().add(levelUniqueCheck3);

        // Create hierarchy check for parent-child hierarchy
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for " + HIERARCHY_NAME);
        hierarchyCheck.setHierarchyName(HIERARCHY_NAME);
        hierarchyCheck.getLevelChecks().add(levelCheck1);
        hierarchyCheck.getLevelChecks().add(levelCheck2);

        // Create dimension check
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + DIMENSION_NAME);
        dimensionCheck.setDimensionName(DIMENSION_NAME);
        dimensionCheck.getHierarchyChecks().add(hierarchyCheck);

        // Create cube check with measure and dimension checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Check that cube '" + CUBE_NAME + "' exists with parent-child hierarchy");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with parent-child hierarchy");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for parent-child hierarchy with link tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Parent Child Link Suite");
        suite.setDescription("Check suite for the parent-child hierarchy with link mapping tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
