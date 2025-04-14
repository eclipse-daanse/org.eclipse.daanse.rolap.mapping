/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CustomMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class CustomMeasureMappingImpl extends MeasureMappingImpl implements CustomMeasureMapping{

    private String template;

    private List<? extends ColumnMapping> columns;

    private List<String> properties;


    private CustomMeasureMappingImpl(Builder builder) {
        super(builder);
        template = builder.template;
        columns = builder.columns;
        properties = builder.properties;
    }

    @Override
    public String getTemplate() {
        return this.template;
    }

    @Override
    public List<? extends ColumnMapping> getColumns() {
        return this.columns;
    }

    @Override
    public List<String> getProperties() {
        return this.properties;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends MeasureMappingImpl.Builder {
        private String template;
        private List<? extends ColumnMapping> columns;
        private List<String> properties;


        private Builder() {
            super();
        }

        public Builder withCalculatedMemberProperty(
                List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
            super.withCalculatedMemberProperty(calculatedMemberProperties);
            return this;
        }

        public Builder withCellFormatter(CellFormatterMappingImpl cellFormatter) {
            super.withCellFormatter(cellFormatter);
            return this;
        }

        public Builder withBackColor(String backColor) {
            super.withBackColor(backColor);
            return this;
        }

        public Builder withTemplate(String template) {
            this.template = template;
            return this;
        }

        public Builder withColumns(List<? extends ColumnMapping> columns) {
            this.columns = columns;
            return this;
        }

        public Builder withProperties(List<String> properties) {
            this.properties = properties;
            return this;
        }

        public Builder withDatatype(InternalDataType datatype) {
            super.withDatatype(datatype);
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            super.withDisplayFolder(displayFolder);
            return this;
        }

        public Builder withFormatString(String formatString) {
            super.withFormatString(formatString);
            return this;
        }

        public Builder withFormatter(String formatter) {
            super.withFormatter(formatter);
            return this;
        }

        public Builder withVisible(boolean visible) {
            super.withVisible(visible);
            return this;
        }

        public Builder withName(String name) {
            super.withName(name);
            return this;
        }

        public Builder withId(String id) {
            super.withId(id);
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            super.withAnnotations(annotations);
            return this;
        }

        public Builder withDescription(String description) {
            super.withDescription(description);
            return this;
        }

        public Builder withMeasureGroup(MeasureGroupMappingImpl measureGroup) {
            super.withMeasureGroup(measureGroup);
            return this;
        }

        public Builder withCalculatedMemberProperties(
                List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
            super.withCalculatedMemberProperties(calculatedMemberProperties);
            return this;
        }

        public CustomMeasureMappingImpl build() {
            return new CustomMeasureMappingImpl(this);
        }

    }
}
