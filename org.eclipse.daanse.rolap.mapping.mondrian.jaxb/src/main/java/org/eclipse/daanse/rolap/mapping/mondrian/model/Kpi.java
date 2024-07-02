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

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Kpi extends AbstractMainElement {

    @XmlElement(name = "id")
    protected String id;
    @XmlElement(name = "Translation", type = Translation.class)
    @XmlElementWrapper(name = "Translations")
    protected List<Translation> translations;
    @XmlElement(name = "DisplayFolder")
    protected String displayFolder;
    @XmlElement(name = "AssociatedMeasureGroupID")
    protected String associatedMeasureGroupID;
    @XmlElement(name = "Value", required = true)
    protected String value;
    @XmlElement(name = "Goal")
    protected String goal;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Trend")
    protected String trend;
    @XmlElement(name = "Weight")
    protected String weight;
    @XmlElement(name = "TrendGraphic")
    protected String trendGraphic;
    @XmlElement(name = "StatusGraphic")
    protected String statusGraphic;
    @XmlElement(name = "CurrentTimeMember")
    protected String currentTimeMember;
    @XmlElement(name = "ParentKpiID")
    protected String parentKpiID;

    public String associatedMeasureGroupID() {
        return associatedMeasureGroupID;
    }

    public String currentTimeMember() {
        return currentTimeMember;
    }

    public String displayFolder() {
        return displayFolder;
    }

    public String goal() {
        return goal;
    }

    public String id() {
        return id;
    }

    public String parentKpiID() {
        return parentKpiID;
    }

    public void setAssociatedMeasureGroupID(String associatedMeasureGroupID) {
        this.associatedMeasureGroupID = associatedMeasureGroupID;
    }

    public void setCurrentTimeMember(String currentTimeMember) {
        this.currentTimeMember = currentTimeMember;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentKpiID(String parentKpiID) {
        this.parentKpiID = parentKpiID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusGraphic(String statusGraphic) {
        this.statusGraphic = statusGraphic;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public void setTrendGraphic(String trendGraphic) {
        this.trendGraphic = trendGraphic;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String status() {
        return status;
    }

    public String statusGraphic() {
        return statusGraphic;
    }

    public List<Translation> translations() {
        return translations;
    }

    public String trend() {
        return trend;
    }

    public String trendGraphic() {
        return trendGraphic;
    }

    public String value() {
        return value;
    }

    public String weight() {
        return weight;
    }
}
