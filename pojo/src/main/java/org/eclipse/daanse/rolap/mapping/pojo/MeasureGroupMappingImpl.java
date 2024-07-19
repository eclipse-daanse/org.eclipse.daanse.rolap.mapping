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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;

public class MeasureGroupMappingImpl implements MeasureGroupMapping {

    private List<MeasureMappingImpl> measures;

    private String name;

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
}
