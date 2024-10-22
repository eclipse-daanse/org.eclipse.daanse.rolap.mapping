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

import org.eclipse.daanse.rdb.structure.pojo.InlineTableImpl;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;

public class InlineTableQueryMappingImpl extends RelationalQueryMappingImpl implements InlineTableQueryMapping {

    private InlineTableImpl table;

    private InlineTableQueryMappingImpl(Builder builder) {
        this.table = builder.table;
        super.setAlias(builder.alias);
    }

    public InlineTableImpl getTable() {
        return table;
    }

    public void setTable(InlineTableImpl table) {
        this.table = table;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private InlineTableImpl table;
        private String alias;

        private Builder() {
        }

        public Builder withTable(InlineTableImpl table) {
            this.table = table;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public InlineTableQueryMappingImpl build() {
            return new InlineTableQueryMappingImpl(this);
        }
    }
}
