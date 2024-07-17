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
@XmlType(name = "", propOrder = { "cubeGrants" })
public class SchemaGrant {

    @XmlElement(name = "CubeGrant", required = true, type = CubeGrant.class)
    protected List<CubeGrant> cubeGrants;
    @XmlAttribute(name = "access", required = true)
    @XmlJavaTypeAdapter(AccessAdaptor.class)
    protected AccessEnum access;

    public AccessEnum access() {
        return access;
    }

    public List<CubeGrant> cubeGrants() {
        if (cubeGrants == null) {
            cubeGrants = new ArrayList<>();
        }
        return this.cubeGrants;
    }

    public void setAccess(AccessEnum value) {
        this.access = value;
    }

    public void setCubeGrants(List<CubeGrant> cubeGrants) {
        this.cubeGrants = cubeGrants;
    }
}
