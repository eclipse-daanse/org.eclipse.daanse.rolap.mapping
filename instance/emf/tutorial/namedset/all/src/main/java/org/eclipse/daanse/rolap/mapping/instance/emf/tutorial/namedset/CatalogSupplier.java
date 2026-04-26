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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.namedset;


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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.NamedSet;
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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.08.01", source = Source.EMF, group = "Namedset") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private Schema databaseSchema;
    private Catalog catalog;
    private StandardDimension dimension2;
    private PhysicalCube cube;
    private StandardDimension dimension1;
    private TableSource query;
    private NamedSet namedSet2;
    private NamedSet namedSet1;
    private NamedSet namedSet3;
    private NamedSet namedSet4;
    private Level level;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses NamedSets.

    - NsWithFolderDimension1    : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel. NamedSet have folder
    - NsWithoutFolderDimension1 : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel.
    - NSInCubeWithFolder        : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube on excel. NamedSet have folder
    - NSInCubeWithoutFolder     : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.
    """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: KEY and VALUE. The KEY column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String levelBody = """
            This Example uses one simple Level1 based on the KEY column.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level2.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy.
            """;

    private static final String dimension2Body = """
            The dimension1 is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            The cube1 is defined by the DimensionConnector  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String namedSet1Body = """
            NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel. NamedSet have folder
            """;

    private static final String namedSet2Body = """
            NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel.
            """;

    private static final String namedSet3Body = """
            NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube on excel. NamedSet have folder
            """;

    private static final String namedSet4Body = """
            NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.
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
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        level = LevelFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setColumn(keyColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        dimension1 = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        dimension2 = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("Dimension2");
        dimension2.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dimension1");
        dimensionConnector1.setDimension(dimension1);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Dimension2");
        dimensionConnector2.setDimension(dimension1);

        namedSet1 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet1.setName("NsWithFolderDimension1");
        namedSet1.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");
        namedSet1.setDisplayFolder("Folder1");

        namedSet2 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet2.setName("NsWithoutFolderDimension1");
        namedSet2.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");

        namedSet3 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet3.setName("NSInCubeWithFolder");
        namedSet3.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");
        namedSet3.setDisplayFolder("Folder2");

        namedSet4 = DimensionFactory.eINSTANCE.createNamedSet();
        namedSet4.setName("NSInCubeWithoutFolder");
        namedSet4.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        cube.getNamedSets().addAll(List.of(namedSet1, namedSet2, namedSet3, namedSet4));

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Namedset All");
        catalog.setDescription("Named set configurations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Namedset All", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level1", levelBody, 1, 3, 0, level, 0),
                        new DocSection("Hierarchy1 without hasAll Level1", hierarchyBody, 1, 4, 0, hierarchy, 0),
                        new DocSection("Dimension1", dimension1Body, 1, 5, 0, dimension1, 0),
                        new DocSection("Dimension1", dimension2Body, 1, 6, 0, dimension2, 0),
                        new DocSection("NamedSet1", namedSet1Body, 1, 7, 0, namedSet1, 0),
                        new DocSection("NamedSet1", namedSet2Body, 1, 8, 0, namedSet2, 0),
                        new DocSection("NamedSet1", namedSet3Body, 1, 9, 0, namedSet3, 0),
                        new DocSection("NamedSet1", namedSet4Body, 1, 10, 0, namedSet4, 0),
                        new DocSection("Cube with NamedSets", cubeBody, 1, 11, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
