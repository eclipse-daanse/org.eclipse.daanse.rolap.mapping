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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MaxMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MinMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.6", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    // Static columns - Fact Table (parcels)
    public static final Column COLUMN_PARCEL_ID_FACT;
    public static final Column COLUMN_WIDTH_FACT;
    public static final Column COLUMN_DEPTH_FACT;
    public static final Column COLUMN_HEIGHT_FACT;
    public static final Column COLUMN_TYPE_ID_FACT;
    public static final Column COLUMN_DEFECT_ID_FACT;
    public static final Column COLUMN_DELIVERABLE_FACT;
    public static final Column COLUMN_CUSTOMS_FACT;
    public static final Column COLUMN_RETURN_FACT;
    public static final Column COLUMN_SENDER_ID_FACT;
    public static final Column COLUMN_RECEIVER_ID_FACT;
    public static final Column COLUMN_DROP_OFF_ID_FACT;
    public static final Column COLUMN_DELIVERY_ID_FACT;
    public static final Column COLUMN_POSTAGE_FACT;
    public static final Column COLUMN_INSURANCE_VALUE_FACT;
    public static final Column COLUMN_WEIGHT_FACT;

    // Static columns - Parcel Type Table
    public static final Column COLUMN_TYPE_ID_TYPE;
    public static final Column COLUMN_TYPE_NAME_TYPE;

    // Static columns - Defect Table
    public static final Column COLUMN_DEFECT_ID_DEFECT;
    public static final Column COLUMN_DEFECT_NAME_DEFECT;

    // Static columns - Address Table
    public static final Column COLUMN_ADDRESS_ID_ADDRESS;
    public static final Column COLUMN_CONTINENT_ADDRESS;
    public static final Column COLUMN_COUNTRY_ADDRESS;
    public static final Column COLUMN_CITY_ADDRESS;
    public static final Column COLUMN_POSTAL_CODE_ADDRESS;
    public static final Column COLUMN_STREET_ADDRESS;

    // Static tables
    public static final PhysicalTable TABLE_PARCELS;
    public static final PhysicalTable TABLE_PARCEL_TYPES;
    public static final PhysicalTable TABLE_DEFECTS;
    public static final PhysicalTable TABLE_ADDRESSES;

    // Static levels
    public static final Level LEVEL_WIDTH;
    public static final Level LEVEL_DEPTH;
    public static final Level LEVEL_HEIGHT;
    public static final Level LEVEL_PARCEL_TYPE;
    public static final Level LEVEL_DEFECT;
    public static final Level LEVEL_DELIVERABLE;
    public static final Level LEVEL_CUSTOMS;
    public static final Level LEVEL_RETURN;
    public static final Level LEVEL_CONTINENT;
    public static final Level LEVEL_COUNTRY;
    public static final Level LEVEL_CITY;
    public static final Level LEVEL_POSTAL_CODE;
    public static final Level LEVEL_STREET;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_WIDTH;
    public static final ExplicitHierarchy HIERARCHY_DEPTH;
    public static final ExplicitHierarchy HIERARCHY_HEIGHT;
    public static final ExplicitHierarchy HIERARCHY_PARCEL_TYPE;
    public static final ExplicitHierarchy HIERARCHY_DEFECT;
    public static final ExplicitHierarchy HIERARCHY_DELIVERABLE;
    public static final ExplicitHierarchy HIERARCHY_CUSTOMS;
    public static final ExplicitHierarchy HIERARCHY_RETURN;
    public static final ExplicitHierarchy HIERARCHY_ADDRESS;

    // Static dimensions
    public static final StandardDimension DIMENSION_WIDTH;
    public static final StandardDimension DIMENSION_DEPTH;
    public static final StandardDimension DIMENSION_HEIGHT;
    public static final StandardDimension DIMENSION_PARCEL_TYPE;
    public static final StandardDimension DIMENSION_DEFECT;
    public static final StandardDimension DIMENSION_DELIVERABLE;
    public static final StandardDimension DIMENSION_CUSTOMS;
    public static final StandardDimension DIMENSION_RETURN;
    public static final StandardDimension DIMENSION_SENDER_ADDRESS;
    public static final StandardDimension DIMENSION_RECEIVER_ADDRESS;
    public static final StandardDimension DIMENSION_DROP_OFF_ADDRESS;
    public static final StandardDimension DIMENSION_DELIVERY_ADDRESS;

    // Static cube
    public static final PhysicalCube CUBE_PARCELS;

    // Static table queries
    public static final TableQuery TABLEQUERY_PARCELS;
    public static final TableQuery TABLEQUERY_PARCEL_TYPES;
    public static final TableQuery TABLEQUERY_DEFECTS;
    public static final TableQuery TABLEQUERY_ADDRESSES;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_WIDTH;
    public static final DimensionConnector CONNECTOR_DEPTH;
    public static final DimensionConnector CONNECTOR_HEIGHT;
    public static final DimensionConnector CONNECTOR_PARCEL_TYPE;
    public static final DimensionConnector CONNECTOR_DEFECT;
    public static final DimensionConnector CONNECTOR_DELIVERABLE;
    public static final DimensionConnector CONNECTOR_CUSTOMS;
    public static final DimensionConnector CONNECTOR_RETURN;
    public static final DimensionConnector CONNECTOR_SENDER_ADDRESS;
    public static final DimensionConnector CONNECTOR_RECEIVER_ADDRESS;
    public static final DimensionConnector CONNECTOR_DROP_OFF_ADDRESS;
    public static final DimensionConnector CONNECTOR_DELIVERY_ADDRESS;

    // Static measures and measure group
    public static final CountMeasure MEASURE_PARCEL_COUNT;
    public static final SumMeasure MEASURE_POSTAGE_SUM;
    public static final SumMeasure MEASURE_INSURANCE_VALUE_SUM;
    public static final MinMeasure MEASURE_WEIGHT_MIN;
    public static final MaxMeasure MEASURE_WEIGHT_MAX;
    public static final AvgMeasure MEASURE_WEIGHT_AVG;
    public static final MeasureGroup MEASUREGROUP_PARCELS;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_PARCEL;
    public static final Catalog CATALOG_PARCEL;

    private static final String parcelBody = """
            Parcel delivery service database for analyzing package logistics and delivery operations.
            Contains parcel data with dimensional analysis for delivery performance optimization
            and logistics management insights.
            """;

    private static final String parcelCubeBody = """
            The Parcel cube contains delivery metrics with dimensional breakdowns for packages.
            Enables analysis by dimensions, addresses, parcel types, and delivery characteristics.
            """;

    private static final String dimensionBody = """
            Physical dimensions enable analysis of package sizes and space utilization
            for logistics optimization and capacity planning.
            """;

    private static final String typeBody = """
            Parcel type dimension categorizes packages by delivery service type
            for service-specific performance analysis.
            """;

    private static final String defectBody = """
            Defect dimension tracks package condition and damage levels
            for quality control and insurance claim analysis.
            """;

    private static final String statusBody = """
            Status dimensions track delivery characteristics like deliverability,
            customs requirements, and return status for operational insights.
            """;

    private static final String addressBody = """
            Address dimension provides geographic hierarchy analysis from continent
            to street level for delivery routing and regional performance tracking.
            """;

    static {
        // Initialize Parcels (Fact) columns
        COLUMN_PARCEL_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_PARCEL_ID_FACT.setName("parcel_id");
        COLUMN_PARCEL_ID_FACT.setId("_column_parcels_parcel_id");
        COLUMN_PARCEL_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_WIDTH_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WIDTH_FACT.setName("width");
        COLUMN_WIDTH_FACT.setId("_column_parcels_width");
        COLUMN_WIDTH_FACT.setType(ColumnType.DECIMAL);

        COLUMN_DEPTH_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEPTH_FACT.setName("depth");
        COLUMN_DEPTH_FACT.setId("_column_parcels_depth");
        COLUMN_DEPTH_FACT.setType(ColumnType.DECIMAL);

        COLUMN_HEIGHT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_HEIGHT_FACT.setName("height");
        COLUMN_HEIGHT_FACT.setId("_column_parcels_height");
        COLUMN_HEIGHT_FACT.setType(ColumnType.DECIMAL);

        COLUMN_TYPE_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TYPE_ID_FACT.setName("type_id");
        COLUMN_TYPE_ID_FACT.setId("_column_parcels_type_id");
        COLUMN_TYPE_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_DEFECT_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEFECT_ID_FACT.setName("defect_id");
        COLUMN_DEFECT_ID_FACT.setId("_column_parcels_defect_id");
        COLUMN_DEFECT_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_DELIVERABLE_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DELIVERABLE_FACT.setName("deliverable");
        COLUMN_DELIVERABLE_FACT.setId("_column_parcels_deliverable");
        COLUMN_DELIVERABLE_FACT.setType(ColumnType.VARCHAR);

        COLUMN_CUSTOMS_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CUSTOMS_FACT.setName("customs");
        COLUMN_CUSTOMS_FACT.setId("_column_parcels_customs");
        COLUMN_CUSTOMS_FACT.setType(ColumnType.VARCHAR);

        COLUMN_RETURN_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_RETURN_FACT.setName("return_status");
        COLUMN_RETURN_FACT.setId("_column_parcels_return");
        COLUMN_RETURN_FACT.setType(ColumnType.VARCHAR);

        COLUMN_SENDER_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_SENDER_ID_FACT.setName("sender_id");
        COLUMN_SENDER_ID_FACT.setId("_column_parcels_sender_id");
        COLUMN_SENDER_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_RECEIVER_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_RECEIVER_ID_FACT.setName("receiver_id");
        COLUMN_RECEIVER_ID_FACT.setId("_column_parcels_receiver_id");
        COLUMN_RECEIVER_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_DROP_OFF_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DROP_OFF_ID_FACT.setName("drop_off_id");
        COLUMN_DROP_OFF_ID_FACT.setId("_column_parcels_drop_off_id");
        COLUMN_DROP_OFF_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_DELIVERY_ID_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DELIVERY_ID_FACT.setName("delivery_id");
        COLUMN_DELIVERY_ID_FACT.setId("_column_parcels_delivery_id");
        COLUMN_DELIVERY_ID_FACT.setType(ColumnType.INTEGER);

        COLUMN_POSTAGE_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSTAGE_FACT.setName("postage");
        COLUMN_POSTAGE_FACT.setId("_column_parcels_postage");
        COLUMN_POSTAGE_FACT.setType(ColumnType.DECIMAL);

        COLUMN_INSURANCE_VALUE_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_INSURANCE_VALUE_FACT.setName("insurance_value");
        COLUMN_INSURANCE_VALUE_FACT.setId("_column_parcels_insurance_value");
        COLUMN_INSURANCE_VALUE_FACT.setType(ColumnType.DECIMAL);

        COLUMN_WEIGHT_FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_WEIGHT_FACT.setName("weight");
        COLUMN_WEIGHT_FACT.setId("_column_parcels_weight");
        COLUMN_WEIGHT_FACT.setType(ColumnType.DECIMAL);

        // Initialize Parcel Type Table columns
        COLUMN_TYPE_ID_TYPE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TYPE_ID_TYPE.setName("type_id");
        COLUMN_TYPE_ID_TYPE.setId("_column_type_type_id");
        COLUMN_TYPE_ID_TYPE.setType(ColumnType.INTEGER);

        COLUMN_TYPE_NAME_TYPE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_TYPE_NAME_TYPE.setName("type_name");
        COLUMN_TYPE_NAME_TYPE.setId("_column_type_type_name");
        COLUMN_TYPE_NAME_TYPE.setType(ColumnType.VARCHAR);

        // Initialize Defect Table columns
        COLUMN_DEFECT_ID_DEFECT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEFECT_ID_DEFECT.setName("defect_id");
        COLUMN_DEFECT_ID_DEFECT.setId("_column_defect_defect_id");
        COLUMN_DEFECT_ID_DEFECT.setType(ColumnType.INTEGER);

        COLUMN_DEFECT_NAME_DEFECT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_DEFECT_NAME_DEFECT.setName("defect_name");
        COLUMN_DEFECT_NAME_DEFECT.setId("_column_defect_defect_name");
        COLUMN_DEFECT_NAME_DEFECT.setType(ColumnType.VARCHAR);

        // Initialize Address Table columns
        COLUMN_ADDRESS_ID_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_ADDRESS_ID_ADDRESS.setName("address_id");
        COLUMN_ADDRESS_ID_ADDRESS.setId("_column_address_address_id");
        COLUMN_ADDRESS_ID_ADDRESS.setType(ColumnType.INTEGER);

        COLUMN_CONTINENT_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CONTINENT_ADDRESS.setName("continent");
        COLUMN_CONTINENT_ADDRESS.setId("_column_address_continent");
        COLUMN_CONTINENT_ADDRESS.setType(ColumnType.VARCHAR);

        COLUMN_COUNTRY_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_COUNTRY_ADDRESS.setName("country");
        COLUMN_COUNTRY_ADDRESS.setId("_column_address_country");
        COLUMN_COUNTRY_ADDRESS.setType(ColumnType.VARCHAR);

        COLUMN_CITY_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_CITY_ADDRESS.setName("city");
        COLUMN_CITY_ADDRESS.setId("_column_address_city");
        COLUMN_CITY_ADDRESS.setType(ColumnType.VARCHAR);

        COLUMN_POSTAL_CODE_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_POSTAL_CODE_ADDRESS.setName("postal_code");
        COLUMN_POSTAL_CODE_ADDRESS.setId("_column_address_postal_code");
        COLUMN_POSTAL_CODE_ADDRESS.setType(ColumnType.VARCHAR);

        COLUMN_STREET_ADDRESS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_STREET_ADDRESS.setName("street");
        COLUMN_STREET_ADDRESS.setId("_column_address_street");
        COLUMN_STREET_ADDRESS.setType(ColumnType.VARCHAR);

        // Initialize tables
        TABLE_PARCELS = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PARCELS.setName("parcels");
        TABLE_PARCELS.setId("_table_parcels");
        TABLE_PARCELS.getColumns()
                .addAll(List.of(COLUMN_PARCEL_ID_FACT, COLUMN_WIDTH_FACT, COLUMN_DEPTH_FACT, COLUMN_HEIGHT_FACT,
                        COLUMN_TYPE_ID_FACT, COLUMN_DEFECT_ID_FACT, COLUMN_DELIVERABLE_FACT, COLUMN_CUSTOMS_FACT,
                        COLUMN_RETURN_FACT, COLUMN_SENDER_ID_FACT, COLUMN_RECEIVER_ID_FACT, COLUMN_DROP_OFF_ID_FACT,
                        COLUMN_DELIVERY_ID_FACT, COLUMN_POSTAGE_FACT, COLUMN_INSURANCE_VALUE_FACT, COLUMN_WEIGHT_FACT));

        TABLE_PARCEL_TYPES = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_PARCEL_TYPES.setName("parcel_types");
        TABLE_PARCEL_TYPES.setId("_table_parcel_types");
        TABLE_PARCEL_TYPES.getColumns().addAll(List.of(COLUMN_TYPE_ID_TYPE, COLUMN_TYPE_NAME_TYPE));

        TABLE_DEFECTS = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_DEFECTS.setName("defects");
        TABLE_DEFECTS.setId("_table_defects");
        TABLE_DEFECTS.getColumns().addAll(List.of(COLUMN_DEFECT_ID_DEFECT, COLUMN_DEFECT_NAME_DEFECT));

        TABLE_ADDRESSES = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_ADDRESSES.setName("addresses");
        TABLE_ADDRESSES.setId("_table_addresses");
        TABLE_ADDRESSES.getColumns().addAll(List.of(COLUMN_ADDRESS_ID_ADDRESS, COLUMN_CONTINENT_ADDRESS,
                COLUMN_COUNTRY_ADDRESS, COLUMN_CITY_ADDRESS, COLUMN_POSTAL_CODE_ADDRESS, COLUMN_STREET_ADDRESS));

        // Initialize levels
        LEVEL_WIDTH = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_WIDTH.setName("Width");
        LEVEL_WIDTH.setColumn(COLUMN_WIDTH_FACT);
        LEVEL_WIDTH.setId("_level_width");

        LEVEL_DEPTH = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_DEPTH.setName("Depth");
        LEVEL_DEPTH.setColumn(COLUMN_DEPTH_FACT);
        LEVEL_DEPTH.setId("_level_depth");

        LEVEL_HEIGHT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_HEIGHT.setName("Height");
        LEVEL_HEIGHT.setColumn(COLUMN_HEIGHT_FACT);
        LEVEL_HEIGHT.setId("_level_height");

        LEVEL_PARCEL_TYPE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_PARCEL_TYPE.setName("Parcel Type");
        LEVEL_PARCEL_TYPE.setColumn(COLUMN_TYPE_ID_TYPE);
        LEVEL_PARCEL_TYPE.setNameColumn(COLUMN_TYPE_NAME_TYPE);
        LEVEL_PARCEL_TYPE.setId("_level_parcel_type");

        LEVEL_DEFECT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_DEFECT.setName("Defect");
        LEVEL_DEFECT.setColumn(COLUMN_DEFECT_ID_DEFECT);
        LEVEL_DEFECT.setNameColumn(COLUMN_DEFECT_NAME_DEFECT);
        LEVEL_DEFECT.setId("_level_defect");

        LEVEL_DELIVERABLE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_DELIVERABLE.setName("Deliverable");
        LEVEL_DELIVERABLE.setColumn(COLUMN_DELIVERABLE_FACT);
        LEVEL_DELIVERABLE.setId("_level_deliverable");

        LEVEL_CUSTOMS = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMS.setName("Customs");
        LEVEL_CUSTOMS.setColumn(COLUMN_CUSTOMS_FACT);
        LEVEL_CUSTOMS.setId("_level_customs");

        LEVEL_RETURN = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_RETURN.setName("Return");
        LEVEL_RETURN.setColumn(COLUMN_RETURN_FACT);
        LEVEL_RETURN.setId("_level_return");

        // Address hierarchy levels
        LEVEL_CONTINENT = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CONTINENT.setName("Continent");
        LEVEL_CONTINENT.setColumn(COLUMN_CONTINENT_ADDRESS);
        LEVEL_CONTINENT.setId("_level_continent");

        LEVEL_COUNTRY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY.setName("Country");
        LEVEL_COUNTRY.setColumn(COLUMN_COUNTRY_ADDRESS);
        LEVEL_COUNTRY.setId("_level_country");

        LEVEL_CITY = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_CITY.setName("City");
        LEVEL_CITY.setColumn(COLUMN_CITY_ADDRESS);
        LEVEL_CITY.setId("_level_city");

        LEVEL_POSTAL_CODE = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_POSTAL_CODE.setName("Postal Code");
        LEVEL_POSTAL_CODE.setColumn(COLUMN_POSTAL_CODE_ADDRESS);
        LEVEL_POSTAL_CODE.setId("_level_postal_code");

        LEVEL_STREET = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_STREET.setName("Street");
        LEVEL_STREET.setColumn(COLUMN_STREET_ADDRESS);
        LEVEL_STREET.setId("_level_street");

        // Initialize table queries
        TABLEQUERY_PARCELS = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PARCELS.setTable(TABLE_PARCELS);
        TABLEQUERY_PARCELS.setId("_query_parcels");

        TABLEQUERY_PARCEL_TYPES = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_PARCEL_TYPES.setTable(TABLE_PARCEL_TYPES);
        TABLEQUERY_PARCEL_TYPES.setId("_query_parcel_types");

        TABLEQUERY_DEFECTS = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_DEFECTS.setTable(TABLE_DEFECTS);
        TABLEQUERY_DEFECTS.setId("_query_defects");

        TABLEQUERY_ADDRESSES = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLEQUERY_ADDRESSES.setTable(TABLE_ADDRESSES);
        TABLEQUERY_ADDRESSES.setId("_query_addresses");

        // Initialize hierarchies
        HIERARCHY_WIDTH = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_WIDTH.setName("Width");
        HIERARCHY_WIDTH.setId("_hierarchy_width");
        HIERARCHY_WIDTH.setHasAll(true);
        HIERARCHY_WIDTH.setAllMemberName("All Widths");
        HIERARCHY_WIDTH.setPrimaryKey(COLUMN_WIDTH_FACT);
        HIERARCHY_WIDTH.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_WIDTH.getLevels().add(LEVEL_WIDTH);

        HIERARCHY_DEPTH = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEPTH.setName("Depth");
        HIERARCHY_DEPTH.setId("_hierarchy_depth");
        HIERARCHY_DEPTH.setHasAll(true);
        HIERARCHY_DEPTH.setAllMemberName("All Depths");
        HIERARCHY_DEPTH.setPrimaryKey(COLUMN_DEPTH_FACT);
        HIERARCHY_DEPTH.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_DEPTH.getLevels().add(LEVEL_DEPTH);

        HIERARCHY_HEIGHT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HEIGHT.setName("Height");
        HIERARCHY_HEIGHT.setId("_hierarchy_height");
        HIERARCHY_HEIGHT.setHasAll(true);
        HIERARCHY_HEIGHT.setAllMemberName("All Heights");
        HIERARCHY_HEIGHT.setPrimaryKey(COLUMN_HEIGHT_FACT);
        HIERARCHY_HEIGHT.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_HEIGHT.getLevels().add(LEVEL_HEIGHT);

        HIERARCHY_PARCEL_TYPE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PARCEL_TYPE.setName("Parcel Type");
        HIERARCHY_PARCEL_TYPE.setId("_hierarchy_parcel_type");
        HIERARCHY_PARCEL_TYPE.setHasAll(true);
        HIERARCHY_PARCEL_TYPE.setAllMemberName("All Types");
        HIERARCHY_PARCEL_TYPE.setPrimaryKey(COLUMN_TYPE_ID_TYPE);
        HIERARCHY_PARCEL_TYPE.setQuery(TABLEQUERY_PARCEL_TYPES);
        HIERARCHY_PARCEL_TYPE.getLevels().add(LEVEL_PARCEL_TYPE);

        HIERARCHY_DEFECT = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEFECT.setName("Defect");
        HIERARCHY_DEFECT.setId("_hierarchy_defect");
        HIERARCHY_DEFECT.setHasAll(true);
        HIERARCHY_DEFECT.setAllMemberName("All Defects");
        HIERARCHY_DEFECT.setPrimaryKey(COLUMN_DEFECT_ID_DEFECT);
        HIERARCHY_DEFECT.setQuery(TABLEQUERY_DEFECTS);
        HIERARCHY_DEFECT.getLevels().add(LEVEL_DEFECT);

        HIERARCHY_DELIVERABLE = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DELIVERABLE.setName("Deliverable");
        HIERARCHY_DELIVERABLE.setId("_hierarchy_deliverable");
        HIERARCHY_DELIVERABLE.setHasAll(true);
        HIERARCHY_DELIVERABLE.setAllMemberName("All Deliverable");
        HIERARCHY_DELIVERABLE.setPrimaryKey(COLUMN_DELIVERABLE_FACT);
        HIERARCHY_DELIVERABLE.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_DELIVERABLE.getLevels().add(LEVEL_DELIVERABLE);

        HIERARCHY_CUSTOMS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMS.setName("Customs");
        HIERARCHY_CUSTOMS.setId("_hierarchy_customs");
        HIERARCHY_CUSTOMS.setHasAll(true);
        HIERARCHY_CUSTOMS.setAllMemberName("All Customs");
        HIERARCHY_CUSTOMS.setPrimaryKey(COLUMN_CUSTOMS_FACT);
        HIERARCHY_CUSTOMS.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_CUSTOMS.getLevels().add(LEVEL_CUSTOMS);

        HIERARCHY_RETURN = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_RETURN.setName("Return");
        HIERARCHY_RETURN.setId("_hierarchy_return");
        HIERARCHY_RETURN.setHasAll(true);
        HIERARCHY_RETURN.setAllMemberName("All Return");
        HIERARCHY_RETURN.setPrimaryKey(COLUMN_RETURN_FACT);
        HIERARCHY_RETURN.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_RETURN.getLevels().add(LEVEL_RETURN);

        HIERARCHY_ADDRESS = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ADDRESS.setName("Geographic Address");
        HIERARCHY_ADDRESS.setId("_hierarchy_address");
        HIERARCHY_ADDRESS.setHasAll(true);
        HIERARCHY_ADDRESS.setAllMemberName("All Addresses");
        HIERARCHY_ADDRESS.setPrimaryKey(COLUMN_ADDRESS_ID_ADDRESS);
        HIERARCHY_ADDRESS.setQuery(TABLEQUERY_ADDRESSES);
        HIERARCHY_ADDRESS.getLevels()
                .addAll(List.of(LEVEL_CONTINENT, LEVEL_COUNTRY, LEVEL_CITY, LEVEL_POSTAL_CODE, LEVEL_STREET));

        // Initialize dimensions
        DIMENSION_WIDTH = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_WIDTH.setName("Width");
        DIMENSION_WIDTH.setId("_dimension_width");
        DIMENSION_WIDTH.getHierarchies().add(HIERARCHY_WIDTH);

        DIMENSION_DEPTH = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEPTH.setName("Depth");
        DIMENSION_DEPTH.setId("_dimension_depth");
        DIMENSION_DEPTH.getHierarchies().add(HIERARCHY_DEPTH);

        DIMENSION_HEIGHT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_HEIGHT.setName("Height");
        DIMENSION_HEIGHT.setId("_dimension_height");
        DIMENSION_HEIGHT.getHierarchies().add(HIERARCHY_HEIGHT);

        DIMENSION_PARCEL_TYPE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PARCEL_TYPE.setName("Parcel Type");
        DIMENSION_PARCEL_TYPE.setId("_dimension_parcel_type");
        DIMENSION_PARCEL_TYPE.getHierarchies().add(HIERARCHY_PARCEL_TYPE);

        DIMENSION_DEFECT = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEFECT.setName("Defect");
        DIMENSION_DEFECT.setId("_dimension_defect");
        DIMENSION_DEFECT.getHierarchies().add(HIERARCHY_DEFECT);

        DIMENSION_DELIVERABLE = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DELIVERABLE.setName("Deliverable");
        DIMENSION_DELIVERABLE.setId("_dimension_deliverable");
        DIMENSION_DELIVERABLE.getHierarchies().add(HIERARCHY_DELIVERABLE);

        DIMENSION_CUSTOMS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMS.setName("Customs");
        DIMENSION_CUSTOMS.setId("_dimension_customs");
        DIMENSION_CUSTOMS.getHierarchies().add(HIERARCHY_CUSTOMS);

        DIMENSION_RETURN = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_RETURN.setName("Return");
        DIMENSION_RETURN.setId("_dimension_return");
        DIMENSION_RETURN.getHierarchies().add(HIERARCHY_RETURN);

        DIMENSION_SENDER_ADDRESS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SENDER_ADDRESS.setName("Sender Address");
        DIMENSION_SENDER_ADDRESS.setId("_dimension_sender_address");
        DIMENSION_SENDER_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_RECEIVER_ADDRESS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_RECEIVER_ADDRESS.setName("Receiver Address");
        DIMENSION_RECEIVER_ADDRESS.setId("_dimension_receiver_address");
        DIMENSION_RECEIVER_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_DROP_OFF_ADDRESS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DROP_OFF_ADDRESS.setName("Drop Off Address");
        DIMENSION_DROP_OFF_ADDRESS.setId("_dimension_drop_off_address");
        DIMENSION_DROP_OFF_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_DELIVERY_ADDRESS = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DELIVERY_ADDRESS.setName("Delivery Address");
        DIMENSION_DELIVERY_ADDRESS.setId("_dimension_delivery_address");
        DIMENSION_DELIVERY_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        // Initialize dimension connectors
        CONNECTOR_WIDTH = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WIDTH.setDimension(DIMENSION_WIDTH);
        CONNECTOR_WIDTH.setForeignKey(COLUMN_WIDTH_FACT);
        CONNECTOR_WIDTH.setId("_connector_width");
        CONNECTOR_WIDTH.setOverrideDimensionName("Width");

        CONNECTOR_DEPTH = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DEPTH.setDimension(DIMENSION_DEPTH);
        CONNECTOR_DEPTH.setForeignKey(COLUMN_DEPTH_FACT);
        CONNECTOR_DEPTH.setId("_connector_depth");
        CONNECTOR_DEPTH.setOverrideDimensionName("Depth");

        CONNECTOR_HEIGHT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HEIGHT.setDimension(DIMENSION_HEIGHT);
        CONNECTOR_HEIGHT.setForeignKey(COLUMN_HEIGHT_FACT);
        CONNECTOR_HEIGHT.setId("_connector_height");
        CONNECTOR_HEIGHT.setOverrideDimensionName("Height");

        CONNECTOR_PARCEL_TYPE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PARCEL_TYPE.setDimension(DIMENSION_PARCEL_TYPE);
        CONNECTOR_PARCEL_TYPE.setForeignKey(COLUMN_TYPE_ID_FACT);
        CONNECTOR_PARCEL_TYPE.setId("_connector_parcel_type");
        CONNECTOR_PARCEL_TYPE.setOverrideDimensionName("Parcel Type");

        CONNECTOR_DEFECT = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DEFECT.setDimension(DIMENSION_DEFECT);
        CONNECTOR_DEFECT.setForeignKey(COLUMN_DEFECT_ID_FACT);
        CONNECTOR_DEFECT.setId("_connector_defect");
        CONNECTOR_DEFECT.setOverrideDimensionName("Defect");

        CONNECTOR_DELIVERABLE = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DELIVERABLE.setDimension(DIMENSION_DELIVERABLE);
        CONNECTOR_DELIVERABLE.setForeignKey(COLUMN_DELIVERABLE_FACT);
        CONNECTOR_DELIVERABLE.setId("_connector_deliverable");
        CONNECTOR_DELIVERABLE.setOverrideDimensionName("Deliverable");

        CONNECTOR_CUSTOMS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMS.setDimension(DIMENSION_CUSTOMS);
        CONNECTOR_CUSTOMS.setForeignKey(COLUMN_CUSTOMS_FACT);
        CONNECTOR_CUSTOMS.setId("_connector_customs");
        CONNECTOR_CUSTOMS.setOverrideDimensionName("Customs");

        CONNECTOR_RETURN = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_RETURN.setDimension(DIMENSION_RETURN);
        CONNECTOR_RETURN.setForeignKey(COLUMN_RETURN_FACT);
        CONNECTOR_RETURN.setId("_connector_return");
        CONNECTOR_RETURN.setOverrideDimensionName("Return");

        CONNECTOR_SENDER_ADDRESS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SENDER_ADDRESS.setDimension(DIMENSION_SENDER_ADDRESS);
        CONNECTOR_SENDER_ADDRESS.setForeignKey(COLUMN_SENDER_ID_FACT);
        CONNECTOR_SENDER_ADDRESS.setId("_connector_sender_address");
        CONNECTOR_SENDER_ADDRESS.setOverrideDimensionName("Sender Address");

        CONNECTOR_RECEIVER_ADDRESS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_RECEIVER_ADDRESS.setDimension(DIMENSION_RECEIVER_ADDRESS);
        CONNECTOR_RECEIVER_ADDRESS.setForeignKey(COLUMN_RECEIVER_ID_FACT);
        CONNECTOR_RECEIVER_ADDRESS.setId("_connector_receiver_address");
        CONNECTOR_RECEIVER_ADDRESS.setOverrideDimensionName("Receiver Address");

        CONNECTOR_DROP_OFF_ADDRESS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DROP_OFF_ADDRESS.setDimension(DIMENSION_DROP_OFF_ADDRESS);
        CONNECTOR_DROP_OFF_ADDRESS.setForeignKey(COLUMN_DROP_OFF_ID_FACT);
        CONNECTOR_DROP_OFF_ADDRESS.setId("_connector_drop_off_address");
        CONNECTOR_DROP_OFF_ADDRESS.setOverrideDimensionName("Drop Off Address");

        CONNECTOR_DELIVERY_ADDRESS = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DELIVERY_ADDRESS.setDimension(DIMENSION_DELIVERY_ADDRESS);
        CONNECTOR_DELIVERY_ADDRESS.setForeignKey(COLUMN_DELIVERY_ID_FACT);
        CONNECTOR_DELIVERY_ADDRESS.setId("_connector_delivery_address");
        CONNECTOR_DELIVERY_ADDRESS.setOverrideDimensionName("Delivery Address");

        // Initialize measures
        MEASURE_PARCEL_COUNT = RolapMappingFactory.eINSTANCE.createCountMeasure();
        MEASURE_PARCEL_COUNT.setName("Parcel Count");
        MEASURE_PARCEL_COUNT.setId("_measure_parcel_count");
        MEASURE_PARCEL_COUNT.setColumn(COLUMN_PARCEL_ID_FACT);
        MEASURE_PARCEL_COUNT.setFormatString("#,###");

        MEASURE_POSTAGE_SUM = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_POSTAGE_SUM.setName("Postage Sum");
        MEASURE_POSTAGE_SUM.setId("_measure_postage_sum");
        MEASURE_POSTAGE_SUM.setColumn(COLUMN_POSTAGE_FACT);
        MEASURE_POSTAGE_SUM.setFormatString("#,##0.00");

        MEASURE_INSURANCE_VALUE_SUM = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_INSURANCE_VALUE_SUM.setName("Insurance Value Sum");
        MEASURE_INSURANCE_VALUE_SUM.setId("_measure_insurance_value_sum");
        MEASURE_INSURANCE_VALUE_SUM.setColumn(COLUMN_INSURANCE_VALUE_FACT);
        MEASURE_INSURANCE_VALUE_SUM.setFormatString("#,##0.00");

        MEASURE_WEIGHT_MIN = RolapMappingFactory.eINSTANCE.createMinMeasure();
        MEASURE_WEIGHT_MIN.setName("Weight Min");
        MEASURE_WEIGHT_MIN.setId("_measure_weight_min");
        MEASURE_WEIGHT_MIN.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_MIN.setFormatString("#,##0.00");

        MEASURE_WEIGHT_MAX = RolapMappingFactory.eINSTANCE.createMaxMeasure();
        MEASURE_WEIGHT_MAX.setName("Weight Max");
        MEASURE_WEIGHT_MAX.setId("_measure_weight_max");
        MEASURE_WEIGHT_MAX.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_MAX.setFormatString("#,##0.00");

        MEASURE_WEIGHT_AVG = RolapMappingFactory.eINSTANCE.createAvgMeasure();
        MEASURE_WEIGHT_AVG.setName("Weight Avg");
        MEASURE_WEIGHT_AVG.setId("_measure_weight_avg");
        MEASURE_WEIGHT_AVG.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_AVG.setFormatString("#,##0.00");

        MEASUREGROUP_PARCELS = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_PARCELS.getMeasures().addAll(List.of(MEASURE_PARCEL_COUNT, MEASURE_POSTAGE_SUM,
                MEASURE_INSURANCE_VALUE_SUM, MEASURE_WEIGHT_MIN, MEASURE_WEIGHT_MAX, MEASURE_WEIGHT_AVG));

        // Initialize cube
        CUBE_PARCELS = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_PARCELS.setName("Parcels");
        CUBE_PARCELS.setId("_cube_parcels");
        CUBE_PARCELS.setQuery(TABLEQUERY_PARCELS);
        CUBE_PARCELS.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_WIDTH, CONNECTOR_DEPTH, CONNECTOR_HEIGHT, CONNECTOR_PARCEL_TYPE,
                        CONNECTOR_DEFECT, CONNECTOR_DELIVERABLE, CONNECTOR_CUSTOMS, CONNECTOR_RETURN,
                        CONNECTOR_SENDER_ADDRESS, CONNECTOR_RECEIVER_ADDRESS, CONNECTOR_DROP_OFF_ADDRESS,
                        CONNECTOR_DELIVERY_ADDRESS));
        CUBE_PARCELS.getMeasureGroups().add(MEASUREGROUP_PARCELS);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_PARCEL = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA_PARCEL.setId("_databaseSchema_parcel");
        DATABASE_SCHEMA_PARCEL.getTables()
                .addAll(List.of(TABLE_PARCELS, TABLE_PARCEL_TYPES, TABLE_DEFECTS, TABLE_ADDRESSES));

        CATALOG_PARCEL = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_PARCEL.setName("Parcel Delivery Service");
        CATALOG_PARCEL.setDescription("Parcel delivery service logistics database - EMF Version");
        CATALOG_PARCEL.setId("_catalog_parcel");
        CATALOG_PARCEL.getDbschemas().add(DATABASE_SCHEMA_PARCEL);
        CATALOG_PARCEL.getCubes().add(CUBE_PARCELS);

        // Add documentation
        document(CATALOG_PARCEL, "Parcel Database", parcelBody, 1, 0, 0, false, 0);
        document(CUBE_PARCELS, "Parcel Cube", parcelCubeBody, 1, 1, 0, true, 0);
        document(DIMENSION_WIDTH, "Width Dimension", dimensionBody, 1, 2, 0, true, 0);
        document(DIMENSION_DEPTH, "Depth Dimension", dimensionBody, 1, 3, 0, true, 0);
        document(DIMENSION_HEIGHT, "Height Dimension", dimensionBody, 1, 4, 0, true, 0);
        document(DIMENSION_PARCEL_TYPE, "Parcel Type Dimension", typeBody, 1, 5, 0, true, 0);
        document(DIMENSION_DEFECT, "Defect Dimension", defectBody, 1, 6, 0, true, 0);
        document(DIMENSION_DELIVERABLE, "Deliverable Dimension", statusBody, 1, 7, 0, true, 0);
        document(DIMENSION_CUSTOMS, "Customs Dimension", statusBody, 1, 8, 0, true, 0);
        document(DIMENSION_RETURN, "Return Dimension", statusBody, 1, 9, 0, true, 0);
        document(DIMENSION_SENDER_ADDRESS, "Sender Address Dimension", addressBody, 1, 10, 0, true, 0);
        document(DIMENSION_RECEIVER_ADDRESS, "Receiver Address Dimension", addressBody, 1, 11, 0, true, 0);
        document(DIMENSION_DROP_OFF_ADDRESS, "Drop Off Address Dimension", addressBody, 1, 12, 0, true, 0);
        document(DIMENSION_DELIVERY_ADDRESS, "Delivery Address Dimension", addressBody, 1, 13, 0, true, 0);
    }

    @Override
    public CatalogMapping get() {
        return CATALOG_PARCEL;
    }

}
