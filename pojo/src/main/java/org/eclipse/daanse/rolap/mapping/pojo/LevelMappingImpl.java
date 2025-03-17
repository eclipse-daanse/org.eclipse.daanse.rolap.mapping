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

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;

public class LevelMappingImpl extends AbstractElementMappingImpl implements LevelMapping {

    private ParentChildLinkMappingImpl parentChildLink;

    private List<MemberPropertyMappingImpl> memberProperties;

    private MemberFormatterMappingImpl memberFormatter;

    private String approxRowCount;

    private ColumnMapping captionColumn;

    private ColumnMapping column;

    private HideMemberIfType hideMemberIfType;

    private LevelType levelType;

    private ColumnMapping nameColumn;

    private String nullParentValue;

    private ColumnMapping ordinalColumn;

    private ColumnMapping parentColumn;

    private InternalDataType dataType;

    private boolean uniqueMembers;

    private boolean visible;


    private LevelMappingImpl(Builder builder) {
        this.parentChildLink = builder.parentChildLink;
        this.memberProperties = builder.memberProperties;
        this.memberFormatter = builder.memberFormatter;
        this.approxRowCount = builder.approxRowCount;
        this.captionColumn = builder.captionColumn;
        this.column = builder.column;
        this.hideMemberIfType = builder.hideMemberIfType;
        this.levelType = builder.levelType;
        this.nameColumn = builder.nameColumn;
        this.nullParentValue = builder.nullParentValue;
        this.ordinalColumn = builder.ordinalColumn;
        this.parentColumn = builder.parentColumn;
        this.dataType = builder.dataType;
        this.uniqueMembers = builder.uniqueMembers;
        this.visible = builder.visible;
        super.setName(builder.name);
        super.setDescription(builder.description);
        super.setId(builder.id);
        super.setAnnotations(builder.annotations);
    }

    public ParentChildLinkMappingImpl getParentChildLink() {
        return parentChildLink;
    }

    public void setParentChildLink(ParentChildLinkMappingImpl parentChildLink) {
        this.parentChildLink = parentChildLink;
    }

    public List<MemberPropertyMappingImpl> getMemberProperties() {
        return memberProperties;
    }

    public void setMemberProperties(List<MemberPropertyMappingImpl> memberProperties) {
        this.memberProperties = memberProperties;
    }

    public MemberFormatterMappingImpl getMemberFormatter() {
        return memberFormatter;
    }

    public void setMemberFormatter(MemberFormatterMappingImpl memberFormatter) {
        this.memberFormatter = memberFormatter;
    }

    public String getApproxRowCount() {
        return approxRowCount;
    }

    public void setApproxRowCount(String approxRowCount) {
        this.approxRowCount = approxRowCount;
    }

    public ColumnMapping getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn (ColumnMapping captionColumn) {
        this.captionColumn = captionColumn;
    }

    public ColumnMapping getColumn() {
        return column;
    }

    public void setColumn (ColumnMapping column) {
        this.column = column;
    }

    public HideMemberIfType getHideMemberIfType() {
        if (hideMemberIfType == null) {
            return HideMemberIfType.NEVER;
        }
        return hideMemberIfType;
    }

    public void setHideMemberIfType(HideMemberIfType hideMemberIf) {
        this.hideMemberIfType = hideMemberIf;
    }

    public LevelType getLevelType() {
        if (levelType == null) {
            return LevelType.REGULAR;
        } else {
            return levelType;
        }
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public ColumnMapping getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn (ColumnMapping nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getNullParentValue() {
        return nullParentValue;
    }

    public void setNullParentValue(String nullParentValue) {
        this.nullParentValue = nullParentValue;
    }

    public ColumnMapping getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn (ColumnMapping ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public ColumnMapping getParentColumn() {
        return parentColumn;
    }

    public void setParentColumn (ColumnMapping parentColumn) {
        this.parentColumn = parentColumn;
    }

    public InternalDataType getDataType() {
        return this.dataType != null ? this.dataType : InternalDataType.STRING;
    }

    public void setDataType(InternalDataType dataType) {
        this.dataType = dataType;
    }

    public boolean isUniqueMembers() {
        return uniqueMembers;
    }

    public void setUniqueMembers(boolean uniqueMembers) {
        this.uniqueMembers = uniqueMembers;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ParentChildLinkMappingImpl parentChildLink;
        private List<MemberPropertyMappingImpl> memberProperties = new ArrayList<>();
        private MemberFormatterMappingImpl memberFormatter;
        private String approxRowCount;
        private ColumnMapping captionColumn;
        private ColumnMapping column;
        private HideMemberIfType hideMemberIfType;
        private LevelType levelType;
        private ColumnMapping nameColumn;
        private String nullParentValue;
        private ColumnMapping ordinalColumn;
        private ColumnMapping parentColumn;
        private InternalDataType dataType;
        private boolean uniqueMembers;
        private boolean visible;
        private String name;
        private String description;
        private String id;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();

        private Builder() {
        }

        public Builder withParentChildLink(ParentChildLinkMappingImpl parentChildLink) {
            this.parentChildLink = parentChildLink;
            return this;
        }

        public Builder withMemberProperties(List<MemberPropertyMappingImpl> memberProperties) {
            this.memberProperties = memberProperties;
            return this;
        }

        public Builder withMemberFormatter(MemberFormatterMappingImpl memberFormatter) {
            this.memberFormatter = memberFormatter;
            return this;
        }

        public Builder withApproxRowCount(String approxRowCount) {
            this.approxRowCount = approxRowCount;
            return this;
        }

        public Builder withCaptionColumn (ColumnMapping captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withColumn (ColumnMapping column) {
            this.column = column;
            return this;
        }

        public Builder withHideMemberIfType(HideMemberIfType hideMemberIfType) {
            this.hideMemberIfType = hideMemberIfType;
            return this;
        }

        public Builder withLevelType(LevelType levelType) {
            this.levelType = levelType;
            return this;
        }

        public Builder withNameColumn (ColumnMapping nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withNullParentValue(String nullParentValue) {
            this.nullParentValue = nullParentValue;
            return this;
        }

        public Builder withOrdinalColumn (ColumnMapping ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public Builder withParentColumn (ColumnMapping parentColumn) {
            this.parentColumn = parentColumn;
            return this;
        }

        public Builder withType(InternalDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder withUniqueMembers(boolean uniqueMembers) {
            this.uniqueMembers = uniqueMembers;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public LevelMappingImpl build() {
            return new LevelMappingImpl(this);
        }
    }

}
