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

import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;

public class SQLExpressionMappingImpl implements SQLExpressionMapping {

    private List<SQLMappingImpl> sqls;

    private SQLExpressionMappingImpl(Builder builder) {
        this.sqls = builder.sqls;
    }

    public List<SQLMappingImpl> getSqls() {
        return sqls;
    }

    public void setSqls(List<SQLMappingImpl> sqls) {
        this.sqls = sqls;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<SQLMappingImpl> sqls = new ArrayList<>();

        private Builder() {
        }

        public Builder withSqls(List<SQLMappingImpl> sqls) {
            this.sqls = sqls;
            return this;
        }

        public SQLExpressionMappingImpl build() {
            return new SQLExpressionMappingImpl(this);
        }
    }
}
