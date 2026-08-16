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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.junit.jupiter.api.Test;

class UnresolvedProxyTest {

    @Test
    void brokenHrefIsReported() {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");

        // a proxy pointing at a file that does not exist — the shape a broken
        // cross-catalog import leaves behind after loading
        StandardDimension ghost = DimensionFactory.eINSTANCE.createStandardDimension();
        ((InternalEObject) ghost).eSetProxyURI(URI.createFileURI("missing.xmi").appendFragment("_dim_gone"));
        catalog.getImportedElement().add(ghost);

        List<VerificationResult> results = new MandantoriesSchemaWalker(null).checkSchema(catalog);
        assertThat(results).anyMatch(r -> r.description().contains("missing.xmi")
                && r.description().contains("unresolved"));
    }

    @Test
    void resolvedModelsStaySilent() {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");
        StandardDimension dim = DimensionFactory.eINSTANCE.createStandardDimension();
        dim.setName("Zeit");
        catalog.getOwnedElement().add(dim);

        List<VerificationResult> results = new MandantoriesSchemaWalker(null).checkSchema(catalog);
        assertThat(results).noneMatch(r -> r.description().contains("unresolved"));
    }
}
