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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.format;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
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
 * Provides a check suite for the measure format cube mapping.
 * Checks that the catalog, cube, and measures with different format strings exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check for "Format #,##0.00" with FORMAT_STRING attribute check
        MeasureAttributeCheck formatTwoDecimalsAttributeCheck = factory.createMeasureAttributeCheck();
        formatTwoDecimalsAttributeCheck.setName("Measure Attribute Check FORMAT_STRING #,##0.00");
        formatTwoDecimalsAttributeCheck.setAttributeType(MeasureAttribute.FORMAT_STRING);
        formatTwoDecimalsAttributeCheck.setExpectedValue("#,##0.00");

        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureCheck measureTwoDecimalsCheck = factory.createMeasureCheck();
        measureTwoDecimalsCheck.setName("MeasureCheck-Format #,##0.00");
        measureTwoDecimalsCheck.setDescription("Check that measure 'Format #,##0.00' exists with format string '#,##0.00'");
        measureTwoDecimalsCheck.setMeasureName("Format #,##0.00");
        measureTwoDecimalsCheck.getMeasureAttributeChecks().add(formatTwoDecimalsAttributeCheck);
        measureTwoDecimalsCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck1);

        // Create measure check for "Format #,##0" with FORMAT_STRING attribute check
        MeasureAttributeCheck formatNoDecimalsAttributeCheck = factory.createMeasureAttributeCheck();
        formatNoDecimalsAttributeCheck.setName("Measure Attribute Check FORMAT_STRING #,##0");
        formatNoDecimalsAttributeCheck.setAttributeType(MeasureAttribute.FORMAT_STRING);
        formatNoDecimalsAttributeCheck.setExpectedValue("#,##0");

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setExpectedAggregator(AggregatorType.SUM);

        MeasureCheck measureNoDecimalsCheck = factory.createMeasureCheck();
        measureNoDecimalsCheck.setName("MeasureCheck-Format #,##0");
        measureNoDecimalsCheck.setDescription("Check that measure 'Format #,##0' exists with format string '#,##0'");
        measureNoDecimalsCheck.setMeasureName("Format #,##0");
        measureNoDecimalsCheck.getMeasureAttributeChecks().add(formatNoDecimalsAttributeCheck);
        measureNoDecimalsCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck2);

        // Create measure check for "Format #,##0." with FORMAT_STRING attribute check
        MeasureAttributeCheck formatOneDecimalAttributeCheck = factory.createMeasureAttributeCheck();
        formatOneDecimalAttributeCheck.setName("Measure Attribute Check FORMAT_STRING #,##0.");
        formatOneDecimalAttributeCheck.setAttributeType(MeasureAttribute.FORMAT_STRING);
        formatOneDecimalAttributeCheck.setExpectedValue("#,##0.");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setExpectedAggregator(AggregatorType.SUM);

        MeasureCheck measureOneDecimalCheck = factory.createMeasureCheck();
        measureOneDecimalCheck.setName("MeasureCheck-Format #,##0.");
        measureOneDecimalCheck.setDescription("Check that measure 'Format #,##0.' exists with format string '#,##0.'");
        measureOneDecimalCheck.setMeasureName("Format #,##0.");
        measureOneDecimalCheck.getMeasureAttributeChecks().add(formatOneDecimalAttributeCheck);
        measureOneDecimalCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck3);

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresFormatCube");
        cubeCheck.setDescription("Check that cube 'MeasuresFormatCube' exists");
        cubeCheck.setCubeName("MeasuresFormatCube");
        cubeCheck.getMeasureChecks().add(measureTwoDecimalsCheck);
        cubeCheck.getMeasureChecks().add(measureNoDecimalsCheck);
        cubeCheck.getMeasureChecks().add(measureOneDecimalCheck);

        // Create query checks for each measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Format #,##0.00]");
        queryCheck1CellValueCheck.setExpectedValue("63.00");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Format #,##0.00");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Format #,##0.00");
        queryCheck1.setQuery("SELECT FROM [MeasuresFormatCube] WHERE ([Measures].[Format #,##0.00])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Format #,##0]");
        queryCheck2CellValueCheck.setExpectedValue("63.000");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Format #,##0");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Format #,##0");
        queryCheck2.setQuery("SELECT FROM [MeasuresFormatCube] WHERE ([Measures].[Format #,##0])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

        CellValueCheck queryCheck3CellValueCheck = factory.createCellValueCheck();
        queryCheck3CellValueCheck.setName("[Measures].[Format #,##0.]");
        queryCheck3CellValueCheck.setExpectedValue("");

        QueryCheck queryCheck3 = factory.createQueryCheck();
        queryCheck3.setName("Measure Query Check Format #,##0.");
        queryCheck3.setDescription("Verify MDX query returns Measure data for Format #,##0.");
        queryCheck3.setQuery("SELECT FROM [MeasuresFormatCube] WHERE ([Measures].[Format #,##0.])");
        queryCheck3.setQueryLanguage(QueryLanguage.MDX);
        queryCheck3.setExpectedColumnCount(1);
        queryCheck3.getCellChecks().add(queryCheck3CellValueCheck);
        queryCheck3.setEnabled(true);

        // Create database column checks for the Fact table
        DatabaseColumnAttributeCheck columnAttributeCheckKey = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckKey.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckKey.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckKey = factory.createDatabaseColumnCheck();
        columnCheckKey.setName("Database Column Check KEY");
        columnCheckKey.setColumnName("KEY");
        columnCheckKey.getColumnAttributeChecks().add(columnAttributeCheckKey);

        DatabaseColumnAttributeCheck columnAttributeCheckValue = factory.createDatabaseColumnAttributeCheck();
        columnAttributeCheckValue.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAttributeCheckValue.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckValue = factory.createDatabaseColumnCheck();
        columnCheckValue.setName("Database Column Check VALUE");
        columnCheckValue.setColumnName("VALUE");
        columnCheckValue.getColumnAttributeChecks().add(columnAttributeCheckValue);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckFact = factory.createDatabaseTableCheck();
        databaseTableCheckFact.setName("Database Table Fact Check");
        databaseTableCheckFact.setTableName("Fact");
        databaseTableCheckFact.getColumnChecks().add(columnCheckKey);
        databaseTableCheckFact.getColumnChecks().add(columnCheckValue);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Format");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Format");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Format' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Format");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getQueryChecks().add(queryCheck3);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Format");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Format");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Format");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Format");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
