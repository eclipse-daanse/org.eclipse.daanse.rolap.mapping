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

import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;

public class CellFormatterMappingImpl extends FormatterMappingImpl implements CellFormatterMapping {

    private CellFormatterMappingImpl(Builder builder) {
        super.setRef(builder.ref);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String ref;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;

        private Builder() {
        }

        public Builder withRef(String ref) {
            this.ref = ref;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CellFormatterMappingImpl build() {
            return new CellFormatterMappingImpl(this);
        }
    }
}
