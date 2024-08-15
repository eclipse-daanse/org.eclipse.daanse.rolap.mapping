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
package org.eclipse.daanse.rolap.mapping.api.model;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelTypeEnum;
import org.eclipse.daanse.rolap.mapping.api.model.enums.TypeEnum;

public interface LevelMapping extends AbstractElementMapping{

    SQLExpressionMapping getKeyExpression();

    SQLExpressionMapping getNameExpression();

    SQLExpressionMapping getCaptionExpression();

    SQLExpressionMapping getOrdinalExpression();

    SQLExpressionMapping getParentExpression();

    ParentChildLinkMapping getParentChildLink();

    List<? extends MemberPropertyMapping> getMemberProperties();

    MemberFormatterMapping getMemberFormatter();

    String getApproxRowCount();

    String getCaptionColumn();

    String getColumn();

    HideMemberIfEnum getHideMemberIf();

    InternalTypeEnum getInternalType();

    LevelTypeEnum getLevelType();

    String getNameColumn();

    String getNullParentValue();

    String getOrdinalColumn();

    String getParentColumn();

    String getTable();

    TypeEnum getType();

    boolean isUniqueMembers();

    boolean isVisible();


}
