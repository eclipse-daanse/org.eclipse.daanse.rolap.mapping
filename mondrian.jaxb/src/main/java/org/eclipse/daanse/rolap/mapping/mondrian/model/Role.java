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
@XmlType(name = "Role", propOrder = { "schemaGrants", "union" })
public class Role extends AbstractMainElement {

    @XmlElement(name = "SchemaGrant", type = SchemaGrant.class)
    protected List<SchemaGrant> schemaGrants;
    @XmlElement(name = "Union", required = true, type = Union.class)
    protected Union union;

    public List<SchemaGrant> schemaGrants() {
        if (schemaGrants == null) {
            schemaGrants = new ArrayList<>();
        }
        return this.schemaGrants;
    }

    public void setSchemaGrants(List<SchemaGrant> schemaGrants) {
        this.schemaGrants = schemaGrants;
    }

    public void setUnion(Union value) {
        this.union = value;
    }

    public Union union() {
        return union;
    }
}
