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

public class AccessCubeGrantMappingImpl implements AccessCubeGrantMapping {

    private List<AccessDimensionGrantMappingImpl> dimensionGrants;

    private List<AccessHierarchyGrantMappingImpl> hierarchyGrants;

    private String access;

    private CubeMappingImpl cube;


    public List<AccessDimensionGrantMappingImpl> getDimensionGrants() {
        return dimensionGrants;
    }

    public void setDimensionGrants(List<AccessDimensionGrantMappingImpl> dimensionGrants) {
        this.dimensionGrants = dimensionGrants;
    }

    public List<AccessHierarchyGrantMappingImpl> getHierarchyGrants() {
        return hierarchyGrants;
    }

    public void setHierarchyGrants(List<AccessHierarchyGrantMappingImpl> hierarchyGrants) {
        this.hierarchyGrants = hierarchyGrants;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public CubeMappingImpl getCube() {
        return cube;
    }

    public void setCube(CubeMappingImpl cube) {
        this.cube = cube;
    }
}
