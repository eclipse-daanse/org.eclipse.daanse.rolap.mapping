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

import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class ParameterMappingImpl implements ParameterMapping {

    private String defaultValue;

    private String description;

    private boolean modifiable; //false by default

    private String name;

    private InternalDataType type;

    private ParameterMappingImpl(Builder builder) {
        this.defaultValue = builder.defaultValue;
        this.description = builder.description;
        this.modifiable = builder.modifiable;
        this.name = builder.name;
        this.type = builder.type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InternalDataType getType() {
        return type;
    }

    public void setDataType(InternalDataType dataType) {
        this.type = dataType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String defaultValue;
        private String description;
        private boolean modifiable = true;
        private String name;
        private InternalDataType type;

        private Builder() {
        }

        public Builder withDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withModifiable(boolean modifiable) {
            this.modifiable = modifiable;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withType(InternalDataType type) {
            this.type = type;
            return this;
        }

        public ParameterMappingImpl build() {
            return new ParameterMappingImpl(this);
        }
    }
}
