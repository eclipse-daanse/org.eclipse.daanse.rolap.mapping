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
import java.util.Optional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cube", propOrder = { "fact", "dimensionUsageOrDimensions", "measures", "calculatedMembers",
        "namedSets", "drillThroughActions", "writebackTable", "kpis", "actions" })
public class Cube extends AbstractMainElement {

    @XmlElements({ @XmlElement(name = "DimensionUsage", type = DimensionUsage.class),
            @XmlElement(name = "Dimension", type = Dimension.class) })
    protected List<DimensionOrDimensionUsage> dimensionUsageOrDimensions;
    @XmlElement(name = "Measure", required = true, type = Measure.class)
    protected List<Measure> measures;
    @XmlElement(name = "CalculatedMember", type = CalculatedMember.class)
    protected List<CalculatedMember> calculatedMembers;
    @XmlElement(name = "NamedSet", type = NamedSet.class)
    protected List<NamedSet> namedSets;
    @XmlElement(name = "DrillThroughAction", type = DrillThroughAction.class)
    protected List<DrillThroughAction> drillThroughActions;
    @XmlElement(name = "WritebackTable", type = WritebackTable.class)
    protected WritebackTable writebackTable;
    @XmlElement(name = "Kpi", type = Kpi.class)
    @XmlElementWrapper(name = "Kpis")
    protected List<Kpi> kpis;
    @XmlAttribute(name = "defaultMeasure")
    protected String defaultMeasure;
    @XmlAttribute(name = "cache")
    protected Boolean cache;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;
    @XmlElements({ @XmlElement(name = "InlineTable", type = InlineTable.class),
            @XmlElement(name = "Table", type = Table.class), @XmlElement(name = "View", type = View.class) })
    protected RelationOrJoin fact;
    @XmlElement(name = "Action", type = Action.class)
    protected List<Action> actions;

    public List<Action> actions() {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        return actions;
    }

    public Boolean cache() {
        if (cache == null) {
            return true;
        } else {
            return cache;
        }
    }

    public List<CalculatedMember> calculatedMembers() {
        if (calculatedMembers == null) {
            calculatedMembers = new ArrayList<>();
        }
        return this.calculatedMembers;
    }

    public String defaultMeasure() {
        return defaultMeasure;
    }

    public List<DimensionOrDimensionUsage> dimensionUsageOrDimensions() {
        if (dimensionUsageOrDimensions == null) {
            dimensionUsageOrDimensions = new ArrayList<>();
        }
        return this.dimensionUsageOrDimensions;
    }

    public List<DrillThroughAction> drillThroughActions() {
        if (drillThroughActions == null) {
            drillThroughActions = new ArrayList<>();
        }
        return this.drillThroughActions;
    }

    public Boolean enabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    public RelationOrJoin fact() {
        return fact;
    }

    public List<Kpi> kpis() {
        if (kpis == null) {
            kpis = new ArrayList<>();
        }
        return kpis;
    }

    public List<Measure> measures() {
        if (measures == null) {
            measures = new ArrayList<>();
        }
        return this.measures;
    }

    public List<NamedSet> namedSets() {
        if (namedSets == null) {
            namedSets = new ArrayList<>();
        }
        return this.namedSets;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setCache(Boolean value) {
        this.cache = value;
    }

    public void setCalculatedMembers(List<CalculatedMember> calculatedMembers) {
        this.calculatedMembers = calculatedMembers;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
    }

    public void setDimensionUsageOrDimensions(List<DimensionOrDimensionUsage> dimensionUsageOrDimensions) {
        this.dimensionUsageOrDimensions = dimensionUsageOrDimensions;
    }

    public void setDrillThroughActions(List<DrillThroughAction> drillThroughActions) {
        this.drillThroughActions = drillThroughActions;
    }

    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    public void setFact(RelationOrJoin fact) {
        this.fact = fact;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public void setNamedSets(List<NamedSet> namedSets) {
        this.namedSets = namedSets;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setWritebackTable(WritebackTable writebackTable) {
        this.writebackTable = writebackTable;
    }

    public Boolean visible() {
        return visible;
    }

    public Optional<WritebackTable> writebackTable() {
        return Optional.ofNullable(writebackTable);
    }

}
