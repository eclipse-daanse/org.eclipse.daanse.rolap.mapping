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
package org.eclipse.daanse.rolap.mapping.instance.rec.tutorial.basic.structure.plain;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.TUTORIAL, source = Source.POJO, number = "1")
@Component(service =  CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class TutorialMappingSupplier implements CatalogMappingSupplier {

    private final static CatalogMappingImpl schema = CatalogMappingImpl.builder()
            .withName("AnySchemaName")

            .build();

    @Override
    public CatalogMapping get() {
        return schema;
    }

}
