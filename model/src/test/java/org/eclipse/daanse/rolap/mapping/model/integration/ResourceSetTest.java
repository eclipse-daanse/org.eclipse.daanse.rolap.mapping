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

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.emf.osgi.ResourceSetFactory;
import org.eclipse.fennec.emf.osgi.annotation.require.RequireEMF;
import org.eclipse.fennec.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.dictionary.Dictionaries;
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


    private static String formatValue(Object v) {
        if (v == null) {
            return "null";
        }
        if (v.getClass().isArray()) {
            StringBuilder sb = new StringBuilder("[");
            int len = Array.getLength(v);
            for (int i = 0; i < len; i++) {
                if (i > 0)
                    sb.append(", ");
                sb.append(Array.get(v, i));
            }
            sb.append("]");
            return sb.toString();
        }
        return v.toString();
    }

    @Test
    @Order(1)
    public void allServicesProperties(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 0, timeout = 1000) ServiceAware<EPackage> saEPackage,
            @InjectService(cardinality = 0, timeout = 1000) ServiceAware<ResourceSet> saResourceSet,
            @InjectService(cardinality = 0, timeout = 1000) ServiceAware<ResourceSetFactory> saResourceSetFactory)
            throws InterruptedException, IOException {

        Thread.sleep(1000);
        System.out.println("\n========== EPackage Services ==========");
        saEPackage.waitForService(1000);
        for (ServiceReference<EPackage> ref : saEPackage.getServiceReferences()) {
            System.out.println("EPackage:");
            Dictionaries.asMap(ref.getProperties())
                    .forEach((k, v) -> System.out.println("   " + k + " = " + formatValue(v)));
            System.out.println();
        }

        System.out.println("\n========== ResourceSet Services ==========");
        saResourceSet.waitForService(1000);
        for (ServiceReference<ResourceSet> ref : saResourceSet.getServiceReferences()) {
            System.out.println("ResourceSet:");
            Dictionaries.asMap(ref.getProperties())
                    .forEach((k, v) -> System.out.println("   " + k + " = " + formatValue(v)));
            System.out.println();
        }

        System.out.println("\n========== ResourceSetFactory Services ==========");
        saResourceSetFactory.waitForService(1000);
        for (ServiceReference<ResourceSetFactory> ref : saResourceSetFactory.getServiceReferences()) {
            System.out.println("ResourceSetFactory:");
            Dictionaries.asMap(ref.getProperties())
                    .forEach((k, v) -> System.out.println("   " + k + " = " + formatValue(v)));
            System.out.println();
        }
        System.out.println("==========================================\n");
    }

    
    @Test
    public void resourceSetExistsTest(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NSURI + "="
                    + RolapMappingPackage.eNS_URI+ ")",timeout = 10000) ServiceAware<ResourceSet> saResourceSet)
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
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NSURI + "="
                    + RolapMappingPackage.eNS_URI + ")",timeout = 10000) ServiceAware<EPackage> saResourceSet)
            throws SQLException, InterruptedException, IOException {
        assertThat(saResourceSet.getServices()).hasSize(1);

        EPackage rs = saResourceSet.getService();

//        Path file = Files.createTempFile(tempDir, "out", ".xmi");
//        URI uri = URI.createFileURI(file.toAbsolutePath().toString());
//        Resource resource = rs.createResource(uri);
//        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
//        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
//        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
//        resource.getContents().add(RolapMappingFactory.eINSTANCE.createCatalog());
//
//        resource.save(Map.of());
//        System.out.println(Files.readString(file));


    }

}
