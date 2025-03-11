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

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowMapping;

public class InlineTableMappingImpl extends AbstractTableMappingImpl implements InlineTableMapping {

    private List<? extends RowMapping> rows;

    public static Builder builder() {
        return new Builder();
    }

    public InlineTableMappingImpl(Builder builder) {
        setName(builder.name);
        setColumns(builder.columns);
        setSchema(builder.schema);
        setDescription(builder.description);
        setRows(builder.rows);
    }

    @Override
    public List<? extends RowMapping> getRows() {
        return rows;
    }

    public void setRows(List<? extends RowMapping> rows) {
        this.rows = rows;
    }

    public static final class Builder {

        private String name;

        private List<ColumnMapping> columns;

        private DatabaseSchemaMappingImpl schema;

        private String description;

        private List<? extends RowMapping> rows;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withColumns(List<ColumnMapping> columns) {
            this.columns = columns;
            return this;
        }

        public Builder withsSchema(DatabaseSchemaMappingImpl schema) {
            this.schema = schema;
            return this;
        }

        public Builder withsDdescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withRows(List<RowMapping> rows) {
            this.rows = rows;
            return this;
        }

        public InlineTableMappingImpl build() {
            return new InlineTableMappingImpl(this);
        }

    }
}
