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

import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;

public class DimensionConnectorMappingImpl implements DimensionConnectorMapping {

    private String foreignKey;

    private LevelMapping level;

    private String usagePrefix;

    private boolean visible;

    private DimensionMapping dimension;

    private String overrideDimensionName;

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public LevelMapping getLevel() {
        return level;
    }

    public void setLevel(LevelMapping level) {
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

    public DimensionMapping getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMapping dimension) {
        this.dimension = dimension;
    }

    public String getOverrideDimensionName() {
        return overrideDimensionName;
    }

    public void setOverrideDimensionName(String overrideDimensionName) {
        this.overrideDimensionName = overrideDimensionName;
    }
}
