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

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InlineTable", propOrder = { "columnDefs", "rows" })
public class InlineTable implements RelationOrJoin {

    @XmlElementWrapper(name = "ColumnDefs")
    @XmlElement(name = "ColumnDef", required = true, type = ColumnDef.class)
    protected List<ColumnDef> columnDefs;
    @XmlElementWrapper(name = "Rows", required = true)
    @XmlElement(name = "Row", required = true, type = Row.class)
    protected List<Row> rows;
    @XmlAttribute(name = "alias")
    private String alias;

    public String alias() {
        return alias;
    }

    public List<ColumnDef> columnDefs() {
        return columnDefs;
    }

    public List<Row> rows() {
        return rows;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setColumnDefs(List<ColumnDef> value) {
        this.columnDefs = value;
    }

    public void setRows(List<Row> value) {
        this.rows = value;
    }

}
