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
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggMeasure")
public class AggMeasure {

    @XmlAttribute(name = "column", required = true)
    protected String column;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "rollupType")
    protected String rollupType;

    public String column() {
        return column;
    }

    public String name() {
        return name;
    }

    public String rollupType() {
        return rollupType;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setRollupType(String rollupType) {
        this.rollupType = rollupType;
    }
}
