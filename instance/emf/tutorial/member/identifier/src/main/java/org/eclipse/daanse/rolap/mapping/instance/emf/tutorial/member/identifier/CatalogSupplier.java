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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.identifier;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.06.01", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level level2;
    private PhysicalCube cube;
    private TableSource query;


    private static final String introBody = """
            In some cases, all data are stored in one table, the fact as well as multiple levels. This Tutorial shows how to handle this case with different cases of data.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on only one tables with different cases of data
            space, space first, space last, separate line, long with over 100 12345678....   , äüö, some russian latter, some french letters, @€
            """;

    private static final String level1Body = """
            The level of the level1 used the `column` attribute to define the column that holds the name, which is also the key Column.
            """;

    private static final String level2Body = """
            The level  of the level2 used the `column` attribute to define the column that holds the name, which is also the key Column.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains both defined levels. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used.
            """;

    private static final String queryFactBody = """
            The TableSource for the Levels and the Measure.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column columnKey1 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKey1.setName("KEY1");
        columnKey1.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnValue = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnValue.setName("VALUE");
        columnValue.setType(SqlSimpleTypes.Sql99.integerType());

        Column columnKey2 = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKey2.setName("KEY2");
        columnKey2.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableFact = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableFact.setName("Fact");
        tableFact.getFeature().addAll(List.of(columnKey1,columnKey2, columnValue));
        databaseSchema.getOwnedElement().add(tableFact);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(tableFact);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(columnValue);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("level1");
        level1.setColumn(columnKey1);

        level2 = LevelFactory.eINSTANCE.createLevel();
        level2.setName("level2");
        level2.setColumn(columnKey2);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(columnKey1);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level1);
        hierarchy.getLevels().add(level2);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnKey1);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Member Identifier");
        catalog.setDescription("Member identifier configurations");
        catalog.getCubes().add(cube);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Member Identifier", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryFactBody, 1, 3, 0, query, 2),
                        new DocSection("Level1", level1Body, 1, 4, 0, level1, 0),
                        new DocSection("Level2", level2Body, 1, 5, 0, level2, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 6, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 7, 0, dimension, 0),
                        new DocSection("Cube with different member identifiers", cubeBody, 1, 7, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
