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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.calculatedmember.intro;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.6", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This tutorial discusses Calculated Members, which allow you to define members in the measure or dimension area of a cube without storing them directly in the database. Instead, these members are computed on the fly, often based on the values of other members or measures. This is particularly useful for creating derived measures or dimension members that are not present in the underlying data source.

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
            This calculated member only coes a calculation with both of the existing Measures. The Forula holds the calculation instruction. The Formula Expression is a MDX expression.
            """;
    private static final String cm2Body = """
            This calculated member has also a Formula. Additionaly it references the Hierarchy where it should be addes and a Parent Expression that defins unter wich Element it should be added. The Parent Expression is a MDX expression.

            """;
    private static final String cubeBody = """
            The cube is defines by the DimensionConnector and the MeasureGroup and most importantly the calculated members.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_calculatedMemberIntro");

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
        measureSum.setName("Measure1-Sum");
        measureSum.setId("_measure_measure1Sum");
        measureSum.setColumn(valueColumn);

        CountMeasure measureCount = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure2-Count");
        measureCount.setId("_measure_measure2Count");
        measureCount.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

//        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE
//        .createCalculatedMemberProperty();
//        calculatedMemberProperty.setId("_cmp_1");
//        calculatedMemberProperty.setName("FORMAT_STRING");
//        calculatedMemberProperty.setValue("0.0%");

        CalculatedMember calculatedMember1 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("Calculated Member 1");
        calculatedMember1.setId("_calculatedMember_calculatedMember1");
        calculatedMember1.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
//        calculatedMember1.getCalculatedMemberProperties().add(calculatedMemberProperty);

        CalculatedMember calculatedMember2 = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("Calculated Member 2");
        calculatedMember2.setId("_calculatedMember_calculatedMember2");
        calculatedMember2.setHierarchy(hierarchy);
        calculatedMember2.setParent("[theDimension].[theHierarchy].[All theHierarchys]");
        calculatedMember2.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
//        calculatedMember2.getCalculatedMemberProperties().add(calculatedMemberProperty);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube CalculatedMember");
        cube.setId("_cube_calculatedMemberCube");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember1, calculatedMember2));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube - CalculatedMembers Intro");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube - CalculatedMembers Intro", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level, "Level", levelBody, 1, 3, 0, true, 0);

        document(hierarchy, "Hierarchy without hasAll Level", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 0);

        document(calculatedMember1, "Calculated Member in Measure", cm1Body, 1, 6, 0, true, 0);
        document(calculatedMember2, "Calculated Member in Dimension", cm2Body, 1, 7, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 8, 0, true, 2);

        return catalog;

    }

}
