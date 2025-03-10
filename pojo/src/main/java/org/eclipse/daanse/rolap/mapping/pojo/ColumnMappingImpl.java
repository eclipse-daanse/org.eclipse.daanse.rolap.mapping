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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import org.eclipse.daanse.rolap.mapping.api.model.ColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnDataType;

public class ColumnMappingImpl implements ColumnMapping {

    public ColumnMappingImpl(Builder builder) {
        this.name = builder.name;
        this.table = builder.table;
        this.dataType = builder.dataType;
        this.nullable = builder.nullable;
        this.charOctetLength = builder.charOctetLength;
        this.numPrecRadix = builder.numPrecRadix;
        this.columnSize = builder.columnSize;
        this.decimalDigits = builder.decimalDigits;
        this.description = builder.description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TableMapping getTable() {
        return table;
    }

    public void setTable(TableMapping table) {
        this.table = table;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public void setNumPrecRadix(int numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String name;

    private TableMapping table;

    private ColumnDataType dataType;

    private boolean nullable;

    private int charOctetLength;

    private int numPrecRadix;

    private Integer columnSize;

    private Integer decimalDigits;

    private String description;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;

        private TableMapping table;

        private ColumnDataType dataType;

        private String description;

        private boolean nullable;

        private int charOctetLength;

        private int numPrecRadix;

        private Integer columnSize;

        private Integer decimalDigits;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTable(TableMapping table) {
            this.table = table;
            return this;
        }

        public Builder withDataType(ColumnDataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder withDecimalDigits(Integer decimalDigits) {
            this.decimalDigits = decimalDigits;
            return this;
        }

        public Builder withColumnSize(Integer columnSize) {
            this.columnSize = columnSize;
            return this;
        }

        public Builder withNumPrecRadix(int numPrecRadix) {
            this.numPrecRadix = numPrecRadix;
            return this;
        }

        public Builder withCharOctetLength(int charOctetLength) {
            this.charOctetLength = charOctetLength;
            return this;
        }

        public Builder withNullable(boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ColumnMappingImpl build() {
            return new ColumnMappingImpl(this);
        }
    }

    @Override
    public Integer getColumnSize() {
        return columnSize;
    }

    @Override
    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    @Override
    public Integer getNumPrecRadix() {
        return numPrecRadix;
    }

    @Override
    public Integer getCharOctetLength() {
        return charOctetLength;
    }

    @Override
    public Boolean getNullable() {
        return nullable;
    }



}
