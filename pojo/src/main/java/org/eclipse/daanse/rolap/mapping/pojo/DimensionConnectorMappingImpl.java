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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;

public class DimensionConnectorMappingImpl implements DimensionConnectorMapping {

    private Column foreignKey;

    private LevelMappingImpl level;

    private String usagePrefix;

    private boolean visible = true;

    private DimensionMappingImpl dimension;

    private String overrideDimensionName;

    private PhysicalCubeMappingImpl physicalCube;

    private DimensionConnectorMappingImpl(Builder builder) {
        this.foreignKey = builder.foreignKey;
        this.level = builder.level;
        this.usagePrefix = builder.usagePrefix;
        this.visible = builder.visible;
        this.dimension = builder.dimension;
        this.overrideDimensionName = builder.overrideDimensionName;
        this.physicalCube = builder.physicalCube;
}

    public Column getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Column foreignKey) {
        this.foreignKey = foreignKey;
    }

    public LevelMappingImpl getLevel() {
        return level;
    }

    public void setLevel(LevelMappingImpl level) {
        this.level = level;
    }

    public String getUsagePrefix() {
        return usagePrefix;
    }

    public void setUsagePrefix(String usagePrefix) {
        this.usagePrefix = usagePrefix;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public DimensionMappingImpl getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMappingImpl dimension) {
        this.dimension = dimension;
    }

    public String getOverrideDimensionName() {
        return overrideDimensionName;
    }

    public void setOverrideDimensionName(String overrideDimensionName) {
        this.overrideDimensionName = overrideDimensionName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PhysicalCubeMappingImpl getPhysicalCube() {
        return physicalCube;
    }

    public void setPhysicalCube(PhysicalCubeMappingImpl physicalCube) {
        this.physicalCube = physicalCube;
    }

    public static final class Builder {
        private Column foreignKey;
        private LevelMappingImpl level;
        private String usagePrefix;
        private boolean visible = true;
        private DimensionMappingImpl dimension;
        private String overrideDimensionName;
        private PhysicalCubeMappingImpl physicalCube;

        private Builder() {
        }

        public Builder withForeignKey(Column foreignKey) {
            this.foreignKey = foreignKey;
            return this;
        }

        public Builder withLevel(LevelMappingImpl level) {
            this.level = level;
            return this;
        }

        public Builder withUsagePrefix(String usagePrefix) {
            this.usagePrefix = usagePrefix;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withDimension(DimensionMappingImpl dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder withOverrideDimensionName(String overrideDimensionName) {
            this.overrideDimensionName = overrideDimensionName;
            return this;
        }

        public Builder withPhysicalCube(PhysicalCubeMappingImpl physicalCube) {
            this.physicalCube = physicalCube;
            return this;
        }

        public DimensionConnectorMappingImpl build() {
            return new DimensionConnectorMappingImpl(this);
        }
    }
}
