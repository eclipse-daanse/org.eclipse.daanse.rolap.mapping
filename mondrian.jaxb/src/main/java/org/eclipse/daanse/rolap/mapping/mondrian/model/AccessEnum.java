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
package org.eclipse.daanse.rolap.mapping.mondrian.model;

public enum AccessEnum {

    ALL("all"), CUSTOM("custom"), NONE("none"), ALL_DIMENSION("all_dimensions");

    public static AccessEnum fromValue(String v) {
        for (AccessEnum c : AccessEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    private final String value;

    AccessEnum(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
}
