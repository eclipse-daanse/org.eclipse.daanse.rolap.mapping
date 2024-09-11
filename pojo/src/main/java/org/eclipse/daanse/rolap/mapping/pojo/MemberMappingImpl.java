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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberMapping;

public abstract class MemberMappingImpl extends AbstractElementMappingImpl implements MemberMapping {

    private String displayFolder;

    private String formatString;

    private boolean visible;

    private CellFormatterMapping cellFormatter;

    private List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties;

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CellFormatterMapping getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMapping cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public List<? extends CalculatedMemberPropertyMapping> getCalculatedMemberProperties() {
        return calculatedMemberProperties;
    }

    public void setCalculatedMemberProperties(List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }


}
