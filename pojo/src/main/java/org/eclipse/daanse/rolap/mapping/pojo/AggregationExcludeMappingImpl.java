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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;

public class AggregationExcludeMappingImpl implements AggregationExcludeMapping {

    private boolean ignorecase;

    private String name;

    private String pattern;

    private String id;

    private AggregationExcludeMappingImpl(Builder builder) {
        this.ignorecase = builder.ignorecase;
        this.name = builder.name;
        this.pattern = builder.pattern;
        this.id = builder.id;
    }

    public boolean isIgnorecase() {
        return ignorecase;
    }

    public void setIgnorecase(boolean ignorecase) {
        this.ignorecase = ignorecase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean ignorecase;
        private String name;
        private String pattern;
        private String id;

        private Builder() {
        }

        public Builder withIgnorecase(boolean ignorecase) {
            this.ignorecase = ignorecase;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public AggregationExcludeMappingImpl build() {
            return new AggregationExcludeMappingImpl(this);
        }
    }
}
