/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.verifyer.basic;

import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NOT_SET;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.daanse.rolap.mapping.model.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.Action;
import org.eclipse.daanse.rolap.mapping.model.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.model.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.model.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.model.AggregationLevelProperty;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.model.AggregationMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.model.AggregationName;
import org.eclipse.daanse.rolap.mapping.model.AggregationPattern;
import org.eclipse.daanse.rolap.mapping.model.AggregationTable;
import org.eclipse.daanse.rolap.mapping.model.Annotation;
import org.eclipse.daanse.rolap.mapping.model.BaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.CellFormatter;
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.Cube;
import org.eclipse.daanse.rolap.mapping.model.CubeConnector;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.Dimension;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Hierarchy;
import org.eclipse.daanse.rolap.mapping.model.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.Kpi;
import org.eclipse.daanse.rolap.mapping.model.Level;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.model.MemberReaderParameter;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.model.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.Query;
import org.eclipse.daanse.rolap.mapping.model.Row;
import org.eclipse.daanse.rolap.mapping.model.RowValue;
import org.eclipse.daanse.rolap.mapping.model.SQLExpressionBaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.SQLExpressionColumn;
import org.eclipse.daanse.rolap.mapping.model.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.model.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.SqlView;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.model.Translation;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.model.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.WritebackTable;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;

public abstract class AbstractSchemaWalker {

    protected List<VerificationResult> results = new ArrayList<>();

    public List<VerificationResult> checkSchema(Catalog schema) {

        if (schema != null) {
            checkAnnotationList(schema.getAnnotations());
            checkParameterList(schema.getParameters());
            checkCubeList(schema.getCubes(), schema);
            checkNamedSetList(schema.getNamedSets());
            checkRoleList(schema.getAccessRoles(), schema);
        }

        return results;
    }

    protected void checkCube(Cube cube, Catalog schema) {
        if (cube != null) {
            checkAnnotationList(cube.getAnnotations());
            checkKpiList(cube.getKpis(), cube);
            checkCalculatedMemberList(cube.getCalculatedMembers());
            checkNamedSetList(cube.getNamedSets());

            if (cube instanceof PhysicalCube physicalCube) {
                checkPhysicalCube(physicalCube, schema);
            }
            if (cube instanceof VirtualCube virtualCube) {
                checkVirtualCube(virtualCube, schema);
            }
        }
    }

    private void checkDimensionConnectorsList(List<? extends DimensionConnector> dimensionConnectors,
            Cube cube, Catalog schema) {
        if (dimensionConnectors != null) {
            dimensionConnectors.forEach(dc -> checkDimensionConnector(dc, cube, schema));
        }
    }

    protected void checkAction(Action action) {
        if (action != null) {
            checkAnnotationList(action.getAnnotations());
        }
        if (action instanceof DrillThroughAction drillThroughAction) {
            checkDrillThroughAction(drillThroughAction);
        }
    }

    protected void checkDrillThroughAction(DrillThroughAction drillThroughAction) {
        if (drillThroughAction != null) {
            checkDrillThroughAttributeList(drillThroughAction.getDrillThroughAttribute());
            checkDrillThroughMeasureList(drillThroughAction.getDrillThroughMeasure());
        }
    }

    private void checkDrillThroughAttributeList(List<? extends DrillThroughAttribute> list) {
        if (list != null) {
            list.forEach(this::checkDrillThroughAttribute);
        }
    }

    private void checkDrillThroughMeasureList(List<? extends BaseMeasure> list) {
        if (list != null) {
            list.forEach(this::checkDrillThroughMeasure);
        }
    }

    protected void checkDrillThroughAttribute(DrillThroughAttribute drillThroughAttribute) {
        // empty
    }

    protected void checkDrillThroughMeasure(BaseMeasure drillThroughElement) {
        // empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkMeasure(BaseMeasure measure, Cube cube) {
        if (measure != null) {
            checkMeasureColumn(measure, cube);
            checkMeasureAggregation(measure, cube);
            checkAnnotationList(measure.getAnnotations());
            checkCalculatedMemberPropertyList(measure.getCalculatedMemberProperties());
            if (measure instanceof SQLExpressionBaseMeasure semm) {
                checkExpressionView(semm.getColumn());
            }
            checkCellFormatter(measure.getCellFormatter());
        }
    }

    protected void checkKpi(Kpi kpi, Cube cube) {
        if (kpi != null) {
            checkAnnotationList(kpi.getAnnotations());
            checkTranslationList(kpi.getTranslations());
        }
        if (cube instanceof PhysicalCube physicalCube) {
            checkKpiPhysicalCube(kpi, physicalCube);
        }
        if (cube instanceof VirtualCube virtualCube) {
            checkKpiVirtualCube(kpi, virtualCube);
        }
    }

    protected void checkKpiPhysicalCube(Kpi kpi, PhysicalCube physicalCube) {
        // empty
    }

    protected void checkKpiVirtualCube(Kpi kpi, VirtualCube virtualCube) {
        // empty
    }

    protected void checkMeasureAggregation(BaseMeasure measure, Cube cube) {
        // empty
    }

    protected void checkMeasureColumn(BaseMeasure measure, Cube cube) {
        // empty
    }

    protected void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        // empty
    }

    protected void checkExpressionView(SQLExpressionColumn measureExpression) {
        if (measureExpression != null) {
            checkSqlList(measureExpression.getSqls());
        }
    }

    private void checkSqlList(List<? extends SqlStatement> sqls) {
        if (sqls != null) {
            sqls.forEach(s -> checkSQL(s));
        }
    }

    protected void checkSQL(SqlStatement sql) {
        // empty
    }

    protected void checkCellFormatter(CellFormatter elementFormatter) {
        // empty
    }

    protected void checkElementFormatterClass(String className) {
        // empty
    }

    protected void checkDimension(Dimension cubeDimension, Cube cube, Catalog schema) {
        if (cubeDimension != null) {
            checkAnnotationList(cubeDimension.getAnnotations());
            if (cubeDimension.getHierarchies() != null) {
                cubeDimension.getHierarchies().forEach(h -> checkHierarchy(h, cubeDimension, cube));
            }
        }
    }

    protected void checkHierarchy(Hierarchy hierarchy, Dimension cubeDimension, Cube cube) {
        if (hierarchy != null) {
            checkAnnotationList(hierarchy.getAnnotations());
            checkMemberReaderParameterList(hierarchy.getMemberReaderParameters());
            checkQuery(hierarchy.getQuery());
            if (hierarchy instanceof ExplicitHierarchy eh) {
                // Level
                if (eh.getLevels() != null) {
                    eh.getLevels().forEach(l -> checkLevel(l, hierarchy, cubeDimension, cube));
                }
            }
            if (hierarchy instanceof ParentChildHierarchy pch) {
                if (pch.getLevel() != null) {
                    checkLevel(pch.getLevel(), hierarchy, cubeDimension, cube);
                }
                if (pch.getParentColumn() instanceof SQLExpressionColumn sec) {
                    checkSqlExpression(sec);

                }

                checkParentChildLink(pch.getParentChildLink());

            }
        }
    }

    protected void checkMemberReaderParameter(MemberReaderParameter memberReaderParameter) {
        // empty
    }

    protected void checkJoinQuery(JoinQuery join) {
        if (join != null) {
            checkJoinedQueryElement(join.getLeft());
            checkJoinedQueryElement(join.getRight());
        }
    }

    protected void checkJoinedQueryElement(JoinedQueryElement element) {
        if (element != null) {
            checkQuery(element.getQuery());
        }
    }

    protected void checkQuery(Query relationOrJoin) {
        if (relationOrJoin != null) {
            if (relationOrJoin instanceof InlineTableQuery inlineTable) {
                checkInlineTableQuery(inlineTable);
            }
            if (relationOrJoin instanceof JoinQuery join) {
                checkJoinQuery(join);
            }
            if (relationOrJoin instanceof TableQuery table) {
                checkTable(table);
            }
            if (relationOrJoin instanceof SqlSelectQuery view) {
                checkSqlSelectQuery(view);
            }
        }
    }

    protected void checkSqlSelectQuery(SqlSelectQuery relationOrJoin) {
        if (relationOrJoin != null) {
            checkSqlView(relationOrJoin.getSql());
        }
    }

    protected void checkSqlView(SqlView sql) {
        if (sql != null) {

        }
    }

    protected void checkInlineTableQuery(InlineTableQuery relationOrJoin) {
        if (relationOrJoin != null) {
            checkInlineTable(relationOrJoin.getTable());
        }
    }

    protected void checkInlineTable(InlineTable table) {
        if (table != null) {
            checkInlineTableRows(table.getRows());
            checkInlineTableColumns(table.getColumns());
        }
    }

    protected void checkInlineTableColumns(List<? extends Column> list) {
        if (list != null) {
            list.forEach(c -> checkInlineTableColumn(c));
        }
    }

    protected void checkInlineTableColumn(Column c) {
        // empty
    }

    private void checkInlineTableRows(List<? extends Row> rows) {
        if (rows != null) {
            rows.forEach(this::checkRow);
        }
    }

    protected void checkRow(Row row) {
        if (row != null) {
            checkRowValueList(row.getRowValues());
        }
    }

    private void checkRowValueList(List<? extends RowValue> rowValues) {
        if (rowValues != null) {
            rowValues.forEach(this::checkRowValue);
        }
    }

    protected void checkRowValue(RowValue value) {
        if (value != null) {
            checkColumn(value.getColumn());
            checkValue(value.getValue());
        }
    }

    private void checkValue(String value) {
        // empty

    }

    protected void checkColumn(Column columnDef) {
        // empty
    }

    protected void checkTable(TableQuery table) {
        if (table != null) {
            checkSqlWhereExpression(table.getSqlWhereExpression());

            checkAggregationExcludeList(table.getAggregationExcludes(), table.getTable().getSchema());

            checkAggregationTableList(table.getAggregationTables(), table.getTable().getSchema());

            checkHintList(table.getOptimizationHints());
        }
    }

    private void checkSqlWhereExpression(SqlStatement sqlWhereExpression) {
        checkSQL(sqlWhereExpression);
    }

    protected void checkHint(TableQueryOptimizationHint hint) {
        // empty
    }

    protected void checkAggregationTable(AggregationTable aggTable, DatabaseSchema schema) {
        if (aggTable != null) {
            checkAggregationColumnName(aggTable.getAggregationFactCount());
            checkAggregationColumnNameList(aggTable.getAggregationIgnoreColumns());
            checkAggregationForeignKeyList(aggTable.getAggregationForeignKeys());
            checkAggregationMeasureList(aggTable.getAggregationMeasures());
            checkAggregationLevelList(aggTable.getAggregationLevels());
            checkAggregationMeasureFactCountList(aggTable.getAggregationMeasureFactCounts());
            if (aggTable instanceof AggregationName aggName) {
                checkAggregationName(aggName);
            }
            if (aggTable instanceof AggregationPattern aggPattern) {
                checkAggregationPattern(aggPattern, schema);
            }
        }
    }

    protected void checkAggregationPattern(AggregationPattern aggTable, DatabaseSchema schema) {
        if (aggTable != null) {
            checkAggregationExcludeList(aggTable.getExcludes(), schema);
        }
    }

    protected void checkAggregationName(AggregationName aggTable) {
        // empty
    }

    protected void checkAggregationMeasureFactCount(AggregationMeasureFactCount aggMeasureFactCount) {
        // empty
    }

    protected void checkAggregationLevel(AggregationLevel aggLevel) {
        if (aggLevel != null) {
            checkAggregationLevelPropertyList(aggLevel.getAggregationLevelProperties());
        }
    }

    protected void checkAggregationLevelProperty(AggregationLevelProperty aggLevelProperty) {
        // empty
    }

    protected void checkAggregationMeasure(AggregationMeasure aggMeasure) {
        // empty
    }

    protected void checkAggregationForeignKey(AggregationForeignKey aggForeignKey) {
        // empty
    }

    protected void checkAggregationColumnName(AggregationColumnName aggFactCount) {
        if (aggFactCount != null && aggFactCount instanceof AggregationMeasureFactCount aggMeasureFactCount) {
            checkAggregationMeasureFactCount(aggMeasureFactCount);
        }
    }

    protected void checkAggregationExclude(AggregationExclude aggExclude, DatabaseSchema schemaName) {
        // empty
    }

    protected void checkLevel(Level level, Hierarchy hierarchy, Dimension parentDimension,
            Cube cube) {
        if (level != null) {
            checkAnnotationList(level.getAnnotations());

            if (level.getColumn() instanceof SQLExpressionColumn sec) {
                checkSqlExpression(sec);

            }
            if (level.getNameColumn() instanceof SQLExpressionColumn sec) {
                checkSqlExpression(sec);

            }
            if (level.getCaptionColumn() instanceof SQLExpressionColumn sec) {
                checkSqlExpression(sec);

            }
            if (!level.getOrdinalColumns().isEmpty()) {
                for (OrderedColumn oc : level.getOrdinalColumns()) {
                    if (oc.getColumn() != null && oc.getColumn() instanceof SQLExpressionColumn sec) {
                        checkSqlExpression(sec);
                    }
                }
            }

            checkMemberPropertyList(level.getMemberProperties(), level, hierarchy, cube);

            checkMemberFormatter(level.getMemberFormatter());
        }
    }

    protected void checkMemberFormatter(MemberFormatter memberFormatter) {
        // empty

    }

    protected void checkParentChildLink(ParentChildLink closure) {
        if (closure != null) {
            checkTable(closure.getTable());
        }
    }

    protected void checkSqlExpression(SQLExpressionColumn sqlExpression) {
        if (sqlExpression != null) {
            checkSqlList(sqlExpression.getSqls());
        }
    }

    @SuppressWarnings("java:S1172")
    protected void checkMemberProperty(MemberProperty property, Level level, Hierarchy hierarchy,
            Cube cube) {
        if (property != null) {
            // ElementFormatter
            checkMemberPropertyFormatter(property.getFormatter());
        }
    }

    protected void checkMemberPropertyFormatter(MemberPropertyFormatter formatter) {
        // empty

    }

    protected void checkPhysicalCube(PhysicalCube physicalCube, Catalog schema) {
        if (physicalCube != null) {
            checkQuery(physicalCube.getQuery());
            checkWritebackTable(physicalCube.getWritebackTable(), physicalCube);
            checkActionList(physicalCube.getAction());
            checkMeasureGroupList(physicalCube.getMeasureGroups(), physicalCube);
            checkDimensionConnectorsList(physicalCube.getDimensionConnectors(), physicalCube, schema);
        }
    }

    protected void checkVirtualCube(VirtualCube virtCube, Catalog schema) {
        if (virtCube != null) {
            checkCubeUsageList(virtCube.getCubeUsages());
            checkReferencedMeasureList(virtCube.getReferencedMeasures(), virtCube, schema);
            checkReferencedCalculatedMemberList(virtCube.getCalculatedMembers(), virtCube, schema);
            checkDimensionConnectorsList(virtCube.getDimensionConnectors(), null, schema);
        }
    }

    protected void checkReferencedCalculatedMemberList(List<? extends CalculatedMember> referencedMeasures,
            VirtualCube virtCube, Catalog schema) {
        if (referencedMeasures != null) {
            referencedMeasures.forEach(r -> checkReferencedCalculatedMember(r));
        }
    }

    protected void checkReferencedCalculatedMember(CalculatedMember r) {
        // empty
    }

    protected void checkReferencedMeasureList(List<? extends BaseMeasure> referencedMeasures,
            VirtualCube virtCube, Catalog schema) {
        if (referencedMeasures != null) {
            referencedMeasures.forEach(r -> checkReferencedCalculatedMember(r));
        }
    }

    protected void checkReferencedCalculatedMember(BaseMeasure r) {
        // empty
    }

    protected void checkCubeUsage(CubeConnector cube) {
        // empty
    }

    protected void checkVirtualCubeMeasure(BaseMeasure virtualCubeMeasure, VirtualCube vCube,
            Catalog schema) {
        if (virtualCubeMeasure != null) {
            checkAnnotationList(virtualCubeMeasure.getAnnotations());
        }
    }

    protected void checkCalculatedMember(CalculatedMember calculatedMember) {
        if (calculatedMember != null) {
            checkAnnotationList(calculatedMember.getAnnotations());
            checkCalculatedMemberPropertyList(calculatedMember.getCalculatedMemberProperties());

            if (calculatedMember.getFormula() != null) {
                checkFormula(calculatedMember.getFormula());
            }

            if (calculatedMember.getCellFormatter() != null) {
                checkCellFormatter(calculatedMember.getCellFormatter());
            }
        }
    }

    protected void checkFormula(String formula) {
        // empty
    }

    /**
     * Validates a column, and returns an error message if it is invalid.
     *
     * @param column          Column
     * @param fieldName       Field name
     * @param level           Level
     * @param cube            Cube
     * @param parentHierarchy Hierarchy
     */
    protected void checkColumn(Column column, String fieldName, Level level, Cube cube,
            Hierarchy parentHierarchy) {
        // empty
    }

    protected void checkNamedSet(NamedSet namedSet) {
        if (namedSet != null) {
            checkAnnotationList(namedSet.getAnnotations());

            if (namedSet.getFormula() != null) {
                checkFormula(namedSet.getFormula());
            }
        }
    }

    protected void checkParameter(Parameter parameter) {
        // empty
    }

    protected void checkAnnotation(Annotation annotation) {
        // empty
    }

    protected void checkTranslation(Translation annotation) {
        // empty
    }

    protected void checkRole(AccessRole role, Catalog schema) {
        if (role != null) {
            checkAnnotationList(role.getAnnotations());
            checkSchemaGrantList(role.getAccessCatalogGrants(), schema);
            checkAccessRoleList(role.getReferencedAccessRoles());
        }
    }

    protected void checkAccessRoleList(List<? extends AccessRole> list) {
        if (list != null) {
            list.forEach(r -> checkAccessRole(r));
        }
    }

    protected void checkAccessRole(AccessRole r) {
        // empty
    }

    protected void checkSchemaGrant(AccessCatalogGrant schemaGrant, Catalog schema) {
        if (schemaGrant != null) {
            checkCubeGrantList(schemaGrant.getCubeGrants(), schema);
        }
    }

    protected void checkCubeGrant(AccessCubeGrant cubeGrant, Catalog schema) {
        if (cubeGrant != null && cubeGrant.getCube() != null) {
            checkDimensionGrantList(cubeGrant.getDimensionGrants());
            checkHierarchyGrantList(cubeGrant.getHierarchyGrants(), cubeGrant.getCube(), schema);
        }
    }

    protected void checkHierarchyGrant(AccessHierarchyGrant hierarchyGrant, Cube cubeName,
            Catalog schema) {
        if (hierarchyGrant != null) {
            checkMemberGrantList(hierarchyGrant.getMemberGrants(), cubeName, schema);
        }
    }

    protected void checkMemberGrant(AccessMemberGrant memberGrant, Cube cubeName, Catalog schema) {
        // empty
    }

    protected void checkDimensionGrant(AccessDimensionGrant dimensionGrant) {
        // empty
    }

    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

    protected void checkNamedSetList(List<? extends NamedSet> namedSet) {
        if (namedSet != null) {
            namedSet.forEach(this::checkNamedSet);
        }
    }

    protected void checkWritebackTable(WritebackTable writebackTable, PhysicalCube cube) {
        if (writebackTable != null) {
            if (writebackTable.getWritebackAttribute() != null) {
                writebackTable.getWritebackAttribute().forEach(c -> checkWritebackAttribute(c, cube));
            }
            if (writebackTable.getWritebackMeasure() != null) {
                writebackTable.getWritebackMeasure().forEach(c -> checkWritebackMeasure(c, cube));
            }
        }
    }

    protected void checkWritebackMeasure(WritebackMeasure writebackColumn, PhysicalCube cube) {
        // empty
    }

    protected void checkWritebackAttribute(WritebackAttribute writebackColumn, PhysicalCube cube) {
        // empty
    }

    protected boolean isSchemaRequired() {
        return true;
    }

    protected String orNotSet(String value) {
        return value == null ? NOT_SET : value;
    }

    protected void checkFact(Cube cube, Catalog schema) {
        // empty
    }

    private void checkHintList(List<? extends TableQueryOptimizationHint> list) {
        if (list != null) {
            list.forEach(this::checkHint);
        }
    }

    private void checkCalculatedMemberPropertyList(List<? extends CalculatedMemberProperty> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMemberProperty);
        }
    }

    private void checkAggregationTableList(List<? extends AggregationTable> list, DatabaseSchema schema) {
        if (list != null) {
            list.forEach(at -> checkAggregationTable(at, schema));
        }
    }

    private void checkMemberReaderParameterList(List<? extends MemberReaderParameter> list) {
        if (list != null) {
            list.forEach(this::checkMemberReaderParameter);
        }
    }

    private void checkAggregationMeasureFactCountList(List<? extends AggregationMeasureFactCount> list) {
        if (list != null) {
            list.forEach(this::checkAggregationMeasureFactCount);
        }
    }

    private void checkAggregationLevelList(List<? extends AggregationLevel> list) {
        if (list != null) {
            list.forEach(this::checkAggregationLevel);
        }
    }

    private void checkAggregationLevelPropertyList(List<? extends AggregationLevelProperty> list) {
        if (list != null) {
            list.forEach(this::checkAggregationLevelProperty);
        }
    }

    private void checkAggregationMeasureList(List<? extends AggregationMeasure> list) {
        if (list != null) {
            list.forEach(this::checkAggregationMeasure);
        }
    }

    private void checkAggregationForeignKeyList(List<? extends AggregationForeignKey> list) {
        if (list != null) {
            list.forEach(this::checkAggregationForeignKey);
        }
    }

    private void checkAggregationColumnNameList(List<? extends AggregationColumnName> list) {
        if (list != null) {
            list.forEach(this::checkAggregationColumnName);
        }
    }

    private void checkAggregationExcludeList(List<? extends AggregationExclude> list,
            DatabaseSchema schema) {
        if (list != null) {
            list.forEach(ae -> checkAggregationExclude(ae, schema));
        }
    }

    private void checkMemberPropertyList(List<? extends MemberProperty> list, Level level,
            Hierarchy hierarchy, Cube cube) {
        if (list != null) {
            list.forEach(it -> checkMemberProperty(it, level, hierarchy, cube));
        }
    }

    private void checkCubeUsageList(List<? extends CubeConnector> list) {
        if (list != null) {
            list.forEach(this::checkCubeUsage);
        }
    }

    private void checkCubeGrantList(List<? extends AccessCubeGrant> list, Catalog schema) {
        if (list != null) {
            list.forEach(cg -> checkCubeGrant(cg, schema));
        }
    }

    private void checkHierarchyGrantList(List<? extends AccessHierarchyGrant> list, Cube cubeName,
            Catalog schema) {
        if (list != null) {
            list.forEach(hg -> checkHierarchyGrant(hg, cubeName, schema));
        }
    }

    private void checkMemberGrantList(List<? extends AccessMemberGrant> list, Cube cubeName,
            Catalog schema) {
        if (list != null) {
            list.forEach(mg -> checkMemberGrant(mg, cubeName, schema));
        }
    }

    private void checkDimensionGrantList(List<? extends AccessDimensionGrant> list) {
        if (list != null) {
            list.forEach(this::checkDimensionGrant);
        }
    }

    private void checkSchemaGrantList(List<? extends AccessCatalogGrant> list, Catalog schema) {
        if (list != null) {
            list.forEach(sg -> checkSchemaGrant(sg, schema));
        }
    }

    protected void checkDimensionConnectorList(List<? extends DimensionConnector> list, Cube cube,
            Catalog schema) {
        if (list != null) {
            list.forEach(it -> checkDimensionConnector(it, cube, schema));
        }
    }

    protected void checkDimensionConnector(DimensionConnector dc, Cube cube, Catalog schema) {
        if (dc != null) {
            checkDimension(dc.getDimension(), cube, schema);
        }
    }

    private void checkCubeList(List<? extends Cube> list, Catalog schema) {
        if (list != null) {
            list.forEach(cube -> checkCube(cube, schema));
        }
    }

    private void checkAnnotationList(List<? extends Annotation> list) {
        if (list != null) {
            list.forEach(this::checkAnnotation);
        }
    }

    private void checkTranslationList(List<? extends Translation> list) {
        if (list != null) {
            list.forEach(this::checkTranslation);
        }
    }

    private void checkParameterList(List<? extends Parameter> list) {
        if (list != null) {
            list.forEach(this::checkParameter);
        }
    }

    private void checkCalculatedMemberList(List<? extends CalculatedMember> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMember);
        }
    }

    private void checkMeasureGroupList(List<? extends MeasureGroup> list, Cube cube) {
        if (list != null) {
            list.forEach(mg -> checkMeasureGroup(mg, cube));
        }
    }

    private Object checkMeasureGroup(MeasureGroup mg, Cube cube) {
        if (mg != null) {
            checkMeasureList(mg.getMeasures(), cube);
        }
        return null;
    }

    private void checkMeasureList(List<? extends BaseMeasure> list, Cube cube) {
        if (list != null) {
            list.forEach(m -> checkMeasure(m, cube));
        }
    }

    private void checkKpiList(List<? extends Kpi> list, Cube cube) {
        if (list != null) {
            list.forEach(k -> checkKpi(k, cube));
            checkKpiPrentRing(list, cube);
        }
    }

    protected void checkKpiPrentRing(List<? extends Kpi> list, Cube cube) {
        //empty
    }

    public boolean hasRing(Kpi head) {
        Set<Kpi> visited = new HashSet<>();
        Kpi current = head;

        while (current != null) {
            if (visited.contains(current)) {
                return true;
            }
            visited.add(current);
            current = current.getParentKpi();
        }
        return false;
    }

    private void checkKpiList(List<? extends Kpi> list, VirtualCube cube) {
        if (list != null) {
            list.forEach(k -> checkKpi(k, cube));
        }
    }

    private void checkRoleList(List<? extends AccessRole> list, Catalog schema) {
        if (list != null) {
            list.forEach(r -> checkRole(r, schema));
        }
    }

    private void checkActionList(List<? extends Action> list) {
        if (list != null) {
            list.forEach(this::checkAction);
        }
    }
}
