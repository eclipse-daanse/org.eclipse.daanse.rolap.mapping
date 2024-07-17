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
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;

public class CubeConnectorMappingImpl implements CubeConnectorMapping {

    private CubeMapping cube;

    private boolean ignoreUnrelatedDimensions;

    public CubeMapping getCube() {
        return cube;
    }

    public void setCube(CubeMapping cube) {
        this.cube = cube;
    }

    public boolean isIgnoreUnrelatedDimensions() {
        return ignoreUnrelatedDimensions;
    }

    public void setIgnoreUnrelatedDimensions(boolean ignoreUnrelatedDimensions) {
        this.ignoreUnrelatedDimensions = ignoreUnrelatedDimensions;
    }
}
