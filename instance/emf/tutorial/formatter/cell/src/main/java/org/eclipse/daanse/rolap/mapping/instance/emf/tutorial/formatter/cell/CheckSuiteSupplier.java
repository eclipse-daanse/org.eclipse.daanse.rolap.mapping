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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.formatter.cell;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
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

/**
 * Provides a check suite for the formatter cell tutorial.
 * Checks that the catalog, cube, and measure with cell formatter exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        //TODO fix aggregates check executer
        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setAttributeType(MeasureAttribute.AGGREGATOR);
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setAttributeType(MeasureAttribute.NAME);
        measureSumAttributeCheck2.setExpectedValue("Measure1");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        measureSumAttributeCheck3.setExpectedValue("[Measures].[Measure1]");

        // Create measure check
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-Measure1");
        measureCheck.setDescription("Check that measure 'Measure1' exists with cell formatter");
        measureCheck.setMeasureName("Measure1");
        measureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck1);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-CubeOneNumericMeasureDifferentDataTypes");
        cubeCheck.setDescription("Check that cube 'CubeOneNumericMeasureDifferentDataTypes' exists");
        cubeCheck.setCubeName("CubeOneNumericMeasureDifferentDataTypes");
        cubeCheck.getMeasureChecks().add(measureCheck);

        // Create query check
        CellValueCheck queryCheckCellValueCheck = factory.createCellValueCheck();
        queryCheckCellValueCheck.setName("[Measures].[Measure1]");
        queryCheckCellValueCheck.setExpectedValue("63.63");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [CubeOneNumericMeasureDifferentDataTypes] WHERE ([Measures].[Measure1])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(0);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Formatter Cell");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Formatter Cell' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Formatter Cell");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Formatter Cell");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Formatter Cell");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Formatter Cell");
        suite.setDescription("Check suite for the Daanse Tutorial - Formatter Cell");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
