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
package org.eclipse.daanse.rolap.mapping.modifier.emf;

import java.util.Collection;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessCatalogGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.OrderedColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowValueMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionColumnMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCatalog;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimension;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchy;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;
import org.eclipse.daanse.rolap.mapping.api.model.enums.BitAggregationType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.PercentileType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.RollupPolicyType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.BaseMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.BitAggMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.BitAggType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Column;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnInternalDataType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ColumnType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Cube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CustomMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseCatalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HideMemberIf;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Link;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MaxMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberAccess;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberReaderParameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MinMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NthAggMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Parameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PercentType;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PercentileMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Query;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RollupPolicy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Row;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RowValue;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlStatement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlView;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SystemTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Table;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TextAggMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Translation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ViewTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.InlineTableImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.PhysicalColumnImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.PhysicalTableImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.SQLExpressionColumnImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.SqlViewImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.SystemTableImpl;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.impl.ViewTableImpl;
import org.eclipse.daanse.rolap.mapping.modifier.common.AbstractMappingModifier;

public class EmfMappingModifier extends AbstractMappingModifier {

    protected EmfMappingModifier(CatalogMapping catalog) {
        super(catalog);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableMapping createViewTable(String name, DatabaseSchemaMapping schema,
            String description) {
        ViewTable table = RolapMappingFactory.eINSTANCE
                .createViewTable();
        table.setName(name);
        table.setSchema((DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Table createSystemTable(String name, DatabaseSchemaMapping schema,
            String description) {
        SystemTable table = RolapMappingFactory.eINSTANCE.createSystemTable();
        table.setName(name);
        table.setSchema((DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PhysicalTable createPhysicalTable(String name, DatabaseSchemaMapping schema,
            String description) {
        PhysicalTable table = RolapMappingFactory.eINSTANCE.createPhysicalTable();
        table.setName(name);
        table.setSchema((DatabaseSchema) schema);
        table.setDescription(description);
        return null;
    }

    @Override
    protected Column createPhysicalColumn(String name, TableMapping  table, ColumnDataType type, Integer columnSize, Integer decimalDigits,
            Integer numPrecRadix, Integer charOctetLength, Boolean nullable, String description) {
        Column column = RolapMappingFactory.eINSTANCE
                .createPhysicalColumn();
        column.setName(name);
        column.setTable((Table) table);
        column.setType(toEmf(type));
        column.setColumnSize(columnSize);
        column.setDecimalDigits(decimalDigits);
        column.setNumPrecRadix(numPrecRadix);
        column.setCharOctetLength(charOctetLength);
        column.setNullable(nullable);
        column.setDescription(description);
        return column;
    }

    private ColumnType toEmf(ColumnDataType type) {
        return ColumnType.valueOf(type.name().toUpperCase());
    }

    private BitAggType toEmf(BitAggregationType type) {
        return BitAggType.valueOf(type.name().toUpperCase());
    }

    private PercentType toEmf(PercentileType type) {
        return PercentType.valueOf(type.name().toUpperCase());
    }


    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseSchema createDatabaseSchema(List<? extends TableMapping> tables, String name, String id) {
        DatabaseSchema databaseSchema = RolapMappingFactory.eINSTANCE
                .createDatabaseSchema();
        databaseSchema.getTables()
                .addAll((Collection<? extends Table>) tables);
        databaseSchema.setName(name);
        databaseSchema.setId(id);
        return databaseSchema;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessRoleMapping createAccessRole(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends AccessCatalogGrantMapping> accessCatalogGrants,
            List<? extends AccessRoleMapping> referencedAccessRoles) {
        AccessRole accessRole = RolapMappingFactory.eINSTANCE.createAccessRole();
        accessRole.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        accessRole.setId(id);
        accessRole.setDescription(description);
        accessRole.setName(name);
        accessRole.getAccessCatalogGrants().addAll((Collection<? extends AccessCatalogGrant>) accessCatalogGrants);
        accessRole.getReferencedAccessRoles().addAll((Collection<? extends AccessRole>) referencedAccessRoles);
        return accessRole;
    }

    @Override
    protected AccessMemberGrantMapping createAccessMemberGrant(AccessMember access, String member) {
        AccessMemberGrant accessMemberGrant = RolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        accessMemberGrant.setMemberAccess(toEmf(access));
        accessMemberGrant.setMember(member);
        return accessMemberGrant;
    }

    @Override
    protected QueryMapping createInlineTableQuery(String alias, InlineTableMapping table, String id) {
        InlineTableQuery inlineTableQuery = RolapMappingFactory.eINSTANCE.createInlineTableQuery();
        inlineTableQuery.setAlias(alias);
        inlineTableQuery.setTable((InlineTable) table);
        inlineTableQuery.setId(id);
        return inlineTableQuery;
    }

    @Override
    protected QueryMapping createJoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right, String id) {
        JoinQuery joinQuery = RolapMappingFactory.eINSTANCE.createJoinQuery();
        joinQuery.setLeft((JoinedQueryElement) left);
        joinQuery.setRight((JoinedQueryElement) right);
        joinQuery.setId(id);
        return joinQuery;
    }

    @Override
    protected JoinedQueryElementMapping createJoinedQueryElement(String alias, ColumnMapping key, QueryMapping query) {
        JoinedQueryElement joinedQueryElement = RolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        joinedQueryElement.setAlias(alias);
        joinedQueryElement.setKey((Column) key);
        joinedQueryElement.setQuery((Query) query);
        return joinedQueryElement;
    }

    @Override
    protected QueryMapping createSqlSelectQuery(String alias, SqlViewMapping sqlView, String id) {
        SqlSelectQuery sqlSelectQuery = RolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        sqlSelectQuery.setAlias(alias);
        sqlSelectQuery.setSql((SqlView) sqlView);
        sqlSelectQuery.setId(id);
        return sqlSelectQuery;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TableQueryMapping createTableQuery(String alias, SqlStatementMapping sqlWhereExpression,
            List<? extends AggregationExcludeMapping> aggregationExcludes,
            List<? extends TableQueryOptimizationHintMapping> optimizationHints, TableMapping  table,
            List<? extends AggregationTableMapping> aggregationTables, String id) {
        TableQuery tableQuery = RolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuery.setAlias(alias);
        tableQuery.setSqlWhereExpression((SqlStatement) sqlWhereExpression);
        tableQuery.getAggregationExcludes().addAll((Collection<? extends AggregationExclude>) aggregationExcludes);
        tableQuery.getOptimizationHints().addAll((Collection<? extends TableQueryOptimizationHint>) optimizationHints);
        tableQuery.setTable((Table) table);
        tableQuery.getAggregationTables().addAll((Collection<? extends AggregationTable>) aggregationTables);
        tableQuery.setId(id);
        return tableQuery;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationTableMapping createAggregationPattern(AggregationColumnNameMapping aggregationFactCount,
            List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
            List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
            List<? extends AggregationMeasureMapping> aggregationMeasures,
            List<? extends AggregationLevelMapping> aggregationLevels,
            List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
            String id, String pattern, List<? extends AggregationExcludeMapping> excludes) {
        AggregationPattern aggregationPattern = RolapMappingFactory.eINSTANCE.createAggregationPattern();
        aggregationPattern.setAggregationFactCount((AggregationColumnName) aggregationFactCount);
        aggregationPattern.getAggregationIgnoreColumns()
                .addAll((Collection<? extends AggregationColumnName>) aggregationIgnoreColumns);
        aggregationPattern.getAggregationForeignKeys()
                .addAll((Collection<? extends AggregationForeignKey>) aggregationForeignKeys);
        aggregationPattern.getAggregationMeasures()
                .addAll((Collection<? extends AggregationMeasure>) aggregationMeasures);
        aggregationPattern.getAggregationLevels().addAll((Collection<? extends AggregationLevel>) aggregationLevels);
        aggregationPattern.getAggregationMeasureFactCounts()
                .addAll((Collection<? extends AggregationMeasureFactCount>) aggregationMeasureFactCounts);
        aggregationPattern.setIgnorecase(ignorecase);
        aggregationPattern.setId(id);
        aggregationPattern.setPattern(pattern);
        aggregationPattern.getExcludes().addAll((Collection<? extends AggregationExclude>) excludes);
        return aggregationPattern;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationTableMapping createAggregationName(AggregationColumnNameMapping aggregationFactCount,
            List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
            List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
            List<? extends AggregationMeasureMapping> aggregationMeasures,
            List<? extends AggregationLevelMapping> aggregationLevels,
            List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
            String id, String approxRowCount, TableMapping  name) {
        AggregationName aggregationName = RolapMappingFactory.eINSTANCE.createAggregationName();
        aggregationName.setAggregationFactCount((AggregationColumnName) aggregationFactCount);
        aggregationName.getAggregationIgnoreColumns()
                .addAll((Collection<? extends AggregationColumnName>) aggregationIgnoreColumns);
        aggregationName.getAggregationForeignKeys()
                .addAll((Collection<? extends AggregationForeignKey>) aggregationForeignKeys);
        aggregationName.getAggregationMeasures().addAll((Collection<? extends AggregationMeasure>) aggregationMeasures);
        aggregationName.getAggregationLevels().addAll((Collection<? extends AggregationLevel>) aggregationLevels);
        aggregationName.getAggregationMeasureFactCounts()
                .addAll((Collection<? extends AggregationMeasureFactCount>) aggregationMeasureFactCounts);
        aggregationName.setIgnorecase(ignorecase);
        aggregationName.setId(id);
        aggregationName.setApproxRowCount(approxRowCount);
        aggregationName.setName((Table) name);
        return aggregationName;
    }

    @Override
    protected AggregationMeasureFactCountMapping createAggregationMeasureFactCount(ColumnMapping column, ColumnMapping factColumn) {
        AggregationMeasureFactCount aggregationMeasureFactCount = RolapMappingFactory.eINSTANCE
                .createAggregationMeasureFactCount();
        aggregationMeasureFactCount.setColumn((Column) column);
        aggregationMeasureFactCount.setFactColumn((Column) factColumn);
        return aggregationMeasureFactCount;
    }

    @Override
    protected AggregationLevelPropertyMapping createAggregationLevelProperty(ColumnMapping column, String name) {
        AggregationLevelProperty aggregationLevelProperty = RolapMappingFactory.eINSTANCE
                .createAggregationLevelProperty();
        aggregationLevelProperty.setColumn((Column) column);
        aggregationLevelProperty.setName(name);
        return aggregationLevelProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AggregationLevelMapping createAggregationLevel(
            List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, ColumnMapping captionColumn,
            boolean collapsed, ColumnMapping column, String name, ColumnMapping nameColumn, ColumnMapping ordinalColumn) {
        AggregationLevel aggregationLevel = RolapMappingFactory.eINSTANCE.createAggregationLevel();
        aggregationLevel.getAggregationLevelProperties()
                .addAll((Collection<? extends AggregationLevelProperty>) aggregationLevelProperties);
        aggregationLevel.setCaptionColumn((Column) captionColumn);
        aggregationLevel.setCollapsed(collapsed);
        aggregationLevel.setColumn((Column) column);
        aggregationLevel.setName(name);
        aggregationLevel.setNameColumn((Column) nameColumn);
        aggregationLevel.setOrdinalColumn((Column) ordinalColumn);
        return aggregationLevel;
    }

    @Override
    protected AggregationMeasureMapping createAggregationMeasure(ColumnMapping column, String name, String rollupType) {
        AggregationMeasure aggregationMeasure = RolapMappingFactory.eINSTANCE.createAggregationMeasure();
        aggregationMeasure.setColumn((Column) column);
        aggregationMeasure.setName(name);
        aggregationMeasure.setRollupType(rollupType);
        return aggregationMeasure;
    }

    @Override
    protected AggregationForeignKeyMapping createAggregationForeignKey(ColumnMapping aggregationColumn, ColumnMapping factColumn) {
        AggregationForeignKey aggregationForeignKey = RolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        aggregationForeignKey.setAggregationColumn((Column) aggregationColumn);
        aggregationForeignKey.setFactColumn((Column) factColumn);
        return aggregationForeignKey;
    }

    @Override
    protected AggregationColumnNameMapping createAggregationColumn(ColumnMapping column) {
        AggregationColumnName aggregationColumnName = RolapMappingFactory.eINSTANCE.createAggregationColumnName();
        aggregationColumnName.setColumn((Column) column);
        return aggregationColumnName;
    }

    @Override
    protected TableQueryOptimizationHintMapping createTableQueryOptimizationHint(String value, String type) {
        TableQueryOptimizationHint tableQueryOptimizationHint = RolapMappingFactory.eINSTANCE
                .createTableQueryOptimizationHint();
        tableQueryOptimizationHint.setValue(value);
        tableQueryOptimizationHint.setType(type);
        return tableQueryOptimizationHint;
    }

    @Override
    protected AggregationExcludeMapping createAggregationExclude(boolean ignorecase, String name, String pattern,
            String id) {
        AggregationExclude aggregationExclude = RolapMappingFactory.eINSTANCE.createAggregationExclude();
        aggregationExclude.setIgnorecase(ignorecase);
        aggregationExclude.setName(name);
        aggregationExclude.setPattern(pattern);
        aggregationExclude.setId(id);
        return aggregationExclude;
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
    protected HierarchyMapping createExplicitHierarchy(List<? extends AnnotationMapping> annotations, String id,
            String description, String name, List<? extends LevelMapping> levels,
            List<? extends MemberReaderParameterMapping> memberReaderParameters, String allLevelName,
            String allMemberCaption, String allMemberName, String defaultMember, String displayFolder, boolean hasAll,
            String memberReaderClass, String origin, ColumnMapping primaryKey,
            String uniqueKeyLevelName, boolean visible, QueryMapping query) {
        ExplicitHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        hierarchy.setId(id);
        hierarchy.setDescription(description);
        hierarchy.setName(name);
        hierarchy.getLevels().addAll((Collection<? extends Level>) levels);
        hierarchy.getMemberReaderParameters()
                .addAll((Collection<? extends MemberReaderParameter>) memberReaderParameters);
        hierarchy.setAllLevelName(allLevelName);
        hierarchy.setAllMemberCaption(allMemberCaption);
        hierarchy.setAllMemberName(allMemberName);
        hierarchy.setDefaultMember(defaultMember);
        hierarchy.setDisplayFolder(displayFolder);
        hierarchy.setHasAll(hasAll);
        hierarchy.setMemberReaderClass(memberReaderClass);
        hierarchy.setOrigin(origin);
        hierarchy.setPrimaryKey((Column) primaryKey);
        hierarchy.setUniqueKeyLevelName(uniqueKeyLevelName);
        hierarchy.setVisible(visible);
        hierarchy.setQuery((Query) query);
        return hierarchy;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected HierarchyMapping createParentChildHierarchy(List<? extends AnnotationMapping> annotations,
            String id, String description, String name,
            List<? extends MemberReaderParameterMapping> memberReaderParameters, String allLevelName,
            String allMemberCaption, String allMemberName, String defaultMember, String displayFolder, boolean hasAll,
            String memberReaderClass, String origin, ColumnMapping primaryKey, String uniqueKeyLevelName,
            boolean visible, QueryMapping query, LevelMapping level,
            ParentChildLinkMapping parentChildLink, String nullParentValue, ColumnMapping parentColumn,
            boolean parentAsLeafEnable, String parentAsLeafNameFormat) {
        ParentChildHierarchy hierarchy = RolapMappingFactory.eINSTANCE.createParentChildHierarchy();
        hierarchy.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        hierarchy.setId(id);
        hierarchy.setDescription(description);
        hierarchy.setName(name);
        hierarchy.setLevel((Level) level);
        hierarchy.getMemberReaderParameters()
                .addAll((Collection<? extends MemberReaderParameter>) memberReaderParameters);
        hierarchy.setAllLevelName(allLevelName);
        hierarchy.setAllMemberCaption(allMemberCaption);
        hierarchy.setAllMemberName(allMemberName);
        hierarchy.setDefaultMember(defaultMember);
        hierarchy.setDisplayFolder(displayFolder);
        hierarchy.setHasAll(hasAll);
        hierarchy.setMemberReaderClass(memberReaderClass);
        hierarchy.setOrigin(origin);
        hierarchy.setPrimaryKey((Column) primaryKey);
        hierarchy.setUniqueKeyLevelName(uniqueKeyLevelName);
        hierarchy.setVisible(visible);
        hierarchy.setQuery((Query) query);
        return hierarchy;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberFormatterMapping createMemberFormatter(List<? extends AnnotationMapping> annotations, String id,
            String description, String name, String ref) {
        MemberFormatter memberFormatter = RolapMappingFactory.eINSTANCE.createMemberFormatter();
        memberFormatter.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        memberFormatter.setId(id);
        memberFormatter.setDescription(description);
        memberFormatter.setName(name);
        memberFormatter.setRef(ref);
        return memberFormatter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberPropertyMapping createMemberProperty(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            MemberPropertyFormatterMapping formatter, ColumnMapping column, boolean dependsOnLevelValue, InternalDataType dataType) {
        MemberProperty memberProperty = RolapMappingFactory.eINSTANCE.createMemberProperty();
        memberProperty.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        memberProperty.setId(id);
        memberProperty.setDescription(description);
        memberProperty.setName(name);
        memberProperty.setFormatter((MemberPropertyFormatter) formatter);
        memberProperty.setColumn((Column) column);
        memberProperty.setDependsOnLevelValue(dependsOnLevelValue);
        memberProperty.setPropertyType(toEmf(dataType));
        return memberProperty;
    }

    @Override
    protected ParentChildLinkMapping createParentChildLink(TableQueryMapping table, ColumnMapping childColumn,
            ColumnMapping parentColumn) {
        ParentChildLink parentChildLink = RolapMappingFactory.eINSTANCE.createParentChildLink();
        parentChildLink.setTable((TableQuery) table);
        parentChildLink.setChildColumn((Column) childColumn);
        parentChildLink.setParentColumn((Column) parentColumn);
        return parentChildLink;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SQLExpressionColumnMapping createSQLExpression(List<? extends SqlStatementMapping> sqls, String name, TableMapping table, ColumnDataType type, Integer columnSize, Integer decimalDigits,
            Integer numPrecRadix, Integer charOctetLength, Boolean nullable, String description) {
        SQLExpressionColumn sqlExpression = RolapMappingFactory.eINSTANCE.createSQLExpressionColumn();
        sqlExpression.getSqls().addAll((Collection<? extends SqlStatement>) sqls);
        sqlExpression.setName(name);
        sqlExpression.setTable((Table) table);
        sqlExpression.setType(toEmf(type));
        sqlExpression.setColumnSize(columnSize);
        sqlExpression.setDecimalDigits(decimalDigits);
        sqlExpression.setNumPrecRadix(numPrecRadix);
        sqlExpression.setCharOctetLength(charOctetLength);
        sqlExpression.setNullable(nullable);
        sqlExpression.setDescription(description);
        return sqlExpression;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LevelMapping createLevel(
            List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
            String approxRowCount, ColumnMapping captionColumn, ColumnMapping column, HideMemberIfType hideMemberIf,
            LevelType levelType, ColumnMapping nameColumn, ColumnMapping ordinalColumn,
            InternalDataType type, boolean uniqueMembers, boolean visible, String name, String id, String description) {
        Level level = RolapMappingFactory.eINSTANCE.createLevel();
        level.getMemberProperties().addAll((Collection<? extends MemberProperty>) memberProperties);
        level.setMemberFormatter((MemberFormatter) memberFormatter);
        level.setApproxRowCount(approxRowCount);
        level.setCaptionColumn((Column) captionColumn);
        level.setColumn((Column) column);
        level.setHideMemberIf(toEmf(hideMemberIf));
        level.setType(toEmf(levelType));
        level.setNameColumn((Column) nameColumn);
        level.setOrdinalColumn((Column) ordinalColumn);
        level.setColumnType(toEmf(type));
        level.setUniqueMembers(uniqueMembers);
        level.setVisible(visible);
        level.setName(name);
        level.setId(id);
        level.setDescription(description);
        return level;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessHierarchyGrantMapping createAccessHierarchyGrant(
            List<? extends AccessMemberGrantMapping> memberGrants, AccessHierarchy access, LevelMapping bottomLevel,
            RollupPolicyType rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy) {
        AccessHierarchyGrant accessHierarchyGrant = RolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        accessHierarchyGrant.getMemberGrants().addAll((Collection<? extends AccessMemberGrant>) memberGrants);
        accessHierarchyGrant.setHierarchyAccess(toEmf(access));
        accessHierarchyGrant.setBottomLevel((Level) bottomLevel);
        accessHierarchyGrant.setRollupPolicy(toEmf(rollupPolicy));
        accessHierarchyGrant.setTopLevel((Level) topLevel);
        accessHierarchyGrant.setHierarchy((Hierarchy) hierarchy);
        return accessHierarchyGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TimeDimensionMapping createTimeDimension(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible) {
        TimeDimension timeDimension = RolapMappingFactory.eINSTANCE.createTimeDimension();
        timeDimension.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        timeDimension.setId(id);
        timeDimension.setDescription(description);
        timeDimension.setName(name);
        timeDimension.getHierarchies().addAll((Collection<? extends Hierarchy>) hierarchies);
        timeDimension.setUsagePrefix(usagePrefix);
        timeDimension.setVisible(visible);
        return timeDimension;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected StandardDimensionMapping createStandardDimension(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible) {
        StandardDimension standardDimension = RolapMappingFactory.eINSTANCE.createStandardDimension();
        standardDimension.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        standardDimension.setId(id);
        standardDimension.setDescription(description);
        standardDimension.setName(name);
        standardDimension.getHierarchies().addAll((Collection<? extends Hierarchy>) hierarchies);
        standardDimension.setUsagePrefix(usagePrefix);
        standardDimension.setVisible(visible);
        return standardDimension;
    }

    @Override
    protected AccessDimensionGrantMapping createAccessDimensionGrant(AccessDimension access,
            DimensionMapping dimension) {
        AccessDimensionGrant accessDimensionGrant = RolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        accessDimensionGrant.setDimensionAccess(toEmf(access));
        accessDimensionGrant.setDimension((Dimension) dimension);
        return accessDimensionGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCubeGrantMapping createAccessCubeGrant(List<? extends AccessDimensionGrantMapping> dimensionGrants,
            List<? extends AccessHierarchyGrantMapping> hierarchyGrants, AccessCube access, CubeMapping cube) {
        AccessCubeGrant accessCubeGrant = RolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant.getDimensionGrants().addAll((Collection<? extends AccessDimensionGrant>) dimensionGrants);
        accessCubeGrant.getHierarchyGrants().addAll((Collection<? extends AccessHierarchyGrant>) hierarchyGrants);
        accessCubeGrant.setCubeAccess(toEmf(access));
        accessCubeGrant.setCube((Cube) cube);
        return accessCubeGrant;
    }

    private CubeAccess toEmf(AccessCube access) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AccessCatalogGrantMapping createAccessCatalogGrant(List<? extends AccessCubeGrantMapping> accessCubeGrant,
            AccessCatalog access) {
        AccessCatalogGrant accessCatalogGrant = RolapMappingFactory.eINSTANCE.createAccessCatalogGrant();
        accessCatalogGrant.getCubeGrants().addAll((Collection<? extends AccessCubeGrant>) accessCubeGrant);
        accessCatalogGrant.setCatalogAccess(toEmf(access));
        return accessCatalogGrant;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected NamedSetMapping createNamedSet(List<? extends AnnotationMapping> annotations, String id,
            String description, String name, String displayFolder, String formula) {
        NamedSet namedSet = RolapMappingFactory.eINSTANCE.createNamedSet();
        namedSet.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        namedSet.setId(id);
        namedSet.setDescription(description);
        namedSet.setName(name);
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
    protected VirtualCubeMapping createVirtualCube(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends DimensionConnectorMapping> dimensionConnectors,
            List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
            List<? extends KpiMapping> kpis, MemberMapping defaultMeasure, boolean enabled, boolean visible,
            List<? extends MeasureMapping> referencedMeasures, List<? extends CalculatedMemberMapping> referencedCalculatedMembers,
            List<? extends CubeConnectorMapping> cubeUsages) {
        VirtualCube virtualCube = RolapMappingFactory.eINSTANCE.createVirtualCube();
        virtualCube.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        virtualCube.setId(id);
        virtualCube.setDescription(description);
        virtualCube.setName(name);
        virtualCube.getDimensionConnectors().addAll((Collection<? extends DimensionConnector>) dimensionConnectors);
        virtualCube.getCalculatedMembers().addAll((Collection<? extends CalculatedMember>) calculatedMembers);
        virtualCube.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        virtualCube.getKpis().addAll((Collection<? extends Kpi>) kpis);
        virtualCube.setDefaultMeasure((BaseMeasure) defaultMeasure);
        virtualCube.setEnabled(enabled);
        virtualCube.setVisible(visible);
        virtualCube.getReferencedMeasures().addAll((Collection<? extends BaseMeasure>) referencedMeasures);
        virtualCube.getReferencedCalculatedMembers().addAll((Collection<? extends CalculatedMember>) referencedCalculatedMembers);
        virtualCube.getCubeUsages().addAll((Collection<? extends CubeConnector>) cubeUsages);
        return virtualCube;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PhysicalCubeMapping createPhysicalCube(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends DimensionConnectorMapping> dimensionConnectors,
            List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
            List<? extends KpiMapping> kpis, MemberMapping defaultMeasure, boolean enabled, boolean visible,
            List<? extends MeasureGroupMapping> measureGroups, QueryMapping query, WritebackTableMapping writebackTable,
            List<? extends ActionMapping> action, boolean cache) {
        PhysicalCube physicalCube = RolapMappingFactory.eINSTANCE.createPhysicalCube();
        physicalCube.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        physicalCube.setId(id);
        physicalCube.setDescription(description);
        physicalCube.setName(name);
        physicalCube.getDimensionConnectors().addAll((Collection<? extends DimensionConnector>) dimensionConnectors);
        physicalCube.getCalculatedMembers().addAll((Collection<? extends CalculatedMember>) calculatedMembers);
        physicalCube.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        physicalCube.getKpis().addAll((Collection<? extends Kpi>) kpis);
        physicalCube.setDefaultMeasure((BaseMeasure) defaultMeasure);
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
    protected ActionMapping createDrillThroughAction(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends DrillThroughAttributeMapping> drillThroughAttribute,
            List<? extends MeasureMapping> drillThroughMeasure, boolean def) {
        DrillThroughAction action = RolapMappingFactory.eINSTANCE.createDrillThroughAction();
        action.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        action.setId(id);
        action.setDescription(description);
        action.setName(name);
        action.getDrillThroughAttribute().addAll((Collection<? extends DrillThroughAttribute>) drillThroughAttribute);
        action.getDrillThroughMeasure().addAll((Collection<? extends BaseMeasure>) drillThroughMeasure);
        action.setDefault(def);
        return action;
    }

    @Override
    protected DrillThroughAttributeMapping createDrillThroughAttribute(DimensionMapping dimension,
            HierarchyMapping hierarchy, LevelMapping level, String property) {
        DrillThroughAttribute drillThroughAttribute = RolapMappingFactory.eINSTANCE.createDrillThroughAttribute();
        drillThroughAttribute.setDimension((Dimension) dimension);
        drillThroughAttribute.setHierarchy((Hierarchy) hierarchy);
        drillThroughAttribute.setLevel((Level) level);
        drillThroughAttribute.setProperty(property);
        return drillThroughAttribute;
    }

    @Override
    protected WritebackAttributeMapping createWritebackAttribute(ColumnMapping column, DimensionConnectorMapping dimensionConnector) {
        WritebackAttribute writebackAttribute = RolapMappingFactory.eINSTANCE.createWritebackAttribute();
        writebackAttribute.setColumn((Column)column);
        writebackAttribute.setDimensionConnector((DimensionConnector) dimensionConnector);
        return writebackAttribute;
    }

    @Override
    protected WritebackMeasureMapping createwritebackMeasure(ColumnMapping column, String name) {
        WritebackMeasure writebackMeasure = RolapMappingFactory.eINSTANCE.createWritebackMeasure();
        writebackMeasure.setColumn((Column) column);
        writebackMeasure.setName(name);
        return writebackMeasure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected WritebackTableMapping createWritebackTable(List<? extends WritebackAttributeMapping> writebackAttribute,
            List<? extends WritebackMeasureMapping> writebackMeasure, String name, String schema) {
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
        measureGroup.getMeasures().addAll((Collection<? extends BaseMeasure>) measures);
        measureGroup.setName(name);
        return measureGroup;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createSumMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id) {
        SumMeasure measure = RolapMappingFactory.eINSTANCE.createSumMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createBitAggregationMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column,
            InternalDataType datatype, String displayFolder, String formatString, String formatter, boolean visible,
            String name, String id, BitAggregationType bitAggrigationType, boolean not) {
        BitAggMeasure measure = RolapMappingFactory.eINSTANCE.createBitAggMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setNot(not);
        measure.setAggType(toEmf(bitAggrigationType));
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createPercentileMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor,
            InternalDataType datatype, String displayFolder, String formatString, String formatter, boolean visible,
            String name, String id, Double percentile, PercentileType percentileType, OrderedColumnMapping ordColumn) {
        PercentileMeasure measure = RolapMappingFactory.eINSTANCE.createPercentileMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setPercentile(percentile);
        measure.setPercentType(toEmf(percentileType));
        measure.setColumn((OrderedColumn) ordColumn);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createNthAggMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype, String displayFolder,
            String formatString, String formatter, boolean visible, String name, String id, boolean ignoreNulls,
            Integer n, List<? extends OrderedColumnMapping> orderByColumns) {
        NthAggMeasure measure = RolapMappingFactory.eINSTANCE.createNthAggMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setIgnoreNulls(ignoreNulls);;
        measure.setN(n);
        measure.getOrderByColumns().addAll((Collection<? extends OrderedColumn>) orderByColumns);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createMaxMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id) {
        MaxMeasure measure = RolapMappingFactory.eINSTANCE.createMaxMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createMinMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id) {
        MinMeasure measure = RolapMappingFactory.eINSTANCE.createMinMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createAvgMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id) {
        AvgMeasure measure = RolapMappingFactory.eINSTANCE.createAvgMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createCountMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id, boolean distinct) {
        CountMeasure measure = RolapMappingFactory.eINSTANCE.createCountMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setDistinct(distinct);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CellFormatterMapping createCellFormatter(List<? extends AnnotationMapping> annotations, String id,
            String description, String name, String ref) {
        CellFormatter cellFormatter = RolapMappingFactory.eINSTANCE.createCellFormatter();
        cellFormatter.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        cellFormatter.setId(id);
        cellFormatter.setDescription(description);
        cellFormatter.setName(name);
        cellFormatter.setRef(ref);
        return cellFormatter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CalculatedMemberPropertyMapping createCalculatedMemberProperty(
            List<? extends AnnotationMapping> annotations, String id, String description, String name,
            String expression, String value) {
        CalculatedMemberProperty calculatedMemberProperty = RolapMappingFactory.eINSTANCE
                .createCalculatedMemberProperty();
        calculatedMemberProperty.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        calculatedMemberProperty.setId(id);
        calculatedMemberProperty.setDescription(description);
        calculatedMemberProperty.setName(name);
        calculatedMemberProperty.setExpression(expression);
        calculatedMemberProperty.setValue(value);
        return calculatedMemberProperty;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected TranslationMapping createTranslation(long language, String caption, String description,
            String displayFolder, List<? extends AnnotationMapping> annotations) {
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
    protected KpiMapping createKpi(List<? extends AnnotationMapping> annotations, String id, String description,
            String name, List<? extends TranslationMapping> translations,
            String displayFolder, String associatedMeasureGroupID, String value, String goal, String status,
            String trend, String weight, String trendGraphic, String statusGraphic, String currentTimeMember,
            KpiMapping parentKpi) {
        Kpi kpi = RolapMappingFactory.eINSTANCE.createKpi();
        kpi.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        kpi.setId(id);
        kpi.setDescription(description);
        kpi.setName(name);
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
        kpi.setParentKpi((Kpi) parentKpi);
        return kpi;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CalculatedMemberMapping createCalculatedMember(List<? extends AnnotationMapping> annotations, String id,
            String description, String name,
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String formula, String displayFolder, String formatString,
            HierarchyMapping hierarchy, String parent, boolean visible) {
        CalculatedMember calculatedMember = RolapMappingFactory.eINSTANCE.createCalculatedMember();
        calculatedMember.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        calculatedMember.setId(id);
        calculatedMember.setDescription(description);
        calculatedMember.setName(name);
        calculatedMember.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
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
    protected DimensionConnectorMapping createDimensionConnector(ColumnMapping foreignKey, LevelMapping level,
            String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName,
            PhysicalCubeMapping physicalCube) {
        DimensionConnector dimensionConnector = RolapMappingFactory.eINSTANCE.createDimensionConnector();
        dimensionConnector.setForeignKey((Column) foreignKey);
        dimensionConnector.setLevel((Level) level);
        dimensionConnector.setUsagePrefix(usagePrefix);
        dimensionConnector.setVisible(visible);
        dimensionConnector.setDimension((Dimension) dimension);
        dimensionConnector.setOverrideDimensionName(overrideDimensionName);
        dimensionConnector.setPhysicalCube((PhysicalCube) physicalCube);
        return dimensionConnector;
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
    protected CatalogMapping createCatalog(List<? extends AnnotationMapping> annotations, String id, String description,
            String name, List<? extends ParameterMapping> parameters,
            List<? extends CubeMapping> cubes, List<? extends NamedSetMapping> namedSets,
            List<? extends AccessRoleMapping> accessRoles, AccessRoleMapping defaultAccessRole,
            String measuresDimensionName, List<? extends DatabaseSchemaMapping> dbschemas) {
        Catalog schema = RolapMappingFactory.eINSTANCE.createCatalog();
        schema.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        schema.setId(id);
        schema.setDescription(description);
        schema.setName(name);
        schema.getParameters().addAll((Collection<? extends Parameter>) parameters);
        schema.getCubes().addAll((Collection<? extends Cube>) cubes);
        schema.getNamedSets().addAll((Collection<? extends NamedSet>) namedSets);
        schema.getAccessRoles().addAll((Collection<? extends AccessRole>) accessRoles);
        schema.setDefaultAccessRole((AccessRole) defaultAccessRole);
        schema.setMeasuresDimensionName(measuresDimensionName);
        schema.getDbschemas().addAll((Collection<? extends DatabaseSchema>) dbschemas);
        return schema;
    }

    private MemberAccess toEmf(AccessMember access) {
        return null;
    }

    private LevelDefinition toEmf(LevelType levelType) {
        return null;
    }

    private HideMemberIf toEmf(HideMemberIfType hideMemberIf) {
        return null;
    }

    private DimensionAccess toEmf(AccessDimension access) {
        return null;
    }

    private CatalogAccess toEmf(AccessCatalog access) {
        return null;
    }

    private ColumnInternalDataType toEmf(InternalDataType datatype) {
        return null;
    }

    private RollupPolicy toEmf(RollupPolicyType rollupPolicy) {
        return null;
    }

    private HierarchyAccess toEmf(AccessHierarchy access) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MemberPropertyFormatterMapping createMemberPropertyFormatter(
            List<? extends AnnotationMapping> annotations, String id, String description, String name,
            String ref) {
        MemberPropertyFormatter memberPropertyFormatter = RolapMappingFactory.eINSTANCE.createMemberPropertyFormatter();
        memberPropertyFormatter.getAnnotations().addAll((Collection<? extends Annotation>) annotations);
        memberPropertyFormatter.setId(id);
        memberPropertyFormatter.setDescription(description);
        memberPropertyFormatter.setName(name);
        memberPropertyFormatter.setRef(ref);
        return memberPropertyFormatter;
    }


    @Override
    protected SqlStatementMapping createSqlStatement(List<String> dialects, String sql) {
        SqlStatement sqlStatement = RolapMappingFactory.eINSTANCE
                .createSqlStatement();
        sqlStatement.getDialects().addAll(dialects);
        sqlStatement.setSql(sql);
        return sqlStatement;
    }


    @SuppressWarnings("unchecked")
    @Override
    protected SqlView createSqlView(
        String name, DatabaseSchemaMapping schema,
        String description, List<? extends SqlStatementMapping> sqlStatements
    ) {
        SqlView sqlView = RolapMappingFactory.eINSTANCE
                .createSqlView();
        sqlView.setName(name);
        sqlView.setSchema((DatabaseSchema) schema);
        sqlView.setDescription(description);
        sqlView.getSqlStatements().addAll((Collection<? extends SqlStatement>) sqlStatements);
        return sqlView;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected InlineTable createInlineTable(
        String name, DatabaseSchemaMapping schema,
        String description, List<? extends RowMapping> rows
    ) {
        InlineTable inlineTable = RolapMappingFactory.eINSTANCE
                .createInlineTable();
        inlineTable.setName(name);
        inlineTable.setSchema((DatabaseSchema) schema);
        inlineTable.setDescription(description);
        inlineTable.getRows().addAll((Collection<? extends Row>) rows);
        return inlineTable;
    }

    @Override
    protected RowValue createRowValue(ColumnMapping column, String value) {
        RowValue rowValue = RolapMappingFactory.eINSTANCE
                .createRowValue();
        rowValue.setColumn((Column) column);
        rowValue.setValue(value);
        return rowValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Row createRow(List<? extends RowValueMapping> rowValues) {
        Row row = RolapMappingFactory.eINSTANCE
                .createRow();
        row.getRowValues().addAll((Collection<? extends RowValue>) rowValues);
        return row;
    }

    @Override
    protected LinkMapping createLink(ColumnMapping primaryKey, ColumnMapping foreignKey) {
        Link link = RolapMappingFactory.eINSTANCE.createLink();
        link.setPrimaryKey((Column) primaryKey);
        link.setForeignKey((Column) foreignKey);
        return link;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected DatabaseCatalog createDatabaseCatalog(List<? extends DatabaseSchemaMapping> schemas,
            List<? extends LinkMapping> links) {
        DatabaseCatalog databaseCatalog = RolapMappingFactory.eINSTANCE.createDatabaseCatalog();
        databaseCatalog.getSchemas().addAll((Collection<? extends DatabaseSchema>) schemas);
        databaseCatalog.getLinks().addAll((Collection<? extends Link>) links);
        return databaseCatalog;
    }

    protected ColumnMapping column(ColumnMapping column) {
        ColumnMapping c = super.column(column);
        if (c instanceof PhysicalColumnImpl pc) {
            pc.setTable((Table) columnTable(column));
        }
        return c;
    }

    protected TableMapping table(TableMapping table) {
        TableMapping t = super.table(table);
        if (t != null) {
        List<? extends ColumnMapping> cs = tableColumns(table);
        setTableInColumns(cs, t);
        List<Column> columns = cs != null ? cs.stream().map(Column.class::cast).toList() : List.of();
        if (t instanceof PhysicalTableImpl pt) {
            pt.getColumns().addAll(columns);
        }
        if (t instanceof SystemTableImpl st) {
            st.getColumns().addAll(columns);
        }
        if (t instanceof ViewTableImpl vt) {
            vt.getColumns().addAll(columns);
        }
        if (t instanceof InlineTableImpl it) {
            it.getColumns().addAll(columns);
        }
        if (t instanceof SqlViewImpl sv) {
            sv.getColumns().addAll(columns);
        }
        }
        return t;
    }

    protected void setTableInColumns(List<? extends ColumnMapping> columns,
            TableMapping tableMappingImpl) {
        if (columns != null) {
            for (ColumnMapping column : columns) {
                if (column.getTable() == null) {
                    if (column instanceof PhysicalColumnImpl pc) {
                        pc.setTable((Table) tableMappingImpl);
                    }
                    if (column instanceof SQLExpressionColumnImpl sec) {
                        sec.setTable((Table) tableMappingImpl);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createTextAggMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, SQLExpressionColumnMapping column,
            InternalDataType datatype, String displayFolder, String formatString, String formatter, boolean visible,
            String name, String id, boolean distinct, List<? extends OrderedColumnMapping> orderByColumns,
            String separator, String coalesce, String onOverflowTruncate) {
        TextAggMeasure measure = RolapMappingFactory.eINSTANCE.createTextAggMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setDistinct(distinct);
        measure.getOrderByColumns().addAll((Collection<? extends OrderedColumn>) orderByColumns);
        measure.setSeparator(separator);
        measure.setCoalesce(coalesce);
        measure.setOnOverflowTruncate(onOverflowTruncate);
        return measure;
    }

    @Override
    protected OrderedColumnMapping createOrderedColumn(ColumnMapping column, boolean ascend) {
        OrderedColumn orderedColumn = RolapMappingFactory.eINSTANCE.createOrderedColumn();
        orderedColumn.setColumn((Column) column);
        orderedColumn.setAscend(ascend);
        return orderedColumn;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createCustomMeasure(
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, ColumnMapping column, InternalDataType datatype,
            String displayFolder, String formatString, String formatter, boolean visible, String name, String id,
            String template, List<? extends ColumnMapping> columns, List<String> properties) {
        CustomMeasure measure = RolapMappingFactory.eINSTANCE.createCustomMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        measure.setTemplate(template);
        measure.getColumns().addAll((Collection<? extends Column>) columns);
        measure.getProperties().addAll(properties);
        return measure;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected MeasureMapping createNoneMeasure(List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
            CellFormatterMapping cellFormatter, String backColor, SQLExpressionColumnMapping column,
            InternalDataType datatype, String displayFolder, String formatString, String formatter, boolean visible,
            String name, String id) {
        TextAggMeasure measure = RolapMappingFactory.eINSTANCE.createTextAggMeasure();
        measure.getCalculatedMemberProperties()
                .addAll((Collection<? extends CalculatedMemberProperty>) calculatedMemberProperties);
        measure.setCellFormatter((CellFormatter) cellFormatter);
        measure.setBackColor(backColor);
        measure.setColumn((Column) column);
        measure.setDataType(toEmf(datatype));
        measure.setDisplayFolder(displayFolder);
        measure.setFormatString(formatString);
        measure.setFormatter(formatter);
        measure.setVisible(visible);
        measure.setName(name);
        measure.setId(id);
        return measure;
    }

}
