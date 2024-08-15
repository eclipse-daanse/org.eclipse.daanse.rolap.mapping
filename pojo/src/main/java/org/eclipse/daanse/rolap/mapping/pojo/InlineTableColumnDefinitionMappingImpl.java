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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableColumnDefinitionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.TypeEnum;

public class InlineTableColumnDefinitionMappingImpl implements InlineTableColumnDefinitionMapping {

    private String name;

    private TypeEnum type;

    private InlineTableColumnDefinitionMappingImpl(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private TypeEnum type;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withType(TypeEnum type) {
            this.type = type;
            return this;
        }

        public InlineTableColumnDefinitionMappingImpl build() {
            return new InlineTableColumnDefinitionMappingImpl(this);
        }
    }
}
