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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.TypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class ColumnDef {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(TypeAdaptor.class)
    protected TypeEnum type;

    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setType(TypeEnum value) {
        this.type = value;
    }

    public TypeEnum type() {
        return type;
    }

}
