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

import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;

public class DrillThroughAttributeMappingImpl implements DrillThroughAttributeMapping {

    private DimensionMapping dimension;

    private HierarchyMapping hierarchy;

    private LevelMapping level;

    private String property;

    public DimensionMapping getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMapping dimension) {
        this.dimension = dimension;
    }

    public HierarchyMapping getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMapping hierarchy) {
        this.hierarchy = hierarchy;
    }

    public LevelMapping getLevel() {
        return level;
    }

    public void setLevel(LevelMapping level) {
        this.level = level;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
