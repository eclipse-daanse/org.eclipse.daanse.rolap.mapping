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
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;

public class SchemaMappingImpl extends AbstractElementMappingImpl implements SchemaMapping {

    private List<ParameterMappingImpl> parameters;

    private List<CubeMappingImpl> cubes;

    private List<NamedSetMappingImpl> namedSets;

    private List<AccessRoleMappingImpl> accessRoles;

    private AccessRoleMapping defaultAccessRole;

    private String measuresDimensionName;

    public List<ParameterMappingImpl> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterMappingImpl> parameters) {
        this.parameters = parameters;
    }

    public List<CubeMappingImpl> getCubes() {
        return cubes;
    }

    public void setCubes(List<CubeMappingImpl> cubes) {
        this.cubes = cubes;
    }

    public List<NamedSetMappingImpl> getNamedSets() {
        return namedSets;
    }

    public void setNamedSets(List<NamedSetMappingImpl> namedSets) {
        this.namedSets = namedSets;
    }

    public List<AccessRoleMappingImpl> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<AccessRoleMappingImpl> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public AccessRoleMapping getDefaultAccessRole() {
        return defaultAccessRole;
    }

    public void setDefaultAccessRole(AccessRoleMapping defaultAccessRole) {
        this.defaultAccessRole = defaultAccessRole;
    }

    public String getMeasuresDimensionName() {
        return measuresDimensionName;
    }

    public void setMeasuresDimensionName(String measuresDimensionName) {
        this.measuresDimensionName = measuresDimensionName;
    }
}
