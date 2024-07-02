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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * A schema is a collection of cubes and virtual cubes. It can also contain
 * shared dimensions (for use by those cubes), named sets, roles, and
 * declarations of user-defined functions.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "parameters", "dimensions", "cubes", "virtualCubes", "namedSets", "roles",
        "documentation","measuresCaption" })
@XmlRootElement(name = "Schema")
public class Schema extends AbstractMainElement {

    @XmlElement(name = "Parameter", type = Parameter.class)
    protected List<Parameter> parameters;
    @XmlElement(name = "Dimension", type = Dimension.class)
    protected List<Dimension> dimensions;
    @XmlElement(name = "Cube", required = true, type = Cube.class)
    protected List<Cube> cubes;
    @XmlElement(name = "VirtualCube", type = VirtualCube.class)
    protected List<VirtualCube> virtualCubes;
    @XmlElement(name = "NamedSet", type = NamedSet.class)
    protected List<NamedSet> namedSets;
    @XmlElement(name = "Role", type = Role.class)
    protected List<Role> roles;
    @XmlElement(name = "Documentation", type = Documentation.class)
    protected Documentation documentation;

    protected String measuresCaption;
    @XmlAttribute(name = "defaultRole")
    protected String defaultRole;

    public List<Cube> cubes() {
        if (cubes == null) {
            cubes = new ArrayList<>();
        }
        return this.cubes;
    }

    public String defaultRole() {
        return defaultRole;
    }

    public List<Dimension> dimensions() {
        if (dimensions == null) {
            dimensions = new ArrayList<>();
        }
        return this.dimensions;
    }

    public Optional<Documentation> documentation() {
        return Optional.ofNullable(documentation);
    }

    public String measuresCaption() {
        return measuresCaption;
    }

    public List<NamedSet> namedSets() {
        if (namedSets == null) {
            namedSets = new ArrayList<>();
        }
        return this.namedSets;
    }

    public List<Parameter> parameters() {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        return this.parameters;
    }

    public List<Role> roles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return this.roles;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public void setDocumentation(Optional<Documentation> documentation) {
        this.documentation = documentation.orElse(null);
    }

    public void setMeasuresCaption(String value) {
        this.measuresCaption = value;
    }

    public void setNamedSets(List<NamedSet> namedSets) {
        this.namedSets = namedSets;

    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;

    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;

    }

    public void setVirtualCubes(List<VirtualCube> virtualCubs) {
        this.virtualCubes = virtualCubs;
    }

    public List<VirtualCube> virtualCubes() {
        if (virtualCubes == null) {
            virtualCubes = new ArrayList<>();
        }
        return this.virtualCubes;
    }

}
