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

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;

public class RolapContextMappingImpl extends AbstractElementMappingImpl implements RolapContextMapping {

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

    private RolapContextMappingImpl(Builder builder) {
        this.catalogs = builder.catalogs;
        this.schemas = builder.schemas;
        this.cubes = builder.cubes;
        this.dimensions = builder.dimensions;
        this.hierarchies = builder.hierarchies;
        this.levels = builder.levels;
        this.formatters = builder.formatters;
        this.dbschemas = builder.dbschemas;
        this.measures = builder.measures;
        this.accessRoles = builder.accessRoles;
        this.aggregationTables = builder.aggregationTables;
        this.aggregationExcludes = builder.aggregationExcludes;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<CatalogMappingImpl> catalogs = Collections.emptyList();
        private List<SchemaMappingImpl> schemas = Collections.emptyList();
        private List<CubeMappingImpl> cubes = Collections.emptyList();
        private List<DimensionMappingImpl> dimensions = Collections.emptyList();
        private List<HierarchyMappingImpl> hierarchies = Collections.emptyList();
        private List<LevelMappingImpl> levels = Collections.emptyList();
        private List<FormatterMappingImpl> formatters = Collections.emptyList();
        private List<DatabaseSchemaMappingImpl> dbschemas = Collections.emptyList();
        private List<MeasureMappingImpl> measures = Collections.emptyList();
        private List<AccessRoleMappingImpl> accessRoles = Collections.emptyList();
        private List<AggregationTableMappingImpl> aggregationTables = Collections.emptyList();
        private List<AggregationExcludeMappingImpl> aggregationExcludes = Collections.emptyList();
        private List<AnnotationMappingImpl> annotations = Collections.emptyList();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withCatalogs(List<CatalogMappingImpl> catalogs) {
            this.catalogs = catalogs;
            return this;
        }

        public Builder withSchemas(List<SchemaMappingImpl> schemas) {
            this.schemas = schemas;
            return this;
        }

        public Builder withCubes(List<CubeMappingImpl> cubes) {
            this.cubes = cubes;
            return this;
        }

        public Builder withDimensions(List<DimensionMappingImpl> dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder withHierarchies(List<HierarchyMappingImpl> hierarchies) {
            this.hierarchies = hierarchies;
            return this;
        }

        public Builder withLevels(List<LevelMappingImpl> levels) {
            this.levels = levels;
            return this;
        }

        public Builder withFormatters(List<FormatterMappingImpl> formatters) {
            this.formatters = formatters;
            return this;
        }

        public Builder withDbschemas(List<DatabaseSchemaMappingImpl> dbschemas) {
            this.dbschemas = dbschemas;
            return this;
        }

        public Builder withMeasures(List<MeasureMappingImpl> measures) {
            this.measures = measures;
            return this;
        }

        public Builder withAccessRoles(List<AccessRoleMappingImpl> accessRoles) {
            this.accessRoles = accessRoles;
            return this;
        }

        public Builder withAggregationTables(List<AggregationTableMappingImpl> aggregationTables) {
            this.aggregationTables = aggregationTables;
            return this;
        }

        public Builder withAggregationExcludes(List<AggregationExcludeMappingImpl> aggregationExcludes) {
            this.aggregationExcludes = aggregationExcludes;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public RolapContextMappingImpl build() {
            return new RolapContextMappingImpl(this);
        }
    }

}
