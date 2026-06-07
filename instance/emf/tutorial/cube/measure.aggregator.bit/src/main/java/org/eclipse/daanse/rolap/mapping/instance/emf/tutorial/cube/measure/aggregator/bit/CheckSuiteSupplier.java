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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.bit;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the minimal cube mapping.
 * Checks that the catalog, cube, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck bitAggAndMeasureCheck = factory.createMeasureCheck();
        bitAggAndMeasureCheck.setName("MeasureCheck-BitAgg AND");
        bitAggAndMeasureCheck.setDescription("Check that measure 'BitAgg AND' exists");
        bitAggAndMeasureCheck.setMeasureName("BitAgg AND");

        MeasureCheck bitAggOrMeasureCheck = factory.createMeasureCheck();
        bitAggOrMeasureCheck.setName("MeasureCheck-BitAgg OR");
        bitAggOrMeasureCheck.setDescription("Check that measure 'BitAgg OR' exists");
        bitAggOrMeasureCheck.setMeasureName("BitAgg OR");

        MeasureCheck bitAggXOrMeasureCheck = factory.createMeasureCheck();
        bitAggXOrMeasureCheck.setName("MeasureCheck-BitAgg XOR");
        bitAggXOrMeasureCheck.setDescription("Check that measure 'BitAgg XOR' exists");
        bitAggXOrMeasureCheck.setMeasureName("BitAgg XOR");

        MeasureCheck bitAggNAndMeasureCheck = factory.createMeasureCheck();
        bitAggNAndMeasureCheck.setName("MeasureCheck-BitAgg NAND");
        bitAggNAndMeasureCheck.setDescription("Check that measure 'BitAgg NAND' exists");
        bitAggNAndMeasureCheck.setMeasureName("BitAgg NAND");


        MeasureCheck bitAggNOrMeasureCheck = factory.createMeasureCheck();
        bitAggNOrMeasureCheck.setName("MeasureCheck-BitAgg NOR");
        bitAggNOrMeasureCheck.setDescription("Check that measure 'BitAgg NOR' exists");
        bitAggNOrMeasureCheck.setMeasureName("BitAgg NOR");

        MeasureCheck bitAggNXOrMeasureCheck = factory.createMeasureCheck();
        bitAggNXOrMeasureCheck.setName("MeasureCheck-BitAgg NXOR");
        bitAggNXOrMeasureCheck.setDescription("Check that measure 'BitAgg NXOR' exists");
        bitAggNXOrMeasureCheck.setMeasureName("BitAgg NXOR");


        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresAggregatorsCube");
        cubeCheck.setDescription("Check that cube 'MeasuresAggregatorsCube' exists");
        cubeCheck.setCubeName("MeasuresAggregatorsCube");
        cubeCheck.getMeasureChecks().add(bitAggAndMeasureCheck);
        cubeCheck.getMeasureChecks().add(bitAggOrMeasureCheck);
        cubeCheck.getMeasureChecks().add(bitAggXOrMeasureCheck);
        cubeCheck.getMeasureChecks().add(bitAggNAndMeasureCheck);
        cubeCheck.getMeasureChecks().add(bitAggNOrMeasureCheck);
        cubeCheck.getMeasureChecks().add(bitAggNXOrMeasureCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[BitAgg AND]");
        queryCheck1CellValueCheck.setExpectedValue("0");
        queryCheck1CellValueCheck.getCoordinates().add(0);
        queryCheck1CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check BitAgg AND");
        queryCheck1.setDescription("Verify MDX query returns Measure data for BitAgg AND");
        queryCheck1.setQuery("SELECT [Measures].[BitAgg AND] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[BitAgg OR]");
        queryCheck2CellValueCheck.setExpectedValue("1");
        queryCheck2CellValueCheck.getCoordinates().add(0);
        queryCheck2CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check BitAgg OR");
        queryCheck2.setDescription("Verify MDX query returns Measure data for BitAgg OR");
        queryCheck2.setQuery("SELECT [Measures].[BitAgg OR] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[BitAgg XOR]");
        queryCheck3CellValueCheck.setExpectedValue("1");
        queryCheck3CellValueCheck.getCoordinates().add(0);
        queryCheck3CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check BitAgg XOR");
        queryCheck3.setDescription("Verify MDX query returns Measure data for BitAgg XOR");
        queryCheck3.setQuery("SELECT [Measures].[BitAgg XOR] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        CellValueCheck queryCheck4CellValueCheck = factory.createCellValueCheck();
        queryCheck4CellValueCheck.setName("[Measures].[BitAgg NAND]");
        queryCheck4CellValueCheck.setExpectedValue("-1");
        queryCheck4CellValueCheck.getCoordinates().add(0);
        queryCheck4CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck4 = factory.createQueryCheck();
        queryCheck4.setName("Measure Query Check BitAgg NAND");
        queryCheck4.setDescription("Verify MDX query returns Measure data for BitAgg NAND");
        queryCheck4.setQuery("SELECT [Measures].[BitAgg NAND] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck4.setQueryLanguage(QueryLanguage.MDX);
        queryCheck4.getCellChecks().add(queryCheck4CellValueCheck);
        queryCheck4.setEnabled(true);

        CellValueCheck queryCheck5CellValueCheck = factory.createCellValueCheck();
        queryCheck5CellValueCheck.setName("[Measures].[BitAgg NOR]");
        queryCheck5CellValueCheck.setExpectedValue("-2");
        queryCheck5CellValueCheck.getCoordinates().add(0);
        queryCheck5CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck5 = factory.createQueryCheck();
        queryCheck5.setName("Measure Query Check BitAgg NOR");
        queryCheck5.setDescription("Verify MDX query returns Measure data for BitAgg NOR");
        queryCheck5.setQuery("SELECT [Measures].[BitAgg NOR] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck5.setQueryLanguage(QueryLanguage.MDX);
        queryCheck5.getCellChecks().add(queryCheck5CellValueCheck);
        queryCheck5.setEnabled(true);

        CellValueCheck queryCheck6CellValueCheck = factory.createCellValueCheck();
        queryCheck6CellValueCheck.setName("[Measures].[BitAgg NXOR]");
        queryCheck6CellValueCheck.setExpectedValue("-2");
        queryCheck6CellValueCheck.getCoordinates().add(0);
        queryCheck6CellValueCheck.setTolerance(0.001);

        QueryCheck queryCheck6 = factory.createQueryCheck();
        queryCheck6.setName("Measure Query Check BitAgg NXOR");
        queryCheck6.setDescription("Verify MDX query returns Measure data for BitAgg NXOR");
        queryCheck6.setQuery("SELECT [Measures].[BitAgg NXOR] ON COLUMNS FROM [MeasuresAggregatorsCube]");
        queryCheck6.setQueryLanguage(QueryLanguage.MDX);
        queryCheck6.getCellChecks().add(queryCheck6CellValueCheck);
        queryCheck6.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Aggregator Base");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Aggregator Bit' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Aggregator Bit");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getQueryChecks().add(queryCheck4);
        catalogCheck.getQueryChecks().add(queryCheck5);
        catalogCheck.getQueryChecks().add(queryCheck6);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Aggregator Bit");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Aggregator Bit");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Aggregator Bit");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Aggregator Bit");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
