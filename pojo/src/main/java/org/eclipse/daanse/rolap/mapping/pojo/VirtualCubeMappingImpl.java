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

import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;

public class VirtualCubeMappingImpl extends CubeMappingImpl implements VirtualCubeMapping {

    private List<CubeConnectorMappingImpl> cubeUsages;

    private VirtualCubeMappingImpl(Builder builder) {
        this.cubeUsages = builder.cubeUsages;
        super.setDimensionConnectors(builder.dimensionConnectors);
        super.setCalculatedMembers(builder.calculatedMembers);
        super.setNamedSets(builder.namedSets);
        super.setKpis(builder.kpis);
        super.setDefaultMeasure(builder.defaultMeasure);
        super.setEnabled(builder.enabled);
        super.setVisible(builder.visible);
        super.setMeasureGroups(builder.measureGroups);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<CubeConnectorMappingImpl> getCubeUsages() {
        return cubeUsages;
    }

    public void setCubeUsages(List<CubeConnectorMappingImpl> cubeUsages) {
        this.cubeUsages = cubeUsages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<CubeConnectorMappingImpl> cubeUsages = Collections.emptyList();
        private List<DimensionConnectorMappingImpl> dimensionConnectors = Collections.emptyList();
        private List<CalculatedMemberMappingImpl> calculatedMembers = Collections.emptyList();
        private List<NamedSetMappingImpl> namedSets = Collections.emptyList();
        private List<KpiMappingImpl> kpis = Collections.emptyList();
        private MeasureMappingImpl defaultMeasure;
        private boolean enabled;
        private boolean visible;
        private List<MeasureGroupMappingImpl> measureGroups = Collections.emptyList();
        private List<AnnotationMappingImpl> annotations = Collections.emptyList();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withCubeUsages(List<CubeConnectorMappingImpl> cubeUsages) {
            this.cubeUsages = cubeUsages;
            return this;
        }

        public Builder withDimensionConnectors(List<DimensionConnectorMappingImpl> dimensionConnectors) {
            this.dimensionConnectors = dimensionConnectors;
            return this;
        }

        public Builder withCalculatedMembers(List<CalculatedMemberMappingImpl> calculatedMembers) {
            this.calculatedMembers = calculatedMembers;
            return this;
        }

        public Builder withNamedSets(List<NamedSetMappingImpl> namedSets) {
            this.namedSets = namedSets;
            return this;
        }

        public Builder withKpis(List<KpiMappingImpl> kpis) {
            this.kpis = kpis;
            return this;
        }

        public Builder withDefaultMeasure(MeasureMappingImpl defaultMeasure) {
            this.defaultMeasure = defaultMeasure;
            return this;
        }

        public Builder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withMeasureGroups(List<MeasureGroupMappingImpl> measureGroups) {
            this.measureGroups = measureGroups;
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

        public VirtualCubeMappingImpl build() {
            return new VirtualCubeMappingImpl(this);
        }
    }
}
