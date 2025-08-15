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

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.9.1", source = Source.EMF, group = "Dimension") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "CubeTimeDimension";
    private static final String FACT = "Fact";

    private static final String catalogBody = """
            This tutorial discusses NamedSets.

            NsWithFolderDimension1    : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel. NamedSet have folder
            NsWithoutFolderDimension1 : NamedSet use only Dimension1 in formula. By this reason it connected to Dimension1 on excel.
            NSInCubeWithFolder        : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube on excel. NamedSet have folder
            NSInCubeWithoutFolder     : NamedSet use Dimension1 and Dimension2 in formula. By this reason it connected to Cube.
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the Fact table with 9 columns: DATE_KEY, YEAR_ID, QTR_ID, QTR_NAME, MONTH_ID, MONTH_NAME, WEEK_IN_MONTH, DAY_IN_MONTH and VALUE.
            The DATE_KEY column is used as the discriminator in the Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the Fact table to use in in the hierarchy and in the cube for the measures.
            """;

    private static final String levelYearsBody = """
            This Example uses Years level bases on the YEAR_ID column and has TIME_YEARS type.
            """;

    private static final String levelQuartersBody = """
            This Example uses Quarters level bases on the QTR_ID column and has TIME_QUARTERS type with name column QTR_NAME.
            """;

    private static final String levelMonthsBody = """
            This Example uses Months level bases on the MONTH_ID column and has TIME_MONTHS type with name column MONTH_NAME.
            """;

    private static final String levelWeekBody = """
            This Example uses Week level bases on the MONTH_ID column and has TIME_WEEKS type.
            """;

    private static final String levelDayBody = """
            This Example uses Week level bases on the MONTH_ID column and has TIME_DAYS type.
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

            TimeYears   Level is a year
            TimeQuarters    Level is a quarter
            TimeMonths  Level is a month
            TimeWeeks   Level is a week
            TimeDays    Level represents days
            """;

    private static final String schemaDocumentationTxt = """
            Time dimensions based on year/month/week/day are coded differently in the catalog due to the MDX time related functions such as:

            ParallelPeriod([level[, index[, member]]])
            PeriodsToDate([level[, member]])
            WTD([member])
            MTD([member])
            QTD([member])
            YTD([member])
            LastPeriod(index[, member])

            Cube have TimeDimension. The role of a level in a time dimension is indicated by the level's levelType attribute, whose allowable values are as follows:

            TimeYears   Level is a year
            TimeQuarters    Level is a quarter
            TimeMonths  Level is a month
            TimeWeeks   Level is a week
            TimeDays    Level represents days
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_timeDimension");

        Column dateKeyColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dateKeyColumn.setName("DATE_KEY");
        dateKeyColumn.setId("_column_fact_dateKey");
        dateKeyColumn.setType(ColumnType.TIMESTAMP);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("_column_fact_value");
        valueColumn.setType(ColumnType.INTEGER);

        Column yearIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        yearIdColumn.setName("YEAR_ID");
        yearIdColumn.setId("_column_fact_yearId");
        yearIdColumn.setType(ColumnType.INTEGER);

        Column qtrIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        qtrIdColumn.setName("QTR_ID");
        qtrIdColumn.setId("_column_fact_qtrId");
        qtrIdColumn.setType(ColumnType.VARCHAR);

        Column qtrNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        qtrNameColumn.setName("QTR_NAME");
        qtrNameColumn.setId("_column_fact_qtrName");
        qtrNameColumn.setType(ColumnType.VARCHAR);

        Column monthIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        monthIdColumn.setName("MONTH_ID");
        monthIdColumn.setId("_column_fact_monthId");
        monthIdColumn.setType(ColumnType.VARCHAR);

        Column monthNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        monthNameColumn.setName("MONTH_NAME");
        monthNameColumn.setId("_column_fact_monthName");
        monthNameColumn.setType(ColumnType.VARCHAR);

        Column weekInMonthColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        weekInMonthColumn.setName("WEEK_IN_MONTH");
        weekInMonthColumn.setId("_column_fact_weekInMonth");
        weekInMonthColumn.setType(ColumnType.INTEGER);

        Column dayInMonthColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        dayInMonthColumn.setName("DAY_IN_MONTH");
        dayInMonthColumn.setId("_column_fact_dayInMonth");
        dayInMonthColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_fact");
        table.getColumns().addAll(List.of(dateKeyColumn, valueColumn, yearIdColumn, qtrIdColumn, qtrNameColumn, monthIdColumn, monthNameColumn, weekInMonthColumn, dayInMonthColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setId("_measure_sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelYears = RolapMappingFactory.eINSTANCE.createLevel();
        levelYears.setName("Years");
        levelYears.setId("_level_years");
        levelYears.setColumn(yearIdColumn);
        levelYears.setType(LevelDefinition.TIME_YEARS);
        levelYears.setUniqueMembers(true);
        levelYears.setHideMemberIf(HideMemberIf.NEVER);

        Level levelQuarters = RolapMappingFactory.eINSTANCE.createLevel();
        levelQuarters.setName("Quarters");
        levelQuarters.setId("_level_quarters");
        levelQuarters.setColumn(qtrNameColumn);
        levelQuarters.setOrdinalColumn(qtrIdColumn);
        levelQuarters.setType(LevelDefinition.TIME_QUARTERS);
        levelQuarters.setUniqueMembers(false);
        levelQuarters.setHideMemberIf(HideMemberIf.NEVER);

        Level levelMonths = RolapMappingFactory.eINSTANCE.createLevel();
        levelMonths.setName("Months");
        levelMonths.setId("_level_months");
        levelMonths.setColumn(monthNameColumn);
        levelMonths.setOrdinalColumn(monthIdColumn);
        levelMonths.setType(LevelDefinition.TIME_MONTHS);
        levelMonths.setUniqueMembers(false);
        levelMonths.setHideMemberIf(HideMemberIf.NEVER);

        Level levelWeek = RolapMappingFactory.eINSTANCE.createLevel();
        levelWeek.setName("Week");
        levelWeek.setId("_level_week");
        levelWeek.setColumn(weekInMonthColumn);
        levelWeek.setType(LevelDefinition.TIME_WEEKS);
        levelWeek.setUniqueMembers(false);

        Level levelDay = RolapMappingFactory.eINSTANCE.createLevel();
        levelDay.setName("Day");
        levelDay.setId("_level_day");
        levelDay.setColumn(dayInMonthColumn);
        levelDay.setType(LevelDefinition.TIME_DAYS);
        levelDay.setUniqueMembers(false);

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setId("_hierarchy_time");
        hierarchy.setAllMemberName("All Years");
        hierarchy.setPrimaryKey(dateKeyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelYears, levelQuarters, levelMonths, levelWeek, levelDay));

        TimeDimension dimension = RolapMappingFactory.eINSTANCE.createTimeDimension();
        dimension.setName("Time");
        dimension.setId("_dimension_time");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_time");
        dimensionConnector.setOverrideDimensionName("Time");
        dimensionConnector.setDimension(dimension);
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube_timeDimension");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal Cube with Time_Dimension");
        catalog.setDescription("Schema with cube with Time Dimension");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "catalog with Cube with Time Dimensions", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);

        document(levelYears, "LevelYears", levelYearsBody, 1, 3, 0, true, 0);
        document(levelQuarters, "LevelQuarters", levelQuartersBody, 1, 4, 0, true, 0);
        document(levelMonths, "LevelMonths", levelMonthsBody, 1, 5, 0, true, 0);
        document(levelWeek, "LevelWeek", levelWeekBody, 1, 6, 0, true, 0);
        document(levelDay, "LevelDay", levelDayBody, 1, 7, 0, true, 0);

        document(hierarchy, "Hierarchy", hierarchyBody, 1, 8, 0, true, 0);
        document(dimension, "Dimension", dimensionBody, 1, 9, 0, true, 0);

        document(cube, "Cube with Time_Dimension", cubeBody, 1, 10, 0, true, 2);

        return catalog;
    }

}
