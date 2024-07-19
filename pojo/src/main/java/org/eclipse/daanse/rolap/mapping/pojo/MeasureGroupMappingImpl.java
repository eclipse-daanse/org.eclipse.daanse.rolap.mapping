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

import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;

public class MeasureGroupMappingImpl implements MeasureGroupMapping {

    private List<MeasureMappingImpl> measures;

    private String name;

    private MeasureGroupMappingImpl(Builder builder) {
        this.measures = builder.measures;
        this.name = builder.name;
    }

    public List<MeasureMappingImpl> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureMappingImpl> measures) {
        this.measures = measures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<MeasureMappingImpl> measures = Collections.emptyList();
        private String name;

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

        public MeasureGroupMappingImpl build() {
            return new MeasureGroupMappingImpl(this);
        }
    }
}
