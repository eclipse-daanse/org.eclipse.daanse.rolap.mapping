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

import org.eclipse.emf.ecore.EObject;

/**
 * One narrative section in a tutorial. Order is controlled by the
 * {@code orderMajor}/{@code orderMinor}/{@code orderMicro} triple; headings
 * deepen with each non-zero minor/micro level.
 *
 * @param title               section heading (nullable)
 * @param body                markdown body (nullable)
 * @param orderMajor          primary sort key
 * @param orderMinor          secondary sort key — non-zero makes heading deeper
 * @param orderMicro          tertiary sort key — non-zero makes heading deeper still
 * @param elementRef          EObject whose structure should be rendered as an
 *                            XMI snippet below the body; {@code null} means
 *                            no snippet
 * @param showContainmentDepth how many containment levels to keep when
 *                            rendering the snippet ({@code 0} = only the
 *                            element itself, no contained children)
 */
public record DocSection(
        String title,
        String body,
        int orderMajor,
        int orderMinor,
        int orderMicro,
        EObject elementRef,
        int showContainmentDepth) {
}
