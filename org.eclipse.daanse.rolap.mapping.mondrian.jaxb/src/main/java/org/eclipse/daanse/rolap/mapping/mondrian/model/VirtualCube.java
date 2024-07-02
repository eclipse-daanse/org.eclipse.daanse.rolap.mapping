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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "cubeUsages", "virtualCubeDimensions", "virtualCubeMeasures", "calculatedMembers",
        "namedSets", "kpis" })
public class VirtualCube extends AbstractMainElement {

    @XmlElementWrapper(name = "CubeUsages", required = true)
    @XmlElement(name = "CubeUsage", required = true, type = CubeUsage.class)
    protected List<CubeUsage> cubeUsages;
    @XmlElement(name = "VirtualCubeDimension", required = true, type = VirtualCubeDimension.class)
    protected List<VirtualCubeDimension> virtualCubeDimensions;
    @XmlElement(name = "VirtualCubeMeasure", required = true, type = VirtualCubeMeasure.class)
    protected List<VirtualCubeMeasure> virtualCubeMeasures;
    @XmlElement(name = "CalculatedMember", type = CalculatedMember.class)
    protected List<CalculatedMember> calculatedMembers;
    @XmlElement(name = "NamedSet", type = NamedSet.class)
    protected List<NamedSet> namedSets;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    @XmlElement(name = "Kpi", type = Kpi.class)
    @XmlElementWrapper(name = "Kpis")
    protected List<Kpi> kpis;

    @XmlAttribute(name = "defaultMeasure")
    protected String defaultMeasure;

    @XmlAttribute(name = "visible")
    protected Boolean visible = true;

    public List<CalculatedMember> calculatedMembers() {
        if (calculatedMembers == null) {
            calculatedMembers = new ArrayList<>();
        }
        return this.calculatedMembers;
    }

    public List<CubeUsage> cubeUsages() {
        return cubeUsages;
    }

    public String defaultMeasure() {
        return defaultMeasure;
    }

    public Boolean enabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    public List<Kpi> kpis() {
        return kpis;
    }

    public List<NamedSet> namedSets() {
        if (namedSets == null) {
            namedSets = new ArrayList<>();
        }
        return this.namedSets;
    }

    public void setCalculatedMembers(List<CalculatedMember> calculatedMembers) {
        this.calculatedMembers = calculatedMembers;
    }

    public void setCubeUsages(List<CubeUsage> value) {
        this.cubeUsages = value;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public void setNamedSets(List<NamedSet> namedSets) {
        this.namedSets = namedSets;
    }

    public void setVirtualCubeDimensions(List<VirtualCubeDimension> virtualCubeDimensions) {
        this.virtualCubeDimensions = virtualCubeDimensions;
    }

    public void setVirtualCubeMeasures(List<VirtualCubeMeasure> virtualCubeMeasures) {
        this.virtualCubeMeasures = virtualCubeMeasures;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<VirtualCubeDimension> virtualCubeDimensions() {
        if (virtualCubeDimensions == null) {
            virtualCubeDimensions = new ArrayList<>();
        }
        return this.virtualCubeDimensions;
    }

    public List<VirtualCubeMeasure> virtualCubeMeasures() {
        if (virtualCubeMeasures == null) {
            virtualCubeMeasures = new ArrayList<>();
        }
        return this.virtualCubeMeasures;
    }

    public Boolean visible() {
        return visible;
    }
}
