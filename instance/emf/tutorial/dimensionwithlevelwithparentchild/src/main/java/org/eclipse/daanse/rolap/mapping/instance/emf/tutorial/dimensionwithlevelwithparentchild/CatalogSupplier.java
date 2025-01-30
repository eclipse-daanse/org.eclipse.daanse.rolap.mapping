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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.dimensionwithlevelwithparentchild;

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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-08_7_Cube_with_cub_dimension_with_level_with_closure_table";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """
                A basic OLAP schema with a level

                Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
                This example schema contains one cube named "Cube".

            A closure table is a SQL table which contains a record for every employee/supervisor relationship,
            regardless of depth. (In mathematical terms, this is called the 'reflexive transitive closure'
            of the employee/supervisor relationship.
            The distance column is not strictly required, but it makes it easier to populate the table.)
                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();

        Column employeeIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        employeeIdColumn.setName("employee_id");
        employeeIdColumn.setId("Fact_employee_id");
        employeeIdColumn.setType("INTEGER");

        Column fullNameColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        fullNameColumn.setName("full_name");
        fullNameColumn.setId("Fact_full_name");
        fullNameColumn.setType("VARCHAR");

        Column supervisorIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        supervisorIdColumn.setName("supervisor_id");
        supervisorIdColumn.setId("Fact_supervisor_id");
        supervisorIdColumn.setType("INTEGER");

        Column maritalStatusColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        maritalStatusColumn.setName("marital_status");
        maritalStatusColumn.setId("Fact_marital_status");
        maritalStatusColumn.setType("VARCHAR");

        Column positionTitleColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        positionTitleColumn.setName("position_title");
        positionTitleColumn.setId("Fact_position_title");
        positionTitleColumn.setType("VARCHAR");

        Column genderColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        genderColumn.setName("gender");
        genderColumn.setId("Fact_gender");
        genderColumn.setType("VARCHAR");

        Column salaryColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        salaryColumn.setName("salary");
        salaryColumn.setId("Fact_salary");
        salaryColumn.setType("INTEGER");

        Column educationLevelColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        educationLevelColumn.setName("education_level");
        educationLevelColumn.setId("Fact_education_level");
        educationLevelColumn.setType("INTEGER");

        Column managementRoleColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        managementRoleColumn.setName("management_role");
        managementRoleColumn.setId("Fact_management_role");
        managementRoleColumn.setType("VARCHAR");

        PhysicalTable table = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(employeeIdColumn, fullNameColumn, supervisorIdColumn, maritalStatusColumn,
                positionTitleColumn, genderColumn, salaryColumn, educationLevelColumn, managementRoleColumn));
        databaseSchema.getTables().add(table);

        Column employeeClosureSupervisorIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        employeeClosureSupervisorIdColumn.setName("supervisor_id");
        employeeClosureSupervisorIdColumn.setId("employee_closure_supervisor_id");
        employeeClosureSupervisorIdColumn.setType("INTEGER");

        Column employeeClosureEmployeeIdColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        employeeClosureEmployeeIdColumn.setName("employee_id");
        employeeClosureEmployeeIdColumn.setId("employee_closure_employee_id");
        employeeClosureEmployeeIdColumn.setType("INTEGER");

        Column employeeClosureDistanceColumn = RelationalDatabaseFactory.eINSTANCE.createColumn();
        employeeClosureDistanceColumn.setName("distance");
        employeeClosureDistanceColumn.setId("employee_closure_distance");
        employeeClosureDistanceColumn.setType("INTEGER");

        PhysicalTable employeeClosureTable = RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        employeeClosureTable.setName("employee_closure");
        employeeClosureTable.setId("employee_closure");
        employeeClosureTable.getColumns().addAll(List.of(employeeClosureSupervisorIdColumn,
                employeeClosureEmployeeIdColumn, employeeClosureDistanceColumn));
        databaseSchema.getTables().add(employeeClosureTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(table);

        TableQuery employeeClosureQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setTable(employeeClosureTable);

        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setAggregator(MeasureAggregator.SUM);
        measure.setName("Measure");
        measure.setColumn(salaryColumn);

        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        MemberProperty propMaritalStatus = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propMaritalStatus.setName("Marital Status");
        propMaritalStatus.setId("MaritalStatus");
        propMaritalStatus.setColumn(maritalStatusColumn);

        MemberProperty propPositionTitle = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propPositionTitle.setName("Position Title");
        propPositionTitle.setId("PositionTitle");
        propPositionTitle.setColumn(positionTitleColumn);

        MemberProperty propGender = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propGender.setName("Gender");
        propGender.setId("Gender");
        propGender.setColumn(genderColumn);

        MemberProperty propSalary = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propSalary.setName("Salary");
        propSalary.setId("Salary");
        propSalary.setColumn(salaryColumn);

        MemberProperty propEducationLevel = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propEducationLevel.setName("Education Level");
        propEducationLevel.setId("EducationLevel");
        propEducationLevel.setColumn(salaryColumn);

        MemberProperty propManagementRole = RolapMappingFactory.eINSTANCE.createMemberProperty();
        propManagementRole.setName("Management Role");
        propManagementRole.setId("ManagementRole");
        propManagementRole.setColumn(managementRoleColumn);

        ParentChildLink parentChildLink = RolapMappingFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setParentColumn(employeeClosureSupervisorIdColumn);
        parentChildLink.setChildColumn(employeeClosureEmployeeIdColumn);
        parentChildLink.setTable(employeeClosureQuery);

        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setName("Employee Id");
        level.setUniqueMembers(true);
        level.setColumnType(ColumnDataType.NUMERIC);
        level.setColumn(employeeIdColumn);
        level.setNameColumn(fullNameColumn);
        level.setParentColumn(supervisorIdColumn);
        level.setNullParentValue("0");
        level.setParentChildLink(parentChildLink);
        level.getMemberProperties().addAll(List.of(propMaritalStatus, propPositionTitle, propGender, propSalary,
                propEducationLevel, propManagementRole));

        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.setHasAll(true);

        hierarchy.setAllMemberName("All Employees");
        hierarchy.setPrimaryKey(employeeIdColumn);
        hierarchy.setQuery(query);
        hierarchy.getLevels().add(level);

        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Employees");
        dimension.getHierarchies().add(hierarchy);

        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Employees");
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.setName("Minimal_Cube_with_cube_dimension_level_with_closure_table");
        schema.setDescription("Schema of a minimal cube with cube dimension level with closure table");
        schema.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        schema.setDocumentation(schemaDocumentation);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG);
        catalog.getSchemas().add(schema);
        catalog.getDbschemas().add(databaseSchema);
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue("Catalog with schema of a minimal cube with cube dimension level with closure table");
        catalog.setDocumentation(documentation);
        return catalog;
    }

}
