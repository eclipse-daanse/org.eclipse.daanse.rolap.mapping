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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.level.member.property.intro;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
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

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.5.1", source = Source.EMF, group = "Member") // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            This Tutorial is about MemberProperties. MemberProperties are attributes of a hierarchy level’s members. They provide additional details about each member, such as a description or any other related value. The only requirement for defining a MemberProperty is that a corresponding column exists to store the property’s value.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on two tables. Fact and Town. The Fact table contains a measures and a reference to the Town table. The Fact table is linked with its ID column to the Town table by the TOWN_ID column. The Town Table also contains the Name and the value of the MemberPropery, in this case the `CAPITAL` flag.
            """;

    private static final String memberPropertyBody = """
            The MemberProperty with a name `Capital`, the `propertyType` attribute `String`, and the reference to the `column` thats holds the values of the MemberProperty.
            """;

    private static final String levelBody = """
            The level used the `column` attribute to define the primary key column. It also defines the `nameColumn` attribute to define the column that contains the name of the level. The `nameColumn` attribute is optional, if it is not defined, the server will use the column defined in the `column` attribute as name column.
            The `memberProperties` attribute is also set, to the before defines Capital-MemberProperty.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains only one level. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used. The dimension has set the attribute `foreignKey` to define the column that contains the foreign key of the dimension in the fact table.
            """;

    private static final String queryLevelBody = """
            The TableQuery for the Level, as it directly references the physical table `Town`.
            """;

    private static final String queryFactBody = """
            The TableQuery for the Level, as it directly references the physical table `Fact`.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column columnFactTownId = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFactTownId.setName("TOWN_ID");
        columnFactTownId.setId("_col_fact_townId");
        columnFactTownId.setType(ColumnType.INTEGER);

        Column columnFactValue = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnFactValue.setName("VALUE");
        columnFactValue.setId("_col_fact_value");
        columnFactValue.setType(ColumnType.INTEGER);

        PhysicalTable tableFact = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFact.setName("Fact");
        tableFact.setId("_tab_fact");
        tableFact.getColumns().addAll(List.of(columnFactTownId, columnFactValue));
        databaseSchema.getTables().add(tableFact);

        Column columnTownId = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownId.setName("ID");
        columnTownId.setId("_col_town_id");
        columnTownId.setType(ColumnType.INTEGER);

        Column columnTownName = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownName.setName("NAME");
        columnTownName.setId("_col_town_name");
        columnTownName.setType(ColumnType.VARCHAR);

        Column columnTownCapital = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnTownCapital.setName("CAPITAL");
        columnTownCapital.setId("_col_town_capital");
        columnTownCapital.setType(ColumnType.VARCHAR);
        columnTownCapital.setColumnSize(100);

        PhysicalTable tableTown = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableTown.setName("Town");
        tableTown.setId("_tab_town");
        tableTown.getColumns().addAll(List.of(columnTownId, columnTownName));
        databaseSchema.getTables().add(tableTown);

        TableQuery queryFact = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryFact.setId("_query_Fact");
        queryFact.setTable(tableFact);

        TableQuery queryHier = RolapMappingFactory.eINSTANCE.createTableQuery();
        queryHier.setId("_Query_LevelTown");
        queryHier.setTable(tableTown);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setId("_measure");
        measure.setColumn(columnFactValue);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        MemberProperty memberProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        memberProperty.setName("Capital");
        memberProperty.setId("_memberprop");
        memberProperty.setColumn(columnTownCapital);
        memberProperty.setPropertyType(ColumnInternalDataType.STRING);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Town");
        level.setId("_level_town");
        level.setColumn(columnTownId);
        level.setNameColumn(columnTownName);
        level.getMemberProperties().add(memberProperty);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_town");
        hierarchy.setPrimaryKey(columnTownId);
        hierarchy.setQuery(queryHier);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.setId("_dim_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnFactTownId);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Query linked Tables");
        cube.setId("_cube");
        cube.setQuery(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Level - MemberProperties Intro");
        catalog.getCubes().add(cube);

        document(catalog, "Level - MemberProperties Intro", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(queryHier, "Query Level", queryLevelBody, 1, 2, 0, true, 2);
        document(queryFact, "Query Fact", queryFactBody, 1, 3, 0, true, 2);

        document(memberProperty, "MemberProperty", memberPropertyBody, 1, 4, 0, true, 0);
        document(level, "Level", levelBody, 1, 4, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 5, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 6, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
