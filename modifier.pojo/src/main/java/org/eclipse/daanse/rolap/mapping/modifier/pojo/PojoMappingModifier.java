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
package org.eclipse.daanse.rolap.mapping.modifier.pojo;

import java.util.List;

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
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlStatementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlViewMapping;
import org.eclipse.daanse.rolap.mapping.api.model.StandardDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TimeDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimension;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchy;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCatalog;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.RollupPolicyType;
import org.eclipse.daanse.rolap.mapping.modifier.common.AbstractMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.AbstractTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCatalogGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ActionMappingMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationExcludeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureFactCountMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationPatternMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CellFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseCatalogImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DrillThroughActionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DrillThroughAttributeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.KpiMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LinkMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberReaderParameterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.NamedSetMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ParameterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ParentChildLinkMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.QueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RowMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RowValueMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlSelectQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlStatementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlViewMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SystemTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryOptimizationHintMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TranslationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ViewTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.VirtualCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackAttributeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackTableMappingImpl;

public class PojoMappingModifier extends AbstractMappingModifier {

    public PojoMappingModifier(CatalogMapping catalog) {
        super(catalog);
    }

    @Override
    protected ColumnMapping createColumn(
        String name, TableMapping table, String type, Integer columnSize, Integer decimalDigits,
        Integer numPrecRadix, Integer charOctetLength, Boolean nullable, String description
    ) {
        ColumnMappingImpl column = ColumnMappingImpl.builder().build();
        column.setName(name);
        column.setTable(table);
        column.setType(type);
        column.setColumnSize(columnSize);
        column.setDecimalDigits(decimalDigits);
        column.setNumPrecRadix(numPrecRadix);
        column.setCharOctetLength(charOctetLength);
        column.setNullable(nullable);
        column.setDescription(description);
        return column;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PhysicalTableMapping createPhysicalTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    ) {
        PhysicalTableMappingImpl physicalTableImpl = PhysicalTableMappingImpl.builder().build();
        physicalTableImpl.setName(name);
        physicalTableImpl.setColumns((List<ColumnMappingImpl>) columns);
        physicalTableImpl.setSchema((DatabaseSchemaMappingImpl) schema);
        physicalTableImpl.setDescription(description);
        return physicalTableImpl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableMapping createSystemTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    ) {
        SystemTableMappingImpl systemTableImpl = SystemTableMappingImpl.builder().build();
        systemTableImpl.setName(name);
        systemTableImpl.setColumns((List<ColumnMappingImpl>) columns);
        systemTableImpl.setSchema((DatabaseSchemaMappingImpl) schema);
        systemTableImpl.setDescription(description);
        return systemTableImpl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableMapping createViewTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description
    ) {
        ViewTableMappingImpl viewTableImpl = ViewTableMappingImpl.builder().build();
        viewTableImpl.setName(name);
        viewTableImpl.setColumns((List<ColumnMappingImpl>) columns);
        viewTableImpl.setSchema((DatabaseSchemaMappingImpl) schema);
        viewTableImpl.setDescription(description);
        return viewTableImpl;

    }

    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseSchemaMapping createDatabaseSchema(List<? extends TableMapping> tables, String name, String id) {
        DatabaseSchemaMappingImpl databaseSchema = DatabaseSchemaMappingImpl.builder().build();
        databaseSchema.setTables((List<AbstractTableMappingImpl>) tables);
        databaseSchema.setName(name);
        databaseSchema.setId(id);
        return databaseSchema;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CatalogMapping createCatalog(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, DocumentationMapping documentation, List<? extends ParameterMapping> parameters,
        List<? extends CubeMapping> cubes, List<? extends NamedSetMapping> namedSets,
        List<? extends AccessRoleMapping> accessRoles, AccessRoleMapping defaultAccessRole,
        String measuresDimensionName, List<? extends DatabaseSchemaMapping> dbschemas
    ) {
        return CatalogMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withParameters((List<ParameterMappingImpl>) parameters)
            .withCubes((List<CubeMappingImpl>) cubes)
            .withNamedSets((List<NamedSetMappingImpl>) namedSets)
            .withAccessRoles((List<AccessRoleMappingImpl>) accessRoles)
            .withDefaultAccessRole((AccessRoleMappingImpl) defaultAccessRole)
            .withMeasuresDimensionName(measuresDimensionName)
            .withDbSchemas((List<DatabaseSchemaMappingImpl>) dbschemas)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessRoleMapping createAccessRole(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends AccessCatalogGrantMapping> accessCatalogGrants,
        List<? extends AccessRoleMapping> referencedAccessRoles
    ) {
        return AccessRoleMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withAccessCatalogGrants((List<AccessCatalogGrantMappingImpl>) accessCatalogGrants)
            .withReferencedAccessRoles((List<AccessRoleMappingImpl>) referencedAccessRoles)
            .build();
    }

    @Override
    protected AccessMemberGrantMapping createAccessMemberGrant(AccessMember access, String member) {
        return AccessMemberGrantMappingImpl.builder()
            .withAccess(access)
            .withMember(member)
            .build();
    }

    @Override
    protected QueryMapping createInlineTableQuery(
        String alias,
        InlineTableMapping table, String id, DocumentationMapping documentation
    ) {
        return InlineTableQueryMappingImpl.builder()
            .withAlias(alias)
            .withTable((InlineTableMappingImpl) table)
            .withId(id)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .build();
    }

    @Override
    protected QueryMapping createJoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right,
            String id, DocumentationMapping documentation) {
        return JoinQueryMappingImpl.builder()
            .withLeft((JoinedQueryElementMappingImpl) left)
            .withRight((JoinedQueryElementMappingImpl) right)
            .withId(id)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .build();
    }

    @Override
    protected JoinedQueryElementMapping createJoinedQueryElement(String alias, ColumnMapping key, QueryMapping query) {
        return JoinedQueryElementMappingImpl.builder()
            .withAlias(alias)
            .withKey((ColumnMappingImpl) key)
            .withQuery((QueryMappingImpl) query)
            .build();
    }

    @Override
    protected QueryMapping createSqlSelectQuery(String alias, SqlViewMapping sql,
            String id, DocumentationMapping documentation) {
        return SqlSelectQueryMappingImpl.builder()
            .withAlias(alias)
            .withSql((SqlViewMappingImpl) sql)
            .withId(id)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableQueryMapping createTableQuery(
        String alias, SqlStatementMapping sqlWhereExpression,
        List<? extends AggregationExcludeMapping> aggregationExcludes,
        List<? extends TableQueryOptimizationHintMapping> optimizationHints, TableMapping table,
        List<? extends AggregationTableMapping> aggregationTables,
        String id, DocumentationMapping documentation
    ) {
        return TableQueryMappingImpl.builder()
            .withAlias(alias)
            .withSqlWhereExpression((SqlStatementMappingImpl) sqlWhereExpression)
            .withAggregationExcludes((List<AggregationExcludeMappingImpl>) aggregationExcludes)
            .withOptimizationHints((List<TableQueryOptimizationHintMappingImpl>) optimizationHints)
            .withTable((PhysicalTableMappingImpl) table)
            .withAggregationTables((List<AggregationTableMappingImpl>) aggregationTables)
            .withId(id)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .build();
    }

    @Override
    protected AggregationMeasureFactCountMapping createAggregationMeasureFactCount(ColumnMapping column, ColumnMapping factColumn) {
        return AggregationMeasureFactCountMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .withFactColumn((ColumnMappingImpl) factColumn)
            .build();
    }

    @Override
    protected AggregationLevelPropertyMapping createAggregationLevelProperty(ColumnMapping column, String name) {
        return AggregationLevelPropertyMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationLevelMapping createAggregationLevel(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, ColumnMapping captionColumn,
        boolean collapsed, ColumnMapping column, String name, ColumnMapping nameColumn, ColumnMapping ordinalColumn
    ) {
        return AggregationLevelMappingImpl.builder()
            .withAggregationLevelProperties((List<AggregationLevelPropertyMappingImpl>) aggregationLevelProperties)
            .withCaptionColumn((ColumnMappingImpl) captionColumn)
            .withCollapsed(collapsed)
            .withColumn((ColumnMappingImpl) column)
            .withName(name)
            .withNameColumn((ColumnMappingImpl) nameColumn)
            .withOrdinalColumn((ColumnMappingImpl) ordinalColumn)
            .build();
    }

    @Override
    protected AggregationMeasureMapping createAggregationMeasure(ColumnMapping column, String name, String rollupType) {
        return AggregationMeasureMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .withName(name)
            .withRollupType(rollupType)
            .build();
    }

    @Override
    protected AggregationForeignKeyMapping createAggregationForeignKey(ColumnMapping aggregationColumn, ColumnMapping factColumn) {
        return AggregationForeignKeyMappingImpl.builder()
            .withAggregationColumn((ColumnMappingImpl) aggregationColumn)
            .withFactColumn((ColumnMappingImpl) factColumn)
            .build();
    }

    @Override
    protected AggregationColumnNameMapping createAggregationColumn(ColumnMapping column) {
        return AggregationColumnNameMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .build();
    }

    @Override
    protected TableQueryOptimizationHintMapping createTableQueryOptimizationHint(String value, String type) {
        return TableQueryOptimizationHintMappingImpl.builder()
            .withValue(value)
            .withType(type)
            .build();
    }

    @Override
    protected AggregationExcludeMapping createAggregationExclude(
        boolean ignorecase, String name, String pattern,
        String id
    ) {
        return AggregationExcludeMappingImpl.builder()
            .withIgnorecase(ignorecase)
            .withName(name)
            .withPattern(pattern)
            .withId(id)
            .build();
    }


    @Override
    protected SqlStatementMapping createSqlStatement(List<String> dialects, String statement) {
        return SqlStatementMappingImpl.builder()
            .withDialects(dialects)
            .withSql(statement)
            .build();
    }

    @Override
    protected MemberReaderParameterMapping createMemberReaderParameter(String name, String value) {
        return MemberReaderParameterMappingImpl.builder()
            .withName(name)
            .withValue(value)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected HierarchyMapping createHierarchy(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, List<? extends LevelMapping> levels,
        List<? extends MemberReaderParameterMapping> memberReaderParameters, String allLevelName,
        String allMemberCaption, String allMemberName, String defaultMember, String displayFolder, boolean hasAll,
        String memberReaderClass, String origin, ColumnMapping primaryKey, TableMapping primaryKeyTable,
        String uniqueKeyLevelName, boolean visible, QueryMapping query
    ) {
        return HierarchyMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withId(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withLevels((List<LevelMappingImpl>) levels)
            .withMemberReaderParameters((List<MemberReaderParameterMappingImpl>) memberReaderParameters)
            .withAllLevelName(allLevelName)
            .withAllMemberCaption(allMemberCaption)
            .withAllMemberName(allMemberName)
            .withDefaultMember(defaultMember)
            .withDisplayFolder(displayFolder)
            .withHasAll(hasAll)
            .withMemberReaderClass(memberReaderClass)
            .withOrigin(origin)
            .withPrimaryKey((ColumnMappingImpl) primaryKey)
            .withPrimaryKeyTable(primaryKeyTable)
            .withUniqueKeyLevelName(uniqueKeyLevelName)
            .withVisible(visible)
            .withQuery((QueryMappingImpl) query)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberFormatterMapping createMemberFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String ref
    ) {
        return MemberFormatterMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withRef(ref)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberPropertyMapping createMemberProperty(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        MemberPropertyFormatterMapping formatter, ColumnMapping column, boolean dependsOnLevelValue, DataType type
    ) {
        return MemberPropertyMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withFormatter((MemberPropertyFormatterMappingImpl) formatter)
            .withColumn((ColumnMappingImpl) column)
            .withDependsOnLevelValue(dependsOnLevelValue)
            .withDataType(type)
            .build();
    }

    @Override
    protected ParentChildLinkMapping createParentChildLink(
        TableQueryMapping table, ColumnMapping childColumn,
        ColumnMapping parentColumn
    ) {
        return ParentChildLinkMappingImpl.builder()
            .withTable((TableQueryMappingImpl) table)
            .withChildColumn((ColumnMappingImpl) childColumn)
            .withParentColumn((ColumnMappingImpl) parentColumn)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLExpressionMapping createSQLExpression(List<? extends SqlStatementMapping> sqls) {
        return SQLExpressionMappingImpl.builder()
            .withSqls((List<SqlStatementMappingImpl>) sqls)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LevelMapping createLevel(
        SQLExpressionMapping keyExpression, SQLExpressionMapping nameExpression,
        SQLExpressionMapping captionExpression, SQLExpressionMapping ordinalExpression,
        SQLExpressionMapping parentExpression, ParentChildLinkMapping parentChildLink,
        List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
        String approxRowCount, ColumnMapping captionColumn, ColumnMapping column, HideMemberIfType hideMemberIf,
        LevelType levelType, ColumnMapping nameColumn, String nullParentValue, ColumnMapping ordinalColumn, ColumnMapping parentColumn,
        TableMapping table, DataType type, boolean uniqueMembers, boolean visible, String name, String id, String description
    ) {
        return LevelMappingImpl.builder()
            .withKeyExpression((SQLExpressionMappingImpl) keyExpression)
            .withNameExpression((SQLExpressionMappingImpl) nameExpression)
            .withCaptionExpression((SQLExpressionMappingImpl) captionExpression)
            .withOrdinalExpression((SQLExpressionMappingImpl) ordinalExpression)
            .withParentExpression((SQLExpressionMappingImpl) parentExpression)
            .withParentChildLink((ParentChildLinkMappingImpl) parentChildLink)
            .withMemberProperties((List<MemberPropertyMappingImpl>) memberProperties)
            .withMemberFormatter((MemberFormatterMappingImpl) memberFormatter)
            .withApproxRowCount(approxRowCount)
            .withCaptionColumn((ColumnMappingImpl) captionColumn)
            .withColumn((ColumnMappingImpl) column)
            .withHideMemberIfType(hideMemberIf)
            .withLevelType(levelType)
            .withNameColumn((ColumnMappingImpl) nameColumn)
            .withNullParentValue(nullParentValue)
            .withOrdinalColumn((ColumnMappingImpl) ordinalColumn)
            .withParentColumn((ColumnMappingImpl) parentColumn)
            .withTable(table)
            .withType(type)
            .withUniqueMembers(uniqueMembers)
            .withVisible(visible)
            .withName(name)
            .withId(id)
            .withDescription(description)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessHierarchyGrantMapping createAccessHierarchyGrant(
        List<? extends AccessMemberGrantMapping> memberGrants, AccessHierarchy access, LevelMapping bottomLevel,
        RollupPolicyType rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy
    ) {
        return AccessHierarchyGrantMappingImpl.builder()
            .withMemberGrants((List<AccessMemberGrantMappingImpl>) memberGrants)
            .withAccess(access)
            .withBottomLevel((LevelMappingImpl) bottomLevel)
            .withRollupPolicyType(rollupPolicy)
            .withTopLevel((LevelMappingImpl) topLevel)
            .withHierarchy((HierarchyMappingImpl) hierarchy)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected StandardDimensionMapping createStandardDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    ) {
        return StandardDimensionMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withHierarchies((List<HierarchyMappingImpl>) hierarchies)
            .withUsagePrefix(usagePrefix)
            .withVisible(visible)
            .build();
    }

    @Override
    protected AccessDimensionGrantMapping createAccessDimensionGrant(AccessDimension access, DimensionMapping dimension) {
        return AccessDimensionGrantMappingImpl.builder()
            .withAccess(access)
            .withDimension((DimensionMappingImpl) dimension)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCubeGrantMapping createAccessCubeGrant(
        List<? extends AccessDimensionGrantMapping> dimensionGrants,
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants, AccessCube access, CubeMapping cube
    ) {
        return AccessCubeGrantMappingImpl.builder()
            .withDimensionGrants((List<AccessDimensionGrantMappingImpl>) dimensionGrants)
            .withHierarchyGrants((List<AccessHierarchyGrantMappingImpl>) hierarchyGrants)
            .withAccess(access)
            .withCube((CubeMappingImpl) cube)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCatalogGrantMapping createAccessCatalogGrant(
        List<? extends AccessCubeGrantMapping> accessCubeGrant,
        AccessCatalog access
    ) {
        return AccessCatalogGrantMappingImpl.builder()
            .withCubeGrant((List<AccessCubeGrantMappingImpl>) accessCubeGrant)
            .withAccess(access)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected NamedSetMapping createNamedSet(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String displayFolder, String formula
    ) {
        return NamedSetMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withDisplayFolder(displayFolder)
            .withFormula(formula)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureGroupMapping createMeasureGroup(List<? extends MeasureMapping> measures, String name) {
        return MeasureGroupMappingImpl.builder()
            .withMeasures((List<MeasureMappingImpl>) measures)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createMeasure(
        SQLExpressionMapping measureExpression,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty,
        CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, DataType datatype, String displayFolder,
        String formatString, String formatter, boolean visible, String name, String id, MeasureAggregatorType type
    ) {
        return MeasureMappingImpl.builder()
            .withMeasureExpression((SQLExpressionMappingImpl) measureExpression)
            .withCalculatedMemberProperty((List<CalculatedMemberPropertyMappingImpl>) calculatedMemberProperty)
            .withCellFormatter((CellFormatterMappingImpl) cellFormatter)
            .withBackColor(backColor)
            .withColumn((ColumnMappingImpl) column)
            .withDatatype(datatype)
            .withDisplayFolder(displayFolder)
            .withFormatString(formatString)
            .withVisible(visible)
            .withName(name)
            .withId(id)
            .withAggregatorType(type)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CellFormatterMapping createCellFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String ref
    ) {
        return CellFormatterMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withRef(ref)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CalculatedMemberPropertyMapping createCalculatedMemberProperty(
        List<? extends AnnotationMapping> annotations, String id, String description, String name,
        DocumentationMapping documentation, String expression, String value
    ) {
        return CalculatedMemberPropertyMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withExpression(expression)
            .withValue(value)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TranslationMapping createTranslation(
        long language, String caption, String description,
        String displayFolder, List<? extends AnnotationMapping> annotations
    ) {
        return TranslationMappingImpl.builder()
            .withLanguage(language)
            .withCaption(caption)
            .withDescription(description)
            .withDisplayFolder(displayFolder)
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected KpiMapping createKpi(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, DocumentationMapping documentation, List<? extends TranslationMapping> translations,
        String displayFolder, String associatedMeasureGroupID, String value, String goal, String status,
        String trend, String weight, String trendGraphic, String statusGraphic, String currentTimeMember,
        String parentKpiID
    ) {
        return KpiMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withTranslations((List<TranslationMappingImpl>) translations)
            .withDisplayFolder(displayFolder)
            .withAssociatedMeasureGroupID(associatedMeasureGroupID)
            .withValue(value)
            .withGoal(goal)
            .withStatus(status)
            .withTrend(trend)
            .withWeight(weight)
            .withTrendGraphic(trendGraphic)
            .withStatusGraphic(statusGraphic)
            .withCurrentTimeMember(currentTimeMember)
            .withParentKpiID(parentKpiID)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CalculatedMemberMapping createCalculatedMember(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
        CellFormatterMapping cellFormatter, String formula, String displayFolder, String formatString,
        HierarchyMapping hierarchy, String parent, boolean visible
    ) {
        return CalculatedMemberMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withCalculatedMemberProperties((List<CalculatedMemberPropertyMappingImpl>) calculatedMemberProperties)
            .withCellFormatter((CellFormatterMappingImpl) cellFormatter)
            .withFormula(formula)
            .withDisplayFolder(displayFolder)
            .withFormatString(formatString)
            .withHierarchy((HierarchyMappingImpl) hierarchy)
            .withParent(parent)
            .withVisible(visible)
            .build();
    }

    @Override
    protected DimensionConnectorMapping createDimensionConnector(
        ColumnMapping foreignKey, LevelMapping level,
        String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName,
        PhysicalCubeMapping physicalCube
    ) {
        return DimensionConnectorMappingImpl.builder()
            .withForeignKey((ColumnMappingImpl) foreignKey)
            .withLevel((LevelMappingImpl) level)
            .withUsagePrefix(usagePrefix)
            .withVisible(visible)
            .withDimension((DimensionMappingImpl) dimension)
            .withOverrideDimensionName(overrideDimensionName)
            .withPhysicalCube((PhysicalCubeMappingImpl) physicalCube)
            .build();
    }

    @Override
    protected DocumentationMapping createDocumentation(String value) {
        return DocumentationMappingImpl.builder().withValue(value).build();
    }

    @Override
    protected AnnotationMapping createAnnotation(String value, String name) {
        return AnnotationMappingImpl.builder()
            .withValue(value)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationTableMapping createAggregationPattern(
        AggregationColumnNameMapping aggregationFactCount,
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
        List<? extends AggregationMeasureMapping> aggregationMeasures,
        List<? extends AggregationLevelMapping> aggregationLevels,
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
        String id, String pattern, List<? extends AggregationExcludeMapping> excludes
    ) {
        return AggregationPatternMappingImpl.builder()
            .withAggregationFactCount((AggregationColumnNameMappingImpl) aggregationFactCount)
            .withAggregationIgnoreColumns((List<AggregationColumnNameMappingImpl>) aggregationIgnoreColumns)
            .withAggregationForeignKeys((List<AggregationForeignKeyMappingImpl>) aggregationForeignKeys)
            .withAggregationMeasures((List<AggregationMeasureMappingImpl>) aggregationMeasures)
            .withAggregationLevels((List<AggregationLevelMappingImpl>) aggregationLevels)
            .withAggregationMeasureFactCounts((List<AggregationMeasureFactCountMappingImpl>) aggregationMeasureFactCounts)
            .withIgnorecase(ignorecase)
            .withId(id)
            .withPattern(pattern)
            .withExcludes((List<AggregationExcludeMappingImpl>) excludes)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationTableMapping createAggregationName(
        AggregationColumnNameMapping aggregationFactCount,
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
        List<? extends AggregationMeasureMapping> aggregationMeasures,
        List<? extends AggregationLevelMapping> aggregationLevels,
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
        String id, String approxRowCount, TableMapping name
    ) {

        return AggregationNameMappingImpl.builder()
            .withAggregationFactCount((AggregationColumnNameMappingImpl) aggregationFactCount)
            .withAggregationIgnoreColumns((List<AggregationColumnNameMappingImpl>) aggregationIgnoreColumns)
            .withAggregationForeignKeys((List<AggregationForeignKeyMappingImpl>) aggregationForeignKeys)
            .withAggregationMeasures((List<AggregationMeasureMappingImpl>) aggregationMeasures)
            .withAggregationLevels((List<AggregationLevelMappingImpl>) aggregationLevels)
            .withAggregationMeasureFactCounts((List<AggregationMeasureFactCountMappingImpl>) aggregationMeasureFactCounts)
            .withIgnorecase(ignorecase)
            .withId(id)
            .withApproxRowCount(approxRowCount)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TimeDimensionMapping createTimeDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    ) {
        return TimeDimensionMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withHierarchies((List<HierarchyMappingImpl>) hierarchies)
            .withUsagePrefix(usagePrefix)
            .withVisible(visible)
            .build();
    }

    @Override
    protected CubeConnectorMapping createCubeConnector(CubeMapping cube, boolean ignoreUnrelatedDimensions) {
        return CubeConnectorMappingImpl.builder()
            .withCube((CubeMappingImpl) cube)
            .withIgnoreUnrelatedDimensions(ignoreUnrelatedDimensions)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected VirtualCubeMapping createVirtualCube(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends DimensionConnectorMapping> dimensionConnectors,
        List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
        List<? extends KpiMapping> kpis, MemberMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureMapping> referencedMeasures, List<? extends CalculatedMemberMapping> referencedCalculatedMembers,
        List<? extends CubeConnectorMapping> cubeUsages
    ) {
        return VirtualCubeMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withDimensionConnectors((List<DimensionConnectorMappingImpl>) dimensionConnectors)
            .withCalculatedMembers((List<CalculatedMemberMappingImpl>) calculatedMembers)
            .withNamedSets((List<NamedSetMappingImpl>) namedSets)
            .withKpis((List<KpiMappingImpl>) kpis)
            .withDefaultMeasure((MeasureMappingImpl) defaultMeasure)
            .withEnabled(enabled)
            .withVisible(visible)
            .withReferencedMeasures(referencedMeasures)
            .withReferencedCalculatedMembers(referencedCalculatedMembers)
            .withCubeUsages((List<CubeConnectorMappingImpl>) cubeUsages)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PhysicalCubeMapping createPhysicalCube(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends DimensionConnectorMapping> dimensionConnectors,
        List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
        List<? extends KpiMapping> kpis, MemberMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureGroupMapping> measureGroups, QueryMapping query, WritebackTableMapping writebackTable,
        List<? extends ActionMapping> action, boolean cache
    ) {

        return PhysicalCubeMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withDimensionConnectors((List<DimensionConnectorMappingImpl>) dimensionConnectors)
            .withCalculatedMembers((List<CalculatedMemberMappingImpl>) calculatedMembers)
            .withNamedSets((List<NamedSetMappingImpl>) namedSets)
            .withKpis((List<KpiMappingImpl>) kpis)
            .withDefaultMeasure((MeasureMappingImpl) defaultMeasure)
            .withEnabled(enabled)
            .withVisible(visible)
            .withMeasureGroups((List<MeasureGroupMappingImpl>) measureGroups)
            .withQuery((QueryMappingImpl) query)
            .withWritebackTable((WritebackTableMappingImpl) writebackTable)
            .withAction((List<ActionMappingMappingImpl>) action)
            .withCache(cache)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ActionMapping createDrillThroughAction(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends DrillThroughAttributeMapping> drillThroughAttribute,
        List<? extends MeasureMapping> drillThroughMeasure, boolean def
    ) {
        return DrillThroughActionMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withDrillThroughAttribute((List<DrillThroughAttributeMappingImpl>) drillThroughAttribute)
            .withDrillThroughMeasure((List<MeasureMappingImpl>) drillThroughMeasure)
            .withTheDefault(def)
            .build();
    }

    @Override
    protected DrillThroughAttributeMapping createDrillThroughAttribute(
        DimensionMapping dimension, HierarchyMapping hierarchy,
        LevelMapping level, String property
    ) {
        return DrillThroughAttributeMappingImpl.builder()
            .withDimension((DimensionMappingImpl) dimension)
            .withHierarchy((HierarchyMappingImpl) hierarchy)
            .withLevel((LevelMappingImpl) level)
            .withProperty(property)
            .build();
    }

    @Override
    protected WritebackAttributeMapping createWritebackAttribute(ColumnMapping column, DimensionConnectorMapping dimensionConnector) {
        return WritebackAttributeMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .withDimensionConnector((DimensionConnectorMappingImpl) dimensionConnector)
            .build();
    }

    @Override
    protected WritebackMeasureMapping createwritebackMeasure(ColumnMapping column, String name) {
        return WritebackMeasureMappingImpl.builder()
            .withColumn((ColumnMappingImpl) column)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected WritebackTableMapping createWritebackTable(
        List<? extends WritebackAttributeMapping> writebackAttribute,
        List<? extends WritebackMeasureMapping> writebackMeasure, String name, String schema
    ) {
        return WritebackTableMappingImpl.builder()
            .withWritebackAttribute((List<WritebackAttributeMappingImpl>) writebackAttribute)
            .withWritebackMeasure((List<WritebackMeasureMappingImpl>) writebackMeasure)
            .withName(name)
            .withSchema(schema)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberPropertyFormatterMapping createMemberPropertyFormatter(
            List<? extends AnnotationMapping> annotations, String id, String description, String name,
            DocumentationMapping documentation, String ref) {
        return MemberPropertyFormatterMappingImpl.builder()
                .withAnnotations((List<AnnotationMappingImpl>) annotations)
                .withId(id)
                .withDescription(description)
                .withName(name)
                .withDocumentation((DocumentationMappingImpl) documentation)
                .withRef(ref)
                .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SqlViewMapping createSqlView(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description, List<? extends SqlStatementMapping> sqlStatements
    ) {
        SqlViewMappingImpl sqlView = SqlViewMappingImpl.builder().build();
        sqlView.setName(name);
        sqlView.setColumns((List<ColumnMappingImpl>) columns);
        sqlView.setSchema((DatabaseSchemaMappingImpl) schema);
        sqlView.setDescription(description);
        sqlView.setSqlStatements(sqlStatements);
        return sqlView;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected InlineTableMapping createInlineTable(
        String name, List<? extends ColumnMapping> columns, DatabaseSchemaMapping schema,
        String description, List<? extends RowMapping> rows
    ) {
        InlineTableMappingImpl inlineTable = InlineTableMappingImpl.builder().build();
        inlineTable.setName(name);
        inlineTable.setColumns((List<ColumnMappingImpl>) columns);
        inlineTable.setSchema((DatabaseSchemaMappingImpl) schema);
        inlineTable.setDescription(description);
        inlineTable.setRows(rows);
        return inlineTable;
    }

    @Override
    protected RowValueMapping createRowValue(ColumnMapping column, String value) {
        RowValueMappingImpl rowValue = RowValueMappingImpl.builder().build();
        rowValue.setColumn((ColumnMappingImpl) column);
        rowValue.setValue(value);
        return rowValue;
    }

    @Override
    protected RowMapping createRow(List<? extends RowValueMapping> rowValues) {
        RowMappingImpl row = RowMappingImpl.builder().build();
        row.setRowValues(rowValues);
        return row;
    }

    @Override
    protected LinkMapping createLink(ColumnMapping primaryKey, ColumnMapping foreignKey) {
        LinkMappingImpl link = LinkMappingImpl.builder().withPrimaryKey((ColumnMappingImpl) primaryKey)
                .withForeignKey((ColumnMappingImpl) foreignKey).build();
        return link;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseCatalogMapping createDatabaseCatalog(List<? extends DatabaseSchemaMapping> schemas,
            List<? extends LinkMapping> links) {
        DatabaseCatalogImpl databaseCatalog = DatabaseCatalogImpl.builder()
                .withSchemas((List<DatabaseSchemaMappingImpl>) schemas)
                .withLinks((List<LinkMappingImpl>) links).build();
        return databaseCatalog;
    }

}
