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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;

public class ParentChildLinkMappingImpl implements ParentChildLinkMapping {

    private TableQueryMappingImpl table;

    private Column childColumn;

    private Column parentColumn;

    private ParentChildLinkMappingImpl(Builder builder) {
        this.table = builder.table;
        this.childColumn = builder.childColumn;
        this.parentColumn = builder.parentColumn;
    }

    public TableQueryMappingImpl getTable() {
        return table;
    }

    public void setTable(TableQueryMappingImpl table) {
        this.table = table;
    }

    public Column getChildColumn() {
        return childColumn;
    }

    public void setChildColumn(Column childColumn) {
        this.childColumn = childColumn;
    }

    public Column getParentColumn() {
        return parentColumn;
    }

    public void setParentColumn(Column parentColumn) {
        this.parentColumn = parentColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TableQueryMappingImpl table;
        private Column childColumn;
        private Column parentColumn;

        private Builder() {
        }

        public Builder withTable(TableQueryMappingImpl table) {
            this.table = table;
            return this;
        }

        public Builder withChildColumn(Column childColumn) {
            this.childColumn = childColumn;
            return this;
        }

        public Builder withParentColumn(Column parentColumn) {
            this.parentColumn = parentColumn;
            return this;
        }

        public ParentChildLinkMappingImpl build() {
            return new ParentChildLinkMappingImpl(this);
        }
    }
}
