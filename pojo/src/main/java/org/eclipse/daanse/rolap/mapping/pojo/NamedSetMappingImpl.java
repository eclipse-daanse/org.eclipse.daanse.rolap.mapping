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

import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;

public class NamedSetMappingImpl extends AbstractElementMappingImpl implements NamedSetMapping {

    private String displayFolder;

    private String formula;

    private NamedSetMappingImpl(Builder builder) {
        this.displayFolder = builder.displayFolder;
        this.formula = builder.formula;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String displayFolder;
        private String formula;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withFormula(String formula) {
            this.formula = formula;
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

        public NamedSetMappingImpl build() {
            return new NamedSetMappingImpl(this);
        }
    }
}
