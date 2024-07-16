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

import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;

public class AccessCubeGrantMappingImpl implements AccessCubeGrantMapping {

    private List<? extends AccessDimensionGrantMapping> dimensionGrants;

    private List<? extends AccessHierarchyGrantMapping> hierarchyGrants;

    private String access;

    private CubeMapping cube;


    public List<? extends AccessDimensionGrantMapping> getDimensionGrants() {
        return dimensionGrants;
    }

    public void setDimensionGrants(List<? extends AccessDimensionGrantMapping> dimensionGrants) {
        this.dimensionGrants = dimensionGrants;
    }

    public List<? extends AccessHierarchyGrantMapping> getHierarchyGrants() {
        return hierarchyGrants;
    }

    public void setHierarchyGrants(List<? extends AccessHierarchyGrantMapping> hierarchyGrants) {
        this.hierarchyGrants = hierarchyGrants;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public CubeMapping getCube() {
        return cube;
    }

    public void setCube(CubeMapping cube) {
        this.cube = cube;
    }
}
