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
import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;

public class AccessRoleMappingImpl extends AbstractElementMappingImpl implements AccessRoleMapping {

    private List<? extends AccessSchemaGrantMapping> accessSchemaGrants;

    private List<? extends AccessRoleMapping> referencedAccessRoles;

    public List<? extends AccessSchemaGrantMapping> getAccessSchemaGrants() {
        return accessSchemaGrants;
    }

    public void setAccessSchemaGrants(List<? extends AccessSchemaGrantMapping> accessSchemaGrants) {
        this.accessSchemaGrants = accessSchemaGrants;
    }

    public List<? extends AccessRoleMapping> getReferencedAccessRoles() {
        return referencedAccessRoles;
    }

    public void setReferencedAccessRoles(List<? extends AccessRoleMapping> referencedAccessRoles) {
        this.referencedAccessRoles = referencedAccessRoles;
    }
}
