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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.cataloggrand;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.access.common.CatalogAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.access.olap.CubeAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.access.common.CommonFactory;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseFactory;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapFactory;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.04.04", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube1;
    private AccessRole roleAllDimWithCubeGrand;
    private Level level1;
    private Schema databaseSchema;
    private Catalog catalog;
    private AccessRole roleAll;
    private AccessRole roleAllDimWithoutCubeGrand;
    private StandardDimension dimension1;
    private AccessRole roleNone;
    private TableSource query;
    private ExplicitHierarchy hierarchy1;


    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with CatalogGrant.

    - `roleAll` role                   : use CatalogGrant access `all`; (access `cube1`)
    - `roleNone` role                  : use CatalogGrant access `none`; (no access `cube1`)
    - `roleAllDimWithCubeGrand` role   : use CatalogGrant access `all_dimensions`; CubeGrant `cube1` access all; `cube2` access none (access `cube1`)
    - `roleAllDimWithoutCubeGrand` role: use CatalogGrant access `all_dimensions` without cube grant (As result is 'no access'. For access need CubeGrant with `custom` or `all`)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the `Fact` table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String levelBody = """
            This Example uses one simple Level1 based on the `KEY` column.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level1.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy1.
            """;

    private static final String cube1Body = """
            The cube1 is defined by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The roleAll use CatalogGrant access `all`; (access `cube1`)
            """;

    private static final String roleNoneBody = """
            The `roleNone` use CatalogGrant access `none`; (no access `cube1`)
            """;

    private static final String roleAllDimWithCubeGrandBody = """
            The `roleAllDimWithCubeGrand` use CatalogGrant access `all_dimensions`; CubeGrant access `all`; (access `cube1`)
            """;

    private static final String roleAllDimWithoutCubeGrandBody = """
            The `roleAllDimWithoutCubeGrand` use CatalogGrant access `all_dimensions` without CubeGrant; (no access `cube1`)
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure1 = MeasureFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroupC1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        level1 = LevelFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setColumn(keyColumn);

        hierarchy1 = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(false);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(level1);

        dimension1 = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.getHierarchies().add(hierarchy1);

        DimensionConnector dimensionConnectorCube11 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube12 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube12.setOverrideDimensionName("Dimension2");
        dimensionConnectorCube12.setDimension(dimension1);
        dimensionConnectorCube12.setForeignKey(keyColumn);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessCubeGrant accessCubeGrant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant.setCube(cube1);

        AccessCatalogGrant accessCatalogGrantAll = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrantAll.getCubeGrants().add(accessCubeGrant);

        AccessCatalogGrant accessCatalogGrantNone = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantNone.setCatalogAccess(CatalogAccess.NONE);
        accessCatalogGrantNone.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        AccessCatalogGrant accessCatalogGrantAllDimWithCubeGrand = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAllDimWithCubeGrand.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        accessCatalogGrantAllDimWithCubeGrand.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
        accessCatalogGrantAllDimWithCubeGrand.getCubeGrants().add(accessCubeGrant);

        AccessCatalogGrant accessCatalogGrantAllDimWithoutCubeGrand = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAllDimWithoutCubeGrand.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        accessCatalogGrantAllDimWithoutCubeGrand.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        roleAll = CommonFactory.eINSTANCE.createAccessRole();
        roleAll.setName("roleAll");
        roleAll.getAccessCatalogGrants().add(accessCatalogGrantAll);

        roleNone = CommonFactory.eINSTANCE.createAccessRole();
        roleNone.setName("roleNone");
        roleNone.getAccessCatalogGrants().add(accessCatalogGrantNone);

        roleAllDimWithCubeGrand = CommonFactory.eINSTANCE.createAccessRole();
        roleAllDimWithCubeGrand.setName("roleAllDimWithCubeGrand");
        roleAllDimWithCubeGrand.getAccessCatalogGrants().add(accessCatalogGrantAllDimWithCubeGrand);

        roleAllDimWithoutCubeGrand = CommonFactory.eINSTANCE.createAccessRole();
        roleAllDimWithoutCubeGrand.setName("roleAllDimWithoutCubeGrand");
        roleAllDimWithoutCubeGrand.getAccessCatalogGrants().add(accessCatalogGrantAllDimWithoutCubeGrand);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Catalog Grant");
        catalog.setDescription("Demonstrates access control with catalog grants and roles");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(roleAll);
        catalog.getAccessRoles().add(roleNone);
        catalog.getAccessRoles().add(roleAllDimWithCubeGrand);
        catalog.getAccessRoles().add(roleAllDimWithoutCubeGrand);
        catalog.getDbschemas().add(databaseSchema);





            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Access Catalog Grant", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Level1", levelBody, 1, 3, 0, level1, 0),
                        new DocSection("Hierarchy1 without hasAll Level1", hierarchyBody, 1, 4, 0, hierarchy1, 0),
                        new DocSection("Dimension1", dimension1Body, 1, 5, 0, dimension1, 0),
                        new DocSection("Cube1 with access all", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("roleAll", roleAllBody, 1, 7, 0, roleAll, 2),
                        new DocSection("roleNone", roleNoneBody, 1, 8, 0, roleNone, 2),
                        new DocSection("roleAllDimWithCubeGrand", roleAllDimWithCubeGrandBody, 1, 9, 0, roleAllDimWithCubeGrand, 2),
                        new DocSection("roleAllDimWithoutCubeGrand", roleAllDimWithoutCubeGrandBody, 1, 10, 0, roleAllDimWithoutCubeGrand, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
