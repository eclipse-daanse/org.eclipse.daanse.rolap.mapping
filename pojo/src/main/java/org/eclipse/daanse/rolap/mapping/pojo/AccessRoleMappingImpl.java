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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;

public class AccessRoleMappingImpl extends AbstractElementMappingImpl implements AccessRoleMapping {

    private List<AccessSchemaGrantMappingImpl> accessSchemaGrants;

    private List<AccessRoleMappingImpl> referencedAccessRoles;

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
}
