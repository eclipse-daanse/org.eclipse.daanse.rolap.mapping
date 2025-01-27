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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.dimensionwithfunctionaldependencyoptimization;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureAggregator;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

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
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column auotoDimIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        auotoDimIdColumn.setName("AUTO_DIM_ID");
        auotoDimIdColumn.setId("AUTOMOTIVE_DIM_AUTO_DIM_ID");
        auotoDimIdColumn.setType("INTEGER");

        Column makeIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        makeIdColumn.setName("MAKE_ID");
        makeIdColumn.setId("AUTOMOTIVE_DIM_MAKE_ID");
        makeIdColumn.setType("INTEGER");

        Column makeColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        makeColumn.setName("MAKE");
        makeColumn.setId("AUTOMOTIVE_DIM_MAKE");
        makeColumn.setType("VARCHAR");
        makeColumn.setColumnSize(100);

        Column modelIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        modelIdColumn.setName("MODEL_ID");
        modelIdColumn.setId("AUTOMOTIVE_DIM_MODEL_ID");
        modelIdColumn.setType("INTEGER");

        Column modelColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        modelColumn.setName("MODEL");
        modelColumn.setId("AUTOMOTIVE_DIM_MODEL");
        modelColumn.setType("VARCHAR");
        modelColumn.setColumnSize(100);

        Column plantIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        plantIdColumn.setName("PLANT_ID");
        plantIdColumn.setId("AUTOMOTIVE_DIM_PLANT_ID");
        plantIdColumn.setType("INTEGER");

        Column plantColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        plantColumn.setName("PLANT");
        plantColumn.setId("AUTOMOTIVE_DIM_PLANT");
        plantColumn.setType("VARCHAR");
        plantColumn.setColumnSize(100);

        Column plantStateIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        plantStateIdColumn.setName("PLANT_STATE_ID");
        plantStateIdColumn.setId("AUTOMOTIVE_DIM_PLANT_STATE_ID");
        plantStateIdColumn.setType("INTEGER");

        Column plantCityIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        plantCityIdColumn.setName("PLANT_CITY_ID");
        plantCityIdColumn.setId("AUTOMOTIVE_DIM_PLANT_CITY_ID");
        plantCityIdColumn.setType("INTEGER");

        Column vehicleIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        vehicleIdColumn.setName("VEHICLE_ID");
        vehicleIdColumn.setId("AUTOMOTIVE_DIM_VEHICLE_ID");
        vehicleIdColumn.setType("INTEGER");

        Column colorIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        colorIdColumn.setName("COLOR_ID");
        colorIdColumn.setId("AUTOMOTIVE_DIM_COLOR_ID");
        colorIdColumn.setType("INTEGER");

        Column trimIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        trimIdColumn.setName("TRIM_ID");
        trimIdColumn.setId("AUTOMOTIVE_DIM_TRIM_ID");
        trimIdColumn.setType("INTEGER");

        Column licenseIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        licenseIdColumn.setName("LICENSE_ID");
        licenseIdColumn.setId("AUTOMOTIVE_DIM_LICENSE_ID");
        licenseIdColumn.setType("INTEGER");

        Column licenseColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        licenseColumn.setName("LICENSE");
        licenseColumn.setId("AUTOMOTIVE_DIM_LICENSE");
        licenseColumn.setType("VARCHAR");
        licenseColumn.setColumnSize(100);

        Column licenseStateIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        licenseStateIdColumn.setName("LICENSE_STATE_ID");
        licenseStateIdColumn.setId("AUTOMOTIVE_DIM_LICENSE_STATE_ID");
        licenseStateIdColumn.setType("INTEGER");

        Column priceColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        priceColumn.setName("PRICE");
        priceColumn.setId("AUTOMOTIVE_DIM_PRICE");
        priceColumn.setType("INTEGER");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(auotoDimIdColumn, makeIdColumn, makeColumn, modelIdColumn, modelColumn, plantIdColumn,
                plantColumn, plantStateIdColumn, plantCityIdColumn, vehicleIdColumn,colorIdColumn, trimIdColumn, licenseIdColumn,
                licenseColumn,licenseStateIdColumn,priceColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setColumn(priceColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelMake = RolapMappingFactory.eINSTANCE.createLevel();
        levelMake.setName("Make");
        levelMake.setColumn(makeIdColumn);
        levelMake.setNameColumn(makeColumn);
        levelMake.setColumnType(ColumnDataType.NUMERIC);

        Level levelModel = RolapMappingFactory.eINSTANCE.createLevel();
        levelModel.setName("Model");
        levelModel.setColumn(modelIdColumn);
        levelModel.setNameColumn(modelColumn);
        levelModel.setColumnType(ColumnDataType.NUMERIC);

        MemberProperty stateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        stateProperty.setName("State");
        stateProperty.setColumn(plantStateIdColumn);
        stateProperty.setPropertyType(ColumnDataType.NUMERIC);
        stateProperty.setDependsOnLevelValue(true);

        MemberProperty cytyProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        cytyProperty.setName("City");
        cytyProperty.setColumn(plantCityIdColumn);
        cytyProperty.setPropertyType(ColumnDataType.NUMERIC);
        cytyProperty.setDependsOnLevelValue(true);

        Level levelPlant = RolapMappingFactory.eINSTANCE.createLevel();
        levelPlant.setName("ManufacturingPlant");
        levelPlant.setColumn(plantIdColumn);
        levelPlant.setNameColumn(plantColumn);
        levelPlant.setColumnType(ColumnDataType.NUMERIC);
        levelPlant.getMemberProperties().addAll(List.of(stateProperty, cytyProperty));

        MemberProperty colorProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        colorProperty.setName("Color");
        colorProperty.setColumn(colorIdColumn);
        colorProperty.setPropertyType(ColumnDataType.NUMERIC);
        colorProperty.setDependsOnLevelValue(true);

        MemberProperty trimProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        trimProperty.setName("Trim");
        trimProperty.setColumn(trimIdColumn);
        trimProperty.setPropertyType(ColumnDataType.NUMERIC);
        trimProperty.setDependsOnLevelValue(true);

        Level levelVehicle = RolapMappingFactory.eINSTANCE.createLevel();
        levelVehicle.setName("Vehicle Identification Number");
        levelVehicle.setColumn(vehicleIdColumn);
        levelVehicle.setColumnType(ColumnDataType.NUMERIC);
        levelVehicle.getMemberProperties().addAll(List.of(colorProperty, trimProperty));

        MemberProperty licenseStateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        licenseStateProperty.setName("State");
        licenseStateProperty.setColumn(licenseStateIdColumn);
        licenseStateProperty.setPropertyType(ColumnDataType.NUMERIC);
        licenseStateProperty.setDependsOnLevelValue(true);

        Level levelLicense = RolapMappingFactory.eINSTANCE.createLevel();
        levelLicense.setName("LicensePlateNum");
        levelLicense.setColumn(vehicleIdColumn);
        levelLicense.setColumnType(ColumnDataType.STRING);
        levelLicense.getMemberProperties().addAll(List.of(licenseStateProperty));

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setPrimaryKey(auotoDimIdColumn);
        hierarchy.setUniqueKeyLevelName("Vehicle Identification Number");
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelMake, levelModel, levelPlant, levelVehicle, levelLicense));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Automotive");
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

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cube_with_cube_dimension_with_functional_dependency_optimizations");
        schema.setDescription("Schema with cube dimension with functional dependency optimizations");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getSchemas().add(schema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("Catalog with schema with cube dimension with functional dependency optimizations");
        catalog.setDocumentation(documentation);
        return catalog;
    }

}
