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

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.osgi.service.component.annotations.Component;

@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    private static final String CATALOG = "tutorial_01-08_7_Cube_with_cub_dimension_with_level_with_closure_table";
    private static final String CUBE = "Cube";
    private static final String FACT = "Fact";

    private static final String schemaDocumentationTxt = """

                    """;

    @Override
    public CatalogMapping get() {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.setId("databaseSchema");

        Column employeeIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        employeeIdColumn.setName("employee_id");
        employeeIdColumn.setId("Fact_employee_id");
        employeeIdColumn.setType(ColumnType.INTEGER);

        Column fullNameColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        fullNameColumn.setName("full_name");
        fullNameColumn.setId("Fact_full_name");
        fullNameColumn.setType(ColumnType.VARCHAR);

        Column supervisorIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        supervisorIdColumn.setName("supervisor_id");
        supervisorIdColumn.setId("Fact_supervisor_id");
        supervisorIdColumn.setType(ColumnType.INTEGER);

        Column maritalStatusColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        maritalStatusColumn.setName("marital_status");
        maritalStatusColumn.setId("Fact_marital_status");
        maritalStatusColumn.setType(ColumnType.VARCHAR);

        Column positionTitleColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        positionTitleColumn.setName("position_title");
        positionTitleColumn.setId("Fact_position_title");
        positionTitleColumn.setType(ColumnType.VARCHAR);

        Column genderColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        genderColumn.setName("gender");
        genderColumn.setId("Fact_gender");
        genderColumn.setType(ColumnType.VARCHAR);

        Column salaryColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        salaryColumn.setName("salary");
        salaryColumn.setId("Fact_salary");
        salaryColumn.setType(ColumnType.INTEGER);

        Column educationLevelColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        educationLevelColumn.setName("education_level");
        educationLevelColumn.setId("Fact_education_level");
        educationLevelColumn.setType(ColumnType.INTEGER);

        Column managementRoleColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        managementRoleColumn.setName("management_role");
        managementRoleColumn.setId("Fact_management_role");
        managementRoleColumn.setType(ColumnType.VARCHAR);

        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(FACT);
        table.setId(FACT);
        table.getColumns().addAll(List.of(employeeIdColumn, fullNameColumn, supervisorIdColumn, maritalStatusColumn,
                positionTitleColumn, genderColumn, salaryColumn, educationLevelColumn, managementRoleColumn));
        databaseSchema.getTables().add(table);

        Column employeeClosureSupervisorIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        employeeClosureSupervisorIdColumn.setName("supervisor_id");
        employeeClosureSupervisorIdColumn.setId("employee_closure_supervisor_id");
        employeeClosureSupervisorIdColumn.setType(ColumnType.INTEGER);

        Column employeeClosureEmployeeIdColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        employeeClosureEmployeeIdColumn.setName("employee_id");
        employeeClosureEmployeeIdColumn.setId("employee_closure_employee_id");
        employeeClosureEmployeeIdColumn.setType(ColumnType.INTEGER);

        Column employeeClosureDistanceColumn = RolapMappingFactory.eINSTANCE.createPhysicalColumn();
        employeeClosureDistanceColumn.setName("distance");
        employeeClosureDistanceColumn.setId("employee_closure_distance");
        employeeClosureDistanceColumn.setType(ColumnType.INTEGER);

        PhysicalTable employeeClosureTable = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        employeeClosureTable.setName("employee_closure");
        employeeClosureTable.setId("employee_closure");
        employeeClosureTable.getColumns().addAll(List.of(employeeClosureSupervisorIdColumn,
                employeeClosureEmployeeIdColumn, employeeClosureDistanceColumn));
        databaseSchema.getTables().add(employeeClosureTable);

        TableQuery query = RolapMappingFactory.eINSTANCE.createTableQuery();
        query.setId("FactQuery");
        query.setTable(table);

        TableQuery employeeClosureQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        employeeClosureQuery.setId("EmployeeClosureQuery");
        employeeClosureQuery.setTable(employeeClosureTable);

        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure");
        measure.setId("Measure");
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
        propEducationLevel.setColumn(educationLevelColumn);

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
        level.setId("EmployeeIdLevel");
        level.setUniqueMembers(true);
        level.setColumn(employeeIdColumn);
        level.setNameColumn(fullNameColumn);
        level.getMemberProperties().addAll(List.of(propMaritalStatus, propPositionTitle, propGender, propSalary,
                propEducationLevel, propManagementRole));

        ParentChildHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setId("hierarchy");
        hierarchy.setAllMemberName("All Employees");
        hierarchy.setPrimaryKey(employeeIdColumn);
        hierarchy.setQuery(query);
        hierarchy.setLevel(level);
        hierarchy.setParentColumn(supervisorIdColumn);
        hierarchy.setNullParentValue("0");
        hierarchy.setParentChildLink(parentChildLink);


        StandardDimension dimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        dimension.setName("Employees");
        dimension.setId("Employees");
        dimension.getHierarchies().add(hierarchy);


        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setOverrideDimensionName("Employees");
        dimensionConnector.setForeignKey(employeeIdColumn);
        dimensionConnector.setDimension(dimension);

        PhysicalCube cube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE);
        cube.setId(CUBE);
        cube.setQuery(query);
        cube.getMeasureGroups().add(measureGroup);
        cube.getDimensionConnectors().add(dimensionConnector);

        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.setName("Minimal_Cube_with_cube_dimension_level_with_closure_table");
        catalog.setDescription("Schema of a minimal cube with cube dimension level with closure table");
        catalog.getCubes().add(cube);
        Documentation schemaDocumentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        schemaDocumentation.setValue(schemaDocumentationTxt);
        catalog.getDocumentations().add(schemaDocumentation);
        catalog.getDbschemas().add(databaseSchema);
        return catalog;
    }

}
