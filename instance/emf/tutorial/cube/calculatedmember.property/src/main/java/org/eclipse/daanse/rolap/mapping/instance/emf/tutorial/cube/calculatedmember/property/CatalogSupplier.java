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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.calculatedmember.property;

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
import org.eclipse.daanse.rolap.mapping.model.CountMeasure;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.6", source = Source.EMF, group = "Cube") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial discusses Calculated Members with properties `MEMBER_ORDINAL` and `FORMAT_STRING`, which allow you to define members in the measure or dimension area of a cube without storing them directly in the database. Instead, these members are computed on the fly, often based on the values of other members or measures. This is particularly useful for creating derived measures or dimension members that are not present in the underlying data source.

            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with three columns: `KEY` and `VALUE` and `VALUE_NUMERIC`. The `KEY` column is used as the discriminator in the the Level and Hierarchy definitions.
            """;
    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the `Fact` table to use in in the hierarchy and in the cube for the measures.
            """;
    private static final String levelBody = """
            This Example uses one simple Level bases on the `KEY` column.
            """;
    private static final String hierarchyBody = """
            The Hierarchy is defined with the hasAll property set to true and the one level.
            """;
    private static final String dimensionBody = """
            The dimension is defined with the one hierarchy. The hierarchy is used in the cube and in the calculated member.
            """;
    private static final String cm1Body = """
            This calculated member only coes a calculation with both of the existing Measures. The Forula holds the calculation instruction. The Formula Expression is a MDX expression. Member had properties MEMBER_ORDINAL = 1 and FORMAT_STRING
            """;
    private static final String cm2Body = """
            This calculated member only coes a calculation with both of the existing Measures. The Forula holds the calculation instruction. The Formula Expression is a MDX expression. Member had properties MEMBER_ORDINAL = 2 and FORMAT_STRING

            """;
    private static final String cubeBody = """
            The cube is defines by the DimensionConnector and the MeasureGroup and most importantly the calculated members.
            """;

    @Override
    public Catalog get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_calculatedMemberProperty");

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

        CalculatedMemberProperty memberOrdinalCalculatedMemberProperty1 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdinalCalculatedMemberProperty1.setName("MEMBER_ORDINAL");
        memberOrdinalCalculatedMemberProperty1.setId("_calculatedMemberProperty_memberOrdinal1");
        memberOrdinalCalculatedMemberProperty1.setValue("3");

        SumMeasure measureSum = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measureSum.setName("Measure-Sum");
        measureSum.setId("_measure_measureSum");
        measureSum.setColumn(valueColumn);
        measureSum.getCalculatedMemberProperties().add(memberOrdinalCalculatedMemberProperty1);

        CalculatedMemberProperty memberOrdinalCalculatedMemberProperty2 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdinalCalculatedMemberProperty2.setName("MEMBER_ORDINAL");
        memberOrdinalCalculatedMemberProperty2.setId("_calculatedMemberProperty_memberOrdinal2");
        memberOrdinalCalculatedMemberProperty2.setValue("4");

        CountMeasure measureCount = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure-Count");
        measureCount.setId("_measure_measureCount");
        measureCount.setColumn(valueColumn);
        measureCount.getCalculatedMemberProperties().add(memberOrdinalCalculatedMemberProperty2);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

        CalculatedMemberProperty memberOrdinalCalculatedMemberProperty3 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdinalCalculatedMemberProperty3.setName("MEMBER_ORDINAL");
        memberOrdinalCalculatedMemberProperty3.setId("_calculatedMemberProperty_memberOrdinal3");
        memberOrdinalCalculatedMemberProperty3.setValue("1");

        CalculatedMemberProperty memberOrdina1lCalculatedMemberProperty1 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdina1lCalculatedMemberProperty1.setName("FORMAT_STRING");
        memberOrdina1lCalculatedMemberProperty1.setId("_calculatedMemberProperty_format1");
        memberOrdina1lCalculatedMemberProperty1.setValue("$#,##0.00");

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("Calculated Member 1");
        calculatedMember1.setDisplayFolder("folder");
        calculatedMember1.setId("_calculatedMember_calculatedMember1");
        calculatedMember1.setFormula("[Measures].[Measure-Sum] / [Measures].[Measure-Count]");
        calculatedMember1.setDisplayFolder("folder");
        calculatedMember1.getCalculatedMemberProperties().addAll(List.of(memberOrdinalCalculatedMemberProperty3, memberOrdina1lCalculatedMemberProperty1));


        CalculatedMemberProperty memberOrdinalCalculatedMemberProperty4 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdinalCalculatedMemberProperty4.setName("MEMBER_ORDINAL");
        memberOrdinalCalculatedMemberProperty4.setId("_calculatedMemberProperty_memberOrdinal4");
        memberOrdinalCalculatedMemberProperty4.setValue("2");

        CalculatedMemberProperty memberOrdina1lCalculatedMemberProperty2 = RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        memberOrdina1lCalculatedMemberProperty2.setName("FORMAT_STRING");
        memberOrdina1lCalculatedMemberProperty2.setId("_calculatedMemberProperty_format2");
        memberOrdina1lCalculatedMemberProperty2.setValue("$#,##");

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("Calculated Member 2");
        calculatedMember2.setId("_calculatedMember_calculatedMember2");
        calculatedMember2.setFormula("[Measures].[Measure-Sum] / [Measures].[Measure-Count]");
        calculatedMember2.setDisplayFolder("folder");
        calculatedMember2.getCalculatedMemberProperties().addAll(List.of(memberOrdinalCalculatedMemberProperty4, memberOrdina1lCalculatedMemberProperty2));

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube CalculatedMember with properties");
        cube.setId("_cube_calculatedMemberPropertyCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember2,calculatedMember1));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Cube Calculated Member Property");
        catalog.setDescription("Properties for calculated members");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Cube Calculated Member Property", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level, "Level", levelBody, 1, 3, 0, true, 0);

        document(hierarchy, "Hierarchy without hasAll Level", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 0);

        document(calculatedMember1, "Calculated Member in Measure with properties", cm1Body, 1, 6, 0, true, 0);

        document(calculatedMember2, "Calculated Member in Measure with properties", cm2Body, 1, 6, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 8, 0, true, 2);

        return catalog;

    }

}
