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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;

public class CalculatedMemberMappingImpl extends AbstractElementMappingImpl implements CalculatedMemberMapping {

    private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties;

    private CellFormatterMappingImpl cellFormatter;

    private String formula;

    private String displayFolder;

    private String formatString;

    private HierarchyMappingImpl hierarchy;

    private DimensionConnectorMappingImpl dimensionConector;

    private String parent;

    private boolean visible = true;

    private CalculatedMemberMappingImpl(Builder builder) {
        this.calculatedMemberProperties = builder.calculatedMemberProperties;
        this.cellFormatter = builder.cellFormatter;
        this.formula = builder.formula;
        this.displayFolder = builder.displayFolder;
        this.formatString = builder.formatString;
        this.hierarchy = builder.hierarchy;
        this.parent = builder.parent;
        this.setVisible(builder.visible);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public List<CalculatedMemberPropertyMappingImpl> getCalculatedMemberProperties() {
        return calculatedMemberProperties;
    }

    public void setCalculatedMemberProperties(List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }

    public CellFormatterMappingImpl getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMappingImpl cellFormatter) {
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

    public HierarchyMappingImpl getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMappingImpl hierarchy) {
        this.hierarchy = hierarchy;
    }

    public DimensionConnectorMappingImpl getDimensionConector() {
        return dimensionConector;
    }

    public void setDimensionConector(DimensionConnectorMappingImpl dimensionConector) {
        this.dimensionConector = dimensionConector;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static final class Builder {
        private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties = new ArrayList<>();
        private CellFormatterMappingImpl cellFormatter;
        private String formula;
        private String displayFolder;
        private String formatString;
        private HierarchyMappingImpl hierarchy;
        private String parent;
        private boolean visible = true;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withCalculatedMemberProperties(
                List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties) {
            this.calculatedMemberProperties = calculatedMemberProperties;
            return this;
        }

        public Builder withCellFormatter(CellFormatterMappingImpl cellFormatter) {
            this.cellFormatter = cellFormatter;
            return this;
        }

        public Builder withFormula(String formula) {
            this.formula = formula;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withFormatString(String formatString) {
            this.formatString = formatString;
            return this;
        }

        public Builder withHierarchy(HierarchyMappingImpl hierarchy) {
            this.hierarchy = hierarchy;
            return this;
        }

        public Builder withParent(String parent) {
            this.parent = parent;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withAnnotations(List<AnnotationMappingImpl> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDocumentation(DocumentationMappingImpl documentation) {
            this.documentation = documentation;
            return this;
        }

        public CalculatedMemberMappingImpl build() {
            return new CalculatedMemberMappingImpl(this);
        }
    }
}
