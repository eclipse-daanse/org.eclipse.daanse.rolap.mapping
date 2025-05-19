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
import org.eclipse.daanse.rolap.mapping.api.model.NthAggMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.OrderedColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class NthAggMeasureMappingImpl extends MeasureMappingImpl implements NthAggMeasureMapping{

    private ColumnMapping column;

    private boolean ignoreNulls;

    private Integer n;

    private List<? extends OrderedColumnMapping> orderByColumns;

    private NthAggMeasureMappingImpl(Builder builder) {
        super(builder);
        column = builder.column;
        ignoreNulls = builder.ignoreNulls;
        n = builder.n;
        orderByColumns = builder.orderByColumns;
    }

    @Override
    public ColumnMapping getColumn() {
        return this.column;
    }

    public boolean isIgnoreNulls() {
        return ignoreNulls;
    }

    @Override
    public Integer getN() {
        return this.n;
    }

    @Override
    public List<? extends OrderedColumnMapping> getOrderByColumns() {
        return this.orderByColumns;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends MeasureMappingImpl.Builder {
        private ColumnMapping column;
        private boolean ignoreNulls;
        private Integer n;
        private List<? extends OrderedColumnMapping> orderByColumns;


        private Builder() {
            super();
        }

        public Builder withColumn(ColumnMapping column) {
            this.column = column;
            return this;
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

        public Builder withN(Integer n) {
            this.n = n;
            return this;
        }

        public Builder withIgnoreNulls(boolean ignoreNulls) {
            this.ignoreNulls = ignoreNulls;
            return this;
        }

        public Builder withOrderedColumns(List<? extends OrderedColumnMapping> orderByColumns) {
            this.orderByColumns = orderByColumns;
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

        public NthAggMeasureMappingImpl build() {
            return new NthAggMeasureMappingImpl(this);
        }

    }
}
