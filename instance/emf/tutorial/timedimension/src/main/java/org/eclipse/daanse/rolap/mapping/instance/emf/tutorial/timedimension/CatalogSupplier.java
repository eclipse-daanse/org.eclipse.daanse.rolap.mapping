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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.timedimension;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_15-08_Cube_with_share_dimension_with_TimeDimension";
    private static final String CUBE = "CubeTimeDimension";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
            Time dimensions based on year/month/week/day are coded differently in the Mondrian schema due to the MDX time related functions such as:

            ParallelPeriod([level[, index[, member]]])
            PeriodsToDate([level[, member]])
            WTD([member])
            MTD([member])
            QTD([member])
            YTD([member])
            LastPeriod(index[, member])

            Time dimensions have type="TimeDimension". The role of a level in a time dimension is indicated by the level's levelType attribute, whose allowable values are as follows:

            TimeYears   Level is a year
            TimeQuarters    Level is a quarter
            TimeMonths  Level is a month
            TimeWeeks   Level is a week
            TimeDays    Level represents days
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column dateKeyColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dateKeyColumn.setName("DATE_KEY");
        dateKeyColumn.setId("Fact_DATE_KEY");
        dateKeyColumn.setType(ColumnType.TIMESTAMP);

        Column valueColumn = RolapMappingFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType(ColumnType.INTEGER);

        Column yearIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        yearIdColumn.setName("YEAR_ID");
        yearIdColumn.setId("Fact_YEAR_ID");
        yearIdColumn.setType(ColumnType.INTEGER);

        Column qtrIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        qtrIdColumn.setName("QTR_ID");
        qtrIdColumn.setId("Fact_QTR_ID");
        qtrIdColumn.setType(ColumnType.VARCHAR);

        Column qtrNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        qtrNameColumn.setName("QTR_NAME");
        qtrNameColumn.setId("Fact_QTR_NAME");
        qtrNameColumn.setType(ColumnType.VARCHAR);

        Column monthIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        monthIdColumn.setName("MONTH_ID");
        monthIdColumn.setId("Fact_MONTH_ID");
        monthIdColumn.setType(ColumnType.VARCHAR);

        Column monthNameColumn = RolapMappingFactory.eINSTANCE.createColumn();
        monthNameColumn.setName("MONTH_NAME");
        monthNameColumn.setId("Fact_MONTH_NAME");
        monthNameColumn.setType(ColumnType.VARCHAR);

        Column weekInMonthColumn = RolapMappingFactory.eINSTANCE.createColumn();
        weekInMonthColumn.setName("WEEK_IN_MONTH");
        weekInMonthColumn.setId("Fact_WEEK_IN_MONTH");
        weekInMonthColumn.setType(ColumnType.INTEGER);

        Column dayInMonthColumn = RolapMappingFactory.eINSTANCE.createColumn();
        dayInMonthColumn.setName("DAY_IN_MONTH");
        dayInMonthColumn.setId("Fact_DAY_IN_MONTH");
        dayInMonthColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dateKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure-Sum");
        measure.setId("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelYears = RolapMappingFactory.eINSTANCE.createLevel();
        levelYears.setName("Years");
        levelYears.setId("Years");
        levelYears.setColumn(yearIdColumn);
        levelYears.setType(LevelDefinition.TIME_YEARS);
        levelYears.setUniqueMembers(true);
        levelYears.setHideMemberIf(HideMemberIf.NEVER);

        Level levelQuarters = RolapMappingFactory.eINSTANCE.createLevel();
        levelQuarters.setName("Quarters");
        levelQuarters.setId("Quarters");
        levelQuarters.setColumn(qtrNameColumn);
        levelQuarters.setOrdinalColumn(qtrIdColumn);
        levelQuarters.setType(LevelDefinition.TIME_QUARTERS);
        levelQuarters.setUniqueMembers(false);
        levelQuarters.setHideMemberIf(HideMemberIf.NEVER);

        Level levelMonths = RolapMappingFactory.eINSTANCE.createLevel();
        levelMonths.setName("Months");
        levelMonths.setId("Months");
        levelMonths.setColumn(monthNameColumn);
        levelMonths.setOrdinalColumn(monthIdColumn);
        levelMonths.setType(LevelDefinition.TIME_MONTHS);
        levelMonths.setUniqueMembers(false);
        levelMonths.setHideMemberIf(HideMemberIf.NEVER);

        Level levelWeek = RolapMappingFactory.eINSTANCE.createLevel();
        levelWeek.setName("Week");
        levelWeek.setId("Week");
        levelWeek.setColumn(weekInMonthColumn);
        levelWeek.setType(LevelDefinition.TIME_WEEKS);
        levelWeek.setUniqueMembers(false);

        Level levelDay = RolapMappingFactory.eINSTANCE.createLevel();
        levelDay.setName("Day");
        levelDay.setId("Day");
        levelDay.setColumn(dayInMonthColumn);
        levelDay.setType(LevelDefinition.TIME_DAYS);
        levelDay.setUniqueMembers(false);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setId("hierarchy");
        hierarchy.setAllMemberName("All Years");
        hierarchy.setPrimaryKey(dateKeyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelYears, levelQuarters, levelMonths, levelWeek, levelDay));

        TimeDimension dimension = RolapMappingFactory.eINSTANCE.createTimeDimension();
        dimension.setName("Time");
        dimension.setId("Time");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Time");
        dimensionConnector.setDimension(dimension);
        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_Time_Dimension");
        catalog.setDescription("Schema with cube with Time Dimension");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
