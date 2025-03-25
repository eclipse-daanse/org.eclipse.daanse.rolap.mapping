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

public class SchemaWalkerMessages {
    public static final String NOT_SET = "not set";
    //titles
    public static final String VIRTUAL_CUBE = "VirtualCube";
    public static final String SCHEMA = "Schema";
    public static final String CUBE = "Cube";
    public static final String DIMENSIONS = "Dimensions";
    public static final String VIRTUAL_CUBE_DIMENSIONS = "Virtual Cube Dimensions";
    public static final String VIRTUAL_CUBE_MEASURE = "Virtual Cube Measure";
    public static final String MEASURE = "Measure";
    public static final String KPI = "KPI";
    public static final String HIERARCHY = "Hierarchy";
    public static final String PROPERTY = "Property";
    public static final String CUBE_DIMENSION = "Cube Dimension";
    public static final String JOIN = "Join";
    public static final String TABLE = "Table";
    public static final String FORMULA = "Formula";
    public static final String LEVEL = "Level";
    public static final String ELEMENT_FORMATTER = "ElementFormatter";
    //titles
    public static final String WRITEBACK_TABLE = "WritebackTable";
    public static final String CUBE_USAGE = "CubeUsage";
    public static final String CLOSURE = "Closure";
    public static final String CALCULATED_MEMBER_PROPERTY = "CalculatedMemberProperty";
    public static final String VIEW = "View";
    public static final String SQL = "SQL";
    public static final String HINT = "Hint";
    public static final String INLINE_TABLE = "InlineTable";
    public static final String COLUMN_DEF = "ColumnDef";
    public static final String VALUE = "Value";
    public static final String AGG_TABLE = "AggTable";
    public static final String AGG_NAME = "AggName";
    public static final String AGG_PATTERN = "AggPattern";
    public static final String AGG_COLUMN_NAME = "AggColumnName";
    public static final String AGG_MEASURE_FACT_COUNT = "AggMeasureFactCount";
    public static final String AGG_FOREIGN_KEY = "AggForeignKey";
    public static final String AGG_LEVEL = "AggLevel";
    public static final String AGG_MEASURE = "AggMeasure";
    public static final String ROLE = "Role";
    public static final String SCHEMA_GRANT = "SchemaGrant";
    public static final String CUBE_GRANT = "CubeGrant";
    public static final String DIMENSION_GRANT = "DimensionGrant";
    public static final String HIERARCHY_GRANT = "HierarchyGrant";
    public static final String MEMBER_GRANT = "MemberGrant";
    public static final String UNION = "Union";
    public static final String ROLE_USAGE = "RoleUsage";
    public static final String PARAMETER = "Parameter";
    public static final String ANNOTATION = "Annotation";
    public static final String DRILL_THROUGH_ATTRIBUTE = "DrillThroughAttribute";
    public static final String DRILL_THROUGH_MEASURE = "DrillThroughMeasure";
    public static final String ACTION = "Action";
    public static final String COLUMN = "Column";
    public static final String NAMED_SET = "NamedSet";
    public static final String SHARED_DIMENSION = "SharedDimension";
    public static final String DRILL_THROUGH_ACTION = "DrillThroughAction";


    public static final String SCHEMA_SPACE = "schema ";
    public static final String WRITEBACK_TABLE_NAME_MUST_BE_SET = "WritebackTable name must be set";
    public static final String WRITEBACK_COLUMNS_MUST_BE_SET = "WritebackTable attrebute or measure must be set";
    public static final String WRITEBACK_COLUMN_NAME_MUST_BE_SET = "WritebackTable column name must be set";
    public static final String TABLE_NAME_MUST_BE_SET = "Table name must be set";
    public static final String JOIN_RELATION_MUST_BE_SET_LEFT_AND_RIGHT = "Join: relation must be set left and right";
    public static final String CUBE_USAGE_CUBE_NAME_MUST_BE_SET = "CubeUsage cubeName must be set";
    public static final String CLOSURE_PARENT_COLUMN_MUST_BE_SET = "Closure parentColumn must be set";
    public static final String CLOSURE_TABLE_MUST_BE_SET = "Closure table must be set";
    public static final String CLOSURE_CHILD_COLUMN_MUST_BE_SET = "Closure childColumn must be set";
    public static final String CALCULATED_MEMBER_PROPERTY_NAME_MUST_BE_SET = "CalculatedMemberProperty name must be set";
    public static final String VIEW_ALIAS_MUST_BE_SET = "View alias must be set";
    public static final String SQL_DIALECT_MUST_BE_SET = "SQL dialect must be set";
    public static final String HINT_TYPE_MUST_BE_SET = "Hint type must be set";
    public static final String INLINE_TABLE_COLUMN_DEFS_MUST_BE_SET = "InlineTable columnDefs must be set";
    public static final String INLINE_TABLE_ROWS_MUST_BE_SET = "InlineTable rows must be set";
    public static final String COLUMN_DEF_TYPE_MUST_BE_SET = "ColumnDef type must be set";
    public static final String COLUMN_DEF_NAME_MUST_BE_SET = "ColumnDef name must be set";
    public static final String VALUE_COLUMN_MUST_BE_SET = "Value column must be set";
    public static final String AGG_TABLE_AGG_FACT_COUNT_MUST_BE_SET = "AggTable aggFactCount must be set";
    public static final String AGG_NAME_NAME_MUST_BE_SET = "AggName name must be set";
    public static final String AGG_PATTERN_PATTERN_MUST_BE_SET = "AggPattern pattern must be set";
    public static final String AGG_COLUMN_NAME_COLUMN_MUST_BE_SET = "AggColumnName column must be set";
    public static final String AGG_MEASURE_FACT_COUNT_FACT_COLUMN_MUST_BE_SET = "AggMeasureFactCount factColumn must be set";
    public static final String AGG_FOREIGN_KEY_FACT_COLUMN_MUST_BE_SET = "AggForeignKey factColumn must be set";
    public static final String AGG_FOREIGN_KEY_AGG_COLUMN_MUST_BE_SET = "AggForeignKey aggColumn must be set";
    public static final String AGG_LEVEL_NAME_MUST_BE_SET = "AggLevel name must be set";
    public static final String AGG_LEVEL_COLUMN_MUST_BE_SET = "AggLevel column must be set";
    public static final String AGG_MEASURE_COLUMN_MUST_BE_SET = "AggMeasure column must be set";
    public static final String AGG_MEASURE_NAME_MUST_BE_SET = "AggMeasure name must be set";
    public static final String COLUMN_NAME_MUST_BE_SET = "Column name must be set";
    public static final String ROLE_NAME_MUST_BE_SET = "Role name must be set";
    public static final String SCHEMA_GRANT_ACCESS_MUST_BE_SET = "SchemaGrant access must be set";
    public static final String CUBE_GRANT_CUBE_MUST_BE_SET = "CubeGrant cube must be set";
    public static final String CUBE_GRANT_CUBE_ABSENT_IN_SCHEMA = "CubeGrant cube %s absent in schema";
    public static final String CUBE_GRANT_ACCESS_MUST_BE_SET = "CubeGrant access must be set";
    public static final String DIMENSION_GRANT_DIMENSION_MUST_BE_SET = "DimensionGrant dimension must be set";
    public static final String HIERARCHY_GRANT_HIERARCHY_MUST_BE_SET = "HierarchyGrant hierarchy must be set";
    public static final String HIERARCHY_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME =
        "HierarchyGrant hierarchy use %s  dimension which absent in cube with name %s";
    public static final String MEMBER_GRANT_USE_DIMENSION_WHICH_ABSENT_IN_CUBE_WITH_NAME =
        "MemberGrant member use %s dimension which absent in cube with name %s";
    public static final String MEMBER_GRANT_MEMBER_MUST_BE_SET = "MemberGrant member must be set";
    public static final String MEMBER_GRANT_ACCESS_MUST_BE_SET = "MemberGrant access must be set";
    public static final String UNION_ROLE_USAGE_MUST_BE_SET = "Union roleUsage must be set";
    public static final String ROLE_USAGE_ROLE_NAME_MUST_BE_SET = "RoleUsage roleName must be set";
    public static final String PARAMETER_TYPE_MUST_BE_SET = "Parameter type must be set";
    public static final String PARAMETER_NAME_MUST_BE_SET = "Parameter name must be set";
    public static final String ANNOTATION_NAME_MUST_BE_SET = "Annotation name must be set";
    public static final String DRILL_THROUGH_ATTRIBUTE_NAME_MUST_BE_SET = "DrillThroughAttribute name must be set";
    public static final String DRILL_THROUGH_MEASURE_NAME_MUST_BE_SET = "DrillThroughMeasure name must be set";
    public static final String ACTION_NAME_MUST_BE_SET = "Action name must be set";
    public static final String WRITEBACK_MEASURE = "WritebackMeasure";
    public static final String WRITEBACK_MEASURE_COLUMN_MUST_BE_SET = "WritebackMeasure column must be set";
    public static final String MEASURE_WITH_NAME_ABSENT_IN_CUBE = "Measure with name %s absent in cube with name %s for write back";
    public static final String DIMENSION_WITH_NAME_ABSENT_IN_CUBE = "Dimension with name %s absent in cube with name %s for write back";
    public static final String DIMENSION_WITH_NAME_DONT_HAVE_FOREIGN_KEY_IN_DIMENSION_IN_CUBE =
        "Dimension with name %s doesn't have foreign key %s in dimension %s in cube with name %s for write back";
    public static final String WRITEBACK_MEASURE_NAME_MUST_BE_SET = "WritebackMeasure name must be set";
    public static final String WRITEBACK_ATTRIBUTE = "WritebackAttribute";
    public static final String WRITEBACK_ATTRIBUTE_COLUMN_MUST_BE_SET = "WritebackAttribute column must be set";
    public static final String WRITEBACK_ATTRIBUTE_DIMENSION_MUST_BE_SET = "WritebackAttribute dimension must be set";


    public static final String FACT_NAME_MUST_BE_SET = "Fact name must be set for cube with name %s";
    public static final String CUBE_WITH_NAME_MUST_CONTAIN = "Cube with name %s must contain %s";
    public static final String MEASURE_NAME_MUST_BE_SET = "Measure name must be set for cube with name %s";
    public static final String KPI_NAME_MUST_BE_SET = "KPI name must be set for cube with name %s";
    public static final String MEASURE_AGGREGATOR_MUST_BE_SET = "Measure Aggregator must be set for cube with name %s";
    public static final String KPI_VALUE_MUST_BE_SET = "KPI value must be set for KPI with name %s for cube with name %s";
    public static final String KPI_PARAM_WRONG = "KPI %s have wrong format for KPI with name %s for cube with name %s";
    public static final String KPI_PARAM_MUST_START_MEASURE = "KPI %s should start from '[measure].' for KPI with name %s for cube with name %s";
    public static final String MEASURE_WITH_NAME_FOR_PARAM_FOR_KPI_FOR_CUBE = "Measure or calculated member %s absent for KPI %s for KPI with name %s for cube with name %s";
    public static final String PRIMARY_KEY_TABLE_AND_PRIMARY_KEY_MUST_BE_SET_FOR_JOIN =
        "Hierarchy: PrimaryKeyTable and PrimaryKey must be set for Join in dimension with name %s";
    public static final String PRIMARY_KEY_TABLE_MUST_BE_SET_FOR_JOIN =
        "Hierarchy : PrimaryKeyTable must be set for Join in dimension with name %s";
    public static final String PRIMARY_KEY_MUST_BE_SET_FOR_JOIN =
        "Hierarchy: PrimaryKey must be set for Join in dimension with name %s";
    public static final String LEVEL_MUST_BE_SET_FOR_HIERARCHY =
        "Hierarchy: At least one Level must be set for Hierarchy in dimension with name %s";
    public static final String MEASURE_COLUMN_MUST_BE_SET =
        "Measure column must be set for cube with name %s";
    public static final String FORMATTER_EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED =
        "Formatter: either a Class Name or a Script are required";
    public static final String FORMATTER_CLASS_NAME_NOT_FOUND =
        "Formatter: Class Name %s not found";
    public static final String DIMENSION_CONNECTOR_OVERRIDE_NAME_MUST_BE_SET =
        "Dimension connector override name must be set for cube with name %s";
    public static final String DIMENSION_CONNECTOR_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES_IN_CUBE =
        "Cube Dimension with name %s meets more then one time in cube with name %s";
    public static final String SCHEMA_DIMENSION_WITH_NAME_MEETS_MORE_THEN_ONE_TIMES =
        "Schema Dimension with name %s meets more then one time";
    public static final String SCHEMA_DIMENSION_WITH_NAME_ABSENT_IN_SCHEMA_BUT_USED_IN_CUBE_WITH_NAME =
        "Schema Dimension with name %s absent in schema but usedin cube with name %s";
    public static final String SCHEMA_DIMENSION_NAME_MUST_BE_SET =
        "Schema Dimension name must be set";
    public static final String VIRTUAL_CUBE_DIMENSION_CUBE_NAME_MUST_BE_SET =
        "Virtual Cube Dimension Cube name must be set";
    public static final String VIRTUAL_CUBE_MEASURE_CUBE_NAME_MUST_BE_SET =
        "Virtual Cube Measure Cube name must be set";
    public static final String VIRTUAL_CUBE_DIMENSION_CUBE_NAME_IS_WRONG_CUBE_ABSENT_IN_SCHEMA =
        "Virtual Cube Dimension Cube name %s is wrong. Cube with name %s absent in schema";
    public static final String VIRTUAL_CUBE_MEASURE_IS_WRONG_CUBE_ABSENT_IN_SCHEMA =
        "Virtual Cube Measure is wrong. Cube absent in schema";
    public static final String DIMENSION_MUST_BE_SET_FOR_DIMENSION_CONNECTOR_WITH_NAME =
        "Dimension must be set for dimension connector with name %s";
    public static final String JOIN_LEFT_KEY_MUST_BE_SET = "Join: Left key must be set";
    public static final String JOIN_RIGHT_KEY_MUST_BE_SET = "Join: Right key must be set";
    public static final String SCHEMA_MUST_BE_SET = "Table: Schema must be set";
    public static final String HIERARCHY_TABLE_FIELD_MUST_BE_EMPTY =
        "Hierarchy: Table field must be empty in dimension with name %s";
    public static final String HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN =
        "Hierarchy: Table %s does not correspond to any join in dimension with name %s";
    public static final String HIERARCHY_TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION =
        "Hierarchy: Table value does not correspond to Hierarchy Relation  in dimension with name %s";
    public static final String LEVEL_LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_TIME_DIMENSION =
        "Level: levelType %s can only be used with a TimeDimension";
    public static final String LEVEL_TYPE_S_CAN_ONLY_BE_USED_WITH_A_STANDARD_DIMENSION =
        "Level: levelType %s can only be used with a StandardDimension";
    public static final String LEVEL_TYPE_MUST_BE_SET =
        "Level: Type should be set for %s";
    public static final String NAMED_SET_NAME_MUST_BE_SET = "NamedSet name must be set";
    public static final String NAMED_SET_FORMULA_MUST_BE_SET = "NamedSet Formula must be set";
    public static final String USER_DEFINED_FUNCTION = "UserDefinedFunction";
    public static final String PROPERTY_COLUMN_MUST_BE_SET = "Property Column must be set";
    public static final String PROPERTY_TYPE_MUST_BE_SET = "Property type should be set";
    public static final String LEVEL_COLUMN_MUST_BE_SET =
        "Level: Column must be set for hierarchy with name %s";
    public static final String LEVEL_NAME_MUST_BE_SET =
        "Level name must be set for hierarchy with name %s";
    public static final String VIRTUAL_CUBE_NAME_MUST_BE_SET = "VirtualCube name must be set";
    public static final String CALCULATED_MEMBER_NAME_MUST_BE_SET = "CalculatedMember name must be set";
    public static final String CALCULATED_MEMBER = "CalculatedMember";
    public static final String FORMULA_MUST_BE_SET = "Formula must be set";
    public static final String TABLE_FOR_COLUMN_CANNOT_BE_SET_IN_VIEW = "Table for column cannot be set in View";
    public static final String TABLE_VALUE_DOES_NOT_CORRESPOND_TO_HIERARCHY_RELATION =
        "Table value does not correspond to Hierarchy Relation";
    public static final String TABLE_MUST_BE_SET = "Table must be set";
    public static final String TABLE_VALUE_DOES_NOT_CORRESPOND_TO_ANY_JOIN =
        "Table value does not correspond to any join";
    public static final String EITHER_A_CLASS_NAME_OR_A_SCRIPT_ARE_REQUIRED =
        "UserDefinedFunction with name %s. Either a Class Name or a Script are required ";
    public static final String FORMULA_MUST_BE_SET_FOR_CALCULATED_MEMBER =
        "Formula must be set for CalculatedMember with name %s";
    public static final String HIERARCHY_MUST_BE_SET_FOR_CALCULATED_MEMBER =
        "Hierarchy must be set for CalculatedMember with name %s";
    public static final String USER_DEFINED_FUNCTION_NAME_MUST_BE_SET = "UserDefinedFunction name must be set";
    public static final String SCHEMA_NAME_MUST_BE_SET = "Schema name must be set";
    public static final String SCHEMA_MUST_BE_NOT_NULL = "Schema must be set";
    public static final String VIRTUAL_CUBE_MUST_CONTAIN_DIMENSIONS =
        "VirtualCube with name %s must contain dimensions ";
    public static final String VIRTUAL_CUBE_MUST_CONTAIN_MEASURES =
        "VirtualCube with name %s must contain measures";

    public static final String COULD_NOT_CHECK_EXISTANCE_OF_TABLE_0_DOES_NOT_EXIST_IN_DATABASE =
        "could not check existence of table {0} does not exist in database {1}";
    public static final String FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE =
        "Fact table %s does not exist in database %s";
    public static final String AGG_EXCLUDE_TABLE_0_DOES_NOT_EXIST_IN_DATABASE =
        "Agg Exclude table %s does not exist in database %s";
    public static final String CUBE_MUST_CONTAIN_MEASURES = "Cube with name %s must contain measures";
    public static final String COULD_NOT_QUERY_COLUMN_DATA_TYPE_ON_SCHEMA_TABLE_COLUMN =
        "Could not Query ColumnDataType on Schema: %s, Table: %s, Column: %s";
    public static final String AGGREGATOR_IS_NOT_VALID_FOR_THE_DATA_TYPE_OF_THE_COLUMN =
        "Aggregator %s is not valid for the data type of the column %s";
    public static final String DATABASE_DOES_NOW_ANSWER_WITH_DATA_TYPE_FOR_AGGREGATOR_COLUMN =
        "Database does now answer with DataType for aggregator: %s Column: %s";
    public static final String COULD_NOT_EVALUEATE_DOES_COLUMN_EXIST_SCHEMA_TABLE_COLUMN =
        "Could not evalueate doesColumnExist Schema: %s Table: %s Column: %s";
    public static final String CUBE_DIMENSION_FOREIGN_KEY_S_DOES_NOT_EXIST_IN_FACT_TABLE =
        "Cube Dimension foreignKey %s does not exist in fact table";
    public static final String COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE =
        "Could not lookup existence of Column %s defined in field %s in table %s";
    public static final String FOREIGN_KEY = "foreignKey";
    public static final String COLUMN_S_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE =
        "Column %s defined in field %s does not exist in table %s";
    public static final String HIERARCHY_PRIMARY_KEY = "hierarchy.primaryKey";
    public static final String PRIMARY_KEY = "primaryKey";
    public static final String DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE =
        "Degenerate dimension validation check - Column %s does not exist in fact table";
    public static final String RELATON = "relaton";
    public static final String PROPERTY_COLUMN_0_DOES_NOT_EXIST_IN_HIERARCHY_TABLE =
        "Property column %s does not exist in Hierarchy table %s";
    public static final String COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN =
        "Could not lookup existence of Column %s defined in field %s in ";
    public static final String MEASURE_COLUMN_DOES_NOT_EXIST_IN_CUBE_TABLE =
        "Measure %s column %s doesn't exist in table %s of cube with name %s";
    public static final String COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_IN_TABLE =
        "Could not lookup existence of Column %s in table %s";
    public static final String PARENT_TABLE_NAME = "parentTable.name";
    public static final String COLUMN_S_DOES_NOT_EXIST_IN_LEVEL_TABLE_S = "Column %s does not exist in Level table %s";
    public static final String COLUMN_S_DOES_NOT_EXIST_IN_WRITE_BACK_TABLE_S = "Column %s does not exist in table %s";
    public static final String TABLE_S_DOES_NOT_EXIST_IN_DATABASE = "Table %s does not exist in database";
    public static final String COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0 = "could noch check existence of Table {0}";
    public static final String SCHEMA_S_DOES_NOT_EXIST = "Schema %s does not exist";
    public static final String COULD_NOT_CHECK_EXISTANCE_OF_SCHEMA_S = "could not check existence of Schema %s";
    public static final String DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE1 =
        "Degenerate dimension validation check - Column %s does not exist in fact table";
    public static final String COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE_24 =
        "Could not lookup existence of Column %s defined in field %s in table {2}";
    public static final String COLUMN_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE =
        "Column %s defined in field %s does not exist in table %s";
    public static final String COLUMN_S_DEFINED_IN_FIELD_S_DOES_NOT_EXIST_IN_TABLE_S2 =
        "Column %s defined in field %s does not exist in table %s";
    public static final String COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE =
        "Could not lookup existence of Column %s defined in field %s in table %s";
    public static final String CUBE_NAME_MUST_SET = "Cube name must be set";
    public static final String DIMENSION_MUST_CONTAIN_DESCRIPTION = "Dimension must contain description";
    public static final String VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION = "Virtual Cube must contain description";
    public static final String CUBE_MUST_CONTAIN_DESCRIPTION = "Cube must contain description";
    public static final String MEASURE_MUST_CONTAIN_DESCRIPTION = "Measure must contain description";
    public static final String KPI_MUST_CONTAIN_DESCRIPTION = "KPI must contain description";
    public static final String CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION =
        "CalculatedMemberProperty must contain description";
    public static final String CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION = "CalculatedMember must contain description";
    public static final String HIERARCHY_MUST_CONTAIN_DESCRIPTION = "Hierarchy must contain description";
    public static final String LEVEL_MUST_CONTAIN_DESCRIPTION = "Level must contain description";
    public static final String ACTION_MUST_CONTAIN_DESCRIPTION = "Action must contain description";
    public static final String SHARED_DIMENSION_MUST_CONTAIN_DESCRIPTION = "SharedDimension must contain description";
    public static final String PROPERTY_MUST_CONTAIN_DESCRIPTION = "Property must contain description";
    public static final String NAMED_SET_MUST_CONTAIN_DESCRIPTION = "NamedSet must contain description";
    public static final String PARAMETER_MUST_CONTAIN_DESCRIPTION = "Parameter must contain description";
    public static final String DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION =
        "DrillThroughAction must contain description";
    public static final String SCHEMA_MUST_CONTAIN_DESCRIPTION = "Schema must contain description";
    public static final String WRONG_MEASURE_AGGREGATOR_FOR_MEASURE_IN_CUBE =
        "Wrong measure aggregator %s  for measure with name %s in cube with name %s";
    public static final String KPIS_FROM_CUBE_WITH_NAME_HAS_RING_PARENT = "KPIs from cube with name %s has parent ring";
    private SchemaWalkerMessages() {
        //private constructor
    }
}
