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

import org.eclipse.daanse.rolap.mapping.mondrian.model.InternalTypeEnum;

public class InternalTypeAdaptor extends Adaptor<InternalTypeEnum> {

    protected InternalTypeAdaptor() {
        super(InternalTypeEnum.class);
    }

    @Override
    public String marshal(Enum<InternalTypeEnum> e) throws Exception {
        if (e != null) {
            return InternalTypeEnum.valueOf(e.name()).getValue();
        }
        return null;
    }

    @Override
    public Enum<InternalTypeEnum> unmarshal(String v) {
        return InternalTypeEnum.fromValue(v);
    }

}
