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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rdb.structure.api.model.SqlView;
import org.eclipse.daanse.rdb.structure.pojo.SqlViewImpl;
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;

public class SqlSelectQueryMappingImpl extends RelationalQueryMappingImpl implements SqlSelectQueryMapping {

    private SqlViewImpl sql;

    private SqlSelectQueryMappingImpl(Builder builder) {
        this.sql = builder.sql;
        super.setAlias(builder.alias);
    }

    public SqlViewImpl getSql() {
        return sql;
    }

    public void setSql(SqlViewImpl sql) {
        this.sql = sql;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SqlViewImpl sql;
        private String alias;

        private Builder() {
        }

        public Builder withSql(SqlViewImpl sql) {
            this.sql = sql;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public SqlSelectQueryMappingImpl build() {
            return new SqlSelectQueryMappingImpl(this);
        }
    }
}
