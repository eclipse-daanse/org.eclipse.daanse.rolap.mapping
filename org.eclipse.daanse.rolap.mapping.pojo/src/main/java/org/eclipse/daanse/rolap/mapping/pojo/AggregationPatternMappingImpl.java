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

import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;

public class AggregationPatternMappingImpl extends AggregationTableMappingImpl implements AggregationPatternMapping{

    private String pattern;

    private List<? extends AggregationExcludeMapping> excludes;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<? extends AggregationExcludeMapping> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<? extends AggregationExcludeMapping> excludes) {
        this.excludes = excludes;
    }
}
