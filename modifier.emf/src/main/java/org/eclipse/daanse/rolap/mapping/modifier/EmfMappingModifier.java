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
package org.eclipse.daanse.rolap.mapping.modifier;

import java.util.Collection;
import java.util.List;

import org.eclipse.daanse.emf.model.rdbstructure.RelationalDatabaseFactory;
import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rdb.structure.api.model.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.api.model.Table;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Action;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevelProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationPattern;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Annotation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Cube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableColumnDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRow;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRowCell;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberReaderParameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Parameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Query;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQL;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpression;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Translation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;

public class EmfMappingModifier extends AbstractMappingModifier {

    protected EmfMappingModifier(CatalogMapping catalog) {
        super(catalog);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createViewTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        org.eclipse.daanse.emf.model.rdbstructure.ViewTable table =
            RelationalDatabaseFactory.eINSTANCE.createViewTable();
        table.setName(name);
        table.getColumns().addAll((Collection<? extends org.eclipse.daanse.emf.model.rdbstructure.Column>) columns);
        table.setSchema((org.eclipse.daanse.emf.model.rdbstructure.DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createSystemTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        org.eclipse.daanse.emf.model.rdbstructure.SystemTable table =
            RelationalDatabaseFactory.eINSTANCE.createSystemTable();
        table.setName(name);
        table.getColumns().addAll((Collection<? extends org.eclipse.daanse.emf.model.rdbstructure.Column>) columns);
        table.setSchema((org.eclipse.daanse.emf.model.rdbstructure.DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createPhysicalTable(
        String name, List<? extends Column> columns, DatabaseSchema schema,
        String description
    ) {
        org.eclipse.daanse.emf.model.rdbstructure.PhysicalTable table =
            RelationalDatabaseFactory.eINSTANCE.createPhysicalTable();
        table.setName(name);
        table.getColumns().addAll((Collection<? extends org.eclipse.daanse.emf.model.rdbstructure.Column>) columns);
        table.setSchema((org.eclipse.daanse.emf.model.rdbstructure.DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @Override
    protected Column createColumn(
        String name, Table table, String type, List<String> typeQualifiers,
        String description
    ) {
        org.eclipse.daanse.emf.model.rdbstructure.Column column = RelationalDatabaseFactory.eINSTANCE.createColumn();
        column.setName(name);
        column.setTable((org.eclipse.daanse.emf.model.rdbstructure.Table) table);
        column.setType(type);
        column.getTypeQualifiers().addAll(typeQualifiers);
        column.setDescription(description);
        return column;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseSchema createDatabaseSchema(List<? extends Table> tables, String name, String id) {
        org.eclipse.daanse.emf.model.rdbstructure.DatabaseSchema databaseSchema =
            RelationalDatabaseFactory.eINSTANCE.createDatabaseSchema();
        databaseSchema.getTables().addAll((Collection<? extends org.eclipse.daanse.emf.model.rdbstructure.Table>) tables);
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
        Catalog catalog = RolapMappingFactory.eINSTANCE.createCatalog();
        catalog.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        catalog.setId(id);
        catalog.setDescription(description);
        catalog.setName(name);
        catalog.setDocumentation((Documentation) documentation);
        catalog.getSchemas().addAll((Collection<? extends Schema>) schemas);
        //??
        catalog.getDbschermas().addAll((Collection<? extends org.eclipse.daanse.rdb.structure.emf.rdbstructure.DatabaseSchema>) dbschemas);
        return catalog;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessRoleMapping createAccessRole(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends AccessSchemaGrantMapping> accessSchemaGrants,
        List<? extends AccessRoleMapping> referencedAccessRoles
    ) {
        AccessRole accessRole = RolapMappingFactory.eINSTANCE.createAccessRole();
        accessRole.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        accessRole.setId(id);
        accessRole.setDescription(description);
        accessRole.setName(name);
        accessRole.setDocumentation((Documentation) documentation);
        accessRole.getAccessSchemaGrants().addAll((Collection<? extends AccessSchemaGrant>) accessSchemaGrants);
        accessRole.getReferencedAccessRoles().addAll((Collection<? extends AccessRole>) referencedAccessRoles);
        return accessRole;
    }

    @Override
    protected AccessMemberGrantMapping createAccessMemberGrant(String access, String member) {
        AccessMemberGrant accessMemberGrant = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        accessMemberGrant.setAccess(access);
        accessMemberGrant.setMember(member);
        return accessMemberGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryMapping createInlineTableQuery(
        String alias,
        List<? extends InlineTableColumnDefinitionMapping> columnDefinitions,
        List<? extends InlineTableRowMappingMapping> rows
    ) {
        InlineTableQuery inlineTableQuery = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        inlineTableQuery.setAlias(alias);
        inlineTableQuery.getColumnDefinitions().addAll((Collection<? extends InlineTableColumnDefinition>) columnDefinitions);
        inlineTableQuery.getRows().addAll((Collection<? extends InlineTableRow>) rows);
        return inlineTableQuery;
    }

    @Override
    protected InlineTableRowCellMapping createInlineTableRowCell(String value, String columnName) {
        InlineTableRowCell inlineTableRowCell = RolapMappingFactory.eINSTANCE.createInlineTableRowCell();
        inlineTableRowCell.setValue(value);
        inlineTableRowCell.setColumnName(columnName);
        return inlineTableRowCell;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected InlineTableRowMappingMapping createInlineTableRowMapping(
        List<? extends InlineTableRowCellMapping> cells
    ) {
        InlineTableRow inlineTableRow = RolapMappingFactory.eINSTANCE.createInlineTableRow();
        inlineTableRow.getCells().addAll((Collection<? extends InlineTableRowCell>) cells);
        return inlineTableRow;
    }

    @Override
    protected InlineTableColumnDefinitionMapping createInlineTableColumnDefinition(String name, String type) {
        InlineTableColumnDefinition inlineTableColumnDefinition =
            RolapMappingFactory.eINSTANCE.createInlineTableColumnDefinition();
        inlineTableColumnDefinition.setName(name);
        inlineTableColumnDefinition.setType(type);
        return inlineTableColumnDefinition;
    }

    @Override
    protected QueryMapping createJoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right) {
        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setLeft((JoinedQueryElement) left);
        joinQuery.setRight((JoinedQueryElement) right);
        return joinQuery;
    }

    @Override
    protected JoinedQueryElementMapping createJoinedQueryElement(String alias, String key, QueryMapping query) {
        JoinedQueryElement joinedQueryElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinedQueryElement.setAlias(alias);
        joinedQueryElement.setKey(key);
        joinedQueryElement.setQuery((Query) query);
        return joinedQueryElement;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryMapping createSqlSelectQuery(String alias, List<? extends SQLMapping> sqls) {
        SqlSelectQuery sqlSelectQuery = RolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        sqlSelectQuery.setAlias(alias);
        sqlSelectQuery.getSQL().addAll((Collection<? extends SQL>) sqls);
        return sqlSelectQuery;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableQueryMapping createTableQuery(
        String alias, SQLMapping sqlWhereExpression,
        List<? extends AggregationExcludeMapping> aggregationExcludes,
        List<? extends TableQueryOptimizationHintMapping> optimizationHints, String name, String schema,
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        TableQuery tableQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuery.setAlias(schema);
        tableQuery.setSqlWhereExpression((SQL) sqlWhereExpression);
        tableQuery.getAggregationExcludes().addAll((Collection<? extends AggregationExclude>) aggregationExcludes);
        tableQuery.getOptimizationHints().addAll((Collection<? extends TableQueryOptimizationHint>) optimizationHints);
        tableQuery.setName(name);
        tableQuery.setSchema(schema);
        tableQuery.getAggregationTables().addAll((Collection<? extends AggregationTable>) aggregationTables);
        return tableQuery;
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
        AggregationPattern aggregationPattern = RolapMappingFactory.eINSTANCE.createAggregationPattern();
        aggregationPattern.setAggregationFactCount((AggregationColumnName) aggregationFactCount);
        aggregationPattern.getAggregationIgnoreColumns().addAll((Collection<? extends AggregationColumnName>) aggregationIgnoreColumns);
        aggregationPattern.getAggregationForeignKeys().addAll((Collection<? extends AggregationForeignKey>) aggregationForeignKeys);
        aggregationPattern.getAggregationMeasures().addAll((Collection<? extends AggregationMeasure>) aggregationMeasures);
        aggregationPattern.getAggregationLevels().addAll((Collection<? extends AggregationLevel>) aggregationLevels);
        aggregationPattern.getAggregationMeasureFactCounts().addAll((Collection<? extends AggregationMeasureFactCount>) aggregationMeasureFactCounts);
        aggregationPattern.setIgnorecase(ignorecase);
        aggregationPattern.setId(id);
        aggregationPattern.setPattern(pattern);
        aggregationPattern.getExcludes().addAll((Collection<? extends AggregationExclude>) excludes);
        return aggregationPattern;
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
        AggregationName aggregationName = RolapMappingFactory.eINSTANCE.createAggregationName();
        aggregationName.setAggregationFactCount((AggregationColumnName) aggregationFactCount);
        aggregationName.getAggregationIgnoreColumns().addAll((Collection<? extends AggregationColumnName>) aggregationIgnoreColumns);
        aggregationName.getAggregationForeignKeys().addAll((Collection<? extends AggregationForeignKey>) aggregationForeignKeys);
        aggregationName.getAggregationMeasures().addAll((Collection<? extends AggregationMeasure>) aggregationMeasures);
        aggregationName.getAggregationLevels().addAll((Collection<? extends AggregationLevel>) aggregationLevels);
        aggregationName.getAggregationMeasureFactCounts().addAll((Collection<? extends AggregationMeasureFactCount>) aggregationMeasureFactCounts);
        aggregationName.setIgnorecase(ignorecase);
        aggregationName.setId(id);
        aggregationName.setApproxRowCount(approxRowCount);
        aggregationName.setName(name);
        return aggregationName;
    }

    @Override
    protected AggregationMeasureFactCountMapping createAggregationMeasureFactCount(String column, String factColumn) {
        AggregationMeasureFactCount aggregationMeasureFactCount =
            RolapMappingFactory.eINSTANCE.createAggregationMeasureFactCount();
        aggregationMeasureFactCount.setColumn(column);
        aggregationMeasureFactCount.setFactColumn(factColumn);
        return aggregationMeasureFactCount;
    }

    @Override
    protected AggregationLevelPropertyMapping createAggregationLevelProperty(String column, String name) {
        AggregationLevelProperty aggregationLevelProperty =
            RolapMappingFactory.eINSTANCE.createAggregationLevelProperty();
        aggregationLevelProperty.setColumn(column);
        aggregationLevelProperty.setName(name);
        return aggregationLevelProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationLevelMapping createAggregationLevel(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, String captionColumn,
        boolean collapsed, String column, String name, String nameColumn, String ordinalColumn
    ) {
        AggregationLevel aggregationLevel = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        aggregationLevel.getAggregationLevelProperties().addAll((Collection<? extends AggregationLevelProperty>) aggregationLevelProperties);
        aggregationLevel.setCaptionColumn(captionColumn);
        aggregationLevel.setCollapsed(collapsed);
        aggregationLevel.setColumn(column);
        aggregationLevel.setName(name);
        aggregationLevel.setNameColumn(nameColumn);
        aggregationLevel.setOrdinalColumn(ordinalColumn);
        return aggregationLevel;
    }

    @Override
    protected AggregationMeasureMapping createAggregationMeasure(String column, String name, String rollupType) {
        AggregationMeasure aggregationMeasure = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        aggregationMeasure.setColumn(column);
        aggregationMeasure.setName(name);
        aggregationMeasure.setRollupType(rollupType);
        return aggregationMeasure;
    }

    @Override
    protected AggregationForeignKeyMapping createAggregationForeignKey(String aggregationColumn, String factColumn) {
        AggregationForeignKey aggregationForeignKey = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        aggregationForeignKey.setAggregationColumn(aggregationColumn);
        aggregationForeignKey.setFactColumn(factColumn);
        return aggregationForeignKey;
    }

    @Override
    protected AggregationColumnNameMapping createAggregationColumn(String column) {
        AggregationColumnName aggregationColumnName = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        aggregationColumnName.setColumn(column);
        return aggregationColumnName;
    }

    @Override
    protected TableQueryOptimizationHintMapping createTableQueryOptimizationHint(String value, String type) {
        TableQueryOptimizationHint tableQueryOptimizationHint =
            RolapMappingFactory.eINSTANCE.createTableQueryOptimizationHint();
        tableQueryOptimizationHint.setValue(value);
        tableQueryOptimizationHint.setType(type);
        return tableQueryOptimizationHint;
    }

    @Override
    protected AggregationExcludeMapping createAggregationExclude(
        boolean ignorecase, String name, String pattern,
        String id
    ) {
        AggregationExclude aggregationExclude = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude.setIgnorecase(ignorecase);
        aggregationExclude.setName(name);
        aggregationExclude.setPattern(pattern);
        aggregationExclude.setId(id);
        return aggregationExclude;
    }

    @Override
    protected SQLMapping createSQL(List<String> dialects, String statement) {
        SQL sql = RolapMappingFactory.eINSTANCE.createSQL();
        sql.getDialects().addAll(dialects);
        sql.setStatement(statement);
        return sql;
    }

    @Override
    protected MemberReaderParameterMapping createMemberReaderParameter(String name, String value) {
        MemberReaderParameter memberReaderParameter = RolapMappingFactory.eINSTANCE.createMemberReaderParameter();
        memberReaderParameter.setName(name);
        memberReaderParameter.setValue(value);
        return memberReaderParameter;
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
        Hierarchy hierarchy = RolapMappingFactory.eINSTANCE.createHierarchy();
        hierarchy.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        hierarchy.setId(id);
        hierarchy.setDescription(description);
        hierarchy.setName(name);
        hierarchy.setDocumentation((Documentation) documentation);
        hierarchy.getLevels().addAll((Collection<? extends Level>) levels);
        hierarchy.getMemberReaderParameters().addAll((Collection<? extends MemberReaderParameter>) memberReaderParameters);
        hierarchy.setAllLevelName(allLevelName);
        hierarchy.setAllMemberCaption(allMemberCaption);
        hierarchy.setAllMemberName(allMemberName);
        hierarchy.setDefaultMember(defaultMember);
        hierarchy.setDisplayFolder(displayFolder);
        hierarchy.setHasAll(hasAll);
        hierarchy.setMemberReaderClass(memberReaderClass);
        hierarchy.setOrigin(origin);
        hierarchy.setPrimaryKey(primaryKey);
        hierarchy.setPrimaryKeyTable(primaryKeyTable);
        hierarchy.setUniqueKeyLevelName(uniqueKeyLevelName);
        hierarchy.setVisible(visible);
        hierarchy.setQuery((Query) query);
        return hierarchy;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberFormatterMapping createMemberFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String ref
    ) {
        MemberFormatter memberFormatter = RolapMappingFactory.eINSTANCE.createMemberFormatter();
        memberFormatter.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        memberFormatter.setId(id);
        memberFormatter.setDescription(description);
        memberFormatter.setName(name);
        memberFormatter.setDocumentation((Documentation) documentation);
        memberFormatter.setRef(ref);
        return memberFormatter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberPropertyMapping createMemberProperty(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        MemberPropertyFormatterMapping formatter, String column, boolean dependsOnLevelValue, String type
    ) {
        MemberProperty memberProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        memberProperty.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        memberProperty.setId(id);
        memberProperty.setDescription(description);
        memberProperty.setName(name);
        memberProperty.setDocumentation((Documentation) documentation);
        memberProperty.setFormatter((MemberPropertyFormatter) formatter);
        memberProperty.setColumn(column);
        memberProperty.setDependsOnLevelValue(dependsOnLevelValue);
        memberProperty.setType(type);
        return memberProperty;
    }

    @Override
    protected ParentChildLinkMapping createParentChildLink(
        TableQueryMapping table, String childColumn,
        String parentColumn
    ) {
        ParentChildLink parentChildLink = RolapMappingFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setTable((TableQuery) table);
        parentChildLink.setChildColumn(childColumn);
        parentChildLink.setParentColumn(parentColumn);
        return parentChildLink;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLExpressionMapping createSQLExpression(List<? extends SQLMapping> sqls) {
        SQLExpression sqlExpression = RolapMappingFactory.eINSTANCE.createSQLExpression();
        sqlExpression.getSqls().addAll((Collection<? extends SQL>) sqls);
        return sqlExpression;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LevelMapping createLevel(
        SQLExpressionMapping keyExpression, SQLExpressionMapping nameExpression,
        SQLExpressionMapping captionExpression, SQLExpressionMapping ordinalExpression,
        SQLExpressionMapping parentExpression, ParentChildLinkMapping parentChildLink,
        List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
        String approxRowCount, String captionColumn, String column, String hideMemberIf, String internalType,
        String levelType, String nameColumn, String nullParentValue, String ordinalColumn, String parentColumn,
        String table, String type, boolean uniqueMembers, boolean visible, String name, String id
    ) {
        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.setKeyExpression((SQLExpression) keyExpression);
        level.setNameExpression((SQLExpression) nameExpression);
        level.setCaptionExpression((SQLExpression) captionExpression);
        level.setOrdinalExpression((SQLExpression) ordinalExpression);
        level.setParentExpression((SQLExpression) parentExpression);
        level.setParentChildLink((ParentChildLink) parentChildLink);
        level.getMemberProperties().addAll((Collection<? extends MemberProperty>) memberProperties);
        level.setMemberFormatter((MemberFormatter) memberFormatter);
        level.setApproxRowCount(approxRowCount);
        level.setCaptionColumn(captionColumn);
        level.setColumn(column);
        level.setHideMemberIf(hideMemberIf);
        level.setInternalType(internalType);
        level.setLevelType(levelType);
        level.setNameColumn(nameColumn);
        level.setNullParentValue(nullParentValue);
        level.setOrdinalColumn(ordinalColumn);
        level.setParentColumn(parentColumn);
        level.setTable(table);
        level.setType(type);
        level.setUniqueMembers(uniqueMembers);
        level.setVisible(visible);
        level.setName(name);
        level.setId(id);
        return level;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessHierarchyGrantMapping createAccessHierarchyGrant(
        List<? extends AccessMemberGrantMapping> memberGrants, String access, LevelMapping bottomLevel,
        String rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy
    ) {
        AccessHierarchyGrant accessHierarchyGrant = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        accessHierarchyGrant.getMemberGrants().addAll((Collection<? extends AccessMemberGrant>) memberGrants);
        accessHierarchyGrant.setAccess(access);
        accessHierarchyGrant.setBottomLevel((Level) bottomLevel);
        accessHierarchyGrant.setRollupPolicy(rollupPolicy);
        accessHierarchyGrant.setTopLevel((Level) topLevel);
        accessHierarchyGrant.setHierarchy((Hierarchy) hierarchy);
        return accessHierarchyGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TimeDimensionMapping createTimeDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    ) {
        TimeDimension timeDimension = RolapMappingFactory.eINSTANCE.createTimeDimension();
        timeDimension.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        timeDimension.setId(id);
        timeDimension.setDescription(description);
        timeDimension.setName(name);
        timeDimension.setDocumentation((Documentation) documentation);
        timeDimension.getHierarchies().addAll((Collection<? extends Hierarchy>) hierarchies);
        timeDimension.setUsagePrefix(usagePrefix);
        timeDimension.setVisible(visible);
        return timeDimension;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected StandardDimensionMapping createStandardDimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    ) {
        StandardDimension standardDimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        standardDimension.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        standardDimension.setId(id);
        standardDimension.setDescription(description);
        standardDimension.setName(name);
        standardDimension.setDocumentation((Documentation) documentation);
        standardDimension.getHierarchies().addAll((Collection<? extends Hierarchy>) hierarchies);
        standardDimension.setUsagePrefix(usagePrefix);
        standardDimension.setVisible(visible);
        return standardDimension;
    }

    @Override
    protected AccessDimensionGrantMapping createAccessDimensionGrant(String access, DimensionMapping dimension) {
        AccessDimensionGrant accessDimensionGrant = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        accessDimensionGrant.setAccess(access);
        accessDimensionGrant.setDimension((Dimension) dimension);
        return accessDimensionGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCubeGrantMapping createAccessCubeGrant(
        List<? extends AccessDimensionGrantMapping> dimensionGrants,
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants, String access, CubeMapping cube
    ) {
        AccessCubeGrant accessCubeGrant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant.getDimensionGrants().addAll((Collection<? extends AccessDimensionGrant>) dimensionGrants);
        accessCubeGrant.getHierarchyGrants().addAll((Collection<? extends AccessHierarchyGrant>) hierarchyGrants);
        accessCubeGrant.setAccess(access);
        accessCubeGrant.setCube((Cube) cube);
        return accessCubeGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessSchemaGrantMapping createAccessSchemaGrant(
        List<? extends AccessCubeGrantMapping> accessCubeGrant,
        String access
    ) {
        AccessSchemaGrant accessSchemaGrant = RolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrant.getCubeGrant().addAll((Collection<? extends AccessCubeGrant>) accessCubeGrant);
        accessSchemaGrant.setAccess(access);
        return accessSchemaGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected NamedSetMapping createNamedSet(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String displayFolder, String formula
    ) {
        NamedSet namedSet = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        namedSet.setId(id);
        namedSet.setDescription(description);
        namedSet.setName(name);
        namedSet.setDocumentation((Documentation) documentation);
        namedSet.setDisplayFolder(displayFolder);
        namedSet.setFormula(formula);
        return namedSet;
    }

    @Override
    protected CubeConnectorMapping createCubeConnector(CubeMapping cube, boolean ignoreUnrelatedDimensions) {
        CubeConnector cubeConnector = RolapMappingFactory.eINSTANCE.createCubeConnector();
        cubeConnector.setCube((Cube) cube);
        cubeConnector.setIgnoreUnrelatedDimensions(ignoreUnrelatedDimensions);
        return cubeConnector;
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
        VirtualCube virtualCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        virtualCube.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        virtualCube.setId(id);
        virtualCube.setDescription(description);
        virtualCube.setName(name);
        virtualCube.setDocumentation((Documentation) documentation);
        virtualCube.getDimensionConnectors().addAll((Collection<? extends DimensionConnector>) dimensionConnectors);
        virtualCube.getCalculatedMembers().addAll((Collection<? extends CalculatedMember>) calculatedMembers);
        virtualCube.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        virtualCube.getKpis().addAll((Collection<? extends Kpi>) kpis);
        virtualCube.setDefaultMeasure((Measure) defaultMeasure);
        virtualCube.setEnabled(enabled);
        virtualCube.setVisible(visible);
        virtualCube.getMeasureGroups().addAll((Collection<? extends MeasureGroup>) measureGroups);
        virtualCube.getCubeUsages().addAll((Collection<? extends CubeConnector>) cubeUsages);
        return virtualCube;
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
        PhysicalCube physicalCube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        physicalCube.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        physicalCube.setId(id);
        physicalCube.setDescription(description);
        physicalCube.setName(name);
        physicalCube.setDocumentation((Documentation) documentation);
        physicalCube.getDimensionConnectors().addAll((Collection<? extends DimensionConnector>) dimensionConnectors);
        physicalCube.getCalculatedMembers().addAll((Collection<? extends CalculatedMember>) calculatedMembers);
        physicalCube.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        physicalCube.getKpis().addAll((Collection<? extends Kpi>) kpis);
        physicalCube.setDefaultMeasure((Measure) defaultMeasure);
        physicalCube.setEnabled(enabled);
        physicalCube.setVisible(visible);
        physicalCube.getMeasureGroups().addAll((Collection<? extends MeasureGroup>) measureGroups);
        physicalCube.setQuery((Query) query);
        physicalCube.setWritebackTable((WritebackTable) writebackTable);
        physicalCube.getAction().addAll((Collection<? extends Action>) action);
        physicalCube.setCache(cache);
        return physicalCube;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ActionMappingMapping createDrillThroughAction(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends DrillThroughAttributeMapping> drillThroughAttribute,
        List<? extends MeasureMapping> drillThroughMeasure, boolean def
    ) {
        DrillThroughAction action = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        action.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        action.setId(id);
        action.setDescription(description);
        action.setName(name);
        action.setDocumentation((Documentation) documentation);
        action.getDrillThroughAttribute().addAll((Collection<? extends DrillThroughAttribute>) drillThroughAttribute);
        action.getDrillThroughMeasure().addAll((Collection<? extends Measure>) drillThroughMeasure);
        action.setDefault(def);
        return action;
    }

    @Override
    protected DrillThroughAttributeMapping createDrillThroughAttribute(
        DimensionMapping dimension,
        HierarchyMapping hierarchy, LevelMapping level, String property
    ) {
        DrillThroughAttribute drillThroughAttribute = RolapMappingFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute.setDimension((Dimension) dimension);
        drillThroughAttribute.setHierarchy((Hierarchy) hierarchy);
        drillThroughAttribute.setLevel((Level) level);
        drillThroughAttribute.setProperty(property);
        return drillThroughAttribute;
    }

    @Override
    protected WritebackAttributeMapping createWritebackAttribute(String column, DimensionMapping dimension) {
        WritebackAttribute writebackAttribute = RolapMappingFactory.eINSTANCE.createWritebackAttribute();
        writebackAttribute.setColumn(column);
        writebackAttribute.setDimension((Dimension) dimension);
        return writebackAttribute;
    }

    @Override
    protected WritebackMeasureMapping createwritebackMeasure(String column, String name) {
        WritebackMeasure writebackMeasure = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure.setColumn(column);
        writebackMeasure.setName(name);
        return writebackMeasure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected WritebackTableMapping createWritebackTable(
        List<? extends WritebackAttributeMapping> writebackAttribute,
        List<? extends WritebackMeasureMapping> writebackMeasure, String name, String schema
    ) {
        WritebackTable writebackTable = RolapMappingFactory.eINSTANCE.createWritebackTable();
        writebackTable.getWritebackAttribute().addAll((Collection<? extends WritebackAttribute>) writebackAttribute);
        writebackTable.getWritebackMeasure().addAll((Collection<? extends WritebackMeasure>) writebackMeasure);
        writebackTable.setName(name);
        writebackTable.setSchema(schema);
        return writebackTable;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureGroupMapping createMeasureGroup(List<? extends MeasureMapping> measures, String name) {
        MeasureGroup measureGroup = RolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll((Collection<? extends Measure>) measures);
        measureGroup.setName(name);
        return measureGroup;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createMeasure(
        SQLExpressionMapping measureExpression,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty,
        CellFormatterMapping cellFormatter, String backColor, String column, String datatype, String displayFolder,
        String formatString, String formatter, boolean visible, String name, String id, String type
    ) {
        Measure measure = RolapMappingFactory.eINSTANCE.createMeasure();
        measure.setMeasureExpression((SQLExpression) measureExpression);
        measure.getCalculatedMemberProperty().addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperty);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn(column);
        measure.setDatatype(datatype);
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setType(type);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CellFormatterMapping createCellFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String ref
    ) {
        CellFormatter cellFormatter = RolapMappingFactory.eINSTANCE.createCellFormatter();
        cellFormatter.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        cellFormatter.setId(id);
        cellFormatter.setDescription(description);
        cellFormatter.setName(name);
        cellFormatter.setDocumentation((Documentation) documentation);
        cellFormatter.setRef(ref);
        return cellFormatter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CalculatedMemberPropertyMapping createCalculatedMemberProperty(
        List<? extends AnnotationMapping> annotations, String id, String description, String name,
        DocumentationMapping documentation, String expression, String value
    ) {
        CalculatedMemberProperty calculatedMemberProperty =
            RolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        calculatedMemberProperty.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        calculatedMemberProperty.setId(id);
        calculatedMemberProperty.setDescription(description);
        calculatedMemberProperty.setName(name);
        calculatedMemberProperty.setDocumentation((Documentation) documentation);
        calculatedMemberProperty.setExpression(expression);
        calculatedMemberProperty.setValue(value);
        return calculatedMemberProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TranslationMapping createTranslation(
        long language, String caption, String description,
        String displayFolder, List<? extends AnnotationMapping> annotations
    ) {
        Translation translation = RolapMappingFactory.eINSTANCE.createTranslation();
        translation.setLanguage(language);
        translation.setCaption(caption);
        translation.setDescription(description);
        translation.setDisplayFolder(displayFolder);
        translation.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        return translation;
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
        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        kpi.setId(id);
        kpi.setDescription(description);
        kpi.setName(name);
        kpi.setDocumentation((Documentation) documentation);
        kpi.getTranslations().addAll((Collection<? extends Translation>) translations);
        kpi.setDisplayFolder(displayFolder);
        kpi.setAssociatedMeasureGroupID(associatedMeasureGroupID);
        kpi.setValue(value);
        kpi.setGoal(goal);
        kpi.setStatus(status);
        kpi.setTrend(trend);
        kpi.setWeight(weight);
        kpi.setTrendGraphic(trendGraphic);
        kpi.setStatusGraphic(statusGraphic);
        kpi.setCurrentTimeMember(currentTimeMember);
        kpi.setParentKpiID(parentKpiID);
        return kpi;
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
        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        calculatedMember.setId(id);
        calculatedMember.setDescription(description);
        calculatedMember.setName(name);
        calculatedMember.setDocumentation((Documentation) documentation);
        calculatedMember.getCalculatedMemberProperties().addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        calculatedMember.setCellFormatter((CellFormatter) cellFormatter);
        calculatedMember.setFormula(formula);
        calculatedMember.setDisplayFolder(displayFolder);
        calculatedMember.setFormatString(formatString);
        calculatedMember.setHierarchy((Hierarchy) hierarchy);
        calculatedMember.setParent(parent);
        calculatedMember.setVisible(visible);
        return calculatedMember;
    }

    @Override
    protected DimensionConnectorMapping createDimensionConnector(
        String foreignKey, LevelMapping level,
        String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName,
        PhysicalCubeMapping physicalCube
    ) {
        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setForeignKey(foreignKey);
        dimensionConnector.setLevel((Level) level);
        dimensionConnector.setUsagePrefix(usagePrefix);
        dimensionConnector.setVisible(visible);
        dimensionConnector.setDimension((Dimension) dimension);
        dimensionConnector.setOverrideDimensionName(overrideDimensionName);
        dimensionConnector.setPhysicalCube((PhysicalCube) physicalCube);
        return dimensionConnector;
    }

    @Override
    protected DocumentationMapping createDocumentation(String value) {
        Documentation documentation = RolapMappingFactory.eINSTANCE.createDocumentation();
        documentation.setValue(value);
        return documentation;
    }

    @Override
    protected AnnotationMapping createAnnotation(String value, String name) {
        Annotation annotation = RolapMappingFactory.eINSTANCE.createAnnotation();
        annotation.setValue(value);
        annotation.setName(value);
        return annotation;
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
        Schema schema = RolapMappingFactory.eINSTANCE.createSchema();
        schema.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        schema.setId(id);
        schema.setDescription(description);
        schema.setName(name);
        schema.setDocumentation((Documentation) documentation);
        schema.getParameters().addAll((Collection<? extends Parameter>) parameters);
        schema.getCubes().addAll((Collection<? extends Cube>) cubes);
        schema.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        schema.getAccessRoles().addAll((Collection<? extends AccessRole>) accessRoles);
        schema.setDefaultAccessRole((AccessRole) defaultAccessRole);
        schema.setMeasuresDimensionName(measuresDimensionName);
        return schema;
    }

}
