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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Table", propOrder = { "sql", "aggExcludes", "aggTables", "hints" })
@XmlRootElement(name = "Table")
public class Table implements RelationOrJoin {

    @XmlElement(name = "SQL", type = SQL.class)
    protected SQL sql;
    @XmlElement(name = "AggExclude", type = AggExclude.class)
    protected List<AggExclude> aggExcludes;
    @XmlElements({ @XmlElement(name = "AggName", type = AggName.class),
            @XmlElement(name = "AggPattern", type = AggPattern.class) })
    protected List<AggTable> aggTables;
    @XmlElement(name = "Hint", type = Hint.class)
    protected List<Hint> hints;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "schema")
    protected String schema;
    @XmlAttribute(name = "alias")
    protected String alias;

    public List<AggExclude> aggExcludes() {
        if (aggExcludes == null) {
            aggExcludes = new ArrayList<>();
        }
        return this.aggExcludes;
    }

    public List<AggTable> aggTables() {
        if (aggTables == null) {
            aggTables = new ArrayList<>();
        }
        return this.aggTables;
    }

    public String alias() {
        return alias;
    }

    public List<Hint> hints() {
        if (hints == null) {
            hints = new ArrayList<>();
        }
        return this.hints;
    }

    public String name() {
        return name;
    }

    public String schema() {
        return schema;
    }

    public void setAggExcludes(List<AggExclude> aggExcludes) {
        this.aggExcludes = aggExcludes;
    }

    public void setAggTables(List<AggTable> aggTables) {
        this.aggTables = aggTables;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setSchema(String value) {
        this.schema = value;
    }

    public void setSql(SQL value) {
        this.sql = value;
    }

    public SQL sql() {
        return sql;
    }
}
