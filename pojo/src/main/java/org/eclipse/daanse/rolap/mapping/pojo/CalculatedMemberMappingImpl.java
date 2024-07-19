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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;

public class CalculatedMemberMappingImpl extends AbstractElementMappingImpl implements CalculatedMemberMapping {

    private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties;

    private CellFormatterMapping cellFormatter;

    private String formula;

    private String displayFolder;

    private String formatString;

    private HierarchyMapping hierarchy;

    private DimensionConnectorMapping dimensionConector;

    private String parent;

    public List<CalculatedMemberPropertyMappingImpl> getCalculatedMemberProperties() {
        return calculatedMemberProperties;
    }

    public void setCalculatedMemberProperties(List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }

    public CellFormatterMapping getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMapping cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public HierarchyMapping getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMapping hierarchy) {
        this.hierarchy = hierarchy;
    }

    public DimensionConnectorMapping getDimensionConector() {
        return dimensionConector;
    }

    public void setDimensionConector(DimensionConnectorMapping dimensionConector) {
        this.dimensionConector = dimensionConector;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
