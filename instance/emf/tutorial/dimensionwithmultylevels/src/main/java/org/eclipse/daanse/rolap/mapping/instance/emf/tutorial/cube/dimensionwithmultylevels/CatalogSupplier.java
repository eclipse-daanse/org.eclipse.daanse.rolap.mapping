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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.dimensionwithmultylevels;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
        A basic OLAP schema with a level with property

        Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
        This example schema contains one cube named "Cube".

        A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
        In this case the database table representing the fact table is named "Fact" in the database, which is addressed in the name attribute within the <Table> tag.

        Each measurement of the cube is defined in a separate <Measure> element.
        The measurement in this example cube is named "Measure" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
        Level is defined in <Level> element.
        Property is defined in <Property> element inside <Level> element. Property we can see in cell tooltip in excel
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("VALUE");
        keyColumn.setId("Fact_VALUE");
        keyColumn.setType("INTEGER");

        Column l1Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("L1");
        keyColumn.setId("Fact_L1");
        keyColumn.setType("VARCHAR");
        keyColumn.setColumnSize(100);

        Column l2Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("L2");
        keyColumn.setId("Fact_L2");
        keyColumn.setType("INTEGER");

        Column prop1Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("prop1");
        keyColumn.setId("Fact_prop1");
        keyColumn.setType("VARCHAR");
        keyColumn.setColumnSize(100);

        Column prop2Column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        keyColumn.setName("prop2");
        keyColumn.setId("Fact_prop2");
        keyColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn, l1Column, l2Column, prop1Column, prop2Column));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        MemberProperty memberProperty1 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        memberProperty1.setName("Prop1");
        memberProperty1.setId("Prop1");
        memberProperty1.setColumn(prop1Column);
        memberProperty1.setPropertyType(ColumnDataType.STRING);

        MemberProperty memberProperty2 = RolapMappingFactory.eINSTANCE.createMemberProperty();
        memberProperty2.setName("Prop2");
        memberProperty2.setId("Prop2");
        memberProperty2.setColumn(prop2Column);
        memberProperty2.setPropertyType(ColumnDataType.INTEGER);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level");
        level1.setId("Level1");
        level1.getMemberProperties().add(memberProperty1);
        level1.setColumn(l1Column);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level");
        level2.setId("Level2");
        level2.getMemberProperties().add(memberProperty2);
        level2.setColumn(l2Column);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(level1, level2));

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

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cube_with_cube_dimension_with_multy_level_with_property");
        schema.setDescription("Schema of a minimal cube with dimension with multy level with property");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getSchemas().add(schema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema of a minimal cube with dimension with multy level with property");
        catalog.setDocumentation(documentation);
        return catalog;
    }

}
