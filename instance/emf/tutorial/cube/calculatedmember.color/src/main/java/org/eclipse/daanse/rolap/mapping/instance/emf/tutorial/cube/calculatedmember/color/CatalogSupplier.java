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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.calculatedmember.color;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.6", source = Source.EMF, group = "Cube") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial discusses Calculated Members and Measures with diferent colors.

            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with three columns: KEY and VALUE and VALUE_NUMERIC. The KEY column is used as the discriminator in the the Level and Hierarchy definitions.
            """;
    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;
    private static final String levelBody = """
            This Example uses one simple Level bases on the KEY column.
            """;
    private static final String hierarchyBody = """
            The Hierarchy is defined with the hasAll property set to true and the one level.
            """;
    private static final String dimensionBody = """
            The dimension is defined with the one hierarchy. The hierarchy is used in the cube and in the calculated member.
            """;
    private static final String cm1Body = """
            This calculated member have BACK_COLOR in format string. It show posibility to have different colors incal culated member
            """;
    private static final String cm2Body = """
            This calculated member have BACK_COLOR in format string. It show posibility to have different colors incal culated member

            """;
    private static final String cubeBody = """
            The cube is defines by the DimensionConnector and the MeasureGroup and most importantly the calculated members.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_calculatedMemberColor");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("theLevel");
        level.setId("_level_theLevel");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("theHierarchy");
        hierarchy.setId("_hierarchy_theHierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("theDimension");
        dimension.setId("_dimension_theDimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_theDimension");
        dimensionConnector.setForeignKey(keyColumn);
        dimensionConnector.setDimension(dimension);


        SumMeasure measureSum = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureSum.setName("Measure-Sum");
        measureSum.setId("_measure_measureSum");
        measureSum.setColumn(valueColumn);
        measureSum.setFormatString("$#,##0.00;BACK_COLOR=32768;FORE_COLOR=0");//green

        CountMeasure measureCount = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure-Count");
        measureCount.setId("_measure_measureCount");
        measureCount.setColumn(valueColumn);
        measureCount.setFormatString("$#,##0.00;BACK_COLOR=16711680;FORE_COLOR=0");//red

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

        CalculatedMemberProperty formatCalculatedMemberProperty1 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        formatCalculatedMemberProperty1.setName("FORMAT_STRING");
        formatCalculatedMemberProperty1.setId("_calculatedMemberProperty_format1");
        formatCalculatedMemberProperty1.setValue("$#,##0.00;BACK_COLOR=65535;FORE_COLOR=13369395");

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("Calculated Member 1");
        calculatedMember1.setDisplayFolder("folder");
        calculatedMember1.setId("_calculatedMember_calculatedMember1");
        calculatedMember1.setFormula("[Measures].[Measure-Sum] / [Measures].[Measure-Count]");
        calculatedMember1.setDisplayFolder("folder");
        calculatedMember1.getCalculatedMemberProperties().addAll(List.of(formatCalculatedMemberProperty1));
        calculatedMember1.setHierarchy(hierarchy);
        calculatedMember1.setParent("[theDimension].[theHierarchy].[All theHierarchys]");

        CalculatedMemberProperty formatCalculatedMemberProperty2 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        formatCalculatedMemberProperty2.setName("FORMAT_STRING");
        formatCalculatedMemberProperty2.setId("_calculatedMemberProperty_format2");
        formatCalculatedMemberProperty2.setValue("$#,##;BACK_COLOR=255;FORE_COLOR=13369395");

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("Calculated Member 2");
        calculatedMember2.setId("_calculatedMember_calculatedMember2");
        calculatedMember2.setFormula("[Measures].[Measure-Sum] / [Measures].[Measure-Count]");
        calculatedMember2.setDisplayFolder("folder");
        calculatedMember2.getCalculatedMemberProperties().addAll(List.of(formatCalculatedMemberProperty2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube CalculatedMember with different colors");
        cube.setId("_cube_calculatedMemberColorCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember2,calculatedMember1));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube - CalculatedMembers with different colors");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube - CalculatedMembers with different colors", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level, "Level", levelBody, 1, 3, 0, true, 0);

        document(hierarchy, "Hierarchy without hasAll Level", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 0);

        document(calculatedMember1, "Calculated Member in Measure with different colors properties", cm1Body, 1, 6, 0, true, 0);

        document(calculatedMember2, "Calculated Member in Measure with different colors properties", cm2Body, 1, 6, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 8, 0, true, 2);

        return catalog;

    }

}
