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
public class CubeUsage {

    @XmlAttribute(name = "cubeName", required = true)
    protected String cubeName;
    @XmlAttribute(name = "ignoreUnrelatedDimensions")
    protected Boolean ignoreUnrelatedDimensions;

    public String cubeName() {
        return cubeName;
    }

    public Boolean ignoreUnrelatedDimensions() {
        if (ignoreUnrelatedDimensions == null) {
            return false;
        } else {
            return ignoreUnrelatedDimensions;
        }
    }

    public void setCubeName(String value) {
        this.cubeName = value;
    }

    public void setIgnoreUnrelatedDimensions(Boolean value) {
        this.ignoreUnrelatedDimensions = value;
    }

}
