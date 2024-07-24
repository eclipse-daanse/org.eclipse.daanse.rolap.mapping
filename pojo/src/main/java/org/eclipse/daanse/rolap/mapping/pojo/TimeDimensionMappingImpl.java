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

import org.eclipse.daanse.rolap.mapping.api.model.TimeDimensionMapping;

public class TimeDimensionMappingImpl extends DimensionMappingImpl implements TimeDimensionMapping {

    private TimeDimensionMappingImpl(Builder builder) {
        super.setHierarchies(builder.hierarchies);
        super.setUsagePrefix(builder.usagePrefix);
        super.setVisible(builder.visible);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<HierarchyMappingImpl> hierarchies = new ArrayList<>();
        private String usagePrefix;
        private boolean visible;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withHierarchies(List<HierarchyMappingImpl> hierarchies) {
            this.hierarchies = hierarchies;
            return this;
        }

        public Builder withUsagePrefix(String usagePrefix) {
            this.usagePrefix = usagePrefix;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
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

        public TimeDimensionMappingImpl build() {
            return new TimeDimensionMappingImpl(this);
        }
    }
}
