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

import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.HideMemberIfAdaptor;
import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.InternalTypeAdaptor;
import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.LevelTypeAdaptor;
import org.eclipse.daanse.rolap.mapping.mondrian.model.adapter.TypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "keyExpression", "nameExpression", "captionExpression", "ordinalExpression",
        "parentExpression", "closure", "properties", "memberFormatter" })
public class Level extends AbstractMainElement {

    @XmlElement(name = "KeyExpression", type = ExpressionView.class)
    protected ExpressionView keyExpression;
    @XmlElement(name = "NameExpression", type = ExpressionView.class)
    protected ExpressionView nameExpression;
    @XmlElement(name = "CaptionExpression", type = ExpressionView.class)
    protected ExpressionView captionExpression;
    @XmlElement(name = "OrdinalExpression", type = ExpressionView.class)
    protected ExpressionView ordinalExpression;
    @XmlElement(name = "ParentExpression", type = ExpressionView.class)
    protected ExpressionView parentExpression;
    @XmlElement(name = "Closure", type = Closure.class)
    protected Closure closure;
    @XmlElement(name = "Property", type = Property.class)
    protected List<Property> properties;
    @XmlAttribute(name = "approxRowCount")
    protected String approxRowCount;

    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "nameColumn")
    protected String nameColumn;
    @XmlAttribute(name = "ordinalColumn")
    protected String ordinalColumn;
    @XmlAttribute(name = "parentColumn")
    protected String parentColumn;
    @XmlAttribute(name = "nullParentValue")
    protected String nullParentValue;
    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(TypeAdaptor.class)
    protected TypeEnum type;
    @XmlAttribute(name = "uniqueMembers")
    protected Boolean uniqueMembers;
    @XmlAttribute(name = "levelType")
    @XmlJavaTypeAdapter(LevelTypeAdaptor.class)
    protected LevelTypeEnum levelType;
    @XmlAttribute(name = "hideMemberIf")
    @XmlJavaTypeAdapter(HideMemberIfAdaptor.class)
    protected HideMemberIfEnum hideMemberIf;
    @XmlAttribute(name = "formatter")
    protected String formatter;

    @XmlAttribute(name = "captionColumn")
    protected String captionColumn;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;
    @XmlAttribute(name = "internalType")
    @XmlJavaTypeAdapter(InternalTypeAdaptor.class)
    protected InternalTypeEnum internalType;
    @XmlElement(name = "MemberFormatter", type = ElementFormatter.class)
    ElementFormatter memberFormatter;

    public String approxRowCount() {
        return approxRowCount;
    }

    public String captionColumn() {
        return captionColumn;
    }

    public ExpressionView captionExpression() {
        return captionExpression;
    }

    public Closure closure() {
        return closure;
    }

    public String column() {
        return column;
    }

    public String formatter() {
        return formatter;
    }

    public HideMemberIfEnum hideMemberIf() {
        if (hideMemberIf == null) {
            return HideMemberIfEnum.NEVER;
        } else {
            return hideMemberIf;
        }
    }

    public InternalTypeEnum internalType() {
        return internalType;
    }

    public ExpressionView keyExpression() {
        return keyExpression;
    }

    public LevelTypeEnum levelType() {
        if (levelType == null) {
            return LevelTypeEnum.REGULAR;
        } else {
            return levelType;
        }
    }

    public ElementFormatter memberFormatter() {
        return memberFormatter;
    }

    public String nameColumn() {
        return nameColumn;
    }

    public ExpressionView nameExpression() {
        return nameExpression;
    }

    public String nullParentValue() {
        return nullParentValue;
    }

    public String ordinalColumn() {
        return ordinalColumn;
    }

    public ExpressionView ordinalExpression() {
        return ordinalExpression;
    }

    public String parentColumn() {
        return parentColumn;
    }

    public ExpressionView parentExpression() {
        return parentExpression;
    }

    public List<Property> properties() {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        return this.properties;
    }

    public void setApproxRowCount(String value) {
        this.approxRowCount = value;
    }

    public void setCaptionColumn(String value) {
        this.captionColumn = value;
    }

    public void setCaptionExpression(ExpressionView value) {
        this.captionExpression = value;
    }

    public void setClosure(Closure value) {
        this.closure = value;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    public void setFormatter(String value) {
        this.formatter = value;
    }

    public void setHideMemberIf(HideMemberIfEnum value) {
        this.hideMemberIf = value;
    }

    public void setInternalType(InternalTypeEnum internalType) {
        this.internalType = internalType;
    }

    public void setKeyExpression(ExpressionView value) {
        this.keyExpression = value;
    }

    public void setLevelType(LevelTypeEnum value) {
        this.levelType = value;
    }

    public void setMemberFormatter(ElementFormatter memberFormatter) {
        this.memberFormatter = memberFormatter;
    }

    public void setNameColumn(String value) {
        this.nameColumn = value;
    }

    public void setNameExpression(ExpressionView value) {
        this.nameExpression = value;
    }

    public void setNullParentValue(String value) {
        this.nullParentValue = value;
    }

    public void setOrdinalColumn(String value) {
        this.ordinalColumn = value;
    }

    public void setOrdinalExpression(ExpressionView value) {
        this.ordinalExpression = value;
    }

    public void setParentColumn(String value) {
        this.parentColumn = value;
    }

    public void setParentExpression(ExpressionView value) {
        this.parentExpression = value;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void setTable(String value) {
        this.table = value;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public void setUniqueMembers(Boolean value) {
        this.uniqueMembers = value;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String table() {
        return table;
    }

    public TypeEnum type() {
        return type != null ? type : TypeEnum.STRING;
    }

    public Boolean uniqueMembers() {
        if (uniqueMembers == null) {
            return false;
        } else {
            return uniqueMembers;
        }
    }

    public Boolean visible() {
        return visible;
    }
}
