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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;

public class AggregationPatternMappingImpl extends AggregationTableMappingImpl implements AggregationPatternMapping {

    private String pattern;

    private List<AggregationExcludeMappingImpl> excludes;

    private AggregationPatternMappingImpl(Builder builder) {
        this.pattern = builder.pattern;
        this.excludes = builder.excludes;
        super.setAggregationFactCount(builder.aggregationFactCount);
        super.setAggregationIgnoreColumns(builder.aggregationIgnoreColumns);
        super.setAggregationForeignKeys(builder.aggregationForeignKeys);
        super.setAggregationMeasures(builder.aggregationMeasures);
        super.setAggregationLevels(builder.aggregationLevels);
        super.setAggregationMeasureFactCounts(builder.aggregationMeasureFactCounts);
        super.setIgnorecase(builder.ignorecase);
        super.setId(builder.id);
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<AggregationExcludeMappingImpl> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<AggregationExcludeMappingImpl> excludes) {
        this.excludes = excludes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String pattern;
        private List<AggregationExcludeMappingImpl> excludes = Collections.emptyList();
        private AggregationColumnNameMappingImpl aggregationFactCount;
        private List<AggregationColumnNameMappingImpl> aggregationIgnoreColumns = Collections.emptyList();
        private List<AggregationForeignKeyMappingImpl> aggregationForeignKeys = Collections.emptyList();
        private List<AggregationMeasureMappingImpl> aggregationMeasures = Collections.emptyList();
        private List<AggregationLevelMappingImpl> aggregationLevels = Collections.emptyList();
        private List<AggregationMeasureFactCountMappingImpl> aggregationMeasureFactCounts = Collections.emptyList();
        private boolean ignorecase;
        private String id;

        private Builder() {
        }

        public Builder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder withExcludes(List<AggregationExcludeMappingImpl> excludes) {
            this.excludes = excludes;
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

        public AggregationPatternMappingImpl build() {
            return new AggregationPatternMappingImpl(this);
        }
    }
}
