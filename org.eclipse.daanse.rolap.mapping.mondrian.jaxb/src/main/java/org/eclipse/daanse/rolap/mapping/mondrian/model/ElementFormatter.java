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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElementFormatter", propOrder = { "script" })
public class ElementFormatter {

    @XmlAttribute(name = "className")
    protected String className;
    @XmlElement(name = "Script", type = Script.class)
    protected Script script;

    public String className() {
        return className;
    }

    public Script script() {
        return script;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}
