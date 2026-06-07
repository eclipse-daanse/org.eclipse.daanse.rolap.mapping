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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.optimisationwithlevelattribute;

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
 * Provides a check suite for the dimension optimisation with level attribute tutorial.
 * Checks that the catalog, cube, and dimension with level attribute optimization exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure");
        measureCheck.setDescription("Check that measure 'Measure' exists");
        measureCheck.setMeasureName("Measure");

        // Create level checks
        LevelCheck levelH1Level1Check = factory.createLevelCheck();
        levelH1Level1Check.setName("LevelCheck for H1_Level1");
        levelH1Level1Check.setLevelName("H1_Level1");

        LevelCheck levelH1Level2Check = factory.createLevelCheck();
        levelH1Level2Check.setName("LevelCheck for H1_Level2");
        levelH1Level2Check.setLevelName("H1_Level2");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check for Hierarchy1
        HierarchyCheck hierarchyCheck1 = factory.createHierarchyCheck();
        hierarchyCheck1.setName("HierarchyCheck for Hierarchy1 (Dim1)");
        hierarchyCheck1.setHierarchyName("Hierarchy1");
        hierarchyCheck1.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck1.getLevelChecks().add(levelH1Level1Check);
        hierarchyCheck1.getLevelChecks().add(levelH1Level2Check);

        // Create dimension check for Dim1
        DimensionCheck dimensionCheck1 = factory.createDimensionCheck();
        dimensionCheck1.setName("DimensionCheck for Dim1");
        dimensionCheck1.setDimensionName("Dim1");
        dimensionCheck1.getHierarchyChecks().add(hierarchyCheck1);

        // Create level checks for Dim2 (reusing the same level definitions)
        LevelCheck levelH1Level1Check2 = factory.createLevelCheck();
        levelH1Level1Check2.setName("LevelCheck for H1_Level1 (Dim2)");
        levelH1Level1Check2.setLevelName("H1_Level1");

        LevelCheck levelH1Level2Check2 = factory.createLevelCheck();
        levelH1Level2Check2.setName("LevelCheck for H1_Level2 (Dim2)");
        levelH1Level2Check2.setLevelName("H1_Level2");

        // Create hierarchy attribute check for hasAll (Dim2)
        HierarchyAttributeCheck hierarchyAttributeCheck2 = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck2.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck2.setExpectedBoolean(true);

        // Create hierarchy check for Hierarchy1 (for Dim2)
        HierarchyCheck hierarchyCheck2 = factory.createHierarchyCheck();
        hierarchyCheck2.setName("HierarchyCheck for Hierarchy1 (Dim2)");
        hierarchyCheck2.setHierarchyName("Hierarchy1");
        hierarchyCheck2.getHierarchyAttributeChecks().add(hierarchyAttributeCheck2);
        hierarchyCheck2.getLevelChecks().add(levelH1Level1Check2);
        hierarchyCheck2.getLevelChecks().add(levelH1Level2Check2);

        // Create dimension check for Dim2
        DimensionCheck dimensionCheck2 = factory.createDimensionCheck();
        dimensionCheck2.setName("DimensionCheck for Dim2");
        dimensionCheck2.setDimensionName("Dim2");
        dimensionCheck2.getHierarchyChecks().add(hierarchyCheck2);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube");
        cubeCheck.setDescription("Check that cube 'Cube' exists");
        cubeCheck.setCubeName("Cube");
        cubeCheck.getMeasureChecks().add(measureCheck);
        cubeCheck.getDimensionChecks().add(dimensionCheck1);
        cubeCheck.getDimensionChecks().add(dimensionCheck2);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure]");
        queryCheckCellValueCheck.setExpectedValue("147.0");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Dimension Optimisation With Level Attribute' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Dimension Optimisation With Level Attribute");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Dimension Optimisation With Level Attribute");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Dimension Optimisation With Level Attribute");
        suite.setDescription("Check suite for the Daanse Tutorial - Dimension Optimisation With Level Attribute");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
