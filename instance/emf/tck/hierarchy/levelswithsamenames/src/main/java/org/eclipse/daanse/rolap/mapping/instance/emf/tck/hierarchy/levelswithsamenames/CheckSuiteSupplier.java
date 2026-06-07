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

import org.eclipse.daanse.olap.check.model.check.AggregatorType;
import org.eclipse.daanse.olap.check.model.check.CatalogCheck;
import org.eclipse.daanse.olap.check.model.check.CellValueCheck;
import org.eclipse.daanse.olap.check.model.check.CubeCheck;
import org.eclipse.daanse.olap.check.model.check.DimensionCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttribute;
import org.eclipse.daanse.olap.check.model.check.HierarchyAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.HierarchyCheck;
import org.eclipse.daanse.olap.check.model.check.LevelCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureAttribute;
import org.eclipse.daanse.olap.check.model.check.MeasureAttributeCheck;
import org.eclipse.daanse.olap.check.model.check.MeasureCheck;
import org.eclipse.daanse.olap.check.model.check.OlapCheckFactory;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.model.check.OlapConnectionCheck;
import org.eclipse.daanse.olap.check.model.check.QueryCheck;
import org.eclipse.daanse.olap.check.model.check.QueryLanguage;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a check suite for the minimal cube mapping.
 * Checks that the catalog, cube, and measure exist and are accessible.
 */
@Component(service = OlapCheckSuiteSupplier.class)
public class CheckSuiteSupplier implements OlapCheckSuiteSupplier {

    private static final OlapCheckFactory factory = OlapCheckFactory.eINSTANCE;

    @Override
    public OlapCheckSuite get() {
        // Create measure check
        MeasureCheck theMeasureCheck = factory.createMeasureCheck();
        theMeasureCheck.setName("MeasureCheck-theMeasure");
        theMeasureCheck.setDescription("Check that measure 'theMeasure' exists");
        theMeasureCheck.setMeasureName("theMeasure");

        //TODO fix aggregates check executer
        MeasureAttributeCheck measureSumAttributeCheck1 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck1.setAttributeType(MeasureAttribute.AGGREGATOR);
        measureSumAttributeCheck1.setExpectedAggregator(AggregatorType.SUM);

        MeasureAttributeCheck measureSumAttributeCheck2 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck2.setAttributeType(MeasureAttribute.NAME);
        measureSumAttributeCheck2.setExpectedValue("theMeasure");

        MeasureAttributeCheck measureSumAttributeCheck3 = factory.createMeasureAttributeCheck();
        measureSumAttributeCheck3.setAttributeType(MeasureAttribute.UNIQUE_NAME);
        measureSumAttributeCheck3.setExpectedValue("[Measures].[theMeasure]");

        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck2);
        theMeasureCheck.getMeasureAttributeChecks().add(measureSumAttributeCheck3);

        // Create Level check
        LevelCheck levelTownCheck = factory.createLevelCheck();
        levelTownCheck.setName("levelCheck for Town");
        levelTownCheck.setLevelName("Town");

        LevelCheck levelObjectCheck = factory.createLevelCheck();
        levelObjectCheck.setName("levelCheck for LevelObject");
        levelObjectCheck.setLevelName("Objects");

        LevelCheck levelIdCheck = factory.createLevelCheck();
        levelIdCheck.setName("levelCheck for LevelId");
        levelIdCheck.setLevelName("Id");

        HierarchyAttributeCheck hierarchyTownHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyTownHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyTownHasAllAttributeCheck.setExpectedBoolean(true);

        // Create hierarchy check
        HierarchyCheck hierarchyTownCheck = factory.createHierarchyCheck();
        hierarchyTownCheck.setName("HierarchyCheck for TownHierarchy");
        hierarchyTownCheck.setHierarchyName("TownHierarchy");
        hierarchyTownCheck.getHierarchyAttributeChecks().add(hierarchyTownHasAllAttributeCheck);
        hierarchyTownCheck.getLevelChecks().add(levelTownCheck);

        HierarchyAttributeCheck hierarchyObjectsHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyObjectsHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyObjectsHasAllAttributeCheck.setExpectedBoolean(true);

        HierarchyCheck objectsHierarchyCheck = factory.createHierarchyCheck();
        objectsHierarchyCheck.setName("HierarchyCheck for ObjectsHierarchy");
        objectsHierarchyCheck.setHierarchyName("ObjectsHierarchy");
        objectsHierarchyCheck.getHierarchyAttributeChecks().add(hierarchyObjectsHasAllAttributeCheck);
        objectsHierarchyCheck.getLevelChecks().add(levelObjectCheck);
        objectsHierarchyCheck.getLevelChecks().add(levelIdCheck);

        HierarchyAttributeCheck hierarchyYearHasAllAttributeCheck = factory.createHierarchyAttributeCheck();
        hierarchyYearHasAllAttributeCheck.setAttributeType(HierarchyAttribute.HAS_ALL);
        hierarchyYearHasAllAttributeCheck.setExpectedBoolean(false);

        LevelCheck levelYearCheck = factory.createLevelCheck();
        levelYearCheck.setName("levelCheck for LevelYear");
        levelYearCheck.setLevelName("Year");

        HierarchyCheck hierarchyYearCheck = factory.createHierarchyCheck();
        hierarchyYearCheck.setName("HierarchyCheck for ObjectsHierarchy");
        hierarchyYearCheck.setHierarchyName("YearHierarchy");
        hierarchyYearCheck.getHierarchyAttributeChecks().add(hierarchyYearHasAllAttributeCheck);
        hierarchyYearCheck.getLevelChecks().add(levelYearCheck);

        // Create dimension check
        DimensionCheck dimensionTownCheck = factory.createDimensionCheck();
        dimensionTownCheck.setName("DimensionCheck for Town");
        dimensionTownCheck.setDimensionName("Town");
        dimensionTownCheck.getHierarchyChecks().add(hierarchyTownCheck);
        dimensionTownCheck.getHierarchyChecks().add(objectsHierarchyCheck);

        DimensionCheck dimensionYearCheck = factory.createDimensionCheck();
        dimensionYearCheck.setName("DimensionCheck for Town");
        dimensionYearCheck.setDimensionName("Year");
        dimensionYearCheck.getHierarchyChecks().add(hierarchyYearCheck);

        // Create cube check with measure check
        CubeCheck cubeCheck = factory.createCubeCheck();
        cubeCheck.setName("CubeCheck-Cube with levels with same names of values");
        cubeCheck.setDescription("Check that cube 'Cube with levels with same names of values");
        cubeCheck.setCubeName("Cube with levels with same names of values");
        cubeCheck.getMeasureChecks().add(theMeasureCheck);
        cubeCheck.getDimensionChecks().add(dimensionTownCheck);
        cubeCheck.getDimensionChecks().add(dimensionYearCheck);

        CellValueCheck queryCheck1CellValueCheck = factory.createCellValueCheck();
        queryCheck1CellValueCheck.setName("[Measures].[theMeasure]");
        queryCheck1CellValueCheck.setExpectedValue("836.0");
        queryCheck1CellValueCheck.setExpectedNumericValue(836.0);

        QueryCheck queryCheck1 = factory.createQueryCheck();
        queryCheck1.setName("Measure Query Check theMeasure");
        queryCheck1.setDescription("Verify MDX query returns Measure data for theMeasure");
        queryCheck1.setQuery("SELECT FROM [Cube with levels with same names of values] WHERE ([Measures].[theMeasure])");
        queryCheck1.setQueryLanguage(QueryLanguage.MDX);
        queryCheck1.setExpectedColumnCount(0);
        queryCheck1.getCellChecks().add(queryCheck1CellValueCheck);
        queryCheck1.setEnabled(true);

        CellValueCheck queryCheck2CellValueCheck1 = factory.createCellValueCheck();
        queryCheck2CellValueCheck1.setName("DRILLTHROUGH value1");
        queryCheck2CellValueCheck1.setExpectedValue("2025.0");
        queryCheck2CellValueCheck1.setExpectedNumericValue(2025.0);
        queryCheck2CellValueCheck1.getCoordinates().addAll(List.of(0, 0));

        CellValueCheck queryCheck2CellValueCheck2 = factory.createCellValueCheck();
        queryCheck2CellValueCheck2.setName("DRILLTHROUGH value2");
        queryCheck2CellValueCheck2.setExpectedValue("Berlin");
        queryCheck2CellValueCheck2.getCoordinates().addAll(List.of(0, 1));

        CellValueCheck queryCheck2CellValueCheck3 = factory.createCellValueCheck();
        queryCheck2CellValueCheck3.setName("DRILLTHROUGH value3");
        queryCheck2CellValueCheck3.setExpectedValue("1");
        queryCheck2CellValueCheck3.setCheckFormattedValue(true);
        queryCheck2CellValueCheck3.getCoordinates().addAll(List.of(0, 2));

        CellValueCheck queryCheck2CellValueCheck4 = factory.createCellValueCheck();
        queryCheck2CellValueCheck4.setName("DRILLTHROUGH value4");
        queryCheck2CellValueCheck4.setExpectedValue("11");
        queryCheck2CellValueCheck4.setCheckFormattedValue(true);
        queryCheck2CellValueCheck4.getCoordinates().addAll(List.of(0, 3));

        CellValueCheck queryCheck2CellValueCheck5 = factory.createCellValueCheck();
        queryCheck2CellValueCheck5.setName("DRILLTHROUGH value5");
        queryCheck2CellValueCheck5.setExpectedValue("square");
        queryCheck2CellValueCheck5.setCheckFormattedValue(true);
        queryCheck2CellValueCheck5.getCoordinates().addAll(List.of(0, 4));

        CellValueCheck queryCheck2CellValueCheck6 = factory.createCellValueCheck();
        queryCheck2CellValueCheck6.setName("DRILLTHROUGH value6");
        queryCheck2CellValueCheck6.setExpectedValue("11");
        queryCheck2CellValueCheck6.setCheckFormattedValue(true);
        queryCheck2CellValueCheck6.getCoordinates().addAll(List.of(0, 5));

        CellValueCheck queryCheck2CellValueCheck7 = factory.createCellValueCheck();
        queryCheck2CellValueCheck7.setName("DRILLTHROUGH value7");
        queryCheck2CellValueCheck7.setExpectedValue("1");
        queryCheck2CellValueCheck7.setCheckFormattedValue(true);
        queryCheck2CellValueCheck7.getCoordinates().addAll(List.of(0, 6));

        CellValueCheck queryCheck2CellValueCheck8 = factory.createCellValueCheck();
        queryCheck2CellValueCheck8.setName("DRILLTHROUGH value8");
        queryCheck2CellValueCheck8.setExpectedValue("2");
        queryCheck2CellValueCheck8.setCheckFormattedValue(true);
        queryCheck2CellValueCheck8.getCoordinates().addAll(List.of(0, 7));

        QueryCheck queryCheck2 = factory.createQueryCheck();
        queryCheck2.setName("DRILLTHROUGH Query Check");
        queryCheck2.setDescription("Verify MDX query returns DRILLTHROUGH");
        queryCheck2.setQuery("DRILLTHROUGH MAXROWS 1000 SELECT FROM [Cube with levels with same names of values] WHERE (([Measures].[theMeasure],[Town].[TownHierarchy].[Berlin]))");
        queryCheck2.setQueryLanguage(QueryLanguage.MDX);
        queryCheck2.setExpectedColumnCount(8);
        queryCheck2.setExpectedRowCount(4);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck1);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck2);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck3);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck4);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck5);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck6);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck7);
        queryCheck2.getCellChecks().add(queryCheck2CellValueCheck8);

        queryCheck2.setEnabled(true);


        // Create catalog check with cube check
        CatalogCheck catalogCheck = factory.createCatalogCheck();
        catalogCheck.setName("Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.setDescription("Check that catalog 'Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.setCatalogName("Daanse Tck - Hierarchy with levels with same names of values");
        catalogCheck.getCubeChecks().add(cubeCheck);
        catalogCheck.getQueryChecks().add(queryCheck1);
        catalogCheck.getQueryChecks().add(queryCheck2);

        // Create connection check (uses default connection)
        OlapConnectionCheck connectionCheck = factory.createOlapConnectionCheck();
        connectionCheck.setName("Connection Check Daanse Tck - Hierarchy with levels with same names of values");
        connectionCheck.setDescription("Connection check for Daanse Tck - Hierarchy with levels with same names of values");
        connectionCheck.getCatalogChecks().add(catalogCheck);

        // Create suite containing the connection check
        OlapCheckSuite suite = factory.createOlapCheckSuite();
        suite.setName("Daanse Tck - Hierarchy with levels with same names of values");
        suite.setDescription("Check suite for the Daanse Tck - Hierarchy with levels with same names of values");
        suite.getConnectionChecks().add(connectionCheck);

        return suite;
    }
}
