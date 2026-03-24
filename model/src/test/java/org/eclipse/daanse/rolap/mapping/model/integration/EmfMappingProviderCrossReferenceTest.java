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
package org.eclipse.daanse.rolap.mapping.model.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.model.provider.AnnotationHelper.SetupMappingProviderWithTestInstanceAndAdditionalResources;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.fennec.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
public class EmfMappingProviderCrossReferenceTest {

    @Test
    @SetupMappingProviderWithTestInstanceAndAdditionalResources
    public void crossReference(
            @InjectService(cardinality = 1, timeout = 10000) CatalogMappingSupplier supplier) {

        Catalog catalog = supplier.get();

        assertThat(catalog).isNotNull();
        assertThat(catalog.getName()).isEqualTo("testCatalog");
        assertThat(catalog.getDbschemas()).isNotEmpty();

        DatabaseSchema schema = catalog.getDbschemas().get(0);

        // verify cross-reference was resolved (not an unresolved proxy)
        assertThat(((InternalEObject) schema).eIsProxy()).isFalse();

        assertThat(schema.getName()).isEqualTo("testSchema");
        assertThat(schema.getId()).isEqualTo("dbs1");
        assertThat(schema.getTables()).hasSize(1);
        assertThat(schema.getTables().get(0).getName()).isEqualTo("testTable");
    }
}
