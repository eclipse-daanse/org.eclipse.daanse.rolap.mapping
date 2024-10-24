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

import org.eclipse.daanse.rdb.structure.api.model.PhysicalTable;
import org.eclipse.daanse.rdb.structure.api.model.Table;

public interface TableQueryMapping extends RelationalQueryMapping {

    SQLMapping getSqlWhereExpression();

    List<? extends AggregationExcludeMapping> getAggregationExcludes();

    List<? extends TableQueryOptimizationHintMapping> getOptimizationHints();

    Table getTable();

    List<? extends AggregationTableMapping> getAggregationTables();

}
