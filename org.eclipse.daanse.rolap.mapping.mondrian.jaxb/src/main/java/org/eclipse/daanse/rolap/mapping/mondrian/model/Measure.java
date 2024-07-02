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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.MeasureDataTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "measureExpression", "calculatedMemberProperties", "cellFormatter" })
public class Measure extends AbstractMainElement {

    @XmlElement(name = "MeasureExpression", type = ExpressionView.class)
    protected ExpressionView measureExpression;
    @XmlElement(name = "CalculatedMemberProperty", type = CalculatedMemberProperty.class)
    protected List<CalculatedMemberProperty> calculatedMemberProperties;

    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "datatype")
    @XmlJavaTypeAdapter(MeasureDataTypeAdaptor.class)
    protected MeasureDataTypeEnum datatype;
    @XmlAttribute(name = "formatString")
    protected String formatString;
    @XmlAttribute(name = "aggregator", required = true)
    protected String aggregator;
    @XmlAttribute(name = "formatter")
    protected String formatter;

    @XmlAttribute(name = "visible")
    protected Boolean visible;
    @XmlAttribute(name = "displayFolder")
    protected String displayFolder;
    @XmlElement(name = "CellFormatter", type = CellFormatter.class)
    ElementFormatter cellFormatter;
    @XmlAttribute(name = "backColor")
    protected String backColor;

    public String aggregator() {
        return aggregator;
    }

    public String backColor() {
        return backColor;
    }

    public List<CalculatedMemberProperty> calculatedMemberProperties() {
        if (calculatedMemberProperties == null) {
            calculatedMemberProperties = new ArrayList<>();
        }
        return this.calculatedMemberProperties;
    }

    public ElementFormatter cellFormatter() {
        return cellFormatter;
    }

    public String column() {
        return column;
    }

    public MeasureDataTypeEnum datatype() {
        return datatype;
    }

    public String displayFolder() {
        return displayFolder;
    }

    public String formatString() {
        return formatString;
    }

    public String formatter() {
        return formatter;
    }

    public ExpressionView measureExpression() {
        return measureExpression;
    }

    public void setAggregator(String value) {
        this.aggregator = value;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public void setCalculatedMemberProperties(List<CalculatedMemberProperty> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }

    public void setCellFormatter(ElementFormatter cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    public void setDatatype(MeasureDataTypeEnum value) {
        this.datatype = value;
    }

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public void setFormatString(String value) {
        this.formatString = value;
    }

    public void setFormatter(String value) {
        this.formatter = value;
    }

    public void setMeasureExpression(ExpressionView value) {
        this.measureExpression = value;
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

    public Boolean visible() {
        if (visible == null) {
            return true;
        } else {
            return visible;
        }
    }
}
