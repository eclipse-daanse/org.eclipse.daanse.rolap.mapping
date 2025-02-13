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

    private SqlStatementMappingImpl sqlWhereExpression;

    private List<AggregationExcludeMappingImpl> aggregationExcludes;

    private List<TableQueryOptimizationHintMappingImpl> optimizationHints;

    private PhysicalTableMappingImpl table;

    private List<AggregationTableMappingImpl> aggregationTables;

    private String id;

    private TableQueryMappingImpl(Builder builder) {
        this.sqlWhereExpression = builder.sqlWhereExpression;
        this.aggregationExcludes = builder.aggregationExcludes;
        this.optimizationHints = builder.optimizationHints;
        this.table = builder.table;
        this.aggregationTables = builder.aggregationTables;
        this.id = builder.id;
        super.setAlias(builder.alias);
    }

    public SqlStatementMappingImpl getSqlWhereExpression() {
        return sqlWhereExpression;
    }

    public void setSqlWhereExpression(SqlStatementMappingImpl sqlWhereExpression) {
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

    public PhysicalTableMappingImpl getTable() {
        return table;
    }

    public void setTable(PhysicalTableMappingImpl table) {
        this.table = table;
    }

    public List<AggregationTableMappingImpl> getAggregationTables() {
        return aggregationTables;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAggregationTables(List<AggregationTableMappingImpl> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SqlStatementMappingImpl sqlWhereExpression;
        private List<AggregationExcludeMappingImpl> aggregationExcludes = new ArrayList<>();
        private List<TableQueryOptimizationHintMappingImpl> optimizationHints = new ArrayList<>();
        private PhysicalTableMappingImpl table;
        private List<AggregationTableMappingImpl> aggregationTables = new ArrayList<>();
        private String alias;
        private String id;

        private Builder() {
        }

        public Builder withSqlWhereExpression(SqlStatementMappingImpl sqlWhereExpression) {
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

        public Builder withTable(PhysicalTableMappingImpl table) {
            this.table = table;
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

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public TableQueryMappingImpl build() {
            return new TableQueryMappingImpl(this);
        }
    }

}
