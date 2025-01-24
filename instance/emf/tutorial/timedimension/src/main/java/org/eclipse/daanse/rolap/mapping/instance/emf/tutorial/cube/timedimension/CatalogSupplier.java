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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.timedimension;

import java.util.List;

import org.eclipse.daanse.rdb.structure.emf.rdbstructure.Column;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.PhysicalTable;
import org.eclipse.daanse.rdb.structure.emf.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnDataType;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

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
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column dateKeyColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        dateKeyColumn.setName("DATE_KEY");
        dateKeyColumn.setId("Fact_DATE_KEY");
        dateKeyColumn.setType("TIMESTAMP");

        Column valueColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setId("Fact_VALUE");
        valueColumn.setType("INTEGER");

        Column yearIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        yearIdColumn.setName("YEAR_ID");
        yearIdColumn.setId("Fact_YEAR_ID");
        yearIdColumn.setType("INTEGER");

        Column qtrIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        qtrIdColumn.setName("QTR_ID");
        qtrIdColumn.setId("Fact_QTR_ID");
        qtrIdColumn.setType("VARCHAR");

        Column qtrNameColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        qtrNameColumn.setName("QTR_NAME");
        qtrNameColumn.setId("Fact_QTR_NAME");
        qtrNameColumn.setType("VARCHAR");

        Column monthIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        monthIdColumn.setName("MONTH_ID");
        monthIdColumn.setId("Fact_MONTH_ID");
        monthIdColumn.setType("VARCHAR");

        Column monthNameColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        monthNameColumn.setName("MONTH_NAME");
        monthNameColumn.setId("Fact_MONTH_NAME");
        monthNameColumn.setType("VARCHAR");

        Column weekInMonthColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        weekInMonthColumn.setName("WEEK_IN_MONTH");
        weekInMonthColumn.setId("Fact_WEEK_IN_MONTH");
        weekInMonthColumn.setType("INTEGER");

        Column dayInMonthColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        dayInMonthColumn.setName("DAY_IN_MONTH");
        dayInMonthColumn.setId("Fact_DAY_IN_MONTH");
        dayInMonthColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(dateKeyColumn, valueColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelYears = RolapMappingFactory.eINSTANCE.createLevel();
        levelYears.setName("Years");
        levelYears.setId("Years");
        levelYears.setColumn(yearIdColumn);
        levelYears.setType(LevelDefinition.TIME_YEARS);
        levelYears.setColumnType(ColumnDataType.INTEGER);
        levelYears.setUniqueMembers(true);
        levelYears.setHideMemberIf(HideMemberIf.NEVER);

        Level levelQuarters = RolapMappingFactory.eINSTANCE.createLevel();
        levelQuarters.setName("Quarters");
        levelQuarters.setId("Quarters");
        levelQuarters.setColumn(qtrNameColumn);
        levelQuarters.setOrdinalColumn(qtrIdColumn);
        levelQuarters.setType(LevelDefinition.TIME_QUARTERS);
        levelQuarters.setColumnType(ColumnDataType.STRING);
        levelQuarters.setUniqueMembers(false);
        levelQuarters.setHideMemberIf(HideMemberIf.NEVER);

        Level levelMonths = RolapMappingFactory.eINSTANCE.createLevel();
        levelMonths.setName("Months");
        levelMonths.setId("Months");
        levelMonths.setColumn(monthNameColumn);
        levelMonths.setOrdinalColumn(monthIdColumn);
        levelMonths.setType(LevelDefinition.TIME_MONTHS);
        levelMonths.setColumnType(ColumnDataType.STRING);
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
        levelMonths.setColumnType(ColumnDataType.NUMERIC);
        levelDay.setUniqueMembers(false);

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("All Years");
        hierarchy.setPrimaryKey(dateKeyColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelYears, levelQuarters, levelMonths, levelWeek, levelDay));

        TimeDimension dimension = RolapMappingFactory.eINSTANCE.createTimeDimension();
        dimension.setName("Time");
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

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cube_with_Time_Dimension");
        schema.setDescription("Schema with cube with Time Dimension");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getSchemas().add(schema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("catalog with schema with cube with Time Dimension");
        catalog.setDocumentation(documentation);
        return catalog;
    }

}
