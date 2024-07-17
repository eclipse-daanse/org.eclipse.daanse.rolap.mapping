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

import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;

public class HierarchyMappingImpl extends AbstractElementMappingImpl implements HierarchyMapping {
    private List<? extends LevelMapping> levels;

    private List<? extends MemberReaderParameterMappingImpl> memberReaderParameters;

    private String allLevelName;

    private String allMemberCaption;

    private String allMemberName;

    private String defaultMember;

    private String displayFolder;

    private boolean hasAll;

    private String memberReaderClass;

    private String origin;

    private String primaryKey;

    private String primaryKeyTable;

    private String uniqueKeyLevelName;

    private boolean visible;

    private QueryMappingImpl query;

    public String getAllLevelName() {
        return allLevelName;
    }

    public void setAllLevelName(String allLevelName) {
        this.allLevelName = allLevelName;
    }

    public String getAllMemberCaption() {
        return allMemberCaption;
    }

    public void setAllMemberCaption(String allMemberCaption) {
        this.allMemberCaption = allMemberCaption;
    }

    public String getAllMemberName() {
        return allMemberName;
    }

    public void setAllMemberName(String allMemberName) {
        this.allMemberName = allMemberName;
    }

    public String getDefaultMember() {
        return defaultMember;
    }

    public void setDefaultMember(String defaultMember) {
        this.defaultMember = defaultMember;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public boolean isHasAll() {
        return hasAll;
    }

    public void setHasAll(boolean hasAll) {
        this.hasAll = hasAll;
    }

    public String getMemberReaderClass() {
        return memberReaderClass;
    }

    public void setMemberReaderClass(String memberReaderClass) {
        this.memberReaderClass = memberReaderClass;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKeyTable() {
        return primaryKeyTable;
    }

    public void setPrimaryKeyTable(String primaryKeyTable) {
        this.primaryKeyTable = primaryKeyTable;
    }

    public String getUniqueKeyLevelName() {
        return uniqueKeyLevelName;
    }

    public void setUniqueKeyLevelName(String uniqueKeyLevelName) {
        this.uniqueKeyLevelName = uniqueKeyLevelName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public QueryMappingImpl getQuery() {
        return query;
    }

    public void setQuery(QueryMappingImpl query) {
        this.query = query;
    }

    public List<? extends LevelMapping> getLevels() {
        return levels;
    }

    public void setLevels(List<? extends LevelMapping> levels) {
        this.levels = levels;
    }

    public List<? extends MemberReaderParameterMappingImpl> getMemberReaderParameters() {
        return memberReaderParameters;
    }

    public void setMemberReaderParameters(List<? extends MemberReaderParameterMappingImpl> memberReaderParameters) {
        this.memberReaderParameters = memberReaderParameters;
    }
}
