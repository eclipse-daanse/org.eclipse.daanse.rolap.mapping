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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.CountMeasure;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.03.06", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private CalculatedMember calculatedMember2;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private CalculatedMember calculatedMember1;
    private Level level;


    private static final String introBody = """
            This tutorial discusses Calculated Members, which allow you to define members in the measure or dimension area of a cube without storing them directly in the database. Instead, these members are computed on the fly, often based on the values of other members or measures. This is particularly useful for creating derived measures or dimension members that are not present in the underlying data source.

            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with three columns: `KEY` and `VALUE` and `VALUE_NUMERIC`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;
    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;
    private static final String levelBody = """
            This Example uses one simple Level based on the `KEY` column.
            """;
    private static final String hierarchyBody = """
            The Hierarchy is defined with the hasAll property set to true and the one level.
            """;
    private static final String dimensionBody = """
            The dimension is defined with the one hierarchy. The hierarchy is used in the cube and in the calculated member.
            """;
    private static final String cm1Body = """
            This calculated member only does a calculation with both of the existing Measures. The Formula holds the calculation instruction. The Formula Expression is a MDX expression.
            """;
    private static final String cm2Body = """
            This calculated member has also a Formula. Additionaly it references the Hierarchy where it should be added and a Parent Expression that defines under which Element it should be added. The Parent Expression is a MDX expression.

            """;
    private static final String cubeBody = """
            The cube is defined by the DimensionConnector and the MeasureGroup and most importantly the calculated members.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("theLevel");
        level.setColumn(keyColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("theHierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("theDimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setForeignKey(keyColumn);
        dimensionConnector.setDimension(dimension);

        SumMeasure measureSum = MeasureFactory.eINSTANCE.createSumMeasure();
        measureSum.setName("Measure1-Sum");
        measureSum.setColumn(valueColumn);

        CountMeasure measureCount = MeasureFactory.eINSTANCE.createCountMeasure();
        measureCount.setName("Measure2-Count");
        measureCount.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measureSum, measureCount));

//        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE
//        .createCalculatedMemberProperty();
//        calculatedMemberProperty.setId("_cmp_1");
//        calculatedMemberProperty.setName("FORMAT_STRING");
//        calculatedMemberProperty.setValue("0.0%");

        calculatedMember1 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember1.setName("Calculated Member 1");
        calculatedMember1.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
//        calculatedMember1.getCalculatedMemberProperties().add(calculatedMemberProperty);

        calculatedMember2 = LevelFactory.eINSTANCE.createCalculatedMember();
        calculatedMember2.setName("Calculated Member 2");
        calculatedMember2.setHierarchy(hierarchy);
        calculatedMember2.setParent("[theDimension].[theHierarchy].[All theHierarchys]");
        calculatedMember2.setFormula("[Measures].[Measure1-Sum] / [Measures].[Measure2-Count]");
//        calculatedMember2.getCalculatedMemberProperties().add(calculatedMemberProperty);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube CalculatedMember");
        cube.setQuery(query);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(calculatedMember1, calculatedMember2));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Calculated Member Intro");
        catalog.setDescription("Introduction to calculated members in cubes");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);





            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Calculated Member Intro", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level", levelBody, 1, 3, 0, level, 0),
                        new DocSection("Hierarchy without hasAll Level", hierarchyBody, 1, 4, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 5, 0, dimension, 0),
                        new DocSection("Calculated Member in Measure", cm1Body, 1, 6, 0, calculatedMember1, 0),
                        new DocSection("Calculated Member in Dimension", cm2Body, 1, 7, 0, calculatedMember2, 0),
                        new DocSection("Cube and DimensionConnector and Measure", cubeBody, 1, 8, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
