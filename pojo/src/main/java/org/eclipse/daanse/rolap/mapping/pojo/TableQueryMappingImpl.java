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

import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class TableQueryMappingImpl extends RelationalQueryMappingImpl implements TableQueryMapping {

    private SQLMappingImpl sqlWhereExpression;

    private List<AggregationExcludeMappingImpl> aggregationExcludes;

    private List<TableQueryOptimizationHintMappingImpl> optimizationHints;

    private String name;

    private String schema;

    private List<AggregationTableMappingImpl> aggregationTables;

    private TableQueryMappingImpl(Builder builder) {
        this.sqlWhereExpression = builder.sqlWhereExpression;
        this.aggregationExcludes = builder.aggregationExcludes;
        this.optimizationHints = builder.optimizationHints;
        this.name = builder.name;
        this.schema = builder.schema;
        this.aggregationTables = builder.aggregationTables;
        super.setAlias(builder.alias);
    }

    public SQLMappingImpl getSqlWhereExpression() {
        return sqlWhereExpression;
    }

    public void setSqlWhereExpression(SQLMappingImpl sqlWhereExpression) {
        this.sqlWhereExpression = sqlWhereExpression;
    }

    public List<AggregationExcludeMappingImpl> getAggregationExcludes() {
        return aggregationExcludes;
    }

    public void setAggregationExcludes(List<AggregationExcludeMappingImpl> aggregationExcludes) {
        this.aggregationExcludes = aggregationExcludes;
    }

    public List<TableQueryOptimizationHintMappingImpl> getOptimizationHints() {
        return optimizationHints;
    }

    public void setOptimizationHints(List<TableQueryOptimizationHintMappingImpl> optimizationHints) {
        this.optimizationHints = optimizationHints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<AggregationTableMappingImpl> getAggregationTables() {
        return aggregationTables;
    }

    public void setAggregationTables(List<AggregationTableMappingImpl> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SQLMappingImpl sqlWhereExpression;
        private List<AggregationExcludeMappingImpl> aggregationExcludes = new ArrayList<>();
        private List<TableQueryOptimizationHintMappingImpl> optimizationHints = new ArrayList<>();
        private String name;
        private String schema;
        private List<AggregationTableMappingImpl> aggregationTables = new ArrayList<>();
        private String alias;

        private Builder() {
        }

        public Builder withSqlWhereExpression(SQLMappingImpl sqlWhereExpression) {
            this.sqlWhereExpression = sqlWhereExpression;
            return this;
        }

        public Builder withAggregationExcludes(List<AggregationExcludeMappingImpl> aggregationExcludes) {
            this.aggregationExcludes = aggregationExcludes;
            return this;
        }

        public Builder withOptimizationHints(List<TableQueryOptimizationHintMappingImpl> optimizationHints) {
            this.optimizationHints = optimizationHints;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSchema(String schema) {
            this.schema = schema;
            return this;
        }

        public Builder withAggregationTables(List<AggregationTableMappingImpl> aggregationTables) {
            this.aggregationTables = aggregationTables;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public TableQueryMappingImpl build() {
            return new TableQueryMappingImpl(this);
        }
    }
}
