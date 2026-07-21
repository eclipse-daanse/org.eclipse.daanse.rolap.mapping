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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.level.relationalsource;


import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.enumerations.NullableType;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.osgi.service.component.annotations.Component;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SqlSimpleTypes;
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
@MappingInstance(kind = Kind.TUTORIAL, number = "2.14.08", source = Source.EMF, group = "Level") // NOSONAR
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private ExplicitHierarchy hierarchy;
    private StandardDimension dimension;
    private Schema databaseSchema;
    private Catalog catalog;
    private Level roomLevel;
    private PhysicalCube cube;
    private Level levelRole;
    private SumMeasure measure;
    private TableSource queryFact;


    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";
    private static final String EMPLOYEE = "Employee";

    private static final String catalogBody = """
            A basic OLAP schema with a one level : level role.
            Catalog has cube with one level role with RelationalSource
            """;

    private static final String databaseSchemaBody = """
            The cube defined in this example is based on a one table that stores all the data.
            - The phisical table is named `Fact` uses for Cube and contains two columns: `employee_id`, `salary`.
            The `employee_id` column serves as a discriminator, while the `salary` column contains the measurements to be aggregated.
            """;

    private static final String queryBody = """
            The bridge between the cube and the database is the query element. In this case, it is a TableQuery,
            as it directly references the physical table `Fact`.
            """;

    private static final String levelRoleBody = """
            The Level uses the column attribute to specify the primary key `employee_id` from `Employee`.
            `employee_id` column have unique values
            """;

    private static final String hierarchyBody = """
            This hierarchy consists one level Role.
            - The primaryKey attribute specifies the column that contains the primary key of the hierarchy.
            - The query attribute references the query used to retrieve the data for the hierarchy.
            """;

    private static final String dimensionBody = """
            The Dimension has only one hierarchy.
            """;

    private static final String measureBody = """
            Measure use Fact table salary column with sum aggregation in Cube.
    """;

    private static final String cubeBody = """
            In this example uses cube with fact table Fact as data.
            """;

    @Override
    public Catalog get() {
        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createSchema();

        Column employeeIdFactColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        employeeIdFactColumn.setName("employee_id");
        employeeIdFactColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column salaryFactColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        salaryFactColumn.setName("salary");
        salaryFactColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Table factTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        factTable.setName(FACT);
        factTable.getFeature().addAll(List.of(employeeIdFactColumn, salaryFactColumn));
        databaseSchema.getOwnedElement().add(factTable);

        Column employeeIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        employeeIdColumn.setName("employee_id");
        employeeIdColumn.setType(SqlSimpleTypes.Sql99.integerType());

        Column supervisorIdColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        supervisorIdColumn.setName("supervisor_id");
        supervisorIdColumn.setType(SqlSimpleTypes.Sql99.integerType());
        supervisorIdColumn.setIsNullable(NullableType.COLUMN_NULLABLE);

        Column nameColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        nameColumn.setName("name");
        nameColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Column managementRroleColumn = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        managementRroleColumn.setName("management_role");
        managementRroleColumn.setType(SqlSimpleTypes.Sql99.varcharType());

        Table employeeTable = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        employeeTable.setName(EMPLOYEE);
        employeeTable.getFeature().addAll(List.of(employeeIdColumn, supervisorIdColumn));
        databaseSchema.getOwnedElement().add(employeeTable);

        TableSource employeeQuery = SourceFactory.eINSTANCE.createTableSource();
        employeeQuery.setTable(employeeTable);
        employeeQuery.setAlias("employee");

        TableSource employeeManagerQuery = SourceFactory.eINSTANCE.createTableSource();
        employeeManagerQuery.setTable(employeeTable);
        employeeManagerQuery.setAlias("employee_manager");

        JoinedQueryElement queryHierarchyEmployeeLeft = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryHierarchyEmployeeLeft.setKey(supervisorIdColumn);
        queryHierarchyEmployeeLeft.setSource(employeeQuery);

        JoinedQueryElement queryHierarchyEmployeeRight = SourceFactory.eINSTANCE.createJoinedQueryElement();
        queryHierarchyEmployeeRight.setKey(employeeIdColumn);
        queryHierarchyEmployeeRight.setSource(employeeManagerQuery);

        JoinSource queryHierarchyEmployee = SourceFactory.eINSTANCE.createJoinSource();
        queryHierarchyEmployee.setLeft(queryHierarchyEmployeeLeft);
        queryHierarchyEmployee.setRight(queryHierarchyEmployeeRight);

        levelRole = LevelFactory.eINSTANCE.createLevel();
        levelRole.setName("Role");
        levelRole.setColumn(managementRroleColumn);
        levelRole.setRelationalSource(employeeManagerQuery);
        levelRole.setUniqueMembers(true);

        queryFact = SourceFactory.eINSTANCE.createTableSource();
        queryFact.setTable(factTable);

        measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setColumn(salaryFactColumn);

        MeasureGroup measureGroup1 = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup1.getMeasures().add(measure);

        hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setName("Employee");
        hierarchy.setPrimaryKey(employeeIdColumn);
        hierarchy.setSource(queryHierarchyEmployee);
        hierarchy.getLevels().addAll(List.of(levelRole));

        dimension = DimensionFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Employee");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Employee");
        dimensionConnector.setDimension(dimension);
        dimensionConnector.setForeignKey(employeeIdColumn);

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setSource(queryFact);
        cube.getMeasureGroups().add(measureGroup1);
        cube.getDimensionConnectors().add(dimensionConnector);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Daanse Tutorial - Level with relational source");
        catalog.setDescription("Level handling blank names");
        catalog.getCubes().add(cube);
        catalog.getDbschemas().add(databaseSchema);

            return catalog;
    }


    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection("Daanse Tutorial - Level with relational source", catalogBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Query Fact", queryBody, 1, 2, 0, queryFact, 2),
                        new DocSection("Dimension Employee", dimensionBody, 1, 5, 0, dimension, 2),
                        new DocSection("Hierarchy", hierarchyBody, 1, 6, 0, hierarchy, 2),
                        new DocSection("Role Level", levelRoleBody, 1, 7, 0, levelRole, 2),
                        new DocSection("Measure", measureBody, 1, 9, 0, measure, 2),
                        new DocSection("Cube", cubeBody, 1, 10, 0, cube, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
