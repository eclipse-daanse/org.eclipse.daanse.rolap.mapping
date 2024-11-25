package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Cause;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.VerificationResultR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MandantoriesSchemaWalker extends AbstractSchemaWalker {

    private static final Logger LOGGER = LoggerFactory.getLogger(MandantoriesSchemaWalker.class);
    private static final String[] DEF_LEVEL = {"column", "nameColumn", "parentColumn", "ordinalColumn", "captionColumn"};
    private static final Set<String> aggregation = Set.of("sum", "count", "min", "max", "avg", "distinct-count");

    public MandantoriesSchemaWalker(MandantoriesVerifierConfig config) {
    }

    @Override
    public List<VerificationResult> checkSchema(SchemaMapping schema) {
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
    protected void checkCube(MappingCube cube, MappingSchema schema) {
        super.checkCube(cube, schema);
        if (cube != null) {
            if (isEmpty(cube.name())) {
                results.add(new VerificationResultR(CUBE, CUBE_NAME_MUST_SET, Level.ERROR,
                    Cause.SCHEMA));
            }

            if (cube.fact() == null
                || ((cube.fact() instanceof MappingTableQuery table) && isEmpty(table.getName()))
                || ((cube.fact() instanceof MappingViewQuery view) && isEmpty(view.getAlias()))) {
                String msg = String.format(FACT_NAME_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(CUBE, msg, ERROR,
                    Cause.SCHEMA));
            }

            //Measure
            if (cube.measures() == null || cube.measures()
                .isEmpty()) {
                String msg = String.format(CUBE_WITH_NAME_MUST_CONTAIN, orNotSet(cube.name()), MEASURE);
                results.add(new VerificationResultR(MEASURE, msg, ERROR,
                    Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkMeasure(MappingMeasure measure, MappingCube cube) {
        super.checkMeasure(measure, cube);
        if (measure != null) {
            if (isEmpty(measure.name())) {
                String msg = String.format(MEASURE_NAME_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));

            }
            if (isEmpty(measure.aggregator())) {
                String msg = String.format(MEASURE_AGGREGATOR_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
            }

            //ExpressionView
            if (measure.measureExpression() != null) {
                // Measure expressions are OK
            } else if (isEmpty(measure.column())) {
                String msg = String.format(MEASURE_COLUMN_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(MEASURE, msg, ERROR,
                    Cause.SCHEMA));

            }
        }
    }

    @Override
    protected void checkKpi(MappingKpi kpi, MappingCube cube) {
        super.checkKpi(kpi, cube);
        List<String> measureNames = cube.measures() != null ?
            cube.measures().stream().map(MappingMeasure::name).toList() : List.of();
        List<String> calculatedMemberNames = cube.calculatedMembers() != null ?
            cube.calculatedMembers().stream().map(MappingCalculatedMember::name).toList() : List.of();
        checkKpi(kpi, cube.name(), measureNames, calculatedMemberNames);
    }

    @Override
    protected void checkKpi(MappingKpi kpi, MappingVirtualCube cube) {
        super.checkKpi(kpi, cube);
        List<String> measureNames = cube.virtualCubeMeasures() != null ?
            cube.virtualCubeMeasures().stream().map(MappingVirtualCubeMeasure::name).toList() : List.of();
        List<String> calculatedMemberNames = cube.calculatedMembers() != null ?
            cube.calculatedMembers().stream().map(MappingCalculatedMember::name).toList() : List.of();
        checkKpi(kpi, cube.name(), measureNames, calculatedMemberNames);
    }

    private void checkKpi(MappingKpi kpi, String cubeName, List<String> measureNames, List<String> calculatedMemberNames) {
        if (kpi != null) {
            if (isEmpty(kpi.name())) {
                String msg = String.format(KPI_NAME_MUST_BE_SET, orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));

            }

            if (isEmpty(kpi.value())) {
                String msg = String.format(KPI_VALUE_MUST_BE_SET, orNotSet(kpi.name()), orNotSet(cubeName));
                results.add(new VerificationResultR(KPI, msg, ERROR, Cause.SCHEMA));
            } else {
                checkMeasureCalculationName(kpi.value(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "Value");
            }

            if (!isEmpty(kpi.goal())) {
                checkMeasureCalculationName(kpi.goal(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "Goal");
            }
            if (!isEmpty(kpi.status())) {
                checkMeasureCalculationName(kpi.status(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "Status");
            }

            if (!isEmpty(kpi.trend())) {
                checkMeasureCalculationName(kpi.trend(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "Trend");
            }

            if (!isEmpty(kpi.weight())) {
                checkMeasureCalculationName(kpi.weight(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "Weight");
            }

            if (!isEmpty(kpi.currentTimeMember())) {
                checkMeasureCalculationName(kpi.currentTimeMember(), cubeName, measureNames, calculatedMemberNames, kpi.name(), "CurrentTimeMember");
            }

        }
    }

    private void checkMeasureCalculationName(String value, String cubeName, List<String> measureNames,  List<String> calculatedMemberNames, String kpiName, String paramName) {
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
    protected void checkElementFormatter(MappingElementFormatter elementFormatter) {
        super.checkElementFormatter(elementFormatter);
        if (elementFormatter != null) {
            if (isEmpty(elementFormatter.className()) && elementFormatter.script() == null) {
            	results.add(new VerificationResultR(ELEMENT_FORMATTER,
            			FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, ERROR, Cause.SCHEMA));
            } else {
            	if (!isEmpty(elementFormatter.className())) {
            		checkElementFormatterClass(elementFormatter.className());
            	}
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
    protected void checkCubeDimension(MappingCubeDimension cubeDimension, MappingCube cube, MappingSchema schema) {
        super.checkCubeDimension(cubeDimension, cube, schema);

        if (cube != null) {
            if (isEmpty(cubeDimension.name())) {
                String msg = String.format(CUBE_DIMENSION_NAME_MUST_BE_SET, orNotSet(cube.name()));
                results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
            } else {
                if (cube.dimensionUsageOrDimensions() != null) {
                    long countWithSameName = cube.dimensionUsageOrDimensions().stream().filter(d ->  cubeDimension.name().equals(d.name())).count();
                    if (countWithSameName > 1) {
                        String msg = String.format(CUBE_DIMENSION_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES_IN_CUBE, cubeDimension.name(), orNotSet(cube.name()));
                        results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }

            if (cubeDimension instanceof MappingDimensionUsage dimensionUsage) {
                if (isEmpty(dimensionUsage.source())) {
                    String msg = String.format(SOURCE_MUST_BE_SET, orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                } else {
                    //check that share dimension exist
                    Optional<MappingPrivateDimension> oDim = schema.dimensions().stream().filter(d -> dimensionUsage.source().equals(d.name())).findFirst();
                    if (!oDim.isPresent()) {
                        String msg = String.format(SCHEMA_DIMENSION_WITH_NAME_ABSENT_IN_SCHEMA_BUT_USED_IN_CUBE_WITH_NAME, dimensionUsage.source(), orNotSet(cube.name()));
                        results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }
        }
        else {
            //schema dimension
            if (isEmpty(cubeDimension.name())) {
                results.add(new VerificationResultR(DIMENSIONS, SCHEMA_DIMENSION_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            } else {
                if (schema.dimensions() != null) {
                    long countWithSameName = schema.dimensions().stream().filter(d ->  cubeDimension.name().equals(d.name())).count();
                    if (countWithSameName > 1) {
                        String msg = String.format(SCHEMA_DIMENSION_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES, cubeDimension.name());
                        results.add(new VerificationResultR(DIMENSIONS, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }
            //virtual cube dimension
            if (cubeDimension instanceof MappingVirtualCubeDimension virtualCubeDimension) {
                if (virtualCubeDimension.cubeName() == null) {
                    results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, VIRTUAL_CUBE_DIMENSION_CUBE_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
                } else {
                    Optional<MappingCube> oCube = schema.cubes().stream().filter(c -> virtualCubeDimension.cubeName().equals(c.name())).findFirst();
                    if (!oCube.isPresent()) {
                        String msg = String.format(VIRTUAL_CUBE_DIMENSION_CUBE_NAME_IS_WRONG_CUBE_ABSENT_IN_SCHEMA, cubeDimension.name(), cubeDimension.name());
                        results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, msg, ERROR, Cause.SCHEMA));
                    }
                }
            }
        }
    }

    @Override
    protected void checkVirtualCubeMeasure(MappingVirtualCubeMeasure virtualCubeMeasure, MappingVirtualCube vCube, MappingSchema schema) {
        super.checkVirtualCubeMeasure(virtualCubeMeasure, vCube, schema);
        if (virtualCubeMeasure != null) {
            if (virtualCubeMeasure.cubeName() == null) {
                results.add(new VerificationResultR(VIRTUAL_CUBE_MEASURE, VIRTUAL_CUBE_MEASURE_CUBE_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            } else {
                Optional<MappingCube> oCube = schema.cubes().stream().filter(c -> virtualCubeMeasure.cubeName().equals(c.name())).findFirst();
                if (!oCube.isPresent()) {
                    String msg = String.format(VIRTUAL_CUBE_MEASURE_CUBE_NAME_IS_WRONG_CUBE_ABSENT_IN_SCHEMA, virtualCubeMeasure.cubeName(), virtualCubeMeasure.cubeName());
                    results.add(new VerificationResultR(VIRTUAL_CUBE_DIMENSIONS, msg, ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkHierarchy(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension, MappingCube cube) {
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
    protected void checkJoin(MappingJoinQuery join) {
        super.checkJoin(join);
        if (join != null) {
            if (join.left() == null || join.right() == null) {
                results.add(new VerificationResultR(JOIN, JOIN_RELATION_MUST_BE_SET_LEFT_AND_RIGHT,
                    ERROR, Cause.SCHEMA));
            } else {
                if (isEmpty(join.left().getKey())) {
                    results.add(new VerificationResultR(JOIN, JOIN_LEFT_KEY_MUST_BE_SET, ERROR,
                        Cause.SCHEMA));
                }
                if (isEmpty(join.right().getKey())) {
                    results.add(new VerificationResultR(JOIN, JOIN_RIGHT_KEY_MUST_BE_SET,
                        ERROR, Cause.SCHEMA));
                }
            }
        }
    }

    @Override
    protected void checkTable(MappingTableQuery table) {
        super.checkTable(table);
        if (table != null) {
            if (isEmpty(table.getName())) {
                results.add(
                    new VerificationResultR(TABLE, TABLE_NAME_MUST_BE_SET, ERROR, Cause.DATABASE));
            }

            String theSchema = table.getSchema();
            if (isEmpty(theSchema) && isSchemaRequired()) {
                results.add(
                    new VerificationResultR(TABLE, SCHEMA_MUST_BE_SET, WARNING, Cause.DATABASE));
            }
        }
    }

    @Override
    protected void checkLevel(
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level, MappingHierarchy hierarchy,
        MappingPrivateDimension parentDimension, MappingCube cube
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
            if (isEmpty(level.name())) {
                String msg = String.format(LEVEL_NAME_MUST_BE_SET, orNotSet(hierarchy.name()));
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            }

            // check level's column is in fact table
            checkLevelColumn(level, hierarchy, cube);
            if (level.memberFormatter() != null) {
                checkElementFormatter(level.memberFormatter());
            }
        }
    }

    @Override
    protected void checkProperty(
        MappingProperty property, org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingHierarchy hierarchy, MappingCube cube
    ) {
        super.checkProperty(property, level, hierarchy, cube);
        // Check 'column' exists in 'table' if [level table] is
        // specified otherwise :: case of join.

        // It should exist in [hierarchy relation table] if it is
        // specified otherwise :: case of table.

        // It should exist in [fact table] :: case of degenerate
        // dimension where dimension columns exist in fact table and
        // there is no separate table.

        // check property's column is in table
        if (property != null) {
            String column = property.column();
            if (isEmpty(column)) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_COLUMN_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }

            if (property.type() == null) {
                results.add(new VerificationResultR(PROPERTY, PROPERTY_TYPE_MUST_BE_SET, WARNING, Cause.SCHEMA));
            }
        }

    }

    @Override
    protected void checkVirtualCube(MappingVirtualCube virtCube, MappingSchema schema) {
        super.checkVirtualCube(virtCube, schema);
        if (virtCube != null) {
            if (isEmpty(virtCube.name())) {
                results.add(new VerificationResultR(VIRTUAL_CUBE, VIRTUAL_CUBE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));

            }
            if (virtCube.virtualCubeDimensions() == null || virtCube.virtualCubeDimensions().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS, orNotSet(virtCube.name()));
                results.add(new VerificationResultR(VIRTUAL_CUBE,
                    msg, ERROR, Cause.SCHEMA));
            }
            if (virtCube.virtualCubeMeasures() == null || virtCube.virtualCubeMeasures().isEmpty()) {
                String msg = String.format(VIRTUAL_CUBE_MUST_CONTAIN_MEASURES, orNotSet(virtCube.name()));
                results.add(new VerificationResultR(VIRTUAL_CUBE, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkCalculatedMember(MappingCalculatedMember calculatedMember) {
        super.checkCalculatedMember(calculatedMember);
        if (calculatedMember != null) {
            if (isEmpty(calculatedMember.name())) {
                results.add(new VerificationResultR(CALCULATED_MEMBER,
                    CALCULATED_MEMBER_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(calculatedMember.dimension())) {
                String msg = String.format(DIMENSION_MUST_BE_SET_FOR_CALCULATED_MEMBER,
                    orNotSet(calculatedMember.name()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(calculatedMember.formula()) && calculatedMember.formulaElement() == null) {
                String msg = String.format(FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER,
                    orNotSet(calculatedMember.name()));
                results.add(new VerificationResultR(CALCULATED_MEMBER, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkFormula(MappingFormula formula) {
        super.checkFormula(formula);
        if (formula != null && isEmpty(formula.cdata())) {
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
        String column, String fieldName, org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingCube cube, MappingHierarchy parentHierarchy
    ) {
        super.checkColumn(column, fieldName, level, cube, parentHierarchy);
        if (!isEmpty(column)) {

            // specified table for level's column
            String table = level.table();
            checkColumnJoin(table, parentHierarchy);
            checkColumnTable(table, parentHierarchy);
            checkColumnView(table, parentHierarchy);

            if (isEmpty(table)) {
                if (parentHierarchy != null && parentHierarchy.relation() instanceof MappingJoinQuery join) {
                    // relation is join, table should be specified
                    results.add(new VerificationResultR(LEVEL, TABLE_MUST_BE_SET, ERROR,
                        Cause.DATABASE));

                    checkJoin(join);
                }
            } else {
                // if using Joins then gets the table name for doesColumnExist
                // validation.
                if (parentHierarchy != null && parentHierarchy.relation() instanceof MappingJoinQuery join) {
                    checkJoin(join);
                }
            }
        }
    }

    @Override
    protected void checkNamedSet(MappingNamedSet namedSet) {
        super.checkNamedSet(namedSet);
        if (namedSet != null) {
            if (isEmpty(namedSet.name())) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_NAME_MUST_BE_SET, ERROR,
                    Cause.SCHEMA));
            }
            if (isEmpty(namedSet.formula()) && namedSet.formulaElement() == null) {
                results.add(new VerificationResultR(NAMED_SET, NAMED_SET_FORMULA_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    /**
     * @return @deprecated
     */
    @Deprecated(since="new version", forRemoval=true)
    @Override
    protected void checkUserDefinedFunction(MappingUserDefinedFunction udf) {
        super.checkUserDefinedFunction(udf);
        if (udf != null) {
            if (isEmpty(udf.name())) {
                results.add(new VerificationResultR(USER_DEFINED_FUNCTION,
                    USER_DEFINED_FUNCTION_NAME_MUST_BE_SET, ERROR, Cause.SCHEMA));
            }
            if (isEmpty(udf.className()) && udf.script() == null) {
                String msg = String.format(EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED, orNotSet(udf.name()));
                results.add(new VerificationResultR(USER_DEFINED_FUNCTION, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    // were add from mondrian.xml
    @Override
    protected void checkCubeUsage(MappingCubeUsage cubeUsage) {
        super.checkCubeUsage(cubeUsage);
        if (cubeUsage != null && cubeUsage.cubeName() == null) {
            results.add(new VerificationResultR(CUBE_USAGE, CUBE_USAGE_CUBE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkClosure(MappingClosure closure) {
        super.checkClosure(closure);
        if (closure != null) {
            if (isEmpty(closure.parentColumn())) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_PARENT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(closure.childColumn())) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_CHILD_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (closure.table() == null) {
                results.add(new VerificationResultR(CLOSURE, CLOSURE_TABLE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkMeasureAggregation(MappingMeasure measure, MappingCube cube) {
        super.checkMeasureAggregation(measure, cube);
        if (measure != null && !isEmpty(measure.aggregator())) {
            String aggregatorName = measure.aggregator();
            Optional<String> op = aggregation.stream().filter(it -> aggregatorName.equals(it)).findFirst();
            if (!op.isPresent()) {
                String chString = aggregation.stream().collect(
                    Collectors.joining(", "));
                String msg = String.format(WRONG_MEASURE_AGGREGATOR_FOR_MEASURE_IN_CUBE, aggregatorName, measure.name(), cube.name());
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
            }
        }
    }


    @Override
    protected void checkCalculatedMemberProperty(MappingCalculatedMemberProperty calculatedMemberProperty) {
        super.checkCalculatedMemberProperty(calculatedMemberProperty);
        if (calculatedMemberProperty != null && isEmpty(calculatedMemberProperty.name())) {
            results.add(new VerificationResultR(CALCULATED_MEMBER_PROPERTY, CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkView(MappingViewQuery view) {
        super.checkView(view);
        if (view != null && isEmpty(view.getAlias())) {
            results.add(new VerificationResultR(VIEW, VIEW_ALIAS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkSQL(MappingSQL sql) {
        super.checkSQL(sql);
        if (sql != null && (sql.dialects() == null || sql.dialects().isEmpty())) {
            results.add(new VerificationResultR(SQL, SQL_DIALECT_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkHint(MappingTableQueryOptimisationHint hint) {
        super.checkHint(hint);
        if (hint != null && isEmpty(hint.type())) {
            results.add(new VerificationResultR(HINT, HINT_TYPE_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkInlineTable(MappingInlineTableQuery inlineTable) {
        super.checkInlineTable(inlineTable);
        if (inlineTable != null) {
            if (inlineTable.columnDefs() == null) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_COLUMN_DEFS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (inlineTable.rows() == null) {
                results.add(new VerificationResultR(INLINE_TABLE, INLINE_TABLE_ROWS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkColumnDef(MappingInlineTableColumnDefinition columnDef) {
        super.checkColumnDef(columnDef);
        if (columnDef != null) {
            if (isEmpty(columnDef.name())) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (columnDef.type() == null) {
                results.add(new VerificationResultR(COLUMN_DEF, COLUMN_DEF_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkValue(MappingInlineTableRowCell value) {
        super.checkValue(value);
        if (value != null && isEmpty(value.column())) {
            results.add(new VerificationResultR(VALUE, VALUE_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggTable(MappingAggTable aggTable, String schema) {
        super.checkAggTable(aggTable, schema);
        if (aggTable != null && aggTable.aggFactCount() == null) {
            results.add(new VerificationResultR(AGG_TABLE, AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggName(MappingAggName aggName) {
        super.checkAggName(aggName);
        if (aggName != null && isEmpty(aggName.name())) {
            results.add(new VerificationResultR(AGG_NAME, AGG_NAME_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggPattern(MappingAggPattern aggPattern, String schema) {
        super.checkAggPattern(aggPattern, schema);
        if (aggPattern != null && isEmpty(aggPattern.pattern())) {
            results.add(new VerificationResultR(AGG_PATTERN, AGG_PATTERN_PATTERN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggColumnName(MappingAggColumnName aggColumnName) {
        super.checkAggColumnName(aggColumnName);
        if (aggColumnName != null && isEmpty(aggColumnName.column())) {
            results.add(new VerificationResultR(AGG_COLUMN_NAME, AGG_COLUMN_NAME_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggMeasureFactCount(MappingAggMeasureFactCount aggMeasureFactCount) {
        super.checkAggMeasureFactCount(aggMeasureFactCount);
        if (aggMeasureFactCount != null && isEmpty(aggMeasureFactCount.factColumn())) {
            results.add(new VerificationResultR(AGG_MEASURE_FACT_COUNT, AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAggForeignKey(MappingAggForeignKey aggForeignKey) {
        super.checkAggForeignKey(aggForeignKey);
        if (aggForeignKey != null) {
            if (isEmpty(aggForeignKey.factColumn())) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (isEmpty(aggForeignKey.aggColumn())) {
                results.add(new VerificationResultR(AGG_FOREIGN_KEY, AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggLevel(MappingAggLevel aggLevel) {
        super.checkAggLevel(aggLevel);
        if (aggLevel != null) {
            if (aggLevel.name() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggLevel.column() == null) {
                results.add(new VerificationResultR(AGG_LEVEL, AGG_LEVEL_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAggMeasure(MappingAggMeasure aggMeasure) {
        super.checkAggMeasure(aggMeasure);
        if (aggMeasure != null) {
            if (aggMeasure.column() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (aggMeasure.name() == null) {
                results.add(new VerificationResultR(AGG_MEASURE, AGG_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkColumn(MappingColumn column) {
        super.checkColumn(column);
        if (column != null && column.getName() == null) {
            results.add(new VerificationResultR(COLUMN, COLUMN_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkRole(MappingRole role, MappingSchema schema) {
        super.checkRole(role, schema);
        if (role != null && role.name() == null) {
            results.add(new VerificationResultR(ROLE, ROLE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkSchemaGrant(MappingSchemaGrant schemaGrant, MappingSchema schema) {
        super.checkSchemaGrant(schemaGrant, schema);
        if (schemaGrant != null && schemaGrant.access() == null) {
            results.add(new VerificationResultR(SCHEMA_GRANT, SCHEMA_GRANT_ACCESS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkCubeGrant(MappingCubeGrant cubeGrant, MappingSchema schema) {
        super.checkCubeGrant(cubeGrant, schema);
        if (cubeGrant != null && isEmpty(cubeGrant.cube())) {
            results.add(new VerificationResultR(CUBE_GRANT, CUBE_GRANT_CUBE_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
        if (cubeGrant != null && !isEmpty(cubeGrant.cube())) {
            Optional<MappingCube> oCube = schema.cubes().stream().filter(c -> cubeGrant.cube().equals(c.name())).findFirst();
            if (!oCube.isPresent()) {
                String msg = String.format(CUBE_GRANT_CUBE_ABSENT_IN_SCHEMA, cubeGrant.cube());
                results.add(new VerificationResultR(CUBE_GRANT, msg,
                    ERROR, Cause.SCHEMA));
            }
        }

        if (cubeGrant != null && isEmpty(cubeGrant.access())) {
            results.add(new VerificationResultR(CUBE_GRANT, CUBE_GRANT_ACCESS_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkDimensionGrant(MappingDimensionGrant dimensionGrant) {
        super.checkDimensionGrant(dimensionGrant);
        if (dimensionGrant != null && isEmpty(dimensionGrant.dimension())) {
            results.add(new VerificationResultR(DIMENSION_GRANT, DIMENSION_GRANT_DIMENSION_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkHierarchyGrant(MappingHierarchyGrant hierarchyGrant, String cubeName, MappingSchema schema) {
        super.checkHierarchyGrant(hierarchyGrant, cubeName, schema);
        if (hierarchyGrant != null && isEmpty(hierarchyGrant.hierarchy())) {
            results.add(new VerificationResultR(HIERARCHY_GRANT, HIERARCHY_GRANT_HIERARCHY_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
        if (hierarchyGrant != null && !isEmpty(hierarchyGrant.hierarchy())) {
            String hierarchy = removeBrackets(hierarchyGrant.hierarchy());
            if (hierarchy != null) {
                Optional<MappingCube> oCube = schema.cubes().stream().filter(c -> cubeName.equals(c.name())).findFirst();
                if (oCube.isPresent()) {
                    Optional<MappingCubeDimension> oDim = oCube.get().dimensionUsageOrDimensions().stream().filter(d -> hierarchy.equals(d.name())).findFirst();
                    if (!oDim.isPresent()) {
                        String msg = String.format(HIERARCHY_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME,
                            hierarchy, orNotSet(cubeName));
                        results.add(new VerificationResultR(HIERARCHY_GRANT, msg,
                            ERROR, Cause.SCHEMA));

                    }
                }
            }
        }
    }

    private String removeBrackets(String str) {
        if (str.length() > 0 && str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.length() > 1 && str.charAt(str.length() - 1) == ']') {
            str =  str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    protected void checkMemberGrant(MappingMemberGrant memberGrant, String cubeName, MappingSchema schema) {
        super.checkMemberGrant(memberGrant, cubeName, schema);
        if (memberGrant != null) {
            if (isEmpty(memberGrant.member())) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_MEMBER_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                String[] ms = memberGrant.member().split("\\.");
                if(ms.length > 0) {
                    String hierarchy = removeBrackets(ms[0]);
                    Optional<MappingCube> oCube = schema.cubes().stream().filter(c -> cubeName.equals(c.name())).findFirst();
                    if (oCube.isPresent()) {
                        Optional<MappingCubeDimension> oDim = oCube.get().dimensionUsageOrDimensions().stream().filter(d -> hierarchy.equals(d.name())).findFirst();
                        if (!oDim.isPresent()) {
                            String msg = String.format(MEMBER_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME,
                                hierarchy, orNotSet(cubeName));
                            results.add(new VerificationResultR(MEMBER_GRANT, msg,
                                ERROR, Cause.SCHEMA));

                        }
                    }
                }
            }
            if (memberGrant.access() == null) {
                results.add(new VerificationResultR(MEMBER_GRANT, MEMBER_GRANT_ACCESS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkUnion(MappingUnion union) {
        super.checkUnion(union);
        if (union != null && union.roleUsages() == null) {
            results.add(new VerificationResultR(UNION, UNION_ROLE_USAGE_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkRoleUsage(MappingRoleUsage roleUsage) {
        super.checkRoleUsage(roleUsage);
        if (roleUsage != null && isEmpty(roleUsage.roleName())) {
            results.add(new VerificationResultR(ROLE_USAGE, ROLE_USAGE_ROLE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkParameter(MappingParameter parameter) {
        super.checkParameter(parameter);
        if (parameter != null) {
            if (isEmpty(parameter.name())) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
            if (parameter.type() == null) {
                results.add(new VerificationResultR(PARAMETER, PARAMETER_TYPE_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkAnnotation(MappingAnnotation annotation) {
        if (annotation != null) {
            super.checkAnnotation(annotation);
            if (isEmpty(annotation.name())) {
                results.add(new VerificationResultR(ANNOTATION, ANNOTATION_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkDrillThroughAttribute(MappingDrillThroughAttribute drillThroughAttribute) {

        super.checkDrillThroughAttribute(drillThroughAttribute);
        if (drillThroughAttribute != null && isEmpty(drillThroughAttribute.dimension())) {
            results.add(new VerificationResultR(DRILL_THROUGH_ATTRIBUTE, DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkDrillThroughMeasure(MappingDrillThroughMeasure drillThroughMeasure) {
        super.checkDrillThroughMeasure(drillThroughMeasure);
        if (drillThroughMeasure != null && isEmpty(drillThroughMeasure.name())) {
            results.add(new VerificationResultR(DRILL_THROUGH_MEASURE, DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAction(MappingAction action) {
        super.checkAction(action);
        if (action != null && isEmpty(action.name())) {
            results.add(new VerificationResultR(ACTION, ACTION_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkWritebackAttribute(MappingWritebackAttribute writebackAttribute, MappingCube cube) {
        super.checkWritebackAttribute(writebackAttribute, cube);
        if (writebackAttribute != null) {
            if (isEmpty(writebackAttribute.dimension())) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                if (!cube.dimensionUsageOrDimensions().stream().anyMatch(d -> d.name().equals(writebackAttribute.dimension()))) {
                    String msg = String.format(DIMENSION_WITH_NAME_ABSENT_IN_CUBE, orNotSet(writebackAttribute.dimension()), orNotSet(cube.name()));
                    results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, msg,
                        ERROR, Cause.SCHEMA));
                } else {
                    if (!isEmpty(writebackAttribute.column())) {
                        Optional<MappingCubeDimension> oDimension = cube.dimensionUsageOrDimensions().stream().filter(d -> d.name().equals(writebackAttribute.dimension())).findFirst();
                        if (oDimension.isPresent()) {
                            String foreignKey = oDimension.get().foreignKey();
                            if (!writebackAttribute.column().equals(foreignKey)) {
                                String msg = String.format(DIMENSION_WITH_NAME_DONT_HAVE_FOREIGN_KEY_IN_DIMENSION_IN_CUBE,
                                    orNotSet(writebackAttribute.dimension()), writebackAttribute.column(),
                                    orNotSet(writebackAttribute.dimension()), orNotSet(cube.name()));
                                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, msg,
                                    ERROR, Cause.SCHEMA));
                            }
                        }
                    }
                }
            }
            if (isEmpty(writebackAttribute.column())) {
                results.add(new VerificationResultR(WRITEBACK_ATTRIBUTE, WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackMeasure(MappingWritebackMeasure writebackMeasure, MappingCube cube) {
        super.checkWritebackMeasure(writebackMeasure, cube);
        if (writebackMeasure != null) {
            if (isEmpty(writebackMeasure.name())) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_NAME_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                if (!cube.measures().stream().anyMatch(m -> m.name().equals(writebackMeasure.name()))) {
                    String msg = String.format(MEASURE_WITH_NAME_ABSENT_IN_CUBE, orNotSet(writebackMeasure.name()), orNotSet(cube.name()));
                    results.add(new VerificationResultR(WRITEBACK_MEASURE, msg,
                        ERROR, Cause.SCHEMA));
                }
            }
            if (isEmpty(writebackMeasure.column())) {
                results.add(new VerificationResultR(WRITEBACK_MEASURE, WRITEBACK_MEASURE_COLUMN_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            }
        }
    }

    @Override
    protected void checkWritebackTable(MappingWritebackTable writebackTable, MappingCube cube) {
        super.checkWritebackTable(writebackTable, cube);
        if (writebackTable != null && isEmpty(writebackTable.name())) {
            results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_TABLE_NAME_MUST_BE_SET,
                ERROR, Cause.SCHEMA));
        } else {
            if (writebackTable.columns() == null) {
                results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_COLUMNS_MUST_BE_SET,
                    ERROR, Cause.SCHEMA));
            } else {
                for (MappingWritebackColumn column : writebackTable.columns()) {
                    if (isEmpty(column.column())) {
                        results.add(new VerificationResultR(WRITEBACK_TABLE, WRITEBACK_COLUMN_NAME_MUST_BE_SET,
                            ERROR, Cause.SCHEMA));
                    }
                    if (column instanceof MappingWritebackAttribute mappingWritebackAttribute) {
                        checkWritebackAttribute(mappingWritebackAttribute, cube);
                    }
                    if (column instanceof MappingWritebackMeasure mappingWritebackMeasure) {
                        checkWritebackMeasure(mappingWritebackMeasure, cube);
                    }
                }
            }
        }
    }


    private void checkHierarchyJoin(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension) {
        if (hierarchy.relation() instanceof MappingJoinQuery) {
            if (isEmpty(hierarchy.primaryKeyTable())) {
                if (isEmpty(hierarchy.primaryKey())) {
                    String msg = String.format(PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN,
                        orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(
                        HIERARCHY, msg, ERROR, Cause.SCHEMA));

                } else {
                    String msg = String.format(PRIMARY_KEY_TABLE_MUST_BE_SET_FOR_JOIN,
                        orNotSet(cubeDimension.name()));
                    results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
                }
            }
            if (isEmpty(hierarchy.primaryKey())) {
                String msg = String.format(PRIMARY_KEY_MUST_BE_SET_FOR_JOIN, orNotSet(cubeDimension.name()));
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
            }
        }
    }

    private void checkHierarchyLevels(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension) {
        List<? extends org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel> levels = hierarchy.levels();
        if (levels == null || levels.isEmpty()) {
            String msg = String.format(LEVEL_MUST_BE_SET_FOR_HIERARCHY, orNotSet(cubeDimension.name()));
            results.add(new VerificationResultR(HIERARCHY,
                msg, ERROR, Cause.SCHEMA));
        }
    }

    private void checkHierarchyTable(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension) {
        if (hierarchy.relation() instanceof MappingTableQuery table) {
            if (!isEmpty(hierarchy.primaryKeyTable())) {
                String msg = String.format(HIERARCHY_TABLE_FIELD_MUST_BE_EMPTY, orNotSet(cubeDimension.name()));
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.SCHEMA));
            }
            checkTable(table);
        }
    }

    private void checkHierarchyPrimaryKeyTable(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension) {
        String primaryKeyTable = hierarchy.primaryKeyTable();
        if (!isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof MappingJoinQuery join)) {
            TreeSet<String> joinTables = new TreeSet<>();
            SchemaExplorer.getTableNamesForJoin(hierarchy.relation(), joinTables);
            if (!joinTables.contains(primaryKeyTable)) {
                String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN,
                    orNotSet(primaryKeyTable), orNotSet(cubeDimension.name()));
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, Cause.DATABASE));
            }
            checkJoin(join);
        }

        if (!isEmpty(primaryKeyTable) && (hierarchy.relation() instanceof MappingTableQuery theTable)) {
            String compareTo = (theTable.getAlias() != null && theTable.getAlias()
                .trim()
                .length() > 0) ? theTable.getAlias() : theTable.getName();
            if (!primaryKeyTable.equals(compareTo)) {
                String msg = String.format(HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION,
                    orNotSet(cubeDimension.name()));
                results.add(new VerificationResultR(HIERARCHY,
                    msg, ERROR,
                    Cause.DATABASE));
            }
            checkTable(theTable);
        }
    }

    private void checkLevelType(org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level, MappingPrivateDimension parentDimension) {
        if (level.levelType() != null && parentDimension != null) {
            // Empty leveltype is treated as default value of "Regular""
            // which is ok with standard/time dimension.
            if ((parentDimension.type() == null || parentDimension.type()
                .equals(DimensionTypeEnum.STANDARD_DIMENSION)) && level.levelType() != null
                && (!level.levelType()
                .equals(LevelTypeEnum.REGULAR))) {
                // If dimension type is 'standard' then leveltype
                // should be 'regular'
                String msg = String.format(LEVEL_LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_TIME_DIMENSION,
                    level.levelType()
                        .getValue());
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));

            } else if (parentDimension.type() != null && (parentDimension.type()
                .equals(DimensionTypeEnum.TIME_DIMENSION)) && level.levelType() != null && (level.levelType()
                .equals(LevelTypeEnum.REGULAR))) {
                // If dimension type is 'time' then leveltype value
                // could be 'timeyears', 'timedays' etc'
                String msg = String.format(LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_STANDARD_DIMENSION,
                    level.levelType()
                        .getValue());
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            }
        }
        if (level.type() == null) {
            String msg = String.format(LEVEL_TYPE_MUST_BE_SET,
                level.name() == null ? NOT_SET : level.name());
            results.add(new VerificationResultR(LEVEL, msg, WARNING, Cause.SCHEMA));
        }
    }

    private void checkLevelColumn(org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level, MappingHierarchy hierarchy,
                                  MappingCube cube) {
        String column = level.column();
        if (isEmpty(column)) {
            if (level.properties() == null || level.properties().isEmpty()) {
                String msg = String.format(LEVEL_COLUMN_MUST_BE_SET, orNotSet(hierarchy.name()));
                results.add(new VerificationResultR(LEVEL, msg, ERROR, Cause.SCHEMA));
            } else {
                level.properties()
                    .forEach(p -> checkProperty(p, level, hierarchy, cube));
            }
        } else {
            // Enforces validation for all column types against invalid
            // value.
            try {
                for (String element : DEF_LEVEL) {
                    Method method = level.getClass()
                        .getDeclaredMethod(element);
                    column = (String) method.invoke(level);
                    checkColumn(column, element, level, cube, hierarchy);
                }
            } catch (Exception ex) {
                LOGGER.error("Validation", ex);
            }
        }
    }

    private void checkColumnJoin(String table, MappingHierarchy parentHierarchy) {
        // If table has been changed in join then sets the table value
        // to null to cause "tableMustBeSet" validation fail.
        if (!isEmpty(table) && parentHierarchy != null
            && parentHierarchy.relation() instanceof MappingJoinQuery) {
            TreeSet<String> joinTables = new TreeSet<>();
            SchemaExplorer.getTableNamesForJoin(parentHierarchy.relation(), joinTables);
            if (!joinTables.contains(table)) {

                results.add(new VerificationResultR(LEVEL,
                    TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN, ERROR, Cause.SCHEMA));
            }
        }
    }

    private void checkColumnTable(String table, MappingHierarchy parentHierarchy) {
        if (!isEmpty(table) && parentHierarchy != null
            && parentHierarchy.relation() instanceof MappingTableQuery parentTable) {
            MappingTableQuery theTable = parentTable;
            String compareTo = (theTable.getAlias() != null && theTable.getAlias()
                .trim()
                .length() > 0) ? theTable.getAlias() : theTable.getName();
            if (!table.equals(compareTo)) {
                results.add(new VerificationResultR(LEVEL,
                    TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION, ERROR, Cause.SCHEMA));
            }
            checkTable(parentTable);
        }
    }

    private void checkColumnView(String table, MappingHierarchy parentHierarchy) {
        if (!isEmpty(table) && parentHierarchy != null
            && parentHierarchy.relation() instanceof MappingViewQuery) {
            results.add(new VerificationResultR(LEVEL,
                TABLE_FOR_COLUMN_CANNOT_BE_SET_IN_VIEW, ERROR, Cause.SCHEMA));
        }
    }
}
