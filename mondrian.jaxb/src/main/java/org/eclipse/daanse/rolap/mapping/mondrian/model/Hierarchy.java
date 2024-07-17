
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
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hierarchy", propOrder = { "relation", "levels", "memberReaderParameters" })
public class Hierarchy extends AbstractMainElement {

    @XmlElement(name = "Level", required = true, type = Level.class)
    protected List<Level> levels;
    @XmlElement(name = "MemberReaderParameter", type = MemberReaderParameter.class)
    protected List<MemberReaderParameter> memberReaderParameters;

    @XmlAttribute(name = "hasAll", required = true)
    protected boolean hasAll;
    @XmlAttribute(name = "allMemberName")
    protected String allMemberName;
    @XmlAttribute(name = "allMemberCaption")
    protected String allMemberCaption;
    @XmlAttribute(name = "allLevelName")
    protected String allLevelName;
    @XmlAttribute(name = "primaryKey")
    protected String primaryKey;
    @XmlAttribute(name = "primaryKeyTable")
    protected String primaryKeyTable;
    @XmlAttribute(name = "defaultMember")
    protected String defaultMember;
    @XmlAttribute(name = "memberReaderClass")
    protected String memberReaderClass;

    @XmlAttribute(name = "uniqueKeyLevelName")
    protected String uniqueKeyLevelName;
    @XmlAttribute(name = "visible")
    private boolean visible = true;
    @XmlAttribute(name = "displayFolder")
    private String displayFolder;
    @XmlAttribute(name = "origin")
    private String origin;
    @XmlElements({ @XmlElement(name = "Table", type = Table.class), @XmlElement(name = "View", type = View.class),
            @XmlElement(name = "Join", type = Join.class),
            @XmlElement(name = "InlineTable", type = InlineTable.class) })
    protected RelationOrJoin relation;

    public String allLevelName() {
        return allLevelName;
    }

    public String allMemberCaption() {
        return allMemberCaption;
    }

    public String allMemberName() {
        return allMemberName;
    }

    public String defaultMember() {
        return defaultMember;
    }

    public String displayFolder() {
        return displayFolder;
    }

    public Boolean hasAll() {
        return hasAll;
    }

    public List<Level> levels() {
        if (levels == null) {
            levels = new ArrayList<>();
        }
        return this.levels;
    }

    public String memberReaderClass() {
        return memberReaderClass;
    }

    public List<MemberReaderParameter> memberReaderParameters() {
        if (memberReaderParameters == null) {
            memberReaderParameters = new ArrayList<>();
        }
        return this.memberReaderParameters;
    }

    public String origin() {
        return origin;
    }

    public String primaryKey() {
        return primaryKey;
    }

    public String primaryKeyTable() {
        return primaryKeyTable;
    }

    public RelationOrJoin relation() {
        return relation;
    }

    public void setAllLevelName(String value) {
        this.allLevelName = value;
    }

    public void setAllMemberCaption(String value) {
        this.allMemberCaption = value;
    }

    public void setAllMemberName(String value) {
        this.allMemberName = value;
    }

    public void setDefaultMember(String value) {
        this.defaultMember = value;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public void setHasAll(Boolean value) {
        this.hasAll = value;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public void setMemberReaderClass(String value) {
        this.memberReaderClass = value;
    }

    public void setMemberReaderParameters(List<MemberReaderParameter> memberReaderParameters) {
        this.memberReaderParameters = memberReaderParameters;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrimaryKey(String value) {
        this.primaryKey = value;
    }

    public void setPrimaryKeyTable(String value) {
        this.primaryKeyTable = value;
    }

    public void setRelation(RelationOrJoin relation) {
        this.relation = relation;
    }

    public void setUniqueKeyLevelName(String value) {
        this.uniqueKeyLevelName = value;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String uniqueKeyLevelName() {
        return uniqueKeyLevelName;
    }

    public Boolean visible() {
        return visible;
    }

}
