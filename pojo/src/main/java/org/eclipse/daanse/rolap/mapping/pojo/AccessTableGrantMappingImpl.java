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

import org.eclipse.daanse.rolap.mapping.api.model.AccessColumnGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessTableGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessTable;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl.Builder;

public class AccessTableGrantMappingImpl implements AccessTableGrantMapping{

    private AccessTable access;

    private List<? extends AccessColumnGrantMapping> columnGrants;

    private TableMapping table;

    private AccessTableGrantMappingImpl(Builder builder) {
        this.access = builder.access;
        this.table = builder.table;
        this.columnGrants = builder.columnGrants;
    }

    @Override
    public AccessTable getAccess() {
        return this.access;
    }

    @Override
    public List<? extends AccessColumnGrantMapping> getColumnGrants() {
        return this.columnGrants;
    }

    @Override
    public TableMapping getTable() {
        return this.table;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessTable access;
        private TableMapping table;
        private List<AccessColumnGrantMappingImpl> columnGrants = new ArrayList<>();

        private Builder() {
        }

        public Builder withColumnGrants(List<AccessColumnGrantMappingImpl> columnGrants) {
            this.columnGrants = columnGrants;
            return this;
        }

        public Builder withAccess(AccessTable access) {
            this.access = access;
            return this;
        }

        public Builder withTable(TableMapping table) {
            this.table = table;
            return this;
        }

        public AccessTableGrantMappingImpl build() {
            return new AccessTableGrantMappingImpl(this);
        }
    }

}
