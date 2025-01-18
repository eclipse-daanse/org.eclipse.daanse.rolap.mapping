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

import org.eclipse.daanse.rolap.mapping.api.model.AccessCatalogGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;

public class AccessRoleMappingImpl extends AbstractElementMappingImpl implements AccessRoleMapping {

    private List<AccessSchemaGrantMappingImpl> accessSchemaGrants;

    private List<AccessRoleMappingImpl> referencedAccessRoles;

    private AccessRoleMappingImpl(Builder builder) {
        this.accessSchemaGrants = builder.accessSchemaGrants;
        this.referencedAccessRoles = builder.referencedAccessRoles;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<AccessSchemaGrantMappingImpl> getAccessSchemaGrants() {
        return accessSchemaGrants;
    }

    public void setAccessSchemaGrants(List<AccessSchemaGrantMappingImpl> accessSchemaGrants) {
        this.accessSchemaGrants = accessSchemaGrants;
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
        private List<AccessSchemaGrantMappingImpl> accessSchemaGrants = new ArrayList<>();
        private List<AccessRoleMappingImpl> referencedAccessRoles = new ArrayList<>();
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withAccessSchemaGrants(List<AccessSchemaGrantMappingImpl> accessSchemaGrants) {
            this.accessSchemaGrants = accessSchemaGrants;
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

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public AccessRoleMappingImpl build() {
            return new AccessRoleMappingImpl(this);
        }
    }

    @Override
    public List<? extends AccessCatalogGrantMapping> getAccessCatalogGrants() {
        return null;//TODO: IMPLEMENT
    }
}
