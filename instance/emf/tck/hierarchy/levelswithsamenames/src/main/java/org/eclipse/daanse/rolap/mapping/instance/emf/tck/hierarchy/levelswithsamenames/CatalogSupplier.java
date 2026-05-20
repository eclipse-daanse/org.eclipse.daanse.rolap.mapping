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
package org.eclipse.daanse.rolap.mapping.instance.emf.tck.hierarchy.levelswithsamenames;


import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.osgi.service.component.annotations.Component;

@Component(service = { CatalogMappingSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier {

    private ExplicitHierarchy hierarchy;
    private ExplicitHierarchy hierarchyObject;
    private ExplicitHierarchy hierarchyYear;
    private StandardDimension dimension;
    private StandardDimension dimensionYear;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private Level levelObject;
    private Level levelId;
    private Level levelYear;
    private TableSource queryHier;
    private TableSource queryHier1;
    private Level levelTown;
    private TableSource queryFact;
    private TableSource queryFact1;

    private static String description  ="""
            Hierarchy with levels with same names of values. We have issue with show ObjectHierarchy members.
            ObjectHierarchy members absent for Objects level.
            Issue reproduced only for YearHierarchy hasAll false
            Issue reproduced only for dimension Year as standart dimension. Issue can not reproduced for dimension Year as TimeDimension
    """;
    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column yearColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        yearColumn.setName("YEAR");
        yearColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column idColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        idColumn.setName("ID");
        idColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(yearColumn, idColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        Column idColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        idColumnTown.setName("ID");
        idColumnTown.setType(SqlSimpleTypes.Sql99.integerType());

        Column townIdColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        townIdColumnTown.setName("TOWNID");
        townIdColumnTown.setType(SqlSimpleTypes.Sql99.integerType());

        Column nameColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumnTown.setName("NAME");
        nameColumnTown.setType(SqlSimpleTypes.Sql99.varcharType());

        Column objectIdColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        objectIdColumnTown.setName("OBJECTID");
        objectIdColumnTown.setType(SqlSimpleTypes.Sql99.integerType());

        Column objectColumnTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        objectColumnTown.setName("OBJECT");
        objectColumnTown.setType(SqlSimpleTypes.Sql99.varcharType());

        Table tableTown = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        tableTown.setName("Town");
        tableTown.getFeature().addAll(List.of(idColumnTown, townIdColumnTown, nameColumnTown, objectIdColumnTown, objectColumnTown));
        databaseSchema.getOwnedElement().add(tableTown);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(table);

        queryFact1 = SourceFactory.eINSTANCE.createTableSource();
        queryFact1.setTable(table);

        queryHier = SourceFactory.eINSTANCE.createTableSource();
        queryHier.setTable(tableTown);

        queryHier1 = SourceFactory.eINSTANCE.createTableSource();
        queryHier1.setTable(tableTown);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("theMeasure");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        levelTown = LevelFactory.eINSTANCE.createLevel();
        levelTown.setName("Town");
        levelTown.setColumn(townIdColumnTown);
        levelTown.setNameColumn(nameColumnTown);

        levelObject = LevelFactory.eINSTANCE.createLevel();
        levelObject.setName("Objects");
        levelObject.setColumn(objectIdColumnTown);
        levelObject.setNameColumn(objectColumnTown);

        levelObject = LevelFactory.eINSTANCE.createLevel();
        levelObject.setName("Objects");
        levelObject.setColumn(objectIdColumnTown);
        levelObject.setNameColumn(objectColumnTown);

        levelId = LevelFactory.eINSTANCE.createLevel();
        levelId.setName("Id");
        levelId.setColumn(idColumnTown);

        levelYear = LevelFactory.eINSTANCE.createLevel();
        levelYear.setName("Year");
        levelYear.setColumn(yearColumn);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setName("TownHierarchy");
        hierarchy.setPrimaryKey(idColumnTown);
        hierarchy.setSource(queryHier);
        hierarchy.getLevels().add(levelTown);

        hierarchyObject = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyObject.setName("ObjectsHierarchy");
        hierarchyObject.setPrimaryKey(idColumnTown);
        hierarchyObject.setSource(queryHier1);
        hierarchyObject.getLevels().add(levelObject);
        hierarchyObject.getLevels().add(levelId);

        hierarchyYear = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchyYear.setName("YearHierarchy");
        hierarchyYear.setPrimaryKey(yearColumn);
        hierarchyYear.setSource(queryFact1);
        hierarchyYear.setHasAll(false);
        hierarchyYear.getLevels().add(levelYear);

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Town");
        dimension.getHierarchies().addAll(List.of(hierarchy, hierarchyObject));

        dimensionYear = DimensionFactory.eINSTANCE.createStandardDimension();
        dimensionYear.setName("Year");
        dimensionYear.getHierarchies().addAll(List.of(hierarchyYear));

        DimensionConnector dimensionConnector1 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector1.setDimension(dimensionYear);
        dimensionConnector1.setForeignKey(yearColumn);

        DimensionConnector dimensionConnector2 = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector2.setDimension(dimension);
        dimensionConnector2.setForeignKey(idColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Cube with levels with same names of values");
        cube.setSource(queryFact);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector1);
        cube.getDimensionConnectors().add(dimensionConnector2);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.getDbschemas().add(databaseSchema);
        catalog.setName("Daanse Tck - Hierarchy with levels with same names of values");
        catalog.setDescription(description);
        catalog.getCubes().add(cube);

            return catalog;
    }

}
