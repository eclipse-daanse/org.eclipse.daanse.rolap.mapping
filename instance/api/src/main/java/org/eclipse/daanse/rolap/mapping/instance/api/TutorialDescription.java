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

import java.util.List;

/**
 * Tutorial narrative + runtime-catalog pointers. A tutorial can describe
 * elements that span multiple runtime catalogs; the markdown generator will
 * emit one XMI per {@link CatalogRef} and use {@link #sections()} for the
 * surrounding markdown.
 *
 * @param sections  ordered list of documentation sections
 * @param catalogs  the runtime catalogs that {@code sections} reference
 */
public record TutorialDescription(List<DocSection> sections, List<CatalogRef> catalogs) {
}
