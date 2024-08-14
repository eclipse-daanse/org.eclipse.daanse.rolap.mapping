/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.complex.population;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.COMPLEX, source = Source.POJO, number = "4")
@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class PopulationMappingSupplier implements CatalogMappingSupplier {

    private static final String INTEGER = "integer";
    private static final String STATE = "state";
    private static final String GENDER = "Gender";
    private static final String GENDER_ID = "gender_id";
    private static final String AGE_GROUP = "AgeGroup";
    private static final String GEOGRAPHICAL = "Geographical";

    private static final String POPULATION = "Population";
    private static final TableQueryMappingImpl TABLE_FACT =
        TableQueryMappingImpl.builder().withName("population").build();
    private static final TableQueryMappingImpl TABLE1 = TableQueryMappingImpl.builder().withName("year").build();
    private static final TableQueryMappingImpl TABLE22 = TableQueryMappingImpl.builder().withName("country").build();
    private static final TableQueryMappingImpl TABLE23 = TableQueryMappingImpl.builder().withName("continent").build();
    private static final TableQueryMappingImpl TABLE21 = TableQueryMappingImpl.builder().withName(STATE).build();
    private static final TableQueryMappingImpl TABLE3 = TableQueryMappingImpl.builder().withName("gender").build();
    private static final TableQueryMappingImpl TABLE4 = TableQueryMappingImpl.builder().withName("ageGroups").build();

    private static final String DOCUMENTATION_TEXT = "";

    private static final DocumentationMappingImpl documentation = new DocumentationMappingImpl(DOCUMENTATION_TEXT);

    private static final JoinQueryMappingImpl JOIN21 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("continent_id")
            .withQuery(TABLE22)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(TABLE23)
            .build())
        .build();

    private static final JoinQueryMappingImpl JOIN1 = JoinQueryMappingImpl.builder()
        .withLeft(JoinedQueryElementMappingImpl.builder()
            .withKey("contry_id")
            .withQuery(TABLE21)
            .build())
        .withRight(JoinedQueryElementMappingImpl.builder()
            .withKey("id")
            .withQuery(JOIN21)
            .build())
        .build();

    private static final LevelMappingImpl LEVEL1 = LevelMappingImpl
        .builder()
        .withName("Year")
        .withColumn("year")
        .withOrdinalColumn("ordinal")
        .withDescription("Year")
        .build();

    private static final LevelMappingImpl LEVEL21 = LevelMappingImpl
        .builder()
        .withName("Continent")
        .withColumn("id")
        .withNameColumn("name")
        .withType(INTEGER)
        .withTable("continent")
        .withDescription("Continent")
        .build();

    private static final LevelMappingImpl LEVEL22 = LevelMappingImpl
        .builder()
        .withName("Country")
        .withColumn("id")
        .withNameColumn("name")
        .withType(INTEGER)
        .withTable("country")
        .withDescription("Country")
        .build();

    private static final LevelMappingImpl LEVEL23 = LevelMappingImpl
        .builder()
        .withName("State")
        .withColumn("id")
        .withNameColumn("name")
        .withType(INTEGER)
        .withTable(STATE)
        .withDescription("State")
        .build();

    private static final LevelMappingImpl LEVEL3 = LevelMappingImpl
        .builder()
        .withName(GENDER)
        .withColumn(GENDER_ID)
        .withNameColumn("name")
        .withType(INTEGER)
        .withDescription(GENDER)
        .build();

    private static final LevelMappingImpl LEVEL41 = LevelMappingImpl
        .builder()
        .withName("Age")
        .withColumn("age")
        .withDescription("Age")
        .build();

    private static final LevelMappingImpl LEVEL42 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn("H1")
        .withOrdinalColumn("H1_Order")
        .withDescription("Age Group H1")
        .build();

    private static final LevelMappingImpl LEVEL43 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn("H2")
        .withOrdinalColumn("H2_Order")
        .withDescription("Age Group H2")
        .build();

    private static final LevelMappingImpl LEVEL44 = LevelMappingImpl
        .builder()
        .withName(AGE_GROUP)
        .withColumn("H9")
        .withOrdinalColumn("H9_Order")
        .withDescription("Age Group H9")
        .build();

    private static final HierarchyMappingImpl HIERARCHY1 = HierarchyMappingImpl
        .builder()
        .withHasAll(false)
        .withName("Year")
        .withPrimaryKey("year")
        .withDescription("Year")
        .withQuery(TABLE1)
        .withLevels(List.of(LEVEL1))
        .build();

    private static final HierarchyMappingImpl HIERARCHY2 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName(GEOGRAPHICAL)
        .withPrimaryKey("id")
        .withPrimaryKeyTable(STATE)
        .withDescription(GEOGRAPHICAL)
        .withQuery(JOIN1)
        .withLevels(List.of(LEVEL21, LEVEL22, LEVEL23))
        .build();

    private static final HierarchyMappingImpl HIERARCHY3 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Gender (m/f/d)")
        .withPrimaryKey(GENDER_ID)
        .withDescription(GENDER)
        .withQuery(TABLE3)
        .withLevels(List.of(LEVEL3))
        .build();

    private static final HierarchyMappingImpl HIERARCHY41 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age (single vintages)")
        .withPrimaryKey("age")
        .withDescription("Age (single vintages)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY42 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (Standard)")
        .withPrimaryKey("age")
        .withDescription("Age group (Standard)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL42, LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY43 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (children)")
        .withPrimaryKey("age")
        .withDescription("Age group (children)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL43, LEVEL41))
        .build();

    private static final HierarchyMappingImpl HIERARCHY44 = HierarchyMappingImpl
        .builder()
        .withHasAll(true)
        .withName("Age group (10-year groups)")
        .withPrimaryKey("age")
        .withDescription("Age group (10-year groups)")
        .withQuery(TABLE4)
        .withLevels(List.of(LEVEL44, LEVEL41))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION1 = StandardDimensionMappingImpl
        .builder()
        .withName("Year")
        .withHierarchies(List.of(HIERARCHY1))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION2 = StandardDimensionMappingImpl
        .builder()
        .withName(GEOGRAPHICAL)
        .withHierarchies(List.of(HIERARCHY2))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION3 = StandardDimensionMappingImpl
        .builder()
        .withName(GENDER)
        .withHierarchies(List.of(HIERARCHY3))
        .build();

    private static final StandardDimensionMappingImpl DIMENSION4 = StandardDimensionMappingImpl
        .builder()
        .withName("Age")
        .withHierarchies(List.of(HIERARCHY41, HIERARCHY42, HIERARCHY43, HIERARCHY44))
        .build();

    private static final PhysicalCubeMappingImpl CUBE = PhysicalCubeMappingImpl
        .builder()
        .withName(POPULATION)
        .withDescription("Population Cube")
        .withQuery(TABLE_FACT)
        .withDimensionConnectors(List.of(
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Year").withDimension(DIMENSION1).withForeignKey("year").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GEOGRAPHICAL).withDimension(DIMENSION2).withForeignKey("state_id").build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName(GENDER).withDimension(DIMENSION3).withForeignKey(GENDER_ID).build(),
            DimensionConnectorMappingImpl.builder().withOverrideDimensionName("Age").withDimension(DIMENSION4).withForeignKey("age").build()))
        .build();

    private static final SchemaMappingImpl
        SCHEMA = SchemaMappingImpl.builder()
        .withName(POPULATION)
        .withCubes(List.of(CUBE))
        .build();

    @Override
    public CatalogMapping get() {
        return CatalogMappingImpl.builder()
            .withName(POPULATION)
            .withDocumentation(documentation)
            .withSchemas(List.of(SCHEMA))
            .build();
    }

}
