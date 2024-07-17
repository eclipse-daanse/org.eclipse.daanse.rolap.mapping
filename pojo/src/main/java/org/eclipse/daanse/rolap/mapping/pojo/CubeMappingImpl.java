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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;

public class CubeMappingImpl extends AbstractElementMappingImpl implements CubeMapping {

    private List<? extends DimensionConnectorMapping> dimensionConnectors;

    private List<? extends CalculatedMemberMapping> calculatedMembers;

    private List<? extends NamedSetMappingImpl> namedSets;

    private List<? extends KpiMappingImpl> kpis;

    private MeasureMapping defaultMeasure;

    private boolean enabled;

    private boolean visible;

    private List<? extends MeasureGroupMappingImpl> measureGroups;

    public List<? extends DimensionConnectorMapping> getDimensionConnectors() {
        return dimensionConnectors;
    }

    public void setDimensionConnectors(List<? extends DimensionConnectorMapping> dimensionConnectors) {
        this.dimensionConnectors = dimensionConnectors;
    }

    public List<? extends CalculatedMemberMapping> getCalculatedMembers() {
        return calculatedMembers;
    }

    public void setCalculatedMembers(List<? extends CalculatedMemberMappingImpl> calculatedMembers) {
        this.calculatedMembers = calculatedMembers;
    }

    public List<? extends NamedSetMappingImpl> getNamedSets() {
        return namedSets;
    }

    public void setNamedSets(List<? extends NamedSetMappingImpl> namedSets) {
        this.namedSets = namedSets;
    }

    public List<? extends KpiMappingImpl> getKpis() {
        return kpis;
    }

    public void setKpis(List<? extends KpiMappingImpl> kpis) {
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

    public List<? extends MeasureGroupMappingImpl> getMeasureGroups() {
        return measureGroups;
    }

    public void setMeasureGroups(List<? extends MeasureGroupMappingImpl> measureGroups) {
        this.measureGroups = measureGroups;
    }
}
