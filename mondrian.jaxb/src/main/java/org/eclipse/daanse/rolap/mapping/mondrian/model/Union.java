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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "roleUsages" })
public class Union {

    @XmlElement(name = "RoleUsage", required = true, type = RoleUsage.class)
    protected List<RoleUsage> roleUsages;

    public List<RoleUsage> roleUsages() {
        if (roleUsages == null) {
            roleUsages = new ArrayList<>();
        }
        return this.roleUsages;
    }

    public void setRoleUsages(List<RoleUsage> roleUsages) {
        this.roleUsages = roleUsages;
    }

}
