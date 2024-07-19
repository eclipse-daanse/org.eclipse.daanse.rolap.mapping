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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class TableQueryMappingImpl extends RelationalQueryMappingImpl implements TableQueryMapping {

    private SQLMappingImpl sqlWhereExpression;

    private List<AggregationExcludeMappingImpl> aggregationExcludes;

    private List<TableQueryOptimizationHintMappingImpl> optimizationHints;

    private String name;

    private String schema;

    private List<AggregationTableMappingImpl> aggregationTables;

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
}
