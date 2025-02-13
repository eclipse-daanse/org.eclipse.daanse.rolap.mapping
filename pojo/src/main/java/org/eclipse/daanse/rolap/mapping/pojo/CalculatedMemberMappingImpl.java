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
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;

public class CalculatedMemberMappingImpl extends MemberMappingImpl implements CalculatedMemberMapping {

    private String formula;
    private HierarchyMappingImpl hierarchy;

    private String parent;

    private PhysicalCubeMapping physicalCube;

    private CalculatedMemberMappingImpl(Builder builder) {
        this.formula = builder.formula;
        this.hierarchy = builder.hierarchy;
        this.parent = builder.parent;
        this.physicalCube = builder.physicalCube;
        this.setVisible(builder.visible);
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentations(builder.documentation);
        super.setDisplayFolder(builder.displayFolder);
        super.setFormatString(builder.formatString);
        super.setVisible(builder.visible);
        super.setCellFormatter(builder.cellFormatter);
        super.setCalculatedMemberProperties(builder.calculatedMemberProperties);
    }

    @Override
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    @Override
    public HierarchyMappingImpl getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMappingImpl hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public PhysicalCubeMapping getPhysicalCube() {
        return physicalCube;
    }

    public void setPhysicalCube(PhysicalCubeMapping physicalCube) {
        this.physicalCube = physicalCube;
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
        private PhysicalCubeMapping physicalCube;

        private Builder() {
        }

        public Builder withCalculatedMemberProperties(
            List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperties
        ) {
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

        public Builder withPhysicalCube(PhysicalCubeMapping physicalCube) {
            this.physicalCube = physicalCube;
            return this;
        }

        public CalculatedMemberMappingImpl build() {
            return new CalculatedMemberMappingImpl(this);
        }
    }

}
