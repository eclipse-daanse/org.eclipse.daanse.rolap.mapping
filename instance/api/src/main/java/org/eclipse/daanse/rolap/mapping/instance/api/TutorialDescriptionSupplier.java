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

/**
 * Side-channel API that complements {@code CatalogMappingSupplier} with
 * narrative content for tutorial rendering. A tutorial supplier typically
 * implements both interfaces: {@code CatalogMappingSupplier#get()} returns the
 * CWM-clean runtime catalog; {@link #describe()} returns the narrative used by
 * the markdown generator.
 *
 * <p>This decouples the tutorial narrative from the model, so the XMI
 * artifacts delivered to consumers never carry documentation payloads.
 */
public interface TutorialDescriptionSupplier {

    /** Returns the description for this tutorial. */
    TutorialDescription describe();
}
