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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.expressivenames;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
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
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.2", group = "Full Examples")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG_NAME = "ExpressiveNames";
    private static final String CUBE_1_NAME = "Cube1";

    // String constants for dimension/hierarchy/level names
    public static final String D_1_H_1_L_1 = "D1H1L1";
    public static final String D_2_H_1_L_1 = "D2H1L1";
    public static final String D_2_H_2_L_2 = "D2H2L2";
    public static final String D_3_H_1_L_1 = "D3H1L1";
    public static final String D_3_H_2_L_1 = "D3H2L1";
    public static final String D_3_H_3_L_1 = "D3H3L1";
    public static final String D_3_H_3_L_2 = "D3H3L2";
    public static final String D_3_H_3_L_3 = "D3H3L3";
    public static final String D_3_H_2_L_2 = "D3H2L2";
    public static final String DIMENSION_1 = "Dimension1";
    public static final String DIMENSION_2 = "Dimension2";
    public static final String DIMENSION_3 = "Dimension3";

    // Static columns - Fact table
    public static final Column COLUMN_D1_CUBE1FACT;
    public static final Column COLUMN_D2_CUBE1FACT;
    public static final Column COLUMN_D3_CUBE1FACT;
    public static final Column COLUMN_M1_CUBE1FACT;

    // Static columns - Dimension tables
    public static final Column COLUMN_D1H1L1_D1H1L1TABLE;
    public static final Column COLUMN_D1H1L1_NAME_D1H1L1TABLE;
    public static final Column COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE;

    public static final Column COLUMN_D2H1L1_D2H1L1TABLE;
    public static final Column COLUMN_D2H1L1_NAME_D2H1L1TABLE;
    public static final Column COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE;

    public static final Column COLUMN_D2H2L2_D2H2L2TABLE;
    public static final Column COLUMN_D2H2L1_D2H2L2TABLE;
    public static final Column COLUMN_D2H2L2_NAME_D2H2L2TABLE;
    public static final Column COLUMN_D2H2L1_NAME_D2H2L2TABLE;
    public static final Column COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE;
    public static final Column COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE;

    public static final Column COLUMN_D3H1L1_D3H1L1TABLE;
    public static final Column COLUMN_D3H1L1_NAME_D3H1L1TABLE;
    public static final Column COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE;

    public static final Column COLUMN_D3H2L2_D3H2L2TABLE;
    public static final Column COLUMN_D3H2L2_ID_D3H2L2TABLE;
    public static final Column COLUMN_D3H2L1_ID_D3H2L2TABLE;
    public static final Column COLUMN_D3H2L2_NAME_D3H2L2TABLE;
    public static final Column COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE;

    public static final Column COLUMN_D3H2L1_D3H2L1TABLE;
    public static final Column COLUMN_D3H2L1_NAME_D3H2L1TABLE;
    public static final Column COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE;

    public static final Column COLUMN_D3H3L3_D3H3L3TABLE;
    public static final Column COLUMN_D3H3L2_ID_D3H3L3TABLE;
    public static final Column COLUMN_D3H3L3_NAME_D3H3L3TABLE;
    public static final Column COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE;

    public static final Column COLUMN_D3H3L2_D3H3L2TABLE;
    public static final Column COLUMN_D3H3L1_ID_D3H3L2TABLE;
    public static final Column COLUMN_D3H3L2_NAME_D3H3L2TABLE;
    public static final Column COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE;

    public static final Column COLUMN_D3H3L1_D3H3L1TABLE;
    public static final Column COLUMN_D3H3L1_NAME_D3H3L1TABLE;
    public static final Column COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE;

    // Static tables
    public static final PhysicalTable TABLE_CUBE1FACT;
    public static final PhysicalTable TABLE_D1H1L1TABLE;
    public static final PhysicalTable TABLE_D2H1L1TABLE;
    public static final PhysicalTable TABLE_D2H2L2TABLE;
    public static final PhysicalTable TABLE_D3H1L1TABLE;
    public static final PhysicalTable TABLE_D3H2L2TABLE;
    public static final PhysicalTable TABLE_D3H2L1TABLE;
    public static final PhysicalTable TABLE_D3H3L3TABLE;
    public static final PhysicalTable TABLE_D3H3L2TABLE;
    public static final PhysicalTable TABLE_D3H3L1TABLE;

    // Static queries
    public static final TableQuery QUERY_CUBE1FACT;
    public static final TableQuery QUERY_D1H1L1TABLE;
    public static final TableQuery QUERY_D2H1L1TABLE;
    public static final TableQuery QUERY_D2H2L2TABLE;
    public static final TableQuery QUERY_D3H1L1TABLE;
    public static final TableQuery QUERY_D3H2L2TABLE;
    public static final TableQuery QUERY_D3H2L1TABLE;
    public static final TableQuery QUERY_D3H3L3TABLE;
    public static final TableQuery QUERY_D3H3L2TABLE;
    public static final TableQuery QUERY_D3H3L1TABLE;

    // Static join queries
    public static final JoinQuery JOIN_D3H2;
    public static final JoinQuery JOIN_D3H3_INNER;
    public static final JoinQuery JOIN_D3H3;

    // Static levels
    public static final Level LEVEL_D1H1L1;
    public static final Level LEVEL_D2H1L1;
    public static final Level LEVEL_D2H2L1;
    public static final Level LEVEL_D2H2L2;
    public static final Level LEVEL_D3H1L1;
    public static final Level LEVEL_D3H2L1;
    public static final Level LEVEL_D3H2L2;
    public static final Level LEVEL_D3H3L1;
    public static final Level LEVEL_D3H3L2;
    public static final Level LEVEL_D3H3L3;

    // Static hierarchies
    public static final ExplicitHierarchy HIERARCHY_D1H1;
    public static final ExplicitHierarchy HIERARCHY_D2H1;
    public static final ExplicitHierarchy HIERARCHY_D2H2;
    public static final ExplicitHierarchy HIERARCHY_D3H1;
    public static final ExplicitHierarchy HIERARCHY_D3H2;
    public static final ExplicitHierarchy HIERARCHY_D3H3;

    // Static dimensions
    public static final StandardDimension DIMENSION_SCHEMA1;
    public static final StandardDimension DIMENSION_SCHEMA2;
    public static final StandardDimension DIMENSION_SCHEMA3;

    // Static measures
    public static final SumMeasure MEASURE_1;

    // Static measure group
    public static final MeasureGroup MEASUREGROUP_1;

    // Static dimension connectors
    public static final DimensionConnector CONNECTOR_DIMENSION1;
    public static final DimensionConnector CONNECTOR_DIMENSION2;
    public static final DimensionConnector CONNECTOR_DIMENSION3;

    // Static cube
    public static final PhysicalCube CUBE_CUBE1;

    // Static database schema and catalog
    public static final DatabaseSchema DATABASE_SCHEMA_EXPRESSIVENAMES;
    public static final Catalog CATALOG_EXPRESSIVENAMES;

    static {
        // Initialize columns - Fact table
        COLUMN_D1_CUBE1FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D1_CUBE1FACT.setId("_column_d1_cube1fact");
        COLUMN_D1_CUBE1FACT.setName("D1");
        COLUMN_D1_CUBE1FACT.setType(ColumnType.VARCHAR);

        COLUMN_D2_CUBE1FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2_CUBE1FACT.setId("_column_d2_cube1fact");
        COLUMN_D2_CUBE1FACT.setName("D2");
        COLUMN_D2_CUBE1FACT.setType(ColumnType.VARCHAR);

        COLUMN_D3_CUBE1FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3_CUBE1FACT.setId("_column_d3_cube1fact");
        COLUMN_D3_CUBE1FACT.setName("D3");
        COLUMN_D3_CUBE1FACT.setType(ColumnType.VARCHAR);

        COLUMN_M1_CUBE1FACT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_M1_CUBE1FACT.setId("_column_m1_cube1fact");
        COLUMN_M1_CUBE1FACT.setName("M1");
        COLUMN_M1_CUBE1FACT.setType(ColumnType.INTEGER);

        // Initialize columns - D1H1L1 table
        COLUMN_D1H1L1_D1H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D1H1L1_D1H1L1TABLE.setId("_column_d1h1l1_d1h1l1table");
        COLUMN_D1H1L1_D1H1L1TABLE.setName(D_1_H_1_L_1);
        COLUMN_D1H1L1_D1H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D1H1L1_NAME_D1H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D1H1L1_NAME_D1H1L1TABLE.setId("_column_d1h1l1_name_d1h1l1table");
        COLUMN_D1H1L1_NAME_D1H1L1TABLE.setName("D1H1L1_NAME");
        COLUMN_D1H1L1_NAME_D1H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE.setId("_column_d1h1l1_ordinal_d1h1l1table");
        COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE.setName("D1H1L1_Ordinal");
        COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D2H1L1 table
        COLUMN_D2H1L1_D2H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H1L1_D2H1L1TABLE.setId("_column_d2h1l1_d2h1l1table");
        COLUMN_D2H1L1_D2H1L1TABLE.setName(D_2_H_1_L_1);
        COLUMN_D2H1L1_D2H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H1L1_NAME_D2H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H1L1_NAME_D2H1L1TABLE.setId("_column_d2h1l1_name_d2h1l1table");
        COLUMN_D2H1L1_NAME_D2H1L1TABLE.setName("D2H1L1_NAME");
        COLUMN_D2H1L1_NAME_D2H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE.setId("_column_d2h1l1_ordinal_d2h1l1table");
        COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE.setName("D2H1L1_Ordinal");
        COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D2H2L2 table
        COLUMN_D2H2L2_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L2_D2H2L2TABLE.setId("_column_d2h2l2_d2h2l2table");
        COLUMN_D2H2L2_D2H2L2TABLE.setName(D_2_H_2_L_2);
        COLUMN_D2H2L2_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H2L1_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L1_D2H2L2TABLE.setId("_column_d2h2l1_d2h2l2table");
        COLUMN_D2H2L1_D2H2L2TABLE.setName("D2H2L1");
        COLUMN_D2H2L1_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H2L2_NAME_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L2_NAME_D2H2L2TABLE.setId("_column_d2h2l2_name_d2h2l2table");
        COLUMN_D2H2L2_NAME_D2H2L2TABLE.setName("D2H2L2_NAME");
        COLUMN_D2H2L2_NAME_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H2L1_NAME_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L1_NAME_D2H2L2TABLE.setId("_column_d2h2l1_name_d2h2l2table");
        COLUMN_D2H2L1_NAME_D2H2L2TABLE.setName("D2H2L1_NAME");
        COLUMN_D2H2L1_NAME_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE.setId("_column_d2h2l2_ordinal_d2h2l2table");
        COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE.setName("D2H2L2_Ordinal");
        COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE.setId("_column_d2h2l1_ordinal_d2h2l2table");
        COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE.setName("D2H2L1_Ordinal");
        COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H1L1 table
        COLUMN_D3H1L1_D3H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H1L1_D3H1L1TABLE.setId("_column_d3h1l1_d3h1l1table");
        COLUMN_D3H1L1_D3H1L1TABLE.setName(D_3_H_1_L_1);
        COLUMN_D3H1L1_D3H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H1L1_NAME_D3H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H1L1_NAME_D3H1L1TABLE.setId("_column_d3h1l1_name_d3h1l1table");
        COLUMN_D3H1L1_NAME_D3H1L1TABLE.setName("D3H1L1_NAME");
        COLUMN_D3H1L1_NAME_D3H1L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE.setId("_column_d3h1l1_ordinal_d3h1l1table");
        COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE.setName("D3H1L1_Ordinal");
        COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H2L2 table
        COLUMN_D3H2L2_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L2_D3H2L2TABLE.setId("_column_d3h2l2_d3h2l2table");
        COLUMN_D3H2L2_D3H2L2TABLE.setName(D_3_H_2_L_2);
        COLUMN_D3H2L2_D3H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L2_ID_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L2_ID_D3H2L2TABLE.setId("_column_d3h2l2_id_d3h2l2table");
        COLUMN_D3H2L2_ID_D3H2L2TABLE.setName("D3H2L2_id");
        COLUMN_D3H2L2_ID_D3H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L1_ID_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L1_ID_D3H2L2TABLE.setId("_column_d3h2l1_id_d3h2l2table");
        COLUMN_D3H2L1_ID_D3H2L2TABLE.setName("D3H2L1_id");
        COLUMN_D3H2L1_ID_D3H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L2_NAME_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L2_NAME_D3H2L2TABLE.setId("_column_d3h2l2_name_d3h2l2table");
        COLUMN_D3H2L2_NAME_D3H2L2TABLE.setName("D3H2L2_NAME");
        COLUMN_D3H2L2_NAME_D3H2L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE.setId("_column_d3h2l2_ordinal_d3h2l2table");
        COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE.setName("D3H2L2_Ordinal");
        COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H2L1 table
        COLUMN_D3H2L1_D3H2L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L1_D3H2L1TABLE.setId("_column_d3h2l1_d3h2l1table");
        COLUMN_D3H2L1_D3H2L1TABLE.setName(D_3_H_2_L_1);
        COLUMN_D3H2L1_D3H2L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L1_NAME_D3H2L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L1_NAME_D3H2L1TABLE.setId("_column_d3h2l1_name_d3h2l1table");
        COLUMN_D3H2L1_NAME_D3H2L1TABLE.setName("D3H2L1_NAME");
        COLUMN_D3H2L1_NAME_D3H2L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE.setId("_column_d3h2l1_ordinal_d3h2l1table");
        COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE.setName("D3H2L1_Ordinal");
        COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H3L3 table
        COLUMN_D3H3L3_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L3_D3H3L3TABLE.setId("_column_d3h3l3_d3h3l3table");
        COLUMN_D3H3L3_D3H3L3TABLE.setName(D_3_H_3_L_3);
        COLUMN_D3H3L3_D3H3L3TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L2_ID_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L2_ID_D3H3L3TABLE.setId("_column_d3h3l2_id_d3h3l3table");
        COLUMN_D3H3L2_ID_D3H3L3TABLE.setName("D3H3L2_id");
        COLUMN_D3H3L2_ID_D3H3L3TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L3_NAME_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L3_NAME_D3H3L3TABLE.setId("_column_d3h3l3_name_d3h3l3table");
        COLUMN_D3H3L3_NAME_D3H3L3TABLE.setName("D3H3L3_NAME");
        COLUMN_D3H3L3_NAME_D3H3L3TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE.setId("_column_d3h3l3_ordinal_d3h3l3table");
        COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE.setName("D3H3L3_Ordinal");
        COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H3L2 table
        COLUMN_D3H3L2_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L2_D3H3L2TABLE.setId("_column_d3h3l2_d3h3l2table");
        COLUMN_D3H3L2_D3H3L2TABLE.setName(D_3_H_3_L_2);
        COLUMN_D3H3L2_D3H3L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L1_ID_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L1_ID_D3H3L2TABLE.setId("_column_d3h3l1_id_d3h3l2table");
        COLUMN_D3H3L1_ID_D3H3L2TABLE.setName("D3H3L1_id");
        COLUMN_D3H3L1_ID_D3H3L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L2_NAME_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L2_NAME_D3H3L2TABLE.setId("_column_d3h3l2_name_d3h3l2table");
        COLUMN_D3H3L2_NAME_D3H3L2TABLE.setName("D3H3L2_NAME");
        COLUMN_D3H3L2_NAME_D3H3L2TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE.setId("_column_d3h3l2_ordinal_d3h3l2table");
        COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE.setName("D3H3L2_Ordinal");
        COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE.setType(ColumnType.VARCHAR);

        // Initialize columns - D3H3L1 table
        COLUMN_D3H3L1_D3H3L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L1_D3H3L1TABLE.setId("_column_d3h3l1_d3h3l1table");
        COLUMN_D3H3L1_D3H3L1TABLE.setName(D_3_H_3_L_1);
        COLUMN_D3H3L1_D3H3L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L1_NAME_D3H3L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L1_NAME_D3H3L1TABLE.setId("_column_d3h3l1_name_d3h3l1table");
        COLUMN_D3H3L1_NAME_D3H3L1TABLE.setName("D3H3L1_NAME");
        COLUMN_D3H3L1_NAME_D3H3L1TABLE.setType(ColumnType.VARCHAR);

        COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE.setId("_column_d3h3l1_ordinal_d3h3l1table");
        COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE.setName("D3H3L1_Ordinal");
        COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE.setType(ColumnType.VARCHAR);

        // Initialize tables
        TABLE_CUBE1FACT = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_CUBE1FACT.setId("_table_cube1fact");
        TABLE_CUBE1FACT.setName("Cube1Fact");
        TABLE_CUBE1FACT.getColumns()
                .addAll(List.of(COLUMN_D1_CUBE1FACT, COLUMN_D2_CUBE1FACT, COLUMN_D3_CUBE1FACT, COLUMN_M1_CUBE1FACT));

        TABLE_D1H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D1H1L1TABLE.setId("_table_d1h1l1table");
        TABLE_D1H1L1TABLE.setName("D1H1L1Table");
        TABLE_D1H1L1TABLE.getColumns().addAll(
                List.of(COLUMN_D1H1L1_D1H1L1TABLE, COLUMN_D1H1L1_NAME_D1H1L1TABLE, COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE));

        TABLE_D2H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D2H1L1TABLE.setId("_table_d2h1l1table");
        TABLE_D2H1L1TABLE.setName("D2H1L1Table");
        TABLE_D2H1L1TABLE.getColumns().addAll(
                List.of(COLUMN_D2H1L1_D2H1L1TABLE, COLUMN_D2H1L1_NAME_D2H1L1TABLE, COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE));

        TABLE_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D2H2L2TABLE.setId("_table_d2h2l2table");
        TABLE_D2H2L2TABLE.setName("D2H2L2Table");
        TABLE_D2H2L2TABLE.getColumns().addAll(List.of(COLUMN_D2H2L2_D2H2L2TABLE, COLUMN_D2H2L2_NAME_D2H2L2TABLE,
                COLUMN_D2H2L1_NAME_D2H2L2TABLE, COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE, COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE));

        TABLE_D3H1L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H1L1TABLE.setId("_table_d3h1l1table");
        TABLE_D3H1L1TABLE.setName("D3H1L1Table");
        TABLE_D3H1L1TABLE.getColumns().addAll(
                List.of(COLUMN_D3H1L1_D3H1L1TABLE, COLUMN_D3H1L1_NAME_D3H1L1TABLE, COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE));

        TABLE_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H2L2TABLE.setId("_table_d3h2l2table");
        TABLE_D3H2L2TABLE.setName("D3H2L2Table");
        TABLE_D3H2L2TABLE.getColumns().addAll(List.of(COLUMN_D3H2L2_D3H2L2TABLE, COLUMN_D3H2L2_ID_D3H2L2TABLE,
                COLUMN_D3H2L1_ID_D3H2L2TABLE, COLUMN_D3H2L2_NAME_D3H2L2TABLE, COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE));

        TABLE_D3H2L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H2L1TABLE.setId("_table_d3h2l1table");
        TABLE_D3H2L1TABLE.setName("D3H2L1Table");
        TABLE_D3H2L1TABLE.getColumns().addAll(
                List.of(COLUMN_D3H2L1_D3H2L1TABLE, COLUMN_D3H2L1_NAME_D3H2L1TABLE, COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE));

        TABLE_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H3L3TABLE.setId("_table_d3h3l3table");
        TABLE_D3H3L3TABLE.setName("D3H3L3Table");
        TABLE_D3H3L3TABLE.getColumns().addAll(List.of(COLUMN_D3H3L3_D3H3L3TABLE, COLUMN_D3H3L2_ID_D3H3L3TABLE,
                COLUMN_D3H3L3_NAME_D3H3L3TABLE, COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE));

        TABLE_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H3L2TABLE.setId("_table_d3h3l2table");
        TABLE_D3H3L2TABLE.setName("D3H3L2Table");
        TABLE_D3H3L2TABLE.getColumns().addAll(List.of(COLUMN_D3H3L2_D3H3L2TABLE, COLUMN_D3H3L1_ID_D3H3L2TABLE,
                COLUMN_D3H3L2_NAME_D3H3L2TABLE, COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE));

        TABLE_D3H3L1TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        TABLE_D3H3L1TABLE.setId("_table_d3h3l1table");
        TABLE_D3H3L1TABLE.setName("D3H3L1Table");
        TABLE_D3H3L1TABLE.getColumns().addAll(
                List.of(COLUMN_D3H3L1_D3H3L1TABLE, COLUMN_D3H3L1_NAME_D3H3L1TABLE, COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE));

        // Initialize table queries
        QUERY_CUBE1FACT = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_CUBE1FACT.setId("_query_cube1fact");
        QUERY_CUBE1FACT.setTable(TABLE_CUBE1FACT);

        QUERY_D1H1L1TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D1H1L1TABLE.setId("_query_d1h1l1table");
        QUERY_D1H1L1TABLE.setTable(TABLE_D1H1L1TABLE);

        QUERY_D2H1L1TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D2H1L1TABLE.setId("_query_d2h1l1table");
        QUERY_D2H1L1TABLE.setTable(TABLE_D2H1L1TABLE);

        QUERY_D2H2L2TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D2H2L2TABLE.setId("_query_d2h2l2table");
        QUERY_D2H2L2TABLE.setTable(TABLE_D2H2L2TABLE);

        QUERY_D3H1L1TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H1L1TABLE.setId("_query_d3h1l1table");
        QUERY_D3H1L1TABLE.setTable(TABLE_D3H1L1TABLE);

        QUERY_D3H2L2TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H2L2TABLE.setId("_query_d3h2l2table");
        QUERY_D3H2L2TABLE.setTable(TABLE_D3H2L2TABLE);

        QUERY_D3H2L1TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H2L1TABLE.setId("_query_d3h2l1table");
        QUERY_D3H2L1TABLE.setTable(TABLE_D3H2L1TABLE);

        QUERY_D3H3L3TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H3L3TABLE.setId("_query_d3h3l3table");
        QUERY_D3H3L3TABLE.setTable(TABLE_D3H3L3TABLE);

        QUERY_D3H3L2TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H3L2TABLE.setId("_query_d3h3l2table");
        QUERY_D3H3L2TABLE.setTable(TABLE_D3H3L2TABLE);

        QUERY_D3H3L1TABLE = RolapMappingFactory.eINSTANCE.createTableQuery();
        QUERY_D3H3L1TABLE.setId("_query_d3h3l1table");
        QUERY_D3H3L1TABLE.setTable(TABLE_D3H3L1TABLE);

        // Initialize join queries
        JoinedQueryElement leftElement1 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        leftElement1.setKey(COLUMN_D3H2L1_ID_D3H2L2TABLE);
        leftElement1.setQuery(QUERY_D3H2L2TABLE);

        JoinedQueryElement rightElement1 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        rightElement1.setKey(COLUMN_D3H2L1_D3H2L1TABLE);
        rightElement1.setQuery(QUERY_D3H2L1TABLE);

        JOIN_D3H2 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_D3H2.setId("_joinquery_d3h2");
        JOIN_D3H2.setLeft(leftElement1);
        JOIN_D3H2.setRight(rightElement1);

        JoinedQueryElement leftElement2 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        leftElement2.setKey(COLUMN_D3H3L1_ID_D3H3L2TABLE);
        leftElement2.setQuery(QUERY_D3H3L2TABLE);

        JoinedQueryElement rightElement2 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        rightElement2.setKey(COLUMN_D3H3L1_D3H3L1TABLE);
        rightElement2.setQuery(QUERY_D3H3L1TABLE);

        JOIN_D3H3_INNER = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_D3H3_INNER.setId("_joinquery_d3h3_inner");
        JOIN_D3H3_INNER.setLeft(leftElement2);
        JOIN_D3H3_INNER.setRight(rightElement2);

        JoinedQueryElement leftElement3 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        leftElement3.setKey(COLUMN_D3H3L2_ID_D3H3L3TABLE);
        leftElement3.setQuery(QUERY_D3H3L3TABLE);

        JoinedQueryElement rightElement3 = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        rightElement3.setKey(COLUMN_D3H3L2_D3H3L2TABLE);
        rightElement3.setQuery(JOIN_D3H3_INNER);

        JOIN_D3H3 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN_D3H3.setId("_joinquery_d3h3");
        JOIN_D3H3.setLeft(leftElement3);
        JOIN_D3H3.setRight(rightElement3);

        // Initialize levels
        LEVEL_D1H1L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D1H1L1.setId("_level_d1h1l1");
        LEVEL_D1H1L1.setName(D_1_H_1_L_1);
        LEVEL_D1H1L1.setColumn(COLUMN_D1H1L1_D1H1L1TABLE);
        LEVEL_D1H1L1.setOrdinalColumn(COLUMN_D1H1L1_ORDINAL_D1H1L1TABLE);
        LEVEL_D1H1L1.setNameColumn(COLUMN_D1H1L1_NAME_D1H1L1TABLE);
        LEVEL_D1H1L1.setDescription("Level 1 Dimension 1 Hierarchy1");

        LEVEL_D2H1L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D2H1L1.setId("_level_d2h1l1");
        LEVEL_D2H1L1.setName(D_2_H_1_L_1);
        LEVEL_D2H1L1.setColumn(COLUMN_D2H1L1_D2H1L1TABLE);
        LEVEL_D2H1L1.setNameColumn(COLUMN_D2H1L1_NAME_D2H1L1TABLE);
        LEVEL_D2H1L1.setOrdinalColumn(COLUMN_D2H1L1_ORDINAL_D2H1L1TABLE);
        LEVEL_D2H1L1.setDescription("Level 1 Hierarchy 1 Dimension 2");

        LEVEL_D2H2L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D2H2L1.setId("_level_d2h2l1");
        LEVEL_D2H2L1.setName("D2H2L1");
        LEVEL_D2H2L1.setColumn(COLUMN_D2H2L1_D2H2L2TABLE);
        LEVEL_D2H2L1.setNameColumn(COLUMN_D2H2L1_NAME_D2H2L2TABLE);
        LEVEL_D2H2L1.setOrdinalColumn(COLUMN_D2H2L1_ORDINAL_D2H2L2TABLE);
        LEVEL_D2H2L1.setDescription("Level 2 Hierarchy 2 Dimension 2");

        LEVEL_D2H2L2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D2H2L2.setId("_level_d2h2l2");
        LEVEL_D2H2L2.setName(D_2_H_2_L_2);
        LEVEL_D2H2L2.setColumn(COLUMN_D2H2L2_D2H2L2TABLE);
        LEVEL_D2H2L2.setNameColumn(COLUMN_D2H2L2_NAME_D2H2L2TABLE);
        LEVEL_D2H2L2.setOrdinalColumn(COLUMN_D2H2L2_ORDINAL_D2H2L2TABLE);
        LEVEL_D2H2L2.setDescription("Level 2 Dimension 3");

        LEVEL_D3H1L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H1L1.setId("_level_d3h1l1");
        LEVEL_D3H1L1.setName(D_3_H_1_L_1);
        LEVEL_D3H1L1.setColumn(COLUMN_D3H1L1_D3H1L1TABLE);
        LEVEL_D3H1L1.setNameColumn(COLUMN_D3H1L1_NAME_D3H1L1TABLE);
        LEVEL_D3H1L1.setOrdinalColumn(COLUMN_D3H1L1_ORDINAL_D3H1L1TABLE);
        LEVEL_D3H1L1.setDescription("Level 1 Hierarchy1 Dimension 3");

        LEVEL_D3H2L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H2L1.setId("_level_d3h2l1");
        LEVEL_D3H2L1.setName(D_3_H_2_L_1);
        LEVEL_D3H2L1.setColumn(COLUMN_D3H2L1_D3H2L1TABLE);
        LEVEL_D3H2L1.setNameColumn(COLUMN_D3H2L1_NAME_D3H2L1TABLE);
        LEVEL_D3H2L1.setOrdinalColumn(COLUMN_D3H2L1_ORDINAL_D3H2L1TABLE);
        LEVEL_D3H2L1.setDescription("Level 1 Hierarchy2 Dimension 3");

        LEVEL_D3H2L2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H2L2.setId("_level_d3h2l2");
        LEVEL_D3H2L2.setName(D_3_H_2_L_2);
        LEVEL_D3H2L2.setColumn(COLUMN_D3H2L2_D3H2L2TABLE);
        LEVEL_D3H2L2.setNameColumn(COLUMN_D3H2L2_NAME_D3H2L2TABLE);
        LEVEL_D3H2L2.setOrdinalColumn(COLUMN_D3H2L2_ORDINAL_D3H2L2TABLE);
        LEVEL_D3H2L2.setDescription("Level 2 Hierarchy2 Dimension 3");

        LEVEL_D3H3L1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H3L1.setId("_level_d3h3l1");
        LEVEL_D3H3L1.setName(D_3_H_3_L_1);
        LEVEL_D3H3L1.setColumn(COLUMN_D3H3L1_D3H3L1TABLE);
        LEVEL_D3H3L1.setNameColumn(COLUMN_D3H3L1_NAME_D3H3L1TABLE);
        LEVEL_D3H3L1.setOrdinalColumn(COLUMN_D3H3L1_ORDINAL_D3H3L1TABLE);
        LEVEL_D3H3L1.setDescription("Level 1 Hierarchy3 Dimension 3");

        LEVEL_D3H3L2 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H3L2.setId("_level_d3h3l2");
        LEVEL_D3H3L2.setName(D_3_H_3_L_2);
        LEVEL_D3H3L2.setColumn(COLUMN_D3H3L2_D3H3L2TABLE);
        LEVEL_D3H3L2.setNameColumn(COLUMN_D3H3L2_NAME_D3H3L2TABLE);
        LEVEL_D3H3L2.setOrdinalColumn(COLUMN_D3H3L2_ORDINAL_D3H3L2TABLE);
        LEVEL_D3H3L2.setDescription("Level 2 Hierarchy3 Dimension 3");

        LEVEL_D3H3L3 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL_D3H3L3.setId("_level_d3h3l3");
        LEVEL_D3H3L3.setName(D_3_H_3_L_3);
        LEVEL_D3H3L3.setColumn(COLUMN_D3H3L3_D3H3L3TABLE);
        LEVEL_D3H3L3.setNameColumn(COLUMN_D3H3L3_NAME_D3H3L3TABLE);
        LEVEL_D3H3L3.setOrdinalColumn(COLUMN_D3H3L3_ORDINAL_D3H3L3TABLE);
        LEVEL_D3H3L3.setDescription("Level 3 Hierarchy3 Dimension 3");

        // Initialize hierarchies
        HIERARCHY_D1H1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D1H1.setId("_hierarchy_d1h1");
        HIERARCHY_D1H1.setHasAll(true);
        HIERARCHY_D1H1.setName("D1H1");
        HIERARCHY_D1H1.setPrimaryKey(COLUMN_D1H1L1_D1H1L1TABLE);
        HIERARCHY_D1H1.setDescription("Hierarchy 1 Dimension 1");
        HIERARCHY_D1H1.setQuery(QUERY_D1H1L1TABLE);
        HIERARCHY_D1H1.getLevels().add(LEVEL_D1H1L1);

        HIERARCHY_D2H1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D2H1.setId("_hierarchy_d2h1");
        HIERARCHY_D2H1.setHasAll(true);
        HIERARCHY_D2H1.setName("D2H1");
        HIERARCHY_D2H1.setPrimaryKey(COLUMN_D2H1L1_D2H1L1TABLE);
        HIERARCHY_D2H1.setDescription("Hierarchy 1 Dimension 2");
        HIERARCHY_D2H1.setQuery(QUERY_D2H1L1TABLE);
        HIERARCHY_D2H1.getLevels().add(LEVEL_D2H1L1);

        HIERARCHY_D2H2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D2H2.setId("_hierarchy_d2h2");
        HIERARCHY_D2H2.setHasAll(true);
        HIERARCHY_D2H2.setName("D2H2");
        HIERARCHY_D2H2.setDescription("Hierarchy 2 Dimension 2");
        HIERARCHY_D2H2.setPrimaryKey(COLUMN_D2H2L2_D2H2L2TABLE);
        HIERARCHY_D2H2.setQuery(QUERY_D2H2L2TABLE);
        HIERARCHY_D2H2.getLevels().addAll(List.of(LEVEL_D2H2L1, LEVEL_D2H2L2));

        HIERARCHY_D3H1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D3H1.setId("_hierarchy_d3h1");
        HIERARCHY_D3H1.setHasAll(true);
        HIERARCHY_D3H1.setName("D3H1");
        HIERARCHY_D3H1.setPrimaryKey(COLUMN_D3H1L1_D3H1L1TABLE);
        HIERARCHY_D3H1.setDescription("Hierarchy 1 Dimension 3");
        HIERARCHY_D3H1.setQuery(QUERY_D3H1L1TABLE);
        HIERARCHY_D3H1.getLevels().add(LEVEL_D3H1L1);

        HIERARCHY_D3H2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D3H2.setId("_hierarchy_d3h2");
        HIERARCHY_D3H2.setHasAll(true);
        HIERARCHY_D3H2.setName("D3H2");
        HIERARCHY_D3H2.setPrimaryKey(COLUMN_D3H2L2_D3H2L2TABLE);
        HIERARCHY_D3H2.setDescription("Hierarchy 2 Dimension 3");
        HIERARCHY_D3H2.setQuery(JOIN_D3H2);
        HIERARCHY_D3H2.getLevels().addAll(List.of(LEVEL_D3H2L1, LEVEL_D3H2L2));

        HIERARCHY_D3H3 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY_D3H3.setId("_hierarchy_d3h3");
        HIERARCHY_D3H3.setHasAll(true);
        HIERARCHY_D3H3.setName("D3H3");
        HIERARCHY_D3H3.setPrimaryKey(COLUMN_D3H3L3_D3H3L3TABLE);
        HIERARCHY_D3H3.setDescription("Hierarchy 1 Dimension 3");
        HIERARCHY_D3H3.setQuery(JOIN_D3H3);
        HIERARCHY_D3H3.getLevels().addAll(List.of(LEVEL_D3H3L1, LEVEL_D3H3L2, LEVEL_D3H3L3));

        // Initialize dimensions
        DIMENSION_SCHEMA1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SCHEMA1.setId("_dimension_dimension1");
        DIMENSION_SCHEMA1.setName(DIMENSION_1);
        DIMENSION_SCHEMA1.setDescription("Hierarchy 1 Dimension 1");
        DIMENSION_SCHEMA1.getHierarchies().add(HIERARCHY_D1H1);

        DIMENSION_SCHEMA2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SCHEMA2.setId("_dimension_dimension2");
        DIMENSION_SCHEMA2.setName(DIMENSION_2);
        DIMENSION_SCHEMA2.getHierarchies().addAll(List.of(HIERARCHY_D2H1, HIERARCHY_D2H2));

        DIMENSION_SCHEMA3 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION_SCHEMA3.setId("_dimension_dimension3");
        DIMENSION_SCHEMA3.setName(DIMENSION_3);
        DIMENSION_SCHEMA3.getHierarchies().addAll(List.of(HIERARCHY_D3H1, HIERARCHY_D3H2, HIERARCHY_D3H3));

        // Initialize measure
        MEASURE_1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        MEASURE_1.setId("_measure_measure1");
        MEASURE_1.setName("Measure1");
        MEASURE_1.setColumn(COLUMN_M1_CUBE1FACT);
        MEASURE_1.setFormatString("Standard");

        // Initialize measure group
        MEASUREGROUP_1 = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        MEASUREGROUP_1.getMeasures().add(MEASURE_1);

        // Initialize dimension connectors
        CONNECTOR_DIMENSION1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIMENSION1.setId("_connector_dimension1");
        CONNECTOR_DIMENSION1.setOverrideDimensionName(DIMENSION_1);
        CONNECTOR_DIMENSION1.setDimension(DIMENSION_SCHEMA1);
        CONNECTOR_DIMENSION1.setForeignKey(COLUMN_D1_CUBE1FACT);

        CONNECTOR_DIMENSION2 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIMENSION2.setId("_connector_dimension2");
        CONNECTOR_DIMENSION2.setOverrideDimensionName(DIMENSION_2);
        CONNECTOR_DIMENSION2.setDimension(DIMENSION_SCHEMA2);
        CONNECTOR_DIMENSION2.setForeignKey(COLUMN_D2_CUBE1FACT);

        CONNECTOR_DIMENSION3 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        CONNECTOR_DIMENSION3.setId("_connector_dimension3");
        CONNECTOR_DIMENSION3.setOverrideDimensionName(DIMENSION_3);
        CONNECTOR_DIMENSION3.setDimension(DIMENSION_SCHEMA3);
        CONNECTOR_DIMENSION3.setForeignKey(COLUMN_D3_CUBE1FACT);

        // Initialize cube
        CUBE_CUBE1 = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE_CUBE1.setId("_cube_cube1");
        CUBE_CUBE1.setName(CUBE_1_NAME);
        CUBE_CUBE1.setDescription("Test Cube");
        CUBE_CUBE1.setQuery(QUERY_CUBE1FACT);
        CUBE_CUBE1.getDimensionConnectors()
                .addAll(List.of(CONNECTOR_DIMENSION1, CONNECTOR_DIMENSION2, CONNECTOR_DIMENSION3));
        CUBE_CUBE1.getMeasureGroups().add(MEASUREGROUP_1);

        // Initialize database schema and catalog
        DATABASE_SCHEMA_EXPRESSIVENAMES = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        DATABASE_SCHEMA_EXPRESSIVENAMES.setId("_databaseschema_expressivenames");
        DATABASE_SCHEMA_EXPRESSIVENAMES.setName(CATALOG_NAME);
        DATABASE_SCHEMA_EXPRESSIVENAMES.getTables()
                .addAll(List.of(TABLE_CUBE1FACT, TABLE_D1H1L1TABLE, TABLE_D2H1L1TABLE, TABLE_D2H2L2TABLE,
                        TABLE_D3H1L1TABLE, TABLE_D3H2L2TABLE, TABLE_D3H2L1TABLE, TABLE_D3H3L3TABLE, TABLE_D3H3L2TABLE,
                        TABLE_D3H3L1TABLE));

        CATALOG_EXPRESSIVENAMES = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG_EXPRESSIVENAMES.setId("_catalog_expressivenames");
        CATALOG_EXPRESSIVENAMES.setName(CATALOG_NAME);
        CATALOG_EXPRESSIVENAMES.setDescription("ExpressiveNames Sample Database - EMF Version");
        CATALOG_EXPRESSIVENAMES.getCubes().add(CUBE_CUBE1);
        CATALOG_EXPRESSIVENAMES.getDbschemas().add(DATABASE_SCHEMA_EXPRESSIVENAMES);

        // Add documentation
        document(CATALOG_EXPRESSIVENAMES, "ExpressiveNames Database",
                "Sample catalog demonstrating complex hierarchies and naming patterns", 1, 0, 0, false, 0);
        document(CUBE_CUBE1, "Test Cube", "Cube with multiple dimensions and complex hierarchies", 1, 1, 0, true, 0);
    }

    @Override
    public CatalogMapping get() {
        return CATALOG_EXPRESSIVENAMES;
    }
}
