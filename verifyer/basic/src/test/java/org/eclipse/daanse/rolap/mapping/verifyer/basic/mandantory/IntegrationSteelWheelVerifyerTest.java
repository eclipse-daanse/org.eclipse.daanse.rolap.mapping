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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class IntegrationSteelWheelVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory.MandantoriesVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    @Test
    void testSteelWheels(
        @InjectService(filter = "(component.name=org.eclipse.daanse.rolap.mapping.instance.rec.complex.steelwheels.SteelwheelsSupplier)")
            CatalogMappingSupplier supplier)
        throws CloneNotSupportedException {
        doTest(supplier);
    }

    private void doTest(CatalogMappingSupplier supplier) {
        CatalogMapping catalog = supplier.get();
        assertThat(catalog).isNotNull();
    }
}
