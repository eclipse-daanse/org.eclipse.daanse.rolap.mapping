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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;

public class CalculatedMemberPropertyMappingImpl extends AbstractElementMappingImpl
        implements CalculatedMemberPropertyMapping {

    private String expression;

    private String value;

    private CalculatedMemberPropertyMappingImpl(Builder builder) {
        this.expression = builder.expression;
        this.value = builder.value;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String expression;
        private String value;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withExpression(String expression) {
            this.expression = expression;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
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

        public CalculatedMemberPropertyMappingImpl build() {
            return new CalculatedMemberPropertyMappingImpl(this);
        }
    }
}
