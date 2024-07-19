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

import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;

public class MemberPropertyMappingImpl extends AbstractElementMappingImpl implements MemberPropertyMapping {

    private MemberPropertyFormatterMappingImpl formatter;

    private String column;

    private boolean dependsOnLevelValue;

    private String type;

    public MemberPropertyFormatterMappingImpl getFormatter() {
        return formatter;
    }

    public void setFormatter(MemberPropertyFormatterMappingImpl formatter) {
        this.formatter = formatter;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isDependsOnLevelValue() {
        return dependsOnLevelValue;
    }

    public void setDependsOnLevelValue(boolean dependsOnLevelValue) {
        this.dependsOnLevelValue = dependsOnLevelValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
