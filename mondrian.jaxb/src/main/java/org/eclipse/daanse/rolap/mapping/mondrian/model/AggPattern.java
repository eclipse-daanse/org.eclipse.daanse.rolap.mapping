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
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "aggFactCount", "aggIgnoreColumns", "aggForeignKeys", "aggMeasures", "aggLevels",
        "aggExcludes", "measureFactCounts" })
public class AggPattern implements AggTable {

    @XmlElement(name = "AggFactCount", required = true, type = AggColumnName.class)
    protected AggColumnName aggFactCount;
    @XmlElement(name = "AggIgnoreColumn", type = AggColumnName.class)
    protected List<AggColumnName> aggIgnoreColumns;
    @XmlElement(name = "AggForeignKey", type = AggForeignKey.class)
    protected List<AggForeignKey> aggForeignKeys;
    @XmlElement(name = "AggMeasure", required = true, type = AggMeasure.class)
    protected List<AggMeasure> aggMeasures;
    @XmlElement(name = "AggLevel", type = AggLevel.class)
    protected List<AggLevel> aggLevels;
    @XmlElement(name = "AggExclude", type = AggExclude.class)
    protected List<AggExclude> aggExcludes;
    @XmlAttribute(name = "pattern", required = true)
    protected String pattern;
    @XmlAttribute(name = "ignorecase")
    protected Boolean ignorecase;
    @XmlElement(name = "AggMeasureFactCount", type = AggMeasureFactCount.class)
    protected List<AggMeasureFactCount> measureFactCounts;

    public List<AggExclude> aggExcludes() {
        if (aggExcludes == null) {
            aggExcludes = new ArrayList<>();
        }
        return this.aggExcludes;
    }

    public AggColumnName aggFactCount() {
        return aggFactCount;
    }

    public List<AggForeignKey> aggForeignKeys() {
        if (aggForeignKeys == null) {
            aggForeignKeys = new ArrayList<>();
        }
        return this.aggForeignKeys;
    }

    public List<AggColumnName> aggIgnoreColumns() {
        if (aggIgnoreColumns == null) {
            aggIgnoreColumns = new ArrayList<>();
        }
        return this.aggIgnoreColumns;
    }

    public List<AggLevel> aggLevels() {
        if (aggLevels == null) {
            aggLevels = new ArrayList<>();
        }
        return this.aggLevels;
    }

    public List<AggMeasure> aggMeasures() {
        if (aggMeasures == null) {
            aggMeasures = new ArrayList<>();
        }
        return this.aggMeasures;
    }

    public Boolean ignorecase() {
        if (ignorecase == null) {
            return true;
        } else {
            return ignorecase;
        }
    }

    public List<AggMeasureFactCount> measuresFactCounts() {
        return measureFactCounts;
    }

    public String pattern() {
        return pattern;
    }

    public void setAggExcludes(List<AggExclude> aggExcludes) {
        this.aggExcludes = aggExcludes;
    }

    public void setAggFactCount(AggColumnName value) {
        this.aggFactCount = value;
    }

    public void setAggForeignKeys(List<AggForeignKey> aggForeignKeys) {
        this.aggForeignKeys = aggForeignKeys;
    }

    public void setAggIgnoreColumns(List<AggColumnName> aggIgnoreColumns) {
        this.aggIgnoreColumns = aggIgnoreColumns;
    }

    public void setAggLevels(List<AggLevel> aggLevels) {
        this.aggLevels = aggLevels;
    }

    public void setAggMeasures(List<AggMeasure> aggMeasures) {
        this.aggMeasures = aggMeasures;
    }

    public void setIgnorecase(Boolean value) {
        this.ignorecase = value;
    }

    public void setMeasuresFactCounts(List<AggMeasureFactCount> measuresFactCounts) {
        this.measureFactCounts = measuresFactCounts;
    }

    public void setPattern(String value) {
        this.pattern = value;
    }
}