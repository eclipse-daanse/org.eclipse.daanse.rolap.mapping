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

import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;

public class RolapContextMappingImpl implements RolapContextMapping{

    private List<CatalogMappingImpl> catalogs;

    private List<SchemaMappingImpl> schemas;

    private List<CubeMappingImpl> cubes;

    private List<DimensionMappingImpl> dimensions;

    private List<HierarchyMappingImpl> hierarchies;

    private List<LevelMappingImpl> levels;

    private List<FormatterMappingImpl> formatters;

    private List<DatabaseSchemaMappingImpl> dbschemas;

    private List<MeasureMappingImpl> measures;

    private List<AccessRoleMappingImpl> accessRoles;

    private List<AggregationTableMappingImpl> aggregationTables;

    private List<AggregationExcludeMappingImpl> aggregationExcludes;

    public List<SchemaMappingImpl> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<SchemaMappingImpl> schemas) {
        this.schemas = schemas;
    }

    public List<CubeMappingImpl> getCubes() {
        return cubes;
    }

    public void setCubes(List<CubeMappingImpl> cubes) {
        this.cubes = cubes;
    }

    public List<DimensionMappingImpl> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DimensionMappingImpl> dimensions) {
        this.dimensions = dimensions;
    }

    public List<HierarchyMappingImpl> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(List<HierarchyMappingImpl> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public List<LevelMappingImpl> getLevels() {
        return levels;
    }

    public void setLevels(List<LevelMappingImpl> levels) {
        this.levels = levels;
    }

    public List<FormatterMappingImpl> getFormatters() {
        return formatters;
    }

    public void setFormatters(List<FormatterMappingImpl> formatters) {
        this.formatters = formatters;
    }

    public List<DatabaseSchemaMappingImpl> getDbschemas() {
        return dbschemas;
    }

    public void setDbschemas(List<DatabaseSchemaMappingImpl> dbschemas) {
        this.dbschemas = dbschemas;
    }

    public List<MeasureMappingImpl> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureMappingImpl> measures) {
        this.measures = measures;
    }

    public List<AccessRoleMappingImpl> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<AccessRoleMappingImpl> accessRoles) {
        this.accessRoles = accessRoles;
    }

    public List<AggregationTableMappingImpl> getAggregationTables() {
        return aggregationTables;
    }

    public void setAggregationTables(List<AggregationTableMappingImpl> aggregationTables) {
        this.aggregationTables = aggregationTables;
    }

    public List<AggregationExcludeMappingImpl> getAggregationExcludes() {
        return aggregationExcludes;
    }

    public void setAggregationExcludes(List<AggregationExcludeMappingImpl> aggregationExcludes) {
        this.aggregationExcludes = aggregationExcludes;
    }

    public List<CatalogMappingImpl> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<CatalogMappingImpl> catalogs) {
        this.catalogs = catalogs;
    }
}
