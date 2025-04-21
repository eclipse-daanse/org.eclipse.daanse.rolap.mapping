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

import org.eclipse.daanse.rolap.mapping.api.model.BitAggMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.BitAggregationType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class BitAggMeasureMappingImpl extends MeasureMappingImpl implements BitAggMeasureMapping{

    private boolean not;

    private ColumnMapping column;

    private BitAggregationType bitAggType;

    private BitAggMeasureMappingImpl(Builder builder) {
        super(builder);
        column = builder.column;
        not = builder.not;
        bitAggType = builder.bitAggType;
    }

    public ColumnMapping getColumn() {
        return column;
    }

    @Override
    public boolean isNot() {
        return this.not;
    }

    @Override
    public BitAggregationType getBitAggType() {
        return this.bitAggType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends MeasureMappingImpl.Builder {
        private ColumnMapping column;
        private boolean not;
        private BitAggregationType bitAggType;


        private Builder() {
            super();
        }

        public Builder withColumn(ColumnMapping column) {
            this.column = column;
            return this;
        }

        public Builder withNot(boolean not) {
            this.not = not;
            return this;
        }

        public Builder withBitAggType(BitAggregationType bitAggType) {
            this.bitAggType = bitAggType;
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

        public BitAggMeasureMappingImpl build() {
            return new BitAggMeasureMappingImpl(this);
        }

    }
}
