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

import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;

public class AccessCubeGrantMappingImpl implements AccessCubeGrantMapping {

    private List<AccessDimensionGrantMappingImpl> dimensionGrants;

    private List<AccessHierarchyGrantMappingImpl> hierarchyGrants;

    private AccessCube access;

    private CubeMappingImpl cube;

    private AccessCubeGrantMappingImpl(Builder builder) {
        this.dimensionGrants = builder.dimensionGrants;
        this.hierarchyGrants = builder.hierarchyGrants;
        this.access = builder.access;
        this.cube = builder.cube;
    }

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

    public AccessCube getAccess() {
        return access;
    }

    public void setAccess(AccessCube access) {
        this.access = access;
    }

    public CubeMappingImpl getCube() {
        return cube;
    }

    public void setCube(CubeMappingImpl cube) {
        this.cube = cube;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AccessDimensionGrantMappingImpl> dimensionGrants = new ArrayList<>();
        private List<AccessHierarchyGrantMappingImpl> hierarchyGrants = new ArrayList<>();
        private AccessCube access;
        private CubeMappingImpl cube;

        private Builder() {
        }

        public Builder withDimensionGrants(List<AccessDimensionGrantMappingImpl> dimensionGrants) {
            this.dimensionGrants = dimensionGrants;
            return this;
        }

        public Builder withHierarchyGrants(List<AccessHierarchyGrantMappingImpl> hierarchyGrants) {
            this.hierarchyGrants = hierarchyGrants;
            return this;
        }

        public Builder withAccess(AccessCube access) {
            this.access = access;
            return this;
        }

        public Builder withCube(CubeMappingImpl cube) {
            this.cube = cube;
            return this;
        }

        public AccessCubeGrantMappingImpl build() {
            return new AccessCubeGrantMappingImpl(this);
        }
    }
}
