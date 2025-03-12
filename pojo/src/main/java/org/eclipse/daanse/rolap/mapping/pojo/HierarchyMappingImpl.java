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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;

public class HierarchyMappingImpl extends AbstractElementMappingImpl implements HierarchyMapping {
    private List<LevelMappingImpl> levels;

    private List<MemberReaderParameterMappingImpl> memberReaderParameters;

    private String allLevelName;

    private String allMemberCaption;

    private String allMemberName;

    private String defaultMember;

    private String displayFolder;

    private boolean hasAll;

    private String memberReaderClass;

    private String origin;

    private PhysicalColumnMappingImpl primaryKey;

    private TableMapping primaryKeyTable;

    private String uniqueKeyLevelName;

    private boolean visible;

    private QueryMappingImpl query;

    private HierarchyMappingImpl(Builder builder) {
        this.levels = builder.levels;
        this.memberReaderParameters = builder.memberReaderParameters;
        this.allLevelName = builder.allLevelName;
        this.allMemberCaption = builder.allMemberCaption;
        this.allMemberName = builder.allMemberName;
        this.defaultMember = builder.defaultMember;
        this.displayFolder = builder.displayFolder;
        this.hasAll = builder.hasAll;
        this.memberReaderClass = builder.memberReaderClass;
        this.origin = builder.origin;
        this.primaryKey = builder.primaryKey;
        this.primaryKeyTable = builder.primaryKeyTable;
        this.uniqueKeyLevelName = builder.uniqueKeyLevelName;
        this.visible = builder.visible;
        this.query = builder.query;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
    }

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

    public PhysicalColumnMappingImpl getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PhysicalColumnMappingImpl primaryKey) {
        this.primaryKey = primaryKey;
    }

    public TableMapping getPrimaryKeyTable() {
        return primaryKeyTable;
    }

    public void setPrimaryKeyTable(TableMapping primaryKeyTable) {
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

    public List<LevelMappingImpl> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelMappingImpl> levels) {
        this.levels = levels;
    }

    public List<MemberReaderParameterMappingImpl> getMemberReaderParameters() {
        return memberReaderParameters;
    }

    public void setMemberReaderParameters(List<MemberReaderParameterMappingImpl> memberReaderParameters) {
        this.memberReaderParameters = memberReaderParameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<LevelMappingImpl> levels = new ArrayList<>();
        private List<MemberReaderParameterMappingImpl> memberReaderParameters = new ArrayList<>();
        private String allLevelName;
        private String allMemberCaption;
        private String allMemberName;
        private String defaultMember;
        private String displayFolder;
        private boolean hasAll;
        private String memberReaderClass;
        private String origin;
        private PhysicalColumnMappingImpl primaryKey;
        private TableMapping primaryKeyTable;
        private String uniqueKeyLevelName;
        private boolean visible;
        private QueryMappingImpl query;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;

        private Builder() {
        }

        public Builder withLevels(List<LevelMappingImpl> levels) {
            this.levels = levels;
            return this;
        }

        public Builder withMemberReaderParameters(List<MemberReaderParameterMappingImpl> memberReaderParameters) {
            this.memberReaderParameters = memberReaderParameters;
            return this;
        }

        public Builder withAllLevelName(String allLevelName) {
            this.allLevelName = allLevelName;
            return this;
        }

        public Builder withAllMemberCaption(String allMemberCaption) {
            this.allMemberCaption = allMemberCaption;
            return this;
        }

        public Builder withAllMemberName(String allMemberName) {
            this.allMemberName = allMemberName;
            return this;
        }

        public Builder withDefaultMember(String defaultMember) {
            this.defaultMember = defaultMember;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withHasAll(boolean hasAll) {
            this.hasAll = hasAll;
            return this;
        }

        public Builder withMemberReaderClass(String memberReaderClass) {
            this.memberReaderClass = memberReaderClass;
            return this;
        }

        public Builder withOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder withPrimaryKey(PhysicalColumnMappingImpl primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public Builder withPrimaryKeyTable(TableMapping primaryKeyTable) {
            this.primaryKeyTable = primaryKeyTable;
            return this;
        }

        public Builder withUniqueKeyLevelName(String uniqueKeyLevelName) {
            this.uniqueKeyLevelName = uniqueKeyLevelName;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withQuery(QueryMappingImpl query) {
            this.query = query;
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

        public HierarchyMappingImpl build() {
            return new HierarchyMappingImpl(this);
        }
    }
}
