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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rdb.structure.api.model.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.api.model.Table;
import org.eclipse.daanse.rdb.structure.pojo.AbstractTable;
import org.eclipse.daanse.rdb.structure.pojo.ColumnImpl;
import org.eclipse.daanse.rdb.structure.pojo.DatabaseSchemaImpl;
import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl;
import org.eclipse.daanse.rdb.structure.pojo.SystemTableImpl;
import org.eclipse.daanse.rdb.structure.pojo.ViewTableImpl;
import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ActionMappingMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableColumnDefinitionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowCellMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.KpiMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberReaderParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.StandardDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TimeDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCubeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimensionEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchyEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMemberGrantEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessSchemaEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.PropertyTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.TypeEnum;
import org.eclipse.daanse.rolap.mapping.modifier.common.AbstractMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessSchemaGrantMappingImpl;
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
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CellFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DrillThroughActionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DrillThroughAttributeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableColumnDefinitionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowCellMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.KpiMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
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
import org.eclipse.daanse.rolap.mapping.pojo.QueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlSelectQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryOptimizationHintMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TranslationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.VirtualCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackAttributeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackTableMappingImpl;

public class PojoMappingModifier extends AbstractMappingModifier {

    public PojoMappingModifier(CatalogMapping catalog) {
        super(catalog);
    }

    @Override
    protected Column createColumn(
        String name, Table table, String type, List<String> typeQualifiers,
        String description
    ) {
        ColumnImpl column = new ColumnImpl();
        column.setName(name);
        column.setTable(table);
        column.setType(type);
        column.setTypeQualifiers(typeQualifiers);
        column.setDescription(description);
        return column;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createPhysicalTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        PhysicalTableImpl physicalTableImpl = new PhysicalTableImpl();
        physicalTableImpl.setName(name);
        physicalTableImpl.setColumns((List<ColumnImpl>) columns);
        physicalTableImpl.setSchema((DatabaseSchemaImpl) schema);
        physicalTableImpl.setDescription(description);
        return physicalTableImpl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createSystemTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        SystemTableImpl systemTableImpl = new SystemTableImpl();
        systemTableImpl.setName(name);
        systemTableImpl.setColumns((List<ColumnImpl>) columns);
        systemTableImpl.setSchema((DatabaseSchemaImpl) schema);
        systemTableImpl.setDescription(description);
        return systemTableImpl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createViewTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        ViewTableImpl viewTableImpl = new ViewTableImpl();
        viewTableImpl.setName(name);
        viewTableImpl.setColumns((List<ColumnImpl>) columns);
        viewTableImpl.setSchema((DatabaseSchemaImpl) schema);
        viewTableImpl.setDescription(description);
        return viewTableImpl;

    }

    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseSchema createDatabaseSchema(List<? extends Table> tables, String name, String id) {
        DatabaseSchemaImpl databaseSchema = new DatabaseSchemaImpl();
        databaseSchema.setTables((List<AbstractTable>) tables);
        databaseSchema.setName(name);
        databaseSchema.setId(id);
        return databaseSchema;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CatalogMapping createCatalog(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, DocumentationMapping documentation, List<? extends SchemaMapping> schemas,
        List<? extends DatabaseSchema> dbschemas
    ) {
        return CatalogMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withSchemas((List<SchemaMappingImpl>) schemas)
            .build();
    }


    @SuppressWarnings("unchecked")
    @Override
    protected AccessRoleMapping createAccessRole(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends AccessSchemaGrantMapping> accessSchemaGrants,
        List<? extends AccessRoleMapping> referencedAccessRoles
    ) {
        return AccessRoleMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withAccessSchemaGrants((List<AccessSchemaGrantMappingImpl>) accessSchemaGrants)
            .withReferencedAccessRoles((List<AccessRoleMappingImpl>) referencedAccessRoles)
            .build();
    }

    @Override
    protected AccessMemberGrantMapping createAccessMemberGrant(AccessMemberGrantEnum access, String member) {
        return AccessMemberGrantMappingImpl.builder()
            .withAccess(access)
            .withMember(member)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryMapping createInlineTableQuery(
        String alias,
        List<? extends InlineTableColumnDefinitionMapping> columnDefinitions,
        List<? extends InlineTableRowMappingMapping> rows
    ) {
        return InlineTableQueryMappingImpl.builder()
            .withAlias(alias)
            .withColumnDefinitions((List<InlineTableColumnDefinitionMappingImpl>) columnDefinitions)
            .withRows((List<InlineTableRowMappingImpl>) rows)
            .build();
    }

    @Override
    protected InlineTableRowCellMapping createInlineTableRowCell(String value, String columnName) {
        return InlineTableRowCellMappingImpl.builder()
            .withValue(value)
            .withColumnName(columnName)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected InlineTableRowMappingMapping createInlineTableRowMapping(
        List<? extends InlineTableRowCellMapping> cells
    ) {
        return InlineTableRowMappingImpl.builder()
            .withCells((List<InlineTableRowCellMappingImpl>) cells)
            .build();
    }

    @Override
    protected InlineTableColumnDefinitionMapping createInlineTableColumnDefinition(String name, TypeEnum type) {
        return InlineTableColumnDefinitionMappingImpl.builder()
            .withName(name)
            .withType(type)
            .build();
    }

    @Override
    protected QueryMapping createJoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right) {
        return JoinQueryMappingImpl.builder()
            .withLeft((JoinedQueryElementMappingImpl) left)
            .withRight((JoinedQueryElementMappingImpl) right)
            .build();
    }

    @Override
    protected JoinedQueryElementMapping createJoinedQueryElement(String alias, String key, QueryMapping query) {
        return JoinedQueryElementMappingImpl.builder()
            .withAlias(alias)
            .withKey(key)
            .withQuery((QueryMappingImpl) query)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryMapping createSqlSelectQuery(String alias, List<? extends SQLMapping> sqls) {
        return SqlSelectQueryMappingImpl.builder()
            .withAlias(alias)
            .withSql((List<SQLMappingImpl>) sqls)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableQueryMapping createTableQuery(
        String alias, SQLMapping sqlWhereExpression,
        List<? extends AggregationExcludeMapping> aggregationExcludes,
        List<? extends TableQueryOptimizationHintMapping> optimizationHints, String name, String schema,
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        return TableQueryMappingImpl.builder()
            .withAlias(alias)
            .withSqlWhereExpression((SQLMappingImpl) sqlWhereExpression)
            .withAggregationExcludes((List<AggregationExcludeMappingImpl>) aggregationExcludes)
            .withOptimizationHints((List<TableQueryOptimizationHintMappingImpl>) optimizationHints)
            .withName(name)
            .withSchema(schema)
            .build();
    }

    @Override
    protected AggregationMeasureFactCountMapping createAggregationMeasureFactCount(String column, String factColumn) {
        return AggregationMeasureFactCountMappingImpl.builder()
            .withColumn(column)
            .withFactColumn(factColumn)
            .build();
    }

    @Override
    protected AggregationLevelPropertyMapping createAggregationLevelProperty(String column, String name) {
        return AggregationLevelPropertyMappingImpl.builder()
            .withColumn(column)
            .withName(name)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationLevelMapping createAggregationLevel(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, String captionColumn,
        boolean collapsed, String column, String name, String nameColumn, String ordinalColumn
    ) {
        return AggregationLevelMappingImpl.builder()
            .withAggregationLevelProperties((List<AggregationLevelPropertyMappingImpl>) aggregationLevelProperties)
            .withCaptionColumn(captionColumn)
            .withCollapsed(collapsed)
            .withColumn(column)
            .withName(name)
            .withNameColumn(nameColumn)
            .withOrdinalColumn(ordinalColumn)
            .build();
    }

    @Override
    protected AggregationMeasureMapping createAggregationMeasure(String column, String name, String rollupType) {
        return AggregationMeasureMappingImpl.builder()
            .withColumn(column)
            .withName(name)
            .withRollupType(rollupType)
            .build();
    }

    @Override
    protected AggregationForeignKeyMapping createAggregationForeignKey(String aggregationColumn, String factColumn) {
        return AggregationForeignKeyMappingImpl.builder()
            .withAggregationColumn(aggregationColumn)
            .withFactColumn(factColumn)
            .build();
    }

    @Override
    protected AggregationColumnNameMapping createAggregationColumn(String column) {
        return AggregationColumnNameMappingImpl.builder()
            .withColumn(column)
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
    protected SQLMapping createSQL(List<String> dialects, String statement) {
        return SQLMappingImpl.builder()
            .withDialects(dialects)
            .withStatement(statement)
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
        String memberReaderClass, String origin, String primaryKey, String primaryKeyTable,
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
            .withPrimaryKey(primaryKey)
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
        MemberPropertyFormatterMapping formatter, String column, boolean dependsOnLevelValue, PropertyTypeEnum type
    ) {
        return MemberPropertyMappingImpl.builder()
            .withAnnotations((List<AnnotationMappingImpl>) annotations)
            .withId(id)
            .withDescription(description)
            .withName(name)
            .withDocumentation((DocumentationMappingImpl) documentation)
            .withFormatter((MemberPropertyFormatterMappingImpl) formatter)
            .withColumn(column)
            .withDependsOnLevelValue(dependsOnLevelValue)
            .withType(type)
            .build();
    }

    @Override
    protected ParentChildLinkMapping createParentChildLink(
        TableQueryMapping table, String childColumn,
        String parentColumn
    ) {
        return ParentChildLinkMappingImpl.builder()
            .withTable((TableQueryMappingImpl) table)
            .withChildColumn(childColumn)
            .withParentColumn(parentColumn)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLExpressionMapping createSQLExpression(List<? extends SQLMapping> sqls) {
        return SQLExpressionMappingImpl.builder()
            .withSqls((List<SQLMappingImpl>) sqls)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LevelMapping createLevel(
        SQLExpressionMapping keyExpression, SQLExpressionMapping nameExpression,
        SQLExpressionMapping captionExpression, SQLExpressionMapping ordinalExpression,
        SQLExpressionMapping parentExpression, ParentChildLinkMapping parentChildLink,
        List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
        String approxRowCount, String captionColumn, String column, HideMemberIfEnum hideMemberIf, InternalTypeEnum internalType,
        LevelTypeEnum levelType, String nameColumn, String nullParentValue, String ordinalColumn, String parentColumn,
        String table, TypeEnum type, boolean uniqueMembers, boolean visible, String name, String id
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
            .withCaptionColumn(captionColumn)
            .withColumn(column)
            .withHideMemberIf(hideMemberIf)
            .withInternalType(internalType)
            .withLevelType(levelType)
            .withNameColumn(nameColumn)
            .withNullParentValue(nullParentValue)
            .withOrdinalColumn(ordinalColumn)
            .withParentColumn(parentColumn)
            .withTable(table)
            .withType(type)
            .withUniqueMembers(uniqueMembers)
            .withVisible(visible)
            .withName(name)
            .withId(id)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessHierarchyGrantMapping createAccessHierarchyGrant(
        List<? extends AccessMemberGrantMapping> memberGrants, AccessHierarchyEnum access, LevelMapping bottomLevel,
        String rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy
    ) {
        return AccessHierarchyGrantMappingImpl.builder()
            .withMemberGrants((List<AccessMemberGrantMappingImpl>) memberGrants)
            .withAccess(access)
            .withBottomLevel((LevelMappingImpl) bottomLevel)
            .withRollupPolicy(rollupPolicy)
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
    protected AccessDimensionGrantMapping createAccessDimensionGrant(AccessDimensionEnum access, DimensionMapping dimension) {
        return AccessDimensionGrantMappingImpl.builder()
            .withAccess(access)
            .withDimension((DimensionMappingImpl) dimension)
            .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCubeGrantMapping createAccessCubeGrant(
        List<? extends AccessDimensionGrantMapping> dimensionGrants,
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants, AccessCubeEnum access, CubeMapping cube
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
    protected AccessSchemaGrantMapping createAccessSchemaGrant(
        List<? extends AccessCubeGrantMapping> accessCubeGrant,
        AccessSchemaEnum access
    ) {
        return AccessSchemaGrantMappingImpl.builder()
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
        CellFormatterMapping cellFormatter, String backColor, String column, MeasureDataTypeEnum datatype, String displayFolder,
        String formatString, String formatter, boolean visible, String name, String id, String type
    ) {
        return MeasureMappingImpl.builder()
            .withMeasureExpression((SQLExpressionMappingImpl) measureExpression)
            .withCalculatedMemberProperty((List<CalculatedMemberPropertyMappingImpl>) calculatedMemberProperty)
            .withCellFormatter((CellFormatterMappingImpl) cellFormatter)
            .withBackColor(backColor)
            .withColumn(column)
            .withDatatype(datatype)
            .withDisplayFolder(displayFolder)
            .withFormatString(formatString)
            .withFormatter(formatter)
            .withVisible(visible)
            .withName(name)
            .withId(id)
            .withType(type)
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
        String foreignKey, LevelMapping level,
        String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName,
        PhysicalCubeMapping physicalCube
    ) {
        return DimensionConnectorMappingImpl.builder()
            .withForeignKey(foreignKey)
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
    protected SchemaMapping createSchema(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, DocumentationMapping documentation, List<? extends ParameterMapping> parameters,
        List<? extends CubeMapping> cubes, List<? extends NamedSetMapping> namedSets,
        List<? extends AccessRoleMapping> accessRoles, AccessRoleMapping defaultAccessRole,
        String measuresDimensionName
    ) {
        return SchemaMappingImpl.builder()
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
        String id, String approxRowCount, String name
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
        List<? extends KpiMapping> kpis, MeasureMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureGroupMapping> measureGroups, List<? extends CubeConnectorMapping> cubeUsages
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
            .withMeasureGroups((List<MeasureGroupMappingImpl>) measureGroups)
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
        List<? extends KpiMapping> kpis, MeasureMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureGroupMapping> measureGroups, QueryMapping query, WritebackTableMapping writebackTable,
        List<? extends ActionMappingMapping> action, boolean cache
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
    protected ActionMappingMapping createDrillThroughAction(
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
    protected WritebackAttributeMapping createWritebackAttribute(String column, DimensionMapping dimension) {
        return WritebackAttributeMappingImpl.builder()
            .withColumn(column)
            .withDimension((DimensionMappingImpl) dimension)
            .build();
    }

    @Override
    protected WritebackMeasureMapping createwritebackMeasure(String column, String name) {
        return WritebackMeasureMappingImpl.builder()
            .withColumn(column)
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

}
