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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.bit;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.BitAggMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.BitAggType;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.02.06", source = Source.EMF, group = "Measure")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private Schema databaseSchema;


    private static final String introBody = """
            Data cubes have multiple measures with different bit aggregations are required for a column.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a single table that stores all the data. The table, named `Fact`, contains two columns: `KEY` and `VALUE`. The `KEY` column acts as a discriminator, while the `VALUE` column holds the measurements to be aggregated.
            """;

    private static final String queryBody = """
            This example uses a TableQuery, as it directly references the physical table `Fact`.
            """;

    private static final String cubeBody = """
            In this example, multiple measures are defined. All measures reference the `VALUE` column and use the following aggregation functions:
            - BIT AGG AND – bit aggregation 'and'.
            - BIT AGG OR  – bit aggregation 'or'.
            - BIT AGG XOR  – bit aggregation 'xor'.
            - BIT AGG NAND – bit aggregation 'nand'.
            - BIT AGG NOR  – bit aggregation 'nor'.
            - BIT AGG NXOR  – bit aggregation 'nxor'.
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

        BitAggMeasure measure1 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure1.setName("BitAgg AND");
        measure1.setAggType(BitAggType.AND);
        measure1.setColumn(valueColumn);

        BitAggMeasure measure2 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure2.setName("BitAgg OR");
        measure2.setAggType(BitAggType.OR);
        measure2.setColumn(valueColumn);

        BitAggMeasure measure3 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure3.setName("BitAgg XOR");
        measure3.setAggType(BitAggType.XOR);
        measure3.setColumn(valueColumn);

        BitAggMeasure measure4 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure4.setName("BitAgg NAND");
        measure4.setAggType(BitAggType.AND);
        measure4.setNot(true);
        measure4.setColumn(valueColumn);

        BitAggMeasure measure5 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure5.setName("BitAgg NOR");
        measure5.setAggType(BitAggType.OR);
        measure5.setNot(true);
        measure5.setColumn(valueColumn);

        BitAggMeasure measure6 = MeasureFactory.eINSTANCE.createBitAggMeasure();
        measure6.setName("BitAgg NXOR");
        measure6.setAggType(BitAggType.XOR);
        measure6.setNot(true);
        measure6.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(measure1, measure2, measure3, measure4, measure5, measure6));

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("MeasuresAggregatorsCube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setId("_catalog_measureBitAggregators");
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Measure Aggregator Bit");
        catalog.setDescription("Bitwise aggregation functions");
        catalog.getCubes().add(cube);

        return catalog;

    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Multiple Measures with Bit Aggragators", introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
