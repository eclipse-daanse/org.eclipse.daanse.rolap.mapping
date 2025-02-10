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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelexpressions;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpression;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-08_5_Cube_with_cub_dimension_with_level_expressions ";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            A basic OLAP schema with a level with expressions

            Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
            This example schema contains one cube named "Cube".

            A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
            In this case the database table representing the fact table is named "Fact" in the database, which is adressed in the name attribute within the <Table> tag.

            Each measurement of the cube is defined in a separate <Measure> element.
            The measurement in this example cube is named "Measure" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
            Level is defined in <Level> element.
            Property is defined in <Property> element inside <Level> element. Property we can see in cell tooltip in excel
                """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();

        Column keyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setId("Fact_KEY");
        keyColumn.setType("VARCHAR");

        Column key1Column = RolapMappingFactory.eINSTANCE.createColumn();
        key1Column.setName("KEY1");
        key1Column.setId("Fact_KEY1");
        key1Column.setType("VARCHAR");

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(keyColumn, key1Column, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        SqlStatement nameSql = RolapMappingFactory.eINSTANCE.createSqlStatement();
        nameSql.getDialects().addAll(List.of("generic", "h2"));
        nameSql.setSql("\"KEY\" || ' ' || \"KEY1\"");

        SQLExpression nameExpression = RolapMappingFactory.eINSTANCE.createSQLExpression();
        nameExpression.getSqls().add(nameSql);

        Level level1 = RolapMappingFactory.eINSTANCE.createLevel();
        level1.setName("Level1");
        level1.setId("Level1");
        level1.setColumn(keyColumn);
        level1.setNameExpression(nameExpression);

        SqlStatement keySql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        keySql1.getDialects().addAll(List.of("generic"));
        keySql1.setSql("KEY");
        SqlStatement keySql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        keySql2.getDialects().addAll(List.of("h2"));
        keySql2.setSql("\"KEY1\" || ' ' || \"KEY\"");

        SQLExpression keyExpression = RolapMappingFactory.eINSTANCE.createSQLExpression();
        keyExpression.getSqls().addAll(List.of(keySql1, keySql2));

        SqlStatement captionSql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        captionSql1.getDialects().addAll(List.of("generic"));
        captionSql1.setSql("KEY");
        SqlStatement captionSql2 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        captionSql2.getDialects().addAll(List.of("h2"));
        captionSql2.setSql("\"KEY1\" || '___' || \"KEY\"");

        SQLExpression captionExpression = RolapMappingFactory.eINSTANCE.createSQLExpression();
        captionExpression.getSqls().addAll(List.of(captionSql1, captionSql2));

        SqlStatement ordinalSql1 = RolapMappingFactory.eINSTANCE.createSqlStatement();
        ordinalSql1.getDialects().addAll(List.of("generic", "h2"));
        ordinalSql1.setSql("\"KEY\" || '___' || \"KEY1\"");

        SQLExpression ordinalExpression = RolapMappingFactory.eINSTANCE.createSQLExpression();
        ordinalExpression.getSqls().addAll(List.of(ordinalSql1));

        Level level2 = RolapMappingFactory.eINSTANCE.createLevel();
        level2.setName("Level2");
        level2.setId("Level2");
        level2.setKeyExpression(keyExpression);
        level2.setCaptionExpression(captionExpression);
        level2.setOrdinalExpression(ordinalExpression);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("HierarchyWithHasAll");
        hierarchy.setPrimaryKey(keyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(level1, level2));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Dimension");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Dimension");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_level_with_expressions");
        catalog.setDescription("Schema of a minimal cube with level with expressions");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.setDocumentation(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
