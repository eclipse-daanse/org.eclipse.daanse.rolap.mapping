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
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WritebackTable")
public class WritebackTable {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "schema")
    protected String schema;
    @XmlElements({ @XmlElement(name = "WritebackAttribute", type = WritebackAttribute.class),
            @XmlElement(name = "WritebackMeasure", type = WritebackMeasure.class) })
    private List<WritebackColumn> columns;

    public List<WritebackColumn> columns() {
        return columns;
    }

    public String name() {
        return name;
    }

    public String schema() {
        return schema;
    }

    public void setColumns(List<WritebackColumn> columns) {
        this.columns = columns;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setSchema(String value) {
        this.schema = value;
    }
}
