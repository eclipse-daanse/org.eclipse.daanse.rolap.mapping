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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import static org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR;
import static org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.eclipse.daanse.rolap.mapping.model.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.model.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.Action;
import org.eclipse.daanse.rolap.mapping.model.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.model.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.model.AggregationLevel;
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
import org.eclipse.daanse.rolap.mapping.model.Column;
import org.eclipse.daanse.rolap.mapping.model.ColumnBaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.Cube;
import org.eclipse.daanse.rolap.mapping.model.CubeConnector;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.Dimension;
import org.eclipse.daanse.rolap.mapping.model.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAttribute;
import org.eclipse.daanse.rolap.mapping.model.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.Hierarchy;
import org.eclipse.daanse.rolap.mapping.model.InlineTable;
import org.eclipse.daanse.rolap.mapping.model.JoinQuery;
import org.eclipse.daanse.rolap.mapping.model.Kpi;
import org.eclipse.daanse.rolap.mapping.model.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.ParentChildHierarchy;
import org.eclipse.daanse.rolap.mapping.model.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.RowValue;
import org.eclipse.daanse.rolap.mapping.model.SQLExpressionBaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.model.SqlStatement;
import org.eclipse.daanse.rolap.mapping.model.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.Table;
import org.eclipse.daanse.rolap.mapping.model.TableQuery;
import org.eclipse.daanse.rolap.mapping.model.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.model.TimeDimension;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.model.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.WritebackTable;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Cause;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaExplorer;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.VerificationResultR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MandantoriesSchemaWalker extends AbstractSchemaWalker {

    private static final Logger LOGGER = LoggerFactory.getLogger(MandantoriesSchemaWalker.class);
    private static final String[] DEF_LEVEL = {"getColumn", "getNameColumn", "getOrdinalColumn", "getCaptionColumn"};

    public MandantoriesSchemaWalker(MandantoriesVerifierConfig config) {
    }

    @Override
    public List<VerificationResult> checkSchema(Catalog schema) {
        super.checkSchema(schema);
        if (schema != null) {
            if (isEmpty(schema.getName())) {
                results.add(new VerificationResultR(SCHEMA, SCHEMA_NAME_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
        } else {
            results.add(new VerificationResultR(SCHEMA, SCHEMA_MUST_BE_NOT_NULL, ERROR,
                Cause.SCHEMA));
        }

        return results;
    }

    @Override
    protected void checkCube(Cube cube, Catalog schema) {
        super.checkCube(cube, schema);
        if (cube != null) {
            if (isEmpty(cube.getName())) {
                results.add(new VerificationResultR(CUBE, CUBE_NAME_MUST_SET, Level.ERROR,
                    Cause.SCHEMA));
            }
            if (cube instanceof PhysicalCube physicalCube) {
                if (physicalCube.getQuery() == null
                    || ((physicalCube.getQuery() instanceof TableQuery table) && isEmpty(table.getTable().getName()))
                    || ((physicalCube.getQuery() instanceof SqlSelectQuery view) && isEmpty(view.getAlias()))) {
                    String msg = String.format(FACT_NAME_MUST_BE_SET, orNotSet(cube.getName()));
                    results.add(new VerificationResultR(CUBE, msg, ERROR,
                        Cause.SCHEMA));
                }

                //Measure
                if (physicalCube.getMeasureGroups() == null || physicalCube.getMeasureGroups()
                    .isEmpty()) {
                    String msg = String.format(CUBE_WITH_NAME_MUST_CONTAIN, orNotSet(cube.getName()), MEASURE);
                    results.add(new VerificationResultR(MEASURE, msg, ERROR,
                        Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkMeasure(BaseMeasure measure, Cube cube) {
        super.checkMeasure(measure, cube);
        if (measure != null) {
            if (isEmpty(measure.getName())) {
                String msg = String.format(MEASURE_NAME_MUST_BE_SET, orNotSet(cube.getName()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));

            }
            if (measure instanceof ColumnBaseMeasure cmm) {
                if (cmm.getColumn() == null) {
                    String msg = String.format(MEASURE_COLUMN_MUST_BE_SET, orNotSet(cube.getName()));
                    results.add(new VerificationResultR(MEASURE, msg, ERROR,
                        Cause.SCHEMA));

                }
            }
            if (measure instanceof SQLExpressionBaseMeasure semm) {
                if (semm.getColumn() == null) {
                    String msg = String.format(MEASURE_COLUMN_MUST_BE_SET, orNotSet(cube.getName()));
                    results.add(new VerificationResultR(MEASURE, msg, ERROR,
                        Cause.SCHEMA));

                }
            }
        }
    }

    @Override
    protected void checkKpiPhysicalCube(Kpi kpi, PhysicalCube cube) {
        super.checkKpiPhysicalCube(kpi, cube);
        List<String> measureNames = cube.getMeasureGroups() != null ?
            cube.getMeasureGroups().stream().map(MeasureGroup::getMeasures).flatMap(Collection::stream).map(BaseMeasure::getName).toList() : List.of();
        List<String> calculatedMemberNames = cube.getCalculatedMembers() != null ?
            cube.getCalculatedMembers().stream().map(CalculatedMember::getName).toList() : List.of();
        checkKpi(kpi, cube.getName(), measureNames, calculatedMemberNames);
    }

    @Override
    protected void checkKpiPrentRing(List<? extends Kpi> list, Cube cube) {
        for (Kpi kpi : list) {
            if (kpi.getParentKpi() != null) {
                if (hasRing(kpi)) {
                    String msg = String.format(KPIS_FROM_CUBE_WITH_NAME_HAS_RING_PARENT, orNotSet(cube.getName()));
                    results.add(new VerificationResultR(KPI, msg, WARNING,
                            Cause.SCHEMA));
                    break;
                }
            }
        }
    }

    @Override
    protected void checkKpiVirtualCube(Kpi kpi, VirtualCube cube) {
        super.checkKpiVirtualCube(kpi, cube);
        List<String> measureNames = cube.getReferencedMeasures() != null ?
            cube.getReferencedMeasures().stream().map(BaseMeasure::getName).toList() : List.of();
        List<String> calculatedMemberNames = cube.getCalculatedMembers() != null ?
            cube.getCalculatedMembers().stream().map(CalculatedMember::getName).toList() : List.of();
        checkKpi(kpi, cube.getName(), measureNames, calculatedMemberNames);
    }

    private void checkKpi(Kpi kpi, String cubeName, List<String> measureNames, List<String> calculatedMemberNames) {
        if (kpi != null) {
            if (isEmpty(kpi.getName())) {
                String msg = String.format(KPI_NAME_MUST_BE_SET, orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));

            }

            if (isEmpty(kpi.getValue())) {
                String msg = String.format(KPI_VALUE_MUST_BE_SET, orNotSet(kpi.getName()), orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
            } else {
                checkMeasureCalculationName(kpi.getValue(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "Value");
            }

            if (!isEmpty(kpi.getGoal())) {
                checkMeasureCalculationName(kpi.getGoal(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "Goal");
            }
            if (!isEmpty(kpi.getStatus())) {
                checkMeasureCalculationName(kpi.getStatus(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "Status");
            }

            if (!isEmpty(kpi.getTrend())) {
                checkMeasureCalculationName(kpi.getTrend(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "Trend");
            }

            if (!isEmpty(kpi.getWeight())) {
                checkMeasureCalculationName(kpi.getWeight(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "Weight");
            }

            if (!isEmpty(kpi.getCurrentTimeMember())) {
                checkMeasureCalculationName(kpi.getCurrentTimeMember(), cubeName, measureNames, calculatedMemberNames, kpi.getName(), "CurrentTimeMember");
            }

        }
    }

    private void checkMeasureCalculationName(String value, String cubeName, List<String> measureNames, List<String> calculatedMemberNames, String kpiName, String paramName) {
        String[] values = value.split("\\.");
        if (values.length != 2) {
            String msg = String.format(KPI_PARAM_WRONG, paramName, orNotSet(kpiName), orNotSet(cubeName));
            results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
        } else {
            if (!"[Measures]".equals(values[0])) {
                String msg = String.format(KPI_PARAM_MUST_START_MEASURE, paramName, orNotSet(kpiName), orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
            }
            if (!(values[1].startsWith("[") && values[1].endsWith("]"))) {
                String msg = String.format(KPI_PARAM_WRONG, paramName, orNotSet(kpiName), orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
            } else {
                String mesName = values[1].substring(1, values[1].length() - 1);
                if (!(measureNames.stream().anyMatch(m -> mesName.equals(m))
                    || calculatedMemberNames.stream().anyMatch(m -> mesName.equals(m)))) {
                    String msg = String.format(MEASURE_WITH_NAME_FOR_PARAM_FOR_KPI_FOR_CUBE, mesName, paramName, orNotSet(kpiName), orNotSet(cubeName));
                    results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
                }
            }
        }

    }

    @Override
    protected void checkMemberFormatter(MemberFormatter elementFormatter) {
        super.checkMemberFormatter(elementFormatter);
        if (elementFormatter != null) {
            if (isEmpty(elementFormatter.getRef())) {
                results.add(new VerificationResultR(ELEMENT_FORMATTER,
                    FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, ERROR, Cause.SCHEMA));
            }
        }
    }

    protected void checkElementFormatterClass(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            String msg = String.format(FORMATTER_CLASS_NAME_NOT_FOUND, orNotSet(className));
            results.add(new VerificationResultR(ELEMENT_FORMATTER,
                msg, ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkDimensionConnector(DimensionConnector dimensionConnector, Cube cube, Catalog schema) {
        super.checkDimensionConnector(dimensionConnector, cube, schema);

        if (cube != null) {
            if (isEmpty(dimensionConnector.getOverrideDimensionName()) && dimensionConnector.getDimension() != null && dimensionConnector.getDimension().getName() != null) {
                long countWithSameName = getCubeDimensionConnectors(cube).stream().filter(dc ->
                (dimensionConnector.getDimension().getName().equals(dc.getOverrideDimensionName())) ||
                (dc.getOverrideDimensionName() == null && dc.getDimension() != null && dimensionConnector.getDimension().getName().equals(dc.getDimension().getName()))).count();
                if (countWithSameName > 1) {
                    String msg = String.format(DIMENSION_CONNECTOR_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES_IN_CUBE, dimensionConnector.getDimension().getName(), orNotSet(cube.getName()));
                    results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                }
            } else {
                List<DimensionConnector> cubeDimensionConnectors = getCubeDimensionConnectors(cube);
                if (cubeDimensionConnectors != null) {
                    long countWithSameName = cubeDimensionConnectors.stream().filter(dc -> dc.getOverrideDimensionName().equals(dimensionConnector.getOverrideDimensionName())).count();
                    if (countWithSameName > 1) {
                        String msg = String.format(DIMENSION_CONNECTOR_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES_IN_CUBE, dimensionConnector.getOverrideDimensionName(), orNotSet(cube.getName()));
                        results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }

            if (dimensionConnector.getDimension() == null) {
                String msg = String.format(DIMENSION_MUST_BE_SET_FOR_DIMENSION_CONNECTOR_WITH_NAME, orNotSet(dimensionConnector.getOverrideDimensionName()));
                results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
            }
        } else {
            //virtual cube dimension

            if (dimensionConnector.getPhysicalCube() == null) {
                results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, VIRTUAL_CUBE_DIMENSION_CUBE_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            } else {
                Optional<? extends Cube> oCube = schema.getCubes().stream().filter(c -> dimensionConnector.getPhysicalCube().equals(c)).findFirst();
                if (!oCube.isPresent()) {
                    String msg = String.format(VIRTUAL_CUBE_DIMENSION_CUBE_NAME_IS_WRONG_CUBE_ABSENT_IN_SCHEMA, dimensionConnector.getPhysicalCube().getName(), dimensionConnector.getPhysicalCube().getName());
                    results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, msg, ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkVirtualCubeMeasure(BaseMeasure virtualCubeMeasure, VirtualCube vCube, Catalog schema) {
        super.checkVirtualCubeMeasure(virtualCubeMeasure, vCube, schema);
        if (virtualCubeMeasure != null) {
            Optional<? extends Cube> oCube = schema.getCubes().stream().filter(c -> (c instanceof PhysicalCube pCube && pCube.getMeasureGroups() != null
                && pCube.getMeasureGroups().stream().anyMatch(mg -> mg.getMeasures().stream().anyMatch(m -> m.equals(virtualCubeMeasure))))).findFirst();
            if (!oCube.isPresent()) {
                results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, VIRTUAL_CUBE_MEASURE_IS_WRONG_CUBE_ABSENT_IN_SCHEMA, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkHierarchy(Hierarchy hierarchy, Dimension cubeDimension, Cube cube) {
        super.checkHierarchy(hierarchy, cubeDimension, cube);

        if (hierarchy != null) {
            checkHierarchyJoin(hierarchy, cubeDimension);


            //Level
            checkHierarchyLevels(hierarchy, cubeDimension);


            // Validates against primaryKeyTable name on field when using
            // Table.
            checkHierarchyTable(hierarchy, cubeDimension);

            // Validates that the value at primaryKeyTable corresponds to
            // tables in joins.
            checkHierarchyPrimaryKeyTable(hierarchy, cubeDimension);
        }

    }

    @Override
    protected void checkJoinQuery(JoinQuery join) {
        super.checkJoinQuery(join);
        if (join != null) {
            if (join.getLeft() == null || join.getRight() == null) {
                results.add(new VerificationResultR(JOIN, JOIN_RELATION_MUST_BE_SET_LEFT_AND_RIGHT,
                    ERROR, Cause.SCHEMA));
            } else {
                if (join.getLeft().getKey() == null) {
                    results.add(new VerificationResultR(JOIN, JOIN_LEFT_KEY_MUST_BE_SET, ERROR,
                        Cause.SCHEMA));
                }
                if (join.getRight().getKey() == null) {
                    results.add(new VerificationResultR(JOIN, JOIN_RIGHT_KEY_MUST_BE_SET,
                        ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkTable(TableQuery table) {
        super.checkTable(table);
        if (table != null) {
            if (table.getTable() == null) {
                results.add(
                    new VerificationResultR(TABLE, TABLE_NAME_MUST_BE_SET, ERROR, Cause.DATABASE));
            } else {
                DatabaseSchema theSchema = table.getTable().getSchema();
                if ((theSchema == null && isSchemaRequired()) || (theSchema != null && isEmpty(theSchema.getName()) && isSchemaRequired())) {
                    results.add(
                        new VerificationResultR(TABLE, SCHEMA_MUST_BE_SET, WARNING, Cause.DATABASE));
                }
            }
        }
    }

    @Override
    protected void checkLevel(
            org.eclipse.daanse.rolap.mapping.model.Level level, Hierarchy hierarchy,
        Dimension parentDimension, Cube cube
    ) {
        super.checkLevel(level, hierarchy, parentDimension, cube);
        // Check 'column' exists in 'table' if table is specified
        // otherwise :: case of join.

        // It should exist in relation table if it is specified
        // otherwise :: case of table.

        // It should exist in fact table :: case of degenerate dimension
        // where dimension columns exist in fact table and there is no
        // separate table.

        if (level != null) {
            checkLevelType(level, parentDimension);
            // verify level's name is set
            if (isEmpty(level.getName())) {
                String msg = String.format(LEVEL_NAME_MUST_BE_SET, orNotSet(hierarchy.getName()));
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            }

            // check level's column is in fact table
            checkLevelColumn(level, hierarchy, cube);
            if (level.getMemberFormatter() != null) {
                checkMemberFormatter(level.getMemberFormatter());
            }
        }
    }

    @Override
    protected void checkMemberProperty(
        MemberProperty property, org.eclipse.daanse.rolap.mapping.model.Level level,
        Hierarchy hierarchy, Cube cube
    ) {
        super.checkMemberProperty(property, level, hierarchy, cube);
        // Check 'column' exists in 'table' if [level table] is
        // specified otherwise :: case of join.

        // It should exist in [hierarchy relation table] if it is
        // specified otherwise :: case of table.

        // It should exist in [fact table] :: case of degenerate
        // dimension where dimension columns exist in fact table and
        // there is no separate table.

        // check property's column is in table
        if (property != null) {
            Column column = property.getColumn();
            if (column == null) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_COLUMN_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }

            if (property.getPropertyType() == null) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_TYPE_MUST_BE_SET, WARNING, Cause.SCHEMA));
            }
        }

    }

    @Override
    protected void checkMemberPropertyFormatter(MemberPropertyFormatter elementFormatter) {
        super.checkMemberPropertyFormatter(elementFormatter);
        if (elementFormatter != null) {
            if (isEmpty(elementFormatter.getRef())) {
                results.add(new VerificationResultR(ELEMENT_FORMATTER,
                    FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkVirtualCube(VirtualCube virtCube, Catalog schema) {
        super.checkVirtualCube(virtCube, schema);
        if (virtCube != null) {
            if (isEmpty(virtCube.getName())) {
                results.add(new VerificationResultR(VIRTUAL_CUBE, VIRTUAL_CUBE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));

            }
            if (virtCube.getDimensionConnectors() == null || virtCube.getDimensionConnectors().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, orNotSet(virtCube.getName()));
                results.add(new VerificationResultR(VIRTUAL_CUBE,
                    msg, ERROR, Cause.SCHEMA));
            }
            if (virtCube.getReferencedMeasures() == null || virtCube.getReferencedMeasures().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, orNotSet(virtCube.getName()));
                results.add(new VerificationResultR(VIRTUAL_CUBE, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCalculatedMember(CalculatedMember calculatedMember) {
        super.checkCalculatedMember(calculatedMember);
        if (calculatedMember != null) {
            if (isEmpty(calculatedMember.getName())) {
                results.add(new VerificationResultR(CALCULATED_MEMBER,
                    CALCULATED_MEMBER_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
            if (calculatedMember.getHierarchy() == null) {
                String msg = String.format(HIERARCHY_MUST_BE_SET_FOR_CALCULATED_MEMBER,
                    orNotSet(calculatedMember.getName()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(calculatedMember.getFormula())) {
                String msg = String.format(FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER,
                    orNotSet(calculatedMember.getName()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkFormula(String formula) {
        super.checkFormula(formula);
        if (isEmpty(formula)) {
            results.add(
                new VerificationResultR(FORMULA, FORMULA_MUST_BE_SET, ERROR, Cause.SCHEMA));
        }
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
    @Override
    protected void checkColumn(
        Column column, String fieldName, org.eclipse.daanse.rolap.mapping.model.Level level,
        Cube cube, Hierarchy parentHierarchy
    ) {
        super.checkColumn(column, fieldName, level, cube, parentHierarchy);
        if (column != null) {

            // specified table for level's column
            Table table = level.getColumn().getTable();
            checkColumnJoin(table, parentHierarchy);
            checkColumnTable(table, parentHierarchy);
            checkColumnView(table, parentHierarchy);

            if (table == null) {
                if (parentHierarchy != null && parentHierarchy.getQuery() instanceof JoinQuery join) {
                    // relation is join, table should be specified
                    results.add(new VerificationResultR(LEVEL, TABLE_MUST_BE_SET, ERROR,
                        Cause.DATABASE));

                    checkJoinQuery(join);
                }
            } else {
                // if using Joins then gets the table name for doesColumnExist
                // validation.
                if (parentHierarchy != null && parentHierarchy.getQuery() instanceof JoinQuery join) {
                    checkJoinQuery(join);
                }
            }
        }
    }

    @Override
    protected void checkNamedSet(NamedSet namedSet) {
        super.checkNamedSet(namedSet);
        if (namedSet != null) {
            if (isEmpty(namedSet.getName())) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_NAME_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
            if (isEmpty(namedSet.getFormula())) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_FORMULA_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }


    // were add from mondrian.xml
    @Override
    protected void checkCubeUsage(CubeConnector cubeUsage) {
        super.checkCubeUsage(cubeUsage);
        if (cubeUsage != null && cubeUsage.getCube() == null) {
            results.add(new VerificationResultR(CUBE_USAGE, CUBE_USAGE_CUBE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkParentChildLink(ParentChildLink closure) {
        super.checkParentChildLink(closure);
        if (closure != null) {
            if (closure.getParentColumn() == null) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_PARENT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (closure.getChildColumn() == null) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_CHILD_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (closure.getTable() == null) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_TABLE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        super.checkCalculatedMemberProperty(calculatedMemberProperty);
        if (calculatedMemberProperty != null && isEmpty(calculatedMemberProperty.getName())) {
            results.add(new VerificationResultR(CALCULATED_MEMBER_PROPERTY, CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkSqlSelectQuery(SqlSelectQuery view) {
        super.checkSqlSelectQuery(view);
        if (view != null && isEmpty(view.getAlias())) {
            results.add(new VerificationResultR(VIEW, VIEW_ALIAS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkSQL(SqlStatement sql) {
        super.checkSQL(sql);
        if (sql != null && (sql.getDialects() == null || sql.getDialects().isEmpty())) {
            results.add(new VerificationResultR(SQL, SQL_DIALECT_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkHint(TableQueryOptimizationHint hint) {
        super.checkHint(hint);
        if (hint != null && isEmpty(hint.getType())) {
            results.add(new VerificationResultR(HINT, HINT_TYPE_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkInlineTable(InlineTable inlineTable) {
        super.checkInlineTable(inlineTable);
        if (inlineTable != null) {
            if (inlineTable.getColumns() == null || inlineTable.getColumns().isEmpty()) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_COLUMN_DEFS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (inlineTable.getRows() == null) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_ROWS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkInlineTableColumn(Column column) {
        super.checkInlineTableColumn(column);
        if (column != null) {
            if (isEmpty(column.getName())) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (column.getType() == null) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkRowValue(RowValue value) {
        super.checkRowValue(value);
        if (value != null && value.getColumn() == null) {
            results.add(new VerificationResultR(VALUE, VALUE_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationTable(AggregationTable aggTable, DatabaseSchema schema) {
        super.checkAggregationTable(aggTable, schema);
        if (aggTable != null && aggTable.getAggregationFactCount() == null) {
            results.add(new VerificationResultR(AGG_TABLE, AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationName(AggregationName aggName) {
        super.checkAggregationName(aggName);
        if (aggName != null && aggName.getName() == null) {
            results.add(new VerificationResultR(AGG_NAME, AGG_NAME_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationPattern(AggregationPattern aggPattern, DatabaseSchema schema) {
        super.checkAggregationPattern(aggPattern, schema);
        if (aggPattern != null && isEmpty(aggPattern.getPattern())) {
            results.add(new VerificationResultR(AGG_PATTERN, AGG_PATTERN_PATTERN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationColumnName(AggregationColumnName aggColumnName) {
        super.checkAggregationColumnName(aggColumnName);
        if (aggColumnName != null && aggColumnName.getColumn() == null) {
            results.add(new VerificationResultR(AGG_COLUMN_NAME, AGG_COLUMN_NAME_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationMeasureFactCount(AggregationMeasureFactCount aggMeasureFactCount) {
        super.checkAggregationMeasureFactCount(aggMeasureFactCount);
        if (aggMeasureFactCount != null && aggMeasureFactCount.getFactColumn() == null) {
            results.add(new VerificationResultR(AGG_MEASURE_FACT_COUNT, AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggregationForeignKey(AggregationForeignKey aggForeignKey) {
        super.checkAggregationForeignKey(aggForeignKey);
        if (aggForeignKey != null) {
            if (aggForeignKey.getFactColumn() == null) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggForeignKey.getAggregationColumn() == null) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggregationLevel(AggregationLevel aggLevel) {
        super.checkAggregationLevel(aggLevel);
        if (aggLevel != null) {
            if (aggLevel.getName() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggLevel.getColumn() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggregationMeasure(AggregationMeasure aggMeasure) {
        super.checkAggregationMeasure(aggMeasure);
        if (aggMeasure != null) {
            if (aggMeasure.getColumn() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggMeasure.getName() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkColumn(Column column) {
        super.checkColumn(column);
        if (column != null && column.getName() == null) {
            results.add(new VerificationResultR(COLUMN, COLUMN_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkRole(AccessRole role, Catalog schema) {
        super.checkRole(role, schema);
        if (role != null && role.getName() == null) {
            results.add(new VerificationResultR(ROLE, ROLE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkSchemaGrant(AccessCatalogGrant schemaGrant, Catalog schema) {
        super.checkSchemaGrant(schemaGrant, schema);
        if (schemaGrant != null && schemaGrant.getCatalogAccess() == null) {
            results.add(new VerificationResultR(SCHEMA_GRANT, SCHEMA_GRANT_ACCESS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkCubeGrant(AccessCubeGrant cubeGrant, Catalog schema) {
        super.checkCubeGrant(cubeGrant, schema);
        if (cubeGrant != null && cubeGrant.getCube() == null) {
            results.add(new VerificationResultR(CUBE_GRANT, CUBE_GRANT_CUBE_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
        if (cubeGrant != null && cubeGrant.getCube() != null) {
            Optional<? extends Cube> oCube = schema.getCubes().stream().filter(c -> cubeGrant.getCube().equals(c)).findFirst();
            if (!oCube.isPresent()) {
                String msg = String.format(CUBE_GRANT_CUBE_ABSENT_IN_SCHEMA, cubeGrant.getCube());
                results.add(new VerificationResultR(CUBE_GRANT, msg,
                    ERROR, Cause.SCHEMA));
            }
        }

        if (cubeGrant != null && cubeGrant.getCubeAccess() == null) {
            results.add(new VerificationResultR(CUBE_GRANT, CUBE_GRANT_ACCESS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkDimensionGrant(AccessDimensionGrant dimensionGrant) {
        super.checkDimensionGrant(dimensionGrant);
        if (dimensionGrant != null && dimensionGrant.getDimension() == null) {
            results.add(new VerificationResultR(DIMENSION_GRANT, DIMENSION_GRANT_DIMENSION_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkHierarchyGrant(AccessHierarchyGrant hierarchyGrant, Cube cube, Catalog schema) {
        super.checkHierarchyGrant(hierarchyGrant, cube, schema);
        if (hierarchyGrant != null && hierarchyGrant.getHierarchy() == null) {
            results.add(new VerificationResultR(HIERARCHY_GRANT, HIERARCHY_GRANT_HIERARCHY_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
        if (hierarchyGrant != null && hierarchyGrant.getHierarchy() != null) {
            Optional<? extends Cube> oCube = schema.getCubes().stream().filter(c -> cube.equals(c)).findFirst();
            if (oCube.isPresent()) {
                List<Dimension> dimList = getCubeDimensionConnectors(oCube.get()).stream().map(dc -> dc.getDimension()).toList();
                Optional<Dimension> oDim = dimList.stream().filter(d -> d.getHierarchies().stream().anyMatch(h -> h.equals(hierarchyGrant.getHierarchy()))).findFirst();
                if (!oDim.isPresent()) {
                    String msg = String.format(HIERARCHY_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME,
                        orNotSet(hierarchyGrant.getHierarchy().getName()), orNotSet(cube.getName()));
                    results.add(new VerificationResultR(HIERARCHY_GRANT, msg,
                        ERROR, Cause.SCHEMA));
                }
            }

        }
    }

    private String removeBrackets(String str) {
        if (str.length() > 0 && str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.length() > 1 && str.charAt(str.length() - 1) == ']') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    protected void checkMemberGrant(AccessMemberGrant memberGrant, Cube cube, Catalog schema) {
        super.checkMemberGrant(memberGrant, cube, schema);
        if (memberGrant != null) {
            if (isEmpty(memberGrant.getMember())) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_MEMBER_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                String[] ms = memberGrant.getMember().split("\\.");
                if (ms.length > 0) {
                    String hierarchy = removeBrackets(ms[0]);
                    Optional<? extends Cube> oCube = schema.getCubes().stream().filter(c -> cube.equals(c)).findFirst();
                    if (oCube.isPresent()) {
                        List<Dimension> dimList = getCubeDimensionConnectors( oCube.get()).stream().map(dc -> dc.getDimension()).toList();
                        Optional<Dimension> oDim = dimList.stream().filter(d -> d.getHierarchies().stream().anyMatch(h -> h.getName().equals(hierarchy))).findFirst();
                        if (!oDim.isPresent()) {
                            String msg = String.format(MEMBER_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME,
                                hierarchy, orNotSet(cube.getName()));
                            results.add(new VerificationResultR(MEMBER_GRANT, msg,
                                ERROR, Cause.SCHEMA));

                        }
                    }
                }
            }
            if (memberGrant.getMemberAccess() == null) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_ACCESS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkParameter(Parameter parameter) {
        super.checkParameter(parameter);
        if (parameter != null) {
            if (isEmpty(parameter.getName())) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (parameter.getDataType() == null) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAnnotation(Annotation annotation) {
        if (annotation != null) {
            super.checkAnnotation(annotation);
            if (isEmpty(annotation.getName())) {
                results.add(new VerificationResultR(ANNOTATION, ANNOTATION_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkDrillThroughAttribute(DrillThroughAttribute drillThroughAttribute) {
        super.checkDrillThroughAttribute(drillThroughAttribute);
        if (drillThroughAttribute != null && drillThroughAttribute.getDimension() == null) {
            results.add(new VerificationResultR(DRILL_THROUGH_ATTRIBUTE, DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkDrillThroughMeasure(BaseMeasure drillThroughMeasure) {
        super.checkDrillThroughMeasure(drillThroughMeasure);
        if (drillThroughMeasure != null && isEmpty(drillThroughMeasure.getName())) {
            results.add(new VerificationResultR(DRILL_THROUGH_MEASURE, DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAction(Action action) {
        super.checkAction(action);
        if (action != null && isEmpty(action.getName())) {
            results.add(new VerificationResultR(ACTION, ACTION_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkWritebackAttribute(WritebackAttribute writebackAttribute, PhysicalCube cube) {
        super.checkWritebackAttribute(writebackAttribute, cube);
        if (writebackAttribute != null) {
            if (writebackAttribute.getDimensionConnector() == null) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                if (!cube.getDimensionConnectors().stream().anyMatch(dc -> dc.getDimension().equals(writebackAttribute.getDimensionConnector()))) {
                    String msg = String.format(DIMENSION_WITH_NAME_ABSENT_IN_CUBE, orNotSet(writebackAttribute.getDimensionConnector().getOverrideDimensionName()), orNotSet(cube.getName()));
                    results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, msg,
                        ERROR, Cause.SCHEMA));
                } else {
                    if (writebackAttribute.getColumn() != null) {
                        Optional<? extends DimensionConnector> oDimensionConnector = cube.getDimensionConnectors().stream().filter(dc -> dc.equals(writebackAttribute.getDimensionConnector())).findFirst();
                        if (oDimensionConnector.isPresent()) {
                            Column foreignKey = oDimensionConnector.get().getForeignKey();
                            if (!writebackAttribute.getColumn().equals(foreignKey)) {
                                String msg = String.format(DIMENSION_WITH_NAME_DONT_HAVE_FOREIGN_KEY_IN_DIMENSION_IN_CUBE,
                                    orNotSet(writebackAttribute.getDimensionConnector().getOverrideDimensionName()), writebackAttribute.getColumn().getName(),
                                    orNotSet(writebackAttribute.getDimensionConnector().getOverrideDimensionName()), orNotSet(cube.getName()));
                                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, msg,
                                    ERROR, Cause.SCHEMA));
                            }
                        }
                    }
                }
            }
            if (writebackAttribute.getColumn() == null) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackMeasure(WritebackMeasure writebackMeasure, PhysicalCube cube) {
        super.checkWritebackMeasure(writebackMeasure, cube);
        if (writebackMeasure != null) {
            if (isEmpty(writebackMeasure.getName())) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                if (!cube.getMeasureGroups().stream().anyMatch(mg -> mg.getMeasures().stream().anyMatch(m -> m.getName().equals(writebackMeasure.getName())))) {
                    String msg = String.format(MEASURE_WITH_NAME_ABSENT_IN_CUBE, orNotSet(writebackMeasure.getName()), orNotSet(cube.getName()));
                    results.add(new VerificationResultR(WRITEBACK_MEASURE, msg,
                        ERROR, Cause.SCHEMA));
                }
            }
            if (writebackMeasure.getColumn() == null) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackTable(WritebackTable writebackTable, PhysicalCube cube) {
        super.checkWritebackTable(writebackTable, cube);
        if (writebackTable != null) {
            if (isEmpty(writebackTable.getName())) {
                results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_TABLE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                if ((writebackTable.getWritebackAttribute() == null || writebackTable.getWritebackAttribute().isEmpty())
                    && (writebackTable.getWritebackMeasure() == null || writebackTable.getWritebackMeasure().isEmpty())) {
                    results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_COLUMNS_MUST_BE_SET,
                        ERROR, Cause.SCHEMA));
                } else {
                    if (writebackTable.getWritebackAttribute() != null) {
                        for (WritebackAttribute writebackAttribute : writebackTable.getWritebackAttribute()) {
                            checkWritebackAttribute(writebackAttribute, cube);
                        }
                    }
                    if (writebackTable.getWritebackMeasure() != null) {
                        for (WritebackMeasure writebackMeasure : writebackTable.getWritebackMeasure()) {
                            checkWritebackMeasure(writebackMeasure, cube);
                        }
                    }
                }
            }
        }
    }


    private void checkHierarchyJoin(Hierarchy hierarchy, Dimension cubeDimension) {
        if (hierarchy.getQuery() instanceof JoinQuery) {
            if (hierarchy.getPrimaryKey() == null) {
                String msg = String.format(PRIMARY_KEY_MUST_BE_SET_FOR_JOIN, orNotSet(cubeDimension.getName()));
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
            } else {
                if (hierarchy.getPrimaryKey().getTable() == null) {
                    String msg = String.format(PRIMARY_KEY_TABLE_MUST_BE_SET_FOR_JOIN,
                            orNotSet(cubeDimension.getName()));
                        results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    private void checkHierarchyLevels(Hierarchy hierarchy, Dimension cubeDimension) {
        if (hierarchy instanceof ExplicitHierarchy eh) {
            List<? extends org.eclipse.daanse.rolap.mapping.model.Level> levels = eh.getLevels();
            if (levels == null || levels.isEmpty()) {
                String msg = String.format(LEVEL_MUST_BE_SET_FOR_HIERARCHY, orNotSet(cubeDimension.getName()));
                results.add(new VerificationResultR(HIERARCHY,
                        msg, ERROR, Cause.SCHEMA));
            }
        }
        if (hierarchy instanceof ParentChildHierarchy ph) {
            org.eclipse.daanse.rolap.mapping.model.Level level = ph.getLevel();
            if (level == null) {
                String msg = String.format(LEVEL_MUST_BE_SET_FOR_HIERARCHY, orNotSet(cubeDimension.getName()));
                results.add(new VerificationResultR(HIERARCHY,
                        msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    private void checkHierarchyTable(Hierarchy hierarchy, Dimension cubeDimension) {
        if (hierarchy.getQuery() instanceof TableQuery table) {
            checkTable(table);
        }
    }

    private void checkHierarchyPrimaryKeyTable(Hierarchy hierarchy, Dimension cubeDimension) {
        if (hierarchy.getPrimaryKey() != null) {
            Table primaryKeyTable = hierarchy.getPrimaryKey().getTable();
            if (primaryKeyTable != null && (hierarchy.getQuery() instanceof JoinQuery join)) {
                TreeSet<String> joinTables = new TreeSet<>();
                SchemaExplorer.getTableNamesForJoin(hierarchy.getQuery(), joinTables);
                if (!joinTables.contains(primaryKeyTable.getName())) {
                    String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN,
                            orNotSet(primaryKeyTable.getName()), orNotSet(cubeDimension.getName()));
                    results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.DATABASE));
                }
                checkJoinQuery(join);
            }

            if (primaryKeyTable != null && (hierarchy.getQuery() instanceof TableQuery theTable)) {
                String compareTo = (theTable.getAlias() != null && theTable.getAlias()
                        .trim()
                        .length() > 0) ? theTable.getAlias() : theTable.getTable().getName();
                if (!primaryKeyTable.getName().equals(compareTo)) {
                    String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION,
                            orNotSet(cubeDimension.getName()));
                    results.add(new VerificationResultR(HIERARCHY,
                            msg, ERROR,
                            Cause.DATABASE));
                }
                checkTable(theTable);
            }
        }
    }

    private void checkLevelType(org.eclipse.daanse.rolap.mapping.model.Level level, Dimension dimension) {
        if (level.getType() != null && dimension != null) {
            // Empty leveltype is treated as default value of "Regular""
            // which is ok with standard/time dimension.
            if (dimension instanceof StandardDimension && level.getType() != null
                && !level.getType().equals(LevelDefinition.REGULAR)) {
                // If dimension type is 'standard' then leveltype
                // should be 'regular'
                String msg = String.format(LEVEL_LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_TIME_DIMENSION,
                    level.getType()
                        .getValue());
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));

            } else if (dimension instanceof TimeDimension && level.getType() != null && (level.getType()
                .equals(LevelDefinition.REGULAR))) {
                // If dimension type is 'time' then leveltype value
                // could be 'timeyears', 'timedays' etc'
                String msg = String.format(LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_STANDARD_DIMENSION,
                    level.getType()
                        .getValue());
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    private void checkLevelColumn(org.eclipse.daanse.rolap.mapping.model.Level level, Hierarchy hierarchy, Cube cube) {
        Column column = level.getColumn();
        if (column == null) {
            if (level.getMemberProperties() == null || level.getMemberProperties().isEmpty()) {
                String msg = String.format(LEVEL_COLUMN_MUST_BE_SET, orNotSet(hierarchy.getName()));
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            } else {
                level.getMemberProperties()
                    .forEach(p -> checkMemberProperty(p, level, hierarchy, cube));
            }
        } else {
            // Enforces validation for all column types against invalid
            // value.
            try {
                for (String element : DEF_LEVEL) {
                    Method method = level.getClass()
                        .getDeclaredMethod(element);
                    column = (Column) method.invoke(level);
                    checkColumn(column, element, level, cube, hierarchy);
                }
            } catch (Exception ex) {
                LOGGER.error("Validation", ex);
            }
        }
    }

    private void checkColumnJoin(Table table, Hierarchy parentHierarchy) {
        // If table has been changed in join then sets the table value
        // to null to cause "tableMustBeSet" validation fail.
        if (table != null && parentHierarchy != null
            && parentHierarchy.getQuery() instanceof JoinQuery) {
            TreeSet<String> joinTables = new TreeSet<>();
            SchemaExplorer.getTableNamesForJoin(parentHierarchy.getQuery(), joinTables);
            if (!joinTables.contains(table.getName())) {

                results.add(new VerificationResultR(LEVEL,
                    TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN, ERROR, Cause.SCHEMA));
            }
        }
    }

    private void checkColumnTable(Table table, Hierarchy parentHierarchy) {
        if (table != null && parentHierarchy != null
            && parentHierarchy.getQuery() instanceof TableQuery parentTable) {
            TableQuery theTable = parentTable;
            String compareTo = (theTable.getAlias() != null && theTable.getAlias()
                .trim()
                .length() > 0) ? theTable.getAlias() : theTable.getTable().getName();
            if (!compareTo.equals(table.getName())) {
                results.add(new VerificationResultR(LEVEL,
                    TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION, ERROR, Cause.SCHEMA));
            }
            checkTable(parentTable);
        }
    }

    private void checkColumnView(Table table, Hierarchy parentHierarchy) {
        if (table != null && parentHierarchy != null
            && parentHierarchy.getQuery() instanceof SqlSelectQuery) {
            results.add(new VerificationResultR(LEVEL,
                TABLE_FOR_COLUMN_CANNOT_BE_SET_IN_VIEW, ERROR, Cause.SCHEMA));
        }
    }

    private static List<DimensionConnector> getCubeDimensionConnectors(
        Cube cube) {

        if(cube instanceof PhysicalCube pcube) {
            return pcube.getDimensionConnectors();
        }
if(cube instanceof VirtualCube vcube) {
            return vcube.getDimensionConnectors();
        }
        return null;
    }
}
