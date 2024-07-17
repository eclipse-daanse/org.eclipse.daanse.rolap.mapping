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
@XmlType(name = "", propOrder = { "table" })
public class Closure {

    @XmlElement(name = "Table", required = true, type = Table.class)
    protected Table table;
    @XmlAttribute(name = "parentColumn", required = true)
    protected String parentColumn;
    @XmlAttribute(name = "childColumn", required = true)
    protected String childColumn;

    public String childColumn() {
        return childColumn;
    }

    public String parentColumn() {
        return parentColumn;
    }

    public void setChildColumn(String value) {
        this.childColumn = value;
    }

    public void setParentColumn(String value) {
        this.parentColumn = value;
    }

    public void setTable(Table value) {
        this.table = value;
    }

    public Table table() {
        return table;
    }

}
