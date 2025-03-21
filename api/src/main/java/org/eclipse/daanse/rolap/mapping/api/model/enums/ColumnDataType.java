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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.api.model.enums;

public enum ColumnDataType {
    VARCHAR("Varchar"),
    NUMERIC("Numeric"),
    DECIMAL("Decimal"),
    FLOAT("Float"),
    REAL("Real"),
    INTEGER("Integer"),
    SMALLINT("SmallInt"),
    DOUBLE("Double"),
    OTHER("Other"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    TIME("Time"),
    TIMESTAMP("Timestamp"),
    BIGINT("BigInt");

    private final String value;

    ColumnDataType(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static ColumnDataType fromValue(String v) {
        for (ColumnDataType c : ColumnDataType.values()) {
            if (c.value.equalsIgnoreCase(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
