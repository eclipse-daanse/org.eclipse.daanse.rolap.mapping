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
 *
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.SqlStatementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlViewMapping;

public class SqlViewMappingImpl extends AbstractTableMappingImpl implements SqlViewMapping {

    private List<? extends SqlStatementMapping> sqlStatements;

    public SqlViewMappingImpl(Builder builder) {
        setName(builder.getName());
        setColumns(builder.getColumns());
        setSchema(builder.getSchema());
        setDescription(builder.getDescription());
        setSqlStatements(builder.sqlStatements);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<? extends SqlStatementMapping> getSqlStatements() {
        return sqlStatements;
    }

    public void setSqlStatements(List<? extends SqlStatementMapping> sqlStatements) {
        this.sqlStatements = sqlStatements;
    }

    public static final class Builder extends AbstractBuilder {

        private List<? extends SqlStatementMapping> sqlStatements;

        private Builder() {
        }

        public Builder withSqlStatements(List<? extends SqlStatementMapping> sqlStatements) {
            this.sqlStatements = sqlStatements;
            return this;
        }

        public SqlViewMappingImpl build() {
            return new SqlViewMappingImpl(this);
        }

    }
}
