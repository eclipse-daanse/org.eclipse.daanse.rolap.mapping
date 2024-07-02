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
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "relations" })
public class Join implements RelationOrJoin {

    @XmlElements({ @XmlElement(name = "Table", type = Table.class), @XmlElement(name = "View", type = View.class),
            @XmlElement(name = "Join", type = Join.class),
            @XmlElement(name = "InlineTable", type = InlineTable.class) })
    protected List<RelationOrJoin> relations;
    @XmlAttribute(name = "leftAlias")
    protected String leftAlias;
    @XmlAttribute(name = "leftKey")
    protected String leftKey;
    @XmlAttribute(name = "rightAlias")
    protected String rightAlias;
    @XmlAttribute(name = "rightKey")
    protected String rightKey;

    public String leftAlias() {
        return leftAlias;
    }

    public String leftKey() {
        return leftKey;
    }

    public List<RelationOrJoin> relations() {
        if (relations == null) {
            relations = new ArrayList<>();
        }
        return this.relations;
    }

    public String rightAlias() {
        return rightAlias;
    }

    public String rightKey() {
        return rightKey;
    }

    public void setLeftAlias(String value) {
        this.leftAlias = value;
    }

    public void setLeftKey(String value) {
        this.leftKey = value;
    }

    public void setRelations(List<RelationOrJoin> relations) {
        this.relations = relations;
    }

    public void setRightAlias(String value) {
        this.rightAlias = value;
    }

    public void setRightKey(String value) {
        this.rightKey = value;
    }
}
