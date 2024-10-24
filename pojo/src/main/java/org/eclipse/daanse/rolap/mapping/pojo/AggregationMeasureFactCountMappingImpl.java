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
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;

public class AggregationMeasureFactCountMappingImpl implements AggregationMeasureFactCountMapping {

    private Column column;

    private Column factColumn;

    private AggregationMeasureFactCountMappingImpl(Builder builder) {
        this.column = builder.column;
        this.factColumn = builder.factColumn;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
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
        private Column column;
        private Column factColumn;

        private Builder() {
        }

        public Builder withColumn(Column column) {
            this.column = column;
            return this;
        }

        public Builder withFactColumn(Column factColumn) {
            this.factColumn = factColumn;
            return this;
        }

        public AggregationMeasureFactCountMappingImpl build() {
            return new AggregationMeasureFactCountMappingImpl(this);
        }
    }
}
