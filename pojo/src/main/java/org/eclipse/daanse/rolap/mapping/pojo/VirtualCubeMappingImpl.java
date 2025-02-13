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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;

public class VirtualCubeMappingImpl extends CubeMappingImpl implements VirtualCubeMapping {

    private List<CubeConnectorMappingImpl> cubeUsages;
    private List<? extends MeasureMapping> referencedMeasures;
    private List<? extends CalculatedMemberMapping> referencedCalculatedMembers;

    private VirtualCubeMappingImpl(Builder builder) {
        this.cubeUsages = builder.cubeUsages;
        this.referencedMeasures = builder.referencedMeasures;
        this.referencedCalculatedMembers = builder.referencedCalculatedMembers;
        super.setDimensionConnectors(builder.dimensionConnectors);
        super.setCalculatedMembers(builder.calculatedMembers);
        super.setNamedSets(builder.namedSets);
        super.setKpis(builder.kpis);
        super.setDefaultMeasure(builder.defaultMeasure);
        super.setEnabled(builder.enabled);
        super.setVisible(builder.visible);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
    }

    @Override
    public List<CubeConnectorMappingImpl> getCubeUsages() {
        return cubeUsages;
    }

    public void setCubeUsages(List<CubeConnectorMappingImpl> cubeUsages) {
        this.cubeUsages = cubeUsages;
    }

    @Override
    public List<? extends MeasureMapping> getReferencedMeasures() {
        return referencedMeasures;
    }

    public void setReferencedMeasures(List<MeasureMappingImpl> referencedMeasures) {
        this.referencedMeasures = referencedMeasures;
    }

    @Override
    public List<? extends CalculatedMemberMapping> getReferencedCalculatedMembers() {
        return referencedCalculatedMembers;
    }

    public void setReferencedCalculatedMembers(List<CalculatedMemberMappingImpl> referencedCalculatedMembers) {
        this.referencedCalculatedMembers = referencedCalculatedMembers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<CubeConnectorMappingImpl> cubeUsages = new ArrayList<>();
        private List<DimensionConnectorMappingImpl> dimensionConnectors = new ArrayList<>();
        private List<CalculatedMemberMappingImpl> calculatedMembers = new ArrayList<>();
        private List<NamedSetMappingImpl> namedSets = new ArrayList<>();
        private List<KpiMappingImpl> kpis = new ArrayList<>();
        private MemberMappingImpl defaultMeasure;
        private boolean enabled = true;
        private boolean visible = true;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private List<? extends MeasureMapping> referencedMeasures = new ArrayList<>();
        private List<? extends CalculatedMemberMapping> referencedCalculatedMembers = new ArrayList<>();

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

        public Builder withDefaultMeasure(MemberMappingImpl defaultMeasure) {
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

        public Builder withReferencedMeasures(List<? extends MeasureMapping> referencedMeasures) {
            this.referencedMeasures = referencedMeasures;
            return this;
        }

        public Builder withReferencedCalculatedMembers(List<? extends CalculatedMemberMapping> referencedCalculatedMembers) {
            this.referencedCalculatedMembers = referencedCalculatedMembers;
            return this;
        }

        public VirtualCubeMappingImpl build() {
            return new VirtualCubeMappingImpl(this);
        }
    }
}
