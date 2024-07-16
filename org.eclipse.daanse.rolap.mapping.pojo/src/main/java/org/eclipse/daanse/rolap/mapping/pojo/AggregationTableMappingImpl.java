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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationColumnNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;

public class AggregationTableMappingImpl implements AggregationTableMapping {

    AggregationColumnNameMapping aggregationFactCount;

    List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns;

    List<? extends AggregationForeignKeyMapping> aggregationForeignKeys;

    List<? extends AggregationMeasureMapping> aggregationMeasures;

    List<? extends AggregationLevelMapping> aggregationLevels;

    List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts;

    boolean ignorecase;

    String id;

    public AggregationColumnNameMapping getAggregationFactCount() {
        return aggregationFactCount;
    }

    public void setAggregationFactCount(AggregationColumnNameMapping aggregationFactCount) {
        this.aggregationFactCount = aggregationFactCount;
    }

    public List<? extends AggregationColumnNameMapping> getAggregationIgnoreColumns() {
        return aggregationIgnoreColumns;
    }

    public void setAggregationIgnoreColumns(List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns) {
        this.aggregationIgnoreColumns = aggregationIgnoreColumns;
    }

    public List<? extends AggregationForeignKeyMapping> getAggregationForeignKeys() {
        return aggregationForeignKeys;
    }

    public void setAggregationForeignKeys(List<? extends AggregationForeignKeyMapping> aggregationForeignKeys) {
        this.aggregationForeignKeys = aggregationForeignKeys;
    }

    public List<? extends AggregationMeasureMapping> getAggregationMeasures() {
        return aggregationMeasures;
    }

    public void setAggregationMeasures(List<? extends AggregationMeasureMapping> aggregationMeasures) {
        this.aggregationMeasures = aggregationMeasures;
    }

    public List<? extends AggregationLevelMapping> getAggregationLevels() {
        return aggregationLevels;
    }

    public void setAggregationLevels(List<? extends AggregationLevelMapping> aggregationLevels) {
        this.aggregationLevels = aggregationLevels;
    }

    public List<? extends AggregationMeasureFactCountMapping> getAggregationMeasureFactCounts() {
        return aggregationMeasureFactCounts;
    }

    public void setAggregationMeasureFactCounts(List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts) {
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
