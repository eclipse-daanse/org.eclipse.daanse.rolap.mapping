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

import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.FormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;

public class RolapContextMappingImpl implements RolapContextMapping{

    private List<? extends CatalogMapping> catalogs;

    private List<? extends SchemaMapping> schemas;

    private List<? extends CubeMapping> cubes;

    private List<? extends DimensionMapping> dimensions;

    private List<? extends HierarchyMapping> hierarchies;

    private List<? extends LevelMapping> levels;

    private List<? extends FormatterMapping> formatters;

    private List<? extends DatabaseSchemaMapping> dbschemas;

    private List<? extends MeasureMapping> measures;

    private List<? extends AccessRoleMapping> accessRoles;

    private List<? extends AggregationTableMapping> aggregationTables;

    private List<? extends AggregationExcludeMapping> aggregationExcludes;

    public List<? extends SchemaMapping> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<? extends SchemaMapping> schemas) {
        this.schemas = schemas;
    }

    public List<? extends CubeMapping> getCubes() {
        return cubes;
    }

    public void setCubes(List<? extends CubeMapping> cubes) {
        this.cubes = cubes;
    }

    public List<? extends DimensionMapping> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<? extends DimensionMapping> dimensions) {
        this.dimensions = dimensions;
    }

    public List<? extends HierarchyMapping> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<? extends HierarchyMapping> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public List<? extends LevelMapping> getLevels() {
        return levels;
    }

    public void setLevels(List<? extends LevelMapping> levels) {
        this.levels = levels;
    }

    public List<? extends FormatterMapping> getFormatters() {
        return formatters;
    }

    public void setFormatters(List<? extends FormatterMapping> formatters) {
        this.formatters = formatters;
    }

    public List<? extends DatabaseSchemaMapping> getDbschemas() {
        return dbschemas;
    }

    public void setDbschemas(List<? extends DatabaseSchemaMapping> dbschemas) {
        this.dbschemas = dbschemas;
    }

    public List<? extends MeasureMapping> getMeasures() {
        return measures;
    }

    public void setMeasures(List<? extends MeasureMapping> measures) {
        this.measures = measures;
    }

    public List<? extends AccessRoleMapping> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<? extends AccessRoleMapping> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public List<? extends AggregationTableMapping> getAggregationTables() {
        return aggregationTables;
    }

    public void setAggregationTables(List<? extends AggregationTableMapping> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }

    public List<? extends AggregationExcludeMapping> getAggregationExcludes() {
        return aggregationExcludes;
    }

    public void setAggregationExcludes(List<? extends AggregationExcludeMapping> aggregationExcludes) {
        this.aggregationExcludes = aggregationExcludes;
    }

    public List<? extends CatalogMapping> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<? extends CatalogMapping> catalogs) {
        this.catalogs = catalogs;
    }
}
