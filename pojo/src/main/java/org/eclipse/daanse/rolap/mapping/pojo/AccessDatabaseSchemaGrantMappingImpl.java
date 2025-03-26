/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessDatabaseSchemaGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDatabaseSchema;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl.Builder;

public class AccessDatabaseSchemaGrantMappingImpl implements AccessDatabaseSchemaGrantMapping {

    private List<AccessTableGrantMappingImpl> tableGrants;
    private AccessDatabaseSchema access;
    private DatabaseSchemaMapping databaseSchema;

    private AccessDatabaseSchemaGrantMappingImpl(Builder builder) {
        this.access = builder.access;
        this.databaseSchema = builder.databaseSchema;
        this.tableGrants = builder.tableGrants;
    }

    @Override
    public AccessDatabaseSchema getAccess() {
        return this.access;
    }

    @Override
    public DatabaseSchemaMapping getDatabaseSchema() {
        return this.databaseSchema;
    }

    @Override
    public List<AccessTableGrantMappingImpl> getTableGrants() {
        return this.tableGrants;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessDatabaseSchema access;
        private DatabaseSchemaMapping databaseSchema;
        private List<AccessTableGrantMappingImpl> tableGrants = new ArrayList<>();

        private Builder() {
        }

        public Builder withTableGrants(List<AccessTableGrantMappingImpl> tableGrants) {
            this.tableGrants = tableGrants;
            return this;
        }

        public Builder withAccess(AccessDatabaseSchema access) {
            this.access = access;
            return this;
        }

        public Builder withDatabaseSchema(DatabaseSchemaMappingImpl databaseSchema) {
            this.databaseSchema = databaseSchema;
            return this;
        }

        public AccessDatabaseSchemaGrantMappingImpl build() {
            return new AccessDatabaseSchemaGrantMappingImpl(this);
        }
    }
}
