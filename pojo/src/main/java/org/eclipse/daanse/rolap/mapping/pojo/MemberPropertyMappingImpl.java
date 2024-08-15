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

import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;

public class MemberPropertyMappingImpl extends AbstractElementMappingImpl implements MemberPropertyMapping {

    private MemberPropertyFormatterMappingImpl formatter;

    private String column;

    private boolean dependsOnLevelValue;

    private DataType dataType;

    private MemberPropertyMappingImpl(Builder builder) {
        this.formatter = builder.formatter;
        this.column = builder.column;
        this.dependsOnLevelValue = builder.dependsOnLevelValue;
        this.dataType = builder.dataType;
        super.setAnnotations(builder.annotations);
        super.setId(builder.id);
        super.setDescription(builder.description);
        super.setName(builder.name);
        super.setDocumentation(builder.documentation);
    }

    public MemberPropertyFormatterMappingImpl getFormatter() {
        return formatter;
    }

    public void setFormatter(MemberPropertyFormatterMappingImpl formatter) {
        this.formatter = formatter;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isDependsOnLevelValue() {
        return dependsOnLevelValue;
    }

    public void setDependsOnLevelValue(boolean dependsOnLevelValue) {
        this.dependsOnLevelValue = dependsOnLevelValue;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType type) {
        this.dataType = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private MemberPropertyFormatterMappingImpl formatter;
        private String column;
        private boolean dependsOnLevelValue;
        private DataType dataType;
        private List<AnnotationMappingImpl> annotations = new ArrayList<>();
        private String id;
        private String description;
        private String name;
        private DocumentationMappingImpl documentation;

        private Builder() {
        }

        public Builder withFormatter(MemberPropertyFormatterMappingImpl formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withDependsOnLevelValue(boolean dependsOnLevelValue) {
            this.dependsOnLevelValue = dependsOnLevelValue;
            return this;
        }

        public Builder withDataType(DataType dataType) {
            this.dataType = dataType;
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

        public MemberPropertyMappingImpl build() {
            return new MemberPropertyMappingImpl(this);
        }
    }
}
