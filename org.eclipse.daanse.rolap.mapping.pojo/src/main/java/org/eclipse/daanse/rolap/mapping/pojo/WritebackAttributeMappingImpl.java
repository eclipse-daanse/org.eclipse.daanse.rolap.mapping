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

import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;

public class WritebackAttributeMappingImpl implements WritebackAttributeMapping{

    private String column;

    private DimensionMapping dimension;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public DimensionMapping getDimension() {
        return dimension;
    }

    public void setDimension(DimensionMapping dimension) {
        this.dimension = dimension;
    }
}
