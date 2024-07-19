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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;

public class TranslationMappingImpl implements TranslationMapping {

    private long language;

    private String caption;

    private String description;

    private String displayFolder;

    private List<AnnotationMappingImpl> annotations;

    private TranslationMappingImpl(Builder builder) {
        this.language = builder.language;
        this.caption = builder.caption;
        this.description = builder.description;
        this.displayFolder = builder.displayFolder;
        this.annotations = builder.annotations;
    }

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long language) {
        this.language = language;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public List<AnnotationMappingImpl> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationMappingImpl> annotations) {
        this.annotations = annotations;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long language;
        private String caption;
        private String description;
        private String displayFolder;
        private List<AnnotationMappingImpl> annotations = Collections.emptyList();

        private Builder() {
        }

        public Builder withLanguage(long language) {
            this.language = language;
            return this;
        }

        public Builder withCaption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public TranslationMappingImpl build() {
            return new TranslationMappingImpl(this);
        }
    }
}
