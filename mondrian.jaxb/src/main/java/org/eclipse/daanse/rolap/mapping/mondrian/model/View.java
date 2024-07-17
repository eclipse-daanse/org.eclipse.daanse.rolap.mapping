
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
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "View", propOrder = { "sqls" })
public class View implements RelationOrJoin {

    @XmlElement(name = "SQL", required = true, type = SQL.class)
    protected List<SQL> sqls;
    @XmlAttribute(name = "alias", required = true)
    protected String alias;

    public void addCode(String dialect, String code) {
        if (sqls == null) {
            sqls = new ArrayList<>();
        }
        SQL sql = new SQL();
        sql.setDialect(dialect);
        sql.setContent(code);
        sqls.add(sql);
    }

    public String alias() {
        return alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public void setSqls(List<SQL> sqls) {
        this.sqls = sqls;
    }

    public List<SQL> sqls() {
        if (sqls == null) {
            sqls = new ArrayList<>();
        }
        return this.sqls;
    }
}
