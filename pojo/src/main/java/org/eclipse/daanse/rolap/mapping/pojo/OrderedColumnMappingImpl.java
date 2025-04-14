/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.OrderedColumnMapping;

public class OrderedColumnMappingImpl implements OrderedColumnMapping {

    private ColumnMapping column;
    private boolean ascend;

    private OrderedColumnMappingImpl(Builder builder) {
        this.column = builder.column;
        this.ascend = builder.ascend;
    }

    @Override
    public ColumnMapping getColumn() {
        return this.column;
    }

    @Override
    public boolean isAscend() {
        return this.ascend;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ColumnMapping column;
        private boolean ascend;

        public Builder withColumn(ColumnMapping column) {
            this.column = column;
            return this;
        }

        public Builder withAscend(boolean ascend) {
            this.ascend = ascend;
            return this;
        }

        public OrderedColumnMappingImpl build() {
            return new OrderedColumnMappingImpl(this);
        }
    }

}
