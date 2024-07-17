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
@XmlType(name = "")
public class WritebackAttribute implements WritebackColumn {

    @XmlAttribute(name = "dimension")
    private String dimension;
    @XmlAttribute(name = "column")
    private String column;

    @Override
    public String column() {
        return column;
    }

    public String dimension() {
        return dimension;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
