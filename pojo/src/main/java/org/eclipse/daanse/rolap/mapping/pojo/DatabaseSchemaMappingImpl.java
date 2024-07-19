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

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;

public class DatabaseSchemaMappingImpl implements DatabaseSchemaMapping {

    private String id;

    private String name;

    private List<DatabaseTableMappingImpl> tables;

    private DatabaseSchemaMappingImpl(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.tables = builder.tables;
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

    public List<DatabaseTableMappingImpl> getTables() {
        return tables;
    }

    public void setTables(List<DatabaseTableMappingImpl> tables) {
        this.tables = tables;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private List<DatabaseTableMappingImpl> tables = Collections.emptyList();

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

        public Builder withTables(List<DatabaseTableMappingImpl> tables) {
            this.tables = tables;
            return this;
        }

        public DatabaseSchemaMappingImpl build() {
            return new DatabaseSchemaMappingImpl(this);
        }
    }
}
