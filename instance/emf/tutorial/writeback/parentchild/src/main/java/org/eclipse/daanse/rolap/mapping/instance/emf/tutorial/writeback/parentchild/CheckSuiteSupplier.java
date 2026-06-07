/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.parentchild;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;

import org.osgi.service.component.annotations.Component;

@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Writeback Parent-Child";
    private static final String CUBE_NAME = "C";

    @Override
    public OlapCheckSuite get() {
        DimensionCheck dim = createDimensionCheck("Tree",
                createHierarchyCheck("Tree", createLevelCheck("Node")));

        MeasureCheck value = createMeasureCheck("Value");

        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-" + CUBE_NAME);
        cubeCheck.setDescription("Cube '" + CUBE_NAME + "' carries one Value measure");
        cubeCheck.setCubeName(CUBE_NAME);
        cubeCheck.getMeasureChecks().add(value);
        cubeCheck.getDimensionChecks().add(dim);


        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Catalog check for the writeback-parentchild tutorial");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheck);

        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for the writeback-parentchild tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Writeback Parent-Child Suite");
        suite.setDescription("Check suite for the writeback-parentchild tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    private MeasureCheck createMeasureCheck(String name) {
        MeasureCheck m = factory.createMeasureCheck();
        m.setName("MeasureCheck-" + name);
        m.setDescription("Measure '" + name + "' must exist");
        m.setMeasureName(name);
        return m;
    }

    private DimensionCheck createDimensionCheck(String name, HierarchyCheck... hierarchies) {
        DimensionCheck d = factory.createDimensionCheck();
        d.setName("DimensionCheck for " + name);
        d.setDimensionName(name);
        for (HierarchyCheck h : hierarchies) {
            d.getHierarchyChecks().add(h);
        }
        return d;
    }

    private HierarchyCheck createHierarchyCheck(String name, LevelCheck... levels) {
        HierarchyCheck h = factory.createHierarchyCheck();
        h.setName("HierarchyCheck-" + name);
        h.setHierarchyName(name);
        for (LevelCheck l : levels) {
            h.getLevelChecks().add(l);
        }
        return h;
    }

    private LevelCheck createLevelCheck(String name) {
        LevelCheck l = factory.createLevelCheck();
        l.setName("LevelCheck-" + name);
        l.setLevelName(name);
        return l;
    }


}
