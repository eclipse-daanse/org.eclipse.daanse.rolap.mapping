/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.daanse.rolap.mapping.model.RolapMappingPackage;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.Dimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.eclipse.daanse.rolap.mapping.model.olap.cube.Cube;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.util.Namespaces;
import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.util.Packages;
/**
 * Cross-file import: catalog B imports a
 * dimension owned by catalog A, both serialize to their own files, B's file
 * references A's per href, and after a fresh load B's dimension IS A's
 * instance again.
 */
class CrossCatalogImportTest {

    @TempDir
    Path dir;

    private static ResourceSet resourceSet() {
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        rs.getPackageRegistry().put(RolapMappingPackage.eNS_URI, RolapMappingPackage.eINSTANCE);
        for (EPackage sub : RolapMappingPackage.eINSTANCE.getESubpackages()) {
            register(rs, sub);
        }
        return rs;
    }

    private static void register(ResourceSet rs, EPackage p) {
        rs.getPackageRegistry().put(p.getNsURI(), p);
        p.getESubpackages().forEach(s -> register(rs, s));
    }

    @Test
    void importedDimensionSurvivesTheFileRoundTrip() throws Exception {
        // catalog A owns the shared dimension
        Catalog a = CatalogFactory.eINSTANCE.createCatalog();
        a.setName("Stammdaten");
        TimeDimension zeit = DimensionFactory.eINSTANCE.createTimeDimension();
        zeit.setName("Zeit");
        a.getOwnedElement().add(zeit);

        // catalog B imports it and wires it to its cube
        Catalog b = CatalogFactory.eINSTANCE.createCatalog();
        b.setName("Vertrieb");
        b.getImportedElement().add(zeit);
        PhysicalCube umsatz = CubeFactory.eINSTANCE.createPhysicalCube();
        umsatz.setName("Umsatz");
        DimensionConnector connector = DimensionFactory.eINSTANCE.createDimensionConnector();
        connector.setDimension(zeit);
        umsatz.getDimensionConnectors().add(connector);
        b.getOwnedElement().add(umsatz);

        // two files, one resource set
        ResourceSet out = resourceSet();
        URI uriA = URI.createFileURI(dir.resolve("a.xmi").toString());
        URI uriB = URI.createFileURI(dir.resolve("b.xmi").toString());
        Resource ra = out.createResource(uriA);
        ra.getContents().add(a);
        Resource rb = out.createResource(uriB);
        rb.getContents().add(b);
        ra.save(null);
        rb.save(null);

        // B's file references A's by href; A's file carries the accepted
        // importer back-reference (non-transient in the frozen core).
        String fileB = Files.readString(dir.resolve("b.xmi"));
        assertThat(fileB).contains("a.xmi#");
        String fileA = Files.readString(dir.resolve("a.xmi"));
        assertThat(fileA).contains("b.xmi#");

        // fresh load: demand-load resolves the proxy against the neighbour file
        ResourceSet in = resourceSet();
        Catalog b2 = (Catalog) in.getResource(uriB, true).getContents().get(0);
        Dimension imported = Packages.available(b2, Dimension.class).get(0);
        Catalog a2 = (Catalog) in.getResource(uriA, true).getContents().get(0);
        Dimension owned = Namespaces.ownedElementStream(a2, Dimension.class).findFirst().orElseThrow();

        assertThat(imported.getName()).isEqualTo("Zeit");
        assertThat(imported).isSameAs(owned);

        // and the functional wiring resolves to the very same instance
        PhysicalCube umsatz2 = (PhysicalCube) Packages.available(b2, Cube.class).get(0);
        assertThat(umsatz2.getDimensionConnectors().get(0).getDimension()).isSameAs(owned);
    }
}
