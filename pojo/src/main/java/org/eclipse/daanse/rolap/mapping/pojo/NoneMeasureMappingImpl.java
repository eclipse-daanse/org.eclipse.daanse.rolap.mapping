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
import org.eclipse.daanse.rolap.mapping.api.model.NoneMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public class NoneMeasureMappingImpl extends MeasureMappingImpl implements NoneMeasureMapping{

    private SQLExpressionColumnMapping sqlExpressionColumn;

    private NoneMeasureMappingImpl(Builder builder) {
        super(builder);
        sqlExpressionColumn = builder.sqlExpressionColumn;
    }

    @Override
    public SQLExpressionColumnMapping getColumn() {
        return this.sqlExpressionColumn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends MeasureMappingImpl.Builder {
        private SQLExpressionColumnMapping sqlExpressionColumn;

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

        public Builder withColumn(SQLExpressionColumnMapping sqlExpressionColumn) {
            this.sqlExpressionColumn = sqlExpressionColumn;
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

        public NoneMeasureMappingImpl build() {
            return new NoneMeasureMappingImpl(this);
        }

    }
}
