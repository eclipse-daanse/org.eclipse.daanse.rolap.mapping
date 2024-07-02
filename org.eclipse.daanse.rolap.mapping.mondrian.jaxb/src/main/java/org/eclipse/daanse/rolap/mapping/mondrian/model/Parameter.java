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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.ParameterTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Parameter")
public class Parameter {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(ParameterTypeAdaptor.class)
    protected ParameterTypeEnum type;
    @XmlAttribute(name = "modifiable")
    protected Boolean modifiable;
    @XmlAttribute(name = "defaultValue")
    protected String defaultValue;

    public String defaultValue() {
        return defaultValue;
    }

    public String description() {
        return description;
    }

    public Boolean modifiable() {
        if (modifiable == null) {
            return true;
        } else {
            return modifiable;
        }
    }

    public String name() {
        return name;
    }

    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public void setModifiable(Boolean value) {
        this.modifiable = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setType(ParameterTypeEnum value) {
        this.type = value;
    }

    public ParameterTypeEnum type() {
        return type;
    }

}
