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

import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;

public class TableQueryOptimizationHintMappingImpl implements TableQueryOptimizationHintMapping {

    private String value;

    private String type;

    private TableQueryOptimizationHintMappingImpl(Builder builder) {
        this.value = builder.value;
        this.type = builder.type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String value;
        private String type;

        private Builder() {
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public TableQueryOptimizationHintMappingImpl build() {
            return new TableQueryOptimizationHintMappingImpl(this);
        }
    }
}
