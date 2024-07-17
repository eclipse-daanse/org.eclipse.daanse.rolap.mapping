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
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrillThroughAction", propOrder = { "drillThroughElements" })
public class DrillThroughAction extends AbstractMainElement {

    @XmlElements({ @XmlElement(name = "DrillThroughAttribute", type = DrillThroughAttribute.class),
            @XmlElement(name = "DrillThroughMeasure", type = DrillThroughMeasure.class) })
    protected List<DrillThroughElement> drillThroughElements;

    @XmlAttribute(name = "default")
    protected Boolean defaultt;

    public Boolean defaultt() {
        return defaultt;
    }

    public List<DrillThroughElement> drillThroughElements() {
        if (drillThroughElements == null) {
            drillThroughElements = new ArrayList<>();
        }
        return this.drillThroughElements;
    }

    public void setDefaultt(Boolean value) {
        this.defaultt = value;
    }

    public void setDrillThroughElements(List<DrillThroughElement> drillThroughElements) {
        this.drillThroughElements = drillThroughElements;
    }
}
