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

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "AbstractMainElement", propOrder = { "annotations", "name", "description", "caption" })
@XmlSeeAlso({ Action.class, CalculatedMember.class, CalculatedMemberProperty.class, Cube.class, DimensionUsage.class,
        DrillThroughAction.class, Hierarchy.class, Level.class, Measure.class, NamedSet.class, PrivateDimension.class,
        Property.class, Role.class, Schema.class, SharedDimension.class, VirtualCubeDimension.class, VirtualCube.class,
        VirtualCubeMeasure.class, })
public abstract class AbstractMainElement {
    @XmlElement(name = "Annotation", type = Annotation.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<Annotation> annotations;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "caption")
    protected String caption;

    public AbstractMainElement() {
        super();
    }

    public List<Annotation> annotations() {
        if (annotations == null) {
            annotations = new ArrayList<>();
        }
        return this.annotations;
    }

    public String caption() {
        return caption;
    }

    public String description() {
        return description;
    }

    public String name() {
        return name;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public void setName(String value) {
        this.name = value;
    }

}
