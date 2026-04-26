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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimension.timedimension;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.09.01", source = Source.EMF, group = "Dimension") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private Level levelQuarters;
    private TimeDimension dimension;
    private Level levelMonths;
    private Schema databaseSchema;
    private Catalog catalog;
    private PhysicalCube cube;
    private Level levelDay;
    private TableSource query;
    private Level levelYears;
    private Level levelWeek;


    private static final String CUBE = "CubeTimeDimension";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial discusses NamedSets.

            - NsWithFolderDimension1    : NamedSet use only `Dimension1` in formula. By this reason it connected to `Dimension1` on excel. NamedSet have folder
            - NsWithoutFolderDimension1 : NamedSet use only `Dimension1` in formula. By this reason it connected to `Dimension1` on excel.
            - NSInCubeWithFolder        : NamedSet use `Dimension1` and `Dimension2` in formula. By this reason it connected to Cube on excel. NamedSet have folder
            - NSInCubeWithoutFolder     : NamedSet use `Dimension1` and `Dimension2` in formula. By this reason it connected to Cube.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `Fact` table with 9 columns: `DATE_KEY`, `YEAR_ID`, `QTR_ID`, `QTR_NAME`, `MONTH_ID`, `MONTH_NAME`, `WEEK_IN_MONTH`, `DAY_IN_MONTH` and `VALUE`.
            The `DATE_KEY` column is used as the discriminator in the Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableSource that selects all columns from the Fact table to use in the hierarchy and in the cube for the measures.
            """;

    private static final String levelYearsBody = """
            This Example uses Years level based on the YEAR_ID column and has TIME_YEARS type.
            """;

    private static final String levelQuartersBody = """
            This Example uses Quarters level based on the QTR_ID column and has TIME_QUARTERS type with name column QTR_NAME.
            """;

    private static final String levelMonthsBody = """
            This Example uses Months level based on the MONTH_ID column and has TIME_MONTHS type with name column MONTH_NAME.
            """;

    private static final String levelWeekBody = """
            This Example uses Week level based on the MONTH_ID column and has TIME_WEEKS type.
            """;

    private static final String levelDayBody = """
            This Example uses Week level based on the MONTH_ID column and has TIME_DAYS type.
            """;

    private static final String hierarchyBody = """
            The Hierarchy1 is defined with the hasAll property set to false and the one level2.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.
            """;

    private static final String cubeBody = """
            The cube with TimeDimension
            Time cube have TimeDimension. The role of a level in a time dimension is indicated by the level's levelType attribute, whose allowable values are as follows:

            - TimeYears   Level is a year
            - TimeQuarters    Level is a quarter
            - TimeMonths  Level is a month
            - TimeWeeks   Level is a week
            - TimeDays    Level represents days
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column dateKeyColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dateKeyColumn.setName("DATE_KEY");
        dateKeyColumn.setType(SqlSimpleTypes.Sql99.timestampType());

        Column valueColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column yearIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        yearIdColumn.setName("YEAR_ID");
        yearIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column qtrIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        qtrIdColumn.setName("QTR_ID");
        qtrIdColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column qtrNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        qtrNameColumn.setName("QTR_NAME");
        qtrNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column monthIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        monthIdColumn.setName("MONTH_ID");
        monthIdColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column monthNameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        monthNameColumn.setName("MONTH_NAME");
        monthNameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column weekInMonthColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        weekInMonthColumn.setName("WEEK_IN_MONTH");
        weekInMonthColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column dayInMonthColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        dayInMonthColumn.setName("DAY_IN_MONTH");
        dayInMonthColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature().addAll(List.of(dateKeyColumn, valueColumn, yearIdColumn, qtrIdColumn, qtrNameColumn, monthIdColumn, monthNameColumn, weekInMonthColumn, dayInMonthColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        OrderedColumn qtrIdOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        qtrIdOrderedColumn.setColumn(qtrIdColumn);

        OrderedColumn monthIdOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        monthIdOrderedColumn.setColumn(monthIdColumn);

        levelYears = LevelFactory.eINSTANCE.createLevel();
        levelYears.setName("Years");
        levelYears.setColumn(yearIdColumn);
        levelYears.setType(LevelDefinition.TIME_YEARS);
        levelYears.setUniqueMembers(true);
        levelYears.setHideMemberIf(HideMemberIf.NEVER);

        levelQuarters = LevelFactory.eINSTANCE.createLevel();
        levelQuarters.setName("Quarters");
        levelQuarters.setColumn(qtrNameColumn);
        levelQuarters.getOrdinalColumns().add(qtrIdOrderedColumn);
        levelQuarters.setType(LevelDefinition.TIME_QUARTERS);
        levelQuarters.setUniqueMembers(false);
        levelQuarters.setHideMemberIf(HideMemberIf.NEVER);

        levelMonths = LevelFactory.eINSTANCE.createLevel();
        levelMonths.setName("Months");
        levelMonths.setColumn(monthNameColumn);
        levelMonths.getOrdinalColumns().add(monthIdOrderedColumn);
        levelMonths.setType(LevelDefinition.TIME_MONTHS);
        levelMonths.setUniqueMembers(false);
        levelMonths.setHideMemberIf(HideMemberIf.NEVER);

        levelWeek = LevelFactory.eINSTANCE.createLevel();
        levelWeek.setName("Week");
        levelWeek.setColumn(weekInMonthColumn);
        levelWeek.setType(LevelDefinition.TIME_WEEKS);
        levelWeek.setUniqueMembers(false);

        levelDay = LevelFactory.eINSTANCE.createLevel();
        levelDay.setName("Day");
        levelDay.setColumn(dayInMonthColumn);
        levelDay.setType(LevelDefinition.TIME_DAYS);
        levelDay.setUniqueMembers(false);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("All Years");
        hierarchy.setPrimaryKey(dateKeyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelYears, levelQuarters, levelMonths, levelWeek, levelDay));

        dimension = DimensionFactory.eINSTANCE.createTimeDimension();
        dimension.setName("Time");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Time");
        dimensionConnector.setDimension(dimension);
        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Dimension Time Dimension");
        catalog.setDescription("Time dimension configuration");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);



            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Dimension Time Dimension", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("LevelYears", levelYearsBody, 1, 3, 0, levelYears, 0),
                        new DocSection("LevelQuarters", levelQuartersBody, 1, 4, 0, levelQuarters, 0),
                        new DocSection("LevelMonths", levelMonthsBody, 1, 5, 0, levelMonths, 0),
                        new DocSection("LevelWeek", levelWeekBody, 1, 6, 0, levelWeek, 0),
                        new DocSection("LevelDay", levelDayBody, 1, 7, 0, levelDay, 0),
                        new DocSection("Hierarchy", hierarchyBody, 1, 8, 0, hierarchy, 0),
                        new DocSection("Dimension", dimensionBody, 1, 9, 0, dimension, 0),
                        new DocSection("Cube with Time_Dimension", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
