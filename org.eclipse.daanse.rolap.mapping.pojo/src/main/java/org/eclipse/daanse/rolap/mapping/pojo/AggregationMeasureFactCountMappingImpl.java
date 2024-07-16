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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;

public class AggregationMeasureFactCountMappingImpl implements AggregationMeasureFactCountMapping {

    private String column;

    private String factColumn;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getFactColumn() {
        return factColumn;
    }

    public void setFactColumn(String factColumn) {
        this.factColumn = factColumn;
    }
}
