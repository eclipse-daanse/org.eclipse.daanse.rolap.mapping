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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseCatalogMapping;

public class DatabaseCatalogImpl implements DatabaseCatalogMapping {

    private List<DatabaseSchemaMappingImpl> schemas;

    public DatabaseCatalogImpl(Builder builder) {
        this.schemas = builder.schemas;
        this.links = builder.links;
    }

    public List<DatabaseSchemaMappingImpl> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<DatabaseSchemaMappingImpl> schemas) {
        this.schemas = schemas;
    }

    public List<LinkMappingImpl> getLinks() {
        return links;
    }

    public void setLinks(List<LinkMappingImpl> links) {
        this.links = links;
    }

    private List<LinkMappingImpl> links;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private List<DatabaseSchemaMappingImpl> schemas;
        private List<LinkMappingImpl> links;

        public Builder withSchemas(List<DatabaseSchemaMappingImpl> schemas) {
            this.schemas = schemas;
            return this;
        }

        public Builder withLinks(List<LinkMappingImpl> links) {
            this.links = links;
            return this;
        }

        public DatabaseCatalogImpl build() {
            return new DatabaseCatalogImpl(this);
        }
    }

}
