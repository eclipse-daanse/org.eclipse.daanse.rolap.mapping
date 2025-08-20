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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.action.drillthrough;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
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

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.10.1", source = Source.EMF, group = "Actions") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial discusses DrillThroughAction.
            DrillThroughAction feature is enabling users to seamlessly transition from  analytical summaries
            to detailed operational data without losing analytical context or requiring technical
            knowledge of underlying data structures.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: KEY and VALUE.
            H1_L1 table with two columns: KEY and NAME.
            H2_L1 table with two columns: KEY and NAME.
            HX_L2 table with 4 columns: KEY, NAME, H1L1_KEY, H2L1_KEY.
            The KEY column of Fact table is used as the discriminator in the the dimension.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String h1L1QueryBody = """
            The Query is a simple TableQuery that selects all columns from the H1_L1 table table.
            """;

    private static final String h2L1QueryBody = """
            The Query is a simple TableQuery that selects all columns from the H2_L1 table table.
            """;

    private static final String hxL2QueryBody = """
            The Query is a simple TableQuery that selects all columns from the HX_L2 table table.
            """;

    private static final String join1Body = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (HX_L2), the join uses the foreign key H1L1_KEY.
            - In the upper-level table (H1_L1), the join uses the primary key KEY.
            """;

    private static final String join2Body = """
            The JoinQuery specifies which TableQueries should be joined. It also defines the columns in each table that are used for the join:

            - In the lower-level table (HX_L2), the join uses the foreign key H2L1_KEY.
            - In the upper-level table (H2_L1), the join uses the primary key KEY.
            """;

    private static final String level1Body = """
            This Example uses H1_Level1 level bases on the KEY column as kew and NAME column as name from H1_L1 table.
            """;

    private static final String level2Body = """
            This Example uses H1_Level2 level bases on the KEY column as kew and NAME column as name from HX_L2 table.
            """;

    private static final String level3Body = """
            This Example uses H2_Level1 level bases on the KEY column as kew and NAME column as name from H2_L1 table.
            """;

    private static final String level4Body = """
            This Example uses H2_Level2 level bases on the KEY column as kew and NAME column as name from HX_L2 table.
            """;

    private static final String hierarchy1Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the two levels H1_Level1, H1_Level2.
            """;

    private static final String hierarchy2Body = """
            The Hierarchy1 is defined with the hasAll property set to false and the two levels H1_Level1, H1_Level2.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the 2 hierarchies.
            """;

    private static final String drillthroughH1L1Body = """
            Specialized action that enables users to drill through from aggregated analytical views to the underlying
            detailed transaction data that contributed to specific measure values, providing powerful capabilities
            for data exploration, validation, and detailed investigation of analytical results.
            Collection of DrillThroughAttribute objects that specify which dimensional attributes and descriptive columns
            should be included in drill-through result sets

            DrillThroughAttribute have reference to H1_Level1 level from Hierarchy1; H1_L1 table KEY and NAME column
            """;

    private static final String drillthroughH2L1Body = """
            DrillThroughAttribute have reference to H2_Level1 level from Hierarchy2; H2_L1 table KEY and NAME column
            """;

    private static final String cubeBody = """
            The cube with DrillThroughAction
            """;

    private static final String schemaDocumentationTxt = """
            Schema of a minimal cube with DrillThroughAction
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_DrillThrough");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_column_fact_key");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        // "KEY","NAME"
        // INTEGER,VARCHAR

        Column h1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1KeyColumn.setName("KEY");
        h1L1KeyColumn.setId("_column_h1_l1_key");
        h1L1KeyColumn.setType(ColumnType.INTEGER);

        Column h1L1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h1L1NameColumn.setName("NAME");
        h1L1NameColumn.setId("_column_h1_l1_name");
        h1L1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable h1L1table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h1L1table.setName("H1_L1");
        h1L1table.setId("_table_h1_l1");
        h1L1table.getColumns().addAll(List.of(h1L1KeyColumn, h1L1NameColumn));
        databaseSchema.getTables().add(h1L1table);

        Column h2L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h2L1KeyColumn.setName("KEY");
        h2L1KeyColumn.setId("_column_h2_l1_key");
        h2L1KeyColumn.setType(ColumnType.INTEGER);

        Column h2L1NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        h2L1NameColumn.setName("NAME");
        h2L1NameColumn.setId("_column_h2_l1_name");
        h2L1NameColumn.setType(ColumnType.VARCHAR);

        PhysicalTable h2L1table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        h2L1table.setName("H2_L1");
        h2L1table.setId("_table_h2_l1");
        h2L1table.getColumns().addAll(List.of(h2L1KeyColumn, h2L1NameColumn));
        databaseSchema.getTables().add(h2L1table);

        Column hxL2KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2KeyColumn.setName("KEY");
        hxL2KeyColumn.setId("_column_hx_l2_key");
        hxL2KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2NameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2NameColumn.setName("NAME");
        hxL2NameColumn.setId("_column_hx_l2_name");
        hxL2NameColumn.setType(ColumnType.VARCHAR);

        Column hxL2H1L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H1L1KeyColumn.setName("H1L1_KEY");
        hxL2H1L1KeyColumn.setId("_column_hx_l2_h1l1_key");
        hxL2H1L1KeyColumn.setType(ColumnType.INTEGER);

        Column hxL2H2L1KeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        hxL2H2L1KeyColumn.setName("H2L1_KEY");
        hxL2H2L1KeyColumn.setId("_column_hx_l2_h2l1_key");
        hxL2H2L1KeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable hxL2table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        hxL2table.setName("HX_L2");
        hxL2table.setId("_table_hx_l2");
        hxL2table.getColumns().addAll(List.of(hxL2KeyColumn, hxL2NameColumn, hxL2H1L1KeyColumn, hxL2H2L1KeyColumn));
        databaseSchema.getTables().add(hxL2table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_factQuery");
        query.setTable(table);

        TableQuery hxL2Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        hxL2Query.setId("_query_HxL2Query");
        hxL2Query.setTable(hxL2table);


        TableQuery h1L1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        h1L1Query.setId("_query_H1L1Query");
        h1L1Query.setTable(h1L1table);

        TableQuery h2L1Query = RolapMappingFactory.eINSTANCE.createTableQuery();
        h2L1Query.setId("_query_H2L1Query");
        h2L1Query.setTable(h2L1table);

        JoinedQueryElement join1Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join1Left.setKey(hxL2H1L1KeyColumn);
        join1Left.setQuery(hxL2Query);

        JoinedQueryElement join1Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join1Right.setKey(h1L1KeyColumn);
        join1Right.setQuery(h1L1Query);

        JoinQuery join1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join1.setId("_joinQuery_join1");
        join1.setLeft(join1Left);
        join1.setRight(join1Right);

        JoinedQueryElement join2Left = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join2Left.setKey(hxL2H2L1KeyColumn);
        join2Left.setQuery(hxL2Query);

        JoinedQueryElement join2Right = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        join2Right.setKey(h2L1KeyColumn);
        join2Right.setQuery(h2L1Query);

        JoinQuery join2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        join2.setId("_joinQuery_join2");
        join2.setLeft(join2Left);
        join2.setRight(join2Right);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("_measure_Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("H1_Level1");
        level1.setId("_level_H1_Level1");
        level1.setColumn(h1L1KeyColumn);
        level1.setNameColumn(h1L1NameColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("H1_Level2");
        level2.setId("_level_H1_Level2");
        level2.setColumn(hxL2KeyColumn);
        level2.setNameColumn(hxL2NameColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(true);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("_hierarchy_Hierarchy1");
        hierarchy1.setPrimaryKey(hxL2KeyColumn);
        hierarchy1.setQuery(join1);
        hierarchy1.getLevels().addAll(List.of(level1, level2));

        Level level3 = RolapMappingFactory.eINSTANCE.createLevel();
        level3.setName("H2_Level1");
        level3.setId("_level_H2_Level1");
        level3.setColumn(h2L1KeyColumn);
        level3.setNameColumn(h2L1NameColumn);

        Level level4 = RolapMappingFactory.eINSTANCE.createLevel();
        level4.setName("H2_Level2");
        level4.setId("_level_H2_Level2");
        level4.setColumn(hxL2KeyColumn);
        level4.setNameColumn(hxL2NameColumn);

        ExplicitHierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy2.setHasAll(true);
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setId("_hierarchy_Hierarchy2");
        hierarchy2.setPrimaryKey(hxL2KeyColumn);
        hierarchy2.setQuery(join2);
        hierarchy2.getLevels().addAll(List.of(level3, level4));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("_dimension_Dimension1");
        dimension.getHierarchies().add(hierarchy1);
        dimension.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_dimension1");
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
        drillThroughAction1.setId("_drillThroughAction_DrillthroughH1L1");
        drillThroughAction1.setDefault(true);
        drillThroughAction1.getDrillThroughAttribute().add(drillThroughAttribute1);
        drillThroughAction1.getDrillThroughMeasure().add(measure);

        DrillThroughAction drillThroughAction2 = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        drillThroughAction2.setName("DrillthroughH2L1");
        drillThroughAction2.setId("_drillThroughAction_DrillthroughH2L1");
        drillThroughAction2.getDrillThroughAttribute().add(drillThroughAttribute2);
        drillThroughAction2.getDrillThroughMeasure().add(measure);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube_Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);
        cube.getAction().addAll(List.of(drillThroughAction1, drillThroughAction2));

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Action Drillthrough");
        catalog.setDescription("Drill-through action configuration");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Minimal Cube with DrillThroughAction", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(hxL2Query, "HxL2Query", hxL2QueryBody, 1, 3, 0, true, 2);
        document(h1L1Query, "H1L1Query", h1L1QueryBody, 1, 2, 0, true, 2);
        document(h2L1Query, "H2L1Query", h2L1QueryBody, 1, 2, 0, true, 2);
        document(join1, "join1", join1Body, 1, 2, 0, true, 2);
        document(join2, "join2", join2Body, 1, 2, 0, true, 2);


        document(level1, "Level1", level1Body, 1, 3, 0, true, 0);
        document(level2, "Level2", level2Body, 1, 3, 0, true, 0);
        document(level3, "Level3", level3Body, 1, 3, 0, true, 0);
        document(level4, "Level4", level4Body, 1, 3, 0, true, 0);

        document(hierarchy1, "Hierarchy1", hierarchy1Body, 1, 8, 0, true, 0);
        document(hierarchy2, "Hierarchy2", hierarchy2Body, 1, 8, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 9, 0, true, 0);

        document(drillThroughAction1, "DrillthroughH1L1", drillthroughH1L1Body, 1, 9, 0, true, 0);
        document(drillThroughAction2, "DrillthroughH2L1", drillthroughH2L1Body, 1, 9, 0, true, 0);

        document(cube, "Cube with Time_Dimension", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
