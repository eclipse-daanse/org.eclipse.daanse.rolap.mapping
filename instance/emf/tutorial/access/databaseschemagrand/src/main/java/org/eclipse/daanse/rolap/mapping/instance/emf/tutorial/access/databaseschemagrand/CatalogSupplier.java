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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.databaseschemagrand;


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
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
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
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.04.01", source = Source.EMF, group = "Access") // NOSONAR
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
    This tutorial discusses roles with DatabaseSchemaGrant.

    - `roleAll` role : use DatabaseSchemaGrant access `all`; (access all database)
    - `roleNone` role: use CatalogGrant access `none`; (no access to database)
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with two columns: `KEY` and `VALUE`. The `KEY` column is used as the discriminator in the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String cube1Body = """
            The cube1 is defined by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String roleAllBody = """
            The `roleAll` use DatabaseSchemaGrant access `all`; (access all database)
            """;

    private static final String roleNoneBody = """
            The `roleNone` use DatabaseSchemaGrant access `none`; (no access to database)
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

        AccessCubeGrant accessCubeGrant1 = OlapFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant1.setCube(cube1);
        accessCubeGrant1.setCubeAccess(CubeAccess.ALL);

        AccessCubeGrant accessCubeGrant2 = OlapFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant2.setCube(cube1);
        accessCubeGrant2.setCubeAccess(CubeAccess.ALL);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrantNone = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrantNone.setDatabaseSchemaAccess(DatabaseSchemaAccess.NONE);
        accessDatabaseSchemaGrantNone.setDatabaseSchema(databaseSchema);

        AccessCatalogGrant accessCatalogGrantAll = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrantAll.setCatalogAccess(CatalogAccess.ALL);
        accessCatalogGrantAll.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);
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
        catalog.setName("Daanse Tutorial - Access Database Schema Grant");
        catalog.setDescription("Access control with database schema grants");
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
                        new DocSection("Daanse Tutorial - Access Database Schema Grant", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Cube1 with access all", cube1Body, 1, 6, 0, cube1, 2),
                        new DocSection("roleAll", roleAllBody, 1, 7, 0, roleAll, 2),
                        new DocSection("roleNone", roleNoneBody, 1, 8, 0, roleNone, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
