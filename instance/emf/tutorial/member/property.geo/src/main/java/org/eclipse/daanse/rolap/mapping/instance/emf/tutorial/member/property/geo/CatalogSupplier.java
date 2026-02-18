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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.property.geo;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.06.02.02", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial demonstrates how to use member properties with geographic data stored across multiple tables.
            Member properties provide additional attributes for hierarchy level members, such as geographic coordinates,
            descriptions, and location data in GeoJSON format.

            The example shows a single-level Location hierarchy where member details are stored in a separate Member table,
            joined with the fact table for OLAP analysis.
            """;

    private static final String databaseSchemaBody = """
            The cube is based on two tables: `Fact` and `Member`.

            - The `Fact` table contains measures (VALUES) and references to the `Member` table via MEMBER_ID
            - The `Member` table holds the member details including:
                - ID (primary key)
                - NAME (member display name)
                - LOCATION (GeoJSON representation of the geographic area)
                - LATITUDE and LONGITUDE (numeric coordinates)
                - DESCRIPTION (additional member information)

            This normalized structure allows rich member properties while maintaining efficient fact table storage.
            """;

    private static final String memberPropertyLocationBody = """
            The Location member property stores geographic boundary data in GeoJSON format.
            This allows complex geographic shapes to be associated with each member for mapping and spatial analysis.
            """;

    private static final String memberPropertyLatitudeBody = """
            The Latitude member property stores the decimal latitude coordinate as a numeric value for precise positioning.
            """;

    private static final String memberPropertyLongitudeBody = """
            The Longitude member property stores the decimal longitude coordinate as a numeric value for precise positioning.
            """;

    private static final String memberPropertyDescriptionBody = """
            The Description member property provides additional contextual information about each location member.
            """;

    private static final String levelBody = """
            The Location level uses the Member table's ID as the primary key and NAME as the display name.
            Multiple member properties are attached to provide rich geographic information for each location.
            """;

    private static final String hierarchyBody = """
            This single-level hierarchy demonstrates how member properties work with joined tables.
            The query joins the Member table to access both the level data and the member properties.
            """;

    private static final String dimensionBody = """
            The Location dimension contains one hierarchy with geographic member properties.
            """;

    private static final String cubeBody = """
            The cube connects the Fact table (containing measures) with the Member table (containing member properties)
            via a join query. The DimensionConnector uses MEMBER_ID as the foreign key to link facts to location members.
            """;

    private static final String queryFactBody = """
            Simple table query referencing the Fact table for measure data.
            """;

    private static final String queryMemberBody = """
            Simple table query referencing the Member table for location data and properties.
            """;

    private static final String queryJoinBody = """
            The join query connects the Fact table to the Member table using:
            - Fact.MEMBER_ID (foreign key) joined to Member.ID (primary key)

            This allows the hierarchy to access member properties while the cube measures reference the fact data.
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_main");

        // Fact table columns
        Column columnFactMemberId = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFactMemberId.setName("MEMBER_ID");
        columnFactMemberId.setId("_column_fact_memberId");
        columnFactMemberId.setType(ColumnType.INTEGER);

        Column columnFactValue = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFactValue.setName("VALUE");
        columnFactValue.setId("_column_fact_value");
        columnFactValue.setType(ColumnType.DECIMAL);

        PhysicalTable tableFact = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFact.setName("Fact");
        tableFact.setId("_table_fact");
        tableFact.getColumns().addAll(List.of(columnFactMemberId, columnFactValue));
        databaseSchema.getTables().add(tableFact);

        // Member table columns
        Column columnMemberId = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberId.setName("ID");
        columnMemberId.setId("_column_member_id");
        columnMemberId.setType(ColumnType.INTEGER);

        Column columnMemberName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberName.setName("NAME");
        columnMemberName.setId("_column_member_name");
        columnMemberName.setType(ColumnType.VARCHAR);

        Column columnMemberLocation = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberLocation.setName("LOCATION");
        columnMemberLocation.setId("_column_member_location");
        columnMemberLocation.setType(ColumnType.VARCHAR);

        Column columnMemberLatitude = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberLatitude.setName("LATITUDE");
        columnMemberLatitude.setId("_column_member_latitude");
        columnMemberLatitude.setType(ColumnType.DECIMAL);

        Column columnMemberLongitude = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberLongitude.setName("LONGITUDE");
        columnMemberLongitude.setId("_column_member_longitude");
        columnMemberLongitude.setType(ColumnType.DECIMAL);

        Column columnMemberDescription = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnMemberDescription.setName("DESCRIPTION");
        columnMemberDescription.setId("_column_member_description");
        columnMemberDescription.setType(ColumnType.VARCHAR);

        PhysicalTable tableMember = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableMember.setName("Member");
        tableMember.setId("_table_member");
        tableMember.getColumns().addAll(List.of(columnMemberId, columnMemberName, columnMemberLocation,
                columnMemberLatitude, columnMemberLongitude, columnMemberDescription));
        databaseSchema.getTables().add(tableMember);

        // Queries
        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_query_fact");
        queryFact.setTable(tableFact);

        TableQuery queryMember = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryMember.setId("_query_member");
        queryMember.setTable(tableMember);

        // Join query for hierarchy (Fact -> Member)
        JoinedQueryElement joinElementFact = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFact.setQuery(queryFact);
        joinElementFact.setKey(columnFactMemberId);

        JoinedQueryElement joinElementMember = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinElementMember.setQuery(queryMember);
        joinElementMember.setKey(columnMemberId);

        JoinQuery queryJoin = RolapMappingFactory.eINSTANCE.createJoinQuery();
        queryJoin.setId("_query_factToMember");
        queryJoin.setLeft(joinElementFact);
        queryJoin.setRight(joinElementMember);

        // Member Properties
        MemberProperty propertyLocation = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propertyLocation.setName("Location");
        propertyLocation.setId("_memberProperty_location");
        propertyLocation.setColumn(columnMemberLocation);
        propertyLocation.setPropertyType(ColumnInternalDataType.STRING);

        MemberProperty propertyLatitude = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propertyLatitude.setName("Latitude");
        propertyLatitude.setId("_memberProperty_latitude");
        propertyLatitude.setColumn(columnMemberLatitude);
        propertyLatitude.setPropertyType(ColumnInternalDataType.NUMERIC);

        MemberProperty propertyLongitude = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propertyLongitude.setName("Longitude");
        propertyLongitude.setId("_memberProperty_longitude");
        propertyLongitude.setColumn(columnMemberLongitude);
        propertyLongitude.setPropertyType(ColumnInternalDataType.NUMERIC);

        MemberProperty propertyDescription = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propertyDescription.setName("Description");
        propertyDescription.setId("_memberProperty_description");
        propertyDescription.setColumn(columnMemberDescription);
        propertyDescription.setPropertyType(ColumnInternalDataType.STRING);

        // Level with member properties
        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Location");
        level.setId("_level_location");
        level.setColumn(columnMemberId);
        level.setNameColumn(columnMemberName);
        level.getMemberProperties()
                .addAll(List.of(propertyLocation, propertyLatitude, propertyLongitude, propertyDescription));

        // Hierarchy
        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("LocationHierarchy");
        hierarchy.setId("_hierarchy_location");
        hierarchy.setPrimaryKey(columnMemberId);
        hierarchy.setQuery(queryJoin);
        hierarchy.getLevels().add(level);

        // Dimension
        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Location");
        dimension.setId("_dimension_location");
        dimension.getHierarchies().add(hierarchy);

        // Measure
        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("TotalValue");
        measure.setId("_measure_totalValue");
        measure.setColumn(columnFactValue);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        // Dimension Connector
        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_location");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(columnFactMemberId);

        // Cube
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Geographic Analysis");
        cube.setId("_cube_geographic");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        // Catalog
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Member Properties with Geographic Data");
        catalog.setDescription("Tutorial showing member properties with location data across multiple tables");
        catalog.getCubes().add(cube);

        // Documentation
        document(catalog, "Daanse Tutorial - Member Properties with Geographic Data", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);

        document(queryFact, "Query - Fact Table", queryFactBody, 1, 2, 0, true, 2);
        document(queryMember, "Query - Member Table", queryMemberBody, 1, 3, 0, true, 2);
        document(queryJoin, "Query - Join Fact to Member", queryJoinBody, 1, 4, 0, true, 2);

        document(propertyLocation, "Member Property - Location (GeoJSON)", memberPropertyLocationBody, 1, 5, 0, true,
                0);
        document(propertyLatitude, "Member Property - Latitude", memberPropertyLatitudeBody, 1, 6, 0, true, 0);
        document(propertyLongitude, "Member Property - Longitude", memberPropertyLongitudeBody, 1, 7, 0, true, 0);
        document(propertyDescription, "Member Property - Description", memberPropertyDescriptionBody, 1, 8, 0, true, 0);

        document(level, "Level with Member Properties", levelBody, 1, 9, 0, true, 0);
        document(hierarchy, "Hierarchy with Join Query", hierarchyBody, 1, 10, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 11, 0, true, 0);

        document(cube, "Cube with Geographic Analysis", cubeBody, 1, 12, 0, true, 2);

        return catalog;
    }
}
