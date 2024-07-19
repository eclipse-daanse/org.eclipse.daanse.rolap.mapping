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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;

public class AggregationLevelMappingImpl implements AggregationLevelMapping {

    private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties;

    private String captionColumn;

    private boolean collapsed;

    private String column;

    private String name;

    private String nameColumn;

    private String ordinalColumn;

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

    public String getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn(String captionColumn) {
        this.captionColumn = captionColumn;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn(String ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties = Collections.emptyList();
        private String captionColumn;
        private boolean collapsed;
        private String column;
        private String name;
        private String nameColumn;
        private String ordinalColumn;

        private Builder() {
        }

        public Builder withAggregationLevelProperties(
                List<AggregationLevelPropertyMappingImpl> aggregationLevelProperties) {
            this.aggregationLevelProperties = aggregationLevelProperties;
            return this;
        }

        public Builder withCaptionColumn(String captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
            return this;
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNameColumn(String nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withOrdinalColumn(String ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public AggregationLevelMappingImpl build() {
            return new AggregationLevelMappingImpl(this);
        }
    }
}
