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

import org.eclipse.daanse.rolap.mapping.api.model.RowValueMapping;

public class RowValueMappingImpl implements RowValueMapping {

    private PhysicalColumnMappingImpl column;
    private String value;

    public RowValueMappingImpl(Builder builder) {
        this.column = builder.column;
        this.value = builder.value;
    }

    @Override
    public PhysicalColumnMappingImpl getColumn() {
        return column;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setColumn (PhysicalColumnMappingImpl column) {
        this.column = column;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private PhysicalColumnMappingImpl column;
        private String value;

        public Builder withColumn (PhysicalColumnMappingImpl column) {
            this.column = column;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public RowValueMappingImpl build() {
            return new RowValueMappingImpl(this);
        }
    }

}
