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
@XmlType(name = "AggExclude")
public class AggExclude {

    @XmlAttribute(name = "pattern")
    protected String pattern;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;

    public Boolean ignorecase() {
        if (ignorecase == null) {
            return true;
        } else {
            return ignorecase;
        }
    }

    public String name() {
        return name;
    }

    public String pattern() {
        return pattern;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setPattern(String value) {
        this.pattern = value;
    }

}
