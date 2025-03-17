/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.modifier.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessCatalogGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ActionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationColumnNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseCatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughActionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.FormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.KpiMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberReaderParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowValueMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlStatementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlViewMapping;
import org.eclipse.daanse.rolap.mapping.api.model.StandardDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SystemTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TimeDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ViewTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimension;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchy;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCatalog;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.RollupPolicyType;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;

public abstract class AbstractMappingModifier implements CatalogMappingSupplier {

    protected CatalogMapping catalog;

    protected CatalogMapping modifiedCatalog = null;

    private Map<CubeMapping, CubeMapping> cubeMap = new HashMap<>();

    private Map<DimensionMapping, DimensionMapping> dimensionMap = new HashMap<>();

    private Map<HierarchyMapping, HierarchyMapping> hierarchyMap = new HashMap<>();

    private Map<LevelMapping, LevelMapping> levelMap = new HashMap<>();

    private Map<FormatterMapping, FormatterMapping> formatterMap = new HashMap<>();

    private Map<DatabaseSchemaMapping, DatabaseSchemaMapping> dbSchemaMap = new HashMap<>();

    private Map<MeasureMapping, MeasureMapping> measureMap = new HashMap<>();

    private Map<CalculatedMemberMapping, CalculatedMemberMapping> calculatedMemberMap = new HashMap<>();

    private Map<AccessRoleMapping, AccessRoleMapping> accessRoleMap = new HashMap<>();

    private Map<AggregationTableMapping, AggregationTableMapping> aggregationTableMap = new HashMap<>();

    private Map<AggregationExcludeMapping, AggregationExcludeMapping> aggregationExcludeMap = new HashMap<>();

    private Map<QueryMapping, QueryMapping> queryMap = new HashMap<>();

    protected AbstractMappingModifier(CatalogMapping catalog) {
        super();
        this.catalog = catalog;
    }

    public CatalogMapping get() {
        if (modifiedCatalog == null) {
            modifiedCatalog = modifyCatalog(catalog);
        }
        return modifiedCatalog;
    }

    protected CatalogMapping modifyCatalog(CatalogMapping catalog2) {
        if (catalog2 != null) {

            List<? extends AnnotationMapping> annotations = catalogAnnotations(catalog2);
            String id = catalogId(catalog2);
            String description = catalogDescription(catalog2);
            String name = catalogName(catalog2);
            List<? extends ParameterMapping> parameters = catalogParameters(catalog2);
            List<? extends CubeMapping> cubes = catalogCubes(catalog2);
            List<? extends NamedSetMapping> namedSets = catalogNamedSets(catalog2);
            List<? extends AccessRoleMapping> accessRoles = catalogAccessRoles(catalog2);
            AccessRoleMapping defaultAccessRole = catalogDefaultAccessRole(catalog2);
            String measuresDimensionName = catalogMeasuresDimensionName(catalog2);
            List<? extends DatabaseSchemaMapping> dbschemas = catalogDatabaseSchemas(catalog2);

            return createCatalog(annotations, id, description, name, parameters, cubes, namedSets,
                accessRoles, defaultAccessRole, measuresDimensionName, dbschemas);
        }
        return null;

    }


    protected List<? extends DatabaseSchemaMapping> catalogDatabaseSchemas(CatalogMapping catalog2) {
        return databaseSchemas(catalog2.getDbschemas());
    }

    protected List<? extends AnnotationMapping> annotations(CatalogMapping catalog2) {
        return annotations(catalog2.getAnnotations());
    }

    protected List<DatabaseSchemaMapping> databaseSchemas(List<? extends DatabaseSchemaMapping> dbschemas) {
        if (dbschemas != null) {
            return dbschemas.stream().map(this::dbschema).toList();
        }
        return List.of();
    }

    protected DatabaseSchemaMapping dbschema(DatabaseSchemaMapping databaseSchema) {
        if (databaseSchema != null) {
            if (!dbSchemaMap.containsKey(databaseSchema)) {
                List<? extends TableMapping> tables = databaseSchemaTables(databaseSchema);
                String name = databaseSchemaName(databaseSchema);
                String id = databaseSchemaId(databaseSchema);
                DatabaseSchemaMapping ds = createDatabaseSchema(tables, name, id);
                dbSchemaMap.put(databaseSchema, ds);
                return ds;
            } else {
                return dbSchemaMap.get(databaseSchema);
            }
        }
        return null;
    }

    protected List<? extends TableMapping> databaseSchemaTables(DatabaseSchemaMapping databaseSchema) {
        return tables(databaseSchema.getTables());
    }

    protected List<TableMapping> tables(List<? extends TableMapping> tables) {
        if (tables != null) {
            return tables.stream().map(this::table).toList();
        }
        return List.of();
    }

    protected TableMapping table(TableMapping table) {
        if (table != null) {
            String name = tableName(table);
            List<? extends ColumnMapping> columns = tableColumns(table);
            DatabaseSchemaMapping schema = tableSchema(table);
            String description = tableDescription(table);
            if (table instanceof PhysicalTableMapping pt) {
                return physicalTable(pt);
            }
            if (table instanceof SystemTableMapping) {
                return createSystemTable(name, columns, schema, description);
            }
            if (table instanceof ViewTableMapping) {
                return createViewTable(name, columns, schema, description);
            }
            if (table instanceof InlineTableMapping it) {
                return inlineTable(it);
            }
            if (table instanceof SqlViewMapping sv) {
                List<? extends SqlStatementMapping> sqlStatements = sqlViewSqlStatements(sv);
                return createSqlView(name, columns, schema, description, sqlStatements);
            }
        }
        return null;
    }

    private PhysicalTableMapping physicalTable(TableMapping table) {
        String name = tableName(table);
        List<? extends ColumnMapping> columns = tableColumns(table);
        DatabaseSchemaMapping schema = tableSchema(table);
        String description = tableDescription(table);
        return createPhysicalTable(name, columns, schema, description);
    }

    protected SqlViewMapping sqlView(SqlViewMapping table) {
        if (table != null) {
            String name = tableName(table);
            List<? extends ColumnMapping> columns = tableColumns(table);
            DatabaseSchemaMapping schema = tableSchema(table);
            String description = tableDescription(table);
            List<? extends SqlStatementMapping> sqlStatements = sqlViewSqlStatements(table);
            return createSqlView(name, columns, schema, description, sqlStatements);
        }
        return null;
    }

    protected InlineTableMapping inlineTable(InlineTableMapping table) {
        if (table != null) {
            String name = tableName(table);
            List<? extends ColumnMapping> columns = tableColumns(table);
            DatabaseSchemaMapping schema = tableSchema(table);
            String description = tableDescription(table);
            List<? extends RowMapping> rows = inlineTableRows(table);
            return createInlineTable(name, columns, schema, description, rows);
        }
        return null;
    }

    protected List<? extends SqlStatementMapping> sqlViewSqlStatements(SqlViewMapping sv) {
        if (sv != null) {
            return sqlStatements(sv.getSqlStatements());
        }
        return List.of();
    }

    protected List<? extends SqlStatementMapping> sqlStatements(List<? extends SqlStatementMapping> sqlStatements) {
        if (sqlStatements != null) {
            return sqlStatements.stream().map(this::sqlStatement).toList();
        }
        return List.of();
    }



    protected List<String> sqlStatementDdialects(SqlStatementMapping sqlStatement) {
        return dialects(sqlStatement.getDialects());
    }

    protected String sqlStatementSql(SqlStatementMapping sqlStatement) {
        return sqlStatement.getSql();
    }

    protected abstract SqlViewMapping createSqlView(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description, List<? extends SqlStatementMapping> sqlStatements
    );

    protected abstract InlineTableMapping createInlineTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description, List<? extends RowMapping> rows
    );

    protected List<? extends RowMapping> inlineTableRows(InlineTableMapping it) {
        if (it != null) {
            return rows(it.getRows());
        }
        return List.of();
    }

    protected List<? extends RowMapping> rows(List<? extends RowMapping> rows) {
        if (rows != null) {
            return rows.stream().map(this::row).toList();
        }
        return List.of();
    }

    protected RowMapping row(RowMapping r) {
        if (r != null) {
            List<? extends RowValueMapping> rowValues = rowRowValue(r);
            return createRow(rowValues);
        }
        return null;
    }

    protected List<? extends RowValueMapping> rowRowValue(RowMapping r) {
        return rowValue(r.getRowValues());
    }

    protected List<? extends RowValueMapping> rowValue(List<? extends RowValueMapping> rowValues) {
        if (rowValues != null) {
            return rowValues.stream().map(this::rowValue).toList();
        }
        return List.of();
    }

    protected RowValueMapping rowValue(RowValueMapping rowValue) {
        if (rowValue != null) {
            ColumnMapping column = rowValueColumn(rowValue);
            String value = rowValueValue(rowValue);
            return createRowValue(column, value);
        }
        return null;
    }

    protected abstract RowValueMapping createRowValue(ColumnMapping column, String value);

    protected String rowValueValue(RowValueMapping rowValue) {
        return rowValue.getValue();
    }

    protected ColumnMapping rowValueColumn(RowValueMapping rowValue) {
        return column(rowValue.getColumn());
    }

    protected abstract RowMapping createRow(List<? extends RowValueMapping> rowValues);

    protected abstract TableMapping createViewTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    );

    protected abstract TableMapping createSystemTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    );

    protected abstract PhysicalTableMapping createPhysicalTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    );

    protected String tableDescription(TableMapping table) {
        return table.getDescription();
    }

    protected DatabaseSchemaMapping tableSchema(TableMapping table) {
        return dbschema(table.getSchema());
    }

    protected List<? extends ColumnMapping> tableColumns(TableMapping table) {
        return columns(table.getColumns());
    }

    protected List<ColumnMapping> columns(List<? extends ColumnMapping> columns) {
        if (columns != null) {
            return columns.stream().map(this::column).toList();
        }
        return List.of();
    }

    protected ColumnMapping column(ColumnMapping column) {
        if (column instanceof SQLExpressionColumnMapping sec) {
            return sqlExpression(sec);
        }
        else if (column instanceof ColumnMapping) {
            String name = columnName(column);
            TableMapping table = columnTable(column);
            ColumnDataType type = columnDataType(column);
            Integer columnSize = columnColumnSize(column);
            Integer decimalDigits = columnDecimalDigits(column);
            Integer numPrecRadix = columnNumPrecRadix(column);
            Integer charOctetLength = columnCharOctetLength(column);
            Boolean nullable = columnNullable(column);
            String description = columnDescription(column);
            return createPhysicalColumn(name, table, type, columnSize, decimalDigits, numPrecRadix, charOctetLength, nullable, description);
        }
        return null;
    }

    protected Integer columnColumnSize(ColumnMapping column) {
        return column.getColumnSize();
    }

    protected Integer columnDecimalDigits(ColumnMapping column) {
        return column.getDecimalDigits();
    }

    protected Integer columnNumPrecRadix(ColumnMapping column) {
        return column.getNumPrecRadix();
    }

    protected Integer columnCharOctetLength(ColumnMapping column) {
        return column.getNumPrecRadix();
    }

    protected Boolean columnNullable(ColumnMapping column) {
        return column.getNullable();
    }

    protected String columnDescription(ColumnMapping column) {
        return column.getDescription();
    }

    protected ColumnDataType columnDataType(ColumnMapping column) {
        return column.getDataType();
    }

    protected TableMapping columnTable(ColumnMapping column) {
        return table(column.getTable());
    }

    protected String columnName(ColumnMapping column) {
        return column.getName();
    }

    protected abstract ColumnMapping createPhysicalColumn(
        String name, TableMapping table, ColumnDataType type, Integer columnSize, Integer decimalDigits,
        Integer numPrecRadix, Integer charOctetLength, Boolean nullable, String description
    );

    protected String tableName(TableMapping table) {
        return table.getName();
    }

    protected String databaseSchemaId(DatabaseSchemaMapping databaseSchema) {
        return databaseSchema.getId();
    }

    protected String databaseSchemaName(DatabaseSchemaMapping databaseSchema) {
        return databaseSchema.getName();
    }

    protected abstract DatabaseSchemaMapping createDatabaseSchema(List<? extends TableMapping> tables, String name, String id);

    protected String catalogName(CatalogMapping catalog2) {
        return catalog2.getName();
    }

    protected String catalogDescription(CatalogMapping catalog2) {
        return catalog2.getDescription();
    }

    protected String catalogId(CatalogMapping catalog2) {
        return catalog2.getId();
    }

    protected String catalogMeasuresDimensionName(CatalogMapping catalogMappingOriginal) {
        return catalogMappingOriginal.getMeasuresDimensionName();
    }

    protected AccessRoleMapping catalogDefaultAccessRole(CatalogMapping catalogMappingOriginal) {
        return accessRole(catalogMappingOriginal.getDefaultAccessRole());
    }

    protected AccessRoleMapping accessRole(AccessRoleMapping accessRole) {
        if (accessRole != null) {
            if (!accessRoleMap.containsKey(accessRole)) {
                List<? extends AnnotationMapping> annotations = accessRoleAnnotations(accessRole);
                String id = accessRoleId(accessRole);
                String description = accessRoleDescription(accessRole);
                String name = accessRoleName(accessRole);

                List<? extends AccessCatalogGrantMapping> accessCatalogGrants = accessRoleAccessCatalogGrants(accessRole);
                List<? extends AccessRoleMapping> referencedAccessRoles = accessRoleReferencedAccessRoles(accessRole);
                AccessRoleMapping ar = createAccessRole(annotations, id, description, name,
                    accessCatalogGrants,
                    referencedAccessRoles);
                accessRoleMap.put(accessRole, ar);
                return ar;
            } else {
                return accessRoleMap.get(accessRole);
            }
        }
        return null;
    }

    protected List<? extends AnnotationMapping> accessRoleAnnotations(AccessRoleMapping accessRole) {
        return annotations(accessRole.getAnnotations());
    }

    protected String accessRoleName(AccessRoleMapping accessRole) {
        return accessRole.getName();
    }

    protected String accessRoleDescription(AccessRoleMapping accessRole) {
        return accessRole.getDescription();
    }

    protected String accessRoleId(AccessRoleMapping accessRole) {
        return accessRole.getId();
    }

    protected abstract AccessRoleMapping createAccessRole(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends AccessCatalogGrantMapping> accessCatalogGrants,
        List<? extends AccessRoleMapping> referencedAccessRoles
    );

    protected List<? extends AccessRoleMapping> accessRoleReferencedAccessRoles(AccessRoleMapping accessRole) {
        return accessRoles(accessRole.getReferencedAccessRoles());
    }

    protected List<? extends AccessCatalogGrantMapping> accessRoleAccessCatalogGrants(AccessRoleMapping accessRole) {
        return accessCatalogGrants(accessRole.getAccessCatalogGrants());
    }

    protected List<AccessCatalogGrantMapping> accessCatalogGrants(
        List<? extends AccessCatalogGrantMapping> accessCatalogGrants
    ) {
        if (accessCatalogGrants != null) {
            return accessCatalogGrants.stream().map(this::accessCatalogGrant).toList();
        }
        return List.of();
    }

    protected AccessCatalogGrantMapping accessCatalogGrant(AccessCatalogGrantMapping accessCatalogGrant) {
        if (accessCatalogGrant != null) {
            List<? extends AccessCubeGrantMapping> accessCubeGrant = accessCatalogGrantAccessCubeGrant(
                accessCatalogGrant);
            AccessCatalog access = accessCatalogGrantAccess(accessCatalogGrant);
            return createAccessCatalogGrant(accessCubeGrant, access);
        }
        return null;
    }

    protected List<? extends AccessCubeGrantMapping> accessCatalogGrantAccessCubeGrant(
        AccessCatalogGrantMapping accessCatalogGrant
    ) {
        return accessCubeGrants(accessCatalogGrant.getCubeGrants());
    }

    protected List<AccessCubeGrantMapping> accessCubeGrants(
        List<? extends AccessCubeGrantMapping> accessCubeGrants
    ) {
        if (accessCubeGrants != null) {
            return accessCubeGrants.stream().map(this::accessCubeGrant).toList();
        }
        return List.of();
    }

    protected AccessCubeGrantMapping accessCubeGrant(AccessCubeGrantMapping accessCubeGrant) {
        if (accessCubeGrant != null) {
            List<? extends AccessDimensionGrantMapping> dimensionGrants = accessCubeGrantAccessDimension(
                accessCubeGrant);

            List<? extends AccessHierarchyGrantMapping> hierarchyGrants = accessCubeGrantAccessHierarchyGrant(
                accessCubeGrant);

            AccessCube access = accessCubeGrantAccess(accessCubeGrant);

            CubeMapping cube = accessCubeGrantCube(accessCubeGrant);

            return createAccessCubeGrant(dimensionGrants, hierarchyGrants, access, cube);
        }
        return null;
    }

    protected List<? extends AccessHierarchyGrantMapping> accessCubeGrantAccessHierarchyGrant(
        AccessCubeGrantMapping accessCubeGrant
    ) {
        return accessHierarchyGrants(accessCubeGrant.getHierarchyGrants());
    }

    protected List<AccessHierarchyGrantMapping> accessHierarchyGrants(
        List<? extends AccessHierarchyGrantMapping> accessHierarchyGrants
    ) {
        if (accessHierarchyGrants != null) {
            return accessHierarchyGrants.stream().map(this::accessHierarchyGrant).toList();
        }
        return List.of();
    }

    protected AccessHierarchyGrantMapping accessHierarchyGrant(AccessHierarchyGrantMapping accessHierarchyGrant) {
        if (accessHierarchyGrant != null) {
            List<? extends AccessMemberGrantMapping> memberGrants = accessHierarchyGrantMemberGrants(
                accessHierarchyGrant);
            AccessHierarchy access = accessHierarchyGrantAccess(accessHierarchyGrant);
            LevelMapping bottomLevel = accessHierarchyGrantBottomLevel(accessHierarchyGrant);
            RollupPolicyType rollupPolicy = accessHierarchyGrantRollupPolicy(accessHierarchyGrant);
            LevelMapping topLevel = accessHierarchyGrantTopLevel(accessHierarchyGrant);
            HierarchyMapping hierarchy = accessHierarchyGrantHierarchy(accessHierarchyGrant);

            return createAccessHierarchyGrant(memberGrants, access, bottomLevel, rollupPolicy, topLevel, hierarchy);
        }
        return null;
    }

    protected List<? extends AccessMemberGrantMapping> accessHierarchyGrantMemberGrants(
        AccessHierarchyGrantMapping accessHierarchyGrant
    ) {
        return accessMemberGrants(accessHierarchyGrant.getMemberGrants());
    }

    protected List<AccessMemberGrantMapping> accessMemberGrants(
        List<? extends AccessMemberGrantMapping> accessMemberGrants
    ) {
        if (accessMemberGrants != null) {
            return accessMemberGrants.stream().map(this::accessMemberGrant).toList();
        }
        return List.of();
    }

    protected AccessMemberGrantMapping accessMemberGrant(AccessMemberGrantMapping accessMemberGrant) {
        if (accessMemberGrant != null) {
            AccessMember access = accessMemberGrantAccess(accessMemberGrant);
            String member = accessMemberGrantMember(accessMemberGrant);
            return createAccessMemberGrant(access, member);
        }
        return null;
    }

    protected String accessMemberGrantMember(AccessMemberGrantMapping accessMemberGrant) {
        return accessMemberGrant.getMember();
    }

    protected AccessMember accessMemberGrantAccess(AccessMemberGrantMapping accessMemberGrant) {
        return accessMemberGrant.getAccess();
    }

    protected abstract AccessMemberGrantMapping createAccessMemberGrant(AccessMember access, String member);

    protected HierarchyMapping accessHierarchyGrantHierarchy(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return hierarchy(accessHierarchyGrant.getHierarchy());
    }

    protected HierarchyMapping hierarchy(HierarchyMapping hierarchy) {
        if (hierarchy != null) {
            if (!hierarchyMap.containsKey(hierarchy)) {
                List<? extends AnnotationMapping> annotations = hierarchyAnnotations(hierarchy);
                String id = hierarchyId(hierarchy);
                String description = hierarchyDescription(hierarchy);
                String name = hierarchyName(hierarchy);

                List<? extends LevelMapping> levels = hierarchyLevels(hierarchy);
                List<? extends MemberReaderParameterMapping> memberReaderParameters = hierarchyMemberReaderParameters(
                    hierarchy);
                String allLevelName = hierarchyAllLevelName(hierarchy);
                String allMemberCaption = hierarchyAllMemberCaption(hierarchy);
                String allMemberName = hierarchyAllMemberName(hierarchy);
                String defaultMember = hierarchyDefaultMember(hierarchy);
                String displayFolder = hierarchyDisplayFolder(hierarchy);
                boolean hasAll = hierarchyHasAll(hierarchy);
                String memberReaderClass = hierarchyMemberReaderClass(hierarchy);
                String origin = hierarchyOrigin(hierarchy);
                ColumnMapping primaryKey = hierarchyPrimaryKey(hierarchy);
                String uniqueKeyLevelName = hierarchyUniqueKeyLevelName(hierarchy);
                boolean visible = hierarchyVisible(hierarchy);
                QueryMapping query = hierarchyQuery(hierarchy);

                HierarchyMapping h = createHierarchy(annotations, id, description, name, levels,
                    memberReaderParameters,
                    allLevelName, allMemberCaption, allMemberName, defaultMember, displayFolder, hasAll,
                    memberReaderClass, origin, primaryKey, uniqueKeyLevelName, visible, query);
                hierarchyMap.put(hierarchy, h);
                return h;
            } else {
                return hierarchyMap.get(hierarchy);
            }

        }
        return null;
    }

    protected QueryMapping hierarchyQuery(HierarchyMapping hierarchy) {
        return query(hierarchy.getQuery());
    }

    protected QueryMapping query(QueryMapping query) {
        QueryMapping q = null;
        if (query != null) {
            if (!queryMap.containsKey(query)) {
                if (query instanceof TableQueryMapping tq) {
                    q = tableQuery(tq);
                    queryMap.put(query, q);
                }
                if (query instanceof SqlSelectQueryMapping ssq) {
                    q = sqlSelectQuery(ssq);
                    queryMap.put(query, q);
                }
                if (query instanceof JoinQueryMapping jq) {
                    q = joinQuery(jq);
                    queryMap.put(query, q);
                }
                if (query instanceof InlineTableQueryMapping itq) {
                    q = inlineTableQuery(itq);
                    queryMap.put(query, q);
                }
            } else {
                return queryMap.get(query);
            }
        }
        return q;
    }

    protected QueryMapping inlineTableQuery(InlineTableQueryMapping itq) {
        if (itq != null) {
            String alias = inlineTableQueryAlias(itq);
            InlineTableMapping table = inlineTableInlineTable(itq);
            String id = inlineTableId(itq);
            return createInlineTableQuery(alias, table, id );
        }
        return null;
    }

    private String inlineTableId(InlineTableQueryMapping itq) {
        return itq.getId();
    }

    private InlineTableMapping inlineTableInlineTable(InlineTableQueryMapping itq) {
        return inlineTable(itq.getTable());
    }

    protected abstract QueryMapping createInlineTableQuery(
        String alias,
        InlineTableMapping table,
        String id
    );

    protected String inlineTableQueryAlias(InlineTableQueryMapping itq) {
        return itq.getAlias();
    }

    protected QueryMapping joinQuery(JoinQueryMapping jq) {
        if (jq != null) {
            JoinedQueryElementMapping left = joinQueryLeft(jq);
            JoinedQueryElementMapping right = joinQueryRight(jq);
            String id = joinQueryId(jq);
            return createJoinQuery(left, right, id);
        }
        return null;
    }

    protected String joinQueryId(JoinQueryMapping jq) {
        return jq.getId();
    }

    protected abstract QueryMapping createJoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right, String id);

    protected JoinedQueryElementMapping joinQueryRight(JoinQueryMapping jq) {
        return joinedQueryElement(jq.getRight());
    }

    protected JoinedQueryElementMapping joinedQueryElement(JoinedQueryElementMapping joinedQueryElement) {
        if (joinedQueryElement != null) {
            String alias = joinedQueryElementAlias(joinedQueryElement);
            ColumnMapping key = joinedQueryElementKey(joinedQueryElement);
            QueryMapping query = joinedQueryElementQuery(joinedQueryElement);
            return createJoinedQueryElement(alias, key, query);
        }
        return null;
    }

    protected String joinedQueryElementAlias(JoinedQueryElementMapping joinedQueryElement) {
        return joinedQueryElement.getAlias();
    }

    protected ColumnMapping joinedQueryElementKey(JoinedQueryElementMapping joinedQueryElement) {
        return column(joinedQueryElement.getKey());
    }

    protected QueryMapping joinedQueryElementQuery(JoinedQueryElementMapping joinedQueryElement) {
        return query(joinedQueryElement.getQuery());
    }

    protected abstract JoinedQueryElementMapping createJoinedQueryElement(String alias, ColumnMapping key, QueryMapping query);

    protected JoinedQueryElementMapping joinQueryLeft(JoinQueryMapping jq) {
        return joinedQueryElement(jq.getLeft());
    }

    protected QueryMapping sqlSelectQuery(SqlSelectQueryMapping ssq) {
        if (ssq != null) {
            String alias = sqlSelectQueryAlias(ssq);
            SqlViewMapping sql = sqlSelectQuerySqlView(ssq);
            String id = sqlSelectQueryId(ssq);
            return createSqlSelectQuery(alias, sql, id);
        }
        return null;
    }

    private String sqlSelectQueryId(SqlSelectQueryMapping ssq) {
        return ssq.getId();
    }

    private SqlViewMapping sqlSelectQuerySqlView(SqlSelectQueryMapping ssq) {
        return sqlView(ssq.getSql());
    }

    protected abstract QueryMapping createSqlSelectQuery(String alias, SqlViewMapping sql, String id);

    protected List<SqlStatementMapping> sqls(List<? extends SqlStatementMapping> sqls) {
        if (sqls != null) {
            return sqls.stream().map(this::sqlStatement).toList();
        }
        return List.of();
    }

    protected String sqlSelectQueryAlias(SqlSelectQueryMapping ssq) {
        return ssq.getAlias();
    }

    protected TableQueryMapping tableQuery(TableQueryMapping tableQuery) {
        if (tableQuery != null) {
            String alias = tableQueryAlias(tableQuery);
            SqlStatementMapping sqlWhereExpression = tableQuerySqlWhereExpression(tableQuery);

            List<? extends AggregationExcludeMapping> aggregationExcludes = tableQueryAggregationExcludes(tableQuery);

            List<? extends TableQueryOptimizationHintMapping> optimizationHints = tableQueryOptimizationHints(
                tableQuery);

            TableMapping table = tableTable(tableQuery);

            List<? extends AggregationTableMapping> aggregationTables = tableQueryAggregationTables(tableQuery);

            String id = tableId(tableQuery);

            return createTableQuery(alias, sqlWhereExpression, aggregationExcludes, optimizationHints, table,
                aggregationTables, id);
        }
        return null;

    }

    protected String tableId(TableQueryMapping tableQuery) {
        return tableQuery.getId();
    }

    protected TableMapping tableTable(TableQueryMapping tableQuery) {
        return table(tableQuery.getTable());
    }

    protected abstract TableQueryMapping createTableQuery(
        String alias, SqlStatementMapping sqlWhereExpression,
        List<? extends AggregationExcludeMapping> aggregationExcludes,
        List<? extends TableQueryOptimizationHintMapping> optimizationHints, TableMapping table,
        List<? extends AggregationTableMapping> aggregationTables, String id
    );

    protected List<? extends AggregationTableMapping> tableQueryAggregationTables(TableQueryMapping tableQuery) {
        return aggregationTables(tableQuery.getAggregationTables());
    }

    protected List<AggregationTableMapping> aggregationTables(
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        if (aggregationTables != null) {
            return aggregationTables.stream().map(this::aggregationTable).toList();
        }
        return List.of();
    }

    protected AggregationTableMapping aggregationTable(AggregationTableMapping aggregationTable) {
        if (aggregationTable != null) {
            if (!aggregationTableMap.containsKey(aggregationTable)) {
                AggregationColumnNameMapping aggregationFactCount =
                    aggregationTableAggregationFactCount(aggregationTable);
                List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns =
                    aggregationTableAggregationIgnoreColumns(
                        aggregationTable);
                List<? extends AggregationForeignKeyMapping> aggregationForeignKeys =
                    aggregationTableAggregationForeignKeys(
                        aggregationTable);
                List<? extends AggregationMeasureMapping> aggregationMeasures = aggregationTableAggregationMeasures(
                    aggregationTable);
                List<? extends AggregationLevelMapping> aggregationLevels = aggregationTableAggregationLevels(
                    aggregationTable);
                List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts =
                    aggregationTableAggregationMeasureFactCounts(
                        aggregationTable);
                boolean ignorecase = aggregationTableIgnorecase(aggregationTable);
                String id = aggregationTableId(aggregationTable);
                if (aggregationTable instanceof AggregationNameMapping an) {
                    String approxRowCount = aggregationNameApproxRowCount(an);
                    TableMapping name = aggregationNameName(an);
                    AggregationTableMapping at = createAggregationName(aggregationFactCount, aggregationIgnoreColumns
                        , aggregationForeignKeys,
                        aggregationMeasures, aggregationLevels, aggregationMeasureFactCounts, ignorecase, id,
                        approxRowCount, name);
                    aggregationTableMap.put(aggregationTable, at);
                    return at;
                }
                if (aggregationTable instanceof AggregationPatternMapping ap) {
                    String pattern = aggregationPatternPattern(ap);
                    List<? extends AggregationExcludeMapping> excludes = aggregationPatternExcludes(ap);
                    AggregationTableMapping at = createAggregationPattern(aggregationFactCount,
                        aggregationIgnoreColumns, aggregationForeignKeys,
                        aggregationMeasures, aggregationLevels, aggregationMeasureFactCounts, ignorecase, id, pattern,
                        excludes);
                    aggregationTableMap.put(aggregationTable, at);
                    return at;

                }
            } else {
                return aggregationTableMap.get(aggregationTable);
            }
        }
        return null;
    }

    protected abstract AggregationTableMapping createAggregationPattern(
        AggregationColumnNameMapping aggregationFactCount,
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
        List<? extends AggregationMeasureMapping> aggregationMeasures,
        List<? extends AggregationLevelMapping> aggregationLevels,
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
        String id, String pattern, List<? extends AggregationExcludeMapping> excludes
    );

    protected List<? extends AggregationExcludeMapping> aggregationPatternExcludes(AggregationPatternMapping ap) {
        return ap.getExcludes();
    }

    protected String aggregationPatternPattern(AggregationPatternMapping ap) {
        return ap.getPattern();
    }

    protected abstract AggregationTableMapping createAggregationName(
        AggregationColumnNameMapping aggregationFactCount,
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
        List<? extends AggregationMeasureMapping> aggregationMeasures,
        List<? extends AggregationLevelMapping> aggregationLevels,
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
        String id, String approxRowCount, TableMapping name
    );

    protected TableMapping aggregationNameName(AggregationNameMapping an) {
        return table(an.getName());
    }

    protected String aggregationNameApproxRowCount(AggregationNameMapping an) {
        return an.getApproxRowCount();
    }

    protected String aggregationTableId(AggregationTableMapping aggregationTable) {
        return aggregationTable.getId();
    }

    protected boolean aggregationTableIgnorecase(AggregationTableMapping aggregationTable) {
        return aggregationTable.isIgnorecase();
    }

    protected List<? extends AggregationMeasureFactCountMapping> aggregationTableAggregationMeasureFactCounts(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationMeasureFactCounts(aggregationTable.getAggregationMeasureFactCounts());
    }

    protected List<AggregationMeasureFactCountMapping> aggregationMeasureFactCounts(
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts
    ) {
        if (aggregationMeasureFactCounts != null) {
            return aggregationMeasureFactCounts.stream().map(this::aggregationMeasureFactCount).toList();
        }
        return List.of();
    }

    protected AggregationMeasureFactCountMapping aggregationMeasureFactCount(
        AggregationMeasureFactCountMapping aggregationMeasureFactCount
    ) {
        if (aggregationMeasureFactCount != null) {
            ColumnMapping column = aggregationMeasureFactCountColumn(aggregationMeasureFactCount);
            ColumnMapping factColumn = aggregationMeasureFactCountFactColumn(aggregationMeasureFactCount);
            return createAggregationMeasureFactCount(column, factColumn);
        }
        return null;
    }

    protected abstract AggregationMeasureFactCountMapping createAggregationMeasureFactCount(
        ColumnMapping column,
        ColumnMapping factColumn
    );

    protected ColumnMapping  aggregationMeasureFactCountFactColumn(
        AggregationMeasureFactCountMapping aggregationMeasureFactCount
    ) {
        return column(aggregationMeasureFactCount.getFactColumn());
    }

    protected ColumnMapping  aggregationMeasureFactCountColumn(AggregationMeasureFactCountMapping aggregationMeasureFactCount) {
        return column(aggregationMeasureFactCount.getColumn());
    }

    protected List<? extends AggregationLevelMapping> aggregationTableAggregationLevels(
        AggregationTableMapping aggregationTable
    ) {
        return AggregationLevels(aggregationTable.getAggregationLevels());
    }

    protected List<AggregationLevelMapping> AggregationLevels(
        List<? extends AggregationLevelMapping> aggregationLevels
    ) {
        if (aggregationLevels != null) {
            return aggregationLevels.stream().map(this::aggregationLevel).toList();
        }
        return List.of();
    }

    protected AggregationLevelMapping aggregationLevel(AggregationLevelMapping aggregationLevel) {
        if (aggregationLevel != null) {
            List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties =
                aggregationLevelAggregationLevelProperties(
                    aggregationLevel);
            ColumnMapping captionColumn = aggregationLevelCaptionColumn(aggregationLevel);
            boolean collapsed = aggregationLevelCollapsed(aggregationLevel);
            ColumnMapping column = aggregationLevelColumn(aggregationLevel);
            String name = aggregationLevelName(aggregationLevel);
            ColumnMapping nameColumn = aggregationLevelNameColumn(aggregationLevel);
            ColumnMapping ordinalColumn = aggregationLevelOrdinalColumn(aggregationLevel);
            return createAggregationLevel(aggregationLevelProperties, captionColumn, collapsed, column, name,
                nameColumn,
                ordinalColumn);
        }
        return null;
    }

    protected List<? extends AggregationLevelPropertyMapping> aggregationLevelAggregationLevelProperties(
        AggregationLevelMapping aggregationLevel
    ) {
        return aggregationLevelProperties(aggregationLevel.getAggregationLevelProperties());
    }

    protected List<AggregationLevelPropertyMapping> aggregationLevelProperties(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties
    ) {
        if (aggregationLevelProperties != null) {
            return aggregationLevelProperties.stream().map(this::aggregationLevelProperty).toList();
        }
        return List.of();
    }

    protected AggregationLevelPropertyMapping aggregationLevelProperty(
        AggregationLevelPropertyMapping aggregationLevelProperty
    ) {
        if (aggregationLevelProperty != null) {
            ColumnMapping column = aggregationLevelPropertyColumn(aggregationLevelProperty);
            String name = aggregationLevelPropertyName(aggregationLevelProperty);
            return createAggregationLevelProperty(column, name);
        }
        return null;
    }

    protected abstract AggregationLevelPropertyMapping createAggregationLevelProperty(ColumnMapping column, String name);

    protected String aggregationLevelPropertyName(AggregationLevelPropertyMapping aggregationLevelProperty) {
        return aggregationLevelProperty.getName();
    }

    protected ColumnMapping aggregationLevelPropertyColumn(AggregationLevelPropertyMapping aggregationLevelProperty) {
        return column(aggregationLevelProperty.getColumn());
    }

    protected ColumnMapping aggregationLevelOrdinalColumn(AggregationLevelMapping aggregationLevel) {
        return column(aggregationLevel.getOrdinalColumn());
    }

    protected ColumnMapping aggregationLevelNameColumn(AggregationLevelMapping aggregationLevel) {
        return column(aggregationLevel.getNameColumn());
    }

    protected String aggregationLevelName(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getName();
    }

    protected ColumnMapping  aggregationLevelColumn(AggregationLevelMapping aggregationLevel) {
        return column(aggregationLevel.getColumn());
    }

    protected boolean aggregationLevelCollapsed(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.isCollapsed();
    }

    protected ColumnMapping  aggregationLevelCaptionColumn(AggregationLevelMapping aggregationLevel) {
        return column(aggregationLevel.getCaptionColumn());
    }

    protected abstract AggregationLevelMapping createAggregationLevel(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, ColumnMapping captionColumn,
        boolean collapsed, ColumnMapping column, String name, ColumnMapping nameColumn, ColumnMapping ordinalColumn
    );

    protected List<? extends AggregationMeasureMapping> aggregationTableAggregationMeasures(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationMeasures(aggregationTable.getAggregationMeasures());
    }

    protected List<AggregationMeasureMapping> aggregationMeasures(
        List<? extends AggregationMeasureMapping> aggregationMeasures
    ) {
        if (aggregationMeasures != null) {
            return aggregationMeasures.stream().map(this::aggregationMeasure).toList();
        }
        return List.of();
    }

    protected AggregationMeasureMapping aggregationMeasure(AggregationMeasureMapping aggregationMeasure) {
        if (aggregationMeasure != null) {
            ColumnMapping column = aggregationMeasureColumn(aggregationMeasure);
            String name = aggregationMeasureName(aggregationMeasure);
            String rollupType = aggregationMeasureRollupType(aggregationMeasure);
            return createAggregationMeasure(column, name, rollupType);
        }
        return null;
    }

    protected abstract AggregationMeasureMapping createAggregationMeasure(
        ColumnMapping column,
        String name,
        String rollupType
    );

    protected String aggregationMeasureRollupType(AggregationMeasureMapping aggregationMeasure) {
        return aggregationMeasure.getRollupType();
    }

    protected String aggregationMeasureName(AggregationMeasureMapping aggregationMeasure) {
        return aggregationMeasure.getName();
    }

    protected ColumnMapping aggregationMeasureColumn(AggregationMeasureMapping aggregationMeasure) {
        return column(aggregationMeasure.getColumn());
    }

    protected List<? extends AggregationForeignKeyMapping> aggregationTableAggregationForeignKeys(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationForeignKeys(aggregationTable.getAggregationForeignKeys());
    }

    protected List<AggregationForeignKeyMapping> aggregationForeignKeys(
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys
    ) {
        if (aggregationForeignKeys != null) {
            return aggregationForeignKeys.stream().map(this::aggregationForeignKey).toList();
        }
        return List.of();
    }

    protected AggregationForeignKeyMapping aggregationForeignKey(AggregationForeignKeyMapping aggregationForeignKey) {
        if (aggregationForeignKey != null) {
            ColumnMapping aggregationColumn = aggregationForeignKeyAggregationColumn(aggregationForeignKey);
            ColumnMapping factColumn = aggregationForeignKeyFactColumn(aggregationForeignKey);
            return createAggregationForeignKey(aggregationColumn, factColumn);
        }
        return null;
    }

    protected ColumnMapping aggregationForeignKeyFactColumn(AggregationForeignKeyMapping aggregationForeignKey) {
        return column(aggregationForeignKey.getFactColumn());
    }

    protected ColumnMapping aggregationForeignKeyAggregationColumn(AggregationForeignKeyMapping aggregationForeignKey) {
        return column(aggregationForeignKey.getAggregationColumn());
    }

    protected abstract AggregationForeignKeyMapping createAggregationForeignKey(
        ColumnMapping aggregationColumn,
        ColumnMapping factColumn
    );

    protected List<? extends AggregationColumnNameMapping> aggregationTableAggregationIgnoreColumns(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationColumnNames(aggregationTable.getAggregationIgnoreColumns());
    }

    protected List<AggregationColumnNameMapping> aggregationColumnNames(
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns
    ) {
        if (aggregationIgnoreColumns != null) {
            return aggregationIgnoreColumns.stream().map(this::aggregationColumnName).toList();
        }
        return List.of();
    }

    protected AggregationColumnNameMapping aggregationTableAggregationFactCount(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationColumnName(aggregationTable.getAggregationFactCount());
    }

    protected AggregationColumnNameMapping aggregationColumnName(AggregationColumnNameMapping aggregationColumnName) {
        if (aggregationColumnName != null) {
            ColumnMapping column = aggregationColumnNameColumn(aggregationColumnName);
            return createAggregationColumn(column);
        }
        return null;
    }

    protected ColumnMapping aggregationColumnNameColumn(AggregationColumnNameMapping aggregationColumnName) {
        return column(aggregationColumnName.getColumn());
    }

    protected abstract AggregationColumnNameMapping createAggregationColumn(ColumnMapping column);

    protected TableMapping tableQueryTable(TableQueryMapping tableQuery) {
        return table(tableQuery.getTable());
    }

    protected List<? extends TableQueryOptimizationHintMapping> tableQueryOptimizationHints(
        TableQueryMapping tableQuery
    ) {
        return tableQueryOptimizationHints(tableQuery.getOptimizationHints());
    }

    protected List<TableQueryOptimizationHintMapping> tableQueryOptimizationHints(
        List<? extends TableQueryOptimizationHintMapping> optimizationHints
    ) {
        if (optimizationHints != null) {
            return optimizationHints.stream().map(this::tableQueryOptimizationHint).toList();
        }
        return List.of();
    }

    protected TableQueryOptimizationHintMapping tableQueryOptimizationHint(
        TableQueryOptimizationHintMapping tableQueryOptimizationHint
    ) {
        if (tableQueryOptimizationHint != null) {
            String value = tableQueryOptimizationHintValue(tableQueryOptimizationHint);
            String type = tableQueryOptimizationHintType(tableQueryOptimizationHint);
            return createTableQueryOptimizationHint(value, type);
        }
        return null;
    }

    protected String tableQueryOptimizationHintType(TableQueryOptimizationHintMapping tableQueryOptimizationHint) {
        return tableQueryOptimizationHint.getType();
    }

    protected String tableQueryOptimizationHintValue(TableQueryOptimizationHintMapping tableQueryOptimizationHint) {
        return tableQueryOptimizationHint.getValue();
    }

    protected abstract TableQueryOptimizationHintMapping createTableQueryOptimizationHint(String value, String type);

    protected List<? extends AggregationExcludeMapping> tableQueryAggregationExcludes(TableQueryMapping tableQuery) {
        return aggregationExcludes(tableQuery.getAggregationExcludes());
    }

    protected List<AggregationExcludeMapping> aggregationExcludes(
        List<? extends AggregationExcludeMapping> aggregationExcludes
    ) {
        if (aggregationExcludes != null) {
            return aggregationExcludes.stream().map(this::aggregationExclude).toList();
        }
        return List.of();
    }

    protected AggregationExcludeMapping aggregationExclude(AggregationExcludeMapping aggregationExclude) {
        if (aggregationExclude != null) {
            if (!aggregationExcludeMap.containsKey(aggregationExclude)) {
                boolean ignorecase = aggregationExcludeIgnorecase(aggregationExclude);
                String name = aggregationExcludeName(aggregationExclude);
                String pattern = aggregationExcludePattern(aggregationExclude);
                String id = aggregationExcludeId(aggregationExclude);
                AggregationExcludeMapping ae = createAggregationExclude(ignorecase, name, pattern, id);
                aggregationExcludeMap.put(aggregationExclude, ae);
                return ae;
            } else {
                return aggregationExcludeMap.get(aggregationExclude);
            }
        }
        return null;
    }

    protected abstract AggregationExcludeMapping createAggregationExclude(
        boolean ignorecase, String name, String pattern,
        String id
    );

    protected String aggregationExcludeId(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getId();
    }

    protected String aggregationExcludePattern(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getPattern();
    }

    protected String aggregationExcludeName(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getName();
    }

    protected boolean aggregationExcludeIgnorecase(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.isIgnorecase();
    }

    protected SqlStatementMapping tableQuerySqlWhereExpression(TableQueryMapping tableQuery) {
        return sqlStatement(tableQuery.getSqlWhereExpression());
    }

    protected SqlStatementMapping sqlStatement(SqlStatementMapping sqlStatement) {
        if (sqlStatement != null) {
            List<String> dialects = sqlStatementDdialects(sqlStatement);
            String sql = sqlStatementSql(sqlStatement);
            return createSqlStatement(dialects, sql);
        }
        return null;
    }

    protected String sqlStatementToString(SqlStatementMapping sql) {
        return sql.getSql();
    }

    protected abstract SqlStatementMapping createSqlStatement(List<String> dialects, String statement);

    protected List<String> sqlDialects(SqlStatementMapping sql) {
        return dialects(sql.getDialects());
    }

    protected List<String> dialects(List<String> dialects) {
        if (dialects != null) {
            return dialects.stream().map(d -> d).toList();
        }
        return List.of();
    }

    protected String tableQueryAlias(TableQueryMapping tableQuery) {
        return tableQuery.getAlias();
    }

    protected boolean hierarchyVisible(HierarchyMapping hierarchy) {
        return hierarchy.isVisible();
    }

    protected String hierarchyUniqueKeyLevelName(HierarchyMapping hierarchy) {
        return hierarchy.getUniqueKeyLevelName();
    }

    protected ColumnMapping hierarchyPrimaryKey(HierarchyMapping hierarchy) {
        return column(hierarchy.getPrimaryKey());
    }

    protected String hierarchyOrigin(HierarchyMapping hierarchy) {
        return hierarchy.getOrigin();
    }

    protected String hierarchyMemberReaderClass(HierarchyMapping hierarchy) {
        return hierarchy.getMemberReaderClass();
    }

    protected boolean hierarchyHasAll(HierarchyMapping hierarchy) {
        return hierarchy.isHasAll();
    }

    protected String hierarchyDisplayFolder(HierarchyMapping hierarchy) {
        return hierarchy.getDisplayFolder();
    }

    protected String hierarchyDefaultMember(HierarchyMapping hierarchy) {
        return hierarchy.getDefaultMember();
    }

    protected String hierarchyAllMemberName(HierarchyMapping hierarchy) {
        return hierarchy.getAllMemberName();
    }

    protected String hierarchyAllMemberCaption(HierarchyMapping hierarchy) {
        return hierarchy.getAllMemberCaption();
    }

    protected String hierarchyAllLevelName(HierarchyMapping hierarchy) {
        return hierarchy.getAllLevelName();
    }

    protected List<? extends MemberReaderParameterMapping> hierarchyMemberReaderParameters(HierarchyMapping hierarchy) {
        return memberReaderParameters(hierarchy.getMemberReaderParameters());
    }

    protected List<MemberReaderParameterMapping> memberReaderParameters(
        List<? extends MemberReaderParameterMapping> memberReaderParameters
    ) {
        if (memberReaderParameters != null) {
            return memberReaderParameters.stream().map(this::memberReaderParameter).toList();
        }
        return List.of();
    }

    protected MemberReaderParameterMapping memberReaderParameter(MemberReaderParameterMapping memberReaderParameter) {
        if (memberReaderParameter != null) {
            String name = memberReaderParameterName(memberReaderParameter);
            String value = memberReaderParameterValue(memberReaderParameter);
            return createMemberReaderParameter(name, value);
        }
        return null;
    }

    protected String memberReaderParameterName(MemberReaderParameterMapping memberReaderParameter) {
        return memberReaderParameter.getName();
    }

    protected String memberReaderParameterValue(MemberReaderParameterMapping memberReaderParameter) {
        return memberReaderParameter.getValue();

    }

    protected abstract MemberReaderParameterMapping createMemberReaderParameter(String name, String value);

    protected List<? extends LevelMapping> hierarchyLevels(HierarchyMapping hierarchy) {
        return levels(hierarchy.getLevels());
    }

    protected List<LevelMapping> levels(List<? extends LevelMapping> levels) {
        if (levels != null) {
            return levels.stream().map(this::level).toList();
        }
        return List.of();
    }

    protected String hierarchyName(HierarchyMapping hierarchy) {
        return hierarchy.getName();
    }

    protected String hierarchyDescription(HierarchyMapping hierarchy) {
        return hierarchy.getDescription();
    }

    protected String hierarchyId(HierarchyMapping hierarchy) {
        return hierarchy.getId();
    }

    protected List<? extends AnnotationMapping> hierarchyAnnotations(HierarchyMapping hierarchy) {
        return annotations(hierarchy.getAnnotations());
    }

    protected abstract HierarchyMapping createHierarchy(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, List<? extends LevelMapping> levels,
        List<? extends MemberReaderParameterMapping> memberReaderParameters, String allLevelName,
        String allMemberCaption, String allMemberName, String defaultMember, String displayFolder, boolean hasAll,
        String memberReaderClass, String origin, ColumnMapping primaryKey,
        String uniqueKeyLevelName, boolean visible, QueryMapping query
    );

    protected LevelMapping accessHierarchyGrantTopLevel(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return level(accessHierarchyGrant.getTopLevel());
    }

    protected RollupPolicyType accessHierarchyGrantRollupPolicy(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return accessHierarchyGrant.getRollupPolicyType();
    }

    protected LevelMapping accessHierarchyGrantBottomLevel(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return level(accessHierarchyGrant.getBottomLevel());
    }

    protected LevelMapping level(LevelMapping level) {
        if (level != null) {
            if (!levelMap.containsKey(level)) {
                ParentChildLinkMapping parentChildLink = levelParentChildLink(level);
                List<? extends MemberPropertyMapping> memberProperties = levelMemberProperties(level);
                MemberFormatterMapping memberFormatter = levelMemberFormatter(level);
                String approxRowCount = levelApproxRowCount(level);
                ColumnMapping captionColumn = levelCaptionColumn(level);
                ColumnMapping column = levelColumn(level);
                HideMemberIfType hideMemberIf = levelHideMemberIf(level);
                LevelType levelType = levelLevelType(level);
                ColumnMapping nameColumn = levelNameColumn(level);
                String nullParentValue = levelNullParentValue(level);
                ColumnMapping ordinalColumn = levelOrdinalColumn(level);
                ColumnMapping parentColumn = levelParentColumn(level);
                InternalDataType type = levelType(level);
                boolean uniqueMembers = levelUniqueMembers(level);
                boolean visible = levelVisible(level);
                String name = levelName(level);
                String id = levelId(level);
                String description = levelDescription(level);
                return createLevel( parentChildLink, memberProperties, memberFormatter, approxRowCount,
                    captionColumn, column, hideMemberIf, levelType, nameColumn, nullParentValue, ordinalColumn,
                    parentColumn, type, uniqueMembers, visible, name, id, description);
            } else {
                return levelMap.get(level);
            }
        }
        return null;
    }

    private String levelDescription(LevelMapping level) {
        return level.getDescription();
    }

    protected String levelId(LevelMapping level) {
        return level.getId();
    }

    protected String levelName(LevelMapping level) {
        return level.getName();
    }

    protected boolean levelVisible(LevelMapping level) {
        return level.isVisible();
    }

    protected boolean levelUniqueMembers(LevelMapping level) {
        return level.isUniqueMembers();
    }

    protected InternalDataType levelType(LevelMapping level) {
        return level.getDataType();
    }

    protected ColumnMapping levelParentColumn(LevelMapping level) {
        return column(level.getParentColumn());
    }

    protected ColumnMapping levelOrdinalColumn(LevelMapping level) {
        return column(level.getOrdinalColumn());
    }

    protected String levelNullParentValue(LevelMapping level) {
        return level.getNullParentValue();
    }

    protected ColumnMapping levelNameColumn(LevelMapping level) {
        return column(level.getNameColumn());
    }

    protected LevelType levelLevelType(LevelMapping level) {
        return level.getLevelType();
    }

    protected HideMemberIfType levelHideMemberIf(LevelMapping level) {
        return level.getHideMemberIfType();
    }

    protected ColumnMapping levelColumn(LevelMapping level) {
        return column(level.getColumn());
    }

    protected ColumnMapping levelCaptionColumn(LevelMapping level) {
        return column(level.getCaptionColumn());
    }

    protected String levelApproxRowCount(LevelMapping level) {
        return level.getApproxRowCount();
    }

    protected MemberFormatterMapping levelMemberFormatter(LevelMapping level) {
        return memberFormatter(level.getMemberFormatter());
    }

    protected MemberFormatterMapping memberFormatter(MemberFormatterMapping memberFormatter) {
        if (memberFormatter != null) {
            if (!formatterMap.containsKey(memberFormatter)) {
                List<? extends AnnotationMapping> annotations = memberFormatterAnnotations(memberFormatter);
                String id = memberFormatterId(memberFormatter);
                String description = memberFormatterDescription(memberFormatter);
                String name = memberFormatterName(memberFormatter);
                String ref = memberFormatterRef(memberFormatter);
                MemberFormatterMapping mf = createMemberFormatter(annotations, id, description, name,
                    ref);
                formatterMap.put(memberFormatter, mf);
                return mf;
            } else {
                return (MemberFormatterMapping) formatterMap.get(memberFormatter);
            }
        }
        return null;
    }

    protected abstract MemberFormatterMapping createMemberFormatter(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name, String ref
    );

    protected String memberFormatterRef(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getRef();
    }

    protected String memberFormatterName(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getName();
    }

    protected String memberFormatterDescription(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getDescription();
    }

    protected String memberFormatterId(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getId();
    }

    protected List<? extends AnnotationMapping> memberFormatterAnnotations(MemberFormatterMapping memberFormatter) {
        return annotations(memberFormatter.getAnnotations());
    }

    protected List<? extends MemberPropertyMapping> levelMemberProperties(LevelMapping level) {
        return memberProperties(level.getMemberProperties());
    }

    protected List<MemberPropertyMapping> memberProperties(
        List<? extends MemberPropertyMapping> memberProperties
    ) {
        if (memberProperties != null) {
            return memberProperties.stream().map(this::memberProperty).toList();
        }
        return List.of();
    }

    protected MemberPropertyMapping memberProperty(MemberPropertyMapping memberProperty) {
        if (memberProperty != null) {
            List<? extends AnnotationMapping> annotations = memberPropertyAnnotations(memberProperty);
            String id = memberPropertyId(memberProperty);
            String description = memberPropertyDescription(memberProperty);
            String name = memberPropertyName(memberProperty);

            MemberPropertyFormatterMapping formatter = memberPropertyFormatter(memberProperty);
            ColumnMapping column = memberPropertyColumn(memberProperty);
            boolean dependsOnLevelValue = memberPropertyDependsOnLevelValue(memberProperty);
            InternalDataType type = memberDataType(memberProperty);

            return createMemberProperty(annotations, id, description, name, formatter, column,
                dependsOnLevelValue, type);
        }
        return null;
    }

    private ColumnMapping memberPropertyColumn(MemberPropertyMapping memberProperty) {
        return column(memberProperty.getColumn());
    }

    protected abstract MemberPropertyMapping createMemberProperty(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name,
        MemberPropertyFormatterMapping formatter, ColumnMapping column, boolean dependsOnLevelValue, InternalDataType type
    );

    protected InternalDataType memberDataType(MemberPropertyMapping memberProperty) {
        return memberProperty.getDataType();
    }

    protected boolean memberPropertyDependsOnLevelValue(MemberPropertyMapping memberProperty) {
        return memberProperty.isDependsOnLevelValue();
    }

    protected MemberPropertyFormatterMapping memberPropertyFormatter(MemberPropertyMapping memberProperty) {
        return memberPropertyFormatter(memberProperty.getFormatter());
    }

    private MemberPropertyFormatterMapping memberPropertyFormatter(MemberPropertyFormatterMapping memberPropertyFormatter) {
        if (memberPropertyFormatter != null) {
            if (!formatterMap.containsKey(memberPropertyFormatter)) {
                List<? extends AnnotationMapping> annotations =
                    memberPropertyFormatterAnnotations(memberPropertyFormatter);
                String id = memberPropertyFormatterId(memberPropertyFormatter);
                String description = memberPropertyFormatterDescription(memberPropertyFormatter);
                String name = memberPropertyFormatterName(memberPropertyFormatter);
                String ref = memberPropertyFormatterRef(memberPropertyFormatter);
                MemberPropertyFormatterMapping mf = createMemberPropertyFormatter(annotations, id, description, name, ref);
                formatterMap.put(memberPropertyFormatter, mf);
                return mf;
            } else {
                return (MemberPropertyFormatterMapping) formatterMap.get(memberPropertyFormatter);
            }
        }
        return null;
    }

    protected abstract MemberPropertyFormatterMapping createMemberPropertyFormatter(
        List<? extends AnnotationMapping> annotations, String id, String description, String name,
        String ref
    );

    private String memberPropertyFormatterRef(MemberPropertyFormatterMapping memberPropertyFormatter) {
        return memberPropertyFormatter.getRef();
    }

    private String memberPropertyFormatterName(MemberPropertyFormatterMapping memberPropertyFormatter) {
        return memberPropertyFormatter.getName();
    }

    private String memberPropertyFormatterDescription(MemberPropertyFormatterMapping memberPropertyFormatter) {
        return memberPropertyFormatter.getDescription();
    }

    private String memberPropertyFormatterId(MemberPropertyFormatterMapping memberPropertyFormatter) {
        return memberPropertyFormatter.getId();
    }

    private List<? extends AnnotationMapping> memberPropertyFormatterAnnotations(
        MemberPropertyFormatterMapping memberPropertyFormatter
    ) {
        return annotations(memberPropertyFormatter.getAnnotations());
    }

    protected String memberPropertyName(MemberPropertyMapping memberProperty) {
        return memberProperty.getName();
    }

    protected String memberPropertyDescription(MemberPropertyMapping memberProperty) {

        return memberProperty.getDescription();
    }

    protected String memberPropertyId(MemberPropertyMapping memberProperty) {
        return memberProperty.getId();
    }

    protected List<? extends AnnotationMapping> memberPropertyAnnotations(MemberPropertyMapping memberProperty) {
        return annotations(memberProperty.getAnnotations());
    }

    protected ParentChildLinkMapping levelParentChildLink(LevelMapping level) {
        return parentChildLink(level.getParentChildLink());
    }

    protected ParentChildLinkMapping parentChildLink(ParentChildLinkMapping parentChildLink) {
        if (parentChildLink != null) {
            TableQueryMapping table = parentChildLinkTable(parentChildLink);
            ColumnMapping childColumn = parentChildLinkChildColumn(parentChildLink);
            ColumnMapping parentColumn = parentChildLinkParentColumn(parentChildLink);
            return createParentChildLink(table, childColumn, parentColumn);
        }
        return null;
    }

    protected ColumnMapping parentChildLinkParentColumn(ParentChildLinkMapping parentChildLink) {
        return column(parentChildLink.getParentColumn());
    }

    protected ColumnMapping parentChildLinkChildColumn(ParentChildLinkMapping parentChildLink) {
        return column(parentChildLink.getChildColumn());
    }

    protected TableQueryMapping parentChildLinkTable(ParentChildLinkMapping parentChildLink) {
        return tableQuery(parentChildLink.getTable());
    }

    protected abstract ParentChildLinkMapping createParentChildLink(
        TableQueryMapping table, ColumnMapping childColumn,
        ColumnMapping parentColumn
    );

    protected SQLExpressionColumnMapping sqlExpression(SQLExpressionColumnMapping sqlExpression) {
        if (sqlExpression != null) {
            List<? extends SqlStatementMapping> sqls = sqlExpressionSqls(sqlExpression);
            String name = columnName(sqlExpression);
            TableMapping table = columnTable(sqlExpression);
            ColumnDataType type = columnDataType(sqlExpression);
            Integer columnSize = columnColumnSize(sqlExpression);
            Integer decimalDigits = columnDecimalDigits(sqlExpression);
            Integer numPrecRadix = columnNumPrecRadix(sqlExpression);
            Integer charOctetLength = columnCharOctetLength(sqlExpression);
            Boolean nullable = columnNullable(sqlExpression);
            String description = columnDescription(sqlExpression);
            return createSQLExpression(sqls, name, table, type, columnSize, decimalDigits, numPrecRadix, charOctetLength, nullable, description);
        }
        return null;
    }

    protected abstract SQLExpressionColumnMapping createSQLExpression(List<? extends SqlStatementMapping> sqls, String name, TableMapping table, ColumnDataType type, Integer columnSize, Integer decimalDigits,
            Integer numPrecRadix, Integer charOctetLength, Boolean nullable, String description);

    protected List<? extends SqlStatementMapping> sqlExpressionSqls(SQLExpressionColumnMapping sqlExpression) {
        return sqls(sqlExpression.getSqls());
    }

    protected abstract LevelMapping createLevel(
        ParentChildLinkMapping parentChildLink,
        List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
        String approxRowCount, ColumnMapping captionColumn, ColumnMapping column, HideMemberIfType hideMemberIf,
        LevelType levelType, ColumnMapping nameColumn, String nullParentValue, ColumnMapping ordinalColumn, ColumnMapping parentColumn,
        InternalDataType type, boolean uniqueMembers, boolean visible, String name, String id, String description
    );

    protected AccessHierarchy accessHierarchyGrantAccess(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return accessHierarchyGrant.getAccess();
    }

    protected abstract AccessHierarchyGrantMapping createAccessHierarchyGrant(
        List<? extends AccessMemberGrantMapping> memberGrants, AccessHierarchy access, LevelMapping bottomLevel,
        RollupPolicyType rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy
    );

    protected CubeMapping accessCubeGrantCube(AccessCubeGrantMapping accessCubeGrant) {
        return cube(accessCubeGrant.getCube());
    }

    protected AccessCube accessCubeGrantAccess(AccessCubeGrantMapping accessCubeGrant) {
        return accessCubeGrant.getAccess();
    }

    protected List<? extends AccessDimensionGrantMapping> accessCubeGrantAccessDimension(
        AccessCubeGrantMapping accessCubeGrant
    ) {
        return accessDimensionGrants(accessCubeGrant.getDimensionGrants());
    }

    protected List<AccessDimensionGrantMapping> accessDimensionGrants(
        List<? extends AccessDimensionGrantMapping> accessDimensionGrants
    ) {
        if (accessDimensionGrants != null) {
            return accessDimensionGrants.stream().map(this::accessDimensionGrant).toList();
        }
        return List.of();
    }

    protected AccessDimensionGrantMapping accessDimensionGrant(AccessDimensionGrantMapping accessDimensionGrant) {
        if (accessDimensionGrant != null) {
            AccessDimension access = accessDimensionGrantAccess(accessDimensionGrant);
            DimensionMapping dimension = accessDimensionGrantDimension(accessDimensionGrant);

            return createAccessDimensionGrant(access, dimension);
        }
        return null;
    }

    protected DimensionMapping accessDimensionGrantDimension(AccessDimensionGrantMapping accessDimensionGrant) {
        return dimension(accessDimensionGrant.getDimension());
    }

    protected DimensionMapping dimension(DimensionMapping dimension) {
        DimensionMapping dm = null;
        if (dimension != null) {
            if (!dimensionMap.containsKey(dimension)) {
                List<? extends AnnotationMapping> annotations = dimensionAnnotations(dimension);
                String id = dimensionId(dimension);
                String description = dimensionDescription(dimension);
                String name = dimensionName(dimension);

                List<? extends HierarchyMapping> hierarchies = dimensionHierarchies(dimension);
                String usagePrefix = dimensionUsagePrefix(dimension);
                boolean visible = dimensionVisible(dimension);
                if (dimension instanceof StandardDimensionMapping) {
                    dm = createStandardDimension(annotations, id, description, name, hierarchies,
                        usagePrefix, visible);
                    dimensionMap.put(dimension, dm);
                }
                if (dimension instanceof TimeDimensionMapping) {
                    dm = createTimeDimension(annotations, id, description, name, hierarchies,
                        usagePrefix, visible);
                    dimensionMap.put(dimension, dm);
                }
            } else {
                return dimensionMap.get(dimension);
            }
        }
        return dm;
    }

    protected abstract TimeDimensionMapping createTimeDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    );

    protected abstract StandardDimensionMapping createStandardDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    );

    protected boolean dimensionVisible(DimensionMapping dimension) {
        return dimension.isVisible();
    }

    protected String dimensionUsagePrefix(DimensionMapping dimension) {
        return dimension.getUsagePrefix();
    }

    protected List<? extends HierarchyMapping> dimensionHierarchies(DimensionMapping dimension) {
        return hierarchies(dimension.getHierarchies());
    }

    protected List<HierarchyMapping> hierarchies(List<? extends HierarchyMapping> hierarchies) {
        if (hierarchies != null) {
            return hierarchies.stream().map(this::hierarchy).toList();
        }
        return List.of();
    }

    protected String dimensionName(DimensionMapping dimension) {
        return dimension.getName();
    }

    protected String dimensionDescription(DimensionMapping dimension) {
        return dimension.getDescription();
    }

    protected String dimensionId(DimensionMapping dimension) {
        return dimension.getId();
    }

    protected List<? extends AnnotationMapping> dimensionAnnotations(DimensionMapping dimension) {
        return annotations(dimension.getAnnotations());
    }

    protected AccessDimension accessDimensionGrantAccess(AccessDimensionGrantMapping accessDimensionGrant) {
        return accessDimensionGrant.getAccess();
    }

    protected abstract AccessDimensionGrantMapping createAccessDimensionGrant(
        AccessDimension access,
        DimensionMapping dimension
    );

    protected abstract AccessCubeGrantMapping createAccessCubeGrant(
        List<? extends AccessDimensionGrantMapping> dimensionGrants,
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants, AccessCube access, CubeMapping cube
    );

    protected AccessCatalog accessCatalogGrantAccess(AccessCatalogGrantMapping accessCatalogGrant) {
        return accessCatalogGrant.getAccess();
    }

    protected abstract AccessCatalogGrantMapping createAccessCatalogGrant(
        List<? extends AccessCubeGrantMapping> accessCubeGrant, AccessCatalog access
    );

    protected List<? extends AccessRoleMapping> catalogAccessRoles(CatalogMapping catalogMappingOriginal) {
        return accessRoles(catalogMappingOriginal.getAccessRoles());
    }

    protected List<AccessRoleMapping> accessRoles(List<? extends AccessRoleMapping> accessRoles) {
        if (accessRoles != null) {
            return accessRoles.stream().map(this::accessRole).toList();
        }
        return List.of();
    }

    protected List<? extends NamedSetMapping> catalogNamedSets(CatalogMapping catalogMappingOriginal) {
        return namedSets(catalogMappingOriginal.getNamedSets());
    }

    protected List<NamedSetMapping> namedSets(List<? extends NamedSetMapping> namedSets) {
        if (namedSets != null) {
            return namedSets.stream().map(this::namedSet).toList();
        }
        return List.of();
    }

    protected NamedSetMapping namedSet(NamedSetMapping namedSet) {
        if (namedSet != null) {
            List<? extends AnnotationMapping> annotations = namedSetAnnotations(namedSet);
            String id = namedSetId(namedSet);
            String description = namedSetDescription(namedSet);
            String name = namedSetName(namedSet);

            String displayFolder = namedSetDisplayFolder(namedSet);
            String formula = namedSetFormula(namedSet);
            return createNamedSet(annotations, id, description, name, displayFolder, formula);
        }
        return null;
    }

    protected abstract NamedSetMapping createNamedSet(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, String displayFolder, String formula
    );

    protected String namedSetFormula(NamedSetMapping namedSet) {
        return namedSet.getFormula();
    }

    protected String namedSetDisplayFolder(NamedSetMapping namedSet) {
        return namedSet.getDisplayFolder();
    }

    protected String namedSetName(NamedSetMapping namedSet) {
        return namedSet.getName();
    }

    protected String namedSetDescription(NamedSetMapping namedSet) {
        return namedSet.getDescription();
    }

    protected String namedSetId(NamedSetMapping namedSet) {
        return namedSet.getId();
    }

    protected List<? extends AnnotationMapping> namedSetAnnotations(NamedSetMapping namedSet) {
        return annotations(namedSet.getAnnotations());
    }

    protected List<? extends CubeMapping> catalogCubes(CatalogMapping catalogMappingOriginal) {
        return cubes(catalogMappingOriginal.getCubes());
    }

    protected List<CubeMapping> cubes(List<? extends CubeMapping> cubes) {
        if (cubes != null) {
            return cubes.stream().map(this::cube).toList();
        }
        return List.of();
    }

    protected CubeMapping cube(CubeMapping cube) {
        if (cube instanceof PhysicalCubeMapping pc) {
            return physicalCube(pc);
        }
        if (cube instanceof VirtualCubeMapping vc) {
            return virtualCube(vc);
        }
        return null;
    }

    protected VirtualCubeMapping virtualCube(VirtualCubeMapping cube) {
        if (cube != null) {
            if (!cubeMap.containsKey(cube)) {
                List<? extends AnnotationMapping> annotations = cubeAnnotations(cube);
                String id = cubeId(cube);
                String description = cubeDescription(cube);
                String name = cubeName(cube);

                List<? extends DimensionConnectorMapping> dimensionConnectors = cubeDimensionConnectors(cube);
                List<? extends CalculatedMemberMapping> calculatedMembers = cubeCalculatedMembers(cube);
                List<? extends NamedSetMapping> namedSets = cubeNamedSets(cube);
                List<? extends KpiMapping> kpis = cubeKpis(cube);
                MemberMapping defaultMeasure = cubeDefaultMeasure(cube);
                boolean enabled = cubeEnabled(cube);
                boolean visible = cubeVisible(cube);
                List<? extends MeasureMapping> referencedMeasures = virtualCubeReferencedMeasures(cube);
                List<? extends CalculatedMemberMapping> referencedCalculatedMembers =
                    virtualCubeReferencedCalculatedMembers(cube);
                List<? extends CubeConnectorMapping> cubeUsages = virtualCubeCubeUsages(cube);
                VirtualCubeMapping vc = createVirtualCube(annotations, id, description, name,
                    dimensionConnectors, calculatedMembers, namedSets, kpis, defaultMeasure, enabled, visible,
                    referencedMeasures, referencedCalculatedMembers, cubeUsages);
                cubeMap.put(cube, vc);
                return vc;
            } else {
                return (VirtualCubeMapping) cubeMap.get(cube);
            }

        }
        return null;
    }

    protected List<? extends CalculatedMemberMapping> virtualCubeReferencedCalculatedMembers(VirtualCubeMapping cube) {
        return calculatedMembers(cube.getReferencedCalculatedMembers());
    }

    protected List<? extends MeasureMapping> virtualCubeReferencedMeasures(VirtualCubeMapping cube) {
        return measures(cube.getReferencedMeasures());
    }

    protected PhysicalCubeMapping physicalCube(PhysicalCubeMapping cube) {
        if (cube != null) {
            if (!cubeMap.containsKey(cube)) {
                List<? extends AnnotationMapping> annotations = cubeAnnotations(cube);
                String id = cubeId(cube);
                String description = cubeDescription(cube);
                String name = cubeName(cube);
                List<? extends DimensionConnectorMapping> dimensionConnectors = cubeDimensionConnectors(cube);
                List<? extends CalculatedMemberMapping> calculatedMembers = cubeCalculatedMembers(cube);
                List<? extends NamedSetMapping> namedSets = cubeNamedSets(cube);
                List<? extends KpiMapping> kpis = cubeKpis(cube);
                MemberMapping defaultMeasure = cubeDefaultMeasure(cube);
                boolean enabled = cubeEnabled(cube);
                boolean visible = cubeVisible(cube);
                List<? extends MeasureGroupMapping> measureGroups = physicalCubeMeasureGroups(cube);
                QueryMapping query = physicalCubeQuery(cube);
                WritebackTableMapping writebackTable = physicalCubeWritebackTable(cube);
                List<? extends ActionMapping> action = physicalCubeAction(cube);
                boolean cache = physicalCubeCache(cube);
                PhysicalCubeMapping pc = createPhysicalCube(annotations, id, description, name,
                    dimensionConnectors, calculatedMembers, namedSets, kpis, defaultMeasure, enabled, visible,
                    measureGroups, query, writebackTable, action, cache);
                for (MeasureGroupMapping mg : measureGroups) {
                    ((MeasureGroupMappingImpl) mg).setPhysicalCube(pc);
                }
                for (CalculatedMemberMapping cm : calculatedMembers) {
                    ((CalculatedMemberMappingImpl) cm).setPhysicalCube(pc);
                }
                cubeMap.put(cube, pc);
                return pc;
            } else {
                return (PhysicalCubeMapping) cubeMap.get(cube);
            }

        }
        return null;
    }

    protected List<? extends CubeConnectorMapping> virtualCubeCubeUsages(VirtualCubeMapping vc) {
        return cubeConnectors(vc.getCubeUsages());
    }

    protected List<CubeConnectorMapping> cubeConnectors(List<? extends CubeConnectorMapping> cubeUsages) {
        if (cubeUsages != null) {
            return cubeUsages.stream().map(this::cubeConnector).toList();
        }
        return List.of();
    }

    protected CubeConnectorMapping cubeConnector(CubeConnectorMapping cubeConnector) {
        if (cubeConnector != null) {
            CubeMapping cube = cubeConnectorCube(cubeConnector);
            boolean ignoreUnrelatedDimensions = cubeConnectorIgnoreUnrelatedDimensions(cubeConnector);
            return createCubeConnector(cube, ignoreUnrelatedDimensions);
        }
        return null;
    }

    protected abstract CubeConnectorMapping createCubeConnector(CubeMapping cube, boolean ignoreUnrelatedDimensions);

    protected boolean cubeConnectorIgnoreUnrelatedDimensions(CubeConnectorMapping cubeConnector) {
        return cubeConnector.isIgnoreUnrelatedDimensions();
    }

    protected CubeMapping cubeConnectorCube(CubeConnectorMapping cubeConnector) {
        return cube(cubeConnector.getCube());
    }

    protected abstract VirtualCubeMapping createVirtualCube(
        List<? extends AnnotationMapping> annotations,
        String id,
        String description,
        String name,

        List<? extends DimensionConnectorMapping> dimensionConnectors,
        List<? extends CalculatedMemberMapping> calculatedMembers,
        List<? extends NamedSetMapping> namedSets,
        List<? extends KpiMapping> kpis,
        MemberMapping defaultMeasure,
        boolean enabled,
        boolean visible,
        List<? extends MeasureMapping> referencedMeasures,
        List<? extends CalculatedMemberMapping> referencedCalculatedMembers,
        List<? extends CubeConnectorMapping> cubeUsages
    );

    protected abstract PhysicalCubeMapping createPhysicalCube(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends DimensionConnectorMapping> dimensionConnectors,
        List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
        List<? extends KpiMapping> kpis, MemberMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureGroupMapping> measureGroups, QueryMapping query, WritebackTableMapping writebackTable,
        List<? extends ActionMapping> action, boolean cache
    );

    protected boolean physicalCubeCache(PhysicalCubeMapping pc) {
        return pc.isCache();
    }

    protected List<? extends ActionMapping> physicalCubeAction(PhysicalCubeMapping pc) {
        return actionMappings(pc.getAction());
    }

    protected List<ActionMapping> actionMappings(List<? extends ActionMapping> actions) {
        if (actions != null) {
            return actions.stream().map(this::actionMapping).toList();
        }
        return List.of();
    }

    protected ActionMapping actionMapping(ActionMapping actionMapping) {
        if (actionMapping != null && actionMapping instanceof DrillThroughActionMapping dta) {
            List<? extends AnnotationMapping> annotations = actionMappingAnnotations(actionMapping);
            String id = actionMappingId(actionMapping);
            String description = actionMappingDescription(actionMapping);
            String name = actionMappingName(actionMapping);

            List<? extends DrillThroughAttributeMapping> drillThroughAttribute =
                drillThroughActionDrillThroughAttribute(dta);
            List<? extends MeasureMapping> drillThroughMeasure = drillThroughActionDrillThroughMeasure(dta);
            boolean def = drillThroughActionDefault(dta);
            return createDrillThroughAction(annotations, id, description, name,
                drillThroughAttribute, drillThroughMeasure, def);
        }
        return null;
    }

    protected String actionMappingName(ActionMapping actionMapping) {
        return actionMapping.getName();
    }

    protected String actionMappingDescription(ActionMapping actionMapping) {
        return actionMapping.getDescription();
    }

    protected String actionMappingId(ActionMapping actionMapping) {
        return actionMapping.getId();
    }

    protected List<? extends AnnotationMapping> actionMappingAnnotations(ActionMapping actionMapping) {
        return annotations(actionMapping.getAnnotations());
    }

    protected abstract ActionMapping createDrillThroughAction(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends DrillThroughAttributeMapping> drillThroughAttribute,
        List<? extends MeasureMapping> drillThroughMeasure, boolean def
    );

    protected boolean drillThroughActionDefault(DrillThroughActionMapping dta) {
        return dta.isDefault();
    }

    protected List<? extends MeasureMapping> drillThroughActionDrillThroughMeasure(DrillThroughActionMapping dta) {
        return measureMappings(dta.getDrillThroughMeasure());
    }

    protected List<MeasureMapping> measureMappings(List<? extends MeasureMapping> measures) {
        if (measures != null) {
            return measures.stream().map(this::measure).toList();
        }
        return List.of();
    }

    protected List<? extends DrillThroughAttributeMapping> drillThroughActionDrillThroughAttribute(
        DrillThroughActionMapping dta
    ) {
        return drillThroughAttributes(dta.getDrillThroughAttribute());
    }

    protected List<DrillThroughAttributeMapping> drillThroughAttributes(
        List<? extends DrillThroughAttributeMapping> drillThroughAttributes
    ) {
        if (drillThroughAttributes != null) {
            return drillThroughAttributes.stream().map(this::drillThroughAttribute).toList();
        }
        return List.of();
    }

    protected DrillThroughAttributeMapping drillThroughAttribute(DrillThroughAttributeMapping drillThroughAttribute) {
        if (drillThroughAttribute != null) {
            DimensionMapping dimension = drillThroughAttributeDimension(drillThroughAttribute);
            HierarchyMapping hierarchy = drillThroughAttributeHierarchy(drillThroughAttribute);
            LevelMapping level = drillThroughAttributeLevel(drillThroughAttribute);
            String property = drillThroughAttributeProperty(drillThroughAttribute);
            return createDrillThroughAttribute(dimension, hierarchy, level, property);

        }
        return null;
    }

    protected DimensionMapping drillThroughAttributeDimension(DrillThroughAttributeMapping drillThroughAttribute) {
        return dimension(drillThroughAttribute.getDimension());
    }

    protected HierarchyMapping drillThroughAttributeHierarchy(DrillThroughAttributeMapping drillThroughAttribute) {
        return hierarchy(drillThroughAttribute.getHierarchy());
    }

    protected LevelMapping drillThroughAttributeLevel(DrillThroughAttributeMapping drillThroughAttribute) {
        return level(drillThroughAttribute.getLevel());
    }

    protected String drillThroughAttributeProperty(DrillThroughAttributeMapping drillThroughAttribute) {
        return drillThroughAttribute.getProperty();
    }

    protected abstract DrillThroughAttributeMapping createDrillThroughAttribute(
        DimensionMapping dimension, HierarchyMapping hierarchy,
        LevelMapping level, String property
    );

    protected WritebackTableMapping physicalCubeWritebackTable(PhysicalCubeMapping pc) {
        return writebackTable(pc.getWritebackTable());
    }

    protected WritebackTableMapping writebackTable(WritebackTableMapping writebackTable) {
        if (writebackTable != null) {
            List<? extends WritebackAttributeMapping> writebackAttribute =
                writebackTableWritebackAttribute(writebackTable);
            List<? extends WritebackMeasureMapping> writebackMeasure = writebackTableWritebackMeasure(writebackTable);
            String name = writebackTableName(writebackTable);
            String schema = writebackTableSchema(writebackTable);
            return createWritebackTable(writebackAttribute, writebackMeasure, name, schema);
        }
        return null;
    }

    protected List<? extends WritebackAttributeMapping> writebackTableWritebackAttribute(
        WritebackTableMapping writebackTable
    ) {
        return writebackAttributes(writebackTable.getWritebackAttribute());
    }

    protected List<WritebackAttributeMapping> writebackAttributes(
        List<? extends WritebackAttributeMapping> writebackAttributes
    ) {
        if (writebackAttributes != null) {
            return writebackAttributes.stream().map(this::writebackAttribute).toList();
        }
        return List.of();
    }

    protected WritebackAttributeMapping writebackAttribute(WritebackAttributeMapping writebackAttribute) {
        if (writebackAttribute != null) {
            ColumnMapping column = writebackAttributeColumn(writebackAttribute);
            DimensionConnectorMapping dimensionConnector = writebackAttributeDimensionConnector(writebackAttribute);
            return createWritebackAttribute(column, dimensionConnector);
        }
        return null;
    }

    protected DimensionConnectorMapping writebackAttributeDimensionConnector(WritebackAttributeMapping writebackAttribute) {
        return dimensionConnector(writebackAttribute.getDimensionConnector());
    }

    protected ColumnMapping writebackAttributeColumn(WritebackAttributeMapping writebackAttribute) {
        return column(writebackAttribute.getColumn());
    }

    protected abstract WritebackAttributeMapping createWritebackAttribute(ColumnMapping column, DimensionConnectorMapping dimension);

    protected List<? extends WritebackMeasureMapping> writebackTableWritebackMeasure(
        WritebackTableMapping writebackTable
    ) {
        return writebackMeasures(writebackTable.getWritebackMeasure());
    }

    protected List<WritebackMeasureMapping> writebackMeasures(
        List<? extends WritebackMeasureMapping> writebackMeasures
    ) {
        if (writebackMeasures != null) {
            return writebackMeasures.stream().map(this::writebackMeasure).toList();
        }
        return List.of();
    }

    protected WritebackMeasureMapping writebackMeasure(WritebackMeasureMapping writebackMeasure) {
        if (writebackMeasure != null) {
            ColumnMapping column = writebackMeasureColumn(writebackMeasure);
            String name = writebackMeasureName(writebackMeasure);
            return createwritebackMeasure(column, name);
        }
        return null;
    }

    protected abstract WritebackMeasureMapping createwritebackMeasure(ColumnMapping column, String name);

    protected String writebackMeasureName(WritebackMeasureMapping writebackMeasure) {
        return writebackMeasure.getName();
    }

    protected ColumnMapping writebackMeasureColumn(WritebackMeasureMapping writebackMeasure) {
        return column(writebackMeasure.getColumn());
    }

    protected String writebackTableName(WritebackTableMapping writebackTable) {
        return writebackTable.getName();
    }

    protected String writebackTableSchema(WritebackTableMapping writebackTable) {
        return writebackTable.getSchema();
    }

    protected abstract WritebackTableMapping createWritebackTable(
        List<? extends WritebackAttributeMapping> writebackAttribute,
        List<? extends WritebackMeasureMapping> writebackMeasure, String name, String schema
    );

    protected QueryMapping physicalCubeQuery(PhysicalCubeMapping pc) {
        return query(pc.getQuery());
    }

    protected List<? extends MeasureGroupMapping> physicalCubeMeasureGroups(PhysicalCubeMapping cube) {
        return measureGroups(cube.getMeasureGroups());
    }

    protected List<MeasureGroupMapping> measureGroups(List<? extends MeasureGroupMapping> measureGroups) {
        if (measureGroups != null) {
            return measureGroups.stream().map(this::measureGroup).toList();
        }
        return List.of();
    }

    protected MeasureGroupMapping measureGroup(MeasureGroupMapping measureGroup) {
        if (measureGroup != null) {
            List<? extends MeasureMapping> measures = measureGroupMeasures(measureGroup);
            String name = measureGroupName(measureGroup);
            MeasureGroupMapping mg = createMeasureGroup(measures, name);
            for (MeasureMapping m : measures) {
                ((MeasureMappingImpl) m).setMeasureGroup(mg);
            }
            return mg;
        }
        return null;
    }

    protected List<? extends MeasureMapping> measureGroupMeasures(MeasureGroupMapping measureGroup) {
        return measures(measureGroup.getMeasures());
    }

    protected List<MeasureMapping> measures(List<? extends MeasureMapping> measures) {
        if (measures != null) {
            return measures.stream().map(this::measure).toList();
        }
        return List.of();
    }

    protected String measureGroupName(MeasureGroupMapping measureGroup) {
        return measureGroup.getName();
    }

    protected abstract MeasureGroupMapping createMeasureGroup(List<? extends MeasureMapping> measures, String name);

    protected boolean cubeVisible(CubeMapping cube) {
        return cube.isVisible();
    }

    protected boolean cubeEnabled(CubeMapping cube) {
        return cube.isEnabled();
    }

    protected MemberMapping cubeDefaultMeasure(CubeMapping cube) {
        return member(cube.getDefaultMeasure());
    }

    protected MemberMapping member(MemberMapping member) {
        if (member instanceof MeasureMapping m) {
            return measure(m);
        }
        if (member instanceof CalculatedMemberMapping cm) {
            return calculatedMember(cm);
        }
        return null;
    }

    protected MeasureMapping measure(MeasureMapping measure) {
        if (measure != null) {
            if (!measureMap.containsKey(measure)) {
                List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty =
                    measureCalculatedMemberProperty(
                        measure);
                CellFormatterMapping cellFormatter = measureCellFormatter(measure);
                String backColor = measureBackColor(measure);
                ColumnMapping column = measureColumn(measure);
                InternalDataType datatype = measureDatatype(measure);
                String displayFolder = measureDisplayFolder(measure);
                String formatString = measureFormatString(measure);
                String formatter = measureFormatter(measure);
                boolean visible = measureVisible(measure);
                String name = measureName(measure);
                String id = measureId(measure);
                MeasureAggregatorType aggregatorType = aggregatorType(measure);
                MeasureMapping m = createMeasure( calculatedMemberProperty, cellFormatter, backColor,
                    column, datatype, displayFolder, formatString, formatter, visible, name, id, aggregatorType);
                measureMap.put(measure, m);
                return m;
            } else {
                return measureMap.get(measure);
            }
        }
        return null;
    }

    protected abstract MeasureMapping createMeasure(
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty,
        CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype, String displayFolder,
        String formatString, String formatter, boolean visible, String name, String id, MeasureAggregatorType type
    );

    protected MeasureAggregatorType aggregatorType(MeasureMapping measure) {
        return measure.getAggregatorType();
    }

    protected String measureId(MeasureMapping measure) {
        return measure.getId();
    }

    protected String measureName(MeasureMapping measure) {
        return measure.getName();
    }

    protected boolean measureVisible(MeasureMapping measure) {
        return measure.isVisible();
    }

    protected String measureFormatter(MeasureMapping measure) {
        return measure.getFormatter();
    }

    protected String measureFormatString(MeasureMapping measure) {
        return measure.getFormatString();
    }

    protected String measureDisplayFolder(MeasureMapping measure) {
        return measure.getDisplayFolder();
    }

    protected InternalDataType measureDatatype(MeasureMapping measure) {
        return measure.getDatatype();
    }

    protected ColumnMapping measureColumn(MeasureMapping measure) {
        return column(measure.getColumn());

    }

    protected String measureBackColor(MeasureMapping measure) {
        return measure.getBackColor();
    }

    protected CellFormatterMapping measureCellFormatter(MeasureMapping measure) {
        return cellFormatter(measure.getCellFormatter());
    }

    protected CellFormatterMapping cellFormatter(CellFormatterMapping cellFormatter) {
        if (cellFormatter != null) {
            if (!formatterMap.containsKey(cellFormatter)) {
                List<? extends AnnotationMapping> annotations = cellFormatterAnnotations(cellFormatter);
                String id = cellFormatterId(cellFormatter);
                String description = cellFormatterDescription(cellFormatter);
                String name = cellFormatterName(cellFormatter);
                String ref = cellFormatterRef(cellFormatter);
                CellFormatterMapping cf = createCellFormatter(annotations, id, description, name, ref);
                formatterMap.put(cellFormatter, cf);
                return cf;
            } else {
                return (CellFormatterMapping) formatterMap.get(cellFormatter);
            }
        }
        return null;
    }

    protected String cellFormatterRef(CellFormatterMapping cellFormatter) {
        return cellFormatter.getRef();
    }

    protected String cellFormatterName(CellFormatterMapping cellFormatter) {
        return cellFormatter.getName();
    }

    protected String cellFormatterDescription(CellFormatterMapping cellFormatter) {
        return cellFormatter.getDescription();
    }

    protected String cellFormatterId(CellFormatterMapping cellFormatter) {
        return cellFormatter.getId();
    }

    protected List<? extends AnnotationMapping> cellFormatterAnnotations(CellFormatterMapping cellFormatter) {
        return annotations(cellFormatter.getAnnotations());
    }

    protected abstract CellFormatterMapping createCellFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, String ref
    );

    protected List<? extends CalculatedMemberPropertyMapping> measureCalculatedMemberProperty(MeasureMapping measure) {
        return calculatedMemberProperties(measure.getCalculatedMemberProperties());
    }

    protected List<CalculatedMemberPropertyMapping> calculatedMemberProperties(
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties
    ) {
        if (calculatedMemberProperties != null) {
            return calculatedMemberProperties.stream().map(this::calculatedMemberProperty).toList();
        }
        return List.of();
    }

    protected CalculatedMemberPropertyMapping calculatedMemberProperty(
        CalculatedMemberPropertyMapping calculatedMemberProperty
    ) {
        if (calculatedMemberProperty != null) {
            List<? extends AnnotationMapping> annotations = calculatedMemberPropertyAnnotations(
                calculatedMemberProperty);
            String id = calculatedMemberPropertyId(calculatedMemberProperty);
            String description = calculatedMemberPropertyDescription(calculatedMemberProperty);
            String name = calculatedMemberPropertyName(calculatedMemberProperty);

            String expression = calculatedMemberPropertyExpression(calculatedMemberProperty);
            String value = calculatedMemberPropertyValue(calculatedMemberProperty);

            return createCalculatedMemberProperty(annotations, id, description, name, expression, value);
        }
        return null;
    }

    protected abstract CalculatedMemberPropertyMapping createCalculatedMemberProperty(
        List<? extends AnnotationMapping> annotations, String id, String description, String name,
        String expression, String value
    );

    protected String calculatedMemberPropertyValue(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getValue();
    }

    protected String calculatedMemberPropertyExpression(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getExpression();
    }

    protected String calculatedMemberPropertyName(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getName();
    }

    protected String calculatedMemberPropertyDescription(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getDescription();
    }

    protected String calculatedMemberPropertyId(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getId();
    }

    protected List<? extends AnnotationMapping> calculatedMemberPropertyAnnotations(
        CalculatedMemberPropertyMapping calculatedMemberProperty
    ) {
        return annotations(calculatedMemberProperty.getAnnotations());
    }


    protected List<? extends KpiMapping> cubeKpis(CubeMapping cube) {
        return kpis(cube.getKpis());
    }

    protected List<KpiMapping> kpis(List<? extends KpiMapping> kpis) {
        if (kpis != null) {
            return kpis.stream().map(this::kpi).toList();
        }
        return List.of();
    }

    protected KpiMapping kpi(KpiMapping kpi) {
        if (kpi != null) {
            List<? extends AnnotationMapping> annotations = kpiAnnotations(kpi);
            String id = kpiId(kpi);
            String description = kpiDescription(kpi);
            String name = kpiName(kpi);

            List<? extends TranslationMapping> translations = kpiTranslations(kpi);
            String displayFolder = kpiDisplayFolder(kpi);
            String associatedMeasureGroupID = kpiAssociatedMeasureGroupID(kpi);
            String value = kpiValue(kpi);
            String goal = kpiGoal(kpi);
            String status = kpiStatus(kpi);
            String trend = kpiTrend(kpi);
            String weight = kpiWeight(kpi);
            String trendGraphic = kpiTrendGraphic(kpi);
            String statusGraphic = kpiStatusGraphic(kpi);
            String currentTimeMember = kpiCurrentTimeMember(kpi);
            String parentKpiID = kpiParentKpiID(kpi);
            return createKpi(annotations, id, description, name, translations, displayFolder,
                associatedMeasureGroupID, value, goal, status, trend, weight, trendGraphic, statusGraphic,
                currentTimeMember, parentKpiID);
        }
        return null;
    }

    protected String kpiParentKpiID(KpiMapping kpi) {
        return kpi.getParentKpiID();
    }

    protected String kpiCurrentTimeMember(KpiMapping kpi) {
        return kpi.getCurrentTimeMember();
    }

    protected String kpiStatusGraphic(KpiMapping kpi) {
        return kpi.getStatusGraphic();
    }

    protected String kpiTrendGraphic(KpiMapping kpi) {
        return kpi.getTrendGraphic();
    }

    protected String kpiWeight(KpiMapping kpi) {
        return kpi.getWeight();
    }

    protected String kpiTrend(KpiMapping kpi) {
        return kpi.getTrend();
    }

    protected String kpiStatus(KpiMapping kpi) {
        return kpi.getStatus();
    }

    protected String kpiGoal(KpiMapping kpi) {
        return kpi.getGoal();
    }

    protected String kpiValue(KpiMapping kpi) {
        return kpi.getValue();
    }

    protected String kpiAssociatedMeasureGroupID(KpiMapping kpi) {
        return kpi.getAssociatedMeasureGroupID();
    }

    protected String kpiDisplayFolder(KpiMapping kpi) {
        return kpi.getDisplayFolder();
    }

    protected List<? extends TranslationMapping> kpiTranslations(KpiMapping kpi) {
        return translations(kpi.getTranslations());
    }

    protected List<TranslationMapping> translations(List<? extends TranslationMapping> translations) {
        if (translations != null) {
            return translations.stream().map(this::translation).toList();
        }
        return List.of();
    }

    protected TranslationMapping translation(TranslationMapping translation) {
        if (translation != null) {
            long language = translationLanguage(translation);
            String caption = translationCaption(translation);
            String description = translationDescription(translation);
            String displayFolder = translationDisplayFolder(translation);
            List<? extends AnnotationMapping> annotations = translationAnnotations(translation);
            return createTranslation(language, caption, description, displayFolder, annotations);
        }
        return null;
    }

    protected List<? extends AnnotationMapping> translationAnnotations(TranslationMapping translation) {
        return annotations(translation.getAnnotations());
    }

    protected String translationDisplayFolder(TranslationMapping translation) {
        return translation.getDisplayFolder();
    }

    protected String translationDescription(TranslationMapping translation) {
        return translation.getDescription();
    }

    protected String translationCaption(TranslationMapping translation) {
        return translation.getCaption();
    }

    protected long translationLanguage(TranslationMapping translation) {
        return translation.getLanguage();
    }

    protected abstract TranslationMapping createTranslation(
        long language, String caption, String description,
        String displayFolder, List<? extends AnnotationMapping> annotations
    );

    protected String kpiName(KpiMapping kpi) {
        return kpi.getName();
    }

    protected List<? extends AnnotationMapping> kpiAnnotations(KpiMapping kpi) {
        return annotations(kpi.getAnnotations());
    }

    protected String kpiDescription(KpiMapping kpi) {
        return kpi.getDescription();
    }

    protected String kpiId(KpiMapping kpi) {
        return kpi.getId();
    }

    protected abstract KpiMapping createKpi(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, List<? extends TranslationMapping> translations,
        String displayFolder, String associatedMeasureGroupID, String value, String goal, String status,
        String trend, String weight, String trendGraphic, String statusGraphic, String currentTimeMember,
        String parentKpiID
    );

    protected List<? extends NamedSetMapping> cubeNamedSets(CubeMapping cube) {
        return namedSets(cube.getNamedSets());
    }

    protected List<? extends CalculatedMemberMapping> cubeCalculatedMembers(CubeMapping cube) {
        return calculatedMembers(cube.getCalculatedMembers());
    }

    protected List<CalculatedMemberMapping> calculatedMembers(
        List<? extends CalculatedMemberMapping> calculatedMembers
    ) {
        if (calculatedMembers != null) {
            return calculatedMembers.stream().map(this::calculatedMember).toList();
        }
        return List.of();
    }

    protected CalculatedMemberMapping calculatedMember(CalculatedMemberMapping calculatedMember) {
        if (calculatedMember != null) {
            if (!calculatedMemberMap.containsKey(calculatedMember)) {
                List<? extends AnnotationMapping> annotations = calculatedMemberAnnotations(calculatedMember);
                String id = calculatedMemberId(calculatedMember);
                String description = calculatedMemberDescription(calculatedMember);
                String name = calculatedMemberName(calculatedMember);

                List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties =
                    calculatedMemberCalculatedMemberProperties(
                        calculatedMember);
                CellFormatterMapping cellFormatter = calculatedMemberCellFormatter(calculatedMember);
                String formula = calculatedMemberFormula(calculatedMember);
                String displayFolder = calculatedMemberDisplayFolder(calculatedMember);
                String formatString = calculatedMemberFormatString(calculatedMember);
                HierarchyMapping hierarchy = calculatedMemberHierarchy(calculatedMember);
                String parent = calculatedMemberParent(calculatedMember);
                boolean visible = calculatedMemberVisible(calculatedMember);

                CalculatedMemberMapping cm = createCalculatedMember(annotations, id, description, name
                    , calculatedMemberProperties,
                    cellFormatter, formula, displayFolder, formatString, hierarchy, parent, visible);
                calculatedMemberMap.put(calculatedMember, cm);
                return cm;
            } else {
                return calculatedMemberMap.get(calculatedMember);
            }
        }
        return null;
    }

    protected abstract CalculatedMemberMapping createCalculatedMember(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
        CellFormatterMapping cellFormatter, String formula, String displayFolder, String formatString,
        HierarchyMapping hierarchy, String parent, boolean visible
    );

    protected boolean calculatedMemberVisible(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.isVisible();
    }

    protected String calculatedMemberParent(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getParent();
    }

    protected HierarchyMapping calculatedMemberHierarchy(CalculatedMemberMapping calculatedMember) {
        return hierarchy(calculatedMember.getHierarchy());
    }

    protected String calculatedMemberFormatString(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getFormatString();
    }

    protected String calculatedMemberDisplayFolder(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getDisplayFolder();
    }

    protected String calculatedMemberFormula(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getFormula();
    }

    protected CellFormatterMapping calculatedMemberCellFormatter(CalculatedMemberMapping calculatedMember) {
        return cellFormatter(calculatedMember.getCellFormatter());
    }

    protected List<? extends CalculatedMemberPropertyMapping> calculatedMemberCalculatedMemberProperties(
        CalculatedMemberMapping calculatedMember
    ) {
        return calculatedMemberProperties(calculatedMember.getCalculatedMemberProperties());
    }

    protected String calculatedMemberName(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getName();
    }

    protected String calculatedMemberDescription(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getDescription();
    }

    protected String calculatedMemberId(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getId();
    }

    protected List<? extends AnnotationMapping> calculatedMemberAnnotations(CalculatedMemberMapping calculatedMember) {
        return annotations(calculatedMember.getAnnotations());
    }

    protected List<? extends DimensionConnectorMapping> cubeDimensionConnectors(CubeMapping cube) {
        return dimensionConnectors(cube.getDimensionConnectors());
    }

    protected List<DimensionConnectorMapping> dimensionConnectors(
        List<? extends DimensionConnectorMapping> dimensionConnectors
    ) {
        if (dimensionConnectors != null) {
            return dimensionConnectors.stream().map(this::dimensionConnector).toList();
        }
        return List.of();
    }

    protected DimensionConnectorMapping dimensionConnector(DimensionConnectorMapping dimensionConnector) {
        if (dimensionConnector != null) {
            ColumnMapping foreignKey = dimensionConnectorForeignKey(dimensionConnector);
            LevelMapping level = dimensionConnectorLevel(dimensionConnector);
            String usagePrefix = dimensionConnectorUsagePrefix(dimensionConnector);
            boolean visible = dimensionConnectorVisible(dimensionConnector);
            DimensionMapping dimension = dimensionConnectorDimension(dimensionConnector);
            String overrideDimensionName = dimensionConnectorOverrideDimensionName(dimensionConnector);
            PhysicalCubeMapping physicalCube = dimensionConnectorPhysicalCube(dimensionConnector);
            return createDimensionConnector(foreignKey, level, usagePrefix, visible, dimension, overrideDimensionName
                , physicalCube);
        }

        return null;
    }

    protected PhysicalCubeMapping dimensionConnectorPhysicalCube(DimensionConnectorMapping dimensionConnector) {
        return physicalCube(dimensionConnector.getPhysicalCube());
    }

    protected abstract DimensionConnectorMapping createDimensionConnector(
        ColumnMapping foreignKey, LevelMapping level,
        String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName,
        PhysicalCubeMapping physicalCube
    );

    protected String dimensionConnectorOverrideDimensionName(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.getOverrideDimensionName();
    }

    protected DimensionMapping dimensionConnectorDimension(DimensionConnectorMapping dimensionConnector) {
        return dimension(dimensionConnector.getDimension());
    }

    protected boolean dimensionConnectorVisible(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.isVisible();
    }

    protected String dimensionConnectorUsagePrefix(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.getUsagePrefix();
    }

    protected LevelMapping dimensionConnectorLevel(DimensionConnectorMapping dimensionConnector) {
        return level(dimensionConnector.getLevel());
    }

    protected ColumnMapping dimensionConnectorForeignKey(DimensionConnectorMapping dimensionConnector) {
        return column(dimensionConnector.getForeignKey());
    }

    protected String cubeName(CubeMapping cube) {
        return cube.getName();
    }

    protected String cubeDescription(CubeMapping cube) {
        return cube.getDescription();
    }

    protected String cubeId(CubeMapping cube) {
        return cube.getId();
    }

    protected List<? extends AnnotationMapping> cubeAnnotations(CubeMapping cube) {
        return annotations(cube.getAnnotations());
    }

    protected List<? extends ParameterMapping> catalogParameters(CatalogMapping catalogMappingOriginal) {
        return parameters(catalogMappingOriginal.getParameters());
    }

    protected List<? extends ParameterMapping> parameters(List<? extends ParameterMapping> parameters) {
        return List.of();
    }

    protected List<? extends AnnotationMapping> catalogAnnotations(CatalogMapping catalogMappingOriginal) {
        return annotations(catalogMappingOriginal.getAnnotations());
    }

    protected List<AnnotationMapping> annotations(List<? extends AnnotationMapping> annotations) {
        if (annotations != null) {
            return annotations.stream().map(this::annotation).toList();
        }
        return List.of();
    }

    protected AnnotationMapping annotation(AnnotationMapping annotation) {
        if (annotation != null) {
            String value = annotation.getValue();
            String name = annotation.getName();
            return createAnnotation(value, name);
        }
        return null;
    }

    protected abstract AnnotationMapping createAnnotation(String value, String name);

    protected abstract CatalogMapping createCatalog(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name,
        List<? extends ParameterMapping> parameters, List<? extends CubeMapping> cubes,
        List<? extends NamedSetMapping> namedSets, List<? extends AccessRoleMapping> accessRoles,
        AccessRoleMapping defaultAccessRole, String measuresDimensionName,
        List<? extends DatabaseSchemaMapping> dbSchemas
    );

    protected List<? extends LinkMapping> databaseCatalogLinks(DatabaseCatalogMapping catalog2) {
        return links(catalog2.getLinks());
    }

    protected List<? extends LinkMapping> links(List<? extends LinkMapping> links) {
        if (links != null) {
            return links.stream().map(this::link).toList();
        }
        return null;
    }

    protected LinkMapping link(LinkMapping link) {
        if (link != null) {
            ColumnMapping primaryKey = linkPrimaryKey(link);
            ColumnMapping foreignKey = linkForeignKey(link);
            return createLink(primaryKey, foreignKey);
        }
        return null;
    }

    protected abstract LinkMapping createLink(ColumnMapping primaryKey, ColumnMapping foreignKey);

    protected ColumnMapping linkForeignKey(LinkMapping link) {
        return column(link.getForeignKey());
    }

    protected ColumnMapping linkPrimaryKey(LinkMapping link) {
        return column(link.getPrimaryKey());
    }

    protected abstract DatabaseCatalogMapping createDatabaseCatalog(
        List<? extends DatabaseSchemaMapping> schemas,
        List<? extends LinkMapping> links
    );

    protected List<? extends DatabaseSchemaMapping> catalogSchemas(DatabaseCatalogMapping catalog2) {
        return databaseSchemas(catalog2.getSchemas());
    }

    protected CubeMapping look(CubeMapping c) {
        return cubeMap.get(c);
    }

    protected DimensionMapping look(DimensionMapping d) {
        return dimensionMap.get(d);
    }

    protected HierarchyMapping look(HierarchyMapping h) {
        return hierarchyMap.get(h);
    }

    protected LevelMapping look(LevelMapping l) {
        return levelMap.get(l);
    }

    protected FormatterMapping look(FormatterMapping f) {
        return formatterMap.get(f);
    }

    protected DatabaseSchemaMapping look(DatabaseSchemaMapping d) {
        return dbSchemaMap.get(d);
    }

    protected AccessRoleMapping look(AccessRoleMapping r) {
        return accessRoleMap.get(r);
    }

    protected AggregationTableMapping look(AggregationTableMapping at) {
        return aggregationTableMap.get(at);
    }

    protected AggregationExcludeMapping look(AggregationExcludeMapping ae) {
        return aggregationExcludeMap.get(ae);
    }

    protected QueryMapping look(QueryMapping q) {
        return queryMap.get(q);
    }

    protected MeasureMapping look(MeasureMapping m) {
        return measureMap.get(m);
    }

    protected CalculatedMemberMapping look(CalculatedMemberMapping cm) {
        return calculatedMemberMap.get(cm);
    }
}
