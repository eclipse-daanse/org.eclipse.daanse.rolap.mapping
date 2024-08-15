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

import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;

public class CubeConnectorMappingImpl implements CubeConnectorMapping {

    private CubeMappingImpl cube;

    private boolean ignoreUnrelatedDimensions; //false by default

    private CubeConnectorMappingImpl(Builder builder) {
        this.cube = builder.cube;
        this.ignoreUnrelatedDimensions = builder.ignoreUnrelatedDimensions;
    }

    public CubeMappingImpl getCube() {
        return cube;
    }

    public void setCube(CubeMappingImpl cube) {
        this.cube = cube;
    }

    public boolean isIgnoreUnrelatedDimensions() {
        return ignoreUnrelatedDimensions;
    }

    public void setIgnoreUnrelatedDimensions(boolean ignoreUnrelatedDimensions) {
        this.ignoreUnrelatedDimensions = ignoreUnrelatedDimensions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CubeMappingImpl cube;
        private boolean ignoreUnrelatedDimensions; //false by default

        private Builder() {
        }

        public Builder withCube(CubeMappingImpl cube) {
            this.cube = cube;
            return this;
        }

        public Builder withIgnoreUnrelatedDimensions(boolean ignoreUnrelatedDimensions) {
            this.ignoreUnrelatedDimensions = ignoreUnrelatedDimensions;
            return this;
        }

        public CubeConnectorMappingImpl build() {
            return new CubeConnectorMappingImpl(this);
        }
    }
}
