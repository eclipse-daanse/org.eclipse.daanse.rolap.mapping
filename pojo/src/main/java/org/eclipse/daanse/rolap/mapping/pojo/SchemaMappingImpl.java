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
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;

public class SchemaMappingImpl extends AbstractElementMappingImpl implements SchemaMapping {

    private List<? extends ParameterMapping> parameters;

    private List<? extends CubeMapping> cubes;

    private List<? extends NamedSetMapping> namedSets;

    private List<? extends AccessRoleMapping> accessRoles;

    private AccessRoleMapping defaultAccessRole;

    private String measuresDimensionName;

    public List<? extends ParameterMapping> getParameters() {
        return parameters;
    }

    public void setParameters(List<? extends ParameterMapping> parameters) {
        this.parameters = parameters;
    }

    public List<? extends CubeMapping> getCubes() {
        return cubes;
    }

    public void setCubes(List<? extends CubeMapping> cubes) {
        this.cubes = cubes;
    }

    public List<? extends NamedSetMapping> getNamedSets() {
        return namedSets;
    }

    public void setNamedSets(List<? extends NamedSetMapping> namedSets) {
        this.namedSets = namedSets;
    }

    public List<? extends AccessRoleMapping> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<? extends AccessRoleMapping> accessRoles) {
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
