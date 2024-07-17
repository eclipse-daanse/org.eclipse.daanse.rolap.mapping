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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableColumnDefinitionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;

public class InlineTableQueryMappingImpl extends RelationalQueryMappingImpl implements InlineTableQueryMapping {

    private List<? extends InlineTableColumnDefinitionMapping> columnDefinitions;

    private List<? extends InlineTableRowMappingMapping> rows;

    public List<? extends InlineTableColumnDefinitionMapping> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<? extends InlineTableColumnDefinitionMapping> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    public List<? extends InlineTableRowMappingMapping> getRows() {
        return rows;
    }

    public void setRows(List<? extends InlineTableRowMappingMapping> rows) {
        this.rows = rows;
    }
}
