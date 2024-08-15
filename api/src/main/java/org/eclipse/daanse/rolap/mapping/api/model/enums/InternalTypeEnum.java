/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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

public enum InternalTypeEnum {
    INT("int"),
    LONG("long"),
    OBJECT("Object"),
    STRING("String");

    private final String value;

    InternalTypeEnum(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static InternalTypeEnum fromValue(String v) {
        for (InternalTypeEnum c: InternalTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Illegal value '" + v + "'.  Legal values: {int, long, Object, String}");
    }
}
