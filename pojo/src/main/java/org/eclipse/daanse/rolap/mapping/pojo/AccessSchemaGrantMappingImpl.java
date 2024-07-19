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

import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;

public class AccessSchemaGrantMappingImpl implements AccessSchemaGrantMapping {

    private List<AccessCubeGrantMappingImpl> cubeGrant;

    private String access;

    public List<AccessCubeGrantMappingImpl> getCubeGrant() {
        return cubeGrant;
    }

    public void setCubeGrant(List<AccessCubeGrantMappingImpl> cubeGrant) {
        this.cubeGrant = cubeGrant;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
