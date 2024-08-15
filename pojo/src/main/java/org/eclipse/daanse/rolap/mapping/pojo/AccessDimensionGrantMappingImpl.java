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

import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimensionEnum;

public class AccessDimensionGrantMappingImpl implements AccessDimensionGrantMapping {

    private AccessDimensionEnum access;

    private DimensionMappingImpl dimension;

    private AccessDimensionGrantMappingImpl(Builder builder) {
        this.access = builder.access;
        this.dimension = builder.dimension;
    }

    public AccessDimensionEnum getAccess() {
        return access;
    }

    public void setAccess(AccessDimensionEnum access) {
        this.access = access;
    }

    public DimensionMappingImpl getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMappingImpl dimension) {
        this.dimension = dimension;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessDimensionEnum access;
        private DimensionMappingImpl dimension;

        private Builder() {
        }

        public Builder withAccess(AccessDimensionEnum access) {
            this.access = access;
            return this;
        }

        public Builder withDimension(DimensionMappingImpl dimension) {
            this.dimension = dimension;
            return this;
        }

        public AccessDimensionGrantMappingImpl build() {
            return new AccessDimensionGrantMappingImpl(this);
        }
    }
}
