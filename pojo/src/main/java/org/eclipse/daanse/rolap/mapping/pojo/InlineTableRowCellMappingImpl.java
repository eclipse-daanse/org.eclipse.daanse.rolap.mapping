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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowCellMapping;

public class InlineTableRowCellMappingImpl implements InlineTableRowCellMapping {

    public InlineTableRowCellMappingImpl(String value, String columnName) {
        super();
        this.value = value;
        this.columnName = columnName;
    }

    private String value;

    private String columnName;

    private InlineTableRowCellMappingImpl(Builder builder) {
        this.value = builder.value;
        this.columnName = builder.columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String value;
        private String columnName;

        private Builder() {
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public InlineTableRowCellMappingImpl build() {
            return new InlineTableRowCellMappingImpl(this);
        }
    }
}
