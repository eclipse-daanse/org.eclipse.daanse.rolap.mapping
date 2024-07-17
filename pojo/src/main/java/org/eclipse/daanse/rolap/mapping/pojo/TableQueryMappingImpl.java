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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class TableQueryMappingImpl extends RelationalQueryMappingImpl implements TableQueryMapping {

    private SQLMappingImpl sqlWhereExpression;

    private List<? extends AggregationExcludeMapping> aggregationExcludes;

    private List<? extends TableQueryOptimizationHintMappingImpl> optimizationHints;

    private String name;

    private String schema;

    private List<? extends AggregationTableMapping> aggregationTables;

    public SQLMappingImpl getSqlWhereExpression() {
        return sqlWhereExpression;
    }

    public void setSqlWhereExpression(SQLMappingImpl sqlWhereExpression) {
        this.sqlWhereExpression = sqlWhereExpression;
    }

    public List<? extends AggregationExcludeMapping> getAggregationExcludes() {
        return aggregationExcludes;
    }

    public void setAggregationExcludes(List<? extends AggregationExcludeMapping> aggregationExcludes) {
        this.aggregationExcludes = aggregationExcludes;
    }

    public List<? extends TableQueryOptimizationHintMappingImpl> getOptimizationHints() {
        return optimizationHints;
    }

    public void setOptimizationHints(List<? extends TableQueryOptimizationHintMappingImpl> optimizationHints) {
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

    public List<? extends AggregationTableMapping> getAggregationTables() {
        return aggregationTables;
    }

    public void setAggregationTables(List<? extends AggregationTableMapping> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }
}
