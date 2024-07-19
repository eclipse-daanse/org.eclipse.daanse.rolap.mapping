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

import org.eclipse.daanse.rolap.mapping.api.model.KpiMapping;

public class KpiMappingImpl extends AbstractElementMappingImpl implements KpiMapping {

    private List<TranslationMappingImpl> translations;

    private String displayFolder;

    private String associatedMeasureGroupID;

    private String value;

    private String goal;

    private String status;

    private String trend;

    private String weight;

    private String trendGraphic;

    private String statusGraphic;

    private String currentTimeMember;

    private String parentKpiID;

    public List<TranslationMappingImpl> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationMappingImpl> translations) {
        this.translations = translations;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public String getAssociatedMeasureGroupID() {
        return associatedMeasureGroupID;
    }

    public void setAssociatedMeasureGroupID(String associatedMeasureGroupID) {
        this.associatedMeasureGroupID = associatedMeasureGroupID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTrendGraphic() {
        return trendGraphic;
    }

    public void setTrendGraphic(String trendGraphic) {
        this.trendGraphic = trendGraphic;
    }

    public String getStatusGraphic() {
        return statusGraphic;
    }

    public void setStatusGraphic(String statusGraphic) {
        this.statusGraphic = statusGraphic;
    }

    public String getCurrentTimeMember() {
        return currentTimeMember;
    }

    public void setCurrentTimeMember(String currentTimeMember) {
        this.currentTimeMember = currentTimeMember;
    }

    public String getParentKpiID() {
        return parentKpiID;
    }

    public void setParentKpiID(String parentKpiID) {
        this.parentKpiID = parentKpiID;
    }
}
