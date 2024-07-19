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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughActionMapping;

public class DrillThroughActionMappingImpl extends ActionMappingMappingImpl implements DrillThroughActionMapping {

    private List<DrillThroughAttributeMappingImpl> drillThroughAttribute;

    private List<DrillThroughMeasureMappingImpl> drillThroughMeasure;

    private boolean theDefault;

    private DrillThroughActionMappingImpl(Builder builder) {
        this.drillThroughAttribute = builder.drillThroughAttribute;
        this.drillThroughMeasure = builder.drillThroughMeasure;
        this.theDefault = builder.theDefault;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<DrillThroughAttributeMappingImpl> getDrillThroughAttribute() {
        return drillThroughAttribute;
    }

    public void setDrillThroughAttribute(List<DrillThroughAttributeMappingImpl> drillThroughAttribute) {
        this.drillThroughAttribute = drillThroughAttribute;
    }

    public List<DrillThroughMeasureMappingImpl> getDrillThroughMeasure() {
        return drillThroughMeasure;
    }

    public void setDrillThroughMeasure(List<DrillThroughMeasureMappingImpl> drillThroughMeasure) {
        this.drillThroughMeasure = drillThroughMeasure;
    }

    public boolean isDefault() {
        return theDefault;
    }

    public void setDefault(boolean theDefault) {
        this.theDefault = theDefault;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<DrillThroughAttributeMappingImpl> drillThroughAttribute = Collections.emptyList();
        private List<DrillThroughMeasureMappingImpl> drillThroughMeasure = Collections.emptyList();
        private boolean theDefault;
        private List<AnnotationMappingImpl> annotations = Collections.emptyList();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withDrillThroughAttribute(List<DrillThroughAttributeMappingImpl> drillThroughAttribute) {
            this.drillThroughAttribute = drillThroughAttribute;
            return this;
        }

        public Builder withDrillThroughMeasure(List<DrillThroughMeasureMappingImpl> drillThroughMeasure) {
            this.drillThroughMeasure = drillThroughMeasure;
            return this;
        }

        public Builder withTheDefault(boolean theDefault) {
            this.theDefault = theDefault;
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

        public DrillThroughActionMappingImpl build() {
            return new DrillThroughActionMappingImpl(this);
        }
    }
}
