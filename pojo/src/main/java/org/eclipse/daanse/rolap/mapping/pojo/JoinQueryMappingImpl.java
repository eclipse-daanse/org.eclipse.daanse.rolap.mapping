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

import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;

public class JoinQueryMappingImpl extends QueryMappingImpl implements JoinQueryMapping {

    private JoinedQueryElementMappingImpl left;

    private JoinedQueryElementMappingImpl right;

    private String id;

    private JoinQueryMappingImpl(Builder builder) {
        this.left = builder.left;
        this.right = builder.right;
        this.id = builder.id;
    }

    public JoinedQueryElementMappingImpl getLeft() {
        return left;
    }

    public void setLeft(JoinedQueryElementMappingImpl left) {
        this.left = left;
    }

    public JoinedQueryElementMappingImpl getRight() {
        return right;
    }

    public void setRight(JoinedQueryElementMappingImpl right) {
        this.right = right;
    }

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
        private JoinedQueryElementMappingImpl left;
        private JoinedQueryElementMappingImpl right;
        private String id;

        private Builder() {
        }

        public Builder withLeft(JoinedQueryElementMappingImpl left) {
            this.left = left;
            return this;
        }

        public Builder withRight(JoinedQueryElementMappingImpl right) {
            this.right = right;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public JoinQueryMappingImpl build() {
            return new JoinQueryMappingImpl(this);
        }
    }
}
