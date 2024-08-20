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

import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;

public class MeasureMappingImpl extends AbstractElementMappingImpl implements MeasureMapping {

    private SQLExpressionMappingImpl measureExpression;

    private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty;

    private CellFormatterMappingImpl cellFormatter;

    private String backColor;

    private String column;

    private DataType datatype;

    private String displayFolder;

    private String formatString;

    private String formatter;

    private boolean visible;

    private MeasureAggregatorType aggregatorType;

    private MeasureMappingImpl(Builder builder) {
        this.measureExpression = builder.measureExpression;
        this.calculatedMemberProperty = builder.calculatedMemberProperty;
        this.cellFormatter = builder.cellFormatter;
        this.backColor = builder.backColor;
        this.column = builder.column;
        this.datatype = builder.datatype;
        this.displayFolder = builder.displayFolder;
        this.formatString = builder.formatString;
        this.formatter = builder.formatter;
        this.visible = builder.visible;
        this.aggregatorType = builder.aggregatorType;
        super.setName(builder.name);
        super.setId(builder.id);
        super.setAnnotations(builder.annotations);
        super.setDescription(builder.description);
        super.setDocumentation(builder.documentation);
    }

    public SQLExpressionMappingImpl getMeasureExpression() {
        return measureExpression;
    }

    public void setMeasureExpression(SQLExpressionMappingImpl measureExpression) {
        this.measureExpression = measureExpression;
    }

    public List<CalculatedMemberPropertyMappingImpl> getCalculatedMemberProperty() {
        return calculatedMemberProperty;
    }

    public void setCalculatedMemberProperty(List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty) {
        this.calculatedMemberProperty = calculatedMemberProperty;
    }

    public CellFormatterMappingImpl getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMappingImpl cellFormatter) {
        this.cellFormatter = cellFormatter;
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

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public MeasureAggregatorType getAggregatorType() {
        return aggregatorType;
    }

    public void setAggregatorType(MeasureAggregatorType type) {
        this.aggregatorType = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SQLExpressionMappingImpl measureExpression;
        private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty = new ArrayList<>();
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

        public MeasureMappingImpl build() {
            return new MeasureMappingImpl(this);
        }
    }


}
