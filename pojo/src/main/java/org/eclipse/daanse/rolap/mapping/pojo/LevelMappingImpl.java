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

import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;

public class LevelMappingImpl extends AbstractElementMappingImpl implements LevelMapping {

    private SQLExpressionMappingImpl keyExpression;

    private SQLExpressionMappingImpl nameExpression;

    private SQLExpressionMappingImpl captionExpression;

    private SQLExpressionMappingImpl ordinalExpression;

    private SQLExpressionMappingImpl parentExpression;

    private ParentChildLinkMappingImpl parentChildLink;

    private List<MemberPropertyMappingImpl> memberProperties;

    private MemberFormatterMappingImpl memberFormatter;

    private String approxRowCount;

    private String captionColumn;

    private String column;

    private HideMemberIfType hideMemberIfType;

    private LevelType levelType;

    private String nameColumn;

    private String nullParentValue;

    private String ordinalColumn;

    private String parentColumn;

    private String table;

    private DataType dataType;

    private boolean uniqueMembers;

    private boolean visible;

    private String name;

    private String description;

    private String id;

    private LevelMappingImpl(Builder builder) {
        this.keyExpression = builder.keyExpression;
        this.nameExpression = builder.nameExpression;
        this.captionExpression = builder.captionExpression;
        this.ordinalExpression = builder.ordinalExpression;
        this.parentExpression = builder.parentExpression;
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
        this.table = builder.table;
        this.dataType = builder.dataType;
        this.uniqueMembers = builder.uniqueMembers;
        this.visible = builder.visible;
        this.name = builder.name;
        this.description = builder.description;
        this.id = builder.id;
        super.setAnnotations(builder.annotations);
        super.setDocumentation(builder.documentation);
    }

    public SQLExpressionMappingImpl getKeyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(SQLExpressionMappingImpl keyExpression) {
        this.keyExpression = keyExpression;
    }

    public SQLExpressionMappingImpl getNameExpression() {
        return nameExpression;
    }

    public void setNameExpression(SQLExpressionMappingImpl nameExpression) {
        this.nameExpression = nameExpression;
    }

    public SQLExpressionMappingImpl getCaptionExpression() {
        return captionExpression;
    }

    public void setCaptionExpression(SQLExpressionMappingImpl captionExpression) {
        this.captionExpression = captionExpression;
    }

    public SQLExpressionMappingImpl getOrdinalExpression() {
        return ordinalExpression;
    }

    public void setOrdinalExpression(SQLExpressionMappingImpl ordinalExpression) {
        this.ordinalExpression = ordinalExpression;
    }

    public SQLExpressionMappingImpl getParentExpression() {
        return parentExpression;
    }

    public void setParentExpression(SQLExpressionMappingImpl parentExpression) {
        this.parentExpression = parentExpression;
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

    public String getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn(String captionColumn) {
        this.captionColumn = captionColumn;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
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

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getNullParentValue() {
        return nullParentValue;
    }

    public void setNullParentValue(String nullParentValue) {
        this.nullParentValue = nullParentValue;
    }

    public String getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn(String ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public String getParentColumn() {
        return parentColumn;
    }

    public void setParentColumn(String parentColumn) {
        this.parentColumn = parentColumn;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public DataType getDataType() {
        return this.dataType != null ? this.dataType : DataType.STRING;
    }

    public void setDataType(DataType dataType) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SQLExpressionMappingImpl keyExpression;
        private SQLExpressionMappingImpl nameExpression;
        private SQLExpressionMappingImpl captionExpression;
        private SQLExpressionMappingImpl ordinalExpression;
        private SQLExpressionMappingImpl parentExpression;
        private ParentChildLinkMappingImpl parentChildLink;
        private List<MemberPropertyMappingImpl> memberProperties = new ArrayList<>();
        private MemberFormatterMappingImpl memberFormatter;
        private String approxRowCount;
        private String captionColumn;
        private String column;
        private HideMemberIfType hideMemberIfType;
        private LevelType levelType;
        private String nameColumn;
        private String nullParentValue;
        private String ordinalColumn;
        private String parentColumn;
        private String table;
        private DataType dataType;
        private boolean uniqueMembers;
        private boolean visible;
        private String name;
        private String description;
        private String id;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withKeyExpression(SQLExpressionMappingImpl keyExpression) {
            this.keyExpression = keyExpression;
            return this;
        }

        public Builder withNameExpression(SQLExpressionMappingImpl nameExpression) {
            this.nameExpression = nameExpression;
            return this;
        }

        public Builder withCaptionExpression(SQLExpressionMappingImpl captionExpression) {
            this.captionExpression = captionExpression;
            return this;
        }

        public Builder withOrdinalExpression(SQLExpressionMappingImpl ordinalExpression) {
            this.ordinalExpression = ordinalExpression;
            return this;
        }

        public Builder withParentExpression(SQLExpressionMappingImpl parentExpression) {
            this.parentExpression = parentExpression;
            return this;
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

        public Builder withCaptionColumn(String captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withColumn(String column) {
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

        public Builder withNameColumn(String nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withNullParentValue(String nullParentValue) {
            this.nullParentValue = nullParentValue;
            return this;
        }

        public Builder withOrdinalColumn(String ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public Builder withParentColumn(String parentColumn) {
            this.parentColumn = parentColumn;
            return this;
        }

        public Builder withTable(String table) {
            this.table = table;
            return this;
        }

        public Builder withType(DataType dataType) {
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

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public LevelMappingImpl build() {
            return new LevelMappingImpl(this);
        }
    }

}
