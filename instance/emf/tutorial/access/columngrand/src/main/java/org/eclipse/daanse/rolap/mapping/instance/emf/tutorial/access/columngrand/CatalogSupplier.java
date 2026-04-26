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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.columngrand;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessColumnGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessTableGrant;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.access.common.CatalogAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.access.database.ColumnAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.CubeAccess;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.access.database.TableAccess;
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
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.04.03", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private PhysicalCube cube1;
    private Schema databaseSchema;
    private Catalog catalog;
    private AccessRole roleAll;
    private AccessRole roleNone;
    private TableSource query;


    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with ColumnGrant.

    - `roleAll`    role: use TableGrant access `all`; (access all database all tables all columns)
    - `roleNone`   role: use TableGrant access `none`; (no access to database columns)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the `Fact` table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String cube1Body = """
            The `cube1` is defined by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The `roleAll` use TableGrant access `all`; (access all tables columns)
            """;

    private static final String roleNoneBody = """
            The `roleNone` use ColumnGrant access `none`; (no access to all tables columns)
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

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure1);

        cube1 = CubeFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroup);

        AccessColumnGrant accessColumnGrantAll1 = DatabaseFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantAll1.setColumnAccess(ColumnAccess.ALL);
        accessColumnGrantAll1.setColumn(valueColumn);

        AccessColumnGrant accessColumnGrantAll2 = DatabaseFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantAll2.setColumnAccess(ColumnAccess.ALL);
        accessColumnGrantAll2.setColumn(keyColumn);

        AccessColumnGrant accessColumnGrantNone = DatabaseFactory.eINSTANCE.createAccessColumnGrant();
        accessColumnGrantNone.setColumnAccess(ColumnAccess.NONE);
        accessColumnGrantNone.setColumn(valueColumn);

        AccessTableGrant accessTableGrantAll = DatabaseFactory.eINSTANCE.createAccessTableGrant();
        accessTableGrantAll.setTableAccess(TableAccess.CUSTOM);
        accessTableGrantAll.setTable(table);
        accessTableGrantAll.getColumnGrants().add(accessColumnGrantAll1);
        accessTableGrantAll.getColumnGrants().add(accessColumnGrantAll2);

        AccessTableGrant accessTableGrantNone = DatabaseFactory.eINSTANCE.createAccessTableGrant();
        accessTableGrantNone.setTableAccess(TableAccess.CUSTOM);
        accessTableGrantNone.setTable(table);
        accessTableGrantNone.getColumnGrants().add(accessColumnGrantNone);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantAll = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantAll.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        accessDatabaseSchemaGrantAll.setDatabaseSchema(databaseSchema);
        accessDatabaseSchemaGrantAll.getTableGrants().add(accessTableGrantAll);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantNone = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantNone.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        accessDatabaseSchemaGrantNone.setDatabaseSchema(databaseSchema);
        accessDatabaseSchemaGrantNone.getTableGrants().add(accessTableGrantNone);

        AccessCubeGrant accessCubeGrant1 = OlapFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant1.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant1.setCube(cube1);

        AccessCubeGrant accessCubeGrant2 = OlapFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant2.setCubeAccess(CubeAccess.ALL);
        accessCubeGrant2.setCube(cube1);

        AccessCatalogGrant accessCatalogGrantAll = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrantAll);
        accessCatalogGrantAll.getCubeGrants().add(accessCubeGrant1);

        AccessCatalogGrant accessCatalogGrantNone = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantNone.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantNone.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrantNone);
        accessCatalogGrantNone.getCubeGrants().add(accessCubeGrant2);

        roleAll = CommonFactory.eINSTANCE.createAccessRole();
        roleAll.setName("roleAll");
        roleAll.getAccessCatalogGrants().add(accessCatalogGrantAll);

        roleNone = CommonFactory.eINSTANCE.createAccessRole();
        roleNone.setName("roleNone");
        roleNone.getAccessCatalogGrants().add(accessCatalogGrantNone);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Access Column Grant");
        catalog.setDescription("Demonstrates access control with column-level grants");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(roleAll);
        catalog.getAccessRoles().add(roleNone);
        catalog.getDbschemas().add(databaseSchema);



        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Access Column Grant", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Cube1 with access all", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("roleAll", roleAllBody, 1, 7, 0, roleAll, 2),
                        new DocSection("roleNone", roleNoneBody, 1, 8, 0, roleNone, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
