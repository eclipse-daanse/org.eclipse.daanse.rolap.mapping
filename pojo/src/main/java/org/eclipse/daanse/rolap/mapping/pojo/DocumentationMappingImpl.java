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

import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;

public class DocumentationMappingImpl implements DocumentationMapping {

    private String value;

    private DocumentationMappingImpl(Builder builder) {
        this.value = builder.value;
    }

    public DocumentationMappingImpl(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String value;

        private Builder() {
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public DocumentationMappingImpl build() {
            return new DocumentationMappingImpl(this);
        }
    }
}
