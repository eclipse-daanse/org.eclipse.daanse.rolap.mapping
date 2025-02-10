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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelwithproperties;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-08_1_Cube_with_cub_dimension_with_level_with_property";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A basic OLAP schema with a level with property

            Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
            This example schema contains one cube named "Cube".

            A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
            In this case the database table representing the fact table is named "Fact" in the database, which is adressed in the name attribute within the <Table> tag.

            Each measurement of the cube is defined in a separate <Measure> element.
            The measurement in this example cube is named "Measure" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
            Level is defined in <Level> element.
            Property is defined in <Property> element inside <Level> element. Property we can see in cell tooltip in excel
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        Column lColumn = RolapMappingFactory.eINSTANCE.createColumn();
        lColumn.setName("L");
        lColumn.setId("Fact_L");
        lColumn.setType("VARCHAR");
        lColumn.setColumnSize(100);

        Column propColumn = RolapMappingFactory.eINSTANCE.createColumn();
        propColumn.setName("prop");
        propColumn.setId("Fact_prop");
        propColumn.setType("VARCHAR");
        propColumn.setColumnSize(100);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn, lColumn, propColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        MemberProperty prop = RolapMappingFactory.eINSTANCE.createMemberProperty();
        prop.setName("Prop");
        prop.setId("Prop");
        prop.setColumn(propColumn);
        prop.setPropertyType(ColumnDataType.STRING);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setColumn(lColumn);
        level.getMemberProperties().add(prop);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_level_with_property");
        catalog.setDescription("Schema of a minimal cube with cube dimension level with property");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
