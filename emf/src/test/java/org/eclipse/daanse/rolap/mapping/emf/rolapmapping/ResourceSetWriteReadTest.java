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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.emf.rolapmapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceSetWriteReadTest {

    @TempDir
    static Path tempDir;

    static Path fileRolap;
    static Path fileRdbs;

    @BeforeAll
    public static void beforeAll() throws IOException {
        fileRolap = Files.createFile(tempDir.resolve("catalog.yml"));
        fileRdbs = Files.createFile(tempDir.resolve("db.yml"));
    }

    @Test
    @Order(1)
    public void writePopulation(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        URI uriRdbs = URI.createFileURI(fileRdbs.toAbsolutePath().toString());
        Resource resourceRdbs = rs.createResource(uriRdbs);

        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setName("jena.statistik");
        databaseSchema.setId("jena.statistik_id");

        // einwohner table start
        Column columnEinwohnerJahr = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerJahr.setName("JAHR");
        columnEinwohnerJahr.setId("EinwohnerJAHR");
        columnEinwohnerJahr.setType(ColumnType.INTEGER);

        Column columnEinwohnerStatbez = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerStatbez.setName("STATBEZ");
        columnEinwohnerStatbez.setId("EinwohnerSTATBEZ");
        columnEinwohnerStatbez.setType(ColumnType.INTEGER);

        Column columnEinwohnerKerGesch = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerKerGesch.setName("KER_GESCH");
        columnEinwohnerKerGesch.setId("EinwohnerKerGesch");
        columnEinwohnerKerGesch.setType(ColumnType.VARCHAR);

        Column columnEinwohnerAge = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerAge.setName("AGE");
        columnEinwohnerAge.setId("EinwohnerAge");
        columnEinwohnerAge.setType(ColumnType.INTEGER);

        Column columnEinwohnerAnzahl = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerAnzahl.setName("Anzahl");
        columnEinwohnerAnzahl.setId("EinwohnerAnzahl");
        columnEinwohnerAnzahl.setType(ColumnType.INTEGER);

        Column columnEinwohnerGeojson = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnEinwohnerGeojson.setName("GEOJSON");
        columnEinwohnerGeojson.setId("EinwohnerGeojson");
        columnEinwohnerGeojson.setType(ColumnType.VARCHAR);

        PhysicalTable tableEinwohner = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableEinwohner.setName("einwohner");
        tableEinwohner.setId("einwohner");
        tableEinwohner.getColumns().addAll(List.of(columnEinwohnerJahr, columnEinwohnerStatbez, columnEinwohnerKerGesch,
                columnEinwohnerAge, columnEinwohnerAnzahl, columnEinwohnerGeojson));
        databaseSchema.getTables().add(tableEinwohner);

        // einwohner table end

        // year table start
        Column columnYearYear = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnYearYear.setName("year");
        columnYearYear.setId("YearYear");
        columnYearYear.setType(ColumnType.INTEGER);

        Column columnYearOrdinal = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnYearOrdinal.setName("ordinal");
        columnYearOrdinal.setId("YearOrdinal");
        columnYearOrdinal.setType(ColumnType.INTEGER);

        PhysicalTable tableYear = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableYear.setName("year");
        tableYear.setId("tyear");
        tableYear.getColumns().addAll(List.of(columnYearYear, columnYearOrdinal));
        databaseSchema.getTables().add(tableYear);

        // year table end

        // statbez table start
        Column columnStatbezGid = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezGid.setName("gid");
        columnStatbezGid.setId("StatbezGid");
        columnStatbezGid.setType(ColumnType.INTEGER);

        Column columnStatbezPlraum = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezPlraum.setName("plraum");
        columnStatbezPlraum.setId("StatbezPlraum");
        columnStatbezPlraum.setType(ColumnType.INTEGER);

        Column columnStatbezStatbezName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezStatbezName.setName("statbez_name");
        columnStatbezStatbezName.setId("StatbezStatbezName");
        columnStatbezStatbezName.setType(ColumnType.INTEGER);

        Column columnStatbezTheGeom = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezTheGeom.setName("the_geom");
        columnStatbezTheGeom.setId("StatbezTheGeom");
        columnStatbezTheGeom.setType(ColumnType.INTEGER);

        Column columnStatbezUuid = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezUuid.setName("uuid");
        columnStatbezUuid.setId("StatbezUuid");
        columnStatbezUuid.setType(ColumnType.INTEGER);

        Column columnStatbezGeojson = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnStatbezGeojson.setName("geojson");
        columnStatbezGeojson.setId("StatbezGeojson");
        columnStatbezGeojson.setType(ColumnType.INTEGER);

        PhysicalTable tableStatbez = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableStatbez.setName("statbez");
        tableStatbez.setId("statbez");
        tableStatbez.getColumns().addAll(List.of(columnStatbezGid, columnStatbezPlraum, columnStatbezStatbezName,
                columnStatbezTheGeom, columnStatbezUuid, columnStatbezGeojson));
        databaseSchema.getTables().add(tableStatbez);

        // statbez table end

        // plraum table start
        Column columnPlraumGid = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumGid.setName("gid");
        columnPlraumGid.setId("PlraumGid");
        columnPlraumGid.setType(ColumnType.INTEGER);

        Column columnPlraumPlraum = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumPlraum.setName("plraum");
        columnPlraumPlraum.setId("PlraumPlraum");
        columnPlraumPlraum.setType(ColumnType.VARCHAR);

        Column columnPlraumTheGeom = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumTheGeom.setName("the_geom");
        columnPlraumTheGeom.setId("PlraumTheGeom");
        columnPlraumTheGeom.setType(ColumnType.VARCHAR);

        Column columnPlraumPlraumNr = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumPlraumNr.setName("plraum_nr");
        columnPlraumPlraumNr.setId("PlraumPlraumNr");
        columnPlraumPlraumNr.setType(ColumnType.INTEGER);

        Column columnPlraumUuid = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumUuid.setName("uuid");
        columnPlraumUuid.setId("PlraumUuid");
        columnPlraumUuid.setType(ColumnType.VARCHAR);

        Column columnPlraumGeojson = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumGeojson.setName("geojson");
        columnPlraumGeojson.setId("PlraumGeojson");
        columnPlraumGeojson.setType(ColumnType.INTEGER);

        Column columnPlraumTownid = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnPlraumTownid.setName("townid");
        columnPlraumTownid.setId("PlraumTownid");
        columnPlraumTownid.setType(ColumnType.INTEGER);

        PhysicalTable tablePlraum = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tablePlraum.setName("plraum");
        tablePlraum.setId("plraum");
        tablePlraum.getColumns().addAll(List.of(columnPlraumGid, columnPlraumPlraum, columnPlraumTheGeom,
                columnPlraumUuid, columnPlraumGeojson, columnPlraumTownid));
        databaseSchema.getTables().add(tablePlraum);

        // plraum table end

        // town table start
        Column columnTownId = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownId.setName("id");
        columnTownId.setId("TownId");
        columnTownId.setType(ColumnType.INTEGER);

        Column columnTownName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownName.setName("name");
        columnTownName.setId("TownName");
        columnTownName.setType(ColumnType.VARCHAR);

        Column columnTownGeojson = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownGeojson.setName("geojson");
        columnTownGeojson.setId("TownGeojson");
        columnTownGeojson.setType(ColumnType.VARCHAR);

        PhysicalTable tableTown = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTown.setName("town");
        tableTown.setId("town");
        tableTown.getColumns().addAll(List.of(columnTownId, columnTownName, columnTownGeojson));
        databaseSchema.getTables().add(tableTown);

        // town table end

        // gender table start
        Column columnGenderKey = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnGenderKey.setName("key");
        columnGenderKey.setId("GenderKey");
        columnGenderKey.setType(ColumnType.VARCHAR);

        Column columnGenderName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnGenderName.setName("name");
        columnGenderName.setId("GenderName");
        columnGenderName.setType(ColumnType.VARCHAR);

        PhysicalTable tableGender = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableGender.setName("gender");
        tableGender.setId("gender");
        tableGender.getColumns().addAll(List.of(columnGenderKey, columnGenderName));
        databaseSchema.getTables().add(tableGender);

        // gender table end

        // AgeGroups table start
        Column columnAgeGroupsAge = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsAge.setName("Age");
        columnAgeGroupsAge.setId("AgeGroupsAge");
        columnAgeGroupsAge.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH1 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH1.setName("H1");
        columnAgeGroupsH1.setId("AgeGroupsH1");
        columnAgeGroupsH1.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH1Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH1Order.setName("H1_Order");
        columnAgeGroupsH1Order.setId("AgeGroupsH1Order");
        columnAgeGroupsH1Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH2 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH2.setName("H2");
        columnAgeGroupsH2.setId("AgeGroupsH2");
        columnAgeGroupsH2.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH2Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH2Order.setName("H2_Order");
        columnAgeGroupsH2Order.setId("AgeGroupsH2Order");
        columnAgeGroupsH2Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH3 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH3.setName("H3");
        columnAgeGroupsH3.setId("AgeGroupsH3");
        columnAgeGroupsH3.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH3Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH3Order.setName("H3_Order");
        columnAgeGroupsH3Order.setId("AgeGroupsH3Order");
        columnAgeGroupsH3Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH4 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH4.setName("H4");
        columnAgeGroupsH4.setId("AgeGroupsH4");
        columnAgeGroupsH4.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH4Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH4Order.setName("H4_Order");
        columnAgeGroupsH4Order.setId("AgeGroupsH4Order");
        columnAgeGroupsH4Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH5 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH5.setName("H5");
        columnAgeGroupsH5.setId("AgeGroupsH5");
        columnAgeGroupsH5.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH5Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH5Order.setName("H5_Order");
        columnAgeGroupsH5Order.setId("AgeGroupsH5Order");
        columnAgeGroupsH5Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH6 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH6.setName("H6");
        columnAgeGroupsH6.setId("AgeGroupsH6");
        columnAgeGroupsH6.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH6Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH6Order.setName("H6_Order");
        columnAgeGroupsH6Order.setId("AgeGroupsH6Order");
        columnAgeGroupsH6Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH7 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH7.setName("H7");
        columnAgeGroupsH7.setId("AgeGroupsH7");
        columnAgeGroupsH7.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH7Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH7Order.setName("H7_Order");
        columnAgeGroupsH7Order.setId("AgeGroupsH7Order");
        columnAgeGroupsH7Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH8 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH8.setName("H8");
        columnAgeGroupsH8.setId("AgeGroupsH8");
        columnAgeGroupsH8.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH8Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH8Order.setName("H8_Order");
        columnAgeGroupsH8Order.setId("AgeGroupsH8Order");
        columnAgeGroupsH8Order.setType(ColumnType.INTEGER);

        Column columnAgeGroupsH9 = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH9.setName("H9");
        columnAgeGroupsH9.setId("AgeGroupsH9");
        columnAgeGroupsH9.setType(ColumnType.VARCHAR);

        Column columnAgeGroupsH9Order = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnAgeGroupsH9Order.setName("H9_Order");
        columnAgeGroupsH9Order.setId("AgeGroupsH9Order");
        columnAgeGroupsH9Order.setType(ColumnType.INTEGER);

        PhysicalTable tableAgeGroups = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableAgeGroups.setName("AgeGroups");
        tableAgeGroups.setId("AgeGroups");
        tableAgeGroups.getColumns()
                .addAll(List.of(columnAgeGroupsAge, columnAgeGroupsH1, columnAgeGroupsH1Order, columnAgeGroupsH2,
                        columnAgeGroupsH2Order, columnAgeGroupsH3, columnAgeGroupsH3Order, columnAgeGroupsH4,
                        columnAgeGroupsH4Order, columnAgeGroupsH5, columnAgeGroupsH5Order, columnAgeGroupsH6,
                        columnAgeGroupsH6Order, columnAgeGroupsH7, columnAgeGroupsH7Order, columnAgeGroupsH8,
                        columnAgeGroupsH8Order, columnAgeGroupsH9, columnAgeGroupsH9Order));
        databaseSchema.getTables().add(tableAgeGroups);

        DatabaseCatalog dbCatalog = RolapMappingFactory.eINSTANCE.createDatabaseCatalog();
        dbCatalog.getSchemas().add(databaseSchema);

        resourceRdbs.getContents().add(dbCatalog);
        resourceRdbs.save(Map.of());
        System.out.println(Files.readString(fileRdbs, StandardCharsets.UTF_8));
        /////////////////////////

        URI uriRolap = URI.createFileURI(fileRolap.toAbsolutePath().toString());
        Resource resourceRolap = rs.createResource(uriRolap);

        TableQuery queryEinwohner = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryEinwohner.setTable(tableEinwohner);
        TableQuery queryYear = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryYear.setTable(tableYear);
        TableQuery queryStatbez = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryStatbez.setTable(tableStatbez);
        TableQuery queryPlraum = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryPlraum.setTable(tablePlraum);
        TableQuery queryTown = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryTown.setTable(tableTown);

        TableQuery queryGender = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryGender.setTable(tableGender);

        TableQuery queryAgeGroups = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryAgeGroups.setTable(tableAgeGroups);
        // AgeGroups table end

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Bevolkerung");
        cube.setId("Bevolkerung");
        cube.setQuery(queryEinwohner);

        DimensionConnector dcJahr = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcJahr.setOverrideDimensionName("Jahr");
        dcJahr.setId("Jahr");
        dcJahr.setForeignKey(columnEinwohnerJahr);
        StandardDimension dJahr = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcJahr.setDimension(dJahr);
        dJahr.setName("Jahr");
        dJahr.setId("dJahr");
        ExplicitHierarchy hJahr = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();



        Documentation d=RolapMappingFactory.eINSTANCE.createDocumentation();
        d.setValue("dsds");


        Level lJahr = RolapMappingFactory.eINSTANCE.createLevel();
        lJahr.getDocumentations().add(d);
        lJahr.setName("Jahr");
        lJahr.setId("lJahr");
        lJahr.setColumn(columnYearYear);
        lJahr.setOrdinalColumn(columnYearOrdinal);

        dJahr.getHierarchies().add(hJahr);
        hJahr.setId("hJahr");
        hJahr.setHasAll(false);
        hJahr.setName("Jahr");
        hJahr.setQuery(queryYear);
        hJahr.setPrimaryKey(columnYearYear);
        hJahr.setDefaultMember("2023");
        hJahr.getLevels().add(lJahr);

        DimensionConnector dcStatistischerBezirk = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcStatistischerBezirk.setOverrideDimensionName("statistischer Bezirk");
        dcStatistischerBezirk.setId("dcStatistischerBezirk");
        dcStatistischerBezirk.setForeignKey(columnEinwohnerStatbez);
        StandardDimension dStatistischerBezirk = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcStatistischerBezirk.setDimension(dStatistischerBezirk);
        dStatistischerBezirk.setName("statistischer Bezirk");
        dStatistischerBezirk.setId("dStatistischerBezirk");

        ExplicitHierarchy hStadtPlanungsraumStatistischerBezirk = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hStadtPlanungsraumStatistischerBezirk.setHasAll(true);
        hStadtPlanungsraumStatistischerBezirk.setName("Stadt - Planungsraum - statistischer Bezirk");
        hStadtPlanungsraumStatistischerBezirk.setId("hStadtPlanungsraumStatistischerBezirk");
        hStadtPlanungsraumStatistischerBezirk.setPrimaryKey(columnStatbezGid);
        dStatistischerBezirk.getHierarchies().add(hStadtPlanungsraumStatistischerBezirk);

        JoinQuery queryStatistischerBezirk = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JoinedQueryElement left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        left.setQuery(queryStatbez);
        left.setKey(columnStatbezPlraum);
        JoinedQueryElement right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();

        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JoinedQueryElement l = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQuery.setLeft(l);
        l.setKey(columnPlraumTownid);
        l.setQuery(queryPlraum);
        JoinedQueryElement r = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinQuery.setRight(r);
        r.setKey(columnTownId);
        r.setQuery(queryTown);

        right.setQuery(joinQuery);
        right.setKey(columnPlraumGid);
        queryStatistischerBezirk.setLeft(left);
        queryStatistischerBezirk.setRight(right);
        hStadtPlanungsraumStatistischerBezirk.setQuery(queryStatistischerBezirk);

        Level lStadt = RolapMappingFactory.eINSTANCE.createLevel();

        MemberProperty lStadtP = RolapMappingFactory.eINSTANCE.createMemberProperty();
        lStadtP.setName("GeoJson");
        lStadtP.setId("GeoJson1");
        lStadtP.setColumn(columnTownGeojson);
        lStadtP.setPropertyType(ColumnInternalDataType.STRING);

        lStadt.setName("Stadt");
        lStadt.setId("Stadt");
        lStadt.setColumn(columnTownName);
        lStadt.getMemberProperties().addAll(List.of(lStadtP));

        Level lPlanungsraum = RolapMappingFactory.eINSTANCE.createLevel();

        MemberProperty lPlanungsraumP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        lPlanungsraumP1.setName("uuid");
        lPlanungsraumP1.setId("uuid1");
        lPlanungsraumP1.setColumn(columnPlraumUuid);

        MemberProperty lPlanungsraumP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        lPlanungsraumP2.setName("GeoJson");
        lPlanungsraumP2.setId("GeoJson1");
        lPlanungsraumP2.setColumn(columnPlraumGeojson);
        lPlanungsraumP2.setPropertyType(ColumnInternalDataType.STRING);

        lPlanungsraum.setName("Planungsraum");
        lPlanungsraum.setId("Planungsraum");
        lPlanungsraum.setColumn(columnPlraumGid);
        lPlanungsraum.setNameColumn(columnPlraumPlraum);
        lPlanungsraum.getMemberProperties().addAll(List.of(lPlanungsraumP1, lPlanungsraumP2));

        Level lStatistischerBezirk = RolapMappingFactory.eINSTANCE.createLevel();

        MemberProperty lStatistischerBezirkP1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        lStatistischerBezirkP1.setName("uuid");
        lStatistischerBezirkP1.setId("uuid2");
        lStatistischerBezirkP1.setColumn(columnStatbezUuid);

        MemberProperty lStatistischerBezirkP2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        lStatistischerBezirkP2.setName("GeoJson");
        lStatistischerBezirkP2.setId("GeoJson2");
        lStatistischerBezirkP2.setColumn(columnStatbezGeojson);
        lStatistischerBezirkP2.setPropertyType(ColumnInternalDataType.STRING);

        lStatistischerBezirk.setName("Statistischer Bezirk");
        lStatistischerBezirk.setId("lStatistischerBezirk");
        lStatistischerBezirk.setColumn(columnStatbezGid);
        lStatistischerBezirk.setNameColumn(columnStatbezStatbezName);
        lStatistischerBezirk.getMemberProperties().addAll(List.of(lStatistischerBezirkP1, lStatistischerBezirkP2));

        hStadtPlanungsraumStatistischerBezirk.getLevels().addAll(List.of(lStadt, lPlanungsraum, lStatistischerBezirk));

        DimensionConnector dcGeschlecht = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcGeschlecht.setOverrideDimensionName("Geschlecht");
        dcGeschlecht.setId("dcGeschlecht");
        dcGeschlecht.setForeignKey(columnEinwohnerKerGesch);
        StandardDimension dGeschlecht = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcGeschlecht.setDimension(dGeschlecht);
        dGeschlecht.setName("Geschlecht");
        dGeschlecht.setId("dGeschlecht");

        ExplicitHierarchy hGeschlecht = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hGeschlecht.setHasAll(true);
        hGeschlecht.setName("Geschlecht (m/w/d)");
        hGeschlecht.setId("hGeschlecht");
        hGeschlecht.setPrimaryKey(columnGenderKey);
        hGeschlecht.setQuery(queryGender);

        Level lGeschlecht = RolapMappingFactory.eINSTANCE.createLevel();
        lGeschlecht.setName("Geschlecht");
        lGeschlecht.setId("lGeschlecht");
        lGeschlecht.setColumn(columnGenderKey);
        lGeschlecht.setNameColumn(columnGenderName);

        hGeschlecht.getLevels().add(lGeschlecht);
        dGeschlecht.getHierarchies().add(hGeschlecht);

        DimensionConnector dcAlter = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcAlter.setOverrideDimensionName("Alter");
        dcAlter.setId("Alter");
        dcAlter.setForeignKey(columnEinwohnerAge);
        StandardDimension dAlter = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcAlter.setDimension(dAlter);
        dcAlter.setDimension(dGeschlecht);
        dAlter.setName("Alter");
        dAlter.setId("Alter");

        ExplicitHierarchy hAlter = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAlter.setHasAll(true);
        hAlter.setName("Alter (Einzeljahrgänge)");
        hAlter.setId("Alter (Einzeljahrgänge)");
        hAlter.setPrimaryKey(columnAgeGroupsAge);
        hAlter.setQuery(queryAgeGroups);

        Level lAlter = RolapMappingFactory.eINSTANCE.createLevel();
        lAlter.setName("Alter");
        lAlter.setId("Alter1");
        lAlter.setColumn(columnAgeGroupsAge);
        hAlter.getLevels().add(lAlter);

        ExplicitHierarchy hAltersgruppenStandard = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAltersgruppenStandard.setHasAll(true);
        hAltersgruppenStandard.setName("Altersgruppen (Standard)");
        hAltersgruppenStandard.setId("Altersgruppen (Standard)");
        hAltersgruppenStandard.setPrimaryKey(columnAgeGroupsAge);
        hAltersgruppenStandard.setQuery(queryAgeGroups);

        Level lAltersgruppeH1 = RolapMappingFactory.eINSTANCE.createLevel();
        lAltersgruppeH1.setName("Altersgruppe");
        lAltersgruppeH1.setId("AltersgruppeH1");
        lAltersgruppeH1.setColumn(columnAgeGroupsH1);
        lAltersgruppeH1.setOrdinalColumn(columnAgeGroupsH1Order);
        hAltersgruppenStandard.getLevels().add(lAltersgruppeH1);

        Level lAlterH1 = RolapMappingFactory.eINSTANCE.createLevel();
        lAlterH1.setName("Alter");
        lAlterH1.setId("AlterH1");
        lAlterH1.setColumn(columnAgeGroupsAge);
        hAltersgruppenStandard.getLevels().add(lAlterH1);

        ExplicitHierarchy hAltersgruppenKinder = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAltersgruppenKinder.setHasAll(true);
        hAltersgruppenKinder.setName("Altersgruppen (Kinder)");
        hAltersgruppenKinder.setId("Altersgruppen (Kinder)");
        hAltersgruppenKinder.setPrimaryKey(columnAgeGroupsAge);
        hAltersgruppenKinder.setQuery(queryAgeGroups);

        Level lAltersgruppeH2 = RolapMappingFactory.eINSTANCE.createLevel();
        lAltersgruppeH2.setName("Altersgruppe");
        lAltersgruppeH2.setId("AltersgruppeH2");
        lAltersgruppeH2.setColumn(columnAgeGroupsH2);
        lAltersgruppeH2.setOrdinalColumn(columnAgeGroupsH2Order);
        hAltersgruppenKinder.getLevels().add(lAltersgruppeH2);

        Level lAlterH2 = RolapMappingFactory.eINSTANCE.createLevel();
        lAlterH2.setName("Alter");
        lAlterH2.setId("AlterH2");
        lAlterH2.setColumn(columnAgeGroupsAge);
        hAltersgruppenKinder.getLevels().add(lAlterH2);

        ExplicitHierarchy hAltersgruppenSystematikRKI = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAltersgruppenSystematikRKI.setHasAll(true);
        hAltersgruppenSystematikRKI.setName("Altersgruppen (Systematik RKI)");
        hAltersgruppenSystematikRKI.setId("Altersgruppen (Systematik RKI)");
        hAltersgruppenSystematikRKI.setPrimaryKey(columnAgeGroupsAge);
        hAltersgruppenSystematikRKI.setQuery(queryAgeGroups);

        Level lAltersgruppeH7 = RolapMappingFactory.eINSTANCE.createLevel();
        lAltersgruppeH7.setName("Altersgruppe");
        lAltersgruppeH7.setId("AltersgruppeH7");
        lAltersgruppeH7.setColumn(columnAgeGroupsH7);
        lAltersgruppeH7.setOrdinalColumn(columnAgeGroupsH7Order);
        hAltersgruppenKinder.getLevels().add(lAltersgruppeH7);

        Level lAlterH7 = RolapMappingFactory.eINSTANCE.createLevel();
        lAlterH7.setName("Alter");
        lAlterH7.setId("AlterH7");
        lAlterH7.setColumn(columnAgeGroupsAge);
        hAltersgruppenSystematikRKI.getLevels().add(lAlterH7);

        ExplicitHierarchy hAltersgruppenCovidstatistik = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAltersgruppenCovidstatistik.setHasAll(true);
        hAltersgruppenCovidstatistik.setName("Altersgruppen (Systematik RKI)");
        hAltersgruppenCovidstatistik.setId("Altersgruppen (Systematik RKI)");
        hAltersgruppenCovidstatistik.setPrimaryKey(columnAgeGroupsAge);
        hAltersgruppenCovidstatistik.setQuery(queryAgeGroups);

        Level lAltersgruppeH8 = RolapMappingFactory.eINSTANCE.createLevel();
        lAltersgruppeH8.setName("Altersgruppe");
        lAltersgruppeH8.setId("AltersgruppeH8");
        lAltersgruppeH8.setColumn(columnAgeGroupsH8);
        lAltersgruppeH8.setOrdinalColumn(columnAgeGroupsH8Order);
        hAltersgruppenKinder.getLevels().add(lAltersgruppeH8);

        Level lAlterH8 = RolapMappingFactory.eINSTANCE.createLevel();
        lAlterH8.setName("Alter");
        lAlterH8.setId("AlterH8");
        lAlterH8.setColumn(columnAgeGroupsAge);
        hAltersgruppenCovidstatistik.getLevels().add(lAlterH8);

        ExplicitHierarchy hAltersgruppenJahresGruppen = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hAltersgruppenJahresGruppen.setHasAll(true);
        hAltersgruppenJahresGruppen.setName("Altersgruppen (10-Jahres-Gruppen)");
        hAltersgruppenJahresGruppen.setId("Altersgruppen (10-Jahres-Gruppen)");
        hAltersgruppenJahresGruppen.setPrimaryKey(columnAgeGroupsAge);
        hAltersgruppenJahresGruppen.setQuery(queryAgeGroups);

        Level lAltersgruppeH9 = RolapMappingFactory.eINSTANCE.createLevel();
        lAltersgruppeH9.setName("Altersgruppe");
        lAltersgruppeH9.setId("AltersgruppeH9");
        lAltersgruppeH9.setColumn(columnAgeGroupsH9);
        lAltersgruppeH9.setOrdinalColumn(columnAgeGroupsH9Order);
        hAltersgruppenKinder.getLevels().add(lAltersgruppeH9);

        Level lAlterH9 = RolapMappingFactory.eINSTANCE.createLevel();
        lAlterH9.setName("Alter");
        lAlterH9.setId("AlterH9");
        lAlterH9.setColumn(columnAgeGroupsAge);
        hAltersgruppenJahresGruppen.getLevels().add(lAlterH9);

        cube.getDimensionConnectors().addAll(List.of(dcJahr, dcStatistischerBezirk, dcGeschlecht, dcAlter));

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Einwohnerzahl");
        measure.setColumn(columnEinwohnerAnzahl);
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Bevolkerung");
        catalog.setDescription("Statistische Daten, wie");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        resourceRolap.getContents()
                .addAll(List.of(lJahr, lStadt, lPlanungsraum, lStatistischerBezirk, lGeschlecht, lAlter,
                        lAltersgruppeH1, lAlterH1, lAltersgruppeH2, lAlterH2, lAltersgruppeH7, lAlterH7,
                        lAltersgruppeH8, lAlterH8, lAltersgruppeH9, lAlterH9));

        resourceRolap.getContents()
                .addAll(List.of(hJahr, hStadtPlanungsraumStatistischerBezirk, hGeschlecht, hAlter,
                        hAltersgruppenStandard, hAltersgruppenKinder, hAltersgruppenSystematikRKI,
                        hAltersgruppenCovidstatistik, hAltersgruppenJahresGruppen));

        resourceRolap.getContents().addAll(List.of(dJahr, dStatistischerBezirk, dGeschlecht, dAlter));
        resourceRolap.getContents().add(catalog);

        resourceRolap.save(Map.of());
        System.out.println(fileRolap.toAbsolutePath());
        System.out.println(Files.readString(fileRolap, StandardCharsets.UTF_8));

    }

    @Test
    @Order(2)
    public void read(@InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
            + RolapMappingPackage.eNAME + ")") ResourceSet resourceSet) throws IOException {

        URI uriRdbs = URI.createFileURI(fileRdbs.toAbsolutePath().toString());
        URI uriRolap = URI.createFileURI(fileRolap.toAbsolutePath().toString());
        Resource resourceRdbs = resourceSet.getResource(uriRdbs, true);
        resourceRdbs.load(Map.of());
        Resource resourceRolap = resourceSet.getResource(uriRolap, true);
        resourceRolap.load(Map.of());
        EList<EObject> conents = resourceRolap.getContents();

    }

}
