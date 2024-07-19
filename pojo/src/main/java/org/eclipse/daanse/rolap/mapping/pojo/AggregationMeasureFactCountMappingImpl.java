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

    private String column;

    private String factColumn;

    private AggregationMeasureFactCountMappingImpl(Builder builder) {
        this.column = builder.column;
        this.factColumn = builder.factColumn;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getFactColumn() {
        return factColumn;
    }

    public void setFactColumn(String factColumn) {
        this.factColumn = factColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String column;
        private String factColumn;

        private Builder() {
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withFactColumn(String factColumn) {
            this.factColumn = factColumn;
            return this;
        }

        public AggregationMeasureFactCountMappingImpl build() {
            return new AggregationMeasureFactCountMappingImpl(this);
        }
    }
}
