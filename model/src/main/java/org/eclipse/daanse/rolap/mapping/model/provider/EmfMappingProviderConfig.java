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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.model.provider;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public @interface EmfMappingProviderConfig {

    @AttributeDefinition(name = Constants.RESOURCE_URL, required = true, description = "File path to the primary EMF resource containing the Catalog mapping definition. "
            + "The file is loaded as an EMF Resource and must contain a Catalog root element.")
    String resource_url();

    @AttributeDefinition(name = Constants.ADDITIONAL_RESOURCE_GLOBS, required = false, description = "Optional glob patterns for additional EMF resources to load before the primary resource. "
            + "These resources are resolved via cross-references from the primary Catalog resource. "
            + "Patterns follow java.nio.file.PathMatcher glob syntax, e.g. '/data/mappings/**/*.xmi'.")
    String[] additional_resource_globs() default {};
}
