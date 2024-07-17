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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.PropertyTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", propOrder = { "propertyFormatter" })
public class Property extends AbstractMainElement {

    @XmlAttribute(name = "column", required = true)
    protected String column;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(PropertyTypeAdaptor.class)
    protected PropertyTypeEnum type;
    @XmlAttribute(name = "formatter")
    protected String formatter;

    @XmlAttribute(name = "dependsOnLevelValue")
    protected Boolean dependsOnLevelValue;
    @XmlElement(name = "PropertyFormatter", type = ElementFormatter.class)
    protected ElementFormatter propertyFormatter;

    public String column() {
        return column;
    }

    public Boolean dependsOnLevelValue() {
        if (dependsOnLevelValue == null) {
            return false;
        } else {
            return dependsOnLevelValue;
        }
    }

    public String formatter() {
        return formatter;
    }

    public ElementFormatter propertyFormatter() {
        return propertyFormatter;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    public void setDependsOnLevelValue(Boolean value) {
        this.dependsOnLevelValue = value;
    }

    public void setFormatter(String value) {
        this.formatter = value;
    }

    public void setPropertyFormatter(ElementFormatter propertyFormatter) {
        this.propertyFormatter = propertyFormatter;
    }

    public void setType(PropertyTypeEnum type) {
        this.type = type;
    }

    public PropertyTypeEnum type() {
        if (type == null) {
            return PropertyTypeEnum.STRING;
        } else {
            return type;
        }
    }
}
