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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rdb.structure.api.model.Table;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationNameMapping;

public class AggregationNameMappingImpl extends AggregationTableMappingImpl implements AggregationNameMapping {

    private String approxRowCount;

    private Table name;

    private AggregationNameMappingImpl(Builder builder) {
        this.approxRowCount = builder.approxRowCount;
        this.name = builder.name;
        super.setAggregationFactCount(builder.aggregationFactCount);
        super.setAggregationIgnoreColumns(builder.aggregationIgnoreColumns);
        super.setAggregationForeignKeys(builder.aggregationForeignKeys);
        super.setAggregationMeasures(builder.aggregationMeasures);
        super.setAggregationLevels(builder.aggregationLevels);
        super.setAggregationMeasureFactCounts(builder.aggregationMeasureFactCounts);
        super.setIgnorecase(builder.ignorecase);
        super.setId(builder.id);
    }

    public String getApproxRowCount() {
        return approxRowCount;
    }

    public void setApproxRowCount(String approxRowCount) {
        this.approxRowCount = approxRowCount;
    }

    public Table getName() {
        return name;
    }

    public void setName(Table name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String approxRowCount;
        private Table name;
        private AggregationColumnNameMappingImpl aggregationFactCount;
        private List<AggregationColumnNameMappingImpl> aggregationIgnoreColumns = new ArrayList<>();
        private List<AggregationForeignKeyMappingImpl> aggregationForeignKeys = new ArrayList<>();
        private List<AggregationMeasureMappingImpl> aggregationMeasures = new ArrayList<>();
        private List<AggregationLevelMappingImpl> aggregationLevels = new ArrayList<>();
        private List<AggregationMeasureFactCountMappingImpl> aggregationMeasureFactCounts = new ArrayList<>();
        private boolean ignorecase;
        private String id;

        private Builder() {
        }

        public Builder withApproxRowCount(String approxRowCount) {
            this.approxRowCount = approxRowCount;
            return this;
        }

        public Builder withName(Table name) {
            this.name = name;
            return this;
        }

        public Builder withAggregationFactCount(AggregationColumnNameMappingImpl aggregationFactCount) {
            this.aggregationFactCount = aggregationFactCount;
            return this;
        }

        public Builder withAggregationIgnoreColumns(List<AggregationColumnNameMappingImpl> aggregationIgnoreColumns) {
            this.aggregationIgnoreColumns = aggregationIgnoreColumns;
            return this;
        }

        public Builder withAggregationForeignKeys(List<AggregationForeignKeyMappingImpl> aggregationForeignKeys) {
            this.aggregationForeignKeys = aggregationForeignKeys;
            return this;
        }

        public Builder withAggregationMeasures(List<AggregationMeasureMappingImpl> aggregationMeasures) {
            this.aggregationMeasures = aggregationMeasures;
            return this;
        }

        public Builder withAggregationLevels(List<AggregationLevelMappingImpl> aggregationLevels) {
            this.aggregationLevels = aggregationLevels;
            return this;
        }

        public Builder withAggregationMeasureFactCounts(
                List<AggregationMeasureFactCountMappingImpl> aggregationMeasureFactCounts) {
            this.aggregationMeasureFactCounts = aggregationMeasureFactCounts;
            return this;
        }

        public Builder withIgnorecase(boolean ignorecase) {
            this.ignorecase = ignorecase;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public AggregationNameMappingImpl build() {
            return new AggregationNameMappingImpl(this);
        }
    }
}
