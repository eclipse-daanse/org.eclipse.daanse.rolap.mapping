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
import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;

public class AggregationForeignKeyMappingImpl implements AggregationForeignKeyMapping {

    private Column aggregationColumn;

    private Column factColumn;

    private AggregationForeignKeyMappingImpl(Builder builder) {
        this.aggregationColumn = builder.aggregationColumn;
        this.factColumn = builder.factColumn;
    }

    public Column getAggregationColumn() {
        return aggregationColumn;
    }

    public void setAggregationColumn(Column aggregationColumn) {
        this.aggregationColumn = aggregationColumn;
    }

    public Column getFactColumn() {
        return factColumn;
    }

    public void setFactColumn(Column factColumn) {
        this.factColumn = factColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Column aggregationColumn;
        private Column factColumn;

        private Builder() {
        }

        public Builder withAggregationColumn(Column aggregationColumn) {
            this.aggregationColumn = aggregationColumn;
            return this;
        }

        public Builder withFactColumn(Column factColumn) {
            this.factColumn = factColumn;
            return this;
        }

        public AggregationForeignKeyMappingImpl build() {
            return new AggregationForeignKeyMappingImpl(this);
        }
    }
}
