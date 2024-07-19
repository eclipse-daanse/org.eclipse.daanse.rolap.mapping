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

import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;

public class CubeMappingImpl extends AbstractElementMappingImpl implements CubeMapping {

    private List<DimensionConnectorMappingImpl> dimensionConnectors;

    private List<CalculatedMemberMappingImpl> calculatedMembers;

    private List<NamedSetMappingImpl> namedSets;

    private List<KpiMappingImpl> kpis;

    private MeasureMapping defaultMeasure;

    private boolean enabled;

    private boolean visible;

    private List<MeasureGroupMappingImpl> measureGroups;

    public List<DimensionConnectorMappingImpl> getDimensionConnectors() {
        return dimensionConnectors;
    }

    public void setDimensionConnectors(List<DimensionConnectorMappingImpl> dimensionConnectors) {
        this.dimensionConnectors = dimensionConnectors;
    }

    public List<CalculatedMemberMappingImpl> getCalculatedMembers() {
        return calculatedMembers;
    }

    public void setCalculatedMembers(List<CalculatedMemberMappingImpl> calculatedMembers) {
        this.calculatedMembers = calculatedMembers;
    }

    public List<NamedSetMappingImpl> getNamedSets() {
        return namedSets;
    }

    public void setNamedSets(List<NamedSetMappingImpl> namedSets) {
        this.namedSets = namedSets;
    }

    public List<KpiMappingImpl> getKpis() {
        return kpis;
    }

    public void setKpis(List<KpiMappingImpl> kpis) {
        this.kpis = kpis;
    }

    public MeasureMapping getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(MeasureMapping defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<MeasureGroupMappingImpl> getMeasureGroups() {
        return measureGroups;
    }

    public void setMeasureGroups(List<MeasureGroupMappingImpl> measureGroups) {
        this.measureGroups = measureGroups;
    }
}
