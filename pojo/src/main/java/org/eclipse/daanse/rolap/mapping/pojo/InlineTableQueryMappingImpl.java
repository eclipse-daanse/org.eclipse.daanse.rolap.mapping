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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;

public class InlineTableQueryMappingImpl extends RelationalQueryMappingImpl implements InlineTableQueryMapping {

    private List<InlineTableColumnDefinitionMappingImpl> columnDefinitions;

    private List<InlineTableRowMappingMappingImpl> rows;

    private InlineTableQueryMappingImpl(Builder builder) {
        this.columnDefinitions = builder.columnDefinitions;
        this.rows = builder.rows;
        super.setAlias(builder.alias);
    }

    public List<InlineTableColumnDefinitionMappingImpl> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<InlineTableColumnDefinitionMappingImpl> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public List<InlineTableRowMappingMappingImpl> getRows() {
        return rows;
    }

    public void setRows(List<InlineTableRowMappingMappingImpl> rows) {
        this.rows = rows;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<InlineTableColumnDefinitionMappingImpl> columnDefinitions = Collections.emptyList();
        private List<InlineTableRowMappingMappingImpl> rows = Collections.emptyList();
        private String alias;

        private Builder() {
        }

        public Builder withColumnDefinitions(List<InlineTableColumnDefinitionMappingImpl> columnDefinitions) {
            this.columnDefinitions = columnDefinitions;
            return this;
        }

        public Builder withRows(List<InlineTableRowMappingMappingImpl> rows) {
            this.rows = rows;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public InlineTableQueryMappingImpl build() {
            return new InlineTableQueryMappingImpl(this);
        }
    }
}
