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

import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;

public class AccessRoleMappingImpl extends AbstractElementMappingImpl implements AccessRoleMapping {

    private List<AccessCatalogGrantMappingImpl> accessCatalogGrants;

    private List<AccessRoleMappingImpl> referencedAccessRoles;

    private AccessRoleMappingImpl(Builder builder) {
        this.accessCatalogGrants = builder.accessCatalogGrants;
        this.referencedAccessRoles = builder.referencedAccessRoles;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
    }

    public List<AccessCatalogGrantMappingImpl> getAccessCatalogGrants() {
        return accessCatalogGrants;
    }

    public void setAccessCatalogGrants(List<AccessCatalogGrantMappingImpl> accessCatalogGrants) {
        this.accessCatalogGrants = accessCatalogGrants;
    }

    public List<AccessRoleMappingImpl> getReferencedAccessRoles() {
        return referencedAccessRoles;
    }

    public void setReferencedAccessRoles(List<AccessRoleMappingImpl> referencedAccessRoles) {
        this.referencedAccessRoles = referencedAccessRoles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<AccessCatalogGrantMappingImpl> accessCatalogGrants = new ArrayList<>();
        private List<AccessRoleMappingImpl> referencedAccessRoles = new ArrayList<>();
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;

        private Builder() {
        }

        public Builder withAccessCatalogGrants(List<AccessCatalogGrantMappingImpl> accessCatalogGrants) {
            this.accessCatalogGrants = accessCatalogGrants;
            return this;
        }

        public Builder withReferencedAccessRoles(List<AccessRoleMappingImpl> referencedAccessRoles) {
            this.referencedAccessRoles = referencedAccessRoles;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public AccessRoleMappingImpl build() {
            return new AccessRoleMappingImpl(this);
        }
    }
}
