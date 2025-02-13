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

import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;

public class PhysicalCubeMappingImpl extends CubeMappingImpl implements PhysicalCubeMapping {

    private QueryMappingImpl query;

    private WritebackTableMappingImpl writebackTable;

    private List<ActionMappingMappingImpl> action;

    private boolean cache;

    private List<MeasureGroupMappingImpl> measureGroups;

    private PhysicalCubeMappingImpl(Builder builder) {
        setQuery(builder.query);
        setWritebackTable(builder.writebackTable);
        setAction(builder.action);
        setCache(builder.cache);
        setMeasureGroups(builder.measureGroups);
        super.setDimensionConnectors(builder.dimensionConnectors);
        setCalculatedMembers(builder.calculatedMembers);
        super.setNamedSets(builder.namedSets);
        super.setKpis(builder.kpis);
        super.setDefaultMeasure(builder.defaultMeasure);
        super.setEnabled(builder.enabled);
        super.setVisible(builder.visible);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentations(builder.documentation);
    }

    @Override
    public QueryMappingImpl getQuery() {
        return query;
    }

    public void setQuery(QueryMappingImpl query) {
        this.query = query;
    }

    @Override
    public WritebackTableMappingImpl getWritebackTable() {
        return writebackTable;
    }

    public void setWritebackTable(WritebackTableMappingImpl writebackTable) {
        this.writebackTable = writebackTable;
    }

    @Override
    public List<ActionMappingMappingImpl> getAction() {
        return action;
    }

    public void setAction(List<ActionMappingMappingImpl> action) {
        this.action = action;
    }

    @Override
    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    @Override
    public List<MeasureGroupMappingImpl> getMeasureGroups() {
        return measureGroups;
    }

    public void setMeasureGroups(List<MeasureGroupMappingImpl> measureGroups) {
        this.measureGroups = measureGroups;
        for (MeasureGroupMappingImpl mg : measureGroups) {
            mg.setPhysicalCube(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void setCalculatedMembers(List<CalculatedMemberMappingImpl> calculatedMembers) {
        for (CalculatedMemberMappingImpl cm : calculatedMembers) {
            cm.setPhysicalCube(this);
        }
        super.setCalculatedMembers(calculatedMembers);
    }

    public static final class Builder {
        private QueryMappingImpl query;
        private WritebackTableMappingImpl writebackTable;
        private List<ActionMappingMappingImpl> action = new ArrayList<>();
        private boolean cache = true;
        private List<DimensionConnectorMappingImpl> dimensionConnectors = new ArrayList<>();
        private List<CalculatedMemberMappingImpl> calculatedMembers = new ArrayList<>();
        private List<NamedSetMappingImpl> namedSets = new ArrayList<>();
        private List<KpiMappingImpl> kpis = new ArrayList<>();
        private MemberMappingImpl defaultMeasure;
        private boolean enabled = true;
        private boolean visible = true;
        private List<MeasureGroupMappingImpl> measureGroups = new ArrayList<>();
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withQuery(QueryMappingImpl query) {
            this.query = query;
            return this;
        }

        public Builder withWritebackTable(WritebackTableMappingImpl writebackTable) {
            this.writebackTable = writebackTable;
            return this;
        }

        public Builder withAction(List<ActionMappingMappingImpl> action) {
            this.action = action;
            return this;
        }

        public Builder withCache(boolean cache) {
            this.cache = cache;
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

        public PhysicalCubeMappingImpl build() {
            return new PhysicalCubeMappingImpl(this);
        }
    }
}
