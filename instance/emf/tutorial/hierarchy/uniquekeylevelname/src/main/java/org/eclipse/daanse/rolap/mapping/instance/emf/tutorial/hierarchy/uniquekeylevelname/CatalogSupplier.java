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


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.rolap.mapping.model.database.relational.ColumnInternalDataType;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.16.03", source = Source.EMF, group = "Hierarchy") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Level levelModel;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level levelVehicle;
    private PhysicalCube cube;
    private Level levelMake;
    private TableSource query;
    private Level levelPlant;
    private Level levelLicense;
    private SumMeasure measure;


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
            The Query is a simple TableSource that selects all columns from the `AUTOMOTIVE_DIM` table to use in the measures.
            """;

    private static final String levelMakeBody = """
            This Example uses 'Make' level based on the `MAKE_ID` column as key and name column `MAKE` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelModelBody = """
            This Example uses 'Model' level based on the `MODEL_ID` column as key and name column `MODEL` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelPlantBody = """
            This Example uses 'ManufacturingPlant' level based on the `PLANT_ID` column as key and name column `PLANT` of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelVehicleBody = """
            This Example uses 'Vehicle Identification Number' level based on the `VEHICLE_ID` column as key of table `AUTOMOTIVE_DIM`.
            """;

    private static final String levelLicenseBody = """
            This Example uses 'LicensePlateNum' level based on the `LICENSE_ID` column as key and name column `LICENSE` of table `AUTOMOTIVE_DIM`.
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
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column auotoDimIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        auotoDimIdColumn.setName("AUTO_DIM_ID");
        auotoDimIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column makeIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        makeIdColumn.setName("MAKE_ID");
        makeIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column makeColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        makeColumn.setName("MAKE");
        makeColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column modelIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        modelIdColumn.setName("MODEL_ID");
        modelIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column modelColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        modelColumn.setName("MODEL");
        modelColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column plantIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        plantIdColumn.setName("PLANT_ID");
        plantIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column plantColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        plantColumn.setName("PLANT");
        plantColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column plantStateIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        plantStateIdColumn.setName("PLANT_STATE_ID");
        plantStateIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column plantCityIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        plantCityIdColumn.setName("PLANT_CITY_ID");
        plantCityIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column vehicleIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        vehicleIdColumn.setName("VEHICLE_ID");
        vehicleIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column colorIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        colorIdColumn.setName("COLOR_ID");
        colorIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column trimIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        trimIdColumn.setName("TRIM_ID");
        trimIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column licenseIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        licenseIdColumn.setName("LICENSE_ID");
        licenseIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column licenseColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        licenseColumn.setName("LICENSE");
        licenseColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column licenseStateIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        licenseStateIdColumn.setName("LICENSE_STATE_ID");
        licenseStateIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column priceColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        priceColumn.setName("PRICE");
        priceColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table table = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        table.setName(FACT);
        table.getFeature()
                .addAll(List.of(auotoDimIdColumn, makeIdColumn, makeColumn, modelIdColumn, modelColumn, plantIdColumn,
                        plantColumn, plantStateIdColumn, plantCityIdColumn, vehicleIdColumn, colorIdColumn,
                        trimIdColumn, licenseIdColumn, licenseColumn, licenseStateIdColumn, priceColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(priceColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        levelMake = LevelFactory.eINSTANCE.createLevel();
        levelMake.setName("Make");
        levelMake.setColumn(makeIdColumn);
        levelMake.setNameColumn(makeColumn);

        levelModel = LevelFactory.eINSTANCE.createLevel();
        levelModel.setName("Model");
        levelModel.setColumn(modelIdColumn);
        levelModel.setNameColumn(modelColumn);

        MemberProperty stateProperty = LevelFactory.eINSTANCE.createMemberProperty();
        stateProperty.setName("State");
        stateProperty.setColumn(plantStateIdColumn);
        stateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        stateProperty.setDependsOnLevelValue(true);

        MemberProperty cytyProperty = LevelFactory.eINSTANCE.createMemberProperty();
        cytyProperty.setName("City");
        cytyProperty.setColumn(plantCityIdColumn);
        cytyProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        cytyProperty.setDependsOnLevelValue(true);

        levelPlant = LevelFactory.eINSTANCE.createLevel();
        levelPlant.setName("ManufacturingPlant");
        levelPlant.setColumn(plantIdColumn);
        levelPlant.setNameColumn(plantColumn);
        levelPlant.getMemberProperties().addAll(List.of(stateProperty, cytyProperty));

        MemberProperty colorProperty = LevelFactory.eINSTANCE.createMemberProperty();
        colorProperty.setName("Color");
        colorProperty.setColumn(colorIdColumn);
        colorProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        colorProperty.setDependsOnLevelValue(true);

        MemberProperty trimProperty = LevelFactory.eINSTANCE.createMemberProperty();
        trimProperty.setName("Trim");
        trimProperty.setColumn(trimIdColumn);
        trimProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        trimProperty.setDependsOnLevelValue(true);

        levelVehicle = LevelFactory.eINSTANCE.createLevel();
        levelVehicle.setName("Vehicle Identification Number");
        levelVehicle.setColumn(vehicleIdColumn);
        levelVehicle.getMemberProperties().addAll(List.of(colorProperty, trimProperty));

        MemberProperty licenseStateProperty = LevelFactory.eINSTANCE.createMemberProperty();
        licenseStateProperty.setName("State");
        licenseStateProperty.setColumn(licenseStateIdColumn);
        licenseStateProperty.setPropertyType(ColumnInternalDataType.NUMERIC);
        licenseStateProperty.setDependsOnLevelValue(true);

        levelLicense = LevelFactory.eINSTANCE.createLevel();
        levelLicense.setName("LicensePlateNum");
        levelLicense.setColumn(licenseIdColumn);
        levelLicense.getMemberProperties().addAll(List.of(licenseStateProperty));

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setPrimaryKey(auotoDimIdColumn);
        hierarchy.setUniqueKeyLevelName("Vehicle Identification Number");
        hierarchy.setQuery(query);
        hierarchy.getLevels().addAll(List.of(levelMake, levelModel, levelPlant, levelVehicle, levelLicense));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Automotive");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Automotive");
        dimensionConnector.setDimension(dimension);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Unique Key Level Name");
        catalog.setDescription("Hierarchy with unique key level name optimizations");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);


        return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Unique Key Level Name", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query", queryBody, 1, 2, 0, query, 2),
                        new DocSection("Automotive", dimensionBody, 1, 3, 0, dimension, 2),
                        new DocSection("Hierarchy", hierarchyBody, 1, 4, 0, hierarchy, 2),
                        new DocSection("Make", levelMakeBody, 1, 5, 0, levelMake, 2),
                        new DocSection("Model", levelModelBody, 1, 6, 0, levelModel, 2),
                        new DocSection("ManufacturingPlant", levelPlantBody, 1, 7, 0, levelPlant, 2),
                        new DocSection("Vehicle Identification Number", levelVehicleBody, 1, 8, 0, levelVehicle, 2),
                        new DocSection("LicensePlateNum", levelLicenseBody, 1, 9, 0, levelLicense, 2),
                        new DocSection("Measure", measure1Body, 1, 10, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 11, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
