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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLMapping;

public class SQLExpressionMappingImpl implements SQLExpressionMapping {

    private List<? extends SQLMapping> sqls;

    public List<? extends SQLMapping> getSqls() {
        return sqls;
    }

    public void setSqls(List<? extends SQLMapping> sqls) {
        this.sqls = sqls;
    }
}
