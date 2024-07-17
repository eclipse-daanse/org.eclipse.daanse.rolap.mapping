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

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseTableMapping;

public class DatabaseTableMappingImpl implements DatabaseTableMapping {

    private String id;

    private String name;

    private String description;

    private List<? extends DatabaseColumnMapping> columns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<? extends DatabaseColumnMapping> getColumns() {
        return columns;
    }

    public void setColumns(List<? extends DatabaseColumnMapping> columns) {
        this.columns = columns;
    }
}
