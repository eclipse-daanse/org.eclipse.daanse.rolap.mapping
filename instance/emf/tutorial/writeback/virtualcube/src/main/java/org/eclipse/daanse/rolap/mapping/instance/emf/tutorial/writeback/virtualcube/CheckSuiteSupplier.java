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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.virtualcube;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;

import org.osgi.service.component.annotations.Component;

/** Check suite for the writeback-virtualcube tutorial. */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Daanse Tutorial - Writeback Virtual Cube";

    @Override
    public OlapCheckSuite get() {
        DimensionCheck categoryDim = createDimensionCheck("Category",
                createHierarchyCheck("Category", createLevelCheck("Category")));
        DimensionCheck regionDim = createDimensionCheck("Region",
                createHierarchyCheck("Region", createLevelCheck("Region")));

        MeasureCheck amount = createMeasureCheck("Amount");
        MeasureCheck value = createMeasureCheck("Value");
        MeasureCheck comments = createMeasureCheck("Comments");

        // CN: read-only cube with Amount only
        CubeCheck cubeNCheck = factory.createCubeCheck();
        cubeNCheck.setName("CubeCheck-CN");
        cubeNCheck.setDescription("Cube CN carries the read-only Amount measure");
        cubeNCheck.setCubeName("CN");
        cubeNCheck.getMeasureChecks().add(amount);
        cubeNCheck.getDimensionChecks().add(createDimensionCheck("Category",
                createHierarchyCheck("Category", createLevelCheck("Category"))));
        cubeNCheck.getDimensionChecks().add(createDimensionCheck("Region",
                createHierarchyCheck("Region", createLevelCheck("Region"))));

        // CT: writeback cube with numeric Value + text Comments
        CubeCheck cubeTCheck = factory.createCubeCheck();
        cubeTCheck.setName("CubeCheck-CT");
        cubeTCheck.setDescription("Cube CT carries the numeric-writeback Value and text-writeback Comments measures");
        cubeTCheck.setCubeName("CT");
        cubeTCheck.getMeasureChecks().add(value);
        cubeTCheck.getMeasureChecks().add(comments);
        cubeTCheck.getDimensionChecks().add(createDimensionCheck("Category",
                createHierarchyCheck("Category", createLevelCheck("Category"))));
        cubeTCheck.getDimensionChecks().add(createDimensionCheck("Region",
                createHierarchyCheck("Region", createLevelCheck("Region"))));

        // V: VirtualCube exposing all three measures
        CubeCheck cubeVCheck = factory.createCubeCheck();
        cubeVCheck.setName("CubeCheck-V");
        cubeVCheck.setDescription("Virtual Cube V references Amount, Value and Comments");
        cubeVCheck.setCubeName("V");
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("Amount"));
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("Value"));
        cubeVCheck.getMeasureChecks().add(createMeasureCheck("Comments"));
        cubeVCheck.getDimensionChecks().add(categoryDim);
        cubeVCheck.getDimensionChecks().add(regionDim);

        DatabaseTableCheck factNTable = createTableCheck("FACT_N",
                createColumnCheck("CATEGORY", "VARCHAR"),
                createColumnCheck("REGION", "VARCHAR"),
                createColumnCheck("AMOUNT", "INTEGER"));
        DatabaseTableCheck factTTable = createTableCheck("FACT_T",
                createColumnCheck("CATEGORY", "VARCHAR"),
                createColumnCheck("REGION", "VARCHAR"),
                createColumnCheck("VALUE", "INTEGER"),
                createColumnCheck("COMMENT", "VARCHAR"));
        DatabaseTableCheck categoryTable = createTableCheck("CATEGORY",
                createColumnCheck("CATEGORY", "VARCHAR"),
                createColumnCheck("NAME", "VARCHAR"));
        DatabaseTableCheck regionTable = createTableCheck("REGION",
                createColumnCheck("REGION", "VARCHAR"),
                createColumnCheck("NAME", "VARCHAR"));
        DatabaseTableCheck wbTable = createTableCheck("FACTWB_T",
                createColumnCheck("CATEGORY", "VARCHAR"),
                createColumnCheck("REGION", "VARCHAR"),
                createColumnCheck("VALUE", "INTEGER"),
                createColumnCheck("COMMENT", "VARCHAR"),
                createColumnCheck("ID", "VARCHAR"),
                createColumnCheck("USER", "VARCHAR"));

        DatabaseSchemaCheck schemaCheck = factory.createDatabaseSchemaCheck();
        schemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        schemaCheck.setDescription("Schema check for the writeback-virtualcube tutorial");
        schemaCheck.getTableChecks().add(factNTable);
        schemaCheck.getTableChecks().add(factTTable);
        schemaCheck.getTableChecks().add(categoryTable);
        schemaCheck.getTableChecks().add(regionTable);
        schemaCheck.getTableChecks().add(wbTable);

        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Catalog check for the writeback-virtualcube tutorial");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeNCheck);
        catalogCheck.getCubeChecks().add(cubeTCheck);
        catalogCheck.getCubeChecks().add(cubeVCheck);
        catalogCheck.getDatabaseSchemaChecks().add(schemaCheck);

        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for the writeback-virtualcube tutorial");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Writeback VirtualCube Suite");
        suite.setDescription("Check suite for the writeback-virtualcube tutorial");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck m = factory.createMeasureCheck();
        m.setName("MeasureCheck-" + measureName);
        m.setDescription("Measure '" + measureName + "' must exist");
        m.setMeasureName(measureName);
        return m;
    }

    private DimensionCheck createDimensionCheck(String name, HierarchyCheck... hierarchies) {
        DimensionCheck d = factory.createDimensionCheck();
        d.setName("DimensionCheck for " + name);
        d.setDimensionName(name);
        for (HierarchyCheck hc : hierarchies) {
            d.getHierarchyChecks().add(hc);
        }
        return d;
    }

    private HierarchyCheck createHierarchyCheck(String name, LevelCheck... levels) {
        HierarchyCheck h = factory.createHierarchyCheck();
        h.setName("HierarchyCheck-" + name);
        h.setHierarchyName(name);
        for (LevelCheck lc : levels) {
            h.getLevelChecks().add(lc);
        }
        return h;
    }

    private LevelCheck createLevelCheck(String name) {
        LevelCheck l = factory.createLevelCheck();
        l.setName("LevelCheck-" + name);
        l.setLevelName(name);
        return l;
    }

    private DatabaseColumnCheck createColumnCheck(String name, String type) {
        DatabaseColumnAttributeCheck attr = factory.createDatabaseColumnAttributeCheck();
        attr.setAttributeType(DatabaseColumnAttribute.TYPE);
        attr.setExpectedValue(type);

        DatabaseColumnCheck c = factory.createDatabaseColumnCheck();
        c.setName("Database Column Check " + name);
        c.setColumnName(name);
        c.getColumnAttributeChecks().add(attr);
        return c;
    }

    private DatabaseTableCheck createTableCheck(String name, DatabaseColumnCheck... columns) {
        DatabaseTableCheck t = factory.createDatabaseTableCheck();
        t.setName("Database Table Check " + name);
        t.setTableName(name);
        for (DatabaseColumnCheck c : columns) {
            t.getColumnChecks().add(c);
        }
        return t;
    }
}
