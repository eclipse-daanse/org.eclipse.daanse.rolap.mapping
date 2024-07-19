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

import org.eclipse.daanse.rolap.mapping.api.model.SQLMapping;

public class SQLMappingImpl implements SQLMapping {

    private List<String> dialects;

    private String statement;

    private SQLMappingImpl(Builder builder) {
        this.dialects = builder.dialects;
        this.statement = builder.statement;
    }

    public List<String> getDialects() {
        return dialects;
    }

    public void setDialects(List<String> dialects) {
        this.dialects = dialects;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<String> dialects = Collections.emptyList();
        private String statement;

        private Builder() {
        }

        public Builder withDialects(List<String> dialects) {
            this.dialects = dialects;
            return this;
        }

        public Builder withStatement(String statement) {
            this.statement = statement;
            return this;
        }

        public SQLMappingImpl build() {
            return new SQLMappingImpl(this);
        }
    }
}
