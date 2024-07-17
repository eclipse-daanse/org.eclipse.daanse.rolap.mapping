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

public class LevelMappingImpl {

    private SQLExpressionMappingImpl keyExpression;

    private SQLExpressionMappingImpl nameExpression;

    private SQLExpressionMappingImpl captionExpression;

    private SQLExpressionMappingImpl ordinalExpression;

    private SQLExpressionMappingImpl parentExpression;

    private ParentChildLinkMappingImpl parentChildLink;

    private List<? extends MemberPropertyMappingImpl> memberProperties;

    private MemberFormatterMappingImpl memberFormatter;

    private String approxRowCount;

    private String captionColumn;

    private String column;

    private String hideMemberIf;

    private String internalType;

    private String levelType;

    private String nameColumn;

    private String nullParentValue;

    private String ordinalColumn;

    private String parentColumn;

    private String type;

    private boolean uniqueMembers;

    private boolean visible;

    private String name;

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

    public List<? extends MemberPropertyMappingImpl> getMemberProperties() {
        return memberProperties;
    }

    public void setMemberProperties(List<? extends MemberPropertyMappingImpl> memberProperties) {
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

    public String getHideMemberIf() {
        return hideMemberIf;
    }

    public void setHideMemberIf(String hideMemberIf) {
        this.hideMemberIf = hideMemberIf;
    }

    public String getInternalType() {
        return internalType;
    }

    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
