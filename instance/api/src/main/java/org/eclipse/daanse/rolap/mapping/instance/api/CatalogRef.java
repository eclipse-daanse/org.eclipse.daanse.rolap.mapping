/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.instance.api;

import java.util.function.Supplier;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;

/**
 * A runtime catalog shipped with a tutorial. The {@link #name()} becomes the
 * XMI filename inside the tutorial ZIP; {@link #catalog()} is a lazy supplier
 * of the catalog instance (invoked at serialization time).
 */
public record CatalogRef(String name, Supplier<Catalog> catalog) {
}
