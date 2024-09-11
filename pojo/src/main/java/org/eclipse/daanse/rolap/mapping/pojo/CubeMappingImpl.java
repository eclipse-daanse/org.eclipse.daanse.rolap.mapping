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

public abstract class CubeMappingImpl extends AbstractElementMappingImpl implements CubeMapping {

    private List<DimensionConnectorMappingImpl> dimensionConnectors;

    private List<CalculatedMemberMappingImpl> calculatedMembers;

    private List<NamedSetMappingImpl> namedSets;

    private List<KpiMappingImpl> kpis;

    private MeasureMappingImpl defaultMeasure;

    private boolean enabled;

    private boolean visible = true;

    @Override
    public List<DimensionConnectorMappingImpl> getDimensionConnectors() {
        return dimensionConnectors;
    }

    public void setDimensionConnectors(List<DimensionConnectorMappingImpl> dimensionConnectors) {
        this.dimensionConnectors = dimensionConnectors;
    }

    @Override
    public List<CalculatedMemberMappingImpl> getCalculatedMembers() {
        return calculatedMembers;
    }

    public void setCalculatedMembers(List<CalculatedMemberMappingImpl> calculatedMembers) {
        this.calculatedMembers = calculatedMembers;
    }

    @Override
    public List<NamedSetMappingImpl> getNamedSets() {
        return namedSets;
    }

    public void setNamedSets(List<NamedSetMappingImpl> namedSets) {
        this.namedSets = namedSets;
    }

    @Override
    public List<KpiMappingImpl> getKpis() {
        return kpis;
    }

    public void setKpis(List<KpiMappingImpl> kpis) {
        this.kpis = kpis;
    }

    @Override
    public MeasureMappingImpl getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(MeasureMappingImpl defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
