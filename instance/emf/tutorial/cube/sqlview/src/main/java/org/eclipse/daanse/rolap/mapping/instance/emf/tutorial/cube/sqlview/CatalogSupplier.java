/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.sqlview;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.relational.DialectSqlView;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlSelectSource;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.01.02", source = Source.EMF, group = "Cube")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private SqlSelectSource sqlSelectSource;
    private DialectSqlView sqlview;
    private Table table;

    public static final String CUBE_NAME = "MinimalCubeSqlView";
    public static final String MEASURE_NAME = "Measure-Sum";

    private static final String introBody = """
            The `SqlView` Table is a special Table that is used to reference columns of an SQL Query. The differenxe to the View is that the `SqlView` Table is not a view in the Database, but it holds the SQLStatement inside the mapping.
            """;

    private static final String sqlviewBody = """
            The DialectSqlView must contain a SqlStatement that is used to get the data from the Database. The SqlStatement is a simple SQL Query. The DialectSqlView can have multiple SqlStatements for different Dialects. The SqlStatement can alsobe used for multiple dialects. The DialectSqlView must also have the columns defined in the SQL Query.
            """;

    private static final String tableSourceBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableSource, as it directly references the sql view `sqlview`. The table source element is not visible to users accessing the cube through the XMLA API, such as Daanse Dashboard, Power BI, or Excel.
            """;


    @Override
    public Catalog get() {
        Schema databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column columnKeyFact = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKeyFact.setName("KEY");
        columnKeyFact.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnValueFact = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnValueFact.setName("VALUE");
        columnValueFact.setType(SqlSimpleTypes.Sql99.integerType());

        table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("FACT");
        table.getFeature().addAll(List.of(columnKeyFact, columnValueFact));
        databaseSchema.getOwnedElement().add(table);

        Column columnKey = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnKey.setName("Key");
        columnKey.setType(SqlSimpleTypes.Sql99.varcharType());

        Column columnValue = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        columnValue.setName("Value");
        columnValue.setType(SqlSimpleTypes.Sql99.integerType());

        sqlview = org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory.eINSTANCE.createDialectSqlView();
        sqlview.setName("sqlview");
        sqlview.getFeature().addAll(List.of(columnKey, columnValue));
        databaseSchema.getOwnedElement().add(sqlview);

        SqlStatement sqlStatement = SourceFactory.eINSTANCE.createSqlStatement();
        sqlStatement.setSql("select \"FACT\".\"KEY\" as \"Key\", \"FACT\".\"VALUE\" as \"Value\" from FACT");
        sqlStatement.getDialects().add("h2");
        sqlview.getDialectStatements().add(sqlStatement);

        sqlSelectSource = SourceFactory.eINSTANCE.createSqlSelectSource();
        sqlSelectSource.setAlias("sqlview");
        sqlSelectSource.setSql(sqlview);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName(MEASURE_NAME);
        measure.setColumn(columnValue);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setSource(sqlSelectSource);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Database SQL View Cube");
        catalog.setDescription("SQL view definitions and usage in cube");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Database SQL View Cube", introBody, 1, 0, 0, null, 0),
                        new DocSection("SqlView and SqlStatement", sqlviewBody, 1, 1, 0, sqlview, 3),
                        new DocSection("TableSource", tableSourceBody, 1, 2, 0, sqlSelectSource, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
