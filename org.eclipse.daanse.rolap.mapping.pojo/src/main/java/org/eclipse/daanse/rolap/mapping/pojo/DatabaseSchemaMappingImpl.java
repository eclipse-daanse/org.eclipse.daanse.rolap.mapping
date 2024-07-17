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

import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseTableMapping;

public class DatabaseSchemaMappingImpl implements DatabaseSchemaMapping {

    private String id;

    private String name;

    private List<? extends DatabaseTableMapping> tables;

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

    public List<? extends DatabaseTableMapping> getTables() {
        return tables;
    }

    public void setTables(List<? extends DatabaseTableMapping> tables) {
        this.tables = tables;
    }
}
