/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.description;

import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public interface DescriptionVerifierConfig {

    @AttributeDefinition(description = "calculatedMemberProperty")
    default Level calculatedMemberProperty() {
        return null;
    }

    @AttributeDefinition(description = "action")
    default Level action() {
        return null;
    }

    @AttributeDefinition(description = "calculatedMember")
    default Level calculatedMember() {
        return null;
    }

    @AttributeDefinition(description = "cube")
    default Level cube() {
        return null;
    }

    @AttributeDefinition(description = "drillThroughAction")
    default Level drillThroughAction() {
        return null;
    }

    @AttributeDefinition(description = "hierarchy")
    default Level hierarchy() {
        return null;
    }

    @AttributeDefinition(description = "level")
    default Level level() {
        return null;
    }

    @AttributeDefinition(description = "measure")
    default Level measure() {
        return null;
    }

    @AttributeDefinition(description = "kpi")
    default Level kpi() {
        return null;
    }

    @AttributeDefinition(description = "namedSet")
    default Level namedSet() {
        return null;
    }

    @AttributeDefinition(description = "parameter")
    default Level parameter() {
        return null;
    }

    @AttributeDefinition(description = "dimension")
    default Level dimension() {
        return null;
    }

    @AttributeDefinition(description = "property")
    default Level property() {
        return null;
    }

    @AttributeDefinition(description = "schema")
    default Level schema() {
        return null;
    }

    @AttributeDefinition(description = "sharedDimension")
    default Level sharedDimension() {
        return null;
    }

    @AttributeDefinition(description = "virtualCube")
    default Level virtualCube() {
        return null;
    }
}
