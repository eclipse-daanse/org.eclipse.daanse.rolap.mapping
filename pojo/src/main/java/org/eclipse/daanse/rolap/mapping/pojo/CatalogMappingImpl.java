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

import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;

public class CatalogMappingImpl extends AbstractElementMappingImpl implements CatalogMapping {

    private List<SchemaMappingImpl> schemas;

    private CatalogMappingImpl(Builder builder) {
        this.schemas = builder.schemas;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<SchemaMappingImpl> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<SchemaMappingImpl> schemas) {
        this.schemas = schemas;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<SchemaMappingImpl> schemas = new ArrayList<>();
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withSchemas(List<SchemaMappingImpl> schemas) {
            this.schemas = schemas;
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

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public CatalogMappingImpl build() {
            return new CatalogMappingImpl(this);
        }
    }
}
