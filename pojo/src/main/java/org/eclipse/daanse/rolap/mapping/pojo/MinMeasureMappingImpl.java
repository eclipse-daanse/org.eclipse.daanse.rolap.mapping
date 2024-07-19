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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.MinMeasureMapping;

public class MinMeasureMappingImpl extends MeasureMappingImpl implements MinMeasureMapping {

    private MinMeasureMappingImpl(Builder builder) {
        super.setMeasureExpression(builder.measureExpression);
        super.setCalculatedMemberProperty(builder.calculatedMemberProperty);
        super.setCellFormatter(builder.cellFormatter);
        super.setBackColor(builder.backColor);
        super.setColumn(builder.column);
        super.setDatatype(builder.datatype);
        super.setDisplayFolder(builder.displayFolder);
        super.setFormatString(builder.formatString);
        super.setFormatter(builder.formatter);
        super.setVisible(builder.visible);
        super.setName(builder.name);
        super.setId(builder.id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SQLExpressionMappingImpl measureExpression;
        private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty = Collections.emptyList();
        private CellFormatterMappingImpl cellFormatter;
        private String backColor;
        private String column;
        private String datatype;
        private String displayFolder;
        private String formatString;
        private String formatter;
        private boolean visible;
        private String name;
        private String id;

        private Builder() {
        }

        public Builder withMeasureExpression(SQLExpressionMappingImpl measureExpression) {
            this.measureExpression = measureExpression;
            return this;
        }

        public Builder withCalculatedMemberProperty(
                List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty) {
            this.calculatedMemberProperty = calculatedMemberProperty;
            return this;
        }

        public Builder withCellFormatter(CellFormatterMappingImpl cellFormatter) {
            this.cellFormatter = cellFormatter;
            return this;
        }

        public Builder withBackColor(String backColor) {
            this.backColor = backColor;
            return this;
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withDatatype(String datatype) {
            this.datatype = datatype;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withFormatString(String formatString) {
            this.formatString = formatString;
            return this;
        }

        public Builder withFormatter(String formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public MinMeasureMappingImpl build() {
            return new MinMeasureMappingImpl(this);
        }
    }
}
