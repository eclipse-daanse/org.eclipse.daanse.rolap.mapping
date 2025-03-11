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
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;

public class LevelMappingImpl extends AbstractElementMappingImpl implements LevelMapping {

    private SQLExpressionMappingColumnImpl keyExpression;

    private SQLExpressionMappingColumnImpl nameExpression;

    private SQLExpressionMappingColumnImpl captionExpression;

    private SQLExpressionMappingColumnImpl ordinalExpression;

    private SQLExpressionMappingColumnImpl parentExpression;

    private ParentChildLinkMappingImpl parentChildLink;

    private List<MemberPropertyMappingImpl> memberProperties;

    private MemberFormatterMappingImpl memberFormatter;

    private String approxRowCount;

    private ColumnMappingImpl captionColumn;

    private ColumnMappingImpl column;

    private HideMemberIfType hideMemberIfType;

    private LevelType levelType;

    private ColumnMappingImpl nameColumn;

    private String nullParentValue;

    private ColumnMappingImpl ordinalColumn;

    private ColumnMappingImpl parentColumn;

    private TableMapping table;

    private InternalDataType dataType;

    private boolean uniqueMembers;

    private boolean visible;


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
        super.setName(builder.name);
        super.setDescription(builder.description);
        super.setId(builder.id);
        super.setAnnotations(builder.annotations);
    }

    public SQLExpressionMappingColumnImpl getKeyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(SQLExpressionMappingColumnImpl keyExpression) {
        this.keyExpression = keyExpression;
    }

    public SQLExpressionMappingColumnImpl getNameExpression() {
        return nameExpression;
    }

    public void setNameExpression(SQLExpressionMappingColumnImpl nameExpression) {
        this.nameExpression = nameExpression;
    }

    public SQLExpressionMappingColumnImpl getCaptionExpression() {
        return captionExpression;
    }

    public void setCaptionExpression(SQLExpressionMappingColumnImpl captionExpression) {
        this.captionExpression = captionExpression;
    }

    public SQLExpressionMappingColumnImpl getOrdinalExpression() {
        return ordinalExpression;
    }

    public void setOrdinalExpression(SQLExpressionMappingColumnImpl ordinalExpression) {
        this.ordinalExpression = ordinalExpression;
    }

    public SQLExpressionMappingColumnImpl getParentExpression() {
        return parentExpression;
    }

    public void setParentExpression(SQLExpressionMappingColumnImpl parentExpression) {
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

    public ColumnMappingImpl getCaptionColumn() {
        return captionColumn;
    }

    public void setCaptionColumn(ColumnMappingImpl captionColumn) {
        this.captionColumn = captionColumn;
    }

    public ColumnMappingImpl getColumn() {
        return column;
    }

    public void setColumn(ColumnMappingImpl column) {
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

    public ColumnMappingImpl getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(ColumnMappingImpl nameColumn) {
        this.nameColumn = nameColumn;
    }

    public String getNullParentValue() {
        return nullParentValue;
    }

    public void setNullParentValue(String nullParentValue) {
        this.nullParentValue = nullParentValue;
    }

    public ColumnMappingImpl getOrdinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn(ColumnMappingImpl ordinalColumn) {
        this.ordinalColumn = ordinalColumn;
    }

    public ColumnMappingImpl getParentColumn() {
        return parentColumn;
    }

    public void setParentColumn(ColumnMappingImpl parentColumn) {
        this.parentColumn = parentColumn;
    }

    public TableMapping getTable() {
        return table;
    }

    public void setTable(TableMapping table) {
        this.table = table;
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
        private SQLExpressionMappingColumnImpl keyExpression;
        private SQLExpressionMappingColumnImpl nameExpression;
        private SQLExpressionMappingColumnImpl captionExpression;
        private SQLExpressionMappingColumnImpl ordinalExpression;
        private SQLExpressionMappingColumnImpl parentExpression;
        private ParentChildLinkMappingImpl parentChildLink;
        private List<MemberPropertyMappingImpl> memberProperties = new ArrayList<>();
        private MemberFormatterMappingImpl memberFormatter;
        private String approxRowCount;
        private ColumnMappingImpl captionColumn;
        private ColumnMappingImpl column;
        private HideMemberIfType hideMemberIfType;
        private LevelType levelType;
        private ColumnMappingImpl nameColumn;
        private String nullParentValue;
        private ColumnMappingImpl ordinalColumn;
        private ColumnMappingImpl parentColumn;
        private TableMapping table;
        private InternalDataType dataType;
        private boolean uniqueMembers;
        private boolean visible;
        private String name;
        private String description;
        private String id;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();

        private Builder() {
        }

        public Builder withKeyExpression(SQLExpressionMappingColumnImpl keyExpression) {
            this.keyExpression = keyExpression;
            return this;
        }

        public Builder withNameExpression(SQLExpressionMappingColumnImpl nameExpression) {
            this.nameExpression = nameExpression;
            return this;
        }

        public Builder withCaptionExpression(SQLExpressionMappingColumnImpl captionExpression) {
            this.captionExpression = captionExpression;
            return this;
        }

        public Builder withOrdinalExpression(SQLExpressionMappingColumnImpl ordinalExpression) {
            this.ordinalExpression = ordinalExpression;
            return this;
        }

        public Builder withParentExpression(SQLExpressionMappingColumnImpl parentExpression) {
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

        public Builder withCaptionColumn(ColumnMappingImpl captionColumn) {
            this.captionColumn = captionColumn;
            return this;
        }

        public Builder withColumn(ColumnMappingImpl column) {
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

        public Builder withNameColumn(ColumnMappingImpl nameColumn) {
            this.nameColumn = nameColumn;
            return this;
        }

        public Builder withNullParentValue(String nullParentValue) {
            this.nullParentValue = nullParentValue;
            return this;
        }

        public Builder withOrdinalColumn(ColumnMappingImpl ordinalColumn) {
            this.ordinalColumn = ordinalColumn;
            return this;
        }

        public Builder withParentColumn(ColumnMappingImpl parentColumn) {
            this.parentColumn = parentColumn;
            return this;
        }

        public Builder withTable(TableMapping table) {
            this.table = table;
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
