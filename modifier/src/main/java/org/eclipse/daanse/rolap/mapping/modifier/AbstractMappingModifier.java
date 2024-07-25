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
package org.eclipse.daanse.rolap.mapping.modifier;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;

public abstract class AbstractMappingModifier implements CatalogMappingSupplier {

    protected CatalogMapping catalog;

    protected AbstractMappingModifier(CatalogMapping catalog) {
        super();
        this.catalog = catalog;
    }

    public CatalogMapping get() {
        return modifyCatalog(catalog);
    }
    protected CatalogMapping modifyCatalog(CatalogMapping catalog) {
        return null;
    }


}
