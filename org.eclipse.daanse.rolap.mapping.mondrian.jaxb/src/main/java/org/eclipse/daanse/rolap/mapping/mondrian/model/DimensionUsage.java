
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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DimensionUsage", propOrder = {})
@XmlRootElement(name = "DimensionUsage")
public class DimensionUsage extends AbstractMainElement implements DimensionOrDimensionUsage{

    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "level")
    protected String level;
    @XmlAttribute(name = "usagePrefix")
    protected String usagePrefix;
    @XmlAttribute(name = "foreignKey")
    protected String foreignKey;

    @XmlAttribute(name = "visible")
    protected Boolean visible = true;

    public String foreignKey() {
        return foreignKey;
    }

    public String level() {
        return level;
    }

    public void setForeignKey(String value) {
        this.foreignKey = value;
    }

    public void setLevel(String value) {
        this.level = value;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public void setUsagePrefix(String value) {
        this.usagePrefix = value;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String source() {
        return source;
    }

    public String usagePrefix() {
        return usagePrefix;
    }

    public Boolean visible() {
        return visible == null ? Boolean.FALSE : visible;
    }

}
