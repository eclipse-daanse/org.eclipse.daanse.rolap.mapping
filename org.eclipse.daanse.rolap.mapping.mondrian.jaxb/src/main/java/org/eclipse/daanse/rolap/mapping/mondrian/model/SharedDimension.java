
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
@XmlType(name = "SharedDimension", propOrder = { "hierarchies" })
public class SharedDimension extends AbstractMainElement {

    @XmlElement(name = "Hierarchy", required = true, type = Hierarchy.class)
    protected List<Hierarchy> hierarchies;

    @XmlAttribute(name = "type")
    protected String type;

    @XmlAttribute(name = "foreignKey")
    private String foreignKey;

    public String foreignKey() {
        return foreignKey;
    }

    public List<Hierarchy> hierarchies() {
        if (hierarchies == null) {
            hierarchies = new ArrayList<>();
        }
        return this.hierarchies;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String type() {
        if (type == null) {
            return "StandardDimension";
        } else {
            return type;
        }
    }

}
