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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;

public class JoinedQueryElementMappingImpl implements JoinedQueryElementMapping {

    private String alias;

    private Column key;

    private QueryMappingImpl query;

    private JoinedQueryElementMappingImpl(Builder builder) {
        this.alias = builder.alias;
        this.key = builder.key;
        this.query = builder.query;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Column getKey() {
        return key;
    }

    public void setKey(Column key) {
        this.key = key;
    }

    public QueryMappingImpl getQuery() {
        return query;
    }

    public void setQuery(QueryMappingImpl query) {
        this.query = query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String alias;
        private Column key;
        private QueryMappingImpl query;

        private Builder() {
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder withKey(Column key) {
            this.key = key;
            return this;
        }

        public Builder withQuery(QueryMappingImpl query) {
            this.query = query;
            return this;
        }

        public JoinedQueryElementMappingImpl build() {
            return new JoinedQueryElementMappingImpl(this);
        }
    }
}
