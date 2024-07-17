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

public interface RolapContextMapping {

    List<? extends CatalogMapping> getCatalogs();

    List<? extends SchemaMapping> getSchemas();

    List<? extends CubeMapping> getCubes();

    List<? extends DimensionMapping> getDimensions();

    List<? extends HierarchyMapping> getHierarchies();

    List<? extends LevelMapping> getLevels();

    List<? extends FormatterMapping> getFormatters();

    List<? extends DatabaseSchemaMapping> getDbschemas();

    List<? extends MeasureMapping> getMeasures();

    List<? extends AccessRoleMapping> getAccessRoles();

    List<? extends AggregationTableMapping> getAggregationTables();

    List<? extends AggregationExcludeMapping> getAggregationExcludes();
}
