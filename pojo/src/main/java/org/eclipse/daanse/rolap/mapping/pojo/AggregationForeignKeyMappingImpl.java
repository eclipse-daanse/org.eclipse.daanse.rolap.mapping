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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;

public class AggregationForeignKeyMappingImpl implements AggregationForeignKeyMapping {

    private String aggregationColumn;

    private String factColumn;

    private AggregationForeignKeyMappingImpl(Builder builder) {
        this.aggregationColumn = builder.aggregationColumn;
        this.factColumn = builder.factColumn;
    }

    public String getAggregationColumn() {
        return aggregationColumn;
    }

    public void setAggregationColumn(String aggregationColumn) {
        this.aggregationColumn = aggregationColumn;
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
        private String aggregationColumn;
        private String factColumn;

        private Builder() {
        }

        public Builder withAggregationColumn(String aggregationColumn) {
            this.aggregationColumn = aggregationColumn;
            return this;
        }

        public Builder withFactColumn(String factColumn) {
            this.factColumn = factColumn;
            return this;
        }

        public AggregationForeignKeyMappingImpl build() {
            return new AggregationForeignKeyMappingImpl(this);
        }
    }
}
