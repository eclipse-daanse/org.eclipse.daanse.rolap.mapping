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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.MemberGrantAccessAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class MemberGrant {

    @XmlAttribute(name = "member")
    protected String member;
    @XmlAttribute(name = "access", required = true)
    @XmlJavaTypeAdapter(MemberGrantAccessAdaptor.class)
    protected MemberGrantAccessEnum access;

    public MemberGrantAccessEnum access() {
        return access;
    }

    public String member() {
        return member;
    }

    public void setAccess(MemberGrantAccessEnum value) {
        this.access = value;
    }

    public void setMember(String value) {
        this.member = value;
    }

}
