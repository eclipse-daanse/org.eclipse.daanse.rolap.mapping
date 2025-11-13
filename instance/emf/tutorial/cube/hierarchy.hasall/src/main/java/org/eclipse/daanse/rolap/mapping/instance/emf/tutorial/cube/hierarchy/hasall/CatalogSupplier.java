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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.hasall;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.4", source = Source.EMF, group = "Hierarchy") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            In a hierarchy, the top level can sometimes be a special case. Typically, levels are created using a Level object, along with a reference to a column and a query on the hierarchy. However, there are situations where no dedicated column or table entry exists for the top level. For example if you want to represent a grand total. In such cases, you can generate a generic top level that serves as a final aggregation for all members of the level below.

            To manage the creation of this top level, the hierarchy provides specific attributes:

            - `hasAll` A Boolean attribute that indicates whether a so-called “HasAll” level is needed. If hasAll is set to true, a top level will be generated.

            - `allLevelName` Specifies the name for the top level itself.

            - `allMemberName` Specifies the name of the member within this top level. If this attribute is not set, the default name is used in the form: `All <HierarchyName>s`.

            By configuring these attributes, you can control whether a top-level aggregation appears, as well as how it is labeled in your hierarchy.

            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on three table: Fact.

            - The Fact table contains measures and a reference to the Level.
            """;

    private static final String queryBody = """
            This Query references the Fact table and will be udes for the Cube and all Hierarchies in same way.
            """;

    private static final String levelBody = """
            The Level object uses the column attribute to specify the column names `KEY` that represents the level and its members.
            This the only Level that exists in this example and will be used in all hierarchies the same way.
            """;

    private static final String hierarchyNoBody = """
            This Hierarchy sets the attribute `hasAll` to false, which means that no top level will be generated. The hierarchy will only contain the levels defined in the Level object.
            """;

    private static final String hierarchyWithSimpleBody = """
            This hierarchy sets the attribute `hasAll` to true, which means that a top level will be generated. The hierarchy will contain the levels defined in the Level object and an additional top level with the default Name for the All-Level and the All-Member.
            """;

    private static final String hierarchyWithComplexBody = """
            tHis hierarchy sets the attribute `hasAll` to true, which means that a top level will be generated. The hierarchy will contain the levels defined in the Level object and an additional top level with the custom Name for the All-Level and the All-Member.
                        """;

    private static final String dimensionBody = """
            The Dimension that containes all the hierarchies.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseschema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_col_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_col_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName("Fact");
        table.setId("_table");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setId("_measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("theLevel");
        level.setId("_level");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchyHasAllSimple = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllSimple.setHasAll(true);
        hierarchyHasAllSimple.setName("Hierarchy - with HasAll");
        hierarchyHasAllSimple.setId("_hierarchy_hasall_simple");
        hierarchyHasAllSimple.setPrimaryKey(keyColumn);
        hierarchyHasAllSimple.setQuery(query);
        hierarchyHasAllSimple.getLevels().add(level);

        ExplicitHierarchy hierarchyHasAllComplex = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllComplex.setHasAll(true);
        hierarchyHasAllComplex.setAllLevelName("theAllLevelName");
        hierarchyHasAllComplex.setAllMemberName("theAllMemberName");
        hierarchyHasAllComplex.setName("Hierarchy - with HasAll and Names");
        hierarchyHasAllComplex.setId("_hierarchy_hasall_complex");
        hierarchyHasAllComplex.setPrimaryKey(keyColumn);
        hierarchyHasAllComplex.setQuery(query);
        hierarchyHasAllComplex.getLevels().add(level);

        ExplicitHierarchy hierarchyHasAllFalse = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyHasAllFalse.setHasAll(false);
        hierarchyHasAllFalse.setName("Hierarchy - Without HasAll");
        hierarchyHasAllFalse.setId("_hierarchy_hasall_no");
        hierarchyHasAllFalse.setPrimaryKey(keyColumn);
        hierarchyHasAllFalse.setQuery(query);
        hierarchyHasAllFalse.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("_dimension");
        dimension.getHierarchies().add(hierarchyHasAllSimple);
        dimension.getHierarchies().add(hierarchyHasAllComplex);
        dimension.getHierarchies().add(hierarchyHasAllFalse);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setId("_dc_dimension");
        dimensionConnector1.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("HasAll Cube");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tutorial - Hierarchy Has All");
        catalog.setDescription("Hierarchy with all-member configuration");
        catalog.getCubes().add(cube);

        document(catalog, "Daanse Tutorial - Hierarchy Has All", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level, "Level", levelBody, 1, 6, 0, true, 0);

        document(hierarchyHasAllFalse, "Hierarchy without hasAll Level", hierarchyNoBody, 1, 8, 0, true, 0);
        document(hierarchyHasAllSimple, "Hierarchy with hasAll Level and defaut names", hierarchyWithSimpleBody, 1, 8,
                0, true, 0);
        document(hierarchyHasAllComplex, "Hierarchy with hasAll Level and custom names", hierarchyWithComplexBody, 1, 8,
                0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 9, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
