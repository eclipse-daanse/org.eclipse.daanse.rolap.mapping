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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.api.model.enums;

public enum AccessTable {

    ALL("all"), CUSTOM("custom"), NONE("none");

    private final String value;

    AccessTable(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static AccessTable fromValue(String v) {
        for (AccessTable c : AccessTable.values()) {
            if (c.value.equalsIgnoreCase(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
