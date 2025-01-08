/*
* Copyright (c) 2024 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.Test;
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
public class ResourceSetTest {

    private static String BASE_DIR = System.getProperty("basePath");

    @Test
    public void resourceSetExistsTest(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        URI uri = URI.createURI(BASE_DIR + "/src/test/resources/RolapContext.xmi");
        Resource resource = rs.getResource(uri, true);
        resource.load(Map.of());
        EObject root = resource.getContents().get(0);
        System.out.println(root);

    }

    @TempDir
    Path tempDir;

    @Test
    public void write(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        Path file = Files.createTempFile(tempDir, "out", ".xmi");
        URI uri = URI.createFileURI(file.toAbsolutePath().toString());
        Resource resource = rs.createResource(uri);
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());

        resource.save(Map.of());
        System.out.println(Files.readString(file));


    }

    @Test
    public void writeCube1(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        Path file = Files.createTempFile(tempDir, "out", ".xmi");
        URI uri = URI.createFileURI(file.toAbsolutePath().toString());
        Resource resource = rs.createResource(uri);
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setName("test");
        databaseSchema.setId("1");
        Column columnKey = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnKey.setName("KEY");
        columnKey.setId("1");
        columnKey.setType("String");
        Column columnValue = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnValue.setName("VALUE");
        columnValue.setId("2");
        columnValue.setType("Integer");
        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("1");
        table.getColumns().add(columnKey);
        table.getColumns().add(columnValue);
        databaseSchema.getTables().add(table);
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);
        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("01_Minimal_Cube_With_One_Measure");
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure-Sum");
        measure.setColumn(columnValue);
        measureGroup.getMeasures().add(measure);
        cube.setName("CubeOneMeasure");
        cube.setId("1");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        catalog.getCubes().add(cube);
        schema.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);
        catalog.getSchemas().add(schema);
        resource.getContents().add(catalog);
        resource.getContents().add(databaseSchema);
        resource.save(Map.of());
        System.out.println(Files.readString(file));
    }

    @Test
    public void writePopulation(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        Path fileRolap = Files.createTempFile(tempDir, "rolap", ".xmi");
        URI uriRolap = URI.createFileURI(fileRolap.toAbsolutePath().toString());
        Resource resourceRolap = rs.createResource(uriRolap);

        Path fileRdbs = Files.createTempFile(tempDir, "rdbs", ".xmi");
        URI uriRdbs = URI.createFileURI(fileRdbs.toAbsolutePath().toString());
        Resource resourceRdbs = rs.createResource(uriRdbs);

        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setName("jena.statistik");
        databaseSchema.setId("jena.statistik_id");

        //einwohner table start
        Column columnEinwohnerJahr = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerJahr.setName("JAHR");
        columnEinwohnerJahr.setId("EinwohnerJAHR");
        columnEinwohnerJahr.setType("Integer");

        Column columnEinwohnerStatbez = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerStatbez.setName("STATBEZ");
        columnEinwohnerStatbez.setId("EinwohnerSTATBEZ");
        columnEinwohnerStatbez.setType("Integer");

        Column columnEinwohnerKerGesch = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerKerGesch.setName("KER_GESCH");
        columnEinwohnerKerGesch.setId("EinwohnerKerGesch");
        columnEinwohnerKerGesch.setType("Text");

        Column columnEinwohnerAge = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerAge.setName("AGE");
        columnEinwohnerAge.setId("EinwohnerAge");
        columnEinwohnerAge.setType("Integer");

        Column columnEinwohnerAnzahl = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerAnzahl.setName("Anzahl");
        columnEinwohnerAnzahl.setId("EinwohnerAnzahl");
        columnEinwohnerAnzahl.setType("Integer");

        Column columnEinwohnerGeojson = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnEinwohnerGeojson.setName("GEOJSON");
        columnEinwohnerGeojson.setId("EinwohnerGeojson");
        columnEinwohnerGeojson.setType("Text");

        PhysicalTable tableEinwohner = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableEinwohner.setName("einwohner");
        tableEinwohner.setId("einwohner");
        tableEinwohner.getColumns().addAll(List.of(columnEinwohnerJahr, columnEinwohnerStatbez, columnEinwohnerKerGesch, columnEinwohnerAge, columnEinwohnerAnzahl, columnEinwohnerGeojson));
        databaseSchema.getTables().add(tableEinwohner);

        TableQuery queryEinwohner = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryEinwohner.setTable(tableEinwohner);
        //einwohner table end

        //year table start
        Column columnYearYear = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnYearYear.setName("year");
        columnYearYear.setId("YearYear");
        columnYearYear.setType("Integer");

        Column columnYearOrdinal = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnYearOrdinal.setName("ordinal");
        columnYearOrdinal.setId("YearOrdinal");
        columnYearOrdinal.setType("Integer");

        PhysicalTable tableYear = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableYear.setName("year");
        tableYear.setId("year");
        tableYear.getColumns().addAll(List.of(columnYearYear, columnYearOrdinal));
        databaseSchema.getTables().add(tableYear);

        TableQuery queryYear = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryYear.setTable(tableYear);
        //year table end

        //statbez table start
        Column columnStatbezGid = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezGid.setName("gid");
        columnStatbezGid.setId("StatbezGid");
        columnStatbezGid.setType("Integer");

        Column columnStatbezPlraum = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezPlraum.setName("plraum");
        columnStatbezPlraum.setId("StatbezPlraum");
        columnStatbezPlraum.setType("Integer");

        Column columnStatbezStatbezName = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezStatbezName.setName("statbez_name");
        columnStatbezStatbezName.setId("StatbezStatbezName");
        columnStatbezStatbezName.setType("Text");

        Column columnStatbezTheGeom = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezTheGeom.setName("the_geom");
        columnStatbezTheGeom.setId("StatbezTheGeom");
        columnStatbezTheGeom.setType("Text");

        Column columnStatbezUuid = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezUuid.setName("uuid");
        columnStatbezUuid.setId("StatbezUuid");
        columnStatbezUuid.setType("Text");

        Column columnStatbezGeojson = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnStatbezGeojson.setName("geojson");
        columnStatbezGeojson.setId("StatbezGeojson");
        columnStatbezGeojson.setType("Text");

        PhysicalTable tableStatbez = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableStatbez.setName("statbez");
        tableStatbez.setId("statbez");
        tableStatbez.getColumns().addAll(List.of(columnStatbezGid, columnStatbezPlraum, columnStatbezStatbezName, columnStatbezTheGeom, columnStatbezUuid, columnStatbezGeojson));
        databaseSchema.getTables().add(tableStatbez);

        TableQuery queryStatbez = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryStatbez.setTable(tableStatbez);
        //statbez table end

        //plraum  table start
        Column columnPlraumGid = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumGid.setName("gid");
        columnPlraumGid.setId("PlraumGid");
        columnPlraumGid.setType("Integer");

        Column columnPlraumPlraum = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumPlraum.setName("plraum");
        columnPlraumPlraum.setId("PlraumPlraum");
        columnPlraumPlraum.setType("Text");

        Column columnPlraumTheGeom = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumTheGeom.setName("the_geom");
        columnPlraumTheGeom.setId("PlraumTheGeom");
        columnPlraumTheGeom.setType("Text");

        Column columnPlraumPlraumNr = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumPlraumNr.setName("plraum_nr");
        columnPlraumPlraumNr.setId("PlraumPlraumNr");
        columnPlraumPlraumNr.setType("Integer");

        Column columnPlraumUuid = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumUuid.setName("uuid");
        columnPlraumUuid.setId("PlraumUuid");
        columnPlraumUuid.setType("Text");

        Column columnPlraumGeojson = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumGeojson.setName("geojson");
        columnPlraumGeojson.setId("PlraumGeojson");
        columnPlraumGeojson.setType("Text");

        Column columnPlraumTownid = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnPlraumTownid.setName("townid");
        columnPlraumTownid.setId("PlraumTownid");
        columnPlraumTownid.setType("Integer");

        PhysicalTable tablePlraum = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tablePlraum.setName("plraum");
        tablePlraum.setId("plraum");
        tablePlraum.getColumns().addAll(List.of(columnPlraumGid, columnPlraumPlraum, columnPlraumTheGeom, columnPlraumUuid, columnPlraumGeojson, columnPlraumTownid));
        databaseSchema.getTables().add(tablePlraum);

        TableQuery queryPlraum = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryPlraum.setTable(tablePlraum);
        //plraum  table end

        //town  table start
        Column columnTownId = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnTownId.setName("id");
        columnTownId.setId("TownId");
        columnTownId.setType("Integer");

        Column columnTownName = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnTownName.setName("name");
        columnTownName.setId("TownName");
        columnTownName.setType("Text");

        Column columnTownGeojson = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnTownGeojson.setName("geojson");
        columnTownGeojson.setId("TownGeojson");
        columnTownGeojson.setType("Text");

        PhysicalTable tableTown = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableTown.setName("town");
        tableTown.setId("town");
        tableTown.getColumns().addAll(List.of(columnTownId, columnTownName));
        databaseSchema.getTables().add(tableTown);

        TableQuery queryTown = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryTown.setTable(tableTown);
        //town  table end

        //gender table start
        Column columnGenderKey = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnGenderKey.setName("key");
        columnGenderKey.setId("GenderKey");
        columnGenderKey.setType("Text");

        Column columnGenderName = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnGenderName.setName("name");
        columnGenderName.setId("GenderName");
        columnGenderName.setType("Text");

        PhysicalTable tableGender = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableGender.setName("gender");
        tableGender.setId("gender");
        tableGender.getColumns().addAll(List.of(columnGenderKey, columnGenderName));
        databaseSchema.getTables().add(tableGender);

        TableQuery queryGender = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryGender.setTable(tableGender);
        //gender table end

        //AgeGroups table start
        Column columnAgeGroupsAge = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsAge.setName("Age");
        columnAgeGroupsAge.setId("AgeGroupsAge");
        columnAgeGroupsAge.setType("Integer");

        Column columnAgeGroupsH1 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH1.setName("H1");
        columnAgeGroupsH1.setId("AgeGroupsH1");
        columnAgeGroupsH1.setType("Text");

        Column columnAgeGroupsH1Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH1Order.setName("H1_Order");
        columnAgeGroupsH1Order.setId("AgeGroupsH1Order");
        columnAgeGroupsH1Order.setType("Integer");

        Column columnAgeGroupsH2 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH2.setName("H2");
        columnAgeGroupsH2.setId("AgeGroupsH2");
        columnAgeGroupsH2.setType("Text");

        Column columnAgeGroupsH2Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH2Order.setName("H2_Order");
        columnAgeGroupsH2Order.setId("AgeGroupsH2Order");
        columnAgeGroupsH2Order.setType("Integer");

        Column columnAgeGroupsH3 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH3.setName("H3");
        columnAgeGroupsH3.setId("AgeGroupsH3");
        columnAgeGroupsH3.setType("Text");

        Column columnAgeGroupsH3Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH3Order.setName("H3_Order");
        columnAgeGroupsH3Order.setId("AgeGroupsH3Order");
        columnAgeGroupsH3Order.setType("Integer");

        Column columnAgeGroupsH4 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH4.setName("H4");
        columnAgeGroupsH4.setId("AgeGroupsH4");
        columnAgeGroupsH4.setType("Text");

        Column columnAgeGroupsH4Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH4Order.setName("H4_Order");
        columnAgeGroupsH4Order.setId("AgeGroupsH4Order");
        columnAgeGroupsH4Order.setType("Integer");

        Column columnAgeGroupsH5 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH5.setName("H5");
        columnAgeGroupsH5.setId("AgeGroupsH5");
        columnAgeGroupsH5.setType("Text");

        Column columnAgeGroupsH5Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH5Order.setName("H5_Order");
        columnAgeGroupsH5Order.setId("AgeGroupsH5Order");
        columnAgeGroupsH5Order.setType("Integer");

        Column columnAgeGroupsH6 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH6.setName("H6");
        columnAgeGroupsH6.setId("AgeGroupsH6");
        columnAgeGroupsH6.setType("Text");

        Column columnAgeGroupsH6Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH6Order.setName("H6_Order");
        columnAgeGroupsH6Order.setId("AgeGroupsH6Order");
        columnAgeGroupsH6Order.setType("Integer");

        Column columnAgeGroupsH7 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH7.setName("H7");
        columnAgeGroupsH7.setId("AgeGroupsH7");
        columnAgeGroupsH7.setType("Text");

        Column columnAgeGroupsH7Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH7Order.setName("H7_Order");
        columnAgeGroupsH7Order.setId("AgeGroupsH7Order");
        columnAgeGroupsH7Order.setType("Integer");

        Column columnAgeGroupsH8 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH8.setName("H8");
        columnAgeGroupsH8.setId("AgeGroupsH8");
        columnAgeGroupsH8.setType("Text");

        Column columnAgeGroupsH8Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH8Order.setName("H8_Order");
        columnAgeGroupsH8Order.setId("AgeGroupsH8Order");
        columnAgeGroupsH8Order.setType("Integer");

        Column columnAgeGroupsH9 = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH9.setName("H9");
        columnAgeGroupsH9.setId("AgeGroupsH9");
        columnAgeGroupsH9.setType("Text");

        Column columnAgeGroupsH9Order = RelationalDatabaseFactory.eINSTANCE.createColumn();
        columnAgeGroupsH9Order.setName("H9_Order");
        columnAgeGroupsH9Order.setId("AgeGroupsH9Order");
        columnAgeGroupsH9Order.setType("Integer");

        PhysicalTable tableAgeGroups = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        tableAgeGroups.setName("AgeGroups");
        tableAgeGroups.setId("AgeGroups");
        tableAgeGroups.getColumns().addAll(List.of(columnAgeGroupsAge,columnAgeGroupsH1,columnAgeGroupsH1Order,columnAgeGroupsH2,
                columnAgeGroupsH2Order,columnAgeGroupsH3,columnAgeGroupsH3Order,columnAgeGroupsH4,columnAgeGroupsH4Order,
                columnAgeGroupsH5,columnAgeGroupsH5Order,columnAgeGroupsH6,columnAgeGroupsH6Order,columnAgeGroupsH7,columnAgeGroupsH7Order,
                columnAgeGroupsH8,columnAgeGroupsH8Order,columnAgeGroupsH9,columnAgeGroupsH9Order));
        databaseSchema.getTables().add(tableAgeGroups);

        TableQuery queryAgeGroups = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryAgeGroups.setTable(tableAgeGroups);
        //AgeGroups table end

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();

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
        dJahr.setId("Jahr");
        Hierarchy hJahr = RolapMappingFactory.eINSTANCE.createHierarchy();

        Level lJahr = RolapMappingFactory.eINSTANCE.createLevel();
        lJahr.setName("Jahr");
        lJahr.setId("Jahr");
        lJahr.setColumn(columnYearYear);
        lJahr.setOrdinalColumn(columnYearOrdinal);

        dJahr.getHierarchies().add(hJahr);
        hJahr.setId("Jahr");
        hJahr.setHasAll(false);
        hJahr.setName("Jahr");
        hJahr.setQuery(queryYear);
        hJahr.setPrimaryKey(columnYearYear);
        hJahr.setDefaultMember("2023");
        hJahr.getLevels().add(lJahr);

        DimensionConnector dcStatistischerBezirk = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcStatistischerBezirk.setOverrideDimensionName("statistischer Bezirk");
        dcStatistischerBezirk.setId("StatistischerBezirk");
        dcStatistischerBezirk.setForeignKey(columnEinwohnerStatbez);
        StandardDimension dStatistischerBezirk = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcStatistischerBezirk.setDimension(dStatistischerBezirk);
        dStatistischerBezirk.setName("statistischer Bezirk");
        dStatistischerBezirk.setId("StatistischerBezirk");

        Hierarchy hStadtPlanungsraumStatistischerBezirk = RolapMappingFactory.eINSTANCE.createHierarchy();
        hStadtPlanungsraumStatistischerBezirk.setHasAll(true);
        hStadtPlanungsraumStatistischerBezirk.setName("Stadt - Planungsraum - statistischer Bezirk");
        hStadtPlanungsraumStatistischerBezirk.setId("Stadt - Planungsraum - statistischer Bezirk");
        hStadtPlanungsraumStatistischerBezirk.setPrimaryKey(columnStatbezGid);
        hStadtPlanungsraumStatistischerBezirk.setPrimaryKeyTable(tableStatbez);
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
        lStadtP.setPropertyType(ColumnDataType.STRING);

        lStadt.setName("Stadt");
        lStadt.setId("Stadt");
        lStadt.setColumn(columnTownName);
        lStadt.setTable(tableTown);
        lStadt.setOrdinalColumn(columnYearOrdinal);
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
        lPlanungsraumP2.setPropertyType(ColumnDataType.STRING);

        lPlanungsraum.setName("Planungsraum");
        lPlanungsraum.setId("Planungsraum");
        lPlanungsraum.setColumn(columnPlraumGid);
        lPlanungsraum.setTable(tablePlraum);
        lPlanungsraum.setNameColumn(columnPlraumPlraum);
        lPlanungsraum.setColumnType(ColumnDataType.INTEGER);
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
        lStatistischerBezirkP2.setPropertyType(ColumnDataType.STRING);

        lStatistischerBezirk.setName("Statistischer Bezirk");
        lStatistischerBezirk.setId("Statistischer Bezirk");
        lStatistischerBezirk.setColumn(columnStatbezGid);
        lStatistischerBezirk.setTable(tableStatbez);
        lStatistischerBezirk.setNameColumn(columnStatbezStatbezName);
        lStatistischerBezirk.setColumnType(ColumnDataType.INTEGER);
        lStatistischerBezirk.getMemberProperties().addAll(List.of(lStatistischerBezirkP1, lStatistischerBezirkP2));

        hStadtPlanungsraumStatistischerBezirk.getLevels().addAll(List.of(lStadt, lPlanungsraum, lStatistischerBezirk));

        DimensionConnector dcGeschlecht = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dcGeschlecht.setOverrideDimensionName("Geschlecht");
        dcGeschlecht.setId("Geschlecht");
        dcGeschlecht.setForeignKey(columnEinwohnerKerGesch);
        StandardDimension dGeschlecht = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dcGeschlecht.setDimension(dGeschlecht);
        dGeschlecht.setName("Geschlecht");
        dGeschlecht.setId("Geschlecht");

        Hierarchy hGeschlecht = RolapMappingFactory.eINSTANCE.createHierarchy();
        hGeschlecht.setHasAll(true);
        hGeschlecht.setName("Geschlecht (m/w/d)");
        hGeschlecht.setId("Geschlecht");
        hGeschlecht.setPrimaryKey(columnGenderKey);
        hGeschlecht.setQuery(queryGender);

        Level lGeschlecht = RolapMappingFactory.eINSTANCE.createLevel();
        lGeschlecht.setName("Geschlecht");
        lGeschlecht.setId("Geschlecht");
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

        Hierarchy hAlter = RolapMappingFactory.eINSTANCE.createHierarchy();
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

        Hierarchy hAltersgruppenStandard = RolapMappingFactory.eINSTANCE.createHierarchy();
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

        Hierarchy hAltersgruppenKinder = RolapMappingFactory.eINSTANCE.createHierarchy();
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

        Hierarchy hAltersgruppenSystematikRKI = RolapMappingFactory.eINSTANCE.createHierarchy();
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

        Hierarchy hAltersgruppenCovidstatistik = RolapMappingFactory.eINSTANCE.createHierarchy();
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

        Hierarchy hAltersgruppenJahresGruppen = RolapMappingFactory.eINSTANCE.createHierarchy();
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
        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Einwohnerzahl");
        measure.setColumn(columnEinwohnerAnzahl);
        measureGroup.getMeasures().add(measure);
        cube.getMeasureGroups().add(measureGroup);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Bevolkerung");
        schema.getCubes().add(cube);

        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);
        catalog.getSchemas().add(schema);

        resourceRolap.getContents().addAll(List.of(dJahr, dStatistischerBezirk, dGeschlecht, dAlter));
        resourceRolap.getContents().addAll(List.of(hJahr, hStadtPlanungsraumStatistischerBezirk, hGeschlecht, hAlter,
                hAltersgruppenStandard, hAltersgruppenKinder, hAltersgruppenSystematikRKI, hAltersgruppenCovidstatistik, hAltersgruppenJahresGruppen));
        resourceRolap.getContents().addAll(List.of(lJahr, lStadt, lPlanungsraum, lStatistischerBezirk, lGeschlecht, lAlter,
                lAltersgruppeH1, lAlterH1, lAltersgruppeH2, lAlterH2, lAltersgruppeH7, lAlterH7, lAltersgruppeH8, lAlterH8, lAltersgruppeH9, lAlterH9));
        resourceRolap.getContents().add(catalog);

        resourceRolap.save(Map.of());
        System.out.println(Files.readString(fileRolap, StandardCharsets.UTF_8));

        System.out.println("");

        resourceRdbs.getContents().add(databaseSchema);
        resourceRdbs.save(Map.of());
        System.out.println(Files.readString(fileRdbs, StandardCharsets.UTF_8));
    }

}
