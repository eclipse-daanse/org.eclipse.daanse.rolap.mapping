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

import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;

public class InlineTableQueryMappingImpl extends RelationalQueryMappingImpl implements InlineTableQueryMapping {

    private InlineTableMappingImpl table;

    private DocumentationMappingImpl documentation;

    private String id;


    private InlineTableQueryMappingImpl(Builder builder) {
        this.table = builder.table;
        this.documentation = builder.documentation;
        this.id = builder.id;
        super.setAlias(builder.alias);
    }

    public InlineTableMappingImpl getTable() {
        return table;
    }

    public void setTable(InlineTableMappingImpl table) {
        this.table = table;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public DocumentationMapping getDocumentation() {
        return documentation;
    }

    public void setDocumentation(DocumentationMappingImpl documentation) {
        this.documentation = documentation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private InlineTableMappingImpl table;
        private String alias;
        private DocumentationMappingImpl documentation;
        private String id;


        private Builder() {
        }

        public Builder withTable(InlineTableMappingImpl table) {
            this.table = table;
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

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public InlineTableQueryMappingImpl build() {
            return new InlineTableQueryMappingImpl(this);
        }
    }
}
