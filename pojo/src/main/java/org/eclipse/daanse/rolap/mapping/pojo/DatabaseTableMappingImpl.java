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

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseTableMapping;

public class DatabaseTableMappingImpl implements DatabaseTableMapping {

    private String id;

    private String name;

    private String description;

    private List<DatabaseColumnMappingImpl> columns;

    private DatabaseTableMappingImpl(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.columns = builder.columns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DatabaseColumnMappingImpl> getColumns() {
        return columns;
    }

    public void setColumns(List<DatabaseColumnMappingImpl> columns) {
        this.columns = columns;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private List<DatabaseColumnMappingImpl> columns = Collections.emptyList();

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withColumns(List<DatabaseColumnMappingImpl> columns) {
            this.columns = columns;
            return this;
        }

        public DatabaseTableMappingImpl build() {
            return new DatabaseTableMappingImpl(this);
        }
    }
}
