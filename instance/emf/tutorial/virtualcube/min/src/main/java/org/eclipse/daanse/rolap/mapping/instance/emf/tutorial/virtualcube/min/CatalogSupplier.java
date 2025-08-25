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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.min;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.15.1", source = Source.EMF, group = "VirtualCube") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String C1_FACT = "C1_Fact";
    private static final String C2_FACT = "C2_Fact";

    private static final String catalogBody = """
            Minimal Virtual Cubes With Measures.
            A virtual cube that combines measures and dimensions from multiple physical cubes into a unified analytical view.
            Virtual cubes enable cross-cube analysis by creating a logical integration layer over existing physical cubes,
            allowing users to analyze related metrics from different business processes in a single query
            Catalog have two physical cubes Cube1 and Cube2 and virtual cube VirtualCubeMeasureOnly.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a two tables that stores all the data.
            The table is named `C1_Fact` uses for Cube1 and contains two columns: `KEY` and `VALUE`.
            The KEY column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            The table is named `C2_Fact`uses for Cube2 and contains two columns: `KEY` and `VALUE`.
            The KEY column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            """;

    private static final String query1Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `C1_Fact`.
            """;

    private static final String query2Body = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `C2_Fact`.
            """;

    private static final String measure1Body = """
            Measure use C1_Fact table VALUE column with sum aggregation.
    """;

    private static final String measure2Body = """
            Measure use C2_Fact table VALUE column with sum aggregation.
    """;

    private static final String cube1Body = """
            In this example uses cube with fact table C1_Fact as data.
            """;

    private static final String cube2Body = """
            In this example uses cube with fact table C2_Fact as data.
            """;

    private static final String vCubeBody = """
            Virtual cube uses mesures from Cube1 and Cube2. Virtual cube has references for them.
            Also virtual cube has calculatedMember which uses measures from Cube1 and Cube2.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_min");

        Column key1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        key1Column.setName("KEY");
        key1Column.setId("_c1_fact_key");
        key1Column.setType(ColumnType.VARCHAR);

        Column value1Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value1Column.setName("VALUE");
        value1Column.setId("_c1_fact_value");
        value1Column.setType(ColumnType.INTEGER);

        PhysicalTable c1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        c1Table.setName(C1_FACT);
        c1Table.setId("_c1_fact");
        c1Table.getColumns().addAll(List.of(key1Column, value1Column));
        databaseSchema.getTables().add(c1Table);

        Column key2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        key2Column.setName("KEY");
        key2Column.setId("_c2_fact_key");
        key2Column.setType(ColumnType.VARCHAR);

        Column value2Column = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        value2Column.setName("VALUE");
        value2Column.setId("_c2_fact_value");
        value2Column.setType(ColumnType.INTEGER);

        PhysicalTable c2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        c2Table.setName(C2_FACT);
        c2Table.setId("_c2_fact");
        c2Table.getColumns().addAll(List.of(key2Column, value2Column));
        databaseSchema.getTables().add(c2Table);

        TableQuery query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query1.setId("_c1TableQuery");
        query1.setTable(c1Table);

        TableQuery query2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query2.setId("_c2TableQuery");
        query2.setTable(c2Table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("C1-Measure-Sum");
        measure1.setId("_c1-measure-sum");
        measure1.setColumn(value1Column);

        SumMeasure measure2 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure2.setName("C2-Measure-Sum");
        measure2.setId("_c2-measure-sum");
        measure2.setColumn(value2Column);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_cube1");
        cube1.setQuery(query1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId("_cube2");
        cube2.setQuery(query2);
        cube2.getMeasureGroups().add(measureGroup2);

        CubeConnector cubeConnector1 = RolapMappingFactory.eINSTANCE.createCubeConnector();
        cubeConnector1.setCube(cube1);

        CubeConnector cubeConnector2 = RolapMappingFactory.eINSTANCE.createCubeConnector();
        cubeConnector2.setCube(cube2);

        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setName("Calculation1");
        calculatedMember.setId("_calculation1");
        calculatedMember.setFormula("[Measures].[C1-Measure-Sum] + [Measures].[C2-Measure-Sum]");

        VirtualCube vCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        vCube.setName("VirtualCubeMeasureOnly");
        vCube.setId("_virtualcubemeasureonly");
        vCube.getCubeUsages().addAll(List.of(cubeConnector1, cubeConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().add(calculatedMember);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Virtual Cube Minimal");
        catalog.setDescription("Minimal virtual cube configuration");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Virtual Cube Minimal", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query1, "Query1", query1Body, 1, 2, 0, true, 2);
        document(query2, "Query2", query2Body, 1, 3, 0, true, 2);
        document(measure1, "C1-Measure-Sum", measure1Body, 1, 4, 0, true, 2);
        document(measure2, "C2-Measure-Sum", measure2Body, 1, 5, 0, true, 2);
        document(cube1, "Cube1", cube1Body, 1, 6, 0, true, 2);
        document(cube1, "Cube1", cube2Body, 1, 7, 0, true, 2);
        document(vCube, "VirtualCubeMeasureOnly", vCubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
