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

import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;

public interface MeasureMapping extends MemberMapping{

    String getBackColor();

    InternalDataType getDatatype();

    MeasureGroupMapping getMeasureGroup();

    String getFormatter();
}
