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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.hierarchy.uniquekeylevelname;

import static org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider.util.DocumentationUtil.document;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.3", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CUBE = "Cube";
    private static final String FACT = "AUTOMOTIVE_DIM";

    private static final String catalogBody = """
            Catalog with Minimal Cube with Parent Child Hierarchy
            """;

    private static final String databaseSchemaBody = """
            The Database Schema contains the `AUTOMOTIVE_DIM` table with 16 columns:
            `AUTO_DIM_ID`, `MAKE_ID`, `MAKE`, `MODEL_ID`, `MODEL`, `PLANT_ID`, `PLANT`, `PLANT_STATE_ID`,
            `PLANT_CITY_ID`, `VEHICLE_ID`, `COLOR_ID`, `TRIM_ID`, `LICENSE_ID`, `LICENSE`, `LICENSE_STATE_ID`, `PRICE`.
            The `AUTO_DIM_ID` column is used as the discriminator in the Hierarchy definitions.
            """;

    private static final String queryBody = """
            The Query is a simple TableQuery that selects all columns from the `AUTOMOTIVE_DIM` table to use in the measures.
            """;

    private static final String levelMakeBody = """
            This Example uses 'Make' level bases on the `MAKE_ID` column as key and name column `MAKE` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelModelBody = """
            This Example uses 'Model' level bases on the `MODEL_ID` column as key and name column `MODEL` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelPlantBody = """
            This Example uses 'ManufacturingPlant' level bases on the `PLANT_ID` column as key and name column `PLANT` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelVehicleBody = """
            This Example uses 'Vehicle Identification Number' level bases on the `VEHICLE_ID` column as key of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelLicenseBody = """
            This Example uses 'LicensePlateNum' level bases on the `LICENSE_ID` column as key and name column `LICENSE` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String hierarchyBody = """
            This hierarchy consists 5 levels Make, Model, ManufacturingPlant, Vehicle Identification Number, LicensePlateNum.
            Hierarchy has UniqueKeyLevelName attribute.
            The UniqueKeyLevelName attribute of a `<Hierarchy>` is used to indicate that the given level
            taken together with all higher levels in the hierarchy acts as a unique alternate key,
            ensuring that for any unique combination of those level values, there is exactly one combination
            of values for all levels below it.
            """;

    private static final String dimensionBody = """
            The time dimension is defined with the one hierarchy.
            """;

    private static final String measure1Body = """
            Measure use AUTOMOTIVE_DIM table PRICE column with sum aggregation in Cube.
    """;

    private static final String cubeBody = """
            The cube with hierarchy with functional dependency optimizations.
            """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("_databaseSchema_main");

        Column auotoDimIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        auotoDimIdColumn.setName("AUTO_DIM_ID");
        auotoDimIdColumn.setId("_column_automotiveDim_autoDimId");
        auotoDimIdColumn.setType(ColumnType.INTEGER);

        Column makeIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        makeIdColumn.setName("MAKE_ID");
        makeIdColumn.setId("_column_automotiveDim_makeId");
        makeIdColumn.setType(ColumnType.INTEGER);

        Column makeColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        makeColumn.setName("MAKE");
        makeColumn.setId("_column_automotiveDim_make");
        makeColumn.setType(ColumnType.VARCHAR);
        makeColumn.setColumnSize(100);

        Column modelIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        modelIdColumn.setName("MODEL_ID");
        modelIdColumn.setId("_column_automotiveDim_modelId");
        modelIdColumn.setType(ColumnType.INTEGER);

        Column modelColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        modelColumn.setName("MODEL");
        modelColumn.setId("_column_automotiveDim_model");
        modelColumn.setType(ColumnType.VARCHAR);
        modelColumn.setColumnSize(100);

        Column plantIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        plantIdColumn.setName("PLANT_ID");
        plantIdColumn.setId("_column_automotiveDim_plantId");
        plantIdColumn.setType(ColumnType.INTEGER);

        Column plantColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        plantColumn.setName("PLANT");
        plantColumn.setId("_column_automotiveDim_plant");
        plantColumn.setType(ColumnType.VARCHAR);
        plantColumn.setColumnSize(100);

        Column plantStateIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        plantStateIdColumn.setName("PLANT_STATE_ID");
        plantStateIdColumn.setId("_column_automotiveDim_plantStateId");
        plantStateIdColumn.setType(ColumnType.INTEGER);

        Column plantCityIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        plantCityIdColumn.setName("PLANT_CITY_ID");
        plantCityIdColumn.setId("_column_automotiveDim_plantCityId");
        plantCityIdColumn.setType(ColumnType.INTEGER);

        Column vehicleIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        vehicleIdColumn.setName("VEHICLE_ID");
        vehicleIdColumn.setId("_column_automotiveDim_vehicleId");
        vehicleIdColumn.setType(ColumnType.INTEGER);

        Column colorIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        colorIdColumn.setName("COLOR_ID");
        colorIdColumn.setId("_column_automotiveDim_colorId");
        colorIdColumn.setType(ColumnType.INTEGER);

        Column trimIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        trimIdColumn.setName("TRIM_ID");
        trimIdColumn.setId("_column_automotiveDim_trimId");
        trimIdColumn.setType(ColumnType.INTEGER);

        Column licenseIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        licenseIdColumn.setName("LICENSE_ID");
        licenseIdColumn.setId("_column_automotiveDim_licenseId");
        licenseIdColumn.setType(ColumnType.INTEGER);

        Column licenseColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        licenseColumn.setName("LICENSE");
        licenseColumn.setId("_column_automotiveDim_license");
        licenseColumn.setType(ColumnType.VARCHAR);
        licenseColumn.setColumnSize(100);

        Column licenseStateIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        licenseStateIdColumn.setName("LICENSE_STATE_ID");
        licenseStateIdColumn.setId("_column_automotiveDim_licenseStateId");
        licenseStateIdColumn.setType(ColumnType.INTEGER);

        Column priceColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        priceColumn.setName("PRICE");
        priceColumn.setId("_column_automotiveDim_price");
        priceColumn.setType(ColumnType.INTEGER);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId("_table_automotiveDim");
        table.getColumns()
                .addAll(List.of(auotoDimIdColumn, makeIdColumn, makeColumn, modelIdColumn, modelColumn, plantIdColumn,
                        plantColumn, plantStateIdColumn, plantCityIdColumn, vehicleIdColumn, colorIdColumn,
                        trimIdColumn, licenseIdColumn, licenseColumn, licenseStateIdColumn, priceColumn));
        databaseSchema.getTables().add(table);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("_query_fact");
        query.setTable(table);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("_measure_measure");
        measure.setColumn(priceColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        Level levelMake = RolapMappingFactory.eINSTANCE.createLevel();
        levelMake.setName("Make");
        levelMake.setId("_level_make");
        levelMake.setColumn(makeIdColumn);
        levelMake.setNameColumn(makeColumn);

        Level levelModel = RolapMappingFactory.eINSTANCE.createLevel();
        levelModel.setName("Model");
        levelModel.setId("_level_model");
        levelModel.setColumn(modelIdColumn);
        levelModel.setNameColumn(modelColumn);

        MemberProperty stateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        stateProperty.setName("State");
        stateProperty.setId("_memberProperty_state");
        stateProperty.setColumn(plantStateIdColumn);
        stateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        stateProperty.setDependsOnLevelValue(true);

        MemberProperty cytyProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        cytyProperty.setName("City");
        cytyProperty.setId("_memberProperty_city");
        cytyProperty.setColumn(plantCityIdColumn);
        cytyProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        cytyProperty.setDependsOnLevelValue(true);

        Level levelPlant = RolapMappingFactory.eINSTANCE.createLevel();
        levelPlant.setName("ManufacturingPlant");
        levelPlant.setId("_level_manufacturingPlant");
        levelPlant.setColumn(plantIdColumn);
        levelPlant.setNameColumn(plantColumn);
        levelPlant.getMemberProperties().addAll(List.of(stateProperty, cytyProperty));

        MemberProperty colorProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        colorProperty.setName("Color");
        colorProperty.setId("_memberProperty_color");
        colorProperty.setColumn(colorIdColumn);
        colorProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        colorProperty.setDependsOnLevelValue(true);

        MemberProperty trimProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        trimProperty.setName("Trim");
        trimProperty.setId("_memberProperty_trim");
        trimProperty.setColumn(trimIdColumn);
        trimProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        trimProperty.setDependsOnLevelValue(true);

        Level levelVehicle = RolapMappingFactory.eINSTANCE.createLevel();
        levelVehicle.setName("Vehicle Identification Number");
        levelVehicle.setId("_level_vehicleIdentificationNumber");
        levelVehicle.setColumn(vehicleIdColumn);
        levelVehicle.getMemberProperties().addAll(List.of(colorProperty, trimProperty));

        MemberProperty licenseStateProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        licenseStateProperty.setName("State");
        licenseStateProperty.setId("_memberProperty_licenseState");
        licenseStateProperty.setColumn(licenseStateIdColumn);
        licenseStateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        licenseStateProperty.setDependsOnLevelValue(true);

        Level levelLicense = RolapMappingFactory.eINSTANCE.createLevel();
        levelLicense.setName("LicensePlateNum");
        levelLicense.setId("_level_licensePlateNum");
        levelLicense.setColumn(licenseIdColumn);
        levelLicense.getMemberProperties().addAll(List.of(licenseStateProperty));

        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setPrimaryKey(auotoDimIdColumn);
        hierarchy.setId("_hierarchy_automotive");
        hierarchy.setUniqueKeyLevelName("Vehicle Identification Number");
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelMake, levelModel, levelPlant, levelVehicle, levelLicense));

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Automotive");
        dimension.setId("_dimension_automotive");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setId("_dimensionConnector_automotive");
        dimensionConnector.setOverrideDimensionName("Automotive");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId("_cube_cube");
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Unique Key Level Name");
        catalog.setDescription("Hierarchy with unique key level name optimizations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

        document(catalog, "Daanse Tutorial - Unique Key Level Name", catalogBody, 1, 0, 0, false, 0);
        document(databaseSchema, "Database Schema", databaseSchemaBody, 1, 1, 0, true, 3);
        document(query, "Query", queryBody, 1, 2, 0, true, 2);
        document(dimension, "Automotive", dimensionBody, 1, 3, 0, true, 2);
        document(hierarchy, "Hierarchy", hierarchyBody, 1, 4, 0, true, 2);
        document(levelMake, "Make", levelMakeBody, 1, 5, 0, true, 2);
        document(levelModel, "Model", levelModelBody, 1, 6, 0, true, 2);
        document(levelPlant, "ManufacturingPlant", levelPlantBody, 1, 7, 0, true, 2);
        document(levelVehicle, "Vehicle Identification Number", levelVehicleBody, 1, 8, 0, true, 2);
        document(levelLicense, "LicensePlateNum", levelLicenseBody, 1, 9, 0, true, 2);
        document(measure, "Measure", measure1Body, 1, 10, 0, true, 2);
        document(cube, "Cube", cubeBody, 1, 11, 0, true, 2);

        return catalog;
    }

}
