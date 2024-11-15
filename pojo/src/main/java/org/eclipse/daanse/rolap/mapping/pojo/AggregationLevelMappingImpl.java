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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;

public class AggregationLevelMappingImpl implements AggregationLevelMapping {

    private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties;

    private Column captionColumn;

    private boolean collapsed = true;

    private Column column;

    private String name;

    private Column nameColumn;

    private Column ordinalColumn;

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

    public Column getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn(Column captionColumn) {
        this.captionColumn = captionColumn;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Column getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(Column nameColumn) {
        this.nameColumn = nameColumn;
    }

    public Column getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn(Column ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties = new ArrayList<>();
        private Column captionColumn;
        private boolean collapsed = true;
        private Column column;
        private String name;
        private Column nameColumn;
        private Column ordinalColumn;

        private Builder() {
        }

        public Builder withAggregationLevelProperties(
                List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties) {
            this.aggregationLevelProperties = aggregationLevelProperties;
            return this;
        }

        public Builder withCaptionColumn(Column captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
            return this;
        }

        public Builder withColumn(Column column) {
            this.column = column;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNameColumn(Column nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withOrdinalColumn(Column ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public AggregationLevelMappingImpl build() {
            return new AggregationLevelMappingImpl(this);
        }
    }
}
