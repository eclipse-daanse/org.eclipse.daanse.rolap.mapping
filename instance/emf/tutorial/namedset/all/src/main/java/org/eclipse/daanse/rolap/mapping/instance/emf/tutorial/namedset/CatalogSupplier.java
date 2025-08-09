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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.8.1", source = Source.EMF, group = "Namedset") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses NamedSets.

    NsWithFolderDimension1    : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel. NamedSet have folder
    NsWithoutFolderDimension1 : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel.
    NSInCubeWithFolder        : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube on excel. NamedSet have folder
    NSInCubeWithoutFolder     : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.


            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: KEY and VALUE. The KEY column is used as the discriminator in the the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String levelBody = """
            This Example uses one simple Level1 bases on the KEY column.
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
            The cube1 is defines by the DimensionConnector  and the MeasureGroup with measure with aggregation sum.
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

    private static final String schemaDocumentationTxt = """
            Cube with NamedSet.
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_Fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("_Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setId("_Level2");
        level.setColumn(keyColumn);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("_Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("_Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        StandardDimension dimension2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("Dimension2");
        dimension2.setId("_Dimension2");
        dimension2.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setOverrideDimensionName("Dimension1");
        dimensionConnector1.setDimension(dimension1);

        DimensionConnector dimensionConnector2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setOverrideDimensionName("Dimension2");
        dimensionConnector2.setDimension(dimension1);

        Documentation namedSet1Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet1Documentation.setValue("NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1. NamedSet have folder");
        NamedSet namedSet1 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet1.getDocumentations().add(namedSet1Documentation);
        namedSet1.setName("NsWithFolderDimension1");
        namedSet1.setId("_NsWithFolderDimension1");
        namedSet1.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");
        namedSet1.setDisplayFolder("Folder1");

        Documentation namedSet2Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet2Documentation.setValue("NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1.");
        NamedSet namedSet2 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet2.getDocumentations().add(namedSet2Documentation);
        namedSet2.setName("NsWithoutFolderDimension1");
        namedSet2.setId("_NsWithoutFolderDimension1");
        namedSet2.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");

        Documentation namedSet3Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet3Documentation.setValue("NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube. NamedSet have folder");
        NamedSet namedSet3 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet3.getDocumentations().add(namedSet3Documentation);
        namedSet3.setName("NSInCubeWithFolder");
        namedSet3.setId("_NSInCubeWithFolder");
        namedSet3.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");
        namedSet3.setDisplayFolder("Folder2");

        Documentation namedSet4Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet4Documentation.setValue("NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.");
        NamedSet namedSet4 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet4.getDocumentations().add(namedSet4Documentation);
        namedSet4.setName("NSInCubeWithoutFolder");
        namedSet4.setId("_NSInCubeWithoutFolder");
        namedSet4.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        cube.getNamedSets().addAll(List.of(namedSet1, namedSet2, namedSet3, namedSet4));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with NamedSets");
        catalog.setDescription("Schema of a minimal cube with namedSet");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "catalog with Cube with NamedSets", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Cube with NamedSets", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level, "Level1", levelBody, 1, 3, 0, true, 0);

        document(hierarchy, "Hierarchy1 without hasAll Level1", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension1, "Dimension1", dimension1Body, 1, 5, 0, true, 0);
        document(dimension2, "Dimension1", dimension2Body, 1, 6, 0, true, 0);

        document(namedSet1, "NamedSet1", namedSet1Body, 1, 7, 0, true, 0);
        document(namedSet2, "NamedSet1", namedSet2Body, 1, 8, 0, true, 0);
        document(namedSet3, "NamedSet1", namedSet3Body, 1, 9, 0, true, 0);
        document(namedSet4, "NamedSet1", namedSet4Body, 1, 10, 0, true, 0);

        document(cube, "Cube with NamedSets", cubeBody, 1, 11, 0, true, 2);

        return catalog;
    }

}
