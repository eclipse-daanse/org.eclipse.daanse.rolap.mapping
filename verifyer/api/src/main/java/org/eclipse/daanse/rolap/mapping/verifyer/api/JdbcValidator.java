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
package org.eclipse.daanse.rolap.mapping.verifyer.api;

/**
 * Validation for database schema, table, and columns. Extracted interface from
 * <code>mondrian.gui.JDBCMetaData</code>.
 *
 * @author mlowery
 */
public interface JdbcValidator {
    /**
     * Returns the data type of given column.
     *
     * @return SQL type from java.sql.Types
     */
    int getColumnDataType(String schemaName, String tableName, String colName);

    /**
     * Returns true if column exists.
     */
    boolean isColExists(String schemaName, String tableName, String colName);

    /**
     * Returns true if table exists.
     */
    boolean isTableExists(String schemaName, String tableName);

    /**
     * Returns true if this object successfully connected to database (and
     * validation methods can now be called).
     */
    boolean isInitialized();

    /**
     * Returns true if schema exists.
     */
    boolean isSchemaExists(String schemaName);
}
