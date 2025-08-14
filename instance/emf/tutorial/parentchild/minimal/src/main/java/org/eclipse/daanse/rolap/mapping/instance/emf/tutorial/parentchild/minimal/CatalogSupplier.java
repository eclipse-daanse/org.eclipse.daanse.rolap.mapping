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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.parentchild.minimal;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.17.1", source = Source.EMF, group = "Parent Child") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            Catalog with Minimal Cube with Parent Child Hierarchy
            Parent Child Hierarchy is self-referencing hierarchy where members can have parent-child relationships within the same table,
            creating variable-depth structures.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: DIM_KEY and VALUE.
            The DATE_KEY column is used as the discriminator in the Hierarchy definitions.

            Hier_One_Top_Member table with 3 columns: KEY, NAME, PARENT_KEY
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in the measures.
            """;

    private static final String query1Body = """
            The Query is a simple TableQuery that selects all columns from the Hier_One_Top_Member table.
            """;

    private static final String levelBody = """
            This Example uses Level1 level bases on the KEY column and name column NAME of table Hier_One_Top_Member.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 defined parentColumn to PARENT_KEY column of Hier_One_Top_Member table.
            ParentColumn containing the parent reference for each member, establishing the self-referencing relationship.
            This column typically contains the primary key value of the parent member, or the nullParentValue for root members. The column enables the recursive traversal that defines the hierarchy structure.
            Also Hierarchy1 defined the level Level1.
            Level is Single level definition that applies to all members in this parent-child hierarchy.
            Unlike explicit hierarchies with multiple levels, parent-child hierarchies use one level
            definition that describes the properties and behavior of all members regardless of their position in the tree structure.

            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            The cube with with Parent Child Hierarchy.
            """;

    private static final String catalogDocumentationTxt = """
            Catalog with Minimal Cube with Parent Child Hierarchy
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dimKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dimKeyColumn.setName("DIM_KEY");
        dimKeyColumn.setId("_Fact_DIM_KEY");
        dimKeyColumn.setType(ColumnType.INTEGER);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_Fact");
        table.getColumns().addAll(List.of(dimKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        Column memberKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberKeyColumn.setName("KEY");
        memberKeyColumn.setId("_Hier_One_Top_Member_KEY");
        memberKeyColumn.setType(ColumnType.INTEGER);

        Column memberNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberNameColumn.setName("NAME");
        memberNameColumn.setId("_Hier_One_Top_Member_NAME");
        memberNameColumn.setType(ColumnType.VARCHAR);

        Column memberParentKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        memberParentKeyColumn.setName("PARENT_KEY");
        memberParentKeyColumn.setId("_Hier_One_Top_Member_PARENT_KEY");
        memberParentKeyColumn.setType(ColumnType.INTEGER);

        PhysicalTable table1 = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table1.setName("Hier_One_Top_Member");
        table1.setId("_Hier_One_Top_Member");
        table1.getColumns().addAll(List.of(memberKeyColumn, memberNameColumn, memberParentKeyColumn));
        databaseSchema.getTables().add(table1);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        TableQuery query1 = RolapMappingFactory.eINSTANCE.createTableQuery();
        query1.setId("_Hier_One_Top_MemberQuery");
        query1.setTable(table1);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure1");
        measure.setId("Measure1");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Level");
        level.setId("_Level");
        level.setUniqueMembers(true);
        level.setColumn(memberKeyColumn);
        level.setNameColumn(memberNameColumn);

        ParentChildHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Hierarchy1");
        hierarchy.setId("_Hierarchy1");
        hierarchy.setPrimaryKey(memberKeyColumn);
        hierarchy.setQuery(query1);
        hierarchy.setLevel(level);
        hierarchy.setParentColumn(memberParentKeyColumn);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension1");
        dimension.setId("_Dimension1");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dc_dimension1");
        dimensionConnector.setOverrideDimensionName("Dimension1");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(dimKeyColumn);
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_Cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal Parent Child Hierarchy");
        catalog.setDescription("Schema of a minimal cube with parent child hierarchy");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(catalogDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Catalog with Minimal Cube with Parent Child Hierarchy", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Fact Query", queryBody, 1, 2, 0, true, 2);
        document(query1, "Query", query1Body, 1, 3, 0, true, 2);
        document(level, "Level", levelBody, 1, 4, 0, true, 0);

        document(hierarchy, "Hierarchy1", hierarchyBody, 1, 5, 0, true, 0);
        document(dimension, "Diml1", dimensionBody, 1, 6, 0, true, 0);

        document(cube, "Cube", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
