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

public interface TableQueryMapping extends RelationalQueryMapping {

    SqlStatementMapping getSqlWhereExpression();

    List<? extends AggregationExcludeMapping> getAggregationExcludes();

    List<? extends TableQueryOptimizationHintMapping> getOptimizationHints();

    TableMapping getTable();

    List<? extends AggregationTableMapping> getAggregationTables();

}
