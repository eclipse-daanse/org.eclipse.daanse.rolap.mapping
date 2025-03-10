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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcubemeasureonly;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_20-01_VirtualCube_with_Measure_only";
    private static final String CUBE1 = "Cube1";
    private static final String CUBE2 = "Cube2";
    private static final String C1_FACT = "C1_Fact";
    private static final String C2_FACT = "C2_Fact";

    private static final String schemaDocumentationTxt = """
            Virtual cube example with measures
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column key1Column = RolapMappingFactory.eINSTANCE.createColumn();
        key1Column.setName("KEY");
        key1Column.setId("C1_Fact_KEY");
        key1Column.setType(ColumnType.VARCHAR);

        Column value1Column = RolapMappingFactory.eINSTANCE.createColumn();
        value1Column.setName("VALUE");
        value1Column.setId("C1_Fact_VALUE");
        value1Column.setType(ColumnType.INTEGER);

        PhysicalTable c1Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        c1Table.setName(C1_FACT);
        c1Table.setId(C1_FACT);
        c1Table.getColumns().addAll(List.of(key1Column, value1Column));
        databaseSchema.getTables().add(c1Table);

        Column key2Column = RolapMappingFactory.eINSTANCE.createColumn();
        key2Column.setName("KEY");
        key2Column.setId("C2_Fact_KEY");
        key2Column.setType(ColumnType.VARCHAR);

        Column value2Column = RolapMappingFactory.eINSTANCE.createColumn();
        value2Column.setName("VALUE");
        value2Column.setId("C2_Fact_VALUE");
        value2Column.setType(ColumnType.INTEGER);

        PhysicalTable c2Table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        c2Table.setName(C2_FACT);
        c2Table.setId(C2_FACT);
        c2Table.getColumns().addAll(List.of(key2Column, value2Column));
        databaseSchema.getTables().add(c2Table);

        TableQuery query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query1.setId("c1TableQuery");
        query1.setTable(c1Table);

        TableQuery query2 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query2.setId("c2TableQuery");
        query2.setTable(c2Table);

        Measure measure1 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure1.setAggregator(MeasureAggregator.SUM);
        measure1.setName("C1-Measure-Sum");
        measure1.setId("C1-Measure-Sum");
        measure1.setColumn(value1Column);

        Measure measure2 = RolapMappingFactory.eINSTANCE.createMeasure();
        measure2.setAggregator(MeasureAggregator.SUM);
        measure2.setName("C2-Measure-Sum");
        measure2.setId("C2-Measure-Sum");
        measure2.setColumn(value2Column);

        MeasureGroup measureGroup1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure1);

        MeasureGroup measureGroup2 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup2.getMeasures().add(measure2);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId(CUBE1);
        cube1.setQuery(query1);
        cube1.getMeasureGroups().add(measureGroup1);

        PhysicalCube cube2 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube2.setName(CUBE2);
        cube2.setId(CUBE2);
        cube2.setQuery(query2);
        cube2.getMeasureGroups().add(measureGroup2);

        measureGroup1.setPhysicalCube(cube1);
        measureGroup2.setPhysicalCube(cube2);

        CubeConnector cubeConnector1 = RolapMappingFactory.eINSTANCE.createCubeConnector();
        cubeConnector1.setCube(cube1);

        CubeConnector cubeConnector2 = RolapMappingFactory.eINSTANCE.createCubeConnector();
        cubeConnector2.setCube(cube2);

        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.setName("Calculation1");
        calculatedMember.setFormula("[Measures].[C1-Measure-Sum] + [Measures].[C2-Measure-Sum]");
        VirtualCube vCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        vCube.setName("VirtualCubeMeasureOnly");
        vCube.setId("VirtualCubeMeasureOnly");
        vCube.getCubeUsages().addAll(List.of(cubeConnector1, cubeConnector2));
        vCube.getReferencedMeasures().addAll(List.of(measure1, measure2));
        vCube.getCalculatedMembers().add(calculatedMember);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Virtual_Cubes_With_Measures_only");
        catalog.setDescription("Schema of a minimal virtual cube with measures");
        catalog.getCubes().addAll(List.of(cube1, cube2, vCube));
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
