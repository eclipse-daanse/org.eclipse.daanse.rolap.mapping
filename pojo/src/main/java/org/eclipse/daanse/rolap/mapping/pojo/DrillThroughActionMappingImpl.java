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

import org.eclipse.daanse.rolap.mapping.api.model.DrillThroughActionMapping;

public class DrillThroughActionMappingImpl extends ActionMappingMappingImpl implements DrillThroughActionMapping {

    private List<DrillThroughAttributeMappingImpl> drillThroughAttribute;

    private List<DrillThroughMeasureMappingImpl> drillThroughMeasure;

    private boolean theDefault;

    public List<DrillThroughAttributeMappingImpl> getDrillThroughAttribute() {
        return drillThroughAttribute;
    }

    public void setDrillThroughAttribute(List<DrillThroughAttributeMappingImpl> drillThroughAttribute) {
        this.drillThroughAttribute = drillThroughAttribute;
    }

    public List<DrillThroughMeasureMappingImpl> getDrillThroughMeasure() {
        return drillThroughMeasure;
    }

    public void setDrillThroughMeasure(List<DrillThroughMeasureMappingImpl> drillThroughMeasure) {
        this.drillThroughMeasure = drillThroughMeasure;
    }

    public boolean isDefault() {
        return theDefault;
    }

    public void setDefault(boolean theDefault) {
        this.theDefault = theDefault;
    }
}
