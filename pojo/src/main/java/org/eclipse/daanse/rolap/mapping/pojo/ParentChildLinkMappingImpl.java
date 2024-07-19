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

import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;

public class ParentChildLinkMappingImpl implements ParentChildLinkMapping {

    private TableQueryMappingImpl table;

    private String childColumn;

    private String parentColumn;

    public TableQueryMappingImpl getTable() {
        return table;
    }

    public void setTable(TableQueryMappingImpl table) {
        this.table = table;
    }

    public String getChildColumn() {
        return childColumn;
    }

    public void setChildColumn(String childColumn) {
        this.childColumn = childColumn;
    }

    public String getParentColumn() {
        return parentColumn;
    }

    public void setParentColumn(String parentColumn) {
        this.parentColumn = parentColumn;
    }
}
