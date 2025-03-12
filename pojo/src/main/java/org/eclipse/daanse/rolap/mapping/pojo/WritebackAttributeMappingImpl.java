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

import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;

public class WritebackAttributeMappingImpl implements WritebackAttributeMapping {

    private PhysicalColumnMappingImpl column;

    private DimensionConnectorMappingImpl dimensionConnector;

    private WritebackAttributeMappingImpl(Builder builder) {
        this.column = builder.column;
        this.dimensionConnector = builder.dimensionConnector;
    }

    public PhysicalColumnMappingImpl getColumn() {
        return column;
    }

    public void setColumn(PhysicalColumnMappingImpl column) {
        this.column = column;
    }

    public DimensionConnectorMappingImpl getDimensionConnector() {
        return dimensionConnector;
    }

    public void setDimensionConnector(DimensionConnectorMappingImpl dimensionConnector) {
        this.dimensionConnector = dimensionConnector;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PhysicalColumnMappingImpl column;
        private DimensionConnectorMappingImpl dimensionConnector;

        private Builder() {
        }

        public Builder withColumn(PhysicalColumnMappingImpl column) {
            this.column = column;
            return this;
        }

        public Builder withDimensionConnector(DimensionConnectorMappingImpl dimensionConnector) {
            this.dimensionConnector = dimensionConnector;
            return this;
        }

        public WritebackAttributeMappingImpl build() {
            return new WritebackAttributeMappingImpl(this);
        }
    }
}
