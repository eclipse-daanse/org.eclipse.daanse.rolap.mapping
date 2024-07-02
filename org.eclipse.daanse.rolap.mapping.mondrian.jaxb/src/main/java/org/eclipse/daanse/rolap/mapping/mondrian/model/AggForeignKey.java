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
@XmlType(name = "AggForeignKey")
public class AggForeignKey {

    @XmlAttribute(name = "factColumn", required = true)
    protected String factColumn;
    @XmlAttribute(name = "aggColumn", required = true)
    protected String aggColumn;

    public String aggColumn() {
        return aggColumn;
    }

    public String factColumn() {
        return factColumn;
    }

    public void setAggColumn(String value) {
        this.aggColumn = value;
    }

    public void setFactColumn(String value) {
        this.factColumn = value;
    }

}
