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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.table.multilevel.singletable;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, number = "2.3.2.3", source = Source.EMF) // NOSONAR
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String introBody = """
            In some cases, all data are stored in one table, the fact as well as multiple levels. This Tutorial shows how to handle this case.
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on only one tables. The Fact table contains a measures the name of the Town and the Country.
            """;

    private static final String levelTownBody = """
            The level of the Town used the `column` attribute to define the column that holds the name, wich is also the key Column.
            """;

    private static final String levelCountryBody = """
            The level  of the Country used the `column` attribute to define the column that holds the name, wich is also the key Column.
            """;

    private static final String hierarchyBody = """
            This Hierarchy contains both defined levels. The `primaryKey` attribute defines the column that contains the primary key of the hierarchy. The `query` attribute references to the query that will be used to retrieve the data for the hierarchy.

            The order of the Levels in the hierarchy is important, as it determines the drill-down path for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String cubeBody = """
            The cube contains only one Measure in a unnamed MeasureGroup and references to the Dimension.

            To connect the dimension to the cube, a DimensionConnector is used. The dimension has set the attribute `foreignKey` to define the column that contains the foreign key of the dimension in the fact table.
            """;

    private static final String queryFactBody = """
            The TableQuery for the Levels and the Measure.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_dbschema");

        Column columnKey = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnKey.setName("KEY");
        columnKey.setId("_col_fact_key");
        columnKey.setType(ColumnType.VARCHAR);

        Column columnValue = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnValue.setName("VALUE");
        columnValue.setId("_col_fact_value");
        columnValue.setType(ColumnType.INTEGER);

        Column columnCountry = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        columnCountry.setName("KEY");
        columnCountry.setId("_col_fact_country");
        columnCountry.setType(ColumnType.VARCHAR);

        PhysicalTable tableFact = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        tableFact.setName("Fact");
        tableFact.setId("_tab_fact");
        tableFact.getColumns().addAll(List.of(columnCountry, columnValue));
        databaseSchema.getTables().add(tableFact);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query");
        query.setTable(tableFact);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("theMeasure");
        measure.setId("_measure");
        measure.setColumn(columnValue);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelTown = RolapMappingFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setId("_level_town");
        levelTown.setColumn(columnKey);

        Level levelCountry = RolapMappingFactory.eINSTANCE.createLevel();
        levelCountry.setName("Country");
        levelCountry.setId("_level_country");
        levelCountry.setColumn(columnCountry);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setId("_hierarchy_town");
        hierarchy.setPrimaryKey(columnKey);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(levelCountry);
        hierarchy.getLevels().add(levelTown);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.setId("_dim_town");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector1 = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimension);
        dimensionConnector1.setForeignKey(columnCountry);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube Query linked Tables");
        cube.setId("_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Hierarchy - Query based on all in one Table");
        catalog.getCubes().add(cube);

        document(catalog, "Hierarchy - Query based on all in one Table", introBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query Fact", queryFactBody, 1, 3, 0, true, 2);

        document(levelTown, "Level", levelTownBody, 1, 4, 0, true, 0);
        document(levelCountry, "Level", levelCountryBody, 1, 5, 0, true, 0);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 6, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 7, 0, true, 0);

        document(cube, "Cube and DimensionConnector and Measure", cubeBody, 1, 7, 0, true, 2);

        return catalog;
    }

}
