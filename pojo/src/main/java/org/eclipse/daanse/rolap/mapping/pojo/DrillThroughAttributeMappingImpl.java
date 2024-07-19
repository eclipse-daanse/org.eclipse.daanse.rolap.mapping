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

import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;

public class DrillThroughAttributeMappingImpl implements DrillThroughAttributeMapping {

    private DimensionMappingImpl dimension;

    private HierarchyMappingImpl hierarchy;

    private LevelMappingImpl level;

    private String property;

    private DrillThroughAttributeMappingImpl(Builder builder) {
        this.dimension = builder.dimension;
        this.hierarchy = builder.hierarchy;
        this.level = builder.level;
        this.property = builder.property;
    }

    public DimensionMappingImpl getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMappingImpl dimension) {
        this.dimension = dimension;
    }

    public HierarchyMappingImpl getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMappingImpl hierarchy) {
        this.hierarchy = hierarchy;
    }

    public LevelMappingImpl getLevel() {
        return level;
    }

    public void setLevel(LevelMappingImpl level) {
        this.level = level;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private DimensionMappingImpl dimension;
        private HierarchyMappingImpl hierarchy;
        private LevelMappingImpl level;
        private String property;

        private Builder() {
        }

        public Builder withDimension(DimensionMappingImpl dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder withHierarchy(HierarchyMappingImpl hierarchy) {
            this.hierarchy = hierarchy;
            return this;
        }

        public Builder withLevel(LevelMappingImpl level) {
            this.level = level;
            return this;
        }

        public Builder withProperty(String property) {
            this.property = property;
            return this;
        }

        public DrillThroughAttributeMappingImpl build() {
            return new DrillThroughAttributeMappingImpl(this);
        }
    }
}
