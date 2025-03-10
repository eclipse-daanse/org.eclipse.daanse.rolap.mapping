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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithfunctionaldependencyoptimization;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-08_6_Cube_with_cub_dimension_with_functional_dependency_optimizations ";
    private static final String CUBE = "Cube";
    private static final String FACT = "AUTOMOTIVE_DIM";

    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with a level with with functional dependency optimizations

                In some circumstances, it may be possible to optimize performance by taking advantage of known
                functional dependencies in the data being processed. Such dependencies are typically the result
                of business rules associated with the systems producing the data, and often cannot be inferred
                just by looking at the data itself.
            Functional dependencies are declared to Mondrian using the dependsOnLevelValue attribute of the
            <Property> element and the uniqueKeyLevelName attribute of the <Hierarchy> element.
            The dependsOnLevelValue attribute of a member property is used to indicate that the value of the
            member property is functionally dependent on the value of the <Level> in which the member property
            is defined. In other words, for a given value of the level, the value of the property is invariant.
            The uniqueKeyLevelName attribute of a <Hierarchy> is used to indicate that the given level
            (if any) taken together with all higher levels in the hierarchy acts as a unique alternate key,
            ensuring that for any unique combination of those level values, there is exactly one combination
            of values for all levels below it.
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column auotoDimIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        auotoDimIdColumn.setName("AUTO_DIM_ID");
        auotoDimIdColumn.setId("AUTOMOTIVE_DIM_AUTO_DIM_ID");
        auotoDimIdColumn.setType(ColumnDataType.INTEGER);

        Column makeIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        makeIdColumn.setName("MAKE_ID");
        makeIdColumn.setId("AUTOMOTIVE_DIM_MAKE_ID");
        makeIdColumn.setType(ColumnDataType.INTEGER);

        Column makeColumn = RolapMappingFactory.eINSTANCE.createColumn();
        makeColumn.setName("MAKE");
        makeColumn.setId("AUTOMOTIVE_DIM_MAKE");
        makeColumn.setType(ColumnDataType.VARCHAR);
        makeColumn.setColumnSize(100);

        Column modelIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        modelIdColumn.setName("MODEL_ID");
        modelIdColumn.setId("AUTOMOTIVE_DIM_MODEL_ID");
        modelIdColumn.setType(ColumnDataType.INTEGER);

        Column modelColumn = RolapMappingFactory.eINSTANCE.createColumn();
        modelColumn.setName("MODEL");
        modelColumn.setId("AUTOMOTIVE_DIM_MODEL");
        modelColumn.setType(ColumnDataType.VARCHAR);
        modelColumn.setColumnSize(100);

        Column plantIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        plantIdColumn.setName("PLANT_ID");
        plantIdColumn.setId("AUTOMOTIVE_DIM_PLANT_ID");
        plantIdColumn.setType(ColumnDataType.INTEGER);

        Column plantColumn = RolapMappingFactory.eINSTANCE.createColumn();
        plantColumn.setName("PLANT");
        plantColumn.setId("AUTOMOTIVE_DIM_PLANT");
        plantColumn.setType(ColumnDataType.VARCHAR);
        plantColumn.setColumnSize(100);

        Column plantStateIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        plantStateIdColumn.setName("PLANT_STATE_ID");
        plantStateIdColumn.setId("AUTOMOTIVE_DIM_PLANT_STATE_ID");
        plantStateIdColumn.setType(ColumnDataType.INTEGER);

        Column plantCityIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        plantCityIdColumn.setName("PLANT_CITY_ID");
        plantCityIdColumn.setId("AUTOMOTIVE_DIM_PLANT_CITY_ID");
        plantCityIdColumn.setType(ColumnDataType.INTEGER);

        Column vehicleIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        vehicleIdColumn.setName("VEHICLE_ID");
        vehicleIdColumn.setId("AUTOMOTIVE_DIM_VEHICLE_ID");
        vehicleIdColumn.setType(ColumnDataType.INTEGER);

        Column colorIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        colorIdColumn.setName("COLOR_ID");
        colorIdColumn.setId("AUTOMOTIVE_DIM_COLOR_ID");
        colorIdColumn.setType(ColumnDataType.INTEGER);

        Column trimIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        trimIdColumn.setName("TRIM_ID");
        trimIdColumn.setId("AUTOMOTIVE_DIM_TRIM_ID");
        trimIdColumn.setType(ColumnDataType.INTEGER);

        Column licenseIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        licenseIdColumn.setName("LICENSE_ID");
        licenseIdColumn.setId("AUTOMOTIVE_DIM_LICENSE_ID");
        licenseIdColumn.setType(ColumnDataType.INTEGER);

        Column licenseColumn = RolapMappingFactory.eINSTANCE.createColumn();
        licenseColumn.setName("LICENSE");
        licenseColumn.setId("AUTOMOTIVE_DIM_LICENSE");
        licenseColumn.setType(ColumnDataType.VARCHAR);
        licenseColumn.setColumnSize(100);

        Column licenseStateIdColumn = RolapMappingFactory.eINSTANCE.createColumn();
        licenseStateIdColumn.setName("LICENSE_STATE_ID");
        licenseStateIdColumn.setId("AUTOMOTIVE_DIM_LICENSE_STATE_ID");
        licenseStateIdColumn.setType(ColumnDataType.INTEGER);

        Column priceColumn = RolapMappingFactory.eINSTANCE.createColumn();
        priceColumn.setName("PRICE");
        priceColumn.setId("AUTOMOTIVE_DIM_PRICE");
        priceColumn.setType(ColumnDataType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns()
                .addAll(List.of(auotoDimIdColumn, makeIdColumn, makeColumn, modelIdColumn, modelColumn, plantIdColumn,
                        plantColumn, plantStateIdColumn, plantCityIdColumn, vehicleIdColumn, colorIdColumn,
                        trimIdColumn, licenseIdColumn, licenseColumn, licenseStateIdColumn, priceColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setId("Measure");
        measure.setColumn(priceColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelMake = RolapMappingFactory.eINSTANCE.createLevel();
        levelMake.setName("Make");
        levelMake.setId("Make");
        levelMake.setColumn(makeIdColumn);
        levelMake.setNameColumn(makeColumn);
        levelMake.setColumnType(ColumnInternalDataType.NUMERIC);

        Level levelModel = RolapMappingFactory.eINSTANCE.createLevel();
        levelModel.setName("Model");
        levelModel.setId("Model");
        levelModel.setColumn(modelIdColumn);
        levelModel.setNameColumn(modelColumn);
        levelModel.setColumnType(ColumnInternalDataType.NUMERIC);

        MemberProperty stateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        stateProperty.setName("State");
        stateProperty.setId("State");
        stateProperty.setColumn(plantStateIdColumn);
        stateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        stateProperty.setDependsOnLevelValue(true);

        MemberProperty cytyProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        cytyProperty.setName("City");
        cytyProperty.setId("City");
        cytyProperty.setColumn(plantCityIdColumn);
        cytyProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        cytyProperty.setDependsOnLevelValue(true);

        Level levelPlant = RolapMappingFactory.eINSTANCE.createLevel();
        levelPlant.setName("ManufacturingPlant");
        levelPlant.setId("ManufacturingPlant");
        levelPlant.setColumn(plantIdColumn);
        levelPlant.setNameColumn(plantColumn);
        levelPlant.setColumnType(ColumnInternalDataType.NUMERIC);
        levelPlant.getMemberProperties().addAll(List.of(stateProperty, cytyProperty));

        MemberProperty colorProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        colorProperty.setName("Color");
        colorProperty.setId("Color");
        colorProperty.setColumn(colorIdColumn);
        colorProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        colorProperty.setDependsOnLevelValue(true);

        MemberProperty trimProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        trimProperty.setName("Trim");
        trimProperty.setId("Trim");
        trimProperty.setColumn(trimIdColumn);
        trimProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        trimProperty.setDependsOnLevelValue(true);

        Level levelVehicle = RolapMappingFactory.eINSTANCE.createLevel();
        levelVehicle.setName("Vehicle Identification Number");
        levelVehicle.setId("Vehicle_Identification_Number");
        levelVehicle.setColumn(vehicleIdColumn);
        levelVehicle.setColumnType(ColumnInternalDataType.NUMERIC);
        levelVehicle.getMemberProperties().addAll(List.of(colorProperty, trimProperty));

        MemberProperty licenseStateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        licenseStateProperty.setName("State");
        licenseStateProperty.setId("State");
        licenseStateProperty.setColumn(licenseStateIdColumn);
        licenseStateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        licenseStateProperty.setDependsOnLevelValue(true);

        Level levelLicense = RolapMappingFactory.eINSTANCE.createLevel();
        levelLicense.setName("LicensePlateNum");
        levelLicense.setId("LicensePlateNum");
        levelLicense.setColumn(vehicleIdColumn);
        levelLicense.setColumnType(ColumnInternalDataType.STRING);
        levelLicense.getMemberProperties().addAll(List.of(licenseStateProperty));

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setPrimaryKey(auotoDimIdColumn);
        hierarchy.setId("hierarchy");
        hierarchy.setUniqueKeyLevelName("Vehicle Identification Number");
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelMake, levelModel, levelPlant, levelVehicle, levelLicense));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Automotive");
        dimension.setId("Automotive");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Automotive");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_with_functional_dependency_optimizations");
        catalog.setDescription("Schema with cube dimension with functional dependency optimizations");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);

        return catalog;
    }

}
