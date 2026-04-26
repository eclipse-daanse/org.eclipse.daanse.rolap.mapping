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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeConnector;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.VirtualCube;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.15.01", source = Source.EMF, group = "VirtualCube") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private SumMeasure measure2;
    private PhysicalCube cube1;
    private Schema databaseSchema;
    private Catalog catalog;
    private VirtualCube vCube;
    private TableSource query1;
    private SumMeasure measure1;
    private TableSource query2;


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
            - The table is named `C1_Fact` uses for Cube1 and contains two columns: `KEY` and `VALUE`.
            The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
            - The table is named `C2_Fact`uses for Cube2 and contains two columns: `KEY` and `VALUE`.
            The `KEY` column serves as a discriminator, while the `VALUE` column contains the measurements to be aggregated.
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
            Measure use `C1_Fact` table `VALUE` column with sum aggregation.
    """;

    private static final String measure2Body = """
            Measure use `C2_Fact` table `VALUE` column with sum aggregation.
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
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column key1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        key1Column.setName("KEY");
        key1Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column value1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value1Column.setName("VALUE");
        value1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Table c1Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        c1Table.setName(C1_FACT);
        c1Table.getFeature().addAll(List.of(key1Column, value1Column));
        databaseSchema.getOwnedElement().add(c1Table);

        Column key2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        key2Column.setName("KEY");
        key2Column.setType(SqlSimpleTypes.Sql99.varcharType());

        Column value2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value2Column.setName("VALUE");
        value2Column.setType(SqlSimpleTypes.Sql99.integerType());

        Table c2Table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        c2Table.setName(C2_FACT);
        c2Table.getFeature().addAll(List.of(key2Column, value2Column));
        databaseSchema.getOwnedElement().add(c2Table);

        query1 = SourceFactory.eINSTANCE.createTableSource();
        query1.setTable(c1Table);

        query2 = SourceFactory.eINSTANCE.createTableSource();
        query2.setTable(c2Table);

        measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("C1-Measure-Sum");
        measure1.setColumn(value1Column);

        measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("C2-Measure-Sum");
        measure2.setColumn(value2Column);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setQuery(query2);
        cube2.getMeasureGroups().add(measureGroup2);

        CubeConnector cubeConnector1 = CubeFactory.eINSTANCE.createCubeConnector();
        cubeConnector1.setCube(cube1);

        CubeConnector cubeConnector2 = CubeFactory.eINSTANCE.createCubeConnector();
        cubeConnector2.setCube(cube2);

        CalculatedMember calculatedMember = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setName("Calculation1");
        calculatedMember.setFormula("[Measures].[C1-Measure-Sum] + [Measures].[C2-Measure-Sum]");

        vCube = CubeFactory.eINSTANCE.createVirtualCube();
        vCube.setName("VirtualCubeMeasureOnly");
        vCube.getCubeUsages().addAll(List.of(cubeConnector1, cubeConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().add(calculatedMember);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Virtual Cube Minimal");
        catalog.setDescription("Minimal virtual cube configuration");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Virtual Cube Minimal", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query1", query1Body, 1, 2, 0, query1, 2),
                        new DocSection("Query2", query2Body, 1, 3, 0, query2, 2),
                        new DocSection("C1-Measure-Sum", measure1Body, 1, 4, 0, measure1, 2),
                        new DocSection("C2-Measure-Sum", measure2Body, 1, 5, 0, measure2, 2),
                        new DocSection("Cube1", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("Cube1", cube2Body, 1, 7, 0, cube1, 2),
                        new DocSection("VirtualCubeMeasureOnly", vCubeBody, 1, 7, 0, vCube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
