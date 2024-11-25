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
import java.util.List;

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rdb.structure.api.model.DatabaseSchema;
import org.eclipse.daanse.rdb.structure.api.model.InlineTable;
import org.eclipse.daanse.rdb.structure.api.model.Row;
import org.eclipse.daanse.rdb.structure.api.model.RowValue;
import org.eclipse.daanse.rdb.structure.api.model.SqlView;
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
import org.eclipse.daanse.rolap.mapping.api.model.AggregationNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughActionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;
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
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;


public abstract class AbstractSchemaWalker {

    protected List<VerificationResult> results = new ArrayList<>();

    public List<VerificationResult> checkSchema(SchemaMapping schema) {

        if (schema != null) {
            checkAnnotationList(schema.getAnnotations());
            checkParameterList(schema.getParameters());
            //checkCubeDimensionList(schema.dimensions(), null, schema);
            checkCubeList(schema.getCubes(), schema);
            //checkVirtualCubeList(schema.virtualCubes(), schema);
            checkNamedSetList(schema.getNamedSets());
            checkRoleList(schema.getAccessRoles(), schema);
            //checkUserDefinedFunctionList(schema.userDefinedFunctions());
        }

        return results;
    }

    protected void checkCube(CubeMapping cube, SchemaMapping schema) {
        if (cube != null) {
            checkAnnotationList(cube.getAnnotations());
            //checkCubeDimensionList(cube.dimensionUsageOrDimensions(), cube, schema);
            if (cube instanceof PhysicalCubeMapping physicalCube) {
               checkMeasureGroupList(physicalCube.getMeasureGroups(), cube);
               checkActionList(physicalCube.getAction());
               checkWritebackTableOption(physicalCube.getWritebackTable(), cube);
               checkKpiList(physicalCube.getKpis(), cube);
               if (physicalCube.getAction() != null) {
                   physicalCube.getAction().forEach(this::checkAction);
               }
            }
            checkCalculatedMemberList(cube.getCalculatedMembers());
            checkNamedSetList(cube.getNamedSets());
        }
    }

    protected void checkAction(ActionMappingMapping action) {
        if (action != null) {
            checkAnnotationList(action.getAnnotations());
        }
        if (action instanceof DrillThroughActionMapping drillThroughAction) {
           checkDrillThroughAction(drillThroughAction);
        }
    }

    protected void checkDrillThroughAction(DrillThroughActionMapping drillThroughAction) {
        if (drillThroughAction != null) {
                checkDrillThroughAttributeList(drillThroughAction.getDrillThroughAttribute());
                checkDrillThroughMeasureList(drillThroughAction.getDrillThroughMeasure());
        }
    }

    private void checkDrillThroughAttributeList(List<? extends DrillThroughAttributeMapping> drillThroughAttribute) {
        // TODO Auto-generated method stub
	}

    private void checkDrillThroughMeasureList(List<? extends MeasureMapping> drillThroughAttribute) {
        // TODO Auto-generated method stub
	}


    protected void checkDrillThroughAttribute(DrillThroughAttributeMapping drillThroughAttribute) {
        //empty
    }

    protected void checkDrillThroughMeasure(MeasureMapping drillThroughElement) {
        //empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkMeasure(MeasureMapping measure, CubeMapping cube) {
        if (measure != null) {
            checkMeasureColumn(measure, cube);
            checkMeasureAggregation(measure, cube);
            checkAnnotationList(measure.getAnnotations());
            checkCalculatedMemberPropertyList(measure.getCalculatedMemberProperties());
            checkExpressionView(measure.getMeasureExpression());
            checkCellFormatter(measure.getCellFormatter());
        }
    }

    protected void checkKpi(KpiMapping kpi, CubeMapping cube) {
        if (kpi != null) {
            checkAnnotationList(kpi.getAnnotations());
            checkTranslationList(kpi.getTranslations());
        }
    }

    protected void checkKpi(KpiMapping kpi, VirtualCubeMapping cube) {
        if (kpi != null) {
            checkAnnotationList(kpi.getAnnotations());
            checkTranslationList(kpi.getTranslations());
        }
    }

    protected void checkMeasureAggregation(MeasureMapping measure, CubeMapping cube) {
        //empty
    }

    protected void checkMeasureColumn(MeasureMapping measure, CubeMapping cube) {
        //empty
    }

    protected void checkCalculatedMemberProperty(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        //empty
    }

    protected void checkExpressionView(SQLExpressionMapping measureExpression) {
        if (measureExpression != null) {
            checkSqlList(measureExpression.getSqls());
        }
    }

    private void checkSqlList(List<? extends SQLMapping> sqls) {
		// TODO Auto-generated method stub
		
	}

	protected void checkSQL(SQLMapping sql) {
        //empty
    }

    protected void checkCellFormatter(CellFormatterMapping elementFormatter) {
        //empty
    }

    protected void checkElementFormatterClass(String className) {
        //empty
    }

    protected void checkCubeDimension(DimensionMapping cubeDimension, CubeMapping cube, SchemaMapping schema) {
        if (cubeDimension != null) {
            checkAnnotationList(cubeDimension.getAnnotations());
            if (cubeDimension.getHierarchies() != null) {
            	cubeDimension.getHierarchies()
                    .forEach(h -> checkHierarchy(h, cubeDimension, cube));
            }
        }
    }

    protected void checkHierarchy(HierarchyMapping hierarchy, DimensionMapping cubeDimension, CubeMapping cube) {
        if (hierarchy != null) {
            checkAnnotationList(hierarchy.getAnnotations());
            checkMemberReaderParameterList(hierarchy.getMemberReaderParameters());
            checkRelationOrJoin(hierarchy.getQuery());
            //Level
            if (hierarchy.getLevels() != null) {
                hierarchy.getLevels().forEach(l -> checkLevel(l, hierarchy, cubeDimension, cube));
            }
        }
    }

    protected void checkMemberReaderParameter(MemberReaderParameterMapping memberReaderParameter) {
        //empty
    }

    protected void checkJoinQuery(JoinQueryMapping join) {
        if (join != null) {
            checkJoinedQueryElement(join.getLeft());
            checkJoinedQueryElement(join.getRight());
        }
    }

    protected void checkJoinedQueryElement(JoinedQueryElementMapping element) {
        if (element != null) {
            checkRelationOrJoin(element.getQuery());
        }
    }

    protected void checkRelationOrJoin(QueryMapping relationOrJoin) {
        if (relationOrJoin != null) {
            if (relationOrJoin instanceof InlineTableQueryMapping inlineTable) {
                checkInlineTableQuery(inlineTable);
            }
            if (relationOrJoin instanceof JoinQueryMapping join) {
                checkJoinQuery(join);
            }
            if (relationOrJoin instanceof TableQueryMapping table) {
                checkTable(table);
            }
            if (relationOrJoin instanceof SqlSelectQueryMapping view) {
                checkSqlSelectQuery(view);
            }
        }
    }

    protected void checkSqlSelectQuery(SqlSelectQueryMapping relationOrJoin) {
        if (relationOrJoin != null) {
            checkSqlView(relationOrJoin.getSql());
        }
    }

    protected void checkSqlView(SqlView sql) {
        if (sql != null) {
            
        }
	}

	protected void checkInlineTableQuery(InlineTableQueryMapping relationOrJoin) {
        if (relationOrJoin != null) {
            checkInlineTable(relationOrJoin.getTable());
        }
    }

    protected void checkInlineTable(InlineTable table) {
        if (table != null) {
            checkRows(table.getRows());
        }
	}

	private void checkRows(List<? extends Row> rows) {
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
		// TODO Auto-generated method stub
		
	}

	protected void checkColumn(Column columnDef) {
        //empty
    }

    protected void checkTable(TableQueryMapping table) {
        if (table != null) {
            checkSqlWhereExpression(table.getSqlWhereExpression());

            checkAggregationExcludeList(table.getAggregationExcludes(), table.getTable().getSchema());

            checkAggregationTableList(table.getAggregationTables(), table.getTable().getSchema());

            checkHintList(table.getOptimizationHints());
        }
    }

    private void checkSqlWhereExpression(SQLMapping sqlWhereExpression) {
	}

	protected void checkHint(TableQueryOptimizationHintMapping hint) {
        //empty
    }

    protected void checkAggregationTable(AggregationTableMapping aggTable, DatabaseSchema schema) {
        if (aggTable != null) {
            checkAggregationColumnName(aggTable.getAggregationFactCount());
            checkAggregationColumnNameList(aggTable.getAggregationIgnoreColumns());
            checkAggregationForeignKeyList(aggTable.getAggregationForeignKeys());
            checkAggregationMeasureList(aggTable.getAggregationMeasures());
            checkAggregationLevelList(aggTable.getAggregationLevels());
            checkAggregationMeasureFactCountList(aggTable.getAggregationMeasureFactCounts());
            if (aggTable instanceof AggregationNameMapping aggName) {
                checkAggregationName(aggName);
            }
            if (aggTable instanceof AggregationPatternMapping aggPattern) {
                checkAggregationPattern(aggPattern, schema);
            }
        }
    }

    protected void checkAggregationPattern(AggregationPatternMapping aggTable, DatabaseSchema schema) {
        if (aggTable != null) {
            checkAggregationExcludeList(aggTable.getExcludes(), schema);
        }
    }

    protected void checkAggregationName(AggregationNameMapping aggTable) {
        //empty
    }

    protected void checkAggregationMeasureFactCount(AggregationMeasureFactCountMapping aggMeasureFactCount) {
        //empty
    }

    protected void checkAggregationLevel(AggregationLevelMapping aggLevel) {
        if (aggLevel != null) {
            checkAggregationLevelPropertyList(aggLevel.getAggregationLevelProperties());
        }
    }

    protected void checkAggregationLevelProperty(AggregationLevelPropertyMapping aggLevelProperty) {
        //empty
    }

    protected void checkAggregationMeasure(AggregationMeasureMapping aggMeasure) {
        //empty
    }

    protected void checkAggregationForeignKey(AggregationForeignKeyMapping aggForeignKey) {
        //empty
    }

    protected void checkAggregationColumnName(AggregationColumnNameMapping aggFactCount) {
        if (aggFactCount != null && aggFactCount instanceof AggregationMeasureFactCountMapping aggMeasureFactCount) {
            checkAggregationMeasureFactCount(aggMeasureFactCount);
        }
    }

    protected void checkAggregationExclude(AggregationExcludeMapping aggExclude, DatabaseSchema schemaName) {
        //empty
    }

    @SuppressWarnings("java:S1172")
    protected void checkLevel(
        LevelMapping level, HierarchyMapping hierarchy,
        DimensionMapping parentDimension, CubeMapping cube
    ) {
        if (level != null) {
            checkAnnotationList(level.getAnnotations());

            checkSqlExpression(level.getKeyExpression());

            checkSqlExpression(level.getNameExpression());

            checkSqlExpression(level.getCaptionExpression());

            checkSqlExpression(level.getOrdinalExpression());

            checkSqlExpression(level.getParentExpression());

            checkParentChildLink(level.getParentChildLink());

            checkMemberPropertyList(level.getMemberProperties(), level, hierarchy,
                cube);

            checkMemberFormatter(level.getMemberFormatter());
        }
    }

    private void checkMemberFormatter(MemberFormatterMapping memberFormatter) {
		// TODO Auto-generated method stub
		
	}

	protected void checkParentChildLink(ParentChildLinkMapping closure) {
        if (closure != null) {
            checkTable(closure.getTable());
        }
    }

    protected void checkSqlExpression(SQLExpressionMapping sqlExpression) {
        if (sqlExpression != null) {
            checkSqlMappingList(sqlExpression.getSqls());
        }
    }

    private void checkSqlMappingList(List<? extends SQLMapping> sqls) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("java:S1172")
    protected void checkProperty(
        MemberPropertyMapping property, LevelMapping level,
        HierarchyMapping hierarchy, CubeMapping cube
    ) {
        if (property != null) {
            //ElementFormatter
            checkMemberPropertyFormatter(property.getFormatter());
        }
    }

    protected void checkMemberPropertyFormatter(MemberPropertyFormatterMapping formatter) {
		// TODO Auto-generated method stub
		
	}

	protected void checkVirtualCube(VirtualCubeMapping virtCube, SchemaMapping schema) {
        if (virtCube != null) {
            checkAnnotationList(virtCube.getAnnotations());

            checkCubeUsageList(virtCube.getCubeUsages());

            checkDimensionConnectorsList(virtCube.getDimensionConnectors(), null, schema);

            checkReferencedMeasureList(virtCube.getReferencedMeasures(), virtCube, schema);

            checkNamedSetList(virtCube.getNamedSets());

            checkKpiList(virtCube.getKpis(), virtCube);

            //CalculatedMember
            if (virtCube.getCalculatedMembers() != null) {
                virtCube.getCalculatedMembers()
                    .forEach(this::checkCalculatedMember);
            }
        }
    }

	protected void checkDimensionConnectorsList(List<? extends DimensionConnectorMapping> dimensionConnectors,
			Object object, SchemaMapping schema) {
		// TODO Auto-generated method stub
		
	}

	private void checkReferencedMeasureList(List<? extends MeasureMapping> referencedMeasures,
			VirtualCubeMapping virtCube, SchemaMapping schema) {
		// TODO Auto-generated method stub
		
	}

	protected void checkCubeUsage(CubeConnectorMapping cube) {
        //empty
    }

    protected void checkVirtualCubeMeasure(MeasureMapping virtualCubeMeasure, VirtualCubeMapping vCube, SchemaMapping schema) {
        if (virtualCubeMeasure != null) {
            checkAnnotationList(virtualCubeMeasure.getAnnotations());
        }
    }

    protected void checkCalculatedMember(CalculatedMemberMapping calculatedMember) {
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
        //empty
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
    protected void checkColumn(
        String column, String fieldName, LevelMapping level,
        CubeMapping cube, HierarchyMapping parentHierarchy
    ) {
        //empty
    }

    protected void checkNamedSet(NamedSetMapping namedSet) {
        if (namedSet != null) {
            checkAnnotationList(namedSet.getAnnotations());

            if (namedSet.getFormula() != null) {
                checkFormula(namedSet.getFormula());
            }
        }
    }

    protected void checkParameter(ParameterMapping parameter) {
        //empty
    }

    protected void checkAnnotation(AnnotationMapping annotation) {
        //empty
    }

    protected void checkTranslation(TranslationMapping annotation) {
        //empty
    }

    protected void checkRole(AccessRoleMapping role, SchemaMapping schema) {
        if (role != null) {
            checkAnnotationList(role.getAnnotations());
            checkSchemaGrantList(role.getAccessSchemaGrants(), schema);
            checkAccessRoleList(role.getReferencedAccessRoles());
        }
    }


    private void checkAccessRoleList(List<? extends AccessRoleMapping> list) {
        if (list != null) {
            //TODO
        }
    }


    protected void checkSchemaGrant(AccessSchemaGrantMapping schemaGrant, SchemaMapping schema) {
        if (schemaGrant != null) {
            checkCubeGrantList(schemaGrant.getCubeGrants(), schema);
        }
    }

    protected void checkCubeGrant(AccessCubeGrantMapping cubeGrant, SchemaMapping schema) {
        if (cubeGrant != null && cubeGrant.getCube() != null) {
            checkDimensionGrantList(cubeGrant.getDimensionGrants());
            checkHierarchyGrantList(cubeGrant.getHierarchyGrants(), cubeGrant.getCube(), schema);
        }
    }

    protected void checkHierarchyGrant(AccessHierarchyGrantMapping hierarchyGrant, CubeMapping cubeName,  SchemaMapping schema) {
        if (hierarchyGrant != null) {
            checkMemberGrantList(hierarchyGrant.getMemberGrants(), cubeName, schema);
        }
    }

    protected void checkMemberGrant(AccessMemberGrantMapping memberGrant, CubeMapping cubeName, SchemaMapping schema) {
        //empty
    }

    protected void checkDimensionGrant(AccessDimensionGrantMapping dimensionGrant) {
        //empty
    }

    protected static boolean isEmpty(String v) {
        return (v == null) || v.equals("");
    }

    protected void checkNamedSetList(List<? extends NamedSetMapping> namedSet) {
        if (namedSet != null) {
            namedSet.forEach(this::checkNamedSet);
        }
    }

    protected void checkWritebackTable(WritebackTableMapping writebackTable, CubeMapping cube) {
        if (writebackTable != null) {
            if (writebackTable.getWritebackAttribute() != null) {
                writebackTable.getWritebackAttribute().forEach(c -> checkWritebackAttribute(c, cube));
            }
            if (writebackTable.getWritebackMeasure() != null) {
                writebackTable.getWritebackMeasure().forEach(c -> checkWritebackMeasure(c, cube));
            }
        }
    }

    protected void checkWritebackMeasure(WritebackMeasureMapping writebackColumn, CubeMapping cube) {
        //empty
    }

    protected void checkWritebackAttribute(WritebackAttributeMapping writebackColumn, CubeMapping cube) {
        //empty
    }

    protected boolean isSchemaRequired() {
        return true;
    }

    protected String orNotSet(String value) {
        return value == null ? NOT_SET : value;
    }

    protected void checkFact(CubeMapping cube, SchemaMapping schema) {
        //empty
    }

    private void checkHintList(List<? extends TableQueryOptimizationHintMapping> list) {
        if (list != null) {
            list.forEach(this::checkHint);
        }
    }

    private void checkCalculatedMemberPropertyList(List<? extends CalculatedMemberPropertyMapping> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMemberProperty);
        }
    }

    private void checkAggregationTableList(List<? extends AggregationTableMapping> list, DatabaseSchema schema) {
        if (list != null) {
            list.forEach(at -> checkAggregationTable(at, schema));
        }
    }

    protected void checkSQLList(List<? extends SQLMapping> list) {
        if (list != null) {
            list.forEach(this::checkSQL);
        }
    }

    private void checkMemberReaderParameterList(List<? extends MemberReaderParameterMapping> list) {
        if (list != null) {
            list.forEach(this::checkMemberReaderParameter);
        }
    }

    private void checkAggregationMeasureFactCountList(List<? extends AggregationMeasureFactCountMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationMeasureFactCount);
        }
    }

    private void checkAggregationLevelList(List<? extends AggregationLevelMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationLevel);
        }
    }

    private void checkAggregationLevelPropertyList(List<? extends AggregationLevelPropertyMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationLevelProperty);
        }
    }

    private void checkAggregationMeasureList(List<? extends AggregationMeasureMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationMeasure);
        }
    }

    private void checkAggregationForeignKeyList(List<? extends AggregationForeignKeyMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationForeignKey);
        }
    }

    private void checkAggregationColumnNameList(List<? extends AggregationColumnNameMapping> list) {
        if (list != null) {
            list.forEach(this::checkAggregationColumnName);
        }
    }

    private void checkAggregationExcludeList(List<? extends AggregationExcludeMapping> list, DatabaseSchema schema) {
        if (list != null) {
            list.forEach(ae -> checkAggregationExclude(ae, schema));
        }
    }

    private void checkMemberPropertyList(
        List<? extends MemberPropertyMapping> list,
        LevelMapping level,
        HierarchyMapping hierarchy, CubeMapping cube
    ) {
        if (list != null) {
            list.forEach(it -> checkProperty(it, level,
                hierarchy, cube));
        }
    }

    private void checkCubeUsageList(List<? extends CubeConnectorMapping> list) {
        if (list != null) {
            list.forEach(this::checkCubeUsage);
        }
    }

    private void checkCubeGrantList(List<? extends AccessCubeGrantMapping> list, SchemaMapping schema) {
        if (list != null) {
            list.forEach(cg -> checkCubeGrant(cg, schema));
        }
    }

    private void checkHierarchyGrantList(List<? extends AccessHierarchyGrantMapping> list, CubeMapping cubeName, SchemaMapping schema) {
        if (list != null) {
            list.forEach(hg -> checkHierarchyGrant(hg, cubeName, schema));
        }
    }

    private void checkMemberGrantList(List<? extends AccessMemberGrantMapping> list, CubeMapping cubeName, SchemaMapping schema) {
        if (list != null) {
            list.forEach(mg -> checkMemberGrant(mg, cubeName, schema));
        }
    }

    private void checkDimensionGrantList(List<? extends AccessDimensionGrantMapping> list) {
        if (list != null) {
            list.forEach(this::checkDimensionGrant);
        }
    }

    private void checkSchemaGrantList(List<? extends AccessSchemaGrantMapping> list, SchemaMapping schema) {
        if (list != null) {
            list.forEach(sg -> checkSchemaGrant(sg, schema));
        }
    }

    private void checkDimensionList(List<? extends DimensionMapping> list, CubeMapping cube, SchemaMapping schema) {
        if (list != null) {
            list.forEach(it -> checkCubeDimension(it, cube, schema));
        }
    }

    private void checkVirtualCubeList(List<? extends VirtualCubeMapping> list, SchemaMapping schema) {
        if (list != null) {
            list.forEach(vc -> checkVirtualCube(vc, schema));
        }
    }

    private void checkCubeList(List<? extends CubeMapping> list, SchemaMapping schema) {
        if (list != null) {
            list.forEach(cube -> checkCube(cube, schema));
        }
    }

    private void checkAnnotationList(List<? extends AnnotationMapping> list) {
        if (list != null) {
            list.forEach(this::checkAnnotation);
        }
    }

    private void checkTranslationList(List<? extends TranslationMapping> list) {
        if (list != null) {
            list.forEach(this::checkTranslation);
        }
    }

    private void checkParameterList(List<? extends ParameterMapping> list) {
        if (list != null) {
            list.forEach(this::checkParameter);
        }
    }

    private void checkCalculatedMemberList(List<? extends CalculatedMemberMapping> list) {
        if (list != null) {
            list.forEach(this::checkCalculatedMember);
        }
    }

    private void checkMeasureGroupList(List<? extends MeasureGroupMapping> list, CubeMapping cube) {
        if (list != null) {
            list.forEach(mg -> checkMeasureGroup(mg, cube));
        }
    }

    private Object checkMeasureGroup(MeasureGroupMapping mg, CubeMapping cube) {
		// TODO Auto-generated method stub
		return null;
	}

	private void checkMeasureList(List<? extends MeasureMapping> list, CubeMapping cube) {
        if (list != null) {
            list.forEach(m -> checkMeasure(m, cube));
        }
    }

    private void checkKpiList(List<? extends KpiMapping> list, CubeMapping cube) {
        if (list != null) {
            list.forEach(k -> checkKpi(k, cube));
        }
    }

    private void checkKpiList(List<? extends KpiMapping> list, VirtualCubeMapping cube) {
        if (list != null) {
            list.forEach(k -> checkKpi(k, cube));
        }
    }

    private void checkRoleList(List<? extends AccessRoleMapping> list, SchemaMapping schema) {
        if (list != null) {
            list.forEach(r -> checkRole(r, schema));
        }
    }

    private void checkWritebackTableOption(WritebackTableMapping writebackTable, CubeMapping cube) {
        if (writebackTable != null) {
            checkWritebackTable(writebackTable, cube);
        }
    }

    private void checkActionList(List<? extends ActionMappingMapping> list) {
        if (list != null) {
            list.forEach(this::checkAction);
        }
    }
}
