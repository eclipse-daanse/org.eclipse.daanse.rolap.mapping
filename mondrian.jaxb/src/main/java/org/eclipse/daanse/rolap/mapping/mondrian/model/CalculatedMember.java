
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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedMember", propOrder = { "calculatedMemberProperties", "cellFormatter", "formulaElement" })
@XmlRootElement(name = "CalculatedMember")
public class CalculatedMember extends AbstractMainElement {

    @XmlAttribute(name = "formula")
    protected String formula;
    @XmlElement(name = "CalculatedMemberProperty", type = CalculatedMemberProperty.class)
    protected List<CalculatedMemberProperty> calculatedMemberProperties;

    @XmlAttribute(name = "formatString")
    protected String formatString;

    @XmlAttribute(name = "dimension", required = true)
    protected String dimension;
    @XmlAttribute(name = "visible")
    protected Boolean visible;
    @XmlAttribute(name = "displayFolder")
    protected String displayFolder;
    @XmlAttribute(name = "hierarchy")
    protected String hierarchy;
    @XmlAttribute(name = "parent")
    protected String parent;
    @XmlElement(name = "CellFormatter", type = CellFormatter.class)
    protected CellFormatter cellFormatter;
    @XmlElement(name = "Formula", type = Formula.class)
    protected Formula formulaElement;

    public List<CalculatedMemberProperty> calculatedMemberProperties() {
        if (calculatedMemberProperties == null) {
            calculatedMemberProperties = new ArrayList<>();
        }
        return this.calculatedMemberProperties;
    }

    public CellFormatter cellFormatter() {
        return cellFormatter;
    }

    public String dimension() {
        return dimension;
    }

    public String displayFolder() {
        return displayFolder;
    }

    public String formatString() {
        return formatString;
    }

    public String formula() {
        return formula;
    }

    public Formula formulaElement() {
        return formulaElement;
    }

    public String hierarchy() {
        return hierarchy;
    }

    public String parent() {
        return parent;
    }

    public void setCalculatedMemberProperties(List<CalculatedMemberProperty> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }

    public void setCellFormatter(CellFormatter cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void setDimension(String value) {
        this.dimension = value;
    }

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public void setFormatString(String value) {
        this.formatString = value;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setFormulaElement(Formula formulaElement) {
        this.formulaElement = formulaElement;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

    public Boolean visible() {
        if (visible == null) {
            return Boolean.TRUE;
        } else {
            return visible;
        }
    }
}
