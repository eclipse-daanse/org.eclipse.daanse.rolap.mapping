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

import org.eclipse.daanse.rolap.mapping.api.model.AccessCatalogGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCatalog;

public class AccessCatalogGrantMappingImpl implements AccessCatalogGrantMapping {

    private List<AccessCubeGrantMappingImpl> cubeGrant;

    private AccessCatalog access;

    private AccessCatalogGrantMappingImpl(Builder builder) {
        this.cubeGrant = builder.cubeGrant;
        this.access = builder.access;
    }

    public List<AccessCubeGrantMappingImpl> getCubeGrants() {
        return cubeGrant;
    }

    public void setCubeGrant(List<AccessCubeGrantMappingImpl> cubeGrant) {
        this.cubeGrant = cubeGrant;
    }

    public AccessCatalog getAccess() {
        return access;
    }

    public void setAccess(AccessCatalog access) {
        this.access = access;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AccessCubeGrantMappingImpl> cubeGrant = new ArrayList<>();
        private AccessCatalog access;

        private Builder() {
        }

        public Builder withCubeGrant(List<AccessCubeGrantMappingImpl> cubeGrant) {
            this.cubeGrant = cubeGrant;
            return this;
        }

        public Builder withAccess(AccessCatalog access) {
            this.access = access;
            return this;
        }

        public AccessCatalogGrantMappingImpl build() {
            return new AccessCatalogGrantMappingImpl(this);
        }
    }
}
