/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.instance.emf.serializer.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.util.CatalogFingerprint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.ServiceReference;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class FingerprintCorpusTest {

    @Test
    void corpusHashesStableAndPairwiseDistinct(
            @InjectService ServiceAware<CatalogMappingSupplier> mappingSuppliersSA) {

        List<ServiceReference<CatalogMappingSupplier>> refs = mappingSuppliersSA.getServiceReferences();
        assertThat(refs).as("registered catalog suppliers").isNotEmpty();

        Map<String, List<String>> suppliersByHash = new LinkedHashMap<>();
        List<String> unstable = new ArrayList<>();
        for (ServiceReference<CatalogMappingSupplier> ref : refs) {
            CatalogMappingSupplier supplier = mappingSuppliersSA.getService(ref);
            String id = supplier.getClass().getName();

            Catalog first = supplier.get();
            String hex = CatalogFingerprint.sha256Hex(first);
            String hexAgain = CatalogFingerprint.sha256Hex(supplier.get());
            if (!hex.equals(hexAgain)) {
                unstable.add(id);
            }
            suppliersByHash.computeIfAbsent(hex, k -> new ArrayList<>()).add(id + " (" + first.getName() + ")");
        }

        assertThat(unstable).as("suppliers whose two invocations hash differently").isEmpty();

        List<String> collisions = suppliersByHash.values().stream().filter(l -> l.size() > 1)
                .map(Object::toString).toList();
        assertThat(collisions).as("distinct catalogs sharing one fingerprint").isEmpty();
    }
}
