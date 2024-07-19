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
 *
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AbstractElementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;

public abstract class AbstractElementMappingImpl implements AbstractElementMapping {

    private List<AnnotationMappingImpl> annotations;

    private String id;

    private String description;

    private String name;

    private DocumentationMapping documentation;

    public List<AnnotationMappingImpl> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationMappingImpl> annotations) {
        this.annotations = annotations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentationMapping getDocumentation() {
        return documentation;
    }

    public void setDocumentation(DocumentationMapping documentation) {
        this.documentation = documentation;
    }
}
