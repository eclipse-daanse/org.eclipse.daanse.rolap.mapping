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

public enum ColumnType {
    VARCHAR("Varchar"),
    NUMERIC("Numeric"),
    DECIMAL("Decimal"),
    FLOAT("Float"),
    REAL("Real"),
    INTEGER("Integer"),
    SMALLINT("Smallint"),
    DOUBLE("Double"),
    OTHER("Other"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    TIME("Time"),
    TIMESTAMP("Timestamp"),
    BIGINT("Bigint");

    private final String value;

    ColumnType(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static ColumnType fromValue(String v) {
        for (ColumnType c : ColumnType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
