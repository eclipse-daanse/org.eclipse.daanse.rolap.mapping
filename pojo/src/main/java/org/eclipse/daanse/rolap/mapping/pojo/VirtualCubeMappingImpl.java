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

import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;

public class VirtualCubeMappingImpl extends CubeMappingImpl implements VirtualCubeMapping {

    private List<? extends CubeConnectorMapping> cubeUsages;

    public List<? extends CubeConnectorMapping> getCubeUsages() {
        return cubeUsages;
    }

    public void setCubeUsages(List<? extends CubeConnectorMapping> cubeUsages) {
        this.cubeUsages = cubeUsages;
    }
}
