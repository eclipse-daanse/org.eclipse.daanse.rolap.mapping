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

import org.eclipse.daanse.rolap.mapping.api.model.AccessColumnGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessColumn;

public class AccessColumnGrantMappingImpl implements AccessColumnGrantMapping{

    private AccessColumn access;
    private ColumnMapping column;

    private AccessColumnGrantMappingImpl(Builder builder) {
        this.access = builder.access;
        this.column = builder.column;
    }

    @Override
    public AccessColumn getAccess() {
        return this.access;
    }

    @Override
    public ColumnMapping getColumn() {
        return this.column;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessColumn access;
        private ColumnMapping column;

        private Builder() {
        }

        public Builder withAccess(AccessColumn access) {
            this.access = access;
            return this;
        }

        public Builder withColumn(ColumnMapping column) {
            this.column = column;
            return this;
        }

        public AccessColumnGrantMappingImpl build() {
            return new AccessColumnGrantMappingImpl(this);
        }
    }
}
