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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.CountMeasure;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MaxMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MinMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.6", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    public static final PhysicalCube CUBE_PARCELS;
    public static final StandardDimension DIMENSION_PARCEL_TYPE;
    public static final StandardDimension DIMENSION_RECEIVER_ADDRESS;
    public static final StandardDimension DIMENSION_HEIGHT;
    public static final Catalog CATALOG_PARCEL;
    public static final StandardDimension DIMENSION_CUSTOMS;
    public static final StandardDimension DIMENSION_DELIVERY_ADDRESS;
    public static final StandardDimension DIMENSION_DEPTH;
    public static final StandardDimension DIMENSION_DROP_OFF_ADDRESS;
    public static final StandardDimension DIMENSION_RETURN;
    public static final StandardDimension DIMENSION_SENDER_ADDRESS;
    public static final StandardDimension DIMENSION_WIDTH;
    public static final StandardDimension DIMENSION_DEFECT;
    public static final StandardDimension DIMENSION_DELIVERABLE;


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
    public static final Table TABLE_PARCELS;
    public static final Table TABLE_PARCEL_TYPES;
    public static final Table TABLE_DEFECTS;
    public static final Table TABLE_ADDRESSES;

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
    // field assignment only: DIMENSION_WIDTH
    // field assignment only: DIMENSION_DEPTH
    // field assignment only: DIMENSION_HEIGHT
    // field assignment only: DIMENSION_PARCEL_TYPE
    // field assignment only: DIMENSION_DEFECT
    // field assignment only: DIMENSION_DELIVERABLE
    // field assignment only: DIMENSION_CUSTOMS
    // field assignment only: DIMENSION_RETURN
    // field assignment only: DIMENSION_SENDER_ADDRESS
    // field assignment only: DIMENSION_RECEIVER_ADDRESS
    // field assignment only: DIMENSION_DROP_OFF_ADDRESS
    // field assignment only: DIMENSION_DELIVERY_ADDRESS

    // Static cube
    // field assignment only: CUBE_PARCELS

    // Static table queries
    public static final TableSource TABLEQUERY_PARCELS;
    public static final TableSource TABLEQUERY_PARCEL_TYPES;
    public static final TableSource TABLEQUERY_DEFECTS;
    public static final TableSource TABLEQUERY_ADDRESSES;

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
    public static final Schema DATABASE_SCHEMA_PARCEL;
    // field assignment only: CATALOG_PARCEL

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
        COLUMN_PARCEL_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_PARCEL_ID_FACT.setName("parcel_id");
        COLUMN_PARCEL_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_WIDTH_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WIDTH_FACT.setName("width");
        COLUMN_WIDTH_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_DEPTH_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEPTH_FACT.setName("depth");
        COLUMN_DEPTH_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_HEIGHT_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_HEIGHT_FACT.setName("height");
        COLUMN_HEIGHT_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_TYPE_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TYPE_ID_FACT.setName("type_id");
        COLUMN_TYPE_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DEFECT_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEFECT_ID_FACT.setName("defect_id");
        COLUMN_DEFECT_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DELIVERABLE_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DELIVERABLE_FACT.setName("deliverable");
        COLUMN_DELIVERABLE_FACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CUSTOMS_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CUSTOMS_FACT.setName("customs");
        COLUMN_CUSTOMS_FACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_RETURN_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_RETURN_FACT.setName("return_status");
        COLUMN_RETURN_FACT.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_SENDER_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_SENDER_ID_FACT.setName("sender_id");
        COLUMN_SENDER_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_RECEIVER_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_RECEIVER_ID_FACT.setName("receiver_id");
        COLUMN_RECEIVER_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DROP_OFF_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DROP_OFF_ID_FACT.setName("drop_off_id");
        COLUMN_DROP_OFF_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DELIVERY_ID_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DELIVERY_ID_FACT.setName("delivery_id");
        COLUMN_DELIVERY_ID_FACT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_POSTAGE_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSTAGE_FACT.setName("postage");
        COLUMN_POSTAGE_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_INSURANCE_VALUE_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_INSURANCE_VALUE_FACT.setName("insurance_value");
        COLUMN_INSURANCE_VALUE_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        COLUMN_WEIGHT_FACT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_WEIGHT_FACT.setName("weight");
        COLUMN_WEIGHT_FACT.setType(SqlSimpleTypes.decimalType(18, 4));

        // Initialize Parcel Type Table columns
        COLUMN_TYPE_ID_TYPE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TYPE_ID_TYPE.setName("type_id");
        COLUMN_TYPE_ID_TYPE.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_TYPE_NAME_TYPE = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_TYPE_NAME_TYPE.setName("type_name");
        COLUMN_TYPE_NAME_TYPE.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Defect Table columns
        COLUMN_DEFECT_ID_DEFECT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEFECT_ID_DEFECT.setName("defect_id");
        COLUMN_DEFECT_ID_DEFECT.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_DEFECT_NAME_DEFECT = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_DEFECT_NAME_DEFECT.setName("defect_name");
        COLUMN_DEFECT_NAME_DEFECT.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize Address Table columns
        COLUMN_ADDRESS_ID_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ADDRESS_ID_ADDRESS.setName("address_id");
        COLUMN_ADDRESS_ID_ADDRESS.setType(SqlSimpleTypes.Sql99.integerType());

        COLUMN_CONTINENT_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CONTINENT_ADDRESS.setName("continent");
        COLUMN_CONTINENT_ADDRESS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_COUNTRY_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_COUNTRY_ADDRESS.setName("country");
        COLUMN_COUNTRY_ADDRESS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_CITY_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_CITY_ADDRESS.setName("city");
        COLUMN_CITY_ADDRESS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_POSTAL_CODE_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_POSTAL_CODE_ADDRESS.setName("postal_code");
        COLUMN_POSTAL_CODE_ADDRESS.setType(SqlSimpleTypes.Sql99.varcharType());

        COLUMN_STREET_ADDRESS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        COLUMN_STREET_ADDRESS.setName("street");
        COLUMN_STREET_ADDRESS.setType(SqlSimpleTypes.Sql99.varcharType());

        // Initialize tables
        TABLE_PARCELS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PARCELS.setName("parcels");
        TABLE_PARCELS.getFeature()
                .addAll(List.of(COLUMN_PARCEL_ID_FACT, COLUMN_WIDTH_FACT, COLUMN_DEPTH_FACT, COLUMN_HEIGHT_FACT,
                        COLUMN_TYPE_ID_FACT, COLUMN_DEFECT_ID_FACT, COLUMN_DELIVERABLE_FACT, COLUMN_CUSTOMS_FACT,
                        COLUMN_RETURN_FACT, COLUMN_SENDER_ID_FACT, COLUMN_RECEIVER_ID_FACT, COLUMN_DROP_OFF_ID_FACT,
                        COLUMN_DELIVERY_ID_FACT, COLUMN_POSTAGE_FACT, COLUMN_INSURANCE_VALUE_FACT, COLUMN_WEIGHT_FACT));

        TABLE_PARCEL_TYPES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_PARCEL_TYPES.setName("parcel_types");
        TABLE_PARCEL_TYPES.getFeature().addAll(List.of(COLUMN_TYPE_ID_TYPE, COLUMN_TYPE_NAME_TYPE));

        TABLE_DEFECTS = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_DEFECTS.setName("defects");
        TABLE_DEFECTS.getFeature().addAll(List.of(COLUMN_DEFECT_ID_DEFECT, COLUMN_DEFECT_NAME_DEFECT));

        TABLE_ADDRESSES = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        TABLE_ADDRESSES.setName("addresses");
        TABLE_ADDRESSES.getFeature().addAll(List.of(COLUMN_ADDRESS_ID_ADDRESS, COLUMN_CONTINENT_ADDRESS,
                COLUMN_COUNTRY_ADDRESS, COLUMN_CITY_ADDRESS, COLUMN_POSTAL_CODE_ADDRESS, COLUMN_STREET_ADDRESS));

        // Initialize levels
        LEVEL_WIDTH = LevelFactory.eINSTANCE.createLevel();
        LEVEL_WIDTH.setName("Width");
        LEVEL_WIDTH.setColumn(COLUMN_WIDTH_FACT);

        LEVEL_DEPTH = LevelFactory.eINSTANCE.createLevel();
        LEVEL_DEPTH.setName("Depth");
        LEVEL_DEPTH.setColumn(COLUMN_DEPTH_FACT);

        LEVEL_HEIGHT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_HEIGHT.setName("Height");
        LEVEL_HEIGHT.setColumn(COLUMN_HEIGHT_FACT);

        LEVEL_PARCEL_TYPE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_PARCEL_TYPE.setName("Parcel Type");
        LEVEL_PARCEL_TYPE.setColumn(COLUMN_TYPE_ID_TYPE);
        LEVEL_PARCEL_TYPE.setNameColumn(COLUMN_TYPE_NAME_TYPE);

        LEVEL_DEFECT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_DEFECT.setName("Defect");
        LEVEL_DEFECT.setColumn(COLUMN_DEFECT_ID_DEFECT);
        LEVEL_DEFECT.setNameColumn(COLUMN_DEFECT_NAME_DEFECT);

        LEVEL_DELIVERABLE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_DELIVERABLE.setName("Deliverable");
        LEVEL_DELIVERABLE.setColumn(COLUMN_DELIVERABLE_FACT);

        LEVEL_CUSTOMS = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CUSTOMS.setName("Customs");
        LEVEL_CUSTOMS.setColumn(COLUMN_CUSTOMS_FACT);

        LEVEL_RETURN = LevelFactory.eINSTANCE.createLevel();
        LEVEL_RETURN.setName("Return");
        LEVEL_RETURN.setColumn(COLUMN_RETURN_FACT);

        // Address hierarchy levels
        LEVEL_CONTINENT = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CONTINENT.setName("Continent");
        LEVEL_CONTINENT.setColumn(COLUMN_CONTINENT_ADDRESS);

        LEVEL_COUNTRY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_COUNTRY.setName("Country");
        LEVEL_COUNTRY.setColumn(COLUMN_COUNTRY_ADDRESS);

        LEVEL_CITY = LevelFactory.eINSTANCE.createLevel();
        LEVEL_CITY.setName("City");
        LEVEL_CITY.setColumn(COLUMN_CITY_ADDRESS);

        LEVEL_POSTAL_CODE = LevelFactory.eINSTANCE.createLevel();
        LEVEL_POSTAL_CODE.setName("Postal Code");
        LEVEL_POSTAL_CODE.setColumn(COLUMN_POSTAL_CODE_ADDRESS);

        LEVEL_STREET = LevelFactory.eINSTANCE.createLevel();
        LEVEL_STREET.setName("Street");
        LEVEL_STREET.setColumn(COLUMN_STREET_ADDRESS);

        // Initialize table queries
        TABLEQUERY_PARCELS = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_PARCELS.setTable(TABLE_PARCELS);

        TABLEQUERY_PARCEL_TYPES = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_PARCEL_TYPES.setTable(TABLE_PARCEL_TYPES);

        TABLEQUERY_DEFECTS = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_DEFECTS.setTable(TABLE_DEFECTS);

        TABLEQUERY_ADDRESSES = SourceFactory.eINSTANCE.createTableSource();
        TABLEQUERY_ADDRESSES.setTable(TABLE_ADDRESSES);

        // Initialize hierarchies
        HIERARCHY_WIDTH = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_WIDTH.setName("Width");
        HIERARCHY_WIDTH.setHasAll(true);
        HIERARCHY_WIDTH.setAllMemberName("All Widths");
        HIERARCHY_WIDTH.setPrimaryKey(COLUMN_WIDTH_FACT);
        HIERARCHY_WIDTH.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_WIDTH.getLevels().add(LEVEL_WIDTH);

        HIERARCHY_DEPTH = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEPTH.setName("Depth");
        HIERARCHY_DEPTH.setHasAll(true);
        HIERARCHY_DEPTH.setAllMemberName("All Depths");
        HIERARCHY_DEPTH.setPrimaryKey(COLUMN_DEPTH_FACT);
        HIERARCHY_DEPTH.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_DEPTH.getLevels().add(LEVEL_DEPTH);

        HIERARCHY_HEIGHT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_HEIGHT.setName("Height");
        HIERARCHY_HEIGHT.setHasAll(true);
        HIERARCHY_HEIGHT.setAllMemberName("All Heights");
        HIERARCHY_HEIGHT.setPrimaryKey(COLUMN_HEIGHT_FACT);
        HIERARCHY_HEIGHT.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_HEIGHT.getLevels().add(LEVEL_HEIGHT);

        HIERARCHY_PARCEL_TYPE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_PARCEL_TYPE.setName("Parcel Type");
        HIERARCHY_PARCEL_TYPE.setHasAll(true);
        HIERARCHY_PARCEL_TYPE.setAllMemberName("All Types");
        HIERARCHY_PARCEL_TYPE.setPrimaryKey(COLUMN_TYPE_ID_TYPE);
        HIERARCHY_PARCEL_TYPE.setQuery(TABLEQUERY_PARCEL_TYPES);
        HIERARCHY_PARCEL_TYPE.getLevels().add(LEVEL_PARCEL_TYPE);

        HIERARCHY_DEFECT = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DEFECT.setName("Defect");
        HIERARCHY_DEFECT.setHasAll(true);
        HIERARCHY_DEFECT.setAllMemberName("All Defects");
        HIERARCHY_DEFECT.setPrimaryKey(COLUMN_DEFECT_ID_DEFECT);
        HIERARCHY_DEFECT.setQuery(TABLEQUERY_DEFECTS);
        HIERARCHY_DEFECT.getLevels().add(LEVEL_DEFECT);

        HIERARCHY_DELIVERABLE = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_DELIVERABLE.setName("Deliverable");
        HIERARCHY_DELIVERABLE.setHasAll(true);
        HIERARCHY_DELIVERABLE.setAllMemberName("All Deliverable");
        HIERARCHY_DELIVERABLE.setPrimaryKey(COLUMN_DELIVERABLE_FACT);
        HIERARCHY_DELIVERABLE.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_DELIVERABLE.getLevels().add(LEVEL_DELIVERABLE);

        HIERARCHY_CUSTOMS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_CUSTOMS.setName("Customs");
        HIERARCHY_CUSTOMS.setHasAll(true);
        HIERARCHY_CUSTOMS.setAllMemberName("All Customs");
        HIERARCHY_CUSTOMS.setPrimaryKey(COLUMN_CUSTOMS_FACT);
        HIERARCHY_CUSTOMS.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_CUSTOMS.getLevels().add(LEVEL_CUSTOMS);

        HIERARCHY_RETURN = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_RETURN.setName("Return");
        HIERARCHY_RETURN.setHasAll(true);
        HIERARCHY_RETURN.setAllMemberName("All Return");
        HIERARCHY_RETURN.setPrimaryKey(COLUMN_RETURN_FACT);
        HIERARCHY_RETURN.setQuery(TABLEQUERY_PARCELS);
        HIERARCHY_RETURN.getLevels().add(LEVEL_RETURN);

        HIERARCHY_ADDRESS = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_ADDRESS.setName("Geographic Address");
        HIERARCHY_ADDRESS.setHasAll(true);
        HIERARCHY_ADDRESS.setAllMemberName("All Addresses");
        HIERARCHY_ADDRESS.setPrimaryKey(COLUMN_ADDRESS_ID_ADDRESS);
        HIERARCHY_ADDRESS.setQuery(TABLEQUERY_ADDRESSES);
        HIERARCHY_ADDRESS.getLevels()
                .addAll(List.of(LEVEL_CONTINENT, LEVEL_COUNTRY, LEVEL_CITY, LEVEL_POSTAL_CODE, LEVEL_STREET));

        // Initialize dimensions
        DIMENSION_WIDTH = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_WIDTH.setName("Width");
        DIMENSION_WIDTH.getHierarchies().add(HIERARCHY_WIDTH);

        DIMENSION_DEPTH = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEPTH.setName("Depth");
        DIMENSION_DEPTH.getHierarchies().add(HIERARCHY_DEPTH);

        DIMENSION_HEIGHT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_HEIGHT.setName("Height");
        DIMENSION_HEIGHT.getHierarchies().add(HIERARCHY_HEIGHT);

        DIMENSION_PARCEL_TYPE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_PARCEL_TYPE.setName("Parcel Type");
        DIMENSION_PARCEL_TYPE.getHierarchies().add(HIERARCHY_PARCEL_TYPE);

        DIMENSION_DEFECT = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DEFECT.setName("Defect");
        DIMENSION_DEFECT.getHierarchies().add(HIERARCHY_DEFECT);

        DIMENSION_DELIVERABLE = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DELIVERABLE.setName("Deliverable");
        DIMENSION_DELIVERABLE.getHierarchies().add(HIERARCHY_DELIVERABLE);

        DIMENSION_CUSTOMS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_CUSTOMS.setName("Customs");
        DIMENSION_CUSTOMS.getHierarchies().add(HIERARCHY_CUSTOMS);

        DIMENSION_RETURN = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_RETURN.setName("Return");
        DIMENSION_RETURN.getHierarchies().add(HIERARCHY_RETURN);

        DIMENSION_SENDER_ADDRESS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SENDER_ADDRESS.setName("Sender Address");
        DIMENSION_SENDER_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_RECEIVER_ADDRESS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_RECEIVER_ADDRESS.setName("Receiver Address");
        DIMENSION_RECEIVER_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_DROP_OFF_ADDRESS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DROP_OFF_ADDRESS.setName("Drop Off Address");
        DIMENSION_DROP_OFF_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        DIMENSION_DELIVERY_ADDRESS = DimensionFactory.eINSTANCE.createStandardDimension();
        DIMENSION_DELIVERY_ADDRESS.setName("Delivery Address");
        DIMENSION_DELIVERY_ADDRESS.getHierarchies().add(HIERARCHY_ADDRESS);

        // Initialize dimension connectors
        CONNECTOR_WIDTH = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_WIDTH.setDimension(DIMENSION_WIDTH);
        CONNECTOR_WIDTH.setForeignKey(COLUMN_WIDTH_FACT);
        CONNECTOR_WIDTH.setOverrideDimensionName("Width");

        CONNECTOR_DEPTH = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DEPTH.setDimension(DIMENSION_DEPTH);
        CONNECTOR_DEPTH.setForeignKey(COLUMN_DEPTH_FACT);
        CONNECTOR_DEPTH.setOverrideDimensionName("Depth");

        CONNECTOR_HEIGHT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_HEIGHT.setDimension(DIMENSION_HEIGHT);
        CONNECTOR_HEIGHT.setForeignKey(COLUMN_HEIGHT_FACT);
        CONNECTOR_HEIGHT.setOverrideDimensionName("Height");

        CONNECTOR_PARCEL_TYPE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_PARCEL_TYPE.setDimension(DIMENSION_PARCEL_TYPE);
        CONNECTOR_PARCEL_TYPE.setForeignKey(COLUMN_TYPE_ID_FACT);
        CONNECTOR_PARCEL_TYPE.setOverrideDimensionName("Parcel Type");

        CONNECTOR_DEFECT = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DEFECT.setDimension(DIMENSION_DEFECT);
        CONNECTOR_DEFECT.setForeignKey(COLUMN_DEFECT_ID_FACT);
        CONNECTOR_DEFECT.setOverrideDimensionName("Defect");

        CONNECTOR_DELIVERABLE = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DELIVERABLE.setDimension(DIMENSION_DELIVERABLE);
        CONNECTOR_DELIVERABLE.setForeignKey(COLUMN_DELIVERABLE_FACT);
        CONNECTOR_DELIVERABLE.setOverrideDimensionName("Deliverable");

        CONNECTOR_CUSTOMS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_CUSTOMS.setDimension(DIMENSION_CUSTOMS);
        CONNECTOR_CUSTOMS.setForeignKey(COLUMN_CUSTOMS_FACT);
        CONNECTOR_CUSTOMS.setOverrideDimensionName("Customs");

        CONNECTOR_RETURN = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_RETURN.setDimension(DIMENSION_RETURN);
        CONNECTOR_RETURN.setForeignKey(COLUMN_RETURN_FACT);
        CONNECTOR_RETURN.setOverrideDimensionName("Return");

        CONNECTOR_SENDER_ADDRESS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_SENDER_ADDRESS.setDimension(DIMENSION_SENDER_ADDRESS);
        CONNECTOR_SENDER_ADDRESS.setForeignKey(COLUMN_SENDER_ID_FACT);
        CONNECTOR_SENDER_ADDRESS.setOverrideDimensionName("Sender Address");

        CONNECTOR_RECEIVER_ADDRESS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_RECEIVER_ADDRESS.setDimension(DIMENSION_RECEIVER_ADDRESS);
        CONNECTOR_RECEIVER_ADDRESS.setForeignKey(COLUMN_RECEIVER_ID_FACT);
        CONNECTOR_RECEIVER_ADDRESS.setOverrideDimensionName("Receiver Address");

        CONNECTOR_DROP_OFF_ADDRESS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DROP_OFF_ADDRESS.setDimension(DIMENSION_DROP_OFF_ADDRESS);
        CONNECTOR_DROP_OFF_ADDRESS.setForeignKey(COLUMN_DROP_OFF_ID_FACT);
        CONNECTOR_DROP_OFF_ADDRESS.setOverrideDimensionName("Drop Off Address");

        CONNECTOR_DELIVERY_ADDRESS = DimensionFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DELIVERY_ADDRESS.setDimension(DIMENSION_DELIVERY_ADDRESS);
        CONNECTOR_DELIVERY_ADDRESS.setForeignKey(COLUMN_DELIVERY_ID_FACT);
        CONNECTOR_DELIVERY_ADDRESS.setOverrideDimensionName("Delivery Address");

        // Initialize measures
        MEASURE_PARCEL_COUNT = MeasureFactory.eINSTANCE.createCountMeasure();
        MEASURE_PARCEL_COUNT.setName("Parcel Count");
        MEASURE_PARCEL_COUNT.setColumn(COLUMN_PARCEL_ID_FACT);
        MEASURE_PARCEL_COUNT.setFormatString("#,###");

        MEASURE_POSTAGE_SUM = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_POSTAGE_SUM.setName("Postage Sum");
        MEASURE_POSTAGE_SUM.setColumn(COLUMN_POSTAGE_FACT);
        MEASURE_POSTAGE_SUM.setFormatString("#,##0.00");

        MEASURE_INSURANCE_VALUE_SUM = MeasureFactory.eINSTANCE.createSumMeasure();
        MEASURE_INSURANCE_VALUE_SUM.setName("Insurance Value Sum");
        MEASURE_INSURANCE_VALUE_SUM.setColumn(COLUMN_INSURANCE_VALUE_FACT);
        MEASURE_INSURANCE_VALUE_SUM.setFormatString("#,##0.00");

        MEASURE_WEIGHT_MIN = MeasureFactory.eINSTANCE.createMinMeasure();
        MEASURE_WEIGHT_MIN.setName("Weight Min");
        MEASURE_WEIGHT_MIN.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_MIN.setFormatString("#,##0.00");

        MEASURE_WEIGHT_MAX = MeasureFactory.eINSTANCE.createMaxMeasure();
        MEASURE_WEIGHT_MAX.setName("Weight Max");
        MEASURE_WEIGHT_MAX.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_MAX.setFormatString("#,##0.00");

        MEASURE_WEIGHT_AVG = MeasureFactory.eINSTANCE.createAvgMeasure();
        MEASURE_WEIGHT_AVG.setName("Weight Avg");
        MEASURE_WEIGHT_AVG.setColumn(COLUMN_WEIGHT_FACT);
        MEASURE_WEIGHT_AVG.setFormatString("#,##0.00");

        MEASUREGROUP_PARCELS = CubeFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_PARCELS.getMeasures().addAll(List.of(MEASURE_PARCEL_COUNT, MEASURE_POSTAGE_SUM,
                MEASURE_INSURANCE_VALUE_SUM, MEASURE_WEIGHT_MIN, MEASURE_WEIGHT_MAX, MEASURE_WEIGHT_AVG));

        // Initialize cube
        CUBE_PARCELS = CubeFactory.eINSTANCE.createPhysicalCube();
        CUBE_PARCELS.setName("Parcels");
        CUBE_PARCELS.setQuery(TABLEQUERY_PARCELS);
        CUBE_PARCELS.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_WIDTH, CONNECTOR_DEPTH, CONNECTOR_HEIGHT, CONNECTOR_PARCEL_TYPE,
                        CONNECTOR_DEFECT, CONNECTOR_DELIVERABLE, CONNECTOR_CUSTOMS, CONNECTOR_RETURN,
                        CONNECTOR_SENDER_ADDRESS, CONNECTOR_RECEIVER_ADDRESS, CONNECTOR_DROP_OFF_ADDRESS,
                        CONNECTOR_DELIVERY_ADDRESS));
        CUBE_PARCELS.getMeasureGroups().add(MEASUREGROUP_PARCELS);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_PARCEL = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();
        DATABASE_SCHEMA_PARCEL.getOwnedElement()
                .addAll(List.of(TABLE_PARCELS, TABLE_PARCEL_TYPES, TABLE_DEFECTS, TABLE_ADDRESSES));

        CATALOG_PARCEL = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG_PARCEL.setName("Parcel Delivery Service");
        CATALOG_PARCEL.getDbschemas().add(DATABASE_SCHEMA_PARCEL);
        CATALOG_PARCEL.getCubes().add(CUBE_PARCELS);

        // Add documentation
    }

    @Override
    public Catalog get() {
        return CATALOG_PARCEL;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Parcel Database", parcelBody, 1, 0, 0, null, 0),
                        new DocSection("Parcel Cube", parcelCubeBody, 1, 1, 0, CUBE_PARCELS, 0),
                        new DocSection("Width Dimension", dimensionBody, 1, 2, 0, DIMENSION_WIDTH, 0),
                        new DocSection("Depth Dimension", dimensionBody, 1, 3, 0, DIMENSION_DEPTH, 0),
                        new DocSection("Height Dimension", dimensionBody, 1, 4, 0, DIMENSION_HEIGHT, 0),
                        new DocSection("Parcel Type Dimension", typeBody, 1, 5, 0, DIMENSION_PARCEL_TYPE, 0),
                        new DocSection("Defect Dimension", defectBody, 1, 6, 0, DIMENSION_DEFECT, 0),
                        new DocSection("Deliverable Dimension", statusBody, 1, 7, 0, DIMENSION_DELIVERABLE, 0),
                        new DocSection("Customs Dimension", statusBody, 1, 8, 0, DIMENSION_CUSTOMS, 0),
                        new DocSection("Return Dimension", statusBody, 1, 9, 0, DIMENSION_RETURN, 0),
                        new DocSection("Sender Address Dimension", addressBody, 1, 10, 0, DIMENSION_SENDER_ADDRESS, 0),
                        new DocSection("Receiver Address Dimension", addressBody, 1, 11, 0, DIMENSION_RECEIVER_ADDRESS, 0),
                        new DocSection("Drop Off Address Dimension", addressBody, 1, 12, 0, DIMENSION_DROP_OFF_ADDRESS, 0),
                        new DocSection("Delivery Address Dimension", addressBody, 1, 13, 0, DIMENSION_DELIVERY_ADDRESS, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
