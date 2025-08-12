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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.dimensiongrand;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
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
@MappingInstance(kind = Kind.TUTORIAL, number = "2.4.6", source = Source.EMF, group = "Access") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE1 = "Cube1";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
    This tutorial discusses roles with DimensionGrant.

    role1 role:   use DimensionGrant access to Dimension1 and not access to Dimension2 of cube1

            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with two columns: KEY and VALUE. The KEY column is used as the discriminator in the the Level and Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String levelBody = """
            This Example uses one simple Level1 bases on the KEY column.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level1.
            """;

    private static final String dimension1Body = """
            The dimension1 is defined with the one hierarchy1.
            """;

    private static final String cube1Body = """
            The cube1 is defines by the DimensionConnector1 and the DimensionConnector2  and the MeasureGroup with measure with aggregation sum.
            """;

    private static final String role1Body = """
            The role1 use CatalogGrant access all_dimensions; CubeGrant cube1 access all; cube2 access none (access cube1)
            """;

    private static final String schemaDocumentationTxt = """
            Cube with examples of roles with DimensionGrant
            Cube1 - Dimension1 all access
            Cube1 - Dimension2 no access
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column keyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("_Fact_KEY");
        keyColumn.setType(ColumnType.VARCHAR);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_Fact");
        table.getColumns().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_FactQuery");
        query.setTable(table);

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Measure1");
        measure1.setId("_Measure1");
        measure1.setColumn(valueColumn);

        MeasureGroup measureGroupC1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroupC1.getMeasures().add(measure1);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("_Level1");
        level1.setColumn(keyColumn);

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("_Level2");
        level2.setColumn(keyColumn);

        ExplicitHierarchy hierarchy1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy1.setHasAll(false);
        hierarchy1.setName("Hierarchy1");
        hierarchy1.setId("_Hierarchy1");
        hierarchy1.setPrimaryKey(keyColumn);
        hierarchy1.setQuery(query);
        hierarchy1.getLevels().add(level1);

        ExplicitHierarchy hierarchy2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy2.setHasAll(false);
        hierarchy2.setName("Hierarchy2");
        hierarchy2.setId("_Hierarchy2");
        hierarchy2.setPrimaryKey(keyColumn);
        hierarchy2.setQuery(query);
        hierarchy2.getLevels().add(level2);

        StandardDimension dimension1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension1.setName("Dimension1");
        dimension1.setId("_Dimension1");
        dimension1.getHierarchies().add(hierarchy1);

        StandardDimension dimension2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension2.setName("Dimension2");
        dimension2.setId("_Dimension2");
        dimension2.getHierarchies().add(hierarchy2);

        DimensionConnector dimensionConnectorCube11 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube11.setId("_dc_dimension1");
        dimensionConnectorCube11.setOverrideDimensionName("Dimension1");
        dimensionConnectorCube11.setDimension(dimension1);
        dimensionConnectorCube11.setForeignKey(keyColumn);

        DimensionConnector dimensionConnectorCube12 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnectorCube12.setId("_dc_dimension2");
        dimensionConnectorCube12.setOverrideDimensionName("Dimension2");
        dimensionConnectorCube12.setDimension(dimension2);
        dimensionConnectorCube12.setForeignKey(keyColumn);

        PhysicalCube cube1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube1.setName(CUBE1);
        cube1.setId("_Cube1");
        cube1.setQuery(query);
        cube1.getMeasureGroups().add(measureGroupC1);
        cube1.getDimensionConnectors().addAll(List.of(dimensionConnectorCube11, dimensionConnectorCube12));

        AccessDatabaseSchemaGrant accessDatabaseSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        accessDatabaseSchemaGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        accessDatabaseSchemaGrant.setDatabaseSchema(databaseSchema);

        AccessDimensionGrant accessDimensionGrantDimension1 = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        accessDimensionGrantDimension1.setDimension(dimension1);
        accessDimensionGrantDimension1.setDimensionAccess(DimensionAccess.ALL);

        AccessDimensionGrant accessDimensionGrantDimension2 = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        accessDimensionGrantDimension2.setDimension(dimension2);
        accessDimensionGrantDimension2.setDimensionAccess(DimensionAccess.NONE);

        AccessCubeGrant cube1Grant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        cube1Grant.setCube(cube1);
        cube1Grant.setCubeAccess(CubeAccess.CUSTOM);
        cube1Grant.getDimensionGrants().addAll(List.of(accessDimensionGrantDimension1, accessDimensionGrantDimension2));

        AccessCatalogGrant accessCatalogGrant = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant.setCatalogAccess(CatalogAccess.CUSTOM);
        accessCatalogGrant.getCubeGrants().addAll(List.of(cube1Grant));
        accessCatalogGrant.getDatabaseSchemaGrants().add(accessDatabaseSchemaGrant);

        AccessRole role = RolapMappingFactory.eINSTANCE.createAccessRole();
        role.setName("role1");
        role.setId("role1");
        role.getAccessCatalogGrants().add(accessCatalogGrant);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Cube with role access DimensionGrant");
        catalog.setDescription("Schema with access DimensionGrant with access to Dimension1 only and no access to Dimension2 of Cube1");
        catalog.getCubes().add(cube1);
        catalog.getAccessRoles().add(role);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Cube with role with DimensionGtant", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(level1, "Level1", levelBody, 1, 3, 0, true, 0);

        document(hierarchy1, "Hierarchy1 without hasAll Level1", hierarchyBody, 1, 4, 0, true, 0);
        document(dimension1, "Dimension1", dimension1Body, 1, 5, 0, true, 0);

        document(cube1, "Cube1 with access all", cube1Body, 1, 6, 0, true, 2);

        document(role, "Role1", role1Body, 1, 8, 0, true, 2);

        return catalog;
    }

}
