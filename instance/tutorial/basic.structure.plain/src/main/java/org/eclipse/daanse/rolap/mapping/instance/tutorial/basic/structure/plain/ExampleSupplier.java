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
package org.eclipse.daanse.rolap.mapping.instance.tutorial.basic.structure.plain;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.RolapContextMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RolapContextMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.TUTORIAL, source = Source.POJO, number = "1")
@Component(service =  RolapContextMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class ExampleSupplier implements RolapContextMappingSupplier {

    private final static String name = "Structure of a Mapping";

    private final static String documentation_context_text = """
            RolapContext is a Container for Catalogs that are the major element of ther olap structure.
            """;

    private final static SchemaMappingImpl schema = SchemaMappingImpl.builder()
            .withName("AnySchemaName")
            .withDocumentation(new DocumentationMappingImpl("Container for Cubes"))

            .build();

    private final static CatalogMappingImpl catalog = CatalogMappingImpl.builder()
            .withName("AnyCatalogName")
            .withSchemas(List.of(schema))
            .withDocumentation(new DocumentationMappingImpl("Container for Schemas"))
            .build();

    @Override
    public RolapContextMapping get() {
        return RolapContextMappingImpl.builder()
                .withName(name)
                .withDocumentation(new DocumentationMappingImpl(documentation_context_text))
                .withCatalogs(List.of(catalog))
                .build();
    }

}
