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

import org.eclipse.daanse.rolap.mapping.mondrian.model.MemberGrantAccessEnum;

public class MemberGrantAccessAdaptor extends Adaptor<MemberGrantAccessEnum> {

    protected MemberGrantAccessAdaptor() {
        super(MemberGrantAccessEnum.class);
    }

    @Override
    public String marshal(Enum<MemberGrantAccessEnum> e) throws Exception {
        if (e != null) {
            return MemberGrantAccessEnum.valueOf(e.name()).getValue();
        }
        return null;
    }

    @Override
    public Enum<MemberGrantAccessEnum> unmarshal(String v) {
        return MemberGrantAccessEnum.fromValue(v);
    }

}
