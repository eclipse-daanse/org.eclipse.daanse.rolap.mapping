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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowCellMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;

public class InlineTableRowMappingMappingImpl implements InlineTableRowMappingMapping {

    private List<? extends InlineTableRowCellMapping> cells;

    public List<? extends InlineTableRowCellMapping> getCells() {
        return cells;
    }

    public void setCells(List<? extends InlineTableRowCellMapping> cells) {
        this.cells = cells;
    }
}