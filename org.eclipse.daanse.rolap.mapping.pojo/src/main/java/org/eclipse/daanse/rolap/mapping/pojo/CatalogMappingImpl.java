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

import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;

import java.util.List;

public class CatalogMappingImpl extends AbstractElementMappingImpl implements CatalogMapping {

    private List<? extends SchemaMappingImpl> schemas;

    public List<? extends SchemaMappingImpl> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<? extends SchemaMappingImpl> schemas) {
        this.schemas = schemas;
    }
}
