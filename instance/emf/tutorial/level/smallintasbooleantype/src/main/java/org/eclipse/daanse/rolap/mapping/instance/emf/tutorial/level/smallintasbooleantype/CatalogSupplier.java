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

import static org.eclipse.daanse.rolap.mapping.model.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.model.ColumnType;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.02", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Minimal Cube with level with smallInt column as boolan type
            Cube have level which use smallInt column and have boolean ColumnType
            """;

    private static final String databaseSchemaBody = """
            DatabaseSchema includes physical table `Fact`.
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
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        Column flagColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        flagColumn.setName("FLAG");
        flagColumn.setId("_column_fact_flag");
        flagColumn.setType(ColumnType.SMALLINT);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn, flagColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_table_factQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("_Level");
        level.setId("_level_Level");
        level.setColumnType(ColumnInternalDataType.BOOLEAN);
        level.setColumn(flagColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setId("_hierarchywithhasall");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dimension");
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level Small Int As Boolean Type");
        catalog.setDescription("Level using small integer as boolean type");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Level Small Int As Boolean Type", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query Fact", queryFactBody, 1, 2, 0, true, 2);
        document(level, "Level", levelBody, 1, 3, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 5, 0, true, 0);

        document(measure, "Measure", measureBody, 1, 6, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
