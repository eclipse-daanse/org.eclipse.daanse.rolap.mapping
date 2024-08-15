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

import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;

public interface MeasureMapping extends AbstractElementMapping{

    SQLExpressionMapping getMeasureExpression();

    List<? extends CalculatedMemberPropertyMapping> getCalculatedMemberProperty();

    CellFormatterMapping getCellFormatter();

    String getBackColor();

    String getColumn();

    DataType getDatatype();

    String getDisplayFolder();

    String getFormatString();

    String getFormatter();

    boolean isVisible();

    MeasureAggregatorType getAggregatorType();

}
