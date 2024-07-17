
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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedSet", propOrder = { "formulaElement" })
public class NamedSet extends AbstractMainElement {

    @XmlAttribute(name = "displayFolder")
    private String displayFolder;
    @XmlAttribute(name = "formula")
    protected String formula;
    @XmlElement(name = "Formula", type = Formula.class)
    protected Formula formulaElement;

    public String displayFolder() {
        return displayFolder;
    }

    public String formula() {
        return formula;
    }

    public Formula formulaElement() {
        return formulaElement;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public void setFormula(String value) {
        this.formula = value;
    }

    public void setFormulaElement(Formula formulaElement) {
        this.formulaElement = formulaElement;
    }

}
