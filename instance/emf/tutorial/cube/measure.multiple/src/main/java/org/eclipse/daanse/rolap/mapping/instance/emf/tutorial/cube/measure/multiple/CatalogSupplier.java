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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.multiple;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
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
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.02.02", source = Source.EMF, group = "Measure")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private PhysicalCube cube;
    private Schema databaseSchema;


    private static final String introBody = """
            Data cubes can have multiple measures to provide different data related to the cube's topic. This is particularly useful when aggregating different data columns within the same cube.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains four columns: `KEY`, `VALUE1`, `VALUE2`, and `VALUE3`. The `KEY` column acts as a discriminator, while the `VALUE1`, `VALUE2`, and `VALUE3` columns contain the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table Fact.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined:
            - The first measure references the `VALUE1` column.
            - The second measure references the `VALUE2` column.
            - The third measure references the `VALUE3` column.
            All measures use sum aggregation.
            """;

    private static final String defaultMeasureBody = """
            Specifying `defaultMeasure` in the `Cube` element allows users to explicitly set a base measure as the default. If `defaultMeasure` is not specified, the first measure in the list is automatically considered the default measure.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column value1Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value1Column.setName("VALUE1");
        value1Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column value2Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value2Column.setName("VALUE2");
        value2Column.setType(SqlSimpleTypes.Sql99.integerType());

        Column value3Column = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        value3Column.setName("VALUE3");
        value3Column.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, value1Column, value2Column, value3Column));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Sum of Value1");
        measure1.setColumn(value1Column);

        SumMeasure measure2 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure2.setName("Sum of Value2");
        measure2.setColumn(value2Column);

        SumMeasure measure3 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure3.setName("Sum of Value3");
        measure3.setColumn(value3Column);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MultipleMeasuresCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.setDefaultMeasure(measure3);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureMultipleMeasures");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Multiple");
        catalog.setDescription("Multiple measures in cubes");
        catalog.getCubes().add(cube);


            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Measure Multiple", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Measures", cubeBody, 1, 3, 0, cube, 2),
                        new DocSection("DefaultMeasure", defaultMeasureBody, 1, 4, 0, null, 0)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
