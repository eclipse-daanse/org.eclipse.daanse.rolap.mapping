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

import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessSchema;

public class AccessSchemaGrantMappingImpl implements AccessSchemaGrantMapping {

    private List<AccessCubeGrantMappingImpl> cubeGrant;

    private AccessSchema access;

    private AccessSchemaGrantMappingImpl(Builder builder) {
        this.cubeGrant = builder.cubeGrant;
        this.access = builder.access;
    }

    public List<AccessCubeGrantMappingImpl> getCubeGrants() {
        return cubeGrant;
    }

    public void setCubeGrant(List<AccessCubeGrantMappingImpl> cubeGrant) {
        this.cubeGrant = cubeGrant;
    }

    public AccessSchema getAccess() {
        return access;
    }

    public void setAccess(AccessSchema access) {
        this.access = access;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AccessCubeGrantMappingImpl> cubeGrant = new ArrayList<>();
        private AccessSchema access;

        private Builder() {
        }

        public Builder withCubeGrant(List<AccessCubeGrantMappingImpl> cubeGrant) {
            this.cubeGrant = cubeGrant;
            return this;
        }

        public Builder withAccess(AccessSchema access) {
            this.access = access;
            return this;
        }

        public AccessSchemaGrantMappingImpl build() {
            return new AccessSchemaGrantMappingImpl(this);
        }
    }
}
