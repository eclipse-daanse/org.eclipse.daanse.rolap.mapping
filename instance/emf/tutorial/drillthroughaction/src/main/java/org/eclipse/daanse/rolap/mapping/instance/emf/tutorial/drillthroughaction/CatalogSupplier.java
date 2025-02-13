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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.drillthroughaction;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_15-03_Cube_with_share_dimension_with_DrillThroughAction";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
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
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        // "KEY","NAME"
        // INTEGER,VARCHAR

        Column h1L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setId("H1_L1_VALUE");
        h1L1KeyColumn.setType("INTEGER");

        Column h1L1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setId("H1_L1_NAME");
        h1L1NameColumn.setType("VARCHAR");

        PhysicalTable h1L1table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h1L1table.setName("H1_L1");
        h1L1table.setId("H1_L1");
        h1L1table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h1L1table);

        Column h2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h2L1KeyColumn.setName("KEY");
        h2L1KeyColumn.setId("H2_L1_VALUE");
        h2L1KeyColumn.setType("INTEGER");

        Column h2L1NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        h2L1NameColumn.setName("NAME");
        h2L1NameColumn.setId("H2_L1_NAME");
        h2L1NameColumn.setType("VARCHAR");

        PhysicalTable h2L1table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h2L1table.setName("H2_L1");
        h2L1table.setId("H2_L1");
        h2L1table.getColumns().addAll(List.of(h2L1KeyColumn, h2L1NameColumn));
        databaseSchema.getTables().add(h2L1table);

        Column hxL2KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setId("HX_L2_KEY");
        hxL2KeyColumn.setType("INTEGER");

        Column hxL2NameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setId("HX_L2_NAME");
        hxL2NameColumn.setType("VARCHAR");

        Column hxL2H1L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setId("HX_L2_H1L1_KEY");
        hxL2H1L1KeyColumn.setType("INTEGER");

        Column hxL2H2L1KeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setId("HX_L2_H2L1_KEY");
        hxL2H2L1KeyColumn.setType("INTEGER");

        PhysicalTable hxL2table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hxL2table.setName("HX_L2");
        hxL2table.setId("HX_L2");
        hxL2table.getColumns().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getTables().add(hxL2table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery hxL2Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        hxL2Query.setId("HxL2Query");
        hxL2Query.setTable(hxL2table);

        TableQuery hxL2Query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        hxL2Query1.setId("HxL2Query1");
        hxL2Query1.setTable(hxL2table);

        TableQuery h1L1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        h1L1Query.setId("H1L1Query");
        h1L1Query.setTable(h1L1table);

        TableQuery h2L1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        h2L1Query.setId("H2L1Query");
        h2L1Query.setTable(h2L1table);

        JoinedQueryElement join1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join1Left.setKey(hxL2H1L1KeyColumn);
        join1Left.setQuery(hxL2Query);

        JoinedQueryElement join1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join1Right.setKey(h1L1KeyColumn);
        join1Right.setQuery(h1L1Query);

        JoinQuery join1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join1.setId("join1");
        join1.setLeft(join1Left);
        join1.setRight(join1Right);

        JoinedQueryElement join2Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join2Left.setKey(hxL2H2L1KeyColumn);
        join2Left.setQuery(hxL2Query1);

        JoinedQueryElement join2Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join2Right.setKey(h2L1KeyColumn);
        join2Right.setQuery(h2L1Query);

        JoinQuery join2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join2.setId("join2");
        join2.setLeft(join2Left);
        join2.setRight(join2Right);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("H1_Level1");
        level1.setId("H1_Level1");
        level1.setColumn(h1L1KeyColumn);
        level1.setNameColumn(h1L1NameColumn);
        level1.setTable(h1L1table);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("H1_Level2");
        level2.setId("H1_Level2");
        level2.setColumn(hxL2KeyColumn);
        level2.setNameColumn(hxL2NameColumn);
        level2.setTable(hxL2table);

        Hierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setPrimaryKeyTable(hxL2table);
        hierarchy1.setQuery(join1);
        hierarchy1.getLevels().addAll(List.of(level1, level2));

        Level level3 = RolapMappingFactory.eINSTANCE.createLevel();
        level3.setName("H2_Level1");
        level3.setId("H2_Level1");
        level3.setColumn(h2L1KeyColumn);
        level3.setNameColumn(h2L1NameColumn);
        level3.setTable(h2L1table);

        Level level4 = RolapMappingFactory.eINSTANCE.createLevel();
        level4.setName("H2_Level2");
        level4.setId("H2_Level2");
        level4.setColumn(hxL2KeyColumn);
        level4.setNameColumn(hxL2NameColumn);
        level4.setTable(hxL2table);

        Hierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy2.setHasAll(true);
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setId("Hierarchy2");
        hierarchy2.setPrimaryKey(hxL2KeyColumn);
        hierarchy2.setPrimaryKeyTable(hxL2table);
        hierarchy2.setQuery(join2);
        hierarchy2.getLevels().addAll(List.of(level3, level4));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("Dimension1");
        dimension.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setForeignKey(keyColumn);
        dimensionConnector.setDimension(dimension);

        DrillThroughAttribute drillThroughAttribute1 = RolapMappingFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute1.setDimension(dimension);
        drillThroughAttribute1.setHierarchy(hierarchy1);
        drillThroughAttribute1.setLevel(level1); // H1_Level1
        // drillThroughAttribute1.setName("H1_Level1");

        DrillThroughAttribute drillThroughAttribute2 = RolapMappingFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute2.setDimension(dimension);
        drillThroughAttribute2.setHierarchy(hierarchy2);
        drillThroughAttribute2.setLevel(level3); // H2_Level1
        // drillThroughAttribute2.setName("H2_Level1");

        DrillThroughAction drillThroughAction1 = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction1.setName("DrillthroughH1L1");
        drillThroughAction1.setId("DrillthroughH1L1");
        drillThroughAction1.setDefault(true);
        drillThroughAction1.getDrillThroughAttribute().add(drillThroughAttribute1);
        drillThroughAction1.getDrillThroughMeasure().add(measure);

        DrillThroughAction drillThroughAction2 = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction2.setName("DrillthroughH2L1");
        drillThroughAction2.setId("DrillthroughH2L1");
        drillThroughAction2.getDrillThroughAttribute().add(drillThroughAttribute1);
        drillThroughAction2.getDrillThroughMeasure().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getAction().addAll(List.of(drillThroughAction1, drillThroughAction2));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_DrillThroughAction");
        catalog.setDescription("Schema of a minimal cube with DrillThroughAction");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
