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

import org.eclipse.daanse.rolap.mapping.api.model.OrderedColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TextAggMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class TextAggMeasureMappingImpl extends MeasureMappingImpl implements TextAggMeasureMapping{

    private boolean distinct;

    private List<? extends OrderedColumnMapping> orderByColumns;

    private String separator;

    private String coalesce;

    private String onOverflowTruncate;



    private TextAggMeasureMappingImpl(Builder builder) {
        super(builder);
        distinct = builder.distinct;
        orderByColumns = builder.orderByColumns;
        separator = builder.separator;
        coalesce = builder.coalesce;
        onOverflowTruncate = builder.onOverflowTruncate;
    }

    @Override
    public boolean isDistinct() {
        return this.distinct;
    }

    @Override
    public List<? extends OrderedColumnMapping> getOrderByColumns() {
        return this.orderByColumns;
    }

    @Override
    public String getSeparator() {
        return this.separator;
    }

    @Override
    public String getCoalesce() {
        return this.coalesce;
    }

    @Override
    public String getOnOverflowTruncate() {
        return this.onOverflowTruncate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends MeasureMappingImpl.Builder {
        private boolean distinct;
        private List<? extends OrderedColumnMapping> orderByColumns;
        private String separator;
        private String coalesce;
        private String onOverflowTruncate;


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

        public Builder withDistinct(boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        public Builder withOrderedColumns(List<? extends OrderedColumnMapping> orderByColumns) {
            this.orderByColumns = orderByColumns;
            return this;
        }

        public Builder withSeparator(String separator) {
            this.separator = separator;
            return this;
        }

        public Builder withCoalesce(String coalesce) {
            this.coalesce = coalesce;
            return this;
        }

        public Builder withOnOverflowTruncate(String onOverflowTruncate) {
            this.onOverflowTruncate = onOverflowTruncate;
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

        public TextAggMeasureMappingImpl build() {
            return new TextAggMeasureMappingImpl(this);
        }

    }
}
