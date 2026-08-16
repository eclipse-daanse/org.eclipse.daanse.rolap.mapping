/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;

import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.junit.jupiter.api.Test;

import org.eclipse.daanse.rolap.mapping.model.provider.util.CwmHelper;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.util.TaggedValues;
class TagNamingSchemeTest {

    private static List<VerificationResult> verify(Catalog catalog) {
        return new MandantoriesSchemaWalker(null).checkSchema(catalog);
    }

    private static Catalog catalogWithTag(String tag) {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");
        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("SalesCube");
        catalog.getOwnedElement().add(cube);
        TaggedValues.set(cube, tag, "x");
        return catalog;
    }

    @Test
    void unconventionalTagsAreWarned() {
        List<VerificationResult> results = verify(catalogWithTag("legacyFlag"));
        assertThat(results).anyMatch(r -> r.description().contains("legacyFlag")
                && r.description().contains("daanse:"));
    }

    @Test
    void daansePrefixAndLocalizationKeysPass() {
        assertThat(verify(catalogWithTag(CwmHelper.tag("cache.timeout"))))
                .noneMatch(r -> r.description().contains("cache.timeout"));
        assertThat(verify(catalogWithTag("caption.de_DE")))
                .noneMatch(r -> r.description().contains("caption.de_DE"));
    }
}
