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
package org.eclipse.daanse.rolap.mapping.api.model.enums;

public enum MeasureAggregatorType {

    SUM("sum"), COUNT("count"), MIN("min"), MAX("max"), AVG("avg"), DICTINCT_COUNT("distinct-count"),
    IPP("Ipp"), NONE("None"), RND("Rnd"), FIRST("first"), LAST("last"), MEDIAN("median"), MODE("mode"),
    MOVINGAVERAGE3("movingAverage3"), PERCENTILE90("percentile90"), QUARTILE3("quartile3"), RANGE("range"),
    STDDEV("stddev"), VARIANCE("variance"), TRUTH("truth"), LISTAGG("LISTAGG");

    private final String value;

    MeasureAggregatorType(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static MeasureAggregatorType fromValue(String v) {
        for (MeasureAggregatorType c : MeasureAggregatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
