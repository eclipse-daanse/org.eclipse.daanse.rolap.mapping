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

import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;

public class MeasureMappingImpl extends MemberMappingImpl implements MeasureMapping {

    private SQLExpressionMappingImpl measureExpression;

    private String backColor;

    private String column;

    private DataType datatype;

    private String formatter;

    private MeasureAggregatorType aggregatorType;

    private MeasureGroupMapping measureGroup;

    private MeasureMappingImpl(Builder builder) {
        this.measureExpression = builder.measureExpression;
        this.backColor = builder.backColor;
        this.column = builder.column;
        this.datatype = builder.datatype;
        this.formatter = builder.formatter;
        this.aggregatorType = builder.aggregatorType;
        this.measureGroup = builder.measureGroup;
        super.setName(builder.name);
        super.setId(builder.id);
        super.setAnnotations(builder.annotations);
        super.setDescription(builder.description);
        super.setDocumentation(builder.documentation);
        super.setDisplayFolder(builder.displayFolder);
        super.setFormatString(builder.formatString);
        super.setVisible(builder.visible);
        super.setCellFormatter(builder.cellFormatter);
        super.setCalculatedMemberProperties(builder.calculatedMemberProperties);

    }

    public SQLExpressionMappingImpl getMeasureExpression() {
        return measureExpression;
    }

    public void setMeasureExpression(SQLExpressionMappingImpl measureExpression) {
        this.measureExpression = measureExpression;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public void setDatatype(DataType datatype) {
        this.datatype = datatype;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public MeasureAggregatorType getAggregatorType() {
        return aggregatorType;
    }

    public void setAggregatorType(MeasureAggregatorType type) {
        this.aggregatorType = type;
    }

    public MeasureGroupMapping getMeasureGroup() {
        return measureGroup;
    }

    public void setMeasureGroup(MeasureGroupMapping measureGroup) {
        this.measureGroup = measureGroup;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private SQLExpressionMappingImpl measureExpression;
        private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties = new ArrayList<>();
        private CellFormatterMappingImpl cellFormatter;
        private String backColor;
        private String column;
        private DataType datatype;
        private String displayFolder;
        private String formatString;
        private String formatter;
        private boolean visible = true;
        private String name;
        private String id;
        private MeasureAggregatorType aggregatorType;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String description;
        private DocumentationMappingImpl documentation;
        private MeasureGroupMapping measureGroup;

        private Builder() {
        }

        public Builder withMeasureExpression(SQLExpressionMappingImpl measureExpression) {
            this.measureExpression = measureExpression;
            return this;
        }

        public Builder withCalculatedMemberProperty(
            List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties
        ) {
            this.calculatedMemberProperties = calculatedMemberProperties;
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

        public Builder withDatatype(DataType datatype) {
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

        public Builder withAggregatorType(MeasureAggregatorType type) {
            this.aggregatorType = type;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public Builder withMeasureGroup(MeasureGroupMappingImpl measureGroup) {
            this.measureGroup = measureGroup;
            return this;
        }

        public Builder withCalculatedMemberProperties(List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
            this.calculatedMemberProperties = calculatedMemberProperties;
            return this;
        }

        public MeasureMappingImpl build() {
            return new MeasureMappingImpl(this);
        }
    }
}
