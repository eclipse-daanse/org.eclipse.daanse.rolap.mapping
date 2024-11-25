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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.jdbc;

import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.AbstractSchemaWalker;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.eclipse.daanse.rolap.mapping.verifyer.api.Cause.DATABASE;
import static org.eclipse.daanse.rolap.mapping.verifyer.api.Level.ERROR;
import static org.eclipse.daanse.rolap.mapping.verifyer.api.Level.WARNING;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGGREGATOR_IS_NOT_VALID_FOR_THE_DATA_TYPE_OF_THE_COLUMN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.AGG_EXCLUDE_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DOES_NOT_EXIST_IN_WRITE_BACK_TABLE_S;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY_COLUMN_0_DOES_NOT_EXIST_IN_HIERARCHY_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COLUMN_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DEFINED_IN_FIELD_S_DOES_NOT_EXIST_IN_TABLE_S2;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COLUMN_S_DOES_NOT_EXIST_IN_LEVEL_TABLE_S;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_CHECK_EXISTANCE_OF_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_CHECK_EXISTANCE_OF_SCHEMA_S;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_EVALUEATE_DOES_COLUMN_EXIST_SCHEMA_TABLE_COLUMN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_IN_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE_24;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.COULD_NOT_QUERY_COLUMN_DATA_TYPE_ON_SCHEMA_TABLE_COLUMN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_DIMENSION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_DIMENSION_FOREIGN_KEY_S_DOES_NOT_EXIST_IN_FACT_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DATABASE_DOES_NOW_ANSWER_WITH_DATA_TYPE_FOR_AGGREGATOR_COLUMN;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE1;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.FOREIGN_KEY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY_PRIMARY_KEY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE_COLUMN_DOES_NOT_EXIST_IN_CUBE_TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARENT_TABLE_NAME;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PRIMARY_KEY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.RELATON;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_SPACE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_S_DOES_NOT_EXIST;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SQL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.TABLE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.TABLE_S_DOES_NOT_EXIST_IN_DATABASE;

public class JDBCSchemaWalker extends AbstractSchemaWalker {

    private DatabaseService databaseService;
    private DatabaseVerifierConfig config;
    private DatabaseMetaData databaseMetaData;
    private Optional<Dialect> optionalDialect;

    public JDBCSchemaWalker(DatabaseVerifierConfig config, DatabaseService databaseService, DatabaseMetaData databaseMetaData, Optional<Dialect> optionalDialect) {
        this.config = config;
        this.databaseService = databaseService;
        this.databaseMetaData = databaseMetaData;
        this.optionalDialect = optionalDialect;
    }

    @Override
    protected void checkCube(CubeMapping cube, SchemaMapping schema) {
        super.checkCube(cube, schema);
        checkFact(cube, schema);
    }

    @Override
    protected void checkFact(CubeMapping cube, SchemaMapping schema) {
        if (cube.fact() instanceof MappingTableQuery table) {
            String schemaName = table.getSchema();
            String factTable = table.getName();
            TableReference tableReference = getTableReference(schemaName, factTable);
            try {
                if (!databaseService.tableExists(databaseMetaData, tableReference)) {
                    String message = String.format(FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE, factTable,
                        ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                    results.add(new VerificationResultR(CUBE, message, ERROR, DATABASE));
                }

                if (table.getAggExcludes() != null && !table.getAggExcludes().isEmpty()) {
                    table.getAggExcludes()
                        .forEach(ae -> checkAggExclude(ae, schemaName));
                }
            } catch (SQLException e) {
                String message = String.format(COULD_NOT_CHECK_EXISTANCE_OF_TABLE_0_DOES_NOT_EXIST_IN_DATABASE,
                    factTable,
                    ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                results.add(new VerificationResultR(CUBE, message, ERROR, DATABASE));
            }
        }
    }

    @Override
    protected void checkAggExclude(MappingAggExclude aggExclude, String schemaName) {
        if (!isEmpty(aggExclude.name())) {
            try {
                TableReference tableReference = getTableReference(schemaName, aggExclude.name());
                if (!databaseService.tableExists(databaseMetaData, tableReference)) {
                    String message = String.format(AGG_EXCLUDE_TABLE_0_DOES_NOT_EXIST_IN_DATABASE, aggExclude.name(),
                        ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                    results.add(new VerificationResultR(CUBE, message, WARNING, DATABASE));
                }
            } catch (SQLException e) {
                String message = String.format(COULD_NOT_CHECK_EXISTANCE_OF_TABLE_0_DOES_NOT_EXIST_IN_DATABASE,
                    aggExclude.name(),
                    ((schemaName == null || schemaName.equals("")) ? "." : SCHEMA_SPACE + schemaName));
                results.add(new VerificationResultR(CUBE, message, ERROR, DATABASE));
            }
        }
    }

    @Override
    protected void checkMeasure(MappingMeasure measure, MappingCube cube) {
        super.checkMeasure(measure, cube);
        if (cube != null && cube.fact() != null && cube.fact() instanceof MappingTableQuery factTable) {
            // Database validity check, if database connection is
            // successful
            String column = measure.column();
            TableReference tableReference = getTableReference(factTable.getSchema(), factTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            try {
                if (databaseService.columnExists(databaseMetaData, columnReference)) {
                    // Check for aggregator type only if column
                    // exists in table.

                    checkMeasureColumnDataType(measure, factTable);
                }
            } catch (SQLException e) {

                String msg = String.format(COULD_NOT_EVALUEATE_DOES_COLUMN_EXIST_SCHEMA_TABLE_COLUMN,
                    factTable.getSchema(), factTable.getName(), column);
                results.add(new VerificationResultR(MEASURE, msg, ERROR, DATABASE));
            }
        }
    }

    @Override
    protected void checkCubeDimension(MappingCubeDimension cubeDimension, MappingCube cube, MappingSchema schema) {
        super.checkCubeDimension(cubeDimension, cube, schema);

        if (cubeDimension instanceof MappingPrivateDimension &&
            !isEmpty((cubeDimension).foreignKey()) &&
            cube != null &&
            cube.fact() instanceof MappingTableQuery factTable) {

            String foreignKey = (cubeDimension).foreignKey();
            TableReference tableReference = getTableReference(factTable.getSchema(), factTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), foreignKey);

            try {
                if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                    String msg = String.format(CUBE_DIMENSION_FOREIGN_KEY_S_DOES_NOT_EXIST_IN_FACT_TABLE,
                        foreignKey);
                    results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, DATABASE));
                }
            } catch (SQLException e) {
                String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                    isEmpty(foreignKey.trim()) ? "' '" : foreignKey, FOREIGN_KEY,
                    factTable.getName());
                results.add(new VerificationResultR(CUBE_DIMENSION, msg, ERROR, DATABASE));
            }

        }
        // TODO: Need to add validation for Views

    }

    @Override
    protected void checkHierarchy(MappingHierarchy hierarchy, MappingPrivateDimension cubeDimension, MappingCube cube) {
        super.checkHierarchy(hierarchy, cubeDimension, cube);

        // Validates that value in primaryKey exists in Table.
        String schema = null;
        String pkTable = null;
        if (hierarchy.relation() instanceof MappingJoinQuery) {
            String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(hierarchy.relation(),
                hierarchy.primaryKeyTable());
            schema = schemaAndTable[0];
            pkTable = schemaAndTable[1];
        } else if (hierarchy.relation() instanceof MappingTableQuery table) {
            pkTable = table.getName();
            schema = table.getSchema();
        }
        try {
            TableReference tableReference = getTableReference(schema, pkTable);
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), hierarchy.primaryKey());
            if (pkTable != null && !databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(COLUMN_S_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE,
                    isEmpty(hierarchy.primaryKey()
                        .trim()) ? "' '" : hierarchy.primaryKey(),
                    PRIMARY_KEY, pkTable);
                results.add(new VerificationResultR(HIERARCHY, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                isEmpty(hierarchy.primaryKey().trim()) ? "' '" : hierarchy.primaryKey(), HIERARCHY_PRIMARY_KEY,
                pkTable);
            results.add(new VerificationResultR(HIERARCHY, msg, ERROR, DATABASE));
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
        String column = property.column();
        String table = null;
        if (level != null) {
            // specified table for level's column'
            table = level.table();
        }
        if (isEmpty(table)) {
            if (hierarchy != null) {
                checkPropertyHierarchy(column, hierarchy, cube);
            }
        } else {
            try {
                TableReference tableReference = getTableReference(null, table);
                ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
                if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                    String msg = String.format(COLUMN_S_DOES_NOT_EXIST_IN_LEVEL_TABLE_S, column, table);
                    results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
                }
            } catch (SQLException e) {
                String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                    isEmpty(column.trim()) ? "' '" : column, "-", table);
                results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
            }
        }

    }

    @Override
    protected void checkTable(MappingTableQuery table) {
        super.checkTable(table);
        String tableName = table.getName();
        try {
            TableReference tableReference = getTableReference(null, tableName);
            if (!databaseService.tableExists(databaseMetaData, tableReference)) {
                String msg = String.format(TABLE_S_DOES_NOT_EXIST_IN_DATABASE, tableName);
                results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String message = String.format(COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0, tableName);
            results.add(new VerificationResultR(TABLE, message, ERROR, DATABASE));
        }

        String theSchema = table.getSchema();
        try {
            List<SchemaReference> schemaList = databaseService.getSchemas(databaseMetaData);
            if (!isEmpty(theSchema) &&  !schemaList.stream().filter(sr -> theSchema.equals(sr.name())).findFirst().isPresent()) {
                String msg = String.format(SCHEMA_S_DOES_NOT_EXIST, theSchema);
                results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_CHECK_EXISTANCE_OF_SCHEMA_S, theSchema);

            results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));

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
        String column,
        String fieldName,
        org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel level,
        MappingCube cube,
        MappingHierarchy parentHierarchy
    ) {
        super.checkColumn(column, fieldName, level, cube, parentHierarchy);
        if (!isEmpty(column)) {
            String table = level.table();
            if (isEmpty(table)) {
                checkColumnIfLevelTableEmpty(column, fieldName, cube, parentHierarchy);

            } else {
                checkColumnIfLevelTableNotEmpty(table, column, fieldName, parentHierarchy);
            }
        }
    }

    protected void checkMeasureColumn(MappingMeasure measure, MappingCube cube) {
        super.checkMeasureColumn(measure, cube);
        if (measure != null && !isEmpty(measure.column())) {
            final MappingRelationQuery relation = cube.fact();
            if (relation instanceof MappingTableQuery mappingTable) {
                checkMeasureColumnInMappingTable(measure, cube, mappingTable);
            }
        }
    }

    @Override
    protected void checkWritebackTable(MappingWritebackTable writebackTable, MappingCube cube) {
        super.checkWritebackTable(writebackTable, cube);
        String tableName = writebackTable.name();
        String schemaName = writebackTable.schema();
        if (tableName != null) {
            TableReference tableReference = getTableReference(schemaName, tableName);
            try {
                if (!databaseService.tableExists(databaseMetaData, tableReference)) {
                    String msg = String.format(TABLE_S_DOES_NOT_EXIST_IN_DATABASE, tableName);
                    results.add(new VerificationResultR(TABLE, msg, ERROR, DATABASE));
                }
            } catch (SQLException e) {
                String message = String.format(COULD_NOCH_CHECK_EXISTANCE_OF_TABLE_0, tableName);
                results.add(new VerificationResultR(TABLE, message, ERROR, DATABASE));
            }
            Iterable<MappingWritebackColumn>  columns = writebackTable.columns();
            if(columns != null || columns.spliterator().getExactSizeIfKnown() > 0) {
                for( MappingWritebackColumn column : columns ){
                    ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column.column());
                    try {
                        if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                            String msg = String.format(COLUMN_S_DOES_NOT_EXIST_IN_WRITE_BACK_TABLE_S, column.column(), tableName);
                            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));

                        }
                    } catch (SQLException throwables) {
                        String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                            isEmpty(column.column()) ? "' '" : column.column(), "-", tableName);
                        results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));

                    }
                }
            }
        }
    }

    private void checkMeasureColumnInMappingTable(MappingMeasure measure, MappingCube cube, MappingTableQuery mappingTable) {
        try {
            TableReference tableReference = getTableReference(mappingTable.getSchema(), mappingTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), measure.column());
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(
                    MEASURE_COLUMN_DOES_NOT_EXIST_IN_CUBE_TABLE,
                    measure.name(), measure.column(), mappingTable.getName(), cube.name());
                results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(
                COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_IN_TABLE,
                measure.column(), mappingTable.getName());
            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
        }
    }

    @Override
    protected boolean isSchemaRequired() {
        return config.isSchemaRequired();
    }

    @Override
    protected void checkSQLList(List<? extends MappingSQL> list) {
        if (list != null && optionalDialect.isPresent()) {
            String dialectName = optionalDialect.get().getDialectName();
            List<? extends MappingSQL> sqls = list.stream().filter(sql -> sql.dialects().stream().anyMatch(d -> dialectName.equals(d))).toList();
            if (!sqls.isEmpty()) {
                checkSQL(sqls.get(0));
            } else {
                sqls = list.stream().filter(sql -> sql.dialects().stream().anyMatch(d -> "generic".equals(d))).toList();
                if (!sqls.isEmpty()) {
                    checkSQL(sqls.get(0));
                }
            }
        }
    }

    @Override
    protected void checkSQL(MappingSQL sql) {
    	if (sql != null && sql.statement() != null) {
    		try {
    			Connection con = databaseMetaData.getConnection();
    			Statement stmt = con.createStatement();
    			ResultSet rs = stmt.executeQuery(sql.statement());
    		} catch (SQLException e) {
    			results.add(new VerificationResultR(SQL, e.getMessage().replace("\n",""), ERROR, DATABASE));
    		}
    	}
    }

    private void checkPropertyHierarchy(String column, MappingHierarchy hierarchy, MappingCube cube) {
        if (hierarchy.relation() == null && cube != null) {
            checkPropertyHierarchyRelationNull(column, cube);
        } else if (hierarchy.relation() instanceof MappingTableQuery parentTable) {
            checkPropertyHierarchyRelationTable(parentTable, column);
            checkTable(parentTable);
        }
    }

    private void checkPropertyHierarchyRelationTable(MappingTableQuery parentTable, String column) {
        try {
            TableReference tableReference = getTableReference(parentTable.getSchema(), parentTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(PROPERTY_COLUMN_0_DOES_NOT_EXIST_IN_HIERARCHY_TABLE,
                    column, parentTable.getName());
                results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN +
                    "table {2}",
                isEmpty(column.trim()) ? "' '" : column, PARENT_TABLE_NAME, parentTable.getName());
            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
        }
    }

    private void checkPropertyHierarchyRelationNull(String column, MappingCube cube) {
        // Case of degenerate dimension within cube,
        // hierarchy table not specified
        final MappingTableQuery factTable = (MappingTableQuery) cube.fact();
        try {
            TableReference tableReference = getTableReference(factTable.getSchema(), factTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(
                    DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE,
                    column);
                results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                isEmpty(column.trim()) ? "' '" : column, RELATON, factTable.getName());
            results.add(new VerificationResultR(PROPERTY, msg, ERROR, DATABASE));
        }
    }

    private void checkMeasureColumnDataType(MappingMeasure measure, MappingTableQuery factTable) {
        // Check if aggregator selected is valid on
        // the data type of the column selected.
        Optional<ColumnDefinition> optionalColumnDefinition = Optional.empty();
        try {
            TableReference tableReference = getTableReference(factTable.getSchema(), factTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), measure.column());
            List<ColumnDefinition> columnDefinitionList = databaseService.getColumnDefinitions(databaseMetaData, columnReference);
            optionalColumnDefinition  = columnDefinitionList.stream().findFirst();
        } catch (SQLException e) {

            String msg = String.format(COULD_NOT_QUERY_COLUMN_DATA_TYPE_ON_SCHEMA_TABLE_COLUMN,
                measure.aggregator(), measure.column());
            results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
        }
        // Coltype of 2, 4,5, 7, 8, -5 is numeric types
        // whereas 1, 12 are char varchar string
        // and 91 is date type.
        // Types are enumerated in java.sql.Types.
        int agIndex = -1;
        if ("sum".equals(measure.aggregator()) || "avg".equals(measure.aggregator())) {
            // aggregator = sum or avg, column should
            // be numeric
            agIndex = 0;
        }
        if (optionalColumnDefinition.isPresent() && optionalColumnDefinition.get().columnMetaData() != null) {
            int colType = optionalColumnDefinition.get().columnMetaData().dataType().getVendorTypeNumber();
            if (!(agIndex == -1 || (colType >= 2 && colType <= 8) || colType == -5 || colType == -6)) {
                String msg = String.format(AGGREGATOR_IS_NOT_VALID_FOR_THE_DATA_TYPE_OF_THE_COLUMN,
                    measure.aggregator(), measure.column());
                results.add(new VerificationResultR(MEASURE, msg, ERROR, Cause.SCHEMA));
            }
        } else {
            String msg = String.format(DATABASE_DOES_NOW_ANSWER_WITH_DATA_TYPE_FOR_AGGREGATOR_COLUMN,
                measure.aggregator(), measure.column());
            results.add(new VerificationResultR(MEASURE, msg, ERROR, DATABASE));

        }
    }

    private void checkColumnIfLevelTableEmpty(String column, String fieldName, MappingCube cube, MappingHierarchy parentHierarchy) {
        if (parentHierarchy != null) {
            if (parentHierarchy.relation() == null && cube != null) {
                // case of degenerate dimension within cube,
                // hierarchy table not specified
                checkColumnWithCubeFctTable(column, fieldName, cube);
            } else if (parentHierarchy.relation() instanceof MappingTableQuery parentTable) {
                checkColumnWithHierarchyRelationTable(parentTable, column, fieldName, parentHierarchy);
            }
        }
    }

    private void checkColumnWithHierarchyRelationTable(MappingTableQuery parentTable, String column, String fieldName, MappingHierarchy parentHierarchy) {
        try {
            TableReference tableReference = getTableReference(parentTable.getSchema(), parentTable.getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(COLUMN_DEFINED_IN_FIELD_DOES_NOT_EXIST_IN_TABLE,
                    isEmpty(column.trim()) ? "' '" : column, fieldName,
                    parentTable.getName());
                results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
                checkTable((MappingTableQuery) parentHierarchy.relation());
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_DEFINED_IN_FIELD_IN_TABLE,
                isEmpty(column.trim()) ? "' '" : column, fieldName, parentTable.getName());
            results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
        }
    }

    private void checkColumnWithCubeFctTable(String column, String fieldName, MappingCube cube) {
        try {
            TableReference tableReference = getTableReference(((MappingTableQuery) cube.fact()).getSchema(), ((MappingTableQuery) cube.fact()).getName());
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg =
                    String.format(DEGENERATE_DIMENSION_VALIDATION_CHECK_COLUMN_S_DOES_NOT_EXIST_IN_FACT_TABLE1, column);
                results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg =
                String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE_24,
                    isEmpty(column.trim()) ? "' '" : column, fieldName, ((MappingTableQuery) cube.fact()).getName());
            results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
        }
    }

    private void checkColumnIfLevelTableNotEmpty(String table, String column, String fieldName, MappingHierarchy parentHierarchy) {
        String schema = null;
        // if using Joins then gets the table name for doesColumnExist
        // validation.
        if (parentHierarchy != null && parentHierarchy.relation() instanceof MappingJoinQuery join) {
            String[] schemaAndTable = SchemaExplorer.getTableNameForAlias(parentHierarchy.relation(), table);
            schema = schemaAndTable[0];
            table = schemaAndTable[1];
            checkJoin(join);
        }
        try {
            TableReference tableReference = getTableReference(schema, table);
            ColumnReference columnReference = new ColumnReferenceR(Optional.of(tableReference), column);
            if (!databaseService.columnExists(databaseMetaData, columnReference)) {
                String msg = String.format(COLUMN_S_DEFINED_IN_FIELD_S_DOES_NOT_EXIST_IN_TABLE_S2,
                    isEmpty(column.trim()) ? "' '" : column, fieldName, table);
                results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));
            }
        } catch (SQLException e) {
            String msg = String.format(COULD_NOT_LOOKUP_EXISTANCE_OF_COLUMN_S_DEFINED_IN_FIELD_S_IN_TABLE,
                isEmpty(column.trim()) ? "' '" : column, fieldName, table);
            results.add(new VerificationResultR(LEVEL, msg, ERROR, DATABASE));

        }
    }

    private TableReference getTableReference(String schemaName, String tableName) {
        return new TableReferenceR(Optional.ofNullable(schemaName != null ? new SchemaReferenceR(schemaName) : null),
            tableName);
    }
}
