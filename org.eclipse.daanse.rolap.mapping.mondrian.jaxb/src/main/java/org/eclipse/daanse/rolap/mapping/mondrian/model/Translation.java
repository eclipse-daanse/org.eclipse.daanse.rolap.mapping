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

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Translation", propOrder = { "language", "caption", "description", "displayFolder", "annotations" })
@XmlRootElement(name = "Translation")
public class Translation {
    @XmlElement(name = "Language")
    @XmlSchemaType(name = "unsignedInt")
    protected long language;
    @XmlElement(name = "Caption")
    protected String caption;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "DisplayFolder")
    protected String displayFolder;
    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation", type = Annotation.class)
    protected List<Annotation> annotations;

    public List<Annotation> annotations() {
        return annotations;
    }

    public String caption() {
        return caption;
    }

    public String description() {
        return description;
    }

    public String displayFolder() {
        return displayFolder;
    }

    public long language() {
        return language;
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

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public void setLanguage(long value) {
        this.language = value;
    }

}
