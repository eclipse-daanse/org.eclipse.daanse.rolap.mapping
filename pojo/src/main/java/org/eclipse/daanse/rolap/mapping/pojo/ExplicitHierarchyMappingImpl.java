/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.rolap.mapping.api.model.ExplicitHierarchyMapping;

public class ExplicitHierarchyMappingImpl extends HierarchyMappingImpl implements ExplicitHierarchyMapping{
    private List<LevelMappingImpl> levels;

    private ExplicitHierarchyMappingImpl(Builder builder) {
        this.levels = builder.levels;
        super.setMemberReaderParameters(builder.memberReaderParameters);
        super.setAllLevelName(builder.allLevelName);
        super.setAllMemberCaption(builder.allMemberCaption);
        super.setAllMemberName(builder.allMemberName);
        super.setDefaultMember(builder.defaultMember);
        super.setDisplayFolder(builder.displayFolder);
        super.setHasAll(builder.hasAll);
        super.setMemberReaderClass(builder.memberReaderClass);
        super.setOrigin(builder.origin);
        super.setPrimaryKey(builder.primaryKey);
        super.setUniqueKeyLevelName(builder.uniqueKeyLevelName);
        super.setVisible(builder.visible);
        super.setQuery(builder.query);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
    }

    public List<LevelMappingImpl> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelMappingImpl> levels) {
        this.levels = levels;
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

        public ExplicitHierarchyMappingImpl build() {
            return new ExplicitHierarchyMappingImpl(this);
        }
    }
}
