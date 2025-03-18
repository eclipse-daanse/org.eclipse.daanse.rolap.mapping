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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;

public abstract class AbstractTableMappingImpl implements TableMapping {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnMapping> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMapping> columns) {
        this.columns = columns;
    }

    public DatabaseSchemaMappingImpl getSchema() {
        return schema;
    }

    public void setSchema(DatabaseSchemaMappingImpl schema) {
        this.schema = schema;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected List<ColumnMapping> setTableInColumns(List<ColumnMapping> columns) {
        if (columns != null) {
            for (ColumnMapping column : columns) {
                if (column.getTable() == null) {
                    if (column instanceof PhysicalColumnMappingImpl pc) {
                        pc.setTable(this);
                    }
                    if (column instanceof SQLExpressionMappingColumnImpl sec) {
                        sec.setTable(this);
                    }
                }
            }
        }
        return columns;
    }

    private String name;

    private List<ColumnMapping> columns;

    private DatabaseSchemaMappingImpl schema;

    private String description;

    public static abstract class AbstractBuilder<B extends AbstractBuilder<B>> {

        private String name;

        private List<ColumnMapping> columns;

        private DatabaseSchemaMappingImpl schema;

        private String description;

        public B withName(String name) {
            this.name = name;
            return (B) this;
        }

        public B withColumns(List<ColumnMapping> columns) {
            this.columns = columns;
            return (B) this;
        }

        public B withsSchema(DatabaseSchemaMappingImpl schema) {
            this.schema = schema;
            return (B) this;
        }

        public B withsDdescription(String description) {
            this.description = description;
            return (B) this;
        }

        public String getName() {
            return name;
        }

        public List<ColumnMapping> getColumns() {
            return columns;
        }

        public DatabaseSchemaMappingImpl getSchema() {
            return schema;
        }

        public String getDescription() {
            return description;
        }
    }

}
