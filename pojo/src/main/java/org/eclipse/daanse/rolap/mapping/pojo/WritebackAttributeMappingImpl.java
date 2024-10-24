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
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;

public class WritebackAttributeMappingImpl implements WritebackAttributeMapping {

    private Column column;

    private DimensionMappingImpl dimension;

    private WritebackAttributeMappingImpl(Builder builder) {
        this.column = builder.column;
        this.dimension = builder.dimension;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
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
        private Column column;
        private DimensionMappingImpl dimension;

        private Builder() {
        }

        public Builder withColumn(Column column) {
            this.column = column;
            return this;
        }

        public Builder withDimension(DimensionMappingImpl dimension) {
            this.dimension = dimension;
            return this;
        }

        public WritebackAttributeMappingImpl build() {
            return new WritebackAttributeMappingImpl(this);
        }
    }
}
