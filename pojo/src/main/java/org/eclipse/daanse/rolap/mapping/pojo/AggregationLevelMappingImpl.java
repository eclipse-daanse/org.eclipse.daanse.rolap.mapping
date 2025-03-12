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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;

public class AggregationLevelMappingImpl implements AggregationLevelMapping {

    private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties;

    private PhysicalColumnMappingImpl captionColumn;

    private boolean collapsed = true;

    private PhysicalColumnMappingImpl column;

    private String name;

    private PhysicalColumnMappingImpl nameColumn;

    private PhysicalColumnMappingImpl ordinalColumn;

    private AggregationLevelMappingImpl(Builder builder) {
        this.aggregationLevelProperties = builder.aggregationLevelProperties;
        this.captionColumn = builder.captionColumn;
        this.collapsed = builder.collapsed;
        this.column = builder.column;
        this.name = builder.name;
        this.nameColumn = builder.nameColumn;
        this.ordinalColumn = builder.ordinalColumn;
    }

    public List<AggregationLevelPropertyMappingImpl> getAggregationLevelProperties() {
        return aggregationLevelProperties;
    }

    public void setAggregationLevelProperties(List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties) {
        this.aggregationLevelProperties = aggregationLevelProperties;
    }

    public PhysicalColumnMappingImpl getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn(PhysicalColumnMappingImpl captionColumn) {
        this.captionColumn = captionColumn;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public PhysicalColumnMappingImpl getColumn() {
        return column;
    }

    public void setColumn(PhysicalColumnMappingImpl column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhysicalColumnMappingImpl getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn (PhysicalColumnMappingImpl nameColumn) {
        this.nameColumn = nameColumn;
    }

    public PhysicalColumnMappingImpl getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn (PhysicalColumnMappingImpl ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties = new ArrayList<>();
        private PhysicalColumnMappingImpl captionColumn;
        private boolean collapsed = true;
        private PhysicalColumnMappingImpl column;
        private String name;
        private PhysicalColumnMappingImpl nameColumn;
        private PhysicalColumnMappingImpl ordinalColumn;

        private Builder() {
        }

        public Builder withAggregationLevelProperties(
                List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties) {
            this.aggregationLevelProperties = aggregationLevelProperties;
            return this;
        }

        public Builder withCaptionColumn (PhysicalColumnMappingImpl captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
            return this;
        }

        public Builder withColumn (PhysicalColumnMappingImpl column) {
            this.column = column;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNameColumn (PhysicalColumnMappingImpl nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withOrdinalColumn (PhysicalColumnMappingImpl ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public AggregationLevelMappingImpl build() {
            return new AggregationLevelMappingImpl(this);
        }
    }
}
