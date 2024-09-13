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

import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;

public class MeasureGroupMappingImpl implements MeasureGroupMapping {

    private List<MeasureMappingImpl> measures;

    private String name;

    private PhysicalCubeMapping physicalCube;

    private MeasureGroupMappingImpl(Builder builder) {
        setMeasures(builder.measures);
        setName(builder.name);
        setPhysicalCube(builder.physicalCube);
    }

    @Override
    public List<MeasureMappingImpl> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureMappingImpl> measures) {
        this.measures = measures;
        for (MeasureMappingImpl m : measures) {
            m.setMeasureGroup(this);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PhysicalCubeMapping getPhysicalCube() {
        return physicalCube;
    }

    public void setPhysicalCube(PhysicalCubeMapping physicalCube) {
        this.physicalCube = physicalCube;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<MeasureMappingImpl> measures = new ArrayList<>();
        private String name;
        private PhysicalCubeMapping physicalCube;

        private Builder() {
        }

        public Builder withMeasures(List<MeasureMappingImpl> measures) {
            this.measures = measures;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPhysicalCube(PhysicalCubeMapping physicalCube) {
            this.physicalCube = physicalCube;
            return this;
        }

        public MeasureGroupMappingImpl build() {
            return new MeasureGroupMappingImpl(this);
        }
    }
}
