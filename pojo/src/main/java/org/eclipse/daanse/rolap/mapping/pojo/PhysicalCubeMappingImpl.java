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

import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;

public class PhysicalCubeMappingImpl extends CubeMappingImpl implements PhysicalCubeMapping {

    private QueryMapping query;

    private WritebackTableMapping writebackTable;

    private List<ActionMappingMappingImpl> action;

    private boolean cache;

    public QueryMapping getQuery() {
        return query;
    }

    public void setQuery(QueryMapping query) {
        this.query = query;
    }

    public WritebackTableMapping getWritebackTable() {
        return writebackTable;
    }

    public void setWritebackTable(WritebackTableMapping writebackTable) {
        this.writebackTable = writebackTable;
    }

    public List<ActionMappingMappingImpl> getAction() {
        return action;
    }

    public void setAction(List<ActionMappingMappingImpl> action) {
        this.action = action;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
}
