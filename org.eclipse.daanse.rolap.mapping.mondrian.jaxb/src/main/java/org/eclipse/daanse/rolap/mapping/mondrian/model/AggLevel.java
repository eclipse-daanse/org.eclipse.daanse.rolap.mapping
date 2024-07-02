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
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggLevel", propOrder = { "properties" })
public class AggLevel {

    @XmlAttribute(name = "column", required = true)
    protected String column;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ordinalColumn")
    protected String ordinalColumn;
    @XmlAttribute(name = "captionColumn")
    protected String captionColumn;
    @XmlAttribute(name = "nameColumn")
    protected String nameColumn;
    @XmlAttribute(name = "collapsed")
    protected Boolean collapsed = true;
    @XmlElement(name = "AggLevelProperty", type = AggLevelProperty.class)
    List<AggLevelProperty> properties;

    public String captionColumn() {
        return captionColumn;
    }

    public Boolean collapsed() {
        return collapsed;
    }

    public String column() {
        return column;
    }

    public String name() {
        return name;
    }

    public String nameColumn() {
        return nameColumn;
    }

    public String ordinalColumn() {
        return ordinalColumn;
    }

    public List<AggLevelProperty> properties() {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        return properties;
    }

    public void setCaptionColumn(String captionColumn) {
        this.captionColumn = captionColumn;
    }

    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public void setOrdinalColumn(String ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public void setProperties(List<AggLevelProperty> properties) {
        this.properties = properties;
    }
}
