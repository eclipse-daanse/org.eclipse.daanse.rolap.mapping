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

import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;

public class InlineTableRowMappingImpl implements InlineTableRowMappingMapping {

    private List<InlineTableRowCellMappingImpl> cells;

    private InlineTableRowMappingImpl(Builder builder) {
        this.cells = builder.cells;
    }

    public List<InlineTableRowCellMappingImpl> getCells() {
        return cells;
    }

    public void setCells(List<InlineTableRowCellMappingImpl> cells) {
        this.cells = cells;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<InlineTableRowCellMappingImpl> cells = Collections.emptyList();

        private Builder() {
        }

        public Builder withCells(List<InlineTableRowCellMappingImpl> cells) {
            this.cells = cells;
            return this;
        }

        public InlineTableRowMappingImpl build() {
            return new InlineTableRowMappingImpl(this);
        }
    }
}
