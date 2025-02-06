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

import org.eclipse.daanse.rdb.structure.pojo.PhysicalTableImpl;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class TableQueryMappingImpl extends RelationalQueryMappingImpl implements TableQueryMapping {

    private SQLMappingImpl sqlWhereExpression;

    private List<AggregationExcludeMappingImpl> aggregationExcludes;

    private List<TableQueryOptimizationHintMappingImpl> optimizationHints;

    private PhysicalTableImpl table;

    private List<AggregationTableMappingImpl> aggregationTables;

    private DocumentationMappingImpl documentation;

    private String id;

    private TableQueryMappingImpl(Builder builder) {
        this.sqlWhereExpression = builder.sqlWhereExpression;
        this.aggregationExcludes = builder.aggregationExcludes;
        this.optimizationHints = builder.optimizationHints;
        this.table = builder.table;
        this.aggregationTables = builder.aggregationTables;
        this.documentation = builder.documentation;
        this.id = builder.id;
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

    public PhysicalTableImpl getTable() {
        return table;
    }

    public void setTable(PhysicalTableImpl table) {
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

    @Override
    public DocumentationMapping getDocumentation() {
        return documentation;
    }

    public void setDocumentation(DocumentationMappingImpl documentation) {
        this.documentation = documentation;
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
        private PhysicalTableImpl table;
        private List<AggregationTableMappingImpl> aggregationTables = new ArrayList<>();
        private String alias;
        private DocumentationMappingImpl documentation;
        private String id;

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

        public Builder withTable(PhysicalTableImpl table) {
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

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public TableQueryMappingImpl build() {
            return new TableQueryMappingImpl(this);
        }
    }

}
