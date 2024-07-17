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
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;

public class JoinQueryMappingImpl extends QueryMappingImpl implements JoinQueryMapping {

    private JoinedQueryElementMapping left;

    private JoinedQueryElementMapping right;

    public JoinedQueryElementMapping getLeft() {
        return left;
    }

    public void setLeft(JoinedQueryElementMapping left) {
        this.left = left;
    }

    public JoinedQueryElementMapping getRight() {
        return right;
    }

    public void setRight(JoinedQueryElementMapping right) {
        this.right = right;
    }
}
