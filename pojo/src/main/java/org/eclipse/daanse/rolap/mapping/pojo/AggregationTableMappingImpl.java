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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;

public abstract class AggregationTableMappingImpl implements AggregationTableMapping {

    private AggregationColumnNameMappingImpl aggregationFactCount;

    private List<AggregationColumnNameMappingImpl> aggregationIgnoreColumns;

    private List<AggregationForeignKeyMappingImpl> aggregationForeignKeys;

    private List<AggregationMeasureMappingImpl> aggregationMeasures;

    private List<AggregationLevelMappingImpl> aggregationLevels;

    private List<AggregationMeasureFactCountMappingImpl> aggregationMeasureFactCounts;

    private boolean ignorecase;

    private String id;

    public AggregationColumnNameMappingImpl getAggregationFactCount() {
        return aggregationFactCount;
    }

    public void setAggregationFactCount(AggregationColumnNameMappingImpl aggregationFactCount) {
        this.aggregationFactCount = aggregationFactCount;
    }

    public List<AggregationColumnNameMappingImpl> getAggregationIgnoreColumns() {
        return aggregationIgnoreColumns;
    }

    public void setAggregationIgnoreColumns(List<AggregationColumnNameMappingImpl> aggregationIgnoreColumns) {
        this.aggregationIgnoreColumns = aggregationIgnoreColumns;
    }

    public List<AggregationForeignKeyMappingImpl> getAggregationForeignKeys() {
        return aggregationForeignKeys;
    }

    public void setAggregationForeignKeys(List<AggregationForeignKeyMappingImpl> aggregationForeignKeys) {
        this.aggregationForeignKeys = aggregationForeignKeys;
    }

    public List<AggregationMeasureMappingImpl> getAggregationMeasures() {
        return aggregationMeasures;
    }

    public void setAggregationMeasures(List<AggregationMeasureMappingImpl> aggregationMeasures) {
        this.aggregationMeasures = aggregationMeasures;
    }

    public List<AggregationLevelMappingImpl> getAggregationLevels() {
        return aggregationLevels;
    }

    public void setAggregationLevels(List<AggregationLevelMappingImpl> aggregationLevels) {
        this.aggregationLevels = aggregationLevels;
    }

    public List<AggregationMeasureFactCountMappingImpl> getAggregationMeasureFactCounts() {
        return aggregationMeasureFactCounts;
    }

    public void setAggregationMeasureFactCounts(
            List<AggregationMeasureFactCountMappingImpl> aggregationMeasureFactCounts) {
        this.aggregationMeasureFactCounts = aggregationMeasureFactCounts;
    }

    public boolean isIgnorecase() {
        return ignorecase;
    }

    public void setIgnorecase(boolean ignorecase) {
        this.ignorecase = ignorecase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
