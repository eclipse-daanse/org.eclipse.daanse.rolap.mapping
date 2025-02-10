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

import org.eclipse.daanse.rolap.mapping.api.model.LinkMapping;

public class LinkMappingImpl implements LinkMapping {

    public LinkMappingImpl(Builder builder) {
        this.primaryKey = builder.primaryKey;
        this.foreignKey = builder.foreignKey;
    }

    public ColumnMappingImpl getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnMappingImpl primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ColumnMappingImpl getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ColumnMappingImpl foreignKey) {
        this.foreignKey = foreignKey;
    }

    private ColumnMappingImpl primaryKey;

    private ColumnMappingImpl foreignKey;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ColumnMappingImpl primaryKey;

        private ColumnMappingImpl foreignKey;

        public Builder withPrimaryKey(ColumnMappingImpl primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public Builder withForeignKey(ColumnMappingImpl foreignKey) {
            this.foreignKey = foreignKey;
            return this;
        }

        public LinkMappingImpl build() {
            return new LinkMappingImpl(this);
        }
    }

}
