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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ColumnInternalDataType;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.MemberProperty;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.06.02.02", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private MemberProperty propertyLocation;
    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Level level;
    private MemberProperty propertyLongitude;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource queryMember;
    private JoinSource queryJoin;
    private MemberProperty propertyLatitude;
    private MemberProperty propertyDescription;
    private TableSource queryFact;


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
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        // Fact table columns
        Column columnFactMemberId = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnFactMemberId.setName("MEMBER_ID");
        columnFactMemberId.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnFactValue = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnFactValue.setName("VALUE");
        columnFactValue.setType(SqlSimpleTypes.decimalType(18, 4));

        Table tableFact = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFact.setName("Fact");
        tableFact.getFeature().addAll(List.of(columnFactMemberId, columnFactValue));
        databaseSchema.getOwnedElement().add(tableFact);

        // Member table columns
        Column columnMemberId = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberId.setName("ID");
        columnMemberId.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnMemberName = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberName.setName("NAME");
        columnMemberName.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnMemberLocation = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberLocation.setName("LOCATION");
        columnMemberLocation.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnMemberLatitude = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberLatitude.setName("LATITUDE");
        columnMemberLatitude.setType(SqlSimpleTypes.decimalType(18, 4));

        Column columnMemberLongitude = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberLongitude.setName("LONGITUDE");
        columnMemberLongitude.setType(SqlSimpleTypes.decimalType(18, 4));

        Column columnMemberDescription = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnMemberDescription.setName("DESCRIPTION");
        columnMemberDescription.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableMember = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableMember.setName("Member");
        tableMember.getFeature().addAll(List.of(columnMemberId, columnMemberName, columnMemberLocation,
                columnMemberLatitude, columnMemberLongitude, columnMemberDescription));
        databaseSchema.getOwnedElement().add(tableMember);

        // Queries
        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(tableFact);

        queryMember = SourceFactory.eINSTANCE.createTableSource();
        queryMember.setTable(tableMember);

        // Join query for hierarchy (Fact -> Member)
        JoinedQueryElement joinElementFact = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementFact.setQuery(queryFact);
        joinElementFact.setKey(columnFactMemberId);

        JoinedQueryElement joinElementMember = SourceFactory.eINSTANCE.createJoinedQueryElement();
        joinElementMember.setQuery(queryMember);
        joinElementMember.setKey(columnMemberId);

        queryJoin = SourceFactory.eINSTANCE.createJoinSource();
        queryJoin.setLeft(joinElementFact);
        queryJoin.setRight(joinElementMember);

        // Member Properties
        propertyLocation = LevelFactory.eINSTANCE.createMemberProperty();
        propertyLocation.setName("Location");
        propertyLocation.setColumn(columnMemberLocation);
        propertyLocation.setPropertyType(ColumnInternalDataType.STRING);

        propertyLatitude = LevelFactory.eINSTANCE.createMemberProperty();
        propertyLatitude.setName("Latitude");
        propertyLatitude.setColumn(columnMemberLatitude);
        propertyLatitude.setPropertyType(ColumnInternalDataType.NUMERIC);

        propertyLongitude = LevelFactory.eINSTANCE.createMemberProperty();
        propertyLongitude.setName("Longitude");
        propertyLongitude.setColumn(columnMemberLongitude);
        propertyLongitude.setPropertyType(ColumnInternalDataType.NUMERIC);

        propertyDescription = LevelFactory.eINSTANCE.createMemberProperty();
        propertyDescription.setName("Description");
        propertyDescription.setColumn(columnMemberDescription);
        propertyDescription.setPropertyType(ColumnInternalDataType.STRING);

        // Level with member properties
        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Location");
        level.setColumn(columnMemberId);
        level.setNameColumn(columnMemberName);
        level.getMemberProperties()
                .addAll(List.of(propertyLocation, propertyLatitude, propertyLongitude, propertyDescription));

        // Hierarchy
        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("LocationHierarchy");
        hierarchy.setPrimaryKey(columnMemberId);
        hierarchy.setQuery(queryJoin);
        hierarchy.getLevels().add(level);

        // Dimension
        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Location");
        dimension.getHierarchies().add(hierarchy);

        // Measure
        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("TotalValue");
        measure.setColumn(columnFactValue);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        // Dimension Connector
        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(columnFactMemberId);

        // Cube
        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Geographic Analysis");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        // Catalog
        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Member Properties with Geographic Data");
        catalog.setDescription("Tutorial showing member properties with location data across multiple tables");
        catalog.getCubes().add(cube);

        // Documentation


            return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Member Properties with Geographic Data", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query - Fact Table", queryFactBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Query - Member Table", queryMemberBody, 1, 3, 0, queryMember, 2),
                        new DocSection("Query - Join Fact to Member", queryJoinBody, 1, 4, 0, queryJoin, 2),
                        new DocSection("Member Property - Location (GeoJSON)", memberPropertyLocationBody, 1, 5, 0, propertyLocation, 0),
                        new DocSection("Member Property - Latitude", memberPropertyLatitudeBody, 1, 6, 0, propertyLatitude, 0),
                        new DocSection("Member Property - Longitude", memberPropertyLongitudeBody, 1, 7, 0, propertyLongitude, 0),
                        new DocSection("Member Property - Description", memberPropertyDescriptionBody, 1, 8, 0, propertyDescription, 0),
                        new DocSection("Level with Member Properties", levelBody, 1, 9, 0, level, 0),
                        new DocSection("Hierarchy with Join Query", hierarchyBody, 1, 10, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 11, 0, dimension, 0),
                        new DocSection("Cube with Geographic Analysis", cubeBody, 1, 12, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
