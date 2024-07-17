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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.AccessAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "memberGrants" })
public class HierarchyGrant {

    @XmlElement(name = "MemberGrant", type = MemberGrant.class)
    protected List<MemberGrant> memberGrants;
    @XmlAttribute(name = "hierarchy", required = true)
    protected String hierarchy;
    @XmlAttribute(name = "access", required = true)
    @XmlJavaTypeAdapter(AccessAdaptor.class)
    protected AccessEnum access;
    @XmlAttribute(name = "topLevel")
    protected String topLevel;
    @XmlAttribute(name = "bottomLevel")
    protected String bottomLevel;
    @XmlAttribute(name = "rollupPolicy")
    protected String rollupPolicy;

    public AccessEnum access() {
        return access;
    }

    public String bottomLevel() {
        return bottomLevel;
    }

    public String hierarchy() {
        return hierarchy;
    }

    public List<MemberGrant> memberGrants() {
        if (memberGrants == null) {
            memberGrants = new ArrayList<>();
        }
        return this.memberGrants;
    }

    public String rollupPolicy() {
        if (rollupPolicy == null) {
            return "full";
        } else {
            return rollupPolicy;
        }
    }

    public void setAccess(AccessEnum value) {
        this.access = value;
    }

    public void setBottomLevel(String value) {
        this.bottomLevel = value;
    }

    public void setHierarchy(String value) {
        this.hierarchy = value;
    }

    public void setMemberGrants(List<MemberGrant> memberGrants) {
        this.memberGrants = memberGrants;
    }

    public void setRollupPolicy(String value) {
        this.rollupPolicy = value;
    }

    public void setTopLevel(String value) {
        this.topLevel = value;
    }

    public String topLevel() {
        return topLevel;
    }

}
