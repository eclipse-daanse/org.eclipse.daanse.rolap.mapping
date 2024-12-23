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

import org.eclipse.daanse.rdb.structure.api.model.Column;
import org.eclipse.daanse.rdb.structure.api.model.Table;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;

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

    Column getCaptionColumn();

    Column getColumn();

    HideMemberIfType getHideMemberIfType();

    LevelType getLevelType();

    Column getNameColumn();

    String getNullParentValue();

    Column getOrdinalColumn();

    Column getParentColumn();

    Table getTable();

    DataType getDataType();

    boolean isUniqueMembers();

    boolean isVisible();


}
