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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.DimensionTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dimension", propOrder = { "hierarchies" })
@XmlRootElement(name = "Dimension")
public class PrivateDimension extends AbstractMainElement {

    @XmlElement(name = "Hierarchy", required = true, type = Hierarchy.class)
    protected List<Hierarchy> hierarchies;

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(DimensionTypeAdaptor.class)
    protected DimensionTypeEnum type;

    @XmlAttribute(name = "foreignKey")
    protected String foreignKey;

    @XmlAttribute(name = "visible")
    private Boolean visible = true;
    @XmlAttribute(name = "usagePrefix")
    private String usagePrefix;

    public String foreignKey() {
        return foreignKey;
    }

    public List<Hierarchy> hierarchies() {
        if (hierarchies == null) {
            hierarchies = new ArrayList<>();
        }
        return this.hierarchies;
    }

    public void setForeignKey(String value) {
        this.foreignKey = value;
    }

    public void setHierarchies(List<Hierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public void setType(DimensionTypeEnum value) {
        this.type = value;
    }

    public void setUsagePrefix(String usagePrefix) {
        this.usagePrefix = usagePrefix;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public DimensionTypeEnum type() {
        return type;
    }

    public String usagePrefix() {
        return usagePrefix;
    }

    public Boolean visible() {
        return visible == null ? Boolean.FALSE : visible;
    }
}
