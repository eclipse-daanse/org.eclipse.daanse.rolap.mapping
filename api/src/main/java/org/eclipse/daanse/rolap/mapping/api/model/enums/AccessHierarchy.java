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

public enum AccessHierarchy {

    ALL("all"), CUSTOM("custom"), NONE("none");

    private final String value;

    AccessHierarchy(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static AccessHierarchy fromValue(String v) {
        for (AccessHierarchy c: AccessHierarchy.values()) {
            if (c.value.equalsIgnoreCase(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
