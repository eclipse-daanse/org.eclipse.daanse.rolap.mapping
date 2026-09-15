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
package org.eclipse.daanse.rolap.mapping.model.provider;

import java.util.function.Supplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.provider.util.CatalogFingerprint;

public interface CatalogMappingSupplier extends Supplier<Catalog> {

    /**
     * SHA-256 content identity of the supplied catalog. File-based providers
     * hash their source files; the default hashes the canonical serialization
     * of the model graph.
     */
    default byte[] sha256() {
        return CatalogFingerprint.sha256(get());
    }
}
