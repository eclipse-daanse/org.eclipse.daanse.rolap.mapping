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

    private KpiMappingImpl(Builder builder) {
        this.translations = builder.translations;
        this.displayFolder = builder.displayFolder;
        this.associatedMeasureGroupID = builder.associatedMeasureGroupID;
        this.value = builder.value;
        this.goal = builder.goal;
        this.status = builder.status;
        this.trend = builder.trend;
        this.weight = builder.weight;
        this.trendGraphic = builder.trendGraphic;
        this.statusGraphic = builder.statusGraphic;
        this.currentTimeMember = builder.currentTimeMember;
        this.parentKpiID = builder.parentKpiID;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentations(builder.documentation);
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<TranslationMappingImpl> translations = new ArrayList<>();
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
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withTranslations(List<TranslationMappingImpl> translations) {
            this.translations = translations;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withAssociatedMeasureGroupID(String associatedMeasureGroupID) {
            this.associatedMeasureGroupID = associatedMeasureGroupID;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder withGoal(String goal) {
            this.goal = goal;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withTrend(String trend) {
            this.trend = trend;
            return this;
        }

        public Builder withWeight(String weight) {
            this.weight = weight;
            return this;
        }

        public Builder withTrendGraphic(String trendGraphic) {
            this.trendGraphic = trendGraphic;
            return this;
        }

        public Builder withStatusGraphic(String statusGraphic) {
            this.statusGraphic = statusGraphic;
            return this;
        }

        public Builder withCurrentTimeMember(String currentTimeMember) {
            this.currentTimeMember = currentTimeMember;
            return this;
        }

        public Builder withParentKpiID(String parentKpiID) {
            this.parentKpiID = parentKpiID;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public KpiMappingImpl build() {
            return new KpiMappingImpl(this);
        }
    }
}
