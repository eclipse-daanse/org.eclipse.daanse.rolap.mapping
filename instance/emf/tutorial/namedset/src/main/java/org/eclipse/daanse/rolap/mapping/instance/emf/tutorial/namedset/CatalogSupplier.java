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

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_27_Cube_with_NamedSet";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            Cube with NamedSet.
            NamedSet as Set in dimension Dimension1
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType(ColumnDataType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level2");
        level.setId("Level2");
        level.setColumnType(ColumnInternalDataType.STRING);
        level.setColumn(keyColumn);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy");
        hierarchy.setId("Hierarchy");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("Dimension1");
        dimension1.getHierarchies().add(hierarchy);

        StandardDimension dimension2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("Dimension2");
        dimension2.setId("Dimension2");
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
        namedSet1.setId("NsWithFolderDimension1");
        namedSet1.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");
        namedSet1.setDisplayFolder("Folder1");

        Documentation namedSet2Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet2Documentation.setValue("NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1.");
        NamedSet namedSet2 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet2.getDocumentations().add(namedSet2Documentation);
        namedSet2.setName("NsWithoutFolderDimension1");
        namedSet2.setId("NsWithoutFolderDimension1");
        namedSet2.setFormula("TopCount([Dimension1].[Level2].MEMBERS, 5, [Measures].[Measure1])");

        Documentation namedSet3Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet3Documentation.setValue("NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube. NamedSet have folder");
        NamedSet namedSet3 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet3.getDocumentations().add(namedSet3Documentation);
        namedSet3.setName("NSInCubeWithFolder");
        namedSet3.setId("NSInCubeWithFolder");
        namedSet3.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");
        namedSet3.setDisplayFolder("Folder2");

        Documentation namedSet4Documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        namedSet4Documentation.setValue("NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.");
        NamedSet namedSet4 = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet4.getDocumentations().add(namedSet4Documentation);
        namedSet4.setName("NSInCubeWithoutFolder");
        namedSet4.setId("NSInCubeWithFolder");
        namedSet4.setFormula("{([Dimension1].[Level2].[A], [Dimension2].[Level2].[A]), ([Dimension1].[Level2].[B], [Dimension2].[Level2].[B])}");

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        cube.getNamedSets().addAll(List.of(namedSet1, namedSet2, namedSet3, namedSet4));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube_with_NamedSet");
        catalog.setDescription("Schema of a minimal cube with namedSet");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
