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

import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;

public class AccessDimensionGrantMappingImpl implements AccessDimensionGrantMapping {

    private String access;

    private DimensionMapping dimension;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public DimensionMapping getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMapping dimension) {
        this.dimension = dimension;
    }
}
