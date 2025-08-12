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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.population;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
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
@MappingInstance(kind = Kind.COMPLEX, number = "99.1.1", source = Source.EMF, group = "Full Examples") // NOSONAR

public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String STATE = "state";
    private static final String GENDER = "Gender";
    private static final String GENDER_ID = "gender_id";
    private static final String AGE_GROUP = "AgeGroup";
    private static final String GEOGRAPHICAL = "Geographical";

    private static final String POPULATION = "Population";

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema");

        //year, state_id, gender_id, age, number
        //INTEGER, INTEGER, INTEGER, INTEGER
        Column YEAR_COLUMN_IN_POPULATION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        YEAR_COLUMN_IN_POPULATION.setName("year");
        YEAR_COLUMN_IN_POPULATION.setId("_col_population_year");
        YEAR_COLUMN_IN_POPULATION.setType(ColumnType.INTEGER);

        Column STATE_ID_COLUMN_IN_POPULATION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        STATE_ID_COLUMN_IN_POPULATION.setName("state_id");
        STATE_ID_COLUMN_IN_POPULATION.setId("_col_population_state_id");
        STATE_ID_COLUMN_IN_POPULATION.setType(ColumnType.INTEGER);

        Column GENDER_ID_COLUMN_IN_POPULATION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        GENDER_ID_COLUMN_IN_POPULATION.setName(GENDER_ID);
        GENDER_ID_COLUMN_IN_POPULATION.setId("_col_population_gender_id");
        GENDER_ID_COLUMN_IN_POPULATION.setType(ColumnType.INTEGER);

        Column AGE_COLUMN_IN_POPULATION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        AGE_COLUMN_IN_POPULATION.setName("age");
        AGE_COLUMN_IN_POPULATION.setId("_col_population_age");
        AGE_COLUMN_IN_POPULATION.setType(ColumnType.INTEGER);

        Column NUMBER_COLUMN_IN_POPULATION = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        NUMBER_COLUMN_IN_POPULATION.setName("number");
        NUMBER_COLUMN_IN_POPULATION.setId("_col_population_number");
        NUMBER_COLUMN_IN_POPULATION.setType(ColumnType.INTEGER);

        PhysicalTable POPULATION_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        POPULATION_TABLE.setName("population");
        POPULATION_TABLE.setId("_population");
        POPULATION_TABLE.getColumns()
                .addAll(List.of(YEAR_COLUMN_IN_POPULATION, STATE_ID_COLUMN_IN_POPULATION,
                        GENDER_ID_COLUMN_IN_POPULATION, AGE_COLUMN_IN_POPULATION, NUMBER_COLUMN_IN_POPULATION));
        
        // year,ordinal
        // INTEGER,INTEGER,
        Column YEAR_IN_YEAR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        YEAR_IN_YEAR.setName("year");
        YEAR_IN_YEAR.setId("_col_year_year");
        YEAR_IN_YEAR.setType(ColumnType.INTEGER);

        Column ORDINAL_IN_YEAR = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ORDINAL_IN_YEAR.setName("ordinal");
        ORDINAL_IN_YEAR.setId("_col_year_ordinal");
        ORDINAL_IN_YEAR.setType(ColumnType.VARCHAR);

        PhysicalTable YEAR_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        YEAR_TABLE.setName("ganztags_art");
        YEAR_TABLE.setId("_tab_year");
        YEAR_TABLE.getColumns().addAll(List.of(YEAR_IN_YEAR, ORDINAL_IN_YEAR));

        // id,traeger_name,traeger_art_id
        // INTEGER,VARCHAR,INTEGER
        Column CONTINENT_ID_COLUMN_IN_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        CONTINENT_ID_COLUMN_IN_COUNTRY.setName("continent_id");
        CONTINENT_ID_COLUMN_IN_COUNTRY.setId("_col_country_continent_id");
        CONTINENT_ID_COLUMN_IN_COUNTRY.setType(ColumnType.INTEGER);

        Column ID_COLUMN_IN_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_COUNTRY.setName("id");
        ID_COLUMN_IN_COUNTRY.setId("_col_country_id");
        ID_COLUMN_IN_COUNTRY.setType(ColumnType.INTEGER);

        Column NAME_COLUMN_IN_COUNTRY = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        NAME_COLUMN_IN_COUNTRY.setName("name");
        NAME_COLUMN_IN_COUNTRY.setId("_col_country_name");
        NAME_COLUMN_IN_COUNTRY.setType(ColumnType.VARCHAR);

        PhysicalTable COUNTRY_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        COUNTRY_TABLE.setName("country");
        COUNTRY_TABLE.setId("_tab_country");
        COUNTRY_TABLE.getColumns().addAll(List.of(CONTINENT_ID_COLUMN_IN_COUNTRY, ID_COLUMN_IN_COUNTRY,
                NAME_COLUMN_IN_COUNTRY));

        // id,traeger_art,traeger_kat_id
        // INTEGER,VARCHAR,VARCHAR
        Column ID_COLUMN_IN_CONTENT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_CONTENT.setName("id");
        ID_COLUMN_IN_CONTENT.setId("_col_continent_id");
        ID_COLUMN_IN_CONTENT.setType(ColumnType.INTEGER);

        Column NAME_COLUMN_IN_CONTENT = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        NAME_COLUMN_IN_CONTENT.setName("name");
        NAME_COLUMN_IN_CONTENT.setId("_col_continent_name");
        NAME_COLUMN_IN_CONTENT.setType(ColumnType.VARCHAR);

        PhysicalTable CONTINENT_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        CONTINENT_TABLE.setName("continent");
        CONTINENT_TABLE.setId("_tab_continent");
        CONTINENT_TABLE.getColumns().addAll(List.of(ID_COLUMN_IN_CONTENT, NAME_COLUMN_IN_CONTENT));

        // id,traeger_kategorie
        // INTEGER,VARCHAR
        Column ID_COLUMN_IN_STATE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        ID_COLUMN_IN_STATE.setName("id");
        ID_COLUMN_IN_STATE.setId("_col_state_id");
        ID_COLUMN_IN_STATE.setType(ColumnType.INTEGER);

        Column CONTRY_ID_COLUMN_IN_STATE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        CONTRY_ID_COLUMN_IN_STATE.setName("contry_id");
        CONTRY_ID_COLUMN_IN_STATE.setId("_col_state_contry_id");
        CONTRY_ID_COLUMN_IN_STATE.setType(ColumnType.INTEGER);

        Column NAME_COLUMN_IN_STATE = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        NAME_COLUMN_IN_STATE.setName("name");
        NAME_COLUMN_IN_STATE.setId("_col_state_name");
        NAME_COLUMN_IN_STATE.setType(ColumnType.VARCHAR);

        PhysicalTable STATE_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        STATE_TABLE.setName(STATE);
        STATE_TABLE.setId("_tab_state");
        STATE_TABLE.getColumns()
                .addAll(List.of(ID_COLUMN_IN_STATE, CONTRY_ID_COLUMN_IN_STATE, NAME_COLUMN_IN_STATE));

        Column GENDER_ID_COLUMN_IN_GENDER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        GENDER_ID_COLUMN_IN_GENDER.setName(GENDER_ID);
        GENDER_ID_COLUMN_IN_GENDER.setId("_col_gender_gender_id");
        GENDER_ID_COLUMN_IN_GENDER.setType(ColumnType.INTEGER);

        Column NAME_COLUMN_IN_GENDER = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        NAME_COLUMN_IN_GENDER.setName("name");
        NAME_COLUMN_IN_GENDER.setId("_col_gender_name");
        NAME_COLUMN_IN_GENDER.setType(ColumnType.VARCHAR);

        PhysicalTable GENDER_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        GENDER_TABLE.setName("gender");
        GENDER_TABLE.setId("_tab_gender");
        GENDER_TABLE.getColumns().addAll(List.of(GENDER_ID_COLUMN_IN_GENDER, NAME_COLUMN_IN_GENDER));

        // "id","schul_jahr","order"
        // ColumnType.INTEGER,ColumnType.VARCHAR,ColumnType.INTEGER
        Column AGE_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        AGE_IN_AGE_GROUPS.setName("age");
        AGE_IN_AGE_GROUPS.setId("_col_age_groups_age");
        AGE_IN_AGE_GROUPS.setType(ColumnType.INTEGER);

        Column H1_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H1_IN_AGE_GROUPS.setName("h1");
        H1_IN_AGE_GROUPS.setId("_col_col_age_groups_h1");
        H1_IN_AGE_GROUPS.setType(ColumnType.VARCHAR);

        Column H1_ORDER_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H1_ORDER_IN_AGE_GROUPS.setName("h1_order");
        H1_ORDER_IN_AGE_GROUPS.setId("_col_col_age_groups_h1_order");
        H1_ORDER_IN_AGE_GROUPS.setType(ColumnType.INTEGER);

        Column H2_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H2_IN_AGE_GROUPS.setName("h2");
        H2_IN_AGE_GROUPS.setId("_col_col_age_groups_h2");
        H2_IN_AGE_GROUPS.setType(ColumnType.VARCHAR);

        Column H2_ORDER_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H2_ORDER_IN_AGE_GROUPS.setName("h2_order");
        H2_ORDER_IN_AGE_GROUPS.setId("_col_col_age_groups_h2_order");
        H2_ORDER_IN_AGE_GROUPS.setType(ColumnType.INTEGER);

        Column H9_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H9_IN_AGE_GROUPS.setName("h9");
        H9_IN_AGE_GROUPS.setId("_col_col_age_groups_h9");
        H9_IN_AGE_GROUPS.setType(ColumnType.VARCHAR);

        Column H9_ORDER_IN_AGE_GROUPS = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        H9_ORDER_IN_AGE_GROUPS.setName("h9_order");
        H9_ORDER_IN_AGE_GROUPS.setId("_col_col_age_groups_h9_order");
        H9_ORDER_IN_AGE_GROUPS.setType(ColumnType.INTEGER);

        PhysicalTable AGE_GROUPS_TABLE = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        AGE_GROUPS_TABLE.setName("agegroups");
        AGE_GROUPS_TABLE.setId("_tab_agegroups");
        AGE_GROUPS_TABLE.getColumns()
                .addAll(List.of(AGE_IN_AGE_GROUPS, H1_IN_AGE_GROUPS, H1_ORDER_IN_AGE_GROUPS, H2_IN_AGE_GROUPS, H2_ORDER_IN_AGE_GROUPS, H9_IN_AGE_GROUPS, H9_ORDER_IN_AGE_GROUPS));

        databaseSchema.getTables()
                .addAll(List.of(POPULATION_TABLE, YEAR_TABLE, COUNTRY_TABLE, CONTINENT_TABLE, STATE_TABLE, GENDER_TABLE, AGE_GROUPS_TABLE));

        TableQuery TABLE_FACT_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE_FACT_QUERY.setId("_query_population");
        TABLE_FACT_QUERY.setTable(POPULATION_TABLE);

        TableQuery TABLE1_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE1_QUERY.setId("_query_year");
        TABLE1_QUERY.setTable(YEAR_TABLE);

        TableQuery TABLE22_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE22_QUERY.setId("_query_country");
        TABLE22_QUERY.setTable(COUNTRY_TABLE);

        TableQuery TABLE23_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE23_QUERY.setId("_query_continent");
        TABLE23_QUERY.setTable(CONTINENT_TABLE);

        TableQuery TABLE21_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE21_QUERY.setId("_query_state");
        TABLE21_QUERY.setTable(STATE_TABLE);

        TableQuery TABLE3_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE3_QUERY.setId("_query_gender");
        TABLE3_QUERY.setTable(GENDER_TABLE);

        TableQuery TABLE4_QUERY = RolapMappingFactory.eINSTANCE.createTableQuery();
        TABLE4_QUERY.setId("_query_age_groups");
        TABLE4_QUERY.setTable(AGE_GROUPS_TABLE);

        JoinedQueryElement JOIN21L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN21L.setKey(CONTINENT_ID_COLUMN_IN_COUNTRY);
        JOIN21L.setQuery(TABLE22_QUERY);

        JoinedQueryElement JOIN21R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN21R.setKey(ID_COLUMN_IN_CONTENT);
        JOIN21R.setQuery(TABLE23_QUERY);

        JoinQuery JOIN21 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN21.setId("_join_JOIN21");
        JOIN21.setLeft(JOIN21L);
        JOIN21.setRight(JOIN21R);

        JoinedQueryElement JOIN1L = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN1L.setKey(CONTRY_ID_COLUMN_IN_STATE);
        JOIN1L.setQuery(TABLE21_QUERY);

        JoinedQueryElement JOIN1R = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        JOIN1R.setKey(ID_COLUMN_IN_COUNTRY);
        JOIN1R.setQuery(JOIN21);

        JoinQuery JOIN1 = RolapMappingFactory.eINSTANCE.createJoinQuery();
        JOIN21.setId("_join_JOIN21");
        JOIN21.setLeft(JOIN1L);
        JOIN21.setRight(JOIN1R);

        Level LEVEL1 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL1.setName("Year");
        LEVEL1.setId("_level_year");
        LEVEL1.setColumn(YEAR_IN_YEAR);
        LEVEL1.setOrdinalColumn(ORDINAL_IN_YEAR);
        LEVEL1.setDescription("Year");

        Level LEVEL21 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL21.setName("Continent");
        LEVEL21.setId("_level_continent");
        LEVEL21.setColumn(ID_COLUMN_IN_CONTENT);
        LEVEL21.setNameColumn(NAME_COLUMN_IN_CONTENT);
        LEVEL21.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL21.setDescription("Continent");

        Level LEVEL22 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL22.setName("Country");
        LEVEL22.setId("_level_country");
        LEVEL22.setColumn(ID_COLUMN_IN_COUNTRY);
        LEVEL22.setNameColumn(NAME_COLUMN_IN_COUNTRY);
        LEVEL22.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL22.setDescription("Country");

        Level LEVEL23 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL23.setName("State");
        LEVEL23.setId("_level_state");
        LEVEL23.setColumn(ID_COLUMN_IN_STATE);
        LEVEL23.setNameColumn(NAME_COLUMN_IN_STATE);
        LEVEL23.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL23.setDescription("State");

        Level LEVEL3 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL3.setName(GENDER);
        LEVEL3.setId("_level_gender");
        LEVEL3.setColumn(GENDER_ID_COLUMN_IN_GENDER);
        LEVEL3.setNameColumn(ID_COLUMN_IN_COUNTRY);
        LEVEL3.setColumnType(ColumnInternalDataType.INTEGER);
        LEVEL3.setDescription(GENDER);

        Level LEVEL41 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL41.setName("Age");
        LEVEL41.setId("_level_age");
        LEVEL41.setColumn(AGE_IN_AGE_GROUPS);
        LEVEL41.setDescription("Age");

        Level LEVEL42 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL42.setName("AgeGroupH1");
        LEVEL42.setId("_level_age_group_h1");
        LEVEL42.setColumn(H1_IN_AGE_GROUPS);
        LEVEL42.setOrdinalColumn(H1_ORDER_IN_AGE_GROUPS);
        LEVEL42.setDescription("Age Group H1");

        Level LEVEL43 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL43.setName("AgeGroupH2");
        LEVEL43.setId("_level_age_group_h2");
        LEVEL43.setColumn(H2_IN_AGE_GROUPS);
        LEVEL43.setOrdinalColumn(H2_ORDER_IN_AGE_GROUPS);
        LEVEL43.setDescription("Age Group H2");

        Level LEVEL44 = RolapMappingFactory.eINSTANCE.createLevel();
        LEVEL44.setName("AgeGroupH9");
        LEVEL44.setId("_level_age_group_h9");
        LEVEL44.setColumn(H9_IN_AGE_GROUPS);
        LEVEL44.setOrdinalColumn(H9_ORDER_IN_AGE_GROUPS);
        LEVEL44.setDescription("Age Group H9");

        ExplicitHierarchy HIERARCHY1 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY1.setId("_hierarchy_year");
        HIERARCHY1.setHasAll(false);
        HIERARCHY1.setName("Year");
        HIERARCHY1.setPrimaryKey(YEAR_IN_YEAR);
        HIERARCHY1.setDescription("Year");
        HIERARCHY1.setQuery(TABLE1_QUERY);
        HIERARCHY1.getLevels().addAll(List.of(LEVEL1));

        ExplicitHierarchy HIERARCHY2 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY2.setId("_hierarchy_geographical");
        HIERARCHY2.setHasAll(true);
        HIERARCHY2.setName(GEOGRAPHICAL);
        HIERARCHY2.setPrimaryKey(ID_COLUMN_IN_STATE);
        HIERARCHY2.setDescription(GEOGRAPHICAL);
        HIERARCHY2.setQuery(JOIN1);
        HIERARCHY2.getLevels().addAll(List.of(LEVEL21, LEVEL22, LEVEL23));

        ExplicitHierarchy HIERARCHY3 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY3.setId("_hierarchy_gender");
        HIERARCHY3.setHasAll(true);
        HIERARCHY3.setName("Gender (m/f/d)");
        HIERARCHY3.setPrimaryKey(GENDER_ID_COLUMN_IN_GENDER);
        HIERARCHY3.setDescription(GENDER);
        HIERARCHY3.setQuery(TABLE3_QUERY);
        HIERARCHY3.getLevels().addAll(List.of(LEVEL3));

        ExplicitHierarchy HIERARCHY41 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY41.setId("_hierarchy_age");
        HIERARCHY41.setHasAll(true);
        HIERARCHY41.setName("Age (single vintages)");
        HIERARCHY41.setPrimaryKey(AGE_IN_AGE_GROUPS);
        HIERARCHY41.setDescription("Age (single vintages)");
        HIERARCHY41.setQuery(TABLE4_QUERY);
        HIERARCHY41.getLevels().addAll(List.of(LEVEL41));

        ExplicitHierarchy HIERARCHY42 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY42.setId("_hierarchy_age_group_standard");
        HIERARCHY42.setHasAll(true);
        HIERARCHY42.setName("Age group (Standard)");
        HIERARCHY42.setPrimaryKey(AGE_IN_AGE_GROUPS);
        HIERARCHY42.setDescription("Age group (Standard)");
        HIERARCHY42.setQuery(TABLE4_QUERY);
        HIERARCHY42.getLevels().addAll(List.of(LEVEL42, LEVEL41));

        ExplicitHierarchy HIERARCHY43 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY43.setId("_hierarchy_age_group_children");
        HIERARCHY43.setHasAll(true);
        HIERARCHY43.setName("Age group (children)");
        HIERARCHY43.setPrimaryKey(AGE_IN_AGE_GROUPS);
        HIERARCHY42.setDescription("Age group (children)");
        HIERARCHY43.setQuery(TABLE4_QUERY);
        HIERARCHY43.getLevels().addAll(List.of(LEVEL43, LEVEL41));

        ExplicitHierarchy HIERARCHY44 = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        HIERARCHY44.setId("_hierarchy_age_group_10_year");
        HIERARCHY44.setHasAll(true);
        HIERARCHY44.setName("Age group (10-year groups)");
        HIERARCHY44.setPrimaryKey(AGE_IN_AGE_GROUPS);
        HIERARCHY44.setQuery(TABLE4_QUERY);
        HIERARCHY44.getLevels().addAll(List.of(LEVEL44, LEVEL41));

        StandardDimension DIMENSION1 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION1.setName("Year");
        DIMENSION1.setId("_dimension_year");
        DIMENSION1.getHierarchies().addAll(List.of(HIERARCHY1));

        StandardDimension DIMENSION2 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION2.setName(GEOGRAPHICAL);
        DIMENSION2.setId("_dimension_geographical");
        DIMENSION2.getHierarchies().addAll(List.of(HIERARCHY2));

        StandardDimension DIMENSION3 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION3.setName(GENDER);
        DIMENSION3.setId("_dimension_gender");
        DIMENSION3.getHierarchies().addAll(List.of(HIERARCHY3));

        StandardDimension DIMENSION4 = RolapMappingFactory.eINSTANCE.createStandardDimension();
        DIMENSION4.setName("Age");
        DIMENSION4.setId("_dimension_age");
        DIMENSION4.getHierarchies().addAll(List.of(HIERARCHY41, HIERARCHY42, HIERARCHY43, HIERARCHY44));

        SumMeasure measure1 = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure1.setName("Anzahl");
        measure1.setId("_measure_Anzahl");
        measure1.setColumn(NUMBER_COLUMN_IN_POPULATION);

        MeasureGroup CUBE1_MEASURE_GROUP = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        CUBE1_MEASURE_GROUP.getMeasures().addAll(List.of(measure1));

        DimensionConnector YEAR_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        YEAR_DC.setOverrideDimensionName("Year");
        YEAR_DC.setDimension(DIMENSION1);
        YEAR_DC.setForeignKey(YEAR_COLUMN_IN_POPULATION);

        DimensionConnector GEOGRAPHICAL_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        GEOGRAPHICAL_DC.setOverrideDimensionName(GEOGRAPHICAL);
        GEOGRAPHICAL_DC.setDimension(DIMENSION2);
        GEOGRAPHICAL_DC.setForeignKey(STATE_ID_COLUMN_IN_POPULATION);

        DimensionConnector GENDER_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        GENDER_DC.setOverrideDimensionName(GENDER);
        GENDER_DC.setDimension(DIMENSION3);
        GENDER_DC.setForeignKey(GENDER_ID_COLUMN_IN_POPULATION);

        DimensionConnector AGE_DC = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        AGE_DC.setOverrideDimensionName("Age");
        AGE_DC.setDimension(DIMENSION4);
        AGE_DC.setForeignKey(AGE_COLUMN_IN_POPULATION);

        PhysicalCube CUBE = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        CUBE.setId("_cube_population");
        CUBE.setName(POPULATION);
        CUBE.setDescription("Population Cube");
        CUBE.setQuery(TABLE_FACT_QUERY);
        CUBE.getDimensionConnectors().addAll(List.of(YEAR_DC, GEOGRAPHICAL_DC, GENDER_DC, AGE_DC));
        CUBE.getMeasureGroups().addAll(List.of(CUBE1_MEASURE_GROUP));

        Catalog CATALOG = RolapMappingFactory.eINSTANCE.createCatalog();
        CATALOG.setName(POPULATION);
        CATALOG.setId("_catalog_Population");
        CATALOG.getCubes().addAll(List.of(CUBE));
        CATALOG.getDbschemas().add(databaseSchema);

        return CATALOG;
    }

}
