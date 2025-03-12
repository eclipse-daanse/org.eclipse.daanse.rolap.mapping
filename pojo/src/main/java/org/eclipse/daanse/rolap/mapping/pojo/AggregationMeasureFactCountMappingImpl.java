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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;

public class AggregationMeasureFactCountMappingImpl implements AggregationMeasureFactCountMapping {

    private PhysicalColumnMappingImpl column;

    private PhysicalColumnMappingImpl factColumn;

    private AggregationMeasureFactCountMappingImpl(Builder builder) {
        this.column = builder.column;
        this.factColumn = builder.factColumn;
    }

    public PhysicalColumnMappingImpl getColumn() {
        return column;
    }

    public void setColumn(PhysicalColumnMappingImpl column) {
        this.column = column;
    }

    public PhysicalColumnMappingImpl getFactColumn() {
        return factColumn;
    }

    public void setFactColumn(PhysicalColumnMappingImpl factColumn) {
        this.factColumn = factColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PhysicalColumnMappingImpl column;
        private PhysicalColumnMappingImpl factColumn;

        private Builder() {
        }

        public Builder withColumn(PhysicalColumnMappingImpl column) {
            this.column = column;
            return this;
        }

        public Builder withFactColumn (PhysicalColumnMappingImpl factColumn) {
            this.factColumn = factColumn;
            return this;
        }

        public AggregationMeasureFactCountMappingImpl build() {
            return new AggregationMeasureFactCountMappingImpl(this);
        }
    }
}
