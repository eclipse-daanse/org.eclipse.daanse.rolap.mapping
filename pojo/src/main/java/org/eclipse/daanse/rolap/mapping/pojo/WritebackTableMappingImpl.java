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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;

public class WritebackTableMappingImpl implements WritebackTableMapping{

    private List<? extends WritebackAttributeMapping> writebackAttribute;

    private List<? extends WritebackMeasureMapping> writebackMeasure;

    private String name;

    private String schema;

    public List<? extends WritebackAttributeMapping> getWritebackAttribute() {
        return writebackAttribute;
    }

    public void setWritebackAttribute(List<? extends WritebackAttributeMapping> writebackAttribute) {
        this.writebackAttribute = writebackAttribute;
    }

    public List<? extends WritebackMeasureMapping> getWritebackMeasure() {
        return writebackMeasure;
    }

    public void setWritebackMeasure(List<? extends WritebackMeasureMapping> writebackMeasure) {
        this.writebackMeasure = writebackMeasure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
