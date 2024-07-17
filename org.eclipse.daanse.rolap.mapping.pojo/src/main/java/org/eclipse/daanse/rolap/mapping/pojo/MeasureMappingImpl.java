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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;

public class MeasureMappingImpl implements MeasureMapping {

    private SQLExpressionMappingImpl measureExpression;

    private List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty;

    private CellFormatterMapping cellFormatter;

    private String backColor;

    private String column;

    private String datatype;

    private String displayFolder;

    private String formatString;

    private String formatter;

    private boolean visible;

    private String name;

    public SQLExpressionMappingImpl getMeasureExpression() {
        return measureExpression;
    }

    public void setMeasureExpression(SQLExpressionMappingImpl measureExpression) {
        this.measureExpression = measureExpression;
    }

    public List<? extends CalculatedMemberPropertyMapping> getCalculatedMemberProperty() {
        return calculatedMemberProperty;
    }

    public void setCalculatedMemberProperty(List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty) {
        this.calculatedMemberProperty = calculatedMemberProperty;
    }

    public CellFormatterMapping getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMapping cellFormatter) {
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

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
