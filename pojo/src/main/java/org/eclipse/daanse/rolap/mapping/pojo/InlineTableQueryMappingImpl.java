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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;

public class InlineTableQueryMappingImpl extends RelationalQueryMappingImpl implements InlineTableQueryMapping {

    private List<InlineTableColumnDefinitionMappingImpl> columnDefinitions;

    private List<InlineTableRowMappingMappingImpl> rows;

    public List<InlineTableColumnDefinitionMappingImpl> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<InlineTableColumnDefinitionMappingImpl> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public List<InlineTableRowMappingMappingImpl> getRows() {
        return rows;
    }

    public void setRows(List<InlineTableRowMappingMappingImpl> rows) {
        this.rows = rows;
    }
}
