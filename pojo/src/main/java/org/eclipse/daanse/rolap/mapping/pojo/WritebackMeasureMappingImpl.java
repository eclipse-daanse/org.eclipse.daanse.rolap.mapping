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

import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;

public class WritebackMeasureMappingImpl implements WritebackMeasureMapping {

    private String column;

    private String name;

    private WritebackMeasureMappingImpl(Builder builder) {
        this.column = builder.column;
        this.name = builder.name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String column;
        private String name;

        private Builder() {
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public WritebackMeasureMappingImpl build() {
            return new WritebackMeasureMappingImpl(this);
        }
    }
}
