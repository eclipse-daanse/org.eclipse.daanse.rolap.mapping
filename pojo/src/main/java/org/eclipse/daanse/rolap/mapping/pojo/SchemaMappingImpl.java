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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;

public class SchemaMappingImpl extends AbstractElementMappingImpl implements SchemaMapping {

    private List<ParameterMappingImpl> parameters;

    private List<? extends CubeMappingImpl> cubes;

    private List<NamedSetMappingImpl> namedSets;

    private List<AccessRoleMappingImpl> accessRoles;

    private AccessRoleMappingImpl defaultAccessRole;

    private String measuresDimensionName;

    private SchemaMappingImpl(Builder builder) {
        this.parameters = builder.parameters;
        this.cubes = builder.cubes;
        this.namedSets = builder.namedSets;
        this.accessRoles = builder.accessRoles;
        this.defaultAccessRole = builder.defaultAccessRole;
        this.measuresDimensionName = builder.measuresDimensionName;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<ParameterMappingImpl> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterMappingImpl> parameters) {
        this.parameters = parameters;
    }

    public List<? extends CubeMappingImpl> getCubes() {
        return cubes;
    }

    public void setCubes(List<? extends CubeMappingImpl> cubes) {
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

    public AccessRoleMappingImpl getDefaultAccessRole() {
        return defaultAccessRole;
    }

    public void setDefaultAccessRole(AccessRoleMappingImpl defaultAccessRole) {
        this.defaultAccessRole = defaultAccessRole;
    }

    public String getMeasuresDimensionName() {
        return measuresDimensionName;
    }

    public void setMeasuresDimensionName(String measuresDimensionName) {
        this.measuresDimensionName = measuresDimensionName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<ParameterMappingImpl> parameters = Collections.emptyList();
        private List<CubeMappingImpl> cubes = Collections.emptyList();
        private List<NamedSetMappingImpl> namedSets = Collections.emptyList();
        private List<AccessRoleMappingImpl> accessRoles = Collections.emptyList();
        private AccessRoleMappingImpl defaultAccessRole;
        private String measuresDimensionName;
        private List<AnnotationMappingImpl> annotations = Collections.emptyList();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withParameters(List<ParameterMappingImpl> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder withCubes(List<CubeMappingImpl> cubes) {
            this.cubes = cubes;
            return this;
        }

        public Builder withNamedSets(List<NamedSetMappingImpl> namedSets) {
            this.namedSets = namedSets;
            return this;
        }

        public Builder withAccessRoles(List<AccessRoleMappingImpl> accessRoles) {
            this.accessRoles = accessRoles;
            return this;
        }

        public Builder withDefaultAccessRole(AccessRoleMappingImpl defaultAccessRole) {
            this.defaultAccessRole = defaultAccessRole;
            return this;
        }

        public Builder withMeasuresDimensionName(String measuresDimensionName) {
            this.measuresDimensionName = measuresDimensionName;
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

        public SchemaMappingImpl build() {
            return new SchemaMappingImpl(this);
        }
    }
}
