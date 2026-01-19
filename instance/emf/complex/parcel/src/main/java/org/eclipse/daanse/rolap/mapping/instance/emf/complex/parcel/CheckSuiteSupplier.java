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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.parcel;

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

/**
 * Provides a check suite for the Parcel Delivery Service complex mapping example.
 * Checks that the catalog with Parcels cube and its associated dimensions and measures exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    private static final String CATALOG_NAME = "Parcel Delivery Service";

    // Cube name
    private static final String CUBE_PARCELS = "Parcels";

    // Measure names
    private static final String MEASURE_PARCEL_COUNT = "Parcel Count";
    private static final String MEASURE_POSTAGE_SUM = "Postage Sum";
    private static final String MEASURE_INSURANCE_VALUE_SUM = "Insurance Value Sum";
    private static final String MEASURE_WEIGHT_MIN = "Weight Min";
    private static final String MEASURE_WEIGHT_MAX = "Weight Max";
    private static final String MEASURE_WEIGHT_AVG = "Weight Avg";

    // Dimension names
    private static final String DIM_WIDTH = "Width";
    private static final String DIM_DEPTH = "Depth";
    private static final String DIM_HEIGHT = "Height";
    private static final String DIM_PARCEL_TYPE = "Parcel Type";
    private static final String DIM_DEFECT = "Defect";
    private static final String DIM_DELIVERABLE = "Deliverable";
    private static final String DIM_CUSTOMS = "Customs";
    private static final String DIM_RETURN = "Return";
    private static final String DIM_SENDER_ADDRESS = "Sender Address";
    private static final String DIM_RECEIVER_ADDRESS = "Receiver Address";
    private static final String DIM_DROP_OFF_ADDRESS = "Drop Off Address";
    private static final String DIM_DELIVERY_ADDRESS = "Delivery Address";

    @Override
    public OlapCheckSuite get() {
        // Create dimension checks
        DimensionCheck dimCheckWidth = createDimensionCheck(DIM_WIDTH,
                createHierarchyCheck("Width",
                        createLevelCheck("Width")));
        DimensionCheck dimCheckDepth = createDimensionCheck(DIM_DEPTH,
                createHierarchyCheck("Depth",
                        createLevelCheck("Depth")));
        DimensionCheck dimCheckHeight = createDimensionCheck(DIM_HEIGHT,
                createHierarchyCheck("Height",
                        createLevelCheck("Height")));
        DimensionCheck dimCheckParcelType = createDimensionCheck(DIM_PARCEL_TYPE,
                createHierarchyCheck("Parcel Type",
                        createLevelCheck("Parcel Type")));
        DimensionCheck dimCheckDefect = createDimensionCheck(DIM_DEFECT,
                createHierarchyCheck("Defect",
                        createLevelCheck("Defect")));
        DimensionCheck dimCheckDeliverable = createDimensionCheck(DIM_DELIVERABLE,
                createHierarchyCheck("Deliverable",
                        createLevelCheck("Deliverable")));
        DimensionCheck dimCheckCustoms = createDimensionCheck(DIM_CUSTOMS,
                createHierarchyCheck("Customs",
                        createLevelCheck("Customs")));
        DimensionCheck dimCheckReturn = createDimensionCheck(DIM_RETURN,
                createHierarchyCheck("Return",
                        createLevelCheck("Return")));
        DimensionCheck dimCheckSenderAddress = createDimensionCheck(DIM_SENDER_ADDRESS,
                createHierarchyCheck("Geographic Address",
                        createLevelCheck("Continent"),
                        createLevelCheck("Country"),
                        createLevelCheck("City"),
                        createLevelCheck("Postal Code"),
                        createLevelCheck("Street")));
        DimensionCheck dimCheckReceiverAddress = createDimensionCheck(DIM_RECEIVER_ADDRESS,
                createHierarchyCheck("Geographic Address",
                        createLevelCheck("Continent"),
                        createLevelCheck("Country"),
                        createLevelCheck("City"),
                        createLevelCheck("Postal Code"),
                        createLevelCheck("Street")));
        DimensionCheck dimCheckDropOffAddress = createDimensionCheck(DIM_DROP_OFF_ADDRESS,
                createHierarchyCheck("Geographic Address",
                        createLevelCheck("Continent"),
                        createLevelCheck("Country"),
                        createLevelCheck("City"),
                        createLevelCheck("Postal Code"),
                        createLevelCheck("Street")));
        DimensionCheck dimCheckDeliveryAddress = createDimensionCheck(DIM_DELIVERY_ADDRESS,
                createHierarchyCheck("Geographic Address",
                        createLevelCheck("Continent"),
                        createLevelCheck("Country"),
                        createLevelCheck("City"),
                        createLevelCheck("Postal Code"),
                        createLevelCheck("Street")));

        // Create measure checks
        MeasureCheck measureCheckParcelCount = createMeasureCheck(MEASURE_PARCEL_COUNT);
        MeasureCheck measureCheckPostageSum = createMeasureCheck(MEASURE_POSTAGE_SUM);
        MeasureCheck measureCheckInsuranceValueSum = createMeasureCheck(MEASURE_INSURANCE_VALUE_SUM);
        MeasureCheck measureCheckWeightMin = createMeasureCheck(MEASURE_WEIGHT_MIN);
        MeasureCheck measureCheckWeightMax = createMeasureCheck(MEASURE_WEIGHT_MAX);
        MeasureCheck measureCheckWeightAvg = createMeasureCheck(MEASURE_WEIGHT_AVG);

        // Create cube check for Parcels
        CubeCheck cubeCheckParcels = factory.createCubeCheck();
        cubeCheckParcels.setName("CubeCheck-" + CUBE_PARCELS);
        cubeCheckParcels.setDescription("Check that cube '" + CUBE_PARCELS + "' exists");
        cubeCheckParcels.setCubeName(CUBE_PARCELS);
        cubeCheckParcels.getMeasureChecks().add(measureCheckParcelCount);
        cubeCheckParcels.getMeasureChecks().add(measureCheckPostageSum);
        cubeCheckParcels.getMeasureChecks().add(measureCheckInsuranceValueSum);
        cubeCheckParcels.getMeasureChecks().add(measureCheckWeightMin);
        cubeCheckParcels.getMeasureChecks().add(measureCheckWeightMax);
        cubeCheckParcels.getMeasureChecks().add(measureCheckWeightAvg);
        cubeCheckParcels.getDimensionChecks().add(dimCheckWidth);
        cubeCheckParcels.getDimensionChecks().add(dimCheckDepth);
        cubeCheckParcels.getDimensionChecks().add(dimCheckHeight);
        cubeCheckParcels.getDimensionChecks().add(dimCheckParcelType);
        cubeCheckParcels.getDimensionChecks().add(dimCheckDefect);
        cubeCheckParcels.getDimensionChecks().add(dimCheckDeliverable);
        cubeCheckParcels.getDimensionChecks().add(dimCheckCustoms);
        cubeCheckParcels.getDimensionChecks().add(dimCheckReturn);
        cubeCheckParcels.getDimensionChecks().add(dimCheckSenderAddress);
        cubeCheckParcels.getDimensionChecks().add(dimCheckReceiverAddress);
        cubeCheckParcels.getDimensionChecks().add(dimCheckDropOffAddress);
        cubeCheckParcels.getDimensionChecks().add(dimCheckDeliveryAddress);

        // Create database table and column checks
        DatabaseTableCheck tableCheckParcels = createTableCheck("parcels",
                createColumnCheck("parcel_id", "INTEGER"),
                createColumnCheck("width", "DECIMAL"),
                createColumnCheck("depth", "DECIMAL"),
                createColumnCheck("height", "DECIMAL"),
                createColumnCheck("type_id", "INTEGER"),
                createColumnCheck("defect_id", "INTEGER"),
                createColumnCheck("deliverable", "VARCHAR"),
                createColumnCheck("customs", "VARCHAR"),
                createColumnCheck("return_status", "VARCHAR"),
                createColumnCheck("sender_id", "INTEGER"),
                createColumnCheck("receiver_id", "INTEGER"),
                createColumnCheck("drop_off_id", "INTEGER"),
                createColumnCheck("delivery_id", "INTEGER"),
                createColumnCheck("postage", "DECIMAL"),
                createColumnCheck("insurance_value", "DECIMAL"),
                createColumnCheck("weight", "DECIMAL")
        );

        DatabaseTableCheck tableCheckParcelTypes = createTableCheck("parcel_types",
                createColumnCheck("type_id", "INTEGER"),
                createColumnCheck("type_name", "VARCHAR")
        );

        DatabaseTableCheck tableCheckDefects = createTableCheck("defects",
                createColumnCheck("defect_id", "INTEGER"),
                createColumnCheck("defect_name", "VARCHAR")
        );

        DatabaseTableCheck tableCheckAddresses = createTableCheck("addresses",
                createColumnCheck("address_id", "INTEGER"),
                createColumnCheck("continent", "VARCHAR"),
                createColumnCheck("country", "VARCHAR"),
                createColumnCheck("city", "VARCHAR"),
                createColumnCheck("postal_code", "VARCHAR"),
                createColumnCheck("street", "VARCHAR")
        );

        // Create Database Schema Check
        DatabaseSchemaCheck databaseSchemaCheck = factory.createDatabaseSchemaCheck();
        databaseSchemaCheck.setName("Database Schema Check for " + CATALOG_NAME);
        databaseSchemaCheck.setDescription("Database Schema Check for Parcel Delivery Service mapping");
        databaseSchemaCheck.getTableChecks().add(tableCheckParcels);
        databaseSchemaCheck.getTableChecks().add(tableCheckParcelTypes);
        databaseSchemaCheck.getTableChecks().add(tableCheckDefects);
        databaseSchemaCheck.getTableChecks().add(tableCheckAddresses);

        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName(CATALOG_NAME);
        catalogCheck.setDescription("Check that catalog '" + CATALOG_NAME + "' exists with all cubes and dimensions");
        catalogCheck.setCatalogName(CATALOG_NAME);
        catalogCheck.getCubeChecks().add(cubeCheckParcels);
        catalogCheck.getDatabaseSchemaChecks().add(databaseSchemaCheck);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check " + CATALOG_NAME);
        connectionCheck.setDescription("Connection check for Parcel Delivery Service mapping example");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Parcel Delivery Service Example Suite");
        suite.setDescription("Check suite for the Parcel Delivery Service mapping example");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }

    /**
     * Creates a MeasureCheck with the specified name.
     *
     * @param measureName the name of the measure
     * @return the configured MeasureCheck
     */
    private MeasureCheck createMeasureCheck(String measureName) {
        MeasureCheck measureCheck = factory.createMeasureCheck();
        measureCheck.setName("MeasureCheck-" + measureName);
        measureCheck.setDescription("Check that measure '" + measureName + "' exists");
        measureCheck.setMeasureName(measureName);
        return measureCheck;
    }

    /**
     * Creates a DimensionCheck with the specified name.
     *
     * @param dimensionName the name of the dimension
     * @param hierarchyChecks the hierarchy checks to add to the dimension check
     * @return the configured DimensionCheck
     */
    private DimensionCheck createDimensionCheck(String dimensionName, HierarchyCheck... hierarchyChecks) {
        DimensionCheck dimensionCheck = factory.createDimensionCheck();
        dimensionCheck.setName("DimensionCheck for " + dimensionName);
        dimensionCheck.setDimensionName(dimensionName);
        if (hierarchyChecks != null) {
            for (HierarchyCheck hierarchyCheck : hierarchyChecks) {
                dimensionCheck.getHierarchyChecks().add(hierarchyCheck);
            }
        }
        return dimensionCheck;
    }

    /**
     * Creates a HierarchyCheck with the specified name and level checks.
     *
     * @param hierarchyName the name of the hierarchy
     * @param levelChecks the level checks to add to the hierarchy check
     * @return the configured HierarchyCheck
     */
    private HierarchyCheck createHierarchyCheck(String hierarchyName, LevelCheck... levelChecks) {
        HierarchyCheck hierarchyCheck = factory.createHierarchyCheck();
        hierarchyCheck.setName("HierarchyCheck-" + hierarchyName);
        hierarchyCheck.setHierarchyName(hierarchyName);
        if (levelChecks != null) {
            for (LevelCheck levelCheck : levelChecks) {
                hierarchyCheck.getLevelChecks().add(levelCheck);
            }
        }
        return hierarchyCheck;
    }

    /**
     * Creates a LevelCheck with the specified name.
     *
     * @param levelName the name of the level
     * @return the configured LevelCheck
     */
    private LevelCheck createLevelCheck(String levelName) {
        LevelCheck levelCheck = factory.createLevelCheck();
        levelCheck.setName("LevelCheck-" + levelName);
        levelCheck.setLevelName(levelName);
        return levelCheck;
    }

    /**
     * Creates a DatabaseColumnCheck with the specified name and type.
     *
     * @param columnName the name of the column
     * @param columnType the expected type of the column
     * @return the configured DatabaseColumnCheck
     */
    private DatabaseColumnCheck createColumnCheck(String columnName, String columnType) {
        DatabaseColumnAttributeCheck columnTypeCheck = factory.createDatabaseColumnAttributeCheck();
        columnTypeCheck.setAttributeType(DatabaseColumnAttribute.TYPE);
        columnTypeCheck.setExpectedValue(columnType);

        DatabaseColumnCheck columnCheck = factory.createDatabaseColumnCheck();
        columnCheck.setName("Database Column Check " + columnName);
        columnCheck.setColumnName(columnName);
        columnCheck.getColumnAttributeChecks().add(columnTypeCheck);

        return columnCheck;
    }

    /**
     * Creates a DatabaseTableCheck with the specified name and column checks.
     *
     * @param tableName the name of the table
     * @param columnChecks the column checks to add to the table check
     * @return the configured DatabaseTableCheck
     */
    private DatabaseTableCheck createTableCheck(String tableName, DatabaseColumnCheck... columnChecks) {
        DatabaseTableCheck tableCheck = factory.createDatabaseTableCheck();
        tableCheck.setName("Database Table Check " + tableName);
        tableCheck.setTableName(tableName);
        for (DatabaseColumnCheck columnCheck : columnChecks) {
            tableCheck.getColumnChecks().add(columnCheck);
        }
        return tableCheck;
    }
}
