/* Copyright (c) 2024 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;

public class AnnotationMappingImpl implements AnnotationMapping {

    private String value;

    private String name;

    private AnnotationMappingImpl(Builder builder) {
        this.value = builder.value;
        this.name = builder.name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        private String value;
        private String name;

        private Builder() {
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public AnnotationMappingImpl build() {
            return new AnnotationMappingImpl(this);
        }
    }
}
