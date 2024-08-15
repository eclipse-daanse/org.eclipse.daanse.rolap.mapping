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

import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;

public class AccessMemberGrantMappingImpl implements AccessMemberGrantMapping {

    private AccessMember access;

    private String member;

    private AccessMemberGrantMappingImpl(Builder builder) {
        this.access = builder.access;
        this.member = builder.member;
    }

    public AccessMember getAccess() {
        return access;
    }

    public void setAccess(AccessMember access) {
        this.access = access;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private AccessMember access;
        private String member;

        private Builder() {
        }

        public Builder withAccess(AccessMember access) {
            this.access = access;
            return this;
        }

        public Builder withMember(String member) {
            this.member = member;
            return this;
        }

        public AccessMemberGrantMappingImpl build() {
            return new AccessMemberGrantMappingImpl(this);
        }
    }
}
