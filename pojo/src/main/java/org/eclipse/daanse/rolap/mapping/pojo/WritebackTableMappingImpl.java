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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;

public class WritebackTableMappingImpl implements WritebackTableMapping {

    private List<WritebackAttributeMappingImpl> writebackAttribute;

    private List<WritebackMeasureMappingImpl> writebackMeasure;

    private String name;

    private String schema;

    private WritebackTableMappingImpl(Builder builder) {
        this.writebackAttribute = builder.writebackAttribute;
        this.writebackMeasure = builder.writebackMeasure;
        this.name = builder.name;
        this.schema = builder.schema;
    }

    public List<WritebackAttributeMappingImpl> getWritebackAttribute() {
        return writebackAttribute;
    }

    public void setWritebackAttribute(List<WritebackAttributeMappingImpl> writebackAttribute) {
        this.writebackAttribute = writebackAttribute;
    }

    public List<WritebackMeasureMappingImpl> getWritebackMeasure() {
        return writebackMeasure;
    }

    public void setWritebackMeasure(List<WritebackMeasureMappingImpl> writebackMeasure) {
        this.writebackMeasure = writebackMeasure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<WritebackAttributeMappingImpl> writebackAttribute = new ArrayList<>();
        private List<WritebackMeasureMappingImpl> writebackMeasure = new ArrayList<>();
        private String name;
        private String schema;

        private Builder() {
        }

        public Builder withWritebackAttribute(List<WritebackAttributeMappingImpl> writebackAttribute) {
            this.writebackAttribute = writebackAttribute;
            return this;
        }

        public Builder withWritebackMeasure(List<WritebackMeasureMappingImpl> writebackMeasure) {
            this.writebackMeasure = writebackMeasure;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSchema(String schema) {
            this.schema = schema;
            return this;
        }

        public WritebackTableMappingImpl build() {
            return new WritebackTableMappingImpl(this);
        }
    }
}
