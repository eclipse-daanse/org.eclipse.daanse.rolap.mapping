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

import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;

public class JoinQueryMappingImpl extends QueryMappingImpl implements JoinQueryMapping {

    private JoinedQueryElementMappingImpl left;

    private JoinedQueryElementMappingImpl right;

    public JoinedQueryElementMappingImpl getLeft() {
        return left;
    }

    public void setLeft(JoinedQueryElementMappingImpl left) {
        this.left = left;
    }

    public JoinedQueryElementMappingImpl getRight() {
        return right;
    }

    public void setRight(JoinedQueryElementMappingImpl right) {
        this.right = right;
    }
}
