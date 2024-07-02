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
package org.eclipse.daanse.rolap.mapping.mondrian.model.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class Adaptor<T extends Enum<T>> extends XmlAdapter<String, Enum<T>> {

    private Class<T> enumClass;

    public Adaptor() {
    }

    protected Adaptor(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String marshal(Enum<T> e) throws Exception {
        if (e != null) {
            return e.toString();
        }
        return null;
    }

    @Override
    public Enum<T> unmarshal(String v) {
        return Enum.valueOf(enumClass, v);
    }
}
