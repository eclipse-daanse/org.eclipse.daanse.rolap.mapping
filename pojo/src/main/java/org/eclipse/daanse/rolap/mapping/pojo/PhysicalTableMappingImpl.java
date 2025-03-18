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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import org.eclipse.daanse.rolap.mapping.api.model.PhysicalTableMapping;

public class PhysicalTableMappingImpl extends AbstractTableMappingImpl implements PhysicalTableMapping {

    public PhysicalTableMappingImpl(Builder builder) {
        setName(builder.getName());
        setColumns(setTableInColumns(builder.getColumns()));
        setSchema(builder.getSchema());
        setDescription(builder.getDescription());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractBuilder<Builder> {

        private Builder() {
        }

        public PhysicalTableMappingImpl build() {
            return new PhysicalTableMappingImpl(this);
        }

    }
}
