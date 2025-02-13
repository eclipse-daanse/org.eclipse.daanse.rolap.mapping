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

import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;

public class SqlSelectQueryMappingImpl extends RelationalQueryMappingImpl implements SqlSelectQueryMapping {

    private SqlViewMappingImpl sql;
    private String id;


    private SqlSelectQueryMappingImpl(Builder builder) {
        this.sql = builder.sql;
        this.id = builder.id;
        super.setAlias(builder.alias);
    }

    public SqlViewMappingImpl getSql() {
        return sql;
    }

    public void setSql(SqlViewMappingImpl sql) {
        this.sql = sql;
    }

    @Override
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
        private SqlViewMappingImpl sql;
        private String alias;
        private String id;

        private Builder() {
        }

        public Builder withSql(SqlViewMappingImpl sql) {
            this.sql = sql;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public SqlSelectQueryMappingImpl build() {
            return new SqlSelectQueryMappingImpl(this);
        }
    }
}
