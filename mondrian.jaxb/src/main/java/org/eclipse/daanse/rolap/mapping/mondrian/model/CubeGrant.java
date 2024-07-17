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
@XmlType(name = "", propOrder = { "dimensionGrants", "hierarchyGrants" })
public class CubeGrant {

    @XmlElement(name = "DimensionGrant", type = DimensionGrant.class)
    protected List<DimensionGrant> dimensionGrants;
    @XmlElement(name = "HierarchyGrant", type = HierarchyGrant.class)
    protected List<HierarchyGrant> hierarchyGrants;
    @XmlAttribute(name = "cube", required = true)
    protected String cube;
    @XmlAttribute(name = "access", required = true)
    protected String access;

    public String access() {
        return access;
    }

    public String cube() {
        return cube;
    }

    public List<DimensionGrant> dimensionGrants() {
        if (dimensionGrants == null) {
            dimensionGrants = new ArrayList<>();
        }
        return this.dimensionGrants;
    }

    public List<HierarchyGrant> hierarchyGrants() {
        if (hierarchyGrants == null) {
            hierarchyGrants = new ArrayList<>();
        }
        return this.hierarchyGrants;
    }

    public void setAccess(String value) {
        this.access = value;
    }

    public void setCube(String value) {
        this.cube = value;
    }

    public void setDimensionGrant(List<DimensionGrant> dimensionGrants) {
        this.dimensionGrants = dimensionGrants;
    }

    public void setHierarchyGrant(List<HierarchyGrant> hierarchyGrants) {
        this.hierarchyGrants = hierarchyGrants;
    }
}
