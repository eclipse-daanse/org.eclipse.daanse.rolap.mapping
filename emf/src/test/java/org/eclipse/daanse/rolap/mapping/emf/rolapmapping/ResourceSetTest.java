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
package org.eclipse.daanse.rolap.mapping.emf.rolapmapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
public class ResourceSetTest {

    private static String BASE_DIR = System.getProperty("basePath");

    @Test
    public void resourceSetExistsTest(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        URI uri = URI.createURI(BASE_DIR + "/src/test/resources/RolapContext.xmi");
        Resource resource = rs.getResource(uri, true);
        resource.load(Map.of());
        EObject root = resource.getContents().get(0);
        System.out.println(root);

    }

    @TempDir
    Path tempDir;

    @Test
    public void write(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ServiceAware<ResourceSet> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        ResourceSet rs = saResourceSet.getService();

        Path file = Files.createTempFile(tempDir, "out", ".xmi");
        URI uri = URI.createFileURI(file.toAbsolutePath().toString());
        Resource resource = rs.createResource(uri);
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());

        resource.save(Map.of());
        System.out.println(Files.readString(file));


    }

}
