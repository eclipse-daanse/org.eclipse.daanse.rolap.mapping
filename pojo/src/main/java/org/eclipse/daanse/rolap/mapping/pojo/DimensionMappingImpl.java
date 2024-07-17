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

import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;

public class DimensionMappingImpl extends AbstractElementMappingImpl implements DimensionMapping {

    private List<? extends HierarchyMapping> hierarchies;

    private String usagePrefix;

    private boolean visible;

    public List<? extends HierarchyMapping> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<? extends HierarchyMapping> hierarchies) {
        this.hierarchies = hierarchies;
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
}
