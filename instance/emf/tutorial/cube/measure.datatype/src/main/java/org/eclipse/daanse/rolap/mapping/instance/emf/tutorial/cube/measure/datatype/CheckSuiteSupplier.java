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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.datatype;

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DataType;
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
 * Provides a check suite for the measure datatype cube mapping.
 * Checks that the catalog, cube, and measures with different data types exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check for "Measure - Datatype Integer" with DATA_TYPE attribute check
        MeasureAttributeCheck integerDataTypeAttributeCheck = factory.createMeasureAttributeCheck();
        integerDataTypeAttributeCheck.setName("Measure Attribute Check DATA_TYPE INTEGER");
        integerDataTypeAttributeCheck.setAttributeType(MeasureAttribute.DATA_TYPE);
        integerDataTypeAttributeCheck.setExpectedDataType(DataType.INTEGER);

        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureCheck measureIntegerCheck = factory.createMeasureCheck();
        measureIntegerCheck.setName("MeasureCheck-Measure - Datatype Integer");
        measureIntegerCheck.setDescription("Check that measure 'Measure - Datatype Integer' exists with INTEGER data type");
        measureIntegerCheck.setMeasureName("Measure - Datatype Integer");
        measureIntegerCheck.getMeasureAttributeChecks().add(integerDataTypeAttributeCheck);
        measureIntegerCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck1);

        // Create measure check for "Measure - Datatype Numeric" with DATA_TYPE attribute check
        MeasureAttributeCheck numericDataTypeAttributeCheck = factory.createMeasureAttributeCheck();
        numericDataTypeAttributeCheck.setName("Measure Attribute Check DATA_TYPE NUMERIC");
        numericDataTypeAttributeCheck.setAttributeType(MeasureAttribute.DATA_TYPE);
        numericDataTypeAttributeCheck.setExpectedDataType(DataType.NUMERIC);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setExpectedAggregator(AggregatorType.SUM);

        MeasureCheck measureNumericCheck = factory.createMeasureCheck();
        measureNumericCheck.setName("MeasureCheck-Measure - Datatype Numeric");
        measureNumericCheck.setDescription("Check that measure 'Measure - Datatype Numeric' exists with NUMERIC data type");
        measureNumericCheck.setMeasureName("Measure - Datatype Numeric");
        measureNumericCheck.getMeasureAttributeChecks().add(numericDataTypeAttributeCheck);
        measureNumericCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck1);

        // Create cube check with all measure checks
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-MeasuresDatatypeCube");
        cubeCheck.setDescription("Check that cube 'MeasuresDatatypeCube' exists");
        cubeCheck.setCubeName("MeasuresDatatypeCube");
        cubeCheck.getMeasureChecks().add(measureIntegerCheck);
        cubeCheck.getMeasureChecks().add(measureNumericCheck);

        // Create query checks for each measure
        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[Measure - Datatype Integer]");

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check Datatype Integer");
        queryCheck1.setDescription("Verify MDX query returns Measure data for Measure - Datatype Integer");
        queryCheck1.setQuery("SELECT FROM [MeasuresDatatypeCube] WHERE ([Measures].[Measure - Datatype Integer])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(1);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck = factory.createCellValueCheck();
        queryCheck2CellValueCheck.setName("[Measures].[Measure - Datatype Numeric]");

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("Measure Query Check Datatype Numeric");
        queryCheck2.setDescription("Verify MDX query returns Measure data for Measure - Datatype Numeric");
        queryCheck2.setQuery("SELECT FROM [MeasuresDatatypeCube] WHERE ([Measures].[Measure - Datatype Numeric])");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck);
        queryCheck2.setEnabled(true);

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
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Measure Datatype");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckFact);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Measure Datatype");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Measure Datatype' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Measure Datatype");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Measure Datatype");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Measure Datatype");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Measure Datatype");
        suite.setDescription("Check suite for the Daanse Tutorial - Measure Datatype");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
