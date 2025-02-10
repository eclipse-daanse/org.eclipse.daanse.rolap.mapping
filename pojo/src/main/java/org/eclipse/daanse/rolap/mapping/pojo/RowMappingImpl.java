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

import org.eclipse.daanse.rolap.mapping.api.model.RowMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowValueMapping;

public class RowMappingImpl implements RowMapping {

    private List<? extends RowValueMapping> rowValues;

    public RowMappingImpl(Builder builder) {
        this.rowValues = builder.rowValues;
    }

    @Override
    public List<? extends RowValueMapping> getRowValues() {
        return rowValues;
    }

    public void setRowValues(List<? extends RowValueMapping> rowValues) {
        this.rowValues = rowValues;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private List<? extends RowValueMapping> rowValues;

        public Builder withRowValues(List<? extends RowValueMapping> rowValues) {
            this.rowValues = rowValues;
            return this;
        }

        public RowMappingImpl build() {
            return new RowMappingImpl(this);
        }
    }
}
