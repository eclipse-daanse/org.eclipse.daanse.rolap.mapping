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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.uniquekeylevelname;

import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttribute;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseColumnCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseSchemaCheck;
import org.eclipse.daanse.olap.check.model.check.DatabaseTableCheck;
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
 * Provides a check suite for the unique key level name hierarchy tutorial.
 * Checks that the catalog, cube, measure, and hierarchy with unique key level name optimization exist and are accessible.
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

        // Create level checks for Automotive dimension
        LevelCheck levelMakeCheck = factory.createLevelCheck();
        levelMakeCheck.setName("LevelCheck for Make");
        levelMakeCheck.setLevelName("Make");

        LevelCheck levelModelCheck = factory.createLevelCheck();
        levelModelCheck.setName("LevelCheck for Model");
        levelModelCheck.setLevelName("Model");

        LevelCheck levelManufacturingPlantCheck = factory.createLevelCheck();
        levelManufacturingPlantCheck.setName("LevelCheck for ManufacturingPlant");
        levelManufacturingPlantCheck.setLevelName("ManufacturingPlant");

        LevelCheck levelVehicleCheck = factory.createLevelCheck();
        levelVehicleCheck.setName("LevelCheck for Vehicle Identification Number");
        levelVehicleCheck.setLevelName("Vehicle Identification Number");

        LevelCheck levelLicensePlateCheck = factory.createLevelCheck();
        levelLicensePlateCheck.setName("LevelCheck for LicensePlateNum");
        levelLicensePlateCheck.setLevelName("LicensePlateNum");

        // Create hierarchy attribute check for hasAll
        HierarchyAttributeCheck hierarchyAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check for Automotive dimension
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck for Automotive");
        hierarchyCheck.setHierarchyName("Automotive");
        hierarchyCheck.getHierarchyAttributeChecks().add(hierarchyAttributeCheck);
        hierarchyCheck.getLevelChecks().add(levelMakeCheck);
        hierarchyCheck.getLevelChecks().add(levelModelCheck);
        hierarchyCheck.getLevelChecks().add(levelManufacturingPlantCheck);
        hierarchyCheck.getLevelChecks().add(levelVehicleCheck);
        hierarchyCheck.getLevelChecks().add(levelLicensePlateCheck);

        // Create dimension check for Automotive
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for Automotive");
        dimensionCheck.setDimensionName("Automotive");
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
        queryCheckCellValueCheck.setName("[Measures].[Measure]");

        QueryCheck queryCheck = factory.createQueryCheck();
        queryCheck.setName("Measure Query Check");
        queryCheck.setDescription("Verify MDX query returns Measure data");
        queryCheck.setQuery("SELECT FROM [Cube] WHERE ([Measures].[Measure])");
        queryCheck.setQueryLanguage(QueryLanguage.MDX);
        queryCheck.setExpectedColumnCount(1);
        queryCheck.getCellChecks().add(queryCheckCellValueCheck);
        queryCheck.setEnabled(true);

        // Create database column checks for AUTOMOTIVE_DIM table
        DatabaseColumnAttributeCheck columnAutoDimIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnAutoDimIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnAutoDimIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckAutoDimId = factory.createDatabaseColumnCheck();
        columnCheckAutoDimId.setName("Database Column Check AUTO_DIM_ID");
        columnCheckAutoDimId.setColumnName("AUTO_DIM_ID");
        columnCheckAutoDimId.getColumnAttributeChecks().add(columnAutoDimIdTypeCheck);

        DatabaseColumnAttributeCheck columnMakeIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMakeIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMakeIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckMakeId = factory.createDatabaseColumnCheck();
        columnCheckMakeId.setName("Database Column Check MAKE_ID");
        columnCheckMakeId.setColumnName("MAKE_ID");
        columnCheckMakeId.getColumnAttributeChecks().add(columnMakeIdTypeCheck);

        DatabaseColumnAttributeCheck columnMakeTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnMakeTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnMakeTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckMake = factory.createDatabaseColumnCheck();
        columnCheckMake.setName("Database Column Check MAKE");
        columnCheckMake.setColumnName("MAKE");
        columnCheckMake.getColumnAttributeChecks().add(columnMakeTypeCheck);

        DatabaseColumnAttributeCheck columnModelIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnModelIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnModelIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckModelId = factory.createDatabaseColumnCheck();
        columnCheckModelId.setName("Database Column Check MODEL_ID");
        columnCheckModelId.setColumnName("MODEL_ID");
        columnCheckModelId.getColumnAttributeChecks().add(columnModelIdTypeCheck);

        DatabaseColumnAttributeCheck columnModelTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnModelTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnModelTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckModel = factory.createDatabaseColumnCheck();
        columnCheckModel.setName("Database Column Check MODEL");
        columnCheckModel.setColumnName("MODEL");
        columnCheckModel.getColumnAttributeChecks().add(columnModelTypeCheck);

        DatabaseColumnAttributeCheck columnPlantIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPlantIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPlantIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckPlantId = factory.createDatabaseColumnCheck();
        columnCheckPlantId.setName("Database Column Check PLANT_ID");
        columnCheckPlantId.setColumnName("PLANT_ID");
        columnCheckPlantId.getColumnAttributeChecks().add(columnPlantIdTypeCheck);

        DatabaseColumnAttributeCheck columnPlantTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPlantTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPlantTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckPlant = factory.createDatabaseColumnCheck();
        columnCheckPlant.setName("Database Column Check PLANT");
        columnCheckPlant.setColumnName("PLANT");
        columnCheckPlant.getColumnAttributeChecks().add(columnPlantTypeCheck);

        DatabaseColumnAttributeCheck columnPlantStateIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPlantStateIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPlantStateIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckPlantStateId = factory.createDatabaseColumnCheck();
        columnCheckPlantStateId.setName("Database Column Check PLANT_STATE_ID");
        columnCheckPlantStateId.setColumnName("PLANT_STATE_ID");
        columnCheckPlantStateId.getColumnAttributeChecks().add(columnPlantStateIdTypeCheck);

        DatabaseColumnAttributeCheck columnPlantCityIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPlantCityIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPlantCityIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckPlantCityId = factory.createDatabaseColumnCheck();
        columnCheckPlantCityId.setName("Database Column Check PLANT_CITY_ID");
        columnCheckPlantCityId.setColumnName("PLANT_CITY_ID");
        columnCheckPlantCityId.getColumnAttributeChecks().add(columnPlantCityIdTypeCheck);

        DatabaseColumnAttributeCheck columnVehicleIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnVehicleIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnVehicleIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckVehicleId = factory.createDatabaseColumnCheck();
        columnCheckVehicleId.setName("Database Column Check VEHICLE_ID");
        columnCheckVehicleId.setColumnName("VEHICLE_ID");
        columnCheckVehicleId.getColumnAttributeChecks().add(columnVehicleIdTypeCheck);

        DatabaseColumnAttributeCheck columnColorIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnColorIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnColorIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckColorId = factory.createDatabaseColumnCheck();
        columnCheckColorId.setName("Database Column Check COLOR_ID");
        columnCheckColorId.setColumnName("COLOR_ID");
        columnCheckColorId.getColumnAttributeChecks().add(columnColorIdTypeCheck);

        DatabaseColumnAttributeCheck columnTrimIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTrimIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTrimIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckTrimId = factory.createDatabaseColumnCheck();
        columnCheckTrimId.setName("Database Column Check TRIM_ID");
        columnCheckTrimId.setColumnName("TRIM_ID");
        columnCheckTrimId.getColumnAttributeChecks().add(columnTrimIdTypeCheck);

        DatabaseColumnAttributeCheck columnLicenseIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLicenseIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLicenseIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckLicenseId = factory.createDatabaseColumnCheck();
        columnCheckLicenseId.setName("Database Column Check LICENSE_ID");
        columnCheckLicenseId.setColumnName("LICENSE_ID");
        columnCheckLicenseId.getColumnAttributeChecks().add(columnLicenseIdTypeCheck);

        DatabaseColumnAttributeCheck columnLicenseTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLicenseTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLicenseTypeCheck.setExpectedValue("VARCHAR");

        DatabaseColumnCheck columnCheckLicense = factory.createDatabaseColumnCheck();
        columnCheckLicense.setName("Database Column Check LICENSE");
        columnCheckLicense.setColumnName("LICENSE");
        columnCheckLicense.getColumnAttributeChecks().add(columnLicenseTypeCheck);

        DatabaseColumnAttributeCheck columnLicenseStateIdTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnLicenseStateIdTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnLicenseStateIdTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckLicenseStateId = factory.createDatabaseColumnCheck();
        columnCheckLicenseStateId.setName("Database Column Check LICENSE_STATE_ID");
        columnCheckLicenseStateId.setColumnName("LICENSE_STATE_ID");
        columnCheckLicenseStateId.getColumnAttributeChecks().add(columnLicenseStateIdTypeCheck);

        DatabaseColumnAttributeCheck columnPriceTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnPriceTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnPriceTypeCheck.setExpectedValue("INTEGER");

        DatabaseColumnCheck columnCheckPrice = factory.createDatabaseColumnCheck();
        columnCheckPrice.setName("Database Column Check PRICE");
        columnCheckPrice.setColumnName("PRICE");
        columnCheckPrice.getColumnAttributeChecks().add(columnPriceTypeCheck);

        // Create Database Table Check
        DatabaseTableCheck databaseTableCheckAutomotiveDim = factory.createDatabaseTableCheck();
        databaseTableCheckAutomotiveDim.setName("Database Table AUTOMOTIVE_DIM Check");
        databaseTableCheckAutomotiveDim.setTableName("AUTOMOTIVE_DIM");
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckAutoDimId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckMakeId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckMake);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckModelId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckModel);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckPlantId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckPlant);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckPlantStateId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckPlantCityId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckVehicleId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckColorId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckTrimId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckLicenseId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckLicense);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckLicenseStateId);
        databaseTableCheckAutomotiveDim.getColumnChecks().add(columnCheckPrice);

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check");
        databaseSchemaCheck.setDescription("Database Schema Check for Daanse Tutorial - Unique Key Level Name");
        databaseSchemaCheck.getTableChecks().add(databaseTableCheckAutomotiveDim);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tutorial - Unique Key Level Name");
        catalogCheck.setDescription("Check that catalog 'Daanse Tutorial - Unique Key Level Name' exists with its cubes");
        catalogCheck.setCatalogName("Daanse Tutorial - Unique Key Level Name");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tutorial - Unique Key Level Name");
        connectionCheck.setDescription("Connection check for Daanse Tutorial - Unique Key Level Name");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tutorial - Unique Key Level Name");
        suite.setDescription("Check suite for the Daanse Tutorial - Unique Key Level Name");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
