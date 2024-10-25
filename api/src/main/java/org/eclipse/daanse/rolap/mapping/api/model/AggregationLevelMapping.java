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

public interface AggregationLevelMapping {

    List<? extends AggregationLevelPropertyMapping> getAggregationLevelProperties();

    Column getCaptionColumn();

    boolean isCollapsed();

    Column getColumn();

    String getName();

    Column getNameColumn();

    Column getOrdinalColumn();

}
