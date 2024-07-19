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

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseColumnMapping;

public class DatabaseColumnMappingImpl implements DatabaseColumnMapping {

    private String id;

    private String name;

    private String type;

    private List<String> typeQualifiers;

    private String description;

    private DatabaseColumnMappingImpl(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.typeQualifiers = builder.typeQualifiers;
        this.description = builder.description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTypeQualifiers() {
        return typeQualifiers;
    }

    public void setTypeQualifiers(List<String> typeQualifiers) {
        this.typeQualifiers = typeQualifiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String type;
        private List<String> typeQualifiers = Collections.emptyList();
        private String description;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withTypeQualifiers(List<String> typeQualifiers) {
            this.typeQualifiers = typeQualifiers;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DatabaseColumnMappingImpl build() {
            return new DatabaseColumnMappingImpl(this);
        }
    }
}
