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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.smallintasbooleantype;


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
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.02", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private TableSource query;
    private SumMeasure measure;
    private Level level;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Minimal Cube with level with smallInt column as boolan type
            Cube have level which use smallInt column and have boolean ColumnType
            """;

    private static final String databaseSchemaBody = """
            Schema includes physical table `Fact`.
            Table, named `Fact`, contains 3 columns: `KEY`, `VALUE` and `FLAG`.
            """;

    private static final String queryFactBody = """
            This example uses a TableQuery, as it directly references the table `Fact`.
            """;

    private static final String levelBody = """
            The Level uses the column attribute to specify the primary key column.
            Column have SMALLINT type but level have ColumnType attribute with boolean type.
            This makes it possible to use level as boolean.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists of level Level.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the queryHierarchy Join-query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measureBody = """
            Measure use fact table column with sum aggregation.
    """;

    private static final String cubeBody = """
            In this example uses cube with level which have "boolean" type. But in database we have "smallInt".
            This makes it possible to use level as boolean.
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

        Column flagColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        flagColumn.setName("FLAG");
        flagColumn.setType(SqlSimpleTypes.Sql99.smallintType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn, flagColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("_Level");
        level.setColumnType(ColumnInternalDataType.BOOLEAN);
        level.setColumn(flagColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level Small Int As Boolean Type");
        catalog.setDescription("Level using small integer as boolean type");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level Small Int As Boolean Type", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryFactBody, 1, 2, 0, query, 2),
                        new DocSection("Level", levelBody, 1, 3, 0, level, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 4, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 5, 0, dimension, 0),
                        new DocSection("Measure", measureBody, 1, 6, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 7, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
