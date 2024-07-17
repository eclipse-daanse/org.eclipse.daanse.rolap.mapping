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
 *
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;

public class CalculatedMemberPropertyMappingImpl extends AbstractElementMappingImpl implements CalculatedMemberPropertyMapping{

    private String expression;

    private String value;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public String getValue() {
        return value;
    }
}
